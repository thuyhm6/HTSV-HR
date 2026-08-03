<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
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
	<c:set value="/ess/affirmLeaveApply/viewLeaveApplyCheckInfo" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>
</c:if>
<div class="pageContent" >
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
								<td>${infoApplyLeave.APPLY_LENGTH_DISPLAY}</td>
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
			<td class="td_title" width="10%" style="text-align: center" rowspan="${affirmorListCnt + 1 }"><!-- 决裁线 -->
					决裁线
			</td>
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
				<td class="td_type" width="15%" style="text-align: center">[${affirmor.EMPID}]${affirmor.LOCAL_NAME}/${affirmor.POSITION_NO }</td>
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
		<c:if test="${affirmorListCnt == 0}">
								<tr>
									<td class="td_title" >&nbsp;</td>
									<td class="td_type" >&nbsp;</td>
									<td class="td_type" ></td>
									<td class="td_type" ></td>
									<td class="td_type" ></td>
									<td class="td_type" ></td>
								</tr>		
							</c:if>
		<tr>
					<td class="td_title" style="text-align: center" rowspan="${checkorListCnt + 1}">
						Check
					</td>
					<td colspan="5">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tbody>
							<tr>
								<td class="td_title" style="text-align: center">
									Type
								</td>
								<td class="td_title" style="text-align: center">
									Requests
								</td>
								<td class="td_title" style="text-align: center">
									Checked
								</td>
							</tr>
							<c:forEach items="${checkorList}" var="checkor" varStatus="i">
								<tr>
									<td class="td_type" style="text-align: center" width="10%">
										public
									</td>
									<td class="td_type" width="45%">
										[${checkor.AFFIRM_EMPID}]-${checkor.AFFIRM_NAME }&nbsp;&nbsp;${checkor.AFFIRM_POSITION}&nbsp;&nbsp;(${checkor.AFFIRM_DEPT_NAME })/${checkor.CHECK_CREATE_DATE }<br/>
										[Request]${checkor.CHECK_REASON}
									</td>
									<td class="td_type" width="45%">
										[${checkor.CHECK_EMPID}]-${checkor.CHECK_NAME }&nbsp;&nbsp;${checkor.CHECK_POSITION }&nbsp;&nbsp;(${checkor.CHECK_DEPT_NAME })&nbsp;&nbsp;/&nbsp;
										<c:if test="${checkor.CHECK_FLAG eq '0'}">
											未check 
										</c:if>
										<c:if test="${checkor.CHECK_FLAG ne '0'}">
										${checkor.CHECKED_DATE }
										</c:if>
										<br/>
										[Check]${checkor.CHECK_CONTENT}
									</td>
								</tr>
							</c:forEach>
							<c:if test="${checkorListCnt == 0}">
								<tr>
									<td class="td_title" style="text-align: center" width="10%">Public</td>
									<td class="td_type" width="45%">
										无
									</td>
									<td class="td_type" width="45%">
										无
									</td>
								</tr>		
							</c:if>
							</tbody>
						</table>
					</td>
				</tr>
	</table>
	<div class="formBar">
			<ul>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close"><spring:message code="public.title.cancle"/><!-- 取消 --></button></div></div>
				</li>
			</ul>
		</div>
</div>