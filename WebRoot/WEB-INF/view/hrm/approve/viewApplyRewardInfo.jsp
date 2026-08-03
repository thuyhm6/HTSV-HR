<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script>

function f_delShiftInfo(obj) {
	$("#" + obj + "").remove();
}

function validateCallbackViewFamilyInfo(form, callback) {

	var $form = $("#updaterewardInfo");

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
}function submitForm1(type){
	alert(type);
	
}
function submitForm2(type){
	if($("#SUBMIT_TYPE").val()==1){
			$("#SUBMIT_TYPE").val(2);
	var $form = $("#updaterewardInfo").submit();
	}
}
function submitForm3(type){
if($("#SUBMIT_TYPE").val()==1){
			$("#SUBMIT_TYPE").val(3);
			$("#APPLY_TYPE_NUM").val(8);
	var $form = $("#updaterewardInfo").submit();
	}

	
}
function submitForm4(type){
	alert(type);
	
}
</script>
<c:if test="${rewardInfo.SUBMIT_TYPE eq 1}">

	<div class="pageHeader"
		style="width: 300px; margin-left: auto; margin-right: 2px; width: 300px;">

		<div class="searchBar" width="100%" style="border: 0px solid #8aa6d7;">
			<div class="subBar">
				<ul>
					<li>
						<div>
							<a class="buttonActive" onclick="submitForm1('print');"
								alt="<spring:message code='hrm.approve.CLICK_UPLOAD.Z' />"><!-- 点击上传 --><span><!--印刷 --> <spring:message code="hrm.approve.PRINTING" /></span> </a>
						</div>
					</li>
					<li>
						<div>
							<a class="buttonActive" onclick="submitForm2('submit');"
								alt="<spring:message code='hrm.approve.CLICK_UPLOAD.Z' />"><!-- 点击上传 --><span><!--完结 --><spring:message code="hrm.approve.OVER" /></span> </a>
						</div>
					</li>
					<li>
						<div>
							<a class="buttonActive" onclick="submitForm3('back');" 
								alt="<spring:message code='hrm.approve.CLICK_UPLOAD.Z' />"><!-- 点击上传 --><span><!--退回 --> <spring:message code="hrm.approve.RETURN"/></span>
							</a>
						</div>
					</li>
					<li>
						<div>
							<a class="buttonActive" onclick="submitForm4('email');"
								alt="点击上传"><span><!--制定邮件--><spring:message code="hrm.approve.MAKE_MAILE" /></span> </a>
						</div>
					</li>
				</ul>
			</div>
		</div>

	</div>
	<div class="pageContent" style="margin: 0px; padding: 0px;">
		<form id="updaterewardInfo"
			onsubmit="return validateCallbackViewFamilyInfo(this,navTabAjaxDoneWithForm);"
			action="/hrm/approve/updateRewardInfo" method="post">
			<table class="table" width="100%" layoutH="288"
				style="margin: 0px; padding: 0px;">
				<thead>
					<tr>

						<th width="25%">
							NO
						</th>
						<th width="25%">
							Line
						</th>
						<th width="25%">
							<!--申请后--> <spring:message code="hrm.approve.APPLY_AFTER" />
						</th>
						<th width="25%">
							<!-- 申请前 --> <spring:message code="hrm.approve.APPLY_FRONT" />
						</th>
					</tr>
				</thead>
				<tbody>



					<input type="hidden" value="${rewardInfo.PERSON_ID}"
						name="PERSON_NO" id="PERSON_NO" />
					<input type="hidden" value="${rewardInfo.REWARD_NO}" id="REWARD_NO"
						name="REWARD_NO" />
					<input type="hidden" value="${rewardInfo.UPDATE_REWARD_NO}"
						id="UPDATE_REWARD_NO" name="UPDATE_REWARD_NO" />

					<input type="hidden" value="${rewardInfo.APPLY_TYPE_NUM}"
						name="APPLY_TYPE" id="APPLY_TYPE_NUM" />
					<!-- 1提交 2审批 3退回 4取消 -->
					<input type="hidden" value="${rewardInfo.SUBMIT_TYPE}"
						id="SUBMIT_TYPE" name="ACTIVITY_NUM" />
					<tr>
						<td>
							1
						</td>
						<td>
							<!--表扬/得奖--> <spring:message code="hrm.empinfo.praise_prize" />
						</td>
						<td>
							<font
								color="${rewardInfo.REWARD_TYPE ne rewardInfoPro.REWARD_TYPE ?'red':''}">${rewardInfo.REWARD_TYPE}
						</td>
						<td>
							${rewardInfoPro.REWARD_TYPE}
						</td>
					</tr>
					<tr>
						<td>
							2
						</td>
						<td>
							<!--表扬得奖日--> <spring:message code="hrm.approve.PRAISE_AND_AWARD_DATE"/>
						</td>
						<td>
							<font
								color="${rewardInfo.REWARD_DATE ne rewardInfoPro.REWARD_DATE ?'red':''}">${rewardInfo.REWARD_DATE}
						</td>
						<td>
							${rewardInfoPro.REWARD_DATE}
						</td>
					</tr>
					<tr>
						<td>
							3
						</td>
						<td>
							<!-- 授予机关 --> <spring:message code="hrm.empinfo.Awarding_authority" />
						</td>
						<td>
							<font
								color="${rewardInfo.REWARD_CNPY ne rewardInfoPro.REWARD_CNPY?'red':''}">${rewardInfo.REWARD_CNPY}
						</td>
						<td>
							${rewardInfoPro.REWARD_CNPY}
						</td>
					</tr>
					<tr>
						<td>
							4
						</td>
						<td>
							<!-- 奖金 --><spring:message code="hrm.empinfo.BONUS" />
						</td>
						<td>
							<font
								color="${rewardInfo.REWARD ne rewardInfoPro.REWARD ?'red':''}">${rewardInfo.REWARD}
						</td>
						<td>
							${rewardInfoPro.REWARD}
						</td>
					</tr>
					<tr>
						<td>
							5
						</td>
						<td>
							<!--奖金支付代码类型--> <spring:message code="hrm.approve.BONUS_PAYMENT_CODE_TYPE" />
						</td>
						<td>
							<font
								color="${rewardInfo.REWARD_TYPE_CODE ne rewardInfoPro.REWARD_TYPE_CODE ?'red':''}">${rewardInfo.REWARD_TYPE_CODE}
						</td>
						<td>
							${rewardInfoPro.REWARD_TYPE_CODE}
						</td>
					</tr>
					<tr>
						<td>
							6
						</td>
						<td>
							<!-- 备注 --><spring:message code="hrm.empinfo.REMARK" />
						</td>
						<td>
							<font
								color="${rewardInfo.REMARKS ne rewardInfoPro.REMARKS ?'red':''}">${rewardInfo.REMARKS}
						</td>
						<td>
							${rewardInfoPro.REMARKS}
						</td>
					</tr>

				</tbody>
			</table>
			<div width="100%">
				<table class="user_table" width="100%">
					<tr>
						<td calss="td_title" width="20%" style="background: #ddd">
							<!-- 回复 --> <spring:message code="hrm.approve.REPLY" />
						</td>
						<td calss="td_type" width="80%">
							<textarea name="CALLBACK" style="width: 200px; height: 80px">${recruitInfo.REMARK}</textarea>
						</td>
					</tr>
				</table>

				<div width="100%">
					<table class="user_table" width="100%">
						<tr>
							<td calss="td_title" width="20%" style="background: #ddd">
								<!-- 错误内容  --> <spring:message code="hrm.approve.ERROR_CONTENT" />
							</td>
							<td calss="td_type" width="80%">
								<textarea name="EARROR" style="width: 200px; height: 80px">${recruitInfo.REMARK}</textarea>
							</td>
						</tr>
					</table>
				</div>
		</form>
	</div>
</c:if>