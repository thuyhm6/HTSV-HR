<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageHeader">
	<form method="post" action="/report/pa/viewPaTranserList" onsubmit="return dwzSearch(this,'dialog')" rel="pagerForm">
	<input type="hidden" name="pageNum" value="${pageNum}" />
	<input type="hidden" name="numPerPage" value="${numPerPage}" />
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td width="5%" style="text-align:right"><%--部门名称--%>
					<spring:message code="org.orgManage.title.deptName"/>：
				</td>
				<td width="12%" style="text-align:center">
					<ait:deptTree name="seach_DEPTNO" limit="ar" selected="${DEPTNO}"/>
				</td>
				<td width="5%" style="text-align:right"><%--工号/姓名--%>
					<spring:message code="public.title.empIdAndName"/>：
				</td>
				<td width="8%" style="text-align:center">
				    <input type="text" id="seach_EMPID" name="seach_EMPID" maxlength="25" size="8" value="${EMPID }">
				</td>
				<td width="5" style="text-align:right"><%--工资月--%>
					<spring:message code="ar.viewarprogress.title.gongziyue"/>：
				</td>
				<td width="25" style="text-align:left">
					<select id="seach_YEAR" name="seach_YEAR" style="width:75px">
				    	<option value=""><%--请选择 --%>
				    		<spring:message code="hr.viewPersonalInfo.title.ADDED_BY_KELI"/>
				    	</option>
						<c:forEach var="i" begin="2000" end="2020" step="1"> 
					    	<option value="${i}" <c:if test="${YEAR eq i }">selected</c:if> >${i}</option>
					    </c:forEach> 
					 </select>
					<select id="seach_MONTH" name="seach_MONTH" >
						<option value=""><%--请选择--%>
							<spring:message code="hr.viewPersonalInfo.title.ADDED_BY_KELI"/>
						</option>
						<option value="01" <c:if test="${MONTH eq '01' }">selected</c:if>>01</option>
						<option value="02" <c:if test="${MONTH eq '02' }">selected</c:if>>02</option>
						<option value="03" <c:if test="${MONTH eq '03' }">selected</c:if>>03</option>
						<option value="04" <c:if test="${MONTH eq '04' }">selected</c:if>>04</option>
						<option value="05" <c:if test="${MONTH eq '05' }">selected</c:if>>05</option>
						<option value="06" <c:if test="${MONTH eq '06' }">selected</c:if>>06</option>
						<option value="07" <c:if test="${MONTH eq '07' }">selected</c:if>>07</option>
						<option value="08" <c:if test="${MONTH eq '08' }">selected</c:if>>08</option>
						<option value="09" <c:if test="${MONTH eq '09' }">selected</c:if>>09</option>
						<option value="10" <c:if test="${MONTH eq '10' }">selected</c:if>>10</option>
						<option value="11" <c:if test="${MONTH eq '11' }">selected</c:if>>11</option>
						<option value="12" <c:if test="${MONTH eq '12' }">selected</c:if>>12</option>
					</select>
				</td>
			</tr>
		</table>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent">
						<button type="submit"><spring:message code="public.title.search"/><!-- 检索 --></button>
					</div></div>
				</li>
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
				<th width="25%" align="center"><%--金额--%>
					<spring:message code="rp.report.title.amount"/>
				</th>
				<th width="10%" align="center"><%--银行--%>
					<spring:message code="rp.report.title.bankname"/>
				</th>
				<th width="35%" align="center"><%--银行账号--%>
					<spring:message code="rp.report.title.bankcardno"/>
				</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${paTranserList}" var="item" varStatus="i">
				<tr target="PERSON_ID" rel="${item.PERSON_ID}">
					<td width="10%" align="center">${i.index + 1}</td>
					<td width="10%" align="center">${item.EMPID }</td>
					<td width="10%" align="center">${item.LOCAL_NAME }</td>
					<td width="25%" align="center" style="vnd.ms-excel.numberformat:@">${item.ACTUAL_RELEASE_SALARY }</td>
					<td width="15%" align="center">${item.BANK }</td>
					<td width="35%" align="center" style="vnd.ms-excel.numberformat:@">${item.CARD_NO }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<form id="pagerForm" method="post" action="/report/pa/viewPaTranserList">
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