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
<script type="text/javascript">
var ajaxGet_add;
function SearchContent(condition,affirmor_id){
 		var ok = "ok";
	  	var id=1;
			if (ajaxGet_add != null) {
				ajaxGet_add.abort();
			}
			ajaxGet_add = $.ajax( {
						type : "POST",
						url : "/LGEP/affirm/getPersonCntXiao",
						data : {CONTENT : condition,AFFIRMOR_ID : affirmor_id,CHECK : ok}, 
						dataType : "json",
						success : function(data) {
							 
							$('#emp_list').html("");
							var html = '';
							if (typeof (data['pidEidList']) != "undefined") {
								$('#emp_list').show();
								$.each(data['pidEidList'],
												function(commentIndex, comment) {
													html += '<li class="deptTreeLi" onclick="selectedIt_add(\'' + comment.EMPID + '\',\'' + comment.LOCAL_NAME + '\',\'' + comment.DEPT_NAME+  '\',\'' + comment.PERSON_ID + '\')">'
													+ '<div>'
															+ comment.EMPID 
															+ '</div><div>'
															+ comment.LOCAL_NAME
															+ '</div><i>'
															+ comment.DEPT_NAME 
															+ '</i></li>';
												});
								
							}
						 
							$('#emp_list').html(html);
							$("#emp_list_panel").show();
						}
					});
}
function selectedIt_add(empid,localName,deptName,person){
 
	
	$("#PERSON_ID").val(person);
	$("#PERSON_IDD").val(person);
	$("#DEPT_NAME").html(deptName);
	$("#LOCAL_NAME").html(localName);
	$("#LOCAL_NAME").val(localName);
	$("#EMPID").val(empid);
	$('#emp_list_panel').hide();
}
//注意input的id和tr的id要一样
function addCheck(){
	

	var name = $("#LOCAL_NAME").val();
	 
	if(name==null||name==""){
		alert("请您输入正确的工号进行查询");
 		return false;
	}else{
		var pro_flag = $("#pro_flag").val();
		 
		 if(pro_flag == 0){
			$("#pro_flag").val("1");
	 		$.ajax({
			type: 'POST',
			url:$("#viewFullApplyRemarkInfo").attr("action"),
			data:$("#viewFullApplyRemarkInfo").serializeArray(),
			dataType:"json",
			cache: false,
			success: function(json){
				if (json.statusCode == 200){
					alert(json.message);
					window.close();
					window.returnValue='true';
				}else{
					alert(json.message);
					$("#pro_flag").val("0");
				}
			}
					});
		 }else{
			alert("添加check处理中，请稍后");

		 }

	} 
	 return false;
}

function closeCheck(){
	window.close();
	window.returnValue='true';
	//window.close();
	//window.opener.location.reload();
}
</script>
</head>
<body>
<div class="pageContent">
	<form id="viewFullApplyRemarkInfo" method="post" action="/LGEP/affirm/addApplyCheckList" class="pageForm required-validate" 
		onsubmit="return  addCheck();" >
		<div class="pageFormContent" style="overflow:visible;">
			<table width="100%"  border="0" cellpadding="0" cellspacing="0" class="user_table margin_b">
				<tr>
					<td class="td_title" >工号</td><input type="hidden" name="APPLY_TYPE" value="${APPLY_TYPE}">
					<input type="hidden" name="APPLY_NO" id="APPLY_NO" value="${APPLY_NO}">
					<td class="td_type">
									<input id="ESS_AFFIRM_NO" name="ESS_AFFIRM_NO" value="${ESS_AFFIRM_NO }" type="hidden"/>
									<input id="pro_flag" name="pro_flag" type="hidden" value="0" />	

							 <input id="OT_TIME_TYPE_FLAG" name="OT_TIME_TYPE_FLAG" value="${OT_TIME_TYPE}" type="hidden"/><!-- 加班专用 -->
									<input id="CHECKURL"  name="CHECKURL"  value="${CHECKURL }" type="hidden"/><!-- 发送check地址用-->

							 		<input id="OT_TIME_TYPE_FLAG" name="OT_TIME_TYPE_FLAG" value="${OT_TIME_TYPE}" type="hidden"/><!-- 加班专用 -->
									<input id="PERSON_ID" name="PERSON_ID" value="" type="hidden"/><!-- 主要用 PERSON_ID  和下面的ADMIN_ID两个参数 -->
									<input type="hidden" id="ADMIN_ID" name="ADMIN_ID" value="${AFFIRMOR_ID }"/> <!-- 这个${AFFIRMOR_ID}是传过来的决裁者，也就是登陆者 -->
									<input id="EMPID" name="dwz.person.empId" onkeyup="SearchContent(this.value, '${AFFIRMOR_ID}' )" type="text">
									<span style="color:red"> 输入工号或姓名进行查询</span>
						<div id="emp_list_panel" class="deptContent_sso" style="display:none;">
							<div class="ztree_dept_sso">
								<div class="ztree_dept_title">
									<ul class="ztree_dept_table">
											<li class="deptTreeLi" style="background:#E0D8DC;color:#796C5E;"><div>工号</div><div>姓名</div><i>部门</i></li>
									</ul>
								</div>
								<div class="ztree_dept_type_sso"><ul id="emp_list" class="ztree_dept_table" ></ul></div>
							</div>
						</div>
					</td>
				</tr>
				<tr>
					<td class="td_title" >
						姓名
					</td>
					<td class="td_type">
					<div id="LOCAL_NAME">
					
					</div>
					</td>
				</tr>
				<tr>
					<td class="td_title" >
						部门
					</td>
					<td class="td_type">
						<div id="DEPT_NAME">
							
						</div>
					
					</td>
				</tr>
				<tr>
					<td class="td_title" >
						Comments
					</td>
					<td class="td_type">
							<textarea id="CHECK_REASON" name="CHECK_REASON" rows="5" cols="60"></textarea>
					</td>
				</tr>
			</table>
		</div>
		<div class="formBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="submit"><!-- 提交 -->
								<spring:message code="public.title.submit"/>
							</button>
						</div>
					</div>
				</li>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button"  onclick="window.close();"; class="close"><!-- 取消 -->
								<spring:message code="public.title.cancle"/>
							</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</form>
</div>
</body>
</html>