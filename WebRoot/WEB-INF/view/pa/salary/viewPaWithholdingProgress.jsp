<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script type="text/javascript">

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
		
		var empType = "";
		$("input[name='empType_isChecked']:checkbox").each(function(){ 
	            if($(this).attr("checked")){
	                empType += "'"+$(this).val()+"'"+"!";
	            }
	        })
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
		
		var empType = "";
		$("input[name='empType_isChecked']:checkbox").each(function(){ 
	            if($(this).attr("checked")){
	                empType += "'"+$(this).val()+"'"+"!";
	            }
	        })
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
		
		var empType = "";
		$("input[name='empType_isChecked']:checkbox").each(function(){ 
	            if($(this).attr("checked")){
	                empType += "'"+$(this).val()+"'"+"!";
	            }
	        })
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
												$("#paCalculateResult").html(responseStr);
												$("#loading_viewPaCalculate").hide();
												$("#paCalculate").show();
											}
									});
							}
						});

	}
</script>
<div class="pageHeader">
<form id="form_viewPaCalculate" name="form_viewPaCalculate" onsubmit="return navTabSearch(this);" 
			action="/pa/salary/viewPaWithholdingProgress" method="post" rel="pagerForm">
 <div class="searchBar"> 
		<table class="searchContent">
			<tr>
				<td >
					<spring:message code="pa.insurance.title.salaryMonth"/><!--工资月-->
				</td>
				<td>
					<ait:dateProMonth yearName="paYear" monthName="paMonth" yearSelected="${paYear}" monthSelected="${paMonth}"/>
				</td>
				<c:if test="${CPNY_ID eq 'TSTO' }"> 
					<td>
						<!-- 大区 --><spring:message code="alert.hrm.dept.daqu"/>
					</td>
	    			<td >
	    				<ait:deptTreeMulti id="seach_DEPT_NO" name="seach_PAY_AREA_NM"  selectedNm="${PAY_AREA_NAME}" selected="${PAY_AREA_ID}" limit="pa" level="2" ></ait:deptTreeMulti>
					</td>
				</c:if>	
				<td>
				人员类型组
				</td>
				<td class="td_type">
				<ait:SelectEmpTypeCode name="empType_isChecked" selected="${emptypeture}"   limit="pa" type="group" notin="211812,211814"/>
					 
					<input id="CPNY_ID" name="CPNY_ID" type="hidden" value="${CPNY_ID }"/>
				</td>
			</tr>
		</table>
		<div class="subBar">
					<ul>
						<li>
							<div class="buttonActive">
								<div class="buttonContent">
									<button type="submit">
										<spring:message code="public.title.search"/><!--检索-->
									</button>
								</div>
							</div>
						</li>
					</ul>
				</div>
	</div>
</form>
</div>
<div class="pageContent">
	
	<table class="table" width="100%" layoutH="171">
		<thead>
			<tr>
				<th width="15%"><!-- 工资月 --><spring:message code="ar.viewarprogress.title.gongziyue"/></th>
				<th width="20%"> 员工类型 </th>
				<c:if test="${CPNY_ID eq 'TSTO' }">
				  <th width="20%">地域区分</th>
				</c:if>
				<!--<th width="25"> 日考勤锁定<spring:message code="ar.viewarprogress.title.rikaoqinsuoding"/></th> -->
				<th width="25%">预提工资关闭</th>
				<th width="25%">预提工资传送</th>
				<!-- <th width="25">工资锁定 <spring:message code="ar.viewarprogress.title.gongzisuoding"/></th>-->
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${itemList}" var="list" varStatus="i">
			
				<tr>
					<td style="text-align:center" >${list.PA_MONTH_STR}</td>
					<td style="text-align:center" >${list.EMPTYPE_NAME}</td>
				     <c:if test="${CPNY_ID eq 'TSTO' }">
					   <td style="text-align:center" >${list.AR_DEPT_NAME}</td>
					 </c:if>
					<td style="text-align:center" >
						<img src="/resources/images/${list.PA_WITHHOLDING_LOCK_FLAG }.gif"  style="cursor:hand">
					</td>
					<td style="text-align:center" >
						<img src="/resources/images/${list.IFYUTI}.gif"  style="cursor:hand">
					</td>

				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div id="viewArProgress" style="border:10;overflow:auto;bottom:10;right:100;width:300;z-index:1;"></div>
	<c:set value="/ar/attendanceMintenance/viewArProgress" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>