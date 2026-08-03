<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.easydrag.js"></script>
<script type="text/javascript">
//添加审批者
function addRowByIDEss2016(currentRowID){
	var count = parseInt($("#affirmCountDimi").val());
    var htm  ='<tr id="rowIdEss2016'+ count +'"><td class="td_type" style="text-align: center" width="33%"><span name="rowIndex"></span></td>';
		htm +='<td class="td_type" style="text-align: center" width="33%">';
		htm +='<input id="dwz.person.dimipersonId' + count + '" name="AFFIRMOR_ID" value="" type="hidden"/>';
		htm +='<input id="dwz.person.dimiempName' + count + '" name="empid" type="text" class="required" lookupGroup="person" onkeydown="submitKeyClick_affirmor_dimi(this,' + count + ',event)" class="required"/>';
		htm +='</td>';
		htm +='<td class="td_type" style="text-align: center" width="33%">';
		htm +='<img src="/resources/images/+.gif" title="添加"';	
		htm +='border="0" align="absmiddle" style="cursor:hand" onclick="addRowByIDEss2016(' + count + ')"/>&nbsp;&nbsp;&nbsp;';
		htm +='<img src="/resources/images/-.gif" title="删除"';	
		htm +='border="0" align="absmiddle" style="cursor:hand" onclick="javaScript:document.all.addAffirm_listEss2016.deleteRow(event.srcElement.parentElement.parentElement.rowIndex);changeAffirmLevel();"/></td></tr>';

   	//当前行之后插入一行
   	$("#rowIdEss2016" + currentRowID).after(htm);
	$("[id='dwz.person.dimiempName" + count + "']").attr("alt","请输入关键字按回车检索").attr("size","30").inputAlert();
   	changeAffirmLevel();
  	$("#affirmCountDimi").val(++count) ;
}


function addRowByIDApplyDimiFirst(){
	var count = parseInt($("#affirmCountDimi").val());
	str = '<tr id = "rowIdEss2016'+count+'">'
      	+'<td style="text-align: center"></td>'
			+'<td style="text-align: center">'
				+'<input id="dwz.person.dimipersonId'+count+'" name="AFFIRMOR_ID" value="" type="hidden" lookupGroup="person"/>'
				+'<input id="dwz.person.dimiempName'+count+'" name="empid" value="" type="text" lookupGroup="person" '
				+'	onkeydown="submitKeyClick_affirmor_dimi(this,' + count + ',event)" class="required"/>'
			+'</td>'
			+'<td style="text-align: center">'
					+'<img src="/resources/images/+.gif" style="cursor:hand" title="添加" onclick="addRowByIDEss2016(' + count + ');"/>&nbsp;&nbsp;&nbsp;'
					+'<img src="/resources/images/-.gif" title="删除"'
					+'border="0" align="absmiddle" style="cursor:hand" onclick="javaScript:document.all.addAffirm_listEss2016.deleteRow(event.srcElement.parentElement.parentElement.rowIndex);changeAffirmLevel();"/></td></tr>';

	var tb2 = document.getElementById("addAffirm_listEss2016");
 	//当前行之后插入一行
 	$("#" + tb2.rows[0].id).before(str);
	$("[id='dwz.person.dimiempName" + count + "']").attr("alt","请输入关键字按回车检索").attr("size","30").inputAlert();
	changeAffirmLevel();
	$("#affirmCountDimi").val(++count) ;
}

//修改审批者等级
function changeAffirmLevel(){
	var tb2 = document.getElementById("addAffirm_listEss2016");
	var rowCount = tb2.rows.length;
	for(var m=0;m<rowCount;m++){
		tb2.rows[m].cells[0].innerHTML = m+1;
	}
}

