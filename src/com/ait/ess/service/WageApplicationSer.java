package com.ait.ess.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

@SuppressWarnings("unchecked")
public interface WageApplicationSer {
	/**
	 * 通过请求查找列表,这个可以分页用
	 * 
	 * @param request
	 * @return
	 */
	public List getListByRequest(HttpServletRequest request);

	/**
	 * 通过请求获取总数
	 * 
	 * @param request
	 * @return
	 */
	public int getListCnt(HttpServletRequest request);

	public List getApplicationList(HttpServletRequest request);

	public int getApplicationListCnt(HttpServletRequest request);

	/**
	 * 获取决裁者列表
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @Description: TODO
	 * @author yuanxq@ait.net.cn
	 * @date 2014-7-9 下午01:05:22
	 * @version V1.0
	 */
	public List getProveAppList(HttpServletRequest request);

	/**
	 * 批量操作费用审批
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @Description: TODO
	 * @author yuanxq@ait.net.cn
	 * @date 2014-7-10 上午10:58:16
	 * @version V1.0
	 */
	public int updateBatchSubmitApp(HttpServletRequest request);

	/**
	 * 获取添加的check列表
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @Description: TODO
	 * @author yuanxq@ait.net.cn
	 * @date 2014-7-10 下午02:25:26
	 * @version V1.0
	 */
	public List getProveCheckAppList(HttpServletRequest request);

	public int getProveCheckAppListCnt(HttpServletRequest request);

	/**
	 * 获取撤销状态，最大日期在考勤周期当月工资确认前准许撤销; 返回1时说明该月工资还没有确认，直接删除；否则，只能单个删除。
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @Description: TODO
	 * @author yuanxq@ait.net.cn
	 * @date 2014-7-11 上午10:23:30
	 * @version V1.0
	 */
	public int getCancleApplicationState(HttpServletRequest request);

	/**
	 * 撤销费用申请
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @Description: TODO
	 * @author yuanxq@ait.net.cn
	 * @date 2014-7-11 上午11:35:32
	 * @version V1.0
	 */
	public int deleteOtApplication(HttpServletRequest request);

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
	public List getPbWageList(HttpServletRequest request);

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
	public int getPbWageCnt(HttpServletRequest request);

	/**
	 * 个人级别费用申请撤销
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @Description: TODO
	 * @author yuanxq@ait.net.cn
	 * @date 2014-7-11 下午04:09:10
	 * @version V1.0
	 */
	public int getBackPbOtApplication(HttpServletRequest request);

	public List getWageApplicationTempList(HttpServletRequest request);

	public int getWageApplicationTempCnt(HttpServletRequest request);

	public int getWageApplicationTempErrCnt(HttpServletRequest request);

	public String importApplicationExcelTempExcel(HttpServletRequest request);

	public int getCancleSigleApplicationState(HttpServletRequest request);

	public int getWageApplicationCnt(HttpServletRequest request);

	public List getWageApplicationList(HttpServletRequest request);

	public int checkApplyApplicationState(HttpServletRequest request);

	public List getApplicationByNoApplyNo(HttpServletRequest request);

	public int getApplicationByNoApplyNoCnt(HttpServletRequest request);
	/**
	 * 保存费用申请上传的文件
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author yuanxq@ait.net.cn
	* @date 2014-8-26 上午10:40:57 
	* @version V1.0
	 */
	public int saveWageAppFile(HttpServletRequest request, Map<String, Object> map);
	/**
	 * 查询上传的附件
	 * WageNo 费用申请的No号
	 * PERSON_ID 附件的上传人
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author yuanxq@ait.net.cn
	* @date 2014-8-26 上午11:10:03 
	* @version V1.0
	 */
	public List getAppliFileList(HttpServletRequest request);
	/**
	 * 删除上传的附件
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author yuanxq@ait.net.cn
	* @date 2014-8-26 上午11:28:35 
	* @version V1.0
	 */
	public int deleteWageAppFile(HttpServletRequest request);
	/**
	 * 将费用申请的No号更新到对应的附件表中
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author yuanxq@ait.net.cn
	* @date 2014-8-26 下午02:12:30 
	* @version V1.0
	 * @param errorInt 
	 */
	public void updateWageAppFile(HttpServletRequest request, int wageNo);
	/**
	 * 未保存的附件将批量删除
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author yuanxq@ait.net.cn
	* @date 2014-8-26 下午02:13:06 
	* @version V1.0
	 */
	public void deleteAllWageAppFile(HttpServletRequest request);
	
	
	/**
	 * 费用履历查看  
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getWageCheckList(HttpServletRequest request);
	
	/**
	 *费用履历查看  数量
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int getWageCheckCnt(HttpServletRequest request);

}
