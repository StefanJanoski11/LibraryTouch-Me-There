package cn.Boys.library.mapper;

import cn.Boys.library.entity.Books;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BooksMapper {
    /*查询所有图书*/
    @Select("Select books_id,books_type,books_name,books_author,books_last,books_state,books_registerDate,books_daily" +
            ",books_weekly,books_monthly,books_publisherId,books_createTime from books")
    public List<Books> getAllBook();

    /*插入图书*/
    @Options(useGeneratedKeys = true,keyProperty = "books_id")
    @Insert("insert into books(books_type,books_name,books_author,books_last,books_state," +
            "books_registerDate,books_publisherId,books_daily,books_weekly,books_monthly,books_createTime)" +
            " values(#{books_type},#{books_name},#{books_author},#{books_last},1,#{books_registerDate}," +
            "#{books_publisherId},#{books_daily},#{books_weekly},#{books_monthly},#{books_createTime})")
    public int insertBook(Books books);
}
