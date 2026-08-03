package com.ait.ar.action.attendanceMintenance;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;

import com.ait.ar.service.ArReadCardSer;
import com.ait.ar.service.AttendanceKeeperSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.config.ConfigurationException;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.ReadFile;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;
import com.ait.web.util.UserConfiguration;

/**
 * Copyright: LDCC Company: LDCC
 * 
 * @fileName: ArReadCardCtroller.java
 * @Description:
 * @Create date: 2012-4-18 上午11:06:58
 * @Create by: jiahc(jiahongchang@ait.net.cn)
 * @version 5.1
 */
@Controller
@RequestMapping(value = "/ar/attendanceMintenance")
public class ArReadCardCtroller {

	Logger logger = Logger.getLogger(ArReadCardCtroller.class);

	@Autowired
	private ArReadCardSer arReadCardSer;
	@Autowired
	private ToolMenuSer toolMenuSer;
	@Autowired
	private AttendanceKeeperSer attendanceKeeperSer ;

	/**
	 * 显示刷卡读取页面(view ArCardRecord)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewArReadCard")
	public ModelAndView viewArCardRecordList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		HttpSession session = request.getSession();
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);

		List getArCardRecordList = this.arReadCardSer
				.getArCardRecordList(request);
		int getArCardRecordListCnt = this.arReadCardSer
				.getArCardRecordListCnt(request);
		
		UserConfiguration config = UserConfiguration.getInstance("/system.properties");
		
		String cardFilePath = config.getString("ar.local.card.path");
		
		List nameList = ReadFile.readTxtFileName(cardFilePath);
		
		modelMap.put("getArCardRecordList", getArCardRecordList);
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, getArCardRecordListCnt);
		modelMap.put("nameList", nameList);

		String supervisorId = admin.getAdminID();
		modelMap.put("defaultCpny", admin.getCpnyId());
		
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "2379")) ;
		
		return new ModelAndView("/ar/attendanceMintenance/viewArReadCard",
				modelMap);
	}
	
	/**
	 * 刷卡读取数据(get Card Interface data)
	 * @param request
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/getCardInterfacedata")
	@ResponseBody
	public Map getCardInterfacedata(HttpServletRequest request)throws Exception{
			
		Map<String, Object> map = new HashMap<String, Object>();
		
		int result = this.arReadCardSer.getCardInterfacedata(request) ;
		
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("ar.alert.message.readsuccess",request));//读取成功
			map.put("navTabId", "ar0210");
		}else{	
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("ar.alert.message.readerror",request));//读取失败，请检查接口数据
		}	
		
		return map;
	}
	
	/**
	 * 从FTP 读取card文件(view ArCardRecord)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/getCardInterfaceFile")
	@ResponseBody
	public Map getCardInterfaceFile(HttpServletRequest request)throws Exception{
			
		Map<String, Object> map = new HashMap<String, Object>();
		
		//文件目录
		UserConfiguration config = UserConfiguration.getInstance("/system.properties");
		
		String cardFilePath = "";
		String server = "";
		String username = "";
		String password = "";
		String ftpPath = "";
		try {
			server = config.getString("ar.ftp.card.server");
			username = config.getString("ar.ftp.card.username");
			password = config.getString("ar.ftp.card.password");
			ftpPath = config.getString("ar.ftp.card.path");
			cardFilePath = config.getString("ar.local.card.path");			
			
			
		} catch (ConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
		
		//从ftp读取文件
		int result = ReadFile.downloadFtpFiles(server,username,password,ftpPath,cardFilePath);	
		
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("ar.alert.message.readsuccess",request));//读取成功
			map.put("navTabId", "ar0210");
		}else{	
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("ar.alert.message.readerror",request));//读取失败，请检查接口数据
		}	
		
		return map;
	}
}
