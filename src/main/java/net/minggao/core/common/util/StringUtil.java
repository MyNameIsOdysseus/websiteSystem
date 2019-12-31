package net.minggao.core.common.util;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sun.media.jfxmedia.logging.Logger;


/**
 * @author edision
 * @version v1.00 2012-2-6
 * modify log
 * 20181220
 * 新增一个 getGroupCommaLikeSQL 方法，获取群组like查询条件
 */
public class StringUtil {
	private static Log logger = LogFactory.getLog(StringUtil.class);
	/**
	 *判断字符串是否不为空，为空返回false
	 * @param Value
	 * @return
	 */
	public final static boolean strIsNotNull(String Value) {
		boolean Return = false;
		try {
			if (Value != null) {
				Value = Value.trim();
				if (Value.length() > 0) {
					Return = true;
				}
			}
		} catch (Exception ex) {
			Return = false;
		} finally {
			return Return;
		}
	}
	/**
	 * 判断字符串是否为空，为空返回true
	 * @param Value
	 * @return
	 */
	public static final boolean isBlank(String Value) {

		return StringUtils.isBlank(Value);
	}
	/**
	 * 判断对象类型是否不为空，为空返回false
	 * @param test
	 * @return
	 */
	public static final boolean isNotBlank(Object test) {

		if (test != null) {
			if (test instanceof String) {
				return StringUtils.isNotBlank((String) test);
			} else {
				return true;
			}

		} else {
			return false;
		}

	}
	/**
	 * 判断对象类型变量是否为空，为空(null)返回true
	 * @param o
	 * @return
	 */
	public static final boolean isEmpty(Object o) {
		if (o == null) {
			return true;
		}
		String s = null;
		if (!(o instanceof String)) {
			s = o.toString();
		} else {
			s = (String) o;
		}

		if (s == null || s.trim().length() == 0 || "null".equalsIgnoreCase(s)) {
			return true;
		}
		return false;
	}

	/** long或string类型是否为null或null字符 */
	public static final boolean isPrimaryKey(Object obj) {
		boolean Return = true;
		if (null != obj && !"null".equalsIgnoreCase(obj.toString())
				&& !StringUtil.isEmpty(obj.toString())) {
			for (int k = 0; k < obj.toString().length(); k++) {
				if (!java.lang.Character.isDigit(obj.toString().charAt(k))) {
					return false;
				}
			}
		} else {
			Return = false;
		}
		return Return;
	}

	/***************************************************************************
	 * 实现在对sql条件字段加前缀，并且转为有效查询条件
	 *
	 * @param alias
	 * @param sql
	 * @return
	 */
	public static String getPreFixWhereSQL(String alias, String sql) {
		StringBuffer sb = new StringBuffer();
		alias = (alias == null || "".equals(alias)) ? "" : alias + ".";
		if (null != sql && !"".equals(sql)) {
			sql = sql.replaceAll("( )+", " ");
			String[] mySQL = sql.split(" ");

			for (int k = 0; null != mySQL && k < mySQL.length; k++) {
				String[] keyValue = mySQL[k].split("=");
				if (keyValue.length >= 2) {
					sb.append(alias).append(keyValue[0]).append("=").append(
							keyValue[1]);
				} else {
					sb.append(mySQL[k]);
				}
				sb.append(" ");
			}
			String tempStr = sb.toString().trim().toLowerCase();
			if (!tempStr.startsWith("where")) {
				int startIndex = 0;
				if (tempStr.startsWith("and")) {
					startIndex = "and".length();
				} else if (tempStr.startsWith("or")) {
					startIndex = "or".length();
				} else if (tempStr.startsWith("in")) {
					startIndex = "in".length();
				}
				if (startIndex != 0) {
					startIndex = startIndex + 1;
				}

				return " where "
						+ sb.toString().substring(startIndex,
								sb.toString().length()).trim();
			} else {
				return " " + sb.toString().trim();
			}
		}
		return sb.toString();
	}

	/**
	 * 将组织OragnFullid分解成like sql语法
	 *
	 * @param organFullid
	 * @param prop
	 * @return
	 */
	public static String getOrganFullidLikeSQL(String organFullid, String prop,
			String link) {
		String result = "";
		if (null == organFullid || organFullid.length() == 0)
			return "";

		List list = new ArrayList();
		Pattern pattern1 = Pattern.compile("\\$[0-9]*\\$");// 人员
		Matcher matcher1 = pattern1.matcher(organFullid);
		while (matcher1.find()) {
			String str = (matcher1.group().replaceAll("\\$", ""));
			if (!"".equals(str)) {
				list.add(str);
			}
		}
		for (int k = 0; k < list.size(); k++) {
			result += prop + " like '%*" + list.get(k) + "*%' ";
			if (k != list.size() - 1) {
				result += " " + link + " ";
			}
		}
		return result;
	}

	/**
	 * 将组织OragnFullid分解成 = sql语法
	 *
	 * @param organFullid
	 * @param prop
	 * @return
	 */
	public static String getOrganFullidEqSQL(String organFullid, String prop,
			String link) {
		String result = "";
		if (null == organFullid || organFullid.length() == 0)
			return "";
		// 组织ID串以-符号分割
		String[] _arr = organFullid.split("-");
		for (int k = 0; k < _arr.length; k++) {
			result += prop + " = '" + _arr[k] + "' ";
			if (k != _arr.length - 1) {
				result += " " + link + " ";
			}
		}
		return result;
	}

	/**
	 * 将以逗号分解成like sql语法
	 *
	 * @param ids
	 * @param prop
	 * @param link
	 * @return
	 */
	public static String getCommaLikeSQL(String ids, String prop, String link) {
		String result = "";
		if (null == ids || ids.length() == 0)
			return "";
		String[] _arr = ids.split(",");
		for (int k = 0; (k < _arr.length) &&(!StringUtil.isEmpty(_arr[k])); k++) {
			result += prop + " like '%" + _arr[k] + "%' ";
			if (k != _arr.length - 1) {
				result += " " + link + " ";
			}
		}
		return result;
	}
	
