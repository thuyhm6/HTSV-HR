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
</script>

<c:if test="${infoApplyLeave.APPLY_TYPE eq 'BATCH'}">
<div class="pageContent">
	<table class="table" width="100%" layoutH="240" nowrapTD="false">
		<thead>
			<tr>
				<th>社号</th>
				<th>姓名</th>
				<th>Leave开始时间</th>
				<th>Leave结束时间</th>
				<th>Leave时长</th>
				<th>Leave类型</th>
				<th>总天数</th>
				<th>已使用天数</th>
				<th>剩余天数</th>
				<th>Leave原因</th>
				<th>附件</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${leaveBatchAffirmList}" var="item" varStatus="i">			
				<tr>
					<td class='td_center'>${item.EMPID}</td>
					<td class='td_center'>${item.LOCAL_NAME}</td>
					<td class='td_center'>${item.LEAVE_FROM_TIME}</td>
					<td class='td_center'>${item.LEAVE_TO_TIME}</td>
					<td class='td_center'>${item.APPLY_LENGTH_DISPLAY}</td>
					<td class='td_center'>${item.LEAVE_TYPE}</td>
					<td class='td_center'>${item.LEAVE_TOTAL}</td>
					<td class='td_center'>${item.LEAVE_USE}</td>
					<td class='td_center'>${item.LEAVE_SURPLUS}</td>
					<td style="text-align:left">${item.LEAVE_REASON}</td>
					<td style="text-align:left">
						<c:forEach items="${item.fileList}" var="file" varStatus="j">	
								&nbsp;&nbsp;<a href="/ess/infoApplyLeave/downloadFile?fileName=${file.FILE_URL }&file=${file.FILE_NAME}" >${file.FILE_NAME}</a>;
						</c:forEach>
					</td>
				</tr>			
			</c:forEach>			
		</tbody>
	</table>
	<c:set value="/ess/affirmLeaveApply/checkApplyCheckInfo" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>
