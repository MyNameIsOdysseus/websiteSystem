package net.minggao.core.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
/**
 * 
 * json工具类
 * @author         liumc
 * @version        V1.0  
 * @Date           2017-3-29 下午1:55:04
 */
public class GsonUtil {
	Log logger = LogFactory.getLog(getClass());
	
	public static String toJson(Object obj){
		Gson gson = new GsonBuilder().serializeNulls().disableHtmlEscaping().create(); //避免中文转义为unicode类型;//new Gson();
		return gson.toJson(obj);
	}
	public static String toJsonWithUnicode(Object obj){
		Gson gson = new Gson(); //中文转义为unicode
		return gson.toJson(obj);
	}
	
	public static Object fromJson(String json,Class clazz){
		Object rslt = null;
		Gson gson = new GsonBuilder().disableHtmlEscaping().create(); //避免中文转义为unicode类型;//new Gson();
		rslt = gson.fromJson(json, clazz);
		return rslt;
	}
	
	public static Object fromJsonWithUnicode(String json,Class clazz){
		Object rslt = null;
		Gson gson = new Gson(); 
		rslt = gson.fromJson(json, clazz);
		return rslt;
	}
	
	public static void  main(String[] args){
////		String json = "{\"access_token\":\"ACCESS_TOKEN\",\"expires_in\":7200}";
//		String json = "{\"total\":23000,\"count\":10000,\"data\":{\"openid\":[\"OPENID10001\",\"OPENID10002\",\"OPENID20000\"]},\"next_openid\":\"OPENID20000\"}";
		GsonUtil gu = new GsonUtil();
//		Map jsonMap = (Map) gu.fromJson(json, java.util.Map.class); 
//		gu.logger.info("jsonMap : "+jsonMap);
//
//		gu.logger.info("jsonMap.get(total) : "+jsonMap.get("total"));
//		gu.logger.info("jsonMap.get(count) : "+jsonMap.get("count"));
//		gu.logger.info("jsonMap.get(next_openid) : "+jsonMap.get("next_openid"));
//		gu.logger.info("jsonMap.get(data) : "+jsonMap.get("data").getClass());
//		
//		Map data = (Map)jsonMap.get("data");
//		gu.logger.info("data.get(openid) : "+data.get("openid"));
		
		List list = new ArrayList();
		Map m2 = new HashMap();
		Map m3 = new HashMap();
		
		List dList = new ArrayList();
		Map dm = new HashMap();
		dm.put("试题id", "试题idValue");
		dList.add(dm);
		m2.put("list", dList);
		m3.put("list", dList);
		
		list.add(m2);
		list.add(m3);
		
		//System.out.println("json = "+gu.toJson(list));
		
	}
}
