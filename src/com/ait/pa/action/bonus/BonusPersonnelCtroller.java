package com.ait.pa.action.bonus;

import java.io.PrintWriter;
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
import org.springframework.web.portlet.ModelAndView;
import com.ait.pa.service.bonus.BonusPersonnelSer;
import com.ait.pa.service.wagebase.PaAccountSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.CompanySer;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.JsonUtil;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;
import com.ait.web.util.UiUtil;

/**
 * Copyright: LDCC Company: LDCC
 * 
 * @fileName: BonusPersonnelCtroller.java
 * @Description:
 * @Create date: 2012-1-17 下午03:09:17
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
@Controller
@RequestMapping(value = "/pa/bonus")
public class BonusPersonnelCtroller {
	Logger logger = Logger.getLogger(BonusPersonnelCtroller.class);

	@Autowired
	private BonusPersonnelSer bonusPersonnelSer;

	@Autowired
	private CompanySer companySer;

	@Autowired
	private ToolMenuSer toolMenuSer;

	@Autowired
	private PaAccountSer paAccountSer;

	/**
	 * 跳转到奖金计算人员列表页面(view Bonus Personnel List)
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewBonusPersonnel")
	public ModelAndView viewBonusPersonnelList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		List bonusPersonnelList = this.bonusPersonnelSer
				.getBonusPersonnelList(request);
		int bonusPersonnelCnt = this.bonusPersonnelSer
				.getBonusPersonnelCnt(request);

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		modelMap.put("CPNY_ID", paramMap.get("CPNY_ID") == null ? admin
				.getCpnyId() : paramMap.get("CPNY_ID").toString());

		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("compList", this.companySer.getCompanyItemAllList(request));
		modelMap.put("bonusPersonnelList", bonusPersonnelList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, bonusPersonnelCnt);
		modelMap.put("toolbarInfo",request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "2369"));

		return new ModelAndView("/pa/bonus/viewBonusPersonnel", modelMap);
	}

	/**
	 * 跳转到奖金计算人员增加页面(add Bonus Personnel View)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateBonusPersonnelView")
	public ModelAndView updateBonusPersonnelView(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		Object bnPersonnel = this.bonusPersonnelSer
				.getBonusPersonnelInfo(request);
		// List prSearchEmployeeList = this.paBasicSer
		// .getPaSearchEmployeeForBonusList(request);
		// int prSearchEmployeeCnt = this.paBasicSer
		// .getPaSearchEmployeeForBonusCnt(request);

		modelMap.put("bnPersonnel", bnPersonnel);
		// modelMap.put(UiUtil.TOTAL_COUNT_NAME, prSearchEmployeeCnt);
		// modelMap.put("PA_MONTH", PA_MONTH);

		return new ModelAndView("/pa/bonus/updateBonusPersonnelView", modelMap);
	}

	/**
	 * 处理奖金计算人员请求(add Bonus Personnel Info)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateBonusPersonnelInfo")
	@ResponseBody
	public Map<String, Object> updateBonusPersonnelInfo(
			HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			int errorInt = this.bonusPersonnelSer
					.updateBonusPersonnelInfo(request);
			if (errorInt == 0) {
				map.put("message", TipMessage.getTipMessage(
						"alert.message.update_success", request));
				map.put("statusCode", "200");
				map.put("navTabId", "pa0602");
			} else {
				map.put("statusCode", "300");
				map.put("message", TipMessage.getTipMessage(
						"alert.message.update_fail", request));
			}
		} catch (Exception e) {
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_fail", request));
		}
		return map;
	}

	/**
	 * 查询出奖金对象列表
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author zhengxiaochen zhengxiaochen@ait.net.cn
	* @date 2013-9-4 上午11:44:09 
	* @version V1.0
	 */
	@RequestMapping(value = "/viewBonusObject")
	public ModelAndView viewBonusObjectList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		if(!"".equals(request.getParameter("seach_BONUS_GIVE_DATE")) && request.getParameter("seach_BONUS_GIVE_DATE") !=null ){
			modelMap.put("itemList", this.bonusPersonnelSer.getBonusObjectList(request) );
			modelMap.put("defaultCpny", admin.getCpnyId());
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.bonusPersonnelSer.getBonusObjectListCnt(request) ) ;
			modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
			modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
					toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "123442")) ;
			modelMap.put("CALC_FLAG", request.getParameter("seach_BONUS_CALC_FLAG"));
		}
		
		return new ModelAndView("/pa/bonus/viewBonusObject",modelMap);
	}
	
	/**
	 * 导出账户信息列表excel（get view Pa Account List）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewBonusObjectExcel")
	public ModelAndView viewPaBonusObjectExcel(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		modelMap.put("itemList", this.bonusPersonnelSer.getBonusObjectList(request));
		modelMap.put("defaultCpny", admin.getCpnyId());
//		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
//		toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "2369")) ;
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
		toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "123442")) ;

		return new ModelAndView("/pa/wagebase/viewBonusObjectExcel",modelMap);

	}
	
	/**
	 * 更新计算标识
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author zhengxiaochen zhengxiaochen@ait.net.cn
	* @date 2013-9-5 下午12:08:51 
	* @version V1.0
	 */
	@RequestMapping(value = "/updateBonusCalcFlagByPersonId")
	@ResponseBody
	public String updateBonusCalcFlagByPersonId(HttpServletRequest request)throws Exception{
		String result = "";
		if (this.bonusPersonnelSer.updateBonusCalcFlagByPersonId(request)==1){//奖金维护-->计算人员
				result = "Y";
			}else{
				result = "N";
			}
		return result;
	}
	
	/**
	 * 处理删除奖金计算人员请求(delete Bonus Personnel Info)
	 * 
	 * @param List
	 * @return
	 * 
	 * @SuppressWarnings("unchecked")
	 * @RequestMapping(value = "/deleteBonusPersonnelInfo")
	 * @ResponseBody public Map deleteBonusPersonnelInfo(HttpServletRequest
	 *               request) throws Exception { Map<String, Object> map = new
	 *               HashMap<String, Object>(); try {
	 *               this.bonusPersonnelSer.deleteBonusPersonnelInfo(request);
	 *               map.put("statusCode", "200"); map.put("message", "删除成功");
	 *               map.put("navTabId", "pa0602");
	 *  } catch (Exception e) { map.put("statusCode", "300");
	 * map.put("message", "删除失败"); } return map; }
	 */
	
	/**
	 * 奖金计算对象
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author zhengxiaochen zhengxiaochen@ait.net.cn
	* @date 2013-9-15 下午11:38:42 
	* @version V1.0
	 */
	@RequestMapping("/bonusCalculationObject")
	@ResponseBody
	public void bonusCalculationObject(HttpServletRequest request,HttpServletResponse response)throws Exception{
	
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");

		String returnString = this.paAccountSer.calculateObject(request);

		PrintWriter out = response.getWriter();
		out.println(JsonUtil.writeInternal(TipMessage.getTipMessage(returnString,request)));

		out.flush();
		out.close();
	}
	
	/**
	 * 跳转到修改页面(updatePaComputeItemView)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateBonusObjectView")
	public ModelAndView updateBonusObjectCtrollerView(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		Object bonusObject = this.bonusPersonnelSer.getBonusObjectCtrollerInfo(request);
		
		Map map = new HashMap();
		map = (HashMap)bonusObject;
		
		modelMap.put("bonusObject", bonusObject);
		modelMap.put("pageNum", request.getParameter("pageNum"));
		
		return new ModelAndView("/pa/bonus/updateBonusObjectView",modelMap);
	}
	
	/**
	 * 执行修改（updatePaAccountInfo）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/updateBonusObjectInfo")
	@ResponseBody
	public Map updateBonusObjectInfo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		int errorInt=this.bonusPersonnelSer.updateBonusObjectInfo(request) ;
		if(errorInt==1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_success", request) );
			map.put("forwardUrl", "/pa/bonus/viewBonusObject?pageNum="+request.getParameter("bonusPageNum")+"&menuNo=123442&navTabId=pa0612&seach_BONUS_GIVE_DATE="+request.getParameter("BONUS_GIVE_DATE")+"&seach_bonusYear="+request.getParameter("bonusPaYear")+"&seach_bonusMonth="+request.getParameter("bonusPaMonth"));
			map.put("navTabId", "pa0612");
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage(
					"alert.message.update_fail", request) );
		}
		return map;
	}
}
