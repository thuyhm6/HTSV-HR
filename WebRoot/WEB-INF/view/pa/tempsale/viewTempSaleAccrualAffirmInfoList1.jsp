<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script type="text/javascript">
function validateBatchTempSaleAffirmCallback(form,callback) {	
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
function addRowByIDTempSaleTwoBatch(currentRowID){
	var count = parseInt($("#affirmListCnt").val());
	str = '<tr id = "rowIdApplyBatchTempSale'+count+'">'
        	+'<td style="text-align: center"></td>'
			+'<td style="text-align: center">'
				+'<input id="dwz.person.baotpersonId'+count+'" name="AFFIRMOR_ID" value="" type="hidden" lookupGroup="person"/>'
				+'<input id="dwz.person.baotempName'+count+'" name="empid" value="" type="text" lookupGroup="person" '
				+'	onkeydown="submitKeyClick_affirmor_tempSale(this,' + count + ',event)" class="required"/>'
			+'</td>'
			+'<td style="text-align: center">'
				+'<img src="/resources/images/+.gif" style="cursor:hand" title="添加" onclick="addRowByIDTempSaleTwoBatch(' + count + ');"/>&nbsp;&nbsp;&nbsp;'
				+'<img src="/resources/images/-.gif" style="cursor:hand" title="删除" '
				//先删除，再排序
				+'	onclick="javaScript:document.all.addApplyBatchTempSaleAffirm_list.deleteRow(event.srcElement.parentElement.parentElement.rowIndex);changeApplyBatchOtLevel();"/>'
			+'</td>'
		+'</tr>';
	//当前行之后插入一行
	$("#rowIdApplyBatchTempSale" + currentRowID).after(str);
	$("[id='dwz.person.baotempName" + count + "']").attr("alt","请输入关键字按回车检索").attr("size","30").inputAlert();
	changeApplyBatchOtLevel();
	$("#affirmListCnt").val(++count) ;
}


function addRowByIDApplyBatchLOTFirst(){
	var count = parseInt($("#affirmListCnt").val());
	str = '<tr id = "rowIdApplyBatchTempSale'+count+'">'
      	+'<td style="text-align: center"></td>'
			+'<td style="text-align: center">'
				+'<input id="dwz.person.baotpersonId'+count+'" name="AFFIRMOR_ID" value="" type="hidden" lookupGroup="person"/>'
				+'<input id="dwz.person.baotempName'+count+'" name="empid" value="" type="text" lookupGroup="person" '
				+'	onkeydown="submitKeyClick_affirmor_tempSale(this,' + count + ',event)" class="required"/>'
			+'</td>'
			+'<td style="text-align: center">'
					+'<img src="/resources/images/+.gif" style="cursor:hand" title="添加" onclick="addRowByIDTempSaleTwoBatch(' + count + ');"/>&nbsp;&nbsp;&nbsp;'
					+'<img src="/resources/images/-.gif" style="cursor:hand" title="删除" '
					//先删除，再排序
					+'	onclick="javaScript:document.all.addApplyBatchTempSaleAffirm_list.deleteRow(event.srcElement.parentElement.parentElement.rowIndex);changeApplyBatchOtLevel();"/>'
			+'</td>'
		+'</tr>';

	var tb2 = document.getElementById("addApplyBatchTempSaleAffirm_list");

   	if(tb2.rows.length == 1){
   		$("#addApplyBatchTempSaleAffirm_list").append(str);
   	} else {
   	 	//当前行之后插入一行
   	 	$("#" + tb2.rows[1].id).before(str);
   	}
	$("[id='dwz.person.baotempName" + count + "']").attr("alt","请输入关键字按回车检索").attr("size","30").inputAlert();
	changeApplyBatchOtLevel();
	$("#affirmListCnt").val(++count) ;
}

//修改决裁者等级
function changeApplyBatchOtLevel(){
	var tb2 = document.getElementById("addApplyBatchTempSaleAffirm_list");
	var rowCount = tb2.rows.length;
	for(var m=1;m<rowCount;m++){
		tb2.rows[m].cells[0].innerHTML = m;
	}
}

var keyCodeInit=0;
function submitKeyClick_affirmor_tempSale(obj,index,event){
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

//文件上传的js方法
  function uploadifySuccess_Yt(file, data, response){
  //获取后台返回到前台的文件名，添加到隐藏域,多文件用";"号隔开
  var files = $("#fileNmae_Yt").html();
  var fileUrl = $("#fileUrl_Yt").val();
  var fileName = $("#fileName_Yt").val();
  var fileResult = data.split(";");
  //第一个文件
  if(files=="" || files==null){
    files = fileResult[0];
    fileName = fileResult[0];
    fileUrl = fileResult[1];
  }else{
    files+=";"+fileResult[0];
    fileName+=";"+fileResult[0];
    fileUrl+=";"+fileResult[1];
  }
  $("#fileNmae_Yt").html(files);
  $("#fileUrl_Yt").val(fileUrl);
  $("#fileName_Yt").val(fileName);
} 
</script>
<div class="pageContent">
<table class="user_table" width="100%">
		<tr>
			<td class="td_title" style="text-align:right;width:10%;">申请人</td>
			<td colspan="7" class="td_type" style="text-align:left;width:90%;">[${eventInfo.EMPID}]${eventInfo.LOCAL_NAME}</td>
		</tr>
		<tr>
			<td class="td_title" style="text-align:right;">申请时间</td>
			<td colspan="7" class="td_type" style="text-align: left;">${eventInfo.APPLY_DATE}</td>
		</tr>
		<tr>
			<td class="td_title" style="text-align:right;">申请内容</td>
			<td colspan="7" style="text-align: center;">
				<table width="100%">
					<tr>
						<td colspan="2" class="td_title" style="text-align: center;width:12%;">大区</td>
						<td colspan="2" class="td_type" style="text-align: center;width:12%;">${eventInfo.PAY_AREA_NAME}</td>
						<td colspan="2" class="td_title" style="text-align: center;width:12%;">支社</td>
						<td colspan="2" class="td_type" style="text-align: center;width:12%;">${eventInfo.BRANCH_NAME}</td>
						<td colspan="2" class="td_title" style="text-align: center;width:12%;">支付月份</td>
						<td colspan="2" class="td_type" style="text-align: center;width:12%;">${eventInfo.PAY_DATE}</td>
						<td colspan="2" class="td_title" style="text-align: center;width:12%;">对应共同社编</td>
						<td colspan="2" class="td_type" style="text-align: center;width:12%;">${eventInfo.COMMON_EMPID}</td>
					</tr>
					<c:forEach items="${paTempSalesAccuralInfoList}" var="item" varStatus="i">
						<tr target="INFO_NO" rel="${item.INFO_NO}">
							<td class="td_title" style="text-align: center">${i.count}</td>
							<td class="td_title" style="text-align: center">产品类型</td>
							<td colspan="6" class="td_type">${item.PROD_TP}</td>
							<td colspan="2" class="td_title" style="text-align: center">金额</td>
							<td colspan="6" class="td_type">${item.TOTAL_PAY}</td>
						</tr>
					</c:forEach>
					<tr>
						<td colspan="8" class="td_type"></td>
						<td colspan="2" class="td_title" style="text-align:center;">合计</td>
						<td colspan="6" class="td_type">${eventInfo.TOTAL_SALARY}</td>
					</tr>
		
				</table>
			</td>
		</tr>
</table>
</div>
<div class="pageContent" >
	<form id="modifyAffirmorForBatchOt" method="post" action="/pa/tempsale/modifyAffirmorForBatchTempSale?yuty=1&yutyNo=${eventInfo.EVENT_ID}" class="pageForm required-validate" 
		onsubmit="return validateBatchTempSaleAffirmCallback(this,navTabAjaxDone);">
	<table class="user_table" width="100%" border="0">
	<tr>
			<td class="td_title" style="text-align:right;width:10%;">附件上传</td>
			<td colspan="7" class="td_type" style="text-align:left;width:90%;"><input id="testFileInput_Yt"
					type="file" name="file"
					 uploaderOption="{
						swf:'/resources/js/uploadify/scripts/uploadify.swf',
					    uploader:'/ess/infoApplyLeave/uploadBatch?PERSON_ID=${personInfo.personId}',
						formData:{ajax:1},
						queueID:'fileQueue_Yt',
						buttonText:'请选择',
						height:25,
						width:50,
						auto:false,
						onUploadSuccess:uploadifySuccess_Yt,
						removeTimeout:1
					    }" />
					<span id="fileNmae_Yt"></span>
					<div id="fileQueue_Yt" class="fileQueue"></div> 
					<input type="hidden" id="fileUrl_Yt" name="fileUrl" value="" /> 
					<input type="hidden" id="fileName_Yt" name="fileName" value="" />

					<div class="buttonActive">
						<div class="buttonContent">
							<!--保存-->
							<button type="button"
								onclick="$('#testFileInput_Yt').uploadify('upload', '*');return false;">
								上传</button>
						</div>
					</div>
					<div class="buttonActive">
						<div class="buttonContent">
							<!--提交-->
							<button type="button"
								onclick="$('#testFileInput_Yt').uploadify('cancel', '*');return false;">
								取消</button>
						</div>
					</div></td>
		</tr>
		<tr>
			<td class="td_title" style="text-align: center;width:10%;"><!-- 决裁线 -->
					决裁线
			</td>
			<td colspan="3" style="text-align: center;width:90%;">
				<table class="user_table" width="100%" id="addApplyBatchTempSaleAffirm_list">
					<tr>
									<td class="td_title" style="text-align:center;" width="11%">决裁等级</td>
									<td class="td_title" style="text-align:center;" width="44%">决裁者</td>
									<td class="td_title" style="text-align:center;" width="44%">是否新增(<img src="/resources/images/+.gif" title="添加" border="0" align="absmiddle" style="cursor:hand" onclick="addRowByIDApplyBatchLOTFirst()"/>)</td>
					</tr>
								<c:forEach items="${affirmList}" var="affirmor" varStatus="i">			
									<tr id="rowIdApplyBatchTempSale${i.index }">
										<td class="td_type" style="text-align: center">${affirmor.AFFIRM_LEVEL}</td>
										<td class="td_type" style="text-align: center">[${affirmor.EMPID}]-${affirmor.LOCAL_NAME}<input type="hidden" name="AFFIRMOR_ID" value="${affirmor.AFFIRMOR_ID}"/></td>
										<td class="td_type" style="text-align: center"><img src="/resources/images/+.gif" title="添加" border="0" align="absmiddle" style="cursor:hand" onclick="addRowByIDTempSaleTwoBatch(${i.index })"/></td>
									</tr>			
								</c:forEach>
				</table>
							<input type="hidden" id="affirmListCnt" name="affirmListCnt" value="${affirmListCnt }"/>
							<input type="hidden" name="APPLY_NO" id="APPLY_NO" value="${EVENT_ID }">
							<input type="hidden" name="APPLY_TYPE" id="APPLY_TYPE" value="218064">
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
