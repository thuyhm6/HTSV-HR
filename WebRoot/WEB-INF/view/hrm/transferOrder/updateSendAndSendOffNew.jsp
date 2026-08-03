<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function validateCallback_updateSendAndSendOffNew(form, callback) {
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
	<form method="post" action="/hrm/transferOrder/updateSendAndSendOffNew" class="pageForm required-validate" onsubmit="return validateCallback_updateSendAndSendOffNew(this,dialogAjaxDone);">
		<div class="pageFormContent nowrap" layoutH="60">
			<input type="hidden" name="EXP_INSIDE_NO" value="${hrDispatchInfo.EXP_INSIDE_NO}"/>
			<dl>
				<dt><spring:message code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME"/><!--社号/姓名：--></dt>
				<dd>
					${hrDispatchInfo.EMPID}/${hrDispatchInfo.LOCAL_NAME} 
				</dd>
			</dl>
			<dl>
				<dt><spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/><!-- 部门  --></dt>
				<dd>
					${hrDispatchInfo.ORG_NAME_LOCAL}
				</dd>
			</dl>
			<dl>
				<dt><spring:message code="sys.affirm.title.duty"/><!--职责--></dt>
				<dd>
					${hrDispatchInfo.POSITION_NAME}
				</dd>
			</dl>
			
			<dl>
				<dt><spring:message code="inct.salesman.empType"/><!--人员类型--></dt>
				<dd>
					 ${hrDispatchInfo.EMP_TYPE_NAME}
				</dd>
			</dl>
			
			<dl>
				<dt><spring:message code="hr.viewPersonalInfo.title.gongzuodiqu"/><!--工作地区--></dt>
				<dd>
					${hrDispatchInfo.WORK_AREA}
				</dd>
			</dl>
			
			<dl>
				<dt><spring:message code="hr.enpinfo.title.EMP.EXPDATE"/><!--发令日期--></dt>
				<dd>
					${hrDispatchInfo.ORDER_DATE}
				</dd>
			</dl>
			
			<dl>
				<dt><spring:message code="pa.insurance.title.startMonth"/><!--开始月--></dt>
				<dd>
					<ait:date yearName="paYearStartDate" monthName="paMonthStartDate" yearSelected="${hrDispatchInfo.S_YEAR}" monthSelected="${hrDispatchInfo.S_MONTH}"/>
				</dd>
			</dl>
			
			<dl>
				<dt><spring:message code="pa.insurance.title.endMonth"/><!-- 结束月  --></dt>
				<dd>
					<ait:date yearName="paYearEndDate" monthName="paMonthEndDate" yearSelected="${hrDispatchInfo.E_YEAR}" monthSelected="${hrDispatchInfo.E_MONTH}"/>
				</dd>
			</dl>
			
			<dl>
				<dt><spring:message code="display.emp.ben.or.sendtoadministrator"/><!-- 派遣地  --></dt>
				<dd>
					${hrDispatchInfo.CONTENTS }
				</dd>
			</dl>
			
			<dl>
				<dt><spring:message code="display.emp.ben.sendtype"/><!-- 派遣类型  --></dt>
				<dd>
					${hrDispatchInfo.TRANS_NAME}
				</dd>
			</dl>
			
			<dl>
				<dt><spring:message code="hr.viewBadArchives.title.REMARK"/><!-- 备注  --></dt>
				<dd>
				${hrDispatchInfo.REMARK}
				</dd>
			</dl>
			
			<dl>
				<dt><spring:message code="is.joininstance.title.statement"/><!-- 状态  --></dt>
				<dd>
					 <input id="ACTIVITY" type="radio" name="ACTIVITY" value="1" <c:if test="${hrDispatchInfo.ACTIVITY eq 1}">checked</c:if>/>已生效
					 <input id="ACTIVITY" type="radio" name="ACTIVITY" value="0" <c:if test="${hrDispatchInfo.ACTIVITY eq 0}">checked</c:if>/>未生效
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

