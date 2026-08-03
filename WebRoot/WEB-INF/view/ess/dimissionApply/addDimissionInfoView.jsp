<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
//添加决裁者
function addRowByIDEss2016(currentRowID){
	var count = parseInt($("#affirmCount").val());
    var htm  ='<tr id="rowIdEss2016'+ count +'"><td class="td_type" style="text-align: center" width="33%"><span name="rowIndex"></span></td>';
		htm +='<td class="td_type" style="text-align: center" width="33%">';
		htm +='<input id="AFFIRMOR_IDEss2016' + count + '" name="AFFIRMOR_ID" value="" type="hidden"/>';
		htm +='<input id="EMPINFOEss2016' + count + '" name="empid" type="text" class="required" lookupGroup="person" onkeydown="submitKeyClick_affirmor(this,' + count + ',event)" class="required"/>';
		//htm +='<a class="btnLook" href="/ar/attendanceSettings/viewKeeperList?pageNum=1" lookupGroup="person">';
		htm +='</td>';
		htm +='<td class="td_type" style="text-align: center" width="33%">';
		htm +='<img src="/resources/images/+.gif" title="添加"';	
		htm +='border="0" align="absmiddle" style="cursor:hand" onclick="addRowByIDEss2016(' + count + ')"/>&nbsp;&nbsp;&nbsp;';
		htm +='<img src="/resources/images/-.gif" title="删除"';	
		htm +='border="0" align="absmiddle" style="cursor:hand" onclick="javaScript:document.all.addAffirm_listEss2016.deleteRow(event.srcElement.parentElement.parentElement.rowIndex);changeAffirmLevel();"/></td></tr>';

   	//当前行之后插入一行
   	$("#rowIdEss2016" + currentRowID).after(htm);
   	changeAffirmLevel();
  	$("#affirmCount").val(++count) ;
}
//修改决裁者等级
function changeAffirmLevel(){
	var tb2 = document.getElementById("addAffirm_listEss2016");
	var rowCount = tb2.rows.length;
	for(var m=0;m<rowCount;m++){
		tb2.rows[m].cells[0].innerHTML = m+1;
	}
}
var keyCodeInit=0;
function submitKeyClick_affirmor(obj,index,event){
	var localName = '';
	var idcardNo = '';
	var navTabId = '';
 	var e= event ? event : window.event; 
 	var keyCode = e.which ? e.which : e.keyCode;
   	if(keyCode==13){
   		keyCodeInit=keyCode;
		var empid=obj.value;
		var empIdStr=obj.id;
		var personIdStr="personId"+empIdStr.substring(7);
		var empNameStr="empName"+empIdStr.substring(7);
   		$.ajax({
			type: 'POST',
			url: encodeURI('/sys/affirm/getPersonCnt?navTabId=' + navTabId + '&EMPID='+empid+'&LOCAL_NAME='+localName+'&IDCARD_NO='+idcardNo),
			dataType:"json",
			cache: false,
			success: function(jsonObject){
						if (jsonObject.perCnt==0){
							alertMsg.error('<spring:message code="alert.message.sys.affirm.searchNoPerson"/>');
						}
						if(jsonObject.perCnt>1 ){
							document.getElementById("onck").href=encodeURI(encodeURI("/sys/arAffirmPost/viewAffirmorsEmpIdList?pageNum=1&navTabId=" + navTabId 
									+'&seach_EMPID='+empid
									+'&seach_LOCAL_NAME='+localName
									+'&seach_IDCARD_NO='+idcardNo
									+'&empId_sy0482='+empIdStr
									+'&personId_sy0482='+personIdStr  
									+'&empName_sy0482='+empNameStr 
									));
							document.getElementById("onck").click();
						}
						if(jsonObject.perCnt==1){
							$("#EMPINFOEss2016" + index).val('['+jsonObject.empId + ']-'+jsonObject.empName);
							$("#AFFIRMOR_IDEss2016" + index).val( jsonObject.personId);
						}
					},
			error: DWZ.ajaxError
		});
    }
 }

/**
 * 禁用textArea以及Input框的enter键的自动提交
 */
