<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
    function validateCallbackViewupdatePaTempSalesEmp(form, callback) {
		var $form = $("#updatePaTempSalesEmp");
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
	<form id="updatePaTempSalesEmp" method="post" action="/pa/tempsale/updatePaTempSalesEmp" class="pageForm required-validate" onsubmit="return validateCallbackViewupdatePaTempSalesEmp(this, dialogAjaxDone)">
		<div class="pageFormContent" layoutH="56">
			<table width="100%"  border="0" cellpadding="0" cellspacing="0" class="user_table margin_b">
				<tr>
					<td class="td_title"  width="20%">
						姓名
					</td>
					<td class="td_type" width="30%">
						<input type="text" id="LOCAL_NAME" name="LOCAL_NAME" value="${empInfo.LOCAL_NAME }" class="required textInput"  maxlength="20" size="30"/>
					</td>
					<td class="td_title"  width="18%">
						身份证号码
					</td>
					<td class="td_type"  width="30%">${empInfo.ID_CARD }
						<input type="hidden" id="ID_CARD" name="ID_CARD" value="${empInfo.ID_CARD }"/>
					</td>
				</tr>
				<tr>
					<td class="td_title" >
						出生年月
					</td>
					<td class="td_type">${empInfo.BIRTH_DATE }
						<input type="hidden" id="BIRTH_DATE" name="BIRTH_DATE" value="${empInfo.BIRTH_DATE }" >
					</td>
					<td class="td_title" >
						联系方式
					</td>
					<td class="td_type">
						<input type="text" id="CELLPHONE" name="CELLPHONE" value="${empInfo.CELLPHONE }" class="required textInput" maxlength="20" size="30"/>
					</td>
				</tr>
				<tr>
					<td class="td_title" >
						所属单位
					</td>
					<td class="td_type" colspan="3">
					
						<select id="DEPT_NAME" name="DEPT_NAME">
							<OPTION value="">请选择</OPTION>
							<c:forEach items="${branchList}" var="item" varStatus="i">
								<OPTION value="${item.ACC_ORG_CODE }" <c:if test="${item.ACC_ORG_CODE eq empInfo.DEPT_NAME}">selected</c:if>>${item.CONTENT}</OPTION>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td class="td_title" >
						评价等级
					</td>
					<td class="td_type">
		 				<ait:SelectSyCodeByCpnyID id="EVS_GRADE" name="EVS_GRADE" parentNo="123195" selected="${empInfo.EVS_GRADE}" cnpyID="${defaultCpny}" limit="all"/>
					</td>
					<td class="td_title" >
						黑名单与否
					</td>
					<td class="td_type">
						<select name="IS_BLACK_LIST">
							<option value="">请选择</option>
					   		<option value="Y" <c:if test="${empInfo.IS_BLACK_LIST eq 'Y'}">selected</c:if>>Y</option>
					   		<option value="N" <c:if test="${empInfo.IS_BLACK_LIST eq 'N'}">selected</c:if>>N</option>
				   		</select>
					</td>
				</tr>
				<tr>
					<td class="td_title" >
						银行帐号
					</td>
					<td class="td_type">
						<input type="text" id="BANK_NO" name="BANK_NO" value="${empInfo.BANK_NO }" class="required textInput" maxlength="20" size="30"/>
					</td>
					<td class="td_title" >
						开户行
					</td>
					<td class="td_type">
						<input type="text" id="BANK_NAME" name="BANK_NAME" value="${empInfo.BANK_NAME }" class="required textInput" maxlength="50" size="30"/>
					</td>
				</tr>
			</table>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit"><spring:message code="heran.examineSave.title"/><!-- 保存 --></button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close"><spring:message code="public.title.cancle"/><!-- 取消 --></button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>