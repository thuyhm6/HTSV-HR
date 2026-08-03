<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<SCRIPT>

$(document).ready(function() {
	var stateCd=$('#seach_STATENM_p1').val();
	var cityCd0=$('#hCITYNM_c1').val();
	changeState_z1(stateCd,cityCd0);
	
	$('#seach_STATENM_p1').live('change',function(){
		var st=$('#seach_STATENM_p1').val();
		changeState_z1(st,cityCd0);
	});
});

function changeState_z1(stateCd, cityCd){
	$.ajax({
		cache: false,
		url : '${base}/promoter/getListBySelect?type=CITY&parentNo='+stateCd+'&selected=${paiQianDiInfo.CSMC}'+'&name=seach_CITYNM_c1',
		type : "get",
		dataType : "html",
		success : function(data) {
			$("#seach_CITYNM_c1").html(data);
		}
	});
}

		
function validateCallbackUpdateZdgz(form, dialogAjaxDone){
	var csmc = $("#seach_CITYNM_c1").val();
	var zdgz = $("#PQD_ZDGZ").val();
	var citycd = $("#seach_STATENM_p1").val();
	var qf = $("#PQD_QF").val();
	 if(citycd == null || citycd == ''){
			alertMsg.error('<spring:message code="pa.salary.canShu.citycdcantkong"/>');
			return false;
	} 
	
		/* if(csmc == null || csmc == ''){
			alertMsg.error('<spring:message code="pa.salary.canShu.chengshimingchengcantkong"/>');
			return false;
		} 
 */
		 if(zdgz == null || zdgz == ''){
			 alertMsg.error('最低工资不能为空!');
				return false;
		} 


		 if(isNaN(zdgz)){
		alertMsg.error('<spring:message code="pa.salary.canShu.zuidigongzierror"/>');
		return false;
		}  
			

		 if(qf == null || qf == ''){
				alertMsg.error('<spring:message code="pa.salary.canShu.qufencantkong"/>');
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
	<form method="post" action="/pa/salaryCanShu/updateZuiDiGongZiBiaoZhunCuXiaoYuanInfo" 
	class="pageForm required-validate" 
	onsubmit="return validateCallbackUpdateZdgz(this, dialogAjaxDone);">

	

		<div class="pageFormContent nowrap"> 
				<input id="hCITYNM_c1" name="hCITYNM_c1" type="hidden" value="${searchMap.seach_CITYNM_c1}" />
			 <input type="hidden" name="PQD_NO" value="${paiQianDiInfo.NO1 }">
			
		    <dl>
				<dt><spring:message code="pa.salary.canShu.niandu"/><!--年度--></dt>
				<dd>
					<ait:date yearName="PQD_ND"></ait:date>		
				</dd>
			</dl>
			
			<%-- <dl>
				<dt><spring:message code="pa.salary.canShu.citycd"/><!--CITY_CD--></dt>
				<dd>
					 <ait:selectSyCode name="PQD_CITY_CD" parentNo="219597" limit="ALL" selected="${paiQianDiInfo.CITYCD }"/>			
				</dd>
			</dl>
			
			<dl>
				<dt><spring:message code="pa.salary.canShu.chengShiMingCheng"/><!--城市名称--></dt>
				<dd>
					 <ait:selectSyCode name="PQD_CSMC" parentNo="218074" selected="${paiQianDiInfo.CSMC }" limit="ALL"/>					
				</dd>
			</dl> --%>
				
			 <dl>
					<dt>省名称</dt>
					<dd><ait:SelectState id="seach_STATENM_p1" name="seach_STATENM_p1" type="STATE" parentNo=""  limit="all" selected="${paiQianDiInfo.CITYCD}" />
				</dd>
			</dl>
				<dl><!-- 城市名称： -->
					<dt>城市名称</dt>
					<td><span id="seach_CITYNM_c1" name="seach_CITYNM_c1" ></select></span>
				</td>
			</dl>
			<dl>
				<dt><spring:message code="pa.salary.canShu.zuidigongzi"/><!--最低工资--></dt>
				<dd>
					 <input name="PQD_ZDGZ" id="PQD_ZDGZ" class="required" value="${paiQianDiInfo.ZDGZ }" />
				</dd>
			</dl>
		
			 	<dt><spring:message code="pa.salary.canShu.qufen"/><!--区分--></dt>
				<dd>
					 <select name="PQD_QF" id="PQD_QF">
							<option value="" <c:if test="${paiQianDiInfo.QF eq '' }"> selected </c:if>><spring:message code="pa.salary.canShu.qingXuanZe"/><!-- 请选择 --></option>
							<option value="SQ" <c:if test="${paiQianDiInfo.QF eq 'SQ' }"> selected </c:if>><spring:message code="pa.salary.canShu.shuiqian"/><!--应发 --></option>
							<option value="SH" <c:if test="${paiQianDiInfo.QF eq 'SH' }"> selected </c:if>><spring:message code="pa.salary.canShu.shuihou"/><!--实得 --></option>
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
