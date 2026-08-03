<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="../../inc/initTaglibs.jsp"%>
 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
 

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
//添加决裁者
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
	
	function affirmDimission(flag){
		$("#affirmDimissionApplyInfoFLAG_affirm").val(flag);
		$("#affirmDimissionLgepApplyInfo").submit();
	}
	function addRowByIDess2017_Check(essAffirmNo,AFFIRMOR_ID){
		//$.pdialog.open("/ess/editionAffirm/viewAddCheckEssDimission?AFFIRM_NO=${affirm_no}", "addRowByIDess2017_Check", "添加Check人", {width:400,height:200,mask:true});
	  var returnValue =  window.showModalDialog("/LGEP/affirm/addCheckInfo?APPLY_TYPE=218296&APPLY_NO=${affirm_no}&ESS_AFFIRM_NO=" + essAffirmNo +  "&AFFIRMOR_ID=" + AFFIRMOR_ID,window,"dialogWidth=500px;dialogHeight=300px;help=no;center=yes;resizable=no;status=no;scroll=no");
	  if(returnValue == "true"){
          window.location.reload();
      }
	}

function validateDimissionAffirmCallback(form,callback) {	
	var $form = $("#affirmDimissionLgepApplyInfo");
	if (!$form.valid()) {
			return false;
		}
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
				}
			}
		});
		return false;
}
</script>
</head>
<body><div class="pageContent">
	<table class="table" width="100%" layoutH="350" nowrapTD="false">
		<thead>
			<tr>
				<th width="5%">
					<!-- 工号 --> <spring:message code="display.emp.ben.serviceno" />
				</th>
				<th width="5%">
					<!-- 姓名 --> <spring:message code="inct.salesman.Name" />
				</th>
				<th width="10%">
					<!-- 人员类型 --> <spring:message code="is.company.title.PERSON_TYPE" />
				</th>
				<th width="10%"><spring:message
						code="ess.infoApply.title.essApplyTime" /> <!-- 申请日期 -->
				</th>
				<th width="10%"><spring:message
						code="display.pa.ecc.expectresigndate" /> <!-- 预离职日期 -->
				</th>
				<th width="21%"><spring:message
						code="ess.trans.title.resignReason" /> <!-- 离职原因-->
				</th>
			</tr>
		</thead>
		<tbody>
									<tr>
										<td style="text-align:center">${applyorInfo.EMPID } 
										<input type="hidden" name="APPLY_NO" value="${applyorInfo.APPLY_NO}" />
										<input type="hidden" name="PERSON_ID" value="${applyorInfo.PERSON_ID}" />
										<input type="hidden" name="DEPTNO" value="${applyorInfo.DEPTNO}" />
										</td>
										<td style="text-align:center">${applyorInfo.LOCAL_NAME }</td>
										<td style="text-align:center">${applyorInfo.EMP_TYPE_NAME }</td>
										<td style="text-align:center">${applyorInfo.APPLY_TIME }</td>
										<td style="text-align:center">${applyorInfo.APPLY_LEAVE_TIME }</td>
										<td style="text-align:center">${applyorInfo.APPLY_REASON}</td>
									</tr>
		</tbody>
	</table>
