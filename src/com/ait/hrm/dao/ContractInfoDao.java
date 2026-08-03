package com.ait.hrm.dao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: ContractInfoDao.java
 * @Description: implement Class ContractInfoDaoImpl.java
 * @Create date: Jan 6, 2012 4:24:27 PM
 * @Create by: liyy(liyueyang@ait.net.cn)
 * @version 5.1
 */
public interface ContractInfoDao {
	@SuppressWarnings("unchecked")
	public List getContractListForSearch(Object object);

	@SuppressWarnings("unchecked")
	public List getContractListForSearchALL(Object object);
	
	@SuppressWarnings("unchecked")
	public List getNOContractList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getContractListForSearch(Object object, int currentPage, int pageSize);

	@SuppressWarnings("unchecked")
	public List getContractListForSearchALL(Object object, int currentPage, int pageSize);

	@SuppressWarnings("unchecked")
	public List getNOContractList(Object object, int currentPage, int pageSize);
	
	@SuppressWarnings("unchecked")
	public int getContractCntForSearch(Object object);

	@SuppressWarnings("unchecked")
	public List getBecomeRegularWarnList(Object obj);
	
	@SuppressWarnings("unchecked")
	public int getContractCntForSearchALL(Object object);
	
	@SuppressWarnings("unchecked")
	public int getNOContractCnt(Object object);

	@SuppressWarnings("unchecked")
	public void saveNewContract(Object object)throws Exception;
	
	@SuppressWarnings("unchecked")
	public void insertBecomeRegularEvaluate(Object object)throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getContractByInsertForGrid(Object object, int currentPage, int pageSize);
	
	@SuppressWarnings("unchecked")
	public List getContractByInsertForGrid(Object object);
	
	@SuppressWarnings("unchecked")
	public int getContractByInsertCntForSearch(Object object);
	
	@SuppressWarnings("unchecked")
	public List getRenewContractForGrid(Object object);
	
	@SuppressWarnings("unchecked")
	public List getExpiredIdCardList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getPaNotImportList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getRenewContractForGrid(Object object, int currentPage, int pageSize);
	
	@SuppressWarnings("unchecked")
	public int getRenewContractCntForGrid(Object object);
	
	@SuppressWarnings("unchecked")
	public void updateRenewContractByInsert(HttpServletRequest request,LinkedHashMap object)throws Exception;
	/***
	 * 只是修改08年后合同签订次数
	 * @param object
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void updateRenewContract(Object object)throws Exception;
	
	@SuppressWarnings("unchecked")
	public Object getPersonalInfoForContract(Object object);
	
	@SuppressWarnings("unchecked")
	public String getParamVale(Object object)throws Exception;
	
	@SuppressWarnings("unchecked")
	public Object getContractForUpdate(Object object);
	
	@SuppressWarnings("unchecked")
	public void updateContractInfo(Object object)throws Exception;
	
	@SuppressWarnings("unchecked")
	public void updateContractInfo1(Object object)throws Exception;
	
	@SuppressWarnings("unchecked")
	public void addContractInfo1(Object object)throws Exception;
	
	@SuppressWarnings("unchecked")
	public int deleteContractInfo1(List list) throws Exception;
	
	/**
	 * 查询详细人力区分类型
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getParticularHumanDistinguishList(LinkedHashMap paramMap)throws Exception;

	public List getCodeList(LinkedHashMap paramMap)throws Exception;

	/**
	 * 详细人力区分 关联 详细合同类型
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-10-14 下午5:30:00 
	* @version V1.0
	 */
	public List getRenLiAndQiYueList(LinkedHashMap paramMap);
	
	/**
	 * 签订合同
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author weizhengchen@ait.net.cn 
	* @date 2013-10-14 下午5:21:33 
	* @version V1.0
	 */
	public void insertContract(Object obj) throws Exception;
	
	public List expiredContractApprove(Object obj, int currentPage,
			int pageSize);
	public List expiredContractApprove(Object obj);
	public int expiredContractApproveCnt(Object object);
	public void approveExpiredContract(Object object) throws Exception;
	
	/**
	 * 获取合同序号
	 * @param obj
	 * @author weizhengchen
	 * @throws Exception
	 */
	public int getContractSeq(Object object) throws Exception ;
	
	/**
	 * 插入续签合同决裁者
	 * @param obj
	 * @author weizhengchen
	 * @throws Exception
	 */
	public void insertContractAffirm(Object object) throws Exception ;
	
	/**
	 * 获取决裁者列表
	 * @param paramMap
	 * @author weizhengchen
	 * @return
	 */
	public List<LinkedHashMap> getEssAffirmList(LinkedHashMap paramMap);
	
	/**
	 * 决裁合同
	 * @param obj
	 * @author weizhengchen
	 * @throws Exception
	 */
	public void affirmContract(Object object) throws Exception;
	
	/**
	 * 合同变更查询 (Contract inquires)
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return List
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getContractChangeList(Object obj, int currentPage,
			int pageSize);
	
	/**
	 * 合同修改查询 (Contract inquires)
	 * @param obj
	 * @param currentPage
	 * @param pageSize
	 * @return List
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getContractUpdateList(Object obj, int currentPage,
			int pageSize);
	
	/**
	 * 合同变更查询 (Contract inquires)
	 * @param obj
	 * @return List
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getContractChangeList(Object obj);
	
	/**
	 * 合同变更查询 (Contract inquires)
	 * @param obj
	 * @return List
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getContractUpdateList(Object obj);
	
	@SuppressWarnings("unchecked")
	public int deleteContractUpdateList(Object obj);
	
	@SuppressWarnings("unchecked")
	public List getNullContractUpdateList(Object obj);
	
	/**
	 * 得到所有可变更合同的数量 (Get all the number of contract)
	 * @param obj
	 * @return int
	 * @throws Exception
	 */
	public int getContractChangeCnt(Object obj) ;
	
	/**
	 * 得到所有可变更合同的数量 (Get all the number of contract)
	 * @param obj
	 * @return int
	 * @throws Exception
	 */
	public int checkContractNo(Object obj);
	
	/**
	 * 获取合同导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getContractTempList(Object object);
	
	/**
	 * 获取合同导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getContractTempList(Object object, int currentPage, int pageSize);
	
	/**
	 * 获取合同导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getContractTempCnt(Object object);
	
	/**
	 * 获取出错的合同导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getContractTempErrorCnt(Object object);
	
	/**
	 * 合同担当查询 (Contract inquires)
	 * 
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getContractManagerList() ;
	
	/**
	 * 需要续签的合同信息查询 (Contract inquires)
	 * @param request
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getContractRemindList() ;

	@SuppressWarnings("unchecked")
	public int updateContractInfoForUpdate(Object object);


	/**
	 * 合同变更履历查询 (Contract inquires)
	 * 
	 * @param obj
	 * @return List
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List viewChangeContractHistoryList(Object obj) ;
	@SuppressWarnings("unchecked")
	public List getExpiredIdCardList(Object obj, int currentPage, int pageSize);
	@SuppressWarnings("unchecked")
	public List getPaNotImportList(Object obj, int currentPage, int pageSize);
	@SuppressWarnings("unchecked")
	public List getBecomeRegularWarn(Object obj, int currentPage,int pageSize);
}

