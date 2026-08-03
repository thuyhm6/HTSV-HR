package com.ait.ar.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.ar.dao.ArEatCardAssociateDao;
import com.ait.web.util.SqlMapClientSupport;
@Repository
public class ArEatCardAssociateDaoImpl extends SqlMapClientSupport implements
		ArEatCardAssociateDao {
	/**
	 * 查看卡号数量(get CardAssociate Cnt)
	 * @param Object
	 * @return int
	 * @throws
	 */
	public int getEatCardAssociateCnt(Object object) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("ar.cardAssociate.getEatCardAssociateCnt",
							object)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnInt;
	}
	/**
	 * 取得所有考勤员列表(get AttendanceKeeper List)
	 * @param Object
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getEatCardAssociateList(Object object) {
		List returnList = new ArrayList();

		returnList = this.getEatCardAssociateList(object, -1, -1);

		return returnList;
	}

	/**
	 * 取得所有考勤员列表(get AttendanceKeeper List)分页显示
	 * @param Object
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getEatCardAssociateList(Object object, int currentPage,
			int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList(
						"ar.cardAssociate.getEatCardAssociateList", object,
						currentPage, pageSize);
			} else {
				returnList = this.queryForList(
						"ar.cardAssociate.getEatCardAssociateList", object);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return returnList;
	}
	/**
	 * 修改保存(update CardAssociate Info)
	 * @param Object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void updateEatCardAssociateInfo(Object object) throws Exception {
		
		this.deleteForList("ar.cardAssociate.deleteEatCardAssociateInfo", (List)object) ;
		
		this.insertForList("ar.cardAssociate.insertEatCardAssociateInfo", (List)object) ;
	}
}
