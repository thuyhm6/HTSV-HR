<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<div class="pageHeader">
	<form id="viewarmonthpersoninfo" name="viewarmonthpersoninfo" onsubmit="return navTabSearch(this);" action="/ar/attendanceMintenance/viewArMonthPersonInfo?pageNum=1&numPerPage=0" method="post" >
	<div class="searchBar" >
		<table class="searchContent">
			<tr>
				<td><!-- 考勤月 --><spring:message code='ar.excelexport.title.armonth'/>:</td>
				 
				<td><ait:date yearName="seach_year_ar0106" yearSelected="${year_ar0106}"
						monthName="seach_month_ar0106" monthSelected="${month_ar0106}"/></td>
				
				<td><!-- 工号/姓名 --><spring:message code='public.title.empIdAndName'/>:</td>
				<td> 
					<input name="seach_condition" type="text" id="seach_condition" value="${condition}"/>
				 </td>
				 
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
 
	<table >
	  ${datetable}
	</table>
	
</div>
</br>

