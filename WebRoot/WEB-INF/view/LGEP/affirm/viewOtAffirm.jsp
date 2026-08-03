<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="../../inc/initTaglibs.jsp"%>
 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
 

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<title>LGE CHRS2.0</title>
<link href="/resources/css/dwzUI/core.css" rel="stylesheet"
	type="text/css" />


<link href="/resources/css/dwzUI/uploadify/uploadify.css"
	rel="stylesheet" type="text/css" />
<link href="/resources/css/ligerUI/skins/Aqua/css/ligerui-all.css"
	rel="stylesheet" type="text/css" />

<link href="/resources/css/ztree/zTreeStyle/zTreeStyle.css"
	rel="stylesheet" type="text/css" />
<link href="/resources/css/dwzUI/themes/lge/style.css" rel="stylesheet"
	type="text/css" />
<link href="/resources/css/dwzUI/themes/lge/sso_style.css" rel="stylesheet" type="text/css" />

<!--[if IE]>
<link href="themes/css/ieHack.css" rel="stylesheet" type="text/css" />
<![endif]-->

<!-- 
<script src="/resources/js/dwzUI/speedup.js" type="text/javascript"></script>
 -->

<!-- jquery -->

<script src="/resources/js/jquery/jquery.all.js" type="text/javascript"></script>
<script src="/resources/js/dwzUI/dwz.min.js" type="text/javascript"></script>

<script type="text/javascript">
<!--
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
		+'<li class="deptTreeLi" style="background:#E0D8DC;color:#796C5E;" onclick="selectedIt_hide(' + id + ')" title="关闭 " ><div>工号</div><div>姓名</div><i>部门</i></li>'
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
 
