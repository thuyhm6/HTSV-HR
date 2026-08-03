package com.ait.hrm.dao.impl;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.hrm.dao.AddRecPageHubDao;
import com.ait.sys.bean.AdminBean;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.SqlMapClientSupport;
import com.ait.web.util.StringUtil;

@Repository
public class AddRecPageHubDaoImpl extends SqlMapClientSupport implements AddRecPageHubDao {	
	
	/**
	 * 个人添加招聘者信息(add recPageInfo)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void addRecPageInfo(LinkedHashMap paramMap) throws Exception {
		LinkedHashMap tempMap = new LinkedHashMap();
		
			//附件上传
			if (paramMap.get("fileName") != null && !"".equals(StringUtil.checkNull(paramMap.get("fileName")))) {
				String[] fileName = StringUtil.checkNull(paramMap.get("fileName")).split(";");
				String[] fileUrl = StringUtil.checkNull(paramMap.get("fileUrl")).split(";");
				if (fileUrl != null && fileUrl.length > 0) {
					for (int j=0;j<fileUrl.length ;j++) {
						paramMap.put("fileName", fileName[j]);
						paramMap.put("fileUrl", "/resources/temp/recFiles/" + paramMap.get("adminID") + "/" + fileUrl[j]);
					}
				}
			}
			if(paramMap.get("CPNY_ID").equals("HTSV")){
				this.insert("hrm.recPage.insertRecPage", paramMap);
			} else {
				String REC_EMPLOYEE_NO = getWORKEXPERSeq();
				paramMap.put("REC_EMPLOYEE_NO", REC_EMPLOYEE_NO);
				this.insert("hrm.recPage.insertRecPageHAE", paramMap);
				String CPNY_NAME = (String) paramMap.get("CPNY_NAME");
				if(CPNY_NAME != "" && CPNY_NAME != null){
					this.insert("hrm.recPage.insertRecWORKEXPERHAE", paramMap);
				}
				
			}
	}
	
	public String getWORKEXPERSeq(){
		String str = null;
		try {
			str = ObjectUtils.toString(this
					.queryForObject("hrm.recPage.getWORKEXPERSeq"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	/**
	 * 人事修改招聘者信息(update recPageInfo)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void addEditRecPageInfoHub(LinkedHashMap paramMap) throws Exception {
			//附件上传
			if (paramMap.get("fileName") != null && !"".equals(StringUtil.checkNull(paramMap.get("fileName")))) {
				String[] fileName = StringUtil.checkNull(paramMap.get("fileName")).split(";");
				String[] fileUrl = StringUtil.checkNull(paramMap.get("fileUrl")).split(";");
				if (fileUrl != null && fileUrl.length > 0) {
					for (int j=0;j<fileUrl.length ;j++) {
						paramMap.put("fileName", fileName[j]);
						paramMap.put("fileUrl", "/resources/temp/recFiles/" + paramMap.get("adminID") + "/" + fileUrl[j]);
					}
				}
			}
			if(paramMap.get("CPNY_ID").equals("HTSV")){
				this.update("hrm.recPage.editRecPage", paramMap);
			} else {
				this.update("hrm.recPage.editRecPageHAE", paramMap);
				int returnInt =  NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("hrm.recPage.IS_EXISTS_REC_EMPLOYEE_NO", paramMap)), Integer.class) ;
				if(returnInt > 0 ){
					this.update("hrm.recPage.editRecPageWorkHAE", paramMap);
				} else{
					String CPNY_NAME = (String) paramMap.get("CPNY_NAME");
					if(CPNY_NAME != "" && CPNY_NAME != null){
						this.insert("hrm.recPage.insertRecWORKEXPERHAE", paramMap);
					}
				}
			}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getRecPageList(LinkedHashMap map) throws Exception {
		// TODO Auto-generated method stub
		List returnList = new ArrayList();
		returnList = this.queryForList("hrm.recPage.getRecPageList",map);
		return returnList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getRecPageListCnt(LinkedHashMap map) throws Exception {
		// TODO Auto-generated method stub
		int count = 0;
		count = NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("hrm.recPage.getRecPageListCnt",map)), Integer.class);
		return count;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List getRecPageInfo(LinkedHashMap map) throws Exception {
		// TODO Auto-generated method stub
		List returnList = new ArrayList();
		returnList = this.queryForList("hrm.recPage.getRecPageInfo",map);
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List getRecPageworkInfo(LinkedHashMap map) throws Exception {
		List returnList = new ArrayList();
		returnList = this.queryForList("hrm.recPage.getRecPageworkInfo",map);
		return returnList;
	}
	
	/**
	 * 删除招聘者信息(delete recPageInfo)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void deleteRecPageInfo(LinkedHashMap paramMap) throws Exception {
	
			this.update("hrm.recPage.deleteRecPageInfo", paramMap);
	}
	
	/**
	 * 删除空的数据 
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int deleteRecPageList(Object object)throws Exception {
		this.insert("hrm.recPage.deleteRecPageList",object);
		return 1;
	}
}
