<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function f_IsCalcFlagUpdate(index,is_calc_flag,person_id){
	var params = [];
	params.push({
		name: 'seach_PERSON_ID',
		value: person_id
	});
	IS_CALC_FLAG = is_calc_flag == 'Y' ? 'N': 'Y';
	params.push({
		name: 'seach_IS_CALC_FLAG',
		value: IS_CALC_FLAG
	});
	$.ajax({
	  url: '/pa/insurance/updateIsCalcFlagByPersonId',
	  data: params,
	  cache: false,
	  success: function(responseText){
		if (responseText == "Y"){
			//成功之后不提示，否则太麻烦
			//alert('<spring:message code="alert.message.pa.salary.add_success"/>');
			//页面重载
			navTabSearch(document.viewInsurancePersonnelForm);
		}else{
			//错误之后提示
			alert('<spring:message code="alert.message.pa.salary.add_fail"/>');
		}
	  }
	});
} 

function reportExcel(a){
	var $this=$(a);
    var title = $this.attr("title"); 
    var $form = $("#viewInsurancePersonnelForm");  
    
	  var url ="/pa/insurance/viewInsurancePersonnelTranserExcel";
	   alertMsg.confirm(title, {
					okCall: function(){  
					   window.location =url+ (url.indexOf('?') == -1 ? "?" : "&") + $form.serialize();
					}});
}
</script>
<div class="pageHeader">
	<form id="viewInsurancePersonnelForm" name="viewInsurancePersonnelForm" onsubmit="return navTabSearch(this);" 
	      action="/pa/insurance/viewInsurancePersonnel" method="post" rel="pagerForm">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
				     <spring:message code="public.title.deptName"/><!--部门-->：
				     <ait:deptTree name="seach_DEPTNO" limit="hr" selected="${DEPTNO}"/>
				</td>
				<td>
					<spring:message code="public.title.empId"/><!--工号-->/
					<spring:message code="public.title.name"/><!--姓名-->：
					<input type="text" name="seach_KEY" value="${KEY }" />
				</td>
				<td>
					<spring:message code="pa.insurance.title.caculateFlag"/><!--计算标识-->：
					<!--<input type="text" name="seach_CALC_FLAG" value="${CALC_FLAG}" />-->
					<select id="seach_CALC_FLAG" name="seach_CALC_FLAG" >
						<option value=""><%-- 请选择 --%>
							<spring:message code="pa.insurance.title.pleaseChoose"/>
						</option>
						<option value="Y" <c:if test="${CALC_FLAG eq 'Y' }">selected</c:if>>&nbsp;&nbsp;Y</option>
						<option value="N" <c:if test="${CALC_FLAG eq 'N' }">selected</c:if>>&nbsp;&nbsp;N</option>
					</select>
				</td>
				<td>
					<spring:message code="liang.hr.viewPersonalInfo.title.WHETHER_OR_NOT_TO_TRY"/><!-- 试用与否 -->
					<select id="seach_IN_THE_DIFFERENCE" name="seach_IN_THE_DIFFERENCE" >
						<option value=""><%-- 请选择 --%>
							<spring:message code="pa.insurance.title.pleaseChoose"/>
						</option>
						<option value="Y" <c:if test="${IN_THE_DIFFERENCE eq 'Y' }">selected</c:if>>&nbsp;&nbsp;Y</option>
						<option value="N" <c:if test="${IN_THE_DIFFERENCE eq 'N' }">selected</c:if>>&nbsp;&nbsp;N</option>
					</select>
				</td>				
			</tr>
			<tr><td>
				<spring:message code="pa.insurance.title.salaryMonth"/><!--工资月-->
				<ait:date yearName="seach_paYear" monthName="seach_paMonth" yearSelected="${paYear}" monthSelected="${paMonth}"/>
			</td>
			<td>
				<ul>
					<li style="float:left;padding-top:5px;">
			    		<spring:message code="pa.salary.title.salaryProvideDate"/>：<!-- 工资发放日 -->
					</li>
					<li style="float:left;">
						<input id="GIVE_DATE" name="seach_GIVE_DATE" class="date" value="${GIVE_DATE}">
					</li>
				</ul>
			</td>
			</tr>
		</table>
		<div class="subBar">
			<ul>
				<li>
					<!-- <div class="buttonActive">
						<div class="buttonContent"><button type="submit">工资放对象</button></div>
					</div> -->
					<div class="buttonActive">
						<div class="buttonContent"><button type="submit"><spring:message code="public.title.search"/><!--检索--></button></div>
					</div>
					
					<div class="buttonActive">
						<a onclick="reportExcel(this)"  <%--是否导出?--%> title="<spring:message code='rp.report.title.exportYN'/>">
					  	<span><%--Excel导出--%><spring:message code="ar.addempshift.title.excelexport"/></span></a>
					</div>
				</li>
			</ul>
		</div>
	</div>
	</form>
