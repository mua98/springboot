package com.mua.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mua.entity.CityEntity;

import java.util.List;

/**
 * @Author: ASUS Xu Wei
 * @Since: 2023-03-07 下午 5:55
 * @Comment:
 */
public interface CityService extends IService<CityEntity> {
    List<CityEntity> getCity();
}
