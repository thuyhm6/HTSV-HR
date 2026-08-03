package com.ait.ess.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.util.NumberUtils;

public interface AnnualadjustmentInfoDao {
	
	/**
	 * 查询年假福利年假天数 
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public List retrieveNianjiaFuli(Object obj) throws Exception;
	
	
	/**
	 * 添加年休假信息 
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public void addAnnualadjustmentInBatch(List list) throws Exception;

	
	
	
	/**
	 * 查看年假调整List 分页查询
	 * 
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getAnnualadjustmentAffirmList(Object object, int currentPage,
			int pageSize);
	
	
	/**
	 * 批量删除申请(batch delete overtime apply)
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int delAnnuApplyInBatch(List list) throws Exception;
	
	/**
	 *  分页查询
	 * 
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getCwaAbnormalApplyInfoList(Object object, int currentPage,
			int pageSize);
	
	
	
	@SuppressWarnings("unchecked")
	public List getviewCwaAbnormalAffirmList(Object object, int currentPage,
			int pageSize) throws Exception;
	
	
	
	@SuppressWarnings("unchecked")
	public List getviewCwaAbnormalAffirmList(Object object) throws Exception;
	/**
	 *  bu分页查询
	 * 
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getCwaAbnormalApplyInfoList(Object object);
	
	
	
	/**
	 * 查看年假调整List 分页查询
	 * 
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getAnnualadjustmentAffirmapplyList(Object object, int currentPage,
			int pageSize);

	/**
	 * 查看年假调整List 不分页
	 * 
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getAnnualadjustmentAffirmList(Object object);
	
	/**
	 * 查看年假调整List 不分页
	 * 
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getAnnualadjustmentAffirmapplyList(Object object);
	
	@SuppressWarnings("unchecked")
	public int getAnnualadjustmentAffirmListCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public int getAnnualadjustmentAffirmapplyListCnt(Object object);
	
	
	@SuppressWarnings("unchecked")
	public int getCwaAbnormalApplyInfoListCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public int getviewCwaAbnormalAffirmListCnt(Object object);
	/**
	 * 添加考勤异常信息 
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public void addBatchCwaAbnormalApplyBatch(List list) throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getCwaAbnormalAffirmByApplyNOList(Object object); 
	
	/**
	 * 批量删除考勤异常
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int delCwaAbnormalApplyInfo(List list) throws Exception;

	
	@SuppressWarnings("unchecked")
	public List getCwaCheckList(Object obj, int currentPage, int pageSize)
			throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getCwaCheckList(Object obj)throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getAnnuCheckList(Object obj, int currentPage, int pageSize)
			throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getAnnuCheckList(Object obj)
			throws Exception;
	
	
	@SuppressWarnings("unchecked")
	public int getAnnuCheckListCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public List getAnnuapplyListByApplyno(Object obj, int currentPage, int pageSize)
			throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getAnnuapplyListByApplyno(Object obj)
			throws Exception;
	
	
	@SuppressWarnings("unchecked")
	public int delAnnApplyInBatch(List list) throws Exception;
	
	
	
	/**
	 * 加班申请check信息查询(search ot info list)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getCheckorByApplyNoList(Object object);
	
	
	/**
	 * 根据加班申请NO决裁信息查询(search ot info list)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getAffirmorByApplyNoList(Object object);
	
	public int getCwaCheckListCnt(Object object) ;
	

	/**
	 * 获取年假调整导入信息
	 * 
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getEssArVacTempList(Object object);
	
	/**
	 * 获取年假调整导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getEssArVacTempList(Object object, int currentPage, int pageSize);
	/**
	 * 获取年假调整导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getEssArVacTempCnt(Object object);
	
	/**
	 * 获取出错的年假调整导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getEssArVacTempErrorCnt(Object object);
	
	/**
	 * 获取年假调整批量信息
	 * 
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getEssArVacList(Object object) ;
	
	/**
	 * 获取年假调整批量信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getEssArVacList(Object object, int currentPage, int pageSize);
	
	/**
	 * 获取年假调整批量数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getEssArVacCnt(Object object);
	
	/**
	 * 删除未审核
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public boolean delArVacRecordApplyInfo(List list) throws Exception;
	
	/**
	 * 批量提交漏刷卡申请
	 * 
	 * @param obj
	 * @return
	 */
	public int submitArVacApplyInBatch(List list) throws Exception;
}
