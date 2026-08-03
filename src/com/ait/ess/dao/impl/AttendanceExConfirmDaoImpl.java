package com.ait.ess.dao.impl;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;

import com.ait.ess.dao.AttendanceExConfirmDao;
import com.ait.web.util.SqlMapClientSupport;
@Repository
public class AttendanceExConfirmDaoImpl extends SqlMapClientSupport  implements AttendanceExConfirmDao{
	@SuppressWarnings("unchecked")
	@Override
	public List viewAttendanceExList(Object object, String target) throws Exception{
		List result = null;
		try {
			result = this.queryForList("ess.attendanceConfirm." + target, object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public int addAttendanceExJsonPro(Object object, String target)
			throws Exception {
		List list = (List)object;
		if(list != null && list.size() > 0){
			for(int i=0;i<list.size();i++){
				Map map = (Map)list.get(i);
				map.put("message", "") ;
				this.insert("ess.attendanceConfirm." + target, map) ;
			}
		}
			return 1;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List viewPOtApplyInfoConfirmList(Object object, String target) throws Exception{
		List result = null;
		try {
			result = this.queryForList("ess.attendanceConfirm." + target, object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public int addPOtApplyInfoJsonPro(Object object, String target)
			throws Exception {
		List list = (List)object;
		if(list != null && list.size() > 0){
			for(int i=0;i<list.size();i++){
				Map map = (Map)list.get(i);
				map.put("message", "") ;
				this.insert("ess.attendanceConfirm." + target, map) ;
			}
		}
			return 1;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List viewLeaveConfirmList(Object object, String target) throws Exception{
		List result = null;
		try {
			result = this.queryForList("ess.attendanceConfirm." + target, object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public int addLeaveJsonPro(Object object, String target) throws Exception {
		List list = (List)object;
		if(list != null && list.size() > 0){
			for(int i=0;i<list.size();i++){
				Map map = (Map)list.get(i);
				map.put("message", "") ;
				this.insert("ess.attendanceConfirm." + target, map) ;
			}
		}
			return 1;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int sickLeaveProofConfirm(Object object, String target) throws Exception {
		List list = (List)object;
		if(list != null && list.size() > 0){
			for(int i=0;i<list.size();i++){
				Map map = (Map)list.get(i);
				this.update("ess.attendanceConfirm." + target, map) ;
			}
		}
		return 1;
	}
}
