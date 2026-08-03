<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
function validateCallback_AffirmSalary(form, callback) {
	var $form = $("#updateAffirmAttendInfo");
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
</script>
<a id="pa_affirm" href="" target="navTab" rel="pa_affirm" style="display:none;">添加</a>
<div class="pageContent">

<form onsubmit="return validateCallback_AffirmSalary(this,navTabAjaxDone);"
		action="/pa/affirmsalarycode/updateAffirmSalaryInfo" id="updateAffirmAttendInfo" method="post">
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">
				     提交</button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close">
					<spring:message code="public.title.cancle"/><!--取消--></button></div></div>
				</li>
			</ul>
		</div>
<table class="table" width="100%" layoutH="270">
	<thead>
		<tr>
				<th width="10%"> 
					申请类型
					
				</th>
				<th width="8%"> 
					申请法人
				</th>
			    <th width="10%"> 
					<spring:message code="pa.insurance.title.projectType"/><!--项目类型-->
				</th>
				<th width="15%">
					<spring:message code="pa.insurance.title.projectName"/><!--项目名称-->
				</th>
				<th width="20%">
					<spring:message code="pa.salarycode.affirm.reason"/><!--申请事由-->
				</th>
				<th width="15%">
					<spring:message code="hr.viewSuggestion.title.Suggestion"/><!--决裁意见-->
				</th>
				<th width="12%"><spring:message code="ess.infoApply.title.essApplyTime"/><!--申请时间-->
					</th>
				<th width="15%" style="text-align: center">
					<spring:message code="ar.viewcycle.title.zhuangtai"/>
				</th>	
			</tr>
	</thead>
	<tbody>
		
				<tr target="AFFIRM_ITEM_NO" rel="${salaryCodeInfo.AFFIRM_ITEM_NO}">
				  
				    <td >
						${salaryCodeInfo.ACTIVITY_TYPE eq '1' ? '新项目申请' : '申请启用'}
						<input type="hidden" name="ACTIVITY_TYPE" id="ACTIVITY_TYPE" value="${salaryCodeInfo.ACTIVITY_TYPE}"/>
					</td>
					<td >
						${salaryCodeInfo.AFFIRM_CPNY_ID}
					</td>
				    <td >
						${salaryCodeInfo.PROJECT_TYPE eq 1 ? '基础项目' : salaryCodeInfo.PROJECT_TYPE eq 2 ? '输入项目' : '计算项目'}
						<input type="hidden" name="PROJECT_TYPE" id="PROJECT_TYPE" value="${salaryCodeInfo.PROJECT_TYPE}"/>
					</td>
					
					<td>
						${salaryCodeInfo.ITEM_NAME}
						<input type="TEXT" name="AFFIRM_ITEM_NO" id="AFFIRM_ITEM_NO" value="${salaryCodeInfo.AFFIRM_ITEM_NO}"/>
						<input type="hidden" name="ITEM_NO" id="ITEM_NO" value="${salaryCodeInfo.AFFIRM_ITEM_NO}"/>
						<input type="hidden" name="ITEM_NAME" id="ITEM_NAME" value="${salaryCodeInfo.ITEM_NAME}"/>
						<input type="hidden" name="cpny" id="cpny" value="${salaryCodeInfo.AFFIRM_CPNY_ID}"/>
					</td>
					<td>
						${salaryCodeInfo.AFFIRM_REASON}
					</td>
					<td>
					   <input name="AFFIRM_DESCR" id="AFFIRM_DESCR" value="${salaryCodeInfo.AFFIRM_DESCR }"/>
					</td>
					<td>
						<fmt:formatDate value="${salaryCodeInfo.CREATE_DATE }" pattern="yyyy-MM-dd HH:mm:ss" /> 
					</td>
					<td style="text-align: center">
					<input name="flag" type="radio" value="1" checked/>通过
				    <input name="flag" type="radio" value="2"/>否决
                    </td>		
				</tr>
			
	</tbody>
</table>
</form>
</div>
