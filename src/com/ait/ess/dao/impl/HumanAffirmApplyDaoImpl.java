package com.ait.ess.dao.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.ess.dao.HumanAffirmApplyDao;
import com.ait.web.util.SqlMapClientSupport;

@Repository
public class HumanAffirmApplyDaoImpl extends SqlMapClientSupport implements
		HumanAffirmApplyDao {

	/**
	 * 个人信息申请集合(personal information apply list)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public List getPersonInfoApplyConfirmList(Object obj) throws Exception {
		return this.getPersonInfoApplyConfirmList(obj, -1, -1);
	}

	/**
	 * 个人信息申请集合(personal information apply list)
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@Override
	public List getPersonInfoApplyConfirmList(Object obj, int currentPage,
			int pageSize) throws Exception {
		List returnList = new ArrayList();
		if (currentPage > -1 && pageSize > -1) {
			returnList = this.queryForList(
					"ess.humanAffirm.getPersonInfoApplyConfirmList", obj,
					currentPage, pageSize);
		} else {
			returnList = this.queryForList(
					"ess.humanAffirm.getPersonInfoApplyConfirmList", obj);
		}
		return returnList;
	}

	/**
	 * 个人信息申请总数(personal information apply total count)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getPersonInfoApplyConfirmListCnt(Object obj) throws Exception {
		return NumberUtils
				.parseNumber(
						ObjectUtils
								.toString(this
										.queryForObject(
												"ess.humanAffirm.getPersonInfoApplyConfirmListCnt",
												obj)), Integer.class);
	}

	/**
	 * 人事确认通过/否决(human confirm pass or reject)
	 * 
	 * @param object
	 * @param conFlag
	 * @return
	 * @throws Exception
	 */
	public int savePersonInfoHumanConfirm(Object object) throws Exception {
		this.update("ess.humanAffirm.updateEssPersonalInfoByPersonId", object);

		String confirmFlag = ((Map) object).get("CONFIRM_FLAG") != null ? ((Map) object)
				.get("CONFIRM_FLAG").toString()
				: "";
		if ("1".equals(confirmFlag)) {
			//this.delete("ess.humanAffirm.delHrPersonalInfoByPersonId", object);
			//this.insert("ess.humanAffirm.insertHrPersonalInfoByPersonId",object);
			this.update("ess.humanAffirm.updateHrPersonalInfoByPersonIdNew",object);
			this.update("ess.humanAffirm.updateHrPersonalInfoByHrEmployee",object);
		}
		return 1;
	}

	/**
	 * 批量人事确认通过/否决(batch human confirm pass or reject)
	 * 
	 * @param object
	 * @param conFlag
	 * @return
	 * @throws Exception
	 */
	public int savePersonInfoHumanConfirmForBatch(List list) throws Exception {
		for (int i = 0; i < list.size(); i++) {
			Map map = (Map) list.get(i);
			this.update("ess.humanAffirm.updateEssPersonalInfoByPersonId", map);
			String confirmFlag = map.get("CONFIRM_FLAG") != null ? map.get(
					"CONFIRM_FLAG").toString() : "";
			if ("1".equals(confirmFlag)) {
				//this.delete("ess.humanAffirm.delHrPersonalInfoByPersonId", map);
				//this.insert("ess.humanAffirm.insertHrPersonalInfoByPersonId",map);
				//this.update("ess.humanAffirm.updateHrPersonalInfoByPersonId",map);
				this.update("ess.humanAffirm.updateHrPersonalInfoByPersonIdNew",map);
				this.update("ess.humanAffirm.updateHrPersonalInfoByHrEmployee",map);
			}
		}
		return 1;
	}
	
	/**
	 * 查询属于该法人的加班类型List
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getOverTimeApplyTypeList(Object obj) throws Exception {
		List returnList = new ArrayList();
		returnList = this.queryForList("ess.humanAffirm.getOverTimeApplyTypeList", obj);
			
		return returnList;
	}
	
	/**
	 * 查询属于该法人的加班转换类型List
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getConverTypeList(Object obj) throws Exception {
		List returnList = new ArrayList();
		returnList = this.queryForList("ess.humanAffirm.getConverTypeList", obj);
			
		return returnList;
	}
	
	/**
	 * 查询属于该法人的加班转默认调休类型List
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getDefaultDaoXiuList(Object obj) throws Exception {
		List returnList = new ArrayList();
		returnList = this.queryForList("ess.humanAffirm.getDefaultDaoXiuList", obj);
			
		return returnList;
	}
	
	/**
	 * 人事确认--加班申请信息列表(personnel confirm:overtime apply information list)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public List getOvertimeApplyConfirmList(Object obj) throws Exception {
		return this.getOvertimeApplyConfirmList(obj, -1, -1);
	}

	/**
	 * 人事确认--加班申请信息列表(personnel confirm:overtime apply information list)
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@Override
	public List getOvertimeApplyConfirmList(Object obj, int currentPage,
			int pageSize) throws Exception {
		List returnList = new ArrayList();
		if (currentPage > -1 && pageSize > -1) {
			returnList = this.queryForList(
					"ess.humanAffirm.getOvertimeApplyConfirmList", obj,
					currentPage, pageSize);
		} else {
			returnList = this.queryForList(
					"ess.humanAffirm.getOvertimeApplyConfirmList", obj);
		}
		return returnList;
	}

	/**
	 * 人事确认--加班申请信息总数(personnel confirm:overtime apply information total count)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getOvertimeApplyConfirmListCnt(Object obj) throws Exception {
		int countIfNormConf = NumberUtils
				.parseNumber(
						ObjectUtils
								.toString(this
										.queryForObject(
												"ess.humanAffirm.getOvertimeApplyConfirmListCnt",
												obj)), Integer.class);
		int countIfPreConf = NumberUtils
				.parseNumber(
						ObjectUtils
								.toString(this
										.queryForObject(
												"ess.humanAffirm.getOvertimeApplyConfirmListIfPreConfCnt",
												obj)), Integer.class);
		return countIfNormConf + countIfPreConf;
	}

	/**
	 * 人事确认--休假/出差/外出申请列表(personnel confirm:leave/evection/egression apply
	 * information list)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public List getLeaveApplyConfirmList(Object obj) throws Exception {
		return this.getLeaveApplyConfirmList(obj, -1, -1);
	}

	/**
	 * 人事确认--休假/出差/外出申请列表(personnel confirm:leave/evection/egression apply
	 * information list)
	 * 
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	@Override
	public List getLeaveApplyConfirmList(Object obj, int currentPage,
			int pageSize) throws Exception {
		List returnList = new ArrayList();
		if (currentPage > -1 && pageSize > -1) {
			returnList = this.queryForList("ess.humanAffirm.getLeaveApplyConfirmList", obj,currentPage, pageSize);
		} else {
			returnList = this.queryForList("ess.humanAffirm.getLeaveApplyConfirmList", obj);
		}
		return returnList;
	}

	/**
	 * 人事确认--休假/出差/外出申请总数(personnel confirm:leave/evection/egression apply
	 * information total count)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getLeaveApplyConfirmListCnt(Object obj) throws Exception {
		int countIfNormConf = NumberUtils.parseNumber(ObjectUtils.toString(this
				.queryForObject("ess.humanAffirm.getLeaveApplyConfirmListCnt",
						obj)), Integer.class);
		int countIfPreConf =0;
		if("1".equals(((LinkedHashMap)obj).get("IF_PRE_CONF")) ){
		countIfPreConf= NumberUtils.parseNumber(ObjectUtils.toString(this
				.queryForObject(
						"ess.humanAffirm.getLeaveApplyConfirmListIfPreConfCnt",
						obj)), Integer.class);
		}
		return countIfNormConf + countIfPreConf;
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
		return this.queryForObject("ess.humanAffirm.getParamInfoValue", obj);
	}

	/**
	 * 根据申请号获得休假申请信息(get Leave-Apply Information By ApplyNo)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public Object getLeaveApplyInfoByApplyNo(LinkedHashMap obj)
			throws Exception {
		return this.queryForObject(
				"ess.humanAffirm.getLeaveApplyInfoByApplyNo", obj);
	}

	/**
	 * 根据申请号获得加班申请信息(get Overtime-Apply Information By ApplyNo)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public Object getOvertimeApplyInfoByApplyNo(LinkedHashMap obj)
			throws Exception {
		return this.queryForObject(
				"ess.humanAffirm.getOvertimeApplyInfoByApplyNo", obj);
	}

	/**
	 * 加班申请人事确认通过/否决(human confirm pass or reject)
	 * 
	 * @param object
	 * @param conFlag
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int saveOvertimeApplyHumanConfirm(Object object) throws Exception {
		// 修改ESS_APPLY_OT表信息
		this.update("ess.humanAffirm.updateOvertimeApplyByApplyNo", object);
		String confirmFlag = object != null && ((LinkedHashMap) object).get("CONFIRM_FLAG") != null ? 
				((LinkedHashMap) object).get("CONFIRM_FLAG").toString(): "";
		// 人事确认通过
		if ("1".equals(confirmFlag)) {
			// 删除之前的AR_APPLY_RESULT的数据
			this.delete("ess.humanAffirm.deleteArApplyResultByApplyNoAndPersonId",object);
			// 插入新的的AR_APPLY_RESULT的数据
			this.insert("ess.humanAffirm.insertArApplyResultForArApply",object);
			// 计算考勤
			((LinkedHashMap) object).put("caltype", "emp");
			this.insert("ess.humanAffirm.caculateDetailP", object);
		}
		return 1;
	}

	/**
	 * 加班申请批量人事确认通过/否决(batch human confirm pass or reject)
	 * 
	 * @param object
	 * @param conFlag
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int saveOvertimeApplyHumanConfirmForBatch(List list)throws Exception {
		for (int i = 0; i < list.size(); i++) {
			Map map = (Map) list.get(i);
			// 修改ESS_APPLY_OT表信息
			String affirmFlag = map.get("CONFIRM_FLAG") != null ? map.get("CONFIRM_FLAG").toString() : "";
			this.update("ess.humanAffirm.updateOvertimeApplyByApplyNo", map);
			// 人事确认通过
			if ("1".equals(affirmFlag)) {
				// 删除之前的AR_APPLY_RESULT的数据
				this.delete("ess.humanAffirm.deleteArApplyResultByApplyNoAndPersonId",map);
				// 插入新的的AR_APPLY_RESULT的数据
				this.insert("ess.humanAffirm.insertArApplyResultForArApply",map);
				// 计算考勤
				map.put("caltype", "emp");
				this.insert("ess.humanAffirm.caculateDetailP", map);
			}
		}
		return 1;
	}

	/**
	 * 休假申请人事确认通过/否决(human confirm pass or reject)
	 * 
	 * @param object
	 * @param conFlag
	 * @return
	 * @throws Exception
	 */
	@Override
	public int saveLeaveApplyHumanConfirm(Object object) throws Exception {
		String confirmFlag = object != null
		&& ((LinkedHashMap) object).get("CONFIRM_FLAG") != null ? ((LinkedHashMap) object)
		.get("CONFIRM_FLAG").toString()
		: "";
		// 修改ESS_LEAVE_APPLY_TB表信息
		this.update("ess.humanAffirm.updateLeaveApplyByApplyNo", object);
		// 人事确认通过
		if ("1".equals(confirmFlag)) {
			// 删除之前的AR_APPLY_RESULT的数据
			this.delete(
					"ess.humanAffirm.deleteArApplyResultByApplyNoAndPersonId",
					object);
			
			
			if(((LinkedHashMap) object).get("navTabId")!=null&&((LinkedHashMap) object).get("navTabId").equals("ess0226")){
				
				// 插入新的的AR_APPLY_RESULT的数据
				String FROM_TIME=((LinkedHashMap) object).get("OLD_DAY").toString();//开始时间
				String FROM_TIMESTART=((LinkedHashMap) object).get("OLD_DAY").toString().substring(0,10)+" 09:00:00";//加班开始时间
				String FROM_TIMEEND=((LinkedHashMap) object).get("OLD_DAY").toString().substring(0,10)+" 18:00:00";//加班结束时间
				String TO_TIME=((LinkedHashMap) object).get("TO_TIME").toString();//结束时间
				String TO_TIME_START=TO_TIME.substring(0,10)+" 09:00:00";
				String TO_TIME_END=TO_TIME.substring(0,10)+" 18:00:00";
				//加班
				((LinkedHashMap) object).put("FROM_TIME", FROM_TIMESTART);
				((LinkedHashMap) object).put("TO_TIME", FROM_TIMEEND);
				((LinkedHashMap) object).put("APPLY_TYPE_CODE", "124858");
				this.insert("ess.humanAffirm.insertArApplyResultForLeaveApply",object);
				//调休
				((LinkedHashMap) object).put("FROM_TIME", TO_TIME_START);
				((LinkedHashMap) object).put("TO_TIME", TO_TIME_END);
				((LinkedHashMap) object).put("APPLY_TYPE_CODE", "123646");
				this.insert("ess.humanAffirm.insertArApplyResultForLeaveApply1",object);
			}else{
				this.insert("ess.humanAffirm.insertArApplyResultForLeaveApply",object);
			}
			
			// 计算考勤
			((LinkedHashMap) object).put("caltype", "emp");
			this.insert("ess.humanAffirm.caculateDetailP", object);
		}
		return 1;
	}

	/**
	 * 休假申请批量人事确认通过/否决(batch human confirm pass or reject)
	 * 
	 * @param object
	 * @param conFlag
	 * @return
	 * @throws Exception
	 */
	@Override
	public int saveLeaveApplyHumanConfirmForBatch(List list) throws Exception {
		for (int i = 0; i < list.size(); i++) {
			Map map = (Map) list.get(i);
			// 修改ESS_LEAVE_APPLY_TB表信息
			String affirmFlag = map.get("CONFIRM_FLAG") != null ? map.get(
					"CONFIRM_FLAG").toString() : "";
			this.update("ess.humanAffirm.updateLeaveApplyByApplyNo", map);

			// 人事确认通过
			if ("1".equals(affirmFlag)) {
				// 删除之前的AR_APPLY_RESULT的数据
				this
						.delete(
								"ess.humanAffirm.deleteArApplyResultByApplyNoAndPersonId",
								map);
				
				if(map.get("navTabId")!=null&&map.get("navTabId").equals("ess0226")){
					// 插入新的的AR_APPLY_RESULT的数据
					String FROM_TIME=map.get("OLD_DAY").toString();//开始时间
					String FROM_TIMESTART=map.get("OLD_DAY").toString().substring(0,10)+" 09:00:00";//加班开始时间
					String FROM_TIMEEND=map.get("OLD_DAY").toString().substring(0,10)+" 18:00:00";//加班结束时间
					String TO_TIME=map.get("TO_TIME").toString();//结束时间
					String TO_TIME_START=TO_TIME.substring(0,10)+" 09:00:00";
					String TO_TIME_END=TO_TIME.substring(0,10)+" 18:00:00";
					//加班
					map.put("FROM_TIME", FROM_TIMESTART);
					map.put("TO_TIME", FROM_TIMEEND);
					map.put("APPLY_TYPE_CODE", "124858");
					this.insert("ess.humanAffirm.insertArApplyResultForLeaveApply",map);
					//调休
					map.put("FROM_TIME", TO_TIME_START );
					map.put("TO_TIME", TO_TIME_END);
					map.put("APPLY_TYPE_CODE", "123646");
					this.insert("ess.humanAffirm.insertArApplyResultForLeaveApply1",map);
				
					
				}else{
					this.insert("ess.humanAffirm.insertArApplyResultForLeaveApply",map);
				}
				// 计算考勤
				map.put("caltype", "emp");
				this.insert("ess.humanAffirm.caculateDetailP", map);
			}
		}
		return 1;
	}
}