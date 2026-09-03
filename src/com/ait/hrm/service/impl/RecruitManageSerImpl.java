package com.ait.hrm.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ait.hrm.dao.EmpInfoDao;
import com.ait.hrm.dao.RecruitManageDao;
import com.ait.hrm.service.RecruitManageSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.DateUtil;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;

@Service
public class RecruitManageSerImpl implements RecruitManageSer {
	Logger logger = Logger.getLogger(RecruitManageSerImpl.class);

	@Autowired
	private RecruitManageDao recruitManageDao;
	@Autowired
	private EmpInfoDao empInfoDao;

	public List viewRecruitList(HttpServletRequest request) {
		List list = new ArrayList();
		LinkedHashMap param = ObjectBindUtil.getRequestParamData(request,"seach_");
		String data_sources = StringUtil.checkNull(request.getParameter("seach_DATA_SOURCES"));
		if (data_sources == null || data_sources.equals("")) {
			data_sources = "'1','0'";
		}
		param.put("DATA_SOURCES", data_sources);
		list = recruitManageDao.viewRecruitList(param);
		return list;
	}
	
	/**
	 * 新增招聘信息
	 */
	@SuppressWarnings("unchecked")
	public int addRecruitInfo(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		
		try {
			this.recruitManageDao.addRecruitInfo(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 更新招聘发令照片
	 */
	@SuppressWarnings("unchecked")
	public int updateRecruitPhotoInfo(HttpServletRequest request, String photoPath) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("PHOTO_PATH", photoPath);
		paramMap.put("currentIndex", 5);
		try {
			this.recruitManageDao.addRecruitInfo(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	/**
	 * 删除招聘发令
	 */
	@SuppressWarnings("unchecked")
	public int deleteRecruitInfo(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		try {
			this.recruitManageDao.deleteRecruitInfo(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 工作经历管理
	 */
	public List viewWorkExperienceList(HttpServletRequest request) {
		List list = new ArrayList();
		LinkedHashMap param = ObjectBindUtil.getRequestParamData(request,"seach_");
		list = recruitManageDao.viewWorkExperienceList(param);
		return list;
	}
	
	/**
	 * 教育经历管理
	 */
	public List viewEducationList(HttpServletRequest request) {
		List list = new ArrayList();
		LinkedHashMap param = ObjectBindUtil.getRequestParamData(request,"seach_");
		list = recruitManageDao.viewEducationList(param);
		return list;
	}

	/**
	 * 家庭信息管理
	 */
	public List viewFamilyList(HttpServletRequest request) {
		List list = new ArrayList();
		LinkedHashMap param = ObjectBindUtil.getRequestParamData(request,"seach_");
		list = recruitManageDao.viewFamilyList(param);
		return list;
	}
	
	/**
	 * 招聘发令确认 、拉回
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String executeRecruit(HttpServletRequest request) {
		String returnString = "" ;		
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		try {
			returnString = this.recruitManageDao.executeRecruit(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
		return returnString;
	}
	
	/**
	 * 执行sql
	 */
	public List doSql(HttpServletRequest request) {
		List list = new ArrayList();
		LinkedHashMap param = ObjectBindUtil.getRequestParamData(request,"seach_");
		list = recruitManageDao.doSql(param);
		return list;
	}
	
	/**
	 * 新增招聘注册信息
	 */
	@SuppressWarnings("unchecked")
	public int addRegisterInfo(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		try {
			this.recruitManageDao.addRegisterInfo(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 获取注册日信息
	 */
	public List viewRegisterInfoList(HttpServletRequest request,int flag) {
		List list = new ArrayList();
		LinkedHashMap param = ObjectBindUtil.getRequestParamData(request,"seach_");
		param.put("FLAG", flag);
		list = recruitManageDao.viewRegisterInfoList(param);
		return list;
	}
	
	/**
	 * 获取招聘发令批量信息
	 */
	public List viewRecruitBatchList(LinkedHashMap paramData) {
		List list = new ArrayList();
		list = recruitManageDao.viewRecruitBatchList(paramData);
		return list;
	}

	/**
	 * 单条删除批量导入的人员信息
	 */
	@SuppressWarnings("unchecked")
	public int deleteRecruitBatchInfo(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		String SEQ = StringUtil.checkNull(paramMap.get("SEQ"));
		String[] SEQP = SEQ.split(",");
		for (String s : SEQP) {
			paramMap.put("SEQ", s);
			try {
				this.recruitManageDao.deleteRecruitBatchInfo(paramMap);
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
		}
		
		return 1;
	}

	/**
	 * 发令概要管理
	 */
	public List viewResumeList(HttpServletRequest request) {
		List list = new ArrayList();
		LinkedHashMap param = ObjectBindUtil.getRequestParamData(request,"seach_");
		list = recruitManageDao.viewResumeList(param);
		return list;
	}

	@SuppressWarnings("unchecked")
	public int addResumeInfo(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		try {
			this.recruitManageDao.addResumeInfo(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	

	@SuppressWarnings("unchecked")
	public int deleteResumeInfo(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		try {
			this.recruitManageDao.deleteResumeInfo(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 获取发令批量信息
	 */
	public List viewExperienceBatchList(LinkedHashMap paramData) {
		List list = new ArrayList();
		list = recruitManageDao.viewExperienceBatchList(paramData);
		return list;
	}

	/**
	 * 单条删除批量导入的发令信息
	 */
	@SuppressWarnings("unchecked")
	public int deleteExperienceBatchInfo(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		try {
			this.recruitManageDao.deleteExperienceBatchInfo(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 批量导入信息修改
	 */
	@SuppressWarnings("unchecked")
	public int addRecruitBatchInfo(HttpServletRequest request) {
		try {
			String jsonString = request.getParameter("jsonData") ;
			List<LinkedHashMap<String, Object>> dataList = ObjectBindUtil.getRequestJsonData(jsonString) ;
			this.recruitManageDao.addRecruitBatchInfo(dataList);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 增加一条空的数据
	 */
	@SuppressWarnings("unchecked")
	public int addEmptyRecruitInfo(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		try {
			this.recruitManageDao.addEmptyRecruitInfo(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 删除空的数据 
	 */
	@SuppressWarnings("unchecked")
	public int deleteEmptyRecruitInfo(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		try {
			this.recruitManageDao.deleteEmptyRecruitInfo(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	@SuppressWarnings("unchecked")
	public int deleteExperienceInfo(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		try {
			this.recruitManageDao.deleteExperienceInfo(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 获取发令信息
	 */
	//public List viewExperienceList(HttpServletRequest request) {
	//	List list = new ArrayList();
	//	Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
	//	list = recruitManageDao.viewExperienceList(paramMap);
	//	return list;
	//}
	
	@SuppressWarnings("unchecked")
	public List viewExperienceList(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String deptno = StringUtil.checkNull(request.getParameter("seach_DEPTNO"));
		String lowerDepart = StringUtil.checkNull(request.getParameter("seach_SON_FLAG"));
		
		String str = "";
		if (!"".equals(deptno)) {
			if (lowerDepart.equals("1")) {
				str = " and B.DEPTNO IN (SELECT DEPTNO  FROM HR_DEPARTMENT START WITH DEPTNO = '"
						+ deptno + "'  CONNECT BY PRIOR DEPTNO=PARENT_DEPT_NO)";
				paramMap.put("DEPTNO", str);
			} else {
				str = " and B.DEPTNO= '" + deptno + "'";
			}
			paramMap.put("deptContent", str);
		}
		String sql = " SELECT A.START_DATE ORDER_DATE,get_global_name(A.TRANS_CODE,'vi') ORDER_TYPE,get_global_name(A.TRANS_RESOURCE,'vi') ORDER_REASON,B.LOCAL_NAME NAME,B.EMPID EMPID,get_dept_name(A.DEPTNO,'vi') DEPARTMENT,get_global_name(A.EMP_TYPE_CODE,'vi') EMPLOYEE_TYPE,get_global_name(A.POST_GRADE_NO,'vi') RANK,get_global_name(A.MAIN_BUSINESS,'vi') MAJOR_BUSINESS FROM HR_EXPERIENCE_INSIDE A,HR_EMPLOYEE B,HR_PERSONAL_INFO C WHERE A.PERSON_ID = B.PERSON_ID AND A.PERSON_ID = C.PERSON_ID AND A.ACTIVITY <> 0 AND B.EMPID NOT LIKE '111111%' AND B.CPNY_ID = '"
					+ admin.getCpnyId()
					+ "'";
		String personid = "";
		if (request.getParameter("PERSON_ID") != null
				&& !"".equals(request.getParameter("PERSON_ID"))) {
			paramMap.put("PERSON_ID", request.getParameter("PERSON_ID"));
			personid = " and A.PERSON_ID='" + request.getParameter("PERSON_ID")
					+ "'";
		}
		String seach_START_DATE = "";
		if (request.getParameter("seach_START_DATE") != null
				&& !"".equals(request.getParameter("seach_START_DATE"))) {
			seach_START_DATE = " and TO_DATE(A.START_DATE,'DD/MM/YYYY')>=TO_DATE('"
					+ request.getParameter("seach_START_DATE") + "','DD/MM/YYYY')";
		}
		String seach_END_DATE = "";
		if (request.getParameter("seach_END_DATE") != null
				&& !"".equals(request.getParameter("seach_END_DATE"))) {
			seach_END_DATE = " and TO_DATE(A.START_DATE,'DD/MM/YYYY')<=TO_DATE('"
					+ request.getParameter("seach_END_DATE") + "','DD/MM/YYYY')";
		}
		String seach_IDCARD_NO = "";
		if (request.getParameter("seach_IDCARD_NO") != null
				&& !"".equals(request.getParameter("seach_IDCARD_NO"))) {
			seach_IDCARD_NO = " and C.IDCARD_NO ='"
					+ request.getParameter("seach_IDCARD_NO") + "'";
		}
		String seach_TRANS_CODE_NAME = "";
		if (request.getParameter("seach_TRANS_CODE_Multi") != null
				&& !"".equals(request.getParameter("seach_TRANS_CODE_Multi"))) {
			seach_TRANS_CODE_NAME = " and A.TRANS_CODE in ("
					+ request.getParameter("seach_TRANS_CODE_Multi") + ")";
		}
		String seach_TRANS_RESOURCE_NAME = "";
		if (request.getParameter("seach_TRANS_RESOURCE_Multi") != null
				&& !"".equals(request.getParameter("seach_TRANS_RESOURCE_Multi"))) {
			seach_TRANS_RESOURCE_NAME = " and A.TRANS_RESOURCE in ("
					+ request.getParameter("seach_TRANS_RESOURCE_Multi") + ")";
		}
		String seach_POST_FAMILY_NAME = "";
		if (request.getParameter("seach_POST_FAMILY_Multi") != null
				&& !"".equals(request.getParameter("seach_POST_FAMILY_Multi"))) {
			seach_POST_FAMILY_NAME = " and B.POST_FAMILY in ("
					+ request.getParameter("seach_POST_FAMILY_Multi") + ")";
		}
		String seach_EMP_TYPE_CODE_NAME = "";
		if (request.getParameter("seach_EMP_TYPE_CODE_Multi") != null
				&& !"".equals(request.getParameter("seach_EMP_TYPE_CODE_Multi"))) {
			seach_EMP_TYPE_CODE_NAME = " and B.EMP_TYPE_CODE in ("
					+ request.getParameter("seach_EMP_TYPE_CODE_Multi") + ")";
		}
		String seach_EMP_OFFICE_NAME = "";
		if (request.getParameter("seach_EMP_OFFICE_Multi") != null
				&& !"".equals(request.getParameter("seach_EMP_OFFICE_Multi"))) {
			seach_EMP_OFFICE_NAME = " and A.EMP_OFFICE in ("
					+ request.getParameter("seach_EMP_OFFICE_Multi") + ")";
		}
		String seach_START_DATE_JOIN = "";
		if (request.getParameter("seach_START_DATE_JOIN") != null
				&& !"".equals(request.getParameter("seach_START_DATE_JOIN"))) {
			seach_START_DATE_JOIN = " and B.DATE_STARTED>=to_date('"
					+ request.getParameter("seach_START_DATE_JOIN") + "','dd/MM/yyyy')";
		}
		String seach_END_DATE_JOIN = "";
		if (request.getParameter("seach_END_DATE_JOIN") != null
				&& !"".equals(request.getParameter("seach_END_DATE_JOIN"))) {
			seach_END_DATE_JOIN = " and B.DATE_STARTED<=to_date('"
					+ request.getParameter("seach_END_DATE_JOIN") + "','dd/MM/yyyy')";
		}
		
		sql = sql  + personid + str + seach_START_DATE + seach_END_DATE 
			+ seach_IDCARD_NO + seach_TRANS_CODE_NAME + seach_POST_FAMILY_NAME
			+ seach_TRANS_RESOURCE_NAME + seach_EMP_TYPE_CODE_NAME
			+ seach_EMP_OFFICE_NAME
			+ seach_START_DATE_JOIN + seach_END_DATE_JOIN + " ORDER BY TO_DATE(A.START_DATE,'DD/MM/YYYY') DESC";
		paramMap.put("SQL_STMT", sql);
		paramMap.put("sql_seq", 151);
		try {
			this.empInfoDao.tiquziliao_new(paramMap);
		} catch (Exception e) {

			e.printStackTrace();
		}
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		return recruitManageDao.viewExperienceList(paramMap);
	}

	/**
	 * 录用发令 判断社号是否存在
	 */
	public int IS_EXISTS_EMPID(HttpServletRequest request) {
		int cnt = 0;
		LinkedHashMap param = ObjectBindUtil.getRequestParamData(request,"seach_");

		if(param.get("PERSON_ID")== null || param.get("PERSON_ID")== ""){
			param.put("PERSON_ID","000000000");
		}
		cnt = recruitManageDao.IS_EXISTS_EMPID(param);
		return cnt;
	}
	
	/**
	 * 统一录用发令 判断社号是否存在
	 */
	public int IS_EXISTS_EMPID1(HttpServletRequest request) {
		int cnt = 0;
		List list = new ArrayList();
		//LinkedHashMap param = ObjectBindUtil.getRequestParamData(request,"seach_");
		String jsonString = request.getParameter("jsonData") ;
		List<LinkedHashMap<String, Object>> dataList = ObjectBindUtil.getRequestJsonData(jsonString) ;
		LinkedHashMap param = new LinkedHashMap();
		LinkedHashMap param1 = new LinkedHashMap();
		for (int i = 0; i < dataList.size(); i ++) {
			list = recruitManageDao.isExistsEmpidRecruitBatch(dataList.get(i));
			cnt = recruitManageDao.IS_EXISTS_EMPID(dataList.get(i));
			param = (LinkedHashMap)dataList.get(i);
			String empid = StringUtil.checkNull(param.get("EMPID"));
			String seq = StringUtil.checkNull(param.get("SEQ"));
			if (list.size() == 0) {
				break;
			} else {
				param1 = (LinkedHashMap)list.get(0);
				String empidResult = StringUtil.checkNull(param1.get("EMPID"));
				String seqResult = StringUtil.checkNull(param1.get("SEQ"));
				if (empid.equals(empidResult)) {
					if (seq.equals(seqResult)) {
						cnt = 0;
					} else {
						cnt = 1;
						break;}
				}
			}
		}
		return cnt;
	}
	
	/**
	 * 判断姓名是否存在
	 */
	public int IS_EXISTS_NAME(HttpServletRequest request) {
		int cnt = 0;
		LinkedHashMap param = ObjectBindUtil.getRequestParamData(request,"seach_");
		cnt = recruitManageDao.IS_EXISTS_NAME(param);
		return cnt;
	}

	/**
	 * 主要业务说明书
	 */
	public List viewMainBusinessList(HttpServletRequest request) {
		List list = new ArrayList();
		LinkedHashMap param = ObjectBindUtil.getRequestParamData(request,"seach_");
		list = recruitManageDao.viewMainBusinessList(param);
		return list;
	}

	@SuppressWarnings("unchecked")
	public int addMainBusinessInfo(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		try {
			this.recruitManageDao.addMainBusinessInfo(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 验证身份证号码是否重复
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String PR_VALID_EMP_DATA(HttpServletRequest request) {
		String returnString = "" ;		
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		try {
			returnString = this.recruitManageDao.PR_VALID_EMP_DATA(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
		return returnString;
	}
	
	/**
	 * 获取供人公司
	 */
	public List viewPersonSupplier(LinkedHashMap paramData) {
		List list = new ArrayList();
		list = recruitManageDao.viewPersonSupplier(paramData);
		return list;
	}
	
	/**
	 * 删除空的数据 
	 */
	@SuppressWarnings("unchecked")
	public int deleteEmptyPersonSupplier(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		try {
			this.recruitManageDao.deleteEmptyPersonSupplier(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 增加一条空的数据
	 */
	@SuppressWarnings("unchecked")
	public int addEmptyPersonSupplier(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		try {
			this.recruitManageDao.addEmptyPersonSupplier(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 供人公司修改
	 */
	@SuppressWarnings("unchecked")
	public int addPersonSupplier(HttpServletRequest request) {
		try {
			String jsonString = request.getParameter("jsonData") ;
			List<LinkedHashMap<String, Object>> dataList = ObjectBindUtil.getRequestJsonData(jsonString) ;
			this.recruitManageDao.addPersonSupplier(dataList);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 单条删除公司信息
	 */
	@SuppressWarnings("unchecked")
	public int deletePersonSupplierInfo(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		String activity = StringUtil.checkNull(request.getParameter("ACTIVITY"));
		if (activity == "1" || "1".equals(activity)) {
			activity = "2";
		}else {activity = "1";}
		try {
			this.recruitManageDao.deletePersonSupplierInfo(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
}
