package com.ait.ar.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.ar.dao.ArEatMealCountDao;
import com.ait.ar.service.ArEatMealCountSer;
import com.ait.report.hr.dao.HrReportDao;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

/**
 * 
 * @author Administrator 业务接口实现类
 */
@Service
public class ArEatMealCountSerImp implements ArEatMealCountSer {
	Logger logger = Logger.getLogger(ArEatMealCountSerImp.class);
	@Autowired
	private ArEatMealCountDao arEatMealCountDao;
	
	@Autowired
	private HrReportDao hrReportDao;

	/**
	 * 初始化指定日期范围内的员工食堂刷卡次数
	 * @param request
	 * @return int
	 * @throws 
	 */
	@SuppressWarnings("unchecked")
	public List<String> initMealCount(LinkedHashMap paramMap){
		List<String> resultList = new ArrayList<String>();
		String result = "";
		Calendar c = Calendar.getInstance();
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		String date = f.format(c.getTime());
		
		String fromDate = paramMap.get("FROM_DATE")!=null?paramMap.get("FROM_DATE").toString():date;
		String endDate = paramMap.get("END_DATE")!=null?paramMap.get("END_DATE").toString():date;
		LinkedHashMap map = new LinkedHashMap();
		map.put("FROM_DATE", fromDate);
		map.put("TO_DATE", endDate);
		List<LinkedHashMap> dateList = new ArrayList();
		try {
			dateList = (List<LinkedHashMap>)this.arEatMealCountDao.getInitDateList(map);
		} catch (Exception e1) {
			result = "2,AR_EAT_MEAL_ATT,AR_EAT_MEAL_TT INIT GET DATE LIST ERROR!";
			e1.printStackTrace();
		}
		if(dateList != null){
			for(LinkedHashMap lmap : dateList){
				lmap.put("CPNY_ID", paramMap.get("CPNY_ID"));
				lmap.put("CREATED_BY", paramMap.get("CREATED_BY"));
				List<LinkedHashMap> dataList = (ArrayList<LinkedHashMap>)arEatMealCountDao.getEatMealCountC12(lmap);
				List<LinkedHashMap> mealDataList = new ArrayList<LinkedHashMap>();
				//移除某天的员工吃饭次数已经锁定的信息
				for(LinkedHashMap dataMap : dataList){
					int mealCount = 0;
					mealCount = arEatMealCountDao.getEmpMealCountByDateCnt(dataMap);
					//判断该天，员工吃饭次数是否已经被锁定，未锁定的，允许重新初始化操作
					if(mealCount==0){
						mealDataList.add(dataMap);
					}
				}
				try {
					arEatMealCountDao.initMealCount(mealDataList) ;
				} catch (Exception e) {
					result = "2,AR_EAT_MEAL_ATT,"+lmap.get("FROM_DATE").toString()+" AR_EAT_MEAL_TT INIT FAILE!";
					e.printStackTrace();
				}
				result = "1,AR_EAT_MEAL_ATT,"+lmap.get("FROM_DATE").toString()+" AR_EAT_MEAL_TT INIT SUCCESS!";
			}
			resultList.add(result);
		}
		return resultList;
	}
	
