<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function addInsuranceInputItemDataApply(form, callback) {
	var $form = $("#addInsuranceInputItemDataApplyView");
	if (!$form.valid()) {
		return false;
	}
	var returnValue=document.getElementById("RETURN_VALUE").value;
	if(returnValue!=null&&returnValue!=""){
		//alert(returnValue>0);
		if(!(returnValue>0)){
			alertMsg.error("数值需要大于0!");
			return false;
		}
	}else{
		alertMsg.error("数值不能为空!");
    	return false;
	}
	
 	var startMonth = document.getElementById("START_MONTH").value;
 	if(startMonth!=null&&startMonth!=""){
		if (!/^(?:19[7-9]\d|2\d{3,3})(?:0[1-9]|1[0-2])$/.test(startMonth)){
	         alertMsg.error('<spring:message code="alert.message.pa.insurance.startMonthIsNotCorrect"/>');
	         return false;
	    }
    }else{
    	alertMsg.error("开始月不能为空!");
    	return false;
    }
// 	var endMonth = document.getElementById("END_MONTH").value;
// 	if(endMonth!=null && endMonth !=""){
// 		if (!/^(?:19[7-9]\d|2\d{3,3})(?:0[1-9]|1[0-2])$/.test(endMonth)){
// 	        alertMsg.error('<spring:message code="alert.message.pa.insurance.endMonthIsNotCorrect"/>');
// 	        return false;
// 	    }
// 		如果结束月份比开始月早， 请重新填写结束月！
// 	    if(endMonth < startMonth){
// 	    	alertMsg.error('<spring:message code="alert.message.pa.insurance.endMonthIsBeforeStartDate"/>');
// 	        return false;
// 	    }
// 	}
    
	$.ajax({
		type: form.method || 'POST',
		url:$form.attr("action"),
		data:$form.serializeArray(),
		dataType:"json",
		cache: false,
		success: callback || DWZ.ajaxDone,
		error: DWZ.ajaxError
	});
	
	return false;
}
//上传附件的按钮
function shangchuan(){
	
	$("#addInsuranceInputItemDataApplyView").attr("action","/pa/insurance/upload");
	$("#addInsuranceInputItemDataApplyView").attr("enctype","multipart/form-data");
	$("#addInsuranceInputItemDataApplyView").attr("target","callbackframe");
	
	$("#addInsuranceInputItemDataApplyView").attr("class","pageForm");
	$("#addInsuranceInputItemDataApplyView").attr("onsubmit","return iframeCallback_photochange(this,dialogAjaxDone);");
	
}
//上传附件的按钮
function baocun(){
	$("#addInsuranceInputItemDataApplyView").attr("action","/pa/insurance/addInsuranceInputItemDataApply?seach_PARAM_NO=${PARAM_NO}&seach_CPNY_ID=${CPNY_ID}");
	$("#addInsuranceInputItemDataApplyView").attr("onsubmit","return addInsuranceInputItemDataApply(this, dialogAjaxDone);");
	
}

function iframeCallback_photochange(form, callback){
	var filePath = document.getElementById("file1").value;
	if(document.getElementById("file1").value == ''){
		alertMsg.error('请先选择上传路径!');
		return false;
	}
	$(document.getElementById("shagnchuanfujian")).hide();
	var $form = $(form), $iframe = $("#callbackframe");
	if(!$form.valid()) {return false;}

	if ($iframe.size() == 0) {
		$iframe = $("<iframe id='callbackframe' name='callbackframe' src='about:blank' style='display:none'></iframe>");
		$form.appendTo($iframe);
	}
	if(!form.ajax) {
		$form.append('<input type="hidden" name="ajax" value="1" />');
	}
	
	//form.target = "callbackframe";
	
	_iframeResponse($iframe[0], callback || DWZ.ajaxDone);
}
</script>

