<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
/*     
    var month = document.getElementById("year").value+document.getElementById("month").value;
    var m=$(".add").attr("href")+"&PA_MONTH="+ month;
	$(".add").attr("href",m);
	
function passMonthValue()
{    
    var month = document.getElementById("year").value+document.getElementById("month").value;
    var p     = $(".add").attr("href").split("?");
    var m     = p[0]+"?&pageNum=1&PA_MONTH="+ month;
    $(".add").attr("href","");
	$(".add").attr("href",m);
	document.getElementById("seach_PA_MONTH").value = month;
}

*/
</script>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/pa/bonus/viewBonusPersonnel" method="post" rel="pagerForm">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<!--<td>
					公司：
					<select name="seach_CPNY_ID" id="CPNY_ID" style="width:180px; position: static; visibility: inherit;">
		           	     <option value="">请选择</option>
						 <c:forEach items="${compList}" var="item">
						 	<option value="${item.CPNY_ID}" <c:if test="${CPNY_ID eq item.CPNY_ID }">selected</c:if>>${item.CONTENT}</option>
						 </c:forEach>
					</select>
				</td>  -->
				<td>
				     <spring:message code="public.title.deptName"/><!--部门-->：
				     <ait:deptTree name="seach_DEPTNO" limit="ar" selected="${DEPTNO}"/>
				</td>
				<td>
					<spring:message code="public.title.empId"/><!--工号-->/<spring:message code="public.title.name"/><!--姓名-->：
					<input type="text" name="seach_KEY" value="${KEY }" />
				</td>
				<td>
					<spring:message code="pa.insurance.title.caculateFlag"/><!--计算标识-->：
					<input type="text" name="seach_CALC_FLAG" value="${CALC_FLAG}" />
					</td>
				<td>
					<spring:message code="pa.insurance.title.status"/><!--状态-->：
						<ait:SelectSyCodeByCpnyID name="seach_STATUS_CODE" parentNo="1372"
							cnpyID="${defaultCpny}" limit="all" selected="${STATUS_CODE}" />
				</td>				
			</tr>
		</table>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">
				<spring:message code="public.title.search"/><!--检索--></button></div></div></li>
			</ul>
		</div>
	</div>
	</form>
</div>

<div class="pageContent" >
	<!--<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="/pa/bonus/addBonusPersonnelView?pageNum=1" target="navTab" title="添加奖金计算人员"  width="800" height="400"><span>添加</span></a></li>
			<li><a class="delete" href="/pa/bonus/deleteBonusPersonnelInfo?PERSON_ID={sid}" target="ajaxTodo" title="确定要删除吗?"><span>删除</span></a></li>
			<li class="line">line</li>
		</ul>
	</div>-->
	<c:set value="/pa/bonus/updateBonusPersonnelView?seach_PERSON_ID={sid}" var="edit_Url"/>
	<c:set value="600" var="edit_width" />
	<c:set value="400" var="edit_height" />
	<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>
	
	<table class="table" width="100%" layoutH="145">
		<thead>
			<tr>
				
				<th width="11%"><spring:message code="public.title.empId"/><!--工号--></th>
				<th width="11%"><spring:message code="public.title.name"/><!--姓名--></th>
				<th width="11%"><spring:message code="public.title.deptName"/><!--部门--></th>
				<th width="11%"><spring:message code="pa.insurance.title.postGrade"/><!--职级--></th>
				<th width="11%"><spring:message code="pa.insurance.title.status"/><!--状态--></th>
				<th width="11%"><spring:message code="pa.insurance.title.entryCpmpanyDate"/><!--入司日期--></th>
				<th width="11%"><spring:message code="pa.insurance.title.resignDate"/><!--离职日期--></th>
				<th width="11%"><spring:message code="pa.insurance.title.salaryCaculateDate"/><!--工资结算日期--></th>
				<th width="11%"><spring:message code="pa.insurance.title.ifCaculateBonus"/><!--是否计算奖金--></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${bonusPersonnelList}" var="bonusPersonnel" varStatus="i">			
				<tr target="sid" rel="${bonusPersonnel.PERSON_ID}">
					
					<td>${bonusPersonnel.EMPID}</td>
					<td>${bonusPersonnel.LOCAL_NAME}</td>
					<td>${bonusPersonnel.DEPT_NAME}</td>
					<td>${bonusPersonnel.POST_GRADE_NAME}</td>
					<td>${bonusPersonnel.STATUS_NAME}</td>
					<td>${bonusPersonnel.JOIN_COMPANY_DATE}</td>
					<td>${bonusPersonnel.DATE_LEFT}</td>
					<td>${bonusPersonnel.SETTLEMENT_DATE}</td>
					<td>${bonusPersonnel.BN_CALC_FLAG}</td>					
				</tr>			
			</c:forEach>
		</tbody>
    
	</table>
	<c:set value="/pa/bonus/viewBonusPersonnel" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>