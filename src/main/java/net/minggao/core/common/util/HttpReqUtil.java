package net.minggao.core.common.util;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class HttpReqUtil {
	/**
	 * 把request提交的参数都重新带入到request.setAttribute中
	 * @param request  
	 */
		public static void bringParams(HttpServletRequest request){
		    java.util.Enumeration enu = request.getParameterNames();
		    while(enu.hasMoreElements()){
		    	String paraName = String.valueOf(enu.nextElement());
		    	String paraValue = request.getParameter(paraName);
		    	String chParaValue = paraValue;
//		    	try{
//		    		chParaValue = new String(paraValue.getBytes("ISO8859_1"),"UTF-8");
//		    	}catch(Exception ee){}
		    	if(StringUtil.isContainChinese(chParaValue)) request.setAttribute(paraName, chParaValue);
		    	else request.setAttribute(paraName, paraValue);
		    }
		}
		
		/**
		 * 获取request中传递过来的参数的值
		 * @param request http请求
		 * @param pName   参数名
		 * @return
		 */
		public static String getP(HttpServletRequest request,String pName){
			String paraValue =  request.getParameter(pName);
			String chParaValue = paraValue;
//	    	try{
//	    		chParaValue = new String(paraValue.getBytes("ISO8859_1"),"UTF-8");
//	    	}catch(Exception ee){}
	    	if(StringUtil.isContainChinese(chParaValue))  return chParaValue;
	    	else return paraValue;
		}
		
		/**
		 * 获取request中传递过来的Attribute
		 * @param request http请求
		 * @param vName 属性名
		 * @return
		 */
		public static Object getV(HttpServletRequest request,String vName){
			return request.getAttribute(vName);
		}
		
		public static String getPandV(HttpServletRequest request,String pname){
			String rslt = getP(request,pname);
			if(null == rslt) rslt = request.getAttribute(pname)==null?null:request.getAttribute(pname).toString();
			return rslt;
		}
		
		/**
		 * 获取页面表单参数
		 * @param request
		 * @return
		 */
		public static Map getReqMap(HttpServletRequest request){
			Map rslt = new HashMap();
			java.util.Enumeration enu = request.getParameterNames();
		    while(enu.hasMoreElements()){
		    	String paraName = String.valueOf(enu.nextElement());
		    	String pavaValue = request.getParameter(paraName);
		    	String chParaValue = pavaValue;
//		    	try{
//		    		chParaValue = new String(pavaValue.getBytes("ISO8859_1"),"UTF-8");
//		    	}catch(Exception ee){}
		    	if(StringUtil.isContainChinese(chParaValue)) rslt.put(paraName, chParaValue);
		    	else rslt.put(paraName, pavaValue);
		    }
			return rslt;
		}
		
		/**
		 * 
		 * 根据request获取request传递的parameter到map，同时也把session中的attribute放到map中。 
		 * @param request
		 * @return      
		 * @author        liumc
		 * @Date          2017-1-9 下午2:24:07
		 */
		public static Map getReqMapWtihSession(HttpServletRequest request){
			Map rslt = new HashMap();
			java.util.Enumeration enu = request.getParameterNames();
		    while(enu.hasMoreElements()){
		    	String paraName = String.valueOf(enu.nextElement());
		    	String[] objarr = request.getParameterValues(paraName);
		    	if(null!=objarr){
		    		rslt.put(paraName, objarr);
		    	}else{
		    		rslt.put(paraName, request.getParameter(paraName));
		    	}
		    }
		    //处理session
		    Map session = new HashMap();
		    
		    session.put("curUserId", HttpSessionUtil.getSessionAttribute(request, "userId")+"");//用户id
		    session.put("curOrgName", HttpSessionUtil.getSessionAttribute(request, "orgName")+"");//组织名称
		    session.put("curUserName", HttpSessionUtil.getSessionAttribute(request, "userName")+"");//用户名称
		    session.put("curUserEngName", HttpSessionUtil.getSessionAttribute(request, "userEnglishname")+"");//英文名
		    session.put("curUserAccount", HttpSessionUtil.getSessionAttribute(request, "userAccount")+"");//帐号
		    session.put("curUserCode", HttpSessionUtil.getSessionAttribute(request, "userCode")+"");  //工号
		    session.put("domainId", HttpSessionUtil.getSessionAttribute(request, "domainId")+"");
		    session.put("curUserDutyLevel", HttpSessionUtil.getSessionAttribute(request, "dutyLevel")+"");
		    session.put("curOrgId", HttpSessionUtil.getSessionAttribute(request, "orgId")+"");
		    session.put("curOrgFullName", HttpSessionUtil.getSessionAttribute(request, "orgFullName")+"");
		    session.put("curOrgFullId", HttpSessionUtil.getSessionAttribute(request, "orgFullId")+"");
		    session.put("curOrgIdString", HttpSessionUtil.getSessionAttribute(request, "orgIdString")+"");
		    session.put("rootPath", HttpSessionUtil.getSessionAttribute(request, "rootPath")+"");
		    session.put("appTitle", HttpSessionUtil.getSessionAttribute(request, "appTitle")+"");
		    session.put("serverIP", HttpSessionUtil.getSessionAttribute(request, "serverIP")+"");
		    session.put("userIP", HttpSessionUtil.getSessionAttribute(request, "userIP")+"");
		    session.put("curUserGrpId", HttpSessionUtil.getSessionAttribute(request, "groupId")+"");
		    session.put("curOrgCode", HttpSessionUtil.getSessionAttribute(request, "orgCode")+"");
		    session.put("curUserIdcard", HttpSessionUtil.getSessionAttribute(request, "idcard")+"");//身份证
		    session.put("curUserBusinessPhone", HttpSessionUtil.getSessionAttribute(request, "userBusinessPhone")+""); //商务手机号
		    session.put("curUserDutyName", HttpSessionUtil.getSessionAttribute(request, "dutyName")+""); //职务
		    session.put("curUserPosition", HttpSessionUtil.getSessionAttribute(request, "userPosition")+""); //岗位
		    session.put("curOrgSimpleName", HttpSessionUtil.getSessionAttribute(request, "orgSimpleName")+""); //组织简称
		    
		    session.put("curOrgEngName", HttpSessionUtil.getSessionAttribute(request, "orgEnglishName")+"");
		    rslt.put("session",session);
			return rslt;
		}
		
		/**
		 * 把页面上的参数转为对应的PO或VO文件，PO和VO类必须满足Bean的条件，包括无参构造函数，每个属性都有get和set方法
		 * @param request
		 * @param clazz
		 * @return
		 */
		public static Object getBean(HttpServletRequest request,Class valueObjectClass){
			Map map = getReqMap(request);
			Object valueObject = null;
			try {
				valueObject = valueObjectClass.newInstance();
				PropertyDescriptor[] voPropertyDescriptor = Introspector.getBeanInfo(valueObjectClass, Object.class).getPropertyDescriptors();
				 for (int i = 0; i < voPropertyDescriptor.length; i++) {//属性描述
					 String name = voPropertyDescriptor[i].getName();//属性名
					 if(map.get(name) !=null){//map里找到对应的属性值
						 Object[] parameter = new Object[1];
						 parameter[0] = map.get(name);
						// log.info("transMapTO.debug[" + name + "==" + parameter[0] + "]");
						 
						 Method writeMethodInFormBean = voPropertyDescriptor[i].getWriteMethod();
						 //数据类型处理
						 Class[] paramType = writeMethodInFormBean.getParameterTypes();
				         if(null == paramType || paramType.length == 0 || paramType.length >1)continue;
				         String writeParamType = paramType[0].getName();
				         if(null == parameter[0])continue;
				        
			        	  if("java.lang.Short".equals(writeParamType)){
			        		  parameter[0] = Short.valueOf(StringUtil.isEmpty(parameter[0])?"0":parameter[0].toString());
			        	  }else if("java.lang.Long".equals(writeParamType)){
			        		  parameter[0] = Long.valueOf(StringUtil.isEmpty(parameter[0])?"0":parameter[0].toString());
			        	  }else if("java.lang.Integer".equals(writeParamType)){
			        		  parameter[0] = Integer.valueOf(StringUtil.isEmpty(parameter[0])?"0":parameter[0].toString());
			        	  }else if("java.lang.Double".equals(writeParamType)){
			        		  parameter[0] = Double.valueOf(StringUtil.isEmpty(parameter[0])?"0":parameter[0].toString());
			        	  }else if("java.lang.Boolean".equals(writeParamType)){
			        		  parameter[0] = Boolean.valueOf(StringUtil.isEmpty(parameter[0])?"false":parameter[0].toString());
			        	  }else{
			        		  parameter[0] = parameter[0].toString();
			        	  }
			        	  writeMethodInFormBean.invoke(valueObject,parameter);
					 }

				 }
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IntrospectionException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			return valueObject;
		}
}
