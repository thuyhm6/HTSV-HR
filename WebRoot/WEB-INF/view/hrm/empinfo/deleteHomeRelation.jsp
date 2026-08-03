<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>


<script type="text/javascript">
<!--
function validateCallbackDeleteFamilyInfo(form, callback) {


	var $form = $(form);
	
	if (!$form.valid()) {
		return false;
	}
   
	var checked=false;
	var ids= document.getElementsByName("FN");
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
	<form method="post" action="/hrm/empinfo/deleteHomeRelationInfo" class="pageForm required-validate" onsubmit="return validateCallbackDeleteFamilyInfo(this, dialogAjaxDone);">
		
		<input type="hidden" id="isEssSystem" name="isEssSystem" value="${isEssSystem }"/>
			<table class="table" width="101.7%" layoutH="60">
					<thead>
						<tr>
							<th width="10"></th>
							<th width="100">
								<spring:message code="hr.viewRelation.title.FAM_TYPE_NAME"/>
								<!--关系-->
								<input type="hidden" name="PERSON_ID" size="13"  value="${PERSON_ID }"/>
							</th>
							<th width="100">
								<spring:message code="hr.viewPersonalInfo.title.LOCAL_NAME"/>
								<!--姓名-->
							</th>
							<%--<th width="100">
								<spring:message code="hr.viewPersonalInfo.title.IDCARD_NO"/>
								<!--身份证号码-->
							</th>
							--%><th width="100">
								<spring:message code="hr.viewPersonalInfo.title.DOB"/>
								<!--出生日期-->
							</th>
							<%--<th width="100">
								<spring:message code="hr.viewRelation.title.FAM_ADDRESS"/>
								<!--地址-->
							</th>
							<th width="100">
								<spring:message code="hr.viewRelation.title.FAM_PHONE"/>
								<!--联系电话-->
							</th>
							<th width="100">
								<spring:message code="hr.viewRelation.title.FAM_COMPANY_NAME"/>
								<!--工作单位/职(岗)位-->
							</th>
							<th width="100">
								<spring:message code="hr.viewRelation.title.LIVE_YN_NAME"/>
								<!--一起居住与否-->
							</th>
							<th width="100">
								<spring:message code="hr.viewRelation.title.EMERGENCY_CONTACT_YN_NAME"/>
								<!--是否紧急联系人-->
							</th>
							<th width="100">
								<spring:message code="hr.viewRelation.title.FAM_PERSON_ID"/>
								<!--亲属员工号-->
							</th>
						--%>
							<%--<th width="100">
								<spring:message
										code="hr.viewPersonalInfo.title.FAMILY_YINYANGRILI" />
										<!--阴/阳历区分-->
							</th>
											
							--%><th width="100">
								<spring:message
										code="hr.viewPersonalInfo.title.FAMILY_CPNYNAME" />
									<!--单位名称-->
							</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${deleteRelationInfoList}" var="item" >
						
							<tr>
								<td>
									<input type="checkbox" id="FN" name="FN" value="${item.FAMILY_NO}" />
								</td>
								<td>${item.FAM_TYPE_NAME}</td>
								<td>${item.FAM_NAME}</td>
								<%--<td>${item.FAM_IDCARD}</td>
								--%><td>${item.FAM_BORNDATE}</td>
								<%--<td>${item.FAM_ADDRESS}</td>
								<td>${item.FAM_PHONE}</td>
								<td>${item.FAM_COMPANY_NAME}</td>
								<td>${item.LIVE_YN}</td>
								<td>${item.EMERGENCY_CONTACT_YN}</td>
								<td>${item.FAM_PERSON_ID}</td>
							--%><%--
								<td>
									${item.FAM_YINYANGLINAME }
								</td>
								--%><td>${item.FAM_COMPANY_NAME}</td>
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