package com.ait.ar.action.attendanceMintenance;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;
import com.ait.ar.service.ArDetailCalulateSer;
import com.ait.web.mail.ar.SendArCalInfoEmailToEmployee;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.JsonUtil;
import com.ait.web.util.SessionUtil;

/**
 * Copyright: LDCC Company: LDCC
 * 
 * @fileName: ArDetailCalculateCtroller.java
 * @Description:
 * @Create date: 2012-2-7 上午11:56:45
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
@Controller
@RequestMapping(value = "/ar/attendanceMintenance")
public class ArDetailCalculateCtroller {
	Logger logger = Logger.getLogger(ArDetailCalculateCtroller.class);

	@Autowired
	private ArDetailCalulateSer arDetailCalulateSer;

	/**
	 * 明细计算页面(view ArDetailCalculate)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewArDetailCalculate", method = RequestMethod.GET)
	public ModelAndView viewArDetailCalculate(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List arSupervisorList = this.arDetailCalulateSer
				.getArSupervisorList(request);
		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String supervisorId = admin.getPersonId();
		modelMap.put("supervisorlist", arSupervisorList);
		modelMap.put("supervisorId", supervisorId);
		modelMap.put("cpny_id", admin.getCpnyId());
		return new ModelAndView(
				"/ar/attendanceMintenance/viewArDetailCalculate", modelMap);
	}

	/**
	 * 明细计算(detail Calculate)
	 * 
	 * @param request
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/detailCalculate")
	@ResponseBody
	public void detailCalculate(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		response.setContentType("text/html;charset=UTF-8");

		response.setHeader("Cache-Control", "no-cache");

		String returnString = this.arDetailCalulateSer.detailCalculate(request);

		PrintWriter out = response.getWriter();

		out.println(JsonUtil.writeInternal(returnString));

		out.flush();
		out.close();
	}
	/**
	 * 事后申请 计算
	 * 
	 * @param request
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/detailShiHouCalculate")
	@ResponseBody
	public void detailShiHouCalculate(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		response.setContentType("text/html;charset=UTF-8");

		response.setHeader("Cache-Control", "no-cache");

		String returnString =  this.arDetailCalulateSer.detailShiHouCalculate(request);

		PrintWriter out = response.getWriter();

		out.println(JsonUtil.writeInternal(returnString));

		out.flush();
		out.close();
	}
	
	/**
	 * 追溯 计算
	 * 
	 * @param request
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/detailLastMonthCalculate")
	@ResponseBody
	public void detailLastMonthCalculate(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		response.setContentType("text/html;charset=UTF-8");

		response.setHeader("Cache-Control", "no-cache");

		String returnString =  this.arDetailCalulateSer.detailLastMonthCalculate(request);

		PrintWriter out = response.getWriter();

		out.println(JsonUtil.writeInternal(returnString));

		out.flush();
		out.close();
	}
	/**
	 * 明细计算页面发送邮件(sendarmail)
	 * 
	 * @param request
	 * @return String
	 * @throws Exception
	 */
	@SuppressWarnings("null")
	@RequestMapping(value = "/sendarmail")
	@ResponseBody
	public ModelAndView sendarmail(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		// HtmlEmail mailSender = new HtmlEmail();
		SendArCalInfoEmailToEmployee sendmail = new SendArCalInfoEmailToEmployee();
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		List arHistoryinfo = this.arDetailCalulateSer.getardetailsendview(request);
		sendmail.setList(arHistoryinfo);
		new Thread(sendmail).start();
		System.out.println(sendmail.getList()+"***********************");
		//new Thread(sendmail).start();
		return new ModelAndView(
				"/ar/attendanceMintenance/viewArDetailCalculate", modelMap);

	}

}
