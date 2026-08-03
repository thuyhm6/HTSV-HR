package com.ait.sys.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.sys.bean.AdminBean;
import com.ait.sys.dao.RolesGroupDao;
import com.ait.sys.dao.SyLanguageDao;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.SqlMapClientSupport;
import com.ait.web.util.StringUtil;
/**
 * 
 * Copyright:   LDCC (c)
 * Company:     LDCC
 * @fileName RolesGroupDaoImpl.java
 * @author zhouyeqing(zhouyeqing@ait.net.cn)
 * @Date 2012-1-4 下午05:21:24
 * @version 5.0
 *
 */
@Repository
public class RolesGroupDaoImpl extends SqlMapClientSupport implements RolesGroupDao {
	
	@Autowired
	private SyLanguageDao syLanguageDao;
	
	/**
	 * 取得所有权限信息
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object getRolesGroup(Object obj) {
		Object returnObj = new Object() ;
		List returnList = this.getRolesGroupList(obj) ;
		if(returnList.size() > 0){
			returnObj = returnList.get(0) ;
		}
		return returnObj ;
	}
	
	/**
	 * 取得所有权限组列表
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getRolesGroupList(Object obj) {
		List returnList = new ArrayList() ;
		returnList = this.getRolesGroupList(obj, -1, -1) ;
		return returnList ;
	}
	
	@Override
	public int getRolesGroupCnt(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("sys.rolesGroup.getRolesGroupCnt", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	 * 取得所有权限组列表
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getRolesGroupList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("sys.rolesGroup.getRolesGroupList", obj, currentPage, pageSize);
			}else{
				returnList = this.queryForList("sys.rolesGroup.getRolesGroupList", obj);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 插入权限组信息
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int addRolesGroupInfo(Object obj) throws SQLException{
		this.insert("sys.rolesGroup.addRolesGroupInfo", obj) ;
		Map paramMap = (Map)obj ;
		List insertRolesGroupPageList = (List)paramMap.get("insertRolesGroupPageList") ; 
		this.insertForList("sys.rolesGroup.addRolesGroupPageInfo", insertRolesGroupPageList) ;
		return 0 ;
	}
	
	/**
	 * 批量插入权限组信息
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int addRolesGroupInfo(List obj) {
		try {
			this.insertForList("sys.rolesGroup.addRolesGroupInfo", obj) ;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0 ;
	}
	
	/**
	 * 更新权限组信息
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int updateRolesGroupInfo(Object obj) {
		try {
			this.startTransaction() ;
			this.update("sys.rolesGroup.updateRolesGroupInfo", obj) ;
			this.delete("sys.rolesGroup.deleteRolesGroupPageInfo", obj) ;
			Map paramMap = (Map)obj ;
			List insertRolesGroupPageList = (List)paramMap.get("insertRolesGroupPageList") ; 
			this.insertForList("sys.rolesGroup.addRolesGroupPageInfo", insertRolesGroupPageList) ;
			this.commitTransation() ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}finally {
			try {
				this.endTransation() ;
			} catch (Exception e) {
				e.printStackTrace();
			}
		 }
		return 0 ;
	}
	
	/**
	 * 批量更新权限组信息
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int updateRolesGroupInfo(List obj) {
		try {
			this.updateForList("sys.rolesGroup.updateRolesGroupInfo", obj) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return 0 ;
	}
	
	/**
	 * 删除权限组信息
	 * @param Object
	 * @return
	 */
	public int deleteRolesGroupInfo(Object obj) {
		try {
			this.delete("sys.rolesGroup.deleteRolesGroupInfo", obj) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return 0 ;
	}
	
	/**
	 * 批量删除权限组信息
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int deleteRolesGroupInfo(List obj) {
		try {
			this.startTransaction() ;
			this.deleteForList("sys.rolesGroup.deleteRolesGroupPageInfo", obj) ;
			this.deleteForList("sys.rolesGroup.deleteRolesGroupInfo", obj) ;
			this.commitTransation() ;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				this.endTransation() ;
			} catch (Exception e) {
				e.printStackTrace();
			}
		 }
		return 0 ;
	}
	
	/**
	 * 取得所有页面信息列表
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getRolesGroupPageList(Object obj) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("sys.rolesGroup.getRolesGroupPageList", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	@Override
	public int checkRolesGroupIdExsit(Object object) {
			int returnInt = 0 ;
			try {
				returnInt = 
					NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("sys.rolesGroup.isRolesGroupIdExsit", object)), Integer.class) ;
			} catch (SQLException e) {			
				e.printStackTrace();
			}
			return returnInt ;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void saveOrUpdateRolesGroupInfo(HttpServletRequest request,
			Object object) throws Exception {
			Map SELECTRs=StringUtil.getAliasSplitParams(request.getParameterValues("SELECTR"), "\\|");
			Map INSERTRs=StringUtil.getAliasSplitParams(request.getParameterValues("INSERTR"), "\\|");
			Map UPDATERs=StringUtil.getAliasSplitParams(request.getParameterValues("UPDATER"), "\\|");
			Map DELETERs=StringUtil.getAliasSplitParams(request.getParameterValues("DELETER"), "\\|");
			String[] MENU_NOS=request.getParameterValues("MENU_NOS");
			Map tempMap=(Map) object;
			if((Integer)this.queryForObject("sys.rolesGroup.checkSyLoginScreen", object)>0){
				this.syLanguageDao.updateSyGlobalName(object);
				this.update("sys.rolesGroup.updateSyLoginScreen", object);
				tempMap=(Map) this.queryForObject("sys.rolesGroup.backscreenGrant",object);
//				LinkedHashMap paramMap = (LinkedHashMap)this.syLanguageDao.saveSyGlobalName(object);
//				this.update("sys.rolesGroup.updateSyLoginScreen", paramMap);
//				tempMap=(Map) this.queryForObject("sys.rolesGroup.backscreenGrant",paramMap);
			}else{ 
				LinkedHashMap paramMap = (LinkedHashMap)this.syLanguageDao.saveSyGlobalName(object);
				this.insert("sys.rolesGroup.saveSyLoginScreen" , paramMap);
//				this.insert("sys.rolesGroup.saveSyLoginScreen" , object);
//				 Map paramMap=new LinkedHashMap();
				 paramMap.put("ROLE_ID", ((Map)object).get("ROLE_ID"));
				 paramMap.put("CPNY_ID", ((Map)object).get("CPNY_ID"));
				 paramMap.put("interCpnyID", ((Map)object).get("CPNY_ID"));
				 paramMap.put("interLanguage", ((Map)object).get("interLanguage"));
				 tempMap=(Map) this.queryForObject("sys.rolesGroup.getRolesGroupList",paramMap);
			}
			if(MENU_NOS!=null&&MENU_NOS.length>0){
				for(int i=0;i<MENU_NOS.length;i++){
					tempMap.put("MENU_NO",  MENU_NOS[i]);
					tempMap.put("SELECTR",  SELECTRs==null||SELECTRs.get(MENU_NOS[i])==null?0:SELECTRs.get(MENU_NOS[i]));
					tempMap.put("INSERTR",  INSERTRs==null||INSERTRs.get(MENU_NOS[i])==null?0:INSERTRs.get(MENU_NOS[i]));
					tempMap.put("UPDATER",  UPDATERs==null||UPDATERs.get(MENU_NOS[i])==null?0:UPDATERs.get(MENU_NOS[i]));
					tempMap.put("DELETER",  DELETERs==null||DELETERs.get(MENU_NOS[i])==null?0:DELETERs.get(MENU_NOS[i]));
					if((Integer)this.queryForObject("sys.rolesGroup.checkSyScreenGrant", tempMap)>0){
						this.update("sys.rolesGroup.updateSyScreenGrant", tempMap);
					}else{
						this.insert("sys.rolesGroup.saveSyScreenGrant" , tempMap);
					}
				 }
			}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getSelectMenuForRoles(Object object) {
		List temp=null;
		try {
			  temp=this.queryForList("sys.rolesGroup.getSelectMenuForRoles", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return temp;
	}

	@Override
	public String deleteRolesGroupView(HttpServletRequest request, Object object)
			throws Exception {
		 try {
			this.delete("sys.rolesGroup.deleteRolesGroupItem",object);
			this.delete("sys.rolesGroup.deleteRolesGroup",object);
			this.delete("sys.rolesGroup.deleteRolesGroupRelation",object);
		} catch (Exception e) {
			e.printStackTrace();
			return "操作失败！";
		}
		return "操作成功！";
	}

	/**
	 * 取得所有权限组的分组列表SY_ROLE_GROUP
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getSyRolesGroupList(Object obj) {
		List returnList = new ArrayList() ;
		returnList = this.getSyRolesGroupList(obj, -1, -1) ;
		return returnList ;
	}

	/**
	 * 取得所有权限组的分组列表SY_ROLE_GROUP
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getSyRolesGroupList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("sys.rolesGroup.getSyRolesGroupList", obj, currentPage, pageSize);
			}else{
				returnList = this.queryForList("sys.rolesGroup.getSyRolesGroupList", obj);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	@Override
	public int getSyRolesGroupCnt(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("sys.rolesGroup.getSyRolesGroupListCnt", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List getSyRolesIfCheckedList(Object object){
		List temp=null;
		try {
			temp=this.queryForList("sys.rolesGroup.getSyRolesIfCheckedList", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return temp;
	}
	/**
	 * 保存或更新到sy_role_group表以及sy_role_group_relation表
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void saveOrUpdateSyRolesGroupInfo(HttpServletRequest request,
			Object object) throws Exception {
		String[] noArr=(String[])((Map)object).get("ROLE_NOS");
		LinkedHashMap paramMap=new LinkedHashMap();
		if(((Map)object).get("ROLE_GROUP_NO")!=null&&!((Map)object).get("ROLE_GROUP_NO").toString().equals("")){
			paramMap=(LinkedHashMap)object;
			this.syLanguageDao.updateSyGlobalName(object);
			this.update("sys.rolesGroup.UpdateSyRoleGroupInfo", object);
			this.delete("sys.rolesGroup.deleteSyRoleGroupRelationInfo", object);
		}else{
			paramMap = (LinkedHashMap)this.syLanguageDao.saveSyGlobalName(object);
			paramMap.put("ROLE_GROUP_NO", paramMap.get("NO"));
			this.insert("sys.rolesGroup.InsertSyRoleGroupInfo", object);//保存到sy_role_group
			//先根据权限组的名称查找gsod权限表中存不存在，如果存在就查当前法人的存不存在如果存在不做操作，如果不存在
			//返回groupNo的值，如果都不存在groupNo为gsod表中的最大值+1
//			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
//			((Map)object).put("ROLE_GSOD_NAME",paramMap.get("proName_zh"));
//			((Map)object).put("ACTIVITY",1);
//			((Map)object).put("CREATE_BY",admin.getPersonId());
//			List listName = this.getGsodRoleGroupList(object);
//			if(listName != null && listName.size()>0){
//				((Map)object).put("CPNY_ID",((Map)object).get("defaultCpny"));
//				List listCpnyName = this.getGsodRoleGroupList(object);
//				((Map)object).put("SY_ROLE_GROUP_NO",((Map)object).get("ROLE_GROUP_NO"));
//				if(listCpnyName==null && listCpnyName.size()==0){
//					((Map)object).put("GROUPNo",((Map)listName).get("GROUPNO"));
//					this.insert("sys.rolesGroup.addGsodRoleGroupInfo", object);//保存到sy_role_group_gsod
//				}
//			}else{
//				int maxNum = this.findGroupNoFromGsod();
//				((Map)object).put("GROUPNo",maxNum+1);
//				((Map)object).put("SY_ROLE_GROUP_NO",((Map)object).get("ROLE_GROUP_NO"));
//				this.insert("sys.rolesGroup.addGsodRoleGroupInfo", object);//保存到sy_role_group_gsod
//			}
		}
		if(noArr!=null&&noArr.length>0){
			for(int i=0;i<noArr.length;i++){
				paramMap.put("ROLE_NO", noArr[i]);
				this.insert("sys.rolesGroup.InsertSyRoleGroupRelationInfo", paramMap);
			}
		}
	}
	
	/**
	 * 从SY_ROLE_GROUP里取
	 */
	@SuppressWarnings("unchecked")
	public Object getSyRolesGroupInfo(Object obj) {
		Object returnObj = new Object() ;
		List returnList = this.getSyRolesGroupList(obj) ;
		if(returnList.size() > 0){
			returnObj = returnList.get(0) ;
		}
		return returnObj ;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void deleteSyRolesGroupView(HttpServletRequest request, Object object)
			throws Exception {
		this.syLanguageDao.deleteSyGlobalName(object);
		((Map)object).put("ROLE_GROUP_NO", ((Map)object).get("NO"));
		this.delete("sys.rolesGroup.deleteSyRolesGroup",object);//删除sy_role_group表中的数据
		this.delete("sys.rolesGroup.deleteSyRoleGroupRelationInfo",object);//删除sy_role_group_relation中的数据
		this.delete("sys.rolesGroup.deleteSyUserRelation", object);//删除sy_user_relation中的数据
	}
	
	@SuppressWarnings("unchecked")
	public List getMenuForRolesGroup(Object obj){
		List temp=null;
		try {
			  temp=this.queryForList("sys.rolesGroup.getMenuForRolesGroup", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return temp;
	}
	
	/***********************************GSOD权限**************************************/
	/**
	 * 查找gsod权限单条记录的详细信息
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
    public Object getGsodRoleGroup(Object object){
		Object returnObj = new Object() ;
		List returnList = this.getGsodRoleGroupList(object) ;
		if(returnList.size() > 0){
			returnObj = returnList.get(0) ;
		}
		return returnObj ;
	}
	
	/**
	 * 查找gsod权限的List（不分页）
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getGsodRoleGroupList(Object object){
		List returnList = new ArrayList() ;
		returnList = this.getGsodRoleGroupList(object, -1, -1) ;
		return returnList ;
	}
	
	/**
	 * 查找gsod权限的数量
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int getGsodRoleGroupCnt(Object object){
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("sys.rolesGroup.getGsodRoleGroupCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	 * 分页查找gsod权限的List
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getGsodRoleGroupList(Object object, int currentPage, int pageSize){
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("sys.rolesGroup.getGsodRoleGroupList", object, currentPage, pageSize);
			}else{
				returnList = this.queryForList("sys.rolesGroup.getGsodRoleGroupList", object);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 添加gsod的新纪录
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int addGsodRoleGroupInfo(Object object) throws SQLException{
		try {
			this.insert("sys.rolesGroup.addGsodRoleGroupInfo", object) ;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 1 ;
	}
	
	/**
	 * 修改gsod权限
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int deleteGsodRoleGroupInfoAll(Object object){
		try {
			this.delete("sys.rolesGroup.deleteGsodRoleGroupInfoAll", object) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return 1 ;
	}
	
	/**
	 * 删除gsod权限记录
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int deleteGsodRoleGroupInfo(Object object){
		try {
			this.delete("sys.rolesGroup.deleteGsodRoleGroupInfo", object) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return 0 ;
	}
	
	
	public int findGroupNoFromGsod(){
		int num = 0;
		try {
		num = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("sys.rolesGroup.getGsodGroupNo")),
					Integer.class);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return num ;
	}
	
	/**
	 * 添加和修改的时候看名称是否已经存在
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int checkGsodRoleGroupExsit(Object object){
		int returnInt = 0 ;
		try {
			int returnInt1 = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("sys.rolesGroup.checkGsodRoleGroupExsitName", object)), Integer.class) ;
			int returnInt2 = 
					NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("sys.rolesGroup.checkGsodRoleGroupExsit", object)), Integer.class) ;
			if(returnInt1==1){
				returnInt = 1;
			}else if(returnInt2==1){
				returnInt = 2;
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	 * 查找getSyRolesGroupListGsod权限的List（不分页） 添加使用
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getSyRolesGroupListGsodAdd(Object object){
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("sys.rolesGroup.getSyRolesGroupListGsodAdd", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	/**
	 * 查找getSyRolesGroupListGsod权限的List（不分页） 修改使用
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getSyRolesGroupListGsod(Object object){
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("sys.rolesGroup.getSyRolesGroupListGsod", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	/**
	 * 取得用户所有权限的id
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getRoleIdByPersonId(Object obj) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("sys.rolesGroup.getRoleIdByPersonId", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList ;
	}
	

	/**
	 * 查询是否存在于考勤员表
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getArSupervisor(Object obj) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("sys.rolesGroup.getArSupervisor", obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList ;
	}
	/**
	 * 获取员工的总数 首页显示
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int getTotalEmpCountForMain(Object obj) {
		int returnObj = 0 ;
		try {
			returnObj = Integer.parseInt(this.queryForObject("sys.rolesGroup.getTotalEmpCountForMain", obj).toString());
		} catch (SQLException e) {
			e.printStackTrace();
			returnObj = 0 ;
		}
		return returnObj ;
	}
	/**
	 * 获取在职的员工 首页显示
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int getInCpnyTotalEmpCountForMain(Object obj) {
		int returnObj = 0 ;
		try {
			returnObj = Integer.parseInt(this.queryForObject("sys.rolesGroup.getInCpnyTotalEmpCountForMain", obj).toString());
		} catch (SQLException e) {
			e.printStackTrace();
			returnObj = 0 ;
		}
		return returnObj ;
	}
	/**
	 * 获取离职的员工 首页显示
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int getLeftManTotalEmpCountForMain(Object obj) {
		int returnObj = 0 ;
		try {
			returnObj = Integer.parseInt(this.queryForObject("sys.rolesGroup.getLeftManTotalEmpCountForMain", obj).toString());
		} catch (SQLException e) {
			e.printStackTrace();
			returnObj = 0 ;
		}
		return returnObj ;
	}
	/**
	 * 获取新入职的员工 首页显示
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int getNewManTotalEmpCountForMain(Object obj) {
		int returnObj = 0 ;
		try {
			returnObj = Integer.parseInt(this.queryForObject("sys.rolesGroup.getNewManTotalEmpCountForMain", obj).toString());
		} catch (SQLException e) {
			e.printStackTrace();
			returnObj = 0 ;
		}
		return returnObj ;
	}
}
