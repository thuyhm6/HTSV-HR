package com.ait.paEcc.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;

import com.ait.paEcc.service.PaEccService;
import com.ait.sys.bean.AdminBean;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;
/**
 * Copyright: AIT Company: AIT
 * 
 * @fileName: PaEccConfirmObjectCtroller.java
 * @Description: 经济补偿金===计算
 * @Create date: 2014-1-17 下午02:55:16
 * @Create by: 
 * @version 5.5
 */
@Controller
@RequestMapping(value="/paEcc/confirmobject")
public class PaEccConfirmObjectCtroller {
	
	@Autowired
	private PaEccService paEccService;
	
	/**
	 * 跳转到经济补偿金计算页面
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/viewPaEccCalculate")
	public ModelAndView viewPaEccCalculate(HttpServletRequest request,HttpServletResponse response,
				ModelMap modelMap){
		String paMonth = request.getParameter("year")+request.getParameter("month");
		modelMap.put("pageNum", request.getParameter("pageNum")!=null?request.
				getParameter("pageNum"):"1");
		modelMap.put("numPerPage", request.getParameter("numPerPage")!=null?request.
			getParameter("numPerPage"):"10");
		List eccEmpInfos = paEccService.getPaEccInfo(request);
		List batchList = searchBatchesByPaMonth(paMonth);
		String palFlag = paEccService.getEccFlag(request);
		modelMap.put("batches", request.getParameter("batches"));
		modelMap.put("palFlag", palFlag);
		modelMap.put("eccEmpInfos", eccEmpInfos);
		modelMap.put("year", request.getParameter("year"));
		modelMap.put("month", request.getParameter("month"));
		modelMap.put("empId", request.getParameter("empID"));
		modelMap.put("batcheList", batchList);
		modelMap.put("defaultSelect", ((LinkedHashMap)batchList.get(0)).get("BATCHVALUE"));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME,paEccService.getPaEccCnt(request));
		return new ModelAndView("/paEcc/confirmobject/viewPaEccCalculate",modelMap);
	}
	/**
	 * 删除PA_ECC_HISTORY 经济补偿金记录
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/delPaEccHisInfo")
	@ResponseBody
	public Map delPaEccHisInfo(HttpServletRequest request,HttpServletResponse response,
			ModelMap modelMap){
		Map map = new HashMap();
		int returnInt = 0;
		returnInt = paEccService.delPaEccInfo(request);
		if(returnInt>0){
			map.put("returnMsg", TipMessage.getTipMessage("alert.message.delete_success", request));// 删除成功
		}else{
			map.put("returnMsg", TipMessage.getTipMessage("alert.message.delete_fail", request));// 删除失败
		}
		return map;
	}
	
	/**
	 * 计算
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/calculatePaEcc")
	@ResponseBody
	public Map calculatePaEcc(HttpServletRequest request,HttpServletResponse response,
			ModelMap modelMap){
		Map returnMap = new HashMap();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String jsonString = request.getParameter("updates");
		JSONArray jArray= JSONArray.fromObject(jsonString);
		JSONObject jsonObject;
		Map<String, Object> valueMap = null;
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
	    for (int i = 0; i < jArray.size(); i++){ 
	    	jsonObject = jArray.getJSONObject(i);
	    	Iterator<String> keyIter = jsonObject.keys();
	        String key;
	        Object value;
	        valueMap = new LinkedHashMap<String, Object>();
	        while (keyIter.hasNext()){
	        	key = (String) keyIter.next();
	        	value = jsonObject.get(key);
	        	/**
	        	 * !key.toString().equals("eccTax")&&value.toString()=="")||
	        			(!key.toString().equals("leftType")&&!(jsonObject.get("eccTax")!="")&&
	        					!NumberUtils.isNumber(value.toString()))
	        	 */
	        	if(!key.equals("eccTax")&&value==null){
	        		returnMap.put("returnMsg",TipMessage.getTipMessage("display.pa.ecc.dataerror", request));
	        		return returnMap;
	        	}
	        	valueMap.put(key, value);
	        	valueMap.put("CPNY_ID", admin.getCpnyId());
	        	valueMap.put("PA_MONTH",request.getParameter("PA_MONTH"));
	        	valueMap.put("eccMax", "8000");
	        	valueMap.put("eccMin", "2000");
	        	valueMap.put("eccMianshui","172404");
	        }
//        	if(StringUtil.checkNull(valueMap.get("eccAmt")).toString().equals(StringUtil.checkNull(valueMap.get("oldEccAmt")).toString())
//        		&&StringUtil.checkNull(valueMap.get("noticeAmt")).toString().equals(StringUtil.checkNull(valueMap.get("oldNoticeAmt")).toString())
//        		&&!StringUtil.checkNull(valueMap.get("eccTax")).toString().equals(StringUtil.checkNull(valueMap.get("oldEccTax")).toString())){
//	        	valueMap.put("UPDATE_TAX_FLAG", "UPDATE");
//        	}
        	
        	list.add(valueMap);
	    }
	    int result = paEccService.updatePaEccInfo(list);
	    if(result >0){
	    	paEccService.calculatePaEcc(valueMap);
	    	returnMap.put("returnMsg", 
	    		TipMessage.getTipMessage("liang.alert.message.ess.trans.Successful_operation", request));// 计算完成
	    }else{
	    	returnMap.put("returnMsg", 
	    		TipMessage.getTipMessage("ar.viewararmonthcalculate.title.jisuanshibai", request));// 计算失败
	    }
		return returnMap;
	}
	/**
	 * 结算
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/settlementPaEcc")
	@ResponseBody
	public Map settlementPaEcc(HttpServletRequest request,HttpServletResponse response,
			ModelMap modelMap){
		
		Map returnMap = new HashMap();
		int result = paEccService.settlementPaEcc(request);
		if(result==1){
			returnMap.put("returnMsg", 
		    		TipMessage.getTipMessage("liang.alert.message.ess.trans.Successful_operation", request));//结算成功
		}else if(result==2){
			returnMap.put("returnMsg", 
					TipMessage.getTipMessage("display.pa.ecc.calculatefirst", request));
		}else{
			returnMap.put("returnMsg", 
		    		TipMessage.getTipMessage("liang.alert.message.ess.trans.operation_failure", request));//结算失败
		}
		return returnMap;
	}
	/**
	 * 取消结算
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/cancelsettlementPaEcc")
	@ResponseBody
	public Map cancelsettlementPaEcc(HttpServletRequest request,HttpServletResponse response,
			ModelMap modelMap){
		
		Map returnMap = new HashMap();
		int result = paEccService.cancelSettlementPaEcc(request);
		if(result>0){
			returnMap.put("returnMsg", 
		    		TipMessage.getTipMessage("liang.alert.message.ess.trans.Successful_operation", request));//取消结算成功
		}else{
			returnMap.put("returnMsg", 
		    		TipMessage.getTipMessage("liang.alert.message.ess.trans.operation_failure", request));//取消结算失败
		}
		return returnMap;
	}
	@SuppressWarnings("unchecked")
	private List searchBatchesByPaMonth(String paMonth) {
		Map parameterObject = new LinkedHashMap();
		parameterObject.put("paMonth", paMonth);
		return paEccService.searchBatchesByPaMonth(parameterObject);
	}
}
