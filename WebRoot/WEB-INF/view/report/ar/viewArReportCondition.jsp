<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script type="text/javascript">

function showSheet(){
    var seach_EMPID = "";
    var S_DATE = document.getElementById("S_DATE").value;
    var checkVal = document.getElementById("checkVal").value;
  //alert('aaa='+checkVal);
    if (checkVal=='report5'){
    	 seach_EMPID = document.getElementById("seach_EMPID").value;
    	document.getElementById("reportpdf").href='/ar/report/payReport04?reportName=hrCapabilityEvaluationTable&checkVal=report5'+'&empid='+seach_EMPID+'&searchDate='+S_DATE;
    }else if (checkVal=='report4'){
    	 var companyName = document.getElementById("company").value;
    	 seach_EMPID = document.getElementById("seach_EMPID").value;
    	document.getElementById("reportpdf").href='/ar/report/payReport04?reportName=hrDimissionCertification&checkVal=report4'+'&empid='+seach_EMPID+'&company='+companyName+'&searchDate='+S_DATE;
    }else if (checkVal=='report3'){
    	 seach_EMPID = document.getElementById("seach_EMPID").value;
    	document.getElementById("reportpdf").href='/ar/report/payReport04?reportName=hrRenewTheContract10&checkVal=report3'+'&empid='+seach_EMPID+'&searchDate='+S_DATE;
    }else if (checkVal=='report2'){
    	 seach_EMPID = document.getElementById("seach_EMPID").value;
    	document.getElementById("reportpdf").href='/ar/report/payReport04?reportName=hrRenewTheContract&checkVal=report2'+'&empid='+seach_EMPID+'&searchDate='+S_DATE;
    }else if (checkVal=='report1') {
       document.getElementById("reportpdf").href='/ar/report/payReport04?reportName=hrPersonRecordInfo&checkVal=report1'+'&searchDate='+S_DATE;
    }
}

</script>

<div class="pageHeader">
	
	<div class="searchBar">
		<table class="searchContent">
		   <input type="hidden" name="checkVal" id="checkVal" value="${checkVal}"/>
			<tr> 
			   <c:if test="${checkVal != 'report1'}">
			    <td><!-- 工号/姓名： --> <spring:message
					code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME" /> 
				</td> 
				<td><input type="text" id="seach_EMPID" name="seach_EMPID" maxlength="25" size="8" value="${EMPID }">
				</td>
				</c:if>
				<c:if test="${checkVal == 'report4'}">
				<td>公司名称
				</td>
				<td><input type="text" id="company" name="company">
				</td>
				</c:if>
				<td>
					搜索时间：
					 </td>
				<td ><input id="S_DATE" type="text" name="S_DATE" class="date required" readonly="true"/>
				</td>
				
			</tr>
			
		</table>
		<div class="subBar">
			<ul>
				<li>
					<a id="reportpdf" class="button" onclick="showSheet();" <%--是否导出?--%> title="<spring:message code='rp.report.title.exportYN'/>">
                      <span><%--pdf导出--%><spring:message code="ar.addempshift.title.pdfdaochu"/></span></a>
				</li>
			</ul>
		</div>
	</div>
</div>