</c:if>
<div class="pageContent" >
	<form id="checkApplyInfo" method="post" action="/ess/affirmLeaveApply/checkApplyInfo" class="pageForm required-validate" 
		onsubmit="return validateCallback(this, navTabAjaxDone)">
	<table class="user_table" width="100%" border="0" cellpadding="0" cellspacing="0" class="user_table margin_b">
		<c:if test="${infoApplyLeave.APPLY_TYPE eq 'PERSON'}">
			<tr>
				<td colspan="6">
					<table class="user_table" width="100%" border="0">
							<tr>
								<td class="td_title" style="text-align: center;width:12%;">社号/姓名</td>
								<td class="td_type" style="text-align:left;width:12%;">[${infoApplyLeave.EMPID}]${infoApplyLeave.LOCAL_NAME}</td>
								<td class="td_title" style="text-align: center;width:12%;">开始日期时间</td>
								<td class="td_type" style="text-align:left;width:12%;">${infoApplyLeave.LEAVE_FROM_TIME}&nbsp;${infoApplyLeave.FROMTIME}</td>
								<td class="td_title" style="text-align: center;width:12%;">结束日期时间</td>
								<td class="td_type" style="text-align:left;width:12%;">${infoApplyLeave.LEAVE_TO_TIME}&nbsp;${infoApplyLeave.TOTIME}</td>
								<td class="td_title" style="text-align: center;width:12%;">Leave时长</td>
								<td style="width:12%;">${infoApplyLeave.APPLY_LENGTH_DISPLAY}</td>
							</tr>
							<tr>
								<td class="td_title" style="text-align: center;width:12%;">考勤类型</td>
								<td class="td_type" style="text-align:left;width:12%;">${infoApplyLeave.APPLY_TYPE_NAME}</td>
								<td class="td_title" style="text-align: center">Leave/销假</td>
								<td class="td_type" style="text-align:left;width:12%;">
									<c:if test="${infoApplyLeave.APPLY_TYPE_NAME eq '销假' }">销假</c:if>
									<c:if test="${infoApplyLeave.APPLY_TYPE_NAME ne '销假' }">Leave</c:if>
								</td>
								<td class="td_title" style="text-align: center">附件</td>
								<td class="td_type" style="text-align:left;width:12%;">
									<c:forEach items="${infoApplyLeave.fileList}" var="file" varStatus="j">	
										<a href="/ess/infoApplyLeave/downloadFile?fileName=${file.FILE_URL }&file=${file.FILE_NAME}" >${file.FILE_NAME}</a>
									</c:forEach>
								</td>
								<td class="td_title" style="text-align: center">申请事由</td>
								<td  class="td_type" style="text-align:left;width:12%;">${infoApplyLeave.LEAVE_REASON}</td>
							</tr>
							<c:if test="${infoApplyLeave.LEAVE_TYPE_CODE eq '26' or infoApplyLeave.LEAVE_TYPE_CODE eq '18135'}">
								<tr>
								<td class="td_title" style="text-align: center;width:12%;">${infoApplyLeave.APPLY_TYPE_NAME}总天数</td>
								<td class="td_type" style="text-align:left;width:12%;">${infoApplyLeave.LEAVE_TOTAL}</td>
								<td class="td_title" style="text-align: center">已使用${infoApplyLeave.APPLY_TYPE_NAME}天数</td>
								<td class="td_type" style="text-align:left;width:12%;">${infoApplyLeave.LEAVE_USE}</td>
								<td class="td_title" style="text-align: center">剩余${infoApplyLeave.APPLY_TYPE_NAME}天数</td>
								<td class="td_type" style="text-align:left;width:12%;">${infoApplyLeave.LEAVE_SURPLUS}</td>
								<td class="td_title" style="text-align: center"></td>
								<td  class="td_type" style="text-align:left;width:12%;"></td>
							</tr>
							</c:if>
					</table>
				</td>
			</tr>
		</c:if>
			<tr>
				<c:if test="${affirmorListCnt > 0}">
					<td class="td_title" style="text-align: center;width:10%;" rowspan="${affirmorListCnt+1 }"><!-- 审批线 -->
						审批线
					</td>
				</c:if>
				<c:if test="${affirmorListCnt == 0}">
					<td class="td_title" style="text-align: center;width:10%;" rowspan="${2 }"><!-- 审批线 -->
						审批线
					</td>
				</c:if>
				<td class="td_title" style="text-align: center;width:8%;"><!-- 审批等级 -->
					审批等级
				</td>
				<td class="td_title" style="text-align: center;width:15%;"><!-- 审批者 -->
					审批者
				</td>
				<td class="td_title" style="text-align: center;width:12%;"><!-- 审批情况 -->
					审批情况
				</td>
				<td class="td_title" style="text-align: center;width:15%;"><!-- 审批时间 -->
					审批时间
				</td>
				<td class="td_title" style="text-align: center;width:40%;"><!-- 审批批注 -->
					审批批注
				</td>
			</tr>
			<c:forEach items="${affirmorList}" var="affirmor" varStatus="i">			
				<tr>
					<td class="td_type" style="text-align: center">${affirmor.AFFIRM_LEVEL}</td>
					<td class="td_type" style="text-align: center">[${affirmor.EMPID}]${affirmor.LOCAL_NAME}</td>
					<td class="td_type" style="text-align: center">
						<c:if test="${affirmor.AFFIRM_FLAG eq '0'}">
							<!--<font color="blue">未审批</font>-->
							未审批
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
					<td class="td_type" style="text-align: center">${affirmor.AFFIRM_DATE}</td>
					<td class="td_type" style="text-align: center">${affirmor.AFFIRM_CONTENT}</td>
				</tr>			
			</c:forEach>
			<tr>
				<c:if test="${checkorListCnt > 0}">
					<td class="td_title" style="text-align: center" rowspan="${checkorListCnt+1 }"><!-- Review -->
						Review
					</td>
				</c:if>
				<c:if test="${checkorListCnt == 0}">
					<td class="td_title" style="text-align: center" rowspan="${2 }"><!-- Review -->
						Review
					</td>
				</c:if>
				<td class="td_title" style="text-align: center"><!-- Type -->
					Type
				</td>
				<td class="td_title" style="text-align: center" colspan="3"><!-- Requests -->
					Requests
				</td>
				<td class="td_title" style="text-align: center"><!-- Reviewed -->
					Reviewed
				</td>
			</tr>
			
			<c:forEach items="${checkorList}" var="checkor" varStatus="i">			
				<tr>
					<td class="td_type"style="text-align: center">Public</td>
					<td class="td_type"colspan="3">
						[${checkor.AFFIRM_EMPID}]${checkor.AFFIRM_NAME}&nbsp;&nbsp;&nbsp;${checkor.AFFIRM_POSITION}
						&nbsp;&nbsp;(${checkor.AFFIRM_DEPT_NAME})&nbsp;/&nbsp;${checkor.CHECK_CREATE_DATE}&nbsp;<br/>
						[Request]：${checkor.CHECK_REASON}
					</td>
					<td class="td_type">
						[${checkor.CHECK_EMPID}]${checkor.CHECK_NAME}&nbsp;&nbsp;&nbsp;${checkor.CHECK_POSITION}
						&nbsp;&nbsp;(${checkor.CHECK_DEPT_NAME})&nbsp;&nbsp;/&nbsp;
										<c:if test="${checkor.CHECK_FLAG eq '0'}">
											未check 
										</c:if>
										<c:if test="${checkor.CHECK_FLAG ne '0'}">
										${checkor.CHECKED_DATE }
										</c:if><br/>
						<c:if test="${checkor.ESS_CHECK_NO eq essCheckNo}">
							[Check]：<input id="CHECK_CONTENT" name="CHECK_CONTENT" size="55" maxlength="200"/>
							<input type="hidden" id="ESS_CHECK_NO" name="ESS_CHECK_NO" value="${checkor.ESS_CHECK_NO }"/>
							<input id="PERSON_ID" name="PERSON_ID" type="hidden" value="${PERSON_ID }" />
							<input id="APPLY_NO" name="APPLY_NO" type="hidden" value="${infoApplyLeave.APPLY_NO}" />
							<input id="APPLY_TYPE" name="APPLY_TYPE" type="hidden" value="21" />
						</c:if>
						<c:if test="${checkor.ESS_CHECK_NO ne essCheckNo}">
							[Check]：${checkor.CHECK_CONTENT}
						</c:if>
					</td>
				</tr>	
			</c:forEach>
			<c:if test="${checkorListCnt == 0}">
				<tr>
					<td class="td_type">Public</td>
					<td class="td_type" colspan="3">无</td>
					<td class="td_type">无</td>
				</tr>
			</c:if>
		</table>
		<div class="formBar">
			<ul>
				<c:if test="${is_check eq '1' }">
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
								<spring:message code="public.title.cancle"/><!-- 关闭 -->
							</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</form>
</div>