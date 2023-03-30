package com.example.demosfw.ES;

import com.alibaba.fastjson2.JSON;
import com.example.demosfw.Controller.ESController;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * 索引服务类
 */
@Service
public class IndexTestServiceImpl implements IndexTestService {
    private final Logger logger = LoggerFactory.getLogger(ESController.class);

    @Autowired
    RestHighLevelClient restHighLevelClient;



    @Override
    public boolean indexCreate() throws Exception {
        // 1、创建 创建索引request 参数：索引名mess
        CreateIndexRequest indexRequest = new CreateIndexRequest("goods");
        // 2、设置索引的settings
/*        indexRequest.settings(H2ConsoleProperties.Settings.builder()
                .put("index.number_of_shards", 3)
                .put("index.number_of_replicas", 2)
        );*/
        // 3、设置索引的mappings
        String mapping = "{\n" +
                "\n" +
                "\t\t\"properties\": {\n" +
                "\t\t  \"brandName\": {\n" +
                "\t\t\t\"type\": \"keyword\"\n" +
                "\t\t  },\n" +
                "\t\t  \"categoryName\": {\n" +
                "\t\t\t\"type\": \"keyword\"\n" +
                "\t\t  },\n" +
                "\t\t  \"createTime\": {\n" +
                "\t\t\t\"type\": \"date\",\n" +
                "\t\t\t\"format\": \"yyyy-MM-dd HH:mm:ss\"\n" +
                "\t\t  },\n" +
                "\t\t  \"id\": {\n" +
                "\t\t\t\"type\": \"long\"\n" +
                "\t\t  },\n" +
                "\t\t  \"price\": {\n" +
                "\t\t\t\"type\": \"double\"\n" +
                "\t\t  },\n" +
                "\t\t  \"saleNum\": {\n" +
                "\t\t\t\"type\": \"integer\"\n" +
                "\t\t  },\n" +
                "\t\t  \"status\": {\n" +
                "\t\t\t\"type\": \"integer\"\n" +
                "\t\t  },\n" +
                "\t\t  \"stock\": {\n" +
                "\t\t\t\"type\": \"integer\"\n" +
                "\t\t  },\n" +
                "\t\t\"spec\": {\n" +
                "\t\t\t\"type\": \"text\",\n" +
                "\t\t\t\"analyzer\": \"ik_max_word\",\n" +
                "\t\t\t\"search_analyzer\": \"ik_smart\"\n" +
                "\t\t  },\n" +
                "\t\t  \"title\": {\n" +
                "\t\t\t\"type\": \"text\",\n" +
                "\t\t\t\"analyzer\": \"ik_max_word\",\n" +
                "\t\t\t\"search_analyzer\": \"ik_smart\"\n" +
                "\t\t  }\n" +
                "\t\t}\n" +
                "  }";
        // 4、 设置索引的别名
        // 5、 发送请求
        // 5.1 同步方式发送请求
        // 4、 设置索引的别名
        // 5、 发送请求
        // 5.1 同步方式发送请求
        IndicesClient indicesClient = restHighLevelClient.indices();
        indexRequest.mapping("_doc",mapping, XContentType.JSON);

        // 请求服务器
        CreateIndexResponse response = indicesClient.create(indexRequest, RequestOptions.DEFAULT);

        return response.isAcknowledged();
    }

    @Override
    public Map<String, Object> getMapping(String indexName) throws Exception {
        IndicesClient indicesClient = restHighLevelClient.indices();

        // 创建get请求
        GetIndexRequest request = new GetIndexRequest(indexName);
        // 发送get请求
        GetIndexResponse response = indicesClient.get(request, RequestOptions.DEFAULT);
        // 获取表结构
        Map<String, MappingMetaData> mappings = response.getMappings();
        Map<String, Object> sourceAsMap = mappings.get(indexName).getSourceAsMap();
        return sourceAsMap;
    }

    @Override
    public RestStatus addDocument() throws IOException {
        Goods goods = new Goods();
        goods.setId(1L);
        goods.setTitle("Apple iPhone 13 Pro (A2639) 256GB 远峰蓝色 支持移动联通电信5G 双卡双待手机");
        goods.setPrice(new BigDecimal("8799.00"));
        goods.setStock(1000);
        goods.setSaleNum(599);
        goods.setCategoryName("手机");
        goods.setBrandName("Apple");
        goods.setStatus(0);
        goods.setCreateTime(new Date());

        // 返回状态
        RestStatus status = null;
        try {
            // 将对象转为json
            String data = JSON.toJSONString(goods);
            // 创建索引请求对象
            IndexRequest indexRequest = new IndexRequest("goods","_doc").id(goods.getId() + "").source(data, XContentType.JSON);
            // 执行增加文档
            IndexResponse response = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);

            status = response.status();

        } catch (Exception e) {
            logger.error("添加文档失败，错误信息：" + e.getMessage());
        }
        System.out.println("添加文档响应状态：" + status);
        return status;

    }
}