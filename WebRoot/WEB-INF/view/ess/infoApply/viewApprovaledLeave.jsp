<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function submitAffirmPro(flag){
	var isHtml = $("#isHtml_leave").val();
	$("#AFFIRM_FLAG",$.pdialog.getCurrent()).val(flag);
  	var $form = $("#viewApprovaledLeave_form");
	$.ajax({
  		type: 'POST',
  		url:$form.attr("action"),
  		data:$form.serializeArray(),
  		dataType:"json",
  		cache: false,
  		success: function(){
  			if(isHtml == 'main'){
  				$.pdialog.closeCurrent();
  			}else{
  				$.pdialog.closeCurrent();
  				navTabSearch($("#viewApprovaledLeave_form"));
  			}
  		} ,
  		error: DWZ.ajaxError
  	});
}
</script>
<c:if test="${empty currentAffirmor}">
	<div class="formBar">
		<ul>
			<li>
				<div class="button">
					<div class="buttonContent">
						<button type="button" onclick="printDialog()">
							<!--打印--><spring:message code="ess.infoApply.print" />
						</button>
					</div>
				</div>
			</li>
			<li>
				<div class="button">
					<div class="buttonContent">
						<button type="button" class="close">
							<!--关闭--><spring:message code="ess.infoApply.close" />
						</button>
					</div>
				</div>
			</li>
		</ul>
	</div>
