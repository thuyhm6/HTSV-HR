<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="../../inc/initTaglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head> 

<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<title>LGE CHRS2.0</title>
<link href="/resources/css/dwzUI/core.css" rel="stylesheet" type="text/css" />

<link href="/resources/css/dwzUI/uploadify/uploadify.css" rel="stylesheet" type="text/css" />
<link href="/resources/css/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />

<link href="/resources/css/ztree/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css"/>
<link href="/resources/css/dwzUI/themes/lge/style.css" rel="stylesheet" type="text/css" />
<link href="/resources/css/dwzUI/themes/lge/sso_style.css" rel="stylesheet" type="text/css" />

<!-- jquery -->
<script src="/resources/js/jquery/jquery.all.js" type="text/javascript"></script>
<script>
//添加决裁者
function addRowByIDCx1600_affirm(currentRowID){
	var count = parseInt($("#affirmCount_affirm").val());
    var htm  ='<tr id="rowIdCx1600_affirm'+ count +'"><td class="td_type" style="text-align: center" width="33%"><span name="rowIndex"></span></td>';
		htm +='<td class="td_type" style="text-align: center" width="33%">';
		htm +='<input id="AFFIRMOR_IDCx1600_affirm' + count + '" name="AFFIRMOR_ID" value="" type="hidden"/>';
		htm +='<input id="EMPINFOCx1600_affirm' + count + '" name="empid" type="text" class="required" lookupGroup="person" onkeydown="submitKeyClick_affirmor_affirm(this,' + count + ',event)" class="required"/>';
		htm +='</td>';
		htm +='<td></td>';
		htm +='<td></td>';
		htm +='<td></td>';
		htm +='<td style="text-align: center">';
		htm +='<img src="/resources/images/+.gif" title="添加"';	
		htm +='border="0" align="absmiddle" style="cursor:hand" onclick="addRowByIDCx1600_affirm(' + count + ')"/>&nbsp;&nbsp;&nbsp;';
		htm +='<img src="/resources/images/-.gif" title="删除"';	
		htm +='border="0" align="absmiddle" style="cursor:hand" onclick="javaScript:document.all.addAffirm_listCx1600_affirm.deleteRow(event.srcElement.parentElement.parentElement.rowIndex);changeAffirmLevelCx1600_affirm();"/></td></tr>';

   	//当前行之后插入一行
   	$("#rowIdCx1600_affirm" + currentRowID).after(htm);
   	changeAffirmLevelCx1600_affirm();
  	$("#affirmCount_affirm").val(++count) ;
}
//修改决裁者等级
function changeAffirmLevelCx1600_affirm(){
	var tb2 = document.getElementById("addAffirm_listCx1600_affirm");
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
							$("#EMPINFOCx1600_affirm" + index).val('['+jsonObject.empId + ']-'+jsonObject.empName);
							$("#AFFIRMOR_IDCx1600_affirm" + index).val( jsonObject.personId);
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
	function addCheckCx1600(essAffirmNo,AFFIRMOR_ID){
		var returnValue =  window.showModalDialog("/LGEP/affirm/addCheckInfo?APPLY_TYPE=219983&APPLY_NO=${reqInfo.REQ_ID}&ESS_AFFIRM_NO=" + essAffirmNo + "&AFFIRMOR_ID=" + AFFIRMOR_ID,window,"dialogWidth=500px;dialogHeight=300px;help=no;center=yes;resizable=no;status=no;scroll=no");
		if(returnValue == "true"){
	     	window.location.reload();
	    }
	}
	function view(index)
	{
		for(var i=1;i<=6;i++)
		{
			var obj1 = document.getElementById('displaySellPanel_'+i);
			obj1.style.display='none';
		}
		var obj  = document.getElementById('displaySellPanel_'+index);
		obj.style.display = 'block';
	}
	function expReqDtlInfoA(expType){
		var eForm = document.getElementById("excelForm_cx1605");
		eForm.action="/LGEP/affirm/expSelloutReqDtlExcelA?CPNY_ID=LGECH&LANGUAGE=ZH&REQ_ID="+document.getElementById("REQ_ID").value;
		eForm.submit();
	}
</script>
</head>
<body>
<div>
	<form id="excelForm_cx1605" name="excelForm_cx1605" action="" method="post">
	</form>
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
  <div class="pageContent">
	<form id="viewAffirmSellOut" method="post" action="/LGEP/affirm/affirmSellout" class="pageForm required-validate" onsubmit="return validateCallbackViewSelloutReq(this, navTabAjaxDone)">
		<div class="pageFormContent" style="height:100px">
			<table width="100%"  border="0" cellpadding="0" cellspacing="0" class="user_table margin_b">
				<tr>
					<td class="td_title" style="text-align: right;width:10%;">
						审批线
					</td>
					<td style="width:90%;">
						<table width="100%" border="0" cellpadding="0" cellspacing="0" id="addAffirm_listCx1600_affirm">
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
								<tr id="rowIdCx1600_affirm${j.index}">
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
									<c:if test="${affirmor.ESS_AFFIRM_NO eq affirm_no && j.count<affirmorListCnt}">
										<img src="/resources/images/+.gif" title="添加"
											border="0" align="absmiddle" style="cursor:hand" onclick="addRowByIDCx1600_affirm(${j.index})"/>
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
						<table width="100%" border="0" cellpadding="0" cellspacing="0" id="addACheck_listCx1600_affirm">
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
		    <input type="hidden" id="REQ_ID" name="REQ_ID" value="${reqInfo.REQ_ID}"/>
		    <input type="hidden" name="affirmCount" id="affirmCount_affirm" value="${affirmorListCnt }">
		    <input type="hidden" id="viewAffirmSellOutFLAG" name="viewAffirmSellOutFLAG" value="1" />
		    <input type="hidden" id="affirmOrCheck" name="affirmOrCheck" value="${affirmOrCheck}" />
		    <input type="hidden" id="dept_level" name="dept_level" value="${dept_level}" />
		    <input type="hidden" id="personId" name="personId" value="${personId}" />
			<input id="pro_flag" name="pro_flag" type="hidden" value="0" />
		<c:if test="${affirm_no ne ''}">
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="affirmTempSalary(1);">通过</button></div></div></li>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="affirmTempSalary(2);">否决</button></div></div></li>
			</ul>
		</div>
		</c:if>
		<c:if test="${check_no ne ''}">
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="affirmTempSalary(1);">通过</button></div></div></li>
			</ul>
		</div>
		</c:if>
	</form>	
  </div>

<div class="pageContent">
<div style="clear: both;"></div>
<div class="tabs" currentIndex="${tabsSelected }" eventType="click">
<div class="tabsHeader">
<div class="tabsHeaderContent">
<ul>
	<c:forEach items="${menuThirdList}" var="menu" varStatus="i">
		<li><a href="javascript:view(${i.index+1});"><span>${menu.MENU_NAME } </span> </a>
		</li>
	</c:forEach>
</ul>
</div>
</div>
<div class="tabsContent" id="displaycheckbox">
<c:forEach items="${menuThirdList}" var="menu" varStatus="i">
	<c:if test="${menu.MENU_CODE eq 'cx1601'}">
	  <div style="display: block;" id="displaySellPanel_1">
		<div class="pageFormContent" style="height:240px">
		  <table id="tbcx1601" class="table" width="70%" layoutH="300">
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
	  <div style="display: none;" id="displaySellPanel_2">
		<div class="pageFormContent" style="height:240px">
		    <table class="table" width="100%" layoutH="350">
		    <thead>
		      <tr>
		        <th>门店编码</th>
		        <th>PR社番</th>
		        <th>销售时间</th>
		        <th>产品型号</th>
		        <th>汇总</th>
		        <th>办事处</th>
		        <th>卖场名称</th>
		        <th>门店等级</th>
		        <th>渠道等级</th>
		        <th>渠道</th>
		        <th>Bill to渠道</th>
		        <th>支付比例(%)</th>
		        <th>确认销量</th>
		      </tr>
		    </thead>
		    <tbody>
		    <c:forEach items="${reqOver10List}" var="item2" varStatus="i">
			  <tr>
				<td class='td_center'>${item2.SHOP_CD}</td>
				<td class='td_center'>${item2.PR_EMPID}</td>
				<td class='td_center'><fmt:formatDate value="${item2.SALE_DATE}" pattern="yyyy-MM-dd" /></td>
				<td class='td_center'>${item2.PROD_ID}</td>
				<td class='td_center'>${item2.SALS_QTY}</td>
				<td class='td_center'>${item2.BRANCH}</td>
				<td class='td_center'>${item2.SHOP_NAME}</td>
				<td class='td_center'>${item2.SHOP_LEVEL}</td>
				<td class='td_center'>${item2.CHANNEL_GRADE}</td>
				<td class='td_center'>${item2.CHANNEL_NAME}</td>
				<td class='td_center'>${item2.BILL_TO_NAME}</td>
				<td class='td_center'>${item2.PAY_RATE}</td>
				<td class='td_center'>${item2.REAL_QTY}</td>
			  </tr>
		    </c:forEach>
		    </tbody>
		    </table>
		</div>
	  </div>
	</c:if>
	<c:if test="${menu.MENU_CODE eq 'cx1603'}">
	  <div style="display: none;" id="displaySellPanel_3">
		<div class="pageFormContent" style="height:240px">
		    <table class="table" width="100%" layoutH="350">
		    <thead>
		      <tr>
		        <th>办事处</th>
		        <th>社号</th>
		        <th>姓名</th>
		        <th>门店代码</th>
		        <th>主责商场</th>
		        <th>channel</th>
		        <th>主责产品</th>
		        <th>当月销售金额</th>
		        <th>全月销量</th>
		        <th>提成合计</th><!--
		        <th>支社是否核对（Y/N）</th>
		        <th>内容说明</th>-->
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
				<td class='td_center'>${item3.INC_AMT}</td><!--
				<td class='td_center'>${item3.CHECK_FLAG}</td>
				<td class='td_center'>${item3.REMARK}</td>-->
			  </tr>
		    </c:forEach>
		    </tbody>
		    </table>
		</div>
	  </div>
	</c:if>
	<c:if test="${menu.MENU_CODE eq 'cx1604'}">
	  <div style="display: none;" id="displaySellPanel_4">
		<div class="pageFormContent" style="height:240px">
		    <table class="table" width="100%" layoutH="350">
		    <thead>
		      <tr>
		        <th>支社</th>
		        <th>社号</th>
		        <th>姓名</th>
		        <th>门店代码</th>
		        <th>主责商场</th>
		        <th>channel</th>
		        <th>主责产品</th>
		        <th>本月销量</th>
		        <th>上月销量</th>
		        <th>提成合计</th>
		        <th>上月提成</th>
		        <th>伸张率%</th><!--
		        <th>支社是否核对（Y/N）</th>
		        <th>内容说明</th>-->
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
				<td class='td_center'>${item4.SALS_QTY}</td>
				<td class='td_center'>${item4.LAST_SALS_QTY}</td>
				<td class='td_center'>${item4.INC_AMT}</td>
				<td class='td_center'>${item4.LAST_INC_AMT}</td>
				<td class='td_center'>${item4.RATIO}</td><!--
				<td class='td_center'>${item4.CHECK_FLAG}</td>
				<td class='td_center'>${item4.REMARK}</td>-->
			  </tr>
		    </c:forEach>
		    </tbody>
		    </table>
		</div>
	  </div>
	</c:if>
	<c:if test="${menu.MENU_CODE eq 'cx1605'}">
	  <div style="display: none;" id="displaySellPanel_5">
		<div class="pageFormContent" style="height:240px">
		    <table class="table" width="100%" layoutH="350">
		    <thead>
		      <tr>
		        <th>支社</th>
		        <th>社号</th>
		        <th>姓名</th>
		        <th>门店代码</th>
		        <th>主责商场</th>
		        <th>channel</th>
		        <th>主责产品</th>
		        <th>本月销量</th>
		        <th>上月销量</th>
		        <th>提成合计</th>
		        <th>上月提成</th>
		        <th>伸张率%</th><!--
		        <th>支社是否核对（Y/N）</th>
		        <th>签字凭证是否提供</th>-->
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
				<td class='td_center'>${item5.SALS_QTY}</td>
				<td class='td_center'>${item5.LAST_SALS_QTY}</td>
				<td class='td_center'>${item5.INC_AMT}</td>
				<td class='td_center'>${item5.LAST_INC_AMT}</td>
				<td class='td_center'>${item5.RATIO}</td><!--
				<td class='td_center'>${item5.CHECK_FLAG}</td>
				<td class='td_center'>${item5.REMARK}</td>-->
			  </tr>
		    </c:forEach>
		    </tbody>
		    </table>
		</div>
	  </div>
	</c:if>
	<c:if test="${menu.MENU_CODE eq 'cx1606'}">
	  <div style="display: none;" id="displaySellPanel_6">
		<div class="pageFormContent" style="height:240px">
		    <table class="table" width="150%" layoutH="350">
		    <thead>
		      <tr>
		        <th>COM_CODE</th>
		        <th>社号</th>
		        <th>姓名</th>
		        <th>月份</th>
		        <th>销售数量</th>
		        <th>产品类型</th>
		        <th>产品ID</th>
		        <th>支社</th>
		        <th>客户ID</th>
		        <th>门店名称</th>
		        <th>CHANNEL_CODE</th>
		        <th>EZ上报销量</th>
		        <th>支社确认销量</th><!--
		        <th>凭证</th>
		        <th>确认方式</th>-->
		      </tr>
		    </thead>
		    <tbody>
		    <c:forEach items="${reqReportList}" var="item6" varStatus="i">
			  <tr>
				<td class='td_center'>${item6.COM_CODE}</td>
				<td class='td_center'>${item6.EMP_NO}</td>
				<td class='td_center'>${item6.EMP_NM}</td>
				<td class='td_center'>${item6.SALE_MONTH}</td>
				<td class='td_center'>${item6.SALE_QTY}</td>
				<td class='td_center'>${item6.MODEL_CATEGORY_CODE}</td>
				<td class='td_center'>${item6.MODEL_CODE}</td>
				<td class='td_center'>${item6.BRANCH}</td>
				<td class='td_center'>${item6.SHIP_TO_CODE}</td>
				<td class='td_center'>${item6.SHIP_TO_NAME}</td>
				<td class='td_center'>${item6.CHANNEL_CODE}</td>
				<td class='td_center'>${item6.SALE_QTY}</td>
				<td class='td_center'>${item6.SALE_QTY}</td><!--
				<td class='td_center'></td>
				<td class='td_center'></td>-->
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

<div class="formBar">
   <ul>
      <li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="expReqDtlInfoA(0);">下载</button></div></div></li>
   </ul>
</div>

</div>
</div>
</div>
</body>
</html>