/*
 * @(#)DateTag.java 1.0 2006-12-11 下午01:14:31
 *
 *Copyright 2001 - 2006 AIT. All Rights Reserved.
 */
package com.ait.web.taglib;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;

import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

import com.ait.sys.bean.AdminBean;
import com.ait.web.util.SessionUtil;

/**
 * Copyright: AIT (c) Company: AIT
 * 
 * @author kelly (wangliwei@ait.net.cn)
 * @Date 2006-12-11 下午01:14:31
 * @version 1.0
 * 
 */

@SuppressWarnings("serial")
@Component
public class DateTag extends RequestContextAwareTag {

	protected String yearName = null;

	protected String monthName = null;

	protected String yearSelected = null;

	// DateUtil.getToday("yyyy");

	protected String monthSelected = null;

	// DateUtil.getToday("MM");

	protected String yearMinus = null;

	protected String yearPlus = null;

	protected String onChange = null;

	protected boolean monthSelectedOnly = false;
	
	protected String limit = null;

	public int doStartTagInternal() throws JspException {

		if (yearName != null) {
			yearName = eval("yearName", yearName, Object.class).toString();
		} else {
			yearName = "YYYY";
		}
		if (monthName != null) {
			monthName = eval("monthName", monthName, Object.class).toString();
		}
		// else {
		// monthName = "MM";
		// }
		
		if (limit != null) {
			limit = eval("limit", limit, Object.class).toString();
		} else {
			limit = "";
		}
		if (yearSelected != null && !yearSelected.equals("")) {
			Object object = eval("yearSelected", yearSelected, Object.class);
			if (object == null || object.equals("")) {
				yearSelected = this.getToday("yyyy");
			} else {
				yearSelected = object.toString();
			}
		} else {
			if(limit.equals("")){
				yearSelected = this.getToday("yyyy");
			}
		}
		if (monthSelected != null && !monthSelected.equals("")) {
			Object object = eval("monthSelected", monthSelected, Object.class);
			if (object == null || object.equals("")) {
				monthSelected = this.getToday("MM");
			} else {
				monthSelected = object.toString();
				if(monthSelected.length() == 1)
					monthSelected = "0"+monthSelected ;
			}
		} else {
			if(limit.equals("")){
				monthSelected = this.getToday("MM");
			}
		}
		if (yearMinus != null) {
			yearMinus = eval("yearMinus", yearMinus, Object.class).toString();
		} else {
			yearMinus = "2";
		}
		if (yearPlus != null) {
			yearPlus = eval("yearPlus", yearPlus, Object.class).toString();
		} else {
			yearPlus = "8";
		}
		if (onChange != null) {
			onChange = eval("onChange", onChange, Object.class).toString();
		} else {
			onChange = "";
		}

		
		
		HttpServletRequest request = (HttpServletRequest) pageContext
				.getRequest();

		JspWriter writer = pageContext.getOut();

		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		try {
			//MM
			if (monthName != null) {
				writer.print("<select id = \"" + monthName + "\" name=\"" + monthName + "\"");
				if (!"".equals(onChange)) {
					writer.print(" onChange=\"");
					writer.print(onChange);
					writer.print("\" ");
				}
				writer.println(" class=\"input_select_short\">");
				
				if(!limit.equals("")&&limit.toLowerCase().equals("all")){
					writer.print("<option value=''" );
					if (("").equals(monthSelected)) {
						writer.print(" selected ");
					}
					if("ko".equals(admin.getLanguage())){
						writer.print(" >선택하세요</option>");
					}else if("zh".equals(admin.getLanguage())){
						writer.print(" >请选择</option>");
					}else if("vi".equals(admin.getLanguage())){
						writer.print(" >Lựa chọn</option>");
					}else{
						writer.print(" >select</option>");
					}
				}
				for (int j = 1; j <= 12; j++) {

					String tmp = "";
					if (j < 10) {
						tmp = "0";
					}
					if (!(tmp + j).equals(monthSelected) && monthSelectedOnly) {
						continue;
					}
					writer.print("<option value=\"" + tmp + j + "\"");
					if ((tmp + j).equals(monthSelected)) {
						if(!("").equals(monthSelected)){
							writer.print(" selected ");
						}
					}
					writer.println(">");
					writer.print(tmp + j);
					writer.println("</option>");

				}
				writer.println("</select>");
			}
			
			// YYYY
			writer.print("<select id= \"" + yearName + "\" name=\"" + yearName + "\"");

			if (!"".equals(onChange)) {
				writer.print(" onChange=\"");
				writer.print(onChange);
				writer.print("\" ");
			}

			writer.println(" class=\"input_select_short\">");

			int i = 0;
			int minus = Integer.parseInt(yearMinus);
			int plus = Integer.parseInt(yearPlus);
			
			if(!limit.equals("")&&limit.toLowerCase().equals("all")){
				writer.print("<option value=''" );
				if (("").equals(yearSelected)) {
					writer.print(" selected ");
				} 
				if("ko".equals(admin.getLanguage())){
					writer.print(" >선택하세요</option>");
				}else if("zh".equals(admin.getLanguage())){
					writer.print(" >请选择</option>");
				}else if("vi".equals(admin.getLanguage())){
					writer.print(" >Lựa chọn</option>");
				}else{
					writer.print(" >select</option>");
				}
			}
			for (i = 0 - minus; i <= plus; i++) {

				writer.print("<option value=\""
						+ (Integer.parseInt(this.getToday("yyyy")) + i)
						+ "\"");

				String a=String.valueOf((Integer.parseInt(this.getToday("yyyy")) + i));
				if (("" + (Integer.parseInt(this.getToday("yyyy")) + i))
						.equals(yearSelected)) {
					
					if(!("").equals(yearSelected)){
						writer.print(" selected ");
					}
				}
				writer.println(">");
				writer.print(""
						+ (Integer.parseInt(this.getToday("yyyy")) + i));
				writer.println("</option>");

			}
			// 适应 9999 缺省选中
			if (yearSelected.equals("9999")) {
				writer.print("<option vlaue=\"9999\" selected>9999</option>");
			}
			writer.println("</select>");
			// writer.println("&nbsp;");

			
			
		} catch (IOException ex) {
			throw new JspTagException(ex.getMessage());
		}

		return EVAL_PAGE;
	}

