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
<c:if test="${RecordInfo.BATCH_YN eq 'Y'}">
<div class="pageContent">
	<table class="table" width="100%" layoutH="240" nowrapTD="false">
		<thead>
			<tr>
				<th>社号</th>
				<th>姓名</th>
				<th>本月申请次数</th>
				<th>考勤日期</th>
				<th>打卡时间</th>
				<th>进出门类型</th>
				<th>备注</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${arMacBatchAffirmList}" var="item" varStatus="i">			
				<tr>
					<td class='td_center'>${item.EMPID}</td>
					<td class='td_center'>${item.LOCAL_NAME}</td>
					<td class='td_center'>${item.MAC_APPLY_CNT}</td>
					<td class='td_center'>${item.APPLY_DATE}</td>
					<td class='td_center'>${item.APPLY_TIME}</td>
					<td class='td_center'>${item.DOOR_TYPE}</td>
					<td style="text-align:left">${item.REMARK}</td>
				</tr>			
			</c:forEach>			
		</tbody>
	</table>
	<c:set value="/ess/recordApply/checkApplyCheckInfo?seach_APPLY_TYPE_NO=218294&seach_APPLY_NO=${RecordInfo.RECORD_NO}" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>
</c:if>
<div class="pageContent" >
	<form id="checkApplyInfo" method="post" action="/ess/recordApply/checkApplyInfo" class="pageForm required-validate" 
		onsubmit="return validateCallback(this, navTabAjaxDone)">
			<table class="user_table" width="100%">	
				<c:if test="${RecordInfo.BATCH_YN eq 'N'}">
				<tr style="text-align: center" >
					 
						 
							<td class="td_title" style="text-align: right;width:7%;" style="text-align: center">申请信息</td>
							<td colspan="7">
								<table class="user_table" width="100%" border="0">
									<tr>
										<td class="td_title" style="text-align: center">申请人</td>
										<td colspan="7">${RecordInfo.CREATED_BY}</td>
									</tr>
									<tr>
										<td class="td_title" style="text-align: center">申请日期</td>
										<td colspan="7">${RecordInfo.CREATE_DATE}</td>
									</tr>
									<tr>
										<td class="td_title" style="text-align: center;width:12%;">社号/姓名</td>
										<td style="width:12%;">[${RecordInfo.EMPID}]${RecordInfo.LOCAL_NAME}</td>
										<td class="td_title" style="text-align: center;width:12%;">部门</td>
										<td style="width:12%;">${RecordInfo.DEPT_NAME}</td>
										<td class="td_title" style="text-align: center;width:12%;">漏刷卡时间</td>
										<td style="width:12%;">${RecordInfo.R_TIME }</td>
										<td class="td_title" style="text-align: center;width:12%;">进出门类型</td>
										<td style="width:12%;">${RecordInfo.DOOR_TYPE}</td>
									</tr>
									
									<tr>
										<td class="td_title" style="text-align: center">附件</td>
										<td>
											<c:forEach items="${RecordInfo.fileList}" var="file" varStatus="j">	
											<a href="/ess/infoApplyLeave/downloadFile?fileName=${file.FILE_URL }&file=${file.FILE_NAME}" >${file.FILE_NAME}</a>
											</c:forEach>
										</td>
										<c:if test="${RecordInfo.CPNY_ID eq 'LGEPN'}">
										  	<td class="td_title" style="text-align: center">漏刷卡类型</td>
											<td>${RecordInfo.RECORD_TYPE }</td>	
										  	<td class="td_title" style="text-align: center">本月申请次数</td>
											<td>${RecordInfo.MAC_APPLY_CNT }</td>	
									     	<td class="td_title" style="text-align: center"  > 
														 申请事由
											</td>
											<td class="td_type"  style="text-align: left;"> 
														 ${RecordInfo.REMARK }
											</td>
										</c:if>
										<c:if test="${RecordInfo.CPNY_ID ne 'LGEPN'}">
										  	<td class="td_title" style="text-align: center">本月申请次数</td>
											<td>${RecordInfo.MAC_APPLY_CNT }</td>	
									     	<td class="td_title" style="text-align: center"  > 
														 申请事由
											</td>
											<td class="td_type"  style="text-align: left;"  colspan="3"> 
														 ${RecordInfo.REMARK }
											</td>
										</c:if>
									</tr>
				</table>
				</tr>
				</c:if>
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
				<td class="td_title" width="10%" style="text-align: center"><!-- 决裁等级 -->
					决裁等级
				</td>
				<td class="td_title" width="20%" style="text-align: center"><!-- 决裁者 -->
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
					<td class="td_type" width="10%" style="text-align: center">${affirmor.AFFIRM_LEVEL}</td>
					<td class="td_type" width="20%" style="text-align: center">[${affirmor.EMPID}]${affirmor.LOCAL_NAME}</td>
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
				<td class="td_title" width="9%" style="text-align: center"><!-- Type -->
					Type
				</td>
				<td class="td_title" width="40%" style="text-align: center" colspan="2"><!-- Requests -->
					Requests
				</td>
				<td class="td_title" width="40%" style="text-align: center" colspan="2"><!-- Reviewed -->
					Reviewed
				</td>
			</tr>
			
			<c:forEach items="${checkorList}" var="checkor" varStatus="i">			
				<tr>
					<td class="td_type" style="text-align: center">Public</td>
					<td class="td_type" style="text-align: left" colspan="2">
						[${checkor.AFFIRM_EMPID}]${checkor.AFFIRM_NAME}&nbsp;&nbsp;&nbsp;${checkor.AFFIRM_POSITION}
						&nbsp;&nbsp;(${checkor.AFFIRM_DEPT_NAME})&nbsp;/&nbsp;${checkor.AFFIRM_DATE}&nbsp;<br/>
						[Request]：${checkor.CHECK_REASON}
					</td>
					<td class="td_type" style="text-align: left" colspan="2">
						[${checkor.CHECK_EMPID}]${checkor.CHECK_NAME}&nbsp;&nbsp;&nbsp;${checkor.CHECK_POSITION}
						&nbsp;&nbsp;(${checkor.CHECK_DEPT_NAME})&nbsp;/&nbsp;${checkor.CHECK_DATE}&nbsp;
						<c:if test="${checkor.CHECK_FLAG == 0}">
							未Check
						</c:if>
						<c:if test="${checkor.CHECK_FLAG == 1}">
							已Check
						</c:if><br/>
						 <c:if test="${checkor.ESS_CHECK_NO eq essCheckNo}">
							<input type="hidden" id="ESS_CHECK_NO" name="ESS_CHECK_NO" value="${checkor.ESS_CHECK_NO }"/>
							<input type="hidden" id="ESS_AFFIRM_NO" name="ESS_AFFIRM_NO" value="${checkor.ESS_AFFIRM_NO }"/>
							<input type="hidden" id="APPLY_TYPE" name="APPLY_TYPE" value="218294"/>
							<input id="APPLY_NO" name="APPLY_NO" type="hidden" value="${RecordInfo.APPLY_NO}" />
							[Check]：<input id="CHECK_CONTENT" name="CHECK_CONTENT" size="55" maxlength="200"/>
						</c:if>
						<c:if test="${checkor.ESS_CHECK_NO ne essCheckNo}">
							[Check]：${checkor.CHECK_CONTENT}
						</c:if>
					</td>
				</tr>	
			</c:forEach>
			<c:if test="${checkorListCnt == 0}">
				<tr>
				<td class="td_type" style="text-align: center">Public</td>
					<td class="td_type" style="text-align: center" colspan="2">无</td>
					<td class="td_type" style="text-align: center" colspan="2">无</td>
				</tr>
			</c:if>
		</table>
		
		
		<div class="formBar">
			<ul>
					<li>
						<div class="button">
							<div class="buttonContent"><!--提交-->
								<button type="submit">
									提交
								</button>
							</div>
						</div>
					</li>
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