<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>



<script type="text/javascript">
<!--
function validateCallbackUpdateHealthInfo(form, callback) {


	var $form = $("#updateHealthInfo");
	
	if (!$form.valid()) {
		return false;
	}
   
	var checked=false;
	var ids= document.getElementsByName("HNO");
	for(var i=0;i<ids.length;i++){
		if(ids[i].checked){
			checked=true;
		}
	}
	if(!checked){
		//请选择信息再进行保存操作
		alertMsg.error('<spring:message code="hr.alert.message.viewPersonalInfo.checkBoxForChecked"/>');
		return false;
	}
	
	
	var healthListnum= document.getElementById("healthListSize").value;
	
	for (i=0;i<healthListnum;i++){
		if(document.getElementById("HNO"+i).checked){
			var sd=document.getElementById("PHYSICAL_DATE_"+i).value;
			var ed=document.getElementById("EFFECTIVE_DATE_"+i).value;
			
			var date1 = sd.replaceAll("-","");
			var date2 = ed.replaceAll("-","");
			
			if (date1 - date2 > 0) {
				alertMsg.error('<spring:message code="hr.alert.message.viewHealth.checkPhysicalDateAndEffectiveDate"/>');//检查日期不能晚于有效期
				document.getElementById("PHYSICAL_DATE_"+i).focus();
				return false;
			}
		}
	}
	
	
	//确定要提交吗？
	if (confirm ('<spring:message code="hr.viewEvaluate.title.COMMIT_CONFIRM"/>')){	
		$.ajax({
			type: form.method || 'POST',
			url:$form.attr("action"),
			data:$form.serializeArray(),
			dataType:"json",
			cache: false,
			success: callback || DWZ.ajaxDone,
			error: DWZ.ajaxError
		});
	}
	return false;
}
//-->
</script>

<div class="pageContent">
	<form id="updateHealthInfo" method="post" action="/hrm/empinfo/editHealthInfo" class="pageForm required-validate" onsubmit="return validateCallbackUpdateHealthInfo(this, dialogAjaxDone);">

	<div class="panelBar">
		<ul class="toolBar">
			<li id="addLi">
				<span>&nbsp;</span>
			</li>
		</ul>
	</div>
	
	<input type="hidden" id="healthListSize" name="educationListSize" value="${fn:length(healthList)}" />
	<table class="table" width="102.2%" layoutH="150">
		<thead>
			<tr>
				<th width="10"><input type="hidden" name="PERSON_ID" class="textInput" value="${PERSON_ID }"/></th>
				<th width="80">
					<spring:message code="hr.viewHealth.title.PHYSICAL_DATE"/>
					<!--检查日期-->
				</th>
				<th width="80">
					<spring:message code="hr.viewHealth.title.PHYSICAL_TYPE_NAME"/>
					<!--检查类型-->
				</th>
				<th width="80">
					<spring:message code="hr.viewHealth.title.INDUSTRY_DISTINGUISH_NAME"/>
					<!--区分-->
				</th>
				<th width="80">
					<spring:message code="hr.viewHealth.title.EFFECTIVE_DATE"/>
					<!--有效期-->
				</th>
				<th width="80">
					<spring:message code="hr.viewHealth.title.CHECK_YN_NAME"/>
					<!--检查与否-->
				</th>
				<th width="80">
					<spring:message code="hr.viewHealth.title.GENRAL_HEALTH_NAME"/>
					<!--健康情况-->
				</th>
				<th width="80">
					<spring:message code="hr.viewHealth.title.BLOOD_TYPE_NAME"/>
					<!--血型-->
				</th>
				<th width="80">
					<spring:message code="hr.viewHealth.title.HEALTH_CERTIFICATE_YN_NAME"/>
					<!--是否提交健康证-->
				</th>
				<th width="80">
					<spring:message code="hr.viewHealth.title.SPECIAL_MATTERS"/>
					<!--特殊事项-->
				</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${healthList}" var="item" varStatus="i">
				<tr target="healthNo" rel="${item.HEALTH_NO}">
					
					<td><input type="checkbox" id="HNO${i.index}" name="HNO" value="${item.HEALTH_NO}" /></td>
					
					<td>
						<input type="text" id="PHYSICAL_DATE_${i.index}" name="PHYSICAL_DATE_${item.HEALTH_NO}" class="date required" value="${item.PHYSICAL_DATE}" readonly="true" format="yyyy-MM-dd" yearstart="-50" yearend="5" onClick="setdate(this);"/>
					</td>
					
					<td><ait:SelectSyCodeByCpnyID name="PHYSICAL_TYPE_CODE_${item.HEALTH_NO}" parentNo="4570" selected="${item.PHYSICAL_TYPE_CODE}" cnpyID="${defaultCpny}" limit="all"/></td>
					
					<td><ait:SelectSyCodeByCpnyID name="INDUSTRY_DISTINGUISH_CODE_${item.HEALTH_NO}" parentNo="14900" selected="${item.INDUSTRY_DISTINGUISH_CODE}" cnpyID="${defaultCpny}" limit="all"/></td>
					
					<td>
						<input type="text" id="EFFECTIVE_DATE_${i.index}" name="EFFECTIVE_DATE_${item.HEALTH_NO}" class="date" value="${item.EFFECTIVE_DATE}" readonly="true" format="yyyy-MM-dd" yearstart="-50" yearend="5" onClick="setdate(this);"/>
					</td>
					
					
					<td>
						<ait:SelectSyCodeByCpnyID name="CHECK_YN_${item.HEALTH_NO}" selected="${item.CHECK_YN}" parentNo="14892" cnpyID="${defaultCpny}" limit="all"/>
					</td>
					
					<td><ait:SelectSyCodeByCpnyID name="GENERAL_HEALTH_${item.HEALTH_NO}" parentNo="4572" selected="${item.GENERAL_HEALTH}" cnpyID="${defaultCpny}" limit="all"/></td>
					
					
					<td><ait:SelectSyCodeByCpnyID name="BLOOD_TYPE_CODE_${item.HEALTH_NO}" parentNo="4573" selected="${item.BLOOD_TYPE_CODE}" cnpyID="${defaultCpny}" limit="all"/></td>
					
					<td>
						<ait:SelectSyCodeByCpnyID name="HEALTH_CERTIFICATE_YN_${item.HEALTH_NO}" selected="${item.HEALTH_CERTIFICATE_YN}" parentNo="14892" cnpyID="${defaultCpny}" limit="all"/>
					</td>
					
					<td><input name="REMARK_${item.HEALTH_NO}" type="text" maxlength="30" size="15"	value="${item.REMARK}" /></td>
				</tr>
			</c:forEach>
			
		</tbody>
	</table>
	
	
	
	
	<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit"><spring:message code="public.title.submit"/><!-- 保存 --></button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close"><spring:message code="public.title.cancle"/><!-- 取消 --></button></div></div>
				</li>
			</ul>
	</div>
	</form>
</div>