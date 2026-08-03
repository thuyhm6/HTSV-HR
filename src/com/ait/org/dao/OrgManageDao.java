package com.ait.org.dao;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface OrgManageDao {
	@SuppressWarnings("unchecked")
	public List getDeptInfo(Object object);

	@SuppressWarnings("unchecked")
	public List getDeptInfoTree(Object object);

	@SuppressWarnings("unchecked")
	public List getDeptLevel(Object object);

	@SuppressWarnings("unchecked")
	public Map getOrganizationInfoForSearch(Object object, int skipResults,
			int maxResults);

	@SuppressWarnings("unchecked")
	public List getCpnyForSelect(Object object) throws Exception;

	@SuppressWarnings("unchecked")
	public List getPostForChoose(Object object) throws Exception;

	@SuppressWarnings("unchecked")
	public List getCpnyNameForChoose(Object object) throws Exception;

	@SuppressWarnings("unchecked")
	public List getSelectDept(Object object) throws Exception;

	@SuppressWarnings("unchecked")
	public Map getOrganizationByChoosed(Object object) throws Exception;

	public void saveOrganization(Object object) throws Exception;

	@SuppressWarnings("unchecked")
	public List getDeptPost(Object object) throws Exception;

	public void updateOrganization(Object object) throws Exception;

	// 修改组织排序号的时候 后面的顺序都要+1
	public void updateOrganizationNo(Object object);

	@SuppressWarnings("unchecked")
	public List getOrganizationInfoList(Object object);

	@SuppressWarnings("unchecked")
	public List getOrganizationInfoList(Object object, int currentPage,
			int pageSize);

	public int getOrganizationInfoCnt(Object object);

	public int addOrganizationInfo(Object object) throws Exception;

	public void deleteOrganizationInfo(Object object) throws Exception;

	public Object getOrganizationInfo(Object obj);

	@SuppressWarnings("unchecked")
	public List getDeptNameListByActivity();

	@SuppressWarnings("unchecked")
	public List getDeptNameNameListByActivity(Object object);

	public void updateDeptName(Object obj) throws Exception;

	public Object getDeptTopLevel(Object object);

	@SuppressWarnings("unchecked")
	public List getChildCodeList(Object object);

	@SuppressWarnings("unchecked")
	public List getEmpInfoInOrg(Object object);

	// 查询人员
	@SuppressWarnings("unchecked")
	public List viewStructure(Object object) throws Exception;

	@SuppressWarnings("unchecked")
	public List getHrEmployeeByCpnyAndDeptNo(Object object) throws Exception;

	/**
	 * 部门组织结构导出(export the org info)，不分页
	 * 
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getOrgStructureInfoList(Object object);

	/**
	 * 部门组织结构数量(export the org info count)
	 * 
	 * @param object
	 * @return int
	 */
	@SuppressWarnings("unchecked")
	public int getOrgStructureInfoListCnt(Object object);

	/**
	 * 部门组织结构导出(export the org info)
	 * 
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getOrgStructureInfoList(Object object, int currentPage,
			int pageSize);

	/**
	 * 启用某个部门
	 * 
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return list
	 */
	public int openOrgStructureInfo(Object object) throws Exception;

	/**
	 * 关闭某个部门
	 * 
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return list
	 */
	public int closeOrgStructureInfo(Object object) throws Exception;

	/**
	 * 获取部门列表信息，供页面部门检索条件用
	 * 
	 * @param request
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getDeptList(Object object) throws Exception;

	/**
	 * 获取大区信息
	 * 
	 * @param request
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getPayAreaInfoList(Object obj);

	/**
	 * 更新大区信息
	 * 
	 * @param request
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	public int updatePayAreaInfo(Object obj) throws Exception;

	/**
	 * 更新社员大区信息
	 * 
	 * @param request
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	public int updateEmpPayAreaInfo(Object obj) throws Exception;

	/**
	 * 根据parent_code_no获取code列表信息，供页面code检索条件用
	 * 
	 * @param request
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getSyCodeList(Object object) throws Exception;

	/**
	 * 组织排序设置list查找
	 * 
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getOrgInfoOrderList(Object object, int currentPage, int pageSize);

	@SuppressWarnings("unchecked")
	public int getOrgOrgInfoOrderCnt(Object object);

	@SuppressWarnings("unchecked")
	public List getOrgInfoOrderList(Object object);

	/**
	 * 分发人查看添加的No在数据表中是否存在
	 * 
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int checkOrganizationNo(Object object);

	/**
	 * 单条组织信息详情
	 * 
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object getOrgInfoOrder(Object obj);

	/**
	 * 获取组织排序导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getDeptOrderImportTempList(Object object, int currentPage,
			int pageSize);

	/**
	 * 获取组织排序导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getDeptOrderImportTempList(Object object);

	/**
	 * 获取组织排序导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getDeptOrderImportTempCnt(Object object);

	/**
	 * 获取出错的组织排序导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getDeptOrderImportTempErrorCnt(Object object);

	/**
	 * 数据导入(验证通过后--从临时表导入到正式表)
	 * 
	 * @param
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String importDeptOrderFromExcel(Object object) throws Exception;

	/**
	 * 获取部门列表信息，供页面部门检索条件用（概要）
	 * 
	 * @param request
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getResumeDeptList(Object obj);

	public List viewResumeList(Object object);

	/**
	 * 插入概要改编insert
	 * 
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int addResumeInfo(Object object) throws Exception;

	/**
	 * 删除概要改编
	 * 
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int deleteResumeInfo(Object object) throws Exception;

	/**
	 * 取得全局SEQ
	 * 
	 * @param request
	 * @return
	 */
	public List getResumneNo(Object obj) throws Exception;
	
	/**
	 * deptnomax
	 * 
	 * @param request
	 * @return
	 */
	public List getDeptNoMax(Object obj) throws Exception;
	/**
	 * deptnomax
	 * 
	 * @param request
	 * @return
	 */
	public List getDeptNoMax1(Object obj) throws Exception;

	/**
	 * 改编流程执行
	 * 
	 * @param
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String executeResumeProcess(Object object) throws Exception;

	/**
	 * 流程执行状态
	 * 
	 * @param
	 * @return
	 */
	public List viewResumeProcess(Object object);

	/**
	 * 获取默认deptNO
	 * 
	 * @param request
	 * @return
	 */
	public String getDefaultDeptNo(Object obj) throws Exception;

	/**
	 * 获取部门信息
	 * 
	 * @param request
	 * @return
	 */
	public List viewResumeDeptList(Object object);

	/**
	 * 获取人员信息
	 * 
	 * @param request
	 * @return
	 */
	public List viewResumeEmpList(Object object);

	/**
	 * 移动部门人员
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	public String executeMoveEmp(Object object) throws Exception;

	/**
	 * 插入组织
	 * 
	 * @param Object
	 * @return
	 */
	public int addOrgInfo(Object object) throws Exception;

	/**
	 * 删除组织
	 * 
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String deleteOrgInfo(Object object) throws Exception;

	/**
	 * 获取部门顺序
	 * 
	 * @param request
	 * @return
	 */
	public List viewModifyOrgOrderNo(Object object);

	/**
	 * 部门顺序再定义
	 * 
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int modifyOrgOrderNo(Object object) throws Exception;

	/**
	 * 部门合并
	 * 
	 * @param
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String modifyOrgMerge(Object object) throws Exception;

	/**
	 * 部门分割
	 * 
	 * @param
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String modifyOrgSplit(Object object) throws Exception;

	public List viewDeptManagerCheck(Object object);

	/**
	 * 发令核查
	 * 
	 * @param object
	 * @return
	 */
	public List viewOrgExperirnceInsideList(Object object);

	public int addOrgExperirnceInsideInfo(Object object) throws Exception;

	public int deleteOrgExperirnceInsideInfo(Object object) throws Exception;

	public List viewChangeDetailOrgInfoList(Object object);

	public List viewChangeDetailEmpInfoList(Object object);

	/**
	 * 工作地管理
	 */
	public List viewWorkAreaList(Object object);

	public int addWorkAreaInfo(Object object) throws Exception;

	public int deleteWorkAreaInfo(Object object) throws Exception;

	/**
	 * 成本中心管理
	 */
	public List viewCostCenterList(Object object);

	public String addCostCenterInfo(Object object) throws Exception;

	public int deleteCostCenterInfo(Object object) throws Exception;

	/**
	 * 部门业务管理
	 */
	public List viewBusinessList(Object object);

	public int addBusinessInfo(Object object) throws Exception;

	public int saveBusinessInfo(Object object) throws Exception;

	public int deleteBusinessInfo(Object object) throws Exception;

	public int deleteEmptyBusinessInfo(Object object) throws Exception;

	/**
	 * 保存部门管理者信息
	 */
	@SuppressWarnings("unchecked")
	public int saveDeptManager(Object object) throws Exception;

	/**
	 * 部门变更履历
	 * 
	 * @param object
	 * @return
	 */
	public List viewOrgChangeInfoList(Object object);

	/**
	 * 保存部门变更履历备注
	 */
	@SuppressWarnings("unchecked")
	public int saveOrgChangeInfo(Object object) throws Exception;

	/**
	 * 当前组织 及 组织内人员信息
	 * 
	 * @param request
	 * @return
	 */
	public List viewCurrentOrgDetailEmpInfo(Object object);

	/**
	 * 当前组织信息
	 * 
	 * @param request
	 * @return
	 */
	public List viewCurrentOrgDetailOrgInfo(Object object);

	/**
	 * 根据日期获取概要NO
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getHistoryResumneNo(Object obj) throws Exception;

	/**
	 * 获取版本组织信息
	 * 
	 * @param request
	 * @return
	 */
	public List viewResumeOrgList(Object object);

	/**
	 * 获取历史部门人员信息
	 * 
	 * @param request
	 * @return
	 */
	public List viewHistoryResumeEmpList(Object object);

	/**
	 * 查询部门分割 人员临时信息
	 */
	public List viewOrgSplitTemp(Object object);

	/**
	 * 获取概要改编的状态
	 * 
	 * @param request
	 * @return
	 */
	public List getResumeActivity(Object obj) throws Exception;

	/**
	 * 删除部门信息
	 * 
	 * @param Object
	 * @return
	 */
	public void deleteFile(Object object) throws Exception;

	/**
	 * 变更履历--个人
	 * 
	 * @param object
	 * @return
	 */
	public List viewEmpChangeInfoList(Object object);

	/**
	 * 预发令核查
	 * 
	 * @param request
	 * @return
	 */
	public List viewOrgPreExperirnceInsideList(Object object);

	/**
	 * 修改预发令信息
	 */
	@SuppressWarnings("unchecked")
	public int modifyPreExperirnceInsideInfo(Object object) throws Exception;

	/**
	 * 保存预发令信息到组织发令表
	 */
	@SuppressWarnings("unchecked")
	public int addPreExperirnceInsideInfo(Object object) throws Exception;

	/**
	 * 部门履历
	 */
	public List viewOrgInfoList(Object object);

	/**
	 * 部门长履历
	 */
	public List viewOrgManagerList(Object object);

	/**
	 * 检查部门领导是否重复任职
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	public int checkDeptManager(Object obj);

	/**
	 * 获取明细导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getDeptTempList(Object object);
	
	/**
	 * 获取明细导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getDeptTempCnt(Object object);
	
	/**
	 * 获取出错的明细导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getDeptTempErrorCnt(Object object);
}