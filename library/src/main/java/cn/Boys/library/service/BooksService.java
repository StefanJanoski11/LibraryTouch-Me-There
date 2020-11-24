package cn.Boys.library.service;

import cn.Boys.library.dto.BookStatusDTO;
import cn.Boys.library.dto.Result;
import cn.Boys.library.entity.Books;
import cn.Boys.library.entity.Books_info;

import java.util.List;

public interface BooksService {

    public Boolean putBooksInRedis(Books books);

    public Books selectBookById(Integer books_id);

    public Boolean putAccessNumberInRedis(Books books);

    public Boolean increaseAccessNumberInRedis(Books books);

    /*获取更新Redis中书的每日点击访问量*/
    public List<Books> updateDailyAccessInRedis();

    /*获取更新Redis中图书的每周点击访问量*/
    public List<Books> updateWeeklyAccessInRedis();

    /*获取更新Redis中图书的每月点击访问量*/
    public List<Books> updateMonthlyAccessInRedis();

    //模糊查询
    public Result quaryBooks(String name);
    //点击图书库后查询图书状态
    public List<BookStatusDTO> BooksStatusList();
    //根据类型查询
    public Result getBooksByType(Books_info bookInfo);
    //修改图书类型
    public Result editBooksInfo(Books_info books_info);
    //根据ID上架或下架书籍
    public Result publishBook(Integer bookId,Integer status);
    //根据ID为剩余数量加一
    public Integer BooksLastRaise(Integer bookId);
    public Integer BooksLastDown(Integer bookId);
    //真，所有信息
    public Result getAllBookInfo(Integer booksId);


}
