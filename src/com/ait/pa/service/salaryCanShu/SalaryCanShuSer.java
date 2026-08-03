package com.ait.pa.service.salaryCanShu;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * 工资参数。。。
 * 派遣地管理
 * 派遣津贴标准
 * @author LXJ
 *
 */
public interface SalaryCanShuSer {

	@SuppressWarnings("unchecked")
	public Object getPaiQianDiInfo(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public Object getZuiDiGongZiBiaoZhunFeiCuXiaoYuanObjInfo(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public Object getZuiDiGongZiBiaoZhunCuXiaoYuanObjInfo(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public Object getYuTiDuiXiangGuanLiInfo(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public Object viewHaoFengSetList(HttpServletRequest request) ;

	@SuppressWarnings("unchecked")
	public List viewHaoFengSetListGrade(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List viewHaoFengSetListGradeNo(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public Object getNianZhongJiangYuTiInfo(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public Object getPaiQianDiJinTieBiaoZhunInfo(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public Object getHaoFengSheZhiInfo(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getPaiQianDiList(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getZuiDiGongZiBiaoZhunList(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getZuiDiGongZiBiaoZhunCuXiaoYuanList(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getPaiQianDiTempList(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getZuiDiGongZiBiaoZhunFeiCuXiaoYuanTempList(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getZuiDiGongZiBiaoZhunCuXiaoYuanTempList(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getPaiQianDiJinTieBiaoZhunTempList(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int getPaiQianDiTempCnt(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int getZuiDiGongZiBiaoZhunFeiCuXiaoYuanTempListCnt(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int getZuiDiGongZiBiaoZhunCuXiaoYuanTempListCnt(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int getPaiQianDiJinTieBiaoZhunTempListCnt(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int getPaiQianDiTempErrorCnt(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int getZuiDiGongZiBiaoZhunFeiCuXiaoYuanTempErrorCnt(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int getZuiDiGongZiBiaoZhunCuXiaoYuanTempErrorCnt(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int getPaiQianDiJinTieBiaoZhunTempErrorCnt(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getYuTiDuiXiangGuanList(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getDaQuNamesList(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getNianZhongJiangJiTiList(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getNianZhongJiangJiTiJiSuanList(HttpServletRequest request) ;

	@SuppressWarnings("unchecked")
	public List viewZhengGuiYuTiJiSuanList(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getPaiQianDiJtBzList(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List viewHaoFengSetListfenye(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List viewHaoFengSetListfenyeExcel(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int getPaiQianDiCnt(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int geZuiDiGongZiBiaoZhunListCnt(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int getZuiDiGongZiBiaoZhunCuXiaoYuanListCnt(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int getYuTiDuiXiangGuanListCnt(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int getNianZhongJiangJiTiListCntCnt(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int getNianZhongJiangJiTiJiSuanListCnt(HttpServletRequest request) ;

	@SuppressWarnings("unchecked")
	public int viewZhengGuiYuTiJiSuanListCn(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int getPaiQianDiJtBzCnt(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int getHaoFengSheZhiListCnt(HttpServletRequest request) ;
	
	
	@SuppressWarnings("unchecked")
	public int addPaiQianDiGuanLiInfo(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int addZuiDiGongZiBiaoZhunFeiCuXiaoYuanInfo(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int addZuiDiGongZiBiaoZhunCuXiaoYuanInfo(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int addYuTiDuiXiangGuanLiInfo(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int addNianZhongJiangYuTiInfo(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int addPaiQianDiJinTieBiaoZhunInfo(HttpServletRequest request) ;

	@SuppressWarnings("unchecked")
	public int addHaoFengGuanLiInfo(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int updatePaiQianDiGuanLiInfo(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int updateZuiDiGongZiBiaoZhunFeiCuXiaoYuanInfo(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int updateZuiDiGongZiBiaoZhunCuXiaoYuanInfo(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int updatePaiQianDiErrorInfo(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int updatePaiQianDiJinTieBiaoZhunErrorInfo(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int deletePaiQianDiTempChongFuInfo(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int insertPaiQianDiInfoFromTempInfo(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int updateYuTiDuiXiangGuanLiInfo(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int updateNianZhongJiangYuTiInfo(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int updateZuiDiGongZiBiaoZhunFeiCuXiaoYuanExcelResultToNos(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int updatePaiQianDiGuanLiExcelResultToNos(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int updatePaiQianDiJinTieBiaoZhunExcelResultToNos(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int updateZuiDiGongZiBiaoZhunCuXiaoYuanExcelResultToNos(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public String callNianZhongJiangYuTiJiSuanProduce(HttpServletRequest request) ;
	
	
	@SuppressWarnings("unchecked")
	public int callZhengGuiYuTiJiSuanProduce(HttpServletRequest request) ;
	
	
	@SuppressWarnings("unchecked")
	public int updatePaiQianDiJinTieBiaoZhunInfo(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int getHaoFengDayMonAjax(HttpServletRequest request);

	@SuppressWarnings("unchecked")
	public int updateHaoFengSheZhiInfo(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int deletePaiQianDiGuanLiInfo(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int deleteYuTiDuiXiangGuanLiInfo(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int deletePaiQianDiJinTieBiaoZhunInfo(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public int deleteHaoFengSheZhiInfo(HttpServletRequest request) ;
	
	/**
	 * 添加导入的派遣地数据(add pai qian di data)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int addImportPaiQianDiData(HttpServletRequest request)throws Exception;
	
	/**
	 * 导入派遣地信息--查看信息列表(get pai qian di import list)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List getPaiQianDiImportInfoList(HttpServletRequest request) throws Exception;
	
	public List getCompanyList(HttpServletRequest request)
	throws SQLException ;
	
	public List getCompanyListYuti(HttpServletRequest request)
	throws SQLException ;
	/**
	 * check当前年度未添加基准的法人
	 */
	@SuppressWarnings("unchecked")
	public String checkAnnualBonusParamSetup(HttpServletRequest request);
	
	public List viewHaoFeng(HttpServletRequest request);
	
	public List viewHaoFengNAME(HttpServletRequest request);
}
