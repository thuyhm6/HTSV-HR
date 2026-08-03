<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<SCRIPT type="text/javascript">
function showPersonRecord(person_id) {
     theUrl="/report/hr/viewPersonRecodeCard?seach_PERSON_ID="+person_id;
     var name="searchPersonRecord";
     var features = "toolbar=no,location=no,directory=no,status=no,menubar=no,scrollbars=no,resizable=no,copyhistory=no,left=540,top=0,resizable=yes,scrollbars=yes,width=600,height=500";
     window.open(theUrl,name,features);        
}
</SCRIPT>
<div class="pageHeader">
	<form method="post" action="/report/hr/viewPersonRecodeList" onsubmit="return dwzSearch(this,'dialog')" rel="pagerForm">
	<input type="hidden" name="pageNum" value="${pageNum}" />
	<input type="hidden" name="numPerPage" value="${numPerPage}" />
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td width="5%" align="right"><%--部门名称--%>
					<spring:message code="org.orgManage.title.deptName"/>：
				</td>
				<td width="12%" align="left">
					<ait:deptTree name="seach_DEPTNO" limit="ar" selected="${DEPTNO}"/>
				</td>
				<td width="5%" align="center"><%--工号/姓名--%>
					<spring:message code="public.title.empIdAndName"/>：
				</td>
				<td width="10%" align="center">
				    <input type="text" id="seach_EMPID" name="seach_EMPID" maxlength="25" size="8" value="${EMPID }">
				</td>
				<td width="5%" align="center"><%--身份证号/护照号--%>
					<spring:message code="rp.report.title.idcardorpassport"/>：
				</td>
				<td width="10%" align="center">
				    <input type="text" id="seach_IDCARD_NO" name="seach_IDCARD_NO" maxlength="25" size="8" value="${IDCARD_NO }">
				</td>
			</tr>
		</table>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit"><spring:message code="public.title.search"/><!-- 检索 --></button></div></div></li>
			</ul>
		</div>
	</div>
	</form>
</div>
<div class="pageContent">
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="10%" align="center"><%--区分--%>
					<spring:message code="hr.viewHealth.title.INDUSTRY_DISTINGUISH_NAME"/>
				</th>
				<th width="10%" align="center"><%--工号--%>
					<spring:message code="public.title.empId"/>
				</th>
				<th width="10%" align="center"><%--姓名--%>
					<spring:message code="public.title.name"/>
				</th>
				<th width="10%" align="center"><%--部门--%>
					<spring:message code="public.title.deptName"/>
				</th>
				<th width="35%" align="center"><%--护照号码 /身份证号码--%>
					<spring:message code="rp.report.title.idcardorpassport"/>
				</th>
				<th width="15%" align="center"><%--人事记录卡--%>
					<spring:message code="rp.report.title.personalrecord"/>
				</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${personEmpList}" var="item" varStatus="i">
				<tr>
					<td width="10%" align="center">${i.index + 1}</td>
					<td width="10%" align="center">${item.EMPID }</td>
					<td width="10%" align="center">${item.LOCAL_NAME }</td>
					<td width="10%" align="center">${item.DEPARTMENT }</td>
					<td width="10%" align="center">${item.IDCARD_NO }</td>
					<td width="10%" align="center"><%--查看员工人事记录卡--%>
						<a href="#" onclick="showPersonRecord('${item.PERSON_ID}');" title="<spring:message code='rp.report.title.personalrecordview'/>">
						<%--信息查看--%><spring:message code="main.home.message.viewinfo"/>
						</a>							
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<form id="pagerForm" method="post" action="/report/hr/viewPersonRecodeList">
	<div class="panelBar">
		<div class="pages">
			<span><spring:message code="public.title.view"/><!--显示--></span>
				<select class="combox" name="numPerPage" onchange="dialogPageBreak({targetType:'dialog', numPerPage:this.value})">
					<option value="10"  <c:if test="${numPerPage == 10 }" >selected</c:if> >10</option>
					<option value="20"  <c:if test="${numPerPage == 20 }" >selected</c:if> >20</option>
					<option value="30"  <c:if test="${numPerPage == 30 }" >selected</c:if> >30</option>
				</select>
			<span><spring:message code="public.title.tiao"/><!--条-->，<spring:message code="public.title.gong"/><!--共-->${totalCount}<spring:message code="public.title.tiao"/><!--条--></span>
		</div>
		<div class="pagination" targetType="dialog" totalCount="${totalCount}" numPerPage="${numPerPage}" currentPage="${pageNum}"></div>
	</div>
	</form>
</div>