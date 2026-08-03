<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">

 function checkPaMonth(){
	 var $form = $("#viewPaJobOfferrInfo");
    // var deptno = $form.find("#seach_DEPTNO").val();
     var year = $form.find("#seach_YEAR").val();
     var month = $form.find("#seach_MONTH").val();
     var empType=$form.find("#PAY_STEP").val();
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
     if(empType=='' || empType==null){
    	alert("员工类型为必选项，请选择类型！");		
 		$form.find("#seach_empType").focus();
 		return false;
     }
     return true;
 }
 function  openPaJobOfferrExecl(a){
      var $this=$(a);
      var title = $this.attr("title"); 
      var $from = $("#viewPaJobOfferrInfo");  
      
	  var url ="/report/pac04/viewPaJobOfferExcel";
	  if(checkPaMonth()==true){
		   alertMsg.confirm(title, {
						okCall: function(){  
						   window.location =url+ (url.indexOf('?') == -1 ? "?" : "&") + $from.serialize();
						}});
	  }
    
 }
</script>
<div class="pageHeader" style="border:1px #B8D0D6 solid">
	 <form id="viewPaJobOfferrInfo" method="post" action="/report/pac04/viewPaJobOfferrInfo">
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
							<option value="01" <c:if test="${MONTH eq '1' }">selected</c:if>>01</option>
							<option value="02" <c:if test="${MONTH eq '2' }">selected</c:if>>02</option>
							<option value="03" <c:if test="${MONTH eq '3' }">selected</c:if>>03</option>
							<option value="04" <c:if test="${MONTH eq '4' }">selected</c:if>>04</option>
							<option value="05" <c:if test="${MONTH eq '5' }">selected</c:if>>05</option>
							<option value="06" <c:if test="${MONTH eq '6' }">selected</c:if>>06</option>
							<option value="07" <c:if test="${MONTH eq '7' }">selected</c:if>>07</option>
							<option value="08" <c:if test="${MONTH eq '8' }">selected</c:if>>08</option>
							<option value="09" <c:if test="${MONTH eq '9' }">selected</c:if>>09</option>
							<option value="10" <c:if test="${MONTH eq '10' }">selected</c:if>>10</option>
							<option value="11" <c:if test="${MONTH eq '11' }">selected</c:if>>11</option>
							<option value="12" <c:if test="${MONTH eq '12' }">selected</c:if>>12</option>
						</select>
					</td>
					<td width="5%" style="text-align:center">
					员工类型:
					</td>
					<td width="12%" style="text-align:left">
						<select name="PAY_STEP" id="PAY_STEP">
								<option value="">
									<spring:message code="hr.viewPersonalInfo.title.ADDED_BY_KELI" />
									<!-- 请选择 -->
								</option>
								<option value="1369"> 正式员工</option>
							</select>
					</td>
					<td>&nbsp;</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
		           <li>
					  <a class="button" onclick="openPaJobOfferrExecl(this)"<%--是否导出?--%>title="<spring:message code='rp.report.title.exportYN'/>">
					  <span><%--Excel导出--%><spring:message code="ar.addempshift.title.excelexport"/></span></a>
		           </li>
				</ul>
			</div>
		</div>
	 </form>
</div>