package com.ait.web.util.jFreeChart;

import java.awt.Color;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.time.Day;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;

@Controller
@RequestMapping(value = "/pa/chartUtil")
public class chartUtilCtroller {
	Logger logger = Logger.getLogger(chartUtilCtroller.class);

	@RequestMapping(value = "/Chart")
	public ModelAndView Chart(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		modelMap.put("compList", "changge");

		return new ModelAndView("/pa/workManagement/Chart", modelMap);

	}

	@RequestMapping(value = "/ServletDemo2")
	@ResponseBody
	public void ServletDemo2(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		PrintWriter out = new PrintWriter(response.getWriter());

		String type = request.getParameter("chart");
		request.setAttribute("type", type);
		System.out.println("in servlet type: " + type);
		response.sendRedirect("/pa/workManagement/Chart");

	}

	@RequestMapping(value = "/ServletDemo2ChartGenerator")
	@ResponseBody
	public void ServletDemo2ChartGenerator(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		OutputStream out = response.getOutputStream();

		try {
			String type = request.getParameter("type");
			JFreeChart chart = null;
			if (type.equals("pie")) {
				chart = createPieChart();
			} else if (type.equals("bar")) {
				chart = createBarChart();
			} else if (type.equals("time")) {// 折线图
				chart = createTimeSeriesChart(request);
			}
			if (chart != null) {
				response.setContentType("image/png");
				ChartUtilities.writeChartAsPNG(out, chart, 800, 600);
			}
		} catch (Exception e) {
			System.err.println(e.toString());
		} finally {
			out.close();
		}
	}

	@RequestMapping(value = "/ServletDemo1")
	@ResponseBody
	public void ServletDemo1(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		OutputStream out = response.getOutputStream();
		try {

			response.setContentType("image/png");
			ChartUtilities.writeChartAsPNG(out, createBarChart2(), 400, 300);
		} catch (Exception e) {
			System.err.println(e.toString());
		} finally {
			out.close();
		}
	}

	private JFreeChart createPieChart() {
		// create a dataset...
		DefaultPieDataset data = new DefaultPieDataset();
		data.setValue("One", new Double(43.2));
		data.setValue("Two", new Double(10.0));
		data.setValue("Three", new Double(27.5));
		data.setValue("Four", new Double(17.5));
		data.setValue("Five", new Double(11.0));
		data.setValue("Six", new Double(19.4));
		JFreeChart chart = ChartFactory.createPieChart("Pie Chart", data, true,
				true, false);
		return chart;
	}

	private JFreeChart createBarChart() {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		dataset.addValue(10.0, "S1", "C1");
		dataset.addValue(4.0, "S1", "C2");
		dataset.addValue(15.0, "S1", "C3");
		dataset.addValue(14.0, "S1", "C4");
		dataset.addValue(-5.0, "S2", "C1");
		dataset.addValue(-7.0, "S2", "C2");
		dataset.addValue(14.0, "S2", "C3");
		dataset.addValue(-3.0, "S2", "C4");
		dataset.addValue(6.0, "S3", "C1");
		dataset.addValue(17.0, "S3", "C2");
		dataset.addValue(-12.0, "S3", "C3");
		dataset.addValue(7.0, "S3", "C4");
		dataset.addValue(7.0, "S4", "C1");
		dataset.addValue(15.0, "S4", "C2");
		dataset.addValue(11.0, "S4", "C3");
		dataset.addValue(0.0, "S4", "C4");
		dataset.addValue(-8.0, "S5", "C1");
		dataset.addValue(-6.0, "S5", "C2");
		dataset.addValue(10.0, "S5", "C3");
		dataset.addValue(-9.0, "S5", "C4");
		dataset.addValue(9.0, "S6", "C1");
		dataset.addValue(8.0, "S6", "C2");
		dataset.addValue(null, "S6", "C3");
		dataset.addValue(6.0, "S6", "C4");
		dataset.addValue(-10.0, "S7", "C1");
		dataset.addValue(9.0, "S7", "C2");
		dataset.addValue(7.0, "S7", "C3");
		dataset.addValue(7.0, "S7", "C4");
		dataset.addValue(11.0, "S8", "C1");
		dataset.addValue(13.0, "S8", "C2");
		dataset.addValue(9.0, "S8", "C3");
		dataset.addValue(9.0, "S8", "C4");
		dataset.addValue(-3.0, "S9", "C1");
		dataset.addValue(7.0, "S9", "C2");
		dataset.addValue(11.0, "S9", "C3");
		dataset.addValue(-10.0, "S9", "C4");
		JFreeChart chart = ChartFactory.createBarChart3D("Bar Chart",
				"Category", "Value", dataset, PlotOrientation.VERTICAL, true,
				true, false);
		return chart;
	}

