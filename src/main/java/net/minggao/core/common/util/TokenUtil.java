package net.minggao.core.common.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.minggao.core.framework.ApplicationConstant;

/**
 * 为了解决表单重复提交，系统中使用token来进行处理
 * @author liumc
 * @date 20190118
 *
 */
public class TokenUtil {
	public static String getToken() {
		return Encrypt.e(Encrypt.md5_16(""+System.currentTimeMillis()));
	}
	
	/**
	 * 刷新session中的formToken
	 * @param request
	 */
	public static void refreshSessionToken(HttpServletRequest request) {
		HttpSession session = HttpSessionUtil.getSession(request);
		session.setAttribute(ApplicationConstant.SESSION_FORM_TOKEN, TokenUtil.getToken());
	}
	
	/**
	 * 移除session中的formToken
	 * @param request
	 */
	public static void removeSessionToken(HttpServletRequest request) {
		HttpSession session = HttpSessionUtil.getSession(request);
		session.removeAttribute(ApplicationConstant.SESSION_FORM_TOKEN);
	}
	
	/**
	 * 获取session中的formToken
	 * @param request
	 * @return
	 */
	public static String getSessionToken(HttpServletRequest request) {
		HttpSession session = HttpSessionUtil.getSession(request);
		return session.getAttribute(ApplicationConstant.SESSION_FORM_TOKEN)==null?"-1":session.getAttribute(ApplicationConstant.SESSION_FORM_TOKEN).toString();
	}
}
