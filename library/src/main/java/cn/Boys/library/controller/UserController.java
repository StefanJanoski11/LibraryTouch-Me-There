package cn.Boys.library.controller;

import cn.Boys.library.dto.Result;
import cn.Boys.library.entity.DES;
import cn.Boys.library.entity.User;
import cn.Boys.library.enums.ResultEnum;
import cn.Boys.library.mapper.UserMapper;
import cn.Boys.library.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {
    @Autowired
    UserMapper userMapper;
    @Autowired
    UserServiceImpl userService;



    /*修改密码*/
    @PutMapping("/changePassword")
    public Result changePassword(Integer user_id,String newPassword,String checkPassword){
        if (newPassword.length()<=30 && newPassword.length()>=8){
            if (userService.checkPassword(newPassword, checkPassword)){/*两次密码校验成功*/
                /*修改数据库*/
                newPassword = DES.getEncryptString(newPassword);
                if (userMapper.changePassword(newPassword,user_id)<=0){
                    System.out.println("数据库修改失败!");
                    return new Result(null,ResultEnum.SERVER_ERROR);/*修改数据库失败*/
                }
                /*修改数据成功后*/
                /*修改redis*/
                User user = userMapper.selectUserByPrimaryKey(user_id);
                if (!userService.putUserInRedis(user)){
                    System.out.println("添加失败！");
                }
                /*数据库修改成功*/
                return new Result(null,ResultEnum.OK);
            }
            return new Result(null,ResultEnum.NOT_ACCEPTABLE);/*密码错误*/
        }
        return new Result(null,ResultEnum.NOT_ACCEPTABLE);/*格式不符合*/
    }

    /*检查名字是否重复*/
    @GetMapping("/checkUsernameIsExist")
    public Result checkUsernameIsExist(String user_name){
        User user = userService.selectUserByUsername(user_name);
        if (user != null){/*已存在用户*/
            return new Result(null,ResultEnum.NOT_ACCEPTABLE);
        }
        /*用户未存在*/
        return new Result(null,ResultEnum.OK);
    }

    /*登录模块*/
    @GetMapping("/login")
    public Result login(String username,String password) {
        User user = userService.selectUserByUsername(username);
        password = DES.getEncryptString(password);
        if (user != null){/*在redis中找到了，并且密码正确*/
            if (userService.checkPassword(user.getUser_password(),password)){/*验证密码是否正确*/
                System.out.println("用户名和密码均正确");
                return new Result(user,ResultEnum.OK);
            }else {
                return new Result(null,ResultEnum.NOT_FOUND);
            }
        }
        /*在redis没找到，均执行下列操作*/
        user = userMapper.selectUserByUsernameAndPassword(username, password);
        if (user == null){
            return new Result(null,ResultEnum.NOT_FOUND);
        }
        /*找到用户了*/
        if (userService.putUserInRedis(user)){
            if (userService.checkPassword(user.getUser_password(),password)){/*验证数据库的用户是否密码正确*/
                return new Result(user,ResultEnum.CREATED);
            }else {
                return new Result(null,ResultEnum.NOT_FOUND);
            }
        }
        return new Result(null,ResultEnum.SERVER_ERROR);

    }


//    /**
//     * 检测session是否有效
//     * @param request
//     * @return
//     */
//    @GetMapping("/isValid")
//    public Result isSessionValid(HttpServletRequest request){
//        //简化if-else表达式（其实很多地方可以简化的，这里为了方便新手朋友可以看得顺畅点，我尽量不简化）
//        if (request.isRequestedSessionIdValid()){
//            return new Result("SUCCESS LOGIN",ResultEnum.OK);
//        }
//        return new Result("SUCCESS LOGIN",ResultEnum.NOT_FOUND);
//
//    }
    /**
     * 注销登录
     * @param session
     * @return
     */
    @GetMapping("/logout")
    public Result logout(User user){

        return new Result("logout",ResultEnum.OK);
    }

    /*注册模块*/
    @PostMapping("/register")
    public Result register(User user, String checkPassword){
        if (!userService.checkUserInvalid(user,checkPassword)){
            return new Result(null,ResultEnum.NOT_ACCEPTABLE);/*格式错误*/
        }
        user.setUser_password(DES.getEncryptString(checkPassword));
        int i = userMapper.registerUser(user);
        System.out.println(user);
        if (i>0){
            /*将注册者信息存到redis中*/
            if (!userService.putUserInRedis(user)){
                System.out.println("添加失败！");
            }
            return new Result(user,ResultEnum.CREATED);
        }
        return new Result(user,ResultEnum.NOT_ACCEPTABLE);
    }
    /*用户查看我的详情模块*/
    @GetMapping("/detail")
    public Result getMyDetail(@RequestParam("user_id") Integer user_id, @RequestParam("user_name")String user_name){
        User user = userService.selectUserByUsername(user_name);
        if (user!=null){
            return new Result(user,ResultEnum.OK);
        }
        /*在redis中没有找到该用户,就到数据库中查找*/
        user = userMapper.selectUserByPrimaryKey(user_id);
        if (user == null){
            return new Result(null,ResultEnum.NOT_FOUND);
        }
        /*将数据库找到的信息，放到redis中*/
        if (!userService.putUserInRedis(user)){
            System.out.println("添加失败！");
        }
        return new Result(user,ResultEnum.CREATED);
    }
}
