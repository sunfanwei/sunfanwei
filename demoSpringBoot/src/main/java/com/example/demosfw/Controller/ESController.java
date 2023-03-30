package com.example.demosfw.Controller;

import com.alibaba.fastjson2.JSON;
import com.example.demosfw.ES.IndexTestService;
import org.elasticsearch.rest.RestStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
public class ESController {
    private final Logger logger = LoggerFactory.getLogger(ESController.class);
    @Autowired
    IndexTestService indexTestService;

    @RequestMapping("/indexCreate")
    public Object indexCreate() {
        boolean flag = false;
        try {
            flag = indexTestService.indexCreate();
            indexTestService.getMapping("goods");
        } catch (Exception e) {
            logger.error("创建索引失败，错误信息：" + e.getMessage());
        }
        System.out.println("创建索引是否成功：" + flag);
        return "success";
    }

    @RequestMapping("/getMapping")
    public Object getMapping() {
        try {
            Map<String, Object> indexMap = indexTestService.getMapping("goods");

            // 将bean 转化为格式化后的json字符串
            String pretty1 = JSON.toJSONString(indexMap);
            logger.info("索引信息：{}", pretty1);

        } catch (Exception e) {
            logger.error("获取索引失败，错误信息：" + e.getMessage());
        }
        return "success";
    }

    @RequestMapping("/addDocument")
    public Object addDocument() {
        try {
            RestStatus restStatus = indexTestService.addDocument();

            logger.info("索引信息：{}", restStatus);

        } catch (Exception e) {
            logger.error("获取索引失败，错误信息：" + e.getMessage());
        }
        return "success";
    }


}
