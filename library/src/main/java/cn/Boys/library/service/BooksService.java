package cn.Boys.library.service;

import cn.Boys.library.dto.BookStatusDTO;
import cn.Boys.library.dto.Result;

import cn.Boys.library.entity.Books;
import cn.Boys.library.entity.Books_info;

import java.util.List;

public interface BooksService {

    public Boolean putBooksInRedis(Books books);
    //模糊查询
    public Result quaryBooks(String name);
    //点击图书库后查询图书状态
    public List<BookStatusDTO> BooksStatusList();
    //根据类型查询
    public Result getBooksByType(Books_info bookInfo);
    //修改图书类型
    public Result editBooksInfo(Books_info books_info);
}
