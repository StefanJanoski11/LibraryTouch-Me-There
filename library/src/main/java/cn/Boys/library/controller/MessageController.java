package cn.Boys.library.controller;

import cn.Boys.library.util.HttpUtils;
import cn.Boys.library.util.StringCodeUtil;
import com.alibaba.fastjson.JSON;
import org.apache.http.HttpResponse;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/message")
public class MessageController {

    @PostMapping("/sendMessage")
    public String sendMessage(@RequestParam("user_phone") String phone, HttpServletRequest request){
        String host = "https://dxyzm.market.alicloudapi.com";
        String path = "/chuangxin/dxjk";
        String method = "POST";
        String appcode = "0aab6efeddf841c789b76dbfd009209a";
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<>();
        String code = StringCodeUtil.getCode(6);
        querys.put("content", "【人事管理系统】提醒您：验证码是：" + code+"，3分钟内有效！");
        querys.put("mobile", "13630455401");
        Map<String, String> bodys = new HashMap<>();
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(phone);
        Map<String, String> map = new HashMap<>();
        map.put("code",code);
        request.getSession().setAttribute("code",code);
        map.put("result","1");
        return JSON.toJSONString(map);
    }

    @GetMapping(value = "/checkMessage")
    public String checkMessage(@RequestParam("message")String message,HttpServletRequest request){
        String code = (String) request.getSession().getAttribute("code");
        System.out.println("session生成的验证码为---------------"+code);
        System.out.println("用户填入的验证码为-------------------"+message);
        if (code.equals(message)){
            return "1";
        }
        return "0";
    }
}
