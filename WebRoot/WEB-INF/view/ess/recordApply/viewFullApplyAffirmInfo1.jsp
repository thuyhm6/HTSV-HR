<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script type="text/javascript">
function validateBatchArMacAffirmCallback(form,callback) {	
	var $form = $(form);	
	if (!$form.valid()) {
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
//注意input的id和tr的id要一样
function addRowByIDArMacTwoBatch(currentRowID){
	var count = parseInt($("#affirmListCnt").val());
	str = '<tr id = "rowIdApplyBatchArMac'+count+'">'
        	+'<td style="text-align: center"></td>'
			+'<td style="text-align: center">'
				+'<input id="dwz.person.ArMapersonId'+count+'" name="AFFIRMOR_ID" value="" type="hidden" lookupGroup="person"/>'
				+'<input id="dwz.person.ArMaempName'+count+'" name="empid" value="" type="text" lookupGroup="person" '
				+'	onkeydown="submitKeyClick_affirmor_ArMac(this,' + count + ',event)" class="required"/>'
			+'</td>'
			+'<td style="text-align: center">'
				+'<img src="/resources/images/+.gif" style="cursor:hand" title="添加" onclick="addRowByIDArMacTwoBatch(' + count + ');"/>&nbsp;&nbsp;&nbsp;'
				+'<img src="/resources/images/-.gif" style="cursor:hand" title="删除" '
				//先删除，再排序
				+'	onclick="javaScript:document.all.addApplyBatchArMacAffirm_list.deleteRow(event.srcElement.parentElement.parentElement.rowIndex);changeApplyBatchOtLevel();"/>'
			+'</td>'
		+'</tr>';
	//当前行之后插入一行
	$("#rowIdApplyBatchArMac" + currentRowID).after(str);
	$("[id='dwz.person.ArMaempName" + count + "']").attr("alt","请输入关键字按回车检索").attr("size","30").inputAlert();
	changeApplyBatchOtLevel();
	$("#affirmListCnt").val(++count) ;
}


function addRowByIDApplyBatchArMacFirst(){
	var count = parseInt($("#affirmListCnt").val());
	str = '<tr id = "rowIdApplyBatchArMac'+count+'">'
      	+'<td style="text-align: center"></td>'
			+'<td style="text-align: center">'
				+'<input id="dwz.person.ArMapersonId'+count+'" name="AFFIRMOR_ID" value="" type="hidden" lookupGroup="person"/>'
				+'<input id="dwz.person.ArMaempName'+count+'" name="empid" value="" type="text" lookupGroup="person" '
				+'	onkeydown="submitKeyClick_affirmor_ArMac(this,' + count + ',event)" class="required"/>'
			+'</td>'
			+'<td style="text-align: center">'
					+'<img src="/resources/images/+.gif" style="cursor:hand" title="添加" onclick="addRowByIDArMacTwoBatch(' + count + ');"/>&nbsp;&nbsp;&nbsp;'
					+'<img src="/resources/images/-.gif" style="cursor:hand" title="删除" '
					//先删除，再排序
					+'	onclick="javaScript:document.all.addApplyBatchArMacAffirm_list.deleteRow(event.srcElement.parentElement.parentElement.rowIndex);changeApplyBatchOtLevel();"/>'
			+'</td>'
		+'</tr>';

	var tb2 = document.getElementById("addApplyBatchArMacAffirm_list");

   	if(tb2.rows.length == 1){
   		$("#addApplyBatchArMacAffirm_list").append(str);
   	} else {
   	 	//当前行之后插入一行
   	 	$("#" + tb2.rows[1].id).before(str);
   	}
	$("[id='dwz.person.ArMaempName" + count + "']").attr("alt","请输入关键字按回车检索").attr("size","30").inputAlert();
	changeApplyBatchOtLevel();
	$("#affirmListCnt").val(++count) ;
}

//修改决裁者等级
function changeApplyBatchOtLevel(){
	var tb2 = document.getElementById("addApplyBatchArMacAffirm_list");
	var rowCount = tb2.rows.length;
	for(var m=1;m<rowCount;m++){
		tb2.rows[m].cells[0].innerHTML = m;
	}
}

var keyCodeInit=0;
function submitKeyClick_affirmor_ArMac(obj,index,event){
	var e= event ? event : window.event; 
	var keyCode = e.which ? e.which : e.keyCode;
 	if(keyCode==13){
 		keyCodeInit=keyCode;
		var empid=obj.value.replace(/[ ]/g,"");
		var empIdStr=obj.id.substring(11);
		var personIdStr="ArMapersonId"+empIdStr.substring(11);
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
					  	$("[id='dwz.person.ArMaempName" + index + "']").val('['+jsonObject.empId + ']-'+jsonObject.empName);
					  	$("[id='dwz.person.ArMapersonId" + index + "']").val( jsonObject.personId);
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

function uploadAttWindow(divId,applyNo,applyType){
	$.pdialog.open("/sys/notice/uploadWindow?APPLY_NO=" + applyNo + "&APPLY_TYPE=" + applyType + "&divId=" + divId, "uploadWindow", "附件上传", {width:550,height:320,mask:true});
}
</script>
<div class="pageContent">
	<table class="table" width="100%" layoutH="240" nowrapTD="false">
		<thead>
			<tr>
				<th>社号</th>
				<th>姓名</th>
				<th>本月申请次数</th>
				<th>考勤日期</th>
				<th>打卡时间</th>
				<th>进出门类型</th>
				<th>备注</th>
				<th>附件</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${arMacBatchAffirmList}" var="item" varStatus="i">			
				<tr>
					<td class='td_center'>${item.EMPID}</td>
					<td class='td_center'>${item.LOCAL_NAME}</td>
					<td class='td_center'>${item.MAC_APPLY_CNT}</td>
					<td class='td_center'>${item.APPLY_DATE}</td>
					<td class='td_center'>${item.APPLY_TIME}</td>
					<td class='td_center'>${item.DOOR_TYPE}</td>
					<td style="text-align:left">${item.REMARK}</td>
					<td style="text-align:left;width:16%" id="arCardBatchUpload_${i.index}">
							<c:forEach items="${item.fileList}" var="file" varStatus="j">	
								&nbsp;&nbsp;<a href="/ess/infoApplyLeave/downloadFile?fileName=${file.FILE_URL }&file=${file.FILE_NAME}" >${file.FILE_NAME}</a>;
							</c:forEach>
						&nbsp;&nbsp;<img src="/resources/images/+.gif" title="上传" border="0" align="absmiddle" style="cursor:hand" onclick="uploadAttWindow('arCardBatchUpload_${i.index}',${item.RECORD_NO },218294)"/>
					</td>
				</tr>			
			</c:forEach>			
		</tbody>
	</table>
	<c:set value="/ess/recordApply/viewFullApplyAffirmInfo1?seach_APPLY_TYPE_NO=218294&seach_APPLY_NO=${arMacRecord.RECORD_NO}" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>
<div class="pageContent" >
	<form id="modifyAffirmorForBatchArMac" method="post" action="/ess/recordApply/modifyAffirmorForBatchArMac" class="pageForm required-validate" 
		onsubmit="return validateBatchArMacAffirmCallback(this,navTabAjaxDone);">
	<table class="user_table" width="100%" border="0">
		<tr>
			<td class="td_title" style="text-align: center;width:10%;"><!-- 决裁线 -->
					决裁线
			</td>
			<td colspan="3" style="text-align: center;width:90%;">
				<table class="user_table" width="100%" id="addApplyBatchArMacAffirm_list">
					<tr>
									<td class="td_title" style="text-align:center;" width="11%">决裁等级</td>
									<td class="td_title" style="text-align:center;" width="44%">决裁者</td>
									<td class="td_title" style="text-align:center;" width="44%">是否新增(<img src="/resources/images/+.gif" title="添加" border="0" align="absmiddle" style="cursor:hand" onclick="addRowByIDApplyBatchArMacFirst()"/>)</td>
					</tr>
								<c:forEach items="${affirmorList}" var="affirmor" varStatus="i">			
									<tr id="rowIdApplyBatchArMac${i.index }">
										<td class="td_type" style="text-align: center">${affirmor.AFFIRM_LEVEL}</td>
										<td class="td_type" style="text-align: center">[${affirmor.EMPID}]-${affirmor.LOCAL_NAME}<input type="hidden" name="AFFIRMOR_ID" value="${affirmor.AFFIRMOR_ID}"/></td>
										<td class="td_type" style="text-align: center"><img src="/resources/images/+.gif" title="添加" border="0" align="absmiddle" style="cursor:hand" onclick="addRowByIDArMacTwoBatch(${i.index })"/></td>
									</tr>			
								</c:forEach>
				</table>
							<input type="hidden" id="affirmListCnt" name="affirmListCnt" value="${affirmorListCnt }"/>
							<input type="hidden" name="APPLY_NO" id="APPLY_NO" value="${APPLY_NO }">
							<input type="hidden" name="APPLY_TYPE" id="APPLY_TYPE" value="218294">
							<a id="onck" name="onck"  href="" lookupGroup="person"></a>
			</td>
		</tr>
	</table>
		<div class="formBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">
								保存
							</button>
						</div>
					</div>
				</li>
			</ul>
		</div>   
	</form>
</div>