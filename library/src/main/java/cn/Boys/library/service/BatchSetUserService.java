package cn.Boys.library.service;

import cn.Boys.library.entity.User;

import java.util.List;

public interface BatchSetUserService {
    public void batchSetUserInRedis(List<User> list);
}
