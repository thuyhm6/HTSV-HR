package com.ait.ar.action.attendanceSettings;

import java.util.HashMap;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;

import com.ait.ar.service.ArCardAssociateSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: AttendanceKeeperCtroller.java
 * @Description:
 * @Create date: 2012-1-14 下午01:43:31
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
@Controller
@RequestMapping(value = "/ar/attendanceSettings")
public class ArCardAssociateCtroller {
	Logger logger = Logger.getLogger(ArCardAssociateCtroller.class);
	
	@Autowired
	private ArCardAssociateSer arCardAssociateSer ;
	@Autowired
	private ToolMenuSer toolMenuSer;
	
	/**
	 * 查看工号卡号关系(view CardAssociate List)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewCardAssociate")
	public ModelAndView viewCardAssociateList(HttpServletRequest request,
			HttpServletResponse response,ModelMap modelMap)throws Exception{
		
		List cardAssociateList = this.arCardAssociateSer.getCardAssociateList(request) ;
		int cardAssociateCnt = this.arCardAssociateSer.getCardAssociateCnt(request) ;

		modelMap.put("cardAssociateList", cardAssociateList) ;
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, cardAssociateCnt) ;
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "22793")) ;
	
		return new ModelAndView("/ar/attendanceSettings/viewCardAssociate", modelMap);
	}

	/**
	 * 修改保存(update CardAssociate Info)
	 * @param request
	 * @return Map
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateCardAssociateInfo")
	@ResponseBody
	public Map updateCardAssociateInfo(HttpServletRequest request)throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		String isChecked = this.arCardAssociateSer.checkCardValidity(request);
		
		if("".equals(isChecked)){
			int result = arCardAssociateSer.updateCardAssociateInfo(request) ;
			
			if(result == 1){
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage("alert.message.update_success",request));//修改成功
				map.put("navTabId", "ar0310");
			}else{
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage("alert.message.update_fail",request));//修改失败
			}
		}else{
			//请检查卡号开始结束时间
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("ar.alert.message.viewCardAssociate.pleasecheck",request)
					+ TipMessage.getTipMessage("ar.viewCardAssociate.title.kahao",request)
					+ isChecked
					+ TipMessage.getTipMessage("ar.alert.message.viewCardAssociate.startendtime",request));//请检查卡号开始结束时间
		}
		
		return map;		
	}
	
}
