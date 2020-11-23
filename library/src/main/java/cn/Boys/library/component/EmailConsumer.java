package cn.Boys.library.component;


import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Component
public class EmailConsumer {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Log4j.class);

    @Autowired
    private JavaMailSender mailSender;/*用于发送文本文件*/

    // 使用JmsListener配置消费者监听的队列，其中name是接收到的消息
    @JmsListener(destination = "ActiveMQQueue")
    // SendTo 会将此方法返回的数据, 写入到 OutQueue 中去
    @SendTo("SQueue")
    public String consumeEmail(String email){
        try {
            System.out.println("成功接受email" + email);
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("13630455401@163.com");
            message.setTo(email);
            message.setSubject("【图书馆系统提醒您】注册消息");
            message.setText(email+"在本系统注册成功！欢迎您的使用！");
            mailSender.send(message);
            return "成功发送注册邮件email地址为：" + email;
        }catch (Exception e){
            log.error("邮箱发送失败");
            return "【注意】发送注册邮件email地址为：" + email +"失败了!";
        }
    }

}