</c:if>
<div class="pageContent" sysLong='printDiv' layoutH="50" style="text-align:left;">
	<div style="font:bold 14px/20px arial,sans-serif;text-align:center;width:100%;padding-top:20px;padding-bottom:20px;"><!--【考勤申请】 --><spring:message code="ess.infoApply.attendance_application" /></div>
	<form id="viewApprovaledLeave_form" method="post" action="/ess/infoApply/executeAffirm" class="pageForm required-validate">
		<div style="padding-left:20px;padding-right:20px;">
						<table class="user_table" width="100%" border="0">
							<tr>
								<td class="td_title" style="text-align:right;width:20%;"><!--标题 --><spring:message code="ess.title.Title" /></td>
								<td class="td_type" style="text-align:left;width:80%;">${viewAffirmList[0].TITLE}</td>
							</tr>
							<tr>
								<td class="td_title" style="text-align:right;width:20%;"><!--期案者 --><spring:message code="public.title.qianzhe" /></td>
								<td class="td_type" style="text-align:left;width:80%;">${viewAffirmList[0].APPLY_PERSON_INFO}</td>
							</tr>
						</table>
						<br></br>
						<table class="user_table"  width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td class="td_title" style="text-align: center;width:5%;"><!-- 决裁等级 -->
									<!-- 顺次 --><spring:message code="ess.infoApply.in_order" />
								</td>
								<td class="td_title" style="text-align: center;width:10%;"><!-- 决裁者 -->
									<!--审批区分--><spring:message code="ess.infoApply.approval_distinction" />
								</td>
								<td class="td_title" style="text-align: center;width:10%;"><!-- 决裁情况 -->
									<!--审批状态--><spring:message code="ess.infoApply.approval_status" />
								</td>
								<td class="td_title" style="text-align: center;width:10%;"><!-- 审批时间 -->
									<!--姓名--><spring:message code="ess.infoApply.NAME" />
								</td>
								<td class="td_title" style="text-align: center;width:25%;"><!-- 审批时间 -->
									<!--批注--><spring:message code="ess.infoApply.comment" />
								</td>
								<td class="td_title" style="text-align: center;width:20%;"><!-- 决裁批注 -->
									<!--处理事务时--><spring:message code="ess.infoApply.dealing_with_transactions" />
								</td>
								<td class="td_title" style="text-align: center;width:20%;"><!-- 决裁批注 -->
									<!--部门名--><spring:message code="ess.infoApply.DEPT_NAME" />
								</td>
							</tr>
								<c:forEach items="${viewAffirmList}" var="affirmor" varStatus="i">			
									<tr>
										<td class="td_type" style="text-align: center">${i.count}</td>
										<td class="td_type" style="text-align: center">
											<c:if test="${affirmor.AFFIRM_TYPE eq '1'}"><!--审核--><spring:message code="ess.viewAttendanceEx.SHENHE.b" /></c:if>
											<c:if test="${affirmor.AFFIRM_TYPE eq '2'}"><!--协议--><spring:message code="ess.viewAttendanceEx.XIEYI.b" /></c:if>
											<c:if test="${affirmor.AFFIRM_TYPE eq '3'}"><!--通告--><spring:message code="ess.viewAttendanceEx.TONGGAO.b" /></c:if>
											<c:if test="${affirmor.AFFIRM_TYPE eq '4'}"><!--企案--><spring:message code="ess.viewAttendanceEx.QIAN.b" /></c:if>
										</td>
										<td class="td_type" style="text-align: center">
											<c:choose>
												   <c:when test="${affirmor.AFFIRM_TYPE eq '3'}">  
												        <c:if test="${affirmor.AFFIRM_FLAG eq '0'}">
		                                                   <!--未通告--><spring:message code="ess.viewAttendanceEx.WEITONGGAO.b" />
		                                                </c:if>
		                                                <c:if test="${affirmor.AFFIRM_FLAG ne '0'}">
		                                                   <!-- 已通告--><spring:message code="ess.viewAttendanceEx.YITONGGAO.b" />
		                                                </c:if>     
												   </c:when>
												   <c:when test="${affirmor.AFFIRM_TYPE eq '4'}">  
												        <c:if test="${affirmor.AFFIRM_FLAG eq '2'}">
		                                                    <!-- 取消--><spring:message code="public.title.cancle" />
		                                                </c:if>
		                                                <c:if test="${affirmor.AFFIRM_FLAG ne '2'}">
		                                                    <!-- 提交--><spring:message code="public.title.submit" />
		                                                </c:if>  
												   </c:when>
												   <c:otherwise> 
												        <c:if test="${affirmor.AFFIRM_FLAG eq '0'}">
		                                                    <!--未审批--><spring:message code="ess.affirmApply.title.remark.weishenpi" />
		                                                </c:if>
		                                                <c:if test="${affirmor.AFFIRM_FLAG eq '1'}">
		                                                    <!-- 已通过--><spring:message code="ess.affirmApply.title.remark.yitongguo" />
		                                                </c:if>
		                                                <c:if test="${affirmor.AFFIRM_FLAG ne '1' && affirmor.AFFIRM_FLAG ne '0'}">
		                                                    <!--已否决--><spring:message code="ess.affirmApply.title.remark.yifoujue" />
		                                                </c:if>
												   </c:otherwise>
												</c:choose>
										</td>
										<td class="td_type" style="text-align: center">${affirmor.AFFIRM_NAME}</td>
										<td class="td_type" style="text-align: center">
											<c:if test="${currentAffirmor.SEQ eq affirmor.SEQ}">
												<input id="AFFIRM_FLAG" name="AFFIRM_FLAG" type="hidden" value="" />
												<input id="APPLY_NO" name="APPLY_NO" type="hidden" value="${affirmor.APPLY_NO }" />
												<input id="APPLY_FLAG" name="APPLY_FLAG" type="hidden" value="${affirmor.APPLY_FLAG }" />
												<input id="APPLY_TYPE" name="APPLY_TYPE" type="hidden" value="${affirmor.APPLY_TYPE }" />
												<input id="AFFIRM_LEVEL" name="AFFIRM_LEVEL" type="hidden" value="${affirmor.AFFIRM_LEVEL}" />
												<input id="AFFIRM_CONTENT" name="AFFIRM_CONTENT" type="text" value="" size="20"/>
											</c:if>
											<c:if test="${currentAffirmor.SEQ ne affirmor.SEQ}">
												${affirmor.AFFIRM_CONTENT}
											</c:if>
										</td>
										<td class="td_type" style="text-align: center">${affirmor.UPDATE_DATE}</td>
										<td class="td_type" style="text-align: center">${affirmor.DEPTNAME}</td>
									</tr>			
								</c:forEach>
						</table>
		</div>
		<c:if test="${not empty currentAffirmor}">
		<div class="formBar">
			<ul>
				<li>
					<div class="button">
						<div class="buttonContent"><!-- 通过 -->
							<button type="button" onclick="submitAffirmPro(1)">
								<!--通过--><spring:message code="ess.infoApply.adopt" />
							</button>
						</div>
					</div>
				</li>
				<li>
					<div class="button">
						<div class="buttonContent"><!-- 否决 -->
							<button type="button" onclick="submitAffirmPro(2)">
								<!--否决--><spring:message code="ess.infoApply.veto" />
							</button>
						</div>
					</div>
				</li>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" class="close">
								<!--关闭--><spring:message code="ess.infoApply.close" />
							</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
		</c:if>
	</form>
	<br></br>
	<div style="padding-left:20px;padding-right:20px;text-align:left;float:left;">
	<div style="font:bold 12px/20px arial,sans-serif;text-align:left;width:100%;"><!--申请者信息 --><spring:message code="ess.infoApply.applicant_information" /></div>
						<table class="user_table" width="70%" border="0">
							<tr>
								<td class="td_title" style="text-align:right;width:15%;"><!--姓名 --><spring:message code="ess.infoApply.NAME" /></td>
								<td class="td_type" style="text-align:left;width:35%;">${leaveApplyInfo.LOCAL_NAME}</td>
								<td class="td_title" style="text-align:right;width:15%;"><!--社号 --><spring:message code="ess.infoApply.EMPID" /></td>
								<td class="td_type" style="text-align:left;width:35%;">${leaveApplyInfo.EMPID}</td>
							</tr>
							<tr>
								<td class="td_title" style="text-align:right;width:15%;"><!--部门 --><spring:message code="ess.infoApply.DEPT" /></td>
								<td class="td_type" style="text-align:left;width:35%;">${leaveApplyInfo.DEPTNAME}</td>
								<td class="td_title" style="text-align:right;width:15%;"><!--职级 --><spring:message code="ess.infoApply.Rank" /></td>
								<td class="td_type" style="text-align:left;width:35%;">${leaveApplyInfo.POST_GRADE_NAME}</td>
							</tr>
						</table>
	<br></br>
	<c:if test="${leaveApplyInfo.LEAVE_TYPE_CODE eq '26'}">
		<div style="font:bold 12px/20px arial,sans-serif;text-align:left;width:100%;"><!--年假信息 --><spring:message code="ess.infoApply.year_leave_information" /></div>
							<table class="user_table" width="70%" border="0">
								<tr>
									<td class="td_title" style="text-align:center;width:10%;"><!--年假总数 --><spring:message code="ess.infoApply.sum_year_leave_days" /></td>
									<td class="td_title" style="text-align:center;width:10%;"><!--已使用 --><spring:message code="ess.infoApply.already_used" /></td>
									<td class="td_title" style="text-align:center;width:10%;"><!--剩余 --><spring:message code="ess.infoApply.Remainder" /></td>
								</tr>
								<tr>
									<td class="td_type" style="text-align:center;">
											${empVacInfo.TOT_VAC_CNT}
									</td>
									<td class="td_type" style="text-align:center;">
											${empVacInfo.USE_VAC}
									</td>
									<td class="td_type" style="text-align:center;">
											${empVacInfo.TOT_VAC_CNT - empVacInfo.USE_VAC}
									</td>
								</tr>
							</table>
		<br></br>
	</c:if>
	<div style="font:bold 12px/20px arial,sans-serif;text-align:left;width:100%;"><!--申请信息 --><spring:message code="ess.infoApply.application_information" /></div>
						<table class="user_table" width="100%" border="0">
							<tr>
								<td class="td_title" style="text-align:center;width:10%;"><!--考勤类型 --><spring:message code="ess.infoApply.attendance_type" /></td>
								<td class="td_title" style="text-align:center;width:10%;"><!--考勤开始时间 --><spring:message code="ess.infoApply.attendance_start_time" /></td>
								<td class="td_title" style="text-align:center;width:10%;"><!--考勤结束时间 --><spring:message code="ess.infoApply.attendance_end_time" /></td>
								<td class="td_title" style="text-align:center;width:10%;"><!--考勤时长 --><spring:message code="ess.infoApply.attendance_time" /></td>
								<td class="td_title" style="text-align:center;width:30%;"><!--原因 --><spring:message code="ess.infoApply.Reason" /></td>
								<td class="td_title" style="text-align:center;width:20%;"><!--确认状态--><spring:message code="ess.infoApply.confirm_status" /></td>
							</tr>
							<tr>
								<td class="td_type" style="text-align:center;">${leaveApplyInfo.LEAVE_TYPE_NAME}</td>
								<td class="td_type" style="text-align:center;">${leaveApplyInfo.LEAVE_FROM_TIME}</td>
								<td class="td_type" style="text-align:center;">${leaveApplyInfo.LEAVE_TO_TIME}</td>
								<td class="td_type" style="text-align:center;"><c:if test="${leaveApplyInfo.LEAVE_LENGTH ge leaveApplyInfo.DAY_HOURS }"><fmt:formatNumber type="number"  value="${leaveApplyInfo.LEAVE_LENGTH/leaveApplyInfo.DAY_HOURS + (leaveApplyInfo.LEAVE_LENGTH%leaveApplyInfo.DAY_HOURS == 0 ? 0 : -1)}" pattern="#" maxFractionDigits="0"/>&nbsp;<!-- 天--><spring:message code="ar.viewsummaryparameteritem.title.day"/></c:if>&nbsp;${leaveApplyInfo.LEAVE_LENGTH % leaveApplyInfo.DAY_HOURS}&nbsp;<!-- 小时--><spring:message code="ar.viewsummaryparameteritem.title.hour"/></td>
								<td class="td_type" style="text-align:center;">${leaveApplyInfo.APPLY_REMARK}</td>
								<td class="td_type" style="text-align:center;">${leaveApplyInfo.AFFIRM_FLAG_NAME}&nbsp;&nbsp;<c:if test="${leaveApplyInfo.CONFIRM_FLAG eq 0}"><spring:message code="ess.title.WEIQUEREN" /><!--人事未确认--></c:if><c:if test="${leaveApplyInfo.CONFIRM_FLAG eq 1}"><spring:message code="ess.title.RENSHITONGGUO" /><!--人事通过--></c:if><c:if test="${leaveApplyInfo.CONFIRM_FLAG eq 2}"><spring:message code="ess.title.RENSHIFOUJUE" /><!--人事否决--></c:if></td>
							</tr>
						</table>
	<br></br>
 	<div style="font:bold 12px/20px arial,sans-serif;text-align:left;width:100%;"><!--附件--><spring:message code="org.title.enclosure" /></div>
					<table class="user_table" width="100%" border="0">
							<tr>
								<td class="td_title" style="text-align:center;width:10%;">No</td>
								<td class="td_title" style="text-align:center;width:90%;"><!--附件--><spring:message code="org.title.enclosure" /></td>
							</tr>
							<c:forEach items="${leaveApplyInfo.fileList}" var="item" varStatus="i">
								<tr>
									<td class="td_type" style="text-align:center;">${i.count}</td>
									<td class="td_type" style="text-align:left;"><a href="/ess/infoApplyLeave/downloadFile?fileName=${item.FILE_URL }&file=${item.FILE_NAME}" >${item.FILE_NAME}</a></td>
								</tr>
							</c:forEach>
					</table>
</div> 
<input type="hidden" value="${isHtml}" id="isHtml_leave">
</div>