<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageContent">
	<form method="post" action="/pa/salarycode/updateSalaryCodeMappingInfo"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent nowrap">
		    <dl>
				<dt>法人</dt>
				<dd>
					<input id="CPNY_ID" name="CPNY_ID" value="${salaryCodeInfo.CPNY_ID}" readonly="readonly">
				</dd>
			</dl>
		    <dl>
				<dt>
					<spring:message code="pa.insurance.title.projectType"/><!--项目类型-->
				</dt>
				<dd>
					<select id="PROJECT_TYPE" name="PROJECT_TYPE" disabled="disabled">
						<option value="1"<c:if test="${salaryCodeInfo.PROJECT_TYPE eq '基础项目'}">selected</c:if>>
							<spring:message code="pa.wagebase.title.basicItemInfo"/><!--基础项目-->
						</option>
						<option value="2"<c:if test="${salaryCodeInfo.PROJECT_TYPE eq '输入项目'}">selected</c:if>>
							<spring:message code="pa.salary.title.inputItem"/><!--输入项目-->
						</option>
						<option value="3"<c:if test="${salaryCodeInfo.PROJECT_TYPE eq '计算项目'}">selected</c:if>>
							<spring:message code="pa.salary.title.caculateItem"/><!--计算项目-->
						</option>
						<option value="4"<c:if test="${salaryCodeInfo.PROJECT_TYPE eq '保险输入项目'}">selected</c:if>>
							保险输入项目
						</option>
						<option value="5"<c:if test="${salaryCodeInfo.PROJECT_TYPE eq '保险计算项目'}">selected</c:if>>
							保险计算项目
						</option>
					</select>
				</dd>
			</dl>
			<dl>
				<dt><spring:message code="pa.insurance.title.projectID"/><!--项目ID--></dt>
				<dd>
					<input id="ITEM_ID" name="ITEM_ID" value="${salaryCodeInfo.ITEM_ID }" readonly="readonly">
					<input name="ITEM_NO" type="hidden" id="ITEM_NO" value="${salaryCodeInfo.ITEM_NO }"  />
					<c:if test="${salaryCodeInfo.PROJECT_TYPE eq '基础项目'}">
					<input name="PROJECT_TYPE" type="hidden" id="PROJECT_TYPE" value="1"/>
					</c:if>
					<c:if test="${salaryCodeInfo.PROJECT_TYPE eq '输入项目'}">
					<input name="PROJECT_TYPE" type="hidden" id="PROJECT_TYPE" value="2"/>
					</c:if>
					<c:if test="${salaryCodeInfo.PROJECT_TYPE eq '计算项目'}">
					<input name="PROJECT_TYPE" type="hidden" id="PROJECT_TYPE" value="3"/>
					</c:if>
					<c:if test="${salaryCodeInfo.PROJECT_TYPE eq '保险输入项目'}">
					<input name="PROJECT_TYPE" type="hidden" id="PROJECT_TYPE" value="4"/>
					</c:if>
					<c:if test="${salaryCodeInfo.PROJECT_TYPE eq '保险计算项目'}">
					<input name="PROJECT_TYPE" type="hidden" id="PROJECT_TYPE" value="5"/>
					</c:if>
					
				</dd>
			</dl>
			
			<dl>
				<dt>项目IDCHRS1.0</dt>
				<dd>
					<input id="MAP_CODE" name="MAP_CODE" value="${salaryCodeInfo.MAP_CODE }">
				</dd>
			</dl>
			<dl>
				<dt>是否传递财务</dt>
				<dd>
					<select id="TO_FINANCE_FLAG" name="TO_FINANCE_FLAG">
				 		<option value='Y'<c:if test="${salaryCodeInfo.TO_FINANCE_FLAG eq '1'}">selected</c:if>>是 </option>
				 		<option value='N'<c:if test="${salaryCodeInfo.TO_FINANCE_FLAG eq '0'}">selected</c:if>>否 </option>
				 	</select>
				</dd>
			</dl>
			
			<ait:SyLanguage1 languageNo="${salaryCodeInfo.ITEM_NO}"/>

			<div class="formBar" layoutH="106">
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
		</div>
	</form>
</div>