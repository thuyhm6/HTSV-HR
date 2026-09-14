package com.ait.disc.action;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import sun.misc.BASE64Encoder;

import com.ait.ar.service.ArCardRecordSer;
import com.ait.ar.service.ArDetailSer;
import com.ait.ar.service.CompanyCalendarSer;
import com.ait.ar.service.ItemsSer;
import com.ait.ar.service.impl.ArDetailSerImp;
import com.ait.disc.service.RetrieveSqlMasterSer;
import com.ait.disc.service.SqlParamSer;
import com.ait.ess.service.EssDeptEmpAttSer;
import com.ait.ess.service.InfoApplyLeaveSer;
import com.ait.ess.service.InfoApplySer;
import com.ait.ess.service.TempEmpSer;
import com.ait.ess.service.ViewDeptPerSer;
import com.ait.hrm.service.EmpInfoSer;
import com.ait.pa.service.excelUtil.ExcelUtilSer;
import com.ait.pa.service.imp.excelUtil.ExcelTemplateUtil;
import com.ait.pa.service.workManagement.viewPaParamSer;
import com.ait.report.pa.service.PaReportSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.config.ConfigurationException;
import com.ait.web.util.AuthorityUtil;
import com.ait.web.util.FileAnsiToUTF8;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.ReadFile;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;
import com.ait.web.util.TempltUtil;
import com.ait.web.util.UiUtil;
import com.ait.web.util.UserConfiguration;

/**
 * @ClassName: RetrieveNoticeListCtroller
 * @Description: 自动下载excel 控制类
 * @author 孙鹏
 * @date 2014年10月21日 下午5:48:11
 * 
 */
@Controller
@RequestMapping(value = "/disc/autoExcel")
public class RetrieveMasterListCtroller {

