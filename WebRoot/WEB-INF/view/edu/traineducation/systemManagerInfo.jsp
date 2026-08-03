<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">

</script>
<div class="pageContent">
	<form method="post" action="/edu/traineducation/updateSystemManager" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone);">
		<input type="hidden" name="SYSMANA_NO" id="SYSMANA_NO" value="${systemManagerInfo.SYSMANA_NO }">
		<div class="pageFormContent nowrap">
			<dl>
				<dt><spring:message code="liang.hr.viewTraining.title.TRAINING_DIFFERENTIATE"/><!--培训区分--></dt>
				<dd>
					${systemManagerInfo.TRAIN_DIFF_CODE_NAME }
				</dd>
			</dl>
			<dl>
				<dt><spring:message code="edu.systemManager.PEIXUNLEIXING.a"/><!--培训类型--></dt>
				<dd>
					${systemManagerInfo.TRAIN_TYPE_CODE_NAME }
				</dd>
			</dl>
			<dl>
				<dt><spring:message code="edu.systemManager.LEIXINGBIANHAO.a"/><!--类型编号--></dt>
				<dd>
				${systemManagerInfo.TRAIN_TYPE_NO }
				</dd>
			</dl>
			<dl>
				<dt><spring:message code="org.title.REMARK"/><!--备注--></dt>
				<dd>
					<input type="text" name="REMARK" id="REMARK" value="${systemManagerInfo.REMARK }"/>
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
