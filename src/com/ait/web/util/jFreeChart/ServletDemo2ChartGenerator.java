package com.ait.web.util.jFreeChart;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.time.Day;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

public class ServletDemo2ChartGenerator extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public ServletDemo2ChartGenerator() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		OutputStream out = response.getOutputStream();

		try {
			String type = request.getParameter("type");
			JFreeChart chart = null;
			if (type.equals("pie")) {
				chart = createPieChart();
			} else if (type.equals("bar")) {
				chart = createBarChart();
			} else if (type.equals("time")) {
				chart = createTimeSeriesChart();
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

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
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

	private JFreeChart createTimeSeriesChart() {
		// here we just populate a series with random data...
		TimeSeries series = new TimeSeries("Random Data");
		Day current = new Day(1, Month.JANUARY, 2001);
		for (int i = 0; i < 100; i++) {
			series.add(current, Math.random() * 100);
			current = (Day) current.next();
		}
		XYDataset data = new TimeSeriesCollection(series);
		JFreeChart chart = ChartFactory.createTimeSeriesChart(
				"Time Series Chart", "Date", "Rate", data, true, true, false);
		return chart;
	}

}
