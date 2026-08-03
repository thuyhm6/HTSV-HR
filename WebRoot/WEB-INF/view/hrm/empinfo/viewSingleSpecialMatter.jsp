<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript">

function validateAddResumeInfoCallback(form,callback) {
	var $form = $("#" + form);	
	if (!$form.valid()) {
		return false;
	}
	alertMsg.confirm("<spring:message code="hrm.empinfo.SAVE_CONFIRM"/>",//确定要保存吗？
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

function validateDeleteResumeInfoCallback(form,callback) {
	var $form = $("#" + form);	
	alertMsg.confirm("<spring:message code="hrm.alert.empinfo.Sure.delete"/>",//确定要删除吗？
  		  	{okCall:function(){
		  	$.ajax({
  				type: form.method || 'POST',
  				url:'/hrm/empinfo/deleteSpecialMatter',
  				data:$form.serializeArray(),
  				dataType:"json",
  				cache: false,
  				success: callback || DWZ.ajaxDone,
  				error: DWZ.ajaxError
  			});
  	}});
	return false;
}

//附件上传
function uploadifySuccess_Recognitionfile(file, data, response){
	  //获取后台返回到前台的文件名，添加到隐藏域,多文件用";"号隔开
	  var files = $("#fileNmae",navTab.getCurrentPanel()).html();
	  var fileUrl = $("#fileUrl",navTab.getCurrentPanel()).val();
	  var fileName = $("#fileName",navTab.getCurrentPanel()).val();
	  var fileResult = data.split(";");
	  //第一个文件
	  if(files==""){
	    files = fileResult[0];
	    fileName = fileResult[0];
	    fileUrl = fileResult[1];
	  }else{
	    files+=";"+fileResult[0];
	    fileName+=";"+fileResult[0];
	    fileUrl+=";"+fileResult[1];
	  }
	  $("#fileNmae",navTab.getCurrentPanel()).html(files);
	  $("#fileUrl",navTab.getCurrentPanel()).val(fileUrl);
	  $("#fileName",navTab.getCurrentPanel()).val(fileName);
}
</script>
	<div>
		<form id="editSpecialMatter" method="post" action="/hrm/empinfo/editSpecialMatter" class="pageForm required-validate" 
			onsubmit="return validateAddResumeInfoCallback(this,navTab);">
			<input TYPE="hidden" NAME="PERSON_ID" VALUE="${personInfo.PERSON_ID}" >
			<input TYPE="hidden" NAME="SINGLE_PERSON_ID" id="SINGLE_PERSON_ID" VALUE="${PERSON_ID}" >
        <input type="hidden" name="SPECIAL_NO" id="SPECIAL_NO" value="${personInfo.SPECIAL_NO}">
			<div>
				<table class="user_table" width="100%" border="1" cellpadding="2" cellspacing="1">
					<tr>
						<td>
							<table  class="user_table" width="100%">
								<tr>
						<td>
							<table  class="user_table" width="100%" >
								<tr>
									<td width="15%" class="td_title" >
										<spring:message code="inct.salesman.classify"/><!-- 信息区分 -->
									</td>
									<td width="35%" class="td_type" colspan='3'>
										<ait:SelectSyCodeByCpnyID name="INFOR_DIS_CODE" id="INFOR_DIS_CODE" parentNo="14014361"
											cnpyID="${defaultCpny}" selected="${personInfo.INFOR_DIS_CODE}" limit="all" />
					                </td>
								</tr>
								<tr>
									<td width="15%" class="td_title" >
										<spring:message code="ar.viewcycle.title.kaishiri"/><!-- 开始日期 --></th>
									</td>
					                <td class="td_type"  width="25%" >
										<input name="START_DATE"  id="START_DATE" type="text" class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})" value="${personInfo.START_DATE}" />
									</td>
					                <td width="15%" class="td_title" >
										<spring:message code="ar.viewcycle.title.jieshuri"/><!-- 结束日期 -->
									</td>
					                <td class="td_type"  width="25%" >
										<input name="END_DATE"  id="END_DATE" type="text" class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})" value="${personInfo.END_DATE}" />
									</td>
							   </tr>
								<tr>
									<td width="15%" class="td_title">
										<spring:message code="hrm.contract.content"/><!-- 内容 -->
									</td>
									<td width="35%" class="td_type" colspan="3">
										<textarea name="SPECIAL_CONTENT"  id="SPECIAL_CONTENT" style="width:500px;height:80px">${personInfo.SPECIAL_CONTENT}</textarea>
									</td>
								</tr>
							</table>
						</td>
					</tr>
							</table>
							
							<table width="100%"  border="0" cellpadding="0" cellspacing="0" class="user_table">
			<tr>
			    <c:if test="${not empty personInfo.UPDATED_BY}">
			    <td class="td_title" width="5%"><spring:message code="hrm.empinfo.UPDATED_BY"/><!-- 变更者 --></td>
					<td class="td_type"  width="25%" >
					${personInfo.UPDATED_BY }&nbsp&nbsp${personInfo.UPDATED_IP }
					</td>
					<td class="td_title" width="5%"><spring:message code="hrm.empinfo.UPDATE_DATE"/><!-- 变更时间 --></td>
					<td class="td_type"  width="25%" >
					${personInfo.UPDATE_DATE }
					</td>
					</c:if>
					<c:if test="${empty personInfo.UPDATED_BY}">
					<td class="td_title" width="5%"><spring:message code="hrm.empinfo.UPDATED_BY"/><!-- 变更者 --></td>
					<td class="td_type"  width="25%" >
					${personInfo.CREATED_BY }&nbsp&nbsp${personInfo.CREATED_IP }
					</td>
					<td class="td_title" width="5%"><spring:message code="hrm.empinfo.UPDATE_DATE"/><!-- 变更时间 --></td>
					<td class="td_type"  width="25%" >
					${personInfo.CREATE_DATE }
					</td>
					</c:if>
								</tr>	
							</table>	
						</td>
					</tr>
				</table>	
						</td>
					</tr>
				</table>
			</div>
			<c:if test="${special_no=='0' }">
			<div>
					<div style="font:10px;float:left;height:20px;line-height:20px;">
						<a class="w_button" href="#" onclick="uploadAttDialogInsert_new('<spring:message code="js.upload.msg.fileToUpload" />');">
						<span><spring:message code="hrm.recruitManage.ATTACHED_FILE"/><!-- 附加文件 --></span></a>
						<a class="w_button" href="#" onclick="deleteAttListInsert_new('<spring:message code="js.upload.msg.firstSelectDelete" />');">
						<span><spring:message code="button.delete"/><!-- 删除 --></span></a>
					</div>
					<table id="fileTable" class="list" width="100%">
						<thead>
							<tr>
								<th width="10%">V</th>
								<th width="90%"><spring:message code="hrm.recruitManage.ENCLOSURE"/><!-- 附件 --></th>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
				</div>
				</c:if>
			<c:if test="${special_no!='0' }">
			<div>
				<div style="font:10px;float:left;height:20px;line-height:20px;">
				<a class="w_button" href="#" onclick="uploadAttDialog_new('viewResumeList_viewSpecialMatterunit','/hrm/empinfo/viewSingleSpecialMatter?PERSON_ID=${PERSON_ID}$SPECIAL_NO=${special_no }','${special_no}','hrSpecialMatter','<spring:message code="js.upload.msg.saveAndUpload" />','<spring:message code="js.upload.msg.fileToUpload" />')">
				<span><spring:message code="hrm.recruitManage.ATTACHED_FILE"/><!-- 附加文件 --></span></a>
				<a class="w_button" href="#" onclick="deleteAttList_new('viewResumeList_viewSpecialMatterunit','/hrm/empinfo/viewSingleSpecialMatter?PERSON_ID=${PERSON_ID}&SPECIAL_NO=${special_no }',divAjaxDone,'<spring:message code="js.upload.msg.firstSelectDelete" />','<spring:message code="js.upload.msg.confirmToDelete" />')">
				<span><spring:message code="button.delete"/><!-- 删除 --></span></a>
				</div>
				<table class="table" width="100%" layoutH="670">
					<thead>
						<tr>
							<th width="10%">V</th>
							<th width="90%"><spring:message code="hrm.recruitManage.ENCLOSURE"/><!-- 附件 --></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${personInfo.fileList}" var="item" varStatus="i">
							<tr>
								<td class='td_center'><input type="checkbox" name="FILE_NO" value="${item.FILE_NO}"/></td>
								<td><a href="/ess/infoApplyLeave/downloadFile?fileName=${item.FILE_URL }&file=${item.FILE_NAME}" >${item.FILE_NAME}</a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			</c:if>
	  	</form>	
	</div>
