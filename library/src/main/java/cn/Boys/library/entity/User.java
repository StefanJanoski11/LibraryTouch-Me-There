package cn.Boys.library.entity;

import java.util.Date;

public class User {
    private Integer user_id;
    private Integer user_identity_id;
    private String user_name;
    private String user_password;
    private Integer user_sex;
    private String user_phone;
    private Date user_birthday;
    private String user_address;
    private String user_self_desc;
    private Integer user_sincerity;
    private Integer user_exist_state;
    private Date user_create_date;
    private Integer user_create_admin;
    private Date user_alter_date;
    private Integer user_alter_admin;

    public User() {
    }

    public User(Integer user_id, String user_name, String user_password, Integer user_sex, String user_phone, Date user_birthday, String user_address, String user_self_desc, Integer user_sincerity, Integer user_exist_state, Date user_create_date, Integer user_create_admin, Date user_alter_date, Integer user_alter_admin) {
        this.user_id = user_id;
        this.user_identity_id = 1;
        this.user_name = user_name;
        this.user_password = user_password;
        this.user_sex = user_sex;
        this.user_phone = user_phone;
        this.user_birthday = user_birthday;
        this.user_address = user_address;
        this.user_self_desc = user_self_desc;
        this.user_sincerity = 0;
        this.user_exist_state = 1;
        this.user_create_date = user_create_date;
        this.user_create_admin = 0;
        this.user_alter_date = user_alter_date;
        this.user_alter_admin = 0;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id=" + user_id +
                ", user_identity_id=" + user_identity_id +
                ", user_name='" + user_name + '\'' +
                ", user_password='" + user_password + '\'' +
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
