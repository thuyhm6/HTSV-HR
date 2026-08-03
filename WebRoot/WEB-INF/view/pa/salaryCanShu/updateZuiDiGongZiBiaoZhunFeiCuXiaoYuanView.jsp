<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<SCRIPT>		
		function validateCallbackUpdateZdgzFei(form, dialogAjaxDone){
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
				alertMsg.error('区分不能为空!');
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
</SCRIPT>
<div class="pageContent">  
	<form method="post" action="/pa/salaryCanShu/updateZuiDiGongZiBiaoZhunFeiCuXiaoYuanInfo" 
	class="pageForm required-validate" 
	onsubmit="return validateCallbackUpdateZdgzFei(this, dialogAjaxDone);">

		<div class="pageFormContent nowrap"> 
		
			 <input type="hidden" name="PQD_NO" value="${paiQianDiInfo.NO1 }">
			
		    <dl>
				<dt><spring:message code="pa.salary.canShu.faRen"/><!--法人--></dt>
				<dd>
					 ${paiQianDiInfo.FR }			
				</dd>
			</dl>
			
			<dl>
				<dt><spring:message code="pa.salary.canShu.niandu"/><!--年度--></dt>
				<dd>
				<ait:date yearName="PQD_ND" yearSelected="${paiQianDiInfo.ND}" ></ait:date>
				</dd>
			</dl>
			
			<dl>
				<dt><spring:message code="pa.salary.canShu.fulidiqu"/><!--福利地区--></dt>
				<dd>
					<ait:SelectSyCodeByCpnyID name="PQD_FLDQ" selected="${paiQianDiInfo.FLDQ }" parentNo="216736" cnpyID="${paiQianDiInfo.FR }" limit="all"/>
				</dd>
			</dl>
			
			<dl>
				<dt><spring:message code="pa.salary.canShu.zuidigongzi"/><!--最低工资--></dt>
				<dd>
					 <input name="PQD_ZDGZ" id="PQD_ZDGZ" class="required" value="${paiQianDiInfo.ZDGZ }" />
				</dd>
			</dl>
			
			<dl>
				<dt><spring:message code="pa.salary.canShu.shepinggongzi"/><!--社平工资--></dt>
				<dd>
					 <input name="PQD_SPGZ" id="PQD_SPGZ" class="required" value="${paiQianDiInfo.SPGZ }"/>
				</dd>
			</dl>
		    
			<dl>
				<dt><spring:message code="pa.salary.canShu.zuixiaojishu"/><!--最小基数--></dt>
				<dd>
			 		<input name="PQD_ZXJS" id="PQD_ZXJS" class="required" value="${paiQianDiInfo.ZXJS }	"/>
				</dd>
			</dl>
			
			<dl>
				<dt><spring:message code="pa.salary.canShu.zuidajishu"/><!--最大基数--></dt>
				<dd>
			 	     <input name="PQD_ZDJS" id="PQD_ZDJS" class="required" value="${paiQianDiInfo.ZDJS }"/>			
				</dd>
			</dl>
				
			<dl>
				<dt><spring:message code="pa.salary.canShu.qufen"/><!--区分--></dt>
					<dd>
						 <select name="PQD_QF" id="PQD_QF">
								<option value="" <c:if test="${paiQianDiInfo.QF eq ''}">selected</c:if>><spring:message code="pa.salary.canShu.qingXuanZe"/><!-- 请选择 --></option>
								<option value="SQ" <c:if test="${paiQianDiInfo.QF eq 'SQ'}">selected</c:if>><spring:message code="pa.salary.canShu.shuiqian"/><!-- 应发 --></option>
								<option value="SH" <c:if test="${paiQianDiInfo.QF eq 'SH'}">selected</c:if>><spring:message code="pa.salary.canShu.shuihou"/><!--实得 --></option>
						</select>
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
