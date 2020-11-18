package cn.Boys.library.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
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
    private Integer books_daily;
    private Integer books_weekly;
    private Integer books_monthly;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date books_createTime;
    private Integer books_publisherId;
    private Integer books_modifiedId;

    public Integer getBooks_id() {
        return books_id;
    }

    public void setBooks_id(Integer books_id) {
        this.books_id = books_id;
    }

    public Integer getBooks_type() {
        return books_type;
    }

    public void setBooks_type(Integer books_type) {
        this.books_type = books_type;
    }

    public String getBooks_name() {
        return books_name;
    }

    public void setBooks_name(String books_name) {
        this.books_name = books_name;
    }

    public String getBooks_author() {
        return books_author;
    }

    public void setBooks_author(String books_author) {
        this.books_author = books_author;
    }

    public Integer getBooks_last() {
        return books_last;
    }

    public void setBooks_last(Integer books_last) {
        this.books_last = books_last;
    }

    public Integer getBooks_state() {
        return books_state;
    }

    public void setBooks_state(Integer books_state) {
        this.books_state = books_state;
    }

    public Date getBooks_registerDate() {
        return books_registerDate;
    }

    public void setBooks_registerDate(Date books_registerDate) {
        this.books_registerDate = books_registerDate;
    }

    public String getBooks_pic() {
        return books_pic;
    }

    public void setBooks_pic(String books_pic) {
        this.books_pic = books_pic;
    }

    public Integer getBooks_daily() {
        return books_daily;
    }

    public void setBooks_daily(Integer books_daily) {
        this.books_daily = books_daily;
    }

    public Integer getBooks_weekly() {
        return books_weekly;
    }

    public void setBooks_weekly(Integer books_weekly) {
        this.books_weekly = books_weekly;
    }

    public Integer getBooks_monthly() {
        return books_monthly;
    }

    public void setBooks_monthly(Integer books_monthly) {
        this.books_monthly = books_monthly;
    }

    public Date getBooks_createTime() {
        return books_createTime;
    }

    public void setBooks_createTime(Date books_createTime) {
        this.books_createTime = books_createTime;
    }

    public Integer getBooks_publisherId() {
        return books_publisherId;
    }

    public void setBooks_publisherId(Integer books_publisherId) {
        this.books_publisherId = books_publisherId;
    }

    public Integer getBooks_modifiedId() {
        return books_modifiedId;
    }

    public void setBooks_modifiedId(Integer books_modifiedId) {
        this.books_modifiedId = books_modifiedId;
    }

    @Override
    public String toString() {
        return "Books{" +
                "books_id=" + books_id +
                ", books_type=" + books_type +
                ", books_name='" + books_name + '\'' +
                ", books_author='" + books_author + '\'' +
                ", books_last=" + books_last +
                ", books_state=" + books_state +
                ", books_registerDate=" + books_registerDate +
                ", books_pic='" + books_pic + '\'' +
                ", books_daily=" + books_daily +
                ", books_weekly=" + books_weekly +
                ", books_monthly=" + books_monthly +
                ", books_createTime=" + books_createTime +
                ", books_publisherId=" + books_publisherId +
                ", books_modifiedId=" + books_modifiedId +
                '}';
    }

    public Books() {
    }

    public Books(Integer books_id, Integer books_type, String books_name, String books_author, Integer books_last, Integer books_state, Date books_registerDate, String books_pic, Integer books_daily, Integer books_weekly, Integer books_monthly, Date books_createTime, Integer books_publisherId, Integer books_modifiedId) {
        this.books_id = books_id;
        this.books_type = books_type;
        this.books_name = books_name;
        this.books_author = books_author;
        this.books_last = books_last;
        this.books_state = books_state;
        this.books_registerDate = books_registerDate;
        this.books_pic = books_pic;
        this.books_daily = books_daily;
        this.books_weekly = books_weekly;
        this.books_monthly = books_monthly;
        this.books_createTime = books_createTime;
        this.books_publisherId = books_publisherId;
        this.books_modifiedId = books_modifiedId;
    }
}
