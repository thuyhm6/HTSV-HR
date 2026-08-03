<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageContent">
	<form method="post" action="/pa/workManagement/updatePaEmpAccountInfo" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDoneWithForm);">
		<div class="pageFormContent nowrap">
			<dl>
				<dt><!-- 员工信息 --> <spring:message code="pa.addPaEmpAccount.YUANGONGXINXI.C" /></dt>
				<dd>
					${empBaseInfo }
					<input type="hidden" name="PA_EMP_ACCOUNT_NO" value="${paEmpAccountInfo.PA_EMP_ACCOUNT_NO }" />
				</dd>
			</dl>
			<dl>
				<dt><!-- 银行类型 --> <spring:message code="pa.viewPaEmpAccount.YINHANGLEIXING.C" /></dt>
				<dd>
					<ait:SelectSyCodeByCpnyID name="ACCOUNT_TYPE"
							selected="${paEmpAccountInfo.ACCOUNT_TYPE}" parentNo="14015883"
							cnpyID="${LoginUser.cpnyId}" limit="all"/>
				</dd>
			</dl>
			<dl>
				<dt><!-- 银行账号 --> <spring:message code="rp.report.title.bankcardno" /></dt>
				<dd>
					<input type="text"  name="ACCOUNT_NO" class="required"  value="${paEmpAccountInfo.ACCOUNT_NO }" size="80"/>
				</dd>
			</dl>
			<dl>
				<dt><!-- 开户行  --><spring:message code="hr.viewAccount.title.BANK_NAME" /></dt>
				<dd>
					<input type="text"  name="ACCOUNT_NAME"  value="${paEmpAccountInfo.ACCOUNT_NAME }" size="80"/>
				</dd>
			</dl>
			<!--<dl>
				<dt>地址<spring:message code="hrm.empinfo.FAM_ADDRESS" /></dt>
				<dd>
					<input type="text"  name="ACCOUNT_ADDRESS" value="${paEmpAccountInfo.ACCOUNT_ADDRESS }" size="80"/>
				</dd>
			</dl>-->
			<dl>
				<dt><!--保险号码--><spring:message code="pa.viewPaEmpAccount.BAOXIANHAOMA.b" /></dt>
				<dd>
					<input type="text"  name="SECURITY_NO" class="required" value="${paEmpAccountInfo.SECURITY_NO }"  size="80"/>
				</dd>
			</dl>
			<dl>
				<dt><!--税号--><spring:message code="pa.viewPaEmpAccount.SHUIHAO.b" /></dt>
				<dd>
					<input type="text"  name="TAX_NO" class="required" value="${paEmpAccountInfo.TAX_NO }"  size="80"/>
				</dd>
			</dl>
			<!--<dl>
				<dt> 公积金账号  <spring:message code="pa.viewPaEmpAccount.GONGJIJINZHANGHAO.C" /></dt>
				<dd>
					<input type="text"  name="FUND_NO"  value="${paEmpAccountInfo.FUND_NO }" size="80"/>
				</dd>
			</dl>
			<dl>
				<dt> 社保缴纳时间  <spring:message code="pa.viewPaEmpAccount.SHEBAOJIAONASHIJIAN.C" /></dt>
				<dd>
					<input type="text"
						id="SECURITY_PAY_DATE" name="SECURITY_PAY_DATE" size="80" class="Wdate"
						onClick="WdatePicker({dateFmt:'yyyy.MM'})"
						value="${paEmpAccountInfo.SECURITY_PAY_DATE }" />
				</dd>
			</dl>
			<dl>
				<dt> 公积金缴纳时间  <spring:message code="pa.viewPaEmpAccount.GONGJIJINJIAONASHIJIAN.C" /></dt>
				<dd>
					<input type="text"
						id="FUND_PAY_DATE" name="FUND_PAY_DATE" size="80" class="Wdate"
						onClick="WdatePicker({dateFmt:'yyyy.MM'})"
						value="${paEmpAccountInfo.FUND_PAY_DATE }" />
				</dd>
			</dl>-->
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit"><!-- 提交 --><spring:message code="public.title.submit"/></button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close"><!-- 取消 --><spring:message code="public.title.cancle"/></button></div></div></li>
			</ul>
		</div>
	</form>
</div>
