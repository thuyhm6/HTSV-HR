<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<c:if test="${applyorInfo.BATCH_YN eq 'Y'}">
<div class="pageContent">
	<table class="table" width="100%" layoutH="240" nowrapTD="false">
		<thead>
			<tr>
				<th>社号</th>
				<th>姓名</th>
				<th>考勤日期</th>
				<th>开始时间</th>
				<th>结束时间</th>
				<th>考勤类型</th>
				<th>备注</th>
				<th>附件</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${arCwaBatchAffirmList}" var="item" varStatus="i">			
				<tr>
					<td class='td_center'>${item.EMPID}</td>
					<td class='td_center'>${item.LOCAL_NAME}</td>
					<td class='td_center'>${item.AR_DATE_STR}</td>
					<td class='td_center'>${item.FROM_TIME}</td>
					<td class='td_center'>${item.TO_TIME}</td>
					<td class='td_center'>正常出勤转旷工</td>
					<td style="text-align:left">${item.APPLY_REASON}</td>
					<td style="text-align:left;width:16%" id="arCwaBatchUpload_${i.index}">
							<c:forEach items="${item.fileList}" var="file" varStatus="j">	
								&nbsp;&nbsp;<a href="/ess/infoApplyLeave/downloadFile?fileName=${file.FILE_URL }&file=${file.FILE_NAME}" >${file.FILE_NAME}</a>;
							</c:forEach>
					</td>
				</tr>			
			</c:forEach>			
		</tbody>
	</table>
	<c:set value="/ess/infoApply/viewFullCwaApplyAffirmInfo?seach_APPLY_TYPE_NO=218197&seach_APPLY_NO=${applyorInfo.APPLY_NO}" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>
</c:if>
<div class="pageContent" >
	<c:if test="${applyorInfo.BATCH_YN eq 'N'}">
		<table class="user_table" width="100%" border="0">
							<tr>
								<td class="td_title" style="text-align: center;width:12%;">社号/姓名</td>
								<td class="td_type" style="text-align:left;width:12%;">[${applyorInfo.EMPID}]${applyorInfo.LOCAL_NAME}</td>
								<td class="td_title" style="text-align: center;width:12%;">考勤日期</td>
								<td class="td_type" style="text-align:left;width:12%;">${applyorInfo.AR_DATE_STR}</td>
								<td class="td_title" style="text-align: center;width:12%;">考勤类型</td>
								<td class="td_type" style="text-align:left;width:12%;">${applyorInfo.ITEMNAME}</td>
								<td class="td_title" style="text-align: center;width:12%;">申请类型</td>
								<td class="td_type" style="text-align:left;width:12%;">
								<font color="red">
									<c:if test="${applyorInfo.ITEM_NO eq '141439'}">
										正常出勤 (转) 旷工
									</c:if>
									<c:if test="${applyorInfo.ITEM_NO ne '141439'}">
										${applyorInfo.ITEMNAME} (转) 正常出勤
									</c:if>
								</font>
								</td>
							</tr>
							<tr>
								<td class="td_title" style="text-align: center">申请事由</td>
								<td  class="td_type" style="text-align:left;" colspan="7">${applyorInfo.APPLY_REASON}</td>
							</tr>
						</table>
	</c:if>
	<table class="user_table" width="100%" border="0">
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
				<td class="td_type" style="text-align: center">${affirmor.AFFIRM_LEVEL}</td>
				<td class="td_type" style="text-align: center">[${affirmor.EMPID}]${affirmor.LOCAL_NAME}<c:if test="${defaultCpny eq 'SST'}">/${affirmor.POSITION_NO }</c:if></td>
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
				<td class="td_type" colspan="2">
					[${checkor.AFFIRM_EMPID}]${checkor.AFFIRM_NAME}&nbsp;${checkor.AFFIRM_POSITION}
					&nbsp;&nbsp;(${checkor.AFFIRM_DEPT_NAME})&nbsp;/&nbsp;${checkor.AFFIRM_DATE}<br>
					[Request]：${checkor.CHECK_REASON}
				</td>
				<td class="td_type" colspan="2">
					[${checkor.CHECK_EMPID}]${checkor.CHECK_NAME}&nbsp;${checkor.CHECK_POSITION}
					&nbsp;&nbsp;(${checkor.CHECK_DEPT_NAME})&nbsp;/&nbsp;${checkor.CHECK_DATE}
					<c:if test="${checkor.CHECK_FLAG == 0}">
						未Check
					</c:if>
					<c:if test="${checkor.CHECK_FLAG == 1}">
						已Check
					</c:if><br/>
					[Check]：${checkor.CHECK_CONTENT}
				</td>
			</tr>			
		</c:forEach>	
		
		<c:if test="${checkorListCnt == 0}">
			<tr>
				<td class="td_title" style="text-align: center">Public</td>
				<td class="td_type" style="text-align: left" colspan="2">
					无
				</td>
				<td class="td_type" style="text-align: left" colspan="2">
					无
				</td>
			</tr>		
		</c:if>	
	</table>
	
	<div class="formBar">
		<ul>
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
</div>