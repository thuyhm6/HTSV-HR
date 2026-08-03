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
import com.ait.ar.dao.ArDetailCalulateDao;
import com.ait.ess.dao.AffirmLeaveApplyDao;
import com.ait.ess.dao.InfoApplyLeaveDao;
import com.ait.pa.dao.tempsale.PaTempSalesDAO;
import com.ait.web.util.DateUtil;
import com.ait.web.util.SqlMapClientSupport;
import com.ait.web.util.StringUtil;

@Repository
@SuppressWarnings("unchecked")
public class AffirmLeaveApplyDaoImpl extends SqlMapClientSupport implements
		AffirmLeaveApplyDao {

	@Autowired
	private ArDetailCalulateDao arDetailCalulateDao;

	@Autowired
	private InfoApplyLeaveDao infoApplyLeaveDao;

	@Autowired
	PaTempSalesDAO paTempSalesDAO;

	@Autowired
	private AffirmInfoToLGEPSer affirmInfoToLGEPSer;
	
	private static String APPLY_TYPE_NO = "21";

	/**
	 * 加班决裁列表(overtime apply affirm list)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public List getOtAffirmList(Object obj) throws Exception {
		return this.getOtAffirmList(obj, -1, -1);
	}

	/**
	 * 加班决裁列表(overtime apply affirm list)
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@Override
	public List getOtAffirmList(Object obj, int currentPage, int pageSize)
			throws Exception {
		List returnList = new ArrayList();
		if (currentPage > -1 && pageSize > -1) {
			returnList = this.queryForList(
					"ess.affirmLeaveApply.getOtAffirmList", obj, currentPage,
					pageSize);
		} else {
			returnList = this.queryForList(
					"ess.affirmLeaveApply.getOtAffirmList", obj);
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
		return NumberUtils.parseNumber(ObjectUtils
				.toString(this.queryForObject(
						"ess.affirmLeaveApply.getOtAffirmListCnt", obj)),
				Integer.class);
	}

	/**
	 * 批量通过/否决加班申请(batch pass and reject overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Override
	public int saveOvertimeApplyAffirmInBatch(List list) throws Exception {
		for (int i = 0; i < list.size(); i++) {
			Map map = (Map) list.get(i);
			String flag = map.get("FLAG") != null ? map.get("FLAG").toString()
					: "0";
			this.update("ess.affirmLeaveApply.updateEssAffirmByAffirmNo", map);
			this.update("ess.affirmLeaveApply.updateEssApplyOtByApplyNo", map);
			// this.update("ess.affirmLeaveApply.",map);
			// 如果是决裁流程的最后一步且为通过的时候执行下面的操作
			if ("1".equals(String.valueOf(flag))) {
				// --2014-06-22--lufeng--不需要再判断是否需要人事确认
				// Object obj =
				// this.queryForObject("ess.infoApply.getParamInfoValue", map);
				// String ifConfirmFlag = obj != null ? obj.toString() : "";
				// if ("0".equals(ifConfirmFlag)) {
				LinkedHashMap overtimeApply = (LinkedHashMap) this
						.queryForObject(
								"ess.humanAffirm.getOvertimeApplyInfoByApplyNo",
								map);
				overtimeApply.put("AFFIRM_TYPE", "OVERTIME_AFFIRM");
				// 删除之前的AR_APPLY_RESULT的数据
				this
						.delete(
								"ess.humanAffirm.deleteArApplyResultByApplyNoAndPersonId",
								overtimeApply);
				// 插入新的的AR_APPLY_RESULT的数据
				this.insert("ess.humanAffirm.insertArApplyResultForArApply",
						overtimeApply);
				// 计算考勤
				((LinkedHashMap) overtimeApply).put("caltype", "emp");
				this.insert("ess.humanAffirm.caculateDetailP", overtimeApply);
				// }
			}
		}
		return 1;
	}

	/**
	 * 批量通过/否决年假调整申请(batch pass and reject overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Override
	public int saveAnnualadjustmentAffirmInBatch(List list) throws Exception {
		for (int i = 0; i < list.size(); i++) {
			Map map = (Map) list.get(i);
			String flag = map.get("FLAG") != null ? map.get("FLAG").toString()
					: "0";
			this.update("ess.affirmLeaveApply.updateEssAffirmByAffirmNo", map);
			// this.update("ess.affirmLeaveApply.updateEssApplyOtByApplyNo",
			// map);
			// this.update("ess.affirmLeaveApply.",map);
			// 如果是决裁流程的最后一步且为通过的时候执行下面的操作
			if ("1".equals(String.valueOf(flag))) {

			}
		}
		return 1;
	}

	/**
	 * 通过/否决加班申请(pass and reject overtime apply)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@Override
	public int saveOvertimeApplyAffirm(LinkedHashMap object) throws Exception {
		String flag = object.get("FLAG") != null ? object.get("FLAG")
				.toString() : "0";
		this.update("ess.affirmLeaveApply.updateEssAffirmByAffirmNo", object);
		this
				.update("ess.affirmLeaveApply.updateEssApplyLeaveByApplyNo",
						object);
		// 如果是决裁流程的最后一步通过而且也不需要人事确认
		if ("1".equals(flag)) {
			Object obj = this.queryForObject(
					"ess.infoApplyLeave.getParamInfoValue", object);
			String ifConfirmFlag = obj != null ? obj.toString() : "";
			if ("0".equals(ifConfirmFlag)) {
				LinkedHashMap leaveApply = (LinkedHashMap) this
						.queryForObject(
								"ess.humanAffirm.getovertimeApplyInfoByApplyNo",
								object);
				leaveApply.put("ADMIN_ID", object.get("ADMIN_ID"));
				leaveApply.put("CPNY_ID", object.get("CPNY_ID"));
				leaveApply.put("AFFIRM_TYPE", "OVERTIME_AFFIRM");
				// 删除之前的AR_APPLY_RESULT的数据
				this
						.delete(
								"ess.humanAffirm.deleteArApplyResultByApplyNoAndPersonId",
								leaveApply);
				// 插入新的的AR_APPLY_RESULT的数据
				this.insert("ess.humanAffirm.insertarApplyResultForArApply",
						leaveApply);
				// 计算考勤
				((LinkedHashMap) leaveApply).put("caltype", "emp");
				this.insert("ess.humanAffirm.caculateDetailP", leaveApply);
			}
		}
		return 1;
	}

	/**
	 * 通过/否决加班申请(pass and reject overtime apply)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@Override
	public int saveAnnualadjustmentAffirm(LinkedHashMap object)
			throws Exception {
		String flag = object.get("FLAG") != null ? object.get("FLAG")
				.toString() : "0";
		this.update("ess.affirmLeaveApply.updateEssAffirmByAffirmNo", object);
		this.update("ess.affirmLeaveApply.updateEssAnnuByApplyNo", object);
		// 如果是决裁流程的最后一步通过而且也不需要人事确认
		if ("1".equals(flag)) {
			this.update(
					"ess.affirmLeaveApply.updateEssAnnuIsACTIVITYByApplyNo",
					object);
			this
					.update(
							"ess.affirmLeaveApply.updateVacLastYearByAnnuIsACTIVITYApplyNo",
							object);
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
	@Override
	public int getAffirmorCntByApplyNo(LinkedHashMap obj) throws Exception {
		return NumberUtils.parseNumber(ObjectUtils.toString(this
				.queryForObject("ess.affirmLeaveApply.getAffirmorCntByApplyNo",
						obj)), Integer.class);
	}

	/**
	 * 批量添加加班申请(add overtime apply)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public void addNewApplyAffirmor(LinkedHashMap map) throws Exception {
		try {
			// 插入新决裁者之前先将等级比较高的决裁者等级加1
			this.update("ess.affirmLeaveApply.updateAffirmLevelByApplyNo", map);
			// 插入新的决裁者
			this.insert("ess.affirmLeaveApply.insertApplyAffirmor", map);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 加班Check列表(overtime apply affirm list)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
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
	@Override
	public List getOtCheckList(Object obj, int currentPage, int pageSize)
			throws Exception {
		List returnList = new ArrayList();
		if (currentPage > -1 && pageSize > -1) {
			returnList = this.queryForList(
					"ess.affirmLeaveApply.getOtCheckList", obj, currentPage,
					pageSize);
		} else {
			returnList = this.queryForList(
					"ess.affirmLeaveApply.getOtCheckList", obj);
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
		return NumberUtils.parseNumber(
				ObjectUtils.toString(this.queryForObject(
						"ess.affirmLeaveApply.getOtCheckListCnt", obj)),
				Integer.class);
	}

	/**
	 * 获得决裁信息(get ess_affirm information by affirmNo)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public Object getEssAffirmInfoByAffirmNo(LinkedHashMap obj)
			throws Exception {
		return this.queryForObject(
				"ess.affirmLeaveApply.getEssAffirmInfoByAffirmNo", obj);
	}

	/**
	 * 获得Check信息(get ess_Check information by checkNo)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public Object getEssCheckInfoByCheckNo(LinkedHashMap obj) throws Exception {
		return this.queryForObject(
				"ess.affirmLeaveApply.getEssCheckInfoByCheckNo", obj);
	}

	/**
	 * 获得Check信息(get ess_Check information by CheckorId)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public Object getEssCheckInfoByCheckorId(LinkedHashMap obj)
			throws Exception {
		return this.queryForObject(
				"ess.affirmLeaveApply.getEssCheckInfoByCheckorId", obj);
	}

	/**
	 * 获得Check信息(get ess_Check information by CheckorId)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getEssCheckCntByCheckorId(LinkedHashMap obj) throws Exception {
		return NumberUtils
				.parseNumber(
						ObjectUtils
								.toString(this
										.queryForObject(
												"ess.affirmLeaveApply.getEssCheckCntByCheckorId",
												obj)), Integer.class);
	}

	/**
	 * 获得当前信息决裁流程中最大的决裁级别(get Max Affirm level By ApplyNo )
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getMaxAffirmLevelByApplyNo(LinkedHashMap obj) throws Exception {
		return NumberUtils
				.parseNumber(
						ObjectUtils
								.toString(this
										.queryForObject(
												"ess.affirmLeaveApply.getMaxAffirmLevelByApplyNo",
												obj)), Integer.class);
	}

	/**
	 * 获得当前信息Check流程中最大的Check级别(get Max Check level By ApplyNo )
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getMaxCheckLevelByApplyNo(LinkedHashMap obj) throws Exception {
		return NumberUtils
				.parseNumber(
						ObjectUtils
								.toString(this
										.queryForObject(
												"ess.affirmLeaveApply.getMaxCheckLevelByApplyNo",
												obj)), Integer.class);
	}

	/**
	 * 获得决裁信息(get ess_check information by applyNo and level)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public Object getEssAffirmInfoByApplyNoAndLevel(LinkedHashMap obj)
			throws Exception {
		return this.queryForObject(
				"ess.affirmLeaveApply.getEssAffirmInfoByApplyNoAndLevel", obj);
	}

	/**
	 * 获得下一级Check信息(get ess_check information by applyNo and level)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public Object getEssCheckInfoByApplyNoAndLevel(LinkedHashMap obj)
			throws Exception {
		return this.queryForObject(
				"ess.affirmLeaveApply.getEssCheckInfoByApplyNoAndLevel", obj);
	}

	/**
	 * check加班申请(check overtime apply)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@Override
	public int checkApplyInfo(LinkedHashMap object) throws Exception {
		this.update("ess.affirmLeaveApply.updateEssCheckByCheckNo", object);

		return 1;
	}

	/**
	 * check加班申请(check overtime apply)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@Override
	public int updateCurrentCheckor(LinkedHashMap object) throws Exception {
		this.update("ess.affirmLeaveApply.updateEssNextCheckorByAffirmNo",
				object);
		return 1;
	}

	/**
	 * 添加checkor(add checkor)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@Override
	public int addApplyCheckList(LinkedHashMap object) throws Exception {
		this.insert("ess.affirmLeaveApply.insertCheckorByAffirmNo", object);

		return 1;
	}

	/**
	 * 加班决裁列表(overtime apply affirm list)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
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
	@Override
	public List getOvertimeAffirmList(Object obj, int currentPage, int pageSize)
			throws Exception {
		List returnList = new ArrayList();
		if (currentPage > -1 && pageSize > -1) {
			returnList = this.queryForList(
					"ess.affirmLeaveApply.getOvertimeAffirmList", obj,
					currentPage, pageSize);
		} else {
			returnList = this.queryForList(
					"ess.affirmLeaveApply.getOvertimeAffirmList", obj);
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
		return NumberUtils.parseNumber(ObjectUtils.toString(this
				.queryForObject(
						"ess.affirmLeaveApply.getOvertimeAffirmListCnt", obj)),
				Integer.class);
	}

	/**
	 * 休假/出差/外出申请决裁列表(leave/evection/egression apply affirm list)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public List getLeaveApplyAffirmList(Object obj) throws Exception {
		return this.getLeaveApplyAffirmList(obj, -1, -1);
	}
	/**
	 * 休假申请决裁列表(leave/evection/egression apply affirm list)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public List getAttendanceAffirmList(Object obj) throws Exception {
		List returnList = new ArrayList();
		
			returnList = this.queryForList(
					"ess.affirmLeaveApply.getAttendanceAffirmList1", obj);
		
		return returnList;
	}

	/**
	 * 休假/出差/外出申请决裁列表(leave/evection/egression apply affirm list)
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@Override
	public List getLeaveApplyAffirmList(Object obj, int currentPage,
			int pageSize) throws Exception {
		List returnList = new ArrayList();
		if (currentPage > -1 && pageSize > -1) {
			returnList = this.queryForList(
					"ess.affirmLeaveApply.getLeaveApplyAffirmList", obj,
					currentPage, pageSize);
		} else {
			returnList = this.queryForList(
					"ess.affirmLeaveApply.getLeaveApplyAffirmList", obj);
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
		return NumberUtils
				.parseNumber(
						ObjectUtils
								.toString(this
										.queryForObject(
												"ess.affirmLeaveApply.getLeaveApplyAffirmListCnt",
												obj)), Integer.class);
	}

	/**
	 * 批量通过/否决休假/出差/外出申请(batch pass and reject leave/evection/egression apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Override
	public int saveLeaveApplyAffirmInBatch(List list) throws Exception {
		for (int i = 0; i < list.size(); i++) {
			LinkedHashMap map = (LinkedHashMap) list.get(i);
			String flag = map.get("FLAG") != null ? map.get("FLAG").toString()
					: "0";
			this.update("ess.affirmLeaveApply.updateEssAffirmByAffirmNo", map);
			this.update("ess.affirmLeaveApply.updateEssApplyLeaveByApplyNo",
					map);
			//check信息修改为已check
			//this.sendToLGEPCheckBatch(map);
			// 审批者决裁完后更新他添加的check信息为已check
			//this.paTempSalesDAO.updateCheckFlagByEssAffirmNo(map);
			// 如果是决裁流程的最后一步且为通过的时候执行下面的操作
			//LinkedHashMap leaveApply = (LinkedHashMap) this.queryForObject(
			//		"ess.humanAffirm.getLeaveApplyInfoByApplyNo", map);
			/*if ("1".equals(String.valueOf(flag))) {
				leaveApply.put("AFFIRM_TYPE", "LEAVE_AFFIRM");
				leaveApply.put("CPNY_ID", map.get("CPNY_ID"));
				leaveApply.put("ADMIN_ID", map.get("ADMIN_ID"));
				leaveApply.put("LANGUAGE", map.get("LANGUAGE"));
				map.put("APPLY_PERSON_ID", leaveApply.get("PERSON_ID"));
				// 休假时段单位默认分钟
				// ar_apply_result相关操作
				leaveApply.put("UNIT", "MINUTE");
				if (null != leaveApply.get("APPLY_TYPE")
						&& "BATCH".equals(leaveApply.get("APPLY_TYPE"))) {
					doBatchInsertArApplyResult(leaveApply);
				} else {
					if (null != leaveApply.get("UNDO_APPLY_NO")
							&& !"".equals(leaveApply.get("UNDO_APPLY_NO"))) {
						leaveApply.put("SIGNLE", leaveApply.get("UNDO_APPLY_NO"));
					} 
					doInsertArApplyResult(leaveApply);
				}
			}
			if(leaveApply.get("UNDO_APPLY_NO") != null && !"".equals(leaveApply.get("UNDO_APPLY_NO")) && !"null".equals(leaveApply.get("UNDO_APPLY_NO"))){
				map.put("UNDO_APPLY_NO", StringUtil.checkNull(leaveApply.get("UNDO_APPLY_NO")));
			}else{
				map.put("UNDO_APPLY_NO", "");
			}*/
			//审批发送LGEP
			//sendToLGEP(map);
		}
		return 1;
	}
	/**
	 * 批量通过/否决休假(TSTO管理者审批)(batch pass and reject leave/evection/egression apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Override
	public int saveLeaveApplyAffirmInBatchForMangement(List list) throws Exception {
		for (int i = 0; i < list.size(); i++) {
			LinkedHashMap map = (LinkedHashMap) list.get(i);
			String APPLY_NO = (String) map.get("PK_NO");
			if (!"".equals(APPLY_NO)&& APPLY_NO != null) {
				this.update("ess.affirmLeaveApply.updateSyAffirmEmailByAffirmNo", map);//决裁表
				this.update("ess.affirmLeaveApply.updateEssApplyLeaveByApplyNo",map);//申请表
			}
			/*if(map.get("FROM_DATE").equals(map.get("TO_DATE"))){
			  this.update("ess.affirmLeaveApply.updateArDetailTstoApplyLeave",map);//修改明细表
			}else {
			  this.update("ess.affirmLeaveApply.updateArDetailAffrimMoreDay",map);//修改明细表	
			}*/
			
		}
		return 1;
	}
	@SuppressWarnings("unchecked")
	private String getArDetailTstoApplyOfApplyNo(LinkedHashMap map) throws Exception {
		String object2 = (String) this.queryForObject("ess.affirmLeaveApply.getArDetailTstoApplyOfApplyNo", map);
		if(object2 ==null){
			object2 = "0";
		}
		return object2;
	}

	/**
	 * 通过/否决休假/出差/外出申请(pass and reject leave/evection/egression apply)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@Override
	public int saveLeaveApplyAffirm(LinkedHashMap object) throws Exception {
		String flag = object.get("FLAG") != null ? object.get("FLAG")
				.toString() : "0";
		this.update("ess.affirmLeaveApply.updateEssAffirmByAffirmNo", object);
		this.update("ess.affirmLeaveApply.updateEssLeaveApplyTbByApplyNo",
				object);

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

				if (leaveApply.get("navTabId") != null
						&& leaveApply.get("navTabId").equals("ess0228")) {
					// 插入新的的AR_APPLY_RESULT的数据
					@SuppressWarnings("unused")
					String FROM_TIME = leaveApply.get("OLD_DAY").toString();// 开始时间
					String FROM_TIMESTART = leaveApply.get("OLD_DAY")
							.toString().substring(0, 10)
							+ " 09:00:00";// 加班开始时间
					String FROM_TIMEEND = leaveApply.get("OLD_DAY").toString()
							.substring(0, 10)
							+ " 18:00:00";// 加班结束时间
					String TO_TIME = leaveApply.get("TO_TIME").toString();// 结束时间
					String TO_TIME_START = TO_TIME.substring(0, 10)
							+ " 09:00:00";
					String TO_TIME_END = TO_TIME.substring(0, 10) + " 18:00:00";
					// 加班
					leaveApply.put("FROM_TIME", FROM_TIMESTART);
					leaveApply.put("TO_TIME", FROM_TIMEEND);
					leaveApply.put("APPLY_TYPE_CODE", "124858");
					this.insert(
							"ess.humanAffirm.insertArApplyResultForArApply",
							leaveApply);
					// 调休
					leaveApply.put("FROM_TIME", TO_TIME_START);
					leaveApply.put("TO_TIME", TO_TIME_END);
					leaveApply.put("APPLY_TYPE_CODE", "123646");
					if (!leaveApply.get("APPLY_DATE").equals("31")) {
						leaveApply.put("APPLY_DATE", "");
					}
					this.insert(
							"ess.humanAffirm.insertArApplyResultForArApply1",
							leaveApply);

				} else {
					if (!leaveApply.get("APPLY_DATE").equals("31")) {
						leaveApply.put("APPLY_DATE", "");
					}
					this.insert(
							"ess.humanAffirm.insertArApplyResultForArApply",
							leaveApply);
				}
				// 计算考勤
				((LinkedHashMap) leaveApply).put("caltype", "emp");
				this.insert("ess.humanAffirm.caculateDetailP", leaveApply);
			}

		}

		// 当裁决选择否定的时候 删除 AR_APPLY_RESULT的数据
		/*
		 * String affirmFlag = object.get("AFFIRM_FLAG") != null ?
		 * object.get("AFFIRM_FLAG").toString() : "0";
		 * if("2".equals(affirmFlag)){ LinkedHashMap leaveApplyA =new
		 * LinkedHashMap(); leaveApplyA.put("APPLY_NO", object.get("APPLY_NO"));
		 * this
		 * .delete("ess.humanAffirm.deleteArApplyResultByApplyNoAndPersonId",
		 * leaveApplyA); }
		 */
		return 1;
	}

	/**
	 * 根据法人和参数号查找对应的值(get parameter Value By CpnyId And ParamNo)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public Object getParamValueByCpnyIdAndParamNo(LinkedHashMap obj)
			throws Exception {
		return this.queryForObject("ess.affirmLeaveApply.getParamInfoValue",
				obj);
	}

	/**
	 * 通过信息申请NO获得该信息决裁流程的决裁者(get affirmor list by applyNo)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public List getEssAffirmInfoByApplyNo(LinkedHashMap obj) throws Exception {
		return this.queryForList(
				"ess.affirmLeaveApply.getEssAffirmInfoByApplyNoAndLevel", obj);
	}

	@Override
	public int approveApplication(LinkedHashMap map) {
		try {
			this.update("ess.affirmLeaveApply.updateEssAffirmByAffirmNo", map);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	public void addPaParamApplication(LinkedHashMap map) {
		try {
			this.insert("ess.WageApp.insertPaParamApplications", map);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateApplicatCheck(LinkedHashMap paramMap) {
		try {
			this.insert("ess.WageApp.updateApplicatCheck", paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public int saveApplyLeaveAffirm(LinkedHashMap object) throws Exception {
		String flag = object.get("FLAG") != null ? object.get("FLAG")
				.toString() : "0";
		this.update("ess.affirmLeaveApply.updateEssAffirmByAffirmNo", object);
		this
				.update("ess.affirmLeaveApply.updateEssApplyLeaveByApplyNo",
						object);
		//check信息修改为已check
		this.sendToLGEPCheckBatch(object);
		// 审批者决裁完后更新他添加的check信息为已check
		this.paTempSalesDAO.updateCheckFlagByEssAffirmNo(object);
		// 如果是决裁流程的最后一步通过而且也不需要人事确认
		LinkedHashMap leaveApply = (LinkedHashMap) this.queryForObject(
				"ess.humanAffirm.getLeaveApplyInfoByApplyNo", object);
		if ("1".equals(flag)) {
			leaveApply.put("ADMIN_ID", object.get("ADMIN_ID"));
			leaveApply.put("CPNY_ID", object.get("CPNY_ID"));
			leaveApply.put("LANGUAGE", object.get("LANGUAGE"));
			object.put("APPLY_PERSON_ID", leaveApply.get("PERSON_ID"));
			leaveApply.put("AFFIRM_TYPE", "LEAVE_AFFIRM");
			// 休假时段单位默认分钟
			leaveApply.put("UNIT", "MINUTE");
			// ar_apply_result相关操作
			if (null != leaveApply.get("APPLY_TYPE")
					&& "BATCH".equals(leaveApply.get("APPLY_TYPE"))) {
				doBatchInsertArApplyResult(leaveApply);
			} else {
				if (null != leaveApply.get("UNDO_APPLY_NO")
						&& !"".equals(leaveApply.get("UNDO_APPLY_NO"))) {
					leaveApply.put("SIGNLE", leaveApply.get("UNDO_APPLY_NO"));
				} 
				doInsertArApplyResult(leaveApply);
			}
		}
		if(leaveApply.get("UNDO_APPLY_NO") != null && !"".equals(leaveApply.get("UNDO_APPLY_NO")) && !"null".equals(leaveApply.get("UNDO_APPLY_NO"))){
			object.put("UNDO_APPLY_NO", StringUtil.checkNull(leaveApply.get("UNDO_APPLY_NO")));
		}else{
			object.put("UNDO_APPLY_NO", "");
		}
		//审批发送LGEP
		sendToLGEP(object);
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
	private void sendToLGEPCheckBatch(LinkedHashMap paramMap){
		List checkList = this.paTempSalesDAO.getCheckListToLgep(paramMap);
		if(checkList != null && checkList.size() > 0){
			for(int i=0;i<checkList.size();i++){
				LinkedHashMap lgepMap = (LinkedHashMap)checkList.get(i);
				lgepMap.put("APPLY_TYPE", APPLY_TYPE_NO);
				lgepMap.put("APPLY_NO", paramMap.get("APPLY_NO"));
				lgepMap.put("AFFIRM_FLAG", '1');
				lgepMap.put("AFFIRM_LEVEL", lgepMap.get("ESS_CHECK_NO"));
				lgepMap.put("AFFIRM_EMPID", lgepMap.get("CHECKOR_ID"));
				lgepMap.put("AAI_URL", "http://{serverIp}/LGEP/affirm/viewLeaveCheck?LGEP=LGEP&LANGUAGE=zh&personId=123&APPLY_NO=" + paramMap.get("APPLY_NO") + "&affirmOrCheck=2");
				this.affirmInfoToLGEPSer.check(lgepMap);
			}
		}
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
	private void sendToLGEPCheckBatchCwa(LinkedHashMap paramMap){
		List checkList = this.paTempSalesDAO.getCheckListToLgep(paramMap);
		if(checkList != null && checkList.size() > 0){
			for(int i=0;i<checkList.size();i++){
				LinkedHashMap lgepMap = (LinkedHashMap)checkList.get(i);
				lgepMap.put("APPLY_TYPE", "218197");
				lgepMap.put("APPLY_NO", paramMap.get("APPLY_NO"));
				lgepMap.put("AFFIRM_FLAG", '1');
				lgepMap.put("AFFIRM_LEVEL", lgepMap.get("ESS_CHECK_NO"));
				lgepMap.put("AFFIRM_EMPID", lgepMap.get("CHECKOR_ID"));
				lgepMap.put("AAI_URL", "http://{serverIp}/LGEP/affirm/CwaCheck?LGEP=LGEP&LANGUAGE=zh&personId=123&APPLY_NO=" + paramMap.get("APPLY_NO") + "&affirmOrCheck=2");
				this.affirmInfoToLGEPSer.check(lgepMap);
			}
		}
	}
	
	@Override
	public int saveApplyCwaAffirm(LinkedHashMap object) throws Exception {
		String flag = object.get("FLAG") != null ? object.get("FLAG")
				.toString() : "0";

		this.update("ess.affirmLeaveApply.updateEssAffirmByAffirmNo", object);
		this.update("ess.affirmLeaveApply.updateEssApplyCwaByApplyNo", object);

		//check信息修改为已check
		this.sendToLGEPCheckBatchCwa(object);
		// 审批者决裁完后更新他添加的check信息为已check
		this.paTempSalesDAO.updateCheckFlagByEssAffirmNo(object);
		LinkedHashMap leaveApply = (LinkedHashMap) this.queryForObject(
				"ess.humanAffirm.getCwaApplyInfoByApplyNo", object);
		object.put("APPLY_PERSON_ID", leaveApply.get("PERSON_ID"));
		
		// 如果是决裁流程的最后一步通过而且也不需要人事确认
		if ("1".equals(flag)) {
			try {
				if(!this.isAfterLeaveCwa(leaveApply)){
					// 插入新的的AR_SHIFT_CHANGE的数据
					LinkedHashMap param = new LinkedHashMap();
					param.put("PERSON_ID", leaveApply.get("PERSON_ID"));
					param.put("CPNY_ID", leaveApply.get("CPNY_ID"));
					param.put("AR_FROM_TIME", leaveApply.get("APPLY_DATE"));
					param.put("AR_TO_TIME", leaveApply.get("APPLY_DATE"));
					param.put("CHANGE_TYPE", "考勤异常申请");

					if("N".equals(leaveApply.get("BATCH_YN"))){
						if("Y".equals(leaveApply.get("LASTMONTH_YN"))){
							this.insert("ess.humanAffirm.insertLeaveApplyResultForArApplyAfterZhuisu",param);
						}else{
							this.insert("ess.humanAffirm.insertLeaveApplyResultForArApplyAfter",param);
						}
					}else{
						this.insert("ess.humanAffirm.insertLeaveApplyResultForArApplyAfterBATCH",param);
						this.insert("ess.humanAffirm.insertLeaveApplyResultForArApplyAfterZhuisuBATCH",param);
					}
				}
				//this.detailCalculate(leaveApply);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		this.sendCwaToLGEP(object);
		return 1;
	}
	

	/**
	 * 判断是否为事后申请休假 overtime apply)
	 * 
	 * @param paramMap
	 * @return
	 */
	private boolean isAfterLeaveCwa(Map paramMap) throws Exception {
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
	private void sendCwaToLGEP(LinkedHashMap paramMap){
			LinkedHashMap lgepMap = new LinkedHashMap();
			lgepMap.put("APPLY_NO", paramMap.get("APPLY_NO"));
			lgepMap.put("APPLY_TYPE", "218197");
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
			lgepMap.put("AAI_URL", "http://{serverIp}/LGEP/affirm/viewCwaAbnormalAffirm?LGEP=LGEP&LANGUAGE=zh&APPLY_TYPE_NO=218197&personId=123&APPLY_NO=" + paramMap.get("APPLY_NO").toString());
			lgepMap.put("AAF_URL", "http://{serverIp}/LGEP/affirm/viewCwaAbnormalAffirm?LGEP=LGEP&LANGUAGE=zh&APPLY_TYPE_NO=218197&personId=123&APPLY_NO=" + paramMap.get("APPLY_NO").toString());
			lgepMap.put("ABY_URL", "http://{serverIp}/LGEP/affirm/viewCwaAbnormalAffirm?LGEP=LGEP&LANGUAGE=zh&APPLY_TYPE_NO=218197&personId=" + paramMap.get("NEXT_AFFIRM_ID") + "&APPLY_NO=" + paramMap.get("APPLY_NO").toString());
			lgepMap.put("PRE_AFFIRM_EMPID", paramMap.get("NEXT_AFFIRM_ID"));
			lgepMap.put("CURRENT_AFFIRM_ID", paramMap.get("NEXT_AFFIRM_ID"));
			this.affirmInfoToLGEPSer.affirm(lgepMap);
	}
	@Override
	public List getLeaveCheckList(Object obj, int currentPage, int pageSize)
			throws Exception {
		List returnList = new ArrayList();
		if (currentPage > -1 && pageSize > -1) {
			returnList = this.queryForList(
					"ess.affirmLeaveApply.getLeaveCheckList", obj, currentPage,
					pageSize);
		} else {
			returnList = this.queryForList(
					"ess.affirmLeaveApply.getLeaveCheckList", obj);
		}
		return returnList;
	}

	@Override
	public List getLeaveCheckList(Object obj) throws Exception {
		return this.getLeaveCheckList(obj, -1, -1);
	}

	@Override
	public int getLeaveCheckListCnt(Object obj) throws Exception {
		return NumberUtils.parseNumber(ObjectUtils.toString(this
				.queryForObject("ess.affirmLeaveApply.getLeaveCheckListCnt",
						obj)), Integer.class);
	}

	/**
	 * 审批通过后计算日考勤
	 * 
	 * @param map
	 */
	private void detailCalculate(LinkedHashMap map) {
		map.put("from_date", map.get("AR_FROM_TIME"));
		map.put("to_date", map.get("AR_TO_TIME"));
		map.put("caltype", "emp");
		map.put("interCpnyID", map.get("CPNY_ID"));
		map.put("empid", map.get("PERSON_ID"));
		arDetailCalulateDao.detailCalculate(map);
	}

	/**
	 * 判断是否为追溯休假 overtime apply)
	 * 
	 * @param paramMap
	 * @return
	 */
	public boolean isZhuisuLeave(LinkedHashMap paramMap) throws Exception {
		paramMap.put("FLAG", "S");// 获取考勤月开始日期
		String arStartDateStr = this.infoApplyLeaveDao
				.getCurrentArDate(paramMap);
		paramMap.put("FLAG", "E");// 获取考勤月结束日期
		String arEndDateStr = this.infoApplyLeaveDao.getCurrentArDate(paramMap);
		Date date = new Date();
		SimpleDateFormat sb = new SimpleDateFormat("yyyy-MM-dd HH:mm");

		String leaveApplyFrom = paramMap.get("FROM_TIME") != null ? paramMap
				.get("FROM_TIME").toString()
				: sb.format(date);
		String leaveApplyTo = paramMap.get("TO_TIME") != null ? paramMap
				.get("TO_TIME").toString() : sb.format(date);
		GregorianCalendar applyFrom = DateUtil
				.ParseGregorianCalendar(leaveApplyFrom);
		GregorianCalendar applyTo = DateUtil
				.ParseGregorianCalendar(leaveApplyTo);
		GregorianCalendar arStartDate = DateUtil
				.ParseGregorianCalendar(arStartDateStr);
		GregorianCalendar arEndDate = DateUtil
				.ParseGregorianCalendar(arEndDateStr);
		if ("TSTO".equals(paramMap.get("CPNY_ID"))) {
			arStartDate.add(2, -1);
			arEndDate.add(2, -1);
			if (applyFrom.after(arStartDate) && applyTo.before(arEndDate)) {
				return true;
			}
		}
		return false;
	}


	/**
	 * 判断是否为事后申请休假 overtime apply)
	 * 
	 * @param paramMap
	 * @return
	 */
	private boolean isAfterLeave(LinkedHashMap paramMap) throws Exception {
		Date date = new Date();
		SimpleDateFormat sb = new SimpleDateFormat("yyyy-MM-dd");

		String leaveApplyFrom = paramMap.get("AR_FROM_TIME") != null ? paramMap
				.get("AR_FROM_TIME").toString()
				: sb.format(date);
		String sysDateStr = sb.format(date);

		GregorianCalendar applyFrom = DateUtil
				.ParseGregorianCalendar(leaveApplyFrom);
		GregorianCalendar sysDate = DateUtil.ParseGregorianCalendar(sysDateStr);
		if(sysDateStr.equals(leaveApplyFrom)){
			return true;
		}
		if (applyFrom.after(sysDate)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 审批通过并完成，插入ar_apply_result
	 * 
	 * @param paramMap
	 * @return
	 */
	private void doInsertArApplyResult(LinkedHashMap leaveApply)
			throws Exception {
		if (null != leaveApply.get("UNDO_APPLY_NO")
				&& !"".equals(leaveApply.get("UNDO_APPLY_NO"))) {
			if (null != leaveApply.get("SIGNLE")
					&& !"".equals(leaveApply.get("SIGNLE"))) {
			//先删除追溯表
				this
						.delete(
								"ess.humanAffirm.deleteArApplyResultByApplyNoAndPersonIdZhuisuSignle",
								leaveApply);
				this
				.delete(
						"ess.humanAffirm.deleteArApplyResultByApplyNoAndPersonIdSignle",
						leaveApply);
			} else {
			//先删除追溯表
				this
						.delete(
								"ess.humanAffirm.deleteArApplyResultByApplyNoAndPersonIdZhuisu",
								leaveApply);
				this
				.delete(
						"ess.humanAffirm.deleteArApplyResultByApplyNoAndPersonId",
						leaveApply);
			}

				if(!this.isAfterLeave(leaveApply)){
					// 插入新的的AR_SHIFT_CHANGE的数据
					leaveApply.put("CHANGE_TYPE", "休假申请");
					this
							.insert(
									"ess.humanAffirm.insertLeaveApplyResultForArApplyAfter",
									leaveApply);
				}
				
		} else {
			// 申请日期以申请开始日期为准
			leaveApply.put("APPLY_DATE", leaveApply.get("FROM_TIME").toString()
					.length() >= 10 ? leaveApply.get("FROM_TIME").toString()
					.substring(0, 10) : "");
			if (this.isZhuisuLeave(leaveApply)) {
				// 删除之前的AR_APPLY_RESULT_LASTMONTH的数据
				this.delete("ess.humanAffirm.deleteArApplyResultByApplyNoAndPersonIdZhuisu",leaveApply);
				// 插入新的的AR_APPLY_RESULT_LASTMONTH的数据
				this.insert("ess.humanAffirm.insertLeaveApplyResultForArApplyZhuisu",leaveApply);
				// 插入新的的AR_SHIFT_CHANGE的数据
				leaveApply.put("CHANGE_TYPE", "休假申请");
				this.insert("ess.humanAffirm.insertLeaveApplyResultForArApplyAfterZhuisu",leaveApply);
			} else {
			
				// 删除之前的AR_APPLY_RESULT的数据
				this
						.delete(
								"ess.humanAffirm.deleteArApplyResultByApplyNoAndPersonId",
								leaveApply);

				if("16415".equals(leaveApply.get("APPLY_TYPE_CODE"))){
					if("TSTO".equals(leaveApply.get("CPNY_ID")) ||
							"LGETR".equals(leaveApply.get("CPNY_ID")) ||
							"LGEHN".equals(leaveApply.get("CPNY_ID")) ||
							"LGEHZ".equals(leaveApply.get("CPNY_ID")) ||
							"LGEQA".equals(leaveApply.get("CPNY_ID")) ||
							"LGEKS".equals(leaveApply.get("CPNY_ID")) ||
							"LGEQD".equals(leaveApply.get("CPNY_ID"))){
						leaveApply.put("FROM_TIME", leaveApply.get("AR_FROM_TIME"));
						leaveApply.put("TO_TIME", leaveApply.get("AR_TO_TIME"));
					}
				}
				// 插入新的的AR_APPLY_RESULT的数据
				this.insert("ess.humanAffirm.insertLeaveApplyResultForArApply",
						leaveApply);
				
				if(!this.isAfterLeave(leaveApply)){
					// 插入新的的AR_SHIFT_CHANGE的数据
					leaveApply.put("CHANGE_TYPE", "休假申请");
					this
							.insert(
									"ess.humanAffirm.insertLeaveApplyResultForArApplyAfter",
									leaveApply);
				}
			}
		}
		// 计算日考勤
		if("26".equals(leaveApply.get("APPLY_TYPE_CODE")) || "18135".equals(leaveApply.get("APPLY_TYPE_CODE"))){
			this.detailCalculate(leaveApply);
		}
	}

	/**
	 * 批量申请审批通过并完成，插入ar_apply_result
	 * 
	 * @param paramMap
	 * @return
	 */
	private void doBatchInsertArApplyResult(LinkedHashMap leaveApply)
			throws Exception {
		if (null != leaveApply.get("UNDO_APPLY_NO")
				&& !"".equals(leaveApply.get("UNDO_APPLY_NO"))) {
			leaveApply.put("APPLY_NO", leaveApply.get("UNDO_APPLY_NO"));
			List list = this.queryForList(
					"ess.infoApplyLeave.getEssLeaveBatchList", leaveApply);
			for (int i = 0; i < list.size(); i++) {
				LinkedHashMap leaveApplyMap = (LinkedHashMap) list.get(i);
				leaveApplyMap.put("CPNY_ID", leaveApply.get("CPNY_ID"));
				leaveApplyMap.put("APPLY_NO", leaveApplyMap.get("BATCH_APPLY_NO"));
				leaveApplyMap.put("UNDO_APPLY_NO",leaveApply.get("UNDO_APPLY_NO"));
				doInsertArApplyResult(leaveApplyMap);
			}
		}else{
			List list = this.queryForList(
					"ess.infoApplyLeave.getEssLeaveBatchList", leaveApply);
			for (int i = 0; i < list.size(); i++) {
				LinkedHashMap leaveApplyMap = (LinkedHashMap) list.get(i);
				leaveApplyMap.put("CPNY_ID", leaveApply.get("CPNY_ID"));
				leaveApplyMap.put("APPLY_NO", leaveApplyMap.get("BATCH_APPLY_NO"));
				doInsertArApplyResult(leaveApplyMap);
			}
		}
	}
	
	public Object getNextAffirmProver(LinkedHashMap leaveApply) {
		Object object = null;
		try {
			object = this.queryForObject(
					"ess.affirmLeaveApply.getNextAffirmProver", leaveApply);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object;
	}
	
	/**
	 * 审批后发送LGEP
	 * @param eventId
	 */
	private void sendToLGEP(LinkedHashMap paramMap){
			LinkedHashMap lgepMap = new LinkedHashMap();
			lgepMap.put("APPLY_TYPE", APPLY_TYPE_NO);
			lgepMap.put("APPLY_NO", paramMap.get("APPLY_NO"));
			if("4".equals(paramMap.get("AFFIRM_READ_FLAG").toString())){
				lgepMap.put("AFFIRM_LEVEL", paramMap.get("NEXT_AFFIRM_LEVEL"));
				lgepMap.put("AFFIRM_FLAG", 1);
			}else{//决裁完成
				lgepMap.put("FINISH", "FINISH");
				lgepMap.put("AFFIRM_FLAG", paramMap.get("AFFIRM_READ_FLAG"));
				lgepMap.put("AFFIRM_LEVEL", paramMap.get("currentAffirmLevel"));
			}
			lgepMap.put("CREATED_BY", paramMap.get("APPLY_PERSON_ID"));
			lgepMap.put("AFFIRM_EMPID", paramMap.get("CURRENT_AFFIRM_ID"));
			lgepMap.put("AAI_URL", "http://{serverIp}/LGEP/affirm/viewLeaveAffirm?LGEP=LGEP&LANGUAGE=zh&personId=123&APPLY_NO=" + paramMap.get("APPLY_NO").toString() + "&unDoApplyNo=" + StringUtil.checkNull(paramMap.get("UNDO_APPLY_NO")));
			lgepMap.put("AAF_URL", "http://{serverIp}/LGEP/affirm/viewLeaveAffirm?LGEP=LGEP&LANGUAGE=zh&personId=123&APPLY_NO=" + paramMap.get("APPLY_NO").toString() + "&unDoApplyNo=" + StringUtil.checkNull(paramMap.get("UNDO_APPLY_NO")));
			lgepMap.put("ABY_URL", "http://{serverIp}/LGEP/affirm/viewLeaveAffirm?LGEP=LGEP&LANGUAGE=zh&personId=" + paramMap.get("NEXT_AFFIRM_ID") + "&APPLY_NO=" + paramMap.get("APPLY_NO").toString() + "&unDoApplyNo=" + StringUtil.checkNull(paramMap.get("UNDO_APPLY_NO")));
			lgepMap.put("PRE_AFFIRM_EMPID", paramMap.get("NEXT_AFFIRM_ID"));
			lgepMap.put("CURRENT_AFFIRM_ID", paramMap.get("NEXT_AFFIRM_ID"));
			this.affirmInfoToLGEPSer.affirm(lgepMap);
	}


	/**
	 * 根据perosnid获取法人代码
	 * @param leaveApply
	 * @return
	 */
	public String getCpnyIdByPersonId(LinkedHashMap leaveApply) {
		String object = null;
		try {
			object = StringUtil.checkNull(this.queryForObject(
					"ess.affirmLeaveApply.getCpnyIdByPersonId", leaveApply));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object;
	}
	

	public int saveApplyCwaAffirmBatch(List list) throws Exception {
		for (int i = 0; i < list.size(); i++) {
			LinkedHashMap map = (LinkedHashMap) list.get(i);
			String flag = map.get("FLAG") != null ? map.get("FLAG")
					.toString() : "0";
	
			this.update("ess.affirmLeaveApply.updateEssAffirmByAffirmNo", map);
			this.update("ess.affirmLeaveApply.updateEssApplyCwaByApplyNo", map);
	
			//check信息修改为已check
			this.sendToLGEPCheckBatchCwa(map);
			// 审批者决裁完后更新他添加的check信息为已check
			this.paTempSalesDAO.updateCheckFlagByEssAffirmNo(map);
			LinkedHashMap leaveApply = (LinkedHashMap) this.queryForObject(
					"ess.humanAffirm.getCwaApplyInfoByApplyNo", map);

			map.put("APPLY_PERSON_ID", leaveApply.get("PERSON_ID"));
			// 如果是决裁流程的最后一步通过而且也不需要人事确认
			if ("1".equals(flag)) {
				try {
	
					if(!this.isAfterLeaveCwa(leaveApply)){
						// 插入新的的AR_SHIFT_CHANGE的数据
						LinkedHashMap param = new LinkedHashMap();
						param.put("PERSON_ID", leaveApply.get("PERSON_ID"));
						param.put("CPNY_ID", leaveApply.get("CPNY_ID"));
						param.put("AR_FROM_TIME", leaveApply.get("APPLY_DATE"));
						param.put("AR_TO_TIME", leaveApply.get("APPLY_DATE"));
						param.put("CHANGE_TYPE", "考勤异常申请");
						
						this.insert("ess.humanAffirm.insertLeaveApplyResultForArApplyAfter",param);
						if("Y".equals(leaveApply.get("LASTMONTH_YN"))){
							this.insert("ess.humanAffirm.insertLeaveApplyResultForArApplyAfterZhuisu",param);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			this.sendCwaToLGEP(map);
		}
		return 1;
	}
}