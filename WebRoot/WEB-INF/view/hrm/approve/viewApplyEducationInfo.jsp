<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript">

function f_delShiftInfo(obj) {
	$("#" + obj + "").remove();
}

function validateCallbackViewFamilyInfo(form, callback) {

	var $form = $("#updateEducationInfo");

	if (!$form.valid()) {
		return false;
	}

	$.ajax( {
		type : form.method || 'POST',
		url : $form.attr("action"),
		data : $form.serializeArray(),
		dataType : "json",
		cache : false,
		success : callback || DWZ.ajaxDone,
		error : DWZ.ajaxError
	});

	return false;
}

var clickType; 
function submitForm1(type){
	alert(type);
	
}
function submitForm2(type){
	if($("#SUBMIT_TYPE").val()==1){
			$("#SUBMIT_TYPE").val(2);
			
	var $form = $("#updateEducationInfo").submit();
	}
}
function submitForm3(type){
	if($("#SUBMIT_TYPE").val()==1){
	 $("#SUBMIT_TYPE").val(3);
			$("#APPLY_TYPE_NUM").val(8);
	var $form = $("#updateEducationInfo").submit();
	}
	
}
function submitForm4(type){
	alert(type);
	
}

</script>
<style type="text/css">
.a{
color: red;
}
</style>
  <%-- <c:if test="${educationInfoList.SUBMIT_TYPE eq 1}"  > --%>

<div class="pageHeader"
	style="width: 300px; margin-left: auto; margin-right: 2px; width: 300px;">

	<div class="searchBar" width="100%" style="border: 0px solid #8aa6d7;">
		<div class="subBar">
			<ul>
				<li>
					<div>
						<a class="buttonActive" onclick="print()" 
						alt="<spring:message code='hrm.approve.CLICK_UPLOAD.Z' />"><!-- 点击上传 --><span><!--印刷 --> <spring:message code="hrm.approve.PRINTING" />    </span>
						</a>
					</div>
				</li>
				<c:if test="${educationInfoList.SUBMIT_TYPE eq 1}"  >
				<li>
					<div>
						<a class="buttonActive" onclick="submitForm2('submit');" 
						alt="<spring:message code='hrm.approve.CLICK_UPLOAD.Z' />"><!-- 点击上传 --><span><!-- 完结  --> <spring:message code="hrm.approve.OVER" /> </span>
						</a>
					</div>
				</li>
				<li>
					<div>
						<a class="buttonActive" onclick="submitForm3('back');" 
						alt="<spring:message code='hrm.approve.CLICK_UPLOAD.Z' />"><!-- 点击上传 --><span><!-- 退回    --> <spring:message code="hrm.approve.RETURN" />  </span>
						</a>
					</div>
				</li>
				</c:if>
				<%-- <li>
					<div>
						<a class="buttonActive" onclick="submitForm4('email');" alt="点击上传"><span><!-- 制定邮件  --> <spring:message code="hrm.approve.MAKE_MAILE" /> </span>
						</a>
					</div>
				</li> --%>
			</ul>
		</div>
	</div>

</div>

