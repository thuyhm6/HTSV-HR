package com.ait.ess.dao.impl;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.affirm.service.AffirmInfoToLGEPSer;
import com.ait.ess.dao.AffirmApplyDao;
import com.ait.ess.dao.InfoApplyDao;
import com.ait.ess.dao.InfoApplyLeaveDao;
import com.ait.pa.dao.tempsale.PaTempSalesDAO;
import com.ait.web.util.DateUtil;
import com.ait.web.util.SqlMapClientSupport;

@Repository
public class AffirmApplyDaoImpl extends SqlMapClientSupport implements
		AffirmApplyDao {
	
	@Autowired
	private InfoApplyLeaveDao infoApplyLeaveDao;
	
	@Autowired
	private InfoApplyDao infoApplyDao;
	@Autowired
	private AffirmInfoToLGEPSer affirmInfoToLGEPSer;
	@Autowired
	PaTempSalesDAO paTempSalesDAO;
	
	private static String APPLY_TYPE_NO = "31";
	/**
	 * 加班决裁列表(overtime apply affirm list)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getOtAffirmList(Object obj) throws Exception {
		return this.getOtAffirmList(obj, -1, -1);
	}

	@SuppressWarnings("unchecked")
		@Override
		public List getAffirmorByApplyNoList(Object obj) {
			List returnList = new ArrayList();
			try {
				returnList = this.queryForList("ess.affirmApply.getAffirmorByApplyNoList_OT",obj);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return returnList;
		}
	/**
	 * 加班决裁列表(overtime apply affirm list)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getOtAffirmListTwo(Object obj) throws Exception {
		return this.getOtAffirmListTwo(obj, -1, -1);
	}
	/**
	 * 加班决裁列表(overtime apply affirm list)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getOtAffirmListFinal(Object obj) throws Exception {
		return this.getOtAffirmListFinal(obj, -1, -1);
	}
	/**
	 * 加班决裁列表(overtime apply affirm list)
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getOtAffirmList(Object obj, int currentPage, int pageSize)
			throws Exception {
		List returnList = new ArrayList();
		returnList = this.queryForList("ess.affirmApply.getOtAffirmList", obj);
		/*if (currentPage > -1 && pageSize > -1) {
			returnList = this.queryForList("ess.affirmApply.getOtAffirmList", obj, currentPage,pageSize);
		} else {
			returnList = this.queryForList("ess.affirmApply.getOtAffirmList", obj);
		}*/
		return returnList;
	}
	/**
	 * 加班决裁列表(overtime apply affirm list)
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getOtAffirmListTwo(Object obj, int currentPage, int pageSize)
			throws Exception {
		List returnList = new ArrayList();
		 
			returnList = this.queryForList("ess.affirmApply.getOtAffirmListTwo", obj);
		 
		return returnList;
	}
	/**
	 * 加班决裁列表(overtime apply affirm list)
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getOtAffirmListFinal(Object obj, int currentPage, int pageSize)
			throws Exception {
		List returnList = new ArrayList();
		if (currentPage > -1 && pageSize > -1) {
			returnList = this.queryForList("ess.affirmApply.getOtAffirmListFinal", obj, currentPage,pageSize);
		} else {
			returnList = this.queryForList("ess.affirmApply.getOtAffirmListFinal", obj);
		}
		return returnList;
	}
	 
	/**
	 * 加班决裁列表总数(overtime apply affirm total count)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getOtAffirmListCnt(Object obj) throws Exception {
		return NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ess.affirmApply.getOtAffirmListCnt", obj)),Integer.class);
	}
	/**
	 * 加班决裁列表总数(overtime apply affirm total count)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getOtAffirmListTwoCnt(Object obj) throws Exception {
		return NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ess.affirmApply.getOtAffirmListTwoCnt", obj)),Integer.class);
	}
	/**
	 * 加班决裁列表总数(overtime apply affirm total count)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getOtAffirmListFinalCnt(Object obj) throws Exception {
		return NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ess.affirmApply.getOtAffirmListFinalCnt", obj)),Integer.class);
	}
	
	/**
	 * 批量通过/否决加班申请(batch pass and reject overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int saveOvertimeApplyAffirmInBatch(List list) throws Exception {
		for (int i = 0; i < list.size(); i++) {
			LinkedHashMap map = (LinkedHashMap) list.get(i);
				this.update("ess.affirmApply.updateArDetailApplyOt", map);//UPDATE DETAIL
				this.update("ess.affirmApply.updateEssAffirmByAffirmNo", map);//UPDATE ESS_AFFRIM
				this.update("ess.affirmApply.updateEssApplyOtByApplyNo", map);//UPDATE OT_APPLY
		}
			return 1;
	}
	
	

	
	@SuppressWarnings("unchecked")
	private String getArDetailTstoApplyOfApplyNo(LinkedHashMap map) throws Exception {
		String object2 = (String) this.queryForObject("ess.affirmApply.getArDetailTstoApplyOfApplyNo", map);
		if(object2 ==null){
			object2 = "0";
		}
		return object2;
	}

	/**
	 * 通过/否决加班申请(pass and reject overtime apply)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int saveOvertimeApplyAffirm(LinkedHashMap object) throws Exception {
		String flag = object.get("FLAG") != null ? object.get("FLAG").toString() : "0";
		this.update("ess.affirmApply.updateEssAffirmByAffirmNo", object);
		this.update("ess.affirmApply.updateEssApplyOtByApplyNo", object);

		//check信息修改为已check 发送EP
		this.sendToLGEPCheckBatch(object,"31","startApplyCheckInfo");
		// 审批者决裁完后更新他添加的check信息为已check
		this.paTempSalesDAO.updateCheckFlagByEssAffirmNo(object);
		
		
		// 如果是决裁流程的最后一步通过而且也不需要人事确认
	 	if ("1".equals(flag)) { 
	 		//以下几行，是从SY_PARAM_INFO_PARAM表里查数据据说不用了，有报错，我现在注释了  by:wangqiang
				LinkedHashMap overtimeApply = (LinkedHashMap) this.queryForObject("ess.humanAffirm.getOvertimeApplyInfoByApplyNo",object);
				overtimeApply.put("ADMIN_ID", object.get("ADMIN_ID"));
				overtimeApply.put("LANGUAGE", "zh");
				overtimeApply.put("AFFIRM_TYPE", "OVERTIME_AFFIRM");
				object.put("APPLY_PERSON_ID", overtimeApply.get("PERSON_ID"));
				
				

				if (null != overtimeApply.get("APPLY_TYPE")
						&& "BATCH".equals(overtimeApply.get("APPLY_TYPE"))) {
					doBatchInsertArApplyResult(overtimeApply);
				} else {
					doInsertArApplyResult(overtimeApply);
				}
				
		}
	 	this.sendToLGEP(object);
		return 1;
	}
	/**
	 * 批量申请审批通过并完成，插入ar_apply_result
	 * 
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private void doBatchInsertArApplyResult(LinkedHashMap overtimeApply)
			throws Exception {
		List list = this.queryForList(
				"ess.infoApplyLeave.getEssOtBatchList", overtimeApply);
		for (int i = 0; i < list.size(); i++) {
			LinkedHashMap overtimeApplyMap = (LinkedHashMap) list.get(i);
			overtimeApplyMap.put("CPNY_ID", overtimeApply.get("CPNY_ID"));
			overtimeApplyMap.put("APPLY_NO", overtimeApplyMap.get("BATCH_APPLY_NO"));
			doInsertArApplyResult(overtimeApplyMap);
		}
	}
	
	
	/**
	 * 审批通过并完成，插入ar_apply_result
	 * 
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	private void doInsertArApplyResult(LinkedHashMap overtimeApply)
			throws Exception {
			 
			String lastOtYn = overtimeApply.get("IF_ZS")!=null?overtimeApply.get("IF_ZS").toString():"N";
		
			if (overtimeApply.get("OT_TIME_TYPE").equals("L")) {
				overtimeApply.put("APPLY_DATE", overtimeApply.get("APPLY_DATE")
						.toString().length() >= 10 ? overtimeApply.get(
						"APPLY_DATE").toString().substring(0, 10) : "");
			} else {
				overtimeApply.put("APPLY_DATE", overtimeApply.get("APPLY_DATE")
						.toString().length() >= 10 ? overtimeApply.get(
						"APPLY_DATE").toString().substring(0, 10) : "");
			}

			if ("Y".equals(lastOtYn)||this.isZhuiSuYn(overtimeApply)) {
					// 删除之前的AR_APPLY_RESULT_LASTMONTH的数据
					this.delete("ess.humanAffirm.deleteArApplyResultByApplyNoAndPersonIdZhuisuOt",overtimeApply);
					// 插入新的的AR_APPLY_RESULT_LASTMONTH的数据
					this.insert("ess.humanAffirm.insertOtApplyResultForArApplyZhuisu",overtimeApply);
				    LinkedHashMap param = new LinkedHashMap();
					param.put("PERSON_ID", overtimeApply.get("PERSON_ID"));
					param.put("CPNY_ID", overtimeApply.get("CPNY_ID"));
					param.put("AR_FROM_TIME", overtimeApply.get("APPLY_DATE"));
					param.put("AR_TO_TIME", overtimeApply.get("APPLY_DATE"));
					param.put("CHANGE_TYPE", "加班申请");
					this .insert( "ess.humanAffirm.insertLeaveApplyResultForArApplyAfterZhuisuOt",param);
			} else {
				// 删除之前的AR_APPLY_RESULT的数据
				this.delete("ess.humanAffirm.deleteArApplyResultByApplyNoAndPersonId",overtimeApply);
				// 插入新的的AR_APPLY_RESULT的数据
				this.insert("ess.humanAffirm.insertArApplyResultForArApply",overtimeApply);

				if(!this.isAfterLeave(overtimeApply)){
					// 插入新的的AR_SHIFT_CHANGE的数据
					LinkedHashMap param = new LinkedHashMap();
					param.put("PERSON_ID", overtimeApply.get("PERSON_ID"));
					param.put("CPNY_ID", overtimeApply.get("CPNY_ID"));
					param.put("AR_FROM_TIME", overtimeApply.get("APPLY_DATE"));
					param.put("AR_TO_TIME", overtimeApply.get("APPLY_DATE"));
					param.put("CHANGE_TYPE", "加班申请");
					
					this .insert( "ess.humanAffirm.insertLeaveApplyResultForArApplyAfter",param);
				}
			}
			
		// 计算考勤
		/*((LinkedHashMap) overtimeApply).put("caltype", "emp");
		try{
			this.insert("ess.humanAffirm.caculateDetailP", overtimeApply);
		}catch(SQLException e){
			e.printStackTrace();
		}*/
	}
	

	/**
	 * 判断是否为事后申请加班 overtime apply
	 * 
	 * @param paramMap
	 * @return
	 */
	private boolean isAfterLeave(LinkedHashMap paramMap) throws Exception {
		Date date = new Date();
		SimpleDateFormat sb = new SimpleDateFormat("yyyy-MM-dd");

		String leaveApplyFrom = paramMap.get("APPLY_DATE") != null ? paramMap
				.get("APPLY_DATE").toString()
				: sb.format(date);
		String sysDateStr = sb.format(date);

		GregorianCalendar applyFrom = DateUtil
				.ParseGregorianCalendar(leaveApplyFrom);
		GregorianCalendar sysDate = DateUtil.ParseGregorianCalendar(sysDateStr);
		if (applyFrom.after(sysDate)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 审批后发送LGEP
	 * @param eventId
	 */
	@SuppressWarnings("unchecked")
	private void sendToLGEP(LinkedHashMap paramMap){
			LinkedHashMap lgepMap = new LinkedHashMap();
			lgepMap.put("APPLY_TYPE", APPLY_TYPE_NO);
			lgepMap.put("APPLY_NO", paramMap.get("APPLY_NO"));
			if("4".equals(paramMap.get("AFFIRM_READ_FLAG").toString())){//决裁中是4
				lgepMap.put("AFFIRM_LEVEL", paramMap.get("NEXT_AFFIRM_LEVEL"));
				lgepMap.put("AFFIRM_FLAG", 1);
			}else{//决裁完成
				lgepMap.put("FINISH", "FINISH");
				lgepMap.put("AFFIRM_FLAG", paramMap.get("AFFIRM_READ_FLAG"));
				lgepMap.put("AFFIRM_LEVEL", paramMap.get("currentAffirmLevel"));
				lgepMap.put("CREATED_BY", paramMap.get("APPLY_PERSON_ID"));
			}
			lgepMap.put("AFFIRM_EMPID", paramMap.get("CURRENT_AFFIRM_ID"));
			lgepMap.put("AAI_URL", "http://{serverIp}/LGEP/affirm/viewOtAffirm?LGEP=LGEP&LANGUAGE=zh&personId=123&APPLY_NO=" + paramMap.get("APPLY_NO").toString());
			lgepMap.put("AAF_URL", "http://{serverIp}/LGEP/affirm/viewOtAffirm?LGEP=LGEP&LANGUAGE=zh&personId=123&APPLY_NO=" + paramMap.get("APPLY_NO").toString());
			lgepMap.put("ABY_URL", "http://{serverIp}/LGEP/affirm/viewOtAffirm?LGEP=LGEP&LANGUAGE=zh&personId=" + paramMap.get("NEXT_AFFIRM_ID") + "&APPLY_NO=" + paramMap.get("APPLY_NO").toString());
			lgepMap.put("PRE_AFFIRM_EMPID", paramMap.get("NEXT_AFFIRM_ID"));
			lgepMap.put("CURRENT_AFFIRM_ID", paramMap.get("NEXT_AFFIRM_ID"));
			this.affirmInfoToLGEPSer.affirm(lgepMap);
	}
	
	@SuppressWarnings("unused")
	private boolean isZhuiSuYn(LinkedHashMap paramMap)throws Exception{
	 
		     String otflag = (String)this.queryForObject("ess.infoApply.getIfZhuiSu",paramMap);
		     if("Y".equals(otflag)){
		    	 return true;
		     }
			 
		return false;
	}
	
	/**
	 * 判断是否为追溯加班 overtime apply)
	 * 
	 * @param paramMap
	 * @return
	 */
	private boolean isZhuisuOt(LinkedHashMap paramMap) throws Exception {
		paramMap.put("FLAG", "S");//获取考勤月开始日期
		String arDateStr = this.infoApplyLeaveDao.getCurrentArDateOt(paramMap);
		 
		String emp_type_code = paramMap.get("empTypeCodeGroup") != null ? paramMap.get("empTypeCodeGroup").toString():"";
		Date date = new Date();
		SimpleDateFormat sb = new SimpleDateFormat("yyyy-MM-dd HH:mm");

		String leaveApplyFrom = paramMap.get("APPLY_OT_DATE") != null ? paramMap
					.get("APPLY_OT_DATE").toString()
					: (paramMap.get("APPLY_DATE") != null ? paramMap
							.get("APPLY_DATE").toString():sb.format(date));
		GregorianCalendar applyFrom = DateUtil
					.ParseGregorianCalendar(leaveApplyFrom);
		GregorianCalendar arDate = DateUtil
					.ParseGregorianCalendar(arDateStr);
		GregorianCalendar arDate2 = DateUtil.ParseGregorianCalendar(arDateStr);
		GregorianCalendar sysDate = DateUtil.ParseGregorianCalendar(sb.format(date));
		 
		arDate.add(2, -1);
		GregorianCalendar dateCurrent = DateUtil.ParseGregorianCalendar(arDateStr);
		dateCurrent.add(2, 1);
		 
			
			paramMap.put("R_DATE",leaveApplyFrom);
			paramMap.put("interCpnyID",paramMap.get("CPNY_ID"));
			String lastMonthFlag = infoApplyDao.arValidLastMonth(paramMap);
			if("OK".equals(lastMonthFlag)){
				 
				return true;
			}else{
				return false;
			}
		
		
		 
			
		 
	 
		 
	}
	/**
	 * 通过/否决年假调整申请
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int saveAnnualadjustmentAffirm(LinkedHashMap object) throws Exception {
		String flag = object.get("FLAG") != null ? object.get("FLAG").toString() : "0";
		this.update("ess.affirmApply.updateEssAffirmIsAnnuByAffirmNo", object);
		this.update("ess.affirmApply.updateEssAnnuByApplyNo", object);
		
		//check信息修改为已check 发送EP
		this.sendToLGEPCheckBatch(object,"216691","AnnuCheck");
		// 审批者决裁完后更新他添加的check信息为已check
		this.paTempSalesDAO.updateCheckFlagByEssAffirmNo(object);
		// 如果是决裁流程的最后一步通过而且也不需要人事确认
		LinkedHashMap annuMap = (LinkedHashMap)this.queryForObject("ess.affirmApply.getAnnuapplyListByApplyno",object);
		if ("1".equals(flag)) { 
			this.update("ess.affirmApply.updateEssAnnuIsACTIVITYByApplyNo", object);
			this.update("ess.affirmApply.PR_OP_VAC_DATA", object);
			/*if("LGEYT".equals(map.get("CPNY_ID")) || "LGEHN".equals(map.get("CPNY_ID"))){
				this.update("ess.affirmApply.updateVacChange", annuMap);
			}else{
				this.update("ess.affirmApply.updateVacLastYearByAnnuIsACTIVITYApplyNo", map);
			}*/
		}
		object.put("APPLY_PERSON_ID", annuMap.get("PERSON_ID"));
		this.sendAnnuToLGEP(object);
		return 1;
	}
	

	/**
	 * 审批后发送LGEP
	 * @param eventId/**
	 * check：
	 *  APPLY_NO:申请的seq
	 *  APPLY_TYPE：申请类型（数字代码）
	 *	AFFIRM_LEVEL：ess_check_no
	 *	AFFIRM_FLAG：传1，表示通过
	 *	AFFIRM_EMPID：check人person_id
	 *	AAI_URL:check结果查看页面url
	 */
	private void sendToLGEPCheckBatch(Map paramMap,String APPLY_TYPE_NO, String type){
		List checkList = this.paTempSalesDAO.getCheckListToLgep(paramMap);
		if(checkList != null && checkList.size() > 0){
			for(int i=0;i<checkList.size();i++){
				LinkedHashMap lgepMap = (LinkedHashMap)checkList.get(i);
				lgepMap.put("APPLY_TYPE", APPLY_TYPE_NO);
				lgepMap.put("APPLY_NO", paramMap.get("APPLY_NO"));
				lgepMap.put("AFFIRM_FLAG", '1');
				lgepMap.put("AFFIRM_LEVEL", lgepMap.get("ESS_CHECK_NO"));
				lgepMap.put("AFFIRM_EMPID", lgepMap.get("CHECKOR_ID"));
				lgepMap.put("AAI_URL", "http://{serverIp}/LGEP/affirm/" + type + "?LGEP=LGEP&LANGUAGE=zh&personId=123&APPLY_NO=" + paramMap.get("APPLY_NO") + "&affirmOrCheck=2");
				this.affirmInfoToLGEPSer.check(lgepMap);
			}
		}
	}
	
	/**
	 * 审批后发送LGEP
	 * @param eventId
	 */
	private void sendAnnuToLGEP(Map paramMap){
			LinkedHashMap lgepMap = new LinkedHashMap();
			lgepMap.put("APPLY_NO", paramMap.get("APPLY_NO"));
			lgepMap.put("APPLY_TYPE", "216691");
			if("4".equals(paramMap.get("AFFIRM_READ_FLAG").toString())){
				lgepMap.put("AFFIRM_LEVEL", paramMap.get("NEXT_AFFIRM_LEVEL"));
				lgepMap.put("AFFIRM_FLAG", 1);
			}else{//决裁完成
				lgepMap.put("FINISH", "FINISH");
				lgepMap.put("AFFIRM_FLAG", paramMap.get("AFFIRM_READ_FLAG"));
				lgepMap.put("AFFIRM_LEVEL", paramMap.get("AFFIRM_LEVEL"));
				lgepMap.put("CREATED_BY", paramMap.get("APPLY_PERSON_ID"));
			}
			lgepMap.put("AFFIRM_EMPID", paramMap.get("CURRENT_AFFIRM_ID"));
			lgepMap.put("AAI_URL", "http://{serverIp}/LGEP/affirm/viewAnnuAffirm?LGEP=LGEP&LANGUAGE=zh&APPLY_TYPE_NO=216691&personId=123&APPLY_NO=" + paramMap.get("APPLY_NO").toString());
			lgepMap.put("AAF_URL", "http://{serverIp}/LGEP/affirm/viewAnnuAffirm?LGEP=LGEP&LANGUAGE=zh&APPLY_TYPE_NO=216691&personId=123&APPLY_NO=" + paramMap.get("APPLY_NO").toString());
			lgepMap.put("ABY_URL", "http://{serverIp}/LGEP/affirm/viewAnnuAffirm?LGEP=LGEP&LANGUAGE=zh&APPLY_TYPE_NO=216691&personId=" + paramMap.get("NEXT_AFFIRM_ID") + "&APPLY_NO=" + paramMap.get("APPLY_NO").toString());
			lgepMap.put("PRE_AFFIRM_EMPID", paramMap.get("NEXT_AFFIRM_ID"));
			lgepMap.put("CURRENT_AFFIRM_ID", paramMap.get("NEXT_AFFIRM_ID"));
			this.affirmInfoToLGEPSer.affirm(lgepMap);
	}
	

	/**
	 * 批量通过/否决年假调整申请(batch pass and reject overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int saveAnnualadjustmentAffirmInBatch(List list) throws Exception {
		for (int i = 0; i < list.size(); i++) {
			Map map = (Map) list.get(i);
			String flag = map.get("FLAG") != null ? map.get("FLAG").toString(): "0";
			this.update("ess.affirmApply.updateEssAffirmIsAnnuByAffirmNo", map);
			this.update("ess.affirmApply.updateEssAnnuByApplyNo", map);
			//check信息修改为已check 发送EP
			this.sendToLGEPCheckBatch(map,"216691","AnnuCheck");
			// 审批者决裁完后更新他添加的check信息为已check
			this.paTempSalesDAO.updateCheckFlagByEssAffirmNo(map);
			LinkedHashMap annuMap = (LinkedHashMap)this.queryForObject("ess.affirmApply.getAnnuapplyListByApplyno",map);
			// 如果是决裁流程的最后一步且为通过的时候执行下面的操作
			if ("1".equals(String.valueOf(flag))) {
				this.update("ess.affirmApply.updateEssAnnuIsACTIVITYByApplyNo", map);
				this.update("ess.affirmApply.PR_OP_VAC_DATA", map);
				/*if("LGEYT".equals(map.get("CPNY_ID")) || "LGEHN".equals(map.get("CPNY_ID"))){
					this.update("ess.affirmApply.updateVacChange", annuMap);
				}else{
					this.update("ess.affirmApply.updateVacLastYearByAnnuIsACTIVITYApplyNo", map);
				}*/
			}
			map.put("APPLY_PERSON_ID", annuMap.get("PERSON_ID"));
			this.sendAnnuToLGEP(map);
		}
		return 1;
	}
	
	
	/**
	 * 根据apply_no,person_id查询此人是否是此次申请的决裁者
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getAffirmorCntByApplyNo(LinkedHashMap obj) throws Exception {
		return NumberUtils.parseNumber(ObjectUtils.toString(this
				.queryForObject("ess.affirmApply.getAffirmorCntByApplyNo",
						obj)), Integer.class);
	}
	
	/**
	 * 批量添加加班申请(add overtime apply)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void addNewApplyAffirmor(LinkedHashMap map) throws Exception {
		//插入新决裁者之前先将等级比较高的决裁者等级加1
		this.update("ess.affirmApply.updateAffirmLevelByApplyNo",map);
		//插入新的决裁者
		this.insert("ess.affirmApply.insertApplyAffirmor",map);
	}
	
	/**
	 * 加班Check列表(overtime apply affirm list)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getOtCheckList(Object obj) throws Exception {
		return this.getOtCheckList(obj, -1, -1);
	}
	/**
	 * 加班Check列表(overtime apply affirm list)
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getOtCheckList(Object obj, int currentPage, int pageSize)
			throws Exception {
		List returnList = new ArrayList();
		if (currentPage > -1 && pageSize > -1) {
			returnList = this.queryForList("ess.affirmApply.getOtCheckList", obj, currentPage,pageSize);
		} else {
			returnList = this.queryForList("ess.affirmApply.getOtCheckList", obj);
		}
		return returnList;
	}
	/**
	 * 加班Check列表总数(overtime apply affirm total count)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getOtCheckListCnt(Object obj) throws Exception {
		return NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ess.affirmApply.getOtCheckListCnt", obj)),Integer.class);
	}
	
	/**
	 * 获得决裁信息(get ess_affirm information by affirmNo)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object getEssAffirmInfoByAffirmNo(LinkedHashMap obj)
			throws Exception {
		return this.queryForObject(
				"ess.affirmApply.getEssAffirmInfoByAffirmNo", obj);
	}
	@SuppressWarnings("unchecked")
	@Override
	public Object getEssAffirmInfoForApplyNo(LinkedHashMap obj)
	throws Exception {
		return this.queryForObject(
				"ess.affirmApply.getEssAffirmInfoForApplyNo", obj);
	}
	@SuppressWarnings("unchecked")
	@Override
	public Object getEssAffirmInfoForEssAffrimNO(LinkedHashMap obj)
	throws Exception {
		return this.queryForObject(
				"ess.affirmApply.getEssAffirmInfoForEssAffrimNO", obj);
	}
	
	/**
	 * 获得Check信息(get ess_Check information by checkNo)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object getEssCheckInfoByCheckNo(LinkedHashMap obj)
			throws Exception {
		return this.queryForObject(
				"ess.affirmApply.getEssCheckInfoByCheckNo", obj);
	}
	
	/**
	 * 获得Check信息(get ess_Check information by CheckorId)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object getEssCheckInfoByCheckorId(LinkedHashMap obj)
			throws Exception {
		return this.queryForObject(
				"ess.affirmApply.getEssCheckInfoByCheckorId", obj);
	}
	
	/**
	 * 获得Check信息(get ess_Check information by CheckorId)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getEssCheckCntByCheckorId(LinkedHashMap obj)
			throws Exception {
		return NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject(
				"ess.affirmApply.getEssCheckCntByCheckorId", obj)), Integer.class);
	}
	
	/**
	 * 获得当前信息决裁流程中最大的决裁级别(get Max Affirm level By ApplyNo )
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getMaxAffirmLevelByApplyNo(LinkedHashMap obj) throws Exception {
		return NumberUtils.parseNumber(ObjectUtils.toString(this
				.queryForObject("ess.affirmApply.getMaxAffirmLevelByApplyNo",
						obj)), Integer.class);
	}
	
	/**
	 * 获得当前信息Check流程中最大的Check级别(get Max Check level By ApplyNo )
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getMaxCheckLevelByApplyNo(LinkedHashMap obj) throws Exception {
		return NumberUtils.parseNumber(ObjectUtils.toString(this
				.queryForObject("ess.affirmApply.getMaxCheckLevelByApplyNo",
						obj)), Integer.class);
	}
	
	/**
	 * 获得决裁信息(get ess_check information by applyNo and level)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object getEssAffirmInfoByApplyNoAndLevel(LinkedHashMap obj)
			throws Exception {
		return this.queryForObject(
				"ess.affirmApply.getEssAffirmInfoByApplyNoAndLevel", obj);
	}
	
	/**
	 * 获得下一级Check信息(get ess_check information by applyNo and level)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object getEssCheckInfoByApplyNoAndLevel(LinkedHashMap obj)
			throws Exception {
		return this.queryForObject(
				"ess.affirmApply.getEssCheckInfoByApplyNoAndLevel", obj);
	}
	
	/**
	 * check加班申请(check overtime apply)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int checkApplyInfo(LinkedHashMap object) throws Exception {
		String flag = object.get("NEXT_CHECK_FLAG") != null ? object.get("NEXT_CHECK_FLAG").toString() : "2";
		if("1".equals(flag)){
			this.update("ess.affirmApply.updateEssCheckByCheckNo", object);
			this.update("ess.affirmApply.updateEssNextCheckorByAffirmNo", object);
		}else{
			this.update("ess.affirmApply.updateEssCheckByCheckNo", object);
		}
		
		return 1;
	}
	
	/**
	 * check加班申请，最后一步check之后将ess_affirm中的current_check_id置空(check pa for left apply)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int checkPaForLeftApplyInfo(LinkedHashMap object) throws Exception {
		String flag = object.get("NEXT_CHECK_FLAG") != null ? object.get("NEXT_CHECK_FLAG").toString() : "2";
		if("1".equals(flag)){
			this.update("ess.affirmApply.updateEssCheckByCheckNo", object);
			this.update("ess.affirmApply.updateEssNextCheckorByAffirmNo", object);
		}else{//check之后还需将ess_affirm中的current_affirm_id置空
			this.update("ess.affirmApply.updateEssCheckByCheckNo", object);
			this.update("ess.affirmApply.updateEssNextCheckorNullByAffirmNo", object);
		}
		//check完毕之后要将pa_leftmen_apply中的affirm_flag修改为3(3--check中)，只有当affirm_flag=0时，即第一次check时执行，其它情况下不变
		//本来应该修改为3，4--审批中，但是目前没有3这个状态，就修改为4--审批中
		this.update("ess.affirmApply.updateApplyAffirmFlagByAffirmNo", object);
		
		return 1;
	}
	
	/**
	 * check加班申请(check overtime apply)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int updateCurrentCheckor(LinkedHashMap object) throws Exception {
		this.update("ess.affirmApply.updateEssNextCheckorByAffirmNo", object);
		return 1;
	}
	
	/**
	 * 添加checkor(add checkor)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int addApplyCheckList(LinkedHashMap object) throws Exception {
		Object Obj = this.insert("ess.affirmApply.insertCheckorByAffirmNo", object);
		return 1;
	}
	
	/**
	 * 添加checkor(add checkor)--checke_level自增
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int addApplyCheckListNew(LinkedHashMap object) throws Exception {
		Object Obj = this.insert("ess.affirmApply.insertCheckorByAffirmNoNew", object);
		return 1;
	}
	
	/**
	 * 查询checkno 发送小页面用(add checkor)
	 * by:wangqiang
	 * @param object
	 * @return 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int selectCheckNo() throws Exception {
		 
		return NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ess.affirmApply.selectCheckNo")),Integer.class);
		 
			 
	}
	/**
	 * 加班申请--编辑列表(overtime apply affirm list)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getEditOtApplyList(Object obj) throws Exception {
		return this.getEditOtApplyList(obj, -1, -1);
	}
	
	
	/**
	 * 加班申请--编辑列表(overtime apply affirm list)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getEditOtApplyBatchList(Object obj) throws Exception {
		return this.getEditOtApplyBatchList(obj, -1, -1);
	}
	/**
	 * 加班申请--编辑列表(overtime apply affirm list)
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getEditOtApplyList(Object obj, int currentPage, int pageSize)
			throws Exception {
		List returnList = new ArrayList();
		if (currentPage > -1 && pageSize > -1) {
			returnList = this.queryForList("ess.affirmApply.getEditOtApplyList", obj, currentPage,pageSize);
		} else {
			returnList = this.queryForList("ess.affirmApply.getEditOtApplyList", obj);
		}
		return returnList;
	}
	
	
	/**
	 * 加班申请--批量修改编辑列表(overtime apply affirm list)
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getEditOtApplyBatchList(Object obj, int currentPage, int pageSize)
			throws Exception {
		List returnList = new ArrayList();
		if (currentPage > -1 && pageSize > -1) {
			returnList = this.queryForList("ess.affirmApply.getEditOtApplyBatchList", obj, currentPage,pageSize);
		} else {
			returnList = this.queryForList("ess.affirmApply.getEditOtApplyBatchList", obj);
		}
		return returnList;
	}
	/**
	 * 加班申请--编辑列表总数(overtime apply affirm list count)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getEditOtApplyListCnt(Object obj) throws Exception {
		return NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ess.affirmApply.getEditOtApplyListCnt", obj)),Integer.class);
	}
	/**
	 * 加班申请--编辑列表总数(overtime apply affirm list count)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getEditOtApplyListBatchCnt(Object obj) throws Exception {
		return NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ess.affirmApply.getEditOtApplyListBatchCnt", obj)),Integer.class);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	/**
	 * 加班决裁列表(overtime apply affirm list)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getOvertimeAffirmList(Object obj) throws Exception {
		return this.getOvertimeAffirmList(obj, -1, -1);
	}

	/**
	 * 加班决裁列表(overtime apply affirm list)
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getOvertimeAffirmList(Object obj, int currentPage, int pageSize)
			throws Exception {
		List returnList = new ArrayList();
		if (currentPage > -1 && pageSize > -1) {
			returnList = this.queryForList(
					"ess.affirmApply.getOvertimeAffirmList", obj, currentPage,
					pageSize);
		} else {
			returnList = this.queryForList(
					"ess.affirmApply.getOvertimeAffirmList", obj);
		}
		return returnList;
	}

	/**
	 * 加班决裁列表总数(overtime apply affirm total count)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getOvertimeAffirmListCnt(Object obj) throws Exception {
		return NumberUtils.parseNumber(ObjectUtils
				.toString(this.queryForObject(
						"ess.affirmApply.getOvertimeAffirmListCnt", obj)),
				Integer.class);
	}

	/**
	 * 休假/出差/外出申请决裁列表(leave/evection/egression apply affirm list)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getLeaveApplyAffirmList(Object obj) throws Exception {
		return this.getLeaveApplyAffirmList(obj, -1, -1);
	}

	/**
	 * 休假/出差/外出申请决裁列表(leave/evection/egression apply affirm list)
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getLeaveApplyAffirmList(Object obj, int currentPage,
			int pageSize) throws Exception {
		List returnList = new ArrayList();
		if (currentPage > -1 && pageSize > -1) {
			returnList = this.queryForList(
					"ess.affirmApply.getLeaveApplyAffirmList", obj,
					currentPage, pageSize);
		} else {
			returnList = this.queryForList(
					"ess.affirmApply.getLeaveApplyAffirmList", obj);
		}
		return returnList;
	}

	/**
	 * 休假/出差/外出申请决裁列表总数(leave/evection/egression apply affirm total count)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getLeaveApplyAffirmListCnt(Object obj) throws Exception {
		return NumberUtils.parseNumber(ObjectUtils.toString(this
				.queryForObject("ess.affirmApply.getLeaveApplyAffirmListCnt",
						obj)), Integer.class);
	}

	/**
	 * 批量通过/否决休假/出差/外出申请(batch pass and reject leave/evection/egression apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int saveLeaveApplyAffirmInBatch(List list) throws Exception {
		for (int i = 0; i < list.size(); i++) {
			Map map = (Map) list.get(i);
			String flag = map.get("FLAG") != null ? map.get("FLAG").toString(): "0";
			this.update("ess.affirmApply.updateEssAffirmByAffirmNo", map);
			this.update("ess.affirmApply.updateEssLeaveApplyTbByApplyNo", map);
			// 如果是决裁流程的最后一步通过而且也不需要人事确认
			if ("1".equals(String.valueOf(flag))) {
				Object obj = this.queryForObject("ess.infoApply.getParamInfoValue", map);
				String ifConfirmFlag = obj != null ? obj.toString() : "";
				if ("0".equals(ifConfirmFlag)) {
					Object leaveApply = this.queryForObject("ess.humanAffirm.getLeaveApplyInfoByApplyNo", map);
					// 删除之前的AR_APPLY_RESULT的数据
					this.delete("ess.humanAffirm.deleteArApplyResultByApplyNoAndPersonId",leaveApply);
					if(map.get("navTabId")!=null&&map.get("navTabId").equals("ess0228")){
						// 插入新的的AR_APPLY_RESULT的数据
						@SuppressWarnings("unused")
						String FROM_TIME=map.get("OLD_DAY").toString();//开始时间
						String FROM_TIMESTART=map.get("OLD_DAY").toString().substring(0,10)+" 09:00:00";//加班开始时间
						String FROM_TIMEEND=map.get("OLD_DAY").toString().substring(0,10)+" 18:00:00";//加班结束时间
						String TO_TIME=map.get("TO_TIME").toString();//结束时间
						String TO_TIME_START=TO_TIME.substring(0,10)+" 09:00:00";
						String TO_TIME_END=TO_TIME.substring(0,10)+" 18:00:00";
						//加班
						((LinkedHashMap) leaveApply).put("FROM_TIME", FROM_TIMESTART);
						((LinkedHashMap) leaveApply).put("TO_TIME", FROM_TIMEEND);
						((LinkedHashMap) leaveApply).put("APPLY_TYPE_CODE", "124858");
						this.insert("ess.humanAffirm.insertArApplyResultForArApply",leaveApply);
						//调休
						((LinkedHashMap) leaveApply).put("FROM_TIME", TO_TIME_START);
						((LinkedHashMap) leaveApply).put("TO_TIME", TO_TIME_END);
						((LinkedHashMap) leaveApply).put("APPLY_TYPE_CODE", "123646");
						if(!((LinkedHashMap) leaveApply).get("APPLY_DATE").equals("31")){
							((LinkedHashMap) leaveApply).put("APPLY_DATE", "");
						}
						this.insert("ess.humanAffirm.insertArApplyResultForArApply1",leaveApply);
					}else{
						if(!((LinkedHashMap) leaveApply).get("APPLY_DATE").equals("31")){
							((LinkedHashMap) leaveApply).put("APPLY_DATE", "");
						}
						this.insert("ess.humanAffirm.insertArApplyResultForArApply",leaveApply);
					}
					// 计算考勤
					//((LinkedHashMap) leaveApply).put("caltype", "emp");
					//this.insert("ess.humanAffirm.caculateDetailP", leaveApply);
				}
			}
		}
		return 1;
	}

	/**
	 * 通过/否决休假/出差/外出申请(pass and reject leave/evection/egression apply)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int saveLeaveApplyAffirm(LinkedHashMap object) throws Exception {
		String flag = object.get("FLAG") != null ? object.get("FLAG")
				.toString() : "0";
		this.update("ess.affirmApply.updateEssAffirmByAffirmNo", object);
		this.update("ess.affirmApply.updateEssLeaveApplyTbByApplyNo", object);

		// 如果是决裁流程的最后一步通过而且也不需要人事确认
		if ("1".equals(flag)) {

			Object obj = this.queryForObject("ess.infoApply.getParamInfoValue",
					object);
			String ifConfirmFlag = obj != null ? obj.toString() : "";
			if ("0".equals(ifConfirmFlag)) {

				LinkedHashMap leaveApply = (LinkedHashMap) this.queryForObject(
						"ess.humanAffirm.getLeaveApplyInfoByApplyNo", object);
				leaveApply.put("ADMIN_ID", object.get("ADMIN_ID"));
				leaveApply.put("CPNY_ID", object.get("CPNY_ID"));
				// 删除之前的AR_APPLY_RESULT的数据
				this
						.delete(
								"ess.humanAffirm.deleteArApplyResultByApplyNoAndPersonId",
								leaveApply);

				
				if(leaveApply.get("navTabId")!=null&&leaveApply.get("navTabId").equals("ess0228")){
					// 插入新的的AR_APPLY_RESULT的数据
					@SuppressWarnings("unused")
					String FROM_TIME=leaveApply.get("OLD_DAY").toString();//开始时间
					String FROM_TIMESTART=leaveApply.get("OLD_DAY").toString().substring(0,10)+" 09:00:00";//加班开始时间
					String FROM_TIMEEND=leaveApply.get("OLD_DAY").toString().substring(0,10)+" 18:00:00";//加班结束时间
					String TO_TIME=leaveApply.get("TO_TIME").toString();//结束时间
					String TO_TIME_START=TO_TIME.substring(0,10)+" 09:00:00";
					String TO_TIME_END=TO_TIME.substring(0,10)+" 18:00:00";
					//加班
					leaveApply.put("FROM_TIME", FROM_TIMESTART);
					leaveApply.put("TO_TIME", FROM_TIMEEND);
					leaveApply.put("APPLY_TYPE_CODE", "124858");
					this.insert("ess.humanAffirm.insertArApplyResultForArApply",leaveApply);
					//调休
					leaveApply.put("FROM_TIME", TO_TIME_START);
					leaveApply.put("TO_TIME", TO_TIME_END);
					leaveApply.put("APPLY_TYPE_CODE", "123646");
					if(!leaveApply.get("APPLY_DATE").equals("31")){
						leaveApply.put("APPLY_DATE", "");
					}
					this.insert("ess.humanAffirm.insertArApplyResultForArApply1",leaveApply);
					
					
				}else{
					if(!leaveApply.get("APPLY_DATE").equals("31")){
						leaveApply.put("APPLY_DATE", "");
					}
					this.insert("ess.humanAffirm.insertArApplyResultForArApply",leaveApply);
				}
				// 计算考勤
				//((LinkedHashMap) leaveApply).put("caltype", "emp");
				//this.insert("ess.humanAffirm.caculateDetailP", leaveApply);
			}

		}
		
		// 当裁决选择否定的时候 删除  AR_APPLY_RESULT的数据
		/*String affirmFlag = object.get("AFFIRM_FLAG") != null ? object.get("AFFIRM_FLAG").toString() : "0";
		if("2".equals(affirmFlag)){
			LinkedHashMap leaveApplyA =new LinkedHashMap();
			leaveApplyA.put("APPLY_NO", object.get("APPLY_NO"));
			this.delete("ess.humanAffirm.deleteArApplyResultByApplyNoAndPersonId",leaveApplyA);
		}*/
		return 1;
	}

	/**
	 * 根据法人和参数号查找对应的值(get parameter Value By CpnyId And ParamNo)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object getParamValueByCpnyIdAndParamNo(LinkedHashMap obj)
			throws Exception {
		return this.queryForObject("ess.affirmApply.getParamInfoValue", obj);
	}

	/**
	 * 通过信息申请NO获得该信息决裁流程的决裁者(get affirmor list by applyNo)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getEssAffirmInfoByApplyNo(LinkedHashMap obj) throws Exception {
		return this.queryForList(
				"ess.affirmApply.getEssAffirmInfoByApplyNoAndLevel", obj);
	}

	/**
	 * 临时职入职审批
	 */
	@Override
	public List getHireAffirmList(Object obj) throws Exception {
		return this.getHireAffirmList(obj, -1, -1);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List getHireAffirmList(Object obj, int currentPage, int pageSize) throws Exception {
		List returnList = new ArrayList();

		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("ess.affirmApply.getHireAffirmList", obj, currentPage,pageSize);
			} else {
				returnList = this.queryForList("ess.affirmApply.getHireAffirmList", obj);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	/**
	 * 临时职入职审批
	 */
	@Override
	public int getHireAffirmListCnt(Object obj) throws Exception {
		return NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ess.affirmApply.getHireAffirmListCnt", obj)),Integer.class);
	}

	@Override
	public List getHireAffirmByReqID(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ess.affirmApply.getHireAffirmByReqID",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	@Override
	public List getCheckorByByReqID(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ess.affirmApply.getCheckorByByReqID",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@Override
	public List getHireAffirmListByReqID(Object obj) throws Exception {
		return this.getHireAffirmListByReqID(obj, -1, -1);
	}
	public List getHireAffirmListByReqID(Object obj, int currentPage, int pageSize) throws Exception {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("ess.affirmApply.getHireAffirmListByReqID", obj, currentPage,pageSize);
			} else {
				returnList = this.queryForList("ess.affirmApply.getHireAffirmListByReqID", obj);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	@Override
	public int getHireAffirmListByReqIDCnt(Object obj) throws Exception {
		return NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ess.affirmApply.getHireAffirmListByReqIDCnt", obj)),Integer.class);
	}
	
	@Override
	public int saveApplyHireAffirm(LinkedHashMap object) throws Exception {
		String flag = object.get("AFFIRM_READ_FLAG") != null ? object.get("AFFIRM_READ_FLAG")
				.toString() : "0";
		this.update("ess.affirmLeaveApply.updateEssAffirmByAffirmNo", object);
		this.update("ess.affirmApply.updateEssApplyHireByApplyNo", object);
		//check信息修改为已check 发送EP
		//this.sendToLGEPCheckBatch(object,"31","startApplyCheckInfo");
		// 审批者决裁完后更新他添加的check信息为已check
		this.paTempSalesDAO.updateCheckFlagByEssAffirmNo(object);
		// 如果是决裁流程的最后一步通过而且也不需要人事确认
		if ("1".equals(flag)) {
			Map map = new LinkedHashMap();
			map.put("REQ_ID", object.get("REQ_ID").toString());
			this.saveHireAffirm(map);
		}
		//审批发送LGEP
		sendHireToLGEP(object);
		return 1;
	}

	public void saveHireAffirm(Object obj) throws Exception {
		// 保存到HR_EMPLOYEE
		this.insert("hrm.transferOrder.saveEmployeeByReqId", obj);
		// 保存到HR_PERSONAL_INFO
		this.insert("hrm.transferOrder.savePersonalByReqId", obj);
		// 保存到HR_EMP_PA_INFO
		this.insert("hrm.transferOrder.saveEmpPaByReqId", obj);
		// 保存到HR_EXPERIENCE_INSIDE
		this.insert("hrm.transferOrder.saveExperienceByReqId", obj);

	}

	@SuppressWarnings("unchecked")
	private void sendHireToLGEP(LinkedHashMap paramMap) throws Exception{
			LinkedHashMap lgepMap = new LinkedHashMap();
			lgepMap.put("APPLY_TYPE", "23292329");
			lgepMap.put("APPLY_NO", paramMap.get("APPLY_NO"));
			if("4".equals(paramMap.get("AFFIRM_READ_FLAG").toString())){//决裁中是4
				lgepMap.put("AFFIRM_LEVEL", paramMap.get("NEXT_AFFIRM_LEVEL"));
				lgepMap.put("AFFIRM_FLAG", 1);
			}else{//决裁完成
				lgepMap.put("FINISH", "FINISH");
				lgepMap.put("AFFIRM_FLAG", paramMap.get("AFFIRM_READ_FLAG"));
				lgepMap.put("AFFIRM_LEVEL", paramMap.get("currentAffirmLevel"));
			}
			lgepMap.put("CREATED_BY", paramMap.get("APPLY_PERSON_ID")==null? (paramMap.get("PERSON_ID")==null?paramMap.get("CREATED_BY"):paramMap.get("PERSON_ID").toString()):paramMap.get("APPLY_PERSON_ID").toString());
			lgepMap.put("AFFIRM_EMPID", paramMap.get("CURRENT_AFFIRM_ID"));
			lgepMap.put("AAI_URL", "http://{serverIp}/LGEP/affirm/viewReqHireAffirm?pageNum=1&LGEP=LGEP&LANGUAGE=zh&personId="+paramMap.get("CURRENT_AFFIRM_ID")+"&REQ_ID=" + paramMap.get("REQ_ID").toString());
			lgepMap.put("AAF_URL", "http://{serverIp}/LGEP/affirm/viewReqHireAffirm?pageNum=1&LGEP=LGEP&LANGUAGE=zh&personId="+paramMap.get("CURRENT_AFFIRM_ID")+"&REQ_ID=" + paramMap.get("REQ_ID").toString());
			lgepMap.put("ABY_URL", "http://{serverIp}/LGEP/affirm/viewReqHireAffirm?pageNum=1&LGEP=LGEP&LANGUAGE=zh&personId=" + paramMap.get("NEXT_AFFIRM_ID") + "&REQ_ID=" + paramMap.get("REQ_ID").toString());
			lgepMap.put("PRE_AFFIRM_EMPID", paramMap.get("NEXT_AFFIRM_ID"));
			lgepMap.put("CURRENT_AFFIRM_ID", paramMap.get("NEXT_AFFIRM_ID"));
			List reqList = this.getHireAffirmListByReqID(paramMap);
			this.affirmInfoToLGEPSer.affirmWithReqDetail(lgepMap, reqList);
	}
	
	/**
	 * 临时职离职职审批
	 */
	@Override
	public List getResignAffirmList(Object obj) throws Exception {
		return this.getResignAffirmList(obj, -1, -1);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List getResignAffirmList(Object obj, int currentPage, int pageSize) throws Exception {
		List returnList = new ArrayList();

		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("ess.affirmApply.getResignAffirmList", obj, currentPage,pageSize);
			} else {
				returnList = this.queryForList("ess.affirmApply.getResignAffirmList", obj);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	@Override
	public int getResignAffirmListCnt(Object obj) throws Exception {
		return NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ess.affirmApply.getResignAffirmListCnt", obj)),Integer.class);
	}
	@Override
	public List getResignAffirmByReqID(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ess.affirmApply.getResignAffirmByReqID",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	@Override
	public List getResignCheckorByByReqID(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ess.affirmApply.getResignCheckorByByReqID",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@Override
	public List getResignAffirmListByReqID(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ess.affirmApply.getResignAffirmListByReqID",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	@Override
	public int getResignAffirmListByReqIDCnt(Object obj) throws Exception {
		return NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ess.affirmApply.getResignAffirmListByReqIDCnt", obj)),Integer.class);
	}
	@Override
	public int saveApplyResignAffirm(LinkedHashMap object) throws Exception {
		String flag = object.get("FLAG") != null ? object.get("FLAG").toString() : "0";
		String currAffirmFlag = object.get("AFFIRM_FLAG") != null ? object.get("AFFIRM_FLAG").toString() : "0";
		this.update("ess.affirmLeaveApply.updateEssAffirmByAffirmNo", object);
		//check信息修改为已check 发送EP
		//this.sendToLGEPCheckBatch(object,"31","startApplyCheckInfo");
		// 审批者决裁完后更新他添加的check信息为已check
		//this.paTempSalesDAO.updateCheckFlagByEssAffirmNo(object);
		// 如果是决裁流程的最后一步通过而且也不需要人事确认
		LinkedHashMap map = object;
		if ("1".equals(flag)) {
			map.put("STATE", 50);
		}else{
			if("1".equals(currAffirmFlag)){
				map.put("STATE", 40);//当前审批通过
			}else{
				map.put("STATE", 60);//当前审批拒绝
			}			
		}
		map.put("REQ_ID", object.get("REQ_ID").toString());
		this.update("ess.affirmApply.updateEssApplyResignByApplyNo", map);
		
		return (Integer)map.get("STATE");
	}
	/*
	 * 撤销离职发令
	 * */
	@Override
	public Map callRevokeResignation(Map paramMap) throws Exception {
		this.insert("hrm.transferOrder.callRevokeResignation", paramMap);	
		return paramMap;
	}
	@Override
	public void rollbackApplyResignAffirm(LinkedHashMap object) throws Exception {
		this.update("ess.affirmApply.rollbackEssApplyResignByApplyNo", object);
	}

	/**
	 * 实贩卖实绩审批查询列表
	 */
	@Override
	public List getSellOutAffirmList(Object obj) throws Exception {
		return this.getSellOutAffirmList(obj, -1, -1);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List getSellOutAffirmList(Object obj, int currentPage, int pageSize) throws Exception {
		List returnList = new ArrayList();

		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("ess.affirmApply.getSellOutAffirmList", obj, currentPage,pageSize);
			} else {
				returnList = this.queryForList("ess.affirmApply.getSellOutAffirmList", obj);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	@Override
	public int getSellOutAffirmListCnt(Object obj) throws Exception {
		return NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ess.affirmApply.getSellOutAffirmListCnt", obj)),Integer.class);
	}

}