package com.ait.org.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;
import org.apache.commons.lang.ObjectUtils;

import com.ait.org.dao.OrgManageDao;
import com.ait.sys.dao.SyLanguageDao;
import com.ait.web.util.SqlMapClientSupport;
import com.ait.web.util.StringUtil;

@Repository
public class OrgManageDaoImpl extends SqlMapClientSupport implements
		OrgManageDao {

	@Autowired
	private SyLanguageDao syLanguageDao;

	@SuppressWarnings("unchecked")
	public List getDeptInfo(Object object) {
		List result = null;
		try {
			result = this.queryForList("org.manage.getDeptInfo", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public List getDeptInfoTree(Object object) {
		List result = null;
		try {
			result = this.queryForList("org.manage.getDeptInfoTree", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public List getDeptLevel(Object object) {
		List result = null;
		try {
			result = this.queryForList("org.manage.getDeptLevel", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map getOrganizationInfoForSearch(Object object, int skipResults,
			int maxResults) {
		Map object2 = new LinkedHashMap();
		try {
			List Rows = this.queryForList("org.manage.searchOrganizationInfo",
					object, skipResults, maxResults);
			Integer count = (Integer) this.queryForObject(
					"org.manage.searchOrganizationInfoCnt", object);
			object2.put("Rows", Rows);
			object2.put("Total", count);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return object2;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getPostForChoose(Object object) throws Exception {
		List temp = this.queryForList("org.manage.getPostForChoose", object);
		return temp;
	}

	@SuppressWarnings("unchecked")
	public List getCpnyNameForChoose(Object object) throws Exception {
		List temp = this
				.queryForList("org.manage.getCpnyNameForChoose", object);
		return temp;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getCpnyForSelect(Object object) throws Exception {
		List temp = this.queryForList("org.manage.getCpnyForSelect", object);
		return temp;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getSelectDept(Object obj) throws Exception {
		List temp = this.queryForList("org.manage.getSelectDept", obj);
		return temp;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map getOrganizationByChoosed(Object object) throws Exception {
		return (Map) this.queryForObject("org.manage.getDeptById", object);
	}

	/**
	 * 插入部门信息insert
	 * 
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int addOrganizationInfo(Object object) throws Exception {
		Map parsers = (Map) object;
		if (((LinkedHashMap) object).get("DEPTNO") != null) {
			// 需要update
			this.updateDeptName(object);
			this.update("org.manage.updateOrganization", object);
		} else {
			this.insertDepartmentName(object);
			this.insert("org.manage.addOrganizationInfo", object);
		}
		return 1;
	}

	/**
	 * update组织信息
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void saveOrganization(Object object) throws Exception {
		LinkedHashMap obj = (LinkedHashMap) object;
		if (!"".equals(obj.get("ORDERNO")) && obj.get("ORDERNO") != null) {
			String orderno = obj.get("ORDERNO").toString();
		}
		if (((LinkedHashMap) object).get("DEPTNO") != null) {
			this.updateDeptName(object);
			this.update("org.manage.updateOrganization", obj);
		} else {
			this.insert("org.manage.addOrganizationInfo", object);
		}
		Map parsers = (Map) object;
	}

	/**
	 * update组织信息paixu
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void updateOrganizationNo(Object object) {
		if (((LinkedHashMap) object).get("DEPTNO") != null) {
			try {
				this.update("org.manage.updateOrganizationNo", object);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 更新国际化信息
	 * 
	 * @param Object
	 * @return
	 * @throws Exception
	 */
	@Override
	public void updateDeptName(Object obj) throws Exception {
		this.deleteDepartmentName(obj);
		this.insertDepartmentName(obj);
	}

	/**
	 * 删除国际化信息
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public void deleteDepartmentName(Object obj) throws Exception {
		this.delete("org.manage.deleteDepartmentName", obj);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List getDeptPost(Object object) throws Exception {
		List temp = this.queryForList("org.manage.getDeptPost", object);
		return temp;
	}

	@Override
	public void updateOrganization(Object object) throws Exception {
		this.update("org.manage.updateOrganization", object);
	}

	@SuppressWarnings("unchecked")
	public List getOrganizationInfoList(Object obj) {
		List returnList = new ArrayList();
		returnList = this.getOrganizationInfoList(obj, -1, -1);
		return returnList;
	}

	@SuppressWarnings("unchecked")
	public List getOrganizationInfoList(Object object, int currentPage,
			int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"org.manage.getOrganizationInfoList", object,
						currentPage, pageSize);
			} else {
				returnList = this.queryForList(
						"org.manage.getOrganizationInfoList", object);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@Override
	public int getOrganizationInfoCnt(Object obj) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("org.manage.getOrganizationInfoCnt", obj)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}

	/**
	 * 取得国际化语言信息
	 * 
	 * @param
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getDeptNameListByActivity() {
		List temp = new ArrayList();
		try {
			temp = this.queryForList("org.manage.getDeptNameListByActivity");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return temp;
	}

	/**
	 * 取得国际化语言信息
	 * 
	 * @param
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getDeptNameNameListByActivity(Object object) {
		List temp = new ArrayList();
		try {
			temp = this.queryForList(
					"org.manage.getDeptNameNameListByActivity", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return temp;
	}

	/**
	 * 取得国际化语言
	 * 
	 * @param
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getSyLanguageListByActivity() {
		List temp = new ArrayList();
		try {
			temp = this.queryForList("org.manage.getSyLanguageListByActivity");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return temp;
	}

	/**
	 * 取得全局SEQ
	 * 
	 * @param request
	 * @return
	 */
	public String getDeptmentNameNo(Object obj) throws Exception {
		String deptmentNameNo = "";
		if (this.queryForObject("org.manage.getMaxNumber", obj) != null) {
			deptmentNameNo = this
					.queryForObject("org.manage.getMaxNumber", obj).toString();
		} else {
			deptmentNameNo = "1";
		}
		return deptmentNameNo;
	}

	/**
	 * 插入国际化信息
	 * 
	 * @param Object
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void insertDepartmentName(Object obj) throws Exception {
		List languageList = new ArrayList();
		languageList = this.syLanguageDao.getSyLanguageListByActivity();
		List paramList = new ArrayList();
		int orderNo = 1;
		orderNo = this.getOrderNoByCpnyId(obj);
		String no = StringUtil.checkNull(((LinkedHashMap) obj).get("DEPTNO"));
		String adminid = StringUtil.checkNull(((LinkedHashMap) obj)
				.get("adminid"));
		for (int i = 0; i < languageList.size(); i++) {
			LinkedHashMap paramMap = new LinkedHashMap();
			LinkedHashMap languageMap = new LinkedHashMap();
			languageMap = (LinkedHashMap) languageList.get(i);
			String language = StringUtil.checkNull(languageMap.get("LANGUAGE"));
			String content = StringUtil.checkNull(((LinkedHashMap) obj)
					.get("proName_" + languageMap.get("LANGUAGE")));
			if (language.equals("") || content.equals("")) {
				continue;
			} else {
				if (no == null || (no != null && no.equals(""))) {
					no = getDeptmentNameNo(obj);
					paramMap.put("DEPTNO", ((LinkedHashMap) obj)
							.get("interCpnyID")
							+ no);

					((Map) obj).put("DEPTNO", ((LinkedHashMap) obj)
							.get("interCpnyID")
							+ no);
					((Map) obj).put("deptId", no);
					((Map) obj).put("ORDERNO", orderNo);
				} else {
					paramMap.put("DEPTNO", StringUtil
							.checkNull(((LinkedHashMap) obj).get("DEPTNO")));

					((Map) obj).put("DEPTNO", StringUtil
							.checkNull(((LinkedHashMap) obj).get("DEPTNO")));
					((Map) obj).put("deptId", StringUtil
							.checkNull(((LinkedHashMap) obj).get("deptId")));
					((Map) obj).put("ORDERNO", orderNo);
				}
				paramMap.put("interCpnyID", ((LinkedHashMap) obj)
						.get("interCpnyID"));
				paramMap.put("LANGUAGE", language);
				paramMap.put("CONTENT", content);
				paramMap.put("adminid", adminid);
				paramMap.put("ORDERNO", orderNo);
				paramList.add(paramMap);
				this.insert("org.manage.addDeptmentNameInfo", paramMap);
			}
		}
	}

	/**
	 * 删除部门信息
	 * 
	 * @param Object
	 * @return
	 */
	@Override
	public void deleteOrganizationInfo(Object object) throws Exception {
		this.update("org.manage.deleteOrganizationInfo", object);
	}

	/**
	 * 取得部门信息
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object getOrganizationInfo(Object obj) {
		LinkedHashMap returnObj = new LinkedHashMap();
		List returnList = this.getOrganizationInfoList(obj);
		if (returnList.size() > 0) {
			returnObj = (LinkedHashMap) returnList.get(0);
		}
		return returnObj;
	}

	/**
	 * 获取可以看到部门的最小的deptlevel
	 */
	@SuppressWarnings("unchecked")
	public Object getDeptTopLevel(Object object) {
		Object returnObject = null;
		try {
			List returnList = this.queryForList("org.manage.getDeptTopLevel",
					object);
			returnObject = (returnList.size() == 0 ? null : returnList.get(0));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnObject;
	}

	@SuppressWarnings("unchecked")
	public List getChildCodeList(Object object) {
		List temp = new ArrayList();
		try {
			temp = this.queryForList("org.manage.getChildCodeList", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return temp;
	}

	@SuppressWarnings("unchecked")
	public List getEmpInfoInOrg(Object object) {
		List temp = new ArrayList();
		try {
			temp = this.queryForList("org.manage.getEmpInfoInOrg", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return temp;
	}

	/**
	 * 取得全局orderno
	 * 
	 * @param request
	 * @return
	 */
	public int getOrderNoByCpnyId(Object obj) throws Exception {

		int orderNo = 1;

		try {
			orderNo = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("org.manage.getOrderNoByCpnyId", obj)),
					Integer.class);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return orderNo;
	}

	@SuppressWarnings("unchecked")
	public List viewStructure(Object object) throws Exception {
		List temp = this.queryForList("org.manage.viewStructure", object);
		return temp;
	}

	@SuppressWarnings("unchecked")
	public List getHrEmployeeByCpnyAndDeptNo(Object object) throws Exception {
		List temp = this.queryForList(
				"org.manage.getHrEmployeeByCpnyAndDeptNo", object);
		return temp;
	}

	/**
	 * 部门组织结构查询，Excel导出用(query the emp status info)，不分页
	 * 
	 * @param object
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getOrgStructureInfoList(Object obj) {
		List returnList = new ArrayList();
		returnList = this.getOrgStructureInfoList(obj, -1, -1);
		return returnList;
	}

	/**
	 * 部门组织结构查询，Excel导出用(query the emp status info)
	 * 
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List getOrgStructureInfoList(Object obj, int currentPage,
			int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"org.manage.getOrgStructureInfoList", obj, currentPage,
						pageSize);
			} else {
				returnList = this.queryForList(
						"org.manage.getOrgStructureInfoList", obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 部门组织结构查询数量(query the emp status info count)
	 * 
	 * @param object
	 * @return int
	 */
	public int getOrgStructureInfoListCnt(Object obj) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("org.manage.getOrgStructureInfoListCnt",
							obj)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}

	/**
	 * 启用某个部门
	 * 
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return list
	 */
	public int openOrgStructureInfo(Object object) throws Exception {
		this.update("org.manage.openOrgStructureInfo", object);
		return 1;
	}

	/**
	 * 关闭某个部门
	 * 
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return list
	 */
	public int closeOrgStructureInfo(Object object) throws Exception {
		this.update("org.manage.closeOrgStructureInfo", object);
		return 1;
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
	public List getPayAreaInfoList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this
					.queryForList("org.manage.getPayAreaInfoList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 更新大区信息
	 * 
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return list
	 */
	public int updatePayAreaInfo(Object object) throws Exception {
		this.update("org.manage.updatePayAreaInfo", object);
		return 1;
	}

	/**
	 * 更新社员大区信息
	 * 
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return list
	 */
	public int updateEmpPayAreaInfo(Object object) throws Exception {
		this.update("org.manage.updateEmpPayAreaInfo", object);
		return 1;
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
	public List getSyCodeList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("org.manage.getSyCodeList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 组织排序设置list查找
	 * 
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getOrgInfoOrderList(Object object, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"org.manage.getOrgInfoOrderList", object, currentPage,
						pageSize);
			} else {
				returnList = this.queryForList(
						"org.manage.getOrgInfoOrderList", object);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 单条组织信息详情
	 * 
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object getOrgInfoOrder(Object obj) {
		LinkedHashMap returnObj = new LinkedHashMap();
		List returnList = this.getOrgInfoOrderList(obj);
		if (returnList.size() > 0) {
			returnObj = (LinkedHashMap) returnList.get(0);
		}
		return returnObj;
	}

	/**
	 * 组织不分页的list
	 */
	@SuppressWarnings("unchecked")
	public List getOrgInfoOrderList(Object obj) {
		List returnList = new ArrayList();
		returnList = this.getOrgInfoOrderList(obj, -1, -1);
		return returnList;
	}

	@Override
	public int getOrgOrgInfoOrderCnt(Object obj) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("org.manage.getOrgOrgInfoOrderCnt", obj)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}

	/**
	 * 分发人查看添加的No在数据表中是否存在
	 * 
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int checkOrganizationNo(Object object) {
		int num = 0;
		try {
			num = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("org.manage.checkOrganizationNo", object)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return num;
	}

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
			int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"org.manage.getDeptOrderImportTempList", object,
						currentPage, pageSize);
			} else {
				returnList = this.queryForList(
						"org.manage.getDeptOrderImportTempList", object);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 获取组织排序导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getDeptOrderImportTempList(Object object) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"org.manage.getDeptOrderImportTempList", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 获取组织排序导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getDeptOrderImportTempCnt(Object object) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("org.manage.getDeptOrderImportTempCnt",
							object)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}

	/**
	 * 获取出错的组织排序导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author wendi@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getDeptOrderImportTempErrorCnt(Object object) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils
					.parseNumber(
							ObjectUtils
									.toString(this
											.queryForObject(
													"org.manage.getDeptOrderImportTempErrorCnt",
													object)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}

	/**
	 * 数据导入(验证通过后--从临时表导入到正式表)
	 * 
	 * @param
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String importDeptOrderFromExcel(Object object) throws Exception {
		String returnString = "";
		try {
			LinkedHashMap paramMap = (LinkedHashMap) object;
			paramMap.put("message", "");
			this.insert("org.manage.importDeptOrderFromExcel", paramMap);
			returnString = ObjectUtils.toString(paramMap.get("message"));
		} catch (SQLException e) {
			returnString = e.getMessage();
			throw e;
		}
		return returnString;
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
	public List getDeptList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("org.manage.getDeptList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 获取部门列表信息，供页面部门检索条件用（概要）
	 * 
	 * @param request
	 * @author weizhengchen
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getResumeDeptList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("org.manage.getResumeDeptList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	public List viewResumeList(Object object) {
		List result = null;
		try {
			result = this.queryForList("org.manage.viewResumeList", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 插入概要改编insert
	 * 
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int addResumeInfo(Object object) throws Exception {
		Map obj = (Map) object;
		if (((LinkedHashMap) object).get("SEQ") != null
				&& !"".equals(((LinkedHashMap) object).get("SEQ").toString())) {
			// 需要update
			this.update("org.manage.updateResumeInfo", object);
		} else {
			String seq = StringUtil.checkNull(this.insert(
					"org.manage.addResumeInfo", object));
			// 附件上传
			if (obj.get("fileName") != null
					&& !"".equals(StringUtil.checkNull(obj.get("fileName")))) {
				String[] fileName = StringUtil.checkNull(obj.get("fileName"))
						.split(";");
				String[] fileUrl = StringUtil.checkNull(obj.get("fileUrl"))
						.split(";");
				if (fileUrl != null && fileUrl.length > 0) {
					for (int j = 0; j < fileUrl.length; j++) {
						LinkedHashMap fileMap = new LinkedHashMap();
						fileMap.put("fileName", fileName[j]);
						fileMap
								.put("fileUrl",
										"/resources/temp/apply/applyleave/"
												+ obj.get("adminID") + "/"
												+ fileUrl[j]);
						fileMap.put("APPLY_NO", seq);
						fileMap.put("APPLY_TYPE", "RESUME");
						fileMap.put("CREATED_BY", obj.get("adminID"));
						this.insert("ess.infoApplyLeave.insertEssFile",
										fileMap);
					}
				}
			}
		}
		return 1;
	}

	/**
	 * 删除概要改编
	 * 
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int deleteResumeInfo(Object object) throws Exception {
		this.update("org.manage.deleteResumeInfo", object);
		return 1;
	}

	/**
	 * 取得全局SEQ
	 * 
	 * @param request
	 * @return
	 */
	public List getResumneNo(Object obj) throws Exception {
		return this.queryForList("org.manage.getResumneNo", obj);
	}

	/**
	 * deptnomax
	 * 
	 * @param request
	 * @return
	 */
	public List getDeptNoMax(Object obj) throws Exception {
		return this.queryForList("org.manage.getDeptNoMax", obj);
	}
	/**
	 * deptnomax
	 * 
	 * @param request
	 * @return
	 */
	public List getDeptNoMax1(Object obj) throws Exception {
		return this.queryForList("org.manage.getDeptNoMax1", obj);
	}


	/**
	 * 改编流程执行
	 * 
	 * @param
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String executeResumeProcess(Object object) throws Exception {
		String returnString = "";
		try {
			LinkedHashMap paramMap = (LinkedHashMap) object;
			paramMap.put("message", "");
			this.insert("org.manage.executeResumeProcess", paramMap);
			returnString = ObjectUtils.toString(paramMap.get("message"));
		} catch (SQLException e) {
			returnString = e.getMessage();
			throw e;
		}
		return returnString;
	}

	/**
	 * 流程执行状态
	 * 
	 * @param
	 * @return
	 */
	public List viewResumeProcess(Object object) {
		List result = null;
		try {
			result = this.queryForList("org.manage.viewResumeProcess", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 获取默认deptNO
	 * 
	 * @param request
	 * @return
	 */
	public String getDefaultDeptNo(Object obj) throws Exception {
		return StringUtil.checkNull(this.queryForObject(
				"org.manage.getDefaultDeptNo", obj));
	}

	/**
	 * 获取部门信息
	 * 
	 * @param request
	 * @return
	 */
	public List viewResumeDeptList(Object object) {
		List result = null;
		try {
			result = this.queryForList("org.manage.viewResumeDeptList", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 获取人员信息
	 * 
	 * @param request
	 * @return
	 */
	public List viewResumeEmpList(Object object) {
		List result = null;
		try {
			result = this.queryForList("org.manage.viewResumeEmpList", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 移动部门人员
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	public String executeMoveEmp(Object object) throws Exception {
		String returnString = "OK";
		try {
			this.insert("org.manage.executeMoveEmp", object);
		} catch (SQLException e) {
			returnString = e.getMessage();
			throw e;
		}
		return returnString;
	}

	/**
	 * 插入组织
	 * 
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int addOrgInfo(Object object) throws Exception {
		Map obj = (Map) object;
		if (((LinkedHashMap) object).get("NEW") != null
				&& !"".equals(((LinkedHashMap) object).get("NEW").toString())) {
			int count = (Integer) this.queryForObject(
					"org.manage.isHaveDeptNo", object);
			if (count > 0) {
				return 2;
			}
			if ("HTSV".equals(StringUtil.checkNull(obj.get("interCpnyID")))){
				this.insert("org.manage.addOrgInfoHTSV", object);
			}else{
				this.insert("org.manage.addOrgInfo", object);
			}
			
		} else {
			// 需要update
			if ("HTSV".equals(StringUtil.checkNull(obj.get("interCpnyID")))){
				this.update("org.manage.updateOrgInfoHTSV", object);
			}else{
				this.update("org.manage.updateOrgInfo", object);
			}
			
		}
		if ("TSTO".equals(StringUtil.checkNull(obj.get("interCpnyID")))
				&& "".equals(StringUtil.checkNull(obj.get("IS_PART_TIME")))) {
			// 更新其他为兼职
			this.update("org.manage.updateOrgInfoParttime", object);
		}
		return 1;
	}

	/**
	 * 删除组织
	 * 
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String deleteOrgInfo(Object object) throws Exception {
		String returnString = "";
		try {
			LinkedHashMap paramMap = (LinkedHashMap) object;
			paramMap.put("message", "");
			this.insert("org.manage.deleteOrgInfo", paramMap);
			returnString = ObjectUtils.toString(paramMap.get("message"));
		} catch (SQLException e) {
			returnString = e.getMessage();
			throw e;
		}
		return returnString;
	}

	/**
	 * 获取部门顺序
	 * 
	 * @param request
	 * @return
	 */
	public List viewModifyOrgOrderNo(Object object) {
		List result = null;
		try {
			result = this.queryForList("org.manage.viewModifyOrgOrderNo",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 部门顺序再定义
	 * 
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int modifyOrgOrderNo(Object object) throws Exception {
		this.update("org.manage.modifyOrgOrderNo", object);
		return 1;
	}

	/**
	 * 部门合并
	 * 
	 * @param
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String modifyOrgMerge(Object object) throws Exception {
		String returnString = "";
		try {
			LinkedHashMap paramMap = (LinkedHashMap) object;
			paramMap.put("message", "");
			this.insert("org.manage.modifyOrgMerge", paramMap);
			returnString = ObjectUtils.toString(paramMap.get("message"));
		} catch (SQLException e) {
			returnString = e.getMessage();
			throw e;
		}
		return returnString;
	}

	/**
	 * 部门分割
	 * 
	 * @param
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String modifyOrgSplit(Object object) throws Exception {
		String returnString = "";
		try {
			LinkedHashMap paramMap = (LinkedHashMap) object;
			paramMap.put("message", "");
			this.insert("org.manage.modifyOrgSplit", paramMap);
			returnString = ObjectUtils.toString(paramMap.get("message"));
		} catch (SQLException e) {
			returnString = e.getMessage();
			throw e;
		}
		return returnString;
	}

	public List viewDeptManagerCheck(Object object) {
		List result = null;
		try {
			result = this.queryForList("org.manage.viewDeptManagerCheck",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 发令核查
	 */
	public List viewOrgExperirnceInsideList(Object object) {
		List result = null;
		try {
			result = this.queryForList(
					"org.manage.viewOrgExperirnceInsideList", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public int addOrgExperirnceInsideInfo(Object object) throws Exception {
		Map obj = (Map) object;
		if (((LinkedHashMap) object).get("SEQ") != null
				&& !"".equals(((LinkedHashMap) object).get("SEQ").toString())) {
			// 需要update
			this.update("org.manage.updateOrgExperirnceInsideInfo", object);
		} else {
			this.insert("org.manage.addOrgExperirnceInsideInfo", object);
		}
		return 1;
	}

	@SuppressWarnings("unchecked")
	public int deleteOrgExperirnceInsideInfo(Object object) throws Exception {
		this.updateForList("org.manage.deleteOrgExperirnceInsideInfo",
				(List) object);
		return 1;
	}

	public List viewChangeDetailOrgInfoList(Object object) {
		List result = null;
		try {
			result = this.queryForList(
					"org.manage.viewChangeDetailOrgInfoList", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public List viewChangeDetailEmpInfoList(Object object) {
		List result = null;
		try {
			result = this.queryForList(
					"org.manage.viewChangeDetailEmpInfoList", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 工作地管理
	 */
	public List viewWorkAreaList(Object object) {
		List result = null;
		try {
			result = this.queryForList("org.manage.viewWorkAreaList", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public int addWorkAreaInfo(Object object) throws Exception {
		Map obj = (Map) object;
		if (((LinkedHashMap) object).get("SEQ") != null
				&& !"".equals(((LinkedHashMap) object).get("SEQ").toString())) {
			// 需要update
			this.update("org.manage.updateWorkAreaInfo", object);
		} else {
			this.insert("org.manage.addWorkAreaInfo", object);
		}
		return 1;
	}

	@SuppressWarnings("unchecked")
	public int deleteWorkAreaInfo(Object object) throws Exception {
		this.update("org.manage.deleteWorkAreaInfo", object);
		return 1;
	}

	/**
	 * 成本中心管理
	 */
	public List viewCostCenterList(Object object) {
		List result = null;
		try {
			result = this.queryForList("org.manage.viewCostCenterList", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public String addCostCenterInfo(Object object) throws Exception {
		String seq = "";
		if (((LinkedHashMap) object).get("SEQ") != null
				&& !"".equals(((LinkedHashMap) object).get("SEQ").toString())) {
			// 需要update
			if ("HTSV".equals(StringUtil.checkNull(((LinkedHashMap) object).get("interCpnyID")))){
				this.insert("org.manage.updateCostCenterInfoHTSV", object);
			}else{
				this.insert("org.manage.updateCostCenterInfo", object);
			}
			seq = ((LinkedHashMap) object).get("SEQ").toString();
		} else {
			int count = (Integer) this.queryForObject("org.manage.isHaveCostCenterNo", object);
			if (count > 0) {
				return "2";
			}
			if ("HTSV".equals(StringUtil.checkNull(((LinkedHashMap) object).get("interCpnyID")))){
				seq = StringUtil.checkNull(this.insert("org.manage.addCostCenterInfoHTSV", object));
			}else{
				seq = StringUtil.checkNull(this.insert("org.manage.addCostCenterInfo", object));
			}
		}
		return seq;
	}

	@SuppressWarnings("unchecked")
	public int deleteCostCenterInfo(Object object) throws Exception {
		this.update("org.manage.deleteCostCenterInfo", object);
		return 1;
	}

	/**
	 * 主要业务管理
	 */
	public List viewBusinessList(Object object) {
		List result = null;
		try {
			result = this.queryForList("org.manage.viewBusinessList", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public int saveBusinessInfo(Object object) throws Exception {
		this.updateForList("org.manage.updateBusinessInfo", (List) object);
		return 1;
	}

	@SuppressWarnings("unchecked")
	public int addBusinessInfo(Object object) throws Exception {
		Map obj = (Map) object;
		if (((LinkedHashMap) object).get("SEQ") != null
				&& !"".equals(((LinkedHashMap) object).get("SEQ").toString())) {
			// 需要update
			this.update("org.manage.updateBusinessInfo", object);
		} else {
			this.insert("org.manage.addBusinessInfo", object);
		}
		return 1;
	}

	@SuppressWarnings("unchecked")
	public int deleteBusinessInfo(Object object) throws Exception {
		this.update("org.manage.deleteBusinessInfo", object);
		return 1;
	}

	@SuppressWarnings("unchecked")
	public int deleteEmptyBusinessInfo(Object object) throws Exception {
		this.update("org.manage.deleteEmptyBusinessInfo", object);
		return 1;
	}

	/**
	 * 保存部门管理者信息
	 */
	@SuppressWarnings("unchecked")
	public int saveDeptManager(Object object) throws Exception {
		this.updateForList("org.manage.saveDeptManager", (List) object);
		return 1;
	}

	/**
	 * 部门变更履历
	 * 
	 * @param object
	 * @return
	 */
	public List viewOrgChangeInfoList(Object object) {
		List result = null;
		try {
			result = this.queryForList("org.manage.viewOrgChangeInfoList",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 保存部门变更履历备注
	 */
	@SuppressWarnings("unchecked")
	public int saveOrgChangeInfo(Object object) throws Exception {
		this.updateForList("org.manage.saveOrgChangeInfo", (List) object);
		return 1;
	}

	/**
	 * 当前组织 及 组织内人员信息
	 * 
	 * @param request
	 * @return
	 */
	public List viewCurrentOrgDetailEmpInfo(Object object) {
		List result = null;
		try {
			result = this.queryForList(
					"org.manage.viewCurrentOrgDetailEmpInfo", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 当前组织信息
	 * 
	 * @param request
	 * @return
	 */
	public List viewCurrentOrgDetailOrgInfo(Object object) {
		List result = null;
		try {
			result = this.queryForList(
					"org.manage.viewCurrentOrgDetailOrgInfo", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
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
	public List getHistoryResumneNo(Object obj) throws Exception {
		return this.queryForList("org.manage.getHistoryResumneNo", obj);
	}

	/**
	 * 获取版本组织信息
	 * 
	 * @param request
	 * @return
	 */
	public List viewResumeOrgList(Object object) {
		List result = null;
		try {
			result = this.queryForList("org.manage.viewHistoryResumeDeptList",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 获取历史部门人员信息
	 * 
	 * @param request
	 * @return
	 */
	public List viewHistoryResumeEmpList(Object object) {
		List result = null;
		try {
			result = this.queryForList("org.manage.viewHistoryResumeEmpList",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 查询部门分割 人员临时信息
	 */
	public List viewOrgSplitTemp(Object object) {
		List result = null;
		try {
			result = this.queryForList("org.manage.viewOrgSplitTemp", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 获取概要改编的状态
	 * 
	 * @param request
	 * @return
	 */
	public List getResumeActivity(Object obj) throws Exception {
		return this.queryForList("org.manage.getResumeActivity", obj);
	}

	/**
	 * 删除部门信息
	 * 
	 * @param Object
	 * @return
	 */
	@Override
	public void deleteFile(Object object) throws Exception {
		this.update("org.manage.deleteFile", object);
	}

	/**
	 * 变更履历--个人
	 * 
	 * @param object
	 * @return
	 */
	public List viewEmpChangeInfoList(Object object) {
		List result = null;
		try {
			result = this.queryForList("org.manage.viewEmpChangeInfoList",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 预发令核查
	 * 
	 * @param request
	 * @return
	 */
	public List viewOrgPreExperirnceInsideList(Object object) {
		List result = null;
		try {
			result = this.queryForList(
					"org.manage.viewOrgPreExperirnceInsideList", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 修改预发令信息
	 */
	@SuppressWarnings("unchecked")
	public int modifyPreExperirnceInsideInfo(Object object) throws Exception {
		this.updateForList("org.manage.modifyPreExperirnceInsideInfo",
				(List) object);
		return 1;
	}

	/**
	 * 保存预发令信息到组织发令表
	 */
	@SuppressWarnings("unchecked")
	public int addPreExperirnceInsideInfo(Object object) throws Exception {
		List<LinkedHashMap<String, Object>> dataList = (List<LinkedHashMap<String, Object>>) object;
		for (int i = 0; i < dataList.size(); i++) {
			LinkedHashMap personMap = (LinkedHashMap) dataList.get(i);
			this.update("org.manage.deletePreExperirnceInsideInfo",
					personMap);
			this.update("org.manage.modifyPreExperirnceInsideInfo",
					personMap);
			this.insert("org.manage.addPreExperirnceInsideInfo",
					personMap);
		}
		return 1;
	}

	/**
	 * 部门履历
	 */
	public List viewOrgInfoList(Object object) {
		List result = null;
		try {
			result = this.queryForList("org.manage.viewOrgInfoList", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 部门长履历
	 */
	public List viewOrgManagerList(Object object) {
		List result = null;
		try {
			result = this.queryForList("org.manage.viewOrgManagerList", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 检查部门领导是否重复任职
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	public int checkDeptManager(Object obj) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("org.manage.checkDeptManager", obj)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
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
	public List getDeptTempList(Object object){
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("org.manage.getDeptTempList", object);
		} catch (SQLException e) {	
			e.printStackTrace();
		}
		return returnList ;
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
	public int getDeptTempCnt(Object object){
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("org.manage.getDeptTempCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	 * 获取出错的明细导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getDeptTempErrorCnt(Object object){
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("org.manage.getDeptTempErrorCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
}