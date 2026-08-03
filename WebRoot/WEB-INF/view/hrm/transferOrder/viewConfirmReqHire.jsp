<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
//添加决裁者
function addRowByIDhr0504(currentRowID){
	var count = parseInt($("#affirmCount").val());
    var htm  ='<tr id="rowIdhr0504'+ count +'"><td class="td_type" style="text-align: center" width="25%"><span name="rowIndex"></span></td>';
		htm +='<td class="td_type" style="text-align: center" width="37.5%">';
		htm +='<input id="AFFIRMOR_IDhr0504' + count + '" name="AFFIRMOR_ID" value="" type="hidden"/>';
		htm +='<input id="EMPINFOhr0504' + count + '" name="empid" type="text" class="required" lookupGroup="person" onkeydown="submitKeyClick_affirmor(this,' + count + ',event)" class="required"/>';
		htm +='</td>';
		htm +='<td class="td_type" style="text-align: center" width="37.5%">';
		htm +='<img src="/resources/images/+.gif" title="添加"';	
		htm +='border="0" align="absmiddle" style="cursor:hand" onclick="addRowByIDhr0504(' + count + ')"/>&nbsp;&nbsp;&nbsp;';
		htm +='<img src="/resources/images/-.gif" title="删除"';	
		htm +='border="0" align="absmiddle" style="cursor:hand" onclick="javaScript:document.all.addAffirm_listhr0504.deleteRow(event.srcElement.parentElement.parentElement.rowIndex);changeAffirmLevel();"/></td></tr>';

   	//当前行之后插入一行
   	$("#rowIdhr0504" + currentRowID).after(htm);
   	changeAffirmLevel();
  	$("#affirmCount").val(++count) ;
}
function addRowByIDHireFirst(){
	var count = parseInt($("#affirmCount").val());
    var htm  ='<tr id="rowIdhr0504'+ count +'"><td class="td_type" style="text-align: center" width="25%"><span name="rowIndex"></span></td>';
		htm +='<td class="td_type" style="text-align: center" width="37.5%">';
		htm +='<input id="AFFIRMOR_IDhr0504' + count + '" name="AFFIRMOR_ID" value="" type="hidden"/>';
		htm +='<input id="EMPINFOhr0504' + count + '" name="empid" type="text" class="required" lookupGroup="person" onkeydown="submitKeyClick_affirmor(this,' + count + ',event)" class="required"/>';
		htm +='</td>';
		htm +='<td class="td_type" style="text-align: center" width="37.5%">';
		htm +='<img src="/resources/images/+.gif" title="添加"';	
		htm +='border="0" align="absmiddle" style="cursor:hand" onclick="addRowByIDhr0504(' + count + ')"/>&nbsp;&nbsp;&nbsp;';
		htm +='<img src="/resources/images/-.gif" title="删除"';	
		htm +='border="0" align="absmiddle" style="cursor:hand" onclick="javaScript:document.all.addAffirm_listhr0504.deleteRow(event.srcElement.parentElement.parentElement.rowIndex);changeAffirmLevel();"/></td></tr>';

	var tb2 = document.getElementById("addAffirm_listhr0504");
   	if(tb2.rows.length == 0){
   		$("#addAffirm_listhr0504:last tbody").html(htm);
   	} else {
   	   	//当前行之后插入一行
   	   	$("#" + tb2.rows[0].id).before(htm);
   	}
  	$("[id='EMPINFOhr0504" + count + "']").attr("alt","请输入关键字按回车检索").attr("size","30").inputAlert();
  	changeAffirmLevel();
  	$("#affirmCount").val(++count) ;
}

