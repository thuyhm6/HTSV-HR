package com.ait.pa.dao;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: CycleDao.java
 * @Description: implement Class CycleDaoImp.java
 * @Create date: 2012-1-6 下午03:29:47
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
public interface SalaryCanShuDao {
	
	@SuppressWarnings("unchecked")
	public BigDecimal getPqdNoAddOne() ;
	
	@SuppressWarnings("unchecked")
	public Object getPaiQianDiInfo(Object obj) ;
	
	@SuppressWarnings("unchecked")
	public Object getZuiDiGongZiBiaoZhunFeiCuXiaoYuanObjInfo(Object obj) ;
	
	@SuppressWarnings("unchecked")
	public Object getZuiDiGongZiBiaoZhunCuXiaoYuanObjInfo(Object obj) ;
	
	@SuppressWarnings("unchecked")
	public Object getYuTiDuiXiangGuanLiObjInfo(Object obj) ;
	
	@SuppressWarnings("unchecked")
	public Object viewHaoFengSetList(Object obj) ;
	
	@SuppressWarnings("unchecked")
	public List viewHaoFengSetListGrade(Object obj) ;
	
	@SuppressWarnings("unchecked")
	public List viewHaoFengSetListGradeNo(Object obj) ;
	
	
	
	@SuppressWarnings("unchecked")
	public Object getNianZhongJiangYuTiObjInfo(Object obj) ;
	
	@SuppressWarnings("unchecked")
	public Object getPaiQianDiJinTieBiaoZhunObjInfo(Object obj) ;

	@SuppressWarnings("unchecked")
	public Object getHaoFengSheZhiInfo(Object obj) ;
	
	
	@SuppressWarnings("unchecked")
	public List getPaiQianDiList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getZuiDiGongZiBiaoZhunList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getZuiDiGongZiBiaoZhunCuXiaoYuanList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getPaiQianDiTempList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getZuiDiGongZiBiaoZhunFeiCuXiaoYuanTempList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getZuiDiGongZiBiaoZhunCuXiaoYuanTempList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getPaiQianDiJinTieBiaoZhunTempList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getYuTiDuiXiangGuanList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getDaQuNamesList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getNianZhongJiangJiTiList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getNianZhongJiangJiTiJiSuanList(Object object);
	
	@SuppressWarnings("unchecked")
	public List viewZhengGuiYuTiJiSuanList(Object object);
	
	@SuppressWarnings("unchecked")
	public List getPaiQianDiJtBzList(Object object);

	@SuppressWarnings("unchecked")
	public List viewHaoFengSetListfenye(Object object);
	

	@SuppressWarnings("unchecked")
	public List viewHaoFengSetListfenyeExcel(Object object);
	
	@SuppressWarnings("unchecked")
	public int getPaiQianDiCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public int geZuiDiGongZiBiaoZhunListCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public int getZuiDiGongZiBiaoZhunCuXiaoYuanListCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public int getPaiQianDiTempCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public int getZuiDiGongZiBiaoZhunFeiCuXiaoYuanTempListCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public int getZuiDiGongZiBiaoZhunCuXiaoYuanTempListCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public int getPaiQianDiJinTieBiaoZhunTempListCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public int getPaiQianDiTempErrorCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public int getZuiDiGongZiBiaoZhunFeiCuXiaoYuanTempErrorCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public int getZuiDiGongZiBiaoZhunCuXiaoYuanTempErrorCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public int getPaiQianDiJinTieBiaoZhunTempErrorCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public int getYuTiDuiXiangGuanListCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public int getNianZhongJiangJiTiListCntCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public int getNianZhongJiangJiTiJiSuanListCnt(Object object);

	@SuppressWarnings("unchecked")
	public int viewZhengGuiYuTiJiSuanListCn(Object object);
	
	@SuppressWarnings("unchecked")
	public int getPaiQianDiJtBzCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public int getHaoFengSheZhiListCnt(Object object);
	
	@SuppressWarnings("unchecked")
	public List getPaiQianDiList(Object object, int currentPage, int pageSize);
	
	@SuppressWarnings("unchecked")
	public List getZuiDiGongZiBiaoZhunList(Object object, int currentPage, int pageSize);
	
