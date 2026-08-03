<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
</script>
	<div class="formBar"> 
		<ul>
			<li>
				<div class="button">
					<div class="buttonContent">
						<button type="button" class="close">
							关闭
						</button>
					</div>
				</div>
			</li>
		</ul>
	</div>
<div class="pageContent" sysLong='printDiv' layoutH="50"> 
		<div style="padding-left:20px;padding-right:20px;">
		<br/><font style="color: blue;">已确认</font><br/>
			<table class="user_table"  width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td class="td_title" style="text-align: center;width:5%;"> 
						NO.
					</td> 
					<td class="td_title" style="text-align: center;width:10%;"> 
						<!--考勤状态 --><spring:message code="ess.infoApply.attendState" />
					</td>
					<td class="td_title" style="text-align: center;width:20%;"> 
						<!--考勤时长 --><spring:message code="ess.infoApply.attendance_time" />
					</td>
					<td class="td_title" style="text-align: center;width:20%;"> 
						<!--日期 --><spring:message code="org.title.DATE" />
					</td>
					<td class="td_title" style="text-align: center;width:20%;"> 
						<!--开始时间 --><spring:message code="ess.infoApply.title.startTime" />
					</td>
					<td class="td_title" style="text-align: center;width:20%;"> 
						<!--结束时间 --><spring:message code="ess.infoApply.end_time" />
					</td>
					<td class="td_title" style="text-align: center;width:20%;"> 
						<!--考勤状态 --><spring:message code="ess.infoApply.localyn" />
					</td>
				</tr>
					<c:forEach items="${leaveCoordList}" var="item" varStatus="i">			
						<tr>
							<td class="td_type" style="text-align: center">${i.count}</td> 
							<td class="td_type" style="text-align: center">${item.ITEM_NAME}</td>
							<td class="td_type" style="text-align: center">${item.QUANTITY}</td>
							<td class="td_type" style="text-align: center">${item.AR_DATE_STR}</td>
							<td class="td_type" style="text-align: center">${item.FROM_DATE}</td>
							<td class="td_type" style="text-align: center">${item.TO_DATE}</td>
							<td class="td_type" style="text-align: center">
								<c:if test="${item.LOCK_YN eq 'Y'}">
									已锁定
								</c:if>
								<c:if test="${item.LOCK_YN eq 'N'}">
									未锁定
								</c:if>
							</td>
						</tr>			
					</c:forEach>
			</table>
			<br/><font style="color: blue;">未确认</font><br/>
			<table class="user_table"  width="100%" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td class="td_title" style="text-align: center;width:5%;"> 
						NO.
					</td>
					<td class="td_title" style="text-align: center;width:10%;"> 
						<!--考勤状态 --><spring:message code="ess.infoApply.attendState" />
					</td>
					<td class="td_title" style="text-align: center;width:20%;"> 
						<!--考勤时长 --><spring:message code="ess.infoApply.attendance_time" />
					</td> 
					<td class="td_title" style="text-align: center;width:20%;"> 
						<!--开始时间 --><spring:message code="ess.infoApply.title.startTime" />
					</td>
					<td class="td_title" style="text-align: center;width:20%;"> 
						<!--结束时间 --><spring:message code="ess.infoApply.end_time" />
					</td>
					<td class="td_title" style="text-align: center;width:20%;"> 
						审批状态
					</td>
				</tr>
					<c:forEach items="${leaveInfoList}" var="item" varStatus="i">			
						<tr>
							<td class="td_type" style="text-align: center">${i.count}</td> 
							<td class="td_type" style="text-align: center">${item.LEAVE_TYPE_CODE}</td>
							<td class="td_type" style="text-align: center">${item.APPLY_LENGTH}</td> 
							<td class="td_type" style="text-align: center">${item.FROM_DATE}</td>
							<td class="td_type" style="text-align: center">${item.TO_DATE}</td>
							<td class="td_type" style="text-align: center">${item.AFFIRM_FLAG}</td>
						</tr>			
					</c:forEach>
			</table>
		</div>
	</div>
</div>