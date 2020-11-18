package cn.Boys.library.service;

import cn.Boys.library.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.regex.Pattern;

@Service
public interface UserService {
    public Boolean checkPassword(String password, String check_password);

    public Boolean putUserInRedis(User user);

    public User selectUserByUsername(String username);

    public Boolean checkUserInvalid(User user, String checkPassword);
}
