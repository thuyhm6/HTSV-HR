<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function navTabSearchLoginInfo(form, navTabId){
	var $form = $(form);

	if (form[DWZ.pageInfo.pageNum]){
		form[DWZ.pageInfo.pageNum].value = 1 ;
	}
	if(CheckFormLoginInfo(form,navTabId)){
		var params = $(form).serializeArray();
		if (!form[DWZ.pageInfo.pageNum]){
			params.push({name: DWZ.pageInfo.pageNum, value: 1}) ;
		}

		navTab.reload($form.attr('action'), {data: params, navTabId:navTabId});
	}
	return false;
}
function CheckFormLoginInfo(form,navTabId){
	var $form=$(form);
	var fromDate  = document.getElementById("seach_FROM_DATE").value;
    var toDate    = document.getElementById("seach_TO_DATE").value;
    if(fromDate!=null && fromDate!="" && toDate!=null && toDate!=""){
	    if(fromDate>toDate){
		   alertMsg.error('<spring:message code="alert.message.ess.infoApply.startTimeNotLaterThanEndTime"/>');
		   $form.find("#seach_FROM_DATE").focus();
		   return false;
		}   
	}
    return true;
}
</script>

<div class="pageHeader">
	<form id="viewLoginInfo" onsubmit="return navTabSearchLoginInfo(this,'${request_navTabId}');" action="/sys/rightsManagement/viewLoginInfo" method="post" rel="pagerForm">
	<div class="searchBar" >
		<table class="searchContent">
			<tr>
				<td width="5%"><!-- 工号/姓名 -->
					<spring:message code="public.title.empIdAndName"/>：
				</td>
				<td width="15%" class="td_type">
					<input type="text" name="seach_EMP_INFO" value="${EMP_INFO}"/>
				</td>
				<td width="5%"><!-- 开始日期 -->
					<spring:message code="public.title.startDate"/>：
				</td>
				<td width="15%" class="td_type" colspan="2">
				    <input type="text" id="seach_FROM_DATE" name="seach_FROM_DATE" class="date required" format="yyyy-MM-dd" readonly="true" value="${FROM_DATE}"/>
				    <a class="inputDateButton" href="javascript:;"><spring:message code="public.title.choose"/><!-- 选择 --></a>
				</td>
				<td width="5%"><!-- 结束日期 -->
					<spring:message code="public.title.endDate"/>：
				</td>
				<td width="15%" class="td_type">
				    <input type="text" id="seach_TO_DATE" name="seach_TO_DATE" class="date" format="yyyy-MM-dd" readonly="true" value="${TO_DATE}"/>
				    <a class="inputDateButton" href="javascript:;"><spring:message code="public.title.choose"/><!-- 选择 --></a>				    
				</td>    
			</tr>
		</table>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">
				<spring:message code="public.title.search"/><!-- 检索 --></button></div></div></li>
			</ul>
		</div>
	</div>
	</form>
</div>

<div class="pageContent">
	<table class="table" width="100%" layoutH="177">
		<thead>
			<tr>
				<th width="80"><spring:message code="public.title.empId"/><!--工号--></th>
				<th width="80"><spring:message code="public.title.name"/><!--姓名--></th>
				<th width="80"><spring:message code="public.title.deptName"/><!--部门--></th>
				<th width="80"><spring:message code="sys.essParam.title.legalPerson"/><!--法人--></th>
				<th width="80">IP</th>
				<th width="80"><spring:message code="sys.rights.title.loginTime"/><!--登陆时间--></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${loginInfoList}" var="loginInfo" varStatus="i">
				<tr>
					<td>${loginInfo.EMPID}</td>
					<td>${loginInfo.EMP_NAME}</td>
					<td>${loginInfo.DEPT_NAME}</td>
					<td>${loginInfo.CPNYNAME}</td>
					<td>${loginInfo.IP}</td>
					<td>${loginInfo.LOGIN_DATE}</td>
				</tr>			
			</c:forEach>			
		</tbody>
	</table>
		<c:set value="/sys/rightsManagement/viewLoginInfo" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>	
</div>