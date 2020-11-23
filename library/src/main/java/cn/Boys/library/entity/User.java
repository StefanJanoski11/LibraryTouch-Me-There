package cn.Boys.library.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.util.Date;

public class User {
    private Integer user_id;
    private Integer user_identity_id=0;/*1代表管理员，0代表用户*/
    @NotNull(message = "用户名不能为空")
    @NotEmpty(message = "用户名不能为空串")
    @Length(max = 20,min = 3,message = "用户名长度必须在3至20之间")
    private String user_name;
    @NotNull(message = "密码不能为空")
    @NotEmpty(message = "密码不能为空串")
    @Pattern(regexp = "(?=.*\\d)(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).{8,30}",message = "密码必须由数字、字母、特殊字符组合,长度必须在8-30之间")
    private String user_password;
    @NotNull(message = "邮箱不能为空")
    @NotEmpty(message = "邮箱不能为空串")
    @Email(message = "邮箱格式不正确")
    private String user_email;
    @NotNull(message = "性别不能为空")/*具体数值需要判断一下*/
    private Integer user_sex;
    @NotNull(message = "手机号码不能为空")
    @NotEmpty(message = "手机号码不能为空串")
    @Pattern(regexp = "^((0\\d{2,3}-\\d{7,8})|(1[34578]\\d{9}))$",message = "目前只支持中国大陆的手机号码")
    private String user_phone;
    @NotNull(message = "出生日期不能为空")
    @Past(message = "请输入一个过去时间")
    private Date user_birthday;
    @NotNull(message = "地址栏不能为空")
    @NotEmpty(message = "地址栏不能为空串")
    private String user_address;
    private String user_self_desc;
    private Integer user_sincerity=0;/*0代表无不良记录用，负数代表不良用户*/
    private Integer user_exist_state=1;/*1代表存在*/
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date user_create_date=new Date();
    private Integer user_create_admin=0;/*0代表注册*/
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date user_alter_date=new Date();
    private Integer user_alter_admin=0;/*0代表注册*/

    public User() {
    }

    public User(Integer user_id, Integer user_identity_id, String user_name, String user_password, String user_email, Integer user_sex, String user_phone, Date user_birthday, String user_address, String user_self_desc, Integer user_sincerity, Integer user_exist_state, Date user_create_date, Integer user_create_admin, Date user_alter_date, Integer user_alter_admin) {
        this.user_id = user_id;
        this.user_identity_id = user_identity_id;
        this.user_name = user_name;
        this.user_password = user_password;
        this.user_email = user_email;
        this.user_sex = user_sex;
        this.user_phone = user_phone;
        this.user_birthday = user_birthday;
        this.user_address = user_address;
        this.user_self_desc = user_self_desc;
        this.user_sincerity = user_sincerity;
        this.user_exist_state = user_exist_state;
        this.user_create_date = user_create_date;
        this.user_create_admin = user_create_admin;
        this.user_alter_date = user_alter_date;
        this.user_alter_admin = user_alter_admin;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id=" + user_id +
                ", user_identity_id=" + user_identity_id +
                ", user_name='" + user_name + '\'' +
                ", user_password='" + user_password + '\'' +
                ", user_email='" + user_email + '\'' +
                ", user_sex=" + user_sex +
                ", user_phone='" + user_phone + '\'' +
                ", user_birthday=" + user_birthday +
                ", user_address='" + user_address + '\'' +
                ", user_self_desc='" + user_self_desc + '\'' +
                ", user_sincerity=" + user_sincerity +
                ", user_exist_state=" + user_exist_state +
                ", user_create_date=" + user_create_date +
                ", user_create_admin=" + user_create_admin +
                ", user_alter_date=" + user_alter_date +
                ", user_alter_admin=" + user_alter_admin +
                '}';
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getUser_identity_id() {
        return user_identity_id;
    }

    public void setUser_identity_id(Integer user_identity_id) {
        this.user_identity_id = user_identity_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public Integer getUser_sex() {
        return user_sex;
    }

    public void setUser_sex(Integer user_sex) {
        this.user_sex = user_sex;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public Date getUser_birthday() {
        return user_birthday;
    }

    public void setUser_birthday(Date user_birthday) {
        this.user_birthday = user_birthday;
    }

    public String getUser_address() {
        return user_address;
    }

    public void setUser_address(String user_address) {
        this.user_address = user_address;
    }

    public String getUser_self_desc() {
        return user_self_desc;
    }

    public void setUser_self_desc(String user_self_desc) {
        this.user_self_desc = user_self_desc;
    }

    public Integer getUser_sincerity() {
        return user_sincerity;
    }

    public void setUser_sincerity(Integer user_sincerity) {
        this.user_sincerity = user_sincerity;
    }

    public Integer getUser_exist_state() {
        return user_exist_state;
    }

    public void setUser_exist_state(Integer user_exist_state) {
        this.user_exist_state = user_exist_state;
    }

    public Date getUser_create_date() {
        return user_create_date;
    }

    public void setUser_create_date(Date user_create_date) {
        this.user_create_date = user_create_date;
    }

    public Integer getUser_create_admin() {
        return user_create_admin;
    }

    public void setUser_create_admin(Integer user_create_admin) {
        this.user_create_admin = user_create_admin;
    }

    public Date getUser_alter_date() {
        return user_alter_date;
    }

    public void setUser_alter_date(Date user_alter_date) {
        this.user_alter_date = user_alter_date;
    }

    public Integer getUser_alter_admin() {
        return user_alter_admin;
    }

    public void setUser_alter_admin(Integer user_alter_admin) {
        this.user_alter_admin = user_alter_admin;
    }

}
