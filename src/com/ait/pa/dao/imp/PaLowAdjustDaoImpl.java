package com.ait.pa.dao.imp;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.pa.dao.PaLowAdjustDao;
import com.ait.pa.dao.PaProgressDao;
import com.ait.web.util.SqlMapClientSupport;

@Repository
public class PaLowAdjustDaoImpl extends SqlMapClientSupport implements
		PaLowAdjustDao {

	/**
	 * 最低工资调整列表
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getLowSalaryList(Object obj) {
		List returnList = new ArrayList();
		returnList = this.getLowSalaryList(obj, -1, -1);
		return returnList;
	}

	/**
	 * 最低工资调整列表
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getLowSalaryList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			// if (currentPage > -1 && pageSize > -1){
			// returnList = this.queryForList("pa.lowadjust.getPaLowAdjustList",
			// obj, currentPage, pageSize);
			// }else{
			// returnList = this.queryForList("pa.lowadjust.getPaLowAdjustList",
			// obj);
			// }
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"pa.lowadjust.getPaLowAdjustCuList", obj, currentPage,
						pageSize);
			} else {
				returnList = this.queryForList(
						"pa.lowadjust.getPaLowAdjustCuList", obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 最低工资调整数
	 * 
	 * @param List
	 * @return
	 */
	public int getLowSalaryListCnt(Object obj) {
		int returnInt = 0;
		try {
			// returnInt =
			// NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.lowadjust.getPaLowAdjustCnt",
			// obj)), Integer.class) ;
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("pa.lowadjust.getPaLowAdjustCuCnt", obj)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}

	@Override
	public int updateLowSalaryBatch(List list) throws Exception {

		for (int i = 0; i < list.size(); i++) {
			Map map = (Map) list.get(i);
			this.delete("pa.lowadjust.deletePaLowAdjust", map);
			this.insert("pa.lowadjust.insertPaLowAdjust", map);

		}

		return 1;
	}

	@Override
	public void cancellowadjust(Map map) throws Exception {
		// TODO Auto-generated method stub
		this.delete("pa.lowadjust.deletePaLowAdjust", map);
	}

	/**
	 * 最低工资调整列表-非促销员
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getFeiCuLowSalaryList(Object obj) {
		List returnList = new ArrayList();
		returnList = this.getFeiCuLowSalaryList(obj, -1, -1);
		return returnList;
	}

	/**
	 * 最低工资调整列表
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getFeiCuLowSalaryList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			// if (currentPage > -1 && pageSize > -1){
			// returnList = this.queryForList("pa.lowadjust.getPaLowAdjustList",
			// obj, currentPage, pageSize);
			// }else{
			// returnList = this.queryForList("pa.lowadjust.getPaLowAdjustList",
			// obj);
			// }
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"pa.lowadjust.getPaLowAdjustFeiCuList", obj,
						currentPage, pageSize);
			} else {
				returnList = this.queryForList(
						"pa.lowadjust.getPaLowAdjustFeiCuList", obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 最低工资调整数-非促销员
	 * 
	 * @param List
	 * @return
	 */
	public int getFeiCuLowSalaryListCnt(Object obj) {
		int returnInt = 0;
		try {
			// returnInt =
			// NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.lowadjust.getPaLowAdjustCnt",
			// obj)), Integer.class) ;
			returnInt = NumberUtils.parseNumber(ObjectUtils
					.toString(this.queryForObject(
							"pa.lowadjust.getPaLowAdjustFeiCuCnt", obj)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}

	@Override
	public List getFeiCuLowSalaryForTAList(Object obj, int currentPage,
			int pageSize) {
		List returnList = new ArrayList();
		try {
			// if (currentPage > -1 && pageSize > -1){
			// returnList = this.queryForList("pa.lowadjust.getPaLowAdjustList",
			// obj, currentPage, pageSize);
			// }else{
			// returnList = this.queryForList("pa.lowadjust.getPaLowAdjustList",
			// obj);
			// }
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"pa.lowadjust.getPaLowAdjustFeiCuForTAList", obj,
						currentPage, pageSize);
			} else {
				returnList = this.queryForList(
						"pa.lowadjust.getPaLowAdjustFeiCuForTAList", obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@Override
	public List getFeiCuLowSalaryForTAList(Object obj) {
		List returnList = new ArrayList();
		returnList = this.getFeiCuLowSalaryForTAList(obj, -1, -1);
		return returnList;
	}

	@Override
	public List getLowSalaryForTAList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			// if (currentPage > -1 && pageSize > -1){
			// returnList = this.queryForList("pa.lowadjust.getPaLowAdjustList",
			// obj, currentPage, pageSize);
			// }else{
			// returnList = this.queryForList("pa.lowadjust.getPaLowAdjustList",
			// obj);
			// }
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"pa.lowadjust.getPaLowAdjustCuForTAList", obj,
						currentPage, pageSize);
			} else {
				returnList = this.queryForList(
						"pa.lowadjust.getPaLowAdjustCuForTAList", obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@Override
	public List getLowSalaryForTAList(Object obj) {
		List returnList = new ArrayList();
		returnList = this.getLowSalaryForTAList(obj, -1, -1);
		return returnList;
	}
	/**
	 * 最低工资调整列表-非促销员
	 * 
	 * @param List
	 * @return
	 */
	@Override
	public List getFeiCuLowSalaryForTRList(Object obj) {
		List returnList = new ArrayList();
		returnList = this.getFeiCuLowSalaryList(obj, -1, -1);
		return returnList;
	}

	/**
	 * 最低工资调整列表-TR
	 * 
	 * @param List
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getFeiCuLowSalaryForTRList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			// if (currentPage > -1 && pageSize > -1){
			// returnList = this.queryForList("pa.lowadjust.getPaLowAdjustList",
			// obj, currentPage, pageSize);
			// }else{
			// returnList = this.queryForList("pa.lowadjust.getPaLowAdjustList",
			// obj);
			// }
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"pa.lowadjust.getPaLowAdjustFeiCuTRList", obj,
						currentPage, pageSize);
			} else {
				returnList = this.queryForList(
						"pa.lowadjust.getPaLowAdjustFeiCuTRList", obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	@Override
	public int getFeiCuLowSalaryListTRCnt(Object obj) {
		int returnInt = 0;
		try {
			// returnInt =
			// NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("pa.lowadjust.getPaLowAdjustCnt",
			// obj)), Integer.class) ;
			returnInt = NumberUtils.parseNumber(ObjectUtils
					.toString(this.queryForObject(
							"pa.lowadjust.getFeiCuLowSalaryListTRCnt", obj)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}
}
