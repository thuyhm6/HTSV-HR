package com.ait.sys.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;
import com.ait.sys.dao.LoginUserDao;
import com.ait.web.util.SqlMapClientSupport;
import com.ait.web.util.StringUtil;

/**
 * 
 * Copyright: AIT (c) Company: AIT
 * 
 * @fileName LoginUserDaoImpl.java
 * @author zhouyeqing(zhouyeqing@ait.net.cn)
 * @Date 2012-1-4 下午05:21:10
 * @version 5.0
 * 
 */
@Repository
public class LoginUserDaoImpl extends SqlMapClientSupport implements
		LoginUserDao {

	/**
	 * 取得登陆用户权限组列表
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getLoginUserRolesGroupList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"sys.loginUser.getLoginUserRolesGroupList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 
	 * 获取GSOD权限
	 */

	@SuppressWarnings("unchecked")
	public List getloginUserInfoRolesGSODList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"sys.loginUser.getloginUserInfoRolesGSODList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 取得员工类别(get EmpTypeCode List)
	 * 
	 * @param request
	 * @return List
	 */
	@Override
	public List getEmpTypeCodeList(Map obj) {

		List returnList = new ArrayList();

		try {

			returnList = this.queryForList("sys.loginUser.getEmpTypeCodeList",
					obj);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 取得员工类别(get EmpTypeCode List)
	 * 
	 * @param request
	 * @return List
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List getSySupervisorEmpTypeCodeList(Map obj) {

		List returnList = new ArrayList();

		try {

			returnList = this.queryForList(
					"sys.loginUser.getSySupervisorEmpTypeCodeList", obj);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 取得登陆用户信息
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object getLoginUser(Object obj) {
		Object returnObj = new Object();
		List returnList = new ArrayList();
		returnList = this.getLoginUserList(obj);
		if (returnList.size() > 0) {
			returnObj = returnList.get(0);
		}
		return returnObj;
	}

	/**
	 * 取得所有登陆用户列表
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getLoginUserList(Object obj) {
		List returnList = new ArrayList();
		returnList = this.getLoginUserList(obj, -1, -1);
		return returnList;
	}

	@Override
	public int getLoginUserCnt(Object obj) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("sys.loginUser.getLoginUserCnt", obj)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}

	/**
	 * 取得所有登陆用户列表
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getLoginUserList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"sys.loginUser.getLoginUserList", obj, currentPage,
						pageSize);
			} else {
				returnList = this.queryForList(
						"sys.loginUser.getLoginUserList", obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 插入登陆者信息
	 * 
	 * @param Object
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int addLoginUserInfo(Object obj) throws Exception {
		int returnInt = 1;
		Object nos = ((Map) obj).get("SCREEN_GRANT_NO");
		String userNo = this.queryForObject("sys.loginUser.getUserNo")
				.toString();
		((Map) obj).put("USER_NO", userNo);
		((Map) obj).put("USERNO", userNo);
		this.insert("sys.loginUser.insertLoginUserInfo", obj);
		if (nos != null && !nos.equals("")) {
			String[] noArr = (String[]) nos;
			for (int i = 0; i < noArr.length; i++) {
				((Map) obj).put("GROUPNO", noArr[i]);
				this.insert("sys.loginUser.InsertLoginUserRelationInfo", obj);
			}
		}
		this.delete("sys.loginUser.deleteLoginUserDeptInfo", obj);
		if (((Map) obj).get("SPECIAL_PARAM") != null
				&& ((Map) obj).get("SPECIAL_PARAM").equals("special")) {
			List<String> temp = StringUtil.getSplitParams(((Map) obj).get(
					"deptNos").toString(), ",");
			if (temp != null && temp.size() > 0 && !temp.get(0).equals("")) {

				for (String deptNo : temp) {
					((Map) obj).put("DEPTNO", deptNo);
					this.insert("sys.loginUser.insertLoginUserDeptList", obj);
				}
			}
		}
		return returnInt;
	}

	/**
	 * 更新登陆用户信息
	 * 
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int updateLoginUserInfo(Object obj) throws Exception {
		int returnInt = 1;
		Object nos = ((Map) obj).get("SCREEN_GRANT_NO");
		this.update("sys.loginUser.updateLoginUserInfo", obj);
		this.delete("sys.loginUser.deleteLoginUserRelationInfo", obj);// 删除SY_USER_RELATION表的数据
		if (nos != null && !nos.equals("")) {
			String[] noArr = (String[]) nos;
			for (int i = 0; i < noArr.length; i++) {
				((Map) obj).put("GROUPNO", noArr[i]);
				this.insert("sys.loginUser.InsertLoginUserRelationInfo", obj);
			}
		}
		this.delete("sys.loginUser.deleteLoginUserDeptInfo", obj);
		if (((Map) obj).get("SPECIAL_PARAM") != null
				&& ((Map) obj).get("SPECIAL_PARAM").equals("special")) {
			List<String> temp = StringUtil.getSplitParams(((Map) obj).get(
					"deptNos").toString(), ",");
			if (temp != null && temp.size() > 0 && !temp.get(0).equals("")) {
				for (String deptNo : temp) {
					((Map) obj).put("DEPTNO", deptNo);
					this.insert("sys.loginUser.insertLoginUserDeptList", obj);
				}
			}
		}
		Map paramMap = (Map) obj;
		this.delete("sys.loginUser.deleteSySupervisorEmpTypeCodeInfo", obj);
		if (paramMap.get("postNos") != null
				&& ((String[]) paramMap.get("postNos")).length > 0) {
			for (String emptype : ((String[]) paramMap.get("postNos"))) {
				Map tempP = new LinkedHashMap();
				tempP.put("emptypecode", emptype);
				tempP.put("PERSON_ID", paramMap.get("PERSON_ID"));
				tempP.put("CREATED_BY", paramMap.get("UPDATED_BY"));
				this.insert("sys.loginUser.addSySupervisorEmpTypeCodeInfo",
						tempP);
			}
		}
		return returnInt;
	}

	/**
	 * 删除登陆用户信息
	 * 
	 * @param Object
	 * @return
	 */
	public int deleteLoginUserInfo(Object obj) {
		return 0;
	}

	/**
	 * 取得所有登陆用户部门权限,最高级别部门列表
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getLoginUserMaxDeptList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"sys.loginUser.getLoginUserMaxDeptList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 取得所有登陆用户部门权限信息列表
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getLoginUserDeptList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList(
					"sys.loginUser.getLoginUserDeptList", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	public List getDeptInfoTree(Object object) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("sys.loginUser.getDeptInfoTree",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	public int validatePersonIdExist(Object object) throws Exception {
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("sys.loginUser.validatePersonIdExist",
							object)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}

	public void deleteLoginUser(Object object) throws Exception {
		this.delete("sys.loginUser.deleteLoginUserInfo", object);
		this.delete("sys.loginUser.deleteLoginUserRelationInfo", object);
		this.delete("sys.loginUser.deleteLoginUserDeptInfo", object);
	}

	@Override
	public void changePassword(Object object) throws Exception {

		this.update("sys.loginUser.changePassword", object);

	}

	/**
	 * 取得所有登陆用户IP列表
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getLoginUserIPList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("sys.loginUser.getLoginUserIPList",
					obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	public List viewFileInfoList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("sys.loginUser.viewFileInfoList",
					obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	public List viewFileUrlInfo(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this
					.queryForList("sys.loginUser.viewFileUrlInfo", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@SuppressWarnings("unchecked")
	public Object viewFileInfo(Object obj) {
		Object Ob = new Object();
		try {
			Ob = this.queryForObject("sys.loginUser.viewFileInfo", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Ob;
	}

	/**
	 * 插入文件信息
	 * 
	 * @param Object
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int addFileInfo(Object obj) throws Exception {
		int returnInt = 1;

		return returnInt;
	}

	/**
	 * 修改文件信息
	 * 
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int updateFileInfo(Object obj) throws Exception {
		int returnInt = 1;

		/*
		 * this.update("sys.loginUser.updateLoginUserInfo", obj);
		 * this.delete("sys.loginUser.deleteLoginUserRelationInfo",
		 * obj);//删除SY_USER_RELATION表的数据
		 * 
		 * this.insert("sys.loginUser.insertLoginUserDeptList", obj);
		 */
		return returnInt;
	}

	/**
	 * 删除上传文件
	 * 
	 * @param Object
	 * @return
	 */
	public int deleteFileInfo(Object obj) {
		return 0;
	}

}
