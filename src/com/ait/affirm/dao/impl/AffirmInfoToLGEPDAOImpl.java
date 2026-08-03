package com.ait.affirm.dao.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.ait.affirm.dao.AffirmInfoToLGEPDAO;
import com.ait.web.util.SqlMapClientSupport;

@Repository
public class AffirmInfoToLGEPDAOImpl extends SqlMapClientSupport implements AffirmInfoToLGEPDAO{

	/**
	 * 服务器ip:端口号
	 */
	private String LGEP_SEND_FLAG;
    
	public void insertAffirmInfo(Object object)  throws Exception{
		if(LGEP_SEND_FLAG != null && "0".equals(LGEP_SEND_FLAG)){
			Map map = (LinkedHashMap)object;
			map.put("SABUN1", "11111125");
		}
		this.insert("affirm.LGEP.addAffirmInfo", object) ;
	}
	
	public void insertAffirmInfoNoDate(Object object)  throws Exception{
		if(LGEP_SEND_FLAG != null && "0".equals(LGEP_SEND_FLAG)){
			Map map = (LinkedHashMap)object;
			map.put("SABUN1", "11111125");
		}
		this.insert("affirm.LGEP.addAffirmInfoNoDate", object) ;
	}

	public void insertAffirmInfoDelegate(Object object)  throws Exception{
		if(LGEP_SEND_FLAG != null && "0".equals(LGEP_SEND_FLAG)){
			Map map = (LinkedHashMap)object;
			map.put("SABUN1", "11111125");
			map.put("SABUN2", "11111125");
		}
		this.insert("affirm.LGEP.addAffirmInfoDelegate", object) ;
	}
	

	public void crateAffirm(Object mapARD, Object mapARI, Object mapABY)  throws Exception{
		
		if(LGEP_SEND_FLAG != null && "0".equals(LGEP_SEND_FLAG)){
			Map map = (LinkedHashMap)mapARD;
			map.put("SABUN1", "11111125");
		}
		this.insert("affirm.LGEP.addAffirmInfoNoDate", mapARD) ;
		

		if(LGEP_SEND_FLAG != null && "0".equals(LGEP_SEND_FLAG)){
			Map map = (LinkedHashMap)mapARI;
			map.put("SABUN1", "11111125");
		}
		this.insert("affirm.LGEP.addAffirmInfo", mapARI) ;
		
		
		if(LGEP_SEND_FLAG != null && "0".equals(LGEP_SEND_FLAG)){
			Map map = (LinkedHashMap)mapABY;
			map.put("SABUN1", "11111125");
		}
		this.insert("affirm.LGEP.addAffirmInfoNoDate", mapABY) ;
	}
	

	public void affirm(Object mapAAI, Object mapABY)  throws Exception{

		if(LGEP_SEND_FLAG != null && "0".equals(LGEP_SEND_FLAG)){
			Map map = (LinkedHashMap)mapAAI;
			map.put("SABUN1", "11111125");
		} 
		this.insert("affirm.LGEP.addAffirmInfo", mapAAI) ;
		
		
		if(LGEP_SEND_FLAG != null && "0".equals(LGEP_SEND_FLAG)){
			Map map = (LinkedHashMap)mapABY;
			map.put("SABUN1", "11111125");
		}
		this.insert("affirm.LGEP.addAffirmInfoNoDate", mapABY) ;
	}
	
	public void affirmF(Object mapAAI, Object mapAAF)  throws Exception{

		if(LGEP_SEND_FLAG != null && "0".equals(LGEP_SEND_FLAG)){
			Map map = (LinkedHashMap)mapAAI;
			map.put("SABUN1", "11111125");
		}
		this.insert("affirm.LGEP.addAffirmInfo", mapAAI) ;
		
		
		if(LGEP_SEND_FLAG != null && "0".equals(LGEP_SEND_FLAG)){
			Map map = (LinkedHashMap)mapAAF;
			map.put("SABUN1", "11111125");
		}
		this.insert("affirm.LGEP.addAffirmInfoNoDate", mapAAF) ;
	}
}
