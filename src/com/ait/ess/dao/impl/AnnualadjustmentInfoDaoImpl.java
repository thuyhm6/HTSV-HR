package com.ait.ess.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.affirm.service.AffirmInfoToLGEPSer;
import com.ait.ess.dao.AnnualadjustmentInfoDao;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.SqlMapClientSupport;
import com.ait.web.util.StringUtil;

@Repository
public class AnnualadjustmentInfoDaoImpl extends SqlMapClientSupport implements AnnualadjustmentInfoDao{
	@Autowired
	private AffirmInfoToLGEPSer affirmInfoToLGEPSer;
	//考勤异常决裁no
	private static String APPLY_TYPE_NO = "218197";
	
	//考勤异常名称
	private static String APPLY_TYPE_NAME = "考勤异常";
	/**
	 * 查询年假福利年假天数(rest annual leave)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public List retrieveNianjiaFuli(Object obj) throws Exception {
		List object2 = this.queryForList("ess.Annualadjustment.retrieveNianjiaFuli", obj);
		if(object2 ==null){
			object2 = null;
		}
		return object2;
	}
	
	
	
	/**
	 * 添加年假调休信息
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void addAnnualadjustmentInBatch(List list) throws Exception {
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				LinkedHashMap map = (LinkedHashMap) list.get(i);
				LinkedHashMap tempMap = new LinkedHashMap();
				int AnnualadjustmentApplySeq = getAnnualadjustmentApplySeq();
				
				if (map != null && map.get("PARAM_MAP") != null) {
					LinkedHashMap obj = (LinkedHashMap) map.get("PARAM_MAP");
					obj.put("APPLY_NO_SEQ", AnnualadjustmentApplySeq);
					this.insert("ess.Annualadjustment.insertArAnnualadjustmentApply", obj);
					tempMap.put("CREATED_BY", ((LinkedHashMap) obj).get("CREATED_BY"));
					tempMap.put("APPLY_TYPE_NO", ((LinkedHashMap) obj).get("APPLY_TYPE_NO"));
					tempMap.put("APPLY_NO_SEQ", AnnualadjustmentApplySeq);

					//保存附件
					if (obj.get("FILE_NAME") != null && !"".equals(StringUtil.checkNull(obj.get("FILE_NAME")))) {
						String[] fileName = StringUtil.checkNull(obj.get("FILE_NAME")).split(";");
						String[] fileUrl = StringUtil.checkNull(obj.get("FILE_URL")).split(";");
						if (fileUrl != null && fileUrl.length > 0) {
							for (int j=0;j<fileUrl.length ;j++) {
								LinkedHashMap fileMap = new LinkedHashMap();
								fileMap.put("fileName", fileName[j]);
								fileMap.put("fileUrl", "/resources/temp/apply/applyleave/" + obj.get("PERSON_ID") + "/" + fileUrl[j]);
								fileMap.put("APPLY_NO", AnnualadjustmentApplySeq);
								fileMap.put("APPLY_TYPE", "216691");
								fileMap.put("CREATED_BY", obj.get("CREATED_BY"));
								this.insert("ess.infoApplyLeave.insertEssFile",fileMap);
							}
						}
					}
				}

				if (map != null && map.get("DISTINCT_LIST") != null) {
					List<LinkedHashMap> aList = (List) map.get("DISTINCT_LIST");
					if (aList != null && aList.size() > 0) {
						for (LinkedHashMap parmers : aList) {
							tempMap.put("AffirmLevel", parmers.get("AFFIRM_LEVEL"));
							tempMap.put("AffirmorId", parmers.get("AFFIRMOR_ID"));
							this.insert("ess.Annualadjustment.insertApplyReviewer",tempMap);
						}
					}
				}
				LinkedHashMap epmap = (LinkedHashMap)map.get("PARAM_MAP");
				this.sendAnnuToLGEPInsert(epmap);
			}
		}
	}
	
/**
 * 提交后发送LGEP
 * @param eventId
 */
private void sendAnnuToLGEPInsert(LinkedHashMap paramMap){
		LinkedHashMap lgepMap = new LinkedHashMap();
		lgepMap.put("APPLY_TYPE", "216691");
		lgepMap.put("APPLY_NO", paramMap.get("APPLY_NO_SEQ"));
		lgepMap.put("APPLY_TYPE_NAME", "年假调整");
		lgepMap.put("APPLY_TITLE", "年假调整");
		lgepMap.put("APPLY_EMPID", paramMap.get("PERSON_ID"));
		lgepMap.put("ARI_URL", "http://{serverIp}/LGEP/affirm/viewAnnuAffirm?LGEP=LGEP&LANGUAGE=zh&APPLY_TYPE_NO=216691&personId=123&APPLY_NO=" + paramMap.get("APPLY_NO_SEQ"));
		lgepMap.put("ABY_URL", "http://{serverIp}/LGEP/affirm/viewAnnuAffirm?LGEP=LGEP&LANGUAGE=zh&APPLY_TYPE_NO=216691&personId=" + paramMap.get("CURRENT_AFFIRM_ID") + "&APPLY_NO=" + paramMap.get("APPLY_NO_SEQ"));
		lgepMap.put("AFFIRM_LEVEL", "1");
		lgepMap.put("PRE_AFFIRM_EMPID", paramMap.get("CURRENT_AFFIRM_ID"));
		lgepMap.put("CURRENT_AFFIRM_ID", paramMap.get("CURRENT_AFFIRM_ID"));
		this.affirmInfoToLGEPSer.crateAffirm(lgepMap);
}
	
	
	/**
	 * 获得信息申请序列(get information apply sequences)
	 * 
	 * @param object
	 * @return
	 */
	private int getAnnualadjustmentApplySeq() throws Exception {
		try {
			return NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("ess.macRecordApply.getArMacRecordApplySeq")),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List getAnnualadjustmentAffirmList(Object object, int currentPage,int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("ess.Annualadjustment.getArAnnualadjustmentList", object,currentPage, pageSize);
			} else {
				returnList = this.queryForList("ess.Annualadjustment.getArAnnualadjustmentList", object);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	
	/**
	 * 批量删除申请(batch delete overtime apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int delAnnuApplyInBatch(List list) throws Exception {
		try {
			if(list!=null && list.size()>0){
				for(int i=0;i<list.size();i++){
					LinkedHashMap map = (LinkedHashMap)list.get(i);
					//删除发送LGEP
					affirmInfoToLGEPSer.deleteAffirm(map);
				}
			}
			this.deleteForList("ess.Annualadjustment.delAnnApplyByApplyNo", list);
			this.deleteForList("ess.Annualadjustment.delAffirmByApplyNo", list);
			
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
		return 1;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List getAnnualadjustmentAffirmapplyList(Object object, int currentPage,int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("ess.Annualadjustment.getArAnnualadjustmentapplyList", object,currentPage, pageSize);
			} else {
				returnList = this.queryForList("ess.Annualadjustment.getArAnnualadjustmentapplyList", object);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List getAnnualadjustmentAffirmList(Object object) {
		List returnList = new ArrayList();
		returnList = this.getAnnualadjustmentAffirmList(object, -1, -1);
		return returnList;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List getAnnualadjustmentAffirmapplyList(Object object) {
		List returnList = new ArrayList();
		returnList = this.getAnnualadjustmentAffirmapplyList(object, -1, -1);
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int getAnnualadjustmentAffirmListCnt(Object object) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("ess.Annualadjustment.getArAnnualadjustmentListCnt",object)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public int getCwaAbnormalApplyInfoListCnt(Object object) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("ess.Annualadjustment.getCwaAbnormalApplyInfoListCnt",object)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public int getviewCwaAbnormalAffirmListCnt(Object object) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("ess.Annualadjustment.getviewCwaAbnormalAffirmListCnt",object)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public int getAnnualadjustmentAffirmapplyListCnt(Object object) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("ess.Annualadjustment.getArAnnualadjustmentapplyListCnt",object)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}
	
	
	
	/**
	 * 添加考勤异常信息
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void addBatchCwaAbnormalApplyBatch(List list) throws Exception {
		
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				int AnnualadjustmentApplySeq = getAnnualadjustmentApplySeq();
			    LinkedHashMap map = (LinkedHashMap) list.get(i);
                LinkedHashMap tempMap = new LinkedHashMap();
				
                try {
					if (map != null && map.get("PARAM_MAP") != null) {
						LinkedHashMap obj = (LinkedHashMap) map.get("PARAM_MAP");
						obj.put("ID", AnnualadjustmentApplySeq);
						//删除旧数据
	                	LinkedHashMap delMap = new LinkedHashMap();
	                	delMap.put("APPLY_NO", obj.get("PK_NO").toString());
	                	delMap.put("interCpnyID", obj.get("interCpnyID").toString());
	                	delMap.put("APPLY_TYPE", "218197");
	                	this.update("ess.Annualadjustment.delArDetail",delMap);
	        			//修改决裁表
	    				LinkedHashMap personMap = (LinkedHashMap) map.get("personMap");
	    				tempMap.put("LAST_NAME", "Abnormal Attendance Application(" + personMap.get("LOCAL_NAME") + ")[Date：" + ((String) obj.get("AR_DATE_STR")).replaceAll("/",".") + "]"); //考勤异常申请
	    				tempMap.put("APPLY_PERSON_INFO", personMap.get("LOCAL_NAME") + "/" + StringUtil.checkNull(personMap.get("POST_GRADE_NAME")) + "/" + StringUtil.checkNull(personMap.get("DEPTNAME")));
	        			//插入新数据
	    				this.insert("ess.Annualadjustment.insertCwaAbnormalApplyNotConfirm", obj);
						tempMap.put("CREATED_BY", obj.get("PERSON_ID"));
						tempMap.put("APPLY_TYPE_NO",  obj.get("APPLY_TYPE_NO"));
						tempMap.put("APPLY_NO_SEQ", obj.get("PK_NO").toString());
						tempMap.put("adminID", obj.get("adminID"));
						tempMap.put("adminIP", obj.get("adminIP"));
						tempMap.put("APPLY_TYPE_CODE", obj.get("ITEM_NO"));
						tempMap.put("APPLY_AFFIRM_FLAG", obj.get("APPLY_AFFIRM_FLAG"));
						tempMap.put("APPLY_FLAG", obj.get("APPLY_FLAG"));
						//先插入申请人
						tempMap.put("AFFIRM_LEVEL", "0");
						tempMap.put("AFFIRMOR_ID", obj.get("PERSON_ID"));
						tempMap.put("AFFIRM_TYPE", "4");
						this.insert("ess.infoApply.addSyAffirmInfo", tempMap); 

						if (map != null && map.get("DISTINCT_LIST") != null) {
							List<LinkedHashMap> aList = (List) map.get("DISTINCT_LIST");
							if (aList != null && aList.size() > 0) {
								for (LinkedHashMap parmers : aList) {
									tempMap.put("AFFIRM_LEVEL", parmers.get("AFFIRM_LEVEL"));
									tempMap.put("AFFIRMOR_ID", parmers.get("AFFIRMOR_ID"));
									tempMap.put("AFFIRM_TYPE", "1");
									this.insert("ess.infoApply.addSyAffirmInfo",tempMap);
								}
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	/**
	 * 批量删除考勤异常申请信息
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int delCwaAbnormalApplyInfo(List list) throws Exception {
		try {
			if(list!=null && list.size()>0){
				for(int i=0;i<list.size();i++){
					LinkedHashMap map = (LinkedHashMap)list.get(i);
					//删除发送LGEP
					affirmInfoToLGEPSer.deleteAffirm(map);
				}
			}
			this.deleteForList("ess.Annualadjustment.delCwaAbnormalApplyInfo", list);
			this.deleteForList("ess.Annualadjustment.delAffirmByApplyNo", list);
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
		return 1;
	}
	
	
	/**
	 * 提交后发送LGEP
	 * @param eventId
	 */
	private void sendToLGEPInsert(LinkedHashMap paramMap){
			LinkedHashMap lgepMap = new LinkedHashMap();
			lgepMap.put("APPLY_TYPE", APPLY_TYPE_NO);
			lgepMap.put("APPLY_NO", paramMap.get("PK_NO").toString());
			lgepMap.put("APPLY_TYPE_NAME", APPLY_TYPE_NAME);
			lgepMap.put("APPLY_TITLE", APPLY_TYPE_NAME);
			lgepMap.put("APPLY_EMPID", paramMap.get("PERSON_ID"));
			lgepMap.put("ARI_URL", "http://{serverIp}/LGEP/affirm/viewCwaAbnormalAffirm?LGEP=LGEP&LANGUAGE=zh&APPLY_TYPE_NO="+APPLY_TYPE_NO+"&personId=123&APPLY_NO=" + paramMap.get("PK_NO"));
			lgepMap.put("ABY_URL", "http://{serverIp}/LGEP/affirm/viewCwaAbnormalAffirm?LGEP=LGEP&LANGUAGE=zh&APPLY_TYPE_NO="+APPLY_TYPE_NO+"&personId=" + paramMap.get("CURRENT_AFFIRM_ID") + "&APPLY_NO=" + paramMap.get("PK_NO"));
			lgepMap.put("AFFIRM_LEVEL", "1");
			lgepMap.put("PRE_AFFIRM_EMPID", paramMap.get("CURRENT_AFFIRM_ID"));
			lgepMap.put("CURRENT_AFFIRM_ID", paramMap.get("CURRENT_AFFIRM_ID"));
			this.affirmInfoToLGEPSer.crateAffirm(lgepMap);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List getCwaAbnormalApplyInfoList(Object object, int currentPage,int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("ess.Annualadjustment.getCwaAbnormalApplyInfoList", object,currentPage, pageSize);
			} else {
				returnList = this.queryForList("ess.Annualadjustment.getCwaAbnormalApplyInfoList", object);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List getCwaAbnormalApplyInfoList(Object object) {
		List returnList = new ArrayList();
		returnList = this.getCwaAbnormalApplyInfoList(object, -1, -1);
		return returnList;
	}
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List getviewCwaAbnormalAffirmList(Object obj, int currentPage,
			int pageSize) throws Exception {
		List returnList = new ArrayList();
		if (currentPage > -1 && pageSize > -1) {
			returnList = this.queryForList(
					"ess.Annualadjustment.getviewCwaAbnormalAffirmList", obj,
					currentPage, pageSize);
		} else {
			returnList = this.queryForList(
					"ess.Annualadjustment.getviewCwaAbnormalAffirmList", obj);
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List getviewCwaAbnormalAffirmList(Object object) throws Exception{
		List returnList = new ArrayList();
		returnList = this.getviewCwaAbnormalAffirmList(object, -1, -1);
		return returnList;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List getCwaAbnormalAffirmByApplyNOList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ess.Annualadjustment.getCwaApplyorByApplyNoList",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	
	@Override
	public List getCwaCheckList(Object obj, int currentPage, int pageSize)
			throws Exception {
		List returnList = new ArrayList();
		if (currentPage > -1 && pageSize > -1) {
			returnList = this.queryForList("ess.Annualadjustment.getCwaCheckList", obj, currentPage,pageSize);
		} else {
			returnList = this.queryForList("ess.Annualadjustment.getCwaCheckList", obj);
		}
		return returnList;
	}
	@Override
	public List getCwaCheckList(Object obj) throws Exception {
		return this.getCwaCheckList(obj, -1, -1);
	}
	

	@Override
	public int getCwaCheckListCnt(Object object) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("ess.Annualadjustment.getCwaCheckListCnt",object)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}
	
	@Override
	public List getAnnuCheckList(Object obj, int currentPage, int pageSize)
			throws Exception {
		List returnList = new ArrayList();
		if (currentPage > -1 && pageSize > -1) {
			returnList = this.queryForList("ess.Annualadjustment.getArAnnuapplyCheckList", obj, currentPage,pageSize);
		} else {
			returnList = this.queryForList("ess.Annualadjustment.getArAnnuapplyCheckList", obj);
		}
		return returnList;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public int getAnnuCheckListCnt(Object object) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("ess.Annualadjustment.getAnnuCheckListCnt",object)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}
	
	
	@Override
	public List getAnnuCheckList(Object obj) throws Exception {
		return this.getAnnuCheckList(obj, -1, -1);
	}
	

	@Override
	public List getAnnuapplyListByApplyno(Object obj, int currentPage, int pageSize)
			throws Exception {
		List returnList = new ArrayList();
		if (currentPage > -1 && pageSize > -1) {
			returnList = this.queryForList("ess.Annualadjustment.getAnnuapplyListByApplyno", obj, currentPage,pageSize);
		} else {
			returnList = this.queryForList("ess.Annualadjustment.getAnnuapplyListByApplyno", obj);
		}
		return returnList;
	}
	
	
	
	
	@Override
	public List getAnnuapplyListByApplyno(Object obj) throws Exception {
		return this.getAnnuapplyListByApplyno(obj, -1, -1);
	}
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public int delAnnApplyInBatch(List list) throws Exception {
		try {
			this.deleteForList("ess.Annualadjustment.delAnnApplyByApplyNo", list);
			this.deleteForList("ess.Annualadjustment.delAffirmByApplyNo", list);
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
		return 1;
	}
	
	/**
	 * 根据加班申请NO决裁信息查询(search ot info list)
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getAffirmorByApplyNoList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ess.Annualadjustment.getAffirmorByApplyNoList",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	/**
	 * 加班申请check信息查询(search ot info list)
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getCheckorByApplyNoList(Object obj) {
		List returnList = new ArrayList();
		try {
			returnList = this.queryForList("ess.Annualadjustment.getCheckorByApplyNoList",obj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	/**
	 * 获取年假调整导入信息
	 * 
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getEssArVacTempList(Object object) {
		List returnList = new ArrayList();
		returnList = this.getEssArVacTempList(object, -1, -1);
		return returnList;
	}
	
	/**
	 * 获取年假调整导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getEssArVacTempList(Object object, int currentPage, int pageSize){
		List returnList = new ArrayList() ;
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("ess.Annualadjustment.getEssArVacTempList", object, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("ess.Annualadjustment.getEssArVacTempList", object);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 获取年假调整导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getEssArVacTempCnt(Object object){
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ess.Annualadjustment.getEssArVacTempCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	 * 获取出错的年假调整导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getEssArVacTempErrorCnt(Object object){
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ess.Annualadjustment.getEssArVacTempErrorCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	 * 获取年假调整批量信息
	 * 
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getEssArVacList(Object object) {
		List returnList = new ArrayList();
		returnList = this.getEssArVacList(object, -1, -1);
		return returnList;
	}
	
	/**
	 * 获取年假调整批量信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getEssArVacList(Object object, int currentPage, int pageSize){
		List returnList = new ArrayList() ;
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("ess.Annualadjustment.getEssArVacList", object, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("ess.Annualadjustment.getEssArVacList", object);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 获取年假调整批量数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getEssArVacCnt(Object object){
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ess.Annualadjustment.getEssArVacCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	 * 删除未审核
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean delArVacRecordApplyInfo(List list) throws Exception {
		Boolean falg = true;
		for(int i=0;i<list.size();i++){
			LinkedHashMap map = (LinkedHashMap)list.get(i);
			map.put("APPLY_TYPE", "216691");
			//删除发送LGEP
			affirmInfoToLGEPSer.deleteAffirm(map);
		}
		this.deleteForList("ess.Annualadjustment.delArVacRecordAffirmRelation", list);
		this.deleteForList("ess.Annualadjustment.delArVacRecordApplyByRecordNo", list);
		this.deleteForList("ess.Annualadjustment.delArVacRecordApplyByBatchRecordNo", list);
		return falg;
	}
	
	/**
	 * 批量提交申请
	 * 
	 * @param obj
	 * @return
	 */
	public int submitArVacApplyInBatch(List list) throws Exception {
		try {
			this.updateForList("ess.Annualadjustment.submitTempArVacApply", list);
			this.sendToLGEPInsert(list);
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
		return 1;
	}
	
	/**
	 * 提交后发送LGEP
	 * @param eventId
	 */
	private void sendToLGEPInsert(List list){
		for(int i=0;i<list.size();i++){
			LinkedHashMap paramMap = (LinkedHashMap)list.get(i);
			List LeaveList = this.getArVacRecordAffirmViewListBySingle(paramMap);
			LinkedHashMap lgepMap = (LinkedHashMap)LeaveList.get(0);
			lgepMap.put("APPLY_TYPE", "216691");
			lgepMap.put("APPLY_NO", lgepMap.get("APPLY_NO"));
			lgepMap.put("APPLY_TYPE_NAME", "年假批量调整");
			lgepMap.put("APPLY_TITLE", "年假批量调整");
			lgepMap.put("APPLY_EMPID", lgepMap.get("PERSON_ID"));
			lgepMap.put("ARI_URL", "http://{serverIp}/LGEP/affirm/viewAnnuAffirm?LGEP=LGEP&LANGUAGE=zh&APPLY_TYPE_NO=216691&personId=123&APPLY_NO=" + lgepMap.get("APPLY_NO"));
			lgepMap.put("ABY_URL", "http://{serverIp}/LGEP/affirm/viewAnnuAffirm?LGEP=LGEP&LANGUAGE=zh&APPLY_TYPE_NO=216691&personId=" + lgepMap.get("CURRENT_AFFIRM_ID") + "&APPLY_NO=" + lgepMap.get("APPLY_NO"));
			lgepMap.put("AFFIRM_LEVEL", "1");
			lgepMap.put("PRE_AFFIRM_EMPID", lgepMap.get("CURRENT_AFFIRM_ID"));
			lgepMap.put("CURRENT_AFFIRM_ID", lgepMap.get("CURRENT_AFFIRM_ID"));
			this.affirmInfoToLGEPSer.crateAffirm(lgepMap);
		}
	}
	
	/**
	 * 决裁情况用
	 * 
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getArVacRecordAffirmViewListBySingle(Object object) {
		List returnList = new ArrayList();
		try {
				returnList = this.queryForList("ess.Annualadjustment.getArVacRecordAffirmViewListBySingle", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
}
