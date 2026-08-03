<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<script>

function f_delShiftInfo(obj) {
	$("#" + obj + "").remove();
}

function validateCallbackViewFamilyInfo(form, callback) {

	var $form = $("#essAddRewardInfo");

	if (!$form.valid()) {
		return false;
	}

	$.ajax( {
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
<h1>能力信息（表彰事项）</h1>
	<form id="essAddRewardInfo" method="post"
		action="/ess/empinfo/essAddRewardInfo"
		class="pageForm required-validate"
		onsubmit="return validateCallbackViewFamilyInfo(this, dialogAjaxDone);">

		<div class="pageFormContent" layoutH="56">
			<table width="100%" border="0" cellpadding="0" cellspacing="0"
				class="user_table">
				<c:if test="${APPLY_TYPE=='1'}">
					<input type="hidden" name="APPLY_TYPE" value="${1}" />

				</c:if>
				<c:if test="${APPLY_TYPE=='2'}">
					<input type="hidden" name="APPLY_TYPE" value="${2}" />

				</c:if>
				<input type="hidden" name="UPDATE_REWARD_NO" value="${REWARD_NO}" />

				<tr>

					<td class="td_title" >
						表扬/得奖
					</td>


					<td class="td_type" >

					<ait:SelectSyCodeByCpnyID name="REWARD_TYPE" parentNo="14014334"
							cnpyID="${defaultCpny}"  selected="${rewardInfo.REWARD_TYPE}" />
					</td>

				</tr>
				
				
				<tr>

					<td class="td_title" >
						表扬（得奖）日
					</td>


					<td class="td_type" >

						<input type="text" name="REWARD_DATE" class="date required"
										readonly="true" format="yyyy-MM-dd" yearstart="-50"
										yearend="5" onClick="setdate(this);" value="${rewardInfo.REWARD_DATE}" />
					</td>

				</tr>

				<tr>

					<td class="td_title" >
				授予机关
					</td>


					<td class="td_type" >
					
					<input type="text" name="REWARD_CNPY" class="textInput" value="${rewardInfo.REWARD_CNPY}" />
								
								
					</td>
				

				</tr>
				<tr>

					<td class="td_title" >
			     奖金
					</td>


					<td class="td_type" >
					
			    	<input type="text" name="REWARD" class="textInput" value="${rewardInfo.REWARD}" />
								
								
					</td>
					<td class="td_title" >
			     奖金支付类型代码
					</td>


					<td class="td_type">
					

					<ait:SelectSyCodeByCpnyID name="REWARD_TYPE_CODE" parentNo="14014347"
							cnpyID="${defaultCpny}" selected="${rewardInfo.REWARD_TYPE_CODE}" />								
								
					</td>
				

				</tr>


				<tr>

					<td class="td_title" >
						备注
					</td>


					<td class="td_type">

						<textarea rows="4" cols="30" name='REMARKS'>${rewardInfo.REMARKS}</textarea>
					</td>

					

				</tr>




			</table>

			<div id="createTable" width="100%"></div>

			<input type="hidden" name="count" id="count" value="1">
		</div>
		<div class="formBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">
								<spring:message code="public.title.submit" />
								<!-- 保存 -->
							</button>
						</div>
					</div>
				</li>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" class="close">
								<spring:message code="public.title.cancle" />
								<!-- 取消 -->
							</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</form>
</div>