package cn.Boys.library.service.impl;

import cn.Boys.library.entity.Books;
import cn.Boys.library.service.BooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class BooksServiceImpl implements BooksService {
    @Autowired
    RedisTemplate redisTemplate;
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
}
