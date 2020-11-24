package cn.Boys.library.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class Books_info {
    private Integer info_id;
    private String info_country;
    private Integer info_length;
    private String info_theme;
    private String info_type;
    private Integer info_state=1;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date info_createTime=new Date();
    private Integer info_publisher;
    private Integer info_modifiedId;

    public Books_info() {
    }

    public Books_info(Integer info_id, String info_country, Integer info_length, String info_theme, String info_type, Integer info_state, Date info_createTime, Integer info_publisher, Integer info_modifiedId) {
        this.info_id = info_id;
        this.info_country = info_country;
        this.info_length = info_length;
        this.info_theme = info_theme;
        this.info_type = info_type;
        this.info_state = info_state;
        this.info_createTime = info_createTime;
        this.info_publisher = info_publisher;
        this.info_modifiedId = info_modifiedId;
    }

    public Integer getInfo_id() {
        return info_id;
    }

    public void setInfo_id(Integer info_id) {
        this.info_id = info_id;
    }

    public String getInfo_country() {
        return info_country;
    }

    public void setInfo_country(String info_country) {
        this.info_country = info_country;
    }

    public Integer getInfo_length() {
        return info_length;
    }

    public void setInfo_length(Integer info_length) {
        this.info_length = info_length;
    }

    public String getInfo_theme() {
        return info_theme;
    }

    public void setInfo_theme(String info_theme) {
        this.info_theme = info_theme;
    }

    public String getInfo_type() {
        return info_type;
    }

    public void setInfo_type(String info_type) {
        this.info_type = info_type;
    }

    public Integer getInfo_state() {
        return info_state;
    }

    public void setInfo_state(Integer info_state) {
        this.info_state = info_state;
    }

    public Date getInfo_createTime() {
        return info_createTime;
    }

    public void setInfo_createTime(Date info_createTime) {
        this.info_createTime = info_createTime;
    }

    public Integer getInfo_publisher() {
        return info_publisher;
    }

    public void setInfo_publisher(Integer info_publisher) {
        this.info_publisher = info_publisher;
    }

    public Integer getInfo_modifiedId() {
        return info_modifiedId;
    }

    public void setInfo_modifiedId(Integer info_modifiedId) {
        this.info_modifiedId = info_modifiedId;
    }
}