	/**
	 * @param deduct
	 *            The deduct to set.
	 */
	public void setYearName(String yearName) {
		this.yearName = yearName;
	}

	@SuppressWarnings("unchecked")
	private Object eval(String attName, String attValue, Class clazz) throws JspException {
		Object obj = ExpressionEvaluatorManager.evaluate(attName, attValue, clazz, this, pageContext);
		if (obj == null) {
			return "";
		} else {
			return obj;
		}
	}

	/**
	 * @param monthName
	 *            The codeClass to set.
	 */
	public void setMonthName(String monthName) {
		this.monthName = monthName;
	}

	/**
	 * @param selected
	 *            The selected to set.
	 */
	public void setYearSelected(String yearSelected) {
		this.yearSelected = yearSelected;
	}

	/**
	 * @param onChange
	 *            The onChange to set.
	 */
	public void setMonthSelected(String monthSelected) {
		this.monthSelected = monthSelected;
	}

	public void setYearMinus(String yearMinus) {
		this.yearMinus = yearMinus;
	}

	public void setYearPlus(String yearPlus) {
		this.yearPlus = yearPlus;
	}

	public void setOnChange(String onChange) {
		this.onChange = onChange;
	}

	public void setMonthSelectedOnly(boolean monthSelectedOnly) {
		this.monthSelectedOnly = monthSelectedOnly;
	}
	
	
	public String getLimit() {
		return limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	/**
	 * 根据格式参数返回当前日期
	 * @param pOutformat
	 * @return String
	 */
    private String getToday(String pOutformat) {

        SimpleDateFormat pOutformatter = new SimpleDateFormat(pOutformat,
                java.util.Locale.CHINA);

        String rDateString = null;
        Date vDate = new Date();

        try {
            rDateString = pOutformatter.format(vDate);

        } catch (Exception e) {
        }

        return rDateString;
    }
}
