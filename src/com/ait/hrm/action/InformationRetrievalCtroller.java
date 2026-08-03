package com.ait.hrm.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.ModelAndView;

//import sun.java2d.pipe.SpanShapeRenderer.Simple;

import com.ait.web.util.StringUtil;

import com.ait.hrm.service.EmpInfoSer;
import com.ait.hrm.service.InformationRetrievalSer;
import com.ait.hrm.service.TransferOrderSer;
import com.ait.org.service.OrgManageSer;
import com.ait.sys.bean.AdminBean;
import com.ait.sys.service.ToolMenuSer;
import com.ait.web.i18n.TipMessage;
import com.ait.web.util.JsonUtil;
import com.ait.web.util.ObjectBindUtil;
import com.ait.web.util.SessionUtil;
import com.ait.web.util.UiUtil;
import com.ait.web.util.UserConfiguration;

/**
 * Copyright:   LDCC
 * Company:     LDCC
 * @fileName: InformationRetrievalCtroller.java
 * @Description:
 * @Create date: 
 * @Create by:  
 * @version 5.1
 */
@Controller
@RequestMapping(value = "/hrm/informationRetrieval")
public class InformationRetrievalCtroller {
	Logger logger = Logger.getLogger(InformationRetrievalCtroller.class);
	@Autowired
	private EmpInfoSer empInfoSer;

