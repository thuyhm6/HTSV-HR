/**
 * 
 */
package com.ait.web.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @Copyright   LDCC
 * @Company:    LDCC
 * @fileName:   IpUtil.java
 * @Description:
 * @Create date: 2012-5-16上午11:03:09
 * @author:   lufeng(lufeng@ait.net.cn)
 * @Update Date: 2012-5-16上午11:03:09
 * @Update by:   
 * @version 5.1
 */
public class IpUtil {

	/**
	 * 查询客户端IP地址
	 * @param request
	 * @return
	 */
	public static String getIpAddrByRequest(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		System.out.println("-----request---ip------:"+ip);
		return ip;
	}
	
	/**
	 * 查询外网IP地址，查不到外网IP地址查询内网
	 * @return
	 * @throws SocketException
	 */
	public static String getRealIp() throws SocketException {
		String localip = null;// 本地IP，如果没有配置外网IP则返回它
		String netip = null;// 外网IP
		Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
		InetAddress ip = null;
		boolean finded = false;// 是否找到外网IP
		while (netInterfaces.hasMoreElements() && !finded) {
			NetworkInterface ni = netInterfaces.nextElement();
			Enumeration<InetAddress> address = ni.getInetAddresses();
			while (address.hasMoreElements()) {
				ip = address.nextElement();
				if (!ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1) {// 外网IP
					netip = ip.getHostAddress();
					finded = true;
					break;
				} else if (ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1) {// 内网IP
					localip = ip.getHostAddress();
				}
			}
		}
		if(netip != null && !"".equals(netip)) {
			return netip;
		}else {
			return localip;
		}
	}
	
	/**
	 * 查询外网IP地址，查不到外网IP地址查询内网
	 * @return
	 * @throws SocketException
	 */
	public static String getLocalIp() throws SocketException {
		String localip = null;// 本地IP，如果没有配置外网IP则返回它
		Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
		InetAddress ip = null;
		boolean finded = false;// 是否找到外网IP
		while (netInterfaces.hasMoreElements() && !finded) {
			NetworkInterface ni = netInterfaces.nextElement();
			Enumeration<InetAddress> address = ni.getInetAddresses();
			while (address.hasMoreElements()) {
				ip = address.nextElement();
				localip = ip.getHostAddress();
			}
		}
		return localip;
	}
	
	public static void main(String[] args) {
		String ip = "123";
		IpUtil ipUtil = new IpUtil();
		try {
			ip = ipUtil.getRealIp();
		} catch (SocketException e) {
			e.printStackTrace();
		}
		
		try {
			ip = ipUtil.getLocalIp();
		} catch (SocketException e) {
			e.printStackTrace();
		}
		
	}
}