	/**
	 * 获取群组like语句
	 * Date 2018-12-20
	 * @param grpIds   群组id串，如：ID，ID，ID，ID，。。。ID
	 * @param prop     对于的群组的属性或数据库字段名
	 * @param link
	 * @return
	 */
	public static String getGroupCommaLikeSQL(String grpIds, String prop, String link) {
		String result = "";
		if (null == grpIds || grpIds.length() == 0)
			return "";
		String[] _arr = grpIds.split(",");
		for (int k = 0; (k < _arr.length) &&(!StringUtil.isEmpty(_arr[k])); k++) {
			result += prop + " like '%@" + _arr[k] + "@%' ";
			if (k != _arr.length - 1) {
				result += " " + link + " ";
			}
		}
		return result;
	}

	/**
	 * 将以逗号分解成= sql语法
	 *
	 * @param ids
	 * @param prop
	 * @param link
	 * @return
	 */
	public static String getCommaEqSQL(String ids, String prop, String link) {
		String result = "";
		if (null == ids || ids.length() == 0)
			return "";
		String[] _arr = ids.split(",");
		for (int k = 0; k < _arr.length; k++) {
			result += prop + " = '" + _arr[k] + "' ";
			if (k != _arr.length - 1) {
				result += " " + link + " ";
			}
		}
		return result;
	}
	/**
	 * 以逗号分解成Long数组
	 * @param ids
	 * @return
	 */
	public static Long[] getLongId(String ids) {
		if (ids.endsWith(",")) {
			ids = ids.substring(0, ids.lastIndexOf(","));
		}
		String[] idArray = ids.split(",");
		Long[] result = new Long[idArray.length];
		for (int k = 0; k < idArray.length; k++) {
			result[k] = Long.valueOf(idArray[k]);
		}

		return result;
	}

	/**
	 * 将字符串处理成标准的in条件sql
	 *
	 * @param ids
	 * @return
	 */
	public static String getInSqlStr(String ids) {
		if (null != ids && ids.startsWith(",")) {
			ids = ids.substring(1, ids.length());
		}
		if (null != ids && ids.endsWith(",")) {
			ids = ids.substring(0, ids.length() - 1);
		}
		return ids;
	}
	
	

	/**
	 * 将字符串处理成标准的in条件sql
	 *
	 * @param ids
	 * @return
	 */
	public static String getInSqlStr(String[] ids) {
		String result = "";
		for (int k = 0; k < ids.length; k++) {
			if (!"".equals(ids[k])) {
				result += ids[k] + ",";
			}
		}
		if (!"".equals(result)) {
			result = result.substring(0, result.length() - 1);
		}
		return result;
	}

	
	/**
	 * 过滤list列表重复的数据(list装载如果数组，则以数组0索引主为主键过滤)
	 *
	 * @param list
	 * @return
	 */
	public static List getFilterRepeatList(List list) {
		List result = new ArrayList();
		String idStr = "";
		int listSize = list.size();
		for (int k = 0; k < listSize; k++) {
			Object obj = list.get(k);

			if (obj.getClass().isArray()) {
				Object[] array = (Object[]) obj;
				if (null != array && array.length > 0) {
					if (idStr.indexOf(array[0].toString()) == -1) {
						result.add(obj);
						idStr += array[0].toString() + ",";
					}
				}

			} else if (obj instanceof String) {
				if (idStr.indexOf(obj.toString()) == -1) {
					result.add(obj);
					idStr += obj.toString() + ",";
				}
			} else {
				result.add(obj);
			}
		}
		return result;
	}

	/***************************************************************************
	 * 过滤ID串中重复的ID(ID串以逗号连接)
	 *
	 * @param str
	 * @return
	 */
	public static String getFilterRepeatString(String str) {
		String result = "";
		if (null != str && !"".equals(str)) {
			String[] arr = str.split(",");
			for (int k = 0; k < arr.length; k++) {
				if ("".equals(arr[k]))
					continue;
				if (result.indexOf(arr[k]) == -1) {
					result += arr[k] + ",";
				}
			}
		}
		if (!"".equals(result)) {
			result = result.substring(0, result.length() - 1);
		} else {
			result = str;
		}
		return result;
	}

	/**
	 * 过滤文本中html的标签(html转换成文本)
	 *
	 * @param inputString
	 * @return
	 */
	public static String Html2Text(String inputString) {
		String htmlStr = inputString; // 含html标签的字符串
		String textStr = "";
		java.util.regex.Pattern p_script;
		java.util.regex.Matcher m_script;
		java.util.regex.Pattern p_style;
		java.util.regex.Matcher m_style;
		java.util.regex.Pattern p_html;
		java.util.regex.Matcher m_html;
		java.util.regex.Pattern p_html1;
		java.util.regex.Matcher m_html1;
		try {
			String regEx_script = "<[//s]*?script[^>]*?>[//s//S]*?<[//s]*?///[//s]*?script[//s]*?>"; // 定义script的正则表达式{或<script[^>]*?>[//s//S]*?<///script>
			String regEx_style = "<[//s]*?style[^>]*?>[//s//S]*?<[//s]*?///[//s]*?style[//s]*?>"; // 定义style的正则表达式{或<style[^>]*?>[//s//S]*?<///style>
			String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
			String regEx_html1 = "<[^>]+";
			p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
			m_script = p_script.matcher(htmlStr);
			htmlStr = m_script.replaceAll(""); // 过滤script标签

			p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
			m_style = p_style.matcher(htmlStr);
			htmlStr = m_style.replaceAll(""); // 过滤style标签

			p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
			m_html = p_html.matcher(htmlStr);
			htmlStr = m_html.replaceAll(""); // 过滤html标签

			p_html1 = Pattern.compile(regEx_html1, Pattern.CASE_INSENSITIVE);
			m_html1 = p_html1.matcher(htmlStr);
			htmlStr = m_html1.replaceAll(""); // 过滤html标签

			textStr = htmlStr;
		} catch (Exception e) {
			//System.err.println("Html2Text: " + e.getMessage());
		}
		return textStr;// 返回文本字符串
	}

