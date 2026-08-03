<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
<%--打开查看页面用，未开发
 function  openPaPayrollCostswin(){
	 var $form = $("#viewPaPayrollCostsInfo");
     var empid = $form.find("#seach_EMPID").val();
     var deptno = $form.find("#seach_DEPTNO").val();
     var year = $form.find("#seach_YEAR").val();
     var month = $form.find("#seach_MONTH").val();
     
     if(checkPaMonth()==true){
	     window.document.getElementById("paPayrollCosts").href="/report/pac01/viewPaPayrollCostsList?pageNum=1&seach_EMPID="+empid+"&seach_DEPTNO="+deptno
	     		+"&seach_YEAR="+year+"&seach_MONTH="+month;
     }else{
    	 window.document.getElementById("paPayrollCosts").href="/report/pac01/viewSapPaError";
     }
 }
--%> 
 function checkPaMonth(){
	 var $form = $("#viewPaActualSalaryInfo");
     var deptno = $form.find("#seach_DEPTNO").val();
     var year = $form.find("#seach_YEAR").val();
     var month = $form.find("#seach_MONTH").val();
     if(deptno=='' || deptno==null){
    	//alert("部门为必选项，请选择部门！");
 		alertMsg.error("<spring:message code='pa.message.pa.check.deptnomustchoosed'/>");
 		$form.find("#seach_DEPTNO").focus();
 		return false;
     }
     if(year=='' || year==null){
    	//alert("工资年份为必选项，请选择年份！");
 		alertMsg.error("<spring:message code='pa.message.pa.check.payearmustchoosed'/>");
 		$form.find("#seach_YEAR").focus();
 		return false;
     }
     if(month=='' || month==null){
    	//alert("工资月份为必选项，请选择月份！");
 		alertMsg.error("<spring:message code='pa.message.pa.check.pamonthmustchoosed'/>");
 		$form.find("#seach_MONTH").focus();
 		return false;
     }
     return true;
 }
 function  openPaActualSalaryExecl(a){
      var $this=$(a);
      var title = $this.attr("title"); 
      var $from = $("#viewPaActualSalaryInfo");  
      
	  var url ="/report/pac01/viewPaActualSalaryExcel";
	  if(checkPaMonth()==true){
		   alertMsg.confirm(title, {
						okCall: function(){  
						   window.location =url+ (url.indexOf('?') == -1 ? "?" : "&") + $from.serialize();
						}});
	  }
    
 }
</script>
<div class="pageHeader" style="border:1px #B8D0D6 solid">
	 <form id="viewPaActualSalaryInfo" method="post" action="/report/pac01/viewPaActualSalaryInfo">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td width="5%" style="text-align:center"><%--工资月--%>
						<spring:message code="ar.viewarprogress.title.gongziyue"/>：
					</td>
					<td width="6%" style="text-align:left">
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
					<td width="5%" style="text-align:center"><%--部门名称--%>
						<spring:message code="org.orgManage.title.deptName"/>：
					</td>
					<td width="12%" style="text-align:left">
						<c:if test="${loginName ne 'IT'}">
							<ait:deptTree  selected="${DEPTNO}" name="seach_DEPTNO" limit="pa"/>
						</c:if>
						<c:if test="${loginName eq 'IT'}">
							<ait:deptTree  selected="${DEPTNO}" name="seach_DEPTNO" limit="hr"/>
						</c:if>
					</td>
					<!--<td width="12%" style="text-align:left">
						<c:if test="${loginName ne 'IT'}">
							<ait:deptTree  selected="${DEPTNO}" name="seach_DEPTNO" limit="pa"/>
						</c:if>
						<c:if test="${loginName eq 'IT'}">
							<ait:deptTree  selected="${DEPTNO}" name="seach_DEPTNO" limit="hr"/>
						</c:if>
					</td><%--
					<td width="5%" style="text-align:center">工号/姓名
						<spring:message code="public.title.empIdAndName"/>：
					</td>
					<td width="8%" style="text-align:left">
					    <input type="text" id="seach_EMPID" name="seach_EMPID" maxlength="25" size="8" value="${EMPID }">
					</td>--%>-->
					<td>&nbsp;</td>
				</tr>
			</table>
			<div class="subBar">
				<ul><%-- 
					<li>
					 <a class="button" id="paPayrollCosts" onclick="openPaPayrollCostswin()" target="dialog" width="1000" height="420" fresh="false">
					 	<span>检索<spring:message code="public.title.search"/></span></a>
					</li>--%>
		           <li>
					  <a class="button" onclick="openPaActualSalaryExecl(this)"<%--是否导出?--%>title="<spring:message code='rp.report.title.exportYN'/>">
					  <span><%--Excel导出--%><spring:message code="ar.addempshift.title.excelexport"/></span></a>
		           </li>
				</ul>
			</div>
		</div>
	 </form>
</div>