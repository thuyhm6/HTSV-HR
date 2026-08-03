<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>


<script type="text/javascript">
function validateCallbackorderUpdateCPFBaseManagement(form, callback) {


	var $form = $("#orderUpdateCPFBaseManagement");
	
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
</script>

<div class="pageContent">
<form id="orderUpdateCPFBaseManagement" method="post"
		action="/is/accumulationfund/orderUpdateCPFBaseManagement"
		class="pageForm required-validate"
		onsubmit="return validateCallbackorderUpdateCPFBaseManagement(this, dialogAjaxDone);">
	
<div class="formBar">
			<ul>
				<li><div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">
								<!-- 发令 -->
									<spring:message code="display.emp.statistics.mes202" />
							</button>
						</div>
					</div>
				</li>
			</ul>
</div>
		
				
		
			

<table class="table" width="101.7%" layoutH="160"  >
<tr>
<td colspan="3" class='td_center' style="background-color:rgb(244, 247, 250);">发令统计信息 </td>
</tr>
<tr>
<td colspan="3" class='td_center'  style="background-color:rgb(244, 247, 250);">${alertMsg}</td>
</tr>
<tr>
<td class='td_center'  style="background-color:rgb(244, 247, 250);">本次发令人数 </td>
<td class='td_center'  style="background-color:rgb(244, 247, 250);">实际发令影响人数 </td>
<td class='td_center'  style="background-color:rgb(244, 247, 250);">发令剩余人数 </td>
</tr>
<tr>
<td class='td_center'>${orderCount}</td>
<td class='td_center'>${orderTrueCount}</td>
<td class='td_center'>${orderCount - orderTrueCount}</td>
</tr>
</table> 


<div class="pageContent">

		<input type="hidden" id="qualificationListSize"
			name="qualificationListSize" value="${fn:length(qualificationList)}" />
		<input type="hidden" name="PERSON_ID" class="textInput"
			value="${PERSON_ID }" />
		<table class="table" width="101.7%" layoutH="150">
			<thead>
				<tr>
				<th colspan="5" class='td_center' style="background-color:rgb(244, 247, 250);">发令剩余人员信息  </th>
				</tr>
				<tr>
					<th width="100">
						<!-- 序号 --> <spring:message
						code="hr.viewPersonalInfo.title.TRADEUNION_NUM" />
					</th>
					<th width="100">
						<!-- 部门 --><spring:message code="public.title.deptName" />
					</th>
					<th width="100" >
						<!-- 职号 --><spring:message code="display.emp.statistics.mes209" />
					</th>
					<th width="100">
						<!-- 姓名 --><spring:message code="public.title.name" />
					</th>
					<th width="100">
						<!-- 身份证号码 --><spring:message
						code="ess.personalinfo.title.IDCardNo" />
					</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${falseOrder}" var="show" varStatus="i">
                        <tr>
							<td  style="white-space:nowrap" align="center">
								${i.index + 1}
							</td>
							<td  style="white-space:nowrap" align="left">
								${show.DEPTNAME}
							</td>
							<td  style="white-space:nowrap" align="center">
								${show.EMPID}
							</td>
							<td  style="white-space:nowrap" align="center">
								${show.CHINESENAME}
							</td>
							<td  style="white-space:nowrap" align="center">
								${show.IDCARD_NO}
							</td>
						</tr>
				</c:forEach>
			</tbody>
		</table>
	

</div>
</form>

</div>