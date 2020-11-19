package cn.Boys.library.mapper;


import cn.Boys.library.entity.Books;
import cn.Boys.library.entity.Books_info;
import org.apache.ibatis.annotations.*;

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
    //图书模糊查询
    @Select("Select books_id,books_type,books_name,books_author,books_last,books_state,books_registerDate,books_daily" +
            ",books_weekly,books_monthly,books_publisherId,books_createTime from books " +
            "where books_name like '%#{name}%'")
    public List<Books> quaryBook(String name);

    //根据图书type查询类别信息
    @Select("Select info_id,info_country,info_length,info_theme,info_type,info_state,info_createTime,info_publisher,info_modifiedId" +
            "from books_info where books_info =#{type} ")
    public Books_info getBooksByType(Integer type);

    //根据类型动态查询
    public List<Books_info> quaryBooksByType(Books_info bookInfo);

    //修改类别
    public Books_info editBooksInfo(Books_info books_info);

    //根据类型窗口通过type查询剩余数量
    @Select("select books_last from books where books_id=#{type}")
    public Integer getBooksLastByType(Integer type);

    //根据类型窗口通过type修改数量
    @Update("update Books set books_last=#{last} where books_last=#{type} ")
    public Integer editBooksLast(Integer type,Integer last);

}
