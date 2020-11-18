package cn.Boys.library.service.impl;

import cn.Boys.library.entity.Books;
import cn.Boys.library.mapper.BooksMapper;
import cn.Boys.library.mapper.RecordMapper;
import cn.Boys.library.redis.JedisConfig;
import cn.Boys.library.service.BooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class BooksServiceImpl {
    private final String key = "BOOK";
    @Autowired
    BooksMapper booksMapper;
    @Autowired
    RedisTemplate redisTemplate;





}
