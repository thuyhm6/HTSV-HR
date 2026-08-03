package com.ait.pa.service.imp.salary;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ait.ar.service.ArMonthSer;
import com.ait.ar.service.ItemsSer;
import com.ait.pa.dao.BonusComputeItemParamDao;
import com.ait.pa.dao.BonusInputItemParamDao;
import com.ait.pa.dao.InsuranceComputeItemDao;
import com.ait.pa.dao.InsuranceInputItemDao;
import com.ait.pa.dao.PaBasicItemDao;
import com.ait.pa.dao.PaComputeItemDao;
import com.ait.pa.dao.PaInputItemParamDao;
import com.ait.pa.dao.PaResultDao;
import com.ait.pa.service.salary.PaResultSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: PaResultSerImp.java
 * @Description:
 * @Create date: 2012-1-17 下午03:12:56
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
@Service
public class PaResultSerImp implements PaResultSer {

	Logger logger = Logger.getLogger(PaResultSerImp.class);
	
	@Autowired
	private PaResultDao paResultDao ;
	
	@Autowired
	private PaComputeItemDao paComputeItemDao ;
	
	@Autowired
	private PaInputItemParamDao paInputItemParamDao ;
	
	@Autowired
	private PaBasicItemDao paBasicItemDao ;
	
	@Autowired
	private InsuranceInputItemDao insuranceInputItemDao ;
	
	@Autowired
	private InsuranceComputeItemDao insuranceComputeItemDao ;
	
	@Autowired
	private BonusComputeItemParamDao bonusComputeItemParamDao ;
	
	@Autowired
	private ArMonthSer arMonthSer;
	
	@Autowired
	private ItemsSer itemsSer ;
	
	@Autowired
	private BonusInputItemParamDao bonusInputItemParamDao;
	
	/**
	 * 方法说明（中文，英文）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Map getPaResultAllItem(HttpServletRequest request)throws Exception{
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		paramMap.put("PERSON_ID", admin.getPersonId() != null ? admin.getPersonId() : "");
		paramMap.put("USERNAME", admin.getUsername() != null ? admin.getUsername() : "");
		//"1"表示为工资的计算结果
		paramMap.put("FUNCTIONFLAG", "1");
		request.setAttribute("FUNCTIONFLAG", "1");
		String distinguish=null;
		String menuNo = StringUtil.checkNull(request.getParameter("menuNo"));
		/*if(menuNo.equals("2404")){
			distinguish="1";
		}else if(menuNo.equals("123521")){
			distinguish="2";
		}*/
		distinguish = menuNo;
		paramMap.put("DISTINGUISH", distinguish);
		request.setAttribute("DISTINGUISH", distinguish);
		//工资计算项目
		List paComputeItemList = this.paComputeItemDao.getPaComputeItemParamList(paramMap);
		//工资输入项目
		List paInputItemList = this.paInputItemParamDao.getPaInputItemParamList(paramMap);
		//工资基础项目
//		List paBasicInputItemList = this.paBasicItemDao.getPaBasicItemParamList(paramMap);
		//保险输入项目
//		List insuranceInputItemList = this.insuranceInputItemDao.getInsuranceInputItemParamList(paramMap);
		//保险计算项目
//		List insuranceComputeItemList = this.insuranceComputeItemDao.getInsuranceComputeItemParamList(paramMap) ;
		
		//人事项目
		paramMap.put("TABLE_NAME", "PA_HR_V") ;
		List hrItemList = new ArrayList() ;
		LinkedHashMap tMap = new LinkedHashMap() ;
		tMap.put("DISTINCT_FIELD", "PAY_SCHEDULE_NO") ;
		//tMap.put("FIELD_NAME", "工资月") ;
		tMap.put("FIELD_NAME", TipMessage.getTipMessage("ess.empInfo.pay_plan", request)+" No") ;//工资支付计划
//		LinkedHashMap gMap = new LinkedHashMap() ;
//		gMap.put("DISTINCT_FIELD", "GIVE_DATE");
//		//gMap.put("FIELD_NAME", "工资发放日");
//		if(("ko").equals(admin.getLanguage().toString())){
//			gMap.put("FIELD_NAME", "급여발급일") ;
//		}else{
//			gMap.put("FIELD_NAME", "工资发放日") ;
//		}
		hrItemList.add(tMap) ;
//		hrItemList.add(gMap) ;
		hrItemList.addAll(this.insuranceInputItemDao.getDistinctFieldList(paramMap)) ;
		
