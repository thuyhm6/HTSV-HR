package com.ait.pa.service.salarycode;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import com.ait.sys.bean.AdminBean;
import com.ait.web.util.SessionUtil;

public interface FSESalaryItemSer {

	/**
	 * 分页查看法人、  职种（人员类型组）、和人员类型的对应关系   页面只显示职种和人员类型的对应关系
	 * @param paramMap
	 * @param pageNum
	 * @param numPerPage
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getFSESalaryItemList(HttpServletRequest request);

	/**
	 * 同上  只是不分页
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int getFSESalaryItemCnt(HttpServletRequest request);

	/**
	 * 获取人员类型导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getJobTypeTempList(HttpServletRequest request) ;
	
	/**
	 * 获取人员类型导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getJobTypeTempCnt(HttpServletRequest request, String errorFlag);
	
	/**
	 * 人员类型excel信息提交
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public String submitImportExcelFSESalaryData(HttpServletRequest request);
	
	/**
	 * 获取FSE人员工资导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getFSESalaryImportTempList(HttpServletRequest request) ;
	
	/**
	 * 获取FSE人员工资导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getFSESalaryImportTempCnt(HttpServletRequest request, String errorFlag);
	
	/**
	 * 组装FSE人员工资导入模版信息,导出带错误提示的数据
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public String getFSESalarylateInfoByExcelData(HttpServletRequest request, List aliasNameList, List list,List mapList,List mapNameList) throws SQLException;
}
