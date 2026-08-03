<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script type="text/javascript">
<!--
function updateIsParamDataNoPayAreaCallback(form,callback) {	
	var $form = $(form);	
	if (!$form.valid()) {
		return false;
	}
	var insureRate = $("#viewEditIsParamDataNoPayAreaInfo input[name='INSURE_RATE']").val();
	var insureValue = $("#viewEditIsParamDataNoPayAreaInfo input[name='INSURE_VALUE']").val();
	
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
	<form id="viewEditIsParamDataNoPayAreaInfo" name="viewEditIsParamDataNoPayAreaInfo" method="post" class="pageForm required-validate" 
		action="/is/insurancesystem/updateInsuranceParamDataNoPayArea" onsubmit="return updateIsParamDataNoPayAreaCallback(this, dialogAjaxDone);">
		<input type="hidden" id="DATA_NO" name="DATA_NO" value="${isParamMap.DATA_NO }"/>
		<div class="pageFormContent" layoutH="56">
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
			<c:if test="${defaultCpny eq 'LGEQA' }">
				<P><!-- 四舍五入 -->
					<label>
						四舍五入：
					</label>	
						<select name="CARRY_WAY" id="CARRY_WAY">
								<option value="A" <c:if test="${isParamMap.CARRY_WAY eq 'A' }">selected</c:if>>向上进一位</option>
								<option value="B" <c:if test="${isParamMap.CARRY_WAY eq 'B' }">selected</c:if>>四舍五入，保留两位小数 </option>
								<option value="C" <c:if test="${isParamMap.CARRY_WAY eq 'C' }">selected</c:if>>四舍五入后取整</option>
								<option value="D" <c:if test="${isParamMap.CARRY_WAY eq 'D' }">selected</c:if>>向下取整</option>
								<option value="E" <c:if test="${isParamMap.CARRY_WAY eq 'E' }">selected</c:if>>保留一位小数，第二位进1 </option>
						</select>
				</p>
			</c:if>						
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