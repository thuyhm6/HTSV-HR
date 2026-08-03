<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function addIsParamDataNoPayArea(form, callback) {
	var $form = $("#addIsParamDataNoPayAreaInfo");
	var insrareaId = $("#addIsParamDataNoPayAreaInfo select[name='INSRAREA_ID']:selected").val();
	var insureId = $("#addIsParamDataNoPayAreaInfo select[name='INSURE_ID']:selected").val();
	var insureRate = $("#addIsParamDataNoPayAreaInfo input[name='INSURE_RATE']").val();
	var insureValue = $("#addIsParamDataNoPayAreaInfo input[name='INSURE_VALUE']").val();
	
	if(insrareaId==""){ 
		alertMsg.error("福利地区不能为空，请选择福利地区!");
		$("#INSRAREA_ID").focus();
		return false;
	}
	if(insureId==""){ 
		alertMsg.error("福利项目不能为空，请选择福利项目!");
		$("#INSURE_ID").focus();
		return false;
	}
	if(insureRate==""){ 
		alertMsg.error("地区比率不能为空，请填写地区比率!");
		$("#INSURE_RATE").focus();
		return false;
	}
	if(insureValue==""){ 
		alertMsg.error("地区金额不能为空，请填写地区金额!");
		$("#INSURE_VALUE").focus();
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
		success: callback || DWZ.ajaxDone,
		error: DWZ.ajaxError
	});
	
	return false;
}
</script>

<script type="text/javascript">
	var ajaxGet_add;
	function searchInsContentAdd(key){
 		var ok = "ok";
	  	var id=1;
		if (ajaxGet_add != null) {
			ajaxGet_add.abort();
		}
		ajaxGet_add = $.ajax( {
			type : "POST",
			url : "/is/insurancesystem/getInsrareaInfoListByKey",
			data : {KEY   : key,
					CHECK : ok}, 
			dataType : "json",
			success : function(data) {			 
				$('#insarea_listAdd').html("");
				var html = '';
				if (typeof (data['insAreaList']) != "undefined") {
					$('#insarea_listAdd').show();
					$.each(data['insAreaList'],
						function(commentIndex, comment) {
							html += '<li class="deptTreeLi" onclick="selectedIt_addInsAreaAdd(\'' + comment.CODE_NO + '\',\'' 
								+ comment.CODE_NAME + '\',\'' + comment.DESCRIPTION+'\')">'
								+ '<div>'+ comment.CODE_NO + '</div><div>'+ comment.CODE_NAME + '</div><i>'+ comment.DESCRIPTION+ '</i></li>';
						});
					}
					$('#insarea_listAdd').html(html);
					$("#insarea_list_panelAdd").show();
				}
			});
	}
	function selectedIt_addInsAreaAdd(code_no,code_name,description){
		$("#INSRAREA_ID").val('');
		$("#INSRAREA_ID").val(code_no);
		$("#INSRAREA_ID_VIEW").val(code_name);
		$('#insarea_list_panelAdd').hide();
	}
	function closeInsAreaAdd() {                                                                                              
		$('#insarea_list_panelAdd').css('display', 'none');                                                       
	}

	var ajaxGet_add2;
	function searchInsureContentAdd(key){
 		var ok = "ok";
	  	var id=1;
		if (ajaxGet_add != null) {
			ajaxGet_add.abort();
		}
		ajaxGet_add = $.ajax( {
			type : "POST",
			url : "/is/insurancesystem/getInsureInfoListByKey",
			data : {KEY   : key,
					CHECK : ok}, 
			dataType : "json",
			success : function(data) {			 
				$('#insure_listAdd').html("");
				var html = '';
				if (typeof (data['insureList']) != "undefined") {
					$('#insure_listAdd').show();
					$.each(data['insureList'],
						function(commentIndex, comment) {
							html += '<li class="deptTreeLi" onclick="selectedIt_addInsureAdd(\'' + comment.CODE_NO + '\',\'' 
								+ comment.CODE_NAME + '\',\'' + comment.DESCRIPTION+'\')">'
								+ '<div>'+ comment.CODE_NO + '</div><div>'+ comment.CODE_NAME + '</div></li>';
						});
					}
					$('#insure_listAdd').html(html);
					$("#insure_list_panelAdd").show();
				}
			});
	}
	function selectedIt_addInsureAdd(code_no,code_name,description){
		$("#INSURE_ID").val('');
		$("#INSURE_ID").val(code_no);
		$("#INSURE_ID_VIEW").val(code_name);
		$('#insure_list_panelAdd').hide();
	}
	function closeInsureAdd() {                                                                                              
		$('#insure_list_panelAdd').css('display', 'none');                                                       
	}
</script>

