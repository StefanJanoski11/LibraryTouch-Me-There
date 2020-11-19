package cn.Boys.library.controller;

import cn.Boys.library.dto.RecordDTO;
import cn.Boys.library.entity.Record;
import cn.Boys.library.mapper.RecordMapper;
import cn.Boys.library.service.RecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Parameter;
import java.util.List;
@Api("跟记录相关的操作")
@RestController
@RequestMapping("/record")
public class RecordController {
    @Autowired
    RecordMapper recordMapper;
    @Autowired
    RecordService recordService;
    //借书
    @ApiOperation("借书,填好借书记录表传record对象")
    @PutMapping("/insertNotReturn")
    public RecordDTO insertNotReturn(@RequestBody Record record){
        return recordService.InsertNotReturn(record);
    }
    //查询
    @ApiOperation("根据用户ID来查询有哪些书没还,返回list")
    @GetMapping("/getNRById")
    public List<Record> getNotReturnById(@RequestParam("id")Integer id){
        return recordService.getNotReturn(id);
    }

    //还书
    @ApiOperation("根据用户ID来查询有哪些书没还")
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
