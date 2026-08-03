package com.ait.pa.dao.tempsale;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
/**
 * 临促工资
 * 
 * @author weizhengchen@ait.net.cn
 * @date 2014-7-03
 * @version V1.0
 * 
 */
@SuppressWarnings("unchecked")
public interface PaTempSalesDAO {

	/**
	 * 获取临促工资编号
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public String getPaTempSalesSeq() throws Exception;
	
	/**
	 * 获取临促工资
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getPaTempSalesList(Object object);

	/**
	 * 获取临促工资数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getPaTempSalesCnt(Object object);

	/**
	 * 获取需要决裁的临促工资数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getTempSalesAffirmCnt(Object object);
	
	/**
	 * 获取临促工资人员信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getPaTempSalesEmpInfoCnt(Object object);
	
	/**
	 * 分页获取临促工资
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getPaTempSalesList(Object object, int currentPage, int pageSize);
	
	/**
	 * 获取需要裁决的临促信息
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	public List getTempSalesAffirmList(Object object);
	
	/**
	 * 获取需要裁决的临促信息
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	public List getTempSalesAffirmList(Object object, int currentPage, int pageSize);
	/**
	 * 分页获取临促工资人员信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getPaTempSalesEmpInfoList(Object object, int currentPage, int pageSize);
	
	/**
	 * 获取临促工资人员信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getPaTempSalesEmpInfoList(Object object);
	/**
	 * 根据eventid获取临促信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-07
	 * @version V1.0
	 */
	public LinkedHashMap getTempSalesEmpInfoByEventId(Object object);
	
	/**
	 * 根据infono获取临促人员信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-07
	 * @version V1.0
	 */
	public LinkedHashMap getTempSalesEmpInfoByInfoNo(Object object);
	
	/**
	 * 新增临促工资
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public void insertPaTempSales(Object object) throws Exception;

	/**
	 * 新增临促工资人员信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public void insertPaTempSalesEmpInfo(Object object) throws Exception;
	
	/**
	 * 更新临促工资
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public void updatePaTempSales(Object object) throws Exception;

	/**
	 * 删除临促工资
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public void deletePaTempSales(Object object) throws Exception;
	
	/**
	 * 添加临促决裁者
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public void insertAffirmor(Object object)  throws Exception;
	
	/**
	 * 获取决裁情况
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getAffirmorList(Object object);
	
	/**
	 * 获取check
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getCheckList(Object object);
	
	/**
	 * 批量提交未提交的临促工资项目
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public void submitTempSalary(Object object) throws Exception;/**
	/**
	 * 批量删除临促工资项目
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public void deleteTempSalary(Object object) throws Exception;
	
	/**
	 * 删除临促决裁者
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public void deleteAffirmor(Object object)  throws Exception;

	public List getExcelMessage(LinkedHashMap paramMap);

	public int saveWageApplication(LinkedHashMap paramMap);

	public int deleteWageApplicationDetail(LinkedHashMap paHashMap);
	
	/**
	 * 人员信息变动后自动更新EVNET人员，工资信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-07
	 * @version V1.0
	 */
	public void updateEventInfoByEmoInfo(Object object)  throws Exception;
	
	/**
	 * 更新临促人员信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public void updatePaTempSalesEmpInfo(Object object) throws Exception;
	
	/**
	 * 删除临促工人员信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public void deletePaTempSalesEmpInfo(Object object)  throws Exception;
	
	/**
	 * 添加Check人
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-07
	 * @version V1.0
	 */
	public void addCheckPaTempSales(Object object)  throws Exception;
	
