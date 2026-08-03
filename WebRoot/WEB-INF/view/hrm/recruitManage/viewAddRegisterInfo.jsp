<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function validateAddRegisterInfoCallback(){
	var $form = $("#viewAddRegisterInfoForm");	
	alertMsg.confirm("<spring:message code="hrm.empinfo.SAVE_CONFIRM" />",
  		{okCall:function(){
		  	$.ajax({
  				type: $form.method || 'POST',
  				url:$form.attr("action"),
  				data:$form.serializeArray(),
  				dataType:"json",
  				cache: false,
  				success: dialogAjaxDone,
  				error: DWZ.ajaxError
  			});
  		}});
	return false;
}
</script>
<form id="viewAddRegisterInfoForm" method="post" action="/hrm/recruitManage/addRegisterInfo" class="pageForm required-validate"
			onsubmit="return validateAddRegisterInfoCallback();">
			<div>
							<table  class="user_table" width="100%">
							
								<tr style="display:none">
									<td width="15%" class="td_title"><spring:message code="hrm.empinfo.Registration_type" /><!-- 注册类型 --></td>
									<td width="85%" class="td_type">
										<c:if test="${FLAG eq '1'}">
											<input type="text" value="<spring:message code='hrm.empinfo.RECRUITMENT.Z' />" class="required" readonly="true" size="35"/><!-- 录用 -->
											<input type="hidden" id="REGISTER_TYPE" name="REGISTER_TYPE" value="14014410"/>
										</c:if>
										<c:if test="${FLAG eq '2'}">
											<input type="text" value="<spring:message code='ar.menu.title.changeover' />" class="required" readonly="true" size="35"/><!-- 转正 -->
											<input type="hidden" id="REGISTER_TYPE" name="REGISTER_TYPE" value="80000057"/>
										</c:if>
										<c:if test="${FLAG eq '3'}">
											<input type="text" value="<spring:message code='ar.menu.title.changeover' />" class="required" readonly="true" size="35"/><!-- 转正 -->
											<input type="hidden" id="REGISTER_TYPE" name="REGISTER_TYPE" value="80000058"/>
										</c:if>
										<input type="hidden" id="FLAG" name="FLAG" value="${FLAG }"/>
									</td>
								</tr>
								<tr>
									<td width="15%" class="td_title"><spring:message code="hrm.empinfo.REGISTRATION_DATE" /><!-- 注册日 --></td>
									<td width="85%" class="td_type">
										<c:if test="${FLAG ne '3'}">
											<input type="text" id="REGISTER_DATE" name="REGISTER_DATE" class="Wdate required" size="35" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})" value=""/>
										</c:if>
										<c:if test="${FLAG eq '3'}">
											<input type="text" id="REGISTER_DATE" name="REGISTER_DATE" value=""/>
										</c:if>
									</td>
								</tr>
							</table>
			</div>
		<div class="formBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent"><!--保存-->
								<button type="submit">
									<spring:message code="button.sys.affirm.save" /><!-- 保存 -->
								</button>
							</div>
						</div>
					</li>
					<li>
						<div class="button">
							<div class="buttonContent"><!--提交-->
								<button type="button" class="close">
									<spring:message code="hrm.empinfo.close" /><!-- 关闭 -->
								</button>
							</div>
						</div>
					</li>
				</ul>
		</div>
	  	</form>