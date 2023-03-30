package com.example.demosfw.ES;


import org.elasticsearch.rest.RestStatus;

import java.io.IOException;
import java.util.Map;

public interface IndexTestService {

    public boolean indexCreate() throws Exception;

    public Map<String,Object> getMapping(String indexName) throws Exception;

    public RestStatus addDocument() throws IOException;

}