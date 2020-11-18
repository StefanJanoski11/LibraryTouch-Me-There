package cn.Boys.library.service.impl;

import cn.Boys.library.entity.Record;
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
    //
    public int setNotReturn(Record record ) {
       String PutKey = key+record.getUser_id();
       String name = record.getUser_id().toString()+record.getBook_id().toString();
      //redis操作对象
      BoundHashOperations op = redisTemplate.boundHashOps(key);
      //先找redis，如果找到大于三，直接返回
      if(op.values().size()>=MAX){
          return 0;
      }else if(op.values().size()==0){
          //找不到就去数据库找
          List<Record> list = recordMapper.getNotReturnById(record.getUser_id());
          //如果没有就直接返回
          if (list.size()==0) return 0;
      }
      op.put(name,record);
      recordMapper.setNotReturnById(record);
      return 1;
    }

    //
    // List<record> notrecord = recordMapper.getNotReturnById(id);
    //        String PutKey = key+id;
    //        System.out.println("key"+key);
    //        String name = "";
    //
    //        for (record records: notrecord) {
    //            name = records.getUser_id().toString()+records.getBook_id().toString();
    //            System.out.println("name"+name);
    //            redisTemplate.boundHashOps(PutKey).put(name,records);

    @Override
    public void setHaveReturn(Integer id) {
//        List<record> haveRecord =   recordMapper.getHaveReturn();
//        int count = 0;
//        String name = "";


        }







    @Override
    public List<Record> getNotReturn(Integer id) {
        key +=id;
        System.out.println("require:"+key);
        List<Record> notrecord =  redisTemplate.boundHashOps(key).values();
        System.out.println("返回结果："+notrecord);
        return notrecord;
    }

    @Override
    public List<Record> getHaveReturn(Integer id) {
       String getKey=key + id;
       List<Record> haverecord = redisTemplate.boundHashOps(getKey).values();
       return haverecord;
    }




}
