package com.ait.web.util;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ait.sys.bean.CodeBean;
import com.ait.sys.service.SysSer;


@Component
public class CodeUtil {
	
	Logger logger = Logger.getLogger(CodeUtil.class);
	
	private static LinkedHashMap<String,CodeBean> codeMap = new LinkedHashMap<String,CodeBean>() ;
	
	private static CodeUtil instance = new CodeUtil();
	
	@Autowired  
	private SysSer sysSer ;
	
	private List codeList ;
	
	private List codeLanguageList ;
	
	private List codeParamList ;
	
	public static LinkedHashMap<String,CodeBean> getCodeMap() {
		return codeMap;
	}

	public static CodeUtil getInstance() {
		return instance;
	}
	
	public static CodeUtil getInstance(String codeClass) {
		return instance;
	}
	/**
	 * <p>
	 * 读取Code信息，读入内存对象
	 * <p>
	 * @PostConstruct
	 */
	@SuppressWarnings("unchecked")
	
	public void initCode(){
		
		LinkedHashMap<String,CodeBean> codeBeanMap = new LinkedHashMap<String,CodeBean>() ;
		
		// code 信息
		codeList = sysSer.getCode() ;
		
		// code 语言信息
		codeLanguageList = sysSer.getCodeLanguage() ;
		
		// code 参数信息
		codeParamList = sysSer.getCodeParamList() ;
		
		//从SY_CODE表中查询出所有PARENT_CODE_NO
		List<CodeBean> parentCodeList = sysSer.getParentCodeNo() ;
		
		//循环parentCodeList
		for(CodeBean parentCode : parentCodeList){
			this.setCodeLanguageInfo(parentCode) ;
			
			this.setCodeCpnyInfo(parentCode) ;
			
			this.setCodeChildInfo(parentCode) ;
			/*
			System.out.println(parentCode.getCpnyMap()) ;
			
			System.out.println(parentCode.getLanguageMap()) ;
			
			for(int i = 0 ; i < parentCode.getChildCodeBeanList().size() ; ++ i ){
				CodeBean codeBean = (CodeBean)parentCode.getChildCodeBeanList().get(i) ;
				
				System.out.println(codeBean.getCpnyMap()) ;
				
				System.out.println(codeBean.getLanguageMap()) ;
			}
			*/
			
			codeBeanMap.put(parentCode.getCodeNo(), parentCode) ;
		}
		
		this.codeMap.clear() ;
		
		this.codeMap.putAll(codeBeanMap) ;
		
		// 清空
		
		codeBeanMap = null ;
		
		codeList = null ;
		
		codeLanguageList = null ;
		
		codeParamList = null ;
	}
	
	/**
	 * <p>
	 * 加载code的语言信息
	 * <p>
	 */
	private void setCodeLanguageInfo(CodeBean codeBean){

		// 循环查找code信息,加载语言信息
		for(int i = 0 ; i < codeLanguageList.size(); ++i ){
			Map infoMap = (LinkedHashMap)codeLanguageList.get(i) ;
			
			if( infoMap.get("CODE_NO").toString().equals(codeBean.getCodeNo()) 
				&& infoMap.get("CONTENT") != null && infoMap.get("CONTENT").toString().length() > 0 ){
				
				codeBean.getLanguageMap().put(infoMap.get("LANGUAGE").toString(), infoMap.get("CONTENT").toString()) ;
				
			}
			
		}
	}
	
	/**
	 * <p>
	 * 加载code的公司信息
	 * <p>
	 */
	private void setCodeCpnyInfo(CodeBean codeBean){
		
		// 循环查找code信息,加载公司信息
		for(int i = 0 ; i < codeParamList.size(); ++i ){
			Map infoMap = (LinkedHashMap)codeParamList.get(i) ;
			
			if( infoMap.get("CODE_NO").toString().equals(codeBean.getCodeNo()) ){
				
				codeBean.getCpnyMap().put(infoMap.get("CPNY_ID").toString(), infoMap.get("CODE_NO").toString()) ;
				
			}
			
		}
	}
	
	/**
	 * <p>
	 * 加载code的子code信息
	 * <p>
	 */
	private void setCodeChildInfo(CodeBean codeBean){
	
		// 循环查找code信息,加载子code信息
		for(int i = 0 ; i < codeList.size(); ++i ){
			Map infoMap = (LinkedHashMap)codeList.get(i) ;
			
			if( infoMap.get("PARENT_CODE_NO").toString().equals(codeBean.getCodeNo()) ){
				
				CodeBean code = new CodeBean() ;
				code.setCodeNo(infoMap.get("CODE_NO").toString()) ;
				
				this.setCodeCpnyInfo(code) ;
				this.setCodeLanguageInfo(code) ;
				
				
				codeBean.getChildCodeMap().put(code.getCodeNo(), code) ;
			}
			
		}
	}
	public   void refreshCodes() {
		synchronized (this) {
			if (codeMap != null) {
				codeMap.clear();
				initCode();
			}
		}
	}

}
