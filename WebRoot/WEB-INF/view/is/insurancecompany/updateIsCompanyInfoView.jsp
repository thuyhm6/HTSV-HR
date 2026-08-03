<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
	
</script>
<div class="pageContent">
	<form id="updateIsCompanyInfoView" method="post" action="/is/insurancecompany/updateIsCompanyInfo" 
		class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		
		
		<div class="pageFormContent nowrap" layoutH="56">
			
			<dl>
				<dt><spring:message code="pa.wagebase.title.companyName"/><!-- 公司名称 --></dt>
				<dd>
					<input type="hidden" name="CP_NO" value="${item.CP_NO}"/>
					<input type="text" name="CP_NAME" value="${item.CP_NAME }" class="required">
				</dd>
			</dl>
			<dl>
				<dt><spring:message code="hr.viewPersonalInfo.title.fulidiqu"/><!-- 公司地区 --></dt>
				<dd>
<!-- 					<input type="text" name="CP_ADDR" value="${item.CP_ADDRESS }" class="required"> -->
					<ait:SelectSyCodeByCpnyID id="CP_ADDR" name="CP_ADDR" parentNo="216736" selected="${item.CP_ADDRESS}" cnpyID="${defaultCpny}" limit="all"/>
				</dd>
			</dl>
			<dl>
				<dt><spring:message code="hr.viewCondSql.title.FEIYONG"/><!-- 维护费 --></dt>
				<dd>
					<input type="text" name="COST" value="${item.COST }" class="digits required">
				</dd>
			</dl>
			<c:if test="${defaultCpny eq 'TSTO'}">
			<dl>
				<dt><spring:message code="is.company.title.PERSON_TYPE"/><!-- 人员类型--></dt>
				<dd>
				    <select name="PERSON_TYPE">
                        <option value="0"<c:if test="${item.PERSON_TYPE=='0'}">selected</c:if>>促销员</option>
                        <option value="1"<c:if test="${item.PERSON_TYPE=='1'}">selected</c:if>>其他</option>
                    </select>
				</dd>
				</dl>
				</c:if>
				<c:if test="${defaultCpny ne 'TSTO'}">
			<dl>
				<dt><spring:message code="is.company.title.PERSON_TYPE"/><!-- 人员类型 --></dt>
				<dd>
                       <ait:SelectSyCodeByCpnyID id="PERSON_TYPE" name="PERSON_TYPE" parentNo="1368" selected="${item.PERSON_TYPE}" cnpyID="${defaultCpny}" limit="all"/>
				</dd>
			</dl>
			</c:if>
			<dl>
				<dt><spring:message code="ar.viewarcardrecord.title.beizhu"/><!-- 备注 --></dt>
				<dd>
					<input type="text" name="REMARK" value="${item.REMARK }">
				</dd>
			</dl>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit"><!-- 提交 --><spring:message code="public.title.submit"/></button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close"><!-- 取消 --><spring:message code="public.title.cancle"/></button></div></div></li>
			</ul>
		</div>
	</form>
</div>
