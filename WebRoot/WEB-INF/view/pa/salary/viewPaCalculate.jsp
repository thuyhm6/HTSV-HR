<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script type="text/javascript">
	$(function() {
		$("#loading_viewPaCalculate").hide();
	});
	function f_apply_confirm(){
		
		 
		var $form = $("#form_viewPaCalculate",navTab.getCurrentPanel());
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
	        $("input[name='pa_isChecked']:checkbox").each(function(){ 
	            if($(this).attr("checked")){
	                deptStr +=  +$(this).val()+"!";
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
		urlParam = 'AR_DEPT_NO=' + deptStr;
	 
		 $("#paCalculate").hide();
		 $.ajax( {
							type : 'post',
							cache : false,
							contentType : 'application/json',
							url : '/pa/salary/paSalaryConfirmApply?'+ urlParam + '&paMonth=' +paMonth+'&statNo='+statNo,
							dataType : "json",
							
							success : function(responseStr) {
								$("#paCalculateResult").html(responseStr);
							 
								$("#paCalculate").show();
							}
				});
					 
						     
	}
	

	
	
	
	function f_Calculate_viewPacalculate() {
		var $form = $("#form_viewPaCalculate",navTab.getCurrentPanel());
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
	        $("input[name='pa_isChecked']:checkbox").each(function(){ 
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
		urlParam = 'deptid=' + deptStr;
		
		alertMsg.confirm(
				"<spring:message code='pa.viewiscalculate.title.iscal'/>"+"[" + paMonth + "]"+"<spring:message code='pa.viewpacalculate.title.salary'/>"+"?",
						{
							okCall : function() {
								$("#loading_viewPaCalculate").show();
								$("#paCalculate").hide();
									$.ajax( {
											type : 'post',
											cache : false,
											contentType : 'application/json',
											url : '/pa/salary/paCalculate?'+ urlParam + '&paMonth=' +paMonth+'&statNo='+statNo,
											dataType : "json",
											
											success : function(responseStr) {
												$("#paCalculateResult").html(responseStr);
												$("#loading_viewPaCalculate").hide();
												$("#paCalculate").show();
											}
									});
							}
						});

	}
	
	
	function f_apply_closed() {
		var $form = $("#form_viewPaCalculate",navTab.getCurrentPanel());
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
	        $("input[name='pa_isChecked']:checkbox").each(function(){ 
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
		urlParam = 'deptid=' + deptStr;
		
		alertMsg.confirm(
				"确认申请"+"[" + paMonth + "]"+"工资关闭"+"?",
						{
							okCall : function() {
								$("#loading_viewPaCalculate").show();
								$("#paCalculate").hide();
									$.ajax( {
											type : 'post',
											cache : false,
											contentType : 'application/json',
											url : '/pa/salary/paApplyclosed?FLAG=1&'+ urlParam + '&paMonth=' +paMonth+'&statNo='+statNo,
											dataType : "json",
											
											success : function(responseStr) {
												$("#paCalculateResult").html(responseStr);
												$("#loading_viewPaCalculate").hide();
												$("#paCalculate").show();
											}
									});
							}
						});

	}
	

function f_apply_closedss() {
		var $form = $("#form_viewPaCalculate",navTab.getCurrentPanel());
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
	        $("input[name='pa_isChecked']:checkbox").each(function(){ 
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
		urlParam = 'deptid=' + deptStr;
		
		alertMsg.confirm(
				"确认申请"+"[" + paMonth + "]"+"工资关闭解除"+"?",
						{
							okCall : function() {
								$("#loading_viewPaCalculate").show();
								$("#paCalculate").hide();
									$.ajax( {
											type : 'post',
											cache : false,
											contentType : 'application/json',
											url : '/pa/salary/paApplyclosed?FLAG=0&'+ urlParam + '&paMonth=' +paMonth+'&statNo='+statNo,
											dataType : "json",
											
											success : function(responseStr) {
												$("#paCalculateResult").html(responseStr);
												$("#loading_viewPaCalculate").hide();
												$("#paCalculate").show();
											}
									});
							}
						});

	}
	
	//工资开放
	function Salary_open() {
		var $form = $("#form_viewPaCalculate",navTab.getCurrentPanel());
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
	        $("input[name='pa_isChecked']:checkbox").each(function(){ 
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
		urlParam = 'deptid=' + deptStr;
		
		alertMsg.confirm(
				"<spring:message code='pa.viewiscalculate.title.iscal'/>"+"[" + paMonth + "]"+"<spring:message code='pa.viewpacalculate.title.salary'/>"+"?",
						{
							okCall : function() {
								$("#loading_viewPaCalculate").show();
								$("#paCalculate").hide();
									$.ajax( {
											type : 'post',
											cache : false,
											contentType : 'application/json',
											url : '/pa/salary/paSalaryopen?FLAG=1&'+ urlParam + '&paMonth=' +paMonth+'&statNo='+statNo,
											dataType : "json",
											
											success : function(responseStr) {
												$("#paCalculateResult").html(responseStr);
												$("#loading_viewPaCalculate").hide();
												$("#paCalculate").show();
											}
									});
							}
						});

	}
	
		function  f_applyClose_Guan(){
		var $form = $("#form_viewPaCalculate",navTab.getCurrentPanel());
		var arMonth = $form.find("#paYear").val() + $form.find("#paMonth").val();
		var statNo = $form.find("#STAT_NO").val();
		var CPNY_ID = $form.find("#CPNY_ID").val();
		var deptStr="";
		if(CPNY_ID == 'TSTO'){
	       $("input[name='pa_isChecked']:checkbox").each(function(){ 
                if($(this).attr("checked")){
                    deptStr += $(this).val()+"!";
                }
            })

        if(deptStr.length == 0){  
           //alert("请选择大区");
		    alertMsg.error("请选择大区");
		    //bool = false;
		    return false;
        }
         deptStr=deptStr.substring(0, deptStr.lastIndexOf('!'));
        }
		$.pdialog.open("/pa/salary/viewpaApplyCloseGuan?FLAG=1&arMonth="+arMonth+"&STAT_NO="+statNo+"&AR_DEPT_NO="+deptStr, "f_applyClose_Guan", "工资申请关闭", {width:800,height:350,mask:true});
 	}
 	
	
</script>
<form name="form_viewPaCalculate" method="post" action="" id="form_viewPaCalculate">
		<div id="loading_viewPaCalculate"
			style="width: 100%; text-align: center; padding-top: 200px; position: absolute;">
			<img src="/resources/images/loading.gif">
		</div>
	<!-- 工资计算 -->
	<div id="paCalculate" title='<spring:message code="pa.salary.title.salarycalculation"/>' style="padding-top: 10px;">
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
						<spring:message code="pa.salary.title.salarycalculation"/><!-- 工资计算 --></td>
				</tr>
			</table>
		 
		<div class="formBar">
		<ul class="toolBar">
		        <li>
					<a onclick="f_applyClose_Guan()"> <!-- f_apply_closed() -->
						<span>  工资申请关闭</span>
					</a>
				</li>
				<li>
					<a onclick="f_apply_closedss()">
						<span>工资申请关闭解除</span>
					</a>
				</li>
				<li>
					<a onclick="f_apply_confirm()">
						<span><spring:message code="pa.salary.title.salaryconfirmapply"/><!-- 工资申请确认 --></span>
					</a>
				</li>
			</ul>
			<ul class="toolBar">
				<li>
					<a onclick="f_Calculate_viewPacalculate()">
						<span><spring:message code="pa.salary.title.salarycalculation"/><!-- 工资计算 --></span>
					</a>
				</li>
			</ul>
			
		</div>
		<table width="100%"  border="0" cellspacing="0" cellpadding="0" class="lge_table">
			<tr>
				<td width="15%" class="td_title">
					<spring:message code="pa.insurance.title.salaryMonth"/><!--工资月-->
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
						     <input name="pa_isChecked" id="pa_isChecked_${vlist.DEPTNO}" value="${vlist.DEPTNO}"  type="checkbox"  />
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
					<div id="paCalculateResult" style="color:#F00;"></div>
				</td>
			</tr>
		</table>
	</div>
</form>