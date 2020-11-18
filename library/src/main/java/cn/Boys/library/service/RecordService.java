package cn.Boys.library.service;

import cn.Boys.library.dto.RecordDTO;
import cn.Boys.library.entity.Record;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RecordService {

    public RecordDTO InsertNotReturn(Record record);

    public RecordDTO setHaveReturn(Record record);

    public List<Record> getNotReturn(Integer id);

    public List<Record> getHaveReturn(Integer id);

}
