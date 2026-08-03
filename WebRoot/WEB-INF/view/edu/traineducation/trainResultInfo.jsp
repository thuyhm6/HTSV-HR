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

</script>
		<form method="post" id="trainResultInfo" action="/edu/traineducation/updateTrainResultInfo" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone);">
		<div class="pageContent" layoutH="10" >
		                 <ul style="padding-left: 600px;">
							<li>
								<a class="buttonActive" href="#" onclick="baocunresu()">
									<span><spring:message code="org.title.SAVE"/><!--保存--></span>
								</a>
							</li>
							<li >
								<a class="buttonActive"  href="#" onclick="quxiaoresu()">
									<span><spring:message code="hrm.empinfo.cancel"/><!--取消--></span>
								</a>
							</li>
						</ul>
				<input type="hidden" name="RESULT_NO" id="RESULT_NO" value="${trainResultInfo.RESULT_NO }">
				<input type="hidden" name="BASIC_NO" id="BASIC_NO" value="${BASIC_NO }">
				<table id="tearcherTable" class="user_table" width="92%" style="margin-left:32px;" border="1" cellpadding="2" cellspacing="1" >
				<tr>
				   <td class="td_title" width="1%"><spring:message code="org.title.EMPID"/><!--社号--></td>
				   <td class="td_title" width="1%"><spring:message code="edu.trainResult.PINGJIAZHE.a"/><!--评价者--></td>
				   <td class="td_title" width="1%"><spring:message code="edu.trainResult.KECHENGZHENGTIMANYIDUERSHI.a"/><!--课程整体满意程度(20%)--></td>
				   <td class="td_title" width="1%"><spring:message code="edu.trainResult.KECHENGYIZHANGWOCHENGDUERSHI.a"/><!--课程易掌握程度(20%)--></td>
				   <td class="td_title" width="1%"><spring:message code="edu.trainResult.KECHENGSHIJIANCHANGDUERSHI.a"/><!--课程时间长度(20%)--></td>
				   <td class="td_title" width="1%"><spring:message code="edu.trainResult.PEIXUNSHIYONGXING.a"/><!--培训实用性(40%)--></td>
				   <td class="td_title" width="1%"><spring:message code="ess.viewpersonalpainfo.heji"/><!--合计--></td>
				   <td class="td_title" width="1%"><spring:message code="hr.viewSuggestion.title.Suggestion"/><!--意见--></td>
				</tr>
				<tr>
				   <td class="td_type" width="1%">${trainResultInfo.STU_EMPID }</td>
				   <td class="td_type" width="1%">${trainResultInfo.STU_LOCAL_NAME }</td>
				   <td class="td_type" width="1%">
				   <select name="DIFFICULTY" id="peixunnanyidu" onchange="jisuanjieguo()">
					<option value="0"><spring:message code="pa.insurance.title.pleaseChoose"/><!--请选择--></option>
					<option value="5">5</option>
					<option value="4">4</option>
					<option value="3">3</option>
					<option value="2">2</option>
					<option value="1">1</option>
					</select>
				   </td>
				   <td class="td_type" width="1%">
				   <select name="CONTENT_RICH" id="peixunneirongchongshi" onchange="jisuanjieguo()">
					<option value="0"><spring:message code="pa.insurance.title.pleaseChoose"/><!--请选择--></option>
					<option value="5">5</option>
					<option value="4">4</option>
					<option value="3">3</option>
					<option value="2">2</option>
					<option value="1">1</option>
					</select>
				   </td>
				   <td class="td_type" width="1%">
				   <select name="TIME_MODERATE" id="shijianshizhong" onchange="jisuanjieguo()">
					<option value="0"><spring:message code="pa.insurance.title.pleaseChoose"/><!--请选择--></option>
					<option value="5">5</option>
					<option value="4">4</option>
					<option value="3">3</option>
					<option value="2">2</option>
					<option value="1">1</option>
					</select>
				   </td>
				   <td class="td_type" width="1%">
				   <select name="PRACTICABILITY" id="peixunshiyongxing" onchange="jisuanjieguo()">
					<option value="0"><spring:message code="pa.insurance.title.pleaseChoose"/><!--请选择--></option>
					<option value="5">5</option>
					<option value="4">4</option>
					<option value="3">3</option>
					<option value="2">2</option>
					<option value="1">1</option>
					</select>
				   </td>
				   <td class="td_type" width="1%">
				   <span id="allScoreResu">${trainResultInfo.ALLSCORE }</span>
				   <input type="hidden" name="ALLSCORE" id="ALLSCORERESU" value="${trainResultInfo.ALLSCORE }">
				   </td>
				   <td class="td_type" width="1%">
				   <input type="text" name="OTHER_ADVISE" id="OTHER_ADVISE" value="${trainResultInfo.OTHER_ADVISE }">
				   </td>
			    </tr>
				<tr>
				<td class="td_title" width="1%"><spring:message code="edu.trainResult.PEIXUNBAOGAO.a"/><!--培训报告--></td>
				<td class="td_type"  width="20%" colspan='7'>
					<a style="color:blue"  href="#" onclick="uploadAttDialog_new('trainResultInfo','/edu/traineducation/trainResultInfo?PERSON_ID=${PERSON_ID}$RESULT_NO=${trainResultInfo.RESULT_NO }','${trainResultInfo.RESULT_NO }','eduTrainResult','<spring:message code="js.upload.msg.saveAndUpload" />','<spring:message code="js.upload.msg.fileToUpload" />')"><span><spring:message code="hrm.empinfo.upload"/><!--上传--></span></a>
					<a style="color:blue" href="#" onclick="deleteAttListResu('trainResultInfo','/edu/traineducation/trainResultInfo?PERSON_ID=${PERSON_ID}&RESULT_NO=${trainResultInfo.RESULT_NO }',divAjaxDone)"><span><spring:message code="ess.empInfo.Delete"/><!--删除--></span></a>
					<c:forEach items="${trainResultInfo.fileList}" var="item" varStatus="i">
										<input type="checkbox" name="FILE_NO" value="${item.FILE_NO}"/>
											<a href="/ess/infoApplyLeave/downloadFile?fileName=${item.FILE_URL }&file=${item.FILE_NAME}" >${item.FILE_NAME}</a>
				    </c:forEach>
				    
					</td>
				
				
				</tr>
				</table>
		</div>
		</form>
