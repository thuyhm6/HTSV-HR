<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageContent">
     <form method="post" action="/pa/salaryCanShu/addNianZhongJiangYuTiInfo" class="pageForm required-validate" 
     	onsubmit="return validateCallback(this, dialogAjaxDone);" id="">
		<div class="pageFormContent nowrap"> 
			<dl>
				<dt><spring:message code="pa.salary.canShu.nianDu"/><!--年度--></dt>
				<dd>
					 <ait:date yearName="PQD_ND" />			
				</dd>
			</dl>
		
		    <dl>
				<dt><spring:message code="pa.salary.canShu.faRen"/><!--法人--></dt>
				<dd>
						<c:if test="${authority eq '1'}">
							<select id="seach_faren" name="PQD_FR">
								<c:forEach items="${companyList}" var="item" varStatus="i">
									<option value="${item.CPNY_ID }" <c:if test="${CPNY_ID eq item.CPNY_ID}">selected</c:if>>${item.CPNY_ID }</option>
								</c:forEach>
							</select>
						</c:if>
						<c:if test="${authority eq '0'}">
							${CPNY_ID}
							<input type="hidden" id="seach_faren" name="PQD_FR" value="${CPNY_ID}"/>
						</c:if>
				</dd>
			</dl>
			
			<dl>
				<dt><spring:message code="pa.salary.canShu.jiTiBiLv"/><!--计提比率--></dt>
				<dd>
					  <input type="text" name="PQD_JTBL" id="PQD_JTBL" value="" class="number required textInput" />%						
				</dd>
			</dl>
			
			<dl>
				<dt><spring:message code="pa.salary.canShu.zhiFuYueFen"/><!--支付月份--></dt>
				<dd>
					 <ait:date yearName="PQD_YF_YEAR" monthName="PQD_YF_MONTH"/>				
				</dd>
			</dl>
			
			<dl>
				<dt>备注</dt>
				<dd>
					 <input type="text" name="REMARK" value="" class="textInput" size="55" maxlength="100"/>
				</dd>
			</dl>
			</div>
			<div class="formBar">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent"><button type="submit">
					<spring:message code="public.title.submit"/><!-- 提交 --></button></div></div></li>
					<li><div class="button"><div class="buttonContent"><button type="button" class="close">
					<spring:message code="public.title.cancle"/><!--取消--></button></div></div></li>
				</ul>
			</div> 
        </form>
 </div>
