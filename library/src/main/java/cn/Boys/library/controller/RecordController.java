package cn.Boys.library.controller;

import cn.Boys.library.dto.RecordDTO;
import cn.Boys.library.entity.Record;
import cn.Boys.library.mapper.RecordMapper;
import cn.Boys.library.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Parameter;
import java.util.List;

@RestController
@RequestMapping("/record")
public class RecordController {
    @Autowired
    RecordMapper recordMapper;
    @Autowired
    RecordService recordService;
    //借书
    @PutMapping("/insertNotReturn")
    public RecordDTO insertNotReturn(@RequestBody Record record){
        return recordService.InsertNotReturn(record);
    }
    //查询
    @GetMapping("/getNRById")
    public List<Record> getNotReturnById(@RequestParam("id")Integer id){
        return recordService.getNotReturn(id);
    }

    //还书
    @PutMapping("/returnBook")
    public RecordDTO returnBookById(@RequestBody Record record){
        return recordService.setHaveReturn(record);
    }

//    @GetMapping("/HaveReturn")
//    public List<record> getHaveReturn(Integer userId){
//
//        return recordService.getNotReturn(userId);
//    }
}
