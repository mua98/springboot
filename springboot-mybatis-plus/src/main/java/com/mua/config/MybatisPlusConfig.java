package com.mua.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description
 * @Author: ASUS 徐伟
 * @Data: 2022-08-24 0:01
 * @Comment: mybatis-plus 插件配置
 */
@Configuration
public class MybatisPlusConfig {

    /**
     * 分页插件 配置
     *
     * @return page
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 添加分页插件
        PaginationInnerInterceptor pageInterceptor = new PaginationInnerInterceptor();
        // 设置请求的页面大于最大页后操作，true调回到首页，false继续请求。默认false
        // pageInterceptor.setOverflow(false);
        // 单页分页条数限制，默认无限制
        pageInterceptor.setMaxLimit(100L);
        // 设置数据库类型
        // pageInterceptor.setDbType(DbType.MYSQL);
        interceptor.addInnerInterceptor(pageInterceptor);
        return interceptor;
    }
}
