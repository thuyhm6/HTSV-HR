<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageContent">
	<form method="post" action="/sys/attendancesetting/addAffirmAttendItemInfo"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, dialogAjaxDone);">
		<input name="ACTIVITY" type="hidden" id="ACTIVITY" value = "0"/>
		<input name="CPNY_ID" type="hidden" id="CPNY_ID" value = "${CPNY_ID }"/>
		<input name="GROUP_NO" id="GROUP_NO" type="hidden" value="constant"/>
		<input name="ACTIVITY_TYPE" type="hidden" id="ACTIVITY_TYPE" value = "2"/>
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
					<select id="PROJECT_TYPE" name="PROJECT_TYPE" disabled="disabled">
						<option value="1"<c:if test="${attendItemInfo.PROJECT_TYPE eq '明细项目'}">selected</c:if>>
							<spring:message code="ar.viewitemparameter.title.mingxixiangmuku"/><!--明细项目-->
						</option>
						<option value="2"<c:if test="${attendItemInfo.PROJECT_TYPE eq '汇总项目'}">selected</c:if>>
							<spring:message code="ar.viewsummaryparameteritem.title.huizongxiangmu"/><!--汇总项目-->
						</option>
					</select>
				</dd>
			</dl>
			<dl>
				<dt><spring:message code="pa.insurance.title.projectName"/><!--项目名称-->:</dt>
				<dd>
					<input id="ITEM_NAME" name="ITEM_NAME" value="${attendItemInfo.ITEM_NAME }" readonly="readonly">
				</dd>
			</dl>
			<dl>
				<dt><spring:message code="pa.insurance.title.projectID"/><!--项目ID-->:</dt>
				<dd>
					<input id="ITEM_ID" name="ITEM_ID" value="${attendItemInfo.ITEM_ID }" readonly="readonly">
					<input name="ITEM_NO" type="hidden" id="ITEM_NO" value="${attendItemInfo.ITEM_NO }"  />
					<c:if test="${attendItemInfo.PROJECT_TYPE eq '明细项目'}">
					<input name="PROJECT_TYPE" type="hidden" id="PROJECT_TYPE" value="1"/>
					</c:if>
					<c:if test="${attendItemInfo.PROJECT_TYPE eq '汇总项目'}">
					<input name="PROJECT_TYPE" type="hidden" id="PROJECT_TYPE" value="2"/>
					</c:if>
				</dd>
			</dl>
		   <dl>
				<dt><spring:message code="pa.insurance.title.company"/><!--公司-->:</dt>
				<dd>
					<input type="text" name="CPNY_ID" id="CPNY_ID" value="${CPNY_ID }" readonly="readonly"/>
				</dd>
			</dl>
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