var keyCodeInit=0;
function submitKeyClick_affirmor_dimi(obj,index,event){
	var e= event ? event : window.event; 
	var keyCode = e.which ? e.which : e.keyCode;
 	if(keyCode==13){
 		keyCodeInit=keyCode;
		var empid=obj.value.replace(/[ ]/g,"");
		var empIdStr=obj.id.substring(11);
		var personIdStr="dimipersonId"+empIdStr.substring(11);
		if(empid == ''){
			obj.value=" ";
			document.getElementById("onck").href=encodeURI(encodeURI("/ar/attendanceMintenance/viewAddAffirmList?isEmployeement=1&firstFlag=1&limit=super&pageNum=1"
					+'&seach_KEY='+empid
					+'&empidStr='+empIdStr
					+'&personidStr='+personIdStr  
					));
			document.getElementById("onck").click();
		}else{
	   		$.ajax({
				type: 'POST',
				url: encodeURI('/sys/affirm/getPersonCntByEmpid?EMPID='+empid ),
				dataType:"json",
				cache: false,
				success: function(jsonObject){
					if(jsonObject.perCnt != 1 ){
						document.getElementById("onck").href=encodeURI(encodeURI("/ar/attendanceMintenance/viewAddAffirmList?isEmployeement=1&limit=super&pageNum=1"
								+'&seach_KEY='+empid
								+'&empidStr='+empIdStr
								+'&personidStr='+personIdStr
								));
						document.getElementById("onck").click();
					}
					if(jsonObject.perCnt==1){
					  	$("[id='dwz.person.dimiempName" + index + "']").val('['+jsonObject.empId + ']-'+jsonObject.empName);
					  	$("[id='dwz.person.dimipersonId" + index + "']").val( jsonObject.personId);
					}
				},
				error: DWZ.ajaxError
			});
		}
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
			alertMsg.error("请先设置审批者。");
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
										"forwardUrl":"/ess/dimissionApply/addDimissionView?flag=1&PERSON_ID=" + jsonObject.personId, 
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
	<form id="addDimissionInfoApplyP" method="post"
		action="/ess/dimissionApply/addDimissionInfoApply"
		class="pageForm required-validate"
		onsubmit="return validateCallback_pApply(this,navTabAjaxDone);">
	
		<div class="formBar">
			<ul>
				<li><div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">
								<spring:message code="public.title.submit" />
								<!-- 提交 -->
							</button>
						</div>
					</div>
				</li>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" class="close">
								<spring:message code="public.title.cancle" />
								<!-- 取消 -->
							</button>
						</div>
					</div></li>
			</ul>
		</div>
				<div>
					<table width="100%" border="0" cellpadding="0" cellspacing="0"
						class="user_table">
						<tr>
							<td width="20%" class="td_title" style="text-align:center">申请人</td>
							<td width="30%" class="td_type"><c:if
									test="${authority ne '1'}">
									<input id="empId_apply_b" name="dwz.person.empId1"
										value="${personInfo.EMPID}" type="hidden" />
						 
						${personInfo.EMPID}
						 </c:if> <c:if test="${authority eq '1'}">
									<input id="empId_apply" name="dwz.person.empId1"
										value="${personInfo.EMPID}" type="text" lookupGroup="person"
										onkeydown="submitKeyClick_apply(this,event)" class="required" />

								</c:if> <input id="empName_apply" name="empName_apply" type="hidden"
								value="${personInfo.LOCAL_NAME}" readonly
								style="border:0;background:transparent;" type="text"
								lookupGroup="person" />${personInfo.LOCAL_NAME}/${personInfo.DEPARTMENT}
								<input id="PERSON_ID" name="PERSON_ID" type="hidden" size="30"
								value="${personInfo.PERSON_ID}" lookupGroup="person" /> <!-- 隐藏的一些参数 -->

								<input id="APPLY_TYPE_NO" name="APPLY_TYPE_NO" type="hidden"
								value="218296" /> <input id="APPLY_TYPE" name="APPLY_TYPE"
								type="hidden" value="PERSON" /> <input id="AFFIRM_FLAG"
								name="AFFIRM_FLAG" type="hidden" value="" /></td>
								<td width="20%" class="td_title">人员类型/职级</td>
							<td width="30%" class="td_type">
						${personInfo.EMP_TYPE_NAME}/${personInfo.POST_GRADE_NO}</td>
						</tr>

						<tr>
							<td width="15%" class="td_title" style="text-align:center"><spring:message
									code="display.pa.ecc.expectresigndate" />
								<!--预离职日期-->
							</td>
							<td width="35%" class="td_type" colspan="3"><input
								type="text" id="APPLY_LEAVE_TIME" name="APPLY_LEAVE_TIME"
								class="date" format="yyyy-MM-dd" /> <a class="inputDateButton"><spring:message
										code="public.title.choose" />
									<!-- 选择 -->
							</a></td>
						</tr>
						<tr>
							<td width="15%" class="td_title" style="text-align:center"><spring:message
									code="pa.salarycode.affirm.reason" />
								<!--申请事由-->
							</td>
							<td width="35%" class="td_type" colspan="3">
								<textarea id="APPLY_REASON"  style="width:400px;height:100px"
									name="APPLY_REASON"></textarea>
							
								<input type="hidden" name="APPLY_NO" id="APPLY_NO"
									value="${APPLY_NO }" />
							</td>
						</tr>
					</table>
				</div>
				<div>
					<table width="100%" border="0" cellpadding="0" cellspacing="0"
						class="user_table">
						<tr>
							<td class="td_title" width="15%" rowspan="2" style="text-align:center">审批线</td>
							<td class="td_title" width="20%" style="text-align:center">审批等级</td>
							<td class="td_title" width="20%" style="text-align:center">审批者</td>
							<td class="td_title" width="20%" style="text-align:center">是否新增(<img src="/resources/images/+.gif" title="添加" border="0" align="absmiddle" style="cursor:hand" onclick="addRowByIDApplyDimiFirst()"/>)</td>
						</tr>
						<tr>
							<td colspan="3">
								<table width="100%" border="0" cellpadding="0" cellspacing="0"
									id="addAffirm_listEss2016">
									<tbody>
										<c:forEach items="${affirmorList}" var="affirmor"
											varStatus="j">
											<tr id="rowIdEss2016${j.index}">
												<td class="td_type" style="text-align: center" width="33%">
													${j.count}</td>
												<td class="td_type" style="text-align: center" width="33%">
													[${affirmor.EMPID}]-${affirmor.LOCAL_NAME } <input
													type="hidden" name="AFFIRMOR_ID"
													value="${affirmor.AFFIRMOR_ID }" /></td>
												<td class="td_type" style="text-align: center" width="33%">
														<img src="/resources/images/+.gif" title="添加" border="0"
															align="absmiddle" style="cursor:hand"
															onclick="addRowByIDEss2016(${j.index})" />
													</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
								</td>
						</tr>
					</table>
				</div>
										<a id="onck" name="onck"  href="" lookupGroup="person"></a>
			<input type="hidden" name="affirmCountDimi" id="affirmCountDimi"
				value="${affirmorListCnt }">
	</form>
</div>