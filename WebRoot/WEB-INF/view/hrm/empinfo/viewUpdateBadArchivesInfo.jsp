<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function uploadifySuccess_Jiangcheng(file, data, response){
  //获取后台返回到前台的文件名，添加到隐藏域,多文件用";"号隔开
  var files = $("#fileNmae").html();
  var fileUrl = $("#fileUrl").val();
  var fileName = $("#fileName").val();
  var fileResult = data.split(";");
  //第一个文件
  if(files=="" || files==null){
    files = fileResult[0];
    fileName = fileResult[0];
    fileUrl = fileResult[1];
  }else{
    files+=";"+fileResult[0];
    fileName+=";"+fileResult[0];
    fileUrl+=";"+fileResult[1];
  }
  $("#fileNmae").html(files);
  $("#fileUrl").val(fileUrl);
  $("#fileName").val(fileName);
}
</script>
<div class="pageContent">
	<form method="post" action="/hrm/empinfo/addOrUpdateBadArchivesInfo"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this,dialogAjaxDone);">
		<div class="formBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">
								<!-- 保存 --><spring:message code="public.title.submit" />
							</button>
						</div>
					</div>
				</li>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" class="close">
								<!-- 取消 --><spring:message code="public.title.cancle" />
							</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
		<div class="pageFormContent nowrap" layoutH="56">
			<dl>
				<dt>社号/姓名:</dt>
				<dd>
					${archive.LOCAL_NAME}[${archive.EMPID}]
				</dd>
			</dl>
			<dl>
				<dt><!--发生日期--><spring:message code="hr.viewBadArchives.title.HAPPEN_DATE" />:</dt>
				<dd>
					<input type="text" id="HAPPEN_DATE0" name="HAPPEN_DATE0"
						class="required" readonly="true" format="yyyy-MM-dd"
						yearstart="-50" yearend="5" onClick="setdate(this);"
						value="${archive.HAPPEN_DATE}" />
				</dd>
			</dl>
			<dl>
				<dt><!--类型--><spring:message code="hr.viewBadArchives.title.ARCHIVES_TYPE" />:</dt>
				<dd>
					<select name="ARCHIVES_TYPE0" id="ARCHIVES_TYPE0">
						<c:forEach items="${codeList}" var="recsource">
							<option value="${recsource.CODE_NO}"
								<c:if test="${recsource.CODE_NO eq archive.ARCHIVES_TYPE}">selected</c:if>>
								${recsource.CODENAME}
							</option>
						</c:forEach>
					</select>
				</dd>
			</dl>
			<dl>
				<dt><!--详细描述--><spring:message code="hr.viewBadArchives.title.DETAIL_DESCRIPT" />:</dt>
				<dd>
					<input type="text" name="DETAIL_DESCRIPT0" class="textInput"
						maxlength="160" size="60" value="${archive.DETAIL_DESCRIPT}" />
				</dd>
			</dl>
			<dl>
				<dt><!--备注--><spring:message code="hr.viewBadArchives.title.REMARK" />:</dt>
				<dd>
					<input type="text" name="REMARK0" class="textInput"	value="${archive.REMARK}" />
					<input type="hidden" name="ID" class="textInput" value="${archive.ID}" />
					<input type="hidden" name="count" class="textInput" value="1" />
				</dd>
			</dl>
			<dl>
			<dt width="20%" class="td_title">
										附件上传
									</dt>
									<dd width="30%" class="td_type">
										 <input id="testFileInput_Jiangcheng" type="file" name="file" 
												uploaderOption="{
													swf:'/resources/js/uploadify/scripts/uploadify.swf',
													uploader:'/ess/infoApplyLeave/uploadBatch?PERSON_ID=${personInfo.PERSON_ID}',
													formData:{ajax:1},
													queueID:'fileQueue_Jiangcheng',
													buttonText:'请选择',
													height:25,
													width:50,
													auto:false,
													onUploadSuccess:uploadifySuccess_Jiangcheng,
													removeTimeout:1
												}"
											/>
										  <span id="fileNmae">${archive.FILE_NAME}</span>
										  <div id="fileQueue_Jiangcheng" class="fileQueue"></div>
										  <input type="hidden" id="fileUrl" name="fileUrl" value="${archive.FILE_URL}"/>
										  <input type="hidden" id="fileName" name="fileName" value="${archive.FILE_NAME}"/>
										  
											<div class="buttonActive">
												<div class="buttonContent"><!--保存-->
													<button type="button" onclick="$('#testFileInput_Jiangcheng').uploadify('upload', '*');return false;">
														上传
													</button>
												</div>
											</div>
											<div class="buttonActive">
												<div class="buttonContent"><!--提交-->
													<button type="button" onclick="$('#testFileInput_Jiangcheng').uploadify('cancel', '*');return false;">
														取消
													</button>
												</div>
											</div>
									</dd>  
			</dl>
		</div>
	</form>
</div>