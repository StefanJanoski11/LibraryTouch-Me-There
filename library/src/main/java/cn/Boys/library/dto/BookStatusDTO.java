package cn.Boys.library.dto;

import cn.Boys.library.enums.BookStatusEnum;

import java.awt.print.Book;

public class BookStatusDTO {
    //状态
    private Integer state;
    //状态信息
    private String info;
    //书的对象
    private Book book;

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public BookStatusDTO() {
    }

    public BookStatusDTO(BookStatusEnum enums, Book book) {
        this.state = enums.getStatus();
        this.info = enums.getInfo();
        this.book = book;
    }
}
