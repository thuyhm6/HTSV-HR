package com.ait.pa.service.excelUtil;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;

/**
 * Copyright:   LDCC(c)
 * Company:     LDCC
 * @fileName ExcelUtilSer.java
 * @author zhouyeqing(zhouyeqing@ait.net.cn)
 * @Date 2012-3-29 下午08:31:59
 * @version 5.0
 * 
 */
public interface ExcelUtilSer {

	public int getCodeNoByName(Object name);
	
	public String queryAgreeno();
	
	public void insertEduTrainAgreement(Object obj);
	public int insertOTLimit(Object obj);
	public int insertTrainResult(Object obj);
	public int insertTeacherEvaluate(Object obj);
	public int insertVacPlan(Object obj);
	
	public void updateEduFreeEmployee(Object obj);
	
	public void updateEduTrainSyllabus(Object obj);
	
	public void deleteEduTrainSyllabus(Object obj) throws Exception;
	
	public void insertExcelData(Object obj) throws Exception;
	
	public void deleteWithTarget(Object obj, String target);
	
	public void insertWithTarget(Object obj, String target);
	
	@SuppressWarnings("unchecked")
	public List getExcelExportDataList(Object obj);
	
	@SuppressWarnings("unchecked")
	public List getPaHistoryDataList(Object obj);
	
	@SuppressWarnings("unchecked")
	public List trainAgreementTemp(Object obj);
	
	@SuppressWarnings("unchecked")
	public LinkedHashMap putIntoAliasValueAndTypeMap(String tableName,LinkedHashMap aliasValueMap,LinkedHashMap aliasTypeMap,LinkedHashMap aliasValueI18nMap);
	
	@SuppressWarnings("unchecked")
	public LinkedHashMap importData(HttpServletRequest request,HttpServletResponse response,LinkedHashMap map,ModelMap modelMap);
	
	@SuppressWarnings("unchecked")
	public LinkedHashMap importOtData(HttpServletRequest request,HttpServletResponse response,LinkedHashMap map,ModelMap modelMap,String forwardUrl,String navTabId);
	
	@SuppressWarnings("unchecked")
	public LinkedHashMap importPaForLeftData(HttpServletRequest request,HttpServletResponse response,LinkedHashMap map,ModelMap modelMap,String forwardUrl,String navTabId);
	
	@SuppressWarnings("unchecked")
	public LinkedHashMap importInsCalcObjectData(HttpServletRequest request,HttpServletResponse response,LinkedHashMap map,ModelMap modelMap,String forwardUrl,String navTabId);
	
	@SuppressWarnings("unchecked")
	public LinkedHashMap importFundCalcObjectData(HttpServletRequest request,HttpServletResponse response,LinkedHashMap map,ModelMap modelMap,String forwardUrl,String navTabId);
	
	@SuppressWarnings("unchecked")
	public LinkedHashMap importData2(HttpServletRequest request,HttpServletResponse response,LinkedHashMap map,ModelMap modelMap,List list,List list1,int num);
	
	@SuppressWarnings("unchecked")
	public void exportExcel(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap,LinkedHashMap sqlContentmap,List aliasNameList,List aliasList) throws Exception;
	
	@SuppressWarnings("unchecked")
	public void exportIsParamDataExcel(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap,LinkedHashMap sqlContentmap,List aliasNameList,List aliasList,String fileName) throws Exception;
	
	@SuppressWarnings("unchecked")
	public LinkedHashMap putIntoSqlContentMap(List list);

	@SuppressWarnings("unchecked")
	public LinkedHashMap importArData(HttpServletRequest request,HttpServletResponse response,LinkedHashMap map,ModelMap modelMap);
	
	public void download(HttpServletRequest request,HttpServletResponse response,String pathstr)throws Exception;
	
	public void downloadPaHistory(HttpServletRequest request,HttpServletResponse response,String pathstr)throws Exception;
	
	public void downloadPaRiseInfo(HttpServletRequest request,HttpServletResponse response,String pathstr)throws Exception;
	
	@SuppressWarnings("unchecked")
	public String exportIntoExcel(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap,LinkedHashMap sqlContentmap,List aliasNameList,List aliasList)throws Exception;
	@SuppressWarnings("unchecked")
	public String exportIntoExcel(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap,LinkedHashMap sqlContentmap,List aliasNameList,List aliasList,List aliasListExpType)throws Exception;
	@SuppressWarnings("unchecked")
	public String exportIntoExcel1(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap,LinkedHashMap sqlContentmap,List aliasNameList,List aliasList)throws Exception;
	@SuppressWarnings("unchecked")
	public String exportIntoExcel2(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap,LinkedHashMap sqlContentmap,List aliasNameList,List aliasList)throws Exception;
	@SuppressWarnings("unchecked")
	public String exportPaHistoryExcel(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap,LinkedHashMap sqlContentmap,List aliasNameList,List aliasList)throws Exception;
	@SuppressWarnings("unchecked")
	public String exportPaHistoryExcel(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap,LinkedHashMap sqlContentmap,List aliasNameList,List aliasList,List aliasListExpType)throws Exception;
	
