<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
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
	<c:set value="/ess/infoApplyLeave/viewFullApplyBatchAffirmInfo?APPLY_NO=${APPLY_NO}" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>
<div class="pageContent" >
	<table class="user_table" width="100%" border="0">
		<tr>
			<c:if test="${affirmorListCnt > 0}">
				<td class="td_title" style="text-align: center" rowspan="${affirmorListCnt+1 }"><!-- 决裁线 -->
					决裁线
				</td>
			</c:if>
			<c:if test="${affirmorListCnt == 0}">
				<td class="td_title" style="text-align: center" rowspan="${2 }"><!-- 决裁线 -->
					决裁线
				</td>
			</c:if>
			<td class="td_title" style="text-align: center"><!-- 决裁等级 -->
				决裁等级
			</td>
			<td class="td_title" style="text-align: center"><!-- 决裁者 -->
				决裁者
			</td>
			<td class="td_title" style="text-align: center"><!-- 决裁情况 -->
				决裁情况
			</td>
			<td class="td_title" style="text-align: center"><!-- 审批时间 -->
				审批时间
			</td>
			<td class="td_title" style="text-align: center"><!-- 决裁批注 -->
				决裁批注
			</td>
		</tr>
		<c:forEach items="${affirmorList}" var="affirmor" varStatus="i">			
			<tr>
				<td class="td_type" style="text-align: center">${affirmor.AFFIRM_LEVEL}</td>
				<td class="td_type" style="text-align: center">[${affirmor.EMPID}]${affirmor.LOCAL_NAME}</td>
				<td class="td_type" style="text-align: center">
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
				<td class="td_type" style="text-align: center">${affirmor.AFFIRM_DATE}</td>
				<td class="td_type" style="text-align: center">${affirmor.AFFIRM_CONTENT}</td>
			</tr>			
		</c:forEach>
		<tr>
			<c:if test="${checkorListCnt > 0}">
				<td class="td_title" style="text-align: center" rowspan="${checkorListCnt*2+1 }"><!-- Review -->
					Review
				</td>
			</c:if>
			<c:if test="${checkorListCnt == 0}">
				<td class="td_title" style="text-align: center" rowspan="${3 }"><!-- Review -->
					Review
				</td>
			</c:if>
			<td class="td_title" style="text-align: center"><!-- Type -->
				Type
			</td>
			<td class="td_title" style="text-align: center" colspan="2"><!-- Requests -->
				Requests
			</td>
			<td class="td_title" style="text-align: center" colspan="2"><!-- Reviewed -->
				Reviewed
			</td>
		</tr>
		<c:forEach items="${checkorList}" var="checkor" varStatus="i">			
			<tr>
				<td class="td_type" style="text-align: center">Public</td>
				<td class="td_type" colspan="2">
					[${checkor.AFFIRM_EMPID}]${checkor.AFFIRM_NAME}&nbsp;${checkor.AFFIRM_POSITION}
					&nbsp;&nbsp;(${checkor.AFFIRM_DEPT_NAME})&nbsp;/&nbsp;${checkor.AFFIRM_DATE}&nbsp;<br>
					[Request]：${checkor.CHECK_REASON}
				</td>
				<td class="td_type" colspan="2">
					[${checkor.CHECK_EMPID}]${checkor.CHECK_NAME}&nbsp;${checkor.CHECK_POSITION}
					&nbsp;&nbsp;(${checkor.CHECK_DEPT_NAME})&nbsp;/&nbsp;${checkor.CHECK_DATE}&nbsp;<br>
					[Check]：${checkor.CHECK_CONTENT}
				</td>
			</tr>	
		</c:forEach>	
		<c:if test="${checkorListCnt == 0}">
			<tr>
				<td class="td_type" style="text-align: center">Public</td>
				<td class="td_type" style="text-align: left" colspan="2">
					无
				</td>
				<td class="td_type" style="text-align: left" colspan="2">
					无
				</td>
			</tr>	
		</c:if>	
	</table>
</div>