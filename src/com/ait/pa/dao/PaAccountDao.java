package com.ait.pa.dao;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

@SuppressWarnings("unchecked")
public interface PaAccountDao {
	public List getPaAccountList(Object object) ;
	
	public int getPaAccountCnt(Object object);
	
	public List getPaAccountList(Object object, int currentPage, int pageSize);
	
	public int updatePaAccountInfo(Object object);
	public Object getPaAccountInfoByPersonid(Object object) throws SQLException;
	public int savePaAccountInfo(Object object) throws SQLException;
	public Object getPaAccountCtrollerInfo(Object object);
	
	public List getBankList(Object object) ;
	
	public List getBankBranchList(Object object) ;
	
	public int updatePaCalcFlagByPersonId(Object object) ;

	/**
	 * 查询出工资对象列表
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author zhengxiaochen zhengxiaochen@ait.net.cn
	* @date 2013-9-4 上午11:48:44 
	* @version V1.0
	 */
	public List getPaSalaryObjectList(Object object, int pageNum,
			int numPerPage)throws Exception;

	/**
	 * 查询出工资对象列表
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author zhengxiaochen zhengxiaochen@ait.net.cn
	* @date 2013-9-4 上午11:52:47 
	* @version V1.0
	 */
	public List getPaSalaryObjectList(Object object)throws Exception;
	
	/**
	 * 查询出工资对象列表数量
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author zhengxiaochen zhengxiaochen@ait.net.cn
	* @date 2013-9-4 上午11:52:47 
	* @version V1.0
	 */
	public int getPaSalaryObjectCnt(Object object)throws Exception;
	
	/**
	 * 更新计算标识
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author zhengxiaochen zhengxiaochen@ait.net.cn
	* @date 2013-9-5 下午02:03:05 
	* @version V1.0
	 */
	public int updatePaSalCalcFlagByPersonId(Object object) throws Exception;

	/**
	 * 查询员工状态
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author zhengxiaochen zhengxiaochen@ait.net.cn
	* @date 2013-9-6 下午03:58:09 
	* @version V1.0
	 */
	public List getEmpOfficeList(LinkedHashMap paramMap)throws Exception;

	/**
	 * 计算对象(奖金,工资,保险)
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author zhengxiaochen zhengxiaochen@ait.net.cn
	* @date 2013-9-15 下午11:21:52 
	* @version V1.0
	 */
	public String calculateObject(LinkedHashMap paramMap)throws Exception;

	public Object getPaObjectCtrollerInfo(LinkedHashMap paramMap)throws Exception;

	public int updatePaObjectInfo(LinkedHashMap paramMap)throws Exception;

	public List getPersonType(LinkedHashMap paramMap);

	/**
	 * 查询职责津贴(个人)
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author yuanxq@ait.net.cn
	* @date 2014-6-25 下午06:35:30 
	* @version V1.0
	 */
	public List getPersonAllowance(LinkedHashMap paramMap);

	/**
	 * 查询职责津贴(个人)
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author yuanxq@ait.net.cn
	* @date 2014-6-25 下午06:35:30 
	* @version V1.0
	 */
	public List getPersonAllowance(LinkedHashMap paramMap, int pageNum,
			int numPerPage);

	public int getPersonAllowanceCnt(LinkedHashMap paramMap);
	/**
	 * 获取职责List
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author yuanxq@ait.net.cn
	* @date 2014-6-26 上午10:00:42 
	* @version V1.0
	 */
	public List getPositionList(LinkedHashMap paramMap);
	/**
	 * 查找职责津贴个人
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author yuanxq@ait.net.cn
	* @date 2014-6-26 上午11:01:07 
	* @version V1.0
	 */
	public Object getPaAllowanceSelf(LinkedHashMap paramMap);
	/**
	 * 保存个人津贴修改
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author yuanxq@ait.net.cn
	* @date 2014-6-26 上午11:52:24 
	* @version V1.0
	 */
	public int updatePaAllowanceSelfInfo(LinkedHashMap paramMap);
	/**
	 * 根据Id号查找职责津贴标准
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author yuanxq@ait.net.cn
	* @date 2014-6-26 下午02:19:10 
	* @version V1.0
	 */
	public Object getPaAllowance(LinkedHashMap paramMap);
	/**
	 * 新增职责津贴标准
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author yuanxq@ait.net.cn
	* @date 2014-6-26 下午02:19:43 
	* @version V1.0
	 */
	public int insertPaAllowanceInfo(LinkedHashMap paramMap);
	/**
	 * 更新职责津贴标准
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author yuanxq@ait.net.cn
	* @date 2014-6-26 下午02:19:59 
	* @version V1.0
	 */
	public int updatePaAllowanceInfo(LinkedHashMap paramMap);
	/**
	 * 获取职责津贴列表
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author yuanxq@ait.net.cn
	* @date 2014-6-26 下午03:21:25 
	* @version V1.0
	 */
	public List getAllowance(LinkedHashMap paramMap);
	/**
	 * 获取职责津贴列表
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author yuanxq@ait.net.cn
	* @date 2014-6-26 下午03:21:25 
	* @version V1.0
	 */
	public List getAllowance(LinkedHashMap paramMap, int pageNum, int numPerPage);
	/**
	 * 获取职责津贴的总条数
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author yuanxq@ait.net.cn
	* @date 2014-6-26 下午03:26:10 
	* @version V1.0
	 */
	public int getAllowanceCnt(LinkedHashMap paramMap);
	/**
	 * 删除职责津贴标准
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author yuanxq@ait.net.cn
	* @date 2014-6-26 下午05:25:23 
	* @version V1.0
	 */
	public int deletePaAllowanceInfo(LinkedHashMap paramMap);

	public List getEvaluationDataImportResultList(LinkedHashMap paramMap,
			int pageNum, int numPerPage);

	public List getEvaluationDataImportResultList(LinkedHashMap paramMap);

	public int getEvaluationDataImportResultListCnt(LinkedHashMap paramMap);

	public int getEvaluationDataImportErrCnt(LinkedHashMap paramMap);

	public String importPaAllowanceExcelTempExcel(LinkedHashMap paramMap);

	public List getEvaluationDataImportResultSelfList(LinkedHashMap paramMap,
			int pageNum, int numPerPage);

	public List getEvaluationDataImportResultSelfList(LinkedHashMap paramMap);
	public List getPaAccountDataImportResultList(LinkedHashMap paramMap);
	
	public List getPaAccountDataImportResultList(LinkedHashMap paramMap,
			int pageNum, int numPerPage);

	public int getEvaluationDataImportResultListSelfCnt(LinkedHashMap paramMap);

	public int getEvaluationDataImportErrSelfCnt(LinkedHashMap paramMap);
	public int getPaAccountDataImportResultListErrCnt(LinkedHashMap paramMap);
	public int getPaAccountDataImportResultListCnt(LinkedHashMap paramMap);

	public String importPaAllowanceExcelSelfExcel(LinkedHashMap paramMap);

	public String importPaAccountExcelExcel(LinkedHashMap paramMap);
	/**
	 * sy工资计算状态页面修改试用支付比例
	 * @param request
	 * @return
	 */
	public int updateC_PROB_PAY_RAT(LinkedHashMap paramMap);
}