	@SuppressWarnings("unchecked")
	public String exportPaRiseInfoExcel(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap,List<LinkedHashMap> dataList)throws Exception;
	
	@SuppressWarnings("unchecked")
	public List getContentNoByFiled(Object filed);
	
	@SuppressWarnings("unchecked")
	public List exportPersonalList(Object obj);
	
	@SuppressWarnings("unchecked")
	public void exportExcelTwoSheet(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap,LinkedHashMap sqlContentmap,List aliasNameList,List aliasList,List sheet2List)throws Exception;
	@SuppressWarnings("unchecked")
	public void exportExcelTwoSheetDiaoling(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap,LinkedHashMap sqlContentmap,List aliasNameList,List aliasList,List sheet2List)throws Exception;
	@SuppressWarnings("unchecked")
	public LinkedHashMap importArShiftData(HttpServletRequest request,HttpServletResponse response,LinkedHashMap map,ModelMap modelMap);
	
	@SuppressWarnings("unchecked")
	public LinkedHashMap importArCardRecordData(HttpServletRequest request,HttpServletResponse response,LinkedHashMap map,ModelMap modelMap);
	
	@SuppressWarnings("unchecked")
	public LinkedHashMap importArDetialData(HttpServletRequest request,HttpServletResponse response,LinkedHashMap map,LinkedHashMap historymap,ModelMap modelMap);
	
	@SuppressWarnings("unchecked")
	public LinkedHashMap importArCardRecordData3(HttpServletRequest request,HttpServletResponse response,LinkedHashMap map,ModelMap modelMap);
	
	@SuppressWarnings("unchecked")
	public LinkedHashMap importArCardRecordData4(HttpServletRequest request,HttpServletResponse response,LinkedHashMap map,ModelMap modelMap);
	
	@SuppressWarnings("unchecked")
	public LinkedHashMap importPaiQianDiInfoData(HttpServletRequest request,HttpServletResponse response,LinkedHashMap map,ModelMap modelMap);
	
	@SuppressWarnings("unchecked")
	public LinkedHashMap importPaForLeftMenInfoData(HttpServletRequest request,HttpServletResponse response,LinkedHashMap map,ModelMap modelMap);
	
	@SuppressWarnings("unchecked")
	public LinkedHashMap importZuiDiGongZiBiaoZhunFeiCuXiaoYuanInfo(HttpServletRequest request,HttpServletResponse response,LinkedHashMap map,ModelMap modelMap);
	
	@SuppressWarnings("unchecked")
	public LinkedHashMap importZuiDiGongZiBiaoZhunCuXiaoYuanInfo(HttpServletRequest request,HttpServletResponse response,LinkedHashMap map,ModelMap modelMap);
	
	@SuppressWarnings("unchecked")
	public LinkedHashMap importPaiQianDiJinTieBiaoZhunTempInfoData(HttpServletRequest request,HttpServletResponse response,LinkedHashMap map,ModelMap modelMap);
	
	@SuppressWarnings("unchecked")
	public LinkedHashMap importPaiQianDiInfoData(HttpServletRequest request,HttpServletResponse response,LinkedHashMap map,ModelMap modelMap, String checkResult);
	
	@SuppressWarnings("unchecked")
	public LinkedHashMap importPaiQianDiJinTieBiaoZhunInfoData(HttpServletRequest request,HttpServletResponse response,LinkedHashMap map,ModelMap modelMap);
	
	@SuppressWarnings("unchecked")
	public LinkedHashMap importArCardRecordData2(HttpServletRequest request,HttpServletResponse response,LinkedHashMap map,ModelMap modelMap);
	
	@SuppressWarnings("unchecked")
	public void exportExcelByName(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap,LinkedHashMap sqlContentmap,List aliasNameList,List aliasList,String name) throws Exception;

	@SuppressWarnings("unchecked")
	public void exportExcelTwoSheetByName(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap,LinkedHashMap sqlContentmap,List aliasNameList,List aliasList,List sheet2List,String name)throws Exception;

