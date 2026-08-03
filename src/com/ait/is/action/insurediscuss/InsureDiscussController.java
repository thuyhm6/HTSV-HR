package com.ait.is.action.insurediscuss;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.ModelAndView;

import com.ait.is.service.InsureDiscussSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.SessionUtil;


@Controller
@RequestMapping(value="/is/insureDiscuss")
public class InsureDiscussController {

	@Autowired
	InsureDiscussSer insureDiscussSer ;
	@RequestMapping(value="/viewInsureDiscuss")
	public ModelAndView viewInsureDiscuss(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap) throws ParseException {
		
		
		request.setAttribute("menu_code", request.getParameter("menu_code"));
		String method = request.getParameter("method");//方法区分
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String adminID = admin.getAdminID();//操作人	
		Map map = new HashMap();
		map.put("adminID", adminID);//操作人
		
		
		
		
		
		Map param = new HashMap();
		//取时间
		Object obj = request.getParameter("seach_InsureDiscuss_DATE");
		
		Date date =  new Date();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		
		String d="";
		if(obj!=null&&!"".equals(obj)){
			date = sdf.parse((String)obj);
			param.put("date", date);
		}else{
			d = sdf.format(date);
			param.put("date", d);
		}
		
		
		param.put("date", date);
		//put param
		
		if ("apply".equals(method)) {//数据汇总传输
			int checkApply = insureDiscussSer.checkPaBenPaymentInfoBz(param);
			int checkTotal = insureDiscussSer.checkExistsForPaBenApplyBz(param);
			if(checkTotal == 0){//尚未数据汇总
				request.setAttribute("message", "尚未数据汇总，无法汇总传输！");
			}else if(checkApply > 0){//已经汇总
				request.setAttribute("message", "已经汇总传输，如需要重新汇总传输，请先做取消操作！");
			}else{
				insureDiscussSer.paBenCompuationCreateApplyBz(map);
				request.setAttribute("message", "汇总传输成功 ！");
			}
		}else if ("cancel".equals(method)) {//取消
			int checkApply = insureDiscussSer.checkPaBenPaymentInfoBz(param);
			int checkCal = insureDiscussSer.checkPaCalBz(map);
			//如果最大裁决标志为0，则可以进行删除、取消等操作操作
			if(checkApply == 0){
				request.setAttribute("message", "尚未进行汇总传输，不必取消！");
			}else if(checkCal > 0){
				request.setAttribute("message", "工资已经计算，不可以取消！");
			}else{
				insureDiscussSer.paBenCompuationCancelApplyBz(map);
				request.setAttribute("message", "取消成功 ！");
			}
		}
		
		int checkApply = insureDiscussSer.checkPaBenPaymentInfoBz(param);
		if(checkApply > 0){//已经汇总
			request.setAttribute("over", true);
		}
		
		List list = insureDiscussSer.getPaBenManageLastCurrBz(param);
		request.setAttribute("changeList", list);
		List list2 = insureDiscussSer.getPaBenPaymentInfoListBz(param);
		request.setAttribute("payInfoList", list2);
		List<Map> adjustList = (List<Map>)insureDiscussSer.getAdjustValueBz(param);
		String reason = "";
		double adjustValue = 0;
		if (adjustList != null && adjustList.size() > 0) {
			reason = (String) adjustList.get(0).get("REASON");
			if (adjustList.get(0).get("ADJUST_VALUE") != null && !"".equals(adjustList.get(0).get("ADJUST_VALUE"))) {
				adjustValue = Double.parseDouble(adjustList.get(0).get("ADJUST_VALUE").toString());
			}
		}
		request.setAttribute("reason", reason);//调整事由
		request.setAttribute("adjustValue", adjustValue);//调整金额

		
		return new ModelAndView("/is/insureDiscuss/viewInsureDiscuss");
		
	}
	
	
}
