<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@page import="com.ait.sys.service.ToolMenuSer"%>
<%@page import="com.ait.sys.service.impl.ToolMenuSerImpl"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageHeader">
<form id="viewEmpInfo" onsubmit="return navTabSearch(this);" action="/hrm/empinfo/viewPerConversionList" method="post" rel="pagerForm">
<div class="searchBar">
<table class="searchContent">
	<tr>
		<td><!-- 部门： --> <spring:message
			code="hr.viewPersonalInfo.title.DEPTNAME" /> 
		</td>
		<td>
			<ait:deptList name="seach_DEPTNO" cpnyId="${defaultCpny}" limit="hr" id="viewPerConversionList_seachDept"/>
			<ait:deptTreeIcon name="seach_DEPTNO" cpnyId="${defaultCpny}" limit="hr" id="viewPerConversionList_seachDept" selected="${DEPTNO}"/></td>
		<td><!-- 社号/姓名： --> <spring:message
			code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME" /> 
		</td>
		<td><input
			type="text" name="seach_KEY" value="${KEY}" /></td>
		<td>
		<!-- 职责： --> <spring:message
			code="ess.infoApply.title.dutyName" /> 
		</td>
		<td>
			<select name="seach_POSITION">
				<option value="">请选择</option>
				<c:forEach items="${positionList}" var="position">
					<option value="${position.POSITION}" <c:if test="${position.POSITION eq POSITION}">selected</c:if>>${position.POSITION}</option>
				</c:forEach>
			</select>
		</td>
		<td><!-- 人员类型： --> <spring:message
			code="hr.enpinfo.title.EMP.TYPE" /> 
		</td>
		<td>
		 	<ait:SelectEmpTypeCode id="seach_EMP_TYPE" name="seach_EMP_TYPE" selected="${EMP_TYPE}" cnpyID="${defaultCpny}" limit="hr"/>
		</td>
	</tr>
</table>
<div class="subBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">
								<spring:message code="public.title.search"/><!-- 检索 -->
							</button>
						</div>
					</div>
				</li>
			</ul>
</div>
</div>
</form>
</div>


<div class="pageContent">
	<div layoutH="10" style="float:left; display:block; overflow:auto; width:350PX;; border:solid 1px #CCC; line-height:21px; background:#fff">
		<table class="table" width="101.8%" layoutH="173">
			<thead>
				<tr>
					<th width="8%"><spring:message
						code="hr.enpinfo.title.EMP.EMPNUMBER" /> <!--社号--></th>
					<th width="8%"><spring:message
						code="public.title.name" /> <!--姓名--></th>
					<th width="8%"><spring:message
						code="public.title.deptName" /> <!--部门--></th>
					<th width="8%"><spring:message
						code="public.title.positionName" /> <!--职位--></th>
					<th width="8%"><spring:message
						code="hr.viewWorkInfo.title.DUTY" /> <!--职责--></th>
					<th width="8%"><spring:message
						code="hr.enpinfo.title.EMP.TYPE" /> <!--人员类型--></th>
					<th width="8%"><spring:message
						code="hr.enpinfo.title.EMP.PROBATION_START_DATE" /> <!--试用期开始日期--></th>
					<th width="8%"><spring:message
						code="hr.enpinfo.title.EMP.PROBATION_END_DATE" /> <!--试用期结束日期--></th>
					<th width="8%"><spring:message
						code="hr.enpinfo.title.EMP.PROBATION_PAY_RATE" /> <!--试用期比例--></th>
					<th width="8%"><spring:message
						code="hr.enpinfo.title.EMP.POSITIVED_ATE" /> <!--转正日期--></th>
					<th width="8%"><spring:message
						code="hr.enpinfo.title.EMP.RECIPROCAL.DAYS" /> <!--倒计天数--></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${perConversionList}" var="item" varStatus="i">
		
						<tr onclick="openOnRight('/hrm/empinfo/viewPersonalInfo?pageNum=1&menuNo=2540&navTabId=hr0101&PERSON_ID=${item.PERSON_ID}','viewEmpinfoEdit_hr0101');">
						<td style="text-align:left">${item.EMPID}</td>
						<td style="text-align:left">${item.LOCAL_NAME}</td>
						<td style="text-align:left">${item.DEPT_NAME}</td>
						<td style="text-align:left">${item.DUTY_NAME}</td>
						<td style="text-align:left">${item.POSITION_NAME}</td>
						<td style="text-align:left">${item.EMP_TYPE_NAME}</td>
						<td class='td_center' >${item.PROB_STRT_DATE}</td>
						<td class='td_center' >${item.END_PROBATION_DATE}</td>
						<td class='td_center' >${item.PROB_PAY_RAT}</td>
						<td class='td_center' >${item.BEFORE_END_PROBATION_DATE}</td>
						<td class='td_center' >${item.DAYS}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<c:set value="/hrm/empinfo/viewPerConversionList" var="pageUrl"/>
		<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
	</div>
	<div id="viewEmpinfoEdit_hr0101" class="unitBox" style="margin-left:370px;">
		<div class="pageContent" style="border-left:1px #B8D0D6 solid;border-right:1px #B8D0D6 solid">
		</div>
	</div>
</div>
