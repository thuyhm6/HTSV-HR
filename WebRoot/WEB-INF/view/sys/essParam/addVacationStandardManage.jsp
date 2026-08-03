<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
 <script type="text/javascript">
  <!--
	function validateCallback_saveVacationStandardManage(form, callback) {


	var $form = $(form);
	
	if (!$form.valid()) {
		return false;
	}
	var workarea= $("#seach_WORK_AREA_ESS0229").val();
   	if(workarea==""||workarea==null){
   		alertMsg.error("所属地区不能为空");
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
	<form method="post" action="/sys/essParam/saveVacationStandardManage" class="pageForm required-validate" 
		onsubmit="return validateCallback_saveVacationStandardManage(this,dialogAjaxDone);">
		<div class="pageFormContent" layoutH="90">
			<dl>
				<dt>
					<spring:message code="org.orgManage.title.ownArea"/><!--所属地区-->:
				</dt>
				<dd>
					<select name="seach_WORK_AREA" id="seach_WORK_AREA_ESS0229">
						<option value=""><!--请选择-->
							--<spring:message code="sys.affirm.title.choose"/>--
                    	</option>
                		<c:forEach items="${workAreaList}" var="item" varStatus="j">	
                     		<option value="${item.WORK_AREA }">${item.WORK_AREA_NAME }</option>
                     	</c:forEach>
                    </select>
				</dd>
			</dl>
			<dl>
				<dt>
					<spring:message code="heran.ess.viewLikeLeaveApplyInfo.xiajiajizhun"/><!--休假基准-->:
				</dt>
				<dd>
					  <textarea name="seach_STANDARD_EXPLAIN" rows="10" cols="42">
						</textarea>  
				</dd>
			</dl>
		</div>		
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">
					<spring:message code="public.title.submit"/><!--提交--></button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">
					<spring:message code="public.title.cancle"/><!--取消--></button></div></div></li>
			</ul>
		</div>
	</form>
</div>
