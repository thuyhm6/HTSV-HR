<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script type="text/javascript">  
	$(function() {
		$("#loading_viewPaCalculate").hide();
	});
 
	 function changeState(){
		
		var $form = $("#form_viewPaCalculate",navTab.getCurrentPanel());
	 	var duixiang = $form.find("#duixiang").val();
	 	
	 	$form.find("#STAT_NO").val(duixiang);
	  
 		if(duixiang == '211812'){ 
		 	duixiang ='yingyeyuan';
	 	}else if(duixiang == '211814'){
	 		duixiang = 'cuxiaoyuan';
	 	}   
		var paMonth = $form.find("#paYear").val() + $form.find("#paMonth").val();
		var STAT_NO = $form.find("#STAT_NO").val();
		var CPNY_ID = $form.find("#CPNY_ID").val(); 
		var str = "";
		 
	  
		var deptStr = $form.find(":input[name='seach_PAY_AREA_CD']").val();
		 
		urlParam = 'deptid=' + deptStr;
				 $.ajax( {
										type : 'post',
										cache : false,
										contentType : 'application/json',
										url : '/pa/salary/paSalaryTransAjaxYuti?'+ urlParam + '&paMonth=' +paMonth+'&duixiang='+duixiang+'&STAT_NO='+STAT_NO,
										dataType : "json",
										
										success : function(responseStr) {
					 							 
					 					 	  if(responseStr=='已传送'){
					 					 		   $("#chuansongid").hide();
					 					 	  }else{
					 					 		   $("#chuansongid").show();
					 					 	  }
											$("#paClose",navTab.getCurrentPanel()).html(responseStr);
										 
											 
										}
							});		

				 changeStatePaclose();	     

	 }
	 function changeStatePaclose(){
			var $form = $("#form_viewPaCalculate",navTab.getCurrentPanel());
			var paMonth = $form.find("#paYear").val() + $form.find("#paMonth").val();
			var STAT_NO = $form.find("#STAT_NO").val();
			var CPNY_ID = $form.find("#CPNY_ID").val(); 
			var str = "";
			var	deptStr = $form.find(":input[name='seach_PAY_AREA_CD']").val();
			urlParam = 'deptid=' + deptStr;
		 	var duixiang = $form.find("#duixiang").val();
		 	if(duixiang == '211812'){ 
			 	duixiang ='yingyeyuan';
		 	}else if(duixiang == '211814'){
		 		duixiang = 'cuxiaoyuan';
		 	}  
					 $.ajax( {
											type : 'post',
											cache : false,
											contentType : 'application/json',
											url : '/pa/salary/paSalaryTransAjaxClose?'+ urlParam + '&paMonth=' +paMonth+'&duixiang='+duixiang+'&STAT_NO='+STAT_NO,
											dataType : "json",
											
											success : function(responseStr) {
						 					 	  if(responseStr==0){
						 					 		   $("#chuansongid").hide();
						 					 		 responseStr='未关闭';
						 					 	  }else{
						 					 		   $("#chuansongid").show();
						 					 		 responseStr = '已关闭';
						 					 	  }
												$("#paClose2",navTab.getCurrentPanel()).html(responseStr);
												$("#paCalculateResult",navTab.getCurrentPanel()).html("");
											 
												 
											}
								});			     

		}
 
	function f_salary_confirm() {
		var flag = 0;
		
		var $form = $("#form_viewPaCalculate",navTab.getCurrentPanel());
	 
		var paMonth = $form.find("#paYear").val() + $form.find("#paMonth").val();
	 
		var statNo = $form.find("#STAT_NO").val();
		
		var CPNY_ID = $form.find("#CPNY_ID").val();
		var duixiang = $form.find("#duixiang").val();
		statNo = duixiang;
		if(duixiang == '211812'){ 
		 	duixiang ='yingyeyuan';
		 	
	 	}else if(duixiang == '211814'){
	 		duixiang = 'cuxiaoyuan';
	 	}  
		if(duixiang==null||duixiang==""){
			alertMsg.error("请选择传送对象");
			return  false;
		}
	 
		var str = "";
		if(statNo == ''){
			alertMsg.error("<spring:message code='pa.salary.title.salaryProvideStatnoIsMust'/>");
			return;
		} 
		
 		var deptStr = $form.find(":input[name='seach_PAY_AREA_CD']").val();
 		var urlParam = "";
		urlParam = 'deptid=' + deptStr;
	 	 
		
		
		  		 $("#paCalculate").hide();
					 $.ajax( {
										type : 'post',
										cache : false,
										contentType : 'application/json',
										url : '/pa/salary/paSalaryTransferYuti?'+ urlParam + '&paMonth=' +paMonth+'&duixiang='+duixiang+'&statNo='+statNo+'&flag='+flag,
										dataType : "json",
										
										success : function(responseStr) {
											 $("#paClose",navTab.getCurrentPanel()).html("已传送");
											$("#paCalculateResult",navTab.getCurrentPanel()).html(responseStr);
										 
											$("#paCalculate",navTab.getCurrentPanel()).show();
											
										}
							});			     

	}

 </script>