<div class="pageContent">
	<form id="addInsuranceInputItemDataApplyView" name="addInsuranceInputItemDataApplyView" method="post" 
		action="/pa/insurance/addInsuranceInputItemDataApply?seach_PARAM_NO=${PARAM_NO}&seach_CPNY_ID=${CPNY_ID}"
		class="pageForm required-validate" onsubmit="return addInsuranceInputItemDataApply(this, dialogAjaxDone);">
		<div class="pageFormContent nowrap" layoutH="55">
			<c:if test="${insuranceInputItemParamInfo.DISTINCT_FIELD eq 'PERSON_ID'}">
				<dl>
					<dt><!--公司-->
						<spring:message code="pa.insurance.title.company"/>
					</dt>
					<dd>
						<select name="CPNY_ID" id="CPNY_ID" style="width: 180px; position: static; visibility: inherit;">
							<c:forEach items="${cpnyList}" var="cpny">
								<option value="${cpny.CPNY_ID}"
									<c:if test="${CPNY_ID eq cpny.CPNY_ID}">selected</c:if> disabled="true">
									${cpny.CONTENT}
								</option>
							</c:forEach>
						</select>
					</dd>
				</dl>
				<dl>
					<dt><!--工号-->
						<spring:message code="public.title.empId"/>
					</dt>
					<dd>
						<input id="personId" name="dwz.person.personId" value="" type="hidden" lookupGroup="person" />
						<input id="empId" name="dwz.person.empId" value="" type="text"
							lookupGroup="person" chass="textInput required" readonly="true" />
						<a class="btnLook" href="/pa/insurance/viewAddInsurancePersonalDataList?pageNum=1"
							lookupGroup="person" target="dialog" mask="true" width="800" height="400">
							<hi:text key='<spring:message code="pa.insurance.title.searchAndBringBack"/>' /><!--查找带回-->
						</a>
					</dd>
				</dl>
				<dl>
					<dt><!--数值-->
						<spring:message code="pa.insurance.title.dataValue"/>:
					</dt>
					<dd>
						<input type="text" name="RETURN_VALUE"  id="RETURN_VALUE" class="textInput" style="text-align:right;">
					</dd>
				</dl>
				<dl>
					<dt><!--开始月-->
						<spring:message code="pa.insurance.title.startMonth"/>:
					</dt>
					<dd>
						<input type="text" name="START_MONTH" id="START_MONTH" class="textInput ">
						<font color="red"><!--例:2012年2月---201202(下同)-->
							201202
						</font>
					</dd>
				</dl>
