package com.ait.pa.service.tempsale;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

@SuppressWarnings("unchecked")
public interface PaTempSalesSer {
	public List getTempSalesList(HttpServletRequest request,String accuralFlag) ;
	public List getTempSalesAffirmList(HttpServletRequest request,String accuralFlag);
	public int getTempSalesCnt(HttpServletRequest request,String accuralFlag);
	public int getTempSalesAffirmCnt(HttpServletRequest request,String accuralFlag);
	
	public List getTempSalesEmpInfoList(HttpServletRequest request) ;

	public int getTempSalesEmpInfoCnt(HttpServletRequest request);
	
	/**
	 * 获取临促工资编号
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public String getPaTempSalesSeq(HttpServletRequest request);
	
	/**
	 * 添加临促工工资
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int addPaTempSales(HttpServletRequest request) ;
	/**
	 * 添加临促工人员信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int addPaTempSalesEmpInfo(HttpServletRequest request) ;
	
	public List getAffirmorList(HttpServletRequest request) throws Exception;
	
	/**
	 * 获取决裁情况
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getAffirmorListByEventId(HttpServletRequest request);
	
	/**
	 * 获取check信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getCheckListByEventId(HttpServletRequest request);
	
	/**
	 * 批量提交未提交的临促工资项目
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int submitTempSalary(HttpServletRequest request);
	/**
	 * 根据编号获取对应临促信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getApplyFeeList(HttpServletRequest request) throws Exception;

	public LinkedHashMap getTempSalesEmpInfoByEventId(HttpServletRequest request) ;
	
	/**
	 * 根据编号获取对应临促人员信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public LinkedHashMap getTempSalesEmpInfoByInfoNo(HttpServletRequest request) ;
	
	/**
	 * 修改临促工工资
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int updatePaTempSales(HttpServletRequest request) ;
	
	/**
	 * 组装临促模版信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public String getTemplateInfo(HttpServletRequest request, List aliasNameList, List list,List mapList,List mapNameList) throws SQLException;
	
	/**
	 * 组装临促模版信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public String getTemplateInfoByExcelData(HttpServletRequest request, List aliasNameList, List list,List mapList,List mapNameList) throws SQLException;
	/**
	 * 获取Excel上传的数据
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author yuanxq@ait.net.cn
	* @date 2014-7-8 上午09:40:51 
	* @version V1.0
	 */
	public List getExcelMessage(HttpServletRequest request);

	public int saveWageApplication(HttpServletRequest request);

	public int deleteWageApplicationDetail(HttpServletRequest request);
	
	/**
	 * 修改临促工人员信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int updatePaTempSalesEmpInfo(HttpServletRequest request) ;
	
	/**
	 * 删除临促工人员信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int deletePaTempSalesEmpInfo(HttpServletRequest request);
	
	/**
	 * 添加Check人
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int addCheckPaTempSales(HttpServletRequest request);
	
	/**
	 * 决裁临促工资项目
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int affirmPaTempSales(HttpServletRequest request);
	
	/**
	 * check临促工资项目
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int checkPaTempSales(HttpServletRequest request);
	
	/**
	 * check信息列表
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getTempSalesCheckList(HttpServletRequest request,String accuralFlag);
	
	/**
	 * check信息列表
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getTempSalesCheckCnt(HttpServletRequest request,String accuralFlag);
	
	/**
	 * 获取临促导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getTempSalesTempList(HttpServletRequest request);
	
	/**
	 * 获取临促导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getTempSalesTempCnt(HttpServletRequest request, String errorFlag);
	
	/**
	 * 获取临促导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getTempSalesTempEmpList(HttpServletRequest request) ;
	
	/**
	 * 获取临促导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getTempSalesEmpTempCnt(HttpServletRequest request, String errorFlag);
	
	/**
	 * 临促工资excel信息提交
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public String submitImportExcelTempSalesData(HttpServletRequest request);
	
	/**
	 * 派遣津贴标准提交
	 * @param request
	 * @return
	 */
	public String submitImportExcelTempPqdJtbzData(HttpServletRequest request);
	
	public String submitImportExcelTempPqdGuanLiData(HttpServletRequest request);
	
	public String submitImportExcelTempZuiDiGongZiBiaoZhunFeiCuXiaoYuanData(HttpServletRequest request);
	
	public String submitImportExcelTempZuiDiGongZiBiaoZhunCuXiaoYuanData(HttpServletRequest request);
	
	/**
	 * 临促工资人员excel信息提交
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public String submitImportExcelTempSalesEmpData(HttpServletRequest request);
	/**
	 * 判断是否有促销人员为空的信息提交
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int checkTempSalesCount(HttpServletRequest request);
	
	/**
	 * 获取门店信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getSpmsShopList(HttpServletRequest request);
	
	/**
	 * 根据部门id获取共同社编
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-07
	 * @version V1.0
	 */
	public LinkedHashMap getCommonEmpId(HttpServletRequest request);
	
	public List getTempSalesEmpList(HttpServletRequest request) ;
	
	public int getTempSalesEmpCnt(HttpServletRequest request);
	
	/**
	 * 根据身份证号获取对应临促人员信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public LinkedHashMap getTempSalesEmpInfoByIdCard(HttpServletRequest request);
	/**
	 * 修改临促工人员信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int updatePaTempSalesEmp(HttpServletRequest request) ;
	
	public List getTempSalesSummaryList(HttpServletRequest request) ;
	public int getTempSalesSummaryCnt(HttpServletRequest request) ;
	/**
	 * 临促汇总
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public String viewTempSaleSummary(HttpServletRequest request);
	
	public List getTempSalesAccuralInfoList(HttpServletRequest request);
	
	public int getTempSalesAccuralInfoCnt(HttpServletRequest request);
	
	/**
	 * 根据eventid获取临促信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-07
	 * @version V1.0
	 */
	public LinkedHashMap getTempSalesInfoByEventId(HttpServletRequest request);
	/**
	 * 获取支社列表
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public List getBranchList(HttpServletRequest request) throws Exception ;
	
	/**
	 * 临促工资发送财务
	 * @param request
	 * @return
	 */
	public List getTempSalesSendList(HttpServletRequest request,String accrualFlag);
	
	/**
	 * 临促工资发送财务数量
	 * @param request
	 * @return
	 */
	public int getTempSalesSendCnt(HttpServletRequest request,String accrualFlag);
	
	/**
	 * 临促传送财务
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public String viewTempSaleSend(HttpServletRequest request);
	
	/**
	 * 发送数据给大区担当确认
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public String viewTempSaleConfirm(HttpServletRequest request);
	
	/**
	 * 临促月别总计
	 * @param request
	 * @return
	 */
	public LinkedHashMap getTempSalesSum(HttpServletRequest request,String accrualFlag) ;
	
	/**
	 * 临促工资状态查看
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	public List viewTempSaleState(HttpServletRequest request) ;
	
	/**
	 * 获取支社列表(人事权限)
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public List getBranchListHR(HttpServletRequest request) throws Exception ;
	/**
	 * 根据月份、大区、预提标示 获取临促汇总信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-07
	 * @version V1.0
	 */
	public LinkedHashMap getTempSalesSummaryInfo(HttpServletRequest request);
	
	/**
	 * 获取决裁情况
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getAffirmorListByEventId(String eventId) ;
	
	/**
	 * 获取check信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getCheckListByEventId(String eventId);
}