<form name="form_viewPaCalculate" method="post" action="" id="form_viewPaCalculate">
		 
	<!-- 财务传送 -->
	<div id="paCalculate" title='预提传送' style="padding-top: 10px;">
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
						营业员/促销员预提传送<!-- 工资计算 --></td>
				</tr>
			</table>
					<div id="chuansongidss" class="formBar">
						<ul class="toolBar">
							<li>
								<a onclick="f_salary_confirm()">
									<span>预提<spring:message code="pa.salary.title.salaryconfirm.chuansong"/><!-- 传送 --></span>
								</a>
							</li>
						</ul>
					</div>
		
		<table width="100%"  border="0" cellspacing="0" cellpadding="0" class="lge_table">
			<tr>
				<td   class="td_title">
					<spring:message code="pa.insurance.title.salaryMonth"/><!--工资月-->
				</td>
						<td class="td_type">
							<ait:dateProMonth yearName="paYear" monthName="paMonth"  />
						</td>
				<c:if test="${CPNY_ID eq 'TSTO' }"> 
			 	<td class="td_title"><spring:message code="inct.salesman.daqu" /> <!--大区-->：</td>
				 
		    			<td ><ait:deptTreeMulti id="seach_PAY_AREA_CD" name="seach_PAY_AREA_NM" limit="pa" level="2" ></ait:deptTreeMulti>
				 
	 </td></c:if>	 
				
			<td   class="td_title">
					<spring:message code="pa.insurance.title.salarytransferobject"/><!--传送对象-->
				</td>
						<td class="td_type">
						 
 <ait:SelectEmpTypeCode id="duixiang" name="duixiang" selected="${emptypeture}"    limit="pa" type="group" notin="211812,211814"/>
							 
							 <input id="CPNY_ID" name="CPNY_ID" type="hidden" value="${CPNY_ID}"/>
							 <input id="STAT_NO" name="STAT_NO" type="hidden" value=""/>
						</td>
			</tr><!--
			<tr>
						 <td class="td_title"><spring:message code="pa.insurance.title.gongziguanbi"/>工资关闭:</td>
							
						<td> <div id="paClose2" style="color:#F00;"></div></td>
						 <td class="td_title"><spring:message code="pa.insurance.title.chuansongzhuangtai"/>传送状态：</td>
						
						<td> <div id="paClose" style="color:#F00;"></div></td>  
			</tr>
		
			 	--><tr>
				<td  class="td_title">
					传送结果:
				</td>
				<td class="td_type" colspan="5">
					<div id="paCalculateResult" style="color:#F00;"></div>
				</td>
			</tr>
		</table>
	</div>
</form>