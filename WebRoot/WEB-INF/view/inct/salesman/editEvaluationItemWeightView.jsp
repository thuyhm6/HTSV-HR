<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
	function validateCallback_editEvaluationItemWeightView(form, callback) {
		var $form = $(form);
		if (!$form.valid()) {
			return false;
		}
		var weight = document.editEvaluationItemWeightForm.WEIGHT.value;
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
	<form method="post" id="editEvaluationItemWeightForm"
		name="editEvaluationItemWeightForm"
		action="/inct/salesman/editEvaluationItemWeight"
		class="pageForm required-validate"
		onsubmit="return validateCallback_editEvaluationItemWeightView(this,dialogAjaxDone);">

		<div class="pageFormContent nowrap" layoutH="60">
			<dl>
				<dt style="width: 100px">
					<spring:message code="inct.salesman.evaluationItemType" />
					<!--评价项目-->
				</dt>
				<dd style="width: 260px">
					${evaluationItem.CATEGORY_NM} <input type="hidden"
						id="SALES_EVAL_TYPE" name="SALES_EVAL_TYPE"
						value="${evaluationItem.CATEGORY}" />
				</dd>
			</dl>
			<dl>
				<dt style="width: 100px">
					<spring:message code="inct.salesman.evaluationType" />
					<!--评价类型-->
				</dt>
				<dd style="width: 260px">
					${evaluationItem.EV_TP_NM} <input type="hidden" id="EV_TP_CD"
						name="EV_TP_CD" value="${evaluationItem.EV_TP_CD}" />
				</dd>
			</dl>
			<dl>
				<dt style="width: 100px">
					<spring:message code="sys.affirm.title.duty" />
					<!--职责 -->
				</dt>
				<dd style="width: 260px">
					${evaluationItem.JOB_POSI_NM} <input type="hidden" id="JOB_POSI_CD"
						name="JOB_POSI_CD" value="${evaluationItem.JOB_POSI_CD}" />
				</dd>
			</dl>
			<dl>
				<dt style="width: 100px">
					<spring:message code="inct.salesman.evaluation.weight" />
					<!--权重-->
				</dt>
				<dd style="width: 260px">
					<input type="text" name="WEIGHT" id="WEIGHT"
						value="${evaluationItem.WEIGHT}" maxlength='18'
						class="required textInput"
						onkeyup="if(isNaN(value))execCommand('undo')"
						onafterpaste="if(isNaN(value))execCommand('undo')" /> 【0-100,2位小数】
				</dd>
			</dl>
			<dl>
				<dt style="width: 100px">
					<spring:message code="sys.postManage.title.ifUsed" />
					<!--是否使用-->
				</dt>
				<dd style="width: 260px">
					<select class="combox" name="USE_YN" id="USE_YN">
						<option value="N"
							<c:if test="${evaluationItem.USE_YN eq 'N'}" >selected</c:if>>
							<spring:message code="sys.arAffirmPost.title.enable" /><!--不启用--></option>
						<option value="Y"
							<c:if test="${evaluationItem.USE_YN eq 'Y'}" >selected</c:if>>
							<spring:message code="sys.arAffirmPost.title.able" /><!--启用--></option>
					</select>
				</dd>
			</dl>
			<dl>
				<dt style="width: 100px">
					<spring:message code="inct.salesman.createBy" />
					<!--注册人-->
				</dt>
				<dd style="width: 260px">${evaluationItem.RGST_USER_NM}</dd>
			</dl>
			<dl>
				<dt style="width: 100px">
					<spring:message code="inct.salesman.createTime" />
					<!--注册时间-->
				</dt>
				<dd style="width: 260px">${evaluationItem.RGST_DTIME}</dd>
			</dl>
		</div>

		<div class="formBar">
			<ul>
				<li><div class="button">
						<div class="buttonContent">
							<button type="submit">
								<spring:message code="button.sys.affirm.save" />
								<!--保存-->
							</button>
						</div>
					</div></li>
				<li><div class="button">
						<div class="buttonContent">
							<button type="button" class="close">
								<spring:message code="public.title.cancle" />
								<!--取消-->
							</button>
						</div>
					</div></li>
			</ul>
		</div>
	</form>

</div>