<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.easydrag.js"></script>
<script type="text/javascript">
<!--
//注意input的id和tr的id要一样
function addRowByIDess0520_affirm(currentRowID){
	var count = parseInt($("#affirmCount_affirm").val());
    var htm  ='<tr id="rowIdess0520_affirm'+ count +'"><td class="td_type" style="text-align: center" width="33%"><span name="rowIndex"></span></td>';
		htm +='<td class="td_type" style="text-align: center" width="33%">';
		htm +='<input id="AFFIRMOR_IDess0520_affirm' + count + '" name="AFFIRMOR_ID" value="" type="hidden"/>';
		htm +='<input id="EMPINFOess0520_affirm' + count + '" name="empid" type="text" class="required" lookupGroup="person" onkeydown="submitKeyClick_affirmor_affirm(this,' + count + ',event)" class="required"/>';
		htm +='</td>';
		htm +='<td></td>';
		htm +='<td></td>';
		htm +='<td></td>';
		htm +='<td style="text-align: center">';
		htm +='<img src="/resources/images/+.gif" title="添加"';	
		htm +='border="0" align="absmiddle" style="cursor:hand" onclick="addRowByIDess0520_affirm(' + count + ')"/>&nbsp;&nbsp;&nbsp;';
		htm +='<img src="/resources/images/-.gif" title="删除"';	
		htm +='border="0" align="absmiddle" style="cursor:hand" onclick="javaScript:document.all.addAffirm_listess0520_affirm.deleteRow(event.srcElement.parentElement.parentElement.rowIndex);changeAffirmLeveless0520_affirm();"/></td></tr>';

   	//当前行之后插入一行
   	$("#rowIdess0520_affirm" + currentRowID).after(htm);
   	changeAffirmLeveless0520_affirm();
  	$("#affirmCount_affirm").val(++count) ;
}
//修改决裁者等级
function changeAffirmLeveless0520_affirm(){
	var tb2 = document.getElementById("addAffirm_listess0520_affirm");
	var rowCount = tb2.rows.length;
	for(var m=0;m<rowCount;m++){
		tb2.rows[m].cells[0].innerHTML = m+1;
	}
}
var keyCodeInit=0;
function submitKeyClick_affirmor_affirm(obj,index,event){
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
			url: encodeURI('/sys/affirm/getPersonCntByEmpid?navTabId=' + navTabId + '&EMPID='+empid+'&LOCAL_NAME='+localName+'&IDCARD_NO='+idcardNo),
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
							$("#EMPINFOess0520_affirm" + index).val('['+jsonObject.empId + ']-'+jsonObject.empName);
							$("#AFFIRMOR_IDess0520_affirm" + index).val( jsonObject.personId);
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
	function affirmTempSalary(flag){
		$("#viewAffirmSellOutFLAG").val(flag);
		var pro_flag = $("#pro_flag").val();
		if(pro_flag == 0){
			$.ajax({
				type:$("#viewAffirmSellOut").method || 'POST',
				url:$("#viewAffirmSellOut").attr("action"),
				data:$("#viewAffirmSellOut").serializeArray(),
				dataType:"json",
				cache: false,
				success: function(json){
					if (json.statusCode == 200){
						alert(json.message);
						window.location.href=window.location.href;
					}else{
						alert(json.message);
						$("#pro_flag").val("0");
					}
				}
			});
		}else{
			alert("审批处理中，请稍等。。。");
		}
	}
	function addCheckess0520(essAffirmNo,AFFIRMOR_ID){
		var returnValue =  window.showModalDialog("/LGEP/affirm/addCheckInfo?APPLY_TYPE=219983&APPLY_NO=${reqInfo.REQ_ID}&ESS_AFFIRM_NO=" + essAffirmNo + "&AFFIRMOR_ID=" + AFFIRMOR_ID,window,"dialogWidth=500px;dialogHeight=300px;help=no;center=yes;resizable=no;status=no;scroll=no");
		if(returnValue == "true"){
	     	window.location.reload();
	    }
	}
//-->
</script>
<script type="text/javascript">
<!--
function submitSellAffirm(flag){
	$("#viewAffirmSellOutFLAG").val(flag);
  	var $from = $("#sellOutAffirmInfo");
  	$from.submit();
}

function expReqDtlInfoB(expType){
	var eForm = document.getElementById("excelForm_ess0520");
	eForm.action="/LGEP/affirm/expSelloutReqDtlExcelA?CPNY_ID=LGECH&LANGUAGE=ZH&REQ_ID=${REQ_ID}";
	eForm.submit();
}

function validateSellOutAffirmCallback(form,callback) {
	var $form = $(form);
	if (!$form.valid()) {
		return false;
	}
    var affirmFlag = document.getElementById("viewAffirmSellOutFLAG").value;
    
	var result = "";
	if(affirmFlag==1){
		result = "确定要通过此条申请信息？";
	}else if(affirmFlag==2){
		result = "确定要否决此条申请信息？";
	}
	if (confirm (result)){	          
		$.ajax({
			type: form.method || 'POST',
			url:$form.attr("action"),
			data:$form.serializeArray(),
			dataType:"json",
			cache: false,
			success: callback || DWZ.ajaxDone,
			error: DWZ.ajaxError
		});		
	}
	return false;
}

function change_name(thisvalue){
	//拼接字符串
	var personId_str="";
	var empId_str="";
	var empName_str="";
	//显示DIV
	var temp_div_name="";
	//隐藏值
	var temp_input_personId = $("#temp_personId").val()!=""?$("#temp_personId").val().split(","):new Array();
	//回传值
	var oldpersonId = $("#personId").val()!=""?$("#personId").val().split(","):new Array();
	var oldempId = $("#empId").val()!=""?$("#empId").val().split(","):new Array();
	var oldempName = thisvalue != "" ? thisvalue.split(",") : new Array();

	//根据隐藏值 嵌套回传值 循环
	if(temp_input_personId.length > 0){
		if(oldpersonId.length > 0){
			for(k = 0 ; k < oldpersonId.length ; k++){
				//boolean
				var flag = true ;
				for(j = 0 ; j < temp_input_personId.length ; j++){
					if(temp_input_personId[j] == oldpersonId[k]){
						flag=false;
						break;
					}
				} 
				if(flag){
					$("#temp_personId").attr("value",$("#temp_personId").val()+","+oldpersonId[k]);
					temp_div_name+="<div id='"+oldpersonId[k]+"' style='color:red;' onclick='removetd(this)'>"+oldempName[k]+"("+oldempId[k]+")</div>";
				}
			} 
			$("#addempshift_name").append(temp_div_name);
		}
		
	}else{
		if(oldpersonId.length > 0){
			for(i = 0 ; i < oldempName.length ; i++){
				temp_div_name+="<div id='"+oldpersonId[i]+"' style='color:red;' onclick='removetd(this)'>"+oldempName[i]+"("+oldempId[i]+")</div>";
			} 
			$("#temp_personId").attr("value",$("#personId").val());
			$("#addempshift_name").append(temp_div_name);
		} 
	}
	
}
//-->
</script>
<div class="pageContent">
  <div class="pageContent">
      <table class="user_table" width="100%">
		<tr>
			<td class="td_title" style="text-align:right;width:6%;">申请人</td>
			<td class="td_type" style="text-align:left;width:10%;">[${reqInfo.CREATED_BY}]${reqInfo.LOCAL_NAME}</td>
			<td class="td_title" style="text-align:right;width:6%;">申请时间</td>
			<td class="td_type" style="text-align: left;width:15%;">${reqInfo.CREATE_DATE}</td>
			<td class="td_title" style="text-align:right;width:6%;">申请内容</td>
			<td class="td_type" style="text-align: left;width:20%;">${reqInfo.REQ_TITLE}</td>
			<td class="td_title" style="text-align:right;width:6%;">附件</td>
			<td class="td_type" style="text-align: left;width:31%;">
			    <c:forEach items="${fileList}" var="file" varStatus="i">
		            &nbsp;<a href="/LGEP/affirm/downloadFile?fileName=${file.FILE_URL }&file=${file.FILE_NAME}" >${file.FILE_NAME}</a>&nbsp;
		        </c:forEach>
			</td>
		</tr>
      </table>
  </div>
  
	<form id="excelForm_ess0520" name="excelForm_ess0520" action="" method="post">
	</form>
	<form id="sellOutAffirmInfo" method="post" action="/LGEP/affirm/affirmSellout" class="pageForm required-validate" 
			onsubmit="return validateSellOutAffirmCallback(this,navTabAjaxDone);">
		<input id="viewAffirmSellOutFLAG" name="viewAffirmSellOutFLAG" type="hidden" value="" />
		<input id="REQ_ID" name="REQ_ID" type="hidden" value="${REQ_ID}" />
		<input id="dept_level" name="dept_level" type="hidden" value="${dept_level}" />
		<div class="formBar">
			<ul>
			<c:if test="${affirm_no ne ''}">
				<li>
					<div class="button">
						<div class="buttonContent"><!-- 通过 -->
							<button type="button" onclick="submitSellAffirm(1)">
								通过
							</button>
						</div>
					</div>
				</li>
				<li>
					<div class="button">
						<div class="buttonContent"><!-- 否决 -->
							<button type="button" onclick="submitSellAffirm(2)">
								否决
							</button>
						</div>
					</div>
				</li>
			</c:if>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" class="close">
								<spring:message code="public.title.close"/><!-- 关闭 -->
							</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
		<div>
			<table width="100%"  border="0" cellpadding="0" cellspacing="0" class="user_table margin_b">
				<tr>
					<td class="td_title" style="text-align: right;width:10%;">
						审批线
					</td>
					<td style="width:90%;">
						<table width="100%" border="0" cellpadding="0" cellspacing="0" id="addAffirm_listess0520_affirm">
							<tbody>
							<tr>
								<td class="td_title" style="text-align: center;width:8%;">
									审批等级
								</td>
								<td class="td_title" style="text-align: center;width:15%;">
									审批者
								</td>
								<td class="td_title" style="text-align: center;width:12%;">
									审批情况
								</td>
								<td class="td_title" style="text-align: center;width:15%;">
									审批时间
								</td>
								<td class="td_title" style="text-align: center;width:36%;">
									审批批注
								</td>
								<td class="td_title" style="text-align: center;width:7%;">
									审批者(+/-)
								</td>
							</tr>
							<c:forEach items="${affirmList}" var="affirmor" varStatus="j">	
								<tr id="rowIdess0520_affirm${j.index}">
									<td class="td_type" style="text-align: center">
										${j.count}
									</td>
									<td class="td_type" style="text-align: center">
										[${affirmor.EMPID}]-${affirmor.LOCAL_NAME }
									</td>
									<td class="td_type" style="text-align: center">
										<c:if test="${affirmor.AFFIRM_FLAG eq '0'}">未决裁</c:if>
										<c:if test="${affirmor.AFFIRM_FLAG eq '1'}">已通过</c:if>
										<c:if test="${affirmor.AFFIRM_FLAG eq '2'}">已否决</c:if>
									</td>
									<td class="td_type" style="text-align: center">
										${affirmor.UPDATE_DATE}
									</td>
									<td class="td_type" style="text-align: center">
										<input type="hidden" name="AFFIRMOR_ID" value="${affirmor.AFFIRMOR_ID }"/>
										<c:if test="${affirmor.ESS_AFFIRM_NO eq affirm_no}">
											<input type="hidden" name="ESS_AFFIRM_NO" value="${affirmor.ESS_AFFIRM_NO}"/>
											<input type="text" name="AFFIRM_CONTENT" size="55" maxlength="200"/>
										</c:if>
										<c:if test="${affirmor.ESS_AFFIRM_NO ne affirm_no}">
											${affirmor.AFFIRM_CONTENT}
										</c:if>
									</td>
									<td class="td_type" style="text-align: center">
									  <c:if test="${affirmor.ESS_AFFIRM_NO eq affirm_no}">
										<img src="/resources/images/+.gif" title="添加"
											border="0" align="absmiddle" style="cursor:hand" onclick="addRowByIDess0520_affirm(${j.index})"/>
									  </c:if>
									</td>
								</tr>
							</c:forEach>
							</tbody>
						</table>
					</td>
				</tr>
				<tr>
					<td class="td_title" style="text-align: right">
						Check
					</td>
					<td>
						<table width="100%" border="0" cellpadding="0" cellspacing="0" id="addACheck_listess0520_affirm">
							<tbody>
							<tr>
								<td class="td_title" style="text-align: center;width:8%;">
									Type
								</td>
								<td class="td_title" style="text-align: center;width:42%;">
									Requests
								</td>
								<td class="td_title" style="text-align: center;width:50%;">
									Checked
								</td>
							</tr>
							<c:forEach items="${checkList}" var="check" varStatus="j">
								<tr>
									<td class="td_type" style="text-align: center">
										public
									</td>
									<td class="td_type">
										[${check.EMPID_R}]-${check.LOCAL_NAME_R }&nbsp;&nbsp;${check.POSITION_NO_R }&nbsp;&nbsp;(${check.DEPTNAME_R })/${check.DATE_R }<br/>
										[Request]${check.CHECK_REASON}
									</td>
									<td class="td_type">
										[${check.EMPID_C}]-${check.LOCAL_NAME_C }&nbsp;&nbsp;${check.POSITION_NO_C }&nbsp;&nbsp;(${check.DEPTNAME_C })
										<c:if test="${check.CHECK_FLAG eq '0'}">
											/未Check
										</c:if>
										<c:if test="${check.CHECK_FLAG ne '0'}">
										/${check.DATE_C }
										</c:if>
										<br/>
										<c:if test="${check.CHECK_FLAG eq '1'}">
											[Check]${check.CHECK_CONTENT}
										</c:if>
										<c:if test="${check.ESS_CHECK_NO eq check_no}">
											[Check]<input type="text" name="CHECK_CONTENT" size="55" maxlength="200"/>
											<input type="hidden" name="ESS_CHECK_NO" value="${check.ESS_CHECK_NO}"/>
											<input type="hidden" name="PERSON_ID" value="${check.CHECKOR_ID}"/>
										</c:if>
										<c:if test="${check.CHECK_FLAG ne '1' && check.ESS_CHECK_NO ne check_no}">
											[Check]
										</c:if>
									</td>
								</tr>
							</c:forEach>
							<c:if test="${checkListCnt == 0}">
								<tr>
									<td class="td_type" style="text-align: center">Public</td>
									<td class="td_type">
										无
									</td>
									<td class="td_type">
										无
									</td>
								</tr>		
							</c:if>
							</tbody>
						</table>
					</td>
				</tr>
			</table>
		</div>
	</form>

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
			<input type="hidden" name="REQ_ID" id="REQ_ID" value="${REQ_ID}">
		    <table class="table" width="100%" layoutH="300">
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
		</div>
	  </div>
	</c:if>
	<c:if test="${menu.MENU_CODE eq 'cx1602'}">
	  <div style="display: block;" id="displaySellPanel_2">
		<div class="pageFormContent">
		    <table class="table" width="200%" layoutH="280">
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
		    <table class="table" width="150%" layoutH="280">
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
		    <table class="table" width="200%" layoutH="280">
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
		    <table class="table" width="200%" layoutH="280">
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
		    <table class="table" width="200%" layoutH="280">
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
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="expReqDtlInfoB(0);">下载</button></div></div></li>
			</ul>
		</div>
</div>