	private JFreeChart createTimeSeriesChart(HttpServletRequest request) {
		// here we just populate a series with random data...
		TimeSeries series = new TimeSeries("Random Data");
		
		DefaultCategoryDataset linedataset = new DefaultCategoryDataset();
		Date d=new Date();
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");   
	 
		/*String  NOW_DATE=request.getParameter("NOW_DATE");//横坐标
		String PRO_DATE =request.getParameter("PRO_DATE");//横坐标
		String PRO_PRO_DATE =request.getParameter("PRO_PRO_DATE");//横坐标
		
		double vertical1 = Double.parseDouble(request.getParameter("NOW"));//纵坐标
		double vertical2 = Double.parseDouble(request.getParameter("PRO"));//纵坐标
		double vertical3 = Double.parseDouble(request.getParameter("PRO_PRO"));//纵坐标
*/
		 String series1 = request.getParameter("chartName");
		 double vertical1 = Double.parseDouble(request.getParameter("NOW"));//纵坐标
			double vertical2 = Double.parseDouble(request.getParameter("PRO"));//纵坐标
			double vertical3 = Double.parseDouble(request.getParameter("PRO_PRO"));//纵坐标
			String  NOW_DATE=request.getParameter("NOW_DATE");//横坐标
			String PRO_DATE =request.getParameter("PRO_DATE");//横坐标
			String PRO_PRO_DATE =request.getParameter("PRO_PRO_DATE");//横坐标

	
		
		  linedataset.addValue(vertical3, series1, PRO_PRO_DATE);
		  linedataset.addValue(vertical2, series1, PRO_DATE);
		  linedataset.addValue(vertical1, series1, NOW_DATE);
		  
		  
		  
	 
		XYDataset data = new TimeSeriesCollection(series);
		JFreeChart chart = ChartFactory.createLineChart(
				request.getParameter("chartName"), "Date", "Rate", linedataset, PlotOrientation.VERTICAL,true, true, false);
		
		 CategoryPlot plot = chart.getCategoryPlot();
		  // customise the range axis...
		  NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		  rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		  rangeAxis.setAutoRangeIncludesZero(true);
		  rangeAxis.setUpperMargin(0.20);
		  rangeAxis.setLabelAngle(Math.PI / 2.0);
		  
		  
		  chart.setBackgroundPaint(Color.white);//设置背景色
		  
		  CategoryPlot categoryplot = (CategoryPlot)chart.getPlot();
	      LineAndShapeRenderer renderer = (LineAndShapeRenderer)categoryplot.getRenderer();
	     
	      renderer.setBaseItemLabelsVisible(true);//基本项标签显示
	      
		return chart;
	}

	private JFreeChart createBarChart2() {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		dataset.addValue(10.0, "S1", "C1");
		dataset.addValue(4.0, "S1", "C2");
		dataset.addValue(15.0, "S1", "C3");
		dataset.addValue(14.0, "S1", "C4");
		dataset.addValue(-5.0, "S2", "C1");
		dataset.addValue(-7.0, "S2", "C2");
		dataset.addValue(14.0, "S2", "C3");
		dataset.addValue(-3.0, "S2", "C4");
		dataset.addValue(6.0, "S3", "C1");
		dataset.addValue(17.0, "S3", "C2");
		dataset.addValue(-12.0, "S3", "C3");
		dataset.addValue(7.0, "S3", "C4");
		dataset.addValue(7.0, "S4", "C1");
		dataset.addValue(15.0, "S4", "C2");
		dataset.addValue(11.0, "S4", "C3");
		dataset.addValue(0.0, "S4", "C4");
		dataset.addValue(-8.0, "S5", "C1");
		dataset.addValue(-6.0, "S5", "C2");
		dataset.addValue(10.0, "S5", "C3");
		dataset.addValue(-9.0, "S5", "C4");
		dataset.addValue(9.0, "S6", "C1");
		dataset.addValue(8.0, "S6", "C2");
		dataset.addValue(null, "S6", "C3");
		dataset.addValue(6.0, "S6", "C4");
		dataset.addValue(-10.0, "S7", "C1");
		dataset.addValue(9.0, "S7", "C2");
		dataset.addValue(7.0, "S7", "C3");
		dataset.addValue(7.0, "S7", "C4");
		dataset.addValue(11.0, "S8", "C1");
		dataset.addValue(13.0, "S8", "C2");
		dataset.addValue(9.0, "S8", "C3");
		dataset.addValue(9.0, "S8", "C4");
		dataset.addValue(-3.0, "S9", "C1");
		dataset.addValue(7.0, "S9", "C2");
		dataset.addValue(11.0, "S9", "C3");
		dataset.addValue(-10.0, "S9", "C4");
		JFreeChart chart = ChartFactory.createBarChart("Bar Chart", "Category",
				"Value", dataset, PlotOrientation.VERTICAL, true, true, false);
	 

		return chart;
	}
}
