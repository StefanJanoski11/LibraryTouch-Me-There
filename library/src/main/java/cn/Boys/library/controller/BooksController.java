package cn.Boys.library.controller;

import cn.Boys.library.dto.BookStatusDTO;
import cn.Boys.library.dto.Result;
import cn.Boys.library.entity.Books;
import cn.Boys.library.entity.Books_info;
import cn.Boys.library.enums.ResultEnum;
import cn.Boys.library.mapper.BooksMapper;
import cn.Boys.library.mapper.Books_infoMapper;
import cn.Boys.library.service.impl.BatchSetBooksServiceImpl;
import cn.Boys.library.service.impl.BooksServiceImpl;
import cn.Boys.library.service.impl.Books_infoServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RequestMapping("/books")
@RestController
@CrossOrigin
@Api(description = "图书")
public class BooksController {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Log4j.class);
    private static final String pictureSavePath = "C:/Users/Administrator/Desktop/11.24/1.3/LibraryTouch-Me-There/library/src/main/resources/static/img/";

    @Autowired
    BooksMapper booksMapper;
    @Autowired
    BooksServiceImpl booksService;
    @Autowired
    Books_infoServiceImpl books_infoService;
    @Autowired
    Books_infoMapper books_infoMapper;
    @Autowired
    BatchSetBooksServiceImpl batchSetService;


    /*获取日点击量多的5个*/
    @ApiOperation(value = "获取每日热点图书", notes = "获取每日热点图书")
    @GetMapping("/getDailyHot")
    public Result getDailyHot() {
        Set<Books> booksSet = booksService.getDailyHotByRedis();
        return new Result(booksSet, ResultEnum.OK);
    }

    /*获取周点击量多的5个*/
    @ApiOperation(value = "获取每周热点图书", notes = "获取每周热点图书")
    @GetMapping("/getWeeklyHot")
    public Result getWeeklyHot() {
        Set<Books> booksSet = booksService.getWeeklyHotByRedis();
        return new Result(booksSet, ResultEnum.OK);
    }

    /*获取月点击量多的5个*/
    @ApiOperation(value = "获取每月热点图书", notes = "获取每月热点图书")
    @GetMapping("/getMonthlyHot")
    public Result getMonthlyHot() {
        Set<Books> booksSet = booksService.getMonthlyHotByRedis();
        return new Result(booksSet, ResultEnum.OK);
    }

    /*查看所有存在的图书*/
    @ApiOperation(value = "获取所有存在图书", notes = "分页获取所有存在的图书")
    @GetMapping("/getAll")
    public Result getAllExistBooks(Integer pageNum) {
        pageNum = pageNum == null ? 1 : pageNum;
        PageHelper.startPage(pageNum, 10);/*一页存放数量*/
        List<Books> booksList = booksMapper.getAllExistBooks();
        if (booksList.size() == 0) {
            return new Result(null, ResultEnum.NOT_FOUND);
        }
        PageInfo<Books> info = new PageInfo<>(booksList, 5);/*可以显示10页点击【1】【2】..【10】*/
        log.info("正在执行批量导入redis线程...");
        batchSetService.batchSetBooksInRedis(booksList);
        return new Result(info, ResultEnum.OK);
    }

    /*查看图书详情*/
    @ApiOperation(value = "获取图书详情", notes = "根据图书编号获取图书详情")
    @GetMapping("/detail")
    public Result detail(Integer books_id) {
        Map<String, Object> map = new HashMap<>();
        /*先在redis中查找图书*/
        Books book = booksService.selectBookById(books_id);
        if (book != null) {/*在redis中找到了*/
            map.put("books", book);
            return getAndSetBooks_infoInRedis(map, book);
        }
        /*redis中没找到*/
        /*到数据库中查找*/
        book = booksMapper.selectBooksByPrimaryKey(books_id);
        if (book != null) {/*在数据库中找到了*/
            /*将图书存放到redis*/
            if (!booksService.putBooksInRedis(book)) {
                return new Result(null, ResultEnum.SERVER_ERROR);
            }
            map.put("books", book);
            return getAndSetBooks_infoInRedis(map, book);
        }
        /*没有找到指定图书资源*/
        return new Result(null, ResultEnum.NOT_FOUND);
    }

    /*从redis中获取图书类型，并把图书类型存放到redis中*/
    private Result getAndSetBooks_infoInRedis(Map<String, Object> map, Books book) {
        /*先在redis查找图书类型*/
        Books_info books_info = books_infoService.selectBooks_infoById(book.getBooks_type());
        if (books_info != null) {/*类型在redis中找到了*/
            map.put("books_info", books_info);
            return new Result(map, ResultEnum.OK);
        }
        /*类型在redis中没找到，现在到数据库中找*/
        books_info = books_infoMapper.selectBooks_infoByPrimaryKey(book.getBooks_type());
        if (books_info != null) {/*类型在数据库中找到了*/
            /*将类型放到redis中*/
            if (!books_infoService.putBooks_infoInRedis(books_info)) {
                return new Result(null, ResultEnum.SERVER_ERROR);
            }
            map.put("books_info", books_info);
            return new Result(map, ResultEnum.OK);
        }
        return new Result(null, ResultEnum.NOT_FOUND);
    }


    @PostMapping("/upload")
    @ResponseBody
    public Result upload(@RequestBody MultipartFile books_img) {
        if (books_img.isEmpty()) {
            log.error("上传的图片为空！");
            return new Result("/img/default.jpg", ResultEnum.OK);
        }
        String picName = books_img.getOriginalFilename();
        String absoluteUrl = UUID.randomUUID().toString().substring(0, 6) + "_" + picName;
        String url = pictureSavePath + absoluteUrl;
        System.out.println(url);
        File realFile = new File(url);
        if (!realFile.getParentFile().exists()) {/*文件不存在就自动创建*/
            realFile.getParentFile().mkdir();
        }
        try {
            books_img.transferTo(realFile);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(null, ResultEnum.SERVER_ERROR);
        }
        return new Result("/img/" + absoluteUrl, ResultEnum.OK);
    }

    /*插入图书信息*/
    @ApiOperation(value = "插入图书信息", notes = "通过图书全字段信息插入图书")
    @PostMapping("/insert")
    public Result insertBook(@RequestBody Books books) {
        int i = booksMapper.insertBook(books);
        /*将图书插入到数据库*/
        if (i <= 0) {
            return new Result(null, ResultEnum.NOT_ACCEPTABLE);
        }
        /*将图书存放到redis*/
        if (!booksService.putBooksInRedis(books)) {
            return new Result(null, ResultEnum.SERVER_ERROR);
        }
        return new Result(books, ResultEnum.CREATED);
    }

    /*批量导出图书信息*/
    @ApiOperation(value = "批量导出获取图书", notes = "批量导出获取图书")
    @GetMapping("/download")
    public Result download(HttpServletResponse response) throws IOException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String filename = LocalDate.now().format(dtf) + "_LIBRARY.xlsx";
        filename = URLDecoder.decode(filename, "UTF-8");
        String[] head = {"编号","国家","篇幅（字数）","主题","类型", "名称", "作者", "剩余量", "状态", "上架时间", "日点击量", "周点击量", "月点击量", "上架管理员id", "最新修改时间"};
        HSSFWorkbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("图书信息");
        /*设置表的表头*/
        int currRow = 0;
        Row row = sheet.createRow(currRow++);
        for (int i = 0; i < head.length; i++) {
            Cell cell = row.createCell(i);
            HSSFRichTextString hrts = new HSSFRichTextString(head[i]);
            cell.setCellValue(hrts);
        }
        List<Books> booksList = booksMapper.getAllBook();
        List<Books_info> books_infoList = books_infoMapper.getAllBooks_info();
        Map<Integer,Books_info> books_infoMap = new HashMap<>();
        books_infoList.forEach(books_info -> {
            books_infoMap.put(books_info.getInfo_id(),books_info);
        });
        for (Books book : booksList) {
            Row sheetRow = sheet.createRow(currRow++);
            sheetRow.createCell(0).setCellValue(book.getBooks_id());
            Books_info books_info = books_infoMap.get(book.getBooks_type());
            if (books_info == null){
                sheetRow.createCell(1).setCellValue("无");
                sheetRow.createCell(2).setCellValue("无");
                sheetRow.createCell(3).setCellValue("无");
                sheetRow.createCell(4).setCellValue("无");
            }else {
                sheetRow.createCell(1).setCellValue(books_info.getInfo_country());
                sheetRow.createCell(2).setCellValue(books_info.getInfo_length());
                sheetRow.createCell(3).setCellValue(books_info.getInfo_theme());
                sheetRow.createCell(4).setCellValue(books_info.getInfo_type());
            }
            sheetRow.createCell(5).setCellValue(book.getBooks_name());
            sheetRow.createCell(6).setCellValue(book.getBooks_author());
            sheetRow.createCell(7).setCellValue(book.getBooks_last());
            sheetRow.createCell(8).setCellValue(book.getBooks_state() == 1 ? "上架" : "下架");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
            sheetRow.createCell(9).setCellValue(sdf.format(book.getBooks_create_date()));
            sheetRow.createCell(10).setCellValue(book.getBooks_daily());
            sheetRow.createCell(11).setCellValue(book.getBooks_weekly());
            sheetRow.createCell(12).setCellValue(book.getBooks_monthly());
            sheetRow.createCell(13).setCellValue(book.getBooks_publisherId());
            sheetRow.createCell(14).setCellValue(sdf.format(book.getBooks_alter_date()));
        }
        response.setContentType("application/octet-stream");/*二进制流*/
        response.setHeader("Content-disposition", "attachment;filename=" + filename);
        OutputStream os = null;
        try {
            os = response.getOutputStream();
            response.flushBuffer();
            workbook.write(os);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(null, ResultEnum.SERVER_ERROR);
        } finally {
            os.close();
        }
        log.info("文件名称：" + filename);
        return new Result(null, ResultEnum.OK);
    }


    //模糊查询测试
    @ApiOperation(value = "根据书名模糊查询", notes = "根据书名模糊查询")
    @GetMapping("/quaryName")
    public Result quaryBooks(@RequestParam String name) {
        return booksService.quaryBooks(name);
    }

    @ApiOperation(value = "根据图书类型查询", notes = "根据图书类型进行动态查询")
    @PostMapping("/quaryType")
    public Result quaryBooks(@RequestBody Books_info bookInfo) {
        return booksService.getBooksByType(bookInfo);
    }

    @ApiOperation(value = "上架和下架图书", notes = "通过图书编号以及状态进行图书上架与下架")
    @GetMapping("/setBookStatus")
    public Result setBookStatus(@RequestParam("bookId") Integer bookId, @RequestParam("status") Integer status) {
        return booksService.publishBook(bookId, status);
    }

//    @ApiOperation(value = "修改图书类型",notes = "通过图书类型全字段修改")
//    @GetMapping("/updateBookType")
//    public Result updateBookType(@RequestBody Books_info booksInfo){
//        return booksService.editBooksInfo(booksInfo);
//    }

    @ApiOperation(value = "获取所有图书", notes = "获取所有图书并在状态列显示有货、已借、无货")
    @GetMapping("/booksList")
    public List<BookStatusDTO> BooksList() {
        return booksService.BooksStatusList();
    }

    @ApiOperation(value = "获取图书详情", notes = "根据图书编号获取图书详情")
    @GetMapping("/getBookAllInfo")
    public Result getBookAllInfo(@RequestParam Integer id) {
        return booksService.getAllBookInfo(id);
    }
}
