package cn.Boys.library.config;

import lombok.extern.log4j.Log4j;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;

@Configuration
public class ActiveMqConfig {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Log4j.class);
    //定义存放消息的队列
    @Bean
    public Queue queue(){
        return new ActiveMQQueue("ActiveMQQueue");
    }

    /*设置activeMq连接工厂*/
    @Bean
    public ConnectionFactory connectionFactory(){
        log.info("正在设置activeMq连接工厂");
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL("tcp://localhost:61616");
        connectionFactory.setUserName("admin");
        connectionFactory.setPassword("admin");
        return connectionFactory;
    }

}
