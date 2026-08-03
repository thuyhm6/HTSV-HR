<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
	function validateCallback_updateSubject(form, callback) {
		var $form = $(form);
		if (!$form.valid()) {
			return false;
		}
		var standardC = document.updateSubjectForm.STANDARD_C.value;
		if (standardC<0 || standardC>9999) {
			alertMsg.error("<spring:message code='empsubject.alert.standardC'/>");
			//alert("标准课时必须是0-9999之间的数！");
			return false;
		}
		if (standardC.indexOf('.') > -1
				&& standardC.length - standardC.indexOf('.') - 1 > 1) {
			alertMsg.error("<spring:message code='empsubject.alert.standardCLength'/>");
			//alert("最多保留1位小数！");
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
	<form id="updateSubjectForm" name="updateSubjectForm" method="post" action="/empsubject/updateSubject" class="pageForm required-validate" onsubmit="return validateCallback_updateSubject(this,dialogAjaxDone);">
		<div class="pageFormContent nowrap" layoutH="60">
			<dl>
				<dt><spring:message code="ar.viewcycleparameter.title.gongsi"/><!-- 公司 --></dt>
				<dd style="width:100px"><input name="SUBSD_CD" value="${subjectInfo.SUBSD_CD}" readonly="true"/></dd>
			</dl>
			<dl>
				<dt><spring:message code="empsubject.subjectGrID"/><!-- 课程组ID --></dt>
				<dd style="width:100px"><input name="SUBJT_GR_ID" value="${subjectInfo.SUBJT_GR_ID}" readonly="true"/></dd>
			</dl>
			<dl>
				<dt><spring:message code="empsubject.subjectID"/><!-- 课程ID --></dt>
				<dd style="width:100px"><input name="SUBJT_ID" value="${subjectInfo.SUBJT_ID}" readonly="true"/></dd>
			</dl>
			<dl>
				<dt><spring:message code="empsubject.subjectNm"/><!-- 课程名称 --></dt>
				<dd style="width:100px"><input name="SUBJT_NM" value="${subjectInfo.SUBJT_NM}" maxlength="33" class="required textInput"/></dd>
			</dl>
			<dl>
				<dt><spring:message code="empsubject.standardC"/><!-- 标准课时 --></dt>
					<dd style="width:260px">
						<input id="STANDARD_C" name="STANDARD_C" class="required textInput" value="${subjectInfo.STANDARD_C}" maxlength="6" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')"  style="ime-mode:disabled"/>
					            <spring:message code="empsubject.message.standardC"/><!-- 【0-9999,1位小数】 -->
					</dd>
			</dl>
			<dl><!-- 开始日期 -->
				<dt><spring:message code="public.title.startDate"/></dt>
				<dd style="width:260x"><input id="START_DATE" type="text" name="START_DATE" class="date required" readonly value="${subjectInfo.START_DATE}"/>
				<a class="inputDateButton"><!-- 选择 -->
					 <spring:message code="public.title.choose"/>
				</a></dd>
			</dl>
			<dl><!-- 结束日期 -->
				<dt><spring:message code="public.title.endDate"/></dt>
				<dd style="width:260px"><input id="END_DATE" type="text" name="END_DATE" class="date required" readonly value="${subjectInfo.END_DATE}"/>
			    <a class="inputDateButton"><!-- 选择 -->
	                 <spring:message code="public.title.choose"/>
				</a></dd>
			</dl>
			<dl>
				<dt><spring:message code="empsubject.useYn"/><!-- 状态 --></dt>
				<dd style="width:100px">
					<select name="USE_YN">
						<option value="Y" <c:if test="${subjectInfo.USE_YN eq 'Y'}">selected</c:if>>
						<spring:message code="sys.affirm.title.yes"/><!--是--></option>
						<option value="N" <c:if test="${subjectInfo.USE_YN eq 'N'}">selected</c:if>>
						<spring:message code="sys.affirm.title.no"/><!--否--></option>
					</select>
				</dd>
			</dl>
			<dl>
				<dt>线上区分</dt>
				<dd style="width:100px">
					<ait:ComboSyCodeDescByCpnyID id="ONLINE_STATE"
							name="ONLINE_STATE" parentNo="14013523"
							selected="${subjectInfo.ONLINE_STATE}" cnpyID="${defaultCpny}" limit="all" />
				</dd>
			</dl>
			<dl>
				<dt><spring:message code="empsubject.updateTime"/><!-- 更新时间 --></dt>
				<dd style="width:100px"><input name="UPDT_DTIME" value="${subjectInfo.UPDT_DTIME}" readonly="true"/></dd>
			</dl>
			<dl>
				<dt><spring:message code="empsubject.empNm"/><!-- 员工 --></dt>
				<dd style="width:300px">[${empId}]${personName}</dd>
				<input name="UPDT_USER" value="${personId}"  type="hidden" />
			</dl>	
		</div>
		
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">
				<spring:message code="button.sys.affirm.save"/><!--保存--></button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">
				<spring:message code="public.title.cancle"/><!--取消--></button></div></div></li>
			</ul>
		</div>
	</form>
	
</div>