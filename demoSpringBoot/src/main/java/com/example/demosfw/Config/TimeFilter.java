package com.example.demosfw.Config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeFilter implements Filter {
    private static final Logger log = LoggerFactory.getLogger(TimeFilter.class);

    private SimpleDateFormat simpleDateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:S");
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("TimeFilter拦截的时间：{}",simpleDateFormat.format(new Date(System.currentTimeMillis())));
        chain.doFilter(request, response);
        log.info("执行完请求处理逻辑后，回到TimeFilter的时间：{}",simpleDateFormat.format(new Date(System.currentTimeMillis())));
    }
}
