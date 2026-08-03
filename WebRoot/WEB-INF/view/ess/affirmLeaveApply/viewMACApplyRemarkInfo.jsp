<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
</script>
<div class="pageContent">
	<form id="viewLeaveApplyRemarkInfo" method="post" action="/ess/affirmApply/addApplyMACCheckList" class="pageForm required-validate" 
		onsubmit="return validateCallback(this, dialogAjaxDone)">
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
									<input id="APPLY_NO" name="APPLY_NO" value="${APPLY_NO }" type="hidden"/>
									<input id="PAGE_FLAG" name="PAGE_FLAG" value="ARMAC" type="hidden"/>
									<input id="ESS_AFFIRM_NO" name="ESS_AFFIRM_NO" value="${ESS_AFFIRM_NO }" type="hidden"/>
									<input id="APPLY_TYPE" name="APPLY_TYPE" value="${APPLY_TYPE }" type="hidden"/>
									<input id="jsonData" name="jsonData" value="" type="hidden"/>
									<input id="PERSON_ID" name="PERSON_ID" value="" type="hidden"/>
									<input id="personId" name="dwz.person.personId" value="" type="hidden" lookupGroup="person"/>
									<input name="dwz.person.empId" type="text" class="required"  readOnly lookupGroup="person"/>
									<a class="btnLook" href="/ar/attendanceMintenance/viewEmpCalendarList?isEmployeement=1&firstFlag=1&limit=super&pageNum=1" lookupGroup="person"><!-- 查找带回 -->
										<spring:message code="ar.alert.message.viewattendencekeeper.chazhaodaihui"/>
									</a>
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