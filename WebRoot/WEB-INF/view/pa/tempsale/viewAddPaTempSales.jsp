<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
//添加决裁者
function addRowByIDPa0701(currentRowID){
	var count = parseInt($("#affirmCount").val());
    var htm  ='<tr id="rowIdPa0701'+ count +'"><td class="td_type" style="text-align: center" width="33%"><span name="rowIndex"></span></td>';
		htm +='<td class="td_type" style="text-align: center" width="33%">';
		htm +='<input id="AFFIRMOR_IDPa0701' + count + '" name="AFFIRMOR_ID" value="" type="hidden"/>';
		htm +='<input id="EMPINFOPa0701' + count + '" name="empid" type="text" class="required" lookupGroup="person" onkeydown="submitKeyClick_affirmor(this,' + count + ',event)" class="required"/>';
		//htm +='<a class="btnLook" href="/ar/attendanceSettings/viewKeeperList?pageNum=1" lookupGroup="person">';
		htm +='</td>';
		htm +='<td class="td_type" style="text-align: center" width="33%">';
		htm +='<img src="/resources/images/+.gif" title="添加"';	
		htm +='border="0" align="absmiddle" style="cursor:hand" onclick="addRowByIDPa0701(' + count + ')"/>&nbsp;&nbsp;&nbsp;';
		htm +='<img src="/resources/images/-.gif" title="删除"';	
		htm +='border="0" align="absmiddle" style="cursor:hand" onclick="javaScript:document.all.addAffirm_listPa0701.deleteRow(event.srcElement.parentElement.parentElement.rowIndex);changeAffirmLevel();"/></td></tr>';

   	//当前行之后插入一行
   	$("#rowIdPa0701" + currentRowID).after(htm);
   	changeAffirmLevel();
  	$("#affirmCount").val(++count) ;
}
//修改决裁者等级
function changeAffirmLevel(){
	var tb2 = document.getElementById("addAffirm_listPa0701");
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
							$("#EMPINFOPa0701" + index).val('['+jsonObject.empId + ']-'+jsonObject.empName);
							$("#AFFIRMOR_IDPa0701" + index).val( jsonObject.personId);
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
	//身份证验证
	function validateIdCard(index){
		var num = $("#IDCARD_NO" + index).val();
		var len = num.length, re;
		if (len == 15){
			re = new RegExp(/^(\d{6})()?(\d{2})(\d{2})(\d{2})(\d{3})$/);
		}else if (len == 18){
			re = new RegExp(/^(\d{6})()?(\d{4})(\d{2})(\d{2})(\d{3})(\d|X|x)$/);
		}else if (len == 0){
			//alert("输入的身份证号不能为空！"); 
			alertMsg.error('<spring:message code="hr.alert.message.viewHire.checkNotNullIdcardNo"/>');
			return false;
		}else {
			//alert("输入的数字位数不对！"); 
			alertMsg.error('<spring:message code="hr.alert.message.viewPersonalInfo.checkDigitalDigits"/>');
			return false;
		}
		var a = num.match(re);
		var B = null;
		var D = null;
		if (a != null){
			if (len==15){
				D = new Date("19"+a[3]+"/"+a[4]+"/"+a[5]);
				B = D.getYear()==a[3]&&(D.getMonth()+1)==a[4]&&D.getDate()==a[5];
			}
			else{
				D = new Date(a[3]+"/"+a[4]+"/"+a[5]);
				B = D.getFullYear()==a[3]&&(D.getMonth()+1)==a[4]&&D.getDate()==a[5];
			}
			if (!B){
				//alert("输入的身份证号 "+ a[0] +" 里出生日期不对！"); 
				alertMsg.error('<spring:message code="hr.alert.message.viewPersonalInfo.checkIdCardNoForSplitA"/>'+ a[0] +'<spring:message code="hr.alert.message.viewPersonalInfo.checkIdCardNoForSplitB"/>');
				return false;
			}else{
				$("#BIRTH_DATE" + index).val(D.getFullYear()+"-"+((D.getMonth()+1) < 10 ? "0"+(D.getMonth()+1) : (D.getMonth()+1))+"-"+((D.getDate()) < 10 ? "0"+(D.getDate()) : (D.getDate())));
				return true;
			}
		}else{
			alertMsg.error('<spring:message code="hr.alert.message.viewPersonalInfo.checkIdCardNo"/>');//您输入的身份证号不正确
			return false;
		}
	}
	//添加临工项目人员
	function addrow(){
	    var count = parseInt($("#count").val());
	   
	    var i = count ;
	    var htm="";
	   		htm+='<table id="table' + i + '" tableName="addEmpInfoTable" border="0" width="100%" cellpadding="0" cellspacing="0" class="user_table margin_b">';
	   		htm+='<tr>';
	   		htm+='<td rowspan="6" width="2" id="index' + i + '"></td>';
	   		htm+='<td class="td_title"  width="18%">姓名</td>';
	   		htm+='<td  class="td_type"  width="30%"><input type="text" id="EMP_NAME' + i + '" name="EMP_NAME' + i + '" class="required textInput"  maxlength="20" size="30"/></td>';
	   		htm+='<td  class="td_title" width="18%">身份证号码</td>';
	   		htm+='<td  class="td_type" width="30%"><input type="text" id="IDCARD_NO' + i + '" name="IDCARD_NO' + i + '" class="required textInput" onblur="validateIdCard(' + i + ')"  maxlength="20" size="30"></td>';
	   		htm+='<td rowspan="6" width="2%"><img src="/resources/css/ligerUI/skins/icons/delete.gif" onclick="f_delShiftInfo(\'table'+i+'\')" style="cursor:hand"> </td>';
	   		htm+='</tr>';
	   		htm+='<tr>';
	   		htm+='<td  class="td_title">出生年月</td>';
	   		htm+='<td  class="td_type"><input type="text" id="BIRTH_DATE' + i + '" name="BIRTH_DATE' + i + '" class="required textInput"  readonly="true" maxlength="20" size="30"/></td>';
	   		htm+='<td  class="td_title">联系方式</td>';
	   		htm+='<td  class="td_type"><input type="text" id="CELLPHONE' + i + '" name="CELLPHONE' + i + '" class="required textInput" maxlength="20" size="30"/></td>';
	   		htm+='</tr>';
	   		htm+='<tr>';
	   		htm+='<td  class="td_title">所属单位</td>';
	   		htm+='<td  class="td_type" colspan="3"><input type="text" id="DEPT_NAME' + i + '" name="DEPT_NAME' + i + '" class="textInput" maxlength="80" size="80"/></td>';
	   		htm+='</tr>';
	   		htm+='<tr>';
	   		htm+='<td  class="td_title">评价等级</td>';
	   		htm+='<td  class="td_type"><select name="EVS_GRADE' + i + '"><option value="">请选择</option>' ;
	   			<c:forEach items="${grade}" var="item" >
	   			htm+='<option value="${item.CODE_NO}">${item.CODENAME}</option>';
				</c:forEach>
			htm+='</select></td>';
	   		htm+='<td  class="td_title">黑名单与否</td>';
	   		htm+='<td  class="td_type"><select name="BLACK_LIST_YN' + i + '"><option value="">请选择</option>' ;
	   		htm+='<option value="Y">Y</option>';
	   		htm+='<option value="N">N</option></select>';
			htm+='</td>';
	   		htm+='</tr>';
	   		htm+='<tr>';
	   		htm+='<td  class="td_title">工作天数</td>';
	   		htm+='<td  class="td_type"><input type="text" id="WORK_DAYS' + i + '" name="WORK_DAYS' + i + '" class="number required textInput" maxlength="20" size="30"/></td>';
	   		htm+='<td  class="td_title" >应发工资</td>';
	   		htm+='<td  class="td_type"><input type="text" id="EVENT_SALARY' + i + '" name="EVENT_SALARY' + i + '" class="number required textInput"  onchange="changeTotal();" onblur="changeTotal();" maxlength="20" size="30"/></td>';
	   		htm+='</tr>';
	   		htm+='<tr>';
	   		htm+='<td  class="td_title">银行帐号</td>';
	   		htm+='<td  class="td_type"><input type="text" id="BANK_NO' + i + '" name="BANK_NO' + i + '" class="number textInput" maxlength="20" size="30"/></td>';
	   		htm+='<td  class="td_title" ></td>';
	   		htm+='<td  class="td_type"></td>';
	   		htm+='</tr>';
	   		htm+='</table>';
	   	$("#createTable").append(htm) ;
	   	count++;  
	    $("#count").attr("value",count) ;
	   	changeTotal();
    }
    function f_delShiftInfo(obj){
    	$("#"+obj+"").remove();
	   	changeTotal();
    }
    function validateCallbackViewPaTempSales(form, callback) {
		var $form = $("#viewAddPaTempSales");
		if (!$form.valid()) {
			return false;
		}
		if($("#viewAddPaTempSalesFLAG").val() == 1){
			if(!checkSubmit()){
				return false;
			}
		}
		if($("#event_deptno").val() == ""){
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

	function changeTotal(){
	    var count = parseInt($("#count").val());
	    var totalSalary = 0;
	    var cnt = 0;
	    for(var i=0; i<count; i++){
		    var salaryIdObj = $("#EVENT_SALARY" +  i).val();
		    if(typeof (salaryIdObj) != "undefined"){
			    var salary = salaryIdObj != '' ? salaryIdObj : 0;
		    	totalSalary = totalSalary + parseInt(salary);
		    	cnt++;
				$("#index" + i).html(cnt);
		    }
	    }
		$("#TOTAL_NUM").val(cnt);
		$("#TOTAL_SALARY").val(totalSalary);
		$("#TOTAL_NUM_TEXT").html(cnt);
		$("#TOTAL_SALARY_TEXT").html(totalSalary);
		if(cnt>0){
			$("#tempSalaryEmpPanel").css('display','');
		}else{
			$("#tempSalaryEmpPanel").css('display','none');
		}
	}

	function saveTempSalary(flag){
		$("#viewAddPaTempSalesFLAG").val(flag);
		$("#viewAddPaTempSales").submit();
	}

	function checkSubmit(){
		var addEmpInfoTable = $("table[tableName='addEmpInfoTable']") ;
		if(addEmpInfoTable.length == 0){
			alertMsg.error("请先添加至少一个临促人员信息后再提交，否则只能保存。");
			return false;
		}else{
			return true;
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
	var ajaxGet_add_pa0701_add;
	function ajaxAdd_add_pa0701_add() {
		alert($(":input[sysLong='viewAddPaTempSales_seachDept']").val());
		if (ajaxGet_add_pa0701_add != null) {
			ajaxGet_add_pa0701_add.abort();
		}
		$.ajaxSettings.global = false;
		ajaxGet_add_pa0701_add = $.ajax( {
				type : "POST",
				url : "/pa/tempsale/getAffirmorInfo",
				data : {DEPT_NO : $(":input[sysLong='viewAddPaTempSales_seachDept']").val()},
				dataType : "json",
				success : function(data) {
					$('#addAffirm_listPa0701 tbody').html("");
					var html = "";
					if (typeof (data['affirmList']) != "undefined") {
						$.each(data['affirmList'],
										function(commentIndex, comment) {
											html += '<tr id="rowIdPa0701' + commentIndex  + '">';
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
					$('#addAffirm_listPa0701 tbody').html(html);
				}
		});
		$.ajaxSettings.global = true;
	}
	//文件上传的js方法
  function uploadifySuccess_Lc(file, data, response){
  //获取后台返回到前台的文件名，添加到隐藏域,多文件用";"号隔开
  var files = $("#fileNmae1_Lc").html();
  var fileUrl = $("#fileUrl_Lc").val();
  var fileName = $("#fileName_Lc").val();
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
  $("#fileNmae1_Lc").html(files);
  $("#fileUrl_Lc").val(fileUrl);
  $("#fileName_Lc").val(fileName);
}
</script>
<div class="pageContent">
	<form id="viewAddPaTempSales" method="post" action="/pa/tempsale/addPaTempSales" class="pageForm required-validate" onsubmit="return validateCallbackViewPaTempSales(this, dialogAjaxDone)">
		<div class="pageFormContent">
			<table width="100%"  border="0" cellpadding="0" cellspacing="0" class="user_table margin_b">
				<tr>
					<td class="td_title"  width="20%">
						Event名称
					</td>
					<td class="td_type" width="30%">
						（自动生成：内容+开始日期）
					</td>
					<td class="td_title"  width="18%">
						Event ID
					</td>
					<td class="td_type"  width="30%">
						${EVENT_ID }
						<input type="hidden" name="EVENT_ID" value="${EVENT_ID }" />
					</td>
					<!--<td rowspan='4'  width="2%"><img src="/resources/css/ligerUI/skins/icons/add.gif" border="0" align="absmiddle" style="cursor:hand" onclick="addrow()"/></td>
				--></tr>
				<tr>
					<td class="td_title" >
						开始日期
					</td>
					<td class="td_type">
						<input type="text" id="START_DATE" name="START_DATE" class="required date" readonly="true" format="yyyy-MM-dd" yearstart="-50" yearend="5" onclick="setdate(this);" maxlength="20" size="30"/>
						<a class="inputDateButton"><spring:message code="public.title.choose" /><!-- 选择 --></a>
					</td>
					<td class="td_title" >
						结束日期
					</td>
					<td class="td_type">
						<input type="text" id="END_DATE" name="END_DATE" class="required date" readonly="true" format="yyyy-MM-dd" yearstart="-50" yearend="5" onclick="setdate(this);" maxlength="20" size="30"/>
						<a class="inputDateButton"><spring:message code="public.title.choose" /><!-- 选择 --></a>
					</td>
				</tr>
				<tr>
					<td class="td_title" >
						EVENT部门
					</td>
					<td class="td_type">
						<select name="DEPTNO" id="event_deptno">
							<option value="">请选择</option>
							<c:forEach items="${branchList}" var="item" varStatus="i">
								<option value="${item.ACC_ORG_CODE }">${item.CONTENT}</option>
							</c:forEach>
						</select>
					</td>
					<td class="td_title" >
						工资支付月
					</td>
					<td class="td_type">
						<ait:date yearName="paYear" yearSelected="${YEAR}"
							monthName="paMonth" monthSelected="${MONTH}" />
					</td>
<!--  					<td class="td_title" >
						EVENT门店
					</td>
					<td class="td_type">
						<select id="EVENT_STORE_CODE_add" name="EVENT_STORE_CODE">
							<OPTION value="">请选择</OPTION>
						</select>
					</td>-->
				</tr>
				<tr>
					<td class="td_title" >
						EVENT内容
					</td>
					<td class="td_type" colspan="4">
						<input type="text" name="EVENT_CONTENT" class="required textInput" maxlength="100" size="100"/>
					</td>
				</tr>
			<!--</table>
			<div class="panel collapse" id="tempSalaryEmpPanel" style="display:none;">
			<h1>
				临促人员信息
			</h1>
			<div id="createTable" width="100%"></div>
			</div>
			<table width="100%"  border="0" cellpadding="0" cellspacing="0" class="user_table margin_b">
				<tr>
					<td class="td_title"  width="20%">
						总人数
					</td>
					<td class="td_type" width="30%">
						<span id="TOTAL_NUM_TEXT">0</span>
						<input type="hidden" id="TOTAL_NUM" name="TOTAL_NUM" value="0"  />
					</td>
					<td class="td_title"  width="18%">
						总金额
					</td>
					<td class="td_type"  width="30%">
						<span id="TOTAL_SALARY_TEXT">0</span>
						<input type="hidden" id="TOTAL_SALARY" name="TOTAL_SALARY" value="0"  />
					</td>
				</tr>-->
				<tr>
					<td class="td_title" >
						备注
					</td>
					<td class="td_type" colspan="4">
						<input type="text" name="REMARK" class="textInput" maxlength="100" size="100"/>
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
						queueID:'fileQueue_Lc',
						buttonText:'请选择',
						height:25,
						width:50,
						auto:false,
						onUploadSuccess:uploadifySuccess_Lc,
						removeTimeout:1
					    }" />
					<span id="fileNmae1_Lc"></span>
					<div id="fileQueue_Lc" class="fileQueue"></div> 
					<input type="hidden" id="fileUrl_Lc" name="fileUrl" value="" /> 
					<input type="hidden" id="fileName_Lc" name="fileName" value="" />

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
						<table width="100%" border="0" cellpadding="0" cellspacing="0" id="addAffirm_listPa0701">
							<tbody>
							<c:forEach items="${affirmorList}" var="affirmor" varStatus="j">	
								<tr id="rowIdPa0701${j.index}">
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
											border="0" align="absmiddle" style="cursor:hand" onclick="addRowByIDPa0701(${j.index})"/>
									</c:if>
									</td>
								</tr>
							</c:forEach>
							</tbody>
						</table>
					</td>
				</tr>
			</table>
		    --><input type="hidden" name="count" id="count" value="0">
		    <input type="hidden" name="affirmCount" id="affirmCount" value="${affirmorListCnt }">
		    <input type="hidden" id="viewAddPaTempSalesFLAG" name="FLAG" value="" />
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="saveTempSalary(1);"><spring:message code="public.title.submit"/><!-- 提交 --></button></div></div></li>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="saveTempSalary(0);">暂存</button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close"><spring:message code="public.title.cancle"/><!-- 取消 --></button></div></div>
				</li>
			</ul>
		</div>
	</form>	
</div>