package com.ait.pa.dao.imp;

import org.springframework.stereotype.Repository;

import com.ait.pa.dao.salaryCheckDao;
import com.ait.web.util.SqlMapClientSupport;

@Repository
public class salaryCheckDaoImpl extends SqlMapClientSupport implements salaryCheckDao{
	/**
	 * 根据公司id和项目item_no来确定该法人对该条公司代码的使用状态
	 */
	@SuppressWarnings("unchecked")
	public int findTypeByParam(String cpny_id,String item_no){
		return 0;
	}
}
