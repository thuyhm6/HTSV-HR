<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<div class="pageHeader">
	<form method="post" action="/sys/affirm/viewAffirmorsList?navTabId=${request_navTabId}" onsubmit="return dwzSearch(this,'dialog')" rel="pagerForm">
	<input type="hidden" name="pageNum" value="${pageNum}" />
	<input type="hidden" name="numPerPage" value="${numPerPage}" />
	<div class="searchBar" >
		<table class="searchContent">
			<tr>
				<td>
					<spring:message code="public.title.empId"/><!--工号-->/<spring:message code="public.title.name"/><!--姓名-->
				</td>
				<td>
					<input type="text" name="seach_EMPID" value="${EMPID}"/>
				</td>
				<td>
					部门
				</td>
				<td>
					<ait:deptList name="seach_DEPTNO" limit="hr" id="deptName_sy0130_add_0_viewAffirmors"/>
					<ait:deptTreeIcon name="seach_DEPTNO" limit="hr" id="deptName_sy0130_add_0_viewAffirmors" selected="${DEPTNO}"/>
				</td>
			</tr>
		</table>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent">
				<button type="submit"><spring:message code="public.title.search"/><!--检索--></button></div></div></li>
			</ul>
		</div>
	</div>
	</form>
</div>
<div class="pageContent">
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="80"><spring:message code="public.title.empId"/><!--工号--></th>
				<th width="80"><spring:message code="public.title.name"/><!--姓名--></th>
				<th width="80"><spring:message code="sys.affirm.title.idNumber"/><!--身份证号--></th>
				<th width="80"><spring:message code="public.title.deptName"/><!--部门--></th>
				<th width="80"><spring:message code="sys.affirm.title.confirm"/><!--确认--></th>
			</tr>
		</thead>
		<tbody>
		<script type="text/javascript">
		var initVar=1;
		function checkTrAffirmors(personId,empId,localName,deptName,letter){
			var personIdStr="btnSelect1"+personId;
			if(initVar==1){
				checkAffirmor(personId,empId,localName,deptName,letter);
				initVar=initVar+1;
				document.getElementById(personIdStr).click();
			}
		}
		</script>
			<c:forEach items="${empList}" var="item">
				<tr target="EMPID" rel="${item.EMPID}" onclick="checkTrAffirmors('${item.PERSON_ID}','${item.EMPID}','${item.LOCAL_NAME}','${item.DEPTNAME}','');">
					<td >${item.EMPID}</td>
					<td>${item.LOCAL_NAME}</td>
					<td>${item.IDCARD_NO}</td>
					<td>${item.DEPTNAME}</td>
					<td>
						<a class="btnSelect" id="btnSelect1${item.PERSON_ID}" 
						href="javascript:
							$.bringBack({'${personId}':'${item.PERSON_ID}',	'${empId}':'${item.EMPID}','${empName}':'${item.LOCAL_NAME}'
							})" title="<spring:message code='sys.affirm.title.findBackTo'/>"><spring:message code="public.title.choose"/></a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<form id="pagerForm" method="post" action="/sys/affirm/viewAffirmorsList?navTabId=${request_navTabId}">
	<div class="panelBar">
		<div class="pages">
			<span><spring:message code="public.title.view"/><!-- 显示 --></span>
				<select class="combox" name="numPerPage" onchange="dialogPageBreak({targetType:'dialog', numPerPage:this.value})">
					<option value="10"  <c:if test="${numPerPage == 10 }" >selected</c:if> >10</option>
					<option value="20"  <c:if test="${numPerPage == 20 }" >selected</c:if> >20</option>
					<option value="30"  <c:if test="${numPerPage == 30 }" >selected</c:if> >30</option>
				</select>
			<span><spring:message code="public.title.tiao"/><!--条-->,<spring:message code="public.title.gong"/><!--共-->${totalCount}<spring:message code="public.title.tiao"/><!--条--></span>
		</div>
		<div class="pagination" targetType="dialog" totalCount="${totalCount}" numPerPage="${numPerPage}" currentPage="${pageNum}"></div>
	</div>
	</form>
</div>