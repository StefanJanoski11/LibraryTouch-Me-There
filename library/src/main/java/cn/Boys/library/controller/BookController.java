package cn.Boys.library.controller;

import cn.Boys.library.dto.Result;
import cn.Boys.library.entity.Books;
import cn.Boys.library.enums.ResultEnum;
import cn.Boys.library.mapper.BooksMapper;
import cn.Boys.library.service.impl.BooksServiceImpl;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    BooksMapper booksMapper;
    @Autowired
    BooksServiceImpl booksService;

    @PostMapping("/insert")
    public Result insertBook(Books book){
        int i = booksMapper.insertBook(book);
        /*将图书插入到数据库*/
        if (i <= 0){
            return new Result(null,ResultEnum.NOT_ACCEPTABLE);
        }
        /*将图书存放到redis*/
        if (!booksService.putBooksInRedis(book)){
            return new Result(null,ResultEnum.SERVER_ERROR);
        }
        return new Result(book,ResultEnum.CREATED);
    }
    /*批量导出图书信息*/
    @GetMapping("/download")
    public String download(HttpServletResponse response) throws IOException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String filename = LocalDate.now().format(dtf) +"_LIBRARY.xlsx";
        filename = URLDecoder.decode(filename,"UTF-8");
        String[] head = {"编号","类型编号","名称","作者","剩余量","状态","上架时间","日点击量","周点击量","月点击量","上架管理员id","产生时间"};
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
        for (Books book :booksList) {
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
        try{
            os = response.getOutputStream();
            response.flushBuffer();
            workbook.write(os);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            os.close();
        }
        System.out.println(filename);
        return "下载文件成功！";
    }
}
