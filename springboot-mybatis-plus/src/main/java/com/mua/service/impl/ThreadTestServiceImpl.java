package com.mua.service.impl;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.thread.NamedThreadFactory;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mua.entity.ThreadTestEntity;
import com.mua.mapper.ThreadTestMapper;
import com.mua.service.ThreadTestService;
import org.apache.commons.collections4.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author ASUS
 * @description 针对表【thread_test】的数据库操作Service实现
 * @createDate 2023-03-21 15:20:49
 */
@Service
public class ThreadTestServiceImpl extends ServiceImpl<ThreadTestMapper, ThreadTestEntity> implements ThreadTestService {

    @Autowired
    private ThreadTestMapper testMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void test1() {
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
        //mybatisPlus提供的批量保存方法，数字代表每几条数据提交一次事务，默认1000
        saveBatch(entityList, 1000);
    }

    /**
     * @Transactional对于多线程是控制不了所有的事务的
     * Spring实现事务的原理是通过ThreadLocal把数据库连接绑定到当前线程中，
     * 同一个事务中数据库操作使用同一个jdbc connection，新开启的线程获取
     * 不到当前jdbc connection。
     */
    @Override
    // @Transactional(rollbackFor = Exception.class)
    public void test2() throws InterruptedException {
        //手动创建线程池，注意你 数据库连接池的 允许连接数量，别超过了就行。
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(5, 5, 30, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(10),
                //isDaemon 设置线程是否是守护线程，true的话，主线程结束，new的线程就不会继续工作
                new NamedThreadFactory("执行线程", false), (r, executor) -> System.out.println("拒绝" + r));

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
        //拆分list，将其拆分成5份，然后上面线程池创建也是5个核心线程，刚好执行
        List<List<ThreadTestEntity>> partition = ListUtils.partition(entityList, 1000);
        //使用CountDownLatch保证所有线程都执行完成
        CountDownLatch latch = new CountDownLatch(5);
        partition.forEach(item -> {
            poolExecutor.execute(() -> {
                saveBatch(item, 1000);
                latch.countDown();
            });
        });
        latch.await();
        // 也可以这么写，设定超时时间
        //latch.await(100,TimeUnit.SECONDS);
        //关闭线程池
        poolExecutor.shutdown();
    }

    @Override
    @Async("asyncServiceExecutor") // 这里进行标注为异步任务，在执行此方法的时候，会单独开启线程来执行(并指定线程池的名字)
    public void test3(List<ThreadTestEntity> listSub, CountDownLatch countDownLatch) {
        try {
            log.warn("start executeAsync");
            //异步线程要做的事情
            saveBatch(listSub);
            log.warn("end executeAsync");
        } finally {
            countDownLatch.countDown();// 很关键, 无论上面程序是否异常必须执行countDown,否则await无法释放
        }
    }
}




