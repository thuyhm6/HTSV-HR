<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
	function searchPop2(flag){
		var name=encodeURI(encodeURI($('#seach_KEY2').val()));
		var scheduleNo=$('#PAY_SCHEDULE_NO').val();
		$('#searchPop2').attr('href','/pa/workManagement/viewEmpForPopList?pageNum=1&numPerPage=10&limit=pa&seach_KEY='+name+'&PAY_SCHEDULE_NO='+scheduleNo);
		if(flag == 'onkeyup')
			$('#searchPop2').click();
	}
</script>
<div class="pageContent">
	<form method="post" action="/pa/workManagement/addPaPayObjInfo" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDoneWithForm);">
		<div class="pageFormContent nowrap">
			
			<dl>
				<dt><!-- 工资支付计划--> <spring:message code="ess.empInfo.pay_plan" /></dt>
				<dd>
					<input type="hidden" id="PAY_SCHEDULE_NO" name="PAY_SCHEDULE_NO" value="${PAY_SCHEDULE_NO }"/>
					${schedualName }
				</dd>
			</dl>
			<dl>
				<dt>
					<spring:message
						code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME" /></dt>
				<dd>
					<input
						type="text" name="seach_KEY" id="seach_KEY2" value="${KEY}" />
						<a class="btnLook" id="searchPop2" onclick="searchPop2()" href="#" lookupGroup="person"></a>
				</dd>
			</dl>
			<dl>
				<dt><!-- 员工信息--> <spring:message code="pa.addPaEmpAccount.YUANGONGXINXI.C" /></dt>
				<dd>
					<input type="hidden" id="dwz.person.empid" name="EMPID" lookupGroup="person"/>
					<input type="hidden" id="dwz.person.personid" name="PERSON_ID" lookupGroup="person"/>
					<input type="hidden" id="dwz.person.deptno" name="DEPTNO" lookupGroup="person"/>
					<input type="text" readonly="readonly" id="dwz.person.empInfo" name="empInfo" lookupGroup="person" size="80"/>
				</dd>
			</dl>
			<dl>
				<dt><!-- 区分--> <spring:message code="display.emp.ben.or.benhs67" /></dt>
				<dd>
					<!-- 包括--> <spring:message code="pa.viewPaPayObj.BAOKUO.C" />
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
