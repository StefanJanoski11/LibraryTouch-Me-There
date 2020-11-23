package cn.Boys.library.controller;

import cn.Boys.library.dto.Result;
import cn.Boys.library.enums.ResultEnum;
import cn.Boys.library.util.HttpUtils;
import cn.Boys.library.util.StringCodeUtil;
import lombok.extern.log4j.Log4j;
import org.apache.http.HttpResponse;

import org.apache.http.util.EntityUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/message")
@CrossOrigin
public class MessageController {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Log4j.class);
    @GetMapping("/send")
    public Result sendMessage(@RequestParam("user_phone") String user_phone, HttpSession session){
        System.out.println(user_phone);
        String host = "https://zwp.market.alicloudapi.com";
        String path = "/sms/sendv2";
        String method = "GET";
        String appcode = "0aab6efeddf841c789b76dbfd009209a";
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<>();
        String code = StringCodeUtil.getCode(6);
        querys.put("content", "图书馆系统提醒您：验证码是：" + code +"。如非本人操作，请忽略本短信");
        querys.put("mobile", user_phone);
        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
            log.info(response.toString());
            //获取response的body
            //System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("手机号："+user_phone+",验证码为："+code);
        session.setAttribute("code",code);
        return new Result(code,ResultEnum.OK);
    }

//    @GetMapping(value = "/check")
//    public Result checkMessage(@RequestParam("message")String message,HttpServletRequest request){
//        String code = (String) request.getSession().getAttribute("code");
//        log.info("session生成的验证码为---------------"+code);
//        log.info("用户填入的验证码为-------------------"+message);
//        if (code.equals(message)){
//            return new Result(null,ResultEnum.OK);
//        }
//        return new Result(null,ResultEnum.NOT_ACCEPTABLE);
//    }

    public static void main1(String[] args) {
        String host = "https://zwp.market.alicloudapi.com";
        String path = "/sms/edittemplete";
        String method = "POST";
        String appcode = "0aab6efeddf841c789b76dbfd009209a";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("content", "图书馆系统提醒您：验证码是：#code#。如非本人操作，请忽略本短信");
        querys.put("signature", "云通知");
        Map<String, String> bodys = new HashMap<String, String>();
        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());
            //获取response的body
            log.error(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main2(String[] args) {
        String host = "https://zwp.market.alicloudapi.com";
        String path = "/sms/getmessagestatus";
        String method = "GET";
        String appcode = "0aab6efeddf841c789b76dbfd009209a";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("messageId", "2509");
        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
            System.out.println(response.toString());
            //获取response的body
            System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String host = "https://zwp.market.alicloudapi.com";
        String path = "/sms/gettempletestatus";
        String method = "GET";
        String appcode = "0aab6efeddf841c789b76dbfd009209a";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("templateId", "2509");
        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
            System.out.println(response.toString());
            //获取response的body
            System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
