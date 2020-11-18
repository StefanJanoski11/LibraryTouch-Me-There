package cn.Boys.library.service;

import cn.Boys.library.entity.User;

public interface UserService {
    public Boolean checkPassword(String password, String check_password);

    public Boolean putUserInRedis(User user);

    public User selectUserByUsername(String username);

    public Boolean checkUserInvalid(User user, String checkPassword);
}
