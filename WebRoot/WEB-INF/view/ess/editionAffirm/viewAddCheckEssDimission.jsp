<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
</script>
<div class="pageContent">
	<form id="viewFullApplyRemarkInfo" method="post" action="/ess/editionAffirm/addCheckAffirmDimission" class="pageForm required-validate" 
		onsubmit="return validateCallback(this, dialogAjaxDone)">
		<div class="pageFormContent nowrap" layoutH="60">
			<dl>
				<dt>
					<!-- 工号 -->
									<spring:message code="public.title.empId"/>:
				</dt>
				<dd>
									<input id="ESS_AFFIRM_NO" name="ESS_AFFIRM_NO" value="${AFFIRM_NO }" type="hidden"/>
									<input id="jsonData" name="jsonData" value="" type="hidden"/>
									<input id="PAGE_FLAG" name="PAGE_FLAG" value="218296" type="hidden"/>
									<input id="APPLY_TYPE" name="APPLY_TYPE" value="218296" type="hidden"></input>
									
									<input id="personId" name="dwz.person.personId" value="" type="hidden" lookupGroup="person"/>
									<input name="dwz.person.empId" type="text" class="required"  readOnly lookupGroup="person"/>
									<a class="btnLook" href="/ar/attendanceMintenance/viewEmpCalendarList?isEmployeement=1&firstFlag=1&limit=super&pageNum=1" lookupGroup="person"><!-- 查找带回 -->
										<spring:message code="ar.alert.message.viewattendencekeeper.chazhaodaihui"/>
									</a>
				</dd>
			</dl>
			<dl>
				<dt>
					<!-- 姓名 -->
									<spring:message code="public.title.name"/>:
				</dt>
				<dd>
					<input name="dwz.person.empName" type="text" readOnly lookupGroup="person"/>
				</dd>
			</dl>
			<dl>
				<dt>
					<!-- 部门 -->
									<spring:message code="public.title.deptName"/>:
				</dt>
				<dd>
					 <input name="dwz.person.empDept" type="text" readOnly lookupGroup="person"/>
				</dd>
			</dl>
			<dl style="height:auto">
				<table>
					<tr>
						<td class="td_title" style="width:122px;">Comments:</td>
						<td class="td_type">
							<textarea id="CHECK_REASON" name="CHECK_REASON" rows="5" cols="60"></textarea>
                        </td>
					</tr>
				</table>
			</dl>
		</div>
		<div class="formBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="submit"><!-- 提交 -->
								<spring:message code="public.title.submit"/>
							</button>
						</div>
					</div>
				</li>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" class="close"><!-- 取消 -->
								<spring:message code="public.title.cancle"/>
							</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</form>
</div>