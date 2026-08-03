<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageContent">
	<form method="post" action="/pa/salarycode/updateSalaryCodeInfo"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent nowrap">
		    <dl>
				<dt>
					<spring:message code="pa.insurance.title.projectType"/><!--项目类型-->
				</dt>
				<dd>
					<select id="ITEM_TYPE" name="ITEM_TYPE" disabled="disabled"> 
						<option value="">全部</option>
						<option value="1" <c:if test="${salaryCodeInfo.ITEM_TYPE eq 1}">selected</c:if>>
							标准项目
						</option>
						<option value="2" <c:if test="${salaryCodeInfo.ITEM_TYPE eq 2}">selected</c:if>>
							支付调整项目
						</option>
						<option value="3" <c:if test="${salaryCodeInfo.ITEM_TYPE eq 3}">selected</c:if>>
							支付例外项目
						</option>
						<option value="4" <c:if test="${salaryCodeInfo.ITEM_TYPE eq 4}">selected</c:if>>
							扣除调整项目
						</option>
						<option value="5" <c:if test="${salaryCodeInfo.ITEM_TYPE eq 5}">selected</c:if>>
							扣除例外项目
						</option>
						<option value="6" <c:if test="${salaryCodeInfo.ITEM_TYPE eq 6}">selected</c:if>>
							计算项目
						</option>
						</select>
				</dd>
			</dl>
			<dl>
				<dt><spring:message code="pa.insurance.title.projectID"/><!--项目ID-->:</dt>
				<dd>
				    <%-- <c:if test="${salaryCodeInfo.ITEM_ID ne ''}"> --%>
					<input id="ITEM_ID" name="ITEM_ID" value="${salaryCodeInfo.ITEM_ID }" readonly="readonly">
					<%-- </c:if>
					<c:if test="${salaryCodeInfo.ITEM_ID eq ''}">
					<input id="ITEM_ID" name="ITEM_ID" value="${salaryCodeInfo.ITEM_ID }">
					</c:if>
					<input name="NO" type="hidden" id="ITEM_NO" value="${salaryCodeInfo.ITEM_NO }"  />
					<c:if test="${salaryCodeInfo.PROJECT_TYPE eq '基础项目'}">
					<input name="PROJECT_TYPE" type="hidden" id="PROJECT_TYPE" value="1"/>
					</c:if>
					<c:if test="${salaryCodeInfo.PROJECT_TYPE eq '输入项目'}">
					<input name="PROJECT_TYPE" type="hidden" id="PROJECT_TYPE" value="2"/>
					</c:if>
					<c:if test="${salaryCodeInfo.PROJECT_TYPE eq '计算项目'}">
					<input name="PROJECT_TYPE" type="hidden" id="PROJECT_TYPE" value="3"/>
					</c:if> --%>
					
				</dd>
			</dl>
			
<!-- 			<dl> -->
<!-- 				<dt><spring:message code="pa.insurance.title.projectID"/>项目IDCHRS1.0:</dt> -->
<!-- 				<dd> -->
<!-- 					<input id="MAP_CODE" name="MAP_CODE" value="${salaryCodeInfo.MAP_CODE }"> -->
<!-- 				</dd> -->
<!-- 			</dl> -->
			
			<ait:SyLanguage languageNo="${salaryCodeInfo.ITEM_NO}" />

			<dl>
				<dt><spring:message code="pa.insurance.title.dataType"/><!--数据类型-->:</dt>
				<dd>
					<select id="DATA_TYPE" name="DATA_TYPE" disabled="disabled">
						<option value="NUMBER(14,4)" <c:if test="${salaryCodeInfo.DATA_TYPE eq 'NUMBER(14,4)' }" >selected</c:if> >
						<spring:message code="pa.insurance.title.numberType"/><!--数字类型--></option>
						<option value="VARCHAR(100)" <c:if test="${salaryCodeInfo.DATA_TYPE eq 'VARCHAR(100)' }" >selected</c:if> >
						<spring:message code="pa.insurance.title.varcharType"/><!--字符类型--></option>
					</select>
<!-- 					<input type="hidden" name="DATA_TYPE" id="DATA_TYPE" value="${salaryCodeInfo.DATA_TYPE}"/> -->
				</dd>
			</dl>
			<dl style="height:auto">
				<table>
					<tr>
						<td class="td_title" style="width:122px;"><spring:message code="pa.insurance.title.description"/><!--描述-->:</td>
						<td class="td_type">
							<textarea cols="100" rows="4" readonly="no" class="l-textarea" name="DESCR" id="DESCR" style="width:400px">${salaryCodeInfo.DESCR }</textarea>
						</td>
					</tr>
				</table>
			</dl>
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