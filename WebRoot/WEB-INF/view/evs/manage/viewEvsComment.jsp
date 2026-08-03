<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
$(document).ready(function(){
	<c:if test="${editFlag eq 1}">
	$("#AFFIRM_CONTENT",$.pdialog.getCurrent()).val($("#affirmContent_${index}" ,navTab.getCurrentPanel()).val());
	</c:if>
	$("#viewEvsComment_confirm",$.pdialog.getCurrent()).click(function(){
		var affirmContent = $("#affirmContent_${index}" ,navTab.getCurrentPanel());
		affirmContent.val($("#AFFIRM_CONTENT",$.pdialog.getCurrent()).val());
		if($("#AFFIRM_CONTENT",$.pdialog.getCurrent()).val() == ''){
			affirmContent.parent().find("img").attr("src","/resources/images/newImages/Add_en.gif");
		}else{
			affirmContent.parent().find("img").attr("src","/resources/images/newImages/view_en.gif");
		}
		$.pdialog.closeCurrent();
	});
});
</script>
<div style="background-color:#ffffff;">
	<div style="margin-left:20px;margin-right:20px;">
		<div style="font:bold 14px/20px arial,sans-serif;float:left;height:30px;line-height:30px;">Comment</div>
		<textarea style="width:100%;height:150px;" id="AFFIRM_CONTENT" class="editor" tools="Cut,Copy,Paste,|,Fullscreen">${affirmContent }</textarea>
	</div><div class="formBar">
			<ul>
				<c:if test="${editFlag eq 1 }">
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" id="viewEvsComment_confirm">
								<spring:message code="hrm.empinfo.CONFIRM"/><!--确定-->
							</button>
						</div>
					</div>
				</li>
				</c:if>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" class="close">
								<spring:message code="ess.infoApply.close"/><!--关闭-->
							</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
</div>

