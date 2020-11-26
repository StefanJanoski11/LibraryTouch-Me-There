package cn.Boys.library.mapper;

import cn.Boys.library.dto.Result;
import cn.Boys.library.entity.BooksStatistic;
import cn.Boys.library.entity.Notice;
import cn.Boys.library.entity.Record;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;


@Mapper
public interface RecordMapper {
    /*获取图书借出还入统计*/
    @Select("select "+currentDayLend+"as currentDayLend,"+currentWeekLend+"as currentWeekLend,"+currentMonthLend+"as currentMonthLend," +
            currentDayReturn+"as currentDayReturn,"+currentWeekReturn+"as currentWeekReturn,"+currentMonthReturn+"as currentMonthReturn")
    public BooksStatistic getStatistic();

    String currentDayLend = " (select count(1) from record where DATE_FORMAT(book_lend_date,'%Y-%m-%d')=CURRENT_DATE) ";
    String currentWeekLend = " (select count(1) from record where WEEK(book_lend_date)=WEEK(CURRENT_DATE) and YEAR(book_lend_date)=YEAR(CURRENT_DATE )) ";
    String currentMonthLend = " (select count(1) from record where MONTH(book_lend_date)=MONTH(CURRENT_DATE) and YEAR(book_lend_date)=YEAR(CURRENT_DATE )) ";
    String currentDayReturn = " (select count(1) from record where DATE_FORMAT(book_return_date,'%Y-%m-%d')=CURRENT_DATE) ";
    String currentWeekReturn = " (select count(1) from record where WEEK(book_return_date)=WEEK(CURRENT_DATE) and YEAR(book_return_date)=YEAR(CURRENT_DATE )) ";
    String currentMonthReturn = " (select count(1) from record where MONTH(book_return_date)=MONTH(CURRENT_DATE) and YEAR(book_return_date)=YEAR(CURRENT_DATE )) ";


    @Select("Select user_id,user_name,book_id,book_name,book_return_state,book_return_date,book_lend_date,book_scheduled from record")
    public List<Record> getAllRecord();

//    @Select("SELECT user_id,book_id,book_name,book_return_state,book_return_date,book_lend_date,book_scheduled from record where user_id = #{id}")
//    public List<record> getByUserId(Integer id);

    @Select("SELECT user_id,user_name,book_id,book_name,book_return_state,book_return_date,book_lend_date,book_scheduled " +
            "from record where book_return_state = 0 ")
    public List<Record> getNotReturn();
    //根据ID查已经还书
    @Select("SELECT user_id,user_name,book_id,book_name,book_return_state,book_return_date,book_lend_date,book_scheduled " +
            "from record where book_return_state = 1 and user_id=#{userId} ")
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

    @Select("SELECT * FROM notice ORDER BY notice_post DESC LIMIT 0,1")
    public Notice quaryNotice();

    @Insert("insert into notice(notice_text) values(#{notice_text})")
    public Integer addNotice(String notice_text);

    @Update("UPDATE notice set notice_text =#{text}  where user_id=#{userId}")
    public Result updateNotice(String text, Insert userId);

    //借阅历史模糊查询
    @Select("SELECT user_id,user_name,book_id,book_name,book_return_state,book_return_date,book_lend_date,book_scheduled " +
            "from record where book_name like CONCAT('%',#{name},'%')")
    public List<Record> quaryRecord(String name);

    @Select("select books_pic from books where books_id = #{bookId}")
    public String getBookPic(Integer bookId);
}
