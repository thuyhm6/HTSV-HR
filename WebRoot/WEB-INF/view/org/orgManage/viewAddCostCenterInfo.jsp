<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript">
function validateAddCostCenterInfoCallback(form,callback) {
	var $form = $("#" + form);	
	if (!$form.valid()) {
		return false;
	}
	alertMsg.confirm('<spring:message code="org.title.SAVE_CONFIRM" />',
  		  	{okCall:function(){
		  	$.ajax({
  				type: form.method || 'POST',
  				url:$form.attr("action"),
  				data:$form.serializeArray(),
  				dataType:"json",
  				cache: false,
  				success: function(json){
		  			DWZ.ajaxDone(json);
		  			if (json.statusCode == DWZ.statusCode.ok){
						navTab.reload(json.forwardUrl);
		  			}
		  		},
  				error: DWZ.ajaxError
  			});
  	}});
	return false;
}

function validateDeleteCostCenterInfoCallback(form,callback) {
	var $form = $("#" + form);	
	alertMsg.confirm('<spring:message code="button.delete.sure" />',
  		  	{okCall:function(){
		  	$.ajax({
  				type: form.method || 'POST',
  				url:'/org/orgManage/deleteCostCenterInfo',
  				data:$form.serializeArray(),
  				dataType:"json",
  				cache: false,
  				success: callback || DWZ.ajaxDone,
  				error: DWZ.ajaxError
  			});
  	}});
	return false;
}
</script>
		<form id="viewAddCostCenterInfo" method="post" action="/org/orgManage/addCostCenterInfo" class="pageForm required-validate" 
			onsubmit="return validateAddCostCenterInfoCallback(this,navTabAjaxDone);">
			<div>
				<table class="user_table" width="100%" border="1" cellpadding="2" cellspacing="1">
					<tr>
						<td>
							<table  class="user_table" width="100%">
								<tr>
									<td width="15%" class="td_title"><spring:message code="org.title.COST_CENTER_NO" /><!-- 成本中心代码 --></td>
									<td width="35%" class="td_type">
										<input type="text" id="CODE_NO" name="CODE_NO" value="${CostCenterInfo.CODE_NO}" class="required"
											<c:if test="${not empty CostCenterInfo.CODE_NO }">
												 readonly="true"
											</c:if>  
											size="35"/>
										<input type="hidden" id="SEQ" name="SEQ" value="${CostCenterInfo.SEQ}"/>
									</td>
									<td width="15%" class="td_title">
										<spring:message code="org.title.status" /><!-- 状态 -->
									</td>
									<td width="35%" class="td_type">
									   	<ait:SelectSyCodeByCpnyID name="ACTIVITY" selected="${CostCenterInfo.ACTIVITY}" parentNo="14013911" cnpyID="${LoginUser.cpnyId}"/>
									</td> 
								</tr>
								<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
								<tr>
									<td width="15%" class="td_title">
										<spring:message code="org.title.COST_CENTER_ENGLISH.Z" /><!-- 成本中心英文名称 -->
									</td>
									<td width="35%" class="td_type">
										<input type="text" id="CODE_ENG_NAME" name="CODE_ENG_NAME" value="${CostCenterInfo.CODE_ENG_NAME}" size="35"/>
									</td>
									<td width="15%" class="td_title"></td>
									<td width="35%" class="td_type"></td>
								</tr>
								</c:if>
								<c:if test="${LoginUser.cpnyId eq 'HAE'}">
								<tr>
									<td width="15%" class="td_title">
										<spring:message code="org.title.COST_CENTER_CHINESE.Z" /><!-- 成本中心中文名称 -->
									</td>
									<td width="35%" class="td_type">
										<input type="text" id="CODE_NAME" name="CODE_NAME" value="${CostCenterInfo.CODE_NAME}" class="required" size="35"/>
									</td>
									<td width="15%" class="td_title">
										<spring:message code="org.title.COST_CENTER_ENGLISH.Z" /><!-- 成本中心英文名称 -->
									</td>
									<td width="35%" class="td_type">
										<input type="text" id="CODE_ENG_NAME" name="CODE_ENG_NAME" value="${CostCenterInfo.CODE_ENG_NAME}" size="35"/>
									</td>
								</tr>
								<tr>
									<td width="15%" class="td_title">
										<spring:message code="org.title.COST_CENTER_VIETNAMESE.Z" /><!-- 成本中心越南文名称 -->
									</td>
									<td width="35%" class="td_type">
										<input type="text" id="CODE_VIETNAMESE_NAME" name="CODE_VIETNAMESE_NAME" value="${CostCenterInfo.CODE_VIETNAMESE_NAME}" class="required" size="35"/>
									</td>
									<td width="15%" class="td_title">
										<spring:message code="org.title.COST_CENTER_KOREAN.Z" /><!-- 成本中心韩文名称 -->
									</td>
									<td width="35%" class="td_type">
										<input type="text" id="CODE_KOREAN_NAME" name="CODE_KOREAN_NAME" value="${CostCenterInfo.CODE_KOREAN_NAME}" size="35"/>
									</td>
								</tr>
								</c:if>
								<tr>
									<td width="15%" class="td_title">
										<spring:message code="org.title.START_DATE" /><!-- 生成日期 -->
									</td>
									<td width="35%" class="td_type">
										
										<input id="START_DATE" name="START_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})"  readonly="true" 
											<c:if test="${not empty CostCenterInfo.START_DATE }">
					                    	value="${CostCenterInfo.START_DATE}"
											</c:if>
											<c:if test="${empty CostCenterInfo.START_DATE }">
					                    	value="${START_DATE}"
											</c:if> size="35"/>
					                    <a class="inputDateButton" href="javascript:;"><spring:message code="public.title.choose"/><!-- 选择 --></a>
									</td>
									<td width="15%" class="td_title">
										<spring:message code="org.title.END_DATE" /><!-- 废弃日期 -->
									</td>
									<td width="35%" class="td_type">
										<input id="END_DATE" name="END_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})"
											<c:if test="${not empty CostCenterInfo.END_DATE }">
					                    	value="${CostCenterInfo.END_DATE}"
											</c:if>
											<c:if test="${empty CostCenterInfo.END_DATE }">
					                    	value="${END_DATE}"
											</c:if> size="35"/>
					                    <a class="inputDateButton" href="javascript:;"><spring:message code="public.title.choose"/><!-- 选择 --></a>
									</td>
								</tr>
							</table>
							<table  class="user_table" width="100%">
								<c:if test="${LoginUser.cpnyId eq 'SST'}">
								<tr>
									<td width="15%" class="td_title">
										<spring:message code="org.title.BUSINESS_SCOPE" /><!-- 业务范围 -->
									</td>
									<td width="35%" class="td_type">
										<input type="text" id="BUSINESS_SCOPE" name="BUSINESS_SCOPE" value="${CostCenterInfo.BUSINESS_SCOPE}" size="35"/>
									</td>
									<td width="15%" class="td_title">
										<spring:message code="org.title.PROFIT_CENTER" /><!-- 利润中心 -->
									</td>
									<td width="35%" class="td_type">
										<input type="text" id="PROFIT_CENTER" name="PROFIT_CENTER" value="${CostCenterInfo.PROFIT_CENTER}" size="35"/>
									</td> 
								</tr>
								</c:if>
								<tr>
									<td width="15%" class="td_title">
										<spring:message code="org.title.UPDATED_IP" /><!-- 变更者 -->
									</td>
									<td width="35%" class="td_type">
										[${CostCenterInfo.EMPID}]${CostCenterInfo.LOCAL_NAME} ${CostCenterInfo.UPDATED_IP}
									</td>
									<td width="15%" class="td_title">
										<spring:message code="org.title.UPDATE_DATE" /><!-- 变更时间 -->
									</td>
									<td width="35%" class="td_type">
									   	${CostCenterInfo.UPDATE_DATE}
									</td> 
								</tr>
							</table>	
				</table>
			</div>
	  	</form>	
