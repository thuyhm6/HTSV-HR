<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript">
function validateAddpaItemFormulaInfoCallback(form,callback) {
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

function validateDeletepaItemFormulaInfoCallback(form,callback) {
	var $form = $("#" + form);	
	alertMsg.confirm("确定要删除吗？",
  		  	{okCall:function(){
		  	$.ajax({
  				type: form.method || 'POST',
  				url:'/pa/salarycode/deletePaItemFormulaInfo',
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
		<form id="viewAddpaItemFormulaInfo" method="post" action="/pa/salarycode/addPaItemFormulaInfo" class="pageForm required-validate" 
			onsubmit="return validateAddpaItemFormulaInfoCallback(this,navTabAjaxDone);">
			<div>
				<table class="user_table" width="100%" border="1" cellpadding="2" cellspacing="1">
					<tr>
						<td>
							<table  class="user_table" width="100%">
								<tr>
									<td width="15%" class="td_title">报表名</td>
									<td width="35%" class="td_type">
									   	<ait:SelectSyCodeByCpnyID name="REPORT_TYPE" selected="${paItemFormulaInfo.REPORT_TYPE}" parentNo="14015465" cnpyID="${LoginUser.cpnyId}"/>
									</td>
									<td width="15%" class="td_title">
										text域
									</td>
									<td width="35%" class="td_type">
										<input type="text" id="CONTENT" name="CONTENT" value="${paItemFormulaInfo.CONTENT}" size="35"/>
									</td>
								</tr>
								<tr>
									<td width="15%" class="td_title">工资项目</td>
									<td width="35%" class="td_type">
										<input type="text" id="ITEM_NAME" name="ITEM_NAME" value="${paItemFormulaInfo.ITEM_NAME}" size="35"/>
										<input type="hidden" id="SEQ" name="SEQ" value="${paItemFormulaInfo.SEQ}"/>
									</td>
									<td width="15%" class="td_title">
										状态
									</td>
									<td width="35%" class="td_type">
									   	<ait:SelectSyCodeByCpnyID name="ACTIVITY" selected="${paItemFormulaInfo.ACTIVITY}" parentNo="14013911" cnpyID="${LoginUser.cpnyId}"/>
									</td>
								</tr>
								<tr>
									<td width="15%" class="td_title">
										借贷区分
									</td>
									<td width="35%" class="td_type">
										<input type="text" id="DR_CR" name="DR_CR" value="${paItemFormulaInfo.DR_CR}" class="required" size="35"/>
									</td>
									<td width="15%" class="td_title">
										会计科目
									</td>
									<td width="35%" class="td_type">
										<input type="text" id="ACCOUNT" name="ACCOUNT" value="${paItemFormulaInfo.ACCOUNT}" class="required" size="35"/>
									</td>
								</tr>
								
								<tr>
									<td width="15%" class="td_title">
										汇总类型
									</td>
									<td width="35%" class="td_type">
										<select name="SUM_TYPE">
											<option value="1" <c:if test="${paItemFormulaInfo.SUM_TYPE eq 1 }">selected</c:if>>部门</option>
											<option value="2" <c:if test="${paItemFormulaInfo.SUM_TYPE eq 2 }">selected</c:if>>公司</option>
										</select>
									</td>
									<td width="15%" class="td_title">
										反记账
									</td>
									<td width="35%" class="td_type">
										<input type="text" id="FAN_JIZHANG" name="FAN_JIZHANG" value="${paItemFormulaInfo.FAN_JIZHANG}" size="35"/>
									</td>
								</tr>
								
								<tr>
									<td width="15%" class="td_title">
										WBS
									</td>
									<td width="35%" class="td_type">
										<input type="text" id="WBS" name="WBS" value="${paItemFormulaInfo.WBS}" size="35"/>
									</td>
									<td width="15%" class="td_title">
										排序号
									</td>
									<td width="35%" class="td_type">
										<input type="text" id="ORDER_NO" name="ORDER_NO" value="${paItemFormulaInfo.ORDER_NO}" size="35"/>
									</td>
								</tr>
								<tr>
									<td width="15%" class="td_title">
										总科目
									</td>
									<td class="td_type" colspan="3">
										<input type="text" id="PARENT_ACCOUNT" name="PARENT_ACCOUNT" value="${paItemFormulaInfo.PARENT_ACCOUNT}" size="35"/>
									</td>
								</tr>
								<tr>
									<td width="15%" class="td_title">
										基准式
									</td>
									<td width="85%" class="td_type" colspan="3">
										<textarea name="FORMULA"  style="width:650px;height:120px">${paItemFormulaInfo.FORMULA}</textarea>
									</td>
								</tr>
								<tr>
									<td width="15%" class="td_title">
										备注
									</td>
									<td width="85%" class="td_type" colspan="3">
										<textarea name="REMARK"  style="width:650px;height:120px">${paItemFormulaInfo.REMARK}</textarea>
									</td>
								</tr>
								<tr>
									<td width="15%" class="td_title">
										变更者
									</td>
									<td width="35%" class="td_type">
										${paItemFormulaInfo.UPDATED_BY}
									</td>
									<td width="15%" class="td_title">
										变更时间
									</td>
									<td width="35%" class="td_type">
									   	${paItemFormulaInfo.UPDATE_DATE}
									</td> 
								</tr>
							</table>	
				</table>
			</div>
	  	</form>	
