<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
$(function(){
	$('#peixunnanyidu option[value='+"${trainResultInfo.DIFFICULTY}"+']').attr('selected','selected');
	$('#peixunneirongchongshi option[value='+"${trainResultInfo.CONTENT_RICH}"+']').attr('selected','selected');
	$('#peixunshiyongxing option[value='+"${trainResultInfo.PRACTICABILITY}"+']').attr('selected','selected');
	$('#shijianshizhong option[value='+"${trainResultInfo.TIME_MODERATE}"+']').attr('selected','selected');
});

function baocunresu(){
	$('#trainResultInfo').submit();
	$('#resuweikao_'+"${BASIC_NO }").attr('style','color:red;');
	$('#resuweikaoping_'+"${BASIC_NO }").html("<spring:message code='edu.teacherEvaluate.YIKAOPING.a'/>");//<!--已考评-->已考评
	$.pdialog.closeCurrent();
}
function quxiaoresu(){
	$.pdialog.closeCurrent();
}
function jisuanjieguo(){
	var peixunnanyidu=$('#peixunnanyidu').attr('value');
	var peixunneirongchongshi=$('#peixunneirongchongshi').attr('value');
	var peixunshiyongxing=$('#peixunshiyongxing').attr('value');
	var shijianshizhong=$('#shijianshizhong').attr('value');
	var allscore=(20*parseInt(peixunnanyidu)+20*parseInt(peixunneirongchongshi)+40*parseInt(peixunshiyongxing)+20*parseInt(shijianshizhong))*0.2;
	$('#allScoreResu').html(allscore);
	$('#ALLSCORERESU').attr('value',allscore);
}

/**
 * 删除附件
 */
