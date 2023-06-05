package com.mua.controller;

import com.mua.common.Result;
import com.mua.entity.CityEntity;
import com.mua.service.CityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: ASUS Xu Wei
 * @Since: 2023-03-07 下午 5:54
 * @Comment: 递归查询（菜单）
 */
@Slf4j
@RestController
@RequestMapping("city")
public class CityController {

    @Autowired
    private CityService cityService;

    @GetMapping("")
    public Result city(){
        List<CityEntity> city = cityService.getCity();
        return Result.data(city);
    }

}
