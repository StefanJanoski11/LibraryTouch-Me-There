package cn.Boys.library.service;

import cn.Boys.library.dto.RecordDTO;
import cn.Boys.library.dto.Result;
import cn.Boys.library.entity.Record;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RecordService {

    public List<Record> getAllRecord();

    public RecordDTO InsertNotReturn(Record record);

    public RecordDTO setHaveReturn(Record record);

    public List<Record> getNotReturn(Integer id);

    public List<Record> getHaveReturn(Integer id);

    //公告增加
    public Result addNotice(String notice);

    //公告修改
//    public Result updateNotice();
    //公告删除
//    public Result delNotice();
    //公告查询
    public Result quaryNotice();

    public Result quaryRecord(String name);

}
