package com.ait.report.pa.action;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.FileBufferedOutputStream;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.ModelAndView;

import com.ait.report.pa.service.PaReportC04Ser;
import com.ait.report.pa.service.PaReportC11Ser;
import com.ait.sys.bean.AdminBean;
import com.ait.web.config.ConfigurationException;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.StringUtil;
import com.ait.web.util.UserConfiguration;

@Controller
@RequestMapping(value = "/report/pac11")
public class PaReportC11Ctroller {
	@Autowired
	private PaReportC11Ser paReportC11Ser;
	
	public static UserConfiguration config = UserConfiguration.getInstance("/system.properties");
 
	
	/**
	 * 跳转到工资单页面（按月份查询）(query the pa list info of employee by monthed)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewPaListInfo")
	public ModelAndView viewPaTranserInfoList(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		//modelMap.put("paTranserList",this.paReportSer.getPaTranserList(request));
		//modelMap.put(UiUtil.TOTAL_COUNT_NAME,this.paReportSer.getPaTranserListCnt(request));
		String ddate = "";
	    Date dt = new Date();
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
	    ddate=sdf.format(dt);
	    String b[] = ddate.split("-");
	    
	    modelMap.put("YEAR", b[0].trim().toString());
	    modelMap.put("MONTH", b[1].trim().toString());
	    AdminBean admin = SessionUtil.getLoginUserFromSession(request);
	    modelMap.put("defaultCpny",admin.getCpnyId());
	    
		
		return new ModelAndView("/report/pac11/viewPaListInfo",modelMap);
	}
	
	/**
	 * 工资单页面导出pdf（按月份查询）(query the pa list info of employee by monthed)
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@RequestMapping(value = "/jasperServlet")
	public void retrievePaJasperReportPayrollData(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		
		Logger.getLogger("C04工资单------------开始生成--------111111");
		
	 
        AdminBean admin = SessionUtil.getLoginUserFromSession(request);
  
        String CpnyId = admin.getCpnyId();
        String servletPath = request.getRealPath("");
		// 报表参数
		Map parameters = new HashMap();
		// gain report file name
		String reportId = StringUtil.checkNull(request.getParameter("reportName"));
		String pamonth = StringUtil.checkNull(request.getParameter("pamonth"));
		String deptid = StringUtil.checkNull(request.getParameter("deptid"));
		String empid = StringUtil.checkNull(request.getParameter("empid"));
		String emp_type_code = StringUtil.checkNull(request.getParameter("emp_type_code"));
		//取得数据集合
		List sourceList = new ArrayList() ;
		String reportFileName="";
		 
		if (!reportId.equals("")) {
		 
			int init = servletPath.indexOf("\\");
			String server = "win";
			if (init <0) { // WebServer的os为unix/linux
				server = "u/l";
			}
			if ("win".equals(server)) {
			//	reportFileName = servletPath + "\\resources\\temp\\download\\" + CpnyId+ "\\"+ reportId + ".jasper";
			    reportFileName = servletPath + "\\resources\\reportFile\\" + CpnyId+ "\\"+ reportId + ".jasper";
						
			} else {
			 //	String filePath = config.getString("hrm.photo.read.ftp");
			 //	reportFileName = filePath + "/" + CpnyId  + "/" + reportId + ".jasper";
			 	//reportFileName = filePath + "/resources/temp/download/" + CpnyId + "/" + reportId + ".jasper";
				
			  	reportFileName = servletPath + "/resources/reportFile/" + CpnyId + "/" + reportId + ".jasper";
			}
				// reportFileName = servletPath +"WEB-INF"+File.separator+"view"+File.separator+ "report"+File.separator+"pac04" +File.separator+ reportId + ".jasper";
					
 
		 
			parameters.put("pamonth",pamonth);
			parameters.put("CPNY_ID", admin.getCpnyId());
			parameters.put("EMPID",empid);
			parameters.put("deptid",deptid);
			parameters.put("emp_type_code",emp_type_code);
			
			sourceList = (List)this.paReportC11Ser.retrievePaJasperReportPayrollC11Data(request);
			
			 String fileName =createPDFByXmlName(sourceList,parameters, admin, request, reportId, request.getSession(), response);   
			 request.getSession().setAttribute("PDFNAME", fileName);   
			 
				File file = new File(servletPath+"/resources/reportFile/"+CpnyId+ "/pdf/painfo"+CpnyId+".pdf" );
				response.setContentType("application/pdf");
				response.setContentLength((int) file.length());
				response.setHeader("Content-Disposition", "attachment;filename="
						+ "painfo"+CpnyId+".pdf");
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
	}
	 public static String createPDFByXmlName( List mapList,Map parameters,AdminBean admin,HttpServletRequest request,String reportId,HttpSession session,HttpServletResponse response) throws UnsupportedEncodingException, JRException, ServletException, ConfigurationException {   
		     
		 Logger.getLogger("C04工资单----pdf---------开始生成--------");
		 System.out.println("C04工资单----pdf---------开始生成--------");
		     String path = "";   
		     
		     JasperReport jasperReport;   
		     JasperPrint jasperPrint;           
		     String reportFileName="";
		     String servletPath = request.getRealPath("");
		     try {   
		    	 String CpnyId = admin.getCpnyId();
		        
		    		int init = servletPath.indexOf("\\");
					String server = "win";
					if (init <0) { // WebServer的os为unix/linux
						server = "u/l";
					}
					if ("win".equals(server)) {
					      reportFileName = servletPath + "\\resources\\reportFile\\" + CpnyId+ "\\"+ reportId ;//+ ".jasper";
					     // path="WebRoot\\resources\\reportFile\\" + CpnyId+ "\\";
					   } else {
					      reportFileName = servletPath + "/resources/reportFile/" + CpnyId + "/" + reportId ;//+ ".jasper";
					     // path="WebRoot/resources/reportFile/" + CpnyId+ "/";
					   }
		    	 
					Logger.getLogger("------------生成jasper---------");
					System.out.println("-生成jasper------");
		         File file = new File(reportFileName + ".jasper");   
		         //如果jasper文件不存在，就调用jrxml文件编译生成   
		         //JasperCompileManager.compileReportToFile(String sourceFileName, String destFileName)   
		         if (!file.exists()) {   
		             JasperCompileManager.compileReportToFile(reportFileName+ ".jrxml",reportFileName + ".jasper");   
		        }   
		             Logger.getLogger("------------完成生成jasper---------");
		         	System.out.println("--完成生成-----");
		             Logger.getLogger("------------ftp上传 jasper---------");
		         	System.out.println("--ftp上传 jasper----");
		     //        FtpUploadFileSample ftpUploadFileSample=new FtpUploadFileSample(request, response);
		     //        ftpUploadFileSample.uploadForPa(CpnyId,"palist");
		          
		    ///        Logger.getLogger("------------完成ftp上传 jasper---------");
		    ///          String PhotoPath = config.getString("hrm.photo.read.ftp");
		     ///         file = new File(PhotoPath+"/workarea/lotte_ss/lotte/resources/reportFile/"+CpnyId+"/"+reportId+".jasper");   
		          jasperReport = (JasperReport) JRLoader.loadObject(file);   
		              Logger.getLogger("------------读取 jasper---------");
		              System.out.println("--读取 jasper-------");
		         JRDataSource dataSource  = new JRBeanCollectionDataSource(mapList);   
		             //此处为关键，将对象列表设为数据源             
		         jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);   
		             //因模版上没有传入参数，所以此处第二个参数为空   
		   
		         //String fileName = "pdf/"+reportName+session.getId()+".pdf";        
		         String fileName= "pdf/painfo"+CpnyId+".pdf"; 
		         File fi = new File(servletPath+"/resources/reportFile/"+CpnyId+"/pdf");   
		         if(!fi.exists()){   
		             fi.mkdir();   
		         }              
		         //生成方法1   
		         JRPdfExporter exporter = new JRPdfExporter();   
		         exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT, jasperPrint);   
		         exporter.setParameter(JRPdfExporterParameter.CHARACTER_ENCODING,"UTF-8");   
		         exporter.setParameter(JRPdfExporterParameter.OUTPUT_FILE_NAME, servletPath+"/resources/reportFile/"+CpnyId+"/"+fileName);   
		                             //注意此处用的不是JRPdfExporterParameter.OUTPUT_FILE，要用这个，还需新建File   
		         exporter.exportReport();   
		 
		         session.getServletContext().setAttribute("FilePath", servletPath+"/pdf");   
		          return fileName;   
		     }catch(JRException e){   
		         e.printStackTrace();               
		        throw new JRException(e);   
		     }   
		 }  

	 /**
		 * 跳转到导出员工考勤月表页面（按月份查询）
		 * @param request
		 * @param response
		 * @param modelMap
		 * @return ModelAndView
		 * @throws Exception
		 */
		@RequestMapping(value = "/viewArMonthShiftInfo")
		public ModelAndView viewPaDivisionsAttendance(HttpServletRequest request,
					HttpServletResponse response,ModelMap modelMap) throws Exception{
			
			String ddate = "";
		    Date dt = new Date();
		    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		    ddate=sdf.format(dt);
		    String b[] = ddate.split("-");
		    
		    modelMap.put("YEAR", b[0].trim().toString());
		    modelMap.put("MONTH", b[1].trim().toString());
			
			return new ModelAndView("/report/pac03/viewArMonthShiftInfo",modelMap);
		}
		
		
		/**
		 * 工资汇总表   （按月份查询），导出Excel用
		 * @param request
		 * @param response
		 * @param modelMap
		 * @return ModelAndView
		 * @throws Exception
		 */
		@RequestMapping(value = "/viewArMonthShiftInfoTranserExcel")
		public ModelAndView viewPaDivisionsAttendanceTranserExcel(HttpServletRequest request,
					HttpServletResponse response,ModelMap modelMap) throws Exception{
			String ddate = "";
		    Date dt = new Date();
		    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		    ddate=sdf.format(dt);
			modelMap.put("date", ddate);
			modelMap.put("YEAR", request.getParameter("seach_YEAR"));
			modelMap.put("MONTH", request.getParameter("seach_MONTH"));
			
			modelMap.put("dataList",this.paReportC11Ser.getArMonthShiftList(request));
			
			return new ModelAndView("/report/pac04/viewArMonthShiftInfoTranserExcel",modelMap);
		}
}
