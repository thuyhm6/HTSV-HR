<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script type="text/javascript">  
	$(function() {
		$("#loading_viewPaCalculate").hide();
	});
 
	 
	function f_salary_confirm() {
		
		var $form = $("#form_viewPaCalculate",navTab.getCurrentPanel());
		var year = $form.find("#paYear").val();
		var ANNUDATE = $form.find("#ANNUDATE").val();
		 $("#paCalculate").hide();
					 $.ajax( {
										type : 'post',
										cache : false,
										contentType : 'application/json',
										url : '/ar/attendanceVacations/getTAWelfare?YEAR='+year+'&ANNUDATE='+ANNUDATE,
										dataType : "json",
										
										success : function(responseStr) {
											$("#paCalculateResult").html(responseStr);
										 
											$("#paCalculate").show();
										}
							});
					 
						     

	}
	
 </script>
<form name="form_viewPaCalculate" method="post" action="" id="form_viewPaCalculate">
	<div id="paCalculate" style="padding-top: 10px;">
		 
		 
	 
		<div class="formBar">
		<ul class="toolBar">
			<c:if test="${CPNY_ID eq 'SST' }"> 	
				<li>
					<a onclick="f_salary_confirm()">
						<span>福利年假生成</span>
					</a>
				</li>
				</c:if>
			</ul>
		</div>
	 
		<table width="100%"  border="0" cellspacing="0" cellpadding="0" class="lge_table">
			<tr>
				<td width="15%" class="td_title">
					年份
				</td>
				<td class="td_type">
					<ait:date yearName="paYear" />
				</td>
			</tr>
			
				<td width="15%" class="td_title">
					生成日期
				</td>
				<td class="td_type">
					 <input type="text" id="ANNUDATE" name="ANNUDATE" value="" class="date required"
										yearstart="-20" yearend="20" readonly="true" />
										<a class="inputDateButton"><spring:message code="public.title.choose"/><!-- 选择 --></a>
				</td>
			</tr>
			
			<input id="CPNY_ID" name="CPNY_ID" type="hidden" value="SST"/>
		
			 	<tr>
				<td width="15%" class="td_title">
					<!-- 计算结果 --><spring:message code="ar.viewararmonthcalculate.title.querenjieguo"/>:
				</td>
				<td class="td_type">
					<div id="paCalculateResult" style="color:#F00;"></div>
				</td>
			</tr>
		</table>
	</div>
</form>