<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
//添加决裁者
function addRowByIDCx1602(currentRowID){
	var count = parseInt($("#affirmCount").val());
    var htm  ='<tr id="rowIdCx1602'+ count +'"><td class="td_type" style="text-align: center" width="25%"><span name="rowIndex"></span></td>';
		htm +='<td class="td_type" style="text-align: center" width="37.5%">';
		htm +='<input id="AFFIRMOR_IDCx1602' + count + '" name="AFFIRMOR_ID" value="" type="hidden"/>';
		htm +='<input id="EMPINFOCx1602' + count + '" name="empid" type="text" class="required" lookupGroup="person" onkeydown="submitKeyClick_affirmor(this,' + count + ',event)" class="required"/>';
		htm +='</td>';
		htm +='<td class="td_type" style="text-align: center" width="37.5%">';
		htm +='<img src="/resources/images/+.gif" title="添加"';	
		htm +='border="0" align="absmiddle" style="cursor:hand" onclick="addRowByIDCx1602(' + count + ')"/>&nbsp;&nbsp;&nbsp;';
		htm +='<img src="/resources/images/-.gif" title="删除"';	
		htm +='border="0" align="absmiddle" style="cursor:hand" onclick="javaScript:document.all.addAffirm_listCx1602.deleteRow(event.srcElement.parentElement.parentElement.rowIndex);changeAffirmLevel();"/></td></tr>';

   	//当前行之后插入一行
   	$("#rowIdCx1602" + currentRowID).after(htm);
   	changeAffirmLevel();
  	$("#affirmCount").val(++count) ;
}
function addRowByID1602First(){
	var count = parseInt($("#affirmCount").val());
    var htm  ='<tr id="rowIdCx1602'+ count +'"><td class="td_type" style="text-align: center" width="25%"><span name="rowIndex"></span></td>';
		htm +='<td class="td_type" style="text-align: center" width="37.5%">';
		htm +='<input id="AFFIRMOR_IDCx1602' + count + '" name="AFFIRMOR_ID" value="" type="hidden"/>';
		htm +='<input id="EMPINFOCx1602' + count + '" name="empid" type="text" class="required" lookupGroup="person" onkeydown="submitKeyClick_affirmor(this,' + count + ',event)" class="required"/>';
		htm +='</td>';
		htm +='<td class="td_type" style="text-align: center" width="37.5%">';
		htm +='<img src="/resources/images/+.gif" title="添加"';	
		htm +='border="0" align="absmiddle" style="cursor:hand" onclick="addRowByIDCx1602(' + count + ')"/>&nbsp;&nbsp;&nbsp;';
		htm +='<img src="/resources/images/-.gif" title="删除"';	
		htm +='border="0" align="absmiddle" style="cursor:hand" onclick="javaScript:document.all.addAffirm_listCx1602.deleteRow(event.srcElement.parentElement.parentElement.rowIndex);changeAffirmLevel();"/></td></tr>';

	var tb2 = document.getElementById("addAffirm_listCx1602");
   	if(tb2.rows.length == 0){
   		$("#addAffirm_listCx1602:last tbody").html(htm);
   	} else {
   	   	//当前行之后插入一行
   	   	$("#" + tb2.rows[0].id).before(htm);
   	}
  	$("[id='EMPINFOCx1602" + count + "']").attr("alt","请输入关键字按回车检索").attr("size","30").inputAlert();
  	changeAffirmLevel();
  	$("#affirmCount").val(++count) ;
}
//修改决裁者等级
function changeAffirmLevel(){
	var tb2 = document.getElementById("addAffirm_listCx1602");
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
			url: encodeURI('/sys/affirm/getPersonCntByEmpid?EMPID='+empid ),
			dataType:"json",
			cache: false,
			success: function(jsonObject){
						if (jsonObject.perCnt!=1){
							alertMsg.error('<spring:message code="alert.message.sys.affirm.searchNoPerson"/>');
						}
						if(jsonObject.perCnt==1){
							$("#EMPINFOCx1602" + index).val('['+jsonObject.empId + ']-'+jsonObject.empName);
							$("#AFFIRMOR_IDCx1602" + index).val( jsonObject.personId);
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
    function validateCallbackViewSelloutReq(form, callback) {
		var $form = $("#viewSelloutReqDtl");
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

	function saveSelloutReq(flag){
		$("#viewAddSelloutReqFLAG").val(flag);
		$("#viewSelloutReqDtl").submit();
	}

	var ajaxGet_add_Cx1602_add;
	function ajaxAdd_add_Cx1602_add() {
		alert($(":input[sysLong='viewAddPaTempSales_seachDept']").val());
		if (ajaxGet_add_Cx1602_add != null) {
			ajaxGet_add_Cx1602_add.abort();
		}
		$.ajaxSettings.global = false;
		ajaxGet_add_Cx1602_add = $.ajax( {
				type : "POST",
				url : "/pa/tempsale/getAffirmorInfo",
				data : {DEPT_NO : $(":input[sysLong='viewAddPaTempSales_seachDept']").val()},
				dataType : "json",
				success : function(data) {
					$('#addAffirm_listCx1602 tbody').html("");
					var html = "";
					if (typeof (data['affirmList']) != "undefined") {
						$.each(data['affirmList'],
										function(commentIndex, comment) {
											html += '<tr id="rowIdCx1602' + commentIndex  + '">';
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
					$('#addAffirm_listCx1602 tbody').html(html);
				}
		});
		$.ajaxSettings.global = true;
	}
	function expReqDtlInfo(expType){
		var sform = document.getElementById("viewSelloutReqDtl");
		var eForm = document.getElementById("excelForm_cx1602");

		eForm.REQ_ID.value		= sform.REQ_ID.value;

		eForm.submit();
	}

	function uploadSuccess_SellDtl(file, data, response){
		  //获取后台返回到前台的文件名，添加到隐藏域,多文件用";"号隔开
		  var Sell_files = $("#Sell_fileNmae").html();
		  var Sell_fileUrl = $("#Sell_fileUrl").val();
		  var Sell_fileName = $("#Sell_fileName").val();
		  var Sell_fileResult = data.split(";");
		  //第一个文件
		  if(Sell_files=="" || Sell_files==null){
			  Sell_files = Sell_fileResult[0];
			  Sell_fileName = Sell_fileResult[0];
			  Sell_fileUrl = Sell_fileResult[1];
		  }else{
			  Sell_files+=";"+Sell_fileResult[0];
			  Sell_fileName+=";"+Sell_fileResult[0];
			  Sell_fileUrl+=";"+Sell_fileResult[1];
		  }
		  $("#Sell_fileNmae").html(Sell_files);
		  $("#Sell_fileUrl").val(Sell_fileUrl);
		  $("#Sell_fileName").val(Sell_fileName);
	}

</script>
<div class="pageContent">
	<form id="excelForm_cx1602" name="excelForm_cx1602" action="/promoter/expSelloutReqDtlExcel" method="post">
	    <input type="hidden" id="REQ_ID" 		name="REQ_ID" 	value="" />
	</form>
	<form id="viewSelloutReqDtl" method="post" action="/promoter/addSelloutReq" class="pageForm required-validate" onsubmit="return validateCallbackViewSelloutReq(this, dialogAjaxDone)">
<div style="clear: both;"></div>
<div class="tabs" currentIndex="${tabsSelected }" eventType="click">
<div class="tabsHeader">
<div class="tabsHeaderContent">
<ul>
	<c:forEach items="${menuThirdList}" var="menu" varStatus="i">
		<li><a href="javascript:;"><span>${menu.MENU_NAME } </span> </a>
		</li>
	</c:forEach>
</ul>
</div>
</div>
<div class="tabsContent" id="displaycheckbox">
<c:forEach items="${menuThirdList}" var="menu" varStatus="i">
	<c:if test="${menu.MENU_CODE eq 'cx1601'}">
	  <div style="display: block;" id="displaySellPanel_1">
		<div class="pageFormContent">
			<table width="100%"  border="0" cellpadding="0" cellspacing="0" class="user_table margin_b">
				<tr>
					<td class="td_title"  width="20%">
						申请类容
					</td>
					<td class="td_type" width="80%">
					<c:if test="${REQ_ID eq null}">
						（自动生成：[提成实绩]+支社+月份+[申请日期]）
					</c:if>
					<c:if test="${REQ_ID ne null}">
						${mstList.REQ_TITLE}
					</c:if>
					</td>
				</tr>
			</table>
			<input type="hidden" name="REQ_ID" id="REQ_ID" value="${REQ_ID}">
		    <input type="hidden" name="viewAddSelloutReqFLAG" id="viewAddSelloutReqFLAG" value="" />
			<input type="hidden" name="PAY_AREA_CD" id="PAY_AREA_CD" value="${mstList.PAY_AREA_CD}">
			<input type="hidden" name="BRANCH_CD" id="BRANCH_CD" value="${mstList.BRANCH_CD}">
			<input type="hidden" name="year" id="year" value="${mstList.YEAR}">
			<input type="hidden" name="month" id="month" value="${mstList.MONTH}">
			<input type="hidden" name="count" id="count" value="0">
			<input type="hidden" name="affirmCount" id="affirmCount" value="${fn:length(affirmorList)}">
		
		    <table class="table" width="100%" layoutH="500">
		    <thead>
		      <tr>
		        <th width="10%">大区 </th>
		        <th width="10%">支社</th>
		        <th width="8%">产品类型</th>
		        <th width="5%">SALS_QTY</th>
		        <th width="5%">支社核对</th>
		        <th width="7%">核对差异</th>
		        <th width="7%">发票销量</th>
		        <th width="7%">截屏及凭证销量</th>
		      </tr>
		    </thead>
		    <tbody>
		    <c:forEach items="${itemList}" var="item" varStatus="i">
			  <tr>
			    <c:if test="${item.PNUM eq 1 and menu.MENU_CODE eq 'cx1601'}">
				<td class='td_center' rowspan="${item.PCOUNT}">${item.PAY_AREA_NM}</td>
				<td class='td_center' rowspan="${item.PCOUNT}">${item.BRANCH_NM}</td>
				</c:if>
				<td class='td_center'>${item.PROD_TP_NM}</td>
				<td class='td_right'>${item.TOTAL_NUM}</td>
				<td class='td_right'>${item.BRANCH_QTY}</td>
				<td class='td_right'>${item.TOTAL_NUM - item.BRANCH_QTY}</td>
			    <c:if test="${item.PNUM eq 1 and mstList.SUBMIT_STATUS eq 0 }">
				<td class='td_right' rowspan="${item.PCOUNT}"><input id="INVOICE_QTY" name="INVOICE_QTY" style="width:60px" value="${item.INVOICE_QTY}" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')"/></td>
				<td class='td_right' rowspan="${item.PCOUNT}"><input id="SCREEN_QTY"name="SCREEN_QTY" style="width:60px" value="${item.SCREEN_QTY}" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')"/></td>
				</c:if>
			    <c:if test="${item.PNUM eq 1 and mstList.SUBMIT_STATUS ne 0 }">
				<td class='td_right' rowspan="${item.PCOUNT}">${item.INVOICE_QTY}</td>
				<td class='td_right' rowspan="${item.PCOUNT}">${item.SCREEN_QTY}</td>
				</c:if>
			  </tr>
		    </c:forEach>
		    </tbody>
		    </table>
			<table width="100%"  border="0" cellpadding="0" cellspacing="0" class="user_table margin_b">
			<tr>
				<td class="td_title" style="text-align: center" width="20%" rowspan="2">决裁线</td>
				<td class="td_title" style="text-align: center" width="20%">决裁等级</td>
				<td class="td_title" style="text-align: center" width="30%">决裁者</td>
				<c:if test="${mstList.SUBMIT_STATUS eq '0'}">
				<td class="td_title" style="text-align: center" width="30%">是否新增(<img src="/resources/images/+.gif" title="添加" border="0" align="absmiddle" style="cursor:hand" onclick="addRowByID1602First()"/>)</td>
				</c:if>
				<c:if test="${mstList.SUBMIT_STATUS ne '0'}">
				<td class="td_title" style="text-align: center" width="30%">决裁意见</td>
				</c:if>
			</tr>
			<tr>
				<td colspan="3">
					<table width="100%" border="0" cellpadding="0" cellspacing="0" id="addAffirm_listCx1602">
						<tbody>
							<c:forEach items="${affirmorList}" var="affirmor" varStatus="j">
								<tr id="rowIdCx1602${j.index}">
									<td class="td_type" style="text-align: center" width="25%">${j.count}</td>
									<td class="td_type" style="text-align: center" width="37.5%">
										[${affirmor.EMPID}]-${affirmor.LOCAL_NAME }
										<input type="hidden" name="AFFIRMOR_ID" value="${affirmor.AFFIRMOR_ID }" />
									</td>
									<c:if test="${mstList.SUBMIT_STATUS eq '0'}">
									<td class="td_type" style="text-align: center" width="37.5%">
									<!-- c:if test="${fn:length(affirmorList) gt j.count}"> -->
										<img src="/resources/images/+.gif" title="添加" border="0"
											align="absmiddle" style="cursor: hand" onclick="addRowByIDCx1602(${j.index})" />
									<!-- /c:if> -->
									</td>
									</c:if>
									<c:if test="${mstList.SUBMIT_STATUS ne '0'}">
									<td class="td_type" style="text-align: center" width="37.5%">
										${affirmor.AFFIRM_CONTENT}
									</td>
									</c:if>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</td>
			</tr>
		    </table>
		    
		    <table width="100%"  border="0" cellpadding="0" cellspacing="0" class="user_table margin_b">
			<tr>
				<td width="20%" class="td_title" style="text-align:center">
					附件上传
				</td>
				<td width="80%" class="td_type">
				  <c:if test="${mstList.SUBMIT_STATUS eq '0'}">
				    <input id="reqSell_file" type="file" name="file" 
							uploaderOption="{
								swf:'/resources/js/uploadify/scripts/uploadify.swf',
								uploader:'/ess/infoApplyLeave/uploadBatch?PERSON_ID=${PERSON_ID}',
								formData:{ajax:1},
								queueID:'fileQueue_Sell',
								buttonText:'请选择',
								height:25,
								width:50,
								auto:false,
								onUploadSuccess:uploadSuccess_SellDtl,
								removeTimeout:1
							}"
						/>
				    </c:if><span id="Sell_fileNmae">${SellDtl_fileNmae}</span>
					  <div id="fileQueue_Sell" class="fileQueue" style="height:30px"></div>
					  <input type="hidden" id="Sell_fileUrl" name="Sell_fileUrl" value="${SellDtl_fileUrl}"/>
					  <input type="hidden" id="Sell_fileName" name="Sell_fileName" value="${SellDtl_fileNmae}"/>
				  <c:if test="${mstList.SUBMIT_STATUS eq '0'}">
						<div class="buttonActive">
							<div class="buttonContent"><!--保存-->
								<button type="button" onclick="$('#reqSell_file').uploadify('upload', '*');return false;">
									上传
								</button>
							</div>
						</div>
						<div class="buttonActive">
							<div class="buttonContent"><!--提交-->
								<button type="button" onclick="$('#reqSell_file').uploadify('cancel', '*');return false;">
									取消
								</button>
							</div>
						</div>
				    </c:if>
				</td>
			</tr>
		    </table>
		    
		</div>
	  </div>
	</c:if>
	<c:if test="${menu.MENU_CODE eq 'cx1602'}">
	  <div style="display: block;" id="displaySellPanel_2">
		<div class="pageFormContent">
		    <table class="table" width="200%" layoutH="150">
		    <thead>
		      <tr>
		        <th>门店编码</th>
		        <th>PR社番</th>
		        <th>销售日期</th>
		        <th>产品型号</th>
		        <th>PR上报销量汇总</th>
		        <th>办事处</th>
		        <th>卖场名称</th>
		        <th>门店等级</th>
		        <th>渠道等级</th>
		        <th>渠道</th>
		        <th>Bill to渠道</th>
		        <th>支付比例(%)</th>
		        <th>支社确认销量</th>
		      </tr>
		    </thead>
		    <tbody>
		    <c:forEach items="${reqOver10List}" var="item2" varStatus="i">
			  <tr>
				<td class='td_center'>${item2.SHOP_CD}</td>
				<td class='td_center'>${item2.PR_EMPID}</td>
				<td class='td_center'>${item2.SALE_DATE}</td>
				<td class='td_center'>${item2.PROD_ID}</td>
				<td class='td_center'>${item2.SALS_QTY}</td>
				<td class='td_center'>${item2.BRANCH}</td>
				<td class='td_center'>${item2.SHOP_NAME}</td>
				<td class='td_center'>${item2.SHOP_LEVEL}</td>
				<td class='td_center'>${item2.CHANNEL_GRADE}</td>
				<td class='td_center'>${item2.CHANNEL_NAME}</td>
				<td class='td_center'>${item2.BILL_TO_NAME}</td>
				<td class='td_center'>
				    <c:if test="${mstList.SUBMIT_STATUS eq '0'}">
				        <!-- input type="hidden" name="SELL_SEQ_CX1601" value="${item2.SEQ}" />
				        <input type="text" id="PAY_RATE_CX1601${i.index}" name="PAY_RATE_CX1601${i.index}" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')" value="${item2.PAY_RATE}"/>
				         -->
				        ${item2.PAY_RATE}
				    </c:if>
				    <c:if test="${mstList.SUBMIT_STATUS ne '0'}">
				        ${item2.PAY_RATE}
				    </c:if>
				</td>
				<td class='td_center'>${item2.REAL_QTY}</td>
			  </tr>
		    </c:forEach>
		    </tbody>
		    </table>
		</div>
	  </div>
	</c:if>
	<c:if test="${menu.MENU_CODE eq 'cx1603'}">
	  <div style="display: block;" id="displaySellPanel_3">
		<div class="pageFormContent">
		    <table class="table" width="150%" layoutH="150">
		    <thead>
		      <tr>
		        <th>办事处</th>
		        <th>社号</th>
		        <th>姓名</th>
		        <th>门店代码</th>
		        <th>主责商场</th>
		        <th>channel</th>
		        <th>主责产品</th>
		        <th>本月销售金额</th>
		        <th>本月销量</th>
		        <th>本月预计提成</th>
		      </tr>
		    </thead>
		    <tbody>
		    <c:forEach items="${reqOver9kList}" var="item3" varStatus="i">
			  <tr>
				<td class='td_center'>${item3.BRANCH}</td>
				<td class='td_center'>${item3.EMPID}</td>
				<td class='td_center'>${item3.LOCAL_NAME}</td>
				<td class='td_center'>${item3.SHOP_CD}</td>
				<td class='td_center'>${item3.SHOP_NAME}</td>
				<td class='td_center'>${item3.CHANNEL_NAME}</td>
				<td class='td_center'>${item3.PROD_TP}</td>
				<td class='td_center'>${item3.SALS_AMT}</td>
				<td class='td_center'>${item3.SALS_QTY}</td>
				<td class='td_center'>${item3.INC_AMT}</td>
			  </tr>
		    </c:forEach>
		    </tbody>
		    </table>
		</div>
	  </div>
	</c:if>
	<c:if test="${menu.MENU_CODE eq 'cx1604'}">
	  <div style="display: block;" id="displaySellPanel_4">
		<div class="pageFormContent">
		    <table class="table" width="200%" layoutH="150">
		    <thead>
		      <tr>
		        <th>支社</th>
		        <th>社号</th>
		        <th>姓名</th>
		        <th>门店代码</th>
		        <th>主责商场</th>
		        <th>channel</th>
		        <th>主责产品</th>
		        <th>上月确认销量</th>
		        <th>上月实发提成</th>
		        <th>本月确认销量</th>
		        <th>本月预计提成</th>
		        <th>对比上月伸张率%</th>
		      </tr>
		    </thead>
		    <tbody>
		    <c:forEach items="${reqRatioList}" var="item4" varStatus="i">
			  <tr>
				<td class='td_center'>${item4.BRANCH}</td>
				<td class='td_center'>${item4.EMPID}</td>
				<td class='td_center'>${item4.LOCAL_NAME}</td>
				<td class='td_center'>${item4.SHOP_CD}</td>
				<td class='td_center'>${item4.SHOP_NAME}</td>
				<td class='td_center'>${item4.CHANNEL_NAME}</td>
				<td class='td_center'>${item4.PROD_TP}</td>
				<td class='td_center'>${item4.LAST_SALS_QTY}</td>
				<td class='td_center'>${item4.LAST_INC_AMT}</td>
				<td class='td_center'>${item4.SALS_QTY}</td>
				<td class='td_center'>${item4.INC_AMT}</td>
				<td class='td_center'>${item4.RATIO}</td>
			  </tr>
		    </c:forEach>
		    </tbody>
		    </table>
		</div>
	  </div>
	</c:if>
	<c:if test="${menu.MENU_CODE eq 'cx1605'}">
	  <div style="display: block;" id="displaySellPanel_5">
		<div class="pageFormContent">
		    <table class="table" width="200%" layoutH="150">
		    <thead>
		      <tr>
		        <th>支社</th>
		        <th>社号</th>
		        <th>姓名</th>
		        <th>门店代码</th>
		        <th>主责商场</th>
		        <th>channel</th>
		        <th>主责产品</th>
		        <th>上月确认销量</th>
		        <th>上月实发提成</th>
		        <th>本月确认销量</th>
		        <th>本月预计提成</th>
		        <th>对比上月伸张率%</th>
		      </tr>
		    </thead>
		    <tbody>
		    <c:forEach items="${reqExshopList}" var="item5" varStatus="i">
			  <tr>
				<td class='td_center'>${item5.BRANCH}</td>
				<td class='td_center'>${item5.EMPID}</td>
				<td class='td_center'>${item5.LOCAL_NAME}</td>
				<td class='td_center'>${item5.SHOP_CD}</td>
				<td class='td_center'>${item5.SHOP_NAME}</td>
				<td class='td_center'>${item5.CHANNEL_NAME}</td>
				<td class='td_center'>${item5.PROD_TP}</td>
				<td class='td_center'>${item5.LAST_SALS_QTY}</td>
				<td class='td_center'>${item5.LAST_INC_AMT}</td>
				<td class='td_center'>${item5.SALS_QTY}</td>
				<td class='td_center'>${item5.INC_AMT}</td>
				<td class='td_center'>${item5.RATIO}</td>
			  </tr>
		    </c:forEach>
		    </tbody>
		    </table>
		</div>
	  </div>
	</c:if>
	<c:if test="${menu.MENU_CODE eq 'cx1606'}">
	  <div style="display: block;" id="displaySellPanel_6">
		<div class="pageFormContent">
		    <table class="table" width="200%" layoutH="150">
		    <thead>
		      <tr>
		        <th>COME_CODE</th>
		        <th>社号</th>
		        <th>姓名</th>
		        <th>月份</th>
		        <th>销售日期</th>
		        <th>产品类型</th>
		        <th>产品ID</th>
		        <th>支社</th>
		        <th>客户ID</th>
		        <th>门店名称</th>
		        <th>CHANNEL_CODE</th>
		        <th>支社确认销量</th>
		        <th>NOTICE PRICE</th>
		        <th>SELLOUT PRICE</th>
		        <th>SEQ</th>
		      </tr>
		    </thead>
		    <tbody>
		    <c:forEach items="${reqReportList}" var="item6" varStatus="i">
			  <tr>
				<td class='td_center'>${item6.COM_CODE}</td>
				<td class='td_center'>${item6.EMP_NO}</td>
				<td class='td_center'>${item6.EMP_NM}</td>
				<td class='td_center'>${item6.SALE_MONTH}</td>
				<td class='td_center'>${item6.SALE_DAY}</td>
				<td class='td_center'>${item6.MODEL_CATEGORY_CODE}</td>
				<td class='td_center'>${item6.MODEL_CODE}</td>
				<td class='td_center'>${item6.BRANCH}</td>
				<td class='td_center'>${item6.SHIP_TO_CODE}</td>
				<td class='td_center'>${item6.SHIP_TO_NAME}</td>
				<td class='td_center'>${item6.CHANNEL_CODE}</td>
				<td class='td_center'>${item6.SALE_QTY}</td>
				<td class='td_center'>${item6.NOTICE_PRICE}</td>
				<td class='td_center'>${item6.SELLOUT_PRICE}</td>
				<td class='td_center'>${item6.SEQ}</td>
			  </tr>
		    </c:forEach>
		    </tbody>
		    </table>
		   共${TOTALREQCNT}条，更多记录请下载。
		</div>
	  </div>
	</c:if>
</c:forEach>
</div>
<div class="tabsFooter">
<div class="tabsFooterContent"></div>
</div>
</div>
		<div class="formBar">
			<ul>
		  <c:if test="${mstList.SUBMIT_STATUS eq '0'}">
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="saveSelloutReq(1);"><spring:message code="public.title.submit"/><!-- 提交 --></button></div></div></li>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="saveSelloutReq(0);">暂存</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close"><spring:message code="public.title.cancle"/><!-- 取消 --></button></div></div></li>
		  </c:if>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="expReqDtlInfo(0);">下载</button></div></div></li>
			</ul>
		</div>
	</form>	
</div>