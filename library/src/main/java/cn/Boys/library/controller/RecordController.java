package cn.Boys.library.controller;

import cn.Boys.library.dto.RecordDTO;
import cn.Boys.library.dto.Result;
import cn.Boys.library.entity.BooksStatistic;
import cn.Boys.library.entity.Record;
import cn.Boys.library.enums.ResultEnum;
import cn.Boys.library.mapper.RecordMapper;
import cn.Boys.library.service.RecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/record")
@CrossOrigin
@Api(description = "借阅记录")
public class RecordController {
    @Autowired
    RecordMapper recordMapper;
    @Autowired
    RecordService recordService;

    /*获取图书借出还入统计*/
    @ApiOperation(value = "获取图书统计",notes = "获取图书的日、周、月统计数据")
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
    @ApiOperation(value = "获取所有借阅记录",notes = "获取所有借阅记录")
    @GetMapping("/getAll")
    public List<Record> getAllRecord(){
        return recordService.getAllRecord();
    }

    //借书
    @ApiOperation(value = "借阅图书",notes = "通过借阅记录全字段信息插入借阅记录")
    @PostMapping("/insertNotReturn")
    public RecordDTO insertNotReturn(@RequestBody Record record){
        return recordService.InsertNotReturn(record);
    }

    //查询还没还
    @ApiOperation(value = "获取用户所有未还借阅记录",notes = "根据用户编号查询所有未还借阅记录")
    @GetMapping("/getNRById")
    public List<Record> getNotReturnById(@RequestParam("id")Integer id){
        return recordService.getNotReturn(id);
    }

    //查询已经还
    @ApiOperation(value = "获取用户所有已还借阅记录",notes = "根据用户编号查询所有已还借阅记录")
    @GetMapping("/getHRById")
    public List<Record> getHaveReturnById(@RequestParam("id")Integer id){
        return recordService.getHaveReturn(id);
    }

    //借阅历史
    //查询已经还
    @ApiOperation(value = "获取用户所有借阅记录",notes = "根据用户编号查询所有借阅记录")
    @GetMapping("/getRecordById")
    public List<Record> getAllById(@RequestParam("id")Integer id){
        List<Record> list1 = recordService.getHaveReturn(id);
        List<Record> list2 = recordService.getNotReturn(id);
        list1.addAll(list2);
        return list1;
    }

    //还书
    @ApiOperation(value = "归还图书",notes = "通过借阅记录全字段信息插入还书记录")
    @PostMapping("/returnBook")
    public RecordDTO returnBookById(@RequestBody Record record){
        return recordService.setHaveReturn(record);
    }

    //增加公告
    @ApiOperation(value = "添加公告",notes = "添加公告，日期自动更新")
    @PostMapping("/addNotice")
    public Result addNotice(@RequestParam String notice){
       return recordService.addNotice(notice);
    }

    @ApiOperation(value = "查询最新公告",notes = "查询最新公告")
    @GetMapping("/quaryNotice")
    public Result quaryNotice(){
        return recordService.quaryNotice();
    }

    @ApiOperation(value = "模糊查询图书",notes = "根据书名模糊查询")
    @GetMapping("/quaryBook")
    public Result quaryByBookName(@RequestParam String name){
        return recordService.quaryRecord(name);
    }
}
