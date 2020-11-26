package cn.Boys.library.mapper;


import cn.Boys.library.entity.User;
import org.apache.ibatis.annotations.*;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface UserMapper {
    String allFields = " user_id,user_identity_id,user_name,user_password,user_email,user_sex,"+
            "user_phone,user_birthday,user_address,user_self_desc,user_sincerity," +
            "user_exist_state,user_create_date,user_create_admin,user_alter_date,user_alter_admin ";

    /*查询所有用户*/
    @Select("select " + allFields + "from user")
    public List<User> selectAllUser();

    /*查询状态存在的所有用户*/
    @Select("select " + allFields + "from user where user_exist_state=1")
    public List<User> selectAllExistUser();

    /*通过用户名和密码查询用户*/
    @Select("select " + allFields +
            "from user where user_name=#{user_name} and user_password=#{user_password} and user_exist_state=1")
    public User selectUserByUsernameAndPassword(String user_name, String user_password);

    /*根据用户名查找用户*/
    @Select("select " + allFields +
            "from user where user_name=#{user_name} and user_exist_state=1")
    public User selectUserByUsername(String user_name);

    /*根据手机查找用户*/
    @Select("select " +allFields+
            "from user where user_phone=#{user_phone} and user_exist_state=1")
    public User selectUserByPhone(String user_phone);

    /*单独修改密码功能*/
    @Update("update user set user_password=#{user_password} where user_id=#{user_id}")
    public int changePassword(String user_password,Integer user_id);

    /*注册用户*/
    @Options(useGeneratedKeys = true,keyProperty = "user_id")
    @Insert("insert into user(user_identity_id,user_name,user_password,user_email,user_sex,user_phone,user_birthday," +
            "user_address,user_self_desc,user_sincerity,user_exist_state,user_create_admin,user_alter_admin)" +
            "values(#{user_identity_id},#{user_name},#{user_password},#{user_email},#{user_sex},#{user_phone},#{user_birthday}," +
            "#{user_address},#{user_self_desc},#{user_sincerity},#{user_exist_state}," +
            "#{user_create_admin},#{user_alter_admin})")
    public int registerUser(User user);

    /*通过主键查找用户*/
    @Select("select " + allFields+
            " from user where user_id=#{user_id} and user_exist_state=1")
    public User selectUserByPrimaryKey(Integer user_id);

    @Update("<script>update user" +
            "<trim prefix='set' suffixOverrides=','>" +
            "<if test='user_alter_admin != null'>user_alter_admin=#{user_alter_admin},</if> " +
            "<if test='user_email != null'>user_email=#{user_email},</if> " +
            "<if test='user_password != null'>user_password=#{user_password},</if> " +
            "<if test='user_sex != null'>user_sex=#{user_sex},</if> " +
            "<if test='user_phone != null'>user_phone=#{user_phone},</if> " +
            "<if test='user_birthday != null'>user_birthday=#{user_birthday},</if> " +
            "<if test='user_address != null'>user_address=#{user_address},</if> " +
            "<if test='user_self_desc != null'>user_self_desc=#{user_self_desc},</if> " +
            "</trim>" +
            "<where><choose>" +
            "<when test='user_id != null'>user_id=#{user_id}</when>" +
            "<otherwise> 1=2 </otherwise>" +
            "</choose></where></script>")
    public int alterDetail(User user);


    //模糊查询用户
    @Select("select user_id,user_identity_id,user_name,user_sex,user_phone,user_birthday,user_address,user_self_desc,user_sincerity," +
            "user_exist_state,user_create_date,user_create_admin,user_alter_date,user_alter_admin,user_password from user " +
            "where user_name like CONCAT('%',#{user_name},'%') and user_exist_state=1")
    public List<User> queryUserByName(String user_name);

    //注销用户
    @Update("Update user set user_exist_state = 0 where user_id =#{userId}")
    public Integer deleteUser(Integer userId);


    /*通过查找存在用户*/
    @Select("select user_id,user_identity_id,user_name,user_sex,user_phone,user_birthday,user_address,user_self_desc,user_sincerity," +
            "user_exist_state,user_create_date,user_create_admin,user_alter_date,user_alter_admin,user_password from user where user_exist_state=1")
    public List<User> selectUserExist();
}
