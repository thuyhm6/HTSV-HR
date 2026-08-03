package com.ait.is.action.insurancesystem;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ait.web.util.UiUtil;

/**
 * 汇缴核算
 * 
 * @ClassName:CheckComputationCtroller
 * @Description: TODO
 * @author bai chenfeifei
 * 
 */

@Controller
@RequestMapping(value="/is/insurancesystem")
public class CheckComputationCtroller {
	Logger logger = Logger.getLogger(ObjectMangementCtroller.class);
	  /**
     * 跳转到汇缴核算信息显示页面
     * @param request
     * @param response
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/viewCheckComputation")
	public ModelAndView viewCheckComputationList(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
    	/*List objList = this.objSer.getObjManagementList(request);
		int objCnt = this.objSer.getObjManagementCnt(request);
		modelMap.put("ObjList", objList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, objCnt);
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "124906")) ;*/
		return new ModelAndView("/is/insurancesystem/viewCheckComputation",modelMap);
	}
    
}
