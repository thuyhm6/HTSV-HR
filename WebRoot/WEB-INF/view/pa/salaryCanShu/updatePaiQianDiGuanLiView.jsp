<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<SCRIPT type='text/javascript'>		
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

 	function validateCallbackFormUpdateCheck(form, dialogAjaxDone){
		var csdj = $("#PQD_CSDJ").val();
		var sf = $("#PQD_SF").val();
		var csmc = $("#PQD_CSMC select").val();
		var dqmc = $("#PQD_DQMC select").val();
		
		if(csdj == null || csdj == ''){
				alertMsg.error('城市等级不能为空！');
				return false;
		}
		if(sf == null || sf == ''){
				alertMsg.error('省份不能为空！');
				return false;
		}
		if(csmc == null || csmc == ''){
				alertMsg.error('城市名称不能为空！');
				return false;
		}
		if(dqmc == null || dqmc == ''){
				alertMsg.error('地区名称不能为空！');
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
 
 	$(document).ready(function() {
		var stateCd=$('#PQD_SF').val();
		var cityCd0=$('#hCITYNM_ADD').val();
		var region0=$('#hREGION_ADD').val();
		changeState(stateCd,cityCd0);
		
		$('#PQD_SF').live('change',function(){
			var st=$('#PQD_SF').val();
			changeState(st,cityCd0);
			changeCity(null, null);
		});
		
		var cityCd=$('#PQD_CSMC select').val();
		changeCity(cityCd0, region0);
		
		$('#PQD_CSMC select').live('change',function(){
			var st=$('#PQD_CSMC select').val();
			changeCity(st, region0);
		});
	});
	
	function changeState(stateCd, cityCd){
		$.ajax({
			cache: false,
			url : '${base}/promoter/getListBySelect?type=CITY_DIS&parentNo='+stateCd+'&selected='+cityCd+'&name=PQD_CSMC',
			type : "get",
			dataType : "html",
			success : function(data) {
				$("#PQD_CSMC").html(data);
			}
		});
	}
	
	function changeCity(cityCd, region){
		$.ajax({
			cache: false,
			url : '${base}/promoter/getListBySelect?type=REGION_DIS&parentNo='+cityCd+"&selected="+region+'&name=PQD_DQMC',
			type : "get",
			dataType : "html",
			success : function(data) {
				$('#PQD_DQMC').html(data);
			}
		});
	}
</SCRIPT>
<div class="pageContent">
	<form method="post" action="/pa/salaryCanShu/updatePaiQianDiGuanLiInfo" class="pageForm required-validate" 
		onsubmit="return validateCallbackFormUpdateCheck(this, dialogAjaxDone);">
		<div class="pageFormContent nowrap">
			<input type="hidden" name="PQD_NO" value="${paiQianDiInfo.NO1 }">
			<input id="hCITYNM_ADD" name="hCITYNM_ADD" type="hidden" value="${paiQianDiInfo.CS_NO}" />
			<input id="hREGION_ADD" name="hREGION_ADD" type="hidden" value="${paiQianDiInfo.DQ_NO}" />
		    <dl>
				<dt><spring:message code="pa.salary.canShu.faRen"/><!--法人--></dt>
				<dd>
					 ${paiQianDiInfo.FR }			
				</dd>
			</dl>
			
			<dl>
				<dt><spring:message code="pa.salary.canShu.chengShiDengJi"/><!--城市等级--></dt>
				<dd>
					 <ait:selectSyCode name="PQD_CSDJ" parentNo="218067" selected="${paiQianDiInfo.CSDJ }"/>					
				</dd>
			</dl>
			
			<dl>
				<dt><spring:message code="pa.salary.canShu.shengFen"/><!--省份--></dt>
				<dd>
					 <ait:SelectState id="PQD_SF" name="PQD_SF" type="SHENG" parentNo="" selected="${paiQianDiInfo.SF_NO }" limit="all"/>				
				</dd>
			</dl>
			
			<dl>
				<dt><spring:message code="pa.salary.canShu.chengShiMingCheng"/><!--城市名称--></dt>
				<dd><%-- ${paiQianDiInfo.CSMC } --%>			
					 <span id="PQD_CSMC" name="PQD_CSMC"></span>		
				</dd>
			</dl>
		    
			<dl>
				<dt><spring:message code="pa.salary.canShu.diQuMingCheng"/><!--地区名称--></dt>
				<dd><%-- ${paiQianDiInfo.DQMC } --%>
					 <span id="PQD_DQMC" name="PQD_DQMC"></span>				
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