<div class="pageContent">
	<form id="addIsParamDataNoPayAreaInfo" name="addIsParamDataNoPayAreaInfo" method="post" class="pageForm required-validate" 
		action="/is/insurancesystem/addInsuranceParamDataNoPayArea" onsubmit="return addIsParamDataNoPayArea(this, dialogAjaxDone);">
		<div class="pageFormContent nowrap" layoutH="90">
			<table width="100%"  border="0" cellpadding="0" cellspacing="0">
			<tr>
					<td width="25%" style="text-align:center"><!-- 福利地区 -->
						福利地区:
					</td>
					<td>
						<%--
						<ait:SelectSyCodeByCpnyID name="INSRAREA_ID" parentNo="216736" cnpyID="${defaultCpny}" selected="${INSRAREA_ID}"/>
						--%>
						<input id="INSRAREA_ID" name="INSRAREA_ID" type="hidden" value=""/>
						<input id="INSRAREA_ID_VIEW" name="INSRAREA_ID_VIEW" onkeyup="searchInsContentAdd(this.value)" 
							onfocus="searchInsContentAdd(this.value)" type="text"/>
						<font color="red">输入地区编号/名称进行查询</font>
					</td>
				</tr>
				<tr>					
					<td colspan="2">					
						<div id="insarea_list_panelAdd" class="deptContent_sso" style="display:none;">
							<div class="ztree_dept_sso">
								<div class="ztree_dept_title">
									<ul class="ztree_dept_table">
										<li class="deptTreeLi" style="background:#E0D8DC;color:#796C5E;"><div>编号</div><div>名称</div><i>描述</i></li>
									</ul>
								</div>
								<div class="ztree_dept_type_sso">
									<ul id="insarea_listAdd" class="ztree_dept_table" ></ul>
								</div>
								<div class="ztree_dept_color">
									<a href="#" onclick="closeInsAreaAdd()" class="ztree_dept_color_a">
										<span>关闭</span>
									</a>
								</div>
							</div>
						</div>
					</td>
				</tr>
				<tr>
					<td width="25%" style="text-align:center"><!-- 福利项目 -->
						福利项目:
					</td>
					<td>
					<%--
					<ait:SelectSyCodeByCpnyID name="INSURE_ID" parentNo="219677" cnpyID="${defaultCpny}" selected="${INSURE_ID}"/>
					--%>
						<input id="INSURE_ID" name="INSURE_ID" type="hidden" value=""/>
						<input id="INSURE_ID_VIEW" name="INSURE_ID_VIEW" onkeyup="searchInsureContentAdd(this.value)" 
							onfocus="searchInsureContentAdd(this.value)" type="text"/>
						<font color="red">输入项目编号/名称进行查询</font>
					</td>
				</tr>
				<tr>					
					<td colspan="2">					
						<div id="insure_list_panelAdd" class="deptContent_sso" style="display:none;">
							<div class="ztree_dept_sso">
								<div class="ztree_dept_title">
									<ul class="ztree_dept_table">
										<li class="deptTreeLi" style="background:#E0D8DC;color:#796C5E;"><div>编号</div><div>名称</div></li>
									</ul>
								</div>
								<div class="ztree_dept_type_sso">
									<ul id="insure_listAdd" class="ztree_dept_table" ></ul>
								</div>
								<div class="ztree_dept_color">
									<a href="#" onclick="closeInsureAdd()" class="ztree_dept_color_a">
										<span>关闭</span>
									</a>
								</div>
							</div>
						</div>
					</td>
				</tr>
				<tr>
					<td width="25%" style="text-align:center"><!-- 地区比率 -->
						地区比率:
					</td>
					<td>
						<input type="text" id="INSURE_RATE" name="INSURE_RATE" value="" size="10" class="textInput required" min="-99999999999999"/>
					</td>
				</tr>
				<tr>
					<td width="25%" style="text-align:center"><!-- 地区金额 -->
						地区金额:
					</td>
					<td>
						<input type="text" id="INSURE_VALUE" name="INSURE_VALUE" value="" size="10" class="textInput required" min="-99999999999999"//>
					</td>
				</tr>
				<tr>
					<td width="25%" style="text-align:center"><!-- 是否启用 -->
						是否启用：
					</td>
					<td>
						<select name="ACTIVITY_FLAG" id="ACTIVITY_FLAG">
							<option value="1" <c:if test="${ACTIVITY_FLAG eq '1' }">selected</c:if>><!-- 是 -->是</option>
							<option value="0" <c:if test="${ACTIVITY_FLAG eq '0' }">selected</c:if>><!-- 否 -->否</option>
						</select>
					</td>
				</tr>
				<c:if test="${defaultCpny eq 'LGEQA' }">
					<tr>
						<td width="25%" style="text-align:center"><!-- 四舍五入 -->
						四舍五入：
						</td>
						<td>
							<select name="CARRY_WAY" id="CARRY_WAY">
								<option value="A" <c:if test="${CARRY_WAY eq 'A' }">selected</c:if>>向上进一位</option>
								<option value="B" <c:if test="${CARRY_WAY eq 'B' }">selected</c:if>>四舍五入，保留两位小数 </option>
								<option value="C" <c:if test="${CARRY_WAY eq 'C' }">selected</c:if>>四舍五入后取整</option>
								<option value="D" <c:if test="${CARRY_WAY eq 'D' }">selected</c:if>>向下取整</option>
								<option value="E" <c:if test="${CARRY_WAY eq 'E' }">selected</c:if>>保留一位小数，第二位进1 </option>
							</select>
						</td>
					</tr>
				</c:if>				
				<tr>
					<td width="25%" style="text-align:center"><!--备注-->
						备注:
					</td>
					<td>
						<textarea cols="40" rows="4" class="l-textarea" name="REMARK"
							id="REMARK" style="width: 300px" class="required" maxlength="320"></textarea>
					</td>						
				</tr>
			</table>
		</div>
		<div class="formBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="submit"><!--保存-->
								<spring:message code="pa.insurance.title.submit"/>
							</button>
						</div>
					</div>
				</li>
				<li>
					<div class="button">
						<div class="buttonContent"><!-- 取消 -->
							<button type="button" class="close">
								取消
							</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</form>
</div>
