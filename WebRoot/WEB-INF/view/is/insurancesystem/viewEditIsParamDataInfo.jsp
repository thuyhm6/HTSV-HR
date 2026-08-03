<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script type="text/javascript">
<!--
function updateIsParamDataCallback(form,callback) {	
	var $form = $(form);	
	if (!$form.valid()) {
		return false;
	}
	var insureRate = $("#viewEditIsParamDataInfo input[name='INSURE_RATE']").val();
	var insureValue = $("#viewEditIsParamDataInfo input[name='INSURE_VALUE']").val();
	
	if(insureRate==""){ 
		alertMsg.error("地区比率不能为空，请选择地区比率!");
		$("#INSURE_RATE").focus();
		return false;
	}
	if(insureValue==""){ 
		alertMsg.error("地区金额不能为空，请选择地区金额!");
		$("#INSURE_VALUE").focus();
		return false;
	}
	//if (confirm ("确定要修改吗？")){	          
		$.ajax({
			type: form.method || 'POST',
			url:$form.attr("action"),
			data:$form.serializeArray(),
			dataType:"json",
			cache: false,
			success: callback || DWZ.ajaxDone,
			 
			error: DWZ.ajaxError
		});		
	//}
	return false;
}
//-->
</script>

<div class="pageContent">
	<form id="viewEditIsParamDataInfo" name="viewEditIsParamDataInfo" method="post" action="/is/insurancesystem/updateInsuranceParamData" 
		class="pageForm required-validate" onsubmit="return updateIsParamDataCallback(this, dialogAjaxDone);">
		<input type="hidden" id="DATA_NO" name="DATA_NO" value="${isParamMap.DATA_NO }"/>
		<div class="pageFormContent" layoutH="56">
			<p>
				<label><!-- 大区编码 -->
					大区编码:
				</label>
				${isParamMap.PAY_AREA_NAME }
			</p>
			<p>
				<label><!-- 福利地区 -->
					福利地区:
				</label>
				${isParamMap.INSRAREA_NAME }
			</p>
			<p>
				<label><!-- 福利项目 -->
					福利项目:
				</label>
				${isParamMap.INSURE_NAME }
			</p>
			<p>
				<label><!-- 地区比率 -->
					地区比率:
				</label>
				<input type="text" id="INSURE_RATE" name="INSURE_RATE" value="${isParamMap.INSURE_RATE }" size="12"/>
			</p>
			<p>
				<label><!-- 地区金额 -->
					地区金额:
				</label>
				<input type="text" id="INSURE_VALUE" name="INSURE_VALUE" value="${isParamMap.INSURE_VALUE }" size="12"/>
			</p>
			<p>
				<label><!-- 是否启用 -->
					是否启用：
				</label>
				<select name="ACTIVITY_FLAG" id="ACTIVITY_FLAG">
					<option value="1" <c:if test="${ACTIVITY_FLAG eq isParamMap.ACTIVITY }">selected</c:if>><!-- 是 -->是</option>
					<option value="0" <c:if test="${ACTIVITY_FLAG eq isParamMap.ACTIVITY }">selected</c:if>><!-- 否 -->否</option>
				</select>
			</p>					
			<p>
				<label><!--备注-->
					备注:
				</label>
				<textarea cols="80" rows="4" class="l-textarea" name="REMARK"
					id="REMARK" style="width: 400px" class="required" maxlength="1000">${isParamMap.REMARK }</textarea>
			</p>

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