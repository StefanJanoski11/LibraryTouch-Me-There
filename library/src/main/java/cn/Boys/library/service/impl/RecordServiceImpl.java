package cn.Boys.library.service.impl;

import cn.Boys.library.dto.RecordDTO;
import cn.Boys.library.dto.Result;
import cn.Boys.library.entity.Books;
import cn.Boys.library.entity.Notice;
import cn.Boys.library.entity.Record;
import cn.Boys.library.entity.User;
import cn.Boys.library.enums.ResultEnum;
import cn.Boys.library.mapper.RecordMapper;
import cn.Boys.library.service.BooksService;
import cn.Boys.library.service.RecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecordServiceImpl implements RecordService {
    Logger logger = LoggerFactory.getLogger(RecordServiceImpl.class);
    private  String key = "RECORD";
    private final Integer MAX = 3;
    @Autowired
    RecordMapper recordMapper;
    @Autowired
    BooksService booksService;
    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public List<Record> getAllRecord() {
        List<Record> list = new ArrayList<>();
        list = recordMapper.getAllRecord();
        return list;
    }

    @Override
    //读书人的事情，这不算偷
    public RecordDTO InsertNotReturn(Record record) {
        //记录mysql插入状态
        int status = 0;
        if(recordMapper.getNotReturnById(record.getUser_id()).size()>=3){
            return new RecordDTO(ResultEnum.NOT_ACCEPTABLE,record);
        }
        //mysql插入失败则返回
       if(recordMapper.setNotReturnById(record)!=1) {
           return new RecordDTO(ResultEnum.NOT_ACCEPTABLE,record);
       }
       String PutKey = key+record.getUser_id();
       System.out.println(record);
       String name = record.getUser_id().toString()+record.getBook_id().toString();
       //redis操作对象
       BoundHashOperations op = redisTemplate.boundHashOps(key);
       op.put(name,record);

       return new RecordDTO(ResultEnum.OK,record);

    }


    //还书还书还书！！！
    @Override
    public RecordDTO setHaveReturn(Record record) {
        //数据库更新状态
        System.out.println(record);
        int status = 0;
        String GetKey = key + record.getUser_id();
        String name = record.getUser_id().toString() + record.getBook_id().toString();
//        System.out.println();
        logger.info("name:"+name);

        BoundHashOperations op = redisTemplate.boundHashOps(GetKey);
        //先从数据库中把归还记录更改
        List<Record> list = recordMapper.getNotReturnById(record.getUser_id());
        for (Record item : list) {
            if (item.getBook_id() == record.getBook_id()) {
                status = recordMapper.setHaveReturn(record.getUser_id(), record.getBook_id());
                booksService.BooksLastRaise(record.getBook_id());
            }
        }
            if (op.delete(name) != null && status != 0) {
                return new RecordDTO(ResultEnum.OK, record);
            } else if (status != 0) {
                return new RecordDTO(ResultEnum.OK, record);
            }

            return new RecordDTO(ResultEnum.NOT_FOUND, record);

    }


    //看看你又多少本书没还
    @Override
    public List<Record> getNotReturn(Integer id) {
        String GetKey =key+id;
        System.out.println("require:"+GetKey);
        //从redis里面找
        List<Record> notrecord =  redisTemplate.boundHashOps(GetKey).values();
        //如果找不到，到数据库找
        if (notrecord.size()==0){
           notrecord =  recordMapper.getNotReturnById(id);
        }else {
            return notrecord;
        }
        return notrecord;
    }

    //还书记录查询
    @Override
    public List<Record> getHaveReturn(Integer id) {
       String getKey=key + id;
       List<Record> haverecord = redisTemplate.boundHashOps(getKey).values();
        if (haverecord.size()==0){
            haverecord =  recordMapper.getHaveReturn(id);
        }
       return haverecord;
    }

    @Override
    public Result addNotice(String notice) {

         if(recordMapper.addNotice(notice)>0){
             return new Result(notice,ResultEnum.OK);
         }
        return new Result(notice,ResultEnum.SERVER_ERROR);
    }


    @Override
    public Result quaryNotice() {
        Notice notice = recordMapper.quaryNotice();
        if(notice!=null){
            return new Result(notice,ResultEnum.OK);
        }
        return new Result(0,ResultEnum.SERVER_ERROR);
    }

    @Override
    public Result quaryRecord(String name) {
        logger.info(name);
        List<Record> list = recordMapper.quaryRecord(name);
        if( list.size()==0){
            return  new Result(null, ResultEnum.NOT_FOUND);
        }
        return new Result(list,ResultEnum.OK);
    }



}

