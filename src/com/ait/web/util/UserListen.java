package com.ait.web.util;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

public class UserListen{

    public static String getMACAddress(String ipAddress) { 
		String str = "", strMAC = "", macAddress = ""; 
		try { 
			Process pp = Runtime.getRuntime().exec("nbtstat -a " + ipAddress); 
			InputStreamReader ir = new InputStreamReader(pp.getInputStream()); 
			LineNumberReader input = new LineNumberReader(ir); 
			for (int i = 1; i < 100; i++) { 
				str = input.readLine(); 
				if (str != null) { 
					if (str.indexOf("MAC Address") > 1) { 
						strMAC = str.substring(str.indexOf("MAC Address") + 14, 
						str.length()); 
						break; 
					} 
				} 
			} 
		} catch (IOException ex) { 
			return "Can't Get MAC Address!"; 
		} 
		
//		System.out.println("----- " + strMAC);
		
//		if (strMAC.length() < 17) { 
//			return "Error!"; 
//		} 
		
		if (strMAC.length() < 17) { 
			return strMAC; 
		} 
		
		macAddress = strMAC.substring(0, 2) + ":" + strMAC.substring(3, 5) 
		+ ":" + strMAC.substring(6, 8) + ":" + strMAC.substring(9, 11) 
		+ ":" + strMAC.substring(12, 14) + ":" 
		+ strMAC.substring(15, 17); 

		return macAddress; 
	} 
} 