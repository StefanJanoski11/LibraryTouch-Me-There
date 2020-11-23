package cn.Boys.library.controller;

import cn.Boys.library.dto.Result;
import cn.Boys.library.entity.Books_info;
import cn.Boys.library.enums.ResultEnum;
import cn.Boys.library.mapper.Books_infoMapper;
import cn.Boys.library.service.impl.Books_infoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books_info")
@CrossOrigin
public class Books_infoController {
    @Autowired
    Books_infoMapper books_infoMapper;
    @Autowired
    Books_infoServiceImpl books_infoService;

    /*添加图书类型*/
    @GetMapping("/insert")
    public Result insert(Books_info books_info){
        int i = books_infoMapper.insertBooks_info(books_info);
        if (i<=0){/*数据库插入类型失败*/
            return new Result(null, ResultEnum.SERVER_ERROR);
        }
        /*在将类型存放到redis中*/
        if (!books_infoService.putBooks_infoInRedis(books_info)){/*存入redis有误*/
            return new Result(null,ResultEnum.SERVER_ERROR);
        }
        return new Result(books_info,ResultEnum.OK);
    }

}
