package com.ait.hrm.service;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: CycleSer.java
 * @Description: implement Class ContractInfoSerImp.java
 * @Create date: Jan 6, 2012 4:17:57 PM
 * @Create by: liyy(liyueyang@ait.net.cn)
 * @version 5.1
 */
public interface ContractInfoSer {
	@SuppressWarnings("unchecked")
	public List getContractInfoListForSearch(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getContractInfoListForSearchALL(HttpServletRequest request) ;

	@SuppressWarnings("unchecked")
	public List getNOContractInfoList(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getContractInfoListForSearchExcel(HttpServletRequest request) ;

	@SuppressWarnings("unchecked")
	public List getContractInfoListForSearchALLExcel(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getNOContractInfoExcel(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getBecomeRegularWarn(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public int getContractCntForSearch(HttpServletRequest request) ;	

	@SuppressWarnings("unchecked")
	public int getContractCntForSearchALL(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int getNOContractCnt(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int updateContract(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public int insertBecomeRegularEvaluate(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getContractByInsertForGrid(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int getContractByInsertCntForSearch(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getRenewContractForGrid(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getExpiredIdCardList (HttpServletRequest request)throws Exception ;
	
	@SuppressWarnings("unchecked")
	public List getPaNotImportList (HttpServletRequest request)throws Exception ;
	
	@SuppressWarnings("unchecked")
	public int getRenewContractCntForGrid(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int updateRenewContractByInsert(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public Object getPersonalInfoForContract(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public Object getContractForUpdate(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public int updateContractInfo(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public int updateContractInfo1(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public int addContractInfo1(HttpServletRequest request)
			throws Exception;
	
	@SuppressWarnings("unchecked")
	public int deleteContractInfo1(
			HttpServletRequest request) throws Exception;
	/**
	 * 查询详细人力区分类型
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getParticularHumanDistinguishList(HttpServletRequest request)throws Exception;

	/**
	 * 签订契约Excel导出
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getContractByInsertForSearchExcel(HttpServletRequest request)throws Exception;

	/**
	 * 续签契约Excel导出
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getExpiredContractForSearchExcel(HttpServletRequest request)throws Exception;

	@SuppressWarnings("unchecked")
	public List getCodeList(String parentCodeNo, HttpServletRequest request)throws Exception;

	
	/**
	 * 详细人力区分 关联 详细合同类型
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-10-14 下午5:21:33 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	public List getRenLiAndQiYueList(HttpServletRequest request);
	
	/**
	 * 签订合同
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author weizhengchen@ait.net.cn 
	* @date 2013-10-14 下午5:21:33 
	* @version V1.0
	 */
	public int insertContract(HttpServletRequest request);
	public List expiredContractApprove(HttpServletRequest request);
	public int expiredContractApproveCnt(HttpServletRequest request);
	public int approveExpiredContract(HttpServletRequest request);
	
	/**
	 * 获取决裁情况
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getAffirmorListByContractNo(HttpServletRequest request);
	
	/**
	 * 获取check信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getCheckListByContractNo(HttpServletRequest request);
	
	/**
	 * 合同变更数量 (Contract count inquires)
	 * @param request
	 * @return int
	 */
	public int getContractChangeCnt(HttpServletRequest request) ;
	
	/**
	 * 合同变更查询 (Contract inquires)
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getContractChangeList(HttpServletRequest request);
	
	/**
	 * 合同修改查询 (Contract inquires)
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getContractUpdateList(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public void deleteContractUpdateList(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getNullContractUpdateList(HttpServletRequest request);
	
	/**
	 * 检测合同编号是否重复
	 * @param request
	 * @return int
	 */
	public int checkContractNo(HttpServletRequest request);
	/**
	 * 组装未签合同信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public String getNotSignContractInfo(HttpServletRequest request, List aliasNameList, List list,List mapList,List mapNameList) throws SQLException;
	
	/**
	 * 获取合同导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getContractTempList(HttpServletRequest request);
	
	/**
	 * 获取合同导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getContractTempCnt(HttpServletRequest request, String errorFlag);
	
	/**
	 * 合同excel信息提交
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public String submitImportExcelContractData(HttpServletRequest request);
	
	/**
	 * 合同excel信息提交
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public String submitImportExcelContractData2(HttpServletRequest request);
	
	/**
	 * 合同担当查询 (Contract inquires)
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getContractManagerList();
	
	/**
	 * 需要续签的合同信息查询 (Contract inquires)
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getContractRemindList() ;
	
	/**
	 * 组装未签合同信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public String getNotSignContractTempInfo(HttpServletRequest request, List aliasNameList, List list,List mapList,List mapNameList) throws SQLException;

	/**
	 * 组装续签合同信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public String getContractInfo(HttpServletRequest request, List aliasNameList, List list,List mapList,List mapNameList) throws SQLException;
	
	
	
	@SuppressWarnings("unchecked")
	public int updateContractInfoForUpdate(HttpServletRequest request) ;
	
	/**
	 * 合同变更履历查询 (Contract inquires)
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List viewChangeContractHistoryList(HttpServletRequest request) ;
}
