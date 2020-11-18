package cn.Boys.library.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class Record {
    private Integer user_id;
    private String user_name;
    private Integer book_id;
    private String book_name;
    //是否还书
    private Integer book_return_state;
    //还书日期
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date book_return_date;

    //借出日期
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date book_lend_date;

    //预期还书时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date book_scheduled;

    public Record(){

    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public Record(Integer user_id, String user_name, Integer book_id, String book_name, Integer book_return_state, Date book_return_date, Date book_lend_date, Date book_scheduled) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.book_id = book_id;
        this.book_name = book_name;
        this.book_return_state = book_return_state;
        this.book_return_date = book_return_date;
        this.book_lend_date = book_lend_date;
        this.book_scheduled = book_scheduled;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getBook_id() {
        return book_id;
    }

    public void setBook_id(Integer book_id) {
        this.book_id = book_id;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public Integer getBook_return_state() {
        return book_return_state;
    }

    public void setBook_return_state(Integer book_return_state) {
        this.book_return_state = book_return_state;
    }

    public Date getBook_return_date() {
        return book_return_date;
    }

    public void setBook_return_date(Date book_return_date) {
        this.book_return_date = book_return_date;
    }

    public Date getBook_lend_date() {
        return book_lend_date;
    }

    public void setBook_lend_date(Date book_lend_date) {
        this.book_lend_date = book_lend_date;
    }

    public Date getBook_scheduled() {
        return book_scheduled;
    }

    public void setBook_scheduled(Date book_scheduled) {
        this.book_scheduled = book_scheduled;
    }

    @Override
    public String toString() {
        return "Record{" +
                "user_id=" + user_id +
                ", user_name='" + user_name + '\'' +
                ", book_id=" + book_id +
                ", book_name='" + book_name + '\'' +
                ", book_return_state=" + book_return_state +
                ", book_return_date=" + book_return_date +
                ", book_lend_date=" + book_lend_date +
                ", book_scheduled=" + book_scheduled +
                '}';
    }
}
