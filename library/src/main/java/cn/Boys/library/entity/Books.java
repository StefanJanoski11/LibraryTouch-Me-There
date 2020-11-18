package cn.Boys.library.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.relational.core.sql.In;
import org.springframework.format.annotation.DateTimeFormat;

import javax.xml.crypto.Data;
import java.util.Date;

public class Books {
    private Integer books_id;
    private Integer books_type;
    private String books_name;
    private String books_author;
    private Integer books_last;
    private Integer books_state;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date books_registerDate;
    private String books_pic;
    private String books_publisher;
    private Integer books_daily;
    private Integer books_weekly;
    private Integer books_monthly;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date books_createTime;
    private Integer books_publisherId;
    private Integer books_modifiedId;


}
