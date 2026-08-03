package com.ait.sys.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.affirm.service.AffirmInfoToLGEPSer;
import com.ait.sys.dao.AttendItemMappingDao;
import com.ait.sys.dao.SyLanguageDao;
import com.ait.web.util.SqlMapClientSupport;

@Repository
public class AttendItemMapingDaoImpl extends SqlMapClientSupport implements AttendItemMappingDao{
	@Autowired
	private SyLanguageDao syLanguageDao;
	
	@Autowired
	private AffirmInfoToLGEPSer affirmInfoToLGEPSer;
	private static String APPLY_TYPE_NO = "1102";

	// 离职申请决裁邀请名称
	private static String APPLY_TYPE_NAME = "考勤代码审批邀请";
	/**
	 * 查找所有考勤项目（明细项目和汇总项目）的和法人匹配信息列表   不分页
	 * @param object
	 * @return
	 */
	@Override
	public List getAttendItemMappingList(Object object) {
        List returnList = new ArrayList() ;
		returnList = this.getAttendItemMappingList(object, -1, -1) ;
		return returnList ;
	}
	
	/**
	 * 查找所有考勤项目（明细项目和汇总项目）的和法人匹配信息列表   不分页 考勤代码查看页面
	 * @param object
	 * @return
	 */
	@Override
	public List getAttendItemCheckMapList(Object object) {
        List returnList = new ArrayList() ;
		returnList = this.getAttendItemCheckMapList(object, -1, -1) ;
		return returnList ;
	}


	/**
	 * 查找所有考勤项目(明细项目和汇总项目)的和法人匹配信息的数量
	 * @param object
	 * @return
	 */
	@Override
	public int getAttendItemMappingCnt(Object object) {
        int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("sys.attendancesetting.getAttendItemMappingCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}
	
	/**
	 * 查找所有考勤项目(明细项目和汇总项目)的和法人匹配信息的数量 考勤代码查看页面
	 * @param object
	 * @return
	 */
	@Override
	public int getAttendItemCheckMapCnt(Object object) {
        int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("sys.attendancesetting.getAttendItemCheckMapCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}

	/**
	 * 分页查找所有考勤项目（明细项目和汇总项目）的和法人匹配信息列表   
	 * @param object
	 * @return
	 */
	@Override
	public List getAttendItemMappingList(Object object, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("sys.attendancesetting.getAttendItemMappingList", object, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("sys.attendancesetting.getAttendItemMappingList", object);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	/**
	 * 分页查找所有考勤项目（明细项目和汇总项目）的和法人匹配信息列表    考勤代码查看页面
	 * @param object
	 * @return
	 */
	@Override
	public List getAttendItemCheckMapList(Object object, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("sys.attendancesetting.getAttendItemCheckMapList", object, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("sys.attendancesetting.getAttendItemCheckMapList", object);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}

	/**
	 * 查找单行记录的详细信息
	 */
	@Override
	public Object getAttendItemMappingInfo(Object object) {
	    LinkedHashMap returnObj = new LinkedHashMap() ;
		List returnList = this.getAttendItemMappingList(object) ;
		if(returnList.size() > 0){
			returnObj = (LinkedHashMap)returnList.get(0) ;
		}
		return returnObj ;
	}
	
	@Override
	public int addAffirmAttendItemInfo(Object object) throws Exception {
		try {
			LinkedHashMap obj = (LinkedHashMap)this.syLanguageDao.saveSyGlobalName(object);
			this.insert("sys.attendancesetting.addAffirmAttendItemInfo", object);
			obj.put("APPLY_NO", obj.get("NO"));
			List list = new ArrayList();
			list = this.findAfirmorByRelation();
			LinkedHashMap affirmor = new LinkedHashMap();
			if(list != null && list.size()>0){
				affirmor = (LinkedHashMap) list.get(0);
			}
			obj.put("CURRENT_AFFIRM_ID",affirmor.get("PERSON_ID"));
			this.sendToLGEPInsert(obj);
			return 1;
		} catch (SQLException e) {			
			e.printStackTrace();
			return 0;
		}
	}
	
	
	/**
	 * 提交后发送LGEP
	 * 
	 * @param eventId
	 */
	private void sendToLGEPInsert(LinkedHashMap paramMap) {
		LinkedHashMap lgepMap = new LinkedHashMap();
		lgepMap.put("APPLY_TYPE", APPLY_TYPE_NO);
		lgepMap.put("APPLY_NO", paramMap.get("APPLY_NO"));
		lgepMap.put("APPLY_TYPE_NAME", APPLY_TYPE_NAME);
		lgepMap.put("APPLY_TITLE", APPLY_TYPE_NAME);
		lgepMap.put("APPLY_EMPID", paramMap.get("PERSON_ID"));
		lgepMap.put(
				"ARI_URL",
				"http://{serverIp}/LGEP/affirm/viewAffirmArItemInfo?LGEP=LGEP&LANGUAGE=zh&personId=123&APPLY_NO="
						+ paramMap.get("APPLY_NO")
						+ "&CPNY_ID="
						+ paramMap.get("CPNY_ID"));
		lgepMap.put(
				"ABY_URL",
				"http://{serverIp}/LGEP/affirm/viewAffirmArItemInfo?LGEP=LGEP&LANGUAGE=zh&personId="
						+ paramMap.get("CURRENT_AFFIRM_ID")
						+ "&APPLY_NO="
						+ paramMap.get("APPLY_NO")
						+ "&CPNY_ID="
						+ paramMap.get("CPNY_ID"));
		lgepMap.put("AFFIRM_LEVEL", "1");
		lgepMap.put("PRE_AFFIRM_EMPID", paramMap.get("CURRENT_AFFIRM_ID"));
		lgepMap.put("CURRENT_AFFIRM_ID", paramMap.get("CURRENT_AFFIRM_ID"));
		this.affirmInfoToLGEPSer.crateAffirm(lgepMap);
	}
	
	@Override
	public int addAffirmAttendInfo(Object object) throws Exception {
		try {
			this.insert("sys.attendancesetting.addAffirmAttendInfo", object);
			LinkedHashMap obj = (LinkedHashMap)object;
			obj.put("APPLY_NO", obj.get("ITEM_NO"));
			List list = new ArrayList();
			list = this.findAfirmorByRelation();
			LinkedHashMap affirmor = new LinkedHashMap();
			if(list != null && list.size()>0){
				affirmor = (LinkedHashMap) list.get(0);
			}
			obj.put("CURRENT_AFFIRM_ID",affirmor.get("PERSON_ID"));
			this.sendToLGEPInsert(obj);
			return 1;
		} catch (SQLException e) {			
			e.printStackTrace();
			return 0;
		}
	}

	/***
	 * 根据权限信息查找审批人  有工资考勤代码担当的权限的人就是审批人
	 */
	public List findAfirmorByRelation() throws Exception{
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("sys.attendancesetting.findAfirmorByRelation");
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
}
