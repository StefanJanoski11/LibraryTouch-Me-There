package cn.Boys.library.controller;

import cn.Boys.library.component.EmailProducer;
import cn.Boys.library.dto.Result;
import cn.Boys.library.entity.DES;
import cn.Boys.library.entity.User;
import cn.Boys.library.enums.ResultEnum;
import cn.Boys.library.enums.UserSincerityEnum;
import cn.Boys.library.mapper.UserMapper;
import cn.Boys.library.service.impl.BatchSetUserServiceImpl;
import cn.Boys.library.service.impl.UserServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Api("用户相关")
@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Log4j.class);
    @Autowired
    UserMapper userMapper;
    @Autowired
    UserServiceImpl userService;
    @Autowired
    BatchSetUserServiceImpl batchSetUserService;
    @Autowired
    EmailProducer emailProducer;

    /*获取所有存在的用户*/
    @GetMapping("/getAll")
    public Result selectAllExistUser(Integer pageNum){
        pageNum = pageNum==null?1:pageNum;
        PageHelper.startPage(pageNum,10);/*一页存放数量*/
        List<User> userList = userMapper.selectAllExistUser();
        if (userList.size() == 0){
            return new Result(null, ResultEnum.NOT_FOUND);
        }
        PageInfo<User> info = new PageInfo<>(userList,5);/*可以显示10页点击【1】【2】..【10】*/
        log.info("正在执行批量导入redis线程...");

        batchSetUserService.batchSetUserInRedis(userList);
        return new Result(info, ResultEnum.OK);
    }

    /*用户信息修改*/
    @PostMapping("/alterDetail")
    public Result alterDetail(@Valid User user,BindingResult result, String checkPassword){
        if (result.hasErrors()){
            /*存在格式错误*/
            return new Result(result,ResultEnum.NOT_ACCEPTABLE);
        }
        if (user.getUser_sex() != 1 && user.getUser_sex() != 0){
            return new Result("用户性别有误，请正确选择！",ResultEnum.NOT_ACCEPTABLE);
        }
        if (!user.getUser_password().equals(checkPassword)){
            return new Result("两次密码输入必须正确！",ResultEnum.NOT_ACCEPTABLE);
        }
        user.setUser_password(DES.getEncryptString(checkPassword));
        /*数据库修改用户信息*/
        int i = userMapper.alterDetail(user);
        if (i<=0){
            log.error("数据库修改失败！");
            return new Result(null, ResultEnum.NOT_ACCEPTABLE);/*修改数据库失败,没有找到*/
        }
        /*修改完数据库，从数据库获取完整信息，再修改redis*/
        user = userMapper.selectUserByPrimaryKey(user.getUser_id());
        if (!userService.putUserInRedis(user)){
            log.error("redis修改失败了！");
        }
        return new Result(user, ResultEnum.OK);
    }

    /*单独修改密码功能*/
    @PutMapping("/changePassword")
    public Result changePassword(Integer user_id, String newPassword, String checkPassword){
        if (newPassword.length()<=30 && newPassword.length()>=8){
            if (userService.checkPassword(newPassword, checkPassword)){/*两次密码校验成功*/
                /*修改数据库*/
                newPassword = DES.getEncryptString(newPassword);
                if (userMapper.changePassword(newPassword,user_id)<=0){
                    log.error("数据库修改失败");
                    return new Result(null, ResultEnum.NOT_FOUND);/*修改数据库失败,没有找到*/
                }
                /*修改数据成功后*/
                /*修改redis*/
                User user = userMapper.selectUserByPrimaryKey(user_id);
                if (!userService.putUserInRedis(user)){
                    log.error("添加失败！");
                }
                /*数据库修改成功*/
                return new Result(null, ResultEnum.OK);
            }
            return new Result(null, ResultEnum.NOT_ACCEPTABLE);/*密码错误*/
        }
        return new Result(null, ResultEnum.NOT_ACCEPTABLE);/*格式不符合*/
    }

    /*检查名字是否重复*/
    @GetMapping("/checkUsernameIsExist")
    public Result checkUsernameIsExist(String user_name){
        User user = userService.selectUserByUsername(user_name);
        if (user != null){/*已存在用户*/
            return new Result(null, ResultEnum.NOT_ACCEPTABLE);
        }
        user = userMapper.selectUserByUsername(user_name);
        if (user!=null){
            /*在数据库中已经存在用户名*/
            return new Result(null, ResultEnum.NOT_ACCEPTABLE);
        }
        /*用户未存在*/
        return new Result(null, ResultEnum.OK);
    }

    /*检查手机是否重复*/
    @GetMapping("/checkPhoneIsExist")
    public Result checkPhoneIsExist(String user_phone){
        User user = userMapper.selectUserByPhone(user_phone);
        if (user!=null){
            /*在数据库中已经存在用户名*/
            return new Result(null, ResultEnum.NOT_ACCEPTABLE);
        }
        /*用户未存在*/
        return new Result(null, ResultEnum.OK);
    }

    /*登录模块*/
    @PostMapping("/login")
    public Result login(@RequestBody User user_temp) {
        System.out.println(user_temp.toString());
        User user = userService.selectUserByUsername(user_temp.getUser_name());
        user_temp.setUser_password(DES.getEncryptString(user_temp.getUser_password()));
        if (user != null){/*在redis中找到了*/
            if (userService.checkPassword(user.getUser_password(),user_temp.getUser_password())){/*验证密码是否正确*/
                log.info("用户名和密码均正确!");
                return new Result(user, ResultEnum.OK);
            }else {
                return new Result(null, ResultEnum.NOT_FOUND);
            }
        }
        /*在redis没找到，均执行下列操作*/
        user = userMapper.selectUserByUsernameAndPassword(user_temp.getUser_name(), user_temp.getUser_password());
        if (user == null){
             return new Result(null, ResultEnum.NOT_FOUND);
        }
        /*找到用户了*/
        if (userService.putUserInRedis(user)){
            log.info("从数据库中找到了，并正在写入到redis中...");
        }else {
            log.error("从数据库中找到了，但写入redis过程出现问题...");
        }
        return new Result(user, ResultEnum.OK);
    }

    /*注册模块*/
    @PostMapping("/register")
    public Result register(@Valid User user, BindingResult result, String checkPassword){
        if (result.hasErrors()){
            /*存在格式错误*/
            return new Result(null, ResultEnum.NOT_ACCEPTABLE);
        }
        if (user.getUser_sex()!=1 && user.getUser_sex()!=0){
            return new Result("用户性别有误，请正确选择！", ResultEnum.NOT_ACCEPTABLE);
        }
        if (!user.getUser_password().equals(checkPassword)){
            return new Result("两次密码输入必须正确！",ResultEnum.NOT_ACCEPTABLE);
        }
        user.setUser_password(DES.getEncryptString(checkPassword));
        int i = userMapper.registerUser(user);
        /*发送邮件到注册用户邮箱*/
        try {
            emailProducer.send(user.getUser_email());
        }catch (Exception e){
            log.error(e.toString());
        }
        /*数据库添加成功*/
        if (i>0){
            /*将注册者信息存到redis中*/
            if (!userService.putUserInRedis(user)){
                log.error("添加失败！");
            }
            return new Result(user, ResultEnum.CREATED);
        }
        return new Result(user, ResultEnum.NOT_ACCEPTABLE);
    }

    /*用户查看我的详情模块*/
    @GetMapping("/detail")
    public Result getMyDetail(@RequestParam("user_id") Integer user_id, @RequestParam("user_name")String user_name){
        User user = userService.selectUserByUsername(user_name);
        if (user!=null){
            return new Result(user, ResultEnum.OK);
        }
        /*在redis中没有找到该用户,就到数据库中查找*/
        user = userMapper.selectUserByPrimaryKey(user_id);
        if (user == null){
            return new Result(null, ResultEnum.NOT_FOUND);
        }
        /*将数据库找到的信息，放到redis中*/
        if (!userService.putUserInRedis(user)){
            log.error("添加失败！");
        }
        return new Result(user, ResultEnum.CREATED);
    }

    /*批量导出图书信息*/
    @GetMapping("/download")
    public Result download(HttpServletResponse response) throws IOException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String filename = LocalDate.now().format(dtf) +"_USER.xlsx";
        filename = URLDecoder.decode(filename,"UTF-8");
        String[] head = {"编号","身份","用户名","邮箱","性别","手机号码","生日","住址","个人描述","用户诚信度","存在状态","注册时间","注册者编号（0为注册）","最新修改时间","最新修改者编号（0为注册）"};
        HSSFWorkbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("用户详情信息");
        /*设置表的表头*/
        int currRow = 0;
        Row row = sheet.createRow(currRow++);
        for (int i = 0; i < head.length; i++) {
            Cell cell = row.createCell(i);
            HSSFRichTextString hrts = new HSSFRichTextString(head[i]);
            cell.setCellValue(hrts);
        }
        List<User> userList = userMapper.selectAllUser();
        for (User user :userList) {
            Row sheetRow = sheet.createRow(currRow++);
            sheetRow.createCell(0).setCellValue(user.getUser_id());
            sheetRow.createCell(1).setCellValue(user.getUser_identity_id());
            sheetRow.createCell(2).setCellValue(user.getUser_name());
            sheetRow.createCell(3).setCellValue(user.getUser_email());
            sheetRow.createCell(4).setCellValue(user.getUser_sex());
            sheetRow.createCell(5).setCellValue(user.getUser_phone());
            sheetRow.createCell(6).setCellValue(user.getUser_birthday());
            sheetRow.createCell(7).setCellValue(user.getUser_address());
            sheetRow.createCell(8).setCellValue(user.getUser_self_desc());
            sheetRow.createCell(9).setCellValue(UserSincerityEnum.stateOf(user.getUser_sincerity()).getDescribe());
            sheetRow.createCell(10).setCellValue(user.getUser_exist_state()==1?"存在":"不存在");
            sheetRow.createCell(11).setCellValue(user.getUser_create_date());
            sheetRow.createCell(12).setCellValue(user.getUser_create_admin());
            sheetRow.createCell(13).setCellValue(user.getUser_alter_date());
            sheetRow.createCell(14).setCellValue(user.getUser_alter_admin());
        }
        response.setContentType("application/octet-stream");/*二进制流*/
        response.setHeader("Content-disposition", "attachment;filename=" + filename);
        OutputStream os = null;
        try{
            os = response.getOutputStream();
            response.flushBuffer();
            workbook.write(os);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(null, ResultEnum.SERVER_ERROR);
        }finally {
            os.close();
        }
        log.info("文件名称："+filename);
        return new Result(null, ResultEnum.OK);
    }

    /**
     * 注销登录
     * @param
     * @return
     */
    @GetMapping("/logout")
    public Result logout(User user){

        return new Result("logout",ResultEnum.OK);
    }

    @ApiOperation("根据用户名模糊查询用户")
    @GetMapping("/quaryUser")
    public Result quaryUser(String name){
        return userService.quaryUserByName(name);
    }

    @ApiOperation("删除用户")
    @GetMapping("/deleteUser")
    public Result deleteUser(Integer id){
        return userService.deleteUser(id);
    }
    @ApiOperation("查询所有还存活用户")
    @GetMapping("/quaryExistUser")
    public Result quaryExistUser(){
        return userService.selectUserExist();
    }
}
