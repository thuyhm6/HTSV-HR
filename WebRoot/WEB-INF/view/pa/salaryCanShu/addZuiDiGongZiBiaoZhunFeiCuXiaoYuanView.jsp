<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
	function validateCallbackFormCheck(form, dialogAjaxDone){
			var fldq = $("#PQD_FLDQ").val();
			var zdgz = $("#PQD_ZDGZ").val();
			var spgz = $("#PQD_SPGZ").val();
			var zxjs = $("#PQD_ZXJS").val();
			var zdjs = $("#PQD_ZDJS").val();
			var nd = $("#PQD_ND").val();
			var qf = $("#PQD_QF").val();
			if(fldq == null || fldq == ''){
					alertMsg.error('<spring:message code="pa.salary.canShu.fulidiquerror"/>');
					return false;
				} 

			if(nd == null || nd == ''){
				alertMsg.error('年度不能为空!');
				return false;
			} 

			if(qf == null || qf == ''){
				alertMsg.error('<区分不能为空!');
				return false;
			} 
				 if(isNaN(zdgz)){
				alertMsg.error('<spring:message code="pa.salary.canShu.zuidigongzierror"/>');
				return false;
				} 
				 if(isNaN(spgz)){
				alertMsg.error('<spring:message code="pa.salary.canShu.shepinggongzierror"/>');
				return false;
				} 
				if(isNaN(zxjs)){
				alertMsg.error('<spring:message code="pa.salary.canShu.zuixiaojishuerror"/>');
				return false;
				} 
				 if(isNaN(zdjs)){
				alertMsg.error('<spring:message code="pa.salary.canShu.zuidajishuerror"/>');
				return false;
				}  

			var $form = $(form);
			
			if (!$form.valid()) {
				return false;
			}

			$.ajax({
				type: form.method || 'POST',
				url:$form.attr("action"),
				data:$form.serializeArray(),
				dataType:"json",
				cache: false,
				success: dialogAjaxDone || DWZ.ajaxDone,
				error: DWZ.ajaxError
			});

			return false;
		}
</script>
<div class="pageContent">
     <form method="post" action="/pa/salaryCanShu/addZuiDiGongZiBiaoZhunFeiCuXiaoYuanInfo" class="pageForm required-validate" 
     	onsubmit="return validateCallbackFormCheck(this, dialogAjaxDone);">
		<div class="pageFormContent nowrap"> 
		    <dl>
				<dt><spring:message code="pa.salary.canShu.faRen"/><!--法人--></dt>
				<dd>
					  <ait:SyCompany target="config" cpnyId="${sessionScope.LoginUser.cpnyId }" name="PQD_FR" language="zh"  limit="ALL" activity="1" selected="${faren}"/>
				</dd>
			</dl>
			
			<dl>
				<dt><spring:message code="pa.salary.canShu.niandu"/><!--年度--></dt>
				<dd>
				<ait:date yearName="PQD_ND" ></ait:date>
				</dd>
			</dl>
			
			<dl>
				<dt><spring:message code="pa.salary.canShu.fulidiqu"/><!--福利地区--></dt>
				<dd>
					 <ait:SelectSyCodeByCpnyID name="PQD_FLDQ" parentNo="216736" cnpyID="${sessionScope.LoginUser.cpnyId }" limit="all"/>				
				</dd>
			</dl>
			
			<dl>
				<dt><spring:message code="pa.salary.canShu.zuidigongzi"/><!--最低工资--></dt>
				<dd>
					 <input name="PQD_ZDGZ" id="PQD_ZDGZ" class="required" />					
				</dd>
			</dl>
			
			<dl>
				<dt><spring:message code="pa.salary.canShu.shepinggongzi"/><!--社平工资--></dt>
				<dd>
					 <input name="PQD_SPGZ" id="PQD_SPGZ" class="required" />						
				</dd>
			</dl>
		     
			<dl>
				<dt><spring:message code="pa.salary.canShu.zuixiaojishu"/><!--最小基数--></dt>
				<dd>
					 <input name="PQD_ZXJS" id="PQD_ZXJS" class="required" />					
				</dd>
			</dl>
					
			<dl>
				<dt><spring:message code="pa.salary.canShu.zuidajishu"/><!--最大基数--></dt>
				<dd>
					 <input name="PQD_ZDJS" id="PQD_ZDJS" class="required" />					
				</dd>
			</dl>
			
			<dl>
				<dt><spring:message code="pa.salary.canShu.qufen"/><!--区分--></dt>
				<dd>
					 <select name="PQD_QF" id="PQD_QF">
							<option value="" ><spring:message code="pa.salary.canShu.qingXuanZe"/><!-- 请选择 --></option>
							<option value="SQ" ><spring:message code="pa.salary.canShu.shuiqian"/><!-- 应发 --></option>
							<option value="SH" ><spring:message code="pa.salary.canShu.shuihou"/><!--实得 --></option>
					</select>
				</dd>
			</dl>
			
			
			<div class="formBar">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent"><button type="submit">
					<spring:message code="public.title.submit"/><!-- 提交 --></button></div></div></li>
					<li><div class="button"><div class="buttonContent"><button type="button" class="close">
					<spring:message code="public.title.cancle"/><!--取消--></button></div></div></li>
				</ul>
			</div> 
			</div>
        </form>
 </div>
