package cn.Boys.library.service;

import cn.Boys.library.entity.Books_info;

public interface Books_infoService {

    public Books_info selectBooks_infoById(Integer info_id);

    public Boolean putBooks_infoInRedis(Books_info books_info);
}
