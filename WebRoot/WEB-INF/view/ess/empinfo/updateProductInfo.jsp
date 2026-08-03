<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script>

    function f_delShiftInfo(obj){
    	$("#"+obj+"").remove();
    }
    
    
     function delectApplyInfo(){
    	 if(window.confirm('确定提交删除申请吗？')){
               $("#deleteProductInfo").submit();
              }
    	
    	
    }
     
       function validateCallInfo(form, callback) {
	
		var $form = $("#deleteProductInfo");
		
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
    
    
    function validateCallbackViewFamilyInfo(form, callback) {
	
		var $form = $("#essAddProductInfo");
		
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

<div class="pageContent">
	<form id="essAddProductInfo" method="post"
		action="/ess/empinfo/essAddProductInfo"
		class="pageForm required-validate"
		onsubmit="return validateCallbackViewFamilyInfo(this, dialogAjaxDone);">

		<div class="pageFormContent" layoutH="56">
			<table width="100%" border="0" cellpadding="0" cellspacing="0"
				class="user_table">
				<c:if test="${APPLY_TYPE=='1'}">
					<input type="hidden" name="APPLY_TYPE"
							value="${1}"/>
							
				</c:if>
				<c:if test="${APPLY_TYPE=='2'}">
					<input type="hidden" name="APPLY_TYPE"
							value="${2}"/>
							
				</c:if>
				<input type="hidden" name="UPDATE_PRODUCT_NO"
							value="${PRODUCT_NO}"/>
				<input type="hidden" name="MAIN_PRODUCT"
							value="${MAIN_PRODUCT}"/>
				

<tr>

<td class="td_title" width="25%">
						开始日期
					</td>
					
<td class="td_title" width="25%">
						结束日期
					</td>
					
<td class="td_title" width="25%">
						产品类型
					</td>
					
<td class="td_title" width="25%">
						%
					</td>
</tr>


				<tr>
					
					<td class="td_type" >
						<input type="text" value="${ProductInfoList.START_DATE}"
							class="date required" readonly="true"
							format="yyyy-MM-dd" yearstart="-50" yearend="5"
						   disabled="disabled"	/>

					</td>
					<input type="hidden" name="START_DATE" value="${ProductInfoList.START_DATE}"/>
					
					<td class="td_type" >

						<input type="text" value="${ProductInfoList.END_DATE}"
							name="END_DATE" class="date required" readonly="true"
							format="yyyy-MM-dd" yearstart="-50" yearend="5"
							onClick="setdate(this);" />

					</td>

				
					
					<td class="td_type" >
						<ait:SelectSyCodeByCpnyID parentNo="211424" disabled="disabled" name="pp"
							cnpyID="${defaultCpny}" selected="${ProductInfoList.PRODUCT_TYPE}"  />

					</td>
					
					<input type="hidden" name="PRODUCT_TYPE" value="${ProductInfoList.PRODUCT_TYPE}"/>
				
					<td class="td_type" width="35%">

						<input type="text" name="PERCENT"
							value="${ProductInfoList.PERCENT}" class="textInput" />
					</td>

				</tr>

				


			</table>

			<div id="createTable" width="100%"></div>

			<input type="hidden" name="count" id="count" value="1">
		</div>
		<div class="formBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">
								<spring:message code="public.title.submit" />
								<!-- 保存 -->
							</button>
						</div>
					</div>
				</li>
				<li>
				<div class="button"><div class="buttonContent"><a type="button" class="close" onclick="javascript:delectApplyInfo()" ><span><spring:message code="public.title.delete"/> </span><!--删除 --> </a></div></div>
			</li>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" class="close">
								<spring:message code="public.title.cancle" />
								<!-- 取消 -->
							</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</form>
</div>


<!-- 删除申请 -->

<div style="visibility: hidden;">

<form id="deleteProductInfo" method="post"
		action="/ess/empinfo/essAddProductInfo"
		class="pageForm required-validate"
		onsubmit="return validateCallInfo(this, dialogAjaxDone);">

		<div class="pageFormContent" layoutH="56">
			<table width="100%" border="0" cellpadding="0" cellspacing="0"
				class="user_table">
		
					<input type="hidden" name="APPLY_TYPE"
							value="${3}"/>
							
				
				<input type="hidden" name="UPDATE_PRODUCT_NO"
							value="${PRODUCT_NO}"/>
				<input type="hidden" name="MAIN_PRODUCT"
							value="${MAIN_PRODUCT}"/>
				




				<tr>
					
					<td class="td_type" >
						<input type="text" value="${ProductInfoList.START_DATE}"
							name="START_DATE" class="date required" readonly="true"
							format="yyyy-MM-dd" yearstart="-50" yearend="5"
							/>

					</td>
					
					<td class="td_type" >

						<input type="text" value="${ProductInfoList.END_DATE}"
							name="END_DATE" class="date required" readonly="true"
							format="yyyy-MM-dd" yearstart="-50" yearend="5"
							onClick="setdate(this);" />

					</td>

				
					
					<td class="td_type" >
						<ait:SelectSyCodeByCpnyID name="PRODUCT_TYPE" parentNo="211424"
							cnpyID="${defaultCpny}" selected="${ProductInfoList.PRODUCT_TYPE}"  />

					</td>
				
					<td class="td_type" width="35%">

						<input type="text" name="PERCENT"
							value="${ProductInfoList.PERCENT}" class="textInput" />
					</td>

				</tr>

			</table>

			<div id="createTable" width="100%"></div>

			<input type="hidden" name="count" id="count" value="1">
		</div>
		
	</form>
</div>