	/**
	 * 查看指定日期里员工的食堂就餐打卡信息(get eat meal count List)
	 * 
	 * @param request
	 * @return List
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List getEatMealCountList(HttpServletRequest request) {
		List retrunList = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");

		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("ADMIN_ID", admin.getAdminID());

		if (UiUtil.getPageNum(request) > 0) {
			retrunList = arEatMealCountDao.getEatMealCountList(
					paramMap, UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = arEatMealCountDao.getEatMealCountList(paramMap);
		}

		return retrunList;
	}

	/**
	 * 查看指定日期里员工的食堂就餐打卡信息(get eat meal count cnt)数量
	 * 
	 * @param request
	 * @return int
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getEatMealCountCnt(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");

		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("ADMIN_ID", admin.getAdminID());

		return arEatMealCountDao.getEatMealCountCnt(paramMap);
	}

	/**
	 * 查看指定日期里员工的食堂就餐打卡信息(get eat meal count List)，导出用
	 * 
	 * @param request
	 * @return List
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List getEatMealCountExcelList(HttpServletRequest request) {
		List retrunList = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");

		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		
		retrunList = arEatMealCountDao.getEatMealCountList(paramMap);
		return retrunList;
	}
	
	/**
	 * 查找指定日期里可添加食堂就餐打卡信息的员工名单(get the person info List for add eat meal count),分页显示
	 * 
	 * @param request
	 * @return List
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List getEatMealCountPersonList(HttpServletRequest request) {
		List retrunList = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");

		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("ADMIN_ID", admin.getAdminID());

		if (UiUtil.getPageNum(request) > 0) {
			retrunList = arEatMealCountDao.getEatMealCountPersonList(
					paramMap, UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = arEatMealCountDao.getEatMealCountPersonList(paramMap);
		}

		return retrunList;
	}

	/**
	 * 查找指定日期里可添加食堂就餐打卡信息的员工名单(get the person info List for add eat meal count),数量
	 * 
	 * @param request
	 * @return int
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getEatMealCountPersonListCnt(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");

		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("ADMIN_ID", admin.getAdminID());

		return arEatMealCountDao.getEatMealCountPersonListCnt(paramMap);
	}
	
	/**
	 * 添加食堂刷卡次数信息
	 * 
	 * @param request
	 * @return
	 * @throws ParseException 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String addBatchEatCount(HttpServletRequest request) throws ParseException{
		//获取页面参数
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List batchEatMealCountList = new ArrayList();
		String result = "1";
		String[] paramData = request.getParameterValues("c1");
		//员工循环
		for (int j = 0; j < paramData.length; j++) {
			String personId = paramData[j];
			paramMap.put("PERSON_ID", personId);
			String fromDate = paramMap.get(personId + "_FROM_DATE") != null ?
					paramMap.get(personId + "_FROM_DATE").toString(): "";
			String toDate = paramMap.get(personId + "_TO_DATE") != null ?
					paramMap.get(personId + "_TO_DATE").toString(): paramMap.get(personId + "_FROM_DATE").toString();
			String mealCount = paramMap.get(personId + "_MEAL_COUNT") != null ? 
					paramMap.get(personId + "_MEAL_COUNT").toString(): "";
			String mealRemark = paramMap.get(personId + "_MEAL_REMARK") != null ? 
					paramMap.get(personId + "_MEAL_REMARK").toString(): "";

			paramMap.put("FROM_DATE", fromDate);
			paramMap.put("TO_DATE", toDate);
			//添加开始之前先验证日期，食堂刷卡信息只允许添加今天和今天之后的日期，不允许添加以前的数据
			Date ar_date_str = null;
			SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");   
			ar_date_str = formatDate.parse(fromDate+" 23:59:59");
			//指定日期同今天进行比较
			int compareResult = ar_date_str.compareTo(new Date());
			//如果要删除的日期比今天要早，则不允许添加
			if(compareResult < 0){
				result = "-3";
				return result;
			}
			//先判断从   开始日期  到  结束日期  之间，该员工是否有已经锁定信息，如果有，则停止添加、返回结果
			paramMap.put("LOCK_FLAG", "Y");//验证的是已锁定的信息
			int eatCnt = arEatMealCountDao.getEmpMealCountByDateCnt(paramMap);
			if(eatCnt>0){
				LinkedHashMap empMap = (LinkedHashMap)arEatMealCountDao.getEmpMealCountByDateList(paramMap).get(0);
				String empid = empMap.get("EMPID")!=null?empMap.get("EMPID").toString():"";
				String name = empMap.get("LOCAL_NAME")!=null?empMap.get("LOCAL_NAME").toString():"";
				String arDateStr = empMap.get("AR_DATE_STR")!=null?empMap.get("AR_DATE_STR").toString():"";
				result = "-12:" + empid+name+" "+arDateStr;//员工XXXX年XX月XX日信息锁定，不允许再添加！
				return result;
			}
			//日期循环
			List<LinkedHashMap> dateList = new ArrayList<LinkedHashMap>();
			try {
				dateList = (List<LinkedHashMap>)this.arEatMealCountDao.getInitDateList(paramMap);
			} catch (Exception e) {
				result = "-4";//循环日期前，获取日期list出错
				e.printStackTrace();
			}
			for (int i = 0; i < dateList.size(); i++) {
				LinkedHashMap dateMap = (LinkedHashMap) dateList.get(i);
				String arDateStr = dateMap.get("FROM_DATE") != null ?dateMap.get("FROM_DATE").toString(): "";
				
				dateMap.put("PERSON_ID", personId);
				dateMap.put("AR_DATE_STR", arDateStr);
				dateMap.put("MEAL_NUM", mealCount);
				dateMap.put("REMARK", mealRemark);
				dateMap.put("LOCK_FLAG", "Y");//手动添加的信息，默认锁定
				dateMap.put("CPNY_ID", admin.getCpnyId().toString());
				dateMap.put("CREATED_BY", admin.getPersonId());

				batchEatMealCountList.add(dateMap);
			}
		}
		try {
			this.arEatMealCountDao.initMealCount(batchEatMealCountList);
		} catch (Exception e) {
			result = "-2";//循环日期后，批量插入时出错
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * 删除食堂刷卡次数信息
	 * 
	 * @param request
	 * @return
	 * @throws ParseException 
	 * @throws Exception
	 */
	@SuppressWarnings({ "unused", "unchecked" })
	@Override
	public int deleteArEatCountInfo(HttpServletRequest request) throws ParseException{
		//获取页面参数
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		
		int result = 1;
		LinkedHashMap empMap = (LinkedHashMap)arEatMealCountDao.getEmpMealCountByDateList(paramMap).get(0);
		String arDateStr = empMap.get("AR_DATE_STR")!=null?empMap.get("AR_DATE_STR").toString():"";
		Date ar_date_str = null;
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");   
		ar_date_str = formatDate.parse(arDateStr+" 23:59:59");
		//指定日期同今天进行比较
		int compareResult = ar_date_str.compareTo(new Date());
		//如果要删除的日期比今天要早，则不允许删除
		if(compareResult < 0){
			result = -3;
		}else{
			try {
				this.arEatMealCountDao.deleteArEatCountInfo(paramMap);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * 查找指定日期里可添加食堂就餐打卡信息的员工名单(get the person info List for add eat meal count),分页显示
	 * 
	 * @param request
	 * @return List
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List getEmpMealCountByDateList(HttpServletRequest request) {
		List retrunList = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");

		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("ADMIN_ID", admin.getAdminID());

		retrunList = arEatMealCountDao.getEmpMealCountByDateList(paramMap);
		
		return retrunList;
	}
	
	/**
	 * 删除食堂刷卡次数信息
	 * 
	 * @param request
	 * @return
	 * @throws ParseException 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int updateArEatCountInfo(HttpServletRequest request) throws ParseException{
		//获取页面参数
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("UPDATED_BY", admin.getPersonId());
		int result = 1;
		LinkedHashMap empMap = (LinkedHashMap)arEatMealCountDao.getEmpMealCountByDateList(paramMap).get(0);
		String arDateStr = empMap.get("AR_DATE_STR")!=null?empMap.get("AR_DATE_STR").toString():"";
		Date ar_date_str = null;
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");   
		ar_date_str = formatDate.parse(arDateStr+" 23:59:59");
		//指定日期同今天进行比较
		int compareResult = ar_date_str.compareTo(new Date());
		//如果要修改的日期比今天要早，则不允许修改
		if(compareResult < 0){
			result = -3;
		}else{
			try {
				this.arEatMealCountDao.updateArEatCountInfo(paramMap);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}