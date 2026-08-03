package com.ait.web.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

@Controller
public class writeTxtUtil {
	// 创建文件
	@SuppressWarnings("unchecked")
	public void runPageWriteTxt(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {

		// 报表名称
		String name = (String) modelMap.get("XLS_NAME");
		// 报表模板名称
		String tempName = (String) modelMap.get("XLS_IN");
		// 报表模板路径
		String webPath = request.getRealPath("/").replace("\\", "/");
		String templateFileName = webPath;
		String destFileName = webPath;
		templateFileName += "resources/template/report/" + tempName + ".txt";

		List dateList = (ArrayList) modelMap.get("exportPayDetailTxtReport");

		try {
			File file = new File(templateFileName);

			BufferedWriter BW = new BufferedWriter(new FileWriter(file));

			String ACCOUNT_NO = "";
			String LOCAL_NAME = "";
			String IDCARD_NO = "";
			String NET_PAY = "";
			 

			if (dateList.size() > 0) {
				for (int i = 0; i < dateList.size(); i++) {
					Map iddq = (Map) dateList.get(i);// qu mei yi hang
					if (dateList.get(i) != null && !dateList.get(i).equals("")) {

						ACCOUNT_NO = iddq.get("ACCOUNT_NO")
								.toString();
						LOCAL_NAME = iddq.get("LOCAL_NAME")
								.toString();
						IDCARD_NO =iddq.get("IDCARD_NO")
								.toString();
						NET_PAY = iddq.get("NET_PAY")
								.toString();
						BW.write(ACCOUNT_NO +'|'+ LOCAL_NAME +'|'+ IDCARD_NO +'|'+ NET_PAY+'|');
						BW.newLine();
					}
				}
			}
			BW.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		response.setCharacterEncoding("utf-8");
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment;fileName="
				+ new String(name.getBytes("gbk"), "ISO8859-1") + ".txt");

		try {
			File file = new File(templateFileName);
			InputStream inputStream = new FileInputStream(file);
			OutputStream os = response.getOutputStream();
			byte[] b = new byte[102400];
			int length;
			while ((length = inputStream.read(b)) > 0) {
				os.write(b, 0, length);
			}
			inputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/*
	 * 设置长度保证身份证长度为20 金额 长度不限
	 */
	public String controllCharCength(String chara) {
		int dif = 0;
		if (hasDigit(chara)) {
			dif = 20 - chara.length();
		} else {
			dif = 20 - chara.length() * 2;

		}

		for (int i = 0; i < dif; i++) {
			chara += " ";

		}

		chara += "|";
		return chara;
	}

	// 判断字符串中是否包含数字

	public boolean hasDigit(String content) {

		boolean flag = false;

		Pattern p = Pattern.compile(".*\\d+.*");

		Matcher m = p.matcher(content);

		if (m.matches())

			flag = true;

		return flag;

	}

}