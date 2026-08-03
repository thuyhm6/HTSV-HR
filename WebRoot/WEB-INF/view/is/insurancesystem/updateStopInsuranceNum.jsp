<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>


<script type="text/javascript">
function validateCallbackeditCPFStopInsurance(form, callback) {
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
	<form id="editStopInsurance_a" name="editStopInsurance_a" method="post" action="/is/insurancesystem/saveUpdStopInsurance" class="pageForm required-validate" onsubmit="return validateCallbackeditCPFStopInsurance(this,dialogAjaxDone);" >
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
				<th width="50"><!-- 序号-->
					<spring:message code="hr.viewPersonalInfo.title.TRADEUNION_NUM"/>
				</th>
				<th width="100" ><!-- 部门-->
					<spring:message code="public.title.deptName"/>
				</th>
				<th width="100" ><!-- 职号-->
					<spring:message code="display.emp.statistics.mes209"/>
				</th>
				<th width="80"><!-- 姓名-->
					<spring:message code="public.title.name"/>
				</th>
				<th width="50"><!-- 性别-->
					<spring:message code="hr.viewPersonalInfo.title.SEX"/>
				</th>
				
				<th width="130"><!-- 身份证号 -->
					<spring:message code="ess.personalinfo.title.IDCardNo"/>
				</th>
				<th width="100"><!-- 公积金账号-->
					<spring:message code="display.emp.statistics.mes223" />
				</th>				
				<th width="100" ><!-- 在职状态-->
					<spring:message code="display.emp.statistics.mes204"/>
				</th>
				<th width="100" ><!-- 入社日期-->
					<spring:message code="display.emp.statistics.mes206"/>
				</th>
			
				<th width="100" ><!-- 离职日期-->
					<spring:message code="ess.trans.title.resignDate"/>
				</th>				
				<th width="100" ><!-- 终止缴纳月-->
					<spring:message code="display.emp.statistics.mes224" />
				</th>				
		</thead>
		<tbody>
			<c:forEach items="${allowUpdate}" var="show" varStatus="i">
                        <tr align="center" onclick="band('#f4f7fa','black')">
							<td >
								${i.index + 1}&nbsp;
							</td>
							<td align="left">
								${show.DEPTNAME}&nbsp;
							</td>
							<td >
								${show.EMPID}&nbsp;
							</td>
							<td >
								${show.CHINESENAME}&nbsp;
							</td>
							<td >
								${show.SEX_NAME}&nbsp;
							</td>
							<td >
								${show.IDCARD_NO}&nbsp;
							</td>
							<td >
								${show.SOCIAL_NO}&nbsp;
							</td>
							<td >
								${show.EMP_OFFICE_NAME}&nbsp;
							</td>
							<td >
								${show.DATE_STARTED}&nbsp;
							</td>
							<td >
								<c:choose>
									<c:when test="${empty show.DATE_LEFT}">-</c:when>
									<c:otherwise>${show.DATE_LEFT}</c:otherwise>
								</c:choose>
							</td>
						
							<td  style="white-space:nowrap">
								<input type="hidden" name="empid" value="${show.EMPID}">
								<ait:date yearName="year" monthName="month" yearSelected="${show.END_YEAR}" monthSelected="${show.END_MONTH}" yearPlus="10" yearMinus="10"/>
							</td><!-- 开始缴纳月-->
							
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