package net.minggao.core.framework;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.minggao.core.common.util.FastJsonUtil;
import net.minggao.core.common.util.GsonUtil;
import net.minggao.core.common.util.HttpSessionUtil;
import net.minggao.core.common.util.TokenUtil;
import net.minggao.core.framework.validate.ValidatorUtil;

/**
 * 
 * 控制器类的基类
 * 
 * 如果在url中包含中文字符都参数值，请使用类型转换如下：
 * String value = new String(request.getParameter("pname").getBytes("ISO8859_1"),"UTF-8")
 * @author         liumc
 * @version        V1.0  
 * @Date           2017-3-29 上午10:15:37
 */
public class BaseController {
	/**
	 * 
	 * 校验网页提交过来的数据的bean是否合法，使用的是validator进行校验
	 * 目前支持的注解标签如下：
	 * 
	 *<p> ● @Null被注释的元素必须为 null
		  ● @NotNull被注释的元素必须不为 null
		  ● @AssertTrue被注释的元素必须为 true@AssertFalse被注释的元素必须为 false@Min(value)被注释的元素必须是一个数字，其值必须大于等于指定的最小值
		  ● @Max(value)被注释的元素必须是一个数字，其值必须小于等于指定的最大值
		  ● @DecimalMin(value)被注释的元素必须是一个数字，其值必须大于等于指定的最小值
		  ● @DecimalMax(value)被注释的元素必须是一个数字，其值必须小于等于指定的最大值
		  ● @Size(max, min)被注释的元素的大小必须在指定的范围内
		  ● @Digits (integer, fraction)被注释的元素必须是一个数字，其值必须在可接受的范围内
		  ● @Past被注释的元素必须是一个过去的日期
		  ● @Future被注释的元素必须是一个将来的日期
		  ● @Pattern(value)被注释的元素必须符合指定的正则表达式
     </p>
     1. Bean Validation 中内置的 constraint
           注解                                      作用
	@Valid	被注释的元素是一个对象，需要检查此对象的所有字段值
	@Null	被注释的元素必须为 null
	@NotNull	被注释的元素必须不为 null
	@AssertTrue	被注释的元素必须为 true
	@AssertFalse	被注释的元素必须为 false
	@Min(value)	被注释的元素必须是一个数字，其值必须大于等于指定的最小值
	@Max(value)	被注释的元素必须是一个数字，其值必须小于等于指定的最大值
	@DecimalMin(value)	被注释的元素必须是一个数字，其值必须大于等于指定的最小值
	@DecimalMax(value)	被注释的元素必须是一个数字，其值必须小于等于指定的最大值
	@Size(max, min)	被注释的元素的大小必须在指定的范围内
	@Digits (integer, fraction)	被注释的元素必须是一个数字，其值必须在可接受的范围内
	@Past	被注释的元素必须是一个过去的日期
	@Future	被注释的元素必须是一个将来的日期
	@Pattern(value)	被注释的元素必须符合指定的正则表达式
	
	2. Hibernate Validator 附加的 constraint
	
	注解	作用
	@Email	被注释的元素必须是电子邮箱地址
	@Length(min=, max=)	被注释的字符串的大小必须在指定的范围内
	@NotEmpty	被注释的字符串的必须非空
	@Range(min=, max=)	被注释的元素必须在合适的范围内
	@NotBlank	被注释的字符串的必须非空
	@URL(protocol=,
	host=,    port=, 
	regexp=, flags=)	被注释的字符串必须是一个有效的url
	@CreditCardNumber
	被注释的字符串必须通过Luhn校验算法，
	银行卡，信用卡等号码一般都用Luhn
	计算合法性
	@ScriptAssert (lang=, script=, alias=)	要有Java Scripting API 即JSR 223  ("Scripting for the JavaTM Platform")的实现
	@SafeHtml (whitelistType=,  additionalTags=)	classpath中要有jsoup包 hibernate补充的注解中，最后3个不常用，可忽略。
	主要区分下@NotNull  @NotEmpty  @NotBlank 3个注解的区别：
	@NotNull           任何对象的value不能为null
	@NotEmpty       集合对象的元素不为0，即集合不为空，也可以用于字符串不为null
	@NotBlank        只能用于字符串不为null，并且字符串trim()以后length要大于0
	 * 
	 * @param bean，待校验的bean的实例
	 * @return 如果返回的Map为空(null)，就说明当前 bean合法，否则就有数据不合法      
	 * @author        liumc
	 * @Date          2017-3-29 上午10:19:28
	 */
	protected <T> Map validator(T bean){
		return ValidatorUtil.validate(bean);
	}
	
