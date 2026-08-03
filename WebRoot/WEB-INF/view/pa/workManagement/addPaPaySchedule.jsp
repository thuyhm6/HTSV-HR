<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageContent">
	<form method="post" action="/pa/workManagement/addPaPayScheduleInfo" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDoneWithForm);">
		<div class="pageFormContent nowrap">
			
			<dl>
				<dt style="width: 200px;"><!-- 支付日期 --> <spring:message code="display.pa.ecc.paydate" /></dt>
				<dd>
					<input id="PAY_DATE" type="text" name="PAY_DATE" class="Wdate required" onClick="WdatePicker({dateFmt:'dd-MM-yyyy',lang:'en'})" readonly="true" />
				</dd>
			</dl>
			<dl>
				<dt style="width: 200px;"><!-- 工资区分 --> <spring:message code="pa.viewPaPaySchedule.GONGZIQUFEN.C" /></dt>
				<dd>
					<ait:SelectSyCodeByCpnyID name="SALARY_DISTIN_NO" parentNo="14013797"
							cnpyID="${LoginUser.cpnyId}"  />
				</dd>
			</dl>
			
			<%-- <dl>
				<dt style="width: 200px;"><!--发令开始日期 --> <spring:message code="pa.viewPaPaySchedule.FALINGKAISHIRIQI.C" /></dt>
				<dd>
					<input id="HR_START_DATE" type="text" name="HR_START_DATE" class="Wdate required" onClick="WdatePicker({dateFmt:'dd-MM-yyyy',lang:'en'})" readonly="true" />
				</dd>
			</dl>
			<dl>
				<dt style="width: 200px;"><!-- 发令结束日期 --> <spring:message code="pa.viewPaPaySchedule.FALINGJIESHURIQI.C" /></dt>
				<dd>
					<input id="HR_END_DATE" type="text" name="HR_END_DATE" class="Wdate required" onClick="WdatePicker({dateFmt:'dd-MM-yyyy',lang:'en'})" readonly="true" />
				</dd>
			</dl> --%>
			<dl>
				<dt style="width: 200px;"><!-- 考勤取值开始日期--> <spring:message code="pa.viewPaPaySchedule.KAIQINKAISHIRIQI.C" /></dt>
				<dd>
					<input id="AR_START_DATE" type="text" name="AR_START_DATE" class="Wdate required" onClick="WdatePicker({dateFmt:'dd-MM-yyyy',lang:'en'})" readonly="true" />
				</dd>
			</dl>
			<dl>
				<dt style="width: 200px;"><!--考勤取值结束日期--> <spring:message code="pa.viewPaPaySchedule.KAIQINJIESHURIQI.C" /></dt>
				<dd>
					<input id="AR_END_DATE" type="text" name="AR_END_DATE" class="Wdate required" onClick="WdatePicker({dateFmt:'dd-MM-yyyy',lang:'en'})" readonly="true" />
				</dd>
			</dl>
			<dl>
				<dt style="width: 200px;"><!-- 开放日期 --> <spring:message code="pa.viewPaPaySchedule.KAIFANGRIQI.C" /></dt>
				<dd>
					<input id="PA_OPEN_DATE" type="text" name="PA_OPEN_DATE"  class="Wdate required" onClick="WdatePicker({dateFmt:'dd-MM-yyyy',lang:'en'})" readonly="true"/>
				</dd>
			</dl>
			<dl>
				<dt style="width: 200px;"><!-- 传票日期--> <spring:message code="pa.viewPaPaySchedule.CHUANPIAORIQI.C" /></dt>
				<dd>
					<input id="PA_TRANS_DATE" type="text" name="PA_TRANS_DATE" onClick="WdatePicker({dateFmt:'dd-MM-yyyy',lang:'en'})" readonly="true"/>
				</dd>
			</dl>
			<dl>
				<dt style="width: 200px;"><!-- 更意见 --> <spring:message code="hrm.contract.Change_opinion" /></dt>
				<dd>
					<input id="EMP_OPINION" type="text" name="EMP_OPINION" />
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
