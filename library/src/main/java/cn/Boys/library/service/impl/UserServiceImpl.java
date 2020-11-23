package cn.Boys.library.service.impl;

import cn.Boys.library.dto.Result;
import cn.Boys.library.entity.User;
import cn.Boys.library.enums.ResultEnum;
import cn.Boys.library.mapper.UserMapper;
import cn.Boys.library.service.UserService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Log4j.class);
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    UserMapper userMapper;

    public Boolean checkPassword(String password, String check_password) {
        if (password.compareTo(check_password) == 0) {
            return true;/*密码正确*/
        }
        return false;
    }

    public Boolean putUserInRedis(User user) {
        try {
            redisTemplate.opsForHash().put("user", user.getUser_name(), user);
            redisTemplate.expire("user",30, TimeUnit.MINUTES);
            log.info("hash值为user,key为" + user.getUser_name() + ",value为" + user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public User selectUserByUsername(String username) {
        if (redisTemplate.opsForHash().hasKey("user", username) && redisTemplate.opsForHash().get("user", username) != null) {/*包含这个用户名hashKey*/
            User user = (User) redisTemplate.opsForHash().get("user", username);
            log.info(user.toString());
            if (user.getUser_exist_state() == 1){
                log.info("redis中找到了");
                return user;
            }
            return null;
        }
        return null;/*在redis中没找到*/
    }

    //模糊查询
    @Override
    public Result quaryUserByName(String name) {
        List<User> list = userMapper.queryUserByName(name);
        if( list.size()==0){
            return  new Result(null, ResultEnum.NOT_FOUND);
        }
        return new Result(list,ResultEnum.OK);
    }

    @Override
    public Result deleteUser(Integer userId) {
        int res = userMapper.deleteUser(userId);
        if(res==0){
            return new Result(res,ResultEnum.NOT_ACCEPTABLE);
        }
        return new Result(res, ResultEnum.OK);
    }

    @Override
    public Result selectUserExist() {
        List<User> list = userMapper.selectUserExist();
        if( list.size()==0){
            return  new Result(null, ResultEnum.NOT_FOUND);
        }
        return new Result(list,ResultEnum.OK);
    }

}
