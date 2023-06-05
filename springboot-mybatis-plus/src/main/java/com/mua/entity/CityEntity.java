package com.mua.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

/**
 * @Author: ASUS Xu Wei
 * @Since: 2023-03-07 下午 5:47
 * @Comment:
 */
@Data
@TableName("city")
public class CityEntity {

    /**
     * id
     */
    private Integer id;

    /**
     * 父id
     */
    private Integer parentId;

    /**
     * 名称
     */
    private String cname;

    /**
     * 0: 中国
     * 1:省级行政区
     * 2:县级行政区
     */
    private Integer ctype;

    /**
     * 子级
     */
    @TableField(exist = false)
    private List<CityEntity> children;
}
