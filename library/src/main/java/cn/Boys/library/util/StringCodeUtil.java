package cn.Boys.library.util;

import java.util.Random;

/*
* 产生指定个数的随机数
* */
public class StringCodeUtil {
    public static String getCode(int number){
        /*产生指定个数的随机数0-9*/
        Random random = new Random();
        String code = "";
        for (int i = 0; i < number; i++) {
            code += random.nextInt(10);
        }
        System.out.println("验证码是："+code);
        return code;
    }

}
