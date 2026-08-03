package com.ait.pa.dao;

import java.util.List;
import java.util.LinkedHashMap;

public interface PaResultDao {

	@SuppressWarnings("unchecked")
	public String paBalance(LinkedHashMap paramMap);
	
	/**
	 * 查询登录用户是否有保存过"计算结果"的选项
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Integer selectSelectedOptions(LinkedHashMap paramMap)throws Exception;
	
	
	/**
	 * 删除登录用户上次选择的"计算结果"中的选项
	 * @param paramMap
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void deleteSelectedOptions(LinkedHashMap paramMap)throws Exception;

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
	public List getiPaGiveDate(LinkedHashMap paramMap)throws Exception; 
}
