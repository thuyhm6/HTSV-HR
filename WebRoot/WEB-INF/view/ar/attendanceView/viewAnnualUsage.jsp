<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function openAnnualUsageExecl(a){
     var $this=$(a);
     var title = $this.attr("title"); 
     var $from = $("#viewAnnualUsage");  
     
	 var url ="/ar/attendanceView/viewAnnualUsageExcel";
	 alertMsg.confirm(title, {
					okCall: function(){  
					   window.location =url+ (url.indexOf('?') == -1 ? "?" : "&") + $from.serialize();
					}});   
}
</script>
<div class="pageHeader" >
	<form id="viewAnnualUsage" onsubmit="return navTabSearch(this);" action="/ar/attendanceView/viewAnnualUsage" method="post" rel="pagerForm">
		<input type="hidden" id="seach_DATA_FLAG" name="seach_DATA_FLAG" value="1"/>
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td><!-- 年份 -->
						<spring:message code="pa.payear.title.payear"/>
					</td>
					<td>
						<ait:date yearName="seach_AR_YEAR" yearSelected="${AR_YEAR}"/>
					</td>
					<td><%--部门名称--%>
						<spring:message code="org.orgManage.title.deptName"/>
					</td>
					<td>
						<ait:deptList name="seach_DEPT_NO" limit="ar" id="viewAnnualUsage_seachDept"/>
						<ait:deptTreeIcon name="seach_DEPT_NO" limit="ar" id="viewAnnualUsage_seachDept" selected="${DEPT_NO}"/>
					</td>
					<td><%--工号/姓名--%>
						<spring:message code="public.title.empIdAndName"/>
					</td>
					<td>
					    <input type="text" id="seach_KEY" name="seach_KEY" maxlength="25" value="${KEY }">
					</td>
				  <td>在职状态</td>
				  <td>
		 			  <ait:SelectSyCodeByCpnyID id="seach_EmpOffice" name="EmpOffice" parentNo="15118" selected="${EmpOffice}" cnpyID="${defaultCpny}" limit="all"/>
				  </td>
				</tr>
				<tr>
						<td>人员类型组 </td>
						<td>
							<input type="hidden" id="ar0108_limit" name="limit" value="ar">
							<input type="hidden" id="ar0108_seach_CPNY" name="seach_CPNY" value="${LoginUser.cpnyId}">
							<ait:SelectEmpTypeCode  id="ar0108_seach_JobTypeGroupNo"  name="seach_JobTypeGroupNo" selected="${JobTypeGroupNo}" limit="ar" type="group"
								onChangeName="ajaxEmpTypeForGroupToList(-1,ar0108_seach_JobTypeGroupNo,ar0108_seach_EmpTypeCodeNo,ar0108_seach_CPNY,ar0108_limit)"/>
						</td>
						<td>人员类型 </td>
						<td>
		 					<ait:SelectEmpTypeCode id="ar0108_seach_EmpTypeCodeNo" name="seach_EmpTypeCodeNo" selected="${EmpTypeCodeNo}" limit="ar"/>
						</td>	
				</tr>
			</table>
			
						<div class="subBar">
						 	<ul>
							 	<li>
								 	<div class="buttonActive">
									 	<div class="buttonContent">
										 	<button type="submit">
										 		&nbsp;<spring:message code="public.title.search"/><!--检索-->&nbsp;
										 	</button>
									 	</div>
								 	</div>
							 	</li>
							 	<li>
								  	<a class="buttonActive" onclick="openAnnualUsageExecl(this)" title="<spring:message code='rp.report.title.exportYN'/>">
									  	<span>
									  		<%--Excel导出--%><spring:message code="ar.addempshift.title.excelexport"/>
									  	</span>
								  	</a>
				           		</li>
						 	</ul>
						</div> 
		</div>
	</form>
</div>

<div class="pageContent">
	<table class="table" width="100%"  layoutH="180" >
		<thead>
			<tr>
				<th style="text-align: center">社号/姓名</th>				
				<th style="text-align: center">总年假</th>
				<th style="text-align: center">本年年假</th>				
				<th style="text-align: center">移年年假</th>				
				<th style="text-align: center">福利年假</th>				
				<th style="text-align: center">福利年假调整</th>
				<th style="text-align: center">已用总天数</th>
				<th style="text-align: center">年假使用明细</th>
				<th style="text-align: center">剩余总年假</th>				
				<th style="text-align: center">剩余法定年假天数</th>
				<th style="text-align: center">剩余福利年假天数</th>				
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${annualUsageList}" var="annual" varStatus="i">				
				<tr>
					<td style="text-align: center">${annual.EMPID }/${annual.LOCAL_NAME }</td>					
					<td style="text-align: center">${annual.TOTAL_VAC }</td>
					<td style="text-align: center">${annual.TOT_VAC_CNT1}</td>
					<td style="text-align: center">${annual.LAST_YEAR_VAC1 }</td>
					<td style="text-align: center">${annual.TOT_VAC_CNT2  }</td>
					<td style="text-align: center">${annual.ADD_VAC }</td>					
					<td style="text-align: center">${annual.USE_VAC  }</td>
					<td style="text-align: center"><a href="/ar/attendanceView/showAnnualUsageList?PERSON_ID=${annual.PERSON_ID}&AR_YEAR=${annual.VAC_ID}" target="dialog" mask="true" width="400" height="300"  >查看已休年假</a></td>
					<td style="text-align: center">${annual.TOTAL_VAC - annual.USE_VAC  }</td>
					<td style="text-align: center">${annual.TOT_VAC_CNT1 + annual.LAST_YEAR_VAC1 - annual.USE_FD_VAC} </td>					
					<td style="text-align: center">${annual.TOT_VAC_CNT2 + annual.ADD_VAC - annual.USE_FL_VAC}</td>					
				</tr>				
			</c:forEach>    
		</tbody>
	</table>
	<c:set value="/ar/attendanceView/viewAnnualUsage" var="pageUrl" />
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>
