package com.example.demosfw.Config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Bean
    public FilterRegistrationBean TimeFilter(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        TimeFilter timeFilter = new TimeFilter();
        filterRegistrationBean.setFilter(timeFilter);
        // 添加拦截路径
        filterRegistrationBean.addUrlPatterns("/filter/*");
        Set<String> path = new HashSet<>();
        // 多路径情况
        // path.add("/filter/test1");
        // path.add("/filter/test2");
        // filterRegistrationBean.setUrlPatterns(path);
        // 设置过滤器的执行顺序
        filterRegistrationBean.setOrder(1);
        return filterRegistrationBean;
    }
}

