package com.ait.web.util;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * 对比两对象并对不同的字段高亮显示
 * 
 * @Copyright: LDCC
 * @Company: LDCC
 * @fileName: BeanUtil.java
 * @Description:
 * @Create date: Feb 13, 2012 11:11:41 AM
 * @Create by: zhoulei(zhoulei@ait.net.cn)
 * @Update Date: Feb 13, 2012 11:11:41 AM
 * @Update by: zhoulei(zhoulei@ait.net.cn)
 * @version 5.1
 */
public class BeanUtil {

	/**
	 * 对比两map并对不同的字段高亮显示
	 * 
	 * @param newObject
	 * @param oldObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Map compareObject(Map newObject, Map oldObject) throws Exception {

		Set<String> newMapKey = newObject.keySet();
		for (Iterator it = newMapKey.iterator(); it.hasNext();) {
			String newKey = (String) it.next();
			String newValue = newObject.get(newKey) != null ? newObject.get(
					newKey).toString() : "";
			String oldValue = oldObject.get(newKey) != null ? oldObject.get(
					newKey).toString() : "";
			if (!newValue.equals(oldValue)) {
				newObject.put(newKey, "<font color=\"red\">" + newValue
						+ "</font>");
			}
		}
		return newObject;
	}
	
	/**
	 * 对比两map，完全相同返回0，有任何不同返回1
	 * 
	 * @param newObject
	 * @param oldObject
	 * @return 0 或者1
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public int compareObjectYN(LinkedHashMap newObject, LinkedHashMap oldObject) throws Exception {
		Set<String> newMapKey = newObject.keySet();
		
		for (Iterator it = newMapKey.iterator(); it.hasNext();) {
			String newKey = (String) it.next();
			String newValue = newObject.get(newKey) != null ? newObject.get(newKey).toString() : "";
			String oldValue = oldObject.get(newKey) != null ? oldObject.get(newKey).toString() : "";
			if (!newValue.equals(oldValue)) {
				return 1;
			}
		}
		return 0;
	}
}