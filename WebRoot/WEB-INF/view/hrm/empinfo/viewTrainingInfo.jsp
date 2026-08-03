<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script>
	function addrow(){
	    var count = parseInt($("#count").val());
	   
	    var i = count ;
	    var htm="";
	   		htm+='<table id="table' + i + '" border="0" width="100%" cellpadding="0" cellspacing="0" class="user_table margin_b">';
	   		htm+='<tr>';
	   		
	   		htm+='<td  class="td_title"><spring:message code="liang.hr.viewTraining.title.COURSE_NAME"/><!--课程名--></td>';
	   		htm+='<td  class="td_type"><input type="text" id="COURSE_NAME' + i + '" name="COURSE_NAME' + i + '" class="required" maxlength="20"></td>';
	   		
	   		htm+='<td  class="td_title"><spring:message code="liang.hr.viewTraining.title.SELECT_MUST"/><!--选择/必选--></td>';
	   		htm+='<td  class="td_type"><select id="MUST_CODE' + i + '" name="MUST_CODE' + i + '"><option value=""><spring:message code="hr.viewPersonalInfo.title.ADDED_BY_KELI" /></option>' ;
	   			<c:forEach items="${mustList}" var="item" >
	   			htm+='<option value="${item.CODE_NO}">${item.CODENAME}</option>';
				</c:forEach>
	   		htm+='</select></td>';
	   		
	   		htm+='<td  class="td_title"><spring:message code="liang.hr.viewTraining.title.TRAINING_DIFFERENTIATE"/><!--培训区分  --></td>';
	   		htm+='<td  class="td_type"><select id="TRAINING_DIFFERENTIATE' + i + '" name="TRAINING_DIFFERENTIATE' + i + '"><option value=""><spring:message code="hr.viewPersonalInfo.title.ADDED_BY_KELI" /></option>' ;
	   			<c:forEach items="${differentiateList}" var="item" >
	   			htm+='<option value="${item.CODE_NO}">${item.CODENAME}</option>';
				</c:forEach>
	   		htm+='</select></td>';
	   		
	   		htm+='<td  class="td_title"><spring:message code="zxc.hr.contract.CONTRACT_START_DATE"/><!--开始日期--></td>';
	   		htm+='<td  class="td_type"><input type="text" id="START_DATE' + i + '"  name="START_DATE' + i + '" class="date required" readonly="true" format="yyyy-MM-dd" yearstart="-50" yearend="5" onClick="setdate(this);"/></td>';
	   		
	   		
	   		
	   		
	   		
	   		
	   		htm+='<td rowspan="3"><img src="/resources/css/ligerUI/skins/icons/delete.gif" onclick="f_delShiftInfo(\'table'+i+'\')" style="cursor:hand"> </td>';
	   		
	   		htm+='</tr>';
	   		htm+='<tr>';
	   		
	   		
	   		htm+='<td  class="td_title"><spring:message code="liang.hr.viewTraining.title.INSTITUTION_NAME"/><!--培训机关--></td>';
	   		htm+='<td  class="td_type"><input type="text" id="INSTITUTION_NAME' + i + '" name="INSTITUTION_NAME' + i + '" maxlength="60"></td>';
	   		
	   		
	   		htm+='<td  class="td_title"><spring:message code="liang.hr.viewTraining.title.TRAINING_METHOD"/><!--培训方法--></td>';
	   		htm+='<td  class="td_type"><select id="TRAINING_METHOD' + i + '" name="TRAINING_METHOD' + i + '"><option value=""><spring:message code="hr.viewPersonalInfo.title.ADDED_BY_KELI" /></option>' ;
	   			<c:forEach items="${trainingMethodList}" var="item" >
	   			htm+='<option value="${item.CODE_NO}">${item.CODENAME}</option>';
				</c:forEach>
	   		htm+='</select></td>';
	   		
	   		htm+='<td  class="td_title"><spring:message code="liang.hr.viewTraining.title.TRAINING_TIME" /><!--培训时间--></td>';
	   		htm+='<td  class="td_type" ><input type="text" id="TRAINING_TIME' + i + '" name="TRAINING_TIME' + i + '" class="textInput"></td>';
	   		htm+='<td  class="td_title"><spring:message code="zxc.hr.contract.CONTRACT_END_DATE"/><!--结束日期--></td>';
	   		htm+='<td  class="td_type"><input type="text" id="END_DATE' + i + '" name="END_DATE' + i + '" class="date required" readonly="true" format="yyyy-MM-dd" yearstart="-50" yearend="5" onClick="setdate(this);"/></td>';
	   		
	   		htm+='</tr>';
	   		htm+='<tr>';
	   		htm+='<td  class="td_title"><spring:message code="liang.hr.viewTraining.title.TRAINING_RESULT" /><!--培训结果--></td>';
	   		htm+='<td  class="td_type"><input type="text" id="TRAINING_RESULT' + i + '" name="TRAINING_RESULT' + i + '" class="textInput"  /></td>';
	   		htm+='<td  class="td_title"><spring:message code="liang.hr.viewTraining.title.REMARKS"/><!--备注--></td>';
	   		htm+='<td  class="td_type" colspan="7"><input type="text" id="REMARKS' + i + '" name="REMARKS' + i + '" class="textInput"></td>';
	   		htm+='</tr>';
	   		htm+='</table>';
	   		

	   	$("#createTable").append(htm) ;

	   	count++;  
	    $("#count").attr("value",count) ;
    }


    function f_delShiftInfo(obj){
    	$("#"+obj+"").remove();
    }
    
    function validateCallbackViewTrainingInfo(form, callback) {
	
		var $form = $("#viewTrainingInfo");
		
		if (!$form.valid()) {
			return false;
		}
		
		var count = parseInt($("#count").val());
		
		for (i=0;i<count;i++){
			
			if(document.getElementById("START_DATE"+i) != null ){
				
				var sd=document.getElementById("START_DATE"+i).value;
				var ed=document.getElementById("END_DATE"+i).value;
				
				var date1 = sd.replaceAll("-","");
				var date2 = ed.replaceAll("-","");
				
				if (date1 - date2 > 0) {
					alertMsg.error('<spring:message code="hr.alert.message.viewPersonalInfo.checkStartEndDate"/>');//开始日期不能晚于结束日期
					document.getElementById("START_DATE"+i).focus();
					return false;
				}
			}
			
			if(document.getElementById("AGREEMENT_START_DATE"+i) != null && document.getElementById("AGREEMENT_END_DATE"+i) != null ){
				
				var sd1=document.getElementById("AGREEMENT_START_DATE"+i).value;
				var ed1=document.getElementById("AGREEMENT_END_DATE"+i).value;
				
				var date3 = sd1.replaceAll("-","");
				var date4 = ed1.replaceAll("-","");
				
				if (date3 - date4 > 0) {
					alertMsg.error('<spring:message code="hr.alert.message.viewTraining.checkAgreementStartDateAndEndDate"/>');//协议开始日期不能晚于协议结束日期
					document.getElementById("AGREEMENT_START_DATE"+i).focus();
					return false;
				}
			}
			
			
		}
		
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
	
	function agreementControl(){
	
	}
	
</script>

<div class="pageContent">
	<form id="viewTrainingInfo" method="post" action="/hrm/empinfo/addTrainingInfo" class="pageForm required-validate" onsubmit="return validateCallbackViewTrainingInfo(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<table width="100%"  border="0" cellpadding="0" cellspacing="0" class="user_table margin_b">
				<tr>
					<td class="td_title">
						<spring:message code="liang.hr.viewTraining.title.COURSE_NAME"/>
						<!--课程名-->
					</td>
					<td class="td_type">
						<input type="text" name="COURSE_NAME0" class="textInput required" maxlength="20"/>
						<input type="hidden" name="PERSON_ID" class="textInput" value="${PERSON_ID }"/>
					</td>
					<td class="td_title">
						<spring:message code="liang.hr.viewTraining.title.SELECT_MUST"/>
						<!--选择/必选-->
					</td>
					<td class="td_type">
						<select name="MUST_CODE0" id="MUST_CODE0"  >
							<option value="">
								<spring:message code="hr.viewPersonalInfo.title.ADDED_BY_KELI" />
								<!-- 请选择 -->
							</option>
							<c:forEach items="${mustList}" var="recsource">
								<option value="${recsource.CODE_NO}" >
									${recsource.CODENAME}
								</option>
							</c:forEach>
						</select>
					</td>
					<td class="td_title">
						<spring:message code="liang.hr.viewTraining.title.TRAINING_DIFFERENTIATE"/>
						<!--培训区分  -->
					</td>
					<td class="td_type">
						<select name="TRAINING_DIFFERENTIATE0" id="TRAINING_DIFFERENTIATE0"  >
							<option value="">
								<spring:message code="hr.viewPersonalInfo.title.ADDED_BY_KELI" />
								<!-- 请选择 -->
							</option>
							<c:forEach items="${differentiateList}" var="recsource">
								<option value="${recsource.CODE_NO}" >
									${recsource.CODENAME}
								</option>
							</c:forEach>
						</select>
					</td>
					
					<td class="td_title">
						<spring:message code="zxc.hr.contract.CONTRACT_START_DATE"/>
						<!--开始日期-->
					</td>
					<td class="td_type">
						<input type="text" id="START_DATE0"  name="START_DATE0" class="date required" readonly="true" format="yyyy-MM-dd" yearstart="-50" yearend="5" onClick="setdate(this);"/>
					</td>
					
					<td rowspan='3'><img src="/resources/css/ligerUI/skins/icons/add.gif" border="0" align="absmiddle" style="cursor:hand" onclick="addrow()"/></td>
				</tr>
				<tr>
					<td class="td_title">
						<spring:message code="liang.hr.viewTraining.title.INSTITUTION_NAME"/>
						<!--培训机关-->
					</td>
					<td class="td_type">
						<input type="text" name="INSTITUTION_NAME0" class="textInput" maxlength="60"/>
					</td>
					<td class="td_title">
						<spring:message code="liang.hr.viewTraining.title.TRAINING_METHOD"/>
						<!--培训方法-->
					</td>
					<td class="td_type">
						<select name="TRAINING_METHOD0" id="TRAINING_METHOD0"  >
							<option value="">
								<spring:message code="hr.viewPersonalInfo.title.ADDED_BY_KELI" />
								<!-- 请选择 -->
							</option>
							<c:forEach items="${trainingMethodList}" var="recsource">
								<option value="${recsource.CODE_NO}" >
									${recsource.CODENAME}
								</option>
							</c:forEach>
						</select>
					</td>
					<td class="td_title">
						<spring:message code="liang.hr.viewTraining.title.TRAINING_TIME" />
						<!--培训时间-->
					</td>
					<td class="td_type" >
						<input type="text" name="TRAINING_TIME0" class="textInput"  />
					</td>
					<td class="td_title">
						<spring:message code="zxc.hr.contract.CONTRACT_END_DATE"/>
						<!--结束日期-->
					</td>
					<td class="td_type">
						<input type="text" id="END_DATE0"  name="END_DATE0" class="date required" readonly="true" format="yyyy-MM-dd" yearstart="-50" yearend="5" onClick="setdate(this);"/>
					</td>
				</tr>	
				<tr>
					<td class="td_title">
						<spring:message code="liang.hr.viewTraining.title.TRAINING_RESULT" />
						<!--培训结果-->
					</td>
					<td class="td_type">
						<input type="text" name="TRAINING_RESULT0" class="textInput"  />
					</td>
					<td class="td_title">
						<spring:message code="liang.hr.viewTraining.title.REMARKS"/>
						<!--备注-->
					</td>
					<td class="td_type" colspan="5">
						<input type="text" name="REMARKS0" class="textInput"  />
					</td>
				</tr>		
			</table>
			
			<div id="createTable" width="100%"></div>
			
		    <input type="hidden" name="count" id="count" value="1">
		</div>
		
		
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