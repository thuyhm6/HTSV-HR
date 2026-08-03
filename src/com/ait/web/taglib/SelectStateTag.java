package com.ait.web.taglib;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

import com.ait.sys.bean.AdminBean;
import com.ait.promoter.dao.GetTagDataDao;
import com.ait.web.messages.Messages;
import com.ait.web.util.CodeUtil;
import com.ait.web.util.SessionUtil;

/*-- @Create date: 2014.06.11 --*/
/*-- @Create by:   CH.W.G     --*/
@SuppressWarnings("serial")
@Component
public class SelectStateTag extends RequestContextAwareTag {

	public String getOnChange() {
		return onChange;
	}

	public void setOnChange(String onChange) {
		this.onChange = onChange;
	}

	public GetTagDataDao getGetTagDataDao() {
		return getTagDataDao;
	}

	public void setGetTagDataDao(GetTagDataDao getTagDataDao) {
		this.getTagDataDao = getTagDataDao;
	}

	protected String id = null;

	protected String name = null;

	protected String type = null;
	
	protected String parentNo = null;

	protected String selected = null;
	

	protected String onChange = null;

	protected String limit = null; 
	//是否按照汉字的拼音进行排序（PY--拼音排序；EN--英语排序；ZH--汉字排序）
	protected String orderType = null;
	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	@Autowired
	private GetTagDataDao getTagDataDao;	

	/**
	 * tag body
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int doStartTagInternal() throws JspException {

		name = eval("name", name, Object.class).toString();
		parentNo = eval("parentNo", parentNo, Object.class).toString();
		type = eval("type", type, Object.class).toString();
		
		if (id != null) {
			id = eval("id", id, Object.class).toString();
		} else {
			id = "";
		}		
		
		if (selected != null) {
			selected = eval("selected", selected, Object.class).toString();
		} else {
			selected = "";
		}		
		
		if (limit != null) {
			limit = eval("limit", limit, Object.class).toString();
		} else {
			limit = "";
		}
		
		if (onChange != null) {
			onChange = eval("onChange", onChange, Object.class).toString();
		} else {
			onChange = "";
		}
		if (orderType != null) {
			orderType = eval("orderType", orderType, Object.class).toString();
		} else {
			orderType = "";
		}
		getTagDataDao = (GetTagDataDao)getRequestContext().getWebApplicationContext().getAutowireCapableBeanFactory().getBean("getTagDataDaoImpl");
		
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		try {
			
			JspWriter writer = pageContext.getOut();
			
			if(type.equals("CITY2")||type.equals("REGIONTWO")){
				
				
			}else{
				
				if(id!=null && !"".equals(id)){
					writer.print("<select id='"+id+"' name='"+name+"'");
				}else{
					writer.print("<select id='"+name+"' name='"+name+"'");
				}
				if (!"".equals(onChange)) {
					writer.print(" onChange=\"");
					writer.print(onChange);
					writer.print("\" ");
				}
				
				writer.print(">");
				
			}
			
			
			
			if(!limit.equals("")&&limit.toLowerCase().equals("all")){
				if("ko".equals(admin.getLanguage())){
				    writer.print("<option value=''>선택하세요</option>");
				}else if("zh".equals(admin.getLanguage())){
					writer.print("<option value=''>请选择</option>");
				}else if("vi".equals(admin.getLanguage())){
					writer.print("<option value=''>请选择</option>");
				}else{
					writer.print("<option value=''>select</option>");
				}

			}
			
			if(CodeUtil.getCodeMap().size() == 0){
				
				Map paramMap=new LinkedHashMap();
				paramMap.put("PARENT_CODE_NO",parentNo);
				paramMap.put("language",Messages.getLanguage(request));
				paramMap.put("interLanguage",admin.getLanguage());
				paramMap.put("CPNYID", admin.getCpnyId());
				paramMap.put("USER_NO", admin.getUserNo());
				paramMap.put("ORDER_TYPE",orderType);
				
				List codeList = null;
				if(type.equals("STATE")){
					codeList = getTagDataDao.getStateList(paramMap, -1, -1) ;
				}
				if(type.equals("CITY")){
					codeList = getTagDataDao.getCityByStateList(paramMap, -1, -1) ;
				}
				if(type.equals("REGION")){
					codeList = getTagDataDao.getRegionByCityList(paramMap, -1, -1) ;
				}
				if(type.equals("PAYAREA")){
					paramMap.put("deptLevel", 2);
					codeList = getTagDataDao.getBranchList(paramMap) ;
				}
				if(type.equals("BRANCH")){
					paramMap.put("deptLevel", 3);
					codeList = getTagDataDao.getBranchList(paramMap) ;
				}
				// start  create date:2014-09-12  by:wang qiang 
				if(type.equals("SHENG")){
					codeList = getTagDataDao.getShengList(paramMap, -1, -1) ;
				}
				if(type.equals("CITY2")){
					codeList = getTagDataDao.getCity2ByStateList(paramMap, -1, -1) ;
				}
				if(type.equals("REGIONTWO")){
					codeList = getTagDataDao.getRegionByCityTwoList(paramMap, -1, -1) ;
				}
				if(type.equals("CITY_DIS")){
					codeList = getTagDataDao.getCity2ByStateList(paramMap, -1, -1) ;
				}
				if(type.equals("REGION_DIS")){
					codeList = getTagDataDao.getRegionByCityTwoList(paramMap, -1, -1) ;
				}
				
				
				/// end 
				for(Object map : codeList){
					Map temp = (Map) map;
					writer.print("<option value='"+temp.get("CODE_NO")+"'");
					if(temp.get("CODE_NO").equals(selected)){
						writer.print(" selected ");
					}
					writer.print(">"+temp.get("CODE_NAME")+"</option>");
				}
				
			}
			
			if(type.equals("CITY2")||type.equals("REGIONTWO")){
				
				
			}else{
				
			
			writer.print("</select>");
			
			}
			
		} catch (Exception ex) {
			throw new JspTagException(ex.getMessage());
		} 

		return EVAL_PAGE;
	}

	@SuppressWarnings({ "rawtypes" })
	private Object eval(String attName, String attValue, Class clazz) throws JspException {
		Object obj = ExpressionEvaluatorManager.evaluate(attName, attValue, clazz, this, pageContext);
		if (obj == null) {
			return "";
		} else {
			return obj;
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSelected() {
		return selected;
	}

	public void setSelected(String selected) {
		this.selected = selected;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}

	public void setParentNo(String parentNo) {
		this.parentNo = parentNo;
	}
	
	public String getParentNo() {
		return parentNo;
	}

	public String getLimit() {
		return limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

}
