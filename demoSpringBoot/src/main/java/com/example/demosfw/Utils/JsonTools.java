package com.example.demosfw.Utils;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class JsonTools {
	
	public static Log log = LogFactory.getLog(JsonTools.class);
	
    private static ObjectMapper objectMapper = new ObjectMapper();
	/**
     * 
     * @author JSON工具类
     * @param 
     * 
     */
    
    
    /***
     * 将JSON对象序列化为JSON文本
     * @param object string
     * @return
     */
    public static String toObjString(Object object)
    {
    	JSONObject jsonObject = JSONObject.fromObject(object);
        return jsonObject.toString();
    } 
    
    /***
     * 将对象转换为JSON对象数组
     * @param object
     * @return
     */
    public static JSONArray toJSONArray(Object object)
    {
        return JSONArray.fromObject(object);
    }

    /***
     * 将对象转换为JSON对象
     * @param object
     * @return
     */
    public static JSONObject toJSONObject(Object object)
    {
        return JSONObject.fromObject(object);
    }

    /***
     * 将对象转换为HashMap
     * @param object
     * @return
     */
    public static HashMap toHashMap(Object object)
    {
        HashMap<String, Object> data = new HashMap<String, Object>();
        JSONObject jsonObject = JsonTools.toJSONObject(object);
        Iterator it = jsonObject.keys();
        while (it.hasNext())
        {
            String key = String.valueOf(it.next());
            Object value = jsonObject.get(key);
            data.put(key, value);
        }

        return data;
    }
    
  
    public static <T> T json2Bean(String json, Class<T> beanClass) {  
        try {  
            return objectMapper.readValue(json, beanClass);  
        } catch (Exception e) {  
        	log.error(e);
            new Exception("解析参数出错");
        }  
        return null;  
    }  

    
    public static <T> List<T> json2List(String json, Class<T> beanClass) {  
        try {  
            return (List<T>)objectMapper.readValue(json, getCollectionType(List.class, beanClass));  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return null;  
    }  
    
    public static String getJsonFromObject(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return objectMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);     
    }   
}
