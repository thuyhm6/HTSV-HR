package com.ait.hrm.action.searchTransferOrder;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;

import com.ait.hrm.service.EmpInfoSer;
import com.ait.hrm.service.TransactionViewSer;
import com.ait.pa.service.excelUtil.ExcelUtilSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.DateUtil;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

/**
 * 入职发令查看和取消(entry transaction view and cancel)
 * 
 * @Copyright: LDCC
 * @Company: LDCC
 * @fileName: SearchEntryCtroller.java
 * @Description:
 * @Create date: Feb 27, 2012 9:26:49 PM
 * @Create by: zhoulei(zhoulei@ait.net.cn)
 * @Update Date: Feb 27, 2012 9:26:49 PM
 * @Update by: zhoulei(zhoulei@ait.net.cn)
 * @version 5.1
 */
@Controller
@RequestMapping(value = "/hrm/searchTransferOrder")
public class SearchEntryCtroller {
	@Autowired
	private TransactionViewSer transactionViewSer;
	@Autowired
	private ToolMenuSer toolMenuSer;
	@Autowired
	private ExcelUtilSer excelUtilSer;

	@Autowired
	private EmpInfoSer empInfoSer;
	
	/**
	 * 查看入职发令列表(view entry transaction list)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewEntryTransViewList")
	public ModelAndView viewEntryTransViewList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("USER_NO", admin.getUserNo());
		if(!paramMap.containsKey("FROM_TIME")){
			paramMap.put("FROM_TIME", DateUtil.getCurrentMonthStrFormat()+"-01");
			paramMap.put("TO_TIME", DateUtil.getSysdateStr());			
		}
		paramMap.put("EMPID", paramMap.get("dwz.person.empId"));
		
		modelMap.put("empTypeList", empInfoSer.getEmpTypeList(request));
		modelMap.put("searchMap", paramMap);

        String seach_FIRST_FLAG = request.getParameter("seach_FIRST_FLAG");
        if(seach_FIRST_FLAG != null && !"".equals(seach_FIRST_FLAG)){
    		modelMap.put("hrExperienceInsideList", this.transactionViewSer.getEntryTransViewList(request));
    		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.transactionViewSer.getEntryTransViewListCnt(request));
        }
        
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "2329")) ;

		return new ModelAndView("/hrm/searchTransferOrder/viewEntryTransViewList", modelMap);
	}

	/**
	 * 查看入职发令(view entry transaction)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewEntryTrans")
	public ModelAndView viewEntryTrans(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("person", this.transactionViewSer
				.viewEntryTransInfo(request));
		return new ModelAndView("/hrm/searchTransferOrder/viewEntryTrans",
				modelMap);
	}

	
	/**
	 * 查看入职发令temp表的数据(view entry transaction)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewEntryTransTemp")
	public ModelAndView viewEntryTransTemp(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("person", this.transactionViewSer
				.viewEntryTransTempInfo(request));
		return new ModelAndView("/hrm/searchTransferOrder/viewEntryTransTemp",
				modelMap);
	}
	
	/**
	 * 批量取消调动发令(batch cancel Transaction transaction)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/cancelEntryTransInBatch")
	@ResponseBody
	public Map<String, Object> cancelEntryTransInBatch(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.transactionViewSer
				.cancelHrExperienceInsideByNo(request);
		if (result == 1) {
			map.put("navTabId", "hr0504");
			map.put("message", TipMessage.getTipMessage("hr.alert.message.cancle_the_success",request));//发令取消成功
			map.put("statusCode", "200");
		} else if (result == 2) {
			map.put("message", TipMessage.getTipMessage("hr.alert.message.cannot_be_cancle",request));//有发令不能被取消,请重试!
			map.put("statusCode", "300");
		} else {
			map.put("message", TipMessage.getTipMessage("hr.alert.message.cancle_the_fail",request));//发令取消失败
			map.put("statusCode", "300");
		}
		return map;
	}
}