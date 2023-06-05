package com.mua.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mua.entity.BookEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author ASUS
* @description 针对表【book】的数据库操作Mapper
* @createDate 2023-03-10 10:45:34
* @Entity com.mua.entity.BookEntity
*/
public interface BookMapper extends BaseMapper<BookEntity> {

    List<BookEntity> pageList(@Param("entity") BookEntity bookEntity, Page<BookEntity> result);
}




