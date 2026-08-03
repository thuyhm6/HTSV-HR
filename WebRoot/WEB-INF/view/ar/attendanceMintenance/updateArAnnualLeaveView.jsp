<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<script type="text/javascript">
function changevalue_updatearannualleaveview(){
	var cvalue = $("#TOT_VAC_CNT_DAY").val();
	var nvalue = cvalue * 8;
	document.getElementById("TOT_VAC_CNT").value = nvalue;
	document.getElementById("dsnumber").innerHTML = nvalue + '<spring:message code="ar.viewitemparameter.title.xiaoshi"/>';
}
</script>

<div class="pageContent">
	<form method="post" action="/ar/attendanceMintenance/updateArAnnualLeaveInfo" class="pageForm required-validate" onsubmit="return validateCallback(this,navTabAjaxDone);">
		<div class="pageFormContent nowrap">
			<input type="hidden" name="VACATION_NO" value="${annualLeaveInfo.VACATION_NO}"/>
			
			<dl>
				<dt><!-- 工号 --><spring:message code="public.title.empId"/>:</dt>
				<dd>
					<input id="empId" name="empId" value="${annualLeaveInfo.EMPID}" readOnly type="text"/>
				</dd>
			</dl>
			
			<dl>
				<dt><!-- 部门 --><spring:message code="public.title.deptName"/>:</dt>
				<dd>
					<input id="empName" name="empName" value="${annualLeaveInfo.DEPTNAME}" readOnly type="text"/>
				</dd>
			</dl>
			
			<dl>
				<dt><!-- 年假ID --><spring:message code="ar.viewarannualeave.title.annualeaveID"/>:</dt>
				<dd>
					<input id="vacId" name="vacId" value="${annualLeaveInfo.VAC_ID}" readOnly type="text"/>
				</dd>
			</dl>
			
			<dl>
				<dt><!-- 年假天数 --><spring:message code="ar.viewArAnnualStandard.title.vacday"/>:</dt>
				<dd>
				    <input type="text" id="TOT_VAC_CNT_DAY" name="TOT_VAC_CNT_DAY" value="${annualLeaveInfo.VAC_HOUR}" size="30" class="number required" onkeyup="changevalue_updatearannualleaveview();"/>
					<span class="info"></span>
	                <div id="dsnumber">${annualLeaveInfo.TOT_VAC_CNT}小时</div>
	                <input name="TOT_VAC_CNT" type="hidden" id="TOT_VAC_CNT" size="30" class="number" value="${annualLeaveInfo.TOT_VAC_CNT}"/>
				</dd>
			</dl>
			
			<div class="formBar">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent"><button type="submit"><!-- 提交 --><spring:message code="public.title.submit"/></button></div></div></li>
					<li><div class="button"><div class="buttonContent"><button type="button" class="close"><!-- 取消 --><spring:message code="public.title.cancle"/></button></div></div></li>
				</ul>
			</div>
		</div>
	</form>
</div>
