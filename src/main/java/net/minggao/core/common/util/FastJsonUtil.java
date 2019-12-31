package net.minggao.core.common.util;

import java.util.Map;

import com.alibaba.fastjson.JSON;

public class FastJsonUtil {
	/**
	 * 
	 * Json字符串转换为Map类型对象，如果失败，则返回null对象 
	 * @param json
	 * @return      
	 * @author        liumc
	 * @Date          2017-9-11 下午3:46:17
	 */
	public static Map json2Map(String json){
		Map map = JSON.parseObject(json, Map.class);
		return map;
	}
	public static String obj2Json(Object obj){
//		JSON.toJSONString(object, true)
		return JSON.toJSONString(obj,true);
	}
	public String getEmptyJson(){
		return "{}";
	}
	public static void  main(String[] args){
		//json2map
	////	String json = "{\"access_token\":\"ACCESS_TOKEN\",\"expires_in\":7200}";
//		String json = "{\"total\":23000,\"count\":10000,\"data\":{\"openid\":[\"OPENID10001\",\"OPENID10002\",\"OPENID20000\"]},\"next_openid\":\"OPENID20000\"}";
//		Map map = JSON.parseObject(json, Map.class);
//		System.out.println("map : "+map);
//		System.out.println("map.total = "+map.get("total"));
//		System.out.println("map.count = "+map.get("count"));
//		System.out.println("map.data = "+map.get("data"));
//		System.out.println("map.next_openid = "+map.get("next_openid"));
//		System.out.println("map.data.openid = "+((Map)map.get("data")).get("openid"));
//		JSONArray ss = (JSONArray) (((Map)map.get("data")).get("openid"));
//		for(int i=0;i<ss.size();i++) System.out.println("ss["+i+"] = "+ss.get(i));
		
		//obj2Json
//		User u = new User();
//		String uString = obj2Json(u);
//		System.out.println("user.json = "+uString);
		
	}

}
