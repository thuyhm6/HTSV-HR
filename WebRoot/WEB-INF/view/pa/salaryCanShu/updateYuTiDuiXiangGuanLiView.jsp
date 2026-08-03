<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<SCRIPT type='text/javascript'>		
function validateCallbackYuTiDuiXiangUpdate(form, ajax){
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


 $(function(){
	var parentMenuTree = [];
	$.ajax({
			type:'get',cache:false,contentType:'application/json',
			url:'/sys/menu/getMenuTree',dataType:'json',
			success:function(data){
						$.each(data, function(i, item){
							parentMenuTree.push({ id: item.MENU_NO, pid: item.MENU_PARENT_NO, text: item.MENU_NAME ,depth : item.DEPTH});
						 });
						$('#parentMenu').ligerComboBox({
							width: 250,selectBoxWidth: 300,selectBoxHeight: 300,treeLeafOnly:false,
							tree: {
									data: parentMenuTree,checkbox:false,idFieldName :'id',parentIDFieldName :'pid',
									nodeWidth:300
									},
							onSelected:function (note,newText){ 
									$.each(data, function(i, item){
										if(note==parentMenuTree[i].id)
											$('#Menudepth').attr('value',parentMenuTree[i].depth);
								 	});
									$('#MenuNo').attr('value',note);
									}
						});
			 }
		});
	});
</SCRIPT>
<div class="pageContent">
	<form method="post" action="/pa/salaryCanShu/updateYuTiDuiXiangGuanLiInfo" 
	class="pageForm required-validate" 
	onsubmit="return validateCallbackYuTiDuiXiangUpdate(this, dialogAjaxDone);">
	<input value="${interCpnyID }" id="faRenFlag" type="hidden">
		<div class="pageFormContent nowrap"> 
		
			 <input type="hidden" name="PQD_NO" value="${paiQianDiInfo.NO1 }">
			
		    	<dl>
				<dt><spring:message code="pa.salary.canShu.faRen"/><!--法人--></dt>
				<dd>
					 <span name="PQD_FR"> ${sessionScope.LoginUser.cpnyId }	</span>
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
									<option value="${daqu.DQ}" <c:if test="${daqu.DQ eq paiQianDiInfo.DQ}">selected</c:if>>${daqu.DQ}</option>
								</c:forEach>
						   </select>
	    			</dd>
	    		</dl>
	    	</c:if>
			
			<dl>
				<dt><spring:message code="pa.salary.canShu.renYuanLeiXingZu"/><!--人员类型组--></dt>
				<dd>
					 <ait:SelectSyCodeByCpnyID id="PQD_RYLXZ" name="PQD_RYLXZ" parentNo="211807" cnpyID="${interCpnyID}" selected="${paiQianDiInfo.RYLXZ}" limit="all"/>			
				</dd>
			</dl>
			
			<dl>
				<dt><spring:message code="pa.salary.canShu.shiFouCanYuYuTi"/><!--是否参与预提--></dt>
				<dd>
					 	<SELECT name="PQD_SFCYYT" id="PQD_SFCYYT"> 
	    					<option value="Y" <c:if test="${paiQianDiInfo.SFCYYT eq 'Y' }">selected</c:if> >Y</option>
	    					<option value="N" <c:if test="${paiQianDiInfo.SFCYYT eq 'N' }">selected</c:if> >N</option>
	    				</SELECT>				
				</dd>
			</dl>
			
			<dl>
				<dt><spring:message code="sys.arAffirmPost.title.ableStatus"/><!--启用状态-->:</dt>
				<dd>
					<select class="combox" name="PQD_ACTIVITY" id="ableStatus_pa0801">
						<option value="1" <c:if test="${paiQianDiInfo.PQD_ACTIVITY == 1 }">selected</c:if> >
						<spring:message code="sys.arAffirmPost.title.able"/><!--启用-->
						</option>
						<option value="0" <c:if test="${paiQianDiInfo.PQD_ACTIVITY == 0 }">selected</c:if> >
						<spring:message code="sys.arAffirmPost.title.enable"/><!--不启用-->
						</option>
					</select>
				</dd>
			</dl>
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
