package com.mua.controller;

import com.mua.common.Result;
import com.mua.entity.BookEntity;
import com.mua.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: ASUS Xu Wei
 * @Since: 2023-03-10 上午 10:58
 * @Comment: 分页查询
 */
@RestController
@RequestMapping("book")
public class BookController {

    @Autowired
    private BookService bookService;

    /**
     * 条件构造器
     */
    @GetMapping("pageBook")
    public Result pageBook(@ModelAttribute BookEntity bookEntity, @RequestParam(value = "pageNum",
            defaultValue = "1") Integer pageNum,
                           @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        return Result.data(bookService.pageBook(bookEntity, pageNum, pageSize));
    }

    /**
     * sql查询
     */
    @GetMapping("pageList")
    public Result pageList(@ModelAttribute BookEntity bookEntity, @RequestParam(value = "pageNum",
            defaultValue = "1") Integer pageNum,
                           @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        return Result.data(bookService.pageList(bookEntity, pageNum, pageSize));
    }
}
