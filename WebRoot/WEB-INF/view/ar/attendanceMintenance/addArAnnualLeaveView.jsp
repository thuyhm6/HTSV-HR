<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<script type="text/javascript">
function changevalue_addarannualleaveview(){
	var cvalue = $("#TOT_VAC_CNT_DAY").val();
	var nvalue = cvalue * 8;
	document.getElementById("TOT_VAC_CNT").value = nvalue;
	document.getElementById("dsnumber").innerHTML = nvalue + '<spring:message code="ar.viewitemparameter.title.xiaoshi"/>';
}

function validateCallback_addarannualleaveview(form, callback) {

	document.getElementById("PERSON_ID").value = document.getElementById("personId").value;

	if(document.getElementById("PERSON_ID").value == ''){
		//请选择员工
		alertMsg.error("<spring:message code='ar.alert.message.viewardetailcaculate.chooseperson'/>");
		return;
	}
		
	var $form = $(form);
	
	if (!$form.valid()) {
		return false;
	}
	
	$.ajax({
		type: form.method || 'POST',
		url:$form.attr("action"),
		data:$form.serializeArray(),
		dataType:"json",
		cache: false,
		success: callback || DWZ.ajaxDone,
		error: DWZ.ajaxError
	});
	
	return false;
}
</script>
<div class="pageContent">
	<form method="post" action="/ar/attendanceMintenance/addArAnnualLeaveInfo" class="pageForm required-validate" onsubmit="return validateCallback_addarannualleaveview(this,navTabAjaxDone);">
		<div class="pageFormContent nowrap" layoutH="56">
			
			<dl>
				<dt><!-- 工号 --><spring:message code="public.title.empId"/>:</dt>
				<dd>
					<input id="PERSON_ID" name="PERSON_ID" type="hidden" value=""/>
					<input id="personId" name="dwz.person.personId" value="" readOnly type="hidden" lookupGroup="person"/>
		 			<input id="empId" name="dwz.person.empId" type="text" readOnly class="required"  readOnly lookupGroup="person"/>
					<a class="btnLook" href="/ar/attendanceMintenance/viewEmpForSupervisorList?pageNum=1" lookupGroup="person"><!-- 查找带回 --><spring:message code="ar.alert.message.viewattendencekeeper.chazhaodaihui"/></a>
				</dd>
			</dl>
			
			<dl>
				<dt><!-- 姓名 --><spring:message code="public.title.name"/>:</dt>
				<dd>
					<input id="empName" name="dwz.person.empName" value="" readOnly type="text" lookupGroup="person"/>
				</dd>
			</dl>
			
			<dl>
				<dt><!-- 部门 --><spring:message code="public.title.deptName"/>:</dt>
				<dd>
					<input id="deptName" name="dwz.person.deptName" value="" readOnly type="text" lookupGroup="person"/>
				</dd>
			</dl>
			
			<dl>
				<dt><!-- 年假ID --><spring:message code="ar.viewarannualeave.title.annualeaveID"/>:</dt>
				<dd>
					<ait:date yearName="VAC_ID" />
				</dd>
			</dl>
			
			<dl> 
				<dt><!-- 年假天数 --><spring:message code="ar.viewArAnnualStandard.title.vacday"/>:</dt>
				<dd>
					<input type="text" id="TOT_VAC_CNT_DAY" name="TOT_VAC_CNT_DAY" size="30" class="number required" onkeyup="changevalue_addarannualleaveview();"/>
					<span class="info"></span>
	                <div id="dsnumber"></div>
	                <input name="TOT_VAC_CNT" type="hidden" id="TOT_VAC_CNT" size="30" class="number"/>
				</dd>
			</dl>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit"><!-- 提交 --><spring:message code="public.title.submit"/></button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close"><!-- 取消 --><spring:message code="public.title.cancle"/></button></div></div></li>
			</ul>
		</div>
	</form>
</div>
