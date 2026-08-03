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


		<c:if test="${infoApplyOt.APPLY_TYPE eq 'BATCH' && infoApplyOt.OT_TIME_TYPE eq 'P' }">
<div class="pageContent">
	<table class="table" width="100%" layoutH="240" nowrapTD="false">
		<thead>
			<tr>
			 
				
				<th>社号</th>
				<th>姓名</th>
				<th>加班开始时间</th>
				<th>加班结束时间</th>
				<th>加班类型</th>
				<th>加班时长</th>
				<th>加班原因</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${OtBatchAffirmList}" var="item" varStatus="i">			
				<tr>
					<td class='td_center'>${item.EMPID}</td>
					<td class='td_center'>${item.LOCAL_NAME}</td>
					<td class='td_center'>${item.OT_FROM_TIME}</td>
					<td class='td_center'>${item.OT_TO_TIME}</td>
					<td class='td_center'>${item.OT_TYPE}</td>
					<td class='td_center'>${item.OT_LENGTH}</td>
					<td style="text-align:left">${item.APPLY_OT_REMARK}</td>
				</tr>			
			</c:forEach>			
		</tbody>
	</table>
	<!-- viewFullApplyCheckInfo 是批量审批查看的 -->
	<c:set value="/ess/affirmApply/viewFullApplyCheckInfoList?APPLY_NO=${infoApplyOt.APPLY_NO}" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>
</c:if>
			
		<c:if test="${infoApplyOt.APPLY_TYPE eq 'BATCH' && infoApplyOt.OT_TIME_TYPE eq 'L' }">
<div class="pageContent">
	<table class="table" width="100%" layoutH="240" nowrapTD="false">
		<thead>
			<tr>
			 
			<th>社号</th>
				<th>姓名</th>
				<th>加班日期</th>
				<th>加班长度</th>
				<th>加班类型</th>
				<th>加班原因</th>
			</tr>
		</thead>
		<tbody>
	<c:forEach items="${OtBatchAffirmList}" var="item" varStatus="i">			
				<tr>
					<td class='td_center'>${item.EMPID}</td>
					<td class='td_center'>${item.LOCAL_NAME}</td>
					<td class='td_center'>${item.APPLY_OT_DATE}</td>
					<td class='td_center'>${item.OT_APPLY_HOUR}</td>
					<td class='td_center'>${item.OT_TYPE}</td>
					<td style="text-align:left">${item.APPLY_OT_REMARK}</td>
				</tr>			
			</c:forEach>		
		</tbody>
	</table>
	<c:set value="/ess/affirmApply/viewFullApplyCheckInfoList?APPLY_NO=${infoApplyOt.APPLY_NO}" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>
