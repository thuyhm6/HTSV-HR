package com.ait.ar.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ait.ar.dao.ArEmailDao;
import com.ait.web.util.SqlMapClientSupport;

@Repository
public class ArEmailDaoImpl extends SqlMapClientSupport implements ArEmailDao {
	@Override
	public List getArEmailList(Object obj) {
		Map object = (LinkedHashMap) obj;
		List returnList = new ArrayList();
		try {
			if(object.get("CPNY_ID").equals("TSTO") || object.get("CPNY_ID")=="TSTO"){
			    returnList = this.queryForList("ar.email.getArEmailLgech", obj);
			}else if(object.get("CPNY_ID").equals("SST") || object.get("CPNY_ID")=="SST"){
				returnList = this.queryForList("ar.email.getArEmailLgeta", obj);
			}else if(object.get("CPNY_ID").equals("LGETR") || object.get("CPNY_ID")=="LGETR"){
				returnList = this.queryForList("ar.email.getArEmailLgetr", obj);
			}else if(object.get("CPNY_ID").equals("LGEND") || object.get("CPNY_ID")=="LGEND"){
				returnList = this.queryForList("ar.email.getArEmailLgend", obj);
			}else if(object.get("CPNY_ID").equals("LGEHN") || object.get("CPNY_ID")=="LGEHN"){
				returnList = this.queryForList("ar.email.getArEmailLgehn", obj);
			}else if(object.get("CPNY_ID").equals("LGEHZ") || object.get("CPNY_ID")=="LGEHZ"){
				returnList = this.queryForList("ar.email.getArEmailLgehz", obj);
			}else if(object.get("CPNY_ID").equals("LGEKS") || object.get("CPNY_ID")=="LGEKS"){
				returnList = this.queryForList("ar.email.getArEmailLgeks", obj);
			}else if(object.get("CPNY_ID").equals("LGEPN") || object.get("CPNY_ID")=="LGEPN"){
				returnList = this.queryForList("ar.email.getArEmailLgepn", obj);
			}else if(object.get("CPNY_ID").equals("LGEQA") || object.get("CPNY_ID")=="LGEQA"){
				returnList = this.queryForList("ar.email.getArEmailLgeqa", obj);
			}else if(object.get("CPNY_ID").equals("LGEQH") || object.get("CPNY_ID")=="LGEQH"){
				returnList = this.queryForList("ar.email.getArEmailLgeqh", obj);
			}else if(object.get("CPNY_ID").equals("LGESY") || object.get("CPNY_ID")=="LGESY"){
				returnList = this.queryForList("ar.email.getArEmailLgesy", obj);
			}else if(object.get("CPNY_ID").equals("LGEYT") || object.get("CPNY_ID")=="LGEYT"){
				returnList = this.queryForList("ar.email.getArEmailLgeyt", obj);
			}else if(object.get("CPNY_ID").equals("LGECR") || object.get("CPNY_ID")=="LGECR"){
				returnList = this.queryForList("ar.email.getArEmailLgecr", obj);
			}                
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	
	@Override
	public List getArDetailListEmail(Object object) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ar.email.getArDetailListEmail",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	//查找考勤员
	@Override
	public List getAttKeeperList(Object object){
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ar.email.getAttKeeperList",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	//查找部门
	@SuppressWarnings("unchecked")
	public List getOrgDeptList(Object object){
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ar.email.getOrgDeptList",
					object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
}