	/**
	 * 决裁临促工资项目
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public void affirmPaTempSales(Object object)  throws Exception;
	
	/**
	 * 修改决裁状态
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public void updateEssAffirm(Object object) throws Exception;
	
	/**
	 * 修改决裁状态
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public void updatePaTempSalesStatus(Object object) throws Exception;
	
	/**
	 * 修改check信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public void updateEssCheck(Object object) throws Exception;
	
	
	/**
	 * 获取需要check的临促信息
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	public List getTempSalesCheckList(Object object);
	
	/**
	 * 获取需要check的临促信息
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	public List getTempSalesCheckList(Object object, int currentPage, int pageSize);
	
	/**
	 * 获取check信息数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getPaTempSalesCheckCnt(Object object);
	
	/**
	 * 根据裁决no修改check FLAG
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public void updateCheckFlagByEssAffirmNo(Object object)  throws Exception;
	
	/**
	 * 获取临促导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getTempSalesTempList(Object object);
	
	/**
	 * 获取临促导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getTempSalesTempList(Object object, int currentPage, int pageSize);
	
	/**
	 * 获取临促导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getTempSalesTempCnt(Object object);
	
	/**
	 * 获取出错的临促导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getTempSalesTempErrorCnt(Object object);
	
	/**
	 * 获取临促人员导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getTempSalesEmpTempList(Object object);
	
	/**
	 * 获取临促人员导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getTempSalesEmpTempList(Object object, int currentPage, int pageSize);
	
	/**
	 * 获取临促人员导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getTempSalesEmpTempCnt(Object object);
	
	/**
	 * 获取出错的临促人员导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getTempSalesEmpTempErrorCnt(Object object);
	
	/**
	 * 数据导入(验证通过后--从临时表导入到正式表)
	 * @param 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String importFromExcel(Object object)  throws Exception;
	
	/**
	 * 派遣津贴标准导入
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String submitImportExcelTempPqdJtbzData(Object object)  throws Exception;
	
	@SuppressWarnings("unchecked")
	public String submitImportExcelTempPqdGuanLiData(Object object)  throws Exception;
	
	@SuppressWarnings("unchecked")
	public String submitImportExcelTempZuiDiGongZiBiaoZhunFeiCuXiaoYuanData(Object object)  throws Exception;
	
	@SuppressWarnings("unchecked")
	public String submitImportExcelTempZuiDiGongZiBiaoZhunCuXiaoYuanData(Object object)  throws Exception;
	
	/**
	 * 获取门店信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getSpmsShopList(Object object);
	
	/**
	 * 根据部门id获取共同社编
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-07
	 * @version V1.0
	 */
	public LinkedHashMap getCommonEmpId(Object object);
	
	public int getPaTempSalesEmpCnt(Object object);
	
	public List getPaTempSalesEmpList(Object object, int currentPage, int pageSize);
	
	public List getPaTempSalesEmpList(Object obj);
	
	/**
	 * 根据IdCard获取临促人员信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-07
	 * @version V1.0
	 */
	public LinkedHashMap getTempSalesEmpInfoByIdCard(LinkedHashMap object);
	
	/**
	 * 更新临促人员信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public void updatePaTempSalesEmp(Object object) throws Exception;
	
	/**
	 * 临促汇总明细查询
	 */
	public List getPaTempSalesSummaryList(Object obj) ;
	public List getPaTempSalesSummaryList(Object object, int currentPage, int pageSize);
	public int getPaTempSalesSummaryCnt(Object object);
	
	/**
	 * 更新临促人员信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public String viewTempSaleSummary(Object object) throws Exception;

	/**
	 * 获取决裁线
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getAffirmorInfo(Object object) ;
	
	public List getTempSalesAccuralInfoList(Object obj);
	
	public List getTempSalesAccuralInfoList(Object object, int currentPage, int pageSize);
	
	public int getTempSalesAccuralInfoCnt(Object object);
	/**
	 * 根据eventid获取临促信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-07
	 * @version V1.0
	 */
	public LinkedHashMap getTempSalesInfoByEventId(Object object);
	/**
	 * 获取支社列表
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getBranchList(Object object);
	
	/**
	 * 获取check
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getCheckListToLgep(Object object);
	/**
	 * 临促工资发送财务
	 */
	public List getPaTempSalesSendList(Object obj);
	public List getPaTempSalesSendList(Object object, int currentPage, int pageSize);
	public int getPaTempSalesSendCnt(Object object);
	/**
	 * 临促传送财务
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public String viewTempSaleSend(Object object) throws Exception;
	/**
	 * 发送给大区担当审批
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public String viewTempSaleConfirm(Object object) throws Exception;
	
	/**
	 * 临促月别总计
	 * @param request
	 * @return
	 */
	public List getTempSalesSum(Object object) ;
	
	/**
	 * 审批成功，更新progress
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public void updateTempSalesProgress(Object object)  throws Exception;
	
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
	public List viewTempSaleState(Object object);
	
	/**
	 * 手机项目直接提交，无需审批
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public void updateTsmStatus(Object object) throws Exception;
	
	/**
	 * 手机项目直接提交，无需审批
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public void updateTsmStatusAffirm(Object object) throws Exception;
	
	/**
	 * 查看是否是手机项目
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int isTsm(Object object);
	/**
	 * 获取支社列表
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getBranchListHR(Object object);
	
	/**
	 * 预提添加附件信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public void updateYuTy(Object object);
	
	/**
	 * 根据月份、大区、预提标示 获取临促汇总信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-07
	 * @version V1.0
	 */
	public LinkedHashMap getTempSalesSummary(Object object);
}