function selectedIt_hide(id){
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
//-->
</script>
<script type="text/javascript">
//<!--
function submitAffirmPreEss0242(flag){
	$("#AFFIRM_FLAG_ESS1231").val(flag);

	if($("#AFFIRM_FLAG_ESS1231").val() == null || '' ==$("#AFFIRM_FLAG_ESS1231").val() ){
		document.getELementById("AFFIRM_FLAG_ESS1231").value=flag;

     }
	var pro_flag = $("#pro_flag").val();
	 
	if(pro_flag == 0){
		$("#pro_flag").val("1");
		$.ajax({
			type:$("#addLeaveAffirmInfo").method || 'POST',
			url:$("#addLeaveAffirmInfo").attr("action"),
			data:$("#addLeaveAffirmInfo").serializeArray(),
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
   	var count = $("#flag").val();
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
      					//+'<img id= "'+addRowID+'" src="/resources/images/+.gif" style="cursor:hand" title="添加" onclick="addAffirmorRow(this.id);"/>&nbsp;&nbsp;&nbsp;'
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
  	 $("#flag").val(count+1);
 	var tb2 = document.getElementById("affirmor_list");
 	var rowCount = tb2.rows.length;
 	for(var m=1;m<rowCount;m++){
		tb2.rows[m].cells[0].innerHTML = m;
	}
}
 
 

 
function addCheck(essAffirmNo,AFFIRMOR_ID){
 //大家用这个方法时候，传参需传 APPLY_TYPE(代表考勤类型 )   apply_no   和ESS_AFFIRM_NO,AFFIRMOR_ID  这四个个即可。
	var returnValue =  window.showModalDialog("/LGEP/affirm/addCheckInfo?APPLY_TYPE=31&APPLY_NO=${infoApplyOt.APPLY_NO}&PAGE_FLAG=L_OTAPPLY&search_ot_time_type=${infoApplyOt.OT_TIME_TYPE}&PAGE_FLAG2=L_OTAPPLY&ESS_AFFIRM_NO=" + essAffirmNo +  "&AFFIRMOR_ID=" + AFFIRMOR_ID,window,"dialogWidth=470px;dialogHeight=300px;help=no;center=yes;resizable=no;status=no;scroll=no");

	  if(returnValue == "true"){
          window.location.reload();
      }
 }
//-->
</script>
</head>
 
<body style="overflow-x:hidden;overflow-y:auto;">

	<form id="addLeaveAffirmInfo" method="post" action="/LGEP/affirm/affirmOt" class="pageForm required-validate" onsubmit="return validateContractAffirmCallback(this,navTabAjaxDone);">
 
   
  	<c:if test="${infoApplyOt.APPLY_TYPE eq 'BATCH' && infoApplyOt.OT_TIME_TYPE eq 'P' }">
<div class="pageContent">
	<table class="table" width="100%" layoutH="240" nowrapTD="false">
		<thead>
			<tr>
			 
				
				<th>社号</th>
				<th>姓名</th>
				<th>加班开始时间</th>
				<th>加班结束时间</th>
				<c:if test="${infoApplyOt.CPNY_ID eq 'TSTO' }">
				<th>进门时间</th>
				<th>出门时间</th>
				</c:if>
				<th>加班类型</th>
					 <c:if test="${infoApplyOt.CPNY_ID ne 'LGEQH' }">
				<th>参考时长</th>
				</c:if>
				 <c:if test="${infoApplyOt.CPNY_ID eq 'LGEHZ' }">
				 <th>本月周末累计</th>
				<th>本月平日累计</th>
				<th>本月加班总计</th>
				 </c:if>
				  <c:if test="${infoApplyOt.CPNY_ID eq 'LGEHZ' }">
				  
				<th>本月加班总计</th>
				 </c:if>
				 
				  <c:if test="${infoApplyOt.CPNY_ID eq 'LGEYT' }">
				  <th>本月加班总计</th>
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
					<c:if test="${infoApplyOt.CPNY_ID eq 'TSTO' }">
					<td class='td_center'><fmt:formatDate value="${item.INTIME}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<td class='td_center'><fmt:formatDate value="${item.OUTTIME}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
					</c:if>
					<td class='td_center'>${item.OT_TYPE}</td>
						 <c:if test="${infoApplyOt.CPNY_ID ne 'LGEQH' }">
						<td class='td_center'>${item.OT_LENGTH}</td>
						</c:if>
						 <c:if test="${infoApplyOt.CPNY_ID eq 'LGEHZ' }">
						<td class='td_center'>${item.WEEKEND}</td>
					<td class='td_center'>${item.WEEKDAY}</td>
					<td class='td_center'>${item.OT_COUNT}</td>
				 </c:if>
				  <c:if test="${infoApplyOt.CPNY_ID eq 'LGEYT' }">
					 
					<td class='td_center'>
					<c:if test="${item.OT_COUNT > 90 }">
							<font color="red">${item.OT_COUNT}</font>
							</c:if>
							<c:if test="${item.OT_COUNT <=90 }">
							<font  >${item.OT_COUNT}</font>
							</c:if></td>
					</c:if>
					<td style="text-align:left">${item.APPLY_OT_REMARK}</td>
					 <td style="text-align:left">
						<c:forEach items="${item.fileList}" var="file" varStatus="j">	
								&nbsp;&nbsp;<a href="/LGEP/affirm/downloadFile?fileName=${file.FILE_URL }&file=${file.FILE_NAME}" >${file.FILE_NAME}</a>;
						</c:forEach>
					</td>
				</tr>			
			</c:forEach>			
		</tbody>
	</table>
 
</div>
</c:if>
			
		<c:if test="${infoApplyOt.APPLY_TYPE eq 'BATCH' && infoApplyOt.OT_TIME_TYPE eq 'L' }">
<div class="pageContent">
	<table class="table" width="100%" layoutH="240" nowrapTD="false">
		<thead>
			<tr>
			 
			<th>社号</th>
				<th>姓名</th>
				<th>加班日期</th>
				<c:if test="${infoApplyOt.CPNY_ID eq 'TSTO' }">
				<th>进门时间</th>
				<th>出门时间</th>
				</c:if>
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
					<c:if test="${infoApplyOt.CPNY_ID eq 'TSTO' }">
					<td class='td_center'><fmt:formatDate value="${item.INTIME}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<td class='td_center'><fmt:formatDate value="${item.OUTTIME}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
					</c:if>
					<td class='td_center'>${item.OT_APPLY_HOUR}</td>
					<td class='td_center'>${item.OT_TYPE}</td>
					<td style="text-align:left">${item.APPLY_OT_REMARK}</td>
						<td style="text-align:left">
						<c:forEach items="${item.fileList}" var="file" varStatus="j">	
								&nbsp;&nbsp;<a href="/LGEP/affirm/downloadFile?fileName=${file.FILE_URL }&file=${file.FILE_NAME}" >${file.FILE_NAME}</a>;
						</c:forEach>
					</td>
				</tr>			
			</c:forEach>		
		</tbody>
	</table>
	 
</div>
</c:if>
  	<c:if test="${infoApplyOt.OT_TIME_TYPE eq 'L' &&  infoApplyOt.APPLY_TYPE eq 'PERSON'}"> 
 
 <div class="pageContent">
	<div class="pageFormContent">
			<tr> <td colspan="8"> 
						<table class="user_table" width="100%" border="0"> <tr>
								<td class="td_title" style="text-align: center">社号|姓名</td>
								<td>[${infoApplyOt.EMPID}]${infoApplyOt.LOCAL_NAME}</td>
								<td class="td_title" style="text-align: center">部门</td>
								<td>${infoApplyOt.DEPT_NAME} </td>
								<td class="td_title" style="text-align: center">加班日期</td>
								<td  >${infoApplyOt.APPLY_OT_DATE_L}</td>
								<td class="td_title" style="text-align: center">加班长度</td>
								<td>${infoApplyOt.OT_APPLY_HOUR}
								
								</td>
							</tr>
							
							<c:if test="${infoApplyOt.CPNY_ID eq 'TSTO' }">
							<tr>
							<td class="td_title" style="text-align: center">进门时间</td>
								<td   ><fmt:formatDate value="${infoApplyOt.INTIME}" pattern="yyyy-MM-dd HH:mm:ss" />&nbsp;&nbsp;&nbsp;&nbsp;</td>
								<td class="td_title" style="text-align: center">出门时间</td>
								<td  ><fmt:formatDate value="${infoApplyOt.OUTTIME}" pattern="yyyy-MM-dd HH:mm:ss" />&nbsp;&nbsp;&nbsp;&nbsp;</td>
								
								<td class="td_title" style="text-align: center">附件</td>
								<td  colspan="3"><c:forEach items="${infoApplyOt.fileList}" var="file" varStatus="j">	
									<a href="/LGEP/affirm/downloadFile?fileName=${file.FILE_URL }&file=${file.FILE_NAME}" >${file.FILE_NAME}</a>
								</c:forEach></td>
								</tr>
								
								</c:if>
							<tr>
							
								<td class="td_title" style="text-align: center">考勤类型</td>
								<td>${infoApplyOt.OT_TYPE_NAME}</td>
								<td class="td_title" style="text-align: center">申请事由</td>
								<td colspan="5">${infoApplyOt.APPLY_OT_REMARK}</td>
							</tr>
						 
							<tr><td></td></tr>
						</table>
					</td>
				</tr>
					</div>
		</div>
			</c:if>
		<c:if test="${infoApplyOt.OT_TIME_TYPE eq 'P'  &&  infoApplyOt.APPLY_TYPE eq 'PERSON'}">
			 <div class="pageContent">
	<div class="pageFormContent">
				<tr>
					<td colspan="8">
						<table class="user_table" width="100%" border="0">
							<tr>
								<td class="td_title" style="text-align: center">社号|姓名</td>
								<td>[${infoApplyOt.EMPID}]${infoApplyOt.LOCAL_NAME}</td>
								<td class="td_title" style="text-align: center">部门</td>
								<td>${infoApplyOt.DEPT_NAME} </td>
								<td class="td_title" style="text-align: center">加班开始时间</td>
								<td>${infoApplyOt.OT_FROM_TIME}</td>
								<td class="td_title" style="text-align: center">加班结束时间</td>
								<td>${infoApplyOt.OT_TO_TIME}</td>
							</tr>
							 
							<tr>
								<td class="td_title" style="text-align: center">考勤类型</td>
								<td>${infoApplyOt.OT_TYPE_NAME}</td>
						 <c:if test="${infoApplyOt.CPNY_ID ne 'LGEQH' }">
								<td class="td_title" style="text-align: center">参考时长</td>
								<td>${infoApplyOt.OT_LENGTH}</td>
								<td class="td_title" style="text-align: center">申请事由</td>
								<td colspan="1">${infoApplyOt.APPLY_OT_REMARK}</td>
									<td class="td_title" style="text-align: center">附件</td>
										<td  ><c:forEach items="${infoApplyOt.fileList}" var="file" varStatus="j">	
									<a href="/LGEP/affirm/downloadFile?fileName=${file.FILE_URL }&file=${file.FILE_NAME}" >${file.FILE_NAME}</a>
								</c:forEach></td>
								</c:if>
					 <c:if test="${infoApplyOt.CPNY_ID eq 'LGEQH' }">
								<td class="td_title" style="text-align: center">申请事由</td>
								<td colspan="3">${infoApplyOt.APPLY_OT_REMARK}</td>
								<td class="td_title" style="text-align: center">附件</td>
								<td  ><c:forEach items="${infoApplyOt.fileList}" var="file" varStatus="j">	
									<a href="/LGEP/affirm/downloadFile?fileName=${file.FILE_URL}&file=${file.FILE_NAME}" >${file.FILE_NAME}</a>
								</c:forEach></td>
								</c:if>
								
							</tr>
							<c:if test="${infoApplyOt.CPNY_ID eq 'LGEHZ' }">
							<tr><td class="td_title" style="text-align: center">本月加班总计时长</td>
								<td  >${infoApplyOt.OT_COUNT} </td>
								<td class="td_title" style="text-align: center">本月周日累计时长</td>
								<td>${infoApplyOt.WEEKEND}</td>
								<td class="td_title" style="text-align: center">本月平日累计时长</td>
								<td colspan="4">${infoApplyOt.WEEKDAY} </td>
								  
							</tr>
							</c:if>
							
							 <c:if test="${infoApplyOt.CPNY_ID eq 'LGEYT' }">
							<tr>
							<td class="td_title" style="text-align: center">本月加班总计时长</td>
							<td colspan="8">&nbsp;&nbsp;&nbsp;      
							<c:if test="${infoApplyOt.OT_COUNT > 90 }">
							<font color="red">${infoApplyOt.OT_COUNT}</font>
							</c:if>
							<c:if test="${infoApplyOt.OT_COUNT <=90 }">
							 ${infoApplyOt.OT_COUNT} 
							</c:if>
							  </td> 
								  
							</tr>
							</c:if>
							 
							<tr><td></td></tr>
						</table>
					</td>
				</tr>
					</div>
		</div>
			</c:if>
	<div class="pageContent">
			<table class="user_table" layoutH="60" width="100%">
			
			
			
				<tr>
			  <input type="hidden" id="ottime_type_wq" name="ottime_type_wq" value="${infoApplyOt.OT_TIME_TYPE}" >
					<!-- 隐藏的一些参数 -->
					  <input type="hidden" id="APPLY_NO" name="APPLY_NO" value="${infoApplyOt.APPLY_NO}" >
					<c:if test="${infoApplyOt.OT_TIME_TYPE eq 'P'}">
					<input id="APPLY_TYPE_WQ" name="APPLY_TYPE_WQ" type="hidden" value="31" />
					</c:if>
					<c:if test="${infoApplyOt.OT_TIME_TYPE eq 'L'}">
					<input id="APPLY_TYPE_WQ" name="APPLY_TYPE_WQ" type="hidden" value="31" />
					</c:if>
					<input id="OT_TIME_TYPE_FLAG" name="OT_TIME_TYPE_FLAG" type="hidden" value="${infoApplyOt.OT_TIME_TYPE }" />
					<input id="APPLY_NO" name="APPLY_NO" type="hidden" value="${APPLY_NO }" />
					<input id="PERSON_ID" name="PERSON_ID" type="hidden" value="${PERSON_ID }" />
					<input id="AFFIRM_FLAG_ESS1231" name="AFFIRM_FLAG" type="hidden" value="" />
				</tr>
				<tr>
					<td class="td_title" style="text-align:right;width:7%;"><!-- 决裁线 -->
						决裁线
					</td>
					<td colspan="7">
						<table width="100%" border="0" cellpadding="0" cellspacing="0" id="affirmor_list">
							<thead>
								<td class="td_title" style="text-align: center;width:7%;"><!-- 审批等级 -->
								审批等级</td>
								<td class="td_title" style="text-align: center;width:16%;"><!-- 审批者 -->
								审批者</td>
								<td class="td_title" style="text-align: center;width:15%;"><!-- 审批情况 -->
								审批情况</td>
								<td class="td_title" style="text-align: center;width:15%;"><!-- 审批时间 -->
								审批时间</td>
								<td class="td_title" style="text-align: center;width:34%;"><!-- 审批批注 -->
								审批批注</td>
								<td class="td_title" style="text-align: center;width:7%;"><!-- 审批批注 -->
								审批者(+/-)</td>
								<td class="td_title" style="text-align: center;width:6%;"><!-- 决裁批注 -->
									check(+/-)
								</td>
							</thead>
							<tbody>
								<c:forEach items="${affirmorList}" var="affirmor" varStatus="j">	
								<tr id="${affirmor.AFFIRMOR_ID }">		
								 
										<td class="td_type" style="text-align: center">${affirmor.AFFIRM_LEVEL}</td>
										<td class="td_type" style="text-align: center">[${affirmor.EMPID}]${affirmor.LOCAL_NAME}/${affirmor.POSITION_NO}</td>
										<td class="td_type" style="text-align: center">
											<c:if test="${affirmor.AFFIRM_FLAG eq '0'}">未审批</c:if>
											<c:if test="${affirmor.AFFIRM_FLAG eq '1'}">通过</c:if>
											<c:if test="${affirmor.AFFIRM_FLAG eq '2'}">否决</c:if>
										</td>
										<td class="td_type" style="text-align: center">${affirmor.AFFIRM_DATE}</td>
										<td class="td_type">
											<c:if test="${affirmor.ESS_AFFIRM_NO ne essAffirmNo}">
												${affirmor.AFFIRM_CONTENT}
											</c:if>
											<c:if test="${affirmor.ESS_AFFIRM_NO eq essAffirmNo}">
												<input type="text" id="AFFIRM_CONTENT" name="AFFIRM_CONTENT" title="批注" size="55" maxlength="200" 
													value="${affirmor.AFFIRM_CONTENT}"/>
												<input id="pro_flag" name="pro_flag" type="hidden" value="0" />
												<!-- 隐藏的一些参数 -->
												<input id="APPLY_NO" name="APPLY_NO" type="hidden" value="${APPLY_NO }" />
													<input id="APPLY_TYPE" name="APPLY_TYPE" type="hidden" value="${affirmor.APPLY_TYPE }" />
												<input id="ESS_AFFIRM_NO" name="ESS_AFFIRM_NO" type="hidden" value="${affirmor.ESS_AFFIRM_NO }" />
												<input id="PERSON_ID" name="PERSON_ID" type="hidden" value="${PERSON_ID }" />
												<input id="AFFIRMOR_ID" name="AFFIRMOR_ID" type="hidden" value="${affirmor.AFFIRMOR_ID}" />
												<input id="APPLY_PERSON_ID" name="APPLY_PERSON_ID" type="hidden" value="${infoApplyOt.PERSON_ID}" />
										 	<input id="AFFIRM_FLAG" name="AFFIRM_FLAG" type="hidden" value="" />
										
												 
											</c:if>
										</td>
										<td class="td_type" style="text-align: center">
											<%-- 如果是自己决裁时，且未决裁时，允许添加决裁者 --%>
											<c:if test="${affirmor.AFFIRM_FLAG eq '0' && affirmor.AFFIRMOR_ID eq PERSON_ID}">
												<img id="${affirmor.AFFIRMOR_ID}" src="/resources/images/+.gif" title="添加决裁者"
													border="0" align="absmiddle" style="cursor:hand" onclick="addAffirmorRow(this.id,'${affirmor.AFFIRMOR_ID}')"/>
											</c:if>&nbsp;&nbsp;&nbsp;
										</td>
										<td class="td_type" style="text-align: center">
											<c:if test="${affirmor.AFFIRM_FLAG eq '0' && affirmor.AFFIRMOR_ID eq PERSON_ID}">
										
 	<img id="${affirmor.AFFIRMOR_ID}" src="/resources/images/+.gif" title="添加Check"
	 border="0" align="absmiddle" style="cursor:hand" onclick="addCheck('${affirmor.ESS_AFFIRM_NO}','${affirmor.AFFIRMOR_ID}')"/></c:if>
										</td>
									</tr>			
								</c:forEach>
							</tbody>
						</table>
						<input  type="hidden" id="flag" name="flag" value="0"></input>
					</td>
				</tr>
				<tr>
					<c:if test="${checkorListCnt > 0}">
						<td class="td_title" style="text-align:right;width:7%;" rowspan="${checkorListCnt*2+1 }"><!-- Review -->
							Review
						</td>
					</c:if>
					<c:if test="${checkorListCnt == 0}">
						<td class="td_title" style="text-align:right;width:7%;" rowspan="${3 }"><!-- Review -->
							Review
						</td>
					</c:if>
					<td colspan="7">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<thead>
								<td class="td_title" style="text-align:center;width:7%;"><!-- Type -->
									Type
								</td>
								<td class="td_title" style="text-align:center;width:46%;" colspan="3"><!-- Requests -->
									Requests
								</td>
								<td class="td_title" style="text-align:center;width:47%;" colspan="3"><!-- Reviewed -->
									Checked
								</td>
							</thead>
							<tbody>
								<c:forEach items="${checkorList}" var="checkor" varStatus="i">			
									<tr>
										<td class="td_type" style="text-align: center">Public</td>
										<td class="td_type" colspan="3">
											[${checkor.AFFIRM_EMPID}]${checkor.AFFIRM_NAME}&nbsp;${checkor.AFFIRM_POSITION}
											&nbsp;&nbsp;(${checkor.AFFIRM_DEPT_NAME})&nbsp;/&nbsp;${checkor.CHECK_CREATE_DATE}&nbsp;<br/>
											[Request]：${checkor.CHECK_REASON}
										</td>
										<td class="td_type" colspan="3">
											[${checkor.CHECK_EMPID}]${checkor.CHECK_NAME}&nbsp;${checkor.CHECK_POSITION}
											&nbsp;&nbsp;(${checkor.CHECK_DEPT_NAME})&nbsp;&nbsp;/&nbsp;
											<c:if test="${checkor.CHECK_FLAG == 0}">
												未Check
											</c:if>
											<c:if test="${checkor.CHECK_FLAG == 1}">
												${checkor.CHECKED_DATE}
											</c:if><br/>
											[Check]：${checkor.CHECK_CONTENT}
										</td>
									</tr>	
								</c:forEach>
								<c:if test="${checkorListCnt == 0}">
									<tr>
										<td class="td_type" rowspan="2">Public</td>
										<td class="td_type" colspan="3">无</td>
										<td class="td_type" colspan="3">无</td>
									</tr>
								</c:if>
							</tbody>
						</table>
				</tr>
			</table>
		<c:if test="${ESS_AFFIRM_NO ne '' && ifdisplay.AFFIRM_FLAG eq '0'}">
			<div class="formBar">
				<ul>
					<li>
						<div class="button">
							<div class="buttonContent"><!-- 通过 -->
								<button type="button" onclick="submitAffirmPreEss0242(1)">
									通过
								</button>
							</div>
						</div>
					</li>
					<li>
						<div class="button">
							<div class="buttonContent"><!-- 否决 -->
								<button type="button" onclick="submitAffirmPreEss0242(2)">
									否决
								</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</c:if>
</div>
	</form>
</body>

</html>