</div>
	<div class="pageContent">
		<form id="affirmDimissionLgepApplyInfo" method="post"
			action="/LGEP/affirm/affirmDimissionApplyInfo"
			class="pageForm required-validate"
			onsubmit="return validateDimissionAffirmCallback(this, navTabAjaxDone)">
			<input type="hidden" id="APPLY_NO" name="APPLY_NO"
				value="${APPLY_NO }" />
			<input type="hidden" name="personId" id="personId" value="${PERSON_ID}"/>	
			<input type="hidden" name="PERSON_ID" id="PERSON_ID" value="${applyorInfo.PERSON_ID}"/>	
			<div class="pageFormContent">
				<table width="100%" border="0" cellpadding="0" cellspacing="0"
					class="user_table margin_b">
					<tr>
						<td class="td_title" style="text-align: center">审批线</td>
						<td>
							<table width="100%" border="0" cellpadding="0" cellspacing="0"
								id="affirmor_list">
								<tbody>
									<tr>
										<td class="td_title" style="text-align: center" width="10%">
											审批等级</td>
										<td class="td_title" style="text-align: center" width="15%">
											审批者</td>
										<td class="td_title" style="text-align: center" width="11%">
											审批情况</td>
										<td class="td_title" style="text-align: center" width="15%">
											审批时间</td>
										<td class="td_title" style="text-align: center" width="35%">
											审批批注</td>
										<td class="td_title" style="text-align: center" width="7%">
											审批者(+/-)</td>
										<td class="td_title" style="text-align: center" width="7%">
											check(+)</td>
									</tr>
									<c:forEach items="${affirmList}" var="affirmor" varStatus="j">
										 	<tr id="${affirmor.AFFIRMOR_ID }">		
											<td class="td_type" style="text-align: center">
												${j.count}</td>
											<td class="td_type" style="text-align: center">
												[${affirmor.EMPID}]-${affirmor.LOCAL_NAME } <c:if
													test="${affirmor.AFFIRM_FLAG eq '0' }">
													<input type="hidden" name=AFFIRMOR_ID
														value="${affirmor.AFFIRMOR_ID }" />
												</c:if>
											</td>
											<td class="td_type" style="text-align: center">
												<c:if test="${affirmor.AFFIRM_FLAG eq '0'}">未审批</c:if> <c:if
													test="${affirmor.AFFIRM_FLAG eq '1'}">已通过</c:if> <c:if
													test="${affirmor.AFFIRM_FLAG eq '2'}">已否决</c:if>
											</td>
											<td class="td_type" style="text-align: center">
												${affirmor.UPDATE_DATE}</td>
											<td class="td_type" style="text-align: center">
												<c:if test="${affirmor.AFFIRMOR_ID eq PERSON_ID}">
													<c:if test="${affirmor.AFFIRM_FLAG eq '0'}">
														<input type="text" name="AFFIRM_CONTENT"  size="45" maxlength="200"/>
														<input type="hidden" name="ESS_AFFIRM_NO"
															value="${affirmor.ESS_AFFIRM_NO}" />
													</c:if>
												</c:if> 
										<c:if test="${affirmor.ESS_AFFIRM_NO ne affirm_no}">
											${affirmor.AFFIRM_CONTENT}
										</c:if>
											</td>
											<td class="td_type" style="text-align: center">
												<c:if test="${affirmor.AFFIRMOR_ID eq PERSON_ID}">
												  <c:if test="${affirmor.AFFIRM_FLAG eq '0'}">
												<input type="hidden" name="ESS_AFFIRM_NO" value="${affirmor.ESS_AFFIRM_NO}" />
													<img src="/resources/images/+.gif" title="添加" border="0" id="${affirmor.AFFIRMOR_ID}"
														align="absmiddle" style="cursor:hand"
														 onclick="addAffirmorRow(this.id,'${affirmor.AFFIRMOR_ID}')"/>
												</c:if>
												</c:if>
											</td>
											<td class="td_type" style="text-align: center">
												<c:if test="${affirmor.AFFIRMOR_ID eq PERSON_ID}">
												<c:if test="${affirmor.AFFIRM_FLAG eq '0'}">
												<input type="hidden" name="ESS_AFFIRM_NO"
															value="${affirmor.ESS_AFFIRM_NO}" />
													<img src="/resources/images/+.gif" title="添加" border="0"
														align="absmiddle" style="cursor:hand"
														onclick="addRowByIDess2017_Check('${affirmor.ESS_AFFIRM_NO}','${affirmor.AFFIRMOR_ID}')" />
												</c:if>
												</c:if>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</td>
					</tr>
					<tr>
						<td class="td_title" style="text-align: center">Check</td>
						<td>
							<table width="100%" border="0" cellpadding="0" cellspacing="0"
								id="addACheck_listPa0901_affirm">
								<tbody>
									<tr>
										<td class="td_title" style="text-align: center">Type</td>
										<td class="td_title" style="text-align: center">Requests
										</td>
										<td class="td_title" style="text-align: center">Checked</td>
									</tr>
									<c:forEach items="${checkList}" var="check" varStatus="j">
										<tr>
											<td class="td_type" style="text-align: center" width="10%">
												public</td>
											<td class="td_type" width="41%">
												[${check.EMPID_R}]-${check.LOCAL_NAME_R
												}&nbsp;&nbsp;${check.POSITION_NO_R
												}&nbsp;&nbsp;(${check.DEPTNAME_R })/${check.DATE_R }<br />
												[Request]${check.CHECK_REASON}</td>
											<td class="td_type" width="49%">
												[${check.EMPID_C}]-${check.LOCAL_NAME_C
												}&nbsp;&nbsp;${check.POSITION_NO_C
												}&nbsp;&nbsp;(${check.DEPTNAME_C }) <c:if
													test="${check.CHECK_FLAG eq '0'}">
											/未Check
										</c:if> <c:if test="${check.CHECK_FLAG ne '0'}">
										/${check.DATE_C }
										</c:if> <br /> <c:if test="${check.CHECK_FLAG eq '1'}">
											[Check]：${check.CHECK_CONTENT}
										</c:if> <c:if test="${check.ESS_CHECK_NO eq check_no}">
											[Check]：
													<input type="text" name="CHECK_CONTENT"  size="55" maxlength="200"/>
													<input type="hidden" name="ESS_CHECK_NO"
														value="${check.ESS_CHECK_NO}" />
												</c:if>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</td>
					</tr>
				</table>
				<input type="hidden" name="affirmCount" id="affirmCount_affirm"
					value="${affirmorListCnt }"> <input type="hidden"
					id="affirmDimissionApplyInfoFLAG_affirm" name="FLAG" value="1" />
					<input type="hidden" id="dept_level" name="dept_level"
					value="${dept_level}" />
			</div>
			<c:if test="${affirmFlag eq '0'}">
				<div class="formBar">

					<ul>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="affirmDimission(1);">通过</button>
								</div>
							</div></li>
						<li><div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="affirmDimission(2);">否决</button>
								</div>
							</div>
						</li>
					</ul>
				</div>
			</c:if>
		</form>
	</div>
</body>
</html>