	@SuppressWarnings("unchecked")
	public void exportExcelTwoSheetByNameZuiDiGongZiFei(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap,LinkedHashMap sqlContentmap,List aliasNameList,List aliasList,List sheet2List,String name)throws Exception;
	
	@SuppressWarnings("unchecked")
	public void exportExcelTwoSheetByNameZuiDiGongZiFeiTemp(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap,LinkedHashMap sqlContentmap,List aliasNameList,List aliasList,List sheet2List,String name)throws Exception;
	
	@SuppressWarnings("unchecked")
	public void exportExcelThreeSheetByName(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap,LinkedHashMap sqlContentmap,List aliasNameList,List aliasList,List sheet2List,List sheet2List1,String name)throws Exception;
	
	@SuppressWarnings("unchecked")
	public void exportExcelOneByOneSheetByName(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap,LinkedHashMap sqlContentmap,List aliasNameList,List aliasList,List sheet2List,String name)throws Exception;
	
	@SuppressWarnings("unchecked")
	public void exportExcelByNamePwd(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap,
			List aliasValueList , List aliasNameList, String[] columns,
			String name,Map paramMap) throws Exception;
	
	@SuppressWarnings("unchecked")
	public LinkedHashMap importSapSpecialEmpInfo(HttpServletRequest request,HttpServletResponse response,LinkedHashMap map,ModelMap modelMap);

	/**
	 * 查询excel导出需要填充的下拉列表值
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author zhengxiaochen zhengxiaochen@ait.net.cn
	* @date 2013-8-29 下午02:57:43 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	public List getExcelExportTypeList(HttpServletRequest request)throws Exception;

	/**
	 * 分析excel的各个列，获取各个列的值，并且导入的数据库中 Description:
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author zhengxiaochen zhengxiaochen@ait.net.cn
	* @date 2013-8-29 下午07:31:37 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	public ModelMap importDataForTransferOrder(HttpServletRequest request,
			HttpServletResponse response, LinkedHashMap map, ModelMap modelMap)throws Exception;
	
	/**
	 * 获得导入Excel的类型编号
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author zhengxiaochen zhengxiaochen@ait.net.cn
	* @date 2013-9-2 上午01:46:34 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	public LinkedHashMap getImportExcelTransferOrderType(HttpServletRequest request,
			HttpServletResponse response)throws Exception;
	
	/**
	 * 获得导入Excel的类型配置集合
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author zhengxiaochen zhengxiaochen@ait.net.cn
	* @date 2013-9-2 上午01:30:41 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	public List getImportTransferOrderTypeConfigList(HttpServletRequest request)throws Exception;

	/**
	 * Excel导入(异动)
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author zhengxiaochen zhengxiaochen@ait.net.cn
	* @date 2013-9-2 下午02:11:13 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	public ModelMap importDataForOrderOperation(HttpServletRequest request,
			HttpServletResponse response, LinkedHashMap map, ModelMap modelMap,Integer transferOrderType)throws Exception;

	@SuppressWarnings("unchecked")
	public LinkedHashMap putIntoAliasValueAndTypeAndNameMap
		(String tableName,LinkedHashMap aliasValueMap,List aliasNameList,
				String titleSql,LinkedHashMap aliasTypeMap,LinkedHashMap aliasValueI18nMap);

	/**
	 * 查询显示不修改且可查看显示的字段
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author zhengxiaochen zhengxiaochen@ait.net.cn
	* @date 2013-9-19 上午02:54:35 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	public List getTransferOrderTypeByParams(HttpServletRequest request)throws Exception;
    /**
	 * 为制作sql准备参数
	 * 
	 * @Description: CHRS2.0 SI PROJECT
	 * @author penghaixia
	 * @date 2014.6.10
	 * @version V1.0
	 */	
	@SuppressWarnings("unchecked")
	public LinkedHashMap putIntoAliasValueAndTypeMapForTemple(String tableName,LinkedHashMap aliasValueMap,LinkedHashMap aliasTypeMap,LinkedHashMap aliasValueI18nMap, LinkedHashMap aliasValueAppendMap);
    /**
	 * 分析excel的各个列，获取各个列的值，并且导入的数据库中 Description:
	 * 
	 * @Description: CHRS2.0 SI PROJECT
	 * @author penghaixia
	 * @date 2014.6.10
	 * @version V1.0
	 */
	@SuppressWarnings("unchecked")
	public ModelMap prepareForImportExcel(HttpServletRequest request,
			String adminID, LinkedHashMap cellmap);
    /**
	 * 将excel数据导入DB:
	 * 
	 * @Description: CHRS2.0 SI PROJECT
	 * @author penghaixia
	 * @date 2014.6.10
	 * @version V1.0
	 */
	public ModelMap importExcelSalesEvalData(HttpServletRequest request,
			HttpServletResponse response, LinkedHashMap map, ModelMap modelMap) throws Exception;
	/**
	 * 将excel数据导入DB:
	 * 
	 * @Description: CHRS2.0 SI PROJECT
	 * @author penghaixia
	 * @date 2014.6.10
	 * @version V1.0
	 */
	public ModelMap importExcelOTLimitData(HttpServletRequest request,
			HttpServletResponse response, LinkedHashMap map, ModelMap modelMap) throws Exception;
	
