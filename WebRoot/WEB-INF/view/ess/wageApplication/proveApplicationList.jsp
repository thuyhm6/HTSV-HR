<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.easydrag.js"></script>
<script type="text/javascript">
//注意input的id和tr的id要一样
function addAffirmorRow(currentRowID){
  	//遍历每一行，找到指定id的行的位置i,然后在该行后添加新行
	$.each( $('table:last tbody tr'), function(i, tr){
      	if($(this).attr('id')==currentRowID){
        	//获取当前行
          	var currentRow=$('table:last tbody tr:eq('+i+')');
          	//要添加的行的id
          	var addRowID=i+2;
          	str = ''
	            +'<tr id = "'+addRowID+'">'
	            	+'<td style="text-align: center">'+addRowID+'</td>'
          			+'<td style="text-align: center">'
						+'<input id="personId'+addRowID+'" name="dwz.person.personId'+addRowID+'" value="" type="hidden" lookupGroup="person"/>'
						+'<input id="empId'+addRowID+'" name="dwz.person.empId'+addRowID+'" value="" type="hidden" lookupGroup="person"/>'
						+'<input id="empName'+addRowID+'" name="dwz.person.empName'+addRowID+'" value="" type="text" lookupGroup="person" '
						+'	onkeydown="submitKeyClick_affirmor(this,event)" class="required"/>'
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
 	var tb2 = document.getElementById("proAffirmor_list");
 	var rowCount = tb2.rows.length;
 	for(var m=0;m<rowCount;m++){
		tb2.rows[m].cells[0].innerHTML = m+1;
	}
}
function delRowAffirmor(){
	//存在一种情况无法解决，同时添加两人，再删除第一人，则再添加新决裁者为第二位出现错位现象
	var tb2 = document.getElementById("proAffirmor_list");
	   var rowCount = tb2.rows.length;
	   for(var m=0;m<rowCount;m++){
			tb2.rows[m].cells[0].innerHTML = m+1;
	   }
}

var keyCodeInit=0;
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
  	var $from = $("#addLeaveAffirmInfo");
  	$from.submit();
}

function validateLeaveAffirmCallback(form,callback) {	
	var $form = $(form);	
	if (!$form.valid()) {
		return false;
	}
	var applyNo = document.getElementById("APPLY_NO").value;
	var essAffirmNo  = document.getElementById("ESS_AFFIRM_NO").value;
    var person_id  = document.getElementById("PERSON_ID").value;
    var affirmFlag = document.getElementById("AFFIRM_FLAG").value;
    
	if(applyNo=="" || essAffirmNo=="" || person_id=="" || affirmFlag==""){
		alert(applyNo);
		alert(essAffirmNo);
		alert(person_id);
		alert(affirmFlag);
		alertMsg.error("信息决裁出现问题，请联系管理员!");
		return false;
	}
	var result = "确定要执行此次操作？";
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
</script>
<div class="pageContent">
	<div>
		<table width="100%" layoutH="360" class="user_table margin_b">
			<c:if test="${fn:length(messageList) > 0}">
				<tr>
					<td class="td_title" width="15%" style="text-align: center">申请者/工号</td>
					<td class="td_title" width="15%" style="text-align: center">费用类型</td>
					<td class="td_title" width="30%" style="text-align: center">申请费用发放期间</td>
					<td class="td_title" width="15%" style="text-align: center">金额</td>
					<td class="td_title" width="15%" style="text-align: center">备注</td>
				</tr>
			</c:if>
			<c:forEach items="${messageList}" var="message" varStatus="i">
				<tr>
					<td class="td_type" width="15%" style="text-align: center">${message.EMPNAME}[${message.EMPID}]</td>
					<td class="td_type" width="15%" style="text-align: center">${message.TYPENAME}</td>
					<td class="td_type" width="30%" style="text-align: center">${message.START_DATE}~${message.END_DATE}</td>
					<td class="td_type" width="15%" style="text-align: center">${message.MONEY}</td>
					<td class="td_type" width="15%" style="text-align: center">${message.DEMO}</td>
				</tr>
				<c:if test="${fn:length(messageList)==i.count}">
					<tr>
						<td class="td_type" width="15%" style="text-align: center"></td>
						<td class="td_type" width="15%" style="text-align: center"></td>
						<td class="td_type" width="30%" style="text-align: center"></td>
						<td class="td_type" width="15%" style="text-align: center">追加款：${message.ZHENG }</td>
						<td class="td_type" width="15%" style="text-align: center">追减款：${message.FU}</td>
					</tr>
				</c:if>
			</c:forEach>
		</table>
		<c:set value="/ess/wageApplication/proveApplicationList" var="pageUrl" />
		<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
	</div>
</div>
<div class="pageContent">
	<form id="addLeaveAffirmInfo" method="post"	action="/ess/wageApplication/approveApplication"
		class="pageForm required-validate" onsubmit="return validateLeaveAffirmCallback(this,navTabAjaxDone);">
		<div>
			<table class="user_table" width="100%">
				<tr>
					<!-- 隐藏的一些参数 -->
					<input id="APPLY_NO" name="APPLY_NO" type="hidden" value="${APPLY_NO}"/>
					<input id="PERSON_ID" name="PERSON_ID" type="hidden" value="${PERSON_ID}"/>
					<input id="AFFIRM_FLAG" name="AFFIRM_FLAG" type="hidden" value=""/>
					<td class="td_title" width="11%" style="text-align: center">决裁线</td>
					<td colspan="7">
						<table width="100%" border="0" cellpadding="0" cellspacing="0" id="affirmor_list">
							<thead>
								<td class="td_title" width="100" style="text-align: center">决裁等级</td>
								<td class="td_title" width="180" style="text-align: center">决裁者</td>
								<td class="td_title" width="100" style="text-align: center">决裁情况</td>
								<td class="td_title" width="180" style="text-align: center">审批时间</td>
								<td class="td_title" width="180" style="text-align: center">决裁批注</td>
								<td class="td_title" width="100" style="text-align: center">决裁者(+/-)</td>
								<td class="td_title" width="100" style="text-align: center">check(+/-)</td>
							</thead>
							<tbody id="proAffirmor_list">
								<c:forEach items="${proveList}" var="affirmor" varStatus="j">
									<c:if test="${fn:length(proveList)==affirmor.AFFIRM_LEVEL && affirmor.AFFIRMOR_ID eq PERSON_ID}">
										<input type="hidden" name="lastLevel" value="${affirmor.AFFIRMOR_ID}"/>
									</c:if>
									<tr id="${affirmor.AFFIRMOR_ID }">
										<td class="td_type" width="100" style="text-align: center">${affirmor.AFFIRM_LEVEL}</td>
										<td class="td_type" width="180" style="text-align: center">${affirmor.EMPNAME}[${affirmor.EMPID}]</td>
										<td class="td_type" width="100" style="text-align: center">
											<c:if test="${affirmor.AFFIRM_FLAG eq '0'}">未决裁</c:if>
											<c:if test="${affirmor.AFFIRM_FLAG eq '1'}">已通过</c:if>
											<c:if test="${affirmor.AFFIRM_FLAG eq '2'}">已否决</c:if>
										</td>
										<td class="td_type" width="180" style="text-align: center">${affirmor.AFFIRM_DATE}</td>
										<td class="td_type">
											<c:if test="${affirmor.AFFIRM_FLAG ne '0' || affirmor.AFFIRMOR_ID ne PERSON_ID}">${affirmor.AFFIRM_CONTENT}</c:if>
											<c:if test="${affirmor.AFFIRMOR_ID eq PERSON_ID}">
												<input type="text" id="AFFIRM_CONTENT" name="AFFIRM_CONTENT" title="批注" size="20" value="${affirmor.AFFIRM_CONTENT}"/>
												<input id="APPLY_TYPE" name="APPLY_TYPE" type="hidden" value="${affirmor.APPLY_TYPE }" />
												<input id="ESS_AFFIRM_NO" name="ESS_AFFIRM_NO" type="hidden" value="${affirmor.ESS_AFFIRM_NO }" />
											</c:if>
										</td>
										<td class="td_type" width="100" style="text-align: center">
											<c:if test="${affirmor.AFFIRM_FLAG eq '0' && affirmor.AFFIRMOR_ID eq PERSON_ID}">
												<img id="${affirmor.AFFIRMOR_ID}" src="/resources/images/+.gif" title="添加决裁者" border="0"
													align="absmiddle" style="cursor: hand" onclick="addAffirmorRow(this.id)" />
											</c:if>
										</td>
										<td class="td_type" width="100" style="text-align: center">
											<c:if test="${affirmor.AFFIRM_FLAG eq '0' && affirmor.AFFIRMOR_ID eq PERSON_ID}">
												<a rel="leaveApplyRemark" href="/ess/affirmLeaveApply/viewLeaveApplyRemarkInfo?seach_APPLY_NO=${APPLY_NO }&seach_ESS_AFFIRM_NO=${affirmor.ESS_AFFIRM_NO}&seach_PAGE_FLAG=PROVEAPP"
													title="添加Checkor" target="dialog" mask="true" width="600" height="350" id="leaveApplyRemarkHref"><img src="/resources/images/+.gif"/></a>
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
					<td class="td_type" colspan="8"><br /></td>
				</tr>
				<tr>
					<c:if test="${fn:length(checkorList) > 0}">
						<td class="td_title" width="10%" style="text-align: center" rowspan="${fn:length(checkorList)*2+1 }">Review</td>
					</c:if>
					<c:if test="${fn:length(checkorList)  == 0}">
						<td class="td_title" width="10%" style="text-align: center" rowspan="${3}">Review</td>
					</c:if>
					<td class="td_title" width="15%" style="text-align: center">Type</td>
					<td class="td_title" width="30%" style="text-align: center" colspan="3">Requests</td>
					<td class="td_title" width="45%" style="text-align: center" colspan="3">Reviewed</td>
				</tr>

				<c:forEach items="${checkorList}" var="checkor" varStatus="i">
					<tr>
						<td class="td_type" width="15%" style="text-align: center" rowspan="2">Public</td>
						<td class="td_type" width="30%" style="text-align: center" colspan="3">
							[${checkor.AFFIRM_EMPID}]${checkor.AFFIRM_NAME}&nbsp;${checkor.AFFIRM_POSITION}
							&nbsp;&nbsp;(${checkor.AFFIRM_DEPT_NAME})&nbsp;/&nbsp;${checkor.AFFIRM_DATE}&nbsp;
							<c:if test="${checkor.AFFIRM_FLAG == 0}">未决裁</c:if>
							<c:if test="${checkor.AFFIRM_FLAG == 1}">已通过</c:if>
							<c:if test="${checkor.AFFIRM_FLAG == 2}">已否决</c:if>
						</td>
						<td class="td_type" width="45%" style="text-align: center" colspan="3">
							[${checkor.CHECK_EMPID}]${checkor.CHECK_NAME}&nbsp;${checkor.CHECK_POSITION}
							&nbsp;&nbsp;(${checkor.CHECK_DEPT_NAME})&nbsp;/&nbsp;${checkor.CHECK_DATE}&nbsp;
							<c:if test="${checkor.CHECK_FLAG == 0}">未Check</c:if>
							<c:if test="${checkor.CHECK_FLAG == 1}">已Check</c:if>
						</td>
					</tr>
					<tr>
						<td class="td_type" width="30%" style="text-align: center" colspan="3">
							<textarea name="affirmRemark" cols="50" rows="2"
								disabled="disabled">[Request]：${checkor.CHECK_REASON}</textarea>
						</td>
						<td class="td_type" width="45%" style="text-align: center" colspan="3">
							<textarea name="checkRemark" cols="50" rows="2"
								disabled="disabled">[Check]：${checkor.CHECK_CONTENT}</textarea>
						</td>
					</tr>
				</c:forEach>
				<c:if test="${fn:length(checkorList) == 0}">
					<tr>
						<td class="td_type" width="15%" style="text-align: center" rowspan="2">Public</td>
						<td class="td_type" width="30%" style="text-align: center" colspan="3">&nbsp;</td>
						<td class="td_type" width="45%" style="text-align: center" colspan="3">&nbsp;</td>
					</tr>
					<tr>
						<td class="td_type" width="30%" style="text-align: center" colspan="3">&nbsp;</td>
						<td class="td_type" width="30%" style="text-align: center" colspan="3">&nbsp;</td>
					</tr>
				</c:if>
			</table>
		</div>
		<div class="formBar">
			<ul>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" onclick="submitAffirmPre(1)">
								通过
							</button>
						</div>
					</div>
				</li>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" onclick="submitAffirmPre(2)">
								否决
							</button>
						</div>
					</div>
				</li>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" class="close">
								<spring:message code="public.title.close" />
								<!-- 关闭 -->
							</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</form>
</div>