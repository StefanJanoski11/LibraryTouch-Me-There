package cn.Boys.library.service;

import cn.Boys.library.entity.Books;
import java.util.List;

public interface BatchSetBooksService {
    public void batchSetBooksInRedis(List<Books> list);
}
