<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>


<script type="text/javascript">
function validateCallbackeditCPFObjInsurance(form, callback) {
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
function close_bx0105ab(){
	$("#recoverUpdState").attr("href","/is/insurancesystem/editObjInsurance_a");
			$("#recoverUpdState").click();
}
</script>


	<!-- class="pageForm required-validate"
		onsubmit="return validateCallbackeditCPFJoinInsurance(this, dialogAjaxDone);"
 -->
<div class="pageContent">
	<form id="editObjInsurance_a" name="editObjInsurance_a" method="post" action="/is/insurancesystem/saveUpdObjInsurance" class="pageForm required-validate" onsubmit="return validateCallbackeditCPFObjInsurance(this,dialogAjaxDone);" >
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
				<th width="80">
				<!-- 社保状态 --><spring:message code="is.objmanagement.title.socialstate" />
				</th>
				
				<th width="130">
				<!-- 社会保险号码 --><spring:message code="is.objmanagement.title.socialInsureNum"/>
				</th>
				<th width="100">
				<!-- 入社基数--> <spring:message code="is.joininstance.title.basenum" />
				</th>				
				<th width="100" >
				<!-- 年度基数--> <spring:message code="display.emp.statistics.mes208" />
				</th>
				<th width="100" >
				<!-- 标记--> <spring:message code="is.joininstance.title.remarking" />
				</th>
			
				<th width="50">
				<!-- 养老 --><spring:message code="is.joininstance.title.yanglao" />
				</th>
				<th width="50">
				<!-- 医疗 --><spring:message code="is.joininstance.title.yiliao" />
				</th>
				<th width="50">
				<!-- 生育 --><spring:message code="is.joininstance.title.shengyu" />
				</th>
				<th width="50">
				<!-- 工伤 --><spring:message code="is.joininstance.title.gongshang" />
				</th>
				<th width="50">
				<!-- 失业 --><spring:message code="is.joininstance.title.shiye" />
				</th>	
		</thead>
		<tbody>
			<c:forEach items="${allowUpdate}" var="show" varStatus="i">
                         <tr align="center" onclick="band('#f4f7fa','black')">
							<td >
								${i.index + 1}&nbsp;
								<input type="hidden" name="seq" value="${show.PA_BEN_MANAGE_SEQ}"/>
								<input type="hidden" name="empID_u" value="${show.EMPID}"/>
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
								<c:choose>
									<c:when test="${show.SOCIAL_STATUS eq '停保'}">
										<select name="socialStatus1">
											<option value="STOP">停保</option>
											<option value="LAST">在保</option>
										</select>
									</c:when>
									<c:when test="${show.SOCIAL_STATUS eq '在保'}">
										${show.SOCIAL_STATUS}
										<input type="hidden" name="socialStatus1" value="LAST">
									</c:when>
									<c:otherwise>
										${show.SOCIAL_STATUS}
										<input type="hidden" name="socialStatus1" value="JOIN">
									</c:otherwise>
								</c:choose>
							</td>
							<td >
								<input type="text" name="socialNo" value="${show.SOCIAL_NO}" style="text-align:left" size="20" maxlength="25">
							</td>
							<td >
								<c:choose>
									<c:when test="${empty show.AVG_SALARY}">
										<input type="text" name="joinValue" value="${show.JOIN_VALUE}" size="10" style= "text-align: right" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')">
									</c:when>
									<c:otherwise>
										<input type="hidden" name="joinValue" value="${show.JOIN_VALUE}">
										${show.JOIN_VALUE}&nbsp;
									</c:otherwise>
								</c:choose>
							</td>
							<td >
								${show.AVG_SALARY}&nbsp;
							</td>
							<td >
								${show.BASELINE}&nbsp;
							</td>
							<td >
								${show.ENDOWMENT_BASE}&nbsp;
							</td>
							<td >
								${show.MEDICARE_BASE}&nbsp;
							</td>
							<td >
								${show.SHENGYU_BASE}&nbsp;
							</td>
							<td >
								${show.COMPO_BASE}&nbsp;
							</td>
							<td >
								${show.UNEMP_BASE}&nbsp;
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