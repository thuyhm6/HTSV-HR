package com.ait.ar.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.ar.dao.ArCardAssociateDao;
import com.ait.ar.dao.AttendanceKeeperDao;
import com.ait.web.util.SqlMapClientSupport;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: ArCardAssociateDaoImpl.java
 * @Description:
 * @Create date: 2012-6-4 上午11:33:35
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
@Repository
public class ArCardAssociateDaoImpl extends SqlMapClientSupport implements ArCardAssociateDao {
	
	/**
	 * 取得所有考勤员列表(get AttendanceKeeper List)
	 * @param Object
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getCardAssociateList(Object obj) {
		List returnList = new ArrayList() ;
		
		returnList = this.getCardAssociateList(obj, -1, -1) ; 
		
		return returnList ;
	}
	
	/**
	 * 取得所有考勤员列表(get AttendanceKeeper List)
	 * @param Object
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getCardAssociateList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("ar.cardAssociate.getCardAssociateList", obj, currentPage, pageSize) ;
			}
			else{
				returnList = this.queryForList("ar.cardAssociate.getCardAssociateList", obj) ;
			}
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnList ;
	}

	/**
	 * 查看卡号数量(get CardAssociate Cnt)
	 * @param Object
	 * @return int
	 * @throws
	 */
	@Override
	public int getCardAssociateCnt(Object object) {
		int returnInt = 0 ;
		
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ar.cardAssociate.getCardAssociateCnt", object)), Integer.class) ;
			
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		
		return returnInt ;
	}
	
	/**
	 * 修改保存(update CardAssociate Info)
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void updateCardAssociateInfo(Object object) throws Exception {
		
		this.deleteForList("ar.cardAssociate.deleteCardAssociateInfo", (List)object) ;
		
		this.insertForList("ar.cardAssociate.insertCardAssociateInfo", (List)object) ;
	}
	
	/**
	 * 检查开始结束时间
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String checkCardValidity(List list) {
		
		String returnStr = "";
		if(list != null){
			for(int i=0;i<list.size();i++){
				Map paraMap = new LinkedHashMap();
				int count = 0;
				paraMap = (LinkedHashMap)list.get(i);
				try {
					count = NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ar.cardAssociate.checkCardValidity", paraMap)), Integer.class) ;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					if("".equals(returnStr)){
						returnStr = paraMap.get("CARD_NO") != null ? paraMap.get("CARD_NO").toString() : "";
					}else{
						returnStr = returnStr + "," + (paraMap.get("CARD_NO") != null ? paraMap.get("CARD_NO").toString() : "");
					}
				}
				
				if(count != 0){
					if("".equals(returnStr)){
						returnStr = paraMap.get("CARD_NO") != null ? paraMap.get("CARD_NO").toString() : "";
					}else{
						returnStr = returnStr + "," + (paraMap.get("CARD_NO") != null ? paraMap.get("CARD_NO").toString() : "");
					}
				}
			}
		}
		
		return returnStr;
	}
}
