package cn.Boys.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.regex.Pattern;

@Service
public class UserService {
    @Autowired
    RedisTemplate redisTemplate;

    public Boolean selectUserByUsernameAndPassword(String username, String password) {
        if (redisTemplate.opsForHash().hasKey("user", username) && redisTemplate.opsForHash().get("user", username) != null) {/*包含这个用户名hashKey*/
            System.out.println("redis中找到了");
            User user = (User) redisTemplate.opsForHash().get("user", username);
            if (user.getUser_password().equals(password)) {
                return true;/*在redis中找到了，并且密码正确*/
            }
            return false;/*密码错误*/
        }
        return false;/*在redis中没找到*/
    }

    public Boolean putUserInRedis(User user) {
        System.out.println(user);
        try {
            redisTemplate.opsForHash().put("user", user.getUser_name(), user);
            System.out.println("hash值为user,key为" + user.getUser_name() + ",value为" + user);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public User selectUserByUsername(String username) {
        if (redisTemplate.opsForHash().hasKey("user", username) && redisTemplate.opsForHash().get("user", username) != null) {/*包含这个用户名hashKey*/
            System.out.println("redis中找到了");
            return (User) redisTemplate.opsForHash().get("user", username);
        }
        return null;/*在redis中没找到*/
    }

    public Boolean checkUserInvalid(User user,String checkPassword){
        if (user!=null){
            /*用户名判断，允许出现20个字符长度，并且不能为空*/
            if (user.getUser_name().length()==0 || user.getUser_name().length()>20 ) return false;/*用户名不匹配则报错*/
            /*确认密码与原密码相同，并且不能为空*/
            if (!StringUtils.isEmpty(user.getUser_password()) && !user.getUser_password().equals(checkPassword)) return false;
            /*性别只能出现0或1*/
            if (user.getUser_sex()!=1 && user.getUser_sex()!=0) return false;
            /*手机号码*/
            Pattern pattern = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$");
            if (!pattern.matcher(user.getUser_phone()).find()) return false;
            /*判断日期是否是将来日期*/
            if (user.getUser_birthday().after(new Date())) return false;
            /*限制居住地址在100字符长度*/
            if (!StringUtils.isEmpty(user.getUser_address()) && user.getUser_address().length()>100) return false;
            /*个人描述限制100字符长度*/
            if (!StringUtils.isEmpty(user.getUser_self_desc()) && user.getUser_self_desc().length()>100) return false;
            /*通过上述判断后，则表示合格*/
            user.setUser_identity_id(1);/*普通用户*/
            user.setUser_sincerity(0);/*无不良记录*/
            user.setUser_exist_state(1);/*状态存在*/
            user.setUser_create_admin(0);/*0表示注册*/
            user.setUser_alter_admin(0);
            return true;
        }else {
            return false;
        }
    }


}
