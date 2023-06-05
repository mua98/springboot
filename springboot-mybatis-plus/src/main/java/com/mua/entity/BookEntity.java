package com.mua.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 *
 * @TableName book
 */
@TableName(value ="book")
@Data
public class BookEntity implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 书名
     */
    @TableField(value = "book_name")
    private String bookName;

    /**
     * 作者
     */
    @TableField(value = "author_name")
    private String authorName;

    /**
     * 介绍
     */
    @TableField(value = "book_intro")
    private String bookIntro;

    /**
     * 类型
     */
    @TableField(value = "book_type")
    private String bookType;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
