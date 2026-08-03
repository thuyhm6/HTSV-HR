package com.ait.pa.action.workManagement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;

import com.ait.pa.service.workManagement.PaPayScheduleSer;
import com.ait.pa.service.workManagement.PaWorkFlowSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.StringUtil;
import com.ait.web.util.UiUtil;

@Controller
@RequestMapping(value = "/pa/workManagement")
public class PaWorkFlowCtroller {
	Logger logger = Logger.getLogger(PaWorkFlowCtroller.class);

	@Autowired
	private PaPayScheduleSer paPayScheduleSer;

	@Autowired
	private PaWorkFlowSer paWorkFlowSer;

	/**
	 * 工资工作流程查看
	 * 
	 */
	@RequestMapping(value = "/viewPaWorkFlow")
	public ModelAndView viewPaWorkFlow(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List paPayScheduleList = this.paPayScheduleSer
				.getPayScheduleAllList(request);
		if(request.getParameter("PAY_SCHEDULE_NO") == null){
			if(paPayScheduleList != null && paPayScheduleList.size() > 0){
				Map paramMap = (Map)paPayScheduleList.get(0);
				
				modelMap.put("paWorkInfo", 
						this.paWorkFlowSer.getPaWorkFlowInfoByScheduleNo(request, StringUtil.checkNull(paramMap.get("PAY_SCHEDULE_NO"),""))) ;
				
				modelMap.put("PAY_SCHEDULE_NO", paramMap.get("PAY_SCHEDULE_NO"));
			}
		}else{
			modelMap.put("paWorkInfo", 
					this.paWorkFlowSer.getPaWorkFlowInfoByScheduleNo(request, StringUtil.checkNull(request.getParameter("PAY_SCHEDULE_NO"),""))) ;
			
			modelMap.put("PAY_SCHEDULE_NO", request.getParameter("PAY_SCHEDULE_NO"));
		}

		modelMap.put("PAY_OBJ_NUM", this.paWorkFlowSer.getPayObjNumByScheduleNo(modelMap.get("PAY_SCHEDULE_NO").toString()));
		
		modelMap.put("paPayScheduleList", paPayScheduleList);
		
		return new ModelAndView("/pa/workManagement/viewPaWorkFlow", modelMap);

	}
	/**
	 * 工资工作流程执行
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/execPaWorkFlow")
	@ResponseBody
	public Map execPaWorkFlow(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String result = this.paWorkFlowSer.execPaWorkFlow(request);
		if (result!=null&&result.indexOf("ERROR")==-1) {
			map.put("statusCode", "200");
			map.put("message",result);
			map.put("formId", "searchViewPaWorkFlowForm");
		}else{
			map.put("statusCode", "300");
			map.put("message",result);
			map.put("formId", "searchViewPaWorkFlowForm");
		}
		return map;
	}
	/**
	 * 工资工作流程执行记录
	 * 
	 */
	@RequestMapping(value = "/viewPaWorkFlowOperationRecordList")
	public ModelAndView viewPaWorkFlowOperationRecordList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List paWorkFlowOperationRecordList = this.paWorkFlowSer
				.getPaWorkFlowOperationRecordList(request);
		int paWorkFlowOperationRecordCnt = this.paWorkFlowSer.getPaWorkFlowOperationRecordListCnt(request);

		modelMap.put("paWorkFlowOperationRecordList", paWorkFlowOperationRecordList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, paWorkFlowOperationRecordCnt);
		
		return new ModelAndView("/pa/workManagement/viewPaWorkFlowOperationRecordList", modelMap);

	}
	/**
	 * 测试新Table
	 * 
	 */
	@RequestMapping(value = "/viewNewDataGridList")
	public ModelAndView viewNewDataGridList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		return new ModelAndView("/pa/workManagement/viewNewDataGridList", modelMap);

	}
}
