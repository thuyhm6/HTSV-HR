package com.ait.sys.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ait.disc.action.RetrieveMasterListCtroller;

/**
 * Copyright: LDCC Company: LDCC
 * 
 * @fileName: AffirmCtroller.java
 * @Create date: Jan 16, 2012 10:44:58 PM
 * @Create by: zhouyq(zhouyeqing@ait.net.cn)
 * @version 5.1
 */
@Controller
@RequestMapping(value = "/sys/util")
public class pageExportCtroller {
	Logger logger = Logger.getLogger(pageExportCtroller.class);
	@Autowired
	private RetrieveMasterListCtroller RetrieveMasterListCtroller;

	/**
	 * monthPersonCountInfoSonList 页面导出
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/pageExportUtil")
	public void pageExportUtil(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		String allData = request.getParameter("allData");
		String filename;
		List headList = new ArrayList();
		if (allData != null && !allData.equals("")) {
			String firstDate[] = allData.split("-");
			for (int i = 0; i < firstDate.length; i++) {
				if(i==2){
					continue;
				}
				List innerList = new ArrayList();
				String secondDate[] = firstDate[i].split(",");
				Map map = new HashMap();
				for (int j = 0; j < secondDate.length; j++) {
					if("is_null".equals(secondDate[j])){
					map.put("report" + j, "");
					}else{
						map.put("report" + j, secondDate[j]);
					}
					
				}
				innerList.add(map);
				headList.add(innerList);
			}

		}
		String data[] = allData.split(",");
		/*if("总经理".equals(data[3])){
			filename = "部门统计_职级别";
		} else if("学历".equals(data[1])){
			filename = "部门统计_学历别";
		} else if("正式".equals(data[1])){
			filename = "招聘现况_员工类型";
		} else if("年龄".equals(data[2])){
			filename = "部门统计_年龄别";
		} else if("总经理".equals(data[2])){
			filename = "离职现况_职级";
		} else if("制造企划".equals(data[2])){
			filename = "招聘现况_职种";
		} else {
			filename = "a_file";
		}**/
		filename = "a_file";
		
		modelMap.put("headList", headList);
		modelMap.put("XLS_NAME", filename);
		modelMap.put("XLS_IN", "pageExportUtil");

		RetrieveMasterListCtroller.runForPageWrite(request, response, modelMap);

	}

}
