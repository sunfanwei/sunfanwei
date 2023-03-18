package com.example.demosfw;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public  class MyTask  implements Callable<Map<String, Integer>> {
    private List<String> phoneList;
    //构造函数，用来向task中传递任务的参数
    public  MyTask(List<String> phoneList) {
        this.phoneList=phoneList;
    }
    //任务执行的动作
    @Override
    public Map<String, Integer> call() {
        Map<String, Integer> res = new HashMap<String, Integer>();
        System.out.println("任务执行:获取到结果 :"+phoneList);
        return  res;
    }
}
