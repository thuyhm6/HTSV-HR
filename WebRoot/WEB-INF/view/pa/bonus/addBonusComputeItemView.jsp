<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageContent">
	<form method="post" action="/pa/bonus/addBonusComputeItemInfo"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<dl>
				<dt>
					<spring:message code="pa.insurance.title.projectID" />
					<!--项目ID-->
					:
				</dt>
				<dd>
					<input name="ITEM_ID" type="text"
						value="${bonusComputeItemInfo.ITEM_ID }"
						class="required alphanumeric" maxlength="30" />
				</dd>
			</dl>
			<ait:SyLanguage />
			<dl>
				<dt>
					<spring:message code="pa.insurance.title.dataType" />
					<!--数据类型-->
					:
				</dt>
				<dd>
					<select id="DATATYPE" name="DATATYPE">
						<option value="NUMBER(14,4)"
							<c:if test="${bonusComputeItemInfo.DATATYPE == 'NUMBER(14,4)' }">selected</c:if>>
							NUMBER(14,4)
						</option>
						<option value="VARCHAR(100)"
							<c:if test="${bonusComputeItemInfo.DATATYPE == 'VARCHAR(100)' }">selected</c:if>>
							VARCHAR(100)
						</option>
					</select>
				</dd>
			</dl>
			<dl>
				<dt>
					<spring:message code="pa.insurance.title.description" />
					<!--描述-->
					:
				</dt>
				<dd>
					<textarea cols="100" rows="4" class="l-textarea" name="DESCR"
						id="DESCR" style="width: 400px" maxlength="1000">${bonusComputeItemInfo.DESCR }</textarea>
				</dd>
			</dl>

		</div>
		<div class="formBar">
			<ul>
				<!--<li><a class="buttonActive" href="javascript:;"><span>保存</span></a></li>-->
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">
								<spring:message code="pa.insurance.title.submit" />
								<!--保存-->
							</button>
						</div>
					</div>
				</li>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" class="close">
								<spring:message code="public.title.cancle" />
								<!--取消-->
							</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</form>
</div>
