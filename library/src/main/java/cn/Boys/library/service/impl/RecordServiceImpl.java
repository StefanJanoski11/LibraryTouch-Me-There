package cn.Boys.library.service.impl;

import cn.Boys.library.dto.RecordDTO;
import cn.Boys.library.entity.Record;
import cn.Boys.library.enums.ResultEnum;
import cn.Boys.library.mapper.RecordMapper;
import cn.Boys.library.service.BooksService;
import cn.Boys.library.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecordServiceImpl implements RecordService {
    private  String key = "RECORD";
    private final Integer MAX = 3;
    @Autowired
    RecordMapper recordMapper;
    @Autowired
    RedisTemplate redisTemplate;
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
    //      if(op.values().size()>=MAX){
//          return new RecordDTO(ResultEnum.NOT_ACCEPTABLE,record);
//      }else if(op.values().size()==0){
//          //找不到就去数据库找
//          List<Record> list = recordMapper.getNotReturnById(record.getUser_id());
//          //如果没有就直接返回
//          if (list.size()==0) return new RecordDTO(ResultEnum.NOT_FOUND,record);
//      }
//      op.put(name,record);
//      recordMapper.setNotReturnById(record);
//      return new RecordDTO(ResultEnum.CREATED,record);

    //还书还书还书！！！
    @Override
    public RecordDTO setHaveReturn(Record record) {
//        List<record> haveRecord =   recordMapper.getHaveReturn();
//        int count = 0;
//        String name = "";
        //数据库更新状态
        System.out.println(record);
        int status = 0;
        String GetKey = key + record.getUser_id();
        String name = record.getUser_id().toString() + record.getBook_id().toString();
        System.out.println("name:"+name);
        BoundHashOperations op = redisTemplate.boundHashOps(GetKey);
        //先从数据库中把归还记录更改
        List<Record> list = recordMapper.getNotReturnById(record.getUser_id());
        for (Record item : list) {
            if (item.getBook_id() == record.getBook_id()) {
                status = recordMapper.setHaveReturn(record.getUser_id(), record.getBook_id());
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
            System.out.println("我真的找不到");
           notrecord =  recordMapper.getNotReturnById(id);
        }else {
            return notrecord;
        }
        System.out.println("返回结果："+notrecord);
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

}


//模拟数据
// List<record> notrecord = recordMapper.getNotReturnById(id);
//        String PutKey = key+id;
//        System.out.println("key"+key);
//        String name = "";
//
//        for (record records: notrecord) {
//            name = records.getUser_id().toString()+records.getBook_id().toString();
//            System.out.println("name"+name);
//            redisTemplate.boundHashOps(PutKey).put(name,records);
