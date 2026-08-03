<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
	function changeStatus(){
		var statu = document.getElementById("seach_LIZHI");
		if(statu.checked){
			statu.value = '1';
		}else{
			statu.value = '';
		}
	}
</script>
<div class="pageHeader">
	<form method="post" action="/ar/attendanceMintenance/viewShiftEmpList?supervisor=1" onsubmit="return dwzSearch(this,'dialog');changeStatus();" rel="pagerForm">
		<input type="hidden" name="pageNum" value="${pageNum}" />
		<input type="hidden" name="numPerPage" value="${numPerPage}" />
		
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td><!-- 关键字 --><spring:message code="ar.viewkeeperlist.title.keyword"/>:</td>
				<td><input type="text" name="seach_KEY" value="${KEY}"/></td>
				<td><!-- 部门 --><spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/>:</td>
				<td>
					<ait:deptTree name="seach_DEPTNO_TREE" limit="ar" selected="${DEPTNO_TREE}"/>
				</td>
			</tr>
			<tr>
				<td>
					<input id="seach_LIZHI"  name="seach_LIZHI" <c:if test="${LIZHI == '1'}">checked="checked" value="1"</c:if> <c:if test="${LIZHI != '1'}">value=""</c:if> type="checkbox"  onclick="changeStatus();"/> 
					<!-- 包含离职人员 -->
					<spring:message code="ar.viewkeeperlist.title.include_lizhi"/>
				</td>
				<td></td>
				<td>
					<!-- 离职起始日期 -->
					<spring:message code="ar.viewkeeperlist.title.start_date"/>
				</td>
				<td>
					<input id="seach_LEFT_FROM_DATE" type="text" name="seach_LEFT_FROM_DATE" value="${LEFT_FROM_DATE}" class="Wdate" onClick="WdatePicker({dateFmt:'dd-MM-yyyy',lang:'en'})" readonly="true"/>
					<!--<input id="seach_LEFT_FROM_DATE" type="text" name="seach_LEFT_FROM_DATE"  class="date" readonly="true"/>-->
				</td>
				<td>
					<!-- 离职截止日期 -->
					<spring:message code="ar.viewkeeperlist.title.end_date"/>
				</td>
				<td>
					<input id="seach_LEFT_END_DATE" type="text" name="seach_LEFT_END_DATE" value="${LEFT_END_DATE}" class="Wdate" onClick="WdatePicker({dateFmt:'dd-MM-yyyy',lang:'en'})" readonly="true"/>
					<!--<input id="seach_LEFT_END_DATE" type="text" name="seach_LEFT_END_DATE" value="${LEFT_END_DATE}" class="date" readonly="true"/>-->
				</td>
			</tr>
		</table>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit"><!-- 查询 --><spring:message code="button.search"/></button></div></div></li>
				<li><div class="button"><div class="buttonContent" onclick="change_name()"><button type="button" multLookup="orgId" ><!-- 选择带回 --><spring:message code="public.title.chooseback"/></button></div></div></li>
			</ul>
		</div>
	</div>
	</form>
</div>
<div class="pageContent">

	<table class="table" layoutH="135" targetType="dialog" width="100%">
		<thead>
			<tr>
				<th width="30"><input type="checkbox" class="checkboxCtrl" group="orgId" /></th>
				<th orderfield="empId"><!-- 员工号 --><spring:message code="hr.viewPersonalInfo.title.EMPID"/></th>
				<th orderfield="empName"><!-- 员工姓名 --><spring:message code="public.title.name"/></th>
				<th orderfield="empDept"><!-- 部门 --><spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/></th>
				<th orderfield="positionName"><!-- 职(岗)位 --><spring:message code="public.title.positionName"/></th>
				<th orderfield="positionName"><!-- 员工状态 --><spring:message code="hr.viewPersonalInfo.title.STATUS_NAME"/></th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${personList}" var="keeper">
			<tr>
				<td><input type="checkbox" name="orgId" 
						value="{personId:'${keeper.PERSON_ID}', 
								empName:'${keeper.LOCAL_NAME}', 
								empId:'${keeper.EMPID}',
								deptName:'${keeper.DEPT_NAME}'}"/></td>
				<td>${keeper.EMPID}</td>
				<td>${keeper.LOCAL_NAME}</td>
				<td>${keeper.DEPT_NAME}</td>
				<td>${keeper.POSITION_NAME}</td>
				<td>${keeper.STATUS_NAME}</td>
			</tr>
		</c:forEach>	
		</tbody>
	</table>
	<form id="pagerForm" method="post" action="/ar/attendanceMintenance/viewShiftEmpList?supervisor=1">
	<div class="panelBar">
		<div class="pages">
			<span><!-- 显示 --><spring:message code="public.title.view"/></span>
				<select class="combox" name="numPerPage" onchange="dialogPageBreak({targetType:'dialog', numPerPage:this.value})">
					<option value="10"  <c:if test="${numPerPage == 10 }" >selected</c:if> >10</option>
					<option value="20"  <c:if test="${numPerPage == 20 }" >selected</c:if> >20</option>
					<option value="30"  <c:if test="${numPerPage == 30 }" >selected</c:if> >30</option>
				</select>
			<span><!-- 条 --><spring:message code="public.title.tiao"/>，<!-- 共 --><spring:message code="public.title.gong"/>${totalCount}<!-- 条 --><spring:message code="public.title.tiao"/></span>
		</div>
		<div class="pagination" targetType="dialog" totalCount="${totalCount}" numPerPage="${numPerPage}" currentPage="${pageNum}"></div>
	</div>
	</form>
</div>