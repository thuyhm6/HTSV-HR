<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script type="text/javascript">  
 
	 
 
	function f_salary_transfer_confirm() {
		var flag = 0;
		
		var $form = $("#form_transfer_viewPaCalculate",navTab.getCurrentPanel());
	 
		var paMonth = $form.find("#paYear").val() + $form.find("#paMonth").val();
	 
		var statNo = $form.find(":input[name='STAT_NO']").val();
		 
		 
		var CPNY_ID = $form.find("#CPNY_ID").val();
		var duixiang = $form.find("#duixiang").val();
	 
		var str = "";
		if(statNo == ''){
			alertMsg.error("<spring:message code='pa.salary.title.salaryProvideStatnoIsMust'/>");
			return;
		} 
		var urlParam = "";
 		var deptStr = $form.find("#seach_EMP_TYPE").val();
 		var deptss = $("#form_transfer_viewPaCalculate  select[id='seach_EMP_TYPE']").val()
 	 
 	 
		urlParam = 'deptid=' + deptStr;

		 
		  		 $("#paCalculate").hide();
					 $.ajax( {
										type : 'post',
										cache : false,
										contentType : 'application/json',
										url : '/pa/salary/paSalaryTransfer?'+ urlParam + '&arMonth=' +paMonth+ '&paMonth=' +paMonth+'&duixiang='+duixiang+'&statNo='+statNo+'&flag='+flag,
										dataType : "json",
										
										success : function(responseStr) {
											$("#aCalculateResult2012").html(responseStr);
										 
											$("#Capalculate2012").show();
										}
							});			     

	}

 </script>
<form name="form_transfer_viewPaCalculate" method="post" action="" id="form_transfer_viewPaCalculate">
		<table width="100%" border="0" cellpadding="0" cellspacing="1" >
				<tr>
					<td align="left" style="font-family: 'Arial','simsun';
					font-size: 14px;color:#333333;font-weight: bold;
					background-image: url(/resources/images/title/top_1.gif);
					background-repeat: no-repeat;
					padding-right: 0px;
					padding-left: 18px;
					padding-top: 3px;
					padding-bottom: 2px;">
						财务传送<!-- 工资计算 --></td>
				</tr>
			</table> 
	<!-- 财务传送 -->
 
		<table width="100%"  border="0" cellspacing="0" cellpadding="0" class="lge_table">
			<tr>
				<td  class="td_title">
					<spring:message code="pa.insurance.title.salaryMonth"/><!--工资月-->
				</td>
						<td class="td_type">
							<ait:date yearName="paYear" monthName="paMonth"/>
						</td>
				<c:if test="${CPNY_ID eq 'TSTO' }"> 
			 	<td class="td_title" ><spring:message code="inct.salesman.daqu" /> <!--大区-->：</td>
				 <td>  
							<ait:deptTreeMulti id="STAT_NO" name="seach_DEPTNAME" limit="pa" level="2" ></ait:deptTreeMulti>
							
							 </td></c:if>	 
				<td class="td_title" >	传送对象：</td>
				 <td> 
		 				<select id="seach_EMP_TYPE" name="seach_EMP_TYPE">
									<option value=''>select</option>
									<c:forEach items="${statList}" var="stat">
										<option value="${stat.STAT_NO}">  ${stat.STAT_NAME}</option>
									</c:forEach>
								</select>
								<input id="CPNY_ID" name="CPNY_ID" type="hidden" value="${CPNY_ID}" /> 
				 </td> 
 
			</tr>
		
			 	<tr>
				<td width="15%" class="td_title">
					<!-- 计算结果 --><spring:message code="ar.viewararmonthcalculate.title.querenjieguo"/>:
				</td>
				<td class="td_type" colspan="5">
					<div id="aCalculateResult2012" style="color:#F00;"></div>
				</td>
			</tr>
		</table>
	 <div id="chuansongid" class="formBar">
			<ul class="toolBar">
				<li>
					<a onclick="f_salary_transfer_confirm()">
						<span>财务<spring:message code="pa.salary.title.salaryconfirm.chuansong"/><!-- 传送 --></span>
					</a>
				</li>
			</ul>
	 
			 
		</div>
</form>