package com.ait.pa.dao.imp;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;

import com.ait.pa.dao.PaResultDao;
import com.ait.web.util.SqlMapClientSupport;

@Repository
public class PaResultDaoImpl extends SqlMapClientSupport implements PaResultDao {
	
	/**
	 * 工资结算
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String paBalance(LinkedHashMap paramMap) {
		String returnString = "" ;
		
		try {
			paramMap.put("message", "") ;
			this.insert("pa.paResult.paBalance", paramMap) ;
			
			returnString = ObjectUtils.toString(paramMap.get("message")) ;
		} catch (SQLException e) {	
			returnString = e.getMessage() ;
			e.printStackTrace();
		}
		
		return returnString ;
	}

	/**
	 * 删除登录用户上次选择的"计算结果"中的选项
	 * @param paramMap
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void deleteSelectedOptions(LinkedHashMap paramMap) throws Exception {
		this.delete("pa.paResult.deleteSelectedOptions", paramMap);
	}

	/**
	 * 查询登录用户是否有保存过"计算结果"的选项
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Integer selectSelectedOptions(LinkedHashMap paramMap) throws Exception {
		return (Integer) this.queryForObject("pa.paResult.selectSelectedOptions", paramMap);
	}

	/**
	 * 查询工资发放日
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author zhengxiaochen zhengxiaochen@ait.net.cn
	* @date 2013-9-20 下午06:10:07 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getiPaGiveDate(LinkedHashMap paramMap) throws Exception {
		
		return this.queryForList("pa.paResult.getiPaGiveDate", paramMap);
	}
}
