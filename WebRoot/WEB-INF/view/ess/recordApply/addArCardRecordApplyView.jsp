<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script type="text/javascript">

//注意input的id和tr的id要一样
function addAffirmorRow(currentRowID){
	var count = parseInt($("#affirmCountCard").val());
        	str = '<tr id = "rowIdApplyCard'+count+'">'
	            	+'<td style="text-align: center"></td>'
        			+'<td style="text-align: center">'
						+'<input id="dwz.person.cardpersonId'+count+'" name="AFFIRMOR_ID" value="" type="hidden" lookupGroup="person"/>'
						+'<input id="dwz.person.cardempName'+count+'" name="empid" value="" type="text" lookupGroup="person" '
						+'	onkeydown="submitKeyClick_affirmor_card(this,' + count + ',event)" class="required"/>'
        			+'</td>'
        			+'<td style="text-align: center">'
    					+'<img src="/resources/images/+.gif" style="cursor:hand" title="添加" onclick="addAffirmorRow(' + count + ');"/>&nbsp;&nbsp;&nbsp;'
    					+'<img src="/resources/images/-.gif" style="cursor:hand" title="删除" '
    					//先删除，再排序
    					+'	onclick="javaScript:document.all.affirmor_list.deleteRow(event.srcElement.parentElement.parentElement.rowIndex);changeApplyCardLevel();"/>'
					+'</td>'
				+'</tr>';
  //当前行之后插入一行
 	$("#rowIdApplyCard" + currentRowID).after(str);
	$("[id='dwz.person.cardempName" + count + "']").attr("alt","请输入关键字按回车检索").attr("size","30").inputAlert();
 	changeApplyCardLevel();
	$("#affirmCountCard").val(++count) ;
}

function addRowByIDApplyCardFirst(){
	var count = parseInt($("#affirmCountCard").val());
	str = '<tr id = "rowIdApplyCard'+count+'">'
      	+'<td style="text-align: center"></td>'
			+'<td style="text-align: center">'
				+'<input id="dwz.person.cardpersonId'+count+'" name="AFFIRMOR_ID" value="" type="hidden" lookupGroup="person"/>'
				+'<input id="dwz.person.cardempName'+count+'" name="empid" value="" type="text" lookupGroup="person" '
				+'	onkeydown="submitKeyClick_affirmor_card(this,' + count + ',event)" class="required"/>'
			+'</td>'
			+'<td style="text-align: center">'
					+'<img src="/resources/images/+.gif" style="cursor:hand" title="添加" onclick="addAffirmorRow(' + count + ');"/>&nbsp;&nbsp;&nbsp;'
					+'<img src="/resources/images/-.gif" style="cursor:hand" title="删除" '
					//先删除，再排序
					+'	onclick="javaScript:document.all.affirmor_list.deleteRow(event.srcElement.parentElement.parentElement.rowIndex);changeApplyCardLevel();"/>'
			+'</td>'
		+'</tr>';

	var tb2 = document.getElementById("affirmor_list");
   	if(tb2.rows.length == 1){
   		$("#affirmor_list").append(str);
   	} else{
 		//当前行之后插入一行
 		$("#" + tb2.rows[1].id).before(str);
   	}
	$("[id='dwz.person.cardempName" + count + "']").attr("alt","请输入关键字按回车检索").attr("size","30").inputAlert();
 	changeApplyCardLevel();
	$("#affirmCountCard").val(++count) ;
}

//修改决裁者等级
function changeApplyCardLevel(){
	var tb2 = document.getElementById("affirmor_list");
	var rowCount = tb2.rows.length;
	for(var m=1;m<rowCount;m++){
		tb2.rows[m].cells[0].innerHTML = m;
	}
}