</div>

<div class="pageContent" >
	<c:set value="/pa/insurance/updateInsurancePersonnelView?seach_PERSON_ID={sid}" var="edit_Url"/>
	<c:set value="600" var="edit_width"/>
	<c:set value="400" var="edit_height"/>
	<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>
	
	<table class="table" width="100%" layoutH="145">
		<thead>
			<tr>				
				<th width="11%" orderField="EMPID" class="${orderDirection}">
					<spring:message code="public.title.empId"/><!--工号--></th>
				<th width="11%" orderField="nlssort(LOCAL_NAME,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
					<spring:message code="public.title.name"/><!--姓名--></th>
				<th width="11%" orderField="nlssort(DEPT_NAME,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
					<spring:message code="public.title.deptName"/><!--部门--></th>
				<th width="11%" orderField="nlssort(POST_GRADE_NAME,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
					<spring:message code="pa.insurance.title.postGrade"/><!--职级--></th>
				<th width="11%" orderField="nlssort(IN_THE_DIFFERENCE,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
					<spring:message code="liang.hr.viewPersonalInfo.title.WHETHER_OR_NOT_TO_TRY"/><!-- 试用与否 --></th>
				<th width="11%" orderField="HR.JOIN_COMPANY_DATE" class="${orderDirection}">
					<spring:message code="pa.insurance.title.entryCpmpanyDate"/><!--入司日期--></th>
				<th width="11%" orderField="HR.DATE_LEFT" class="${orderDirection}">
					<spring:message code="pa.insurance.title.resignDate"/><!--离职日期--></th>
				<th width="11%" orderField="HRE.SETTLEMENT_DATE" class="${orderDirection}">
					<spring:message code="pa.insurance.title.salaryCaculateDate"/><!--工资结算日期--></th>
				<th width="11%" orderField="IS_CALC_FLAG" class="${orderDirection}">
					<spring:message code="pa.insurance.title.ifCaculateInsuranse"/><!--是否计算保险--></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${isPersonnelList}" var="isPersonnel" varStatus="i">			
				<tr target="sid" rel="${isPersonnel.PERSON_ID}">
					<td>${isPersonnel.EMPID}</td>
					<td>${isPersonnel.LOCAL_NAME}</td>
					<td>${isPersonnel.DEPT_NAME}</td>
					<td>${isPersonnel.POST_GRADE_NAME}</td>
					<td>${isPersonnel.IN_THE_DIFFERENCE}</td>
					<td>${isPersonnel.JOIN_COMPANY_DATE}</td>
					<td>${isPersonnel.DATE_LEFT}</td>
					<td>${isPersonnel.SETTLEMENT_DATE}</td>	
					<td>
						 <img src="/resources/images/${isPersonnel.IS_CALC_FLAG}.gif" onclick="f_IsCalcFlagUpdate(${i.index},'${isPersonnel.IS_CALC_FLAG}','${isPersonnel.PERSON_ID}')"
						 style="cursor: hand" /></a>
					</td>			
				</tr>			
			</c:forEach>
		</tbody>
	</table>
	<c:set value="/pa/insurance/viewInsurancePersonnel" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>     
</div>