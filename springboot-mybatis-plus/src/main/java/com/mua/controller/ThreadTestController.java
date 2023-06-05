package com.mua.controller;

import cn.hutool.core.lang.UUID;
import com.mua.common.Result;
import com.mua.entity.ThreadTestEntity;
import com.mua.service.ThreadTestService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @Author: ASUS XuWei
 * @Since: 2023-03-21 下午 3:21
 * @Comment: 多线程测试
 */
@Slf4j
@RestController
public class ThreadTestController {

    @Autowired
    private ThreadTestService threadTestService;

    @PostMapping("test1")
    public Result test1() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        threadTestService.test1();

        stopWatch.stop();
        double totalTimeSeconds = stopWatch.getTotalTimeSeconds();
        log.info("插入数据时间--------->{}", totalTimeSeconds);
        return Result.data("插入数据时间" + totalTimeSeconds);
    }

    @PostMapping("test2")
    public Result test2() throws InterruptedException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        threadTestService.test2();

        stopWatch.stop();
        double totalTimeSeconds = stopWatch.getTotalTimeSeconds();
        log.info("插入数据时间--------->{}", totalTimeSeconds);
        return Result.data("插入数据时间" + totalTimeSeconds);
    }

    @PostMapping("test3")
    public Result test3() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        List<ThreadTestEntity> entityList = new ArrayList<>();
        for (int i = 0; i < 5000; i++) {
            ThreadTestEntity entity = new ThreadTestEntity();
            entity.setId((int) i);
            entity.setA(UUID.randomUUID().toString());
            entity.setB(UUID.randomUUID().toString());
            entity.setC(UUID.randomUUID().toString());
            entity.setD(UUID.randomUUID().toString());
            entity.setE(UUID.randomUUID().toString());
            entity.setF(UUID.randomUUID().toString());
            entity.setG(UUID.randomUUID().toString());
            entityList.add(entity);
        }
        List<List<ThreadTestEntity>> partition = ListUtils.partition(entityList, 500);
        CountDownLatch latch = new CountDownLatch(partition.size());
        for (List<ThreadTestEntity> listSub : partition) {
            threadTestService.test3(listSub, latch);
        }
        try {
            latch.await(); //保证之前的所有的线程都执行完成，才会走下面的；
            // 这样就可以在下面拿到所有线程执行完的集合结果
        } catch (Exception e) {
            log.error("阻塞异常:" + e.getMessage());
        }

        stopWatch.stop();
        double totalTimeSeconds = stopWatch.getTotalTimeSeconds();
        log.info("插入数据时间--------->{}", totalTimeSeconds);
        return Result.data("插入数据时间" + totalTimeSeconds);
    }

}
