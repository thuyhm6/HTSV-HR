package com.ait.ar.action.attendanceSettings;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;

 
import com.ait.ar.service.SummaryFormulaSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.GetMapByPaArUtil;
import com.ait.web.util.SessionUtil;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: SummaryFormulaCtroller.java
 * @Description:
 * @Create date: 2012-1-13 下午03:58:29
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
@Controller
@RequestMapping(value = "/ar/attendanceSettings")
public class SummaryFormulaCtroller {

	Logger logger = Logger.getLogger(SummaryFormulaCtroller.class);
	
	@Autowired
 	private SummaryFormulaSer SummaryFormulaSer ;
	@Autowired
	private ToolMenuSer toolMenuSer;
	
	/**
	 * 汇总公式菜单跳转(view Summary Formula)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewSummaryFormula")
	public ModelAndView viewSummaryFormula(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		List summaryFormulaItemList = this.SummaryFormulaSer.getSummaryFormulaItemList(request) ;
		modelMap.put("SummaryFormulaItemList", summaryFormulaItemList) ;
		
		HttpSession session = request.getSession() ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		modelMap.put("CPNY_ID", admin.getCpnyId()) ;
		modelMap.put("menuNo", request.getParameter("menuNo") != null ? request.getParameter("menuNo") : "2354") ;
		
		return new ModelAndView("/ar/attendanceSettings/viewSummaryFormula",modelMap);
	}
	
	/**
	 * 点击汇总项目列表跳转(view Formula List)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewFormulaList")
	public ModelAndView viewFormulaList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
	  List formulas = this.SummaryFormulaSer.getSummaryFormulaToCN(request) ;
	  modelMap.put("formulas", formulas);
	  modelMap.put("ITEM_NO", request.getParameter("ITEM_NO"));
	  modelMap.put("CPNY_ID", request.getParameter("CPNY_ID"));
	  
	  modelMap.put("toolbarInfo", toolMenuSer.getToolMenuForNo(request,"2354")) ;
	  return new ModelAndView("/ar/attendanceSettings/viewFormulaList",modelMap);
	}
	
	/**
	 * 公式添加跳转(add SummaryFormula View)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/addSummaryFormulaView")
	public ModelAndView addSummaryFormulaView(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		modelMap.put("ITEM_NO", request.getParameter("ITEM_NO"));
		modelMap.put("CPNY_ID", request.getParameter("CPNY_ID"));
		
		return new ModelAndView("/ar/attendanceSettings/addSummaryFormulaView",modelMap);
	}
	
	/**
	 * 公式保存(add SummaryFormula Info)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addSummaryFormulaInfo",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addSummaryFormulaInfo(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		int result = this.SummaryFormulaSer.addFormulaItem(request); 
		
	    if(result == 1){
			map.put("statusCode", "200");
			map.put("rel", "jbsxBoxItemFormula");
			map.put("message", TipMessage.getTipMessage("alert.message.add_success",request));//添加成功
		}else{
			// TODO Auto-generated catch block
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.add_fail",request));//保存失败
		}
	    return map;
	}
	
	/**
	 * 公式查看和编辑跳转(update SummaryFormula View)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateSummaryFormulaView")
	public ModelAndView updateSummaryFormulaView(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		 logger.getLogger(getClass()).debug("funo:"+request.getParameter("FORMULAR_NO"));
		modelMap.put("formula",this.SummaryFormulaSer.getFormulaInfo(request)) ;
        return new ModelAndView("/ar/attendanceSettings/updateSummaryFormulaView",modelMap);
	}
	
	/**
	 * 公式修改保存(update SummaryFormula Info)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateSummaryFormulaInfo",method = RequestMethod.POST)
	@ResponseBody
	public Map updateSummaryFormulaInfo(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
          
		Map<String, Object> map = new HashMap<String, Object>();
		int result = this.SummaryFormulaSer.updateFormulaItem(request);
		
	    if(result == 1){
	    	map.put("statusCode", "200");
			map.put("rel", "jbsxBoxItemFormula");
			map.put("message", TipMessage.getTipMessage("alert.message.update_success",request));//修改成功
			
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.update_fail",request));//修改失败
		}
	    
		return map;		
	}
	
	/**
	 * 工具按钮跳转(View FormularTool)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/ViewFormularTool",method = RequestMethod.GET)
	public ModelAndView ViewFormularTool(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		 List arItems = this.SummaryFormulaSer.getItemList(request) ;
	     modelMap.put("arItems", arItems) ;
	     
	     List summaryFormulaItemList = this.SummaryFormulaSer.getSummaryFormulaItemList(request) ;
	     modelMap.put("summaryItems", summaryFormulaItemList) ;
	     
		 List PersonBasicInfoList = this.SummaryFormulaSer.getPersonBasicInfo(request) ;
	     modelMap.put("PersonBasicInfoList", PersonBasicInfoList) ;
		
		return new ModelAndView("/ar/attendanceSettings/ViewFormularTool",modelMap);
	}
	@RequestMapping(value = "/getsummaryFormulaList",method = RequestMethod.POST)
	@ResponseBody
	public List getsummaryFormulaList(HttpServletRequest request)throws Exception{
		
		List getsummaryFormulaList = this.SummaryFormulaSer.getSummaryFormulaToCN(request);
	 
		return getsummaryFormulaList;		
	}
	
	/**
	 * 公式删除(delete Formula Info)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteSummaryFormulaInfo",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteFormulaInfo(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) throws Exception{
        
        Map<String, Object> map = new HashMap<String, Object>();
        
        int result = this.SummaryFormulaSer.deleteFormulaInfo(request);
	    if(result == 1){
	    	 map.put("statusCode", "200");
	    	 map.put("rel", "jbsxBoxItemFormula");
	    	 map.put("message", TipMessage.getTipMessage("alert.message.delete_success",request));//删除成功
	    	 map.put("menuNo", request.getParameter("menuNo"));
		}else{
			 map.put("statusCode", "300");
	    	 map.put("message", TipMessage.getTipMessage("alert.message.delete_fail",request));//删除失败
	    	 map.put("menuNo", request.getParameter("menuNo"));
		}
		return map;		
	}
	
	
}