var keyCodeInit=0;
function submitKeyClick_affirmor_card(obj,index,event){
	var localName = '';
	var idcardNo = '';
	var navTabId = '';
	var e= event ? event : window.event; 
	var keyCode = e.which ? e.which : e.keyCode;
 	if(keyCode==13){
 		keyCodeInit=keyCode;
		var empid=obj.value.replace(/[ ]/g,"");
		var empIdStr=obj.id.substring(11);
		var personIdStr="cardpersonId"+empIdStr.substring(11);
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
					  	$("[id='dwz.person.cardempName" + index + "']").val('['+jsonObject.empId + ']-'+jsonObject.empName);
					  	$("[id='dwz.person.cardpersonId" + index + "']").val( jsonObject.personId);
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
}; 

function validateArCardRecordApplyCallback(form,callback) {	
	var $form = $(form);	
	if (!$form.valid()) {
		return false;
	}

	var rDate = $form.find("#R_DATE").val();
	var rHour = $form.find("#R_HOUR").val();
	var rMinute = $form.find("#R_MINUTE").val();
	var applyType = $form.find("#DOOR_TYPE").val();

  if(rDate==""){
	       alertMsg.error("请选择进出门的日期！");
	       return false;
  }
  if(rHour=="" || rMinute == ""){
	       alertMsg.error("请输入正确的漏刷卡时间！");
	       return false;
  }
  if(applyType==""){
	       alertMsg.error("请选择进/出门类型！");
	       return false;
	}

	alertMsg.confirm("确认进行进出门刷卡申请？", {
		okCall:function() {  
		$.ajax({
			type: form.method || 'POST',
			url:$form.attr("action"),
			data:$form.serializeArray(),
			dataType:"json",
			cache: false,
			success: callback || DWZ.ajaxDone,
			error: DWZ.ajaxError
		});		
	}});
	return false;
}

function submitKeyClick_arCardRecord(obj,event){
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
		var empNameStr="empId_apply";
 		$.ajax({
			type: 'POST',
			url: encodeURI('/sys/affirm/getPersonCnt?limit=ar&navTabId=' + navTabId + '&EMPID='+empid+'&LOCAL_NAME='+localName+'&IDCARD_NO='+idcardNo),
			dataType:"json",
			cache: false,
			success: function(jsonObject){
						if (jsonObject.perCnt==0){
							alertMsg.error('<spring:message code="alert.message.sys.affirm.searchNoPersonAuthority"/>');
						}
						if(jsonObject.perCnt>1 ){
							alertMsg.error('请填写准确工号！');
						}
						if(jsonObject.perCnt==1){
							document.getElementById(empIdStr).value=jsonObject.empId;
							document.getElementById(personIdStr).value=jsonObject.personId;
							document.getElementById(empNameStr).value='['+jsonObject.empId + ']-'+jsonObject.empName;
							if(jsonObject.perCnt==1){
								uploadfy_destory();
								$.pdialog.reload("/ess/recordApply/addArCardRecordApplyView?APPLY_TYPE_NO=218294&BIAO=A&" + "navTabId=arMacRecordApply" + "&PERSON_ID=" + jsonObject.personId);
							}
						}
					},
			error: DWZ.ajaxError
		});
  }
}

function uploadifySuccess_arMacRec(file, data, response){
	  //获取后台返回到前台的文件名，添加到隐藏域,多文件用";"号隔开
	  var files = $("#fileNmae",$.pdialog.getCurrent()).html();
	  var fileUrl = $("#fileUrl",$.pdialog.getCurrent()).val();
	  var fileName = $("#fileName",$.pdialog.getCurrent()).val();
	  var fileResult = data.split(";");
	  //第一个文件
	  if(files==""){
	    files = fileResult[0];
	    fileName = fileResult[0];
	    fileUrl = fileResult[1];
	  }else{
	    files+=";"+fileResult[0];
	    fileName+=";"+fileResult[0];
	    fileUrl+=";"+fileResult[1];
	  }
	  $("#fileNmae",$.pdialog.getCurrent()).html(files);
	  $("#fileUrl",$.pdialog.getCurrent()).val(fileUrl);
	  $("#fileName",$.pdialog.getCurrent()).val(fileName);
}

