package cn.Boys.library.mapper;


import cn.Boys.library.entity.User;
import org.apache.ibatis.annotations.*;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface UserMapper {
    /*通过用户名和密码查询用户*/
    @Select("select * from user where user_name=#{user_name} and user_password=#{user_password}")
    public User selectUserByUsernameAndPassword(String user_name, String user_password);

    /*修改密码*/
    @Update("update user set user_password=#{user_password} where user_id=#{user_id}")
    public int changePassword(String user_password,Integer user_id);

    /*注册用户*/
    @Options(useGeneratedKeys = true,keyProperty = "user_id")
    @Insert("insert into user(user_identity_id,user_name,user_password,user_sex,user_phone,user_birthday,user_address," +
            "user_self_desc,user_sincerity,user_exist_state,user_create_admin,user_alter_admin)" +
            "values(#{user_identity_id},#{user_name},#{user_password},#{user_sex},#{user_phone},#{user_birthday},#{user_address}" +
            ",#{user_self_desc},#{user_sincerity},#{user_exist_state},#{user_create_admin},#{user_alter_admin})")
    public int registerUser(User user);

    /*通过主键查找用户*/
    @Select("select user_id,user_identity_id,user_name,user_sex,user_phone,user_birthday,user_address,user_self_desc,user_sincerity," +
            "user_exist_state,user_create_date,user_create_admin,user_alter_date,user_alter_admin,user_password from user where user_id=#{user_id}")
    public User selectUserByPrimaryKey(Integer user_id);

}
