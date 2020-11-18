package cn.Boys.library.dto;

import cn.Boys.library.entity.Record;
import cn.Boys.library.enums.ResultEnum;

import java.awt.print.Book;

public class RecordDTO {
    //状态
    private Integer state;
    //状态信息
    private String info;
    //书的对象
    private Record record;

    public RecordDTO(ResultEnum enums, Record record) {
        this.state = enums.getCode();
        this.info = enums.getMsg();
        this.record = record;
    }

    public Integer getState() {
        return state;
    }

    public String getInfo() {
        return info;
    }

    public Record getRecord() {
        return record;
    }
}
