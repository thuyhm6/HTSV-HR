/*
 * @(#)TimeTag.java 1.0 2007-2-26 下午01:36:31
 *
 *Copyright 2001 - 2006 AIT. All Rights Reserved.
 */
package com.ait.web.taglib;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;

import com.ait.web.exception.GlRuntimeException;

/**
 * Copyright: AIT (c) Company: AIT
 * 
 * @author kelly (wangliwei@ait.net.cn)
 * @Date 2007-2-26 下午01:36:31
 * @version 1.0
 * 
 */
public class TimeTag extends TagSupport {

	protected String name = null;

	protected String spacing = null;
	
	protected String spacing1 = null;
	
	protected String spacing2 = null;

	protected String selected = null;

	protected String onChange = null;

	protected String filling = null;

	protected String style = null;

	protected boolean disabled = false;

	protected String beforeTime = null;

	protected String afterTime = null;

	protected String special = null;// 特殊值 e.g 08:00,09:00...

	public void setSpecial(String special) {
		this.special = special;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOnChange(String onChange) {
		this.onChange = onChange;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}
	
	public void setSpacing(String spacing) {
		this.spacing = spacing;
	}
	
	public void setSpacing1(String spacing1) {
		this.spacing1 = spacing1;
	}
	
	public void setSpacing2(String spacing2) {
		this.spacing2 = spacing2;
	}

	public void setFilling(String filling) {
		this.filling = filling;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public void setBeforeTime(String beforeTime) {
		this.beforeTime = beforeTime;
	}

	public void setAfterTime(String afterTime) {
		this.afterTime = afterTime;
	}

	public int doEndTag() throws JspException {

		if (name != null) {
			name = eval("name", name, Object.class).toString();
		} else {
			name = "time";
		}

		if (selected != null) {
			selected = eval("selected", selected, Object.class).toString();
		} else {
			selected = "";
		}

		if (spacing != null) {
			spacing = eval("spacing", spacing, Object.class).toString();
		} else {
			spacing = "60";
		}
		
		if (spacing1 != null) {
			spacing1 = eval("spacing1", spacing1, Object.class).toString();
		} else {
			spacing1 = "";
		}
		
		if (spacing2 != null) {
			spacing2 = eval("spacing2", spacing2, Object.class).toString();
		} else {
			spacing2 = "";
		}

		if (onChange != null) {
			onChange = eval("onChange", onChange, Object.class).toString();
		} else {
			onChange = "";
		}

		if (onChange != null) {
			onChange = eval("onChange", onChange, Object.class).toString();
		} else {
			onChange = "";
		}

		if (special != null) {
			special = eval("special", special, Object.class).toString();
		} else {
			special = "";
		}

		HttpServletRequest request = (HttpServletRequest) pageContext
				.getRequest();

		JspWriter writer = pageContext.getOut();

		try {

			writer.print("<select id=\"" + name + "\" name=\"" + name + "\"");
			if (!"".equals(onChange)) {
				writer.print(" onChange=\"");
				writer.print(onChange);
				writer.print("\" ");
			}

			if (!"".equals(filling)) {
				writer.print(" ");
				writer.print(filling);
				writer.print(" ");
			}

			if (disabled) {
				writer.print(" disabled");
			}

			if (!"".equals(style)) {
				writer.print(" class=\"" + style + "\">");
			} else {
				writer.print(" class=\"input_select_short\">");
			}
			// ***开始时间
			GregorianCalendar today = new GregorianCalendar();
			List list = new ArrayList();
			if (afterTime != null && !afterTime.equals("")
					&& afterTime.length() == 5) {
				today.set(Calendar.HOUR_OF_DAY,
						Integer.parseInt(afterTime.substring(0, 2)));
				today.set(Calendar.MINUTE,
						Integer.parseInt(afterTime.substring(3, 4) + "0"));
			} else {
				today.set(Calendar.HOUR_OF_DAY, 0);
				today.set(Calendar.MINUTE, 0);
			}

			GregorianCalendar tomorrow = new GregorianCalendar();
			tomorrow.setTimeInMillis(today.getTimeInMillis());
			tomorrow.add(Calendar.DAY_OF_MONTH, 1);
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

			// **结束时间
			if (beforeTime != null && !beforeTime.equals("")
					&& beforeTime.length() == 5) {
				tomorrow = new GregorianCalendar();
				tomorrow.set(Calendar.HOUR_OF_DAY,
						Integer.parseInt(beforeTime.substring(0, 2)));
				tomorrow.set(Calendar.MINUTE,
						Integer.parseInt(beforeTime.substring(3, 4) + "0"));
			}
			// ************************************************
			// *************************************************************
			boolean flag = true;

			if (null != special && !"".equals(special)) {
				String[] specialArray = special.split(",");
				for(String str : specialArray){
					writer.print("<option value=\"" + str + "\"");

					if (str.equals(selected)) {
						writer.print(" selected ");
						flag = false;
					}
					writer.print(">");
					writer.print(str);
					writer.print("</option>");
				}
			} else {
				if (beforeTime != null && !beforeTime.equals("") && afterTime != null && !afterTime.equals("")) {
					SimpleDateFormat df = new SimpleDateFormat("HH:mm");
					Date bTime = df.parse(beforeTime);
					Date aTime = df.parse(afterTime);
					int result_ab = bTime.compareTo(aTime);
					if (result_ab < 0) {// 判断有没有次日情况时间时：
						// 先从开始时间到00:00
						today.set(Calendar.HOUR_OF_DAY,Integer.parseInt(afterTime.substring(0, 2)));
						today.set(Calendar.MINUTE,Integer.parseInt(afterTime.substring(3, 4) + "0"));

						tomorrow = new GregorianCalendar();
						tomorrow.set(Calendar.HOUR_OF_DAY, 24);
						tomorrow.set(Calendar.MINUTE, 0);
						while (today.before(tomorrow) || today.equals(tomorrow)) {

							String time = sdf.format(today.getTime());

							writer.print("<option value=\"" + time + "\"");

							if (time.equals(selected)) {
								writer.print(" selected ");
								flag = false;
							}
							writer.print(">");
							writer.print(time);
							writer.print("</option>");

							today.add(Calendar.MINUTE,Integer.parseInt(spacing));

						}

						if (!beforeTime.equals("00:00")) { // 再从00：00到结束时间:---00:00点叠加，所以需判断
							GregorianCalendar today1 = new GregorianCalendar();
							today1.set(Calendar.HOUR_OF_DAY, 24);
							today1.set(Calendar.MINUTE, 0);

							GregorianCalendar tomorrow1 = new GregorianCalendar();
							tomorrow1.setTimeInMillis(today1.getTimeInMillis());
							tomorrow1.add(Calendar.DAY_OF_MONTH, 1);
							tomorrow1.set(Calendar.HOUR_OF_DAY, Integer.parseInt(beforeTime.substring(0, 2)));
							tomorrow1.set(Calendar.MINUTE,Integer.parseInt(beforeTime.substring(3, 4) + "0"));
							while (today1.before(tomorrow1) || today1.equals(tomorrow1)) {
								String time = sdf.format(today1.getTime());
								writer.print("<option value=\"" + time + "\"");
								if (time.equals(selected)) {
									writer.print(" selected ");
									flag = false;
								}
								writer.print(">");
								writer.print(time);
								writer.print("</option>");
								today1.add(Calendar.MINUTE,Integer.parseInt(spacing));

							}
						}

					} else {

						while (today.before(tomorrow) || today.equals(tomorrow)) {
							String time = sdf.format(today.getTime());
							writer.print("<option value=\"" + time + "\"");
							if (time.equals(selected)) {
								writer.print(" selected ");
								flag = false;
							}
							writer.print(">");
							writer.print(time);
							writer.print("</option>");

							today.add(Calendar.MINUTE,Integer.parseInt(spacing));

						}

					}
					// ///////////////////////////////////////////////////////////
				} else if (spacing1 != null && !spacing1.equals("")) {
					int i = 2;
					String spacingTemp = "";
					while (today.before(tomorrow) || today.equals(tomorrow)) {
						String time = sdf.format(today.getTime());
						writer.print("<option value=\"" + time + "\"");
						if (time.equals(selected)) {
							writer.print(" selected ");
							flag = false;
						}
						writer.print(">");
						writer.print(time);
						writer.print("</option>");
						if (i % 2 == 0) {
						    spacingTemp = spacing1;
						} else {
						    spacingTemp = spacing2;
						}
						today.add(Calendar.MINUTE, Integer.parseInt(spacingTemp));
						i++;
					}
				} else {

					while (today.before(tomorrow) || today.equals(tomorrow)) {
						String time = sdf.format(today.getTime());
						writer.print("<option value=\"" + time + "\"");
						if (time.equals(selected)) {
							writer.print(" selected ");
							flag = false;
						}
						writer.print(">");
						writer.print(time);
						writer.print("</option>");

						today.add(Calendar.MINUTE, Integer.parseInt(spacing));

					}
				}
			}

			writer.print("</select>");

		} catch (IOException ex) {

			throw new JspTagException(ex.getMessage());
		} catch (Exception e) {

			throw new GlRuntimeException("Time tag Exception.", e);
		}

		return EVAL_PAGE;
	}

	// ***********************************************

	private Object eval(String attName, String attValue, Class clazz)
			throws JspException {
		Object obj = ExpressionEvaluatorManager.evaluate(attName, attValue,
				clazz, this, pageContext);
		if (obj == null) {
			// throw new NullAttributeException(attName, attValue);
			return "";
		} else {
			return obj;
		}
	}

}