	/**
	 * 解析$111$结构的字符串(如人员IdString)
	 *
	 * @param str
	 * @return
	 */
	public static List getSplit44(String str) {
		List result = new ArrayList();
		Pattern pattern1 = Pattern.compile("\\$(.+?)\\$");
		Matcher matcher1 = pattern1.matcher(str);
		while (matcher1.find()) {
			String id = matcher1.group().replaceAll("\\$", "");
			result.add(id);
		}
		return result;
	}

	/**
	 * 解析@@结构的字符串(如群组)
	 *
	 * @param str
	 * @return
	 */
	public static List getSplit22(String str) {
		List result = new ArrayList();
		Pattern pattern1 = Pattern.compile("\\@(.+?)\\@");
		Matcher matcher1 = pattern1.matcher(str);
		while (matcher1.find()) {
			String id = matcher1.group().replaceAll("\\@", "");
			result.add(id);
		}
		return result;
	}

	/**
	 * 解析**结构的字符串(如组织)
	 *
	 * @param str
	 * @return
	 */
	public static List getSplit88(String str) {
		List result = new ArrayList();
		Pattern pattern1 = Pattern.compile("\\*(.+?)\\*");
		Matcher matcher1 = pattern1.matcher(str);
		while (matcher1.find()) {
			String id = matcher1.group().replaceAll("\\*", "");
			result.add(id);
		}
		return result;
	}

	/**
	 * 截取长度字符串
	 *
	 * @param str
	 * @param len
	 * @return
	 */
	public static String subString(String str, int len, String more) {

		if (str != null && str.trim().length() > 0 && len > 0) {
			int _len = str.trim().length();

			str = str.substring(0, (len > _len ? _len : len));
			str += (len < _len ? (more == null ? "" : more) : "");
		}
		return str;
	}

	/**
	 * 获取URL参数，去掉method之后的参数
	 *
	 * @param request
	 * @return
	 */
	public static String getURLQueryString(HttpServletRequest request) {
		Map pMap = request.getParameterMap();
		java.util.Iterator properties = pMap.keySet().iterator();
		String result = "";
		String key = "";
		String value = "";
		while (properties.hasNext()) {
			key = (String) properties.next();
			if ("".equals(key))
				continue;
			if (key.indexOf("?") != -1)
				continue;
			if ("method".equals(key))
				continue;

			Object valueobj = pMap.get(key);
			if (null == valueobj) {
				value = "";
			} else if (valueobj instanceof String[]) {
				String[] values = (String[]) valueobj;
				for (int k = 0; k < values.length; k++) {
					value = values[k] + ",";
				}
				value = value.substring(0, value.length() - 1);
			} else {
				value = valueobj.toString();
			}
			if (value.indexOf("?") != -1)
				continue;
			result += key + "=" + value + "&";
		}
		if (!"".equals(result)) {
			result = result.substring(0, result.length() - 1);
		}

		return result;
	}

	/**
	 * 获取URL参数，支持排除指定的参数
	 *
	 * @param request
	 * @return
	 */
	public static String getURLQueryString(HttpServletRequest request,
			String[] rejectParam) {
		Map m = request.getParameterMap();
		java.util.Iterator properties = m.keySet().iterator();
		String result = "";
		String key = "";
		String value = "";
		while (properties.hasNext()) {
			key = (String) properties.next();
			if ("".equals(key))
				continue;
			if (key.indexOf("?") != -1)
				continue;
			boolean hasGoTo = false;
			for (int k = 0; k < rejectParam.length; k++) {
				if (rejectParam[k].equals(key)) {
					hasGoTo = true;// 需要跳出
					break;
				}
			}
			if (hasGoTo)
				continue;

			Object valueobj = m.get(key);
			if (null == valueobj) {
				value = "";
			} else if (valueobj instanceof String[]) {
				String[] values = (String[]) valueobj;
				for (int k = 0; k < values.length; k++) {
					value = values[k] + ",";
				}
				value = value.substring(0, value.length() - 1);
			} else {
				value = valueobj.toString();
			}
			if (value.indexOf("?") != -1)
				continue;
			result += key + "=" + value + "&";
		}
		if (!"".equals(result)) {
			result = result.substring(0, result.length() - 1);
		}
		return result;
	}
	
	
	
	/***
	 * 解析url格式字符中参数名称参数值
	 * @param urlQuery
	 * @return
	 */
	public static Map getURLQueryMap(String queryString, String enc){
		Map result = new HashMap();
		if(!isEmpty(queryString)){
			if(queryString.indexOf("?")>-1) queryString = queryString.substring(queryString.indexOf("?")+1,queryString.length());
			//System.out.println("queryString="+queryString);
			String[] tmpArr = queryString.split("\\&");
			for(String s:tmpArr) {
				String[] arr = s.split("=");
				result.put(arr[0], arr[1]);
			}
			/*
			 int ampersandIndex = 0, lastAmpersandIndex = 0;
			 String subStr, param, value;
			 String[] paramPair, values, newValues;
			 do {
				 ampersandIndex = queryString.indexOf('&', lastAmpersandIndex) + 1;
				 if (ampersandIndex > 0) { 
					 subStr = queryString.substring(lastAmpersandIndex, ampersandIndex - 1);
					 lastAmpersandIndex = ampersandIndex;
				 }else{
					 subStr = queryString.substring(lastAmpersandIndex); 
				 }
				 paramPair = subStr.split("=");
				 param = paramPair[0];
				 value = paramPair.length == 1 ? "" : paramPair[1];
				 if(!isEmpty(enc)){
					 try {
						value = URLDecoder.decode(value, enc);
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				 }
				 if (result.containsKey(param)) {
					 values = (String[])result.get(param);
					 int len = values.length;
					 newValues = new String[len + 1];
					 System.arraycopy(values, 0, newValues, 0, len);
					 newValues[len] = value;
				 }else{
					 newValues = new String[] {value};
				 }
				 result.put(param, newValues); 
			 }while (ampersandIndex > 0); */
		}
		return result;
	}
	
