package cn.Boys.library.controller;

import cn.Boys.library.dto.Result;
import cn.Boys.library.entity.User;
import cn.Boys.library.enums.ResultEnum;
import cn.Boys.library.mapper.UserMapper;
import cn.Boys.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserMapper userMapper;
    @Autowired
    UserService userService;

    /*登录模块*/
    @GetMapping("/login")
    public Result login(@RequestParam("username") String username, @RequestParam("password") String password) {
        if (userService.selectUserByUsernameAndPassword(username,password)){
            System.out.println("用户名和密码均正确");
        }
        /*在redis没找到或者密码错误，均执行下列操作*/
        User user = userMapper.selectUserByUsernameAndPassword(username, password);
        if (user == null){
            return new Result(null,ResultEnum.ofState(400));
        }
        if (userService.putUserInRedis(user)){
            return new Result(user,ResultEnum.ofState(201));
        }
        return new Result(null,ResultEnum.ofState(500));
    }
    /*注册模块*/
    @PostMapping("/register")
    public Result register(User user, String checkPassword){
        if (!userService.checkUserInvalid(user,checkPassword)){
            return new Result(null,ResultEnum.ofState(406));/*格式错误*/
        }
        int i = userMapper.registerUser(user);
        System.out.println(user);
        if (i>0){
            /*将注册者信息存到redis中*/
            if (!userService.putUserInRedis(user)){
                System.out.println("添加失败！");
            }
            return new Result(user,ResultEnum.ofState(201));
        }
        return new Result(user,ResultEnum.ofState(406));
    }
    /*用户查看我的详情模块*/
    @GetMapping("/detail")
    public Result getMyDetail(@RequestParam("user_id") Integer user_id, @RequestParam("user_name")String user_name){
        User user = userService.selectUserByUsername(user_name);
        if (user!=null){
            return new Result(user,ResultEnum.ofState(200));
        }
        /*在redis中没有找到该用户,就到数据库中查找*/
        user = userMapper.selectUserByPrimaryKey(user_id);
        if (user == null){
            return new Result(null,ResultEnum.ofState(404));
        }
        /*将数据库找到的信息，放到redis中*/
        if (!userService.putUserInRedis(user)){
            System.out.println("添加失败！");
        }
        return new Result(user,ResultEnum.ofState(201));
    }
}
