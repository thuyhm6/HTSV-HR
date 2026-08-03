<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
	function validateCallback_addEvaluationItemWeightView(form, callback) {
		var $form = $(form);
		if (!$form.valid()) {
			return false;
		}
		var weight = document.addEvaluationItemWeightForm.WEIGHT.value;
		if (weight<0 || weight>100) {
			alert("权重(%)必须是0-100之间的数！");
			//alert("<spring:message code='inct.message.info.weightRange'/>");
			return false;
		}
		if (weight.indexOf('.') > -1
				&& weight.length - weight.indexOf('.') - 1 > 2) {
			alert("最多保留2位小数！");
			//alert("<spring:message code='inct.message.info.weightRange'/>");
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
	<form id="addEvaluationItemWeightForm"
		name="addEvaluationItemWeightForm" method="post"
		action="/inct/salesman/addEvaluationItemWeight"
		class="pageForm required-validate"
		onsubmit="return validateCallback_addEvaluationItemWeightView(this,dialogAjaxDone);">
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
					<spring:message code="inct.salesman.evaluation.weight" />
					<!--权重-->
				</dt>
				<dd style="width: 260px">
					<input type="text" name="WEIGHT" id="WEIGHT" maxlength='18'
						class="required textInput"
						onkeyup="if(isNaN(value))execCommand('undo')"
						onafterpaste="if(isNaN(value))execCommand('undo')" /> 【0-100,2位小数】
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