	/**
	 * 判断当前用户是否有适用范围权限(主要用于批示意见的适用范围)
	 *
	 * @param curUserId
	 * @param userOrgIdstring
	 * @param scopeIdString
	 * @return
	 */
	public static boolean hasScopeRight(String curUserId,
			String userOrgIdstring, String scopeIdString) {
		boolean result = false;
		if (isEmpty(scopeIdString)) {// 适用范围为空即有权限
			result = true;
		} else if (scopeIdString.indexOf("$" + curUserId + "$") != -1) {
			result = true;
		} else {

			Pattern pattern1 = Pattern.compile("\\$(.+?)\\$");//
			Matcher matcher1 = pattern1.matcher(userOrgIdstring);
			while (matcher1.find()) {
				String orgId = (matcher1.group().replaceAll("\\$", ""));
				if (scopeIdString.indexOf("*" + orgId + "*") != -1) {
					return true;
				}
			}
		}
		return result;
	}
	/**
	 * 判断是否是整数
	 * @param sCheck
	 * @return
	 */
	public static final boolean isNumeric(String sCheck) {
		if (sCheck == null)
			return false;
		if (sCheck.length() == 0)
			return false;
		String numStr = "0123456789";
		for (int i = 0; i < sCheck.length(); ++i)
			if (numStr.indexOf(sCheck.charAt(i)) == -1)
				return false;
		
		return true;
	}
	/**
	 * 是否是int整数
	 * @param sCheck
	 * @return
	 */
	public static final boolean isInt(String sCheck) {
		if (isEmpty(sCheck))
			return false;
		if (sCheck.substring(0, 1).equals("-"))
			sCheck = sCheck.substring(1);
		if (!(isNumeric(sCheck)))
			return false;
		return (Long.parseLong(sCheck) <= 2147483647L);
	}
	/**
	 * 判断是否是float数字
	 * @param sCheck
	 * @return
	 */
	public static final boolean isFloat(String sCheck) {
		if (isEmpty(sCheck))
			return false;
		if (sCheck.indexOf(".") != -1) {
			int dotPos = sCheck.indexOf(".");
			sCheck = sCheck.substring(0, dotPos) + sCheck.substring(dotPos + 1);
		}
		return isNumeric(sCheck);
	}
	/**
	 * 如果对象类型为null返回替换字符串
	 * @param obj
	 * @param sReplace
	 * @return
	 */
	public static final String getString(Object obj, String sReplace) {
		if (obj == null)
			return sReplace;
		String sRet = obj.toString();
		if (sRet.length() == 0)
			return sReplace;

		return sRet;
	}
	/**
	 * 字符串如果为null返回空字符
	 */
	public static final String getString(String sCheck) {
		return getString(sCheck, "");
	}
	/**
	 * 截取float小数点前整数部分，如果为空返回替换值
	 * @param sCheck
	 * @param iReplace
	 * @return
	 */
	public static final int getInt(String sCheck, int iReplace) {
		if (sCheck == null)
			return iReplace;
		if ((!(sCheck.equals(""))) && (sCheck.indexOf(".") != -1))
			sCheck = sCheck.substring(0, sCheck.indexOf("."));
		if (!(isInt(sCheck)))
			return iReplace;

		return Integer.parseInt(sCheck);
	}
	/**
	 * 判断是否float并且返回值，非float返回0.0
	 * @param sCheck
	 * @param nReplace
	 * @return
	 */
	public static final float getFloat(String sCheck, float nReplace) {
		if (!(isFloat(sCheck)))
			return nReplace;

		return Float.parseFloat(sCheck);
	}
	/**
	 * 将ISO-8859-1编码转换成GBK
	 * @param sOrigin
	 * @return
	 */
	public static final String str2Chs(String sOrigin) {
		int i;
		try {
			i = 0;
			byte[] arrayByte = sOrigin.getBytes("iso8859-1");
			for (i = 0; i < arrayByte.length; ++i)
				if (arrayByte[i] + 0 < 0)
					return new String(arrayByte, "GBK");

			arrayByte = sOrigin.getBytes("GBK");
			for (i = 0; i < arrayByte.length; ++i)
				if (arrayByte[i] + 0 < 0)
					return new String(arrayByte, "GBK");
		} catch (Exception e) {
		}
		return sOrigin;
	}
	/**
	 * 阿拉伯数值转成中文数值
	 * @param sNum
	 * @return
	 */
	public static final String num2Chs(String sNum) {
		String sNumStr = "零一二三四五六七八九十";
		String sAfterDot = "";
		int posDot = sNum.indexOf(".");
		if (posDot != -1) {
			for (int i = posDot + 1; i < sNum.length(); ++i) {
				String c = sNum.substring(i, i + 1);
				sAfterDot = sAfterDot
						+ sNumStr.substring(Integer.parseInt(c), Integer
								.parseInt(c) + 1);
			}

			sNum = sNum.substring(0, posDot);
		}
		String sReturn = "";
		if (sNum.equals("0")) {
			sReturn = "零";
			if (sAfterDot.length() > 0)
				sNum = "";
			else
				return sReturn;
		}
		if (sNum.length() > 8) {
			sReturn = sReturn
					+ num2Chs(new StringBuffer().append(
							sNum.substring(0, sNum.length() - 8)).append("")
							.toString()) + "亿";
			if (sNum.substring(sNum.length() - 9, sNum.length() - 8)
					.equals("0"))
				sReturn = sReturn + "零";
			sNum = sNum.substring(sNum.length() - 8);
		}
		if ((sNum.length() > 4) && (sNum.length() <= 8)) {
			sReturn = sReturn + num2Chs(sNum.substring(0, sNum.length() - 4))
					+ "万";
			if (sNum.substring(sNum.length() - 4, sNum.length() - 4)
					.equals("0"))
				sReturn = sReturn + "零";
			sNum = sNum.substring(sNum.length() - 4);
		}
		for (int i = 0; i < sNum.length(); ++i) {
			String c = sNum.substring(i, i + 1);
			if (!(c.equals("0"))) {
				sReturn = sReturn
						+ sNumStr.substring(Integer.parseInt(c), Integer
								.parseInt(c) + 1);
				if (i < sNum.length() - 1)
					sReturn = sReturn
							+ "个十百千万".substring(sNum.length() - i - 1, sNum
									.length()
									- i);
			} else {
				if (sReturn == "")
					break;

				for (int j = i; (j > 0) && (sNum.substring(j, j + 1) == "0"); ++j)
					if (j < sNum.length()) {
						sReturn = sReturn + "零";
						if (i < j - 1)
							i = j - 1;
					}
			}
		}
		for (; sReturn.indexOf("零零") != -1; sReturn = replace(sReturn, "零零",
				"零"))
			;
		if ((sReturn.endsWith("零")) && (sNum.length() > 1))
			sReturn = sReturn.substring(0, sReturn.length() - 1);
		return sReturn
				+ ((sAfterDot.length() <= 0) ? "" : new StringBuffer().append(
						"点").append(sAfterDot).toString());
	}
	/**
	 * 替换字符串中子字符串
	 * @param sOrigin    原字符串
	 * @param sPattern   被替换部分
	 * @param sReplaceBy 替换后的内容
	 * @return
	 */
	public static final String replace(String sOrigin, String sPattern,
			String sReplaceBy) {
		if (sOrigin == null)
			return "";
		int s = 0;
		int e = 0;
		StringBuffer sbResult = new StringBuffer();
		while ((e = sOrigin.indexOf(sPattern, s)) >= 0) {
			sbResult.append(sOrigin.substring(s, e));
			sbResult.append(sReplaceBy);
			s = e + sPattern.length();
		}
		sbResult.append(sOrigin.substring(s));
		return sbResult.toString();
	}
	/**
	 * 分解字符串成数组
	 * @param sExpression
	 * @param sDelimiter
	 * @return
	 */
	public static String[] split(String sExpression, String sDelimiter) {
		String[] aRet = null;
		int length = 0;
		int index = -1;
		int index1 = 0;
		int index2 = 0;
		do {
			++length;
			index = sExpression.indexOf(sDelimiter, index + 1);
		} while (index != -1);
		aRet = new String[length];
		length = 0;
		index1 = -1;
		index2 = -1;
		while (true) {
			index1 = sExpression.indexOf(sDelimiter, index2 + 1);
			if (index1 == -1) {
				aRet[length] = sExpression.substring(index2 + 1);
				break;
			}
			aRet[length] = sExpression.substring(index2 + 1, index1);
			++length;
			index2 = index1;
		}
		return aRet;
	}
	/**
	 * 在字符串前添加复制的字符串
	 * @param sOrigin
	 * @param sRepeat
	 * @param nLen
	 * @return
	 */
	public static final String lpad(String sOrigin, String sRepeat, int nLen) {
		if (sOrigin == null)
			sOrigin = "";
		for (; sOrigin.length() < nLen; sOrigin = sRepeat + sOrigin)
			;
		return sOrigin;
	}
	/**
	 * 在字符串后添加复制的字符串
	 * @param sOrigin
	 * @param sRepeat
	 * @param nLen
	 * @return
	 */
	public static final String rpad(String sOrigin, String sRepeat, int nLen) {
		if (sOrigin == null)
			sOrigin = "";
		for (; sOrigin.length() < nLen; sOrigin = sOrigin + sRepeat)
			;
		return sOrigin;
	}
	/**
	 * 去掉两端空格
	 * @param sOrigin
	 * @return
	 */
	public static final String trim(String sOrigin) {
		if (sOrigin == null)
			return null;

		return sOrigin.trim();
	}
	/**
	 * 截取左边字符串（按个数）
	 * @param sOrigin
	 * @param iLen
	 * @return
	 */
	public static String left(String sOrigin, int iLen) {
		sOrigin = getString(sOrigin);
		if (isEmpty(sOrigin))
			return "";
		if (sOrigin.length() <= iLen)
			return sOrigin;

		return sOrigin.substring(0, iLen);
	}
	/**
	 * 截取右边字符串（按个数）
	 * @param sOrigin
	 * @param iLen
	 * @return
	 */
	public static String right(String sOrigin, int iLen) {
		sOrigin = getString(sOrigin);
		if (isEmpty(sOrigin))
			return "";
		if (sOrigin.length() <= iLen)
			return sOrigin;

		return sOrigin.substring(sOrigin.length() - iLen);
	}
	/**
	 * 将字符串转换成unicode码，用%隔开
	 * @param str
	 * @return
	 */
	public static final String escape(String str) {
		String stmp = "";
		for (int i = 0; i < str.length(); ++i) {
			String s;
			int c = str.charAt(i);
			if (c < 128) {
				for (s = Integer.toString(c, 16); s.length() < 2; s = "0" + s)
					;
				stmp = stmp + "%" + s;
			} else {
				for (s = Integer.toString(c, 16); s.length() < 4; s = "0" + s)
					;
				stmp = stmp + "%u" + s;
			}
		}

		return stmp;
	}
	/**
	 * 将unicode转换正常字符
	 * @param s
	 * @return
	 */
	public static final String unescape(String s) {
		String stmp = "";
		for (int i = 0; i < s.length(); ++i) {
			String c = s.substring(i, i + 1);
			if (c.equals("%")) {
				if (i < s.length() - 1) {
					String sHex;
					String c2 = s.substring(i + 1, i + 2);
					if (c2.equalsIgnoreCase("u")) {
						sHex = s.substring(i + 2, i + 6);
						stmp = stmp + (char) Integer.parseInt(sHex, 16);
						i += 5;
					} else {
						sHex = s.substring(i + 1, i + 3);
						stmp = stmp + (char) Integer.parseInt(sHex, 16);
						i += 2;
					}
				} else {
					stmp = stmp + "%";
				}
			} else
				stmp = stmp + c;

		}

		return stmp;
	}
	/**
	 * 两数相除
	 * @param sNum1
	 * @param sNum2
	 * @param nDotNum 小数保留位数
	 * @return
	 */
	public static String getDivideValue(String sNum1, String sNum2, int nDotNum) {
		double nNum1;
		try {
			nNum1 = Double.parseDouble(sNum1);
			double nNum2 = Double.parseDouble(sNum2);
			double nDivideValue = nNum1 / nNum2;
			String sDivideValue = nDivideValue + "";
			if (sDivideValue.indexOf("E") > 0) {
				String sTmp = sDivideValue.substring(
						sDivideValue.indexOf("E") + 2, sDivideValue.length());
				sDivideValue = sDivideValue.substring(0, sDivideValue
						.indexOf("E"));
				sDivideValue = replace(sDivideValue, ".", "");
				for (int i = 1; i <= Integer.parseInt(sTmp); ++i)
					if (i == Integer.parseInt(sTmp))
						sDivideValue = "0." + sDivideValue;
					else
						sDivideValue = "0" + sDivideValue;
			}

			if (sDivideValue.indexOf(".") + 1 + nDotNum <= sDivideValue
					.length() - 1)
				return sDivideValue.substring(0, sDivideValue.indexOf(".") + 1
						+ nDotNum);

			return sDivideValue;
		} catch (Exception e) {
		}
		return null;
	}
	/**
	 * 计算字符串长度，中文字符算两位
	 * @param sTemp
	 * @return
	 */
	public static int getLength(String sTemp) {
		if (sTemp.equals(""))
			return 0;
		int nRet = 0;
		for (int i = 0; i < sTemp.length(); ++i)
			if ((sTemp.charAt(i) > 0) && (sTemp.charAt(i) < 128))
				++nRet;
			else
				nRet += 2;

		return nRet;
	}
	/**
	 * 将URL特殊字符转换成uncode
	 * @param url
	 * @return
	 */
	public static String URLEncode(String url) {
		if (url == null) {
			return null;
		}

		String sReturn = url.toString();
		sReturn = replace(sReturn, "%", "%25");
		sReturn = replace(sReturn, "?", "%3F");
		sReturn = replace(sReturn, "&", "%26");
		sReturn = replace(sReturn, "\\", "%22");
		sReturn = replace(sReturn, "'", "%27");
		sReturn = replace(sReturn, "=", "%3D");
		sReturn = replace(sReturn, "+", "%2B");
		return sReturn;
	}
	/**
	 * 获取字符的ascii码(单个字符)
	 * @param str
	 * @return
	 */
	public static final int getAsc(String str) {
		str = new String(str.substring(0, 1));
		int nRet = 0;
		if ((str.charAt(0) > 0) && (str.charAt(0) < 128))
			nRet = str.charAt(0);
		try {
			byte[] b = str.getBytes();
			nRet = (b[0] + 256) * 256 + b[1] + 256 - 65536;
		} catch (Exception e) {
		}
		return nRet;
	}
	/**
	 * 四舍六入取整数 5保留
	 * @param sNum
	 * @return
	 */
	public static String round(String sNum) {
		return round(sNum, true);
	}
	/**
	 * 四舍六入取整数
	 * @param sNum
	 * @param bNotRetainHalf
	 * @return
	 */
	public static String round(String sNum, boolean bNotRetainHalf) {
		if (sNum == null)
			return "0";
		if (isInt(sNum))
			return sNum;
		if (isFloat(sNum)) {
			int nPos = sNum.indexOf(".");
			if (nPos == -1)
				return sNum;
			if (nPos == sNum.length() - 1)
				sNum = sNum + "0";
			if (nPos == 0)
				sNum = "0" + sNum;
			int nDex = getInt(sNum.substring(nPos + 1, nPos + 2), 0);
			if (nDex < 5)
				return sNum.substring(0, nPos);
			if (nDex == 5)
				return sNum.substring(0, nPos) + ".5";
			if (nDex > 5)
				return "" + (getInt(sNum.substring(0, nPos), 0) + 1);
		}
		return sNum;
	}
	