	@SuppressWarnings("unchecked")
	public List getZuiDiGongZiBiaoZhunCuXiaoYuanList(Object object, int currentPage, int pageSize);
	
	@SuppressWarnings("unchecked")
	public List getPaiQianDiTempList(Object object, int currentPage, int pageSize);
	
	@SuppressWarnings("unchecked")
	public List getZuiDiGongZiBiaoZhunFeiCuXiaoYuanTempList(Object object, int currentPage, int pageSize);
	
	@SuppressWarnings("unchecked")
	public List getZuiDiGongZiBiaoZhunCuXiaoYuanTempList(Object object, int currentPage, int pageSize);
	
	@SuppressWarnings("unchecked")
	public List getPaiQianDiJinTieBiaoZhunTempList(Object object, int currentPage, int pageSize);
	
	@SuppressWarnings("unchecked")
	public List getYuTiDuiXiangGuanList(Object object, int currentPage, int pageSize);
	
	@SuppressWarnings("unchecked")
	public List getDaQuNamesList(Object object, int currentPage, int pageSize);
	
	@SuppressWarnings("unchecked")
	public List getNianZhongJiangJiTiList(Object object, int currentPage, int pageSize);
	
	@SuppressWarnings("unchecked")
	public List getNianZhongJiangJiTiJiSuanList(Object object, int currentPage, int pageSize);
	
	@SuppressWarnings("unchecked")
	public List viewZhengGuiYuTiJiSuanList(Object object, int currentPage, int pageSize);
	
	
	@SuppressWarnings("unchecked")
	public List viewHaoFengSetListfenye(Object object, int currentPage, int pageSize);
	
	@SuppressWarnings("unchecked")
	public List getPaiQianDiJtBzList(Object object, int currentPage, int pageSize);
	
	@SuppressWarnings("unchecked")
	public List viewHaoFengSetList(Object object, int currentPage, int pageSize);
	
