package com.ait.sys.dao.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ait.sys.bean.AdminBean;
import com.ait.sys.dao.MenuDao;
import com.ait.sys.dao.SyLanguageDao;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.SqlMapClientSupport;
import com.ait.web.util.StringUtil;

/**
 * 
 * Copyright:   AIT (c)
 * Company:     AIT
 * @fileName MenuDaoImpl.java
 * @author zhouyeqing(zhouyeqing@ait.net.cn)
 * @Date 2012-1-4 下午04:37:28
 * @version 5.0
 *
 */
@Repository
public class MenuDaoImpl extends SqlMapClientSupport implements MenuDao {
	
	@Autowired
	private SyLanguageDao syLanguageDao;

	@SuppressWarnings("unchecked")
	@Override
	public List getMenuList(Object object) {
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getMenuList(Object object, int currentPage, int pageSize)throws Exception {
		List Rows=this.queryForList("sys.menu.getMenuList", object, currentPage, pageSize);
		return Rows;
	}

	@Override
	public int getMenuListCnt(Object object) throws Exception {
		Integer temp=(Integer) this.queryForObject("sys.menu.getMenuListCnt", object);
		return temp!=null?temp:0;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void updateMenuActivity(Object object) throws Exception {
		@SuppressWarnings("unused")
		String back="";
		this.update("sys.menu.updateMenuActivity", object);
		Map temp=this.getMenuByID(object);
		if(temp!=null&&temp.get("ACTIVITY")!=null){			
			if(1==(new Integer(temp.get("ACTIVITY").toString()))){
				back="已经启用！";
			}
			if(0==(new Integer(temp.get("ACTIVITY").toString()))){
				back="已经关闭！";
			}
		}
	}

	@SuppressWarnings("unchecked")
	public Map getMenuByID(Object object)throws Exception{
		Map temp=(Map) this.queryForObject("sys.menu.getMenuByID",object);
		return temp;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getMenuTree(Object object) throws Exception {
		List temp=this.queryForList("sys.menu.getMenuTree", object);
		return temp;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String saveMenu(Object obj) throws Exception {
		LinkedHashMap object = (LinkedHashMap)this.syLanguageDao.saveSyGlobalName(obj);
		this.insert("sys.menu.saveMenu", object);
		return "菜单保存成功";
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map getMenuByNo(Object object) throws Exception {
		Map temp=(Map) this.queryForObject("sys.menu.getMenuByNo", object);
		return temp!=null?temp:null;
	}

	@Override
	public void updateMenu(Object object) throws Exception {
		this.syLanguageDao.updateSyGlobalName(object);
		this.update("sys.menu.updateMenu",object);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getMenuParamList(Object object, int currentPage, int pageSize)
			throws Exception {
		List Rows=this.queryForList("sys.menu.getMenuParamList", object, currentPage, pageSize);
		return Rows;
	}

	@Override
	public int getMenuParamListCnt(Object object) throws Exception {
		Integer temp=(Integer) this.queryForObject("sys.menu.getMenuParamListCnt", object);
		return temp!=null?temp:0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getMenuParamByNo(Object object) throws Exception {
		Map temp=(Map) this.queryForObject("sys.menu.getMenuParamByNo", object);
		List tempList=null;
		if(temp!=null){
			tempList=this.queryForList("sys.menu.getMenuParamByCpnyId",temp);
		}
		return tempList!=null?tempList:null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getMenuTreeForAll(Object object) throws Exception {
		List temp=this.queryForList("sys.menu.getMenuTreeForAll", object);
		return temp;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String saveMenuParam(Object object,HttpServletRequest request) throws Exception {
		List<String> temp=StringUtil.getSplitParams(((Map)object).get("MENU_NOS").toString(), ",");
		this.delete("sys.menu.deleteMenuByParentNo", object);
		if(temp!=null&&temp.size()>0){
			if(!(temp.size()==1&&temp.get(0).equals(""))){
				for(String menu_no:temp){
					((Map)object).put("MENU_NO", new Integer(menu_no));
					this.insert("sys.menu.saveMenuParam", object);
				}
			}
		}
		return "";
	}

	@SuppressWarnings("unchecked")
	@Override
	public String updateMenuParam(Object object,HttpServletRequest request) throws Exception {
		List<String> temp=StringUtil.getSplitParams(((Map)object).get("MENU_NOS").toString(), ",");
		if(temp!=null&&temp.size()>0)
		for(String menu_no:temp){
			((Map)object).put("MENU_NO", new Integer(menu_no));
			Map menuParma=(Map) this.queryForObject("sys.menu.getMenuParamByCpnyIdAndMenuNo",object);
			AdminBean admin = SessionUtil.getLoginUserFromSession(request) ;
			((Map) object).put("CREATED_BY",admin.getPersonId()!=null?admin.getPersonId().toString():"IT");
			((Map) object).put("UPDATED_BY",admin.getPersonId()!=null?admin.getPersonId().toString():"IT");
			@SuppressWarnings("unused")
			String no = "";
			if(menuParma!=null){
				((Map)object).put("PARAM_NO", menuParma.get("PARAM_NO"));
				no= menuParma.get("PARAM_NO").toString();
			}
			if((Integer)this.queryForObject("sys.menu.checkMenuParma", object)>0){
				this.update("sys.menu.updateMenuParam",object);
			}else{
				this.insert("sys.menu.saveMenuParam", object);
			}
			
		}
		return "";
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map getMenuParamByPK(Object object) throws Exception {
		Map temp=(Map) this.queryForObject("sys.menu.getMenuParamByNo", object);
		return temp!=null?temp:null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getMenuForRolesGroup(Object object) throws Exception {
		List temp=this.queryForList("sys.menu.getMenuForRolesGroup", object);
		return temp;
	}

	@Override
	public int getMenuForRolesGroupCnt(Object object) throws Exception {
		Integer temp=(Integer) this.queryForObject("sys.menu.getMenuForRolesGroupCnt", object);
		return temp!=null?temp:0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getMenuTreeByParentMenu(Object object) throws Exception {
		List temp=this.queryForList("sys.menu.getMenuTreeByParentMenu", object);
		return temp;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getMenuTreeByParentMenuSelect(Object object) throws Exception {
		List temp=this.queryForList("sys.menu.getMenuTreeByParentMenuSelect", object);
		return temp;
	}
	
	@SuppressWarnings("unchecked")
	public List validateMenuIdExist(Object object)throws Exception{
		List temp=this.queryForList("sys.menu.validateMenuIdExist", object);
		return temp;
	}
}
