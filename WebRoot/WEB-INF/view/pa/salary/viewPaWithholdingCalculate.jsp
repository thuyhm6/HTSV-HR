<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script type="text/javascript">
	$(function() {
		$("#loading_viewPaCalculate").hide();
	});
	function f_apply_confirm(){
		
		 
		var $form = $("#form_viewPaCalculate_Yuti",navTab.getCurrentPanel());
		var paMonth = $form.find("#paYear").val() + $form.find("#paMonth").val();
		var statNo = $form.find("#STAT_NO_YU").val();
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
	 
		 $("#paCalculate").hide();
		 $.ajax( {
							type : 'post',
							cache : false,
							contentType : 'application/json',
							url : '/pa/salary/paSalaryConfirmApply?'+ urlParam + '&paMonth=' +paMonth+'&statNo='+statNo,
							dataType : "json",
							
							success : function(responseStr) {
								$("#paCalculateResult",navTab.getCurrentPanel()).html(responseStr);
							 
								$("#paCalculate",navTab.getCurrentPanel()).show();
							}
				});
					 
						     
	}
	

	
	
	
	function f_Calculate_viewPacalculate() {
		var $form = $("#form_viewPaCalculate_Yuti",navTab.getCurrentPanel());
		var paMonth = $form.find("#paYear").val() + $form.find("#paMonth").val();
		var statNo = $form.find("#STAT_NO_YU").val();
		var CPNY_ID = $form.find("#CPNY_ID").val();
		var str = "";
		if(statNo == '' ||statNo == null){
			alertMsg.error("<spring:message code='pa.salary.title.salaryProvideStatnoIsMust'/>");
			return;
		}
		
		var empType = "";
		  
	   empType  = "'"+$("#STAT_NO_YU").val()+"'"+"!";
	       
		    if(empType.length == 0){    
		    	//请选择部门
				alertMsg.error("请选择人员类型组");
			    return ;
		    }
	    	empType = empType.substring(0, empType.lastIndexOf('!'));
		
		

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
				"<spring:message code='pa.viewiscalculate.title.iscal'/>"+"[" + paMonth + "]"+"月的预提工资"+"?",
						{
							okCall : function() {
								$("#loading_viewPaCalculate").show();
								$("#paCalculate").hide();
									$.ajax( {
											type : 'post',
											cache : false,
											contentType : 'application/json',
											url : '/pa/salary/paWithholdingCalculate?'+ urlParam + '&paMonth=' +paMonth+'&statNo='+statNo+'&empType='+empType,
											dataType : "json",
											
											success : function(responseStr) {
												$("#paCalculateResult",navTab.getCurrentPanel()).html(responseStr);
												$("#loading_viewPaCalculate",navTab.getCurrentPanel()).hide();
												$("#paCalculate",navTab.getCurrentPanel()).show();
											}
									});
							}
						});

	}
	
	function f_Calculate_viewArcalculate() {
		var $form = $("#form_viewPaCalculate_Yuti",navTab.getCurrentPanel());
		var paMonth = $form.find("#paYear").val() + $form.find("#paMonth").val();
		var statNo = $form.find("#STAT_NO_YU").val();
		var CPNY_ID = $form.find("#CPNY_ID").val();
		var str = "";
		if(statNo == ''){
			alertMsg.error("<spring:message code='pa.salary.title.salaryProvideStatnoIsMust'/>");
			return;
		}
		
		var empType = "";
		  
	   empType  = "'"+$("#STAT_NO_YU").val()+"'"+"!";
	       
		    if(empType.length == 0){    
		    	//请选择部门
				alertMsg.error("请选择人员类型组");
			    return ;
		    }
	    	empType = empType.substring(0, empType.lastIndexOf('!'));
		
		

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
				"<spring:message code='pa.viewiscalculate.title.iscal'/>"+"[" + paMonth + "]"+"月的预提月考勤汇总"+"?",
						{
							okCall : function() {
								$("#loading_viewPaCalculate").show();
								$("#paCalculate").hide();
									$.ajax( {
											type : 'post',
											cache : false,
											contentType : 'application/json',
											url : '/pa/salary/paWithholdingArCalculate?'+ urlParam + '&paMonth=' +paMonth+'&statNo='+statNo+'&empType='+empType,
											dataType : "json",
											
											success : function(responseStr) {
												$("#paCalculateResult",navTab.getCurrentPanel()).html(responseStr);
												$("#loading_viewPaCalculate",navTab.getCurrentPanel()).hide();
												$("#paCalculate",navTab.getCurrentPanel()).show();
											}
									});
							}
						});

	}
	
	
	function f_apply_closed() {
		var $form = $("#form_viewPaCalculate_Yuti",navTab.getCurrentPanel());
		var paMonth = $form.find("#paYear").val() + $form.find("#paMonth").val();
		var statNo = $form.find("#STAT_NO_YU").val();
		var CPNY_ID = $form.find("#CPNY_ID").val();
		var str = "";
		if(statNo == ''){
			alertMsg.error("<spring:message code='pa.salary.title.salaryProvideStatnoIsMust'/>");
			return;
		}
		
		var empType = "";
	//	$("input[name='empType_isChecked']:checkbox").each(function(){ 
	         //   if($(this).attr("checked")){
	         //       empType += "'"+$(this).val()+"'"+"!";
	         //   }
	       // })
	       
	      
	       empType += "'"+$("#STAT_NO_YU").val()+"'"+"!";
		    if(empType.length == 0){    
		    	//请选择部门
				alertMsg.error("请选择员工类型");
			    return ;
		    }
	    	empType = empType.substring(0, empType.lastIndexOf('!'));
	    	
	    	
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
				"确认申请"+"[" + paMonth + "]"+"预提工资关闭"+"?",
						{
							okCall : function() {
								$("#loading_viewPaCalculate").show();
								$("#paCalculate").hide();
									$.ajax( {
											type : 'post',
											cache : false,
											contentType : 'application/json',
											url : '/pa/salary/paWithholdingApplyclosed?FLAG=1&'+ urlParam + '&paMonth=' +paMonth+'&empType='+empType,
											dataType : "json",
											
											success : function(responseStr) {
												$("#paCalculateResult",navTab.getCurrentPanel()).html(responseStr);
												$("#loading_viewPaCalculate",navTab.getCurrentPanel()).hide();
												$("#paCalculate",navTab.getCurrentPanel()).show();
											}
									});
							}
						});

	}
	