<div class="pageContent" style="margin: 0px; padding: 0px;" sysLong="printDiv">
	<form id="updateEducationInfo"
		onsubmit="return validateCallbackViewFamilyInfo(this, navTabAjaxDoneWithForm);"
		action="/hrm/approve/updateEducationInfo" method="post">
		<table class="table" width="100%" 
			style="margin: 0px; padding: 0px;">
			<thead>
				<tr>

					<th width="25%">
						NO
					</th>
					<th width="25%">
						Line
					</th>
					<th width="25%">
						<!-- 申请后  -->  <spring:message code="hrm.approve.APPLY_AFTER" />
					</th>
					<th width="25%">
						<!-- 申请前   --> <spring:message code="hrm.approve.APPLY_FRONT" />
					</th>
				</tr>
			</thead>
			<tbody>
			<input type="hidden" value="${educationInfoList.PERSON_ID}" name="PERSON_ID" id="PERSON_ID"/>
			<input type="hidden" value="${educationInfoList.ESS_TYPE_CODE }"
						id="ESS_TYPE_CODE" name="ESS_TYPE_CODE" />
					<input type="hidden" value="${educationInfoList.APPLY_TYPE }"
						id="APPLY_TYPES" name="APPLY_TYPES" />
            <input type="hidden"  value="${educationInfoList.EDUC_NO}" id="EDUC_NO" name="EDUC_NO" />
            <input type="hidden"  value="${educationInfoList.UPDATE_EDUC_NO}" id="UPDATE_EDUC_NO" name="UPDATE_EDUC_NO" />
   			 <input type="hidden"  value="${educationInfoList.FILENOSSTR}" id="FILENOSSTR" name="FILENOSSTR" />
   
   <input type="hidden" value="${educationInfoList.APPLY_TYPE_NUM}" name="APPLY_TYPE" id="APPLY_TYPE_NUM"/>
          <!-- 1提交 2审批 3退回 4取消 -->
          <input type="hidden"  value="${educationInfoList.SUBMIT_TYPE}" id="SUBMIT_TYPE" name="ACTIVITY_NUM" />
          	<c:if test="${LoginUser.cpnyId eq 'HAE'}">
          		<tr>
					<td>
						1
					</td>
					<td>
					<!-- 毕业学校 --> <spring:message code="hr.viewPersonalInfo.title.SCHOOL" />
					</td>
					<td>
						<font style="line-height: 20px;"
							color="${educationInfoList.INSTITUTION_NAME ne educationInfoPro.INSTITUTION_NAME ?'red':''}">${educationInfoList.INSTITUTION_NAME}</font>
					</td>
					<td>
						${educationInfoPro.INSTITUTION_NAME}
					</td>
				</tr>
				<tr>
					<td>
						2
					</td>
					<td>
					<!--学校地址--><spring:message code="hrm.empinfo.XUEXIAO_ADDRESS.Z" />
					</td>
					<td>
						<font style="line-height: 20px;"
							color="${educationInfoList.PLACE ne educationInfoPro.PLACE ?'red':''}">${educationInfoList.PLACE}</font>
					</td>
					<td>
						${educationInfoPro.PLACE}
					</td>
				</tr>
				<tr>
					<td>
						3
					</td>
					<td>
					<!-- 学历 --> <spring:message code="hrm.empinfo.DEGREE_CODE" />
					</td>
					<td>
						<font style="line-height: 20px;"
							color="${educationInfoList.DEGREE_CODE ne educationInfoPro.DEGREE_CODE ?'red':''}">${educationInfoList.DEGREE_CODE}</font>
					</td>
					<td>
						${educationInfoPro.DEGREE_CODE}
					</td>
				</tr>
				<tr>
					<td>
						4
					</td>
					<td>
					<spring:message code="hrm.recruitManage.FINAL_DEGREE_WHETHER" /><!--认定学历与否-->
					</td>
					<td>
						<font style="line-height: 20px;"
							color="${educationInfoList.FINAL_DEGREE_WHETHER ne educationInfoPro.FINAL_DEGREE_WHETHER ?'red':''}">
							<input type="checkbox" disabled="disabled" <c:if test="${educationInfoList.FINAL_DEGREE_WHETHER eq 'Y'}"> checked="checked" </c:if> >
						</font>
					</td>
					<td>
						<input type="checkbox" disabled="disabled" <c:if test="${educationInfoPro.FINAL_DEGREE_WHETHER eq 'Y'}"> checked="checked" </c:if> >
					</td>
				</tr>
				<tr>
					<td>
						5
					</td>
					<td>
						<!-- 入学日期 --><spring:message code="hrm.recruitManage.START_DATE" />
					</td>
					<td>
						<font style="line-height: 20px;"
							color="${educationInfoList.START_DATE ne educationInfoPro.START_DATE ?'red':''}">${educationInfoList.START_DATE}</font>
					</td>
					<td>
						${educationInfoPro.START_DATE}
					</td>
				</tr>
				<tr>
					<td>
						6
					</td>
					<td>
					 <!-- 毕业日期 --> <spring:message code="hrm.recruitManage.END_DATE" />
					</td>
					<td>
						<font style="line-height: 20px;"
							color="${educationInfoList.END_DATE ne educationInfoPro.END_DATE ?'red':''}">${educationInfoList.END_DATE}</font>
					</td>
					<td>
						${educationInfoPro.END_DATE}
					</td>
				</tr>
				<tr>
					<td>
						7
					</td>
					<td>
					 <!--专业--><spring:message code="ess.empInfo.major" />
					</td>
					<td>
						<font style="line-height: 20px;"
							color="${educationInfoList.SUBJECT ne educationInfoPro.SUBJECT ?'red':''}">${educationInfoList.SUBJECT}</font>
					</td>
					<td>
						${educationInfoPro.SUBJECT}
					</td>
				</tr>
				<tr>
					<td>
						8
					</td>
					<td>
					 <spring:message code="hr.hrm.empinfo.SUBJECT_SECOND.Z" /><!--副专业-->
					</td>
					<td>
						<font style="line-height: 20px;"
							color="${educationInfoList.SUBJECT_SECOND ne educationInfoPro.SUBJECT_SECOND ?'red':''}">${educationInfoList.SUBJECT_SECOND}</font>
					</td>
					<td>
						${educationInfoPro.SUBJECT_SECOND}
					</td>
				</tr>
				<tr>
					<td>
						9
					</td>
					<td>
					 <!--教育形式--><spring:message code="hrm.empinfo.JIAOYU_XINGSHI.Z" />
					</td>
					<td>
						<font style="line-height: 20px;"
							color="${educationInfoList.GRADUATION_TYPE_NAME ne educationInfoPro.SCHOOL_LENGTH_NAME ?'red':''}">${educationInfoList.GRADUATION_TYPE_NAME}</font>
					</td>
					<td>
						${educationInfoPro.SCHOOL_LENGTH_NAME}
					</td>
				</tr>
				<tr>
					<td>
						10
					</td>
					<td>
					 <spring:message code="hrm.empinfo.graduation_Certificate_number" /><!--毕业证书编号-->
					</td>
					<td>
						<font style="line-height: 20px;"
							color="${educationInfoList.EDU_DEG_NUM ne educationInfoPro.EDU_DEG_NUM ?'red':''}">${educationInfoList.EDU_DEG_NUM}</font>
					</td>
					<td>
						${educationInfoPro.EDU_DEG_NUM}
					</td>
				</tr>
				<tr>
					<td>
						11
					</td>
					<td>
						 <!-- 附件 --><spring:message code="hrm.recruitManage.ENCLOSURE" />
					</td>
					<td>
						<span id="fileN"></span>
				<c:forEach items="${fileListpro}" var="item" varStatus="i">
					<c:forEach items="${fileList}" var="items" varStatus="i">
						 <c:if test="${item.FILE_NAME ne items.FILE_NAME}">
						    <a class="a" href="/ess/infoApplyLeave/downloadFile?fileName=${item.FILE_URL }&file=${item.FILE_NAME}">${item.FILE_NAME}</a>|
						 </c:if>
					</c:forEach>
					<c:if test="${educationInfoList.APPLY_TYPE_NUM eq 3}">
						    <a class="a" href="/ess/infoApplyLeave/downloadFile?fileName=${item.FILE_URL }&file=${item.FILE_NAME}">${item.FILE_NAME}</a>|
						 </c:if>
				</c:forEach>
					</td>
					<td>
					<c:forEach items="${fileList}" var="item" varStatus="i">
					<a href="/ess/infoApplyLeave/downloadFile?fileName=${item.FILE_URL }&file=${item.FILE_NAME}">${item.FILE_NAME}</a>|
					</c:forEach>
					<c:forEach items="${fileListpro}" var="item" varStatus="i">
					<c:forEach items="${fileList}" var="items" varStatus="i">
						 <c:if test="${item.FILE_NAME ne items.FILE_NAME}">
						    <a href="/ess/infoApplyLeave/downloadFile?fileName=${item.FILE_URL }&file=${item.FILE_NAME}">${item.FILE_NAME}</a>|
						 </c:if>
					</c:forEach>
					<c:if test="${educationInfoList.APPLY_TYPE_NUM eq 3}">
						    <a href="/ess/infoApplyLeave/downloadFile?fileName=${item.FILE_URL }&file=${item.FILE_NAME}">${item.FILE_NAME}</a>|
						 </c:if>
					</c:forEach>
					<c:forEach items="${fileListpro}" var="item" varStatus="i">
					<c:if test="${educationInfoList.APPLY_TYPE_NUM eq 3}">
						    <a href="/ess/infoApplyLeave/downloadFile?fileName=${item.FILE_URL }&file=${item.FILE_NAME}">${item.FILE_NAME}</a>|
						 </c:if>
				</c:forEach>
					</td>
				</tr>
				<tr>
					<td>
						12
					</td>
					<td>
						<!-- 备注 --><spring:message code="ess.empInfo.remarks" />
					</td>
					<td>
						<font style="line-height: 20px;"
							color="${educationInfoList.REMARKS ne educationInfoPro.REMARK ?'red':''}">${educationInfoList.REMARKS}
					</td>
					<td>
						${educationInfoPro.REMARK}
					</td>
				</tr>
				</c:if>
				<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
				<tr>
					<td>
						1
					</td>
					<td>
					<!-- 学历 --> <spring:message code="hrm.empinfo.DEGREE_CODE" />
					</td>
					<td>
						<font style="line-height: 20px;"
							color="${educationInfoList.DEGREE_CODE ne educationInfoPro.DEGREE_CODE ?'red':''}">${educationInfoList.DEGREE_CODE}</font>
					</td>
					<td>
						${educationInfoPro.DEGREE_CODE}
					</td>
				</tr>
				<tr>
					<td>
						2
					</td>
					<td>
						<!-- 入学日期 --><spring:message code="hrm.recruitManage.START_DATE" />
					</td>
					<td>
						<font style="line-height: 20px;"
							color="${educationInfoList.START_DATE ne educationInfoPro.START_DATE ?'red':''}">${educationInfoList.START_DATE}</font>
					</td>
					<td>
						${educationInfoPro.START_DATE}
					</td>
				</tr>
				<tr>
					<td>
						3
					</td>
					<td>
					 <!-- 毕业日期 --> <spring:message code="hrm.recruitManage.END_DATE" />
					</td>
					<td>
						<font style="line-height: 20px;"
							color="${educationInfoList.END_DATE ne educationInfoPro.END_DATE ?'red':''}">${educationInfoList.END_DATE}</font>
					</td>
					<td>
						${educationInfoPro.END_DATE}
					</td>
				</tr>
				
				<tr>
					<td>
						4
					</td>
					<td>
						 <spring:message code="hr.viewPersonalInfo.title.SCHOOL" /><!--毕业学校-->
					</td>
					<td>
						<font style="line-height: 20px;"
							color="${educationInfoList.INSTITUTION_NAME ne educationInfoPro.INSTITUTION_NAME ?'red':''}">${educationInfoList.INSTITUTION_NAME}</font>
					</td>
					<td>
						${educationInfoPro.INSTITUTION_NAME}
					</td>
				</tr>
				<tr>
					<td>
						5
					</td>
					<td>
					 <!-- 专业 --> <spring:message code="hrm.recruitManage.SUBJECT" />
					</td>
					<td>
						<font style="line-height: 20px;"
							color="${educationInfoList.SUBJECT ne educationInfoPro.SUBJECT ?'red':''}">${educationInfoList.SUBJECT}</font>
					</td>
					<td>
						${educationInfoPro.SUBJECT}
					</td>
				</tr>
				<tr>
					<td>
						6
					</td>
					<td>
						 <!-- 附件 --><spring:message code="hrm.recruitManage.ENCLOSURE" />
					</td>
					<td>
						<span id="fileN"></span>
				<c:forEach items="${fileListpro}" var="item" varStatus="i">
					<c:forEach items="${fileList}" var="items" varStatus="i">
						 <c:if test="${item.FILE_NAME ne items.FILE_NAME}">
						    <a class="a" href="/ess/infoApplyLeave/downloadFile?fileName=${item.FILE_URL }&file=${item.FILE_NAME}">${item.FILE_NAME}</a>|
						 </c:if>
					</c:forEach>
					<c:if test="${educationInfoList.APPLY_TYPE_NUM eq 3}">
						    <a class="a" href="/ess/infoApplyLeave/downloadFile?fileName=${item.FILE_URL }&file=${item.FILE_NAME}">${item.FILE_NAME}</a>|
						 </c:if>
				</c:forEach>
					</td>
					<td>
					<c:forEach items="${fileList}" var="item" varStatus="i">
					<a href="/ess/infoApplyLeave/downloadFile?fileName=${item.FILE_URL }&file=${item.FILE_NAME}">${item.FILE_NAME}</a>|
					</c:forEach>
					<c:forEach items="${fileListpro}" var="item" varStatus="i">
					<c:forEach items="${fileList}" var="items" varStatus="i">
						 <c:if test="${item.FILE_NAME ne items.FILE_NAME}">
						    <a href="/ess/infoApplyLeave/downloadFile?fileName=${item.FILE_URL }&file=${item.FILE_NAME}">${item.FILE_NAME}</a>|
						 </c:if>
					</c:forEach>
					<c:if test="${educationInfoList.APPLY_TYPE_NUM eq 3}">
						    <a href="/ess/infoApplyLeave/downloadFile?fileName=${item.FILE_URL }&file=${item.FILE_NAME}">${item.FILE_NAME}</a>|
						 </c:if>
					</c:forEach>
					<c:forEach items="${fileListpro}" var="item" varStatus="i">
					<c:if test="${educationInfoList.APPLY_TYPE_NUM eq 3}">
						    <a href="/ess/infoApplyLeave/downloadFile?fileName=${item.FILE_URL }&file=${item.FILE_NAME}">${item.FILE_NAME}</a>|
						 </c:if>
				</c:forEach>
					</td>
				</tr>
				<tr>
					<td>
						7
					</td>
					<td>
						<!-- 备注 --><spring:message code="ess.empInfo.remarks" />
					</td>
					<td>
						<font style="line-height: 20px;"
							color="${educationInfoList.REMARKS ne educationInfoPro.REMARK ?'red':''}">${educationInfoList.REMARKS}
					</td>
					<td>
						${educationInfoPro.REMARK}
					</td>
				</tr>
				</c:if>
				<input type="hidden" id="fileUrls" name="FILE_URL"
					value="${educationInfoList.FILE_URL}" />
				<input type="hidden" id="fileNames" name="FILE_NAME"
					value="${educationInfoList.FILE_NAME}" />
					<input type="hidden" id="personId" name="personId"
					value="${LoginUser.personId}" />
				<script type="text/javascript">
