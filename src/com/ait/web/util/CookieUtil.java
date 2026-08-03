package com.ait.web.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import com.ait.sys.bean.AdminBean;

public class CookieUtil {

	// 保存cookie时的cookieName
	private final static String cookieDomainName = "sysAdmin";

	// 设置cookie有效期是两个星期，根据需要自定义
	private final static long cookieMaxAge = 60 * 60 * 24 * 7 * 2;

	// 保存Cookie到客户端-------------------------------------------------------------------------
	// 在CheckLogonServlet.java中被调用
	// 传递进来的user对象中封装了在登陆时填写的用户名与密码
	public static void saveCookie(AdminBean user, HttpServletResponse response) {

		// cookie的有效期
		long validTime = System.currentTimeMillis() + (cookieMaxAge * 5000);
		// 将要被保存的完整的Cookie值
		String cookieValue = user.getUsername() + "," + validTime + ",";
		// 开始保存Cookie
		Cookie cookie = new Cookie(cookieDomainName, cookieValue);
		// 存两年(这个值应该大于或等于validTime)
		cookie.setMaxAge(60 * 60 * 24 * 365 * 2);
		// cookie有效路径是网站根目录
		cookie.setPath("/");
		// 向客户端写入
		response.addCookie(cookie);
	}
}
