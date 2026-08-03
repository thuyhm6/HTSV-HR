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
import com.ait.sys.dao.salaryMappingDao;
import com.ait.web.util.SqlMapClientSupport;
@Repository
public class salaryMappingDaoImpl extends SqlMapClientSupport implements salaryMappingDao{
	@Autowired
	private SyLanguageDao syLanguageDao;
	@Autowired
	private AffirmInfoToLGEPSer affirmInfoToLGEPSer;
	
	@Autowired
	private AttendItemMappingDao attendItemMappingDao;
	private static String APPLY_TYPE_NO = "1101";

	// 离职申请决裁邀请名称
	private static String APPLY_TYPE_NAME = "工资代码审批邀请";
	
	/**
	 * 查找所有工资项目（基本项目，输入项目，计算项目）的和法人匹配信息列表   不分页
	 * @param object
	 * @return
	 */
	@Override
	public List getSalaryMappingList(Object object) {
        List returnList = new ArrayList() ;
		returnList = this.getSalaryMappingList(object, -1, -1) ;
		return returnList ;
	}
	
	/**
	 * 查找所有工资项目（基本项目，输入项目，计算项目）的和法人匹配信息列表   工资代码查看页面
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getSalaryItemCheckMapList(Object object){
		List returnList = new ArrayList() ;
		returnList = this.getSalaryItemCheckMapList(object, -1, -1) ;
		return returnList ;
	}
	
	/**
	 * 查找所有工资项目参数（基本项目，输入项目，计算项目）的和法人匹配的信息列表   不分页
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getSalaryCpnyList(String item_no){
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("sys.salarymapping.getSalaryCpnyList", item_no);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList ;
	}

	/**
	 * 查找所有工资项目(基本项目，输入项目，计算项目)的和法人匹配信息的数量
	 * @param object
	 * @return
	 */
	@Override
	public int getSalaryMappingCnt(Object object) {
        int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("sys.salarymapping.getSalaryMappingCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}
	
	/**
	 * 查找所有工资项目(基本项目，输入项目，计算项目)的和法人匹配信息的数量 工资代码查看页面
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int getSalaryItemCheckMapCnt(Object object){
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("sys.salarymapping.getSalaryItemCheckMapCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}

	/**
	 * 分页查找所有工资项目（基本项目，输入项目，计算项目）的和法人匹配信息列表   
	 * @param object
	 * @return
	 */
	@Override
	public List getSalaryMappingList(Object object, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("sys.salarymapping.getSalaryMappingList", object, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("sys.salarymapping.getSalaryMappingList", object);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	/**
	 * 分页查找所有工资项目（基本项目，输入项目，计算项目）的和法人匹配信息列表   工资代码查看页面
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getSalaryItemCheckMapList(Object object, int currentPage, int pageSize){
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("sys.salarymapping.getSalaryItemCheckMapList", object, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("sys.salarymapping.getSalaryItemCheckMapList", object);
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
	public Object getSalaryMappingInfo(Object object) {
	    LinkedHashMap returnObj = new LinkedHashMap() ;
		List returnList = this.getSalaryMappingList(object) ;
		if(returnList.size() > 0){
			returnObj = (LinkedHashMap)returnList.get(0) ;
		}
		return returnObj ;
	}

	@Override
	public int updateAffirmSalaryCodeInfo(Object object) {
		try {
			this.update("sys.salarymapping.updateAffirmSalaryCodeInfo", object) ;
			return 1;
		} catch (SQLException e) {			
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int updateAffirmSalaryInfo(Object object) {
		try {
			this.update("sys.salarymapping.updateAffirmSalaryInfo", object) ;
			return 1;
		} catch (SQLException e) {			
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int addAffirmSalaryCodeInfo(Object obj) throws Exception {
		try {
			LinkedHashMap object = (LinkedHashMap)this.syLanguageDao.saveSyGlobalName(obj);
			this.insert("sys.salarymapping.addAffirmSalaryCodeInfo", object);
			object.put("APPLY_NO", object.get("NO"));
			List list = new ArrayList();
			list = this.attendItemMappingDao.findAfirmorByRelation();
			LinkedHashMap affirmor = new LinkedHashMap();
			if(list != null && list.size()>0){
				affirmor = (LinkedHashMap) list.get(0);
			}
			object.put("CURRENT_AFFIRM_ID",affirmor.get("PERSON_ID"));
			this.sendToLGEPInsert(object);
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
				"http://{serverIp}/LGEP/affirm/viewAffirmSalaryItemInfo?LGEP=LGEP&LANGUAGE=zh&personId=123&APPLY_NO="
						+ paramMap.get("APPLY_NO")
						+ "&CPNY_ID="
						+ paramMap.get("CPNY_ID"));
		lgepMap.put(
				"ABY_URL",
				"http://{serverIp}/LGEP/affirm/viewAffirmSalaryItemInfo?LGEP=LGEP&LANGUAGE=zh&personId="
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
	public int addAffirmSalaryInfo(Object obj) throws Exception {
		try {
			this.insert("sys.salarymapping.addAffirmSalaryInfo", obj);
			LinkedHashMap object = (LinkedHashMap)obj;
			object.put("APPLY_NO", object.get("ITEM_NO"));
			List list = new ArrayList();
			list = this.attendItemMappingDao.findAfirmorByRelation();
			LinkedHashMap affirmor = new LinkedHashMap();
			if(list != null && list.size()>0){
				affirmor = (LinkedHashMap) list.get(0);
			}
			object.put("CURRENT_AFFIRM_ID",affirmor.get("PERSON_ID"));
			this.sendToLGEPInsert(object);
			return 1;
		} catch (SQLException e) {			
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int deleteAffirmSalaryCodeInfo(Object object) throws SQLException {
		try {
			this.delete("sys.salarymapping.deleteAffirmSalaryCodeInfo", object) ;
			return 1;
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}

}
