<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript">

function validateAddResumeInfoCallback(form,callback) {
	var $form = $("#" + form);	
	if (!$form.valid()) {
		return false;
	}
	alertMsg.confirm("<spring:message code='ess.message.confirm_sava' />",//确定要保存吗？
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
	alertMsg.confirm("<spring:message code='hrm.alert.empinfo.Sure.delete' />",//确定要删除吗？
  		  	{okCall:function(){
		  	$.ajax({
  				type: form.method || 'POST',
  				url:'/hrm/empinfo/deletePassportFamily',
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
		<form id="editPassportFamily" method="post" action="/hrm/empinfo/editPassportFamily" class="pageForm required-validate" 
			onsubmit="return validateAddResumeInfoCallback(this,navTab);">
			<input TYPE="hidden" NAME="PERSON_ID" VALUE="${personInfo.PERSON_ID}" >
			<input TYPE="hidden" NAME="SINGLE_PERSON_ID" id="SINGLE_PERSON_ID" VALUE="${PERSON_ID}" >
        <input type="hidden" name="PASSPER_NO" id="PASSPER_NO" value="${personInfo.PASSPER_NO}">
        <input type="hidden" name="FLAG" id="FLAG" value="2">
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
										<!-- 关系 --><spring:message code="hr.viewRelation.title.FAM_TYPE_NAME" />
									</td>
									<td width="35%" class="td_type" colspan='3'>
										<ait:SelectSyCodeByCpnyID name="RELATION" id="RELATION" 
                                 parentNo="950" cnpyID="${defaultCpny}" selected="${personInfo.RELATION}" limit="all" />
					                    </td>
							   </tr>
							   <tr>
							  <td width="15%" class="td_title" >
										<!-- 姓名 --><spring:message code="hr.viewPersonalInfo.title.LOCAL_NAME" />
									</td>
									<td width="35%" class="td_type" >
										<input type="text" id="RELATION_NAME" name="RELATION_NAME" value="${personInfo.RELATION_NAME}">
					                    </td>
					                     <td width="15%" class="td_title" >
										<!-- 护照号码 --><spring:message code="hrm.viewpassportFamily.HUZHAOHAOMA.b" />
									</td>
									<td width="35%" class="td_type" >
										<input type="text" id="PASSPORTNUM" name="PASSPORTNUM" value="${personInfo.PASSPORTNUM}">
					                    </td>
							   </tr>
								<tr>
							    <td width="15%" class="td_title" >
										<!-- 护照有效期 --><spring:message code="hrm.empinfo.passport's_period_validity" />
									</td>
									<td width="35%" class="td_type" >
					                    <input name="PASSPORT_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" id="PASSPORT_DATE" type="text" value="${personInfo.PASSPORT_DATE }" />
					                    </td>
					                     <td width="15%" class="td_title" >
										<!-- 身份证号码 --><spring:message code="hrm.viewpassportFamily.SHENFENZHENGHAOMA.b" />
									</td>
									<td width="35%" class="td_type" >
										<input type="text" id="IDCARD_NO" name="IDCARD_NO" value="${personInfo.IDCARD_NO}">
					                    </td>
							   </tr>
							   
							   <tr>
							    <td width="15%" class="td_title" >
										<!-- 居留许可证号码 --><spring:message code="hrm.empinfo.Residence_permit_number" />
									</td>
									<td width="35%" class="td_type" >
										<input type="text" id="RESI_PERMIT_NUM" name="RESI_PERMIT_NUM" value="${personInfo.RESI_PERMIT_NUM}">
					                    </td>
					                     <td width="15%" class="td_title" >
										<!-- 居留许可证有效期 --><spring:message code="hrm.empinfo.Valid_period_residence_permit" />
									</td>
									<td width="35%" class="td_type" >
									 <input name="RESI_PERMIT_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" id="RESI_PERMIT_DATE" type="text" value="${personInfo.RESI_PERMIT_DATE }" />
					                    </td>
							   </tr>
								<tr>
									<td width="15%" class="td_title">
										<!-- 备注 --><spring:message code="hrm.empinfo.REMARK" />
									</td>
									<td width="35%" class="td_type">
										<textarea name="REMARK"  id="REMARK" style="width:300px;height:80px">${personInfo.REMARK}</textarea>
									</td>
								</tr>
							</table>
						</td>
					</tr>
							</table>
							
							<table width="100%"  border="0" cellpadding="0" cellspacing="0" class="user_table">
			<tr>
			    <c:if test="${not empty personInfo.UPDATED_BY}">
			    <td class="td_title" width="5%"><!-- 变更者 --><spring:message code="hrm.empinfo.UPDATED_BY" /></td>
					<td class="td_type"  width="25%" >
					${personInfo.UPDATED_BY }&nbsp&nbsp${personInfo.UPDATED_IP }
					</td>
					<td class="td_title" width="5%"><!-- 变更时间 --><spring:message code="hrm.empinfo.UPDATE_DATE" /></td>
					<td class="td_type"  width="25%" >
					${personInfo.UPDATE_DATE }
					</td>
					</c:if>
					<c:if test="${empty personInfo.UPDATED_BY}">
					<td class="td_title" width="5%"><!-- 变更者 --><spring:message code="hrm.empinfo.UPDATED_BY" /></td>
					<td class="td_type"  width="25%" >
					${personInfo.CREATED_BY }&nbsp&nbsp${personInfo.CREATED_IP }
					</td>
					<td class="td_title" width="5%"><!-- 变更时间 --><spring:message code="hrm.empinfo.UPDATE_DATE" /></td>
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
			<c:if test="${passper_no=='0' }">
			<div>
					<div style="font:10px;float:left;height:20px;line-height:20px;">
						<a class="w_button" href="#" onclick="uploadAttDialogInsert_new('<spring:message code="js.upload.msg.fileToUpload" />');"><span><!-- 附加文件 --><spring:message code="hrm.recruitManage.ATTACHED_FILE" /></span></a>
						<a class="w_button" href="#" onclick="deleteAttListInsert_new('<spring:message code="js.upload.msg.firstSelectDelete" />');"><span><!-- 删除 --><spring:message code="ess.empInfo.Delete" /></span></a>
					</div>
					<table id="fileTable" class="list" width="100%">
						<thead>
							<tr>
								<th width="10%">V</th>
								<th width="90%"><!-- 附件 --><spring:message code="org.title.enclosure" /></th>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
				</div>
				</c:if>
			<c:if test="${passper_no!='0' }">
			<div>
				<div style="font:10px;float:left;height:20px;line-height:20px;">
					<a class="w_button" href="#" onclick="uploadAttDialog_new('viewResumeList_viewPassportFamilyunit','/hrm/empinfo/viewSinglePassportFamily?PERSON_ID=${PERSON_ID}$PASSPER_NO=${passper_no }$flag=2','${passper_no}','hrPassportFamily','<spring:message code="js.upload.msg.saveAndUpload" />','<spring:message code="js.upload.msg.fileToUpload" />')"><span><!-- 附加文件 --><spring:message code="hrm.recruitManage.ATTACHED_FILE" /></span></a>
					<a class="w_button" href="#" onclick="deleteAttList_new('viewResumeList_viewPassportFamilyunit','/hrm/empinfo/viewSinglePassportFamily?PERSON_ID=${PERSON_ID}&PASSPER_NO=${passper_no }&flag=2',divAjaxDone,'<spring:message code="js.upload.msg.firstSelectDelete" />','<spring:message code="js.upload.msg.confirmToDelete" />')"><span><!-- 删除 --><spring:message code="ess.empInfo.Delete" /></span></a>
				</div>
				<table class="table" width="100%" layoutH="670">
					<thead>
						<tr>
							<th width="10%">V</th>
							<th width="90%"><!-- 附件 --><spring:message code="org.title.enclosure" /></th>
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
