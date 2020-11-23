package cn.Boys.library.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Queue;

@Component
public class EmailProducer {
    /*注入消息的队列*/
    @Autowired
    private Queue queue;
    /*注入springboot封装的jms工具类*/
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    public void send(String email){
        /*添加消息到消息队列中*/
        jmsMessagingTemplate.convertAndSend(queue,email);
    }
}
