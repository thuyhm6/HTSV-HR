<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
//添加决裁者
function addRowByIDPa0701_U(currentRowID){
	var count = parseInt($("#affirmCount").val());
    var htm  ='<tr id="rowIdPa0701_U'+ count +'"><td class="td_type" style="text-align: center" width="33%"><span name="rowIndex"></span></td>';
		htm +='<td class="td_type" style="text-align: center" width="33%">';
		htm +='<input id="AFFIRMOR_IDPa0701_U' + count + '" name="AFFIRMOR_ID" value="" type="hidden"/>';
		htm +='<input id="EMPINFOPa0701_U' + count + '" name="empid" type="text" class="required" lookupGroup="person" onkeydown="submitKeyClick_affirmor_U(this,' + count + ',event)" class="required"/>';
		//htm +='<a class="btnLook" href="/ar/attendanceSettings/viewKeeperList?pageNum=1" lookupGroup="person">';
		htm +='</td>';
		htm +='<td class="td_type" style="text-align: center" width="33%">';
		htm +='<img src="/resources/images/+.gif" title="添加"';	
		htm +='border="0" align="absmiddle" style="cursor:hand" onclick="addRowByIDPa0701_U(' + count + ')"/>&nbsp;&nbsp;&nbsp;';
		htm +='<img src="/resources/images/-.gif" title="删除"';	
		htm +='border="0" align="absmiddle" style="cursor:hand" onclick="javaScript:document.all.addAffirm_listPa0701_U.deleteRow(event.srcElement.parentElement.parentElement.rowIndex);changeAffirmLevelPa0701_U();"/></td></tr>';

   	//当前行之后插入一行
   	$("#rowIdPa0701_U" + currentRowID).after(htm);
   	changeAffirmLevelPa0701_U();  
  	$("#affirmCount_U").val(++count) ;
}
//修改决裁者等级
function changeAffirmLevelPa0701_U(){
	var tb2 = document.getElementById("addAffirm_listPa0701_U");
	var rowCount = tb2.rows.length;
	for(var m=0;m<rowCount;m++){
		tb2.rows[m].cells[0].innerHTML = m+1;
	}
}
var keyCodeInit=0;
function submitKeyClick_affirmor_U(obj,index,event){
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
							$("#EMPINFOPa0701_U" + index).val('['+jsonObject.empId + ']-'+jsonObject.empName);
							$("#AFFIRMOR_IDPa0701_U" + index).val( jsonObject.personId);
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
	
    function validateCallbackViewUpdatePaTempSales(form, callback) {
		var $form = $("#viewUpdatePaTempSales");
		if (!$form.valid()) {
			return false;
		}
		if($("#deptNo_update").val() == ""){
			alertMsg.error("请先选择支社。");
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

	function updateTempSalary(flag){
		$("#viewUpdatePaTempSalesFLAG").val(flag);
		$("#viewUpdatePaTempSales").submit();
	}

	var ajaxGet_update;
	function ajaxAdd_update() {
		if (ajaxGet_update != null) {
			ajaxGet_update.abort();
		}
		$.ajaxSettings.global = false;
		ajaxGet_update = $.ajax( {
					type : "POST",
					url : "/pa/tempsale/getSpmsShopList",
					data : {CONTENT : $("#shop_name_update").val()},
					dataType : "json",
					success : function(data) {
						$('#shopTree_update').html("");
						var html = '<tr onclick="selectedIt_update(\'\',\'\')"><th>Code</th><th>门店名称</th></tr>';
						if (typeof (data['shopList']) != "undefined") {
							$.each(data['shopList'],
											function(commentIndex, comment) {
												html += '<tr onclick="selectedIt_update(\''
														+ comment['SHOP_NAME']
														+ '\',\'' + comment['SHOP_CD'] + '\')"><td style="text-align:left;width:30%">'
														+ comment['SHOP_CD']
														+ '</td><td style="text-align:left;width:70%">'
														+ comment['SHOP_NAME']
														+ '</td></tr>';
											});
						}
						$('#shopTree_update').html(html);
						$("#shopContent_update").css("display", "block");
					}
				});
		$.ajaxSettings.global = true;
	}
	function selectedIt_update(shopName,shopNo) {
		$('#shop_name_update').val(shopName);
		$('#shop_cd_update').val(shopNo);
		$('#shopContent_update').css('display', 'none');
	}


	var ajaxGet_add;
	function changeShop_update(deptNo,valueOld) {
		if (ajaxGet_add != null) {
			ajaxGet_add.abort();
		}
		$.ajaxSettings.global = false;
		ajaxGet_add = $.ajax( {
					type : "POST",
					url : "/pa/tempsale/getSpmsShopList",
					data : {CONTENT : deptNo},
					dataType : "json",
					success : function(data) {
						$('#EVENT_STORE_CODE_update').html('');
						var html = '<option value="">请选择</option>';
						if (typeof (data['shopList']) != "undefined") {
							$.each(data['shopList'],
											function(commentIndex, comment) {
												html += '<option value="'+ comment['SHOP_CD']+ '">'+ comment['SHOP_NAME'] + '</option>';
											});
						}
						$('#EVENT_STORE_CODE_update').html(html);
						if(valueOld == -1){
							$("#EVENT_STORE_CODE_update").val($("#EVENT_STORE_CODE_HIDDEN").val());
						}
					}
				});
		$.ajaxSettings.global = true;
	}

	$(document).ready(function(){
		if($("#deptNo_update").val() != ''){
			changeShop_update($("#deptNo_update").val(),-1);
		}
	});
	
	//文件上传的js方法
  function uploadifySuccess_Lc(file, data, response){
  //获取后台返回到前台的文件名，添加到隐藏域,多文件用";"号隔开
  var files = $("#fileNmae").html();
  var fileUrl = $("#fileUrl").val();
  var fileName = $("#fileName").val();
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
  $("#fileNmae").html(files);
  $("#fileUrl").val(fileUrl);
  $("#fileName").val(fileName);
}
</script>
<div class="pageContent">
	<form id="viewUpdatePaTempSales" method="post" action="/pa/tempsale/updatePaTempSales" class="pageForm required-validate" onsubmit="return validateCallbackViewUpdatePaTempSales(this, dialogAjaxDone)">
		<div class="pageFormContent" layoutH="56">
			<table width="100%"  border="0" cellpadding="0" cellspacing="0" class="user_table margin_b">
				<tr>
					<td class="td_title"  style="width:20%;">
						Event名称
					</td>
					<td class="td_type"   style="width:30%;">
						${tempSalInfo.EVENT_NAME }
						（自动生成：内容+门店+开始日期）
					</td>
					<td class="td_title"  style="width:20%;">
						Event ID
					</td>
					<td class="td_type"  style="width:30%;">
						${tempSalInfo.EVENT_ID }
						<input type="hidden" name="EVENT_ID" value="${tempSalInfo.EVENT_ID }" />
					</td>
				</tr>
				<tr>
					<td class="td_title" >
						开始日期
					</td>
					<td class="td_type">
						<input type="text" id="START_DATE" name="START_DATE" value="${tempSalInfo.START_DATE}" class="required date" readonly="true" format="yyyy-MM-dd" yearstart="-50" yearend="5" onClick="setdate(this);" maxlength="20" size="30"/>
						<a class="inputDateButton"><spring:message code="public.title.choose" /><!-- 选择 --></a>
					</td>
					<td class="td_title" >
						结束日期
					</td>
					<td class="td_type">
						<input type="text" id="END_DATE" name="END_DATE" value="${tempSalInfo.END_DATE}" class="required date" readonly="true" format="yyyy-MM-dd" yearstart="-50" yearend="5" onClick="setdate(this);" maxlength="20" size="30"/>
						<a class="inputDateButton"><spring:message code="public.title.choose" /><!-- 选择 --></a>
					</td>
				</tr>
				<tr>
					<td class="td_title" >
						EVENT部门
					</td>
					<td class="td_type">
						${tempSalInfo.EVENT_DEPTNO_NAME }
						<input type="hidden" name = "DEPTNO" value="${tempSalInfo.EVENT_DEPTNO }">
					</td>
					<td class="td_title" >
						工资支付月
					</td>
					<td class="td_type">
						<ait:date yearName="paYear" monthName="paMonth"
							yearSelected="${fn:substring(tempSalInfo.PAY_DATE,0,4)}"  monthSelected="${fn:substring(tempSalInfo.PAY_DATE,4,6)}"/>
					</td>
				</tr>
				<tr>
					<td class="td_title" >
						EVENT内容
					</td>
					<td class="td_type" colspan="3">
						<input type="text" name="EVENT_CONTENT" value="${tempSalInfo.EVENT_CONTENT}" class="required textInput" maxlength="100" size="100"/>
					</td>
				</tr>
				<tr>
					<td class="td_title">
						总人数
					</td>
					<td class="td_type">
						<span id="TOTAL_NUM_TEXT">${tempSalInfo.TOTAL_NUM}</span>
						<input type="hidden" id="TOTAL_NUM" name="TOTAL_NUM" value="${tempSalInfo.TOTAL_NUM}"  />
					</td>
					<td class="td_title">
						总金额
					</td>
					<td class="td_type">
						<span id="TOTAL_SALARY_TEXT">${tempSalInfo.TOTAL_SALARY}</span>
						<input type="hidden" id="TOTAL_SALARY" name="TOTAL_SALARY" value="${tempSalInfo.TOTAL_SALARY}"  />
					</td>
				</tr>
				<tr>
					<td class="td_title" >
						备注
					</td>
					<td class="td_type" colspan="3">
						<input type="text" name="REMARK" value="${tempSalInfo.REMARK}" class="textInput" maxlength="100" size="100"/>
					</td>
				</tr>
				<tr>
			<td width="20%"  class="td_title">
										附件上传
									</td>
				 <td class="td_type" colspan="1">
				 <input id="testFileInput_Lc"
					type="file" name="file"
					 uploaderOption="{
						swf:'/resources/js/uploadify/scripts/uploadify.swf',
					    uploader:'/ess/infoApplyLeave/uploadBatchTemp',
						formData:{ajax:1},
						queueID:'fileQueue',
						buttonText:'请选择',
						height:25,
						width:50,
						auto:false,
						onUploadSuccess:uploadifySuccess_Lc,
						removeTimeout:1
					    }" />
					<span id="fileNmae">${tempSalInfo.FILE_NAME}</span>
                    <div id="fileQueue" class="fileQueue"></div>
					<input type="hidden" id="fileUrl" name="fileUrl" value="${tempSalInfo.FILE_URL}"/>
				    <input type="hidden" id="fileName" name="fileName" value="${tempSalInfo.FILE_NAME}"/>
					<div class="buttonActive">
						<div class="buttonContent">
							<!--保存-->
							<button type="button"
								onclick="$('#testFileInput_Lc').uploadify('upload', '*');return false;">
								上传</button>
						</div>
					</div>
					<div class="buttonActive">
						<div class="buttonContent">
							<!--提交-->
							<button type="button"
								onclick="$('#testFileInput_Lc').uploadify('cancel', '*');return false;">
								取消</button>
						</div>
					</div></td>
			</tr>
			</table>
			<!--<table width="100%"  border="0" cellpadding="0" cellspacing="0" class="user_table margin_b" >
				<tr>
					<td class="td_title" style="text-align: center"  width="20%" rowspan="2">
						决裁线
					</td>
					<td class="td_title" style="text-align: center" width="30%">
						决裁等级
					</td>
					<td class="td_title" style="text-align: center" width="50%">
						决裁者
					</td>
				</tr>
							<c:forEach items="${affirmList}" var="affirmor" varStatus="j">	
								<tr id="rowIdPa0701_U${j.index}">
									<td class="td_type" style="text-align: center" width="30%">
										${j.count}
									</td>
									<td class="td_type" style="text-align: center" width="50%">
										[${affirmor.EMPID}]-${affirmor.LOCAL_NAME }
										<input type="hidden" name="AFFIRMOR_ID" value="${affirmor.AFFIRMOR_ID }"/>
									</td>
								</tr>
							</c:forEach>
			</table>
		    --><input type="hidden" name="affirmCount" id="affirmCount_U" value="${affirmorListCnt }">
		    <input type="hidden" id="viewUpdatePaTempSalesFLAG" name="FLAG" value="" />
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="updateTempSalary(1);"><spring:message code="public.title.submit"/><!-- 提交 --></button></div></div></li>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="updateTempSalary(0);"><spring:message code="heran.examineSave.title"/><!-- 保存 --></button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close"><spring:message code="public.title.cancle"/><!-- 取消 --></button></div></div>
				</li>
			</ul>
		</div>
	</form>	
</div>