<!-- 				<dl> -->
<!-- 					<dt>结束月 -->
<!-- 						<spring:message code="pa.insurance.title.endMonth"/>: -->
<!-- 					</dt> -->
<!-- 					<dd> -->
<!-- 						<input type="text" name="END_MONTH" id="END_MONTH" class="textInput"> -->
<!-- 						<font color="red">例:2012年2月---201202(下同) -->
<!-- 							<spring:message code="pa.insurance.title.exampleMonth"/> -->
<!-- 						</font> -->
<!-- 					</dd> -->
<!-- 				</dl> -->
				<dl>
					<dt><!--备注-->
						<spring:message code="hr.viewPromote.title.REMARK"/>
					</dt>
					<dd>
						<input name="REMARK" type="text" maxlength="200"/>
					</dd>
				</dl>
				<dl>
					<dt><!--网址-->
						<spring:message code="pa.ins.alert.message.exportdata.netAddress"/>
					</dt>
					<dd>
						<input name="URL_STR" type="text" maxlength="200"/>
					</dd>
				</dl>
				<dl>
					<dt><!-- 上传附件-->
						<spring:message code="pa.ins.alert.message.exportdata.importFileAdd"/>
					</dt>
					<dd>
						<input id="file1" name="file" type="file" />
						<iframe id='callbackframe' name='callbackframe' src='about:blank' style='display:none'></iframe>
						<button type="submit" onclick="shangchuan();" id="shagnchuanfujian">上传附件</button>
					</dd>
				</dl>
				<input name="PARAM_NO" type="hidden" value="${PARAM_NO}">
				<input name="seach_PARAMDATANO" type="hidden" value="${PARAMDATANO}">
			</c:if>
			<c:if test="${insuranceInputItemParamInfo.DISTINCT_FIELD ne 'PERSON_ID'}">
				<c:choose>
					<c:when test="${insuranceInputItemParamInfo.DISTINCT_FIELD_2ND_NAME eq null}">
						<dl>
							<dt>
								<spring:message code="pa.insurance.title.company"/><!--公司-->
							</dt>
							<dd>
								<select name="CPNY_ID" id="CPNY_ID" style="width: 180px; position: static; visibility: inherit;">
									<c:forEach items="${cpnyList}" var="cpny">
										<option value="${cpny.CPNY_ID}"
											<c:if test="${CPNY_ID eq cpny.CPNY_ID}">selected</c:if> disabled="true">
											${cpny.CONTENT}
										</option>
									</c:forEach>
								</select>
							</dd>
						</dl>
						<dl>
							<dt>
								${insuranceInputItemParamInfo.DISTINCT_FIELD_NAME}:
							</dt>
							<dd>
								<select name="FIELD1_VALUE">
									<c:forEach items="${distinctList}" var="distinct">
										<option value="${distinct.FIELD_VALUE}">${distinct.FIELD_NAME}</option>
									</c:forEach>
								</select>
							</dd>
						</dl>
						<dl>
							<dt><!--数值-->
								<spring:message code="pa.insurance.title.dataValue"/>:
							</dt>
							<dd>
								<input type="text" name="RETURN_VALUE" id="RETURN_VALUE" class="textInput " style="text-align:right;">
							</dd>
						</dl>
						<dl>
							<dt><!--开始月-->
								<spring:message code="pa.insurance.title.startMonth"/>:
							</dt>
							<dd>
								<input type="text" name="START_MONTH" id="START_MONTH" class="textInput ">
								<font color="red"><!--例:2012年2月---201202(下同)-->
									201202
								</font>
							</dd>
						</dl>
<!-- 						<dl> -->
<!-- 							<dt>结束月 -->
<!-- 								<spring:message code="pa.insurance.title.endMonth"/>: -->
<!-- 							</dt> -->
<!-- 							<dd> -->
<!-- 								<input type="text" name="END_MONTH" id="END_MONTH" class="textInput"> -->
<!-- 								<font color="red">例:2012年2月---201202(下同) -->
<!-- 									<spring:message code="pa.insurance.title.exampleMonth"/> -->
<!-- 								</font> -->
<!-- 							</dd> -->
<!-- 						</dl> -->
						<dl>
							<dt><!--备注-->
								<spring:message code="hr.viewPromote.title.REMARK"/>
							</dt>
							<dd>
								<input name="REMARK" type="text" maxlength="200"/>
							</dd>
						</dl>
						<dl>
							<dt><!--网址-->
								<spring:message code="pa.ins.alert.message.exportdata.netAddress"/>
							</dt>
							<dd>
								<input name="URL_STR" type="text" maxlength="200"/>
							</dd>
						</dl>
						<dl>
							<dt><!-- 上传附件-->
								<spring:message code="pa.ins.alert.message.exportdata.importFileAdd"/>
							</dt>
							<dd>
								<input id="file1" name="file" type="file" />
								<iframe id='callbackframe' name='callbackframe' src='about:blank' style='display:none'></iframe>
								<button type="submit" onclick="shangchuan();" id="shagnchuanfujian">上传附件</button>
							</dd>
						</dl>
						<input name="PARAM_NO" type="hidden" value="${PARAM_NO}">
						<input name="seach_PARAMDATANO" type="hidden" value="${PARAMDATANO}">
					</c:when>
					<c:otherwise>
						<dl>
							<dt><!--公司-->
								<spring:message code="pa.insurance.title.company"/>
							</dt>
							<dd>
								<select name="CPNY_ID" id="CPNY_ID" style="width: 180px; position: static; visibility: inherit;">
									<c:forEach items="${cpnyList}" var="cpny">
										<option value="${cpny.CPNY_ID}"
											<c:if test="${CPNY_ID eq cpny.CPNY_ID}">selected</c:if> disabled="true">
											${cpny.CONTENT}
										</option>
									</c:forEach>
								</select>
							</dd>
						</dl>
	
						<dl>
							<dt>${insuranceInputItemParamInfo.DISTINCT_FIELD_NAME}:</dt>
							<dd>
								<select name="FIELD1_VALUE">
									<c:forEach items="${distinctList}" var="insurance">
										<option value="${insurance.FIELD_VALUE}">
											${insurance.FIELD_NAME}
										</option>
									</c:forEach>
								</select>
							</dd>
						</dl>
						<dl>
							<dt>${insuranceInputItemParamInfo.DISTINCT_FIELD_2ND_NAME}:</dt>
							<dd>
								<select name="FIELD2_VALUE">
									<c:forEach items="${distinctList2}" var="insurance">
										<option value="${insurance.FIELD_VALUE}">
											${insurance.FIELD_NAME}
										</option>
									</c:forEach>
								</select>
							</dd>
						</dl>
						<dl>
							<dt><!--数值-->
								<spring:message code="pa.insurance.title.dataValue"/>:
							</dt>
							<dd>
								<input type="text" name="RETURN_VALUE" id="RETURN_VALUE" class="textInput " style="text-align:right;">
							</dd>
						</dl>
						<dl>
							<dt><!--开始月-->
								<spring:message code="pa.insurance.title.startMonth"/>:
							</dt>
							<dd>
								<input type="text" name="START_MONTH" id="START_MONTH" class="textInput ">
								<font color="red"><!--例:2012年2月---201202(下同)-->
									201202
								</font>
							</dd>
						</dl>