	/**
	 * 获取修改页面多选项id新增和删除项
	 * @param oldStr 如:1234,2333
	 * @param newStr 如:332,4444
	 * @return String[0]:新增;String[1]-册除
	 */
	public static String[] getDiffeIdString(String oldStr,String newStr){
		String[] result = new String[]{"",""};		
		String addIdStr = "";
		String removeIdStr = "";
		
		List oldList = new ArrayList();
		List newList = new ArrayList();		
		
		if(isEmpty(oldStr)){
			addIdStr = newStr;
		}else{
			String[] oldArray = oldStr.split(",");
			for(int k=0;k<oldArray.length;k++){
				if("".equals(oldArray[k]))continue;
				oldList.add(oldArray[k]);
			}			
		}
		if(isEmpty(newStr)){
			removeIdStr = oldStr;
		}else{
			String[] newArray = newStr.split(",");
			for(int k=0;k<newArray.length;k++){
				if("".equals(newArray[k]))continue;
				newList.add(newArray[k]);
			}	
		}
		
		if(oldList.size()>0 && newList.size()>0){
			//是否有删除的
			removeIdStr = "";			
			for(int k=0;k<oldList.size();k++){
				if(!newList.contains(oldList.get(k))){
					removeIdStr += oldList.get(k) + ",";
				}
			}
			//是否有新增
			addIdStr = "";
			for(int k=0;k<newList.size();k++){
				if(!oldList.contains(newList.get(k))){
					addIdStr += newList.get(k) + ",";
				}
			}
		}
		if(addIdStr.endsWith(",")){
			addIdStr = addIdStr.substring(0, addIdStr.length()-1);
		}
		if(removeIdStr.endsWith(",")){
			removeIdStr = removeIdStr.substring(0, removeIdStr.length()-1);
		}
		result[0] = addIdStr;
		result[1] = removeIdStr;
		return result;
		
	}
	
