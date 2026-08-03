<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript">
function validateAddEvsFormulaInfoCallback(form,callback) {
	var $form = $("#" + form);	
	if (!$form.valid()) {
		return false;
	}
	alertMsg.confirm("<spring:message code='ess.message.confirm_sava'/>",//确定要保存吗？
  		  	{okCall:function(){
		  	$.ajax({
  				type: form.method || 'POST',
  				url:$form.attr("action"),
  				data:$form.serializeArray(),
  				dataType:"json",
  				cache: false,
  				success: DWZ.ajaxDone,
  				error: DWZ.ajaxError
  			});
  	}});
	return false;
}

function validateDeleteEvsFormulaInfoCallback(form,callback) {
	var $form = $("#" + form);	
	alertMsg.confirm("<spring:message code='js.upload.msg.confirmToDelete'/>",//确定要删除吗？
  		  	{okCall:function(){
		  	$.ajax({
  				type: form.method || 'POST',
  				url:'/evs/manage/deleteEvsFormulaInfo',
  				data:$form.serializeArray(),
  				dataType:"json",
  				cache: false,
  				success: callback || DWZ.ajaxDone,
  				error: DWZ.ajaxError
  			});
  	}});
	return false;
}
</script>
		<form id="viewAddEvsFormulaInfo" method="post" action="/evs/manage/addEvsFormulaInfo" class="pageForm required-validate" 
			onsubmit="return validateAddEvsFormulaInfoCallback(this,navTabAjaxDone);">
			<div>
				<table class="user_table" width="100%" border="1" cellpadding="2" cellspacing="1">
					<tr>
						<td>
							<table  class="user_table" width="100%">
								<tr>
									<td width="15%" class="td_title"><spring:message code="sys.arAffirmPost.title.code"/><!--代码--></td>
									<td width="35%" class="td_type">
										<input type="text" id="CODE_NO" name="CODE_NO" value="${EvsFormulaInfo.CODE_NO}" class="required"
											<c:if test="${not empty EvsFormulaInfo.CODE_NO }">
												 readonly="true"
											</c:if>
											size="35"/>
										<input type="hidden" id="SEQ" name="SEQ" value="${EvsFormulaInfo.SEQ}"/>
									</td>
									<td width="15%" class="td_title">
										<spring:message code="ar.viewRetrieveSqlMasterList.MINGCHENG.b"/><!--名称-->
									</td>
									<td width="35%" class="td_type">
										<input type="text" id="CODE_NAME" name="CODE_NAME" value="${EvsFormulaInfo.CODE_NAME}" class="required" size="35"/>
									</td>
								</tr>
								<tr>
									<td width="15%" class="td_title">
										<spring:message code="evs.viewEvsParamInfoList.JIZHUNSHI.a"/><!--基准式-->
									</td>
									<td width="35%" class="td_type" colspan="3">
										<textarea name="FORMULA"  style="width:650px;height:120px">${EvsFormulaInfo.FORMULA}</textarea>
									</td>
								</tr>
								<tr>
									<td width="15%" class="td_title">
										<spring:message code="ar.viewarcardrecord.title.beizhu"/><!--备注-->
									</td>
									<td width="35%" class="td_type" colspan="3">
										<textarea name="REMARK"  style="width:650px;height:120px">${EvsFormulaInfo.REMARK}</textarea>
									</td>
								</tr>
								<tr>
									<td width="15%" class="td_title">
										<spring:message code="hrm.empinfo.UPDATED_BY"/><!--变更者-->
									</td>
									<td width="35%" class="td_type">
										${EvsFormulaInfo.UPDATED_BY}
									</td>
									<td width="15%" class="td_title">
										<spring:message code="hrm.empinfo.UPDATE_DATE"/><!--变更时间-->
									</td>
									<td width="35%" class="td_type">
									   	${EvsFormulaInfo.UPDATE_DATE}
									</td>
								</tr>
							</table>	
				</table>
			</div>
	  	</form>	
