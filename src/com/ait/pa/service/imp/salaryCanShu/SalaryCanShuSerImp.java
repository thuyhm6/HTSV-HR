package com.ait.pa.service.imp.salaryCanShu;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.NumberUtils;

import com.ait.pa.dao.SalaryCanShuDao;
import com.ait.ar.dao.CycleDao;
import com.ait.ar.service.CycleSer;
import com.ait.pa.service.salaryCanShu.SalaryCanShuSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.dao.CompanyDao;
import com.ait.sys.dao.SyLanguageDao;
import com.ait.web.messages.Messages;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;
import com.ait.web.util.UiUtil;

/**
 * 
 * @author LXJ
 *
 */
@Service
public class SalaryCanShuSerImp implements SalaryCanShuSer {

	Logger logger = Logger.getLogger(SalaryCanShuSerImp.class);
	
	@Autowired
	private SalaryCanShuDao SalaryCanShuDao;
	@Autowired
	private CompanyDao companyDao;
	
	/**
	 * 通过派遣地的NO获取这条派遣地信息
	 */
	@SuppressWarnings("unchecked")
	public Object getPaiQianDiInfo(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		return this.SalaryCanShuDao.getPaiQianDiInfo(paramMap) ; 
	}
	
	/**
	 * 通过派遣地的NO获取最低工资标准信息非促销员
	 */
	@SuppressWarnings("unchecked")
	public Object getZuiDiGongZiBiaoZhunFeiCuXiaoYuanObjInfo(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		return this.SalaryCanShuDao.getZuiDiGongZiBiaoZhunFeiCuXiaoYuanObjInfo(paramMap) ; 
	}
	
	@SuppressWarnings("unchecked")
	public Object getZuiDiGongZiBiaoZhunCuXiaoYuanObjInfo(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		return this.SalaryCanShuDao.getZuiDiGongZiBiaoZhunCuXiaoYuanObjInfo(paramMap) ;
	}
	
	/**
	 * 通过NO获取预提对象管理的信息
	 */
	@SuppressWarnings("unchecked")
	public Object getYuTiDuiXiangGuanLiInfo(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		return this.SalaryCanShuDao.getYuTiDuiXiangGuanLiObjInfo(paramMap) ; 
	}
	/**
	 * 号俸管理
	  * wangqiang@ait.net.cn
	 * 2014/07/14
	 */
	@SuppressWarnings("unchecked")
	public Object viewHaoFengSetList(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		return this.SalaryCanShuDao.viewHaoFengSetList(paramMap) ; 
	}
	/**
	 * 号俸级别查询
	 * wangqiang@ait.net.cn
	 * 2014/07/14
	 */
	@SuppressWarnings("unchecked")
	public List viewHaoFengSetListGrade(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		
		AdminBean admin = (AdminBean)   request.getSession().getAttribute("LoginUser") ;
		paramMap.put("CPNY_ID", request.getParameter("defaultCpny") == null ? admin.getCpnyId() : request.getParameter("defaultCpny") );
		return this.SalaryCanShuDao.viewHaoFengSetListGrade(paramMap) ; 
	}
	/**
	 * 号俸级号查询
	 *   wangqiang@ait.net.cn
	 * 2014/07/14
	 */
	@SuppressWarnings("unchecked")
	public List viewHaoFengSetListGradeNo(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = (AdminBean)   request.getSession().getAttribute("LoginUser") ;
		paramMap.put("CPNY_ID", request.getParameter("defaultCpny") == null ? admin.getCpnyId() : request.getParameter("defaultCpny") );

		return this.SalaryCanShuDao.viewHaoFengSetListGradeNo(paramMap) ; 
	}
	/**
	 * 通过派遣地的NO获取这条年终奖预提信息
	 */
	@SuppressWarnings("unchecked")
	public Object getNianZhongJiangYuTiInfo(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		return this.SalaryCanShuDao.getNianZhongJiangYuTiObjInfo(paramMap) ; 
	}
	
