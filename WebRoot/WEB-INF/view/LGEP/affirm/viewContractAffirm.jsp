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
<script src="/resources/js/jquery/jquery.all.js" type="text/javascript"></script>
<script src="/resources/js/dwzUI/dwz.min.js" type="text/javascript"></script>
<script type="text/javascript">
//注意input的id和tr的id要一样
function addAffirmorRow_hr0306(currentRowID){
	var affirm_count = $("#affirm_count").val();
  	//遍历每一行，找到指定id的行的位置i,然后在该行后添加新行
	$.each( $('#affirmor_list tbody tr'), function(i, tr){
      	if($(this).attr('id')==currentRowID){
        	//获取当前行
          	var currentRow=$('#affirmor_list tbody tr:eq('+i+')');
          	//要添加的行的id
          	var addRowID= affirm_count;
          	str = ''
	            +'<tr id = "'+addRowID+'">'
	            	+'<td style="text-align: center">'+addRowID+'</td>'
          			+'<td style="text-align: center">'
						+'<input id="personId'+addRowID+'" name="dwz.person.personId'+addRowID+'" value="" type="hidden" lookupGroup="person"/>'
						+'<input id="empId'+addRowID+'" name="dwz.person.empId'+addRowID+'" value="" type="hidden" lookupGroup="person"/>'
						+'<input id="empName'+addRowID+'" name="dwz.person.empName'+addRowID+'" value="" type="text" lookupGroup="person" '
						+'onkeyup="SearchContent(this.value,' + addRowID + ')" class="required"/>'
          			+'</td>'
          			+'<td style="text-align: center">未审批</td>'
          			+'<td style="text-align: center">&nbsp;</td>'
          			+'<td style="text-align: center">&nbsp;</td>'
          			+'<td style="text-align: center">'
      					+'<img id= "'+addRowID+'" src="/resources/images/+.gif" style="cursor:hand" title="添加" onclick="addAffirmorRow_hr0306(this.id);"/>&nbsp;&nbsp;&nbsp;'
      					+'<img id= "'+addRowID+'" src="/resources/images/-.gif" style="cursor:hand" title="删除" '
      					//先删除，再排序
      					+'	onclick="javaScript:document.all.affirmor_list.deleteRow(event.srcElement.parentElement.parentElement.rowIndex);delRowAffirmor();"/>'
					+'</td>'
				+'</tr>';
          	//当前行之后插入一行
          	currentRow.after(str);
      	}
  	});
	$("#affirm_count").val(affirm_count + 1);
 	var tb2 = document.getElementById("affirmor_list");
 	var rowCount = tb2.rows.length;
 	for(var m=1;m<rowCount;m++){
		tb2.rows[m].cells[0].innerHTML = m;
	}
}
function delRowAffirmor(){
	//存在一种情况无法解决，同时添加两人，再删除第一人，则再添加新审批者为第二位出现错位现象
	var tb2 = document.getElementById("affirmor_list");
	   var rowCount = tb2.rows.length;
	   for(var m=1;m<rowCount;m++){
			tb2.rows[m].cells[0].innerHTML = m;
	   }
}