	/**
	 * 将excel数据导入DB:
	 * 
	 * @Description: CHRS2.0 SI PROJECT
	 * @author weizhengchen
	 * @date 2014.6.10
	 * @version V1.0
	 */
	public ModelMap importExcelData(HttpServletRequest request,
			HttpServletResponse response, LinkedHashMap map, ModelMap modelMap, String forwardUrl, String navTabId) throws Exception;
	
	/**
	 * 将excel数据导入DB: hr_evaluate_info
	 * 
	 * @Description: CHRS2.0 SI PROJECT
	 * @author weizhengchen
	 * @date 2014.6.10
	 * @version V1.0
	 */
	public ModelMap importEvaluateExcelData(HttpServletRequest request,
			HttpServletResponse response, LinkedHashMap map, ModelMap modelMap, String forwardUrl, String navTabId) throws Exception;
	/**
	 * 将excel数据导入DB:
	 * 
	 * @Description: CHRS2.0 SI PROJECT
	 * @author weizhengchen
	 * @date 2014.6.10
	 * @version V1.0
	 */
	public ModelMap importExcelData2(HttpServletRequest request,
			HttpServletResponse response, LinkedHashMap map, ModelMap modelMap, String forwardUrl, String navTabId) throws Exception;
	/**
	 * 根据导入的报表数据 更新对应的信息
	 * @param List
	 * @author weizhengchen
	 * @return
	 */
	public void updateImportData(Object obj);
	
	/**
	 * 分析excel的各个列，获取各个列的值，修改对应数据库的对应值:
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param modelMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ModelMap importUpdateData(HttpServletRequest request,
			HttpServletResponse response, LinkedHashMap[] map, ModelMap modelMap, List list,List list1,int num,String aliasNullStr);

	public void exportExcelMoreSheet(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap,
			LinkedHashMap sqlContentmap, List aliasNameList, List aliasList, List mapNameList, List mapList);
	
	
	/**
	 * 导出数据到excel Description:export datas to excel
	 * 单独导出参考列到sheet，可以设置文件名
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 * @author weizhengchen
	 * @date 2014-07-09
	 */
	public void exportExcelMoreSheet(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap,
			LinkedHashMap sqlContentmap, List aliasNameList, List aliasList, List mapNameList, List mapList, String name);
	
	/**
	 * 导出数据到excel Description:export datas to excel
	 * 单独导出参考列到sheet，可以设置文件名
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 * @author weizhengchen
	 * @date 2014-07-09
	 */
	public void exportPaiQianDiModelMoreSheet(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap,
			LinkedHashMap sqlContentmap, List aliasNameList, List aliasList, List mapNameList, List mapList, String name);
	
