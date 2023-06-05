package com.mua.service;

import com.mua.entity.ThreadTestEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
* @author ASUS
* @description 针对表【thread_test】的数据库操作Service
* @createDate 2023-03-21 15:20:49
*/
public interface ThreadTestService extends IService<ThreadTestEntity> {

    void test1();

    void test2() throws InterruptedException;

    void test3(List<ThreadTestEntity> listSub, CountDownLatch countDownLatch);
}
