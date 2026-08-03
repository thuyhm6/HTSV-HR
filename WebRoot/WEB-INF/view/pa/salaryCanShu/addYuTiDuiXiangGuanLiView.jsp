<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
function validateCallbackYuTiDuiXiang(form, ajax){
	var dq = $("#PQD_DQ").val();
	var renYuanLeiXingZu = $("#PQD_RYLXZ").val();
	var yuTi = $("#PQD_SFCYYT").val();
	var faRenFlag = $("#faRenFlag").val();
	
	if('TSTO' == faRenFlag && (dq == '' || dq == null)){
		alertMsg.error("大区不能为空!");
		return false;
	}

	if(renYuanLeiXingZu == '' || renYuanLeiXingZu == null){
		alertMsg.error('<spring:message code="pa.salary.canShu.rylxzbnwk"/>');
		return false;
	}

	if(yuTi == '' || yuTi == null){
		alertMsg.error('<spring:message code="pa.salary.canShu.ytyfbnwk"/>');
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
		success: ajax || DWZ.ajaxDone,
		error: DWZ.ajaxError
	});

	return false;
	
}

</script>

<div class="pageContent">
     <form method="post" action="/pa/salaryCanShu/addYuTiDuiXiangGuanLiInfo" class="pageForm required-validate" 
     	onsubmit="return validateCallbackYuTiDuiXiang(this, dialogAjaxDone);">
     	<input value="${interCpnyID }" id="faRenFlag" type="hidden">
		<div class="pageFormContent nowrap"> 
		    <dl>
				<dt><spring:message code="pa.salary.canShu.faRen"/><!--法人--></dt>
				<dd>
					 <ait:SyCompany target="config" cpnyId="${sessionScope.LoginUser.cpnyId }" name="PQD_FR" language="zh"  limit="ALL" activity="1" selected="${sessionScope.LoginUser.cpnyId }"/>
				</dd>
			</dl>
			
			<c:if test="${'TSTO' eq interCpnyID}">
				<dl>
					<dt>
	    				大区名称
	    			</dt> 
	    			<dd>
	    				 	<select name="PQD_DQ" id="PQD_DQ">
				    				<option value="">请选择</option>
								<c:forEach items="${daQuNamesList}" var="daqu">
									<option value="${daqu.DQ}">${daqu.DQ}</option>
								</c:forEach>
						   </select>
	    			</dd>
	    		</dl>
	    	</c:if>
			
			<dl>
				<dt><spring:message code="pa.salary.canShu.renYuanLeiXingZu"/><!--人员类型组--></dt>
				<dd>
					 <ait:SelectSyCodeByCpnyID id="PQD_RYLXZ" name="PQD_RYLXZ" parentNo="211807" cnpyID="${interCpnyID}" limit="all"/>
				</dd>
			</dl>
			
			<dl>
				<dt><spring:message code="pa.salary.canShu.shiFouCanYuYuTi"/><!--是否参与预提--></dt>
				<dd>
					 <SELECT name="PQD_SFCYYT" id="PQD_SFCYYT"> 
					 		<option value="">select</option>
	    					<option value="Y">Y</option>
	    					<option value="N">N</option>
	    				</SELECT>				
				</dd>
			</dl>
		    
			<dl>
				<dt><spring:message code="sys.arAffirmPost.title.ableStatus"/><!--启用状态-->:</dt>
				<dd>
					<select class="combox" name="PQD_ACTIVITY" id="ableStatus_pa0803">
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
