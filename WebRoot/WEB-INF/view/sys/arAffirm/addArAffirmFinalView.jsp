<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<SCRIPT type='text/javascript'>
var affirmLevel=1;
function appendRow(postFlag){
	  var existFlag=false;
	  if(postFlag!="blank"){
		  var postSelected=document.getElementById("Post");
		  if(postSelected.value==""){
			  alertMsg.error('<spring:message code="alert.message.sys.arAffirm.pleaseChooseDuty"/>');
			  return false;
		  }else{
			  var postNos=document.getElementsByName("AFFIRMOR_NO");
			  if(postNos&&postNos!=null){
				 for(var i=0;i<postNos.length;i++){
					 if(postSelected.value==postNos[i].value){
						 existFlag=true;
						 break;
					 }
				 }
			  }
		  }
	  }
	  if(existFlag==false){
		  var rowNum=document.getElementById('operateTable').rows.length;
		  var nTr = document.getElementById('operateTable').insertRow(rowNum);
		  
		  var cell0=nTr.insertCell(0); 
		  var cell1=nTr.insertCell(1); 
		  var cell2=nTr.insertCell(2); 
		  cell0.style.textAlign="center";
		  cell0.className='td_type'; 
		  cell0.innerHTML = "<input type='radio' name='rowNum' />" 			
		  cell1.style.textAlign="center";
		  cell1.className='td_type'; 
		  if(postFlag=="blank"){
		  		cell1.innerHTML ="空缺<input type='hidden' name='AFFIRMOR_NO' value='vacancy' /> ";
		  }else{
		  		cell1.innerHTML = postSelected.options[postSelected.selectedIndex].text+
						 " <input type='hidden' name='AFFIRMOR_NO' value='" + postSelected.value + "' /> ";
		  }	  
		  cell2.style.textAlign="center";
		  cell2.className='td_type';
		  cell2.innerHTML =affirmLevel
			
		 affirmLevel++ ;
	  }else{
	     alertMsg.error('<spring:message code="alert.message.sys.arAffirm.dutyIsAlreadyExsist"/>');
	  }
}
function deleteRow(){
	    var deleteNum=0;
      var optionsArr=document.getElementsByName("rowNum");
     
      for(var i=2;i<optionsArr.length+2;i++){
	        //i为行号
			if(optionsArr[i-2].checked==true){
				document.getElementById('operateTable').deleteRow(i-deleteNum-1);
				affirmLevel--;
				deleteNum++;
				break;//单选框
			}
	    }
	    if(deleteNum!=0){
	    	var operateTb=document.getElementById('operateTable');
	    	var operateTbLength=document.getElementById('operateTable').rows.length;
	    	for(var j=1;j<operateTbLength;j++){
	    		operateTb.rows[j].cells[2].innerHTML=j;
		    }
		}
}	 
function validateCallbackSy0486(form, callback) {
	
	var $form = $(form);
	
	if (!$form.valid()) {
		return false;
	}
	if(document.getElementById("APPLY_TYPE")){
		var typeVar=document.getElementById("APPLY_TYPE").value;
		if(typeVar==""){
		    alertMsg.error('<spring:message code="alert.message.sys.arAffirm.pleaseChooseApplyType"/>');
			return false;
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
</SCRIPT>
 
<div class="pageContent">
	<form method="post" action="/sys/arAffirm/saveArAffirmFinalInfo" class="pageForm required-validate" onsubmit="return validateCallbackSy0486(this,navTabAjaxDone);">
	<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit"><spring:message code="public.title.submit"/><!--提交--></button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close"><spring:message code="public.title.cancle"/><!--取消--></button></div></div></li>
			</ul>
		</div>		
		
	<div style="clear: both;"></div>
		<div class="panel">
			<h1>
				<spring:message code="sys.affirm.title.projectParam" />
				<!--项目参数-->
			</h1>
			<div>
				<table width="100%" border="0" cellpadding="0" cellspacing="0"
					class="user_table">
					<tr>
						<td class="td_title">
							<spring:message code="sys.affirm.title.applyType" />
							<!--申请类型-->
						</td>
						<td class="td_type" width="15%">
							<select name="APPLY_TYPE" id="APPLY_TYPE">
								<option value="">
									--
									<spring:message code="sys.affirm.title.choose" />
									--
								</option>
								<c:forEach items="${applyList}" var="result">
									<option value="${result.CODE_NO}">
										${result.CODENAME}
									</option>
								</c:forEach>
							</select>
							<input type="hidden" name="TYPE" value="ar" />
						</td>

						<td class="td_title">
							<spring:message code="sys.affirm.title.startFlag" />
							<!--开始标志-->
						</td>
						<td class="td_type" width="15%">
							<select name="REFERENCN_FROM_FLAG" id="REFERENCN_FROM_FLAG">
								<option value="1" selected>
									<spring:message code="sys.affirm.title.yes" />
									<!--是-->
								</option>
								<option value="0">
									<spring:message code="sys.affirm.title.no" />
									<!--否-->
								</option>
							</select>

						</td>
						<td class="td_title">
							<spring:message code="sys.affirm.title.startMigrationDirection" />
							<!--开始偏移方向-->
						</td>
						<td class="td_type" width="15%">
							<select name="REFERENCN_FROM_RELATION"
								id="REFERENCN_FROM_RELATION">
								<option value=">">
									<spring:message code="sys.affirm.title.more" />
									<!--大于-->
								</option>
								<option value="<">
									<spring:message code="sys.affirm.title.less" />
									<!--小于-->
								</option>
								<option value="=" selected>
									<spring:message code="sys.affirm.title.affirmGradeLevel" />
									<!--审批等级-->
									<spring:message code="sys.affirm.title.euqal" />
									<!--等于-->
								</option> 
								<option value=">=">
									<spring:message code="sys.affirm.title.moreThanEqualTo" />
									<!--大于等于-->
								</option>
								<option value="<=">
									<spring:message code="sys.affirm.title.lessThanEqualTo" />
									<!--小于等于-->
								</option>
							</select>
						</td>
						<td class="td_title">
							<spring:message code="sys.affirm.title.startLength" />
							<!--开始长度-->
						</td>
						<td class="td_type">
							<input name="REFERENCN_FROM_OFFSET" type="text"
								id="REFERENCN_FROM_OFFSET" size="5" maxlength="5" value="0">
						</td>

					</tr>
					<tr>
						<td class="td_title">
							<spring:message code="sys.affirm.title.ifReference" />
							<!--是否参考-->
						</td>
						<td class="td_type" width="15%">
							<select name="REFERENCN_FLAG" id="REFERENCN_FLAG">
								<option value="1" selected>
									<spring:message code="sys.affirm.title.yes" />
									<!--是-->
								</option>
								<option value="0">
									<spring:message code="sys.affirm.title.no" />
									<!--否-->
								</option>
							</select>
						</td>
						<td class="td_title">
							<spring:message code="sys.affirm.title.endFlag" />
							<!--结束标志-->
						</td>
						<td class="td_type" width="15%">
							<select name="REFERENCN_TO_FLAG" id="REFERENCN_TO_FLAG">
								<option value="1">
									<spring:message code="sys.affirm.title.yes" />
									<!--是-->
								</option>
								<option value="0" selected>
									<spring:message code="sys.affirm.title.no" />
									<!--否-->
								</option>
							</select>
						</td>
						<td class="td_title">
							<spring:message code="sys.affirm.title.endMigrationDirection" />
							<!--结束偏移方向-->
						</td>
						<td class="td_type" width="15%">
							<select name="REFERENCN_TO_RELATION" id="REFERENCN_TO_RELATION">
								<option value=">">
									<spring:message code="sys.affirm.title.more" />
									<!--大于-->
								</option>
								<option value="<">
									<spring:message code="sys.affirm.title.less" />
									<!--小于-->
								</option>
								<option value="=" selected>
									<spring:message code="sys.affirm.title.affirmGradeLevel" />
									<!--审批等级-->
									<spring:message code="sys.affirm.title.euqal" />
									<!--等于-->
								</option>
								<option value=">=">
									<spring:message code="sys.affirm.title.moreThanEqualTo" />
									<!--大于等于-->
								</option>
								<option value="<=">
									<spring:message code="sys.affirm.title.lessThanEqualTo" />
									<!--小于等于-->
								</option>
							</select>
						</td>
						<td class="td_title">
							<spring:message code="sys.affirm.title.endLength" />
							<!--结束长度-->
						</td>
						<td class="td_type">
							<input name="REFERENCN_TO_OFFSET" type="text"
								id="REFERENCN_TO_OFFSET" size="5" maxlength="5" value="0">
						</td>
					</tr>
					<tr>
						<td class="td_title">
							裁决者等级长度
						</td>
						<td class="td_type">
							<input name="AFFIRM_LEVEL" type="text"
								id="AFFIRM_LEVEL" size="5" maxlength="5" value="0">
						</td>
					</tr>
				</table>
			</div>
		</div>
	</form>
</div>