</c:if>
<div class="pageContent" >
	<form id="checkApplyInfo" method="post" action="/ess/affirmApply/checkApplyInfo" class="pageForm required-validate" 
		onsubmit="return validateCallback(this, dialogAjaxDone)">
	
	 
		
		 <table class="user_table" width="100%">	
			<c:if test="${infoApplyOt.OT_TIME_TYPE eq 'L' &&  infoApplyOt.APPLY_TYPE eq 'PERSON'}"> 
				<tr>
					<td colspan="8">
						<table class="user_table" width="100%" border="0">
							<tr>
								<td class="td_title" style="text-align: center">社号|姓名</td>
								<td>[${infoApplyOt.EMPID}]${infoApplyOt.LOCAL_NAME}</td>
								<td class="td_title" style="text-align: center">部门</td>
								<td>${infoApplyOt.DEPT_NAME} </td>
								<td class="td_title" style="text-align: center">加班日期</td>
								<td>${infoApplyOt.APPLY_OT_DATE}</td>
								<td class="td_title" style="text-align: center">加班长度</td>
								<td>${infoApplyOt.OT_APPLY_HOUR}&nbsp;&nbsp;&nbsp;&nbsp;</td>
								
							</tr>
							<tr>
								<td class="td_title" style="text-align: center">考勤类型</td>
								<td>${infoApplyOt.OT_TYPE_NAME}</td>
								<td class="td_title" style="text-align: center">申请事由</td>
								<td colspan="7">${infoApplyOt.APPLY_OT_REMARK}</td>
							</tr>
							<tr><td></td></tr>
						</table>
					</td>
				</tr>
			</c:if>
			<c:if test="${infoApplyOt.OT_TIME_TYPE eq 'P'  &&  infoApplyOt.APPLY_TYPE eq 'PERSON'}">
				<tr>
					<td colspan="8">
						<table class="user_table" width="100%" border="0">
							<tr>
								<td class="td_title" style="text-align: center">社号|姓名</td>
								<td>[${infoApplyOt.EMPID}]${infoApplyOt.LOCAL_NAME}</td>
								<td class="td_title" style="text-align: center">部门</td>
								<td>${infoApplyOt.DEPT_NAME} </td>
								<td class="td_title" style="text-align: center">加班开始时间</td>
								<td>${infoApplyOt.OT_FROM_TIME}</td>
								<td class="td_title" style="text-align: center">加班结束时间</td>
								<td>${infoApplyOt.OT_TO_TIME}&nbsp;&nbsp;&nbsp;&nbsp;</td>
								
							</tr>
							<tr>
								<td class="td_title" style="text-align: center">考勤类型</td>
								<td>${infoApplyOt.OT_TYPE_NAME}</td>
									<td class="td_title" style="text-align: center">考勤类型</td>
								<td>${infoApplyOt.OT_LENGTH}</td>
								<td class="td_title" style="text-align: center">申请事由</td>
								<td colspan="5">${infoApplyOt.APPLY_OT_REMARK}</td>
							</tr>
							<tr><td></td></tr>
						</table>
					</td>
				</tr>
			</c:if>
			
			<tr>
				<td class="td_title" style="text-align: center">申请对象</td>
				<td colspan="5" class="td_type">${names }</td>
			</tr>
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
			<c:forEach items="${affirmorList}" var="affirmor" varStatus="i">			
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
					<td class="td_type" width="15%" style="text-align: center">	<c:if test="${affirmor.AFFIRM_FLAG eq '1'}">${affirmor.AFFIRM_DATE}</c:if></td>
					<td class="td_type" width="30%" style="text-align: center">${affirmor.AFFIRM_CONTENT}</td>
				</tr>			
			</c:forEach>
			
			<tr>
				<td class="td_title" colspan="6"><br/></td>
			</tr>
			<tr>
				<c:if test="${checkorListCnt > 0}">
					<td class="td_title" width="10%" style="text-align: center" rowspan="${checkorListCnt*2+1 }"><!-- Review -->
						Review
					</td>
				</c:if>
				<c:if test="${checkorListCnt == 0}">
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
			
			<c:forEach items="${checkorList}" var="checkor" varStatus="i">			
				<tr>
					<td class="td_type" width="15%" style="text-align: center" rowspan="2">Public</td>
					<td class="td_type" width="30%" style="text-align: center" colspan="2">
						[${checkor.AFFIRM_EMPID}]${checkor.AFFIRM_NAME}&nbsp;&nbsp;&nbsp;${checkor.AFFIRM_POSITION}
						&nbsp;&nbsp;(${checkor.AFFIRM_DEPT_NAME})&nbsp;/&nbsp;${checkor.AFFIRM_DATE}&nbsp;
						<c:if test="${checkor.AFFIRM_FLAG == 0}">
							未决裁
						</c:if>
						<c:if test="${checkor.AFFIRM_FLAG == 1}">
							已通过
						</c:if>
						<c:if test="${checkor.AFFIRM_FLAG == 2}">
							已否决
						</c:if>
					</td>
					<td class="td_type" width="45%" style="text-align: center" colspan="2">
						[${checkor.CHECK_EMPID}]${checkor.CHECK_NAME}&nbsp;&nbsp;&nbsp;${checkor.CHECK_POSITION}
						&nbsp;&nbsp;(${checkor.CHECK_DEPT_NAME})&nbsp;/&nbsp;${checkor.CHECK_DATE}&nbsp;
						<c:if test="${checkor.CHECK_FLAG == 0}">
							未Check
						</c:if>
						<c:if test="${checkor.CHECK_FLAG == 1}">
							已Check
						</c:if>
					</td>
				</tr>	
				<tr>
					<td class="td_type" width="30%" colspan="2">
						<textarea name="affirmRemark" cols="75" rows="2" disabled="disabled">[Request]：${checkor.CHECK_REASON}</textarea>
					</td>
					<td class="td_type" width="45%" colspan="2">
						<input type="hidden" id="ESS_CHECK_NO" name="ESS_CHECK_NO" value="${checkor.ESS_CHECK_NO }"/>
						<input type="hidden" id="ESS_AFFIRM_NO" name="ESS_AFFIRM_NO" value="${checkor.ESS_AFFIRM_NO }"/>
						[Check]：${checkor.CHECK_CONTENT}
					</td>
				</tr>			
			</c:forEach>
			<c:if test="${checkorListCnt == 0}">
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
			<div class="formBar">
			<ul><%-- 
				<li>
					<div class="button">
						<div class="buttonContent"><!--提交-->
							<button type="submit">
								提交
							</button>
						</div>
					</div>
				</li>--%>
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