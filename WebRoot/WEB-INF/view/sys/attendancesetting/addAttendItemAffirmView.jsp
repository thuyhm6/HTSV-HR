<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageContent">
	<form method="post" action="/sys/attendancesetting/addAffirmAttendItemInfo"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, dialogAjaxDone);">
		<input type="hidden" name="CPNY_ID" id="CPNY_ID" value="${CPNY_ID}"/>
		<input type="hidden" name="ACTIVITY" id="ACTIVITY" value="0"/>
		<input type="hidden" name="ACTIVITY_TYPE" id="ACTIVITY_TYPE" value="1"/>
		<div class="pageFormContent nowrap" layoutH="60">
		    
			<dl>
				<dt>
					<spring:message code="pa.salary.title.affirm_people"/><!--申请人-->:
				</dt>
				<dd>
					${personName}
                    <input id="PERSON_ID" name="PERSON_ID" type="hidden" size="30"
						   value="${PERSON_ID}" />	
				</dd>
			</dl>
			<dl>
				<dt>
					<spring:message code="ess.infoApply.title.essApplyTime"/><!--申请时间-->:
				</dt>
				<dd>
					${date }
				</dd>
			</dl>
		    <dl>
				<dt>
					<spring:message code="pa.insurance.title.projectType"/><!--项目类型-->
				</dt>
				<dd>
					<select id="PROJECT_TYPE" name="PROJECT_TYPE">
						<option value="1">
							<spring:message code="ar.attenditem.title.mingxixiangmu"/><!--数字类型-->
						</option>
						<option value="2">
							<spring:message code="ar.viewsummaryparameteritem.title.huizongxiangmu"/><!--字符类型-->
						</option>
					</select>
				</dd>
			</dl>
<!-- 			<dl> -->
<!-- 				<dt> -->
<!-- 					<spring:message code="pa.wagebase.title.basicItemID"/>基础项目ID: -->
<!-- 				</dt> -->
<!-- 				<dd> -->
<!-- 					<input name="ITEM_ID" type="text" id="ITEM_ID" class="required alphanumeric" maxlength="30"/> -->
					
<!-- 				</dd> -->
<!-- 			</dl> -->
			<ait:SyLanguage />
<!-- 			<dl> -->
<!-- 				<dt> -->
<!-- 					<spring:message code="pa.insurance.title.dataType"/>数据类型: -->
<!-- 				</dt> -->
<!-- 				<dd> -->
<!-- 					<select id="DATA_TYPE" name="DATA_TYPE"> -->
<!-- 						<option value="NUMBER(14,4)" selected> -->
<!-- 							<spring:message code="pa.insurance.title.numberType"/>数字类型 -->
<!-- 						</option> -->
<!-- 						<option value="VARCHAR(100)"> -->
<!-- 							<spring:message code="pa.insurance.title.varcharType"/>字符类型 -->
<!-- 						</option> -->
<!-- 					</select> -->
<!-- 				</dd> -->
<!-- 			</dl> -->
			<dl style="height:auto">
								<table>
								<tr>
								<td class="td_title" style="width:122px;"><spring:message code="pa.salarycode.affirm.reason"/><!--申请事由-->:</td>
								<td class="td_type">
									<textarea cols="100" rows="4" class="l-textarea" name="AFFIRM_REASON"
						id="AFFIRM_REASON" style="width: 400px"></textarea>
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
								<button type="submit">
									<spring:message code="public.title.submit"/><!-- 提交 -->
								</button>
							</div>
						</div>
					</li>
					<li>
						<div class="button">
							<div class="buttonContent">
								<button type="button" class="close">
									<spring:message code="public.title.cancle"/><!--取消-->
								</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		
	</form>
</div>