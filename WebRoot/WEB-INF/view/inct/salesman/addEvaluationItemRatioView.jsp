<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
	function checkNumPot2(value) {
		if (value.indexOf('.') > -1
				&& value.length - value.indexOf('.') - 1 > 2)
			return false;
		else
			return true;
	}
	function validateCallback_addEvaluationItemRatioView(form, callback) {
		var $form = $(form);
		if (!$form.valid()) {
			return false;
		}
		var Lstart_value = document.addEvaluationItemRatioForm.LEFT_START_VALUE.value;
		var Lend_value = document.addEvaluationItemRatioForm.LEFT_END_VALUE.value;
		var Tstart_value = document.addEvaluationItemRatioForm.TOP_START_VALUE.value;
		var Tend_value = document.addEvaluationItemRatioForm.TOP_END_VALUE.value;
		var ratio = document.addEvaluationItemRatioForm.RATIO.value;
		if (eval(Lstart_value) >= eval(Lend_value)) {
			alert("左侧区间起始值不能大于等于结束值！");
			return false;
		}
		if (eval(Tstart_value) >= eval(Tend_value)) {
			alert("顶部区间起始值不能大于等于结束值！");
			return false;
		}
		if (!checkNumPot2(ratio)) {
			alert("最多保留2位小数！");
			$("#RATIO").focus();
			return false;
		}
		if (!checkNumPot2(Lstart_value)) {
			alert("最多保留2位小数！");
			$("#LEFT_START_VALUE").focus();
			return false;
		}
		if (!checkNumPot2(Lend_value)) {
			alert("最多保留2位小数！");
			$("#LEFT_END_VALUE").focus();
			return false;
		}
		if (!checkNumPot2(Tstart_value)) {
			alert("最多保留2位小数！");
			$("#TOP_START_VALUE").focus();
			return false;
		}
		if (!checkNumPot2(Tend_value)) {
			alert("最多保留2位小数！");
			$("#TOP_END_VALUE").focus();
			return false;
		}
		$.ajax({
			type : form.method || 'POST',
			url : $form.attr("action"),
			data : $form.serializeArray(),
			dataType : "json",
			cache : false,
			success : callback || DWZ.ajaxDone,
			error : DWZ.ajaxError
		});
		return false;
	}
</script>
<div class="pageContent">
	<form id="addEvaluationItemRatioForm" name="addEvaluationItemRatioForm"
		method="post" action="/inct/salesman/addEvaluationItemRatio"
		class="pageForm required-validate"
		onsubmit="return validateCallback_addEvaluationItemRatioView(this,dialogAjaxDone);">
		<div class="pageFormContent nowrap">

			<dl>
				<dt style="width: 100px">
					<spring:message code="inct.salesman.evaluationItemType" />
						<!--评价项目-->
				</dt>
				<dd style="width: 260px">
					<ait:ComboSyCodeDescByCpnyID id="SALES_EVAL_TYPE"
						name="SALES_EVAL_TYPE" parentNo="210878"
						selected="${SALES_EVAL_TYPE}" cnpyID="${interCpnyID}" />
				</dd>
			</dl>

			<dl>
				<dt style="width: 100px">
					<spring:message code="inct.salesman.evaluationType" />
						<!--评价类型-->
				</dt>
				<dd style="width: 260px">
					<ait:ComboSyCodeDescByCpnyID id="seach_EV_TP_CD"
							name="seach_EV_TP_CD" parentNo="14895" selected="${EV_TP_CD}"
							cnpyID="${interCpnyID}"/>
				</dd>
			</dl>
			<dl>
				<dt style="width: 100px">
					<spring:message code="sys.affirm.title.duty" />
					<!--职责-->
				</dt>
				<dd style="width: 260px">
					<ait:ComboJobPositionByCpnyIDTag id="JOB_POSI_CD"
						name="JOB_POSI_CD" parentNo="215918" selected="${JOB_POSI_CD}"
						cnpyID="${interCpnyID}" />
				</dd>
			</dl>

			<dl>
				<dt style="width: 100px">
					<spring:message code="inct.salesman.eval.itemRatio.leftStart" />
					<!--左侧区间开始值-->
				</dt>
				<dd style="width: 260px">
					<input type="text" name="LEFT_START_VALUE" id="LEFT_START_VALUE"
						maxlength='18' class="required textInput"
						onkeyup="if(isNaN(value))execCommand('undo')"
						onafterpaste="if(isNaN(value))execCommand('undo')" />
				</dd>
			</dl>
			<dl>
				<dt style="width: 100px">
					<spring:message code="inct.salesman.eval.itemRatio.leftEnd" />
					<!--左侧区间结束值-->
				</dt>
				<dd style="width: 260px">
					<input type="text" name="LEFT_END_VALUE" id="LEFT_END_VALUE"
						maxlength='18' class="required textInput"
						onkeyup="if(isNaN(value))execCommand('undo')"
						onafterpaste="if(isNaN(value))execCommand('undo')" />
				</dd>
			</dl>
			<dl>
				<dt style="width: 100px">
					<spring:message code="inct.salesman.eval.itemRatio.topStart" />
					<!--顶部区间开始值-->
				</dt>
				<dd style="width: 260px">
					<input type="text" name="TOP_START_VALUE" id="TOP_START_VALUE"
						maxlength='18' class="required textInput"
						onkeyup="if(isNaN(value))execCommand('undo')"
						onafterpaste="if(isNaN(value))execCommand('undo')" />
				</dd>
			</dl>
			<dl>
				<dt style="width: 100px">
					<spring:message code="inct.salesman.eval.itemRatio.topEnd" />
					<!--顶部区间结束值-->
				</dt>
				<dd style="width: 260px">
					<input type="text" name="TOP_END_VALUE" id="TOP_END_VALUE"
						maxlength='18' class="required textInput"
						onkeyup="if(isNaN(value))execCommand('undo')"
						onafterpaste="if(isNaN(value))execCommand('undo')" />
				</dd>
			</dl>
			<dl>
				<dt style="width: 100px">
					<spring:message code="inct.salesman.eval.itemRatio.Ratio" />
					<!--系数-->
				</dt>
				<dd style="width: 260px">
					<input type="text" name="RATIO" id="RATIO" maxlength='18'
						class="required textInput"
						onkeyup="if(isNaN(value))execCommand('undo')"
						onafterpaste="if(isNaN(value))execCommand('undo')" />
				</dd>
			</dl>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="button">
						<div class="buttonContent">
							<button type="submit">
								<!-- 提交 -->
								<spring:message code="public.title.submit" />
							</button>
						</div>
					</div></li>
				<li><div class="button">
						<div class="buttonContent">
							<button type="button" class="close">
								<!-- 取消 -->
								<spring:message code="public.title.cancle" />
							</button>
						</div>
					</div></li>
			</ul>
		</div>
	</form>
</div>
