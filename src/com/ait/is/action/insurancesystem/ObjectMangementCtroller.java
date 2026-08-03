package com.ait.is.action.insurancesystem;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ait.is.service.ObjMgtSer;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.UiUtil;

/**
 * 对象管理 (包括对象增加,减少 ,管理)
 * 
 * @ClassName:ObjectMangementCtroller
 * @Description: TODO
 * @author bai chenfeifei
 * 
 */

@Controller
@RequestMapping(value="/is/insurancesystem")
public class ObjectMangementCtroller {
	
	Logger logger = Logger.getLogger(ObjectMangementCtroller.class);
    @Autowired
	private  ObjMgtSer objSer;
    @Autowired
    private ToolMenuSer toolMenuSer;
    /**
     * 跳转到对象管理信息显示页面
     * @param request
     * @param response
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/viewObjectManagement")
	public ModelAndView viewObjInsureList(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
    	List objList = this.objSer.getObjManagementList(request);
		int objCnt = this.objSer.getObjManagementCnt(request);
		modelMap.put("ObjList", objList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, objCnt);
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "124906")) ;
		return new ModelAndView("/is/insurancesystem/viewObjectManagement",modelMap);
	}
    
  /*  @RequestMapping(value="/deleteObjInsureInfo")
    @ResponseBody
	public Map<String, Object> deleteObjInsureInfo(
			HttpServletRequest request, HttpServletResponse response) throws Exception {
    
    	Map<String, Object> map = new HashMap<String, Object>();
		int result = this.objSer.deleteObjInsureInfo(request) ;
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_success",request));//删除成功
			map.put("navTabId", "bx0104");
		}else{	
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_fail",request));//删除失败
		}	
	return map;
	}
    *//**
	 * 进入修改页面
	 */
	@RequestMapping(value = "/updateObjectMgtNum")
	public ModelAndView updateUserView(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		String str = request.getParameter("ids");
		String[] ids = str.split(",");
		for(String id:ids){
			this.objSer.allowPaBenObjInsureUpdateBz(id);
		}
		@SuppressWarnings("rawtypes")
		List list = objSer.getAllowPaBenObjInsureUpdateBz(request);//预修改的信息集合
		modelMap.put("allowUpdate", list);
		return new ModelAndView("/is/insurancesystem/updateObjInsuranceNum",modelMap);
	}
	  /**
		 * 恢复修改状态
	 * @throws Exception 
		 *//*
		@RequestMapping(value = "/editObjInsurance_a")
		@ResponseBody
		public Map callbackObjInsureState(HttpServletRequest request)throws Exception{
			Map<String, Object> map = new HashMap<String, Object>();
			objSer.backPaBenObjInsureUpdateBz();//未修改或者修改后进行“允许修改”的状态恢复
			map.put("navTabId", "bx0103");
			return map;
		}
		
		 *//**
		 * 修改提交上来的信息
	 * @throws Exception 
		 */
		@RequestMapping(value = "/saveUpdObjInsurance")
		@ResponseBody
		public Map saveUpdObjInsurance(HttpServletRequest request)throws Exception{
			Map<String, Object> map = new HashMap<String, Object>();
			
			int result = objSer.updatePaBenManageAddInfoBz(request);
			if(result == 1){
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage("alert.message.update_success",request));//修改成功
				map.put("navTabId", "bx0105");
			}else{
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage("alert.message.update_fail",request));//修改失败
			}
			return map;
		}
		/**
		 * 列表的导出
		 * @param request
		 * @param response
		 * @param modelMap
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value = "/insObjNumListExcel")
		public ModelAndView insBaseNumListExcel(HttpServletRequest request,
					HttpServletResponse response,ModelMap modelMap) throws Exception{
			
			List insBaseNumInfoList = this.objSer.getInsObjNumInfoExcel(request) ;
			modelMap.put("itemList", insBaseNumInfoList);
			
			return new ModelAndView("/is/insurancesystem/viewNOInsObjNumInfoExcel",modelMap);
		}
	
}
