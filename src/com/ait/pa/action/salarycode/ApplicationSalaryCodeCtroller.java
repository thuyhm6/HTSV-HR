package com.ait.pa.action.salarycode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.aspectj.internal.lang.annotation.ajcDeclareAnnotation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;

import com.ait.ar.service.CycleSer;
import com.ait.hrm.service.EmpInfoSer;
import com.ait.pa.bean.SalaryCode;
import com.ait.pa.service.salary.PaInputItemParamSer;
import com.ait.pa.service.salarycode.ApplicationSalaryCodeSer;
import com.ait.pa.service.salarycode.salaryCodeSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.AffirmSer;
import com.ait.sys.service.CompanySer;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.AuthorityUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;
import com.ait.web.util.UiUtil;

@Controller
@RequestMapping(value = "/pa/salarycode")
public class ApplicationSalaryCodeCtroller {
	
	
		Logger logger = Logger.getLogger(ApplicationSalaryCodeCtroller.class);
		
		@Autowired
		private AffirmSer affirmSer;
		
		@Autowired
		private ApplicationSalaryCodeSer applicationSalaryCodeSer;
		
		@Autowired
		private ToolMenuSer toolMenuSer;
		
		@Autowired
		private salaryCodeSer salaryCodeSer;
		
		@Autowired
		private AuthorityUtil authorityUtil;
		
