package com.ait.hrm.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.hrm.dao.InterviewScheduleDao;
import com.ait.web.util.SqlMapClientSupport;
import com.ait.web.util.StringUtil;

@Repository
public class InterviewScheduleDaoImpl extends SqlMapClientSupport implements InterviewScheduleDao {

	@SuppressWarnings("unchecked")
	@Override
	public List getInterviewScheduleList(LinkedHashMap map) throws Exception {
		// TODO Auto-generated method stub
		
		List returnList = new ArrayList();
		returnList = this.queryForList("hrm.interViewSchedul.getInterviewScheduleList",map);
		return returnList;
	}

	/**
	 * 获得简历安排序列(get information interView sequences)
	 * 
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unused")
	private int getRecInterViewSeq() throws Exception {
		try {
			return NumberUtils.parseNumber(ObjectUtils.toString(StringUtil.checknvl(this
					.queryForObject("hrm.interViewSchedul.getRecInterViewSeq"),0)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void addRecAffirmInfo(LinkedHashMap map) throws Exception {
		// TODO Auto-generated method stub
		int RecInterViewSeq = getRecInterViewSeq();
		if (map!= null) {
			map.put("REC_INTERVIEW_NO", RecInterViewSeq);
			//插入面试安排表
			this.insert("hrm.interViewSchedul.insertRecInterView", map);
			if (map != null && map.get("affirmList") != null) {
				List<LinkedHashMap> aList = (List) map.get("affirmList");
				if (aList != null && aList.size() > 0) {
					for (LinkedHashMap parmers : aList) {
						map.put("AFFIRM_LEVEL", parmers.get("AFFIRM_LEVEL"));
						map.put("AFFIRMOR_ID", parmers.get("AFFIRMOR_ID"));
						//插入面试审批表
						this.insert("hrm.interViewSchedul.addRecAffirmInfo",map);
					}
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void updateRecPageFlag(LinkedHashMap paramMap) throws Exception {
		// TODO Auto-generated method stub
		this.update("hrm.interViewSchedul.updateRecPageFlag", paramMap);
	}	
	
		
}
