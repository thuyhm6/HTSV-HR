<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>

	$(document).ready(function() {
	var stateCd=$('#seach_STATENM_p2').val();
	var cityCd0=$('#hCITYNM_c2').val();
	changeState_z2(stateCd,cityCd0);
	
	$('#seach_STATENM_p2').live('change',function(){
		var st=$('#seach_STATENM_p2').val();
		changeState_z2(st,cityCd0);
	});
});

function changeState_z2(stateCd, cityCd){
	$.ajax({
		cache: false,
		url : '${base}/promoter/getListBySelect?type=CITY&parentNo='+stateCd+'&selected='+cityCd+'&name=seach_CITYNM_c2',
		type : "get",
		dataType : "html",
		success : function(data) {
			$("#seach_CITYNM_c2").html(data);
		}
	});
}

	function validateCallbackFormCheck(form, dialogAjaxDone){
			var csmc = $("#seach_CITYNM_c2").val();
			var zdgz = $("#PQD_ZDGZ").val();
			var citycd = $("#seach_STATENM_p2").val();
			var qf = $("#PQD_QF").val();
			 if(citycd == null || citycd == ''){
					alertMsg.error('<spring:message code="pa.salary.canShu.citycdcantkong"/>');
					return false;
			} 
			
				/* if(csmc == null || csmc == ''){
					alertMsg.error('<spring:message code="pa.salary.canShu.chengshimingchengcantkong"/>');
					return false;
				}  */

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
</script>
<div class="pageContent">
     <form method="post" action="/pa/salaryCanShu/addZuiDiGongZiBiaoZhunCuXiaoYuanInfo" class="pageForm required-validate" 
     	onsubmit="return validateCallbackFormCheck(this, dialogAjaxDone);">
		<div class="pageFormContent nowrap"> 
		<input id="hCITYNM_c2" name="hCITYNM_c2" type="hidden" value="${searchMap.seach_CITYNM_c2}" />
		    <dl>
				<dt><spring:message code="pa.salary.canShu.niandu"/><!--年度--></dt>
				<dd>
				<ait:date yearName="PQD_ND" ></ait:date>
				</dd>
			</dl>
			
			<%-- <dl>
				<dt><spring:message code="pa.salary.canShu.citycd"/><!--CITY_CD--></dt>
				<dd>
					 <ait:selectSyCode name="PQD_CITY_CD" parentNo="219597" limit="ALL" />	
				</dd>
			</dl>
			
			<dl>
				<dt><spring:message code="pa.salary.canShu.chengShiMingCheng"/><!--城市名称--></dt>
				<dd>
					 <ait:selectSyCode name="PQD_CSMC" parentNo="218074" limit="ALL"/>					
				</dd>
			</dl> --%>
			
			 <dl>
					<dt>省名称</dt>
					<dd><ait:SelectState id="seach_STATENM_p2" name="seach_STATENM_p2" type="STATE" parentNo=""  limit="all"/>
				</dd>
			</dl>
				<dl><!-- 城市名称： -->
					<dt>城市名称</dt>
					<td><span id="seach_CITYNM_c2" name="seach_CITYNM_c2" ></select></span>
				</td>
			</dl>
			
			<dl>
				<dt><spring:message code="pa.salary.canShu.zuidigongzi"/><!--最低工资--></dt>
				<dd>
					 <input name="PQD_ZDGZ" id="PQD_ZDGZ" class="required" value="" />						
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
