package com.ait.pa.action.salary;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;

import com.ait.ar.service.ArMonthCalculateSer;
import com.ait.pa.dao.PaCalculateDao;
import com.ait.pa.service.salary.PaCalculateSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.CommonException;
import com.ait.web.util.JsonUtil;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;
import com.ait.web.util.UiUtil;

/**
 * Copyright: LDCC Company: LDCC
 * 
 * @fileName: PaCalculateCtroller.java
 * @Description:
 * @Create date: 2012-5-14 上午11:54:03
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
@Controller
@RequestMapping(value = "/pa/salary")
public class PaCalculateCtroller {
	Logger logger = Logger.getLogger(PaCalculateCtroller.class);

	@Autowired
	private PaCalculateSer paCalculateSer;
	@Autowired
	private PaCalculateDao paCalculateDao ;
	
	@Autowired
	private ArMonthCalculateSer arMonthCalculateSer;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewPaCalculate")
	public ModelAndView viewPaCalculate(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		
//		List paSupervisorList  = this.paCalculateSer.getPaSupervisorList(request);
//		HttpSession session = request.getSession() ;
//		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
//		String supervisorId = admin.getPersonId();
//		modelMap.put("supervisorlist", paSupervisorList) ;
//		modelMap.put("supervisorId", supervisorId) ;

		List statList = this.paCalculateSer.getPaStatisticList(request);
		modelMap.put("statList", statList);
		List deptList = this.paCalculateSer.getDeptAreaList(request) ;
		modelMap.put("deptList", deptList) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		modelMap.put("CPNY_ID", admin.getCpnyId());

		return new ModelAndView("/pa/salary/viewPaCalculate", modelMap);
	}
	
	
	/** 
	* @Title: viewPaConfirmprogress 
	* @Description: TODO 工资确认 附带PN工资查看
	* @param @param request
	* @param @param response
	* @param @param modelMap
	* @param @return
	* @param @throws Exception    
	* @return ModelAndView    
	* @throws 
	*/
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewPaConfirmprogress")
	public ModelAndView viewPaConfirmprogressList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		
