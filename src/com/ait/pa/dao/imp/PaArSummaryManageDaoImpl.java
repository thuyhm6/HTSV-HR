package com.ait.pa.dao.imp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.pa.dao.PaArSummaryManageDao;
import com.ait.web.util.SqlMapClientSupport;

@Repository
public class PaArSummaryManageDaoImpl extends SqlMapClientSupport implements PaArSummaryManageDao {
	
	/**
	 * 取得考勤总计管理信息列表
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getPaArSummaryForManageList(Object obj) {
		List returnList = new ArrayList() ;
		try {
			returnList = this.queryForList("pa.workManagement.getPaArSummaryForManageList", obj);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 更改考勤总计管理例外信息
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void updatePaArSummaryForManageInfo(HttpServletRequest request,LinkedHashMap object) throws Exception {
		String[] isChecked = request.getParameterValues("viewCheck");
		for(int i = 0; i < isChecked.length; i++){
			object.put("AR_SUMMARY_MANAGE_NO", object.get("AR_SUMMARY_MANAGE_NO"+"_"+isChecked[i]));
			object.put("FINAL_VALUE", object.get("FINAL_VALUE"+"_"+isChecked[i]));
			object.put("REMARK", object.get("REMARK"+"_"+isChecked[i]));
			this.update("pa.workManagement.updatePaArSummaryForManageInfo", object) ;
		}
		// TODO Auto-generated method stub
	}
}