$("#fileN").html('');
var fileUrl = $("#fileUrls").val();
var checkVal = $("#fileNames").val();
var PERSON_ID=$("#PERSON_ID").val();
var fileResult = fileUrl.split(";");
var fileResult2 = checkVal.split(";");
var url ='';
var personId =$("#personId").val();
if (fileResult.length > 0) {
	for ( var i = 0; i < fileResult.length; i++) {
		url += "<a class='a' href='/ess/infoApplyLeave/downloadFile?fileName=/resources/temp/apply/applyleave/"
			+personId+"/"+ fileResult[i] +"&file="+fileResult2[i]+"'>" + fileResult2[i] + "</a> | ";
	}
}
$("#fileN").html(url);
</script>
			</tbody>
		</table>
		<div width="100%">
			<table class="user_table" width="100%">
				<tr>
					<td calss="td_title" width="20%" style="background: #ddd">
						<!-- 回复 --><spring:message code="hrm.approve.REPLY" />
					</td>
					<td calss="td_type" width="80%">
						<textarea name="CALLBACK" style="width: 200px; height: 80px">${educationInfoList.CALLBACK}</textarea>
					</td>
				</tr>
			</table>

			<div width="100%">
				<table class="user_table" width="100%">
					<tr>
						<td calss="td_title" width="20%" style="background: #ddd">
							<!-- 错误内容 --> <spring:message code="hrm.approve.ERROR_CONTENT" />
						</td>
						<td calss="td_type" width="80%">
							<textarea name="EARROR" style="width: 200px; height: 80px">${educationInfoList.EARROR}</textarea>
						</td>
					</tr>
				</table>
			</div>
	</form>
</div>
<%-- </c:if> --%>