function f_apply_closedss() {
		var $form = $("#form_viewPaCalculate_Yuti",navTab.getCurrentPanel());
		var paMonth = $form.find("#paYear").val() + $form.find("#paMonth").val();
		var statNo = $form.find("#STAT_NO_YU").val();
		var CPNY_ID = $form.find("#CPNY_ID").val();
		var str = "";
		if(statNo == ''){
			alertMsg.error("<spring:message code='pa.salary.title.salaryProvideStatnoIsMust'/>");
			return;
		}
		
		var empType = "";
 
	 
	        empType = "'"+$("#STAT_NO_YU").val()+"'"+"!";
 
	         
		    if(empType.length == 0){    
		    	//请选择部门
				alertMsg.error("请选择员工类型");
			    return ;
		    }
	    	empType = empType.substring(0, empType.lastIndexOf('!'));
	    	
	    	
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
				"确认申请"+"[" + paMonth + "]"+"预提工资关闭解除"+"?",
						{
							okCall : function() {
								$("#loading_viewPaCalculate").show();
								$("#paCalculate").hide();
									$.ajax( {
											type : 'post',
											cache : false,
											contentType : 'application/json',
											url : '/pa/salary/paWithholdingApplyclosed?FLAG=0&'+ urlParam + '&paMonth=' +paMonth+'&empType='+empType,
											dataType : "json",
											
											success : function(responseStr) {
												$("#paCalculateResult",navTab.getCurrentPanel()).html(responseStr);
												$("#loading_viewPaCalculate",navTab.getCurrentPanel()).hide();
												$("#paCalculate",navTab.getCurrentPanel()).show();
											}
									});
							}
						});

	}
</script>
<form name="form_viewPaCalculate_Yuti" method="post" action="" id="form_viewPaCalculate_Yuti">
		<div id="loading_viewPaCalculate"
			style="width: 100%; text-align: center; padding-top: 200px; position: absolute;">
			<img src="/resources/images/loading.gif">
		</div>
	<!-- 工资计算 -->
	<div id="paCalculate" title='预提<spring:message code="pa.salary.title.salarycalculation"/>' style="padding-top: 10px;">
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
						预提<spring:message code="pa.salary.title.salarycalculation"/><!-- 工资计算 --></td>
				</tr>
			</table>
		 
		<div class="formBar">
		<ul class="toolBar">
		        <li>
					<a onclick="f_apply_closed()">
						<span>预提工资申请关闭</span>
					</a>
				</li>
				<li>
					<a onclick="f_apply_closedss()">
						<span>预提工资申请关闭解除</span>
					</a>
				</li>
				<!-- 
				<li>
					<a onclick="f_apply_confirm()">
						<span><spring:message code="pa.salary.title.salaryconfirmapply"/>工资申请确认</span>
					</a>
				</li>
				 -->
			</ul>
			<ul class="toolBar">
				<li>
					<a onclick="f_Calculate_viewArcalculate()">
						<span>预提考勤汇总计算</span>
					</a>
				</li>
				<li>
					<a onclick="f_Calculate_viewPacalculate()">
						<span>预提<spring:message code="pa.salary.title.salarycalculation"/><!-- 工资计算 --></span>
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
					<ait:dateProMonth yearName="paYear" monthName="paMonth"/>
				</td>
			</tr>
			<tr>
				<td width="15%" class="td_title">
				人员类型组
				</td>
				<td class="td_type">
				 <!--<input name="empType_isChecked" id="empType_isChecked_211812" 
						value="211812"  type="checkbox" style="border:0px"/>
						营业职
				 <input name="empType_isChecked" id="empType_isChecked_211814" 
						value="211814"  type="checkbox" style="border:0px"/>
						一般家电促销员
						
				--><select id="STAT_NO_YU" name="STAT_NO_YU">
					 
			 <c:forEach items="${statList}" var="stat">
					 <option value="${stat.STAT_NO}" <c:if test="${stat.STAT_NO eq PAY_AREA_CD}">selected</c:if>>${stat.STAT_NAME}</option>
				 </c:forEach>		 			
								</select>		
						
			     <input id="CPNY_ID" name="CPNY_ID" type="hidden" value="${CPNY_ID }"/>
			     
				</td>
			</tr>
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