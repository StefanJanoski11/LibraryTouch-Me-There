package cn.Boys.library.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class BookDTO {
    private Integer books_id;
    private Integer books_type;
    private String books_name;
    private String books_author;
    private Integer books_last;
    private Integer books_state;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date books_create_date;
    private String books_pic;
    private String info_country;
    private Integer info_length;
    private String info_theme;
    private String info_type;
    private Integer info_state=1;

    @Override
    public String toString() {
        return "BookDTO{" +
                "books_id=" + books_id +
                ", books_type=" + books_type +
                ", books_name='" + books_name + '\'' +
                ", books_author='" + books_author + '\'' +
                ", books_last=" + books_last +
                ", books_state=" + books_state +
                ", books_create_date=" + books_create_date +
                ", books_pic='" + books_pic + '\'' +
                ", info_country='" + info_country + '\'' +
                ", info_length=" + info_length +
                ", info_theme='" + info_theme + '\'' +
                ", info_type='" + info_type + '\'' +
                ", info_state=" + info_state +
                '}';
    }

    public BookDTO() {
    }

    public BookDTO(Integer books_id, Integer books_type, String books_name, String books_author, Integer books_last, Integer books_state, Date books_create_date, String books_pic, String info_country, Integer info_length, String info_theme, String info_type, Integer info_state) {
        this.books_id = books_id;
        this.books_type = books_type;
        this.books_name = books_name;
        this.books_author = books_author;
        this.books_last = books_last;
        this.books_state = books_state;
        this.books_create_date = books_create_date;
        this.books_pic = books_pic;
        this.info_country = info_country;
        this.info_length = info_length;
        this.info_theme = info_theme;
        this.info_type = info_type;
        this.info_state = info_state;
    }

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

    public Date getBooks_create_date() {
        return books_create_date;
    }

    public void setBooks_create_date(Date books_create_date) {
        this.books_create_date = books_create_date;
    }

    public String getBooks_pic() {
        return books_pic;
    }

    public void setBooks_pic(String books_pic) {
        this.books_pic = books_pic;
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
}
