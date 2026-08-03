package com.ait.ess.dao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * 信息申请(information apply)
 * 
 * @Copyright: LDCC
 * @Company: LDCC
 * @fileName: ArMacRecordApplyDao.java
 * @Description:
 * @Create date: Feb 17, 2012 10:51:41 AM
 * @Create by: zhoulei(zhoulei@ait.net.cn)
 * @Update Date: Feb 17, 2012 10:51:41 AM
 * @Update by: zhoulei(zhoulei@ait.net.cn)
 * @version 5.1
 */
public interface ArMacRecordApplyDao {
	/**
	 * 批量添加刷卡申请(add mac record apply)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public void addArMacRecordApplyInBatch(List list) throws Exception;
	
	/**
	 * 获得某人某天某种进出门类型申请数量(get mac record apply data cnt)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public int getExistArRecordApplyCnt(Object object) throws Exception;
	
	/**
	 * 信息决裁用，in/out进出门刷卡数据信息申请数据查询(view ar mac record apply info)，分页查询
	 * 
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getArMacRecordAffirmList(Object object, int currentPage,
			int pageSize);

	/**
	 * 信息决裁用，in/out进出门刷卡数据信息申请数据查询(view ar mac record apply info)，不分页
	 * 
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getArMacRecordAffirmList(Object object);
	
	/**
	 * 信息决裁用，in/out进出门刷卡数据信息申请数据个数(view ar mac record apply information count)
	 * 
	 * @param object
	 * @return
	 */
	public int getArMacRecordAffirmListCnt(Object object);
	
	/**
	 * 决裁情况用，in/out进出门刷卡数据信息申请数据查询(view ar mac record apply info)，分页查询
	 * 
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getArMacRecordAffirmViewList(Object object, int currentPage,
			int pageSize);

	/**
	 * 决裁情况用，in/out进出门刷卡数据信息申请数据查询(view ar mac record apply info)，不分页
	 * 
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getArMacRecordAffirmViewList(Object object);
	
	/**
	 * 决裁情况用，in/out进出门刷卡数据信息申请数据个数(view ar mac record apply information count)
	 * 
	 * @param object
	 * @return
	 */
	public int getArMacRecordAffirmViewListCnt(Object object);
	
	/**
	 * 批量通过/否决：in/out进出门刷卡申请(batch pass and reject ar mac record apply)
	 * 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int saveArMacRecordApplyAffirmInBatch(List list) throws Exception;
	
	/**
	 * 通过/否决in/out进出门刷卡申请(pass and reject ar mac record apply)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int saveArMacRecordApplyAffirm(LinkedHashMap object) throws Exception;
	
	/**
	 * 删除未审核：in/out进出门刷卡信息申请(delete ar mac record apply information)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public boolean delArMacRecordApplyInfo(List list) throws Exception;
	
	/**
	 * 批量加班申请的人员列表(get overtime employee list)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public List getPersonList(Object object, int currentPage, int pageSize);

	/**
	 * 批量加班申请的人员列表(get overtime employee list)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getPersonList(Object object);

	/**
	 * 批量加班申请的人员列表总数(get overtime employee list count)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public int getPersonListCnt(Object obj) throws Exception;

	public int saveRecordAppFile(Map paramMap);

	public List getCardRecordFileList(Map paramMap);

	/**
	 * 决裁情况用，in/out进出门刷卡数据信息申请数据查询(view ar mac record apply info)，不分页
	 * 
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getArMacRecordAffirmCheckViewList(Object object) ;
	

	/**
	 * 决裁情况用，in/out进出门刷卡数据信息申请数据查询(view ar mac record apply info)，分页查询
	 * 
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getArMacRecordAffirmViewCheckList(Object object, int currentPage,int pageSize) ;
	
	/**
	 * 信息决裁用，in/out进出门刷卡数据信息申请数据个数(view ar mac record apply information count)
	 * 
	 * @param object
	 * @return
	 */
	public int getArMacRecordAffirmCheckListCnt(Object object);
	
	/**
	 * 决裁情况用，in/out进出门刷卡数据信息申请数据查询(view ar mac record apply info)，分页查询
	 * 
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getArMacRecordAffirmViewListBySingle(Object object) ;
	
	/**
	 * 获取漏刷卡导入信息
	 * 
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getEssArMacTempList(Object object);
	
	/**
	 * 获取漏刷卡导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getEssArMacTempList(Object object, int currentPage, int pageSize);
	
	/**
	 * 获取漏刷卡导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getEssArMacTempCnt(Object object);
	
	/**
	 * 获取出错的漏刷卡导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getEssArMacTempErrorCnt(Object object);
	
	/**
	 * 获取漏刷卡批量信息
	 * 
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getEssArMacList(Object object) ;
	
	/**
	 * 获取漏刷卡批量信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getEssArMacList(Object object, int currentPage, int pageSize);
	
	/**
	 * 获取漏刷卡批量数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getEssArMacCnt(Object object);
	
	/**
	 * 批量提交漏刷卡申请
	 * 
	 * @param obj
	 * @return
	 */
	public int submitArMacApplyInBatch(List list) throws Exception ;
	
	/**
	 * 
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public LinkedHashMap getArMacRecordApplyInfoForDisplay(Object object);
	
	/**
	 * 取消已审核通过的漏刷卡申请
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public boolean cancelCardApply(Object object) throws Exception;
	
	/**
	 * 查询已申请的刷卡申请数量(get apply data cnt)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public int isExistArRecordApplyCnt(Object object) throws Exception ;
	
	/**
	 * 查看是否有外出、出差、培训申请记录
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public int isExistArRecordApplyLeaveCnt(Object object) throws Exception ;
	
	/**
	 * 查看是否是休息日期
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public int isXiuXi(Object object) throws Exception ;
}
