package com.ait.web.util.jFreeChart;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletDemo2 extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public ServletDemo2() {
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

		PrintWriter out = new PrintWriter(response.getWriter());
		// try {
		// String param = request.getParameter("chart");
		// response.setContentType("text/html");
		// out.println("<HTML>");
		// out.println("<HEAD>");
		// out.println("<TITLE>JFreeChart Servlet Demo 2</TITLE>");
		// out.println("</HEAD>");
		// out.println("<BODY>");
		// out.println("<H2>JFreeChart Servlet Demo</H2>");
		// out.println("<P>");
		// out.println("Please choose a chart type:");
		// out.println("<FORM ACTION='ServletDemo2' METHOD=POST>");
		// String pieChecked = (param.equals("pie") ? " CHECKED" : "");
		// String barChecked = (param.equals("bar") ? " CHECKED" : "");
		// String timeChecked = (param.equals("time") ? " CHECKED" : "");
		// out.println("<INPUT TYPE='radio' NAME='chart' VALUE='pie'"+
		// pieChecked + "> Pie Chart");
		// out.println("<INPUT TYPE='radio' NAME='chart' VALUE='bar'"+
		// barChecked + "> Bar Chart");
		// out.println("<INPUT TYPE='radio' NAME='chart' VALUE='time'"+
		// timeChecked + "> Time Series Chart");
		// out.println("<P>");
		// out.println("<INPUT TYPE='submit' VALUE='Generate Chart'>");
		// out.println("</FORM>");
		// out.println("<P>");
		// out.println("<IMG src='servlet/ServletDemo2ChartGenerator?type="+
		// param + "' BORDER=1 WIDTH=800 HEIGHT=600/>");
		// out.println("</BODY>");
		// out.println("</HTML>");
		// out.flush();
		// out.close();
		// }
		// catch (Exception e) {
		// System.err.println(e.toString());
		// }
		// finally {
		// out.close();
		// }
		String type = request.getParameter("chart");
		request.setAttribute("type", type);
		System.out.println("in servlet type: " + type);
		response.sendRedirect("/jFreeChart/Chart.jsp?type=" + type);
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

}
