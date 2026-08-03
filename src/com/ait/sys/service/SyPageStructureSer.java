package com.ait.sys.service;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
/**
 * 
 * Copyright:   AIT (c)
 * Company:     AIT
 * @fileName SyPageStructureSer.java
 * @author zhouyeqing(zhouyeqing@ait.net.cn)
 * @Date 2012-1-4 下午05:24:09
 * @version 5.0
 *
 */
public interface SyPageStructureSer {
 
	@SuppressWarnings("unchecked")
	public List getIsCanBeBuildPage(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getPageStructureInfoList(HttpServletRequest request) ;
	
	public String addPageStructure(HttpServletRequest request);
	
	public String deletePageStructure(HttpServletRequest request);
	
    @SuppressWarnings("unchecked")
	public List getAddPageStructureDetail(HttpServletRequest request);
    
	public int getAddPageStructureDetailCnt(HttpServletRequest request);
    
	public String AddPageStructureDetailInfo(HttpServletRequest request);
    
    @SuppressWarnings("unchecked")
	public List getUpdatePageStructureDetail(HttpServletRequest request);
    
	public int getUpdatePageStructureDetailCnt(HttpServletRequest request);
    
	public String deletePageStructureDetail(HttpServletRequest request);
    
	public String updatePageStructureDetailInfo(HttpServletRequest request);
}
