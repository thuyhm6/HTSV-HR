package com.ait.pa.dao.imp;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.pa.dao.SalaryCanShuDao;
import com.ait.pa.dao.SalaryCanShuDao;
import com.ait.sys.dao.SyLanguageDao;
import com.ait.sys.dao.impl.SyLanguageDaoImpl;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.SqlMapClientSupport;

/**
 * 
 * @author LXJ
 *
 */
@Repository
public class SalaryCanShuDaoImpl extends SqlMapClientSupport implements SalaryCanShuDao {
	
	@Autowired
	private SyLanguageDao syLanguageDao;
	
	/**
	 * 通过派遣地NO获取派遣地信息
	 */
	@SuppressWarnings("unchecked")
	public Object getPaiQianDiInfo(Object obj) {
		LinkedHashMap returnObj = new LinkedHashMap() ;
		
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("pa.salaryCanShu.getPaiQianDiInfo", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(returnList.size() > 0){
			returnObj = (LinkedHashMap)returnList.get(0) ;
		}
		
		return returnObj ;
	}
	
	/**
	 * 通过派遣地NO获取最低工资标准信息非促销员
	 */
	@SuppressWarnings("unchecked")
	public Object getZuiDiGongZiBiaoZhunFeiCuXiaoYuanObjInfo(Object obj) {
		LinkedHashMap returnObj = new LinkedHashMap() ;
		
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("pa.salaryCanShu.getZuiDiGongZiBiaoZhunFeiCuXiaoYuanObjInfo", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(returnList.size() > 0){
			returnObj = (LinkedHashMap)returnList.get(0) ;
		}
		
		return returnObj ;
	}
	
	@SuppressWarnings("unchecked")
	public Object getZuiDiGongZiBiaoZhunCuXiaoYuanObjInfo(Object obj) {
		LinkedHashMap returnObj = new LinkedHashMap() ;
		
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("pa.salaryCanShu.getZuiDiGongZiBiaoZhunCuXiaoYuanObjInfo", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(returnList.size() > 0){
			returnObj = (LinkedHashMap)returnList.get(0) ;
		}
		
		return returnObj ;
	}
	
	/**
	 * 号俸管理
	 * 
	 */
	@SuppressWarnings("unchecked")
	public Object viewHaoFengSetList(Object obj) {
		 
		
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("pa.salaryCanShu.viewHaoFengSetList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		 
		
		return returnList ;
	}
	/**
	 * 号俸级别查询
	 * wangqiang@ait.net.cn
	 * 2014/07/14
	 */
	@SuppressWarnings("unchecked")
	public List viewHaoFengSetListGrade(Object obj) {
		 
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("pa.salaryCanShu.viewHaoFengSetListGrade", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		 
		
		return returnList ;
		
		
		
	}	
		/**
		 * 号俸级号查询
		 *   wangqiang@ait.net.cn
		 * 2014/07/14
		 */
	@SuppressWarnings("unchecked")
	public List viewHaoFengSetListGradeNo(Object obj) {
		 
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("pa.salaryCanShu.viewHaoFengSetListGradeNo", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		 
		
		return returnList ;
	}
	/**
	 * 通过NO获取预提对象管理信息
	 */
	@SuppressWarnings("unchecked")
	public Object getYuTiDuiXiangGuanLiObjInfo(Object obj) {
		LinkedHashMap returnObj = new LinkedHashMap() ;
		
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("pa.salaryCanShu.getYuTiDuiXiangGuanLiObjInfo", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(returnList.size() > 0){
			returnObj = (LinkedHashMap)returnList.get(0) ;
		}
		
		return returnObj ;
	}
	
	/**
	 * 通过派遣地NO获取年终奖预提信息
	 */
	@SuppressWarnings("unchecked")
	public Object getNianZhongJiangYuTiObjInfo(Object obj) {
		LinkedHashMap returnObj = new LinkedHashMap() ;
		
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("pa.salaryCanShu.getNianZhongJiangYuTiObjInfo", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(returnList.size() > 0){
			returnObj = (LinkedHashMap)returnList.get(0) ;
		}
		
		return returnObj ;
	}
	
	/**
	 * ...
	 */
	@SuppressWarnings("unchecked")
	public Object getPaiQianDiJinTieBiaoZhunObjInfo(Object obj) {
		LinkedHashMap returnObj = new LinkedHashMap() ;
		
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("pa.salaryCanShu.getPaiQianDiJinTieBiaoZhunObjInfo", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(returnList.size() > 0){
			returnObj = (LinkedHashMap)returnList.get(0) ;
		}
		return returnObj ;
	}
	/**
	 * ...号俸设置修改查看页面
	 */
	@SuppressWarnings("unchecked")
	public Object getHaoFengSheZhiInfo(Object obj) {
		LinkedHashMap returnObj = new LinkedHashMap() ;
		
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("pa.salaryCanShu.getHaoFengSheZhiInfo", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(returnList.size() > 0){
			returnObj = (LinkedHashMap)returnList.get(0) ;
		}
		return returnObj ;
	}
	/**
	 * 获取派遣地信息
	 */
	@SuppressWarnings("unchecked")
	public List getPaiQianDiList(Object obj) {
		List returnList = new ArrayList() ;
		returnList = this.getPaiQianDiList(obj, -1, -1) ;
		return returnList ;
	}
	
	/**
	 * 获取最低工资标准非促销员
	 */
	@SuppressWarnings("unchecked")
	public List getZuiDiGongZiBiaoZhunList(Object obj) {
		List returnList = new ArrayList() ;
		returnList = this.getZuiDiGongZiBiaoZhunList(obj, -1, -1) ;
		return returnList ;
	}

	/**
	 * 获取最低工资标准促销员
	 */
	@SuppressWarnings("unchecked")
	public List getZuiDiGongZiBiaoZhunCuXiaoYuanList(Object obj) {
		List returnList = new ArrayList() ;
		returnList = this.getZuiDiGongZiBiaoZhunCuXiaoYuanList(obj, -1, -1) ;
		return returnList ;
	}
	
	@SuppressWarnings("unchecked")
	public List getPaiQianDiTempList(Object obj) {
		List returnList = new ArrayList() ;
		returnList = this.getPaiQianDiTempList(obj, -1, -1) ;
		return returnList ;
	}
	
	@SuppressWarnings("unchecked")
	public List getZuiDiGongZiBiaoZhunFeiCuXiaoYuanTempList(Object obj) {
		List returnList = new ArrayList() ;
		returnList = this.getZuiDiGongZiBiaoZhunFeiCuXiaoYuanTempList(obj, -1, -1) ;
		return returnList ;
	}
	
	@SuppressWarnings("unchecked")
	public List getZuiDiGongZiBiaoZhunCuXiaoYuanTempList(Object obj) {
		List returnList = new ArrayList() ;
		returnList = this.getZuiDiGongZiBiaoZhunCuXiaoYuanTempList(obj, -1, -1) ;
		return returnList ;
	}
	
	@SuppressWarnings("unchecked")
	public List getPaiQianDiJinTieBiaoZhunTempList(Object obj) {
		List returnList = new ArrayList() ;
		returnList = this.getPaiQianDiJinTieBiaoZhunTempList(obj, -1, -1) ;
		return returnList ;
	}
	
	/**
	 * 获取预提对象管理信息
	 */
	@SuppressWarnings("unchecked")
	public List getYuTiDuiXiangGuanList(Object obj) {
		List returnList = new ArrayList() ;
		returnList = this.getYuTiDuiXiangGuanList(obj, -1, -1) ;
		return returnList ;
	}
	
	@SuppressWarnings("unchecked")
	public List getDaQuNamesList(Object obj) {
		List returnList = new ArrayList() ;
		returnList = this.getDaQuNamesList(obj, -1, -1) ;
		return returnList ;
	}
	
	/**
	 * 获取年终奖预提信息
	 */
	@SuppressWarnings("unchecked")
	public List getNianZhongJiangJiTiList(Object obj) {
		List returnList = new ArrayList() ;
		returnList = this.getNianZhongJiangJiTiList(obj, -1, -1) ;
		return returnList ;
	}
	
	/**
	 * 获取年终奖预提计算信息
	 */
	@SuppressWarnings("unchecked")
	public List getNianZhongJiangJiTiJiSuanList(Object obj) {
		List returnList = new ArrayList() ;
		returnList = this.getNianZhongJiangJiTiJiSuanList(obj, -1, -1) ;
		return returnList ;
	}
	/**
	 * 获取正规预提计算信息
	 */
	@SuppressWarnings("unchecked")
	public List viewZhengGuiYuTiJiSuanList(Object obj) {
		List returnList = new ArrayList() ;
		returnList = this.viewZhengGuiYuTiJiSuanList(obj, -1, -1) ;
		return returnList ;
	}
	
	/**
	 * ...
	 */
	@SuppressWarnings("unchecked")
	public List getPaiQianDiJtBzList(Object obj) {
		List returnList = new ArrayList() ;
		returnList = this.getPaiQianDiJtBzList(obj, -1, -1) ;
		return returnList ;
	}
	/**
	 * ...
	 */
	@SuppressWarnings("unchecked")
	public List viewHaoFengSetListfenye(Object obj) {
		List returnList = new ArrayList() ;
		returnList = this.viewHaoFengSetListfenye(obj, -1, -1) ;
		return returnList ;
	}
	 
	/**
	 *...
	 */
	@Override
	public int getPaiQianDiCnt(Object obj) {
		int returnInt = 0 ;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.salaryCanShu.getPaiQianDiCnt", obj)), Integer.class) ;
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}
	
	/**
	 * 最低工资标准数量--非促销员
	 */
	@Override
	public int geZuiDiGongZiBiaoZhunListCnt(Object obj) {
		int returnInt = 0 ;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.salaryCanShu.geZuiDiGongZiBiaoZhunListCnt", obj)), Integer.class) ;
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}
	
	@Override
	public int getZuiDiGongZiBiaoZhunCuXiaoYuanListCnt(Object obj) {
		int returnInt = 0 ;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.salaryCanShu.getZuiDiGongZiBiaoZhunCuXiaoYuanListCnt", obj)), Integer.class) ;
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}
	
	@Override
	public int getPaiQianDiTempCnt(Object obj) {
		int returnInt = 0 ;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.salaryCanShu.getPaiQianDiTempCnt", obj)), Integer.class) ;
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}
	
	@Override
	public int getZuiDiGongZiBiaoZhunFeiCuXiaoYuanTempListCnt(Object obj) {
		int returnInt = 0 ;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.salaryCanShu.getZuiDiGongZiBiaoZhunFeiCuXiaoYuanTempListCnt", obj)), Integer.class) ;
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}
	
	@Override
	public int getZuiDiGongZiBiaoZhunCuXiaoYuanTempListCnt(Object obj) {
		int returnInt = 0 ;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.salaryCanShu.getZuiDiGongZiBiaoZhunCuXiaoYuanTempListCnt", obj)), Integer.class) ;
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}
	
	
	@Override
	public int getPaiQianDiJinTieBiaoZhunTempListCnt(Object obj) {
		int returnInt = 0 ;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.salaryCanShu.getPaiQianDiJinTieBiaoZhunTempListCnt", obj)), Integer.class) ;
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}
	
	@Override
	public int getPaiQianDiTempErrorCnt(Object obj) {
		int returnInt = 0 ;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.salaryCanShu.getPaiQianDiTempErrorCnt", obj)), Integer.class) ;
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}
	
	@Override
	public int getZuiDiGongZiBiaoZhunFeiCuXiaoYuanTempErrorCnt(Object obj) {
		int returnInt = 0 ;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.salaryCanShu.getZuiDiGongZiBiaoZhunFeiCuXiaoYuanTempErrorCnt", obj)), Integer.class) ;
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}
	
	@Override
	public int getZuiDiGongZiBiaoZhunCuXiaoYuanTempErrorCnt(Object obj) {
		int returnInt = 0 ;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.salaryCanShu.getZuiDiGongZiBiaoZhunCuXiaoYuanTempErrorCnt", obj)), Integer.class) ;
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}
	
	@Override
	public int getPaiQianDiJinTieBiaoZhunTempErrorCnt(Object obj) {
		int returnInt = 0 ;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.salaryCanShu.getPaiQianDiJinTieBiaoZhunTempErrorCnt", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}
	
	/**
	 * 获取预提对象管理的数量
	 */
	@Override
	public int getYuTiDuiXiangGuanListCnt(Object obj) {
		int returnInt = 0 ;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.salaryCanShu.getYuTiDuiXiangGuanListCnt", obj)), Integer.class) ;
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}
	
	/**
	 *  年终奖预提数量
	 */
	@Override
	public int getNianZhongJiangJiTiListCntCnt(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.salaryCanShu.getNianZhongJiangJiTiListCnt", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	 *  年终奖预提计算数量
	 */
	@Override
	public int getNianZhongJiangJiTiJiSuanListCnt(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.salaryCanShu.getNianZhongJiangJiTiJiSuanListCnt", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}

	/**
	 *  正规预提计算数量
	 */
	@Override
	public int viewZhengGuiYuTiJiSuanListCn(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.salaryCanShu.viewZhengGuiYuTiJiSuanListCn", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	/**
	 *...
	 */
	@Override
	public int getPaiQianDiJtBzCnt(Object obj) {
		int returnInt = 0 ;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.salaryCanShu.getPaiQianDiCnt", obj)), Integer.class) ;
			
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.salaryCanShu.getPaiQianDiJtBzCnt", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}
	/**
	 *...
	 */
	@Override
	public int getHaoFengSheZhiListCnt(Object obj) {
		int returnInt = 0 ;
		
		try {
		 
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.salaryCanShu.getHaoFengSheZhiListCnt", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}
	
	/**
	 * 获取派遣地的
	 */
	@SuppressWarnings("unchecked")
	public List getPaiQianDiList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("pa.salaryCanShu.getPaiQianDiList", obj, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("pa.salaryCanShu.getPaiQianDiList", obj);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	/**
	 * 获取最低工资标准非促销员
	 */
	@SuppressWarnings("unchecked")
	public List getZuiDiGongZiBiaoZhunList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("pa.salaryCanShu.getZuiDiGongZiBiaoZhunList", obj, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("pa.salaryCanShu.getZuiDiGongZiBiaoZhunList", obj);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	/**
	 * 获取最低工资标准促销员
	 */
	@SuppressWarnings("unchecked")
	public List getZuiDiGongZiBiaoZhunCuXiaoYuanList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("pa.salaryCanShu.getZuiDiGongZiBiaoZhunCuXiaoYuanList", obj, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("pa.salaryCanShu.getZuiDiGongZiBiaoZhunCuXiaoYuanList", obj);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	@SuppressWarnings("unchecked")
	public List getPaiQianDiTempList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("pa.salaryCanShu.getPaiQianDiTempList", obj, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("pa.salaryCanShu.getPaiQianDiTempList", obj);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	@SuppressWarnings("unchecked")
	public List getZuiDiGongZiBiaoZhunFeiCuXiaoYuanTempList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("pa.salaryCanShu.getZuiDiGongZiBiaoZhunFeiCuXiaoYuanTempList", obj, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("pa.salaryCanShu.getZuiDiGongZiBiaoZhunFeiCuXiaoYuanTempList", obj);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	@SuppressWarnings("unchecked")
	public List getZuiDiGongZiBiaoZhunCuXiaoYuanTempList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("pa.salaryCanShu.getZuiDiGongZiBiaoZhunCuXiaoYuanTempList", obj, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("pa.salaryCanShu.getZuiDiGongZiBiaoZhunCuXiaoYuanTempList", obj);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	@SuppressWarnings("unchecked")
	public List getPaiQianDiJinTieBiaoZhunTempList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("pa.salaryCanShu.getPaiQianDiJinTieBiaoZhunTempList", obj, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("pa.salaryCanShu.getPaiQianDiJinTieBiaoZhunTempList", obj);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	/**
	 * 获取预提对象管理信息
	 */
	@SuppressWarnings("unchecked")
	public List getYuTiDuiXiangGuanList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("pa.salaryCanShu.getYuTiDuiXiangGuanList", obj, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("pa.salaryCanShu.getYuTiDuiXiangGuanList", obj);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	@SuppressWarnings("unchecked")
	public List getDaQuNamesList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("pa.salaryCanShu.getDaQuNamesList", obj, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("pa.salaryCanShu.getDaQuNamesList", obj);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	/**
	 * 获取年终奖计提信息
	 */
	@SuppressWarnings("unchecked")
	public List getNianZhongJiangJiTiList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("pa.salaryCanShu.getNianZhongJiangJiTiList", obj, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("pa.salaryCanShu.getNianZhongJiangJiTiList", obj);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 获取年终奖计提计算信息
	 */
	@SuppressWarnings("unchecked")
	public List getNianZhongJiangJiTiJiSuanList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("pa.salaryCanShu.getNianZhongJiangJiTiJiSuanList", obj, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("pa.salaryCanShu.getNianZhongJiangJiTiJiSuanList", obj);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}

	/**
	 * 获取正规预提计算信息
	 */
	@SuppressWarnings("unchecked")
	public List viewZhengGuiYuTiJiSuanList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("pa.salaryCanShu.viewZhengGuiYuTiJiSuanList", obj, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("pa.salaryCanShu.viewZhengGuiYuTiJiSuanList", obj);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	/**
	 * ...
	 */
	@SuppressWarnings("unchecked")
	public List getPaiQianDiJtBzList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("pa.salaryCanShu.getPaiQianDiJtBzList", obj, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("pa.salaryCanShu.getPaiQianDiJtBzList", obj);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	/**
	 * ...
	 */
	@SuppressWarnings("unchecked")
	public List viewHaoFengSetListfenye(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("pa.salaryCanShu.viewHaoFengSetList", obj, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("pa.salaryCanShu.viewHaoFengSetList", obj);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	/**
	 * ...
	 */
	@SuppressWarnings("unchecked")
	public List viewHaoFengSetListfenyeExcel(Object obj) {
		List returnList = new ArrayList() ;
		try {
			 
				returnList = this.queryForList("pa.salaryCanShu.viewHaoFengSetListExcel", obj);
			 
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	/**
	 * ...
	 */
	@SuppressWarnings("unchecked")
	public List viewHaoFengSetList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("pa.salaryCanShu.viewHaoFengSetList", obj, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("pa.salaryCanShu.viewHaoFengSetList", obj);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	/**
	 * 保存派遣地的信息
	 */
	@SuppressWarnings("unchecked")
	public void addPaiQianDiGuanLiInfo(Object obj)throws Exception {
		LinkedHashMap object = (LinkedHashMap)this.syLanguageDao.saveSyGlobalName(obj);
		this.insert("pa.salaryCanShu.addPaiQianDiGuanLiInfo", object) ;
	}
	
	/**
	 * 保存最低工资标准的非促销员
	 */
	@SuppressWarnings("unchecked")
	public void addZuiDiGongZiBiaoZhunFeiCuXiaoYuanInfo(Object obj)throws Exception {
		LinkedHashMap object = (LinkedHashMap)this.syLanguageDao.saveSyGlobalName(obj);
		this.insert("pa.salaryCanShu.addZuiDiGongZiBiaoZhunFeiCuXiaoYuanInfo", object) ;
	}
	
	@SuppressWarnings("unchecked")
	public void addZuiDiGongZiBiaoZhunCuXiaoYuanInfo(Object obj)throws Exception {
		LinkedHashMap object = (LinkedHashMap)this.syLanguageDao.saveSyGlobalName(obj);
		this.insert("pa.salaryCanShu.addZuiDiGongZiBiaoZhunCuXiaoYuanInfo", object) ;
	}
	
	/**
	 * 保存预提对象管理的信息
	 */
	@SuppressWarnings("unchecked")
	public void addYuTiDuiXiangGuanLiInfo(Object obj)throws Exception {
		LinkedHashMap object = (LinkedHashMap)this.syLanguageDao.saveSyGlobalName(obj);
		this.insert("pa.salaryCanShu.addYuTiDuiXiangGuanLiInfo", object) ;
	}
	
	/**
	 * 保存年终奖预提的操作
	 */
	@SuppressWarnings("unchecked")
	public void addNianZhongJiangYuTiInfo(Object obj)throws Exception {
		this.insert("pa.salaryCanShu.addNianZhongJiangYuTiInfo", obj) ;
	}
	
	/**
	 * ...
	 */
	@SuppressWarnings("unchecked")
	public void addPaiQianDiJinTieBiaoZhunInfo(Object obj)throws Exception {
		LinkedHashMap object = (LinkedHashMap)this.syLanguageDao.saveSyGlobalName(obj);
		this.insert("pa.salaryCanShu.addPaiQianDiJinTieBiaoZhunInfo", object) ;
	}
	/**
	 * ...
	 */
	@SuppressWarnings("unchecked")
	public int addHaoFengGuanLiInfo(Object obj)throws Exception {
		int count=0;
		try{
		count=NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.salaryCanShu.panDuanShiFouChongFu", obj)), Integer.class) ; ;
		}catch(Exception e){
			e.printStackTrace();
		}
		if(count==0){
			this.insert("pa.salaryCanShu.addHaoFengGuanLiInfo", obj) ;
		}
		return count;
	}
	
	
	/**
	 * 检测时候有设定好的关系
	 */
	@SuppressWarnings("unchecked")
	public int getPaiQianDiGuanLiInfo(Object obj)throws Exception {
		int result = -1;
		result = (Integer) this.queryForObject("pa.salaryCanShu.getPaiQianDiGuanLiInfo", obj) ;
		return result;
	}
	
	/**
	 * 检测最低工资标准菲促销员是否设定好的关系
	 */
	@SuppressWarnings("unchecked")
	public int getZuiDiGongZiBiaoZhunFeiCuXiaoYuanInfo(Object obj)throws Exception {
		int result = -1;
		result = (Integer) this.queryForObject("pa.salaryCanShu.getZuiDiGongZiBiaoZhunFeiCuXiaoYuanInfo", obj) ;
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public int getZuiDiGongZiBiaoZhunCuXiaoYuanInfo(Object obj)throws Exception {
		int result = -1;
		result = (Integer) this.queryForObject("pa.salaryCanShu.getZuiDiGongZiBiaoZhunCuXiaoYuanInfo", obj) ;
		return result;
	}
	
	/**
	 * 检测时候有设定好的关系
	 */
	@SuppressWarnings("unchecked")
	public int getYuTiDuiXiangGuanLiInfo(Object obj)throws Exception {
		int result = -1;
		result = (Integer) this.queryForObject("pa.salaryCanShu.getYuTiDuiXiangGuanLiInfo", obj) ;
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public int getYuTiDuiXiangGuanLiInfoLgech(Object obj)throws Exception {
		int result = -1;
		result = (Integer) this.queryForObject("pa.salaryCanShu.getYuTiDuiXiangGuanLiInfoLgech", obj) ;
		return result;
	}
	
	/**
	 * 年终奖预提检测是否有重复的关系设置
	 */
	@SuppressWarnings("unchecked")
	public int getNianZhongJiangYuTiInfo(Object obj)throws Exception {
		int result = -1;
		result = (Integer) this.queryForObject("pa.salaryCanShu.getNianZhongJiangYuTiInfo", obj) ;
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public int getPaiQianDiJinTieBiaoZhunInfo(Object obj)throws Exception {
		int result = -1;
		result = (Integer) this.queryForObject("pa.salaryCanShu.getPaiQianDiJinTieBiaoZhunInfo", obj) ;
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public int checkPaiQianDiYesOrNoThisInfo(Object obj)throws Exception {
		int result = -1;
		result = (Integer) this.queryForObject("pa.salaryCanShu.checkPaiQianDiYesOrNoThisInfo", obj) ;
		return result;
	}
	
	/**
	 * 检测时候有设定好的关系
	 */
	@SuppressWarnings("unchecked")
	public int getPaiQianDiGuanLiInfoUpdate(Object obj)throws Exception {
		int result = -1;
		result = (Integer) this.queryForObject("pa.salaryCanShu.getPaiQianDiGuanLiInfoUpdate", obj) ;
		return result;
	}
	
	/**
	 * 检测时候有设定好的关系
	 */
	@SuppressWarnings("unchecked")
	public int getZuiDiGongZiBiaoZhunFeiCuXiaoYuanInfoUpdate(Object obj)throws Exception {
		int result = -1;
		result = (Integer) this.queryForObject("pa.salaryCanShu.getZuiDiGongZiBiaoZhunFeiCuXiaoYuanInfoUpdate", obj) ;
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public int getZuiDiGongZiBiaoZhunCuXiaoYuanInfoUpdate(Object obj)throws Exception {
		int result = -1;
		result = (Integer) this.queryForObject("pa.salaryCanShu.getZuiDiGongZiBiaoZhunCuXiaoYuanInfoUpdate", obj) ;
		return result;
	}
	
	/**
	 * 检测时候有设定好的关系
	 */
	@SuppressWarnings("unchecked")
	public int getYuTiDuiXiangGuanLiInfoUpdate(Object obj)throws Exception {
		int result = -1;
		result = (Integer) this.queryForObject("pa.salaryCanShu.getYuTiDuiXiangGuanLiInfoUpdate", obj) ;
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public int getYuTiDuiXiangGuanLiInfoUpdateLgech(Object obj)throws Exception {
		int result = -1;
		result = (Integer) this.queryForObject("pa.salaryCanShu.getYuTiDuiXiangGuanLiInfoUpdateLgech", obj) ;
		return result;
	}
	
	/**
	 * 检测时候有设定好的关系,年终奖预提
	 */
	@SuppressWarnings("unchecked")
	public int getNianZhongJiangYuTiInfoUpdate(Object obj)throws Exception {
		int result = -1;
		result = (Integer) this.queryForObject("pa.salaryCanShu.getNianZhongJiangYuTiInfoUpdate", obj) ;
		return result;
	}
	/**
	 * 检测时候有设定好的关系
	 */
	@SuppressWarnings("unchecked")
	public int getPaiQianDiJinTieBiaoZhunInfoUpdate(Object obj)throws Exception {
		int result = -1;
		result = (Integer) this.queryForObject("pa.salaryCanShu.getPaiQianDiJinTieBiaoZhunInfoUpdate", obj) ;
		return result;
	}
	
	/**
	 * 
	 * 号俸管理检查日月别区分
	 * 
	 */
	@SuppressWarnings("unchecked")
	public int getHaoFengDayMonAjax(Object obj)throws Exception{
		int result = 0;
		result = (Integer)this.queryForObject("pa.salaryCanShu.getHaoFengDayMonAjax",obj);
		return result;
	}
	/**
	 * 获取派遣地信息
	 */
	@SuppressWarnings("unchecked")
	public String getPaiQianDiGuanLiInfoUpdatePqd(Object obj)throws Exception {
		String result = "PPP";
		result = (String) this.queryForObject("pa.salaryCanShu.getPaiQianDiGuanLiInfoUpdatePqd", obj) ;
		return result;
	}
	
	/**
	 * 保存操作
	 */
	@SuppressWarnings("unchecked")
	public void updatePaiQianDiGuanLiInfo(Object obj)throws Exception {
		this.update("pa.salaryCanShu.updatePaiQianDiGuanLiInfo", obj) ;
	}
	
	/**
	 * 保存操作
	 */
	@SuppressWarnings("unchecked")
	public void updateZuiDiGongZiBiaoZhunFeiCuXiaoYuanInfo(Object obj)throws Exception {
		this.update("pa.salaryCanShu.updateZuiDiGongZiBiaoZhunFeiCuXiaoYuanInfo", obj) ;
	}
	
	@SuppressWarnings("unchecked")
	public void updateZuiDiGongZiBiaoZhunCuXiaoYuanInfo(Object obj)throws Exception {
		this.update("pa.salaryCanShu.updateZuiDiGongZiBiaoZhunCuXiaoYuanInfo", obj) ;
	}
	
	@SuppressWarnings("unchecked")
	public void updatePaiQianDiGuanLiTempInfo(List list)throws Exception {
		this.startTransaction();
		int size = list.size();
		LinkedHashMap map = new LinkedHashMap();
		for (int i = 0; i < size; i++){
			map.put("NO", list.get(i));
			map.remove("NO");
			map.put("NO", list.get(i));
			//设置为E
			this.update("pa.salaryCanShu.updatePaiQianDiGuanLiTempInfo", map) ;
		}
		//设置为正常的N
		this.update("pa.salaryCanShu.updatePaiQianDiGuanLiTempNoErrorInfo") ;
		this.commitTransation();
	}
	
	@SuppressWarnings("unchecked")
	public void updatePaiQianDiGuanLiTempInfo(List list, Map map){
		try {
			this.startTransaction();
			int size = list.size();
			LinkedHashMap paramMap = new LinkedHashMap();
			for (int i = 0; i < size; i++){
				paramMap = (LinkedHashMap) list.get(i);
				map.put("NO", paramMap.get("NO"));
				//设置为E
				this.update("pa.salaryCanShu.updatePaiQianDiGuanLiTempInfo", map) ;
				//把检测出来的错误保存在表中显示在页面上，根据NO可以获取到法人、城市等级、省份、城市名称、地区名称
				//1、把检查结果清空
				this.update("pa.salaryCanShu.updatePaiQianDiGuanLiTempJianChaJieGuoQingKongInfo", map) ;
				//2、生成法人的检查结果
				this.update("pa.salaryCanShu.updatePaiQianDiGuanLiTempFaRenInfo", map) ;
				//3、生成城市等级的检查结果
				this.update("pa.salaryCanShu.updatePaiQianDiGuanLiTempChengShiDengJiInfo", map) ;
				//4、生成省份的检查结果
				this.update("pa.salaryCanShu.updatePaiQianDiGuanLiTempShengFenInfo", map) ;
				//5、生成城市名称的检查结果
				this.update("pa.salaryCanShu.updatePaiQianDiGuanLiTempChengShiMIngChengInfo", map) ;
				//6、生成地区名称的检查结果
				this.update("pa.salaryCanShu.updatePaiQianDiGuanLiTempDiQuMingChengInfo", map) ;
				
			}
			//设置为正常的N
			this.update("pa.salaryCanShu.updatePaiQianDiGuanLiTempNoErrorInfo", map) ;
			this.commitTransation();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				this.endTransation();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public void updatePaiQianDiJinTieBiaoZhunTempInfo(List list, Map map){
		try {
			this.startTransaction();
			int size = list.size();
			LinkedHashMap paramMap = new LinkedHashMap();
			for (int i = 0; i < size; i++){
				paramMap = (LinkedHashMap) list.get(i);
				map.put("NO", paramMap.get("NO"));
				//设置为E
				this.update("pa.salaryCanShu.updatePaiQianDiJinTieBiaoZhunTempInfo", map) ;
				//把检测出来的错误保存在表中显示在页面上，根据NO可以获取到法人、城市等级、省份、城市名称、地区名称
				//1、把检查结果清空
				this.update("pa.salaryCanShu.updatePaiQianDiGuanLiTempJianChaJieGuoQingKongJinTieBiaoZhunInfo", map) ;
				//2、生成法人的检查结果
				this.update("pa.salaryCanShu.updatePaiQianDiGuanLiTempFaRenJinTieBiaoZhunInfo", map) ;
				//3、职责验证结果
				this.update("pa.salaryCanShu.updatePaiQianDiGuanLiTempZhiZeJinTieBiaoZhunInfo", map) ;
				//4、生成城市等级的检查结果
				this.update("pa.salaryCanShu.updatePaiQianDiGuanLiTempChengShiDengJiJinTieBiaoZhunInfo", map) ;
				//5、生成地区名称的检查结果
				this.update("pa.salaryCanShu.updatePaiQianDiGuanLiTempDiQuMingChengJinTieBiaoZhunInfo", map) ;
				//6、数值验证结果
				this.update("pa.salaryCanShu.updatePaiQianDiGuanLiTempShuZhiJinTieBiaoZhunInfo", map) ;
				
				
			}
			//设置为正常的N
			this.update("pa.salaryCanShu.updatePaiQianDiJinTieBiaoZhunTempNoErrorInfo", map) ;
			this.commitTransation();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				this.endTransation();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public void deletePaiQianDiTempChongFuInfo(Object obj)throws Exception {
		this.delete("pa.salaryCanShu.deletePaiQianDiTempChongFuInfo", obj) ;
	}
	
	@SuppressWarnings("unchecked")
	public void insertPaiQianDiInfoFromTempInfo(Object obj) {
		try {
			this.startTransaction();
			//删掉和正式中冲突的数据
			this.delete("pa.salaryCanShu.deletePaiQianDiInfoWithTempInfo", obj) ;
			//导入到正是表中
			this.insert("pa.salaryCanShu.insertPaiQianDiInfoFromTempInfo", obj) ;
			//删除正确的数据 
			this.delete("pa.salaryCanShu.deletePaiQianDiInfoTempOkInfo", obj) ;
			this.commitTransation();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				this.endTransation();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 保存操作--预提对象管理
	 */
	@SuppressWarnings("unchecked")
	public void updateYuTiDuiXiangGuanLiInfo(Object obj)throws Exception {
		this.update("pa.salaryCanShu.updateYuTiDuiXiangGuanLiInfo", obj) ;
	}
	
	/**
	 * 保存年终奖预提的操作
	 */
	@SuppressWarnings("unchecked")
	public void updateNianZhongJiangYuTiInfo(Object obj)throws Exception {
		this.update("pa.salaryCanShu.updateNianZhongJiangYuTiInfo", obj) ;
	}
	
	@SuppressWarnings("unchecked")
	public void updateZuiDiGongZiBiaoZhunFeiCuXiaoYuanExcelResultToNos(Object obj)throws Exception {
		this.update("pa.salaryCanShu.updateZuiDiGongZiBiaoZhunFeiCuXiaoYuanExcelResultToNos", obj) ;
	}
	
	@SuppressWarnings("unchecked")
	public void updatePaiQianDiGuanLiExcelResultToNos(Object obj)throws Exception {
		this.update("pa.salaryCanShu.updatePaiQianDiGuanLiExcelResultToNos", obj) ;
	}
	
	@SuppressWarnings("unchecked")
	public void updatePaiQianDiJinTieBiaoZhunExcelResultToNos(Object obj)throws Exception {
		this.update("pa.salaryCanShu.updatePaiQianDiJinTieBiaoZhunExcelResultToNos", obj) ;
	}
	
	@SuppressWarnings("unchecked")
	public void updateZuiDiGongZiBiaoZhunCuXiaoYuanExcelResultToNos(Object obj)throws Exception {
		this.update("pa.salaryCanShu.updateZuiDiGongZiBiaoZhunCuXiaoYuanExcelResultToNos", obj) ;
	}
	
	/**
	 * 年终奖预提计算的存储
	 */
	@SuppressWarnings("unchecked")
	public String callNianZhongJiangYuTiJiSuanProduce(Map paramMap)throws Exception {
		String result="";
		this.insert("pa.salaryCanShu.callNianZhongJiangYuTiJiSuanProduce", paramMap);	
		result =  ObjectUtils.toString(paramMap.get("MESSAGE")) ;
		return result;
	}
	/**
	 * 正规预提计算的存储
	 */
	@SuppressWarnings("unchecked")
	public String callZhengGuiYuTiJiSuanProduce(Map paramMap)throws Exception {
		String result="";
		this.insert("pa.salaryCanShu.callZhengGuiYuTiJiSuanProduce", paramMap);	
		result =  "Calculate Success" ;
		return result;
	}
	
	/**
	 * 保存操作
	 */
	@SuppressWarnings("unchecked")
	public void updatePaiQianDiJinTieBiaoZhunInfo(Object obj)throws Exception {
		this.update("pa.salaryCanShu.updatePaiQianDiJinTieBiaoZhunInfo", obj) ;
	}
	/**
	 * 号俸修改保存操作
	 */
	@SuppressWarnings("unchecked")
	public void updateHaoFengSheZhiInfo(Object obj) throws Exception {
	 
			this.updateForList("pa.salaryCanShu.updateHaoFengSheZhiInfo", (List)obj) ;
		 
	}
	
	/**
	 * 删除派遣地
	 */
	@SuppressWarnings("unchecked")
	public void deletePaiQianDiGuanLiInfo(Object obj) throws Exception {
		this.delete("pa.salaryCanShu.deletePaiQianDiGuanLiInfo", obj) ;
	}
	
	/**
	 * 删除预提对象管理信息
	 */
	@SuppressWarnings("unchecked")
	public void deleteYuTiDuiXiangGuanLiInfo(Object obj) throws Exception {
		this.delete("pa.salaryCanShu.deleteYuTiDuiXiangGuanLiInfo", obj) ;
	}
	
	/**
	 * 删除派遣津贴
	 */
	@SuppressWarnings("unchecked")
	public void deletePaiQianDiJinTieBiaoZhunInfo(Object obj) throws Exception {
		this.delete("pa.salaryCanShu.deletePaiQianDiJinTieBiaoZhunInfo", obj) ;
	}
	/**
	 * 删除 号俸设置页面
	 */
	@SuppressWarnings("unchecked")
	public void deleteHaoFengSheZhiInfo(Object obj) throws Exception {
		this.delete("pa.salaryCanShu.deleteHaoFengSheZhiInfo", obj) ;
	}
	
	
	/**
	 * 区间参数查询 带分页(get CycleParam List)
	 * @param Object
	 * @return List
	 */
	@Override
	public List getCycleParamList(Map obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("ar.cycle.getCycleParamList", obj, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("ar.cycle.getCycleParamList", obj);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	@Override
	public List  getErrorPqdNos() {
		List  returnList = new ArrayList<Long>() ;
		try {
			returnList = this.queryForList("pa.salaryCanShu.getErrorPqdNos");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList ;
	}
	
	@Override
	public List  getErrorPqdNos(Map map) {
		List  returnList = new ArrayList<Long>() ;
		try {
			returnList = this.queryForList("pa.salaryCanShu.getErrorPqdNos", map);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList ;
	}
	
	@Override
	public List  getErrorPqdJinTieBiaoZhunNos(Map map) {
		List  returnList = new ArrayList<Long>() ;
		try {
			returnList = this.queryForList("pa.salaryCanShu.getErrorPqdJinTieBiaoZhunNos", map);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 区间参数查询全部
	 * @param Object
	 * @return
	 */
	@Override
	public List getCycleParamList(Map obj) {
		// TODO Auto-generated method stub
		List returnList = new ArrayList() ;
		
		returnList = this.getCycleParamList(obj, -1, -1) ;
		
		return returnList ;
	}
	
	/**
	 * 区间参数统计记录总数
	 * @param Object
	 * @return
	 */
	@Override
	public int getCycleParamCnt(Map obj) {
		// TODO Auto-generated method stub
		int returnInt = 0 ;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ar.cycle.getCycleParamCnt", obj)), Integer.class) ;
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}
	
	/**
	 * 添加区间参数(add CycleParam Info)
	 * @param LinkedHashMap
	 * @return
	 */
	@Override
	public void addCycleParamInfo(LinkedHashMap obj) throws Exception {
		
		this.insert("ar.cycle.addCycleParamInfo", obj) ;
		
		if(obj.get("postNos")!=null&&((String [])obj.get("postNos")).length>0){
			
			for(String emptype :((String [])obj.get("postNos")) ){
				Map tempP=new LinkedHashMap();
				tempP.put("EMP_TYPE_CODE", emptype);
				tempP.put("STAT_NO", obj.get("STAT_NO"));
				tempP.put("CPNY_ID", obj.get("interCpnyID"));
				tempP.put("CREATED_BY", obj.get("CREATED_BY"));
				this.insert("ar.cycle.addStatisticEmpTypeCode",tempP);
			}
		}
		
	}
	
	/**
	 * 更新信息
	 * @param List
	 * @return
	 */
	@Override
	public void updateCycleParamInfo(LinkedHashMap obj)throws Exception {
		
		this.update("ar.cycle.updateCycleParamInfo", obj);
		
		this.delete("ar.cycle.deleteStatisticEmpTypeCodeByNo", obj);
		
		if(obj.get("postNos")!=null&&((String [])obj.get("postNos")).length>0){
			
			for(String emptype :((String [])obj.get("postNos")) ){
				Map tempP=new LinkedHashMap();
				tempP.put("EMP_TYPE_CODE", emptype);
				tempP.put("STAT_NO", obj.get("STAT_NO"));
				tempP.put("CPNY_ID", obj.get("interCpnyID"));
				tempP.put("UPDATED_BY", obj.get("UPDATED_BY"));
				this.insert("ar.cycle.addStatisticEmpTypeCode",tempP);
			}
		}
		
	}
	
	/**
	 * 删除区间参数(delete CycleParam Info)
	 * @param List
	 * @return
	 */
	@Override
	public void deleteCycleParamInfo(LinkedHashMap obj) throws Exception {
		
		this.delete("ar.cycle.deleteCycleParamInfo", obj) ;
		
		this.delete("ar.cycle.deleteStatisticEmpTypeCode", obj) ;
		
	}

	@Override
	public Object getCycleParam(Map obj) {
		// TODO Auto-generated method stub
		LinkedHashMap returnObj = new LinkedHashMap() ;
		
		List returnList = this.getCycleParamList(obj) ;
		if(returnList.size() > 0){
			returnObj = (LinkedHashMap)returnList.get(0) ;
		}
		
		return returnObj ;
	}

	/**
	 * 检查唯一性(check CycleInfo Unique)
	 * @param LinkedHashMap
	 * @return int
	 */
	@Override
	public int checkCycleInfoUnique(LinkedHashMap paramMap) {
		// TODO Auto-generated method stub
		int returnInt = 0 ;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ar.cycle.checkCycleUnique", paramMap)), Integer.class) ;
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}

	/**
	 * 删除检查(check Cycle For Delete)
	 * @param Map
	 * @return int
	 */
	@Override
	public int checkCycleForDelete(Map paramMap) {
		// TODO Auto-generated method stub
		int returnInt = 0;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ar.cycle.checkCycleForDelete", paramMap)), Integer.class) ;
			
		} catch (SQLException e) {			
			e.printStackTrace();
			returnInt = 1;
		}
		
		return returnInt ;
	}
	
	/**
	 * 取得员工类别(get EmpTypeCode List)
	 * @param request
	 * @return List
	 */
	@Override
	public List getEmpTypeCodeList(Map obj) {
		
		List returnList = new ArrayList() ;
		
		try {
			
			returnList = this.queryForList("ar.cycle.getEmpTypeCodeList", obj);
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 取得员工类别(get EmpTypeCode List)
	 * @param request
	 * @return List
	 */
	@Override
	public List getStatisticList(Map obj) {
		
		List returnList = new ArrayList() ;
		
		try {
			
			returnList = this.queryForList("ar.cycle.getStatisticList", obj);
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	
	/**
	 * 取得员工类别(get EmpTypeCode List)
	 * @param request
	 * @return List
	 */
	@Override
	public List getKeeperEmpTypeCodeList(Map obj) {
		
		List returnList = new ArrayList() ;
		
		try {
			
			returnList = this.queryForList("ar.cycle.getKeeperEmpTypeCodeList", obj);
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 取得员工类别(get EmpTypeCode List)
	 * @param request
	 * @return List
	 */
	@Override
	public List getPaSupervisorEmpTypeCodeList(Map obj) {
		
		List returnList = new ArrayList() ;
		
		try {
			
			returnList = this.queryForList("ar.cycle.getPaSupervisorEmpTypeCodeList", obj);
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}

	/**
	 * 获取派遣地No
	 */
	@Override
	public BigDecimal getPqdNoAddOne() {
		BigDecimal result = new BigDecimal("0");
		try {
			result = (BigDecimal) this.queryForObject("pa.salaryCanShu.getPqdNoAddOne");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 批量保存导入的派遣地数据(add pai qian di data of import)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void addPaiQianDiDataImport(List list) throws Exception {
		this.insertForList("pa.salaryCanShu.addPaiQianDiDataImport", list);
		this.deleteForList("pa.salaryCanShu.delPaiQianDiDataImportBatch", list);
	}
	
	 /**
	 * 验证导入的城市等级、省份、城市、地区是否存在
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
  @SuppressWarnings("unchecked")
  @Override
  public List getPaiQianDiCodeNoCheckList(Object obj) {
	  LinkedHashMap paramMap = (LinkedHashMap)obj;
	  List returnList = new ArrayList() ;
	  String checkType = paramMap.get("PQD_CHECK_TYPE")!=null?paramMap.get("PQD_CHECK_TYPE").toString():"CSDJ";
	  try {
		  if("FR".equals(checkType)){//获取法人ID
			  returnList = this.queryForList("pa.salaryCanShu.getFrCodeNoCheckList", obj);
		  }else if("CSDJ".equals(checkType)){//获取城市等级NO
			  returnList = this.queryForList("pa.salaryCanShu.getCsdjCodeNoCheckList", obj);
		  }else if("SF".equals(checkType)){//获取省份NO
			  returnList = this.queryForList("pa.salaryCanShu.getSfCodeNoCheckList", obj);
		  }else if("CS".equals(checkType)){//获取城市NO
			  returnList = this.queryForList("pa.salaryCanShu.getCsCodeNoCheckList", obj);
		  }else if("DQ".equals(checkType)){//获取地区NO
			  returnList = this.queryForList("pa.salaryCanShu.getDqCodeNoCheckList", obj);
		  }else if("CS_SF".equals(checkType)){//获取城市与省份
			  returnList = this.queryForList("pa.salaryCanShu.getCsSfCodeNoCheckList", obj);
		  }else if("DQ_CS".equals(checkType)){//获取地区与城市
			  returnList = this.queryForList("pa.salaryCanShu.getDqCsCodeNoCheckList", obj);
		  }
	  } catch (SQLException e) {			
		  e.printStackTrace();
	  }
	  return returnList ;
  }
  
  /**
   * 更新导入的派遣地的check结果(update pai qian di data of import for check result)
   * 
   * @param object
   * @return
   * @throws Exception
   */
  @Override
  public int updatePaiQianDiDataCheckResult(Object object) throws Exception {
    int flag = 0;
    this.update("pa.salaryCanShu.updatePaiQianDiDataCheckResult", object);
    return flag;
  }
  
  /**
   * 取得正式表或者临时表里是否已有相同数据存在或者重复数据
   * @param request
   * @return List
   */
  @SuppressWarnings("unchecked")
  @Override
  public List getPaiQianDiInfoExistList(Object obj) {
	  LinkedHashMap paramMap = (LinkedHashMap)obj;
	  String checkType = paramMap.get("CHECK_TYPE")!=null?paramMap.get("CHECK_TYPE").toString():"TEMP";
	  List returnList = new ArrayList() ;
	  try {
		  if(checkType!=null && "TEMP".equals(checkType)){
			  returnList = this.queryForList("pa.salaryCanShu.getPaiQianDiInfoExistTempList", paramMap);
		  }else{
			  returnList = this.queryForList("pa.salaryCanShu.getPaiQianDiInfoExistList", paramMap);
		  }
	  } catch (SQLException e) {			
		  e.printStackTrace();
	  }
	  return returnList ;
  }
  
  	/**
	 * 导入派遣地信息--查看信息列表(get pai qian di import list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getPaiQianDiImportInfoList(Object obj) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("pa.salaryCanShu.getPaiQianDiImportInfoList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList ;
	}

	/**
	 * 修改时更新旧数据状态
	 */
	@SuppressWarnings("unchecked")
	public void updateOldBonus(Object obj)throws Exception {
		this.update("pa.salaryCanShu.updateOldBonus", obj) ;
	}
	

	/**
	 * check当前年度未添加基准的法人
	 * @param Object
	 * @return
	 */
	@Override
	public List checkAnnualBonusParamSetup(Map obj) {
		List result = new ArrayList() ;
		
		try {
			result = this.queryForList("pa.salaryCanShu.checkAnnualBonusParamSetup", obj) ;
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return result ;
	}
	
	@Override
	public List viewHaoFeng(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("pa.salaryCanShu.viewHaoFeng",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}
	
	@Override
	public List viewHaoFengNAME(Object object) {
		List alist = null;
		try {
			alist = this.queryForList("pa.salaryCanShu.viewHaoFengNAME",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return alist;
	}

}
