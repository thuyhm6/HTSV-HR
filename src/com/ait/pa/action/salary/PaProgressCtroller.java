package com.ait.pa.action.salary;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;
import com.ait.ar.service.ArMonthCalculateSer;
import com.ait.pa.service.salary.PaCalculateSer;
import com.ait.pa.service.salary.PaProgressSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;



@Controller
@RequestMapping(value = "/pa/salary")
public class PaProgressCtroller {
	Logger logger = Logger.getLogger(PaProgressCtroller.class);
	
	@Autowired
	private PaProgressSer paProgressSer ;
	@Autowired
	private PaCalculateSer paCalculateSer;
	
	@Autowired
	private ArMonthCalculateSer arMonthCalculateSer;
	
	/**
	 * 工资系统首页面
	 *
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewPaMain")
	public ModelAndView viewPaMain(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		return new ModelAndView("/pa/salary/viewPaMain",modelMap);
		
	}
	
	/**
	 * 工资维护----工资锁定 
	 *
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewPaProgress")
	public ModelAndView viewPaProgressList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
			List paProgressList = this.paProgressSer.getPaProgressList(request);
			int paProgressCnt = this.paProgressSer.getPaProgressCnt(request);

			modelMap.put("itemList", paProgressList);
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, paProgressCnt);
		
		//取得考勤区间
	    List statnoList = this.paCalculateSer.getPaStatisticList(request);
	    modelMap.put("statnoList", statnoList);
	    AdminBean admin = (AdminBean)   request.getSession().getAttribute("LoginUser") ;
		modelMap.put("CPNY_ID", admin.getCpnyId());
		if (admin.getCpnyId().equals("TSTO")){
		List deptList = this.paCalculateSer.getDeptAreaList(request) ;
		modelMap.put("deptList", deptList) ;
		modelMap.put("DEPT_NO", request.getParameter("DEPT_NO"));
		}
		return new ModelAndView("/pa/salary/viewPaProgress",modelMap);
		
	}
	
	/**
	 * 工资锁定 
	 * */
	@RequestMapping(value = "/updatePaProgressInfo")
	@ResponseBody
	public String updatePaProgressInfo(HttpServletRequest request)throws Exception{
		
		String result = "";
		if (this.paProgressSer.updatePaProgressInfo(request)==1){
			result = "Y";
		}else{
			result = "N";
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewPaProgressByDept")
	public ModelAndView viewPaProgressByDeptList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		List paProgressList = this.paProgressSer.getPaProgressByDeptList(request);
		int paProgressCnt = this.paProgressSer.getPaProgressByDeptCnt(request);
		//取得考勤区间
		List statnoList = this.arMonthCalculateSer.getStatNoList(request) ;
		//取得区域信息
		List deptTypeList = this.arMonthCalculateSer.getDeptTypeList(request) ;
		//取得门店信息
		List deptDistinguishList = this.paProgressSer.getDeptDistinguishList(request);
		
		modelMap.put("deptTypeList", deptTypeList);
		modelMap.put("statnoList", statnoList);
		modelMap.put("deptDistinguishList", deptDistinguishList);
		modelMap.put("itemList", paProgressList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, paProgressCnt);
		
		return new ModelAndView("/pa/salary/viewPaProgressByDept",modelMap);
		
	}
	
	@RequestMapping(value = "/updatePaProgressByDept")
	@ResponseBody
	public String updatePaProgressByDept(HttpServletRequest request)throws Exception{
		String result = "";
		if (this.paProgressSer.updatePaProgressByDept(request)==1){
			result = "Y";
		}else{
			result = "N";
		}
		return result;
		
	}
	
	@RequestMapping(value = "/deletePaProgressByDept")
	@ResponseBody
	public String deletePaProgressByDept(HttpServletRequest request)throws Exception{
		String result = "";
		if (this.paProgressSer.deletePaProgressByDept(request)==1){
			result = "Y";
		}else{
			result = "N";
		}
		return result;
		
	}
	
	@RequestMapping(value = "/copyToNextMonthPaProgressByDept")
	@ResponseBody
	public String copyToNextMonthPaProgressByDept(HttpServletRequest request)throws Exception{
		String result = "";
		int cnt = 0;
		cnt = (Integer)this.paProgressSer.checkPaProgressByDeptCnt(request)!=null?this.paProgressSer.checkPaProgressByDeptCnt(request):0;
		if(cnt>=1){
			result = "E";
		}else{
			if (this.paProgressSer.copyToNextMonthPaProgressByDept(request)==1){
				result = "Y";
			}else{
				result = "N";
			}
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getSalaryLockDatePa")
	@ResponseBody
	public Map getSalaryLockDatePa (HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
			List getSalaryProvideDatePaList=this.paProgressSer.getSalaryLockDatePa(request);
			
			LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
			if(getSalaryProvideDatePaList.size()==0){
				map.put("","当前月没有符合发放日期");
			}else{
				map.put("",TipMessage.getTipMessage("hr.viewPersonalInfo.title.ADDED_BY_KELI", request));//
				for(int i=0;i<getSalaryProvideDatePaList.size();i++){
					map.put((String)((Map) getSalaryProvideDatePaList.get(i)).get("GIVE_DATE"), ((Map) getSalaryProvideDatePaList.get(i)).get("GIVE_DATE"));
				}
			}
			return map;
		
	}
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getSalaryLockStatNo")
	@ResponseBody
	public Map getSalaryLockStatNo (HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception{
		List getSalaryProvideDatePaList=this.paProgressSer.getSalaryLockStatNo(request);
		
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		/*if(getSalaryProvideDatePaList.size()==0){
			map.put("","当前月没有符合发放日期");
		}else{*/
			for(int i=0;i<getSalaryProvideDatePaList.size();i++){
				map.put((String)((Map) getSalaryProvideDatePaList.get(i)).get("STAT_NO"), ((Map) getSalaryProvideDatePaList.get(i)).get("STAT_NAME"));
			/*}*/
		}
		return map;
		
	}
	
	/**
	 * 预提工资状态查看
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewPaWithholdingProgress")
	public ModelAndView viewPaWithholdingProgressList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		
		
		List paProgressList = this.paProgressSer.getPawithholdingProgressList(request);
		int paProgressCnt = this.paProgressSer.getPawithholdingProgressCnt(request);
		
		String emptypeture  = request.getParameter("empType_isChecked");
		
		List deptList = this.paCalculateSer.getDeptAreaList(request) ;
		modelMap.put("deptList", deptList) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		modelMap.put("CPNY_ID", admin.getCpnyId());
		modelMap.put("emptypeture", emptypeture);
		modelMap.put("paYear", request.getParameter("paYear"));
		modelMap.put("paMonth", request.getParameter("paMonth"));
		modelMap.put("PAY_AREA_NAME", request.getParameter("seach_PAY_AREA_NM"));
		modelMap.put("PAY_AREA_ID", request.getParameter("seach_DEPT_NO"));
		modelMap.put("itemList", paProgressList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, paProgressCnt);
		return new ModelAndView("/pa/salary/viewPaWithholdingProgress", modelMap);
	}
	
	
	
}
