package com.ait.sys.action;

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
import org.springframework.web.servlet.ModelAndView;

import com.ait.hrm.service.EmpInfoSer;
import com.ait.pa.service.excelUtil.ExcelUtilSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.AffirmReplaceSer;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.util.AuthorityUtil;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

/** 
* @ClassName: AffirmReplaceCtroller 
* @Description: TODO 决裁者替换与终止
* @author 孙鹏
* @date 2014年12月1日 下午2:37:43 
*  
*/
@Controller
@RequestMapping(value = "/sys/affirmReplace")
public class AffirmReplaceCtroller {
	Logger logger = Logger.getLogger(AffirmReplaceCtroller.class);
	@Autowired
	private AffirmReplaceSer affirmreplaceser;
	
	@Autowired
	private ToolMenuSer toolMenuSer;
	@Autowired
	private EmpInfoSer empInfoSer;
	@Autowired
	private AuthorityUtil authorityUtil;
	/** 
	* @Title: viewAffirmReplace 
	* @Description: TODO 页面跳转进入决裁者替换终止页面
	* @param @return    
	* @return ModelAndView    
	* @throws 
	*/
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/viewAffirmReplaceList")
	public ModelAndView viewAffirmReplaceList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap)throws Exception
	{
		AdminBean admin=SessionUtil.getLoginUserFromSession(request);
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "298976"));
		modelMap.put("menuNo", request.getParameter("menuNo"));
		List showlist=this.affirmreplaceser.getAffirmReplaceList(request);
		int  isParamDataCnt=this.affirmreplaceser.getAffirmReplaceCnt(request);
		modelMap.put("showlist", showlist);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, isParamDataCnt);
		//以下为获取法人信息
		modelMap.put("positionList", empInfoSer.getPositionList(request));
		modelMap.put("companyList", empInfoSer.getCompanyList(request));
		modelMap.put("authority", authorityUtil.isSuperUser(admin.getPersonId()));
		modelMap.put("roleidlist",authorityUtil.getRoleId(admin.getPersonId()));
		modelMap.put("defaultCpny", request.getParameter("defaultCpny") == null ? admin.getCpnyId() : request.getParameter("defaultCpny") );
		
		
		return new ModelAndView("/sys/affirmReplace/viewAffirmReplaceList",modelMap);
	}
	/** 
	* @Title: affirmReplace 
	* @Description: TODO  跳转替换新增记录页面
	* @param @return    
	* @return ModelAndView    
	* @throws 
	*/
	@RequestMapping(value="affirmReplace")
	public ModelAndView affirmReplace(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap){
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paraMap=ObjectBindUtil.getRequestParamData(request,"seach_");
		modelMap.put("CPNY_ID", admin.getCpnyId());//实际是为法人
		modelMap.put("UPDT_USER", admin.getEmpID());//用户编码
		modelMap.put("UPDT_NN", admin.getLocalName());//用户名
		modelMap.put("TABLE_NAME", paraMap.get("TABLE_NAME"));//作用域表
		return new ModelAndView("/sys/affirmReplace/affirmReplace",modelMap);
	}
	
	/** 
	* @Title: stopAffirm 
	* @Description: TODO 跳转终止裁决页面
	* @param @param request
	* @param @param response
	* @param @param modelMap
	* @param @return    
	* @return ModelAndView    
	* @throws 
	*/
	@RequestMapping(value="stopAffirm")
	public ModelAndView stopAffirm(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap){
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paraMap=ObjectBindUtil.getRequestParamData(request,"seach_");

		modelMap.put("CPNY_ID", admin.getCpnyId());//实际是为法人
		modelMap.put("UPDT_USER", admin.getEmpID());//用户编码
		modelMap.put("UPDT_NN", admin.getLocalName());//用户名
		modelMap.put("TABLE_NAME", paraMap.get("TABLE_NAME"));//作用域表
		return new ModelAndView("/sys/affirmReplace/stopAffirm",modelMap);
	}
	
	
	/**
	 * 获得人员列表(view emp List)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewAffirmEmpList")
	public ModelAndView viewAffirmEmpList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		AdminBean admin =  SessionUtil.getLoginUserFromSession(request);
		String firstFlag = request.getParameter("firstFlag");
		if(firstFlag == null){
			modelMap.put("personList", affirmreplaceser.getAffirmEmpList(request));
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, affirmreplaceser.getAffirmEmpCnt(request));
		}else{
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, 0);
		}
		modelMap.put("limit", request.getParameter("limit"));
		modelMap.put("isEmployeement", request.getParameter("isEmployeement"));
		modelMap.put("newold", request.getParameter("newold"));
		return new ModelAndView("/sys/affirmReplace/viewAffirmEmpList", modelMap);
	}
	/** 
	* @Title: insertAffirmReplace 
	* @Description: TODO 替换的新增方法
	* @param @param request
	* @param @param response
	* @param @param modelMap
	* @param @return
	* @param @throws Exception    
	* @return Map    
	* @throws 
	*/
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value="addAffirmReplace")
	public Map insertAffirmReplace(HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap){
		LinkedHashMap<String, Object> map=new LinkedHashMap<String, Object>();
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		int  calFlag=0;
		
				calFlag=this.affirmreplaceser.insertAffirmReplaceForALL(request);
			
			if(calFlag>0){//calflage是插入数据的主键id
				map.put("statusCode", "200");
				map.put("message", "替换成功");
			}else{
				map.put("statusCode", "300");
				map.put("message", "替换出错 ");
			}
		return map;
	}
	/** 
	* @Title: insertAffirmReplace 
	* @Description: TODO 
	* @param @param request
	* @param @param response
	* @param @param modelMap
	* @param @return
	* @param @throws Exception    
	* @return Map    
	* @throws 
	*/
	@ResponseBody
	@RequestMapping(value="addStopAffirm")
	public Map insertStopAffirm(HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap)throws Exception{
		LinkedHashMap<String, Object> map=new LinkedHashMap<String, Object>();
    	int calFlag=this.affirmreplaceser.insertStopAffirmForAll(request);
    	if(calFlag>0){//calflage是插入数据的主键id
			map.put("statusCode", "200");
			map.put("message", "终止成功");
		}else{
			map.put("statusCode", "300");
			map.put("message", "终止出错 ");
		}
		return map;
	}
	/** 
	* @Title: deleteSqlMaster 
	* @Description: TODO 替换终止回退
	* @param @param request
	* @param @param response
	* @param @param modelMap
	* @param @return
	* @param @throws Exception    
	* @return Map    
	* @throws 
	*/
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deleteAffirmReplace")
	@ResponseBody
	public Map deleteAffirmReplace(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		
		int calFlag =this.affirmreplaceser.deleteAffirmReplaceForAll(request);
		if(calFlag==1){
			//map.put("navTabId", "disc0101");
			map.put("statusCode", "200");
			map.put("message", "回退成功");
			//map.put("callbackType", "closeCurrent");
		}else{
			map.put("statusCode", "300");
			map.put("message", "回退失败");
		}
		return map;
	}
}

