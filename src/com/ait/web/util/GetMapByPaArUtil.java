package com.ait.web.util;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GetMapByPaArUtil {

	@SuppressWarnings("unchecked")
	public static void formularToCN(List formularList, Map itemMap, String flag) {
		
		//工资MAP
		LinkedHashMap paFormularMap = null;

		//考勤MAP
		LinkedHashMap arFormularMap = null;
		try {
			// 公共key,value对
			itemMap.put("CASE", "CASE");
			itemMap.put("WHEN", "WHEN");
			itemMap.put("THEN", "THEN");
			itemMap.put("ELSE", "ELSE");
			itemMap.put("AND", "AND");
			itemMap.put("OR", "OR");
			itemMap.put("<", "<");
			itemMap.put(">", ">");
			itemMap.put("=", "=");
			itemMap.put("+", "+");
			itemMap.put("-", "-");
			itemMap.put("*", "*");
			itemMap.put("/", "/");
			itemMap.put("%", "%");
			itemMap.put("(", "(");
			itemMap.put(")", ")");

			// 进行公式替换
			if (flag.equals("PA")) {
				for (int i = 0; i < formularList.size(); i++) {
					paFormularMap = (LinkedHashMap) formularList.get(i);
					
					paFormularMap.put("CONDITION", StringUtil
							.changeLanguage(paFormularMap.get("CONDITION"),itemMap));
					
					paFormularMap.put("FORMULAR", StringUtil.changeLanguage(
							paFormularMap.get("FORMULAR"), itemMap));
					
				}
			} else {
				for (int i = 0; i < formularList.size(); i++) {
					arFormularMap = (LinkedHashMap) formularList.get(i);
					
					arFormularMap.put("CONDITION", StringUtil
							.changeLanguage(arFormularMap.get("CONDITION"),itemMap));
					
					arFormularMap.put("FORMULAR", StringUtil.changeLanguage(
							arFormularMap.get("FORMULAR"), itemMap));

				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
