<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script >
function validateCallback(form, dialogAjaxDone){
	var csdj = $("#PQD_CSDJ").val();
	var sz = $("#PQD_SZ").val();
	var zz = $("#PQD_ZZ").val();
	var dqmc = $("#PQD_DQMC").val();
		if(zz == null || zz == ''){
			alertMsg.error('<spring:message code="pa.salary.canShu.zhizecantkong"/>');
			return false;
		} 
		
		if(isNaN(sz)){
		alertMsg.error('<spring:message code="pa.salary.canShu.shuzhibushishuzi"/>');
		return false;
		} 
			
		 if(csdj == null || csdj == ''){
				alertMsg.error('<spring:message code="pa.salary.canShu.chengshidengjibunengweikong"/>');
				return false;
		} 
			
// 		 if(dqmc == null || dqmc == ''){
// 				alertMsg.error('<spring:message code="pa.salary.canShu.diqumingchengbunengweikong"/>');
// 				return false;
// 		} 

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
     <form method="post" action="/pa/salaryCanShu/addPaiQianDiJinTieBiaoZhunInfo" class="pageForm required-validate" 
     	onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent nowrap"> 
		    <dl>
				<dt><spring:message code="pa.salary.canShu.faRen"/><!--法人--></dt>
				<dd>
					  <ait:SyCompany target="config" cpnyId="${sessionScope.LoginUser.cpnyId }" name="PQD_FR" language="zh"  limit="ALL" activity="1"/>		
				</dd>
			</dl>
			
			
			<dl>
				<dt><spring:message code="pa.salary.canShu.zhiZe"/><!--职责--></dt>
				<dd>
					<select name="PQD_ZZ" id="PQD_ZZ">
				    				<option value="">请选择</option>
								<c:forEach items="${positionList}" var="position">
									<option value="${position.POSITION}" >${position.POSITION}
								</c:forEach>
					</select>		
				</dd>
			</dl>
			
			<dl>
				<dt><spring:message code="pa.salary.canShu.chengShiDengJi"/><!--城市等级--></dt>
				<dd>
					 <ait:selectSyCode name="PQD_CSDJ" parentNo="218067"/>					
				</dd>
			</dl>
			
			<dl>
				<dt><spring:message code="pa.salary.canShu.diQuMingCheng"/><!--地区名称--></dt>
				<dd>
					 <select name="PQD_DQMC" id="PQD_DQMC">
				    				<option value="">请选择</option>
								<c:forEach items="${dqmcListNew}" var="dqmc2">
									<option value="${dqmc2.NO}" >${dqmc2.NAME}</option>
								</c:forEach>
					</select>				
				</dd>
			</dl>
			
			
			<dl>
				<dt><spring:message code="pa.salary.canShu.shuZhi"/><!--数值--></dt>
				<dd>
					<input  name="PQD_SZ" id="PQD_SZ" value="" class="required"/>					
				</dd>
			</dl>
			
			<dl>
				<dt><spring:message code="pa.salary.canShu.beiZhu"/><!--备注--></dt>
				<dd>
					<input  name="PQD_BZ" value="" />					
				</dd>
			</dl>
			
			<dl>
				<dt><spring:message code="sys.arAffirmPost.title.ableStatus"/><!--启用状态-->:</dt>
				<dd>
					<select class="combox" name="PQD_ACTIVITY" id="ableStatus_pa0801">
						<option value="1" selected>
						<spring:message code="sys.arAffirmPost.title.able"/><!--启用-->
						</option>
						<option value="0" >
						<spring:message code="sys.arAffirmPost.title.enable"/><!--不启用-->
						</option>
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
