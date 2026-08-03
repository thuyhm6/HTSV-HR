<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="../../inc/initTaglibs.jsp"%>

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
<script src="/resources/js/dwzUI/dwz.min.js" type="text/javascript"></script>
<script src="/resources/js/jquery/jquery.all.js" type="text/javascript"></script>
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
		+'<li class="deptTreeLi" style="background:#E0D8DC;color:#796C5E;"  onclick="selectedIt_hide(' + id + ')" ><div>工号</div><div>姓名</div><i>部门</i></li>'
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
<!--
function submitAffirmPreEss0242(flag){
	$("#AFFIRM_FLAG_LEAVE_APPLY").val(flag);
	var pro_flag = $("#pro_flag").val();
	if(pro_flag == 0){
		$("#pro_flag").val("1");
		$.ajax({
			type:$("#addMacAffirmInfo").method || 'POST',
			url:$("#addMacAffirmInfo").attr("action"),
			data:$("#addMacAffirmInfo").serializeArray(),
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
 	var tb2 = document.getElementById("affirmor_list");
 	var rowCount = tb2.rows.length;
 	for(var m=1;m<rowCount;m++){
		tb2.rows[m].cells[0].innerHTML = m;
	}
}
 
 

 
function addCheck(applyno,essAffirmNo,AFFIRMOR_ID){
	 
	var returnValue =  window.showModalDialog("/LGEP/affirm/addCheckInfo?APPLY_TYPE="+applyno+"&PAGE_FLAG=L_OTAPPLY&search_ot_time_type=${infoApplyOt.OT_TIME_TYPE}&PAGE_FLAG2=L_OTAPPLY&ESS_AFFIRM_NO=" + essAffirmNo + "&AFFIRMOR_ID=" + AFFIRMOR_ID,window,"dialogWidth=500px;dialogHeight=300px;help=no;center=yes;resizable=no;status=no;scroll=no");

	  if(returnValue == "true"){
          window.location.reload();
      }
	 // window.open("/LGEP/affirm/addCheckInfo?PAGE_FLAG=L_OTAPPLY&search_ot_time_type=${infoApplyOt.OT_TIME_TYPE}&PAGE_FLAG2=L_OTAPPLY&APPLY_NO=${infoApplyOt.APPLY_NO }&ESS_AFFIRM_NO=" + essAffirmNo +  "&PERSON_ID=" + personId + "&CPNY_ID=" + cpny_id,'添加check人','width=600, height=810, top=200, left=200, status=no, scrollbars=no,resizable=no');
}
//-->
</script>
</head>
<body>
<form id="addMacAffirmInfo" method="post" action="/LGEP/affirm/affirmAnnu" class="pageForm required-validate" onsubmit="return validateContractAffirmCallback(this,navTabAjaxDone);">

<c:if test="${annumap.BATCH_YN eq 'Y'}">
<div class="pageContent">
	<table class="table" width="100%" layoutH="240" nowrapTD="false">
		<thead>
			<tr>
				<th>社号</th>
				<th>姓名</th>
				<th>调整日期</th>
				<th>调整天数</th>
				<th>备注</th>
				<th>附件</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${arVacBatchAffirmList}" var="item" varStatus="i">			
				<tr>
					<td class='td_center'>${item.EMPID}</td>
					<td class='td_center'>${item.LOCAL_NAME}</td>
					<td class='td_center'>${item.APPLY_DATE}</td>
					<td class='td_center'>${item.APPLY_TANSHU}</td>
					<td style="text-align:left">${item.ANNUAL_LEAVE_REASON}</td>
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
<c:if test="${annumap.BATCH_YN eq 'N'}">
<div class="pageContent">
		<div class="pageFormContent">
			<table class="user_table" layoutH="60" width="100%">
						<tr>
							<td class="td_title" style="text-align: right;width:7%;">申请信息</td>
							<td colspan="6">
								<table class="user_table" width="100%" border="0">
									<tr>
										<td class="td_title" style="text-align: center;width:20%;">申请人</td>
										<td colspan="7" style="text-align: left;width:80%;">[${annumap.EMPID }]${annumap.NAME }</td>
									</tr>
									<tr>
										<td class="td_title" style="text-align: center">申请日期</td>
										<td colspan="7">${annumap.APPLY_DATE}</td>
									</tr>
									<tr>
										<td class="td_title" style="text-align: center">申请天数</td>
										<td colspan="7">${annumap.APPLY_TANSHU }</td>
									</tr>
									<tr>
										<td class="td_title" style="text-align: center">申请事由</td>
										<td colspan="7">${annumap.ANNUAL_LEAVE_REASON }</td>
									</tr>
									<tr>
										<td class="td_title" style="text-align: center">附件</td>
										<td colspan="7">
											<c:forEach items="${annumap.fileList}" var="file" varStatus="j">
													&nbsp;&nbsp;<a href="/LGEP/affirm/downloadFile?fileName=${file.FILE_URL }&file=${file.FILE_NAME}" >${file.FILE_NAME}</a>;
											</c:forEach>
										</td>
									</tr>
								</table>
							</td>
						</tr>
				</table>
			</div>
		</div>
	</c:if>
	<div class="pageContent">
			<table class="user_table" layoutH="60" width="100%">
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
									<tr id="${affirmor.AFFIRMOR_ID }_${j.index}">
										<td class="td_type" style="text-align: center">${affirmor.AFFIRM_LEVEL}</td>
										<td class="td_type" style="text-align: center">[${affirmor.EMPID}]${affirmor.LOCAL_NAME}</td>
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
												<input id="ESS_AFFIRM_NO" name="ESS_AFFIRM_NO" type="hidden" value="${affirmor.ESS_AFFIRM_NO }" />
												<input id="pro_flag" name="pro_flag" type="hidden" value="0" />
												<!-- 隐藏的一些参数 -->
												<input id="APPLY_NO" name="APPLY_NO" type="hidden" value="${paramMap.APPLY_NO }" />
												<input id="APPLY_TYPE" name="APPLY_TYPE" type="hidden" value="216691" />
											 
												<input id="AFFIRM_LEVEL" name="AFFIRM_LEVEL" type="hidden" value="${affirmor.AFFIRM_LEVEL}" />
												<input id="PERSON_ID" name="PERSON_ID" type="hidden" value="${paramMap.personId }" />
												<input id="AFFIRM_FLAG_LEAVE_APPLY" name="AFFIRM_FLAG" type="hidden" value="" />
												<input id="AFFIRMOR_ID" name="AFFIRMOR_ID" type="hidden" value="${affirmor.AFFIRMOR_ID}" />
											</c:if>
										</td>
										<td class="td_type" style="text-align: center">
											<%-- 如果是自己决裁时，且未决裁时，允许添加决裁者 --%>
											<c:if test="${affirmor.ESS_AFFIRM_NO eq essAffirmNo }">
												<img id="${affirmor.AFFIRMOR_ID}_${j.index}" src="/resources/images/+.gif" title="添加决裁者"
													border="0" align="absmiddle" style="cursor:hand" onclick="addAffirmorRow(this.id,'${affirmor.AFFIRMOR_ID}')"/>
											</c:if>
										</td>
										<td class="td_type" style="text-align: center">
											<c:if test="${affirmor.ESS_AFFIRM_NO eq essAffirmNo}">
											<img id="${affirmor.AFFIRMOR_ID}" src="/resources/images/+.gif" title="添加Check"
												border="0" align="absmiddle" style="cursor:hand" onclick="addCheck('216691','${affirmor.ESS_AFFIRM_NO}','${affirmor.AFFIRMOR_ID}')"/>
											</c:if>
										</td>
									</tr>			
								</c:forEach>
							</tbody>
						</table>
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
		<c:if test="${essAffirmNo ne ''}">
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
</body>
</html>