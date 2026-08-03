package com.ait.web.login;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.ait.sys.bean.AdminBean;

public class OnlineUserListener implements
		ServletContextListener,HttpSessionListener,HttpSessionAttributeListener{
	// 声明一个ServletContext对象
	private ServletContext application = null ;
	//ServletContext创建时调用该方法
	public void contextInitialized(ServletContextEvent sce) {
		//储存所用用户名
		String a="123";
		//获得当前application对象
		application = sce.getServletContext();
		//设置到application范围
		application.setAttribute("LoginUser", a);
		System.out.println("================创建==================");
	}
	//ServletContext销毁时调用该方法
	public void contextDestroyed(ServletContextEvent sce) {
		
	}
	//session创建时调用该方法
	public void sessionCreated(HttpSessionEvent se) {
		
	}
	//session销毁时调用该方法
	public void sessionDestroyed(HttpSessionEvent se) {
		
		
		//获得当前所有的用户
		//ArrayList<String> allUser = (ArrayList<String>) application.getAttribute("LoginUser");
		String a="123";
		//获得删除的用户
		AdminBean user = (AdminBean)se.getSession().getAttribute("LoginUser");
		//删除该用户
		//a.remove(user);
		
		//重新设置到application范围中
		System.out.println("================销毁==================");
		
		application.setAttribute("allUser", a);
	}
	//session范围属性添加时调用
	public void attributeAdded(HttpSessionBindingEvent se) {
		//获得当前所有的用户
		
		ArrayList<String> allUser = (ArrayList<String>) application.getAttribute("LoginUser");
		//获得添加的用户
		String user = (String) se.getValue();
		//添加到所有用户中
		allUser.add(user);
	}
	//session范围属性移除时调用
	public void attributeRemoved(HttpSessionBindingEvent se) {
	}
	//session范围属性替换时调用
	public void attributeReplaced(HttpSessionBindingEvent se) {
	}


}