document.onkeydown = function(event) {  
	  var target, code, tag;  
	  if (!event) {  
	       event = window.event; //针对ie浏览器  
	       target = event.srcElement;  
	       code = event.keyCode;  
	       if (code == 13) {  
	           tag = target.tagName;  
	           if (tag == "TEXTAREA") {
		           return true;
		       }else{ 
			       return false;
			   }  
	       }  
	  }else {  
	       target = event.target; //针对遵循w3c标准的浏览器，如Firefox  
	       code = event.keyCode;  
	       if (code == 13) {  
	           tag = target.tagName;  
	           if (tag == "INPUT"){ 
		           return false; 
		       }else {
			        return true;
			   }   
	      }  
	 }  
}
	
    function validateCallback_pApply(form, callback) {
		var $form = $("#addDimissionInfoApplyP");
	    if (!$form.valid()) {
		    return false;
	    }

		if ($(":input[name='AFFIRMOR_ID']").length == 0){
			alertMsg.error("请先设置决裁者。");
			return false;
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
	
	
function submitKeyClick_apply(obj,event){
	var localName = '';
	var idcardNo = '';
	var navTabId = '';
 	var e= event ? event : window.event; 
 	var keyCode = e.which ? e.which : e.keyCode;
   	if(keyCode==13){
   		keyCodeInit=keyCode;
		var empid=obj.value;
		var empIdStr=obj.id;
		var personIdStr="PERSON_ID";
		var empNameStr="empName_apply";
   		$.ajax({
			type: 'POST',
			url: encodeURI('/sys/affirm/getPersonCnt?navTabId=' + navTabId + '&EMPID='+empid+'&LOCAL_NAME='+localName+'&IDCARD_NO='+idcardNo),
			dataType:"json",
			cache: false,
			success: function(jsonObject){
						if (jsonObject.perCnt==0){
							alertMsg.error('<spring:message code="alert.message.sys.affirm.searchNoPerson"/>');
						}
						if(jsonObject.perCnt>1 ){
							alertMsg.error('请填写准确工号！');
						}
						if(jsonObject.perCnt==1){
							document.getElementById(empIdStr).value=jsonObject.empId;
							document.getElementById(personIdStr).value=jsonObject.personId;
							document.getElementById(empNameStr).value='['+jsonObject.empId + ']-'+jsonObject.empName;
							if(jsonObject.perCnt==1){
								
								 navTabAjaxDone(
					    	    	{
					    	    		"statusCode":"200", 
										"forwardUrl":"/ess/dimissionApply/addDimissionInfoView?flag=1 + &PERSON_ID= + jsonObject.personId", //考勤申请类型
										"callbackType":"forward"
					    	    	}
					    	    ) ;
							}
						}
					},
			error: DWZ.ajaxError
		});
    }
 }
</script>
<div class="pageContent">
	<form id="addDimissionInfoApplyP" method="post" action="/ess/dimissionApply/addDimissionInfoApply" class="pageForm required-validate" onsubmit="return validateCallback_pApply(this,navTabAjaxDone);">
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit"><spring:message code="public.title.submit"/><!-- 提交 --></button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close"><spring:message code="public.title.cancle"/><!-- 取消 --></button></div></div>
				</li>
			</ul>
		</div>
		<div>
			<table width="100%"  border="0" cellpadding="0" cellspacing="0" class="user_table margin_b">
			<tr>
				<td width="15%" class="td_title"><spring:message code="ess.viewApply.title.applyName"/><!-- 申请者--></td>
				<td width="30%" colspan="3">
			    <c:if test="${authority ne '1'}">
				    <input id="PERSON_ID" name="PERSON_ID" value="${personInfo.EMPID}" type="hidden" />
					${personInfo.EMPID}
				</c:if>
				<c:if test="${authority eq '1'}">
					<input id="empId_apply" name="dwz.person.empId1" value="${personInfo.EMPID}" type="text" lookupGroup="person"
						onkeydown="submitKeyClick_apply(this,event)" class="required"/>
				</c:if>
			    <input id="empName_apply" name="empName_apply"  type="hidden" value="${personInfo.LOCAL_NAME}" readonly style="border:0;background:transparent;"
					type="text" lookupGroup="person"/>${personInfo.LOCAL_NAME}/${personInfo.DEPARTMENT}/人员类型（${personInfo.EMP_TYPE_NAME}）/职级（${personInfo.POST_GRADE_NO}）
				<input id="PERSON_ID" name="PERSON_ID" type="hidden" size="30"
					value="${personInfo.PERSON_ID}" lookupGroup="person"/>
				<input type="hidden" name="APPLY_NO" id="APPLY_NO" value="${APPLY_NO }"/>
				</td>
			</tr>
			<tr>
				<td width="15%" class="td_title"><spring:message code="display.pa.ecc.expectresigndate"/><!--预离职日期--></td>
				<td width="35%" class="td_type" colspan="3">
				     <input type="text" id="APPLY_LEAVE_TIME" name="APPLY_LEAVE_TIME" class="date" format="yyyy-MM-dd"/>
				    <a class="inputDateButton"><spring:message code="public.title.choose"/><!-- 选择 --></a>				    
				</td>
				<td width="15%" class="td_title"><spring:message code="pa.salarycode.affirm.reason"/><!--申请事由--></td>
				<td width="35%" class="td_type"  colspan="5">
				    <textarea type="text" id="APPLY_REASON" style="width: 500" name="APPLY_REASON"></textarea>
				</td> 
			</tr>	
	</table>
			</div>
			<div>
			<table width="100%"  border="0" cellpadding="0" cellspacing="0" class="user_table margin_b" >
				<tr>
					<td class="td_title"  width="20%" rowspan="2">
						决裁线
					</td>
					<td class="td_title" width="30%">
						决裁等级
					</td>
					<td class="td_title" width="40%">
						决裁者
					</td>
					<td class="td_title" width="30%">
						是否新增
					</td>
				</tr>
				<tr>
					<td colspan="3">
						<table width="100%" border="0" cellpadding="0" cellspacing="0" id="addAffirm_listEss2016">
							<tbody>
							<c:forEach items="${affirmorList}" var="affirmor" varStatus="j">	
								<tr id="rowIdEss2016${j.index}">
									<td class="td_type" style="text-align: center" width="33%">
										${j.count}
									</td>
									<td class="td_type" style="text-align: center" width="33%">
										[${affirmor.EMPID}]-${affirmor.LOCAL_NAME }
										<input type="hidden" name="AFFIRMOR_ID" value="${affirmor.AFFIRMOR_ID }"/>
									</td>
									<td class="td_type" style="text-align: center" width="33%">
									<c:if test="${j.count ne affirmorListCnt}">
										<img src="/resources/images/+.gif" title="添加"
											border="0" align="absmiddle" style="cursor:hand" onclick="addRowByIDEss2016(${j.index})"/>
									</c:if>
									</td>
								</tr>
							</c:forEach>
							</tbody>
						</table>
					</td>
				</tr>
			</table>
			</div>
		    <input type="hidden" name="count" id="count" value="0">
		    <input type="hidden" name="affirmCount" id="affirmCount" value="${affirmorListCnt }">
	</form>	
</div>
