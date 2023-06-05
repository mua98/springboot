package com.mua.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mua.entity.BookEntity;
import com.mua.mapper.BookMapper;
import com.mua.service.BookService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ASUS
 * @description 针对表【book】的数据库操作Service实现
 * @createDate 2023-03-10 10:45:34
 */
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, BookEntity> implements BookService {

    @Autowired
    private BookMapper bookMapper;

    /**
     * 通过条件构造器查询
     *
     * @param bookEntity 通过书名，作者进行模糊查询
     * @param pageNum    页数 默认为 1
     * @param pageSize   页大小 默认为 10
     * @return 返回实体类
     */
    @Override
    public Page<BookEntity> pageBook(BookEntity bookEntity, Integer pageNum, Integer pageSize) {
        Page<BookEntity> result = new Page<>(pageNum, pageSize);
        QueryWrapper<BookEntity> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(bookEntity.getBookName()))
            wrapper.lambda().like(BookEntity::getBookName, bookEntity.getBookName());
        if (StringUtils.isNotBlank(bookEntity.getAuthorName()))
            wrapper.lambda().like(BookEntity::getAuthorName, bookEntity.getAuthorName());
        return bookMapper.selectPage(result, wrapper);
    }

    /**
     * 通过xml sql查询
     *
     * @param bookEntity 通过书名，作者进行模糊查询
     * @param pageNum    页数 默认为 1
     * @param pageSize   页大小 默认为 10
     * @return 返回实体类
     */
    @Override
    public Page<BookEntity> pageList(BookEntity bookEntity, Integer pageNum, Integer pageSize) {
        Page<BookEntity> result = new Page<>(pageNum, pageSize);
        List<BookEntity> list = bookMapper.pageList(bookEntity, result);
        result.setRecords(list);
        return result;
    }
}




