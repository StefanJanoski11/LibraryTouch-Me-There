package cn.Boys.library.service;

import cn.Boys.library.entity.Books;

public interface BooksService {

    public Boolean putBooksInRedis(Books books);
    //模糊查询
    public Books quaryBook(String name);


}
