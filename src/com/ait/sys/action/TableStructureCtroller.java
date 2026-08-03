package com.ait.sys.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;

import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.SyLanguageSer;
import com.ait.sys.service.TableStructureSer;
import com.ait.web.util.SessionUtil;

/**
 * 
* @ClassName: TableStructureCtroller 
* @Description: TODO
* @author yorio youjia@ait.net.cn
* @date Feb 4, 2013 5:07:10 PM 
*
 */
@Controller
@RequestMapping(value = "/sys/tableStructure")
public class TableStructureCtroller {
	Logger logger = Logger.getLogger(TableStructureCtroller.class);

	@Autowired
	private TableStructureSer tableStructureSer;

	@Autowired
	private SyLanguageSer syLanguageSer;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewTableStructureList")
	public ModelAndView viewTableStructureList(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		List tableStructureList = this.tableStructureSer
				.getTableStructureList(request);
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List languageList = syLanguageSer.getSyLanguageListByActivity();
		modelMap.put("interLanguage", admin.getLanguage());// 当前系统的语言
		modelMap.put("languageList", languageList);// 所有语言状态
		modelMap.put("tableStructureList", tableStructureList);
		return new ModelAndView("/sys/tableStructure/viewTableStructureList",
				modelMap);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getParentTreeData")
	@ResponseBody
	public List getParentTreeData(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List tableStructureList = this.tableStructureSer.getTableStructureList(request);
		return tableStructureList;
	}

	
}