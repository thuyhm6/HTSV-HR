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
	
	$form.attr("action","/ess/wageApplication/backSubmitOtApplication");
	$.ajax({
		type: form.method || 'POST',
		url:$form.attr("action"),
		data:$form.serializeArray(),
		dataType:"json",
		cache: false,
		success: function(data){ //请求成功后处理函数。
			if(data.statusCode=="200"){
				navTabSearch("backSubmit1");
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

function backApplication(f){
	alertMsg.confirm("确定要撤销么?",{okCall:function(){
		$.ajax({
			type: 'POST',
			url:"/ess/wageApplication/backSubmitOtApplication",
			data:{"IDS":f},
			dataType:"json",
			cache: false,
			success: function(data){ //请求成功后处理函数。
				if(data.statusCode=="200"){
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
	}});
}

function deleteApplication(form){
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
	alertMsg.confirm("确定要删除么?",{okCall:function(){
		$form.attr("action","/ess/wageApplication/deleteOtApplication");
		$.ajax({
			type: form.method || 'POST',
			url:$form.attr("action"),
			data:$form.serializeArray(),
			dataType:"json",
			cache: false,
			success: function(data){ //请求成功后处理函数。
				if(data.statusCode=="200"){
					navTabSearch("backSubmit1");
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
	}});
	return false;
}

function checkApplyApplicationState(){
	$.ajax({
		type: 'POST',
		url:"/ess/wageApplication/checkApplyApplicationState",
		data:{"IDS":''},
		dataType:"json",
		cache: false,
		success: function(data){ //请求成功后处理函数。
			if(data.statusCode=="200"){
				$('#checkApplyState').click();
			}else{
				alertMsg.error(data.message);
			}   
   	 	}  ,
		error: DWZ.ajaxError
	});
}
$(document).ready(function(){
	if($("#ess06301_seach_JobTypeGroupNo").val() != ''){
		var EMP_TYPE = $("#ess06301_seach_EmpTypeCodeNo").val();
		ajaxEmpTypeForGroupToList(EMP_TYPE,"ess06301_seach_JobTypeGroupNo","ess06301_seach_EmpTypeCodeNo",
				"ess06301_seach_CPNY","ess06301_limit");
		//要传进的参数分别为 -1，人员类型组select 对象，人员类型select name，法人选项id，要查询的是否为group，权限super/hr/ar/pa
	}
});
</script>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/ess/wageApplication/viewOtWageApplicationList" method="post" rel="pagerForm">
	<div class="searchBar" >
		<table class="searchContent">
			<tr>
				<td>开始时间</td>
				<td> <input type="text" name="seach_STARTTIME" class="date" readonly="true" value="${STARTTIME}"/>
				    <a class="inputDateButton" href="javascript:;"><spring:message code="public.title.choose"/><!-- 选择 --></a>
				</td>
				<td>结束时间</td>
				<td><input type="text" name="seach_ENDTIME" class="date" readonly="true" value="${ENDTIME}"/>
					<a class="inputDateButton"><spring:message code="public.title.choose" /><!-- 选择 --></a>
				</td>
				<td>
					<spring:message code="ess.viewApply.title.affirmStatus"/><!--按决裁状态-->
				</td>
				<td>
					 <select name="seach_STATE" value="${STATE}">
						 <option value="">全部</option>
						 <option value="0" <c:if test="${STATE eq '0'}">selected</c:if>>未决裁</option>
						 <option value="3" <c:if test="${STATE eq '3'}">selected</c:if>>决裁中</option>
						 <option value="1" <c:if test="${STATE eq '1'}">selected</c:if>>已决裁</option>
						 <option value="2" <c:if test="${STATE eq '2'}">selected</c:if>>已否决</option>
					 </select>
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
						<input type="hidden" id="ess06301_limit" name="limit" value="pa">
						<input type="hidden" id="ess06301_seach_CPNY" name="seach_CPNY" value="${defaultCpny}">
						<ait:SelectEmpTypeCode id="ess06301_seach_JobTypeGroupNo" name="seach_JobTypeGroupNo" selected="${JobTypeGroupNo}" limit="pa" type="group"
						onChangeName="ajaxEmpTypeForGroupToList(-1,ess06301_seach_JobTypeGroupNo,ess06301_seach_EmpTypeCodeNo,ess06301_seach_CPNY,ess06301_limit)"/>
						</td>
						<td>人员类型</td>
						<td>
		 					<ait:SelectEmpTypeCode id="ess06301_seach_EmpTypeCodeNo" name="seach_EmpTypeCodeNo" selected="${EmpTypeCodeNo}" limit="pa"/>
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
<form name="backSubmit" id="backSubmit" method="post" action="/ess/wageApplication/backSubmitOtApplication"
	onsubmit="return backApplication(this, navTabAjaxDone);"> 
	<div class="formBar">
		<ul class="toolBar">
			<li><a class="buttonActive" onClick="deleteApplication('backSubmit');"><span>删除</span></a></li>
			<%--<li><a class="buttonActive"	onClick="backApplication('backSubmit');"><span>撤销</span></a></li>--%>
			<li><a class="buttonActive"	onClick="checkApplyApplicationState();"><span>申请</span></a></li>
		</ul>
		<a href="/ess/wageApplication/addNewWageApplicationView" target="navTab" id="checkApplyState" mask="true" title="费用申请"></a>
	</div>
	<table class="table" width="100%" layoutH="206">
		<thead>
			<tr>
				<th width="50"><input type="checkbox" class="checkboxCtrl" group="c1" ></th>
				<th width="80"><spring:message code="ess.viewApply.title.applyName"/><!-- 申请者 --></th>
				<th width="90"><spring:message code="ess.viewApply.title.applyContent"/><!--申请内容--></th>
				<th width="90">附件查看</th>
				<th width="100"><spring:message code="ess.viewApply.title.applyDate"/><!--申请日期--></th>
				<th width="100"><spring:message code="ar.viewArAdjustRest.title.chakanxiangxi"/><!--详细查看--></th>
				<th width="100"><spring:message code="ess.trans.title.affirmStatus"/><!--决裁状态--></th>
				<th width="100">操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${wageAppList}" var="wage" varStatus="i">			
				<tr target="waid" rel="${wage.ID}" align="center">
					<td><c:if test="${wage.FLAG eq '0' && wage.CREATED_BY eq PERSON_ID}"><input type="checkbox" name="c1" id="c1" value="${wage.ID}"></c:if>${wage.ID}</td>
					<td>${wage.EMPNAME}[${wage.EMPID }]</td>
					<td>${wage.TITLE}</td>
					<td><a href="/ess/wageApplication/showWageAppliFileList?APPLY_NO=${wage.ID}&PERSON_ID=${wage.CREATED_BY}" target="dialog" mask="true" width="730" height="500">附件查看</a></td>
					<td>${wage.CREATEDATE}</td>
					<td><a href="/ess/wageApplication/showWageApplicationList?APPLY_NO=${wage.ID}&PERSON_ID=${wage.CREATED_BY}&APPLY_TYPE=217886" target="dialog" mask="true" width="730" height="500">
					<spring:message code="ar.viewArAdjustRest.title.chakanxiangxi"/></a></td>
					<td>
						<c:if test="${wage.FLAG eq '0'}">未决裁</c:if><c:if test="${wage.FLAG eq '3'}">决裁中</c:if>
						<c:if test="${wage.FLAG eq '1'}">已决裁</c:if><c:if test="${wage.FLAG eq '2'}">已否决</c:if>
					</td>
					<td><c:if test="${wage.FLAG eq '1' && wage.CREATED_BY eq PERSON_ID}"><a href="javascript:backApplication('${wage.ID}');">撤销</a></c:if></td>
				</tr>			
			</c:forEach>			
		</tbody>
	</table>
</form>		
	<c:set value="/ess/wageApplication/viewOtWageApplicationList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>