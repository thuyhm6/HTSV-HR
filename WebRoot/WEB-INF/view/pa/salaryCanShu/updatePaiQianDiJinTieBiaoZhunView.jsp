<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<SCRIPT type='text/javascript'>	

function validateCallback_pai(form, dialogAjaxDone){
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
			
		 if(dqmc == null || dqmc == ''){
				alertMsg.error('<spring:message code="pa.salary.canShu.diqumingchengbunengweikong"/>');
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
	<form method="post" action="/pa/salaryCanShu/updatePaiQianDiJinTieBiaoZhunInfo" class="pageForm required-validate" onsubmit="return validateCallback_pai(this, dialogAjaxDone);">

		<div class="pageFormContent nowrap"> 
		
			 <input type="hidden" name="PQD_NO" value="${paiQianDiInfo.NO1 }">
			 <input type="hidden" name="PQD_FR" value="${paiQianDiInfo.FR }">
			
		    <dl>
				<dt><spring:message code="pa.salary.canShu.faRen"/><!--法人--></dt>
				<dd>
					 ${paiQianDiInfo.FR }			
				</dd>
			</dl>
			
			<dl>
				<dt><spring:message code="pa.salary.canShu.zhiZe"/><!--职责--></dt>
				<dd>
					 <select name="PQD_ZZ" id="PQD_ZZ">
				    				<option value="">请选择</option>
								<c:forEach items="${positionList}" var="position">
									<option value="${position.POSITION}" <c:if test="${paiQianDiInfo.ZZ eq position.POSITION }">selected</c:if>>${position.POSITION}
								</c:forEach>
					</select>				
				</dd>
			</dl>
			
			<dl>
				<dt><spring:message code="pa.salary.canShu.chengShiDengJi"/><!--城市等级--></dt>
				<dd>
					 <ait:selectSyCode name="PQD_CSDJ" parentNo="218067" selected="${paiQianDiInfo.CSDJ }"/>					
				</dd>
			</dl>
			
			<dl>
				<dt><spring:message code="pa.salary.canShu.diQuMingCheng"/><!--地区名称--></dt>
				<dd>
					 	 <select name="PQD_DQMC" id="PQD_DQMC">
				    				<option value="">请选择</option>
								<c:forEach items="${dqmcList}" var="dqmc2">
									<option value="${dqmc2.NO}" <c:if test="${dqmc2.NO eq paiQianDiInfo.DQMC }">selected</c:if> >${dqmc2.NAME}</option>
								</c:forEach>
					</select>		
					 			
				</dd>
			</dl>
			
			<dl>
				<dt><spring:message code="pa.salary.canShu.shuZhi"/><!--数值--></dt>
				<dd>
					 <input type="text" value="${paiQianDiInfo.SZ }" name="PQD_SZ"	id="PQD_SZ" class="required"/>				
				</dd>
			</dl>
			
			<dl>
				<dt><spring:message code="pa.salary.canShu.beiZhu"/><!--备注--></dt>
				<dd>
					 <input type="text" value="${paiQianDiInfo.BZ }" name="PQD_BZ"	id="PQD_BZ" />				
				</dd>
			</dl>
		    
			<dl>
				<dt><spring:message code="sys.arAffirmPost.title.ableStatus"/><!--启用状态-->:</dt>
				<dd>
					<select class="combox" name="PQD_ACTIVITY" id="ableStatus_pa0802">
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
