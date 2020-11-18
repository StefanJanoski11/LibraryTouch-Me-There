package cn.Boys.library.controller;

import cn.Boys.library.mapper.RecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/fuck")
@ResponseBody
public class test {
    @Autowired
    RecordMapper recordMapper;

    @GetMapping("/hello")
    public void test(){

        System.out.println(recordMapper.getNotReturn());
    }

}