	@SuppressWarnings("unchecked")
	public void addPaiQianDiGuanLiInfo(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public void addZuiDiGongZiBiaoZhunFeiCuXiaoYuanInfo(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public void addZuiDiGongZiBiaoZhunCuXiaoYuanInfo(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public void addYuTiDuiXiangGuanLiInfo(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public void addNianZhongJiangYuTiInfo(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public void addPaiQianDiJinTieBiaoZhunInfo(Object object) throws Exception;

	@SuppressWarnings("unchecked")
	public int addHaoFengGuanLiInfo(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public int getPaiQianDiGuanLiInfo(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public int getZuiDiGongZiBiaoZhunFeiCuXiaoYuanInfo(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public int getZuiDiGongZiBiaoZhunCuXiaoYuanInfo(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public int getYuTiDuiXiangGuanLiInfo(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public int getYuTiDuiXiangGuanLiInfoLgech(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public int getNianZhongJiangYuTiInfo(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public int getPaiQianDiJinTieBiaoZhunInfo(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public int checkPaiQianDiYesOrNoThisInfo(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public int getPaiQianDiGuanLiInfoUpdate(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public int getZuiDiGongZiBiaoZhunFeiCuXiaoYuanInfoUpdate(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public int getZuiDiGongZiBiaoZhunCuXiaoYuanInfoUpdate(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public int getYuTiDuiXiangGuanLiInfoUpdate(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public int getYuTiDuiXiangGuanLiInfoUpdateLgech(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public int getNianZhongJiangYuTiInfoUpdate(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public int getPaiQianDiJinTieBiaoZhunInfoUpdate(Object object) throws Exception;
	
	
	@SuppressWarnings("unchecked")
	public int getHaoFengDayMonAjax(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public String getPaiQianDiGuanLiInfoUpdatePqd(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public void updatePaiQianDiGuanLiInfo(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public void updateZuiDiGongZiBiaoZhunFeiCuXiaoYuanInfo(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public void updateZuiDiGongZiBiaoZhunCuXiaoYuanInfo(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public void updatePaiQianDiGuanLiTempInfo(List list) throws Exception;
	
	@SuppressWarnings("unchecked")
	public void updatePaiQianDiGuanLiTempInfo(List list, Map map) throws Exception;
	
	@SuppressWarnings("unchecked")
	public void updatePaiQianDiJinTieBiaoZhunTempInfo(List list, Map map) throws Exception;
	
	@SuppressWarnings("unchecked")
	public void deletePaiQianDiTempChongFuInfo(Object obj) throws Exception;
	
	@SuppressWarnings("unchecked")
	public void insertPaiQianDiInfoFromTempInfo(Object obj) throws Exception;
	
	@SuppressWarnings("unchecked")
	public void updateYuTiDuiXiangGuanLiInfo(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public void updateNianZhongJiangYuTiInfo(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public void updateZuiDiGongZiBiaoZhunFeiCuXiaoYuanExcelResultToNos(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public void updatePaiQianDiGuanLiExcelResultToNos(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public void updatePaiQianDiJinTieBiaoZhunExcelResultToNos(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public void updateZuiDiGongZiBiaoZhunCuXiaoYuanExcelResultToNos(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public String callNianZhongJiangYuTiJiSuanProduce(Map map) throws Exception;

	@SuppressWarnings("unchecked")
	public String callZhengGuiYuTiJiSuanProduce(Map map) throws Exception;
	
	
	@SuppressWarnings("unchecked")
	public void updatePaiQianDiJinTieBiaoZhunInfo(Object object) throws Exception;

	@SuppressWarnings("unchecked")
	public void updateHaoFengSheZhiInfo(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public void deletePaiQianDiGuanLiInfo(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public void deleteYuTiDuiXiangGuanLiInfo(Object object) throws Exception;
	
	@SuppressWarnings("unchecked")
	public void deletePaiQianDiJinTieBiaoZhunInfo(Object object) throws Exception;

	@SuppressWarnings("unchecked")
	public void deleteHaoFengSheZhiInfo(Object object) throws Exception;
	
	public List getCycleParamList(Map paramMap, int pageNum, int numPerPage);

	public List getCycleParamList(Map paramMap);
	
	public List getErrorPqdNos();
	
	public List getErrorPqdNos (Map paramMap);
	
	public List getErrorPqdJinTieBiaoZhunNos (Map paramMap);
	
	public int getCycleParamCnt(Map paramMap);

	@SuppressWarnings("unchecked")
	public void addCycleParamInfo(LinkedHashMap paramMap) throws Exception;

	@SuppressWarnings("unchecked")
	public void updateCycleParamInfo(LinkedHashMap paramMap) throws Exception;

	@SuppressWarnings("unchecked")
	public void deleteCycleParamInfo(LinkedHashMap paramMap) throws Exception;

	public Object getCycleParam(Map paramMap);

	public int checkCycleInfoUnique(LinkedHashMap paramMap);

	public int checkCycleForDelete(Map paramMap);
	
	public List getEmpTypeCodeList(Map paramMap);
	
	public List getStatisticList(Map paramMap);
	
	public List getKeeperEmpTypeCodeList(Map paramMap);
	
	public List getPaSupervisorEmpTypeCodeList(Map paramMap);
	
	/**
	 * 批量保存导入的派遣地数据(add pai qian di data)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public void addPaiQianDiDataImport(List list) throws Exception;
	
	/**
	 * 验证导入的城市等级、省份、城市、地区是否存在
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getPaiQianDiCodeNoCheckList(Object obj);
	
	/**
	 * 更新导入的派遣地的check结果(update pai qian di data of import for check result)
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public int updatePaiQianDiDataCheckResult(Object object) throws Exception;
	
	/**
	 * 验证正式表里是否已经存在该派遣地信息
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getPaiQianDiInfoExistList(Object obj);
	
	/**
	 * 导入派遣地信息--查看信息列表(get pai qian di import list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getPaiQianDiImportInfoList(Object object);

	/**
	 * 修改时更新旧数据状态
	 */
	@SuppressWarnings("unchecked")
	public void updateOldBonus(Object obj)throws Exception ;
	/**
	 * check当前年度未添加基准的法人
	 * @param Object
	 * @return
	 */
	public List checkAnnualBonusParamSetup(Map obj);
	
	public List viewHaoFeng(Object object);
	
	public List viewHaoFengNAME(Object object);

}
