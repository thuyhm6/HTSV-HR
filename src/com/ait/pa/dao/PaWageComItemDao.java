package com.ait.pa.dao;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: PaBasicItemDao.java
 * @Description:
 * @Create date: 2012-2-7 下午08:13:04
 * @Create by: zhangmc(zhangmingchong@ait.net.cn)
 * @version 5.1
 */
public interface PaWageComItemDao {

	@SuppressWarnings("unchecked")
	public List getPaContrastList(LinkedHashMap<String, Object> paramMap);
	@SuppressWarnings("unchecked")
	public List getPaScreeningList(LinkedHashMap<String, Object> paramMap);

	@SuppressWarnings("unchecked")
	public List getEmpType(String cpnyId);
	@SuppressWarnings("unchecked")
	
	public List getPaItem2(HttpServletRequest request);
	
}
