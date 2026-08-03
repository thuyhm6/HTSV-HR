package com.ait.interf.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ait.interf.service.AndroidLoginSer;
import com.ait.sys.dao.CompanyDao;
/**
 * Copyright: AIT Company: AIT
 * 
 * @fileName: AndroidInterface.java
 * @Description: Android 
 * @Create date: 2014-3-7 下午02:55:16
 * @Create by:
 * @version 5.5
 */
@Controller
@RequestMapping(value="/androidInterface")
public class AndroidInterface {

	@Autowired
	private AndroidLoginSer loginSer;
	@Autowired
	private CompanyDao companyDao;
	
	@RequestMapping(value = "/androidLogin")
	@ResponseBody
	public String androidLogin(HttpServletRequest request,HttpServletResponse response){
		JSONObject itemJsonObject = new JSONObject();
		JSONArray array = new JSONArray();
		String msg = loginSer.findUser(request);
		if(msg.equals("OK")){
			Map counts = loginSer.getTips(request);
			itemJsonObject.put("paffirm", counts.get("total_count_peraffirmInfo"));//调令待决裁
			itemJsonObject.put("affirm", counts.get("total_count_affirmInfo"));//待决裁
			itemJsonObject.put("confirm", counts.get("total_count_confirmInfo"));//待确认
		}
		itemJsonObject.put("msg", msg);
		array.add(itemJsonObject);
		response.setContentType("text/plain; charset=utf-8"); 
		response.setCharacterEncoding("utf-8"); 
		PrintWriter pw = null;
		try {
			pw = response.getWriter();
			pw.write(array.toString());
			pw.flush();
			pw.close();
			System.out.println("__________________________________________"+array.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			pw.close();
		}
		System.out.println("androidLogin");
		return "/ess/temp";
	}
	@RequestMapping(value="/getAffirmInfo")
	@ResponseBody
	public String getAffirmInfo(HttpServletRequest request,
					HttpServletResponse response) throws Exception{
		JSONObject object = new JSONObject();
		JSONArray array = new JSONArray();
		array.addAll(loginSer.getInfoNotAffirm(request));
		response.setContentType("text/plain; charset=utf-8"); 
		response.setCharacterEncoding("utf-8"); 
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.print(array);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			out.close();
		}
		return "";
	}
	@RequestMapping(value="/getConfirmInfo")
	@ResponseBody
	public String getConfirmInfo(HttpServletRequest request,
					HttpServletResponse response) throws Exception{
		JSONArray array = new JSONArray();
		array.addAll(loginSer.getInfoNotConfirm(request));
		response.setContentType("text/plain; charset=utf-8"); 
		response.setCharacterEncoding("utf-8"); 
		PrintWriter pw = null;
		try {
			pw = response.getWriter();
			pw.write(array.toString());
			pw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			pw.close();
		}
		return "";
	}
	@RequestMapping(value="/getTransferInfo")
	@ResponseBody
	public String getTransferInfo(HttpServletRequest request,
					HttpServletResponse response) throws Exception{
		JSONArray array = new JSONArray();
		array.addAll(loginSer.getTransferOrderList(request));
		response.setContentType("text/plain; charset=utf-8"); 
		response.setCharacterEncoding("utf-8"); 
		PrintWriter pw = null;
		try {
			pw = response.getWriter();
			pw.write(array.toString());
			pw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			pw.close();
		}
		return "";
	}
	@RequestMapping(value = "/getCompany")
	public String getCompany(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		
		String language=request.getParameter("lang");

		if(language==null || language.isEmpty()  ){
			language = "zh";
		}

		String activity="1";
		String key = request.getParameter("key");
		if(!key.equals("EHR_SD_ANDRIOD")){
			return "keyError";
		}
		Map paramMap=new LinkedHashMap();
		paramMap.put("ACTIVITY",activity);
		paramMap.put("interLanguage",language);
		List cnpyList = companyDao.getCompanyItemAllList(paramMap) ;
		JSONObject itemJsonObject = new JSONObject();
		JSONArray array = new JSONArray();
		
		for(Object map : cnpyList){
			Map temp = (Map) map;
			itemJsonObject = new JSONObject();
			itemJsonObject.put("CPNY_ID", temp.get("CPNY_ID"));
			itemJsonObject.put("CPNY_NAME", temp.get("CONTENT"));
			array.add(itemJsonObject);
		}
		
		response.setContentType("text/plain; charset=utf-8"); 
		response.setCharacterEncoding("utf-8");
		PrintWriter pw = null;
		try {
			
			pw = response.getWriter();
			pw.write(array.toString());
			pw.flush();
			pw.close();
//			System.out.println("__________________________________________"+array.toString());
		} catch (IOException e) {
			
			e.printStackTrace();
		} finally {
			pw.close();
		}
//		ModelAndView mdAndView = new ModelAndView("/ess/temp");
//		request.getRequestDispatcher("/ess/temp").forward(request, response);
//		return mdAndView;
		return "/ess/temp";
	}
}
