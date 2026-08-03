<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script type="text/javascript">

	$(function() {
		$("#loading_viewarmonthcalculate").hide();
	});
 	function  f_apply_confirm(){
 		 

 		var arMonth = document.form_viewarmonthcalculate.arYear.value + document.form_viewarmonthcalculate.arMonth.value;
 		 
		var STAT_NO = document.form_viewarmonthcalculate.STAT_NO.value;
		var CPNY_ID = document.form_viewarmonthcalculate.CPNY_ID.value;
		var deptStr="";
		if(CPNY_ID == 'TSTO'){
	  
            $("input[name='isChecked']:checkbox").each(function(){ 
                if($(this).attr("checked")){
                    deptStr +=$(this).val()+"!";
                }
            })
        if(deptStr.length == 0){    
		        alertMsg.error("请选择大区");
		        return ;
        }
       
        deptStr=deptStr.substring(0, deptStr.lastIndexOf('!'));
        
         
        }
       
		
		$("#monthCalculate").hide();
			$.ajax( {
					type : 'post',
					cache : false,
					contentType : 'application/json',
					url : '/ar/attendanceMintenance/monthCalculateConfirmApply?arMonth=' + arMonth + '&STAT_NO='+STAT_NO+'&AR_DEPT_NO='+deptStr,
					dataType : "json",
					
					success : function(responseStr) {
						$("#armonth_calculateResult").html(responseStr);
						 
						$("#monthCalculate").show();
					}
			});

 		
 	}
	function f_Calculate_viewarmonthcalculate() {
//		var arMonth = $("#arYear").val() + $("#arMonth").val();

		var arMonth = document.form_viewarmonthcalculate.arYear.value + document.form_viewarmonthcalculate.arMonth.value;
		var STAT_NO = document.form_viewarmonthcalculate.STAT_NO.value;
		var CPNY_ID = document.form_viewarmonthcalculate.CPNY_ID.value;
		var deptStr="";
		if(CPNY_ID == 'TSTO'){
	  
            $("input[name='isChecked']:checkbox").each(function(){ 
                if($(this).attr("checked")){
                    deptStr +="'"+$(this).val()+"'"+"!";
                }
            })
        if(deptStr.length == 0){    
		        alertMsg.error("请选择大区");
		        return ;
        }
       
        deptStr=deptStr.substring(0, deptStr.lastIndexOf('!'));
        }
        //   alert(deptStr);
		alertMsg.confirm(
					//是否计算N月的考勤
						"<spring:message code='ar.viewararmonthcalculate.title.iscal'/>"+"[" + arMonth + "]"
							+"<spring:message code='ar.viewararmonthcalculate.title.arskaoqin'/>",
						{
							okCall : function() {
							    icon=1;
								$("#loading_viewarmonthcalculate").show();
								$("#monthCalculate").hide();
									$.ajax( {
											type : 'post',
											cache : false,
											contentType : 'application/json',
											url : '/ar/attendanceMintenance/monthCalculate?arMonth=' + arMonth + '&STAT_NO='+STAT_NO+'&AR_DEPT_NO='+deptStr,
											dataType : "json",
											
											success : function(responseStr) {
												$("#armonth_calculateResult").html(responseStr);
												$("#loading_viewarmonthcalculate").hide();
												$("#monthCalculate").show();
												icon=0;
					                            checkSession();
											}
									});
							}
						});

	}
	
	function  f_applyClose_Guan(){
	    //var  bool = true;
 		var arMonth = document.form_viewarmonthcalculate.arYear.value + document.form_viewarmonthcalculate.arMonth.value;
		var STAT_NO = document.form_viewarmonthcalculate.STAT_NO.value;
		var CPNY_ID = document.form_viewarmonthcalculate.CPNY_ID.value;
		var deptStr="";
		if(CPNY_ID == 'TSTO'){
	       $("input[name='isChecked']:checkbox").each(function(){ 
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
		$.pdialog.open("/ar/attendanceMintenance/viewApplyCloseGuan?FLAG=1&arMonth="+arMonth+"&STAT_NO="+STAT_NO+"&AR_DEPT_NO="+deptStr, "f_applyClose_Guan", "考勤申请关闭", {width:800,height:350,mask:true});
 	}
 	
 	function  f_applyClose_Open(){
 		 
 		var arMonth = document.form_viewarmonthcalculate.arYear.value + document.form_viewarmonthcalculate.arMonth.value;
		var STAT_NO = document.form_viewarmonthcalculate.STAT_NO.value;
		var CPNY_ID = document.form_viewarmonthcalculate.CPNY_ID.value;
		
		var deptStr="";
		if(CPNY_ID == 'TSTO'){
	  
            $("input[name='isChecked']:checkbox").each(function(){ 
                if($(this).attr("checked")){
                    deptStr += $(this).val()+"!";
                }
            })
        if(deptStr.length == 0){    
		        alertMsg.error("请选择大区");
		        return ;
        }
       
        deptStr=deptStr.substring(0, deptStr.lastIndexOf('!'));
        }
       
		 
		$("#monthCalculate").hide();
			$.ajax( {
					type : 'post',
					cache : false,
					contentType : 'application/json',
					url : '/ar/attendanceMintenance/applyCloseGuanOpen?FLAG=0&arMonth=' + arMonth + '&STAT_NO='+STAT_NO+'&AR_DEPT_NO='+deptStr,
					dataType : "json",
					
					success : function(responseStr) {
						$("#armonth_calculateResult").html(responseStr);
						 
						$("#monthCalculate").show();
					}
			});

 		
 	}
 function f_Calculate_shihou(){
	var str = "";
	 
  //是否开始计算?
	alertMsg.confirm("<spring:message code='ar.alert.message.viewardetailcaculate.canstart'/>", {
		okCall: function(){
		    icon=1;
			$("#loading_viewarmonthcalculate").show();
			$("#monthCalculate").hide();
			$.ajax({
				type: 'post',
				cache: false,
				contentType: 'application/json',
				url: '/ar/attendanceMintenance/detailShiHouCalculate',
	
				dataType: 'json',
				success: function(responseStr) {
				    
					$("#armonth_calculateResult").html(responseStr);
					$("#loading_viewarmonthcalculate").hide();
					$("#monthCalculate").show();
					icon=0;
					checkSession();
				}
			});
		}
	});
}	

function  f_arSummary_Email(){
	    //var  bool = true;
	    var num = 1;
 		var arMonth = document.form_viewarmonthcalculate.arYear.value + document.form_viewarmonthcalculate.arMonth.value;
		var STAT_NO = document.form_viewarmonthcalculate.STAT_NO.value;
		var CPNY_ID = document.form_viewarmonthcalculate.CPNY_ID.value;
		var deptStr="";
		if(CPNY_ID == 'TSTO'){
	       $("input[name='isChecked']:checkbox").each(function(){ 
                if($(this).attr("checked")){
                    deptStr += $(this).val()+"!";
                }
            });

        if(deptStr.length == 0){  
           //alert("请选择大区");
		    alertMsg.error("请选择大区");
		    //bool = false;
		    return false;
        }
         deptStr=deptStr.substring(0, deptStr.lastIndexOf('!'));
          //根据条件查找该条件下的考勤汇总是否计算如果计算了发送邮件   如果没有返回false  提示还没有考勤月汇总
        $.ajax({
				type: 'post',
				cache: false,
				contentType: 'application/json',
				url: '/ar/attendanceMintenance/findPersonByItem?AR_MONTH=' + arMonth + '&STAT_NO='+STAT_NO+'&PAY_AREA_NO='+deptStr,
				dataType: 'json',
				success: function(responseStr){
				    if(responseStr.length>0){
				        //alert();
				        $.ajax({
				             type: 'post',
				             cache: false,
				             contentType: 'application/json',
				             url: '/ar/attendanceMintenance/sendArEmpEmail?AR_MONTH=' + arMonth + '&STAT_NO='+STAT_NO+'&PAY_AREA_NO='+deptStr,
				             dataType: 'json',
				             success: function(responseStr){
				                alert('发送成功！');
  				             }
			             });
				    }else{
	                    alert("该月还没有汇总数据，请等待汇总计算结果!");	
	                    num = 0;		    
  				    }
				}
			});
        }else{
         //根据条件查找该条件下的考勤汇总是否计算如果计算了发送邮件   如果没有返回false  提示还没有考勤月汇总
        $.ajax({
				type: 'post',
				cache: false,
				contentType: 'application/json',
				url: '/ar/attendanceMintenance/findPersonByItem?AR_MONTH=' + arMonth + '&STAT_NO='+STAT_NO,
				dataType: 'json',
				success: function(responseStr) {
				 if(responseStr.length>0){
				        $.ajax({
				             type: 'post',
				             cache: false,
				             contentType: 'application/json',
				             url: '/ar/attendanceMintenance/sendArEmpEmail?AR_MONTH=' + arMonth + '&STAT_NO='+STAT_NO,
				             dataType: 'json',
				             success: function(responseStr){
				                alert('发送成功！');
  				             }
			             });
				        
				    }else{
	                    alert("该月还没有汇总数据，请等待汇总计算结果!");	
	                    num = 0;		    
  				    }
				}
			});
        }
        if(num = 0){
            return false;
        }
 	}
 	
</script>
<style type="text/css">
body {
	padding: 5px;
	margin: 0;
	padding-bottom: 15px;
}

#layout1 {
	width: 99%;
	margin: 0;
	padding: 0;
}

.l-table-edit {
	
}

.l-table-edit-td {
	padding: 4px;
}

.l-button-submit,.l-button-test {
	width: 80px;
	float: left;
	margin-left: 10px;
	padding-bottom: 2px;
}

.l-verify-tip {
	left: 230px;
	top: 120px;
	h4
	{
	margin
	:
	20px;
}
</style>

<form name="form_viewarmonthcalculate" method="post" action=""
	id="form_viewarmonthcalculate">

	<div id="loading_viewarmonthcalculate"
		style="width: 100%; text-align: center; padding-top: 200px; position: absolute;">
		<img src="/resources/images/loading.gif">
	</div>
	<!-- 汇总计算 -->
	<div id='monthCalculate'
		title="<spring:message code='ar.viewararmonthcalculate.title.huizongjisuan'/>"
		style="padding-top: 10px;">
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			style="display:none">
			<tr>
				<td align="left"
					style="font-family: 'Arial','simsun';
				font-size: 14px;color:#333333;font-weight: bold;
				background-image: url(/resources/images/title/top_1.gif);
				background-repeat: no-repeat;
				padding-right: 0px;
				padding-left: 18px;
				padding-top: 3px;
				padding-bottom: 2px;">
					<!-- 月考勤汇总 -->
					<spring:message
						code="ar.viewararmonthcalculate.title.yuekaoqinhuizong" />
				</td>
			</tr>
		</table>
		<div class="formBar">
			<ul class="toolBar">
				<li><a onclick="f_Calculate_shihou();"><span>事后申请计算</span>
				</a></li>
				<li><a id="applyCloseGuan" onclick="f_applyClose_Guan()"
					target=""> <span>考勤申请关闭</span> </a></li>
				<li><a onclick="f_applyClose_Open()"> <span>考勤申请关闭解除</span>
				</a></li>
				<li><a onclick="f_apply_confirm()"> <span><spring:message
								code="pa.salary.title.salaryconfirmapply" />
							<!-- 考勤申请确认 -->
					</span> </a></li>
				<li><a onclick="f_Calculate_viewarmonthcalculate()"> <!-- 汇总计算 -->
						<span><spring:message
								code="ar.viewararmonthcalculate.title.huizongjisuan" />
					</span> </a></li>
				<li><a onclick="f_arSummary_Email()"> <!-- 发送邮件 -->
						<span>发送邮件</span> </a></li>

			</ul>
		</div>
		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			class="lge_table">
			<tr>
				<td width="15%" class="td_title">
					<!-- 考勤月 -->
					<spring:message code="ar.excelexport.title.armonth" />:</td>
				<td class="td_type"><ait:date yearName="arYear"
						monthName="arMonth" /></td>
			</tr>
			<tr>
				<td width="15%" class="td_title">
					<!-- 区间 -->
					<spring:message code="ar.viewcycleparameter.title.qujian" />:</td>
				<td class="td_type"><select class="combox" name="STAT_NO">
						<c:forEach items="${statnoList}" var="list">
							<option value="${list.STAT_NO}">${list.STAT_NAME}</option>
						</c:forEach>
				</select></td>
			</tr>
			<input id="CPNY_ID" name="CPNY_ID" type="hidden" value="${CPNY_ID }" />
			<c:if test="${CPNY_ID eq 'TSTO' }">
				<tr>
					<td width="15%" class="td_title">地域区分:</td>
					<td class="td_type"><c:forEach items="${deptList}" var="vlist"
							varStatus="i">
							<input name="isChecked" value="${vlist.DEPTNO}" type="checkbox" />
							   ${vlist.DEPTNAME} 
							  <c:if test="${i.count % 5 == 0}">

							</c:if>
						</c:forEach></td>
				</tr>
			</c:if>
			<tr>
				<td width="15%" class="td_title">
					<!-- 计算结果 -->
					<spring:message code="ar.viewararmonthcalculate.title.jisuanjieguo" />:
				</td>
				<td class="td_type">
					<div id="armonth_calculateResult"></div></td>
			</tr>
		</table>
	</div>
</form>
