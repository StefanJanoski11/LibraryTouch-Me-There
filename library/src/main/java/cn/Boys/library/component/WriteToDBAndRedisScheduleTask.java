package cn.Boys.library.component;

import cn.Boys.library.entity.Books;
import cn.Boys.library.mapper.BooksMapper;
import cn.Boys.library.service.impl.BooksServiceImpl;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WriteToDBAndRedisScheduleTask {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Log4j.class);
    @Autowired
    BooksServiceImpl booksService;
    @Autowired
    BooksMapper booksMapper;

    @Scheduled(cron = "0 0 0 * * ?")/*每天的凌晨2点*/
    private void dailyProcess(){
        log.info("正在更新redis中的日点击量...");
        List<Books> booksList = booksService.updateDailyAccessInRedis();
        log.info("正在更新数据库中的日点击量...");
        booksList.forEach(books -> booksMapper.updateBooks_daily(books));
    }

    @Scheduled(cron = "0 0 0 * * 2")/*周一的0点更新*/
    private void weeklyProcess(){
        log.info("正在更新redis中的周点击量...");
        List<Books> booksList = booksService.updateWeeklyAccessInRedis();
        log.info("正在更新数据库中的周点击量...");
        booksList.forEach(books -> booksMapper.updateBooks_weekly(books));
    }

    @Scheduled(cron = "0 0 0 28 * ?")/*每个月第28天更新，避免大小月*/
    private void monthlyProcess(){
        log.info("正在更新redis中的月点击量...");
        List<Books> booksList = booksService.updateMonthlyAccessInRedis();
        log.info("正在更新数据库中的月点击量...");
        booksList.forEach(books -> booksMapper.updateBooks_monthly(books));
    }

}
