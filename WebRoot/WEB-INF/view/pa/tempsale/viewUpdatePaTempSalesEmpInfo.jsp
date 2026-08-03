<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
	//身份证验证
	function validateIdCard(){
		var num = $("#IDCARD_NO").val();
		var len = num.length, re;
		if (len == 15){
			re = new RegExp(/^(\d{6})()?(\d{2})(\d{2})(\d{2})(\d{3})$/);
		}else if (len == 18){
			re = new RegExp(/^(\d{6})()?(\d{4})(\d{2})(\d{2})(\d{3})(\d|X|x)$/);
		}else if (len == 0){
			//alert("输入的身份证号不能为空！"); 
			alertMsg.error('<spring:message code="hr.alert.message.viewHire.checkNotNullIdcardNo"/>');
			return false;
		}else {
			//alert("输入的数字位数不对！"); 
			alertMsg.error('<spring:message code="hr.alert.message.viewPersonalInfo.checkDigitalDigits"/>');
			return false;
		}
		var a = num.match(re);
		var B = null;
		var D = null;
		if (a != null){
			if (len==15){
				D = new Date("19"+a[3]+"/"+a[4]+"/"+a[5]);
				B = D.getYear()==a[3]&&(D.getMonth()+1)==a[4]&&D.getDate()==a[5];
			}
			else{
				D = new Date(a[3]+"/"+a[4]+"/"+a[5]);
				B = D.getFullYear()==a[3]&&(D.getMonth()+1)==a[4]&&D.getDate()==a[5];
			}
			if (!B){
				//alert("输入的身份证号 "+ a[0] +" 里出生日期不对！"); 
				alertMsg.error('<spring:message code="hr.alert.message.viewPersonalInfo.checkIdCardNoForSplitA"/>'+ a[0] +'<spring:message code="hr.alert.message.viewPersonalInfo.checkIdCardNoForSplitB"/>');
				return false;
			}else{
				$("#BIRTH_DATE").val(D.getFullYear()+"-"+((D.getMonth()+1) < 10 ? "0"+(D.getMonth()+1) : (D.getMonth()+1))+"-"+((D.getDate()) < 10 ? "0"+(D.getDate()) : (D.getDate())));
				return true;
			}
		}else{
			alertMsg.error('<spring:message code="hr.alert.message.viewPersonalInfo.checkIdCardNo"/>');//您输入的身份证号不正确
			return false;
		}
	}
    function validateCallbackViewUpdatePaTempSalesEmpInfo(form, callback) {
		var $form = $("#updatePaTempSalesEmpInfo");
		if (!$form.valid()) {
			return false;
		}
		$.ajax({
			type: form.method || 'POST',
			url:$form.attr("action"),
			data:$form.serializeArray(),
			dataType:"json",
			cache: false,
			success: callback || DWZ.ajaxDone,
			error: DWZ.ajaxError
		});
		return false;
	}
</script>
<div class="pageContent">
	<form id="updatePaTempSalesEmpInfo" method="post" action="/pa/tempsale/updatePaTempSalesEmpInfo" class="pageForm required-validate" onsubmit="return validateCallbackViewUpdatePaTempSalesEmpInfo(this, dialogAjaxDone)">
		<div class="pageFormContent" layoutH="56">
			<table width="100%"  border="0" cellpadding="0" cellspacing="0" class="user_table margin_b">
				<tr>
					<td class="td_title"  width="20%">
						姓名
					</td>
					<td class="td_type" width="30%">${empInfo.EMP_NAME }
						<input type="hidden" name="INFO_NO" value="${INFO_NO }"/>
						<input type="hidden" name="EVENT_ID" value="${EVENT_ID }"/>
						<input type="hidden" id="EMP_NAME" name="EMP_NAME" value="${empInfo.EMP_NAME }" class="required textInput"  readonly="true" maxlength="20" size="30"/>
					</td>
					<td class="td_title"  width="18%">
						身份证号码
					</td>
					<td class="td_type"  width="30%">
						<input type="text" id="IDCARD_NO" name="IDCARD_NO" value="${empInfo.IDCARD_NO }" class="required textInput" readonly="true" onblur="validateIdCard(0)"  maxlength="20" size="30">
					</td>
				</tr>
				<tr>
					<td class="td_title" >
						出生年月
					</td>
					<td class="td_type">
						<input type="text" id="BIRTH_DATE" name="BIRTH_DATE" value="${empInfo.BIRTH_DATE }" class="required textInput"  readonly="true" maxlength="20" size="30"/>
					</td>
					<td class="td_title" >
						联系方式
					</td>
					<td class="td_type">
						<input type="text" id="CELLPHONE" name="CELLPHONE" value="${empInfo.CELLPHONE }" class="required textInput"  readonly="true" maxlength="20" size="30"/>
					</td>
				</tr>
				<tr>
					<td class="td_title" >
						银行帐号
					</td>
					<td class="td_type">
						<input type="text" id="BANK_NO" name="BANK_NO" value="${empInfo.BANK_NO }" class="number textInput"  readonly="true" maxlength="20" size="30"/>
					</td>
					<td class="td_title" >
						开户行
					</td>
					<td class="td_type">
						<input type="text" id="BANK_NAME" name="BANK_NAME" value="${empInfo.BANK_NAME }" class="number textInput" readonly="true" maxlength="20" size="30"/>
					</td>
				</tr>
				<tr>
					<td class="td_title" >
						工作天数
					</td>
					<td class="td_type">
						<input type="text" id="WORK_DAYS" name="WORK_DAYS" value="${empInfo.WORK_DAYS }" class="number required textInput" maxlength="20" size="30"/>
					</td>
					<td class="td_title" >
						应发工资
					</td>
					<td class="td_type">
						<input type="text" id="EVENT_SALARY" name="EVENT_SALARY" value="${empInfo.EVENT_SALARY }" class="number required textInput" maxlength="20" size="30"/>
					</td>
				</tr>
				<tr>
					<td class="td_title" >
						评价等级
					</td>
					<td class="td_type">
		 				<ait:SelectSyCodeByCpnyID id="seach_EVS_GRADE" name="seach_EVS_GRADE" parentNo="123195" selected="${empInfo.EVS_GRADE}" cnpyID="${defaultCpny}" limit="all"/>
					</td>
					<td class="td_title" >
					</td>
					<td class="td_type">
					</td>
				</tr>
			</table>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit"><spring:message code="heran.examineSave.title"/><!-- 保存 --></button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close"><spring:message code="public.title.cancle"/><!-- 取消 --></button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>