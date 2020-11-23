package cn.Boys.library.mapper;

import cn.Boys.library.entity.Books_info;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface Books_infoMapper {
    @Select("select info_id,info_country,info_length,info_theme,info_type,info_state,info_createTime," +
            "info_publisher,info_modifiedId from books_info where info_id=#{books_type} and info_state=1")
    public Books_info selectBooks_infoByPrimaryKey(Integer books_type);

    @Options(useGeneratedKeys = true,keyProperty = "info_id")
    @Insert("insert into books_info(info_country,info_length,info_theme,info_type," +
            "info_publisher,info_modifiedId) values(#{info_country},#{info_length},#{info_theme},#{info_type}," +
            "#{info_publisher},#{info_modifiedId})")
    public int insertBooks_info(Books_info books_info);
}
