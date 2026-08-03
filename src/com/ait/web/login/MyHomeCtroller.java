package com.ait.web.login;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ait.sys.service.MyHomeSer;

@Controller
@RequestMapping(value = "/myhome")
public class MyHomeCtroller{

	Logger logger = Logger.getLogger(MyHomeCtroller.class);
		
	@Autowired
	private MyHomeSer myHomeSer;
	
	@RequestMapping(value = "/updateModel",method = RequestMethod.POST)
	@ResponseBody
	public void updateModel(HttpServletRequest request)throws Exception{
		logger.info("start...");
		myHomeSer.updateModel(request);		
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getModel",method = RequestMethod.GET)
	@ResponseBody
	public Map getModel(HttpServletRequest request)throws Exception{
		List list = myHomeSer.getModel(request);
		Map model=new HashMap();
		model.put("modelList", list);
				
		return model;		
	}
	

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getHomePurview",method = RequestMethod.GET)
	@ResponseBody
	public Map getHomePurview(HttpServletRequest request)throws Exception{
		Map model=(Map) myHomeSer.getHomePurview(request);				
		return model;		
	}
	
	@RequestMapping(value = "/updateapp",method = RequestMethod.POST)
	@ResponseBody
	public void updateApp(HttpServletRequest request)throws Exception{		
		myHomeSer.updateApp(request);
	}
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getapp",method = RequestMethod.POST)
	@ResponseBody
	public Map getApp(HttpServletRequest request)throws Exception{	
		Map map = new HashMap();
		map.put("Rows", myHomeSer.getApp(request));
		
		return map;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getSyMenu",method = RequestMethod.GET)
	@ResponseBody
	public List getSyMenu(HttpServletRequest request)throws Exception{		
		return myHomeSer.getSyMenu(request);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getTips",method = RequestMethod.GET)
	@ResponseBody
	public Map getTips(HttpServletRequest request)throws Exception{		
		return (Map)myHomeSer.getTips(request);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getTips_home",method = RequestMethod.GET)
	@ResponseBody
	public Map getTips_home(HttpServletRequest request)throws Exception{		
		return (Map)myHomeSer.getTips_home(request);
	}
	
}
