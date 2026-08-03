<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
	function searchPopAddPaEmpAccount(flag){
		var name=encodeURI(encodeURI($('#seach_KEY2',$.pdialog.getCurrent()).val()));
		var searchForFlag='searchForPaEmpAccount';
		$('#searchPopAddPaEmpAccountId',$.pdialog.getCurrent()).attr('href','/pa/workManagement/viewEmpForPopList?pageNum=1&numPerPage=10&limit=pa&seach_KEY='+name+'&searchForFlag='+searchForFlag);
		if(flag == 'onkeyup')
			$('#searchPopAddPaEmpAccountId',$.pdialog.getCurrent()).click();
	}
</script>
<div class="pageContent">
	<form method="post" action="/pa/workManagement/addPaEmpAccountInfo" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDoneWithForm);">
		<div class="pageFormContent nowrap">
			<dl>
				<dt><spring:message
						code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME" /></dt> 
				<dd>
					<input
						type="text" name="seach_KEY" id="seach_KEY2" value="${KEY}" />
						<a class="btnLook" id="searchPopAddPaEmpAccountId" onclick="searchPopAddPaEmpAccount()" href="#" lookupGroup="person"></a>
				</dd>
			</dl>
			<dl>
				<dt><!--员工信息--><spring:message code="pa.addPaEmpAccount.YUANGONGXINXI.C" /></dt>
				<dd>
					<input type="hidden" id="dwz.person.personid" name="PERSON_ID" lookupGroup="person"/>
					<input type="text" readonly="readonly" id="dwz.person.empInfo" name="empInfo" lookupGroup="person" size="80"/>
				</dd>
			</dl>
			<dl>
				<dt><!--银行类型--><spring:message code="pa.viewPaEmpAccount.YINHANGLEIXING.C" /></dt>
				<dd>
					<ait:SelectSyCodeByCpnyID name="ACCOUNT_TYPE"
							selected="" parentNo="14015883"
							cnpyID="${LoginUser.cpnyId}" limit="all"/>
				</dd>
			</dl>
			<dl>
				<dt><!--银行账号--><spring:message code="rp.report.title.bankcardno" /></dt>
				<dd>
					<input type="text"  name="ACCOUNT_NO" size="80"/>
				</dd>
			</dl>
			<!--<dl>
				<dt>地址<spring:message code="hrm.empinfo.FAM_ADDRESS" /></dt>
				<dd>
					<input type="text"  name="ACCOUNT_ADDRESS" size="80"/>
				</dd>
			</dl>-->
			<dl>
				<dt><!-- 开户行  --><spring:message code="hr.viewAccount.title.BANK_NAME" /></dt>
				<dd>
					<input type="text"  name="ACCOUNT_NAME"  size="80"/>
				</dd>
			</dl>
			
			<dl>
				<dt><!--保险号码--><spring:message code="pa.viewPaEmpAccount.BAOXIANHAOMA.b" /></dt>
				<dd>
					<input type="text"  name="SECURITY_NO" size="80"/>
				</dd>
			</dl>
			<dl>
				<dt><!--税号--><spring:message code="pa.viewPaEmpAccount.SHUIHAO.b" /></dt>
				<dd>
					<input type="text"  name="TAX_NO" size="80"/>
				</dd>
			</dl>
			<!--<dl>
				<dt>公积金账号<spring:message code="pa.viewPaEmpAccount.GONGJIJINZHANGHAO.C" /></dt>
				<dd>
					<input type="text"  name="FUND_NO" class="required"  size="80"/>
				</dd>
			</dl>
			<dl>
				<dt>社保缴纳时间<spring:message code="pa.viewPaEmpAccount.SHEBAOJIAONASHIJIAN.C" /></dt>
				<dd>
					<input type="text"
						id="SECURITY_PAY_DATE" name="SECURITY_PAY_DATE" size="80" class="Wdate"
						onClick="WdatePicker({dateFmt:'yyyy.MM'})"
						value="${recruitInfo.SECURITY_PAY_DATE }" />
				</dd>
			</dl>
			<dl>
				<dt>公积金缴纳时间<spring:message code="pa.viewPaEmpAccount.GONGJIJINJIAONASHIJIAN.C" /></dt>
				<dd>
					<input type="text"
						id="FUND_PAY_DATE" name="FUND_PAY_DATE" size="80" class="Wdate"
						onClick="WdatePicker({dateFmt:'yyyy.MM'})"
						value="${recruitInfo.FUND_PAY_DATE }" />
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
