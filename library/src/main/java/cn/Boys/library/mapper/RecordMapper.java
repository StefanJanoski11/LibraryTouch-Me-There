package cn.Boys.library.mapper;

import cn.Boys.library.entity.Record;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface RecordMapper {
    @Select("Select user_id,book_id,book_name,book_return_state,book_return_date,book_lend_date,book_scheduled from record")
    public Record getAllRecord();

//    @Select("SELECT user_id,book_id,book_name,book_return_state,book_return_date,book_lend_date,book_scheduled from record where user_id = #{id}")
//    public List<record> getByUserId(Integer id);

    @Select("SELECT user_id,book_id,book_name,book_return_state,book_return_date,book_lend_date,book_scheduled from record where book_return_state = 0 ")
    public List<Record> getNotReturn();

    @Select("SELECT user_id,book_id,book_name,book_return_state,book_return_date,book_lend_date,book_scheduled from record where book_return_state = 1 ")
    public List<Record> getHaveReturn();

    @Select("SELECT user_id,book_id,book_name,book_return_state,book_return_date,book_lend_date,book_scheduled from record where book_return_state = 0 and user_id=#{userId}")
    public List<Record> getNotReturnById(Integer userId);

    @Insert("INSERT INTO record(book_id,book_name,book_return_state,book_scheduled) " +
            "Values(#{book_id},#{book_name},0,#{book_scheduled}) where user_id =#{user_id} ")
    public Integer setNotReturnById(Record record);



}
