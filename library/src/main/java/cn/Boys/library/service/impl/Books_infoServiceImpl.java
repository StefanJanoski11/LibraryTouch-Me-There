package cn.Boys.library.service.impl;

import cn.Boys.library.entity.Books_info;
import cn.Boys.library.service.Books_infoService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class Books_infoServiceImpl implements Books_infoService {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Log4j.class);
    @Autowired
    RedisTemplate redisTemplate;



    public Books_info selectBooks_infoById(Integer info_id) {
        if (redisTemplate.opsForHash().hasKey("books_info", info_id) && redisTemplate.opsForHash().get("books_info", info_id) != null) {/*包含图书编号hashKey*/
            Books_info books_info = (Books_info) redisTemplate.opsForHash().get("books_info", info_id);
            if (books_info != null){
                log.info("redis中找到了类型");
                return books_info;
            }
            return null;
        }
        return null;/*在redis中没找到*/
    }

    public Boolean putBooks_infoInRedis(Books_info books_info) {
        try {
            redisTemplate.opsForHash().put("books_info",books_info.getInfo_id(),books_info);
            redisTemplate.expire("books_info",1, TimeUnit.HOURS);
            log.info("hash值为books_info,key为" + books_info.getInfo_id() + ",value为" + books_info);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
