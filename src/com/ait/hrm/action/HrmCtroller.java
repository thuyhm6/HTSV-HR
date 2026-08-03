package com.ait.hrm.action;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ait.hrm.service.HrmSer;

@Controller
@RequestMapping(value = "/hrm")
public class HrmCtroller {
	Logger logger = Logger.getLogger(HrmCtroller.class);
	@Autowired
	private HrmSer hrmSer;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getDeptTree/{limit}")
	@ResponseBody
	public List getEmpList(HttpServletRequest request,
			@PathVariable("limit")String limit) throws Exception{		
		List info = hrmSer.getDeptTree(request,limit);
		return info;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getBusiness")
	@ResponseBody
	public List getBusiness(HttpServletRequest request) throws Exception{		
		List info = hrmSer.getBusiness(request);
		return info;
	}
}
