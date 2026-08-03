<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<div class="pageHeader">
	<form method="post" action="/ar/attendanceMintenance/viewKeeperList" onsubmit="return dwzSearch(this,'dialog')" rel="pagerForm">
		<input type="hidden" name="pageNum" value="${pageNum}" />
		<input type="hidden" name="numPerPage" value="${numPerPage}" />
		<input type="hidden" name="seach_LIZHI" value="${ALL}" />
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td><!-- 关键字 --><spring:message code="ar.viewkeeperlist.title.keyword"/>:</td>
				<td><input type="text" name="seach_KEY" value="${KEY}"/></td>
				<td><!-- 部门 --><spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/>:</td>
				<td>
					<ait:deptTree name="seach_DEPTNO_TREE" limit="hr" selected="${DEPTNO_TREE}"/>
				</td>
				<td>在职状态：</td>
				<td>
			 		<ait:SelectSyCodeByCpnyID id="seach_EmpOffice" name="seach_EmpOffice" parentNo="15118" selected="${EmpOffice}" cnpyID="${LoginUser.cpnyId}" limit="all"/>
				</td>
			</tr>
			<tr>
			   <td>人员类型组： </td>
						<td>
						<select name="seach_JobTypeGroupNo" class="select">
									<option value="">
										<!-- 全部 --><spring:message code="ar.viewarcardrecord.title.quanbu"/>
									</option>
									<c:forEach items="${jobTypeGroupList}" var="item">
										<option value="${item.JOBTYPE_GROUP_NO}" 
											<c:if test="${item.JOBTYPE_GROUP_NO eq JobTypeGroupNo}">selected</c:if>>
											${item.JOBTYPE_GROUP_NAME}
										</option>
									</c:forEach>
							</select>
						</td>
						<td>人员类型： </td>
						<td>
						<select name="seach_EmpTypeCodeNo" class="select">
									<option value="">
										<!-- 全部 --><spring:message code="ar.viewarcardrecord.title.quanbu"/>
									</option>
									<c:forEach items="${getEmpTypeCodeList}" var="item">
										<option value="${item.EMP_TYPE_CODE}" 
											<c:if test="${item.EMP_TYPE_CODE eq EmpTypeCodeNo}">selected</c:if>>
											${item.EMP_TYPE_NAME}
										</option>
									</c:forEach>
							</select>
						</td>
			</tr>
		</table>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit"><!-- 查询 --><spring:message code="button.search"/></button></div></div></li>
			</ul>
		</div>
	</div>
	</form>
</div>
<div class="pageContent">

	<table class="table" layoutH="130" targetType="dialog" width="100%">
		<thead>
			<tr>
				<th orderfield="empId"><!-- 工号 --><spring:message code="public.title.empId"/></th>
				<th orderfield="empName"><!-- 姓名 --><spring:message code="public.title.name"/></th>
				<th orderfield="empDept"><!-- 部门 --><spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/>
				<th width="80"><!-- 确认 --><spring:message code="hr.viewPersonalInfo.title.AFFIRM"/></th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${personList}" var="keeper">
			<tr>
				<td>${keeper.EMPID}</td>
				<td>${keeper.LOCAL_NAME}</td>
				<td>${keeper.DEPT_NAME}</td>
				<td>
					<a class="btnSelect" 
						href="javascript:
							$.bringBack({
								personId:'${keeper.PERSON_ID}',
								empDept:'${keeper.DEPT_NAME}',
								empName:'${keeper.LOCAL_NAME}',
								empId:'${keeper.EMPID}',
								empJobType:'${keeper.EMP_TYPE_NAME}',
								emppostGradeName:'${keeper.POST_GRADE_NO}'
							})" title="<spring:message code='ar.alert.message.viewattendencekeeper.chazhaodaihui'/>"><!-- 选择 --><spring:message code="public.title.choose"/></a>
				</td>
			</tr>
		</c:forEach>	
		</tbody>
	</table>
	<form id="pagerForm" method="post" action="/ar/attendanceMintenance/viewKeeperList">
	<div class="panelBar">
		<div class="pages">
			<span><!-- 显示 --><spring:message code="public.title.view"/></span>
				<select class="combox" name="numPerPage" onchange="dialogPageBreak({targetType:'dialog', numPerPage:this.value})">
					<option value="10"  <c:if test="${numPerPage == 10 }" >selected</c:if> >10</option>
					<option value="20"  <c:if test="${numPerPage == 20 }" >selected</c:if> >20</option>
					<option value="30"  <c:if test="${numPerPage == 30 }" >selected</c:if> >30</option>
				</select>
			<span><!-- 条 --><spring:message code="public.title.tiao"/>，<!-- 共 --><spring:message code="public.title.gong"/>
			${totalCount}<!-- 条 --><spring:message code="public.title.tiao"/></span>
		</div>
		<div class="pagination" targetType="dialog" totalCount="${totalCount}" numPerPage="${numPerPage}" currentPage="${pageNum}"></div>
	</div>
	</form>
</div>