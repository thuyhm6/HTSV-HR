<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function submitAffirmPro(flag){
	
	$("#AFFIRM_FLAG",$.pdialog.getCurrent()).val(flag);
  	var $form = $("#viewApprovaledOt_form");
	$.ajax({
  		type: 'POST',
  		url:$form.attr("action"),
  		data:$form.serializeArray(),
  		dataType:"json",
  		cache: false,
  		success: dialogAjaxDoneWithForm,
  		error: DWZ.ajaxError
  	});
}
</script>
	<div class="formBar">
		<ul>
			<li>
				<div class="button">
					<div class="buttonContent">
						<button type="button" onclick="printDialog()">
							<!-- 打印 --><spring:message code="org.title.PRINT"/>
						</button>
					</div>
				</div>
			</li>
			<li>
				<div class="button">
					<div class="buttonContent">
						<button type="button" class="close">
							<!-- 关闭 --><spring:message code="ess.infoApply.close"/>
						</button>
					</div>
				</div>
			</li>
		</ul>
	</div>
<div class="pageContent" sysLong='printDiv' layoutH="50">
	<div style="font:bold 14px/20px arial,sans-serif;text-align:center;width:100%;padding-top:20px;padding-bottom:20px;">【<spring:message code="ess.infoApply.title.overtimeApply" />】</div><!-- 加班申请 -->
	<form id="viewApprovaledOt_form" method="post" action="/ess/infoApply/executeAffirm" class="pageForm required-validate">
		<div style="padding-left:20px;padding-right:20px;">
						<table class="user_table" width="100%" border="0">
							<tr>
								<td class="td_title" style="text-align:right;width:20%;"><!-- 标题 --><spring:message code="ess.title.Title"/></td>
								<td class="td_type" style="text-align:left;width:80%;">${viewAffirmList[0].TITLE}</td>
							</tr>
							<tr>
								<td class="td_title" style="text-align:right;width:20%;"><!-- 期案者 --><spring:message code="public.title.qianzhe"/></td>
								<td class="td_type" style="text-align:left;width:80%;">${viewAffirmList[0].APPLY_PERSON_INFO}</td>
							</tr>
						</table>
						<br></br>
						<table class="user_table"  width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td class="td_title" style="text-align: center;width:5%;"><!-- 决裁等级 -->
									<!-- 顺次 --><spring:message code="ess.infoApply.in_order"/>
								</td>
								<td class="td_title" style="text-align: center;width:10%;"><!-- 决裁者 -->
									<!-- 审批区分 --><spring:message code="ess.infoApply.approval_distinction"/>
								</td>
								<td class="td_title" style="text-align: center;width:10%;"><!-- 决裁情况 -->
									<!-- 审批状态 --><spring:message code="ess.infoApply.approval_status"/>
								</td>
								<td class="td_title" style="text-align: center;width:10%;"><!-- 审批时间 -->
									<!-- 姓名 --><spring:message code="org.title.LOCAL_NAME"/>
								</td>
								<td class="td_title" style="text-align: center;width:25%;"><!-- 审批时间 -->
									<!-- 批注 --><spring:message code="ess.infoApply.comment"/>
								</td>
								<td class="td_title" style="text-align: center;width:20%;"><!-- 决裁批注 -->
									<!-- 处理事务时 --><spring:message code="ess.infoApply.dealing_with_transactions"/>
								</td>
								<td class="td_title" style="text-align: center;width:20%;"><!-- 决裁批注 -->
									<!-- 部门名 --><spring:message code="ess.infoApply.DEPT_NAME"/>
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
										<td class="td_type" style="text-align: center">${affirmor.AFFIRM_CONTENT}</td>
										<td class="td_type" style="text-align: center">${affirmor.UPDATE_DATE}</td>
										<td class="td_type" style="text-align: center">${affirmor.DEPTNAME}</td>
									</tr>			
								</c:forEach>
						</table>
		</div>
	</form>
	<br></br>
	<div style="padding-left:20px;padding-right:20px;text-align:left;float:left;">
	<div style="font:bold 12px/20px arial,sans-serif;text-align:left;width:100%;"><!-- 申请者信息 --><spring:message code="ess.infoApply.applicant_information"/></div>
						<table class="user_table" width="70%" border="0">
							<tr>
								<td class="td_title" style="text-align:right;width:15%;"><!-- 姓名 --><spring:message code="org.title.LOCAL_NAME"/></td>
								<td class="td_type" style="text-align:left;width:35%;">${otApplyInfo.LOCAL_NAME}</td>
								<td class="td_title" style="text-align:right;width:15%;"><!-- 工号 --><spring:message code="ess.infoApply.EMP_ID"/></td>
								<td class="td_type" style="text-align:left;width:35%;">${otApplyInfo.EMPID}</td>
							</tr>
							<tr>
								<td class="td_title" style="text-align:right;width:15%;"><!-- 部门 --><spring:message code="ess.infoApply.DEPT"/></td>
								<td class="td_type" style="text-align:left;width:35%;">${otApplyInfo.DEPTNAME}</td>
								<td class="td_title" style="text-align:right;width:15%;"><!-- 职级 --><spring:message code="ess.infoApply.Rank"/></td>
								<td class="td_type" style="text-align:left;width:35%;">${otApplyInfo.POST_GRADE_NAME}</td>
							</tr>
						</table>
	<br></br>
	<div style="font:bold 12px/20px arial,sans-serif;text-align:left;width:100%;"><!-- 申请信息--><spring:message code="ess.infoApply.application_information"/></div>
						<table class="user_table" width="100%" border="0">
							<tr>
								<td class="td_title" style="text-align:center;width:10%;"><!-- 日期--><spring:message code="ar.viewardetail.title.date"/></td>
								<td class="td_title" style="text-align:center;width:10%;"><!-- 星期--><spring:message code="ess.infoApply.week"/></td>
								<td class="td_title" style="text-align:center;width:10%;"><!-- 加班类型--><spring:message code="ess.infoApply.overtime_type"/></td>
								<td class="td_title" style="text-align:center;width:10%;"><!-- 加班开始时间--><spring:message code="ess.infoApply.overtime_start_time"/></td>
								<td class="td_title" style="text-align:center;width:10%;"><!-- 结束时间--><spring:message code="ess.infoApply.end_time"/></td>
								<td class="td_title" style="text-align:center;width:10%;"><!-- 加班时长--><spring:message code="ess.infoApply.overtime_hours"/></td>
								<td class="td_title" style="text-align:center;width:10%;"><!-- 本年累计时长--><spring:message code="ess.viewSSTOtApplyInfo.BENNIANLEIJIJIABAN.b"/></td>
								<td class="td_title" style="text-align:center;width:10%;"><!-- 本月累计时长--><spring:message code="ess.viewSSTOtApplyInfo.BENYUELEIJIJIABAN.b"/></td>
								<td class="td_title" style="text-align:center;width:30%;"><!-- 原因--><spring:message code="ess.infoApply.Reason"/></td>
							</tr>
							<tr>
								<td class="td_type" style="text-align:center;">${otApplyInfo.APPLY_OT_DATE}</td>
								<td class="td_type" style="text-align:center;">
								<c:if test="${otApplyInfo.IWEEK eq '1'}"><!-- 星期日 --><spring:message code="ar.week.XINGQIRI.b"/></c:if>
		                        <c:if test="${otApplyInfo.IWEEK eq '2'}"><!-- 星期一 --><spring:message code="ar.week.XINGQIYI.b"/></c:if>
		                        <c:if test="${otApplyInfo.IWEEK eq '3'}"><!-- 星期二 --><spring:message code="ar.week.XINGQIER.b"/></c:if>
		                        <c:if test="${otApplyInfo.IWEEK eq '4'}"><!-- 星期三 --><spring:message code="ar.week.XINGQISAN.b"/></c:if>
		                        <c:if test="${otApplyInfo.IWEEK eq '5'}"><!-- 星期四 --><spring:message code="ar.week.XINGQISI.b"/></c:if>
		                        <c:if test="${otApplyInfo.IWEEK eq '6'}"><!-- 星期五 --><spring:message code="ar.week.XINGQIWU.b"/></c:if>
		                        <c:if test="${otApplyInfo.IWEEK eq '7'}"><!-- 星期六 --><spring:message code="ar.week.XINGQILIU.b"/></c:if>
								</td>
								<td class="td_type" style="text-align:center;">${otApplyInfo.OT_TYPE_NAME}</td>
								<td class="td_type" style="text-align:center;">${otApplyInfo.OT_FROM_TIME}</td>
								<td class="td_type" style="text-align:center;">${otApplyInfo.OT_TO_TIME}</td>
								<td class="td_type" style="text-align:center;">${otApplyInfo.OT_LENGTH}<!--小时--><spring:message code="ar.viewsummaryparameteritem.title.hour"/></td>
								<td class="td_type" style="text-align:center;">${otApplyInfo.OT_LENGTH_OLD}<!--小时--><spring:message code="ar.viewsummaryparameteritem.title.hour"/></td>
								<td class="td_type" style="text-align:center;">${otApplyInfo.OT_LENGTH_MONTH_OLD}<!--小时--><spring:message code="ar.viewsummaryparameteritem.title.hour"/></td>
								<td class="td_type" style="text-align:left;">${otApplyInfo.APPLY_REMARK}</td>
							</tr>
						</table>
						<br></br>
		<div style="font:bold 12px/20px arial,sans-serif;text-align:left;width:100%;"><!-- Meal--><spring:message code="ess.viewSSTOtApplyInfo.DEDUCT_MEAL_TIME_YN.b"/></div>
						<table class="user_table" width="100%" border="0">
							<tr>
								<td class="td_title" style="text-align:center;" width="3%" >No.</td>
								<td class="td_title" style="text-align:center;" width="10%"><!-- 打卡日期 --><spring:message code="ar.viewArCardRecord.DAKARIQI.b"/></td>
								<td class="td_title" style="text-align:center;" width="10%"><!-- 打卡时间 --><spring:message code="ess.infoApply.card_clock_time"/></td> 
								<td class="td_title" style="text-align:center;" width="20%"><!-- Dish Name --><spring:message code="inct.salesman.eval.detail"/></td>
								<td class="td_title" style="text-align:center;" width="10%"><!-- Meal type --><spring:message code="inct.salesman.classify"/></td>
								<td class="td_title" style="text-align:center;" width="2%"><!-- Amount --><spring:message code="ess.viewApplyAttendanceBatchInfoDetail.SHULIANG.b"/></td>
							</tr>
							<c:forEach items="${getArCardRecordMealList}" var="list" varStatus="i">
								<tr target="SEQ" rel="${list.SEQ}">
									<td class="td_type" style="text-align:center;">${i.count}</td>
									<td class="td_type" style="text-align:center;">${list.R_DATE}</td> 
									<td class="td_type" style="text-align:center;">${list.R_TIME}</td>
									<td class="td_type" style="text-align:center;">${list.EAT_NAME}</td> 
									<td class="td_type" style="text-align:center;">${list.EAT_DATE}</td>
									<td class="td_type" style="text-align:center;">${list.AMOUNT}</td>
									
								</tr>
							</c:forEach>			
						</table>
	</div>
</div>