<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
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
<!--[if IE]>
<link href="themes/css/ieHack.css" rel="stylesheet" type="text/css" />
<![endif]-->
<!-- 
<script src="/resources/js/dwzUI/speedup.js" type="text/javascript"></script>
 -->
<script src="/resources/js/jquery/jquery.all.js" type="text/javascript"></script>
<script src="/resources/js/jquery/jquery.validate.js" type="text/javascript"></script>
<script src="/resources/js/dwzUI/source/dwz.validate.method.js" type="text/javascript"></script>
<script src="/resources/js/dwzUI/source/dwz.navTab.js" type="text/javascript"></script>
<script src="/resources/js/dwzUI/source/dwz.tab.js" type="text/javascript"></script>
<script src="/resources/js/dwzUI/source/dwz.ajax.js" type="text/javascript"></script>
<script src="/resources/js/dwzUI/dwz.regional.zh.js" type="text/javascript"></script>
<script type="text/javascript">
//注意input的id和tr的id要一样
function delRowAffirmor(){
	//存在一种情况无法解决，同时添加两人，再删除第一人，则再添加新决裁者为第二位出现错位现象
	var tb2 = document.getElementById("affirmor_list");
	   var rowCount = tb2.rows.length;
	   for(var m=1;m<rowCount;m++){
			tb2.rows[m].cells[0].innerHTML = m;
	   }
}

var keyCodeInit=0;

var ajaxGet_add;
function SearchContent(condition,id,affirmor_id){
	var div='<div id="emp_list_panel" class="deptContent_sso" style="display:none;">'
		+'<div class="ztree_dept_sso">'
		+'<div class="ztree_dept_title">'
		+'<ul class="ztree_dept_table">'
		+'<li class="deptTreeLi" style="background:#E0D8DC;color:#796C5E;"><div>工号</div><div>姓名</div><i>部门</i></li>'
		+'</ul>'
		+'</div>'
		+'<div class="ztree_dept_type_sso"><ul id="emp_list_'+ id + '" class="ztree_dept_table" ></ul></div>'
 		+'</div></div>';
	//	var div = '<div id="emp_list_' + id + '"  style="position:absolute;overflow:auto; top:100;width:370; height:210; z-index:5;" class="pageContent" ></div>';
		$("#empName" + id).parent().append(div);
			if (ajaxGet_add != null) {
				ajaxGet_add.abort();
			}
			ajaxGet_add = $.ajax( {
				type : "POST",
				url : "/LGEP/affirm/getPersonCntXiao",
				data : {CONTENT : condition,AFFIRMOR_ID : affirmor_id},
				dataType : "json",
				success : function(data) {
					$('#emp_list_' + id).html("");
					var html = '';
					if (typeof (data['pidEidList']) != "undefined") {
						$('#emp_list_' + id).show();
						$.each(data['pidEidList'],
						function(commentIndex, comment) {
							html += '<li class="deptTreeLi" onclick="selectedIt_add(\'' + comment.EMPID + '\',\'' + comment.LOCAL_NAME + '\',\'' + comment.PERSON_ID + '\',' + id + ')">'
						     	 + '<div>'
								 + comment.EMPID 
								 + '</div><div>'
								 + comment.LOCAL_NAME
								 + '</div><i>'
								 + comment.DEPT_NAME 
								 + '</i></li>';
							});
					}
					$('#emp_list_' + id).html(html);
					$("#emp_list_panel").show();
			
				}
			});
}
function selectedIt_add(empid,localName,personId,id){
	$("#personId" + id).val(personId);
	$("#empName" + id).val('[' + empid + ']' + localName);
	$('#emp_list_' + id).hide();
	$('#emp_list_panel').hide();
}
 
function submitKeyClick_affirmor(obj,event){
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
							document.getElementById(empIdStr).value=jsonObject.empId;
							document.getElementById(personIdStr).value=jsonObject.personId;
							document.getElementById(empNameStr).value='['+jsonObject.empId + ']-'+jsonObject.empName;
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
}; 
function submitAffirmPre(flag){
	$("#AFFIRM_FLAG").val(flag);
	if($("#AFFIRM_FLAGS").val() == '1'){
		$("#addLeaveAffirmInfo").attr("action","/LGEP/affirm/approveApplication");
	}
	if($("#CHECK_FLAGS").val() == '1'){
		$("#addLeaveAffirmInfo").attr("action","/LGEP/affirm/checkLGEPInfo");
	}
  	$("#addLeaveAffirmInfo").submit();
}

