<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
	function validateCallback_IncentiveCalcItem(form, callback) {
		var $form = $(form);
		if (!$form.valid()) {
			return false;
		}
		var adjuAmtBefore=document.editIncentiveCalcItemForm.ADJST_AMT_BEFORE.value;
		var adjuAmt=document.editIncentiveCalcItemForm.ADJST_AMT.value;
		var accrualYn=document.editIncentiveCalcItemForm.ACCRUAL_YN.value;
		
		if (eval(adjuAmt)==eval(adjuAmtBefore)) {
			alert("提成调整金额无变更！")
			return false;
		}
		$.ajax({
			type : form.method || 'POST',
			url : $form.attr("action"),
			data : $form.serializeArray(),
			dataType : "json",
			cache : false,
			success : function(result) {
				alertMsg.info(result.message);
				if (result.statusCode == 200) {
					$.pdialog.closeCurrent();
					//页面重载
					if (accrualYn=='Y'){
						navTabSearch($("#searchForm_se0202_aa"));
					}else{
						navTabSearch($("#searchForm_se0201_aa"));
					}
				}
			},
			error : DWZ.ajaxError
		});
		return false;
	}
</script>
<div class="pageContent">
	<form method="post" id="editIncentiveCalcItemForm"
		name="editIncentiveCalcItemForm"
		action="/inct/salesman/editIncentiveCalcItem"
		class="pageForm required-validate"
		onsubmit="return validateCallback_IncentiveCalcItem(this,dialogAjaxDone);">
		<input type="hidden" name="ACCRUAL_YN" id="ACCRUAL_YN" value="${searchMap.ACCRUAL_YN}"/>
		<input type="hidden" name="PAY_AREA_CD" id="PAY_AREA_CD" value="${incentiveItem.PAY_AREA_CD}"/>
		<table width="100%"  border="0" cellpadding="0" cellspacing="0" class="user_table margin_b">
			<tr>
				<td class="td_title"  width="20%">
					<spring:message code="hr.viewPersonalInfo.title.DEPTNAME" />
					<!--部门名称-->
				</td>
				<td class="td_type" width="80%">
					${incentiveItem.ORG_NM}
				</td>
			</tr>
			<tr>
				<td class="td_title"  width="20%">
					<spring:message code="inct.salesman.empNo" />
					<!--社号-->
				</td>
				<td class="td_type" width="80%">
					${incentiveItem.EMPNO} 
					<input type="hidden" id="EMPNO" name="EMPNO" value="${incentiveItem.EMPNO}" />
				</td>
			</tr>
			<tr>
				<td class="td_title"  width="20%">
					<spring:message code="inct.salesman.empName" />
					<!--姓名 -->
				</td>
				<td class="td_type" width="80%">
					${incentiveItem.EMP_NM}
				</td>
			</tr>
			<tr>
				<td class="td_title"  width="20%">
					<spring:message code="inct.salesman.calcMonth" />
					<!--计算月份-->
				</td>
				<td class="td_type" width="80%">
					${incentiveItem.INCTV_MON} 
					<input type="hidden" id="INCTV_MON" name="INCTV_MON" value="${incentiveItem.INCTV_MON}" />
				</td>
			</tr>			
			<tr>
				<td class="td_title"  width="20%">
					<spring:message code="inct.salesman.adjustInct" />
					<!--调整提成-->
				</td>
				<td class="td_type" width="80%">
					<input type="hidden" name="ADJST_AMT_BEFORE" id="ADJST_AMT_BEFORE" value="${incentiveItem.ADJST_AMT}" />
					<input type="text" name="ADJST_AMT" id="ADJST_AMT"
						value="${incentiveItem.ADJST_AMT}" maxlength='18'
						class="required textInput"
						onkeyup="if(isNaN(value))execCommand('undo')"
						onafterpaste="if(isNaN(value))execCommand('undo')" />
				</td>
			</tr>
			<tr>				
				<td class="td_title"  width="20%">
					<spring:message code="ar.viewarcardrecord.title.beizhu" />
					<!--备注-->
				</td>
				<td class="td_type" width="80%">
					<input type="text" name="REMARK" id="REMARK" value="${incentiveItem.REMARK}" maxlength='50'
						class="required textInput" />
				</td>
			</tr>
			<tr>
				<td class="td_title"  width="20%">
					<spring:message code="inct.salesman.createTime" />
					<!--注册时间-->
				</td>
				<td class="td_type" width="80%">
					${incentiveItem.RGST_DTIME}
				</td>
			</tr>
			<tr>					
				<td class="td_title"  width="20%">
					注册人
					<!--注册人-->
				</td>
				<td class="td_type" width="80%">
					${incentiveItem.RGST_USER}
				</td>			
			</tr>
			<tr>
				<td class="td_title"  width="20%">
					<spring:message code="inct.salesman.updateTime" />
					<!--更新时间-->
				</td>
				<td class="td_type" width="80%">
					${incentiveItem.UPDT_DTIME}
				</td>
			</tr>
			<tr>
				<td class="td_title"  width="20%">
					<spring:message code="inct.salesman.updateBy" />
					<!--更新人-->
				</td>
				<td class="td_type" width="80%">
					${incentiveItem.UPDT_USER}
				</td>				
			</tr>
		</table>
		<div class="formBar">
			<ul>
				<li><div class="button">
						<div class="buttonContent">
							<button type="submit">
								保存
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