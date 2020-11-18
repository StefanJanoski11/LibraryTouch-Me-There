package cn.Boys.library.mapper;

import cn.Boys.library.dto.RecordDTO;
import cn.Boys.library.entity.Record;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;


@Mapper
public interface RecordMapper {
     @Select("Select user_id,user_name,book_id,book_name,book_return_state,book_return_date,book_lend_date,book_scheduled from record")
     public Record getAllRecord();

//    @Select("SELECT user_id,book_id,book_name,book_return_state,book_return_date,book_lend_date,book_scheduled from record where user_id = #{id}")
//    public List<record> getByUserId(Integer id);

    @Select("SELECT user_id,user_name,book_id,book_name,book_return_state,book_return_date,book_lend_date,book_scheduled " +
            "from record where book_return_state = 0 ")
    public List<Record> getNotReturn();
    //根据ID查已经还书
    @Select("SELECT user_id,user_name,book_id,book_name,book_return_state,book_return_date,book_lend_date,book_scheduled " +
            "from record where book_return_state = 1 and id=#{userId} ")
    public List<Record> getHaveReturn(Integer userId);

    //根据ID查没有还书
    @Select("SELECT user_id,user_name,book_id,book_name,book_return_state,book_return_date,book_lend_date,book_scheduled " +
            "from record where book_return_state = 0 and user_id=#{userId}")
    public List<Record> getNotReturnById(Integer userId);

    //借书
    @Insert("INSERT INTO record(user_id,user_name,book_id,book_name,book_return_state,book_scheduled) " +
            "Values(#{user_id},#{user_name},#{book_id},#{book_name},0,#{book_scheduled}) ")
    public Integer setNotReturnById(Record record);

    //还书
    @Update("UPDATE record set book_return_state = 1 where user_id=#{userId} and book_id=#{bookId}")
    public Integer setHaveReturn(Integer userId,Integer bookId);


}
