package cn.Boys.library.mapper;

import cn.Boys.library.entity.books;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface BooksMapper {
    @Select("SELECT books_type,books_name,books_author,books_last,books_state,books_registerDate，books_pic,books_publisher，books_daily,books_monthlyType,books_createTime from books where book_id = #{id}")
    public books getBooksDetailById(Integer id);

    @Select("Select ")
    public Integer getStatusById(Integer id);

}
