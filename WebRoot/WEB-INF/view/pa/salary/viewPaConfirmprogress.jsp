<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script type="text/javascript">  
	$(function() {
		$("#loading_viewPaCalculate").hide();
	});
 
	 
	function f_salary_confirm(a) {
		
		var flag = a;
		var $form = $("#form_viewPaCalculate_confirm",navTab.getCurrentPanel());
		var paMonth = $form.find("#paYearpaConfirm").val() + $form.find("#paMonthpaConfirm").val();
		var statNo = $form.find("#STAT_NO_PA_CONFIRM").val();
		var CPNY_ID = $form.find("#CPNY_ID").val();
		var str = "";
		if(statNo == ''){
			alertMsg.error("<spring:message code='pa.salary.title.salaryProvideStatnoIsMust'/>");
			return;
		}

		var deptStr = "";
		if(CPNY_ID == 'TSTO'){
	        $("input[name='pa_isChecked_confirm']:checkbox").each(function(){ 
	            if($(this).attr("checked")){
	                deptStr +=   $(this).val() +"!";
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
		
		 
					 
					 
						 $("#paCalculate_confirm").hide();
					 $.ajax( {
										type : 'post',
										cache : false,
										contentType : 'application/json',
										url : '/pa/salary/paSalaryConfirm?'+ urlParam + '&paMonth=' +paMonth+'&statNo='+statNo+'&flag='+flag,
										dataType : "json",
										
										success : function(responseStr) {
											$("#paCalculateResult_confirm").html(responseStr);
										 
											$("#paCalculate_confirm").show();
										}
							});
					 
						     

	}
	
	//工资开放
	function Salary_open() {
		var $form = $("#form_viewPaCalculate_confirm",navTab.getCurrentPanel());
		var statNo = $form.find("#STAT_NO_PA_CONFIRM").val();
		var paYear = $form.find("#paYearpaConfirm").val();
		var PaM = $form.find("#paMonthpaConfirm").val();
		var paMonth = paYear + PaM;
		if(statNo == '219946'){
			if(PaM == '01'){
				paYear = paYear - 1 ;
				PaM = '12' ;
			}else if(PaM == '11' || PaM == '12'){
				PaM = PaM -1 ;
			}else{
				PaM = '0'+(PaM -1) ;
			}
		}
		var fPaMonth =  paYear + PaM ;//财务检查月份 营业职传财务月份减一
		var CPNY_ID = $form.find("#CPNY_ID").val();
		var str = "";
		if(statNo == ''){
			alertMsg.error("<spring:message code='pa.salary.title.salaryProvideStatnoIsMust'/>");
			return;
		}

		var deptStr = "";
		if(CPNY_ID == 'TSTO'){
	        $("input[name='pa_isChecked_confirm']:checkbox").each(function(){ 
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
				//"<spring:message code='pa.viewiscalculate.title.iscal'/>"+"[" + paMonth + "]"+"<spring:message code='pa.viewpacalculate.title.salary'/>"+"?",
				"是否开放"+"[" + paMonth + "]"+"月工资"+"?",
						{
							okCall : function() {
								$("#loading_viewPaCalculate").show();
								$("#paCalculate_confirm").hide();
									$.ajax( {
											type : 'post',
											cache : false,
											contentType : 'application/json',
											url : '/pa/salary/paSalaryopen?FLAG=1&'+ urlParam + '&paMonth=' +paMonth+'&statNo='+statNo+'&fPaMonth='+fPaMonth,
											dataType : "json",
											
											success : function(responseStr) {
												$("#paCalculateResult_confirm").html(responseStr);
												$("#loading_viewPaCalculate").hide();
												$("#paCalculate_confirm").show();
											}
									});
							}
						});

	}

 </script>
<form name="form_viewPaCalculate_confirm" method="post" action="" id="form_viewPaCalculate_confirm">
		 
	<!-- 工资确认 -->
	<div id="paCalculate_confirm" title='<spring:message code="pa.salary.title.salaryconfirm"/>' style="padding-top: 10px;">
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
						<spring:message code="pa.salary.title.salaryconfirm"/><!-- 工资计算 --></td>
				</tr>
			</table>
		 
		<div class="formBar">
		<ul class="toolBar">
				<li>
					<a onclick="f_salary_confirm(1)">
					<c:if test="${CPNY_ID eq 'LGEYT' }"> 
						<span>确认正规职</span>
					</c:if>	
					<c:if test="${CPNY_ID ne 'LGEYT' }"> 
						<span><spring:message code="pa.salary.title.salaryconfirm"/><!-- 工资申请确认 --></span>
					</c:if>	
					</a>
				</li>
			</ul>
	 
			 <ul class="toolBar">
				<li>
					<a onclick="f_salary_confirm(2)">
						<span><spring:message code="pa.salary.title.onfirmCancel"/><!-- 工资取消确认 --></span>
					</a>
				</li>
			</ul>
			<ul class="toolBar">
				<li>
					<a onclick="Salary_open()">
						<span>工资开放</span>
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
					<ait:date yearName="paYearpaConfirm" monthName="paMonthpaConfirm"/>
				</td>
			</tr>
			<tr>
				<td width="15%" class="td_title">
					<!-- 区间 --><spring:message code="ar.viewcycleparameter.title.qujian"/>
				</td>
				<td class="td_type">
					<select id="STAT_NO_PA_CONFIRM" name="STAT_NO_PA_CONFIRM">
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
						     <input name="pa_isChecked_confirm"  id="pa_isChecked_confirm_${vlist.DEPTNO}"  value="${vlist.DEPTNO}"  type="checkbox"  />
							   ${vlist.DEPTNAME} 
							  <c:if test="${i.count % 5 == 0}">  
							    
							  </c:if>
					    </c:forEach>
				</td>
			</tr>
			</c:if>	
			 	<tr>
				<td width="15%" class="td_title">
					<!-- 计算结果 --><spring:message code="ar.viewararmonthcalculate.title.querenjieguo"/>:
				</td>
				<td class="td_type">
					<div id="paCalculateResult_confirm" style="color:#F00;"></div>
				</td>
			</tr>
		</table>
	</div>
</form>
<c:if test="${CPNY_ID eq 'LGEPN'}"> 
<div class="pageHeader">
	<form id="viewarmonth" name="viewPaConfirmMonth" onsubmit="return navTabSearch(this);" action="/pa/salary/viewPaConfirmprogress" method="post" rel="pagerForm">
	<div class="searchBar" >
		<table class="searchContent">
			<tr>
				<td >
					<spring:message code="pa.insurance.title.salaryMonth"/><!--工资月-->
				</td>
				<td >
					<ait:date yearName="paYear" monthName="paMonth" yearSelected="${paYear }" monthSelected="${paMonth }"/>
				</td>

				<td>
					<!-- 区间 --><spring:message code="ar.viewcycleparameter.title.qujian"/>
				</td>
				<td>
					<select id="STAT_NO_PA" name="STAT_NO_PA">
						<c:forEach items="${statList}" var="stat">
							<option value="${stat.STAT_NO}">${stat.STAT_NAME}</option>
						</c:forEach>
					</select>					
				</td>
			
				<td><!-- 工号/姓名 --><spring:message code='public.title.empIdAndName'/></td>
				<td>
					<input name="seach_condition" type="text" id="seach_condition" value="${condition}"/>
				</td>
				<td><!-- 部门 --><spring:message code='public.title.deptName'/></td>
				<td>
					<ait:deptList name="seach_deptNO" limit="pa"  id="ar0106_seachDept"/>
					<ait:deptTreeIcon name="seach_deptNO" limit="pa" id="ar0106_seachDept" selected="${deptNO}"/>
				</td>
			</tr>
			<tr>
					<td>人员类型组 </td>
						<td>
							<input type="hidden" id="ar0106_limit" name="limit" value="ar">
							<input type="hidden" id="ar0106_seach_CPNY" name="seach_CPNY" value="${LoginUser.cpnyId}">
							<ait:SelectEmpTypeCode  id="ar0106_seach_JobTypeGroupNo"  name="seach_JobTypeGroupNo" selected="${JobTypeGroupNo}" limit="pa" type="group"
								onChangeName="ajaxEmpTypeForGroupToList(-1,ar0106_seach_JobTypeGroupNo,ar0106_seach_EmpTypeCodeNo,ar0106_seach_CPNY,ar0106_limit)"/>
						</td>
						<td>人员类型 </td>
						<td>
		 					<ait:SelectEmpTypeCode id="ar0106_seach_EmpTypeCodeNo" name="seach_EmpTypeCodeNo" selected="${EmpTypeCodeNo}" limit="pa"/>
						</td>	
			  <td>在职状态</td>
						<td>
				 	<ait:SelectSyCodeByCpnyID id="seach_EmpOffice" name="seach_EmpOffice" parentNo="15118" selected="${EmpOffice}" cnpyID="${LoginUser.cpnyId}" limit="all"/>
						</td>
			</tr>
		</table>
		<div class="subBar">
						<ul>
                        <li><div class="buttonActive"><div class="buttonContent"><button type="submit"><!-- 查询 --><spring:message code="button.search"/></button></div></div></li>
						 <li>
						</ul>
		</div>
	</div>
	
</div>

<div class="pageContent">
 
	<table class="table" width="100%" layoutH="206" >
		<thead>
			<tr>
				<th width="45"><!-- 工号 --><spring:message code='public.title.empId'/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
				<th width="45"><!-- 姓名 --><spring:message code='public.title.name'/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
				<th width="100"><!-- 部门 --><spring:message code='public.title.deptName'/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
				<th>基本工资标准</th>
				<th>成果工资标准</th>
				<th>基本工资</th>
				<th>成果工资</th>
				<th>职责津贴</th>
				<th>工龄补贴</th>
				<th>住房补贴</th>
				<th>岗位补助</th>
				<th>固定加班费</th>
				<th>评价工资</th>
				<th>值班费</th>
				<th>FSE交通费</th>
				<th>司机加班费</th>
				<th>其他补贴免税</th>
				<th>平时加班</th>
				<th>周末加班</th>
				<th>法定日加班</th>
				<th>上月追溯加班</th>
				<th>其他补贴加</th>
				<th>夜班津贴</th>
				<th>工资调整加</th>
				<th>奖金加</th>
				<th>总发放额</th>
				<th>事假扣款</th>
				<th>病假扣款</th>
				<th>迟到扣款</th>
				<th>早退扣款</th>
				<th>旷工扣款</th>
				<th>迟到早退转旷工扣款</th>
				<th>未打卡扣款</th>
				<th>工资调整减</th>
				<th>年终奖金</th>
				<th>年终奖金税金</th>
				<th>公司养老保险</th>
				<th>公司公积金</th>
				<th>公司失业保险</th>
				<th>公司医疗保险</th>
				<th>公司工伤保险</th>
				<th>公司生育保险</th>
				<th>个人养老保险</th>
				<th>个人公积金</th>
				<th>个人失业保险</th>
				<th>个人大额大病</th>
				<th>个人医疗保险</th>
				<th>保险补扣公司</th>
				<th>保险补扣个人</th>
				<th>个人工会费</th>
				<th>爱心基金个人</th>
				<th>所得税</th>
				<th>应发工资</th>
				<th>实发工资</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${palist}" var="palist" varStatus="j">
			
				<tr>
					
					<td width="45">${palist.EMPID}&nbsp;&nbsp;&nbsp;</td>
					<td width="45">${palist.LOCAL_NAME}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td width="100">${palist.ORG_NAME_LOCAL}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td>${palist.基本工资标准}</td>
					<td>${palist.成果工资标准}</td>
					<td>${palist.基本工资}</td>
					<td>${palist.成果工资}</td>
					<td>${palist.职责津贴}</td>
					<td>${palist.工龄补贴}</td>
					<td>${palist.住房补贴}</td>
					<td>${palist.岗位补助}</td>
					<td>${palist.固定加班费}</td>
					<td>${palist.评价工资}</td>
					<td>${palist.值班费}</td>
					<td>${palist.FSE交通费}</td>
					<td>${palist.司机加班费}</td>
					<td>${palist.其他补贴免税}</td>
					<td>${palist.平时加班}</td>
					<td>${palist.周末加班}</td>
					<td>${palist.法定日加班}</td>
					<td>${palist.上月追溯加班}</td>
					<td>${palist.其他补贴加}</td>
					<td>${palist.夜班津贴}</td>
					<td>${palist.工资调整加}</td>
					<td>${palist.奖金加}</td>
					<td>${palist.总发放额}</td>
					<td>${palist.事假扣款}</td>
					<td>${palist.病假扣款}</td>
					<td>${palist.迟到扣款}</td>
					<td>${palist.早退扣款}</td>
					<td>${palist.旷工扣款}</td>
					<td>${palist.迟到早退转旷工扣款}</td>
					<td>${palist.未打卡扣款}</td>
					<td>${palist.工资调整减}</td>
					<td>${palist.年终奖金}</td>
					<td>${palist.年终奖金税金}</td>
					<td>${palist.公司养老保险}</td>
					<td>${palist.未打卡扣款}</td>
					<td>${palist.公司公积金}</td>
					<td>${palist.公司失业保险}</td>
					<td>${palist.公司医疗保险}</td>
					<td>${palist.公司工伤保险}</td>
					<td>${palist.公司生育保险}</td>
					<td>${palist.个人养老保险}</td>
					<td>${palist.个人公积金}</td>
					<td>${palist.个人失业保险}</td>
					<td>${palist.个人大额大病}</td>
					<td>${palist.个人医疗保险}</td>
					<td>${palist.保险补扣公司}</td>
					<td>${palist.保险补扣个人}</td>
					<td>${palist.个人工会费}</td>
					<td>${palist.爱心基金个人}</td>
					<td>${palist.所得税}</td>
					<td>${palist.应发工资}</td>
					<td>${palist.实发工资}</td>
					
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</form>
	<c:set value="/pa/salary/viewPaConfirmprogress" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>
</c:if>