	/**
	 * 
	 * 输出json到浏览器中 
	 * @param json    json字符串
	 * @param response      
	 * @author        liumc
	 * @Date          2017-3-29 下午1:42:46
	 */
	protected void printJson(String json,HttpServletResponse response){
		PrintWriter writer = null;
	    try {
	      response.setContentType("application/json;charset=utf-8");
	      //设置缓存为空   
	      response.setHeader("Pragma","No-cache");   
	      response.setHeader("Cache-Control","no-cache");   
	      response.setDateHeader("Expires",   0);  
		  //response.setContentType("text/json;charset=UTF-8");
	      writer = response.getWriter();
	      writer.write(json);
	      writer.flush();
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	    json = null;
	}
	
	/**
	 * 输出文本
	 * @param object
	 * @param response
	 */
	protected void print(Object object,HttpServletResponse response){		
		try {
			response.setContentType("text/html;charset=UTF-8");
		      //设置缓存为空   
		      response.setHeader("Pragma","No-cache");   
		      response.setHeader("Cache-Control","no-cache");   
		      response.setDateHeader("Expires",   0); 
			response.getWriter().print(object.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		object = null;
	}
	
	/**
	 * 
	 * 把一个对象输出为json格式的字符串 
	 * @param obj
	 * @param response      
	 * @author        liumc
	 * @Date          2017-3-29 下午1:57:54
	 */
	protected void printJson(Object obj,HttpServletResponse response){
		this.printJson(FastJsonUtil.obj2Json(obj), response);
		obj = null;
	}
	protected void printJsonObject(Object obj,HttpServletResponse response){
		this.printJson(FastJsonUtil.obj2Json(obj), response);
		obj = null;
	}
	
	/**
	 * 输出xml
	 * @param object
	 * @param response
	 */
	protected void printXML(Object object,HttpServletResponse response){		
		try {
			response.setContentType("text/xml;charset=UTF-8");
		      //设置缓存为空   
		      response.setHeader("Pragma","No-cache");   
		      response.setHeader("Cache-Control","no-cache");   
		      response.setDateHeader("Expires",   0); 
			response.getWriter().print(object.toString());
			object = null;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取鉴权跳转路径（session不存在，就跳转到对应的登录页面【微信】，登录后，自动跳转到对应的preURL路径上）
	 * @param request   
	 * @param preURL    
	 * @return
	 */
	protected String getPassportByWx(HttpServletRequest request,String preURL) {
		String rslt = null;
		HttpSession session = HttpSessionUtil.getSession(request);
    	Long userId = null;
    	Long domainId = null;
    	String _MINGGAO_AUTHENTIC_ = null;
    	//判断session是否存在
    	try{
	      userId = (Long)session.getAttribute("userId");
	      domainId = (Long)session.getAttribute("domainId");
	      _MINGGAO_AUTHENTIC_ = (String)session.getAttribute("_MINGGAO_AUTHENTIC_");
    	}catch(Exception e){
    	}
    	if(null==userId  || null==domainId) {
    		request.setAttribute("preURL", preURL);
    		rslt = "mobile/weixin/login"; //跳转到微信登录页面
    	}
		return rslt;
	}
	
	/**
	 * 刷新session中的formToken
	 * @param request
	 */
	protected void refreshSessionToken(HttpServletRequest request) {
		TokenUtil.refreshSessionToken(request);
	}
	
	/**
	 * 移除session中的formToken
	 * @param request
	 */
	protected void removeSessionToken(HttpServletRequest request) {
		TokenUtil.removeSessionToken(request);
	}
	
	/**
	 * 获取session中的formToken
	 * @param request
	 * @return
	 */
	protected String getSessionToken(HttpServletRequest request) {
		return TokenUtil.getSessionToken(request);
	}
	
	/**
	 * 判断session中的formToken跟提交过来的是否一致
	 * @param request
	 * @return true是，false否
	 */
	protected boolean isSameSessionToken(HttpServletRequest request) {
		boolean rslt = false;
		String pageToken = request.getParameter(ApplicationConstant.SESSION_FORM_TOKEN);
		String sessionToken = this.getSessionToken(request);
		rslt = sessionToken.equals(pageToken)?true:false;
		return rslt;
	}

}
