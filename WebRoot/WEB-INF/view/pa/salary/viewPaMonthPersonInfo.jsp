<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<div class="pageHeader">
	<form id="viewarmonthpersoninfo" name="viewarmonthpersoninfo" onsubmit="return navTabSearch(this);" action="/pa/salary/viewPaMonthPersonInfo?pageNum=1&numPerPage=0" method="post" >
	<div class="searchBar" >
		<table class="searchContent">
			<tr>
				<td><!-- 考勤月 --><spring:message code='ar.excelexport.title.armonth'/>:</td>				 
				<td><ait:date yearName="seach_year_ar0106" yearSelected="${year_ar0106}"
						monthName="seach_month_ar0106" monthSelected="${month_ar0106}"/></td>
				
				<!--<td> 工号/姓名<spring:message code='public.title.empIdAndName'/>:</td>
					<td> 
					<input name="seach_condition" type="text" id="seach_condition" value="${condition}"/>
				 </td> -->
				 <td>社号/姓名： </td><td> 
					<input id="jsonData" name="jsonData" value="" type="hidden" /> 
					<input id="empId" name="dwz.person.empId" value="${empid}" type="hidden" lookupGroup="person" /> 
					<input id="type" name="dwz.person.type" value="" type="hidden" lookupGroup="person" /> 
					<input id="personId" name="dwz.person.personId" value="${personid}" type="hidden" lookupGroup="person" /> 
					<input name="dwz.person.empId" id="empId" type="text" class="required" readOnly lookupGroup="person"  value='${empid}'/> 
					<a class="btnLook"
						href="/ar/attendanceSettings/viewKeeperList?pageNum=1&LIZHI=1&EmpOffice=15119"
						lookupGroup="person">
							<!-- 查找带回 --> <spring:message
								code="ar.alert.message.viewattendencekeeper.chazhaodaihui" />
					</a></td>
				<td>
					<div class="subBar">
						<ul>
                        <li><div class="buttonActive"><div class="buttonContent"><button type="submit"><!-- 查询 --><spring:message code="button.search"/></button></div></div></li>
					
					</ul>
					</div>
				</td>
			</tr>
		</table>
	</div>
	</form>
</div>
<div class="pageContent">
<table width='100%'>${dateHrtable}
	</table>
	<table width='100%'>${dateArtable}
	</table>
	<table width='100%'>${datePatable}
	</table>
	<table width='100%'>${dateIstable}
	</table>
	<c:if test="${CPNY_ID ne '' and CPNY_ID ne null}">
	<table width='100%'>

		<tr>
			<td width='100%'><div class='panel'>
					<h1 style='text-align: center'>手工输入项目明细</h1>
					<div>
						<table width='100%' border='1' cellpadding='0' cellspacing='0'
							class='user_table'>
							<tr>
								<td class='td_title' style='text-align: center'>工资项目</td>
								<td class='td_title' style='text-align: center'>详细描述</td>
								<td class='td_title' style='text-align: center'>金额</td>
							</tr>

							<c:forEach items="${paParamDateList}" var="item" varStatus="i">

								<tr>
									<td class='td_type' style='text-align: center'>${item.ITEM_NAME}</td>
									<td class='td_type' style='text-align: center'>${item.REMARK}</td>
									<td class='td_type' style='text-align: center'>${item.RETURN_VALUE}</td>
								</tr>

							</c:forEach>
						</table>
					</div>
				</div></td>
		</tr>
	</table>
	</c:if>
</div>
</br>

