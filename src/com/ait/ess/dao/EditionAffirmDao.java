package com.ait.ess.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface EditionAffirmDao {
	/**
	 * 查找离职申请数量
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public int getEditionAffirmCnt(Object object)throws SQLException;
	public int getDimissionAffirmorCnt(Object object)throws SQLException;
	
	/**
	 * 分页查找离职申请List
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public List getEditionAffirmList(Object object, int currentPage, int pageSize)throws SQLException;
	
	/**
	 * 查找离职申请List
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public List getEditionAffirmList(Object object)throws SQLException;
	
	/**
	 * 根据离职申请NO查询申请的人(search ot info list)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getEditionApplyorByApplyNoList(Object object);
	
	/**
	 * 根据离职申请NO决裁信息查询(search ot info list)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getEditionAffirmorByApplyNoList(Object object);
	
	/**
	 * 离职申请check信息查询(search ot info list)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getEditionCheckorByApplyNoList(Object object);
	
	/**
	 * 删除离职审批决裁者
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-13
	 * @version V1.0
	 */
	public void deleteDimissionAffirmor(Object object)  throws Exception;
	
	/**
	 * 添加离职决裁者
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-13
	 * @version V1.0
	 */
	public void insertDimissionAffirmor(Object object)  throws Exception;
	
	/**
	 * 修改决裁状态
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-13
	 * @version V1.0
	 */
	public void updateDimissionEssAffirm(Object object) throws Exception;
	
	/**
	 * 根据裁决no修改check FLAG
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-13
	 * @version V1.0
	 */
	public void updateCheckFlagByDimissionEssAffirmNo(Object object)  throws Exception;
	
	/**
	 * 决裁离职申请
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-13
	 * @version V1.0
	 */
	public void affirmDimissionInfo(Object object)  throws Exception;
	public void affirmDimissionInfoAffirm(Object object)  throws Exception;
	public void affirmDimissionItemInfo(Object object)  throws Exception;
	
	/**
	 * 修改check信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-13
	 * @version V1.0
	 */
	public void updateDimissionEssCheck(Object object) throws Exception;
	

	/**
	 * 获取决裁情况
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-13
	 * @version V1.0
	 */
	public List getDimissionAffirmorList(Object object);
	public List getDimissionAffirmorList(Object object, int currentPage, int pageSize);
	
	/**
	 * 获取决裁情况
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-13
	 * @version V1.0
	 */
	public List getDimissionAffirmor(Object object);
	
	/**
	 * 获取需要check的离职申请信息
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author wendi
	 * @return
	 * @throws Exception
	 */
	public List getDimissionCheckList(Object object);
	
	/**
	 * 获取需要check的离职申请信息
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author wendi
	 * @return
	 * @throws Exception
	 */
	public List getDimissionCheckList(Object object, int currentPage, int pageSize);
	
	/**
	 * 获取check信息数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getDimissionCheckCnt(Object object);
	
	/**
	 * 添加Check人
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-07
	 * @version V1.0
	 */
	public void addCheckAffirmDimission(Object object)  throws Exception;
	
	/**
	 * 查找离职交接项目明细List
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	public List getAffirmEditionItemList(Object object)throws SQLException;
	
	/**
	 * 根据person——id查找该员工的部门信息
	 */
	public List getDeptByPersonId(Object object)throws SQLException;
	
	/**
	 * 根据离职申请NO查询此次（个人/代）申请的所有人(search ot info list)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getDimissionByApplyNoList(Object object);
	
	/**
	 * 根据离职申请人的personId查找这个人对应的离职交接模版的详细内容
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getEditionItemByPidList(Object object);
	
	/**
	 * 添加离职申请者的个人离职模版信息
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-07
	 * @version V1.0
	 */
	public void addEditionEditionPidInfo(Object object)  throws Exception;
}
