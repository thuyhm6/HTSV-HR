<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<div class="pageContent">
	<form id="addEmpMapping" method="post" action="/sys/empMapping/addEmpMapping" class="pageForm required-validate" 
		onsubmit="return validateCallback(this, dialogAjaxDone)">
		<div class="pageFormContent nowrap">
						<table class="user_table margin_b" style="width:100%;">
							<tr>
								<td class="td_title" style="width:20%;"></td>
								<td class="td_title" style="width:40%;align:center;">新社号</td>
								<td class="td_title" style="width:40%;align:center;">旧社号</td>
							</tr>
							<tr>
								<td class="td_title"><!-- 工号 -->
									<spring:message code="public.title.empId"/>
								</td>
								<td class="td_type">
									<input id="personId" name="dwz.person.personId" value="" type="hidden" lookupGroup="person"/>
									<input name="dwz.person.empId" type="text" class="required"  readOnly lookupGroup="person"/>
									<a class="btnLook" href="/ar/attendanceMintenance/viewEmpCalendarList?firstFlag=1&limit=hr&pageNum=1" lookupGroup="person"><!-- 查找带回 -->
										<spring:message code="ar.alert.message.viewattendencekeeper.chazhaodaihui"/>
									</a>
								</td>
								<td class="td_type">
									<input id="personId" name="dwz.person_old.personId" value="" type="hidden" lookupGroup="person_old"/>
									<input name="dwz.person_old.empId" type="text" class="required"  readOnly lookupGroup="person_old"/>
									<a class="btnLook" href="/ar/attendanceMintenance/viewEmpCalendarList?firstFlag=1&limit=hr&pageNum=1" lookupGroup="person_old"><!-- 查找带回 -->
										<spring:message code="ar.alert.message.viewattendencekeeper.chazhaodaihui"/>
									</a>
								</td>
							</tr>
							<tr>
								<td class="td_title"><!-- 姓名 -->
									<spring:message code="public.title.name"/>
								</td>
								<td class="td_type">
									<input name="dwz.person.empName" type="text" readOnly lookupGroup="person"/>
								</td>
								<td class="td_type">
									<input name="dwz.person_old.empName" type="text" readOnly lookupGroup="person_old"/>
								</td>
							</tr>
							<tr>
								<td class="td_title"><!-- 部门 -->
									<spring:message code="public.title.deptName"/>
								</td>
								<td class="td_type">
									<input name="dwz.person.empDept" type="text" readOnly lookupGroup="person"/>
								</td>
								<td class="td_type">
									<input name="dwz.person_old.empDept" type="text" readOnly lookupGroup="person_old"/>
								</td>
							</tr>
						</table>
		</div>
		<div class="formBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="submit"><!-- 提交 -->
								保存
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