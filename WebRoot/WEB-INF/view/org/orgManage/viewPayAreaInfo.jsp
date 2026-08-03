<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
function changePage(){
	var count = $("#count").val();
	for(var i = 0; i < count; i++){
		$("#edit" + i).css("display","block");
		$("#info" + i).css("display","none");
	}
	$("#submitButton").css("display","block");
	$("#modifyButton").css("display","none");
}

function validateCallbackPayAreaInfo(form, callback) {


	var $form = $("#payAreaInfo");
	 
	if (!$form.valid()) {
		return false;
	}
	
	//确定要提交吗？
	if (confirm ('<spring:message code="hr.viewEvaluate.title.COMMIT_CONFIRM"/>')){	
		$.ajax({
			type: form.method || 'POST',
			url:$form.attr("action"),
			data:$form.serializeArray(),
			dataType:"json",
			cache: false,
			success: callback || DWZ.ajaxDone,
			error: DWZ.ajaxError
		});
	}
	return false;
}
</script>
<div class="pageContent" id="infoPayArea">
	<form id="payAreaInfo" method="post" action="/org/orgManage/updatePayAreaInfo" class="pageForm required-validate" onsubmit="return validateCallbackPayAreaInfo(this,navTabAjaxDone)">
	<div class="formBar">
		<ul class="toolBar">
			<li id="modifyButton"><div class="buttonActive"><div class="buttonContent" onclick="changePage()"><spring:message code="org.title.UPDATE"/></div></div></li>
			<li id="submitButton" style="display:none;"><div class="buttonActive"><div class="buttonContent"><button type="submit"><spring:message code="heran.examineSave.title"/><!-- 保存 --></button></div></div></li>
		</ul>
	</div>
	<table class="table" width="100%">
		<thead>
			<tr>
				<th><spring:message code="org.title.NO"/><!-- 序号 --></th>
				<th><spring:message code="org.title.BIGAREA_ID"/><!-- 大区id --></th>
				<th><spring:message code="org.title.BIGAREA_NAME"/><!-- 大区名称 --></th>
				<th><spring:message code="org.title.BIGAREA_CODE"/><!-- 大区编码 --></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${payAreaInfoList}" var="item" varStatus="i" >
				<tr>
					<td>
						${i.count}
					</td>
					<td>${item.DEPTNO}</td>
					<td>
						${item.DEPTNAME}
					</td>
					<td>
						<div id="info${i.index}">${item.PAY_AREA_CD}</div>
						<div id="edit${i.index}" style="display:none;">
							<input type="text" name="PAY_AREA_CD${i.index}" class="textInput" value="${item.PAY_AREA_CD}" maxlength="20" size="30"/>
							<input type="hidden" name="DEPTNO${i.index}" value="${item.DEPTNO}" />
						</div>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<input type="hidden" id="count" name="count" value="${count }"/>
	</form>
</div>