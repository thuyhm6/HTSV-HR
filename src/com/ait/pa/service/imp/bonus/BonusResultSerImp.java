package com.ait.pa.service.imp.bonus;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ait.pa.dao.BonusComputeItemDao;
import com.ait.pa.dao.BonusInputItemDao;
import com.ait.pa.dao.BonusResultDao;
import com.ait.pa.dao.InsuranceInputItemDao;
import com.ait.pa.dao.PaResultDao;
import com.ait.pa.service.bonus.BonusResultSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: BonusResultSerImp.java
 * @Description:
 * @Create date: 2012-1-17 下午03:20:53
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
@Service
public class BonusResultSerImp implements BonusResultSer {

	Logger logger = Logger.getLogger(BonusResultSerImp.class);
	
	@Autowired
	private BonusResultDao bonusResultDao ;
	
	@Autowired
	private BonusComputeItemDao bonusComputeItemDao ;
	
	@Autowired
	private BonusInputItemDao bonusInputItemDao ;
	
	@Autowired
	private InsuranceInputItemDao insuranceInputItemDao ;

	@Autowired
	private PaResultDao paResultDao;
	
	
	/**
	 * 方法说明（中文，英文）
	 * @param parameterObject
	 * @return
	 * @throws Exception 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Map getBonusResultAllItem(HttpServletRequest request) throws Exception{
		
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("CPNY_ID", admin.getCpnyId()) ;
		paramMap.put("PERSON_ID", admin.getPersonId() !=null ? admin.getPersonId() : "");
		paramMap.put("USERNAME", admin.getPersonId() !=null ? admin.getUsername() : "");
		//"2"表示为奖金的“计算结果”
		paramMap.put("FUNCTIONFLAG", "2");
		String distinguish=null;
		String menuNo = StringUtil.checkNull(request.getParameter("menuNo"));
		/*if(menuNo.equals("2375")){
			distinguish="1";
		}else if(menuNo.equals("123522")){
			distinguish="2";
		}*/
		distinguish = menuNo;
		paramMap.put("DISTINGUISH", distinguish);
		//计算项目
		List bonusComputeItemList = this.bonusComputeItemDao.getBonusComputeItemList(paramMap) ;
		//输入项目
		List bonusInputItemList = this.bonusInputItemDao.getPaBonusInputItemList(paramMap) ;
		//工资项目
		//paramMap.put("BONUS_RELEVANCE_FLAG" , 1) ;
		//List paItemList = this.paComputeItemDao.getPaComputeItemList(paramMap) ;
		
		//人事项目
		paramMap.put("TABLE_NAME", "PA_HR_V") ;
		List hrItemList = new ArrayList() ;
		LinkedHashMap tMap = new LinkedHashMap() ;
		tMap.put("DISTINCT_FIELD", "BN_MONTH") ;
		tMap.put("FIELD_NAME", TipMessage.getTipMessage("pa.insurance.title.bonusMonth", request)) ;
		LinkedHashMap gMap = new LinkedHashMap() ;
		gMap.put("DISTINCT_FIELD", "GIVE_DATE");
		if(("ko").equals(admin.getLanguage().toString())){
			gMap.put("FIELD_NAME", "상여발급일") ;
		}else{
			gMap.put("FIELD_NAME", "奖金发放日") ;
		}
		
		hrItemList.add(tMap) ;
		hrItemList.add(gMap);
		hrItemList.addAll(this.insuranceInputItemDao.getDistinctFieldList(paramMap)) ;
		
		LinkedHashMap itemMap = new LinkedHashMap() ;
		itemMap.put("bonusComputeItemList", bonusComputeItemList) ;
		itemMap.put("bonusInputItemList", bonusInputItemList) ;
		itemMap.put("hrItemList", hrItemList) ;
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
	public String bonusBalance(HttpServletRequest request) {
		String returnString = "" ;
				
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		paramMap.put("SUPERVISOR_ID",admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());

		returnString = bonusResultDao.bonusBalance(paramMap) ;
		
		return returnString ;
	}
}
