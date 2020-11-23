package cn.Boys.library.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class Notice {
    private Integer notice_id;
    private String notice_text;
    private Integer notice_userid;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date notice_post;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date notice_update;

    public Integer getNotice_id() {
        return notice_id;
    }

    public void setNotice_id(Integer notice_id) {
        this.notice_id = notice_id;
    }

    public String getNotice_text() {
        return notice_text;
    }

    public void setNotice_text(String notice_text) {
        this.notice_text = notice_text;
    }

    public Integer getNotice_userid() {
        return notice_userid;
    }

    public void setNotice_userid(Integer notice_userid) {
        this.notice_userid = notice_userid;
    }

    public Date getNotice_post() {
        return notice_post;
    }

    public void setNotice_post(Date notice_post) {
        this.notice_post = notice_post;
    }

    public Date getNotice_update() {
        return notice_update;
    }

    public void setNotice_update(Date notice_update) {
        this.notice_update = notice_update;
    }

    @Override
    public String toString() {
        return "Notice{" +
                "notice_id=" + notice_id +
                ", notice_text='" + notice_text + '\'' +
                ", notice_userid=" + notice_userid +
                ", notice_post=" + notice_post +
                ", notice_update=" + notice_update +
                '}';
    }
}
