package com.example.demosfw.Thead;

import java.util.*;
import java.util.concurrent.*;

public class TestMain {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        new  TestMain().exec();
    }



    void exec() throws InterruptedException, ExecutionException{
        //进行异步任务列表
        List<FutureTask<Map<String, Integer>>> futureTasks = new ArrayList<FutureTask<Map<String, Integer>>>();
        //线程池 初始化十个线程 和JDBC连接池是一个意思 实现重用
        ExecutorService executorService;
        executorService = Executors.newFixedThreadPool(10);
        long start = System.currentTimeMillis();
        /*//类似与run方法的实现 Callable是一个接口，在call中手写逻辑代码
        Callable<Map<String, Integer>> callable = new Callable<Map<String, Integer>>() {
            public Map<String, Integer> call() {
                Map<String, Integer> res = new HashMap<String, Integer>();
                System.out.println("任务执行:获取到结果 :"+res);
                return  res;
            }
        };*/
        List<MyTask> myTaskList = new ArrayList<>();
        for(int i=0;i<10;i++){
            //创建一个异步任务
            List<String> phoneList = new ArrayList<>();
            MyTask myTask = new MyTask(phoneList);
            myTaskList.add(myTask);
            FutureTask<Map<String, Integer>> futureTask = new FutureTask<Map<String, Integer>>(myTask);
            //提交异步任务到线程池，让线程池管理任务 特爽把。
            //由于是异步并行任务，所以这里并不会阻塞
            executorService.submit(futureTask);
            futureTasks.add(futureTask);
        }

        /*invokeAny取得第一个方法的返回值,当第一个任务结束后，会调用interrupt方法中断其它任务。
        invokeAll等线程任务执行完毕后,取得全部任务的结果值。*/

        List<Future<Map<String, Integer>>> futureList = executorService.invokeAll(myTaskList);
        for (Future<Map<String, Integer>> future : futureList) {
            Map<String, Integer> handle = future.get();
        }

        Map<String, Integer> resultMap = new HashMap<>();
        for (FutureTask<Map<String, Integer>> futureTask : futureTasks) {
            //futureTask.get() 得到我们想要的结果
            //ExecutorService的execute是没有返回值的，使用这种用法需要注意的是FutureTask的get方法会一直等待结果的返回，如果get的调用顺序在execute之前的话，那么程序将会停止在get这里。
            //该方法有一个重载get(long timeout, TimeUnit unit) 第一个参数为最大等待时间，第二个为时间的单位
            resultMap.putAll(futureTask.get());
        }
        long end = System.currentTimeMillis();
        System.out.println("线程池的任务全部完成:结果为:"+resultMap+"，main线程关闭，进行线程的清理");
        System.out.println("使用时间："+(end-start)+"ms");
        //清理线程池
        executorService.shutdown();
    }
}