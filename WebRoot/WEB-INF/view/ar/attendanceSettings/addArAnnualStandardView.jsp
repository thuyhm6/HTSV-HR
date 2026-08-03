<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<script type="text/javascript">
function changevalue(){
	var cvalue = $("#VAC_DAY_CNT_DAY").val();
	var nvalue = cvalue * 8;
	document.getElementById("VAC_DAY_CNT").value = nvalue;
	document.getElementById("dsnumber").innerHTML = nvalue + '<spring:message code="ar.viewitemparameter.title.xiaoshi"/>';
}
</script>
<div class="pageContent">
	<form method="post" action="/ar/attendanceSettings/addArAnnualStandardInfo" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone);">
		<div class="pageFormContent nowrap">
			
			<dl>
				<dt><!-- 类型 --><spring:message code="ar.viewarcardrecord.title.leixing"/>:</dt>
				<dd>
					<select id="VAC_TP" name="VAC_TP">
						<option value="1487"><!-- 年假 --><spring:message code="ar.viewArAnnualStandard.title.ninjia"/></option>
						<option value="1767"><!-- 福利年假 --><spring:message code="ar.viewArAnnualStandard.title.fulininjia"/></option>
						<option value="219603"><!-- 公司年假 --><spring:message code="ar.addArAnnualStandardView.GONGSINIANJIA.b"/></option>
					</select>
					<span class="info"></span>
				</dd>
			</dl>
			
			<dl>
				<dt><!-- 开始月 --><spring:message code="ar.viewArAnnualStandard.title.startmonth"/>:</dt>
				<dd>
					<input type="text" name="STRT_MONTH" size="30" class="digits required" />
					<span class="info"></span>
				</dd>
			</dl>
			
			<dl>
				<dt><!-- 结束月 --><spring:message code="ar.viewArAnnualStandard.title.endmonth"/>:</dt>
				<dd>
					<input type="text" name="END_MONTH" size="30" class="digits required" />
					<span class="info"></span>
				</dd>
			</dl>
			
			<dl>
				<dt><!-- 年假天数 --><spring:message code="ar.viewArAnnualStandard.title.vacday"/>:</dt>
				<dd>
	                <input type="text" id="VAC_DAY_CNT_DAY" name="VAC_DAY_CNT_DAY" size="30" class="number required" onkeyup="changevalue();"/>
					<span class="info"></span>
	                <div id="dsnumber"></div>
	                <input name="VAC_DAY_CNT" type="hidden" id="VAC_DAY_CNT" size="30" class="number"/>
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
