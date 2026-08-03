<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script type="text/javascript">
function validateAddClockInfoCallback(form,callback) {	


    var $form = $(form);	
	if (!$form.valid()) {
		return false;
	}
	var $form = $(form);	
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

<div class="panel">
	<h1><spring:message code="ess.recordTest.title.RECORDTEST"/><!--打卡记录--></h1>
	<div>
	<form method="post" action="/ess/recordTest/addRecordTest" class="pageForm required-validate" onsubmit="return validateAddClockInfoCallback(this,navTabAjaxDone);">
		
	   <div class="formBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">
								<spring:message code="ess.addRecordTest.add"/><!--打卡-->
							</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
				
	   <table class="user_table" width="100%" layoutH="0" border="1" cellpadding="2" cellspacing="1">
			<tr>
				<td width="20%" class="td_title"><spring:message code="public.title.empIdAndName"/><!-- 工号/姓名 --></td>
				<td width="20%" class="td_type">
				    ${personInfo.EMPID} / ${personInfo.LOCAL_NAME}
                    <input id="PERSON_ID" name="PERSON_ID" type="hidden" size="30"
						   value="${personInfo.PERSON_ID}" />	
				</td>
				<td width="20%" class="td_title"><spring:message code="public.title.positionName"/><!--职岗位--></td>
				<td width="40%" class="td_type">
				    ${personInfo.POSITION_NAME}
				</td>
			</tr>
			<tr>
			    <td width="20%" class="td_title"><spring:message code="ess.recordTest.title.RECORD_TEST_ADD"/><!--工作地点--></td>
			    <td width="20%" class="td_type" colspan="3">
			    <input type="text" id="RECORD_TEST_ADD"   name="RECORD_TEST_ADD"   class="required" value="${TO_TIME_HOUR}" ></td>
			    </td>
			    </tr>
			    <tr>
				<td width="20%" class="td_title"><spring:message code="ess.recordTest.title.RECORD_TEST_REMARK"/><!--备注信息--></td>
				<td width="40%" class="td_type" colspan="3"> <textarea name="RECORD_TEST_REMARK" cols="80" rows="2"></textarea>
				</td>
			</tr>		
	</table>	
    <div id="evectionApplyAffirmView" style="border:10;overflow:auto;bottom:10;right:100;width:300;z-index:1;"></div>
  </form>	
</div>
</div>