package cn.Boys.library.controller;

import cn.Boys.library.dto.RecordDTO;
import cn.Boys.library.dto.Result;
import cn.Boys.library.entity.BooksStatistic;
import cn.Boys.library.entity.Notice;
import cn.Boys.library.entity.Record;
import cn.Boys.library.enums.ResultEnum;
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
@CrossOrigin
public class RecordController {
    @Autowired
    RecordMapper recordMapper;
    @Autowired
    RecordService recordService;

    /*获取图书借出还入统计*/
    @GetMapping("/getStatistic")
    public Result getStatistic(){
        BooksStatistic statistic = recordMapper.getStatistic();
        if (statistic == null){
            return new Result(null, ResultEnum.SERVER_ERROR);/*服务器出现错误*/
        }
        /*后面可以考虑将记录放到redis中，借书还书修改redis记录*/
        return new Result(statistic, ResultEnum.OK);
    }

    //获得所有记录
    @ApiOperation("获得所有记录")
    @GetMapping("/getAll")
    public List<Record> getAllRecord(){
        return recordService.getAllRecord();
    }
    //借书
    @ApiOperation("借书,填好借书记录表传record对象")
    @PutMapping("/insertNotReturn")
    public RecordDTO insertNotReturn(@RequestBody Record record){
        return recordService.InsertNotReturn(record);
    }
    //查询还没还
    @ApiOperation("根据用户ID来查询有哪些书没还,返回list")
    @GetMapping("/getNRById")
    public List<Record> getNotReturnById(@RequestParam("id")Integer id){
        return recordService.getNotReturn(id);
    }

    //查询已经还
    @ApiOperation("根据用户ID来查询已经还的书,返回list")
    @GetMapping("/getHRById")
    public List<Record> getHaveReturnById(@RequestParam("id")Integer id){
        return recordService.getHaveReturn(id);
    }
    //借阅历史
    //查询已经还
    @ApiOperation("根据用户ID来查询所有记录,返回list")
    @GetMapping("/getRecordById")
    public List<Record> getAllById(@RequestParam("id")Integer id){
        List<Record> list1 = recordService.getHaveReturn(id);
        List<Record> list2 = recordService.getNotReturn(id);
        list1.addAll(list2);
        return list1;
    }

    //还书
    @ApiOperation("还书，传入record对象")
    @PutMapping("/returnBook")
    public RecordDTO returnBookById(@RequestBody Record record){
        return recordService.setHaveReturn(record);
    }

    //增加公告
    @ApiOperation("添加公告，日期自动更新")
    @PostMapping("/addNotice")
    public Result addNotice(@RequestParam String notice){
       return recordService.addNotice(notice);
    }

    @ApiOperation("查询最新公告")
    @GetMapping("/quaryNotice")
    public Result quaryNotice(){
        return recordService.quaryNotice();
    }

    @ApiOperation("根据书名模糊查询")
    @GetMapping("/quaryBook")
    public Result quaryByBookName(@RequestParam String name){
        return recordService.quaryRecord(name);
    }
}
