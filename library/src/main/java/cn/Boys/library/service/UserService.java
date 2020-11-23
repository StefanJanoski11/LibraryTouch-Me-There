package cn.Boys.library.service;

import cn.Boys.library.dto.Result;
import cn.Boys.library.entity.User;

public interface UserService {
    public Boolean checkPassword(String password, String check_password);

    public Boolean putUserInRedis(User user);

    public User selectUserByUsername(String username);

    //模糊查询用户
    public Result quaryUserByName(String name);
    //删除用户
    public Result deleteUser(Integer userId);
    //查询所有存在用户
    public Result selectUserExist();

}