	/**
	 * ...
	 */
	@SuppressWarnings("unchecked")
	public Object getPaiQianDiJinTieBiaoZhunInfo(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		return this.SalaryCanShuDao.getPaiQianDiJinTieBiaoZhunObjInfo(paramMap) ; 
	}
	/**
	 * ...号俸设置修改 查看
	 */
	@SuppressWarnings("unchecked")
	public Object getHaoFengSheZhiInfo(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request) ;
		return this.SalaryCanShuDao.getHaoFengSheZhiInfo(paramMap) ; 
	}
	/**
	 * 获取年终奖计提数据
	 */
	@SuppressWarnings("unchecked")
	public List getNianZhongJiangJiTiList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("niandu", request.getAttribute("niandu"));
		paramMap.put("faren", request.getAttribute("faren"));
		if (UiUtil.getPageNum(request) > 0){
			retrunList = 
				SalaryCanShuDao.getNianZhongJiangJiTiList(paramMap , 
							UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)
						) ;
		}
		else{
			retrunList = SalaryCanShuDao.getNianZhongJiangJiTiList(paramMap) ;
		}
		return retrunList ;
	}
	
	/**
	 * 获取年终奖计提计算的数据
	 */
	@SuppressWarnings("unchecked")
	public List getNianZhongJiangJiTiJiSuanList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		if (UiUtil.getPageNum(request) > 0){
			retrunList = 
				SalaryCanShuDao.getNianZhongJiangJiTiJiSuanList(paramMap , 
							UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)
						) ;
		}
		else{
			retrunList = SalaryCanShuDao.getNianZhongJiangJiTiJiSuanList(paramMap) ;
		}
		return retrunList ;
	}
	/**
	 * 获取预提计算的数据
	 */
	@SuppressWarnings("unchecked")
	public List viewZhengGuiYuTiJiSuanList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("faren", request.getAttribute("faren"));
		paramMap.put("zhifunian", request.getAttribute("zhifunian"));
		paramMap.put("zhifuyue", request.getAttribute("zhifuyue"));
		paramMap.put("bumen", request.getAttribute("bumen"));
		paramMap.put("shehaoxingming", request.getAttribute("shehaoxingming"));
		paramMap.put("seachleixing", request.getAttribute("seach_leixing"));
		if (UiUtil.getPageNum(request) > 0){
			retrunList = 
				SalaryCanShuDao.viewZhengGuiYuTiJiSuanList(paramMap , 
							UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)
						) ;
		}
		else{
			retrunList = SalaryCanShuDao.viewZhengGuiYuTiJiSuanList(paramMap) ;
		}
		return retrunList ;
	}
	
	/**
	 * 获取派遣地的信息
	 */
	@SuppressWarnings("unchecked")
	public List getPaiQianDiList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("language", Messages.getLanguage(request));
		
		/*paramMap.put("faren", request.getAttribute("faren"));
		paramMap.put("csdj", request.getAttribute("csdj"));
		paramMap.put("dqmc", request.getAttribute("dqmc"));*/
		
		if (UiUtil.getPageNum(request) > 0){
			retrunList = 
				SalaryCanShuDao.getPaiQianDiList(paramMap , 
							UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)
						) ;
		}
		else{
			retrunList = SalaryCanShuDao.getPaiQianDiList(paramMap) ;
		}
		return retrunList ;
	}
	
	/**
	 * 获取最低工资标准非促销员
	 */
	@SuppressWarnings("unchecked")
	public List getZuiDiGongZiBiaoZhunList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		if (UiUtil.getPageNum(request) > 0){
			retrunList = 
				SalaryCanShuDao.getZuiDiGongZiBiaoZhunList(paramMap , 
							UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)
						) ;
		}
		else{
			retrunList = SalaryCanShuDao.getZuiDiGongZiBiaoZhunList(paramMap) ;
		}
		return retrunList ;
	}
	
	/**
	 * 获取最低工资标准促销员
	 */
	@SuppressWarnings("unchecked")
	public List getZuiDiGongZiBiaoZhunCuXiaoYuanList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		if (UiUtil.getPageNum(request) > 0){
			retrunList = 
				SalaryCanShuDao.getZuiDiGongZiBiaoZhunCuXiaoYuanList(paramMap , 
							UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)
						) ;
		}
		else{
			retrunList = SalaryCanShuDao.getZuiDiGongZiBiaoZhunCuXiaoYuanList(paramMap) ;
		}
		return retrunList ;
	}
	
	@SuppressWarnings("unchecked")
	public List getPaiQianDiTempList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		//RESULT_FLAG
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("CREATED_BY", admin.getAdminID());
		if (UiUtil.getPageNum(request) > 0){
			retrunList = 
				SalaryCanShuDao.getPaiQianDiTempList(paramMap , 
							UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)
						) ;
		}
		else{
			retrunList = SalaryCanShuDao.getPaiQianDiTempList(paramMap) ;
		}
		return retrunList ;
	}
	
	@SuppressWarnings("unchecked")
	public List getZuiDiGongZiBiaoZhunFeiCuXiaoYuanTempList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		//RESULT_FLAG
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("CREATED_BY", admin.getAdminID());
		if (UiUtil.getPageNum(request) > 0){
			retrunList = 
				SalaryCanShuDao.getZuiDiGongZiBiaoZhunFeiCuXiaoYuanTempList(paramMap , 
							UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)
						) ;
		}
		else{
			retrunList = SalaryCanShuDao.getZuiDiGongZiBiaoZhunFeiCuXiaoYuanTempList(paramMap) ;
		}
		return retrunList ;
	}
	
	@SuppressWarnings("unchecked")
	public List getZuiDiGongZiBiaoZhunCuXiaoYuanTempList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		//RESULT_FLAG
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("CREATED_BY", admin.getAdminID());
		if (UiUtil.getPageNum(request) > 0){
			retrunList = 
				SalaryCanShuDao.getZuiDiGongZiBiaoZhunCuXiaoYuanTempList(paramMap , 
							UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)
						) ;
		}
		else{
			retrunList = SalaryCanShuDao.getZuiDiGongZiBiaoZhunCuXiaoYuanTempList(paramMap) ;
		}
		return retrunList ;
	}
	
	
	@SuppressWarnings("unchecked")
	public List getPaiQianDiJinTieBiaoZhunTempList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		//RESULT_FLAG
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("CREATED_BY", admin.getAdminID());
		if (UiUtil.getPageNum(request) > 0){
			retrunList = SalaryCanShuDao.getPaiQianDiJinTieBiaoZhunTempList(
					paramMap, UiUtil.getPageNum(request), UiUtil
							.getNumPerPage(request));
		}
		else{
			retrunList = SalaryCanShuDao.getPaiQianDiJinTieBiaoZhunTempList(paramMap) ;
		}
		return retrunList ;
	}
	
	/**
	 * 获取预提兑现管理的信息
	 */
	@SuppressWarnings("unchecked")
	public List getYuTiDuiXiangGuanList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		//法人       
		paramMap.put("faren", request.getAttribute("faren"));
		//部门
		paramMap.put("bmbh", request.getAttribute("bumen"));
		//人员类型组
		paramMap.put("rylxz", request.getAttribute("renyuanleixingzu"));
		//是否参与预提
		paramMap.put("sfcyyt", request.getAttribute("shifoucanyuyuti"));
		//是否启用
		paramMap.put("sfqy", request.getAttribute("shifouqiyong"));
		if (UiUtil.getPageNum(request) > 0){
			retrunList = 
				SalaryCanShuDao.getYuTiDuiXiangGuanList(paramMap , 
							UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)
						) ;
		}
		else{
			retrunList = SalaryCanShuDao.getYuTiDuiXiangGuanList(paramMap) ;
		}
		return retrunList ;
	}
	
	/**
	 * 获取大区名称
	 */
	@SuppressWarnings("unchecked")
	public List getDaQuNamesList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		//只有CH法人有大区信息
		if(!"TSTO".equals(paramMap.get("interCpnyID").toString())){
			return null;
		}
		
		if (UiUtil.getPageNum(request) > 0){
			retrunList = 
				SalaryCanShuDao.getYuTiDuiXiangGuanList(paramMap , 
							UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)
						) ;
			
			retrunList = 
				SalaryCanShuDao.getDaQuNamesList(paramMap , 
							UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)
						) ;
		}
		else{
			retrunList = SalaryCanShuDao.getYuTiDuiXiangGuanList(paramMap) ;
			retrunList = SalaryCanShuDao.getDaQuNamesList(paramMap) ;
			
		}
		return retrunList ;
	}
	
	/**
	 * 。。。
	 */
	@SuppressWarnings("unchecked")
	public List getPaiQianDiJtBzList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("faren", request.getAttribute("faren"));
		paramMap.put("zhize", request.getAttribute("zhize"));
		paramMap.put("csdj", request.getAttribute("csdj"));
		paramMap.put("dqmc", request.getAttribute("dqmc"));
		if (UiUtil.getPageNum(request) > 0){
			retrunList = 
				SalaryCanShuDao.getPaiQianDiJtBzList(paramMap , 
							UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)
						) ;
		}
		else{
			retrunList = SalaryCanShuDao.getPaiQianDiJtBzList(paramMap) ;
		}
		return retrunList ;
	}
	/**
	 * 。。。
	 */
	@SuppressWarnings("unchecked")
	public List viewHaoFengSetListfenye(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		
		//paramMap.put("seach_ZHIQUN", request.getParameter("seach_ZHIQUN"));
		paramMap.put("seach_ZHIJI", request.getParameter("seach_ZHIJI"));
		paramMap.put("seach_HAOFENG", request.getParameter("seach_HAOFENG"));
		//paramMap.put("seach_START_DATE", request.getParameter("seach_START_DATE"));
		//paramMap.put("seach_END_DATE", request.getParameter("seach_END_DATE"));
		if(request.getParameter("seach_ACTIVITY")!=null){
			paramMap.put("seach_ACTIVITY", request.getParameter("seach_ACTIVITY"));
		}else{
			paramMap.put("seach_ACTIVITY", 1);
		}
		

		if(paramMap.get("PQDYear") != null){
			paramMap.put("pqdyearmonth",paramMap.get("PQDYear").toString()+paramMap.get("PQDMonth").toString() );
		}
		paramMap.put("CPNY_ID", request.getParameter("defaultCpny") == null ? paramMap.get("interCpnyID") : request.getParameter("defaultCpny") );
		/*if (UiUtil.getPageNum(request) > 0){
			retrunList = 
				SalaryCanShuDao.viewHaoFengSetListfenye(paramMap , 
							UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)
						) ;
		}
		else{*/
			retrunList = SalaryCanShuDao.viewHaoFengSetListfenye(paramMap) ;
		/*}*/
		return retrunList ;
	}
	/**
	 * 。。。
	 */
	@SuppressWarnings("unchecked")
	public List viewHaoFengSetListfenyeExcel(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		
		paramMap.put("seach_ZHIJI", request.getParameter("seach_ZHIJI"));
		paramMap.put("seach_HAOFENG", request.getParameter("seach_HAOFENG"));
		paramMap.put("seach_ACTIVITY", request.getParameter("seach_ACTIVITY"));
		paramMap.put("CPNY_ID", request.getParameter("defaultCpny") == null ? paramMap.get("interCpnyID") : request.getParameter("defaultCpny") );
		if(paramMap.get("PQDYear") != null){
			paramMap.put("pqdyearmonth",paramMap.get("PQDYear").toString()+paramMap.get("PQDMonth").toString() );
		}
			retrunList = SalaryCanShuDao.viewHaoFengSetListfenyeExcel(paramMap) ;
		 
		return retrunList ;
	}
	
	/**
	 * 保存派遣地的信息
	 */
	@SuppressWarnings("unchecked")
	public int addPaiQianDiGuanLiInfo(HttpServletRequest request){
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("CREATED_BY", admin.getAdminID());
		if("".equals(paramMap.get("PQD_FR")) || null == paramMap.get("PQD_FR")){
			paramMap.put("PQD_FR", admin.getCpnyId());
		}
		
		if("".equals(paramMap.get("PQD_FR")) || null == paramMap.get("PQD_FR")){
			return 10;//法人为空
		}
		//检查数据的关系是否已经存在
		int result = -1;
		try {
			result = this.SalaryCanShuDao.getPaiQianDiGuanLiInfo(paramMap);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if(result >= 1){
			return 100;//已经存在有一样的关系了
		}
		try {
			this.SalaryCanShuDao.addPaiQianDiGuanLiInfo(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 保存派遣地的信息
	 */
	@SuppressWarnings("unchecked")
	public int addZuiDiGongZiBiaoZhunFeiCuXiaoYuanInfo(HttpServletRequest request){
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("CREATED_BY", admin.getAdminID());
		paramMap.put("PQD_FR", admin.getCpnyId());
		int result = -1;
		try {
			result = this.SalaryCanShuDao.getZuiDiGongZiBiaoZhunFeiCuXiaoYuanInfo(paramMap);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if(result >= 1){
			return 100;//已经存在有一样的关系了
		}
		try {
			this.SalaryCanShuDao.addZuiDiGongZiBiaoZhunFeiCuXiaoYuanInfo(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	@SuppressWarnings("unchecked")
	public int addZuiDiGongZiBiaoZhunCuXiaoYuanInfo(HttpServletRequest request){
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("CREATED_BY", admin.getAdminID());
		int result = -1;
		try {
			result = this.SalaryCanShuDao.getZuiDiGongZiBiaoZhunCuXiaoYuanInfo(paramMap);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if(result >= 1){
			return 100;//已经存在有一样的关系了
		}
		try {
			this.SalaryCanShuDao.addZuiDiGongZiBiaoZhunCuXiaoYuanInfo(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 保存预提对象管理的信息--insert
	 */
	@SuppressWarnings("unchecked")
	public int addYuTiDuiXiangGuanLiInfo(HttpServletRequest request){
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("CREATED_BY", admin.getAdminID());
		paramMap.put("PQD_FR", admin.getCpnyId());
		//检查数据的关系是否已经存在
		int result = -1;
		try {
			if("TSTO".equals(paramMap.get("interCpnyID").toString())){
				result = this.SalaryCanShuDao.getYuTiDuiXiangGuanLiInfoLgech(paramMap);
			}else{
				result = this.SalaryCanShuDao.getYuTiDuiXiangGuanLiInfo(paramMap);
			}
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if(result >= 1){
			return 100;//已经存在有一样的关系了
		}
		try {
			if(null == paramMap.get("PQD_DQ") || "".equals(paramMap.get("PQD_DQ").toString())){
				paramMap.put("PQD_DQ", " ");
			}
			this.SalaryCanShuDao.addYuTiDuiXiangGuanLiInfo(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 年终奖预提添加
	 */
	@SuppressWarnings("unchecked")
	public int addNianZhongJiangYuTiInfo(HttpServletRequest request){
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("CREATED_BY", admin.getAdminID());
		if("".equals(paramMap.get("PQD_FR")) || null == paramMap.get("PQD_FR")){
			paramMap.put("PQD_FR", admin.getCpnyId());
		}
		
		if("".equals(paramMap.get("PQD_FR")) || null == paramMap.get("PQD_FR")){
			return 10;//法人为空
		}
		//检查数据的关系是否已经存在
		int result = -1;
		try {
			result = this.SalaryCanShuDao.getNianZhongJiangYuTiInfo(paramMap);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if(result >= 1){
			return 100;//已经存在有一样的关系了
		}
		try {
			this.SalaryCanShuDao.addNianZhongJiangYuTiInfo(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * ...
	 */
	@SuppressWarnings("unchecked")
	public int addPaiQianDiJinTieBiaoZhunInfo(HttpServletRequest request){
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("CREATED_BY", admin.getAdminID());
		if("".equals(paramMap.get("PQD_FR")) || null == paramMap.get("PQD_FR")){
			paramMap.put("PQD_FR", admin.getCpnyId());
		}
		
		if("".equals(paramMap.get("PQD_FR")) || null == paramMap.get("PQD_FR")){
			return 10;//法人为空
		}
		
		//检测要添加的数据在派遣地管理中有没有
//		int checkResult = 0;
//		try {
//			checkResult = this.SalaryCanShuDao.checkPaiQianDiYesOrNoThisInfo(paramMap);
//			if(checkResult == 0){
//				return 333;
//			}
//		} catch (Exception e2) {
//			e2.printStackTrace();
//		}
//		
		//检查数据的关系是否已经存在
		int result = -1;
		try {
			result = this.SalaryCanShuDao.getPaiQianDiJinTieBiaoZhunInfo(paramMap);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if(result >= 1){
			return 100;//已经存在有一样的关系了
		}
		try {
			this.SalaryCanShuDao.addPaiQianDiJinTieBiaoZhunInfo(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	/**
	 * ...
	 */
	@SuppressWarnings("unchecked")
	public int addHaoFengGuanLiInfo(HttpServletRequest request){
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("CREATED_BY", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());
	/*	if("".equals(paramMap.get("PQD_FR")) || null == paramMap.get("PQD_FR")){
			return 10;//法人为空
		}
		//检查数据的关系是否已经存在
		int result = -1;
		try {
			result = this.SalaryCanShuDao.getPaiQianDiJinTieBiaoZhunInfo(paramMap);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if(result == 1){
			return 100;//已经存在有一样的关系了
		}*/
		try {
			int result=this.SalaryCanShuDao.addHaoFengGuanLiInfo(paramMap);
			if(result!=0){
				return 6;
			}
		}catch(SQLException e){
			e.printStackTrace();
			if(e.getSQLState()=="23000"){
				return 5;
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 更新派遣地信息
	 */
	@SuppressWarnings("unchecked")
	public int updatePaiQianDiGuanLiInfo(HttpServletRequest request){
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("UPDATE_BY", admin.getAdminID());
		
		int result = -1;
		//String PQD_NO_OLD = "ppp";
		//String PQD_NO_UPDATE = "pppp";
		try {
			//PQD_NO_OLD = (String)paramMap.get("PQD_CSDJ");
			//PQD_NO_UPDATE = this.SalaryCanShuDao.getPaiQianDiGuanLiInfoUpdatePqd(paramMap);
			result = (Integer)this.SalaryCanShuDao.getPaiQianDiGuanLiInfoUpdate(paramMap);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if(result >= 1){
			return 2;
		}
		try {
			this.SalaryCanShuDao.updatePaiQianDiGuanLiInfo(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 更新最低工资标准非促销员信息
	 */
	@SuppressWarnings("unchecked")
	public int updateZuiDiGongZiBiaoZhunFeiCuXiaoYuanInfo(HttpServletRequest request){
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("UPDATE_BY", admin.getAdminID());
		paramMap.put("PQD_FR", admin.getCpnyId());
		int result = -1;
		try {
			result = (Integer)this.SalaryCanShuDao.getZuiDiGongZiBiaoZhunFeiCuXiaoYuanInfoUpdate(paramMap);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if(result >= 1){
			return 2;
		}
		try {
			this.SalaryCanShuDao.updateZuiDiGongZiBiaoZhunFeiCuXiaoYuanInfo(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	@SuppressWarnings("unchecked")
	public int updateZuiDiGongZiBiaoZhunCuXiaoYuanInfo(HttpServletRequest request){
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("UPDATE_BY", admin.getAdminID());
		int result = -1;
		try {
			result = (Integer)this.SalaryCanShuDao.getZuiDiGongZiBiaoZhunCuXiaoYuanInfoUpdate(paramMap);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if(result >= 1){
			return 2;
		}
		try {
			this.SalaryCanShuDao.updateZuiDiGongZiBiaoZhunCuXiaoYuanInfo(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 更新派遣地临时表中的数据正确与否的标志
	 */
	@SuppressWarnings("unchecked")
	public int updatePaiQianDiErrorInfo(HttpServletRequest request){
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("CREATED_BY", admin.getAdminID());
		//将这些NO的数据的检测结果标志为E,其他的标志为N
		List errorPqdNos = new ArrayList ();
		errorPqdNos = this.SalaryCanShuDao.getErrorPqdNos(paramMap);
		
		//将错误的数据的检测结果设置为E,其他的设置为N
		try {
			this.SalaryCanShuDao.updatePaiQianDiGuanLiTempInfo(errorPqdNos, paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 更新派遣津贴标准临时表中的数据正确与否的标志
	 */
	@SuppressWarnings("unchecked")
	public int updatePaiQianDiJinTieBiaoZhunErrorInfo(HttpServletRequest request){
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("CREATED_BY", admin.getAdminID());
		//将这些NO的数据的检测结果标志为E,其他的标志为N
		List errorPqdNos = new ArrayList ();
		errorPqdNos = this.SalaryCanShuDao.getErrorPqdJinTieBiaoZhunNos(paramMap);
		
		//将错误的数据的检测结果设置为E,其他的设置为N
		try {
			this.SalaryCanShuDao.updatePaiQianDiJinTieBiaoZhunTempInfo(errorPqdNos, paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 删除临时表中重复的数据
	 */
	@SuppressWarnings("unchecked")
	public int deletePaiQianDiTempChongFuInfo(HttpServletRequest request){
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("CREATED_BY", admin.getAdminID());
		try {
			this.SalaryCanShuDao.deletePaiQianDiTempChongFuInfo(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 导入正确的数据
	 */
	@SuppressWarnings("unchecked")
	public int insertPaiQianDiInfoFromTempInfo(HttpServletRequest request){
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("CREATED_BY", admin.getAdminID());
		try {
			this.SalaryCanShuDao.insertPaiQianDiInfoFromTempInfo(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 修改预提对象管理的信息
	 */
	@SuppressWarnings("unchecked")
	public int updateYuTiDuiXiangGuanLiInfo(HttpServletRequest request){
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("UPDATE_BY", admin.getAdminID());
		paramMap.put("PQD_FR", admin.getCpnyId());//PQD_FR
		int result = -1;
		try {
			if("TSTO".equals(paramMap.get("interCpnyID").toString())){
				result = (Integer)this.SalaryCanShuDao.getYuTiDuiXiangGuanLiInfoUpdateLgech(paramMap);
			}else{
				result = (Integer)this.SalaryCanShuDao.getYuTiDuiXiangGuanLiInfoUpdate(paramMap);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if(result >= 1){
			return 2;
		}
		try {
			if(null == paramMap.get("PQD_DQ") || "".equals(paramMap.get("PQD_DQ").toString())){
				paramMap.put("PQD_DQ", " ");
			}
			this.SalaryCanShuDao.updateYuTiDuiXiangGuanLiInfo(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 年终奖预提--修改
	 */
	@SuppressWarnings("unchecked")
	public int updateNianZhongJiangYuTiInfo(HttpServletRequest request){
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("UPDATE_BY", admin.getAdminID());
		paramMap.put("CREATED_BY", admin.getAdminID());
		try {
			this.SalaryCanShuDao.updateOldBonus(paramMap);
			
			this.SalaryCanShuDao.addNianZhongJiangYuTiInfo(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	@SuppressWarnings("unchecked")
	public int updateZuiDiGongZiBiaoZhunFeiCuXiaoYuanExcelResultToNos(HttpServletRequest request){
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("CREATED_BY", admin.getAdminID());
		try {
			this.SalaryCanShuDao.updateZuiDiGongZiBiaoZhunFeiCuXiaoYuanExcelResultToNos(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	@SuppressWarnings("unchecked")
	public int updatePaiQianDiGuanLiExcelResultToNos(HttpServletRequest request){
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("CREATED_BY", admin.getAdminID());
		try {
			this.SalaryCanShuDao.updatePaiQianDiGuanLiExcelResultToNos(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	@SuppressWarnings("unchecked")
	public int updatePaiQianDiJinTieBiaoZhunExcelResultToNos(HttpServletRequest request){
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("CREATED_BY", admin.getAdminID());
		try {
			this.SalaryCanShuDao.updatePaiQianDiJinTieBiaoZhunExcelResultToNos(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	@SuppressWarnings("unchecked")
	public int updateZuiDiGongZiBiaoZhunCuXiaoYuanExcelResultToNos(HttpServletRequest request){
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("CREATED_BY", admin.getAdminID());
		try {
			this.SalaryCanShuDao.updateZuiDiGongZiBiaoZhunCuXiaoYuanExcelResultToNos(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 年终奖预提计算调用存储进行计算
	 */
	@SuppressWarnings("unchecked")
	public String callNianZhongJiangYuTiJiSuanProduce(HttpServletRequest request){
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("PERSONID", admin.getPersonId());
		paramMap.put("CNPYID", admin.getCpnyId());
		paramMap.put("MESSAGE", "OK");
		
		try {
			String message = this.SalaryCanShuDao.callNianZhongJiangYuTiJiSuanProduce(paramMap);
			return message;
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage(); 
		}
	}

	/**
	 * 正规预提计算调用存储进行计算
	 */
	@SuppressWarnings("unchecked")
	public int callZhengGuiYuTiJiSuanProduce(HttpServletRequest request){
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("PERSONID", admin.getPersonId());
		paramMap.put("CNPYID", admin.getCpnyId());
		paramMap.put("MESSAGE", "OK");
		
		try {
			String message = this.SalaryCanShuDao.callZhengGuiYuTiJiSuanProduce(paramMap);
			if(null != message && message.equals("OK")){
				return 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	public int getHaoFengDayMonAjax(HttpServletRequest request){
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		int result=0;
		try {
			result = this.SalaryCanShuDao.getHaoFengDayMonAjax(paramMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result=0;
		}
		
		 
		return result; 
		
	}
	
	/**
	 * ...
	 */
	@SuppressWarnings("unchecked")
	public int updatePaiQianDiJinTieBiaoZhunInfo(HttpServletRequest request){
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request) ;
		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("UPDATE_BY", admin.getAdminID());
		
		//检测要添加的数据在派遣地管理中有没有
		int checkResult = 0;
		try {
			checkResult = this.SalaryCanShuDao.checkPaiQianDiYesOrNoThisInfo(paramMap);
			if(checkResult == 0){
				return 333;
			}
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		
		int result = -1;
		try {
			result = (Integer)this.SalaryCanShuDao.getPaiQianDiJinTieBiaoZhunInfoUpdate(paramMap);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if(result >= 1){
			return 2;
		}
		try {
			this.SalaryCanShuDao.updatePaiQianDiJinTieBiaoZhunInfo(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * ...
	 */
	@SuppressWarnings("unchecked")
	public int updateHaoFengSheZhiInfo(HttpServletRequest request){
		
		try {
			String jsonString = request.getParameter("jsonData") ;
			List<LinkedHashMap<String, Object>> dataList = ObjectBindUtil.getRequestJsonData(jsonString) ;
			this.SalaryCanShuDao.updateHaoFengSheZhiInfo(dataList);
		} catch (SQLException e) {
			e.getSQLState();
			if(e.getSQLState()=="23000"){
				return 5;
			}
			
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	
	/**
	 * 删除派遣地
	 */
	@SuppressWarnings("unchecked")
	public int deletePaiQianDiGuanLiInfo(HttpServletRequest request) {
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		try {
			this.SalaryCanShuDao.deletePaiQianDiGuanLiInfo(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 删除预提对象管理数据
	 */
	@SuppressWarnings("unchecked")
	public int deleteYuTiDuiXiangGuanLiInfo(HttpServletRequest request) {
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		try {
			this.SalaryCanShuDao.deleteYuTiDuiXiangGuanLiInfo(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 删除派遣津贴
	 */
	@SuppressWarnings("unchecked")
	public int deletePaiQianDiJinTieBiaoZhunInfo(HttpServletRequest request) {
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		try {
			this.SalaryCanShuDao.deletePaiQianDiJinTieBiaoZhunInfo(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	/**
	 * 删除 号俸设置页面
	 */
	@SuppressWarnings("unchecked")
	public int deleteHaoFengSheZhiInfo(HttpServletRequest request) {
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		try {
			this.SalaryCanShuDao.deleteHaoFengSheZhiInfo(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	/**
	 * ...
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getPaiQianDiCnt(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("language", Messages.getLanguage(request));
		
		/*paramMap.put("faren", request.getAttribute("faren"));
		paramMap.put("csdj", request.getAttribute("csdj"));
		paramMap.put("dqmc", request.getAttribute("dqmc"));*/
		
		return SalaryCanShuDao.getPaiQianDiCnt(paramMap) ;
	}
	
	/**
	 * 获取最低工资标准数量
	 */
	@Override
	public int geZuiDiGongZiBiaoZhunListCnt(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		return SalaryCanShuDao.geZuiDiGongZiBiaoZhunListCnt(paramMap) ;
	}
	
	
	@Override
	public int getZuiDiGongZiBiaoZhunCuXiaoYuanListCnt(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		return SalaryCanShuDao.getZuiDiGongZiBiaoZhunCuXiaoYuanListCnt(paramMap) ;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int getPaiQianDiTempCnt(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("CREATED_BY", admin.getAdminID());
		return SalaryCanShuDao.getPaiQianDiTempCnt(paramMap) ;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int getZuiDiGongZiBiaoZhunFeiCuXiaoYuanTempListCnt(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("CREATED_BY", admin.getAdminID());
		return SalaryCanShuDao.getZuiDiGongZiBiaoZhunFeiCuXiaoYuanTempListCnt(paramMap) ;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int getZuiDiGongZiBiaoZhunCuXiaoYuanTempListCnt(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("CREATED_BY", admin.getAdminID());
		return SalaryCanShuDao.getZuiDiGongZiBiaoZhunCuXiaoYuanTempListCnt(paramMap) ;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int getPaiQianDiJinTieBiaoZhunTempListCnt(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("CREATED_BY", admin.getAdminID());
		return SalaryCanShuDao.getPaiQianDiJinTieBiaoZhunTempListCnt(paramMap) ;
	}
	
	@SuppressWarnings({ "unchecked" })
	@Override
	public int getPaiQianDiTempErrorCnt(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("CREATED_BY", admin.getAdminID());
		return SalaryCanShuDao.getPaiQianDiTempErrorCnt(paramMap) ;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int getZuiDiGongZiBiaoZhunFeiCuXiaoYuanTempErrorCnt(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("CREATED_BY", admin.getAdminID());
		return SalaryCanShuDao.getZuiDiGongZiBiaoZhunFeiCuXiaoYuanTempErrorCnt(paramMap) ;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int getZuiDiGongZiBiaoZhunCuXiaoYuanTempErrorCnt(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("CREATED_BY", admin.getAdminID());
		return SalaryCanShuDao.getZuiDiGongZiBiaoZhunCuXiaoYuanTempErrorCnt(paramMap) ;
	}
	
	@Override
	public int getPaiQianDiJinTieBiaoZhunTempErrorCnt(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("CREATED_BY", admin.getAdminID());
		return SalaryCanShuDao.getPaiQianDiJinTieBiaoZhunTempErrorCnt(paramMap) ;
	}
	
	/**
	 * 获取预提对象管理的数量
	 */
	@Override
	public int getYuTiDuiXiangGuanListCnt(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		//法人       
		paramMap.put("faren", request.getAttribute("faren"));
		//部门
		paramMap.put("bmbh", request.getAttribute("bumen"));
		//人员类型组
		paramMap.put("rylxz", request.getAttribute("renyuanleixingzu"));
		//是否参与预提
		paramMap.put("sfcyyt", request.getAttribute("shifoucanyuyuti"));
		//是否启用
		paramMap.put("sfqy", request.getAttribute("shifouqiyong"));
		return SalaryCanShuDao.getYuTiDuiXiangGuanListCnt(paramMap) ;
	}
	
	/**
	 * 获取年终奖预提的数量
	 */
	@Override
	public int getNianZhongJiangJiTiListCntCnt(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("niandu", request.getAttribute("niandu"));
		paramMap.put("faren", request.getAttribute("faren"));
		return SalaryCanShuDao.getNianZhongJiangJiTiListCntCnt(paramMap) ;
	}
	
	/**
	 * 获取年终奖预提的数量
	 */
	@Override
	public int getNianZhongJiangJiTiJiSuanListCnt(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		return SalaryCanShuDao.getNianZhongJiangJiTiJiSuanListCnt(paramMap) ;
	}
	/**
	 * 获取年终奖预提的数量
	 */
	@Override
	public int viewZhengGuiYuTiJiSuanListCn(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("faren", request.getAttribute("faren"));
		paramMap.put("zhifunian", request.getAttribute("zhifunian"));
		paramMap.put("zhifuyue", request.getAttribute("zhifuyue"));
		paramMap.put("bumen", request.getAttribute("bumen"));
		paramMap.put("shehaoxingming", request.getAttribute("shehaoxingming"));
		paramMap.put("seachleixing", request.getAttribute("seach_leixing"));
		return SalaryCanShuDao.viewZhengGuiYuTiJiSuanListCn(paramMap) ;
	}
	
	/**
	 * ...
	 */
	@Override
	public int getPaiQianDiJtBzCnt(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("faren", request.getAttribute("faren"));
		paramMap.put("csdj", request.getAttribute("csdj"));
		paramMap.put("dqmc", request.getAttribute("dqmc"));
		return SalaryCanShuDao.getPaiQianDiJtBzCnt(paramMap) ;
	}
	/**
	 * ...
	 */
	@Override
	public int getHaoFengSheZhiListCnt(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("seach_dengji", request.getParameter("seach_dengji"));
		paramMap.put("seach_jihao", request.getParameter("seach_jihao"));
		paramMap.put("seach_use_yn", request.getParameter("seach_use_yn"));
		
		paramMap.put("CPNY_ID", request.getParameter("defaultCpny") == null ? paramMap.get("interCpnyID") : request.getParameter("defaultCpny") );
		return SalaryCanShuDao.getHaoFengSheZhiListCnt(paramMap) ;
	}
	
	/**
	 * 添加导入的派遣地数据(add pai qian di data)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int addImportPaiQianDiData(HttpServletRequest request)throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List batchPqdDataList = new ArrayList();
		List importPqdDataList = new ArrayList();
		LinkedHashMap paramMap = new LinkedHashMap();
		paramMap.put("CREATED_BY", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		
		importPqdDataList = this.SalaryCanShuDao.getPaiQianDiImportInfoList(paramMap);
		int checkFlag = 0;
		//这里对所有导入派遣地临时表里的派遣地数据进行验证，并将验证结果存入派遣地信息临时表中
		checkFlag = checkImportPqdData(importPqdDataList,paramMap,request);
		//只有当所有的派遣地验证全部通过之后才能进行派遣地信息插入
		if(checkFlag==0){
			for (int k = 0; k <importPqdDataList.size(); k++) {
				LinkedHashMap pqdMap = new LinkedHashMap();
				pqdMap = (LinkedHashMap)importPqdDataList.get(k);
				String pqd_no = pqdMap.get("PQD_NO")!=null?pqdMap.get("PQD_NO").toString():"";
				String cpnyId = pqdMap.get("PQD_FAREN")!=null?pqdMap.get("PQD_FAREN").toString():admin.getCpnyId();
				String csdj_no = "";//城市等级NO
				String csdj = pqdMap.get("PQD_CHENGSHIDENGJI") != null ? pqdMap.get("PQD_CHENGSHIDENGJI").toString(): "";
				String sf_no = "";//省份NO
				String sf = pqdMap.get("PQD_SHENGFEN")!=null?pqdMap.get("PQD_SHENGFEN").toString():"";
				String csmc_no = "";//城市NO
				String csmc = pqdMap.get("PQD_CHENGSHIMINGCHENG")!=null?pqdMap.get("PQD_CHENGSHIMINGCHENG").toString():"";
				String dqmc_no = "";//地区NO
				String dqmc = pqdMap.get("PQD_DIQUMINGCHENG")!=null?pqdMap.get("PQD_DIQUMINGCHENG").toString():"";
				
				String activity = pqdMap.get("ACTIVITY")!=null?pqdMap.get("ACTIVITY").toString():"1";
				
				LinkedHashMap dataMap = new LinkedHashMap();
				dataMap.put("PQD_NO", pqd_no);
				dataMap.put("PQD_FAREN_CHECK", cpnyId);
				dataMap.put("PQD_FAREN", cpnyId);
				//将城市等级名称转换成code
				dataMap.put("PQD_CHENGSHIDENGJI_CHECK", csdj);
				dataMap.put("PQD_CHECK_TYPE", "CSDJ");//查询城市等级no
				csdj_no = ((LinkedHashMap)SalaryCanShuDao.getPaiQianDiCodeNoCheckList(dataMap).get(0)).get("CODE_NO").toString();
				dataMap.put("PQD_CHENGSHIDENGJI", csdj_no);
				//将省份名称转换成code
				dataMap.put("PQD_SHENGFEN_CHECK", sf);
				dataMap.put("PQD_CHECK_TYPE", "SF");//查询省份no
				sf_no = ((LinkedHashMap)SalaryCanShuDao.getPaiQianDiCodeNoCheckList(dataMap).get(0)).get("CODE_NO").toString();
				dataMap.put("PQD_SHENGFEN", sf_no);
				//将城市名称转换成code
				dataMap.put("PQD_CHENGSHIMINGCHENG_CHECK", csmc);
				dataMap.put("PQD_CHECK_TYPE", "CS");//查询城市no
				csmc_no = ((LinkedHashMap)SalaryCanShuDao.getPaiQianDiCodeNoCheckList(dataMap).get(0)).get("CODE_NO").toString();
				dataMap.put("PQD_CHENGSHIMINGCHENG", csmc_no);
				//将地区名称转换成code
				dataMap.put("PQD_DIQUMINGCHENG_CHECK", dqmc);
				dataMap.put("PQD_CHECK_TYPE", "DQ");//查询地区no
				dqmc_no = ((LinkedHashMap)SalaryCanShuDao.getPaiQianDiCodeNoCheckList(dataMap).get(0)).get("CODE_NO").toString();
				dataMap.put("PQD_DIQUMINGCHENG", dqmc_no);
				
				dataMap.put("ACTIVITY", activity);
				dataMap.put("CREATED_BY", admin.getPersonId());
				
				batchPqdDataList.add(dataMap);
			}
			this.SalaryCanShuDao.addPaiQianDiDataImport(batchPqdDataList);
			return 1;
		}else{
			return -1;
		}
	}
	
	/**
	 * 验证导入的派遣地数据(check pai qian di data info)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int checkImportPqdData(List importPqdDataList,LinkedHashMap paramMap,HttpServletRequest request)throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CREATED_BY", admin.getPersonId());
		int result = 0;
		for (int k = 0; k <importPqdDataList.size(); k++) {
			int checkFlag = 0;
			LinkedHashMap dataMap = new LinkedHashMap();
			dataMap = (LinkedHashMap)importPqdDataList.get(k);
			dataMap.put("UPDATED_BY", admin.getPersonId());
			
			List dataExistList = new ArrayList();
			int dataExistFlag = 0;
			List farenList = new ArrayList();
			int farenFlag = 0;
			List csdjList = new ArrayList();
			int csdjFlag = 0;
			List sfList = new ArrayList();
			int sfFlag = 0;
			List csList = new ArrayList();
			int csFlag = 0;
			List dqList = new ArrayList();
			int dqFlag = 0;
			
			String errorContent = "";
			//1.验证该派遣地信息在正式表中是否存在
			dataMap.put("CHECK_TYPE", "TEMP_NO");
			dataExistList = this.SalaryCanShuDao.getPaiQianDiInfoExistList(dataMap);
			dataExistFlag = dataExistList!=null?dataExistList.size():0;
			if(dataExistFlag >= 1) {
				errorContent = "[该派遣地信息已存在，无法导入!]";
				dataMap.put("ERROR_CONTENT", errorContent);
				checkFlag = this.SalaryCanShuDao.updatePaiQianDiDataCheckResult(dataMap);
				result = result + 1;
			}
			//2.验证该派遣地信息在临时表中是否有重复
			dataMap.put("CHECK_TYPE", "TEMP");
			dataExistList = this.SalaryCanShuDao.getPaiQianDiInfoExistList(dataMap);
			dataExistFlag = dataExistList!=null?dataExistList.size():0;
			if(dataExistFlag >= 1) {
				errorContent = "[导入的派遣地信息重复!]";
				dataMap.put("ERROR_CONTENT", errorContent);
				checkFlag = this.SalaryCanShuDao.updatePaiQianDiDataCheckResult(dataMap);
				result = result + 1;
			}
			//3.验证法人是否存在，是否正确
			dataMap.put("PQD_CHECK_TYPE", "FR");//查询城市等级no
			farenList = this.SalaryCanShuDao.getPaiQianDiCodeNoCheckList(dataMap);
			farenFlag = farenList!=null?farenList.size():0;
			if(farenFlag <= 0) {
				errorContent = "[该法人不存在!]";
				dataMap.put("ERROR_CONTENT", errorContent);
				checkFlag = this.SalaryCanShuDao.updatePaiQianDiDataCheckResult(dataMap);
				result = result + 1;
			}
			//4.验证城市等级是否存在，是否正确
			dataMap.put("PQD_CHECK_TYPE", "CSDJ");//查询城市等级no
			csdjList = this.SalaryCanShuDao.getPaiQianDiCodeNoCheckList(dataMap);
			csdjFlag = csdjList!=null?csdjList.size():0;
			if(csdjFlag <= 0) {
				errorContent = "[该城市等级不存在!]";
				dataMap.put("ERROR_CONTENT", errorContent);
				checkFlag = this.SalaryCanShuDao.updatePaiQianDiDataCheckResult(dataMap);
				result = result + 1;
			}
			//5.验证省份是否存在，是否正确
			dataMap.put("PQD_CHECK_TYPE", "SF");//查询省份no
			sfList = this.SalaryCanShuDao.getPaiQianDiCodeNoCheckList(dataMap);
			sfFlag = sfList!=null?sfList.size():0;
			if(sfFlag <= 0) {
				errorContent = "[该省份不存在!]";
				dataMap.put("ERROR_CONTENT", errorContent);
				checkFlag = this.SalaryCanShuDao.updatePaiQianDiDataCheckResult(dataMap);
				result = result + 1;	
			}
			//6.验证城市是否存在，是否正确
			dataMap.put("PQD_CHECK_TYPE", "CS");//查询城市no
			csList = this.SalaryCanShuDao.getPaiQianDiCodeNoCheckList(dataMap);
			csFlag = csList!=null?csList.size():0;
			if(csFlag <= 0) {
				errorContent = "[该城市不存在!]";
				dataMap.put("ERROR_CONTENT", errorContent);
				checkFlag = this.SalaryCanShuDao.updatePaiQianDiDataCheckResult(dataMap);
				result = result + 1;
			}
			//7.验证城市与省份是否一致，是否正确
			dataMap.put("PQD_CHECK_TYPE", "CS_SF");//查询城市no
			csList = this.SalaryCanShuDao.getPaiQianDiCodeNoCheckList(dataMap);
			csFlag = csList!=null?csList.size():0;
			if(csFlag <= 0){
				errorContent = "[城市与省份不一致!]";
				dataMap.put("ERROR_CONTENT", errorContent);
				checkFlag = this.SalaryCanShuDao.updatePaiQianDiDataCheckResult(dataMap);
				result = result + 1;
			}
			//8.验证地区是否存在，是否正确
			dataMap.put("PQD_CHECK_TYPE", "DQ");//查询地区no
			dqList = this.SalaryCanShuDao.getPaiQianDiCodeNoCheckList(dataMap);
			dqFlag = dqList!=null?dqList.size():0;
			if(dqFlag <= 0) {
				errorContent = "[该地区不存在!]";
				dataMap.put("ERROR_CONTENT", errorContent);
				checkFlag = this.SalaryCanShuDao.updatePaiQianDiDataCheckResult(dataMap);
				result = result + 1;
			}
			//9.验证地区与城市是否一致，是否正确
			dataMap.put("PQD_CHECK_TYPE", "DQ_CS");//查询地区no
			dqList = this.SalaryCanShuDao.getPaiQianDiCodeNoCheckList(dataMap);
			dqFlag = dqList!=null?dqList.size():0;
			if(dqFlag <= 0){
				errorContent = "[地区与城市不一致!]";
				dataMap.put("ERROR_CONTENT", errorContent);
				checkFlag = this.SalaryCanShuDao.updatePaiQianDiDataCheckResult(dataMap);
				result = result + 1;
			}
			
			result = result + checkFlag;
		}
		return result;
	}
	
	/**
	 * 导入派遣地信息--查看信息列表(get pai qian di import list)
	 */
	@SuppressWarnings("unchecked")
	public List getPaiQianDiImportInfoList(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List retrunList = new ArrayList() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_") ;
		paramMap.put("CREATED_BY", admin.getPersonId());
		
		retrunList = SalaryCanShuDao.getPaiQianDiImportInfoList(paramMap) ;
		return retrunList ;
	}
	

	public List getCompanyList(HttpServletRequest request)
	throws SQLException {

		List retrunList = new ArrayList() ;
		
		Map paramMap=new LinkedHashMap();
		
		paramMap.put("ACTIVITY",1);
		retrunList = companyDao.getCompanyBouns(paramMap) ;
		return retrunList ;
	}
	

	public List getCompanyListYuti(HttpServletRequest request)
	throws SQLException {

		List retrunList = new ArrayList() ;
		
		Map paramMap=new LinkedHashMap();
		
		paramMap.put("ACTIVITY",1);
		retrunList = companyDao.getCompanyYuti(paramMap) ;
		return retrunList ;
	}
	
	/**
	 * check当前年度未添加基准的法人
	 */
	@SuppressWarnings("unchecked")
	public String checkAnnualBonusParamSetup(HttpServletRequest request) {
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		String cpnyIdStr = "OK";
		try {
			List list = this.SalaryCanShuDao.checkAnnualBonusParamSetup(paramMap);
			if(list != null && list.size() > 0){
				for(int i=0;i<list.size();i++){
					if(i==0){
						cpnyIdStr = (String)list.get(i);
					}else{
						cpnyIdStr += "," + (String)list.get(i);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		return cpnyIdStr;
	}
	
	@Override
	public List viewHaoFeng(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());

		return this.SalaryCanShuDao.viewHaoFeng(paramMap);
	}
	
	@Override
	public List viewHaoFengNAME(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("interLanguage", admin.getLanguage());
		paramMap.put("seach_HAOFENG", request.getParameter("seach_HAOFENG")==null?"":request.getParameter("seach_HAOFENG"));

		return this.SalaryCanShuDao.viewHaoFengNAME(paramMap);
	}
}
