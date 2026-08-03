package com.ait.sys.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface EssParamSer {
	
	@SuppressWarnings("unchecked")
	public List getEssParamList(HttpServletRequest request) ;
	
	public Object getEssParam(HttpServletRequest request) ;
	
	public int updateEssParamInfo(HttpServletRequest request);

	@SuppressWarnings("unchecked")
	public List getCpnyList(HttpServletRequest request) ;
	
	@SuppressWarnings("unchecked")
	public List getEssCheckParamList(HttpServletRequest request) ;
	
	public int updateEssCheckParamInfo(HttpServletRequest request);
	
	
	
	
	@SuppressWarnings("unchecked")
	public List getOtConverParamList(HttpServletRequest request) ;
	
	public int getOtConverParamCnt(HttpServletRequest request) ;
	
	public Object getOtConverParam(HttpServletRequest request) ;
	
	public int addOtConverParamInfo(HttpServletRequest request) ;
	
	public int updateOtConverParamInfo(HttpServletRequest request) ;
	
	public int deleteOtConverParam(HttpServletRequest request) ;
	
	/**
	 * 休假基准显示页面
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-12-2 上午11:10:28 
	* @version V1.0
	 */
	public List getVacationStandardManageList(HttpServletRequest request);
	
	/**
	 * 休假基准显示页面条数
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-12-2 上午11:32:42 
	* @version V1.0
	 */
	public int getVacationStandardManageCnt(HttpServletRequest request);

	/**
	 * 查找所有部门的工作地list 不显示已经添加完的地区名称
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-12-2 下午2:20:35 
	* @version V1.0
	 */
	public List getWorkAreaList(HttpServletRequest request);
	
	/**
	 * 添加休假基准
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-12-3 上午11:12:10 
	* @version V1.0
	 */
	public int saveVacationStandardManage(HttpServletRequest request);

	/**
	 * 删除休假基准说明
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-12-3 下午1:57:44 
	* @version V1.0
	 */
	public int deleteVacationStandardManage(HttpServletRequest request);
	
	/**
	 * 休假基准修改页面
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-12-3 下午2:31:22 
	* @version V1.0
	 */
	public Map getManageList(HttpServletRequest request);
	
	/**
	 * 修改休假信息
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author lwei liangwei@ait.net.cn 
	* @date 2013-12-3 下午3:27:00 
	* @version V1.0
	 */
	public int updateVacationStandardManage(HttpServletRequest request);
}
