<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
var key13FromWhere ;
function addPaInputItemDataInfo(form, callback) {
	if(key13FromWhere == 'pop'){
		key13FromWhere = '' ;
		return false;
	}
	var $form = $("#addPaInputItemDataInfoForm",$.pdialog.getCurrent());
	if (!$form.valid()) {
		return false;
	}
	var startMonth = document.getElementById("START_MONTH").value;
	if (!/^(?:19[7-9]\d|2\d{3,3})(?:0[1-9]|1[0-2])$/.test(startMonth)){ 
         alertMsg.warn('<spring:message code="alert.message.pa.salary.startMonthIsNotCorrect"/>');
         return false;
    }
	var endMonth = document.getElementById("END_MONTH").value;
	if(endMonth!=null && endMonth !=""){
		if (!/^(?:19[7-9]\d|2\d{3,3})(?:0[1-9]|1[0-2])$/.test(endMonth)){
	        alertMsg.error('<spring:message code="alert.message.pa.insurance.endMonthIsNotCorrect"/>');
	        return false;
	    }
		//如果结束月份比开始月早， 请重新填写结束月！
	    if(endMonth < startMonth){
	    	alertMsg.error('<spring:message code="alert.message.pa.insurance.endMonthIsBeforeStartDate"/>');
	        return false;
	    }
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
	$.pdialog.closeCurrent();
    alertMsg.info('操作成功!');
	return false;
}

$(document).ready(function(){

	$("input[id='END_MONTH'][name='END_MONTH'][inputType='date']").removeClass("required");
});

function searchPopForFixParamData(flag){
	var name=encodeURI(encodeURI($("#seach_KEY",$.pdialog.getCurrent()).val()));
	$("#searchPopForPaParamData",$.pdialog.getCurrent()).attr('href','/ess/tempEmp/viewEmpForFixList?pageNum=1&numPerPage=10&limit=pa&seach_KEY='+name);
	if(flag == 'onkeyup'){
		key13FromWhere = 'pop';
	}
	$("#searchPopForPaParamData",$.pdialog.getCurrent()).click();
}
</script>
<div class="pageContent">
	<form id="addPaInputItemDataInfoForm" name="addPaInputItemDataInfoForm" method="post"
		action="/pa/salary/addPaInputItemDataInfo?seach_PARAM_NO=${PARAM_NO}&seach_CPNY_ID=${CPNY_ID}" class="pageForm required-validate"
		onsubmit="return addPaInputItemDataInfo(this, submitFormViewPaInput_pa0212);">
		<div class="pageFormContent nowrap" layoutH="77">
				<dl>
					<dt><!--工号-->
						<spring:message code="public.title.empId"/>
					</dt>
					<dd>
						<input id="dwz.person.personid" name="personId" value="" type="hidden" lookupGroup="person" />
						<input
						type="text" name="seach_KEY" id="seach_KEY" value="${KEY}"  onkeydown="javascript:if(event.keyCode == 13)searchPopForPaParamData('onkeyup');"/>
						<a class="btnLook" onclick="searchPopForPaParamData('onclick')" href="#"/>
						<a id="searchPopForPaParamData" href="#" lookupGroup="person">
						</a>
					</dd>
				</dl>
				<dl>
					<dt>员工信息</dt>
					<dd>
						<input type="text" readonly="readonly" id="dwz.person.empInfo" name="empInfo" lookupGroup="person" size="60" class="required"/>
					</dd>
				</dl>
				<dl>
					<dt><!--数值-->
						<spring:message code="pa.insurance.title.dataValue"/>:
					</dt>
					<dd>
						<input type="text" name="RETURN_VALUE" class="textInput required"  min="-99999999999999" style="text-align:right;">
					</dd>
				</dl>
				<dl>
					<dt><!--开始月-->
						<spring:message code="pa.insurance.title.startMonth"/>:
					</dt>
					<dd>
						<ait:inputText name="START_MONTH" id="START_MONTH" inputType="date"/>
						<font color="red"><!--例:2012年2月---201202(下同)-->
							<spring:message code="pa.insurance.title.exampleMonth"/>
						</font>
					</dd>
				</dl>
				<dl>
					<dt><!--结束月-->
						<spring:message code="pa.insurance.title.endMonth"/>:
					</dt>
					<dd>
						<ait:inputText name="END_MONTH" id="END_MONTH" inputType="date"/>
						<font color="red"><!--例:2012年2月---201202(下同)-->
							<spring:message code="pa.insurance.title.exampleMonth"/>
						</font>
					</dd>
				</dl>
				<dl>
					<dt><!--备注-->
						<spring:message code="hr.viewPromote.title.REMARK"/>
					</dt>
					<dd>
						<input name="REMARK" type="text" maxlength="200"/>
					</dd>
				</dl>
		</div>
		<div class="formBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent"><!-- 提交 -->
							<button type="submit" >
								<spring:message code="public.title.submit"/>
							</button>
						</div>
					</div>
				</li>
				<li>
					<div class="button">
						<div class="buttonContent"><!--取消-->
							<button type="button" class="close">
								<spring:message code="public.title.cancle"/>
							</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</form>
</div>