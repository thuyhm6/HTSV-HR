package com.ait.ess.dao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public interface WageApplicationDao {
	/**
	 * 查找列表,通过员工ID
	 * 
	 * @param paramMap
	 * @param j
	 * @param i
	 * @return
	 */
	public List getListByEmp(Map paramMap, Integer currentPage, Integer pageSize);

	/**
	 * 通过父级ID和语音来查找子代码list
	 * 
	 * @param paramMap
	 * @param j
	 * @param i
	 * @return
	 */
	public List getApplicationList(Map paramMap, Integer currentPage,
			Integer pageSize);

	public int getApplicationListCnt(Map paramMap);

	/**
	 * 获取决裁者列表
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @Description: TODO
	 * @author yuanxq@ait.net.cn
	 * @date 2014-7-9 下午01:08:57
	 * @version V1.0
	 */
	public List getProveAppList(Map paramMap);

	public int getListCnt(Map paramMap);

	/**
	 * 批量操作费用审批
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @Description: TODO
	 * @author yuanxq@ait.net.cn
	 * @date 2014-7-10 上午10:59:22
	 * @version V1.0
	 */
	public int updateBatchSubmitApp(Map paramMap);

	/**
	 * 获取添加的check列表
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @Description: TODO
	 * @author yuanxq@ait.net.cn
	 * @date 2014-7-10 下午02:25:26
	 * @version V1.0
	 * @param j
	 * @param i
	 */
	public List getProveCheckAppList(Map paramMap, Integer currentPage,
			Integer pageSize);

	public int getProveCheckAppListCnt(Map paramMap);

	/**
	 * 获取撤销状态，最大日期在考勤周期当月工资确认前准许撤销
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @Description: TODO
	 * @author yuanxq@ait.net.cn
	 * @date 2014-7-11 上午10:23:30
	 * @version V1.0
	 */
	public int getCancleApplicationState(Map paramMap);

	/**
	 * 撤销费用申请
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @Description: TODO
	 * @author yuanxq@ait.net.cn
	 * @date 2014-7-11 上午11:45:51
	 * @version V1.0
	 */
	public int deleteOtApplication(Map paramMap);

	/**
	 * 费用申请撤销查看页面List
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @Description: TODO
	 * @author yuanxq@ait.net.cn
	 * @date 2014-7-11 下午02:59:04
	 * @version V1.0
	 */
	public List getPbWageList(Map paramMap, int pageNum, int numPerPage);

	/**
	 * 费用申请撤销查看页面List总条数
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @Description: TODO
	 * @author yuanxq@ait.net.cn
	 * @date 2014-7-11 下午02:59:04
	 * @version V1.0
	 */
	public int getPbWageCnt(Map paramMap);

	/**
	 * 个人级别费用申请撤销
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @Description: TODO
	 * @author yuanxq@ait.net.cn
	 * @date 2014-7-11 下午04:11:40
	 * @version V1.0
	 */
	public int getBackPbOtApplication(Map paramMap);

	public List getWageApplicationTempList(LinkedHashMap paramMap, int pageNum,
			int numPerPage);

	public List getWageApplicationTempList(LinkedHashMap paramMap);

	public int getWageApplicationTempCnt(Map<String, Object> paramMap);

	public int getWageApplicationTempErrCnt(Map<String, Object> paramMap);

	public String importApplicationExcelTempExcel(Map<String, Object> paramMap);

	public List getWageApplicationList(Map paramMap, int pageNum, int numPerPage);

	public int getWageApplicationCnt(Map paramMap);

	public int checkApplyApplicationState(Map paramMap);

	public List getApplicationByNoApplyNo(Map paramMap, int pageNum,
			int numPerPage);

	public int getApplicationByNoApplyNoCnt(Map paramMap);
	/**
	 * 保存费用申请上传的附件
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author yuanxq@ait.net.cn
	* @date 2014-8-26 上午10:41:56 
	* @version V1.0
	 */
	public int saveWageAppFile(Map paramMap);
	/**
	 * 根据wageNo和PERSON_ID查询附件列表
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author yuanxq@ait.net.cn
	* @date 2014-8-26 上午11:12:47 
	* @version V1.0
	 */
	public List getAppliFileList(Map paramMap);
	/**
	 * 删除上传的附件
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author yuanxq@ait.net.cn
	* @date 2014-8-26 上午11:29:28 
	* @version V1.0
	 */
	public int deleteWageAppFile(Map paramMap);
	/**
	 * 将费用申请的No号更新到附件表中
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author yuanxq@ait.net.cn
	* @date 2014-8-26 下午02:17:04 
	* @version V1.0
	 */
	public void updateWageAppFile(Map paramMap);
	
	/**
	 * 费用履历查看  分页
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getWageCheckList(Map paramMap, int currentPage, int pageSize);
	
	/**
	 * 费用履历查看  不分页
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getWageCheckList(Map paramMap);
	
	/**
	 *费用履历查看  数量
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int getWageCheckCnt(Map paramMap);

}
