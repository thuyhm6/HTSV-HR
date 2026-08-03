<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function chooseCode(){
	
}
</script>
<div class="pageContent">
	<form method="post" action="/edu/traineducation/addCourseManagerInfo" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone);">
		<div class="pageFormContent nowrap">
			<dl>
				<dt><spring:message code="edu.systemManager.PEIXUNLEIXING.a"/><!--培训类型--></dt>
				<dd>
					<select id="TRAIN_TYPE_CODE" name="TRAIN_TYPE_CODE" onchange="chooseCode()">
					<c:forEach items="${systemManagerList}" var="s" varStatus="i">
					<option value="${s.SYSMANA_NO }">${s.TRAIN_TYPE_NO }&nbsp&nbsp&nbsp&nbsp${s.TRAIN_TYPE_CODE_NAME }</option>
					</c:forEach>
					</select>
					
				</dd>
			</dl>
			<dl>
				<dt><spring:message code="empsubject.subjectNm"/><!--课程名称
			--></dt>
				<dd>
                    <input type="input" name="COURSE_NAME_CODE" id="COURSE_NAME_CODE">
				</dd>
			</dl>
			<dl>
				<dt><spring:message code="edu.courseManager.KECHENGBIANHAO.a"/><!--课程编号
			--></dt>
				<dd>
				</dd>
			</dl>
			<dl>
				<dt><spring:message code="ar.viewarcardrecord.title.beizhu"/><!--备注
		--></dt>
				<dd>
					<input type="text" name="REMARK" id="REMARK" value=""/>
				</dd>
			</dl>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit"><!-- 提交 --><spring:message code="public.title.submit"/></button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close"><!-- 取消 --><spring:message code="public.title.cancle"/></button></div></div></li>
			</ul>
		</div>
	</form>
</div>
