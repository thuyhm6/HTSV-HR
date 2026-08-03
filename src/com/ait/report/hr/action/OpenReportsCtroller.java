package com.ait.report.hr.action;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.ait.Interface.ParentCtroller;
import com.ait.web.util.UserConfiguration;
/**
 * 用于引入报表系统
 * @author xiamingsong
 *
 */
@Controller
@RequestMapping(value = "/report/openReports")
public class OpenReportsCtroller extends ParentCtroller{
    public static UserConfiguration config = UserConfiguration.getInstance("/system.properties");
	/**
	 * 页面跳转
	 */
	@RequestMapping(value="/{reportType}",method = RequestMethod.GET)
	public void gotoOpenReports(){
		
	}
	
}