	/**
	 * 将in条件折分，以解决超过1000个id值报错的问题
	 * @param ids
	 * @param field
	 * @return
	 */
	public static String getInSql(String ids,String field){
		int maxLen = 900;
		StringBuffer sb = new StringBuffer();
		String[] idArray = ids.split(",");
		List agentList = new ArrayList();
		for(int k=0;k<idArray.length;k++){
			if("".equals(idArray[k])) continue;
			agentList.add(idArray[k]);
		}
		for(int i=0;i<agentList.size();i++){
		   if(i==agentList.size()-1)
		    sb.append(agentList.get(i)).append(")");
		   else if(i%maxLen==0&&i>0)
		    sb.append(agentList.get(i))
		    .append(") or " + field + " in (");
		   else
		    sb.append(agentList.get(i)).append(",");
		}
		
		return field + " in (-1,"+sb;
	}
	/**
	 * 将not in条件折分，以解决超过1000个id值报错的问题
	 * @param ids
	 * @param field
	 * @return
	 */
	public static String getNotInSql(String ids,String field){
		int maxLen = 900;
		StringBuffer sb = new StringBuffer();
		String[] idArray = ids.split(",");
		List agentList = new ArrayList();
		for(int k=0;k<idArray.length;k++){
			if("".equals(idArray)) continue;
			agentList.add(idArray[k]);
		}
		for(int i=0;i<agentList.size();i++){
		   if(i==agentList.size()-1)
		    sb.append(agentList.get(i)).append(")");
		   else if(i%maxLen==0&&i>0)
		    sb.append(agentList.get(i))
		    .append(") or " + field + " not in (");
		   else
		    sb.append(agentList.get(i)).append(",");
		}
		
		return field + " not in (-1,"+sb;
	}
	/**
	 * 将IdString中指定id值和id值对应的name删除掉($108750$$100155$$107857$ 对应"中美,杜丽,郭德福,"删除$100155$)
	 * @param idString
	 * @param nameString
	 * @param removeId
	 * @return
	 */
	public static String[] removeIdAndNameString(String idString ,String nameString,String removeId){
		String[] result = new String[]{idString,nameString};
		if(isEmpty(idString) || isEmpty(nameString)) return result; 
		StringBuffer idBuffer = new StringBuffer();
		StringBuffer nameBuffer = new StringBuffer();		
		String[] ids = idString.split("\\$\\$");
		int removeIndex = -1;
		String _id = "";
		for(int k=0;k<ids.length;k++){
			_id = ids[k].replaceAll("\\$", "");
			
			if(_id.equals(removeId.replaceAll("\\$", ""))){
				removeIndex = k;
			}else{
				idBuffer.append("$" + _id + "$");
			}
		}
		String[] names = nameString.split(",");
		for(int k=0;k<names.length;k++){
			if(removeIndex != k){
				nameBuffer.append(names[k] + ",");
			}
		}
		result[0] = idBuffer.toString();
		result[1] = nameBuffer.toString();
		return result;
	}
	
