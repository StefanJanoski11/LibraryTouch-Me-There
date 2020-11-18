package cn.Boys.library.service;

import cn.Boys.library.entity.Record;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RecordService {

    public int setNotReturn(Record record);

    public void setHaveReturn(Integer id);

    public List<Record> getNotReturn(Integer id);

    public List<Record> getHaveReturn(Integer id);

}
