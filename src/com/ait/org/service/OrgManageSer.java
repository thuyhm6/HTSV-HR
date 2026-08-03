package com.ait.org.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public interface OrgManageSer {
	@SuppressWarnings("unchecked")
	public List getDeptInfo(Map param);
	
	@SuppressWarnings("unchecked")
	public List getDeptInfoTree(Map param);	
	
	@SuppressWarnings("unchecked")
	public List getDeptLevel(Map param);
	
	@SuppressWarnings("unchecked")
	public Map  getOrganizationInfoForSearch(HttpServletRequest request);
	
	public String delOrganizationInfo(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getSelectDept(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getPostForChoose(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getCpnyForSelect(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public Map getOrganizationByChoosed(HttpServletRequest request);
	
	public String saveOrganization(HttpServletRequest request);
 
	@SuppressWarnings("unchecked")
	public List getDeptPost(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getOrganizationInfoList(HttpServletRequest request) throws Exception;
	
	public int getOrganizationInfoCnt(HttpServletRequest request) throws Exception;
	
	public int addOrganizationInfo(HttpServletRequest request);
	
	public int deleteOrganizationInfo(HttpServletRequest request);
	
	public Object getOrganizationInfo(HttpServletRequest request) ;
	
	public int updateOrganizationInfo(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public List getDeptNameListByActivity();
	
	@SuppressWarnings("unchecked")
	public List getDeptNameNameListByActivity(Object object);
	
	@SuppressWarnings("unchecked")
	public List getCpnyNameForChoose(HttpServletRequest request);
	
	public Object getDeptTopLevel(Object object);
	
	@SuppressWarnings("unchecked")
	public List getChildCodeList(HttpServletRequest request,String parentNo);
	
	@SuppressWarnings("unchecked")
	public List getEmpInfoInOrg(HttpServletRequest request);
	//查询人员
	public List viewStructure(HttpServletRequest request) throws Exception;
	
	public List getHrEmployeeByCpnyAndDeptNo(Map param)throws Exception; ;
	

	@SuppressWarnings("unchecked")
	public List getOrgStructureInfoList(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int getOrgStructureInfoListCnt(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getOrgStructureInfoExcelList(HttpServletRequest request) ;
	
	public int openOrgStructureInfo(HttpServletRequest request);
	
	public int closeOrgStructureInfo(HttpServletRequest request);
	
	/**
	 * 获取部门列表信息，供页面部门检索条件用
	 * 
	 * @param request
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getDeptList(HttpServletRequest request);
	
	/**
	 * 获取大区信息
	 * 
	 * @param request
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getPayAreaInfoList(HttpServletRequest request) throws Exception;
	
	/**
	 * 更新大区代码
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public int updatePayAreaInfo(HttpServletRequest request);
	
	/**
	 * 根据parent_code_no获取code列表信息，供页面code检索条件用
	 * 
	 * @param request
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getSyCodeList(HttpServletRequest request);
	
	/**
	 * 组织排序设置的list
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getOrgInfoOrderList(HttpServletRequest request) throws Exception;
	@SuppressWarnings("unchecked")
	public int getOrgOrgInfoOrderCnt(HttpServletRequest request) throws Exception;
	@SuppressWarnings("unchecked")
	public Object getOrgOrderInfo(HttpServletRequest request);
	
	/**
	 * 修改部门排序no
	 */
	public int updateOrgInfo(HttpServletRequest request);
	/**
	 * 分发人查看添加的No在数据表中是否存在
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int checkOrganizationNo(HttpServletRequest request);
	
	/**
	 * 获取部门排序导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getDeptOrderImportTempList(HttpServletRequest request) ;
	/**
	 * 获取部门排序导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getDeptOrderImportTempCnt(HttpServletRequest request, String errorFlag);
	/**
	 * 组装组织模版信息,导出带错误提示的数据
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public String getDeptOrderlateInfoByExcelData(HttpServletRequest request, List aliasNameList, List list,List mapList,List mapNameList) throws SQLException;
	/**
	 * 组织excel信息提交
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public String submitImportExcelDeptOrderData(HttpServletRequest request);
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	/**
	 * 获取部门列表信息，供页面部门检索条件用(概要)
	 * 
	 * @param request
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getResumeDeptList(HttpServletRequest request);
	public List viewResumeList(HttpServletRequest request);
	
	public int addResumeInfo(HttpServletRequest request) ;
	
	/**
	 * 概要NO
	 * @version V1.0
	 */
	public List getResumneNo(HttpServletRequest request);
	
	/**
	 * 最大部门no
	 * @version V1.0
	 */
	public List getDeptNoMax(HttpServletRequest request);
	/**
	 * 最大部门no
	 * @version V1.0
	 */
	public List getDeptNoMax1(HttpServletRequest request);
	/**
	 * 改编流程执行
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	public String executeResumeProcess(HttpServletRequest request) ;
	
	/**
	 * 流程执行状态
	 * @param 
	 * @return
	 */
	public List viewResumeProcess(HttpServletRequest request,List list);
	
	/**
	 * 获取默认deptNO
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public String getDefaultDeptNo(HttpServletRequest request);

	/**
	 * 获取部门信息
	 * @param request
	 * @return
	 */
	public List viewResumeDeptList(HttpServletRequest request) ;
	

	/**
	 * 获取人员信息
	 * @param request
	 * @return
	 */
	public List viewResumeEmpList(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public int deleteResumeInfo(HttpServletRequest request) ;
	
	/**
	 * 移动部门人员
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	public String executeMoveEmp(HttpServletRequest request);
	
	public int addOrgInfo(HttpServletRequest request);
	
	@SuppressWarnings("unchecked")
	public String deleteOrgInfo(HttpServletRequest request) ;
	
	/**
	 * 获取部门顺序
	 * @param request
	 * @return
	 */
	public List viewModifyOrgOrderNo(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public String modifyOrgOrderNo(HttpServletRequest request);

	@SuppressWarnings("unchecked")
	public String modifyOrgMerge(HttpServletRequest request);
	
	public List viewDeptManagerCheck(HttpServletRequest request);
	
	/**
	 * 发令核查
	 * @param request
	 * @return
	 */
	public List viewOrgExperirnceInsideList(HttpServletRequest request) ;
	public int addOrgExperirnceInsideInfo(HttpServletRequest request);
	public int deleteOrgExperirnceInsideInfo(HttpServletRequest request);
	
	public List viewChangeDetailOrgInfoList(HttpServletRequest request) ;
	
	public List viewChangeDetailEmpInfoList(HttpServletRequest request) ;
	
	/**
	 * 工作地管理
	 * @param request
	 * @return
	 */
	public List viewWorkAreaList(HttpServletRequest request);
	public int addWorkAreaInfo(HttpServletRequest request);
	public int deleteWorkAreaInfo(HttpServletRequest request);
	
	/**
	 * 成本中心管理
	 */
	public List viewCostCenterList(HttpServletRequest request);
	public String addCostCenterInfo(HttpServletRequest request) ;
	public int deleteCostCenterInfo(HttpServletRequest request);
	
	/**
	 * 成本业务管理
	 */
	public List viewBusinessList(HttpServletRequest request);
	public int addBusinessInfo(HttpServletRequest request) ;
	public int saveBusinessInfo(HttpServletRequest request) ;
	public int deleteBusinessInfo(HttpServletRequest request);
	public int deleteEmptyBusinessInfo(HttpServletRequest request);
	
	/**
	 * 保存部门管理者信息
	 */
	@SuppressWarnings("unchecked")
	public int saveDeptManager(HttpServletRequest request) ;
	
	/**
	 * 部门变更履历
	 * @param request
	 * @return
	 */
	public List viewOrgChangeInfoList(HttpServletRequest request);
	

	/**
	 * 保存部门变更履历备注
	 */
	@SuppressWarnings("unchecked")
	public int saveOrgChangeInfo(HttpServletRequest request) ;
	

	/**
	 * 当前组织 及 组织内人员信息
	 * @param request
	 * @return
	 */
	public List viewCurrentOrgDetailEmpInfo(HttpServletRequest request) ;
	

	/**
	 * 当前组织 信息
	 * @param request
	 * @return
	 */
	public List viewCurrentOrgDetailOrgInfo(HttpServletRequest request);
	

	/**
	 * 根据日期获取概要NO
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getHistoryResumneNo(HttpServletRequest request);
	
	/**
	 * 获取版本组织信息
	 * @param request
	 * @return
	 */
	public List viewResumeOrgList(HttpServletRequest request);
	
	/**
	 * 获取历史部门人员信息
	 * @param request
	 * @return
	 */
	public List viewHistoryResumeEmpList(HttpServletRequest request) ;

	/**
	 * 部门分割
	 * @param request
	 * @return
	 */
	public String modifyOrgSplit(HttpServletRequest request);

	/**
	 * 查询部门分割  人员临时信息
	 */
	public List viewOrgSplitTemp(HttpServletRequest request);

	/**
	 * 获取概要改编的状态
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public Map getResumeActivity(HttpServletRequest request,String resumeNo);
	

	/**
	 * 删除附件
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int deleteFile(HttpServletRequest request) ;

	/**
	 * 变更履历--个人
	 * @param request
	 * @return
	 */
	public List viewEmpChangeInfoList(HttpServletRequest request);

	/**
	 * 预发令核查
	 * @param request
	 * @return
	 */
	public List viewOrgPreExperirnceInsideList(HttpServletRequest request) ;

	/**
	 * 保存预发令信息
	 */
	@SuppressWarnings("unchecked")
	public int addPreExperirnceInsideInfo(HttpServletRequest request) ;

	/**
	 * 部门履历
	 */
	public List viewOrgInfoList(HttpServletRequest request);
	
	/**
	 * 部门长履历
	 */
	public List viewOrgManagerList(HttpServletRequest request);
	
	/**
	 * 检查部门领导是否重复任职
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int checkDeptManager(HttpServletRequest request);
	

	/**
	 * 获取明细导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getDeptTempList(HttpServletRequest request);
	
	/**
	 * 获取明细导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getDeptTempCnt(HttpServletRequest request, String errorFlag);


	/**
	 * 明细excel信息提交
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public String valImportExcelDeptData(HttpServletRequest request);


	/**
	 * 明细excel信息提交
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public String submitImportExcelDeptData(HttpServletRequest request);
}
