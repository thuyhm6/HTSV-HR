<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>


<script type="text/javascript">
function validateCallbackeditCPFJoinInsurance(form, callback) {
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
		success: callback || DWZ.ajaxDone,
		error: DWZ.ajaxError
	});
	
	return false;
}
/* //修改关闭
function close_bx0103(){
	//document.getElementById(updateid_bx0103).display = "none";
	//document.getElementById(editJoinInsurance).pdialog.close();
	//navTabNum('/is/insurancesystem/viewJoinInstanceMangement?pageNum=1&menuNo=124904&navTabId=bx0103','bx0103', '参保管理');
} */
//恢复状态
function close_bx0103ab(){
	$("#recoverUpdState").attr("href","/is/insurancesystem/editJoinInsurance_a");
			$("#recoverUpdState").click();
}
</script>


	<!-- class="pageForm required-validate"
		onsubmit="return validateCallbackeditCPFJoinInsurance(this, dialogAjaxDone);"
 -->
<div class="pageContent">
	<form id="editJoinInsurance_a" name="editJoinInsurance_a" method="post" action="/is/insurancesystem/saveUpdJoinInsurance" class="pageForm required-validate" onsubmit="return validateCallbackeditCPFJoinInsurance(this,dialogAjaxDone);" >
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
					<th width="50">
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
						<!-- 户口性质 --> <spring:message
							code="hr.viewPersonalInfo.title.REG_TYPE_NAME" /></th>
					<th width="100">
						<!-- 入社日期 --> <spring:message code="display.emp.statistics.mes206" />
					</th>
					<th width="100">
						<!-- 离职日期 --> <spring:message
							code="ess.trans.title.resignDate" /></th>
					<th width="100">
						<!-- 开始缴纳月--> <spring:message code="is.joininstance.title.startwithmonth" />
					</th>
					<th width="100">
						<!-- 基础参照工资--> <spring:message code="is.joininstance.title.basesalary" />
					</th>
					<th width="100" >
					<!-- 入社基数--> <spring:message code="is.joininstance.title.basenum" />
					</th>
					<th width="100">
						<!-- 缴纳基数 --> <spring:message code="is.joininstance.title.paybasenum" />
					</th>
					<th  width="100">
						<!-- 标记 --> <spring:message
							code="is.joininstance.title.remarking" /></th>
					<th width="100">
						<!-- 状态 --> <spring:message code="is.joininstance.title.statement" />
					</th>
					<th width="100">
						<!-- 提示 --> <spring:message code="is.joininstance.title.message" />
					</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${allowUpdate}" var="show" varStatus="i">
					<tr align="center" onclick="band('#f4f7fa','black')">
						<td style="white-space:nowrap">${i.index + 1}&nbsp;</td><!-- 序号 -->
						<td style="white-space:nowrap" align="left">
							
							${show.DEPTNAME}&nbsp;</td><!-- 部门 -->
						<td style="white-space:nowrap">
						<input type="text" name="empid" value= "${show.EMPID}" readonly="readonly" style="text-align:right" size="6">
						</td><!-- 职号 -->
						<td style="white-space:nowrap">${show.CHINESENAME}&nbsp;</td><!-- 姓名 -->
						<td  style="white-space:nowrap">
								${show.REG_TYPE_CODE_NAME}&nbsp;
							</td><!-- 户口性质 -->
							<td  style="white-space:nowrap">
								${show.DATE_STARTED}&nbsp;
							</td><!-- 入社日期 -->
							<td  style="white-space:nowrap">
								<c:choose>
									<c:when test="${empty show.DATE_LEFT}">-</c:when>
									<c:otherwise>${show.DATE_LEFT}</c:otherwise>
								</c:choose>
							</td><!-- 离职日期 --> 
							<td  style="white-space:nowrap">
								<ait:date yearName="year" monthName="month" yearSelected="${show.START_YEAR}" monthSelected="${show.START_MONTH}" yearPlus="10" yearMinus="10"/>
							</td><!-- 开始缴纳月-->
							<td  style="white-space:nowrap">
							${show.REFER_VALUE}&nbsp;
							</td><!-- 基础参照工资-->
							<td  style="white-space:nowrap">
								<input type="text" name="joinValue" value="${show.JOIN_VALUE}" style="text-align:right" size="6">
							</td>><!-- 入社基数 -->
							<td  style="white-space:nowrap">
							${show.ENDOWMENT_BASE}&nbsp;
							</td><!--缴纳基数 -->
							<td  style="white-space:nowrap">
							${show.BASELINE}&nbsp;
							</td><!-- 标记 -->
							<td  style="white-space:nowrap">
								<c:choose>
									<c:when test="${empty show.DATE_TYPE}">-</c:when>
									<c:otherwise>
									${show.DATE_TYPE}&nbsp;
									</c:otherwise>
								</c:choose>
							</td>
							<td  style="white-space:nowrap">
								<c:choose>
									<c:when test="${empty show.ERROR_REMARK}">-</c:when>
									<c:otherwise>${show.ERROR_REMARK}
									${show.ERROR_REMARK}&nbsp;
									</c:otherwise>
								</c:choose>
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
								<!-- 提交 -->
							</button>
						</div>
					</div>
				</li>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="button" class="close">
								<spring:message code="public.title.cancle" />
							</button>
						</div>
					</div></li>
			</ul>
		</div>
	</form>
</div>