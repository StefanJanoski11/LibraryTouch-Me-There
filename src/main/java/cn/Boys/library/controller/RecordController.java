package cn.Boys.library.controller;

import cn.Boys.library.entity.Record;
import cn.Boys.library.mapper.RecordMapper;
import cn.Boys.library.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Parameter;
import java.util.List;

@RestController
@RequestMapping("/record")
public class RecordController {
    @Autowired
    RecordMapper recordMapper;
    @Autowired
    RecordService recordService;

    @GetMapping("/NotReturn")
    public List<Record> getNotReturn(@RequestParam("userId") Integer userId){
        recordService.setHaveReturn(userId);
        return recordService.getNotReturn(userId);
    }
//    @GetMapping("/HaveReturn")
//    public List<record> getHaveReturn(Integer userId){
//
//        return recordService.getNotReturn(userId);
//    }
}
