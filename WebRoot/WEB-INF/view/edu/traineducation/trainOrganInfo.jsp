<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
/**
 * 删除附件
 */
function deleteAttListOrg(id,val,callback){
	var fileNosStr="";
	var flag=false;
	$("input[name='FILE_NO']").each(function(){
		if($(this).attr("checked") == "checked"){
			fileNosStr = fileNosStr + "'" + $(this).val() + "'" + ",";
			flag = true;
		}
	});
	fileNosStr = fileNosStr + "'empty'";
	if(flag == false){
		alertMsg.info("<spring:message code='edu.planManager.QINGXIANXUANZEYAOSHANCHUDEFUJIAN.a'/>");//请先选择要删除的附件
		return false;
	}

	alertMsg.confirm("<spring:message code='hrm.alert.empinfo.Sure.delete'/>",//确定要删除吗？
  		  	{okCall:function(){
		  	$.ajax({
  				type: 'POST',
				url: '/org/orgManage/deleteFile',
  				data:{fileNos:fileNosStr,typeId : id,typeValue : val },
  				dataType:"json",
  				cache: false,
  				success: callback,
  				error: DWZ.ajaxError
  			});
  	}});
	return false;
}
</script>
<div class="pageContent" layoutH="10" id=trainOrganInfo">
	<form method="post"  action="/edu/traineducation/updateTrainOrgan" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone);">
		<div class="pageFormContent nowrap">
		<input type="hidden" name="ORGAN_NO" id="ORGAN_NO" value="${trainOrganInfo.ORGAN_NO }">
		<table  class="user_table" width="100%" border="1" cellpadding="2" cellspacing="1" >
		<tr>
		<td class="td_title" width="1%"><spring:message code="edu.trainOrgan.PEIXUNJIGOUMINGCHENG.a"/><!--培训机构名称--></td>
		<td class="td_type"  width="20%">
		<input type="text" class="required" name="ORGAN_NAME" id="ORGAN_NAME" value="${trainOrganInfo.ORGAN_NAME }">
		</td>
		</tr>
		
		<tr>
		<td class="td_title" width="1%"><spring:message code="edu.trainOrgan.LIANXIREN.a"/> <!--联系人
			--></td>
		<td class="td_type"  width="20%">
		<input type="text"  name="LINKMAN" id="LINKMAN" value="${trainOrganInfo.LINKMAN }">
		</td>
		</tr>
		
		<tr>
		<td class="td_title" width="1%"><spring:message code="hr.viewRelation.title.FAM_ADDRESS"/><!--地址
			--></td>
		<td class="td_type"  width="20%">
		<input type="text"  name="ADDRESS" id="ADDRESS" value="${trainOrganInfo.ADDRESS }">
		</td>
		</tr>
		
		<tr>
		<td class="td_title" width="1%"><spring:message code="hr.viewHire.title.OFFICE_PHONE"/><!--办公室电话
			--></td>
		<td class="td_type"  width="20%">
		<input type="text"  name="OFFICE_PHONE" id="OFFICE_PHONE" value="${trainOrganInfo.OFFICE_PHONE }">
		</td>
		</tr>
		
		<tr>
		<td class="td_title" width="1%"><spring:message code="hrm.empinfo.MOBILE_TELEPHONE"/><!--移动电话
			--></td>
		<td class="td_type"  width="20%">
		<input type="text"  name="CELLPHONE" id="CELLPHONE" value="${trainOrganInfo.CELLPHONE }">
		</td>
		</tr>
		
		<tr>
		<td class="td_title" width="1%"><spring:message code="pa.ins.alert.message.exportdata.netAddress"/><!--网址
			--></td>
		<td class="td_type"  width="20%">
		<input type="text"  name="URL_NET" id="URL_NET" value="${trainOrganInfo.URL_NET }">
		</td>
		</tr>
		
		<tr>
		<td class="td_title" width="1%"><spring:message code="edu.trainOrgan.ZHUYINGLINGYU.a"/><!--主营领域
			--></td>
		<td class="td_type"  width="20%">
		<input type="text"  name="MAIN_FIELD" id="MAIN_FIELD" value="${trainOrganInfo.MAIN_FIELD }">
		</td>
		</tr>
		
		<tr>
		<td class="td_title" width="1%"><spring:message code="edu.trainOrgan.HEZUOQINGKUANGJIPINGJIA.a"/><!--合作情况及评价
		--></td>
		<td class="td_type"  width="20%">
		<input type="text"  name="WORK_TOGETHER" id="WORK_TOGETHER" value="${trainOrganInfo.WORK_TOGETHER }">
		</td>
		</tr>
		
		<tr>
		<td class="td_title" width="1%"><spring:message code="edu.trainOrgan.JIGOUJIANJIE.a"/><!--机构简介
		--></td>
		<td class="td_type"  width="20%">
		<input type="text"  name="ORGAN_ABSTRACT" id="ORGAN_ABSTRACT" value="${trainOrganInfo.ORGAN_ABSTRACT }">
		</td>
		</tr>
		
		<tr>
		<td class="td_title" width="1%"><spring:message code="edu.trainOrgan.HEZUOXIEYI.a"/><!--合作协议--></td>
		<%-- <td class="td_type"  width="20%">
		<a class="w_button" href="#" onclick="uploadAttDialog_new('trainOrganInfo','/edu/traineducation/trainOrganInfo?PERSON_ID=${PERSON_ID}$ORGAN_NO=${trainOrganInfo.ORGAN_NO }','${trainOrganInfo.ORGAN_NO }','eduTrainOrgan','<spring:message code="js.upload.msg.saveAndUpload" />','<spring:message code="js.upload.msg.fileToUpload" />')"><span><spring:message code="ess.empInfo.insert"/><!--添加
		--></span></a>
		<a class="w_button" href="#" onclick="deleteAttListOrg('trainOrganInfo','/edu/traineducation/trainOrganInfo?PERSON_ID=${PERSON_ID}&PLAN_NO=${trainOrganInfo.ORGAN_NO }',divAjaxDone)"><span><spring:message code="ess.empInfo.Delete"/><!--删除
		--></span></a>
		<c:forEach items="${trainOrganInfo.fileList}" var="item" varStatus="i">
							<input type="checkbox" name="FILE_NO" value="${item.FILE_NO}"/>
								<a href="/ess/infoApplyLeave/downloadFile?fileName=${item.FILE_URL }&file=${item.FILE_NAME}" >${item.FILE_NAME}</a>
	    </c:forEach>
		</td> --%>
		<td class="td_type" width="20%">
						<a class="w_button" href="#" onclick="uploadAttDialogInsert_new('<spring:message code="js.upload.msg.fileToUpload" />');">
						<span><spring:message code="button.add"/><!--添加--></span></a>
						<%-- <a class="w_button" href="#" onclick="deletetrainOrganFile();"><span><spring:message code="button.delete"/><!--删除--></span></a> --%>
						<a class="w_button" href="#" onclick="deleteAttListOrg('trainOrganInfo','/edu/traineducation/trainOrganInfo?PERSON_ID=${PERSON_ID}&ORGAN_NO=${trainOrganInfo.ORGAN_NO }',divAjaxDone)"><span><spring:message code="ess.empInfo.Delete"/><!--删除--></span></a>
			<table id="fileTable" class="list" width="100%">
				<thead>
				</thead>
				<tbody>
				<c:forEach items="${trainOrganInfo.fileList}" var="item" varStatus="i">
						<tr>
							<td class='td_center'>
							<input type="checkbox" name="FILE_NO" value="${item.FILE_NO}"/>
							<input type="hidden" name="fileUrl" value="${item.FILE_PATH }">
							<input type="hidden" name="fileName" value="${item.FILE_NAME }">
							</td>
							<td><a href="/ess/infoApplyLeave/downloadFile?fileName=${item.FILE_URL }&file=${item.FILE_NAME}" >${item.FILE_NAME}</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
					</td>
		</tr>
		
		</table>
		
		</div>
		
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit"><!-- 提交 --><spring:message code="public.title.submit"/></button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close"><!-- 取消 --><spring:message code="public.title.cancle"/></button></div></div></li>
			</ul>
		</div>
	</form>
</div>