		@Autowired
		private EmpInfoSer empInfoSer;
		@Autowired
		private CycleSer cycleSer;
		/**
		 * 页面跳转到人员工资权限匹配管理列表页面(Jump to the lists of management)
		 * @param request
		 * @param response
		 * @param modelMap
		 * @return
		 * @throws Exception
		 */
		@SuppressWarnings("unchecked")
		@RequestMapping(value="/viewApplicationSalaryCodeList" )
		public ModelAndView viewApplicationSalaryCodeList(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception{
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			List applicationSalaryList=this.applicationSalaryCodeSer.getApplicationSalaryCodeList(request);
			int applicationSalaryCnt=this.applicationSalaryCodeSer.getApplicationSalaryCodeCnt(request);
			List salaryCodeList=this.applicationSalaryCodeSer.getPaInputItemApplicationList(request);
			List getEmpTypeCodeList =   this.cycleSer.getKeeperEmpTypeCodeList(request,admin.getPersonId()) ; 
			List jobTypeGroupList =this.cycleSer.getJobTypeGroupList(request,admin.getCpnyId());
			modelMap.put("getEmpTypeCodeList", getEmpTypeCodeList);
			modelMap.put("jobTypeGroupList", jobTypeGroupList);
			modelMap.put("salaryCodeList", salaryCodeList);
			modelMap.put("applicationSalaryList", applicationSalaryList);
			modelMap.put("companyList", empInfoSer.getCompanyList(request));
			modelMap.put("authority", authorityUtil.isSuperUser(admin.getPersonId()));
			modelMap.put("CPNY", request.getParameter("CPNY") == null ? admin.getCpnyId() : request.getParameter("CPNY") );
			modelMap.put(UiUtil.TOTAL_COUNT_NAME,  applicationSalaryCnt);
			modelMap.put("toolbarInfo",  request.getParameter("menuNo") != null ? 
					toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "217864")) ;
			return new ModelAndView("/pa/salarycode/viewApplicationSalaryCodeList",modelMap);
		}
		
		
		@SuppressWarnings("unchecked")
		@RequestMapping(value = "/findSalaryCodeObject")
		@ResponseBody
		public List findSalaryCodeObject(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap)
				throws Exception {
			List list = this.salaryCodeSer.getSalaryCodeList(request);
			return list;
		}

		
		/**
		 * 跳转到添加权限者得页面(redirect to the page of add affirmors)
		 * Description:
		 * @param request
		 * @param response
		 * @param modelMap
		 * @return
		 * @throws Exception
		 */
		@SuppressWarnings("unchecked")
		@RequestMapping(value = "/addApplicationSalaryView")
		public ModelAndView addApplicationSalaryView(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception {
			List salaryCodeList=this.applicationSalaryCodeSer.getPaInputItemApplicationList(request);
			modelMap.put("salaryCodeList",salaryCodeList) ;
			return new ModelAndView("/pa/salarycode/addApplicationSalaryView", modelMap);
		}
		
		/**
		 * 给人员添加工资代码权限
		 * Description:add an approval process
		 * @param request
		 * @return
		 * @throws Exception 
		 */
		@SuppressWarnings("unchecked")
		@RequestMapping(value = "/addApplicationSalaryInfo")
		@ResponseBody
		public Map addApplicationSalaryInfo(HttpServletRequest request) throws Exception{
			Map<String, Object> map = new HashMap<String, Object>();
			int result =this.applicationSalaryCodeSer.addApplicationSalaryInfo(request) ;
			if(result == 1){
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage("alert.message.add_success",request));
				map.put("navTabId", "pa1109");
				map.put("callbackType", "closeCurrent");
			}else{
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage("alert.message.add_fail",request));
			}
			return map;		
		}
		
		/**
		 * 页面跳转到更新页面(Jump to the page for update)
		 * @param request
		 * @param response
		 * @param modelMap
		 * @return
		 * @throws Exception
		 */
		@SuppressWarnings("unchecked")
		@RequestMapping(value="/updateApplicationSalaryView" )
		public ModelAndView updateApplicationSalaryView(HttpServletRequest request,
				HttpServletResponse response, ModelMap modelMap) throws Exception{
			List salaryCodeList=this.applicationSalaryCodeSer.getPaInputItemApplicationList(request);
			modelMap.put("salaryCodeList",salaryCodeList) ;
			Map applicationSalaryInfo = this.applicationSalaryCodeSer.getApplicationSalaryInfo(request);
			String salary_code = applicationSalaryInfo.get("SALARY_CODE").toString();
			List salaryList = new ArrayList() ;
			if(salary_code != null && !"".equals(salary_code)){
				String [] salaryCode = salary_code.split(",");
				if(salaryCode.length>0){
					for(int i=0;i<salaryCode.length;i++){
						salaryList.add(salaryCode[i]);
					}
				}
			}
			modelMap.put("salaryList",salaryList);
			modelMap.put("applicationSalaryInfo", applicationSalaryInfo);
			return new ModelAndView("/pa/salarycode/updateApplicationSalaryView",modelMap);
		}
		
		/**
		 * 更新信息(update)
		 * Description:
		 * @param request
		 * @return
		 * @throws Exception 
		 */
		@SuppressWarnings("unchecked")
		@RequestMapping(value = "/updateApplicationSalaryInfo")
		@ResponseBody
		public Map updateApplicationSalaryInfo(HttpServletRequest request) throws Exception{
			Map<String, Object> map = new HashMap<String, Object>();
			int result =this.applicationSalaryCodeSer.updateApplicationSalaryInfo(request);
			if(result == 1){
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage("alert.message.update_success",request));//"修改成功"
				map.put("navTabId", "pa1109");
				map.put("callbackType", "closeCurrent");
			}else{
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage("alert.message.update_fail",request));//"修改失败"
			}
			return map;		
		}
		
		/**
		 * 删除人员工资代码匹配记录
		 * Description:delete an approval process
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		@SuppressWarnings("unchecked")
		@RequestMapping(value = "/deleteApplicationSalaryInfo")
		@ResponseBody
		public Map deleteApplicationSalaryInfo(HttpServletRequest request,HttpServletResponse response)throws Exception{
			Map<String, Object> map = new HashMap<String, Object>();
			int isDelete = this.applicationSalaryCodeSer.deleteApplicationSalaryInfo(request);
			if(isDelete == 1){
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage("alert.message.delete_success",request));//"删除成功"
				map.put("navTabId", "pa1109");
			}else{	
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage("alert.message.delete_fail",request));//"删除失败"
			}	
			return map;
		}
		
		/**
		 * 根绝EMP_ID进行模糊查询(The beautiful EMP_ID for fuzzy query)
		 * @param request
		 * @param response
		 * @param modelMap
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value = "/viewPersonList")
		public ModelAndView viewPersonList(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)throws Exception{
			modelMap.put("turn_to_url",request.getParameter("turnToUrl"));
			modelMap.put("empList",this.affirmSer.getEmpIdList(request));
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.affirmSer.getEmpIdListCnt(request)) ;
			String empId=request.getParameter("empId_pa1109")==null?"":request.getParameter("empId_pa1109").toString();
			String empName=request.getParameter("empName_pa1109")==null?"":request.getParameter("empName_pa1109").toString();
			modelMap.put("empId", empId);
			String personId=request.getParameter("personId_pa1109")==null?"":request.getParameter("personId_pa1109").toString();
			modelMap.put("personId", personId);
			modelMap.put("empName", empName);
			modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
			return new ModelAndView("/pa/salarycode/viewPersonList",modelMap);		
		} 
}
