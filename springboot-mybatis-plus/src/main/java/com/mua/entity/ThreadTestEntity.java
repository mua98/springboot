package com.mua.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 *
 * @TableName thread_test
 */
@TableName(value ="thread_test")
@Data
public class ThreadTestEntity implements Serializable {
    /**
     *
     */
    @TableId(value = "id")
    private Integer id;

    /**
     *
     */
    @TableField(value = "A")
    private String a;

    /**
     *
     */
    @TableField(value = "B")
    private String b;

    /**
     *
     */
    @TableField(value = "C")
    private String c;

    /**
     *
     */
    @TableField(value = "D")
    private String d;

    /**
     *
     */
    @TableField(value = "E")
    private String e;

    /**
     *
     */
    @TableField(value = "F")
    private String f;

    /**
     *
     */
    @TableField(value = "G")
    private String g;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
