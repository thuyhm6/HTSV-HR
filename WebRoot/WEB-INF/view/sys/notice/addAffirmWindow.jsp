<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
function validateCallbackuploadWindow(form,callback) {	
	var $form = $(form);	
	$.ajax({
		type: form.method || 'POST',
		url:$form.attr("action"),
		data:$form.serializeArray(),
		dataType:"json",
		cache: false,
		success: function(data){
			var divIdStr = $("#divIdStr").val();
			var oldHtml = $("#" + divIdStr).html();
			var newHtml = "";

			var fileUrl = $("#fileUrl_upload").val();
			var fileUrlStrs = new Array(); //定义一数组 
			fileUrlStrs = fileUrl.split(";");


			var fileName = $("#fileName_upload").val(fileName);
			var fileNameStrs = new Array(); //定义一数组 
			fileNameStrs = fileName.split(";");
			for (i=0;i<fileUrlStrs.length ;i++ ) { 
				newHtml += "&nbsp;&nbsp;<a href=\"/ess/infoApplyCwa/downloadFile?fileName=/resources/temp/apply/applyCwa/" + fileUrlStrs[i] + "&file=" + fileNameStrs[i] + "\" >" + fileNameStrs[i] + "</a>;";
			} 
			
			$("#" + divIdStr).html(newHtml + oldHtml);
			alertMsg.info("上传成功"); 
			
			$.pdialog.closeCurrent();
		},
		error: DWZ.ajaxError
	});	
	return false;
}
//注意input的id和tr的id要一样
function addRowByIDCwaTwoBatch(currentRowID){
	var count = parseInt($("#affirmListCnt").val());
	str = '<tr id = "rowIdApplyBatchCwa'+count+'">'
        	+'<td style="text-align: center"></td>'
			+'<td style="text-align: center">'
				+'<input id="dwz.person.bacwpersonId'+count+'" name="AFFIRMOR_ID" value="" type="hidden" lookupGroup="person"/>'
				+'<input id="dwz.person.bacwempName'+count+'" name="empid" value="" type="text" lookupGroup="person" '
				+'	onkeydown="submitKeyClick_affirmor_Cwa(this,' + count + ',event)" class="required"/>'
			+'</td>'
			+'<td style="text-align: center">'
				+'<img src="/resources/images/+.gif" style="cursor:hand" title="添加" onclick="addRowByIDCwaTwoBatch(' + count + ');"/>&nbsp;&nbsp;&nbsp;'
				+'<img src="/resources/images/-.gif" style="cursor:hand" title="删除" '
				//先删除，再排序
				+'	onclick="javaScript:document.all.addApplyBatchCwaAffirm_list.deleteRow(event.srcElement.parentElement.parentElement.rowIndex);changeApplyBatchCwaLevel();"/>'
			+'</td>'
		+'</tr>';
	//当前行之后插入一行
	$("#rowIdApplyBatchCwa" + currentRowID).after(str);
	$("[id='dwz.person.bacwempName" + count + "']").attr("alt","请输入关键字按回车检索").attr("size","30").inputAlert();
	changeApplyBatchCwaLevel();
	$("#affirmListCnt").val(++count) ;
}


function addRowByIDApplyBatchLCwaFirst(){
	var count = parseInt($("#affirmListCnt").val());
	str = '<tr id = "rowIdApplyBatchCwa'+count+'">'
      	+'<td style="text-align: center"></td>'
			+'<td style="text-align: center">'
				+'<input id="dwz.person.bacwpersonId'+count+'" name="AFFIRMOR_ID" value="" type="hidden" lookupGroup="person"/>'
				+'<input id="dwz.person.bacwempName'+count+'" name="empid" value="" type="text" lookupGroup="person" '
				+'	onkeydown="submitKeyClick_affirmor_Cwa(this,' + count + ',event)" class="required"/>'
			+'</td>'
			+'<td style="text-align: center">'
					+'<img src="/resources/images/+.gif" style="cursor:hand" title="添加" onclick="addRowByIDCwaTwoBatch(' + count + ');"/>&nbsp;&nbsp;&nbsp;'
					+'<img src="/resources/images/-.gif" style="cursor:hand" title="删除" '
					//先删除，再排序
					+'	onclick="javaScript:document.all.addApplyBatchCwaAffirm_list.deleteRow(event.srcElement.parentElement.parentElement.rowIndex);changeApplyBatchCwaLevel();"/>'
			+'</td>'
		+'</tr>';

	var tb2 = document.getElementById("addApplyBatchCwaAffirm_list");

   	if(tb2.rows.length == 1){
   		$("#addApplyBatchCwaAffirm_list").append(str);
   	} else {
   	 	//当前行之后插入一行
   	 	$("#" + tb2.rows[1].id).before(str);
   	}
	$("[id='dwz.person.bacwempName" + count + "']").attr("alt","请输入关键字按回车检索").attr("size","30").inputAlert();
	changeApplyBatchCwaLevel();
	$("#affirmListCnt").val(++count) ;
}