	Logger logger = Logger.getLogger(RetrieveMasterListCtroller.class);
	@Autowired
	private ToolMenuSer toolMenuSer;
	@Autowired
	private RetrieveSqlMasterSer retrieveSqlMasterSer;
	@Autowired
	private SqlParamSer sqlparamser;
	@Autowired
	private ExcelUtilSer excelUtilSer;
	@Autowired
	private ArDetailSerImp arDetailSerImp;
	@Autowired
	private EmpInfoSer empInfoSer;
	@Autowired
	private ViewDeptPerSer ViewDeptPer;
	@Autowired
	private AuthorityUtil authorityUtil;
	@Autowired
	private ItemsSer itemsSer;
	@Autowired
	private InfoApplyLeaveSer infoApplyLeaveSer;
	@Autowired
	private InfoApplySer infoApplySer;	
	@Autowired
	private PaReportSer paReportSer;
	@Autowired
	private TempEmpSer tempEmpSer;
	@Autowired
	private ViewDeptPerSer viewDeptPerSer;
	@Autowired
	private ArCardRecordSer arCardRecordSer;
	@Autowired
	private CompanyCalendarSer companyCalendarSer;
	@Autowired
	private viewPaParamSer viewPaParamSer;
	@Autowired
	private EssDeptEmpAttSer essDeptEmpAttSer;
	@Autowired
	private ArDetailSer arDetailSer;
	
	
	@RequestMapping(value = "/viewRetrieveNoticeList")
	public ModelAndView viewretrieveNoticeList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "278686"));
		return new ModelAndView("/disc/autoExcel/viewRetrieveNoticeList",
				modelMap);
	}

	/**
	 * @Title: retrieveSqlMasterList
	 * @Description: excel报表列表
	 * @param @param request
	 * @param @param response
	 * @param @param modelMap
	 * @param @return
	 * @param @throws Exception
	 * @return ModelAndView
	 * @throws
	 */

	@RequestMapping(value = "/viewRetrieveSqlMasterList")
	public ModelAndView viewretrieveSqlMasterList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "298976"));
		String firstFlag = request.getParameter("firstFlag");
		String PGM_NMurl = request.getParameter("PGM_NMurl");
		modelMap.put("POWER_FOR", request.getParameter("POWER_FOR"));

		modelMap.put("PGM_NMurl", PGM_NMurl);
		//
		List showList = this.retrieveSqlMasterSer.getSqlMasterList(request);
		// Collections.reverse(showList);
		int isParamDataCnt = this.retrieveSqlMasterSer
				.getSqlMasterListCnt(request);
		request.setAttribute("showList", showList);
		modelMap.put("showlist", showList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, isParamDataCnt);

		// 以下为获取法人信息
		modelMap.put("positionList", empInfoSer.getPositionList(request));
		modelMap.put("companyList", empInfoSer.getCompanyList(request));
		modelMap.put("authority", authorityUtil
				.isSuperUser(admin.getPersonId()));
		modelMap
				.put("roleidlist", authorityUtil.getRoleId(admin.getPersonId()));
		modelMap.put("defaultCpny",
				request.getParameter("defaultCpny") == null ? admin.getCpnyId()
						: request.getParameter("defaultCpny"));
		return new ModelAndView("/disc/autoExcel/viewRetrieveSqlMasterList",
				modelMap);
	}

	/**
	 * @Title: createSqlMaster
	 * @Description: TODO 跳转创建excel报表
	 * @param @param request
	 * @param @param response
	 * @param @param modelMap
	 * @param @return
	 * @param @throws Exception
	 * @return ModelAndView
	 * @throws
	 */
	@RequestMapping(value = "/createSqlMaster")
	public ModelAndView createSqlMaster(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "278686"));
		modelMap.put("CPNY_ID", admin.getCpnyId());// 实际是为法人
		modelMap.put("UPDT_USER", admin.getEmpID());// 用户编码
		modelMap.put("UPDT_NN", admin.getLocalName());// 用户名
		String pgm = request.getParameter("PGM_NMurl");
		modelMap.put("PGM_NMurl", pgm);
		return new ModelAndView("/disc/autoExcel/createSqlMaster", modelMap);
	}

	/**
	 * @Title: updateSqlMasterTo
	 * @Description: TODO 跳转sql报表更新页面
	 * @param @param request
	 * @param @param response
	 * @param @param modelMap
	 * @param @return
	 * @param @throws Exception
	 * @return ModelAndView
	 * @throws
	 */
	@RequestMapping(value = "/updateSqlMasterTo")
	public ModelAndView updateSqlMasterTo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		// List paramlist=this.retrieveSqlMasterSer.getSqlParamList(request);
		Map master = (Map) this.retrieveSqlMasterSer.getSqlMaster(request);
		String PGM_NMurl = request.getParameter("PGM_NMurl");
		master.put("PGM_NMurl", PGM_NMurl);

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "278686"));
		modelMap.put("CPNY_ID", admin.getCpnyId());// 实际是为法人
		modelMap.put("UPDT_USER", admin.getEmpID());// 用户编码
		modelMap.put("UPDT_NN", admin.getLocalName());// 用户名
		modelMap.put("master", master);
		return new ModelAndView("/disc/autoExcel/updateSqlMaster", modelMap);
	}

	/**
	 * @Title: insertSqlMaster
	 * @Description: TODO 插入sql报表主要信息
	 * @param @param request
	 * @param @param response
	 * @param @param modelMap
	 * @param @return
	 * @param @throws Exception
	 * @return Map
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/addSqlMaster")
	public Map insertSqlMaster(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		int calFlag = this.retrieveSqlMasterSer.insertSqlMaster(request);
		if (calFlag > 0) {// calflage是插入数据的主键id
			map.put("statusCode", "200");
			map.put("message", "新增成功");
		} else {
			map.put("statusCode", "300");
			map.put("message", "新增出错 ");
		}
		return map;
	}

	/**
	 * @Title: updateSqlMaster
	 * @Description: TODO 报表更新页面
	 * @param @param request
	 * @param @param response
	 * @param @param modelMap
	 * @param @return
	 * @param @throws Exception
	 * @return Map
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateSqlMaster")
	@ResponseBody
	public Map updateSqlMaster(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		// Map calFlag=this.deleteSqlMaster(request, response, modelMap);
		// if(calFlag.get("statusCode").equals("200")){
		// calFlag=this.insertSqlMaster(request, response, modelMap);
		// }

		int calFlag = this.retrieveSqlMasterSer.updateSqlMaster(request);
		// calFlag=this.retrieveSqlMasterSer.updateSqlMaster(request);
		if (calFlag > 0) {
			map.put("statusCode", "200");
			map.put("message", "修改成功");
		} else {
			map.put("statusCode", "300");
			map.put("message", "修改出错 ");
		}
		return map;
	}

	/**
	 * @Title: deleteSqlMaster
	 * @Description: TODO
	 * @param @param request
	 * @param @param response
	 * @param @param modelMap
	 * @param @return
	 * @param @throws Exception
	 * @return Map
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deleteSqlMaster")
	@ResponseBody
	public Map deleteSqlMaster(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();

		int calFlag = this.retrieveSqlMasterSer.deleteSqlMaster(request);
		if (calFlag == 1) {
			// map.put("navTabId", "disc0101");
			map.put("statusCode", "200");
			map.put("message", "删除成功");
			// map.put("callbackType", "closeCurrent");
		} else {
			map.put("statusCode", "300");
			map.put("message",
					"delete deleteCPFBaseManagement information Exception. ");
		}
		return map;
	}

	/**
	 * @Title: viewUpdateSqlParam
	 * @Description: TODO 跳转报表参数更新页面
	 * @param @param request
	 * @param @param response
	 * @param @param modelMap
	 * @param @return
	 * @param @throws Exception
	 * @return ModelAndView
	 * @throws
	 */
	@RequestMapping(value = "/viewUpdateSqlParamList")
	public ModelAndView viewUpdateSqlParamList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List paramlist = this.retrieveSqlMasterSer.getSqlParamList(request);
		Map mater = (Map) this.retrieveSqlMasterSer.getSqlMaster(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "278686"));
		modelMap.put("CPNY_ID", admin.getCpnyId());// 实际是为法人
		modelMap.put("UPDT_USER", admin.getEmpID());// 用户编码
		modelMap.put("UPDT_NN", admin.getLocalName());// 用户名
		modelMap.put("paramlist", paramlist);
		modelMap.put("master", mater);
		return new ModelAndView("/disc/autoExcel/updateSqlParamList", modelMap);
	}

	/**
	 * @Title: updateSqlParamList
	 * @Description: TODO 报表参数的更新
	 * @param @param request
	 * @param @param response
	 * @param @param modelMap
	 * @param @return
	 * @param @throws Exception
	 * @return Map
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/updateSqlParamList")
	public Map updateSqlParamList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		int calFlag = this.retrieveSqlMasterSer.updateSqlParam(request);
		if (calFlag > 0) {
			calFlag = this.sqlparamser.ParamToJsp(request);
		}
		if (calFlag > 0) {// calflage是插入数据的主键id
			map.put("statusCode", "200");
			map.put("message", "更新成功");
		} else {
			map.put("statusCode", "300");
			map.put("message", "更新参数出错 ");
		}
		return map;
	}

	/**
	 * @throws ConfigurationException
	 * @Title: retrieveParamDesc 处理改变参数时随即改变参数描述
	 * @Description: TODO
	 * @param @param request
	 * @param @param response
	 * @param @param modelMap
	 * @return void
	 * @throws
	 */

	@RequestMapping(value = "/retrieveParamDesc")
	@ResponseBody
	public Map retrieveParamDesc(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap)
			throws ConfigurationException {
		String paramDesc = "";

		HttpSession session = request.getSession();
		UserConfiguration config = UserConfiguration
				.getInstance("/typecode.properties");
		InputStream in;
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		if (!paramMap.get("SQL_PARAM_TP").equals("")) {
			String paramType = "report.parameter.type."
					+ paramMap.get("SQL_PARAM_TP");
			// System.out.println(config.getString(paramType));
			String cardFilePath = config.getString(paramType);
			List nameList = ReadFile.readTxtFileName(cardFilePath);
			String paramValue = config.getString(paramType);
			paramDesc = paramValue.replaceAll("@", (String) paramMap
					.get("param"));
			String pgm = (String) paramMap.get("PGM_NMurl");
			// if(paramMap.get("SQL_PARAM_TP").equals("job_tp")||paramMap.get("SQL_PARAM_TP").equals("job_tp_gr")){
			// paramDesc = paramDesc.replaceAll("#","hr");
			// }
			if (pgm.equals("ALL")) {
				paramDesc = paramDesc.replaceAll("#", "hr");
			} else if (pgm.equals("PAY") || pgm.endsWith("WEL")) {
				paramDesc = paramDesc.replaceAll("#", "pa");
			} else if (pgm.equals("ATT")) {
				paramDesc = paramDesc.replaceAll("#", "ar");
			} else if (pgm.equals("EMP")) {
				paramDesc = paramDesc.replaceAll("#", "hr");
			}

		} else {
			paramDesc = "";
		}

		// request.setAttribute("reload",
		// "parent.createSqlMaster.SQL_PARAM_TP_DESC["
		// + paramMap.get("paramIndex") + "].value='"
		// + paramDesc + "';");
		Map<String, Object> map = new HashMap<String, Object>();
		List result = new ArrayList();
		result.add(paramDesc);
		map.put("statusCode", "200");
		map.put("result", result);

		return map;

	}

	/**
	 * @Title: viewUpdateSqlParamList
	 * @Description: TODO 跳转数据导出页面
	 * @param @param request
	 * @param @param response
	 * @param @param modelMap
	 * @param @return
	 * @param @throws Exception
	 * @return ModelAndView
	 * @throws
	 */
	@RequestMapping(value = "/runSql")
	@SuppressWarnings("unchecked")
	@ResponseBody
	public ModelAndView runSql(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		// List paramlist=this.retrieveSqlMasterSer.getSqlParamList(request);
		Map mater = (Map) this.retrieveSqlMasterSer.getSqlMaster(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		modelMap.put("toolbarInfo",
				request.getParameter("menuNo") != null ? toolMenuSer
						.getToolMenu(request) : toolMenuSer.getToolMenuForNo(
						request, "278686"));
		modelMap.put("CPNY_ID", admin.getCpnyId());// 实际是为法人
		modelMap.put("UPDT_USER", admin.getEmpID());// 用户编码
		modelMap.put("UPDT_NN", admin.getLocalName());// 用户名
		// modelMap.put("paramlist", paramlist);
		modelMap.put("master", mater);
		String id = request.getParameter("SQL_SEQ");
		// 获取地址并检测是否存在jsp页面
		UserConfiguration config = UserConfiguration
				.getInstance("/typecode.properties");
		String configDir = "";
		boolean isWindows = isWindowsOS(); // 判断是否是windows系统
		if (isWindows) {
			configDir = config.getString("sql.param.jsp.folderWin");
		} else { // 如果是Linux系统
			configDir = config.getString("sql.param.jsp.folderUnix");
		}
		String p2 = this.getClass().getResource("").getFile();
		String file = p2.substring(0, p2.indexOf("classes"));
		// OutputStreamWriter osw= new OutputStreamWriter( new
		// FileOutputStream(file+configDir+"\\" + fileName + ".jsp"),"UTF-8");
		String fileisok = (file + configDir + id + ".jsp");
		File os = new File(fileisok);
		if (!os.exists()) {
			return new ModelAndView("/disc/sqlparam/errorForexcel", modelMap);
		}
		return new ModelAndView("/disc/sqlparam/" + id, modelMap);
	}

	public static boolean isWindowsOS() {
		boolean isWindowsOS = false;
		String osName = System.getProperty("os.name");
		if (osName.toLowerCase().indexOf("windows") > -1) {
			isWindowsOS = true;
		}
		return isWindowsOS;
	}

	/**
	 * @throws Exception
	 * @return
	 * @Title: runForSqlWrite
	 * @Description: TODO 生成excel
	 * @param @param request
	 * @param @param response
	 * @param @param modelMap
	 * @param @return
	 * @param @throws Exception
	 * @return ModelAndView
	 * @throws
	 */
	@RequestMapping(value = "/exportLOtImportExcel")
	@SuppressWarnings("unchecked")
	public void runForSqlWrite(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		LinkedHashMap data = ObjectBindUtil.getRequestParamData(request);
		String jspname = (String) data.get("SQL_SEQMEAN");
		LinkedHashMap searchMap = ObjectBindUtil.getRequestParamData(request);
		// 针对129
		if (!"".equals(jspname) && jspname != null) {
			if ("129".equals(jspname)) {
				String seach_APPLY_CODE = request
						.getParameter("seach_APPLY_CODE");
				String ITEM_NO = "";
				if (seach_APPLY_CODE != null && !"".equals(seach_APPLY_CODE)) {
					ITEM_NO = arDetailSerImp.getItemNoOnApplyCode2(searchMap);
					searchMap.put("ITEM_NO", ITEM_NO);
				} else {
					List itemList = itemsSer.getItemParamList2(request);
					for (Iterator iterator = itemList.iterator(); iterator
							.hasNext();) {
						LinkedHashMap itemObject = (LinkedHashMap) iterator
								.next();
						String ITEM_NOPER = itemObject.get("ITEM_NO")
								.toString();
						ITEM_NO += ITEM_NOPER + ",";
					}
					ITEM_NO = ITEM_NO.substring(0, ITEM_NO.length() - 1);
					searchMap.put("ITEM_NO", ITEM_NO);
				}
			}
		}
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		ModelMap datamap = this.sqlparamser.writeExcel(request, response,
				modelMap);
		datamap.put("searchMap", searchMap);
		// excel报表名称
		String name = jspname + "_" + (String) datamap.get("SQL_NM");
		// 报表模板名称
		String tempName = "exl_autoExcel";
		if (datamap.get("special") != null) {
			tempName = datamap.get("SQL_SEQ").toString();
		}
		// 报表模板路径
		String webPath = request.getRealPath("/").replace("\\", "/");
		String templateFileName = webPath;
		String destFileName = webPath;
		templateFileName += "/resources/template/report/" + tempName + ".xls";
		destFileName += "/resources/template/report/" + tempName + "_out.xlsx";
		List aliasValueList = (List) datamap.get("ValueList");
		List aliasNameList = (List) datamap.get("NameList");

		/*if ("138".equals(jspname)) {
			String seach_ITEM_NO = request.getParameter("seach_ITEM_NO");
			String ITEM_NO = "";
			if (seach_ITEM_NO != null && !"".equals(seach_ITEM_NO)) {
				searchMap.put("ITEM_NO", seach_ITEM_NO);
			} else {
				List itemList = itemsSer.getItemParamList2(request);
				for (Iterator iterator = itemList.iterator(); iterator
						.hasNext();) {
					LinkedHashMap itemObject = (LinkedHashMap) iterator.next();
					String ITEM_NOPER = itemObject.get("ITEM_NO").toString();
					ITEM_NO += ITEM_NOPER + ",";
				}
				ITEM_NO = ITEM_NO.substring(0, ITEM_NO.length() - 1);
				searchMap.put("ITEM_NO", ITEM_NO);
				datamap.put("leaveCoordList", infoApplyLeaveSer
						.getCoordLeaveInfoList(request));
			}
		}*/
		// 针对213
		if ("213".equals(jspname)) {
			datamap.put("personInfo", empInfoSer.getPersonalInfoByPid(request));
			datamap.put("hrEmergencyAddressList", empInfoSer
					.gethrEmergencyAddressList(request));
			// 家庭信息
			datamap.put("FamilyList", empInfoSer.gethrFamilyList(request));
			// 学历事项
			datamap.put("hrEducationMatterList", empInfoSer
					.viewEducationMatter(request));
			// 工作经历
			datamap.put("hrExperiencePointList", empInfoSer
					.getExperiencePointList(request));
			// 外语能力
			datamap.put("viewForeignLanguage", empInfoSer
					.viewForeignLanguage(request));
			// 资格事项viewBidMatter
			datamap.put("viewBidMatter", empInfoSer.viewBidMatter(request));
			//银行账号
			datamap.put("accountInfo", empInfoSer.getAccountInfo(request));
			try {
				InputStream is = new FileInputStream(templateFileName);
				Workbook workBook = ExcelTemplateUtil.transformXLS(is, datamap);
				Sheet sheet = workBook.getSheetAt(0);
				String path = request.getSession().getServletContext()
						.getRealPath("/");
				Drawing<?> patriarch = sheet.createDrawingPatriarch();
				CreationHelper anchorHelper = workBook.getCreationHelper();
				for (int i = 0; i < aliasValueList.size(); i++) {
					HashMap valueMap = (HashMap) aliasValueList.get(i);
					// 将图片以字节流的方式输入输出
					String picture = StringUtil.checkNull(valueMap
							.get("PHOTO_PATH"));
					File picFile = new File(path + "/" + picture);
					if (picture != null && !"".equals(picture)) {
						if (picFile.exists()) {
							ByteArrayOutputStream bos = new ByteArrayOutputStream();
							BufferedImage BufferImg = ImageIO.read(picFile);
							ImageIO.write(BufferImg, "JPEG", bos);
							ClientAnchor anchor = anchorHelper.createClientAnchor();
							anchor.setDx1(0); anchor.setDy1(0); anchor.setDx2(1023); anchor.setDy2(255);
							anchor.setCol1(9); anchor.setRow1(3); anchor.setCol2(11); anchor.setRow2(9);
							patriarch.createPicture(anchor, workBook
									.addPicture(bos.toByteArray(),
											Workbook.PICTURE_TYPE_JPEG));
						}
					}
				}
				OutputStream os = new FileOutputStream(destFileName);
				workBook.write(os);
				is.close();
				os.flush();
				os.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if ("232".equals(jspname)) {
			datamap.put("personInfo", empInfoSer.getPersonalInfoByPid(request));
			// 紧急联系人
			datamap.put("hrEmergencyAddressList", empInfoSer
					.gethrEmergencyAddressList(request));
			// 家庭信息
			// datamap.put("FamilyList", empInfoSer.gethrFamilyList(request));
			// 学历事项
			datamap.put("hrEducationMatterList", empInfoSer
					.viewEducationMatter(request));
			// 工作经历
			datamap.put("hrExperiencePointList", empInfoSer
					.getExperiencePointList(request));
			// 外语能力
			datamap.put("viewForeignLanguage", empInfoSer
					.viewForeignLanguage(request));
			// 资格事项viewBidMatter
			datamap.put("viewBidMatter", empInfoSer.viewBidMatter(request));
			// 发令事项
			datamap.put("hrStartPointList", empInfoSer
					.getStartPointList(request));
			// 表彰事项
			datamap.put("viewRecognition", empInfoSer.viewRecognition(request));
			// 惩戒事项
			datamap.put("viewPunishment", empInfoSer.viewPunishment(request));
			// 培训事项
			datamap.put("viewTrain", empInfoSer.viewSingleTrain(request));

			try {
				InputStream is = new FileInputStream(templateFileName);
				Workbook workBook = ExcelTemplateUtil.transformXLS(is, datamap);
				Sheet sheet = workBook.getSheetAt(0);
				String path = request.getSession().getServletContext()
						.getRealPath("/");
				Drawing<?> patriarch = sheet.createDrawingPatriarch();
				CreationHelper anchorHelper = workBook.getCreationHelper();
				for (int i = 0; i < aliasValueList.size(); i++) {
					HashMap valueMap = (HashMap) aliasValueList.get(i);
					// 将图片以字节流的方式输入输出
					String picture = StringUtil.checkNull(valueMap
							.get("PHOTO_PATH"));
					File picFile = new File(path + "/" + picture);
					if (picture != null && !"".equals(picture)) {
						if (picFile.exists()) {
							ByteArrayOutputStream bos = new ByteArrayOutputStream();
							BufferedImage BufferImg = ImageIO.read(picFile);
							ImageIO.write(BufferImg, "JPEG", bos);
							ClientAnchor anchor = anchorHelper.createClientAnchor();
							anchor.setDx1(0); anchor.setDy1(0); anchor.setDx2(1023); anchor.setDy2(255);
							anchor.setCol1(9); anchor.setRow1(3); anchor.setCol2(11); anchor.setRow2(9);
							patriarch.createPicture(anchor, workBook
									.addPicture(bos.toByteArray(),
											Workbook.PICTURE_TYPE_JPEG));
						}
					}
				}
				OutputStream os = new FileOutputStream(destFileName);
				workBook.write(os);
				is.close();
				os.flush();
				os.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if ("27".equals(jspname)) {
			List sqlResult = empInfoSer.employeeSearchResultsTanchu(request);
			
			if (null == sqlResult || sqlResult.size() == 0 || sqlResult.isEmpty()) {
				Map error = new HashMap();
				error.put("error", "No data or parameter input errors, please re-enter");//无数据或者参数输入错误，请重新输入
				sqlResult.add(error);
			} else if (sqlResult.size() > 65000) {
				Map error = new HashMap();
				error.put("error", "The data is over 65,000 rows, please reselect the range.");//数据超过6万5千行，请重新选择范围。
				sqlResult.clear();
				sqlResult.add(error);
			}
			Map indexMap = (Map) sqlResult.get(0);
			// 如果调用生成模板的excel方法，那么需要循环，如果是带密码的excel方法，数据库查出的结果就可以了。
			// dataList=sqlResult;//在这里 把数据库查出的数据直接给了生成excel方法
			Set set = indexMap.keySet();// 用接口实例接口
			Iterator iter = set.iterator();
			List aliasList = new ArrayList();
			while (iter.hasNext()) {// 遍历二次,速度慢
				String nameTemp = (String) iter.next();
				if(!"PERSON_ID".equals(nameTemp)){
					aliasList.add(nameTemp);
				}
			}
			modelMap.put("aliasList", aliasList);
			modelMap.put("ValueList", sqlResult);
			// execl导出处理
			try {
				ExcelTemplateUtil.transformXLS(templateFileName, datamap,
						destFileName);
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else if ("28".equals(jspname)) {
			List arShiftGroupList = this.essDeptEmpAttSer.viewArShiftGroupList(request);
			datamap.put("arShiftGroupList", arShiftGroupList);
			// execl导出处理
			try {
				ExcelTemplateUtil.transformXLS(templateFileName, datamap,
						destFileName);
			} catch (Exception e) {
				e.printStackTrace();
			}
	    } 
		/*else if ("226".equals(jspname)) {
			datamap.put("StatisticsBureau", empInfoSer
			.getStatisticsBureau(request));
			datamap.put("PostStores", empInfoSer.getPostStores(request));
			datamap.put("LaborDispatch", empInfoSer.getLaborDispatch(request));
			datamap.put("Resident", empInfoSer.getResident(request));
			datamap.put("HuKou", empInfoSer.getHuKou(request));
			datamap.put("WorkArea", empInfoSer.getWorkArea(request));
			datamap.put("caiwu", empInfoSer.getCaiWu(request));
			// datamap.put("WorkPerson", empInfoSer.getWorkPerson(request));
			datamap.put("ShangYe", empInfoSer.getShangYe(request));
			datamap.put("CheJian", empInfoSer.getCheJian(request));

			// execl导出处理
			XLSTransformer transformer = new XLSTransformer();
			try {
				transformer.transformXLS(templateFileName, datamap,
				 		destFileName);
			} catch (ParsePropertyException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}*/else if ("226".equals(jspname)) {
				datamap.put("YEAR", request.getParameter("YEAR"));

				datamap.put("count", empInfoSer.getCount(request));
				datamap.put("wages", empInfoSer.getCountWages(request));
				// execl导出处理
				try {
					ExcelTemplateUtil.transformXLS(templateFileName, datamap,
							destFileName);
				} catch (Exception e) {
					e.printStackTrace();
				}
		}else if ("262".equals(jspname)) {
			
			String EMPLOYEE_OWNED = request.getParameter("EMPLOYEE_OWNED");
			String EMP_OFFICE = request.getParameter("EMP_OFFICE");
			String DAY = request.getParameter("DAY");
			
			datamap.put("EMPLOYEE_OWNED", EMPLOYEE_OWNED);
			datamap.put("EMP_OFFICE", EMP_OFFICE);
			datamap.put("DAY", DAY);
			
			datamap.put("PERSONALINFO1", empInfoSer.getPersonalInfo1(request));
			
			// execl导出处理
			try {
				ExcelTemplateUtil.transformXLS(templateFileName, datamap,
						destFileName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if ("244".equals(jspname)) {
			
			String EMPLOYEE_OWNED = request.getParameter("EMPLOYEE_OWNED");
			String S_END_CONTRACT_DATE = request.getParameter("S_END_CONTRACT_DATE");
			String E_END_CONTRACT_DATE = request.getParameter("E_END_CONTRACT_DATE");
			
			datamap.put("EMPLOYEE_OWNED", EMPLOYEE_OWNED);
			datamap.put("S_END_CONTRACT_DATE", S_END_CONTRACT_DATE);
			datamap.put("E_END_CONTRACT_DATE", E_END_CONTRACT_DATE);
			
			datamap.put("CONTRACTDAOQI", empInfoSer.getCOntractDaoqi(request));
			
			// execl导出处理
			try {
				ExcelTemplateUtil.transformXLS(templateFileName, datamap,
						destFileName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}  else if ("282".equals(jspname)) {
			//办公室
			datamap.put("BANGONGSHI", empInfoSer.getBanGongShi(request));
			//南京支援-顶班
			datamap.put("JISHIDINGBAN", empInfoSer.getJishiDingban(request));
			//TC
			datamap.put("TC", empInfoSer.getTC(request));
			//南京银城东苑店
			datamap.put("DONGYUANDIAN", empInfoSer.getDongyuanDian(request));
			//宁海店
			datamap.put("NINGHAIDIAN", empInfoSer.getNinghaiDian(request));
			//石鼓店
			datamap.put("SHIGUDIAN", empInfoSer.getShiguDian(request));
			//虹悦城
			datamap.put("HONGYUCHENG", empInfoSer.getHongYueCheng(request));
			//龙江店
			datamap.put("LONGJIANGDIAN", empInfoSer.getLongJiang(request));
			//珠江店
			datamap.put("ZHUJIANGDIAN", empInfoSer.getZhuJiang(request));
			//湖南店
			datamap.put("HUNANDIAN", empInfoSer.getHuNan(request));
			//华灯坊
			datamap.put("HUADENGFANG", empInfoSer.getHuDengFang(request));
			//清江苏宁
			datamap.put("QINGJIANG", empInfoSer.getQingJiang(request));
			//万达写字楼
			datamap.put("WANDA", empInfoSer.getWanDa(request));
			//金润广场店
			datamap.put("BINGRUNHUI", empInfoSer.getBingRunHui(request));
			//中央商场店
			datamap.put("CENTERSHOP", empInfoSer.getCenterShopping(request));
			//景枫广场店
			datamap.put("JINGFENG", empInfoSer.getJingFeng(request));
			//21世纪太阳城店
			datamap.put("SUNCITY", empInfoSer.getSunCity(request));
			//茂业新天地店
			datamap.put("MAOYE", empInfoSer.getMaoYe(request));
			//常州支援-顶班
			datamap.put("CZJISHIDINGBAN", empInfoSer.getCZJishiDingban(request));
			//常州环球港店
			datamap.put("HUANQIUGANG", empInfoSer.getHuanQiuGang(request));
			//常州新世纪
			datamap.put("NEWCENTURY", empInfoSer.getNewCentury(request));
			//常州常发广场店
			datamap.put("CHANGFA", empInfoSer.getChangFa(request));
			//常州宝龙广场店
			datamap.put("BAOLONG", empInfoSer.getBaoLong(request));
			//常州九州新世界店
			datamap.put("JIUZHOU", empInfoSer.getJiuZhou(request));
			
			datamap.put("CSTATISTICS", empInfoSer.getCStatistics(request));
			//上周合计人数
			datamap.put("LASTWEEKCOUNT", empInfoSer.getLastWeek(request));
			
			
			// execl导出处理
			try {
				ExcelTemplateUtil.transformXLS(templateFileName, datamap,
						destFileName);
			} catch (Exception e) {
				e.printStackTrace();
			}
	   }  else if ("283".equals(jspname)) {
			String MONTH = request.getParameter("MONTH");
			if (MONTH == null || "".equals(MONTH)) {
				SimpleDateFormat timeFormatter = new SimpleDateFormat("YYYYMM");
				MONTH = timeFormatter.format(Calendar.getInstance().getTime());
			}
	
			datamap.put("MONTH", MONTH);
			datamap.put("DEPTNO", request.getParameter("DEPTNO"));
			
			//  性别，学历，民族现状： 每月发行一次
			datamap.put("xingbxuelminz", empInfoSer.XingbXuelMinz(request));
			
			//年龄现状
			datamap.put("agestatus", empInfoSer.AgeStatus(request));
			
			//工龄现状
			datamap.put("workstatus", empInfoSer.WorkStatus(request));
			
			
			// execl导出处理
			try {
				ExcelTemplateUtil.transformXLS(templateFileName, datamap,
						destFileName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if ("236".equals(jspname)) {
			datamap.put("IndividualIncomeTax", empInfoSer.getIndividualIncomeTax(request));
			// execl导出处理
			try {
				ExcelTemplateUtil.transformXLS(templateFileName, datamap,
						destFileName);
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else if ("237".equals(jspname)) {
			String MONTH = request.getParameter("MONTH");
			if (MONTH == null || "".equals(MONTH)) {
				SimpleDateFormat timeFormatter = new SimpleDateFormat("MM");
				MONTH = timeFormatter.format(Calendar.getInstance().getTime());
			}
			// 报表年份
			name = MONTH + "_" + (String) datamap.get("SQL_NM");
			datamap.put("MONTH", MONTH);
			datamap.put("DEPTNO", request.getParameter("DEPTNO"));
			
			datamap.put("BirthdayWelfare", empInfoSer
					.BirthdayWelfare(request));
			// execl导出处理
			try {
				ExcelTemplateUtil.transformXLS(templateFileName, datamap,
						destFileName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if ("272".equals(jspname)) {
			String RECRUIT_DATE = request.getParameter("RECRUIT_DATE");
			if (RECRUIT_DATE == null || "".equals(RECRUIT_DATE)) {
				SimpleDateFormat timeFormatter = new SimpleDateFormat("yyyy-MM");
				RECRUIT_DATE = timeFormatter.format(Calendar.getInstance().getTime());
			}
			// 报表年份
			name = RECRUIT_DATE + "_" + (String) datamap.get("SQL_NM");
			datamap.put("RECRUIT_DATE", RECRUIT_DATE);
			datamap.put("DEPTNO", request.getParameter("DEPTNO"));
			
			datamap.put("RecruitReport", empInfoSer
					.RecruitReport(request));
			// execl导出处理
			try {
				ExcelTemplateUtil.transformXLS(templateFileName, datamap,
						destFileName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if ("276".equals(jspname)) {
			String SEARCH_DATE = request.getParameter("SEARCH_DATE");
			if (SEARCH_DATE == null || "".equals(SEARCH_DATE)) {
				SimpleDateFormat timeFormatter = new SimpleDateFormat("yyyy-MM-dd");
				SEARCH_DATE = timeFormatter.format(Calendar.getInstance().getTime());
			}
			// 报表年份
			name = SEARCH_DATE + "_" + (String) datamap.get("SQL_NM");
			datamap.put("SEARCH_DATE", SEARCH_DATE);
			datamap.put("DEPTNO", request.getParameter("DEPTNO"));
			
			datamap.put("ruzhi", empInfoSer.RuZhiEmployee(request));
			datamap.put("lizhi", empInfoSer.LiZhiEmployee(request));
			datamap.put("ruzhiStatistic", empInfoSer.RuZhiStatistic(request));
			datamap.put("lizhiStatistic", empInfoSer.LiZhiStatistic(request));
			// execl导出处理
			try {
				ExcelTemplateUtil.transformXLS(templateFileName, datamap,
						destFileName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if ("227".equals(jspname)) {

			// execl导出处理
			try {
				ExcelTemplateUtil.transformXLS(templateFileName, datamap,
						destFileName);
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else if ("169".equals(jspname)) {

			// execl导出处理
			try {
				ExcelTemplateUtil.transformXLS(templateFileName, datamap,
						destFileName);
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else if ("141".equals(jspname)) {
			// 部门员工信息
			List list = ViewDeptPer.getPersonManageList(request);
			datamap.put("FamilyList", ViewDeptPer.getPersonManageList(request));

			try {
				InputStream is = new FileInputStream(templateFileName);
				Workbook workBook = ExcelTemplateUtil.transformXLS(is, datamap);
				Sheet sheet = workBook.getSheetAt(0);
				String path = request.getSession().getServletContext()
						.getRealPath("/");
				Drawing<?> patriarch = sheet.createDrawingPatriarch();
				CreationHelper anchorHelper = workBook.getCreationHelper();
				for (int i = 0; i < list.size(); i++) {
					HashMap valueMap = (HashMap) list.get(i);
					// 将图片以字节流的方式输入输出
					String picture = StringUtil.checkNull(valueMap
							.get("PHOTO_PATH"));
					File picFile = new File(path + "/" + picture);
					if (picture != null && !"".equals(picture)) {
						if (picFile.exists()) {
							ByteArrayOutputStream bos = new ByteArrayOutputStream();
							BufferedImage BufferImg = ImageIO.read(picFile);
							ImageIO.write(BufferImg, "JPEG", bos);
							ClientAnchor anchor = anchorHelper.createClientAnchor();
							anchor.setDx1(0); anchor.setDy1(0); anchor.setDx2(1023); anchor.setDy2(255);
							anchor.setCol1(0); anchor.setRow1(1 + (i*6)); anchor.setCol2(0); anchor.setRow2(5 + (i*6));
							patriarch.createPicture(anchor, workBook
									.addPicture(bos.toByteArray(),
											Workbook.PICTURE_TYPE_JPEG));
						}
					}
				}
				OutputStream os = new FileOutputStream(destFileName);
				workBook.write(os);
				is.close();
				os.flush();
				os.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if ("224".equals(jspname)) {
			String YEAR = request.getParameter("YEAR");
			if (YEAR == null || "".equals(YEAR)) {
				SimpleDateFormat timeFormatter = new SimpleDateFormat("yyyy");
				YEAR = timeFormatter.format(Calendar.getInstance().getTime());
			}
			// 报表年份
			name = YEAR + "_" + (String) datamap.get("SQL_NM");
			datamap.put("YEAR", YEAR);
			// 生成日期
			SimpleDateFormat timeFormatter = new SimpleDateFormat("yyyy-MM-dd");
			String generateDay = timeFormatter.format(Calendar.getInstance()
					.getTime());
			datamap.put("generateDay", generateDay);
			datamap.put("DEPTNO", request.getParameter("DEPTNO"));
			datamap.put("EMPLOYEE_OWEND", request.getParameter("EMPLOYEE_OWEND"));
			// 初期人数
			datamap.put("item", ViewDeptPer.totalEmpCountLastYear(request));
			/*datamap.put("item1", ViewDeptPer.getDemissionRateSpcSh(request));*/
			// execl导出处理
			try {
				ExcelTemplateUtil.transformXLS(templateFileName, datamap,
						destFileName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if ("251".equals(jspname)) {
			String YEAR = request.getParameter("YEAR");
			if (YEAR == null || "".equals(YEAR)) {
				SimpleDateFormat timeFormatter = new SimpleDateFormat("yyyy");
				YEAR = timeFormatter.format(Calendar.getInstance().getTime());
			}
			// 报表年份
			name = YEAR + "_" + (String) datamap.get("SQL_NM");
			datamap.put("YEAR", YEAR);
			// 生成日期
			SimpleDateFormat timeFormatter = new SimpleDateFormat("yyyy-MM-dd");
			String generateDay = timeFormatter.format(Calendar.getInstance()
					.getTime());
			datamap.put("generateDay", generateDay);
			datamap.put("DEPTNO", request.getParameter("DEPTNO"));
			// 初期人数
			datamap.put("totalEmpCountLastYear", ViewDeptPer
					.totalEmpCountLastYearTJ(request));
			// 入社人员
			datamap.put("NewManTotalEmpCountLastYear", ViewDeptPer
					.NewManTotalEmpCountLastYearTJ(request));
			// 离职人员
			datamap.put("LeftManTotalEmpCountLastYear", ViewDeptPer
					.LeftManTotalEmpCountLastYearTJ(request));
			// execl导出处理
			try {
				ExcelTemplateUtil.transformXLS(templateFileName, datamap,
						destFileName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if ("228".equals(jspname)) {

			datamap.put("viewArSummaryList", ViewDeptPer
					.viewArSummaryList(request));
			// execl导出处理
			try {
				ExcelTemplateUtil.transformXLS(templateFileName, datamap,
						destFileName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if ("229".equals(jspname)) {

			datamap.put("useOfAnnualLeaveList", ViewDeptPer
					.arForMedicalCountInfoList(request));
			// execl导出处理
			try {
				ExcelTemplateUtil.transformXLS(templateFileName, datamap,
						destFileName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if ("206".equals(jspname)) {
			datamap.put("CPNY_ID", admin.getCpnyId());
			datamap.put("useOfAnnualLeaveList", ViewDeptPer
					.viewUseOfAnnualLeaveList(request));
			// execl导出处理
			try {
				ExcelTemplateUtil.transformXLS(templateFileName, datamap,
						destFileName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if ("148".equals(jspname)) {

			datamap.put("viewEntryInfoList", ViewDeptPer
					.viewEntryInfoList(request));
			// execl导出处理
			try {
				ExcelTemplateUtil.transformXLS(templateFileName, datamap,
						destFileName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			if("134".equals(jspname)){
				datamap.put("leaveCoordList", infoApplyLeaveSer.getCoordLeaveInfoList(request));				
			}else if ("131".equals(jspname)){
				datamap.put("leaveCoordList", (List) arDetailSer.getLeaveManagentForSearchInfoList(request));
			}else if ("132".equals(jspname)){
				datamap.put("otDetailList", (List) arDetailSer.getSearchApplyOtInfoList(request));
			}else if ("138".equals(jspname)){
				datamap.put("leaveDeptList", infoApplyLeaveSer.getDeptLeaveInfoList(request));
			}else if ("139".equals(jspname)){
				datamap.put("otDeptList", infoApplySer.getDeptOtInfoList(request));
			}else if ("133".equals(jspname)){
				datamap.put("otCoordList", infoApplySer.getCoordOtInfoList(request));
			}else if ("246".equals(jspname)||"250".equals(jspname)||"254".equals(jspname)||"257".equals(jspname)||"261".equals(jspname)||"271".equals(jspname)){
				datamap.put("viewPaResultList",this.viewPaParamSer.viewDeptPaResultList(request));
				datamap.put("viewPaResultListSum",this.viewPaParamSer.viewDeptPaResultListSum(request));
			}else if ("245".equals(jspname)||"270".equals(jspname)||"249".equals(jspname)||"252".equals(jspname)||"256".equals(jspname)||"260".equals(jspname)){
				datamap.put("viewPaResultList",this.viewPaParamSer.viewPaResultList(request));
				datamap.put("viewPaResultListSum",this.viewPaParamSer.viewPaResultListSum(request));
			}else if ("298".equals(jspname)){  
				datamap.put("idCardExpire", infoApplyLeaveSer.getIdCardExpire(request));
			}else if ("122".equals(jspname)){
				datamap.put("leaveAffirmInfoList", infoApplyLeaveSer.getNullBatchLeaveAffirmInfoList(request));
			}else if ("314".equals(jspname)){
				datamap.put("attendaceAbnormalInfoList", infoApplyLeaveSer.getAttendanceExForBatchInfoList(request, null));
			}else if ("128".equals(jspname)){
				datamap.put("otAffirmInfoList", infoApplyLeaveSer.getNullBatchOTTSTOAffirmInfoList(request));
			}else if ("143".equals(jspname)){
				datamap.put("ManageEmpPositionInfoList", ViewDeptPer.ManageEmpPositionInfoList(request));
			}else if ("146".equals(jspname)){
				datamap.put("personList", viewDeptPerSer.viewArPersonalList(request));
			}else if("285".equals(jspname)){
				datamap.put("viewTempEmpList", this.tempEmpSer.viewTempEmpList(request,"viewShopDetailConfirmList"));
			}else if("56".equals(jspname)){
				datamap.put("getArCardRecordList", this.arCardRecordSer.getArCardRecordList(request));
			}else if("58".equals(jspname)){
				datamap.put("getArCardRecordList", this.arCardRecordSer.getArCardRecordMealList(request));
			}else if("2".equals(jspname)){
				datamap.put("getArCardRecordList", this.arCardRecordSer.getArCardRecordForSelfList(request));
			}else if("152".equals(jspname)){
				datamap.put("getArCardRecordDayList", this.arCardRecordSer.getArCardRecordDayList(request));
			}else if("238".equals(jspname)){
				datamap.put("paSummaryList", paReportSer.getPaSummaryList(request));
			}else if("239".equals(jspname)){
				datamap.put("otFeeContrastList", paReportSer.getOtFeeContrastList(request));
				datamap.put("grossPayList", paReportSer.getGrossPayList(request));
			}else if("24".equals(jspname)){
				datamap.put("specialMatterList", empInfoSer.viewSpecialMatter(request));
			}else if("240".equals(jspname)){
				datamap.put("paDecisionList", paReportSer.getPaDecisionList(request));
				datamap.put("getPaNetPayList", paReportSer.getPaNetPayList(request));
				datamap.put("getPaSendFeeList", paReportSer.getPaSendFeeList(request));
				datamap.put("getPaOtFeeList", paReportSer.getPaOtFeeList(request));
				datamap.put("getPayInfoList", paReportSer.getPayInfoList(request));
				datamap.put("getEmpDeftSpcBjList", paReportSer.getEmpDeftSpcBjList(request));
				datamap.put("getEmpDeftInfoSpcBjList", paReportSer.getEmpDeftInfoSpcBjList(request));
				datamap.put("getSalaryTotalSpcBjList", paReportSer.getSalaryTotalSpcBjList(request));
			}else if("241".equals(jspname)){
				datamap.put("wagesBonusList", paReportSer.getWagesBonusList(request));
			}else if("242".equals(jspname)){
				String yyDeptStr = "'BJ010101','BJ010102','BJ010204','BJ010301','BJ01030201','BJ01030202','BJ010401','BJ0201','BJ0202','BJ0204','BJ01040101','BJ0104010101','BJ0104010102','BJ0104010103','BJ0104010104','BJ010202','BJ01020201','BJ01020202'";
				String hcDeptStr = "'BJ01040101','BJ0104010101','BJ010401010101','BJ010401010102','BJ010401010103','BJ010401010104','BJ010401010105','BJ0104010102'," +
						 "'BJ010401010201','BJ010401010202','BJ010401010203','BJ010401010204','BJ0104010103','BJ010401010301','BJ010401010302','BJ010401010303','BJ010401010304',"+
						 "'BJ0104010104','BJ010401010401','BJ010401010402','BJ010401010403','BJ010401010404','BJ01040102','BJ0104010201','BJ0104010202'";
				String glDeptStr = "'BJ0301','BJ030101','BJ03010101','BJ03010102','BJ03010104','BJ030102','BJ0302','BJ030201','BJ0303','BJ030301','BJ0304','BJ030401','BJ0401','BJ0402','BJ040201','BJ040202','BJ0501'";
				datamap.put("paAllocation", paReportSer.paAllocationForOwned(request,"BJ010401","14015496","14015586"));								//SPC 生产
				datamap.put("paAllocationForLobby", paReportSer.paAllocationForOwned(request,"BJ0102","14015496","14015586"));							//销售
				datamap.put("paAllocationForFactory", paReportSer.getPaAllocationListForSpcBj(request,"paAllocationForFactory","14015586"));			//工厂
				datamap.put("paAllocationForCj", paReportSer.paAllocationForDept(request,"'BJ060101','BJ06010101','BJ06010102','BJ060102'","14015586"));//车间
				datamap.put("paAllocationForSc", paReportSer.paAllocationForDept(request,"'BJ06','BJ0601'","14015586"));								//生产部驻在员
				datamap.put("paAllocationForYy", paReportSer.paAllocationForDept(request,yyDeptStr,"14015586"));										//运营部
				datamap.put("paAllocationForHC", paReportSer.paAllocationForDept(request,hcDeptStr,"14015586"));										//后厨教育
				datamap.put("paAllocationForTc", paReportSer.paAllocationForDept(request,"'BJ01040201','BJ01040202','BJ06020201'","14015586"));			//tc bc qc
				datamap.put("paAllocationForQt", paReportSer.paAllocationForDept(request,"'BJ0101010101','BJ01010102','BJ010103','BJ010203'","14015586"));//前厅教育
				datamap.put("paAllocationForYyZ", paReportSer.paAllocationForDept(request,"'BJ0101','BJ0102','BJ06010102','BJ060102'","14015586"));		//运营部驻在员
				datamap.put("paAllocationForAb", paReportSer.paAllocationForDept(request,"'BJ03010103'","14015586"));									//安保
				datamap.put("paAllocationForWl", paReportSer.paAllocationForDept(request,"'BJ0603'","14015586"));										//物流
				datamap.put("paAllocationForGl", paReportSer.paAllocationForDept(request,glDeptStr,"14015586"));										//管理部
				datamap.put("paAllocationForGlZ", paReportSer.paAllocationForDept(request,"'BJ','BJ03'","14015586"));									//管理部驻在员
				datamap.put("paAllocationForHxjsSc", paReportSer.paAllocationForOwned(request,"BJ010401","14015496","14015590"));						//翰鑫锦顺 生产
				datamap.put("paAllocationForHxjsXs", paReportSer.paAllocationForOwned(request,"BJ0102","14015496","14015590"));							//销售
				datamap.put("paAllocationForHxjsHC", paReportSer.paAllocationForDept(request,hcDeptStr,"14015590"));									//后厨
				datamap.put("paAllocationForHxjsTc", paReportSer.paAllocationForDept(request,"'BJ01040201','BJ01040202','BJ06020201'","14015590"));		//tc bc qc
				datamap.put("paAllocationForHxjsQt", paReportSer.paAllocationForDept(request,"'BJ0101010101','BJ01010102','BJ010203'","14015590"));		//前厅教育(无PRM)
				datamap.put("paAllocationForHxjsPrm", paReportSer.paAllocationForDept(request,"'BJ010103'","14015590"));								//PRM
				datamap.put("paAllocationForFactoryXq", paReportSer.getPaAllocationListForSpcBj(request,"paAllocationForFactory","14015587"));			//新桥
				datamap.put("paAllocationForXqAb", paReportSer.paAllocationForDept(request,"'BJ03010103'","14015587"));									//安保
				datamap.put("paAllocationForXqWl", paReportSer.paAllocationForDept(request,"'BJ0603'","14015587"));										//物流
				datamap.put("paAllocationForXqLs", paReportSer.paAllocationForDept(request,"'BJ0603'","14015587","'14015552'"));						//临时工(员工类型)
				datamap.put("paAllocationForFactoryJdw", paReportSer.getPaAllocationListForSpcBj(request,"paAllocationForFactory","14015588"));			//金达旺
				datamap.put("paAllocationForJdwAb", paReportSer.paAllocationForDept(request,"'BJ03010103'","14015588"));								//安保
				datamap.put("paAllocationForJdwWl", paReportSer.paAllocationForDept(request,"'BJ0603'","14015588"));									//物流
				datamap.put("paAllocationForJdwLw", paReportSer.paAllocationForDept(request,"'BJ0603'","14015588","'14015551','10414'"));				//劳务职(员工类型)
				datamap.put("paAllocationForFactoryLc", paReportSer.getPaAllocationListForSpcBj(request,"paAllocationForFactory","14015589"));			//乐成
				datamap.put("paAllocationForLcAb", paReportSer.paAllocationForDept(request,"'BJ03010103'","14015589"));									//安保
				datamap.put("paAllocationForLcWl", paReportSer.paAllocationForDept(request,"'BJ0603'","14015589"));										//物流
			}else if("233".equals(jspname)){
				datamap.put("item", empInfoSer.hrStroeNumberReport(request));
			}else if("234".equals(jspname)){
				datamap.put("item", empInfoSer.hrDemissionRateReport(request));
			}else if("235".equals(jspname)){
				LinkedHashMap attendanceMap = viewDeptPerSer.viewAttendanceForSpcBjMonthList(request,"'1','2','3','4','5'","'5'");//等级 包含项
				datamap.put("deptList", attendanceMap.get("deptList"));
				datamap.put("viewAttendanceForMonthList", attendanceMap);
				LinkedHashMap attendanceCntMap = viewDeptPerSer.viewAttendanceForSpcBjMonthList(request,"'1','2'","'1','2'");//等级 包含项
				datamap.put("deptCntList", attendanceCntMap.get("deptList"));
				datamap.put("viewAttendanceForMonthCntList", attendanceCntMap);
			}else if("225".equals(jspname)){
				LinkedHashMap attendanceMap = viewDeptPerSer.viewAttendanceForSpcBjMonthList1(request,"'1','2','3','4','5'","'5'");//等级 包含项
				datamap.put("deptList", attendanceMap.get("deptList"));
				datamap.put("viewAttendanceForMonthList", attendanceMap);
				LinkedHashMap attendanceCntMap = viewDeptPerSer.viewAttendanceForSpcBjMonthList1(request,"'1','2'","'1','2'");//等级 包含项
				datamap.put("deptCntList", attendanceCntMap.get("deptList"));
				datamap.put("viewAttendanceForMonthCntList", attendanceCntMap);
			}else if("286".equals(jspname)){
				datamap.put("position", ViewDeptPer.getCountPosition(request));
				datamap.put("others", ViewDeptPer.getOthers(request));
				datamap.put("searchMonth", ViewDeptPer.getSearchMonth(request));
			}else if("288".equals(jspname)){
				String MONTH_DAY = request.getParameter("MONTH_DAY");
				datamap.put("MONTH_DAY", MONTH_DAY);
				
				datamap.put("position", ViewDeptPer.getPositionRULIzhi(request));
				datamap.put("others", ViewDeptPer.getEmpTypeRULIzhi(request));
				datamap.put("graderuzhi", ViewDeptPer.getGradeRuzhi(request));
				datamap.put("gradelizhi", ViewDeptPer.getGradeLizhi(request));
			}else if("289".equals(jspname)){
				String SEARCH_DATE = request.getParameter("SEARCH_DATE");
			    String month = SEARCH_DATE.substring(0,7);
				datamap.put("SEARCH_DATE", SEARCH_DATE);
				datamap.put("MONTH", month);
				
				datamap.put("position1", ViewDeptPer.getPCountEMP(request));
				datamap.put("others1", ViewDeptPer.getEmpTypeCountEMP(request));
				datamap.put("gradecountemp", ViewDeptPer.getGradeCountEmp(request));
			}else if("247".equals(jspname)){
				datamap.put("item", paReportSer.getGrossPayList(request));
				LinkedHashMap thisMonth = paReportSer.getPaDetaiByDeptNolList(request,"THIS_MONTH","3");//3 部门等级
				datamap.put("thisMonth",thisMonth);
				datamap.put("deptList", thisMonth.get("deptList"));
				LinkedHashMap lastMonth = paReportSer.getPaDetaiByDeptNolList(request,"LAST_MONTH","3");//3 部门等级
				datamap.put("lastMonth",lastMonth);
				datamap.put("lastMonthDeptList", lastMonth.get("deptList"));
				LinkedHashMap thisMonthForDept2 = paReportSer.getPaDetaiByDeptNolList(request,"THIS_MONTH","2");//2 部门等级
				datamap.put("thisMonthForDept2",thisMonthForDept2);
				datamap.put("deptForDept2List", thisMonthForDept2.get("deptList"));
				LinkedHashMap lastMonthForDept2 = paReportSer.getPaDetaiByDeptNolList(request,"LAST_MONTH","2");//2 部门等级
				datamap.put("lastMonthForDept2",lastMonthForDept2);
				datamap.put("lastMonthDeptForDept2List", lastMonthForDept2.get("deptList"));
			}else if("248".equals(jspname)){
				LinkedHashMap managementItem = paReportSer.getManagementSituationList(request,"'1','2'","'1','2'");//等级 包含项
				datamap.put("deptList", managementItem.get("deptList"));
				datamap.put("managementItem", managementItem);
				LinkedHashMap managementChildItem = paReportSer.getManagementSituationList(request,"'3'","'3'");//等级 包含项
				datamap.put("deptChildList", managementChildItem.get("deptList"));
				datamap.put("managementChildItem", managementChildItem);
			}else if("299".equals(jspname)){
				LinkedHashMap bjShopItem = ViewDeptPer.getShopItemList(request,"HTSV");
				LinkedHashMap shShopItem = ViewDeptPer.getShopItemList(request,"SPC_SH"); 
				LinkedHashMap njShopItem = ViewDeptPer.getShopItemList(request,"SPC_NJ");
				LinkedHashMap tjShopItem = ViewDeptPer.getShopItemList(request,"HAE");
				LinkedHashMap hzShopItem = ViewDeptPer.getShopItemList(request,"SPC_HZ");
				LinkedHashMap dlShopItem = ViewDeptPer.getShopItemList(request,"SPC_DL");
				
				datamap.put("bjShopList", bjShopItem.get("deptList"));
				datamap.put("shShopList", shShopItem.get("deptList"));
				datamap.put("njShopList", njShopItem.get("deptList"));
				datamap.put("tjShopList", tjShopItem.get("deptList"));
				datamap.put("hzShopList", hzShopItem.get("deptList"));
				datamap.put("dlShopList", dlShopItem.get("deptList"));
				
				datamap.put("bjShopItem", bjShopItem);
				datamap.put("shShopItem", shShopItem);
				datamap.put("njShopItem", njShopItem);
				datamap.put("tjShopItem", tjShopItem);
				datamap.put("hzShopItem", hzShopItem);
				datamap.put("dlShopItem", dlShopItem);
				
				LinkedHashMap shopCountItem = ViewDeptPer.getShopCountItemList(request);
				datamap.put("shopCountItem", shopCountItem.get("ShopCountItem"));
				
			}else if("301".equals(jspname)){
				LinkedHashMap shShopItem = ViewDeptPer.getShopItemListsh(request,"SPC_SH"); 
				
				datamap.put("shShopList", shShopItem.get("deptList"));
				
				datamap.put("shShopItem", shShopItem);
				
				LinkedHashMap shopCountItem = ViewDeptPer.getShopCountItemListsh(request);
				datamap.put("shopCountItem", shopCountItem.get("ShopCountItem"));
				
			}else if("302".equals(jspname)){
				LinkedHashMap shShopItem = ViewDeptPer.getShopItemListsh1(request,"SPC_SH"); 
				
				datamap.put("shShopList", shShopItem.get("deptList"));
				
				datamap.put("shShopItem", shShopItem);
				
				LinkedHashMap shopCountItem = ViewDeptPer.getShopCountItemListsh(request);
				datamap.put("shopCountItem", shopCountItem.get("ShopCountItem"));
				
			}else if("300".equals(jspname)){
				datamap.put("item", ViewDeptPer.getMonthAttDetailList(request));
			}else if("253".equals(jspname)){
				datamap.put("item", paReportSer.getWagesBonusList(request));
			}else if("255".equals(jspname)){
				datamap.put("demissionItem", empInfoSer.hrDemissionRateReport(request));
				datamap.put("demissionDatilItem", empInfoSer.hrDemissionRateByDeptNoReport(request));
				datamap.put("fullThreeMonthsItem", empInfoSer.hrDemissionRateFullThreeMonthsReport(request));
				datamap.put("xdfItem", empInfoSer.hrDemissionRateByXdfMonthsReport(request));
			}else if("294".equals(jspname)){
				datamap.put("viewDeptDemissionList",this.empInfoSer.viewDeptDemissionList(request));
			}else if("258".equals(jspname)){
				datamap.put("paSummaryList", paReportSer.getPaSummaryList(request));
			}else if("220".equals(jspname)){
				datamap.put("viewFactoryShiftExcelList", tempEmpSer.viewFactoryShiftExcelList(request));
			}else if("219".equals(jspname)){
				datamap.put("viewShopShiftExcelList", tempEmpSer.viewShopShiftExcelList(request));
			}else if("296".equals(jspname)){
				datamap.put("viewPaNotImport", tempEmpSer.viewPaNotImport(request));
			}else if("265".equals(jspname)){
				datamap.put("viewShopShiftExcelList", tempEmpSer.viewAllShiftExcelList(request,"'14015815','14015816'"));
			}else if("266".equals(jspname)){
				datamap.put("viewWuLiuShiftExcelList", tempEmpSer.viewAllShiftExcelList(request,"'14015817'"));
			}else if("267".equals(jspname)){
				datamap.put("viewFactoryShiftExcelList", tempEmpSer.viewAllShiftExcelList(request,"'14015818','14015814'"));
			}else if("264".equals(jspname)){
				datamap.put("viewAttendanceForMonthList", viewDeptPerSer.viewAttendanceForMonthList(request));
			}else if("268".equals(jspname)){
				datamap.put("viewMonthList", this.tempEmpSer.viewTempEmpList(request,"viewMonthList"));
				datamap.put("viewMonthDetailList", this.tempEmpSer.viewTempEmpList(request,"viewMonthDetailApplyList"));
			}else if("305".equals(jspname)){
				//datamap.put("viewMonthList", this.tempEmpSer.viewTempEmpList(request,"viewMonthList"));
				Map param = ObjectBindUtil.getRequestParamData(request,"seach_");
				if(param.get("AR_MONTH")!=null && param.get("AR_MONTH")!=""){
					datamap.put("month", param.get("AR_MONTH").toString().substring(0, 2));
					datamap.put("year", param.get("AR_MONTH").toString().substring(2, 6));
					datamap.put("viewMonthDetailList", this.tempEmpSer.viewTempEmpList(request,"getMonthDetailRealTimeList"));
				}else{
					datamap.put("viewMonthDetailList", null);
				}
			}else if("306".equals(jspname)){
				//datamap.put("viewMonthList", this.tempEmpSer.viewTempEmpList(request,"viewMonthList"));
				Map param = ObjectBindUtil.getRequestParamData(request,"seach_");
				if(param.get("AR_MONTH")!=null && param.get("AR_MONTH")!=""){
					datamap.put("month", param.get("AR_MONTH").toString().substring(0, 2));
					datamap.put("year", param.get("AR_MONTH").toString().substring(2, 6));
					datamap.put("viewMonthDetailList", this.tempEmpSer.viewTempEmpList(request,"getMonthDetailRealTimeList"));
				}else{
					datamap.put("viewMonthDetailList", null);
				}
			}else if("315".equals(jspname)){ // Bảng tổng hợp tăng ca ngày
				//datamap.put("viewMonthList", this.tempEmpSer.viewTempEmpList(request,"viewMonthList"));
				Map param = ObjectBindUtil.getRequestParamData(request,"seach_");
				if(param.get("AR_DATE")!=null && param.get("AR_DATE")!=""){
					datamap.put("AR_DATE", param.get("AR_DATE"));
					datamap.put("Main_Innovation_OtList", this.tempEmpSer.viewTempEmpListWithparam(request,"getDateDetailPersonOtList", "'D11EV110','D11EV130'"));
					datamap.put("VLC_OtList", this.tempEmpSer.viewTempEmpListWithparam(request,"getDateDetailPersonOtList", "'D11EV120'"));
					datamap.put("SMD_OtList", this.tempEmpSer.viewTempEmpListWithparam(request,"getDateDetailPersonOtList", "'D11EV140'"));
					datamap.put("PBA_OtList", this.tempEmpSer.viewTempEmpListWithparam(request,"getDateDetailPersonOtList", "'D11EV150'"));
					datamap.put("Sum_Manu_OtList", this.tempEmpSer.viewTempEmpListWithparam(request,"getDateDetailPersonOtList", "'D11EV110','D11EV130','D11EV120','D11EV140','D11EV150'"));
					
					datamap.put("Circuit_OtList", this.tempEmpSer.viewTempEmpListWithparam(request,"getDateDetailPersonOtList", "'D11EV210'"));
					datamap.put("Mecha_OtList", this.tempEmpSer.viewTempEmpListWithparam(request,"getDateDetailPersonOtList", "'D11EV220'"));
					datamap.put("Engineering_OtList", this.tempEmpSer.viewTempEmpListWithparam(request,"getDateDetailPersonOtList", "'D11EV230'"));
					datamap.put("Sum_Manu_Tech_OtList", this.tempEmpSer.viewTempEmpListWithparam(request,"getDateDetailPersonOtList", "'D11EV210','D11EV220','D11EV230'"));
					
					datamap.put("Product_Assurance_OtList", this.tempEmpSer.viewTempEmpListWithparam(request,"getDateDetailPersonOtList", "'D11EV310'"));
					datamap.put("Component_Assurance_OtList", this.tempEmpSer.viewTempEmpListWithparam(request,"getDateDetailPersonOtList", "'D11EV320'"));
					datamap.put("CS_OtList", this.tempEmpSer.viewTempEmpListWithparam(request,"getDateDetailPersonOtList", "'D11EV330'"));
					datamap.put("Sum_Qua_As_OtList", this.tempEmpSer.viewTempEmpListWithparam(request,"getDateDetailPersonOtList", "'D11EV310','D11EV320','D11EV330'"));
					
					datamap.put("CS_Operation_OtList", this.tempEmpSer.viewTempEmpListWithparam(request,"getDateDetailPersonOtList", "'D11EV331'"));
					
					datamap.put("Manufacturing_Management_OtList", this.tempEmpSer.viewTempEmpListWithparam(request,"getDateDetailPersonOtList", "'D11EV410'"));
					datamap.put("Finish_Warehouse_OtList", this.tempEmpSer.viewTempEmpListWithparam(request,"getDateDetailPersonOtList", "'D11EV440'"));
					datamap.put("Outsourcing_Management_OtList", this.tempEmpSer.viewTempEmpListWithparam(request,"getDateDetailPersonOtList", "'D11EV420'"));
					datamap.put("Material_OtList", this.tempEmpSer.viewTempEmpListWithparam(request,"getDateDetailPersonOtList", "'D11EV530'"));
					datamap.put("Sum_Manu_Mana_OtList", this.tempEmpSer.viewTempEmpListWithparam(request,"getDateDetailPersonOtList", "'D11EV410','D11EV440','D11EV420','D11EV530'"));
					
					datamap.put("Procurement_OtList", this.tempEmpSer.viewTempEmpListWithparam(request,"getDateDetailPersonOtList", "'D11EV520'"));
					
					datamap.put("HR_OtList", this.tempEmpSer.viewTempEmpListWithparam(request,"getDateDetailPersonOtList", "'D11EV811'"));
					datamap.put("GA_OtList", this.tempEmpSer.viewTempEmpListWithparam(request,"getDateDetailPersonOtList", "'D11EV821'"));
					datamap.put("Environment_Safety_OtList", this.tempEmpSer.viewTempEmpListWithparam(request,"getDateDetailPersonOtList", "'D11EV831'"));
					datamap.put("Utility_OtList", this.tempEmpSer.viewTempEmpListWithparam(request,"getDateDetailPersonOtList", "'D11EV640'"));
					datamap.put("Sum_HR_Team_OtList", this.tempEmpSer.viewTempEmpListWithparam(request,"getDateDetailPersonOtList", "'D11EV811','D11EV821','D11EV831','D11EV640'"));
					
					datamap.put("Finance_OtList", this.tempEmpSer.viewTempEmpListWithparam(request,"getDateDetailPersonOtList", "'D11EV911'"));
					datamap.put("IT_OtList", this.tempEmpSer.viewTempEmpListWithparam(request,"getDateDetailPersonOtList", "'D11EV921'"));
					datamap.put("Logistics_OtList", this.tempEmpSer.viewTempEmpListWithparam(request,"getDateDetailPersonOtList", "'D11EV430'"));
					datamap.put("Sum_Admin_OtList", this.tempEmpSer.viewTempEmpListWithparam(request,"getDateDetailPersonOtList", "'D11EV911','D11EV921','D11EV430'"));
					
					datamap.put("Sum_ALL_OtList", this.tempEmpSer.viewTempEmpListWithparam(request,"getDateDetailPersonOtList", "'D11EV110','D11EV130','D11EV120','D11EV140','D11EV150','D11EV210','D11EV220','D11EV230','D11EV310','D11EV320','D11EV330','D11EV410','D11EV440','D11EV420','D11EV530','D11EV520','D11EV811','D11EV821','D11EV831','D11EV640','D11EV911','D11EV921','D11EV430'"));
					
				}else{
					datamap.put("viewDateDetailPersonOtList", null);
				}
			}else if("295".equals(jspname)){
				datamap.put("viewMonthList", this.tempEmpSer.viewTempEmpList(request,"viewMonthList"));
				datamap.put("viewMonthDetailList", this.tempEmpSer.viewTempEmpList(request,"viewMonthDetailApplyList"));
			}else if("281".equals(jspname)){
				datamap.put("viewMonthList", this.tempEmpSer.viewTempEmpList(request,"viewMonthForExcelList"));
				datamap.put("viewMonthDetailList", this.tempEmpSer.viewTempEmpList(request,"viewMonthDetailApplyForExcelList"));
			}else if("269".equals(jspname)){
				datamap.put("viewMonthList", this.tempEmpSer.viewTempEmpList(request,"viewMonthList"));
				datamap.put("viewMonthDetailList", this.tempEmpSer.viewTempEmpList(request,"viewMonthDetailConfirmList"));
			}else if("274".equals(jspname)){
				datamap.put("getZhuZaiYuanPay", this.tempEmpSer.viewTempEmpList(request,"getZhuZaiYuanPay"));
			}else if("275".equals(jspname)){
				datamap.put("getLastMonths", this.tempEmpSer.viewTempEmpList(request,"getLastMonths"));
				datamap.put("getThisMonths", this.tempEmpSer.viewTempEmpList(request,"getThisMonths"));
				datamap.put("getLLastMonths", this.tempEmpSer.viewTempEmpList(request,"getLLastMonths"));
				datamap.put("getThisMonthsOtOpening", this.tempEmpSer.viewTempEmpList(request,"getThisMonthsOtOpening"));
				datamap.put("getThisMonthsOtCondition", this.tempEmpSer.viewTempEmpList(request,"getThisMonthsOtCondition"));
				datamap.put("getMonthsOtCondition1", this.tempEmpSer.viewTempEmpList(request,"getMonthsOtCondition1"));
				datamap.put("getMonthsOtCondition2", this.tempEmpSer.viewTempEmpList(request,"getMonthsOtCondition2"));
				datamap.put("getMonthsOtCondition3", this.tempEmpSer.viewTempEmpList(request,"getMonthsOtCondition3"));
				datamap.put("getThisMonthsOtOpening11", this.tempEmpSer.viewTempEmpList(request,"getThisMonthsOtOpening11"));
				datamap.put("getThisMonthsOtCondition11", this.tempEmpSer.viewTempEmpList(request,"getThisMonthsOtCondition11"));
				datamap.put("getMonthsOtCondition11", this.tempEmpSer.viewTempEmpList(request,"getMonthsOtCondition11"));
				datamap.put("getMonthsOtCondition21", this.tempEmpSer.viewTempEmpList(request,"getMonthsOtCondition21"));
				datamap.put("getMonthsOtCondition31", this.tempEmpSer.viewTempEmpList(request,"getMonthsOtCondition31"));
				datamap.put("getFuKuanMingXi001", this.tempEmpSer.viewTempEmpList(request,"getFuKuanMingXi001"));
				datamap.put("getFuKuanMingXi002", this.tempEmpSer.viewTempEmpList(request,"getFuKuanMingXi002"));
				datamap.put("getFuKuanMingXi003", this.tempEmpSer.viewTempEmpList(request,"getFuKuanMingXi003"));
				datamap.put("getFuKuanMingXi004", this.tempEmpSer.viewTempEmpList(request,"getFuKuanMingXi004"));
				datamap.put("getFuKuanMingXi005", this.tempEmpSer.viewTempEmpList(request,"getFuKuanMingXi005"));
				datamap.put("getFuKuanMingXi006", this.tempEmpSer.viewTempEmpList(request,"getFuKuanMingXi006"));
				datamap.put("getFuKuanMingXi007", this.tempEmpSer.viewTempEmpList(request,"getFuKuanMingXi007"));
				datamap.put("getFuKuanMingXi008", this.tempEmpSer.viewTempEmpList(request,"getFuKuanMingXi008"));
				datamap.put("getFuKuanMingXi009", this.tempEmpSer.viewTempEmpList(request,"getFuKuanMingXi009"));
				datamap.put("getFuKuanMingXi010", this.tempEmpSer.viewTempEmpList(request,"getFuKuanMingXi010"));
				datamap.put("getFuKuanMingXi011", this.tempEmpSer.viewTempEmpList(request,"getFuKuanMingXi011"));
				datamap.put("getFuKuanMingXi012", this.tempEmpSer.viewTempEmpList(request,"getFuKuanMingXi012"));
				datamap.put("getFuKuanMingXi013", this.tempEmpSer.viewTempEmpList(request,"getFuKuanMingXi013"));
				datamap.put("getFuKuanMingXi014", this.tempEmpSer.viewTempEmpList(request,"getFuKuanMingXi014"));
				datamap.put("getFuKuanMingXi015", this.tempEmpSer.viewTempEmpList(request,"getFuKuanMingXi015"));
				datamap.put("getFuKuanMingXi016", this.tempEmpSer.viewTempEmpList(request,"getFuKuanMingXi016"));
				datamap.put("getFuKuanMingXi017", this.tempEmpSer.viewTempEmpList(request,"getFuKuanMingXi017"));
				datamap.put("getFuKuanMingXi018", this.tempEmpSer.viewTempEmpList(request,"getFuKuanMingXi018"));
				datamap.put("getFuKuanMingXi019", this.tempEmpSer.viewTempEmpList(request,"getFuKuanMingXi019"));
				datamap.put("getFuKuanMingXi020", this.tempEmpSer.viewTempEmpList(request,"getFuKuanMingXi020"));
				datamap.put("getFuKuanMingXi021", this.tempEmpSer.viewTempEmpList(request,"getFuKuanMingXi021"));
				datamap.put("getFuKuanMingXi022", this.tempEmpSer.viewTempEmpList(request,"getFuKuanMingXi022"));
				datamap.put("getFuKuanMingXi023", this.tempEmpSer.viewTempEmpList(request,"getFuKuanMingXi023"));
				datamap.put("getFuKuanMingXi024", this.tempEmpSer.viewTempEmpList(request,"getFuKuanMingXi024"));
				datamap.put("getFuKuanMingXi025", this.tempEmpSer.viewTempEmpList(request,"getFuKuanMingXi025"));
				datamap.put("getFuKuanMingXi026", this.tempEmpSer.viewTempEmpList(request,"getFuKuanMingXi026"));
				datamap.put("getFuKuanMingXi027", this.tempEmpSer.viewTempEmpList(request,"getFuKuanMingXi027"));
				datamap.put("getFuKuanMingXi028", this.tempEmpSer.viewTempEmpList(request,"getFuKuanMingXi028"));
				datamap.put("getFuKuanMingXi029", this.tempEmpSer.viewTempEmpList(request,"getFuKuanMingXi029"));
				datamap.put("getFuKuanMingXi030", this.tempEmpSer.viewTempEmpList(request,"getFuKuanMingXi030"));
				datamap.put("getFuKuanMingXi031", this.tempEmpSer.viewTempEmpList(request,"getFuKuanMingXi031"));
				datamap.put("getFuKuanMingXi032", this.tempEmpSer.viewTempEmpList(request,"getFuKuanMingXi032"));
				datamap.put("getFuKuanMingXi033", this.tempEmpSer.viewTempEmpList(request,"getFuKuanMingXi033"));
				datamap.put("getFuKuanMingXi034", this.tempEmpSer.viewTempEmpList(request,"getFuKuanMingXi034"));
				datamap.put("getFuKuanMingXi035", this.tempEmpSer.viewTempEmpList(request,"getFuKuanMingXi035"));
				datamap.put("getFuKuanMingXiLaoWu001", this.tempEmpSer.viewTempEmpList(request,"getFuKuanMingXiLaoWu001"));
				datamap.put("getFuKuanMingXiLaoWu002", this.tempEmpSer.viewTempEmpList(request,"getFuKuanMingXiLaoWu002"));
				datamap.put("getFuKuanMingXiLaoWu003", this.tempEmpSer.viewTempEmpList(request,"getFuKuanMingXiLaoWu003"));
				datamap.put("getFuKuanMingXiLaoWu004", this.tempEmpSer.viewTempEmpList(request,"getFuKuanMingXiLaoWu004"));
				datamap.put("getFuKuanMingXiLaoWu005", this.tempEmpSer.viewTempEmpList(request,"getFuKuanMingXiLaoWu005"));
				datamap.put("getFuKuanMingXiLaoWu006", this.tempEmpSer.viewTempEmpList(request,"getFuKuanMingXiLaoWu006"));
				datamap.put("getFuKuanMingXiLaoWu007", this.tempEmpSer.viewTempEmpList(request,"getFuKuanMingXiLaoWu007"));
				datamap.put("getFuKuanMingXiLaoWu008", this.tempEmpSer.viewTempEmpList(request,"getFuKuanMingXiLaoWu008"));
				datamap.put("getFuKuanMingXiLaoWu009", this.tempEmpSer.viewTempEmpList(request,"getFuKuanMingXiLaoWu009"));
				datamap.put("getFuKuanMingXiLaoWu010", this.tempEmpSer.viewTempEmpList(request,"getFuKuanMingXiLaoWu010"));
				datamap.put("getFuKuanMingXiLaoWu011", this.tempEmpSer.viewTempEmpList(request,"getFuKuanMingXiLaoWu011"));
				datamap.put("getFuKuanMingXiLaoWu012", this.tempEmpSer.viewTempEmpList(request,"getFuKuanMingXiLaoWu012"));
				datamap.put("getFuKuanMingXiLaoWu013", this.tempEmpSer.viewTempEmpList(request,"getFuKuanMingXiLaoWu013"));
				datamap.put("getFuKuanMingXiLaoWu014", this.tempEmpSer.viewTempEmpList(request,"getFuKuanMingXiLaoWu014"));
				datamap.put("getFuKuanMingXiLaoWu015", this.tempEmpSer.viewTempEmpList(request,"getFuKuanMingXiLaoWu015"));
				datamap.put("getFuKuanMingXiLaoWu016", this.tempEmpSer.viewTempEmpList(request,"getFuKuanMingXiLaoWu016"));
				datamap.put("getFuKuanMingXiLaoWu017", this.tempEmpSer.viewTempEmpList(request,"getFuKuanMingXiLaoWu017"));
				datamap.put("getFuKuanMingXiLaoWu018", this.tempEmpSer.viewTempEmpList(request,"getFuKuanMingXiLaoWu018"));
				datamap.put("getFuKuanMingXiLaoWu019", this.tempEmpSer.viewTempEmpList(request,"getFuKuanMingXiLaoWu019"));
				datamap.put("getFuKuanMingXiLaoWu020", this.tempEmpSer.viewTempEmpList(request,"getFuKuanMingXiLaoWu020"));
				datamap.put("getFuKuanMingXiLaoWu021", this.tempEmpSer.viewTempEmpList(request,"getFuKuanMingXiLaoWu021"));
				datamap.put("getFuKuanMingXiLaoWu022", this.tempEmpSer.viewTempEmpList(request,"getFuKuanMingXiLaoWu022"));
				datamap.put("getFuKuanMingXiLaoWu023", this.tempEmpSer.viewTempEmpList(request,"getFuKuanMingXiLaoWu023"));
				datamap.put("getFuKuanMingXiLaoWu024", this.tempEmpSer.viewTempEmpList(request,"getFuKuanMingXiLaoWu024"));
				datamap.put("getFuKuanMingXiLaoWu025", this.tempEmpSer.viewTempEmpList(request,"getFuKuanMingXiLaoWu025"));
				datamap.put("getFuKuanMingXiLaoWu026", this.tempEmpSer.viewTempEmpList(request,"getFuKuanMingXiLaoWu026"));
				datamap.put("getFuKuanMingXiLaoWu027", this.tempEmpSer.viewTempEmpList(request,"getFuKuanMingXiLaoWu027"));
				datamap.put("getFuKuanMingXiLaoWu028", this.tempEmpSer.viewTempEmpList(request,"getFuKuanMingXiLaoWu028"));
				datamap.put("getFuKuanMingXiLaoWu029", this.tempEmpSer.viewTempEmpList(request,"getFuKuanMingXiLaoWu029"));
				datamap.put("getFuKuanMingXiLaoWu030", this.tempEmpSer.viewTempEmpList(request,"getFuKuanMingXiLaoWu030"));
				datamap.put("getFuKuanMingXiLaoWu031", this.tempEmpSer.viewTempEmpList(request,"getFuKuanMingXiLaoWu031"));
				datamap.put("getFuKuanMingXiLaoWu032", this.tempEmpSer.viewTempEmpList(request,"getFuKuanMingXiLaoWu032"));
				datamap.put("getFuKuanMingXiLaoWu033", this.tempEmpSer.viewTempEmpList(request,"getFuKuanMingXiLaoWu033"));
				datamap.put("getFuKuanMingXiLaoWu034", this.tempEmpSer.viewTempEmpList(request,"getFuKuanMingXiLaoWu034"));
				datamap.put("getFuKuanMingXiLaoWu035", this.tempEmpSer.viewTempEmpList(request,"getFuKuanMingXiLaoWu035"));
				datamap.put("getFuKuanMingXiLaoWu036", this.tempEmpSer.viewTempEmpList(request,"getFuKuanMingXiLaoWu036"));
				datamap.put("getFuKuanMingXiLaoWu037", this.tempEmpSer.viewTempEmpList(request,"getFuKuanMingXiLaoWu037"));
				datamap.put("getFuKuanMingXiLaoWu038", this.tempEmpSer.viewTempEmpList(request,"getFuKuanMingXiLaoWu038"));
				datamap.put("getFuKuanMingXiLaoWu039", this.tempEmpSer.viewTempEmpList(request,"getFuKuanMingXiLaoWu039"));
				datamap.put("getFuKuanMingXiLaoWu040", this.tempEmpSer.viewTempEmpList(request,"getFuKuanMingXiLaoWu040"));
				datamap.put("getFuKuanMingXiLaoWu041", this.tempEmpSer.viewTempEmpList(request,"getFuKuanMingXiLaoWu041"));
				datamap.put("getFuKuanMingXiLaoWu042", this.tempEmpSer.viewTempEmpList(request,"getFuKuanMingXiLaoWu042"));
				datamap.put("getFuKuanMingXiLaoWu043", this.tempEmpSer.viewTempEmpList(request,"getFuKuanMingXiLaoWu043"));
				datamap.put("getFuKuanMingXiLaoWu044", this.tempEmpSer.viewTempEmpList(request,"getFuKuanMingXiLaoWu044"));
				datamap.put("getFuKuanMingXiLaoWu045", this.tempEmpSer.viewTempEmpList(request,"getFuKuanMingXiLaoWu045"));
				datamap.put("getFuKuanMingXiLaoWu046", this.tempEmpSer.viewTempEmpList(request,"getFuKuanMingXiLaoWu046"));
				datamap.put("getFuKuanMingXiLaoWu047", this.tempEmpSer.viewTempEmpList(request,"getFuKuanMingXiLaoWu047"));
				datamap.put("getFuKuanMingXiLaoWu048", this.tempEmpSer.viewTempEmpList(request,"getFuKuanMingXiLaoWu048"));
				datamap.put("getFuKuanMingXiLaoWu049", this.tempEmpSer.viewTempEmpList(request,"getFuKuanMingXiLaoWu049"));
				datamap.put("getFuKuanMingXiLaoWu050", this.tempEmpSer.viewTempEmpList(request,"getFuKuanMingXiLaoWu050"));
				datamap.put("getHuiZongBiao001", this.tempEmpSer.viewTempEmpList(request,"getHuiZongBiao001"));
				datamap.put("getHuiZongBiao002", this.tempEmpSer.viewTempEmpList(request,"getHuiZongBiao002"));
				datamap.put("getHuiZongBiao003", this.tempEmpSer.viewTempEmpList(request,"getHuiZongBiao003"));
				datamap.put("getHuiZongBiao004", this.tempEmpSer.viewTempEmpList(request,"getHuiZongBiao004"));
				datamap.put("getHuiZongBiao005", this.tempEmpSer.viewTempEmpList(request,"getHuiZongBiao005"));
				datamap.put("getHuiZongBiao006", this.tempEmpSer.viewTempEmpList(request,"getHuiZongBiao006"));
				datamap.put("getHuiZongBiao007", this.tempEmpSer.viewTempEmpList(request,"getHuiZongBiao007"));
				datamap.put("getHuiZongBiao008", this.tempEmpSer.viewTempEmpList(request,"getHuiZongBiao008"));
				datamap.put("getDianPuBuMenBieGongZiBiao001", this.tempEmpSer.viewTempEmpList(request,"getDianPuBuMenBieGongZiBiao001"));
				datamap.put("getDianPuBuMenBieGongZiBiao002", this.tempEmpSer.viewTempEmpList(request,"getDianPuBuMenBieGongZiBiao002"));
				datamap.put("getDianPuBuMenBieGongZiBiao003", this.tempEmpSer.viewTempEmpList(request,"getDianPuBuMenBieGongZiBiao003"));
				datamap.put("getDianPuBuMenBieGongZiBiao004", this.tempEmpSer.viewTempEmpList(request,"getDianPuBuMenBieGongZiBiao004"));
				datamap.put("getDianPuBuMenBieGongZiBiao005", this.tempEmpSer.viewTempEmpList(request,"getDianPuBuMenBieGongZiBiao005"));
				datamap.put("getDianPuBuMenBieGongZiBiao006", this.tempEmpSer.viewTempEmpList(request,"getDianPuBuMenBieGongZiBiao006"));
				datamap.put("getDianPuBuMenBieGongZiBiao007", this.tempEmpSer.viewTempEmpList(request,"getDianPuBuMenBieGongZiBiao007"));
				datamap.put("getDianPuBuMenBieGongZiBiao008", this.tempEmpSer.viewTempEmpList(request,"getDianPuBuMenBieGongZiBiao008"));
				datamap.put("getDianPuBuMenBieGongZiBiao009", this.tempEmpSer.viewTempEmpList(request,"getDianPuBuMenBieGongZiBiao009"));
				datamap.put("getDianPuBuMenBieGongZiBiao010", this.tempEmpSer.viewTempEmpList(request,"getDianPuBuMenBieGongZiBiao010"));
				datamap.put("getDianPuBuMenBieGongZiBiao011", this.tempEmpSer.viewTempEmpList(request,"getDianPuBuMenBieGongZiBiao011"));
				datamap.put("getDianPuBuMenBieGongZiBiao012", this.tempEmpSer.viewTempEmpList(request,"getDianPuBuMenBieGongZiBiao012"));
				datamap.put("getDianPuBuMenBieGongZiBiao013", this.tempEmpSer.viewTempEmpList(request,"getDianPuBuMenBieGongZiBiao013"));
				datamap.put("getDianPuBuMenBieGongZiBiao014", this.tempEmpSer.viewTempEmpList(request,"getDianPuBuMenBieGongZiBiao014"));
				datamap.put("getDianPuBuMenBieGongZiBiao015", this.tempEmpSer.viewTempEmpList(request,"getDianPuBuMenBieGongZiBiao015"));
				datamap.put("getDianPuBuMenBieGongZiBiao016", this.tempEmpSer.viewTempEmpList(request,"getDianPuBuMenBieGongZiBiao016"));
				datamap.put("getDianPuBuMenBieGongZiBiao017", this.tempEmpSer.viewTempEmpList(request,"getDianPuBuMenBieGongZiBiao017"));
				datamap.put("getDianPuBuMenBieGongZiBiao018", this.tempEmpSer.viewTempEmpList(request,"getDianPuBuMenBieGongZiBiao018"));
				datamap.put("getDianPuBuMenBieGongZiBiao019", this.tempEmpSer.viewTempEmpList(request,"getDianPuBuMenBieGongZiBiao019"));
				datamap.put("getDianPuBuMenBieGongZiBiao020", this.tempEmpSer.viewTempEmpList(request,"getDianPuBuMenBieGongZiBiao020"));
				datamap.put("getDianPuBuMenBieGongZiBiao021", this.tempEmpSer.viewTempEmpList(request,"getDianPuBuMenBieGongZiBiao021"));
				datamap.put("getDianPuBuMenBieGongZiBiao022", this.tempEmpSer.viewTempEmpList(request,"getDianPuBuMenBieGongZiBiao022"));
				datamap.put("getDianPuBuMenBieGongZiBiao023", this.tempEmpSer.viewTempEmpList(request,"getDianPuBuMenBieGongZiBiao023"));
				datamap.put("getDianPuBuMenBieGongZiBiao024", this.tempEmpSer.viewTempEmpList(request,"getDianPuBuMenBieGongZiBiao024"));
				datamap.put("getDianPuBuMenBieGongZiBiao025", this.tempEmpSer.viewTempEmpList(request,"getDianPuBuMenBieGongZiBiao025"));
				datamap.put("getDianPuBuMenBieGongZiBiao026", this.tempEmpSer.viewTempEmpList(request,"getDianPuBuMenBieGongZiBiao026"));
				datamap.put("getDianPuBuMenBieGongZiBiao027", this.tempEmpSer.viewTempEmpList(request,"getDianPuBuMenBieGongZiBiao027"));
				datamap.put("getDianPuBuMenBieGongZiBiao028", this.tempEmpSer.viewTempEmpList(request,"getDianPuBuMenBieGongZiBiao028"));
				datamap.put("getDianPuBuMenBieGongZiBiao029", this.tempEmpSer.viewTempEmpList(request,"getDianPuBuMenBieGongZiBiao029"));
				datamap.put("getDianPuBuMenBieGongZiBiao030", this.tempEmpSer.viewTempEmpList(request,"getDianPuBuMenBieGongZiBiao030"));
				datamap.put("getDianPuBuMenBieGongZiBiao031", this.tempEmpSer.viewTempEmpList(request,"getDianPuBuMenBieGongZiBiao031"));
				datamap.put("getDianPuBuMenBieGongZiBiao032", this.tempEmpSer.viewTempEmpList(request,"getDianPuBuMenBieGongZiBiao032"));
				datamap.put("getDianPuBuMenBieGongZiBiao033", this.tempEmpSer.viewTempEmpList(request,"getDianPuBuMenBieGongZiBiao033"));
				datamap.put("getDianPuBuMenBieGongZiBiao034", this.tempEmpSer.viewTempEmpList(request,"getDianPuBuMenBieGongZiBiao034"));
				datamap.put("getDianPuBuMenBieGongZiBiao035", this.tempEmpSer.viewTempEmpList(request,"getDianPuBuMenBieGongZiBiao035"));
				datamap.put("getDianPuBuMenBieGongZiBiao036", this.tempEmpSer.viewTempEmpList(request,"getDianPuBuMenBieGongZiBiao036"));
				datamap.put("getDianPuBuMenBieGongZiBiao037", this.tempEmpSer.viewTempEmpList(request,"getDianPuBuMenBieGongZiBiao037"));
				datamap.put("getDianPuBuMenBieGongZiBiao038", this.tempEmpSer.viewTempEmpList(request,"getDianPuBuMenBieGongZiBiao038"));
				datamap.put("getDianPuBuMenBieGongZiBiao039", this.tempEmpSer.viewTempEmpList(request,"getDianPuBuMenBieGongZiBiao039"));
				datamap.put("getDianPuBuMenBieGongZiBiao040", this.tempEmpSer.viewTempEmpList(request,"getDianPuBuMenBieGongZiBiao040"));
				datamap.put("getDianPuBuMenBieGongZiBiao041", this.tempEmpSer.viewTempEmpList(request,"getDianPuBuMenBieGongZiBiao041"));
				datamap.put("getDianPuBuMenBieGongZiBiao042", this.tempEmpSer.viewTempEmpList(request,"getDianPuBuMenBieGongZiBiao042"));
				datamap.put("getDianPuBuMenBieGongZiBiao043", this.tempEmpSer.viewTempEmpList(request,"getDianPuBuMenBieGongZiBiao043"));
				datamap.put("getDianPuBuMenBieGongZiBiao044", this.tempEmpSer.viewTempEmpList(request,"getDianPuBuMenBieGongZiBiao044"));
				datamap.put("getDianPuBuMenBieGongZiBiao045", this.tempEmpSer.viewTempEmpList(request,"getDianPuBuMenBieGongZiBiao045"));
				datamap.put("getDianPuBuMenBieGongZiBiao046", this.tempEmpSer.viewTempEmpList(request,"getDianPuBuMenBieGongZiBiao046"));
				datamap.put("getDianPuBuMenBieGongZiBiao047", this.tempEmpSer.viewTempEmpList(request,"getDianPuBuMenBieGongZiBiao047"));
				datamap.put("getDianPuBuMenBieGongZiBiao048", this.tempEmpSer.viewTempEmpList(request,"getDianPuBuMenBieGongZiBiao048"));
				datamap.put("getDianPuBuMenBieGongZiBiao049", this.tempEmpSer.viewTempEmpList(request,"getDianPuBuMenBieGongZiBiao049"));
				datamap.put("getDianPuBuMenBieGongZiBiao050", this.tempEmpSer.viewTempEmpList(request,"getDianPuBuMenBieGongZiBiao050"));
				datamap.put("getDianPuBuMenBieGongZiBiao051", this.tempEmpSer.viewTempEmpList(request,"getDianPuBuMenBieGongZiBiao051"));
			}else if("278".equals(jspname)){
				datamap.put("getThisMonths", this.tempEmpSer.viewTempEmpList(request,"getThisMonths"));
			}else if("280".equals(jspname)){
				datamap.put("viewVacEmpList", this.companyCalendarSer.viewVacEmpList(request));
			}else if("279".equals(jspname)){
				datamap.put("getLastMonths", this.tempEmpSer.viewTempEmpList(request,"getLastMonths"));
				datamap.put("getThisMonths", this.tempEmpSer.viewTempEmpList(request,"getThisMonths"));
				datamap.put("getGongZiFenXiBiao001", this.tempEmpSer.viewTempEmpList(request,"getGongZiFenXiBiao001"));
				datamap.put("getGongZiFenXiBiao002", this.tempEmpSer.viewTempEmpList(request,"getGongZiFenXiBiao002"));
				datamap.put("getGongZiFenXiBiao003", this.tempEmpSer.viewTempEmpList(request,"getGongZiFenXiBiao003"));
				datamap.put("getGongZiFenXiBiao004", this.tempEmpSer.viewTempEmpList(request,"getGongZiFenXiBiao004"));
				datamap.put("getGongZiFenXiBiao005", this.tempEmpSer.viewTempEmpList(request,"getGongZiFenXiBiao005"));
				datamap.put("getGongZiFenXiBiao006", this.tempEmpSer.viewTempEmpList(request,"getGongZiFenXiBiao006"));
				datamap.put("getGongZiFenXiBiao007", this.tempEmpSer.viewTempEmpList(request,"getGongZiFenXiBiao007"));
				datamap.put("getGongZiFenXiBiao008", this.tempEmpSer.viewTempEmpList(request,"getGongZiFenXiBiao008"));
				datamap.put("getGongZiFenXiBiao009", this.tempEmpSer.viewTempEmpList(request,"getGongZiFenXiBiao009"));
				datamap.put("getGongZiFenXiBiao010", this.tempEmpSer.viewTempEmpList(request,"getGongZiFenXiBiao010"));
				datamap.put("getGongZiFenXiBiao011", this.tempEmpSer.viewTempEmpList(request,"getGongZiFenXiBiao011"));
				datamap.put("getGongZiFenXiBiao012", this.tempEmpSer.viewTempEmpList(request,"getGongZiFenXiBiao012"));
				datamap.put("getGongZiFenXiBiao013", this.tempEmpSer.viewTempEmpList(request,"getGongZiFenXiBiao013"));
				datamap.put("getGongZiFenXiBiao014", this.tempEmpSer.viewTempEmpList(request,"getGongZiFenXiBiao014"));
				datamap.put("getGongZiFenXiBiao015", this.tempEmpSer.viewTempEmpList(request,"getGongZiFenXiBiao015"));
				datamap.put("getGongZiFenXiBiao016", this.tempEmpSer.viewTempEmpList(request,"getGongZiFenXiBiao016"));
				datamap.put("getGongZiFenXiBiao017", this.tempEmpSer.viewTempEmpList(request,"getGongZiFenXiBiao017"));
				datamap.put("getGongZiFenXiBiao018", this.tempEmpSer.viewTempEmpList(request,"getGongZiFenXiBiao018"));
				datamap.put("getJiaBanFenXiBiao001", this.tempEmpSer.viewTempEmpList(request,"getJiaBanFenXiBiao001"));
				datamap.put("getJiaBanFenXiBiao002", this.tempEmpSer.viewTempEmpList(request,"getJiaBanFenXiBiao002"));
				datamap.put("getJiaBanFenXiBiao003", this.tempEmpSer.viewTempEmpList(request,"getJiaBanFenXiBiao003"));
				datamap.put("getJiaBanFenXiBiao004", this.tempEmpSer.viewTempEmpList(request,"getJiaBanFenXiBiao004"));
				datamap.put("getJiaBanFenXiBiao005", this.tempEmpSer.viewTempEmpList(request,"getJiaBanFenXiBiao005"));
				datamap.put("getJiaBanFenXiBiao006", this.tempEmpSer.viewTempEmpList(request,"getJiaBanFenXiBiao006"));
				datamap.put("getJiaBanFenXiBiao007", this.tempEmpSer.viewTempEmpList(request,"getJiaBanFenXiBiao007"));
				datamap.put("getJiaBanFenXiBiao008", this.tempEmpSer.viewTempEmpList(request,"getJiaBanFenXiBiao008"));
				datamap.put("getJiaBanFenXiBiao009", this.tempEmpSer.viewTempEmpList(request,"getJiaBanFenXiBiao009"));
				datamap.put("getJiaBanFenXiBiao010", this.tempEmpSer.viewTempEmpList(request,"getJiaBanFenXiBiao010"));
				datamap.put("getJiaBanFenXiBiao011", this.tempEmpSer.viewTempEmpList(request,"getJiaBanFenXiBiao011"));
				datamap.put("getJiaBanFenXiBiao012", this.tempEmpSer.viewTempEmpList(request,"getJiaBanFenXiBiao012"));
				datamap.put("getJiaBanFenXiBiao013", this.tempEmpSer.viewTempEmpList(request,"getJiaBanFenXiBiao013"));
				datamap.put("getJiaBanFenXiBiao014", this.tempEmpSer.viewTempEmpList(request,"getJiaBanFenXiBiao014"));
				datamap.put("getJiaBanFenXiBiao015", this.tempEmpSer.viewTempEmpList(request,"getJiaBanFenXiBiao015"));
				datamap.put("getJiaBanFenXiBiao016", this.tempEmpSer.viewTempEmpList(request,"getJiaBanFenXiBiao016"));
				datamap.put("getJiaBanFenXiBiao017", this.tempEmpSer.viewTempEmpList(request,"getJiaBanFenXiBiao017"));
				datamap.put("getJiaBanFenXiBiao018", this.tempEmpSer.viewTempEmpList(request,"getJiaBanFenXiBiao018"));
			}else if("312".equals(jspname)){
				datamap.put("payDetailList", this.viewPaParamSer.getPayDetailList(request));
				String monthStr = this.viewPaParamSer.getPayScheduleArDate(request);
				datamap.put("monthStr",monthStr);
				name = name+" "+monthStr;
			}
			// execl导出处理
			try {
				ExcelTemplateUtil.transformXLS(templateFileName, datamap,
						destFileName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		response.setCharacterEncoding("utf-8");
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-Disposition", "attachment;fileName="
				+ new String(name.getBytes("gbk"), "ISO8859-1") + ".xlsx");
		try {
			File file = new File(destFileName);
			InputStream inputStream = new FileInputStream(file);
			OutputStream os = response.getOutputStream();
			byte[] b = new byte[102400];
			int length;
			while ((length = inputStream.read(b)) > 0) {
				os.write(b, 0, length);
			}
			inputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @throws Exception
	 * @return
	 * @Title: runForSqlWrite
	 * @Description: TODO 生成excel
	 * @param @param request
	 * @param @param response
	 * @param @param modelMap
	 * @param @return
	 * @param @throws Exception
	 * @return ModelAndView
	 * @throws
	 */
	@RequestMapping(value = "/exportLOtImportExcelBak")
	@SuppressWarnings("unchecked")
	public void runForSqlWriteBak(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		LinkedHashMap data = ObjectBindUtil.getRequestParamData(request);
		String jspname = (String) data.get("SQL_SEQMEAN");

		LinkedHashMap searchMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List mapList = new ArrayList();
		List mapNameList = new ArrayList();

		// String name="SQLParams";

		ModelMap datamap = this.sqlparamser.writeExcel(request, response,
				modelMap);
		List aliasValueList = (List) datamap.get("ValueList");
		List aliasNameList = (List) datamap.get("NameList");
		String[] columns = (String[]) aliasNameList
				.toArray(new String[aliasNameList.size()]);
		// 将数据编号，作为jsp名,jsp名+报表名 = 报表名
		String name = jspname + "_" + (String) datamap.get("SQL_NM");

		// LinkedHashMap sqlContentmap =
		// this.excelUtilSer.putIntoSqlContentMap(aliasValueList);
		// this.excelUtilSer.exportExcelMoreSheet(
		// request, response, modelMap,sqlContentmap, aliasNameList,
		// null,mapNameList,mapList ,"SQLParams");
		// return new ModelAndView("/disc/sqlparam/"+jspname,modelMap);
		// String [] columns ={};
		this.excelUtilSer.exportExcelByNamePwdForDISC(request, response,
				modelMap, aliasValueList, aliasNameList, columns, name,
				searchMap);

	}

	/**
	 * @Title: testRunForSqlWrite
	 * @Description: TODO 测试是否可以生成excel
	 * @param @param request
	 * @param @param response
	 * @param @param modelMap
	 * @param @throws Exception
	 * @return void
	 * @throws
	 */
	@RequestMapping(value = "/testExcel")
	@SuppressWarnings("unchecked")
	public Map testRunForSqlWrite(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) {
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		LinkedHashMap data = ObjectBindUtil.getRequestParamData(request);
		String jspname = (String) data.get("SQL_SEQMEAN");
		try {
			LinkedHashMap searchMap = ObjectBindUtil
					.getRequestParamData(request);
			AdminBean admin = SessionUtil.getLoginUserFromSession(request);
			List mapList = new ArrayList();
			List mapNameList = new ArrayList();

			String name = "SQLParams";

			ModelMap datamap;

			datamap = this.sqlparamser.writeExcel(request, response, modelMap);

			List aliasValueList = (List) datamap.get("ValueList");
			List aliasNameList = (List) datamap.get("NameList");
			String[] columns = (String[]) aliasNameList
					.toArray(new String[aliasNameList.size()]);
		} catch (Exception e) {
			map.put("statusCode", "300");
			map.put("message", "生成excel失败，请检查参数 ");

		}
		map.put("statusCode", "200");
		map.put("message", "可以导出 ");
		return map;

	}

	/**
	 * 跳转到导出excel加密页面
	 * 
	 * @Description: TODO
	 * @author penghaixia
	 * @date 2014.7.23
	 * @version V1.0
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/encryptExcelForExcel")
	@ResponseBody
	public ModelAndView encryptExcel(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map paramMap = ObjectBindUtil.getRequestParamData(request, "seach_");
		modelMap.put("exportFunName", paramMap.get("exportFunName"));
		modelMap.put("navTabId", paramMap.get("navTabId"));
		modelMap.put("formId", paramMap.get("formId"));
		return new ModelAndView("/disc/autoExcel/encryptExcelForExcel",
				modelMap);
	}

	/**
	 * @throws Exception
	 *             页面查询直接导出
	 * @return
	 * @Title: runForSqlWrite
	 * @Description: TODO 生成excel
	 * @param @param request
	 * @param @param response
	 * @param @param modelMap
	 * @param @return
	 * @param @throws Exception
	 * @return ModelAndView
	 * @throws
	 */

	@SuppressWarnings("unchecked")
	public void runForPageWrite(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		LinkedHashMap data = ObjectBindUtil.getRequestParamData(request);
		String jspname = (String) data.get("SQL_SEQMEAN");

		LinkedHashMap searchMap = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		ModelMap datamap = modelMap;
		datamap.put("searchMap", searchMap);
		// excel报表名称
		String name = (String) datamap.get("XLS_NAME");
		// 报表模板名称
		String tempName = (String) datamap.get("XLS_IN");
		// 报表模板路径
		String webPath = request.getRealPath("/").replace("\\", "/");
		String templateFileName = webPath;
		String destFileName = webPath;
		// resolveTemplatePath (called by transformXLS) picks .xlsx over .xls when both exist
		templateFileName += "/resources/template/report/" + tempName;
		destFileName += "/resources/template/report/" + tempName + "_out.xlsx";

		// execl导出处理
		try {
			ExcelTemplateUtil.transformXLS(templateFileName, datamap, destFileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-Disposition", "attachment;fileName="
				+ new String(name.getBytes("gbk"), "ISO8859-1") + ".xlsx");
		try {
			File file = new File(destFileName);
			InputStream inputStream = new FileInputStream(file);
			OutputStream os = response.getOutputStream();
			byte[] b = new byte[102400];
			int length;
			while ((length = inputStream.read(b)) > 0) {
				os.write(b, 0, length);
			}
			inputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @throws Exception
	 * @return
	 * @Title: runForSqlWrite
	 * @Description: TODO 生成excel
	 * @param @param request
	 * @param @param response
	 * @param @param modelMap
	 * @param @return
	 * @param @throws Exception
	 * @return ModelAndView
	 * @throws
	 */
	@RequestMapping(value = "/exportHrmCard")
	@SuppressWarnings("unchecked")
	public void exportHrmCard(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		try {
			Map dataMap = new HashMap();
			if (admin.getCpnyId().equals("HTSV")
					|| admin.getCpnyId().equals("SPC_SH")
					|| admin.getCpnyId().equals("SPC_HZ")
					|| admin.getCpnyId().equals("SPC_NJ")
					|| admin.getCpnyId().equals("HAE")
					|| admin.getCpnyId().equals("SPC_DL")) {
				if (getData(response, request, dataMap)) {
					File previewFile = new File(request.getSession()
							.getServletContext().getRealPath(
									TempltUtil.PREVIEW_DOC));
					InputStream is = new FileInputStream(previewFile);
					response.reset();
					response
							.setContentType("application/vnd.ms-word;charset=UTF-8");
					response.addHeader("Content-Disposition",
							"attachment; filename=\"" + TempltUtil.PREVIEW_DOC
									+ "\"");
					byte[] b = new byte[1024];
					int len;
					while ((len = is.read(b)) > 0) {
						response.getOutputStream().write(b, 0, len);
					}
					is.close();
					response.getOutputStream().flush();
					response.getOutputStream().close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	

	/**
	 * 转正提醒
	 * @return
	 * @Title: runForSqlWrite
	 * @param @param request
	 * @param @param response
	 * @param @param modelMap
	 * @param @return
	 * @param @throws Exception
	 * @return ModelAndView
	 * @throws
	 */
	@RequestMapping(value = "/exportBecomeRegularWarn")
	@SuppressWarnings("unchecked")
	public void exportBecomeRegularWarn(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		try {
			Map dataMap = new HashMap();
			
				if (getData1(response, request, dataMap)) {
					File previewFile = new File(request.getSession()
							.getServletContext().getRealPath(
									TempltUtil.PREVIEW_DOC1));
					InputStream is = new FileInputStream(previewFile);
					response.reset();
					response
							.setContentType("application/vnd.ms-word;charset=UTF-8");
					response.addHeader("Content-Disposition",
							"attachment; filename=\"" + TempltUtil.PREVIEW_DOC1
									+ "\"");
					byte[] b = new byte[1024];
					int len;
					while ((len = is.read(b)) > 0) {
						response.getOutputStream().write(b, 0, len);
					}
					is.close();
					response.getOutputStream().flush();
					response.getOutputStream().close();
				}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * 转正信息导出内容
	 * */
	
	
	@SuppressWarnings("unchecked")
	private boolean getData1(HttpServletResponse response,
			HttpServletRequest request, Map dataMap) {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map personInfo = (Map)empInfoSer.getPersonalInfoByPid2(request);
		dataMap.put("personInfo1", personInfo);
		/*dataMap.put("hrEmergencyAddressList", empInfoSer
				.gethrEmergencyAddressList(request));
		dataMap.put("hrEducationMatterList", empInfoSer
				.viewEducationMatter(request));
		// 资格事项viewBidMatter
		dataMap.put("viewBidMatter", empInfoSer.viewBidMatter(request));
		// 外语能力
		dataMap.put("viewForeignLanguage", empInfoSer
				.viewSingleForeignLanguage(request));
		// 工作经历
		//dataMap.put("viewExperiencePoint", empInfoSer
		//		.viewSingleExperiencePoint(request));
		dataMap.put("viewExperiencePoint", empInfoSer
				.getExperiencePointList(request));
		// 发令事项
		dataMap.put("hrStartPointList", empInfoSer.getStartPointList(request));
		// 表彰事项
		dataMap.put("viewRecognition", empInfoSer.viewRecognition(request));
		// 惩戒事项
		dataMap.put("viewPunishment", empInfoSer.viewPunishment(request));
		// 培训事项
		dataMap.put("viewTrain", empInfoSer.viewSingleTrain(request));*/


			TempltUtil.toPreviewBJ1(request, TempltUtil.WORD_TEMPLATE1, dataMap);

		return true;
	}
	
/*	@SuppressWarnings("unchecked")
	private boolean getData1(HttpServletResponse response,
			HttpServletRequest request, Map dataMap) {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		List personInfo = (List)empInfoSer.getBGPersonalInfoByPid(request);
		dataMap.put("personInfo", personInfo);

		TempltUtil.toPreviewBJ(request, TempltUtil.REGULAR_WARN, dataMap);
		

		return true;
	}
	*/
	
	@SuppressWarnings("unchecked")
	private boolean getData(HttpServletResponse response,
			HttpServletRequest request, Map dataMap) {

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		Map personInfo = (Map)empInfoSer.getPersonalInfoByPid(request);
		dataMap.put("personInfo", personInfo);
		dataMap.put("hrEmergencyAddressList", empInfoSer
				.gethrEmergencyAddressList(request));
		dataMap.put("hrEducationMatterList", empInfoSer
				.viewEducationMatter(request));
		// 资格事项viewBidMatter
		dataMap.put("viewBidMatter", empInfoSer.viewBidMatter(request));
		// 外语能力
		dataMap.put("viewForeignLanguage", empInfoSer
				.viewSingleForeignLanguage(request));
		// 工作经历
		//dataMap.put("viewExperiencePoint", empInfoSer
		//		.viewSingleExperiencePoint(request));
		dataMap.put("viewExperiencePoint", empInfoSer
				.getExperiencePointList(request));
		// 发令事项
		dataMap.put("hrStartPointList", empInfoSer.getStartPointList(request));
		// 表彰事项
		dataMap.put("viewRecognition", empInfoSer.viewRecognition(request));
		// 惩戒事项
		dataMap.put("viewPunishment", empInfoSer.viewPunishment(request));
		// 培训事项
		dataMap.put("viewTrain", empInfoSer.viewSingleTrain(request));

		if (admin.getCpnyId().equals("HTSV")
				|| admin.getCpnyId().equals("SPC_SH")
				|| admin.getCpnyId().equals("SPC_HZ")
				|| admin.getCpnyId().equals("SPC_NJ")
				|| admin.getCpnyId().equals("HAE")
				|| admin.getCpnyId().equals("SPC_DL")) {
			dataMap.put("photoimage", getImageStr(request,StringUtil.checkNull(personInfo.get("PHOTO_PATH"))));
			TempltUtil.toPreviewBJ(request, TempltUtil.WORD_TEMPLATE, dataMap);
		}

		return true;
	}
	
	//获取图片
	private String getImageStr(HttpServletRequest request,String path) {
        String basePath = request.getSession().getServletContext().getRealPath("");
		String imgFile = basePath + path;
        if ("".equals(path)) {
            imgFile = basePath + "/resources/photo/default.jpg";
        }
		InputStream in = null;
		byte[] data = null;
		try {
			in = new FileInputStream(imgFile);
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(data);
	}

	/**
	 * @throws Exception
	 * @return
	 * @Title: runForSqlWrite
	 * @Description: TODO 生成excel
	 * @param @param request
	 * @param @param response
	 * @param @param modelMap
	 * @param @return
	 * @param @throws Exception
	 * @return ModelAndView
	 * @throws
	 */
	@RequestMapping(value = "/exportHrmCardBatch")
	@SuppressWarnings("unchecked")
	public void exportHrmCardBatch(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String cpnyId = admin.getCpnyId();
		String adminId = admin.getEmpID();
		boolean flag = true;
		String filePath = "/resources/template/hrmCard/" + cpnyId + "/"
				+ adminId;
		String zipFilePath = "/resources/template/hrmCard/" + cpnyId + "/";
		String zipFileName = "hrmCard.zip";
		try {
			File file = new File(request.getSession().getServletContext()
					.getRealPath(filePath));
			// 文件夹不存在就创建
			if (!file.exists() && !file.isDirectory()) {
				file.mkdir();
			}
			if ("HTSV".equals(cpnyId) || "SPC_SH".equals(cpnyId)
					|| "SPC_HZ".equals(cpnyId) || "SPC_NJ".equals(cpnyId)) {
				flag = exportHrmCardBatch_HTSV(response, request, filePath);
			} else if ("SPC_DL".equals(cpnyId) || "HAE".equals(cpnyId)) {
				//flag = exportHrmCardBatch_HAE(response, request, filePath);
				flag = exportHrmCardBatch_HTSV(response, request, filePath);
			}
			if (flag) {
				File zipFile = new File(request.getSession()
						.getServletContext().getRealPath(
								zipFilePath + zipFileName));
				InputStream input = null;
				ZipOutputStream zipOut = new ZipOutputStream(
						new FileOutputStream(zipFile));
				File[] files = file.listFiles();
				for (int i = 0; i < files.length; ++i) {
					input = new FileInputStream(files[i]);
					zipOut.putNextEntry(new ZipEntry("人事卡" + File.separator
							+ files[i].getName()));
					zipOut.setEncoding("GBK");
					int temp = 0;
					while ((temp = input.read()) != -1) {
						zipOut.write(temp);
					}
					input.close();
				}
				zipOut.close();
				// 压缩完删除文件
				for (int i = 0; i < files.length; ++i) {
					files[i].delete();
				}
			}

			// 文件下载
			File zipFile = new File(request.getSession().getServletContext()
					.getRealPath(zipFilePath + zipFileName));
			InputStream is = new FileInputStream(zipFile);
			response.reset();
			response.setContentType("application/vnd.ms-word;charset=UTF-8");
			response.addHeader("Content-Disposition", "attachment; filename=\""
					+ zipFileName + "\"");
			byte[] b = new byte[1024];
			int len;
			while ((len = is.read(b)) > 0) {
				response.getOutputStream().write(b, 0, len);
			}
			is.close();
			response.getOutputStream().flush();
			response.getOutputStream().close();

			zipFile.delete();
			file.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private boolean exportHrmCardBatch_HTSV(HttpServletResponse response,
			HttpServletRequest request, String filePath) {
		List hrmList = this.empInfoSer.getEmpSimpleInfoList(request);
		for (int i = 0; i < hrmList.size(); i++) {
			Map empMap = (Map) hrmList.get(i);
			Map dataMap = new HashMap();
			request.setAttribute("PERSON_ID", empMap.get("PERSON_ID"));
			if (getData(response, request, dataMap)) {
				File previewFile = new File(request.getSession()
						.getServletContext()
						.getRealPath(TempltUtil.PREVIEW_DOC));
				FileAnsiToUTF8.CopySingleFile(request.getSession()
						.getServletContext()
						.getRealPath(TempltUtil.PREVIEW_DOC), request
						.getSession().getServletContext().getRealPath(
								filePath + "/" + empMap.get("EMPID") + "_"
										+ empMap.get("LOCAL_NAME") + ".doc"));
			}
		}
		return true;
	}


	@SuppressWarnings("unchecked")
	private boolean exportHrmCardBatch_HAE(HttpServletResponse response,
			HttpServletRequest request, String filePath) {
		List hrmList = this.empInfoSer.getEmpSimpleInfoList(request);
		for (int i = 0; i < hrmList.size(); i++) {
			Map empMap = (Map) hrmList.get(i);
			Map dataMap = new HashMap();
			request.setAttribute("PERSON_ID", empMap.get("PERSON_ID"));
			createExcelFile(response, request, filePath, empMap);
		}
		return true;
	}

	private boolean createExcelFile(HttpServletResponse response,
			HttpServletRequest request, String filePath, Map empMap) {
		LinkedHashMap data = ObjectBindUtil.getRequestParamData(request);
		String jspname = (String) data.get("SQL_SEQMEAN");
		try {
			ModelMap modelMap = new ModelMap();
			ModelMap datamap = this.sqlparamser.writeExcelFile(request, response,
					modelMap);
			// excel报表名称
			String name = jspname + "_" + (String) datamap.get("SQL_NM");
			// 报表模板名称
			String tempName = "exl_autoExcel";
			if (datamap.get("special") != null) {
				tempName = datamap.get("SQL_SEQ").toString();
			}
			// 报表模板路径
			String webPath = request.getRealPath("/").replace("\\", "/");
			String templateFileName = webPath;
			String destFileName = request.getSession().getServletContext().getRealPath(
										filePath + "/" + empMap.get("EMPID") + "_"
												+ empMap.get("LOCAL_NAME") + ".xlsx");
			templateFileName += "/resources/template/report/" + tempName+ ".xls";
			List aliasValueList = (List) datamap.get("ValueList");
			List aliasNameList = (List) datamap.get("NameList");

			// 针对213
			if ("213".equals(jspname)) {
				datamap.put("personInfo", empInfoSer
						.getPersonalInfoByPid(request));
				datamap.put("hrEmergencyAddressList", empInfoSer
						.gethrEmergencyAddressList(request));
				// 家庭信息
				datamap.put("FamilyList", empInfoSer.gethrFamilyList(request));
				// 学历事项
				datamap.put("hrEducationMatterList", empInfoSer
						.viewEducationMatter(request));
				// 工作经历
				datamap.put("hrExperiencePointList", empInfoSer
						.getExperiencePointList(request));
				// 外语能力
				datamap.put("viewForeignLanguage", empInfoSer
						.viewForeignLanguage(request));
				// 资格事项viewBidMatter
				datamap.put("viewBidMatter", empInfoSer.viewBidMatter(request));

				try {
					InputStream is = new FileInputStream(templateFileName);
					Workbook workBook = ExcelTemplateUtil.transformXLS(is, datamap);
					Sheet sheet = workBook.getSheetAt(0);
					String path = request.getSession().getServletContext()
							.getRealPath("/");
					Drawing<?> patriarch = sheet.createDrawingPatriarch();
					CreationHelper anchorHelper = workBook.getCreationHelper();
					for (int i = 0; i < aliasValueList.size(); i++) {
						HashMap valueMap = (HashMap) aliasValueList.get(i);
						// 将图片以字节流的方式输入输出
						String picture = StringUtil.checkNull(valueMap
								.get("PHOTO_PATH"));
						File picFile = new File(path + "/" + picture);
						if (picture != null && !"".equals(picture)) {
							if (picFile.exists()) {
								ByteArrayOutputStream bos = new ByteArrayOutputStream();
								BufferedImage BufferImg = ImageIO.read(picFile);
								ImageIO.write(BufferImg, "JPEG", bos);
								ClientAnchor anchor = anchorHelper.createClientAnchor();
								anchor.setDx1(0); anchor.setDy1(0); anchor.setDx2(1023); anchor.setDy2(255);
								anchor.setCol1(9); anchor.setRow1(3); anchor.setCol2(11); anchor.setRow2(9);
								patriarch.createPicture(anchor, workBook
										.addPicture(bos.toByteArray(),
												Workbook.PICTURE_TYPE_JPEG));
							}
						}
					}
					OutputStream os = new FileOutputStream(destFileName);
					workBook.write(os);
					is.close();
					os.flush();
					os.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if ("232".equals(jspname)) {
				datamap.put("personInfo", empInfoSer
						.getPersonalInfoByPid(request));
				// 紧急联系人
				datamap.put("hrEmergencyAddressList", empInfoSer
						.gethrEmergencyAddressList(request));
				// 家庭信息
				// datamap.put("FamilyList",
				// empInfoSer.gethrFamilyList(request));
				// 学历事项
				datamap.put("hrEducationMatterList", empInfoSer
						.viewEducationMatter(request));
				// 工作经历
				datamap.put("hrExperiencePointList", empInfoSer
						.getExperiencePointList(request));
				// 外语能力
				datamap.put("viewForeignLanguage", empInfoSer
						.viewForeignLanguage(request));
				// 资格事项viewBidMatter
				datamap.put("viewBidMatter", empInfoSer.viewBidMatter(request));
				// 发令事项
				datamap.put("hrStartPointList", empInfoSer
						.getStartPointList(request));
				// 表彰事项
				datamap.put("viewRecognition", empInfoSer
						.viewRecognition(request));
				// 惩戒事项
				datamap.put("viewPunishment", empInfoSer
						.viewPunishment(request));
				// 培训事项
				datamap.put("viewTrain", empInfoSer.viewSingleTrain(request));

				try {
					InputStream is = new FileInputStream(templateFileName);
					Workbook workBook = ExcelTemplateUtil.transformXLS(is, datamap);
					Sheet sheet = workBook.getSheetAt(0);
					String path = request.getSession().getServletContext()
							.getRealPath("/");
					Drawing<?> patriarch = sheet.createDrawingPatriarch();
					CreationHelper anchorHelper = workBook.getCreationHelper();
					for (int i = 0; i < aliasValueList.size(); i++) {
						HashMap valueMap = (HashMap) aliasValueList.get(i);
						// 将图片以字节流的方式输入输出
						String picture = StringUtil.checkNull(valueMap
								.get("PHOTO_PATH"));
						File picFile = new File(path + "/" + picture);
						if (picture != null && !"".equals(picture)) {
							if (picFile.exists()) {
								ByteArrayOutputStream bos = new ByteArrayOutputStream();
								BufferedImage BufferImg = ImageIO.read(picFile);
								ImageIO.write(BufferImg, "JPEG", bos);
								ClientAnchor anchor = anchorHelper.createClientAnchor();
								anchor.setDx1(0); anchor.setDy1(0); anchor.setDx2(1023); anchor.setDy2(255);
								anchor.setCol1(9); anchor.setRow1(3); anchor.setCol2(11); anchor.setRow2(9);
								patriarch.createPicture(anchor, workBook
										.addPicture(bos.toByteArray(),
												Workbook.PICTURE_TYPE_JPEG));
							}
						}
					}
					OutputStream os = new FileOutputStream(destFileName);
					workBook.write(os);
					is.close();
					os.flush();
					os.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
}