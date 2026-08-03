package com.ait.is.action.insurancesystem;

import java.util.HashMap;

import java.util.ArrayList;
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
import org.springframework.web.servlet.ModelAndView;

import com.ait.is.service.JoinInsureSer;
import com.ait.pa.service.excelUtil.ExcelUtilSer;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.UiUtil;
/**
 * 参保管理 (包括对象增加,减少 ,管理)
 * 
 * @ClassName:JoinInsureMangementCtroller
 * @Description: TODO
 * @author bai chenfeifei
 * 
 */

@Controller
@RequestMapping(value="/is/insurancesystem")
public class JoinInsureMangementCtroller {
	
	Logger logger = Logger.getLogger(JoinInsureMangementCtroller.class);
    @Autowired
	private  JoinInsureSer joinInstanceSer;
    @Autowired
    private ToolMenuSer toolMenuSer;
    @Autowired
	private ExcelUtilSer excelUtilSer;
    /**
     * 跳转到参保管理信息显示页面
     * @param request
     * @param response
     * @param modelMap
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/viewJoinInstanceMangement")
	public ModelAndView viewJoinInstanceList(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws Exception {
		List joinInstanceList = this.joinInstanceSer.getJoinInsureList(request);
		int joinInstanceCnt = this.joinInstanceSer.getJoinInsureCnt(request);
		modelMap.put("joinList", joinInstanceList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, joinInstanceCnt);
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "124904")) ;
		
		return new ModelAndView("/is/insurancesystem/ViewJoinInstanceMangement",modelMap);
	}
  
    @RequestMapping(value="/deleteJoinInsureInfo")
    @ResponseBody
	public Map<String, Object> deleteJoinInsureInfo(
			HttpServletRequest request, HttpServletResponse response) throws Exception {
    
    	Map<String, Object> map = new HashMap<String, Object>();
		int result = this.joinInstanceSer.deleteJoinInsureInfo(request) ;
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_success",request));//删除成功
			map.put("navTabId", "bx0103");
		}else{	
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_fail",request));//删除失败
		}	
	return map;
	}
    /**
	 * 进入修改页面
	 */
	@RequestMapping(value = "/updateJoinInsuranceNum")
	public ModelAndView updateUserView(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		String str = request.getParameter("ids");
		String[] ids = str.split(",");
		for(String id:ids){
			this.joinInstanceSer.allowPaBenJoinInsureUpdateBz(id);
		}
		List list = joinInstanceSer.getAllowPaBenJoinInsureUpdateBz();//预修改的信息集合
		modelMap.put("allowUpdate", list);
		return new ModelAndView("/is/insurancesystem/updateJoinInsuranceNum",modelMap);
	}
	  /**
		 * 恢复修改状态
	 * @throws Exception 
		 */
		@RequestMapping(value = "/editJoinInsurance_a")
		@ResponseBody
		public Map callbackJoinInsureState(HttpServletRequest request)throws Exception{
			Map<String, Object> map = new HashMap<String, Object>();
			joinInstanceSer.backPaBenJoinInsureUpdateBz();//未修改或者修改后进行“允许修改”的状态恢复
			map.put("navTabId", "bx0103");
			return map;
		}
		
		 /**
		 * 修改提交上来的信息
	 * @throws Exception 
		 */
		@RequestMapping(value = "/saveUpdJoinInsurance")
		@ResponseBody
		public Map saveUpdJoinInsurance(HttpServletRequest request)throws Exception{
			Map<String, Object> map = new HashMap<String, Object>();
			
			int result = joinInstanceSer.updatePaBenManageAddInfoBz(request);
			if(result == 1){
				map.put("statusCode", "200");
				map.put("message", TipMessage.getTipMessage("alert.message.update_success",request));//修改成功
				map.put("navTabId", "bx0103");
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
		@RequestMapping(value = "/insJoinNumListExcel")
		public ModelAndView insJoinNumListExcel(HttpServletRequest request,
					HttpServletResponse response,ModelMap modelMap) throws Exception{
			
			List insJoinNumInfoList = this.joinInstanceSer.getInsJoinNumInfoExcel(request) ;
			modelMap.put("itemList", insJoinNumInfoList);
			
			return new ModelAndView("/is/insurancesystem/viewNOInsJoinNumInfoExcel",modelMap);
		}
		/**
		 * 下载导入模板
		 */
		@RequestMapping(value = "/downloadJoinTemplete")
		public void downloadJoinTemplete(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws Exception {
			List aliasNameList = new ArrayList();

			aliasNameList.add("职号*");
			aliasNameList.add("姓名*");
			aliasNameList.add("开始缴纳月");
			aliasNameList.add("入社基数");
			List list = new ArrayList();
			LinkedHashMap map = new LinkedHashMap();
			map.put("CELL0", "H12000003");
			map.put("CELL1", "张三");
			map.put("CELL2", "201203");
			map.put("CELL3", "4000.5");

			list.add(map);
			String name = "JOIN_INSURE_DATE";
			LinkedHashMap sqlContentmap = this.excelUtilSer.putIntoSqlContentMap(list);
			this.excelUtilSer.exportExcelByName(request, response, modelMap, sqlContentmap, aliasNameList, null, name);
		}
}
