package com.ait.pa.dao.imp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;
import com.ait.pa.dao.PaInputItemParamDao;
import com.ait.sys.dao.SyLanguageDao;
import com.ait.web.util.SqlMapClientSupport;


/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: PaInputItemParamDaoImpl.java
 * @Description:
 * @Create date: 2012-1-16 下午06:58:05
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
@Repository
public class PaInputItemParamDaoImpl extends SqlMapClientSupport implements PaInputItemParamDao {
	
	@SuppressWarnings("unused")
	@Autowired
	private SyLanguageDao syLanguageDao;
	/**
	 * 方法说明（中文，英文）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@Override
	public void addPaInputItemParamInfo(Object obj) throws Exception{
		
		this.insert("pa.paInputItem.addPaInputItemParamInfo", obj) ;
	}
	
    public void updatePaInputItemParam(Object obj) throws Exception{
		
		this.update("pa.paInputItem.updatePaInputItemParam", obj) ;
	}

	/**
	 * 方法说明（中文，英文）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@Override
	public int checkAddPaInputItemParamInfo(Object obj) {
		int returnInt = 0 ;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.paInputItem.checkAddPaInputItemParamInfo", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}
	/**
	 * 方法说明（中文，英文）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@Override
	public void deletePaInputItemParamInfo(Object obj) throws Exception {
		
		this.delete("pa.paInputItem.deletePaInputItemParamInfo", obj) ;

	}
	
	@Override
	public int updatePaInputItemParamInfoAll(String item_no) throws Exception {
		try {
		    this.delete("pa.paInputItem.updatePaInputItemParamInfoAll", item_no) ;
		    return 1;
	    } catch (RuntimeException e) {
		    e.printStackTrace();
		    return 0;
	    }
	}

	/**
	 * 方法说明（中文，英文）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object getPaInputItemParamInfo(Object obj) {
		LinkedHashMap returnObj = new LinkedHashMap() ;
		
		List returnList = this.getPaInputItemParamList(obj) ;
		if(returnList.size() > 0){
			returnObj = (LinkedHashMap)returnList.get(0) ;
		}
		
		return returnObj ;
	}

	/**
	 * 方法说明（中文，英文）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPaInputItemParamList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("pa.paInputItem.getPaInputItemParamList", obj, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("pa.paInputItem.getPaInputItemParamList", obj);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	/**
	 * 方法说明（中文，英文）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getPaInputItemParamCnt(Object obj) {
		int returnInt = 0 ;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.paInputItem.getPaInputItemParamCnt", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}

	/**
	 * 方法说明（中文，英文）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@Override
	public void updatePaInputItemParamInfo(Object obj) throws Exception{
		
		this.update("pa.paInputItem.updatePaInputItemParamInfo", obj) ;
	}
	
	/**
	 * 方法说明（中文，英文）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@Override
	public void updatePaInputItemMappingInfo(Object obj) throws Exception{
		
		this.update("pa.paInputItem.updatePaInputItemMappingInfo", obj) ;
	}

	/**
	 * 方法说明（中文，英文）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPaInputItemParamList(Object obj) {
		List returnList = new ArrayList() ;
		
		returnList = this.getPaInputItemParamList(obj, -1, -1) ;
		
		return returnList ;
	}

	/**
	 * 方法说明（中文，英文）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@Override
	public int checkDeletePaInputItemParamInfo(Object obj) {
		int returnInt = 0 ;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.paInputItem.checkDeletePaInputItemParamInfo", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}

	/**
	 * 方法说明（中文，英文）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@Override
	public int checkDeletePaInputItemParamInfoSummary(Object obj) {
		int returnInt = 0 ;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.paInputItem.checkDeletePaInputItemParamInfoSummary", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}
	/**
	 * 批量删除输入项目参数
	 * @param Object
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void deleteCheckPaInputItemData(Object object) throws Exception {
		this.deleteForList("pa.paInputItem.deleteCheckPaInputItemData", (List)object);
	}
	
	/**
	 * 清除输入项目参数
	 * @param Object
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void clearPaInputItemDataCallback(Object object) throws Exception {
		this.delete("pa.paInputItem.clearPaInputItemDataCallback", object);
	}
	/**
	 * 批量删除基础项目参数
	 * @param Object
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void deleteCheckPaBasicItemData(Object object) throws Exception {
		this.deleteForList("pa.paInputItem.deleteCheckPaBasicItemData", (List)object);
		
	}
  /**
   * TA FSE批量删除
   */
	@Override
	public void deleteCheckPaInputItemDataFSE(Object object) throws Exception {
		this.deleteForList("pa.paInputItem.deleteCheckPaInputItemData", (List)object);
		
	}
	

}
