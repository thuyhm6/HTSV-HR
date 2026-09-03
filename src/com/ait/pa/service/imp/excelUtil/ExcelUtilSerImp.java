package com.ait.pa.service.imp.excelUtil;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ait.pa.service.imp.excelUtil.jxlcompat.Cell;
import com.ait.pa.service.imp.excelUtil.jxlcompat.CellType;
import com.ait.pa.service.imp.excelUtil.jxlcompat.DateCell;
import com.ait.pa.service.imp.excelUtil.jxlcompat.Sheet;
import com.ait.pa.service.imp.excelUtil.jxlcompat.Workbook;

import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFComment;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.ait.pa.dao.ExcelUtilDao;
import com.ait.pa.dao.PaResultDao;
import com.ait.pa.service.excelUtil.ExcelUtilSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.dao.BasicMaintenanceDao;
import com.ait.web.config.ConfigurationException;
import com.ait.web.i18n.TipMessage;
import com.ait.web.messages.Messages;
import com.ait.web.util.DateUtil;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.ReportUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;
import com.ait.web.util.UserConfiguration;
import com.ait.web.util.uploadpicture.uploadExcel;

/**
 * Copyright: LDCC (c) Company: LDCC
 * 
 * @fileName ExcelUtilSerImp.java
 * @author zhouyeqing(zhouyeqing@ait.net.cn)
 * @Date 2012-3-29 下午08:32:54
 * @version 5.0
 * 
 */
@Service
public class ExcelUtilSerImp implements ExcelUtilSer {

	Logger logger = Logger.getLogger(ExcelUtilSerImp.class);

	/** Compat shim for the old org.apache.poi.hssf.util.Region(rowFrom, colFrom, rowTo, colTo) constructor,
	 * whose argument order differs from CellRangeAddress(firstRow, lastRow, firstCol, lastCol). */
	private static CellRangeAddress region(int rowFrom, int colFrom, int rowTo, int colTo) {
		return new CellRangeAddress(rowFrom, rowTo, colFrom, colTo);
	}

	private static final String CONTENT_TYPE = "text/html; charset=UTF-8";

	public static UserConfiguration config = UserConfiguration
			.getInstance("/system.properties");
	@Autowired
	private BasicMaintenanceDao basicMaintenanceDao;
	@Autowired
	private ExcelUtilDao excelUtilDao;

	String filename = "";

	String path = "";

	String pathCpnyID = "";

	String item_no = "";

	String type = "";

	String language = "";

	@Autowired
	private PaResultDao paResultDao;

	@SuppressWarnings("unchecked")
	public List exportPersonalList(Object obj) {
		return this.excelUtilDao.exportPersonalList(obj);

	}

	public int checkPersonalInfo(Object name) {
		return this.excelUtilDao.checkPersonalInfo(name);
	}

	public int checkSpecialEmpImportCnt(Object name) {
		return this.excelUtilDao.checkSpecialEmpImportCnt(name);
	}

	public int deleteSummaryInfo(Object name) {
		try {
			this.excelUtilDao.deleteSummaryInfo(name);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	public int deleteScheduleInfo(Object name) {
		try {
			this.excelUtilDao.deleteScheduleInfo(name);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}

		return 1;
	}

	/**
	 * 删除已存在的保险信息
	 * 
	 * @param name
	 * @return
	 */
	public void deleteIsParamDateInfo(Object name) {
		try {
			this.excelUtilDao.deleteIsParamDateInfo(name);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int checkPaProgressLock(Object name) {
		return this.excelUtilDao.checkPaProgressLock(name);
	}

	public int getCodeNoByName(Object name) {
		return this.excelUtilDao.getCodeNoByName(name);
	}
	
	public String queryAgreeno() {
		return this.excelUtilDao.queryAgreeno();
	}
	
	public void insertEduTrainAgreement(Object obj) {
		this.excelUtilDao.insertEduTrainAgreement(obj);
	}
	public int insertOTLimit(Object obj) {
		this.excelUtilDao.insertOTLimit(obj);
		return 1;
	}
	public int insertTrainResult(Object obj) {
		this.excelUtilDao.insertTrainResult(obj);
		return 1;
	}
	public int insertTeacherEvaluate(Object obj) {
		this.excelUtilDao.insertTeacherEvaluate(obj);
		return 1;
	}
	public int insertVacPlan(Object obj) {
		this.excelUtilDao.insertVacPlan(obj);
		return 1;
	}
	
	public void updateEduFreeEmployee(Object obj) {
		this.excelUtilDao.updateEduFreeEmployee(obj);
	}
	
	public void updateEduTrainSyllabus(Object obj) {
		this.excelUtilDao.updateEduTrainSyllabus(obj);
	}
	
	public void deleteEduTrainSyllabus(Object obj) throws Exception {
		this.excelUtilDao.deleteEduTrainSyllabus(obj);
	}
	
	public void deleteWithTarget(Object obj, String target) {
		this.excelUtilDao.deleteWithTarget(obj, target);
	}
	
	public void insertWithTarget(Object obj, String target) {
		this.excelUtilDao.insertWithTarget(obj, target);
	}

	@SuppressWarnings("unchecked")
	public List getContentNoByFiled(Object filed) {
		return this.excelUtilDao.getContentNoByFiled(filed);
	}

	public String getArItemID(Object name) {
		return this.excelUtilDao.getArItemID(name);
	}

	/**
	 * 将excel的数据导入到数据库中
	 */
	public void insertExcelData(Object obj) throws Exception {
		this.excelUtilDao.insertExcelData(obj);

	}

	/**
	 * 将excel的数据导入到数据库中的ess_apply_ot_temp
	 */
	public void insertExcelDataOtApply(Object obj) throws Exception {
		this.excelUtilDao.insertExcelDataOtApply(obj);
	}

	/**
	 * 将excel的数据导入到数据库中的ess_apply_ot_temp
	 */
	public void insertExcelDataPaForLeftApply(Object obj) throws Exception {
		this.excelUtilDao.insertExcelDataPaForLeftApply(obj);
	}

	/**
	 * 根据导入的报表数据 更新对应的信息
	 * 
	 * @param List
	 * @author weizhengchen
	 * @return
	 */
	public void updateImportData(Object obj) {
		this.excelUtilDao.updateImportData(obj);
	}

	@SuppressWarnings("unchecked")
	public List getExcelExportDataList(Object obj) {
		return this.excelUtilDao.getExcelExportDataList(obj);
	}

	// 历史工资信息导出Excel专用2012-09-10(Lufeng)
	@SuppressWarnings("unchecked")
	public List getPaHistoryDataList(Object obj) {
		return this.excelUtilDao.getPaHistoryDataList(obj);
	}
	
	@SuppressWarnings("unchecked")
	public List trainAgreementTemp(Object obj) {
		return this.excelUtilDao.trainAgreementTemp(obj);
	}

	/**
	 * 将表名，各个列的值，以及各个列的类型整合到同一个Map中，调用导入方法时，不用动 Description:
	 * 
	 * @param tableName
	 *            要插入的表的表名
	 * @param aliasValueMap
	 *            各个列的值，对应excel的列用CELL+J(J为第几列)
	 * @param aliasTypeMap
	 *            各个列的类型
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public LinkedHashMap putIntoAliasValueAndTypeMap(String tableName,
			LinkedHashMap aliasValueMap, LinkedHashMap aliasTypeMap,
			LinkedHashMap aliasValueI18nMap) {
		LinkedHashMap aliasValueAndTypeMap = new LinkedHashMap();
		aliasValueAndTypeMap.put("tableName", tableName);
		aliasValueAndTypeMap.put("aliasValueMap", aliasValueMap);
		aliasValueAndTypeMap.put("aliasTypeMap", aliasTypeMap);
		aliasValueAndTypeMap.put("aliasValueI18nMap", aliasValueI18nMap);
		return aliasValueAndTypeMap;
	}

	/**
	 * 分析excel的各个列，获取各个列的值，并且导入的数据库中 Description:
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param modelMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ModelMap importData(HttpServletRequest request,
			HttpServletResponse response, LinkedHashMap map, ModelMap modelMap) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String adminID = admin.getAdminID() == null ? "USERNO_"
				+ admin.getUserNo() : admin.getAdminID();
		ObjectBindUtil.getRequestParamData(request);
		this.filename = adminID;
		this.pathCpnyID = admin.getCpnyId();
		this.language = admin.getLanguage();
		if (this.processUploadFile(request, response)) {
			int returnInt = this.impDataBase(request, adminID, map);
			if (returnInt == 0) {
				modelMap.put("sign", "0");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.importfail", request));// 导入失败!
			} else if (returnInt < 0) {
				modelMap.put("sign", "-1");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.writebyrule", request));// 调整事由有误，请按规则填写！
			} else if (returnInt == 2) {
				modelMap.put("sign", "2");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.existnull", request));// 表有空值或有空单元行，请检查！
			} else if (returnInt == 1) {
				modelMap.put("sign", "1");
				modelMap.put("statusCode", "200");
				modelMap.put("rel", "viewInsuranceInputItemDataViewApplyMark");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.importsuccess", request));// 导入成功
			} else if (returnInt == 5) {
				modelMap.put("sign", "-1");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.importfailcalladmin",
						request));// 更新此人上一条数据出错，请联系管理员
			} else if (returnInt == 6) {
				modelMap.put("sign", "-1");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"alert.message.pa.bonus.thisDataIsExist_add_fail",
						request));// 此数据已经存在，请核对数据后再进行导入!
			}
			File file = new File(this.path + "\\" + filename + ".xls");
			file.delete();
		} else {
			modelMap.put("sign", "0");
			modelMap.put("statusCode", "300");
			modelMap.put("message", TipMessage.getTipMessage(
					"ar.alert.message.excelimport.importfail", request));// 导入失败!
		}
		return modelMap;
	}

	/**
	 * 分析excel的各个列，获取各个列的值，并且导入的数据库中 Description:
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param modelMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ModelMap importOtData(HttpServletRequest request,
			HttpServletResponse response, LinkedHashMap map, ModelMap modelMap,
			String forwardUrl, String navTabId) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String adminID = admin.getAdminID() == null ? "USERNO_"
				+ admin.getUserNo() : admin.getAdminID();
		ObjectBindUtil.getRequestParamData(request);
		this.filename = adminID;
		this.pathCpnyID = admin.getCpnyId();
		this.language = admin.getLanguage();
		if (this.processUploadFile(request, response)) {
			int returnInt = this.impDataOtApply(request, adminID, map);
			if (returnInt == 0) {
				modelMap.put("sign", "0");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.importfail", request));// 导入失败!
			} else if (returnInt < 0) {
				modelMap.put("sign", "-1");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.writebyrule", request));// 调整事由有误，请按规则填写！
			} else if (returnInt == 2) {
				modelMap.put("sign", "2");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.existnull", request));// 表有空值或有空单元行，请检查！
			} else if (returnInt == 1) {
				modelMap.put("sign", "1");
				modelMap.put("statusCode", "200");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.importsuccess", request));// 导入成功
				modelMap.put("navTabId", navTabId);
				modelMap.put("forwardUrl", forwardUrl);
			} else if (returnInt == 5) {
				modelMap.put("sign", "-1");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.importfailcalladmin",
						request));// 更新此人上一条数据出错，请联系管理员
			} else if (returnInt == 6) {
				modelMap.put("sign", "-1");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"alert.message.pa.bonus.thisDataIsExist_add_fail",
						request));// 此数据已经存在，请核对数据后再进行导入!
			}
			File file = new File(this.path + "\\" + filename + ".xls");
			file.delete();
		} else {
			modelMap.put("sign", "0");
			modelMap.put("statusCode", "300");
			modelMap.put("message", TipMessage.getTipMessage(
					"ar.alert.message.excelimport.importfail", request));// 导入失败!
		}
		return modelMap;
	}

	/**
	 * 分析excel的各个列，获取各个列的值，并且导入的数据库中 Description:
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param modelMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ModelMap importPaForLeftData(HttpServletRequest request,
			HttpServletResponse response, LinkedHashMap map, ModelMap modelMap,
			String forwardUrl, String navTabId) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String adminID = admin.getAdminID() == null ? "USERNO_"
				+ admin.getUserNo() : admin.getAdminID();
		ObjectBindUtil.getRequestParamData(request);
		this.filename = adminID;
		this.pathCpnyID = admin.getCpnyId();
		this.language = admin.getLanguage();
		if (this.processUploadFile(request, response)) {
			int returnInt = this.impDataPaForLeftApply(request, adminID, map);
			if (returnInt == 0) {
				modelMap.put("sign", "0");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.importfail", request));// 导入失败!
			} else if (returnInt < 0) {
				modelMap.put("sign", "-1");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.writebyrule", request));// 调整事由有误，请按规则填写！
			} else if (returnInt == 2) {
				modelMap.put("sign", "2");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.existnull", request));// 表有空值或有空单元行，请检查！
			} else if (returnInt == 1) {
				modelMap.put("sign", "1");
				modelMap.put("statusCode", "200");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.importsuccess", request));// 导入成功
				modelMap.put("navTabId", navTabId);
				modelMap.put("forwardUrl", forwardUrl);
			} else if (returnInt == 5) {
				modelMap.put("sign", "-1");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.importfailcalladmin",
						request));// 更新此人上一条数据出错，请联系管理员
			} else if (returnInt == 6) {
				modelMap.put("sign", "-1");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"alert.message.pa.bonus.thisDataIsExist_add_fail",
						request));// 此数据已经存在，请核对数据后再进行导入!
			}
			File file = new File(this.path + "\\" + filename + ".xls");
			file.delete();
		} else {
			modelMap.put("sign", "0");
			modelMap.put("statusCode", "300");
			modelMap.put("message", TipMessage.getTipMessage(
					"ar.alert.message.excelimport.importfail", request));// 导入失败!
		}
		return modelMap;
	}

	/**
	 * 分析excel的各个列，获取各个列的值，并且导入的数据库中 Description:
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param modelMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ModelMap importInsCalcObjectData(HttpServletRequest request,
			HttpServletResponse response, LinkedHashMap map, ModelMap modelMap,
			String forwardUrl, String navTabId) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String adminID = admin.getAdminID() == null ? "USERNO_"
				+ admin.getUserNo() : admin.getAdminID();
		ObjectBindUtil.getRequestParamData(request);
		this.filename = adminID;
		this.pathCpnyID = admin.getCpnyId();
		this.language = admin.getLanguage();
		if (this.processUploadFile(request, response)) {
			int returnInt = this.impDataInsObject(request, adminID, map);
			if (returnInt == 0) {
				modelMap.put("sign", "0");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.importfail", request));// 导入失败!
			} else if (returnInt == 2) {
				modelMap.put("sign", "2");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.existnull", request));// 表有空值或有空单元行，请检查！
			} else if (returnInt == 1) {
				modelMap.put("sign", "1");
				modelMap.put("statusCode", "200");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.importsuccess", request));// 导入成功
				modelMap.put("navTabId", navTabId);
				modelMap.put("forwardUrl", forwardUrl);
			}
			File file = new File(this.path + "\\" + filename + ".xls");
			file.delete();
		} else {
			modelMap.put("sign", "0");
			modelMap.put("statusCode", "300");
			modelMap.put("message", TipMessage.getTipMessage(
					"ar.alert.message.excelimport.importfail", request));// 导入失败!
		}
		return modelMap;
	}

	/**
	 * 分析excel的各个列，获取各个列的值，并且导入的数据库中 Description:
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param modelMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ModelMap importFundCalcObjectData(HttpServletRequest request,
			HttpServletResponse response, LinkedHashMap map, ModelMap modelMap,
			String forwardUrl, String navTabId) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String adminID = admin.getAdminID() == null ? "USERNO_"
				+ admin.getUserNo() : admin.getAdminID();
		ObjectBindUtil.getRequestParamData(request);
		this.filename = adminID;
		this.pathCpnyID = admin.getCpnyId();
		this.language = admin.getLanguage();
		if (this.processUploadFile(request, response)) {
			// int returnInt = this.importFundCalcObjectData(request, adminID,
			// map);
			int returnInt = this.importTempDataLF(request, adminID, map,
					"FUND_IMPORT");
			if (returnInt == 0) {
				modelMap.put("sign", "0");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.importfail", request));// 导入失败!
			} else if (returnInt == 2) {
				modelMap.put("sign", "2");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.existnull", request));// 表有空值或有空单元行，请检查！
			} else if (returnInt == 1) {
				modelMap.put("sign", "1");
				modelMap.put("statusCode", "200");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.importsuccess", request));// 导入成功
				modelMap.put("navTabId", navTabId);
				modelMap.put("forwardUrl", forwardUrl);
			}
			File file = new File(this.path + "\\" + filename + ".xls");
			file.delete();
		} else {
			modelMap.put("sign", "0");
			modelMap.put("statusCode", "300");
			modelMap.put("message", TipMessage.getTipMessage(
					"ar.alert.message.excelimport.importfail", request));// 导入失败!
		}
		return modelMap;
	}

	/**
	 * 分析excel的各个列，获取各个列的值，并且导入的数据库中 Description:
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param modelMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ModelMap importData2(HttpServletRequest request,
			HttpServletResponse response, LinkedHashMap map, ModelMap modelMap,
			List list, List list1, int num) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String adminID = admin.getAdminID() == null ? "USERNO_"
				+ admin.getUserNo() : admin.getAdminID();
		ObjectBindUtil.getRequestParamData(request);
		this.filename = adminID;
		this.pathCpnyID = admin.getCpnyId();
		this.language = admin.getLanguage();
		String resultList = "";
		if (this.processUploadFile(request, response)) {
			LinkedHashMap resultMap = this.impDataBase2(request, adminID, map,
					list, list1, num);
			List validateCellList = (List) resultMap.get("validateCellList");
			int returnInt = -1;
			if (validateCellList.size() > 0) {
				returnInt = 0;
				for (int i = 0; i < validateCellList.size(); i++) {
					resultList += validateCellList.get(i) + "\\n";
				}
			} else {
				returnInt = 1;
			}
			if (returnInt == 0) {
				modelMap.put("sign", "0");
				modelMap.put("statusCode", "300");
				if (validateCellList.size() > 0) {
					modelMap.put("message", resultList);// 导入失败!
					modelMap.put("type", "0");
					modelMap.put("resultList", resultList);
				} else {

					modelMap
							.put("message", TipMessage.getTipMessage(
									"ar.alert.message.excelimport.importfail",
									request));// 导入失败!
				}
			} else if (returnInt < 0) {
				modelMap.put("sign", "-1");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.writebyrule", request));// 调整事由有误，请按规则填写！
			} else if (returnInt == 2) {
				modelMap.put("sign", "2");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.existnull", request));// 表有空值或有空单元行，请检查！
			} else if (returnInt == 1) {
				modelMap.put("sign", "1");
				modelMap.put("statusCode", "200");
				modelMap.put("rel", "viewInsuranceInputItemDataViewApplyMark");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.importsuccess", request));// 导入成功
			} else if (returnInt == 5) {
				modelMap.put("sign", "-1");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.importfailcalladmin",
						request));// 更新此人上一条数据出错，请联系管理员
			} else if (returnInt == 6) {
				modelMap.put("sign", "-1");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"alert.message.pa.bonus.thisDataIsExist_add_fail",
						request));// 此数据已经存在，请核对数据后再进行导入!
			}
			File file = new File(this.path + "\\" + filename + ".xls");
			file.delete();
		} else {
			modelMap.put("sign", "0");
			modelMap.put("statusCode", "300");
			modelMap.put("message", TipMessage.getTipMessage(
					"ar.alert.message.excelimport.importfail", request));// 导入失败!
		}
		return modelMap;
	}

	@SuppressWarnings("deprecation")
	public boolean processUploadFile(HttpServletRequest request,
			HttpServletResponse response) {
		boolean b = true;

		// 设置文件在服务器上的路径
		this.path = request.getRealPath("");
		int i = path.indexOf("\\");
		String server = "win";
		if (i < 0) { // WebServer的os为unix/linux
			server = "u/l";
		}
		if ("win".equals(server)) {
			path = path + "\\resources\\temp\\files\\" + pathCpnyID + "\\";
		} else {
			path = path + "/resources/temp/files/" + pathCpnyID;
		}
		File file = new File(path);
		if (!file.exists()) {// 目录或文件是否存在
			file.mkdirs();
		}
		try {
			uploadExcel up = new uploadExcel(request, response);
			if (up.getdata()) {
				up.initFileComents();
				up.disposeData(filename);
				up.deletefile(pathCpnyID, filename + ".xls");
				up.setFilePath("", pathCpnyID);
				up.WriteMdata();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}

	@SuppressWarnings( { "unchecked" })
	public int impDataBase(HttpServletRequest request, String adminID,
			LinkedHashMap cellmap) {
		int token = 0;
		int k = path.indexOf("\\");
		String server = "win";
		if (k < 0) { // WebServer的os为unix/linux
			server = "u/l";
		}
		File file = null;
		if ("win".equals(server)) {
			file = new File(path + "\\" + filename + ".xls");
		} else {
			file = new File(path + "/" + filename + ".xls");
		}
		Map dataMap = new HashMap();
		try {
			Workbook workBook = Workbook.getWorkbook(file);
			// 获得第一个工作表对象
			Sheet sheet = workBook.getSheet(0);
			// 验证是否存在导入数据
			if (sheet.getRows() < 2) {
				logger.debug("Import file not exist data, return.");
				return token;
			}
			String tableName = cellmap.get("tableName") == null ? "" : cellmap
					.get("tableName").toString();// 获取表名
			LinkedHashMap aliasValueMap = cellmap.get("aliasValueMap") == null ? null
					: (LinkedHashMap) cellmap.get("aliasValueMap");
			LinkedHashMap aliasTypeMap = cellmap.get("aliasTypeMap") == null ? null
					: (LinkedHashMap) cellmap.get("aliasTypeMap");
			LinkedHashMap aliasValueI18nMap = cellmap.get("aliasValueI18nMap") == null ? null
					: (LinkedHashMap) cellmap.get("aliasValueI18nMap");
			String field2_value = aliasValueMap.get("FIELD2_VALUE") == null ? ""
					: aliasValueMap.get("FIELD2_VALUE").toString();
			String aliasNullStr = cellmap.get("aliasNullStr") == null ? ""
					: cellmap.get("aliasNullStr").toString();

			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			// 判断导入的是否为已有记录----LM
			boolean isUpdate = false;
			for (int m = 1; m < sheet.getRows(); m++) {
				Cell[] rowTemp = sheet.getRow(m);
				if (rowTemp[0] == null
						|| rowTemp[0].getContents().trim().equals("")) {
					continue;
				}
				/*
				 * if (!validateCell(sheet.getRow(m), sheet.getRow(m).length))
				 * {// 如果所在行全为空，不导入 break; }
				 */

				// 检查工资，保险，奖金，工资基础数据中是否存在要导入的的数据，如果存在，即更新其结束日期 (2013-09-26 修改
				// IS_PARAM_DATA导入模板 走单独的IF )
				/*
				 * if ("PA_PARAM_DATA".equals(tableName)) { LinkedHashMap
				 * tempMap = new LinkedHashMap(); if (cellmap.get("Batch") !=
				 * null && cellmap.get("Batch").equals("Y")) {
				 * tempMap.put("EMPID", rowTemp[1].getContents().trim());
				 * tempMap.put("START_MONTH", rowTemp[3].getContents() .trim());
				 * tempMap.put("END_MONTH", rowTemp[4].getContents() .trim());
				 * String paramNo =
				 * "(SELECT T.PARAM_NO FROM PA_PARAM_ITEM_PARAM T, SY_GLOBAL_NAME SY3 WHERE T.PARAM_ITEM_NO = SY3.NO(+)"
				 * + "AND SY3.LANGUAGE(+) = '" + admin.getLanguage() + "'" +
				 * "AND T.Distinct_Field = 'PERSON_ID'" + "AND T.CPNY_ID = '" +
				 * this.pathCpnyID + "'" + "AND T.ACTIVITY = 1" +
				 * "AND SY3.CONTENT = '" + rowTemp[0].getContents().trim() +
				 * "')"; tempMap.put("PARAM_NO", paramNo); } else {
				 * tempMap.put("EMPID", rowTemp[0].getContents().trim());
				 * tempMap.put("START_MONTH", rowTemp[2].getContents() .trim());
				 * tempMap.put("END_MONTH", rowTemp[3].getContents() .trim());
				 * tempMap.put("PARAM_NO", aliasValueMap.get("PARAM_NO")); }
				 * tempMap.put("CPNY_ID", admin.getCpnyId());
				 * tempMap.put("PA_SUPERVISIOR_ID", admin.getPersonId());
				 * tempMap.put("TABLE_NAME", tableName); //工资输入项目数据允许多条存在，只做添加
				 * // if (this.excelUtilDao.checkAddItemDataInfoMonth(tempMap)
				 * == 0) { // if
				 * (this.excelUtilDao.checkAddItemDataInfo(tempMap) > 0) { // if
				 * (this.excelUtilDao // .updateItemDataInfoMonth(tempMap) != 1)
				 * { // return token = 5;// 更新此人上一条数据出错，请联系管理员 // } // } // }
				 * else { // return token = 6;// 此数据已经存在，请核对数据后再进行导入! // } }
				 */
				// 奖金

				if ("BN_PARAM_DATA".equals(tableName)) {
					LinkedHashMap tempMap = new LinkedHashMap();
					tempMap.put("EMPID", rowTemp[0].getContents().trim());
					tempMap.put("START_MONTH", rowTemp[2].getContents().trim());
					tempMap.put("END_MONTH", rowTemp[3].getContents().trim());
					tempMap.put("CPNY_ID", admin.getCpnyId());
					tempMap.put("PA_SUPERVISIOR_ID", admin.getPersonId());
					tempMap.put("TABLE_NAME", tableName);
					tempMap.put("PARAM_NO", aliasValueMap.get("PARAM_NO"));

					if (this.excelUtilDao.checkAddItemDataInfoMonth(tempMap) == 0) {
						if (this.excelUtilDao.checkAddItemDataInfo(tempMap) > 0) {
							if (this.excelUtilDao
									.updateItemDataInfoMonth(tempMap) != 1) {
								return token = 5;// 更新此人上一条数据出错，请联系管理员
							}
						}
					} else {
						return token = 6;// 此数据已经存在，请核对数据后再进行导入!
					}
				}
				if ("IS_PARAM_DATA".equals(tableName)) {
					LinkedHashMap tempMap = new LinkedHashMap();
					if (cellmap.get("Batch") != null
							&& cellmap.get("Batch").equals("Y")) {
						tempMap.put("EMPID", rowTemp[1].getContents().trim());
						tempMap.put("START_MONTH", rowTemp[3].getContents()
								.trim());
						tempMap.put("END_MONTH", rowTemp[4].getContents()
								.trim());
						String paramNo = "(SELECT T.PARAM_NO FROM IS_PARAM_ITEM_PARAM T, SY_GLOBAL_NAME SY3 WHERE T.PARAM_ITEM_NO = SY3.NO(+)"
								+ "AND SY3.LANGUAGE(+) = '"
								+ admin.getLanguage()
								+ "'"
								+ "AND T.Distinct_Field = 'PERSON_ID'"
								+ "AND T.CPNY_ID = '"
								+ this.pathCpnyID
								+ "'"
								+ "AND T.ACTIVITY = 1"
								+ "AND SY3.CONTENT = '"
								+ rowTemp[0].getContents().trim() + "')";
						tempMap.put("PARAM_NO", paramNo);
					} else {
						tempMap.put("EMPID", rowTemp[0].getContents().trim());
						tempMap.put("START_MONTH", rowTemp[2].getContents()
								.trim());
						tempMap.put("END_MONTH", rowTemp[3].getContents()
								.trim());
						tempMap.put("PARAM_NO", aliasValueMap.get("PARAM_NO"));
					}
					tempMap.put("CPNY_ID", admin.getCpnyId());
					tempMap.put("PA_SUPERVISIOR_ID", admin.getPersonId());
					tempMap.put("TABLE_NAME", tableName);

					if (this.excelUtilDao.checkAddItemDataInfoMonth(tempMap) == 0) {
						if (this.excelUtilDao.checkAddItemDataInfo(tempMap) > 0) {
							if (this.excelUtilDao
									.updateItemDataInfoMonth(tempMap) != 1) {
								return token = 5;// 更新此人上一条数据出错，请联系管理员
							}
						}
					} else {
						return token = 6;// 此数据已经存在，请核对数据后再进行导入!
					}
				}

				if ("PA_BASIC_DATA".equals(tableName)) { // ----工资基础项目数据表
					LinkedHashMap tempMap = new LinkedHashMap();
					if (cellmap.get("Batch") != null
							&& cellmap.get("Batch").equals("Y")) {
						tempMap.put("EMPID", rowTemp[1].getContents().trim());
						// tempMap.put("START_DATE",
						// rowTemp[3].getContents().trim());
						String StartDate = rowTemp[3].getContents().trim();
						String[] contentStr = StartDate.split("/");
						if (contentStr.length == 3
								&& contentStr[0].length() == 2
								&& contentStr[2].length() == 4) {
							tempMap.put("START_DATE", StartDate);
						} else {
							SimpleDateFormat format = new SimpleDateFormat(
									"dd/MM/yyyy");
							SimpleDateFormat format1 = new SimpleDateFormat(
									"yyyy/MM/dd");
							tempMap.put("START_DATE", format.format(format1
									.parse(StartDate)));
						}
						tempMap
								.put("END_DATE", rowTemp[4].getContents()
										.trim());
						String paramNo = "(SELECT T.PARAM_NO FROM PA_BASIC_ITEM_PARAM T, SY_GLOBAL_NAME SY3 WHERE T.ITEM_NO = SY3.NO(+) "
								+ "AND SY3.LANGUAGE(+) = '"
								+ admin.getLanguage()
								+ "'"
								+ "AND T.Distinct_Field = 'PERSON_ID'"
								+ "AND T.CPNY_ID = '"
								+ this.pathCpnyID
								+ "' "
								+ "AND SY3.CONTENT = '"
								+ rowTemp[0].getContents().trim() + "')";
						tempMap.put("PARAM_NO", paramNo);
					} else {
						tempMap.put("EMPID", rowTemp[0].getContents().trim());
						String StartDate = rowTemp[2].getContents().trim();
						String[] contentStr = StartDate.split("/");
						if (contentStr.length == 3
								&& contentStr[0].length() == 2
								&& contentStr[2].length() == 4) {
							tempMap.put("START_DATE", StartDate);
						} else {
							SimpleDateFormat format = new SimpleDateFormat(
									"dd/MM/yyyy");
							SimpleDateFormat format1 = new SimpleDateFormat(
									"yyyy/MM/dd");
							tempMap.put("START_DATE", format.format(format1
									.parse(StartDate)));
						}
						tempMap.put("PARAM_NO", aliasValueMap.get("PARAM_NO"));
					}
					tempMap.put("CPNY_ID", admin.getCpnyId());
					tempMap.put("PA_SUPERVISIOR_ID", admin.getPersonId());
					tempMap.put("TABLE_NAME", tableName);
					if (this.excelUtilDao.checkAddItemDataInfoMonth(tempMap) == 0) {
						if (this.excelUtilDao.checkAddItemDataInfo(tempMap) > 0) {
							if (this.excelUtilDao
									.updatePaBasicItemDataInfoMonth(tempMap) != 1) {
								return token = 5;// 更新此人上一条数据出错，请联系管理员!
							}
							;
						}
					} else {
						return token = 6;// 此数据已经存在，请核对数据后再进行导入!
					}
				}
				if (("BN_PARAM_DATA_OTHER".equals(tableName)
						|| "IS_PARAM_DATA_OTHER".equals(tableName) || "PA_PARAM_DATA_OTHER"
						.equals(tableName))
						&& "".equals(field2_value)) {
					String sqlValueI18n = aliasValueI18nMap.get("FIELD1_VALUE") == null ? ""
							: aliasValueI18nMap.get("FIELD1_VALUE").toString();
					String querySql1 = sqlValueI18n.replaceAll("#CELL0#", "'"
							+ rowTemp[0].getContents().trim() + "'");

					LinkedHashMap tempMap = new LinkedHashMap();
					tempMap.put("FIELD1_VALUE", querySql1);
					tempMap.put("FIELD2_VALUE", field2_value);
					tempMap.put("START_MONTH", rowTemp[1].getContents().trim());
					tempMap.put("END_MONTH", rowTemp[2].getContents().trim());
					tempMap.put("CPNY_ID", admin.getCpnyId());
					tempMap.put("UPDATED_BY", admin.getPersonId());
					tempMap.put("TABLE_NAME", tableName);
					tempMap.put("PARAM_NO", aliasValueMap.get("PARAM_NO"));
					if (this.excelUtilDao
							.checkAddItemDataOtherInfoMonth(tempMap) == 0) {
						if (this.excelUtilDao
								.checkAddItemDataOtherInfo(tempMap) > 0) {
							if (this.excelUtilDao
									.updateItemDataOtherInfoMonth(tempMap) != 1) {
								return token = 5;// 更新此人上一条数据出错，请联系管理员
							}
							;
						}
					} else {
						return token = 6;// 此数据已经存在，请核对数据后再进行导入!
					}
				}
				if ("PA_BASIC_DATA_OTHER".equals(tableName)
						&& "".equals(field2_value)) {
					String sqlValueI18n = aliasValueI18nMap.get("FIELD1_VALUE") == null ? ""
							: aliasValueI18nMap.get("FIELD1_VALUE").toString();
					String querySql1 = sqlValueI18n.replaceAll("#CELL0#", "'"
							+ rowTemp[0].getContents().trim() + "'");

					LinkedHashMap tempMap = new LinkedHashMap();
					tempMap.put("FIELD1_VALUE", querySql1);
					tempMap.put("FIELD2_VALUE", field2_value);
					/*
					 * Date date=null;
					 * if(rowTemp[1].getType().DATE==CellType.DATE){
					 * SimpleDateFormat ds = new
					 * SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); DateCell dc =
					 * (DateCell)rowTemp[1]; date = dc.getDate(); }
					 */
					String StartDate = rowTemp[1].getContents().trim();
					String[] contentStr = StartDate.split("/");
					if (contentStr.length == 3 && contentStr[0].length() == 2
							&& contentStr[2].length() == 4) {
						tempMap.put("START_DATE", StartDate);
					} else {
						SimpleDateFormat format = new SimpleDateFormat(
								"dd/MM/yyyy");
						SimpleDateFormat format1 = new SimpleDateFormat(
								"yyyy/MM/dd");
						tempMap.put("START_DATE", format.format(format1
								.parse(StartDate)));
					}
					// rowTemp[1].getType().DATE.g
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					// System.out.println(new Date(startdate));
					// tempMap.put("START_DATE", sdf.format(date));
					tempMap.put("UPDATED_BY", admin.getPersonId());
					tempMap.put("CPNY_ID", admin.getCpnyId());
					tempMap.put("TABLE_NAME", tableName);
					tempMap.put("PARAM_NO", aliasValueMap.get("PARAM_NO"));
					if (this.excelUtilDao
							.checkAddItemDataOtherInfoMonth(tempMap) == 0) {
						if (this.excelUtilDao
								.checkAddItemDataOtherInfo(tempMap) > 0) {
							if (this.excelUtilDao
									.updatePaBasicItemDataOtherInfoMonth(tempMap) != 1) {
								return token = 5;// 更新此人上一条数据出错，请联系管理员
							}
							;
						}
					} else {
						return token = 6;// 此数据已经存在，请核对数据后再进行导入!
					}
				}
				if (("BN_PARAM_DATA_OTHER".equals(tableName)
						|| "IS_PARAM_DATA_OTHER".equals(tableName) || "PA_PARAM_DATA_OTHER"
						.equals(tableName))
						&& !"".equals(field2_value)) {
					String sqlValueI18n = aliasValueI18nMap.get("FIELD1_VALUE") == null ? ""
							: aliasValueI18nMap.get("FIELD1_VALUE").toString();
					String querySql1 = sqlValueI18n.replaceAll("#CELL0#", "'"
							+ rowTemp[0].getContents().trim() + "'");

					String sqlValueI18n2 = aliasValueI18nMap
							.get("FIELD2_VALUE") == null ? ""
							: aliasValueI18nMap.get("FIELD2_VALUE").toString();
					String querySql2 = sqlValueI18n2.replaceAll("#CELL1#", "'"
							+ rowTemp[1].getContents().trim() + "'");
					LinkedHashMap tempMap = new LinkedHashMap();
					tempMap.put("FIELD1_VALUE", querySql1);
					tempMap.put("FIELD2_VALUE", querySql2);
					tempMap.put("CELL0", rowTemp[0].getContents().trim());
					tempMap.put("CELL1", rowTemp[1].getContents().trim());
					tempMap.put("START_MONTH", rowTemp[2].getContents().trim());
					tempMap.put("END_MONTH", rowTemp[3].getContents().trim());
					tempMap.put("CPNY_ID", admin.getCpnyId());
					tempMap.put("UPDATED_BY", admin.getPersonId());
					tempMap.put("TABLE_NAME", tableName);
					tempMap.put("PARAM_NO", aliasValueMap.get("PARAM_NO"));
					if (this.excelUtilDao
							.checkAddItemDataOtherInfoMonth(tempMap) == 0) {
						if (this.excelUtilDao
								.checkAddItemDataOtherInfo(tempMap) > 0) {
							if (this.excelUtilDao
									.updateItemDataOtherInfoMonth(tempMap) != 1) {
								return token = 5;// 更新此人上一条数据出错，请联系管理员
							}
							;
						}
					} else {
						return token = 6;// 此数据已经存在，请核对数据后再进行导入!
					}
				}
				if ("PA_BASIC_DATA_OTHER".equals(tableName)
						&& !"".equals(field2_value)) {
					String sqlValueI18n = aliasValueI18nMap.get("FIELD1_VALUE") == null ? ""
							: aliasValueI18nMap.get("FIELD1_VALUE").toString();
					String querySql1 = sqlValueI18n.replaceAll("#CELL0#", "'"
							+ rowTemp[0].getContents().trim() + "'");
					String sqlValueI18n2 = aliasValueI18nMap
							.get("FIELD2_VALUE") == null ? ""
							: aliasValueI18nMap.get("FIELD2_VALUE").toString();
					String querySql2 = sqlValueI18n2.replaceAll("#CELL1#", "'"
							+ rowTemp[1].getContents().trim() + "'");

					LinkedHashMap tempMap = new LinkedHashMap();
					tempMap.put("FIELD1_VALUE", querySql1);
					tempMap.put("FIELD2_VALUE", querySql2);
					tempMap.put("CELL0", rowTemp[0].getContents().trim());
					tempMap.put("CELL1", rowTemp[1].getContents().trim());
					tempMap.put("START_DATE", rowTemp[2].getContents().trim());
					tempMap.put("START_DATE", rowTemp[3].getContents().trim());
					tempMap.put("CPNY_ID", admin.getCpnyId());
					tempMap.put("UPDATED_BY", admin.getPersonId());
					tempMap.put("TABLE_NAME", tableName);
					tempMap.put("PARAM_NO", aliasValueMap.get("PARAM_NO"));
					if (this.excelUtilDao
							.checkAddItemDataOtherInfoMonth(tempMap) == 0) {
						if (this.excelUtilDao
								.checkAddItemDataOtherInfo(tempMap) > 0) {
							if (this.excelUtilDao
									.updatePaBasicItemDataOtherInfoMonth(tempMap) != 1) {
								return token = 5;// 更新此人上一条数据出错，请联系管理员
							}
							;
						}
					} else {
						return token = 6;// 此数据已经存在，请核对数据后再进行导入!
					}
				}
				// LM 经济补偿金 导入 覆盖操作
				if ("T_PA_ECC_RESULT".equals(tableName)) {
					LinkedHashMap tempMap = new LinkedHashMap();
					String paMonth = rowTemp[0].getContents().trim();
					if (paMonth.length() != 6) {
						return token = 0;// 年月格式错误！
					}
					tempMap.put("PA_MONTH", paMonth);
					tempMap.put("EMPID", rowTemp[1].getContents().trim());
					// tempMap.put("LEFT_TYPE",
					// rowTemp[2].getContents().trim());
					// tempMap.put("ECC_END_DATE",
					// rowTemp[2].getContents().trim());
					// tempMap.put("ECC_AMT", rowTemp[3].getContents().trim());
					// tempMap.put("NOTICE_AMT",
					// rowTemp[4].getContents().trim());
					tempMap.put("CPNY_ID", admin.getCpnyId());
					tempMap.put("batches", aliasValueMap.get("batches"));
					// tempMap.put("", value)
					if (this.excelUtilDao.checkEccImportHasEmp(tempMap) != null
							&& this.excelUtilDao.checkEccImportHasEmp(tempMap) != "") {
						if (this.excelUtilDao.checkAddPaEccInfo(tempMap) > 0) {
							// 更新已有记录？？？？？
							return token = 6;
							// if(this.excelUtilDao.updatePaEccInfo(tempMap)!=1){
							// return token = 5;
							// }else{
							// isUpdate = true;
							// }
						}
					} else {
						return token = 0;
					}
				}

				/**
				 * 基数管理打入临时表
				 * 
				 * @author wendi
				 * */
				if ("PA_BENHS_BASE_IMP".equals(tableName)) {
					LinkedHashMap tempMap = new LinkedHashMap();
					int startDateL = rowTemp[2].getContents().trim().length();

					tempMap.put("PERSON_ID", rowTemp[0].getContents().trim());
					tempMap.put("CHINESENAME", rowTemp[1].getContents().trim());
					tempMap.put("SOCIAL_NO", rowTemp[2].getContents().trim());
					tempMap.put("AVG_SALARY", Float.parseFloat(rowTemp[3]
							.getContents().trim()));
					if (this.excelUtilDao
							.checkAddItemDataOtherInfoMonth(tempMap) == 0) {
						if (this.excelUtilDao
								.checkAddItemDataOtherInfo(tempMap) > 0) {
							if (this.excelUtilDao
									.updateItemDataOtherInfoMonth(tempMap) != 1) {
								return token = 5;// 更新此人上一条数据出错，请联系管理员
							}
							;
						}
					} else {
						return token = 6;// 此数据已经存在，请核对数据后再进行导入!
					}

				}

				/**
				 * 公积金对象增加导入临时表
				 * 
				 * @author wendi
				 * */
				if ("PA_BENHS_MANEGE_ADD_IMP".equals(tableName)) {
					LinkedHashMap tempMap = new LinkedHashMap();
					int startDateL = rowTemp[2].getContents().trim().length();

					tempMap.put("PERSON_ID", rowTemp[0].getContents().trim());
					tempMap.put("CHINESENAME", rowTemp[1].getContents().trim());
					tempMap.put("START_DATE", rowTemp[2].getContents().trim());
					tempMap.put("ENDOWMENT_BASE", Float.parseFloat(rowTemp[3]
							.getContents().trim()));
					// tempMap.put("SOCIAL_NO",
					// rowTemp[2].getContents().trim());
					// tempMap.put("MEDICARE_BASE",
					// Float.parseFloat(rowTemp[3].getContents().trim()));
					// tempMap.put("SHENGYU_BASE",
					// Float.parseFloat(rowTemp[3].getContents().trim()));
					// tempMap.put("COMPO_BASE",
					// Float.parseFloat(rowTemp[3].getContents().trim()));
					// tempMap.put("UNEMP_BASE",
					// Float.parseFloat(rowTemp[3].getContents().trim()));
					// tempMap.put("JOIN_VALUE",
					// Float.parseFloat(rowTemp[3].getContents().trim()));
					if (this.excelUtilDao
							.checkAddItemDataOtherInfoMonth(tempMap) == 0) {
						if (this.excelUtilDao
								.checkAddItemDataOtherInfo(tempMap) > 0) {
							if (this.excelUtilDao
									.updateItemDataOtherInfoMonth(tempMap) != 1) {
								return token = 5;// 更新此人上一条数据出错，请联系管理员
							}
							;
						}
					} else {
						return token = 6;// 此数据已经存在，请核对数据后再进行导入!
					}

				}

				/**
				 * 公积金对象减少导入临时表
				 * 
				 * @author wendi
				 * */
				if ("PA_BENHS_MANEGE_DEL_IMP".equals(tableName)) {
					LinkedHashMap tempMap = new LinkedHashMap();
					int startDateL = rowTemp[2].getContents().trim().length();

					tempMap.put("PERSON_ID", rowTemp[0].getContents().trim());
					tempMap.put("CHINESENAME", rowTemp[1].getContents().trim());
					tempMap.put("END_DATE", rowTemp[2].getContents().trim());
					tempMap.put("ENDOWMENT_BASE", Float.parseFloat(rowTemp[3]
							.getContents().trim()));
					// tempMap.put("SOCIAL_NO",
					// rowTemp[2].getContents().trim());
					// tempMap.put("MEDICARE_BASE",
					// Float.parseFloat(rowTemp[3].getContents().trim()));
					// tempMap.put("SHENGYU_BASE",
					// Float.parseFloat(rowTemp[3].getContents().trim()));
					// tempMap.put("COMPO_BASE",
					// Float.parseFloat(rowTemp[3].getContents().trim()));
					// tempMap.put("UNEMP_BASE",
					// Float.parseFloat(rowTemp[3].getContents().trim()));
					// tempMap.put("JOIN_VALUE",
					// Float.parseFloat(rowTemp[3].getContents().trim()));
					if (this.excelUtilDao
							.checkAddItemDataOtherInfoMonth(tempMap) == 0) {
						if (this.excelUtilDao
								.checkAddItemDataOtherInfo(tempMap) > 0) {
							if (this.excelUtilDao
									.updateItemDataOtherInfoMonth(tempMap) != 1) {
								return token = 5;// 更新此人上一条数据出错，请联系管理员
							}
							;
						}
					} else {
						return token = 6;// 此数据已经存在，请核对数据后再进行导入!
					}

				}
				/**
				 * 公积金对象管理导入临时表
				 * 
				 * @author wendi
				 * */
				if ("PA_BENHS_MANEGE_IMP".equals(tableName)) {
					LinkedHashMap tempMap = new LinkedHashMap();
					int startDateL = rowTemp[2].getContents().trim().length();

					tempMap.put("PERSON_ID", rowTemp[0].getContents().trim());
					tempMap.put("CHINESENAME", rowTemp[1].getContents().trim());
					tempMap.put("SOCIAL_NO", rowTemp[2].getContents().trim());
					tempMap.put("ENDOWMENT_BASE", Float.parseFloat(rowTemp[3]
							.getContents().trim()));
					// tempMap.put("SOCIAL_NO",
					// rowTemp[2].getContents().trim());
					// tempMap.put("MEDICARE_BASE",
					// Float.parseFloat(rowTemp[3].getContents().trim()));
					// tempMap.put("SHENGYU_BASE",
					// Float.parseFloat(rowTemp[3].getContents().trim()));
					// tempMap.put("COMPO_BASE",
					// Float.parseFloat(rowTemp[3].getContents().trim()));
					// tempMap.put("UNEMP_BASE",
					// Float.parseFloat(rowTemp[3].getContents().trim()));
					// tempMap.put("JOIN_VALUE",
					// Float.parseFloat(rowTemp[3].getContents().trim()));
					if (this.excelUtilDao
							.checkAddItemDataOtherInfoMonth(tempMap) == 0) {
						if (this.excelUtilDao
								.checkAddItemDataOtherInfo(tempMap) > 0) {
							if (this.excelUtilDao
									.updateItemDataOtherInfoMonth(tempMap) != 1) {
								return token = 5;// 更新此人上一条数据出错，请联系管理员
							}
							;
						}
					} else {
						return token = 6;// 此数据已经存在，请核对数据后再进行导入!
					}
				}
				// 公会管理 导入 覆盖操作
				if ("HR_LABOR_UNION".equals(tableName)) {
					LinkedHashMap tempMap = new LinkedHashMap();
					int startDateL = rowTemp[2].getContents().trim().length();

					tempMap.put("EMPID", rowTemp[0].getContents().trim());
					tempMap.put("NAME", rowTemp[1].getContents().trim());
					tempMap.put("START_DATE", rowTemp[2].getContents().trim());
					tempMap.put("END_DATE", rowTemp[3].getContents().trim());

					tempMap.put("CPNY_ID", admin.getCpnyId());
					tempMap.put("batches", aliasValueMap.get("batches"));
					// tempMap.put("", value)
					if (this.excelUtilDao.checkEccImportHasEmp(tempMap) != null
							&& this.excelUtilDao.checkEccImportHasEmp(tempMap) != "") {
						if (this.excelUtilDao.insertHrLaborUnion(tempMap) > 0) {
							return token = 1;
							// if(this.excelUtilDao.updatePaEccInfo(tempMap)!=1){
							// return token = 5;
							// }else{
							// isUpdate = true;
							// }
						}
					} else {
						return token = 0;
					}
				}
			}
			// 派遣地发令导入数据
			LinkedHashMap aliasMapCells = new LinkedHashMap();
			if (aliasValueMap != null && aliasTypeMap != null) {// 存放CELL0：NUMBER类型的键值对
				Set keySet = aliasValueMap.keySet();
				if (keySet != null) {
					Iterator it = keySet.iterator();
					while (it.hasNext()) {
						Object keyvalue = it.next();
						if (keyvalue != null) {
							Object aliasValue = aliasValueMap.get(keyvalue);
							if (aliasValue != null) {
								aliasMapCells.put(aliasValue.toString(),
										aliasTypeMap.get(keyvalue));
							}
						}
					}
				}
			}

			List addList = new ArrayList();
			// 循环工作表的每行数据
			int init = 0;
			for (int i = 1; i < sheet.getRows(); i++) {
				// 如果是可以为空的列就不进行空列的判断
				// 验证当前行是否符合规范
				if (!validateCell(sheet.getRow(i), sheet.getRow(0).length,
						aliasNullStr)) {// 如果所在行全为空，不导入
					break;
				}

				Cell[] row = sheet.getRow(i);
				if (row[0] == null || row[0].getContents().trim().equals("")) {
					continue;
				}

				if (init == 0) {
					init = sheet.getRow(0).length;
				}
				int aaa = sheet.getRow(1).length;
				LinkedHashMap smap = new LinkedHashMap();
				LinkedHashMap aliasCellmap = new LinkedHashMap();
				String firstCellValue = "";
				for (int j = 0; j < init; j++) {
					try {
						String cellValue = "";
						Object cellType = aliasMapCells.get("#CELL" + j + "#");
						String content = "";
						// if(isUpdate){
						// continue;
						// }
						if (j == 0) {
							firstCellValue = row[0].getContents().trim();
						}
						if (aliasNullStr.indexOf("," + j + ",") < 0) {
							content = StringUtil
									.checkNull(row[j].getContents());
							if (cellType != null && !content.equals("")) {// date类型
								try {
									cellValue = this.FormateData(row[j],
											cellType);
									if (cellValue.contains("/")) {
										String[] contentStr = cellValue
												.split("/");
										if (contentStr.length == 3
												&& contentStr[0].length() == 2
												&& contentStr[2].length() == 4) {
											cellValue = contentStr[2] + "/"
													+ contentStr[1] + "/"
													+ contentStr[0];
										}
									} else if (cellValue.contains("-")) {
										String[] contentStr = cellValue
												.split("-");
										if (contentStr.length == 3
												&& contentStr[0].length() == 2
												&& contentStr[2].length() == 4) {
											cellValue = contentStr[2] + "-"
													+ contentStr[1] + "-"
													+ contentStr[0];
										}
									}
								} catch (Exception e) {
									cellValue = "";
									if (row[j].getContents() != null) {
										cellValue = row[j].getContents();
										if (cellValue.contains("/")) {
											String[] contentStr = cellValue
													.split("/");
											if (contentStr.length == 3
													&& contentStr[0].length() == 2
													&& contentStr[2].length() == 4) {
												cellValue = contentStr[2] + "/"
														+ contentStr[1] + "/"
														+ contentStr[0];
											}
										} else if (cellValue.contains("-")) {
											String[] contentStr = cellValue
													.split("-");
											if (contentStr.length == 3
													&& contentStr[0].length() == 2
													&& contentStr[2].length() == 4) {
												cellValue = contentStr[2] + "-"
														+ contentStr[1] + "-"
														+ contentStr[0];
											}
										}
									}
								}
							} else {
								cellValue = row[j].getContents() == null ? ""
										: row[j].getContents();
							}
						} else {
							try {
								cellValue = row[j].getContents() == null ? ""
										: row[j].getContents();
							} catch (Exception e) {
								cellValue = "";
							}
						}
						aliasCellmap.put("#CELL" + j + "#",
								cellValue == null ? "" : cellValue.trim());
					} catch (Exception e) {
						e.printStackTrace();
						// 表有空值或有空单元行，请检查！
						return token = 2;
					}
				}
				String aliasKeySql = "";
				String aliasValueSql = "";
				int initKey = 0;
				if (aliasValueMap != null && aliasTypeMap != null) {
					Set keySet = aliasValueMap.keySet();
					if (keySet != null) {
						Iterator it = keySet.iterator();
						while (it.hasNext()) {
							initKey++;
							Object keyValue = it.next();
							if (keyValue != null && !keyValue.equals("")) {
								int fieldType = Integer.parseInt(aliasTypeMap
										.get(keyValue).toString());
								Object aliasCellmapValue = aliasCellmap
										.get(aliasValueMap.get(keyValue));
								if (aliasCellmapValue == null) {// 不属于excel的列
									aliasCellmapValue = aliasValueMap
											.get(keyValue);
									if (aliasCellmapValue != null
											&& aliasCellmapValue.toString()
													.contains("#CELL")) {
										String sqlContent = aliasCellmapValue
												.toString();
										for (int l = 0; l < init; l++) {
											sqlContent = sqlContent.replaceAll(
													"#CELL" + l + "#",
													aliasCellmap.get(
															"#CELL" + l + "#")
															.toString());
											if (!sqlContent.contains("#CELL")) {
												break;
											}
										}
										LinkedHashMap sqlI8nContentMap = new LinkedHashMap();
										switch (fieldType) {
										case 2:
											sqlI8nContentMap.put(
													"sqlI18nContent",
													sqlContent);
											aliasCellmapValue = this
													.getCodeNoByName(sqlI8nContentMap) == -1 ? ""
													: this
															.getCodeNoByName(sqlI8nContentMap);
											break;
										default:
											sqlI8nContentMap.put("sqlContent",
													sqlContent);
											if (this
													.getContentNoByFiled(sqlI8nContentMap) != null) {
												Object cellObj = this
														.getContentNoByFiled(
																sqlI8nContentMap)
														.size() == 0 ? ""
														: this
																.getContentNoByFiled(
																		sqlI8nContentMap)
																.get(0);
												if (cellObj != null
														&& !cellObj.equals("")
														&& ((Map) cellObj)
																.get(keyValue
																		.toString()) != null) {
													aliasCellmapValue = (cellObj == null ? ""
															: ((Map) cellObj)
																	.get(
																			keyValue
																					.toString()
																					.toUpperCase())
																	.toString());
												} else {
													aliasCellmapValue = "";
												}
											}

											break;
										}
									}
								} else {
									if (aliasValueI18nMap != null
											&& aliasValueI18nMap.get(keyValue) != null) {// 有需要从国际化表读取No的列
										String sqlContent = aliasValueI18nMap
												.get(keyValue) == null ? ""
												: aliasValueI18nMap.get(
														keyValue).toString();
										sqlContent = sqlContent
												.replaceAll(
														aliasValueMap
																.get(keyValue) == null ? ""
																: aliasValueMap
																		.get(
																				keyValue)
																		.toString(),
														"'"
																+ aliasCellmapValue
																		.toString()
																+ "'");

										LinkedHashMap sqlI8nContentMap = new LinkedHashMap();
										sqlI8nContentMap.put("sqlI18nContent",
												sqlContent);
										if (aliasValueI18nMap.get(keyValue
												+ "_FLAG") != null
												&& aliasValueI18nMap.get(
														keyValue + "_FLAG")
														.equals("Y")) {
											sqlI8nContentMap.put("sqlContent",
													sqlContent);
											List contentList = this
													.getContentNoByFiled(sqlI8nContentMap);
											if (contentList.get(0) != null
													&& ((Map) (contentList
															.get(0)))
															.get("CPNY_ID") != null) {
												aliasCellmapValue = (contentList == null ? ""
														: ((Map) (contentList
																.get(0))).get(
																"CPNY_ID")
																.toString());
											} else if (contentList.get(0) != null
													&& ((Map) (contentList
															.get(0)))
															.get("DEPTNO") != null) {
												aliasCellmapValue = (contentList == null ? ""
														: ((Map) (contentList
																.get(0))).get(
																"DEPTNO")
																.toString());
											} else {
												aliasCellmapValue = "";
											}
										} else {
											aliasCellmapValue = this
													.getCodeNoByName(sqlI8nContentMap) == -1 ? ""
													: this
															.getCodeNoByName(sqlI8nContentMap);
										}
										if (aliasCellmapValue.equals("")) {
											System.out
													.println(aliasValueMap.get(
															keyValue)
															.toString()
															+ "-----------------------------------");
										}
									}
								}
								switch (fieldType) {
								case 0:
									// smap.put(keyValue.toString(),
									// "'"+aliasValueMap.get(keyValue)+"'");
									if (initKey == 1) {
										aliasKeySql = keyValue.toString();
										aliasValueSql = "'" + aliasCellmapValue
												+ "'";
									} else {
										aliasKeySql += ","
												+ keyValue.toString();
										aliasValueSql += ",'"
												+ aliasCellmapValue + "'";
									}
									break;
								case 1:
									// smap.put(keyValue.toString(),
									// "'"+aliasValueMap.get(keyValue)+"'");
									if (initKey == 1) {
										aliasKeySql = keyValue.toString();
										if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("/")) {
											aliasValueSql = "to_date('"
													+ aliasCellmapValue
													+ "','YYYY/MM/DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										} else if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("-")) {
											aliasValueSql = "to_date('"
													+ aliasCellmapValue
													+ "','YYYY-MM-DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										}
									} else {
										aliasKeySql += ","
												+ keyValue.toString();
										if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("/")) {
											aliasValueSql += ",to_date('"
													+ aliasCellmapValue
													+ "','YYYY/MM/DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										} else if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("-")) {
											aliasValueSql += ",to_date('"
													+ aliasCellmapValue
													+ "','YYYY-MM-DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										} else if (aliasCellmapValue == "") {
											aliasValueSql += ",'' ";
										}
									}
									break;
								case 11:
									if (initKey == 1) {
										aliasKeySql = keyValue.toString();
										if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("/")) {
											aliasValueSql = "to_date('"
													+ aliasCellmapValue
													+ "','YYYY/MM/DD HH24:MI:SS')";// "'"+aliasCellmapValue+"'";
										} else if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("-")) {
											aliasValueSql = "to_date('"
													+ aliasCellmapValue
													+ "','YYYY-MM-DD HH24:MI:SS')";// "'"+aliasCellmapValue+"'";
										}
									} else {
										aliasKeySql += ","
												+ keyValue.toString();
										if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("/")) {
											aliasValueSql += ",to_date('"
													+ aliasCellmapValue
													+ "','YYYY/MM/DD HH24:MI:SS')";// "'"+aliasCellmapValue+"'";
										} else if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("-")) {
											aliasValueSql += ",to_date('"
													+ aliasCellmapValue
													+ "','YYYY-MM-DD HH24:MI:SS')";
										}
									}
									break;
								case 12:
									if (initKey == 1) {
										aliasKeySql = keyValue.toString();
										if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("/")) {
											aliasValueSql = "to_date('"
													+ aliasCellmapValue
													+ "','YYYY/MM/DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										} else if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("-")) {
											aliasValueSql = "to_date('"
													+ aliasCellmapValue
													+ "','YYYY-MM-DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										}
									} else {
										aliasKeySql += ","
												+ keyValue.toString();
										if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("/")) {
											aliasValueSql += ",to_date('"
													+ aliasCellmapValue
													+ "','YYYY/MM/DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										} else if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("-")) {
											aliasValueSql += ",to_date('"
													+ aliasCellmapValue
													+ "','YYYY-MM-DD HH24:MI')";
										}
									}
									break;
								default:
									if (initKey == 1) {
										aliasKeySql = keyValue.toString();
										aliasValueSql = (aliasCellmapValue == null ? "null"
												: aliasCellmapValue.toString());
									} else {
										aliasKeySql += ","
												+ keyValue.toString();
										aliasValueSql += ","
												+ (aliasCellmapValue == null ? "null"
														: aliasCellmapValue
																.toString())
												+ "";
									}
									break;
								}
							}
						}
					}
				}
				smap.put("tableName", tableName);
				smap.put("aliasNameKeyContent", aliasKeySql);
				smap.put("aliasValueContent", aliasValueSql);
				String[] paramList = aliasValueSql.split(",");
				if (!firstCellValue.equals("保险补扣(个人)")
						&& !firstCellValue.equals("保险补扣(公司)")) {
					smap.put("PERSON_ID", paramList[0].replaceAll("'", ""));
					smap.put("PARAM_NO", paramList.length > 10 ? paramList[10]
							: "");
				}
				addList.add(smap);
			}
			dataMap.put("addList", addList);
			if ("IS_PARAM_DATA".equals(tableName)) {// 保险的批量导入要直接覆盖(保险补扣个人/公司除外)
				// 先删除
				this.deleteIsParamDateInfo(addList);

				// 再插入
				this.insertExcelData(dataMap);
			} else {
				this.insertExcelData(dataMap);
			}

			token = 1;

		} catch (Exception e) { // 未找到文件异常
			e.printStackTrace();
			token = 0;
		}
		return token;
	}

	/**
	 * @Title: impDataBaseTwo
	 * @Description: TODO 将excel数据封装好插入临时表
	 * @param @param request
	 * @param @param adminID
	 * @param @param cellmap
	 * @param @return
	 * @return int
	 * @throws
	 */
	@SuppressWarnings( { "unchecked" })
	public int impDataBaseTwo(HttpServletRequest request, String adminID,
			LinkedHashMap cellmap) {
		int token = 0;
		int k = path.indexOf("\\");
		String server = "win";
		if (k < 0) { // WebServer的os为unix/linux
			server = "u/l";
		}
		File file = null;
		if ("win".equals(server)) {
			file = new File(path + "\\" + filename + ".xls");
		} else {
			file = new File(path + "/" + filename + ".xls");
		}
		Map dataMap = new HashMap();
		try {
			Workbook workBook = Workbook.getWorkbook(file);
			// 获得第一个工作表对象
			Sheet sheet = workBook.getSheet(0);
			// 验证是否存在导入数据
			if (sheet.getRows() < 2) {
				logger.debug("Import file not exist data, return.");
				return token;
			}
			String tableName = cellmap.get("tableName") == null ? "" : cellmap
					.get("tableName").toString();// 获取表名
			LinkedHashMap aliasValueMap = cellmap.get("aliasValueMap") == null ? null
					: (LinkedHashMap) cellmap.get("aliasValueMap");
			LinkedHashMap aliasTypeMap = cellmap.get("aliasTypeMap") == null ? null
					: (LinkedHashMap) cellmap.get("aliasTypeMap");
			LinkedHashMap aliasValueI18nMap = cellmap.get("aliasValueI18nMap") == null ? null
					: (LinkedHashMap) cellmap.get("aliasValueI18nMap");
			String field2_value = aliasValueMap.get("FIELD2_VALUE") == null ? ""
					: aliasValueMap.get("FIELD2_VALUE").toString();
			String aliasNullStr = cellmap.get("aliasNullStr") == null ? ""
					: cellmap.get("aliasNullStr").toString();

			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			// 判断导入的是否为已有记录----LM
			boolean isUpdate = false;
			for (int m = 1; m < sheet.getRows(); m++) {
				Cell[] rowTemp = sheet.getRow(m);
				if (rowTemp[0] == null
						|| rowTemp[0].getContents().trim().equals("")) {
					continue;
				}
			}
			// 派遣地发令导入数据
			LinkedHashMap aliasMapCells = new LinkedHashMap();
			if (aliasValueMap != null && aliasTypeMap != null) {// 存放CELL0：NUMBER类型的键值对
				Set keySet = aliasValueMap.keySet();
				if (keySet != null) {
					Iterator it = keySet.iterator();
					while (it.hasNext()) {
						Object keyvalue = it.next();
						if (keyvalue != null) {
							Object aliasValue = aliasValueMap.get(keyvalue);
							if (aliasValue != null) {
								aliasMapCells.put(aliasValue.toString(),
										aliasTypeMap.get(keyvalue));
							}
						}
					}
				}
			}

			List addList = new ArrayList();
			// 循环工作表的每行数据
			int init = 0;
			for (int i = 1; i < sheet.getRows(); i++) {
				// 如果是可以为空的列就不进行空列的判断
				// 验证当前行是否符合规范
				if (!validateCell(sheet.getRow(i), sheet.getRow(0).length,
						aliasNullStr)) {// 如果所在行全为空，不导入
					break;
				}

				Cell[] row = sheet.getRow(i);
				if (row[0] == null || row[0].getContents().trim().equals("")) {
					continue;
				}

				if (init == 0) {
					init = sheet.getRow(0).length;
				}
				int aaa = sheet.getRow(1).length;
				LinkedHashMap smap = new LinkedHashMap();
				LinkedHashMap aliasCellmap = new LinkedHashMap();
				String firstCellValue = "";
				for (int j = 0; j < init; j++) {
					try {
						String cellValue = "";
						Object cellType = aliasMapCells.get("#CELL" + j + "#");
						String content = "";
						// if(isUpdate){
						// continue;
						// }
						if (j == 0) {
							firstCellValue = row[0].getContents().trim();
						}
						if (aliasNullStr.indexOf("," + j + ",") < 0) {
							content = StringUtil
									.checkNull(row[j].getContents());
							if (cellType != null && !content.equals("")) {// date类型
								try {
									cellValue = this.FormateData(row[j],
											cellType);
									if (cellValue.contains("/")) {
										String[] contentStr = cellValue
												.split("/");
										if (contentStr.length == 3
												&& contentStr[0].length() == 2
												&& contentStr[2].length() == 4) {
											cellValue = contentStr[2] + "/"
													+ contentStr[1] + "/"
													+ contentStr[0];
										}
									} else if (cellValue.contains("-")) {
										String[] contentStr = cellValue
												.split("-");
										if (contentStr.length == 3
												&& contentStr[0].length() == 2
												&& contentStr[2].length() == 4) {
											cellValue = contentStr[2] + "-"
													+ contentStr[1] + "-"
													+ contentStr[0];
										}
									}
								} catch (Exception e) {
									cellValue = "";
									if (row[j].getContents() != null) {
										cellValue = row[j].getContents();
										if (cellValue.contains("/")) {
											String[] contentStr = cellValue
													.split("/");
											if (contentStr.length == 3
													&& contentStr[0].length() == 2
													&& contentStr[2].length() == 4) {
												cellValue = contentStr[2] + "/"
														+ contentStr[1] + "/"
														+ contentStr[0];
											}
										} else if (cellValue.contains("-")) {
											String[] contentStr = cellValue
													.split("-");
											if (contentStr.length == 3
													&& contentStr[0].length() == 2
													&& contentStr[2].length() == 4) {
												cellValue = contentStr[2] + "-"
														+ contentStr[1] + "-"
														+ contentStr[0];
											}
										}
									}
								}
							} else {
								cellValue = row[j].getContents() == null ? ""
										: row[j].getContents();
							}
						} else {
							try {
								cellValue = row[j].getContents() == null ? ""
										: row[j].getContents();
							} catch (Exception e) {
								cellValue = "";
							}
						}
						aliasCellmap.put("#CELL" + j + "#",
								cellValue == null ? "" : cellValue.trim());
					} catch (Exception e) {
						e.printStackTrace();
						// 表有空值或有空单元行，请检查！
						return token = 2;
					}
				}
				String aliasKeySql = "";
				String aliasValueSql = "";
				int initKey = 0;
				if (aliasValueMap != null && aliasTypeMap != null) {
					Set keySet = aliasValueMap.keySet();
					if (keySet != null) {
						Iterator it = keySet.iterator();
						while (it.hasNext()) {
							initKey++;
							Object keyValue = it.next();
							if (keyValue != null && !keyValue.equals("")) {
								int fieldType = Integer.parseInt(aliasTypeMap
										.get(keyValue).toString());
								Object aliasCellmapValue = aliasCellmap
										.get(aliasValueMap.get(keyValue));
								if (aliasCellmapValue == null) {// 不属于excel的列
									aliasCellmapValue = aliasValueMap
											.get(keyValue);
									if (aliasCellmapValue != null
											&& aliasCellmapValue.toString()
													.contains("#CELL")) {
										String sqlContent = aliasCellmapValue
												.toString();
										for (int l = 0; l < init; l++) {
											sqlContent = sqlContent.replaceAll(
													"#CELL" + l + "#",
													aliasCellmap.get(
															"#CELL" + l + "#")
															.toString());
											if (!sqlContent.contains("#CELL")) {
												break;
											}
										}
										LinkedHashMap sqlI8nContentMap = new LinkedHashMap();
										switch (fieldType) {
										case 2:
											sqlI8nContentMap.put(
													"sqlI18nContent",
													sqlContent);
											aliasCellmapValue = this
													.getCodeNoByName(sqlI8nContentMap) == -1 ? ""
													: this
															.getCodeNoByName(sqlI8nContentMap);
											break;
										default:
											sqlI8nContentMap.put("sqlContent",
													sqlContent);
											if (this
													.getContentNoByFiled(sqlI8nContentMap) != null) {
												Object cellObj = this
														.getContentNoByFiled(
																sqlI8nContentMap)
														.size() == 0 ? ""
														: this
																.getContentNoByFiled(
																		sqlI8nContentMap)
																.get(0);
												if (cellObj != null
														&& !cellObj.equals("")
														&& ((Map) cellObj)
																.get(keyValue
																		.toString()) != null) {
													aliasCellmapValue = (cellObj == null ? ""
															: ((Map) cellObj)
																	.get(
																			keyValue
																					.toString()
																					.toUpperCase())
																	.toString());
												} else {
													aliasCellmapValue = "";
												}
											}

											break;
										}
									}
								} else {
									if (aliasValueI18nMap != null
											&& aliasValueI18nMap.get(keyValue) != null) {// 有需要从国际化表读取No的列
										String sqlContent = aliasValueI18nMap
												.get(keyValue) == null ? ""
												: aliasValueI18nMap.get(
														keyValue).toString();
										sqlContent = sqlContent
												.replaceAll(
														aliasValueMap
																.get(keyValue) == null ? ""
																: aliasValueMap
																		.get(
																				keyValue)
																		.toString(),
														"'"
																+ aliasCellmapValue
																		.toString()
																+ "'");

										LinkedHashMap sqlI8nContentMap = new LinkedHashMap();
										sqlI8nContentMap.put("sqlI18nContent",
												sqlContent);
										if (aliasValueI18nMap.get(keyValue
												+ "_FLAG") != null
												&& aliasValueI18nMap.get(
														keyValue + "_FLAG")
														.equals("Y")) {
											sqlI8nContentMap.put("sqlContent",
													sqlContent);
											List contentList = this
													.getContentNoByFiled(sqlI8nContentMap);
											if (contentList.get(0) != null
													&& ((Map) (contentList
															.get(0)))
															.get("CPNY_ID") != null) {
												aliasCellmapValue = (contentList == null ? ""
														: ((Map) (contentList
																.get(0))).get(
																"CPNY_ID")
																.toString());
											} else if (contentList.get(0) != null
													&& ((Map) (contentList
															.get(0)))
															.get("DEPTNO") != null) {
												aliasCellmapValue = (contentList == null ? ""
														: ((Map) (contentList
																.get(0))).get(
																"DEPTNO")
																.toString());
											} else {
												aliasCellmapValue = "";
											}
										} else {
											aliasCellmapValue = this
													.getCodeNoByName(sqlI8nContentMap) == -1 ? ""
													: this
															.getCodeNoByName(sqlI8nContentMap);
										}
										if (aliasCellmapValue.equals("")) {
											System.out
													.println(aliasValueMap.get(
															keyValue)
															.toString()
															+ "-----------------------------------");
										}
									}
								}
								switch (fieldType) {
								case 0:
									// smap.put(keyValue.toString(),
									// "'"+aliasValueMap.get(keyValue)+"'");
									if (initKey == 1) {
										aliasKeySql = keyValue.toString();
										aliasValueSql = "'" + aliasCellmapValue
												+ "'";
									} else {
										aliasKeySql += ","
												+ keyValue.toString();
										aliasValueSql += ",'"
												+ aliasCellmapValue + "'";
									}
									break;
								case 1:
									// smap.put(keyValue.toString(),
									// "'"+aliasValueMap.get(keyValue)+"'");
									if (initKey == 1) {
										aliasKeySql = keyValue.toString();
										if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("/")) {
											aliasValueSql = "to_date('"
													+ aliasCellmapValue
													+ "','YYYY/MM/DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										} else if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("-")) {
											aliasValueSql = "to_date('"
													+ aliasCellmapValue
													+ "','YYYY-MM-DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										}
									} else {
										aliasKeySql += ","
												+ keyValue.toString();
										if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("/")) {
											aliasValueSql += ",to_date('"
													+ aliasCellmapValue
													+ "','YYYY/MM/DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										} else if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("-")) {
											aliasValueSql += ",to_date('"
													+ aliasCellmapValue
													+ "','YYYY-MM-DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										} else if (aliasCellmapValue == "") {
											aliasValueSql += ",'' ";
										}
									}
									break;
								case 11:
									if (initKey == 1) {
										aliasKeySql = keyValue.toString();
										if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("/")) {
											aliasValueSql = "to_date('"
													+ aliasCellmapValue
													+ "','YYYY/MM/DD HH24:MI:SS')";// "'"+aliasCellmapValue+"'";
										} else if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("-")) {
											aliasValueSql = "to_date('"
													+ aliasCellmapValue
													+ "','YYYY-MM-DD HH24:MI:SS')";// "'"+aliasCellmapValue+"'";
										}
									} else {
										aliasKeySql += ","
												+ keyValue.toString();
										if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("/")) {
											aliasValueSql += ",to_date('"
													+ aliasCellmapValue
													+ "','YYYY/MM/DD HH24:MI:SS')";// "'"+aliasCellmapValue+"'";
										} else if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("-")) {
											aliasValueSql += ",to_date('"
													+ aliasCellmapValue
													+ "','YYYY-MM-DD HH24:MI:SS')";
										}
									}
									break;
								case 12:
									if (initKey == 1) {
										aliasKeySql = keyValue.toString();
										if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("/")) {
											aliasValueSql = "to_date('"
													+ aliasCellmapValue
													+ "','YYYY/MM/DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										} else if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("-")) {
											aliasValueSql = "to_date('"
													+ aliasCellmapValue
													+ "','YYYY-MM-DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										}
									} else {
										aliasKeySql += ","
												+ keyValue.toString();
										if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("/")) {
											aliasValueSql += ",to_date('"
													+ aliasCellmapValue
													+ "','YYYY/MM/DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										} else if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("-")) {
											aliasValueSql += ",to_date('"
													+ aliasCellmapValue
													+ "','YYYY-MM-DD HH24:MI')";
										}
									}
									break;
								default:
									if (initKey == 1) {
										aliasKeySql = keyValue.toString();
										aliasValueSql = (aliasCellmapValue == null ? "null"
												: aliasCellmapValue.toString());
									} else {
										aliasKeySql += ","
												+ keyValue.toString();
										aliasValueSql += ","
												+ (aliasCellmapValue == null ? "null"
														: aliasCellmapValue
																.toString())
												+ "";
									}
									break;
								}
							}
						}
					}
				}
				smap.put("tableName", tableName);
				smap.put("aliasNameKeyContent", aliasKeySql);
				smap.put("aliasValueContent", aliasValueSql);
				String[] paramList = aliasValueSql.split(",");
				if (!firstCellValue.equals("保险补扣(个人)")
						&& !firstCellValue.equals("保险补扣(公司)")) {
					smap.put("PERSON_ID", paramList[0].replaceAll("'", ""));
					smap.put("PARAM_NO", paramList[10]);
				}
				addList.add(smap);
			}
			dataMap.put("addList", addList);
			if ("IS_PARAM_DATA".equals(tableName)) {// 保险的批量导入要直接覆盖(保险补扣个人/公司除外)
				// 先删除
				this.deleteIsParamDateInfo(addList);

				// 再插入
				this.insertExcelData(dataMap);
			} else {
				this.insertExcelData(dataMap);
			}

			token = 1;

		} catch (Exception e) { // 未找到文件异常
			e.printStackTrace();
			token = 0;
		}
		return token;
	}

	@SuppressWarnings( { "unchecked" })
	public int impDataOtApply(HttpServletRequest request, String adminID,
			LinkedHashMap cellmap) {
		int token = 0;
		int k = path.indexOf("\\");
		String server = "win";
		if (k < 0) { // WebServer的os为unix/linux
			server = "u/l";
		}
		File file = null;
		if ("win".equals(server)) {
			file = new File(path + "\\" + filename + ".xls");
		} else {
			file = new File(path + "/" + filename + ".xls");
		}
		Map dataMap = new LinkedHashMap();
		try {
			Workbook workBook = Workbook.getWorkbook(file);
			// 获得第一个工作表对象
			Sheet sheet = workBook.getSheet(0);
			// 验证是否存在导入数据
			if (sheet.getRows() < 2) {
				logger.debug("Import file not exist data, return.");
				return token;
			}
			String tableName = cellmap.get("tableName") == null ? "" : cellmap
					.get("tableName").toString();// 获取表名
			LinkedHashMap aliasValueMap = cellmap.get("aliasValueMap") == null ? null
					: (LinkedHashMap) cellmap.get("aliasValueMap");
			LinkedHashMap aliasTypeMap = cellmap.get("aliasTypeMap") == null ? null
					: (LinkedHashMap) cellmap.get("aliasTypeMap");
			LinkedHashMap aliasValueI18nMap = cellmap.get("aliasValueI18nMap") == null ? null
					: (LinkedHashMap) cellmap.get("aliasValueI18nMap");
			String aliasNullStr = cellmap.get("aliasNullStr") == null ? ""
					: cellmap.get("aliasNullStr").toString();

			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			LinkedHashMap aliasMapCells = new LinkedHashMap();
			if (aliasValueMap != null && aliasTypeMap != null) {// 存放CELL0：NUMBER类型的键值对
				Set keySet = aliasValueMap.keySet();
				if (keySet != null) {
					Iterator it = keySet.iterator();
					while (it.hasNext()) {
						Object keyvalue = it.next();
						if (keyvalue != null) {
							Object aliasValue = aliasValueMap.get(keyvalue);
							if (aliasValue != null) {
								aliasMapCells.put(aliasValue.toString(),
										aliasTypeMap.get(keyvalue));
							}
						}
					}
				}
			}
			List addList = new ArrayList();
			// 循环工作表的每行数据
			int init = 0;
			for (int i = 1; i < sheet.getRows(); i++) {
				// 如果是可以为空的列就不进行空列的判断
				// 验证当前行是否符合规范
				if (!validateCell(sheet.getRow(i), sheet.getRow(0).length,
						aliasNullStr)) {// 如果所在行全为空，不导入
					break;
				}
				Cell[] row = sheet.getRow(i);
				if (row[0] == null || row[0].getContents().trim().equals("")) {
					continue;
				}
				if (init == 0) {
					init = sheet.getRow(0).length;
				}
				LinkedHashMap smap = new LinkedHashMap();
				LinkedHashMap aliasCellmap = new LinkedHashMap();
				for (int j = 0; j < init; j++) {
					try {
						String cellValue = "";
						Object cellType = aliasMapCells.get("#CELL" + j + "#");
						String content = "";
						if (aliasNullStr.indexOf("," + j + ",") < 0) {
							content = StringUtil
									.checkNull(row[j].getContents());
							if (cellType != null && !content.equals("")) {// date类型
								try {
									cellValue = this.FormateData(row[j],
											cellType);
									if (cellValue.contains("/")) {
										String[] contentStr = cellValue
												.split("/");
										if (contentStr.length == 3
												&& contentStr[0].length() == 2
												&& contentStr[2].length() == 4) {
											cellValue = contentStr[2] + "/"
													+ contentStr[1] + "/"
													+ contentStr[0];
										}
									} else if (cellValue.contains("-")) {
										String[] contentStr = cellValue
												.split("-");
										if (contentStr.length == 3
												&& contentStr[0].length() == 2
												&& contentStr[2].length() == 4) {
											cellValue = contentStr[2] + "-"
													+ contentStr[1] + "-"
													+ contentStr[0];
										}
									}
								} catch (Exception e) {
									cellValue = "";
									if (row[j].getContents() != null) {
										cellValue = row[j].getContents();
										if (cellValue.contains("/")) {
											String[] contentStr = cellValue
													.split("/");
											if (contentStr.length == 3
													&& contentStr[0].length() == 2
													&& contentStr[2].length() == 4) {
												cellValue = contentStr[2] + "/"
														+ contentStr[1] + "/"
														+ contentStr[0];
											}
										} else if (cellValue.contains("-")) {
											String[] contentStr = cellValue
													.split("-");
											if (contentStr.length == 3
													&& contentStr[0].length() == 2
													&& contentStr[2].length() == 4) {
												cellValue = contentStr[2] + "-"
														+ contentStr[1] + "-"
														+ contentStr[0];
											}
										}
									}
								}
							} else {
								cellValue = row[j].getContents() == null ? ""
										: row[j].getContents();
							}
						} else {
							try {
								cellValue = row[j].getContents() == null ? ""
										: row[j].getContents();
							} catch (Exception e) {
								cellValue = "";
							}
						}
						aliasCellmap.put("#CELL" + j + "#",
								cellValue == null ? "" : cellValue.trim());
					} catch (Exception e) {
						e.printStackTrace();
						// 表有空值或有空单元行，请检查！
						return token = 2;
					}
				}
				String aliasKeySql = "";
				String aliasValueSql = "";
				int initKey = 0;
				if (aliasValueMap != null && aliasTypeMap != null) {
					Set keySet = aliasValueMap.keySet();
					if (keySet != null) {
						Iterator it = keySet.iterator();
						while (it.hasNext()) {
							initKey++;
							Object keyValue = it.next();
							if (keyValue != null && !keyValue.equals("")) {
								int fieldType = Integer.parseInt(aliasTypeMap
										.get(keyValue).toString());
								Object aliasCellmapValue = aliasCellmap
										.get(aliasValueMap.get(keyValue));
								if (aliasCellmapValue == null) {// 不属于excel的列
									aliasCellmapValue = aliasValueMap
											.get(keyValue);
									if (aliasCellmapValue != null
											&& aliasCellmapValue.toString()
													.contains("#CELL")) {
										String sqlContent = aliasCellmapValue
												.toString();
										for (int l = 0; l < init; l++) {
											sqlContent = sqlContent.replaceAll(
													"#CELL" + l + "#",
													aliasCellmap.get(
															"#CELL" + l + "#")
															.toString());
											if (!sqlContent.contains("#CELL")) {
												break;
											}
										}
										LinkedHashMap sqlI8nContentMap = new LinkedHashMap();
										switch (fieldType) {
										case 2:
											sqlI8nContentMap.put(
													"sqlI18nContent",
													sqlContent);
											aliasCellmapValue = this
													.getCodeNoByName(sqlI8nContentMap) == -1 ? ""
													: this
															.getCodeNoByName(sqlI8nContentMap);
											break;
										default:
											sqlI8nContentMap.put("sqlContent",
													sqlContent);
											if (this
													.getContentNoByFiled(sqlI8nContentMap) != null) {
												Object cellObj = this
														.getContentNoByFiled(
																sqlI8nContentMap)
														.size() == 0 ? ""
														: this
																.getContentNoByFiled(
																		sqlI8nContentMap)
																.get(0);
												if (cellObj != null
														&& !cellObj.equals("")
														&& ((Map) cellObj)
																.get(keyValue
																		.toString()) != null) {
													aliasCellmapValue = (cellObj == null ? ""
															: ((Map) cellObj)
																	.get(
																			keyValue
																					.toString()
																					.toUpperCase())
																	.toString());
												} else {
													aliasCellmapValue = "";
												}
											}

											break;
										}
									}
								} else {
									if (aliasValueI18nMap != null
											&& aliasValueI18nMap.get(keyValue) != null) {// 有需要从国际化表读取No的列
										String sqlContent = aliasValueI18nMap
												.get(keyValue) == null ? ""
												: aliasValueI18nMap.get(
														keyValue).toString();
										sqlContent = sqlContent
												.replaceAll(
														aliasValueMap
																.get(keyValue) == null ? ""
																: aliasValueMap
																		.get(
																				keyValue)
																		.toString(),
														"'"
																+ aliasCellmapValue
																		.toString()
																+ "'");

										LinkedHashMap sqlI8nContentMap = new LinkedHashMap();
										sqlI8nContentMap.put("sqlI18nContent",
												sqlContent);
										if (aliasValueI18nMap.get(keyValue
												+ "_FLAG") != null
												&& aliasValueI18nMap.get(
														keyValue + "_FLAG")
														.equals("Y")) {
											sqlI8nContentMap.put("sqlContent",
													sqlContent);
											List contentList = this
													.getContentNoByFiled(sqlI8nContentMap);
											if (contentList.get(0) != null
													&& ((Map) (contentList
															.get(0)))
															.get("CPNY_ID") != null) {
												aliasCellmapValue = (contentList == null ? ""
														: ((Map) (contentList
																.get(0))).get(
																"CPNY_ID")
																.toString());
											} else if (contentList.get(0) != null
													&& ((Map) (contentList
															.get(0)))
															.get("DEPTNO") != null) {
												aliasCellmapValue = (contentList == null ? ""
														: ((Map) (contentList
																.get(0))).get(
																"DEPTNO")
																.toString());
											} else {
												aliasCellmapValue = "";
											}
										} else {
											aliasCellmapValue = this
													.getCodeNoByName(sqlI8nContentMap) == -1 ? ""
													: this
															.getCodeNoByName(sqlI8nContentMap);
										}
										if (aliasCellmapValue.equals("")) {
											System.out.println("--------"
													+ aliasValueMap.get(
															keyValue)
															.toString());
										}
									}
								}
								switch (fieldType) {
								case 0:
									// smap.put(keyValue.toString(),
									// "'"+aliasValueMap.get(keyValue)+"'");
									if (initKey == 1) {
										aliasKeySql = keyValue.toString();
										aliasValueSql = "'" + aliasCellmapValue
												+ "'";
									} else {
										aliasKeySql += ","
												+ keyValue.toString();
										aliasValueSql += ",'"
												+ aliasCellmapValue + "'";
									}
									break;
								case 1:
									// smap.put(keyValue.toString(),
									// "'"+aliasValueMap.get(keyValue)+"'");
									if (initKey == 1) {
										aliasKeySql = keyValue.toString();
										if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("/")) {
											aliasValueSql = "to_date('"
													+ aliasCellmapValue
													+ "','YYYY/MM/DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										} else if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("-")) {
											aliasValueSql = "to_date('"
													+ aliasCellmapValue
													+ "','YYYY-MM-DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										}
									} else {
										aliasKeySql += ","
												+ keyValue.toString();
										if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("/")) {
											aliasValueSql += ",to_date('"
													+ aliasCellmapValue
													+ "','YYYY/MM/DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										} else if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("-")) {
											aliasValueSql += ",to_date('"
													+ aliasCellmapValue
													+ "','YYYY-MM-DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										} else if (aliasCellmapValue == "") {
											aliasValueSql += ",'' ";
										}
									}
									break;
								case 11:
									if (initKey == 1) {
										aliasKeySql = keyValue.toString();
										if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("/")) {
											aliasValueSql = "to_date('"
													+ aliasCellmapValue
													+ "','YYYY/MM/DD HH24:MI:SS')";// "'"+aliasCellmapValue+"'";
										} else if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("-")) {
											aliasValueSql = "to_date('"
													+ aliasCellmapValue
													+ "','YYYY-MM-DD HH24:MI:SS')";// "'"+aliasCellmapValue+"'";
										}
									} else {
										aliasKeySql += ","
												+ keyValue.toString();
										if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("/")) {
											aliasValueSql += ",to_date('"
													+ aliasCellmapValue
													+ "','YYYY/MM/DD HH24:MI:SS')";// "'"+aliasCellmapValue+"'";
										} else if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("-")) {
											aliasValueSql += ",to_date('"
													+ aliasCellmapValue
													+ "','YYYY-MM-DD HH24:MI:SS')";
										}
									}
									break;
								case 12:
									if (initKey == 1) {
										aliasKeySql = keyValue.toString();
										if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("/")) {
											aliasValueSql = "to_date('"
													+ aliasCellmapValue
													+ "','YYYY/MM/DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										} else if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("-")) {
											aliasValueSql = "to_date('"
													+ aliasCellmapValue
													+ "','YYYY-MM-DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										}
									} else {
										aliasKeySql += ","
												+ keyValue.toString();
										if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("/")) {
											aliasValueSql += ",to_date('"
													+ aliasCellmapValue
													+ "','YYYY/MM/DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										} else if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("-")) {
											aliasValueSql += ",to_date('"
													+ aliasCellmapValue
													+ "','YYYY-MM-DD HH24:MI')";
										}
									}
									break;
								default:
									if (initKey == 1) {
										aliasKeySql = keyValue.toString();
										aliasValueSql = (aliasCellmapValue == null ? "null"
												: aliasCellmapValue.toString());
									} else {
										aliasKeySql += ","
												+ keyValue.toString();
										aliasValueSql += ","
												+ (aliasCellmapValue == null ? "null"
														: aliasCellmapValue
																.toString())
												+ "";
									}
									break;
								}
							}
						}
					}
				}
				smap.put("tableName", tableName);
				smap.put("aliasNameKeyContent", aliasKeySql);
				smap.put("aliasValueContent", aliasValueSql);
				addList.add(smap);
			}
			dataMap.put("addList", addList);
			dataMap.put("CPNY_ID", admin.getCpnyId());
			dataMap.put("CREATED_BY", admin.getPersonId());
			dataMap.put("UPLOAD_BY", admin.getPersonId());
			this.insertExcelDataOtApply(dataMap);
			token = 1;
		} catch (Exception e) { // 未找到文件异常
			e.printStackTrace();
			token = 0;
		}
		return token;
	}

	@SuppressWarnings( { "unchecked" })
	public int impDataPaForLeftApply(HttpServletRequest request,
			String adminID, LinkedHashMap cellmap) {
		int token = 0;
		int k = path.indexOf("\\");
		String server = "win";
		if (k < 0) { // WebServer的os为unix/linux
			server = "u/l";
		}
		File file = null;
		if ("win".equals(server)) {
			file = new File(path + "\\" + filename + ".xls");
		} else {
			file = new File(path + "/" + filename + ".xls");
		}
		Map dataMap = new LinkedHashMap();
		try {
			Workbook workBook = Workbook.getWorkbook(file);
			// 获得第一个工作表对象
			Sheet sheet = workBook.getSheet(0);
			// 验证是否存在导入数据
			if (sheet.getRows() < 2) {
				logger.debug("Import file not exist data, return.");
				return token;
			}
			String tableName = cellmap.get("tableName") == null ? "" : cellmap
					.get("tableName").toString();// 获取表名
			LinkedHashMap aliasValueMap = cellmap.get("aliasValueMap") == null ? null
					: (LinkedHashMap) cellmap.get("aliasValueMap");
			LinkedHashMap aliasTypeMap = cellmap.get("aliasTypeMap") == null ? null
					: (LinkedHashMap) cellmap.get("aliasTypeMap");
			LinkedHashMap aliasValueI18nMap = cellmap.get("aliasValueI18nMap") == null ? null
					: (LinkedHashMap) cellmap.get("aliasValueI18nMap");
			String aliasNullStr = cellmap.get("aliasNullStr") == null ? ""
					: cellmap.get("aliasNullStr").toString();

			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			LinkedHashMap aliasMapCells = new LinkedHashMap();
			if (aliasValueMap != null && aliasTypeMap != null) {// 存放CELL0：NUMBER类型的键值对
				Set keySet = aliasValueMap.keySet();
				if (keySet != null) {
					Iterator it = keySet.iterator();
					while (it.hasNext()) {
						Object keyvalue = it.next();
						if (keyvalue != null) {
							Object aliasValue = aliasValueMap.get(keyvalue);
							if (aliasValue != null) {
								aliasMapCells.put(aliasValue.toString(),
										aliasTypeMap.get(keyvalue));
							}
						}
					}
				}
			}
			List addList = new ArrayList();
			// 循环工作表的每行数据
			int init = 0;
			int checkFlag = 0;
			Cell[] headerRow = sheet.getRow(0);
			init = sheet.getRow(0).length;
			for (int i = 1; i < sheet.getRows(); i++) {
				Cell[] row = sheet.getRow(i);
				// 验证当前行是否符合规范
				checkFlag = validateCellForRequiredColum(headerRow, row, init);
				if (checkFlag != 1) {
					// 不符合规范
					break;
				} else {
					// 符合规范
					LinkedHashMap smap = new LinkedHashMap();
					LinkedHashMap aliasCellmap = new LinkedHashMap();
					for (int j = 0; j < init; j++) {
						try {
							String cellValue = "";
							Object cellType = aliasMapCells.get("#CELL" + j
									+ "#");
							String content = "";
							if (aliasNullStr.indexOf("," + j + ",") < 0) {
								content = StringUtil.checkNull(row[j]
										.getContents());
								if (cellType != null && !content.equals("")) {// date类型
									try {
										cellValue = this.FormateData(row[j],
												cellType);
										if (cellValue.contains("/")) {
											String[] contentStr = cellValue
													.split("/");
											if (contentStr.length == 3
													&& contentStr[0].length() == 2
													&& contentStr[2].length() == 4) {
												cellValue = contentStr[2] + "/"
														+ contentStr[1] + "/"
														+ contentStr[0];
											}
										} else if (cellValue.contains("-")) {
											String[] contentStr = cellValue
													.split("-");
											if (contentStr.length == 3
													&& contentStr[0].length() == 2
													&& contentStr[2].length() == 4) {
												cellValue = contentStr[2] + "-"
														+ contentStr[1] + "-"
														+ contentStr[0];
											}
										}
									} catch (Exception e) {
										cellValue = "";
										if (row[j].getContents() != null) {
											cellValue = row[j].getContents();
											if (cellValue.contains("/")) {
												String[] contentStr = cellValue
														.split("/");
												if (contentStr.length == 3
														&& contentStr[0]
																.length() == 2
														&& contentStr[2]
																.length() == 4) {
													cellValue = contentStr[2]
															+ "/"
															+ contentStr[1]
															+ "/"
															+ contentStr[0];
												}
											} else if (cellValue.contains("-")) {
												String[] contentStr = cellValue
														.split("-");
												if (contentStr.length == 3
														&& contentStr[0]
																.length() == 2
														&& contentStr[2]
																.length() == 4) {
													cellValue = contentStr[2]
															+ "-"
															+ contentStr[1]
															+ "-"
															+ contentStr[0];
												}
											}
										}
									}
								} else {
									cellValue = row[j].getContents() == null ? ""
											: row[j].getContents();
								}
							} else {
								try {
									cellValue = row[j].getContents() == null ? ""
											: row[j].getContents();
								} catch (Exception e) {
									cellValue = "";
								}
							}
							aliasCellmap.put("#CELL" + j + "#",
									cellValue == null ? "" : cellValue.trim());
						} catch (Exception e) {
							e.printStackTrace();
							// 表有空值或有空单元行，请检查！
							return token = 2;
						}
					}
					String aliasKeySql = "";
					String aliasValueSql = "";
					int initKey = 0;
					if (aliasValueMap != null && aliasTypeMap != null) {
						Set keySet = aliasValueMap.keySet();
						if (keySet != null) {
							Iterator it = keySet.iterator();
							while (it.hasNext()) {
								initKey++;
								Object keyValue = it.next();
								if (keyValue != null && !keyValue.equals("")) {
									int fieldType = Integer
											.parseInt(aliasTypeMap
													.get(keyValue).toString());
									Object aliasCellmapValue = aliasCellmap
											.get(aliasValueMap.get(keyValue));
									if (aliasCellmapValue == null) {// 不属于excel的列
										aliasCellmapValue = aliasValueMap
												.get(keyValue);
										if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("#CELL")) {
											String sqlContent = aliasCellmapValue
													.toString();
											for (int l = 0; l < init; l++) {
												sqlContent = sqlContent
														.replaceAll(
																"#CELL" + l
																		+ "#",
																aliasCellmap
																		.get(
																				"#CELL"
																						+ l
																						+ "#")
																		.toString());
												if (!sqlContent
														.contains("#CELL")) {
													break;
												}
											}
											LinkedHashMap sqlI8nContentMap = new LinkedHashMap();
											switch (fieldType) {
											case 2:
												sqlI8nContentMap.put(
														"sqlI18nContent",
														sqlContent);
												aliasCellmapValue = this
														.getCodeNoByName(sqlI8nContentMap) == -1 ? ""
														: this
																.getCodeNoByName(sqlI8nContentMap);
												break;
											default:
												sqlI8nContentMap.put(
														"sqlContent",
														sqlContent);
												if (this
														.getContentNoByFiled(sqlI8nContentMap) != null) {
													Object cellObj = this
															.getContentNoByFiled(
																	sqlI8nContentMap)
															.size() == 0 ? ""
															: this
																	.getContentNoByFiled(
																			sqlI8nContentMap)
																	.get(0);
													if (cellObj != null
															&& !cellObj
																	.equals("")
															&& ((Map) cellObj)
																	.get(keyValue
																			.toString()) != null) {
														aliasCellmapValue = (cellObj == null ? ""
																: ((Map) cellObj)
																		.get(
																				keyValue
																						.toString()
																						.toUpperCase())
																		.toString());
													} else {
														aliasCellmapValue = "";
													}
												}

												break;
											}
										}
									} else {
										if (aliasValueI18nMap != null
												&& aliasValueI18nMap
														.get(keyValue) != null) {// 有需要从国际化表读取No的列
											String sqlContent = aliasValueI18nMap
													.get(keyValue) == null ? ""
													: aliasValueI18nMap.get(
															keyValue)
															.toString();
											sqlContent = sqlContent
													.replaceAll(
															aliasValueMap
																	.get(keyValue) == null ? ""
																	: aliasValueMap
																			.get(
																					keyValue)
																			.toString(),
															"'"
																	+ aliasCellmapValue
																			.toString()
																	+ "'");

											LinkedHashMap sqlI8nContentMap = new LinkedHashMap();
											sqlI8nContentMap.put(
													"sqlI18nContent",
													sqlContent);
											if (aliasValueI18nMap.get(keyValue
													+ "_FLAG") != null
													&& aliasValueI18nMap.get(
															keyValue + "_FLAG")
															.equals("Y")) {
												sqlI8nContentMap.put(
														"sqlContent",
														sqlContent);
												List contentList = this
														.getContentNoByFiled(sqlI8nContentMap);
												if (contentList.get(0) != null
														&& ((Map) (contentList
																.get(0)))
																.get("CPNY_ID") != null) {
													aliasCellmapValue = (contentList == null ? ""
															: ((Map) (contentList
																	.get(0)))
																	.get(
																			"CPNY_ID")
																	.toString());
												} else if (contentList.get(0) != null
														&& ((Map) (contentList
																.get(0)))
																.get("DEPTNO") != null) {
													aliasCellmapValue = (contentList == null ? ""
															: ((Map) (contentList
																	.get(0)))
																	.get(
																			"DEPTNO")
																	.toString());
												} else {
													aliasCellmapValue = "";
												}
											} else {
												aliasCellmapValue = this
														.getCodeNoByName(sqlI8nContentMap) == -1 ? ""
														: this
																.getCodeNoByName(sqlI8nContentMap);
											}
											if (aliasCellmapValue.equals("")) {
												System.out.println("--------"
														+ aliasValueMap.get(
																keyValue)
																.toString());
											}
										}
									}
									switch (fieldType) {
									case 0:
										// smap.put(keyValue.toString(),
										// "'"+aliasValueMap.get(keyValue)+"'");
										if (initKey == 1) {
											aliasKeySql = keyValue.toString();
											aliasValueSql = "'"
													+ aliasCellmapValue + "'";
										} else {
											aliasKeySql += ","
													+ keyValue.toString();
											aliasValueSql += ",'"
													+ aliasCellmapValue + "'";
										}
										break;
									case 1:
										// smap.put(keyValue.toString(),
										// "'"+aliasValueMap.get(keyValue)+"'");
										if (initKey == 1) {
											aliasKeySql = keyValue.toString();
											if (aliasCellmapValue != null
													&& aliasCellmapValue
															.toString()
															.contains("/")) {
												aliasValueSql = "to_date('"
														+ aliasCellmapValue
														+ "','YYYY/MM/DD HH24:MI')";// "'"+aliasCellmapValue+"'";
											} else if (aliasCellmapValue != null
													&& aliasCellmapValue
															.toString()
															.contains("-")) {
												aliasValueSql = "to_date('"
														+ aliasCellmapValue
														+ "','YYYY-MM-DD HH24:MI')";// "'"+aliasCellmapValue+"'";
											}
										} else {
											aliasKeySql += ","
													+ keyValue.toString();
											if (aliasCellmapValue != null
													&& aliasCellmapValue
															.toString()
															.contains("/")) {
												aliasValueSql += ",to_date('"
														+ aliasCellmapValue
														+ "','YYYY/MM/DD HH24:MI')";// "'"+aliasCellmapValue+"'";
											} else if (aliasCellmapValue != null
													&& aliasCellmapValue
															.toString()
															.contains("-")) {
												aliasValueSql += ",to_date('"
														+ aliasCellmapValue
														+ "','YYYY-MM-DD HH24:MI')";// "'"+aliasCellmapValue+"'";
											} else if (aliasCellmapValue == "") {
												aliasValueSql += ",'' ";
											}
										}
										break;
									case 11:
										if (initKey == 1) {
											aliasKeySql = keyValue.toString();
											if (aliasCellmapValue != null
													&& aliasCellmapValue
															.toString()
															.contains("/")) {
												aliasValueSql = "to_date('"
														+ aliasCellmapValue
														+ "','YYYY/MM/DD HH24:MI:SS')";// "'"+aliasCellmapValue+"'";
											} else if (aliasCellmapValue != null
													&& aliasCellmapValue
															.toString()
															.contains("-")) {
												aliasValueSql = "to_date('"
														+ aliasCellmapValue
														+ "','YYYY-MM-DD HH24:MI:SS')";// "'"+aliasCellmapValue+"'";
											}
										} else {
											aliasKeySql += ","
													+ keyValue.toString();
											if (aliasCellmapValue != null
													&& aliasCellmapValue
															.toString()
															.contains("/")) {
												aliasValueSql += ",to_date('"
														+ aliasCellmapValue
														+ "','YYYY/MM/DD HH24:MI:SS')";// "'"+aliasCellmapValue+"'";
											} else if (aliasCellmapValue != null
													&& aliasCellmapValue
															.toString()
															.contains("-")) {
												aliasValueSql += ",to_date('"
														+ aliasCellmapValue
														+ "','YYYY-MM-DD HH24:MI:SS')";
											}
										}
										break;
									case 12:
										if (initKey == 1) {
											aliasKeySql = keyValue.toString();
											if (aliasCellmapValue != null
													&& aliasCellmapValue
															.toString()
															.contains("/")) {
												aliasValueSql = "to_date('"
														+ aliasCellmapValue
														+ "','YYYY/MM/DD HH24:MI')";// "'"+aliasCellmapValue+"'";
											} else if (aliasCellmapValue != null
													&& aliasCellmapValue
															.toString()
															.contains("-")) {
												aliasValueSql = "to_date('"
														+ aliasCellmapValue
														+ "','YYYY-MM-DD HH24:MI')";// "'"+aliasCellmapValue+"'";
											}
										} else {
											aliasKeySql += ","
													+ keyValue.toString();
											if (aliasCellmapValue != null
													&& aliasCellmapValue
															.toString()
															.contains("/")) {
												aliasValueSql += ",to_date('"
														+ aliasCellmapValue
														+ "','YYYY/MM/DD HH24:MI')";// "'"+aliasCellmapValue+"'";
											} else if (aliasCellmapValue != null
													&& aliasCellmapValue
															.toString()
															.contains("-")) {
												aliasValueSql += ",to_date('"
														+ aliasCellmapValue
														+ "','YYYY-MM-DD HH24:MI')";
											}
										}
										break;
									default:
										if (initKey == 1) {
											aliasKeySql = keyValue.toString();
											aliasValueSql = (aliasCellmapValue == null ? "null"
													: aliasCellmapValue
															.toString());
										} else {
											aliasKeySql += ","
													+ keyValue.toString();
											aliasValueSql += ","
													+ (aliasCellmapValue == null ? "null"
															: aliasCellmapValue
																	.toString())
													+ "";
										}
										break;
									}
								}
							}
						}
					}
					smap.put("tableName", tableName);
					smap.put("aliasNameKeyContent", aliasKeySql);
					smap.put("aliasValueContent", aliasValueSql);
					addList.add(smap);
				}
			}
			dataMap.put("addList", addList);
			dataMap.put("CPNY_ID", admin.getCpnyId());
			dataMap.put("CREATED_BY", admin.getPersonId());
			dataMap.put("UPLOAD_BY", admin.getPersonId());
			this.insertExcelDataPaForLeftApply(dataMap);
			token = 1;
		} catch (Exception e) { // 未找到文件异常
			e.printStackTrace();
			token = 0;
		}
		return token;
	}

	@SuppressWarnings( { "unchecked" })
	public int impDataInsObject(HttpServletRequest request, String adminID,
			LinkedHashMap cellmap) {
		int token = 0;
		int k = path.indexOf("\\");
		String server = "win";
		if (k < 0) { // WebServer的os为unix/linux
			server = "u/l";
		}
		File file = null;
		if ("win".equals(server)) {
			file = new File(path + "\\" + filename + ".xls");
		} else {
			file = new File(path + "/" + filename + ".xls");
		}
		Map dataMap = new LinkedHashMap();
		try {
			Workbook workBook = Workbook.getWorkbook(file);
			// 获得第一个工作表对象
			Sheet sheet = workBook.getSheet(0);
			// 验证是否存在导入数据
			if (sheet.getRows() < 2) {
				logger.debug("Import file not exist data, return.");
				return token;
			}
			String tableName = cellmap.get("tableName") == null ? "" : cellmap
					.get("tableName").toString();// 获取表名
			LinkedHashMap aliasValueMap = cellmap.get("aliasValueMap") == null ? null
					: (LinkedHashMap) cellmap.get("aliasValueMap");
			LinkedHashMap aliasTypeMap = cellmap.get("aliasTypeMap") == null ? null
					: (LinkedHashMap) cellmap.get("aliasTypeMap");
			LinkedHashMap aliasValueI18nMap = cellmap.get("aliasValueI18nMap") == null ? null
					: (LinkedHashMap) cellmap.get("aliasValueI18nMap");
			String aliasNullStr = cellmap.get("aliasNullStr") == null ? ""
					: cellmap.get("aliasNullStr").toString();

			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			LinkedHashMap aliasMapCells = new LinkedHashMap();
			if (aliasValueMap != null && aliasTypeMap != null) {// 存放CELL0：NUMBER类型的键值对
				Set keySet = aliasValueMap.keySet();
				if (keySet != null) {
					Iterator it = keySet.iterator();
					while (it.hasNext()) {
						Object keyvalue = it.next();
						if (keyvalue != null) {
							Object aliasValue = aliasValueMap.get(keyvalue);
							if (aliasValue != null) {
								aliasMapCells.put(aliasValue.toString(),
										aliasTypeMap.get(keyvalue));
							}
						}
					}
				}
			}
			List addList = new ArrayList();
			// 循环工作表的每行数据
			int colCnt = 0;
			int checkFlag = 0;
			Cell[] headerRow = sheet.getRow(0);
			colCnt = sheet.getRow(0).length;
			for (int i = 1; i < sheet.getRows(); i++) {
				Cell[] row = sheet.getRow(i);
				// 验证当前行是否符合规范
				checkFlag = validateCellForRequiredColum(headerRow, row, colCnt);
				if (checkFlag != 1) {
					// 不符合规范
					break;
				} else {
					// 符合规范
					LinkedHashMap smap = new LinkedHashMap();
					LinkedHashMap aliasCellmap = new LinkedHashMap();
					for (int j = 0; j < colCnt; j++) {
						try {
							if (j < row.length) {
								String cellValue = "";
								Object cellType = aliasMapCells.get("#CELL" + j
										+ "#");
								if (cellType != null
										&& !row[j].getContents().equals("")) {// date类型
									try {
										cellValue = this.FormateData(row[j],
												cellType);
										if (cellValue.contains("/")) {
											String[] contentStr = cellValue
													.split("/");
											if (contentStr.length == 3
													&& contentStr[0].length() == 2
													&& contentStr[2].length() == 4) {
												cellValue = contentStr[2] + "/"
														+ contentStr[1] + "/"
														+ contentStr[0];
											}
										} else if (cellValue.contains("-")) {
											String[] contentStr = cellValue
													.split("-");
											if (contentStr.length == 3
													&& contentStr[0].length() == 2
													&& contentStr[2].length() == 4) {
												cellValue = contentStr[2] + "-"
														+ contentStr[1] + "-"
														+ contentStr[0];
											}
										}
									} catch (Exception e) {
										cellValue = "";
										if (row[j].getContents() != null) {
											cellValue = row[j].getContents();
											if (cellValue.contains("/")) {
												String[] contentStr = cellValue
														.split("/");
												if (contentStr.length == 3
														&& contentStr[0]
																.length() == 2
														&& contentStr[2]
																.length() == 4) {
													cellValue = contentStr[2]
															+ "/"
															+ contentStr[1]
															+ "/"
															+ contentStr[0];
												}
											} else if (cellValue.contains("-")) {
												String[] contentStr = cellValue
														.split("-");
												if (contentStr.length == 3
														&& contentStr[0]
																.length() == 2
														&& contentStr[2]
																.length() == 4) {
													cellValue = contentStr[2]
															+ "-"
															+ contentStr[1]
															+ "-"
															+ contentStr[0];
												}
											}
										}
									}
								} else {
									cellValue = row[j].getContents() == null ? ""
											: row[j].getContents();
								}
								aliasCellmap.put("#CELL" + j + "#",
										cellValue == null ? "" : cellValue
												.trim());
							} else {
								aliasCellmap.put("#CELL" + j + "#", "");
							}
						} catch (Exception e) {
							e.printStackTrace();
							// 表有空值或有空单元行，请检查！
							return token = 2;
						}
					}
					String aliasKeySql = "";
					String aliasValueSql = "";
					int colKey = 0;
					if (aliasValueMap != null && aliasTypeMap != null) {
						Set keySet = aliasValueMap.keySet();
						if (keySet != null) {
							Iterator it = keySet.iterator();
							while (it.hasNext()) {
								colKey++;
								Object keyValue = it.next();
								if (keyValue != null && !keyValue.equals("")) {
									int fieldType = Integer
											.parseInt(aliasTypeMap
													.get(keyValue).toString());
									Object aliasCellmapValue = aliasCellmap
											.get(aliasValueMap.get(keyValue));
									if (aliasCellmapValue == null) {// 不属于excel的列
										aliasCellmapValue = aliasValueMap
												.get(keyValue);
										if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("#CELL")) {
											String sqlContent = aliasCellmapValue
													.toString();
											for (int l = 0; l < colCnt; l++) {
												sqlContent = sqlContent
														.replaceAll(
																"#CELL" + l
																		+ "#",
																aliasCellmap
																		.get(
																				"#CELL"
																						+ l
																						+ "#")
																		.toString());
												if (!sqlContent
														.contains("#CELL")) {
													break;
												}
											}
											LinkedHashMap sqlI8nContentMap = new LinkedHashMap();
											switch (fieldType) {
											case 2:
												sqlI8nContentMap.put(
														"sqlI18nContent",
														sqlContent);
												aliasCellmapValue = this
														.getCodeNoByName(sqlI8nContentMap) == -1 ? ""
														: this
																.getCodeNoByName(sqlI8nContentMap);
												break;
											default:
												sqlI8nContentMap.put(
														"sqlContent",
														sqlContent);
												if (this
														.getContentNoByFiled(sqlI8nContentMap) != null) {
													Object cellObj = this
															.getContentNoByFiled(
																	sqlI8nContentMap)
															.size() == 0 ? ""
															: this
																	.getContentNoByFiled(
																			sqlI8nContentMap)
																	.get(0);
													if (cellObj != null
															&& !cellObj
																	.equals("")
															&& ((Map) cellObj)
																	.get(keyValue
																			.toString()) != null) {
														aliasCellmapValue = (cellObj == null ? ""
																: ((Map) cellObj)
																		.get(
																				keyValue
																						.toString()
																						.toUpperCase())
																		.toString());
													} else {
														aliasCellmapValue = "";
													}
												}

												break;
											}
										}
									} else {
										if (aliasValueI18nMap != null
												&& aliasValueI18nMap
														.get(keyValue) != null) {// 有需要从国际化表读取No的列
											String sqlContent = aliasValueI18nMap
													.get(keyValue) == null ? ""
													: aliasValueI18nMap.get(
															keyValue)
															.toString();
											sqlContent = sqlContent
													.replaceAll(
															aliasValueMap
																	.get(keyValue) == null ? ""
																	: aliasValueMap
																			.get(
																					keyValue)
																			.toString(),
															"'"
																	+ aliasCellmapValue
																			.toString()
																	+ "'");

											LinkedHashMap sqlI8nContentMap = new LinkedHashMap();
											sqlI8nContentMap.put(
													"sqlI18nContent",
													sqlContent);
											if (aliasValueI18nMap.get(keyValue
													+ "_FLAG") != null
													&& aliasValueI18nMap.get(
															keyValue + "_FLAG")
															.equals("Y")) {
												sqlI8nContentMap.put(
														"sqlContent",
														sqlContent);
												List contentList = this
														.getContentNoByFiled(sqlI8nContentMap);
												if (contentList.get(0) != null
														&& ((Map) (contentList
																.get(0)))
																.get("CPNY_ID") != null) {
													aliasCellmapValue = (contentList == null ? ""
															: ((Map) (contentList
																	.get(0)))
																	.get(
																			"CPNY_ID")
																	.toString());
												} else if (contentList.get(0) != null
														&& ((Map) (contentList
																.get(0)))
																.get("DEPTNO") != null) {
													aliasCellmapValue = (contentList == null ? ""
															: ((Map) (contentList
																	.get(0)))
																	.get(
																			"DEPTNO")
																	.toString());
												} else {
													aliasCellmapValue = "";
												}
											} else {
												aliasCellmapValue = this
														.getCodeNoByName(sqlI8nContentMap) == -1 ? ""
														: this
																.getCodeNoByName(sqlI8nContentMap);
											}
											if (aliasCellmapValue.equals("")) {
												System.out
														.println(aliasValueMap
																.get(keyValue)
																.toString()
																+ "------");
											}
										}
									}
									switch (fieldType) {
									case 0:
										if (colKey == 1) {
											aliasKeySql = keyValue.toString();
											aliasValueSql = "'"
													+ aliasCellmapValue + "'";
										} else {
											aliasKeySql += ","
													+ keyValue.toString();
											aliasValueSql += ",'"
													+ aliasCellmapValue + "'";
										}
										break;
									case 1:
										if (colKey == 1) {
											aliasKeySql = keyValue.toString();
											if (aliasCellmapValue != null
													&& aliasCellmapValue
															.toString()
															.contains("/")) {
												aliasValueSql = "to_date('"
														+ aliasCellmapValue
														+ "','YYYY/MM/DD HH24:MI')";// "'"+aliasCellmapValue+"'";
											} else if (aliasCellmapValue != null
													&& aliasCellmapValue
															.toString()
															.contains("-")) {
												aliasValueSql = "to_date('"
														+ aliasCellmapValue
														+ "','YYYY-MM-DD HH24:MI')";// "'"+aliasCellmapValue+"'";
											}
										} else {
											aliasKeySql += ","
													+ keyValue.toString();
											if (aliasCellmapValue != null
													&& aliasCellmapValue
															.toString()
															.contains("/")) {
												aliasValueSql += ",to_date('"
														+ aliasCellmapValue
														+ "','YYYY/MM/DD HH24:MI')";// "'"+aliasCellmapValue+"'";
											} else if (aliasCellmapValue != null
													&& aliasCellmapValue
															.toString()
															.contains("-")) {
												aliasValueSql += ",to_date('"
														+ aliasCellmapValue
														+ "','YYYY-MM-DD HH24:MI')";// "'"+aliasCellmapValue+"'";
											}
										}
										break;
									case 11:
										if (colKey == 1) {
											aliasKeySql = keyValue.toString();
											if (aliasCellmapValue != null
													&& aliasCellmapValue
															.toString()
															.contains("/")) {
												aliasValueSql = "to_date('"
														+ aliasCellmapValue
														+ "','YYYY/MM/DD HH24:MI:SS')";// "'"+aliasCellmapValue+"'";
											} else if (aliasCellmapValue != null
													&& aliasCellmapValue
															.toString()
															.contains("-")) {
												aliasValueSql = "to_date('"
														+ aliasCellmapValue
														+ "','YYYY-MM-DD HH24:MI:SS')";// "'"+aliasCellmapValue+"'";
											}
										} else {
											aliasKeySql += ","
													+ keyValue.toString();
											if (aliasCellmapValue != null
													&& aliasCellmapValue
															.toString()
															.contains("/")) {
												aliasValueSql += ",to_date('"
														+ aliasCellmapValue
														+ "','YYYY/MM/DD HH24:MI:SS')";// "'"+aliasCellmapValue+"'";
											} else if (aliasCellmapValue != null
													&& aliasCellmapValue
															.toString()
															.contains("-")) {
												aliasValueSql += ",to_date('"
														+ aliasCellmapValue
														+ "','YYYY-MM-DD HH24:MI:SS')";
											}
										}
										break;
									case 12:
										if (colKey == 1) {
											aliasKeySql = keyValue.toString();
											if (aliasCellmapValue != null
													&& aliasCellmapValue
															.toString()
															.contains("/")) {
												aliasValueSql = "to_date('"
														+ aliasCellmapValue
														+ "','YYYY/MM/DD HH24:MI')";// "'"+aliasCellmapValue+"'";
											} else if (aliasCellmapValue != null
													&& aliasCellmapValue
															.toString()
															.contains("-")) {
												aliasValueSql = "to_date('"
														+ aliasCellmapValue
														+ "','YYYY-MM-DD HH24:MI')";// "'"+aliasCellmapValue+"'";
											}
										} else {
											aliasKeySql += ","
													+ keyValue.toString();
											if (aliasCellmapValue != null
													&& aliasCellmapValue
															.toString()
															.contains("/")) {
												aliasValueSql += ",to_date('"
														+ aliasCellmapValue
														+ "','YYYY/MM/DD HH24:MI')";// "'"+aliasCellmapValue+"'";
											} else if (aliasCellmapValue != null
													&& aliasCellmapValue
															.toString()
															.contains("-")) {
												aliasValueSql += ",to_date('"
														+ aliasCellmapValue
														+ "','YYYY-MM-DD HH24:MI')";
											}
										}
										break;
									default:
										if (colKey == 1) {
											aliasKeySql = keyValue.toString();
											aliasValueSql = (aliasCellmapValue == null ? "null"
													: aliasCellmapValue
															.toString());
										} else {
											aliasKeySql += ","
													+ keyValue.toString();
											aliasValueSql += ","
													+ (aliasCellmapValue == null ? "null"
															: (aliasCellmapValue
																	.equals("") ? "null"
																	: aliasCellmapValue
																			.toString()));
										}
										break;
									}
								}
							}
						}
					}
					smap.put("tableName", tableName);
					smap.put("aliasNameKeyContent", aliasKeySql);
					smap.put("aliasValueContent", aliasValueSql);
					addList.add(smap);
				}
			}
			dataMap.put("addList", addList);
			dataMap.put("CPNY_ID", admin.getCpnyId());
			dataMap.put("CREATED_BY", admin.getPersonId());
			dataMap.put("UPLOAD_BY", admin.getPersonId());
			this.excelUtilDao.insertExcelDataInsObject(dataMap);
			token = 1;
		} catch (Exception e) { // 未找到文件异常
			e.printStackTrace();
			token = 0;
		}
		return token;
	}

	@SuppressWarnings( { "unchecked" })
	public int importFundCalcObjectData(HttpServletRequest request,
			String adminID, LinkedHashMap cellmap) {
		int token = 0;
		int k = path.indexOf("\\");
		String server = "win";
		if (k < 0) { // WebServer的os为unix/linux
			server = "u/l";
		}
		File file = null;
		if ("win".equals(server)) {
			file = new File(path + "\\" + filename + ".xls");
		} else {
			file = new File(path + "/" + filename + ".xls");
		}
		Map dataMap = new LinkedHashMap();
		try {
			Workbook workBook = Workbook.getWorkbook(file);
			// 获得第一个工作表对象
			Sheet sheet = workBook.getSheet(0);
			// 验证是否存在导入数据
			if (sheet.getRows() < 2) {
				logger.debug("Import file not exist data, return.");
				return token;
			}
			String tableName = cellmap.get("tableName") == null ? "" : cellmap
					.get("tableName").toString();// 获取表名
			LinkedHashMap aliasValueMap = cellmap.get("aliasValueMap") == null ? null
					: (LinkedHashMap) cellmap.get("aliasValueMap");
			LinkedHashMap aliasTypeMap = cellmap.get("aliasTypeMap") == null ? null
					: (LinkedHashMap) cellmap.get("aliasTypeMap");
			LinkedHashMap aliasValueI18nMap = cellmap.get("aliasValueI18nMap") == null ? null
					: (LinkedHashMap) cellmap.get("aliasValueI18nMap");
			String aliasNullStr = cellmap.get("aliasNullStr") == null ? ""
					: cellmap.get("aliasNullStr").toString();

			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			LinkedHashMap aliasMapCells = new LinkedHashMap();
			if (aliasValueMap != null && aliasTypeMap != null) {// 存放CELL0：NUMBER类型的键值对
				Set keySet = aliasValueMap.keySet();
				if (keySet != null) {
					Iterator it = keySet.iterator();
					while (it.hasNext()) {
						Object keyvalue = it.next();
						if (keyvalue != null) {
							Object aliasValue = aliasValueMap.get(keyvalue);
							if (aliasValue != null) {
								aliasMapCells.put(aliasValue.toString(),
										aliasTypeMap.get(keyvalue));
							}
						}
					}
				}
			}
			List addList = new ArrayList();
			// 循环工作表的每行数据
			int colCnt = 0;
			int checkFlag = 0;
			Cell[] headerRow = sheet.getRow(0);
			colCnt = sheet.getRow(0).length;
			for (int i = 1; i < sheet.getRows(); i++) {
				Cell[] row = sheet.getRow(i);
				// 验证当前行是否符合规范
				checkFlag = validateCellForRequiredColum(headerRow, row, colCnt);
				if (checkFlag != 1) {
					// 不符合规范
					break;
				} else {
					// 符合规范
					LinkedHashMap smap = new LinkedHashMap();
					LinkedHashMap aliasCellmap = new LinkedHashMap();
					for (int j = 0; j < colCnt; j++) {
						try {
							if (j < row.length) {
								String cellValue = "";
								Object cellType = aliasMapCells.get("#CELL" + j
										+ "#");
								if (cellType != null
										&& !row[j].getContents().equals("")) {// date类型
									try {
										cellValue = this.FormateData(row[j],
												cellType);
										if (cellValue.contains("/")) {
											String[] contentStr = cellValue
													.split("/");
											if (contentStr.length == 3
													&& contentStr[0].length() == 2
													&& contentStr[2].length() == 4) {
												cellValue = contentStr[2] + "/"
														+ contentStr[1] + "/"
														+ contentStr[0];
											}
										} else if (cellValue.contains("-")) {
											String[] contentStr = cellValue
													.split("-");
											if (contentStr.length == 3
													&& contentStr[0].length() == 2
													&& contentStr[2].length() == 4) {
												cellValue = contentStr[2] + "-"
														+ contentStr[1] + "-"
														+ contentStr[0];
											}
										}
									} catch (Exception e) {
										cellValue = "";
										if (row[j].getContents() != null) {
											cellValue = row[j].getContents();
											if (cellValue.contains("/")) {
												String[] contentStr = cellValue
														.split("/");
												if (contentStr.length == 3
														&& contentStr[0]
																.length() == 2
														&& contentStr[2]
																.length() == 4) {
													cellValue = contentStr[2]
															+ "/"
															+ contentStr[1]
															+ "/"
															+ contentStr[0];
												}
											} else if (cellValue.contains("-")) {
												String[] contentStr = cellValue
														.split("-");
												if (contentStr.length == 3
														&& contentStr[0]
																.length() == 2
														&& contentStr[2]
																.length() == 4) {
													cellValue = contentStr[2]
															+ "-"
															+ contentStr[1]
															+ "-"
															+ contentStr[0];
												}
											}
										}
									}
								} else {
									cellValue = row[j].getContents() == null ? ""
											: row[j].getContents();
								}
								aliasCellmap.put("#CELL" + j + "#",
										cellValue == null ? "" : cellValue
												.trim());
							} else {
								aliasCellmap.put("#CELL" + j + "#", "");
							}
						} catch (Exception e) {
							e.printStackTrace();
							// 表有空值或有空单元行，请检查！
							return token = 2;
						}
					}
					String aliasKeySql = "";
					String aliasValueSql = "";
					int colKey = 0;
					if (aliasValueMap != null && aliasTypeMap != null) {
						Set keySet = aliasValueMap.keySet();
						if (keySet != null) {
							Iterator it = keySet.iterator();
							while (it.hasNext()) {
								colKey++;
								Object keyValue = it.next();
								if (keyValue != null && !keyValue.equals("")) {
									int fieldType = Integer
											.parseInt(aliasTypeMap
													.get(keyValue).toString());
									Object aliasCellmapValue = aliasCellmap
											.get(aliasValueMap.get(keyValue));
									if (aliasCellmapValue == null) {// 不属于excel的列
										aliasCellmapValue = aliasValueMap
												.get(keyValue);
										if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("#CELL")) {
											String sqlContent = aliasCellmapValue
													.toString();
											for (int l = 0; l < colCnt; l++) {
												sqlContent = sqlContent
														.replaceAll(
																"#CELL" + l
																		+ "#",
																aliasCellmap
																		.get(
																				"#CELL"
																						+ l
																						+ "#")
																		.toString());
												if (!sqlContent
														.contains("#CELL")) {
													break;
												}
											}
											LinkedHashMap sqlI8nContentMap = new LinkedHashMap();
											switch (fieldType) {
											case 2:
												sqlI8nContentMap.put(
														"sqlI18nContent",
														sqlContent);
												aliasCellmapValue = this
														.getCodeNoByName(sqlI8nContentMap) == -1 ? ""
														: this
																.getCodeNoByName(sqlI8nContentMap);
												break;
											default:
												sqlI8nContentMap.put(
														"sqlContent",
														sqlContent);
												if (this
														.getContentNoByFiled(sqlI8nContentMap) != null) {
													Object cellObj = this
															.getContentNoByFiled(
																	sqlI8nContentMap)
															.size() == 0 ? ""
															: this
																	.getContentNoByFiled(
																			sqlI8nContentMap)
																	.get(0);
													if (cellObj != null
															&& !cellObj
																	.equals("")
															&& ((Map) cellObj)
																	.get(keyValue
																			.toString()) != null) {
														aliasCellmapValue = (cellObj == null ? ""
																: ((Map) cellObj)
																		.get(
																				keyValue
																						.toString()
																						.toUpperCase())
																		.toString());
													} else {
														aliasCellmapValue = "";
													}
												}

												break;
											}
										}
									} else {
										if (aliasValueI18nMap != null
												&& aliasValueI18nMap
														.get(keyValue) != null) {// 有需要从国际化表读取No的列
											String sqlContent = aliasValueI18nMap
													.get(keyValue) == null ? ""
													: aliasValueI18nMap.get(
															keyValue)
															.toString();
											sqlContent = sqlContent
													.replaceAll(
															aliasValueMap
																	.get(keyValue) == null ? ""
																	: aliasValueMap
																			.get(
																					keyValue)
																			.toString(),
															"'"
																	+ aliasCellmapValue
																			.toString()
																	+ "'");

											LinkedHashMap sqlI8nContentMap = new LinkedHashMap();
											sqlI8nContentMap.put(
													"sqlI18nContent",
													sqlContent);
											if (aliasValueI18nMap.get(keyValue
													+ "_FLAG") != null
													&& aliasValueI18nMap.get(
															keyValue + "_FLAG")
															.equals("Y")) {
												sqlI8nContentMap.put(
														"sqlContent",
														sqlContent);
												List contentList = this
														.getContentNoByFiled(sqlI8nContentMap);
												if (contentList.get(0) != null
														&& ((Map) (contentList
																.get(0)))
																.get("CPNY_ID") != null) {
													aliasCellmapValue = (contentList == null ? ""
															: ((Map) (contentList
																	.get(0)))
																	.get(
																			"CPNY_ID")
																	.toString());
												} else if (contentList.get(0) != null
														&& ((Map) (contentList
																.get(0)))
																.get("DEPTNO") != null) {
													aliasCellmapValue = (contentList == null ? ""
															: ((Map) (contentList
																	.get(0)))
																	.get(
																			"DEPTNO")
																	.toString());
												} else {
													aliasCellmapValue = "";
												}
											} else {
												aliasCellmapValue = this
														.getCodeNoByName(sqlI8nContentMap) == -1 ? ""
														: this
																.getCodeNoByName(sqlI8nContentMap);
											}
											if (aliasCellmapValue.equals("")) {
												System.out
														.println(aliasValueMap
																.get(keyValue)
																.toString()
																+ "------");
											}
										}
									}
									switch (fieldType) {
									case 0:
										if (colKey == 1) {
											aliasKeySql = keyValue.toString();
											aliasValueSql = "'"
													+ aliasCellmapValue + "'";
										} else {
											aliasKeySql += ","
													+ keyValue.toString();
											aliasValueSql += ",'"
													+ aliasCellmapValue + "'";
										}
										break;
									case 1:
										if (colKey == 1) {
											aliasKeySql = keyValue.toString();
											if (aliasCellmapValue != null
													&& aliasCellmapValue
															.toString()
															.contains("/")) {
												aliasValueSql = "to_date('"
														+ aliasCellmapValue
														+ "','YYYY/MM/DD HH24:MI')";// "'"+aliasCellmapValue+"'";
											} else if (aliasCellmapValue != null
													&& aliasCellmapValue
															.toString()
															.contains("-")) {
												aliasValueSql = "to_date('"
														+ aliasCellmapValue
														+ "','YYYY-MM-DD HH24:MI')";// "'"+aliasCellmapValue+"'";
											}
										} else {
											aliasKeySql += ","
													+ keyValue.toString();
											if (aliasCellmapValue != null
													&& aliasCellmapValue
															.toString()
															.contains("/")) {
												aliasValueSql += ",to_date('"
														+ aliasCellmapValue
														+ "','YYYY/MM/DD HH24:MI')";// "'"+aliasCellmapValue+"'";
											} else if (aliasCellmapValue != null
													&& aliasCellmapValue
															.toString()
															.contains("-")) {
												aliasValueSql += ",to_date('"
														+ aliasCellmapValue
														+ "','YYYY-MM-DD HH24:MI')";// "'"+aliasCellmapValue+"'";
											}
										}
										break;
									case 11:
										if (colKey == 1) {
											aliasKeySql = keyValue.toString();
											if (aliasCellmapValue != null
													&& aliasCellmapValue
															.toString()
															.contains("/")) {
												aliasValueSql = "to_date('"
														+ aliasCellmapValue
														+ "','YYYY/MM/DD HH24:MI:SS')";// "'"+aliasCellmapValue+"'";
											} else if (aliasCellmapValue != null
													&& aliasCellmapValue
															.toString()
															.contains("-")) {
												aliasValueSql = "to_date('"
														+ aliasCellmapValue
														+ "','YYYY-MM-DD HH24:MI:SS')";// "'"+aliasCellmapValue+"'";
											}
										} else {
											aliasKeySql += ","
													+ keyValue.toString();
											if (aliasCellmapValue != null
													&& aliasCellmapValue
															.toString()
															.contains("/")) {
												aliasValueSql += ",to_date('"
														+ aliasCellmapValue
														+ "','YYYY/MM/DD HH24:MI:SS')";// "'"+aliasCellmapValue+"'";
											} else if (aliasCellmapValue != null
													&& aliasCellmapValue
															.toString()
															.contains("-")) {
												aliasValueSql += ",to_date('"
														+ aliasCellmapValue
														+ "','YYYY-MM-DD HH24:MI:SS')";
											}
										}
										break;
									case 12:
										if (colKey == 1) {
											aliasKeySql = keyValue.toString();
											if (aliasCellmapValue != null
													&& aliasCellmapValue
															.toString()
															.contains("/")) {
												aliasValueSql = "to_date('"
														+ aliasCellmapValue
														+ "','YYYY/MM/DD HH24:MI')";// "'"+aliasCellmapValue+"'";
											} else if (aliasCellmapValue != null
													&& aliasCellmapValue
															.toString()
															.contains("-")) {
												aliasValueSql = "to_date('"
														+ aliasCellmapValue
														+ "','YYYY-MM-DD HH24:MI')";// "'"+aliasCellmapValue+"'";
											}
										} else {
											aliasKeySql += ","
													+ keyValue.toString();
											if (aliasCellmapValue != null
													&& aliasCellmapValue
															.toString()
															.contains("/")) {
												aliasValueSql += ",to_date('"
														+ aliasCellmapValue
														+ "','YYYY/MM/DD HH24:MI')";// "'"+aliasCellmapValue+"'";
											} else if (aliasCellmapValue != null
													&& aliasCellmapValue
															.toString()
															.contains("-")) {
												aliasValueSql += ",to_date('"
														+ aliasCellmapValue
														+ "','YYYY-MM-DD HH24:MI')";
											}
										}
										break;
									default:
										if (colKey == 1) {
											aliasKeySql = keyValue.toString();
											aliasValueSql = (aliasCellmapValue == null ? "null"
													: aliasCellmapValue
															.toString());
										} else {
											aliasKeySql += ","
													+ keyValue.toString();
											aliasValueSql += ","
													+ (aliasCellmapValue == null ? "null"
															: (aliasCellmapValue
																	.equals("") ? "null"
																	: aliasCellmapValue
																			.toString()));
										}
										break;
									}
								}
							}
						}
					}
					smap.put("tableName", tableName);
					smap.put("aliasNameKeyContent", aliasKeySql);
					smap.put("aliasValueContent", aliasValueSql);
					addList.add(smap);
				}
			}
			dataMap.put("addList", addList);
			dataMap.put("CPNY_ID", admin.getCpnyId());
			dataMap.put("CREATED_BY", admin.getPersonId());
			dataMap.put("UPLOAD_BY", admin.getPersonId());
			this.excelUtilDao.insertExcelDataFundObject(dataMap);
			token = 1;
		} catch (Exception e) { // 未找到文件异常
			e.printStackTrace();
			token = 0;
		}
		return token;
	}

	@SuppressWarnings( { "unchecked" })
	public int importTempDataLF(HttpServletRequest request, String adminID,
			LinkedHashMap cellmap, String importName) {
		LinkedHashMap dataMap = new LinkedHashMap();
		int token = 0;
		dataMap.put("token", token);
		int k = path.indexOf("\\");
		String server = "win";
		if (k < 0) { // WebServer的os为unix/linux
			server = "u/l";
		}
		File file = null;
		if ("win".equals(server)) {
			file = new File(path + "\\" + filename + ".xls");
		} else {
			file = new File(path + "/" + filename + ".xls");
		}
		try {
			Workbook workBook = Workbook.getWorkbook(file);
			// 获得第一个工作表对象
			Sheet sheet = workBook.getSheet(0);
			// 验证是否存在导入数据
			if (sheet.getRows() < 2) {
				logger.debug("Import file not exist data, return.");
				logger.debug("Import file not exist data, return.");
				return token;
			}
			String tableName = cellmap.get("tableName") == null ? "" : cellmap
					.get("tableName").toString();// 获取表名
			LinkedHashMap aliasValueMap = cellmap.get("aliasValueMap") == null ? null
					: (LinkedHashMap) cellmap.get("aliasValueMap");
			LinkedHashMap aliasTypeMap = cellmap.get("aliasTypeMap") == null ? null
					: (LinkedHashMap) cellmap.get("aliasTypeMap");
			LinkedHashMap aliasValueI18nMap = cellmap.get("aliasValueI18nMap") == null ? null
					: (LinkedHashMap) cellmap.get("aliasValueI18nMap");
			String aliasNullStr = cellmap.get("aliasNullStr") == null ? ""
					: cellmap.get("aliasNullStr").toString();

			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			LinkedHashMap aliasMapCells = new LinkedHashMap();
			if (aliasValueMap != null && aliasTypeMap != null) {// 存放CELL0：NUMBER类型的键值对
				Set keySet = aliasValueMap.keySet();
				if (keySet != null) {
					Iterator it = keySet.iterator();
					while (it.hasNext()) {
						Object keyvalue = it.next();
						if (keyvalue != null) {
							Object aliasValue = aliasValueMap.get(keyvalue);
							if (aliasValue != null) {
								aliasMapCells.put(aliasValue.toString(),
										aliasTypeMap.get(keyvalue));
							}
						}
					}
				}
			}
			List addList = new ArrayList();
			// 循环工作表的每行数据
			int colCnt = 0;
			int checkFlag = 0;
			Cell[] headerRow = sheet.getRow(0);
			colCnt = sheet.getRow(0).length;
			for (int i = 1; i < sheet.getRows(); i++) {
				Cell[] row = sheet.getRow(i);
				// 验证当前行是否符合规范
				checkFlag = validateCellForRequiredColum(headerRow, row, colCnt);
				if (checkFlag != 1) {
					// 不符合规范
					break;
				} else {
					// 符合规范
					LinkedHashMap smap = new LinkedHashMap();
					LinkedHashMap aliasCellmap = new LinkedHashMap();
					for (int j = 0; j < colCnt; j++) {
						try {
							if (j < row.length) {
								String cellValue = "";
								Object cellType = aliasMapCells.get("#CELL" + j
										+ "#");
								if (cellType != null
										&& !row[j].getContents().equals("")) {// date类型
									try {
										cellValue = this.FormateData(row[j],
												cellType);
										if (cellValue.contains("/")) {
											String[] contentStr = cellValue
													.split("/");
											if (contentStr.length == 3
													&& contentStr[0].length() == 2
													&& contentStr[2].length() == 4) {
												cellValue = contentStr[2] + "/"
														+ contentStr[1] + "/"
														+ contentStr[0];
											}
										} else if (cellValue.contains("-")) {
											String[] contentStr = cellValue
													.split("-");
											if (contentStr.length == 3
													&& contentStr[0].length() == 2
													&& contentStr[2].length() == 4) {
												cellValue = contentStr[2] + "-"
														+ contentStr[1] + "-"
														+ contentStr[0];
											}
										}
									} catch (Exception e) {
										cellValue = "";
										if (row[j].getContents() != null) {
											cellValue = row[j].getContents();
											if (cellValue.contains("/")) {
												String[] contentStr = cellValue
														.split("/");
												if (contentStr.length == 3
														&& contentStr[0]
																.length() == 2
														&& contentStr[2]
																.length() == 4) {
													cellValue = contentStr[2]
															+ "/"
															+ contentStr[1]
															+ "/"
															+ contentStr[0];
												}
											} else if (cellValue.contains("-")) {
												String[] contentStr = cellValue
														.split("-");
												if (contentStr.length == 3
														&& contentStr[0]
																.length() == 2
														&& contentStr[2]
																.length() == 4) {
													cellValue = contentStr[2]
															+ "-"
															+ contentStr[1]
															+ "-"
															+ contentStr[0];
												}
											}
										}
									}
								} else {
									cellValue = row[j].getContents() == null ? ""
											: row[j].getContents();
								}
								aliasCellmap.put("#CELL" + j + "#",
										cellValue == null ? "" : cellValue
												.trim());
							} else {
								aliasCellmap.put("#CELL" + j + "#", "");
							}
						} catch (Exception e) {
							e.printStackTrace();
							// 表有空值或有空单元行，请检查！
							return token = 2;
						}
					}
					String aliasKeySql = "";
					String aliasValueSql = "";
					int colKey = 0;
					if (aliasValueMap != null && aliasTypeMap != null) {
						Set keySet = aliasValueMap.keySet();
						if (keySet != null) {
							Iterator it = keySet.iterator();
							while (it.hasNext()) {
								colKey++;
								Object keyValue = it.next();
								if (keyValue != null && !keyValue.equals("")) {
									int fieldType = Integer
											.parseInt(aliasTypeMap
													.get(keyValue).toString());
									Object aliasCellmapValue = aliasCellmap
											.get(aliasValueMap.get(keyValue));
									if (aliasCellmapValue == null) {// 不属于excel的列
										aliasCellmapValue = aliasValueMap
												.get(keyValue);
										if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("#CELL")) {
											String sqlContent = aliasCellmapValue
													.toString();
											for (int l = 0; l < colCnt; l++) {
												sqlContent = sqlContent
														.replaceAll(
																"#CELL" + l
																		+ "#",
																aliasCellmap
																		.get(
																				"#CELL"
																						+ l
																						+ "#")
																		.toString());
												if (!sqlContent
														.contains("#CELL")) {
													break;
												}
											}
											LinkedHashMap sqlI8nContentMap = new LinkedHashMap();
											switch (fieldType) {
											case 2:
												sqlI8nContentMap.put(
														"sqlI18nContent",
														sqlContent);
												aliasCellmapValue = this
														.getCodeNoByName(sqlI8nContentMap) == -1 ? ""
														: this
																.getCodeNoByName(sqlI8nContentMap);
												break;
											default:
												sqlI8nContentMap.put(
														"sqlContent",
														sqlContent);
												if (this
														.getContentNoByFiled(sqlI8nContentMap) != null) {
													Object cellObj = this
															.getContentNoByFiled(
																	sqlI8nContentMap)
															.size() == 0 ? ""
															: this
																	.getContentNoByFiled(
																			sqlI8nContentMap)
																	.get(0);
													if (cellObj != null
															&& !cellObj
																	.equals("")
															&& ((Map) cellObj)
																	.get(keyValue
																			.toString()) != null) {
														aliasCellmapValue = (cellObj == null ? ""
																: ((Map) cellObj)
																		.get(
																				keyValue
																						.toString()
																						.toUpperCase())
																		.toString());
													} else {
														aliasCellmapValue = "";
													}
												}
												break;
											}
										}
									} else {
										if (aliasValueI18nMap != null
												&& aliasValueI18nMap
														.get(keyValue) != null) {// 有需要从国际化表读取No的列
											String sqlContent = aliasValueI18nMap
													.get(keyValue) == null ? ""
													: aliasValueI18nMap.get(
															keyValue)
															.toString();
											sqlContent = sqlContent
													.replaceAll(
															aliasValueMap
																	.get(keyValue) == null ? ""
																	: aliasValueMap
																			.get(
																					keyValue)
																			.toString(),
															"'"
																	+ aliasCellmapValue
																			.toString()
																	+ "'");
											LinkedHashMap sqlI8nContentMap = new LinkedHashMap();
											sqlI8nContentMap.put(
													"sqlI18nContent",
													sqlContent);
											if (aliasValueI18nMap.get(keyValue
													+ "_FLAG") != null
													&& aliasValueI18nMap.get(
															keyValue + "_FLAG")
															.equals("Y")) {
												sqlI8nContentMap.put(
														"sqlContent",
														sqlContent);
												List contentList = this
														.getContentNoByFiled(sqlI8nContentMap);
												if (contentList.get(0) != null
														&& ((Map) (contentList
																.get(0)))
																.get("CPNY_ID") != null) {
													aliasCellmapValue = (contentList == null ? ""
															: ((Map) (contentList
																	.get(0)))
																	.get(
																			"CPNY_ID")
																	.toString());
												} else if (contentList.get(0) != null
														&& ((Map) (contentList
																.get(0)))
																.get("DEPTNO") != null) {
													aliasCellmapValue = (contentList == null ? ""
															: ((Map) (contentList
																	.get(0)))
																	.get(
																			"DEPTNO")
																	.toString());
												} else {
													aliasCellmapValue = "";
												}
											} else {
												aliasCellmapValue = this
														.getCodeNoByName(sqlI8nContentMap) == -1 ? ""
														: this
																.getCodeNoByName(sqlI8nContentMap);
											}
											if (aliasCellmapValue.equals("")) {
												System.out
														.println(aliasValueMap
																.get(keyValue)
																.toString()
																+ "------");
											}
										}
									}
									switch (fieldType) {
									case 0:
										if (colKey == 1) {
											aliasKeySql = keyValue.toString();
											aliasValueSql = "'"
													+ aliasCellmapValue + "'";
										} else {
											aliasKeySql += ","
													+ keyValue.toString();
											aliasValueSql += ",'"
													+ aliasCellmapValue + "'";
										}
										break;
									case 1:
										if (colKey == 1) {
											aliasKeySql = keyValue.toString();
											if (aliasCellmapValue != null
													&& aliasCellmapValue
															.toString()
															.contains("/")) {
												aliasValueSql = "to_date('"
														+ aliasCellmapValue
														+ "','YYYY/MM/DD HH24:MI')";// "'"+aliasCellmapValue+"'";
											} else if (aliasCellmapValue != null
													&& aliasCellmapValue
															.toString()
															.contains("-")) {
												aliasValueSql = "to_date('"
														+ aliasCellmapValue
														+ "','YYYY-MM-DD HH24:MI')";// "'"+aliasCellmapValue+"'";
											}
										} else {
											aliasKeySql += ","
													+ keyValue.toString();
											if (aliasCellmapValue != null
													&& aliasCellmapValue
															.toString()
															.contains("/")) {
												aliasValueSql += ",to_date('"
														+ aliasCellmapValue
														+ "','YYYY/MM/DD HH24:MI')";// "'"+aliasCellmapValue+"'";
											} else if (aliasCellmapValue != null
													&& aliasCellmapValue
															.toString()
															.contains("-")) {
												aliasValueSql += ",to_date('"
														+ aliasCellmapValue
														+ "','YYYY-MM-DD HH24:MI')";// "'"+aliasCellmapValue+"'";
											}
										}
										break;
									case 11:
										if (colKey == 1) {
											aliasKeySql = keyValue.toString();
											if (aliasCellmapValue != null
													&& aliasCellmapValue
															.toString()
															.contains("/")) {
												aliasValueSql = "to_date('"
														+ aliasCellmapValue
														+ "','YYYY/MM/DD HH24:MI:SS')";// "'"+aliasCellmapValue+"'";
											} else if (aliasCellmapValue != null
													&& aliasCellmapValue
															.toString()
															.contains("-")) {
												aliasValueSql = "to_date('"
														+ aliasCellmapValue
														+ "','YYYY-MM-DD HH24:MI:SS')";// "'"+aliasCellmapValue+"'";
											}
										} else {
											aliasKeySql += ","
													+ keyValue.toString();
											if (aliasCellmapValue != null
													&& aliasCellmapValue
															.toString()
															.contains("/")) {
												aliasValueSql += ",to_date('"
														+ aliasCellmapValue
														+ "','YYYY/MM/DD HH24:MI:SS')";// "'"+aliasCellmapValue+"'";
											} else if (aliasCellmapValue != null
													&& aliasCellmapValue
															.toString()
															.contains("-")) {
												aliasValueSql += ",to_date('"
														+ aliasCellmapValue
														+ "','YYYY-MM-DD HH24:MI:SS')";
											}
										}
										break;
									case 12:
										if (colKey == 1) {
											aliasKeySql = keyValue.toString();
											if (aliasCellmapValue != null
													&& aliasCellmapValue
															.toString()
															.contains("/")) {
												aliasValueSql = "to_date('"
														+ aliasCellmapValue
														+ "','YYYY/MM/DD HH24:MI')";// "'"+aliasCellmapValue+"'";
											} else if (aliasCellmapValue != null
													&& aliasCellmapValue
															.toString()
															.contains("-")) {
												aliasValueSql = "to_date('"
														+ aliasCellmapValue
														+ "','YYYY-MM-DD HH24:MI')";// "'"+aliasCellmapValue+"'";
											}
										} else {
											aliasKeySql += ","
													+ keyValue.toString();
											if (aliasCellmapValue != null
													&& aliasCellmapValue
															.toString()
															.contains("/")) {
												aliasValueSql += ",to_date('"
														+ aliasCellmapValue
														+ "','YYYY/MM/DD HH24:MI')";// "'"+aliasCellmapValue+"'";
											} else if (aliasCellmapValue != null
													&& aliasCellmapValue
															.toString()
															.contains("-")) {
												aliasValueSql += ",to_date('"
														+ aliasCellmapValue
														+ "','YYYY-MM-DD HH24:MI')";
											}
										}
										break;
									default:
										if (colKey == 1) {
											aliasKeySql = keyValue.toString();
											aliasValueSql = (aliasCellmapValue == null ? "null"
													: aliasCellmapValue
															.toString());
										} else {
											aliasKeySql += ","
													+ keyValue.toString();
											aliasValueSql += ","
													+ (aliasCellmapValue == null ? "null"
															: (aliasCellmapValue
																	.equals("") ? "null"
																	: aliasCellmapValue
																			.toString()));
										}
										break;
									}
								}
							}
						}
					}
					smap.put("tableName", tableName);
					smap.put("aliasNameKeyContent", aliasKeySql);
					smap.put("aliasValueContent", aliasValueSql);
					addList.add(smap);
				}
			}
			dataMap.put("addList", addList);
			dataMap.put("CPNY_ID", admin.getCpnyId());
			dataMap.put("CREATED_BY", admin.getPersonId());
			dataMap.put("UPLOAD_BY", admin.getPersonId());
			if (importName != null && "FUND_IMPORT".equals(importName)) {// 插入公积金计算对象
				this.excelUtilDao.insertExcelDataFundObject(dataMap);
			} else if (importName != null
					&& "IS_SETUP_INFO_TEMP".equals(importName)) {// CH保险设置
				this.insertExcelDataIsParam(dataMap);
			} else {// 插入公积金计算对象
				this.excelUtilDao.insertExcelDataFundObject(dataMap);
			}
			token = 1;
		} catch (Exception e) { // 未找到文件异常
			e.printStackTrace();
			token = 0;
		}
		return token;
	}

	/**
	 * validate cell
	 * 
	 * @param pstmt
	 * @param row
	 * @param columns
	 * @throws SQLException
	 */
	private boolean validateCell(Cell[] row, int columns) {
		// 如果当前行的数据和表头不对应返回false
		// if (row.length != columns){
		// return false;
		// }
		// for (int i = 0; i < columns; i++) {
		// // 如果当前行有空的单元格返回false
		// if (row[i].getType() == CellType.EMPTY)
		// return false;
		// }
		// return true;
		// if (row.length != columns){
		// return false;
		// }
		int init = 0;
		if (columns - row.length >= 1) {
			return false;
		}
		for (int i = 0; i < columns; i++) {
			// 如果当前行有空的单元格返回false

			Object cellsContent = row[i].getContents();
			String content = "";
			if (cellsContent != null) {
				content = cellsContent.toString().replaceAll(" ", "");
			}
			if (row[i].getType() == CellType.EMPTY
					|| ((row[i].getType() != CellType.EMPTY) && content
							.equals(""))) {
				init++;
			}
			// return false;
		}
		if (init == columns) {
			return false;
		}
		return true;
	}

	/**
	 * validate cell区分必填与非必填检查
	 * 
	 * @param pstmt
	 * @param row
	 * @param columns
	 * @throws SQLException
	 */
	private ModelMap validateCellForRequiredCol(Cell[] colNm, Cell[] row,
			int colCnt) {
		ModelMap rtn = new ModelMap();
		rtn.put("message", "Pass Valid!");
		rtn.put("token", 1);

		/*
		 * int requiredColCnt = 0; int blankCnt = 0; //取带*的必填项的列数 for (int i =
		 * 0; i < colCnt; i++) { String colName= colNm[i].getContents().trim();
		 * if(colName.lastIndexOf("*")==(colName.length()-1)){ requiredColCnt++;
		 * } } if(requiredColCnt == colCnt){ if(row.length < colCnt){
		 * rtn.put("message", "Required Column Value!"); rtn.put("token", 3); }
		 * }else{ for (int i = 0; i < colCnt; i++) { String blankFlag = "N";
		 * if(i < row.length){ Object cellsContent = row[i].getContents();
		 * String content = ""; if (cellsContent != null) { content =
		 * cellsContent.toString().replaceAll(" ", ""); } if (row[i].getType()
		 * == CellType.EMPTY || ((row[i].getType() != CellType.EMPTY) && content
		 * .equals(""))) { blankCnt++; blankFlag = "Y"; } } String colName=
		 * colNm[i].getContents().trim(); if(colName.lastIndexOf("*") != -1){
		 * String requiredYn =
		 * colName.lastIndexOf("*")==(colName.length()-1)?"Y":"N";
		 * if(blankFlag.equals("Y") && requiredYn.equals("Y")){
		 * rtn.put("message", "Required Column Value!"); rtn.put("token", 3);
		 * break; } } } // 如果当前行有空的单元格返回false if (blankCnt == colCnt) {
		 * rtn.put("message", "Blank Row!"); rtn.put("token",2); } }
		 */
		return rtn;
	}

	/**
	 * validate cell区分必填与非必填检查
	 * 
	 * @param pstmt
	 * @param row
	 * @param columns
	 * @throws SQLException
	 */
	private int validateCellForRequiredColum(Cell[] colNm, Cell[] row,
			int colCnt) {
		int checkFlag = 1;// 验证结果标志
		int requiredColCnt = 0;
		int blankCnt = 0;
		// 取带*的必填项的列数
		for (int i = 0; i < colCnt; i++) {
			if (colNm[i].toString().trim().lastIndexOf("*") == (colNm[i]
					.toString().trim().length() - 1)) {
				requiredColCnt++;
			}
		}
		if (requiredColCnt == colCnt) {
			if (row.length < colCnt) {
				checkFlag = 2;// 必须填写的colum总数少于必填column总数
			}
		} else {
			for (int i = 0; i < colCnt; i++) {
				String blankFlag = "N";
				if (i < row.length) {
					Object cellsContent = row[i].getContents();
					String content = "";
					if (cellsContent != null) {
						content = cellsContent.toString().replaceAll(" ", "");
					}
					if (row[i].getType() == CellType.EMPTY
							|| ((row[i].getType() != CellType.EMPTY) && content
									.equals(""))) {
						blankCnt++;
						blankFlag = "Y";
					}
				}
				String colName = colNm[i].getContents().trim();
				if (colName.lastIndexOf("*") != -1) {
					String requiredYn = colName.lastIndexOf("*") == (colName
							.length() - 1) ? "Y" : "N";
					if (blankFlag.equals("Y") && requiredYn.equals("Y")) {
						checkFlag = 3;// 必须填写的colum没有进行填写
						break;
					}
				}
			}
			// 如果当前行有空的单元格返回false
			if (blankCnt == colCnt) {
				checkFlag = 3;// 必须填写的colum全部没有进行填写
			}
		}
		return checkFlag;
	}

	private boolean validateCell(Cell[] row, int columns, String nullStr) {
		// 如果当前行的数据和表头不对应返回false
		// if (row.length != columns){
		// return false;
		// }
		// for (int i = 0; i < columns; i++) {
		// // 如果当前行有空的单元格返回false
		// if (row[i].getType() == CellType.EMPTY)
		// return false;
		// }
		// return true;
		// if (row.length != columns){
		// return false;
		// }
		int init = 0;
		// int nullCount = 0;//容许为空的列数
		// if(nullStr.equals("")){
		// nullCount = 0;
		// }else if(nullStr.length()==3){
		// nullCount = 1;
		// }else{
		// String[] strs = nullStr.split(",");
		// nullCount = strs.length-1;
		// }
		// if(columns-row.length-nullCount>=1){
		// return false;
		// }
		for (int i = 0; i < columns; i++) {
			// 如果当前行有空的单元格返回false
			int ii = nullStr.indexOf("," + i + ",");
			if (nullStr.indexOf("," + i + ",") < 0) {
				Object cellsContent = StringUtil
						.checkNull(row[i].getContents());
				String content = "";
				if (cellsContent != null) {
					content = cellsContent.toString().replaceAll(" ", "");
				}
				if (row[i].getType() == CellType.EMPTY
						|| ((row[i].getType() != CellType.EMPTY) && content
								.equals(""))) {
					init++;
				}
				// return false;
			}
		}
		if (init == columns) {
			return false;
		}
		return true;
	}

	/**
	 * 以下方法为导出
	 */

	/**
	 * 下载导入模板时，将例子数据，加到list中（直接调用，不需要修改）
	 */
	@SuppressWarnings("unchecked")
	public LinkedHashMap putIntoSqlContentMap(List list) {
		LinkedHashMap sqlContentmap = new LinkedHashMap();
		sqlContentmap.put("contentList", list);
		return sqlContentmap;
	}

	/**
	 * 导出数据到excel Description:export datas to excel
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings( { "unchecked", "deprecation" })
	public void exportExcel(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap,
			LinkedHashMap sqlContentmap, List aliasNameList, List aliasList)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String adminID = admin.getAdminID() == null ? "USERNO_"
				+ admin.getUserNo() : admin.getAdminID();
		this.type = request.getParameter("type");
		String CpnyId = admin.getCpnyId();
		this.path = request.getRealPath("");

		int init = path.indexOf("\\");
		String server = "win";
		if (init < 0) { // WebServer的os为unix/linux
			server = "u/l";
		}
		if ("win".equals(server)) {
			path = path + "\\resources\\temp\\download\\" + CpnyId + "\\"
					+ adminID + "\\";
		} else {
			path = path + "/resources/temp/download/" + CpnyId + "/" + adminID;
		}
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet1 = wb.createSheet("sheet1");
		XSSFRow row = sheet1.createRow(0);
		XSSFCell cell;

		List result = null;
		try {
			/**
			 * 根据导出报表的类型，定义列名，并且从相应的表中查询数据
			 */
			// if(this.type!=null&&this.type.equals("InsuranceInputItemData")){
			row = sheet1.createRow(0);
			for (int i = 0; i < aliasNameList.size(); i++) {
				cell = row.createCell((short) i);
				cell.setCellValue(aliasNameList.get(i).toString());
			}
			if (sqlContentmap != null
					&& sqlContentmap.get("sqlContent") != null
					&& !sqlContentmap.get("sqlContent").equals("")) {
				result = this.getExcelExportDataList(sqlContentmap);
			}
			// }
			File file = new File(this.path + "\\temp.xlsx");
			file.delete();

			if (result != null && result.size() > 0) {// 导出数据
				for (int i = 0; i < result.size(); i++) {
					int k = 0;
					row = sheet1.createRow(i + 1);
					for (int j = 0; j < aliasList.size(); j++) {
						cell = row.createCell((short) k++);
						cell
								.setCellValue(((Map) (result.get(i)))
										.get(aliasList.get(j).toString()) == null ? " "
										: ((Map) (result.get(i))).get(
												aliasList.get(j).toString())
												.toString());
					}
				}
			} else {// 下载导入模板
				List contentList = sqlContentmap.get("contentList") == null ? null
						: (List) sqlContentmap.get("contentList");
				if (contentList != null && contentList.size() > 0) {
					for (int i = 0; i < contentList.size(); i++) {
						int k = 0;
						row = sheet1.createRow(i + 1);
						Set keySet = ((Map) (contentList.get(i))).keySet();
						if (keySet != null) {
							Iterator it = keySet.iterator();
							while (it.hasNext()) {
								Object itValue = it.next();
								if (itValue != null) {
									cell = row.createCell((short) k++);
									cell.setCellValue(((Map) (contentList
											.get(i))).get(itValue).toString());
								}
							}
						}
					}
				}
			}
			File f = new File(this.path);
			if (!f.exists()) {
				f.mkdirs();
			}
			FileOutputStream fos = new FileOutputStream(new File(this.path
					+ "\\temp.xlsx"));
			wb.write(fos);
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.setContentType(CONTENT_TYPE);

		File file = new File(this.path + "\\temp.xlsx");
		response.setContentType("application/x-msdownload");
		response.setContentLength((int) file.length());
		response.setHeader("Content-Disposition", "attachment;filename="
				+ "temp.xlsx");
		FileInputStream fis = new FileInputStream(file);
		BufferedInputStream buff = new BufferedInputStream(fis);

		byte[] b = new byte[2048];
		long k = 0;
		OutputStream myout = response.getOutputStream();
		while (k < file.length()) {
			int j = buff.read(b, 0, 2048);
			k += j;
			myout.write(b, 0, j);
		}
		myout.flush();
	}

	/**
	 * 导出数据到excel Description:export datas to excel
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings( { "unchecked", "deprecation" })
	public void exportIsParamDataExcel(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap,
			LinkedHashMap sqlContentmap, List aliasNameList, List aliasList,
			String fileName) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String adminID = admin.getAdminID() == null ? "USERNO_"
				+ admin.getUserNo() : admin.getAdminID();
		this.type = request.getParameter("type");
		String CpnyId = admin.getCpnyId();
		this.path = request.getRealPath("");

		int init = path.indexOf("\\");
		String server = "win";
		if (init < 0) { // WebServer的os为unix/linux
			server = "u/l";
		}
		if ("win".equals(server)) {
			path = path + "\\resources\\temp\\download\\" + CpnyId + "\\"
					+ adminID + "\\";
		} else {
			path = path + "/resources/temp/download/" + CpnyId + "/" + adminID;
		}
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet1 = wb.createSheet("sheet1");
		XSSFRow row = sheet1.createRow(0);
		XSSFCell cell;

		List result = null;
		try {
			/**
			 * 根据导出报表的类型，定义列名，并且从相应的表中查询数据
			 */
			// if(this.type!=null&&this.type.equals("InsuranceInputItemData")){
			row = sheet1.createRow(0);
			for (int i = 0; i < aliasNameList.size(); i++) {
				cell = row.createCell((short) i);
				cell.setCellValue(aliasNameList.get(i).toString());
			}
			if (sqlContentmap != null
					&& sqlContentmap.get("sqlContent") != null
					&& !sqlContentmap.get("sqlContent").equals("")) {
				result = this.getExcelExportDataList(sqlContentmap);
			}
			// }
			File file = new File(this.path + "\\temp.xlsx");
			file.delete();

			if (result != null && result.size() > 0) {// 导出数据
				for (int i = 0; i < result.size(); i++) {
					int k = 0;
					row = sheet1.createRow(i + 1);
					for (int j = 0; j < aliasList.size(); j++) {
						cell = row.createCell((short) k++);
						cell
								.setCellValue(((Map) (result.get(i)))
										.get(aliasList.get(j).toString()) == null ? " "
										: ((Map) (result.get(i))).get(
												aliasList.get(j).toString())
												.toString());
					}
				}
			} else {// 下载导入模板
				List contentList = sqlContentmap.get("contentList") == null ? null
						: (List) sqlContentmap.get("contentList");
				if (contentList != null && contentList.size() > 0) {
					for (int i = 0; i < contentList.size(); i++) {
						int k = 0;
						row = sheet1.createRow(i + 1);
						Set keySet = ((Map) (contentList.get(i))).keySet();
						if (keySet != null) {
							Iterator it = keySet.iterator();
							while (it.hasNext()) {
								Object itValue = it.next();
								if (itValue != null) {
									cell = row.createCell((short) k++);
									cell.setCellValue(((Map) (contentList
											.get(i))).get(itValue).toString());
								}
							}
						}
					}
				}
			}
			File f = new File(this.path);
			if (!f.exists()) {
				f.mkdirs();
			}
			FileOutputStream fos = new FileOutputStream(new File(this.path
					+ "\\temp.xlsx"));
			wb.write(fos);
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.setContentType(CONTENT_TYPE);

		File file = new File(this.path + "\\temp.xlsx");
		response.setContentType("application/x-msdownload");
		response.setContentLength((int) file.length());
		if (fileName == null || "".equals(fileName)) {
			response.setHeader("Content-Disposition", "attachment;filename="
					+ "temp.xlsx");
		} else {
			response.setHeader("Content-Disposition",
					"attachment;filename=temp_" + fileName + ".xlsx");
		}
		FileInputStream fis = new FileInputStream(file);
		BufferedInputStream buff = new BufferedInputStream(fis);

		byte[] b = new byte[2048];
		long k = 0;
		OutputStream myout = response.getOutputStream();
		while (k < file.length()) {
			int j = buff.read(b, 0, 2048);
			k += j;
			myout.write(b, 0, j);
		}
		myout.flush();
	}

	/**
	 * 导出数据到excel Description:export datas to excel
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings( { "unchecked", "deprecation" })
	public void exportExcelTwoSheet(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap,
			LinkedHashMap sqlContentmap, List aliasNameList, List aliasList,
			List sheet2List) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String adminID = admin.getAdminID() == null ? "USERNO_"
				+ admin.getUserNo() : admin.getAdminID();
		this.type = request.getParameter("type");
		String CpnyId = admin.getCpnyId();
		this.path = request.getRealPath("");

		int init = path.indexOf("\\");
		String server = "win";
		if (init < 0) { // WebServer的os为unix/linux
			server = "u/l";
		}
		if ("win".equals(server)) {
			path = path + "\\resources\\temp\\download\\" + CpnyId + "\\"
					+ adminID + "\\";
		} else {
			path = path + "/resources/temp/download/" + CpnyId + "/" + adminID;
		}
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet1 = wb.createSheet("sheet1");
		XSSFRow row = sheet1.createRow(0);
		XSSFCell cell;

		XSSFSheet sheet2 = wb.createSheet("sheet2");
		// XSSFRow row2 = sheet1.createRow(0);
		// XSSFCell cell2;

		List result = null;
		try {
			/**
			 * 根据导出报表的类型，定义列名，并且从相应的表中查询数据
			 */
			// if(this.type!=null&&this.type.equals("InsuranceInputItemData")){
			row = sheet1.createRow(0);
			for (int i = 0; i < aliasNameList.size(); i++) {
				cell = row.createCell((short) i);
				cell.setCellValue(aliasNameList.get(i).toString());
			}
			if (sqlContentmap != null
					&& sqlContentmap.get("sqlContent") != null
					&& !sqlContentmap.get("sqlContent").equals("")) {
				result = this.getExcelExportDataList(sqlContentmap);
			}
			// }
			File file = new File(this.path + "\\temp.xlsx");
			file.delete();

			if (result != null && result.size() > 0) {// 导出数据
				for (int i = 0; i < result.size(); i++) {
					int k = 0;
					row = sheet1.createRow(i + 1);
					for (int j = 0; j < aliasList.size(); j++) {
						cell = row.createCell((short) k++);
						cell
								.setCellValue(((Map) (result.get(i)))
										.get(aliasList.get(j).toString()) == null ? ""
										: ((Map) (result.get(i))).get(
												aliasList.get(j).toString())
												.toString());
					}
				}
			} else {// 下载导入模板
				List contentList = sqlContentmap.get("contentList") == null ? null
						: (List) sqlContentmap.get("contentList");
				if (contentList != null && contentList.size() > 0) {
					for (int i = 0; i < contentList.size(); i++) {
						int k = 0;
						row = sheet1.createRow(i + 1);
						Set keySet = ((Map) (contentList.get(i))).keySet();
						if (keySet != null) {
							Iterator it = keySet.iterator();
							while (it.hasNext()) {
								Object itValue = it.next();
								if (itValue != null) {
									cell = row.createCell((short) k++);
									cell.setCellValue(((Map) (contentList
											.get(i))).get(itValue).toString());
								}
							}
						}
					}
				}
			}
			// row2 = sheet2.createRow(0);
			if (sheet2List != null && sheet2List.size() > 0) {// 设定sheet2的列头
				XSSFRow row2 = sheet2.createRow(0);
				XSSFCell cell2 = row2.createCell((short) 0);
				cell2.setCellValue((sheet2List.get(sheet2List.size() - 1))
						.toString());
			}
			for (int i = 0; i < sheet2List.size() - 1; i++) {// 设定sheet2里头的内容
				XSSFRow row2 = sheet2.createRow(i + 1);
				XSSFCell cell2 = row2.createCell((short) 0);
				if (sheet2List.get(i) != null
						&& ((Map) (sheet2List.get(i))).get("CONTENT") != null) {
					cell2.setCellValue(((Map) (sheet2List.get(i))).get(
							"CONTENT").toString());
				} else {
					cell2.setCellValue("");
				}
			}
			File f = new File(this.path);
			if (!f.exists()) {
				f.mkdirs();
			}
			FileOutputStream fos = new FileOutputStream(new File(this.path
					+ "\\temp.xlsx"));
			wb.write(fos);
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.setContentType(CONTENT_TYPE);

		File file = new File(this.path + "\\temp.xlsx");
		response.setContentType("application/x-msdownload");
		response.setContentLength((int) file.length());
		response.setHeader("Content-Disposition", "attachment;filename="
				+ "temp.xlsx");
		FileInputStream fis = new FileInputStream(file);
		BufferedInputStream buff = new BufferedInputStream(fis);

		byte[] b = new byte[2048];
		long k = 0;
		OutputStream myout = response.getOutputStream();
		while (k < file.length()) {
			int j = buff.read(b, 0, 2048);
			k += j;
			myout.write(b, 0, j);
		}
		myout.flush();
	}

	/**
	 * 导出数据到excel Description:export datas to excel
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 */
	@SuppressWarnings( { "unchecked", "deprecation" })
	public void exportExcelMoreSheet(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap,
			LinkedHashMap sqlContentmap, List aliasNameList, List aliasList,
			List mapNameList, List mapList) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String adminID = admin.getAdminID() == null ? "USERNO_"
				+ admin.getUserNo() : admin.getAdminID();
		this.type = request.getParameter("type");
		String CpnyId = admin.getCpnyId();
		this.path = request.getRealPath("");

		int init = path.indexOf("\\");
		String server = "win";
		if (init < 0) { // WebServer的os为unix/linux
			server = "u/l";
		}
		if ("win".equals(server)) {
			path = path + "\\resources\\temp\\download\\" + CpnyId + "\\"
					+ adminID + "\\";
		} else {
			path = path + "/resources/temp/download/" + CpnyId + "/" + adminID;
		}
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet1 = wb.createSheet("sheet1");
		XSSFRow row = sheet1.createRow(0);
		XSSFCell cell;

		List result = null;
		String fileName = modelMap.get("FileName") == null ? "temp" : modelMap
				.get("FileName").toString();
		fileName = fileName + ".xlsx";
		try {
			/**
			 * 根据导出报表的类型，定义列名，并且从相应的表中查询数据
			 */
			row = sheet1.createRow(0);
			for (int i = 0; i < aliasNameList.size(); i++) {
				cell = row.createCell((short) i);
				cell.setCellValue(aliasNameList.get(i).toString());
			}
			if (sqlContentmap != null
					&& sqlContentmap.get("sqlContent") != null
					&& !sqlContentmap.get("sqlContent").equals("")) {
				result = this.getExcelExportDataList(sqlContentmap);
			}
			File file = new File(this.path + "\\" + fileName);
			file.delete();

			if (result != null && result.size() > 0) {// 导出数据
				for (int i = 0; i < result.size(); i++) {
					int k = 0;
					row = sheet1.createRow(i + 1);
					for (int j = 0; j < aliasList.size(); j++) {
						cell = row.createCell((short) k++);
						cell
								.setCellValue(((Map) (result.get(i)))
										.get(aliasList.get(j).toString()) == null ? ""
										: ((Map) (result.get(i))).get(
												aliasList.get(j).toString())
												.toString());
					}
				}
			} else {// 下载导入模板
				List contentList = sqlContentmap.get("contentList") == null ? null
						: (List) sqlContentmap.get("contentList");
				if (contentList != null && contentList.size() > 0) {
					for (int i = 0; i < contentList.size(); i++) {
						int k = 0;
						row = sheet1.createRow(i + 1);
						Set keySet = ((Map) (contentList.get(i))).keySet();
						if (keySet != null) {
							Iterator it = keySet.iterator();
							while (it.hasNext()) {
								Object itValue = it.next();
								if (itValue != null) {
									cell = row.createCell((short) k++);
									cell.setCellValue(((Map) (contentList
											.get(i))).get(itValue).toString());
								}
							}
						}
					}
				}
			}

			for (int r = 0; r < mapList.size(); r++) {
				List sheet2List = new ArrayList();
				if (mapList.get(r) != null && "" != mapList.get(r)) {
					LinkedHashMap map = new LinkedHashMap();
					map.put("sqlContent", mapList.get(r));
					sheet2List = this.getExcelExportDataList(map);
				}
				// XSSFSheet sheet2 = wb.createSheet("sheet"+(r+2));
				XSSFSheet sheet2 = wb.createSheet(String.valueOf(mapNameList
						.get(r)));
				if (mapNameList != null && mapNameList.size() > 0) {// 设定sheet2的列头
					XSSFRow row2 = sheet2.createRow(0);
					XSSFCell cell2 = row2.createCell((short) 0);
					cell2.setCellValue(String.valueOf(mapNameList.get(r)));
				}
				// for (int i = 0; i < sheet2List.size() - 1; i++) {//
				// 设定sheet2里头的内容
				for (int i = 0; i < sheet2List.size(); i++) {// 设定sheet2里头的内容
					XSSFRow row2 = sheet2.createRow(i + 1);
					XSSFCell cell2 = row2.createCell((short) 0);
					if (sheet2List.get(i) != null) {
						cell2.setCellValue(String.valueOf(((Map) sheet2List
								.get(i)).get("CONTENT")));
					} else {
						cell2.setCellValue("");
					}
				}
			}
			File f = new File(this.path);
			if (!f.exists()) {
				f.mkdirs();
			}
			FileOutputStream fos = new FileOutputStream(new File(this.path
					+ "\\" + fileName));
			wb.write(fos);
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.setContentType(CONTENT_TYPE);

		try {
			File file = new File(this.path + "\\" + fileName);
			response.setContentType("application/x-msdownload");
			response.setContentLength((int) file.length());
			response.setHeader("Content-Disposition", "attachment;filename="
					+ fileName);
			FileInputStream fis = new FileInputStream(file);
			BufferedInputStream buff = new BufferedInputStream(fis);

			byte[] b = new byte[2048];
			long k = 0;
			OutputStream myout = response.getOutputStream();
			while (k < file.length()) {
				int j = buff.read(b, 0, 2048);
				k += j;
				myout.write(b, 0, j);
			}
			myout.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 导出的sheet中含有两列数据--一个代表NO，一个代表Content
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author weizhengchen
	 * @date 2014-07-09
	 */
	@SuppressWarnings( { "unchecked", "deprecation" })
	public void exportExcelMoreSheetAndMoreContent(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap,
			LinkedHashMap sqlContentmap, List aliasNameList, List aliasList,
			List mapNameList, List mapList, String excelName) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String adminID = admin.getAdminID() == null ? "USERNO_"
				+ admin.getUserNo() : admin.getAdminID();
		this.type = request.getParameter("type");
		String CpnyId = admin.getCpnyId();
		this.path = request.getRealPath("");

		int init = path.indexOf("\\");
		String server = "win";
		if (init < 0) { // WebServer的os为unix/linux
			server = "u/l";
		}
		if ("win".equals(server)) {
			path = path + "\\resources\\temp\\download\\" + CpnyId + "\\"
					+ adminID + "\\";
		} else {
			path = path + "/resources/temp/download/" + CpnyId + "/" + adminID;
		}
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet1 = wb.createSheet(excelName);
		XSSFRow row = sheet1.createRow(0);
		XSSFCell cell;

		List result = null;
		try {
			/**
			 * 根据导出报表的类型，定义列名，并且从相应的表中查询数据
			 */
			row = sheet1.createRow(0);
			for (int i = 0; i < aliasNameList.size(); i++) {
				cell = row.createCell((short) i);
				cell.setCellValue(aliasNameList.get(i).toString());
			}
			if (sqlContentmap != null
					&& sqlContentmap.get("sqlContent") != null
					&& !sqlContentmap.get("sqlContent").equals("")) {
				result = this.getExcelExportDataList(sqlContentmap);
			}
			File file = new File(this.path + "\\temp.xlsx");
			file.delete();

			if (result != null && result.size() > 0) {// 导出数据
				for (int i = 0; i < result.size(); i++) {
					int k = 0;
					row = sheet1.createRow(i + 1);
					for (int j = 0; j < aliasList.size(); j++) {
						cell = row.createCell((short) k++);
						cell
								.setCellValue(((Map) (result.get(i)))
										.get(aliasList.get(j).toString()) == null ? ""
										: ((Map) (result.get(i))).get(
												aliasList.get(j).toString())
												.toString());
					}
				}
			} else {// 下载导入模板
				List contentList = sqlContentmap.get("contentList") == null ? null
						: (List) sqlContentmap.get("contentList");
				if (contentList != null && contentList.size() > 0) {
					for (int i = 0; i < contentList.size(); i++) {
						int k = 0;
						row = sheet1.createRow(i + 1);
						Set keySet = ((Map) (contentList.get(i))).keySet();
						if (keySet != null) {
							Iterator it = keySet.iterator();
							while (it.hasNext()) {
								Object itValue = it.next();
								if (itValue != null) {
									cell = row.createCell((short) k++);
									cell.setCellValue(((Map) (contentList
											.get(i))).get(itValue).toString());
								}
							}
						}
					}
				}
			}

			for (int r = 0; r < mapList.size(); r++) {
				List sheet2List = new ArrayList();
				if (mapList.get(r) != null && "" != mapList.get(r)) {
					LinkedHashMap map = new LinkedHashMap();
					map.put("sqlContent", mapList.get(r));
					sheet2List = this.getExcelExportDataList(map);
				}
				XSSFSheet sheet2 = wb
						.createSheet(mapNameList.get(r).toString());
				if (mapNameList != null && mapNameList.size() > 0) {// 设定sheet2的列头
					XSSFRow row2 = sheet2.createRow(0);
					XSSFCell cell2 = row2.createCell((short) 0);
					cell2.setCellValue(String.valueOf(mapNameList.get(r)));
				}
				for (int i = 0; i < sheet2List.size(); i++) {// 设定sheet2里头的内容
					XSSFRow row2 = sheet2.createRow(i + 1);
					XSSFCell cell1 = row2.createCell((short) 0);
					XSSFCell cell2 = row2.createCell((short) 1);
					if (sheet2List.get(i) != null) {
						cell1.setCellValue(String.valueOf(((Map) sheet2List
								.get(i)).get("NO")) == "null" ? "" : String
								.valueOf(((Map) sheet2List.get(i)).get("NO")));
						cell2.setCellValue(String.valueOf(((Map) sheet2List
								.get(i)).get("CONTENT")));
					} else {
						cell1.setCellValue("");
						cell2.setCellValue("");
					}
				}
			}
			File f = new File(this.path);
			if (!f.exists()) {
				f.mkdirs();
			}
			FileOutputStream fos = new FileOutputStream(new File(this.path
					+ "\\temp.xlsx"));
			wb.write(fos);
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.setContentType(CONTENT_TYPE);

		try {
			File file = new File(this.path + "\\temp.xlsx");
			response.setContentType("application/x-msdownload");
			response.setContentLength((int) file.length());
			response.setHeader("Content-Disposition", "attachment;filename="
					+ "temp_" + excelName + ".xlsx");
			FileInputStream fis = new FileInputStream(file);
			BufferedInputStream buff = new BufferedInputStream(fis);

			byte[] b = new byte[2048];
			long k = 0;
			OutputStream myout = response.getOutputStream();
			while (k < file.length()) {
				int j = buff.read(b, 0, 2048);
				k += j;
				myout.write(b, 0, j);
			}
			myout.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 导出数据到excel Description:export datas to excel
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author weizhengchen
	 * @date 2014-07-09
	 */
	@SuppressWarnings( { "unchecked", "deprecation" })
	public void exportExcelMoreSheet(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap,
			LinkedHashMap sqlContentmap, List aliasNameList, List aliasList,
			List mapNameList, List mapList, String excelName) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String adminID = admin.getAdminID() == null ? "USERNO_"
				+ admin.getUserNo() : admin.getAdminID();
		this.type = request.getParameter("type");
		String CpnyId = admin.getCpnyId();
		this.path = request.getRealPath("");

		int init = path.indexOf("\\");
		String server = "win";
		if (init < 0) { // WebServer的os为unix/linux
			server = "u/l";
		}
		if ("win".equals(server)) {
			path = path + "\\resources\\temp\\download\\" + CpnyId + "\\"
					+ adminID + "\\";
		} else {
			path = path + "/resources/temp/download/" + CpnyId + "/" + adminID;
		}
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet1 = wb.createSheet(excelName);
		XSSFRow row = sheet1.createRow(0);
		XSSFCell cell;

		List result = null;
		try {
			/**
			 * 根据导出报表的类型，定义列名，并且从相应的表中查询数据
			 */
			row = sheet1.createRow(0);
			for (int i = 0; i < aliasNameList.size(); i++) {
				cell = row.createCell((short) i);
				cell.setCellValue(aliasNameList.get(i).toString());
			}
			if (sqlContentmap != null
					&& sqlContentmap.get("sqlContent") != null
					&& !sqlContentmap.get("sqlContent").equals("")) {
				result = this.getExcelExportDataList(sqlContentmap);
			}
			File file = new File(this.path + "\\temp.xlsx");
			file.delete();

			if (result != null && result.size() > 0) {// 导出数据
				for (int i = 0; i < result.size(); i++) {
					int k = 0;
					row = sheet1.createRow(i + 1);
					for (int j = 0; j < aliasList.size(); j++) {
						cell = row.createCell((short) k++);
						cell
								.setCellValue(((Map) (result.get(i)))
										.get(aliasList.get(j).toString()) == null ? ""
										: ((Map) (result.get(i))).get(
												aliasList.get(j).toString())
												.toString());
					}
				}
			} else {// 下载导入模板
				List contentList = sqlContentmap.get("contentList") == null ? null
						: (List) sqlContentmap.get("contentList");
				if (contentList != null && contentList.size() > 0) {
					for (int i = 0; i < contentList.size(); i++) {
						int k = 0;
						row = sheet1.createRow(i + 1);
						Set keySet = ((Map) (contentList.get(i))).keySet();
						if (keySet != null) {
							Iterator it = keySet.iterator();
							while (it.hasNext()) {
								Object itValue = it.next();
								if (itValue != null) {
									cell = row.createCell((short) k++);
									cell.setCellValue(((Map) (contentList
											.get(i))).get(itValue) == null ? ""
											: ((Map) (contentList.get(i))).get(
													itValue).toString());
								}
							}
						}
					}
				}
			}

			for (int r = 0; r < mapList.size(); r++) {
				List sheet2List = new ArrayList();
				if (mapList.get(r) != null && "" != mapList.get(r)) {
					LinkedHashMap map = new LinkedHashMap();
					map.put("sqlContent", mapList.get(r));
					sheet2List = this.getExcelExportDataList(map);
				}
				XSSFSheet sheet2 = wb
						.createSheet(mapNameList.get(r).toString());
				if (mapNameList != null && mapNameList.size() > 0) {// 设定sheet2的列头
					XSSFRow row2 = sheet2.createRow(0);
					XSSFCell cell2 = row2.createCell((short) 0);
					cell2.setCellValue(String.valueOf(mapNameList.get(r)));
				}
				for (int i = 0; i < sheet2List.size(); i++) {// 设定sheet2里头的内容
					XSSFRow row2 = sheet2.createRow(i + 1);
					XSSFCell cell2 = row2.createCell((short) 0);
					XSSFCell cell3 = row2.createCell((short) 1);
					XSSFCell cell4 = row2.createCell((short) 2);
					if (sheet2List.get(i) != null) {
						cell2.setCellValue(String.valueOf(((Map) sheet2List
								.get(i)).get("CONTENT")));
						if(String.valueOf(((Map) sheet2List.get(i)).get("CODE_MA")) != null){
							cell3.setCellValue(String.valueOf(((Map) sheet2List
									.get(i)).get("CODE_MA")));
						}else {
							cell3.setCellValue("");
						}
						if(String.valueOf(((Map) sheet2List.get(i)).get("CODE_NO")) != null){
							cell4.setCellValue(String.valueOf(((Map) sheet2List
									.get(i)).get("CODE_NO")));
						}else {
							cell4.setCellValue("");
						}
					} else {
						cell2.setCellValue("");
					}
				}
			}
			File f = new File(this.path);
			if (!f.exists()) {
				f.mkdirs();
			}
			FileOutputStream fos = new FileOutputStream(new File(this.path
					+ "\\temp.xlsx"));
			wb.write(fos);
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.setContentType(CONTENT_TYPE);

		try {
			File file = new File(this.path + "\\temp.xlsx");
			response.setContentType("application/x-msdownload");
			response.setContentLength((int) file.length());
			response.setHeader("Content-Disposition", "attachment;filename="
					+ "temp_" + excelName + ".xlsx");
			FileInputStream fis = new FileInputStream(file);
			BufferedInputStream buff = new BufferedInputStream(fis);

			byte[] b = new byte[2048];
			long k = 0;
			OutputStream myout = response.getOutputStream();
			while (k < file.length()) {
				int j = buff.read(b, 0, 2048);
				k += j;
				myout.write(b, 0, j);
			}
			myout.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 导出数据到excel Description:export datas to excel
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author weizhengchen
	 * @date 2014-07-09
	 */
	@SuppressWarnings( { "unchecked", "deprecation" })
	public void exportPaiQianDiModelMoreSheet(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap,
			LinkedHashMap sqlContentmap, List aliasNameList, List aliasList,
			List mapNameList, List mapList, String excelName) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String adminID = admin.getAdminID() == null ? "USERNO_"
				+ admin.getUserNo() : admin.getAdminID();
		this.type = request.getParameter("type");
		String CpnyId = admin.getCpnyId();
		this.path = request.getRealPath("");

		int init = path.indexOf("\\");
		String server = "win";
		if (init < 0) { // WebServer的os为unix/linux
			server = "u/l";
		}
		if ("win".equals(server)) {
			path = path + "\\resources\\temp\\download\\" + CpnyId + "\\"
					+ adminID + "\\";
		} else {
			path = path + "/resources/temp/download/" + CpnyId + "/" + adminID;
		}
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet1 = wb.createSheet(excelName);
		XSSFRow row = sheet1.createRow(0);
		XSSFCell cell;

		List result = null;
		try {
			/**
			 * 根据导出报表的类型，定义列名，并且从相应的表中查询数据
			 */
			row = sheet1.createRow(0);
			for (int i = 0; i < aliasNameList.size(); i++) {
				cell = row.createCell((short) i);
				cell.setCellValue(aliasNameList.get(i).toString());
			}
			if (sqlContentmap != null
					&& sqlContentmap.get("sqlContent") != null
					&& !sqlContentmap.get("sqlContent").equals("")) {
				result = this.getExcelExportDataList(sqlContentmap);
			}
			File file = new File(this.path + "\\temp.xlsx");
			file.delete();

			if (result != null && result.size() > 0) {// 导出数据
				for (int i = 0; i < result.size(); i++) {
					int k = 0;
					row = sheet1.createRow(i + 1);
					for (int j = 0; j < aliasList.size(); j++) {
						cell = row.createCell((short) k++);
						cell
								.setCellValue(((Map) (result.get(i)))
										.get(aliasList.get(j).toString()) == null ? ""
										: ((Map) (result.get(i))).get(
												aliasList.get(j).toString())
												.toString());
					}
				}
			} else {// 下载导入模板
				List contentList = sqlContentmap.get("contentList") == null ? null
						: (List) sqlContentmap.get("contentList");
				if (contentList != null && contentList.size() > 0) {
					for (int i = 0; i < contentList.size(); i++) {
						int k = 0;
						row = sheet1.createRow(i + 1);
						Set keySet = ((Map) (contentList.get(i))).keySet();
						if (keySet != null) {
							Iterator it = keySet.iterator();
							while (it.hasNext()) {
								Object itValue = it.next();
								if (itValue != null) {
									cell = row.createCell((short) k++);
									cell.setCellValue(((Map) (contentList
											.get(i))).get(itValue).toString());
								}
							}
						}
					}
				}
			}

			for (int r = 0; r < mapList.size(); r++) {
				List sheet2List = new ArrayList();
				if (mapList.get(r) != null && "" != mapList.get(r)) {
					LinkedHashMap map = new LinkedHashMap();
					map.put("sqlContent", mapList.get(r));
					sheet2List = this.getExcelExportDataList(map);
				}
				XSSFSheet sheet2 = wb
						.createSheet(mapNameList.get(r).toString());
				String sheetName = mapNameList.get(r).toString();
				if (mapNameList != null && mapNameList.size() > 0) {// 设定sheet2的列头
					XSSFRow row2 = sheet2.createRow(0);
					XSSFCell cell2 = row2.createCell((short) 0);
					if ("城市列表".equals(sheetName)) {
						cell2.setCellValue("城市列表");
						XSSFCell cell3 = row2.createCell((short) 1);
						cell3.setCellValue("所属省份");
					} else if ("地区列表".equals(sheetName)) {
						cell2.setCellValue("地区列表");
						XSSFCell cell3 = row2.createCell((short) 1);
						cell3.setCellValue("所属城市");
					} else {
						cell2.setCellValue(String.valueOf(mapNameList.get(r)));
					}
				}
				for (int i = 0; i < sheet2List.size(); i++) {// 设定sheet2里头的内容
					XSSFRow row2 = sheet2.createRow(i + 1);
					XSSFCell cell2 = row2.createCell((short) 0);
					if ("城市列表".equals(sheetName) || "地区列表".equals(sheetName)) {
						XSSFCell cell3 = row2.createCell((short) 1);
						if (sheet2List.get(i) != null) {
							cell2.setCellValue(String.valueOf(((Map) sheet2List
									.get(i)).get("CONTENT")));
							cell3.setCellValue(String.valueOf(((Map) sheet2List
									.get(i)).get("PARENT_CODE")));
						} else {
							cell2.setCellValue("");
							cell3.setCellValue("");
						}
					} else {
						if (sheet2List.get(i) != null) {
							cell2.setCellValue(String.valueOf(((Map) sheet2List
									.get(i)).get("CONTENT")));
						} else {
							cell2.setCellValue("");
						}
					}
				}
			}
			File f = new File(this.path);
			if (!f.exists()) {
				f.mkdirs();
			}
			FileOutputStream fos = new FileOutputStream(new File(this.path
					+ "\\temp.xlsx"));
			wb.write(fos);
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.setContentType(CONTENT_TYPE);

		try {
			File file = new File(this.path + "\\temp.xlsx");
			response.setContentType("application/x-msdownload");
			response.setContentLength((int) file.length());
			if (excelName != null && !"".equals(excelName)) {
				response.setHeader("Content-Disposition",
						"attachment;filename=temp_" + excelName + ".xlsx");
			} else {
				response.setHeader("Content-Disposition",
						"attachment;filename=temp.xlsx");
			}
			FileInputStream fis = new FileInputStream(file);
			BufferedInputStream buff = new BufferedInputStream(fis);

			byte[] b = new byte[2048];
			long k = 0;
			OutputStream myout = response.getOutputStream();
			while (k < file.length()) {
				int j = buff.read(b, 0, 2048);
				k += j;
				myout.write(b, 0, j);
			}
			myout.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 导出数据到excel Description:export datas to excel
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author weizhengchen
	 * @date 2014-07-09
	 */
	@SuppressWarnings( { "unchecked", "deprecation" })
	public void exportPaForLeftMenModelMoreSheet(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap,
			LinkedHashMap sqlContentmap, List aliasNameList, List aliasList,
			List mapNameList, List mapList, String excelName) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String adminID = admin.getAdminID() == null ? "USERNO_"
				+ admin.getUserNo() : admin.getAdminID();
		this.type = request.getParameter("type");
		String CpnyId = admin.getCpnyId();
		this.path = request.getRealPath("");

		int init = path.indexOf("\\");
		String server = "win";
		if (init < 0) { // WebServer的os为unix/linux
			server = "u/l";
		}
		if ("win".equals(server)) {
			path = path + "\\resources\\temp\\download\\" + CpnyId + "\\"
					+ adminID + "\\";
		} else {
			path = path + "/resources/temp/download/" + CpnyId + "/" + adminID;
		}
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet1 = wb.createSheet(excelName);
		XSSFRow row = sheet1.createRow(0);
		XSSFCell cell;

		List result = null;
		try {
			/**
			 * 根据导出报表的类型，定义列名，并且从相应的表中查询数据
			 */
			row = sheet1.createRow(0);
			for (int i = 0; i < aliasNameList.size(); i++) {
				cell = row.createCell((short) i);
				cell.setCellValue(aliasNameList.get(i).toString());
			}
			if (sqlContentmap != null
					&& sqlContentmap.get("sqlContent") != null
					&& !sqlContentmap.get("sqlContent").equals("")) {
				result = this.getExcelExportDataList(sqlContentmap);
			}
			File file = new File(this.path + "\\temp.xlsx");
			file.delete();

			if (result != null && result.size() > 0) {// 导出数据
				for (int i = 0; i < result.size(); i++) {
					int k = 0;
					row = sheet1.createRow(i + 1);
					for (int j = 0; j < aliasList.size(); j++) {
						cell = row.createCell((short) k++);
						cell
								.setCellValue(((Map) (result.get(i)))
										.get(aliasList.get(j).toString()) == null ? ""
										: ((Map) (result.get(i))).get(
												aliasList.get(j).toString())
												.toString());
					}
				}
			} else {// 下载导入模板
				List contentList = sqlContentmap.get("contentList") == null ? null
						: (List) sqlContentmap.get("contentList");
				if (contentList != null && contentList.size() > 0) {
					for (int i = 0; i < contentList.size(); i++) {
						int k = 0;
						row = sheet1.createRow(i + 1);
						Set keySet = ((Map) (contentList.get(i))).keySet();
						if (keySet != null) {
							Iterator it = keySet.iterator();
							while (it.hasNext()) {
								Object itValue = it.next();
								if (itValue != null) {
									cell = row.createCell((short) k++);
									cell.setCellValue(((Map) (contentList
											.get(i))).get(itValue).toString());
								}
							}
						}
					}
				}
			}

			for (int r = 0; r < mapList.size(); r++) {
				List sheet2List = new ArrayList();
				if (mapList.get(r) != null && "" != mapList.get(r)) {
					LinkedHashMap map = new LinkedHashMap();
					map.put("sqlContent", mapList.get(r));
					sheet2List = this.getExcelExportDataList(map);
				}
				XSSFSheet sheet2 = wb
						.createSheet(mapNameList.get(r).toString());
				String sheetName = mapNameList.get(r).toString();
				if (mapNameList != null && mapNameList.size() > 0) {// 设定sheet2的列头
					XSSFRow row2 = sheet2.createRow(0);
					XSSFCell cell2 = row2.createCell((short) 0);
					if ("项目类型".equals(sheetName)) {
						cell2.setCellValue("类型ID");
						XSSFCell cell3 = row2.createCell((short) 1);
						cell3.setCellValue("类型名称");
						// }else if("详细项目信息".equals(sheetName)){
						// cell2.setCellValue("项目编号");
						// XSSFCell cell3 = row2.createCell((short) 1);
						// cell3.setCellValue("项目ID");
						// XSSFCell cell4 = row2.createCell((short) 2);
						// cell4.setCellValue("项目名称");
						// XSSFCell cell5 = row2.createCell((short) 3);
						// cell5.setCellValue("项目所属类型");
					} else {
						cell2.setCellValue(String.valueOf(mapNameList.get(r)));
					}
				}
				for (int i = 0; i < sheet2List.size(); i++) {// 设定sheet2里头的内容
					XSSFRow row2 = sheet2.createRow(i + 1);
					XSSFCell cell2 = row2.createCell((short) 0);
					if ("项目类型".equals(sheetName)) {
						XSSFCell cell3 = row2.createCell((short) 1);
						if (sheet2List.get(i) != null) {
							cell2.setCellValue(String.valueOf(((Map) sheet2List
									.get(i)).get("ITEM_NO")));
							cell3.setCellValue(String.valueOf(((Map) sheet2List
									.get(i)).get("ITEM_NAME")));
						} else {
							cell2.setCellValue("");
							cell3.setCellValue("");
						}
						// }else if("详细项目信息".equals(sheetName)){
						// XSSFCell cell3 = row2.createCell((short) 1);
						// XSSFCell cell4 = row2.createCell((short) 2);
						// XSSFCell cell5 = row2.createCell((short) 3);
						// if (sheet2List.get(i) != null) {
						// cell2.setCellValue(String.valueOf(((Map)sheet2List.get(i)).get("ITEM_NO")));
						// cell3.setCellValue(String.valueOf(((Map)sheet2List.get(i)).get("ITEM_ID")));
						// cell4.setCellValue(String.valueOf(((Map)sheet2List.get(i)).get("ITEM_NAME")));
						// cell5.setCellValue(String.valueOf(((Map)sheet2List.get(i)).get("ITEM_TYPE")));
						// } else {
						// cell2.setCellValue("");
						// cell3.setCellValue("");
						// cell4.setCellValue("");
						// cell5.setCellValue("");
						// }
					} else {
						if (sheet2List.get(i) != null) {
							cell2.setCellValue(String.valueOf(((Map) sheet2List
									.get(i)).get("ITEM_NAME")));
						} else {
							cell2.setCellValue("");
						}
					}
				}
			}
			File f = new File(this.path);
			if (!f.exists()) {
				f.mkdirs();
			}
			FileOutputStream fos = new FileOutputStream(new File(this.path
					+ "\\temp.xlsx"));
			wb.write(fos);
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.setContentType(CONTENT_TYPE);

		try {
			File file = new File(this.path + "\\temp.xlsx");
			response.setContentType("application/x-msdownload");
			response.setContentLength((int) file.length());
			if (excelName != null && !"".equals(excelName)) {
				response.setHeader("Content-Disposition",
						"attachment;filename=temp_" + excelName + ".xlsx");
			} else {
				response.setHeader("Content-Disposition",
						"attachment;filename=temp.xlsx");
			}
			FileInputStream fis = new FileInputStream(file);
			BufferedInputStream buff = new BufferedInputStream(fis);

			byte[] b = new byte[2048];
			long k = 0;
			OutputStream myout = response.getOutputStream();
			while (k < file.length()) {
				int j = buff.read(b, 0, 2048);
				k += j;
				myout.write(b, 0, j);
			}
			myout.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 导出数据到excel Description:export datas to excel
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings( { "unchecked", "deprecation" })
	public void exportExcelTwoSheetDiaoling(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap,
			LinkedHashMap sqlContentmap, List aliasNameList, List aliasList,
			List sheet2List) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String adminID = admin.getAdminID() == null ? "USERNO_"
				+ admin.getUserNo() : admin.getAdminID();
		this.type = request.getParameter("type");
		String CpnyId = admin.getCpnyId();
		this.path = request.getRealPath("");

		int init = path.indexOf("\\");
		String server = "win";
		if (init < 0) { // WebServer的os为unix/linux
			server = "u/l";
		}
		if ("win".equals(server)) {
			path = path + "\\resources\\temp\\download\\" + CpnyId + "\\"
					+ adminID + "\\";
		} else {
			path = path + "/resources/temp/download/" + CpnyId + "/" + adminID;
		}
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet1 = wb.createSheet("sheet1");
		XSSFRow row = sheet1.createRow(0);
		XSSFCell cell;

		XSSFSheet sheet2 = wb.createSheet("sheet2");
		// XSSFRow row2 = sheet1.createRow(0);
		// XSSFCell cell2;

		List result = null;
		try {
			/**
			 * 根据导出报表的类型，定义列名，并且从相应的表中查询数据
			 */
			// if(this.type!=null&&this.type.equals("InsuranceInputItemData")){
			row = sheet1.createRow(0);
			for (int i = 0; i < aliasNameList.size(); i++) {
				cell = row.createCell((short) i);
				cell.setCellValue(aliasNameList.get(i).toString());
			}
			if (sqlContentmap != null
					&& sqlContentmap.get("sqlContent") != null
					&& !sqlContentmap.get("sqlContent").equals("")) {
				result = this.getExcelExportDataList(sqlContentmap);
			}
			// }
			File file = new File(this.path + "\\temp.xlsx");
			file.delete();

			if (result != null && result.size() > 0) {// 导出数据
				for (int i = 0; i < result.size(); i++) {
					int k = 0;
					row = sheet1.createRow(i + 1);
					for (int j = 0; j < aliasList.size(); j++) {
						cell = row.createCell((short) k++);
						cell
								.setCellValue(((Map) (result.get(i)))
										.get(aliasList.get(j).toString()) == null ? ""
										: ((Map) (result.get(i))).get(
												aliasList.get(j).toString())
												.toString());
					}
				}
			} else {// 下载导入模板
				List contentList = sqlContentmap.get("contentList") == null ? null
						: (List) sqlContentmap.get("contentList");
				if (contentList != null && contentList.size() > 0) {
					for (int i = 0; i < contentList.size(); i++) {
						int k = 0;
						row = sheet1.createRow(i + 1);
						Set keySet = ((Map) (contentList.get(i))).keySet();
						if (keySet != null) {
							Iterator it = keySet.iterator();
							while (it.hasNext()) {
								Object itValue = it.next();
								if (itValue != null) {
									cell = row.createCell((short) k++);
									cell.setCellValue(((Map) (contentList
											.get(i))).get(itValue).toString());
								}
							}
						}
					}
				}
			}
			// row = sheet2.createRow(0);

			XSSFRow row2 = sheet2.createRow(0);
			if (sheet2List != null && sheet2List.size() > 0) {// 设定sheet2的列头
				for (int i = 0; i < sheet2List.size(); i++) {

					XSSFCell cell2 = row2.createCell((short) i);
					cell2.setCellValue(((Map) (sheet2List.get(i))).get(
							"CONTENT").toString());
				}
			}
			int num = 0;
			for (int n = 1; n < 999; n++) {
				row2 = sheet2.createRow(n + 1);
			}
			for (int i = 0; i < sheet2List.size(); i++) {
				XSSFCell cell2 = row2.createCell((short) i);
				Map map = (Map) sheet2List.get(i);
				if (map.get("OUTPUT_TYPE").equals("2")
						|| map.get("OUTPUT_TYPE").equals("4")) {
					if (map.get("PARENT_TABLE_NAME").equals("SY_CODE")) {
						String parentNo = map.get("PARENT_CODE_NO").toString();
						Map paramMap = new LinkedHashMap();
						paramMap.put("PARENT_CODE_NO", parentNo);
						paramMap.put("language", Messages.getLanguage(request));
						paramMap.put("interLanguage", admin.getLanguage());
						paramMap.put("CPNY_ID", admin.getCpnyId());
						paramMap.put("ORDER_TYPE", "");
						List codeList = basicMaintenanceDao
								.getParamCodeListByCpnyID(paramMap, -1, -1);
						for (int k = 0; k < codeList.size(); k++) {
							Map m = (Map) codeList.get(k);
							XSSFRow r = sheet2.getRow(k + 1);
							XSSFCell ce = r.getCell(i + 1);
							if (ce == null)
								ce = r.createCell(k);
							// cell.setCellType(Cell.CELL_TYPE_STRING);
							ce.setCellValue(m.get("CODE_NAME").toString());

						}
					}
				}
			}

			/*
			 * for (int i = 0; i < sheet2List.size() - 1; i++) {// 设定sheet2里头的内容
			 * row2 = sheet2.createRow(i + 1); XSSFCell cell2 =
			 * row2.createCell((short) 0); if (sheet2List.get(i) != null &&
			 * ((Map) (sheet2List.get(i))).get("CONTENT") != null) {
			 * cell2.setCellValue(((Map) (sheet2List.get(i))).get(
			 * "CONTENT").toString()); } else { cell2.setCellValue(""); } }
			 */
			File f = new File(this.path);
			if (!f.exists()) {
				f.mkdirs();
			}
			FileOutputStream fos = new FileOutputStream(new File(this.path
					+ "\\temp.xlsx"));
			wb.write(fos);
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.setContentType(CONTENT_TYPE);

		File file = new File(this.path + "\\temp.xlsx");
		response.setContentType("application/x-msdownload");
		response.setContentLength((int) file.length());
		response.setHeader("Content-Disposition", "attachment;filename="
				+ "temp.xlsx");
		FileInputStream fis = new FileInputStream(file);
		BufferedInputStream buff = new BufferedInputStream(fis);

		byte[] b = new byte[2048];
		long k = 0;
		OutputStream myout = response.getOutputStream();
		while (k < file.length()) {
			int j = buff.read(b, 0, 2048);
			k += j;
			myout.write(b, 0, j);
		}
		myout.flush();
	}

	/**
	 * 分析excel的各个列，获取各个列的值，并且导入的数据库中 Description:
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param modelMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ModelMap importArData(HttpServletRequest request,
			HttpServletResponse response, LinkedHashMap map, ModelMap modelMap) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String adminID = admin.getAdminID() == null ? "USERNO_"
				+ admin.getUserNo() : admin.getAdminID();
		ObjectBindUtil.getRequestParamData(request);
		this.filename = adminID;
		this.pathCpnyID = admin.getCpnyId();
		this.language = admin.getLanguage();
		if (this.processUploadFile(request, response)) {
			int returnInt = this.impArDataBase(adminID, map, request);
			if (returnInt == 0) {
				modelMap.put("sign", "0");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.importfail", request));// 导入失败!
			} else if (returnInt < 0) {
				modelMap.put("sign", "-1");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.rulewrong", request));// 填写规则有误，请重新填写！
			} else if (returnInt == 2) {
				modelMap.put("sign", "2");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.existnull", request));// 表有空值或有空单元行，请检查！
			} else if (returnInt == 1) {
				modelMap.put("sign", "1");
				modelMap.put("statusCode", "200");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.importsuccess", request));// 导入成功
			} else if (returnInt == 3) {
				modelMap.put("sign", "3");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.dataerror", request));// 数据列数有问题，请核对
			} else if (returnInt == 4) {
				modelMap.put("sign", "3");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.datanull", request));// 所导入数据中考勤月或工号存在为空的情况，请检查核对
			} else if (returnInt == 5) {
				modelMap.put("sign", "3");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.armonthlock", request));// 所导入数据中存在考勤月已经锁定情况，不能进行数据导入
			} else if (returnInt == 6) {
				modelMap.put("sign", "3");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.empiderror", request));// 所导入数据中工号存在问题，不能进行数据导入
			} else if (returnInt == 7) {
				modelMap.put("sign", "3");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.deleteerror", request));// 删除汇总历史数据出错,请联系管理员
			} else if (returnInt > 10) {
				modelMap.put("sign", "3");
				modelMap.put("statusCode", "300");
				modelMap.put("message", String.valueOf(returnInt)
						+ TipMessage.getTipMessage(
								"ar.alert.message.excelimport.noauther",
								request));// EMPID:工号存在问题(该员工不存在，或者当前考勤员对该员工所在部门无考勤权限)
			}

			int i = path.indexOf("\\");
			String server = "win";
			if (i < 0) { // WebServer的os为unix/linux
				server = "u/l";
			}
			if ("win".equals(server)) {
				File file = new File(this.path + "\\" + filename + ".xls");
				file.delete();
			} else {
				File file = new File(this.path + "/" + filename + ".xls");
				file.delete();
			}

		} else {
			modelMap.put("sign", "0");
			modelMap.put("statusCode", "300");
			modelMap.put("message", TipMessage.getTipMessage(
					"ar.alert.message.excelimport.importfail", request));// 导入失败!
		}
		return modelMap;
	}

	@SuppressWarnings( { "unchecked" })
	public int impArDataBase(String adminID, LinkedHashMap cellmap,
			HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		int token = 1;
		int p = path.indexOf("\\");
		String server = "win";
		if (p < 0) { // WebServer的os为unix/linux
			server = "u/l";
		}
		File file = null;
		if ("win".equals(server)) {
			file = new File(path + "\\" + filename + ".xls");
		} else {
			file = new File(path + "/" + filename + ".xls");
		}
		Map dataMap = new HashMap();
		String tableName = cellmap.get("tableName") == null ? "" : cellmap.get(
				"tableName").toString();// 获取表名
		try {
			Workbook workBook = Workbook.getWorkbook(file);
			// 获得第一个工作表对象
			Sheet sheet = workBook.getSheet(0);
			// 验证是否存在导入数据
			if (sheet.getRows() < 2) {
				logger.debug("Import file not exist data, return.");
				return 0;
			}
			List addList = new ArrayList();
			// 循环检查EXCEL中数据 规定 第一列 AR_MONTH 第二列 PERSON_ID
			for (int n = 1; n < sheet.getRows(); n++) {
				Cell[] rowID = sheet.getRow(n);
				int rowLength = rowID.length;
				if (rowLength < 2) {
					return 3;// 数据列数有问题，请核对
				}
				if (("".equals(rowID[0].getContents().trim()) || rowID[0]
						.getContents() == null)
						|| ("".equals(rowID[1].getContents().trim()) || rowID[1]
								.getContents() == null)) {
					return 4;// 所导入数据中考勤月或工号存在为空的情况，请检查核对
				}
				// 检查考勤是否锁定及在汇总表中检查数据是否已存在 存在则删除
				LinkedHashMap tempMap = new LinkedHashMap();
				tempMap.put("AR_MONTH", rowID[0].getContents().trim());
				tempMap.put("EMPID", rowID[1].getContents().trim());
				tempMap.put("CPNY_ID", admin.getCpnyId());
				tempMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());
				tempMap.put("AR_SUMMARY_TABLE", tableName);
				// 查看月考勤锁定情况
				/*
				 * if (this.checkPaProgressLock(tempMap) > 0) { return 5;//
				 * 所导入数据中存在考勤月已经锁定情况，不能进行数据导入 }
				 */
				// 查看人员EMPID是否正确
				if (this.checkPersonalInfo(tempMap) == 0) {
					int rTint = 6;
					try {
						rTint = Integer.parseInt(tempMap.get("EMPID")
								.toString());
					} catch (Exception e) {
						rTint = 6;
					}
					// =6所导入数据中工号存在问题，不能进行数据导入;>10
					// EMPID:工号存在问题(该员工不存在，或者当前考勤员对该员工所在部门无考勤权限)
					return rTint;
				}
			}
			List summaryList = new ArrayList();
			// 删除考勤汇总数据
			for (int m = 1; m < sheet.getRows(); m++) {
				Cell[] rowTemp = sheet.getRow(m);
				// 检查考勤是否锁定及在汇总表中检查数据是否已存在 存在则删除
				LinkedHashMap tempMap = new LinkedHashMap();
				tempMap.put("AR_MONTH", rowTemp[0].getContents().trim());
				tempMap.put("EMPID", rowTemp[1].getContents().trim());
				tempMap.put("CPNY_ID", admin.getCpnyId());
				tempMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());
				tempMap.put("AR_SUMMARY_TABLE", tableName);

				summaryList.add(tempMap);
			}
			// 删除汇总表中数据
			if (this.deleteSummaryInfo(summaryList) != 1) {
				return 7;// 删除汇总历史数据出错,请联系管理员
			}
			// 取表头信息
			Cell[] headRow = sheet.getRow(0);
			LinkedHashMap aliasValueMapTemp = cellmap.get("aliasValueMap") == null ? null
					: (LinkedHashMap) cellmap.get("aliasValueMap");
			LinkedHashMap aliasTypeMap = cellmap.get("aliasTypeMap") == null ? null
					: (LinkedHashMap) cellmap.get("aliasTypeMap");
			for (int k = 4; k < sheet.getRow(0).length; k++) {
				// 以第三列开始计算
				LinkedHashMap tempMap = new LinkedHashMap();
				tempMap.put("CONTENT", headRow[k].getContents().trim());
				tempMap.put("CPNY_ID", admin.getCpnyId());
				tempMap.put("LANGUAGE", admin.getLanguage());
				String itemID = this.getArItemID(tempMap);
				aliasValueMapTemp.put(itemID, "#CELL" + k + "#");
				aliasTypeMap.put(itemID, 2);
			}

			// 判断AR_SUMMARY 表是否存在EMPID(工号)列 并插入数据
			int cInt = 0;
			LinkedHashMap columnMap = new LinkedHashMap();
			columnMap.put("AR_SUMMARY_TABLE", tableName);
			columnMap.put("COLUMN_NAME", "EMPID");
			cInt = this.getColumnCnt(columnMap);
			// 循环工作表的每行数据
			int init = 0;
			for (int i = 1; i < sheet.getRows(); i++) {
				// 验证当前行是否符合规范
				// if (!validateCell(sheet.getRow(i), sheet.getRow(0).length))
				// continue;
				Cell[] row = sheet.getRow(i);
				if (init == 0) {
					init = row.length;
				}
				// LinkedHashMap aliasValueMap =
				// cellmap.get("aliasValueMap")==null?null:(LinkedHashMap)cellmap.get("aliasValueMap");
				// LinkedHashMap aliasTypeMap =
				// cellmap.get("aliasTypeMap")==null?null:(LinkedHashMap)cellmap.get("aliasTypeMap");
				LinkedHashMap aliasValueI18nMap = cellmap
						.get("aliasValueI18nMap") == null ? null
						: (LinkedHashMap) cellmap.get("aliasValueI18nMap");
				// 插入部门信息 取工号列(第一列)
				LinkedHashMap deptMap = new LinkedHashMap();
				deptMap.put("EMPID", row[1].getContents().trim());
				deptMap.put("CPNY_ID", admin.getCpnyId());
				String DEPTNO = this.getDeptNo(deptMap);
				aliasValueMapTemp.put("DEPTNO", DEPTNO);
				aliasTypeMap.put("DEPTNO", 0);

				// 插入EMPID
				if (cInt > 0) {
					aliasValueMapTemp.put("EMPID", row[1].getContents().trim());
					aliasTypeMap.put("EMPID", 0);
				}

				LinkedHashMap smap = new LinkedHashMap();
				LinkedHashMap aliasCellmap = new LinkedHashMap();
				// 循环 考勤月 工号 姓名 部门 列
				for (int j = 0; j < 4; j++) {
					String cellValue = "";
					cellValue = row[j].getContents().trim();

					aliasCellmap.put("#CELL" + j + "#", cellValue);
				}
				// 循环数据列
				for (int j = 4; j < init; j++) {
					String cellValue = "";
					float intValue = 0;

					try {
						cellValue = row[j].getContents();
						intValue = Float.parseFloat(cellValue);
					} catch (Exception e) {
						intValue = 0;
					}

					aliasCellmap.put("#CELL" + j + "#", intValue);
				}

				String aliasKeySql = "";
				String aliasValueSql = "";
				int initKey = 0;
				if (aliasValueMapTemp != null && aliasTypeMap != null) {
					Set keySet = aliasValueMapTemp.keySet();
					if (keySet != null) {
						Iterator it = keySet.iterator();
						while (it.hasNext()) {
							initKey++;
							Object keyValue = it.next();
							if (keyValue != null && !keyValue.equals("")) {
								int fieldType = Integer.parseInt(aliasTypeMap
										.get(keyValue).toString());
								Object aliasCellmapValue = aliasCellmap
										.get(aliasValueMapTemp.get(keyValue));
								if (aliasCellmapValue == null) {// 不属于excel的列
									aliasCellmapValue = aliasValueMapTemp
											.get(keyValue);
								} else {
									if (aliasValueI18nMap != null
											&& aliasValueI18nMap.get(keyValue) != null) {// 有需要从国际化表读取No的列
										String sqlContent = aliasValueI18nMap
												.get(keyValue).toString();
										sqlContent = sqlContent.replaceAll(
												aliasValueMapTemp.get(keyValue)
														.toString(), "'"
														+ aliasCellmapValue
																.toString()
														+ "'");
										LinkedHashMap sqlI8nContentMap = new LinkedHashMap();
										sqlI8nContentMap.put("sqlI18nContent",
												sqlContent);
										aliasCellmapValue = this
												.getCodeNoByName(sqlI8nContentMap) == -1 ? ""
												: this
														.getCodeNoByName(sqlI8nContentMap);
									}
								}
								switch (fieldType) {
								case 0:
									// smap.put(keyValue.toString(),
									// "'"+aliasValueMap.get(keyValue)+"'");
									if (initKey == 1) {
										aliasKeySql = keyValue.toString();
										aliasValueSql = "'" + aliasCellmapValue
												+ "'";
									} else {
										aliasKeySql += ","
												+ keyValue.toString();
										aliasValueSql += ",'"
												+ aliasCellmapValue + "'";
									}
									break;
								case 1:
									if (initKey == 1) {
										aliasKeySql = keyValue.toString();
										aliasValueSql = "to_date('"
												+ aliasCellmapValue
												+ "','yyyy-MM-dd')";// "'"+aliasCellmapValue+"'";
									} else {
										aliasKeySql += ","
												+ keyValue.toString();
										aliasValueSql += ",to_date('"
												+ aliasCellmapValue
												+ "','yyyy-MM-dd')";// "'"+aliasCellmapValue+"'";
									}
									break;
								case 11:
									if (initKey == 1) {
										aliasKeySql = keyValue.toString();
										aliasValueSql = "to_date('"
												+ aliasCellmapValue
												+ "','yyyy-MM-dd HH24:MI:ss')";// "'"+aliasCellmapValue+"'";
									} else {
										aliasKeySql += ","
												+ keyValue.toString();
										aliasValueSql += ",to_date('"
												+ aliasCellmapValue
												+ "','yyyy-MM-dd HH24:MI:ss')";// "'"+aliasCellmapValue+"'";
									}
									break;
								case 12:
									if (initKey == 1) {
										aliasKeySql = keyValue.toString();
										aliasValueSql = "to_date('"
												+ aliasCellmapValue
												+ "','yyyy-MM-dd HH24:MI')";// "'"+aliasCellmapValue+"'";
									} else {
										aliasKeySql += ","
												+ keyValue.toString();
										aliasValueSql += ",to_date('"
												+ aliasCellmapValue
												+ "','yyyy-MM-dd HH24:MI')";// "'"+aliasCellmapValue+"'";
									}
									break;
								default:
									// smap.put(keyValue.toString(),
									// aliasValueMap.get(keyValue));
									if (initKey == 1) {
										aliasKeySql = keyValue.toString();
										aliasValueSql = aliasCellmapValue
												.toString();
									} else {
										aliasKeySql += ","
												+ keyValue.toString();
										aliasValueSql += ","
												+ aliasCellmapValue + "";
									}
									break;
								}
							}
						}
					}
				}
				smap.put("tableName", tableName);
				smap.put("aliasNameKeyContent", aliasKeySql);
				smap.put("aliasValueContent", aliasValueSql);
				addList.add(smap);
			}
			dataMap.put("addList", addList);
			// int result=this.insertExcelData(dataMap);
			this.insertExcelData(dataMap);
			// if(result==1){
			// token = 1;
			// }else{
			// token = 0;
			// }
		} catch (Exception e) { // 未找到文件异常
			e.printStackTrace();
			token = 0;
		}
		return token;
	}

	public String FormateData(Cell formatecell, Object type) {

		String restr = "";

		try {
			// java.util.Date mydate = null;
			// String content = formatecell.getContents();
			// DateCell datecll = (DateCell) formatecell;
			// mydate = datecll.getDate();
			// long time = (mydate.getTime() / 1000);
			// mydate.setTime(time * 1000);
			// SimpleDateFormat formatter = null;
			// if (type != null && type.toString().equals("11")) {
			// if (content != null && content.contains("-")) {
			// formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			// } else if (content != null && content.contains("/")) {
			// formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			// }
			// } else if (type != null && type.toString().equals("1")) {//
			// yyyy-MM-dd
			// if (content != null && content.contains("-")) {
			// formatter = new SimpleDateFormat("yyyy/MM/dd");
			// } else if (content != null && content.contains("/")) {
			// formatter = new SimpleDateFormat("yyyy-MM-dd");
			// }
			// } else if (type != null && type.toString().equals("12")) {//
			// yyyy-MM-dd
			// // hh:mm
			// if (content != null && content.contains("-")) {
			// formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");
			// } else if (content != null && content.contains("/")) {
			// formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			// }
			// } else if (type != null && type.toString().equals("13")) {//
			// yyyy-MM-dd
			// if (content != null && content.contains("-")) {
			// formatter = new SimpleDateFormat("yyyy/MM/dd");
			// } else if (content != null && content.contains("/")) {
			// formatter = new SimpleDateFormat("yyyy-MM-dd");
			// }
			// }
			// 取格林威治时间
			TimeZone gmt = TimeZone.getTimeZone("GMT");
			java.util.Date mydate = null;
			String content = formatecell.getContents();
			DateCell datecll = (DateCell) formatecell;
			// DateCell datecll = null;
			// try{
			// //excel 中如果设置时间格式那么默认以；分割
			// datecll = (DateCell)
			// ((Cell)((Object)(formatecell.getContents().split(";"))[0]));
			// }catch (Exception e) {
			// datecll = (DateCell) formatecell;
			// }
			mydate = datecll.getDate();
			long time = (mydate.getTime() / 1000);
			mydate.setTime(time * 1000);
			SimpleDateFormat formatter = null;
			// 设置时区为GMT EXCEL中会默认林威治时间（与服务器时区设置有关系） 然后统一转化成本地时间 这样才能保证导入时间准确性

			if (type != null && type.toString().equals("11")) {

				if (content != null && content.contains("-")) {
					formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss",
							Locale.getDefault());
				} else if (content != null && content.contains("/")) {
					formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
							Locale.getDefault());
				}

				formatter.setTimeZone(gmt);
				restr = formatter.format(mydate);

			} else if (type != null && type.toString().equals("1")) {// yyyy-MM-dd

				if (content != null && content.contains("-")) {
					formatter = new SimpleDateFormat("yyyy/MM/dd", Locale
							.getDefault());
				} else if (content != null && content.contains("/")) {
					formatter = new SimpleDateFormat("yyyy-MM-dd", Locale
							.getDefault());
				}

				formatter.setTimeZone(gmt);
				restr = formatter.format(mydate);

			} else if (type != null && type.toString().equals("12")) {// yyyy-MM-dd
				// hh:mm
				if (content != null && content.contains("-")) {
					formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale
							.getDefault());
				} else if (content != null && content.contains("/")) {
					formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale
							.getDefault());
				}

				formatter.setTimeZone(gmt);
				restr = formatter.format(mydate);

			} else if (type != null && type.toString().equals("13")) {// yyyy-MM-dd

				if (content != null && content.contains("-")) {
					formatter = new SimpleDateFormat("yyyy/MM/dd", Locale
							.getDefault());
				} else if (content != null && content.contains("/")) {
					formatter = new SimpleDateFormat("yyyy-MM-dd", Locale
							.getDefault());
				}

				formatter.setTimeZone(gmt);
				restr = formatter.format(mydate);

			} else {
				restr = formatecell.getContents() == null ? "" : formatecell
						.getContents();
			}

		} catch (Exception e) {
			// e.printStackTrace();
			restr = formatecell.getContents() == null ? "" : formatecell
					.getContents();
		}

		return restr;
	}

	/**
	 * 导出数据到excel的步骤一分为二，一，先导出到excel Description:export datas to excel
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings( { "unchecked", "deprecation" })
	public String exportIntoExcel(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap,
			LinkedHashMap sqlContentmap, List aliasNameList, List aliasList)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String adminID = admin.getAdminID() == null ? "USERNO_"
				+ admin.getUserNo() : admin.getAdminID();
		this.type = request.getParameter("type");
		String CpnyId = admin.getCpnyId();
		this.path = request.getRealPath("");

		int init = path.indexOf("\\");
		String server = "win";
		if (init < 0) { // WebServer的os为unix/linux
			server = "u/l";
		}
		if ("win".equals(server)) {
			path = path + "\\resources\\temp\\download\\" + CpnyId + "\\"
					+ adminID + "\\";
		} else {
			path = path + "/resources/temp/download/" + CpnyId + "/" + adminID;
		}
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet1 = wb.createSheet("sheet1");
		XSSFRow row = sheet1.createRow(0);
		XSSFCell cell;

		List result = null;
		// try{
		// 根据导出报表的类型，定义列名，并且从相应的表中查询数据
		// if(this.type!=null&&this.type.equals("InsuranceInputItemData")){
		row = sheet1.createRow(0);
		for (int i = 0; i < aliasNameList.size(); i++) {
			cell = row.createCell((short) i);
			cell.setCellValue(aliasNameList.get(i).toString());
		}
		if (sqlContentmap != null && sqlContentmap.get("sqlContent") != null
				&& !sqlContentmap.get("sqlContent").equals("")) {
			result = this.getExcelExportDataList(sqlContentmap);
		}
		// }
		File file = new File(this.path + "\\temp.xlsx");
		file.delete();

		if (result != null && result.size() > 0) {// 导出数据
			for (int i = 0; i < result.size(); i++) {
				int k = 0;
				row = sheet1.createRow(i + 1);
				for (int j = 0; j < aliasList.size(); j++) {
					cell = row.createCell((short) k++);
					String str = StringUtil.checkNull(aliasList.get(j));
					SimpleDateFormat simpleFormate = new SimpleDateFormat(
							"yyyy-MM-dd");

					if (str.equals("IDCARD_NO")) {
						String str1 = StringUtil.checkNull(((Map) (result
								.get(i))).get(str));
						cell.setCellValue(str1);
					} else {
						try {
							Double str1 = Double
									.parseDouble(StringUtil
											.checkNull(((Map) (result.get(i)))
													.get(str)));
							cell.setCellValue(str1);
						} catch (Exception e) {
							try {
								String str1 = simpleFormate
										.format(simpleFormate.parse(StringUtil
												.checkNull(((Map) (result
														.get(i))).get(str))));
								cell.setCellValue(str1);
							} catch (Exception e1) {
								String str1 = StringUtil
										.checkNull(((Map) (result.get(i)))
												.get(str));
								cell.setCellValue(str1);
							}

						}
					}
					// Object str1 = ((Map) (result.get(i))).get(str);
					// cell.setCellValue(((Map)
					// (result.get(i))).get(aliasList.get(j).toString()) == null
					// ? "" :
					// ((Map)
					// (result.get(i))).get(aliasList.get(j).toString()).toString());

				}
			}
		} else {// 下载导入模板
			List contentList = sqlContentmap.get("contentList") == null ? null
					: (List) sqlContentmap.get("contentList");
			if (contentList != null && contentList.size() > 0) {
				for (int i = 0; i < contentList.size(); i++) {
					int k = 0;
					row = sheet1.createRow(i + 1);
					Set keySet = ((Map) (contentList.get(i))).keySet();
					if (keySet != null) {
						Iterator it = keySet.iterator();
						while (it.hasNext()) {
							Object itValue = it.next();
							if (itValue != null) {
								cell = row.createCell((short) k++);
								cell.setCellValue(((Map) (contentList.get(i)))
										.get(itValue) == null ? ""
										: ((Map) (contentList.get(i))).get(
												itValue).toString());
							}
						}
					}
				}
			}
		}
		File f = new File(this.path);
		if (!f.exists()) {
			f.mkdirs();
		}
		FileOutputStream fos = new FileOutputStream(new File(this.path
				+ "\\temp.xlsx"));
		wb.write(fos);

		// 删除登录者之前选中的所有项目
		LinkedHashMap userMap = new LinkedHashMap();
		userMap.put("PERSON_ID", admin.getPersonId() != null ? admin
				.getPersonId() : null);
		userMap.put("USERNAME", admin.getUsername() != null ? admin
				.getUsername() : null);
		userMap.put("CPNY_ID", admin.getCpnyId() != null ? admin.getCpnyId()
				: "");
		// "1"表示为工资的计算结果
		userMap.put("FUNCTIONFLAG", request.getParameter("functionFlag"));
		// 计算结果区分标示(1:计算结果1 2:计算结果2)
		userMap.put("DISTINGUISH", request.getParameter("distinguish"));
		if (this.paResultDao.selectSelectedOptions(userMap) > 0) {
			this.paResultDao.deleteSelectedOptions(userMap);
		}

		// 增加保存导出Excel选项功能
		List object = new ArrayList();
		Map optionsMap = null;
		for (int i = 0; i < aliasList.size(); i++) {
			optionsMap = new LinkedHashMap();
			optionsMap.put("PERSON_ID", admin.getPersonId() != null ? admin
					.getPersonId() : "");
			optionsMap.put("CPNY_ID", admin.getCpnyId() != null ? admin
					.getCpnyId() : "");
			optionsMap.put("USERNAME", admin.getUsername() != null ? admin
					.getUsername() : "");
			optionsMap.put("ITEM_ID", aliasList.get(i));
			optionsMap.put("CREATE_BY", admin.getPersonId() != null ? admin
					.getPersonId() : admin.getUsername());
			optionsMap
					.put("FUNCTIONFLAG", request.getParameter("functionFlag"));
			optionsMap.put("DISTINGUISH", request.getParameter("distinguish"));
			optionsMap.put("ORDERNO", (i + 1));
			object.add(optionsMap);
		}
		this.excelUtilDao.insertSelectedOptionsByExprotExcel(object);

		return this.path + "\\temp.xlsx";
	}

	/**
	 * 导出数据到excel的步骤一分为二，一，先导出到excel Description:export datas to excel
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings( { "unchecked", "deprecation" })
	public String exportIntoExcel(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap,
			LinkedHashMap sqlContentmap, List aliasNameList, List aliasList,
			List aliasListExpType) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String adminID = admin.getAdminID() == null ? "USERNO_"
				+ admin.getUserNo() : admin.getAdminID();
		this.type = request.getParameter("type");
		String CpnyId = admin.getCpnyId();
		this.path = request.getRealPath("");
		String saveYn = request.getParameter("saveYn");
		int init = path.indexOf("\\");
		String server = "win";
		if (init < 0) { // WebServer的os为unix/linux
			server = "u/l";
		}
		if ("win".equals(server)) {
			path = path + "\\resources\\temp\\download\\" + CpnyId + "\\"
					+ adminID + "\\";
		} else {
			path = path + "/resources/temp/download/" + CpnyId + "/" + adminID;
		}
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet1 = wb.createSheet("sheet1");
		XSSFRow row = sheet1.createRow(0);
		XSSFCell cell;

		List result = null;
		// try{
		// 根据导出报表的类型，定义列名，并且从相应的表中查询数据
		// if(this.type!=null&&this.type.equals("InsuranceInputItemData")){
		row = sheet1.createRow(0);
		for (int i = 0; i < aliasNameList.size(); i++) {
			cell = row.createCell((short) i);
			cell.setCellValue(aliasNameList.get(i).toString());
		}
		if (sqlContentmap != null && sqlContentmap.get("sqlContent") != null
				&& !sqlContentmap.get("sqlContent").equals("")) {
			result = this.getExcelExportDataList(sqlContentmap);
		}
		// }
		File file = new File(this.path + "\\temp.xlsx");
		file.delete();

		if (result != null && result.size() > 0) {// 导出数据
			for (int i = 0; i < result.size(); i++) {
				int k = 0;
				row = sheet1.createRow(i + 1);
				for (int j = 0; j < aliasList.size(); j++) {
					cell = row.createCell((short) k++);
					String strTemp = StringUtil.checkNull(aliasList.get(j));
					String str = null;
					if (strTemp.indexOf("@") != -1) {
						str = strTemp.split("@")[0];
					} else {
						str = strTemp;
					}
					String expFlag = StringUtil.checkNull(aliasListExpType
							.get(j));
					SimpleDateFormat simpleFormate = new SimpleDateFormat(
							"yyyy-MM-dd");

					if (str.equals("IDCARD_NO")) {
						String str1 = StringUtil.checkNull(((Map) (result
								.get(i))).get(str));
						cell.setCellValue(str1);
					} else {
						try {
							Double str1 = Double
									.parseDouble(StringUtil
											.checkNull(((Map) (result.get(i)))
													.get(str)));
							if (expFlag.equals("1")) {
								DecimalFormat df = new DecimalFormat("#.#####");
								String db = df.format(str1);
								str1 = Double.parseDouble(db);
								cell.setCellValue(str1);
								XSSFCellStyle cellStyle = wb.createCellStyle();
								cellStyle.setDataFormat((short) BuiltinFormats
										.getBuiltinFormat("0.00000"));
								cell.setCellStyle(cellStyle);
							} else {
								cell.setCellValue(str1);
							}
						} catch (Exception e) {
							try {
								String str1 = simpleFormate
										.format(simpleFormate.parse(StringUtil
												.checkNull(((Map) (result
														.get(i))).get(str))));
								cell.setCellValue(str1);
							} catch (Exception e1) {
								String str1 = StringUtil
										.checkNull(((Map) (result.get(i)))
												.get(str));
								cell.setCellValue(str1);
							}

						}
					}
					// Object str1 = ((Map) (result.get(i))).get(str);
					// cell.setCellValue(((Map)
					// (result.get(i))).get(aliasList.get(j).toString()) == null
					// ? "" :
					// ((Map)
					// (result.get(i))).get(aliasList.get(j).toString()).toString());

				}
			}
		} else {// 下载导入模板
			List contentList = sqlContentmap.get("contentList") == null ? null
					: (List) sqlContentmap.get("contentList");
			if (contentList != null && contentList.size() > 0) {
				for (int i = 0; i < contentList.size(); i++) {
					int k = 0;
					row = sheet1.createRow(i + 1);
					Set keySet = ((Map) (contentList.get(i))).keySet();
					if (keySet != null) {
						Iterator it = keySet.iterator();
						while (it.hasNext()) {
							Object itValue = it.next();
							if (itValue != null) {
								cell = row.createCell((short) k++);
								cell.setCellValue(((Map) (contentList.get(i)))
										.get(itValue) == null ? ""
										: ((Map) (contentList.get(i))).get(
												itValue).toString());
							}
						}
					}
				}
			}
		}
		File f = new File(this.path);
		if (!f.exists()) {
			f.mkdirs();
		}
		FileOutputStream fos = new FileOutputStream(new File(this.path
				+ "\\temp.xlsx"));
		wb.write(fos);

		// 删除登录者之前选中的所有项目
		LinkedHashMap userMap = new LinkedHashMap();
		/*
		 * userMap.put("PERSON_ID", admin.getPersonId() != null ?
		 * admin.getPersonId() : null); userMap.put("USERNAME",
		 * admin.getUsername() != null ? admin.getUsername() : null);
		 */
		userMap.put("CPNY_ID", admin.getCpnyId() != null ? admin.getCpnyId()
				: "");
		// "1"表示为工资的计算结果
		/*
		 * userMap.put("FUNCTIONFLAG", request.getParameter("functionFlag")); //
		 * 计算结果区分标示(1:计算结果1 2:计算结果2) userMap.put("DISTINGUISH",
		 * request.getParameter("distinguish"));
		 */

		if ("Y".equals(saveYn)) {
			// if (this.paResultDao.selectSelectedOptions(userMap) > 0) {
			this.paResultDao.deleteSelectedOptions(userMap);
			// }
		}

		// 增加保存导出Excel选项功能
		List object = new ArrayList();
		Map optionsMap = null;
		for (int i = 0; i < aliasList.size(); i++) {
			String str[] = aliasList.get(i).toString().split("@");
			optionsMap = new LinkedHashMap();
			optionsMap.put("PERSON_ID", admin.getPersonId() != null ? admin
					.getPersonId() : "");
			optionsMap.put("CPNY_ID", admin.getCpnyId() != null ? admin
					.getCpnyId() : "");
			optionsMap.put("USERNAME", admin.getUsername() != null ? admin
					.getUsername() : "");
			optionsMap.put("ITEM_ID", str[0]);
			// if("Y".equals(sqlContentmap.get("aliasType"+(i+1))) ||
			// sqlContentmap.get("aliasType"+(i+1)) == "Y"){
			// optionsMap.put("DATA_TYPE","1");
			// }else{
			// if("1".equals(aliasListExpType.get(i)) ||
			// aliasListExpType.get(i)=="1"){
			// optionsMap.put("DATA_TYPE","3");
			// }else if("2".equals(aliasListExpType.get(i)) ||
			// aliasListExpType.get(i)=="2"){
			// optionsMap.put("DATA_TYPE","4");
			// }else{
			// optionsMap.put("DATA_TYPE","2");
			// }
			// }
			optionsMap.put("DATA_TYPE", str[2]);
			optionsMap.put("CREATE_BY", admin.getPersonId() != null ? admin
					.getPersonId() : admin.getUsername());
			optionsMap
					.put("FUNCTIONFLAG", request.getParameter("functionFlag"));
			optionsMap.put("DISTINGUISH", request.getParameter("distinguish"));
			optionsMap.put("ORDERNO", str[1]);
			object.add(optionsMap);
		}
		if ("Y".equals(saveYn)) {
			this.excelUtilDao.insertSelectedOptionsByExprotExcel(object);
		}
		return this.path + "\\temp.xlsx";
	}

	/**
	 * 导出数据到excel的步骤一分为二，一，先导出到excel Description:export datas to excel
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings( { "unchecked", "deprecation" })
	public String exportIntoExcel1(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap,
			LinkedHashMap sqlContentmap, List aliasNameList, List aliasList)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String adminID = admin.getAdminID() == null ? "USERNO_"
				+ admin.getUserNo() : admin.getAdminID();
		this.type = request.getParameter("type");
		String CpnyId = admin.getCpnyId();
		this.path = request.getRealPath("");

		int init = path.indexOf("\\");
		String server = "win";
		if (init < 0) { // WebServer的os为unix/linux
			server = "u/l";
		}
		if ("win".equals(server)) {
			path = path + "\\resources\\temp\\download\\" + CpnyId + "\\"
					+ adminID + "\\";
		} else {
			path = path + "/resources/temp/download/" + CpnyId + "/" + adminID;
		}
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet1 = wb.createSheet("sheet1");
		XSSFRow row = sheet1.createRow(0);
		XSSFCell cell;

		List result = null;
		// try{
		// 根据导出报表的类型，定义列名，并且从相应的表中查询数据
		// if(this.type!=null&&this.type.equals("InsuranceInputItemData")){
		row = sheet1.createRow(0);
		for (int i = 0; i < aliasNameList.size(); i++) {
			cell = row.createCell((short) i);
			cell.setCellValue(aliasNameList.get(i).toString());
		}
		if (sqlContentmap != null && sqlContentmap.get("sqlContent") != null
				&& !sqlContentmap.get("sqlContent").equals("")) {
			result = this.getExcelExportDataList(sqlContentmap);
		}
		// }
		File file = new File(this.path + "\\temp.xlsx");
		file.delete();

		// if (result != null && result.size() > 0) {// 导出数据
		/*
		 * for (int i = 0; i < aliasList.size(); i++) { int k = 0; row =
		 * sheet1.createRow(i + 1); for (int j = 0; j < aliasList.size(); j++) {
		 * cell = row.createCell((short) k++); String str =
		 * StringUtil.checkNull(aliasList.get(j)); SimpleDateFormat
		 * simpleFormate = new SimpleDateFormat( "yyyy-MM-dd" );
		 * 
		 * if(str.equals("IDCARD_NO")){ String str1 =
		 * StringUtil.checkNull(((Map) (result.get(i))).get(str));
		 * cell.setCellValue(str1); }else{ try{ Double str1 =
		 * Double.parseDouble(StringUtil.checkNull(((Map)
		 * (result.get(i))).get(str))); cell.setCellValue(str1);
		 * }catch(Exception e){ try { String str1=simpleFormate.format(
		 * simpleFormate.parse(StringUtil.checkNull(((Map)
		 * (result.get(i))).get(str)))); cell.setCellValue(str1); } catch
		 * (Exception e1) { String str1 = StringUtil.checkNull(((Map)
		 * (result.get(i))).get(str)); cell.setCellValue(str1); }
		 * 
		 * } } // Object str1 = ((Map) (result.get(i))).get(str); //
		 * cell.setCellValue(((Map)
		 * (result.get(i))).get(aliasList.get(j).toString()) == null ? "" : //
		 * ((Map) (result.get(i))).get(aliasList.get(j).toString()).toString());
		 * 
		 * } }
		 */
		/*
		 * } else {// 下载导入模板 List contentList = sqlContentmap.get("contentList")
		 * == null ? null: (List) sqlContentmap.get("contentList"); if
		 * (contentList != null && contentList.size() > 0) { for (int i = 0; i <
		 * contentList.size(); i++) { int k = 0; row = sheet1.createRow(i + 1);
		 * Set keySet = ((Map) (contentList.get(i))).keySet(); if (keySet !=
		 * null) { Iterator it = keySet.iterator(); while (it.hasNext()) {
		 * Object itValue = it.next(); if (itValue != null) { cell =
		 * row.createCell((short) k++); cell.setCellValue(((Map)
		 * (contentList.get(i))).get(itValue) == null ? "": ((Map)
		 * (contentList.get(i))).get(itValue).toString()); } } } } } }
		 */
		// 序号,法人名称,年份,工号,姓名,部门,职级名称, 职责,入职日期,01月 02月 03月 04月 05月 06月 07月 08月 09月
		// 10月 11月 12月
		/*
		 * [{COMPANY_NAME=China HQ, PA_YEAR=2013, EMPID=12000001,
		 * LOCAL_NAME=左祥奉, DEPT_NAME=董事长, POSITION_NAME=决策层, POST_NAME=常务,
		 * POST_GRADE_NAME=18, DUTY_NAME=董事长, DATE_STARTED=2012-02-06,
		 * M01_SALARY=0, M02_SALARY=0, M03_SALARY=0, M04_SALARY=0, M05_SALARY=0,
		 * M06_SALARY=0, M07_SALARY=0, M08_SALARY=0, M09_SALARY=0, M10_SALARY=0,
		 * M11_SALARY=0, M12_SALARY=0, SUM_SALARY=0},
		 */
		String[] nameTitle = { "序号", "COMPANY_NAME", "PA_YEAR", "EMPID",
				"LOCAL_NAME", "DEPT_NAME", "POST_NAME", "DUTY_NAME",
				"DATE_STARTED", "M01_SALARY", "M02_SALARY", "M03_SALARY",
				"M04_SALARY", "M05_SALARY", "M06_SALARY", "M07_SALARY",
				"M08_SALARY", "M09_SALARY", "M10_SALARY", "M11_SALARY",
				"M12_SALARY", "SUM_SALARY" };
		for (int i = 0; i < aliasList.size(); i++) {
			int k = 0;
			row = sheet1.createRow(i + 1);
			for (int j = 0; j < nameTitle.length; j++) {
				cell = row.createCell((short) k++);
				if (j == 0) {
					cell.setCellValue(i + 1);
					continue;
				}
				// cell.setCellValue(((Map)
				// (result.get(i))).get(aliasList.get(j).toString()) == null ?
				// " ": ((Map)
				// (result.get(i))).get(aliasList.get(j).toString()).toString());
				Map map = (Map) aliasList.get(i);
				String title = "";
				if (map.get(nameTitle[j]) != null) {
					title = map.get(nameTitle[j]).toString();
				}
				if (j >= 9 && j <= 21) {
					try {
						Double str1 = Double.parseDouble(StringUtil
								.checkNull(title));
						DecimalFormat df = new DecimalFormat("#.##");
						String db = df.format(str1);
						str1 = Double.parseDouble(db);
						cell.setCellValue(str1);
						XSSFCellStyle cellStyle = wb.createCellStyle();
						cellStyle.setDataFormat((short) BuiltinFormats
								.getBuiltinFormat("0.00"));
						cell.setCellStyle(cellStyle);
					} catch (Exception e) {
						cell.setCellValue(Double.parseDouble(title));
					}
				} else {
					cell.setCellValue(title);
				}
			}
		}
		File f = new File(this.path);
		if (!f.exists()) {
			f.mkdirs();
		}
		FileOutputStream fos = new FileOutputStream(new File(this.path
				+ "\\temp.xlsx"));
		wb.write(fos);

		/*
		 * //删除登录者之前选中的所有项目 LinkedHashMap userMap=new LinkedHashMap();
		 * userMap.put("PERSON_ID", admin.getPersonId() != null ?
		 * admin.getPersonId() : null); userMap.put("USERNAME",
		 * admin.getUsername() != null ? admin.getUsername() : null);
		 * userMap.put("CPNY_ID", admin.getCpnyId() != null ? admin.getCpnyId()
		 * : ""); //"1"表示为工资的计算结果 userMap.put("FUNCTIONFLAG",
		 * request.getParameter("functionFlag")); //计算结果区分标示(1:计算结果1 2:计算结果2)
		 * userMap.put("DISTINGUISH", request.getParameter("distinguish"));
		 * if(this.paResultDao.selectSelectedOptions(userMap) > 0){
		 * this.paResultDao.deleteSelectedOptions(userMap); }
		 * 
		 * //增加保存导出Excel选项功能 List object=new ArrayList(); Map optionsMap=null;
		 * for(int i=0;i<aliasList.size();i++){ optionsMap=new LinkedHashMap();
		 * optionsMap.put("PERSON_ID", admin.getPersonId() != null ?
		 * admin.getPersonId() : ""); optionsMap.put("CPNY_ID",
		 * admin.getCpnyId() != null ? admin.getCpnyId() : "");
		 * optionsMap.put("USERNAME", admin.getUsername() != null ?
		 * admin.getUsername() : ""); optionsMap.put("ITEM_ID",
		 * aliasList.get(i)); optionsMap.put("CREATE_BY", admin.getPersonId() !=
		 * null ? admin.getPersonId() : admin.getUsername());
		 * optionsMap.put("FUNCTIONFLAG", request.getParameter("functionFlag"));
		 * optionsMap.put("DISTINGUISH", request.getParameter("distinguish"));
		 * optionsMap.put("ORDERNO", (i+1)); object.add(optionsMap); }
		 * this.excelUtilDao.insertSelectedOptionsByExprotExcel(object);
		 */

		return this.path + "\\temp.xlsx";
	}

	/**
	 * 导出数据到excel的步骤一分为二，一，先导出到excel Description:export datas to excel
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings( { "unchecked", "deprecation" })
	public String exportIntoExcel2(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap,
			LinkedHashMap sqlContentmap, List aliasNameList, List aliasList)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String adminID = admin.getAdminID() == null ? "USERNO_"
				+ admin.getUserNo() : admin.getAdminID();
		this.type = request.getParameter("type");
		String CpnyId = admin.getCpnyId();
		this.path = request.getRealPath("");

		int init = path.indexOf("\\");
		String server = "win";
		if (init < 0) { // WebServer的os为unix/linux
			server = "u/l";
		}
		if ("win".equals(server)) {
			path = path + "\\resources\\temp\\download\\" + CpnyId + "\\"
					+ adminID + "\\";
		} else {
			path = path + "/resources/temp/download/" + CpnyId + "/" + adminID;
		}
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet1 = wb.createSheet("sheet1");
		XSSFRow row = sheet1.createRow(0);
		XSSFCell cell;

		List result = null;
		// try{
		// 根据导出报表的类型，定义列名，并且从相应的表中查询数据
		// if(this.type!=null&&this.type.equals("InsuranceInputItemData")){
		row = sheet1.createRow(0);
		for (int i = 0; i < aliasNameList.size(); i++) {
			cell = row.createCell((short) i);
			cell.setCellValue(aliasNameList.get(i).toString());
		}
		if (sqlContentmap != null && sqlContentmap.get("sqlContent") != null
				&& !sqlContentmap.get("sqlContent").equals("")) {
			result = this.getExcelExportDataList(sqlContentmap);
		}
		// }
		File file = new File(this.path + "\\temp.xlsx");
		file.delete();

		// if (result != null && result.size() > 0) {// 导出数据
		/*
		 * for (int i = 0; i < aliasList.size(); i++) { int k = 0; row =
		 * sheet1.createRow(i + 1); for (int j = 0; j < aliasList.size(); j++) {
		 * cell = row.createCell((short) k++); String str =
		 * StringUtil.checkNull(aliasList.get(j)); SimpleDateFormat
		 * simpleFormate = new SimpleDateFormat( "yyyy-MM-dd" );
		 * 
		 * if(str.equals("IDCARD_NO")){ String str1 =
		 * StringUtil.checkNull(((Map) (result.get(i))).get(str));
		 * cell.setCellValue(str1); }else{ try{ Double str1 =
		 * Double.parseDouble(StringUtil.checkNull(((Map)
		 * (result.get(i))).get(str))); cell.setCellValue(str1);
		 * }catch(Exception e){ try { String str1=simpleFormate.format(
		 * simpleFormate.parse(StringUtil.checkNull(((Map)
		 * (result.get(i))).get(str)))); cell.setCellValue(str1); } catch
		 * (Exception e1) { String str1 = StringUtil.checkNull(((Map)
		 * (result.get(i))).get(str)); cell.setCellValue(str1); }
		 * 
		 * } } // Object str1 = ((Map) (result.get(i))).get(str); //
		 * cell.setCellValue(((Map)
		 * (result.get(i))).get(aliasList.get(j).toString()) == null ? "" : //
		 * ((Map) (result.get(i))).get(aliasList.get(j).toString()).toString());
		 * 
		 * } }
		 */
		/*
		 * } else {// 下载导入模板 List contentList = sqlContentmap.get("contentList")
		 * == null ? null: (List) sqlContentmap.get("contentList"); if
		 * (contentList != null && contentList.size() > 0) { for (int i = 0; i <
		 * contentList.size(); i++) { int k = 0; row = sheet1.createRow(i + 1);
		 * Set keySet = ((Map) (contentList.get(i))).keySet(); if (keySet !=
		 * null) { Iterator it = keySet.iterator(); while (it.hasNext()) {
		 * Object itValue = it.next(); if (itValue != null) { cell =
		 * row.createCell((short) k++); cell.setCellValue(((Map)
		 * (contentList.get(i))).get(itValue) == null ? "": ((Map)
		 * (contentList.get(i))).get(itValue).toString()); } } } } } }
		 */
		/*
		 * <td class='td_left'>${item.LOCAL_NAME}(${item.CHINESE_PINYIN})</td>
		 * <td>${item.IDCARD_NO} &nbsp;</td> <td>${item.SEX}</td> <td>
		 * <fmt:formatDate value="${item.DOB}" pattern="yyyy-MM-dd"/> </td>
		 * <td>${item.DEPTNAME}</td> <td>${item.GRADE_LEVEL}</td>
		 * 
		 * <td>${item.DUTY_NO}</td> <td>${item.GRADE_NO}</td>
		 * <td>${item.POST_NO}</td> <td> <fmt:formatDate
		 * value="${item.JOIN_COMPANY_DATE}" pattern="yyyy-MM-dd"/> </td>
		 * <td>${item.IN_THE}</td> <td> <fmt:formatDate
		 * value="${item.NOW_DEPARTMENT_DATE}" pattern="yyyy-MM-dd"/></td> <td>
		 * <fmt:formatDate value="${item.PROMOTION_DATE}" pattern="yyyy-MM-dd"/>
		 * </td> <td>${item.DATE_LEFT}</td> <td>${item.WORK_YEAR}</td>
		 * <td>${item.EMPLOYMENTTYPE}</td> <td>${item.WORKTIMETYPE}</td>
		 * <td>${item.CONTRACTTYPE}</td> <td>${item.TYPECODE}</td>
		 */
		String[] nameTitle = { "EMPID", "LOCAL_NAME", "CHINESE_PINYIN",
				"IDCARD_NO", "SEX", "DOB", "DEPTNAME", "GRADE_LEVEL",
				"DUTY_NO", "GRADE_NO", "POST_NO", "JOIN_COMPANY_DATE",
				"IN_THE", "NOW_DEPARTMENT_DATE", "PROMOTION_DATE", "DATE_LEFT",
				"WORK_YEAR", "WORK_MONTH", "EMPLOYMENTTYPE", "WORKTIMETYPE",
				"CONTRACTTYPE", "TYPECODE" };
		for (int i = 0; i < aliasList.size(); i++) {
			int k = 0;
			row = sheet1.createRow(i + 1);
			for (int j = 0; j < nameTitle.length; j++) {
				cell = row.createCell((short) k++);

				// cell.setCellValue(((Map)
				// (result.get(i))).get(aliasList.get(j).toString()) == null ?
				// " ": ((Map)
				// (result.get(i))).get(aliasList.get(j).toString()).toString());
				Map map = (Map) aliasList.get(i);
				String title = "";
				if (map.get(nameTitle[j]) != null) {
					if (nameTitle[j].equals("WORK_YEAR")) {
						title = map.get(nameTitle[j]).toString()
								+ TipMessage.getTipMessage(
										"liang.hr.viewWorkInfo.title.YEAR",
										request);
					} else if (nameTitle[j].equals("WORK_MONTH")) {
						title = map.get(nameTitle[j]).toString()
								+ TipMessage
										.getTipMessage(
												"hr.viewPersonalInfo.title.WORKINFO_MONTH",
												request);
					} else {
						title = map.get(nameTitle[j]).toString();
					}
				}
				/*
				 * if(i==1){
				 * title=map.get(nameTitle[1]).toString()+"("+map.get(nameTitle
				 * [2]).toString()+")"; j=2; k=2; }
				 */
				cell.setCellValue(title);
			}
		}
		File f = new File(this.path);
		if (!f.exists()) {
			f.mkdirs();
		}
		FileOutputStream fos = new FileOutputStream(new File(this.path
				+ "\\temp.xlsx"));
		wb.write(fos);

		/*
		 * //删除登录者之前选中的所有项目 LinkedHashMap userMap=new LinkedHashMap();
		 * userMap.put("PERSON_ID", admin.getPersonId() != null ?
		 * admin.getPersonId() : null); userMap.put("USERNAME",
		 * admin.getUsername() != null ? admin.getUsername() : null);
		 * userMap.put("CPNY_ID", admin.getCpnyId() != null ? admin.getCpnyId()
		 * : ""); //"1"表示为工资的计算结果 userMap.put("FUNCTIONFLAG",
		 * request.getParameter("functionFlag")); //计算结果区分标示(1:计算结果1 2:计算结果2)
		 * userMap.put("DISTINGUISH", request.getParameter("distinguish"));
		 * if(this.paResultDao.selectSelectedOptions(userMap) > 0){
		 * this.paResultDao.deleteSelectedOptions(userMap); }
		 * 
		 * //增加保存导出Excel选项功能 List object=new ArrayList(); Map optionsMap=null;
		 * for(int i=0;i<aliasList.size();i++){ optionsMap=new LinkedHashMap();
		 * optionsMap.put("PERSON_ID", admin.getPersonId() != null ?
		 * admin.getPersonId() : ""); optionsMap.put("CPNY_ID",
		 * admin.getCpnyId() != null ? admin.getCpnyId() : "");
		 * optionsMap.put("USERNAME", admin.getUsername() != null ?
		 * admin.getUsername() : ""); optionsMap.put("ITEM_ID",
		 * aliasList.get(i)); optionsMap.put("CREATE_BY", admin.getPersonId() !=
		 * null ? admin.getPersonId() : admin.getUsername());
		 * optionsMap.put("FUNCTIONFLAG", request.getParameter("functionFlag"));
		 * optionsMap.put("DISTINGUISH", request.getParameter("distinguish"));
		 * optionsMap.put("ORDERNO", (i+1)); object.add(optionsMap); }
		 * this.excelUtilDao.insertSelectedOptionsByExprotExcel(object);
		 */

		return this.path + "\\temp.xlsx";
	}

	/**
	 * 历史工资信息导出Excel专用2012-09-10(Lufeng)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings( { "unchecked", "deprecation" })
	public String exportPaHistoryExcel(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap,
			LinkedHashMap sqlContentmap, List aliasNameList, List aliasList)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String adminID = admin.getAdminID() == null ? "USERNO_"
				+ admin.getUserNo() : admin.getAdminID();
		this.type = request.getParameter("type");
		String CpnyId = admin.getCpnyId();
		this.path = request.getRealPath("");

		int init = path.indexOf("\\");
		String server = "win";
		if (init < 0) { // WebServer的os为unix/linux
			server = "u/l";
		}
		if ("win".equals(server)) {
			path = path + "\\resources\\temp\\download\\" + CpnyId + "\\"
					+ adminID + "\\";
		} else {
			path = path + "/resources/temp/download/" + CpnyId + "/" + adminID;
		}
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet1 = wb.createSheet("sheet1");
		XSSFRow row = sheet1.createRow(0);
		XSSFCell cell;

		List result = null;
		row = sheet1.createRow(0);
		for (int i = 0; i < aliasNameList.size(); i++) {
			cell = row.createCell((short) i);
			cell.setCellValue(aliasNameList.get(i).toString());
		}
		if (sqlContentmap != null && sqlContentmap.get("sqlContent") != null
				&& !sqlContentmap.get("sqlContent").equals("")) {
			result = this.getPaHistoryDataList(sqlContentmap);
		}
		File file = new File(this.path + "\\temp.xlsx");
		file.delete();

		if (result != null && result.size() > 0) {// 导出数据
			for (int i = 0; i < result.size(); i++) {
				int k = 0;
				row = sheet1.createRow(i + 1);
				for (int j = 0; j < aliasList.size(); j++) {
					cell = row.createCell((short) k++);
					String str = StringUtil.checkNull(aliasList.get(j));
					SimpleDateFormat simpleFormate = new SimpleDateFormat(
							"yyyy-MM-dd");

					if (str.equals("IDCARD_NO")) {
						String str1 = StringUtil.checkNull(((Map) (result
								.get(i))).get(str));
						cell.setCellValue(str1);
					} else {
						try {
							Double str1 = Double
									.parseDouble(StringUtil
											.checkNull(((Map) (result.get(i)))
													.get(str)));
							cell.setCellValue(str1);
						} catch (Exception e) {
							try {
								String str1 = simpleFormate
										.format(simpleFormate.parse(StringUtil
												.checkNull(((Map) (result
														.get(i))).get(str))));
								cell.setCellValue(str1);
							} catch (Exception e1) {
								String str1 = StringUtil
										.checkNull(((Map) (result.get(i)))
												.get(str));
								cell.setCellValue(str1);
							}

						}
					}
					// cell.setCellValue(((Map)
					// (result.get(i))).get(aliasList.get(j).toString()) == null
					// ? "" :
					// ((Map)
					// (result.get(i))).get(aliasList.get(j).toString()).toString());
				}
			}
		}
		File f = new File(this.path);
		if (!f.exists()) {
			f.mkdirs();
		}
		FileOutputStream fos = new FileOutputStream(new File(this.path
				+ "\\temp.xlsx"));
		wb.write(fos);

		// 删除登录者之前选中的所有项目
		LinkedHashMap userMap = new LinkedHashMap();
		userMap.put("PERSON_ID", admin.getPersonId() != null ? admin
				.getPersonId() : null);
		userMap.put("USERNAME", admin.getUsername() != null ? admin
				.getUsername() : null);
		userMap.put("CPNY_ID", admin.getCpnyId() != null ? admin.getCpnyId()
				: "");
		// "4"表示为部门工资
		userMap.put("FUNCTIONFLAG", request.getParameter("functionFlag"));
		// 计算结果区分标示(1:计算结果1 2:计算结果2)
		userMap.put("DISTINGUISH", request.getParameter("distinguish"));
		if (this.paResultDao.selectSelectedOptions(userMap) > 0) {
			this.paResultDao.deleteSelectedOptions(userMap);
		}

		// 增加保存导出Excel选项功能
		List object = new ArrayList();
		Map optionsMap = null;
		for (int i = 0; i < aliasList.size(); i++) {
			optionsMap = new LinkedHashMap();
			optionsMap.put("PERSON_ID", admin.getPersonId() != null ? admin
					.getPersonId() : "");
			optionsMap.put("CPNY_ID", admin.getCpnyId() != null ? admin
					.getCpnyId() : "");
			optionsMap.put("USERNAME", admin.getUsername() != null ? admin
					.getUsername() : "");
			optionsMap.put("ITEM_ID", aliasList.get(i));
			optionsMap.put("CREATE_BY", admin.getPersonId() != null ? admin
					.getPersonId() : admin.getUsername());
			optionsMap
					.put("FUNCTIONFLAG", request.getParameter("functionFlag"));
			optionsMap.put("DISTINGUISH", request.getParameter("distinguish"));
			optionsMap.put("ORDERNO", (i + 1));
			object.add(optionsMap);
		}
		this.excelUtilDao.insertSelectedOptionsByExprotExcel(object);

		return this.path + "\\temp.xlsx";
	}

	/**
	 * 历史工资信息导出Excel专用2012-09-10(Lufeng)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings( { "unchecked", "deprecation" })
	public String exportPaHistoryExcel(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap,
			LinkedHashMap sqlContentmap, List aliasNameList, List aliasList,
			List aliasListExpType) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String adminID = admin.getAdminID() == null ? "USERNO_"
				+ admin.getUserNo() : admin.getAdminID();
		this.type = request.getParameter("type");
		String CpnyId = admin.getCpnyId();
		this.path = request.getRealPath("");

		int init = path.indexOf("\\");
		String server = "win";
		if (init < 0) { // WebServer的os为unix/linux
			server = "u/l";
		}
		if ("win".equals(server)) {
			path = path + "\\resources\\temp\\download\\" + CpnyId + "\\"
					+ adminID + "\\";
		} else {
			path = path + "/resources/temp/download/" + CpnyId + "/" + adminID;
		}
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet1 = wb.createSheet("sheet1");
		XSSFRow row = sheet1.createRow(0);
		XSSFCell cell;

		List result = null;
		row = sheet1.createRow(0);
		for (int i = 0; i < aliasNameList.size(); i++) {
			cell = row.createCell((short) i);
			cell.setCellValue(aliasNameList.get(i).toString());
		}
		if (sqlContentmap != null && sqlContentmap.get("sqlContent") != null
				&& !sqlContentmap.get("sqlContent").equals("")) {
			result = this.getPaHistoryDataList(sqlContentmap);
		}
		File file = new File(this.path + "\\temp.xlsx");
		file.delete();

		if (result != null && result.size() > 0) {// 导出数据
			for (int i = 0; i < result.size(); i++) {
				int k = 0;
				row = sheet1.createRow(i + 1);
				for (int j = 0; j < aliasList.size(); j++) {
					cell = row.createCell((short) k++);
					String str = StringUtil.checkNull(aliasList.get(j));
					String expFlag = StringUtil.checkNull(aliasListExpType
							.get(j));
					SimpleDateFormat simpleFormate = new SimpleDateFormat(
							"yyyy-MM-dd");

					if (str.equals("IDCARD_NO")) {
						String str1 = StringUtil.checkNull(((Map) (result
								.get(i))).get(str));
						cell.setCellValue(str1);
					} else {
						try {
							Double str1 = Double
									.parseDouble(StringUtil
											.checkNull(((Map) (result.get(i)))
													.get(str)));
							if (expFlag.equals("1")) {
								DecimalFormat df = new DecimalFormat("#.##");
								String db = df.format(str1);
								str1 = Double.parseDouble(db);
								cell.setCellValue(str1);
								XSSFCellStyle cellStyle = wb.createCellStyle();
								cellStyle.setDataFormat((short) BuiltinFormats
										.getBuiltinFormat("0.00"));
								cell.setCellStyle(cellStyle);
							} else {
								cell.setCellValue(str1);
							}
						} catch (Exception e) {
							try {
								String str1 = simpleFormate
										.format(simpleFormate.parse(StringUtil
												.checkNull(((Map) (result
														.get(i))).get(str))));
								cell.setCellValue(str1);
							} catch (Exception e1) {
								String str1 = StringUtil
										.checkNull(((Map) (result.get(i)))
												.get(str));
								cell.setCellValue(str1);
							}

						}
					}
					// cell.setCellValue(((Map)
					// (result.get(i))).get(aliasList.get(j).toString()) == null
					// ? "" :
					// ((Map)
					// (result.get(i))).get(aliasList.get(j).toString()).toString());
				}
			}
		}
		File f = new File(this.path);
		if (!f.exists()) {
			f.mkdirs();
		}
		FileOutputStream fos = new FileOutputStream(new File(this.path
				+ "\\temp.xlsx"));
		wb.write(fos);

		// 删除登录者之前选中的所有项目
		LinkedHashMap userMap = new LinkedHashMap();
		userMap.put("PERSON_ID", admin.getPersonId() != null ? admin
				.getPersonId() : null);
		userMap.put("USERNAME", admin.getUsername() != null ? admin
				.getUsername() : null);
		userMap.put("CPNY_ID", admin.getCpnyId() != null ? admin.getCpnyId()
				: "");
		// "4"表示为部门工资
		userMap.put("FUNCTIONFLAG", request.getParameter("functionFlag"));
		// 计算结果区分标示(1:计算结果1 2:计算结果2)
		userMap.put("DISTINGUISH", request.getParameter("distinguish"));
		if (this.paResultDao.selectSelectedOptions(userMap) > 0) {
			this.paResultDao.deleteSelectedOptions(userMap);
		}

		// 增加保存导出Excel选项功能
		List object = new ArrayList();
		Map optionsMap = null;
		for (int i = 0; i < aliasList.size(); i++) {
			optionsMap = new LinkedHashMap();
			optionsMap.put("PERSON_ID", admin.getPersonId() != null ? admin
					.getPersonId() : "");
			optionsMap.put("CPNY_ID", admin.getCpnyId() != null ? admin
					.getCpnyId() : "");
			optionsMap.put("USERNAME", admin.getUsername() != null ? admin
					.getUsername() : "");
			optionsMap.put("ITEM_ID", aliasList.get(i));
			optionsMap.put("CREATE_BY", admin.getPersonId() != null ? admin
					.getPersonId() : admin.getUsername());
			optionsMap
					.put("FUNCTIONFLAG", request.getParameter("functionFlag"));
			optionsMap.put("DISTINGUISH", request.getParameter("distinguish"));
			optionsMap.put("ORDERNO", (i + 1));
			object.add(optionsMap);
		}
		this.excelUtilDao.insertSelectedOptionsByExprotExcel(object);

		return this.path + "\\temp.xlsx";
	}

	/**
	 * 历史工资信息导出专用2012-09-10（lufeng）
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	public void downloadPaHistory(HttpServletRequest request,
			HttpServletResponse response, String pathstr) throws Exception {
		response.setContentType(CONTENT_TYPE);
		File file = new File(pathstr);
		response.setContentType("application/x-msdownload");
		response.setContentLength((int) file.length());
		response.setHeader("Content-Disposition", "attachment;filename="
				+ "paHistory.xls");
		FileInputStream fis = new FileInputStream(file);
		BufferedInputStream buff = new BufferedInputStream(fis);

		byte[] b = new byte[2048];
		long k = 0;
		OutputStream myout = response.getOutputStream();
		while (k < file.length()) {
			int j = buff.read(b, 0, 2048);
			k += j;
			myout.write(b, 0, j);
		}
		myout.flush();
	}

	/**
	 * 人员任情表信息导出Excel专用2013-01-23(Lufeng)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings( { "unchecked", "deprecation" })
	public String exportPaRiseInfoExcel(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap,
			List<LinkedHashMap> empPaRiseList) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String adminID = admin.getAdminID() == null ? "USERNO_"
				+ admin.getUserNo() : admin.getAdminID();
		this.type = request.getParameter("type");
		String CpnyId = admin.getCpnyId();
		this.path = request.getRealPath("");

		int init = path.indexOf("\\");
		String server = "win";
		if (init < 0) { // WebServer的os为unix/linux
			server = "u/l";
		}
		if ("win".equals(server)) {
			path = path + "\\resources\\temp\\download\\" + CpnyId + "\\"
					+ adminID + "\\";
		} else {
			path = path + "/resources/temp/download/" + CpnyId + "/" + adminID;
		}
		// 构建报表结构
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet("paRiseInfo");
		XSSFCellStyle style = wb.createCellStyle(); // 样式对象

		style.setVerticalAlignment(VerticalAlignment.CENTER);// 垂直
		style.setAlignment(HorizontalAlignment.CENTER);// 水平
		XSSFRow row = sheet.createRow((short) 0);
		XSSFRow row2 = sheet.createRow((short) 1);
		// 照片后的“”不可删除
		String[] columns = { "No.", "现职地", "社保地", "入职地", "照片", "", "工号", "姓名",
				"部门", "职级", "性别", "出生年月", "年龄", "入职日期", "最高学历", "合同到期日",
				"学校名称", "专业", "月工资 ", "联系方式" };
		for (int i = 0; i < 4; i++) { // 循环18次，每一次都要跨单元格显示
			String colName = columns[i] != null ? columns[i] : "";
			sheet.addMergedRegion(region(0, (short) i, 1, (short) i));
			XSSFCell ce = row.createCell((short) i);
			ce.setCellValue(colName); // 表格的第一行第一列显示的名称
			ce.setCellStyle(style); // 样式，居中
		}
		// 照片
		sheet.addMergedRegion(region(0, (short) 4, 1, (short) 5));
		XSSFCell cePhoto = row.createCell((short) 4);
		cePhoto.setCellValue("照片");
		cePhoto.setCellStyle(style);

		for (int i = 6; i < 20; i++) { // 循环18次，每一次都要跨单元格显示
			String colName = columns[i] != null ? columns[i] : "";
			sheet.addMergedRegion(region(0, (short) i, 1, (short) i));
			XSSFCell ce = row.createCell((short) i);
			ce.setCellValue(colName); // 表格的第一行第一列显示的名称
			ce.setCellStyle(style); // 样式，居中
		}
		// 社外经历单元格合并
		// 四个参数分别是：起始行，起始列，结束行，结束列
		sheet.addMergedRegion(region(0, (short) 20, 0, (short) 22));
		XSSFCell cellOut = row.createCell((short) 20);
		cellOut.setCellValue("社外经历"); // 跨单元格显示的数据
		cellOut.setCellStyle(style); // 样式
		// 社外经历： 时间 -单位-职务-薪资
		XSSFCell cellOut1 = row2.createCell((short) 20);
		XSSFCell cellOut2 = row2.createCell((short) 21);
		XSSFCell cellOut3 = row2.createCell((short) 22);
		cellOut1.setCellValue("时间");
		cellOut1.setCellStyle(style);
		cellOut2.setCellValue("单位");
		cellOut2.setCellStyle(style);
		cellOut3.setCellValue("职务");
		cellOut3.setCellStyle(style);
		// 社内经历单元格合并
		// 四个参数分别是：起始行，起始列，结束行，结束列
		sheet.addMergedRegion(region(0, (short) 23, 0, (short) 26));
		XSSFCell cellIn = row.createCell((short) 23);
		cellIn.setCellValue("社外经历"); // 跨单元格显示的数据
		cellIn.setCellStyle(style); // 样式
		// 社内经历： 时间 -单位-职务-薪资
		XSSFCell cellIn1 = row2.createCell((short) 23);
		XSSFCell cellIn2 = row2.createCell((short) 24);
		XSSFCell cellIn3 = row2.createCell((short) 25);
		XSSFCell cellIn4 = row2.createCell((short) 26);
		cellIn1.setCellValue("时间");
		cellIn1.setCellStyle(style);
		cellIn2.setCellValue("单位");
		cellIn2.setCellStyle(style);
		cellIn3.setCellValue("职务");
		cellIn3.setCellStyle(style);
		cellIn4.setCellValue("薪资");
		cellIn4.setCellStyle(style);

		File file = new File(this.path + "\\temp.xlsx");
		file.delete();

		int sizeNum = 0;// 多少条数据
		sizeNum = empPaRiseList != null ? empPaRiseList.size() : 0;

		String PhotoPath = "ftp://10.231.221.36";
		try {
			PhotoPath = config.getString("hrm.photo.read.ftp");
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
		String CPNY_ID = admin.getCpnyId() != null ? admin.getCpnyId()
				.toString() : "C01";
		XSSFDrawing patriarch = sheet.createDrawingPatriarch();
		int num = 0;
		for (int i = 0; i < sizeNum; i++) {
			LinkedHashMap dataMap = (LinkedHashMap) empPaRiseList.get(i);
			int experNum = 0;
			if (dataMap != null && dataMap.get("experNum") != null) {
				experNum = Integer.parseInt(dataMap.get("experNum").toString()) - 1;
				if (experNum < 0) {
					experNum = 0;
				}
			}

			// 开始循环数据
			XSSFRow rowNo = sheet.createRow((short) (2 + i + num));
			// NO
			sheet.addMergedRegion(region(2 + i + num, (short) 0, 2 + i
					+ num + experNum, (short) 0));
			XSSFCell ceNo0 = rowNo.createCell((short) 0);
			ceNo0.setCellValue(i + 1);
			ceNo0.setCellStyle(style);
			// 现职地
			sheet.addMergedRegion(region(2 + i + num, (short) 1, 2 + i
					+ num + experNum, (short) 1));
			XSSFCell ceNo1 = rowNo.createCell((short) 1);
			ceNo1
					.setCellValue(dataMap.get("DEPT_DISTINGUISH_NAME") != null ? dataMap
							.get("DEPT_DISTINGUISH_NAME").toString()
							: "");
			ceNo1.setCellStyle(style);
			// 社保地
			sheet.addMergedRegion(region(2 + i + num, (short) 2, 2 + i
					+ num + experNum, (short) 2));
			XSSFCell ceNo2 = rowNo.createCell((short) 2);
			ceNo2
					.setCellValue(dataMap.get("SOCIAL_SECURITY_AREA") != null ? dataMap
							.get("SOCIAL_SECURITY_AREA").toString()
							: "");
			ceNo2.setCellStyle(style);
			// 入职地
			sheet.addMergedRegion(region(2 + i + num, (short) 3, 2 + i
					+ num + experNum, (short) 3));
			XSSFCell ceNo3 = rowNo.createCell((short) 3);
			ceNo3.setCellValue(dataMap.get("ENTRY_AREA") != null ? dataMap.get(
					"ENTRY_AREA").toString() : "");
			ceNo3.setCellStyle(style);

			// 照片路径
			String EMPID = dataMap != null && dataMap.get("EMPID") != null ? dataMap
					.get("EMPID").toString()
					: "";
			String empPhoth = PhotoPath + "/" + CPNY_ID + "/" + CPNY_ID + EMPID
					+ ".jpg";
			// 照片
			BufferedInputStream bis = new BufferedInputStream(
					new FileInputStream(empPhoth));
			byte[] buf = new byte[bis.available()];
			while ((bis.read(buf)) != -1) {
				//
			}
			// 有图片时，设置行高为60px;
			rowNo.setHeightInPoints(60);
			// 设置图片所在列宽度为80px,注意这里单位的一个换算
			sheet.setColumnWidth(2 + i + num, (short) (35.7 * 65));
			// sheet.autoSizeColumn(i);
			byte[] bsValue = (byte[]) buf;
			XSSFClientAnchor anchor = new XSSFClientAnchor(0, 0, 455, 255,
					(short) 4, 2 + i + num, (short) 5, 2 + i + num + experNum);
			anchor.setAnchorType(org.apache.poi.ss.usermodel.ClientAnchor.AnchorType.byId(2));
			patriarch.createPicture(anchor, wb.addPicture(bsValue,
					XSSFWorkbook.PICTURE_TYPE_JPEG));

			// 工号
			sheet.addMergedRegion(region(2 + i + num, (short) 6, 2 + i
					+ num + experNum, (short) 6));
			XSSFCell ceNo5 = rowNo.createCell((short) 6);
			ceNo5.setCellValue(dataMap.get("EMPID") != null ? dataMap.get(
					"EMPID").toString() : "");
			ceNo5.setCellStyle(style);
			// 姓名
			sheet.addMergedRegion(region(2 + i + num, (short) 7, 2 + i
					+ num + experNum, (short) 7));
			XSSFCell ceNo6 = rowNo.createCell((short) 7);
			ceNo6.setCellValue(dataMap.get("LOCAL_NAME") != null ? dataMap.get(
					"LOCAL_NAME").toString() : "");
			ceNo6.setCellStyle(style);
			// 部门
			sheet.addMergedRegion(region(2 + i + num, (short) 8, 2 + i
					+ num + experNum, (short) 8));
			XSSFCell ceNo7 = rowNo.createCell((short) 8);
			ceNo7.setCellValue(dataMap.get("DEPARTMENT") != null ? dataMap.get(
					"DEPARTMENT").toString() : "");
			ceNo7.setCellStyle(style);
			// 职级
			sheet.addMergedRegion(region(2 + i + num, (short) 9, 2 + i
					+ num + experNum, (short) 9));
			XSSFCell ceNo8 = rowNo.createCell((short) 9);
			ceNo8.setCellValue(dataMap.get("POST_GRADE_NAME") != null ? dataMap
					.get("POST_GRADE_NAME").toString() : "");
			ceNo8.setCellStyle(style);
			// 性别
			sheet.addMergedRegion(region(2 + i + num, (short) 10, 2 + i
					+ num + experNum, (short) 10));
			XSSFCell ceNo9 = rowNo.createCell((short) 10);
			ceNo9.setCellValue(dataMap.get("SEX") != null ? dataMap.get("SEX")
					.toString() : "");
			ceNo9.setCellStyle(style);
			// 出生年月
			sheet.addMergedRegion(region(2 + i + num, (short) 11, 2 + i
					+ num + experNum, (short) 11));
			XSSFCell ceNo10 = rowNo.createCell((short) 11);
			ceNo10.setCellValue(dataMap.get("DOB") != null ? dataMap.get("DOB")
					.toString() : "");
			ceNo10.setCellStyle(style);
			// 年龄
			sheet.addMergedRegion(region(2 + i + num, (short) 12, 2 + i
					+ num + experNum, (short) 12));
			XSSFCell ceNo11 = rowNo.createCell((short) 12);
			ceNo11.setCellValue(dataMap.get("AGE") != null ? dataMap.get("AGE")
					.toString() : "");
			ceNo11.setCellStyle(style);
			// 入职日期
			sheet.addMergedRegion(region(2 + i + num, (short) 13, 2 + i
					+ num + experNum, (short) 13));
			XSSFCell ceNo12 = rowNo.createCell((short) 13);
			ceNo12.setCellValue(dataMap.get("DATE_STARTED") != null ? dataMap
					.get("DATE_STARTED").toString() : "");
			ceNo12.setCellStyle(style);
			// 最高学历
			sheet.addMergedRegion(region(2 + i + num, (short) 14, 2 + i
					+ num + experNum, (short) 14));
			XSSFCell ceNo13 = rowNo.createCell((short) 14);
			ceNo13
					.setCellValue(dataMap.get("FINAL_DEGREE_NAME") != null ? dataMap
							.get("FINAL_DEGREE_NAME").toString()
							: "");
			ceNo13.setCellStyle(style);
			// 合同到期日
			sheet.addMergedRegion(region(2 + i + num, (short) 15, 2 + i
					+ num + experNum, (short) 15));
			XSSFCell ceNo14 = rowNo.createCell((short) 15);
			ceNo14
					.setCellValue(dataMap.get("END_CONTRACT_DATE") != null ? dataMap
							.get("END_CONTRACT_DATE").toString()
							: "");
			ceNo14.setCellStyle(style);
			// 学校名称
			sheet.addMergedRegion(region(2 + i + num, (short) 16, 2 + i
					+ num + experNum, (short) 16));
			XSSFCell ceNo15 = rowNo.createCell((short) 16);
			ceNo15.setCellValue(dataMap.get("FINAL_SCHOOL") != null ? dataMap
					.get("FINAL_SCHOOL").toString() : "");
			ceNo15.setCellStyle(style);
			// 专业
			sheet.addMergedRegion(region(2 + i + num, (short) 17, 2 + i
					+ num + experNum, (short) 17));
			XSSFCell ceNo16 = rowNo.createCell((short) 17);
			ceNo16.setCellValue(dataMap.get("FINAL_SUBJECT") != null ? dataMap
					.get("FINAL_SUBJECT").toString() : "");
			ceNo16.setCellStyle(style);
			// 月工资
			sheet.addMergedRegion(region(2 + i + num, (short) 18, 2 + i
					+ num + experNum, (short) 18));
			XSSFCell ceNo17 = rowNo.createCell((short) 18);
			ceNo17.setCellValue(dataMap.get("PA_BASIC_DATA") != null ? dataMap
					.get("PA_BASIC_DATA").toString() : "");
			ceNo17.setCellStyle(style);
			// 联系方式
			sheet.addMergedRegion(region(2 + i + num, (short) 19, 2 + i
					+ num + experNum, (short) 19));
			XSSFCell ceNo18 = rowNo.createCell((short) 19);
			ceNo18.setCellValue(dataMap.get("EMPID") != null ? dataMap.get(
					"EMPID").toString() : "");
			ceNo18.setCellStyle(style);
			// 社外工作经历
			// 时间
			sheet.addMergedRegion(region(2 + i + num, (short) 20, 2 + i
					+ num, (short) 20));
			XSSFCell ceNo19 = rowNo.createCell((short) 20);
			String outDate = "";
			String outStartDate = dataMap.get("OUT_START_DATE") != null ? dataMap
					.get("OUT_START_DATE").toString()
					: "";
			String outEndDate = dataMap.get("OUT_END_DATE") != null ? dataMap
					.get("OUT_END_DATE").toString() : "";
			if (!"".equals(outEndDate)) {
				outDate = outStartDate + " ~ " + outEndDate;
			} else {
				outDate = outStartDate;
			}
			ceNo19.setCellValue(outDate);
			ceNo19.setCellStyle(style);
			// 单位
			sheet.addMergedRegion(region(2 + i + num, (short) 21, 2 + i
					+ num, (short) 21));
			XSSFCell ceNo20 = rowNo.createCell((short) 21);
			ceNo20.setCellValue(dataMap.get("OUT_CPNY_NAME") != null ? dataMap
					.get("OUT_CPNY_NAME").toString() : "");
			ceNo20.setCellStyle(style);
			// 职务
			sheet.addMergedRegion(region(2 + i + num, (short) 22, 2 + i
					+ num, (short) 22));
			XSSFCell ceNo21 = rowNo.createCell((short) 22);
			ceNo21.setCellValue(dataMap.get("OUT_POST_NAME") != null ? dataMap
					.get("OUT_POST_NAME").toString() : "");
			ceNo21.setCellStyle(style);
			// 社内工作经历
			// 时间
			sheet.addMergedRegion(region(2 + i + num, (short) 23, 2 + i
					+ num, (short) 23));
			XSSFCell ceNo22 = rowNo.createCell((short) 23);
			String inDate = "";
			String inStartDate = dataMap.get("IN_START_DATE") != null ? dataMap
					.get("IN_START_DATE").toString() : "";
			String inEndDate = dataMap.get("IN_END_DATE") != null ? dataMap
					.get("IN_END_DATE").toString() : "";
			if (!"".equals(inEndDate)) {
				inDate = inStartDate + " ~ " + inEndDate;
			} else {
				inDate = inStartDate;
			}
			ceNo22.setCellValue(inDate);
			ceNo22.setCellStyle(style);
			// 单位
			sheet.addMergedRegion(region(2 + i + num, (short) 24, 2 + i
					+ num, (short) 24));
			XSSFCell ceNo23 = rowNo.createCell((short) 24);
			String cpnyName = "";
			String inCpan = dataMap.get("IN_CPNY_NAME") != null ? dataMap.get(
					"IN_CPNY_NAME").toString() : "";
			String inDept = dataMap.get("IN_DEPT_NAME") != null ? dataMap.get(
					"IN_DEPT_NAME").toString() : "";
			if (!"".equals(inDept)) {
				cpnyName = inCpan + " ~ " + inDept;
			} else {
				cpnyName = inCpan;
			}
			ceNo23.setCellValue(cpnyName);
			ceNo23.setCellStyle(style);
			// 职务
			sheet.addMergedRegion(region(2 + i + num, (short) 25, 2 + i
					+ num, (short) 25));
			XSSFCell ceNo24 = rowNo.createCell((short) 25);
			ceNo24.setCellValue(dataMap.get("IN_POST_NAME") != null ? dataMap
					.get("IN_POST_NAME").toString() : "");
			ceNo24.setCellStyle(style);
			// 薪资
			sheet.addMergedRegion(region(2 + i + num, (short) 26, 2 + i
					+ num, (short) 26));
			XSSFCell ceNo25 = rowNo.createCell((short) 26);
			ceNo25
					.setCellValue(dataMap.get("IN_PA_BASIC_DATA") != null ? dataMap
							.get("IN_PA_BASIC_DATA").toString()
							: "");
			ceNo25.setCellStyle(style);
			// 如果一个人有多条经历的时候
			List<LinkedHashMap> experienceList = new ArrayList<LinkedHashMap>();
			int expCount = 0;
			if (dataMap.get("experienceList") != null) {
				experienceList = (List<LinkedHashMap>) dataMap
						.get("experienceList");
				expCount = experienceList.size() > 0 ? experienceList.size()
						: 0;
			}
			if (expCount > 0) {
				for (int j = 0; j < expCount; j++) {
					XSSFRow rowNoExp = sheet
							.createRow((short) (2 + i + num + 1 + j));
					LinkedHashMap experMap = new LinkedHashMap();
					if (experienceList != null && experienceList.get(j) != null) {
						experMap = (LinkedHashMap) experienceList.get(j);
						// 社外工作经历
						// 时间
						sheet.addMergedRegion(region(2 + i + num + 1 + j,
								(short) 20, 2 + i + num + 1 + j, (short) 20));
						XSSFCell ceNos19 = rowNoExp.createCell((short) 20);
						String outDates = "";
						String outStartDates = experMap.get("OUT_START_DATE") != null ? experMap
								.get("OUT_START_DATE").toString()
								: "";
						String outEndDates = experMap.get("OUT_END_DATE") != null ? experMap
								.get("OUT_END_DATE").toString()
								: "";
						if (!"".equals(outEndDates)) {
							outDates = outStartDates + " ~ " + outEndDates;
						} else {
							outDates = outStartDates;
						}
						ceNos19.setCellValue(outDates);
						ceNos19.setCellStyle(style);
						// 单位
						sheet.addMergedRegion(region(2 + i + num + 1 + j,
								(short) 21, 2 + i + num + 1 + j, (short) 21));
						XSSFCell ceNos20 = rowNoExp.createCell((short) 21);
						ceNos20
								.setCellValue(experMap.get("OUT_CPNY_NAME") != null ? experMap
										.get("OUT_CPNY_NAME").toString()
										: "");
						ceNos20.setCellStyle(style);
						// 职务
						sheet.addMergedRegion(region(2 + i + num + 1 + j,
								(short) 22, 2 + i + num + 1 + j, (short) 22));
						XSSFCell ceNos21 = rowNoExp.createCell((short) 22);
						ceNos21
								.setCellValue(experMap.get("OUT_POST_NAME") != null ? experMap
										.get("OUT_POST_NAME").toString()
										: "");
						ceNos21.setCellStyle(style);
						// 社内工作经历
						// 时间
						sheet.addMergedRegion(region(2 + i + num + 1 + j,
								(short) 23, 2 + i + num + 1 + j, (short) 23));
						XSSFCell ceNos22 = rowNoExp.createCell((short) 23);
						String inDates = "";
						String inStartDates = experMap.get("IN_START_DATE") != null ? experMap
								.get("IN_START_DATE").toString()
								: "";
						String inEndDates = experMap.get("IN_END_DATE") != null ? experMap
								.get("IN_END_DATE").toString()
								: "";
						if (!"".equals(inEndDates)) {
							inDates = inStartDates + " ~ " + inEndDates;
						} else {
							inDates = inStartDates;
						}
						ceNos22.setCellValue(inDates);
						ceNos22.setCellStyle(style);
						// 单位
						sheet.addMergedRegion(region(2 + i + num + 1 + j,
								(short) 24, 2 + i + num + 1 + j, (short) 24));
						XSSFCell ceNos23 = rowNoExp.createCell((short) 24);
						String cpnyNames = "";
						String inCpans = experMap.get("IN_CPNY_NAME") != null ? experMap
								.get("IN_CPNY_NAME").toString()
								: "";
						String inDepts = experMap.get("IN_DEPT_NAME") != null ? experMap
								.get("IN_DEPT_NAME").toString()
								: "";
						if (!"".equals(inDepts)) {
							cpnyNames = inCpans + " ~ " + inDepts;
						} else {
							cpnyNames = inCpans;
						}
						ceNos23.setCellValue(cpnyNames);
						ceNos23.setCellStyle(style);
						// 职务
						sheet.addMergedRegion(region(2 + i + num + 1 + j,
								(short) 25, 2 + i + num + 1 + j, (short) 25));
						XSSFCell ceNos24 = rowNoExp.createCell((short) 25);
						ceNos24
								.setCellValue(experMap.get("IN_POST_NAME") != null ? experMap
										.get("IN_POST_NAME").toString()
										: "");
						ceNos24.setCellStyle(style);
						// 薪资
						sheet.addMergedRegion(region(2 + i + num + 1 + j,
								(short) 26, 2 + i + num + 1 + j, (short) 26));
						XSSFCell ceNos25 = rowNoExp.createCell((short) 26);
						ceNos25
								.setCellValue(experMap.get("IN_PA_BASIC_DATA") != null ? experMap
										.get("IN_PA_BASIC_DATA").toString()
										: "");
						ceNos25.setCellStyle(style);
					}
				}
			}
			num = num + experNum;
		}

		File f = new File(this.path);
		if (!f.exists()) {
			f.mkdirs();
		}
		FileOutputStream fos = new FileOutputStream(new File(this.path
				+ "\\temp.xlsx"));
		wb.write(fos);
		return this.path + "\\temp.xlsx";
	}

	/**
	 * 人员任情表信息导出Excel专用2013-01-23(Lufeng)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	public void downloadPaRiseInfo(HttpServletRequest request,
			HttpServletResponse response, String pathstr) throws Exception {
		response.setContentType(CONTENT_TYPE);
		File file = new File(pathstr);
		response.setContentType("application/x-msdownload");
		response.setContentLength((int) file.length());
		response.setHeader("Content-Disposition", "attachment;filename="
				+ "paRiseInfo.xls");
		FileInputStream fis = new FileInputStream(file);
		BufferedInputStream buff = new BufferedInputStream(fis);

		byte[] b = new byte[2048];
		long k = 0;
		OutputStream myout = response.getOutputStream();
		while (k < file.length()) {
			int j = buff.read(b, 0, 2048);
			k += j;
			myout.write(b, 0, j);
		}
		myout.flush();
	}

	/**
	 * 导出数据到excel的步骤一分为二，二，先导出到excel Description:export datas to excel
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	public void download(HttpServletRequest request,
			HttpServletResponse response, String pathstr) throws Exception {
		response.setContentType(CONTENT_TYPE);
		File file = new File(pathstr);
		response.setContentType("application/x-msdownload");
		response.setContentLength((int) file.length());
		response.setHeader("Content-Disposition", "attachment;filename="
				+ "temp.xlsx");
		FileInputStream fis = new FileInputStream(file);
		BufferedInputStream buff = new BufferedInputStream(fis);

		byte[] b = new byte[20480];
		long k = 0;
		OutputStream myout = response.getOutputStream();
		while (k < file.length()) {
			int j = buff.read(b, 0, 20480);
			k += j;
			myout.write(b, 0, j);
		}
		myout.flush();
	}

	@SuppressWarnings( { "unchecked" })
	public int impArShiftDataBase(String adminID, LinkedHashMap cellmap,
			HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		int token = 0;
		int p = path.indexOf("\\");
		String server = "win";
		if (p < 0) { // WebServer的os为unix/linux
			server = "u/l";
		}
		File file = null;
		if ("win".equals(server)) {
			file = new File(path + "\\" + filename + ".xls");
		} else {
			file = new File(path + "/" + filename + ".xls");
		}
		Map dataMap = new HashMap();
		String tableName = cellmap.get("tableName") == null ? "" : cellmap.get(
				"tableName").toString();// 获取表名
		try {
			Workbook workBook = Workbook.getWorkbook(file);
			// 获得第一个工作表对象
			Sheet sheet = workBook.getSheet(0);
			// 验证是否存在导入数据
			if (sheet.getRows() < 2) {
				logger.debug("Import file not exist data, return.");
				return 0;
			}
			List summaryList = new ArrayList();
			List addList = new ArrayList();
			int init = sheet.getRow(1).length;
			Cell[] rowIndex0 = sheet.getRow(0);
			int columnLength = rowIndex0.length;
			for (int n = 1; n < sheet.getRows(); n++) {
				Cell[] rowIndex1 = sheet.getRow(n);

				LinkedHashMap aliasValueMap = cellmap.get("aliasValueMap") == null ? null
						: (LinkedHashMap) cellmap.get("aliasValueMap");
				LinkedHashMap aliasTypeMap = cellmap.get("aliasTypeMap") == null ? null
						: (LinkedHashMap) cellmap.get("aliasTypeMap");
				LinkedHashMap aliasValueI18nMap = cellmap
						.get("aliasValueI18nMap") == null ? null
						: (LinkedHashMap) cellmap.get("aliasValueI18nMap");

				LinkedHashMap aliasMapCells = new LinkedHashMap();
				if (aliasValueMap != null && aliasTypeMap != null) {// 存放CELL0：NUMBER类型的键值对
					Set keySet = aliasValueMap.keySet();
					if (keySet != null) {
						Iterator it = keySet.iterator();
						while (it.hasNext()) {
							Object keyvalue = it.next();
							if (keyvalue != null) {
								Object aliasValue = aliasValueMap.get(keyvalue);
								if (aliasValue != null) {
									aliasMapCells.put(aliasValue.toString(),
											aliasTypeMap.get(keyvalue));
								}
							}
						}
					}
				}
				// 循环检查EXCEL中数据
				for (int j = 3; j < columnLength; j++) {
					String cellValue = "";
					// 判断是否有标题行有空格
					if (rowIndex0[j].getContents().toString().trim() == "")
						return 21;
					String cellShiftValue = rowIndex1[j].getContents();
					// 判断班次是否有输入空值的情况
					// if (cellShiftValue == null||cellShiftValue=="")
					// break;
					if (rowIndex0[j].getContents() != null) {// date类型
						try {
							cellValue = this.FormateData(rowIndex0[j],
									(Object) 1);
							if (cellValue.contains("/")) {
								String[] contentStr = cellValue.split("/");
								if (contentStr.length == 3
										&& contentStr[0].length() == 2
										&& contentStr[2].length() == 4) {
									cellValue = contentStr[2] + "/"
											+ contentStr[1] + "/"
											+ contentStr[0];
								}
							} else if (cellValue.contains("-")) {
								String[] contentStr = cellValue.split("-");
								if (contentStr.length == 3
										&& contentStr[0].length() == 2
										&& contentStr[2].length() == 4) {
									cellValue = contentStr[2] + "-"
											+ contentStr[1] + "-"
											+ contentStr[0];
								}
							}
						} catch (Exception e) {
							// cellValue=
							// row[j].getContents()==null?"":row[j].getContents();
							cellValue = "";
							if (rowIndex0[j].getContents() != null) {
								cellValue = rowIndex0[j].getContents();
								if (cellValue.contains("/")) {
									String[] contentStr = cellValue.split("/");
									if (contentStr.length == 3
											&& contentStr[0].length() == 2
											&& contentStr[2].length() == 4) {
										cellValue = contentStr[2] + "/"
												+ contentStr[1] + "/"
												+ contentStr[0];
									}
								} else if (cellValue.contains("-")) {
									String[] contentStr = cellValue.split("-");
									if (contentStr.length == 3
											&& contentStr[0].length() == 2
											&& contentStr[2].length() == 4) {
										cellValue = contentStr[2] + "-"
												+ contentStr[1] + "-"
												+ contentStr[0];
									}
								}
							}
						}
					} else {
						cellValue = rowIndex0[j].getContents() == null ? ""
								: rowIndex0[j].getContents();
					}
					// if(rowLength < 3){
					// return 3;//数据列数有问题，请核对
					// }
					// if(("".equals(rowID[0].getContents().trim()) ||
					// rowID[0].getContents() == null)
					// || ("".equals(rowID[3].getContents().trim()) ||
					// rowID[3].getContents() == null)
					// || ("".equals(rowID[4].getContents().trim()) ||
					// rowID[4].getContents() == null)){
					// return 4;//所导入数据中存在为空的情况，请检查核对
					// }

					// 检查数据是否已存在 存在则删除
					LinkedHashMap tempMap = new LinkedHashMap();
					tempMap.put("EMPID", rowIndex1[0].getContents().trim());
					tempMap.put("CPNY_ID", admin.getCpnyId());
					tempMap.put("AR_DATE_STR", cellValue);
					tempMap.put("#CELL" + j + "#", cellShiftValue);
					tempMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());
					tempMap.put("AR_SCHEDULE_TABLE", tableName);
					summaryList.add(tempMap);

					// 查看人员EMPID是否正确
					if (j == 3) {
						if (this.checkPersonalInfo(tempMap) == 0) {
							int rTint = 6;
							try {
								rTint = Integer.parseInt(tempMap.get("EMPID")
										.toString());
							} catch (Exception e) {
								rTint = 6;
							}
							// =6所导入数据中工号存在问题，不能进行数据导入;>10
							// EMPID:工号存在问题(该员工不存在，或者当前考勤员对该员工所在部门无考勤权限)
							return rTint;// 所导入数据中工号存在问题，不能进行数据导入
						}
					}

				}
				if (this.deleteScheduleInfo(summaryList) != 1) {
					return 7;// 删除排班表数据出错,请联系管理员
				}
				// 封装删除排班表数据
				/*
				 * for (int m = 3; m < columnLength; m++) { // String
				 * cellShiftValues = rowIndex1[m].getContents(); //
				 * //判断班次是否有输入空值的情况 // if (cellShiftValues ==
				 * null||cellShiftValues=="") // break; String cellShiftValue =
				 * rowIndex1[m].getContents(); // 格式化日期 String cellValue = "";
				 * if (rowIndex0[m].getContents() != null) {// date类型 try {
				 * cellValue = this.FormateData(rowIndex0[m], (Object) 1); if
				 * (cellValue.contains("/")) { String[] contentStr =
				 * cellValue.split("/"); if (contentStr.length == 3 &&
				 * contentStr[0].length() == 2 && contentStr[2].length() == 4) {
				 * cellValue = contentStr[2] + "/" + contentStr[1] + "/" +
				 * contentStr[0]; } } else if (cellValue.contains("-")) {
				 * String[] contentStr = cellValue.split("-"); if
				 * (contentStr.length == 3 && contentStr[0].length() == 2 &&
				 * contentStr[2].length() == 4) { cellValue = contentStr[2] +
				 * "-" + contentStr[1] + "-" + contentStr[0]; } } } catch
				 * (Exception e) { // cellValue= //
				 * row[j].getContents()==null?"":row[j].getContents(); cellValue
				 * = ""; if (rowIndex0[m].getContents() != null) { cellValue =
				 * rowIndex0[m].getContents(); if (cellValue.contains("/")) {
				 * String[] contentStr = cellValue.split("/"); if
				 * (contentStr.length == 3 && contentStr[0].length() == 2 &&
				 * contentStr[2].length() == 4) { cellValue = contentStr[2] +
				 * "/" + contentStr[1] + "/" + contentStr[0]; } } else if
				 * (cellValue.contains("-")) { String[] contentStr =
				 * cellValue.split("-"); if (contentStr.length == 3 &&
				 * contentStr[0].length() == 2 && contentStr[2].length() == 4) {
				 * cellValue = contentStr[2] + "-" + contentStr[1] + "-" +
				 * contentStr[0]; } } } } } else { cellValue =
				 * rowIndex0[m].getContents() == null ? "" :
				 * rowIndex0[m].getContents(); } // 检查数据是否已存在 存在则删除
				 * LinkedHashMap tempMap = new LinkedHashMap();
				 * tempMap.put("EMPID", rowIndex1[0].getContents().trim());
				 * tempMap.put("CPNY_ID", admin.getCpnyId());
				 * tempMap.put("AR_DATE_STR", cellValue); tempMap.put("#CELL" +
				 * m + "#", cellShiftValue); tempMap.put("AR_SUPERVISIOR_INFO",
				 * admin.getPersonId()); tempMap.put("AR_SCHEDULE_TABLE",
				 * tableName);
				 * 
				 * summaryList.add(tempMap); }
				 */

				// 删除排班表中数据

				// 验证是否存在导入数据
				/*
				 * if (sheet.getRows() < 2) {
				 * logger.debug("Import file not exist data, return."); return
				 * token; } LinkedHashMap aliasValueMap =
				 * cellmap.get("aliasValueMap") == null ? null : (LinkedHashMap)
				 * cellmap.get("aliasValueMap"); LinkedHashMap aliasTypeMap =
				 * cellmap.get("aliasTypeMap") == null ? null : (LinkedHashMap)
				 * cellmap.get("aliasTypeMap"); LinkedHashMap aliasValueI18nMap
				 * = cellmap .get("aliasValueI18nMap") == null ? null :
				 * (LinkedHashMap) cellmap.get("aliasValueI18nMap");
				 * 
				 * LinkedHashMap aliasMapCells = new LinkedHashMap(); if
				 * (aliasValueMap != null && aliasTypeMap != null) {//
				 * 存放CELL0：NUMBER类型的键值对 Set keySet = aliasValueMap.keySet(); if
				 * (keySet != null) { Iterator it = keySet.iterator(); while
				 * (it.hasNext()) { Object keyvalue = it.next(); if (keyvalue !=
				 * null) { Object aliasValue = aliasValueMap.get(keyvalue); if
				 * (aliasValue != null) {
				 * aliasMapCells.put(aliasValue.toString(),
				 * aliasTypeMap.get(keyvalue)); } } } } }
				 */
				// 循环工作表的每行数据
				for (int i = 3; i < columnLength; i++) {
					String cellShiftValues = rowIndex1[i].getContents();
					// 判断班次是否有输入空值的情况
					// if (cellShiftValues == null||cellShiftValues=="")
					// break;
					// for (int i = 1; i < sheet.getRows(); i++) {
					// 验证当前行是否符合规范
					// if (!validateCell(sheet.getRow(i),
					// sheet.getRow(0).length))
					// continue;
					// if (!validateCell(sheet.getRow(i),
					// sheet.getRow(0).length)){//如果所在行全为空，不导入
					// break;
					// }
					// Cell[] row = sheet.getRow(i);
					String cellShiftValue = rowIndex1[i].getContents();
					if (init == 0) {
						// init=row.length;
						init = sheet.getRow(0).length;
					}

					LinkedHashMap smap = new LinkedHashMap();
					LinkedHashMap aliasCellmap = new LinkedHashMap();
					String cellValue = "";
					int j = 0;
					for (j = 0; j < columnLength; j++) {
						try {
							// String
							// cellShiftValueWS=rowIndex1[j].getContents();
							aliasCellmap.put("#CELL" + j + "#", cellValue);

							Object cellType = null;
							cellType = aliasMapCells.get("#CELL" + j + "#");
							Cell[] newRowIndex = null;
							if (j > 2) {
								newRowIndex = rowIndex0;
							} else {
								newRowIndex = rowIndex1;
							}

							if (cellType != null
									&& !newRowIndex[j].getContents().equals("")) {// date类型
								try {
									cellValue = this.FormateData(
											newRowIndex[j], cellType);
									if (cellValue.contains("/")) {
										String[] contentStr = cellValue
												.split("/");
										if (contentStr.length == 3
												&& contentStr[0].length() == 2
												&& contentStr[2].length() == 4) {
											cellValue = contentStr[2] + "/"
													+ contentStr[1] + "/"
													+ contentStr[0];
										}
									} else if (cellValue.contains("-")) {
										String[] contentStr = cellValue
												.split("-");
										if (contentStr.length == 3
												&& contentStr[0].length() == 2
												&& contentStr[2].length() == 4) {
											cellValue = contentStr[2] + "-"
													+ contentStr[1] + "-"
													+ contentStr[0];
										}
									}
								} catch (Exception e) {
									// cellValue=
									// row[j].getContents()==null?"":row[j].getContents();
									cellValue = "";
									if (newRowIndex[j].getContents() != null) {
										cellValue = newRowIndex[j]
												.getContents();
										if (cellValue.contains("/")) {
											String[] contentStr = cellValue
													.split("/");
											if (contentStr.length == 3
													&& contentStr[0].length() == 2
													&& contentStr[2].length() == 4) {
												cellValue = contentStr[2] + "/"
														+ contentStr[1] + "/"
														+ contentStr[0];
											}
										} else if (cellValue.contains("-")) {
											String[] contentStr = cellValue
													.split("-");
											if (contentStr.length == 3
													&& contentStr[0].length() == 2
													&& contentStr[2].length() == 4) {
												cellValue = contentStr[2] + "-"
														+ contentStr[1] + "-"
														+ contentStr[0];
											}
										}
									}
								}
							} else {
								cellValue = newRowIndex[j].getContents() == null ? ""
										: newRowIndex[j].getContents();
							}
							// cellValue=
							// row[j].getContents()==null?"":row[j].getContents();
							if (j > 2) {
								aliasCellmap.put("#CELLA" + j + "#",
										cellValue == null ? "" : cellValue
												.trim());
								aliasCellmap.put("SHIFT_NO", "");
								aliasCellmap.put("AR_DATE_STR", "1");
							} else {
								aliasCellmap.put("#CELL" + j + "#",
										cellValue == null ? "" : cellValue
												.trim());
							}
						} catch (Exception e) {
							e.printStackTrace();
							return token = 2;
						}
					}

					String aliasKeySql = "";
					String aliasValueSql = "";
					int initKey = 0;
					if (aliasValueMap != null && aliasTypeMap != null) {
						Set keySet = aliasValueMap.keySet();
						if (keySet != null) {
							Iterator it = keySet.iterator();
							while (it.hasNext()) {
								initKey++;
								Object keyValue = it.next();
								if (keyValue != null && !keyValue.equals("")) {
									int fieldType = Integer
											.parseInt(aliasTypeMap
													.get(keyValue).toString());
									Object aliasCellmapValue = aliasCellmap
											.get(aliasValueMap.get(keyValue));
									if (aliasCellmapValue == null) {// 不属于excel的列
										if ("AR_DATE_STR".equals(keyValue)) {
											// for (int l = 3; l < columnLength;
											// l++) {
											cellValue = rowIndex0[i]
													.getContents();
											if (cellValue.contains("/")) {
												String[] contentStr = cellValue
														.split("/");
												if (contentStr.length == 3
														&& contentStr[0]
																.length() == 2
														&& contentStr[2]
																.length() == 4) {
													cellValue = contentStr[2]
															+ "/"
															+ contentStr[1]
															+ "/"
															+ contentStr[0];
												}
											} else if (cellValue.contains("-")) {
												String[] contentStr = cellValue
														.split("-");
												if (contentStr.length == 3
														&& contentStr[0]
																.length() == 2
														&& contentStr[2]
																.length() == 4) {
													cellValue = contentStr[2]
															+ "-"
															+ contentStr[1]
															+ "-"
															+ contentStr[0];
												}
												// }
											}
											aliasKeySql += ","
													+ keyValue.toString();
											String dateStr = " to_char(to_date('"
													+ cellValue
													+ "', 'YYYY/MM/DD'),'YYYY/MM/DD')";
											aliasValueSql += "," + dateStr
													+ " ";
										} else {
											aliasCellmapValue = aliasValueMap
													.get(keyValue);
											if (aliasCellmapValue != null
													&& aliasCellmapValue
															.toString()
															.contains("#CELL")) {
												String sqlContent = aliasCellmapValue
														.toString();
												for (int l = 3; l < columnLength; l++) {
													sqlContent = sqlContent
															.replaceAll(
																	"#CELL34#",
																	cellShiftValue);
													sqlContent = sqlContent
															.replaceAll(
																	"#CELLA"
																			+ l
																			+ "#",
																	cellValue);
												}
												LinkedHashMap sqlI8nContentMap = new LinkedHashMap();
												switch (fieldType) {
												case 2:
													sqlI8nContentMap.put(
															"sqlI18nContent",
															sqlContent);
													aliasCellmapValue = this
															.getCodeNoByName(sqlI8nContentMap) == -1 ? ""
															: this
																	.getCodeNoByName(sqlI8nContentMap);
													break;
												default:
													sqlI8nContentMap.put(
															"sqlContent",
															sqlContent);
													if (this
															.getContentNoByFiled(sqlI8nContentMap) != null) {
														Object cellObj = this
																.getContentNoByFiled(
																		sqlI8nContentMap)
																.size() == 0 ? ""
																: this
																		.getContentNoByFiled(
																				sqlI8nContentMap)
																		.get(0);
														if (cellObj != null
																&& !cellObj
																		.equals("")
																&& ((Map) cellObj)
																		.get(keyValue
																				.toString()) != null) {
															aliasCellmapValue = (cellObj == null ? ""
																	: ((Map) cellObj)
																			.get(
																					keyValue
																							.toString()
																							.toUpperCase())
																			.toString());
														} else {
															aliasCellmapValue = "";
														}
													}
													break;
												}
											}
										}
									} else {
										if (aliasValueI18nMap != null
												&& aliasValueI18nMap
														.get(keyValue) != null) {// 有需要从国际化表读取No的列
											String sqlContent = aliasValueI18nMap
													.get(keyValue) == null ? ""
													: aliasValueI18nMap.get(
															keyValue)
															.toString();
											sqlContent = sqlContent
													.replaceAll(
															aliasValueMap
																	.get(keyValue) == null ? ""
																	: aliasValueMap
																			.get(
																					keyValue)
																			.toString(),
															"'"
																	+ aliasCellmapValue
																			.toString()
																	+ "'");

											LinkedHashMap sqlI8nContentMap = new LinkedHashMap();
											sqlI8nContentMap.put(
													"sqlI18nContent",
													sqlContent);
											if (aliasValueI18nMap.get(keyValue
													+ "_FLAG") != null
													&& aliasValueI18nMap.get(
															keyValue + "_FLAG")
															.equals("Y")) {
												sqlI8nContentMap.put(
														"sqlContent",
														sqlContent);
												List contentList = this
														.getContentNoByFiled(sqlI8nContentMap);
												if (contentList.get(0) != null
														&& ((Map) (contentList
																.get(0)))
																.get("CPNY_ID") != null) {
													aliasCellmapValue = (contentList == null ? ""
															: ((Map) (contentList
																	.get(0)))
																	.get(
																			"CPNY_ID")
																	.toString());
												} else {
													aliasCellmapValue = "";
												}
											} else {
												aliasCellmapValue = this
														.getCodeNoByName(sqlI8nContentMap) == -1 ? ""
														: this
																.getCodeNoByName(sqlI8nContentMap);
											}
											if (aliasCellmapValue.equals("")) {
											}

										}
									}
									if (!"AR_DATE_STR".equals(keyValue)) {
										switch (fieldType) {

										case 0:
											// smap.put(keyValue.toString(),
											// "'"+aliasValueMap.get(keyValue)+"'");
											if (initKey == 1) {
												aliasKeySql = keyValue
														.toString();
												aliasValueSql = "'"
														+ aliasCellmapValue
														+ "'";
											} else {
												aliasKeySql += ","
														+ keyValue.toString();
												aliasValueSql += ",'"
														+ aliasCellmapValue
														+ "'";
											}
											break;
										case 1:
											// smap.put(keyValue.toString(),
											// "'"+aliasValueMap.get(keyValue)+"'");
											if (initKey == 1) {
												aliasKeySql = keyValue
														.toString();
												if (aliasCellmapValue != null
														&& aliasCellmapValue
																.toString()
																.contains("/")) {
													aliasValueSql = "to_date('"
															+ aliasCellmapValue
															+ "','YYYY/MM/DD')";// "'"+aliasCellmapValue+"'";
												} else if (aliasCellmapValue != null
														&& aliasCellmapValue
																.toString()
																.contains("-")) {
													aliasValueSql = "to_date('"
															+ aliasCellmapValue
															+ "','YYYY-MM-DD')";// "'"+aliasCellmapValue+"'";
												}
											} else {
												aliasKeySql += ","
														+ keyValue.toString();
												if (aliasCellmapValue != null
														&& aliasCellmapValue
																.toString()
																.contains("/")) {
													aliasValueSql += ",to_date('"
															+ aliasCellmapValue
															+ "','YYYY/MM/DD')";// "'"+aliasCellmapValue+"'";
												} else if (aliasCellmapValue != null
														&& aliasCellmapValue
																.toString()
																.contains("-")) {
													aliasValueSql += ",to_date('"
															+ aliasCellmapValue
															+ "','YYYY-MM-DD')";// "'"+aliasCellmapValue+"'";
												}
											}
											break;
										case 11:
											if (initKey == 1) {
												aliasKeySql = keyValue
														.toString();
												if (aliasCellmapValue != null
														&& aliasCellmapValue
																.toString()
																.contains("/")) {
													aliasValueSql = "to_date('"
															+ aliasCellmapValue
															+ "','YYYY/MM/DD HH24:MI:SS')";// "'"+aliasCellmapValue+"'";
												} else if (aliasCellmapValue != null
														&& aliasCellmapValue
																.toString()
																.contains("-")) {
													aliasValueSql = "to_date('"
															+ aliasCellmapValue
															+ "','YYYY-MM-DD HH24:MI:SS')";// "'"+aliasCellmapValue+"'";
												}
											} else {
												aliasKeySql += ","
														+ keyValue.toString();
												if (aliasCellmapValue != null
														&& aliasCellmapValue
																.toString()
																.contains("/")) {
													aliasValueSql += ",to_date('"
															+ aliasCellmapValue
															+ "','YYYY/MM/DD HH24:MI:SS')";// "'"+aliasCellmapValue+"'";
												} else if (aliasCellmapValue != null
														&& aliasCellmapValue
																.toString()
																.contains("-")) {
													aliasValueSql += ",to_date('"
															+ aliasCellmapValue
															+ "','YYYY-MM-DD HH24:MI:SS')";
												}
											}
											break;
										case 12:
											if (initKey == 1) {
												aliasKeySql = keyValue
														.toString();
												if (aliasCellmapValue != null
														&& aliasCellmapValue
																.toString()
																.contains("/")) {
													aliasValueSql = "to_date('"
															+ aliasCellmapValue
															+ "','YYYY/MM/DD HH24:MI')";// "'"+aliasCellmapValue+"'";
												} else if (aliasCellmapValue != null
														&& aliasCellmapValue
																.toString()
																.contains("-")) {
													aliasValueSql = "to_date('"
															+ aliasCellmapValue
															+ "','YYYY-MM-DD HH24:MI')";// "'"+aliasCellmapValue+"'";
												}
											} else {
												aliasKeySql += ","
														+ keyValue.toString();
												if (aliasCellmapValue != null
														&& aliasCellmapValue
																.toString()
																.contains("/")) {
													aliasValueSql += ",to_date('"
															+ aliasCellmapValue
															+ "','YYYY/MM/DD HH24:MI')";// "'"+aliasCellmapValue+"'";
												} else if (aliasCellmapValue != null
														&& aliasCellmapValue
																.toString()
																.contains("-")) {
													aliasValueSql += ",to_date('"
															+ aliasCellmapValue
															+ "','YYYY-MM-DD HH24:MI')";
												}
											}
											break;
										case 13:
											// smap.put(keyValue.toString(),
											// "'"+aliasValueMap.get(keyValue)+"'");
											if (initKey == 1) {
												aliasKeySql = keyValue
														.toString();
												aliasValueSql = "'"
														+ aliasCellmapValue
														+ "'";
											} else {
												aliasKeySql += ","
														+ keyValue.toString();
												aliasValueSql += ",'"
														+ aliasCellmapValue
														+ "'";
											}
											break;
										default:
											// smap.put(keyValue.toString(),
											// aliasValueMap.get(keyValue));
											if (initKey == 1) {
												aliasKeySql = keyValue
														.toString();
												aliasValueSql = (aliasCellmapValue == null ? "null"
														: aliasCellmapValue
																.toString());
											} else {
												aliasKeySql += ","
														+ keyValue.toString();
												aliasValueSql += ","
														+ (aliasCellmapValue == null ? "null"
																: aliasCellmapValue
																		.toString())
														+ "";
											}
											break;
										}
									}
								}
							}
						}
					}

					smap.put("tableName", tableName);
					smap.put("aliasNameKeyContent", aliasKeySql);
					smap.put("aliasValueContent", aliasValueSql);
					if (cellShiftValues != null && cellShiftValues != "") {
						addList.add(smap);
					}
				}
			}
			dataMap.put("addList", addList);
			// int result=this.insertExcelData(dataMap);
			this.insertExcelData(dataMap);
			// if(result==1){
			token = 1;
		} catch (Exception e) { // 未找到文件异常
			e.printStackTrace();
			token = 0;
		}
		return token;
	}

	/**
	 * 分析excel的各个列，获取各个列的值，并且导入的数据库中 Description:
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param modelMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ModelMap importArShiftData(HttpServletRequest request,
			HttpServletResponse response, LinkedHashMap map, ModelMap modelMap) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String adminID = admin.getAdminID() == null ? "USERNO_"
				+ admin.getUserNo() : admin.getAdminID();
		ObjectBindUtil.getRequestParamData(request);
		this.filename = adminID;
		this.pathCpnyID = admin.getCpnyId();
		this.language = admin.getLanguage();
		if (this.processUploadFile(request, response)) {
			int returnInt = this.impArShiftDataBase(adminID, map, request);
			if (returnInt == 0) {
				modelMap.put("sign", "0");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.importfail", request));// 导入失败!
			} else if (returnInt < 0) {
				modelMap.put("sign", "-1");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.rulewrong", request));// 调整事由有误，请按规则填写！
			} else if (returnInt == 2) {
				modelMap.put("sign", "2");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.existnull", request));// 表有空值或有空单元行，请检查！
			} else if (returnInt == 21) {
				modelMap.put("sign", "2");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.dateInputIsNotNull",
						request));// 日期输入不能为空，请检查！
			} else if (returnInt == 22) {
				modelMap.put("sign", "2");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.shiftInputIsNotNull",
						request));// 班次输入不能为空，请检查！
			} else if (returnInt == 1) {
				modelMap.put("sign", "1");
				modelMap.put("statusCode", "200");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.importsuccess", request));// 导入成功
			} else if (returnInt == 3) {
				modelMap.put("sign", "3");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.dataerror", request));// 数据列数有问题，请核对
			} else if (returnInt == 4) {
				modelMap.put("sign", "3");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.datanull", request));// 所导入数据中考勤月或工号存在为空的情况，请检查核对
			} else if (returnInt == 6) {
				modelMap.put("sign", "3");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.empiderror", request));// 所导入数据中工号存在问题，不能进行数据导入
			} else if (returnInt == 7) {
				modelMap.put("sign", "3");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.deleteerror", request));// 删除历史数据出错,请联系管理员
			} else if (returnInt > 10) {
				modelMap.put("sign", "3");
				modelMap.put("statusCode", "300");
				modelMap.put("message", String.valueOf(returnInt)
						+ TipMessage.getTipMessage(
								"ar.alert.message.excelimport.noauther",
								request));// EMPID:工号存在问题(该员工不存在，或者当前考勤员对该员工所在部门无考勤权限)
			}

			int i = path.indexOf("\\");
			String server = "win";
			if (i < 0) { // WebServer的os为unix/linux
				server = "u/l";
			}
			if ("win".equals(server)) {
				File file = new File(this.path + "\\" + filename + ".xls");
				file.delete();
			} else {
				File file = new File(this.path + "/" + filename + ".xls");
				file.delete();
			}

		} else {
			modelMap.put("sign", "0");
			modelMap.put("statusCode", "300");
			modelMap.put("message", TipMessage.getTipMessage(
					"ar.alert.message.excelimport.importfail", request));// 导入失败!
		}
		return modelMap;
	}

	/**
	 * 分析excel的各个列，获取各个列的值，并且导入的数据库中 Description:
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param modelMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ModelMap importArCardRecordData(HttpServletRequest request,
			HttpServletResponse response, LinkedHashMap map, ModelMap modelMap) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String adminID = admin.getAdminID() == null ? "USERNO_"
				+ admin.getUserNo() : admin.getAdminID();
		ObjectBindUtil.getRequestParamData(request);
		item_no = request.getParameter("id");
		type = request.getParameter("type");
		this.filename = adminID;
		this.pathCpnyID = admin.getCpnyId();
		this.language = admin.getLanguage();
		if (this.processUploadFile(request, response)) {
			int returnInt = this.impArCardRecordDataBase(request, adminID, map);
			if (returnInt == 0) {
				modelMap.put("sign", "0");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.importfail", request));// 导入失败!
			} else if (returnInt < 0) {
				modelMap.put("sign", "-1");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.leixingerror", request));// 类型有误，请按规则填写！
			} else if (returnInt == 2) {
				modelMap.put("sign", "2");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.existnull", request));// 表有空值或有空单元行，请检查！
			} else if (returnInt == 1) {
				modelMap.put("sign", "1");
				modelMap.put("statusCode", "200");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.importsuccess", request));// 导入成功
			} else if (returnInt == 7) {
				modelMap.put("sign", "3");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.deleteerror", request));// 删除历史数据出错,请联系管理员
			}
			File file = new File(this.path + "\\" + filename + ".xls");
			file.delete();
		} else {
			modelMap.put("sign", "0");
			modelMap.put("statusCode", "300");
			modelMap.put("message", TipMessage.getTipMessage(
					"ar.alert.message.excelimport.importfail", request));// 导入失败!
		}
		return modelMap;
	}

	@SuppressWarnings( { "unchecked" })
	public int impArCardRecordDataBase(HttpServletRequest request,
			String adminID, LinkedHashMap cellmap) {
		int token = 0;

		int k = path.indexOf("\\");
		String server = "win";
		if (k < 0) { // WebServer的os为unix/linux
			server = "u/l";
		}

		File file = null;

		if ("win".equals(server)) {
			file = new File(path + "\\" + filename + ".xls");
		} else {
			file = new File(path + "/" + filename + ".xls");
		}

		Map dataMap = new HashMap();

		try {
			Workbook workBook = Workbook.getWorkbook(file);
			// 获得第一个工作表对象
			Sheet sheet = workBook.getSheet(0);
			// 验证是否存在导入数据
			if (sheet.getRows() < 2) {
				logger.debug("Import file not exist data, return.");
				return token;
			}

			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			List cordList = new ArrayList();

			// 删除刷卡数据
			for (int m = 1; m < sheet.getRows(); m++) {

				Cell[] rowTemp = sheet.getRow(m);

				// 检查考勤是否锁定及在汇总表中检查数据是否已存在 存在则删除
				LinkedHashMap tempMap = new LinkedHashMap();
				tempMap.put("EMPID", rowTemp[0].getContents().trim());

				String cellValue = "";

				if (rowTemp[3] != null) {
					try {
						cellValue = this.FormateData(rowTemp[3], (Object) 11);
						if (cellValue.contains("/")) {
							String[] contentStr = cellValue.split("/");
							if (contentStr.length == 3
									&& contentStr[0].length() == 2
									&& contentStr[2].length() == 4) {
								cellValue = contentStr[2] + "/" + contentStr[1]
										+ "/" + contentStr[0];
							}
						} else if (cellValue.contains("-")) {
							String[] contentStr = cellValue.split("-");
							if (contentStr.length == 3
									&& contentStr[0].length() == 2
									&& contentStr[2].length() == 4) {
								cellValue = contentStr[2] + "-" + contentStr[1]
										+ "-" + contentStr[0];
							}
						}
					} catch (Exception e) {
						// cellValue=
						// row[j].getContents()==null?"":row[j].getContents();
						cellValue = "";
						if (rowTemp[3].getContents() != null) {
							cellValue = rowTemp[3].getContents();
							if (cellValue.contains("/")) {
								String[] contentStr = cellValue.split("/");
								if (contentStr.length == 3
										&& contentStr[0].length() == 2
										&& contentStr[2].length() == 4) {
									cellValue = contentStr[2] + "/"
											+ contentStr[1] + "/"
											+ contentStr[0];
								}
							} else if (cellValue.contains("-")) {
								String[] contentStr = cellValue.split("-");
								if (contentStr.length == 3
										&& contentStr[0].length() == 2
										&& contentStr[2].length() == 4) {
									cellValue = contentStr[2] + "-"
											+ contentStr[1] + "-"
											+ contentStr[0];
								}
							}
						}
					}
				} else {
					return 7;
				}
				tempMap.put("R_TIME", cellValue);
				tempMap.put("CPNY_ID", admin.getCpnyId());
				tempMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());
				tempMap.put("AR_MAC_RECORDS", "AR_MAC_RECORDS_"
						+ admin.getCpnyId());

				cordList.add(tempMap);
			}

			// 删除汇总表中数据
			if (this.deleteCordInfo(cordList) != 1) {
				return 7;// 删除汇总历史数据出错,请联系管理员
			}

			String tableName = cellmap.get("tableName") == null ? "" : cellmap
					.get("tableName").toString();// 获取表名

			LinkedHashMap aliasValueMap = cellmap.get("aliasValueMap") == null ? null
					: (LinkedHashMap) cellmap.get("aliasValueMap");
			LinkedHashMap aliasTypeMap = cellmap.get("aliasTypeMap") == null ? null
					: (LinkedHashMap) cellmap.get("aliasTypeMap");
			LinkedHashMap aliasValueI18nMap = cellmap.get("aliasValueI18nMap") == null ? null
					: (LinkedHashMap) cellmap.get("aliasValueI18nMap");

			LinkedHashMap aliasMapCells = new LinkedHashMap();
			if (aliasValueMap != null && aliasTypeMap != null) {// 存放CELL0：NUMBER类型的键值对
				Set keySet = aliasValueMap.keySet();
				if (keySet != null) {
					Iterator it = keySet.iterator();
					while (it.hasNext()) {
						Object keyvalue = it.next();
						if (keyvalue != null) {
							Object aliasValue = aliasValueMap.get(keyvalue);
							if (aliasValue != null) {
								aliasMapCells.put(aliasValue.toString(),
										aliasTypeMap.get(keyvalue));
							}
						}
					}
				}
			}
			List addList = new ArrayList();
			// 循环工作表的每行数据
			int init = 0;
			int init1 = 0;
			for (int i = 1; i < sheet.getRows(); i++) {
				// 验证当前行是否符合规范
				// if (!validateCell(sheet.getRow(i), sheet.getRow(0).length))
				// continue;
				// if (!validateCell(sheet.getRow(i),
				// sheet.getRow(0).length)){//如果所在行全为空，不导入
				// break;
				// }
				Cell[] row = sheet.getRow(i);

				if (init == 0) {
					// init=row.length;
					init = sheet.getRow(0).length;
				}

				if (init1 == 0) {
					// init=row.length;
					init1 = sheet.getRow(i).length;
				}

				LinkedHashMap smap = new LinkedHashMap();
				LinkedHashMap aliasCellmap = new LinkedHashMap();
				for (int j = 0; j < init; j++) {
					try {
						String cellValue = "";
						// cellValue= row[j].getContents();
						// aliasCellmap.put("#CELL"+j+"#", cellValue);
						Object cellType = null;
						cellType = aliasMapCells.get("#CELL" + j + "#");

						try {
							if (cellType != null
									&& !"".equals(row[j].getContents())) {// date类型
								try {
									cellValue = this.FormateData(row[j],
											cellType);
									if (cellValue.contains("/")) {
										String[] contentStr = cellValue
												.split("/");
										if (contentStr.length == 3
												&& contentStr[0].length() == 2
												&& contentStr[2].length() == 4) {
											cellValue = contentStr[2] + "/"
													+ contentStr[1] + "/"
													+ contentStr[0];
										}
									} else if (cellValue.contains("-")) {
										String[] contentStr = cellValue
												.split("-");
										if (contentStr.length == 3
												&& contentStr[0].length() == 2
												&& contentStr[2].length() == 4) {
											cellValue = contentStr[2] + "-"
													+ contentStr[1] + "-"
													+ contentStr[0];
										}
									}
								} catch (Exception e) {
									// cellValue=
									// row[j].getContents()==null?"":row[j].getContents();
									cellValue = "";
									if (row[j].getContents() != null) {
										cellValue = row[j].getContents();
										if (cellValue.contains("/")) {
											String[] contentStr = cellValue
													.split("/");
											if (contentStr.length == 3
													&& contentStr[0].length() == 2
													&& contentStr[2].length() == 4) {
												cellValue = contentStr[2] + "/"
														+ contentStr[1] + "/"
														+ contentStr[0];
											}
										} else if (cellValue.contains("-")) {
											String[] contentStr = cellValue
													.split("-");
											if (contentStr.length == 3
													&& contentStr[0].length() == 2
													&& contentStr[2].length() == 4) {
												cellValue = contentStr[2] + "-"
														+ contentStr[1] + "-"
														+ contentStr[0];
											}
										}
									}
								}
							} else {
								cellValue = row[j].getContents() == null ? ""
										: row[j].getContents();
							}
						} catch (Exception e) {
							cellValue = "";
						}
						aliasCellmap.put("#CELL" + j + "#",
								cellValue == null ? "" : cellValue.trim());
					} catch (Exception e) {
						e.printStackTrace();
						return token = 2;
					}
				}
				String aliasKeySql = "";
				String aliasValueSql = "";
				int initKey = 0;
				if (aliasValueMap != null && aliasTypeMap != null) {
					Set keySet = aliasValueMap.keySet();
					if (keySet != null) {
						Iterator it = keySet.iterator();
						while (it.hasNext()) {
							initKey++;
							Object keyValue = it.next();
							if (keyValue != null && !keyValue.equals("")) {
								int fieldType = Integer.parseInt(aliasTypeMap
										.get(keyValue).toString());
								Object aliasCellmapValue = aliasCellmap
										.get(aliasValueMap.get(keyValue));
								if (aliasCellmapValue == null) {// 不属于excel的列
									aliasCellmapValue = aliasValueMap
											.get(keyValue);
									if (aliasCellmapValue != null
											&& aliasCellmapValue.toString()
													.contains("#CELL")) {
										String sqlContent = aliasCellmapValue
												.toString();
										for (int l = 0; l < init; l++) {
											sqlContent = sqlContent.replaceAll(
													"#CELL" + l + "#",
													aliasCellmap.get(
															"#CELL" + l + "#")
															.toString());
											if (!sqlContent.contains("#CELL")) {
												break;
											}
										}
										LinkedHashMap sqlI8nContentMap = new LinkedHashMap();

										switch (fieldType) {
										case 2:
											sqlI8nContentMap.put(
													"sqlI18nContent",
													sqlContent);
											aliasCellmapValue = this
													.getCodeNoByName(sqlI8nContentMap) == -1 ? ""
													: this
															.getCodeNoByName(sqlI8nContentMap);
											break;
										default:
											sqlI8nContentMap.put("sqlContent",
													sqlContent);
											if (this
													.getContentNoByFiled(sqlI8nContentMap) != null) {
												Object cellObj = this
														.getContentNoByFiled(
																sqlI8nContentMap)
														.size() == 0 ? ""
														: this
																.getContentNoByFiled(
																		sqlI8nContentMap)
																.get(0);
												if (cellObj != null
														&& !cellObj.equals("")
														&& ((Map) cellObj)
																.get(keyValue
																		.toString()) != null) {
													aliasCellmapValue = (cellObj == null ? ""
															: ((Map) cellObj)
																	.get(
																			keyValue
																					.toString()
																					.toUpperCase())
																	.toString());
												} else {
													aliasCellmapValue = "";
												}
											}

											break;
										}
									}
								} else {
									if (aliasValueI18nMap != null
											&& aliasValueI18nMap.get(keyValue) != null) {// 有需要从国际化表读取No的列
										String sqlContent = aliasValueI18nMap
												.get(keyValue) == null ? ""
												: aliasValueI18nMap.get(
														keyValue).toString();
										sqlContent = sqlContent
												.replaceAll(
														aliasValueMap
																.get(keyValue) == null ? ""
																: aliasValueMap
																		.get(
																				keyValue)
																		.toString(),
														"'"
																+ aliasCellmapValue
																		.toString()
																+ "'");

										LinkedHashMap sqlI8nContentMap = new LinkedHashMap();
										sqlI8nContentMap.put("sqlI18nContent",
												sqlContent);
										if (aliasValueI18nMap.get(keyValue
												+ "_FLAG") != null
												&& aliasValueI18nMap.get(
														keyValue + "_FLAG")
														.equals("Y")) {
											sqlI8nContentMap.put("sqlContent",
													sqlContent);
											List contentList = this
													.getContentNoByFiled(sqlI8nContentMap);
											if (contentList.get(0) != null
													&& ((Map) (contentList
															.get(0)))
															.get("CPNY_ID") != null) {
												aliasCellmapValue = (contentList == null ? ""
														: ((Map) (contentList
																.get(0))).get(
																"CPNY_ID")
																.toString());
											} else {
												aliasCellmapValue = "";
											}
										} else {
											aliasCellmapValue = this
													.getCodeNoByName(sqlI8nContentMap) == -1 ? ""
													: this
															.getCodeNoByName(sqlI8nContentMap);
										}

										if (aliasCellmapValue.equals("")) {
											System.out
													.println(aliasValueMap.get(
															keyValue)
															.toString()
															+ "-----------------------------------");
										}
									}
								}
								switch (fieldType) {
								case 0:
									// smap.put(keyValue.toString(),
									// "'"+aliasValueMap.get(keyValue)+"'");
									if (initKey == 1) {
										aliasKeySql = keyValue.toString();
										aliasValueSql = "'" + aliasCellmapValue
												+ "'";
									} else {
										aliasKeySql += ","
												+ keyValue.toString();
										aliasValueSql += ",'"
												+ aliasCellmapValue + "'";
									}
									break;
								case 1:
									// smap.put(keyValue.toString(),
									// "'"+aliasValueMap.get(keyValue)+"'");
									if (initKey == 1) {
										aliasKeySql = keyValue.toString();
										if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("/")) {
											aliasValueSql = "to_date('"
													+ aliasCellmapValue
													+ "','YYYY/MM/DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										} else if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("-")) {
											aliasValueSql = "to_date('"
													+ aliasCellmapValue
													+ "','YYYY-MM-DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										}
									} else {
										aliasKeySql += ","
												+ keyValue.toString();
										if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("/")) {
											aliasValueSql += ",to_date('"
													+ aliasCellmapValue
													+ "','YYYY/MM/DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										} else if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("-")) {
											aliasValueSql += ",to_date('"
													+ aliasCellmapValue
													+ "','YYYY-MM-DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										}
									}
									break;
								case 11:
									if (initKey == 1) {
										aliasKeySql = keyValue.toString();
										if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("/")) {
											aliasValueSql = "to_date('"
													+ aliasCellmapValue
													+ "','YYYY/MM/DD HH24:MI:SS')";// "'"+aliasCellmapValue+"'";
										} else if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("-")) {
											aliasValueSql = "to_date('"
													+ aliasCellmapValue
													+ "','YYYY-MM-DD HH24:MI:SS')";// "'"+aliasCellmapValue+"'";
										}
									} else {
										aliasKeySql += ","
												+ keyValue.toString();
										if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("/")) {
											aliasValueSql += ",to_date('"
													+ aliasCellmapValue
													+ "','YYYY/MM/DD HH24:MI:SS')";// "'"+aliasCellmapValue+"'";
										} else if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("-")) {
											aliasValueSql += ",to_date('"
													+ aliasCellmapValue
													+ "','YYYY-MM-DD HH24:MI:SS')";
										}
									}
									break;
								case 12:
									if (initKey == 1) {
										aliasKeySql = keyValue.toString();
										if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("/")) {
											aliasValueSql = "to_date('"
													+ aliasCellmapValue
													+ "','YYYY/MM/DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										} else if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("-")) {
											aliasValueSql = "to_date('"
													+ aliasCellmapValue
													+ "','YYYY-MM-DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										}
									} else {
										aliasKeySql += ","
												+ keyValue.toString();
										if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("/")) {
											aliasValueSql += ",to_date('"
													+ aliasCellmapValue
													+ "','YYYY/MM/DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										} else if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("-")) {
											aliasValueSql += ",to_date('"
													+ aliasCellmapValue
													+ "','YYYY-MM-DD HH24:MI')";
										}
									}
									break;
								default:
									// smap.put(keyValue.toString(),
									// aliasValueMap.get(keyValue));
									if (initKey == 1) {
										aliasKeySql = keyValue.toString();
										aliasValueSql = (aliasCellmapValue == null ? "null"
												: aliasCellmapValue.toString());
									} else {
										aliasKeySql += ","
												+ keyValue.toString();
										aliasValueSql += ","
												+ (aliasCellmapValue == null ? "null"
														: aliasCellmapValue
																.toString())
												+ "";
									}
									break;
								}
							}
						}
					}
				}
				smap.put("tableName", tableName);
				smap.put("aliasNameKeyContent", aliasKeySql);
				smap.put("aliasValueContent", aliasValueSql);
				addList.add(smap);
			}
			dataMap.put("addList", addList);
			// int result=this.insertExcelData(dataMap);
			this.insertExcelData(dataMap);
			// if(result==1){
			token = 1;
			// }else{
			// token = 0;
			// }
		} catch (Exception e) { // 未找到文件异常
			e.printStackTrace();
			token = 0;
		}
		return token;
	}

	/**
	 * 分析excel的各个列，获取各个列的值，并且导入的数据库中 Description:
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param modelMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ModelMap importArDetialData(HttpServletRequest request,
			HttpServletResponse response, LinkedHashMap map,
			LinkedHashMap historymap, ModelMap modelMap) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String adminID = admin.getAdminID() == null ? "USERNO_"
				+ admin.getUserNo() : admin.getAdminID();
		ObjectBindUtil.getRequestParamData(request);
		item_no = request.getParameter("id");
		type = request.getParameter("type");
		this.filename = adminID;
		this.pathCpnyID = admin.getCpnyId();
		this.language = admin.getLanguage();
		if (this.processUploadFile(request, response)) {
			int returnInt = this.impArDetailDataBase(adminID, map);
			this.impArDetailDataBase(adminID, historymap);
			if (returnInt == 0) {
				modelMap.put("sign", "0");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.importfail", request));// 导入失败!
			} else if (returnInt < 0) {
				modelMap.put("sign", "-1");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.rulewrong", request));// 调整事由有误，请按规则填写！
			} else if (returnInt == 2) {
				modelMap.put("sign", "2");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.existnull", request));// 表有空值或有空单元行，请检查！
			} else if (returnInt == 1) {
				modelMap.put("sign", "1");
				modelMap.put("statusCode", "200");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.importsuccess", request));// 导入成功
			}
			File file = new File(this.path + "\\" + filename + ".xls");
			if (!map.get("tableName").equals("AR_DETAIL_" + this.pathCpnyID)) {
				file.delete();
			}
		} else {
			modelMap.put("sign", "0");
			modelMap.put("statusCode", "300");
			modelMap.put("message", TipMessage.getTipMessage(
					"ar.alert.message.excelimport.importfail", request));// 导入失败!
		}
		return modelMap;
	}

	@SuppressWarnings( { "unchecked" })
	public int impArDetailDataBase(String adminID, LinkedHashMap cellmap) {
		int token = 0;

		int k = path.indexOf("\\");
		String server = "win";
		if (k < 0) { // WebServer的os为unix/linux
			server = "u/l";
		}

		File file = null;

		if ("win".equals(server)) {
			file = new File(path + "\\" + filename + ".xls");
		} else {
			file = new File(path + "/" + filename + ".xls");
		}

		Map dataMap = new HashMap();

		try {
			Workbook workBook = Workbook.getWorkbook(file);
			// 获得第一个工作表对象
			Sheet sheet = workBook.getSheet(0);
			// 验证是否存在导入数据
			if (sheet.getRows() < 2) {
				logger.debug("Import file not exist data, return.");
				return token;
			}

			String tableName = cellmap.get("tableName") == null ? "" : cellmap
					.get("tableName").toString();// 获取表名

			LinkedHashMap aliasValueMap = cellmap.get("aliasValueMap") == null ? null
					: (LinkedHashMap) cellmap.get("aliasValueMap");
			LinkedHashMap aliasTypeMap = cellmap.get("aliasTypeMap") == null ? null
					: (LinkedHashMap) cellmap.get("aliasTypeMap");
			LinkedHashMap aliasValueI18nMap = cellmap.get("aliasValueI18nMap") == null ? null
					: (LinkedHashMap) cellmap.get("aliasValueI18nMap");

			LinkedHashMap aliasMapCells = new LinkedHashMap();
			if (aliasValueMap != null && aliasTypeMap != null) {// 存放CELL0：NUMBER类型的键值对,获取每一列的字符类型
				Set keySet = aliasValueMap.keySet();
				if (keySet != null) {
					Iterator it = keySet.iterator();
					while (it.hasNext()) {
						Object keyvalue = it.next();
						if (keyvalue != null) {
							Object aliasValue = aliasValueMap.get(keyvalue);
							if (aliasValue != null) {
								aliasMapCells.put(aliasValue.toString(),
										aliasTypeMap.get(keyvalue));
							}
						}
					}
				}
			}
			List addList = new ArrayList();
			// 循环工作表的每行数据
			int init = 0;
			// 获取一共有多少列
			for (int i = 1; i < sheet.getRows(); i++) {
				// 验证当前行是否符合规范
				// if (!validateCell(sheet.getRow(i), sheet.getRow(0).length))
				// continue;
				if (!validateCell(sheet.getRow(i), sheet.getRow(0).length)) {// 如果所在行全为空，不导入
					break;
				}
				Cell[] row = sheet.getRow(i);

				if (init == 0) {
					// init=row.length;
					init = sheet.getRow(0).length;
				}

				LinkedHashMap smap = new LinkedHashMap();
				LinkedHashMap aliasCellmap = new LinkedHashMap();
				// 将excel格子里的值放入cellX中
				for (int j = 0; j < init; j++) {
					try {
						String cellValue = "";
						// cellValue= row[j].getContents();
						// aliasCellmap.put("#CELL"+j+"#", cellValue);
						Object cellType = null;
						cellType = aliasMapCells.get("#CELL" + j + "#");
						try {
							if (j != 0 && cellType != null
									&& !row[j].getContents().equals("")) {// date类型
								try {
									cellValue = this.FormateData(row[j],
											cellType);
									if (cellValue.contains("/")) {
										String[] contentStr = cellValue
												.split("/");
										if (contentStr.length == 3
												&& contentStr[0].length() == 2
												&& contentStr[2].length() == 4) {
											cellValue = contentStr[2] + "/"
													+ contentStr[1] + "/"
													+ contentStr[0];
										}
									} else if (cellValue.contains("-")) {
										String[] contentStr = cellValue
												.split("-");
										if (contentStr.length == 3
												&& contentStr[0].length() == 2
												&& contentStr[2].length() == 4) {
											cellValue = contentStr[2] + "-"
													+ contentStr[1] + "-"
													+ contentStr[0];
										}
									}
								} catch (Exception e) {
									// cellValue=
									// row[j].getContents()==null?"":row[j].getContents();
									cellValue = "";
									if (row[j].getContents() != null) {
										cellValue = row[j].getContents();
										if (cellValue.contains("/")) {
											String[] contentStr = cellValue
													.split("/");
											if (contentStr.length == 3
													&& contentStr[0].length() == 2
													&& contentStr[2].length() == 4) {
												cellValue = contentStr[2] + "/"
														+ contentStr[1] + "/"
														+ contentStr[0];
											}
										} else if (cellValue.contains("-")) {
											String[] contentStr = cellValue
													.split("-");
											if (contentStr.length == 3
													&& contentStr[0].length() == 2
													&& contentStr[2].length() == 4) {
												cellValue = contentStr[2] + "-"
														+ contentStr[1] + "-"
														+ contentStr[0];
											}
										}
									}
								}
							} else {
								cellValue = row[j].getContents() == null ? ""
										: row[j].getContents();
							}
						} catch (Exception e) {
							cellValue = "";
						}

						aliasCellmap.put("#CELL" + j + "#",
								cellValue == null ? "" : cellValue.trim());
					} catch (Exception e) {
						e.printStackTrace();
						return token = 2;
					}
				}
				String aliasKeySql = "";
				String aliasValueSql = "";
				int initKey = 0;

				if (aliasValueMap != null && aliasTypeMap != null) {
					Set keySet = aliasValueMap.keySet();
					if (keySet != null) {
						Iterator it = keySet.iterator();
						while (it.hasNext()) {
							initKey++;
							Object keyValue = it.next();
							// 国际化，也就是汉字转代码，empid转personid 考勤转代码sql语句拼接
							if (keyValue != null && !keyValue.equals("")) {
								int fieldType = Integer.parseInt(aliasTypeMap
										.get(keyValue).toString());
								Object aliasCellmapValue = aliasCellmap
										.get(aliasValueMap.get(keyValue));
								if (aliasCellmapValue == null) {// 不属于excel的列
									aliasCellmapValue = aliasValueMap
											.get(keyValue);
									if (aliasCellmapValue != null
											&& aliasCellmapValue.toString()
													.contains("#CELL")) {
										String sqlContent = aliasCellmapValue
												.toString();
										for (int l = 0; l < init; l++) {
											sqlContent = sqlContent.replaceAll(
													"#CELL" + l + "#",
													aliasCellmap.get(
															"#CELL" + l + "#")
															.toString());
											if (!sqlContent.contains("#CELL")) {
												break;
											}
										}
										LinkedHashMap sqlI8nContentMap = new LinkedHashMap();

										switch (fieldType) {
										case 2:
											sqlI8nContentMap.put(
													"sqlI18nContent",
													sqlContent);
											aliasCellmapValue = this
													.getCodeNoByName(sqlI8nContentMap) == -1 ? ""
													: this
															.getCodeNoByName(sqlI8nContentMap);
											break;
										default:
											sqlI8nContentMap.put("sqlContent",
													sqlContent);
											if (this
													.getContentNoByFiled(sqlI8nContentMap) != null) {
												Object cellObj = this
														.getContentNoByFiled(
																sqlI8nContentMap)
														.size() == 0 ? ""
														: this
																.getContentNoByFiled(
																		sqlI8nContentMap)
																.get(0);
												if (cellObj != null
														&& !cellObj.equals("")
														&& ((Map) cellObj)
																.get(keyValue
																		.toString()) != null) {
													aliasCellmapValue = (cellObj == null ? ""
															: ((Map) cellObj)
																	.get(
																			keyValue
																					.toString()
																					.toUpperCase())
																	.toString());
												} else {
													aliasCellmapValue = "";
												}
											}

											break;
										}
									}
								} else {
									if (aliasValueI18nMap != null
											&& aliasValueI18nMap.get(keyValue) != null) {// 有需要从国际化表读取No的列
										String sqlContent = aliasValueI18nMap
												.get(keyValue) == null ? ""
												: aliasValueI18nMap.get(
														keyValue).toString();
										sqlContent = sqlContent
												.replaceAll(
														aliasValueMap
																.get(keyValue) == null ? ""
																: aliasValueMap
																		.get(
																				keyValue)
																		.toString(),
														"'"
																+ aliasCellmapValue
																		.toString()
																+ "'");

										LinkedHashMap sqlI8nContentMap = new LinkedHashMap();
										sqlI8nContentMap.put("sqlI18nContent",
												sqlContent);
										if (aliasValueI18nMap.get(keyValue
												+ "_FLAG") != null
												&& aliasValueI18nMap.get(
														keyValue + "_FLAG")
														.equals("Y")) {
											sqlI8nContentMap.put("sqlContent",
													sqlContent);
											List contentList = this
													.getContentNoByFiled(sqlI8nContentMap);
											if (contentList.get(0) != null
													&& ((Map) (contentList
															.get(0)))
															.get("CPNY_ID") != null) {
												aliasCellmapValue = (contentList == null ? ""
														: ((Map) (contentList
																.get(0))).get(
																"CPNY_ID")
																.toString());
											} else {
												aliasCellmapValue = "";
											}
										} else {
											aliasCellmapValue = this
													.getCodeNoByName(sqlI8nContentMap) == -1 ? ""
													: this
															.getCodeNoByName(sqlI8nContentMap);
										}

										if (aliasCellmapValue.equals("")) {
											System.out
													.println(aliasValueMap.get(
															keyValue)
															.toString()
															+ "-----------------------------------");
										}
									}
								}
								switch (fieldType) {
								case 0:
									// smap.put(keyValue.toString(),
									// "'"+aliasValueMap.get(keyValue)+"'");
									if (initKey == 1) {
										aliasKeySql = keyValue.toString();
										aliasValueSql = "'" + aliasCellmapValue
												+ "'";
									} else {
										aliasKeySql += ","
												+ keyValue.toString();
										aliasValueSql += ",'"
												+ aliasCellmapValue + "'";
									}
									break;
								case 1:
									// smap.put(keyValue.toString(),
									// "'"+aliasValueMap.get(keyValue)+"'");
									if (initKey == 1) {
										aliasKeySql = keyValue.toString();
										if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("/")) {
											aliasValueSql = "to_date('"
													+ aliasCellmapValue
													+ "','YYYY/MM/DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										} else if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("-")) {
											aliasValueSql = "to_date('"
													+ aliasCellmapValue
													+ "','YYYY-MM-DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										}
									} else {
										aliasKeySql += ","
												+ keyValue.toString();
										if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("/")) {
											aliasValueSql += ",to_date('"
													+ aliasCellmapValue
													+ "','YYYY/MM/DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										} else if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("-")) {
											aliasValueSql += ",to_date('"
													+ aliasCellmapValue
													+ "','YYYY-MM-DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										}
									}
									break;
								case 11:
									if (initKey == 1) {
										aliasKeySql = keyValue.toString();
										if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("/")) {
											aliasValueSql = "to_date('"
													+ aliasCellmapValue
													+ "','YYYY/MM/DD HH24:MI:SS')";// "'"+aliasCellmapValue+"'";
										} else if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("-")) {
											aliasValueSql = "to_date('"
													+ aliasCellmapValue
													+ "','YYYY-MM-DD HH24:MI:SS')";// "'"+aliasCellmapValue+"'";
										}
									} else {
										aliasKeySql += ","
												+ keyValue.toString();
										if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("/")) {
											aliasValueSql += ",to_date('"
													+ aliasCellmapValue
													+ "','YYYY/MM/DD HH24:MI:SS')";// "'"+aliasCellmapValue+"'";
										} else if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("-")) {
											aliasValueSql += ",to_date('"
													+ aliasCellmapValue
													+ "','YYYY-MM-DD HH24:MI:SS')";
										}
									}
									break;
								case 12:
									if (initKey == 1) {
										aliasKeySql = keyValue.toString();
										if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("/")) {
											aliasValueSql = "to_date('"
													+ aliasCellmapValue
													+ "','YYYY/MM/DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										} else if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("-")) {
											aliasValueSql = "to_date('"
													+ aliasCellmapValue
													+ "','YYYY-MM-DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										}
									} else {
										aliasKeySql += ","
												+ keyValue.toString();
										if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("/")) {
											aliasValueSql += ",to_date('"
													+ aliasCellmapValue
													+ "','YYYY/MM/DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										} else if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("-")) {
											aliasValueSql += ",to_date('"
													+ aliasCellmapValue
													+ "','YYYY-MM-DD HH24:MI')";
										}
									}
									break;
								default:
									// smap.put(keyValue.toString(),
									// aliasValueMap.get(keyValue));
									if (initKey == 1) {
										aliasKeySql = keyValue.toString();
										aliasValueSql = (aliasCellmapValue == null ? "null"
												: aliasCellmapValue.toString());
									} else {
										aliasKeySql += ","
												+ keyValue.toString();
										aliasValueSql += ","
												+ (aliasCellmapValue == null ? "null"
														: aliasCellmapValue
																.toString())
												+ "";
									}
									break;
								}
							}
						}
					}
				}
				smap.put("tableName", tableName);
				smap.put("aliasNameKeyContent", aliasKeySql);
				smap.put("aliasValueContent", aliasValueSql);
				addList.add(smap);
			}
			dataMap.put("addList", addList);
			// int result=this.insertExcelData(dataMap);
			this.insertExcelData(dataMap);
			// if(result==1){
			token = 1;
			// }else{
			// token = 0;
			// }
		} catch (Exception e) { // 未找到文件异常
			e.printStackTrace();
			token = 0;
		}
		return token;
	}

	public int deleteCordInfo(Object name) {

		try {

			this.excelUtilDao.deleteCordInfo(name);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}

		return 1;
	}

	public String getDeptNo(Object name) {
		return this.excelUtilDao.getDeptNo(name);
	}

	public int getColumnCnt(Object name) {
		return this.excelUtilDao.getColumnCnt(name);
	}

	/**
	 * 分析excel的各个列，获取各个列的值，并且导入的数据库中 Description:
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param modelMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ModelMap importArCardRecordData3(HttpServletRequest request,
			HttpServletResponse response, LinkedHashMap map, ModelMap modelMap) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String adminID = admin.getAdminID() == null ? "USERNO_"
				+ admin.getUserNo() : admin.getAdminID();
		ObjectBindUtil.getRequestParamData(request);
		item_no = request.getParameter("id");
		type = request.getParameter("type");
		this.filename = adminID;
		this.pathCpnyID = admin.getCpnyId();
		this.language = admin.getLanguage();
		if (this.processUploadFile(request, response)) {
			int returnInt = this
					.impArCardRecordDataBase3(request, adminID, map);
			if (returnInt == 0) {
				modelMap.put("sign", "0");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.importfail", request));// 导入失败!
			} else if (returnInt < 0) {
				modelMap.put("sign", "-1");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.leixingerror", request));// 类型有误，请按规则填写！
			} else if (returnInt == 2) {
				modelMap.put("sign", "2");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.existnull", request));// 表有空值或有空单元行，请检查！
			} else if (returnInt == 1) {
				modelMap.put("sign", "1");
				modelMap.put("statusCode", "200");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.importsuccess", request));// 导入成功
			} else if (returnInt == 7) {
				modelMap.put("sign", "3");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.deleteerror", request));// 删除历史数据出错,请联系管理员
			} else if (returnInt > 1000) {
				modelMap.put("sign", "3");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.title.di", request)
						+ String.valueOf(returnInt - 1000)
						+ TipMessage.getTipMessage(
								"ar.alert.message.excelimport.title.hang",
								request)
						+ TipMessage.getTipMessage(
								"ar.alert.message.excelimport.cardnoiserror",
								request));// 卡号有错误，请检查核对！
			}
			File file = new File(this.path + "\\" + filename + ".xls");
			file.delete();
		} else {
			modelMap.put("sign", "0");
			modelMap.put("statusCode", "300");
			modelMap.put("message", TipMessage.getTipMessage(
					"ar.alert.message.excelimport.importfail", request));// 导入失败!
		}
		return modelMap;
	}

	/**
	 * 分析excel的各个列，获取各个列的值，并且导入的数据库中 Description:
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param modelMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ModelMap importArCardRecordData4(HttpServletRequest request,
			HttpServletResponse response, LinkedHashMap map, ModelMap modelMap) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String adminID = admin.getAdminID() == null ? "USERNO_"
				+ admin.getUserNo() : admin.getAdminID();
		ObjectBindUtil.getRequestParamData(request);
		item_no = request.getParameter("id");
		type = request.getParameter("type");
		this.filename = adminID;
		this.pathCpnyID = admin.getCpnyId();
		this.language = admin.getLanguage();
		if (this.processUploadFile(request, response)) {
			int returnInt = this
					.impArCardRecordDataBase4(request, adminID, map);
			if (returnInt == 0) {
				modelMap.put("sign", "0");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.importfail", request));// 导入失败!
			} else if (returnInt < 0) {
				modelMap.put("sign", "-1");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.leixingerror", request));// 类型有误，请按规则填写！
			} else if (returnInt == 2) {
				modelMap.put("sign", "2");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.existnull", request));// 表有空值或有空单元行，请检查！
			} else if (returnInt == 1) {
				modelMap.put("sign", "1");
				modelMap.put("statusCode", "200");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.importsuccess", request));// 导入成功
			} else if (returnInt == 7) {
				modelMap.put("sign", "3");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.deleteerror", request));// 删除历史数据出错,请联系管理员
			} else if (returnInt > 1000) {
				modelMap.put("sign", "3");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.title.di", request)
						+ String.valueOf(returnInt - 1000)
						+ TipMessage.getTipMessage(
								"ar.alert.message.excelimport.title.hang",
								request)
						+ TipMessage.getTipMessage(
								"ar.alert.message.excelimport.cardnoiserror",
								request));// 卡号有错误，请检查核对！
			}
			File file = new File(this.path + "\\" + filename + ".xls");
			file.delete();
		} else {
			modelMap.put("sign", "0");
			modelMap.put("statusCode", "300");
			modelMap.put("message", TipMessage.getTipMessage(
					"ar.alert.message.excelimport.importfail", request));// 导入失败!
		}
		return modelMap;
	}

	@SuppressWarnings( { "unchecked" })
	public int impArCardRecordDataBase3(HttpServletRequest request,
			String adminID, LinkedHashMap cellmap) {
		int token = 0;

		int k = path.indexOf("\\");
		String server = "win";
		if (k < 0) { // WebServer的os为unix/linux
			server = "u/l";
		}

		File file = null;

		if ("win".equals(server)) {
			file = new File(path + "\\" + filename + ".xls");
		} else {
			file = new File(path + "/" + filename + ".xls");
		}

		Map dataMap = new HashMap();

		try {
			Workbook workBook = Workbook.getWorkbook(file);
			// 获得第一个工作表对象
			Sheet sheet = workBook.getSheet(0);
			// 验证是否存在导入数据
			if (sheet.getRows() < 2) {
				logger.debug("Import file not exist data, return.");
				return token;
			}

			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			List cordList = new ArrayList();

			// 检查卡号正确性
			for (int m = 1; m < sheet.getRows(); m++) {

				Cell[] rowTemp = sheet.getRow(m);
				String card_no = "";
				card_no = rowTemp[0].getContents().trim();

				if ("".equals(card_no)) {
					return 1000 + m;// 卡号为空
				}

				LinkedHashMap tempMap = new LinkedHashMap();
				tempMap.put("CARD_NO", card_no);
				tempMap.put("CPNY_ID", admin.getCpnyId());
				tempMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());
				tempMap.put("AR_MAC_RECORDS", "AR_MAC_RECORDS_"
						+ admin.getCpnyId());

				if (this.checkCardNo3(tempMap) == 0) {
					return 1000 + m;// 卡号有错误
				}
			}

			// 删除刷卡数据
			for (int m = 1; m < sheet.getRows(); m++) {

				Cell[] rowTemp = sheet.getRow(m);

				// 检查考勤是否锁定及在汇总表中检查数据是否已存在 存在则删除
				LinkedHashMap tempMap = new LinkedHashMap();
				tempMap.put("CARD_NO", rowTemp[0].getContents().trim());
				String cellValue = "";

				if (rowTemp[3] != null) {
					try {
						cellValue = this.FormateData(rowTemp[3], (Object) 11);
						if (cellValue.contains("/")) {
							String[] contentStr = cellValue.split("/");
							if (contentStr.length == 3
									&& contentStr[0].length() == 2
									&& contentStr[2].length() == 4) {
								cellValue = contentStr[2] + "/" + contentStr[1]
										+ "/" + contentStr[0];
							}
						} else if (cellValue.contains("-")) {
							String[] contentStr = cellValue.split("-");
							if (contentStr.length == 3
									&& contentStr[0].length() == 2
									&& contentStr[2].length() == 4) {
								cellValue = contentStr[2] + "-" + contentStr[1]
										+ "-" + contentStr[0];
							}
						}
					} catch (Exception e) {
						// cellValue=
						// row[j].getContents()==null?"":row[j].getContents();
						cellValue = "";
						if (rowTemp[3].getContents() != null) {
							cellValue = rowTemp[3].getContents();
							if (cellValue.contains("/")) {
								String[] contentStr = cellValue.split("/");
								if (contentStr.length == 3
										&& contentStr[0].length() == 2
										&& contentStr[2].length() == 4) {
									cellValue = contentStr[2] + "/"
											+ contentStr[1] + "/"
											+ contentStr[0];
								}
							} else if (cellValue.contains("-")) {
								String[] contentStr = cellValue.split("-");
								if (contentStr.length == 3
										&& contentStr[0].length() == 2
										&& contentStr[2].length() == 4) {
									cellValue = contentStr[2] + "-"
											+ contentStr[1] + "-"
											+ contentStr[0];
								}
							}
						}
					}
				} else {
					return 7;
				}
				tempMap.put("R_TIME", cellValue);
				tempMap.put("CPNY_ID", admin.getCpnyId());
				tempMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());
				tempMap.put("AR_MAC_RECORDS", "AR_MAC_RECORDS_"
						+ admin.getCpnyId());

				cordList.add(tempMap);
			}

			// 删除汇总表中数据
			// if (this.deleteCordInfoByCardNo(cordList) != 1) {
			// return 7;// 删除汇总历史数据出错,请联系管理员
			// }
			// 删除汇总表中数据
			if (this.deleteCordInfoByCardNo2(cordList) != 1) {
				return 7;// 删除汇总历史数据出错,请联系管理员
			}

			String tableName = cellmap.get("tableName") == null ? "" : cellmap
					.get("tableName").toString();// 获取表名

			LinkedHashMap aliasValueMap = cellmap.get("aliasValueMap") == null ? null
					: (LinkedHashMap) cellmap.get("aliasValueMap");
			LinkedHashMap aliasTypeMap = cellmap.get("aliasTypeMap") == null ? null
					: (LinkedHashMap) cellmap.get("aliasTypeMap");
			LinkedHashMap aliasValueI18nMap = cellmap.get("aliasValueI18nMap") == null ? null
					: (LinkedHashMap) cellmap.get("aliasValueI18nMap");

			LinkedHashMap aliasMapCells = new LinkedHashMap();
			if (aliasValueMap != null && aliasTypeMap != null) {// 存放CELL0：NUMBER类型的键值对
				Set keySet = aliasValueMap.keySet();
				if (keySet != null) {
					Iterator it = keySet.iterator();
					while (it.hasNext()) {
						Object keyvalue = it.next();
						if (keyvalue != null) {
							Object aliasValue = aliasValueMap.get(keyvalue);
							if (aliasValue != null) {
								aliasMapCells.put(aliasValue.toString(),
										aliasTypeMap.get(keyvalue));
							}
						}
					}
				}
			}
			List addList = new ArrayList();
			// 循环工作表的每行数据
			int init = 0;
			int init1 = 0;
			for (int i = 1; i < sheet.getRows(); i++) {
				// 验证当前行是否符合规范
				// if (!validateCell(sheet.getRow(i), sheet.getRow(0).length))
				// continue;
				// if (!validateCell(sheet.getRow(i),
				// sheet.getRow(0).length)){//如果所在行全为空，不导入
				// break;
				// }
				Cell[] row = sheet.getRow(i);

				if (init == 0) {
					// init=row.length;
					init = sheet.getRow(0).length;
				}

				if (init1 == 0) {
					// init=row.length;
					init1 = sheet.getRow(i).length;
				}

				LinkedHashMap smap = new LinkedHashMap();
				LinkedHashMap aliasCellmap = new LinkedHashMap();
				for (int j = 0; j < init; j++) {
					try {
						String cellValue = "";
						// cellValue= row[j].getContents();
						// aliasCellmap.put("#CELL"+j+"#", cellValue);
						Object cellType = null;
						cellType = aliasMapCells.get("#CELL" + j + "#");

						try {
							if (cellType != null
									&& !"".equals(row[j].getContents())) {// date类型
								try {
									cellValue = this.FormateData(row[j],
											cellType);
									if (cellValue.contains("/")) {
										String[] contentStr = cellValue
												.split("/");
										if (contentStr.length == 3
												&& contentStr[0].length() == 2
												&& contentStr[2].length() == 4) {
											cellValue = contentStr[2] + "/"
													+ contentStr[1] + "/"
													+ contentStr[0];
										}
									} else if (cellValue.contains("-")) {
										String[] contentStr = cellValue
												.split("-");
										if (contentStr.length == 3
												&& contentStr[0].length() == 2
												&& contentStr[2].length() == 4) {
											cellValue = contentStr[2] + "-"
													+ contentStr[1] + "-"
													+ contentStr[0];
										}
									}
								} catch (Exception e) {
									// cellValue=
									// row[j].getContents()==null?"":row[j].getContents();
									cellValue = "";
									if (row[j].getContents() != null) {
										cellValue = row[j].getContents();
										if (cellValue.contains("/")) {
											String[] contentStr = cellValue
													.split("/");
											if (contentStr.length == 3
													&& contentStr[0].length() == 2
													&& contentStr[2].length() == 4) {
												cellValue = contentStr[2] + "/"
														+ contentStr[1] + "/"
														+ contentStr[0];
											}
										} else if (cellValue.contains("-")) {
											String[] contentStr = cellValue
													.split("-");
											if (contentStr.length == 3
													&& contentStr[0].length() == 2
													&& contentStr[2].length() == 4) {
												cellValue = contentStr[2] + "-"
														+ contentStr[1] + "-"
														+ contentStr[0];
											}
										}
									}
								}
							} else {
								cellValue = row[j].getContents() == null ? ""
										: row[j].getContents();
							}
						} catch (Exception e) {
							cellValue = "";
						}
						aliasCellmap.put("#CELL" + j + "#",
								cellValue == null ? "" : cellValue.trim());
					} catch (Exception e) {
						e.printStackTrace();
						return token = 2;
					}
				}
				String aliasKeySql = "";
				String aliasValueSql = "";
				int initKey = 0;
				if (aliasValueMap != null && aliasTypeMap != null) {
					Set keySet = aliasValueMap.keySet();
					if (keySet != null) {
						Iterator it = keySet.iterator();
						while (it.hasNext()) {
							initKey++;
							Object keyValue = it.next();
							if (keyValue != null && !keyValue.equals("")) {
								int fieldType = Integer.parseInt(aliasTypeMap
										.get(keyValue).toString());
								Object aliasCellmapValue = aliasCellmap
										.get(aliasValueMap.get(keyValue));
								if (aliasCellmapValue == null) {// 不属于excel的列
									aliasCellmapValue = aliasValueMap
											.get(keyValue);
									if (aliasCellmapValue != null
											&& aliasCellmapValue.toString()
													.contains("#CELL")) {
										String sqlContent = aliasCellmapValue
												.toString();
										for (int l = 0; l < init; l++) {
											sqlContent = sqlContent.replaceAll(
													"#CELL" + l + "#",
													aliasCellmap.get(
															"#CELL" + l + "#")
															.toString());
											if (!sqlContent.contains("#CELL")) {
												break;
											}
										}
										LinkedHashMap sqlI8nContentMap = new LinkedHashMap();

										switch (fieldType) {
										case 2:
											sqlI8nContentMap.put(
													"sqlI18nContent",
													sqlContent);
											aliasCellmapValue = this
													.getCodeNoByName(sqlI8nContentMap) == -1 ? ""
													: this
															.getCodeNoByName(sqlI8nContentMap);
											break;
										default:
											sqlI8nContentMap.put("sqlContent",
													sqlContent);
											if (this
													.getContentNoByFiled(sqlI8nContentMap) != null) {
												Object cellObj = this
														.getContentNoByFiled(
																sqlI8nContentMap)
														.size() == 0 ? ""
														: this
																.getContentNoByFiled(
																		sqlI8nContentMap)
																.get(0);
												if (cellObj != null
														&& !cellObj.equals("")
														&& ((Map) cellObj)
																.get(keyValue
																		.toString()) != null) {
													aliasCellmapValue = (cellObj == null ? ""
															: ((Map) cellObj)
																	.get(
																			keyValue
																					.toString()
																					.toUpperCase())
																	.toString());
												} else {
													aliasCellmapValue = "";
												}
											}

											break;
										}
									}
								} else {
									if (aliasValueI18nMap != null
											&& aliasValueI18nMap.get(keyValue) != null) {// 有需要从国际化表读取No的列
										String sqlContent = aliasValueI18nMap
												.get(keyValue) == null ? ""
												: aliasValueI18nMap.get(
														keyValue).toString();
										sqlContent = sqlContent
												.replaceAll(
														aliasValueMap
																.get(keyValue) == null ? ""
																: aliasValueMap
																		.get(
																				keyValue)
																		.toString(),
														"'"
																+ aliasCellmapValue
																		.toString()
																+ "'");

										LinkedHashMap sqlI8nContentMap = new LinkedHashMap();
										sqlI8nContentMap.put("sqlI18nContent",
												sqlContent);
										if (aliasValueI18nMap.get(keyValue
												+ "_FLAG") != null
												&& aliasValueI18nMap.get(
														keyValue + "_FLAG")
														.equals("Y")) {
											sqlI8nContentMap.put("sqlContent",
													sqlContent);
											List contentList = this
													.getContentNoByFiled(sqlI8nContentMap);
											if (contentList.get(0) != null
													&& ((Map) (contentList
															.get(0)))
															.get("CPNY_ID") != null) {
												aliasCellmapValue = (contentList == null ? ""
														: ((Map) (contentList
																.get(0))).get(
																"CPNY_ID")
																.toString());
											} else {
												aliasCellmapValue = "";
											}
										} else {
											aliasCellmapValue = this
													.getCodeNoByName(sqlI8nContentMap) == -1 ? ""
													: this
															.getCodeNoByName(sqlI8nContentMap);
										}

										if (aliasCellmapValue.equals("")) {
											System.out
													.println(aliasValueMap.get(
															keyValue)
															.toString()
															+ "-----------------------------------");
										}
									}
								}
								switch (fieldType) {
								case 0:
									// smap.put(keyValue.toString(),
									// "'"+aliasValueMap.get(keyValue)+"'");
									if (initKey == 1) {
										aliasKeySql = keyValue.toString();
										aliasValueSql = "'" + aliasCellmapValue
												+ "'";
									} else {
										aliasKeySql += ","
												+ keyValue.toString();
										aliasValueSql += ",'"
												+ aliasCellmapValue + "'";
									}
									break;
								case 1:
									// smap.put(keyValue.toString(),
									// "'"+aliasValueMap.get(keyValue)+"'");
									if (initKey == 1) {
										aliasKeySql = keyValue.toString();
										if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("/")) {
											aliasValueSql = "to_date('"
													+ aliasCellmapValue
													+ "','YYYY/MM/DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										} else if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("-")) {
											aliasValueSql = "to_date('"
													+ aliasCellmapValue
													+ "','YYYY-MM-DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										}
									} else {
										aliasKeySql += ","
												+ keyValue.toString();
										if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("/")) {
											aliasValueSql += ",to_date('"
													+ aliasCellmapValue
													+ "','YYYY/MM/DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										} else if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("-")) {
											aliasValueSql += ",to_date('"
													+ aliasCellmapValue
													+ "','YYYY-MM-DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										}
									}
									break;
								case 11:
									if (initKey == 1) {
										aliasKeySql = keyValue.toString();
										if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("/")) {
											aliasValueSql = "to_date('"
													+ aliasCellmapValue
													+ "','YYYY/MM/DD HH24:MI:SS')";// "'"+aliasCellmapValue+"'";
										} else if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("-")) {
											aliasValueSql = "to_date('"
													+ aliasCellmapValue
													+ "','YYYY-MM-DD HH24:MI:SS')";// "'"+aliasCellmapValue+"'";
										}
									} else {
										aliasKeySql += ","
												+ keyValue.toString();
										if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("/")) {
											aliasValueSql += ",to_date('"
													+ aliasCellmapValue
													+ "','YYYY/MM/DD HH24:MI:SS')";// "'"+aliasCellmapValue+"'";
										} else if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("-")) {
											aliasValueSql += ",to_date('"
													+ aliasCellmapValue
													+ "','YYYY-MM-DD HH24:MI:SS')";
										}
									}
									break;
								case 12:
									if (initKey == 1) {
										aliasKeySql = keyValue.toString();
										if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("/")) {
											aliasValueSql = "to_date('"
													+ aliasCellmapValue
													+ "','YYYY/MM/DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										} else if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("-")) {
											aliasValueSql = "to_date('"
													+ aliasCellmapValue
													+ "','YYYY-MM-DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										}
									} else {
										aliasKeySql += ","
												+ keyValue.toString();
										if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("/")) {
											aliasValueSql += ",to_date('"
													+ aliasCellmapValue
													+ "','YYYY/MM/DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										} else if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("-")) {
											aliasValueSql += ",to_date('"
													+ aliasCellmapValue
													+ "','YYYY-MM-DD HH24:MI')";
										}
									}
									break;
								default:
									// smap.put(keyValue.toString(),
									// aliasValueMap.get(keyValue));
									if (initKey == 1) {
										aliasKeySql = keyValue.toString();
										aliasValueSql = (aliasCellmapValue == null ? "null"
												: aliasCellmapValue.toString());
									} else {
										aliasKeySql += ","
												+ keyValue.toString();
										aliasValueSql += ","
												+ (aliasCellmapValue == null ? "null"
														: aliasCellmapValue
																.toString())
												+ "";
									}
									break;
								}
							}
						}
					}
				}
				smap.put("tableName", tableName);
				smap.put("aliasNameKeyContent", aliasKeySql);
				smap.put("aliasValueContent", aliasValueSql);
				addList.add(smap);
			}
			dataMap.put("addList", addList);
			// int result=this.insertExcelData(dataMap);
			this.insertExcelData(dataMap);
			// if(result==1){
			token = 1;
			// }else{
			// token = 0;
			// }
		} catch (Exception e) { // 未找到文件异常
			e.printStackTrace();
			token = 0;
		}
		return token;
	}

	/**
	 * 派遣津贴标准导入
	 * 
	 * @param request
	 * @param adminID
	 * @param cellmap
	 * @return
	 */
	@SuppressWarnings( { "unchecked" })
	public int importPaiQianDiJinTieBiaoZhunInfoData(
			HttpServletRequest request, String adminID, LinkedHashMap cellmap) {
		int token = 0;
		int k = path.indexOf("\\");
		String server = "win";
		if (k < 0) { // WebServer的os为unix/linux
			server = "u/l";
		}
		File file = null;
		if ("win".equals(server)) {
			file = new File(path + "\\" + filename + ".xls");
		} else {
			file = new File(path + "/" + filename + ".xls");
		}
		Map dataMap = new HashMap();
		try {
			Workbook workBook = Workbook.getWorkbook(file);
			// 获得第一个工作表对象
			Sheet sheet = workBook.getSheet(0);
			// 验证是否存在导入数据
			if (sheet.getRows() < 2) {
				logger.debug("Import file not exist data, return.");
				return token;
			}
			String tableName = cellmap.get("tableName") == null ? "" : cellmap
					.get("tableName").toString();// 获取表名
			LinkedHashMap aliasValueMap = cellmap.get("aliasValueMap") == null ? null
					: (LinkedHashMap) cellmap.get("aliasValueMap");
			LinkedHashMap aliasTypeMap = cellmap.get("aliasTypeMap") == null ? null
					: (LinkedHashMap) cellmap.get("aliasTypeMap");
			LinkedHashMap aliasValueI18nMap = cellmap.get("aliasValueI18nMap") == null ? null
					: (LinkedHashMap) cellmap.get("aliasValueI18nMap");
			LinkedHashMap aliasMapCells = new LinkedHashMap();
			if (aliasValueMap != null && aliasTypeMap != null) {// 存放CELL0：NUMBER类型的键值对
				Set keySet = aliasValueMap.keySet();
				if (keySet != null) {
					Iterator it = keySet.iterator();
					while (it.hasNext()) {
						Object keyvalue = it.next();
						if (keyvalue != null) {
							Object aliasValue = aliasValueMap.get(keyvalue);
							if (aliasValue != null) {
								aliasMapCells.put(aliasValue.toString(),
										aliasTypeMap.get(keyvalue));
							}
						}
					}
				}
			}
			List addList = new ArrayList();
			// 循环工作表的每行数据
			int init = 0;
			int init1 = 0;
			for (int i = 1; i < sheet.getRows(); i++) {
				Cell[] row = sheet.getRow(i);
				if (init == 0) {
					init = sheet.getRow(0).length;
				}
				if (init1 == 0) {
					init1 = sheet.getRow(i).length;
				}
				LinkedHashMap smap = new LinkedHashMap();
				LinkedHashMap aliasCellmap = new LinkedHashMap();
				for (int j = 0; j < init; j++) {
					try {
						String cellValue = "";
						Object cellType = null;
						cellType = aliasMapCells.get("#CELL" + j + "#");
						try {
							if (cellType != null
									&& !"".equals(row[j].getContents())) {// date类型
								try {
									cellValue = this.FormateData(row[j],
											cellType);
									if (cellValue.contains("/")) {
										String[] contentStr = cellValue
												.split("/");
										if (contentStr.length == 3
												&& contentStr[0].length() == 2
												&& contentStr[2].length() == 4) {
											cellValue = contentStr[2] + "/"
													+ contentStr[1] + "/"
													+ contentStr[0];
										}
									} else if (cellValue.contains("-")) {
										String[] contentStr = cellValue
												.split("-");
										if (contentStr.length == 3
												&& contentStr[0].length() == 2
												&& contentStr[2].length() == 4) {
											cellValue = contentStr[2] + "-"
													+ contentStr[1] + "-"
													+ contentStr[0];
										}
									}
								} catch (Exception e) {
									cellValue = "";
									if (row[j].getContents() != null) {
										cellValue = row[j].getContents();
										if (cellValue.contains("/")) {
											String[] contentStr = cellValue
													.split("/");
											if (contentStr.length == 3
													&& contentStr[0].length() == 2
													&& contentStr[2].length() == 4) {
												cellValue = contentStr[2] + "/"
														+ contentStr[1] + "/"
														+ contentStr[0];
											}
										} else if (cellValue.contains("-")) {
											String[] contentStr = cellValue
													.split("-");
											if (contentStr.length == 3
													&& contentStr[0].length() == 2
													&& contentStr[2].length() == 4) {
												cellValue = contentStr[2] + "-"
														+ contentStr[1] + "-"
														+ contentStr[0];
											}
										}
									}
								}
							} else {
								cellValue = row[j].getContents() == null ? ""
										: row[j].getContents();
							}
						} catch (Exception e) {
							cellValue = "";
						}
						aliasCellmap.put("#CELL" + j + "#",
								cellValue == null ? "" : cellValue.trim());
					} catch (Exception e) {
						e.printStackTrace();
						return token = 2;
					}
				}
				String aliasKeySql = "";
				String aliasValueSql = "";
				int initKey = 0;
				if (aliasValueMap != null && aliasTypeMap != null) {
					Set keySet = aliasValueMap.keySet();
					if (keySet != null) {
						Iterator it = keySet.iterator();
						while (it.hasNext()) {
							initKey++;
							Object keyValue = it.next();
							if (keyValue != null && !keyValue.equals("")) {
								int fieldType = Integer.parseInt(aliasTypeMap
										.get(keyValue).toString());
								Object aliasCellmapValue = aliasCellmap
										.get(aliasValueMap.get(keyValue));
								if (aliasCellmapValue == null) {// 不属于excel的列
									aliasCellmapValue = aliasValueMap
											.get(keyValue);
									if (aliasCellmapValue != null
											&& aliasCellmapValue.toString()
													.contains("#CELL")) {
										String sqlContent = aliasCellmapValue
												.toString();
										for (int l = 0; l < init; l++) {
											sqlContent = sqlContent.replaceAll(
													"#CELL" + l + "#",
													aliasCellmap.get(
															"#CELL" + l + "#")
															.toString());
											if (!sqlContent.contains("#CELL")) {
												break;
											}
										}
										LinkedHashMap sqlI8nContentMap = new LinkedHashMap();

										switch (fieldType) {
										case 2:
											sqlI8nContentMap.put(
													"sqlI18nContent",
													sqlContent);
											aliasCellmapValue = this
													.getCodeNoByName(sqlI8nContentMap) == -1 ? ""
													: this
															.getCodeNoByName(sqlI8nContentMap);
											break;
										default:
											sqlI8nContentMap.put("sqlContent",
													sqlContent);
											if (this
													.getContentNoByFiled(sqlI8nContentMap) != null) {
												Object cellObj = this
														.getContentNoByFiled(
																sqlI8nContentMap)
														.size() == 0 ? ""
														: this
																.getContentNoByFiled(
																		sqlI8nContentMap)
																.get(0);
												if (cellObj != null
														&& !cellObj.equals("")
														&& ((Map) cellObj)
																.get(keyValue
																		.toString()) != null) {
													aliasCellmapValue = (cellObj == null ? ""
															: ((Map) cellObj)
																	.get(
																			keyValue
																					.toString()
																					.toUpperCase())
																	.toString());
												} else {
													aliasCellmapValue = "";
												}
											}

											break;
										}
									}
								} else {
									if (aliasValueI18nMap != null
											&& aliasValueI18nMap.get(keyValue) != null) {// 有需要从国际化表读取No的列
										String sqlContent = aliasValueI18nMap
												.get(keyValue) == null ? ""
												: aliasValueI18nMap.get(
														keyValue).toString();
										sqlContent = sqlContent
												.replaceAll(
														aliasValueMap
																.get(keyValue) == null ? ""
																: aliasValueMap
																		.get(
																				keyValue)
																		.toString(),
														"'"
																+ aliasCellmapValue
																		.toString()
																+ "'");

										LinkedHashMap sqlI8nContentMap = new LinkedHashMap();
										sqlI8nContentMap.put("sqlI18nContent",
												sqlContent);
										if (aliasValueI18nMap.get(keyValue
												+ "_FLAG") != null
												&& aliasValueI18nMap.get(
														keyValue + "_FLAG")
														.equals("Y")) {
											sqlI8nContentMap.put("sqlContent",
													sqlContent);
											List contentList = this
													.getContentNoByFiled(sqlI8nContentMap);
											if (contentList.get(0) != null
													&& ((Map) (contentList
															.get(0)))
															.get("CPNY_ID") != null) {
												aliasCellmapValue = (contentList == null ? ""
														: ((Map) (contentList
																.get(0))).get(
																"CPNY_ID")
																.toString());
											} else {
												aliasCellmapValue = "";
											}
										} else {
											aliasCellmapValue = this
													.getCodeNoByName(sqlI8nContentMap) == -1 ? ""
													: this
															.getCodeNoByName(sqlI8nContentMap);
										}

										if (aliasCellmapValue.equals("")) {
											System.out
													.println(aliasValueMap.get(
															keyValue)
															.toString()
															+ "-----------------------------------");
										}
									}
								}
								switch (fieldType) {
								case 0:
									if (initKey == 1) {
										aliasKeySql = keyValue.toString();
										aliasValueSql = "'" + aliasCellmapValue
												+ "'";
									} else {
										aliasKeySql += ","
												+ keyValue.toString();
										aliasValueSql += ",'"
												+ aliasCellmapValue + "'";
									}
									break;
								case 1:
									if (initKey == 1) {
										aliasKeySql = keyValue.toString();
										if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("/")) {
											aliasValueSql = "to_date('"
													+ aliasCellmapValue
													+ "','YYYY/MM/DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										} else if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("-")) {
											aliasValueSql = "to_date('"
													+ aliasCellmapValue
													+ "','YYYY-MM-DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										}
									} else {
										aliasKeySql += ","
												+ keyValue.toString();
										if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("/")) {
											aliasValueSql += ",to_date('"
													+ aliasCellmapValue
													+ "','YYYY/MM/DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										} else if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("-")) {
											aliasValueSql += ",to_date('"
													+ aliasCellmapValue
													+ "','YYYY-MM-DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										}
									}
									break;
								case 11:
									if (initKey == 1) {
										aliasKeySql = keyValue.toString();
										if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("/")) {
											aliasValueSql = "to_date('"
													+ aliasCellmapValue
													+ "','YYYY/MM/DD HH24:MI:SS')";// "'"+aliasCellmapValue+"'";
										} else if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("-")) {
											aliasValueSql = "to_date('"
													+ aliasCellmapValue
													+ "','YYYY-MM-DD HH24:MI:SS')";// "'"+aliasCellmapValue+"'";
										}
									} else {
										aliasKeySql += ","
												+ keyValue.toString();
										if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("/")) {
											aliasValueSql += ",to_date('"
													+ aliasCellmapValue
													+ "','YYYY/MM/DD HH24:MI:SS')";// "'"+aliasCellmapValue+"'";
										} else if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("-")) {
											aliasValueSql += ",to_date('"
													+ aliasCellmapValue
													+ "','YYYY-MM-DD HH24:MI:SS')";
										}
									}
									break;
								case 12:
									if (initKey == 1) {
										aliasKeySql = keyValue.toString();
										if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("/")) {
											aliasValueSql = "to_date('"
													+ aliasCellmapValue
													+ "','YYYY/MM/DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										} else if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("-")) {
											aliasValueSql = "to_date('"
													+ aliasCellmapValue
													+ "','YYYY-MM-DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										}
									} else {
										aliasKeySql += ","
												+ keyValue.toString();
										if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("/")) {
											aliasValueSql += ",to_date('"
													+ aliasCellmapValue
													+ "','YYYY/MM/DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										} else if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("-")) {
											aliasValueSql += ",to_date('"
													+ aliasCellmapValue
													+ "','YYYY-MM-DD HH24:MI')";
										}
									}
									break;
								default:
									if (initKey == 1) {
										aliasKeySql = keyValue.toString();
										aliasValueSql = (aliasCellmapValue == null ? "null"
												: aliasCellmapValue.toString());
									} else {
										aliasKeySql += ","
												+ keyValue.toString();
										aliasValueSql += ","
												+ (aliasCellmapValue == null ? "null"
														: aliasCellmapValue
																.toString())
												+ "";
									}
									break;
								}
							}
						}
					}
				}
				smap.put("tableName", tableName);
				smap.put("aliasNameKeyContent", aliasKeySql);
				smap.put("aliasValueContent", aliasValueSql);
				addList.add(smap);
			}
			dataMap.put("addList", addList);
			this.insertExcelData(dataMap);
			token = 1;
		} catch (Exception e) { // 未找到文件异常
			e.printStackTrace();
			token = 0;
		}
		return token;
	}

	@SuppressWarnings( { "unchecked" })
	public int importPaiQianDiInfoData(HttpServletRequest request,
			String adminID, LinkedHashMap cellmap) {
		int token = 0;

		int k = path.indexOf("\\");
		String server = "win";
		if (k < 0) { // WebServer的os为unix/linux
			server = "u/l";
		}

		File file = null;

		if ("win".equals(server)) {
			file = new File(path + "\\" + filename + ".xls");
		} else {
			file = new File(path + "/" + filename + ".xls");
		}

		Map dataMap = new HashMap();

		try {
			Workbook workBook = Workbook.getWorkbook(file);
			// 获得第一个工作表对象
			Sheet sheet = workBook.getSheet(0);
			// 验证是否存在导入数据
			if (sheet.getRows() < 2) {
				logger.debug("Import file not exist data, return.");
				return token;
			}

			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			List cordList = new ArrayList();

			// 检查卡号正确性
			for (int m = 1; m < sheet.getRows(); m++) {

				Cell[] rowTemp = sheet.getRow(m);
				String fr = "";// 法人
				String csdj = "";// 城市等级
				String sf = "";// 省份
				String csmc = "";// 城市名称
				String dqmc = "";// 地区名称

				fr = rowTemp[0].getContents().trim();
				csdj = rowTemp[1].getContents().trim();
				sf = rowTemp[2].getContents().trim();
				csmc = rowTemp[3].getContents().trim();
				dqmc = rowTemp[4].getContents().trim();

				if ("".equals(fr) || "".equals(csdj) || "".equals(sf)
						|| "".equals(csmc) || "".equals(csmc)) {
					return 10000 + m;// 不能为空
				}

				LinkedHashMap tempMap = new LinkedHashMap();
				tempMap.put("fr", fr);
				tempMap.put("csdj", csdj);
				tempMap.put("sf", sf);
				tempMap.put("csmc", csmc);
				tempMap.put("dqmc", dqmc);
				// 首先先检测要到的数据在系统中是否合理
				int intFr = this.checkPaiQianDiGuanLiFrInfo(tempMap);
				int intCsdj = this.checkPaiQianDiGuanLiCsdjInfo(tempMap);
				int intSf = this.checkPaiQianDiGuanLiSfInfo(tempMap);
				int intCsmc = this.checkPaiQianDiGuanLiCsmcInfo(tempMap);
				int intDqmc = this.checkPaiQianDiGuanLiDqmcInfo(tempMap);
			}
			String tableName = cellmap.get("tableName") == null ? "" : cellmap
					.get("tableName").toString();// 获取表名

			LinkedHashMap aliasValueMap = cellmap.get("aliasValueMap") == null ? null
					: (LinkedHashMap) cellmap.get("aliasValueMap");
			LinkedHashMap aliasTypeMap = cellmap.get("aliasTypeMap") == null ? null
					: (LinkedHashMap) cellmap.get("aliasTypeMap");
			LinkedHashMap aliasValueI18nMap = cellmap.get("aliasValueI18nMap") == null ? null
					: (LinkedHashMap) cellmap.get("aliasValueI18nMap");

			LinkedHashMap aliasMapCells = new LinkedHashMap();
			if (aliasValueMap != null && aliasTypeMap != null) {// 存放CELL0：NUMBER类型的键值对
				Set keySet = aliasValueMap.keySet();
				if (keySet != null) {
					Iterator it = keySet.iterator();
					while (it.hasNext()) {
						Object keyvalue = it.next();
						if (keyvalue != null) {
							Object aliasValue = aliasValueMap.get(keyvalue);
							if (aliasValue != null) {
								aliasMapCells.put(aliasValue.toString(),
										aliasTypeMap.get(keyvalue));
							}
						}
					}
				}
			}
			List addList = new ArrayList();
			// 循环工作表的每行数据
			int init = 0;
			int init1 = 0;
			for (int i = 1; i < sheet.getRows(); i++) {
				// 验证当前行是否符合规范
				// if (!validateCell(sheet.getRow(i), sheet.getRow(0).length))
				// continue;
				// if (!validateCell(sheet.getRow(i),
				// sheet.getRow(0).length)){//如果所在行全为空，不导入
				// break;
				// }
				Cell[] row = sheet.getRow(i);

				if (init == 0) {
					// init=row.length;
					init = sheet.getRow(0).length;
				}

				if (init1 == 0) {
					// init=row.length;
					init1 = sheet.getRow(i).length;
				}

				LinkedHashMap smap = new LinkedHashMap();
				LinkedHashMap aliasCellmap = new LinkedHashMap();
				for (int j = 0; j < init; j++) {
					try {
						String cellValue = "";
						// cellValue= row[j].getContents();
						// aliasCellmap.put("#CELL"+j+"#", cellValue);
						Object cellType = null;
						cellType = aliasMapCells.get("#CELL" + j + "#");

						try {
							if (cellType != null
									&& !"".equals(row[j].getContents())) {// date类型
								try {
									cellValue = this.FormateData(row[j],
											cellType);
									if (cellValue.contains("/")) {
										String[] contentStr = cellValue
												.split("/");
										if (contentStr.length == 3
												&& contentStr[0].length() == 2
												&& contentStr[2].length() == 4) {
											cellValue = contentStr[2] + "/"
													+ contentStr[1] + "/"
													+ contentStr[0];
										}
									} else if (cellValue.contains("-")) {
										String[] contentStr = cellValue
												.split("-");
										if (contentStr.length == 3
												&& contentStr[0].length() == 2
												&& contentStr[2].length() == 4) {
											cellValue = contentStr[2] + "-"
													+ contentStr[1] + "-"
													+ contentStr[0];
										}
									}
								} catch (Exception e) {
									// cellValue=
									// row[j].getContents()==null?"":row[j].getContents();
									cellValue = "";
									if (row[j].getContents() != null) {
										cellValue = row[j].getContents();
										if (cellValue.contains("/")) {
											String[] contentStr = cellValue
													.split("/");
											if (contentStr.length == 3
													&& contentStr[0].length() == 2
													&& contentStr[2].length() == 4) {
												cellValue = contentStr[2] + "/"
														+ contentStr[1] + "/"
														+ contentStr[0];
											}
										} else if (cellValue.contains("-")) {
											String[] contentStr = cellValue
													.split("-");
											if (contentStr.length == 3
													&& contentStr[0].length() == 2
													&& contentStr[2].length() == 4) {
												cellValue = contentStr[2] + "-"
														+ contentStr[1] + "-"
														+ contentStr[0];
											}
										}
									}
								}
							} else {
								cellValue = row[j].getContents() == null ? ""
										: row[j].getContents();
							}
						} catch (Exception e) {
							cellValue = "";
						}
						aliasCellmap.put("#CELL" + j + "#",
								cellValue == null ? "" : cellValue.trim());
					} catch (Exception e) {
						e.printStackTrace();
						return token = 2;
					}
				}
				String aliasKeySql = "";
				String aliasValueSql = "";
				int initKey = 0;
				if (aliasValueMap != null && aliasTypeMap != null) {
					Set keySet = aliasValueMap.keySet();
					if (keySet != null) {
						Iterator it = keySet.iterator();
						while (it.hasNext()) {
							initKey++;
							Object keyValue = it.next();
							if (keyValue != null && !keyValue.equals("")) {
								int fieldType = Integer.parseInt(aliasTypeMap
										.get(keyValue).toString());
								Object aliasCellmapValue = aliasCellmap
										.get(aliasValueMap.get(keyValue));
								if (aliasCellmapValue == null) {// 不属于excel的列
									aliasCellmapValue = aliasValueMap
											.get(keyValue);
									if (aliasCellmapValue != null
											&& aliasCellmapValue.toString()
													.contains("#CELL")) {
										String sqlContent = aliasCellmapValue
												.toString();
										for (int l = 0; l < init; l++) {
											sqlContent = sqlContent.replaceAll(
													"#CELL" + l + "#",
													aliasCellmap.get(
															"#CELL" + l + "#")
															.toString());
											if (!sqlContent.contains("#CELL")) {
												break;
											}
										}
										LinkedHashMap sqlI8nContentMap = new LinkedHashMap();

										switch (fieldType) {
										case 2:
											sqlI8nContentMap.put(
													"sqlI18nContent",
													sqlContent);
											aliasCellmapValue = this
													.getCodeNoByName(sqlI8nContentMap) == -1 ? ""
													: this
															.getCodeNoByName(sqlI8nContentMap);
											break;
										default:
											sqlI8nContentMap.put("sqlContent",
													sqlContent);
											if (this
													.getContentNoByFiled(sqlI8nContentMap) != null) {
												Object cellObj = this
														.getContentNoByFiled(
																sqlI8nContentMap)
														.size() == 0 ? ""
														: this
																.getContentNoByFiled(
																		sqlI8nContentMap)
																.get(0);
												if (cellObj != null
														&& !cellObj.equals("")
														&& ((Map) cellObj)
																.get(keyValue
																		.toString()) != null) {
													aliasCellmapValue = (cellObj == null ? ""
															: ((Map) cellObj)
																	.get(
																			keyValue
																					.toString()
																					.toUpperCase())
																	.toString());
												} else {
													aliasCellmapValue = "";
												}
											}

											break;
										}
									}
								} else {
									if (aliasValueI18nMap != null
											&& aliasValueI18nMap.get(keyValue) != null) {// 有需要从国际化表读取No的列
										String sqlContent = aliasValueI18nMap
												.get(keyValue) == null ? ""
												: aliasValueI18nMap.get(
														keyValue).toString();
										sqlContent = sqlContent
												.replaceAll(
														aliasValueMap
																.get(keyValue) == null ? ""
																: aliasValueMap
																		.get(
																				keyValue)
																		.toString(),
														"'"
																+ aliasCellmapValue
																		.toString()
																+ "'");

										LinkedHashMap sqlI8nContentMap = new LinkedHashMap();
										sqlI8nContentMap.put("sqlI18nContent",
												sqlContent);
										if (aliasValueI18nMap.get(keyValue
												+ "_FLAG") != null
												&& aliasValueI18nMap.get(
														keyValue + "_FLAG")
														.equals("Y")) {
											sqlI8nContentMap.put("sqlContent",
													sqlContent);
											List contentList = this
													.getContentNoByFiled(sqlI8nContentMap);
											if (contentList.get(0) != null
													&& ((Map) (contentList
															.get(0)))
															.get("CPNY_ID") != null) {
												aliasCellmapValue = (contentList == null ? ""
														: ((Map) (contentList
																.get(0))).get(
																"CPNY_ID")
																.toString());
											} else {
												aliasCellmapValue = "";
											}
										} else {
											aliasCellmapValue = this
													.getCodeNoByName(sqlI8nContentMap) == -1 ? ""
													: this
															.getCodeNoByName(sqlI8nContentMap);
										}

										if (aliasCellmapValue.equals("")) {
											System.out
													.println(aliasValueMap.get(
															keyValue)
															.toString()
															+ "-----------------------------------");
										}
									}
								}
								switch (fieldType) {
								case 0:
									// smap.put(keyValue.toString(),
									// "'"+aliasValueMap.get(keyValue)+"'");
									if (initKey == 1) {
										aliasKeySql = keyValue.toString();
										aliasValueSql = "'" + aliasCellmapValue
												+ "'";
									} else {
										aliasKeySql += ","
												+ keyValue.toString();
										aliasValueSql += ",'"
												+ aliasCellmapValue + "'";
									}
									break;
								case 1:
									// smap.put(keyValue.toString(),
									// "'"+aliasValueMap.get(keyValue)+"'");
									if (initKey == 1) {
										aliasKeySql = keyValue.toString();
										if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("/")) {
											aliasValueSql = "to_date('"
													+ aliasCellmapValue
													+ "','YYYY/MM/DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										} else if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("-")) {
											aliasValueSql = "to_date('"
													+ aliasCellmapValue
													+ "','YYYY-MM-DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										}
									} else {
										aliasKeySql += ","
												+ keyValue.toString();
										if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("/")) {
											aliasValueSql += ",to_date('"
													+ aliasCellmapValue
													+ "','YYYY/MM/DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										} else if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("-")) {
											aliasValueSql += ",to_date('"
													+ aliasCellmapValue
													+ "','YYYY-MM-DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										}
									}
									break;
								case 11:
									if (initKey == 1) {
										aliasKeySql = keyValue.toString();
										if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("/")) {
											aliasValueSql = "to_date('"
													+ aliasCellmapValue
													+ "','YYYY/MM/DD HH24:MI:SS')";// "'"+aliasCellmapValue+"'";
										} else if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("-")) {
											aliasValueSql = "to_date('"
													+ aliasCellmapValue
													+ "','YYYY-MM-DD HH24:MI:SS')";// "'"+aliasCellmapValue+"'";
										}
									} else {
										aliasKeySql += ","
												+ keyValue.toString();
										if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("/")) {
											aliasValueSql += ",to_date('"
													+ aliasCellmapValue
													+ "','YYYY/MM/DD HH24:MI:SS')";// "'"+aliasCellmapValue+"'";
										} else if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("-")) {
											aliasValueSql += ",to_date('"
													+ aliasCellmapValue
													+ "','YYYY-MM-DD HH24:MI:SS')";
										}
									}
									break;
								case 12:
									if (initKey == 1) {
										aliasKeySql = keyValue.toString();
										if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("/")) {
											aliasValueSql = "to_date('"
													+ aliasCellmapValue
													+ "','YYYY/MM/DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										} else if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("-")) {
											aliasValueSql = "to_date('"
													+ aliasCellmapValue
													+ "','YYYY-MM-DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										}
									} else {
										aliasKeySql += ","
												+ keyValue.toString();
										if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("/")) {
											aliasValueSql += ",to_date('"
													+ aliasCellmapValue
													+ "','YYYY/MM/DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										} else if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("-")) {
											aliasValueSql += ",to_date('"
													+ aliasCellmapValue
													+ "','YYYY-MM-DD HH24:MI')";
										}
									}
									break;
								default:
									// smap.put(keyValue.toString(),
									// aliasValueMap.get(keyValue));
									if (initKey == 1) {
										aliasKeySql = keyValue.toString();
										aliasValueSql = (aliasCellmapValue == null ? "null"
												: aliasCellmapValue.toString());
									} else {
										aliasKeySql += ","
												+ keyValue.toString();
										aliasValueSql += ","
												+ (aliasCellmapValue == null ? "null"
														: aliasCellmapValue
																.toString())
												+ "";
									}
									break;
								}
							}
						}
					}
				}
				smap.put("tableName", tableName);
				smap.put("aliasNameKeyContent", aliasKeySql);
				smap.put("aliasValueContent", aliasValueSql);
				addList.add(smap);
			}
			dataMap.put("addList", addList);
			// int result=this.insertExcelData(dataMap);
			this.insertExcelData(dataMap);
			// if(result==1){
			token = 1;
			// }else{
			// token = 0;
			// }
		} catch (Exception e) { // 未找到文件异常
			e.printStackTrace();
			token = 0;
		}
		return token;
	}

	@SuppressWarnings( { "unchecked" })
	public int importPaiQianDiInfoData(HttpServletRequest request,
			String adminID, LinkedHashMap cellmap, String checkResult) {
		int token = 0;

		int k = path.indexOf("\\");
		String server = "win";
		if (k < 0) { // WebServer的os为unix/linux
			server = "u/l";
		}

		File file = null;

		if ("win".equals(server)) {
			file = new File(path + "\\" + filename + ".xls");
		} else {
			file = new File(path + "/" + filename + ".xls");
		}

		Map dataMap = new HashMap();

		try {
			Workbook workBook = Workbook.getWorkbook(file);
			// 获得第一个工作表对象
			Sheet sheet = workBook.getSheet(0);
			// 验证是否存在导入数据
			if (sheet.getRows() < 2) {
				logger.debug("Import file not exist data, return.");
				return token;
			}

			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			List cordList = new ArrayList();

			// 检查卡号正确性
			for (int m = 1; m < sheet.getRows(); m++) {

				Cell[] rowTemp = sheet.getRow(m);
				String fr = "";// 法人
				String csdj = "";// 城市等级
				String sf = "";// 省份
				String csmc = "";// 城市名称
				String dqmc = "";// 地区名称

				fr = rowTemp[0].getContents().trim();
				csdj = rowTemp[1].getContents().trim();
				sf = rowTemp[2].getContents().trim();
				csmc = rowTemp[3].getContents().trim();
				dqmc = rowTemp[4].getContents().trim();

				if ("".equals(fr) || "".equals(csdj) || "".equals(sf)
						|| "".equals(csmc) || "".equals(csmc)) {
					return 10000 + m;// 不能为空
				}

				LinkedHashMap tempMap = new LinkedHashMap();
				tempMap.put("fr", fr);
				tempMap.put("csdj", csdj);
				tempMap.put("sf", sf);
				tempMap.put("csmc", csmc);
				tempMap.put("dqmc", dqmc);
				// 首先先检测要到的数据在系统中是否合理
				// int intFr = this.checkPaiQianDiGuanLiFrInfo(tempMap);
				// int intCsdj = this.checkPaiQianDiGuanLiCsdjInfo(tempMap);
				// int intSf = this.checkPaiQianDiGuanLiSfInfo(tempMap);
				// int intCsmc = this.checkPaiQianDiGuanLiCsmcInfo(tempMap);
				// int intDqmc = this.checkPaiQianDiGuanLiDqmcInfo(tempMap);
				// 如果导进来的这五个数据在表中已经存在就把之前的数据删掉，重新添加
				// int cunZaiFou = this.checkPaiQianDiGuanLiInfo(tempMap);
				// if(cunZaiFou > 0){
				// //存在话就删掉
				// int deleteResult = this.deletePaiQianDiGuanLiInfo(tempMap);
				// if(deleteResult == 0){
				// //删除失败
				// return 444;
				// }
				// }
			}

			// 删除刷卡数据
			// for (int m = 1; m < sheet.getRows(); m++) {
			//
			// Cell[] rowTemp = sheet.getRow(m);
			//
			// // 检查考勤是否锁定及在汇总表中检查数据是否已存在 存在则删除
			// LinkedHashMap tempMap = new LinkedHashMap();
			// tempMap.put("CARD_NO", rowTemp[0].getContents().trim());
			// String cellValue = "";
			//
			// if (rowTemp[3] != null) {
			// try {
			// cellValue = this.FormateData(rowTemp[3], (Object) 11);
			// if (cellValue.contains("/")) {
			// String[] contentStr = cellValue.split("/");
			// if (contentStr.length == 3
			// && contentStr[0].length() == 2
			// && contentStr[2].length() == 4) {
			// cellValue = contentStr[2] + "/" + contentStr[1]
			// + "/" + contentStr[0];
			// }
			// } else if (cellValue.contains("-")) {
			// String[] contentStr = cellValue.split("-");
			// if (contentStr.length == 3
			// && contentStr[0].length() == 2
			// && contentStr[2].length() == 4) {
			// cellValue = contentStr[2] + "-" + contentStr[1]
			// + "-" + contentStr[0];
			// }
			// }
			// } catch (Exception e) {
			// // cellValue=
			// // row[j].getContents()==null?"":row[j].getContents();
			// cellValue = "";
			// if (rowTemp[3].getContents() != null) {
			// cellValue = rowTemp[3].getContents();
			// if (cellValue.contains("/")) {
			// String[] contentStr = cellValue.split("/");
			// if (contentStr.length == 3
			// && contentStr[0].length() == 2
			// && contentStr[2].length() == 4) {
			// cellValue = contentStr[2] + "/"
			// + contentStr[1] + "/"
			// + contentStr[0];
			// }
			// } else if (cellValue.contains("-")) {
			// String[] contentStr = cellValue.split("-");
			// if (contentStr.length == 3
			// && contentStr[0].length() == 2
			// && contentStr[2].length() == 4) {
			// cellValue = contentStr[2] + "-"
			// + contentStr[1] + "-"
			// + contentStr[0];
			// }
			// }
			// }
			// }
			// } else {
			// return 7;
			// }
			// // tempMap.put("R_TIME", cellValue);
			// // tempMap.put("CPNY_ID", admin.getCpnyId());
			// // tempMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());
			// tempMap.put("SY_PAIQIANDI","SY_PAIQIANDI");
			//
			// cordList.add(tempMap);
			// }

			// 不用删除信息
			// if (this.deleteCordInfoByCardNo(cordList) != 1) {
			// return 7;// 删除汇总历史数据出错,请联系管理员
			// }

			String tableName = cellmap.get("tableName") == null ? "" : cellmap
					.get("tableName").toString();// 获取表名

			LinkedHashMap aliasValueMap = cellmap.get("aliasValueMap") == null ? null
					: (LinkedHashMap) cellmap.get("aliasValueMap");
			LinkedHashMap aliasTypeMap = cellmap.get("aliasTypeMap") == null ? null
					: (LinkedHashMap) cellmap.get("aliasTypeMap");
			LinkedHashMap aliasValueI18nMap = cellmap.get("aliasValueI18nMap") == null ? null
					: (LinkedHashMap) cellmap.get("aliasValueI18nMap");

			LinkedHashMap aliasMapCells = new LinkedHashMap();
			if (aliasValueMap != null && aliasTypeMap != null) {// 存放CELL0：NUMBER类型的键值对
				Set keySet = aliasValueMap.keySet();
				if (keySet != null) {
					Iterator it = keySet.iterator();
					while (it.hasNext()) {
						Object keyvalue = it.next();
						if (keyvalue != null) {
							Object aliasValue = aliasValueMap.get(keyvalue);
							if (aliasValue != null) {
								aliasMapCells.put(aliasValue.toString(),
										aliasTypeMap.get(keyvalue));
							}
						}
					}
				}
			}
			List addList = new ArrayList();
			// 循环工作表的每行数据
			int init = 0;
			int init1 = 0;
			for (int i = 1; i < sheet.getRows(); i++) {
				// 验证当前行是否符合规范
				// if (!validateCell(sheet.getRow(i), sheet.getRow(0).length))
				// continue;
				// if (!validateCell(sheet.getRow(i),
				// sheet.getRow(0).length)){//如果所在行全为空，不导入
				// break;
				// }
				Cell[] row = sheet.getRow(i);

				if (init == 0) {
					// init=row.length;
					init = sheet.getRow(0).length;
				}

				if (init1 == 0) {
					// init=row.length;
					init1 = sheet.getRow(i).length;
				}

				LinkedHashMap smap = new LinkedHashMap();
				LinkedHashMap aliasCellmap = new LinkedHashMap();
				for (int j = 0; j < init; j++) {
					try {
						String cellValue = "";
						// cellValue= row[j].getContents();
						// aliasCellmap.put("#CELL"+j+"#", cellValue);
						Object cellType = null;
						cellType = aliasMapCells.get("#CELL" + j + "#");

						try {
							if (cellType != null
									&& !"".equals(row[j].getContents())) {// date类型
								try {
									cellValue = this.FormateData(row[j],
											cellType);
									if (cellValue.contains("/")) {
										String[] contentStr = cellValue
												.split("/");
										if (contentStr.length == 3
												&& contentStr[0].length() == 2
												&& contentStr[2].length() == 4) {
											cellValue = contentStr[2] + "/"
													+ contentStr[1] + "/"
													+ contentStr[0];
										}
									} else if (cellValue.contains("-")) {
										String[] contentStr = cellValue
												.split("-");
										if (contentStr.length == 3
												&& contentStr[0].length() == 2
												&& contentStr[2].length() == 4) {
											cellValue = contentStr[2] + "-"
													+ contentStr[1] + "-"
													+ contentStr[0];
										}
									}
								} catch (Exception e) {
									// cellValue=
									// row[j].getContents()==null?"":row[j].getContents();
									cellValue = "";
									if (row[j].getContents() != null) {
										cellValue = row[j].getContents();
										if (cellValue.contains("/")) {
											String[] contentStr = cellValue
													.split("/");
											if (contentStr.length == 3
													&& contentStr[0].length() == 2
													&& contentStr[2].length() == 4) {
												cellValue = contentStr[2] + "/"
														+ contentStr[1] + "/"
														+ contentStr[0];
											}
										} else if (cellValue.contains("-")) {
											String[] contentStr = cellValue
													.split("-");
											if (contentStr.length == 3
													&& contentStr[0].length() == 2
													&& contentStr[2].length() == 4) {
												cellValue = contentStr[2] + "-"
														+ contentStr[1] + "-"
														+ contentStr[0];
											}
										}
									}
								}
							} else {
								cellValue = row[j].getContents() == null ? ""
										: row[j].getContents();
							}
						} catch (Exception e) {
							cellValue = "";
						}
						aliasCellmap.put("#CELL" + j + "#",
								cellValue == null ? "" : cellValue.trim());
					} catch (Exception e) {
						e.printStackTrace();
						return token = 2;
					}
				}
				String aliasKeySql = "";
				String aliasValueSql = "";
				int initKey = 0;
				if (aliasValueMap != null && aliasTypeMap != null) {
					Set keySet = aliasValueMap.keySet();
					if (keySet != null) {
						Iterator it = keySet.iterator();
						while (it.hasNext()) {
							initKey++;
							Object keyValue = it.next();
							if (keyValue != null && !keyValue.equals("")) {
								int fieldType = Integer.parseInt(aliasTypeMap
										.get(keyValue).toString());
								Object aliasCellmapValue = aliasCellmap
										.get(aliasValueMap.get(keyValue));
								if (aliasCellmapValue == null) {// 不属于excel的列
									aliasCellmapValue = aliasValueMap
											.get(keyValue);
									if (aliasCellmapValue != null
											&& aliasCellmapValue.toString()
													.contains("#CELL")) {
										String sqlContent = aliasCellmapValue
												.toString();
										for (int l = 0; l < init; l++) {
											sqlContent = sqlContent.replaceAll(
													"#CELL" + l + "#",
													aliasCellmap.get(
															"#CELL" + l + "#")
															.toString());
											if (!sqlContent.contains("#CELL")) {
												break;
											}
										}
										LinkedHashMap sqlI8nContentMap = new LinkedHashMap();

										switch (fieldType) {
										case 2:
											sqlI8nContentMap.put(
													"sqlI18nContent",
													sqlContent);
											aliasCellmapValue = this
													.getCodeNoByName(sqlI8nContentMap) == -1 ? ""
													: this
															.getCodeNoByName(sqlI8nContentMap);
											break;
										default:
											sqlI8nContentMap.put("sqlContent",
													sqlContent);
											if (this
													.getContentNoByFiled(sqlI8nContentMap) != null) {
												Object cellObj = this
														.getContentNoByFiled(
																sqlI8nContentMap)
														.size() == 0 ? ""
														: this
																.getContentNoByFiled(
																		sqlI8nContentMap)
																.get(0);
												if (cellObj != null
														&& !cellObj.equals("")
														&& ((Map) cellObj)
																.get(keyValue
																		.toString()) != null) {
													aliasCellmapValue = (cellObj == null ? ""
															: ((Map) cellObj)
																	.get(
																			keyValue
																					.toString()
																					.toUpperCase())
																	.toString());
												} else {
													aliasCellmapValue = "";
												}
											}

											break;
										}
									}
								} else {
									if (aliasValueI18nMap != null
											&& aliasValueI18nMap.get(keyValue) != null) {// 有需要从国际化表读取No的列
										String sqlContent = aliasValueI18nMap
												.get(keyValue) == null ? ""
												: aliasValueI18nMap.get(
														keyValue).toString();
										sqlContent = sqlContent
												.replaceAll(
														aliasValueMap
																.get(keyValue) == null ? ""
																: aliasValueMap
																		.get(
																				keyValue)
																		.toString(),
														"'"
																+ aliasCellmapValue
																		.toString()
																+ "'");

										LinkedHashMap sqlI8nContentMap = new LinkedHashMap();
										sqlI8nContentMap.put("sqlI18nContent",
												sqlContent);
										if (aliasValueI18nMap.get(keyValue
												+ "_FLAG") != null
												&& aliasValueI18nMap.get(
														keyValue + "_FLAG")
														.equals("Y")) {
											sqlI8nContentMap.put("sqlContent",
													sqlContent);
											List contentList = this
													.getContentNoByFiled(sqlI8nContentMap);
											if (contentList.get(0) != null
													&& ((Map) (contentList
															.get(0)))
															.get("CPNY_ID") != null) {
												aliasCellmapValue = (contentList == null ? ""
														: ((Map) (contentList
																.get(0))).get(
																"CPNY_ID")
																.toString());
											} else {
												aliasCellmapValue = "";
											}
										} else {
											aliasCellmapValue = this
													.getCodeNoByName(sqlI8nContentMap) == -1 ? ""
													: this
															.getCodeNoByName(sqlI8nContentMap);
										}

										if (aliasCellmapValue.equals("")) {
											System.out
													.println(aliasValueMap.get(
															keyValue)
															.toString()
															+ "-----------------------------------");
										}
									}
								}
								switch (fieldType) {
								case 0:
									// smap.put(keyValue.toString(),
									// "'"+aliasValueMap.get(keyValue)+"'");
									if (initKey == 1) {
										aliasKeySql = keyValue.toString();
										aliasValueSql = "'" + aliasCellmapValue
												+ "'";
									} else {
										aliasKeySql += ","
												+ keyValue.toString();
										aliasValueSql += ",'"
												+ aliasCellmapValue + "'";
									}
									break;
								case 1:
									// smap.put(keyValue.toString(),
									// "'"+aliasValueMap.get(keyValue)+"'");
									if (initKey == 1) {
										aliasKeySql = keyValue.toString();
										if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("/")) {
											aliasValueSql = "to_date('"
													+ aliasCellmapValue
													+ "','YYYY/MM/DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										} else if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("-")) {
											aliasValueSql = "to_date('"
													+ aliasCellmapValue
													+ "','YYYY-MM-DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										}
									} else {
										aliasKeySql += ","
												+ keyValue.toString();
										if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("/")) {
											aliasValueSql += ",to_date('"
													+ aliasCellmapValue
													+ "','YYYY/MM/DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										} else if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("-")) {
											aliasValueSql += ",to_date('"
													+ aliasCellmapValue
													+ "','YYYY-MM-DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										}
									}
									break;
								case 11:
									if (initKey == 1) {
										aliasKeySql = keyValue.toString();
										if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("/")) {
											aliasValueSql = "to_date('"
													+ aliasCellmapValue
													+ "','YYYY/MM/DD HH24:MI:SS')";// "'"+aliasCellmapValue+"'";
										} else if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("-")) {
											aliasValueSql = "to_date('"
													+ aliasCellmapValue
													+ "','YYYY-MM-DD HH24:MI:SS')";// "'"+aliasCellmapValue+"'";
										}
									} else {
										aliasKeySql += ","
												+ keyValue.toString();
										if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("/")) {
											aliasValueSql += ",to_date('"
													+ aliasCellmapValue
													+ "','YYYY/MM/DD HH24:MI:SS')";// "'"+aliasCellmapValue+"'";
										} else if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("-")) {
											aliasValueSql += ",to_date('"
													+ aliasCellmapValue
													+ "','YYYY-MM-DD HH24:MI:SS')";
										}
									}
									break;
								case 12:
									if (initKey == 1) {
										aliasKeySql = keyValue.toString();
										if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("/")) {
											aliasValueSql = "to_date('"
													+ aliasCellmapValue
													+ "','YYYY/MM/DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										} else if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("-")) {
											aliasValueSql = "to_date('"
													+ aliasCellmapValue
													+ "','YYYY-MM-DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										}
									} else {
										aliasKeySql += ","
												+ keyValue.toString();
										if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("/")) {
											aliasValueSql += ",to_date('"
													+ aliasCellmapValue
													+ "','YYYY/MM/DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										} else if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("-")) {
											aliasValueSql += ",to_date('"
													+ aliasCellmapValue
													+ "','YYYY-MM-DD HH24:MI')";
										}
									}
									break;
								default:
									// smap.put(keyValue.toString(),
									// aliasValueMap.get(keyValue));
									if (initKey == 1) {
										aliasKeySql = keyValue.toString();
										aliasValueSql = (aliasCellmapValue == null ? "null"
												: aliasCellmapValue.toString());
									} else {
										aliasKeySql += ","
												+ keyValue.toString();
										aliasValueSql += ","
												+ (aliasCellmapValue == null ? "null"
														: aliasCellmapValue
																.toString())
												+ "";
									}
									break;
								}
							}
						}
					}
				}
				smap.put("tableName", tableName);
				smap.put("aliasNameKeyContent", aliasKeySql);
				smap.put("aliasValueContent", aliasValueSql);
				addList.add(smap);
			}
			dataMap.put("addList", addList);
			// int result=this.insertExcelData(dataMap);
			this.insertExcelData(dataMap);
			// if(result==1){
			token = 1;
			// }else{
			// token = 0;
			// }
		} catch (Exception e) { // 未找到文件异常
			e.printStackTrace();
			token = 0;
		}
		return token;
	}

	@SuppressWarnings( { "unchecked" })
	public int impArCardRecordDataBase4(HttpServletRequest request,
			String adminID, LinkedHashMap cellmap) {
		int token = 0;

		int k = path.indexOf("\\");
		String server = "win";
		if (k < 0) { // WebServer的os为unix/linux
			server = "u/l";
		}

		File file = null;

		if ("win".equals(server)) {
			file = new File(path + "\\" + filename + ".xls");
		} else {
			file = new File(path + "/" + filename + ".xls");
		}

		Map dataMap = new HashMap();

		try {
			Workbook workBook = Workbook.getWorkbook(file);
			// 获得第一个工作表对象
			Sheet sheet = workBook.getSheet(0);
			// 验证是否存在导入数据
			if (sheet.getRows() < 2) {
				logger.debug("Import file not exist data, return.");
				return token;
			}

			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			List cordList = new ArrayList();

			// 检查卡号正确性
			for (int m = 1; m < sheet.getRows(); m++) {

				Cell[] rowTemp = sheet.getRow(m);
				String card_no = "";
				card_no = rowTemp[0].getContents().trim();

				if ("".equals(card_no)) {
					return 1000 + m;// 卡号为空
				}

				LinkedHashMap tempMap = new LinkedHashMap();
				tempMap.put("CARD_NO", card_no);
				tempMap.put("CPNY_ID", admin.getCpnyId());
				tempMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());
				tempMap.put("AR_MAC_RECORDS", "AR_MAC_RECORDS_"
						+ admin.getCpnyId());

				if (this.checkCardNo2(tempMap) == 0) {
					return 1000 + m;// 卡号有错误
				}
			}

			// 删除刷卡数据
			for (int m = 1; m < sheet.getRows(); m++) {

				Cell[] rowTemp = sheet.getRow(m);

				// 检查考勤是否锁定及在汇总表中检查数据是否已存在 存在则删除
				LinkedHashMap tempMap = new LinkedHashMap();
				tempMap.put("CARD_NO", rowTemp[0].getContents().trim());
				String cellValue = "";

				if (rowTemp[3] != null) {
					try {
						cellValue = this.FormateData(rowTemp[3], (Object) 11);
						if (cellValue.contains("/")) {
							String[] contentStr = cellValue.split("/");
							if (contentStr.length == 3
									&& contentStr[0].length() == 2
									&& contentStr[2].length() == 4) {
								cellValue = contentStr[2] + "/" + contentStr[1]
										+ "/" + contentStr[0];
							}
						} else if (cellValue.contains("-")) {
							String[] contentStr = cellValue.split("-");
							if (contentStr.length == 3
									&& contentStr[0].length() == 2
									&& contentStr[2].length() == 4) {
								cellValue = contentStr[2] + "-" + contentStr[1]
										+ "-" + contentStr[0];
							}
						}
					} catch (Exception e) {
						// cellValue=
						// row[j].getContents()==null?"":row[j].getContents();
						cellValue = "";
						if (rowTemp[3].getContents() != null) {
							cellValue = rowTemp[3].getContents();
							if (cellValue.contains("/")) {
								String[] contentStr = cellValue.split("/");
								if (contentStr.length == 3
										&& contentStr[0].length() == 2
										&& contentStr[2].length() == 4) {
									cellValue = contentStr[2] + "/"
											+ contentStr[1] + "/"
											+ contentStr[0];
								}
							} else if (cellValue.contains("-")) {
								String[] contentStr = cellValue.split("-");
								if (contentStr.length == 3
										&& contentStr[0].length() == 2
										&& contentStr[2].length() == 4) {
									cellValue = contentStr[2] + "-"
											+ contentStr[1] + "-"
											+ contentStr[0];
								}
							}
						}
					}
				} else {
					return 7;
				}
				tempMap.put("R_TIME", cellValue);
				tempMap.put("CPNY_ID", admin.getCpnyId());
				tempMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());
				tempMap.put("AR_MAC_RECORDS", "AR_MAC_RECORDS_"
						+ admin.getCpnyId());

				cordList.add(tempMap);
			}

			// 删除汇总表中数据
			if (this.deleteCordInfoByCardNo(cordList) != 1) {
				return 7;// 删除汇总历史数据出错,请联系管理员
			}

			String tableName = cellmap.get("tableName") == null ? "" : cellmap
					.get("tableName").toString();// 获取表名

			LinkedHashMap aliasValueMap = cellmap.get("aliasValueMap") == null ? null
					: (LinkedHashMap) cellmap.get("aliasValueMap");
			LinkedHashMap aliasTypeMap = cellmap.get("aliasTypeMap") == null ? null
					: (LinkedHashMap) cellmap.get("aliasTypeMap");
			LinkedHashMap aliasValueI18nMap = cellmap.get("aliasValueI18nMap") == null ? null
					: (LinkedHashMap) cellmap.get("aliasValueI18nMap");

			LinkedHashMap aliasMapCells = new LinkedHashMap();
			if (aliasValueMap != null && aliasTypeMap != null) {// 存放CELL0：NUMBER类型的键值对
				Set keySet = aliasValueMap.keySet();
				if (keySet != null) {
					Iterator it = keySet.iterator();
					while (it.hasNext()) {
						Object keyvalue = it.next();
						if (keyvalue != null) {
							Object aliasValue = aliasValueMap.get(keyvalue);
							if (aliasValue != null) {
								aliasMapCells.put(aliasValue.toString(),
										aliasTypeMap.get(keyvalue));
							}
						}
					}
				}
			}
			List addList = new ArrayList();
			// 循环工作表的每行数据
			int init = 0;
			int init1 = 0;
			for (int i = 1; i < sheet.getRows(); i++) {
				// 验证当前行是否符合规范
				// if (!validateCell(sheet.getRow(i), sheet.getRow(0).length))
				// continue;
				// if (!validateCell(sheet.getRow(i),
				// sheet.getRow(0).length)){//如果所在行全为空，不导入
				// break;
				// }
				Cell[] row = sheet.getRow(i);

				if (init == 0) {
					// init=row.length;
					init = sheet.getRow(0).length;
				}

				if (init1 == 0) {
					// init=row.length;
					init1 = sheet.getRow(i).length;
				}

				LinkedHashMap smap = new LinkedHashMap();
				LinkedHashMap aliasCellmap = new LinkedHashMap();
				for (int j = 0; j < init; j++) {
					try {
						String cellValue = "";
						// cellValue= row[j].getContents();
						// aliasCellmap.put("#CELL"+j+"#", cellValue);
						Object cellType = null;
						cellType = aliasMapCells.get("#CELL" + j + "#");

						try {
							if (cellType != null
									&& !"".equals(row[j].getContents())) {// date类型
								try {
									cellValue = this.FormateData(row[j],
											cellType);
									if (cellValue.contains("/")) {
										String[] contentStr = cellValue
												.split("/");
										if (contentStr.length == 3
												&& contentStr[0].length() == 2
												&& contentStr[2].length() == 4) {
											cellValue = contentStr[2] + "/"
													+ contentStr[1] + "/"
													+ contentStr[0];
										}
									} else if (cellValue.contains("-")) {
										String[] contentStr = cellValue
												.split("-");
										if (contentStr.length == 3
												&& contentStr[0].length() == 2
												&& contentStr[2].length() == 4) {
											cellValue = contentStr[2] + "-"
													+ contentStr[1] + "-"
													+ contentStr[0];
										}
									}
								} catch (Exception e) {
									// cellValue=
									// row[j].getContents()==null?"":row[j].getContents();
									cellValue = "";
									if (row[j].getContents() != null) {
										cellValue = row[j].getContents();
										if (cellValue.contains("/")) {
											String[] contentStr = cellValue
													.split("/");
											if (contentStr.length == 3
													&& contentStr[0].length() == 2
													&& contentStr[2].length() == 4) {
												cellValue = contentStr[2] + "/"
														+ contentStr[1] + "/"
														+ contentStr[0];
											}
										} else if (cellValue.contains("-")) {
											String[] contentStr = cellValue
													.split("-");
											if (contentStr.length == 3
													&& contentStr[0].length() == 2
													&& contentStr[2].length() == 4) {
												cellValue = contentStr[2] + "-"
														+ contentStr[1] + "-"
														+ contentStr[0];
											}
										}
									}
								}
							} else {
								cellValue = row[j].getContents() == null ? ""
										: row[j].getContents();
							}
						} catch (Exception e) {
							cellValue = "";
						}
						aliasCellmap.put("#CELL" + j + "#",
								cellValue == null ? "" : cellValue.trim());
					} catch (Exception e) {
						e.printStackTrace();
						return token = 2;
					}
				}
				String aliasKeySql = "";
				String aliasValueSql = "";
				int initKey = 0;
				if (aliasValueMap != null && aliasTypeMap != null) {
					Set keySet = aliasValueMap.keySet();
					if (keySet != null) {
						Iterator it = keySet.iterator();
						while (it.hasNext()) {
							initKey++;
							Object keyValue = it.next();
							if (keyValue != null && !keyValue.equals("")) {
								int fieldType = Integer.parseInt(aliasTypeMap
										.get(keyValue).toString());
								Object aliasCellmapValue = aliasCellmap
										.get(aliasValueMap.get(keyValue));
								if (aliasCellmapValue == null) {// 不属于excel的列
									aliasCellmapValue = aliasValueMap
											.get(keyValue);
									if (aliasCellmapValue != null
											&& aliasCellmapValue.toString()
													.contains("#CELL")) {
										String sqlContent = aliasCellmapValue
												.toString();
										for (int l = 0; l < init; l++) {
											sqlContent = sqlContent.replaceAll(
													"#CELL" + l + "#",
													aliasCellmap.get(
															"#CELL" + l + "#")
															.toString());
											if (!sqlContent.contains("#CELL")) {
												break;
											}
										}
										LinkedHashMap sqlI8nContentMap = new LinkedHashMap();

										switch (fieldType) {
										case 2:
											sqlI8nContentMap.put(
													"sqlI18nContent",
													sqlContent);
											aliasCellmapValue = this
													.getCodeNoByName(sqlI8nContentMap) == -1 ? ""
													: this
															.getCodeNoByName(sqlI8nContentMap);
											break;
										default:
											sqlI8nContentMap.put("sqlContent",
													sqlContent);
											if (this
													.getContentNoByFiled(sqlI8nContentMap) != null) {
												Object cellObj = this
														.getContentNoByFiled(
																sqlI8nContentMap)
														.size() == 0 ? ""
														: this
																.getContentNoByFiled(
																		sqlI8nContentMap)
																.get(0);
												if (cellObj != null
														&& !cellObj.equals("")
														&& ((Map) cellObj)
																.get(keyValue
																		.toString()) != null) {
													aliasCellmapValue = (cellObj == null ? ""
															: ((Map) cellObj)
																	.get(
																			keyValue
																					.toString()
																					.toUpperCase())
																	.toString());
												} else {
													aliasCellmapValue = "";
												}
											}

											break;
										}
									}
								} else {
									if (aliasValueI18nMap != null
											&& aliasValueI18nMap.get(keyValue) != null) {// 有需要从国际化表读取No的列
										String sqlContent = aliasValueI18nMap
												.get(keyValue) == null ? ""
												: aliasValueI18nMap.get(
														keyValue).toString();
										sqlContent = sqlContent
												.replaceAll(
														aliasValueMap
																.get(keyValue) == null ? ""
																: aliasValueMap
																		.get(
																				keyValue)
																		.toString(),
														"'"
																+ aliasCellmapValue
																		.toString()
																+ "'");

										LinkedHashMap sqlI8nContentMap = new LinkedHashMap();
										sqlI8nContentMap.put("sqlI18nContent",
												sqlContent);
										if (aliasValueI18nMap.get(keyValue
												+ "_FLAG") != null
												&& aliasValueI18nMap.get(
														keyValue + "_FLAG")
														.equals("Y")) {
											sqlI8nContentMap.put("sqlContent",
													sqlContent);
											List contentList = this
													.getContentNoByFiled(sqlI8nContentMap);
											if (contentList.get(0) != null
													&& ((Map) (contentList
															.get(0)))
															.get("CPNY_ID") != null) {
												aliasCellmapValue = (contentList == null ? ""
														: ((Map) (contentList
																.get(0))).get(
																"CPNY_ID")
																.toString());
											} else {
												aliasCellmapValue = "";
											}
										} else {
											aliasCellmapValue = this
													.getCodeNoByName(sqlI8nContentMap) == -1 ? ""
													: this
															.getCodeNoByName(sqlI8nContentMap);
										}

										if (aliasCellmapValue.equals("")) {
											System.out
													.println(aliasValueMap.get(
															keyValue)
															.toString()
															+ "-----------------------------------");
										}
									}
								}
								switch (fieldType) {
								case 0:
									// smap.put(keyValue.toString(),
									// "'"+aliasValueMap.get(keyValue)+"'");
									if (initKey == 1) {
										aliasKeySql = keyValue.toString();
										aliasValueSql = "'" + aliasCellmapValue
												+ "'";
									} else {
										aliasKeySql += ","
												+ keyValue.toString();
										aliasValueSql += ",'"
												+ aliasCellmapValue + "'";
									}
									break;
								case 1:
									// smap.put(keyValue.toString(),
									// "'"+aliasValueMap.get(keyValue)+"'");
									if (initKey == 1) {
										aliasKeySql = keyValue.toString();
										if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("/")) {
											aliasValueSql = "to_date('"
													+ aliasCellmapValue
													+ "','YYYY/MM/DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										} else if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("-")) {
											aliasValueSql = "to_date('"
													+ aliasCellmapValue
													+ "','YYYY-MM-DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										}
									} else {
										aliasKeySql += ","
												+ keyValue.toString();
										if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("/")) {
											aliasValueSql += ",to_date('"
													+ aliasCellmapValue
													+ "','YYYY/MM/DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										} else if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("-")) {
											aliasValueSql += ",to_date('"
													+ aliasCellmapValue
													+ "','YYYY-MM-DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										}
									}
									break;
								case 11:
									if (initKey == 1) {
										aliasKeySql = keyValue.toString();
										if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("/")) {
											aliasValueSql = "to_date('"
													+ aliasCellmapValue
													+ "','YYYY/MM/DD HH24:MI:SS')";// "'"+aliasCellmapValue+"'";
										} else if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("-")) {
											aliasValueSql = "to_date('"
													+ aliasCellmapValue
													+ "','YYYY-MM-DD HH24:MI:SS')";// "'"+aliasCellmapValue+"'";
										}
									} else {
										aliasKeySql += ","
												+ keyValue.toString();
										if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("/")) {
											aliasValueSql += ",to_date('"
													+ aliasCellmapValue
													+ "','YYYY/MM/DD HH24:MI:SS')";// "'"+aliasCellmapValue+"'";
										} else if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("-")) {
											aliasValueSql += ",to_date('"
													+ aliasCellmapValue
													+ "','YYYY-MM-DD HH24:MI:SS')";
										}
									}
									break;
								case 12:
									if (initKey == 1) {
										aliasKeySql = keyValue.toString();
										if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("/")) {
											aliasValueSql = "to_date('"
													+ aliasCellmapValue
													+ "','YYYY/MM/DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										} else if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("-")) {
											aliasValueSql = "to_date('"
													+ aliasCellmapValue
													+ "','YYYY-MM-DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										}
									} else {
										aliasKeySql += ","
												+ keyValue.toString();
										if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("/")) {
											aliasValueSql += ",to_date('"
													+ aliasCellmapValue
													+ "','YYYY/MM/DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										} else if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("-")) {
											aliasValueSql += ",to_date('"
													+ aliasCellmapValue
													+ "','YYYY-MM-DD HH24:MI')";
										}
									}
									break;
								default:
									// smap.put(keyValue.toString(),
									// aliasValueMap.get(keyValue));
									if (initKey == 1) {
										aliasKeySql = keyValue.toString();
										aliasValueSql = (aliasCellmapValue == null ? "null"
												: aliasCellmapValue.toString());
									} else {
										aliasKeySql += ","
												+ keyValue.toString();
										aliasValueSql += ","
												+ (aliasCellmapValue == null ? "null"
														: aliasCellmapValue
																.toString())
												+ "";
									}
									break;
								}
							}
						}
					}
				}
				smap.put("tableName", tableName);
				smap.put("aliasNameKeyContent", aliasKeySql);
				smap.put("aliasValueContent", aliasValueSql);
				addList.add(smap);
			}
			dataMap.put("addList", addList);
			// int result=this.insertExcelData(dataMap);
			this.insertExcelData(dataMap);
			// if(result==1){
			token = 1;
			// }else{
			// token = 0;
			// }
		} catch (Exception e) { // 未找到文件异常
			e.printStackTrace();
			token = 0;
		}
		return token;
	}

	public int deleteCordInfoByCardNo(Object name) {

		try {

			this.excelUtilDao.deleteCordInfoByCardNo(name);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}

		return 1;
	}

	public int deleteCordInfoByCardNo2(Object name) {

		try {

			this.excelUtilDao.deleteCordInfoByCardNo2(name);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}

		return 1;
	}

	public int checkCardNo(Object name) {

		return this.excelUtilDao.checkCardNo(name);
	}

	public int checkCardNo3(Object name) {

		return this.excelUtilDao.checkCardNo3(name);
	}

	public int checkCardNo2(Object name) {

		return this.excelUtilDao.checkCardNo2(name);
	}

	/**
	 * 如果导进来的这五个数据在表中已经存在
	 * 
	 * @param name
	 * @return
	 */
	public int checkPaiQianDiGuanLiInfo(Object obj) {

		return this.excelUtilDao.checkPaiQianDiGuanLiInfo(obj);
	}

	/**
	 * 检测法人代码是否合理
	 * 
	 * @param name
	 * @return
	 */
	public int checkPaiQianDiGuanLiFrInfo(Object obj) {

		return this.excelUtilDao.checkPaiQianDiGuanLiFrInfo(obj);
	}

	public int checkPaiQianDiGuanLiCsdjInfo(Object obj) {

		return this.excelUtilDao.checkPaiQianDiGuanLiCsdjInfo(obj);
	}

	public int checkPaiQianDiGuanLiSfInfo(Object obj) {

		return this.excelUtilDao.checkPaiQianDiGuanLiSfInfo(obj);
	}

	public int checkPaiQianDiGuanLiCsmcInfo(Object obj) {

		return this.excelUtilDao.checkPaiQianDiGuanLiCsmcInfo(obj);
	}

	public int checkPaiQianDiGuanLiDqmcInfo(Object obj) {

		return this.excelUtilDao.checkPaiQianDiGuanLiDqmcInfo(obj);
	}

	/**
	 * 删除已经存在的派遣关系
	 * 
	 * @param name
	 * @return
	 */
	public int deletePaiQianDiGuanLiInfo(Object obj) {

		return this.excelUtilDao.deletePaiQianDiGuanLiInfo(obj);
	}

	/**
	 * 分析excel的各个列，获取各个列的值，并且导入的数据库中 Description:
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param modelMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ModelMap importArCardRecordData2(HttpServletRequest request,
			HttpServletResponse response, LinkedHashMap map, ModelMap modelMap) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String adminID = admin.getAdminID() == null ? "USERNO_"
				+ admin.getUserNo() : admin.getAdminID();
		ObjectBindUtil.getRequestParamData(request);
		this.filename = adminID;
		this.pathCpnyID = admin.getCpnyId();
		this.language = admin.getLanguage();
		if (this.processUploadFile(request, response)) {
			int returnInt = this
					.impArCardRecordDataBase2(adminID, map, request);
			if (returnInt == 0) {
				modelMap.put("sign", "0");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.importfail", request));// 导入失败!
			} else if (returnInt < 0) {
				modelMap.put("sign", "-1");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.title.di", request)
						+ String.valueOf(0 - returnInt)
						+ TipMessage.getTipMessage(
								"ar.alert.message.excelimport.title.hang",
								request)
						+ TipMessage.getTipMessage(
								"ar.alert.message.excelimport.dateformaterror",
								request));// 日期不能为空
			} else if (returnInt == 2) {
				modelMap.put("sign", "2");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.existnull", request));// 表有空值或有空单元行，请检查！
			} else if (returnInt == 1) {
				modelMap.put("sign", "1");
				modelMap.put("statusCode", "200");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.importsuccess", request));// 导入成功
			} else if (returnInt == 3) {
				modelMap.put("sign", "3");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.dataerror", request));// 数据列数有问题，请核对
			} else if (returnInt == 4) {
				modelMap.put("sign", "3");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.datanull", request));// 所导入数据中考勤月或工号存在为空的情况，请检查核对
			} else if (returnInt == 5) {
				modelMap.put("sign", "3");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.armonthlock", request));// 所导入数据中存在考勤月已经锁定情况，不能进行数据导入
			} else if (returnInt == 6) {
				modelMap.put("sign", "3");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.empiderror", request));// 所导入数据中工号存在问题，不能进行数据导入
			} else if (returnInt == 7) {
				modelMap.put("sign", "3");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.deleteerror", request));// 删除汇总历史数据出错,请联系管理员
			} else if (returnInt > 1000) {
				modelMap.put("sign", "3");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.title.di", request)
						+ String.valueOf(returnInt - 1000)
						+ TipMessage.getTipMessage(
								"ar.alert.message.excelimport.title.hang",
								request)
						+ TipMessage.getTipMessage(
								"ar.alert.message.excelimport.cardnoiserror",
								request));// 卡号有错误，请检查核对！
			}

			int i = path.indexOf("\\");
			String server = "win";
			if (i < 0) { // WebServer的os为unix/linux
				server = "u/l";
			}
			if ("win".equals(server)) {
				File file = new File(this.path + "\\" + filename + ".xls");
				file.delete();
			} else {
				File file = new File(this.path + "/" + filename + ".xls");
				file.delete();
			}

		} else {
			modelMap.put("sign", "0");
			modelMap.put("statusCode", "300");
			modelMap.put("message", TipMessage.getTipMessage(
					"ar.alert.message.excelimport.importfail", request));// 导入失败!
		}
		return modelMap;
	}

	@SuppressWarnings( { "unchecked" })
	public int impArCardRecordDataBase2(String adminID, LinkedHashMap cellmap,
			HttpServletRequest request) {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		int token = 1;

		int p = path.indexOf("\\");
		String server = "win";
		if (p < 0) { // WebServer的os为unix/linux
			server = "u/l";
		}

		File file = null;

		if ("win".equals(server)) {
			file = new File(path + "\\" + filename + ".xls");
		} else {
			file = new File(path + "/" + filename + ".xls");
		}

		Map dataMap = new HashMap();

		String tableName = cellmap.get("tableName") == null ? "" : cellmap.get(
				"tableName").toString();// 获取表名

		try {
			Workbook workBook = Workbook.getWorkbook(file);
			// 获得第一个工作表对象
			Sheet sheet = workBook.getSheet(0);
			// 验证是否存在导入数据
			if (sheet.getRows() < 2) {
				logger.debug("Import file not exist data, return.");
				return 0;
			}

			if (sheet.getRow(0).length < 8) {
				return 10;// 模板错误
			}
			List addList = new ArrayList();

			// 循环检查EXCEL中数据 规定 第一列 AR_MONTH 第二列 PERSON_ID
			for (int n = 1; n < sheet.getRows(); n++) {

				Cell[] rowID = sheet.getRow(n);

				int rowLength = rowID.length;

				if (rowLength < 2) {
					return 3;// 数据列数有问题，请核对
				}

				if (rowID[0].getContents() == null
						|| "".equals(rowID[0].getContents().trim())) {
					return 1000 + n;// n行工号有错误
				}

				if (rowID[3].getContents() == null
						|| "".equals(rowID[3].getContents().trim())) {
					return -n;// n时间有错误
				}
				// 检查考勤是否锁定及在汇总表中检查数据是否已存在 存在则删除
				LinkedHashMap tempMap = new LinkedHashMap();
				tempMap.put("EMPID", rowID[0].getContents().trim());
				tempMap.put("CPNY_ID", admin.getCpnyId());
				tempMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());
				tempMap.put("AR_SUMMARY_TABLE", tableName);
				// 查看月考勤锁定情况
				if (this.checkPersonalInfo(tempMap) == 0) {
					return 1000 + n;// n行工号或时间有错误
				}
			}

			List summaryList = new ArrayList();
			// 删除数据
			for (int m = 1; m < sheet.getRows(); m++) {

				Cell[] rowTemp = sheet.getRow(m);

				// 删除已存在数据
				// 循环4、5、6、7列
				for (int z = 4; z < 8; z++) {
					// 保证循环列ID<=当前行列数
					if (z < rowTemp.length) {
						if (rowTemp[z].getContents() != null
								&& !"".equals(rowTemp[z].getContents().trim())) {
							LinkedHashMap tempMap = new LinkedHashMap();
							tempMap.put("EMPID", rowTemp[0].getContents()
									.trim());
							tempMap.put("CPNY_ID", admin.getCpnyId());
							tempMap.put("AR_SUPERVISIOR_INFO", admin
									.getPersonId());
							tempMap.put("AR_MAC_RECORDS", tableName);

							String rValue = "";
							rValue = this.getValue(rowTemp[3], "1");
							// 格式化出错
							if ("".equals(rValue)) {
								return -m;// 时间有错误
							}

							tempMap.put("R_TIME", rValue + " "
									+ rowTemp[z].getContents().trim());

							summaryList.add(tempMap);
						}
					}
				}
			}

			// 删除表中数据
			if (this.deleteArMacRecordsInfo(summaryList) != 1) {
				return 7;// 删除汇总历史数据出错,请联系管理员
			}

			LinkedHashMap aliasValueMapTemp = cellmap.get("aliasValueMap") == null ? null
					: (LinkedHashMap) cellmap.get("aliasValueMap");
			LinkedHashMap aliasTypeMap = cellmap.get("aliasTypeMap") == null ? null
					: (LinkedHashMap) cellmap.get("aliasTypeMap");

			// 循环工作表的每行数据

			for (int i = 1; i < sheet.getRows(); i++) {

				int init = 0;

				Cell[] row = sheet.getRow(i);

				init = row.length;

				for (int w = 4; w < 8; w++) {
					if (w < init) {

						if (row[w].getContents() != null
								&& !"".equals(row[w].getContents().trim())) {

							LinkedHashMap smap = new LinkedHashMap();
							LinkedHashMap aliasCellmap = new LinkedHashMap();

							// 判断IN/OUT类型
							if (w == 4 || w == 6) {
								aliasValueMapTemp.put("DOOR_TYPE", "IN");
							} else {
								aliasValueMapTemp.put("DOOR_TYPE", "OUT");
							}

							String rValue = "";
							rValue = this.getValue(row[3], "1");
							// 格式化出错
							if ("".equals(rValue)) {
								return -i;// 时间有错误
							}

							aliasCellmap.put("#CELL3#", rValue + " "
									+ row[w].getContents().trim());
							aliasCellmap.put("#CELL0#", row[0].getContents()
									.trim());

							LinkedHashMap aliasValueI18nMap = cellmap
									.get("aliasValueI18nMap") == null ? null
									: (LinkedHashMap) cellmap
											.get("aliasValueI18nMap");

							String aliasKeySql = "";
							String aliasValueSql = "";
							int initKey = 0;
							if (aliasValueMapTemp != null
									&& aliasTypeMap != null) {
								Set keySet = aliasValueMapTemp.keySet();
								if (keySet != null) {
									Iterator it = keySet.iterator();
									while (it.hasNext()) {
										initKey++;
										Object keyValue = it.next();
										if (keyValue != null
												&& !keyValue.equals("")) {
											int fieldType = Integer
													.parseInt(aliasTypeMap.get(
															keyValue)
															.toString());
											Object aliasCellmapValue = aliasCellmap
													.get(aliasValueMapTemp
															.get(keyValue));
											if (aliasCellmapValue == null) {// 不属于excel的列
												aliasCellmapValue = aliasValueMapTemp
														.get(keyValue);
											} else {
												if (aliasValueI18nMap != null
														&& aliasValueI18nMap
																.get(keyValue) != null) {// 有需要从国际化表读取No的列
													String sqlContent = aliasValueI18nMap
															.get(keyValue)
															.toString();
													sqlContent = sqlContent
															.replaceAll(
																	aliasValueMapTemp
																			.get(
																					keyValue)
																			.toString(),
																	"'"
																			+ aliasCellmapValue
																					.toString()
																			+ "'");
													LinkedHashMap sqlI8nContentMap = new LinkedHashMap();
													sqlI8nContentMap.put(
															"sqlI18nContent",
															sqlContent);
													aliasCellmapValue = this
															.getCodeNoByName(sqlI8nContentMap) == -1 ? ""
															: this
																	.getCodeNoByName(sqlI8nContentMap);
												}
											}
											switch (fieldType) {
											case 0:
												// smap.put(keyValue.toString(),
												// "'"+aliasValueMap.get(keyValue)+"'");
												if (initKey == 1) {
													aliasKeySql = keyValue
															.toString();
													aliasValueSql = "'"
															+ aliasCellmapValue
															+ "'";
												} else {
													aliasKeySql += ","
															+ keyValue
																	.toString();
													aliasValueSql += ",'"
															+ aliasCellmapValue
															+ "'";
												}
												break;
											case 1:
												if (initKey == 1) {
													aliasKeySql = keyValue
															.toString();
													aliasValueSql = "to_date('"
															+ aliasCellmapValue
															+ "','yyyy-MM-dd')";// "'"+aliasCellmapValue+"'";
												} else {
													aliasKeySql += ","
															+ keyValue
																	.toString();
													aliasValueSql += ",to_date('"
															+ aliasCellmapValue
															+ "','yyyy-MM-dd')";// "'"+aliasCellmapValue+"'";
												}
												break;
											case 11:
												if (initKey == 1) {
													aliasKeySql = keyValue
															.toString();
													aliasValueSql = "to_date('"
															+ aliasCellmapValue
															+ "','yyyy-MM-dd HH24:MI:ss')";// "'"+aliasCellmapValue+"'";
												} else {
													aliasKeySql += ","
															+ keyValue
																	.toString();
													aliasValueSql += ",to_date('"
															+ aliasCellmapValue
															+ "','yyyy-MM-dd HH24:MI:ss')";// "'"+aliasCellmapValue+"'";
												}
												break;
											case 12:
												if (initKey == 1) {
													aliasKeySql = keyValue
															.toString();
													aliasValueSql = "to_date('"
															+ aliasCellmapValue
															+ "','yyyy-MM-dd HH24:MI')";// "'"+aliasCellmapValue+"'";
												} else {
													aliasKeySql += ","
															+ keyValue
																	.toString();
													aliasValueSql += ",to_date('"
															+ aliasCellmapValue
															+ "','yyyy-MM-dd HH24:MI')";// "'"+aliasCellmapValue+"'";
												}
												break;
											default:
												// smap.put(keyValue.toString(),
												// aliasValueMap.get(keyValue));
												if (initKey == 1) {
													aliasKeySql = keyValue
															.toString();
													aliasValueSql = aliasCellmapValue
															.toString();
												} else {
													aliasKeySql += ","
															+ keyValue
																	.toString();
													aliasValueSql += ","
															+ aliasCellmapValue
															+ "";
												}
												break;
											}
										}
									}
								}
							}
							smap.put("tableName", tableName);
							smap.put("aliasNameKeyContent", aliasKeySql);
							smap.put("aliasValueContent", aliasValueSql);
							addList.add(smap);
						}
					}
				}
			}
			dataMap.put("addList", addList);
			// int result=this.insertExcelData(dataMap);
			this.insertExcelData(dataMap);
			// if(result==1){
			// token = 1;
			// }else{
			// token = 0;
			// }
		} catch (Exception e) { // 未找到文件异常
			e.printStackTrace();
			token = 0;
		}
		return token;
	}

	public int deleteArMacRecordsInfo(Object name) {

		try {

			this.excelUtilDao.deleteArMacRecordsInfo(name);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}

		return 1;
	}

	public String getValue(Cell value, String type) {

		String cellValue = "";

		try {
			cellValue = this.FormateData(value, type);
			if (cellValue.contains("/")) {
				String[] contentStr = cellValue.split("/");
				if (contentStr.length == 3 && contentStr[0].length() == 2
						&& contentStr[2].length() == 4) {
					cellValue = contentStr[2] + "/" + contentStr[1] + "/"
							+ contentStr[0];
				}
			} else if (cellValue.contains("-")) {
				String[] contentStr = cellValue.split("-");
				if (contentStr.length == 3 && contentStr[0].length() == 2
						&& contentStr[2].length() == 4) {
					cellValue = contentStr[2] + "-" + contentStr[1] + "-"
							+ contentStr[0];
				}
			}
		} catch (Exception e) {
			// cellValue= row[j].getContents()==null?"":row[j].getContents();
			cellValue = "";
		}

		return cellValue;
	}

	/**
	 * 导出数据到excel Description:export datas to excel
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings( { "unchecked", "deprecation" })
	public void exportExcelByName(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap,
			LinkedHashMap sqlContentmap, List aliasNameList, List aliasList,
			String name) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String adminID = admin.getAdminID() == null ? "USERNO_"
				+ admin.getUserNo() : admin.getAdminID();
		this.type = request.getParameter("type");
		String CpnyId = admin.getCpnyId();
		this.path = request.getRealPath("");

		int init = path.indexOf("\\");
		String server = "win";
		if (init < 0) { // WebServer的os为unix/linux
			server = "u/l";
		}
		if ("win".equals(server)) {
			path = path + "\\resources\\temp\\download\\" + CpnyId + "\\"
					+ adminID + "\\";
		} else {
			path = path + "/resources/temp/download/" + CpnyId + "/" + adminID;
		}
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet1 = wb.createSheet("sheet1");
		for (int i = 0; i < 35; i++) {
			sheet1.setColumnWidth(i, 3800);
		}
		XSSFRow row = sheet1.createRow(0);
		XSSFCell cell;

		List result = null;
		try {
			/**
			 * 根据导出报表的类型，定义列名，并且从相应的表中查询数据
			 */
			// if(this.type!=null&&this.type.equals("InsuranceInputItemData")){
			row = sheet1.createRow(0);
			for (int i = 0; i < aliasNameList.size(); i++) {
				cell = row.createCell((short) i);
				cell.setCellValue(aliasNameList.get(i).toString());
			}
			if (sqlContentmap != null
					&& sqlContentmap.get("sqlContent") != null
					&& !sqlContentmap.get("sqlContent").equals("")) {
				result = this.getExcelExportDataList(sqlContentmap);
			}
			// }
			File file = new File(this.path + "\\temp_" + name + ".xlsx");
			file.delete();
			if (result != null && result.size() > 0) {// 导出数据
				for (int i = 0; i < result.size(); i++) {
					int k = 0;
					row = sheet1.createRow(i + 1);
					for (int j = 0; j < aliasList.size(); j++) {
						cell = row.createCell((short) k++);
						cell
								.setCellValue(((Map) (result.get(i)))
										.get(aliasList.get(j).toString()) == null ? ""
										: ((Map) (result.get(i))).get(
												aliasList.get(j).toString())
												.toString());
					}
				}
			} else {// 下载导入模板
				List contentList = sqlContentmap.get("contentList") == null ? null
						: (List) sqlContentmap.get("contentList");
				if (contentList != null && contentList.size() > 0) {
					for (int i = 0; i < contentList.size(); i++) {
						int k = 0;
						row = sheet1.createRow(i + 1);
						Set keySet = ((Map) (contentList.get(i))).keySet();
						if (keySet != null) {
							Iterator it = keySet.iterator();
							while (it.hasNext()) {
								Object itValue = it.next();
								if (itValue != null) {
									cell = row.createCell((short) k++);
									cell.setCellValue(((Map) (contentList
											.get(i))).get(itValue).toString());
								}
							}
						}
					}
				}
			}
			File f = new File(this.path);
			if (!f.exists()) {
				f.mkdirs();
			}
			FileOutputStream fos = new FileOutputStream(new File(this.path
					+ "\\temp_" + name + ".xlsx"));
			wb.write(fos);
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.setContentType(CONTENT_TYPE);

		File file = new File(this.path + "\\temp_" + name + ".xlsx");
		response.setContentType("application/x-msdownload");
		response.setContentLength((int) file.length());
		response.setHeader("Content-Disposition", "attachment;filename="
				+ "\\temp_" + name + ".xlsx");
		FileInputStream fin = new FileInputStream(file);
		BufferedInputStream buff = new BufferedInputStream(fin);
		OutputStream myout = response.getOutputStream();
		byte[] b = new byte[2048];
		long k = 0;
		try {
			while (k < file.length()) {
				int j = buff.read(b, 0, 2048);
				k += j;
				myout.write(b, 0, j);
			}
			myout.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fin != null)
				try {
					fin.close();
				} catch (Exception e) {
				}
			if (myout != null)
				try {
					myout.close();
				} catch (Exception e) {
				}
			if (file.exists()) {
				file.delete();
			}
		}
	}

	/**
	 * 导出数据到excel Description:export datas to excel
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings( { "unchecked", "deprecation" })
	public void exportExcelTwoSheetByNameZuiDiGongZiFeiTemp(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap, LinkedHashMap sqlContentmap, List aliasNameList,
			List aliasList, List sheet2List, String name) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String adminID = admin.getAdminID() == null ? "USERNO_"
				+ admin.getUserNo() : admin.getAdminID();
		this.type = request.getParameter("type");
		String CpnyId = admin.getCpnyId();
		this.path = request.getRealPath("");

		int init = path.indexOf("\\");
		String server = "win";
		if (init < 0) { // WebServer的os为unix/linux
			server = "u/l";
		}
		if ("win".equals(server)) {
			path = path + "\\resources\\temp\\download\\" + CpnyId + "\\"
					+ adminID + "\\";
		} else {
			path = path + "/resources/temp/download/" + CpnyId + "/" + adminID;
		}
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet1 = wb.createSheet(name);
		XSSFRow row = sheet1.createRow(0);
		XSSFCell cell;

		XSSFSheet sheet2 = wb.createSheet("sheet2");
		// XSSFRow row2 = sheet1.createRow(0);
		// XSSFCell cell2;

		List result = null;
		try {
			/**
			 * 根据导出报表的类型，定义列名，并且从相应的表中查询数据
			 */
			// if(this.type!=null&&this.type.equals("InsuranceInputItemData")){
			row = sheet1.createRow(0);
			for (int i = 0; i < aliasNameList.size(); i++) {
				cell = row.createCell((short) i);
				cell.setCellValue(aliasNameList.get(i).toString());
			}
			if (sqlContentmap != null) {
				result = this.getExcelExportDataList(sqlContentmap);
			}
			// }
			File file = new File(this.path + "\\temp_" + name + ".xlsx");
			file.delete();

			if (result != null && result.size() > 0) {// 导出数据
				for (int i = 0; i < result.size(); i++) {
					int k = 0;
					row = sheet1.createRow(i + 1);
					for (int j = 0; j < aliasList.size(); j++) {
						cell = row.createCell((short) k++);
						cell
								.setCellValue(((Map) (result.get(i)))
										.get(aliasList.get(j).toString()) == null ? ""
										: ((Map) (result.get(i))).get(
												aliasList.get(j).toString())
												.toString());
					}
				}
			} else {// 下载导入模板
				List contentList = sqlContentmap.get("contentList") == null ? null
						: (List) sqlContentmap.get("contentList");
				if (contentList != null && contentList.size() > 0) {
					for (int i = 0; i < contentList.size(); i++) {
						int k = 0;
						row = sheet1.createRow(i + 1);
						Set keySet = ((Map) (contentList.get(i))).keySet();
						if (keySet != null) {
							Iterator it = keySet.iterator();
							while (it.hasNext()) {
								Object itValue = it.next();
								if (itValue != null) {
									cell = row.createCell((short) k++);
									cell.setCellValue(((Map) (contentList
											.get(i))).get(itValue).toString());
								}
							}
						}
					}
				}
			}
			// row2 = sheet2.createRow(0);
			if (sheet2List != null && sheet2List.size() > 0) {// 设定sheet2的列头
				XSSFRow row2 = sheet2.createRow(0);
				XSSFCell cell2 = row2.createCell((short) 0);
				cell2.setCellValue((sheet2List.get(sheet2List.size() - 1))
						.toString());
			}
			if (sheet2List != null) {
				for (int i = 0; i < sheet2List.size() - 1; i++) {// 设定sheet2里头的内容
					XSSFRow row2 = sheet2.createRow(i + 1);
					XSSFCell cell2 = row2.createCell((short) 0);
					if (sheet2List.get(i) != null
							&& ((Map) (sheet2List.get(i))).get("CONTENT") != null) {
						cell2.setCellValue(((Map) (sheet2List.get(i))).get(
								"CONTENT").toString());
					} else {
						cell2.setCellValue("");
					}
				}
			}
			File f = new File(this.path);
			if (!f.exists()) {
				f.mkdirs();
			}
			FileOutputStream fos = new FileOutputStream(new File(this.path
					+ "\\temp_" + name + ".xlsx"));
			wb.write(fos);
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.setContentType(CONTENT_TYPE);

		File file = new File(this.path + "\\temp_" + name + ".xlsx");
		response.setContentType("application/x-msdownload");
		response.setContentLength((int) file.length());
		response.setHeader("Content-Disposition", "attachment;filename="
				+ "\\temp_" + name + ".xlsx");
		FileInputStream fis = new FileInputStream(file);
		BufferedInputStream buff = new BufferedInputStream(fis);

		byte[] b = new byte[2048];
		long k = 0;
		OutputStream myout = response.getOutputStream();
		while (k < file.length()) {
			int j = buff.read(b, 0, 2048);
			k += j;
			myout.write(b, 0, j);
		}
		myout.flush();
	}

	/**
	 * 导出数据到excel Description:export datas to excel
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings( { "unchecked", "deprecation" })
	public void exportExcelTwoSheetByNameZuiDiGongZiFei(
			HttpServletRequest request, HttpServletResponse response,
			ModelMap modelMap, LinkedHashMap sqlContentmap, List aliasNameList,
			List aliasList, List sheet2List, String name) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String adminID = admin.getAdminID() == null ? "USERNO_"
				+ admin.getUserNo() : admin.getAdminID();
		this.type = request.getParameter("type");
		String CpnyId = admin.getCpnyId();
		this.path = request.getRealPath("");

		int init = path.indexOf("\\");
		String server = "win";
		if (init < 0) { // WebServer的os为unix/linux
			server = "u/l";
		}
		if ("win".equals(server)) {
			path = path + "\\resources\\temp\\download\\" + CpnyId + "\\"
					+ adminID + "\\";
		} else {
			path = path + "/resources/temp/download/" + CpnyId + "/" + adminID;
		}
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet1 = wb.createSheet(name);
		XSSFRow row = sheet1.createRow(0);
		XSSFCell cell;

		XSSFSheet sheet2 = wb.createSheet("sheet2");
		// XSSFRow row2 = sheet1.createRow(0);
		// XSSFCell cell2;

		List result = null;
		try {
			/**
			 * 根据导出报表的类型，定义列名，并且从相应的表中查询数据
			 */
			// if(this.type!=null&&this.type.equals("InsuranceInputItemData")){
			row = sheet1.createRow(0);
			for (int i = 0; i < aliasNameList.size(); i++) {
				cell = row.createCell((short) i);
				cell.setCellValue(aliasNameList.get(i).toString());
			}
			if (sqlContentmap != null
					&& sqlContentmap.get("sqlContent") != null
					&& !sqlContentmap.get("sqlContent").equals("")) {
				result = this.getExcelExportDataList(sqlContentmap);
			}
			// }
			File file = new File(this.path + "\\temp_" + name + ".xlsx");
			file.delete();

			if (result != null && result.size() > 0) {// 导出数据
				for (int i = 0; i < result.size(); i++) {
					int k = 0;
					row = sheet1.createRow(i + 1);
					for (int j = 0; j < aliasList.size(); j++) {
						cell = row.createCell((short) k++);
						cell
								.setCellValue(((Map) (result.get(i)))
										.get(aliasList.get(j).toString()) == null ? ""
										: ((Map) (result.get(i))).get(
												aliasList.get(j).toString())
												.toString());
					}
				}
			} else {// 下载导入模板
				List contentList = sqlContentmap.get("contentList") == null ? null
						: (List) sqlContentmap.get("contentList");
				if (contentList != null && contentList.size() > 0) {
					for (int i = 0; i < contentList.size(); i++) {
						int k = 0;
						row = sheet1.createRow(i + 1);
						Set keySet = ((Map) (contentList.get(i))).keySet();
						if (keySet != null) {
							Iterator it = keySet.iterator();
							while (it.hasNext()) {
								Object itValue = it.next();
								if (itValue != null) {
									cell = row.createCell((short) k++);
									cell.setCellValue(((Map) (contentList
											.get(i))).get(itValue).toString());
								}
							}
						}
					}
				}
			}
			// row2 = sheet2.createRow(0);
			if (sheet2List != null && sheet2List.size() > 0) {// 设定sheet2的列头
				XSSFRow row2 = sheet2.createRow(0);
				XSSFCell cell2 = row2.createCell((short) 0);
				cell2.setCellValue((sheet2List.get(sheet2List.size() - 1))
						.toString());
			}
			if (sheet2List != null) {
				for (int i = 0; i < sheet2List.size() - 1; i++) {// 设定sheet2里头的内容
					XSSFRow row2 = sheet2.createRow(i + 1);
					XSSFCell cell2 = row2.createCell((short) 0);
					if (sheet2List.get(i) != null
							&& ((Map) (sheet2List.get(i))).get("CONTENT") != null) {
						cell2.setCellValue(((Map) (sheet2List.get(i))).get(
								"CONTENT").toString());
					} else {
						cell2.setCellValue("");
					}
				}
			}
			File f = new File(this.path);
			if (!f.exists()) {
				f.mkdirs();
			}
			FileOutputStream fos = new FileOutputStream(new File(this.path
					+ "\\temp_" + name + ".xlsx"));
			wb.write(fos);
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.setContentType(CONTENT_TYPE);

		File file = new File(this.path + "\\temp_" + name + ".xlsx");
		response.setContentType("application/x-msdownload");
		response.setContentLength((int) file.length());
		response.setHeader("Content-Disposition", "attachment;filename="
				+ "\\temp_" + name + ".xlsx");
		FileInputStream fis = new FileInputStream(file);
		BufferedInputStream buff = new BufferedInputStream(fis);

		byte[] b = new byte[2048];
		long k = 0;
		OutputStream myout = response.getOutputStream();
		while (k < file.length()) {
			int j = buff.read(b, 0, 2048);
			k += j;
			myout.write(b, 0, j);
		}
		myout.flush();
	}

	/**
	 * 导出数据到excel Description:export datas to excel
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings( { "unchecked", "deprecation" })
	public void exportExcelTwoSheetByName(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap,
			LinkedHashMap sqlContentmap, List aliasNameList, List aliasList,
			List sheet2List, String name) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String adminID = admin.getAdminID() == null ? "USERNO_"
				+ admin.getUserNo() : admin.getAdminID();
		this.type = request.getParameter("type");
		String CpnyId = admin.getCpnyId();
		this.path = request.getRealPath("");

		int init = path.indexOf("\\");
		String server = "win";
		if (init < 0) { // WebServer的os为unix/linux
			server = "u/l";
		}
		if ("win".equals(server)) {
			path = path + "\\resources\\temp\\download\\" + CpnyId + "\\"
					+ adminID + "\\";
		} else {
			path = path + "/resources/temp/download/" + CpnyId + "/" + adminID;
		}
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet1 = wb.createSheet(name);
		XSSFRow row = sheet1.createRow(0);
		XSSFCell cell;

		XSSFSheet sheet2 = wb.createSheet("sheet2");
		// XSSFRow row2 = sheet1.createRow(0);
		// XSSFCell cell2;

		List result = null;
		try {
			/**
			 * 根据导出报表的类型，定义列名，并且从相应的表中查询数据
			 */
			// if(this.type!=null&&this.type.equals("InsuranceInputItemData")){
			row = sheet1.createRow(0);
			for (int i = 0; i < aliasNameList.size(); i++) {
				cell = row.createCell((short) i);
				cell.setCellValue(aliasNameList.get(i).toString());
			}
			if (sqlContentmap != null
					&& sqlContentmap.get("sqlContent") != null
					&& !sqlContentmap.get("sqlContent").equals("")) {
				result = this.getExcelExportDataList(sqlContentmap);
			}
			// }
			File file = new File(this.path + "\\temp_" + name + ".xlsx");
			file.delete();

			if (result != null && result.size() > 0) {// 导出数据
				for (int i = 0; i < result.size(); i++) {
					int k = 0;
					row = sheet1.createRow(i + 1);
					for (int j = 0; j < aliasList.size(); j++) {
						cell = row.createCell((short) k++);
						cell
								.setCellValue(((Map) (result.get(i)))
										.get(aliasList.get(j).toString()) == null ? ""
										: ((Map) (result.get(i))).get(
												aliasList.get(j).toString())
												.toString());
					}
				}
			} else {// 下载导入模板
				List contentList = sqlContentmap.get("contentList") == null ? null
						: (List) sqlContentmap.get("contentList");
				if (contentList != null && contentList.size() > 0) {
					for (int i = 0; i < contentList.size(); i++) {
						int k = 0;
						row = sheet1.createRow(i + 1);
						Set keySet = ((Map) (contentList.get(i))).keySet();
						if (keySet != null) {
							Iterator it = keySet.iterator();
							while (it.hasNext()) {
								Object itValue = it.next();
								if (itValue != null) {
									cell = row.createCell((short) k++);
									cell.setCellValue(((Map) (contentList
											.get(i))).get(itValue).toString());
								}
							}
						}
					}
				}
			}
			// row2 = sheet2.createRow(0);
			if (sheet2List != null && sheet2List.size() > 0) {// 设定sheet2的列头
				XSSFRow row2 = sheet2.createRow(0);
				XSSFCell cell2 = row2.createCell((short) 0);
				cell2.setCellValue((sheet2List.get(sheet2List.size() - 1))
						.toString());
			}
			if (sheet2List != null) {
				for (int i = 0; i < sheet2List.size() - 1; i++) {// 设定sheet2里头的内容
					XSSFRow row2 = sheet2.createRow(i + 1);
					XSSFCell cell2 = row2.createCell((short) 0);
					if (sheet2List.get(i) != null
							&& ((Map) (sheet2List.get(i))).get("CONTENT") != null) {
						cell2.setCellValue(((Map) (sheet2List.get(i))).get(
								"CONTENT").toString());
					} else {
						cell2.setCellValue("");
					}
				}
			}
			File f = new File(this.path);
			if (!f.exists()) {
				f.mkdirs();
			}
			FileOutputStream fos = new FileOutputStream(new File(this.path
					+ "\\temp_" + name + ".xlsx"));
			wb.write(fos);
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.setContentType(CONTENT_TYPE);

		File file = new File(this.path + "\\temp_" + name + ".xlsx");
		response.setContentType("application/x-msdownload");
		response.setContentLength((int) file.length());
		response.setHeader("Content-Disposition", "attachment;filename="
				+ "\\temp_" + name + ".xlsx");
		FileInputStream fis = new FileInputStream(file);
		BufferedInputStream buff = new BufferedInputStream(fis);

		byte[] b = new byte[2048];
		long k = 0;
		OutputStream myout = response.getOutputStream();
		while (k < file.length()) {
			int j = buff.read(b, 0, 2048);
			k += j;
			myout.write(b, 0, j);
		}
		myout.flush();
	}

	/**
	 * 导出数据到excel Description:export datas to excel
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings( { "unchecked", "deprecation" })
	public void exportExcelThreeSheetByName(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap,
			LinkedHashMap sqlContentmap, List aliasNameList, List aliasList,
			List sheet2List, List sheet2List1, String name) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String adminID = admin.getAdminID() == null ? "USERNO_"
				+ admin.getUserNo() : admin.getAdminID();
		this.type = request.getParameter("type");
		String CpnyId = admin.getCpnyId();
		this.path = request.getRealPath("");

		int init = path.indexOf("\\");
		String server = "win";
		if (init < 0) { // WebServer的os为unix/linux
			server = "u/l";
		}
		if ("win".equals(server)) {
			path = path + "\\resources\\temp\\download\\" + CpnyId + "\\"
					+ adminID + "\\";
		} else {
			path = path + "/resources/temp/download/" + CpnyId + "/" + adminID;
		}
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet1 = wb.createSheet("sheet1");

		XSSFRow row = sheet1.createRow(0);
		XSSFCell cell;

		XSSFSheet sheet2 = wb.createSheet("sheet2");
		// XSSFRow row2 = sheet1.createRow(0);
		// XSSFCell cell2;

		List result = null;
		try {
			/**
			 * 根据导出报表的类型，定义列名，并且从相应的表中查询数据
			 */
			// if(this.type!=null&&this.type.equals("InsuranceInputItemData")){
			row = sheet1.createRow(0);
			for (int i = 0; i < aliasNameList.size(); i++) {
				cell = row.createCell((short) i);
				cell.setCellValue(aliasNameList.get(i).toString());
			}
			if (sqlContentmap != null
					&& sqlContentmap.get("sqlContent") != null
					&& !sqlContentmap.get("sqlContent").equals("")) {
				result = this.getExcelExportDataList(sqlContentmap);
			}
			// }
			File file = new File(this.path + "\\temp_" + name + ".xlsx");
			file.delete();

			if (result != null && result.size() > 0) {// 导出数据
				for (int i = 0; i < result.size(); i++) {
					int k = 0;
					row = sheet1.createRow(i + 1);
					for (int j = 0; j < aliasList.size(); j++) {
						cell = row.createCell((short) k++);
						cell
								.setCellValue(((Map) (result.get(i)))
										.get(aliasList.get(j).toString()) == null ? ""
										: ((Map) (result.get(i))).get(
												aliasList.get(j).toString())
												.toString());
					}
				}
			} else {// 下载导入模板
				List contentList = sqlContentmap.get("contentList") == null ? null
						: (List) sqlContentmap.get("contentList");
				if (contentList != null && contentList.size() > 0) {
					for (int i = 0; i < contentList.size(); i++) {
						int k = 0;
						row = sheet1.createRow(i + 1);
						Set keySet = ((Map) (contentList.get(i))).keySet();
						if (keySet != null) {
							Iterator it = keySet.iterator();
							while (it.hasNext()) {
								Object itValue = it.next();
								if (itValue != null) {
									cell = row.createCell((short) k++);
									cell.setCellValue(((Map) (contentList
											.get(i))).get(itValue).toString());
								}
							}
						}
					}
				}
			}
			// row2 = sheet2.createRow(0);
			if (sheet2List != null && sheet2List.size() > 0) {// 设定sheet2的列头
				XSSFRow row2 = sheet2.createRow(0);
				XSSFCell cell2 = row2.createCell((short) 0);
				cell2.setCellValue((sheet2List.get(sheet2List.size() - 1))
						.toString());
			}
			for (int i = 0; i < sheet2List.size() - 1; i++) {// 设定sheet2里头的内容
				XSSFRow row2 = sheet2.createRow(i + 1);
				XSSFCell cell2 = row2.createCell((short) 0);
				if (sheet2List.get(i) != null
						&& ((Map) (sheet2List.get(i))).get("CONTENT") != null) {
					cell2.setCellValue(((Map) (sheet2List.get(i))).get(
							"CONTENT").toString());
				} else {
					cell2.setCellValue("");
				}
			}

			if (sheet2List1 != null && sheet2List1.size() > 0) {// 设定sheet2的列头
				XSSFRow row2_1 = sheet2.getRow(0) != null ? sheet2.getRow(0)
						: sheet2.createRow(0);
				XSSFCell cell2_1 = row2_1.createCell((short) 3);
				cell2_1.setCellValue((sheet2List1.get(sheet2List1.size() - 1))
						.toString());
			}
			for (int i = 0; i < sheet2List1.size() - 1; i++) {// 设定sheet2里头的内容
				XSSFRow row2_1 = sheet2.getRow(i + 1) != null ? sheet2
						.getRow(i + 1) : sheet2.createRow(i + 1);
				XSSFCell cell2_1 = row2_1.createCell((short) 3);
				if (sheet2List1.get(i) != null
						&& ((Map) (sheet2List1.get(i))).get("CONTENT") != null) {
					cell2_1.setCellValue(((Map) (sheet2List1.get(i))).get(
							"CONTENT").toString());
				} else {
					cell2_1.setCellValue("");
				}
			}

			File f = new File(this.path);
			if (!f.exists()) {
				f.mkdirs();
			}
			FileOutputStream fos = new FileOutputStream(new File(this.path
					+ "\\temp_" + name + ".xlsx"));
			wb.write(fos);
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.setContentType(CONTENT_TYPE);

		File file = new File(this.path + "\\temp_" + name + ".xlsx");
		response.setContentType("application/x-msdownload");
		response.setContentLength((int) file.length());
		response.setHeader("Content-Disposition", "attachment;filename="
				+ "\\temp_" + name + ".xlsx");
		FileInputStream fis = new FileInputStream(file);
		BufferedInputStream buff = new BufferedInputStream(fis);

		byte[] b = new byte[2048];
		long k = 0;
		OutputStream myout = response.getOutputStream();
		while (k < file.length()) {
			int j = buff.read(b, 0, 2048);
			k += j;
			myout.write(b, 0, j);
		}
		myout.flush();
	}

	/**
	 * 导出数据到excel Description:export datas to excel
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings( { "unchecked", "deprecation" })
	public void exportExcelOneByOneSheetByName(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap,
			LinkedHashMap sqlContentmap, List aliasNameList, List aliasList,
			List sheet2List, String name) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String adminID = admin.getAdminID() == null ? "USERNO_"
				+ admin.getUserNo() : admin.getAdminID();
		this.type = request.getParameter("type");
		String CpnyId = admin.getCpnyId();
		this.path = request.getRealPath("");

		int init = path.indexOf("\\");
		String server = "win";
		if (init < 0) { // WebServer的os为unix/linux
			server = "u/l";
		}
		if ("win".equals(server)) {
			path = path + "\\resources\\temp\\download\\" + CpnyId + "\\"
					+ adminID + "\\";
		} else {
			path = path + "/resources/temp/download/" + CpnyId + "/" + adminID;
		}
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet1 = wb.createSheet("sheet1");
		XSSFRow row = sheet1.createRow(0);
		XSSFCell cell;

		XSSFSheet sheet2 = wb.createSheet("sheet2");
		// XSSFRow row2 = sheet1.createRow(0);
		// XSSFCell cell2;

		List result = null;
		try {
			/**
			 * 根据导出报表的类型，定义列名，并且从相应的表中查询数据
			 */
			// if(this.type!=null&&this.type.equals("InsuranceInputItemData")){
			row = sheet1.createRow(0);
			for (int i = 0; i < aliasNameList.size(); i++) {
				cell = row.createCell((short) i);
				cell.setCellValue(aliasNameList.get(i).toString());
				XSSFCellStyle cellStyle = wb.createCellStyle();
				// 指定单元格居右对齐
				cellStyle.setAlignment(HorizontalAlignment.RIGHT);
				cell.setCellStyle(cellStyle);
				if (i > 2) {
					sheet1.setColumnWidth(i, 700);
				}
			}
			if (sqlContentmap != null
					&& sqlContentmap.get("sqlContent") != null
					&& !sqlContentmap.get("sqlContent").equals("")) {
				result = this.getExcelExportDataList(sqlContentmap);
			}
			// }
			File file = new File(this.path + "\\temp_" + name + ".xlsx");
			file.delete();
			// --------------------------------------------------
			// boolean res = file.createNewFile();
			// if(!res)System.out.println("			//--------------------------------------------------创建失败！");
			// --------------------------------------------------
			if (result != null && result.size() > 0) {// 导出数据
				for (int i = 0; i < result.size(); i++) {
					int k = 0;
					row = sheet1.createRow(i + 1);
					for (int j = 0; j < aliasList.size(); j++) {
						cell = row.createCell((short) k++);
						cell
								.setCellValue(((Map) (result.get(i)))
										.get(aliasList.get(j).toString()) == null ? ""
										: ((Map) (result.get(i))).get(
												aliasList.get(j).toString())
												.toString());
					}
				}
			} else {// 下载导入模板
				List contentList = sqlContentmap.get("contentList") == null ? null
						: (List) sqlContentmap.get("contentList");
				if (contentList != null && contentList.size() > 0) {
					for (int i = 0; i < contentList.size(); i++) {
						int k = 0;
						row = sheet1.createRow(i + 1);
						Set keySet = ((Map) (contentList.get(i))).keySet();
						if (keySet != null) {
							Iterator it = keySet.iterator();
							while (it.hasNext()) {
								Object itValue = it.next();
								if (itValue != null) {
									cell = row.createCell((short) k++);
									cell.setCellValue(((Map) (contentList
											.get(i))).get(itValue).toString());
								}
							}
						}
					}
				}
			}
			XSSFRow rowAl = sheet2.createRow(1);
			XSSFCell cellAl = rowAl.createCell((short) 0);
			XSSFCell cellAl2 = rowAl.createCell((short) 1);
			cellAl.setCellValue("班次ID");
			cellAl2.setCellValue("班次名称");
			// row2 = sheet2.createRow(0);
			if (sheet2List != null && sheet2List.size() > 0) {// 设定sheet2的列头
				XSSFRow row2 = sheet2.createRow(0);
				XSSFCell cell2 = row2.createCell((short) 0);
				cell2.setCellValue((sheet2List.get(sheet2List.size() - 1))
						.toString());

			}
			for (int i = 0; i < sheet2List.size() - 1; i++) {// 设定sheet2里头的内容
				XSSFRow row2 = sheet2.createRow(i + 2);
				XSSFCell cell2 = row2.createCell((short) 0);
				if (sheet2List.get(i) != null
						&& ((Map) (sheet2List.get(i))).get("SHIFT_ID") != null) {
					cell2.setCellValue(((Map) (sheet2List.get(i))).get(
							"SHIFT_ID").toString());
				} else {
					cell2.setCellValue("");
				}
				XSSFCell cell3 = row2.createCell((short) 1);
				if (sheet2List.get(i) != null
						&& ((Map) (sheet2List.get(i))).get("SHIFT_NAME") != null) {
					cell3.setCellValue(((Map) (sheet2List.get(i))).get(
							"SHIFT_NAME").toString());
				} else {
					cell3.setCellValue("");
				}
			}
			File f = new File(this.path);
			if (!f.exists()) {
				f.mkdirs();
			}
			FileOutputStream fos = new FileOutputStream(new File(this.path
					+ "\\temp_" + name + ".xlsx"));
			wb.write(fos);
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.setContentType(CONTENT_TYPE);

		File file = new File(this.path + "\\temp_" + name + ".xlsx");
		response.setContentType("application/x-msdownload");
		response.setContentLength((int) file.length());
		response.setHeader("Content-Disposition", "attachment;filename="
				+ "\\temp_" + name + ".xlsx");
		FileInputStream fis = new FileInputStream(file);
		BufferedInputStream buff = new BufferedInputStream(fis);

		byte[] b = new byte[2048];
		long k = 0;
		OutputStream myout = response.getOutputStream();
		while (k < file.length()) {
			int j = buff.read(b, 0, 2048);
			k += j;
			myout.write(b, 0, j);
		}
		myout.flush();
	}

	/**
	 * 分析excel的各个列，获取各个列的值，并且导入的数据库中 Description:
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param modelMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ModelMap importSapSpecialEmpInfo(HttpServletRequest request,
			HttpServletResponse response, LinkedHashMap map, ModelMap modelMap) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String adminID = admin.getAdminID() == null ? "USERNO_"
				+ admin.getUserNo() : admin.getAdminID();
		ObjectBindUtil.getRequestParamData(request);
		this.filename = adminID;
		this.pathCpnyID = admin.getCpnyId();
		this.language = admin.getLanguage();
		if (this.processUploadFile(request, response)) {
			int returnInt = this.impSapSpecialEmpInfo(adminID, map, request);
			if (returnInt == 0) {
				modelMap.put("sign", "0");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.importfail", request));// 导入失败!
			} else if (returnInt < 0) {
				modelMap.put("sign", "-1");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.title.di", request)
						+ String.valueOf(0 - returnInt)
						+ TipMessage.getTipMessage(
								"ar.alert.message.excelimport.title.hang",
								request)
						+ TipMessage.getTipMessage(
								"pa.alert.message.empidisnullornotexist",
								request));// 导入的工号为空或者本公司不存在此工号！
			} else if (returnInt == 2) {
				modelMap.put("sign", "2");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"pa.alert.message.importdatamodelerror", request));// 特殊人员信息导入模版错误！
			} else if (returnInt == 1) {
				modelMap.put("sign", "1");
				modelMap.put("statusCode", "200");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.importsuccess", request));// 导入成功
			} else if (returnInt == 3) {
				modelMap.put("sign", "3");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.dataerror", request));// 数据列数有问题，请核对
			} else if (returnInt == 4) {
				modelMap.put("sign", "3");
				modelMap.put("statusCode", "300");
				// 删除之前导入的特殊人员发生错误，请联系管理员！
				modelMap.put("message", TipMessage.getTipMessage(
						"pa.alert.message.deleteimportdataerror", request));
			} else if (returnInt == 5) {
				modelMap.put("sign", "3");
				modelMap.put("statusCode", "300");
				// 导入的数据里存在多个月份，请检查数据里的月份！
				modelMap.put("message", TipMessage.getTipMessage(
						"pa.alert.message.importdataexistmonthes", request));
			} else if (returnInt > 10000 && returnInt < 20000) {
				modelMap.put("sign", "3");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.title.di", request)
						+ String.valueOf(returnInt - 10000)
						+ TipMessage.getTipMessage(
								"ar.alert.message.excelimport.title.hang",
								request)
						+ TipMessage.getTipMessage(
								"pa.alert.message.themontheserror", request));// 工资月份有错误，请检查核对！
			} else if (returnInt > 20000 && returnInt < 30000) {
				modelMap.put("sign", "3");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.title.di", request)
						+ String.valueOf(returnInt - 20000)
						+ TipMessage.getTipMessage(
								"ar.alert.message.excelimport.title.hang",
								request)
						// 导入的工资发放类型错误，只能导入C(现金发放),W(待转账发放)和F(转账发放或者重新发送)三种类型！
						+ TipMessage
								.getTipMessage(
										"pa.alert.message.importsendtypeerror",
										request));
			} else if (returnInt > 30000) {
				modelMap.put("sign", "3");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.title.di", request)
						+ String.valueOf(returnInt - 30000)
						+ TipMessage.getTipMessage(
								"ar.alert.message.excelimport.title.hang",
								request)
						// 此人本月已经导入过F类型的数据，不允许此人再以C或者W发送类型的数据导入。
						+ TipMessage.getTipMessage(
								"pa.alert.message.sendtypeturnerror", request));
			}

			int i = path.indexOf("\\");
			String server = "win";
			if (i < 0) { // WebServer的os为unix/linux
				server = "u/l";
			}
			if ("win".equals(server)) {
				File file = new File(this.path + "\\" + filename + ".xls");
				file.delete();
			} else {
				File file = new File(this.path + "/" + filename + ".xls");
				file.delete();
			}

		} else {
			modelMap.put("sign", "0");
			modelMap.put("statusCode", "300");
			modelMap.put("message", TipMessage.getTipMessage(
					"ar.alert.message.excelimport.importfail", request));// 导入失败!
		}
		return modelMap;
	}

	@SuppressWarnings( { "unchecked" })
	public int impSapSpecialEmpInfo(String adminID, LinkedHashMap cellmap,
			HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		int token = 1;
		int p = path.indexOf("\\");
		String server = "win";
		if (p < 0) { // WebServer的os为unix/linux
			server = "u/l";
		}
		File file = null;
		if ("win".equals(server)) {
			file = new File(path + "\\" + filename + ".xls");
		} else {
			file = new File(path + "/" + filename + ".xls");
		}
		Map dataMap = new HashMap();
		String tableName = cellmap.get("tableName") == null ? "" : cellmap.get(
				"tableName").toString();// 获取表名
		try {
			Workbook workBook = Workbook.getWorkbook(file);
			// 获得第一个工作表对象
			Sheet sheet = workBook.getSheet(0);
			// 验证是否存在导入数据
			if (sheet.getRows() < 2) {
				logger.debug("Import file not exist data, return.");
				return 0;
			}
			if (sheet.getRow(0).length < 4) {
				return 2;// 模板错误
			}
			String paMonth = "201210";
			List addList = new ArrayList();
			// 循环检查EXCEL中数据 规定 第一列 PA_MONTH,第二列
			// EMPID,第三列LOCAL_NAME,第四列SEND_TYPE(C:现金发放人员;W:待转账人员;F:再次发送人员)
			for (int n = 1; n < sheet.getRows(); n++) {
				Cell[] rowID = sheet.getRow(n);
				int rowLength = rowID.length;
				if (rowLength < 2) {
					return 3;// 数据列数有问题，请核对
				}
				if (rowID[0].getContents() == null
						|| "".equals(rowID[0].getContents().trim())) {
					return 10000 + n;// n行工资月份有错误
				}
				// 如果是第一行取其月份将值赋给paMonth（后面用到此参数）
				if (n == 1) {
					paMonth = rowID[0].getContents().trim().toString();
				}

				if (rowID[1].getContents() == null
						|| "".equals(rowID[1].getContents().trim())) {
					return -n;// n行工号有错误
				}
				if (rowID[3].getContents() == null
						|| "".equals(rowID[3].getContents().trim())) {
					return 20000 + n;// n行填写的工资发放类型为空了---（C代表现金发放;W代表待转账人员;F:转账人员或者需要重新发送人员;）。
				}
				if (rowID[3].getContents() != null
						|| !"".equals(rowID[3].getContents().trim())) {
					if (!"C".equals(rowID[3].getContents().trim())
							&& !"W".equals(rowID[3].getContents().trim())
							&& !"F".equals(rowID[3].getContents().trim())) {
						return 20000 + n;// n行填写的工资发放类型不对（C代表现金发放;W代表待转账人员;F:转账人员或者需要重新发送人员;）。
					}
				}
				// 组织参数
				LinkedHashMap tempMap = new LinkedHashMap();

				tempMap.put("CPNY_ID", admin.getCpnyId());
				tempMap.put("PA_MONTH",
						rowID[0].getContents() != null ? rowID[0].getContents()
								.trim() : "201210");
				tempMap.put("EMPID", rowID[1].getContents().trim());
				// tempMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());
				// tempMap.put("AR_SUMMARY_TABLE", tableName);
				// 查看此工号是否是本公司的员工工号
				if (this.checkPersonalInfo(tempMap) == 0) {
					return -n;// n行工号有错误，不存在此工号
				}
				// 如果导入的发放型为C,W的时候，判断此人本月是否已经导入过F类型的，如果已经导入过，则不允许此人再以C或者W发送类型导入。
				if ("C".equals(rowID[3].getContents().trim())
						|| "W".equals(rowID[3].getContents().trim())) {
					if (this.checkSpecialEmpImportCnt(tempMap) >= 1) {
						return 30000 + n;
					}
				}
			}
			// 将pa_data_to_sap_acutal_special中原来是C(现金发放)或者W(待转账)发放或者已经发过一次SAP的人现在变成F（转账）再次发送时，
			// 将此人放入summaryList中，以summaryList中的工号为条件，删除此人原来的C或者W类型数据
			List summaryList = new ArrayList();
			for (int m = 1; m < sheet.getRows(); m++) {
				Cell[] rowTemp = sheet.getRow(m);
				// 保证循环列ID<=当前行列数
				if (rowTemp.length == 4) {
					// 验证导入的数据里是否存在多个月份
					if (!rowTemp[0].getContents().trim().equals(paMonth)) {
						// 导入的数据里存在多个月份，不允许导入多个月份数据。
						return 5;
					}
					if (rowTemp[3].getContents() != null
							&& !"".equals(rowTemp[3].getContents().trim())) {
						LinkedHashMap tempMap = new LinkedHashMap();
						tempMap
								.put("PA_MONTH", rowTemp[0].getContents()
										.trim());
						tempMap.put("EMPID", rowTemp[1].getContents().trim());
						tempMap.put("CPNY_ID",
								admin.getCpnyId() != null ? admin.getCpnyId()
										.toString() : "C01");

						summaryList.add(tempMap);
					}
				}
			}
			// 删除pa_data_to_sap_acutal_special表中PA_MONTH这个月PERSON_ID此人的数据，重新导入
			if (this.deleteSapSpecialCaseAndWaitEmp(summaryList) != 1) {
				// 删除发放现金或者待转账人员数据出错,请联系管理员
				return 4;
			}
			LinkedHashMap aliasValueMapTemp = cellmap.get("aliasValueMap") == null ? null
					: (LinkedHashMap) cellmap.get("aliasValueMap");
			LinkedHashMap aliasTypeMap = cellmap.get("aliasTypeMap") == null ? null
					: (LinkedHashMap) cellmap.get("aliasTypeMap");
			// 循环工作表的每行数据
			for (int i = 1; i < sheet.getRows(); i++) {
				Cell[] row = sheet.getRow(i);

				LinkedHashMap smap = new LinkedHashMap();
				LinkedHashMap aliasCellmap = new LinkedHashMap();

				aliasCellmap.put("#CELL0#", row[0].getContents().trim());
				aliasCellmap.put("#CELL1#", row[1].getContents().trim());
				aliasCellmap.put("#CELL3#", row[3].getContents().trim());

				LinkedHashMap aliasValueI18nMap = cellmap
						.get("aliasValueI18nMap") == null ? null
						: (LinkedHashMap) cellmap.get("aliasValueI18nMap");

				String aliasKeySql = "";
				String aliasValueSql = "";
				int initKey = 0;
				if (aliasValueMapTemp != null && aliasTypeMap != null) {
					Set keySet = aliasValueMapTemp.keySet();
					if (keySet != null) {
						Iterator it = keySet.iterator();
						while (it.hasNext()) {
							initKey++;
							Object keyValue = it.next();
							if (keyValue != null && !keyValue.equals("")) {
								int fieldType = Integer.parseInt(aliasTypeMap
										.get(keyValue).toString());
								Object aliasCellmapValue = aliasCellmap
										.get(aliasValueMapTemp.get(keyValue));
								if (aliasCellmapValue == null) {// 不属于excel的列
									aliasCellmapValue = aliasValueMapTemp
											.get(keyValue);
								} else {
									if (aliasValueI18nMap != null
											&& aliasValueI18nMap.get(keyValue) != null) {// 有需要从国际化表读取No的列
										String sqlContent = aliasValueI18nMap
												.get(keyValue).toString();
										sqlContent = sqlContent.replaceAll(
												aliasValueMapTemp.get(keyValue)
														.toString(), "'"
														+ aliasCellmapValue
																.toString()
														+ "'");
										LinkedHashMap sqlI8nContentMap = new LinkedHashMap();
										sqlI8nContentMap.put("sqlI18nContent",
												sqlContent);
										aliasCellmapValue = this
												.getCodeNoByName(sqlI8nContentMap) == -1 ? ""
												: this
														.getCodeNoByName(sqlI8nContentMap);
									}
								}
								switch (fieldType) {
								case 0:
									if (initKey == 1) {
										aliasKeySql = keyValue.toString();
										aliasValueSql = "'" + aliasCellmapValue
												+ "'";
									} else {
										aliasKeySql += ","
												+ keyValue.toString();
										aliasValueSql += ",'"
												+ aliasCellmapValue + "'";
									}
									break;
								default:
									if (initKey == 1) {
										aliasKeySql = keyValue.toString();
										aliasValueSql = aliasCellmapValue
												.toString();
									} else {
										aliasKeySql += ","
												+ keyValue.toString();
										aliasValueSql += ","
												+ aliasCellmapValue + "";
									}
									break;
								}
							}
						}
					}
				}
				smap.put("tableName", tableName);
				smap.put("aliasNameKeyContent", aliasKeySql);
				smap.put("aliasValueContent", aliasValueSql);
				addList.add(smap);

			}
			dataMap.put("addList", addList);
			this.insertExcelData(dataMap);
		} catch (Exception e) { // 未找到文件异常
			e.printStackTrace();
			token = 0;
		}
		return token;
	}

	public int deleteSapSpecialCaseAndWaitEmp(Object name) {
		try {
			this.excelUtilDao.deleteSapSpecialCaseAndWaitEmp(name);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 查询excel导出需要填充的下拉列表值
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @Description: TODO
	 * @author zhengxiaochen zhengxiaochen@ait.net.cn
	 * @date 2013-8-29 下午02:57:43
	 * @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getExcelExportTypeList(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = new LinkedHashMap();
		paramMap.put("PARENT_CODE_NO", request.getParameter("parentNo"));
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("interLanguage", admin.getLanguage());
		return this.excelUtilDao.getExcelExportTypeList(paramMap);
	}

	/**
	 * 分析excel的各个列，获取各个列的值，并且导入的数据库中 Description:(奖励/惩戒)
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @Description: TODO
	 * @author zhengxiaochen zhengxiaochen@ait.net.cn
	 * @date 2013-8-29 下午07:31:37
	 * @version V1.0
	 */
	@SuppressWarnings("unchecked")
	public ModelMap importDataForTransferOrder(HttpServletRequest request,
			HttpServletResponse response, LinkedHashMap map, ModelMap modelMap) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String adminID = admin.getAdminID() == null ? "USERNO_"
				+ admin.getUserNo() : admin.getAdminID();
		ObjectBindUtil.getRequestParamData(request);
		this.filename = adminID;
		this.pathCpnyID = admin.getCpnyId();
		this.language = admin.getLanguage();
		if (this.processUploadFile(request, response)) {
			int returnInt = this.impDataBaseForTransferOrder(request, adminID,
					map);
			if (returnInt == 0) {
				modelMap.put("sign", "0");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.importfail", request));// 导入失败!
			} else if (returnInt < 0) {
				modelMap.put("sign", "-1");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.writebyrule", request));// 调整事由有误，请按规则填写！
			} else if (returnInt == 2) {
				modelMap.put("sign", "2");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.existnull", request));// 表有空值或有空单元行，请检查！
			} else if (returnInt == 1) {
				modelMap.put("sign", "1");
				modelMap.put("statusCode", "200");
				modelMap.put("navTabId", "hr0220");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.importsuccess", request));// 导入成功
				modelMap.put("forwardUrl",
						"/hrm/transferOrder/viewRewardAndPunishment?transferOrder_type="
								+ StringUtil.checkNull(request
										.getParameter("transferOrder_type"))
								+ "&navTabId=hr0220");
			} else if (returnInt == 5) {
				modelMap.put("sign", "-1");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.importfailcalladmin",
						request));// 更新此人上一条数据出错，请联系管理员
			} else if (returnInt == 6) {
				modelMap.put("sign", "-1");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"alert.message.pa.bonus.thisDataIsExist_add_fail",
						request));// 此数据已经存在，请核对数据后再进行导入!
			}
			File file = new File(this.path + "\\" + filename + ".xls");
			file.delete();
		} else {
			modelMap.put("sign", "0");
			modelMap.put("statusCode", "300");
			modelMap.put("message", TipMessage.getTipMessage(
					"ar.alert.message.excelimport.importfail", request));// 导入失败!
		}
		return modelMap;
	}

	/**
	 * Excel导入(奖励/惩戒)
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @Description: TODO
	 * @author zhengxiaochen zhengxiaochen@ait.net.cn
	 * @date 2013-8-29 下午07:31:37
	 * @version V1.0
	 */
	@SuppressWarnings( { "unchecked" })
	public int impDataBaseForTransferOrder(HttpServletRequest request,
			String adminID, LinkedHashMap cellmap) {
		int token = 0;
		int k = path.indexOf("\\");
		String server = "win";
		if (k < 0) { // WebServer的os为unix/linux
			server = "u/l";
		}
		File file = null;
		if ("win".equals(server)) {
			file = new File(path + "\\" + filename + ".xls");
		} else {
			file = new File(path + "/" + filename + ".xls");
		}
		Map dataMap = new HashMap();
		try {
			Workbook workBook = Workbook.getWorkbook(file);
			// 获得第一个工作表对象
			Sheet sheet = workBook.getSheet(0);
			// 验证是否存在导入数据
			if (sheet.getRows() < 2) {
				logger.debug("Import file not exist data, return.");
				return token;
			}
			String tableName = cellmap.get("tableName") == null ? "" : cellmap
					.get("tableName").toString();// 获取表名
			LinkedHashMap aliasValueMap = cellmap.get("aliasValueMap") == null ? null
					: (LinkedHashMap) cellmap.get("aliasValueMap");
			LinkedHashMap aliasTypeMap = cellmap.get("aliasTypeMap") == null ? null
					: (LinkedHashMap) cellmap.get("aliasTypeMap");
			LinkedHashMap aliasValueI18nMap = cellmap.get("aliasValueI18nMap") == null ? null
					: (LinkedHashMap) cellmap.get("aliasValueI18nMap");

			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			for (int m = 1; m < sheet.getRows(); m++) {
				Cell[] rowTemp = sheet.getRow(m);
				// 检查是否存在要导入的的数据，如果存在，即更新其结束日期
				if ("HR_REWARD_SAVE".equals(tableName)) {
					LinkedHashMap tempMap = new LinkedHashMap();
					tempMap.put("EMPID", rowTemp[0].getContents().trim());
					String[] rewardDateStr = rowTemp[2].getContents().trim()
							.split("/");
					tempMap.put("REWARD_DATE", rewardDateStr[0] + "-"
							+ rewardDateStr[1] + "-" + rewardDateStr[2]);
					tempMap.put("CPNY_ID", admin.getCpnyId());
					tempMap.put("TABLE_NAME", tableName);
					if (this.excelUtilDao.preHasRecordForReward(tempMap) == 1) {
						return token = 6;// 此数据已经存在，请核对数据后再进行导入!
					}
				}

				if ("HR_PUNISHMENT_SAVE".equals(tableName)) {
					LinkedHashMap tempMap = new LinkedHashMap();
					tempMap.put("EMPID", rowTemp[0].getContents().trim());
					String[] punishmentDateStr = rowTemp[2].getContents()
							.trim().split("/");
					tempMap
							.put("DATE_PUNISHED", punishmentDateStr[0] + "-"
									+ punishmentDateStr[1] + "-"
									+ punishmentDateStr[2]);
					tempMap.put("CPNY_ID", admin.getCpnyId());
					tempMap.put("TABLE_NAME", tableName);
					if (this.excelUtilDao.preHasRecordForPunishment(tempMap) == 1) {
						return token = 6;// 此数据已经存在，请核对数据后再进行导入!
					}
				}
			}

			LinkedHashMap aliasMapCells = new LinkedHashMap();
			if (aliasValueMap != null && aliasTypeMap != null) {// 存放CELL0：NUMBER类型的键值对
				Set keySet = aliasValueMap.keySet();
				if (keySet != null) {
					Iterator it = keySet.iterator();
					while (it.hasNext()) {
						Object keyvalue = it.next();
						if (keyvalue != null) {
							Object aliasValue = aliasValueMap.get(keyvalue);
							if (aliasValue != null) {
								aliasMapCells.put(aliasValue.toString(),
										aliasTypeMap.get(keyvalue));
							}
						}
					}
				}
			}
			List addList = new ArrayList();
			// 循环工作表的每行数据
			int init = 0;
			for (int i = 1; i < sheet.getRows(); i++) {
				// 验证当前行是否符合规范
				if (!validateCell(sheet.getRow(i), sheet.getRow(0).length)) {// 如果所在行全为空，不导入
					break;
				}
				Cell[] row = sheet.getRow(i);
				if (init == 0) {
					init = sheet.getRow(0).length;
				}
				LinkedHashMap smap = new LinkedHashMap();
				LinkedHashMap aliasCellmap = new LinkedHashMap();// 读取Excel数据
				for (int j = 0; j < init; j++) {
					try {
						String cellValue = "";
						Object cellType = aliasMapCells.get("#CELL" + j + "#");
						if (cellType != null
								&& !row[j].getContents().equals("")) {// date类型
							try {
								cellValue = this.FormateData(row[j], cellType);
								if (cellValue.contains("/")) {
									String[] contentStr = cellValue.split("/");
									if (contentStr.length == 3
											&& contentStr[0].length() == 2
											&& contentStr[2].length() == 4) {
										cellValue = contentStr[2] + "/"
												+ contentStr[1] + "/"
												+ contentStr[0];
									}
								} else if (cellValue.contains("-")) {
									String[] contentStr = cellValue.split("-");
									if (contentStr.length == 3
											&& contentStr[0].length() == 2
											&& contentStr[2].length() == 4) {
										cellValue = contentStr[2] + "-"
												+ contentStr[1] + "-"
												+ contentStr[0];
									}
								}
							} catch (Exception e) {
								cellValue = "";
								if (row[j].getContents() != null) {
									cellValue = row[j].getContents();
									if (cellValue.contains("/")) {
										String[] contentStr = cellValue
												.split("/");
										if (contentStr.length == 3
												&& contentStr[0].length() == 2
												&& contentStr[2].length() == 4) {
											cellValue = contentStr[2] + "/"
													+ contentStr[1] + "/"
													+ contentStr[0];
										}
									} else if (cellValue.contains("-")) {
										String[] contentStr = cellValue
												.split("-");
										if (contentStr.length == 3
												&& contentStr[0].length() == 2
												&& contentStr[2].length() == 4) {
											cellValue = contentStr[2] + "-"
													+ contentStr[1] + "-"
													+ contentStr[0];
										}
									}
								}
							}
						} else {
							cellValue = row[j].getContents() == null ? ""
									: row[j].getContents();
						}
						aliasCellmap.put("#CELL" + j + "#",
								cellValue == null ? "" : cellValue.trim());
					} catch (Exception e) {
						e.printStackTrace();
						return token = 2;
					}
				}
				String aliasKeySql = "";
				String aliasValueSql = "";
				int initKey = 0;
				if (aliasValueMap != null && aliasTypeMap != null) {
					Set keySet = aliasValueMap.keySet();
					if (keySet != null) {
						Iterator it = keySet.iterator();
						while (it.hasNext()) {
							initKey++;
							Object keyValue = it.next();
							if (keyValue != null && !keyValue.equals("")) {
								int fieldType = Integer.parseInt(aliasTypeMap
										.get(keyValue).toString());
								Object aliasCellmapValue = aliasCellmap
										.get(aliasValueMap.get(keyValue));
								if (aliasCellmapValue == null) {// 不属于excel的列
									aliasCellmapValue = aliasValueMap
											.get(keyValue);
									if (aliasCellmapValue != null
											&& aliasCellmapValue.toString()
													.contains("#CELL")) {
										String sqlContent = aliasCellmapValue
												.toString();
										for (int l = 0; l < init; l++) {
											sqlContent = sqlContent.replaceAll(
													"#CELL" + l + "#",
													aliasCellmap.get(
															"#CELL" + l + "#")
															.toString());
											if (!sqlContent.contains("#CELL")) {
												break;
											}
										}
										LinkedHashMap sqlI8nContentMap = new LinkedHashMap();
										switch (fieldType) {
										case 2:
											sqlI8nContentMap.put(
													"sqlI18nContent",
													sqlContent);
											aliasCellmapValue = this
													.getCodeNoByName(sqlI8nContentMap) == -1 ? ""
													: this
															.getCodeNoByName(sqlI8nContentMap);
											break;
										default:
											sqlI8nContentMap.put("sqlContent",
													sqlContent);
											if (this
													.getContentNoByFiled(sqlI8nContentMap) != null) {
												Object cellObj = this
														.getContentNoByFiled(
																sqlI8nContentMap)
														.size() == 0 ? ""
														: this
																.getContentNoByFiled(
																		sqlI8nContentMap)
																.get(0);
												if (cellObj != null
														&& !cellObj.equals("")
														&& ((Map) cellObj)
																.get(keyValue
																		.toString()) != null) {
													aliasCellmapValue = (cellObj == null ? ""
															: ((Map) cellObj)
																	.get(
																			keyValue
																					.toString()
																					.toUpperCase())
																	.toString());
												} else {
													aliasCellmapValue = "";
												}
											}

											break;
										}
									}
								} else {
									if (aliasValueI18nMap != null
											&& aliasValueI18nMap.get(keyValue) != null) {// 有需要从国际化表读取No的列
										String sqlContent = aliasValueI18nMap
												.get(keyValue) == null ? ""
												: aliasValueI18nMap.get(
														keyValue).toString();
										sqlContent = sqlContent
												.replaceAll(
														aliasValueMap
																.get(keyValue) == null ? ""
																: aliasValueMap
																		.get(
																				keyValue)
																		.toString(),
														"'"
																+ aliasCellmapValue
																		.toString()
																+ "'");

										LinkedHashMap sqlI8nContentMap = new LinkedHashMap();
										sqlI8nContentMap.put("sqlI18nContent",
												sqlContent);
										if (aliasValueI18nMap.get(keyValue
												+ "_FLAG") != null
												&& aliasValueI18nMap.get(
														keyValue + "_FLAG")
														.equals("Y")) {
											sqlI8nContentMap.put("sqlContent",
													sqlContent);
											List contentList = this
													.getContentNoByFiled(sqlI8nContentMap);
											if (contentList.get(0) != null
													&& ((Map) (contentList
															.get(0)))
															.get("CPNY_ID") != null) {
												aliasCellmapValue = (contentList == null ? ""
														: ((Map) (contentList
																.get(0))).get(
																"CPNY_ID")
																.toString());
											} else if (contentList.get(0) != null
													&& ((Map) (contentList
															.get(0)))
															.get("DEPTNO") != null) {
												aliasCellmapValue = (contentList == null ? ""
														: ((Map) (contentList
																.get(0))).get(
																"DEPTNO")
																.toString());
											} else {
												aliasCellmapValue = "";
											}
										} else {
											aliasCellmapValue = this
													.getCodeNoByName(sqlI8nContentMap) == -1 ? ""
													: this
															.getCodeNoByName(sqlI8nContentMap);
										}
										if (aliasCellmapValue.equals("")) {
											System.out
													.println(aliasValueMap.get(
															keyValue)
															.toString()
															+ "-----------------------------------");
										}
									}
								}
								switch (fieldType) {
								case 0:
									if (initKey == 1) {
										aliasKeySql = keyValue.toString();
										aliasValueSql = "'" + aliasCellmapValue
												+ "'";
									} else {
										aliasKeySql += ","
												+ keyValue.toString();
										aliasValueSql += ",'"
												+ aliasCellmapValue + "'";
									}
									break;
								case 1:
									if (initKey == 1) {
										aliasKeySql = keyValue.toString();
										if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("/")) {
											aliasValueSql = "to_date('"
													+ aliasCellmapValue
													+ "','YYYY/MM/DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										} else if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("-")) {
											aliasValueSql = "to_date('"
													+ aliasCellmapValue
													+ "','YYYY-MM-DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										}
									} else {
										aliasKeySql += ","
												+ keyValue.toString();
										if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("/")) {
											aliasValueSql += ",to_date('"
													+ aliasCellmapValue
													+ "','YYYY/MM/DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										} else if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("-")) {
											aliasValueSql += ",to_date('"
													+ aliasCellmapValue
													+ "','YYYY-MM-DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										}
									}
									break;
								case 11:
									if (initKey == 1) {
										aliasKeySql = keyValue.toString();
										if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("/")) {
											aliasValueSql = "to_date('"
													+ aliasCellmapValue
													+ "','YYYY/MM/DD HH24:MI:SS')";// "'"+aliasCellmapValue+"'";
										} else if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("-")) {
											aliasValueSql = "to_date('"
													+ aliasCellmapValue
													+ "','YYYY-MM-DD HH24:MI:SS')";// "'"+aliasCellmapValue+"'";
										}
									} else {
										aliasKeySql += ","
												+ keyValue.toString();
										if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("/")) {
											aliasValueSql += ",to_date('"
													+ aliasCellmapValue
													+ "','YYYY/MM/DD HH24:MI:SS')";// "'"+aliasCellmapValue+"'";
										} else if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("-")) {
											aliasValueSql += ",to_date('"
													+ aliasCellmapValue
													+ "','YYYY-MM-DD HH24:MI:SS')";
										}
									}
									break;
								case 12:
									if (initKey == 1) {
										aliasKeySql = keyValue.toString();
										if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("/")) {
											aliasValueSql = "to_date('"
													+ aliasCellmapValue
													+ "','YYYY/MM/DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										} else if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("-")) {
											aliasValueSql = "to_date('"
													+ aliasCellmapValue
													+ "','YYYY-MM-DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										}
									} else {
										aliasKeySql += ","
												+ keyValue.toString();
										if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("/")) {
											aliasValueSql += ",to_date('"
													+ aliasCellmapValue
													+ "','YYYY/MM/DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										} else if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("-")) {
											aliasValueSql += ",to_date('"
													+ aliasCellmapValue
													+ "','YYYY-MM-DD HH24:MI')";
										}
									}
									break;
								default:
									if (initKey == 1) {
										aliasKeySql = keyValue.toString();
										aliasValueSql = (aliasCellmapValue == null ? "null"
												: aliasCellmapValue.toString());
									} else {
										aliasKeySql += ","
												+ keyValue.toString();
										aliasValueSql += ","
												+ (aliasCellmapValue == null ? "null"
														: aliasCellmapValue
																.toString())
												+ "";
									}
									break;
								}
							}
						}
					}
				}
				smap.put("tableName", tableName);

				aliasKeySql += ",EXP_INSIDE_NO ";
				aliasValueSql += ",hr_exp_insid_seq.nextval ";
				smap.put("aliasNameKeyContent", aliasKeySql);
				smap.put("aliasValueContent", aliasValueSql);
				addList.add(smap);
			}
			dataMap.put("addList", addList);
			this.insertExcelDataForTransferOrder(dataMap);
			token = 1;

		} catch (Exception e) { // 未找到文件异常
			e.printStackTrace();
			token = 0;
		}
		return token;
	}

	/**
	 * 将excel的数据导入到数据库中
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @Description: TODO
	 * @author zhengxiaochen zhengxiaochen@ait.net.cn
	 * @date 2013-8-30 下午01:56:44
	 * @version V1.0
	 */
	public void insertExcelDataForTransferOrder(Object obj) throws Exception {
		this.excelUtilDao.insertExcelDataForTransferOrder(obj);

	}

	/**
	 * 获得导入Excel的类型编号(异动)
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @Description: TODO
	 * @author zhengxiaochen zhengxiaochen@ait.net.cn
	 * @date 2013-9-2 上午01:46:34
	 * @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@Override
	public LinkedHashMap getImportExcelTransferOrderType(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap transCodeAndEmpIdMap = new LinkedHashMap();
		String adminID = admin.getAdminID() == null ? "USERNO_"
				+ admin.getUserNo() : admin.getAdminID();
		ObjectBindUtil.getRequestParamData(request);
		Date dt = new Date();
		this.filename = adminID + dt.getTime();
		this.pathCpnyID = admin.getCpnyId();
		this.language = admin.getLanguage();
		if (this.processUploadFile(request, response)) {
			int k = path.indexOf("\\");
			String server = "win";
			if (k < 0) { // WebServer的os为unix/linux
				server = "u/l";
			}
			File file = null;
			if ("win".equals(server)) {
				file = new File(path + "\\" + filename + ".xls");
			} else {
				file = new File(path + "/" + filename + ".xls");
			}
			// Map dataMap = new HashMap();
			Workbook workBook = Workbook.getWorkbook(file);
			// 获得第一个工作表对象
			Sheet sheet = workBook.getSheet(0);
			// 验证是否存在导入数据
			if (sheet.getRows() < 2) {
				logger.debug("Import file not exist data, return.");
				transCodeAndEmpIdMap.put("token", "0");
				return transCodeAndEmpIdMap;
			}
			String transCodeName = sheet.getRow(1)[0].getContents().trim();// 获得Excel文件中调令类型的名称
			// String empId=sheet.getRow(1)[1].getContents().trim();//获得EMPID
			LinkedHashMap paramMap = new LinkedHashMap();
			String transCode_sqlI18n = "SELECT  T.CODE_NO FROM  SY_CODE  T,SY_CODE_PARAM  SP,SY_GLOBAL_NAME S1 "
					+ "WHERE T.PARENT_CODE_NO = 123313 AND T.CODE_NO = SP.CODE_NO AND SP.CPNY_ID = '"
					+ this.pathCpnyID
					+ "' AND T.CODE_NO = S1.NO(+) "
					+ "AND S1.LANGUAGE(+)='"
					+ admin.getLanguage()
					+ "' AND T.ACTIVITY ='1" + "'";
			paramMap.put("sqlContent", transCode_sqlI18n);
			String transCode = this.excelUtilDao
					.getImportExcelTransferOrderType(paramMap);
			// String
			// empId_sqlI18n="SELECT PERSON_ID FROM HR_EMPLOYEE WHERE EMPID='"+empId+"' AND CPNY_ID='"+this.pathCpnyID+"'";
			// paramMap.put("sqlContent", empId_sqlI18n);
			// String
			// personId=this.excelUtilDao.getImportExcelTransferOrderType(paramMap);
			String transCodeFromPage = request
					.getParameter("transferOrder_type");
			if (!transCode.equals(transCodeFromPage)) {
				// 导入Excel的调令类型和页面选择的类型不一致
				transCodeAndEmpIdMap.put("token", "7");
				return transCodeAndEmpIdMap;
			} else {
				transCodeAndEmpIdMap.put("transCode", transCode);// 返回transCode
				// transCodeAndEmpIdMap.put("PERSON_ID", personId);//返回empId
				return transCodeAndEmpIdMap;
			}
		}
		return transCodeAndEmpIdMap;
	}

	/**
	 * 获得导入Excel的类型配置集合(异动)
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @Description: TODO
	 * @author zhengxiaochen zhengxiaochen@ait.net.cn
	 * @date 2013-9-2 上午01:30:41
	 * @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getImportTransferOrderTypeConfigList(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("ADMIN_ID", admin.getAdminID());
		paramMap.put("TRANS_CODE", request.getParameter("transferOrder_type"));
		paramMap.put("CPNYID", admin.getCpnyId());
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("LAN", admin.getLanguage());
		paramMap.put("interLanguage", admin.getLanguage());
		paramMap.put("ADMINID", admin.getAdminID());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());

		paramMap.put("TRANS_CONFIG_FLAG", "2");
		paramMap.put("NOT_DISTINCT", "'TRANS_ORDER_TYPE','EMPID','LOCAL_NAME'");

		return this.excelUtilDao.getImportTransferOrderTypeConfigList(paramMap);
	}

	/**
	 * Excel导入(异动)
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @Description: TODO
	 * @author zhengxiaochen zhengxiaochen@ait.net.cn
	 * @date 2013-9-2 下午02:11:13
	 * @version V1.0
	 */
	@SuppressWarnings("unchecked")
	public ModelMap importDataForOrderOperation(HttpServletRequest request,
			HttpServletResponse response, LinkedHashMap map, ModelMap modelMap,
			Integer transferOrderType) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String adminID = admin.getAdminID() == null ? "USERNO_"
				+ admin.getUserNo() : admin.getAdminID();
		ObjectBindUtil.getRequestParamData(request);
		if (this.filename.equals("")) {
			this.filename = adminID;
		}
		this.pathCpnyID = admin.getCpnyId();
		this.language = admin.getLanguage();
		if (transferOrderType > 10) {
			int returnInt = this.importBaseDataForOrderOperation(request,
					adminID, map);
			if (returnInt == 0) {
				modelMap.put("sign", "0");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.importfail", request));// 导入失败!
			} else if (returnInt < 0) {
				modelMap.put("sign", "-1");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.writebyrule", request));// 调整事由有误，请按规则填写！
			} else if (returnInt == 2) {
				modelMap.put("sign", "2");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.existnull", request));// 表有空值或有空单元行，请检查！
			} else if (returnInt == 1) {
				modelMap.put("sign", "1");
				modelMap.put("statusCode", "200");
				modelMap.put("navTabId", "hr0220");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.importsuccess", request));// 导入成功
				modelMap.put("forwardUrl",
						"/hrm/transferOrder/viewRewardAndPunishment?transferOrder_type="
								+ StringUtil.checkNull(request
										.getParameter("transferOrder_type"))
								+ "&navTabId=hr0220");
			} else if (returnInt == 5) {
				modelMap.put("sign", "-1");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.importfailcalladmin",
						request));// 更新此人上一条数据出错，请联系管理员
			} else if (returnInt == 6) {
				modelMap.put("sign", "-1");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"alert.message.pa.bonus.thisDataIsExist_add_fail",
						request));// 此数据已经存在，请核对数据后再进行导入!
			}
			File file = new File(this.path + "\\" + filename + ".xls");
			file.delete();
		} else {
			modelMap.put("sign", "0");
			modelMap.put("statusCode", "300");
			modelMap.put("message", TipMessage.getTipMessage(
					"ar.alert.message.excelimport.importfail", request));// 导入失败!
		}
		return modelMap;
	}

	/**
	 * Excel导入(异动)
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @Description: TODO
	 * @author zhengxiaochen zhengxiaochen@ait.net.cn
	 * @date 2013-8-29 下午07:31:37
	 * @version V1.0
	 */
	@SuppressWarnings( { "unchecked" })
	public int importBaseDataForOrderOperation(HttpServletRequest request,
			String adminID, LinkedHashMap cellmap) {
		int token = 0;
		int k = path.indexOf("\\");
		String server = "win";
		if (k < 0) { // WebServer的os为unix/linux
			server = "u/l";
		}
		File file = null;
		if ("win".equals(server)) {
			file = new File(path + "\\" + filename + ".xls");
		} else {
			file = new File(path + "/" + filename + ".xls");
		}
		Map dataMap = new HashMap();
		try {
			Workbook workBook = Workbook.getWorkbook(file);
			// 获得第一个工作表对象
			Sheet sheet = workBook.getSheet(0);

			// 验证是否存在导入数据
			if (sheet.getRows() < 2) {
				logger.debug("Import file not exist data, return.");
				return token;
			}
			String tableName = cellmap.get("tableName") == null ? "" : cellmap
					.get("tableName").toString();// 获取表名
			LinkedHashMap aliasValueMap = cellmap.get("aliasValueMap") == null ? null
					: (LinkedHashMap) cellmap.get("aliasValueMap");
			LinkedHashMap aliasTypeMap = cellmap.get("aliasTypeMap") == null ? null
					: (LinkedHashMap) cellmap.get("aliasTypeMap");
			LinkedHashMap aliasValueI18nMap = cellmap.get("aliasValueI18nMap") == null ? null
					: (LinkedHashMap) cellmap.get("aliasValueI18nMap");

			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			for (int i = 1; i < sheet.getRows(); i++) {
				Cell[] rowTemp = sheet.getRow(i);
				// 检查是否存在要导入的的数据，如果存在，即更新其结束日期
				if ("HR_EXPERIENCE_INSIDE_SAVE".equals(tableName)) {
					LinkedHashMap tempMap = new LinkedHashMap();
					tempMap.put("TRANS_NO", request
							.getParameter("transferOrder_type"));
					tempMap.put("EMPID", rowTemp[1].getContents().trim());
					tempMap.put("CPNY_ID", admin.getCpnyId());
					tempMap.put("TABLE_NAME", tableName);
					if (this.excelUtilDao.preHasRecordInCurrentType(tempMap) == 1) {
						return token = 6;// 此数据已经存在，请核对数据后再进行导入!
					}
				}
				// break;//目前只导入一行数据,导入多行时删除即可
			}

			LinkedHashMap aliasMapCells = new LinkedHashMap();
			if (aliasValueMap != null && aliasTypeMap != null) {// 存放CELL0：NUMBER类型的键值对
				Set keySet = aliasValueMap.keySet();
				if (keySet != null) {
					Iterator it = keySet.iterator();
					while (it.hasNext()) {
						Object keyvalue = it.next();
						if (keyvalue != null) {
							Object aliasValue = aliasValueMap.get(keyvalue);
							if (aliasValue != null) {
								aliasMapCells.put(aliasValue.toString(),
										aliasTypeMap.get(keyvalue));
							}
						}
					}
				}
			}
			List addList = new ArrayList();
			// 循环工作表的每行数据
			int init = 0;
			for (int i = 1; i < sheet.getRows(); i++) {
				// 验证当前行是否符合规范
				if (!validateCell(sheet.getRow(i), sheet.getRow(0).length)) {// 如果所在行全为空，不导入
					break;
				}
				Cell[] row = sheet.getRow(i);

				if (init == 0) {

					init = sheet.getRow(0).length;
				}
				LinkedHashMap smap = new LinkedHashMap();
				LinkedHashMap aliasCellmap = new LinkedHashMap();// 读取Excel数据
				for (int j = 0; j < init; j++) {
					try {
						String cellValue = "";
						Object cellType = aliasMapCells.get("#CELL" + j + "#");
						if (cellType != null
								&& !row[j].getContents().equals("")) {// date类型
							try {
								cellValue = this.FormateData(row[j], cellType);
								if (cellValue.contains("/")) {
									String[] contentStr = cellValue.split("/");
									if (contentStr.length == 3
											&& contentStr[0].length() == 2
											&& contentStr[2].length() == 4) {
										cellValue = contentStr[2] + "/"
												+ contentStr[1] + "/"
												+ contentStr[0];
									}
								} else if (cellValue.contains("-")) {
									String[] contentStr = cellValue.split("-");
									if (contentStr.length == 3
											&& contentStr[0].length() == 2
											&& contentStr[2].length() == 4) {
										cellValue = contentStr[2] + "-"
												+ contentStr[1] + "-"
												+ contentStr[0];
									}
								}
							} catch (Exception e) {
								cellValue = "";
								if (row[j].getContents() != null) {
									cellValue = row[j].getContents();
									if (cellValue.contains("/")) {
										String[] contentStr = cellValue
												.split("/");
										if (contentStr.length == 3
												&& contentStr[0].length() == 2
												&& contentStr[2].length() == 4) {
											cellValue = contentStr[2] + "/"
													+ contentStr[1] + "/"
													+ contentStr[0];
										}
									} else if (cellValue.contains("-")) {
										String[] contentStr = cellValue
												.split("-");
										if (contentStr.length == 3
												&& contentStr[0].length() == 2
												&& contentStr[2].length() == 4) {
											cellValue = contentStr[2] + "-"
													+ contentStr[1] + "-"
													+ contentStr[0];
										}
									}
								}
							}
						} else {
							cellValue = row[j].getContents() == null ? ""
									: row[j].getContents();
						}
						aliasCellmap.put("#CELL" + j + "#",
								cellValue == null ? "" : cellValue.trim());
					} catch (Exception e) {
						e.printStackTrace();
						return token = 2;
					}
				}
				String aliasKeySql = "";
				String aliasValueSql = "";
				int initKey = 0;
				if (aliasValueMap != null && aliasTypeMap != null) {
					Set keySet = aliasValueMap.keySet();
					if (keySet != null) {
						Iterator it = keySet.iterator();
						while (it.hasNext()) {
							initKey++;
							Object keyValue = it.next();
							if (keyValue != null && !keyValue.equals("")) {
								int fieldType = Integer.parseInt(aliasTypeMap
										.get(keyValue).toString());
								Object aliasCellmapValue = aliasCellmap
										.get(aliasValueMap.get(keyValue));
								if (aliasCellmapValue == null) {// 不属于excel的列
									aliasCellmapValue = aliasValueMap
											.get(keyValue);
									if (aliasCellmapValue != null
											&& aliasCellmapValue.toString()
													.contains("#CELL")) {
										String sqlContent = aliasCellmapValue
												.toString();
										for (int l = 0; l < init; l++) {
											sqlContent = sqlContent.replaceAll(
													"#CELL" + l + "#",
													aliasCellmap.get(
															"#CELL" + l + "#")
															.toString());
											if (!sqlContent.contains("#CELL")) {
												break;
											}
										}
										LinkedHashMap sqlI8nContentMap = new LinkedHashMap();
										switch (fieldType) {
										case 2:
											sqlI8nContentMap.put(
													"sqlI18nContent",
													sqlContent);
											aliasCellmapValue = this
													.getCodeNoByName(sqlI8nContentMap) == -1 ? ""
													: this
															.getCodeNoByName(sqlI8nContentMap);
											break;
										default:
											sqlI8nContentMap.put("sqlContent",
													sqlContent);
											if (this
													.getContentNoByFiled(sqlI8nContentMap) != null) {
												Object cellObj = this
														.getContentNoByFiled(
																sqlI8nContentMap)
														.size() == 0 ? ""
														: this
																.getContentNoByFiled(
																		sqlI8nContentMap)
																.get(0);
												if (cellObj != null
														&& !cellObj.equals("")
														&& ((Map) cellObj)
																.get(keyValue
																		.toString()) != null) {
													aliasCellmapValue = (cellObj == null ? ""
															: ((Map) cellObj)
																	.get(
																			keyValue
																					.toString()
																					.toUpperCase())
																	.toString());
												} else {
													aliasCellmapValue = "";
												}
											}

											break;
										}
									}
									if (aliasCellmapValue != null
											&& aliasCellmapValue.toString()
													.contains("EXP_INSIDE_NO")) {
										String sqlContent = aliasCellmapValue
												.toString();
										LinkedHashMap sqlI8nContentMap = new LinkedHashMap();
										switch (fieldType) {
										case 2:
											sqlI8nContentMap.put(
													"sqlI18nContent",
													sqlContent);
											aliasCellmapValue = this
													.getCodeNoByName(sqlI8nContentMap) == -1 ? ""
													: this
															.getCodeNoByName(sqlI8nContentMap);
											break;
										default:
											sqlI8nContentMap.put("sqlContent",
													sqlContent);
											if (this
													.getContentNoByFiled(sqlI8nContentMap) != null) {
												Object cellObj = this
														.getContentNoByFiled(
																sqlI8nContentMap)
														.size() == 0 ? ""
														: this
																.getContentNoByFiled(
																		sqlI8nContentMap)
																.get(0);
												if (cellObj != null
														&& !cellObj.equals("")
														&& ((Map) cellObj)
																.get(keyValue
																		.toString()) != null) {
													aliasCellmapValue = (cellObj == null ? ""
															: ((Map) cellObj)
																	.get(
																			keyValue
																					.toString()
																					.toUpperCase())
																	.toString());
												} else {
													aliasCellmapValue = "";
												}
											}

											break;
										}
									}
								} else {
									if (aliasValueI18nMap != null
											&& aliasValueI18nMap.get(keyValue) != null) {// 有需要从国际化表读取No的列
										String sqlContent = aliasValueI18nMap
												.get(keyValue) == null ? ""
												: aliasValueI18nMap.get(
														keyValue).toString();
										sqlContent = sqlContent
												.replaceAll(
														aliasValueMap
																.get(keyValue) == null ? ""
																: aliasValueMap
																		.get(
																				keyValue)
																		.toString(),
														"'"
																+ aliasCellmapValue
																		.toString()
																+ "'");

										LinkedHashMap sqlI8nContentMap = new LinkedHashMap();
										sqlI8nContentMap.put("sqlI18nContent",
												sqlContent);
										if (aliasValueI18nMap.get(keyValue
												+ "_FLAG") != null
												&& aliasValueI18nMap.get(
														keyValue + "_FLAG")
														.equals("Y")) {
											sqlI8nContentMap.put("sqlContent",
													sqlContent);
											List contentList = this
													.getContentNoByFiled(sqlI8nContentMap);
											if (contentList.get(0) != null
													&& ((Map) (contentList
															.get(0)))
															.get("CPNY_ID") != null) {
												aliasCellmapValue = (contentList == null ? ""
														: ((Map) (contentList
																.get(0))).get(
																"CPNY_ID")
																.toString());
											} else if (contentList.get(0) != null
													&& ((Map) (contentList
															.get(0)))
															.get("DEPTNO") != null) {
												aliasCellmapValue = (contentList == null ? ""
														: ((Map) (contentList
																.get(0))).get(
																"DEPTNO")
																.toString());
											} else {
												aliasCellmapValue = "";
											}
										} else {
											aliasCellmapValue = this
													.getCodeNoByName(sqlI8nContentMap) == -1 ? ""
													: this
															.getCodeNoByName(sqlI8nContentMap);
										}
										if (aliasCellmapValue.equals("")) {
											System.out
													.println(aliasValueMap.get(
															keyValue)
															.toString()
															+ "-----------------------------------");
										}
									}
								}
								switch (fieldType) {
								case 0:
									if (initKey == 1) {
										aliasKeySql = keyValue.toString();
										aliasValueSql = "'" + aliasCellmapValue
												+ "'";
									} else {
										aliasKeySql += ","
												+ keyValue.toString();
										aliasValueSql += ",'"
												+ aliasCellmapValue + "'";
									}
									break;
								case 1:
									if (initKey == 1) {
										aliasKeySql = keyValue.toString();
										if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("/")) {
											aliasValueSql = "to_date('"
													+ aliasCellmapValue
													+ "','YYYY/MM/DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										} else if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("-")) {
											aliasValueSql = "to_date('"
													+ aliasCellmapValue
													+ "','YYYY-MM-DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										}
									} else {
										aliasKeySql += ","
												+ keyValue.toString();
										if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("/")) {
											aliasValueSql += ",to_date('"
													+ aliasCellmapValue
													+ "','YYYY/MM/DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										} else if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("-")) {
											aliasValueSql += ",to_date('"
													+ aliasCellmapValue
													+ "','YYYY-MM-DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										}
									}
									break;
								case 11:
									if (initKey == 1) {
										aliasKeySql = keyValue.toString();
										if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("/")) {
											aliasValueSql = "to_date('"
													+ aliasCellmapValue
													+ "','YYYY/MM/DD HH24:MI:SS')";// "'"+aliasCellmapValue+"'";
										} else if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("-")) {
											aliasValueSql = "to_date('"
													+ aliasCellmapValue
													+ "','YYYY-MM-DD HH24:MI:SS')";// "'"+aliasCellmapValue+"'";
										}
									} else {
										aliasKeySql += ","
												+ keyValue.toString();
										if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("/")) {
											aliasValueSql += ",to_date('"
													+ aliasCellmapValue
													+ "','YYYY/MM/DD HH24:MI:SS')";// "'"+aliasCellmapValue+"'";
										} else if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("-")) {
											aliasValueSql += ",to_date('"
													+ aliasCellmapValue
													+ "','YYYY-MM-DD HH24:MI:SS')";
										}
									}
									break;
								case 12:
									if (initKey == 1) {
										aliasKeySql = keyValue.toString();
										if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("/")) {
											aliasValueSql = "to_date('"
													+ aliasCellmapValue
													+ "','YYYY/MM/DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										} else if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("-")) {
											aliasValueSql = "to_date('"
													+ aliasCellmapValue
													+ "','YYYY-MM-DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										}
									} else {
										aliasKeySql += ","
												+ keyValue.toString();
										if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("/")) {
											aliasValueSql += ",to_date('"
													+ aliasCellmapValue
													+ "','YYYY/MM/DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										} else if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("-")) {
											aliasValueSql += ",to_date('"
													+ aliasCellmapValue
													+ "','YYYY-MM-DD HH24:MI')";
										}
									}
									break;
								default:
									if (initKey == 1) {
										aliasKeySql = keyValue.toString();
										aliasValueSql = (aliasCellmapValue == null ? "null"
												: aliasCellmapValue.toString());
									} else {
										aliasKeySql += ","
												+ keyValue.toString();
										aliasValueSql += ","
												+ (aliasCellmapValue == null ? "null"
														: aliasCellmapValue
																.toString())
												+ "";
									}
									break;
								}
							}
						}
					}
				}
				smap.put("tableName", tableName);
				smap.put("aliasNameKeyContent", aliasKeySql);
				smap.put("aliasValueContent", aliasValueSql);
				addList.add(smap);
			}
			dataMap.put("addList", addList);
			this.insertExcelDataForTransferOrder(dataMap);
			token = 1;

		} catch (Exception e) { // 未找到文件异常
			e.printStackTrace();
			token = 0;
		}
		return token;
	}

	/**
	 * 将表名，各个列的值，以及各个列的类型整合到同一个Map中，调用导入方法时，不用动 Description:(异动)
	 * 
	 * @param tableName
	 *            要插入的表的表名
	 * @param aliasValueMap
	 *            各个列的值，对应excel的列用CELL+J(J为第几列)
	 * @param aliasTypeMap
	 *            各个列的类型
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public LinkedHashMap putIntoAliasValueAndTypeAndNameMap(String tableName,
			LinkedHashMap aliasValueMap, List aliasNameList, String titleSql,
			LinkedHashMap aliasTypeMap, LinkedHashMap aliasValueI18nMap) {
		LinkedHashMap aliasValueAndTypeMap = new LinkedHashMap();
		aliasValueAndTypeMap.put("tableName", tableName);
		aliasValueAndTypeMap.put("aliasValueMap", aliasValueMap);
		aliasValueAndTypeMap.put("aliasNameList", aliasNameList);
		aliasValueAndTypeMap.put("titleSql", titleSql);
		aliasValueAndTypeMap.put("aliasTypeMap", aliasTypeMap);
		aliasValueAndTypeMap.put("aliasValueI18nMap", aliasValueI18nMap);
		return aliasValueAndTypeMap;
	}

	/**
	 * 查询显示不修改且可查看显示的字段
	 * 
	 * @Copyright: AIT (c)
	 * @Company: AIT
	 * @Description: TODO
	 * @author zhengxiaochen zhengxiaochen@ait.net.cn
	 * @date 2013-9-19 上午02:54:35
	 * @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List getTransferOrderTypeByParams(HttpServletRequest request)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		LinkedHashMap paramMap = new LinkedHashMap();
		paramMap.put("CPNY_ID", admin.getCpnyId());
		paramMap.put("TRANS_CODE", request.getParameter("transferOrder_type"));
		return this.excelUtilDao.getTransferOrderTypeByParams(paramMap);
	}

	public static void main(String[] args) throws Exception {
		String dt = "2013/12/01";

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long dt1 = Date.parse(dt);
		new Date(dt);
		System.out.println(sdf.format(dt1));
		System.out.println(sdf.format(new Date(dt)));
	}

	@SuppressWarnings( { "unchecked" })
	public int impArShiftDataBase1(String adminID, LinkedHashMap cellmap,
			HttpServletRequest request) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		int token = 0;
		int p = path.indexOf("\\");
		String server = "win";
		if (p < 0) { // WebServer的os为unix/linux
			server = "u/l";
		}
		File file = null;
		if ("win".equals(server)) {
			file = new File(path + "\\" + filename + ".xls");
		} else {
			file = new File(path + "/" + filename + ".xls");
		}
		Map dataMap = new HashMap();
		String tableName = cellmap.get("tableName") == null ? "" : cellmap.get(
				"tableName").toString();// 获取表名
		try {
			Workbook workBook = Workbook.getWorkbook(file);
			// 获得第一个工作表对象
			Sheet sheet = workBook.getSheet(0);
			// 验证是否存在导入数据
			if (sheet.getRows() < 2) {
				logger.debug("Import file not exist data, return.");
				return 0;
			}
			List summaryList = new ArrayList();
			List addList = new ArrayList();
			int init = sheet.getRow(1).length;
			Cell[] rowIndex0 = sheet.getRow(0);
			int columnLength = rowIndex0.length;
			for (int n = 1; n < sheet.getRows(); n++) {
				Cell[] rowIndex1 = sheet.getRow(n);
				// 循环检查EXCEL中数据
				for (int j = 3; j < columnLength; j++) {
					String cellValue = "";
					// 判断是否有标题行有空格
					if (rowIndex0[j].getContents().toString().trim() == "")
						return 21;
					String cellShiftValue = rowIndex1[j].getContents();
					// 判断班次是否有输入空值的情况
					// if (cellShiftValue == null||cellShiftValue=="")
					// break;
					if (rowIndex0[j].getContents() != null) {// date类型
						try {
							cellValue = this.FormateData(rowIndex0[j],
									(Object) 1);
							if (cellValue.contains("/")) {
								String[] contentStr = cellValue.split("/");
								if (contentStr.length == 3
										&& contentStr[0].length() == 2
										&& contentStr[2].length() == 4) {
									cellValue = contentStr[2] + "/"
											+ contentStr[1] + "/"
											+ contentStr[0];
								}
							} else if (cellValue.contains("-")) {
								String[] contentStr = cellValue.split("-");
								if (contentStr.length == 3
										&& contentStr[0].length() == 2
										&& contentStr[2].length() == 4) {
									cellValue = contentStr[2] + "-"
											+ contentStr[1] + "-"
											+ contentStr[0];
								}
							}
						} catch (Exception e) {
							// cellValue=
							// row[j].getContents()==null?"":row[j].getContents();
							cellValue = "";
							if (rowIndex0[j].getContents() != null) {
								cellValue = rowIndex0[j].getContents();
								if (cellValue.contains("/")) {
									String[] contentStr = cellValue.split("/");
									if (contentStr.length == 3
											&& contentStr[0].length() == 2
											&& contentStr[2].length() == 4) {
										cellValue = contentStr[2] + "/"
												+ contentStr[1] + "/"
												+ contentStr[0];
									}
								} else if (cellValue.contains("-")) {
									String[] contentStr = cellValue.split("-");
									if (contentStr.length == 3
											&& contentStr[0].length() == 2
											&& contentStr[2].length() == 4) {
										cellValue = contentStr[2] + "-"
												+ contentStr[1] + "-"
												+ contentStr[0];
									}
								}
							}
						}
					} else {
						cellValue = rowIndex0[j].getContents() == null ? ""
								: rowIndex0[j].getContents();
					}
					// if(rowLength < 3){
					// return 3;//数据列数有问题，请核对
					// }
					// if(("".equals(rowID[0].getContents().trim()) ||
					// rowID[0].getContents() == null)
					// || ("".equals(rowID[3].getContents().trim()) ||
					// rowID[3].getContents() == null)
					// || ("".equals(rowID[4].getContents().trim()) ||
					// rowID[4].getContents() == null)){
					// return 4;//所导入数据中存在为空的情况，请检查核对
					// }

					// 检查数据是否已存在 存在则删除
					LinkedHashMap tempMap = new LinkedHashMap();
					tempMap.put("EMPID", rowIndex1[0].getContents().trim());
					tempMap.put("CPNY_ID", admin.getCpnyId());
					tempMap.put("AR_DATE_STR", cellValue);
					tempMap.put("#CELL" + j + "#", cellShiftValue);
					tempMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());
					tempMap.put("AR_SCHEDULE_TABLE", tableName);

					// 查看人员EMPID是否正确
					if (this.checkPersonalInfo(tempMap) == 0) {
						int rTint = 6;
						try {
							rTint = Integer.parseInt(tempMap.get("EMPID")
									.toString());
						} catch (Exception e) {
							rTint = 6;
						}
						// =6所导入数据中工号存在问题，不能进行数据导入;>10
						// EMPID:工号存在问题(该员工不存在，或者当前考勤员对该员工所在部门无考勤权限)
						return rTint;// 所导入数据中工号存在问题，不能进行数据导入
					} else {
						j = columnLength;
					}
				}
				// 封装删除排班表数据
				for (int m = 3; m < columnLength; m++) {
					// String cellShiftValues = rowIndex1[m].getContents();
					// //判断班次是否有输入空值的情况
					// if (cellShiftValues == null||cellShiftValues=="")
					// break;
					String cellShiftValue = rowIndex1[m].getContents();
					// 格式化日期
					String cellValue = "";
					if (rowIndex0[m].getContents() != null) {// date类型
						try {
							cellValue = this.FormateData(rowIndex0[m],
									(Object) 1);
							if (cellValue.contains("/")) {
								String[] contentStr = cellValue.split("/");
								if (contentStr.length == 3
										&& contentStr[0].length() == 2
										&& contentStr[2].length() == 4) {
									cellValue = contentStr[2] + "/"
											+ contentStr[1] + "/"
											+ contentStr[0];
								}
							} else if (cellValue.contains("-")) {
								String[] contentStr = cellValue.split("-");
								if (contentStr.length == 3
										&& contentStr[0].length() == 2
										&& contentStr[2].length() == 4) {
									cellValue = contentStr[2] + "-"
											+ contentStr[1] + "-"
											+ contentStr[0];
								}
							}
						} catch (Exception e) {
							// cellValue=
							// row[j].getContents()==null?"":row[j].getContents();
							cellValue = "";
							if (rowIndex0[m].getContents() != null) {
								cellValue = rowIndex0[m].getContents();
								if (cellValue.contains("/")) {
									String[] contentStr = cellValue.split("/");
									if (contentStr.length == 3
											&& contentStr[0].length() == 2
											&& contentStr[2].length() == 4) {
										cellValue = contentStr[2] + "/"
												+ contentStr[1] + "/"
												+ contentStr[0];
									}
								} else if (cellValue.contains("-")) {
									String[] contentStr = cellValue.split("-");
									if (contentStr.length == 3
											&& contentStr[0].length() == 2
											&& contentStr[2].length() == 4) {
										cellValue = contentStr[2] + "-"
												+ contentStr[1] + "-"
												+ contentStr[0];
									}
								}
							}
						}
					} else {
						cellValue = rowIndex0[m].getContents() == null ? ""
								: rowIndex0[m].getContents();
					}
					// 检查数据是否已存在 存在则删除
					LinkedHashMap tempMap = new LinkedHashMap();
					tempMap.put("EMPID", rowIndex1[0].getContents().trim());
					tempMap.put("CPNY_ID", admin.getCpnyId());
					tempMap.put("AR_DATE_STR", cellValue);
					tempMap.put("#CELL" + m + "#", cellShiftValue);
					tempMap.put("AR_SUPERVISIOR_INFO", admin.getPersonId());
					tempMap.put("AR_SCHEDULE_TABLE", tableName);

					summaryList.add(tempMap);
				}
				// 删除排班表中数据
				if (this.deleteScheduleInfo(summaryList) != 1) {
					return 7;// 删除排班表数据出错,请联系管理员
				}
				// 验证是否存在导入数据
				if (sheet.getRows() < 2) {
					logger.debug("Import file not exist data, return.");
					return token;
				}
				LinkedHashMap aliasValueMap = cellmap.get("aliasValueMap") == null ? null
						: (LinkedHashMap) cellmap.get("aliasValueMap");
				LinkedHashMap aliasTypeMap = cellmap.get("aliasTypeMap") == null ? null
						: (LinkedHashMap) cellmap.get("aliasTypeMap");
				LinkedHashMap aliasValueI18nMap = cellmap
						.get("aliasValueI18nMap") == null ? null
						: (LinkedHashMap) cellmap.get("aliasValueI18nMap");

				LinkedHashMap aliasMapCells = new LinkedHashMap();
				if (aliasValueMap != null && aliasTypeMap != null) {// 存放CELL0：NUMBER类型的键值对
					Set keySet = aliasValueMap.keySet();
					if (keySet != null) {
						Iterator it = keySet.iterator();
						while (it.hasNext()) {
							Object keyvalue = it.next();
							if (keyvalue != null) {
								Object aliasValue = aliasValueMap.get(keyvalue);
								if (aliasValue != null) {
									aliasMapCells.put(aliasValue.toString(),
											aliasTypeMap.get(keyvalue));
								}
							}
						}
					}
				}
				// 循环工作表的每行数据
				for (int i = 3; i < columnLength; i++) {
					String cellShiftValues = rowIndex1[i].getContents();
					// 判断班次是否有输入空值的情况
					// if (cellShiftValues == null||cellShiftValues=="")
					// break;
					// for (int i = 1; i < sheet.getRows(); i++) {
					// 验证当前行是否符合规范
					// if (!validateCell(sheet.getRow(i),
					// sheet.getRow(0).length))
					// continue;
					// if (!validateCell(sheet.getRow(i),
					// sheet.getRow(0).length)){//如果所在行全为空，不导入
					// break;
					// }
					// Cell[] row = sheet.getRow(i);
					String cellShiftValue = rowIndex1[i].getContents();
					if (init == 0) {
						// init=row.length;
						init = sheet.getRow(0).length;
					}

					LinkedHashMap smap = new LinkedHashMap();
					LinkedHashMap aliasCellmap = new LinkedHashMap();
					String cellValue = "";
					int j = 0;
					for (j = 0; j < columnLength; j++) {
						try {
							// String
							// cellShiftValueWS=rowIndex1[j].getContents();
							aliasCellmap.put("#CELL" + j + "#", cellValue);

							Object cellType = null;
							cellType = aliasMapCells.get("#CELL" + j + "#");
							Cell[] newRowIndex = null;
							if (j > 2) {
								newRowIndex = rowIndex0;
							} else {
								newRowIndex = rowIndex1;
							}

							if (cellType != null
									&& !newRowIndex[j].getContents().equals("")) {// date类型
								try {
									cellValue = this.FormateData(
											newRowIndex[j], cellType);
									if (cellValue.contains("/")) {
										String[] contentStr = cellValue
												.split("/");
										if (contentStr.length == 3
												&& contentStr[0].length() == 2
												&& contentStr[2].length() == 4) {
											cellValue = contentStr[2] + "/"
													+ contentStr[1] + "/"
													+ contentStr[0];
										}
									} else if (cellValue.contains("-")) {
										String[] contentStr = cellValue
												.split("-");
										if (contentStr.length == 3
												&& contentStr[0].length() == 2
												&& contentStr[2].length() == 4) {
											cellValue = contentStr[2] + "-"
													+ contentStr[1] + "-"
													+ contentStr[0];
										}
									}
								} catch (Exception e) {
									// cellValue=
									// row[j].getContents()==null?"":row[j].getContents();
									cellValue = "";
									if (newRowIndex[j].getContents() != null) {
										cellValue = newRowIndex[j]
												.getContents();
										if (cellValue.contains("/")) {
											String[] contentStr = cellValue
													.split("/");
											if (contentStr.length == 3
													&& contentStr[0].length() == 2
													&& contentStr[2].length() == 4) {
												cellValue = contentStr[2] + "/"
														+ contentStr[1] + "/"
														+ contentStr[0];
											}
										} else if (cellValue.contains("-")) {
											String[] contentStr = cellValue
													.split("-");
											if (contentStr.length == 3
													&& contentStr[0].length() == 2
													&& contentStr[2].length() == 4) {
												cellValue = contentStr[2] + "-"
														+ contentStr[1] + "-"
														+ contentStr[0];
											}
										}
									}
								}
							} else {
								cellValue = newRowIndex[j].getContents() == null ? ""
										: newRowIndex[j].getContents();
							}
							// cellValue=
							// row[j].getContents()==null?"":row[j].getContents();
							if (j > 2) {
								aliasCellmap.put("#CELLA" + j + "#",
										cellValue == null ? "" : cellValue
												.trim());
								aliasCellmap.put("SHIFT_NO", "");
								aliasCellmap.put("AR_DATE_STR", "1");
							} else {
								aliasCellmap.put("#CELL" + j + "#",
										cellValue == null ? "" : cellValue
												.trim());
							}
						} catch (Exception e) {
							e.printStackTrace();
							return token = 2;
						}
					}

					String aliasKeySql = "";
					String aliasValueSql = "";
					int initKey = 0;

					if (aliasValueMap != null && aliasTypeMap != null) {
						Set keySet = aliasValueMap.keySet();
						if (keySet != null) {
							Iterator it = keySet.iterator();
							while (it.hasNext()) {
								initKey++;
								Object keyValue = it.next();
								if (keyValue != null && !keyValue.equals("")) {
									int fieldType = Integer
											.parseInt(aliasTypeMap
													.get(keyValue).toString());
									Object aliasCellmapValue = aliasCellmap
											.get(aliasValueMap.get(keyValue));
									if (aliasCellmapValue == null) {// 不属于excel的列
										if ("AR_DATE_STR".equals(keyValue)) {
											// for (int l = 3; l < columnLength;
											// l++) {
											cellValue = rowIndex0[i]
													.getContents();
											if (cellValue.contains("/")) {
												String[] contentStr = cellValue
														.split("/");
												if (contentStr.length == 3
														&& contentStr[0]
																.length() == 2
														&& contentStr[2]
																.length() == 4) {
													cellValue = contentStr[2]
															+ "/"
															+ contentStr[1]
															+ "/"
															+ contentStr[0];
												}
											} else if (cellValue.contains("-")) {
												String[] contentStr = cellValue
														.split("-");
												if (contentStr.length == 3
														&& contentStr[0]
																.length() == 2
														&& contentStr[2]
																.length() == 4) {
													cellValue = contentStr[2]
															+ "-"
															+ contentStr[1]
															+ "-"
															+ contentStr[0];
												}
												// }
											}
											aliasKeySql += ","
													+ keyValue.toString();
											String dateStr = " to_char(to_date('"
													+ cellValue
													+ "', 'YYYY/MM/DD'),'YYYY-MM-DD')";
											aliasValueSql += "," + dateStr
													+ " ";
										} else {
											aliasCellmapValue = aliasValueMap
													.get(keyValue);
											if (aliasCellmapValue != null
													&& aliasCellmapValue
															.toString()
															.contains("#CELL")) {
												String sqlContent = aliasCellmapValue
														.toString();
												for (int l = 3; l < columnLength; l++) {
													sqlContent = sqlContent
															.replaceAll(
																	"#CELL3#",
																	cellShiftValue);
													sqlContent = sqlContent
															.replaceAll(
																	"#CELLA"
																			+ l
																			+ "#",
																	cellValue);
												}
												LinkedHashMap sqlI8nContentMap = new LinkedHashMap();
												switch (fieldType) {
												case 2:
													sqlI8nContentMap.put(
															"sqlI18nContent",
															sqlContent);
													aliasCellmapValue = this
															.getCodeNoByName(sqlI8nContentMap) == -1 ? ""
															: this
																	.getCodeNoByName(sqlI8nContentMap);
													break;
												default:
													sqlI8nContentMap.put(
															"sqlContent",
															sqlContent);
													if (this
															.getContentNoByFiled(sqlI8nContentMap) != null) {
														Object cellObj = this
																.getContentNoByFiled(
																		sqlI8nContentMap)
																.size() == 0 ? ""
																: this
																		.getContentNoByFiled(
																				sqlI8nContentMap)
																		.get(0);
														if (cellObj != null
																&& !cellObj
																		.equals("")
																&& ((Map) cellObj)
																		.get(keyValue
																				.toString()) != null) {
															aliasCellmapValue = (cellObj == null ? ""
																	: ((Map) cellObj)
																			.get(
																					keyValue
																							.toString()
																							.toUpperCase())
																			.toString());
														} else {
															aliasCellmapValue = "";
														}
													}
													break;
												}
											}
										}
									} else {
										if (aliasValueI18nMap != null
												&& aliasValueI18nMap
														.get(keyValue) != null) {// 有需要从国际化表读取No的列
											String sqlContent = aliasValueI18nMap
													.get(keyValue) == null ? ""
													: aliasValueI18nMap.get(
															keyValue)
															.toString();
											sqlContent = sqlContent
													.replaceAll(
															aliasValueMap
																	.get(keyValue) == null ? ""
																	: aliasValueMap
																			.get(
																					keyValue)
																			.toString(),
															"'"
																	+ aliasCellmapValue
																			.toString()
																	+ "'");

											LinkedHashMap sqlI8nContentMap = new LinkedHashMap();
											sqlI8nContentMap.put(
													"sqlI18nContent",
													sqlContent);
											if (aliasValueI18nMap.get(keyValue
													+ "_FLAG") != null
													&& aliasValueI18nMap.get(
															keyValue + "_FLAG")
															.equals("Y")) {
												sqlI8nContentMap.put(
														"sqlContent",
														sqlContent);
												List contentList = this
														.getContentNoByFiled(sqlI8nContentMap);
												if (contentList.get(0) != null
														&& ((Map) (contentList
																.get(0)))
																.get("CPNY_ID") != null) {
													aliasCellmapValue = (contentList == null ? ""
															: ((Map) (contentList
																	.get(0)))
																	.get(
																			"CPNY_ID")
																	.toString());
												} else {
													aliasCellmapValue = "";
												}
											} else {
												aliasCellmapValue = this
														.getCodeNoByName(sqlI8nContentMap) == -1 ? ""
														: this
																.getCodeNoByName(sqlI8nContentMap);
											}
											if (aliasCellmapValue.equals("")) {
											}

										}
									}
									if (!"AR_DATE_STR".equals(keyValue)) {
										switch (fieldType) {

										case 0:
											// smap.put(keyValue.toString(),
											// "'"+aliasValueMap.get(keyValue)+"'");
											if (initKey == 1) {
												aliasKeySql = keyValue
														.toString();
												aliasValueSql = "'"
														+ aliasCellmapValue
														+ "'";
											} else {
												aliasKeySql += ","
														+ keyValue.toString();
												aliasValueSql += ",'"
														+ aliasCellmapValue
														+ "'";
											}
											break;
										case 1:
											// smap.put(keyValue.toString(),
											// "'"+aliasValueMap.get(keyValue)+"'");
											if (initKey == 1) {
												aliasKeySql = keyValue
														.toString();
												if (aliasCellmapValue != null
														&& aliasCellmapValue
																.toString()
																.contains("/")) {
													aliasValueSql = "to_date('"
															+ aliasCellmapValue
															+ "','YYYY/MM/DD')";// "'"+aliasCellmapValue+"'";
												} else if (aliasCellmapValue != null
														&& aliasCellmapValue
																.toString()
																.contains("-")) {
													aliasValueSql = "to_date('"
															+ aliasCellmapValue
															+ "','YYYY-MM-DD')";// "'"+aliasCellmapValue+"'";
												}
											} else {
												aliasKeySql += ","
														+ keyValue.toString();
												if (aliasCellmapValue != null
														&& aliasCellmapValue
																.toString()
																.contains("/")) {
													aliasValueSql += ",to_date('"
															+ aliasCellmapValue
															+ "','YYYY/MM/DD')";// "'"+aliasCellmapValue+"'";
												} else if (aliasCellmapValue != null
														&& aliasCellmapValue
																.toString()
																.contains("-")) {
													aliasValueSql += ",to_date('"
															+ aliasCellmapValue
															+ "','YYYY-MM-DD')";// "'"+aliasCellmapValue+"'";
												}
											}
											break;
										case 11:
											if (initKey == 1) {
												aliasKeySql = keyValue
														.toString();
												if (aliasCellmapValue != null
														&& aliasCellmapValue
																.toString()
																.contains("/")) {
													aliasValueSql = "to_date('"
															+ aliasCellmapValue
															+ "','YYYY/MM/DD HH24:MI:SS')";// "'"+aliasCellmapValue+"'";
												} else if (aliasCellmapValue != null
														&& aliasCellmapValue
																.toString()
																.contains("-")) {
													aliasValueSql = "to_date('"
															+ aliasCellmapValue
															+ "','YYYY-MM-DD HH24:MI:SS')";// "'"+aliasCellmapValue+"'";
												}
											} else {
												aliasKeySql += ","
														+ keyValue.toString();
												if (aliasCellmapValue != null
														&& aliasCellmapValue
																.toString()
																.contains("/")) {
													aliasValueSql += ",to_date('"
															+ aliasCellmapValue
															+ "','YYYY/MM/DD HH24:MI:SS')";// "'"+aliasCellmapValue+"'";
												} else if (aliasCellmapValue != null
														&& aliasCellmapValue
																.toString()
																.contains("-")) {
													aliasValueSql += ",to_date('"
															+ aliasCellmapValue
															+ "','YYYY-MM-DD HH24:MI:SS')";
												}
											}
											break;
										case 12:
											if (initKey == 1) {
												aliasKeySql = keyValue
														.toString();
												if (aliasCellmapValue != null
														&& aliasCellmapValue
																.toString()
																.contains("/")) {
													aliasValueSql = "to_date('"
															+ aliasCellmapValue
															+ "','YYYY/MM/DD HH24:MI')";// "'"+aliasCellmapValue+"'";
												} else if (aliasCellmapValue != null
														&& aliasCellmapValue
																.toString()
																.contains("-")) {
													aliasValueSql = "to_date('"
															+ aliasCellmapValue
															+ "','YYYY-MM-DD HH24:MI')";// "'"+aliasCellmapValue+"'";
												}
											} else {
												aliasKeySql += ","
														+ keyValue.toString();
												if (aliasCellmapValue != null
														&& aliasCellmapValue
																.toString()
																.contains("/")) {
													aliasValueSql += ",to_date('"
															+ aliasCellmapValue
															+ "','YYYY/MM/DD HH24:MI')";// "'"+aliasCellmapValue+"'";
												} else if (aliasCellmapValue != null
														&& aliasCellmapValue
																.toString()
																.contains("-")) {
													aliasValueSql += ",to_date('"
															+ aliasCellmapValue
															+ "','YYYY-MM-DD HH24:MI')";
												}
											}
											break;
										case 13:
											// smap.put(keyValue.toString(),
											// "'"+aliasValueMap.get(keyValue)+"'");
											if (initKey == 1) {
												aliasKeySql = keyValue
														.toString();
												aliasValueSql = "'"
														+ aliasCellmapValue
														+ "'";
											} else {
												aliasKeySql += ","
														+ keyValue.toString();
												aliasValueSql += ",'"
														+ aliasCellmapValue
														+ "'";
											}
											break;
										default:
											// smap.put(keyValue.toString(),
											// aliasValueMap.get(keyValue));
											if (initKey == 1) {
												aliasKeySql = keyValue
														.toString();
												aliasValueSql = (aliasCellmapValue == null ? "null"
														: aliasCellmapValue
																.toString());
											} else {
												aliasKeySql += ","
														+ keyValue.toString();
												aliasValueSql += ","
														+ (aliasCellmapValue == null ? "null"
																: aliasCellmapValue
																		.toString())
														+ "";
											}
											break;
										}
									}
								}
							}
						}
					}
					smap.put("tableName", tableName);
					smap.put("aliasNameKeyContent", aliasKeySql);
					smap.put("aliasValueContent", aliasValueSql);
					if (cellShiftValues != null && cellShiftValues != "") {
						addList.add(smap);
					}
				}
			}
			dataMap.put("addList", addList);
			// int result=this.insertExcelData(dataMap);
			this.insertExcelData(dataMap);
			// if(result==1){
			token = 1;
		} catch (Exception e) { // 未找到文件异常
			e.printStackTrace();
			token = 0;
		}
		return token;
	}

	@SuppressWarnings( { "unchecked" })
	public LinkedHashMap impDataBase2(HttpServletRequest request,
			String adminID, LinkedHashMap cellmap, List list, List list1,
			int num) {
		// 记录行列错误
		LinkedHashMap validateCellMap = new LinkedHashMap();
		List validateCellList = new ArrayList();

		int token = 0;
		int k = path.indexOf("\\");
		String server = "win";
		if (k < 0) { // WebServer的os为unix/linux
			server = "u/l";
		}
		File file = null;
		if ("win".equals(server)) {
			file = new File(path + "\\" + filename + ".xls");
		} else {
			file = new File(path + "/" + filename + ".xls");
		}
		Map dataMap = new HashMap();
		try {
			Workbook workBook = Workbook.getWorkbook(file);
			// 获得第一个工作表对象
			Sheet sheet = workBook.getSheet(0);
			// 验证是否存在导入数据
			if (sheet.getRows() < 2) {
				logger.debug("Import file not exist data, return.");
				validateCellMap.put("token", "token");
				return validateCellMap;
			}
			String tableName = cellmap.get("tableName") == null ? "" : cellmap
					.get("tableName").toString();// 获取表名
			LinkedHashMap aliasValueMap = cellmap.get("aliasValueMap") == null ? null
					: (LinkedHashMap) cellmap.get("aliasValueMap");
			LinkedHashMap aliasTypeMap = cellmap.get("aliasTypeMap") == null ? null
					: (LinkedHashMap) cellmap.get("aliasTypeMap");
			LinkedHashMap aliasValueI18nMap = cellmap.get("aliasValueI18nMap") == null ? null
					: (LinkedHashMap) cellmap.get("aliasValueI18nMap");
			String field2_value = aliasValueMap.get("FIELD2_VALUE") == null ? ""
					: aliasValueMap.get("FIELD2_VALUE").toString();
			String aliasNullStr = cellmap.get("aliasNullStr") == null ? ""
					: cellmap.get("aliasNullStr").toString();

			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			// 判断导入的是否为已有记录----LM
			boolean isUpdate = false;
			for (int m = 1; m < sheet.getRows(); m++) {
				Cell[] rowTemp = sheet.getRow(m);
				if (rowTemp[0] == null
						|| rowTemp[0].getContents().trim().equals("")) {
					continue;
				}
				/*
				 * if (!validateCell(sheet.getRow(m), sheet.getRow(m).length))
				 * {// 如果所在行全为空，不导入 break; }
				 */
			}
			// 派遣地发令导入数据

			LinkedHashMap aliasMapCells = new LinkedHashMap();
			if (aliasValueMap != null && aliasTypeMap != null) {// 存放CELL0：NUMBER类型的键值对
				Set keySet = aliasValueMap.keySet();
				if (keySet != null) {
					Iterator it = keySet.iterator();
					while (it.hasNext()) {
						Object keyvalue = it.next();
						if (keyvalue != null) {
							Object aliasValue = aliasValueMap.get(keyvalue);
							if (aliasValue != null) {
								aliasMapCells.put(aliasValue.toString(),
										aliasTypeMap.get(keyvalue));
							}
						}
					}
				}
			}
			for (int i = 1; i < sheet.getRows(); i++) {
				String result = validateCell1(sheet.getRow(i),
						sheet.getRow(0).length, aliasNullStr, list, list1,
						admin);
				// 判断当前i行是否有错误，如果有记录
				if (result != null && result != "") {
					Cell[] row = sheet.getRow(i);
					String empid = row[num].getContents();
					String resutlcul = "社号:" + empid + ";         第" + i + "行，"
							+ result + "<br>";
					validateCellList.add(resutlcul);
				}
			}
			validateCellMap.put("validateCellList", validateCellList);
			if (validateCellList.size() > 0) {
				return validateCellMap;
			}

			List addList = new ArrayList();
			// 循环工作表的每行数据
			int init = 0;
			for (int i = 1; i < sheet.getRows(); i++) {
				// 如果是可以为空的列就不进行空列的判断
				// 验证当前行是否符合规范
				/*
				 * if (!validateCell1(sheet.getRow(i), sheet.getRow(0).length,
				 * aliasNullStr,list)) {// 如果所在行全为空，不导入 break; }
				 */
				Cell[] row = sheet.getRow(i);
				if (row[0] == null || row[0].getContents().trim().equals("")) {
					continue;
				}

				if (init == 0) {
					init = sheet.getRow(0).length;
				}
				int aaa = sheet.getRow(1).length;
				LinkedHashMap smap = new LinkedHashMap();
				LinkedHashMap aliasCellmap = new LinkedHashMap();
				for (int j = 0; j < init; j++) {
					try {
						String cellValue = "";
						Object cellType = aliasMapCells.get("#CELL" + j + "#");
						String content = "";
						// if(isUpdate){
						// continue;
						// }
						if (aliasNullStr.indexOf("," + j + ",") < 0) {
							content = StringUtil
									.checkNull(row[j].getContents());
							if (cellType != null && !content.equals("")) {// date类型
								try {
									cellValue = this.FormateData(row[j],
											cellType);
									if (cellValue.contains("/")) {
										String[] contentStr = cellValue
												.split("/");
										if (contentStr.length == 3
												&& contentStr[0].length() == 2
												&& contentStr[2].length() == 4) {
											cellValue = contentStr[2] + "/"
													+ contentStr[1] + "/"
													+ contentStr[0];
										}
									} else if (cellValue.contains("-")) {
										String[] contentStr = cellValue
												.split("-");
										if (contentStr.length == 3
												&& contentStr[0].length() == 2
												&& contentStr[2].length() == 4) {
											cellValue = contentStr[2] + "-"
													+ contentStr[1] + "-"
													+ contentStr[0];
										}
									}
								} catch (Exception e) {
									cellValue = "";
									if (row[j].getContents() != null) {
										cellValue = row[j].getContents();
										if (cellValue.contains("/")) {
											String[] contentStr = cellValue
													.split("/");
											if (contentStr.length == 3
													&& contentStr[0].length() == 2
													&& contentStr[2].length() == 4) {
												cellValue = contentStr[2] + "/"
														+ contentStr[1] + "/"
														+ contentStr[0];
											}
										} else if (cellValue.contains("-")) {
											String[] contentStr = cellValue
													.split("-");
											if (contentStr.length == 3
													&& contentStr[0].length() == 2
													&& contentStr[2].length() == 4) {
												cellValue = contentStr[2] + "-"
														+ contentStr[1] + "-"
														+ contentStr[0];
											}
										}
									}
								}
							} else {
								cellValue = row[j].getContents() == null ? ""
										: row[j].getContents();
							}
						} else {
							try {
								cellValue = row[j].getContents() == null ? ""
										: row[j].getContents();
							} catch (Exception e) {
								cellValue = "";
							}
						}
						aliasCellmap.put("#CELL" + j + "#",
								cellValue == null ? "" : cellValue.trim());
					} catch (Exception e) {
						e.printStackTrace();
						// 表有空值或有空单元行，请检查！
						// return token = 2;
						validateCellMap.put("token", "2");
						return validateCellMap;
					}
				}
				String aliasKeySql = "";
				String aliasValueSql = "";
				int initKey = 0;
				if (aliasValueMap != null && aliasTypeMap != null) {
					Set keySet = aliasValueMap.keySet();
					if (keySet != null) {
						Iterator it = keySet.iterator();
						while (it.hasNext()) {
							initKey++;
							Object keyValue = it.next();
							if (keyValue != null && !keyValue.equals("")) {
								int fieldType = Integer.parseInt(aliasTypeMap
										.get(keyValue).toString());
								Object aliasCellmapValue = aliasCellmap
										.get(aliasValueMap.get(keyValue));
								if (aliasCellmapValue == null) {// 不属于excel的列
									aliasCellmapValue = aliasValueMap
											.get(keyValue);
									if (aliasCellmapValue != null
											&& aliasCellmapValue.toString()
													.contains("#CELL")) {
										String sqlContent = aliasCellmapValue
												.toString();
										for (int l = 0; l < init; l++) {
											sqlContent = sqlContent.replaceAll(
													"#CELL" + l + "#",
													aliasCellmap.get(
															"#CELL" + l + "#")
															.toString());
											if (!sqlContent.contains("#CELL")) {
												break;
											}
										}
										LinkedHashMap sqlI8nContentMap = new LinkedHashMap();
										switch (fieldType) {
										case 2:
											sqlI8nContentMap.put(
													"sqlI18nContent",
													sqlContent);
											aliasCellmapValue = this
													.getCodeNoByName(sqlI8nContentMap) == -1 ? ""
													: this
															.getCodeNoByName(sqlI8nContentMap);
											break;
										default:
											sqlI8nContentMap.put("sqlContent",
													sqlContent);
											if (this
													.getContentNoByFiled(sqlI8nContentMap) != null) {
												Object cellObj = this
														.getContentNoByFiled(
																sqlI8nContentMap)
														.size() == 0 ? ""
														: this
																.getContentNoByFiled(
																		sqlI8nContentMap)
																.get(0);
												if (cellObj != null
														&& !cellObj.equals("")
														&& ((Map) cellObj)
																.get(keyValue
																		.toString()) != null) {
													aliasCellmapValue = (cellObj == null ? ""
															: ((Map) cellObj)
																	.get(
																			keyValue
																					.toString()
																					.toUpperCase())
																	.toString());
												} else {
													aliasCellmapValue = "";
												}
											}

											break;
										}
									}
								} else {
									if (aliasValueI18nMap != null
											&& aliasValueI18nMap.get(keyValue) != null) {// 有需要从国际化表读取No的列
										String sqlContent = aliasValueI18nMap
												.get(keyValue) == null ? ""
												: aliasValueI18nMap.get(
														keyValue).toString();
										sqlContent = sqlContent
												.replaceAll(
														aliasValueMap
																.get(keyValue) == null ? ""
																: aliasValueMap
																		.get(
																				keyValue)
																		.toString(),
														"'"
																+ aliasCellmapValue
																		.toString()
																+ "'");

										LinkedHashMap sqlI8nContentMap = new LinkedHashMap();
										sqlI8nContentMap.put("sqlI18nContent",
												sqlContent);
										if (aliasValueI18nMap.get(keyValue
												+ "_FLAG") != null
												&& aliasValueI18nMap.get(
														keyValue + "_FLAG")
														.equals("Y")) {
											sqlI8nContentMap.put("sqlContent",
													sqlContent);
											List contentList = this
													.getContentNoByFiled(sqlI8nContentMap);
											if (contentList.get(0) != null
													&& ((Map) (contentList
															.get(0)))
															.get("CPNY_ID") != null) {
												aliasCellmapValue = (contentList == null ? ""
														: ((Map) (contentList
																.get(0))).get(
																"CPNY_ID")
																.toString());
											} else if (contentList.get(0) != null
													&& ((Map) (contentList
															.get(0)))
															.get("DEPTNO") != null) {
												aliasCellmapValue = (contentList == null ? ""
														: ((Map) (contentList
																.get(0))).get(
																"DEPTNO")
																.toString());
											} else {
												aliasCellmapValue = "";
											}
										} else {
											aliasCellmapValue = this
													.getCodeNoByName(sqlI8nContentMap) == -1 ? ""
													: this
															.getCodeNoByName(sqlI8nContentMap);
										}
										if (aliasCellmapValue.equals("")) {
											System.out
													.println(aliasValueMap.get(
															keyValue)
															.toString()
															+ "-----------------------------------");
										}
									}
								}
								switch (fieldType) {
								case 0:
									// smap.put(keyValue.toString(),
									// "'"+aliasValueMap.get(keyValue)+"'");
									if (initKey == 1) {
										aliasKeySql = keyValue.toString();
										aliasValueSql = "'" + aliasCellmapValue
												+ "'";
									} else {
										aliasKeySql += ","
												+ keyValue.toString();
										aliasValueSql += ",'"
												+ aliasCellmapValue + "'";
									}
									break;
								case 1:
									// smap.put(keyValue.toString(),
									// "'"+aliasValueMap.get(keyValue)+"'");
									if (initKey == 1) {
										aliasKeySql = keyValue.toString();
										if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("/")) {
											aliasValueSql = "to_date('"
													+ aliasCellmapValue
													+ "','YYYY/MM/DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										} else if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("-")) {
											aliasValueSql = "to_date('"
													+ aliasCellmapValue
													+ "','YYYY-MM-DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										}
									} else {
										aliasKeySql += ","
												+ keyValue.toString();
										if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("/")) {
											aliasValueSql += ",to_date('"
													+ aliasCellmapValue
													+ "','YYYY/MM/DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										} else if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("-")) {
											aliasValueSql += ",to_date('"
													+ aliasCellmapValue
													+ "','YYYY-MM-DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										} else if (aliasCellmapValue == "") {
											aliasValueSql += ",'' ";
										}
									}
									break;
								case 11:
									if (initKey == 1) {
										aliasKeySql = keyValue.toString();
										if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("/")) {
											aliasValueSql = "to_date('"
													+ aliasCellmapValue
													+ "','YYYY/MM/DD HH24:MI:SS')";// "'"+aliasCellmapValue+"'";
										} else if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("-")) {
											aliasValueSql = "to_date('"
													+ aliasCellmapValue
													+ "','YYYY-MM-DD HH24:MI:SS')";// "'"+aliasCellmapValue+"'";
										}
									} else {
										aliasKeySql += ","
												+ keyValue.toString();
										if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("/")) {
											aliasValueSql += ",to_date('"
													+ aliasCellmapValue
													+ "','YYYY/MM/DD HH24:MI:SS')";// "'"+aliasCellmapValue+"'";
										} else if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("-")) {
											aliasValueSql += ",to_date('"
													+ aliasCellmapValue
													+ "','YYYY-MM-DD HH24:MI:SS')";
										}
									}
									break;
								case 12:
									if (initKey == 1) {
										aliasKeySql = keyValue.toString();
										if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("/")) {
											aliasValueSql = "to_date('"
													+ aliasCellmapValue
													+ "','YYYY/MM/DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										} else if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("-")) {
											aliasValueSql = "to_date('"
													+ aliasCellmapValue
													+ "','YYYY-MM-DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										}
									} else {
										aliasKeySql += ","
												+ keyValue.toString();
										if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("/")) {
											aliasValueSql += ",to_date('"
													+ aliasCellmapValue
													+ "','YYYY/MM/DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										} else if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("-")) {
											aliasValueSql += ",to_date('"
													+ aliasCellmapValue
													+ "','YYYY-MM-DD HH24:MI')";
										}
									}
									break;
								default:
									if (initKey == 1) {
										aliasKeySql = keyValue.toString();
										aliasValueSql = (aliasCellmapValue == null ? "null"
												: aliasCellmapValue.toString());
									} else {
										aliasKeySql += ","
												+ keyValue.toString();
										aliasValueSql += ","
												+ (aliasCellmapValue == null ? "null"
														: aliasCellmapValue
																.toString())
												+ "";
									}
									break;
								}
							}
						}
					}
				}
				smap.put("tableName", tableName);
				smap.put("aliasNameKeyContent", aliasKeySql);
				smap.put("aliasValueContent", aliasValueSql);
				addList.add(smap);
			}
			dataMap.put("addList", addList);
			this.insertExcelData(dataMap);

			validateCellMap.put("token", "token");
			return validateCellMap;
			// token = 1;

		} catch (Exception e) { // 未找到文件异常
			e.printStackTrace();
			token = 0;
			validateCellMap.put("token", "0");
		}
		return validateCellMap;
	}

	private String validateCell1(Cell[] row, int columns, String nullStr,
			List list, List list1, AdminBean admin) {
		int init = 0;
		String result = "";
		boolean isDate;

		for (int i = 0; i < columns; i++) {
			String name = list1.get(i).toString();
			String names[] = name.split(",");
			// 如果当前行有空的单元格返回false
			int ii = nullStr.indexOf("," + i + ",");
			if (nullStr.indexOf("," + i + ",") < 0) {
				Object cellsContent = StringUtil
						.checkNull(row[i].getContents());
				String content = "";
				if (cellsContent != null) {
					content = cellsContent.toString().replaceAll(" ", "");
				}
				if (row[i].getType() == CellType.EMPTY
						|| ((row[i].getType() != CellType.EMPTY) && content
								.equals(""))) {
					init++;
				}
				// 日期类型
				if (names[1].toString().equals("0")) {
					isDate = isValidDate2(cellsContent.toString());
					if (isDate == false) {
						result += names[0].toString()
								+ ":日期格式有错误。正确格式应为(yyyy/MM/dd).";
					}
				} else if (names[1].toString().equals("1")) {
					LinkedHashMap sql = new LinkedHashMap();
					String sqlI18nContent = "";
					if (names[2].equals("0")) {
						sqlI18nContent = "SELECT COUNT(*) FROM "
								+ names[3].toString() + "  WHERE CPNY_ID='"
								+ admin.getCpnyId() + "' and EMPID='"
								+ cellsContent + "'";
					} else if (names[2].equals("1")) {
						sqlI18nContent = "SELECT COUNT(*) FROM SY_CODE SC,SY_GLOBAL_NAME SY WHERE SC.PARENT_CODE_NO="
								+ names[3].toString()
								+ " AND SC.CODE_NO=SY.NO AND SY.LANGUAGE='"
								+ admin.getLanguage()
								+ "' AND SY.CONTENT='"
								+ cellsContent + "'";
					}
					sql.put("sqlI18nContent", sqlI18nContent);
					int count = this.getCodeNoByName(sql);
					if (count == 0) {
						result = result + names[0] + ":值 " + cellsContent
								+ "不存在.";
					}
				} else if (names[1].toString().equals("-1")) {
					// 不需要验证
				}
				// return false;
			}
		}
		if (init == columns) {
			return result;
		}
		return result;
	}

	/**
	 * 判断日期格式:yyyy-mm-dd
	 * 
	 * @param sDate
	 * @return
	 */
	public static boolean isValidDate(String sDate) {
		String datePattern1 = "\\d{4}-\\d{2}-\\d{2}";
		String datePattern2 = "^((\\d{2}(([02468][048])|([13579][26]))"
				+ "[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|"
				+ "(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?"
				+ "((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?("
				+ "(((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?"
				+ "((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
		if ((sDate != null)) {
			Pattern pattern = Pattern.compile(datePattern1);
			Matcher match = pattern.matcher(sDate);
			if (match.matches()) {
				pattern = Pattern.compile(datePattern2);
				match = pattern.matcher(sDate);
				return match.matches();
			} else {
				return false;
			}
		}
		return false;
	}

	/**
	 * 判断日期格式:yyyy-mm-dd
	 * 
	 * @param sDate
	 * @return
	 */
	public static boolean isValidDate1(String sDate) {
		String eL = "^((//d{2}(([02468][048])|([13579][26]))[//-/////s]?((((0?[13578])|(1[02]))[//-/////s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[//-/////s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[//-/////s]?((0?[1-9])|([1-2][0-9])))))|(//d{2}(([02468][1235679])|([13579][01345789]))[//-/////s]?((((0?[13578])|(1[02]))[//-/////s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[//-/////s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[//-/////s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(//s(((0?[0-9])|([1][0-9])|([2][0-3]))//:([0-5]?[0-9])((//s)|(//:([0-5]?[0-9])))))?$";
		Pattern p = Pattern.compile(eL);
		Matcher m = p.matcher(sDate);
		boolean b = m.matches();
		return b;

	}

	/**
	 * 判断日期格式:yyyy-mm-dd
	 * 
	 * @param sDate
	 * @return
	 */
	public static boolean isValidDate2(String sDate) {
		boolean bool = true;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

		try {
			sdf.parse(sDate);
			String a1 = sdf.format(sdf.parse(sDate));
		} catch (Exception e) {
			e.printStackTrace();
			bool = false;
		}
		return bool;
	}

	/**
	 * 将表名，各个列的值，以及各个列的类型整合到同一个Map中，调用导入方法时，不用动 Description:
	 * 
	 * @Description: CHRS2.0 SI PROJECT
	 * @author penghaixia
	 * @date 2014.6.10
	 * @version V1.0
	 * @param tableName
	 *            要插入的表的表名
	 * @param aliasValueMap
	 *            各个列的值，对应excel的列用CELL+J(J为第几列)
	 * @param aliasTypeMap
	 *            各个列的类型
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public LinkedHashMap putIntoAliasValueAndTypeMapForTemple(String tableName,
			LinkedHashMap aliasValueMap, LinkedHashMap aliasTypeMap,
			LinkedHashMap aliasValueI18nMap, LinkedHashMap aliasValueAppendMap) {
		LinkedHashMap aliasValueAndTypeMap = new LinkedHashMap();
		aliasValueAndTypeMap.put("tableName", tableName);
		aliasValueAndTypeMap.put("aliasValueMap", aliasValueMap);
		aliasValueAndTypeMap.put("aliasTypeMap", aliasTypeMap);
		aliasValueAndTypeMap.put("aliasValueI18nMap", aliasValueI18nMap);
		aliasValueAndTypeMap.put("aliasValueAppendMap", aliasValueAppendMap);
		return aliasValueAndTypeMap;
	}
	
	/**
	 * 读取excel数据，并生成导入DB用的sql:
	 * 
	 * @Description: CHRS2.0 SI PROJECT
	 * @author penghaixia
	 * @date 2014.6.10
	 * @version V1.0
	 */
	@SuppressWarnings( { "unchecked" })
	public ModelMap preparePaiQianDiForImportExcel(HttpServletRequest request,
			String adminID, LinkedHashMap cellmap) {
		ModelMap result = new ModelMap();
		result.put("dataMap", "");
		result.put("token", 0);

		int k = path.indexOf("\\");
		String server = "win";
		if (k < 0) { // WebServer的os为unix/linux
			server = "u/l";
		}
		File file = null;
		if ("win".equals(server)) {
			file = new File(path + "\\" + filename + ".xls");
		} else {
			file = new File(path + "/" + filename + ".xls");
		}
		Map dataMap = new HashMap();
		try {
			Workbook workBook = Workbook.getWorkbook(file);
			// 获得第一个工作表对象
			Sheet sheet = workBook.getSheet(0);
			// 验证是否存在导入数据
			if (sheet.getRows() < 2) {
				logger.debug("Import file not exist data, return.");
				result.put("token", 0);
				return result;
			}
			String tableName = cellmap.get("tableName") == null ? "" : cellmap
					.get("tableName").toString();// 获取表名
			LinkedHashMap aliasValueMap = cellmap.get("aliasValueMap") == null ? null
					: (LinkedHashMap) cellmap.get("aliasValueMap");
			LinkedHashMap aliasTypeMap = cellmap.get("aliasTypeMap") == null ? null
					: (LinkedHashMap) cellmap.get("aliasTypeMap");
			LinkedHashMap aliasValueI18nMap = cellmap.get("aliasValueI18nMap") == null ? null
					: (LinkedHashMap) cellmap.get("aliasValueI18nMap");
			// LinkedHashMap aliasValueAppendMap =
			// cellmap.get("aliasValueAppendMap") == null ? null:
			// (LinkedHashMap) cellmap.get("aliasValueAppendMap");

			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			LinkedHashMap aliasMapCells = new LinkedHashMap();
			if (aliasValueMap != null && aliasTypeMap != null) {// 存放CELL0：NUMBER类型的键值对
				Set keySet = aliasValueMap.keySet();
				if (keySet != null) {
					Iterator it = keySet.iterator();
					while (it.hasNext()) {
						Object keyvalue = it.next();
						if (keyvalue != null) {
							Object aliasValue = aliasValueMap.get(keyvalue);
							if (aliasValue != null) {
								aliasMapCells.put(aliasValue.toString(),
										aliasTypeMap.get(keyvalue));
							}
						}
					}
				}
			}
			List addList = new ArrayList();
			// 循环工作表的每行数据
			int init = 0;
			int checkFlag = 0;
			Cell[] headerRow = sheet.getRow(0);
			init = sheet.getRow(0).length;
			for (int i = 1; i < sheet.getRows(); i++) {
				// 验证当前行是否符合规范
				if (!validateCell(sheet.getRow(i), sheet.getRow(0).length)) {// 如果所在行全为空，不导入
					break;
				}
				Cell[] row = sheet.getRow(i);
				if (init == 0) {
					init = sheet.getRow(0).length;
				}
				LinkedHashMap smap = new LinkedHashMap();
				LinkedHashMap aliasCellmap = new LinkedHashMap();// 读取Excel数据
				for (int j = 0; j < init; j++) {
					try {
						String cellValue = "";
						Object cellType = aliasMapCells.get("#CELL" + j + "#");
						if (cellType != null
								&& !row[j].getContents().equals("")) {// date类型
							try {
								cellValue = this.FormateData(row[j], cellType);
								if (cellValue.contains("/")) {
									String[] contentStr = cellValue.split("/");
									if (contentStr.length == 3
											&& contentStr[0].length() == 2
											&& contentStr[2].length() == 4) {
										cellValue = contentStr[2] + "/"
												+ contentStr[1] + "/"
												+ contentStr[0];
									}
								} else if (cellValue.contains("-")) {
									String[] contentStr = cellValue.split("-");
									if (contentStr.length == 3
											&& contentStr[0].length() == 2
											&& contentStr[2].length() == 4) {
										cellValue = contentStr[2] + "-"
												+ contentStr[1] + "-"
												+ contentStr[0];
									}
								}
							} catch (Exception e) {
								cellValue = "";
								if (row[j].getContents() != null) {
									cellValue = row[j].getContents();
									if (cellValue.contains("/")) {
										String[] contentStr = cellValue
												.split("/");
										if (contentStr.length == 3
												&& contentStr[0].length() == 2
												&& contentStr[2].length() == 4) {
											cellValue = contentStr[2] + "/"
													+ contentStr[1] + "/"
													+ contentStr[0];
										}
									} else if (cellValue.contains("-")) {
										String[] contentStr = cellValue
												.split("-");
										if (contentStr.length == 3
												&& contentStr[0].length() == 2
												&& contentStr[2].length() == 4) {
											cellValue = contentStr[2] + "-"
													+ contentStr[1] + "-"
													+ contentStr[0];
										}
									}
								}
							}
						} else {
							cellValue = row[j].getContents() == null ? ""
									: row[j].getContents();
						}
						aliasCellmap.put("#CELL" + j + "#",
								cellValue == null ? "" : cellValue.trim());
					} catch (Exception e) {
						e.printStackTrace();
						result.put("token", 2);
						return result;
					}
				}
				String aliasKeySql = "";
				String aliasValueSql = "";
				int initKey = 0;
				if (aliasValueMap != null && aliasTypeMap != null) {
					Set keySet = aliasValueMap.keySet();
					if (keySet != null) {
						Iterator it = keySet.iterator();
						while (it.hasNext()) {
							initKey++;
							Object keyValue = it.next();
							if (keyValue != null && !keyValue.equals("")) {
								int fieldType = Integer.parseInt(aliasTypeMap
										.get(keyValue).toString());
								Object aliasCellmapValue = aliasCellmap
										.get(aliasValueMap.get(keyValue));
								if (aliasCellmapValue == null) {// 不属于excel的列
									aliasCellmapValue = aliasValueMap
											.get(keyValue);
									if (aliasCellmapValue != null
											&& aliasCellmapValue.toString()
													.contains("#CELL")) {
										String sqlContent = aliasCellmapValue
												.toString();
										for (int l = 0; l < init; l++) {
											sqlContent = sqlContent.replaceAll(
													"#CELL" + l + "#",
													aliasCellmap.get(
															"#CELL" + l + "#")
															.toString());
											if (!sqlContent.contains("#CELL")) {
												break;
											}
										}
										LinkedHashMap sqlI8nContentMap = new LinkedHashMap();
										switch (fieldType) {
										case 2:
											sqlI8nContentMap.put(
													"sqlI18nContent",
													sqlContent);
											aliasCellmapValue = this
													.getCodeNoByName(sqlI8nContentMap) == -1 ? ""
													: this
															.getCodeNoByName(sqlI8nContentMap);
											break;
										default:
											sqlI8nContentMap.put("sqlContent",
													sqlContent);
											if (this
													.getContentNoByFiled(sqlI8nContentMap) != null) {
												Object cellObj = this
														.getContentNoByFiled(
																sqlI8nContentMap)
														.size() == 0 ? ""
														: this
																.getContentNoByFiled(
																		sqlI8nContentMap)
																.get(0);
												if (cellObj != null
														&& !cellObj.equals("")
														&& ((Map) cellObj)
																.get(keyValue
																		.toString()) != null) {
													aliasCellmapValue = (cellObj == null ? ""
															: ((Map) cellObj)
																	.get(
																			keyValue
																					.toString()
																					.toUpperCase())
																	.toString());
												} else {
													aliasCellmapValue = "";
												}
											}

											break;
										}
									}
								} else {
									if (aliasValueI18nMap != null
											&& aliasValueI18nMap.get(keyValue) != null) {// 有需要从国际化表读取No的列
										String sqlContent = aliasValueI18nMap
												.get(keyValue) == null ? ""
												: aliasValueI18nMap.get(
														keyValue).toString();
										sqlContent = sqlContent
												.replaceAll(
														aliasValueMap
																.get(keyValue) == null ? ""
																: aliasValueMap
																		.get(
																				keyValue)
																		.toString(),
														"'"
																+ aliasCellmapValue
																		.toString()
																+ "'");

										LinkedHashMap sqlI8nContentMap = new LinkedHashMap();
										sqlI8nContentMap.put("sqlI18nContent",
												sqlContent);
										if (aliasValueI18nMap.get(keyValue
												+ "_FLAG") != null
												&& aliasValueI18nMap.get(
														keyValue + "_FLAG")
														.equals("Y")) {
											sqlI8nContentMap.put("sqlContent",
													sqlContent);
											List contentList = this
													.getContentNoByFiled(sqlI8nContentMap);
											if (contentList.get(0) != null
													&& ((Map) (contentList
															.get(0)))
															.get("CPNY_ID") != null) {
												aliasCellmapValue = (contentList == null ? ""
														: ((Map) (contentList
																.get(0))).get(
																"CPNY_ID")
																.toString());
											} else if (contentList.get(0) != null
													&& ((Map) (contentList
															.get(0)))
															.get("DEPTNO") != null) {
												aliasCellmapValue = (contentList == null ? ""
														: ((Map) (contentList
																.get(0))).get(
																"DEPTNO")
																.toString());
											} else {
												aliasCellmapValue = "";
											}
										} else {
											aliasCellmapValue = this
													.getCodeNoByName(sqlI8nContentMap) == -1 ? ""
													: this
															.getCodeNoByName(sqlI8nContentMap);
										}
										if (aliasCellmapValue.equals("")) {
											System.out
													.println(aliasValueMap.get(
															keyValue)
															.toString()
															+ "-----------------------------------");
										}
									}
								}
								switch (fieldType) {
								case 0:
									if (initKey == 1) {
										aliasKeySql = keyValue.toString();
										aliasValueSql = "'" + aliasCellmapValue
												+ "'";
									} else {
										aliasKeySql += ","
												+ keyValue.toString();
										aliasValueSql += ",'"
												+ aliasCellmapValue + "'";
									}
									break;
								case 1:
									if (initKey == 1) {
										aliasKeySql = keyValue.toString();
										if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("/")) {
											aliasValueSql = "to_date('"
													+ aliasCellmapValue
													+ "','YYYY/MM/DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										} else if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("-")) {
											aliasValueSql = "to_date('"
													+ aliasCellmapValue
													+ "','YYYY-MM-DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										}
									} else {
										aliasKeySql += ","
												+ keyValue.toString();
										if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("/")) {
											aliasValueSql += ",to_date('"
													+ aliasCellmapValue
													+ "','YYYY/MM/DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										} else if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("-")) {
											aliasValueSql += ",to_date('"
													+ aliasCellmapValue
													+ "','YYYY-MM-DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										}
									}
									break;
								case 11:
									if (initKey == 1) {
										aliasKeySql = keyValue.toString();
										if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("/")) {
											aliasValueSql = "to_date('"
													+ aliasCellmapValue
													+ "','YYYY/MM/DD HH24:MI:SS')";// "'"+aliasCellmapValue+"'";
										} else if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("-")) {
											aliasValueSql = "to_date('"
													+ aliasCellmapValue
													+ "','YYYY-MM-DD HH24:MI:SS')";// "'"+aliasCellmapValue+"'";
										}
									} else {
										aliasKeySql += ","
												+ keyValue.toString();
										if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("/")) {
											aliasValueSql += ",to_date('"
													+ aliasCellmapValue
													+ "','YYYY/MM/DD HH24:MI:SS')";// "'"+aliasCellmapValue+"'";
										} else if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("-")) {
											aliasValueSql += ",to_date('"
													+ aliasCellmapValue
													+ "','YYYY-MM-DD HH24:MI:SS')";
										}
									}
									break;
								case 12:
									if (initKey == 1) {
										aliasKeySql = keyValue.toString();
										if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("/")) {
											aliasValueSql = "to_date('"
													+ aliasCellmapValue
													+ "','YYYY/MM/DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										} else if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("-")) {
											aliasValueSql = "to_date('"
													+ aliasCellmapValue
													+ "','YYYY-MM-DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										}
									} else {
										aliasKeySql += ","
												+ keyValue.toString();
										if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("/")) {
											aliasValueSql += ",to_date('"
													+ aliasCellmapValue
													+ "','YYYY/MM/DD HH24:MI')";// "'"+aliasCellmapValue+"'";
										} else if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("-")) {
											aliasValueSql += ",to_date('"
													+ aliasCellmapValue
													+ "','YYYY-MM-DD HH24:MI')";
										}
									}
									break;
								default:
									if (initKey == 1) {
										aliasKeySql = keyValue.toString();
										aliasValueSql = (aliasCellmapValue == null ? "null"
												: aliasCellmapValue.toString());
									} else {
										aliasKeySql += ","
												+ keyValue.toString();
										aliasValueSql += ","
												+ (aliasCellmapValue == null ? "null"
														: aliasCellmapValue
																.toString())
												+ "";
									}
									break;
								}
							}
						}
					}
				}
				// aliasKeySql +=
				// aliasValueAppendMap.get("appendField")+",LINE_ID";
				// aliasValueSql+= aliasValueAppendMap.get("appendValue")+","+i;
				smap.put("tableName", tableName);
				smap.put("aliasNameKeyContent", aliasKeySql);
				smap.put("aliasValueContent", aliasValueSql);
				addList.add(smap);
			}
			dataMap.put("addList", addList);
			result.put("token", 1);
			result.put("dataMap", dataMap);

		} catch (Exception e) { // 未找到文件异常
			e.printStackTrace();
			result.put("token", 0);
			return result;
		}
		return result;
	}

	/**
	 * 读取excel数据，并生成导入DB用的sql:
	 * 
	 * @Description: CHRS2.0 SI PROJECT
	 * @author penghaixia
	 * @date 2014.6.10
	 * @version V1.0
	 */
	@SuppressWarnings( { "unchecked" })
	public ModelMap prepareForImportExcel(HttpServletRequest request,
			String adminID, LinkedHashMap cellmap) {
		ModelMap result = new ModelMap();
		result.put("dataMap", "");
		result.put("token", 0);

		int k = path.indexOf("\\");
		String server = "win";
		if (k < 0) { // WebServer的os为unix/linux
			server = "u/l";
		}
		File file = null;
		if ("win".equals(server)) {
			file = new File(path + "\\" + filename + ".xls");
		} else {
			file = new File(path + "/" + filename + ".xls");
		}
		Map dataMap = new HashMap();
		try {
			Workbook workBook = Workbook.getWorkbook(file);
			// 获得第一个工作表对象
			Sheet sheet = workBook.getSheet(0);
			// 验证是否存在导入数据
			if (sheet.getRows() < 2) {
				logger.debug("Import file not exist data, return.");
				result.put("token", 0);
				return result;
			}
			String tableName = cellmap.get("tableName") == null ? "" : cellmap
					.get("tableName").toString();// 获取表名
			LinkedHashMap aliasValueMap = cellmap.get("aliasValueMap") == null ? null
					: (LinkedHashMap) cellmap.get("aliasValueMap");
			LinkedHashMap aliasTypeMap = cellmap.get("aliasTypeMap") == null ? null
					: (LinkedHashMap) cellmap.get("aliasTypeMap");
			LinkedHashMap aliasValueI18nMap = cellmap.get("aliasValueI18nMap") == null ? null
					: (LinkedHashMap) cellmap.get("aliasValueI18nMap");
			LinkedHashMap aliasValueAppendMap = cellmap
					.get("aliasValueAppendMap") == null ? null
					: (LinkedHashMap) cellmap.get("aliasValueAppendMap");

			AdminBean admin = SessionUtil.getLoginUserFromSession(request);

			LinkedHashMap aliasMapCells = new LinkedHashMap();
			if (aliasValueMap != null && aliasTypeMap != null) {// 存放CELL0：NUMBER类型的键值对
				Set keySet = aliasValueMap.keySet();
				if (keySet != null) {
					Iterator it = keySet.iterator();
					while (it.hasNext()) {
						Object keyvalue = it.next();
						if (keyvalue != null) {
							Object aliasValue = aliasValueMap.get(keyvalue);
							if (aliasValue != null) {
								aliasMapCells.put(aliasValue.toString(),
										aliasTypeMap.get(keyvalue));
							}
						}
					}
				}
			}
			List addList = new ArrayList();
			// 循环工作表的每行数据
			int colCnt = sheet.getRow(0).length;
			Cell[] headerRow = sheet.getRow(0);
			for (int i = 1; i < sheet.getRows(); i++) {
				Cell[] row = sheet.getRow(i);
				// 验证当前行是否符合规范
				result = validateCellForRequiredCol(headerRow, row, colCnt);
				if ((Integer) result.get("token") != 1) {
					// 不符合规范
					break;
				} else {
					// 符合规范
					LinkedHashMap smap = new LinkedHashMap();
					LinkedHashMap aliasCellmap = new LinkedHashMap();// 读取Excel数据
					for (int j = 0; j < colCnt; j++) {
						try {
							if (j < row.length) {
								String cellValue = "";
								Object cellType = aliasMapCells.get("#CELL" + j
										+ "#");
								if (cellType != null
										&& !row[j].getContents().equals("") 
										&& 3 != Integer.parseInt(cellType.toString())) {// date类型
									try {
										cellValue = this.FormateData(row[j],
												cellType);
										if (cellValue.contains("/")) {
											String[] contentStr = cellValue
													.split("/");
											if (contentStr.length == 3
													&& contentStr[0].length() == 2
													&& contentStr[2].length() == 4) {
												cellValue = contentStr[2] + "/"
														+ contentStr[1] + "/"
														+ contentStr[0];
											}
										} else if (cellValue.contains("-")) {
											String[] contentStr = cellValue
													.split("-");
											if (contentStr.length == 3
													&& contentStr[0].length() == 2
													&& contentStr[2].length() == 4) {
												cellValue = contentStr[2] + "-"
														+ contentStr[1] + "-"
														+ contentStr[0];
											}
										}
									} catch (Exception e) {
										cellValue = "";
										if (row[j].getContents() != null) {
											cellValue = row[j].getContents();
											if (cellValue.contains("/")) {
												String[] contentStr = cellValue
														.split("/");
												if (contentStr.length == 3
														&& contentStr[0]
																.length() == 2
														&& contentStr[2]
																.length() == 4) {
													cellValue = contentStr[2]
															+ "/"
															+ contentStr[1]
															+ "/"
															+ contentStr[0];
												}
											} else if (cellValue.contains("-")) {
												String[] contentStr = cellValue
														.split("-");
												if (contentStr.length == 3
														&& contentStr[0]
																.length() == 2
														&& contentStr[2]
																.length() == 4) {
													cellValue = contentStr[2]
															+ "-"
															+ contentStr[1]
															+ "-"
															+ contentStr[0];
												}
											}
										}
									}
								}else if (cellType != null
										&& !row[j].getContents().equals("") 
										&& 3 == Integer.parseInt(cellType.toString())) {
									try {
										cellValue = this.FormateData(row[j],
												cellType);
										if (cellValue.contains("/")) {
											String[] contentStr = cellValue
													.split("/");
											if (contentStr.length == 3
													&& contentStr[0].length() == 2
													&& contentStr[2].length() == 4) {
												cellValue = contentStr[0] + "/"
														+ contentStr[1] + "/"
														+ contentStr[2];
											}
										} else if (cellValue.contains("-")) {
											String[] contentStr = cellValue
													.split("-");
											if (contentStr.length == 3
													&& contentStr[0].length() == 2
													&& contentStr[2].length() == 4) {
												cellValue = contentStr[0] + "-"
														+ contentStr[1] + "-"
														+ contentStr[2];
											}
										}
									} catch (Exception e) {
										cellValue = "";
										if (row[j].getContents() != null) {
											cellValue = row[j].getContents();
											if (cellValue.contains("/")) {
												String[] contentStr = cellValue
														.split("/");
												if (contentStr.length == 3
														&& contentStr[0]
																.length() == 2
														&& contentStr[2]
																.length() == 4) {
													cellValue = contentStr[0]
															+ "/"
															+ contentStr[1]
															+ "/"
															+ contentStr[2];
												}
											} else if (cellValue.contains("-")) {
												String[] contentStr = cellValue
														.split("-");
												if (contentStr.length == 3
														&& contentStr[0]
																.length() == 2
														&& contentStr[2]
																.length() == 4) {
													cellValue = contentStr[0]
															+ "-"
															+ contentStr[1]
															+ "-"
															+ contentStr[2];
												}
											}
										}
									}
								}else {
									cellValue = row[j].getContents() == null ? ""
											: row[j].getContents();
								}
								aliasCellmap.put("#CELL" + j + "#",
										cellValue == null ? "" : cellValue
												.trim());
							} else {
								aliasCellmap.put("#CELL" + j + "#", "");
							}
						} catch (Exception e) {
							e.printStackTrace();
							result.put("token", 2);
							return result;
						}
					}
					String aliasKeySql = "";
					String aliasValueSql = "";
					int colKey = 0;
					if (aliasValueMap != null && aliasTypeMap != null) {
						Set keySet = aliasValueMap.keySet();
						if (keySet != null) {
							Iterator it = keySet.iterator();
							while (it.hasNext()) {
								colKey++;
								Object keyValue = it.next();
								if (keyValue != null && !keyValue.equals("")) {
									int fieldType = Integer
											.parseInt(aliasTypeMap
													.get(keyValue).toString());
									Object aliasCellmapValue = aliasCellmap
											.get(aliasValueMap.get(keyValue));
									if (aliasCellmapValue == null) {// 不属于excel的列
										aliasCellmapValue = aliasValueMap
												.get(keyValue);
										if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("#CELL")) {
											String sqlContent = aliasCellmapValue
													.toString();
											for (int l = 0; l < colCnt; l++) {
												sqlContent = sqlContent
														.replaceAll(
																"#CELL" + l
																		+ "#",
																aliasCellmap
																		.get(
																				"#CELL"
																						+ l
																						+ "#")
																		.toString());
												if (!sqlContent
														.contains("#CELL")) {
													break;
												}
											}
											LinkedHashMap sqlI8nContentMap = new LinkedHashMap();
											switch (fieldType) {
											case 2:
												sqlI8nContentMap.put(
														"sqlI18nContent",
														sqlContent);
												aliasCellmapValue = this
														.getCodeNoByName(sqlI8nContentMap) == -1 ? ""
														: this
																.getCodeNoByName(sqlI8nContentMap);
												break;
											default:
												sqlI8nContentMap.put(
														"sqlContent",
														sqlContent);
												if (this
														.getContentNoByFiled(sqlI8nContentMap) != null) {
													Object cellObj = this
															.getContentNoByFiled(
																	sqlI8nContentMap)
															.size() == 0 ? ""
															: this
																	.getContentNoByFiled(
																			sqlI8nContentMap)
																	.get(0);
													if (cellObj != null
															&& !cellObj
																	.equals("")
															&& ((Map) cellObj)
																	.get(keyValue
																			.toString()) != null) {
														aliasCellmapValue = (cellObj == null ? ""
																: ((Map) cellObj)
																		.get(
																				keyValue
																						.toString()
																						.toUpperCase())
																		.toString());
													} else {
														aliasCellmapValue = "";
													}
												}

												break;
											}
										}
									} else {
										if (aliasValueI18nMap != null
												&& aliasValueI18nMap
														.get(keyValue) != null) {// 有需要从国际化表读取No的列
											String sqlContent = aliasValueI18nMap
													.get(keyValue) == null ? ""
													: aliasValueI18nMap.get(
															keyValue)
															.toString();
											sqlContent = sqlContent
													.replaceAll(
															aliasValueMap
																	.get(keyValue) == null ? ""
																	: aliasValueMap
																			.get(
																					keyValue)
																			.toString(),
															"'"
																	+ aliasCellmapValue
																			.toString()
																	+ "'");

											LinkedHashMap sqlI8nContentMap = new LinkedHashMap();
											sqlI8nContentMap.put(
													"sqlI18nContent",
													sqlContent);
											if (aliasValueI18nMap.get(keyValue
													+ "_FLAG") != null
													&& aliasValueI18nMap.get(
															keyValue + "_FLAG")
															.equals("Y")) {
												sqlI8nContentMap.put(
														"sqlContent",
														sqlContent);
												List contentList = this
														.getContentNoByFiled(sqlI8nContentMap);
												if (contentList.get(0) != null
														&& ((Map) (contentList
																.get(0)))
																.get("CPNY_ID") != null) {
													aliasCellmapValue = (contentList == null ? ""
															: ((Map) (contentList
																	.get(0)))
																	.get(
																			"CPNY_ID")
																	.toString());
												} else if (contentList.get(0) != null
														&& ((Map) (contentList
																.get(0)))
																.get("DEPTNO") != null) {
													aliasCellmapValue = (contentList == null ? ""
															: ((Map) (contentList
																	.get(0)))
																	.get(
																			"DEPTNO")
																	.toString());
												} else {
													aliasCellmapValue = "";
												}
											} else {
												aliasCellmapValue = this
														.getCodeNoByName(sqlI8nContentMap) == -1 ? ""
														: this
																.getCodeNoByName(sqlI8nContentMap);
											}
											if (aliasCellmapValue.equals("")) {
												System.out
														.println(aliasValueMap
																.get(keyValue)
																.toString()
																+ "-----------------------------------");
											}
										}
									}
									switch (fieldType) {
									case 0:
										if (colKey == 1) {
											aliasKeySql = keyValue.toString();
											aliasValueSql = "'"
													+ aliasCellmapValue + "'";
										} else {
											aliasKeySql += ","
													+ keyValue.toString();
											aliasValueSql += ",'"
													+ aliasCellmapValue + "'";
										}
										break;
									case 3:
										if (colKey == 1) {
											aliasKeySql = keyValue.toString();
											aliasValueSql = "'"
													+ aliasCellmapValue + "'";
										} else {
											aliasKeySql += ","
													+ keyValue.toString();
											aliasValueSql += ",'"
													+ aliasCellmapValue + "'";
										}
										break;
									case 1:
										if (colKey == 1) {
											aliasKeySql = keyValue.toString();
											if (aliasCellmapValue != null
													&& aliasCellmapValue
															.toString()
															.contains("/")) {
												aliasValueSql = "to_date('"
														+ aliasCellmapValue
														+ "','YYYY/MM/DD HH24:MI')";// "'"+aliasCellmapValue+"'";
											} else if (aliasCellmapValue != null
													&& aliasCellmapValue
															.toString()
															.contains("-")) {
												aliasValueSql = "to_date('"
														+ aliasCellmapValue
														+ "','YYYY-MM-DD HH24:MI')";// "'"+aliasCellmapValue+"'";
											}
										} else {
											aliasKeySql += ","
													+ keyValue.toString();
											if (aliasCellmapValue != null
													&& aliasCellmapValue
															.toString()
															.contains("/")) {
												aliasValueSql += ",to_date('"
														+ aliasCellmapValue
														+ "','YYYY/MM/DD HH24:MI')";// "'"+aliasCellmapValue+"'";
											} else if (aliasCellmapValue != null
													&& aliasCellmapValue
															.toString()
															.contains("-")) {
												aliasValueSql += ",to_date('"
														+ aliasCellmapValue
														+ "','YYYY-MM-DD HH24:MI')";// "'"+aliasCellmapValue+"'";
											} else {
												aliasValueSql += ",null";
											}
										}
										break;
									case 11:
										if (colKey == 1) {
											aliasKeySql = keyValue.toString();
											if (aliasCellmapValue != null
													&& aliasCellmapValue
															.toString()
															.contains("/")) {
												aliasValueSql = "to_date('"
														+ aliasCellmapValue
														+ "','YYYY/MM/DD HH24:MI:SS')";// "'"+aliasCellmapValue+"'";
											} else if (aliasCellmapValue != null
													&& aliasCellmapValue
															.toString()
															.contains("-")) {
												aliasValueSql = "to_date('"
														+ aliasCellmapValue
														+ "','YYYY-MM-DD HH24:MI:SS')";// "'"+aliasCellmapValue+"'";
											}
										} else {
											aliasKeySql += ","
													+ keyValue.toString();
											if (aliasCellmapValue != null
													&& aliasCellmapValue
															.toString()
															.contains("/")) {
												aliasValueSql += ",to_date('"
														+ aliasCellmapValue
														+ "','YYYY/MM/DD HH24:MI:SS')";// "'"+aliasCellmapValue+"'";
											} else if (aliasCellmapValue != null
													&& aliasCellmapValue
															.toString()
															.contains("-")) {
												aliasValueSql += ",to_date('"
														+ aliasCellmapValue
														+ "','YYYY-MM-DD HH24:MI:SS')";
											} else if (aliasCellmapValue != null
													&& aliasCellmapValue
															.toString()
															.length() >= 8) {
												aliasValueSql += ",to_date('"
														+ aliasCellmapValue
														+ "','YYYY-MM-DD HH24:MI:SS')";
											}
										}
										break;
									case 12:
										if (colKey == 1) {
											aliasKeySql = keyValue.toString();
											if (aliasCellmapValue != null
													&& aliasCellmapValue
															.toString()
															.contains("/")) {
												aliasValueSql = "to_date('"
														+ aliasCellmapValue
														+ "','YYYY/MM/DD HH24:MI')";// "'"+aliasCellmapValue+"'";
											} else if (aliasCellmapValue != null
													&& aliasCellmapValue
															.toString()
															.contains("-")) {
												aliasValueSql = "to_date('"
														+ aliasCellmapValue
														+ "','YYYY-MM-DD HH24:MI')";// "'"+aliasCellmapValue+"'";
											}
										} else {
											aliasKeySql += ","
													+ keyValue.toString();
											if (aliasCellmapValue != null
													&& aliasCellmapValue
															.toString()
															.contains("/")) {
												aliasValueSql += ",to_date('"
														+ aliasCellmapValue
														+ "','YYYY/MM/DD HH24:MI')";// "'"+aliasCellmapValue+"'";
											} else if (aliasCellmapValue != null
													&& aliasCellmapValue
															.toString()
															.contains("-")) {
												aliasValueSql += ",to_date('"
														+ aliasCellmapValue
														+ "','YYYY-MM-DD HH24:MI')";
											}
										}
										break;
									default:
										if (colKey == 1) {
											aliasKeySql = keyValue.toString();
											aliasValueSql = (aliasCellmapValue == null ? "null"
													: aliasCellmapValue
															.toString());
										} else {
											aliasKeySql += ","
													+ keyValue.toString();
											aliasValueSql += ","
													+ (aliasCellmapValue == null ? "null"
															: (aliasCellmapValue
																	.equals("") ? "null"
																	: aliasCellmapValue
																			.toString()));
										}
										break;
									}
								}
							}
						}
					}
					aliasKeySql += aliasValueAppendMap.get("appendField")
							+ ",LINE_ID";
					aliasValueSql += aliasValueAppendMap.get("appendValue")
							+ "," + i;
					smap.put("tableName", tableName);
					smap.put("aliasNameKeyContent", aliasKeySql);
					smap.put("aliasValueContent", aliasValueSql);
					addList.add(smap);
				}
				dataMap.put("addList", addList);
				result.put("token", 1);
				result.put("dataMap", dataMap);
			}
		} catch (Exception e) { // 未找到文件异常
			e.printStackTrace();
			System.out.println(e.getMessage());
			result.put("token", 0);
		}
		return result;
	}
	/**
	 * 读取excel数据，并生成导入DB用的sql: hr_evaluate_info
	 * 
	 * @Description: CHRS2.0 SI PROJECT
	 * @author penghaixia
	 * @date 2014.6.10
	 * @version V1.0
	 */
	@SuppressWarnings( { "unchecked" })
	public ModelMap evaluateForImportExcel(HttpServletRequest request,
			String adminID, LinkedHashMap cellmap) {
		ModelMap result = new ModelMap();
		result.put("dataMap", "");
		result.put("token", 0);
		
		int k = path.indexOf("\\");
		String server = "win";
		if (k < 0) { // WebServer的os为unix/linux
			server = "u/l";
		}
		File file = null;
		if ("win".equals(server)) {
			file = new File(path + "\\" + filename + ".xls");
		} else {
			file = new File(path + "/" + filename + ".xls");
		}
		Map dataMap = new HashMap();
		try {
			Workbook workBook = Workbook.getWorkbook(file);
			// 获得第一个工作表对象
			Sheet sheet = workBook.getSheet(0);
			// 验证是否存在导入数据
			if (sheet.getRows() < 2) {
				logger.debug("Import file not exist data, return.");
				result.put("token", 0);
				return result;
			}
			String tableName = cellmap.get("tableName") == null ? "" : cellmap
					.get("tableName").toString();// 获取表名
			LinkedHashMap aliasValueMap = cellmap.get("aliasValueMap") == null ? null
					: (LinkedHashMap) cellmap.get("aliasValueMap");
			LinkedHashMap aliasTypeMap = cellmap.get("aliasTypeMap") == null ? null
					: (LinkedHashMap) cellmap.get("aliasTypeMap");
			LinkedHashMap aliasValueI18nMap = cellmap.get("aliasValueI18nMap") == null ? null
					: (LinkedHashMap) cellmap.get("aliasValueI18nMap");
			LinkedHashMap aliasValueAppendMap = cellmap
			.get("aliasValueAppendMap") == null ? null
					: (LinkedHashMap) cellmap.get("aliasValueAppendMap");
			
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			
			LinkedHashMap aliasMapCells = new LinkedHashMap();
			if (aliasValueMap != null && aliasTypeMap != null) {// 存放CELL0：NUMBER类型的键值对
				Set keySet = aliasValueMap.keySet();
				if (keySet != null) {
					Iterator it = keySet.iterator();
					while (it.hasNext()) {
						Object keyvalue = it.next();
						if (keyvalue != null) {
							Object aliasValue = aliasValueMap.get(keyvalue);
							if (aliasValue != null) {
								aliasMapCells.put(aliasValue.toString(),
										aliasTypeMap.get(keyvalue));
							}
						}
					}
				}
			}
			List addList = new ArrayList();
			// 循环工作表的每行数据
			int colCnt = sheet.getRow(0).length;
			Cell[] headerRow = sheet.getRow(0);
			for (int i = 1; i < sheet.getRows(); i++) {
				Cell[] row = sheet.getRow(i);
				// 验证当前行是否符合规范
				result = validateCellForRequiredCol(headerRow, row, colCnt);
				if ((Integer) result.get("token") != 1) {
					// 不符合规范
					break;
				} else {
					// 符合规范
					LinkedHashMap smap = new LinkedHashMap();
					LinkedHashMap aliasCellmap = new LinkedHashMap();// 读取Excel数据
					for (int j = 0; j < colCnt; j++) {
						try {
							if (j < row.length) {
								String cellValue = "";
								Object cellType = aliasMapCells.get("#CELL" + j
										+ "#");
								if (cellType != null
										&& !row[j].getContents().equals("")) {// date类型
									try {
										cellValue = this.FormateData(row[j],
												cellType);
										if (cellValue.contains("/")) {
											String[] contentStr = cellValue
											.split("/");
											if (contentStr.length == 3
													&& contentStr[0].length() == 2
													&& contentStr[2].length() == 4) {
												cellValue = contentStr[2] + "/"
												+ contentStr[1] + "/"
												+ contentStr[0];
											}
										} else if (cellValue.contains("-")) {
											String[] contentStr = cellValue
											.split("-");
											if (contentStr.length == 3
													&& contentStr[0].length() == 2
													&& contentStr[2].length() == 4) {
												cellValue = contentStr[2] + "-"
												+ contentStr[1] + "-"
												+ contentStr[0];
											}
										}
									} catch (Exception e) {
										cellValue = "";
										if (row[j].getContents() != null) {
											cellValue = row[j].getContents();
											if (cellValue.contains("/")) {
												String[] contentStr = cellValue
												.split("/");
												if (contentStr.length == 3
														&& contentStr[0]
														              .length() == 2
														              && contentStr[2]
														                            .length() == 4) {
													cellValue = contentStr[2]
													                       + "/"
													                       + contentStr[1]
													                                    + "/"
													                                    + contentStr[0];
												}
											} else if (cellValue.contains("-")) {
												String[] contentStr = cellValue
												.split("-");
												if (contentStr.length == 3
														&& contentStr[0]
														              .length() == 2
														              && contentStr[2]
														                            .length() == 4) {
													cellValue = contentStr[2]
													                       + "-"
													                       + contentStr[1]
													                                    + "-"
													                                    + contentStr[0];
												}
											}
										}
									}
								} else {
									cellValue = row[j].getContents() == null ? ""
											: row[j].getContents();
								}
								aliasCellmap.put("#CELL" + j + "#",
										cellValue == null ? "" : cellValue
												.trim());
							} else {
								aliasCellmap.put("#CELL" + j + "#", "");
							}
						} catch (Exception e) {
							e.printStackTrace();
							result.put("token", 2);
							return result;
						}
					}
					String aliasKeySql = "";
					String aliasValueSql = "";
					int colKey = 0;
					if (aliasValueMap != null && aliasTypeMap != null) {
						Set keySet = aliasValueMap.keySet();
						if (keySet != null) {
							Iterator it = keySet.iterator();
							while (it.hasNext()) {
								colKey++;
								Object keyValue = it.next();
								if (keyValue != null && !keyValue.equals("")) {
									int fieldType = Integer
									.parseInt(aliasTypeMap
											.get(keyValue).toString());
									Object aliasCellmapValue = aliasCellmap
									.get(aliasValueMap.get(keyValue));
									if (aliasCellmapValue == null) {// 不属于excel的列
										aliasCellmapValue = aliasValueMap
										.get(keyValue);
										if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
												.contains("#CELL")) {
											String sqlContent = aliasCellmapValue
											.toString();
											for (int l = 0; l < colCnt; l++) {
												sqlContent = sqlContent
												.replaceAll(
														"#CELL" + l
														+ "#",
														aliasCellmap
														.get(
																"#CELL"
																+ l
																+ "#")
																.toString());
												if (!sqlContent
														.contains("#CELL")) {
													break;
												}
											}
											LinkedHashMap sqlI8nContentMap = new LinkedHashMap();
											switch (fieldType) {
											case 2:
												sqlI8nContentMap.put(
														"sqlI18nContent",
														sqlContent);
												aliasCellmapValue = this
												.getCodeNoByName(sqlI8nContentMap) == -1 ? ""
														: this
														.getCodeNoByName(sqlI8nContentMap);
												break;
											default:
												sqlI8nContentMap.put(
														"sqlContent",
														sqlContent);
												if (this
														.getContentNoByFiled(sqlI8nContentMap) != null) {
													Object cellObj = this
													.getContentNoByFiled(
															sqlI8nContentMap)
															.size() == 0 ? ""
																	: this
																	.getContentNoByFiled(
																			sqlI8nContentMap)
																			.get(0);
													if (cellObj != null
															&& !cellObj
															.equals("")
															&& ((Map) cellObj)
															.get(keyValue
																	.toString()) != null) {
														aliasCellmapValue = (cellObj == null ? ""
																: ((Map) cellObj)
																.get(
																		keyValue
																		.toString()
																		.toUpperCase())
																		.toString());
													} else {
														aliasCellmapValue = "";
													}
												}
												
												break;
											}
										}
									} else {
										if (aliasValueI18nMap != null
												&& aliasValueI18nMap
												.get(keyValue) != null) {// 有需要从国际化表读取No的列
											String sqlContent = aliasValueI18nMap
											.get(keyValue) == null ? ""
													: aliasValueI18nMap.get(
															keyValue)
															.toString();
											sqlContent = sqlContent
											.replaceAll(
													aliasValueMap
													.get(keyValue) == null ? ""
															: aliasValueMap
															.get(
																	keyValue)
																	.toString(),
																	"'"
																	+ aliasCellmapValue
																	.toString()
																	+ "'");
											
											LinkedHashMap sqlI8nContentMap = new LinkedHashMap();
											sqlI8nContentMap.put(
													"sqlI18nContent",
													sqlContent);
											if (aliasValueI18nMap.get(keyValue
													+ "_FLAG") != null
													&& aliasValueI18nMap.get(
															keyValue + "_FLAG")
															.equals("Y")) {
												sqlI8nContentMap.put(
														"sqlContent",
														sqlContent);
												List contentList = this
												.getContentNoByFiled(sqlI8nContentMap);
												if (contentList.get(0) != null
														&& ((Map) (contentList
																.get(0)))
																.get("CPNY_ID") != null) {
													aliasCellmapValue = (contentList == null ? ""
															: ((Map) (contentList
																	.get(0)))
																	.get(
																	"CPNY_ID")
																	.toString());
												} else if (contentList.get(0) != null
														&& ((Map) (contentList
																.get(0)))
																.get("DEPTNO") != null) {
													aliasCellmapValue = (contentList == null ? ""
															: ((Map) (contentList
																	.get(0)))
																	.get(
																	"DEPTNO")
																	.toString());
												} else {
													aliasCellmapValue = "";
												}
											} else {
												aliasCellmapValue = this
												.getCodeNoByName(sqlI8nContentMap) == -1 ? ""
														: this
														.getCodeNoByName(sqlI8nContentMap);
											}
											if (aliasCellmapValue.equals("")) {
												System.out
												.println(aliasValueMap
														.get(keyValue)
														.toString()
														+ "-----------------------------------");
											}
										}
									}
									switch (fieldType) {
									case 0:
										if (colKey == 1) {
											aliasKeySql = keyValue.toString();
											aliasValueSql = "'"
												+ aliasCellmapValue + "'";
										} else {
											aliasKeySql += ","
												+ keyValue.toString();
											aliasValueSql += ",'"
												+ aliasCellmapValue + "'";
										}
										break;
									case 1:
										if (colKey == 1) {
											aliasKeySql = keyValue.toString();
											if (aliasCellmapValue != null
													&& aliasCellmapValue
													.toString()
													.contains("/")) {
												aliasValueSql = "to_date('"
													+ aliasCellmapValue
													+ "','YYYY/MM/DD HH24:MI')";// "'"+aliasCellmapValue+"'";
											} else if (aliasCellmapValue != null
													&& aliasCellmapValue
													.toString()
													.contains("-")) {
												aliasValueSql = "to_date('"
													+ aliasCellmapValue
													+ "','YYYY-MM-DD HH24:MI')";// "'"+aliasCellmapValue+"'";
											}
										} else {
											aliasKeySql += ","
												+ keyValue.toString();
											if (aliasCellmapValue != null
													&& aliasCellmapValue
													.toString()
													.contains("/")) {
												aliasValueSql += ",to_date('"
													+ aliasCellmapValue
													+ "','YYYY/MM/DD HH24:MI')";// "'"+aliasCellmapValue+"'";
											} else if (aliasCellmapValue != null
													&& aliasCellmapValue
													.toString()
													.contains("-")) {
												aliasValueSql += ",to_date('"
													+ aliasCellmapValue
													+ "','YYYY-MM-DD HH24:MI')";// "'"+aliasCellmapValue+"'";
											} else {
												aliasValueSql += ",null";
											}
										}
										break;
									case 11:
										if (colKey == 1) {
											aliasKeySql = keyValue.toString();
											if (aliasCellmapValue != null
													&& aliasCellmapValue
													.toString()
													.contains("/")) {
												aliasValueSql = "to_date('"
													+ aliasCellmapValue
													+ "','YYYY/MM/DD HH24:MI:SS')";// "'"+aliasCellmapValue+"'";
											} else if (aliasCellmapValue != null
													&& aliasCellmapValue
													.toString()
													.contains("-")) {
												aliasValueSql = "to_date('"
													+ aliasCellmapValue
													+ "','YYYY-MM-DD HH24:MI:SS')";// "'"+aliasCellmapValue+"'";
											}
										} else {
											aliasKeySql += ","
												+ keyValue.toString();
											if (aliasCellmapValue != null
													&& aliasCellmapValue
													.toString()
													.contains("/")) {
												aliasValueSql += ",to_date('"
													+ aliasCellmapValue
													+ "','YYYY/MM/DD HH24:MI:SS')";// "'"+aliasCellmapValue+"'";
											} else if (aliasCellmapValue != null
													&& aliasCellmapValue
													.toString()
													.contains("-")) {
												aliasValueSql += ",to_date('"
													+ aliasCellmapValue
													+ "','YYYY-MM-DD HH24:MI:SS')";
											} else if (aliasCellmapValue != null
													&& aliasCellmapValue
													.toString()
													.length() >= 8) {
												aliasValueSql += ",to_date('"
													+ aliasCellmapValue
													+ "','YYYY-MM-DD HH24:MI:SS')";
											}
										}
										break;
									case 12:
										if (colKey == 1) {
											aliasKeySql = keyValue.toString();
											if (aliasCellmapValue != null
													&& aliasCellmapValue
													.toString()
													.contains("/")) {
												aliasValueSql = "to_date('"
													+ aliasCellmapValue
													+ "','YYYY/MM/DD HH24:MI')";// "'"+aliasCellmapValue+"'";
											} else if (aliasCellmapValue != null
													&& aliasCellmapValue
													.toString()
													.contains("-")) {
												aliasValueSql = "to_date('"
													+ aliasCellmapValue
													+ "','YYYY-MM-DD HH24:MI')";// "'"+aliasCellmapValue+"'";
											}
										} else {
											aliasKeySql += ","
												+ keyValue.toString();
											if (aliasCellmapValue != null
													&& aliasCellmapValue
													.toString()
													.contains("/")) {
												aliasValueSql += ",to_date('"
													+ aliasCellmapValue
													+ "','YYYY/MM/DD HH24:MI')";// "'"+aliasCellmapValue+"'";
											} else if (aliasCellmapValue != null
													&& aliasCellmapValue
													.toString()
													.contains("-")) {
												aliasValueSql += ",to_date('"
													+ aliasCellmapValue
													+ "','YYYY-MM-DD HH24:MI')";
											}
										}
										break;
									default:
										if (colKey == 1) {
											aliasKeySql = keyValue.toString();
											aliasValueSql = (aliasCellmapValue == null ? "null"
													: aliasCellmapValue
													.toString());
										} else {
											aliasKeySql += ","
												+ keyValue.toString();
											aliasValueSql += ","
												+ (aliasCellmapValue == null ? "null"
														: (aliasCellmapValue
																.equals("") ? "null"
																		: aliasCellmapValue
																		.toString()));
										}
										break;
									}
								}
							}
						}
						aliasKeySql += aliasValueAppendMap.get("appendField");
				aliasValueSql += aliasValueAppendMap.get("appendValue");
					}
					smap.put("tableName", tableName);
					smap.put("aliasNameKeyContent", aliasKeySql);
					smap.put("aliasValueContent", aliasValueSql);
					addList.add(smap);
				}
				dataMap.put("addList", addList);
				result.put("token", 1);
				result.put("dataMap", dataMap);
			}
		} catch (Exception e) { // 未找到文件异常
			e.printStackTrace();
			System.out.println(e.getMessage());
			result.put("token", 0);
		}
		return result;
	}

	/**
	 * 将excel的数据导入到数据库中（营业员评价数据导入 临时表）
	 * 
	 * @Description: CHRS2.0 SI PROJECT
	 * @author penghaixia
	 * @date 2014.6.10
	 * @version V1.0
	 */
	@SuppressWarnings("unchecked")
	public ModelMap importExcelSalesEvalData(HttpServletRequest request,
			HttpServletResponse response, LinkedHashMap map, ModelMap modelMap) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String adminID = admin.getAdminID() == null ? "USERNO_"
				+ admin.getUserNo() : admin.getAdminID();
		ObjectBindUtil.getRequestParamData(request);
		this.filename = adminID;
		this.pathCpnyID = admin.getCpnyId();
		this.language = admin.getLanguage();
		if (this.processUploadFile(request, response)) {
			// excel数据读取
			ModelMap preparedMap = prepareForImportExcel(request, adminID, map);
			int returnInt = (Integer) preparedMap.get("token");
			if (returnInt == 1) {
				// excel数据读取成功，将数据导入 临时表
				LinkedHashMap paramMap = new LinkedHashMap();
				paramMap.put("IMP_EMPNO", admin.getEmpID());
				paramMap.put("SUBSD_CD", admin.getCpnyId());
				try {
					// 删除临时表中当前用户旧数据
					this.excelUtilDao.deleteImportSalesEvalTemp(paramMap);
					Map dataMap = new HashMap();
					dataMap = (Map) preparedMap.get("dataMap");
					// 为当前用户向临时表中插入数据
					this.excelUtilDao.insertExcelDataForTransferOrder(dataMap);
				} catch (Exception e) {
					e.printStackTrace();
					returnInt = 4;
				}
			}
			if (returnInt == 1) {
				modelMap.put("sign", "1");
				modelMap.put("statusCode", "200");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.importsuccess", request));// 导入成功
				modelMap.put("navTabId", "ar0704");
				modelMap
						.put("forwardUrl",
								"/inct/salesman/viewEvaluationDataImportResultList?pageNum=1");
			} else if (returnInt == 2) {
				modelMap.put("sign", "2");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"inct.message.excelimport.nullRow", request));// 导入数据中有空行，请检查！
			} else if (returnInt == 3) {
				modelMap.put("sign", "3");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"inct.message.excelimport.requiredCol", request));// 导入数据中*项不可以为空，请检查！
			} else if (returnInt == 4) {
				modelMap.put("sign", "4");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage
						.getTipMessage(
								"inct.message.excelimport.writeTempTable.Fail",
								request));// 写入临时表失败！
			} else if (returnInt == 0) {
				modelMap.put("sign", "0");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.importfail", request));// 导入失败!
			}
			File file = new File(this.path + "\\" + filename + ".xls");
			file.delete();
		} else {
			modelMap.put("sign", "0");
			modelMap.put("statusCode", "300");
			modelMap.put("message", TipMessage.getTipMessage(
					"inct.message.excelimport.readExcelFile.Fail", request));// 文件读取失败!
		}
		return modelMap;
	}
	/**
	 * 将excel的数据导入到数据库中（营业员评价数据导入 临时表）
	 * 
	 * @Description: CHRS2.0 SI PROJECT
	 * @author penghaixia
	 * @date 2014.6.10
	 * @version V1.0
	 */
	@SuppressWarnings("unchecked")
	public ModelMap importExcelOTLimitData(HttpServletRequest request,
			HttpServletResponse response, LinkedHashMap map, ModelMap modelMap) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String adminID = admin.getAdminID() == null ? "USERNO_"
				+ admin.getUserNo() : admin.getAdminID();
				ObjectBindUtil.getRequestParamData(request);
				this.filename = adminID;
				this.pathCpnyID = admin.getCpnyId();
				this.language = admin.getLanguage();
				if (this.processUploadFile(request, response)) {
					// excel数据读取
					ModelMap preparedMap = prepareForImportExcel(request, adminID, map);
					int returnInt = (Integer) preparedMap.get("token");
					// 如果excel数据读取成功，将数据导入 临时表
					if (returnInt == 1) {
						LinkedHashMap paramMap = new LinkedHashMap();
						paramMap.put("TABLE_NAME", map.get("tableName"));
						paramMap.put("PERSONID", admin.getPersonId());
						// 删除临时表中当前用户旧数据
						try {
							this.excelUtilDao.deleteImportTemp(paramMap);
						} catch (Exception e) {
							e.printStackTrace();
							returnInt = 0;
						}
						Map dataMap = new HashMap();
						dataMap = (Map) preparedMap.get("dataMap");
						// 为当前用户向临时表中插入数据
						try {
							this.excelUtilDao.insertExcelDataForTransferOrder(dataMap);
						} catch (Exception e) {
							e.printStackTrace();
							returnInt = 0;
					    }
					}
					if (returnInt == 1) {
						modelMap.put("sign", "1");
						modelMap.put("statusCode", "200");
						modelMap.put("message", TipMessage.getTipMessage(
								"ar.alert.message.excelimport.importsuccess", request));// 导入成功
						modelMap.put("navTabId", "se0102");
						modelMap
						.put("forwardUrl",
						"/ess/infoApply/viewOtLimitDataImportResultList?pageNum=1");
					} else if (returnInt == 2) {
						modelMap.put("sign", "2");
						modelMap.put("statusCode", "300");
						modelMap.put("message", TipMessage.getTipMessage(
								"inct.message.excelimport.nullRow", request));// 导入数据中有空行，请检查！
					} else if (returnInt == 3) {
						modelMap.put("sign", "3");
						modelMap.put("statusCode", "300");
						modelMap.put("message", TipMessage.getTipMessage(
								"inct.message.excelimport.requiredCol", request));// 导入数据中*项不可以为空，请检查！
					} else if (returnInt == 4) {
						modelMap.put("sign", "4");
						modelMap.put("statusCode", "300");
						modelMap.put("message", TipMessage
								.getTipMessage(
										"inct.message.excelimport.writeTempTable.Fail",
										request));// 写入临时表失败！
					} else if (returnInt == 0) {
						modelMap.put("sign", "0");
						modelMap.put("statusCode", "300");
						modelMap.put("message", TipMessage.getTipMessage(
								"ar.alert.message.excelimport.importfail", request));// 导入失败!
					}
					File file = new File(this.path + "\\" + filename + ".xls");
					file.delete();
				} else {
					modelMap.put("sign", "0");
					modelMap.put("statusCode", "300");
					modelMap.put("message", TipMessage.getTipMessage(
							"inct.message.excelimport.readExcelFile.Fail", request));// 文件读取失败!
				}
				return modelMap;
	}

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
			HttpServletResponse response, LinkedHashMap[] map,
			ModelMap modelMap, List list, List list1, int num,
			String aliasNullStr) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String adminID = admin.getAdminID() == null ? "USERNO_"
				+ admin.getUserNo() : admin.getAdminID();
		ObjectBindUtil.getRequestParamData(request);
		this.filename = adminID;
		this.pathCpnyID = admin.getCpnyId();
		this.language = admin.getLanguage();
		if (this.processUploadFile(request, response)) {
			this.validateExcelTemplate(request, response, list, list1, num,
					aliasNullStr, modelMap);
			int returnInt = 0;
			if ("200".equals(modelMap.get("statusCode"))) {
				returnInt = this.impUpdateDataBase(request, adminID, map);
			} else {
				return modelMap;
			}
			if (returnInt == 0) {
				modelMap.put("sign", "0");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.importfail", request));// 导入失败!
			} else if (returnInt == 1) {
				modelMap.put("sign", "1");
				modelMap.put("statusCode", "200");
				modelMap.put("rel", "viewInsuranceInputItemDataViewApplyMark");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.importsuccess", request));// 导入成功
			}
			File file = new File(this.path + "\\" + filename + ".xls");
			file.delete();
		} else {
			modelMap.put("sign", "0");
			modelMap.put("statusCode", "300");
			modelMap.put("message", TipMessage.getTipMessage(
					"ar.alert.message.excelimport.importfail", request));// 导入失败!
		}
		return modelMap;
	}

	/**
	 * 分析excel的各个列，获取各个列的值，修改对应数据库的对应值:
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param modelMap
	 * @return
	 */
	@SuppressWarnings( { "unchecked" })
	private int impUpdateDataBase(HttpServletRequest request, String adminID,
			LinkedHashMap[] map) {
		int token = 0;
		int k = path.indexOf("\\");
		String server = "win";
		if (k < 0) { // WebServer的os为unix/linux
			server = "u/l";
		}
		File file = null;
		if ("win".equals(server)) {
			file = new File(path + "\\" + filename + ".xls");
		} else {
			file = new File(path + "/" + filename + ".xls");
		}
		try {
			Workbook workBook = Workbook.getWorkbook(file);
			// 获得第一个工作表对象
			Sheet sheet = workBook.getSheet(0);
			// 验证是否存在导入数据
			if (sheet.getRows() < 2) {
				logger.debug("Import file not exist data, return.");
				return token;
			}
			for (int x = 0; x < map.length; x++) {
				Map dataMap = new HashMap();
				LinkedHashMap cellmap = map[x];
				String tableName = cellmap.get("tableName") == null ? ""
						: cellmap.get("tableName").toString();// 获取表名
				LinkedHashMap aliasValueMap = cellmap.get("aliasValueMap") == null ? null
						: (LinkedHashMap) cellmap.get("aliasValueMap");
				LinkedHashMap aliasTypeMap = cellmap.get("aliasTypeMap") == null ? null
						: (LinkedHashMap) cellmap.get("aliasTypeMap");
				LinkedHashMap whereMap = cellmap.get("whereMap") == null ? null
						: (LinkedHashMap) cellmap.get("whereMap");
				LinkedHashMap aliasValueI18nMap = cellmap
						.get("aliasValueI18nMap") == null ? null
						: (LinkedHashMap) cellmap.get("aliasValueI18nMap");

				// 派遣地发令导入数据
				LinkedHashMap aliasMapCells = new LinkedHashMap();
				if (aliasValueMap != null && aliasTypeMap != null) {// 存放CELL0：NUMBER类型的键值对
					Set keySet = aliasValueMap.keySet();
					if (keySet != null) {
						Iterator it = keySet.iterator();
						while (it.hasNext()) {
							Object keyvalue = it.next();
							if (keyvalue != null) {
								Object aliasValue = aliasValueMap.get(keyvalue);
								if (aliasValue != null) {
									aliasMapCells.put(aliasValue.toString(),
											aliasTypeMap.get(keyvalue));
								}
							}
						}
					}
				}
				List addList = new ArrayList();
				// 循环工作表的每行数据
				int init = 0;
				for (int i = 1; i < sheet.getRows(); i++) {
					Cell[] row = sheet.getRow(i);
					if (row[0] == null
							|| row[0].getContents().trim().equals("")) {
						continue;
					}
					if (init == 0) {
						init = sheet.getRow(0).length;
					}
					LinkedHashMap smap = new LinkedHashMap();
					LinkedHashMap aliasCellmap = new LinkedHashMap();
					for (int j = 0; j < init; j++) {
						try {
							String cellValue = "";
							Object cellType = aliasMapCells.get("#CELL" + j
									+ "#");
							// row[j]为空直接设为 "",预防取值的时候发生空指针异常
							String content = "";
							if (sheet.getRow(i).length > j) {
								content = StringUtil.checkNull(row[j]
										.getContents());
							}
							if (cellType != null && !content.equals("")) {// date类型
								try {
									cellValue = this.FormateData(row[j],
											cellType);
									if (cellValue.contains("/")) {
										String[] contentStr = cellValue
												.split("/");
										if (contentStr.length == 3
												&& contentStr[0].length() == 2
												&& contentStr[2].length() == 4) {
											cellValue = contentStr[2] + "/"
													+ contentStr[1] + "/"
													+ contentStr[0];
										}
									} else if (cellValue.contains("-")) {
										String[] contentStr = cellValue
												.split("-");
										if (contentStr.length == 3
												&& contentStr[0].length() == 2
												&& contentStr[2].length() == 4) {
											cellValue = contentStr[2] + "-"
													+ contentStr[1] + "-"
													+ contentStr[0];
										}
									}
								} catch (Exception e) {
									cellValue = "";
									if (!"".equals(content)) {
										cellValue = content;
										if (cellValue.contains("/")) {
											String[] contentStr = cellValue
													.split("/");
											if (contentStr.length == 3
													&& contentStr[0].length() == 2
													&& contentStr[2].length() == 4) {
												cellValue = contentStr[2] + "/"
														+ contentStr[1] + "/"
														+ contentStr[0];
											}
										} else if (cellValue.contains("-")) {
											String[] contentStr = cellValue
													.split("-");
											if (contentStr.length == 3
													&& contentStr[0].length() == 2
													&& contentStr[2].length() == 4) {
												cellValue = contentStr[2] + "-"
														+ contentStr[1] + "-"
														+ contentStr[0];
											}
										}
									}
								}
							} else {
								try {
									cellValue = content;
								} catch (Exception e) {
									cellValue = "";
								}
							}
							aliasCellmap.put("#CELL" + j + "#",
									cellValue == null ? "" : cellValue.trim());
						} catch (Exception e) {
							e.printStackTrace();
							// 表有空值或有空单元行，请检查！
							return token = 2;
						}
					}
					String aliasKeySql = "";
					String aliasValueSql = "";
					String whereSql = "";
					int initKey = 0;
					if (whereMap != null) {
						Set keySet = whereMap.keySet();
						if (keySet != null) {
							Iterator it = keySet.iterator();
							while (it.hasNext()) {
								initKey++;
								Object keyValue = it.next();
								if (keyValue != null && !keyValue.equals("")) {
									Object aliasCellmapValue = aliasCellmap
											.get(whereMap.get(keyValue));
									String sqlContent = null;
									if (aliasCellmapValue == null) {// 不属于excel的列
										aliasCellmapValue = whereMap
												.get(keyValue);
										if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("#CELL")) {
											sqlContent = aliasCellmapValue
													.toString();
											for (int l = 0; l < init; l++) {
												sqlContent = sqlContent
														.replaceAll(
																"#CELL" + l
																		+ "#",
																aliasCellmap
																		.get(
																				"#CELL"
																						+ l
																						+ "#")
																		.toString());
												if (!sqlContent
														.contains("#CELL")) {
													break;
												}
											}
										}
									}
									if ("".equals(whereSql)) {
										whereSql = " " + keyValue + " = ("
												+ sqlContent + " ) ";
									} else {
										whereSql += " AND " + keyValue + " = ("
												+ sqlContent + " ) ";
									}
								}
							}
						}
					}
					if (aliasValueMap != null && aliasTypeMap != null) {
						Set keySet = aliasValueMap.keySet();
						if (keySet != null) {
							Iterator it = keySet.iterator();
							while (it.hasNext()) {
								if ("".equals(aliasKeySql)) {
									initKey = 0;
								}
								initKey++;
								Object keyValue = it.next();
								if (keyValue != null && !keyValue.equals("")) {
									int fieldType = Integer
											.parseInt(aliasTypeMap
													.get(keyValue).toString());
									Object aliasCellmapValue = aliasCellmap
											.get(aliasValueMap.get(keyValue));
									if (aliasCellmapValue == null) {// 不属于excel的列
										aliasCellmapValue = aliasValueMap
												.get(keyValue);
										if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("#CELL")) {
											String sqlContent = aliasCellmapValue
													.toString();
											boolean isNull = false;
											for (int l = 0; l < init; l++) {
												if (aliasCellmapValue
														.toString().contains(
																"#CELL" + l
																		+ "#")) {
													String cellValue = aliasCellmap
															.get(
																	"#CELL"
																			+ l
																			+ "#")
															.toString();
													if ("".equals(cellValue)) {// 如果为空值，跳出本次循环,此列不更新
														isNull = true;
														break;
													} else {
														sqlContent = sqlContent
																.replaceAll(
																		"#CELL"
																				+ l
																				+ "#",
																		cellValue);
														if (!sqlContent
																.contains("#CELL")) {
															break;
														}
													}
												}
											}
											if (isNull) {// 如果为空值，跳出本次循环，不更新此列
												continue;
											}
											LinkedHashMap sqlI8nContentMap = new LinkedHashMap();
											switch (fieldType) {
											case 2:
												sqlI8nContentMap.put(
														"sqlI18nContent",
														sqlContent);
												aliasCellmapValue = this
														.getCodeNoByName(sqlI8nContentMap) == -1 ? ""
														: this
																.getCodeNoByName(sqlI8nContentMap);
												break;
											default:
												sqlI8nContentMap.put(
														"sqlContent",
														sqlContent);
												if (this
														.getContentNoByFiled(sqlI8nContentMap) != null) {
													Object cellObj = this
															.getContentNoByFiled(
																	sqlI8nContentMap)
															.size() == 0 ? ""
															: this
																	.getContentNoByFiled(
																			sqlI8nContentMap)
																	.get(0);
													if (cellObj != null
															&& !cellObj
																	.equals("")
															&& ((Map) cellObj)
																	.get(keyValue
																			.toString()) != null) {
														aliasCellmapValue = (cellObj == null ? ""
																: ((Map) cellObj)
																		.get(
																				keyValue
																						.toString()
																						.toUpperCase())
																		.toString());
													} else {
														aliasCellmapValue = "";
													}
												}

												break;
											}
										}
									} else {
										if ("".equals(aliasCellmapValue
												.toString())) {
											continue;
										}
										if (aliasValueI18nMap != null
												&& aliasValueI18nMap
														.get(keyValue) != null) {// 有需要从国际化表读取No的列
											String sqlContent = aliasValueI18nMap
													.get(keyValue) == null ? ""
													: aliasValueI18nMap.get(
															keyValue)
															.toString();
											sqlContent = sqlContent
													.replaceAll(
															aliasValueMap
																	.get(keyValue) == null ? ""
																	: aliasValueMap
																			.get(
																					keyValue)
																			.toString(),
															"'"
																	+ aliasCellmapValue
																			.toString()
																	+ "'");

											LinkedHashMap sqlI8nContentMap = new LinkedHashMap();
											sqlI8nContentMap.put(
													"sqlI18nContent",
													sqlContent);
											if (aliasValueI18nMap.get(keyValue
													+ "_FLAG") != null
													&& aliasValueI18nMap.get(
															keyValue + "_FLAG")
															.equals("Y")) {
												sqlI8nContentMap.put(
														"sqlContent",
														sqlContent);
												List contentList = this
														.getContentNoByFiled(sqlI8nContentMap);
												if (contentList.get(0) != null
														&& ((Map) (contentList
																.get(0)))
																.get("CPNY_ID") != null) {
													aliasCellmapValue = (contentList == null ? ""
															: ((Map) (contentList
																	.get(0)))
																	.get(
																			"CPNY_ID")
																	.toString());
												} else if (contentList.get(0) != null
														&& ((Map) (contentList
																.get(0)))
																.get("DEPTNO") != null) {
													aliasCellmapValue = (contentList == null ? ""
															: ((Map) (contentList
																	.get(0)))
																	.get(
																			"DEPTNO")
																	.toString());
												} else {
													aliasCellmapValue = "";
												}
											} else {
												aliasCellmapValue = this
														.getCodeNoByName(sqlI8nContentMap) == -1 ? ""
														: this
																.getCodeNoByName(sqlI8nContentMap);
											}
											if (aliasCellmapValue.equals("")) {
												System.out
														.println(aliasValueMap
																.get(keyValue)
																.toString()
																+ "-----------------------------------");
											}
										}
									}
									switch (fieldType) {
									case 0:
										// smap.put(keyValue.toString(),
										// "'"+aliasValueMap.get(keyValue)+"'");
										if (initKey == 1) {
											aliasKeySql = keyValue.toString();
											aliasValueSql = "'"
													+ aliasCellmapValue + "'";
										} else {
											aliasKeySql += ","
													+ keyValue.toString();
											aliasValueSql += ",'"
													+ aliasCellmapValue + "'";
										}
										break;
									case 1:
										// smap.put(keyValue.toString(),
										// "'"+aliasValueMap.get(keyValue)+"'");
										if (initKey == 1) {
											aliasKeySql = keyValue.toString();
											if (aliasCellmapValue != null
													&& aliasCellmapValue
															.toString()
															.contains("/")) {
												aliasValueSql = "to_date('"
														+ aliasCellmapValue
														+ "','YYYY/MM/DD HH24:MI')";// "'"+aliasCellmapValue+"'";
											} else if (aliasCellmapValue != null
													&& aliasCellmapValue
															.toString()
															.contains("-")) {
												aliasValueSql = "to_date('"
														+ aliasCellmapValue
														+ "','YYYY-MM-DD HH24:MI')";// "'"+aliasCellmapValue+"'";
											}
										} else {
											aliasKeySql += ","
													+ keyValue.toString();
											if (aliasCellmapValue != null
													&& aliasCellmapValue
															.toString()
															.contains("/")) {
												aliasValueSql += ",to_date('"
														+ aliasCellmapValue
														+ "','YYYY/MM/DD HH24:MI')";// "'"+aliasCellmapValue+"'";
											} else if (aliasCellmapValue != null
													&& aliasCellmapValue
															.toString()
															.contains("-")) {
												aliasValueSql += ",to_date('"
														+ aliasCellmapValue
														+ "','YYYY-MM-DD HH24:MI')";// "'"+aliasCellmapValue+"'";
											} else if (aliasCellmapValue == "") {
												aliasValueSql += ",'' ";
											}
										}
										break;
									case 11:
										if (initKey == 1) {
											aliasKeySql = keyValue.toString();
											if (aliasCellmapValue != null
													&& aliasCellmapValue
															.toString()
															.contains("/")) {
												aliasValueSql = "to_date('"
														+ aliasCellmapValue
														+ "','YYYY/MM/DD HH24:MI:SS')";// "'"+aliasCellmapValue+"'";
											} else if (aliasCellmapValue != null
													&& aliasCellmapValue
															.toString()
															.contains("-")) {
												aliasValueSql = "to_date('"
														+ aliasCellmapValue
														+ "','YYYY-MM-DD HH24:MI:SS')";// "'"+aliasCellmapValue+"'";
											}
										} else {
											aliasKeySql += ","
													+ keyValue.toString();
											if (aliasCellmapValue != null
													&& aliasCellmapValue
															.toString()
															.contains("/")) {
												aliasValueSql += ",to_date('"
														+ aliasCellmapValue
														+ "','YYYY/MM/DD HH24:MI:SS')";// "'"+aliasCellmapValue+"'";
											} else if (aliasCellmapValue != null
													&& aliasCellmapValue
															.toString()
															.contains("-")) {
												aliasValueSql += ",to_date('"
														+ aliasCellmapValue
														+ "','YYYY-MM-DD HH24:MI:SS')";
											}
										}
										break;
									case 12:
										if (initKey == 1) {
											aliasKeySql = keyValue.toString();
											if (aliasCellmapValue != null
													&& aliasCellmapValue
															.toString()
															.contains("/")) {
												aliasValueSql = "to_date('"
														+ aliasCellmapValue
														+ "','YYYY/MM/DD HH24:MI')";// "'"+aliasCellmapValue+"'";
											} else if (aliasCellmapValue != null
													&& aliasCellmapValue
															.toString()
															.contains("-")) {
												aliasValueSql = "to_date('"
														+ aliasCellmapValue
														+ "','YYYY-MM-DD HH24:MI')";// "'"+aliasCellmapValue+"'";
											}
										} else {
											aliasKeySql += ","
													+ keyValue.toString();
											if (aliasCellmapValue != null
													&& aliasCellmapValue
															.toString()
															.contains("/")) {
												aliasValueSql += ",to_date('"
														+ aliasCellmapValue
														+ "','YYYY/MM/DD HH24:MI')";// "'"+aliasCellmapValue+"'";
											} else if (aliasCellmapValue != null
													&& aliasCellmapValue
															.toString()
															.contains("-")) {
												aliasValueSql += ",to_date('"
														+ aliasCellmapValue
														+ "','YYYY-MM-DD HH24:MI')";
											}
										}
										break;
									default:
										if (initKey == 1) {
											aliasKeySql = keyValue.toString();
											aliasValueSql = (aliasCellmapValue == null ? "null"
													: aliasCellmapValue
															.toString());
										} else {
											aliasKeySql += ","
													+ keyValue.toString();
											aliasValueSql += ","
													+ (aliasCellmapValue == null ? "null"
															: aliasCellmapValue
																	.toString())
													+ "";
										}
										break;
									}
								}
							}
						}
					}
					smap.put("tableName", tableName);
					smap.put("aliasNameKeyContent", aliasKeySql);
					smap.put("aliasValueContent", "SELECT " + aliasValueSql
							+ " FROM DUAL ");
					smap.put("whereValueContent", whereSql);
					if ("".equals(aliasKeySql) || "".equals(aliasValueSql)) {
						continue;
					}
					addList.add(smap);
				}
				dataMap.put("addList", addList);
				this.updateImportData(dataMap);
				token = 1;
			}

		} catch (Exception e) { // 未找到文件异常
			e.printStackTrace();
			token = 0;
		}
		return token;
	}

	/**
	 * 派遣津贴标准导入
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param modelMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ModelMap importPaiQianDiJinTieBiaoZhunInfoData(
			HttpServletRequest request, HttpServletResponse response,
			LinkedHashMap map, ModelMap modelMap) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String adminID = admin.getAdminID() == null ? "USERNO_"
				+ admin.getUserNo() : admin.getAdminID();
		ObjectBindUtil.getRequestParamData(request);
		item_no = request.getParameter("id");
		type = request.getParameter("type");
		this.filename = adminID;
		this.pathCpnyID = admin.getCpnyId();
		this.language = admin.getLanguage();
		if (this.processUploadFile(request, response)) {
			// 开始导入派遣地信息
			int returnInt = this.importPaiQianDiJinTieBiaoZhunInfoData(request,
					adminID, map);
			if (returnInt == 0) {
				modelMap.put("sign", "0");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.importfail", request));// 导入失败!
			} else if (returnInt < 0) {
				modelMap.put("sign", "-1");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.leixingerror", request));// 类型有误，请按规则填写！
			} else if (returnInt == 2) {
				modelMap.put("sign", "2");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.existnull", request));// 表有空值或有空单元行，请检查！
			} else if (returnInt == 1) {
				modelMap.put("sign", "1");
				modelMap.put("statusCode", "200");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.importsuccess", request));// 导入成功
			} else if (returnInt == 7) {
				modelMap.put("sign", "3");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.deleteerror", request));// 删除历史数据出错,请联系管理员
			} else if (returnInt > 1000) {
				modelMap.put("sign", "3");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.title.di", request)
						+ String.valueOf(returnInt - 1000)
						+ TipMessage.getTipMessage(
								"ar.alert.message.excelimport.title.hang",
								request)
						+ TipMessage.getTipMessage(
								"ar.alert.message.excelimport.cardnoiserror",
								request));// 卡号有错误，请检查核对！
			}
			File file = new File(this.path + "\\" + filename + ".xls");
			file.delete();
		} else {
			modelMap.put("sign", "0");
			modelMap.put("statusCode", "300");
			modelMap.put("message", TipMessage.getTipMessage(
					"ar.alert.message.excelimport.importfail", request));// 导入失败!
		}
		return modelMap;
	}

	/**
	 * 分析excel的各个列，获取各个列的值，并且导入的数据库中 Description: 导入最低工资标准非促销员到临时表中
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param modelMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ModelMap importZuiDiGongZiBiaoZhunFeiCuXiaoYuanInfo(
			HttpServletRequest request, HttpServletResponse response,
			LinkedHashMap map, ModelMap modelMap) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String adminID = admin.getAdminID() == null ? "USERNO_"
				+ admin.getUserNo() : admin.getAdminID();
		ObjectBindUtil.getRequestParamData(request);
		this.filename = adminID;
		this.pathCpnyID = admin.getCpnyId();
		this.language = admin.getLanguage();
		if (this.processUploadFile(request, response)) {
			// 读取excel表的数据
			// ModelMap preparedMap = prepareForImportExcel(request, adminID,
			// map);
			ModelMap preparedMap = preparePaiQianDiForImportExcel(request,
					adminID, map);
			int returnInt = (Integer) preparedMap.get("token");
			// 如果excel数据读取成功，将数据导入 临时表
			if (returnInt == 1) {
				LinkedHashMap paramMap = new LinkedHashMap();
				paramMap.put("TABLE_NAME", map.get("tableName"));
				paramMap.put("PERSONID", admin.getPersonId());
				// 删除临时表中当前用户旧数据
				try {
					this.excelUtilDao
							.deleteZuiDiGongZiBiaoZhunFeiCuXiaoYuanImportTemp(paramMap);
				} catch (Exception e) {
					e.printStackTrace();
					returnInt = 0;
				}
				Map dataMap = new HashMap();
				dataMap = (Map) preparedMap.get("dataMap");
				// 为当前用户向临时表中插入数据
				try {
					this.excelUtilDao
							.insertZuiDiGongZiBiaoZhunFeiCuXiaoYuanExcelDataForTransferOrder(dataMap);
				} catch (Exception e) {
					e.printStackTrace();
					returnInt = 0;
				}
			}
			if (returnInt == 0) {
				modelMap.put("sign", "0");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.importfail", request));// 导入失败!
			} else if (returnInt < 0) {
				modelMap.put("sign", "-1");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.leixingerror", request));// 类型有误，请按规则填写！
			} else if (returnInt == 2) {
				modelMap.put("sign", "2");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.existnull", request));// 表有空值或有空单元行，请检查！
			} else if (returnInt == 1) {
				modelMap.put("sign", "1");
				modelMap.put("statusCode", "200");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.importsuccess", request));// 导入成功
				modelMap.put("navTabId", "pa0807");
				modelMap
						.put("forwardUrl",
								"/pa/salaryCanShu/viewZuiDiGongZiBiaoZhunFeiCuXiaoYuanExcelResultList");
			} else if (returnInt == 7) {
				modelMap.put("sign", "3");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.deleteerror", request));// 删除历史数据出错,请联系管理员
			} else if (returnInt > 1000) {
				modelMap.put("sign", "3");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.title.di", request)
						+ String.valueOf(returnInt - 1000)
						+ TipMessage.getTipMessage(
								"ar.alert.message.excelimport.title.hang",
								request)
						+ TipMessage.getTipMessage(
								"ar.alert.message.excelimport.cardnoiserror",
								request));// 卡号有错误，请检查核对！
			}
			File file = new File(this.path + "\\" + filename + ".xls");
			file.delete();
		} else {
			modelMap.put("sign", "0");
			modelMap.put("statusCode", "300");
			modelMap.put("message", TipMessage.getTipMessage(
					"ar.alert.message.excelimport.importfail", request));// 导入失败!
		}
		return modelMap;
	}

	@SuppressWarnings("unchecked")
	public ModelMap importZuiDiGongZiBiaoZhunCuXiaoYuanInfo(
			HttpServletRequest request, HttpServletResponse response,
			LinkedHashMap map, ModelMap modelMap) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String adminID = admin.getAdminID() == null ? "USERNO_"
				+ admin.getUserNo() : admin.getAdminID();
		ObjectBindUtil.getRequestParamData(request);
		this.filename = adminID;
		this.pathCpnyID = admin.getCpnyId();
		this.language = admin.getLanguage();
		if (this.processUploadFile(request, response)) {
			// 读取excel表的数据
			// ModelMap preparedMap = prepareForImportExcel(request, adminID,
			// map);
			ModelMap preparedMap = preparePaiQianDiForImportExcel(request,
					adminID, map);
			int returnInt = (Integer) preparedMap.get("token");
			// 如果excel数据读取成功，将数据导入 临时表
			if (returnInt == 1) {
				LinkedHashMap paramMap = new LinkedHashMap();
				paramMap.put("TABLE_NAME", map.get("tableName"));
				paramMap.put("PERSONID", admin.getPersonId());
				// 删除临时表中当前用户旧数据
				try {
					this.excelUtilDao
							.deleteZuiDiGongZiBiaoZhunFeiCuXiaoYuanImportTemp(paramMap);
				} catch (Exception e) {
					e.printStackTrace();
					returnInt = 0;
				}
				Map dataMap = new HashMap();
				dataMap = (Map) preparedMap.get("dataMap");
				// 为当前用户向临时表中插入数据
				try {
					this.excelUtilDao
							.insertZuiDiGongZiBiaoZhunFeiCuXiaoYuanExcelDataForTransferOrder(dataMap);
				} catch (Exception e) {
					e.printStackTrace();
					returnInt = 0;
				}
			}
			if (returnInt == 0) {
				modelMap.put("sign", "0");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.importfail", request));// 导入失败!
			} else if (returnInt < 0) {
				modelMap.put("sign", "-1");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.leixingerror", request));// 类型有误，请按规则填写！
			} else if (returnInt == 2) {
				modelMap.put("sign", "2");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.existnull", request));// 表有空值或有空单元行，请检查！
			} else if (returnInt == 1) {
				modelMap.put("sign", "1");
				modelMap.put("statusCode", "200");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.importsuccess", request));// 导入成功
				modelMap.put("navTabId", "pa0808");
				modelMap
						.put("forwardUrl",
								"/pa/salaryCanShu/viewZuiDiGongZiBiaoZhunCuXiaoYuanExcelResultList");
			} else if (returnInt == 7) {
				modelMap.put("sign", "3");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.deleteerror", request));// 删除历史数据出错,请联系管理员
			} else if (returnInt > 1000) {
				modelMap.put("sign", "3");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.title.di", request)
						+ String.valueOf(returnInt - 1000)
						+ TipMessage.getTipMessage(
								"ar.alert.message.excelimport.title.hang",
								request)
						+ TipMessage.getTipMessage(
								"ar.alert.message.excelimport.cardnoiserror",
								request));// 卡号有错误，请检查核对！
			}
			File file = new File(this.path + "\\" + filename + ".xls");
			file.delete();
		} else {
			modelMap.put("sign", "0");
			modelMap.put("statusCode", "300");
			modelMap.put("message", TipMessage.getTipMessage(
					"ar.alert.message.excelimport.importfail", request));// 导入失败!
		}
		return modelMap;
	}

	/**
	 * 分析excel的各个列，获取各个列的值，并且导入的数据库中 Description:
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param modelMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ModelMap importPaiQianDiInfoData(HttpServletRequest request,
			HttpServletResponse response, LinkedHashMap map, ModelMap modelMap) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String adminID = admin.getAdminID() == null ? "USERNO_"
				+ admin.getUserNo() : admin.getAdminID();
		ObjectBindUtil.getRequestParamData(request);
		this.filename = adminID;
		this.pathCpnyID = admin.getCpnyId();
		this.language = admin.getLanguage();
		if (this.processUploadFile(request, response)) {
			// 读取excel表的数据
			// ModelMap preparedMap = prepareForImportExcel(request, adminID,
			// map);
			ModelMap preparedMap = preparePaiQianDiForImportExcel(request,
					adminID, map);
			int returnInt = (Integer) preparedMap.get("token");
			// 如果excel数据读取成功，将数据导入临时表
			if (returnInt == 1) {
				LinkedHashMap paramMap = new LinkedHashMap();
				paramMap.put("TABLE_NAME", map.get("tableName"));
				paramMap.put("PERSONID", admin.getPersonId());
				// 删除临时表中当前用户旧数据
				try {
					// this.excelUtilDao.deleteImportTemp(paramMap);
					this.excelUtilDao.deletePaiQianDiImportTemp(paramMap);
				} catch (Exception e) {
					e.printStackTrace();
					returnInt = 0;
				}
				Map dataMap = new HashMap();
				dataMap = (Map) preparedMap.get("dataMap");
				// 为当前用户向临时表中插入数据
				try {
					// this.excelUtilDao.insertExcelDataForTransferOrder(dataMap);
					this.excelUtilDao
							.insertPaiQianDiExcelDataForTransferOrder(dataMap);
				} catch (Exception e) {
					e.printStackTrace();
					returnInt = 0;
				}
			}
			// 开始导入派遣地信息
			// int returnInt = this.importPaiQianDiInfoData(request, adminID,
			// map);
			if (returnInt == 0) {
				modelMap.put("sign", "0");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.importfail", request));// 导入失败!
			} else if (returnInt < 0) {
				modelMap.put("sign", "-1");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.leixingerror", request));// 类型有误，请按规则填写！
			} else if (returnInt == 2) {
				modelMap.put("sign", "2");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.existnull", request));// 表有空值或有空单元行，请检查！
			} else if (returnInt == 1) {
				modelMap.put("sign", "1");
				modelMap.put("statusCode", "200");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.importsuccess", request));// 导入成功
				modelMap.put("navTabId", "pa0801");
				modelMap.put("forwardUrl",
						"/pa/salaryCanShu/viewPaiQianDiGuanLiExcelResultList");
			} else if (returnInt == 7) {
				modelMap.put("sign", "3");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.deleteerror", request));// 删除历史数据出错,请联系管理员
			} else if (returnInt > 1000) {
				modelMap.put("sign", "3");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.title.di", request)
						+ String.valueOf(returnInt - 1000)
						+ TipMessage.getTipMessage(
								"ar.alert.message.excelimport.title.hang",
								request)
						+ TipMessage.getTipMessage(
								"ar.alert.message.excelimport.cardnoiserror",
								request));// 卡号有错误，请检查核对！
			}
			File file = new File(this.path + "\\" + filename + ".xls");
			file.delete();
		} else {
			modelMap.put("sign", "0");
			modelMap.put("statusCode", "300");
			modelMap.put("message", TipMessage.getTipMessage(
					"ar.alert.message.excelimport.importfail", request));// 导入失败!
		}
		return modelMap;
	}

	/**
	 * 分析excel的各个列，获取各个列的值，并且导入的数据库中 Description:
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param modelMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ModelMap importPaForLeftMenInfoData(HttpServletRequest request,
			HttpServletResponse response, LinkedHashMap map, ModelMap modelMap) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String adminID = admin.getAdminID() == null ? "USERNO_"
				+ admin.getUserNo() : admin.getAdminID();
		ObjectBindUtil.getRequestParamData(request);
		this.filename = adminID;
		this.pathCpnyID = admin.getCpnyId();
		this.language = admin.getLanguage();
		if (this.processUploadFile(request, response)) {
			// 读取excel表的数据
			// ModelMap preparedMap = prepareForImportExcel(request, adminID,
			// map);
			ModelMap preparedMap = preparePaiQianDiForImportExcel(request,
					adminID, map);
			int returnInt = (Integer) preparedMap.get("token");
			// 如果excel数据读取成功，将数据导入临时表
			if (returnInt == 1) {
				LinkedHashMap paramMap = new LinkedHashMap();
				paramMap.put("TABLE_NAME", map.get("tableName"));
				paramMap.put("PERSONID", admin.getPersonId());
				// 删除临时表中当前用户旧数据
				try {
					// this.excelUtilDao.deleteImportTemp(paramMap);
					this.excelUtilDao.deletePaiQianDiImportTemp(paramMap);
				} catch (Exception e) {
					e.printStackTrace();
					returnInt = 0;
				}
				Map dataMap = new HashMap();
				dataMap = (Map) preparedMap.get("dataMap");
				// 为当前用户向临时表中插入数据
				try {
					// this.excelUtilDao.insertExcelDataForTransferOrder(dataMap);
					this.excelUtilDao
							.insertPaiQianDiExcelDataForTransferOrder(dataMap);
				} catch (Exception e) {
					e.printStackTrace();
					returnInt = 0;
				}
			}
			// 开始导入派遣地信息
			// int returnInt = this.importPaiQianDiInfoData(request, adminID,
			// map);
			if (returnInt == 0) {
				modelMap.put("sign", "0");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.importfail", request));// 导入失败!
			} else if (returnInt < 0) {
				modelMap.put("sign", "-1");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.leixingerror", request));// 类型有误，请按规则填写！
			} else if (returnInt == 2) {
				modelMap.put("sign", "2");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.existnull", request));// 表有空值或有空单元行，请检查！
			} else if (returnInt == 1) {
				modelMap.put("sign", "1");
				modelMap.put("statusCode", "200");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.importsuccess", request));// 导入成功
				// modelMap.put("navTabId", "pa0801");
				// modelMap.put("forwardUrl",
				// "/pa/salaryCanShu/viewPaiQianDiGuanLiExcelResultList");
				modelMap.put("navTabId", "pa0708");
				// modelMap.put("forwardUrl",
				// "/pa/salary/viewPaForLeftMenImportList");
				modelMap
						.put("forwardUrl",
								"/pa/salary/viewAddPaInfoForEmpLeftList?pageNum=1&numPerPage=10");
			} else if (returnInt == 7) {
				modelMap.put("sign", "3");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.deleteerror", request));// 删除历史数据出错,请联系管理员
			} else if (returnInt > 1000) {
				modelMap.put("sign", "3");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.title.di", request)
						+ String.valueOf(returnInt - 1000)
						+ TipMessage.getTipMessage(
								"ar.alert.message.excelimport.title.hang",
								request)
						+ TipMessage.getTipMessage(
								"ar.alert.message.excelimport.cardnoiserror",
								request));// 卡号有错误，请检查核对！
			}
			File file = new File(this.path + "\\" + filename + ".xls");
			file.delete();
		} else {
			modelMap.put("sign", "0");
			modelMap.put("statusCode", "300");
			modelMap.put("message", TipMessage.getTipMessage(
					"ar.alert.message.excelimport.importfail", request));// 导入失败!
		}
		return modelMap;
	}

	/**
	 * 分析excel的各个列，获取各个列的值，并且导入的数据库中 Description:
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param modelMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ModelMap importPaForLeftMenInfoData2(HttpServletRequest request,
			HttpServletResponse response, LinkedHashMap map, ModelMap modelMap) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String adminID = admin.getAdminID() == null ? "USERNO_"
				+ admin.getUserNo() : admin.getAdminID();
		ObjectBindUtil.getRequestParamData(request);
		this.filename = adminID;
		this.pathCpnyID = admin.getCpnyId();
		this.language = admin.getLanguage();
		if (this.processUploadFile(request, response)) {
			// 读取excel表的数据
			ModelMap preparedMap = preparePaiQianDiForImportExcel(request,
					adminID, map);
			int returnInt = (Integer) preparedMap.get("token");
			// 如果excel数据读取成功，将数据导入临时表
			if (returnInt == 1) {
				LinkedHashMap paramMap = new LinkedHashMap();
				paramMap.put("TABLE_NAME", map.get("tableName"));
				paramMap.put("PERSONID", admin.getPersonId());
				// 删除临时表中当前用户旧数据
				try {
					this.excelUtilDao.deletePaForLeftMenImportTemp(paramMap);
				} catch (Exception e) {
					e.printStackTrace();
					returnInt = 0;
				}
				Map dataMap = new HashMap();
				dataMap = (Map) preparedMap.get("dataMap");
				// 为当前用户向临时表中插入数据
				try {
					this.excelUtilDao.insertPaForLeftMenImportTemp(dataMap);
				} catch (Exception e) {
					e.printStackTrace();
					returnInt = 0;
				}
			}
			if (returnInt == 0) {
				modelMap.put("sign", "0");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.importfail", request));// 导入失败!
			} else if (returnInt < 0) {
				modelMap.put("sign", "-1");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.leixingerror", request));// 类型有误，请按规则填写！
			} else if (returnInt == 2) {
				modelMap.put("sign", "2");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.existnull", request));// 表有空值或有空单元行，请检查！
			} else if (returnInt == 1) {
				modelMap.put("sign", "1");
				modelMap.put("statusCode", "200");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.importsuccess", request));// 导入成功
				modelMap.put("navTabId", "pa0708");
				modelMap.put("forwardUrl",
						"/pa/salary/viewPaForLeftMenImportList");
			} else if (returnInt == 7) {
				modelMap.put("sign", "3");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.deleteerror", request));// 删除历史数据出错,请联系管理员
			} else if (returnInt > 1000) {
				modelMap.put("sign", "3");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.title.di", request)
						+ String.valueOf(returnInt - 1000)
						+ TipMessage.getTipMessage(
								"ar.alert.message.excelimport.title.hang",
								request)
						+ TipMessage.getTipMessage(
								"ar.alert.message.excelimport.cardnoiserror",
								request));// 卡号有错误，请检查核对！
			}
			File file = new File(this.path + "\\" + filename + ".xls");
			file.delete();
		} else {
			modelMap.put("sign", "0");
			modelMap.put("statusCode", "300");
			modelMap.put("message", TipMessage.getTipMessage(
					"ar.alert.message.excelimport.importfail", request));// 导入失败!
		}
		return modelMap;
	}

	/**
	 * 分析excel的各个列，获取各个列的值，并且导入的数据库中 Description:
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param modelMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ModelMap importPaiQianDiJinTieBiaoZhunTempInfoData(
			HttpServletRequest request, HttpServletResponse response,
			LinkedHashMap map, ModelMap modelMap) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String adminID = admin.getAdminID() == null ? "USERNO_"
				+ admin.getUserNo() : admin.getAdminID();
		ObjectBindUtil.getRequestParamData(request);
		this.filename = adminID;
		this.pathCpnyID = admin.getCpnyId();
		this.language = admin.getLanguage();
		if (this.processUploadFile(request, response)) {
			// 读取excel表的数据
			ModelMap preparedMap = preparePaiQianDiForImportExcel(request,
					adminID, map);
			int returnInt = (Integer) preparedMap.get("token");
			// 如果excel数据读取成功，将数据导入 临时表
			if (returnInt == 1) {
				LinkedHashMap paramMap = new LinkedHashMap();
				paramMap.put("TABLE_NAME", map.get("tableName"));
				paramMap.put("PERSONID", admin.getPersonId());
				// 删除临时表中当前用户旧数据
				try {
					this.excelUtilDao.deletePaiQianDiImportTemp(paramMap);
				} catch (Exception e) {
					e.printStackTrace();
					returnInt = 0;
				}
				Map dataMap = new HashMap();
				dataMap = (Map) preparedMap.get("dataMap");
				// 为当前用户向临时表中插入数据
				try {
					this.excelUtilDao
							.insertPaiQianDiExcelDataForTransferOrder(dataMap);
				} catch (Exception e) {
					e.printStackTrace();
					returnInt = 0;
				}
			}
			// //开始导入派遣地信息
			// int returnInt = this.importPaiQianDiInfoData(request, adminID,
			// map);

			if (returnInt == 0) {
				modelMap.put("sign", "0");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.importfail", request));// 导入失败!
			} else if (returnInt < 0) {
				modelMap.put("sign", "-1");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.leixingerror", request));// 类型有误，请按规则填写！
			} else if (returnInt == 2) {
				modelMap.put("sign", "2");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.existnull", request));// 表有空值或有空单元行，请检查！
			} else if (returnInt == 1) {
				modelMap.put("sign", "1");
				modelMap.put("statusCode", "200");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.importsuccess", request));// 导入成功
				modelMap.put("navTabId", "pa0802");
				modelMap
						.put("forwardUrl",
								"/pa/salaryCanShu/viewPaiQianDiJinTieBiaoZhunExcelResultList");
			} else if (returnInt == 7) {
				modelMap.put("sign", "3");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.deleteerror", request));// 删除历史数据出错,请联系管理员
			} else if (returnInt > 1000) {
				modelMap.put("sign", "3");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.title.di", request)
						+ String.valueOf(returnInt - 1000)
						+ TipMessage.getTipMessage(
								"ar.alert.message.excelimport.title.hang",
								request)
						+ TipMessage.getTipMessage(
								"ar.alert.message.excelimport.cardnoiserror",
								request));// 卡号有错误，请检查核对！
			}
			File file = new File(this.path + "\\" + filename + ".xls");
			file.delete();
		} else {
			modelMap.put("sign", "0");
			modelMap.put("statusCode", "300");
			modelMap.put("message", TipMessage.getTipMessage(
					"ar.alert.message.excelimport.importfail", request));// 导入失败!
		}
		return modelMap;
	}

	/**
	 * 分析excel的各个列，获取各个列的值，并且导入的数据库中 Description:
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param modelMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ModelMap importPaiQianDiInfoData(HttpServletRequest request,
			HttpServletResponse response, LinkedHashMap map, ModelMap modelMap,
			String checkResult) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String adminID = admin.getAdminID() == null ? "USERNO_"
				+ admin.getUserNo() : admin.getAdminID();
		ObjectBindUtil.getRequestParamData(request);
		this.filename = adminID;
		this.pathCpnyID = admin.getCpnyId();
		this.language = admin.getLanguage();
		if (this.processUploadFile(request, response)) {
			// 开始导入派遣地信息
			int returnInt = this.importPaiQianDiInfoData(request, adminID, map,
					checkResult);
			// int returnInt = this.importPaiQianDiInfoData(request, adminID,
			// map);

			if (returnInt == 0) {
				modelMap.put("sign", "0");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.importfail", request));// 导入失败!
			} else if (returnInt < 0) {
				modelMap.put("sign", "-1");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.leixingerror", request));// 类型有误，请按规则填写！
			} else if (returnInt == 2) {
				modelMap.put("sign", "2");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.existnull", request));// 表有空值或有空单元行，请检查！
			} else if (returnInt == 1) {
				modelMap.put("sign", "1");
				modelMap.put("statusCode", "200");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.importsuccess", request));// 导入成功
				modelMap.put("navTabId", "pa0801");
				modelMap.put("forwardUrl",
						"/pa/salaryCanShu/viewPaiQianDiGuanLiExcelResultList");// 转到临时表导入结果的页面
			} else if (returnInt == 7) {
				modelMap.put("sign", "3");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.deleteerror", request));// 删除历史数据出错,请联系管理员
			} else if (returnInt > 1000) {
				modelMap.put("sign", "3");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.title.di", request)
						+ String.valueOf(returnInt - 1000)
						+ TipMessage.getTipMessage(
								"ar.alert.message.excelimport.title.hang",
								request)
						+ TipMessage.getTipMessage(
								"ar.alert.message.excelimport.cardnoiserror",
								request));// 卡号有错误，请检查核对！
			}
			File file = new File(this.path + "\\" + filename + ".xls");
			file.delete();
		} else {
			modelMap.put("sign", "0");
			modelMap.put("statusCode", "300");
			modelMap.put("message", TipMessage.getTipMessage(
					"ar.alert.message.excelimport.importfail", request));// 导入失败!
		}
		return modelMap;
	}

	/**
	 * 辅助信息批量导入验证 每个单元格的值，为空的不验证
	 * 
	 * @param row
	 * @param columns
	 * @param nullStr
	 * @param list
	 * @param list1
	 * @param admin
	 * @author weizhengchen
	 * @return
	 */
	private ModelMap validateExcelTemplate(HttpServletRequest request,
			HttpServletResponse response, List list, List list1, int num,
			String aliasNullStr, ModelMap modelMap) {
		// 记录行列错误
		String resutlStr = "";
		int token = 0;
		int k = path.indexOf("\\");
		String server = "win";
		if (k < 0) { // WebServer的os为unix/linux
			server = "u/l";
		}
		File file = null;
		if ("win".equals(server)) {
			file = new File(path + "\\" + filename + ".xls");
		} else {
			file = new File(path + "/" + filename + ".xls");
		}
		Map dataMap = new HashMap();
		try {
			Workbook workBook = Workbook.getWorkbook(file);
			// 获得第一个工作表对象
			Sheet sheet = workBook.getSheet(0);
			// 验证是否存在导入数据
			if (sheet.getRows() < 2) {
				logger.debug("Import file not exist data, return.");
				modelMap.put("sign", "2");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.existnull", request));// 表有空值或有空单元行，请检查！
			}

			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			for (int i = 1; i < sheet.getRows(); i++) {
				String result = validateCell2(sheet.getRow(i),
						sheet.getRow(i).length, aliasNullStr, list, list1,
						admin);
				// 判断当前i行是否有错误，如果有记录
				if (result != null && result != "") {
					Cell[] row = sheet.getRow(i);
					String empid = row[num].getContents();
					String resutlcul = "社号:" + empid + ";         第" + i + "行，"
							+ result + "<br>";
					resutlStr += resutlcul + "\\n";
				}
			}
			if ("".equals(resutlStr)) {
				modelMap.put("statusCode", "200");
			} else {
				modelMap.put("sign", "0");
				modelMap.put("statusCode", "300");
				modelMap.put("message", resutlStr);// 导入失败!
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return modelMap;
	}

	/**
	 * 辅助信息批量导入验证 每个单元格的值，为空的不验证
	 * 
	 * @param row
	 * @param columns
	 * @param nullStr
	 * @param list
	 * @param list1
	 * @param admin
	 * @author weizhengchen
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private String validateCell2(Cell[] row, int columns, String nullStr,
			List list, List list1, AdminBean admin) {
		String result = "";
		boolean isDate;

		for (int i = 0; i < columns; i++) {
			String name = list1.get(i).toString();
			String names[] = name.split(",");
			// 如果当前行有空的单元格返回false
			int ii = nullStr.indexOf("," + i + ",");
			if (nullStr.indexOf("," + i + ",") < 0) {
				Object cellsContent = StringUtil
						.checkNull(row[i].getContents());
				String content = "";
				if (cellsContent != null) {
					content = cellsContent.toString().replaceAll(" ", "");
				}
				if (content != null && !"".equals(content)) {
					// 日期类型
					if (names[1].toString().equals("0")) {
						isDate = isValidDate2(cellsContent.toString());
						if (isDate == false) {
							result += names[0].toString()
									+ ":日期格式有错误。正确格式应为(yyyy/MM/dd).\n";
						}
					} else if (names[1].toString().equals("1")) {
						LinkedHashMap sql = new LinkedHashMap();
						String sqlI18nContent = "";
						if (names[2].equals("0")) {
							sqlI18nContent = "SELECT COUNT(*) FROM "
									+ names[3].toString() + "  WHERE CPNY_ID='"
									+ admin.getCpnyId() + "' and EMPID='"
									+ cellsContent + "'";
						} else if (names[2].equals("1")) {
							sqlI18nContent = "SELECT COUNT(*) FROM SY_CODE SC,SY_GLOBAL_NAME SY WHERE SC.PARENT_CODE_NO="
									+ names[3].toString()
									+ " AND SC.CODE_NO=SY.NO AND SY.LANGUAGE='"
									+ admin.getLanguage()
									+ "' AND SY.CONTENT='" + cellsContent + "'";
						}
						sql.put("sqlI18nContent", sqlI18nContent);
						int count = this.getCodeNoByName(sql);
						if (count == 0) {
							result = result + names[0] + ":值 " + cellsContent
									+ "不存在.";
						}
					}
				}
			}
		}
		return result;
	}

	/**
	 * 导出数据到excel Description:export datas to excel 标题上可以加Tip说明
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings( { "unchecked", "deprecation" })
	public void exportExcelByNameWithHeaderTip(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap,
			LinkedHashMap sqlContentmap, List aliasNameList, List aliasList,
			String name, List tipList) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String adminID = admin.getAdminID() == null ? "USERNO_"
				+ admin.getUserNo() : admin.getAdminID();
		this.type = request.getParameter("type");
		String CpnyId = admin.getCpnyId();
		this.path = request.getRealPath("");

		int init = path.indexOf("\\");
		String server = "win";
		if (init < 0) { // WebServer的os为unix/linux
			server = "u/l";
		}
		if ("win".equals(server)) {
			path = path + "\\resources\\temp\\download\\" + CpnyId + "\\"
					+ adminID + "\\";
		} else {
			path = path + "/resources/temp/download/" + CpnyId + "/" + adminID;
		}
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet1 = wb.createSheet("sheet1");
		for (int i = 0; i < 35; i++) {
			sheet1.setColumnWidth(i, 3800);
		}
		XSSFRow row = sheet1.createRow(0);
		XSSFCell cell;
		// 创建绘图对象
		XSSFDrawing p = sheet1.createDrawingPatriarch();

		List result = null;
		try {
			/**
			 * 根据导出报表的类型，定义列名，并且从相应的表中查询数据
			 */
			// if(this.type!=null&&this.type.equals("InsuranceInputItemData")){
			row = sheet1.createRow(0);
			for (int i = 0; i < aliasNameList.size(); i++) {
				String columnName = aliasNameList.get(i).toString();
				cell = row.createCell((short) i);
				cell.setCellValue(columnName);
				for (int j = 0; j < tipList.size(); j++) {
					String tipColumnName = ((Map) tipList.get(j)).get(
							"TIP_COLUMN").toString();
					if (tipColumnName.equals(columnName)) {
						// 前四个参数是坐标点,后四个参数是编辑和显示批注时的大小.
						XSSFComment comment = p
								.createCellComment(new XSSFClientAnchor(0, 0, 0, 0,
										(short) 4, 4, (short) 6, 26));
						// 输入批注信息
						comment.setString(new XSSFRichTextString(((Map) tipList
								.get(j)).get("TIP_CONTENT").toString()));
						cell.setCellComment(comment);
					}
				}
			}
			if (sqlContentmap != null
					&& sqlContentmap.get("sqlContent") != null
					&& !sqlContentmap.get("sqlContent").equals("")) {
				result = this.getExcelExportDataList(sqlContentmap);
			}
			// }
			File file = new File(this.path + "\\temp_" + name + ".xlsx");
			file.delete();
			if (result != null && result.size() > 0) {// 导出数据
				for (int i = 0; i < result.size(); i++) {
					int k = 0;
					row = sheet1.createRow(i + 1);
					for (int j = 0; j < aliasList.size(); j++) {
						cell = row.createCell((short) k++);
						cell
								.setCellValue(((Map) (result.get(i)))
										.get(aliasList.get(j).toString()) == null ? ""
										: ((Map) (result.get(i))).get(
												aliasList.get(j).toString())
												.toString());
					}
				}
			} else {// 下载导入模板
				List contentList = sqlContentmap.get("contentList") == null ? null
						: (List) sqlContentmap.get("contentList");
				if (contentList != null && contentList.size() > 0) {
					for (int i = 0; i < contentList.size(); i++) {
						int k = 0;
						row = sheet1.createRow(i + 1);
						Set keySet = ((Map) (contentList.get(i))).keySet();
						if (keySet != null) {
							Iterator it = keySet.iterator();
							while (it.hasNext()) {
								Object itValue = it.next();
								if (itValue != null) {
									cell = row.createCell((short) k++);
									cell.setCellValue(((Map) (contentList
											.get(i))).get(itValue).toString());
								}
							}
						}
					}
				}
			}
			File f = new File(this.path);
			if (!f.exists()) {
				f.mkdirs();
			}
			FileOutputStream fos = new FileOutputStream(new File(this.path
					+ "\\temp_" + name + ".xlsx"));
			wb.write(fos);
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.setContentType(CONTENT_TYPE);

		File file = new File(this.path + "\\temp_" + name + ".xlsx");
		response.setContentType("application/x-msdownload");
		response.setContentLength((int) file.length());
		response.setHeader("Content-Disposition", "attachment;filename="
				+ "\\temp_" + name + ".xlsx");
		FileInputStream fis = new FileInputStream(file);
		BufferedInputStream buff = new BufferedInputStream(fis);

		byte[] b = new byte[2048];
		long k = 0;
		OutputStream myout = response.getOutputStream();
		while (k < file.length()) {
			int j = buff.read(b, 0, 2048);
			k += j;
			myout.write(b, 0, j);
		}
		myout.flush();
	}

	/**
	 * 将excel的数据导入到数据库中
	 * 
	 * @Description: CHRS2.0 SI PROJECT
	 * @author weizhengchen
	 * @date 2014.07.09
	 * @version V1.0
	 */
	@SuppressWarnings("unchecked")
	public ModelMap importExcelData2(HttpServletRequest request,
			HttpServletResponse response, LinkedHashMap map, ModelMap modelMap,
			String forwardUrl, String navTabId) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String adminID = admin.getAdminID() == null ? "USERNO_"
				+ admin.getUserNo() : admin.getAdminID();
		ObjectBindUtil.getRequestParamData(request);
		this.filename = adminID;
		this.pathCpnyID = admin.getCpnyId();
		this.language = admin.getLanguage();
		if (this.processUploadFile(request, response)) {
			ModelMap preparedMap = prepareForImportExcel(request, adminID, map);
			int returnInt = (Integer) preparedMap.get("token");
			// 如果excel数据读取成功，将数据导入 临时表
			if (returnInt == 1) {
				LinkedHashMap paramMap = new LinkedHashMap();
				paramMap.put("TABLE_NAME", map.get("tableName"));
				Map dataMap01 = new HashMap();
				dataMap01 = (Map) preparedMap.get("dataMap");
				List addList = new ArrayList();
				addList = (List) dataMap01.get("addList");
				for (Iterator iterator = addList.iterator(); iterator.hasNext();) {
					Map eachMap = new HashMap();
					eachMap = (Map) iterator.next();
					String aliasValueContent = (String) eachMap
							.get("aliasValueContent");
					String[] strs = aliasValueContent.split(",");
					for (int i = 0; i < strs.length; i++) {
						String PERSON_ID = strs[0].replace("'", "");
						String START_DATE = strs[2].replace("'", "");
						String END_DATE = strs[3].replace("'", "");
						String ACTIVITY = strs[4].replace("'", "");
						paramMap.put("PERSON_ID", PERSON_ID);
						paramMap.put("START_DATE", START_DATE);
						paramMap.put("END_DATE", END_DATE);
						paramMap.put("ACTIVITY", ACTIVITY);
						// 修改存在数据状态
						try {
							this.excelUtilDao.updateImportTemp(paramMap);
						} catch (Exception e) {
							e.printStackTrace();
							returnInt = 0;
						}
					}

				}

				Map dataMap = new HashMap();
				dataMap = (Map) preparedMap.get("dataMap");
				// 为当前用户向临时表中插入数据
				try {
					this.excelUtilDao.insertExcelDataForTransferOrder(dataMap);
				} catch (Exception e) {
					e.printStackTrace();
					returnInt = 0;
				}
			}
			if (returnInt == 0) {
				modelMap.put("sign", "0");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.importfail", request));// 导入失败!
			} else if (returnInt < 0) {
				modelMap.put("sign", "-1");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.writebyrule", request));// 调整事由有误，请按规则填写！
			} else if (returnInt == 2) {
				modelMap.put("sign", "2");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.existnull", request));// 表有空值或有空单元行，请检查！
			} else if (returnInt == 1) {
				modelMap.put("sign", "1");
				modelMap.put("statusCode", "200");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.importsuccess", request));// 导入成功
				modelMap.put("navTabId", navTabId);
				modelMap.put("forwardUrl", forwardUrl);
			} else if (returnInt == 5) {
				modelMap.put("sign", "-1");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.importfailcalladmin",
						request));// 更新此人上一条数据出错，请联系管理员
			} else if (returnInt == 6) {
				modelMap.put("sign", "-1");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"alert.message.pa.bonus.thisDataIsExist_add_fail",
						request));// 此数据已经存在，请核对数据后再进行导入!
			}
			File file = new File(this.path + "\\" + filename + ".xls");
			file.delete();
		} else {
			modelMap.put("sign", "0");
			modelMap.put("statusCode", "300");
			modelMap.put("message", TipMessage.getTipMessage(
					"ar.alert.message.excelimport.importfail", request));// 导入失败!
		}
		return modelMap;
	}

	/**
	 * 将excel sales incentive 数据导入DB:
	 * 
	 * @Description: CHRS2.0 SI PROJECT
	 * @author penghaixia
	 * @date 2014.6.10
	 * @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ModelMap importExcelSalesInctData(HttpServletRequest request,
			HttpServletResponse response, LinkedHashMap map, ModelMap modelMap)
			throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String adminID = admin.getAdminID() == null ? "USERNO_"
				+ admin.getUserNo() : admin.getAdminID();
		Map reqMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		this.filename = adminID;
		this.pathCpnyID = admin.getCpnyId();
		this.language = admin.getLanguage();
		if (this.processUploadFile(request, response)) {
			ModelMap preparedMap = prepareForImportExcel(request, adminID, map);
			int returnInt = (Integer) preparedMap.get("token");
			if (returnInt == 1) {
				// 如果excel数据读取成功，将数据导入 临时表
				LinkedHashMap paramMap = new LinkedHashMap();
				paramMap.put("SUBSD_CD", admin.getCpnyId());
				paramMap.put("IMP_EMPNO", admin.getEmpID());
				paramMap.put("IMP_DATE", DateUtil.getSysdateStr().replace("-",
						""));
				try {
					// 删除临时表中当前用户旧数据
					this.excelUtilDao.deleteImportSalesInctTemp(paramMap);
					Map dataMap = new HashMap();
					dataMap = (Map) preparedMap.get("dataMap");
					// 为当前用户向临时表中插入数据
					this.excelUtilDao.insertExcelDataForTransferOrder(dataMap);
				} catch (Exception e) {
					e.printStackTrace();
					returnInt = 4;
				}
			}
			if (returnInt == 1) {
				modelMap.put("sign", "1");
				modelMap.put("statusCode", "200");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.importsuccess", request));// 导入成功
				if (reqMap.get("ACCRUAL_YN").equals("N")) {
					modelMap.put("navTabId", "se0201"); // 提成
				} else {
					modelMap.put("navTabId", "se0202"); // 预提
				}
				String fUrl = "/inct/salesman/viewIncentiveCalcImpList?pageNum=1";
				modelMap.put("forwardUrl", fUrl);
			} else if (returnInt == 2) {
				modelMap.put("sign", "2");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"inct.message.excelimport.nullRow", request));// 导入数据中有空行，请检查！
			} else if (returnInt == 3) {
				modelMap.put("sign", "3");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"inct.message.excelimport.requiredCol", request));// 导入数据中*项不可以为空，请检查！
			} else if (returnInt == 4) {
				modelMap.put("sign", "4");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage
						.getTipMessage(
								"inct.message.excelimport.writeTempTable.Fail",
								request));// 写入临时表失败！
			} else if (returnInt == 0) {
				modelMap.put("sign", "0");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.importfail", request));// 导入失败!
			}
			File file = new File(this.path + "\\" + filename + ".xls");
			file.delete();
		} else {
			modelMap.put("sign", "0");
			modelMap.put("statusCode", "300");
			modelMap.put("message", TipMessage.getTipMessage(
					"ar.alert.message.excelimport.importfail", request));// 导入失败!
		}
		return modelMap;
	}

	/**
	 * 将excel的数据导入到数据库中（教育实绩数据导入 临时表）
	 * 
	 * @Description: CHRS2.0 SI PROJECT
	 * @author lihuihua
	 * @date 2014.07.10
	 * @version V1.0
	 */
	@SuppressWarnings("unchecked")
	public ModelMap importExcelPromotoGradeData(HttpServletRequest request,
			HttpServletResponse response, LinkedHashMap map, ModelMap modelMap) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String adminID = admin.getAdminID() == null ? "USERNO_"
				+ admin.getUserNo() : admin.getAdminID();
		ObjectBindUtil.getRequestParamData(request);
		this.filename = adminID;
		this.pathCpnyID = admin.getCpnyId();
		this.language = admin.getLanguage();

		if (this.processUploadFile(request, response)) {
			// excel数据读取
			ModelMap preparedMap = prepareForImportExcel(request, adminID, map);
			int returnInt = (Integer) preparedMap.get("token");
			if (returnInt == 1) {
				// excel数据读取成功，将数据导入 临时表
				LinkedHashMap paramMap = new LinkedHashMap();
				paramMap.put("UPDT_USER", admin.getEmpID());
				try {
					// 删除临时表中当前用户旧数据
					this.excelUtilDao.deleteImportPromotoGradeTemp(paramMap);
					Map dataMap = new HashMap();
					dataMap = (Map) preparedMap.get("dataMap");
					// 为当前用户向临时表中插入数据
					this.excelUtilDao.insertExcelDataForTransferOrder(dataMap);
				} catch (Exception e) {
					e.printStackTrace();
					returnInt = 4;
				}
			}
			if (returnInt == 1) {
				modelMap.put("sign", "1");
				modelMap.put("statusCode", "200");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.importsuccess", request));// 导入成功
				modelMap.put("navTabId", "jy0400");
				modelMap.put("forwardUrl",
						"/empsubject/viewPromotoGradeResultList?pageNum=1");
			} else if (returnInt == 2) {
				modelMap.put("sign", "2");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"inct.message.excelimport.nullRow", request));// 导入数据中有空行，请检查！
			} else if (returnInt == 3) {
				modelMap.put("sign", "3");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"inct.message.excelimport.requiredCol", request));// 导入数据中*项不可以为空，请检查！
			} else if (returnInt == 4) {
				modelMap.put("sign", "4");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage
						.getTipMessage(
								"inct.message.excelimport.writeTempTable.Fail",
								request));// 写入临时表失败！
			} else if (returnInt == 0) {
				modelMap.put("sign", "0");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.importfail", request));// 导入失败!
			}
			File file = new File(this.path + "\\" + filename + ".xls");
			file.delete();
		} else {
			modelMap.put("sign", "0");
			modelMap.put("statusCode", "300");
			modelMap.put("message", TipMessage.getTipMessage(
					"ar.alert.message.excelimport.importfail", request));// 导入失败!
		}
		return modelMap;
	}

	public void setFileName(String filename) {
		this.filename = filename;
	}

	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * 导出数据到excel Description:export datas to excel
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings( { "unchecked", "deprecation" })
	public void exportExcelTwoSheetByLeave(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap,
			LinkedHashMap sqlContentmap, List aliasNameList, List aliasList,
			List sheet2List, String name) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String adminID = admin.getAdminID() == null ? "USERNO_"
				+ admin.getUserNo() : admin.getAdminID();
		this.type = request.getParameter("type");
		String CpnyId = admin.getCpnyId();
		this.path = request.getRealPath("");

		int init = path.indexOf("\\");
		String server = "win";
		if (init < 0) { // WebServer的os为unix/linux
			server = "u/l";
		}
		if ("win".equals(server)) {
			path = path + "\\resources\\temp\\download\\" + CpnyId + "\\"
					+ adminID + "\\";
		} else {
			path = path + "/resources/temp/download/" + CpnyId + "/" + adminID;
		}
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet1 = wb.createSheet("sheet1");
		XSSFRow row = sheet1.createRow(0);
		XSSFCell cell;

		XSSFSheet sheet2 = wb.createSheet("sheet2");
		// XSSFRow row2 = sheet1.createRow(0);
		// XSSFCell cell2;

		List result = null;
		try {
			/**
			 * 根据导出报表的类型，定义列名，并且从相应的表中查询数据
			 */
			// if(this.type!=null&&this.type.equals("InsuranceInputItemData")){
			row = sheet1.createRow(0);
			for (int i = 0; i < aliasNameList.size(); i++) {
				cell = row.createCell((short) i);
				cell.setCellValue(aliasNameList.get(i).toString());
			}
			if (sqlContentmap != null
					&& sqlContentmap.get("sqlContent") != null
					&& !sqlContentmap.get("sqlContent").equals("")) {
				result = this.getExcelExportDataList(sqlContentmap);
			}
			// }
			File file = new File(this.path + "\\temp_" + name + ".xlsx");
			file.delete();

			if (result != null && result.size() > 0) {// 导出数据
				for (int i = 0; i < result.size(); i++) {
					int k = 0;
					row = sheet1.createRow(i + 1);
					for (int j = 0; j < aliasList.size(); j++) {
						cell = row.createCell((short) k++);
						cell
								.setCellValue(((Map) (result.get(i)))
										.get(aliasList.get(j).toString()) == null ? ""
										: ((Map) (result.get(i))).get(
												aliasList.get(j).toString())
												.toString());
					}
				}
			} else {// 下载导入模板
				List contentList = sqlContentmap.get("contentList") == null ? null
						: (List) sqlContentmap.get("contentList");
				if (contentList != null && contentList.size() > 0) {
					for (int i = 0; i < contentList.size(); i++) {
						int k = 0;
						row = sheet1.createRow(i + 1);
						Set keySet = ((Map) (contentList.get(i))).keySet();
						if (keySet != null) {
							Iterator it = keySet.iterator();
							while (it.hasNext()) {
								Object itValue = it.next();
								if (itValue != null) {
									cell = row.createCell((short) k++);
									cell.setCellValue(((Map) (contentList
											.get(i))).get(itValue).toString());
								}
							}
						}
					}
				}
			}
			// row2 = sheet2.createRow(0);
			if (sheet2List != null && sheet2List.size() > 0) {// 设定sheet2的列头
				XSSFRow row2 = sheet2.createRow(0);
				XSSFCell cell2 = row2.createCell((short) 0);
				cell2.setCellValue((sheet2List.get(sheet2List.size() - 1))
						.toString());
			}
			if (sheet2List != null) {
				for (int i = 0; i < sheet2List.size() - 1; i++) {// 设定sheet2里头的内容
					XSSFRow row2 = sheet2.createRow(i + 1);
					XSSFCell cell2 = row2.createCell((short) 0);
					if (sheet2List.get(i) != null
							&& ((Map) (sheet2List.get(i))).get("CONTENT") != null) {
						cell2.setCellValue(((Map) (sheet2List.get(i))).get(
								"CONTENT").toString());
					} else {
						cell2.setCellValue("");
					}
				}
			}
			File f = new File(this.path);
			if (!f.exists()) {
				f.mkdirs();
			}
			FileOutputStream fos = new FileOutputStream(new File(this.path
					+ "\\temp_" + name + ".xlsx"));
			wb.write(fos);
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.setContentType(CONTENT_TYPE);

		File file = new File(this.path + "\\temp_" + name + ".xlsx");
		response.setContentType("application/x-msdownload");
		response.setContentLength((int) file.length());
		response.setHeader("Content-Disposition", "attachment;filename="
				+ "\\temp_" + name + ".xlsx");
		FileInputStream fis = new FileInputStream(file);
		BufferedInputStream buff = new BufferedInputStream(fis);

		byte[] b = new byte[2048];
		long k = 0;
		OutputStream myout = response.getOutputStream();
		while (k < file.length()) {
			int j = buff.read(b, 0, 2048);
			k += j;
			myout.write(b, 0, j);
		}
		myout.flush();
	}

	@Override
	public List getExcelExportTypeListByLeave(Map paramMap) throws Exception {

		return basicMaintenanceDao.getParamCodeCombinListByCpnyID(paramMap, -1,
				-1);
	}

	/**
	 * 导出数据到excel并加密
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings( { "unchecked", "deprecation" })
	public void exportExcelByNamePwd(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap,
			List aliasValueList, List aliasNameList, String[] columns,
			String name, Map paramMap) throws Exception {
		ReportUtil rUtil = new ReportUtil();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String adminID = admin.getAdminID() == null ? "USERNO_"
				+ admin.getUserNo() : admin.getAdminID();
		this.type = request.getParameter("type");
		String CpnyId = admin.getCpnyId();
		this.path = request.getRealPath("");

		int init = path.indexOf("\\");
		String server = "win";
		if (init < 0) { // WebServer的os为unix/linux
			server = "u/l";
		}
		if ("win".equals(server)) {
			path = path + "\\resources\\temp\\download\\" + CpnyId + "\\"
					+ adminID + "\\";
		} else {
			path = path + "/resources/temp/download/" + CpnyId + "/" + adminID;
		}

		// 临时文件名
		String tempFilePath = rUtil.generateTempFileName(this.path, name);
		// 导出文件名
		String exportFilePath = this.path + "\\" + name + ".xls";

		File tempFile = new File(tempFilePath);
		if (tempFile.exists()) {
			tempFile.delete();
		}
		File exportFile = new File(exportFilePath);
		if (exportFile.exists()) {
			exportFile.delete();
		}

		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet1 = wb.createSheet("sheet1");
		for (int i = 0; i < 35; i++) {
			sheet1.setColumnWidth(i, 3800);
		}
		XSSFRow row = sheet1.createRow(0);
		XSSFCell cell;

		List result = null;
		try {
			/**
			 * 制作 导出数据
			 */
			row = sheet1.createRow(0);
			for (int i = 0; i < aliasNameList.size(); i++) {
				cell = row.createCell((short) i);
				cell.setCellValue(aliasNameList.get(i).toString());
			}
			if (aliasValueList != null && aliasValueList.size() > 0) {// 导出数据
				result = aliasValueList;
				for (int i = 0; i < result.size(); i++) {
					int k = 0;
					row = sheet1.createRow(i + 1);
					for (int j = 0; j < columns.length; j++) {
						cell = row.createCell((short) k++);
						cell.setCellValue(((Map) (result.get(i)))
								.get(columns[j].toString()) == null ? ""
								: ((Map) (result.get(i))).get(
										columns[j].toString()).toString());
					}
				}
			}
			File f = new File(this.path);
			if (!f.exists()) {
				f.mkdirs();
			}
			FileOutputStream fos = new FileOutputStream(new File(tempFilePath));
			wb.write(fos);
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		/**
		 * 导出excel
		 */
		response.setContentType(CONTENT_TYPE);
		response.setContentType("application/x-msdownload");
		response.setHeader("Content-Disposition", "attachment;filename=" + "\\"
				+ name + ".xls");
		String password = paramMap.get("password").toString();
		if (password != null && !"".equals(password)) {
			rUtil.rebuildExcel(tempFilePath, exportFilePath);
			tempFile.delete();
			rUtil.excelEncrypt(exportFilePath, password);
		}

		exportFile = new File(exportFilePath);
		byte b[] = new byte[2048];
		long k = 0;
		BufferedInputStream fin = null;
		OutputStream myout = response.getOutputStream();
		try {
			if (exportFile.isFile()) {
				fin = new BufferedInputStream(new FileInputStream(exportFile));
				while (k < exportFile.length()) {
					int j = fin.read(b, 0, 2048);
					k += j;
					myout.write(b, 0, j);
				}
				myout.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fin != null)
				try {
					fin.close();
				} catch (Exception e) {
				}
			if (myout != null)
				try {
					myout.close();
				} catch (Exception e) {
				}

			tempFile = new File(tempFilePath);
			if (tempFile.exists()) {
				tempFile.delete();
			}
			exportFile = new File(exportFilePath);
			if (exportFile.exists()) {
				exportFile.delete();
			}
		}
	}

	/**
	 * 导出数据到excel并加密 为excel自动报表服务，数字类型在excel中为数字类型。
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws Exception
	 */
	@SuppressWarnings( { "unchecked", "deprecation" })
	public void exportExcelByNamePwdForDISC(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap,
			List aliasValueList, List aliasNameList, String[] columns,
			String name, Map paramMap) throws Exception {
		ReportUtil rUtil = new ReportUtil();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String adminID = admin.getAdminID() == null ? "USERNO_"
				+ admin.getUserNo() : admin.getAdminID();
		this.type = request.getParameter("type");
		String CpnyId = admin.getCpnyId();
		this.path = request.getRealPath("");

		int init = path.indexOf("\\");
		String server = "win";
		if (init < 0) { // WebServer的os为unix/linux
			server = "u/l";
		}
		if ("win".equals(server)) {
			path = path + "\\resources\\temp\\download\\" + CpnyId + "\\"
					+ adminID + "\\";
		} else {
			path = path + "/resources/temp/download/" + CpnyId + "/" + adminID;
		}

		// 临时文件名
		String tempFilePath = rUtil.generateTempFileName(this.path, name);
		// 导出文件名
		String exportFilePath = this.path + "\\" + name + ".xls";

		File tempFile = new File(tempFilePath);
		if (tempFile.exists()) {
			tempFile.delete();
		}
		File exportFile = new File(exportFilePath);
		if (exportFile.exists()) {
			exportFile.delete();
		}

		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet1 = wb.createSheet("sheet1");
		// for (int i = 0; i < 35; i++) {
		// sheet1.setColumnWidth(i, 3800);
		// }
		XSSFRow row = sheet1.createRow(0);
		XSSFCell cell;

		List result = null;
		try {
			/**
			 * 制作 导出数据
			 */
			row = sheet1.createRow(0);
			int k = 0;
			for (int i = 0; i < aliasNameList.size(); i++) {
				cell = row.createCell((short) i);
				cell.setCellValue(aliasNameList.get(i).toString());
			}
			if (aliasValueList != null && aliasValueList.size() > 0) {// 导出数据
				result = aliasValueList;
				for (int i = 0; i < result.size(); i++) {
					k = 0;
					row = sheet1.createRow(i + 1);
					for (int j = 0; j < columns.length; j++) {
						cell = row.createCell((short) k++);

						if (((Map) (result.get(i))).get(columns[j].toString()) == null) {
							cell.setCellValue("");
						} else if (((Map) (result.get(i))).get(columns[j]
								.toString()) instanceof String) {
							cell.setCellValue(((Map) (result.get(i)))
									.get(columns[j].toString()) == null ? ""
									: ((Map) (result.get(i))).get(
											columns[j].toString()).toString());
						} else if (((Map) (result.get(i))).get(columns[j]
								.toString()) instanceof Date) {
							cell.setCellValue(((Map) (result.get(i)))
									.get(columns[j].toString()) == null ? ""
									: ((Map) (result.get(i))).get(
											columns[j].toString()).toString());
						} else {
							cell
									.setCellValue(Double
											.parseDouble(((Map) (result.get(i)))
													.get(columns[j].toString()) == null ? ""
													: ((Map) (result.get(i)))
															.get(
																	columns[j]
																			.toString())
															.toString()));
						}
					}
				}
			}
			File f = new File(this.path);
			if (!f.exists()) {
				f.mkdirs();
			}
			FileOutputStream fos = new FileOutputStream(new File(tempFilePath));
			wb.write(fos);
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		/**
		 * 导出excel
		 */
		response.setContentType(CONTENT_TYPE);

		try {
			File file = new File(tempFilePath);
			response.setContentType("application/x-msdownload");
			response.setContentLength((int) file.length());
			response.setHeader("Content-Disposition", "attachment;filename="
					+ new String(("\\" + name + ".xls").getBytes("gb2312"),
							"ISO8859-1"));
			FileInputStream fis = new FileInputStream(file);
			BufferedInputStream buff = new BufferedInputStream(fis);

			byte[] b = new byte[2048];
			long k = 0;
			OutputStream myout = response.getOutputStream();
			while (k < file.length()) {
				int j = buff.read(b, 0, 2048);
				k += j;
				myout.write(b, 0, j);
			}
			myout.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将excel的数据导入到数据库中
	 * 
	 * @Description: CHRS2.0 SI PROJECT
	 * @author li huihua
	 * @date 2014.08.19
	 * @version V1.0
	 */
	@SuppressWarnings("unchecked")
	public void exportExcelMoreSheetWithHeaderTip(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap,
			LinkedHashMap sqlContentmap, List aliasNameList, List aliasList,
			List mapNameList, List mapList, String excelName, List tipList) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String adminID = admin.getAdminID() == null ? "USERNO_"
				+ admin.getUserNo() : admin.getAdminID();
		this.type = request.getParameter("type");
		String CpnyId = admin.getCpnyId();
		this.path = request.getRealPath("");

		int init = path.indexOf("\\");
		String server = "win";
		if (init < 0) { // WebServer的os为unix/linux
			server = "u/l";
		}
		if ("win".equals(server)) {
			path = path + "\\resources\\temp\\download\\" + CpnyId + "\\"
					+ adminID + "\\";
		} else {
			path = path + "/resources/temp/download/" + CpnyId + "/" + adminID;
		}
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet1 = wb.createSheet(excelName);
		for (int i = 0; i < 35; i++) {
			sheet1.setColumnWidth(i, 3800);
		}
		XSSFRow row = sheet1.createRow(0);
		XSSFCell cell;
		// 创建绘图对象
		XSSFDrawing p = sheet1.createDrawingPatriarch();

		List result = null;
		try {
			/**
			 * 根据导出报表的类型，定义列名，并且从相应的表中查询数据
			 */
			row = sheet1.createRow(0);
			for (int i = 0; i < aliasNameList.size(); i++) {
				String columnName = aliasNameList.get(i).toString();
				cell = row.createCell((short) i);
				cell.setCellValue(columnName);
				for (int j = 0; j < tipList.size(); j++) {
					String tipColumnName = ((Map) tipList.get(j)).get(
							"TIP_COLUMN").toString();
					if (tipColumnName.equals(aliasNameList.get(i).toString())) {
						// 前四个参数是坐标点,后四个参数是编辑和显示批注时的大小.
						XSSFComment comment = p
								.createCellComment(new XSSFClientAnchor(0, 0, 0, 0,
										(short) 4, 4, (short) 6, 26));
						// 输入批注信息
						comment.setString(new XSSFRichTextString(((Map) tipList
								.get(j)).get("TIP_CONTENT").toString()));
						cell.setCellComment(comment);
					}
				}
			}
			if (sqlContentmap != null
					&& sqlContentmap.get("sqlContent") != null
					&& !sqlContentmap.get("sqlContent").equals("")) {
				result = this.getExcelExportDataList(sqlContentmap);
			}
			File file = new File(this.path + "\\temp.xlsx");
			file.delete();

			if (result != null && result.size() > 0) {// 导出数据
				for (int i = 0; i < result.size(); i++) {
					int k = 0;
					row = sheet1.createRow(i + 1);
					for (int j = 0; j < aliasList.size(); j++) {
						cell = row.createCell((short) k++);
						cell
								.setCellValue(((Map) (result.get(i)))
										.get(aliasList.get(j).toString()) == null ? ""
										: ((Map) (result.get(i))).get(
												aliasList.get(j).toString())
												.toString());
					}
				}
			} else {// 下载导入模板
				List contentList = sqlContentmap.get("contentList") == null ? null
						: (List) sqlContentmap.get("contentList");
				if (contentList != null && contentList.size() > 0) {
					for (int i = 0; i < contentList.size(); i++) {
						int k = 0;
						row = sheet1.createRow(i + 1);
						Set keySet = ((Map) (contentList.get(i))).keySet();
						if (keySet != null) {
							Iterator it = keySet.iterator();
							while (it.hasNext()) {
								Object itValue = it.next();
								if (itValue != null) {
									cell = row.createCell((short) k++);
									cell.setCellValue(((Map) (contentList
											.get(i))).get(itValue).toString());
								}
							}
						}
					}
				}
			}

			for (int r = 0; r < mapList.size(); r++) {
				List sheet2List = new ArrayList();
				if (mapList.get(r) != null && "" != mapList.get(r)) {
					LinkedHashMap map = new LinkedHashMap();
					map.put("sqlContent", mapList.get(r));
					sheet2List = this.getExcelExportDataList(map);
				}
				XSSFSheet sheet2 = wb
						.createSheet(mapNameList.get(r).toString());
				for (int i = 0; i < 35; i++) {
					sheet2.setColumnWidth(i, 7000);
				}
				if (mapNameList != null && mapNameList.size() > 0) {// 设定sheet2的列头
					XSSFRow row2 = sheet2.createRow(0);
					XSSFCell cell2 = row2.createCell((short) 0);
					cell2.setCellValue(String.valueOf(mapNameList.get(r)));
				}
				for (int i = 0; i < sheet2List.size(); i++) {// 设定sheet2里头的内容
					XSSFRow row2 = sheet2.createRow(i + 1);
					XSSFCell cell2 = row2.createCell((short) 0);
					XSSFCell cell3 = row2.createCell((short) 1);
					if (sheet2List.get(i) != null) {
						cell2.setCellValue(String.valueOf(((Map) sheet2List
								.get(i)).get("CONTENT")));
						cell3.setCellValue(String.valueOf(((Map) sheet2List
								.get(i)).get("CODE")));
					} else {
						cell2.setCellValue("");
						cell3.setCellValue("");
					}
				}
			}
			File f = new File(this.path);
			if (!f.exists()) {
				f.mkdirs();
			}
			FileOutputStream fos = new FileOutputStream(new File(this.path
					+ "\\temp.xlsx"));
			wb.write(fos);
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.setContentType(CONTENT_TYPE);

		try {
			File file = new File(this.path + "\\temp.xlsx");
			response.setContentType("application/x-msdownload");
			response.setContentLength((int) file.length());
			response.setHeader("Content-Disposition", "attachment;filename="
					+ "temp_" + excelName + ".xlsx");
			FileInputStream fis = new FileInputStream(file);
			BufferedInputStream buff = new BufferedInputStream(fis);

			byte[] b = new byte[2048];
			long k = 0;
			OutputStream myout = response.getOutputStream();
			while (k < file.length()) {
				int j = buff.read(b, 0, 2048);
				k += j;
				myout.write(b, 0, j);
			}
			myout.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 导出数据到excel Description:export datas to excel
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author weizhengchen
	 * @date 2014-07-09
	 */
	@SuppressWarnings( { "unchecked", "deprecation" })
	public void exportExcelMoreSheetWithNo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap,
			LinkedHashMap sqlContentmap, List aliasNameList, List aliasList,
			List mapNameList, List mapList, String excelName) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String adminID = admin.getAdminID() == null ? "USERNO_"
				+ admin.getUserNo() : admin.getAdminID();
		this.type = request.getParameter("type");
		String CpnyId = admin.getCpnyId();
		this.path = request.getRealPath("");

		int init = path.indexOf("\\");
		String server = "win";
		if (init < 0) { // WebServer的os为unix/linux
			server = "u/l";
		}
		if ("win".equals(server)) {
			path = path + "\\resources\\temp\\download\\" + CpnyId + "\\"
					+ adminID + "\\";
		} else {
			path = path + "/resources/temp/download/" + CpnyId + "/" + adminID;
		}
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet1 = wb.createSheet(excelName);
		XSSFRow row = sheet1.createRow(0);
		XSSFCell cell;

		List result = null;
		try {
			/**
			 * 根据导出报表的类型，定义列名，并且从相应的表中查询数据
			 */
			row = sheet1.createRow(0);
			for (int i = 0; i < aliasNameList.size(); i++) {
				cell = row.createCell((short) i);
				cell.setCellValue(aliasNameList.get(i).toString());
			}
			if (sqlContentmap != null
					&& sqlContentmap.get("sqlContent") != null
					&& !sqlContentmap.get("sqlContent").equals("")) {
				result = this.getExcelExportDataList(sqlContentmap);
			}
			File file = new File(this.path + "\\temp.xlsx");
			file.delete();

			if (result != null && result.size() > 0) {// 导出数据
				for (int i = 0; i < result.size(); i++) {
					int k = 0;
					row = sheet1.createRow(i + 1);
					for (int j = 0; j < aliasList.size(); j++) {
						cell = row.createCell((short) k++);
						cell
								.setCellValue(((Map) (result.get(i)))
										.get(aliasList.get(j).toString()) == null ? ""
										: ((Map) (result.get(i))).get(
												aliasList.get(j).toString())
												.toString());
					}
				}
			} else {// 下载导入模板
				List contentList = sqlContentmap.get("contentList") == null ? null
						: (List) sqlContentmap.get("contentList");
				if (contentList != null && contentList.size() > 0) {
					for (int i = 0; i < contentList.size(); i++) {
						int k = 0;
						row = sheet1.createRow(i + 1);
						Set keySet = ((Map) (contentList.get(i))).keySet();
						if (keySet != null) {
							Iterator it = keySet.iterator();
							while (it.hasNext()) {
								Object itValue = it.next();
								if (itValue != null) {
									cell = row.createCell((short) k++);
									cell.setCellValue(((Map) (contentList
											.get(i))).get(itValue).toString());
								}
							}
						}
					}
				}
			}

			for (int r = 0; r < mapList.size(); r++) {
				List sheet2List = new ArrayList();
				if (mapList.get(r) != null && "" != mapList.get(r)) {
					LinkedHashMap map = new LinkedHashMap();
					map.put("sqlContent", mapList.get(r));
					sheet2List = this.getExcelExportDataList(map);
				}
				XSSFSheet sheet2 = wb
						.createSheet(mapNameList.get(r).toString());
				if (mapNameList != null && mapNameList.size() > 0) {// 设定sheet2的列头
					XSSFRow row2 = sheet2.createRow(0);
					XSSFCell cell1 = row2.createCell((short) 0);
					cell1.setCellValue("编号");
					XSSFCell cell2 = row2.createCell((short) 1);
					cell2.setCellValue("名称");
				}
				for (int i = 0; i < sheet2List.size(); i++) {// 设定sheet2里头的内容
					XSSFRow row2 = sheet2.createRow(i + 1);
					XSSFCell cell1 = row2.createCell((short) 0);
					if (sheet2List.get(i) != null) {
						cell1.setCellValue(String.valueOf(((Map) sheet2List
								.get(i)).get("CODE_NO")));
					} else {
						cell1.setCellValue("");
					}
					XSSFCell cell2 = row2.createCell((short) 1);
					if (sheet2List.get(i) != null) {
						cell2.setCellValue(String.valueOf(((Map) sheet2List
								.get(i)).get("CODE_NAME")));
					} else {
						cell2.setCellValue("");
					}
				}
			}
			File f = new File(this.path);
			if (!f.exists()) {
				f.mkdirs();
			}
			FileOutputStream fos = new FileOutputStream(new File(this.path
					+ "\\temp.xlsx"));
			wb.write(fos);
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.setContentType(CONTENT_TYPE);
		try {
			File file = new File(this.path + "\\temp.xlsx");
			response.setContentType("application/x-msdownload");
			response.setContentLength((int) file.length());
			response.setHeader("Content-Disposition", "attachment;filename="
					+ excelName + "_model" + ".xls");
			FileInputStream fis = new FileInputStream(file);
			BufferedInputStream buff = new BufferedInputStream(fis);
			byte[] b = new byte[2048];
			long k = 0;
			OutputStream myout = response.getOutputStream();
			while (k < file.length()) {
				int j = buff.read(b, 0, 2048);
				k += j;
				myout.write(b, 0, j);
			}
			myout.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 分析excel的各个列，获取各个列的值，并且导入的数据库中 Description:
	 * 
	 * @param request
	 * @param response
	 * @param map
	 * @param modelMap
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ModelMap importIsParamData(HttpServletRequest request,
			HttpServletResponse response, LinkedHashMap map, ModelMap modelMap,
			String forwardUrl, String navTabId) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String adminID = admin.getAdminID() == null ? "USERNO_"
				+ admin.getUserNo() : admin.getAdminID();
		ObjectBindUtil.getRequestParamData(request);
		this.filename = adminID;
		this.pathCpnyID = admin.getCpnyId();
		this.language = admin.getLanguage();
		if (this.processUploadFile(request, response)) {
			// int returnInt = this.importIsParamData(request, adminID, map);
			int returnInt = this.importTempDataLF(request, adminID, map,
					"IS_SETUP_INFO_TEMP");
			if (returnInt == 0) {
				modelMap.put("sign", "0");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.importfail", request));// 导入失败!
			} else if (returnInt < 0) {
				modelMap.put("sign", "-1");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.writebyrule", request));// 调整事由有误，请按规则填写！
			} else if (returnInt == 2) {
				modelMap.put("sign", "2");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.existnull", request));// 表有空值或有空单元行，请检查！
			} else if (returnInt == 1) {
				modelMap.put("sign", "1");
				modelMap.put("statusCode", "200");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.importsuccess", request));// 导入成功
				modelMap.put("navTabId", navTabId);
				modelMap.put("forwardUrl", forwardUrl);
			} else if (returnInt == 5) {
				modelMap.put("sign", "-1");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.importfailcalladmin",
						request));// 更新此人上一条数据出错，请联系管理员
			} else if (returnInt == 6) {
				modelMap.put("sign", "-1");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"alert.message.pa.bonus.thisDataIsExist_add_fail",
						request));// 此数据已经存在，请核对数据后再进行导入!
			}
			File file = new File(this.path + "\\" + filename + ".xls");
			file.delete();
		} else {
			modelMap.put("sign", "0");
			modelMap.put("statusCode", "300");
			modelMap.put("message", TipMessage.getTipMessage(
					"ar.alert.message.excelimport.importfail", request));// 导入失败!
		}
		return modelMap;
	}

	@SuppressWarnings( { "unchecked" })
	public int importIsParamData(HttpServletRequest request, String adminID,
			LinkedHashMap cellmap) {
		int token = 0;
		int k = path.indexOf("\\");
		String server = "win";
		if (k < 0) { // WebServer的os为unix/linux
			server = "u/l";
		}
		File file = null;
		if ("win".equals(server)) {
			file = new File(path + "\\" + filename + ".xls");
		} else {
			file = new File(path + "/" + filename + ".xls");
		}
		Map dataMap = new LinkedHashMap();
		try {
			Workbook workBook = Workbook.getWorkbook(file);
			// 获得第一个工作表对象
			Sheet sheet = workBook.getSheet(0);
			// 验证是否存在导入数据
			if (sheet.getRows() < 2) {
				logger.debug("Import file not exist data, return.");
				return token;
			}
			String tableName = cellmap.get("tableName") == null ? "" : cellmap
					.get("tableName").toString();// 获取表名
			LinkedHashMap aliasValueMap = cellmap.get("aliasValueMap") == null ? null
					: (LinkedHashMap) cellmap.get("aliasValueMap");
			LinkedHashMap aliasTypeMap = cellmap.get("aliasTypeMap") == null ? null
					: (LinkedHashMap) cellmap.get("aliasTypeMap");
			LinkedHashMap aliasValueI18nMap = cellmap.get("aliasValueI18nMap") == null ? null
					: (LinkedHashMap) cellmap.get("aliasValueI18nMap");
			String aliasNullStr = cellmap.get("aliasNullStr") == null ? ""
					: cellmap.get("aliasNullStr").toString();

			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			LinkedHashMap aliasMapCells = new LinkedHashMap();
			if (aliasValueMap != null && aliasTypeMap != null) {// 存放CELL0：NUMBER类型的键值对
				Set keySet = aliasValueMap.keySet();
				if (keySet != null) {
					Iterator it = keySet.iterator();
					while (it.hasNext()) {
						Object keyvalue = it.next();
						if (keyvalue != null) {
							Object aliasValue = aliasValueMap.get(keyvalue);
							if (aliasValue != null) {
								aliasMapCells.put(aliasValue.toString(),
										aliasTypeMap.get(keyvalue));
							}
						}
					}
				}
			}
			List addList = new ArrayList();
			// 循环工作表的每行数据
			int init = 0;
			int checkFlag = 0;
			Cell[] headerRow = sheet.getRow(0);
			init = sheet.getRow(0).length;
			for (int i = 1; i < sheet.getRows(); i++) {
				Cell[] row = sheet.getRow(i);
				// 验证当前行是否符合规范
				checkFlag = validateCellForRequiredColum(headerRow, row, init);
				if (checkFlag != 1) {
					// 不符合规范
					break;
				} else {
					// 符合规范
					LinkedHashMap smap = new LinkedHashMap();
					LinkedHashMap aliasCellmap = new LinkedHashMap();
					for (int j = 0; j < init; j++) {
						try {
							String cellValue = "";
							Object cellType = aliasMapCells.get("#CELL" + j
									+ "#");
							String content = "";
							if (aliasNullStr.indexOf("," + j + ",") < 0) {
								content = StringUtil.checkNull(row[j]
										.getContents());
								if (cellType != null && !content.equals("")) {// date类型
									try {
										cellValue = this.FormateData(row[j],
												cellType);
										if (cellValue.contains("/")) {
											String[] contentStr = cellValue
													.split("/");
											if (contentStr.length == 3
													&& contentStr[0].length() == 2
													&& contentStr[2].length() == 4) {
												cellValue = contentStr[2] + "/"
														+ contentStr[1] + "/"
														+ contentStr[0];
											}
										} else if (cellValue.contains("-")) {
											String[] contentStr = cellValue
													.split("-");
											if (contentStr.length == 3
													&& contentStr[0].length() == 2
													&& contentStr[2].length() == 4) {
												cellValue = contentStr[2] + "-"
														+ contentStr[1] + "-"
														+ contentStr[0];
											}
										}
									} catch (Exception e) {
										cellValue = "";
										if (row[j].getContents() != null) {
											cellValue = row[j].getContents();
											if (cellValue.contains("/")) {
												String[] contentStr = cellValue
														.split("/");
												if (contentStr.length == 3
														&& contentStr[0]
																.length() == 2
														&& contentStr[2]
																.length() == 4) {
													cellValue = contentStr[2]
															+ "/"
															+ contentStr[1]
															+ "/"
															+ contentStr[0];
												}
											} else if (cellValue.contains("-")) {
												String[] contentStr = cellValue
														.split("-");
												if (contentStr.length == 3
														&& contentStr[0]
																.length() == 2
														&& contentStr[2]
																.length() == 4) {
													cellValue = contentStr[2]
															+ "-"
															+ contentStr[1]
															+ "-"
															+ contentStr[0];
												}
											}
										}
									}
								} else {
									cellValue = row[j].getContents() == null ? ""
											: row[j].getContents();
								}
							} else {
								try {
									cellValue = row[j].getContents() == null ? ""
											: row[j].getContents();
								} catch (Exception e) {
									cellValue = "";
								}
							}
							aliasCellmap.put("#CELL" + j + "#",
									cellValue == null ? "" : cellValue.trim());
						} catch (Exception e) {
							e.printStackTrace();
							// 表有空值或有空单元行，请检查！
							return token = 2;
						}
					}
					String aliasKeySql = "";
					String aliasValueSql = "";
					int initKey = 0;
					if (aliasValueMap != null && aliasTypeMap != null) {
						Set keySet = aliasValueMap.keySet();
						if (keySet != null) {
							Iterator it = keySet.iterator();
							while (it.hasNext()) {
								initKey++;
								Object keyValue = it.next();
								if (keyValue != null && !keyValue.equals("")) {
									int fieldType = Integer
											.parseInt(aliasTypeMap
													.get(keyValue).toString());
									Object aliasCellmapValue = aliasCellmap
											.get(aliasValueMap.get(keyValue));
									if (aliasCellmapValue == null) {// 不属于excel的列
										aliasCellmapValue = aliasValueMap
												.get(keyValue);
										if (aliasCellmapValue != null
												&& aliasCellmapValue.toString()
														.contains("#CELL")) {
											String sqlContent = aliasCellmapValue
													.toString();
											for (int l = 0; l < init; l++) {
												sqlContent = sqlContent
														.replaceAll(
																"#CELL" + l
																		+ "#",
																aliasCellmap
																		.get(
																				"#CELL"
																						+ l
																						+ "#")
																		.toString());
												if (!sqlContent
														.contains("#CELL")) {
													break;
												}
											}
											LinkedHashMap sqlI8nContentMap = new LinkedHashMap();
											switch (fieldType) {
											case 2:
												sqlI8nContentMap.put(
														"sqlI18nContent",
														sqlContent);
												aliasCellmapValue = this
														.getCodeNoByName(sqlI8nContentMap) == -1 ? ""
														: this
																.getCodeNoByName(sqlI8nContentMap);
												break;
											default:
												sqlI8nContentMap.put(
														"sqlContent",
														sqlContent);
												if (this
														.getContentNoByFiled(sqlI8nContentMap) != null) {
													Object cellObj = this
															.getContentNoByFiled(
																	sqlI8nContentMap)
															.size() == 0 ? ""
															: this
																	.getContentNoByFiled(
																			sqlI8nContentMap)
																	.get(0);
													if (cellObj != null
															&& !cellObj
																	.equals("")
															&& ((Map) cellObj)
																	.get(keyValue
																			.toString()) != null) {
														aliasCellmapValue = (cellObj == null ? ""
																: ((Map) cellObj)
																		.get(
																				keyValue
																						.toString()
																						.toUpperCase())
																		.toString());
													} else {
														aliasCellmapValue = "";
													}
												}

												break;
											}
										}
									} else {
										if (aliasValueI18nMap != null
												&& aliasValueI18nMap
														.get(keyValue) != null) {// 有需要从国际化表读取No的列
											String sqlContent = aliasValueI18nMap
													.get(keyValue) == null ? ""
													: aliasValueI18nMap.get(
															keyValue)
															.toString();
											sqlContent = sqlContent
													.replaceAll(
															aliasValueMap
																	.get(keyValue) == null ? ""
																	: aliasValueMap
																			.get(
																					keyValue)
																			.toString(),
															"'"
																	+ aliasCellmapValue
																			.toString()
																	+ "'");

											LinkedHashMap sqlI8nContentMap = new LinkedHashMap();
											sqlI8nContentMap.put(
													"sqlI18nContent",
													sqlContent);
											if (aliasValueI18nMap.get(keyValue
													+ "_FLAG") != null
													&& aliasValueI18nMap.get(
															keyValue + "_FLAG")
															.equals("Y")) {
												sqlI8nContentMap.put(
														"sqlContent",
														sqlContent);
												List contentList = this
														.getContentNoByFiled(sqlI8nContentMap);
												if (contentList.get(0) != null
														&& ((Map) (contentList
																.get(0)))
																.get("CPNY_ID") != null) {
													aliasCellmapValue = (contentList == null ? ""
															: ((Map) (contentList
																	.get(0)))
																	.get(
																			"CPNY_ID")
																	.toString());
												} else if (contentList.get(0) != null
														&& ((Map) (contentList
																.get(0)))
																.get("DEPTNO") != null) {
													aliasCellmapValue = (contentList == null ? ""
															: ((Map) (contentList
																	.get(0)))
																	.get(
																			"DEPTNO")
																	.toString());
												} else {
													aliasCellmapValue = "";
												}
											} else {
												aliasCellmapValue = this
														.getCodeNoByName(sqlI8nContentMap) == -1 ? ""
														: this
																.getCodeNoByName(sqlI8nContentMap);
											}
											if (aliasCellmapValue.equals("")) {
												System.out.println("--------"
														+ aliasValueMap.get(
																keyValue)
																.toString());
											}
										}
									}
									switch (fieldType) {
									case 0:
										// smap.put(keyValue.toString(),
										// "'"+aliasValueMap.get(keyValue)+"'");
										if (initKey == 1) {
											aliasKeySql = keyValue.toString();
											aliasValueSql = "'"
													+ aliasCellmapValue + "'";
										} else {
											aliasKeySql += ","
													+ keyValue.toString();
											aliasValueSql += ",'"
													+ aliasCellmapValue + "'";
										}
										break;
									case 1:
										// smap.put(keyValue.toString(),
										// "'"+aliasValueMap.get(keyValue)+"'");
										if (initKey == 1) {
											aliasKeySql = keyValue.toString();
											if (aliasCellmapValue != null
													&& aliasCellmapValue
															.toString()
															.contains("/")) {
												aliasValueSql = "to_date('"
														+ aliasCellmapValue
														+ "','YYYY/MM/DD HH24:MI')";// "'"+aliasCellmapValue+"'";
											} else if (aliasCellmapValue != null
													&& aliasCellmapValue
															.toString()
															.contains("-")) {
												aliasValueSql = "to_date('"
														+ aliasCellmapValue
														+ "','YYYY-MM-DD HH24:MI')";// "'"+aliasCellmapValue+"'";
											}
										} else {
											aliasKeySql += ","
													+ keyValue.toString();
											if (aliasCellmapValue != null
													&& aliasCellmapValue
															.toString()
															.contains("/")) {
												aliasValueSql += ",to_date('"
														+ aliasCellmapValue
														+ "','YYYY/MM/DD HH24:MI')";// "'"+aliasCellmapValue+"'";
											} else if (aliasCellmapValue != null
													&& aliasCellmapValue
															.toString()
															.contains("-")) {
												aliasValueSql += ",to_date('"
														+ aliasCellmapValue
														+ "','YYYY-MM-DD HH24:MI')";// "'"+aliasCellmapValue+"'";
											} else if (aliasCellmapValue == "") {
												aliasValueSql += ",'' ";
											}
										}
										break;
									case 11:
										if (initKey == 1) {
											aliasKeySql = keyValue.toString();
											if (aliasCellmapValue != null
													&& aliasCellmapValue
															.toString()
															.contains("/")) {
												aliasValueSql = "to_date('"
														+ aliasCellmapValue
														+ "','YYYY/MM/DD HH24:MI:SS')";// "'"+aliasCellmapValue+"'";
											} else if (aliasCellmapValue != null
													&& aliasCellmapValue
															.toString()
															.contains("-")) {
												aliasValueSql = "to_date('"
														+ aliasCellmapValue
														+ "','YYYY-MM-DD HH24:MI:SS')";// "'"+aliasCellmapValue+"'";
											}
										} else {
											aliasKeySql += ","
													+ keyValue.toString();
											if (aliasCellmapValue != null
													&& aliasCellmapValue
															.toString()
															.contains("/")) {
												aliasValueSql += ",to_date('"
														+ aliasCellmapValue
														+ "','YYYY/MM/DD HH24:MI:SS')";// "'"+aliasCellmapValue+"'";
											} else if (aliasCellmapValue != null
													&& aliasCellmapValue
															.toString()
															.contains("-")) {
												aliasValueSql += ",to_date('"
														+ aliasCellmapValue
														+ "','YYYY-MM-DD HH24:MI:SS')";
											}
										}
										break;
									case 12:
										if (initKey == 1) {
											aliasKeySql = keyValue.toString();
											if (aliasCellmapValue != null
													&& aliasCellmapValue
															.toString()
															.contains("/")) {
												aliasValueSql = "to_date('"
														+ aliasCellmapValue
														+ "','YYYY/MM/DD HH24:MI')";// "'"+aliasCellmapValue+"'";
											} else if (aliasCellmapValue != null
													&& aliasCellmapValue
															.toString()
															.contains("-")) {
												aliasValueSql = "to_date('"
														+ aliasCellmapValue
														+ "','YYYY-MM-DD HH24:MI')";// "'"+aliasCellmapValue+"'";
											}
										} else {
											aliasKeySql += ","
													+ keyValue.toString();
											if (aliasCellmapValue != null
													&& aliasCellmapValue
															.toString()
															.contains("/")) {
												aliasValueSql += ",to_date('"
														+ aliasCellmapValue
														+ "','YYYY/MM/DD HH24:MI')";// "'"+aliasCellmapValue+"'";
											} else if (aliasCellmapValue != null
													&& aliasCellmapValue
															.toString()
															.contains("-")) {
												aliasValueSql += ",to_date('"
														+ aliasCellmapValue
														+ "','YYYY-MM-DD HH24:MI')";
											}
										}
										break;
									default:
										if (initKey == 1) {
											aliasKeySql = keyValue.toString();
											aliasValueSql = (aliasCellmapValue == null ? "null"
													: aliasCellmapValue
															.toString());
										} else {
											aliasKeySql += ","
													+ keyValue.toString();
											aliasValueSql += ","
													+ (aliasCellmapValue == null ? "null"
															: aliasCellmapValue
																	.toString())
													+ "";
										}
										break;
									}
								}
							}
						}
					}
					smap.put("tableName", tableName);
					smap.put("aliasNameKeyContent", aliasKeySql);
					smap.put("aliasValueContent", aliasValueSql);
					addList.add(smap);
				}
			}
			dataMap.put("addList", addList);
			dataMap.put("CPNY_ID", admin.getCpnyId());
			dataMap.put("UPLOAD_BY", admin.getPersonId());
			this.insertExcelDataIsParam(dataMap);
			token = 1;
		} catch (Exception e) { // 未找到文件异常
			e.printStackTrace();
			token = 0;
		}
		return token;
	}

	/**
	 * 将excel的数据导入到数据库中的IS_SETUP_INFO_TEMP
	 */
	public void insertExcelDataIsParam(Object obj) throws Exception {
		this.excelUtilDao.insertExcelDataIsParam(obj);

	}

	/**
	 * 导出数据到excel Description:export datas to excel
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @author weizhengchen
	 * @date 2014-07-09
	 */
	@SuppressWarnings( { "unchecked", "deprecation" })
	public void exportExcelMoreSheet2(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap,
			LinkedHashMap sqlContentmap, List aliasNameList, List aliasList,
			List mapNameList, List mapList, String excelName) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String adminID = admin.getAdminID() == null ? "USERNO_"
				+ admin.getUserNo() : admin.getAdminID();
		this.type = request.getParameter("type");
		String CpnyId = admin.getCpnyId();
		this.path = request.getRealPath("");

		int init = path.indexOf("\\");
		String server = "win";
		if (init < 0) { // WebServer的os为unix/linux
			server = "u/l";
		}
		if ("win".equals(server)) {
			path = path + "\\resources\\temp\\download\\" + CpnyId + "\\"
					+ adminID + "\\";
		} else {
			path = path + "/resources/temp/download/" + CpnyId + "/" + adminID;
		}
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet1 = wb.createSheet(excelName);
		XSSFRow row = sheet1.createRow(0);
		XSSFCell cell;

		List result = null;
		try {
			/**
			 * 根据导出报表的类型，定义列名，并且从相应的表中查询数据
			 */
			row = sheet1.createRow(0);
			for (int i = 0; i < aliasNameList.size(); i++) {
				cell = row.createCell((short) i);
				cell.setCellValue(aliasNameList.get(i).toString());
			}
			if (sqlContentmap != null
					&& sqlContentmap.get("sqlContent") != null
					&& !sqlContentmap.get("sqlContent").equals("")) {
				result = this.getExcelExportDataList(sqlContentmap);
			}
			File file = new File(this.path + "\\temp.xlsx");
			file.delete();

			if (result != null && result.size() > 0) {// 导出数据
				for (int i = 0; i < result.size(); i++) {
					int k = 0;
					row = sheet1.createRow(i + 1);
					for (int j = 0; j < aliasList.size(); j++) {
						cell = row.createCell((short) k++);
						cell
								.setCellValue(((Map) (result.get(i)))
										.get(aliasList.get(j).toString()) == null ? ""
										: ((Map) (result.get(i))).get(
												aliasList.get(j).toString())
												.toString());
					}
				}
			} else {// 下载导入模板
				List contentList = sqlContentmap.get("contentList") == null ? null
						: (List) sqlContentmap.get("contentList");
				if (contentList != null && contentList.size() > 0) {
					for (int i = 0; i < contentList.size(); i++) {
						int k = 0;
						row = sheet1.createRow(i + 1);
						Set keySet = ((Map) (contentList.get(i))).keySet();
						if (keySet != null) {
							Iterator it = keySet.iterator();
							while (it.hasNext()) {
								Object itValue = it.next();
								if (itValue != null) {
									cell = row.createCell((short) k++);
									cell.setCellValue(((Map) (contentList
											.get(i))).get(itValue).toString());
								}
							}
						}
					}
				}
			}

			for (int r = 0; r < mapList.size(); r++) {
				List sheet2List = new ArrayList();
				if (mapList.get(r) != null && "" != mapList.get(r)) {
					LinkedHashMap map = new LinkedHashMap();
					map.put("sqlContent", mapList.get(r));
					sheet2List = this.getExcelExportDataList(map);
				}
				XSSFSheet sheet2 = wb
						.createSheet(mapNameList.get(r).toString());
				if (mapNameList != null && mapNameList.size() > 0) {// 设定sheet2的列头
					XSSFRow row2 = sheet2.createRow(0);
					XSSFCell cell2 = row2.createCell((short) 0);
					cell2.setCellValue(String.valueOf(mapNameList.get(r)));
				}
				for (int i = 0; i < sheet2List.size(); i++) {// 设定sheet2里头的内容
					XSSFRow row2 = sheet2.createRow(i + 1);
					XSSFCell cell2 = row2.createCell((short) 0);
					XSSFCell cell3 = row2.createCell((short) 1);
					if (sheet2List.get(i) != null) {
						cell2.setCellValue(String.valueOf(((Map) sheet2List
								.get(i)).get("CONTENT")));
						cell3.setCellValue(String.valueOf(((Map) sheet2List
								.get(i)).get("CONTENT1")));
					} else {
						cell2.setCellValue("");
						cell3.setCellValue("");
					}
				}
			}
			File f = new File(this.path);
			if (!f.exists()) {
				f.mkdirs();
			}
			FileOutputStream fos = new FileOutputStream(new File(this.path
					+ "\\temp.xlsx"));
			wb.write(fos);
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.setContentType(CONTENT_TYPE);

		try {
			File file = new File(this.path + "\\temp.xlsx");
			response.setContentType("application/x-msdownload");
			response.setContentLength((int) file.length());
			response.setHeader("Content-Disposition", "attachment;filename="
					+ "temp_" + excelName + ".xlsx");
			FileInputStream fis = new FileInputStream(file);
			BufferedInputStream buff = new BufferedInputStream(fis);

			byte[] b = new byte[2048];
			long k = 0;
			OutputStream myout = response.getOutputStream();
			while (k < file.length()) {
				int j = buff.read(b, 0, 2048);
				k += j;
				myout.write(b, 0, j);
			}
			myout.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将excel的数据导入到数据库中（营业员评价数据导入 临时表）
	 * 
	 * @Description: CHRS2.0 SI PROJECT
	 * @author PengHaixia
	 * @date 2014.10.10
	 * @version V1.0
	 */
	@SuppressWarnings("unchecked")
	public ModelMap importExcelTempEmpResignData(HttpServletRequest request,
			HttpServletResponse response, LinkedHashMap map, ModelMap modelMap) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String adminID = admin.getAdminID() == null ? "USERNO_"
				+ admin.getUserNo() : admin.getAdminID();
		ObjectBindUtil.getRequestParamData(request);
		this.filename = adminID;
		this.pathCpnyID = admin.getCpnyId();
		this.language = admin.getLanguage();
		if (this.processUploadFile(request, response)) {
			// excel数据读取
			ModelMap preparedMap = prepareForImportExcel(request, adminID, map);
			int returnInt = (Integer) preparedMap.get("token");
			if (returnInt == 1) {
				// excel数据读取成功，将数据导入 临时表
				LinkedHashMap paramMap = new LinkedHashMap();
				paramMap.put("IMP_EMPNO", admin.getEmpID());
				paramMap.put("CPNY_ID", admin.getCpnyId());
				try {
					// 删除临时表中当前用户旧数据
					this.excelUtilDao.deleteImportTempEmpResignTemp(paramMap);
					Map dataMap = new HashMap();
					dataMap = (Map) preparedMap.get("dataMap");
					// 为当前用户向临时表中插入数据
					this.excelUtilDao.insertExcelDataForTransferOrder(dataMap);
				} catch (Exception e) {
					e.printStackTrace();
					returnInt = 4;
				}
			}
			if (returnInt == 1) {
				modelMap.put("sign", "1");
				modelMap.put("statusCode", "200");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.importsuccess", request));// 导入成功
				modelMap.put("navTabId", "hr0206");
				modelMap
						.put("forwardUrl",
								"/hrm/transferOrder/viewResignationImpResultList?pageNum=1");
			} else if (returnInt == 2) {
				modelMap.put("sign", "2");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"inct.message.excelimport.nullRow", request));// 导入数据中有空行，请检查！
			} else if (returnInt == 3) {
				modelMap.put("sign", "3");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"inct.message.excelimport.requiredCol", request));// 导入数据中*项不可以为空，请检查！
			} else if (returnInt == 4) {
				modelMap.put("sign", "4");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage
						.getTipMessage(
								"inct.message.excelimport.writeTempTable.Fail",
								request));// 写入临时表失败！
			} else if (returnInt == 0) {
				modelMap.put("sign", "0");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.importfail", request));// 导入失败!
			}
			File file = new File(this.path + "\\" + filename + ".xls");
			file.delete();
		} else {
			modelMap.put("sign", "0");
			modelMap.put("statusCode", "300");
			modelMap.put("message", TipMessage.getTipMessage(
					"inct.message.excelimport.readExcelFile.Fail", request));// 文件读取失败!
		}
		return modelMap;
	}

	/* 2014.10.30 CH.W.G Add */
	@SuppressWarnings( { "unchecked", "deprecation" })
	public void exportExcelMoreTab(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap,
			List aliasValueList, List aliasNameList, List aliasColList,
			String name, String[] sheets, Map paramMap) throws Exception {
		ReportUtil rUtil = new ReportUtil();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String adminID = "";
		String CpnyId = "";
		if (admin != null) {
			adminID = admin.getAdminID() == null ? "USERNO_"
					+ admin.getUserNo() : admin.getAdminID();
			CpnyId = admin.getCpnyId();
		} else {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			Date now = new Date();
			adminID = dateFormat.format(now);
			CpnyId = request.getParameter("CPNY_ID");
		}

		this.type = request.getParameter("type");
		this.path = request.getRealPath("");

		int init = path.indexOf("\\");
		String server = "win";
		if (init < 0) { // WebServer的os为unix/linux
			server = "u/l";
		}
		if ("win".equals(server)) {
			path = path + "\\resources\\temp\\download\\" + CpnyId + "\\"
					+ adminID + "\\";
		} else {
			path = path + "/resources/temp/download/" + CpnyId + "/" + adminID;
		}
		// 导出文件名
		String exportFilePath = this.path + "\\" + name + ".xls";

		File exportFile = new File(exportFilePath);
		if (exportFile.exists()) {
			exportFile.delete();
		}

		XSSFWorkbook wb = new XSSFWorkbook();
		for (int idx = 0; idx < sheets.length; idx++) {
			XSSFSheet sheet1 = wb.createSheet(sheets[idx]);
			sheet1.setColumnWidth(idx, 3800);
			XSSFRow row = sheet1.createRow(0);
			XSSFCell cell;

			List result = null;
			try {
				row = sheet1.createRow(0);
				String[] names = (String[]) aliasNameList.get(idx);
				for (int i = 0; i < names.length; i++) {
					cell = row.createCell((short) i);
					cell.setCellValue(names[i].toString());
				}
				if (aliasValueList != null
						&& ((List) aliasValueList.get(idx)).size() > 0) {// 导出数据
					result = (List) aliasValueList.get(idx);
					for (int i = 0; i < result.size(); i++) {
						int k = 0;
						row = sheet1.createRow(i + 1);
						String[] columns = (String[]) aliasColList.get(idx);
						for (int j = 0; j < columns.length; j++) {
							cell = row.createCell((short) k++);
							cell.setCellValue(((Map) (result.get(i)))
									.get(columns[j].toString()) == null ? ""
									: ((Map) (result.get(i))).get(
											columns[j].toString()).toString());
						}
					}
				}
				File f = new File(this.path);
				if (!f.exists()) {
					f.mkdirs();
				}
				FileOutputStream fos = new FileOutputStream(new File(
						exportFilePath));
				wb.write(fos);
				fos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		/**
		 * 导出excel
		 */
		response.setContentType(CONTENT_TYPE);
		response.setContentType("application/x-msdownload");
		response.setHeader("Content-Disposition", "attachment;filename=" + "\\"
				+ name + ".xls");

		byte b[] = new byte[2048];
		long k = 0;
		BufferedInputStream fin = null;
		OutputStream myout = response.getOutputStream();
		try {
			if (exportFile.isFile()) {
				fin = new BufferedInputStream(new FileInputStream(exportFile));
				while (k < exportFile.length()) {
					int j = fin.read(b, 0, 2048);
					k += j;
					myout.write(b, 0, j);
				}
				myout.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fin != null)
				try {
					fin.close();
				} catch (Exception e) {
				}
			if (myout != null)
				try {
					myout.close();
				} catch (Exception e) {
				}
		}
	}

	/**
	 * 将excel的临时职发令数据导入到数据库临时表中
	 * 
	 * @Description: CHRS2.0 SI PROJECT
	 * @author PengHaixia
	 * @date 2014.10.10
	 * @version V1.0
	 */
	@SuppressWarnings("unchecked")
	public ModelMap importExcelTempEmpTransferOrderData(
			HttpServletRequest request, HttpServletResponse response,
			LinkedHashMap map, ModelMap modelMap) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String adminID = admin.getAdminID() == null ? "USERNO_"
				+ admin.getUserNo() : admin.getAdminID();
		ObjectBindUtil.getRequestParamData(request);
		this.filename = adminID;
		this.pathCpnyID = admin.getCpnyId();
		this.language = admin.getLanguage();
		if (this.processUploadFile(request, response)) {
			// excel数据读取
			ModelMap preparedMap = prepareForImportExcel(request, adminID, map);
			int returnInt = (Integer) preparedMap.get("token");
			if (returnInt == 1) {
				// excel数据读取成功，将数据导入 临时表
				LinkedHashMap paramMap = new LinkedHashMap();
				paramMap.put("IMP_EMPNO", admin.getEmpID());
				paramMap.put("CPNY_ID", admin.getCpnyId());
				try {
					// 删除临时表中当前用户旧数据
					this.excelUtilDao
							.deleteImportTempEmpTransferOrderTemp(paramMap);
					Map dataMap = new HashMap();
					dataMap = (Map) preparedMap.get("dataMap");
					// 为当前用户向临时表中插入数据
					this.excelUtilDao.insertExcelDataForTransferOrder(dataMap);
				} catch (Exception e) {
					e.printStackTrace();
					returnInt = 4;
				}
			}
			if (returnInt == 1) {
				modelMap.put("sign", "1");
				modelMap.put("statusCode", "200");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.importsuccess", request));// 导入成功
				modelMap.put("navTabId", "hr0515");
				modelMap
						.put("forwardUrl",
								"/hrm/transferOrder/viewTempEmpTransferOrderResultList?pageNum=1");
			} else if (returnInt == 2) {
				modelMap.put("sign", "2");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"inct.message.excelimport.nullRow", request));// 导入数据中有空行，请检查！
			} else if (returnInt == 3) {
				modelMap.put("sign", "3");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"inct.message.excelimport.requiredCol", request));// 导入数据中*项不可以为空，请检查！
			} else if (returnInt == 4) {
				modelMap.put("sign", "4");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage
						.getTipMessage(
								"inct.message.excelimport.writeTempTable.Fail",
								request));// 写入临时表失败！
			} else if (returnInt == 0) {
				modelMap.put("sign", "0");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.importfail", request));// 导入失败!
			}
			File file = new File(this.path + "\\" + filename + ".xls");
			file.delete();
		} else {
			modelMap.put("sign", "0");
			modelMap.put("statusCode", "300");
			modelMap.put("message", TipMessage.getTipMessage(
					"inct.message.excelimport.readExcelFile.Fail", request));// 文件读取失败!
		}
		return modelMap;
	}

	/**
	 * 将excel的人员类型变更发令数据导入到数据库临时表中
	 * 
	 * @Description: CHRS2.0 SI PROJECT
	 * @author PengHaixia
	 * @date 2014.10.10
	 * @version V1.0
	 */
	@SuppressWarnings("unchecked")
	public ModelMap importExcelReguEmpTransferOrderData(
			HttpServletRequest request, HttpServletResponse response,
			LinkedHashMap map, ModelMap modelMap) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String adminID = admin.getAdminID() == null ? "USERNO_"
				+ admin.getUserNo() : admin.getAdminID();
		ObjectBindUtil.getRequestParamData(request);
		this.filename = adminID;
		this.pathCpnyID = admin.getCpnyId();
		this.language = admin.getLanguage();
		if (this.processUploadFile(request, response)) {
			// excel数据读取
			ModelMap preparedMap = prepareForImportExcel(request, adminID, map);
			int returnInt = (Integer) preparedMap.get("token");
			if (returnInt == 1) {
				// excel数据读取成功，将数据导入 临时表
				LinkedHashMap paramMap = new LinkedHashMap();
				paramMap.put("IMP_EMPNO", admin.getEmpID());
				paramMap.put("CPNY_ID", admin.getCpnyId());
				try {
					// 删除临时表中当前用户旧数据
					this.excelUtilDao
							.deleteImportTempEmpTransferOrderTemp(paramMap);
					Map dataMap = new HashMap();
					dataMap = (Map) preparedMap.get("dataMap");
					// 为当前用户向临时表中插入数据
					this.excelUtilDao.insertExcelDataForTransferOrder(dataMap);
				} catch (Exception e) {
					e.printStackTrace();
					returnInt = 4;
				}
			}
			if (returnInt == 1) {
				modelMap.put("sign", "1");
				modelMap.put("statusCode", "200");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.importsuccess", request));// 导入成功
				modelMap.put("navTabId", "hr0516");
				modelMap
						.put("forwardUrl",
								"/hrm/transferOrder/viewReguEmpTransferOrderResultList?pageNum=1");
			} else if (returnInt == 2) {
				modelMap.put("sign", "2");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"inct.message.excelimport.nullRow", request));// 导入数据中有空行，请检查！
			} else if (returnInt == 3) {
				modelMap.put("sign", "3");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"inct.message.excelimport.requiredCol", request));// 导入数据中*项不可以为空，请检查！
			} else if (returnInt == 4) {
				modelMap.put("sign", "4");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage
						.getTipMessage(
								"inct.message.excelimport.writeTempTable.Fail",
								request));// 写入临时表失败！
			} else if (returnInt == 0) {
				modelMap.put("sign", "0");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.importfail", request));// 导入失败!
			}
			File file = new File(this.path + "\\" + filename + ".xls");
			file.delete();
		} else {
			modelMap.put("sign", "0");
			modelMap.put("statusCode", "300");
			modelMap.put("message", TipMessage.getTipMessage(
					"inct.message.excelimport.readExcelFile.Fail", request));// 文件读取失败!
		}
		return modelMap;
	}

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
			HttpServletResponse response, LinkedHashMap map, ModelMap modelMap,
			String forwardUrl, String navTabId) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String adminID = admin.getAdminID() == null ? "USERNO_"
				+ admin.getUserNo() : admin.getAdminID();
		ObjectBindUtil.getRequestParamData(request);
		String tableName = (String) map.get("tableName");
		this.filename = adminID;
		this.pathCpnyID = admin.getCpnyId();
		this.language = admin.getLanguage();
		if (this.processUploadFile(request)) {
			ModelMap preparedMap = prepareForImportExcel(request, adminID, map);
			int returnInt = (Integer) preparedMap.get("token");
			// 如果excel数据读取成功，将数据导入 临时表
			if (returnInt == 1) {
				LinkedHashMap paramMap = new LinkedHashMap();
				paramMap.put("TABLE_NAME", map.get("tableName"));
				paramMap.put("PERSONID", admin.getPersonId());
				// 删除临时表中当前用户旧数据
				if (!"AR_MAC_RECORDS_HTSV_EAT".equals(tableName)) {
				try {
					this.excelUtilDao.deleteImportTemp(paramMap);
				} catch (Exception e) {
					e.printStackTrace();
					returnInt = 0;
				}
				}
				Map dataMap = new HashMap();
				dataMap = (Map) preparedMap.get("dataMap");
				// 为当前用户向临时表中插入数据
				try {
					this.excelUtilDao.insertExcelDataForTransferOrder(dataMap);
				} catch (Exception e) {
					e.printStackTrace();
					returnInt = 0;
				}
			}
			if (returnInt == 0) {
				modelMap.put("sign", "0");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.importfail", request));// 导入失败!
			} else if (returnInt < 0) {
				modelMap.put("sign", "-1");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.writebyrule", request));// 调整事由有误，请按规则填写！
			} else if (returnInt == 2) {
				modelMap.put("sign", "2");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.existnull", request));// 表有空值或有空单元行，请检查！
			} else if (returnInt == 1) {
				modelMap.put("sign", "1");
				modelMap.put("statusCode", "200");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.importsuccess", request));// 导入成功
				modelMap.put("navTabId", navTabId);
				modelMap.put("forwardUrl", forwardUrl);
			} else if (returnInt == 5) {
				modelMap.put("sign", "-1");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.importfailcalladmin",
						request));// 更新此人上一条数据出错，请联系管理员
			} else if (returnInt == 6) {
				modelMap.put("sign", "-1");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"alert.message.pa.bonus.thisDataIsExist_add_fail",
						request));// 此数据已经存在，请核对数据后再进行导入!
			}
			File file = new File(this.path + "\\" + filename + ".xls");
			file.delete();
		} else {
			modelMap.put("sign", "0");
			modelMap.put("statusCode", "300");
			modelMap.put("message", TipMessage.getTipMessage(
					"ar.alert.message.excelimport.importfail", request));// 导入失败!
		}
		return modelMap;
	}
	/**
	 * 将excel的数据导入到数据库中 hr_evaluate_info表
	 * 
	 * @Description: CHRS2.0 SI PROJECT
	 * @author weizhengchen
	 * @date 2014.07.09
	 * @version V1.0
	 */
	@SuppressWarnings("unchecked")
	public ModelMap importEvaluateExcelData(HttpServletRequest request,
			HttpServletResponse response, LinkedHashMap map, ModelMap modelMap,
			String forwardUrl, String navTabId) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String adminID = admin.getAdminID() == null ? "USERNO_"
				+ admin.getUserNo() : admin.getAdminID();
				ObjectBindUtil.getRequestParamData(request);
				this.filename = adminID;
				this.pathCpnyID = admin.getCpnyId();
				this.language = admin.getLanguage();
				if (this.processUploadFile(request)) {
					ModelMap preparedMap = evaluateForImportExcel(request, adminID, map);
					int returnInt = (Integer) preparedMap.get("token");
					// 如果excel数据读取成功，将数据导入 临时表
					if (returnInt == 1) {
						LinkedHashMap paramMap = new LinkedHashMap();
						paramMap.put("TABLE_NAME", map.get("tableName"));
						paramMap.put("PERSONID", admin.getPersonId());
						// 删除临时表中当前用户旧数据
						/*try {
							this.excelUtilDao.deleteImportTemp(paramMap);
						} catch (Exception e) {
							e.printStackTrace();
							returnInt = 0;
						}*/
						Map dataMap = new HashMap();
						dataMap = (Map) preparedMap.get("dataMap");
						// 为当前用户向临时表中插入数据
						try {
							this.excelUtilDao.insertExcelDataForTransferOrder(dataMap);
						} catch (Exception e) {
							e.printStackTrace();
							returnInt = 0;
						}
					}
					if (returnInt == 0) {
						modelMap.put("sign", "0");
						modelMap.put("statusCode", "300");
						modelMap.put("message", TipMessage.getTipMessage(
								"ar.alert.message.excelimport.importfail", request));// 导入失败!
					} else if (returnInt < 0) {
						modelMap.put("sign", "-1");
						modelMap.put("statusCode", "300");
						modelMap.put("message", TipMessage.getTipMessage(
								"ar.alert.message.excelimport.writebyrule", request));// 调整事由有误，请按规则填写！
					} else if (returnInt == 2) {
						modelMap.put("sign", "2");
						modelMap.put("statusCode", "300");
						modelMap.put("message", TipMessage.getTipMessage(
								"ar.alert.message.excelimport.existnull", request));// 表有空值或有空单元行，请检查！
					} else if (returnInt == 1) {
						modelMap.put("sign", "1");
						modelMap.put("statusCode", "200");
						modelMap.put("message", TipMessage.getTipMessage(
								"ar.alert.message.excelimport.importsuccess", request));// 导入成功
						modelMap.put("navTabId", navTabId);
						modelMap.put("forwardUrl", forwardUrl);
					} else if (returnInt == 5) {
						modelMap.put("sign", "-1");
						modelMap.put("statusCode", "300");
						modelMap.put("message", TipMessage.getTipMessage(
								"ar.alert.message.excelimport.importfailcalladmin",
								request));// 更新此人上一条数据出错，请联系管理员
					} else if (returnInt == 6) {
						modelMap.put("sign", "-1");
						modelMap.put("statusCode", "300");
						modelMap.put("message", TipMessage.getTipMessage(
								"alert.message.pa.bonus.thisDataIsExist_add_fail",
								request));// 此数据已经存在，请核对数据后再进行导入!
					}
					File file = new File(this.path + "\\" + filename + ".xls");
					file.delete();
				} else {
					modelMap.put("sign", "0");
					modelMap.put("statusCode", "300");
					modelMap.put("message", TipMessage.getTipMessage(
							"ar.alert.message.excelimport.importfail", request));// 导入失败!
				}
				return modelMap;
	}
	
	@Override
	public void importDataTest(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> param)
			throws Exception {
		// generate a key for temp table
		String timeStr = new SimpleDateFormat("kkmmss").format(Calendar
				.getInstance().getTime());
		String randomStr = String.format("%02d", (int) (Math.random() * 100));
		final String tempId = timeStr + randomStr;
		param.put("TEMP_ID", tempId);

		AdminBean admin = (AdminBean) param.get("admin");

		String adminID = admin.getAdminID() == null ? "USERNO_"
				+ admin.getUserNo() : admin.getAdminID();

		this.filename = adminID;
		this.pathCpnyID = admin.getCpnyId();
		this.language = admin.getLanguage();

		if (!this.processUploadFile(request, response)) {
			throw new Exception();
		}

		int k = path.indexOf("\\");
		String server = "win";
		if (k < 0) { // WebServer的os为unix/linux
			server = "u/l";
		}
		File file = null;
		if ("win".equals(server)) {
			file = new File(path + "\\" + filename + ".xls");
		} else {
			file = new File(path + "/" + filename + ".xls");
		}

		Workbook workbook = Workbook.getWorkbook(file);
		Sheet sheet = workbook.getSheet(0);

		Cell[] headers = sheet.getRow(0);

		int columnCount = headers.length;
		int rowCount = sheet.getRows();

		List<Object> list = new ArrayList<Object>();

		for (int i = 1; i < rowCount; i++) {
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("CPNY_ID", pathCpnyID);
			data.put("TEMP_ID", tempId);

			for (int j = 0; j < columnCount; j++) {
				String value = sheet.getCell(j, i).getContents();
				data.put("CELL" + j, value);
			}

			list.add(data);
		}

		boolean isCreated = false;

		try {
			this.excelUtilDao.createTempTable(param);
			isCreated = true;

			long startTime = System.nanoTime();

			this.excelUtilDao.insertParamTempBatch(list);

			long endTime = System.nanoTime();
			long lTime = endTime - startTime;
			System.out.println("INSERT Temp TIME : " + lTime / 1000000.0
					+ "(ms)");

			startTime = System.nanoTime();

			this.excelUtilDao.changeParamDataBatch(param);

			endTime = System.nanoTime();
			lTime = endTime - startTime;
			System.out.println("BATCH TIME : " + lTime / 1000000.0 + "(ms)");

			if (file.exists()) {
				file.delete();
			}
		} finally {
			if (isCreated) {
				this.excelUtilDao.dropTempTable(param);
			}
		}

	}

	@Override
	public void imporExceltDataTest(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> param)
			throws Exception {

		AdminBean admin = (AdminBean) param.get("admin");

		String adminID = admin.getAdminID() == null ? "USERNO_"
				+ admin.getUserNo() : admin.getAdminID();

		String uploadBy = (String) param.get("UPLOAD_BY");
		this.filename = adminID;
		this.pathCpnyID = admin.getCpnyId();
		this.language = admin.getLanguage();

		if (!this.processUploadFile(request, response)) {
			throw new Exception();
		}

		int k = path.indexOf("\\");
		String server = "win";
		if (k < 0) { // WebServer的os为unix/linux
			server = "u/l";
		}
		File file = null;
		if ("win".equals(server)) {
			file = new File(path + "\\" + filename + ".xls");
		} else {
			file = new File(path + "/" + filename + ".xls");
		}

		Workbook workbook = Workbook.getWorkbook(file);
		Sheet sheet = workbook.getSheet(0);

		Cell[] headers = sheet.getRow(0);

		int columnCount = headers.length;
		int rowCount = sheet.getRows();

		List<Object> list = new ArrayList<Object>();

		for (int i = 1; i < rowCount; i++) {
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("CPNY_ID", pathCpnyID);
			data.put("UPLOAD_BY", uploadBy);
			data.put("LINE_ID", i);

			for (int j = 0; j < columnCount; j++) {
				String value = sheet.getCell(j, i).getContents();
				data.put("CELL" + j, value);
			}

			list.add(data);
		}

		LinkedHashMap paramMap = new LinkedHashMap();
		paramMap.put("TABLE_NAME", "PA_ACCOUNT_TEMP");
		paramMap.put("PERSONID", uploadBy);

		long startTime = System.nanoTime();

		// 删除临时表中当前用户旧数据
		this.excelUtilDao.deleteImportTemp(paramMap);

		long endTime = System.nanoTime();
		long lTime = endTime - startTime;
		System.out.println("DELETE TIME : " + lTime / 1000000.0 + "(ms)");

		startTime = System.nanoTime();

		this.excelUtilDao.insertPaAccountTempBatch(list);

		endTime = System.nanoTime();
		lTime = endTime - startTime;
		System.out.println("INSERT TIME : " + lTime / 1000000.0 + "(ms)");

		if (file.exists()) {
			file.delete();
		}

	}

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
			HttpServletResponse response, LinkedHashMap map, ModelMap modelMap) {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String adminID = admin.getAdminID() == null ? "USERNO_"
				+ admin.getUserNo() : admin.getAdminID();
		ObjectBindUtil.getRequestParamData(request);
		this.filename = adminID;
		this.pathCpnyID = admin.getCpnyId();
		this.language = admin.getLanguage();
		if (this.processUploadFile(request)) {
			ModelMap preparedMap = prepareForImportExcel(request, adminID, map);
			int returnInt = (Integer) preparedMap.get("token");
			// 如果excel数据读取成功，将数据导入 临时表
			if (returnInt == 1) {
				LinkedHashMap paramMap = new LinkedHashMap();
				paramMap.put("TABLE_NAME", map.get("tableName"));
				paramMap.put("PERSONID", admin.getPersonId());
				Map dataMap = new HashMap();
				dataMap = (Map) preparedMap.get("dataMap");
				// 为当前用户向临时表中插入数据
				try {
					this.excelUtilDao.insertPaiQianDiExcelDataForTransferOrder(dataMap);
				} catch (Exception e) {
					e.printStackTrace();
					returnInt = 0;
				}
			}
			if (returnInt == 0) {
				modelMap.put("sign", "0");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.importfail", request));// 导入失败!
			} else if (returnInt < 0) {
				modelMap.put("sign", "-1");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.writebyrule", request));// 调整事由有误，请按规则填写！
			} else if (returnInt == 2) {
				modelMap.put("sign", "2");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.existnull", request));// 表有空值或有空单元行，请检查！
			} else if (returnInt == 1) {
				modelMap.put("sign", "1");
				modelMap.put("statusCode", "200");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.importsuccess", request));// 导入成功
			} else if (returnInt == 5) {
				modelMap.put("sign", "-1");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"ar.alert.message.excelimport.importfailcalladmin",
						request));// 更新此人上一条数据出错，请联系管理员
			} else if (returnInt == 6) {
				modelMap.put("sign", "-1");
				modelMap.put("statusCode", "300");
				modelMap.put("message", TipMessage.getTipMessage(
						"alert.message.pa.bonus.thisDataIsExist_add_fail",
						request));// 此数据已经存在，请核对数据后再进行导入!
			}
			File file = new File(this.path + "\\" + filename + ".xls");
			file.delete();
		} else {
			modelMap.put("sign", "0");
			modelMap.put("statusCode", "300");
			modelMap.put("message", TipMessage.getTipMessage(
					"ar.alert.message.excelimport.importfail", request));// 导入失败!
		}
		return modelMap;
	}

	@SuppressWarnings("deprecation")
	public boolean processUploadFile(HttpServletRequest request) {
		boolean b = true;

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// 设置文件在服务器上的路径
		this.path = request.getRealPath("");
		int i = path.indexOf("\\");
		String server = "win";
		if (i < 0) { // WebServer的os为unix/linux
			server = "u/l";
		}
		if ("win".equals(server)) {
			path = path + "\\resources\\temp\\files\\" + pathCpnyID + "\\";
		} else {
			path = path + "/resources/temp/files/" + pathCpnyID;
		}
		try {
			MultipartHttpServletRequest multipartRequest = null;
			CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
			if (!(request instanceof MultipartHttpServletRequest)
					&& multipartResolver.isMultipart(request)) {
				try {
					multipartResolver.setMaxUploadSize(20971520);
					multipartRequest = multipartResolver
							.resolveMultipart(request);
				} catch (MaxUploadSizeExceededException e) {
					return b;
				}
			} else if (request instanceof MultipartHttpServletRequest) {
				multipartRequest = (MultipartHttpServletRequest) request;
			} else {
				return b;
			}
			/** 得到图片保存目录的真实路径 **/
			String logoRealPathDir = path;
			/** 根据真实路径创建目录 **/
			File logoSaveFile = new File(logoRealPathDir);
			if (!logoSaveFile.exists()){
				logoSaveFile.mkdirs();
			}
			/** 页面控件的文件流 **/
			MultipartFile multipartFile = multipartRequest.getFile("filename");
			/**拼成完整的文件保存路径加文件**/
			/* Always save as ".xls": every reader of this temp file (prepareForImportExcel,
			 * impDataBase, etc.) hardcodes filename + ".xls" when locating it, while
			 * Workbook.getWorkbook() detects the real .xls/.xlsx format from the file's
			 * content, not its extension - so keeping the saved name in sync with what
			 * those readers look for (regardless of the extension the user actually
			 * uploaded) is what lets .xlsx uploads be found and parsed at all. */
			String fileName = logoRealPathDir + File.separator + admin.getAdminID() + ".xls";
			File file = new File(fileName);
			multipartFile.transferTo(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}
}