package com.ait.ess.dao.impl;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.NumberUtils;

import com.ait.affirm.service.AffirmInfoToLGEPSer;
import com.ait.ar.dao.ArDetailCalulateDao;
import com.ait.ess.dao.ArMacRecordApplyDao;
import com.ait.pa.dao.tempsale.PaTempSalesDAO;
import com.ait.web.util.CommonException;
import com.ait.web.util.DateUtil;
import com.ait.web.util.MailManager;
import com.ait.web.util.SqlMapClientSupport;
import com.ait.web.util.StringUtil;

/**
 * 信息申请(information apply)
 * 
 * @Copyright: LDCC
 * @Company: LDCC
 * @fileName: InfoApplyDaoImpl.java
 * @Description:
 * @Create date: Feb 3, 2012 10:39:12 AM
 * @Create by: zhoulei(zhoulei@ait.net.cn)
 * @Update Date: Feb 3, 2012 10:39:12 AM
 * @Update by: zhoulei(zhoulei@ait.net.cn)
 * @version 5.1
 */
@Repository
public class ArMacRecordApplyDaoImpl extends SqlMapClientSupport implements
	ArMacRecordApplyDao {
	
	@Autowired
	private AffirmInfoToLGEPSer affirmInfoToLGEPSer;

	@Autowired
	PaTempSalesDAO paTempSalesDAO;
	@Autowired
	MailManager mailManger;
	//漏刷卡决裁no
	private static String APPLY_TYPE_NO = "218294";
	
	//漏刷卡名称
	private static String APPLY_TYPE_NAME = "漏刷卡";
	
	@Autowired
	private ArDetailCalulateDao arDetailCalulateDao;
	/**
	 * 批量添加漏刷卡申请(add overtime apply)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void addArMacRecordApplyInBatch(List list) throws Exception {
		if (list.size() > 0) {
			
			for (int i = 0; i < list.size(); i++) {
				try {
					LinkedHashMap map = (LinkedHashMap) list.get(i);
					LinkedHashMap tempMap = new LinkedHashMap();
					int arMacRecordApplySeq = getArMacRecordApplySeq();

					if (map != null && map.get("PARAM_MAP") != null) {
						LinkedHashMap obj = (LinkedHashMap) map.get("PARAM_MAP");
						obj.put("RECORD_NO_SEQ", arMacRecordApplySeq);
						this.insert("ess.macRecordApply.insertArMacRecordApply", obj);
						tempMap.put("CREATED_BY", ((LinkedHashMap) obj).get("CREATED_BY"));
						tempMap.put("APPLY_TYPE_NO", ((LinkedHashMap) obj).get("APPLY_TYPE_NO"));
						tempMap.put("APPLY_NO_SEQ", arMacRecordApplySeq);
						

						//保存附件
						if (obj.get("FILE_NAME") != null && !"".equals(StringUtil.checkNull(obj.get("FILE_NAME")))) {
							String[] fileName = StringUtil.checkNull(obj.get("FILE_NAME")).split(";");
							String[] fileUrl = StringUtil.checkNull(obj.get("FILE_URL")).split(";");
							if (fileUrl != null && fileUrl.length > 0) {
								for (int j=0;j<fileUrl.length ;j++) {
									LinkedHashMap fileMap = new LinkedHashMap();
									fileMap.put("fileName", fileName[j]);
									fileMap.put("fileUrl", "/resources/temp/apply/applyleave/" + obj.get("PERSON_ID") + "/" + fileUrl[j]);
									fileMap.put("APPLY_NO", arMacRecordApplySeq);
									fileMap.put("APPLY_TYPE", APPLY_TYPE_NO);
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
								this.insert("ess.macRecordApply.insertApplyReviewer",tempMap);
							}
						}
					}
					LinkedHashMap epmap = (LinkedHashMap)map.get("PARAM_MAP");
					this.sendToLGEPInsert(epmap);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 获得信息申请序列(get information apply sequences)
	 * 
	 * @param object
	 * @return
	 */
	private int getArMacRecordApplySeq() throws Exception {
		try {
			return NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("ess.macRecordApply.getArMacRecordApplySeq")),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 查询已申请的刷卡申请数量(get apply data cnt)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getExistArRecordApplyCnt(Object object) throws Exception {
		try {
			return NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("ess.macRecordApply.getExistArRecordApplyCnt", object)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 查询已申请的刷卡申请数量(get apply data cnt)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public int isExistArRecordApplyCnt(Object object) throws Exception {
		try {
			return NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("ess.macRecordApply.isExistArRecordApplyCnt", object)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 查看是否有外出、出差、培训申请记录
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public int isExistArRecordApplyLeaveCnt(Object object) throws Exception {
		try {
			return NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("ess.macRecordApply.isExistArRecordApplyLeaveCnt", object)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	/**
	 * 信息决裁用，in/out进出门刷卡数据信息申请数据查询(view ar mac record apply info)，分页查询
	 * 
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getArMacRecordAffirmList(Object object, int currentPage,int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("ess.macRecordApply.getArMacRecordAffirmList", object,currentPage, pageSize);
			} else {
				returnList = this.queryForList("ess.macRecordApply.getArMacRecordAffirmList", object);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 信息决裁用，in/out进出门刷卡数据信息申请数据查询(view ar mac record apply info)，不分页
	 * 
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getArMacRecordAffirmList(Object object) {
		List returnList = new ArrayList();
		returnList = this.getArMacRecordAffirmList(object, -1, -1);
		return returnList;
	}
	
	/**
	 * 信息决裁用，in/out进出门刷卡数据信息申请数据个数(view ar mac record apply information count)
	 * 
	 * @param object
	 * @return
	 */
	@Override
	public int getArMacRecordAffirmListCnt(Object object) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("ess.macRecordApply.getArMacRecordAffirmListCnt",object)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}
	
	
	
	/**
	 * 决裁情况用，in/out进出门刷卡数据信息申请数据查询(view ar mac record apply info)，分页查询
	 * 
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getArMacRecordAffirmViewList(Object object, int currentPage,int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("ess.macRecordApply.getArMacRecordAffirmViewList", object,currentPage, pageSize);
			} else {
				returnList = this.queryForList("ess.macRecordApply.getArMacRecordAffirmViewList", object);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}

	/**
	 * 决裁情况用，in/out进出门刷卡数据信息申请数据查询(view ar mac record apply info)，不分页
	 * 
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getArMacRecordAffirmViewList(Object object) {
		List returnList = new ArrayList();
		returnList = this.getArMacRecordAffirmViewList(object, -1, -1);
		return returnList;
	}
	
	/**
	 * 决裁情况用，in/out进出门刷卡数据信息申请数据个数(view ar mac record apply information count)
	 * 
	 * @param object
	 * @return
	 */
	@Override
	public int getArMacRecordAffirmViewListCnt(Object object) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("ess.macRecordApply.getArMacRecordAffirmViewListCnt",object)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}
	
	
	
	
	
	/**
	 * 批量通过/否决：in/out进出门刷卡申请(batch pass and reject ar mac record apply)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int saveArMacRecordApplyAffirmInBatch(List list) throws Exception {
		for (int i = 0; i < list.size(); i++) {
			Map map = (Map) list.get(i);
			String flag = map.get("FLAG") != null ? map.get("FLAG").toString(): "0";
			this.update("ess.affirmApply.updateEssAffirmByAffirmNo", map);
			this.update("ess.macRecordApply.updateArMacRecordByApplyNo", map);
			// 如果是决裁流程的最后一步且为通过的时候执行下面的操作
			if ("1".equals(String.valueOf(flag))) {
				 
					LinkedHashMap arMacRecordApply = (LinkedHashMap) this.queryForObject("ess.macRecordApply.getArMacRecordApplyInfoByApplyNo",map);

					arMacRecordApply.put("APPLY_PERSON_ID", arMacRecordApply.get("PERSON_ID"));
					if (null != arMacRecordApply.get("BATCH_YN")
							&& "Y".equals(arMacRecordApply.get("BATCH_YN"))) {
						doBatchInsertArApplyResult(arMacRecordApply);
					} else {
						doInsertArApplyResult(arMacRecordApply);
					}
					map.put("APPLY_PERSON_ID", arMacRecordApply.get("PERSON_ID"));
			}
			this.sendToLGEP(map);
		}
		return 1;
	}

	/**
	 * 通过/否决：in/out进出门刷卡申请(pass and reject ar mac record apply)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int saveArMacRecordApplyAffirm(LinkedHashMap object) throws Exception {
		String flag = object.get("FLAG") != null ? object.get("FLAG").toString() : "0";
		this.update("ess.affirmLeaveApply.updateEssAffirmByAffirmNo", object);
		this.update("ess.macRecordApply.updateArMacRecordByApplyNo", object);

		//check信息修改为已check
		this.sendToLGEPCheckBatch(object);
		// 审批者决裁完后更新他添加的check信息为已check
		this.paTempSalesDAO.updateCheckFlagByEssAffirmNo(object);
		// 如果是决裁流程的最后一步通过而且也不需要人事确认
		if ("1".equals(flag)) {
				LinkedHashMap arMacRecordApply = (LinkedHashMap) this.queryForObject("ess.macRecordApply.getArMacRecordApplyInfoByApplyNo",object);
				arMacRecordApply.put("ADMIN_ID", object.get("ADMIN_ID"));

				object.put("APPLY_PERSON_ID", arMacRecordApply.get("PERSON_ID"));
				if (null != arMacRecordApply.get("BATCH_YN")
						&& "Y".equals(arMacRecordApply.get("BATCH_YN"))) {
					doBatchInsertArApplyResult(arMacRecordApply);
				} else {
					doInsertArApplyResult(arMacRecordApply);
				}
		}
		this.sendToLGEP(object);
		return 1;
	}

	/**
	 * 审批后发送LGEP
	 * @param eventId/**
	 * check：
	 *  APPLY_NO:申请的seq
	 *  APPLY_TYPE：申请类型（数字代码）
	 *	AFFIRM_LEVEL：ess_check_no
	 *	AFFIRM_FLAG：传1，表示通过
	 *	AFFIRM_EMPID：check人person_id
	 *	AAI_URL:check结果查看页面url
	 */
	private void sendToLGEPCheckBatch(LinkedHashMap paramMap){
		List checkList = this.paTempSalesDAO.getCheckListToLgep(paramMap);
		if(checkList != null && checkList.size() > 0){
			for(int i=0;i<checkList.size();i++){
				LinkedHashMap lgepMap = (LinkedHashMap)checkList.get(i);
				lgepMap.put("APPLY_TYPE", APPLY_TYPE_NO);
				lgepMap.put("APPLY_NO", paramMap.get("APPLY_NO"));
				lgepMap.put("AFFIRM_FLAG", '1');
				lgepMap.put("AFFIRM_LEVEL", lgepMap.get("ESS_CHECK_NO"));
				lgepMap.put("AFFIRM_EMPID", lgepMap.get("CHECKOR_ID"));
				lgepMap.put("AAI_URL", "http://{serverIp}/LGEP/affirm/RecordCheck?LGEP=LGEP&LANGUAGE=zh&personId=123&APPLY_NO=" + paramMap.get("APPLY_NO") + "&affirmOrCheck=2");
				this.affirmInfoToLGEPSer.check(lgepMap);
			}
		}
	}
	/**
	 * 判断是否为事后申请休假 overtime apply)
	 * 
	 * @param paramMap
	 * @return
	 */
	private boolean isAfterLeave(Map paramMap) throws Exception {
		Date date = new Date();
		SimpleDateFormat sb = new SimpleDateFormat("yyyy-MM-dd");

		String leaveApplyFrom = paramMap.get("APPLY_DATE") != null ? paramMap
				.get("APPLY_DATE").toString()
				: sb.format(date);
		String sysDateStr = sb.format(date);

		GregorianCalendar applyFrom = DateUtil
				.ParseGregorianCalendar(leaveApplyFrom);
		GregorianCalendar sysDate = DateUtil.ParseGregorianCalendar(sysDateStr);
		if (sysDate.after(applyFrom)) {
			return false;
		}
		return true;
	}
	
	/**
	 * 审批通过后计算日考勤
	 * 
	 * @param map
	 */
	private void detailCalculate(LinkedHashMap map) {
		map.put("from_date", map.get("AR_FROM_TIME"));
		map.put("to_date", map.get("AR_TO_TIME"));
		map.put("caltype", "emp");
		map.put("interCpnyID", map.get("CPNY_ID"));
		map.put("empid", map.get("PERSON_ID"));
		arDetailCalulateDao.detailCalculate(map);
	}
	
	/**
	 * 审批后发送LGEP
	 * @param eventId
	 */
	private void sendToLGEP(Map paramMap){
			LinkedHashMap lgepMap = new LinkedHashMap();
			lgepMap.put("APPLY_NO", paramMap.get("APPLY_NO"));
			lgepMap.put("APPLY_TYPE", APPLY_TYPE_NO);
			if("4".equals(paramMap.get("AFFIRM_READ_FLAG").toString())){
				lgepMap.put("AFFIRM_LEVEL", paramMap.get("NEXT_AFFIRM_LEVEL"));
				lgepMap.put("AFFIRM_FLAG", 1);
			}else{//决裁完成
				lgepMap.put("FINISH", "FINISH");
				lgepMap.put("AFFIRM_FLAG", paramMap.get("AFFIRM_READ_FLAG"));
				lgepMap.put("AFFIRM_LEVEL", paramMap.get("AFFIRM_LEVEL"));
				lgepMap.put("CREATED_BY", paramMap.get("APPLY_PERSON_ID"));
			}
			lgepMap.put("AFFIRM_EMPID", paramMap.get("CURRENT_AFFIRM_ID"));
			lgepMap.put("AAI_URL", "http://{serverIp}/LGEP/affirm/viewRecordAffirm?LGEP=LGEP&LANGUAGE=zh&APPLY_TYPE_NO="+APPLY_TYPE_NO+"&personId=123&APPLY_NO=" + paramMap.get("APPLY_NO").toString());
			lgepMap.put("AAF_URL", "http://{serverIp}/LGEP/affirm/viewRecordAffirm?LGEP=LGEP&LANGUAGE=zh&APPLY_TYPE_NO="+APPLY_TYPE_NO+"&personId=123&APPLY_NO=" + paramMap.get("APPLY_NO").toString());
			lgepMap.put("ABY_URL", "http://{serverIp}/LGEP/affirm/viewRecordAffirm?LGEP=LGEP&LANGUAGE=zh&APPLY_TYPE_NO="+APPLY_TYPE_NO+"&personId=" + paramMap.get("NEXT_AFFIRM_ID") + "&APPLY_NO=" + paramMap.get("APPLY_NO").toString());
			lgepMap.put("PRE_AFFIRM_EMPID", paramMap.get("NEXT_AFFIRM_ID"));
			lgepMap.put("CURRENT_AFFIRM_ID", paramMap.get("NEXT_AFFIRM_ID"));
			this.affirmInfoToLGEPSer.affirm(lgepMap);
	}
	
	
	/**
	 * 删除未审核：in/out进出门刷卡信息申请(delete ar mac record apply information)
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean delArMacRecordApplyInfo(List list) throws Exception {
		Boolean falg = true;
		for(int i=0;i<list.size();i++){
			LinkedHashMap map = (LinkedHashMap)list.get(i);
			map.put("APPLY_TYPE", APPLY_TYPE_NO);
			//删除发送LGEP
			affirmInfoToLGEPSer.deleteAffirm(map);
		}
		this.deleteForList("ess.macRecordApply.delArMacRecordAffirmRelation", list);
		this.deleteForList("ess.macRecordApply.delArMacRecordApplyByRecordNo", list);
		this.deleteForList("ess.macRecordApply.delArMacRecordApplyByBatchRecordNo", list);
		return falg;
	}
	
	/**
	 * 查询in/out进出门刷卡信息申请人员结果列表(search employee result list）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPersonList(Object obj) {
		List returnList = new ArrayList();
		returnList = this.getPersonList(obj, -1, -1);
		return returnList;
	}

	/**
	 * 查询in/out进出门刷卡信息申请人员结果列表-判断是否分页,(search employee result list and judge if pagenation）
	 * 
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getPersonList(Object obj, int currentPage, int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("ess.macRecordApply.getMacRecordPersonList",
						obj, currentPage, pageSize);
			} else {
				returnList = this.queryForList("ess.macRecordApply.getMacRecordPersonList",
						obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	
	/**
	 * 批量in/out进出门刷卡信息申请的人员列表总数(get ar mac record employee list count)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getPersonListCnt(Object obj) throws Exception {
		return NumberUtils.parseNumber(ObjectUtils.toString(this
				.queryForObject("ess.macRecordApply.getMacRecordPersonListCnt", obj)),
				Integer.class);
	}


	@Override
	public int saveRecordAppFile(Map paramMap) {
		int returnInt = 0;
		try {
			this.insert("ess.macRecordApply.saveRecordAppFile", paramMap);
			returnInt = 1;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		return returnInt;
	}


	@Override
	public List getCardRecordFileList(Map paramMap) {
		List list = new ArrayList();
		try {
			list = this.queryForList("ess.macRecordApply.getCardRecordFileList",paramMap);
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 信息决裁用，in/out进出门刷卡数据信息申请数据个数(view ar mac record apply information count)
	 * 
	 * @param object
	 * @return
	 */
	public int getArMacRecordAffirmCheckListCnt(Object object) {
		int returnInt = 0;
		try {
			returnInt = NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("ess.macRecordApply.getArMacRecordAffirmCheckListCnt",object)), Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnInt;
	}
	
	
	
	/**
	 * 决裁情况用，in/out进出门刷卡数据信息申请数据查询(view ar mac record apply info)，分页查询
	 * 
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getArMacRecordAffirmViewCheckList(Object object, int currentPage,int pageSize) {
		List returnList = new ArrayList();
		try {
			if (currentPage > -1 && pageSize > -1) {
				returnList = this.queryForList("ess.macRecordApply.getArMacRecordAffirmCheckViewList", object,currentPage, pageSize);
			} else {
				returnList = this.queryForList("ess.macRecordApply.getArMacRecordAffirmCheckViewList", object);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	

	/**
	 * 决裁情况用，in/out进出门刷卡数据信息申请数据查询(view ar mac record apply info)，不分页
	 * 
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getArMacRecordAffirmCheckViewList(Object object) {
		List returnList = new ArrayList();
		returnList = this.getArMacRecordAffirmViewCheckList(object, -1, -1);
		return returnList;
	}
	
	/**
	 * 决裁情况用，in/out进出门刷卡数据信息申请数据查询(view ar mac record apply info)，分页查询
	 * 
	 * @param object
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getArMacRecordAffirmViewListBySingle(Object object) {
		List returnList = new ArrayList();
		try {
				returnList = this.queryForList("ess.macRecordApply.getArMacRecordAffirmViewListBySingle", object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnList;
	}
	

	/**
	 * 获取漏刷卡导入信息
	 * 
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getEssArMacTempList(Object object) {
		List returnList = new ArrayList();
		returnList = this.getEssArMacTempList(object, -1, -1);
		return returnList;
	}
	
	/**
	 * 获取漏刷卡导入信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getEssArMacTempList(Object object, int currentPage, int pageSize){
		List returnList = new ArrayList() ;
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("ess.macRecordApply.getEssArMacTempList", object, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("ess.macRecordApply.getEssArMacTempList", object);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 获取漏刷卡导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getEssArMacTempCnt(Object object){
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ess.macRecordApply.getEssArMacTempCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	 * 获取出错的漏刷卡导入数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getEssArMacTempErrorCnt(Object object){
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ess.macRecordApply.getEssArMacTempErrorCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	
	/**
	 * 获取漏刷卡批量信息
	 * 
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getEssArMacList(Object object) {
		List returnList = new ArrayList();
		returnList = this.getEssArMacList(object, -1, -1);
		return returnList;
	}
	
	/**
	 * 获取漏刷卡批量信息
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public List getEssArMacList(Object object, int currentPage, int pageSize){
		List returnList = new ArrayList() ;
		try {
			if(currentPage > -1 && pageSize > -1){
				returnList = this.queryForList("ess.macRecordApply.getEssArMacList", object, currentPage, pageSize);
			}
			else{
				returnList = this.queryForList("ess.macRecordApply.getEssArMacList", object);
			}
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnList ;
	}
	
	/**
	 * 获取漏刷卡批量数量
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @author weizhengchen@ait.net.cn
	 * @date 2014-7-03
	 * @version V1.0
	 */
	public int getEssArMacCnt(Object object){
		int returnInt = 0 ;
		try {
			returnInt = 
				NumberUtils.parseNumber(ObjectUtils.toString(this.queryForObject("ess.macRecordApply.getEssArMacCnt", object)), Integer.class) ;
		} catch (SQLException e) {			
			e.printStackTrace();
		}
		return returnInt ;
	}
	

	/**
	 * 批量提交漏刷卡申请
	 * 
	 * @param obj
	 * @return
	 */
	public int submitArMacApplyInBatch(List list) throws Exception {
		try {
			this.updateForList("ess.macRecordApply.submitTempArMacApply", list);
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
	private void sendToLGEPInsert(LinkedHashMap paramMap){
			LinkedHashMap lgepMap = new LinkedHashMap();
			lgepMap.put("APPLY_TYPE", APPLY_TYPE_NO);
			lgepMap.put("APPLY_NO", paramMap.get("RECORD_NO_SEQ"));
			lgepMap.put("APPLY_TYPE_NAME", APPLY_TYPE_NAME);
			lgepMap.put("APPLY_TITLE", APPLY_TYPE_NAME);
			lgepMap.put("APPLY_EMPID", paramMap.get("PERSON_ID"));
			lgepMap.put("ARI_URL", "http://{serverIp}/LGEP/affirm/viewRecordAffirm?LGEP=LGEP&LANGUAGE=zh&APPLY_TYPE_NO="+APPLY_TYPE_NO+"&personId=123&APPLY_NO=" + paramMap.get("RECORD_NO_SEQ"));
			lgepMap.put("ABY_URL", "http://{serverIp}/LGEP/affirm/viewRecordAffirm?LGEP=LGEP&LANGUAGE=zh&APPLY_TYPE_NO="+APPLY_TYPE_NO+"&personId=" + paramMap.get("CURRENT_AFFIRM_ID") + "&APPLY_NO=" + paramMap.get("RECORD_NO_SEQ"));
			lgepMap.put("AFFIRM_LEVEL", "1");
			lgepMap.put("PRE_AFFIRM_EMPID", paramMap.get("CURRENT_AFFIRM_ID"));
			lgepMap.put("CURRENT_AFFIRM_ID", paramMap.get("CURRENT_AFFIRM_ID"));
			this.affirmInfoToLGEPSer.crateAffirm(lgepMap);
	}

	/**
	 * 提交后发送LGEP
	 * @param eventId
	 */
	private void sendToLGEPInsert(List list){
		for(int i=0;i<list.size();i++){
			LinkedHashMap paramMap = (LinkedHashMap)list.get(i);
			List LeaveList = this.getArMacRecordAffirmViewListBySingle(paramMap);
			LinkedHashMap lgepMap = (LinkedHashMap)LeaveList.get(0);
			lgepMap.put("APPLY_TYPE", APPLY_TYPE_NO);
			lgepMap.put("APPLY_NO", lgepMap.get("RECORD_NO"));
			lgepMap.put("APPLY_TYPE_NAME", APPLY_TYPE_NAME);
			lgepMap.put("APPLY_TITLE", APPLY_TYPE_NAME);
			lgepMap.put("APPLY_EMPID", lgepMap.get("PERSON_ID"));
			lgepMap.put("ARI_URL", "http://{serverIp}/LGEP/affirm/viewRecordAffirm?LGEP=LGEP&LANGUAGE=zh&APPLY_TYPE_NO="+APPLY_TYPE_NO+"&personId=123&APPLY_NO=" + lgepMap.get("RECORD_NO"));
			lgepMap.put("ABY_URL", "http://{serverIp}/LGEP/affirm/viewRecordAffirm?LGEP=LGEP&LANGUAGE=zh&APPLY_TYPE_NO="+APPLY_TYPE_NO+"&personId=" + lgepMap.get("CURRENT_AFFIRM_ID") + "&APPLY_NO=" + lgepMap.get("RECORD_NO"));
			lgepMap.put("AFFIRM_LEVEL", "1");
			lgepMap.put("PRE_AFFIRM_EMPID", lgepMap.get("CURRENT_AFFIRM_ID"));
			lgepMap.put("CURRENT_AFFIRM_ID", lgepMap.get("CURRENT_AFFIRM_ID"));
			this.affirmInfoToLGEPSer.crateAffirm(lgepMap);
		}
	}
	
	/**
	 * 审批通过并完成，插入ar_apply_result
	 * 
	 * @param paramMap
	 * @return
	 */
	private void doInsertArApplyResult(LinkedHashMap arMacRecordApply)
			throws Exception {

		// 删除之前的AR_APPLY_RESULT的数据
		//this.delete("ess.macRecordApply.deleteArMacRecordApplyByApplyNo",
		//		arMacRecordApply);
		// 插入新的的AR_APPLY_RESULT的数据
		this.insert("ess.macRecordApply.insertArMacRecordApplyResult",
				arMacRecordApply);
		if(!this.isAfterLeave(arMacRecordApply)){
			// 插入新的的AR_SHIFT_CHANGE的数据
			LinkedHashMap param = new LinkedHashMap();
			param.put("PERSON_ID", arMacRecordApply.get("PERSON_ID"));
			param.put("CPNY_ID", arMacRecordApply.get("CPNY_ID"));
			param.put("AR_FROM_TIME", arMacRecordApply.get("APPLY_DATE"));
			param.put("AR_TO_TIME", arMacRecordApply.get("APPLY_DATE"));
			param.put("CHANGE_TYPE", "漏刷卡申请");
			this.insert("ess.humanAffirm.insertLeaveApplyResultForArApplyAfterForMac",param);
			if("Y".equals(arMacRecordApply.get("LASTMONTH_YN"))){
				this.insert("ess.humanAffirm.insertLeaveApplyResultForArApplyAfterZhuisu",param);
			}
		}
	}

	/**
	 * 批量申请审批通过并完成，插入ar_apply_result
	 * 
	 * @param paramMap
	 * @return
	 */
	private void doBatchInsertArApplyResult(LinkedHashMap arMacRecordApply)
			throws Exception {
		List list = this.queryForList(
				"ess.macRecordApply.getEssArMacBatchList", arMacRecordApply);
		for (int i = 0; i < list.size(); i++) {
			LinkedHashMap macApplyMap = (LinkedHashMap) list.get(i);
			macApplyMap.put("CPNY_ID", arMacRecordApply.get("CPNY_ID"));
			macApplyMap.put("APPLY_NO", macApplyMap.get("RECORD_NO"));
			macApplyMap.put("ADMIN_ID", arMacRecordApply.get("PERSON_ID"));
			doInsertArApplyResult(macApplyMap);
		}
	}
	

	/**
	 * 
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public LinkedHashMap getArMacRecordApplyInfoForDisplay(Object object) {
		LinkedHashMap arMacRecordApply = null;
		try {
			arMacRecordApply = (LinkedHashMap) this.queryForObject("ess.macRecordApply.getArMacRecordApplyInfoByApplyNoForDisplay",object);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return arMacRecordApply;
	}
	

	/**
	 * 取消已审核通过的漏刷卡申请
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean cancelCardApply(Object object) throws Exception {
		Boolean falg = true;
		LinkedHashMap arMacRecordApply = (LinkedHashMap) this.queryForObject("ess.macRecordApply.getArMacRecordApplyInfoByApplyNo",object);

		if (null != arMacRecordApply.get("BATCH_YN")
				&& "Y".equals(arMacRecordApply.get("BATCH_YN"))) {
			this.delete("ess.macRecordApply.deleteArMacRecordApplyByApplyNoBatch", arMacRecordApply);
			this.insert("ess.macRecordApply.insertLeaveApplyResultForArApplyAfterForMacBatch",
					arMacRecordApply);
		} else {
			this.delete("ess.macRecordApply.deleteArMacRecordApplyByApplyNo", arMacRecordApply);
			if(!this.isAfterLeave(arMacRecordApply)){
				// 插入新的的AR_SHIFT_CHANGE的数据
				LinkedHashMap param = new LinkedHashMap();
				param.put("PERSON_ID", arMacRecordApply.get("PERSON_ID"));
				param.put("CPNY_ID", arMacRecordApply.get("CPNY_ID"));
				param.put("AR_FROM_TIME", arMacRecordApply.get("APPLY_DATE"));
				param.put("AR_TO_TIME", arMacRecordApply.get("APPLY_DATE"));
				param.put("CHANGE_TYPE", "漏刷卡申请");
				this
						.insert(
								"ess.humanAffirm.insertLeaveApplyResultForArApplyAfterForMac",
								param);
			}
		}
		this.update("ess.macRecordApply.cancelArMacRecordByApplyNo", object);
		return falg;
	}
	
	/**
	 * 查看是否是休息日期
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public int isXiuXi(Object object) throws Exception {
		try {
			return NumberUtils.parseNumber(ObjectUtils.toString(this
					.queryForObject("ess.macRecordApply.isXiuXi", object)),
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
}