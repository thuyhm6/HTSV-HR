<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript">
function validateAddpaItemInfoCallback(form,callback) {
	var $form = $("#" + form);	
	if (!$form.valid()) {
		return false;
	}
	alertMsg.confirm("确定要保存吗？",
  		  	{okCall:function(){
		  	$.ajax({
  				type: form.method || 'POST',
  				url:$form.attr("action"),
  				data:$form.serializeArray(),
  				dataType:"json",
  				cache: false,
  				success: callback || DWZ.ajaxDone,
  				error: DWZ.ajaxError
  			});
  	}});
	return false;
}

function validateDeletepaItemInfoCallback(form,callback) {
	var $form = $("#" + form);	
	alertMsg.confirm("确定要删除吗？",
  		  	{okCall:function(){
		  	$.ajax({
  				type: form.method || 'POST',
  				url:'/pa/salarycode/deletePaItemInfo',
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
		<form id="viewAddPaItemInfo" method="post" action="/pa/salarycode/addPaItemInfo" class="pageForm required-validate" 
			onsubmit="return validateAddpaItemInfoCallback(this,navTabAjaxDone);">
			<div>
				<table class="user_table" width="100%" border="1" cellpadding="2" cellspacing="1">
					<tr>
						<td>
							<table  class="user_table" width="100%">
								<tr>
									<td width="15%" class="td_title">工资项目</td>
									<td width="35%" class="td_type">
										<select name="PA_ITEM_SEQ">
											<c:forEach items="${getPaItemList}" var="item" varStatus="i">
											<option value="${item.ITEM_NO }" <c:if test="${item.ITEM_NO eq paItemInfo.PA_ITEM_SEQ }">selected</c:if>>${item.DESCR }</option>
											</c:forEach>
										</select>
										<input type="hidden" id="SEQ" name="SEQ" value="${paItemInfo.SEQ}"/>
									</td>
									<td width="15%" class="td_title">
										状态
									</td>
									<td width="35%" class="td_type">
									   	<ait:SelectSyCodeByCpnyID name="ACTIVITY" selected="${paItemInfo.ACTIVITY}" parentNo="14013911" cnpyID="${LoginUser.cpnyId}"/>
									</td>
								</tr>
								<tr>
									<td width="15%" class="td_title">
										工资区分
									</td>
									<td width="35%" class="td_type">
										<input type="text" id="PA_DES" name="PA_DES" value="${paItemInfo.PA_DES}" class="required" size="35"/>
									</td>
									<td width="15%" class="td_title">
										财务区分
									</td>
									<td width="35%" class="td_type">
										<input type="text" id="CODE_NAME" name="CODE_NAME" value="${paItemInfo.CODE_NAME}" size="35"/>
									</td>
								</tr>
								
								<tr>
									<td width="15%" class="td_title">
										会计科目(40)
									</td>
									<td width="35%" class="td_type">
										<input type="text" id="CODE_40" name="CODE_40" value="${paItemInfo.CODE_40}" class="required" size="35"/>
									</td>
									<td width="15%" class="td_title">
										Assignment(40)
									</td>
									<td width="35%" class="td_type">
										<input type="text" id="CODE_NAME_40" name="CODE_NAME_40" value="${paItemInfo.CODE_NAME_40}" size="35"/>
									</td>
								</tr>
								
								<tr>
									<td width="15%" class="td_title">
										会计科目(50)
									</td>
									<td width="35%" class="td_type">
										<input type="text" id="CODE_50" name="CODE_50" value="${paItemInfo.CODE_50}" class="required" size="35"/>
									</td>
									<td width="15%" class="td_title">
										Assignment(50)
									</td>
									<td width="35%" class="td_type">
										<input type="text" id="CODE_NAME_50" name="CODE_NAME_50" value="${paItemInfo.CODE_NAME_50}" size="35"/>
									</td>
								</tr>
								<tr>
									<td width="15%" class="td_title">
										开始日期
									</td>
									<td width="35%" class="td_type">
										<input type="text" id="START_DATE" name="START_DATE" class="date required" format="yyyy.MM.dd"  readonly="true" 
											<c:if test="${not empty paItemInfo.START_DATE }">
					                    	value="${paItemInfo.START_DATE}"
											</c:if>
											<c:if test="${empty paItemInfo.START_DATE }">
					                    	value="${START_DATE}"
											</c:if> size="35"/>
					                    <a class="inputDateButton" href="javascript:;"><spring:message code="public.title.choose"/><!-- 选择 --></a>
									</td>
									<td width="15%" class="td_title">
										废弃日期
									</td>
									<td width="35%" class="td_type">
										<input type="text" id="END_DATE" name="END_DATE" class="date required" format="yyyy.MM.dd" 
					                    	readonly="true" 
											<c:if test="${not empty paItemInfo.END_DATE }">
					                    	value="${paItemInfo.END_DATE}"
											</c:if>
											<c:if test="${empty paItemInfo.END_DATE }">
					                    	value="${END_DATE}"
											</c:if> size="35"/>
					                    <a class="inputDateButton" href="javascript:;"><spring:message code="public.title.choose"/><!-- 选择 --></a>
									</td>
								</tr>
								<tr>
									<td width="15%" class="td_title">
										变更者
									</td>
									<td width="35%" class="td_type">
										${paItemInfo.UPDATED_BY}
									</td>
									<td width="15%" class="td_title">
										变更时间
									</td>
									<td width="35%" class="td_type">
									   	${paItemInfo.UPDATE_DATE}
									</td> 
								</tr>
							</table>	
				</table>
			</div>
	  	</form>	
