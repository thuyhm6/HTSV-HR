<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript">
	function backApplication(form){
		var $form=null;
		if($('#'+form).length>0)
			$form=$('#'+form);
		else
	 		$form = $(form);
		
		if (!$form.valid()) {
			return false;
		}
	
		var checked=false;
		var ids= document.getElementsByName("c1");
		for(var i=0;i<ids.length;i++){
			if(ids[i].checked){
				checked=true;
			}
		}
		if(!checked){
			alertMsg.error('<spring:message code="alert.message.ess.affirmApply.chooseApplyRecordFirstForBatch"/>'); 
			return false;
		}
		
		$form.attr("action","/ess/wageApplication/backPbOtApplication");
		$.ajax({
			type: form.method || 'POST',
			url:$form.attr("action"),
			data:$form.serializeArray(),
			dataType:"json",
			cache: false,
			success: function(data){ //请求成功后处理函数。
				if(data.statusCode=="200"){
					//navTabSearch("backSubmit1");
					alertMsg.correct(data.message);
				}else{
					if(data.result=="2"){
						alertMsg.info(data.message);
					}else{
						alertMsg.error(data.message);
					}
				}   
	   	 	}  ,
			error: DWZ.ajaxError
		});
		return false;
	}
	$(document).ready(function(){
		if($("#ess06302_seach_JobTypeGroupNo").val() != ''){
			var EMP_TYPE = $("#ess06302_seach_EmpTypeCodeNo").val();
			ajaxEmpTypeForGroupToList(EMP_TYPE,"ess06302_seach_JobTypeGroupNo","ess06302_seach_EmpTypeCodeNo",
					"ess06302_seach_CPNY","ess06302_limit");
			//要传进的参数分别为 -1，人员类型组select 对象，人员类型select name，法人选项id，要查询的是否为group，权限super/hr/ar/pa
		}
	});
</script>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/ess/wageApplication/viewPbWageApplicationList" method="post" rel="pagerForm">
	<div class="searchBar" >
		<table class="searchContent">
			<tr>
				<td>
					社号/姓名
				</td>
				<td>
				    <input type="text" id="seach_EMPID" name="seach_EMPID" value="${EMPID}"/>
				</td>
				<td>开始时间</td>
				<td> <input type="text" name="seach_STARTTIME" class="date required" readonly="true" value="${STARTTIME}"/>
				    <a class="inputDateButton" href="javascript:;"><spring:message code="public.title.choose"/><!-- 选择 --></a>
				</td>
				<td>结束时间</td>
				<td><input type="text" name="seach_ENDTIME" class="date required" readonly="true" value="${ENDTIME}"/>
					<a class="inputDateButton"><spring:message code="public.title.choose" /><!-- 选择 --></a>
				</td>
				  		<td><!-- 在职区分： --> <spring:message
			code="hr.viewPersonalInfo.title.EMP_OFFICE_NAME" /> 
		</td>
						<td>
		 					<ait:SelectSyCodeByCpnyID id="seach_EmpOffice" name="seach_EmpOffice" parentNo="15118" selected="${EmpOffice}" cnpyID="${LoginUser.cpnyId}" limit="all"/>
						</td>
			</tr>
			<tr>
			<td>人员类型组 </td>
						<td>
						<input type="hidden" id="ess06302_limit" name="limit" value="pa">
						<input type="hidden" id="ess06302_seach_CPNY" name="seach_CPNYFYSQ" value="${defaultCpny}">
						<ait:SelectEmpTypeCode id="ess06302_seach_JobTypeGroupNo"name="seach_JobTypeGroupNo" selected="${JobTypeGroupNo}" limit="pa" type="group"
						onChangeName="ajaxEmpTypeForGroupToList(-1,ess06302_seach_JobTypeGroupNo,ess06302_seach_EmpTypeCodeNo,ess06302_seach_CPNY,ess06302_limit)"/>
						</td>
						<td>人员类型</td>
						<td>
		 					<ait:SelectEmpTypeCode id="ess06302_seach_EmpTypeCodeNo" name="seach_EmpTypeCodeNo" selected="${EmpTypeCodeNo}" limit="pa"/>
						</td>
				</tr>
		</table>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">
				<spring:message code="public.title.search"/><!-- 检索 --></button></div></div></li>
			</ul>
		</div>
	</div>
	</form>
</div>
<div class="pageContent">
<form name="backSubmit" id="backSubmit" method="post" action="/ess/wageApplication/backPbOtApplication"
	onsubmit="return backApplication(this, navTabAjaxDone);"> 
	<div class="formBar">
	<ul class="toolBar">
		<li><a class="buttonActive"
			onClick="backApplication('backSubmit');"><span>撤销</span></a></li>
	</ul>
</div>
	<table class="table" width="100%" layoutH="206">
		<thead>
			<tr>
				<th width="30" align="center"><input type="checkbox" class="checkboxCtrl" group="c1" ></th>
				<th width="100">社号/姓名</th>
				<th width="100">申请内容</th>
				<th width="100">费用类型</th>
				<th width="120">申请费用发放期间</th>
				<th width="100">金额</th>
				<th width="100">备注</th>
				<th width="100"><spring:message code="ess.viewApply.title.applyDate"/><!--申请日期--></th>
				<th width="100"><spring:message code="ess.trans.title.affirmStatus"/><!--决裁状态--></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${wageAppList}" var="wage" varStatus="i">			
				<tr target="waid" rel="${wage.ID}" align="center">
					<td><c:if test="${wage.CREATED_BY eq PERSON_ID}"><input type="checkbox" name="c1" id="c1" value="${wage.ID}"></c:if></td>
					<td>${wage.EMPNAME}[${wage.COSTEMP}]</td>
					<td>${wage.TITLE}</td>
					<td>${wage.TYPENAME}</td>
					<td>${wage.START_DATE} ~ ${wage.END_DATE}</td>
					<td>${wage.MONEY}</td>
					<td>${wage.DEMO}</td>
					<td>${wage.CREATE_DATE}</td>
					<td>
						<c:if test="${wage.FLAG eq '0'}">未决裁</c:if><c:if test="${wage.FLAG eq '3'}">决裁中</c:if>
						<c:if test="${wage.FLAG eq '1'}">已决裁</c:if><c:if test="${wage.FLAG eq '2'}">已否决</c:if>
					</td>
				</tr>			
			</c:forEach>			
		</tbody>
	</table>
</form>		
	<c:set value="/ess/wageApplication/viewPbWageApplicationList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>