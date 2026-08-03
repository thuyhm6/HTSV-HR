<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
 function  openPaTranserwin(){
     var $form = $("#viewPaTranserInfo");
     var empid = $form.find("#seach_EMPID").val();
     var deptno = $form.find("#seach_DEPTNO").val();
     var year = $form.find("#seach_YEAR").val();
     var month = $form.find("#seach_MONTH").val();
     window.document.getElementById("onPaTranser").href="/report/pa/viewPaTranserList?pageNum=1&seach_EMPID="+empid+"&seach_DEPTNO="+deptno
     		+"&seach_YEAR="+year+"&seach_MONTH="+month;
         
 }
 function  openPaTranserExecl(a){
      var $this=$(a);
      var title = $this.attr("title"); 
      var $from = $("#viewPaPayOffTranserInfo");  
      
	  var url ="/report/pac04/viewPaLabourPayOffTranserExcel";
	   alertMsg.confirm(title, {
					okCall: function(){  
					   window.location =url+ (url.indexOf('?') == -1 ? "?" : "&") + $from.serialize();
					}});
    
 }
 function  openPaTranserPDF(a){
     var $this=$(a);
     var title = $this.attr("title"); 
     var $from = $("#viewPaPayOffTranserInfo");  
     
	  var url ="/report/pac04/viewPaLabourPayOffTranserPDF";
	   alertMsg.confirm(title, {
					okCall: function(){  
					   window.location =url+ (url.indexOf('?') == -1 ? "?" : "&") + $from.serialize();
					}});
   
}
</script>
<div class="pageHeader" style="border:1px #B8D0D6 solid">
	 <form id="viewPaPayOffTranserInfo" method="post" action="null">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td width="5%" style="text-align:center"><!--劳务所属-->
						<spring:message
							code="hr.viewPersonalInfo.title.SERVICES_BELONG_NAME" />：
					</td>
					<td width="12%" style="text-align:left">
						<select id="seach_service_belong" name="seach_service_belong"  style="width:75px">
							<option value="21928">山东劳务</option>
							<option value="21929">东北劳务</option>
						</select>
					</td>
					<td width="5" style="text-align:right"><%--工资月--%>
						<spring:message code="ar.viewarprogress.title.gongziyue"/>：
					</td>
					<td width="25" style="text-align:left">
						<select id="seach_YEAR" name="seach_YEAR" style="width:75px">
							<c:forEach var="i" begin="2000" end="2020" step="1"> 
						    	<option value="${i}" <c:if test="${YEAR eq i }">selected</c:if> >${i}</option>
						    </c:forEach> 
						 </select>
						<select id="seach_MONTH" name="seach_MONTH" >
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
		           <li>
					  <a class="button" onclick="openPaTranserExecl(this)"  <%--是否导出?--%> title="<spring:message code='rp.report.title.exportYN'/>">
					  <span><%--Excel导出--%><spring:message code="ar.addempshift.title.excelexport"/></span></a>
					  <a class="button" onclick="openPaTranserPDF(this)"  <%--是否导出?--%> title="<spring:message code='rp.report.title.exportYN'/>">
					  <span><%--Excel导出--%><spring:message code="ar.addempshift.title.pdfdaochu"/></span></a>
		           </li>
				</ul>
			</div>
		</div>
	 </form>
</div>