function changeTime(doorType){
	var cpnyId = '${defaultCpny}';
	if(cpnyId == 'LGEKS'){
		if(doorType == 'IN'){
			$("#R_HOUR").html($("#R_HOUR").html().replaceAll("17:00","08:00").replaceAll("11:20","12:20").replaceAll("05:00","20:00").replaceAll("23:20","00:20"));
			$("#R_HOUR").val("");
		}else{
			$("#R_HOUR").html($("#R_HOUR").html().replaceAll("08:00","17:00").replaceAll("12:20","11:20").replaceAll("20:00","05:00").replaceAll("00:20","23:20"));
			$("#R_HOUR").val("");
		}
	}
}
</script>
<div class="pageContent" layoutH="10">
	<div>
		<form id="addArCardRecordApplyView" method="post" action="/ess/recordApply/addArMacRecordApply" class="pageForm required-validate" 
			onsubmit="return validateArCardRecordApplyCallback(this,dialogAjaxDone);">
			<div class="formBar">
				<ul>
					<li>
					<div class="buttonActive">
					<div class="buttonContent">
					<button type="submit"><spring:message
						code="public.title.submit" /><!-- 提交 --></button>
					</div>
					</div>
					</li>
				</ul>
			</div>
			<div>
			<table class="user_table" width="100%" border="1" cellpadding="2" cellspacing="1">
				<tr>
					<td width="20%" class="td_title"><spring:message code="public.title.empIdAndName"/><!-- 工号/姓名 --></td>
					<td width="30%" class="td_type">
						<c:if test="${authority ne '1'}">
								${personInfo.EMPID} / ${personInfo.LOCAL_NAME}
						</c:if>
						<c:if test="${authority eq '1'}">
							<input id="empId_apply" name="empid"
								value="${personInfo.EMPID}" type="text" lookupGroup="person"
								onkeydown="submitKeyClick_arCardRecord(this,event)" class="required" />
							<input id="empName_apply" name="dwz.person.empName1"
								value="${personInfo.LOCAL_NAME}" readonly
								style="border: 0; background: transparent;" type="text"
								lookupGroup="person" rel="arMacRecordApply_lookUp"/>
						</c:if> 
						<input id="PERSON_ID" name="PERSON_ID" type="hidden" size="30" value="${personInfo.PERSON_ID}" />
		                <input id="RECORD_NO" name="RECORD_NO" type="hidden" size="30" value="${arMacRecord.RECORD_NO}" />   
					</td>
					<td width="20%" class="td_title">漏刷卡日期</td>
					<td width="30%" class="td_type">
					    <input type="text" id="R_DATE" name="R_DATE" class="date required" format="yyyy-MM-dd" readonly="true" value="${R_DATE}"/>
					    <a class="inputDateButton" href="javascript:;"><spring:message code="public.title.choose"/><!-- 选择 --></a>
					</td>
				</tr>
				<tr>
				   	<td class="td_title">打卡时间</td>
					<td class="td_type">
							<c:if test="${defaultCpny eq 'LGEKS'}">	
								<select id="R_HOUR" name="R_HOUR">
									<option value=""><!--请选择:-->
										<spring:message code="pa.insurance.title.pleaseChoose"/>
									</option>
									<option value="08:00">08:00</option>
									<option value="12:20">12:20</option>
									<option value="20:00">20:00</option>
									<option value="00:20">00:20</option>
								</select>  
							    <input type="hidden" id="R_MINUTE" name="R_MINUTE" value="00" />
							</c:if>
							<c:if test="${defaultCpny ne 'LGEKS'}">	
							    <input type="text" id="R_HOUR" name="R_HOUR" class="required" value="${R_HOUR}" min="0" max="23" size="7">点
							    <input type="text" id="R_MINUTE" name="R_MINUTE" class="required" value="${R_MINUTE}" min="0" max="59" size="7">分
							    (<font color="red">例子:08点30分</font>)
							</c:if>
					</td>
					<td class="td_title">上/下班类型</td>
					<td class="td_type">
						<input id="APPLY_TYPE_NO" name="APPLY_TYPE_NO" type="hidden" size="30" value="218294" />		
						<select id="DOOR_TYPE" name="DOOR_TYPE" onchange="changeTime(this.value)">
							<option value=""><!--请选择:-->
								<spring:message code="pa.insurance.title.pleaseChoose"/>
							</option>
							<option value="IN"  >进门(IN)</option>
							<option value="OUT" >出门(OUT)</option>
						</select>  
					 	<input type="hidden" id="APPLY_DATE" name="APPLY_DATE" value="${APPLY_DATE}"/>
					</td>
				</tr>
				<c:if test="${defaultCpny ne 'LGEPN'}">
					<tr>
					    <td class="td_title">本月漏刷卡次数</td>
						<td class="td_type" colspan="3">
							${arGetMacApplyCnt }
						</td>
					</tr>
				</c:if>
				<c:if test="${defaultCpny eq 'LGEPN'}">
					<tr>
					    <td class="td_title">本月漏刷卡次数</td>
						<td class="td_type">
							${arGetMacApplyCnt }
						</td>
					   <td class="td_title">漏刷卡类型</td>
						<td class="td_type">
						<select id="RECORD_TYPE" name="RECORD_TYPE">
							<option value="E">忘记打卡(无凭证)</option>
							<option value="E1">忘记打卡(有凭证)</option>
							<option value="C">因公未打卡</option>
						</select>
						<font color="red">(请慎重选择。)</font>
						</td>
					</tr>
				</c:if>
				
								<tr>
					<td class="td_title">申请事由</td>
				    <td class="td_type">
				    	<textarea name="REMARK"  style="width:400px;height:100px"></textarea>
									<td width="20%" class="td_title">
										附件上传
									</td>
									<td width="80%" class="td_type">
									    <input id="testFileInput_arMacRec" type="file" name="file" 
												uploaderOption="{
													swf:'/resources/js/uploadify/scripts/uploadify.swf',
													uploader:'/ess/infoApplyLeave/uploadBatch?PERSON_ID=${personInfo.PERSON_ID}',
													formData:{ajax:1},
													queueID:'fileQueue_arMacRec',
													buttonText:'请选择',
													height:25,
													width:50,
													auto:false,
													onUploadSuccess:uploadifySuccess_arMacRec,
													removeTimeout:1
												}"
											/><span id="fileNmae">${leaveApplyMap.FILE_NAME}</span>
										  <div id="fileQueue_arMacRec" class="fileQueue"></div>
										  <input type="hidden" id="fileUrl" name="fileUrl" value="${leaveApplyMap.FILE_URL}"/>
										  <input type="hidden" id="fileName" name="fileName" value="${leaveApplyMap.FILE_NAME}"/>
											<div class="buttonActive">
												<div class="buttonContent"><!--保存-->
													<button type="button" onclick="$('#testFileInput_arMacRec').uploadify('upload', '*');return false;">
														上传
													</button>
												</div>
											</div>
											<div class="buttonActive">
												<div class="buttonContent"><!--提交-->
													<button type="button" onclick="$('#testFileInput_arMacRec').uploadify('cancel', '*');return false;">
														取消
													</button>
												</div>
											</div>
									</td>
								</tr>	
				<tr>
				</tr>
				<tr>
				  <td colspan="4" >
				    <table width="100%"  border="0" cellpadding="0" cellspacing="0" class="user_table margin_b" >
				      	<tr>
							<td class="td_title" width="20%" style="text-align: center"><!-- 决裁线 -->
							决裁线
							</td>
							<td width="80%" colspan="7">
								<table width="100%" border="0" cellpadding="0" cellspacing="0" id="affirmor_list">
										<tr>
											<td class="td_title" style="text-align:center;" width="33%">决裁等级</td>
											<td class="td_title" style="text-align:center;" width="33%">决裁者</td>
											<td class="td_title" style="text-align:center;" width="34%">是否新增(<img src="/resources/images/+.gif" title="添加" border="0" align="absmiddle" style="cursor:hand" onclick="addRowByIDApplyCardFirst()"/>)
											</td>
										</tr>
										<c:forEach items="${affirmorList}" var="affirmor" varStatus="j">			
											<tr id="rowIdApplyCard${j.index }">
												<td class="td_type" style="text-align: center">${affirmor.AFFIRM_LEVEL}</td>
												<td class="td_type"style="text-align: center">
													[${affirmor.EMPID}]-${affirmor.LOCAL_NAME}
													<input type="hidden" name="AFFIRMOR_ID" value="${affirmor.AFFIRMOR_ID}"/>
												</td>
												<td class="td_type" style="text-align: center">
													<%-- 如果是自己决裁时，且未决裁时，允许添加决裁者 --%>
														<img id="${affirmor.AFFIRMOR_ID}" src="/resources/images/+.gif" title="添加决裁者"
															border="0" align="absmiddle" style="cursor:hand" onclick="addAffirmorRow(${j.index })"/>
												</td>
											</tr>			
										</c:forEach>
								</table>
								<a id="onck" name="onck"  href="" lookupGroup="person" rel="submitKeyClick_apply_mac_affirm"></a>
							</td>
						</tr>
			        </table>
				  </td>
				</tr>
			</table>	
			</div>
    		<input type="hidden" name="affirmCountCard" id="affirmCountCard" value="${affirmorListCnt }">
  		</form>	
	</div>
</div>