function validateProveAppliCallback(form,callback) {
	var $form = $("#addLeaveAffirmInfo");
	if (!$form.valid()) {
		return false;
	}
	/*var applyNo = document.getElementById("APPLY_NO").value;
	var essAffirmNo  = document.getElementById("ESS_AFFIRM_NO").value;
    var person_id  = document.getElementById("PERSON_ID").value;
    var affirmFlag = document.getElementById("AFFIRM_FLAG").value;
	if(applyNo=="" || essAffirmNo=="" || person_id=="" || affirmFlag==""){
		alert("信息决裁出现问题，请联系管理员!");
		return false;
	}*/
	var affirmFlag = document.getElementById("AFFIRM_FLAG").value;
	var result = "确定要执行此次操作?";
	if(affirmFlag==1){
		result = "确定要通过此条申请信息?";
	}else if(affirmFlag==2){
		result = "确定要否决此条申请信息?";
	}
	if (confirm (result)){	          
		$.ajax( {
			type : 'post',
			cache : false,
			url : $form.attr("action"),
			data: $form.serializeArray(),
			dataType:"json",
			success : function(data) {
				alert(data.message);
				if (data.result == 1) {
					window.location.reload();
				}
			}
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
function addAffirmorRow(currentRowID,affirmor_id){
  	//遍历每一行，找到指定id的行的位置i,然后在该行后添加新行
  	$.each( $('#affirmor_list tbody tr'), function(i, tr){
      	if($(this).attr('id')==currentRowID){
        	//获取当前行
          	var currentRow=$('#affirmor_list tbody tr:eq('+i+')');
          	//要添加的行的id
          	var addRowID=i+2;
          	var AFFIRMOR_ID = affirmor_id;
          	str = ''
	            +'<tr id = "'+addRowID+'">'
	            	+'<td style="text-align: center">'+addRowID+'</td>'
          			+'<td style="text-align: center">'
						+'<input id="personId'+addRowID+'" name="dwz.person.personId'+addRowID+'" value="" type="hidden" lookupGroup="person"/>'
						+'<input id="empName'+addRowID+'" name="dwz.person.empName'+addRowID+'" value="" type="text" lookupGroup="person" '
						+'	onkeydown="SearchContent(this.value,' + addRowID + ',\'' + AFFIRMOR_ID + '\')"    onkeyup="SearchContent(this.value,' + addRowID + ',\'' + AFFIRMOR_ID + '\')" class="required"/>'
          			+'</td>'
          			+'<td style="text-align: center">未决裁</td>'
          			+'<td style="text-align: center">&nbsp;</td>'
          			+'<td style="text-align: center">&nbsp;</td>'
          			+'<td style="text-align: center">'
      					+'<img id= "'+addRowID+'" src="/resources/images/+.gif" style="cursor:hand" title="添加" onclick="addAffirmorRow(this.id);"/>&nbsp;&nbsp;&nbsp;'
      					+'<img id= "'+addRowID+'" src="/resources/images/-.gif" style="cursor:hand" title="删除" '
      					//先删除，再排序
      					+'	onclick="javaScript:document.all.affirmor_list.deleteRow(event.srcElement.parentElement.parentElement.rowIndex);delRowAffirmor();"/>'
					+'</td>'
          			+'<td style="text-align: center">&nbsp;</td>'
				+'</tr>';
          	//当前行之后插入一行
          	currentRow.after(str);
      	}
  	});
 	var tb2 = document.getElementById("affirmor_list");
 	var rowCount = tb2.rows.length;
 	for(var m=1;m<rowCount;m++){
		tb2.rows[m].cells[0].innerHTML = m;
	}
}
 
function addCheck(essAffirmNo,personId,AFFIRMOR_ID){
  var returnValue =  window.showModalDialog("/LGEP/affirm/addCheckInfo?APPLY_TYPE=217886&PAGE_FLAG=L_OTAPPLY&search_ot_time_type=${infoApplyOt.OT_TIME_TYPE}&PAGE_FLAG2=L_OTAPPLY&APPLY_NO=${infoApplyOt.APPLY_NO }&ESS_AFFIRM_NO=" + essAffirmNo + "&AFFIRMOR_ID=" + AFFIRMOR_ID,window,"dialogWidth=500px;dialogHeight=300px;help=no;center=yes;resizable=no;status=no;scroll=no");
  if(returnValue == "true"){
     window.location.reload();
  }
}
</script>
</head>
<body>
</br></br>
<div class="pageContent">
	<table class="user_table margin_b" width="100%" height="45%">
		<tr>
			<td class="td_title" width="20%" style="text-align: center">申请者/工号</td>
			<td class="td_title" width="20%" style="text-align: center">费用类型</td>
			<td class="td_title" width="20%" style="text-align: center">申请费用发放期间</td>
			<td class="td_title" width="20%" style="text-align: center">金额</td>
			<td class="td_title" width="20%" style="text-align: center">备注</td>
		</tr>
		<c:forEach items="${messageList}" var="message" varStatus="i">
			<tr>
				<td class="td_type" width="20%" style="text-align: center">[${message.COSTEMP}]${message.EMPNAME}</td>
				<td class="td_type" width="20%" style="text-align: center">${message.TYPENAME}</td>
				<td class="td_type" width="20%" style="text-align: center">${message.START_DATE}~${message.END_DATE}</td>
				<td class="td_type" width="20%" style="text-align: center">${message.MONEY}</td>
				<td class="td_type" width="20%" style="text-align: center">${message.DEMO}</td>
			</tr>
			<c:if test="${fn:length(messageList)==i.count}">
				<tr>
					<td class="td_type" width="20%" style="text-align: center"></td>
					<td class="td_type" width="20%" style="text-align: center"></td>
					<td class="td_type" width="20%" style="text-align: center"></td>
					<td class="td_type" width="20%" style="text-align: center">追加款：${message.ZHENG }</td>
					<td class="td_type" width="20%" style="text-align: center">追减款：${message.FU}</td>
				</tr>			
			</c:if>
		</c:forEach>
		<c:if test="${fn:length(messageList)==0}">
			<tr>
				<td class="td_type" colspan="5" height="20px" style="text-align: center">暂无数据</td>
			</tr>
		</c:if>
	</table>
</div>
<div class="pageContent">
	<form id="addLeaveAffirmInfo" method="post"	action="/LGEP/affirm/approveApplication" 
		class="pageForm required-validate" onsubmit="return validateProveAppliCallback(this,navTabAjaxDone);">
		<div class="pageFormContent">
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="user_table margin_b" layoutH="430">
				<tr>
					<td class="td_title" width="10%" style="text-align: center">决裁线</td>
					<td colspan="7">
						<table width="100%" border="0" cellpadding="0" cellspacing="0" id="affirmor_list">
							  <thead>
								<td class="td_title" width="10%" style="text-align: center">决裁等级</td>
								<td class="td_title" width="12%" style="text-align: center">决裁者</td>
								<td class="td_title" width="18%" style="text-align: center">决裁情况</td>
								<td class="td_title" width="10%" style="text-align: center">审批时间</td>
								<td class="td_title" width="20%" style="text-align: center">决裁批注</td>
								<td class="td_title" width="10%" style="text-align: center">决裁者(+/-)</td>
								<td class="td_title" width="10%" style="text-align: center">check(+/-)</td>
							  </thead>
							  <tbody id="proAffirmor_list"><c:set var="COUNT" value="1"/>
								  	<c:forEach items="${proveList}" var="Affirmor" varStatus="i">
								  		<c:if test="${Affirmor.AFFIRMOR_ID eq PERSON_ID && Affirmor.AFFIRM_FLAG eq '0' && COUNT eq '1'}">
								  			<c:set var="AFFIRM_FLAGS" value="1"/>
											<c:set var="AFFIRMLEVEL" value="${Affirmor.AFFIRM_LEVEL}"/>
											<c:set var="COUNT" value="2"/>
											<input type="hidden" id="AFFIRM_FLAGS" value="1">
										</c:if>
								  	</c:forEach>
								<c:forEach items="${proveList}" var="affirmor" varStatus="j">
									<c:if test="${fn:length(proveList)==affirmor.AFFIRM_LEVEL && affirmor.AFFIRMOR_ID eq PERSON_ID}">
										<input type="hidden" name="lastLevel" value="${affirmor.AFFIRMOR_ID}"/>
									</c:if>
									<c:if test="${affirmor.AFFIRM_LEVEL lt AFFIRMLEVEL && affirmor.AFFIRM_FLAG ne '1'}">
										<c:set var="AFFIRM_FLAGS" value="2"/>
									</c:if>
									<tr id="PA${affirmor.AFFIRMOR_ID }${j.count}">
										<td class="td_type" width="10%" style="text-align: center">${affirmor.AFFIRM_LEVEL}</td>
										<td class="td_type" width="12%" style="text-align: center">[${affirmor.EMPID}]${affirmor.EMPNAME}</td>
										<td class="td_type" width="18%" style="text-align: center">
											<c:if test="${affirmor.AFFIRM_FLAG eq '0'}">未决裁</c:if>
											<c:if test="${affirmor.AFFIRM_FLAG eq '1'}">已通过</c:if>
											<c:if test="${affirmor.AFFIRM_FLAG eq '2'}">已否决</c:if>
										</td>
										<td class="td_type" width="10%" style="text-align: center">${affirmor.AFFIRM_DATE}</td>
										<td class="td_type" width="20%" style="text-align: center">
											<c:if test="${affirmor.AFFIRMOR_ID ne PERSON_ID || affirmor.AFFIRM_FLAG ne 0}">${affirmor.AFFIRM_CONTENT}</c:if>
											<c:if test="${affirmor.AFFIRM_FLAG eq 0 && affirmor.AFFIRMOR_ID eq PERSON_ID && AFFIRM_FLAGS eq '1'}">
												<input type="text" id="AFFIRM_CONTENT" name="AFFIRM_CONTENT" title="批注" size="20" value="${affirmor.AFFIRM_CONTENT}"/>
												<input id="ESS_AFFIRM_NO" name="ESS_AFFIRM_NO" type="hidden" value="${affirmor.ESS_AFFIRM_NO }" />
											</c:if>
										</td>
										<td class="td_type" width="10%" style="text-align: center">
											<c:if test="${affirmor.AFFIRM_FLAG eq 0 && affirmor.AFFIRMOR_ID eq PERSON_ID && AFFIRM_FLAGS eq '1'}">
												<img id="PA${affirmor.AFFIRMOR_ID}${j.count}" src="/resources/images/+.gif" title="添加决裁者" border="0"
													align="absmiddle" style="cursor: hand" onclick="addAffirmorRow(this.id,'${affirmor.AFFIRMOR_ID}')" />
											</c:if>
										</td>
										<td class="td_type" width="10%" style="text-align: center">
											<c:if test="${affirmor.AFFIRM_FLAG eq 0 && affirmor.AFFIRMOR_ID eq PERSON_ID && AFFIRM_FLAGS eq '1'}">
												<img id="${affirmor.AFFIRMOR_ID}" src="/resources/images/+.gif" title="添加Check"	 border="0" align="absmiddle" style="cursor:hand" onclick="addCheck('${affirmor.ESS_AFFIRM_NO}','${affirmor.AFFIRMOR_ID}','${affirmor.AFFIRMOR_ID}')"/>
											</c:if>
										</td>
									</tr>
									<a id="onck" name="onck" href="" lookupGroup="person"></a>
								</c:forEach>
							</tbody>
						</table>
					</td>
				</tr>
				<tr>
					<!-- 隐藏的一些参数 -->
					<input id="APPLY_NO" name="APPLY_NO" type="hidden" value="${APPLY_NO}"/>
					<input id="PERSON_ID" name="PERSON_ID" type="hidden" value="${PERSON_ID}"/>
					<input id="AFFIRM_ID" name="AFFIRMOR_ID" type="hidden" value="${PERSON_ID}"/>
					<input id="APPLY_TYPE" name="APPLY_TYPE" type="hidden" value="217886" />
					<input id="AFFIRM_FLAG" name="AFFIRM_FLAG" type="hidden" value=""/>
				</tr>
				<tr>
					<td class="td_type" colspan="8"><br /></td>
				</tr>
				<tr>
					<c:if test="${fn:length(checkorList) > 0}">
						<td class="td_title" width="10%" style="text-align: center" rowspan="${fn:length(checkorList)*2+1 }">Review</td>
					</c:if>
					<c:if test="${fn:length(checkorList) == 0}">
						<td class="td_title" width="10%" style="text-align: center" rowspan="${3}">Review</td>
					</c:if>
					<td class="td_title" width="10%" style="text-align: center">Type</td>
					<td class="td_title" width="40%" style="text-align: center" colspan="3">Requests</td>
					<td class="td_title" width="40%" style="text-align: center" colspan="3">Reviewed</td>
				</tr>
				
				<c:forEach items="${checkorList}" var="CHECK" varStatus="i">
			  		<c:if test="${CHECK.CHECKOR_ID eq PERSON_ID && CHECK.AFFIRM_FLAG eq '0'}">
			  			<c:set var="CHECK_FLAGS" value="1"/>
			  			<input type="hidden" id="CHECK_FLAGS" value="1">
					</c:if>
			  	</c:forEach>
				<c:forEach items="${checkorList}" var="checkor" varStatus="i">
					<tr>
						<td class="td_type" width="10%" style="text-align: center" rowspan="2">Public</td>
						<td class="td_type" width="40%" style="text-align: center" colspan="3">
							[${checkor.AFFIRM_EMPID}]${checkor.AFFIRM_NAME}&nbsp;${checkor.AFFIRM_POSITION}
							&nbsp;&nbsp;(${checkor.AFFIRM_DEPT_NAME})&nbsp;/&nbsp;${checkor.AFFIRM_DATE}&nbsp;
							<c:if test="${checkor.AFFIRM_FLAG == 0}">未决裁</c:if>
							<c:if test="${checkor.AFFIRM_FLAG == 1}">已通过</c:if>
							<c:if test="${checkor.AFFIRM_FLAG == 2}">已否决</c:if>
						</td>
						<td class="td_type" width="40%" style="text-align: center" colspan="3">
							[${checkor.CHECK_EMPID}]${checkor.CHECK_NAME}&nbsp;${checkor.CHECK_POSITION}
							&nbsp;&nbsp;(${checkor.CHECK_DEPT_NAME})&nbsp;/&nbsp;${checkor.CHECK_DATE}&nbsp;
							<c:if test="${checkor.CHECK_FLAG == 0}">未Check</c:if>
							<c:if test="${checkor.CHECK_FLAG == 1}">已Check</c:if>
						</td>
					</tr>
					<tr>
						<td class="td_type" width="40%" style="text-align: center" colspan="3">
							<textarea name="affirmRemark" cols="50" rows="2"
								disabled="disabled">[Request]：${checkor.CHECK_REASON}</textarea>
						</td>
						<td class="td_type" width="40%" style="text-align: center" colspan="3">
						 <c:choose>
							<c:when test="${checkor.CHECKOR_ID ne PERSON_ID || checkor.AFFIRM_FLAG ne '0'}">
								<textarea cols="50" rows="2" disabled="disabled">[Check]：${checkor.CHECK_CONTENT}</textarea>
							</c:when>
							<c:otherwise>
								<input type="hidden" id="ESS_CHECK_NO" name="ESS_CHECK_NO" value="${checkor.ESS_CHECK_NO }"/>
								<textarea id="CHECK_CONTENT" name="CHECK_CONTENT" cols="50" rows="2">[Check]：${checkor.CHECK_CONTENT}</textarea>
							</c:otherwise>
						 </c:choose>
						</td>
					</tr>
				</c:forEach>
				<c:if test="${fn:length(checkorList) == 0}">
					<tr>
						<td class="td_type" width="10%" style="text-align: center" rowspan="2">Public</td>
						<td class="td_type" width="40%" style="text-align: center" colspan="3">&nbsp;</td>
						<td class="td_type" width="40%" style="text-align: center" colspan="3">&nbsp;</td>
					</tr>
					<tr>
						<td class="td_type" width="40%" style="text-align: center" colspan="3">&nbsp;</td>
						<td class="td_type" width="40%" style="text-align: center" colspan="3">&nbsp;</td>
					</tr>
				</c:if>
			</table>
		</div>
		<c:if test="${AFFIRM_FLAGS eq '1' || CHECK_FLAGS eq '1'}">
			<div class="formBar">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="submitAffirmPre(1)">通过</button>	</div></div></li>
					<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="submitAffirmPre(2)">否决</button>	</div></div></li>
					<li><div class="button"><div class="buttonContent"><button type="button" onClick="window.close()" class="close"><spring:message code="public.title.cancle"/><!--取消 --></button></div></div></li>
				</ul>
			</div>
		</c:if>
	</form>
</div>
</body>
</html>