<!-- 						<dl> -->
<!-- 							<dt>结束月 -->
<!-- 								<spring:message code="pa.insurance.title.endMonth"/>: -->
<!-- 							</dt> -->
<!-- 							<dd> -->
<!-- 								<input type="text" name="END_MONTH" id="END_MONTH" class="textInput"> -->
<!-- 								<font color="red">例:2012年2月---201202(下同) -->
<!-- 									<spring:message code="pa.insurance.title.exampleMonth"/> -->
<!-- 								</font> -->
<!-- 							</dd> -->
<!-- 						</dl> -->
						<dl>
							<dt><!--备注-->
								<spring:message code="hr.viewPromote.title.REMARK"/>
							</dt>
							<dd>
								<input name="REMARK" type="text" maxlength="200"/>
							</dd>
						</dl>
						<dl>
							<dt><!--网址-->
								<spring:message code="pa.ins.alert.message.exportdata.netAddress"/>
							</dt>
							<dd>
								<input name="URL_STR" type="text" maxlength="200"/>
							</dd>
						</dl>
						<dl>
							<dt><!-- 上传附件-->
								<spring:message code="pa.ins.alert.message.exportdata.importFileAdd"/>
							</dt>
							<dd>
								<input id="file1" name="file" type="file" />
								<iframe id='callbackframe' name='callbackframe' src='about:blank' style='display:none'></iframe>
								<button type="submit" onclick="shangchuan();" id="shagnchuanfujian">上传附件</button>
							</dd>
						</dl>
						<input name="PARAM_NO" type="hidden" value="${PARAM_NO}">
						<input name="seach_PARAMDATANO" type="hidden" value="${PARAMDATANO}">
					</c:otherwise>
				</c:choose>
			</c:if>
		</div>
		<div class="formBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="submit" onclick="baocun();"><!--保存-->
								<spring:message code="pa.insurance.title.submit"/>
							</button>
						</div>
					</div>
				</li>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" class="close"><!--取消-->
								<spring:message code="public.title.cancle"/>
							</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</form>
	
</div>
