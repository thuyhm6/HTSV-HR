package com.ait.pa.service.imp.salarycode;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ait.hrm.dao.JobTypeDao;
import com.ait.hrm.service.JobTypeSer;
import com.ait.pa.dao.FSESalaryItemDao;
import com.ait.pa.service.salarycode.FSESalaryItemSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;

@Service
public class FSESalaryItemSerImpl implements FSESalaryItemSer{

	Logger logger = Logger.getLogger(FSESalaryItemSerImpl.class);
	
	@Autowired
	public FSESalaryItemDao fseSalaryDao;

	@SuppressWarnings("unchecked")
	@Override
	public List getFSESalaryItemList(HttpServletRequest request) {
		List retrunList = new ArrayList() ;
		Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
		paramMap.put("interLanguage", "zh");
		paramMap.put("PERSON_ID",admin.getPersonId());
		String CPNY = request.getParameter("defaultCpny");
		if(CPNY == null || "".equals(CPNY)){
			paramMap.put("CPNY", admin.getCpnyId());
			paramMap.put("CPNY_ID",admin.getCpnyId());
		}else{
			paramMap.put("CPNY",CPNY);
			paramMap.put("CPNY_ID",CPNY);
		}
		if (UiUtil.getPageNum(request) > 0){
			retrunList = fseSalaryDao.getFSESalaryList(paramMap ,UiUtil.getPageNum(request), UiUtil.getNumPerPage(request)) ;
		}else{
			retrunList = fseSalaryDao.getFSESalaryList(paramMap) ;
		}
		return retrunList ;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getFSESalaryItemCnt(HttpServletRequest request) {
        Map paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
        AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
        paramMap.put("interLanguage", "zh");
		paramMap.put("PERSON_ID",admin.getPersonId());
        String CPNY = request.getParameter("defaultCpny");
		if(CPNY == null || "".equals(CPNY)){
			paramMap.put("CPNY", admin.getCpnyId());
			paramMap.put("CPNY_ID",admin.getCpnyId());
		}else{
			paramMap.put("CPNY",CPNY);
			paramMap.put("CPNY_ID",CPNY);
		}
		return fseSalaryDao.getFSESalaryCnt(paramMap) ;
	}

	/**
	 * 获取人员类型导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getJobTypeTempList(HttpServletRequest request){
		List retrunList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		if (UiUtil.getPageNum(request) > 0) {
			retrunList = fseSalaryDao.getFSESalaryTempList(paramMap, UiUtil
					.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = fseSalaryDao.getFSESalaryTempList(paramMap);
		}
		return retrunList;
	}
	
	/**
	 * 获取人员类型导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getJobTypeTempCnt(HttpServletRequest request, String errorFlag){
		int retrunInt = 0;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		if("E".equals(errorFlag)){
			retrunInt = fseSalaryDao.getFSESalaryTempErrorCnt(paramMap);
		}else{
			retrunInt = fseSalaryDao.getFSESalaryTempCnt(paramMap);
		}
		return retrunInt;
	}
	
	/**
	 * 人员类型excel信息提交
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public String submitImportExcelFSESalaryData(HttpServletRequest request){
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("PR_NAME", "pkg_fsesalary_excel_imp.pr_import_temp_fsesalary_data");
		try {
			return this.fseSalaryDao.importFSESalaryFromExcel(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
	
	/**
	 * 获取FSE人员工资导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getFSESalaryImportTempList(HttpServletRequest request){
		List retrunList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		if (UiUtil.getPageNum(request) > 0) {
			retrunList = fseSalaryDao.getFSESalaryImportTempList(paramMap, UiUtil
					.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = fseSalaryDao.getFSESalaryImportTempList(paramMap);
		}
		return retrunList;
	}
	
	/**
	 * 获取FSE人员工资导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getFSESalaryImportTempCnt(HttpServletRequest request, String errorFlag){
		int retrunInt = 0;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		if("E".equals(errorFlag)){
			retrunInt = fseSalaryDao.getFSESalaryImportTempErrorCnt(paramMap);
		}else{
			retrunInt = fseSalaryDao.getFSESalaryImportTempCnt(paramMap);
		}

		return retrunInt;
	}
	
	/**
	 * 组装模版信息,导出带错误提示的数据
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public String getFSESalarylateInfoByExcelData(HttpServletRequest request, List aliasNameList, List list,List mapList,List mapNameList) throws SQLException{

		LinkedHashMap paramMap = new LinkedHashMap();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("interCpnyID", admin.getCpnyId());
		paramMap.put("interLanguage", admin.getLanguage());
		
		//模版名称
		String name = "";
		aliasNameList.add("公司*");
		aliasNameList.add("社号*");
		aliasNameList.add("工资月*");
		aliasNameList.add("人员类型*");
		aliasNameList.add("PA_AREA_CD*");
		aliasNameList.add("工资项目*");
		aliasNameList.add("项目金额*");
		aliasNameList.add("ADD_FLAG*");
		aliasNameList.add("验证结果");
		List fseSalaryTempList = this.fseSalaryDao.getFSESalaryImportTempList(paramMap, -1, -1);
		for(int i=0;i<fseSalaryTempList.size();i++){
			LinkedHashMap map = new LinkedHashMap();
			LinkedHashMap map1 = (LinkedHashMap)fseSalaryTempList.get(i);
			map.put("CELL0",map1.get("CPNY_ID") == null ? "" : map1.get("CPNY_ID"));
			map.put("CELL1",map1.get("EMP_ID") == null ? "" : map1.get("EMP_ID"));
			map.put("CELL2",map1.get("PA_MONTH") == null ? "" : map1.get("PA_MONTH"));
			map.put("CELL3",map1.get("EMP_TYPE_CODE") == null ? "" : map1.get("EMP_TYPE_CODE"));
			map.put("CELL4",map1.get("PA_AREA_CD") == null ? "" : map1.get("PA_AREA_CD"));
			map.put("CELL5",map1.get("SALARY_ITEM") == null ? "" : map1.get("SALARY_ITEM"));
			map.put("CELL6",map1.get("SALARY_ITEM_FEE") == null ? "" : map1.get("SALARY_ITEM_FEE"));
			map.put("CELL7",map1.get("ADD_FLAG") == null ? "" : map1.get("ADD_FLAG"));
			map.put("CELL8", map1.get("UPLOAD_ERROR_MSG") == null ? "" : map1.get("UPLOAD_ERROR_MSG"));
			list.add(map);
			//mapNameList.add("公司(法人)参考");
//			mapNameList.add(admin.getCpnyId()+"计算项目参考");
//			mapNameList.add(admin.getCpnyId()+"输入项目参考");
//			mapNameList.add(admin.getCpnyId()+"基础项目参考");
//			
			//String cpnySql = "SELECT T.CPNY_ID CONTENT, T.CPNY_LOCATION CONTENT1 FROM HR_COMPANY T WHERE T.ACTIVITY =1";
//			String salarySql = "SELECT T.ITEM_ID CONTENT, SY.CONTENT CONTENT1 FROM PA_ITEM T,SY_GLOBAL_NAME SY,PA_ITEM_PARAM PT WHERE T.ITEM_NO = PT.ITEM_NO AND T.ITEM_NO = SY.NO AND SY.LANGUAGE = 'zh' AND PT.CPNY_ID = '"+admin.getCpnyId()+"'";
//			String salarySql1 = "SELECT T.PARAM_ITEM_ID CONTENT, SY.CONTENT CONTENT1 FROM PA_PARAM_ITEM T,SY_GLOBAL_NAME SY,PA_PARAM_ITEM_PARAM PT WHERE T.PARAM_ITEM_NO = PT.PARAM_ITEM_NO AND T.PARAM_ITEM_NO = SY.NO AND SY.LANGUAGE = 'zh' AND PT.CPNY_ID = '"+admin.getCpnyId()+"'";
//			String salarySql2 = "SELECT T.ITEM_ID CONTENT, SY.CONTENT CONTENT1 FROM PA_BASIC_ITEM T,SY_GLOBAL_NAME SY,PA_BASIC_ITEM_PARAM PT WHERE T.ITEM_NO = PT.ITEM_NO AND T.ITEM_NO = SY.NO AND SY.LANGUAGE = 'zh' AND PT.CPNY_ID = '"+admin.getCpnyId()+"'";
			//mapList.add(cpnySql);
//			mapList.add(salarySql);
//			mapList.add(salarySql1);
//			mapList.add(salarySql2);
			name = "payFSE_ATM_Import_result";
		}
		return name;
	}
}
 