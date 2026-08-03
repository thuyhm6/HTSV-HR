<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.easydrag.js"></script>
<script>
  

function downloadExl(url) {
	$('#viewDeptPersonalInfo2List').attr("action", url);
	$('#viewDeptPersonalInfo2List').attr("onsubmit", '');
	$('#viewDeptPersonalInfo2List').submit();
	$('#viewDeptPersonalInfo2List').attr("action",
			'/ess/viewDept/viewDeptPersonalInfo2List');
	$('#viewDeptPersonalInfo2List')
			.attr("onsubmit", 'return navTabSearch(this)');
}
</script>

 <div class="panel">
	<h1>
		部门员工人事信息查询
	</h1>
</div>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/ess/viewDept/viewDeptPersonalInfo2List" rel="pagerForm" method="post"
		id="viewDeptPersonalInfo2List" name="viewDeptPersonalInfo2List">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					
				
			
				
					<td><!-- 部门： --> <spring:message
						code="hr.viewPersonalInfo.title.DEPTNAME" /> 
					</td>
					<td>
						<ait:deptList name="seach_DEPT_NO" cpnyId="${defaultCpny}" limit="ar" id="viewDeptPersonalInfo2List_seachDept"/>
						<ait:deptTreeIcon name="seach_DEPT_NO" cpnyId="${defaultCpny}" limit="ar" id="viewDeptPersonalInfo2List_seachDept" selected="${DEPTNO}"/></td>
					<td><!-- 社号/姓名： --> <spring:message
						code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME" />
					</td>
					<td> 
						
							<input type="text" name="seach_KEY" value="${KEY}" />
					
					
							
						
					</td>
				
			 
				</tr>
				 </table>
			<div class="subBar">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent">
					    <button type="submit">
					       <spring:message code="public.title.search"/> 
					    </button>
				        </div>
				        </div>
				    </li>
				    
				    <li>
						<a class="buttonActive"
							onclick="downloadExl('/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=74')"
							href="#"> <span>导出到Excel</span> </a>
					</li>
				</ul>
			</div>
		</div>
	</form>
</div>

<div class="pageContent" >
 
	<form name="delLeaveApplyAffirmForm" id="delLeaveApplyAffirmForm" method="post" action="/ess/infoApplyLeave/delLeaveApplyInBatch" 
	  onsubmit="return delLeaveApplyCallback(this, navTabAjaxDone);"> 
		<table width="100%">
			<thead> </thead>
			<tbody>
				<c:forEach items="${personList}" var="personList" varStatus="i">			
					<tr target="sid" rel="${personList.PERSON_ID_ID}">
	<td width="100%" >
					    <table width="100%" border="0" cellpadding="0" cellspacing="0"
				class="user_table margin_b">
					    <tr>
					    
					    <td rowspan="6" width="10%" class="td_type" > 
					    <img src="${personList.PHOTO_PATH}" width="116px"/>
					   
					    </td>
					   
						<td class="td_title" width='15%'>
					姓名
						<!--性别-->
					    </td>
					    <td class="td_type" width='30%'>
					    <a href="/ess/viewDept/viewDeptPersonalInfo?EMPID=${personList.EMPID_ID}&LOCAL_NAME=${personList.LOCAL_NAME}" target="dialog" style="color: blue;" mask="true"> ${personList.LOCAL_NAME}  </a>
					    </td>
					    <td class="td_title" width='15%'>
					职责
						<!--性别-->
					    </td>
					    <td class="td_type" width='30%'>
					     ${personList.POSITION_NO}
					    </td>
					  </tr>
					   <tr>
					   
					   <td class="td_title" width='15%'>
					主要业务
						<!--性别-->
					    </td>
					    <td class="td_type" width='30%'>
					    ${personList.MAIN_BUSINESS}
					    </td>
					    <td class="td_title" width='15%'>
					员工类型
						<!--性别-->
					    </td>
					    <td class="td_type" width='30%'>
					    ${personList.EMP_TYPE_CODE}
					    </td>
					  					   </tr>
					  					    <tr>
					   
					   <td class="td_title" width='15%'>
					成本中心
						<!--性别-->
					    </td>
					    <td class="td_type" width='30%'>
					    ${personList.COST_CENTER}
					    </td>
					    <td class="td_title" width='15%'>
					部门长
						<!--性别-->
					    </td>
					    <td class="td_type" width='30%'>
					    ${personList.MANAGER_NAME}
					    </td>
					  					   </tr>
					  					    <tr>
					   
					   <td class="td_title" width='15%'>
					出勤时间
						<!--性别-->
					    </td>
					    <td class="td_type" width='30%'>
					    ${personList.INCUMBENCY}
					    </td>
					    <td class="td_title" width='15%'>
				最初入社日
						<!--性别-->
					    </td>
					    <td class="td_type" width='30%'>
					    ${personList.TIME_STARTED}
					    </td>
					  					   </tr>
					  					    <tr>
					   
					   <td class="td_title" width='15%'>
					最终学校
						<!--性别-->
					    </td>
					    <td class="td_type" width='30%'>
					   ${personList.INSTITUTION_NAME}
					    </td>
					    <td class="td_title" width='15%'>
					
						<!--性别-->
					    </td>
					    <td class="td_type" width='30%'>
					    </td>
					  					   </tr>
					  					    <tr>
					   
					   <td class="td_title" width='15%'>
					工作地
						<!--性别-->
					    </td>
					    <td class="td_type" width='30%'>
					    ${personList.WORK_AREA}
					    </td>
					    <td class="td_title" width='15%'>
				
						<!--性别-->
					    </td>
					    <td class="td_type" width='30%'>
					    </td>
					  					   </tr>
					  					   
					  </table>
					</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</form>
    <c:set value="/ess/viewDept/viewDeptPersonalInfo2List" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>