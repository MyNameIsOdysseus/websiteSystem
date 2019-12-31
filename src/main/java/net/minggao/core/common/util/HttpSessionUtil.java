package net.minggao.core.common.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 处理HttpSession的工具类
 * @author liumc
 *
 */
public class HttpSessionUtil {
	//注销session
	public static void invalidate(HttpServletRequest request){
		HttpSession session = getSession(request);
		//在销毁session
		session.invalidate();
	}
	
	public static void invalidate(HttpSession session){
		//在销毁session
		session.invalidate();
	}
	
	/**
	 * 
	 * 获取session 
	 * @param request
	 * @return      
	 * @author        liumc
	 * @Date          2016-10-31 下午4:35:18
	 */
	public static HttpSession getSession(HttpServletRequest request){
		HttpSession session =request.getSession(false); 
		return null==session?request.getSession(true):session;
	}
	
	/**
	 * 
	 * 获取session中的属性 
	 * @param request
	 * @param attributeName
	 * @return      
	 * @author        liumc
	 * @Date          2016-10-31 下午4:35:38
	 */
	public static Object getSessionAttribute(HttpServletRequest request ,String attributeName){
		HttpSession session = getSession(request);
		return (null==session?null:session.getAttribute(attributeName));
	}
	
	/**
	 * 
	 * 把Session中存放的数据，转换为Map返回 
	 * @param request
	 * @return      
	 * @author        liumc
	 * @Date          2017-8-7 下午4:20:27
	 */
	public static Map getSessionAttribute2Map(HttpServletRequest request){
		Map rslt = new HashMap();
		HttpSession sess = request.getSession(false);
		java.util.Enumeration enu = sess.getAttributeNames();
		while(enu.hasMoreElements()){
			String attName = String.valueOf(enu.nextElement());
			Object attValue = sess.getAttribute(attName);
			rslt.put(attName, attValue);
		}
		return rslt;
	}
	
	/**
	 * @param request
	 * @return
	 */
	public static Map getParameter2Map(HttpServletRequest request){
		Map tmp = new HashMap();
		tmp.putAll(request.getParameterMap());
		return tmp;
	}
}
