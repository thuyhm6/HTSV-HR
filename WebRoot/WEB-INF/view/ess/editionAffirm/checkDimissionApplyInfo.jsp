<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function checkApply(form){
	var essCheckNo = $('#ESS_CHECK_NO').attr("value");
	var checkContent = $('#CHECK_CONTENT').attr("value");
	if(essCheckNo == ''){
		//Check信息出错，不能进行Check操作，请联系管理员!
		alertMsg.error("Check信息出错，不能进行Check操作，请联系管理员!");
		return false;
	}
	if(checkContent == ''){
		//Check内容不能为空，请填写Check内容!
		alertMsg.error("Check内容不能为空，请填写Check内容!");
		return false;
	}
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
		success: navTabAjaxDone,
		error: DWZ.ajaxError
	});
	
	return false;
}

function validateCallbackViewUpdateDimission(form, callback) {
		var $form = $("#checkApplyInfo");
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
	<table class="table" width="100%" layoutH="350" nowrapTD="false">
		<thead>
			<tr>
				<th width="5%">
					<!-- 工号 -->
					<spring:message code="display.emp.ben.serviceno" />
				</th>
				<th width="5%">
					<!-- 姓名 -->
					<spring:message code="inct.salesman.Name" />
				</th>
				<th width="10%">
					<!-- 人员类型 -->
					<spring:message code="is.company.title.PERSON_TYPE" />
				</th>
				<th width="10%"><spring:message
						code="ess.infoApply.title.essApplyTime" />
					<!-- 申请日期 -->
				</th>
				<th width="10%"><spring:message
						code="display.pa.ecc.expectresigndate" />
					<!-- 预离职日期 -->
				</th>
				<th width="21%"><spring:message
						code="ess.trans.title.resignReason" />
					<!-- 离职原因-->
				</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${editionAffirmList}" var="item" varStatus="i">
				<tr>
					<td style="text-align:left">${item.EMPID }
					</td>
					<td style="text-align:left">${item.LOCAL_NAME }</td>
					<td style="text-align:left">${item.EMP_TYPE_NAME }</td>
					<td style="text-align:left">${item.APPLY_TIME }</td>
					<td>${item.APPLY_LEAVE_TIME }</td>
					<td>${item.APPLY_REASON}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
<div class="pageContent" >
	<form id="checkApplyInfo" method="post" action="/ess/editionAffirm/checkDimissionInfo" class="pageForm required-validate" 
		onsubmit="return validateCallbackViewUpdateDimission(this, navTabAjaxDone)">
		<table width="100%"  border="0" layoutH="200" cellpadding="0" cellspacing="0" class="user_table margin_b" >
			<tr>
				<c:if test="${affirmorListCnt > 0}">
					<td class="td_title" width="10%" style="text-align: center" rowspan="${affirmorListCnt+1 }"><!-- 决裁线 -->
						决裁线
					</td>
				</c:if>
				<c:if test="${affirmorListCnt == 0}">
					<td class="td_title" width="10%" style="text-align: center" rowspan="${2 }"><!-- 决裁线 -->
						决裁线
					</td>
				</c:if>
				<td class="td_title" width="15%" style="text-align: center"><!-- 决裁等级 -->
					决裁等级
				</td>
				<td class="td_title" width="15%" style="text-align: center"><!-- 决裁者 -->
					决裁者
				</td>
				<td class="td_title" width="15%" style="text-align: center"><!-- 决裁情况 -->
					决裁情况
				</td>
				<td class="td_title" width="15%" style="text-align: center"><!-- 审批时间 -->
					审批时间
				</td>
				<td class="td_title" width="30%" style="text-align: center"><!-- 决裁批注 -->
					决裁批注
				</td>
			</tr>
			<c:forEach items="${affirmList}" var="affirmor" varStatus="i">			
				<tr>
					<td class="td_type" width="15%" style="text-align: center">${affirmor.AFFIRM_LEVEL}</td>
					<td class="td_type" width="15%" style="text-align: center">[${affirmor.EMPID}]${affirmor.LOCAL_NAME}</td>
					<td class="td_type" width="15%" style="text-align: center">
						<c:if test="${affirmor.AFFIRM_FLAG eq '0'}">
							<!--<font color="blue">未决裁</font>-->
							未决裁
						</c:if>
						<c:if test="${affirmor.AFFIRM_FLAG eq '1'}">
							<!--<font color="green">已通过</font>-->
							已通过
						</c:if>
						<c:if test="${affirmor.AFFIRM_FLAG eq '2'}">
							<!--<font color="red">已否决</font>-->
							已否决
						</c:if>
					</td>
					<td class="td_type" width="15%" style="text-align: center">${affirmor.AFFIRM_DATE}</td>
					<td class="td_type" width="30%" style="text-align: center">${affirmor.AFFIRM_CONTENT}</td>
				</tr>			
			</c:forEach>
			<tr>
				<c:if test="${checkListCnt > 0}">
					<td class="td_title" width="10%" style="text-align: center" rowspan="${checkListCnt*2+1 }"><!-- Review -->
						Review
					</td>
				</c:if>
				<c:if test="${checkListCnt == 0}">
					<td class="td_title" width="10%" style="text-align: center" rowspan="${3 }"><!-- Review -->
						Review
					</td>
				</c:if>
				<td class="td_title" width="15%" style="text-align: center"><!-- Type -->
					Type
				</td>
				<td class="td_title" width="30%" style="text-align: center" colspan="2"><!-- Requests -->
					Requests
				</td>
				<td class="td_title" width="45%" style="text-align: center" colspan="2"><!-- Reviewed -->
					Reviewed
				</td>
			</tr>
			
			<c:forEach items="${checkList}" var="checkor" varStatus="i">			
				<tr>
					<td class="td_type" width="15%" style="text-align: center">Public</td>
					<td class="td_type" width="30%" style="text-align: left" colspan="2">
						[${checkor.EMPID_R}]${checkor.LOCAL_NAME_R}&nbsp;&nbsp;&nbsp;${checkor.POSITION_NO_R}
						&nbsp;&nbsp;(${checkor.DEPTNAME_R})&nbsp;/&nbsp;${checkor.DATE_R}&nbsp;<br/>
						[Request]：${checkor.CHECK_REASON}
					</td>
					<td class="td_type" width="45%" style="text-align: left" colspan="2">
						[${checkor.EMPID_C}]${checkor.LOCAL_NAME_C}&nbsp;&nbsp;&nbsp;${checkor.POSITION_NO_C}
						&nbsp;&nbsp;(${checkor.DEPTNAME_C})&nbsp;/&nbsp;${checkor.DATE_C}&nbsp;
						<c:if test="${checkor.CHECK_FLAG == 0}">
							未Check
						</c:if>
						<c:if test="${checkor.CHECK_FLAG == 1}">
							已Check
						</c:if><br/>
						 <c:if test="${checkor.ESS_CHECK_NO eq check_no}">
						 <c:if test="${checkor.PERSON_ID_C eq personId}">
							<input type="hidden" id="ESS_CHECK_NO" name="ESS_CHECK_NO" value="${check_no}"/>
							<input type="hidden" id="ESS_AFFIRM_NO" name="ESS_AFFIRM_NO" value="${checkor.ESS_AFFIRM_NO }"/>
							<input id="PERSON_ID" name="PERSON_ID" type="hidden" value="${personId }" />
							<input id="APPLY_TYPE" name="APPLY_TYPE" type="hidden" value="218296" />
							<input id="APPLY_NO" name="APPLY_NO" type="hidden" value="${editionAffirmList[0].APPLY_NO}" />
							[Check]：<input id="CHECK_CONTENT" name="CHECK_CONTENT" size="55" maxlength="200"/>
						</c:if>
						</c:if>
						<c:if test="${checkor.ESS_CHECK_NO ne check_no}">
							[Check]：${checkor.CHECK_CONTENT}
						</c:if>
					</td>
				</tr>	
			</c:forEach>
			<c:if test="${checkorListCnt == 0}">
				<tr>
				<td class="td_type" width="15%" style="text-align: center">Public</td>
					<td class="td_type" width="30%" style="text-align: center" colspan="2">无</td>
					<td class="td_type" width="45%" style="text-align: center" colspan="2">无</td>
				</tr>
			</c:if>
		</table>
		<div class="formBar">
			<ul>
			      <c:if test="${flag_check  eq '1'}">
					<li>
						<div class="button">
							<div class="buttonContent"><!--提交-->
								<button type="submit">
									提交
								</button>
							</div>
						</div>
					</li>
					</c:if>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" class="close">
								<spring:message code="public.title.close"/><!-- 关闭 -->
							</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</form>
</div>