	public static String splitWith(String src, String regex, String other)
	  {
	    String retString = "";
	    for (int i = 0; i < src.length() - 1; i++) {
	      String traStr = src.substring(i, i + 1);
	      int countRegex = 0;
	      if ((traStr.equals(regex)) || (countRegex % 2 != 0)) {
	        if (traStr.equals(regex))
	          countRegex++;
	        int k = src.indexOf(regex, i + 1);
	        if (k != -1) {
	          String dis = src.substring(i, k + 1);
	          if (checkOther(dis, other)) {
	            retString = retString + dis;
	          } else {
	            src = src.substring(k, src.length());
	            retString = retString + splitWith(src, regex, other);
	            return retString;
	          }
	          i = k;
	        }
	      }
	    }
	    return retString;
	  }

	  private static boolean checkOther(String dist, String other) {
	    for (int i = 0; i < other.length(); i++) {
	      if (dist.indexOf(other.substring(i, i + 1)) != -1)
	        return false;
	    }
	    return true;
	  }
	  public static String splitOrgIdString(String src, String regex, String other) {
	    String retString = "";
	    for (int i = 0; i < src.length() - 1; i++) {
	      String traStr = src.substring(i, i + 1);
	      int countRegex = 0;
	      if ((traStr.equals(regex)) || (countRegex % 2 != 0)) {
	        if (traStr.equals(regex))
	          countRegex++;
	        int k = src.indexOf(regex, i + 1);
	        if (k != -1) {
	          String dis = src.substring(i, k + 1);
	          if (checkOther(dis, other)) {
	            retString = retString + dis;
	          } else {
	            src = src.substring(k, src.length());
	            retString = retString + splitWith(src, regex, other);
	            return retString;
	          }
	          i = k;
	        }
	      }
	    }
	    return retString;
	  }

