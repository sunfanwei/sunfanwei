package com.example.demosfw.ES;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 索引服务类
 */
@Service
public class IndexTestServiceImpl implements IndexTestService {

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
        indexRequest.mapping(mapping, XContentType.JSON);

        // 请求服务器
        CreateIndexResponse response = indicesClient.create(indexRequest, RequestOptions.DEFAULT);

        return response.isAcknowledged();
    }
}