//		List paSupervisorList  = this.paCalculateSer.getPaSupervisorList(request);
//		HttpSession session = request.getSession() ;
//		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
//		String supervisorId = admin.getPersonId();
//		modelMap.put("supervisorlist", paSupervisorList) ;
//		modelMap.put("supervisorId", supervisorId) ;

		List statList = this.paCalculateSer.getPaStatisticList(request);
		modelMap.put("statList", statList);
		List deptList = this.paCalculateSer.getDeptAreaListByHr(request) ;
		modelMap.put("deptList", deptList) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		modelMap.put("CPNY_ID", admin.getCpnyId());
        if(admin.getCpnyId().equals("LGEPN")){
	        Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
			List paList = this.paCalculateSer.getPaConfirmMonth(request);
			int paListCnt = this.paCalculateSer.getPaConfirmMonthCnt(request);
			modelMap.put("palist", paList);
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, paListCnt) ;
			modelMap.put("deptNO", paramMap.get("deptNO"));
			modelMap.put("JobTypeGroupNo", paramMap.get("JobTypeGroupNo"));
			modelMap.put("EmpTypeCodeNo", paramMap.get("EmpTypeCodeNo"));
			modelMap.put("EmpOffice", paramMap.get("EmpOffice"));
			modelMap.put("condition", paramMap.get("condition"));
			modelMap.put("paYear", paramMap.get("paYear"));
			modelMap.put("paMonth", paramMap.get("paMonth"));
		}
		return new ModelAndView("/pa/salary/viewPaConfirmprogress", modelMap);
	}
	/**
	 * 财务传送查看
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewPaTransfer")
	public ModelAndView viewPaTransfer(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		
//		List paSupervisorList  = this.paCalculateSer.getPaSupervisorList(request);
//		HttpSession session = request.getSession() ;
//		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
//		String supervisorId = admin.getPersonId();
//		modelMap.put("supervisorlist", paSupervisorList) ;
//		modelMap.put("supervisorId", supervisorId) ;
		 
		List statList = this.paCalculateSer.getArStatisticPatransferList(request);
		modelMap.put("statList", statList);
		List deptList = this.paCalculateSer.getDeptAreaList(request) ;
		modelMap.put("deptList", deptList) ;
		//工资是否关闭
		String paFlag = this.paCalculateSer.getPaifClose(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		modelMap.put("CPNY_ID", admin.getCpnyId());

		return new ModelAndView("/pa/salary/viewPaTransfer", modelMap);
	}

	/**
	 * 营业员预提管理
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewPaYinYeYuanYuTi")
	public ModelAndView viewPaYinYeYuanYuTi(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		
//		List paSupervisorList  = this.paCalculateSer.getPaSupervisorList(request);
//		HttpSession session = request.getSession() ;
//		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
//		String supervisorId = admin.getPersonId();
//		modelMap.put("supervisorlist", paSupervisorList) ;
//		modelMap.put("supervisorId", supervisorId) ;

		List statList = this.paCalculateSer.getArStatisticList(request);
		modelMap.put("statList", statList);
		List deptList = this.paCalculateSer.getDeptAreaList(request) ;
		modelMap.put("deptList", deptList) ;
		//工资是否关闭
		String paFlag = this.paCalculateSer.getPaifClose(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		modelMap.put("CPNY_ID", admin.getCpnyId());

		return new ModelAndView("/pa/salary/viewPaYinYeYuanYuTi", modelMap);
	}
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getPaCalculateTypeList")
	@ResponseBody
	public List getPaCalculateTypeList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		return this.paCalculateSer.getPaCalculateTypeList(request);
	}

	@RequestMapping(value = "/getCheckPaCalculateType")
	@ResponseBody
	public String getCheckPaCalculateType(HttpServletRequest request)
			throws Exception {

		String returnString = "Y";

		int errorInt = this.paCalculateSer.getCheckPaCalculateType(request);
		if (errorInt > 0) {

			returnString = TipMessage.getTipMessage(
					"alert.message.pa.salary.bonusUseMergerPlanDutyWay",
					request);
		}

		return returnString;
	}

	@RequestMapping(value = "/paCalculate")
	@ResponseBody
	public void paCalculate(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");

		String returnString = this.paCalculateSer.paCalculate(request);

		PrintWriter out = response.getWriter();
		out.println(JsonUtil.writeInternal(returnString));

		out.flush();
		out.close();

		//该流程判断已在工资计算存储中判断，注释掉下面的流程判断（且下面判断存有问题）
//		response.setContentType("text/html;charset=UTF-8");
//		response.setHeader("Cache-Control", "no-cache");
//		LinkedHashMap  paramMap = ObjectBindUtil.getRequestParamData(request) ;
//		String AR_DEPT_NOS= paramMap.get("deptid").toString() ;
//		String[] NOS = AR_DEPT_NOS .split("!");
//		int num1 = 0;
//		String returnString = "";
//		//验证考勤是否确定申请
//		for (int i = 0; i < NOS.length; i++) {
//			paramMap.put("DEPT_NO", NOS[i]);
//			num1 += Integer.parseInt(paCalculateDao.getpaapplyCloseOpen(paramMap));
//		}
//		if(num1 == 0 ){
//		   returnString = this.paCalculateSer.paCalculate(request);
//		}
//		PrintWriter out = response.getWriter();
//		out.println(JsonUtil.writeInternal(returnString));
//
//		out.flush();
//		out.close();
	}
	
	@RequestMapping(value = "/paWithholdingCalculate")
	@ResponseBody
	public void paWithholdingCalculate(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");

		String returnString = this.paCalculateSer.paWithholdingCalculate(request);

		PrintWriter out = response.getWriter();
		out.println(JsonUtil.writeInternal(returnString));

		out.flush();
		out.close();
	}
	
	@RequestMapping(value = "/paWithholdingArCalculate")
	@ResponseBody
	public void paWithholdingArCalculate(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");

		String returnString = this.paCalculateSer.paWithholdingArCalculate(request);

		PrintWriter out = response.getWriter();
		out.println(JsonUtil.writeInternal(returnString));

		out.flush();
		out.close();
	}
	
	
	/**
	 * 工资确认页面功能 
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author  wangqiang@ait.net.cn 
	* @date 2014-7-11 下午6:56:53 
	* @version V1.0
	 */
	@RequestMapping(value = "/paSalaryConfirm")
	@ResponseBody
	public void paSalaryConfirm(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");

		String returnString = this.paCalculateSer.paSalaryConfirm(request);

		PrintWriter out = response.getWriter();
		out.println(JsonUtil.writeInternal(returnString));

		out.flush();
		out.close();
	}
	
	/**
	 * 财务传送  
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author  wangqiang@ait.net.cn 
	* @date 2014-7-11 下午6:56:53 
	* @version V1.0
	 */
	@SuppressWarnings("null")
	@RequestMapping(value = "/paSalaryTransAjax")
	@ResponseBody
	public void paSalaryTransAjax(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");

		String returnString = this.paCalculateSer.getPaifClose(request);
		 
		PrintWriter out = response.getWriter();
		out.println(JsonUtil.writeInternal(returnString));

		out.flush();
		out.close();
	}
	/**
	 * 营业员预提传送  
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author  wangqiang@ait.net.cn 
	* @date 2014-7-11 下午6:56:53 
	* @version V1.0
	 */
	@SuppressWarnings("null")
	@RequestMapping(value = "/paSalaryTransAjaxYuti")
	@ResponseBody
	public void paSalaryTransAjaxYuti(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");

		String returnString = this.paCalculateSer.getPaifCloseYuti(request);
		 
		PrintWriter out = response.getWriter();
		out.println(JsonUtil.writeInternal(returnString));

		out.flush();
		out.close();
	}
	
	/**
	 * 财务传送 中工资是否关闭 
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author  wangqiang@ait.net.cn 
	* @date 2014-7-11 下午6:56:53 
	* @version V1.0
	 */
	@SuppressWarnings("null")
	@RequestMapping(value = "/paSalaryTransAjaxClose")
	@ResponseBody
	public void paSalaryTransAjaxClose(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");

		int returnString=0;
		int num = this.paCalculateSer.getPaifClose2(request);
		if(num>0){
			returnString=1;
		}else{
			returnString = 0;
		}
		PrintWriter out = response.getWriter();
		out.println(JsonUtil.writeInternal(returnString));

		out.flush();
		out.close();
	}
	
	/**
	 * 财务传送送 
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author  wangqiang@ait.net.cn 
	* @date 2014-7-22 下午6:56:53 
	* @version V1.0
	 */
	@RequestMapping(value = "/paSalaryTransfer")
	@ResponseBody
	public void paSalaryTransfer(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");

		String returnString = this.paCalculateSer.paSalaryTransfer(request);

		PrintWriter out = response.getWriter();
		out.println(JsonUtil.writeInternal(returnString));

		out.flush();
		out.close();
	}
	
	/**
	 * 营业员预提传送送 
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author  wangqiang@ait.net.cn 
	* @date 2014-7-22 下午6:56:53 
	* @version V1.0
	 */
	@RequestMapping(value = "/paSalaryTransferYuti")
	@ResponseBody
	public void paSalaryTransferYuti(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");

		String returnString = this.paCalculateSer.paSalaryTransferYuti(request);

		PrintWriter out = response.getWriter();
		out.println(JsonUtil.writeInternal(returnString));

		out.flush();
		out.close();
	}
	
	/**
	 * 工资计算页面申请确认功能 
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author  wangqiang@ait.net.cn 
	* @date 2014-7-11 下午6:56:53 
	* @version V1.0
	 */
	@RequestMapping(value = "/paSalaryConfirmApply")
	@ResponseBody
	public void paSalaryConfirmApply(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");

		String returnString = this.paCalculateSer.paSalaryConfirmApply(request);

		PrintWriter out = response.getWriter();
		out.println(JsonUtil.writeInternal(returnString));

		out.flush();
		out.close();
	}
	
	/**
	 * 保险月 关联出保险发放日
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-9-18 下午6:56:53 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getSalaryProvideDatePa")
	@ResponseBody
	public Map getSalaryProvideDatePa (HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
			List getSalaryProvideDatePaList=this.paCalculateSer.getSalaryProvideDatePa(request);
			
			LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
			if(getSalaryProvideDatePaList.size()==0){
				map.put("","当前月没有符合发放日期");
			}else{
				for(int i=0;i<getSalaryProvideDatePaList.size();i++){
					map.put((String)((Map) getSalaryProvideDatePaList.get(i)).get("GIVE_DATE_PA0108"), ((Map) getSalaryProvideDatePaList.get(i)).get("GIVE_DATE_PA0108"));
				}
			}
			return map;
		
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getSalaryProvideDatePa2")
	@ResponseBody
	public Map getSalaryProvideDatePa2 (HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
			List getSalaryProvideDatePaList=this.paCalculateSer.getSalaryProvideDatePa2(request);
			
			LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
			if(getSalaryProvideDatePaList.size()==0){
				map.put("","当前月没有符合发放日期");
			}else{
				for(int i=0;i<getSalaryProvideDatePaList.size();i++){
					map.put((String)((Map) getSalaryProvideDatePaList.get(i)).get("PACAL_GIVE_DATE"), ((Map) getSalaryProvideDatePaList.get(i)).get("PACAL_GIVE_DATE"));
				}
			}
			return map;
		
	}
	
	/**
	 * 工资申请 关闭 (applyClose_Guan)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewpaApplyCloseGuan")
	public ModelAndView viewpaApplyCloseGuan(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		
		List EssCntlist =  this.arMonthCalculateSer.paEssNOApplyCount(request);
        LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
        String STAT_NO = StringUtil.checkNull(paramMap.get("STAT_NO"));
        String arMonth = StringUtil.checkNull(paramMap.get("arMonth"));
        String AR_DEPT_NO= StringUtil.checkNull(paramMap.get("AR_DEPT_NO"));
		String flag = paramMap.get("FLAG").toString();
		modelMap.put("STAT_NO", STAT_NO);
		modelMap.put("arMonth", arMonth);
		modelMap.put("AR_DEPT_NO", AR_DEPT_NO);
		modelMap.put("FLAG", flag);
		
		modelMap.put("EssCntlist", EssCntlist);
		return new ModelAndView("/pa/salary/viewpaApplyCloseGuan", modelMap);
	}
	
	
	/**
	 * 考勤申请 关闭 (applyClose_Guan)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/paOFF")
	@ResponseBody
	public Map arOFF(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Map paramMap = ObjectBindUtil.getRequestParamData(request);
			String flag = StringUtil.checkNull(paramMap.get("FLAG"));
			 if(flag.equals("2")){
				int result = this.arMonthCalculateSer.paOFF(request);
					if (result == 1) {
						String returnString  = this.arMonthCalculateSer.paapplyCloseGuanstr(request);
						map.put("message", "否决成功成功!");//否决成功成功!+returnString
						map.put("statusCode", "200");
						//map.put("callbackType", "closeCurrent");
					}else{
						map.put("message", "操作出错,请重新申请!");//刷卡申请保存出错,请重新申请!
				
						map.put("statusCode", "300");
					}
			 }else{
				    map.put("message", "邮件发送成功成功!");//否决成功成功!
					map.put("statusCode", "200");
					//map.put("callbackType", "closeCurrent");
			 }
		}  catch (Exception e) {
			e.printStackTrace();
			map.put("message", "操作出错,请重新申请!");//刷卡申请保存出错,请重新申请!
			map.put("statusCode", "300");
		}
		return map;
	}
	
	
	
	@RequestMapping(value = "/paApplyclosed")
	@ResponseBody
	public void paApplyclosed(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		
		
		String result  = this.paCalculateSer.paapplyCloseOpenstr(request);
		
		PrintWriter out = response.getWriter();
		out.println(JsonUtil.writeInternal(result));

		out.flush();
		out.close();
	}
	
	
	
	
	
	@RequestMapping(value = "/paSalaryopen")
	@ResponseBody
	public void paSalaryopen(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		
		
		String result  = this.paCalculateSer.updatepapaOpenFlag(request);
		
		PrintWriter out = response.getWriter();
		out.println(JsonUtil.writeInternal(result));

		out.flush();
		out.close();
	}
	
	
	
	
	
	
	
	
	@RequestMapping(value = "/paWithholdingApplyclosed")
	@ResponseBody
	public void paWithholdingApplyclosed(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		
		
		String result  = this.paCalculateSer.paWithholdingapplyCloseOpenstr(request);
		
		PrintWriter out = response.getWriter();
		out.println(JsonUtil.writeInternal(result));

		out.flush();
		out.close();
	}
	/**
	 * 预提工资计算
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewPaWithholdingCalculate")
	public ModelAndView viewPaWithholdingCalculate(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		
//		List paSupervisorList  = this.paCalculateSer.getPaSupervisorList(request);
//		HttpSession session = request.getSession() ;
//		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
//		String supervisorId = admin.getPersonId();
//		modelMap.put("supervisorlist", paSupervisorList) ;
//		modelMap.put("supervisorId", supervisorId) ;

		/*Map statList = this.paCalculateSer.getJobTypeAreaList(request);
		
		modelMap.put("statList", statList.get("joblist"));
		modelMap.put("empTypeList", statList.get("emptypelist"));*/
		List deptList = this.paCalculateSer.getDeptAreaList(request) ;
		modelMap.put("deptList", deptList) ;
		request.setAttribute("YING_CU","YING_CU");
		List statList = this.paCalculateSer.getArStatisticPatransferList(request);
		modelMap.put("statList", statList);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		modelMap.put("CPNY_ID", admin.getCpnyId());

		return new ModelAndView("/pa/salary/viewPaWithholdingCalculate", modelMap);
	}
	

	
	
	
}