	  public static String splitOrgIdString(String idstr)
	  {
	    return splitOrgIdString(idstr, "$", "_");
	  }

	  public static Long getOrgIdByOrgIdstring(String orgIdString, int level)
	  {
	    Long result = null;
	    String idStr = splitOrgIdString(orgIdString);
	    Pattern pattern1 = Pattern.compile("\\$[0-9]*\\$");
	    Matcher matcher1 = pattern1.matcher(idStr);
	    int _level = 0;
	    while (matcher1.find()) {
	      String str = matcher1.group().replaceAll("\\$", "");
	      if (("".equals(str)) || 
	        (level != _level)) continue;
	      result = getLong(str);
	      break;
	    }

	    return result;
	  }

	  public static String getOrgIdStr2InSql(String idStr)
	  {
	    String result = "";
	    Pattern pattern1 = Pattern.compile("\\$[0-9]*\\$");
	    Matcher matcher1 = pattern1.matcher(idStr);
	    while (matcher1.find()) {
	      String str = matcher1.group().replaceAll("\\$", "");
	      if (!"".equals(str)) {
	        result = result + str + ",";
	      }
	    }
	    if (!"".equals(result)) {
	      result = result.substring(0, result.length() - 1);
	    }
	    return result;
	  }

	  public static String splitOrgIdStr2InSql(String src)
	  {
	    return getOrgIdStr2InSql(splitOrgIdString(src, "$", "_"));
	  }

	  public static String getValue(Object obj)
	  {
	    if (obj == null)
	      return "";
	    if ((obj instanceof String)) {
	      return (String)obj;
	    }
	    return obj.toString();
	  }

	  public static Integer getInteger(Object obj)
	  {
	    if (obj == null)
	      return null;
	    if ((obj instanceof String)) {
	      String data = String.valueOf(obj).trim();
	      if ("".equals(data))
	        return null;
	      if (isInt(data)) {
	        return Integer.valueOf(data);
	      }
	      return null;
	    }
	    if ((obj instanceof BigDecimal))
	      return Integer.valueOf(((BigDecimal)obj).intValue());
	    if (isInt(getValue(obj))) {
	      return Integer.valueOf(getValue(obj));
	    }
	    return null;
	  }

	  public static Short getShort(Object obj)
	  {
	    if (obj == null)
	      return null;
	    if ((obj instanceof String)) {
	      String data = String.valueOf(obj).trim();
	      if ("".equals(data))
	        return null;
	      if (isInt(data)) {
	        return Short.valueOf(data);
	      }
	      return null;
	    }
	    if ((obj instanceof BigDecimal))
	      return Short.valueOf(((BigDecimal)obj).shortValueExact());
	    if (isInt(getValue(obj))) {
	      return Short.valueOf(getValue(obj));
	    }
	    return null;
	  }

	  public static Long getLong(Object obj)
	  {
	    if (obj == null)
	      return null;
	    if ((obj instanceof String)) {
	      String data = getValue(obj).trim();
	      if ("".equals(data))
	        return null;
	      if (isInt(data)) {
	        return Long.valueOf(data);
	      }
	      return null;
	    }
	    if ((obj instanceof BigDecimal))
	      return new Long(((BigDecimal)obj).longValue());
	    if (isInt(getValue(obj))) {
	      return Long.valueOf(getValue(obj));
	    }
	    return null;
	  }

	  public static long getlong(Object obj)
	  {
	    Long result = getLong(obj);
	    return result == null ? 0L : result.longValue();
	  }

	  public static int getint(Object obj)
	  {
	    Integer result = getInteger(obj);
	    return result == null ? 0 : result.intValue();
	  }
	  
	  
	
	public static String TrimAndNull(String str){
		return ((null==str) || "".equals(str) || "null".equals(str))?"":str.trim();
	}
	
	public static String TrimAndNull2String(Object str){
		return ((null==str) || "".equals(String.valueOf(str)) || "null".equals(String.valueOf(str)))?"":String.valueOf(str).trim();
	}
	
	/**
	 * 判断字符串中是否包含中文
	 * @param str
	 * 待校验字符串
	 * @return 是否为中文
	 * @warn 不能校验是否为中文标点符号 
	 */
	public static boolean isContainChinese(String str) {
		if(StringUtil.isEmpty(str)) return false;
	 Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
	 Matcher m = p.matcher(str);
	 if (m.find()) {
	  return true;
	 }
	 return false;
	}
	
	/**
	 * 去除字符串中任何位置的空格
	 * date 2018年11月23日
	 * @author liumc
	 * @param str
	 * @return
	 */
	public static String moveBlank(String str) {
		String rslt = str;
		int flag = rslt.indexOf(" ");
		while(flag>-1) {
			rslt = rslt.replaceAll(" ", "");
			flag = rslt.indexOf(" ");
		}
		return rslt;
	}
	
	/**
	 * 解析orgFullId，获取orgId的数组
	 * @param orgFullId,如：_500000000$14354$_500000000$37896$_500000000$74863$_500000000$75061$
	 * @return
	 */
	public static String[] diffOrgFullIdstr(String orgFullId) {
		String[] rslt = null;
		String[] tmp = orgFullId.split("\\$");
		List<String> list = new ArrayList<String>();
		for(String s:tmp) {
			if(!s.startsWith("_") && s.trim().length()>0) {
				list.add(s);
			}
		}
		rslt = new String[list.size()];
		int i=0;
		for(String s:list) {
			rslt[i] = s;
			i++;
		}
		list.clear();
		list = null;
		return rslt;
	}
}