	public static UserConfiguration config = UserConfiguration
	.getInstance("/system.properties");
	//测试修改菜单
	@Autowired
	private ToolMenuSer toolMenuSer;
	@Autowired
	private  InformationRetrievalSer informationRetrievalSer;
	@Autowired
	private OrgManageSer orgManageSer;
	@Autowired
	private  TransferOrderSer transferOrderSer;
	/**
	 * 信息搜索
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewEmpRetrieve")
	public ModelAndView viewEmpRetrieveList(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		List getCustomerTableList = this.informationRetrievalSer.getCustomerTableList(request);  //自定义表
		//显示列 
		List  HR_EMPLOYEE_V_LIST = this.informationRetrievalSer.getInfoFieldByTableNameList(request,"HR_EMPLOYEE_V");
		modelMap.put("HR_EMPLOYEE_V_LIST", HR_EMPLOYEE_V_LIST); 
		List  HR_PERSONAL_INFO_V_LIST= this.informationRetrievalSer.getInfoFieldByTableNameList(request,"HR_PERSONAL_INFO_V");
		modelMap.put("HR_PERSONAL_INFO_V_LIST", HR_PERSONAL_INFO_V_LIST); 
		
		modelMap.put("defaultLanguage", admin.getLanguage()); 
		modelMap.put("getCustomerTableList", getCustomerTableList); 
		modelMap.put("defaultCpny", admin.getCpnyId());
		modelMap.put("nationalCode", admin.getOperationCodeNo());
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		 
		return new ModelAndView("/hrm/informationRetrieval/viewEmpRetrieve",modelMap);
	}
	
	
	/**
	 * 信息搜索 查询结果
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewEmpRetrieveShow")
	public ModelAndView viewEmpRetrieveShowList(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String[] cols;
		String[] keys;
		if(!ObjectUtils.toString(request.getParameter("CUST_TABLE")).equals("")){ 
			 //读表
			LinkedHashMap resultTable=informationRetrievalSer.getCustomerTableListByNO(request);
			cols=(resultTable.get("COLNAME").toString()).split(",");
			keys=(resultTable.get("SQLKEY").toString()).split(",");
			request.setAttribute("CondSql", resultTable.get("CONDSQL").toString());
			request.setAttribute("CondSqlCnt", resultTable.get("CONDSQLCNT").toString());
			 
		    modelMap.put("CondSql",resultTable.get("CONDSQL").toString());  // sql String
		 	modelMap.put("SqlKey",resultTable.get("SQLKEY").toString());   //key String
		 	modelMap.put("ColName",resultTable.get("COLNAME").toString());  //title String
		 	modelMap.put("CondSqlCnt",resultTable.get("CONDSQLCNT").toString());  //sql cnt String
		 	modelMap.put("tablename", resultTable.get("TABLE_NAME"));
		 }else{
			cols=request.getParameter("ColName").toString().split(",");
			keys=request.getParameter("SqlKey").toString().split(",");
			 
			modelMap.put("CondSql",request.getParameter("CondSql").toString());  // sql String
		 	modelMap.put("SqlKey",request.getParameter("SqlKey").toString());   //key String
		 	modelMap.put("ColName",request.getParameter("ColName").toString());  //title String
		 	modelMap.put("CondSqlCnt",request.getParameter("CondSqlCnt").toString());  //sql cnt String
		 	modelMap.put("tablename", request.getParameter("tablename").toString());
		 }
	 	List newKeys=new ArrayList();
		for (int i = 0; i < keys.length; i++) {
			String temp="";
			temp=keys[i].trim();
			if(temp.indexOf(".")==-1){
				newKeys.add(temp);
			}else{
				temp=temp.substring(temp.indexOf(".")+1);
				newKeys.add(temp);
			}
			
		}
		 
		try{
			List getEmpRetrieveShowList=informationRetrievalSer.getEmpRetrieveShowList(request);
			int cnt=informationRetrievalSer.getEmpRetrieveShowCnt(request) ;
		    modelMap.put("newKeys", newKeys);     //对应的key List
		    modelMap.put("getEmpRetrieveShowList", getEmpRetrieveShowList);   //内容 list
			modelMap.put(UiUtil.TOTAL_COUNT_NAME, cnt);   
			modelMap.put("ColTitle",cols);    //标题 list
			
			return new ModelAndView("/hrm/informationRetrieval/viewEmpRetrieveShow",modelMap);
		}catch (Exception e) {
		    modelMap.put("tablename", 	 TipMessage.getTipMessage("alert.message.chaxuncuowucongxin",request));// 查询条件有误，请从新设置生成！ 
			modelMap.put("errorFlag", "1");
			 
			return new ModelAndView("/hrm/informationRetrieval/viewEmpRetrieveShow",modelMap); 
		}
	}
	/**
	 * 信息搜索 查询结果导出EXECL
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewEmpRetrieveShowExecl")
	public ModelAndView viewEmpRetrieveShowExecl(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String[] cols=request.getParameter("ColName").toString().split(",");
		String[] keys=request.getParameter("SqlKey").toString().split(",");
	 	List newKeys=new ArrayList();
		for (int i = 0; i < keys.length; i++) {
			String temp="";
			temp=keys[i].trim();
			if(temp.indexOf(".")==-1){
				newKeys.add(temp);
			}else{
				temp=temp.substring(temp.indexOf(".")+1);
				newKeys.add(temp);
			}
		}
		List getEmpRetrieveShowList=informationRetrievalSer.getEmpRetrieveShowListAll(request);
	    modelMap.put("newKeys", newKeys);     //对应的key List
	    modelMap.put("getEmpRetrieveShowList", getEmpRetrieveShowList);   //内容 list
	 
		modelMap.put("ColTitle",cols);    //标题 list
		modelMap.put("colslenth",cols.length); 
	 	modelMap.put("CondSql",request.getParameter("CondSql").toString());  // sql String
	 	modelMap.put("SqlKey",request.getParameter("SqlKey").toString());   //key String
	 	modelMap.put("ColName",request.getParameter("ColName").toString());  //title String
	 	modelMap.put("CondSqlCnt",request.getParameter("CondSqlCnt").toString());  //sql cnt String
	 	modelMap.put("tablename",request.getParameter("tablename").toString());
		
		return new ModelAndView("/hrm/informationRetrieval/viewEmpRetrieveShowExecl",modelMap);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewAddEmpRetrieve")
	public ModelAndView viewAddEmpRetrieve(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) throws Exception{
	 	modelMap.put("CondSql",request.getParameter("CondSql").toString());  // sql String
	 	modelMap.put("SqlKey",request.getParameter("SqlKey").toString());   //key String
	 	modelMap.put("ColName",request.getParameter("ColName").toString());  //title String
	 	modelMap.put("CondSqlCnt",request.getParameter("CondSqlCnt").toString());  //CNT
		
		return new ModelAndView("/hrm/informationRetrieval/viewAddEmpRetrieve",modelMap);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deleteCustTable")
	@ResponseBody
	public Map deleteCustTable(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		int result = informationRetrievalSer.deleteCustTable(request);
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_success",request));//删除成功
			map.put("navTabId", "hr0404");
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.delete_fail",request));//删除失败
		}
		return map;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/SaveEmpRetrieveShow")
	@ResponseBody
	public Map SaveEmpRetrieveShow(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) throws Exception{
		modelMap.put("CondSql",request.getParameter("CondSql").toString());  // sql String
	 	modelMap.put("SqlKey",request.getParameter("SqlKey").toString());   //key String
	 	modelMap.put("ColName",request.getParameter("ColName").toString());  //title String
	 	modelMap.put("CondSqlCnt",request.getParameter("CondSqlCnt").toString());  // CNT
		
		Map<String, Object> map = new HashMap<String, Object>();
		int result = informationRetrievalSer.saveEmpRetrieveInfo(request);
		if(result == 1){
			map.put("statusCode", "200");
			map.put("message", TipMessage.getTipMessage("ar.alert.message.addempshift.success",request));//保存成功
			map.put("navTabId", "hr0404");
			map.put("callbackType", "closeCurrent");
		}else{
			map.put("statusCode", "300");
			map.put("message", TipMessage.getTipMessage("alert.message.add_fail",request));//保存失败！
		}
		return map;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/codeTypeChange")
	@ResponseBody
	public void  codeTypeChange(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) throws Exception{
//		System.out.println(request.getParameter("selectedValue"));
		List list= this.informationRetrievalSer.getInfoFieldByTableNameList(request,request.getParameter("selectedValue"));
		StringBuffer htmlStr=new StringBuffer();
		htmlStr.append("");
		htmlStr.append("<table border='0' cellspacing='0' cellpadding='0' style='display:block;' width='100%' class=\'hr04_table\'><tr>");
		for (int i = 0; i < list.size(); i++) {
			 LinkedHashMap map=(LinkedHashMap)list.get(i);
			 htmlStr.append("<td  valign='middle'>");
			 htmlStr.append("<input type='checkbox' name='checkbox' id='checkbox_"+map.get("FIELD_ID") +" }' title='"+map.get("FIELD_NAME") +"' value='"+map.get("FIELD_ID") +"' onClick='add(this)' alt='"+map.get("CODE_TYPE") +"' lang='"+map.get("SHOT_TABLE")+"'/>&nbsp;"+map.get("FIELD_NAME"));
			 htmlStr.append("</td>");
			 if((i+1)%3==0){
				htmlStr.append("</tr><tr valign='middle' >"); 
			}
		}		
		htmlStr.append(" </tr> </table>");
		response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
		String returnString =  htmlStr.toString();
		PrintWriter out = response.getWriter();
		out.println(JsonUtil.writeInternal(returnString));
		out.flush();
		out.close();
	 }
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getCodeParamList")
	@ResponseBody
	public List getCodeParamList(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) throws Exception{
		 List list= this.informationRetrievalSer.getCodeParamList(request);
		 return list;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getTableCodeParamList")
	@ResponseBody
	public List getTableCodeParamList(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) throws Exception{
		//职级——职位——职责——职务
		List list=null;
		String table_name=request.getParameter("TABLE_NAME"); 
		if(table_name.equals("HR_POST_GRADE")){
		     //	职级(GGS)
		     list = this.informationRetrievalSer.getPostGradeForCheckBoxList(request);
		}else if(table_name.equals("HR_POST")){
		    //	职级名称(职务)
		   list = this.informationRetrievalSer.getPostForCheckBoxList(request);
		}else if(table_name.equals("HR_DUTY")){
		    //	职责
		   list = this.informationRetrievalSer.getDutyForCheckBoxList(request);
		} else if(table_name.equals("HR_POSITION")){
		    //职(岗)位
		   list = this.informationRetrievalSer.getPositionForCheckBoxList(request);
		} 
		 
		 return  list;
	}

	
	/**
	 * 信息搜索
	 * @param parameterObject
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/viewZuzhijiegou")
	public ModelAndView viewZuzhijiegouList(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) throws Exception{
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		//List getCustomerTableList = this.informationRetrievalSer.getCustomerTableList(request);  //自定义表
		//显示列 
	
		Map paramMap = ObjectBindUtil.getRequestParamData(request);
		paramMap.put("specialParam", admin.getSpecialParam());
		paramMap.put("userNo", admin.getUserNo());
		paramMap.put("deptNo", admin.getDeptNo());
		paramMap.put("endEddate", request.getParameter("endEddate"));
		String cid="";
		String name=admin.getPersonId();
		if(name==null||name.equals("")){
			cid="0";
		}else{
			cid=admin.getDeptNo();
			paramMap.put("PARENT_DEPT_NO", cid);
		}
		
		List  infolist= this.orgManageSer.getDeptInfoTree(paramMap);
		
		
		//PARENT_DEPT_NO
	
		  String str = "[{name:\"ZT1\",id:1,pId:0,open:false,isParent:true},";
	        str += "{name:\"ZT2\",id:2,pId:1,open:false,isParent:false},";
	        str += "{name:\"ZT3\",id:3,pId:1,open:false,isParent:false},";
	        str += "{name:\"ZT4\",id:4,pId:0,open:true,isParent:true,checked:true},";
	        str += "{name:\"ZT5\",id:5,pId:4,open:true,isParent:false},";
	        str += "{name:\"ZT6\",id:6,pId:4,open:true,isParent:false},";
	        str += "{name:\"ZT7\",id:7,pId:5,open:true,isParent:false},";
	        str += "{name:\"ZT8\",id:8,pId:5,open:true,isParent:false},";
	        str += "{name:\"ZT9\",id:9,pId:5,open:true,isParent:false},";
	        str += "{name:\"ZT10\",id:10,pId:9,open:true,isParent:false}]";

	        
	        
	        String s="[";
	        String content="";
	        for(int i=0;i<infolist.size();i++){
	        	Map m=(Map)infolist.get(i);
	        	
	        	/*if(m.get("PARENT_DEPTID")==null||m.get("PARENT_DEPTID").equals("")||m.get("PARENT_DEPTID").equals("null")){
	        		content="0";
	        	}*/
	        	int num=0;
	        	String pid="";
	        	Map<String, Object> map =new LinkedHashMap();
	        	map.put("cpnyno", admin.getCpnyId());
	        	map.put("deptno", m.get("DEPTNO"));
        		List hrEmployeeList=orgManageSer.getHrEmployeeByCpnyAndDeptNo(map);
        		
