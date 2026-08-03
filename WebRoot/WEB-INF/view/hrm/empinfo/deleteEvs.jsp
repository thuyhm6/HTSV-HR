<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>


<script type="text/javascript">
<!--
function validateCallbackDeleteEvsInfo(form, callback) {


	var $form = $(form);
	
	if (!$form.valid()) {
		return false;
	}
   
	var checked=false;
	var ids= document.getElementsByName("EPNO");
	for(var i=0;i<ids.length;i++){
		if(ids[i].checked){
			checked=true;
		}
	}
	if(!checked){
		alertMsg.error('<spring:message code="hr.alert.message.viewPersonalInfo.checkBoxForChecked"/>');//请选择信息再进行保存操作
		return false;
	}
	//确认要提交吗？
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
	<form method="post" action="/hrm/empinfo/deleteEvsInfo" class="pageForm required-validate" onsubmit="return validateCallbackDeleteEvsInfo(this, dialogAjaxDone);">
		
		
			<table class="table" width="103%" layoutH="60">
					<thead>
						<tr>
							<th width="20"><input name="PERSON_ID" type="hidden" value="${PERSON_ID }"></th>
							<th width="100">
								<spring:message code="hr.viewEvaluate.title.EV_PERIOD"/>
								<!--评价期间-->
							</th>
                            <!--<th width="100">  -->
                                <%--<spring:message code="hr.viewEvaluate.title.EV_TYPE_NAME" />--%>
                                <!--评价类型-->
                            <!--</th>  -->
                            <th width="100">
                                <spring:message code="hr.viewEvaluate.title.EV_ACHI" />
                                <!--업적  -->
                            </th>
                            <th width="100">
                                <spring:message code="hr.viewEvaluate.title.EV_ATTI" />
                                <!--태도 -->
                            </th>
                            <th width="100">
                                <spring:message code="hr.viewEvaluate.title.EV_ABIL" />
                                <!--능력-->
                            </th>                                               
							<th width="80">
								<spring:message code="hr.viewEvaluate.title.EV_MARK"/>
								<!--评价分数-->
							</th>
							<th width="100">
								<spring:message code="hr.viewEvaluate.title.EV_GRADE_NAME"/>
								<!--评价等级-->
							</th>
							<th width="100">
								<spring:message code="hr.viewSuggestion.title.Suggestion" />
								<!--意见-->
							</th>
							<th width="100">
								<spring:message code="hr.viewFinalSequence.title.FinalSequence" />
								<!--最终顺位-->
							</th>
							<th width="100">
								<spring:message code="hr.viewTotalPeople.title.TotalPeople" />
								<!--总职级员人数-->
							</th>
							<th width="100">
								<spring:message code="hr.viewPromote.title.REMARK" />
								<!--备注-->
							</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${EvsInfo}" var="item" >
						
							<tr target="sid" rel="${item.EV_PERIOD}">
								<td>
									<input type="checkbox" id="EPNO" name="EPNO" value="${item.EV_PERIOD}" />
								</td>
								<td>${item.EV_PERIOD}</td>
								<!--<td>${item.EV_TYPE_NAME}</td>-->
                                <td>${item.EV_ACHI}</td>
                                <td>${item.EV_ATTI}</td>
                                <td>${item.EV_ABIL}</td>                                                                
								<td>${item.EV_MARK}</td>
								<td>${item.EV_GRADE_NAME}</td>
								<td>${item.SUGGESTION }</td>
								<td>${item.FINAL_SEQUENCE }</td>
								<td>${item.TOTAL_PEOPLE }</td>
								<td>${item.EV_REMARK}</td>
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
		<input type="text" id="PERSON_ID" name="PERSON_ID" value="${PERSON_ID }">
	</form>
</div>