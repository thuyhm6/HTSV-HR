<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageContent" >
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
	<table class="user_table" width="100%" layoutH="60" border="0">
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
								<td>${infoApplyOt.OT_APPLY_HOUR}</td>
								
							</tr>
							<c:if test="${infoApplyOt.CPNY_ID eq 'TSTO' }">
							<tr>
							<td colspan="1" class="td_title" style="text-align: center">进门时间</td>
								<td colspan="3"><fmt:formatDate value="${infoApplyOt.INTIME}" pattern="yyyy-MM-dd HH:mm:ss" />&nbsp;&nbsp;&nbsp;&nbsp;</td>
								<td colspan="1" class="td_title" style="text-align: center">出门时间</td>
								<td colspan="3"><fmt:formatDate value="${infoApplyOt.OUTTIME}" pattern="yyyy-MM-dd HH:mm:ss" />&nbsp;&nbsp;&nbsp;&nbsp;</td>
							</tr>
							</c:if>
							<tr>
								<td class="td_title" style="text-align: center">考勤类型</td>
								<td>${infoApplyOt.OT_TYPE_NAME}</td>
								<td class="td_title" style="text-align: center">附件</td>
								<td>
									<c:forEach items="${infoApplyOt.fileList}" var="file" varStatus="j">	
										<a href="/ess/infoApplyLeave/downloadFile?fileName=${file.FILE_URL }&file=${file.FILE_NAME}" >${file.FILE_NAME}</a>
									</c:forEach>
								</td>
								<td class="td_title" style="text-align: center">申请事由</td>
								<td colspan="5">${infoApplyOt.APPLY_OT_REMARK}</td>
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
								<td>${infoApplyOt.OT_TO_TIME}</td>
							</tr>
							<c:if test="${infoApplyOt.CPNY_ID eq 'TSTO' }">
							<tr>
							<td colspan="1" class="td_title" style="text-align: center">进门时间</td>
								<td colspan="3" ><fmt:formatDate value="${infoApplyOt.INTIME}" pattern="yyyy-MM-dd HH:mm:ss" />&nbsp;&nbsp;&nbsp;&nbsp;</td>
								<td colspan="1" class="td_title" style="text-align: center">出门时间</td>
								<td colspan="3"><fmt:formatDate value="${infoApplyOt.OUTTIME}" pattern="yyyy-MM-dd HH:mm:ss" />&nbsp;&nbsp;&nbsp;&nbsp;</td>
							</tr>
							</c:if>
							<tr>
								<td class="td_title" style="text-align: center">考勤类型</td>
								<td>${infoApplyOt.OT_TYPE_NAME}</td>
								<td class="td_title" style="text-align: center">加班时长</td>
								<td>${infoApplyOt.OT_LENGTH}</td>
								<td class="td_title" style="text-align: center">申请事由</td>
								<td colspan="5">${infoApplyOt.APPLY_OT_REMARK}</td>
							</tr>
							<tr><td></td></tr>
						</table>
					</td>
				</tr>
			</c:if>
			<c:if test="${!empty applyorInfo}">
				<tr>
					<td colspan="8">
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
					</td>
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
				<td class="td_type" width="15%" style="text-align: center">${affirmor.AFFIRM_DATE}</td>
				<td class="td_type" width="30%" style="text-align: center">${affirmor.AFFIRM_CONTENT}</td>
			</tr>			
		</c:forEach>
		<c:if test="${affirmorListCnt == 0}">
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>		
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>		
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
			</tr>			
		</c:if>
		<tr>
			<c:if test="${checkorListCnt > 0}">
				<td class="td_title" width="10%" style="text-align: center" rowspan="${checkorListCnt+1 }"><!-- Review -->
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
				<td class="td_type" width="15%" style="text-align: center">Public</td>
				<td class="td_type" width="30%" colspan="2">
					[${checkor.AFFIRM_EMPID}]${checkor.AFFIRM_NAME}&nbsp;${checkor.AFFIRM_POSITION}
					&nbsp;&nbsp;(${checkor.AFFIRM_DEPT_NAME})&nbsp;/&nbsp;${checkor.AFFIRM_DATE}&nbsp;<br>
					[Request]：${checkor.CHECK_REASON}
				</td>
				<td class="td_type" width="45%" colspan="2">
					[${checkor.CHECK_EMPID}]${checkor.CHECK_NAME}&nbsp;${checkor.CHECK_POSITION}
					&nbsp;&nbsp;(${checkor.CHECK_DEPT_NAME})&nbsp;/&nbsp;${checkor.CHECK_DATE}&nbsp;<br>
					[Check]：${checkor.CHECK_CONTENT}
				</td>
			</tr>	
		</c:forEach>	
		
		<c:if test="${checkorListCnt == 0}">
			<tr>
				<td class="td_title" width="15%" style="text-align: center">Public</td>
				<td class="td_type" width="30%" colspan="2">
					无
				</td>
				<td class="td_type" width="45%" colspan="2">
					无
				</td>
			</tr>		
		</c:if>	
	</table>
</div>