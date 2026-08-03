<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<div class="pageContent"> 
	<form method="post" action="/pa/salaryCanShu/updateNianZhongJiangYuTiInfo" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">

		<div class="pageFormContent nowrap"> 
		
			 <input type="hidden" name="PQD_NO" value="${paiQianDiInfo.NO1 }">
			
			 <dl>
				<dt><spring:message code="pa.salary.canShu.nianDu"/><!--法人--></dt>
				<dd>
					 ${paiQianDiInfo.ND }	
			 <input type="hidden" name="PQD_ND" value="${paiQianDiInfo.ND }">
				</dd>
			</dl>
			
		    <dl>
				<dt><spring:message code="pa.salary.canShu.faRen"/><!--法人--></dt>
				<dd>
					 ${paiQianDiInfo.FR }			
			 		<input type="hidden" name="PQD_FR" value="${paiQianDiInfo.FR }">
				</dd>
			</dl>
			
			<dl>
				<dt><spring:message code="pa.salary.canShu.jiTiBiLv"/><!--计提比率--></dt>
				<dd>
					 <input type="text" name="PQD_JTBL" value="${paiQianDiInfo.JTBL }" class="required textInput" />%
				</dd>
			</dl>
			
			<dl>
				<dt><spring:message code="pa.salary.canShu.zhiFuYueFen"/><!--支付月份--></dt>
				<dd>
					 <ait:date yearName="PQD_YF_YEAR" yearSelected="${paiQianDiInfo.ZFYFYEAR }" monthName="PQD_YF_MONTH" monthSelected="${paiQianDiInfo.ZFYFMONTH }"/>	
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
				<spring:message code="public.title.submit"/><!--提交--></button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">
				<spring:message code="public.title.cancle"/><!--取消--></button></div></div></li>
			</ul>
		</div>
	</form>
</div>