        		String id="";
        		
        		
        		
	        	s+="{name:\""+m.get("DEPTNAME")+"\",id:'"+m.get("DEPTNO")+"',pid:'"+m.get("PARENT_DEPT_NO")+"',cid:'"+cid+"'},";
	        	if(hrEmployeeList.size()>0){
	        		for(int j=0;j<hrEmployeeList.size();j++){
	        			Map ma=(Map)hrEmployeeList.get(j);
	        			s+="{name:\""+ma.get("NAME")+"\",id:'"+ma.get("PID")+"',pid:'"+ma.get("DEPTNO")+"',cpnyid:'"+ma.get("CPNYID")+"',empid:'"+ma.get("EMPID")+"'},";
	        			//s+="{name:\""+ma.get("NAME")+"\",id:'"+ma.get("PID")+"',pid:'"+ma.get("DEPTNO")+"',cpnyid:'"+ma.get("CPNYID")+"',empid:'"+ma.get("EMPID")+"',url:'/hrm/informationRetrieval/orgViewPersonalInfo?PERSON_ID='"+ma.get("PID")+"',target:'jbsxBox'},";

	        		}
	        		
	        		
	        	}
	        }
	        
	        s+="]";
	        int num=s.lastIndexOf(",");
	        String s1=s.substring(0, num);
	        String s2=s1+="]";
	        request.setAttribute("trees", s2);												   
		return new ModelAndView("/hrm/informationRetrieval/viewInfoZuzhijiegouList",modelMap);
	}
	/**
	 * 查看员工基础信息(view Staff foundation information)
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return ModelAndView
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/orgViewPersonalInfo")
	public ModelAndView orgViewPersonalInfo(HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) throws Exception {
		logger.info("essViewPersonalInfo.start...");
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		LinkedHashMap linkMap = (LinkedHashMap) empInfoSer.getPersonalInfoByPid(request);
		String tabsSelected = request.getSession().getAttribute("TABS_SELECTED") ==null?"0": request.getSession().getAttribute("TABS_SELECTED").toString();
		
		modelMap.put("personInfo", linkMap);
		String PhotoPath = config.getString("hrm.photo.read.ftp");
		String CPNY_ID = linkMap != null && linkMap.get("CPNY_ID") != null ? linkMap.get("CPNY_ID").toString() : "";
		String EMPID = linkMap != null && linkMap.get("EMPID") != null ? linkMap.get("EMPID").toString() : "";
		modelMap.put("PhotoPath",PhotoPath + "/" + CPNY_ID + "/" + CPNY_ID + EMPID + ".jpg");
		
		request.setAttribute("PERSON_ID", request.getParameter("PERSON_ID"));
		
		modelMap.put("photoId", "essViewPersonalInfo");
		//毕业学校
		modelMap.put("educationList", this.empInfoSer.getEducationList(request));
		
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "2418")) ;
		
		//测试插入横向tab(弃用)
//		modelMap.put("menuThirdList",this.empInfoSer.getTabMenuListList("",request));
		
		//引用业务系统的tab菜单
		modelMap.put("menuThirdList",this.empInfoSer.getMenuThirdListList("",request));
		
		//工会信息
		modelMap.put("toolbartradeunion", toolMenuSer.getToolMenuForNo(request, "123194")) ;
		modelMap.put("tradeUnionList", empInfoSer.getTradeunionList(request));
		//评价信息
		modelMap.put("toolbarInfopingjia", toolMenuSer.getToolMenuForNo(request, "2420")) ;
		modelMap.put("EvsInfo", empInfoSer.getEvsInfoList(request));
		//外国语信息
		modelMap.put("toolbarInfozige", toolMenuSer.getToolMenuForNo(request, "123192")) ;
		modelMap.put("languageLevelList", empInfoSer.getLanguageLevelList(request));
		//资格信息
		modelMap.put("toolbarInfozige", toolMenuSer.getToolMenuForNo(request, "2430")) ;
		modelMap.put("qualificationList", empInfoSer.getQualificationList(request));
		//社会关系
		modelMap.put("toolbarInfoshehui", toolMenuSer.getToolMenuForNo(request, "123191")) ;
		modelMap.put("familyList", empInfoSer.getFamilyList(request));
		//家庭关系
		modelMap.put("toolbarInfoHome", toolMenuSer.getToolMenuForNo(request, "123190")) ;
		modelMap.put("homeRelationList", empInfoSer.getHomeRelationList(request));
		//残疾信息
		modelMap.put("toolbarInfodisability", toolMenuSer.getToolMenuForNo(request, "123193")) ;
		modelMap.put("disabilityinfoList", empInfoSer.getDisabilityinfoList(request));
		//工作经历
		modelMap.put("toolbarInfogongzuo",  toolMenuSer.getToolMenuForNo(request, "2427")) ;
		modelMap.put("workExperienceList", empInfoSer.getWorkExperienceList(request));
		//培训
		modelMap.put("toolbarInfopeixun", toolMenuSer.getToolMenuForNo(request, "2423")) ;
		modelMap.put("trainingInfoList", empInfoSer.getTrainingInfoList(request));
		/*//发令信息
		modelMap.put("expInsideList", empInfoSer.getExpInsideList(request));
		modelMap.put("resignationInfo", empInfoSer.getResignationInfo(request));
		//评价信息
		modelMap.put("toolbarInfopingjia", toolMenuSer.getToolMenuForNo(request, "2420")) ;
		modelMap.put("EvsInfo", empInfoSer.getEvsInfoList(request));
		//奖励惩戒
		modelMap.put("rewardList", empInfoSer.getReward(request));
		modelMap.put("punishmentList", empInfoSer.getPunishment(request));
		//兼职派遣
		modelMap.put("pluralityList", empInfoSer.getPluralityList(request));
		//培训信息
		modelMap.put("toolbarInfopeixun", toolMenuSer.getToolMenuForNo(request, "2423")) ;
		modelMap.put("trainingInfoList", empInfoSer.getTrainingInfoList(request));
		//社会关系
		modelMap.put("toolbarInfoshehui", toolMenuSer.getToolMenuForNo(request, "2425")) ;
		modelMap.put("familyList", empInfoSer.getFamilyList(request));
		//家庭关系
		modelMap.put("toolbarInfoHome", toolMenuSer.getToolMenuForNo(request, "2549")) ;
		modelMap.put("homeRelationList", empInfoSer.getHomeRelationList(request));
		//健康信息
		modelMap.put("toolbarInfojiankang", toolMenuSer.getToolMenuForNo(request, "2426")) ;
		modelMap.put("healthList", empInfoSer.getHealthList(request));
		//工作经历
		modelMap.put("toolbarInfogongzuo",  toolMenuSer.getToolMenuForNo(request, "2427")) ;
		modelMap.put("workExperienceList", empInfoSer.getWorkExperienceList(request));
		//特殊事项
		modelMap.put("toolbarInfoteshu",  toolMenuSer.getToolMenuForNo(request, "2552")) ;
		modelMap.put("additionalList", empInfoSer.getAdditionalList(request));
		//账户
		modelMap.put("accountList", empInfoSer.getAccountList(request));
		//资格信息
		modelMap.put("toolbarInfozige", toolMenuSer.getToolMenuForNo(request, "2430")) ;
		modelMap.put("qualificationList", empInfoSer.getQualificationList(request));
		modelMap.put("languageLevelList", empInfoSer.getLanguageLevelList(request));
		//合同档案
		modelMap.put("toolbarInfohetong", toolMenuSer.getToolMenuForNo(request, "2491")) ;
		modelMap.put("contracList", empInfoSer.getContractList(request));
		modelMap.put("fileList", empInfoSer.getFileList(request));
		//出国信息
		modelMap.put("toolbarInfochuguo", toolMenuSer.getToolMenuForNo(request, "2524")) ;
		modelMap.put("goAbroadList", empInfoSer.getGoAbroadList(request));
		//证照信息
		modelMap.put("toolbarInfozhengzhao",  toolMenuSer.getToolMenuForNo(request, "3690")) ;
		modelMap.put("credentialList", empInfoSer.getCredentialList(request));
		//工资信息
		modelMap.put("accountList", empInfoSer.getAccountList(request));*/

		//modelMap.put("tabsSelected",tabsSelected);
		request.getSession().removeAttribute("TABS_SELECTED") ;
		//添加ess标识(0：表示业务系统，1：表示ESS系统)
		modelMap.put("isEssSystem", "1");
		
		//return new ModelAndView("/org/empinfo/orgViewPersonalInfo", modelMap);
		return new ModelAndView(modelMap);
	}

	
	/**
	 * 员工信息查看
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author heran@ait.net.cn 
	* @date 2013-8-05 下午5:58:17 
	* @version V1.0
	 */
	@RequestMapping(value = "/viewEmployeeList")
	public ModelAndView viewEmployeeList(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)throws Exception{
	
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String sta="";
		String CpnyStatus="";
		if(request.getParameter("STATUS_1")==null){
			sta="0";
		}else{
			sta=request.getParameter("STATUS_1");
		}
		//是否查询子部门
		if(request.getParameter("CPNYSTATUS")==null){
			CpnyStatus="1";
		}else{
			CpnyStatus=request.getParameter("CPNYSTATUS");
		}
		
		
		List linkMap = (List)empInfoSer.getPersonalInfoForInformation(request);
		int countList=(Integer)empInfoSer.getPersonalInfoForInformationCount(request);
		
			
	
		modelMap.put("STATUS", sta) ;
		modelMap.put("CPNYSTATUS", CpnyStatus) ;
		
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, countList) ;
		modelMap.put("perinfo", linkMap);
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "123339")) ;
		return new ModelAndView("/hrm/informationRetrieval/viewEmployeeList",modelMap);		
	}
	/**
	 * 员工信息查看EXCEL
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author heran@ait.net.cn 
	* @date 2013-8-05 下午5:58:17 
	* @version V1.0
	 */
	@RequestMapping(value = "/viewEmployeeListExcel")
	public ModelAndView viewEmployeeListExcel(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)throws Exception{
	
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		String sta="";
		String CpnyStatus="";
		if(request.getParameter("STATUS_1")==null){
			sta="0";
		}else{
			sta=request.getParameter("STATUS_1");
		}
		//是否查询子部门
		if(request.getParameter("CPNYSTATUS")==null){
			CpnyStatus="1";
		}else{
			CpnyStatus=request.getParameter("CPNYSTATUS");
		}
		
		
		List linkMap = (List)empInfoSer.getPersonalInfoForInformation(request);
		int countList=(Integer)empInfoSer.getPersonalInfoForInformationCount(request);
		
			
	
		modelMap.put("STATUS", sta) ;
		modelMap.put("CPNYSTATUS", CpnyStatus) ;
		
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, countList) ;
		modelMap.put("perinfo", linkMap);
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "123339")) ;
		return new ModelAndView("/hrm/informationRetrieval/viewEmployeeListExcel",modelMap);		
	}
	/**
	 * 当前法人的人员信息
	* @Copyright:   AIT (c)
	* @Company:     AIT
	* @Description: TODO
	* @author liangwei@ait.net.cn 
	* @date 2013-7-26 下午4:28:17 
	* @version V1.0
	 */
	@RequestMapping(value = "/viewEmpIdRetrieveList")
	public ModelAndView viewEmpIdRetrieveList(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap)throws Exception{
		
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		
		
		modelMap.put("EmpIdRetrieveList",this.informationRetrievalSer.getEmpIdRetrieveList(request));
		modelMap.put(UiUtil.TOTAL_COUNT_NAME, this.informationRetrievalSer.getEmpIdRetrieveListtCnt(request)) ;
		modelMap.put("reqest_navTabId", StringUtil.checkNull(request.getParameter("navTabId")));
		
		return new ModelAndView("/hrm/informationRetrieval/viewEmpIdRetrieveList",modelMap);		
	} 
	
	@RequestMapping(value = "/viewPersonalInfoExcel")
	public ModelAndView viewPersonalInfoExcel(HttpServletRequest request,
				HttpServletResponse response,ModelMap modelMap) throws Exception{
		Map paramMap = ObjectBindUtil.getRequestParamData(request);

		//默认
		String menuNo = "2540";
		AdminBean admin = SessionUtil.getLoginUserFromSession(request);
		logger.info("viewPersonalInfo.start...");
		LinkedHashMap linkMap = (LinkedHashMap)empInfoSer.getPersonalInfoByPid(request);
		String tabsSelected = request.getSession().getAttribute("TABS_SELECTED") ==null?"0": request.getSession().getAttribute("TABS_SELECTED").toString();

		modelMap.put("personInfo", linkMap);
		//毕业学校
		modelMap.put("educationList",this.empInfoSer.getEducationList(request));
		
		modelMap.put("toolbarInfo", request.getParameter("menuNo") != null ? 
				toolMenuSer.getToolMenu(request) : toolMenuSer.getToolMenuForNo(request, "2540")) ;
		
		modelMap.put("menuThirdList",this.empInfoSer.getMenuThirdListList("",request));
		//工会信息
		modelMap.put("toolbartradeunion", toolMenuSer.getToolMenuForNo(request, "123194")) ;
		modelMap.put("tradeUnionList", empInfoSer.getTradeunionList(request));
		//黑色档案信息
		modelMap.put("toolBadArchives", toolMenuSer.getToolMenuForNo(request, "2550")) ;
		modelMap.put("badArchives", empInfoSer.getBadArchivesList(request));
		//辅助信息
		modelMap.put("toolAssist", toolMenuSer.getToolMenuForNo(request, "216001")) ;
		modelMap.put("assistList", empInfoSer.getAssistList(request));
		//评价信息
		modelMap.put("toolbarInfopingjia", toolMenuSer.getToolMenuForNo(request, "2542")) ;
		modelMap.put("EvsInfo", empInfoSer.getEvsInfoList(request));
		//外国语信息
		modelMap.put("toolbarInfozige", toolMenuSer.getToolMenuForNo(request, "123192")) ;
		modelMap.put("languageLevelList", empInfoSer.getLanguageLevelList(request));
		//社会关系
		modelMap.put("toolbarInfoshehui", toolMenuSer.getToolMenuForNo(request, "123191")) ;
		modelMap.put("familyList", empInfoSer.getFamilyList(request));
		//家庭关系
		modelMap.put("toolbarInfoHome", toolMenuSer.getToolMenuForNo(request, "123190")) ;
		modelMap.put("homeRelationList", empInfoSer.getHomeRelationList(request));
		
		//获取兼卖产品类型
		List productList = empInfoSer.getEmpProductList(request);
		modelMap.put("productList", productList);
		//残疾信息
		modelMap.put("toolbarInfodisability", toolMenuSer.getToolMenuForNo(request, "123193")) ;
		modelMap.put("disabilityinfoList", empInfoSer.getDisabilityinfoList(request));
		
		//工作经历
		modelMap.put("toolbarInfogongzuo",  toolMenuSer.getToolMenuForNo(request, "2551")) ;
		modelMap.put("workExperienceList", empInfoSer.getWorkExperienceList(request));
		//培训
		modelMap.put("toolbarInfopeixun", toolMenuSer.getToolMenuForNo(request, "2545")) ;
		modelMap.put("trainingInfoList", empInfoSer.getTrainingInfoList(request));
		//合同
		modelMap.put("toolbarInfohetong", toolMenuSer.getToolMenuForNo(request, "2491")) ;
		modelMap.put("contracList", empInfoSer.getContractList(request));
		
		//发令信息
		modelMap.put("toolbarInfofl", toolMenuSer.getToolMenuForNo(request, "2541")) ;
		modelMap.put("assignmentList", empInfoSer.getAssignmentList(request));
		
		//派遣地
		modelMap.put("toolbarInfofl", toolMenuSer.getToolMenuForNo(request, "2491")) ;
		modelMap.put("expInsideList", empInfoSer.getExpInsideList(request));
		
		//资格信息
		modelMap.put("toolbarInfozige", toolMenuSer.getToolMenuForNo(request, "2554")) ;
		modelMap.put("qualificationList", empInfoSer.getQualificationList(request));
		
		modelMap.put("tabsSelected",tabsSelected);
		request.getSession().removeAttribute("TABS_SELECTED") ;
		modelMap.put("isEssSystem",request.getParameter("isEssSystem") != null ? (String)request.getParameter("isEssSystem") : "0");
		
		return new ModelAndView("/hrm/informationRetrieval/viewPersonnelInfoExcel",modelMap);
	}

}
