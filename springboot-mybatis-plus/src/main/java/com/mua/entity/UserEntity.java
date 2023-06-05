package com.mua.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 *
 * @TableName USER
 */
@TableName(value ="MUA_USER")
@Data
public class UserEntity implements Serializable {
    /**
     * ID
     */
    @TableId(value = "ID")
    private String id;

    /**
     * 姓名
     */
    @TableField(value = "NAME")
    private String name;

    /**
     * 性别
     */
    @TableField(value = "SEX")
    private String sex;

    /**
     * 年龄
     */
    @TableField(value = "AGE")
    private Integer age;

    /**
     * 头像
     */
    @TableField(value = "IMG")
    private String img;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
