package cn.Boys.library.controller;

import cn.Boys.library.dto.Result;
import cn.Boys.library.enums.ResultEnum;
import cn.Boys.library.util.HttpUtils;
import cn.Boys.library.util.StringCodeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j;
import org.apache.http.HttpResponse;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/message")
@CrossOrigin
@Api(value = "短信信息")
public class MessageController {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Log4j.class);

    @ApiOperation(value = "获取验证码",notes = "根据有效的手机号码获取短信验证码")
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
        querys.put("content", "【五邑大学】提醒您：验证码是：" + code +"。如非本人操作，请忽略本短信");
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

}