var ajaxGet_add;
function SearchContent(condition,id){
 
	var div='<div id="emp_list_panel" class="deptContent_sso" style="display:none;">'
		+'<div class="ztree_dept_sso">'
		+'<div class="ztree_dept_title">'
		+'<ul class="ztree_dept_table">'
		+'<li class="deptTreeLi" style="background:#E0D8DC;color:#796C5E;" onclick="selectedIt_hide(' + id + ')" title="关闭 " ><div>工号</div><div>姓名</div><i>部门</i></li>'
		+'</ul>'
		+'</div>'
		+'<div class="ztree_dept_type_sso"><ul id="emp_list_'+ id + '" class="ztree_dept_table" ></ul></div>'
 		+'</div></div>';

		$("#empName" + id).parent().append(div);
			if (ajaxGet_add != null) {
				ajaxGet_add.abort();
			}
			ajaxGet_add = $.ajax( {
						type : "POST",
						url : "/LGEP/affirm/getPersonCntXiao",
						data : {CONTENT : condition,AFFIRMOR_ID : $("#AFFIRMOR_ID").val()},
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


function submitAffirmPrehr0306(flag){
	
	$("#contract_affirm_hr0306_flag").val(flag);
  	$("#addContractAffirmInfo").submit();
}

function validateContractAffirmCallback(form,callback) {	
	var $form = $("#addContractAffirmInfo");
	var pro_flag = $("#pro_flag").val();
	if(pro_flag == 0){
		$("#pro_flag").val("1");
		$.ajaxSettings.global = true;
		$.ajax({
			type: form.method || 'POST',
			url:$form.attr("action"),
			data:$form.serializeArray(),
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
</script>
</head>
<body>
<div class="pageContent">
		<div class="pageFormContent">
			<table class="user_table" layoutH="60" width="100%">
						<tr>
							<td class="td_title" style="text-align: right;width:7%;">申请信息</td>
							<td colspan="6">
								<table class="user_table" width="100%" border="0">
									<tr>
										<td class="td_title" style="text-align: center">申请人</td>
										<td colspan="7">[${contractInfo.EMPID_C}]${contractInfo.LOCAL_NAME_C}</td>
									</tr>
									<tr>
										<td class="td_title" style="text-align: center">申请日期</td>
										<td colspan="7">${contractInfo.CREATE_DATE}</td>
									</tr>
									<tr>
										<td class="td_title" style="text-align: center;width:12%;">社号/姓名</td>
										<td style="width:12%;">[${contractInfo.EMPID}]${contractInfo.LOCAL_NAME}</td>
										<td class="td_title" style="text-align: center;width:12%;">合同次数</td>
										<td style="width:12%;">${contractInfo.TOTAL_PERIOD}</td>
										<td class="td_title" style="text-align: center;width:12%;">合同编号</td>
										<td style="width:12%;">${contractInfo.CONTRACT_NUMBER}</td>
										<td class="td_title" style="text-align: center;width:12%;">部门</td>
										<td style="width:12%;">${contractInfo.DEPTNAME}</td>
									</tr>
									<tr>
										<td class="td_title" style="text-align: center">合同类型</td>
										<td>${contractInfo.CONTRACT_TYPE_NAME}</td>
										<td class="td_title" style="text-align: center">合同版本</td>
										<td>${contractInfo.CONTRACT_VERSION}</td>
										<td class="td_title" style="text-align: center">起始日期</td>
										<td>${contractInfo.START_CONTRACT_DATE}</td>
										<td class="td_title" style="text-align: center">终止日期</td>
										<td>${contractInfo.END_CONTRACT_DATE}</td>
									</tr>
									<tr>
										<td class="td_title" style="text-align: center">工作地区</td>
										<td>${contractInfo.WORK_AREA_NAME}</td>
										<td class="td_title" style="text-align: center">签订意见</td>
										<td colspan="5">${contractInfo.REMARK}</td>
									</tr>
								</table>
							</td>
						</tr>
				</table>
			</div>
		</div>
		<div class="pageContent">	
	<form id="addContractAffirmInfo" method="post" action="/LGEP/affirm/affirmContract" class="pageForm required-validate" onsubmit="return validateContractAffirmCallback(this,navTabAjaxDone);">
				<table class="user_table" layoutH="60" width="100%">
				<tr>
					<td class="td_title" style="text-align: right;width:7%;"><!-- 审批线 -->
					审批线</td>
					<td colspan="6">
					<table width="100%" border="0" cellpadding="0" cellspacing="0"
						id="affirmor_list">
						<thead>
							<td class="td_title" style="text-align: center;width:10%;"><!-- 审批等级 -->
							审批等级</td>
							<td class="td_title" style="text-align: center;width:16%;"><!-- 审批者 -->
							审批者</td>
							<td class="td_title" style="text-align: center;width:15%;"><!-- 审批情况 -->
							审批情况</td>
							<td class="td_title" style="text-align: center;width:15%;"><!-- 审批时间 -->
							审批时间</td>
							<td class="td_title" style="text-align: center;width:34%;"><!-- 审批批注 -->
							审批批注</td>
							<td class="td_title" style="text-align: center;width:10%;"><!-- 审批批注 -->
							审批者(+/-)</td>
						</thead>
						<tbody>
							<c:forEach items="${affirmorList}" var="affirmor" varStatus="j">
								<tr id="${affirmor.AFFIRMOR_ID }_${j.index}">
									<td class="td_type" style="text-align: center">${affirmor.AFFIRM_LEVEL}</td>
									<td class="td_type" style="text-align: center">[${affirmor.EMPID}]${affirmor.LOCAL_NAME}</td>
									<td class="td_type" style="text-align: center">
										<c:if test="${affirmor.AFFIRM_FLAG eq '0'}">未审批</c:if>
										<c:if test="${affirmor.AFFIRM_FLAG eq '1'}">已通过</c:if>
										<c:if test="${affirmor.AFFIRM_FLAG eq '2'}">已否决</c:if>
									</td>
									<td class="td_type" style="text-align: center">${affirmor.UPDATE_DATE}</td>
									<td class="td_type">
										<c:if test="${affirmor.ESS_AFFIRM_NO ne ESS_AFFIRM_NO}">
											${affirmor.AFFIRM_CONTENT}
										</c:if> 
										<c:if test="${affirmor.ESS_AFFIRM_NO eq ESS_AFFIRM_NO}">
											<input type="text" id="AFFIRM_CONTENT" name="AFFIRM_CONTENT_0"
												title="批注" size="55" maxlength="200" value="${affirmor.AFFIRM_CONTENT}" />
											<input id="CONTRACT_NO" name="CONTRACT_NO_0" type="hidden"
												value="${contractInfo.CONTRACT_NO}" />
											<input name="hr0306Check" type="hidden" value="0" />
											<input type="hidden" id="AFFIRMOR_ID" name="AFFIRMOR_ID"
												value="${affirmor.AFFIRMOR_ID }" />
											<input type="hidden" name="ESS_AFFIRM_NO_0" value="${affirmor.ESS_AFFIRM_NO }" />
											<input id="contract_affirm_hr0306_flag" name="FLAG" type="hidden" value="1" />
											<input id="LANGUAGE" name="LANGUAGE" type="hidden" value="zh" />
											<input id="pro_flag" name="pro_flag" type="hidden" value="0" />
											<input name="AFFIRM_LEVEL_CURRENT_0" type="hidden" value="${affirmor.AFFIRM_LEVEL }" />
											<input id="APPLY_PERSON_ID" name="APPLY_PERSON_ID" type="hidden" value="${contractInfo.PERSON_ID}" />
											<input id="affirm_count" name="affirm_count" type="hidden" value="${fn:length(affirmorList)}" />
										</c:if>
									</td>
									<td class="td_type" style="text-align: center"><%-- 如果是自己审批时，且未审批时，允许添加审批者 --%>
									<c:if test="${affirmor.ESS_AFFIRM_NO eq ESS_AFFIRM_NO }">
										<img id="${affirmor.AFFIRMOR_ID}_${j.index}"
											src="/resources/images/+.gif" title="添加审批者" border="0"
											align="absmiddle" style="cursor: hand"
											onclick="addAffirmorRow_hr0306(this.id)" />
									</c:if></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					</td>
				</tr>
			</table>
			<c:if test="${ESS_AFFIRM_NO ne ''}">
				<div class="formBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="submitAffirmPrehr0306(1);">通过</button></div></div></li>
						<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="submitAffirmPrehr0306(2);">否决</button></div></div></li>
					</ul>
				</div>
			</c:if>
		</form>
	</div>
</body>
</html>