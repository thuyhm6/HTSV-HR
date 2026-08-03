<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function submitAffirmPro(flag){
	var isHtml = $("#isHtml_leave").val();
	$("#AFFIRM_FLAG",$.pdialog.getCurrent()).val(flag);
  	var $form = $("#viewApprovaledBatchLeave_form");
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
  				navTabSearch($("#viewApprovaledBatchLeave_form"));
  			}
  		} ,
  		error: DWZ.ajaxError
  	});
}

function executeAffirmBatch(){
	//获取页面的值
	//var affirmContent = $("#AFFIRM_CONTENT",$.pdialog.getCurrent()).val();
	var jsonData = '[';
	$("input[name='index']",$.pdialog.getCurrent()).each(function(i, obj){
			if (jsonData.length > 1) {
				jsonData += ',{';
			} else {
				jsonData += '{';
			}
			var index = obj.id.substring(6);
			var person_id = $("#PERSON_ID_"+index,$.pdialog.getCurrent()).val();
			var affirm_flag = $("input[name='affirm_flag"+index+"']:checked",$.pdialog.getCurrent()).val();
			jsonData += obj.value;
			jsonData += ' "AFFIRM_FLAG": "' + affirm_flag + '" ,';
			//jsonData += ' "AFFIRM_CONTENT": "' + affirmContent + '" ,';
			jsonData += ' "adminID": "' + '${LoginUser.adminID}' + '" ,';
			jsonData += ' "adminIP": "' + '${LoginUser.adminIP}' + '" ,';
			jsonData += ' "interCpnyID": "' + '${LoginUser.cpnyId}' + '", ';
			jsonData += ' "PERSON_ID": "' + person_id + '" ';
			jsonData += '}';
	});
	jsonData += ']';
	if (jsonData.length == 2) {
		//请先选择要操作的数据
		alertMsg.info("<spring:message code='ess.infoApply.select_data_want_operate'/>");
		return;
	}
    //确定要执行此操作吗？
	alertMsg.confirm("<spring:message code='hrm.alert.empinfo.Perform_operation'/>",
  		{okCall:function(){
		  	$.ajax({
  				type: 'POST',
				url: '/ess/infoApply/executeBatchAffirm',
				data: [{ name: 'jsonData', value: jsonData }],
  				dataType:"json",
  				cache: false,
  				success: dialogAjaxDoneWithForm,
  				error: DWZ.ajaxError
  		});
  	}});
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
<div class="pageContent" sysLong='printDiv' layoutH="20" style="text-align:left;">
	<div style="font:bold 14px/20px arial,sans-serif;text-align:center;width:100%;padding-top:20px;padding-bottom:20px;"><!--【考勤申请】 --><spring:message code="ess.infoApply.attendance_application" /></div>
	<form id="viewApprovaledBatchLeave_form" method="post" action="/ess/infoApply/executeAffirm" class="pageForm required-validate">
		<div style="padding-left:20px;padding-right:20px;">
						<table class="user_table" width="100%" border="0">
							<tr>
								<td class="td_title" style="text-align:right;width:20%;"><!--待申请人 --><spring:message code="ar.viewLeaveConfirmList.DAISHENQINGREN.b" /></td>
								<td class="td_type" style="text-align:left;width:80%;">${viewAffirmList[0].CREATED_BY}</td>
							</tr>
							<tr>
								<td class="td_title" style="text-align:right;width:20%;"><!--申请日期 --><spring:message code="ess.empInfo.date_application" /></td>
								<td class="td_type" style="text-align:left;width:80%;">${viewAffirmList[0].APPLY_TIME}</td>
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
								<!--<td class="td_title" style="text-align: center;width:25%;"> 审批时间 
									批注<spring:message code="ess.infoApply.comment" />
								</td>-->
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
										<!--<td class="td_type" style="text-align: center">
											<c:if test="${currentAffirmor.SEQ eq affirmor.SEQ}">
												<input id="AFFIRM_FLAG" name="AFFIRM_FLAG" type="hidden" value="" />
												<input id="BATCH_NO" name="BATCH_NO" type="hidden" value="${affirmor.BATCH_NO }" />
												<input id="AFFIRM_CONTENT" name="AFFIRM_CONTENT" type="text" value="" size="20"/>
											</c:if>
											<c:if test="${currentAffirmor.SEQ ne affirmor.SEQ}">
												${affirmor.AFFIRM_CONTENT}
											</c:if>
										</td>-->
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
						<div class="buttonContent">
							<button type="button" onclick="executeAffirmBatch()">
								<!--审批--><spring:message code="hr.contract.title.shenpi" />
							</button>
						</div>
					</div>
				</li>
				<!--<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" onclick="executeAffirmBatch(2)">
								否决<spring:message code="ess.infoApply.veto" />
							</button>
						</div>
					</div>
				</li>-->
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
	<div style="padding-left:20px;padding-right:20px;text-align:left;float:left;">
    <div style="font:bold 12px/20px arial,sans-serif;text-align:left;width:100%;"><!--申请信息 --><spring:message code="ess.infoApply.application_information" /></div>
						<table class="user_table" width="100%" border="0">
							<tr>
							    <c:if test="${not empty currentAffirmor}">
							    <td style="text-align: center;width:10%;">
							        <!--操作 --><spring:message code="sys.affirm.title.affirmOperation" />
							        <!--<input type="checkbox" class="checkboxCtrl" group="viewBatchApprovalEmail_checkbox" />-->
					            </td>
					            </c:if>
							    <td class="td_title" style="text-align:center;width:10%;"><!--姓名 --><spring:message code="ess.infoApply.NAME" /></td>
								<td class="td_title" style="text-align:center;width:10%;"><!--考勤类型 --><spring:message code="ess.infoApply.attendance_type" /></td>
								<td class="td_title" style="text-align:center;width:10%;"><!--考勤开始时间 --><spring:message code="ess.infoApply.attendance_start_time" /></td>
								<td class="td_title" style="text-align:center;width:10%;"><!--考勤结束时间 --><spring:message code="ess.infoApply.attendance_end_time" /></td>
								<td class="td_title" style="text-align:center;width:10%;"><!--考勤时长 --><spring:message code="ess.infoApply.attendance_time" /></td>
								<td class="td_title" style="text-align:center;width:27%;"><!--原因 --><spring:message code="ess.infoApply.Reason" /></td>
								<td class="td_title" style="text-align:center;width:20%;"><!--审批状态--><spring:message code="ess.affirmApply.title.remark.shenpizhuangtai" /></td>
							</tr>
							<c:forEach items="${leaveApplyInfoList}" var="item" varStatus="i">
								<tr>
								    <c:if test="${not empty currentAffirmor}">
								    <td class="td_type" style="text-align:center;">
								        <c:if test="${item.APPLY_AFFIRM_FLAG eq '14014306' || (item.APPLY_AFFIRM_FLAG eq '14014307' && item.ACTIVITY eq '0')}">
                                        <!--<input type="checkbox" name="viewBatchApprovalEmail_checkbox" id="checkbox_${i.index }" value=' "APPLY_NO":"${item.APPLY_NO}","APPLY_TYPE":"${item.APPLY_TYPE}","APPLY_FLAG":"${item.APPLY_FLAG}","AFFIRM_LEVEL":"${item.AFFIRM_LEVEL}", ' />-->
                                        <input type="hidden" name="index" id="index_${i.index }" value=' "APPLY_NO":"${item.APPLY_NO}","APPLY_TYPE":"${item.APPLY_TYPE}","APPLY_FLAG":"${item.APPLY_FLAG}","AFFIRM_LEVEL":"${item.AFFIRM_LEVEL}", ' />
                                        <input type="radio" name="affirm_flag${i.index }" id="affirm_flag_1_${i.index }" value="1" checked="checked"><!--通过 --><spring:message code="ess.infoApply.adopt" />
                                        <input type="radio" name="affirm_flag${i.index }" id="affirm_flag_2_${i.index }" value="2"><!--否决 --><spring:message code="ess.infoApply.veto" />
                                        </c:if>
                                    </td>
                                    </c:if>
								    <td class="td_type" style="text-align:center;">${item.LOCAL_NAME}
								    <input type="hidden" name="PERSON_ID" id="PERSON_ID_${i.index }" value="${item.PERSON_ID }">
								    </td>
									<td class="td_type" style="text-align:center;">${item.LEAVE_TYPE_NAME}</td>
									<td class="td_type" style="text-align:center;">${item.LEAVE_FROM_TIME}</td>
									<td class="td_type" style="text-align:center;">${item.LEAVE_TO_TIME}</td>
									<td class="td_type" style="text-align:center;"><c:if test="${item.LEAVE_LENGTH ge item.DAY_HOURS }"><fmt:formatNumber type="number"  value="${item.LEAVE_LENGTH/item.DAY_HOURS + (item.LEAVE_LENGTH%item.DAY_HOURS == 0 ? 0 : -0.5)}" pattern="#" maxFractionDigits="0"/><spring:message code="ar.viewsummaryparameteritem.title.day" /><!--天--></c:if>${item.LEAVE_LENGTH%item.DAY_HOURS}<spring:message code="ar.viewsummaryparameteritem.title.hour" /><!--小时--></td>
									<td class="td_type" style="text-align:center;">${item.APPLY_REMARK}</td>
									<td class="td_type" style="text-align:center;">${item.AFFIRM_FLAG_NAME}&nbsp;&nbsp;<c:if test="${item.CONFIRM_FLAG eq 0}"><spring:message code="ess.title.WEIQUEREN" /><!--人事未确认--></c:if><c:if test="${item.CONFIRM_FLAG eq 1}"><spring:message code="ess.title.RENSHITONGGUO" /><!--人事通过--></c:if><c:if test="${item.CONFIRM_FLAG eq 2}"><spring:message code="ess.title.RENSHIFOUJUE" /><!--人事否决--></c:if></td>
								</tr>
							</c:forEach>
						</table>
	<br></br>
 	<!--<div style="font:bold 12px/20px arial,sans-serif;text-align:left;width:100%;">附件<spring:message code="org.title.enclosure" /></div>
					<table class="user_table" width="100%" border="0">
							<tr>
								<td class="td_title" style="text-align:center;width:10%;">No</td>
								<td class="td_title" style="text-align:center;width:90%;">附件<spring:message code="org.title.enclosure" /></td>
							</tr>
							<c:forEach items="${leaveApplyInfo.fileList}" var="item" varStatus="i">
								<tr>
									<td class="td_type" style="text-align:center;">${i.count}</td>
									<td class="td_type" style="text-align:left;"><a href="/ess/infoApplyLeave/downloadFile?fileName=${item.FILE_URL }&file=${item.FILE_NAME}" >${item.FILE_NAME}</a></td>
								</tr>
							</c:forEach>
					</table>
    -->
</div> 
<input type="hidden" value="${isHtml}" id="isHtml_leave">
</div>