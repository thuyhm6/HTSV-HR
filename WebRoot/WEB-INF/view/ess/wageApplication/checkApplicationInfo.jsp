<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.easydrag.js"></script>
<script type="text/javascript">
function validateCallback(form,callback) {
	var essCheckNo = $('#ESS_CHECK_NO').attr("value");
	var checkContent = $('#CHECK_CONTENT').attr("value");
	if (essCheckNo == '') {
		//Check信息出错，不能进行Check操作，请联系管理员!
		alertMsg.error("Check信息出错，不能进行Check操作，请联系管理员!");
		return false;
	}
	if (checkContent == '') {
		//Check内容不能为空，请填写Check内容!
		alertMsg.error("Check内容不能为空，请填写Check内容!");
		return false;
	}
	var $form = $(form);
	if (!$form.valid()) {
		return false;
	}
	$.ajax( {
		type : form.method || 'POST',
		url : $form.attr("action"),
		data : $form.serializeArray(),
		dataType : "json",
		cache : false,
		success : callback || DWZ.ajaxDone,//navTabAjaxDone,
		error : DWZ.ajaxError
	});
	return false;
}
</script>
<div class="pageContent">
	<div>
		<c:if test="${fn:length(messageList) > 0}">
			<table width="99%" align="center" layoutH="360" class="user_table margin_b">
				<tr>
					<td class="td_title" width="15%" style="text-align: center">申请者/工号</td>
					<td class="td_title" width="15%" style="text-align: center">费用类型</td>
					<td class="td_title" width="30%" style="text-align: center">申请费用发放期间</td>
					<td class="td_title" width="15%" style="text-align: center">金额</td>
					<td class="td_title" width="15%" style="text-align: center">备注</td>
				</tr>
				<c:forEach items="${messageList}" var="message" varStatus="i">
					<tr>
						<td class="td_type" width="15%" style="text-align: center">${message.EMPNAME}[${message.EMPID}]</td>
						<td class="td_type" width="15%" style="text-align: center">${message.TYPENAME}</td>
						<td class="td_type" width="30%" style="text-align: center">${message.START_DATE}~${message.END_DATE}</td>
						<td class="td_type" width="15%" style="text-align: center">${message.MONEY}</td>
						<td class="td_type" width="15%" style="text-align: center">${message.DEMO}</td>
					</tr>
				</tr>
				<c:if test="${fn:length(messageList)==i.count}">
					<tr>
						<td class="td_type" width="15%" style="text-align: center"></td>
						<td class="td_type" width="15%" style="text-align: center"></td>
						<td class="td_type" width="30%" style="text-align: center"></td>
						<td class="td_type" width="15%" style="text-align: center">追加款：${message.ZHENG }</td>
						<td class="td_type" width="15%" style="text-align: center">追减款：${message.FU}</td>
					</tr>
				</c:if>
			</c:forEach>
		</table>
		<c:set value="/ess/wageApplication/checkApplicationInfo" var="pageUrl" />
		<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
		</c:if>
	</div>
	<form id="checkApplyInfo" method="post"	action="/ess/affirmLeaveApply/checkApplicationInfo"
		class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone)">
		<div>
			<table class="user_table" width="99%" align="center">
			<tr>
				<c:if test="${fn:length(affirmorList) > 0}">
					<td class="td_title" width="11%" style="text-align: center"	rowspan="${fn:length(affirmorList)+1 }">
						决裁线
					</td>
				</c:if>
				<c:if test="${fn:length(affirmorList) == 0}">
					<td class="td_title" width="11%" style="text-align: center"	rowspan="1">决裁线</td>
				</c:if>
				<td class="td_title" width="15%" style="text-align: center">决裁等级</td>
				<td class="td_title" width="15%" style="text-align: center">决裁者</td>
				<td class="td_title" width="15%" style="text-align: center">决裁情况</td>
				<td class="td_title" width="15%" style="text-align: center">审批时间</td>
				<td class="td_title" width="30%" style="text-align: center">决裁批注</td>
			</tr>
			<c:forEach items="${affirmorList}" var="affirmor" varStatus="j">
				<tr id="${affirmor.AFFIRMOR_ID }">
					<td class="td_type" width="100" style="text-align: center">
						${affirmor.AFFIRM_LEVEL}
					</td>
					<td class="td_type" width="180" style="text-align: center">
						${affirmor.EMPNAME}[${affirmor.EMPID}]
					</td>
					<td class="td_type" width="100" style="text-align: center">
						<c:if test="${affirmor.AFFIRM_FLAG eq '0'}">未决裁</c:if>
						<c:if test="${affirmor.AFFIRM_FLAG eq '1'}">已通过</c:if>
						<c:if test="${affirmor.AFFIRM_FLAG eq '2'}">已否决</c:if>
					</td>
					<td class="td_type" width="180" style="text-align: center">${affirmor.AFFIRM_DATE}</td>
					<td class="td_type">${affirmor.AFFIRM_CONTENT}</td>
				</tr>
			</c:forEach>
			
			<tr>
				<td class="td_type" colspan="6"><br/></td>
			</tr>
			<tr>
				<td class="td_title" width="10%" style="text-align: center" rowspan="${fn:length(checkorList)*2+1 }">Review</td>
				<td class="td_title" width="15%" style="text-align: center">Type</td>
				<td class="td_title" width="30%" style="text-align: center" colspan="2">Requests</td>
				<td class="td_title" width="45%" style="text-align: center" colspan="2">Reviewed</td>
			</tr>

			<c:forEach items="${checkorList}" var="checkor" varStatus="i">
				<tr>
					<c:if test="${i.count eq '1'}">
						<td class="td_type" width="15%" style="text-align: center" rowspan="${fn:length(checkorList)*2}">Public</td>
					</c:if>
					<td class="td_type" width="30%" style="text-align: center" colspan="2">
						[${checkor.AFFIRM_EMPID}]${checkor.AFFIRM_NAME}&nbsp;&nbsp;&nbsp;${checkor.AFFIRM_POSITION}
						&nbsp;&nbsp;(${checkor.AFFIRM_DEPT_NAME})&nbsp;/&nbsp;${checkor.AFFIRM_DATE}&nbsp;
						<c:if test="${checkor.AFFIRM_FLAG == 0}">未决裁</c:if>
						<c:if test="${checkor.AFFIRM_FLAG == 1}">已通过</c:if>
						<c:if test="${checkor.AFFIRM_FLAG == 2}">已否决</c:if>
					</td>
					<td class="td_type" width="45%" style="text-align: center" colspan="2">
						[${checkor.CHECK_EMPID}]${checkor.CHECK_NAME}&nbsp;&nbsp;&nbsp;${checkor.CHECK_POSITION}
						&nbsp;&nbsp;(${checkor.CHECK_DEPT_NAME})&nbsp;/&nbsp;${checkor.CHECK_DATE}&nbsp;
						<c:if test="${checkor.CHECK_FLAG == 0}">未Check</c:if>
						<c:if test="${checkor.CHECK_FLAG == 1}">已Check</c:if>
					</td>
				</tr>
				<tr>
					<td class="td_type" width="30%" colspan="2">
						<textarea name="affirmRemark" cols="75" rows="2" disabled="disabled">[Request]：${checkor.CHECK_REASON}</textarea>
					</td>
					<td class="td_type" width="45%" colspan="2">
						<c:if test="${checkor.CHECKOR_ID eq PERSON_ID}">
							<input type="hidden" id="ESS_CHECK_NO" name="ESS_CHECK_NO" value="${checkor.ESS_CHECK_NO }" />
							<input type="hidden" id="ESS_AFFIRM_NO" name="ESS_AFFIRM_NO" value="${checkor.ESS_AFFIRM_NO }" />
							<input type="hidden" id="APPLY_TYPE" name="APPLY_TYPE" value="217886">
							<input type="hidden" id="PERSON_ID" name="PERSON_ID" value="${PERSON_ID}">
							<input type="hidden" id="APPLY_NO" name="APPLY_NO" value="${APPLY_NO}">
							<textarea id="CHECK_CONTENT" name="CHECK_CONTENT" cols="75"
								rows="2">${checkor.CHECK_CONTENT}</textarea>
						</c:if>
						<c:if test="${checkor.CHECKOR_ID ne PERSON_ID}">
							[Check]：${checkor.CHECK_CONTENT}
						</c:if>
					</td>
				</tr>
			</c:forEach>
			<c:if test="${fn:length(checkorList) == 0}">
				<tr>
					<td class="td_type" width="15%" style="text-align: center" rowspan="2">Public</td>
					<td class="td_type" width="30%" style="text-align: center" colspan="2">无</td>
					<td class="td_type" width="45%" style="text-align: center" colspan="2">无</td>
				</tr>
				<tr>
					<td class="td_type" width="30%" style="text-align: center" colspan="2">无</td>
					<td class="td_type" width="45%" style="text-align: center" colspan="2">无</td>
				</tr>
			</c:if>
		</table>
		</div>
		<div class="formBar">
			<ul>
				<c:if test="${is_check eq '0' }">
					<li>
						<div class="button">
							<div class="buttonContent">
								<button type="submit">提交</button>
							</div>
						</div>
					</li>
				</c:if>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" class="close">
								<spring:message code="public.title.close" />
								<!-- 关闭 -->
							</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</form>
</div>
