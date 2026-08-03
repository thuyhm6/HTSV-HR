package com.ait.disc.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.ait.ar.service.ItemsSer;
import com.ait.ar.service.impl.ArDetailSerImp;
import com.ait.disc.dao.RetrieveSqlMasterDao;
import com.ait.disc.service.SqlParamSer;
import com.ait.sys.bean.AdminBean;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UserConfiguration;

@Service
public class SqlParamSerImpl implements SqlParamSer {
	Logger logger = Logger.getLogger(SqlParamSerImpl.class);
	@Autowired
	private RetrieveSqlMasterDao retrievesqlmasterdao;
	@Autowired
	private ArDetailSerImp arDetailSerImp;
	@Autowired
	private ItemsSer itemsSer;

	/*
	 * Title: ParamToJsp Description:参数生成jsp页面
	 * 
	 * @author 孙鹏
	 * 
	 * @date 2014年10月28日 上午9:33:42
	 * 
	 * @param request
	 * 
	 * @return
	 * 
	 * @throws Exception
	 * 
	 * @seecom.ait.disc.service.SqlParamSer#ParamToJsp(javax.servlet.http.
	 * HttpServletRequest)
	 */
	@Override
	public int ParamToJsp(HttpServletRequest request) throws Exception {
		int returnInt = 0;
		// 页面提交数据
		LinkedHashMap inputData = ObjectBindUtil.getRequestParamData(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		// LinkedHashMap current=new LinkedHashMap();
		try {
			java.util.List resultData = null;
			resultData = this.retrievesqlmasterdao.getSqlParamList(inputData);

			StringBuffer tmp = new StringBuffer();
			String fileName = (String) inputData.get("SQL_SEQ");
			tmp
					.append("<%@ page contentType=\"text/html; charset=UTF-8\" language=\"java\"  errorPage=\"\" %>\r\n");
			tmp.append("<%@include file=\"newheader.jsp\" %>\r\n");
			// 获取properties里的固定格式并且是自动获取值参数名，没有必要显示
			UserConfiguration config = UserConfiguration
					.getInstance("/typecode.properties");
			String paramType = "report.parameter.type.gudingcanshu";
			paramType = config.getString(paramType);
			String[] strtype = paramType.split(",");
			int until = resultData.size();
			for (int i = 0; i < until; i++) {
				Map currentResult = (Map) resultData.get(i);
				String trdisplay = "";
				// byte[] bytes ;
				// String convert = "";
				for (String paramtype : strtype) {
					if (paramtype.equals(currentResult.get("PARAM"))) {
						trdisplay = "<tr  style=\"display:none\"   height= \"50\" onMouseOver=this.style.backgroundColor=\"#F7F7F7\" onMouseOut=this.style.backgroundColor=\"FFFFFF\">\r\n";
						break;
					} else {
						trdisplay = "<tr    height= \"50\" onMouseOver=this.style.backgroundColor=\"#F7F7F7\" onMouseOut=this.style.backgroundColor=\"FFFFFF\">\r\n";
					}

				}
				tmp.append(trdisplay);
				// tmp.append("<tr   height= \"50\" onMouseOver=this.style.backgroundColor=\"#F7F7F7\" onMouseOut=this.style.backgroundColor=\"FFFFFF\">\r\n");
				tmp.append("	<td width=\"15%\" align=\"center\">\r\n");
				tmp.append(currentResult.get("PARAM"));
				tmp.append("	</td>\r\n");

				tmp.append("	<td width=\"25%\" align=\"center\">\r\n");
				tmp.append(currentResult.get("EN_SQL_PARAM_DESC"));
				tmp.append("	</td>\r\n");

				tmp.append("<td width=\"25%\" align=\"center\">\r\n");
				tmp.append(currentResult.get("CN_SQL_PARAM_DESC"));
				tmp.append("</td>\r\n");

				tmp.append("<td width=\"35%\">\r\n");
				tmp.append(currentResult.get("SQL_PARAM_TP_DESC"));
				tmp.append("</td>\r\n");

				tmp.append("</tr>\r\n");
				// tmp.append("<tr>\r\n");
				// tmp.append("<td colspan=\"4\" ></td>\r\n");
				// tmp.append("</tr>\r\n");

			}
			tmp.append("<%@include file=\"newfooter.jsp\" %>\r\n");

			String fileContent = tmp.toString();

			// write jsp file

			returnInt = writeJsp(fileName, fileContent);

		} catch (SQLException se) {
			throw new Exception(se.toString());
		}

		return returnInt;
	}

	/**
	 * @Title: isWindowsOS
	 * @Description: TODO 判断是否为windows系统。是的话，返回true
	 * @param @return
	 * @return boolean
	 * @throws
	 */
	public static boolean isWindowsOS() {
		boolean isWindowsOS = false;
		String osName = System.getProperty("os.name");
		if (osName.toLowerCase().indexOf("windows") > -1) {
			isWindowsOS = true;
		}
		return isWindowsOS;
	}

	/**
	 * @Title: writeJsp
	 * @Description: TODO 输出jsp页面
	 * @param @param fileName
	 * @param @param fileContent
	 * @param @return
	 * @return int
	 * @throws
	 */
	public int writeJsp(String fileName, String fileContent) {
		int returnInt = 0;

		synchronized (this) {
			// write file
			try {
				UserConfiguration config = UserConfiguration
						.getInstance("/typecode.properties");
				boolean isWindows = isWindowsOS(); // 判断是否是windows系统
				String configDir = null;
				if (isWindows) {
					configDir = config.getString("sql.param.jsp.folderWin");
				} else { // 如果是Linux系统
					configDir = config.getString("sql.param.jsp.folderUnix");
				}
				// classpath的文件路径
				String p2 = this.getClass().getResource("").getFile();
				String file = p2.substring(0, p2.indexOf("classes"));
				// OutputStreamWriter osw= new OutputStreamWriter( new
				// FileOutputStream(file+configDir+"\\" + fileName +
				// ".jsp"),"UTF-8");
				FileOutputStream os = new FileOutputStream(new File(file
						+ configDir + fileName + ".jsp"));
				// BufferedWriter bufw = new BufferedWriter(osw);
				// bufw.write(fileContent);
				// bufw.flush();
				// bufw.close();
				os.write(fileContent.getBytes("UTF-8"));
				os.flush();
				os.close();
				returnInt = 1;
			} catch (IOException e) {
				e.printStackTrace();
				returnInt = 0;
			} catch (Exception e) {
				e.printStackTrace();
				returnInt = 0;
			}
		}
		return returnInt;
	}

	/**
	 * @return
	 * @return
	 * @throws Exception
	 * @Title: writeExcel
	 * @Description: TODO
	 * @param @param request
	 * @param @param response
	 * @param @param modelMap
	 * @param @return
	 * @return int
	 * @throws
	 */
	@SuppressWarnings( { "rawtypes", "unchecked" })
	public ModelMap writeExcel(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		LinkedHashMap data = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		String jspname = (String) data.get("SQL_SEQMEAN");
		// 针对129
		if (!"".equals(jspname) && jspname != null) {
			if ("129".equals(jspname)) {
				String seach_APPLY_CODE = request
						.getParameter("seach_APPLY_CODE");
				String ITEM_NO = "";
				if (seach_APPLY_CODE != null && !"".equals(seach_APPLY_CODE)) {
					ITEM_NO = arDetailSerImp.getItemNoOnApplyCode(data);
					data.put("ITEM_NO", ITEM_NO);
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
					data.put("ITEM_NO", ITEM_NO);
				}
			}
			/*if ("138".equals(jspname)) {
				String seach_ITEM_NO = request.getParameter("seach_ITEM_NO");
				String ITEM_NO = "";
				if (seach_ITEM_NO != null && !"".equals(seach_ITEM_NO)) {
					data.put("ITEM_NO", seach_ITEM_NO);
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
					data.put("ITEM_NO", ITEM_NO);
				}
			}*/

			if ("210".equals(jspname)) {
				
				
				String YEAR_DATE = request.getParameter("YEAR_DATE");
				String DAY_DATE = request.getParameter("DAY_DATE");
				String SALARY_DISTIN = request.getParameter("SALARY_DISTIN");
				modelMap.put("SALARY_DISTIN", SALARY_DISTIN);
				modelMap.put("YEAR_DATE", YEAR_DATE);
				modelMap.put("DAY_DATE", DAY_DATE);

			}
		}
		List aliasNameList = new ArrayList();
		List dataList = new ArrayList();
		// 获取properties里的原样输出，不加引号的参数名
		UserConfiguration config = UserConfiguration
				.getInstance("/typecode.properties");
		String paramType = "report.parameter.type.yuanyangcanshu";
		paramType = config.getString(paramType);
		String[] strtype = paramType.split(",");
		// get sql detail
		LinkedHashMap datacopy = new LinkedHashMap();
		datacopy.put("SQL_SEQ", data.get("SQL_SEQMEAN"));
		List resultlist = this.retrievesqlmasterdao.getSqlMaster(datacopy);
		LinkedHashMap result = (LinkedHashMap) resultlist.get(0);
		Clob SQL_STMT = (Clob) result.get("SQL_STMT");
		String sql = null;
		sql = SQL_STMT.getSubString(1, (int) SQL_STMT.length());

		// get param list from db
		List paramResult = this.retrievesqlmasterdao.getSqlParamList(datacopy);
		// param set value
		int until = paramResult.size();
		// 拼接sql将值放入SQL语句
		for (int i = 0; i < until; i++) {
			Map currentResult = (Map) paramResult.get(i);
			// properties定义好原样输出参数名的参数原样输出
			for (String type : strtype) {
				if (type.equalsIgnoreCase((String) currentResult.get("PARAM"))) {
					sql = sql
							.replaceAll("#" + currentResult.get("PARAM") + "#",
									"" + data.get(currentResult.get("PARAM"))
											+ "");
				}
			}
			// //大区类型的参数全部原样输出
			// if(currentResult.get("SQL_PARAM_TP").equals("pay_area")){
			//				
			// String s=(String) data.get(currentResult.get("PARAM"));
			// StringBuffer sb = new StringBuffer();
			// String[] str =s.split(",");
			// for( int i1=0;i1<str.length;i1++ ){
			// str[i1] = str[i1].replaceAll(str[i1],"'" + str[i1] + "'");
			// sb=sb.append(str[i1]+",");
			// }
			// s=sb.toString().substring(0,sb.length()-1);
			// sql = sql.replaceAll("#" + currentResult.get("PARAM")+"#",s);
			// }
			// 普通参数的处理
			sql = sql.replaceAll("#" + currentResult.get("PARAM") + "#", "'"
					+ data.get(currentResult.get("PARAM")) + "'");
			currentResult = null;
		}
		result.put("querySql", sql);
		modelMap.put("SQL_NM", result.get("SQL_NM"));
		// execute sql make excel
		List sqlResult = this.retrievesqlmasterdao.querySql(result);
		sql = null;
		Map indexMap = new HashMap();
		// 取到先执行存储，返回sql语句的报表。
		paramType = "report.parameter.type.zhixingcunchu";
		paramType = config.getString(paramType);
		strtype = paramType.split(",");
		for (String type : strtype) {
			if (datacopy.get("SQL_SEQ").equals(type)) {
				Map CLobMap = (Map) sqlResult.get(0);
				Clob sqlclob = (Clob) CLobMap.get("SQLVALUE");
				String strsql = sqlclob.getSubString(1, (int) sqlclob.length());
				result.put("querySql", strsql);
				sqlResult = this.retrievesqlmasterdao.querySql(result);
			}
		}
		// 判断是否特殊excel报表
		if (result.get("IS_SPECIAL") != null
				&& "Y".equals(result.get("IS_SPECIAL").toString())) {
			modelMap.put("special", "special");
			modelMap.put("SQL_SEQ", result.get("SQL_SEQ"));
		}
		if (null == sqlResult || sqlResult.size() == 0 || sqlResult.isEmpty()) {
			Map error = new HashMap();
			error.put("error", "No data or parameter input error, please re-enter");
			sqlResult.add(error);
		} else if (sqlResult.size() > 65000) {
			Map error = new HashMap();
			error.put("error", "Data over 65,000 lines, please re-select the range.");
			sqlResult.clear();
			sqlResult.add(error);
		}
		indexMap = (Map) sqlResult.get(0);
		// 如果调用生成模板的excel方法，那么需要循环，如果是带密码的excel方法，数据库查出的结果就可以了。
		// dataList=sqlResult;//在这里 把数据库查出的数据直接给了生成excel方法
		Set set = indexMap.keySet();// 用接口实例接口
		Iterator iter = set.iterator();

		while (iter.hasNext()) {// 遍历二次,速度慢
			//lipeng 2017-12-25 英文字段sql不支持空格   所以在此替换
			String replaceStr = iter.next().toString().replace("_", " ");
			aliasNameList.add(replaceStr);
		}
		// for(int i=0;i<sqlResult.size();i++){
		// LinkedHashMap otApplyMap = new LinkedHashMap();
		// otApplyMap = (LinkedHashMap)sqlResult.get(i);
		// LinkedHashMap map = new LinkedHashMap();
		//			
		// for(int j=0;j<aliasNameList.size();j++) {
		// map.put(aliasNameList.get(j),
		// otApplyMap.get(aliasNameList.get(j))!=null?otApplyMap.get(aliasNameList.get(j)).toString():"");
		// }
		// dataList.add(map);
		// }
		modelMap.put("NameList", aliasNameList);
		for(int i=0;i<sqlResult.size();i++){
			Map map = (Map)sqlResult.get(i);
	
		}
		modelMap.put("ValueList", sqlResult);
		// LinkedHashMap sqlContentmap =
		// this.excelUtilSer.putIntoSqlContentMap(dataList);
		// this.excelUtilSer.exportExcel(request, response,
		// modelMap,sqlContentmap, aliasNameList, null);

		// sqlResult.clear();//手动清除list
		return modelMap;
	}

	/**
	 * @return
	 * @return
	 * @throws Exception
	 * @Title: writeExcel
	 * @Description: TODO
	 * @param @param request
	 * @param @param response
	 * @param @param modelMap
	 * @param @return
	 * @return int
	 * @throws
	 */
	@SuppressWarnings( { "rawtypes", "unchecked" })
	public ModelMap writeExcelFile(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		LinkedHashMap data = ObjectBindUtil.getRequestParamData(request,
				"seach_");
		data.put("PERSON_ID", request.getAttribute("PERSON_ID"));

		List aliasNameList = new ArrayList();
		List dataList = new ArrayList();
		// 获取properties里的原样输出，不加引号的参数名
		UserConfiguration config = UserConfiguration
				.getInstance("/typecode.properties");
		String paramType = "report.parameter.type.yuanyangcanshu";
		paramType = config.getString(paramType);
		String[] strtype = paramType.split(",");
		// get sql detail
		LinkedHashMap datacopy = new LinkedHashMap();
		datacopy.put("SQL_SEQ", data.get("SQL_SEQMEAN"));
		List resultlist = this.retrievesqlmasterdao.getSqlMaster(datacopy);
		LinkedHashMap result = (LinkedHashMap) resultlist.get(0);
		Clob SQL_STMT = (Clob) result.get("SQL_STMT");
		String sql = null;
		sql = SQL_STMT.getSubString(1, (int) SQL_STMT.length());

		// get param list from db
		List paramResult = this.retrievesqlmasterdao.getSqlParamList(datacopy);
		// param set value
		int until = paramResult.size();
		// 拼接sql将值放入SQL语句
		for (int i = 0; i < until; i++) {
			Map currentResult = (Map) paramResult.get(i);
			// properties定义好原样输出参数名的参数原样输出
			for (String type : strtype) {
				if (type.equalsIgnoreCase((String) currentResult.get("PARAM"))) {
					sql = sql
							.replaceAll("#" + currentResult.get("PARAM") + "#",
									"" + data.get(currentResult.get("PARAM"))
											+ "");
				}
			}
			sql = sql.replaceAll("#" + currentResult.get("PARAM") + "#", "'"
					+ data.get(currentResult.get("PARAM")) + "'");
			currentResult = null;
		}
		result.put("querySql", sql);
		modelMap.put("SQL_NM", result.get("SQL_NM"));
		// execute sql make excel
		List sqlResult = this.retrievesqlmasterdao.querySql(result);
		sql = null;
		Map indexMap = new HashMap();
		// 取到先执行存储，返回sql语句的报表。
		paramType = "report.parameter.type.zhixingcunchu";
		paramType = config.getString(paramType);
		strtype = paramType.split(",");
		for (String type : strtype) {
			if (datacopy.get("SQL_SEQ").equals(type)) {
				Map CLobMap = (Map) sqlResult.get(0);
				Clob sqlclob = (Clob) CLobMap.get("SQLVALUE");
				String strsql = sqlclob.getSubString(1, (int) sqlclob.length());
				result.put("querySql", strsql);
				sqlResult = this.retrievesqlmasterdao.querySql(result);
			}
		}
		// 判断是否特殊excel报表
		if (result.get("IS_SPECIAL") != null
				&& "Y".equals(result.get("IS_SPECIAL").toString())) {
			modelMap.put("special", "special");
			modelMap.put("SQL_SEQ", result.get("SQL_SEQ"));
		}
		if (null == sqlResult || sqlResult.size() == 0 || sqlResult.isEmpty()) {
			Map error = new HashMap();
			error.put("error", "No data or parameter input error, please re-enter");//无数据或者参数输入错误，请重新输入
			sqlResult.add(error);
		} else if (sqlResult.size() > 65000) {
			Map error = new HashMap();
			error.put("error", "Data over 65,000 lines, please re-select the range.");//数据超过6万5千行，请重新选择范围。
			sqlResult.clear();
			sqlResult.add(error);
		}
		indexMap = (Map) sqlResult.get(0);
		// 如果调用生成模板的excel方法，那么需要循环，如果是带密码的excel方法，数据库查出的结果就可以了。
		// dataList=sqlResult;//在这里 把数据库查出的数据直接给了生成excel方法
		Set set = indexMap.keySet();// 用接口实例接口
		Iterator iter = set.iterator();

		while (iter.hasNext()) {// 遍历二次,速度慢
			aliasNameList.add((String) iter.next());
		}
		modelMap.put("NameList", aliasNameList);
		modelMap.put("ValueList", sqlResult);
		return modelMap;
	}

}
