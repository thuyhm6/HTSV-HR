package com.ait.paEcc.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;

import com.ait.paEcc.dao.PaEccDAO;
import com.ait.web.util.SqlMapClientSupport;

@Repository
public class PaEccDAOImpl extends SqlMapClientSupport 
			implements PaEccDAO{

	@Override
	public List getEmpPaEcc(Object parameterObject) {
		List returnList = null;
		try {
			returnList = this.queryForList("paEcc.common.searchEmpEcc", parameterObject);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	@Override
	public int getPaEccCnt(Object parameterObject) {
		int returnInt = 0;
		try {
			returnInt = Integer.parseInt(ObjectUtils.toString(this.queryForObject("paEcc.common.getEmpIdListCnt",
					parameterObject)));
		} catch (Exception e) {
			e.printStackTrace();
			returnInt = -1;
		}
		return returnInt;
	}

	@Override
	public List getPaEccInfo(Object parameterObject) {
		List returnList = null;
		try {
			returnList = this.queryForList("paEcc.common.getPaEccInfo", parameterObject);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnList;
	}
	@Override
	public List getPaEccInfo(Object parameterObject, int currentPage,
			int pageSize) {
		List returnList = new ArrayList() ;
		try {
			if (currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("paEcc.common.getPaEccInfo", parameterObject, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("paEcc.common.getPaEccInfo", parameterObject);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}

	@Override
	public int delPaEccInfo(Map parameterMap) {
		int returnInt = -1;
		try {
			this.startTransaction();
			String[] dels = parameterMap.get("dels").toString().split(",");
			for (int i = 0; i < dels.length; i++) {
				parameterMap.put("EMPID", dels[i]);
				returnInt = Integer.parseInt(ObjectUtils.toString(this.delete("paEcc.common.delPaEccInfo",
						parameterMap)));
			}
			this.commitTransation();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				this.endTransation();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return returnInt;
	}

	@Override
	public int calculatePaEcc(Map parameterMap) {
		int returnInt = 0;
		try {
			this.queryForObject("paEcc.common.calculatePaEcc",
					parameterMap);
		} catch (Exception e) {
			e.printStackTrace();
			returnInt = -1;
		}
		return returnInt;
	}

	@Override
	public int updatePaEcc(List<Map<String, Object>> list) {
		int result = 1;
		try {
			this.startTransaction();
			for (Map<String, Object> map : list) {
				this.update("paEcc.common.updatePaEcc", map);
			}
			this.commitTransation();
		} catch (Exception e) {
			e.printStackTrace();
			result = 0;
		}finally{
			try {
				this.endTransation();
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

	@Override
	public int settlementPaEcc(Map parameterMap) {
		int returnInt = 1;
		try {
			this.queryForObject("paEcc.common.settlementPaEcc", 
					parameterMap);
		} catch (Exception e) {
			e.printStackTrace();
			returnInt = -1;
		}
		return returnInt;
	}

	@Override
	public int cancelSettlementPaEcc(Map parameterMap) {
		int returnInt = 1;
		try {
			this.queryForObject("paEcc.common.cancelSettlementPaEcc", 
					parameterMap);
		} catch (Exception e) {
			e.printStackTrace();
			returnInt = -1;
		}
		return returnInt;
	}

	@Override
	public List searchBatchesByPaMonth(Object parameterObject) {
		List returnList = null;
		try {
			returnList = this.queryForList("paEcc.common.searchBatchesByPaMonth",
					parameterObject);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@Override
	public String getEccFlag(Object obj) throws Exception {
		String returnStr = "";
		try {
			returnStr = ObjectUtils.toString(this.queryForObject("paEcc.common.getEccFlag", obj));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnStr;
	}

	@Override
	public String getEccResultNo(Object parameterObject) {
		String returnStr = "";
		try {
			returnStr = ObjectUtils.toString(this.queryForObject("paEcc.common.getEccResultNo", 
						parameterObject));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnStr;
	}

	@Override
	public Map getResignInfo(Object parameterObject) {
		Map returnMap = new LinkedHashMap();
		try {
			returnMap = (LinkedHashMap) this.queryForObject("paEcc.common.getResignInfo",
					parameterObject);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnMap;
	}

	@Override
	public List getEccEmpPaInfo(Object parameterObject) {
		List returnList = null;
		try {
			returnList = this.queryForList("paEcc.common.getEccEmpPaInfo",
						parameterObject);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@Override
	public String getEmpPAStartMonth(Object parameterObject) {
		String returnStr = "";
		try {
			returnStr = ObjectUtils.toString(this.queryForObject("paEcc.common.getEmpPAStartMonth",
					parameterObject));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnStr;
	}

}
