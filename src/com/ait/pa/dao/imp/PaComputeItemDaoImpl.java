package com.ait.pa.dao.imp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.pa.dao.PaComputeItemDao;
import com.ait.sys.dao.SyLanguageDao;
import com.ait.web.util.SqlMapClientSupport;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: PaComputeItemDaoImpl.java
 * @Description:
 * @Create date: 2012-1-16 下午06:45:53
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
@Repository
public class PaComputeItemDaoImpl extends SqlMapClientSupport implements PaComputeItemDao {
	
	@Autowired
	private SyLanguageDao syLanguageDao;
	/**
	 * 取得所有工资计算项目信息列表
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object getPaComputeItemInfo(Object obj) {
		LinkedHashMap returnObj = new LinkedHashMap() ;
		
		List returnList = this.getPaComputeItemList(obj) ;
		if(returnList.size() > 0){
			returnObj = (LinkedHashMap)returnList.get(0) ;
		}
		
		return returnObj ;
	}
	
	/**
	 * 取得所有工资计算项目信息列表
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPaComputeItemList(Object obj) {
		List returnList = new ArrayList() ;
		
		returnList = this.getPaComputeItemList(obj, -1, -1) ;
		
		return returnList ;
	}
	
	/**
	 * 取得所有工资计算项目信息列表
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPaComputeItemList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("pa.paComputeItem.getPaComputeItemList", obj, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("pa.paComputeItem.getPaComputeItemList", obj);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	/**
	 * 取得所有工资计算项目信息总数
	 * @param List
	 * @return
	 */
	public int getPaComputeItemCnt(Object obj) {
		int returnInt = 0 ;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.paComputeItem.getPaComputeItemCnt", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}

	/**
	 * 插入工资计算项目信息
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void addPaComputeItemInfo(Object obj) throws Exception{
		
		LinkedHashMap object = (LinkedHashMap)this.syLanguageDao.saveSyGlobalName(obj);
		this.insert("pa.paComputeItem.addPaComputeItemInfo", object) ;	
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void addPaComputeItemInfoAffirm(Object obj) throws Exception{
		
		this.insert("pa.paComputeItem.addPaComputeItemInfoAffirm", obj) ;	
	}
	
	/**
	 * 对插入工资计算项目信息进行是否重复验证
	 * @param List
	 * @return
	 */
	public int checkAddPaComputeItemInfo(Object obj) {
		
		int returnInt = 0 ;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.paComputeItem.checkAddPaComputeItemInfo", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}
	
	/**
	 * 修改工资计算项目信息
	 * @param List
	 * @return
	 */
	@Override
	public void updatePaComputeItemInfo(Object obj) throws Exception{
		
		this.syLanguageDao.updateSyGlobalName(obj);
		this.update("pa.paComputeItem.updatePaComputeItemInfo", obj) ;
		
	}
	
	/**
	 * 验证删除工资计算项目信息
	 * @param List
	 * @return
	 */
	@Override
	public int checkDeletePaComputeItemInfo(Object obj) {
		int returnInt = 0 ;
		try {
			returnInt =
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.paComputeItem.checkDeletePaComputeItemInfo", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	 * 删除工资计算项目信息
	 * @param List
	 * @return
	 */
	@Override
	public void deletePaComputeItemInfo(Object obj) throws Exception{ 
		
		this.syLanguageDao.deleteSyGlobalName(obj);
		this.delete("pa.paComputeItem.deletePaComputeItemInfo", obj) ;
		
	}
	
	/**
	 * 修改计算顺序
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int updatePaComputeItemInfoCalOrder(List list) {
		int returnInt = 0 ;
		
		try {
			 
			this.updateForList("pa.paComputeItem.updatePaComputeItemInfoCalOrder", list);
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}
	
	
	/**
	 * 修改CALCU_ORDER ByCalcuOrder
	 * @param List
	 * @return
	 */
	@Override
	public int updatePCInfoByCalcuOrder(Object obj) {
		
		try {
			this.update("pa.paComputeItem.updatePCInfoByCalcuOrder", obj) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 修改CALCU_ORDER ByItemNo
	 * @param List
	 * @return
	 */
	@Override
	public int updatePCInfoByParamNo(Object obj) {
		
		try {
			this.update("pa.paComputeItem.updatePCInfoByParamNo", obj) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 方法说明（中文，英文）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPaComputeItemParamList(Object obj) {
		
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("pa.paComputeItem.getPaComputeItemParamList", obj) ;
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
	@SuppressWarnings("unchecked")
	@Override
	public List getPaComputeItemParamListYN(Object obj) {
		
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("pa.paComputeItem.getPaComputeItemParamListYN", obj) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 为ess中的个人工资查看服务。
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPaComputeItemParamESSListYN(Object obj) {
		Map object = (LinkedHashMap)obj;
		List returnList = new ArrayList() ;
		try {
			if("3".equals(object.get("DATA_TYPE"))){
				//工资显示列表
				returnList = this.queryForList("pa.paComputeItem.getPaComputeItemParamESSListYN", obj) ;
			}else if("1".equals(object.get("DATA_TYPE"))){
				//人员信息显示列表
				returnList = this.queryForList("pa.paComputeItem.getHrComputeItemParamESSListYN", obj) ;
			}else if("2".equals(object.get("DATA_TYPE"))){
				//考勤项目显示列表
				returnList = this.queryForList("pa.paComputeItem.getArComputeItemParamESSListYN", obj) ;
			}else{
				//保险项目显示列表
				returnList = this.queryForList("pa.paComputeItem.getIsComputeItemParamESSListYN", obj) ;
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}/**
	 * 为ess中的个人工资查看服务。
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPaParamDateList(Object obj) {
		Map object = (LinkedHashMap)obj;
		List returnList = new ArrayList() ;
		try {
			if("1".equals(object.get("personId_flag"))){
				returnList = this.queryForList("pa.paComputeItem.getPaParamDateList_tr", obj) ;
			}else{
				returnList = this.queryForList("pa.paComputeItem.getPaParamDateList", obj) ;
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
	@SuppressWarnings("unchecked")
	@Override
	public List getPaListYN(Object obj) {
		
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("pa.paComputeItem.getPaListYN", obj) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	/* 
	* Title: getEssEmpid
	* Description:为ess工资信息查询empid
	* @author 孙鹏  
	* @date 2014年11月27日 下午2:42:24  
	* @param object
	* @return 
	* @see com.ait.pa.dao.PaComputeItemDao#getEssEmpid(java.lang.Object) 
	*/
	@SuppressWarnings("unchecked")
	@Override
	public List getEssPa(Object object) {
		Map obj = (LinkedHashMap)object;
		List returnList = new ArrayList() ;
		try {
			if("1".equals(obj.get("type")) || obj.get("type")=="1"){
				returnList = this.queryForList("pa.paComputeItem.getEssHr", object) ;
			}else{
				returnList = this.queryForList("pa.paComputeItem.getEssPa", object) ;
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
		
	}	/**
	 * 方法说明（中文，英文）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@Override
	public int checkAddPaComputeItemParamInfo(Object obj) {
		
		int returnInt = 0 ;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.paComputeItem.checkAddPaComputeItemParamInfo", obj)), Integer.class) ;
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
	public void addPaComputeItemParamInfo(Object obj) throws Exception {
		
		this.insert("pa.paComputeItem.addPaComputeItemParamInfo", obj) ;
	}
	
    public void updatePaComputeItemParam(Object obj) throws Exception {
		
		this.update("pa.paComputeItem.updatePaComputeItemParam", obj) ;
	}

	/**
	 * 方法说明（中文，英文）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@Override
	public int upPaComputeItemParamView(Object obj) {
		try {
			this.update("pa.paComputeItem.upPaComputeItemParamView", obj) ;
			return 1;
		} catch (SQLException e) {			
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
	public Object getPaComputeItemParamInfo(Object obj) {
		
		LinkedHashMap returnObj = new LinkedHashMap() ;
		
		List returnList = this.getPaComputeItemParamList(obj) ;
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
	@Override
	public int upPaComputeItemParamInfo(Object obj) {
		try {
			this.update("pa.paComputeItem.upPaComputeItemParamInfo", obj) ;
			
		} catch (SQLException e) {			
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	/**
	 * 方法说明（中文，英文）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@Override
	public int upPaComputeItemMappingInfo(Object obj) {
		try {
			this.update("pa.paComputeItem.upPaComputeItemMappingInfo", obj) ;
			
		} catch (SQLException e) {			
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 方法说明（中文，英文）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@Override
	public void deletePaComputeItemParamInfo(Object obj) throws Exception{
		
		this.delete("pa.paComputeItem.deletePaComputeItemParamInfo", obj) ;
	}
	
	@Override
	public int updatePaComputeItemParamInfoAll(String item_no) throws Exception{
		try {
		    this.delete("pa.paComputeItem.updatePaComputeItemParamInfoAll", item_no) ;
		    return 1;
	    } catch (RuntimeException e) {
		    // TODO Auto-generated catch block
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
	public List getPaComputeItemParamList(Object obj, int currentPage,int pageSize) {
		
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("pa.paComputeItem.getPaComputeItemParamList", obj, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("pa.paComputeItem.getPaComputeItemParamList", obj);
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList;
		
	}

	/**
	 * 方法说明（中文，英文）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 
	@Override
	public int getPaComputeItemParamListCnt(Object obj) {
		int returnInt = 0 ;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.paComputeItem.getPaComputeItemParamListCnt", obj)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}*/

	/**
	 * 方法说明（中文，英文）
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPaItemListForFormula(Object obj) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("pa.paComputeItem.getPaItemListForFormula", obj);
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
	@SuppressWarnings("unchecked")
	@Override
	public List getPaItemListForDayFormula(Object obj) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("pa.paComputeItem.getPaItemListForDayFormula", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
	
	@Override
	public void addShowPaComputeItemParamList(Map paramMap) throws Exception {

		this.update("pa.paComputeItem.updateShowPaComputeItemParamList", paramMap) ;
		
	}

	/* 
	* Title: getPaRemark
	* Description:获取工资调整的备注信息
	* @author 孙鹏  
	* @date 2015年2月6日 上午9:20:40  
	* @param paramMap
	* @return 
	* @see com.ait.pa.dao.PaComputeItemDao#getPaRemark(java.util.Map) 
	*/
	@Override
	public List getPaRemark(Map paramMap) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("pa.paComputeItem.getPaRemark", paramMap);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}

	/**
	 * 查Pn工资项目
	 * @param request
	 * @return
	 */
	public Object getLgepnPaInfo(Map obj){
        LinkedHashMap returnObj = new LinkedHashMap() ;
		List returnList;
		try {
			returnList = this.queryForList("pa.paComputeItem.getLgepnPaInfo", obj);
			if(returnList.size() > 0){
				returnObj = (LinkedHashMap)returnList.get(0) ;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnObj ;
	}
	
	/**
	 * 查找PN输入项目的备注和具体数值
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getLgepnPaParamInfo(Map object){
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("pa.paComputeItem.getLgepnPaParamInfo", object);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}

	/* 
	* Title: getEmpTypeCodeForPersonId
	* Description:查询人员类型
	* @author 孙鹏  
	* @date 2015年3月27日 下午1:49:31  
	* @param paramMap
	* @return 
	* @see com.ait.pa.dao.PaComputeItemDao#getEmpTypeCodeForPersonId(java.util.Map) 
	*/
	@Override
	public List getEmpTypeCodeForPersonId(Map paramMap) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("pa.paComputeItem.getEmpTypeCodeForPersonId", paramMap);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}
}
