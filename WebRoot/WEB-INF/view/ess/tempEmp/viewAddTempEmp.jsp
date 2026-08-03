<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript">
//初始
$(document).ready(function(){
	//保存
	$("#viewAddTempEmp_save",$.pdialog.getCurrent()).click(function(){
		var $form = $("#viewAddTempEmp_form",$.pdialog.getCurrent());
		if (!$form.valid()) {
			return false;
		}
		if($("#SEXCODE",$.pdialog.getCurrent()).val() == ""){
			alterMsg.warn("<spring:message code='liang.hr.alert.message.viewHire.sexcodeNotNull' />");//性别不能为空
			return false;
		}
		alertMsg.confirm("<spring:message code='ess.message.confirm_sava' />",//确定要保存吗？
	  		{okCall:function(){
			  	$.ajax({
	  				type: 'POST',
	  				url:$form.attr("action"),
	  				data:$form.serializeArray(),
	  				dataType:"json",
	  				cache: false,
	  				success: dialogAjaxDone,
					error: DWZ.ajaxError
	  			});
	  		}});
		return false;
	});
});
</script>
	<div>
		<form id="viewAddTempEmp_form" method="post" action="/ess/tempEmp/addTempEmp">
			<div>
				<table  class="user_table" width="100%">
					<tr>
						<td width="30%" style="text-align:right" class="td_title">
							<spring:message code="alert.pa.pasalarycanshu.xingming" /><!--姓名-->
						</td>
						<td width="70%" class="td_type">
							<input type="text" id="LOCAL_NAME" name="LOCAL_NAME" value="" class="required" size="25" />
						</td>
					</tr>
					<tr>
						<td width="30%" style="text-align:right" class="td_title">
							<spring:message code="ess.empInfo.entry_date" /><!--入职日期-->
						</td>
						<td width="70%" class="td_type">
							<input type="text" name="DATE_STARTED" id="DATE_STARTED"  value="" class="Wdate required" onClick="WdatePicker({dateFmt:'yyyyMMdd'})" size="25"/>
						</td>
					</tr>
					<tr>
						<td width="30%" style="text-align:right" class="td_title">
							<spring:message code="org.title.dept" /><!--部门-->
						</td>
						<td width="70%" class="td_type">
							<ait:deptList name="DEPTNO" limit="ar" id="viewAddTempEmp_deptList" />
							<ait:deptTreeIcon name="DEPTNO" limit="ar" id="viewAddTempEmp_deptList"/>
						</td>
					</tr>
					<tr>
						<td width="30%" style="text-align:right" class="td_title">
							<spring:message code="ess.personalinfo.title.IDCardNo" /><!--身份证号-->
						</td>
						<td width="70%" class="td_type">
							<input type="text" id="ID_CARD_NO" name="ID_CARD_NO" value="" class="required" size="25" />
						</td>
					</tr>
					<tr>
						<td width="30%" style="text-align:right" class="td_title">
							<spring:message code="hr.viewPersonalInfo.title.SEX" /><!--性别-->
						</td>
						<td width="70%" class="td_type">
							<ait:SelectSyCodeByCpnyID name="SEXCODE" selected="${recruitInfo.SEXCODE}" parentNo="1324" limit="all" />
						</td>
					</tr>
					<c:if test="${LoginUser.cpnyId eq 'SPC_HZ'}">
					<tr>
						<td width="30%" style="text-align:right" class="td_title">
							<spring:message code="liang.hr.viewPersonalInfo.title.PHONE_NUMBER" /><!--手机号-->
						</td>
						<td width="70%" class="td_type">
							<input type="text" id="PHONE" name="PHONE" value="" class="required" size="25" />
						</td>
					</tr>
					</c:if>
					<c:if test="${LoginUser.cpnyId eq 'SPC_NJ'}">
					<tr>
						<td width="30%" style="text-align:right" class="td_title">
							<spring:message code="rp.report.title.bankcardno" /><!--银行账号-->
						</td>
						<td width="70%" class="td_type">
							<input type="text" id="BANK_NO" name="BANK_NO" value="" class="required" size="25" />
						</td>
					</tr>
					</c:if>
				</table>
			</div>
			<div class="formBar">
				<ul>
					<li>
						<div class="button">
							<div class="buttonContent"><!--提交-->
								<button type="button" id="viewAddTempEmp_save">
									<spring:message code="zxc.hr.hrmAffirmConfig.save" /><!--保存-->
								</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
	  	</form>	
	</div>
