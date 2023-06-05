package com.mua.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mua.entity.BookEntity;

/**
 * @author ASUS
 * @description 针对表【book】的数据库操作Service
 * @createDate 2023-03-10 10:45:34
 */
public interface BookService extends IService<BookEntity> {

    Page<BookEntity> pageBook(BookEntity bookEntity, Integer pageNum, Integer pageSize);

    Page<BookEntity> pageList(BookEntity bookEntity, Integer pageNum, Integer pageSize);

}
