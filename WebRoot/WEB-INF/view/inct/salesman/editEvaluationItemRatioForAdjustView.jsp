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
	function validateCallback_editEvaluationItemRatioForAdjustView(form, callback) {
		var $form = $(form);
		if (!$form.valid()) {
			return false;
		}
		var ratio = document.editEvaluationItemRatioForAdjustForm.RATIO.value;
		
		if (!checkNumPot2(ratio)) {
			alert("最多保留2位小数！");
			$("#RATIO").focus();
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
	<form id="editEvaluationItemRatioForAdjustForm"
		name="editEvaluationItemRatioForAdjustForm" method="post"
		action="/inct/salesman/editEvaluationItemRatioForAdjust"
		class="pageForm required-validate"
		onsubmit="return validateCallback_editEvaluationItemRatioForAdjustView(this,dialogAjaxDone);">
		<input type="hidden" id="JOB_POSI_CD" name="JOB_POSI_CD" value="${evaluationItem.JOB_POSI_CD}" /> 
		<input type="hidden" id="EV_TP_CD" name="EV_TP_CD" value="${evaluationItem.EV_TP_CD}" /> 
		<input type="hidden" id="PAY_AREA_CD" name="PAY_AREA_CD" value="${evaluationItem.PAY_AREA_CD}" /> 
		<div class="pageFormContent nowrap" layoutH="60">
			<dl>
				<dt style="width: 100px">
					年
				</dt>
				<dd style="width: 260px">
					${evaluationItem.YYYY} 
					<input type="hidden" id="YEAR" name="YEAR" value="${evaluationItem.YYYY}" /> 
				</dd>
			</dl>

			<dl>
				<dt style="width: 100px">
					季度
				</dt>
				<dd style="width: 260px">
					${evaluationItem.QUARTER} 
					<input type="hidden" id="QUARTER" name="QUARTER" value="${evaluationItem.QUARTER}" />
				</dd>
			</dl>
			<dl>
				<dt style="width: 100px">
					社号
				</dt>
				<dd style="width: 260px">
					${evaluationItem.EMPNO} 
					<input type="hidden" id="EMPNO" name="EMPNO" value="${evaluationItem.EMPNO}" />
				</dd>
			</dl>
			<dl>
				<dt style="width: 100px">
					姓名
				</dt>
				<dd style="width: 260px">
				 	${evaluationItem.EMPNM} 
					<input type="hidden" name="EMPNM" id="EMPNM"value="${evaluationItem.EMPNM}"/>
				</dd>
			</dl>
			<dl>
				<dt style="width: 100px">
					评价项目
				</dt>
				<dd style="width: 260px">
					${evaluationItem.CATEGORY_NM} 
					<input type="hidden" name="CATEGORY" id="CATEGORY"value="${evaluationItem.CATEGORY}"/>
				</dd>
			</dl>
			<dl>
				<dt style="width: 100px">
					系数
				</dt>
				<dd style="width: 260px">
					<input type="text" name="RATIO" id="RATIO"
						value="${evaluationItem.RATIO}" maxlength='18'
						class="required textInput"
						onkeyup="if(isNaN(value))execCommand('undo')"
						onafterpaste="if(isNaN(value))execCommand('undo')" />
				</dd>
			</dl>
			<dl>
				<dt style="width: 100px">
					评价说明
				</dt>
				<dd style="width: 260px">
					<input type="text" name="REMARK" id="REMARK"
						value="${evaluationItem.REMARK}" maxlength='100'
						class="required textInput"
						/>
				</dd>
			</dl>
			<dl>
				<dt style="width: 100px">
					更新人
				</dt>
				<dd style="width: 260px">
					${evaluationItem.UPDT_USER} 
				</dd>
			</dl>
			<dl>
				<dt style="width: 100px">
					更新时间
				</dt>
				<dd style="width: 260px">${evaluationItem.UPDT_DTIME}</dd>
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