function deleteAttListResu(id,val,callback){
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

function trainResultImport(){
        var BASIC_NO = $('#BASIC_NO').val();
		$("#importExcelDialogTrainResult").attr('href','/pa/excelImport/importExcelData?importFunName=/importTrainResultEV?BASIC_NO='+BASIC_NO);
		$("#importExcelDialogTrainResult").attr('height', "200");
		$("#importExcelDialogTrainResult").attr('width', "300");
		$("#importExcelDialogTrainResult").click();
}
</script>
		<form method="post" id="trainResultInfo" action="/edu/traineducation/updateTrainResultInfo" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone);">
		<div class="pageContent" layoutH="10" >
		                 <ul style="padding-left: 600px;">
		                    <li>
								<a class="buttonActive" href="/edu/traineducation/trainResultImportDemoLoad?flag=load" ><span><spring:message code="pa.button.message.specialempimportmodeldown"/><!--导入模板下载--></span></a>
							</li>
							<li>
								<a class="buttonActive" href="#" onclick="trainResultImport()"><span><spring:message code="hrm.contract.Excel_import"/><!--Excel导入--></span></a>
							</li>
							<%-- 
							<li>
								<a class="buttonActive" href="#" onclick="baocunresu()">
									<span>保存</span>
								</a>
							</li>
							--%>
							<li >
								<a class="buttonActive"  href="#" onclick="quxiaoresu()">
									<span><spring:message code="hrm.empinfo.cancel"/><!--取消--></span>
								</a>
							</li>
						</ul>
				<a id="importExcelDialogTrainResult"  href="#" target="dialog" mask="true" width="300" height="200"></a>
				<input type="hidden" name="RESULT_NO" id="RESULT_NO" value="${trainResultInfo.RESULT_NO }">
				<input type="hidden" name="BASIC_NO" id="BASIC_NO" value="${BASIC_NO }">
				<table id="tearcherTable" class="user_table" width="92%" style="margin-left:32px;" border="1" cellpadding="2" cellspacing="1" >
					<tr>
				<td class="td_title" width="1%"><spring:message code="edu.systemManager.PEIXUNLEIXING.a"/><!--培训类型--></td>
				<td class="td_type" width="1%" colspan='2'>${alreadyTrainResultInfo.TRAIN_TYPE_CODE_NAME}</td>
				<td class="td_title" width="1%"><spring:message code="empsubject.subjectNm"/><!--课程名称--></td>
				<td class="td_type" width="1%" colspan='3'>${alreadyTrainResultInfo.COURSE_NAME_CODE}</td>
				</tr>
				<tr>
				<td class="td_title" width="1%"><spring:message code="edu.planManager.PEIXUNKESHI.a"/><!--培训课时--></td>
				<c:if test="${alreadyTrainResultInfo.IMPLE_CLASS_HOUR!=''}">
				<td colspan='2' class="td_type" width="1%">
					<c:if test="${alreadyTrainResultInfo.IMPLE_CLASS_UNIT=='0' }">
					<span>${alreadyTrainResultInfo.IMPLE_CLASS_HOUR }&nbsp<spring:message code="display.mutual.month"/><!--月--></span>
					</c:if>
					<c:if test="${alreadyTrainResultInfo.IMPLE_CLASS_UNIT=='1' }">
						<span>${alreadyTrainResultInfo.IMPLE_CLASS_HOUR }&nbsp<spring:message code="ar.viewsummaryparameteritem.title.day"/><!--天--></span>
					</c:if>
					<c:if test="${alreadyTrainResultInfo.IMPLE_CLASS_UNIT=='2' }">
						<span>${alreadyTrainResultInfo.IMPLE_CLASS_HOUR }&nbsp<spring:message code="ar.viewsummaryparameteritem.title.hour"/><!--小时--></span>
					</c:if>
				
				</td>
				</c:if>
				<c:if test="${alreadyTrainResultInfo.IMPLE_CLASS_HOUR==''}">
				<td colspan='2' class="td_type" width="1%"></td>
				</c:if>
				<td class="td_title" width="1%"><spring:message code="edu.trainBasicInformation.PEIXUNSHISHIQIJIAN.a"/><!--培训实施期间--></td>
				<td class="td_type" width="1%" colspan='3'>${alreadyTrainResultInfo.IMPLE_START_DATE}~${alreadyTrainResultInfo.IMPLE_END_DATE}</td>
				</tr>
				<tr>
					<td class="td_title" width="1%"><spring:message code="edu.trainResult.KECHENGNEIRONG.a"/><!--课程内容--></td>
					<td class="td_type" width="1%" colspan='6'>${alreadyTrainResultInfo.TRAIN_CONTENT}</td>
				</tr>
				<tr>
					<td class="td_title" width="1%"><spring:message code="edu.trainResult.KAOPINGRENYUAN.a"/><!--考评人员--></td>
					<td class="td_type" width="1%" colspan='6'>
				   	<a style="color:red;" href="/edu/traineducation/checkTrainResultTSTOInfoPer?BASIC_NO=${BASIC_NO}" id="chakan2" rel="chakan2" target="dialog" mask="true" width="700" height="400" >
		            <span><spring:message code="button.sys.view"/><!--查看--></span></a>
				</td>
				</tr>
				<tr>
				   <td  style="text-align:right;" class="td_title" width="10%"><spring:message code="display.emp.ben.or.benhs67"/><!--区分--></td>
				   <td  style="text-align:center;" class="td_title" width="5%"><spring:message code="edu.teacherEvaluate.FEICHANGHAO.a"/><!--非常好--></td>
				   <td  style="text-align:center;" class="td_title" width="5%"><spring:message code="edu.teacherEvaluate.BIJIAOHAO.a"/><!--比较好--></td>
				   <td  style="text-align:center;" class="td_title" width="5%"><spring:message code="edu.teacherEvaluate.YIBAN.a"/><!--一般--></td>
				   <td style="text-align:center;"  class="td_title" width="5%"><spring:message code="edu.teacherEvaluate.BIJIAOBUHAO.a"/><!--比较不好--></td>
				   <td style="text-align:center;"  class="td_title" width="5%"><spring:message code="edu.teacherEvaluate.FEICHANGBUHAO.a"/><!--非常不好--></td>
				   <td style="text-align:center;"  class="td_title" width="5%"><spring:message code="edu.teacherEvaluate.MANYIDU.a"/><!--满意度--></td>
				</tr>
				<c:forEach items="${trainResultInfoList}" var="s" varStatus="i">
				<tr>
				   <%-- <td style="text-align:center;" class="td_title" width="10%" nowrap="nowrap">${s.TYPE_NAME }</td> --%>
				   <td style="text-align:center;" class="td_title" width="10%" nowrap="nowrap">
				   <c:if test="${s.TYPE_NAME=='课程的内容是否实用' }">
					<span><spring:message code="edu.trainResult.KECHENGNEIRONGSHIFOUSHIYONG.a"/><!--月--></span>
					</c:if>
					<c:if test="${s.TYPE_NAME=='课程易掌握的程度' }">
						<span><spring:message code="edu.trainResult.KECHENGYIZHANGWODECHENGDU.a"/><!--天--></span>
					</c:if>
					<c:if test="${s.TYPE_NAME=='课程的时间长度' }">
						<span><spring:message code="edu.trainResult.KECHENGDESHIJIANCHANGDU.a"/><!--小时--></span>
					</c:if>
				    <c:if test="${s.TYPE_NAME=='课程的整体满意度' }">
						<span><spring:message code="edu.trainResult.KECHENGDEZHENGTIMANYIDU.a"/><!--小时--></span>
					</c:if>
				   </td>
				   <td style="text-align:center;" class="td_type" width="5%" >${s.REV_05 }</td>
				   <td style="text-align:center;" class="td_type" width="5%" >${s.REV_04 }</td>
				   <td style="text-align:center;" class="td_type" width="5%" >${s.REV_03 }</td>
				   <td style="text-align:center;" class="td_type" width="5%" >${s.REV_02 }</td>
				   <td style="text-align:center;" class="td_type" width="5%" >${s.REV_01 }</td>
				   <td style="text-align:center;" class="td_type" width="5%" >${s.REV_TOTAL}</td>
			    </tr>
			    </c:forEach>
				<tr>
				<td class="td_title" width="1%"><spring:message code="edu.trainResult.PEIXUNBAOGAO.a"/><!--培训报告--></td>
				<td class="td_type"  width="20%" colspan='7'>
					<a style="color:blue"  href="#" onclick="uploadAttDialog_new('trainResultInfo','/edu/traineducation/trainResultTSTOInfo?BASIC_NO=${BASIC_NO}','${BASIC_NO}','eduTrainResult','<spring:message code="js.upload.msg.saveAndUpload" />','<spring:message code="js.upload.msg.fileToUpload" />')"><span><spring:message code="hrm.empinfo.upload"/><!--上传--></span></a>
					<a style="color:blue" href="#" onclick="deleteAttListResu('trainResultInfo','/edu/traineducation/trainResultTSTOInfo?BASIC_NO=${BASIC_NO}',divAjaxDone)"><span><spring:message code="ess.empInfo.Delete"/><!--删除--></span></a>
					<c:forEach items="${fileList}" var="item" varStatus="i">
										<input type="checkbox" name="FILE_NO" value="${item.FILE_NO}"/>
											<a href="/ess/infoApplyLeave/downloadFile?fileName=${item.FILE_URL }&file=${item.FILE_NAME}" >${item.FILE_NAME}</a>
				    </c:forEach>
					</td>
				</tr>
				</table>
		</div>
		</form>
