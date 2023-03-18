package com.example.demosfw.Thead;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

public class CountDownLatchExample {

    public static void main(String[] args) throws Exception {

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5,5,60,
                TimeUnit.SECONDS,new LinkedBlockingDeque<Runnable>(10),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.DiscardOldestPolicy());

        Map<Object, Object> map = new HashMap<>();

        Integer poolNumber = 5;
        long start = System.currentTimeMillis();
        //初始化会运行的线程数 这里测试固定为 5 个线程，一定要和下面的 i 循环的变量相同
        CountDownLatch countDownLatch = new CountDownLatch( poolNumber );
        for (int i = 1; i <= poolNumber ; i++) {
            Integer time = i * 1000;
            threadPoolExecutor.execute(()->{
                try {
                    System.out.println("time:"+time);
                    //模拟接口调用耗时
                    //Thread.sleep( time );
                    //比如这里拿到每一次接口调用返回的结果
                    map.put(time,time);
                }catch (Exception e){
                    //异常信息
                }finally {
                    //线程计数器-1
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
        System.out.println("总耗时:"+ (System.currentTimeMillis() - start) +" ms");
        //拿到所有的结果，然后往下处理
        System.out.println("map数量:"+map.size());
        threadPoolExecutor.shutdown();
    }
}
