package com.ait.web.util.jFreeChart;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class ServletDemo1 extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public ServletDemo1() {
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

			response.setContentType("image/png");
			ChartUtilities.writeChartAsPNG(out, createBarChart(), 400, 300);
		} catch (Exception e) {
			System.err.println(e.toString());
		} finally {
			out.close();
		}
	}

	  
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
		JFreeChart chart = ChartFactory.createBarChart("Bar Chart", "Category",
				"Value", dataset, PlotOrientation.VERTICAL, true, true, false);
		return chart;
	}
}
