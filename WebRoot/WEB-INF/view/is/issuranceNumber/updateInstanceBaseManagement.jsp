<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>


<script type="text/javascript">
function validateCallbackeditCPFBaseManagement(form, callback) {


	var $form = $("#editInstanceBaseManagement");
	
	if (!$form.valid()) {
		return false;
	}

	//确定要提交吗？
	if (confirm ('<spring:message code="hr.viewEvaluate.title.COMMIT_CONFIRM"/>')){	
		$.ajax({
			type: form.method || 'POST',
			url:$form.attr("action"),
			data:$form.serializeArray(),
			dataType:"json",
			cache: false,
			success: callback || DWZ.ajaxDone,
			error: DWZ.ajaxError
		});
	}
	return false;
}
//修改关闭
function close_bx0102(){
	$.pdialog.close("editInstanceBaseManagement");
	navTabNum('/is/issuranceNumber/viewBaseManagementForSearch?pageNum=1&menuNo=124902&navTabId=bx0102','bx0102', '基数管理');
}
</script>


<div class="pageContent">
	<form id="editInstanceBaseManagement" method="post"
		action="/is/issuranceNumber/editInstanceBaseManagement"
		class="pageForm required-validate"
		onsubmit="return validateCallbackeditCPFBaseManagement(this, dialogAjaxDone);">

		<div class="panelBar">
			<ul class="toolBar">
				<li id="addLi"><span>&nbsp;</span></li>
			</ul>
		</div>

		<input type="hidden" id="qualificationListSize"
			name="qualificationListSize" value="${fn:length(qualificationList)}" />
		<input type="hidden" name="PERSON_ID" class="textInput"
			value="${PERSON_ID }" />
		<table class="table" width="101.7%" layoutH="150">
			<thead>
				<tr>
					<th width="100">
						<!-- 序号 --> <spring:message
							code="hr.viewPersonalInfo.title.TRADEUNION_NUM" /></th>
					<th width="100" style="white-space:nowrap">
						<!-- 部门 --> <spring:message code="public.title.deptName" /></th>
					<th width="100" style="white-space:nowrap">
						<!-- 职号 --> <spring:message code="display.emp.statistics.mes209" />
					</th>
					<th width="100">
						<!-- 姓名 --> <spring:message code="public.title.name" /></th>
					<th width="100">
						<!-- 身份证号码 --> <spring:message
							code="ess.personalinfo.title.IDCardNo" /></th>
					<th width="100">
						<!-- 职系 --> <spring:message code="display.emp.statistics.mes210" />
					</th>
					<th>
						<!-- 户口性质 --> <spring:message
							code="hr.viewPersonalInfo.title.REG_TYPE_NAME" /></th>
					<th>
						<!-- 在职状态--> <spring:message code="display.emp.statistics.mes204" />
					</th>
					<th>
						<!-- 入社日期--> <spring:message code="display.emp.statistics.mes206" />
					</th>
					<th>
						<!-- 离职日期 --> <spring:message code="ess.trans.title.resignDate" />
					</th>
					<th style="white-space:nowrap">
						<!-- 平均扣税工资 --> <spring:message
							code="display.emp.statistics.mes198" /></th>
					<th width="100">
						<!-- 年度基数 --> <spring:message code="display.emp.statistics.mes208" />
					</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${allowUpdate}" var="show" varStatus="i">
					<tr align="center" onclick="band('#f4f7fa','black')">
						<td style="white-space:nowrap">${i.index + 1}&nbsp;</td>
						<td style="white-space:nowrap" align="left">
							${show.DEPTNAME}&nbsp;</td>
						<td style="white-space:nowrap">${show.EMPID}&nbsp;</td>
						<td style="white-space:nowrap">${show.CHINESENAME}&nbsp;</td>
						<td style="white-space:nowrap">${show.IDCARD_NO}&nbsp;</td>
						<!-- 
							<td  style="white-space:nowrap">
								${show.SOCIAL_NO}&nbsp;
							</td>
							 -->
						<td style="white-space:nowrap">${show.POST_COEF_NAME}&nbsp;</td>
						<td style="white-space:nowrap">
							${show.REG_TYPE_CODE_NAME}&nbsp;</td>
						<td style="white-space:nowrap">
							${show.STATUS_CODE_NAME}&nbsp;</td>
						<td style="white-space:nowrap"><c:choose>
								<c:when test="${empty show.DATE_STARTED}">-</c:when>
								<c:otherwise>${show.DATE_STARTED}</c:otherwise>
							</c:choose>
						</td>
						<td style="white-space:nowrap"><c:choose>
								<c:when test="${empty show.DATE_LEFT}">-</c:when>
								<c:otherwise>${show.DATE_LEFT}</c:otherwise>
							</c:choose>
						</td>
						<td style="white-space:nowrap">${show.PAY_SALARY}&nbsp;</td>
						<td style="white-space:nowrap"><input type="hidden"
							name="empID" value="${show.PERSON_ID}"> <input type="text"
							name="avgSalary" value="${show.AVG_SALARY}" size="10"
							style="text-align: right"
							onkeyup="if(isNaN(value))execCommand('undo')"
							onafterpaste="if(isNaN(value))execCommand('undo')">
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>






		<div class="formBar">
			<ul>
				<li><div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">
								<spring:message code="public.title.submit" />
								<!-- 保存 -->
							</button>
						</div>
					</div>
				</li>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" onclick="close_bx0102();">
								<spring:message code="public.title.cancle" />
								<!-- 取消 -->
							</button>
						</div>
					</div></li>
			</ul>
		</div>
	</form>
</div>