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
 function checkPaMont(){
	
 }
 function  openPaTranserExecl(a){
      var $this=$(a);
      var title = $this.attr("title"); 
      var $from = $("#viewPaTranserInfo");  
      
	  var url ="/report/pa/viewPaTranserExcel";
	   alertMsg.confirm(title, {
					okCall: function(){  
					   window.location =url+ (url.indexOf('?') == -1 ? "?" : "&") + $from.serialize();
					}});
    
 }
 
 function showSheet(){
	  var year = document.getElementById("startYear12").value;//alert(year);
	  var month = document.getElementById("startMonth12").value; //alert(month);
	  var deptid = document.getElementById("startDept12").value; //alert(deptid);
	  
	  var seach_EMPID = document.getElementById("seach_EMPID").value;
	//  alert(seach_EMPID);
	 document.getElementById("reportpdf").href='/report/pac04/jasperServlet?reportName=palist&pamonth='+year+month+'&deptid='+deptid+'&empid='+seach_EMPID ;
	 
   // window.open ('/report/pac04/jasperServlet?reportName=palist&pamonth='+year+month+'&deptid='+deptid+'&empid='+seach_EMPID);
	// document.getElementById("viewPaTranserInfo").action='/report/pac04/jasperServlet?reportName=palist&pamonth='+year+month+'&deptid='+deptid+'&empid='+seach_EMPID;
 //	document.getElementById("viewPaTranserInfo").submit();
}
</script>
<div class="pageHeader" style="border:1px #B8D0D6 solid">
	 <form id="viewPaTranserInfo" method="post" action="/report/pa/viewPaTranserList">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td width="5%" style="text-align:right"><%--部门名称--%>
						<spring:message code="org.orgManage.title.deptName"/>：
					</td>
					<td width="12%" style="text-align:center">
						<ait:deptTree name="startDept12"  limit="ar" selected="${DEPTNO}"/>
					</td>
					
					<td width="5%" style="text-align:right"><%--工号/姓名--%>
						<spring:message code="public.title.empId"/>：
					</td>
					<td width="8%" style="text-align:center">
					    <input type="text" id="seach_EMPID" name="seach_EMPID" maxlength="25" size="8" value="${EMPID }">
					</td>
					
					<td width="5" style="text-align:right"><%--工资月--%>
						<spring:message code="ar.viewarprogress.title.gongziyue"/>：
					</td>
					<td width="25" style="text-align:left">
						<select id="startYear12" name="startYear12" style="width:75px">
							<c:forEach var="i" begin="2000" end="2020" step="1"> 
						    	<option value="${i}" <c:if test="${YEAR eq i }">selected</c:if> >${i}</option>
						    </c:forEach> 
						 </select>
						<select id="startMonth12" name="startMonth12" >
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
				<ul><%--
					<li>
					 <a class="button" id="onPaTranser" onclick="openPaTranserwin()" target="dialog" width="1000" height="420" fresh="false">
					 	<span>检索<spring:message code="public.title.search"/></span></a>
					</li>--%>
		           <li>
					  <a id="reportpdf" class="button" onclick="showSheet();" <%--是否导出?--%> title="<spring:message code='rp.report.title.exportYN'/>">
					  <span><%--Excel导出--%><spring:message code="ar.addempshift.title.pdfdaochu"/></span></a>
		           </li>
				</ul>
			</div>
		</div>
	 </form>
</div>