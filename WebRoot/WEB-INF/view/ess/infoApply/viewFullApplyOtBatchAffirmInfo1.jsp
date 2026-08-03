<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script type="text/javascript">
function validateBatchOtAffirmCallback(form,callback) {	
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
function addRowByIDLTwoBatch(currentRowID){
	var count = parseInt($("#affirmorListCnt").val());
	str = '<tr id = "rowIdApplyBatchLot'+count+'">'
        	+'<td style="text-align: center"></td>'
			+'<td style="text-align: center">'
				+'<input id="dwz.person.baotpersonId'+count+'" name="AFFIRMOR_ID" value="" type="hidden" lookupGroup="person"/>'
				+'<input id="dwz.person.baotempName'+count+'" name="empid" value="" type="text" lookupGroup="person" '
				+'	onkeydown="submitKeyClick_affirmor_baot(this,' + count + ',event)" class="required"/>'
			+'</td>'
			+'<td style="text-align: center">'
				+'<img src="/resources/images/+.gif" style="cursor:hand" title="添加" onclick="addRowByIDLTwoBatch(' + count + ');"/>&nbsp;&nbsp;&nbsp;'
				+'<img src="/resources/images/-.gif" style="cursor:hand" title="删除" '
				//先删除，再排序
				+'	onclick="javaScript:document.all.addApplyBatchLOTAffirm_list.deleteRow(event.srcElement.parentElement.parentElement.rowIndex);changeApplyBatchOtLevel();"/>'
			+'</td>'
		+'</tr>';
	//当前行之后插入一行
	$("#rowIdApplyBatchLot" + currentRowID).after(str);
	$("[id='dwz.person.baotempName" + count + "']").attr("alt","请输入关键字按回车检索").attr("size","30").inputAlert();
	changeApplyBatchOtLevel();
	$("#affirmorListCnt").val(++count) ;
}


function addRowByIDApplyBatchLOTFirst(){
	var count = parseInt($("#affirmorListCnt").val());
	str = '<tr id = "rowIdApplyBatchLot'+count+'">'
      	+'<td style="text-align: center"></td>'
			+'<td style="text-align: center">'
				+'<input id="dwz.person.baotpersonId'+count+'" name="AFFIRMOR_ID" value="" type="hidden" lookupGroup="person"/>'
				+'<input id="dwz.person.baotempName'+count+'" name="empid" value="" type="text" lookupGroup="person" '
				+'	onkeydown="submitKeyClick_affirmor_baot(this,' + count + ',event)" class="required"/>'
			+'</td>'
			+'<td style="text-align: center">'
					+'<img src="/resources/images/+.gif" style="cursor:hand" title="添加" onclick="addRowByIDLTwoBatch(' + count + ');"/>&nbsp;&nbsp;&nbsp;'
					+'<img src="/resources/images/-.gif" style="cursor:hand" title="删除" '
					//先删除，再排序
					+'	onclick="javaScript:document.all.addApplyBatchLOTAffirm_list.deleteRow(event.srcElement.parentElement.parentElement.rowIndex);changeApplyBatchOtLevel();"/>'
			+'</td>'
		+'</tr>';

	var tb2 = document.getElementById("addApplyBatchLOTAffirm_list");

   	if(tb2.rows.length == 1){
   		$("#addApplyBatchLOTAffirm_list").append(str);
   	} else {
   	 	//当前行之后插入一行
   	 	$("#" + tb2.rows[1].id).before(str);
   	}
	$("[id='dwz.person.baotempName" + count + "']").attr("alt","请输入关键字按回车检索").attr("size","30").inputAlert();
	changeApplyBatchOtLevel();
	$("#affirmorListCnt").val(++count) ;
}

//修改决裁者等级
function changeApplyBatchOtLevel(){
	var tb2 = document.getElementById("addApplyBatchLOTAffirm_list");
	var rowCount = tb2.rows.length;
	for(var m=1;m<rowCount;m++){
		tb2.rows[m].cells[0].innerHTML = m;
	}
}

var keyCodeInit=0;
function submitKeyClick_affirmor_baot(obj,index,event){
	var e= event ? event : window.event; 
	var keyCode = e.which ? e.which : e.keyCode;
 	if(keyCode==13){
 		keyCodeInit=keyCode;
		var empid=obj.value.replace(/[ ]/g,"");
		var empIdStr=obj.id.substring(11);
		var personIdStr="baotpersonId"+empIdStr.substring(11);
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
					  	$("[id='dwz.person.baotempName" + index + "']").val('['+jsonObject.empId + ']-'+jsonObject.empName);
					  	$("[id='dwz.person.baotpersonId" + index + "']").val( jsonObject.personId);
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

function uploadAttWindowOt(divId,applyNo,applyType){
	$.pdialog.open("/sys/notice/uploadWindow?APPLY_NO=" + applyNo + "&APPLY_TYPE=" + applyType + "&divId=" + divId, "uploadWindow", "附件上传", {width:550,height:320,mask:true});
}
</script>
<div class="pageContent">
		
		
		<c:if test="${CPNY_ID ne 'TSTO'}">
		
		<table class="table" width="100%" layoutH="240" nowrapTD="false">
		<thead>
			<tr>
				<th>社号</th>
				<th>姓名</th>
				<th>加班开始时间</th>
				<th>加班结束时间</th>
				<th>加班类型</th>
				<th>参考时长</th>
				<c:if test="${CPNY_ID eq 'LGEQD'}">
				<th>本月参考时长（含本次）</th>
				</c:if>
				<c:if test="${CPNY_ID eq 'LGEYT'}">
				<th>本月参考时长（含本次）</th>
				</c:if>
				<th>加班原因</th>
				<th>附件</th>
				
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${OtBatchAffirmList}" var="item" varStatus="i">			
				<tr>
					<td class='td_center'>${item.EMPID}</td>
					<td class='td_center'>${item.LOCAL_NAME}</td>
					<td class='td_center'>${item.OT_FROM_TIME}</td>
					<td class='td_center'>${item.OT_TO_TIME}</td>
					<td class='td_center'>${item.OT_TYPE}</td>
					<td class='td_center'>${item.OT_LENGTH}</td>
					<c:if test="${CPNY_ID eq 'LGEYT'}">
					    <c:if test="${item.OT_LENGTH_Z >= 90}">
				            <td class='td_center' style="color:red">${item.OT_LENGTH_Z}</td>
				        </c:if>
				        <c:if test="${item.OT_LENGTH_Z < 90}">
				            <td class='td_center'>${item.OT_LENGTH_Z}</td>
				        </c:if>
				    </c:if>
					<c:if test="${CPNY_ID eq 'LGEQD'}">
					<c:if test="${item.OT_LENGTH_Z >= 70}">
				        <td class='td_center' style="color:red">${item.OT_LENGTH_Z}</td>
				    </c:if>
				    <c:if test="${item.OT_LENGTH_Z < 70}">
				        <td class='td_center'>${item.OT_LENGTH_Z}</td>
				    </c:if>
				    </c:if>
					<td style="text-align:left">${item.APPLY_OT_REMARK}</td>
					<td style="text-align:left;width:16%" id="lotBatchUpload_${i.index}">
							<c:forEach items="${item.fileList}" var="file" varStatus="j">	
								&nbsp;&nbsp;<a href="/ess/infoApplyLeave/downloadFile?fileName=${file.FILE_URL }&file=${file.FILE_NAME}" >${file.FILE_NAME}</a>;
							</c:forEach>
						&nbsp;&nbsp;<img src="/resources/images/+.gif" title="上传" border="0" align="absmiddle" style="cursor:hand" onclick="uploadAttWindowOt('lotBatchUpload_${i.index}',${item.BATCH_APPLY_NO },31)"/>
					</td>
				</tr>			
			</c:forEach>			
		</tbody>
	</table> </c:if>
	
		<c:if test="${CPNY_ID eq 'TSTO'}">
		<table class="table" width="100%" layoutH="240" nowrapTD="false">
		<thead>
			<tr>
				<th>社号</th>
				<th>姓名</th>
				<th>加班日期</th>
				<th>加班长度</th>
				<th>加班类型</th>
				<th>加班原因</th>
				<th>附件</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${OtBatchAffirmList}" var="item" varStatus="i">			
				<tr>
					<td class='td_center'>${item.EMPID}</td>
					<td class='td_center'>${item.LOCAL_NAME}</td>
					<td class='td_center'>${item.APPLY_OT_DATE}</td>
					<td class='td_center'>${item.L_OT_LENGTH}</td>
					<td class='td_center'>${item.OT_TYPE}</td>
					<td style="text-align:left">${item.APPLY_OT_REMARK}</td>
					
					<td style="text-align:left;width:16%" id="lotBatchUpload_${i.index}">${file.FILE_URL } ${file.FILE_NAME}
							<c:forEach items="${item.fileList}" var="file" varStatus="j">	
								&nbsp;&nbsp;<a href="/ess/infoApplyLeave/downloadFile?fileName=${file.FILE_URL }&file=${file.FILE_NAME}" >${file.FILE_NAME}</a>;
							</c:forEach>
						&nbsp;&nbsp;<img src="/resources/images/+.gif" title="上传" border="0" align="absmiddle" style="cursor:hand" onclick="uploadAttWindowOt('lotBatchUpload_${i.index}',${item.BATCH_APPLY_NO },31)"/>
					</td>
				</tr>			
			</c:forEach>			
		</tbody>
	</table> </c:if>
	 
	<c:set value="/ess/infoApply/viewFullApplyOtBatchAffirmInfo1?APPLY_NO=${APPLY_NO}" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>
<div class="pageContent" >
	<form id="modifyAffirmorForBatchOt" method="post" action="/ess/infoApply/modifyAffirmorForBatchOt" class="pageForm required-validate" 
	onsubmit="return validateBatchOtAffirmCallback(this,navTabAjaxDone);">
	<table class="user_table" width="100%" border="0">
		<tr>
			<td class="td_title" style="text-align: center;width:10%;"><!-- 决裁线 -->
					决裁线
			</td>
			<td colspan="3" style="text-align: center;width:90%;">
				<table class="user_table" width="100%" id="addApplyBatchLOTAffirm_list">	
					<tr>
									<td class="td_title" style="text-align:center;" width="11%">决裁等级</td>
									<td class="td_title" style="text-align:center;" width="44%">决裁者</td>
									<td class="td_title" style="text-align:center;" width="44%">是否新增(<img src="/resources/images/+.gif" title="添加" border="0" align="absmiddle" style="cursor:hand" onclick="addRowByIDApplyBatchLOTFirst()"/>)</td>
					</tr>
								<c:forEach items="${affirmorList}" var="affirmor" varStatus="i">			
									<tr id="rowIdApplyBatchLot${i.index }">
										<td class="td_type" style="text-align: center">${affirmor.AFFIRM_LEVEL}</td>
										<td class="td_type" style="text-align: center">[${affirmor.EMPID}]-${affirmor.LOCAL_NAME}<input type="hidden" name="AFFIRMOR_ID" value="${affirmor.AFFIRMOR_ID}"/></td>
										<td class="td_type" style="text-align: center"><img src="/resources/images/+.gif" title="添加" border="0" align="absmiddle" style="cursor:hand" onclick="addRowByIDLTwoBatch(${i.index })"/></td>
									</tr>			
								</c:forEach>
				</table>
							<input type="hidden" id="affirmorListCnt" name="affirmorListCnt" value="${affirmorListCnt }"/>
							<input type="hidden" name="APPLY_NO" id="APPLY_NO" value="${APPLY_NO }">
							<input type="hidden" name="APPLY_TYPE" id="APPLY_TYPE" value="31">
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