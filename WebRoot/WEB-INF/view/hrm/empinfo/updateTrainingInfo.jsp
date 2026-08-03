<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>


<script type="text/javascript">
<!--
function validateCallbackUpdateTrainingInfo(form, callback) {


	var $form = $("#updateTrainingInfo");
	 
	if (!$form.valid()) {
		return false;
	}
   
	var checked=false;
	var ids= document.getElementsByName("TNO");
	for(var i=0;i<ids.length;i++){
		if(ids[i].checked){
			checked=true;
		}
	}
	if(!checked){
		//请选择信息再进行保存操作
		alertMsg.error('<spring:message code="hr.alert.message.viewPersonalInfo.checkBoxForChecked"/>');
		return false;
	}
	
	
	var trainingInfoListnum= document.getElementById("trainingInfoListSize").value;
	

	
	
	
	for (i=0;i<trainingInfoListnum;i++){
		
	if(document.getElementById("START_DATE_"+i)!=null){
		
			if(document.getElementById("END_DATE_"+i).value!=null&&document.getElementById("END_DATE_"+i).value!=""){
				
				var sd=document.getElementById("START_DATE_"+i).value;
				var ed=document.getElementById("END_DATE_"+i).value;
			
				var date1 = sd.replaceAll("-","");
				var date2 = ed.replaceAll("-","");
			
			if (date1 - date2 > 0) {
				alertMsg.error('<spring:message code="hr.alert.message.viewPersonalInfo.checkStartEndDate"/>');//开始日期不能晚于结束日期
				document.getElementById("START_DATE_"+i).focus();
				return false;
			}
		
		
	}
	}
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
function setCheckboxChecked(index){
  //var ckElems = document.getElementsByName(elemName);
  var tld="TNO"+index
  //if (ckElems != null && ckElems.length != null &&index >=0){ 
    //ckElems(index).checked=true;
    document.getElementById(tld).checked=true;
  //}
}
//-->
</script>


<div class="pageContent">
	<form id="updateTrainingInfo" method="post" action="/hrm/empinfo/editTrainingInfo" class="pageForm required-validate" onsubmit="return validateCallbackUpdateTrainingInfo(this, dialogAjaxDone);">

	<div class="panelBar">
		<ul class="toolBar">
			<li id="addLi">
				<span>&nbsp;</span>
			</li>
		</ul>
	</div>
	
	<input type="hidden" id="trainingInfoListSize" name="educationListSize" value="${fn:length(trainingInfoList)}" />
	<table class="table" width="103%" layoutH="150">
		<thead>
			<tr>
				<th width="10"><input type="hidden" name="PERSON_ID" class="textInput" value="${PERSON_ID }"/></th>
				<th width="80">
					<spring:message code="liang.hr.viewTraining.title.COURSE_NAME"/>
					<!--课程名-->
				</th>
				<th width="80">
					<spring:message code="liang.hr.viewTraining.title.SELECT_MUST"/>
					<!--选择/必选  -->
				</th>
				<th width="80">
					<spring:message code="liang.hr.viewTraining.title.TRAINING_DIFFERENTIATE"/>
					<!--培训区分  -->
				</th>
				<th width="80">
					<spring:message code="zxc.hr.contract.CONTRACT_START_DATE"/>
					<!--开始日期-->
				</th>
				<th width="80">
					<spring:message code="zxc.hr.contract.CONTRACT_END_DATE"/>
					<!--结束日期-->
				</th>
				<th width="80">
					<spring:message code="liang.hr.viewTraining.title.INSTITUTION_NAME"/>
					<!--培训机关-->
				</th>
				<th width="80">
					<spring:message code="liang.hr.viewTraining.title.TRAINING_METHOD" />
					<!--培训方法-->
				</th>
				<th width="80">
					<spring:message code="liang.hr.viewTraining.title.TRAINING_TIME" />
					<!--培训时间-->
				</th>
				<th width="80">
					<spring:message code="liang.hr.viewTraining.title.TRAINING_RESULT" />
					<!--培训结果-->
				</th>
				<th width="80">
					<spring:message code="liang.hr.viewTraining.title.REMARKS" />
					<!--备注-->
				</th>

				
				

			</tr>
		</thead>
		<tbody>
			<c:forEach items="${trainingInfoList}" var="item" varStatus="i">
			
				<tr target="PERSON_ID" rel="${item.TRAIN_NO}&PERSON_ID=${item.PERSON_ID }">
						<td><input type="checkbox" id="TNO${i.index}" name="TNO" value="${item.TRAIN_NO}" /></td>
						<td><input onclick="setCheckboxChecked(${i.index})" id="COURSE_NAME_${i.index}" name="COURSE_NAME_${item.TRAIN_NO}" type="text" maxlength="20" size="15"
							value="${item.COURSE_NAME}" class="required"/>
					</td>
					<td>
					<select name="MUST_CODE_${item.TRAIN_NO}" id="MUST_CODE_${i.index}" onclick="setCheckboxChecked(${i.index})" >
							<option value="">
								<spring:message code="hr.viewPersonalInfo.title.ADDED_BY_KELI" />
								<!-- 请选择 -->
							</option>
							<c:forEach items="${mustList}" var="recsource">
								<option value="${recsource.CODE_NO}" <c:if test="${recsource.CODE_NO==item.MUST_CODE}">selected</c:if> >
									${recsource.CODENAME}
								</option>
							</c:forEach>
						</select>
					</td>
					<td>
					<select name="TRAINING_DIFFERENTIATE_${item.TRAIN_NO}" id="TRAINING_DIFFERENTIATE_${i.index}" onclick="setCheckboxChecked(${i.index})" >
							<option value="">
								<spring:message code="hr.viewPersonalInfo.title.ADDED_BY_KELI" />
								<!-- 请选择 -->
							</option>
							<c:forEach items="${differentiateList}" var="recsource">
								<option value="${recsource.CODE_NO}" <c:if test="${recsource.CODE_NO==item.TRAINING_DIFFERENTIATE_CODE}">selected</c:if> >
									${recsource.CODENAME}
								</option>
							</c:forEach>
						</select>
					</td>
					<td>
						<input id="START_DATE_${i.index}"  name="START_DATE_${item.TRAIN_NO}" type="text"  class="date required" value="${item.START_DATE}" readonly="true" format="yyyy-MM-dd" yearstart="-50" yearend="5" onClick="setdate(this);setCheckboxChecked(${i.index})"/>
					</td>
					<td>
						<input id="END_DATE_${i.index}"  name="END_DATE_${item.TRAIN_NO}" type="text"  class="date required" value="${item.END_DATE}" readonly="true" format="yyyy-MM-dd" yearstart="-50" yearend="5" onClick="setdate(this);setCheckboxChecked(${i.index})"/>	
					</td>
					<td><input id="INSTITUTION_NAME_${i.index}" name="INSTITUTION_NAME_${item.TRAIN_NO}" type="text" class="textInput" maxlength="60" size="10"
							value="${item.INSTITUTION_NAME}"  />
					</td>

					<td>
					<select name="TRAINING_METHOD_${item.TRAIN_NO}" id="TRAINING_METHOD_${i.index}" onclick="setCheckboxChecked(${i.index})" >
							<option value="">
								<spring:message code="hr.viewPersonalInfo.title.ADDED_BY_KELI" />
								<!-- 请选择 -->
							</option>
							<c:forEach items="${trainingMethodList}" var="recsource">
								<option value="${recsource.CODE_NO}" <c:if test="${recsource.CODE_NO==item.TRAINING_METHOD}">selected</c:if> >
									${recsource.CODENAME}
								</option>
							</c:forEach>
						</select>
					</td>
					<td>
						<input onclick="setCheckboxChecked(${i.index})" id="TRAINING_TIME_${i.index}" name="TRAINING_TIME_${item.TRAIN_NO}" type="text" class="textInput" value="${item.TRAINING_TIME}"  />
						
					</td>
					<td>
						<input onclick="setCheckboxChecked(${i.index})" id="TRAINING_RESULT_${i.index}" name="TRAINING_RESULT_${item.TRAIN_NO}" type="text" class="textInput" value="${item.TRAINING_RESULT}"  />
						
					</td>
					<td>
						<input onclick="setCheckboxChecked(${i.index})" id="REMARKS_${i.index}" name="REMARKS_${item.TRAIN_NO}" type="text" class="textInput" value="${item.REMARKS}"  />
						
					</td>

				</tr>
			
			</c:forEach>
			
		</tbody>
	</table>
	
	
	
	
	<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit"><spring:message code="public.title.submit"/><!-- 保存 --></button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close"><spring:message code="public.title.cancle"/><!-- 取消 --></button></div></div>
				</li>
			</ul>
	</div>
	</form>
</div>