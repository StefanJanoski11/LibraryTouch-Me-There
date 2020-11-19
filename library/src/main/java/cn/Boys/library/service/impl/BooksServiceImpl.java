package cn.Boys.library.service.impl;

import cn.Boys.library.dto.BookStatusDTO;
import cn.Boys.library.dto.Result;
import cn.Boys.library.entity.Books_info;
import cn.Boys.library.entity.Books;
import cn.Boys.library.enums.BookStatusEnum;
import cn.Boys.library.enums.ResultEnum;
import cn.Boys.library.mapper.BooksMapper;
import cn.Boys.library.service.BooksService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BooksServiceImpl implements BooksService {
    @Autowired
    BooksMapper booksMapper;
    @Autowired
    RedisTemplate redisTemplate;
    private final Logger logger = LoggerFactory.getLogger(BooksServiceImpl.class);
    public Boolean putBooksInRedis(Books books) {
        try {
            redisTemplate.opsForHash().put("books", books.getBooks_id(), books);
            System.out.println("hash值为user,key为" + books.getBooks_id() + ",value为" + books);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //模糊查询
    @Override
    public Result quaryBooks(String name) {
        List<Books> books =booksMapper.quaryBook(name);
        if( books.size()==0){
            return  new Result(null,ResultEnum.NOT_FOUND);
        }
        return new Result(books,ResultEnum.OK);
    }
    //查询图书状态
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
                DTOList.add(new BookStatusDTO(BookStatusEnum.HAVE,book));
            }else DTOList.add(new BookStatusDTO(BookStatusEnum.NORETURN,book));
        }
        return DTOList;
    }

    //根据图书类型查询
    @Override
    public Result getBooksByType(Books_info bookInfo) {

        List<Books_info> booksList = booksMapper.quaryBooksByType(bookInfo);
        if (booksList.size()==0){
            logger.info("怎么走到印度来了？？");
            return new Result(booksList,ResultEnum.NOT_FOUND);
        }

        return new Result(booksList,ResultEnum.OK);
    }


    //更新图书类型
    @Override
    public Result editBooksInfo(Books_info books_info) {
        //先更新类型
        if (booksMapper.editBooksInfo(books_info) == null){
            return new Result(books_info,ResultEnum.NOT_ACCEPTABLE);
        }
        Integer last = books_info.getInfo_state();
        Integer type = books_info.getInfo_type();

        if (last<0){
            return new Result(books_info,ResultEnum.NOT_ACCEPTABLE);
        }

        if(booksMapper.editBooksLast(type,last) !=1 ){
            return new Result(books_info,ResultEnum.SERVER_ERROR);
        }
        return new Result(books_info,ResultEnum.OK);
    }


}