	/**
	 * 导出数据到excel Description:export datas to excel
	 * 单独导出参考列到sheet，可以设置文件名
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 * @author weizhengchen
	 * @date 2014-07-09
	 */
	public void exportPaForLeftMenModelMoreSheet(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap,
			LinkedHashMap sqlContentmap, List aliasNameList, List aliasList, List mapNameList, List mapList, String name);
	/**
	 * 导出数据到excel Description:export datas to excel
	 * 单独导出参考列到sheet，可以设置文件名
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 * @author weizhengchen
	 * @date 2014-07-09
	 */
	public void exportExcelMoreSheetWithNo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap,
			LinkedHashMap sqlContentmap, List aliasNameList, List aliasList, List mapNameList, List mapList, String name);
	
	/**
	 * 导出数据到excel Description:export datas to excel
	 * 单独导出参考列到sheet，可以设置文件名,每个sheet中有两列数据或者一列
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 * @author weizhengchen
	 * @date 2014-07-09
	 */
	public void exportExcelMoreSheetAndMoreContent(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap,
			LinkedHashMap sqlContentmap, List aliasNameList, List aliasList, List mapNameList, List mapList, String name);

	
	/**
	 * 导出数据到excel Description:export datas to excel
	 * 标题上可以加Tip说明
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	public void exportExcelByNameWithHeaderTip(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap,
			LinkedHashMap sqlContentmap, List aliasNameList, List aliasList,
			String name, List tipList) throws Exception;
	
    /**
	 * 将excel sales incentive 数据导入DB:
	 * 
	 * @Description: CHRS2.0 SI PROJECT
	 * @author penghaixia
	 * @date 2014.6.10
	 * @version V1.0
	 */
	public ModelMap importExcelSalesInctData(HttpServletRequest request,
			HttpServletResponse response, LinkedHashMap map, ModelMap modelMap) throws Exception;
	/**
	 * 导出数据到excel Description:export datas to excel
	 * 标题上可以加Tip说明
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	public ModelMap importExcelPromotoGradeData(HttpServletRequest request,
			HttpServletResponse response, LinkedHashMap map, ModelMap modelMap) throws Exception;
	
	public void setFileName(String filename);

	public void setPath(String path);

	/**
	 * 查询excel导出需要填充的下拉列表值
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author zhengxiaochen zhengxiaochen@ait.net.cn
	* @date 2013-8-29 下午02:57:43 
	* @version V1.0
	 */
	@SuppressWarnings("unchecked")
	public List getExcelExportTypeListByLeave(Map paramMap)throws Exception;

	public void exportExcelTwoSheetByLeave(HttpServletRequest request,HttpServletResponse response, ModelMap modelMap,LinkedHashMap sqlContentmap,List aliasNameList,List aliasList,List sheet2List,String name)throws Exception;
	
	@SuppressWarnings("unchecked")
	public LinkedHashMap importIsParamData(HttpServletRequest request,HttpServletResponse response,LinkedHashMap map,ModelMap modelMap,String forwardUrl,String navTabId);

	/**
	 * 导出数据到excel Description:export datas to excel
	 * 单独导出参考列到sheet，可以设置文件名,sheet1可写备注
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 * @author li huihua
	 * @date 2014-08-19
	 */
	public void exportExcelMoreSheetWithHeaderTip(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap,
			LinkedHashMap sqlContentmap, List aliasNameList, List aliasList, List mapNameList, List mapList, String name, List tipList);
	
	/**
	 * 导出数据到excel Description:export datas to excel
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author weizhengchen
	 * @date 2014-07-09
	 */
	@SuppressWarnings({ "unchecked", "deprecation" })
	public void exportExcelMoreSheet2(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap,LinkedHashMap sqlContentmap, 
			List aliasNameList, List aliasList,List mapNameList,List mapList, String excelName) ;
	
	/**
	 * 将excel数据导入DB:
	 * 
	 * @Description: CHRS2.0 SI PROJECT
	 * @author PengHaixia
	 * @date 2014.10.10
	 * @version V1.0
	 */
	public ModelMap importExcelTempEmpResignData(HttpServletRequest request,
			HttpServletResponse response, LinkedHashMap map, ModelMap modelMap) throws Exception;
	
	public void exportExcelMoreTab(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap,
			List aliasValueList , List aliasNameList, List aliasColList,
			String name, String[] sheets, Map paramMap) throws Exception;
	/**
	 * 将excel数据导入DB:临时职发令
	 * 
	 * @Description: CHRS2.0 SI PROJECT
	 * @author PengHaixia
	 * @date 2014.10.10
	 * @version V1.0
	 */
	public ModelMap importExcelTempEmpTransferOrderData(HttpServletRequest request,
			HttpServletResponse response, LinkedHashMap map, ModelMap modelMap) throws Exception;
	public ModelMap importExcelReguEmpTransferOrderData(HttpServletRequest request,
			HttpServletResponse response, LinkedHashMap map, ModelMap modelMap) throws Exception;
	/**
	 * 导出数据到excel并加密 为excel自动报表服务，数字类型在excel中为数字类型。
	 * 
	 * @Description: CHRS2.0 SI PROJECT
	 * @author PengHaixia
	 * @date 2015.01.23
	 * @version V1.0
	 */
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	public void exportExcelByNamePwdForDISC(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap,
			List aliasValueList , List aliasNameList, String[] columns,
			String name,Map paramMap) throws Exception;
	
	public void importDataTest(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> param) throws Exception;

	public void imporExceltDataTest(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> param)
			throws Exception;

	/**
	 * 将excel的数据导入到数据库中
	 * 
	 * @Description: CHRS2.0 SI PROJECT
	 * @author weizhengchen
	 * @date 2014.07.09
	 * @version V1.0
	 */
	@SuppressWarnings("unchecked")
	public ModelMap importExcelData(HttpServletRequest request,
			HttpServletResponse response, LinkedHashMap map, ModelMap modelMap);
}