//修改决裁者等级
function changeAffirmLevel(){
	var tb2 = document.getElementById("addAffirm_listhr0504");
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
		var empid=obj.value.replace(/[ ]/g,"");
		var empIdStr=obj.id;
		var personIdStr="personId"+empIdStr.substring(7);
		var empNameStr="empName"+empIdStr.substring(7);
   		$.ajax({
			type: 'POST',
			url: encodeURI('/sys/affirm/getPersonCntByEmpid?EMPID='+empid ),
			dataType:"json",
			cache: false,
			success: function(jsonObject){
						if (jsonObject.perCnt!=1){
							alertMsg.error('<spring:message code="alert.message.sys.affirm.searchNoPerson"/>');
						}
						if(jsonObject.perCnt==1){
							$("#EMPINFOhr0504" + index).val('['+jsonObject.empId + ']-'+jsonObject.empName);
							$("#AFFIRMOR_IDhr0504" + index).val( jsonObject.personId);
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

	var ajaxGet_add;
	function changeShop_add(deptNo) {
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
						$('#EVENT_STORE_CODE_add').html('');
						var html = '<option value="">请选择</option>';
						if (typeof (data['shopList']) != "undefined") {
							$.each(data['shopList'],
											function(commentIndex, comment) {
												html += '<option value="'+ comment['SHOP_CD']+ '">'+ comment['SHOP_NAME'] + '</option>';
											});
						}
						$('#EVENT_STORE_CODE_add').html(html);
					}
				});
		$.ajaxSettings.global = true;
	}
	function selectedIt_add(shopName,shopNo) {
		$('#shop_name_add').val(shopName);
		$('#shop_cd_add').val(shopNo);
		$('#shopContent_add').css('display', 'none');
	}
	var ajaxGet_add_commonEmpInfo;
	function ajaxAdd_add_commonEmpInfo() {
		if (ajaxGet_add_commonEmpInfo != null) {
			ajaxGet_add_commonEmpInfo.abort();
		}
		$.ajaxSettings.global = false;
		ajaxGet_add_commonEmpInfo = $.ajax( {
			type : "POST",
			url : "/pa/tempsale/getCommonEmpId",
			data : { DEPT_NO : $(":input[sysLong='viewAddPaTempSales_seachDept']").val() },
			dataType : "json",
			success : function(data) {
				if (typeof (data['commonEmp']) != "undefined") {
					var commonEmp = data['commonEmp'];
					$('#COMMON_EMPID_TEXT').html("[" + commonEmp.EMPNO + "]" + commonEmp.EMPNM);
					$('#COMMON_EMPID').val(commonEmp.EMPNO);
				}
			}
		});
		$.ajaxSettings.global = true;
}
	var ajaxGet_add_hr0504_add;
	function ajaxAdd_add_hr0504_add() {
		alert($(":input[sysLong='viewAddPaTempSales_seachDept']").val());
		if (ajaxGet_add_hr0504_add != null) {
			ajaxGet_add_hr0504_add.abort();
		}
		$.ajaxSettings.global = false;
		ajaxGet_add_hr0504_add = $.ajax( {
				type : "POST",
				url : "/pa/tempsale/getAffirmorInfo",
				data : {DEPT_NO : $(":input[sysLong='viewAddPaTempSales_seachDept']").val()},
				dataType : "json",
				success : function(data) {
					$('#addAffirm_listhr0504 tbody').html("");
					var html = "";
					if (typeof (data['affirmList']) != "undefined") {
						$.each(data['affirmList'],
										function(commentIndex, comment) {
											html += '<tr id="rowIdhr0504' + commentIndex  + '">';
											html += '<td class="td_type" style="text-align: center" width="33%">' + (commentIndex + 1) + '</td>';
											html += '<td class="td_type" style="text-align: center" width="33%">[' + comment['EMPID'] + ']-' + comment['LOCAL_NAME'];
											html += '<input type="hidden" name="AFFIRMOR_ID" value="' + comment['AFFIRMOR_ID'] + '"/></td>';
											html += '<td class="td_type" style="text-align: center" width="33%">';
											if((commentIndex + 1) != data['affirmListCnt']){
												html += '<img src="/resources/images/+.gif" title="添加" border="0" align="absmiddle" style="cursor:hand" onclick="addRowByIDApplyLeave('+ commentIndex +')"/>';
											}
											html += '</td>';
										});
					}
					$('#addAffirm_listhr0504 tbody').html(html);
				}
		});
		$.ajaxSettings.global = true;
	}

	function expReqInfo(expType){
		var sform = document.getElementById("viewConfirmReqHire");
		var eForm = document.getElementById("excelForm_hr0504");

		eForm.PAY_AREA_CD.value		= sform.PAY_AREA_CD.value;
		eForm.BRANCH_CD.value	    = sform.BRANCH_CD.value;
		eForm.year.value		    = sform.year.value;
		eForm.month.value		    = sform.month.value;

		eForm.submit();
	}
	
	function saveReq(flag){
		$("#viewConfirmReqHire").submit();
	}

	function uploadSuccess_Hire(file, data, response){
		  //获取后台返回到前台的文件名，添加到隐藏域,多文件用";"号隔开
		  var hire_files = $("#hire_fileNmae").html();
		  var hire_fileUrl = $("#hire_fileUrl").val();
		  var hire_fileName = $("#hire_fileName").val();
		  var hire_fileResult = data.split(";");
		  //第一个文件
		  if(hire_files=="" || hire_files==null){
			  hire_files = hire_fileResult[0];
			  hire_fileName = hire_fileResult[0];
			  hire_fileUrl = hire_fileResult[1];
		  }else{
			  hire_files+=";"+hire_fileResult[0];
			  hire_fileName+=";"+hire_fileResult[0];
			  hire_fileUrl+=";"+hire_fileResult[1];
		  }
		  $("#hire_fileNmae").html(hire_files);
		  $("#hire_fileUrl").val(hire_fileUrl);
		  $("#hire_fileName").val(hire_fileName);
	}
	
</script>
<div class="pageContent">
	<form id="viewConfirmReqHire" method="post" action="/hrm/transferOrder/confirmReqHire" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone)">
		<div class="pageFormContent">
			<input type="hidden" name="CPNY_ID" id="CPNY_ID" value="${CPNY_ID}">
		    <input type="hidden" name="DEPTMENTNO" id="DEPTMENTNO" value="${DEPTMENTNO}" />
			<input type="hidden" name="EMPID" id="EMPID" value="${EMPID}">
			<input type="hidden" name="FROM_TIME" id="FROM_TIME" value="${FROM_TIME}">
			<input type="hidden" name="TO_TIME" id="TO_TIME" value="${TO_TIME}">
			<input type="hidden" name="EMP_TYPE_CODE" id="EMP_TYPE_CODE" value="${EMP_TYPE_CODE}">
			<input type="hidden" name="affirmCount" id="affirmCount" value="${fn:length(affirmorList)}">

		    <table class="table" width="100%" layoutH="356">
		    <thead>
		      <tr>
		        <th width="10%">中文姓名</th>
		        <th width="10%">英文姓名</th>
		        <th width="10%">身份证号码</th>
		        <th width="10%">入社日期</th>
		        <th width="10%">生日</th>
		        <th width="5%">性别</th>
		        <th width="10%">福利地区</th>
		        <th width="14%">人员类型</th>
		        <th width="15%">部门</th>
		        <th width="6">基本工资</th>
		      </tr>
		    </thead>
		    <tbody>
		    <c:forEach items="${itemList}" var="item" varStatus="i">
			  <tr>
			    <input type="hidden" name="hr0504Person" value="${item.PERSON_ID}"/>
				<input type="hidden" name="hr0504DeptNo" value="${item.DEPTNO}"/>
				<td class='td_center'>${item.LOCAL_NAME}</td>
				<td class='td_center'>${item.CHINESE_PINYIN}</td>
				<td class='td_center'>${item.IDCARD_NO}</td>
				<td class='td_center'>${item.JOIN_COMPANY_DATE}</td>
				<td class='td_center'>${item.DOB}</td>
				<td class='td_center'>${item.SEXCODE}</td>
				<td class='td_center'>${item.INSRAREA_NM}</td>
				<td class='td_center'>${item.EMP_TYPE_NM}</td>
				<td>${item.DEPT_NAME}</td>
				<td class='td_right'>${item.BASE_PAY}</td>
			  </tr>
		    </c:forEach>
		    </tbody>
		    </table>
		    
		    <table>
		    <tr valign="top">
		    <td width="50%">
		    <table width="100%"  border="0" cellpadding="0" cellspacing="0" class="user_table margin_b">
			<tr>
				<td width="20%" class="td_title" style="text-align:center">
					附件上传
				</td>
				<td width="80%" class="td_type">
				    <input id="reqHire_file" type="file" name="file" 
							uploaderOption="{
								swf:'/resources/js/uploadify/scripts/uploadify.swf',
								uploader:'/ess/infoApplyLeave/uploadBatch?PERSON_ID=${PERSON_ID}',
								formData:{ajax:1},
								queueID:'fileQueue_Hire',
								buttonText:'请选择',
								height:25,
								width:50,
								auto:false,
								onUploadSuccess:uploadSuccess_Hire,
								removeTimeout:1
							}"
						/><span id="hire_fileNmae"></span>
					  <div id="fileQueue_Hire" class="fileQueue"></div>
					  <input type="hidden" id="hire_fileUrl" name="hire_fileUrl" value=""/>
					  <input type="hidden" id="hire_fileName" name="hire_fileName" value=""/>
						<div class="buttonActive">
							<div class="buttonContent"><!--保存-->
								<button type="button" onclick="$('#reqHire_file').uploadify('upload', '*');return false;">
									上传
								</button>
							</div>
						</div>
						<div class="buttonActive">
							<div class="buttonContent"><!--提交-->
								<button type="button" onclick="$('#reqHire_file').uploadify('cancel', '*');return false;">
									取消
								</button>
							</div>
						</div>
				</td>
			</tr>
		    </table>
		    </td>
		    
		    <td width="50%">
			<table width="100%"  border="0" cellpadding="0" cellspacing="0" class="user_table margin_b">
			<tr>
				<td class="td_title" style="text-align: center" width="20%" rowspan="2">决裁线</td>
				<td class="td_title" style="text-align: center" width="20%">决裁等级</td>
				<td class="td_title" style="text-align: center" width="40%">决裁者</td>
				<td class="td_title" style="text-align:center;" width="20%">是否新增(<img src="/resources/images/+.gif" title="添加" border="0" align="absmiddle" style="cursor:hand" onclick="addRowByIDHireFirst()"/>)</td>
			</tr>
			<tr>
				<td colspan="3">
					<table width="100%" border="0" cellpadding="0" cellspacing="0" id="addAffirm_listhr0504">
						<tbody>
							<c:forEach items="${affirmorList}" var="affirmor" varStatus="j">
								<tr id="rowIdhr0504${j.index}">
									<td class="td_type" style="text-align: center" width="25%">${j.count}</td>
									<td class="td_type" style="text-align: center" width="50%">
										[${affirmor.EMPID}]-${affirmor.LOCAL_NAME }
										<input type="hidden" name="AFFIRMOR_ID" value="${affirmor.AFFIRMOR_ID }" />
									</td>
									<td class="td_type" style="text-align: center" width="25%">
									 <!-- c:if test="${fn:length(affirmorList) gt j.count}"> -->
										<img src="/resources/images/+.gif" title="添加" border="0"
											align="absmiddle" style="cursor: hand" onclick="addRowByIDhr0504(${j.index})" />
									 <!-- /c:if> -->
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</td>
			</tr>
		    </table>
		    </td>
		    </tr>
		    </table>
		</div>

		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="saveReq(1);"><spring:message code="public.title.submit"/><!-- 提交 --></button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close"><spring:message code="public.title.cancle"/><!-- 取消 --></button></div></div></li>
			</ul>
		</div>
	</form>
</div>