//修改决裁者等级
function changeApplyBatchCwaLevel(){
	var tb2 = document.getElementById("addApplyBatchCwaAffirm_list");
	var rowCount = tb2.rows.length;
	for(var m=1;m<rowCount;m++){
		tb2.rows[m].cells[0].innerHTML = m;
	}
}

var keyCodeInit=0;
function submitKeyClick_affirmor_Cwa(obj,index,event){
	var e= event ? event : window.event; 
	var keyCode = e.which ? e.which : e.keyCode;
 	if(keyCode==13){
 		keyCodeInit=keyCode;
		var empid=obj.value.replace(/[ ]/g,"");
		var empIdStr=obj.id.substring(11);
		var personIdStr="bacwpersonId"+empIdStr.substring(11);
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
					  	$("[id='dwz.person.bacwempName" + index + "']").val('['+jsonObject.empId + ']-'+jsonObject.empName);
					  	$("[id='dwz.person.bacwpersonId" + index + "']").val( jsonObject.personId);
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

function saveAffirm(){
	var divIdStr = $("#divIdStr").val();
	var affirmIdStr = $("#affirmIdStr").val();

	var affirmIdArray = "";
	var divIdArray = "";
	$("input[name='AFFIRMOR_ID']").each( function(index){
		affirmIdArray += ($(this).val()) + ";";  
	}); 
	$("input[name='empid']").each( function(index){
		divIdArray += (index+1) + "、" + ($(this).val()) + "</br>"; 
	}); 
	divIdArray += "(<img src=\"/resources/images/+.gif\" title=\"添加\" border=\"0\" align=\"absmiddle\" style=\"cursor:hand\" onclick=\"modifyCwaAffirmList('" + divIdStr + "','" + affirmIdStr + "'," + $("#PERSON_ID").val() + ")\"/>)";
	$("#" + divIdStr).html(divIdArray);
	$("#" + affirmIdStr).val(affirmIdArray);
	$.pdialog.closeCurrent();
}
</script>
<div class="pageContent">
	<table class="user_table" width="100%" border="0">
		<tr>
			<td class="td_title" style="text-align: center;width:10%;"><!-- 决裁线 -->
					决裁线
				<input type="hidden" id="divIdStr" name="divIdStr" value="${divId}"/> 
				<input type="hidden" id="affirmIdStr" name="affirmIdStr" value="${affirmIdStr}"/> 
				<input type="hidden" id="PERSON_ID" name="PERSON_ID" value="${PERSON_ID}"/> 
			</td>
			<td colspan="3" style="text-align: center;width:90%;">
				<table class="user_table" width="100%" id="addApplyBatchCwaAffirm_list">
					<tr>
									<td class="td_title" style="text-align:center;" width="11%">决裁等级</td>
									<td class="td_title" style="text-align:center;" width="44%">决裁者</td>
									<td class="td_title" style="text-align:center;" width="44%">是否新增(<img src="/resources/images/+.gif" title="添加" border="0" align="absmiddle" style="cursor:hand" onclick="addRowByIDApplyBatchLCwaFirst()"/>)</td>
					</tr>
								<c:forEach items="${affirmorList}" var="affirmor" varStatus="i">			
									<tr id="rowIdApplyBatchCwa${i.index }">
										<td class="td_type" style="text-align: center">${affirmor.AFFIRM_LEVEL}</td>
										<td class="td_type" style="text-align: center">[${affirmor.EMPID}]-${affirmor.LOCAL_NAME}<input type="hidden" name="AFFIRMOR_ID" value="${affirmor.AFFIRMOR_ID}"/><input type="hidden" name="empid" value="[${affirmor.EMPID}]-${affirmor.LOCAL_NAME}"/></td>
										<td class="td_type" style="text-align: center"><img src="/resources/images/+.gif" title="添加" border="0" align="absmiddle" style="cursor:hand" onclick="addRowByIDCwaTwoBatch(${i.index })"/></td>
									</tr>			
								</c:forEach>
				</table>
				<input type="hidden" id="affirmListCnt" name="affirmListCnt" value="${fn:length(affirmorList) }"/>
				<a id="onck" name="onck"  href="" lookupGroup="person"></a>
			</td>
		</tr>
	</table>		
	<div class="formBar">
		<ul>
			<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="saveAffirm()">保存</button></div></div></li>
			<li><div class="buttonActive"><div class="buttonContent"><button type="button" class="close">关闭</button></div></div></li>
		</ul>
	</div>
</div>