		//考勤项目
		paramMap.put("TABLE_NAME", "PA_AR_"+admin.getCpnyId()+"_V") ;
		List arItemList = this.arMonthSer.getArColumns(request);
//		//日考勤项目
//		List arItemDayList = this.itemsSer.getAllItemList(request) ;
		
		LinkedHashMap itemMap = new LinkedHashMap() ;
		itemMap.put("paComputeItemList", paComputeItemList) ;
		itemMap.put("paInputItemList", paInputItemList) ;
//		itemMap.put("paBasicInputItemList", paBasicInputItemList) ;
//		itemMap.put("insuranceComputeItemList", insuranceComputeItemList) ;
//		itemMap.put("insuranceInputItemList", insuranceInputItemList) ;
		itemMap.put("hrItemList", hrItemList) ;
		itemMap.put("arItemList", arItemList) ;
//		itemMap.put("arItemDayList", arItemDayList) ;
		itemMap.put("menuNo", menuNo);
		
		return itemMap ;
	}

	@SuppressWarnings("unchecked")
	public Map getPaHistoryAllItem(HttpServletRequest request) {
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		paramMap.put("PERSON_ID", admin.getPersonId() != null ? admin.getPersonId() : "");
		paramMap.put("USERNAME", admin.getUsername() != null ? admin.getUsername() : "");
		//"1"表示为工资的计算结果
		paramMap.put("FUNCTIONFLAG", "4");
		request.setAttribute("FUNCTIONFLAG", "4");
		String distinguish=null;

		String menuNo = StringUtil.checkNull(request.getParameter("menuNo"));
		/*if(menuNo.equals("122025")){
			distinguish="1";
		}else if(menuNo.equals("123523")){
			distinguish="2";
		}*/
		distinguish = menuNo;
		paramMap.put("DISTINGUISH", distinguish);
		request.setAttribute("DISTINGUISH", distinguish);
		//工资计算项目
		List paComputeItemList = this.paComputeItemDao.getPaComputeItemParamList(paramMap);
		//工资输入项目
		List paInputItemList = this.paInputItemParamDao.getPaInputItemParamList(paramMap);
		//工资基础项目
		List paBasicInputItemList = this.paBasicItemDao.getPaBasicItemParamList(paramMap);
		//保险输入项目
		List insuranceInputItemList = this.insuranceInputItemDao.getInsuranceInputItemParamList(paramMap);
		//保险计算项目
		List insuranceComputeItemList = this.insuranceComputeItemDao.getInsuranceComputeItemParamList(paramMap) ;
		//奖金输入项目
		List bonusInputItemList = this.bonusInputItemParamDao.viewBonusInputItemParamList(paramMap);
		//奖金计算项目
		List bonusComputeItemList = this.bonusComputeItemParamDao.getBonusComputeItemParamList(paramMap) ;
		
		//人事项目
		paramMap.put("TABLE_NAME", "PA_HR_V") ;
		List hrItemList = new ArrayList() ;
		LinkedHashMap tMap = new LinkedHashMap() ;
		tMap.put("DISTINCT_FIELD", "PA_MONTH") ;
		//tMap.put("FIELD_NAME", "") ;
		if(("ko").equals(admin.getLanguage().toString())){
			tMap.put("FIELD_NAME", "급여월") ;
		}else{
			tMap.put("FIELD_NAME", "工资月") ;
		}
		hrItemList.add(tMap) ;
		LinkedHashMap gMap = new LinkedHashMap() ;
		gMap.put("DISTINCT_FIELD", "GIVE_DATE") ;
		//gMap.put("FIELD_NAME", "") ;
		if(("ko").equals(admin.getLanguage().toString())){
			gMap.put("FIELD_NAME", "급여발급일") ;
		}else{
			gMap.put("FIELD_NAME", "工资发放日") ;
		}
		hrItemList.add(gMap);
		hrItemList.addAll(this.insuranceInputItemDao.getDistinctFieldList(paramMap)) ;
		
		//考勤项目
		paramMap.put("TABLE_NAME", "PA_AR_"+admin.getCpnyId()+"_V") ;
		List arItemList = this.arMonthSer.getArColumns(request);
		//日考勤项目
		List arItemDayList = this.itemsSer.getAllItemList(request) ;
		
		LinkedHashMap itemMap = new LinkedHashMap() ;
		itemMap.put("paComputeItemListHistory", paComputeItemList) ;
		itemMap.put("paInputItemListHistory", paInputItemList) ;
		itemMap.put("paBasicInputItemListHistory", paBasicInputItemList) ;
		itemMap.put("insuranceComputeItemListHistory", insuranceComputeItemList) ;
		itemMap.put("insuranceInputItemListHistory", insuranceInputItemList) ;
		itemMap.put("bonusComputeItemListHistory", bonusComputeItemList) ;
		itemMap.put("bonusInputItemListHistory", bonusInputItemList) ;
		itemMap.put("hrItemListHistory", hrItemList) ;
		itemMap.put("arItemListHistory", arItemList) ;
		itemMap.put("arItemDayListHistory", arItemDayList) ;
		itemMap.put("menuNo", menuNo);
		
		return itemMap ;
		
	}
	
	/**
	 * 方法说明（中文，英文）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Map getPaPortalAllItem(HttpServletRequest request){
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		if (paramMap != null && paramMap.get("CPNY_ID") == null) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		
		//工资计算项目
		List paComputeItemList = this.paComputeItemDao.getPaComputeItemParamList(paramMap);
		//工资输入项目
		List paInputItemList = this.paInputItemParamDao.getPaInputItemParamList(paramMap);
		//工资基础项目
		List paBasicInputItemList = this.paBasicItemDao.getPaBasicItemParamList(paramMap);
		//保险输入项目
		List insuranceInputItemList = this.insuranceInputItemDao.getInsuranceInputItemParamList(paramMap);
		//保险计算项目
		List insuranceComputeItemList = this.insuranceComputeItemDao.getInsuranceComputeItemParamList(paramMap) ;
		//奖金输入项目
		List bonusInputItemList = this.bonusInputItemParamDao.viewBonusInputItemParamList(paramMap);
		//奖金计算项目
		List bonusComputeItemList = this.bonusComputeItemParamDao.getBonusComputeItemParamList(paramMap) ;
		
		//人事项目
		paramMap.put("TABLE_NAME", "PA_HR_V") ;
		List hrItemList = new ArrayList() ;
		LinkedHashMap tMap = new LinkedHashMap() ;
		tMap.put("DISTINCT_FIELD", "PA_MONTH") ;
		tMap.put("FIELD_NAME", "工资月") ;
		hrItemList.add(tMap) ;
		hrItemList.addAll(this.insuranceInputItemDao.getDistinctFieldList(paramMap)) ;
		
		//考勤项目
		paramMap.put("TABLE_NAME", "PA_AR_"+admin.getCpnyId()+"_V") ;
		List arItemList = this.arMonthSer.getArColumns(request);
		//日考勤项目
		List arItemDayList = this.itemsSer.getAllItemList(request) ;
		
		LinkedHashMap itemMap = new LinkedHashMap() ;
		itemMap.put("paComputeItemListPortal", paComputeItemList) ;
		itemMap.put("paInputItemListPortal", paInputItemList) ;
		itemMap.put("paBasicInputItemListPortal", paBasicInputItemList) ;
		itemMap.put("insuranceComputeItemListPortal", insuranceComputeItemList) ;
		itemMap.put("insuranceInputItemListPortal", insuranceInputItemList) ;
		itemMap.put("bonusComputeItemListPortal", bonusComputeItemList) ;
		itemMap.put("bonusInputItemListPortal", bonusInputItemList) ;
		itemMap.put("hrItemListPortal", hrItemList) ;
		itemMap.put("arItemListPortal", arItemList) ;
		itemMap.put("arItemDayListPortal", arItemDayList) ;
		
		return itemMap ;
		
	}
	
	/**
	 * 工资结算
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String paBalance(HttpServletRequest request) {
		String returnString = "" ;	
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("PA_SUPERVISOR_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		
		returnString = paResultDao.paBalance(paramMap) ;
		
		return returnString ;
	}

	/**
	 * 查询工资发放日
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author zhengxiaochen zhengxiaochen@ait.net.cn
	* @date 2013-9-20 下午06:10:07 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPaGiveDate(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		LinkedHashMap paramMap=new LinkedHashMap();
		paramMap.put("TABLE_NAME", request.getParameter("TABLE_NAME") == null ? "PA_CALC_OBJECT" :request.getParameter("TABLE_NAME"));
		paramMap.put("PA_MONTH", request.getParameter("PA_MONTH") == null ? new SimpleDateFormat("yyyyMM").format(new Date()) : request.getParameter("PA_MONTH"));
		paramMap.put("CREATED_BY", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return this.paResultDao.getiPaGiveDate(paramMap);
	}

}
