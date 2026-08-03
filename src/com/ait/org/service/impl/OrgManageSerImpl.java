package com.ait.org.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ait.org.dao.OrgManageDao;
import com.ait.org.service.OrgManageSer;
import com.ait.pa.dao.tempsale.PaTempSalesDAO;
import com.ait.sys.bean.AdminBean;
import com.ait.web.messages.Messages;
import com.ait.web.util.DateUtil;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;
import com.ait.web.util.UiUtil;

@Service
public class OrgManageSerImpl implements OrgManageSer {
	Logger logger = Logger.getLogger(OrgManageSerImpl.class);

	@Autowired
	private OrgManageDao orgManageDao;
	@Autowired
	private PaTempSalesDAO paTempSalesDAO;

	@SuppressWarnings("unchecked")
	public List getDeptInfo(Map param) {
		List list = new ArrayList();
		list = orgManageDao.getDeptInfo(param);
		return list;
	}

	@SuppressWarnings("unchecked")
	public List getDeptInfoTree(Map param) {
		List list = new ArrayList();
		list = orgManageDao.getDeptInfoTree(param);
		return list;
	}

	@SuppressWarnings("unchecked")
	public List getDeptLevel(Map param) {
		List list = new ArrayList();
		list = orgManageDao.getDeptLevel(param);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map getOrganizationInfoForSearch(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		return orgManageDao.getOrganizationInfoForSearch(paramMap,
				Integer.parseInt(request.getParameter("page").toString()),
				Integer.parseInt(request.getParameter("pagesize").toString()));
	}

	@SuppressWarnings("unchecked")
	@Override
	public String delOrganizationInfo(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		try {
			orgManageDao.deleteOrganizationInfo(paramMap);
			return "Y";
		} catch (Exception e) {
			e.printStackTrace();
			return "N";
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getPostForChoose(HttpServletRequest request) {
		List temp = null;
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		try {
			temp = this.orgManageDao.getPostForChoose(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return temp;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getCpnyForSelect(HttpServletRequest request) {
		List temp = null;
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		try {
			temp = this.orgManageDao.getCpnyForSelect(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return temp;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getSelectDept(HttpServletRequest request) {
		List temp = null;
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		try {
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			paramMap.put("specialParam", admin.getSpecialParam());
			paramMap.put("userNo", admin.getUserNo());
			paramMap.put("deptNo", admin.getDeptNo());
			temp = this.orgManageDao.getSelectDept(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return temp;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map getOrganizationByChoosed(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		if (paramMap.get("deptid") != null) {
			try {
				return this.orgManageDao.getOrganizationByChoosed(paramMap);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getCpnyNameForChoose(HttpServletRequest request) {
		List temp = null;
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		try {
			temp = this.orgManageDao.getCpnyNameForChoose(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return temp;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String saveOrganization(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		//String[] postids = request.getParameterValues("isChecked");
		//paramMap.put("postids", postids);
		paramMap.put("adminid", admin.getAdminID());
		try {
			this.orgManageDao.addOrganizationInfo(paramMap);
			return "Y";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "N";
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getDeptPost(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		List temp = null;
		try {
			temp = this.orgManageDao.getDeptPost(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return temp;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int updateOrganizationInfo(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("adminid", admin.getAdminID());
		paramMap.put("CREATED_BY", admin.getAdminID());
		paramMap.put("DEPT_TYPE", request.getParameter("DEPT_TYPE"));
		paramMap.put("UPDATED_BY", admin.getAdminID());
		paramMap.put("ORDERNO1", request.getParameter("ORDERNO"));
		Map paramer = ObjectBindUtil.getRequestParamData(request);
		paramer.put("ORDERNO", request.getParameter("ORDERNO"));
		paramer.put("DEPTNO1",request.getParameter("DEPTNO"));
		paramer.put("DEPTNO","");
		paramer.put("deptId","");
		//如果要是修改当前组织的orderno则后面的组织orderno都要加1
		if (request.getParameter("ORDERNO") != null
				&& !"".equals(request.getParameter("ORDERNO"))) {
			List retrunList = orgManageDao.getOrganizationInfoList(paramer);
			if (retrunList != null && retrunList.size() > 0) {
				for (int i = 0; i < retrunList.size(); i++) {
					Map deptInfo = (Map) retrunList.get(i);
					paramer.put("DEPTNO", deptInfo.get("DEPTNO"));
					paramer.put("ORDERNO", Integer.parseInt(deptInfo.get("ORDERNO").toString())+1);
					this.orgManageDao.updateOrganizationNo(paramer);
				}
			}
		}
		try {
			this.orgManageDao.saveOrganization(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@SuppressWarnings("unchecked")
	public List getOrganizationInfoList(HttpServletRequest request)
			throws Exception {
		List retrunList = new ArrayList();
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		if (UiUtil.getPageNum(request) > 0) {
			retrunList = orgManageDao.getOrganizationInfoList(paramMap,
					UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = orgManageDao.getOrganizationInfoList(paramMap);
		}

		return retrunList;
	}

	@SuppressWarnings("unchecked")
	public int getOrganizationInfoCnt(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("deptNo", admin.getDeptNo());

		return orgManageDao.getOrganizationInfoCnt(paramMap);
	}

	@SuppressWarnings("unchecked")
	public int addOrganizationInfo(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		try {
			paramMap.put("adminid", admin.getAdminID());
			paramMap.put("CREATED_BY", admin.getAdminID());
			paramMap.put("UPDATED_BY", admin.getAdminID());
			this.orgManageDao.addOrganizationInfo(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int deleteOrganizationInfo(HttpServletRequest request) {
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		try {
			this.orgManageDao.deleteOrganizationInfo(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@SuppressWarnings("unchecked")
	public List getDeptNameListByActivity() {
		return this.orgManageDao.getDeptNameListByActivity();
	}

	@SuppressWarnings("unchecked")
	public List getDeptNameNameListByActivity(Object object) {
		return orgManageDao.getDeptNameNameListByActivity(object);
	}

	@SuppressWarnings("unchecked")
	public Object getOrganizationInfo(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		return this.orgManageDao.getOrganizationInfo(paramMap);
	}

	public Object getDeptTopLevel(Object object) {
		return this.orgManageDao.getDeptTopLevel(object);
	}

	/**
	 * 根据父级的parentNo，查询所属地区 or 部门类型 or 部门区分 的所有子集code
	 */
	@SuppressWarnings("unchecked")
	public List getChildCodeList(HttpServletRequest request, String parentNo) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("parentNo", parentNo);
		return this.orgManageDao.getChildCodeList(paramMap);
	}

	@SuppressWarnings("unchecked")
	public List getEmpInfoInOrg(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		return this.orgManageDao.getEmpInfoInOrg(paramMap);
	}

	// 查询人员
	@SuppressWarnings("unchecked")
	public List viewStructure(HttpServletRequest request) throws Exception {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		return this.orgManageDao.viewStructure(paramMap);
	}

	public List getHrEmployeeByCpnyAndDeptNo(Map paramMap) throws Exception {

		return this.orgManageDao.getHrEmployeeByCpnyAndDeptNo(paramMap);
	}

	/**
	 * 部门组织结构导出(export the org info)
	 * 
	 * @param request
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getOrgStructureInfoList(HttpServletRequest request) {
		List returnList = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		paramMap.put("ADMINID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("USER_NO", admin.getUserNo() != null ? admin.getUserNo()
				.toString() : admin.getUsername());
		String parent_deptNo = request.getParameter("seach_PARENT_DEPT_NO") != null ? request
				.getParameter("seach_PARENT_DEPT_NO").toString() : "";
		if (parent_deptNo == null || "".equals(parent_deptNo)) {
			return returnList;
		}
		if (UiUtil.getPageNum(request) > 0) {
			returnList = orgManageDao.getOrgStructureInfoList(paramMap,
					UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			returnList = orgManageDao.getOrgStructureInfoList(paramMap);
		}
		return returnList;
	}

	/**
	 * 部门组织结构导出(export the org info)
	 * 
	 * @param request
	 * @return int
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getOrgStructureInfoListCnt(HttpServletRequest request) {
		int returnInt = 0;
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		paramMap.put("ADMINID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("USER_NO", admin.getUserNo() != null ? admin.getUserNo()
				.toString() : admin.getUsername());
		String deptno = request.getParameter("seach_PARENT_DEPT_NO") != null ? request
				.getParameter("seach_PARENT_DEPT_NO").toString() : "";
		if (deptno == null || "".equals(deptno)) {
			return 0;
		}
		returnInt = orgManageDao.getOrgStructureInfoListCnt(paramMap);
		return returnInt;
	}

	/**
	 * 部门组织结构导出(export the org info)
	 * 
	 * @param request
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getOrgStructureInfoExcelList(HttpServletRequest request) {
		List returnList = new ArrayList();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		paramMap.put("ADMINID", admin.getAdminID());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("USER_NO", admin.getUserNo() != null ? admin.getUserNo()
				.toString() : admin.getUsername());
		String parent_deptNo = request.getParameter("seach_PARENT_DEPT_NO") != null ? request
				.getParameter("seach_PARENT_DEPT_NO").toString() : "";
		if (parent_deptNo == null || "".equals(parent_deptNo)) {
			return returnList;
		}
		returnList = orgManageDao.getOrgStructureInfoList(paramMap);
		return returnList;
	}

	/**
	 * 启用某个部门
	 * 
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public int openOrgStructureInfo(HttpServletRequest request) {
		int result = 1;
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		try {
			String deptNo = request.getParameter("DEPTNO") != null ? request
					.getParameter("DEPTNO") : "";
			String flag = request.getParameter("FLAG") != null ? request
					.getParameter("FLAG") : "";
			if ("".equals(deptNo) || "".equals(flag)) {
				result = 2;
			}
			paramMap.put("adminid", admin.getAdminID());
			paramMap.put("CPNY_ID", admin.getCpnyId() != null ? admin
					.getCpnyId().toString() : "");
			paramMap.put("CREATED_BY", admin.getAdminID());
			paramMap.put("UPDATED_BY", admin.getAdminID());
			result = this.orgManageDao.openOrgStructureInfo(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return result;
	}

	/**
	 * 关闭某个部门
	 * 
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public int closeOrgStructureInfo(HttpServletRequest request) {
		int result = 1;// 成功
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		try {
			String deptNo = request.getParameter("DEPTNO") != null ? request
					.getParameter("DEPTNO") : "";
			String flag = request.getParameter("FLAG") != null ? request
					.getParameter("FLAG") : "";
			if ("".equals(deptNo) || "".equals(flag)) {
				result = 2;// 失败
			}
			// 判断要关闭的部门是否存在在职人员，如果存在不允许关闭此部门
			List empList = (ArrayList) this.orgManageDao
					.getEmpInfoInOrg(paramMap);
			if (empList != null && empList.size() >= 1) {
				// 要关闭的部门存在在职人员，不允许关闭！
				result = 3;
			}
			paramMap.put("adminid", admin.getAdminID());
			paramMap.put("CPNY_ID", admin.getCpnyId() != null ? admin
					.getCpnyId().toString() : "");
			paramMap.put("CREATED_BY", admin.getAdminID());
			paramMap.put("UPDATED_BY", admin.getAdminID());
			result = this.orgManageDao.closeOrgStructureInfo(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return result;
	}

	
	/**
	 * 获取大区信息
	 * 
	 * @param request
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getPayAreaInfoList(HttpServletRequest request) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("ADMINID", admin.getAdminID());
		paramMap.put("CPNYID", admin.getCpnyId());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("interLanguage", admin.getLanguage());
		try {
			return this.orgManageDao.getPayAreaInfoList(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 更新大区代码
	 * 
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public int updatePayAreaInfo(HttpServletRequest request) {
		int result = 1;
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		try {
			int count = Integer.parseInt(request.getParameter("count"));
			paramMap.put("adminid", admin.getAdminID());
			paramMap.put("CPNY_ID", admin.getCpnyId() != null ? admin
					.getCpnyId().toString() : "");
			for (int i = 0; i < count; i++) {// 先循环保存大区编码信息
				paramMap.put("PAY_AREA_CD",
						request.getParameter("PAY_AREA_CD" + i));
				paramMap.put("DEPTNO", request.getParameter("DEPTNO" + i));
				result = this.orgManageDao.updatePayAreaInfo(paramMap);
			}
			// result = this.orgManageDao.updateEmpPayAreaInfo(paramMap)
			// ;//不需要再修改人员大区信息
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return result;
	}

	/**
	 * 根据parent_code_no获取code列表信息，供页面code检索条件用
	 * 
	 * @param request
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getSyCodeList(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("ADMINID", admin.getAdminID());
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("interLanguage", admin.getLanguage());
		try {
			return this.orgManageDao.getSyCodeList(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List getOrgInfoOrderList(HttpServletRequest request)
			throws Exception {
		List retrunList = new ArrayList();
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("specialParam", admin.getSpecialParam());
		if (UiUtil.getPageNum(request) > 0) {
			retrunList = orgManageDao.getOrgInfoOrderList(paramMap,
					UiUtil.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = orgManageDao.getOrgInfoOrderList(paramMap);
		}

		return retrunList;
	}

	@SuppressWarnings("unchecked")
	public int getOrgOrgInfoOrderCnt(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("deptNo", admin.getDeptNo());
		return orgManageDao.getOrgOrgInfoOrderCnt(paramMap);
	}
	
	@SuppressWarnings("unchecked")
	public Object getOrgOrderInfo(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		return this.orgManageDao.getOrgInfoOrder(paramMap);
	}
	
	/**
	 * 修改部门排序no
	 */
	public int updateOrgInfo(HttpServletRequest request){
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		Map paramer = ObjectBindUtil.getRequestParamData(request);
		//如果要是修改当前组织的orderno则后面的组织orderno都要加1
			Object retrunObj = orgManageDao.getOrgInfoOrder(paramer);
			if (retrunObj != null) {
				Map deptInfo = (Map) retrunObj;
				paramer.put("DEPTNO", deptInfo.get("DEPTNO"));
				paramer.put("CPNY_ID", deptInfo.get("CPNY_ID"));
				paramer.put("ORDERNO", request.getParameter("ORDERNO"));
				paramMap.put("UPDATED_BY", admin.getPersonId());
				this.orgManageDao.updateOrganizationNo(paramer);
			}
		return 1;
	}
	
	/**
	 * 分发人查看添加的No在数据表中是否存在
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int checkOrganizationNo(HttpServletRequest request){
		int retrunInt = 0;
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("language", Messages.getLanguage(request));
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		retrunInt = orgManageDao.checkOrganizationNo(paramMap);
		return retrunInt;
	}
	
	/**
	 * 获取部门排序导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getDeptOrderImportTempList(HttpServletRequest request){
		List retrunList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		if (UiUtil.getPageNum(request) > 0) {
			retrunList = orgManageDao.getDeptOrderImportTempList(paramMap, UiUtil
					.getPageNum(request), UiUtil.getNumPerPage(request));
		} else {
			retrunList = orgManageDao.getDeptOrderImportTempList(paramMap);
		}
		return retrunList;
	}
	
	/**
	 * 获取部门排序导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getDeptOrderImportTempCnt(HttpServletRequest request, String errorFlag){
		int retrunInt = 0;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		if("E".equals(errorFlag)){
			retrunInt = orgManageDao.getDeptOrderImportTempErrorCnt(paramMap);
		}else{
			retrunInt = orgManageDao.getDeptOrderImportTempCnt(paramMap);
		}

		return retrunInt;
	}
	/**
	 * 组装排序模版信息,导出带错误提示的数据
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public String getDeptOrderlateInfoByExcelData(HttpServletRequest request, List aliasNameList, List list,List mapList,List mapNameList) throws SQLException{

		LinkedHashMap paramMap = new LinkedHashMap();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("interCpnyID", admin.getCpnyId());
		paramMap.put("interLanguage", admin.getLanguage());
		
		//模版名称
		String name = "";
		aliasNameList.add("公司ID *");
		aliasNameList.add("组织NO *");
		aliasNameList.add("排序NO *");
		aliasNameList.add("验证结果");
		List deptTempList = this.orgManageDao.getDeptOrderImportTempList(paramMap, -1, -1);
		for(int i=0;i<deptTempList.size();i++){
			LinkedHashMap map = new LinkedHashMap();
			LinkedHashMap map1 = (LinkedHashMap)deptTempList.get(i);
			map.put("CELL0", map1.get("CPNY_ID") == null ? "" : map1.get("CPNY_ID"));
			map.put("CELL1", map1.get("DEPTNO") == null ? "" : map1.get("DEPTNO"));
			map.put("CELL2", map1.get("ORDERNO") == null ? "" : map1.get("ORDERNO"));
			map.put("CELL10", map1.get("UPLOAD_ERROR_MSG") == null ? "" : map1.get("UPLOAD_ERROR_MSG"));
				list.add(map);
			mapNameList.add("公司名称参考");
			mapNameList.add("组织NO参考");
			mapList.add("SELECT '[' || T.CPNY_ID || ']' || T.CPNY_LOCATION CONTENT FROM HR_COMPANY T");
			mapList.add("SELECT '[' || U.DEPTNO || ']' || U.ORG_NAME_LOCAL CONTENT FROM HR_DEPARTMENT U WHERE U.CPNY_ID = '"+admin.getCpnyId()+"' AND U.USE_YN = 'Y'");
			name = "DEPT_ORDER_DATA";
		}
		return name;
	}
	
	/**
	 * 组织排序excel信息提交
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public String submitImportExcelDeptOrderData(HttpServletRequest request){
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("PR_NAME", "pkg_dept_order_excel_imp.pr_import_temp_deptorder_data");
		try {
			return this.orgManageDao.importDeptOrderFromExcel(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 获取部门列表信息，供页面部门检索条件用
	 * 
	 * @param request
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getDeptList(HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("ADMINID", admin.getAdminID());
		if (paramMap.get("CPNY_ID") == null
				|| "".equals(paramMap.get("CPNY_ID").toString())) {
			paramMap.put("CPNY_ID", admin.getCpnyId());
		}
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("interLanguage", admin.getLanguage());
		try {
			return this.orgManageDao.getDeptList(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取部门列表信息，供页面部门检索条件用(概要)
	 * 
	 * @param request
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getResumeDeptList(HttpServletRequest request) {

		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		try {
			return this.orgManageDao.getResumeDeptList(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List viewResumeList(HttpServletRequest request) {
		List list = new ArrayList();
		LinkedHashMap param = ObjectBindUtil.getRequestParamData(request,"seach_");
		list = orgManageDao.viewResumeList(param);
		return list;
	}

	@SuppressWarnings("unchecked")
	public int addResumeInfo(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		try {
			this.orgManageDao.addResumeInfo(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	

	@SuppressWarnings("unchecked")
	public int deleteResumeInfo(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		try {
			this.orgManageDao.deleteResumeInfo(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 概要NO
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getResumneNo(HttpServletRequest request){
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		List list = null;
		try {
			list = this.orgManageDao.getResumneNo(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
		/**
		 * deptnomax
		 * 
		 * @Copyright: AIT (c)
		 * @Company: AIT
		 * @author wendi@ait.net.cn
		 * @date 2014-7-03
		 * @version V1.0
		 */
	public List getDeptNoMax(HttpServletRequest request){
			// 页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
			List list = null;
			try {
				list = this.orgManageDao.getDeptNoMax(paramMap);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return list;
	}
	
	/**
	 * deptnomax
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getDeptNoMax1(HttpServletRequest request){
			// 页面提交数据
			LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
			List list = null;
			try {
				list = this.orgManageDao.getDeptNoMax1(paramMap);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return list;
	}
	
	/**
	 * 改编流程执行
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	public String executeResumeProcess(HttpServletRequest request) {
		String returnString = "" ;		
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		try {
			returnString = this.orgManageDao.executeResumeProcess(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
		return returnString;
	}
	
	/**
	 * 流程执行状态
	 * @param 
	 * @return
	 */
	public List viewResumeProcess(HttpServletRequest request,List list) {
		List proList = new ArrayList();
		LinkedHashMap param = ObjectBindUtil.getRequestParamData(request,"seach_");
		if(param.get("RESUME_NO") == null || "".equals(param.get("RESUME_NO"))){
			if(list != null && list.size() > 0){
				Map resumeInfoMap = (LinkedHashMap) list.get(0);
				param.put("RESUME_NO", resumeInfoMap.get("NO"));
			}else{
				param.put("RESUME_NO", "0");
			}
		}
		proList = orgManageDao.viewResumeProcess(param);
		return proList;
	}
	

	/**
	 * 获取默认deptNO
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public String getDefaultDeptNo(HttpServletRequest request){
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		String str = "";
		try {
			str = this.orgManageDao.getDefaultDeptNo(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}
	

	/**
	 * 获取部门信息
	 * @param request
	 * @return
	 */
	public List viewResumeDeptList(HttpServletRequest request) {
		List list = new ArrayList();
		LinkedHashMap param = ObjectBindUtil.getRequestParamData(request);
		list = orgManageDao.viewResumeDeptList(param);
		return list;
	}
	

	/**
	 * 获取人员信息
	 * @param request
	 * @return
	 */
	public List viewResumeEmpList(HttpServletRequest request) {
		List list = new ArrayList();
		LinkedHashMap param = ObjectBindUtil.getRequestParamData(request);
		list = orgManageDao.viewResumeEmpList(param);
		return list;
	}
	
	/**
	 * 移动部门人员
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	public String executeMoveEmp(HttpServletRequest request) {
		String returnString = "" ;		
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		try {
			returnString = this.orgManageDao.executeMoveEmp(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
		return returnString;
	}
	

	@SuppressWarnings("unchecked")
	public int addOrgInfo(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		int flag = 1;
		try {
			flag = this.orgManageDao.addOrgInfo(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return flag;
	}
	

	@SuppressWarnings("unchecked")
	public String deleteOrgInfo(HttpServletRequest request) {
		String returnString = "" ;		
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		try {
			returnString = this.orgManageDao.deleteOrgInfo(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
		return returnString;
	}
	

	/**
	 * 获取部门顺序
	 * @param request
	 * @return
	 */
	public List viewModifyOrgOrderNo(HttpServletRequest request) {
		List list = new ArrayList();
		LinkedHashMap param = ObjectBindUtil.getRequestParamData(request);
		list = orgManageDao.viewModifyOrgOrderNo(param);
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public String modifyOrgOrderNo(HttpServletRequest request) {
		String returnString = "OK" ;		
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		String[] deptNo = request.getParameterValues("DEPTNO");
		try {
			if(deptNo != null && deptNo.length > 0){
				for(int i = 0; i < deptNo.length; i++){
					paramMap.put("ORDER_DEPTNO", deptNo[i]);
					paramMap.put("ORDER_NO", i + 1);
					this.orgManageDao.modifyOrgOrderNo(paramMap);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
		return returnString;
	}
	
	@SuppressWarnings("unchecked")
	public String modifyOrgMerge(HttpServletRequest request) {
		String returnString = "" ;		
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		String[] deptNo = request.getParameterValues("upDept");
		String deptNos = "";
		try {
			if(deptNo != null && deptNo.length > 0){
				for(int i = 0; i < deptNo.length; i++){
					if(i==0){
						deptNos += deptNo[i];
					}else{
						deptNos += "," + deptNo[i];
					}
				}
				paramMap.put("UP_DEPTNOS", deptNos);
			}
			returnString = this.orgManageDao.modifyOrgMerge(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
		return returnString;
	}

	@SuppressWarnings("unchecked")
	public String modifyOrgSplit(HttpServletRequest request) {
		String returnString = "" ;		
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		String[] empid = request.getParameterValues("empid");
		String empids = "";
		try {
			if(empid != null && empid.length > 0){
				for(int i = 0; i < empid.length; i++){
					if(i==0){
						empids += empid[i];
					}else{
						empids += "," + empid[i];
					}
				}
				paramMap.put("EMPIDS", empids);
			}
			returnString = this.orgManageDao.modifyOrgSplit(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
		return returnString;
	}
	
	public List viewDeptManagerCheck(HttpServletRequest request) {
		List list = new ArrayList();
		LinkedHashMap param = ObjectBindUtil.getRequestParamData(request,"seach_");
		list = orgManageDao.viewDeptManagerCheck(param);
		return list;
	}
	
	/**
	 * 发令核查
	 */
	public List viewOrgExperirnceInsideList(HttpServletRequest request) {
		List list = new ArrayList();
		LinkedHashMap param = ObjectBindUtil.getRequestParamData(request,"seach_");
		list = orgManageDao.viewOrgExperirnceInsideList(param);
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public int addOrgExperirnceInsideInfo(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		try {
			this.orgManageDao.addOrgExperirnceInsideInfo(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	

	@SuppressWarnings("unchecked")
	public int deleteOrgExperirnceInsideInfo(HttpServletRequest request) {
		try {
			String jsonString = request.getParameter("jsonData") ;
			List<LinkedHashMap<String, Object>> dataList = ObjectBindUtil.getRequestJsonData(jsonString) ;
			this.orgManageDao.deleteOrgExperirnceInsideInfo(dataList);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	public List viewChangeDetailOrgInfoList(HttpServletRequest request) {
		List list = new ArrayList();
		LinkedHashMap param = ObjectBindUtil.getRequestParamData(request);
		list = orgManageDao.viewChangeDetailOrgInfoList(param);
		return list;
	}

	public List viewChangeDetailEmpInfoList(HttpServletRequest request) {
		List list = new ArrayList();
		LinkedHashMap param = ObjectBindUtil.getRequestParamData(request);
		list = orgManageDao.viewChangeDetailEmpInfoList(param);
		return list;
	}
	
	/**
	 * 工作地管理
	 */
	public List viewWorkAreaList(HttpServletRequest request) {
		List list = new ArrayList();
		LinkedHashMap param = ObjectBindUtil.getRequestParamData(request,"seach_");
		list = orgManageDao.viewWorkAreaList(param);
		return list;
	}

	@SuppressWarnings("unchecked")
	public int addWorkAreaInfo(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		try {
			this.orgManageDao.addWorkAreaInfo(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	

	@SuppressWarnings("unchecked")
	public int deleteWorkAreaInfo(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		try {
			this.orgManageDao.deleteWorkAreaInfo(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	
	/**
	 * 成本中心管理
	 */
	public List viewCostCenterList(HttpServletRequest request) {
		List list = new ArrayList();
		LinkedHashMap param = ObjectBindUtil.getRequestParamData(request,"seach_");
		list = orgManageDao.viewCostCenterList(param);
		return list;
	}

	@SuppressWarnings("unchecked")
	public String addCostCenterInfo(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		String seq = "";
		try {
			seq = this.orgManageDao.addCostCenterInfo(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
		return seq;
	}
	

	@SuppressWarnings("unchecked")
	public int deleteCostCenterInfo(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		try {
			this.orgManageDao.deleteCostCenterInfo(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	

	/**
	 * 部门业务管理
	 */
	public List viewBusinessList(HttpServletRequest request) {
		List list = new ArrayList();
		LinkedHashMap param = ObjectBindUtil.getRequestParamData(request,"seach_");
		list = orgManageDao.viewBusinessList(param);
		return list;
	}

	@SuppressWarnings("unchecked")
	public int addBusinessInfo(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		try {
			this.orgManageDao.addBusinessInfo(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	

	@SuppressWarnings("unchecked")
	public int saveBusinessInfo(HttpServletRequest request) {
		try {
			String jsonString = request.getParameter("jsonData") ;
			List<LinkedHashMap<String, Object>> dataList = ObjectBindUtil.getRequestJsonData(jsonString) ;
			this.orgManageDao.saveBusinessInfo(dataList);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	@SuppressWarnings("unchecked")
	public int deleteBusinessInfo(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		try {
			this.orgManageDao.deleteBusinessInfo(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	

	@SuppressWarnings("unchecked")
	public int deleteEmptyBusinessInfo(HttpServletRequest request) {
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		try {
			this.orgManageDao.deleteEmptyBusinessInfo(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 保存部门管理者信息
	 */
	@SuppressWarnings("unchecked")
	public int saveDeptManager(HttpServletRequest request) {
		try {
			String jsonString = request.getParameter("jsonData") ;
			List<LinkedHashMap<String, Object>> dataList = ObjectBindUtil.getRequestJsonData(jsonString) ;
			this.orgManageDao.saveDeptManager(dataList);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	public List viewOrgChangeInfoList(HttpServletRequest request) {
		List list = new ArrayList();
		LinkedHashMap param = ObjectBindUtil.getRequestParamData(request,"seach_");
		list = orgManageDao.viewOrgChangeInfoList(param);
		return list;
	}
	

	/**
	 * 保存部门变更履历备注
	 */
	@SuppressWarnings("unchecked")
	public int saveOrgChangeInfo(HttpServletRequest request) {
		try {
			String jsonString = request.getParameter("jsonData") ;
			List<LinkedHashMap<String, Object>> dataList = ObjectBindUtil.getRequestJsonData(jsonString) ;
			this.orgManageDao.saveOrgChangeInfo(dataList);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 当前组织内人员信息
	 * @param request
	 * @return
	 */
	public List viewCurrentOrgDetailEmpInfo(HttpServletRequest request) {
		List list = new ArrayList();
		LinkedHashMap param = ObjectBindUtil.getRequestParamData(request);
		list = orgManageDao.viewCurrentOrgDetailEmpInfo(param);
		return list;
	}
	

	/**
	 * 当前组织 信息
	 * @param request
	 * @return
	 */
	public List viewCurrentOrgDetailOrgInfo(HttpServletRequest request) {
		List list = new ArrayList();
		LinkedHashMap param = ObjectBindUtil.getRequestParamData(request);
		list = orgManageDao.viewCurrentOrgDetailOrgInfo(param);
		return list;
	}
	

	/**
	 * 根据日期获取概要NO
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getHistoryResumneNo(HttpServletRequest request){
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,"search_");
		if(paramMap.get("RESUME_DATE") == null || "".equals(StringUtil.checkNull(paramMap.get("RESUME_DATE")))){
			paramMap.put("RESUME_DATE",DateUtil.getSysdateStr("dd/MM/yyyy"));
		}
		List list = null;
		try {
			list = this.orgManageDao.getHistoryResumneNo(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 获取版本组织信息
	 * @param request
	 * @return
	 */
	public List viewResumeOrgList(HttpServletRequest request) {
		List list = new ArrayList();
		LinkedHashMap param = ObjectBindUtil.getRequestParamData(request);
		list = orgManageDao.viewResumeOrgList(param);
		return list;
	}
	
	/**
	 * 获取历史部门人员信息
	 * @param request
	 * @return
	 */
	public List viewHistoryResumeEmpList(HttpServletRequest request) {
		List list = new ArrayList();
		LinkedHashMap param = ObjectBindUtil.getRequestParamData(request);
		list = orgManageDao.viewHistoryResumeEmpList(param);
		return list;
	}
	
	/**
	 * 查询部门分割  人员临时信息
	 */
	public List viewOrgSplitTemp(HttpServletRequest request) {
		List list = new ArrayList();
		LinkedHashMap param = ObjectBindUtil.getRequestParamData(request,"seach_");
		list = orgManageDao.viewOrgSplitTemp(param);
		return list;
	}
	

	/**
	 * 获取概要改编的状态
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public Map getResumeActivity(HttpServletRequest request,String resumeNo){
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("RESUME_NO", resumeNo);
		String str = "";
		LinkedHashMap map = new LinkedHashMap();
		try {
			List list = this.orgManageDao.getResumeActivity(paramMap);
			if(list != null && list.size() > 0){
				map = (LinkedHashMap)list.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 删除附件
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int deleteFile(HttpServletRequest request) {
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		try {
			this.orgManageDao.deleteFile(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 变更履历--个人
	 * @param request
	 * @return
	 */
	public List viewEmpChangeInfoList(HttpServletRequest request) {
		List list = new ArrayList();
		LinkedHashMap param = ObjectBindUtil.getRequestParamData(request,"seach_");
		list = orgManageDao.viewEmpChangeInfoList(param);
		return list;
	}

	/**
	 * 预发令核查
	 * @param request
	 * @return
	 */
	public List viewOrgPreExperirnceInsideList(HttpServletRequest request) {
		List list = new ArrayList();
		LinkedHashMap param = ObjectBindUtil.getRequestParamData(request,"seach_");
		list = orgManageDao.viewOrgPreExperirnceInsideList(param);
		return list;
	}
	
	/**
	 * 保存预发令信息
	 */
	@SuppressWarnings("unchecked")
	public int addPreExperirnceInsideInfo(HttpServletRequest request) {
		try {
			String jsonString = request.getParameter("jsonData") ;
			List<LinkedHashMap<String, Object>> dataList = ObjectBindUtil.getRequestJsonData(jsonString) ;
			this.orgManageDao.addPreExperirnceInsideInfo(dataList);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 部门履历
	 */
	public List viewOrgInfoList(HttpServletRequest request) {
		List list = new ArrayList();
		LinkedHashMap param = ObjectBindUtil.getRequestParamData(request);
		if(param.get("DEPTNO").equals("")||param.get("DEPTNO")==null){
			param.put("DEPTNO", param.get("CPNY_ID").toString().substring(param.get("CPNY_ID").toString().lastIndexOf("_")+1));
		}
		list = orgManageDao.viewOrgInfoList(param);
		return list;
	}
	
	/**
	 * 部门长履历
	 */
	public List viewOrgManagerList(HttpServletRequest request) {
		List list = new ArrayList();
		LinkedHashMap param = ObjectBindUtil.getRequestParamData(request);
		if(param.get("DEPTNO").equals("")||param.get("DEPTNO")==null){
			param.put("DEPTNO", param.get("CPNY_ID").toString().substring(param.get("CPNY_ID").toString().lastIndexOf("_")+1));
		}
		list = orgManageDao.viewOrgManagerList(param);
		return list;
	}
	
	/**
	 * 检查部门领导是否重复任职
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int checkDeptManager(HttpServletRequest request) {
		int cnt=0;
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request);
		try {
			cnt = this.orgManageDao.checkDeptManager(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return cnt;
	}
	

	/**
	 * 获取明细导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getDeptTempList(HttpServletRequest request) {
		List retrunList = new ArrayList();
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		retrunList = orgManageDao.getDeptTempList(paramMap);
		return retrunList;
	}
	
	/**
	 * 获取明细导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getDeptTempCnt(HttpServletRequest request, String errorFlag){
		int retrunInt = 0;
		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		if("E".equals(errorFlag)){
			retrunInt = orgManageDao.getDeptTempErrorCnt(paramMap);
		}else{
			retrunInt = orgManageDao.getDeptTempCnt(paramMap);
		}

		return retrunInt;
	}

	/**
	 * 明细excel信息提交
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public String valImportExcelDeptData(HttpServletRequest request){

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("PR_NAME", "PKG_RESUME_PROCESS.PR_VALID_DEPT_DATA");
		try {
			return this.paTempSalesDAO.importFromExcel(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}


	/**
	 * 明细excel信息提交
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public String submitImportExcelDeptData(HttpServletRequest request){

		// 页面提交数据
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		paramMap.put("PERSON_ID", admin.getPersonId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("PR_NAME", "PKG_RESUME_PROCESS.PR_IMPORT_DEPT_DATA");
		try {
			return this.paTempSalesDAO.importFromExcel(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
}
