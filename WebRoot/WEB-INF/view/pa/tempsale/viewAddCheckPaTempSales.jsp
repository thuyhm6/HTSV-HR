<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function validateCallbackAddCheckPaTempSales(form, callback) {
	var $form = $("#addCheckPaTempSales");
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
<div class="pageContent">
	<form id="addCheckPaTempSales" method="post" action="/pa/tempsale/addCheckPaTempSales" class="pageForm required-validate" onsubmit="return validateCallbackAddCheckPaTempSales(this, dialogAjaxDone)">
		<div class="pageFormContent nowrap" layoutH="60">
			<div style=" float:center; display:block; margin:10px; width:450px; line-height:21px; background:#FFF;">
				<fieldset>
					<div class="searchBar" >
						<table class="searchContent">
							<tr>
								<td style="width:20px">&nbsp;</td>
								<td><!-- 工号 -->
									<spring:message code="public.title.empId"/>:
								</td>
								<td>
									<input id="personId" name="dwz.person.personId" value="" type="hidden" lookupGroup="person"/>
									<input name="dwz.person.empId" type="text" class="required"  readOnly lookupGroup="person"/> <a class="btnLook"
										href="/ar/attendanceSettings/viewKeeperList?pageNum=1"
										lookupGroup="person">
									<input type="hidden" name="ESS_AFFIRM_NO" value="${AFFIRM_NO }"/>
									<input type="hidden" name="APPLY_NO" value="${APPLY_NO }"/>
								</td>
							</tr>
							<tr>
								<td style="width:20px">&nbsp;</td>
								<td><!-- 姓名 -->
									<spring:message code="public.title.name"/>:
								</td>
								<td>
									<input name="dwz.person.empName" type="text" readOnly lookupGroup="person"/>
								</td>
							</tr>
							<tr>
								<td style="width:20px">&nbsp;</td>
								<td><!-- 部门 -->
									<spring:message code="public.title.deptName"/>:
								</td>
								<td>
									<input name="dwz.person.empDept" type="text" readOnly lookupGroup="person"/>
								</td>
							</tr>
							<tr>
								<td style="width:20px">&nbsp;</td>
								<td>Comments:</td>
								<td>
									<textarea id="CHECK_REASON" name="CHECK_REASON" rows="5" cols="60"></textarea>
                                </td>
							</tr>
						</table>
					</div>
				</fieldset>
			</div>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit"><spring:message code="heran.examineSave.title"/><!-- 保存 --></button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close"><spring:message code="public.title.cancle"/><!-- 取消 --></button></div></div>
				</li>
			</ul>
		</div>
	</form>	
</div>