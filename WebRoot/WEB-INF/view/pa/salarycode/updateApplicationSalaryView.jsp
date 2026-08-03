<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
	function validateCallbackPa1109(form, callback) {

		var $form = $("#updateApplicationSalaryInfo");

		if (!$form.valid()) {
			return false;
		}
		var checked=false;
		var ids= document.getElementsByName("check_pa");
		for(var i=0;i<ids.length;i++){
			if(ids[i].checked){
				checked=true;
			}
		}
		if(!checked){
			//请选择信息再进行删除操作!
			alert("请选择工资项目");
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

	<form id="updateApplicationSalaryInfo" method="post"
		action="/pa/salarycode/updateApplicationSalaryInfo"
		class="pageForm required-validate"
		onsubmit="return validateCallbackPa1109(this, navTabAjaxDone);">
		<input type="hidden" name="PERSON_ID" id="PERSON_ID"
			value="${applicationSalaryInfo.PERSON_ID }" /> 
		<input type="hidden"
			name="SALARY_CODE" id="SALARY_CODE"
			value="${applicationSalaryInfo.SALARY_CODE }" />
			
				<div class="panel" id="jbsxBox" style="display:block;float:left;width:99%;clear:none;" >
				
					<h1><!-- 人员信息 --><spring:message code="ar.alert.message.viewattendencekeeper.personalInfo"/></h1>
					<div class="pageFormContent nowrap">
							<dl>
								<dt><!-- 工号 --><spring:message code="public.title.empId"/>:</dt>
								<dd><input type="text" name="EMPID"
									value="${applicationSalaryInfo.EMPID}" readonly="readonly" /></dd>
							</dl>
							<dl>
								<dt><!-- 姓名 --><spring:message code="public.title.name"/>:</dt>
								<dd><input name="dwz.person.empName" value="${applicationSalaryInfo.LOCAL_NAME}" type="text" readOnly lookupGroup="person"/></dd>
							</dl>
							<dl>
								<dt><!-- 部门 --><spring:message code="public.title.deptName"/>:</dt>
								<dd><input type="text" name="DEPT_NAME"
									value="${applicationSalaryInfo.DEPT_NAME}" readonly="readonly" /></dd>
							</dl>
							<dl>
								<dt>法人:</dt>
								<dd><input type="text" name="CPNY_ID"
									value="${applicationSalaryInfo.CPNY_ID}" readonly="readonly" /></dd>
							</dl>
							<dl style="height:auto">
								<table width="100%">
								<tr>
								<td class="td_title" style="width:124px;"><spring:message code="pa.diff.title.salaryItem" />:</td>
								<td class="td_type">
									<table class="table" width="100%">
					       				<thead>
											<tr>
												<th width="50"><input type="checkbox" name="c1_c"
													id="c1_c" class="checkboxCtrl" group="check_pa"></th>
												<th width="100">
													<!-- 序号--> <spring:message
														code="hr.viewPersonalInfo.title.TRADEUNION_NUM" />
												</th>
												<th><spring:message code="pa.diff.title.salaryItem" /></th>
											</tr>
										</thead>
										<tbody>
											<c:forEach items="${salaryCodeList}" var="item" varStatus="i">
												<tr onclick="band('#f4f7fa','black')" target="sid"
													rel="${item.ITEM_NO}" id="ITEM_NO" name="ITEM_NO">
													<td class="td_center" style="white-space:nowrap"><input
														type="checkbox" id="${item.ITEM_NO}" name="check_pa"
														value="${item.ITEM_NO},${item.ITEM_NAME}"
														<c:forEach items="${salaryList}" var="itemlist" varStatus="j">
														<c:if test="${item.ITEM_NO eq itemlist}">checked=true</c:if>
														</c:forEach> />
													</td>
													<td>${i.index+1 }</td>
													<td>${item.ITEM_NAME} <input type="hidden" name="ITEM_NO"
														id="ITEM_NO" value="${item.ITEM_NO }" /></td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
									
								</td>
							</tr>
							</table>
							</dl>
						</div>
		</div>

		<div class="formBar">
			<ul>
				<li><div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">
								<spring:message code="public.title.submit" />
								<!--提交-->
							</button>
						</div>
					</div></li>
				<li><div class="button">
						<div class="buttonContent">
							<button type="button" class="close">
								<spring:message code="public.title.cancle" />
								<!--取消-->
							</button>
						</div>
					</div></li>
			</ul>
		</div>
	</form>

</div>
