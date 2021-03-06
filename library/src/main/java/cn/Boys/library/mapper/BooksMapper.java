package cn.Boys.library.mapper;

import cn.Boys.library.dto.BookDTO;
import cn.Boys.library.entity.Books;
import cn.Boys.library.entity.BooksStatistic;
import cn.Boys.library.entity.Books_info;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

@Mapper
public interface BooksMapper {
    String allFields = " books_id,books_type,books_name,books_author,books_last,books_state,books_create_date,books_pic,books_daily" +
            ",books_weekly,books_monthly,books_publisherId,books_modifiedId,books_alter_date ";

    @Update("update books set books_daily=#{books_daily} where books_id=#{book_id}")
    public int updateBooks_daily(Books books);

    @Update("update books set books_weekly=#{books_weekly} where books_id=#{book_id}")
    public int updateBooks_weekly(Books books);

    @Update("update books set books_monthly=#{books_monthly} where books_id=#{book_id}")
    public int updateBooks_monthly(Books books);

    /*查询所有存在的图书*/
    @Select("Select " +allFields+"from books where books_state=1")
    public List<Books> getAllExistBooks();

    /*查询所有图书,包含已上架下架*/
    @Select("Select " +allFields+"from books")
    public List<Books> getAllBook();

    /*插入图书*/
    @Options(useGeneratedKeys = true,keyProperty = "books_id")
    @Insert("insert into books(books_type,books_name,books_author,books_last,books_state,books_create_date," +
            "books_pic,books_daily,books_weekly,books_monthly,books_publisherId,books_modifiedId,books_alter_date)" +
            " values(#{books_type},#{books_name},#{books_author},#{books_last},#{books_state},#{books_create_date}," +
            "#{books_pic},#{books_daily},#{books_weekly},#{books_monthly},#{books_publisherId},#{books_publisherId},#{books_create_date})")
    public int insertBook(Books books);

    /*根据图书主键查询*/
    @Select("Select " +allFields+" from books where books_id=#{books_id}")
    public Books selectBooksByPrimaryKey(Integer books_id);


    //图书表模糊查询
    @Select("Select " +allFields + " from books " +"where books_name like  CONCAT('%',#{name},'%')")
    public List<Books> quaryBook(String name);

    //根据图书type查询类别信息
    @Select("Select " + allFields +"from books_info where books_info =#{type} ")
    public Books_info getBooksByType(Integer type);

    //根据类型动态查询
    public List<Books_info> quaryBooksByType(Books_info bookInfo);

    //动态更新修改类别之类信息
    public Books_info editBooksInfo(Books_info books_info);

    //根据类型窗口通过type查询剩余数量
    @Select("select books_last from books where books_id=#{type}")
    public Integer getBooksLastByType(Integer type);

    //根据类型窗口通过info_id修改数量
    @Update("update Books set books_last=#{last} where books_type=#{id} ")
    public Integer editBooksLast(Integer id,Integer last);

    //根据id来修改图书上架下架
    @Update("update Books set books_state = #{status} where books_id = #{bookId}")
    public Integer setBookStatus(Integer bookId,Integer status);

    //根据图书id使图书加一
    @Update("update Books set books_last =books_last+1  where books_id = #{bookId}")
    public Integer BooksLastRaise(Integer bookId);
    //根据图书id使图书减一
    @Update("update Books set books_last =books_last-1  where books_id = #{bookId}")
    public Integer BooksLastDown(Integer bookId);
    //根据id查询所有所有图书信息
    @Select("select * from books a,books_info b where a.books_id =#{booksId} and a.books_type = b.info_id ")
    public BookDTO getAllBookInfo(Integer booksId);
    //根据图书id获取图片位置
    @Select("select a.books_pic ,a.books_id ,r.book_id from books a,record r where r.book_id=#{bookId} and r.book_id = a.books_id")
    public String getPicByBookId(Integer bookId);



}
