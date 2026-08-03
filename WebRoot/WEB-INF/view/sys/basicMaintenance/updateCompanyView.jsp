<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageContent">
	<form method="post" action="/sys/basicMaintenance/updateCompanyInfo" class="pageForm required-validate" onsubmit="return validateCallback(this,navTabAjaxDone);">
		<input name="CPNY_NO" type="hidden" value="${companyInfo.CPNY_NO}" />
		<input type="hidden" name="NO" value="${companyInfo.CPNY_NO}"/>
		<div class="pageFormContent nowrap" layoutH="97">
			<dl>
				<dt><spring:message code="sys.basicMaint.title.companyName"/><!--公司-->ID:</dt>
				<dd>
					<input type="text" name="CPNY_ID" value="${companyInfo.CPNY_ID}" size="30"  />
				</dd>			
			</dl>			 
			<dl>
				<dt><spring:message code="sys.basicMaint.title.companyArea"/><!--公司区域-->:</dt>
				<dd>
					<input type="text" name="CPNY_LOCATION" value="${companyInfo.CPNY_LOCATION}" size="30"  />
				</dd>				
			</dl>
			 <ait:SyLanguage languageNo="${companyInfo.CPNY_NO}"/>
			<dl>
				<dt><spring:message code="sys.basicMaint.title.companyAddress"/><!--公司地址-->:</dt>
				<dd>
					<input type="text" name="CPNY_ADDR" value="${companyInfo.CPNY_ADDR}" size="30"  />
				</dd>				
			</dl>
			<dl>
				<dt><spring:message code="sys.basicMaint.title.companyIntroduction"/><!--公司介绍-->:</dt>
				<dd>
					<input type="text" name="CPNY_INTRO" value="${companyInfo.CPNY_INTRO}" size="30"  />
				</dd>				
			</dl>
			<dl>
				<dt><spring:message code="sys.basicMaint.title.companyHistory"/><!--公司历史-->:</dt>
				<dd>
					<input type="text" name="CPNY_HISTORY" value="${companyInfo.CPNY_HISTORY}" size="30"  />
				</dd>				
			</dl>
			<dl>
				<dt><spring:message code="sys.basicMaint.title.companyNetAddress"/><!--公司网址-->:</dt>
				<dd>
					<input type="text" name="CPNY_WEB_ADDR" value="${companyInfo.CPNY_WEB_ADDR}" size="30"  />
				</dd>				
			</dl>
			<dl>
				<dt><spring:message code="sys.basicMaint.title.companyTelPhoneNo"/><!--公司电话-->:</dt>
				<dd>
					<input type="text" name="CPNY_TEL_NO" value="${companyInfo.CPNY_TEL_NO}" size="30"  />
				</dd>				
			</dl>
			<dl>
				<dt><spring:message code="sys.basicMaint.title.companyFaxNo"/><!--公司传真号-->:</dt>
				<dd>
					<input type="text" name="CPNY_FAX_NO" value="${companyInfo.CPNY_FAX_NO}" size="30"  />
				</dd>				
			</dl>
			<dl>
				<dt>
					<spring:message code="sys.arAffirmPost.title.navgationId"/><!--国家ID-->:
				</dt>
				<dd>
					<select name="OPERATION_ID" id="OPERATION_ID"
						style="width: 180px; position: static; visibility: inherit;">
						<c:forEach items="${operationList}" var="operation">
							<option value="${operation.OPERATION_ID}">
								${operation.OPERATION_NAME}
							</option>
						</c:forEach>
					</select>
				</dd> 
			</dl>			
			<dl>
				<dt><spring:message code="sys.arAffirmPost.title.ifUserGGSCode"/><!--是否启用GGS-->:</dt>
				<dd> 
					<select class="combox" name="GGS_YN" id="GGS_YN">
						<option value="N" <c:if test="${companyInfo.GGS_YN eq 'N'}" >selected</c:if>>
						<spring:message code="sys.arAffirmPost.title.enable"/><!--不启用--></option>
						<option value="Y" <c:if test="${companyInfo.GGS_YN eq 'Y'}" >selected</c:if>>
						<spring:message code="sys.arAffirmPost.title.able"/><!--启用--></option>
					</select>
				</dd>
			</dl>				
			<dl>
				<dt><spring:message code="sys.arAffirmPost.title.ableStatus"/><!--启用状态-->:</dt>
				<dd> 
					<select class="combox" name="ACTIVITY" id="ACTIVITY">
						<option value="2" <c:if test="${companyInfo.ACTIVITY eq 2}" >selected</c:if>>
						<spring:message code="sys.arAffirmPost.title.enable"/><!--不启用--></option>
						<option value="1" <c:if test="${companyInfo.ACTIVITY eq 1}" >selected</c:if>>
						<spring:message code="sys.arAffirmPost.title.able"/><!--启用--></option>
					</select>
				</dd>
			</dl>						
		</div>		
		<div class="formBar">
			<ul>
				<li><div class="button"><div class="buttonContent"><button type="submit">
				<spring:message code="public.title.submit"/><!--提交--></button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">
				<spring:message code="public.title.cancle"/><!--取消--></button></div></div></li>
			</ul>
		</div>
	</form>
</div>