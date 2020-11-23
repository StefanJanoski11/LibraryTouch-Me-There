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

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RequestMapping("/books")
@RestController
@CrossOrigin
@Api(value="与图书有关类",description="借书还书展示" )
public class BooksController {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Log4j.class);
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
    @GetMapping("/getDailyHot")
    public Result getDailyHot() {
        Set<Books> booksSet = booksService.getDailyHotByRedis();
        return new Result(booksSet, ResultEnum.OK);
    }

    /*获取周点击量多的5个*/
    @GetMapping("/getWeeklyHot")
    public Result getWeeklyHot() {
        Set<Books> booksSet = booksService.getWeeklyHotByRedis();
        return new Result(booksSet, ResultEnum.OK);
    }

    /*获取月点击量多的5个*/
    @GetMapping("/getMonthlyHot")
    public Result getMonthlyHot() {
        Set<Books> booksSet = booksService.getMonthlyHotByRedis();
        return new Result(booksSet, ResultEnum.OK);
    }

    /*查看所有存在的图书*/
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
    @GetMapping("/detail/{book_id}")
    public Result detail(@PathVariable("book_id") Integer book_id) {
        Map<String, Object> map = new HashMap<>();
        /*先在redis中查找图书*/
        Books book = booksService.selectBookById(book_id);
        if (book != null) {/*在redis中找到了*/
            map.put("books", book);
            return getAndSetBooks_infoInRedis(map, book);
        }
        /*redis中没找到*/
        /*到数据库中查找*/
        book = booksMapper.selectBooksByPrimaryKey(book_id);
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

    /*插入图书信息*/
    @PostMapping("/insert")
    public Result insertBook(Books book) {
        int i = booksMapper.insertBook(book);
        /*将图书插入到数据库*/
        if (i <= 0) {
            return new Result(null, ResultEnum.NOT_ACCEPTABLE);
        }
        /*将图书存放到redis*/
        if (!booksService.putBooksInRedis(book)) {
            return new Result(null, ResultEnum.SERVER_ERROR);
        }
        return new Result(book, ResultEnum.CREATED);
    }

    /*批量导出图书信息*/
    @GetMapping("/download")
    public Result download(HttpServletResponse response) throws IOException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String filename = LocalDate.now().format(dtf) + "_LIBRARY.xlsx";
        filename = URLDecoder.decode(filename, "UTF-8");
        String[] head = {"编号", "类型编号", "名称", "作者", "剩余量", "状态", "上架时间", "日点击量", "周点击量", "月点击量", "上架管理员id", "产生时间"};
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
        for (Books book : booksList) {
            Row sheetRow = sheet.createRow(currRow++);
            sheetRow.createCell(0).setCellValue(book.getBooks_id());
            sheetRow.createCell(1).setCellValue(book.getBooks_type());
            sheetRow.createCell(2).setCellValue(book.getBooks_name());
            sheetRow.createCell(3).setCellValue(book.getBooks_author());
            sheetRow.createCell(4).setCellValue(book.getBooks_last());
            sheetRow.createCell(5).setCellValue(book.getBooks_state());
            sheetRow.createCell(6).setCellValue(book.getBooks_registerDate().toString());
            sheetRow.createCell(7).setCellValue(book.getBooks_daily());
            sheetRow.createCell(8).setCellValue(book.getBooks_weekly());
            sheetRow.createCell(9).setCellValue(book.getBooks_monthly());
            sheetRow.createCell(10).setCellValue(book.getBooks_publisherId());
            sheetRow.createCell(11).setCellValue(book.getBooks_createTime().toString());
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


    @ApiOperation("根据书名模糊查询")
    //模糊查询测试
    @GetMapping("/quaryName")
    public Result quaryBooks(@RequestParam String name){
        return booksService.quaryBooks(name);
    }

    @ApiOperation("根据类型进行动态查询")
    @PostMapping("/quaryType")
    public Result quaryBooks(@RequestBody Books_info bookInfo){
        return booksService.getBooksByType(bookInfo);
    }

    @ApiOperation("上架和下架图书")
    @GetMapping("/setBookStatus")
    public Result setBookStatus(@RequestParam("bookId") Integer bookId,@RequestParam("status") Integer status){
        return booksService.publishBook(bookId,status);
    }

    @ApiOperation("图书类型各类信息进行修改")
    @GetMapping("/updateBookType")
    public Result updateBookType(@RequestBody Books_info booksInfo){
        return booksService.editBooksInfo(booksInfo);
    }

    @ApiOperation("图书状态列，有货已借无货")
    @GetMapping("/BooksList")
    public List<BookStatusDTO> BooksList(){
        return booksService.BooksStatusList();
    }

    @ApiOperation("得到图书最全的信息")
    @GetMapping("/getBookAllInfo")
    public Result getBookAllInfo(@RequestParam Integer id){
        return booksService.getAllBookInfo(id);
    }
}
