package com.example.demosfw.Controller;

import com.example.demosfw.ES.IndexTestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
        } catch (Exception e) {
            logger.error("创建索引失败，错误信息：" + e.getMessage());
        }
        System.out.println("创建索引是否成功：" + flag);
        return "success";
    }


}
