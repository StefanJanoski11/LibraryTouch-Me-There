package cn.Boys.library.service.impl;

import cn.Boys.library.entity.Books;
import cn.Boys.library.entity.User;
import cn.Boys.library.service.BatchSetUserService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class BatchSetUserServiceImpl implements BatchSetUserService {
    @Autowired
    UserServiceImpl userService;
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Log4j.class);

    private  ExecutorService executorService = null;
    private static LinkedBlockingQueue<List<User>> batchQueue = null;

    /*初始化线程池*/
    @PostConstruct
    private void initThreadPool() {
        executorService = Executors.newFixedThreadPool(20);
        log.info("初始化固定可重用线程池中...");
        batchQueue = new LinkedBlockingQueue<>();
        log.info("初始化无界队列中...");
    }

    @PreDestroy
    private void destroyThreadPool() {
        executorService.shutdown();
        log.info("销毁固定可重用线程池中...");
    }

    @Override
    public void batchSetUserInRedis(List<User> list) {
        if (list.size() == 0) {
            return;
        }
        try {
            /*将图书集合作为一个对象放到队列中*/
            batchQueue.put(list);
            /*将对象封装成为一个Task任务接口*/
            BatchSetBooksTask task = new BatchSetBooksTask(batchQueue);
            /*启动线程从列中获取图书集合进行执行*/
            executorService.execute(task);
        } catch (InterruptedException e) {
            log.error(e.toString());
        }
    }

    /*自定义批量导入任务*/
    private class BatchSetBooksTask implements Runnable {
        private LinkedBlockingQueue<List<User>> batchQueue;

        public BatchSetBooksTask(LinkedBlockingQueue<List<User>> batchQueue) {
            this.batchQueue = batchQueue;
        }

        @Override
        public void run() {
            try {
                List<User> list = batchQueue.take();
                list.forEach(user -> userService.putUserInRedis(user));
            } catch (Exception e) {
                log.error(e.toString());
            }
        }
    }
}
