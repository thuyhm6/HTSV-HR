<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>


<script type="text/javascript">
<!--
function validateCallbackDeleteHealthInfo(form, callback) {


	var $form = $(form);
	
	if (!$form.valid()) {
		return false;
	}
   
	var checked=false;
	var ids= document.getElementsByName("TID");
	for(var i=0;i<ids.length;i++){
		if(ids[i].checked){
			checked=true;
		}
	}
	if(!checked){
		//请选择信息再进行保存操作
		alertMsg.error('<spring:message code="hr.alert.message.viewPersonalInfo.checkBoxForChecked"/>');
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
//-->
</script>


<div class="pageContent">
	<form method="post" action="/hrm/empinfo/deleteTradeunionInfo" class="pageForm required-validate" onsubmit="return validateCallbackDeleteHealthInfo(this, dialogAjaxDone);">
			
			<table class="table" width="101%" layoutH="150">
										<thead>
											<tr>
												<th><input type="hidden" name="PERSON_ID" class="textInput" value="${PERSON_ID }"/></th>
												<th width="80">
													<spring:message code="hr.viewPersonalInfo.title.TRADEUNION_NUM" />
													<!--序号-->
												</th>
												<th width="80">
													<spring:message code="hr.viewPersonalInfo.title.TRADEUNION_ZHIZE" />
													<!--职责-->
												</th>
												<th width="80">
													<spring:message code="hr.viewPersonalInfo.title.TRADEUNION_ADDDATE" />
													<!--加入日期-->
												</th>
												<th width="80">
													<spring:message code="hr.viewPersonalInfo.title.TRADEUNION_QUITDATE" />
													<!--退出日期-->
												</th>
												<th width="80">
													<spring:message code="hr.viewPersonalInfo.title.TRADEUNION_JOIN_FLAG" />
													<!--参加 工会与否-->
												</th>
												<th width="80">
													<spring:message code="hr.viewPersonalInfo.title.TRADEUNION_PAY_FLAG" />
													<!--会费支付状态-->
												</th>
												<th width="80">
													<spring:message code="hr.viewPersonalInfo.title.TRADEUNION_PAY_TYPE" />
													<!--支付方式-->
												</th>
												<th width="80">
													<spring:message code="hr.viewPersonalInfo.title.TRADEUNION_REMARK" />
													<!--描述-->
												</th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${tradeUnionList}" var="item" varStatus="i">
							
												<tr target="TID" rel="${item.TID}">
													<td align="center"  width="80">
														<input type="checkbox" id="TID_${i.count}" name="TID" value="${item.TID}" />
														
													</td>
													<td width="80">
														${i.count}
													</td>
													<td  width="80">${item.RES}</td>
													<td  width="80"> ${fn:substring(item.ADDDATE,0, 10)}</td>
													<td  width="80"> ${fn:substring(item.QUITDATE,0, 10)}</td>
													<td class='td_center'  width="80"> ${item.JOIN_FLAG}</td>
													<td class='td_center'  width="80"> ${item.PAY_FLAG}</td>
													<td class='td_center'  width="80"> ${item.PAY_TYPE}</td>
													<td  width="80">${item.REMARK}</td>
												</tr>
											</c:forEach>

										</tbody>
									</table>
		
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit"><spring:message code="public.title.submit"/><!-- 保存 --></button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close"><spring:message code="public.title.cancle"/><!-- 取消 --></button></div></div>
				</li>
			</ul>
		</div>
		
	</form>
</div>