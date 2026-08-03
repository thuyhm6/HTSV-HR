<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type='text/javascript'>	
function RQcheck(RQ) {
    var date = RQ;
    var result = date.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);

    if (result == null)
        return false;
    var d = new Date(result[1], result[3] - 1, result[4]);
    return (d.getFullYear() == result[1] && (d.getMonth() + 1) == result[3] && d.getDate() == result[4]);

}
function validateCallback(form, callback) {

	var $form = $(form)
	var obj = document.getElementById("RETURN_VALUE");
	var START_DATE = document.getElementById("START_DATE");
	var END_DATE = document.getElementById("END_DATE");
	var ITEM_DISTINGUISH = document.getElementById("ITEM_DISTINGUISH").value;
	if(!/^[0-9]+\.?[0-9]*/.test(obj.value)){  
        alert("值请输入数字!");  
        return false;
    	}
	if(ITEM_DISTINGUISH=='507'){
		 if (!RQcheck(START_DATE.value)) {
	         alert("请输入正确的开始日期");
	         return false;
	     }
		 if (!RQcheck(END_DATE.value)) {
	         alert("请输入正确的结束日期");
	         return false;
	     }
	}else{
		 
		 if(!/^(\d{4})(\d{2})$/.test(START_DATE.value)){  
		        alert("请输入正确的开始日期!");  
		        return false;
		    	}
		 if(!/^(\d{4})(\d{2})$/.test(END_DATE.value)){  
		        alert("请输入正确的结束日期!");  
		        return false;
		    	}
	}
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
	<form method="post" action="/pa/salary/updateItemBatchData" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent nowrap" layoutH="45">
			<dl>
				<dt>项目名称</dt>
				<dd>
					${insuranceItemDataList.ALIAS_NAME}
				</dd>
			</dl>
			<dl>
				<dt>姓名</dt>
				<dd>
					${insuranceItemDataList.LOCAL_NAME}
				</dd>
			</dl>
			<dl>
				<dt>社号</dt>
				<dd>
					${insuranceItemDataList.EMPID}
				</dd>
			</dl>
			<dl>
				<dt>部门名</dt>
				<dd>
					${insuranceItemDataList.DEPT_NAME}
				</dd>
			</dl>
			<dl>
				<dt>开始时间</dt>
				<c:if test="${ITEM_DISTINGUISH eq 507}">
				<dd>
					<input tyep="text" class="required textInput" value='${insuranceItemDataList.START_DATE}' id='START_DATE' name='START_DATE'/>
				</dd>
				</c:if>
				<c:if test="${ITEM_DISTINGUISH eq 508}">
				<dd>
					<input tyep="text" class="required textInput" value='${insuranceItemDataList.START_MONTH}' id='START_DATE' name='START_DATE'/>
				</dd>
				</c:if>
			</dl>
			
			<dl>
				<dt>结束时间</dt>
				<c:if test="${ITEM_DISTINGUISH eq 507}">
				<dd>
					<input tyep="text" class="required textInput" value='${insuranceItemDataList.END_DATE}' id='END_DATE' name='END_DATE'/>
				</dd>
				</c:if>
				<c:if test="${ITEM_DISTINGUISH eq 508}">
				<dd>
					<input tyep="text" class="required textInput" value='${insuranceItemDataList.END_MONTH}' id='END_DATE' name='END_DATE'/>
				</dd>
				</c:if>
			</dl>
			<dl>
				<dt>值</dt>
				<dd>
					<input tyep="text"  class="required textInput" value='${insuranceItemDataList.RETURN_VALUE}' id='RETURN_VALUE' name='RETURN_VALUE'/>
				</dd>
			</dl>
			<dl>
				<dt>备注</dt>
				<dd>
					<input tyep="text"  class="required textInput" value='${insuranceItemDataList.REMARK}' id='REMARK' name='REMARK'/>
				</dd>
			</dl>
						<dl>
				<dt>是否已删除</dt>
				<dd>
										<c:if test="${insuranceItemDataList.UP_FLAG eq 'N'}">
											未删除
										</c:if>
										<c:if test="${ insuranceItemDataList.UP_FLAG eq 'Y'}">
											已删除
										</c:if>
				</dd>
			</dl>
			
		<input type="hidden"  value='${insuranceItemDataList.DATA_NO}' id='DATA_NO' name='DATA_NO'/>
		<input type="hidden"  value='${ITEM_DISTINGUISH}' id='ITEM_DISTINGUISH' name='ITEM_DISTINGUISH'/>

		

			
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">
				     <spring:message code="pa.insurance.title.submit"/><!--保存--></button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close">
					<spring:message code="public.title.cancle"/><!--取消--></button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>