package org.gzu.userservice.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Classname: MyBatisPlusConfig
 * @Description: MyBatis Plus 配置
 * @Author: lions
 * @Datetime: 12/29/2023 12:23 AM
 */
@Configuration
@MapperScan("org.gzu.userservice.mapper")
public class MyBatisPlusConfig {

    /**
     * @Description: Mybatis-plus拦截器配置
     * @Return: Mybatis-plus拦截器Bean
     * @Author: lions
     * @Datetime: 12/29/2023 12:23 AM
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 分页插件
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }
}