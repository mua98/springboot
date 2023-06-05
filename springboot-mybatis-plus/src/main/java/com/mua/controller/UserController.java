package com.mua.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mua.common.Result;
import com.mua.entity.UserEntity;
import com.mua.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: ASUS XuWei
 * @Since: 2023-03-23 上午 11:03
 * @Comment: 多数据源Oracle测试
 */
@Slf4j
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("")
    public Result getUser(@ModelAttribute UserEntity userEntity,
                          @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum, @RequestParam(value =
            "pageSize", defaultValue = "10") Integer pageSize) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Page<UserEntity> list = userService.pageList(userEntity,pageNum, pageSize);

        stopWatch.stop();
        double totalTimeSeconds = stopWatch.getTotalTimeSeconds();
        log.info("查询数据时间--------->{}", totalTimeSeconds);
        return Result.data(list);

    }

}
