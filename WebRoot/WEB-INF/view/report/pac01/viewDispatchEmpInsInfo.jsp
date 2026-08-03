<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
 function checkDispatchEmpInsPaMonth(){
	 var $form = $("#viewDispatchEmpInsInfo");
     var deptno = $form.find("#seach_DEPTNO_INS").val();
     var year = $form.find("#seach_YEAR_INS").val();
     var month = $form.find("#seach_MONTH_INS").val();
     if(deptno=='' || deptno==null){
    	//alert("部门为必选项，请选择部门！");
 		alertMsg.error("<spring:message code='pa.message.pa.check.deptnomustchoosed'/>");
 		$form.find("#seach_DEPTNO_INS").focus();
 		return false;
     }
     if(year=='' || year==null){
    	//alert("工资年份为必选项，请选择年份！");
 		alertMsg.error("<spring:message code='pa.message.pa.check.payearmustchoosed'/>");
 		$form.find("#seach_YEAR_INS").focus();
 		return false;
     }
     if(month=='' || month==null){
    	//alert("工资月份为必选项，请选择月份！");
 		alertMsg.error("<spring:message code='pa.message.pa.check.pamonthmustchoosed'/>");
 		$form.find("#seach_MONTH_INS").focus();
 		return false;
     }
     return true;
 }
 function  openDispatchEmpInsExecl(a){
      var $this=$(a);
      var title = $this.attr("title"); 
      var $from = $("#viewDispatchEmpInsInfo");  
      
	  var url ="/report/pac01/viewDispatchEmpInsExcel";
	  if(checkDispatchEmpInsPaMonth()==true){
		   alertMsg.confirm(title, {
						okCall: function(){  
						   window.location =url+ (url.indexOf('?') == -1 ? "?" : "&") + $from.serialize();
						}});
	  }
    
 }
</script>
<div class="pageHeader" style="border:1px #B8D0D6 solid">
	 <form id="viewDispatchEmpInsInfo" method="post" action="/report/pac01/viewDispatchEmpInsInfo">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td width="5%" style="text-align:center"><%--工资月--%>
						<spring:message code="ar.viewarprogress.title.gongziyue"/>：
					</td>
					<td width="6%" style="text-align:left">
						<select id="seach_YEAR_INS" name="seach_YEAR_INS" style="width:75px">
							<c:forEach var="i" begin="2000" end="2020" step="1"> 
						    	<option value="${i}" <c:if test="${YEAR_INS eq i }">selected</c:if> >${i}</option>
						    </c:forEach> 
						 </select>
						<select id="seach_MONTH_INS" name="seach_MONTH_INS" >
							<option value="01" <c:if test="${MONTH_INS eq '01' }">selected</c:if>>01</option>
							<option value="02" <c:if test="${MONTH_INS eq '02' }">selected</c:if>>02</option>
							<option value="03" <c:if test="${MONTH_INS eq '03' }">selected</c:if>>03</option>
							<option value="04" <c:if test="${MONTH_INS eq '04' }">selected</c:if>>04</option>
							<option value="05" <c:if test="${MONTH_INS eq '05' }">selected</c:if>>05</option>
							<option value="06" <c:if test="${MONTH_INS eq '06' }">selected</c:if>>06</option>
							<option value="07" <c:if test="${MONTH_INS eq '07' }">selected</c:if>>07</option>
							<option value="08" <c:if test="${MONTH_INS eq '08' }">selected</c:if>>08</option>
							<option value="09" <c:if test="${MONTH_INS eq '09' }">selected</c:if>>09</option>
							<option value="10" <c:if test="${MONTH_INS eq '10' }">selected</c:if>>10</option>
							<option value="11" <c:if test="${MONTH_INS eq '11' }">selected</c:if>>11</option>
							<option value="12" <c:if test="${MONTH_INS eq '12' }">selected</c:if>>12</option>
						</select>
					</td>
					<td width="5%" style="text-align:center"><%--部门名称--%>
						<spring:message code="org.orgManage.title.deptName"/>：
					</td>
					<td width="12%" style="text-align:left">
						<%--<c:if test="${loginName ne 'IT'}">
							<ait:deptTree  selected="${DEPTNO}" name="seach_DEPTNO" limit="pa"/>
						</c:if>
						<c:if test="${loginName eq 'IT'}">
							<ait:deptTree  selected="${DEPTNO}" name="seach_DEPTNO" limit="hr"/>
						</c:if>--%>
						<ait:deptTree  selected="${DEPTNO_INS}" name="seach_DEPTNO_INS" limit="hr"/>
					</td>
					<td width="5%" style="text-align:center">工号/姓名
						<spring:message code="public.title.empIdAndName"/>：
					</td>
					<td width="8%" style="text-align:left">
					    <input type="text" id="seach_EMPID_INS" name="seach_EMPID_INS" maxlength="25" size="8" value="${EMPID_INS }">
					</td>
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
					  <a class="button" onclick="openDispatchEmpInsExecl(this)"<%--是否导出?--%>title="<spring:message code='rp.report.title.exportYN'/>">
					  <span><%--Excel导出--%><spring:message code="ar.addempshift.title.excelexport"/></span></a>
		           </li>
				</ul>
			</div>
		</div>
	 </form>
</div>