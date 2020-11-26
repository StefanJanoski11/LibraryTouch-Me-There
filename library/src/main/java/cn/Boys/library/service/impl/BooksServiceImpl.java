package cn.Boys.library.service.impl;

import cn.Boys.library.dto.BookDTO;
import cn.Boys.library.dto.BookStatusDTO;
import cn.Boys.library.dto.Result;
import cn.Boys.library.entity.Books;
import cn.Boys.library.entity.Books_info;
import cn.Boys.library.enums.BookStatusEnum;
import cn.Boys.library.enums.ResultEnum;
import cn.Boys.library.mapper.BooksMapper;
import cn.Boys.library.service.BooksService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class BooksServiceImpl implements BooksService {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Log4j.class);
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    BatchSetBooksServiceImpl batchSetService;
    @Autowired
    BooksMapper booksMapper;

    /*获取更新Redis中书的每日点击访问量*/
    public List<Books> updateDailyAccessInRedis() {
        try {
            /*获取对象和点击数*/
            Set<ZSetOperations.TypedTuple<Books>> booksDailyAccess = redisTemplate.opsForZSet().rangeWithScores("booksDailyAccess", 0, -1);
            List<Books> booksList = new ArrayList<>();
            for (ZSetOperations.TypedTuple<Books> tuple:booksDailyAccess) {
                /*添加到集合中，再将数据清空*/
                Books books = tuple.getValue();
                books.setBooks_daily(tuple.getScore().intValue());
                booksList.add(books);
            }
            booksList.forEach(books -> redisTemplate.opsForZSet().add("booksDailyAccess",books,0));
            return booksList;
        } catch (Exception e) {
            log.error(e.toString());
            return null;
        }
    }

    /*获取更新Redis中图书的每周点击访问量*/
    public List<Books> updateWeeklyAccessInRedis() {
        try {
            /*获取对象和点击数*/
            Set<ZSetOperations.TypedTuple<Books>> booksWeeklyAccess = redisTemplate.opsForZSet().rangeWithScores("booksWeeklyAccess", 0, -1);
            List<Books> booksList = new ArrayList<>();
            for (ZSetOperations.TypedTuple<Books> tuple:booksWeeklyAccess) {
                /*添加到集合中，再将数据清空*/
                Books books = tuple.getValue();
                books.setBooks_weekly(tuple.getScore().intValue());
                booksList.add(books);
            }
            booksList.forEach(books -> redisTemplate.opsForZSet().add("booksWeeklyAccess",books,0));
            return booksList;
        } catch (Exception e) {
            log.error(e.toString());
            return null;
        }
    }

    /*获取更新Redis中图书的每月点击访问量*/
    public List<Books> updateMonthlyAccessInRedis() {
        try {
            /*获取对象和点击数*/
            Set<ZSetOperations.TypedTuple<Books>> booksMonthlyAccess = redisTemplate.opsForZSet().rangeWithScores("booksMonthlyAccess", 0, -1);
            List<Books> booksList = new ArrayList<>();
            for (ZSetOperations.TypedTuple<Books> tuple:booksMonthlyAccess) {
                /*添加到集合中，再将数据清空*/
                Books books = tuple.getValue();
                books.setBooks_monthly(tuple.getScore().intValue());
                booksList.add(books);
            }
            booksList.forEach(books -> redisTemplate.opsForZSet().add("booksMonthlyAccess",books,0));
            return booksList;
        } catch (Exception e) {
            log.error(e.toString());
            return null;
        }
    }

    /*获取日点击量多的5个*/
    public Set<Books> getDailyHotByRedis() {
        try {
            Set<Books> booksSet = redisTemplate.opsForZSet().reverseRangeWithScores("booksDailyAccess", 0, 4);
            if (booksSet.size() != 0) {
                return booksSet;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /*获取周点击量多的5个*/
    public Set<Books> getWeeklyHotByRedis() {
        try {
            Set<Books> booksSet = redisTemplate.opsForZSet().reverseRangeWithScores("booksWeeklyAccess", 0, 4);
            if (booksSet.size() != 0) {
                return booksSet;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /*获取月点击量多的5个*/
    public Set<Books> getMonthlyHotByRedis() {
        try {
            Set<Books> booksSet = redisTemplate.opsForZSet().reverseRangeWithScores("booksMonthlyAccess", 0, 4);
            if (booksSet.size() != 0) {
                return booksSet;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /*访问量递增*/
    public Boolean increaseAccessNumberInRedis(Books books) {
        try {
            redisTemplate.opsForZSet().incrementScore("booksDailyAccess", books, 1);
            redisTemplate.opsForZSet().incrementScore("booksWeeklyAccess", books, 1);
            redisTemplate.opsForZSet().incrementScore("booksMonthlyAccess", books, 1);
            log.info("修改图书访问量正常！");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /*将访问量更新到redis*/
    public Boolean putAccessNumberInRedis(Books books) {
        try {
            redisTemplate.opsForZSet().add("booksDailyAccess,", books, 0);
            redisTemplate.opsForZSet().add("booksWeeklyAccess", books, 0);
            redisTemplate.opsForZSet().add("booksMonthlyAccess", books, 0);
            log.info("zSet:" + books.getBooks_name() + "booksDailyAccess--" + 1);
            log.info("zSet:" + books.getBooks_name() + "booksWeeklyAccess--" + 1);
            log.info("zSet:" + books.getBooks_name() + "booksMonthlyAccess--" +  1);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /*将图书更新到redis*/
    public Boolean putBooksInRedis(Books books) {
        try {
            /*在redis中没找到对应的图书，说明可能过期了*/
            redisTemplate.opsForHash().put("books", books.getBooks_id(), books);
            redisTemplate.expire("books", 1, TimeUnit.HOURS);
            /*这里需要重新更新一下访问量*/
            if (!putAccessNumberInRedis(books)) {
                log.error("修改数据库访问量出现大问题！");
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /*在redis中找图书*/
    public Books selectBookById(Integer books_id) {
        if (redisTemplate.opsForHash().hasKey("books", books_id) && redisTemplate.opsForHash().get("books", books_id) != null) {/*包含图书编号hashKey*/
            /*不管是否下架，均可查询到此书*/
            log.info("redis中找到了");
            Books books = (Books) redisTemplate.opsForHash().get("books", books_id);
            /*更新一下访问量*/
            if (!increaseAccessNumberInRedis(books)) {
                log.error("修改图书访问量出现大问题！");
            }
            return books;
        }
        return null;/*在redis中没找到*/
    }

    //模糊查询
    @Override
    public Result quaryBooks(String name) {
        List<Books> books =booksMapper.quaryBook(name);
        if( books.size()==0){
            return  new Result(null, ResultEnum.NOT_FOUND);
        }
        return new Result(books,ResultEnum.OK);
    }

    //查询图书状态,到底是有的借还是没得借
    @Override
    public List<BookStatusDTO> BooksStatusList() {
        List<Books> list =  booksMapper.getAllBook();
        List<BookStatusDTO> DTOList = new ArrayList<>();
        int status = 0;
        for (Books book:list) {
            status = book.getBooks_last();
            if(status<0){
                DTOList.add(new BookStatusDTO(BookStatusEnum.NONE,book));
            }else if (status==0){
                DTOList.add(new BookStatusDTO(BookStatusEnum.NORETURN,book));
            }else DTOList.add(new BookStatusDTO(BookStatusEnum.HAVE,book));
        }
        return DTOList;
    }

    //根据图书类型查询
    @Override
    public Result getBooksByType(Books_info bookInfo) {
        log.info("传回来"+bookInfo);
        List<Books_info> booksList = booksMapper.quaryBooksByType(bookInfo);
        if (booksList.size()==0){
            log.info("怎么走到印度来了？？");
            return new Result(booksList,ResultEnum.NOT_FOUND);
        }

        return new Result(booksList,ResultEnum.OK);
    }


    //更新图书类型
    @Override
    public Result editBooksInfo(Books_info books_info) {
        //先更新类型
        if (booksMapper.editBooksInfo(books_info) == 0){
            return new Result(books_info,ResultEnum.NOT_ACCEPTABLE);
        }
        return  new Result(books_info,ResultEnum.OK);

    }

    //图书上架下架
    @Override
    public Result publishBook(Integer bookId,Integer status) {
        int res = booksMapper.setBookStatus(bookId,status);
        if(res>0) {
            return new Result(res, ResultEnum.OK);
        }else return new Result(res, ResultEnum.SERVER_ERROR);
    }

    //根据图书id使图书加一
    @Override
    public Integer BooksLastRaise(Integer bookId) {
        return booksMapper.BooksLastRaise(bookId);
    }

    @Override
    public Integer BooksLastDown(Integer bookId) {
        return booksMapper.BooksLastDown(bookId);
    }

    @Override
    public Result getAllBookInfo(Integer booksId) {
        BookDTO bookDTO =  booksMapper.getAllBookInfo(booksId);
        if(bookDTO!=null){
            return new Result(bookDTO,ResultEnum.OK);
        }
        return new Result(null,ResultEnum.NOT_ACCEPTABLE);
    }

}
