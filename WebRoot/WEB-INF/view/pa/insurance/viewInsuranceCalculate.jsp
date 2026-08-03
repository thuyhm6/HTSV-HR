<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script type="text/javascript">
	$(function() {
		$("#loading_viewInsuranceCalculate").hide();
	});

	function f_Calculate_viewIscalculateNew() {
		var $form = $("#form_viewInsuranceCalculate",navTab.getCurrentPanel());
		var paMonth = $form.find("#paYear").val() + $form.find("#paMonth").val();
		var statNo = $form.find("#STAT_NO").val();
		var CPNY_ID = $form.find("#CPNY_ID").val();
		var str = "";
		if(statNo == ''){
			alertMsg.error("<spring:message code='pa.salary.title.salaryProvideStatnoIsMust'/>");
			return;
		}

		var deptStr = "";
		if(CPNY_ID == 'TSTO'){
	        $("input[name='is_isChecked']:checkbox").each(function(){ 
	            if($(this).attr("checked")){
	                deptStr += "'"+$(this).val()+"'"+"!";
	            }
	        })
		    if(deptStr.length == 0){    
		    	//请选择部门
				alertMsg.error("<spring:message code='ar.alert.message.addempshift.choosedept'/>");
			    return ;
		    }
	    	deptStr = deptStr.substring(0, deptStr.lastIndexOf('!'));
		}
    	
		var urlParam = "";
		urlParam = 'seach_deptid=' + deptStr;
		
		alertMsg.confirm(
				"<spring:message code='pa.viewiscalculate.title.isbasicmonth'/>"+"[" + paMonth
				+ "]"+"<spring:message code='pa.viewiscalculate.title.insurancemonth'/>"+"?",
						{
							okCall : function() {
								$("#loading_viewInsuranceCalculate").show();
								$("#InsuranceCalculate").hide();
									$.ajax( {
											type : 'post',
											cache : false,
											contentType : 'application/json',
											url : '/pa/insurance/insuranceCalculateNew?'+ urlParam + '&seach_paMonth=' +paMonth+'&seach_statNo='+statNo,
											dataType : "json",
											
											success : function(responseStr) {
												$("#InsuranceCalculateResult").html(responseStr);
												$("#loading_viewInsuranceCalculate").hide();
												$("#InsuranceCalculate").show();
											}
									});
							}
						});

	}

function f_IS_CLOSED(flags) {
		var $form = $("#form_viewInsuranceCalculate",navTab.getCurrentPanel());
		var paMonth = $form.find("#paYear").val() + $form.find("#paMonth").val();
		var statNo = $form.find("#STAT_NO").val();
		var CPNY_ID = $form.find("#CPNY_ID").val();
		var str = "";
		if(statNo == ''){
			alertMsg.error("<spring:message code='pa.salary.title.salaryProvideStatnoIsMust'/>");
			return;
		}

		var deptStr = "";
		if(CPNY_ID == 'TSTO'){
	        $("input[name='is_isChecked']:checkbox").each(function(){ 
	            if($(this).attr("checked")){
	                deptStr += $(this).val()+"!";
	            }
	        })
		    if(deptStr.length == 0){    
		    	//请选择部门
				alertMsg.error("<spring:message code='ar.alert.message.addempshift.choosedept'/>");
			    return ;
		    }
	    	deptStr = deptStr.substring(0, deptStr.lastIndexOf('!'));
		}
    	
		var urlParam = "";
		urlParam = 'seach_deptid=' + deptStr;
		var tishi = "";
		if(flags == 1){
		   tishi = "是否关闭["+paMonth+"]月的保险?";
		}else{
		   tishi = "是否解除关闭["+paMonth+"]月的保险?";
		}
		
		
		alertMsg.confirm(
				tishi,
						{
							okCall : function() {
								$("#loading_viewInsuranceCalculate").show();
								$("#InsuranceCalculate").hide();
									$.ajax( {
											type : 'post',
											cache : false,
											contentType : 'application/json',
											url : '/pa/insurance/isApplyClosed?'+ urlParam + '&seach_FLAG='+flags+'&seach_paMonth=' +paMonth+'&seach_statNo='+statNo,
											dataType : "json",
											
											success : function(responseStr) {
												$("#InsuranceCalculateResult").html(responseStr);
												$("#loading_viewInsuranceCalculate").hide();
												$("#InsuranceCalculate").show();
											}
									});
							}
						});

	}


</script>
<form name="form_viewInsuranceCalculate" method="post" action="" id="form_viewInsuranceCalculate">
	<div id="loading_viewInsuranceCalculate"
			style="width: 100%; text-align: center; padding-top: 200px; position: absolute;">
			<img src="/resources/images/loading.gif">
		</div>
		<div id="InsuranceCalculate" title='<spring:message code="pa.salary.title.salarycalculation"/>' style="padding-top: 10px;">
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
						<spring:message code="pa.insurance.title.insuranceCaculate"/><!-- 保险计算 --></td>
				</tr>
			</table>
			<div class="formBar">
				<ul class="toolBar">
					<li>
						<a onclick="f_Calculate_viewIscalculateNew()">
							<span><spring:message code="pa.insurance.title.insuranceCaculate"/><!--保险计算 --></span>
						</a>
					</li>
					<li>
						<a onclick="f_IS_CLOSED(1)">
							<span>保险关闭</span>
						</a>
					</li>
					<li>
						<a onclick="f_IS_CLOSED(0)">
							<span>保险解除关闭</span>
						</a>
					</li>
				</ul>
			</div>
			<table width="100%"  border="0" cellspacing="0" cellpadding="0" class="lge_table">
			<tr>
				<td width="15%" class="td_title">
					<spring:message code="pa.insurance.title.insuranceMonth"/><!--保险月-->
				</td>
				<td class="td_type">
					<ait:date yearName="paYear" monthName="paMonth"/>
				</td>
			</tr>
			<tr>
				<td width="15%" class="td_title">
					<!-- 区间 --><spring:message code="ar.viewcycleparameter.title.qujian"/>
				</td>
				<td class="td_type">
					<select id="STAT_NO" name="STAT_NO">
						<c:forEach items="${statList}" var="stat">
							<option value="${stat.STAT_NO}">${stat.STAT_NAME}</option>
						</c:forEach>
					</select>					
				</td>
			</tr>
			<input id="CPNY_ID" name="CPNY_ID" type="hidden" value="${CPNY_ID }"/>
			<c:if test="${CPNY_ID eq 'TSTO' }"> 
			<tr>
				<td width="15%" class="td_title">
					<!-- 大区 --><spring:message code="alert.hrm.dept.daqu"/>
				</td>
				<td class="td_type">
					    <c:forEach items="${deptList}" var="vlist" varStatus="i">
						     <input name="is_isChecked" id="is_isChecked_${vlist.DEPTNO}" value="${vlist.DEPTNO}"  type="checkbox"  />
							   ${vlist.DEPTNAME} 
							  <c:if test="${i.count % 5 == 0}">  
							    
							  </c:if>
					    </c:forEach>
				</td>
			</tr>
			</c:if>	
			<tr>
				<td width="15%" class="td_title">
					<!-- 计算结果 --><spring:message code="ar.viewararmonthcalculate.title.jisuanjieguo"/>:
				</td>
				<td class="td_type">
					<div id="InsuranceCalculateResult" style="color:#F00;"></div>
				</td>
			</tr>
		</table>
	</div>
</form>