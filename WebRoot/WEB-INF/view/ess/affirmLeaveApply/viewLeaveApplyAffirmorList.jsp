<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.easydrag.js"></script>
<script type="text/javascript">
<!--
//注意input的id和tr的id要一样
function addAffirmorRow_ess0242(currentRowID){
  	//遍历每一行，找到指定id的行的位置i,然后在该行后添加新行
	$.each( $('#affirmor_list_leave tbody tr'), function(i, tr){
      	if($(this).attr('id')==currentRowID){
        	//获取当前行
          	var currentRow=$('#affirmor_list_leave tbody tr:eq('+i+')');
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
      					+'<img id= "'+addRowID+'" src="/resources/images/+.gif" style="cursor:hand" title="添加" onclick="addAffirmorRow_ess0242(this.id);"/>&nbsp;&nbsp;&nbsp;'
      					+'<img id= "'+addRowID+'" src="/resources/images/-.gif" style="cursor:hand" title="删除" '
      					//先删除，再排序
      					+'	onclick="javaScript:document.all.affirmor_list_leave.deleteRow(event.srcElement.parentElement.parentElement.rowIndex);delRowAffirmor();"/>'
					+'</td>'
          			+'<td style="text-align: center">&nbsp;</td>'
				+'</tr>';
          	//当前行之后插入一行
          	currentRow.after(str);
      	}
  	});
 	var tb2 = document.getElementById("affirmor_list_leave");
 	var rowCount = tb2.rows.length;
 	for(var m=1;m<rowCount;m++){
		tb2.rows[m].cells[0].innerHTML = m;
	}
}
function delRowAffirmor(){
	//存在一种情况无法解决，同时添加两人，再删除第一人，则再添加新决裁者为第二位出现错位现象
	var tb2 = document.getElementById("affirmor_list_leave");
	   var rowCount = tb2.rows.length;
	   for(var m=1;m<rowCount;m++){
			tb2.rows[m].cells[0].innerHTML = m;
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
    var affirmFlag = document.getElementById("AFFIRM_FLAG_LEAVE_APPLY").value;
    
	if(applyNo=="" || essAffirmNo=="" || person_id=="" || affirmFlag==""){
		alertMsg.error("信息决裁出现问题，请联系管理员!");
		return false;
	}
	var result = "确定要执行此次操作？";
	if(affirmFlag==1){
		result = "确定要通过此条申请信息？";
	}else if(affirmFlag==2){
		result = "确定要否决此条申请信息？";
	}
	alertMsg.confirm(result,{
		okCall:function(){
			$.ajax({
				type: form.method || 'POST',
				url:$form.attr("action"),
				data:$form.serializeArray(),
				dataType:"json",
				cache: false,
				success: callback || DWZ.ajaxDone,
				error: DWZ.ajaxError
			});
		}});
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

function addRowByIDEss0242_Check(ESS_AFFIRM_NO){
	$.pdialog.open("/ess/affirmLeaveApply/viewLeaveApplyRemarkInfo?PAGE_FLAG=LEAVE_APPLY&seach_APPLY_NO=${APPLY_NO }&seach_ESS_AFFIRM_NO=" + ESS_AFFIRM_NO, "addRowByIDEss0242_Check", "添加Check人", {width:600,height:350,mask:true});
}
//-->
</script>
<c:if test="${infoApplyLeave.APPLY_TYPE eq 'BATCH'}">
<div class="pageContent">
	<table class="table" width="100%" layoutH="240" nowrapTD="false">
		<thead>
			<tr>
				<th>社号</th>
				<th>姓名</th>
				<th>Leave开始时间</th>
				<th>Leave结束时间</th>
				<th>Leave时长</th>
				<th>Leave类型</th>
				<th>总天数</th>
				<th>已使用天数</th>
				<th>剩余天数</th>
				<th>Leave原因</th>
				<th>附件</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${leaveBatchAffirmList}" var="item" varStatus="i">			
				<tr>
					<td class='td_center'>${item.EMPID}</td>
					<td class='td_center'>${item.LOCAL_NAME}</td>
					<td class='td_center'>${item.LEAVE_FROM_TIME}</td>
					<td class='td_center'>${item.LEAVE_TO_TIME}</td>
					<td class='td_center'>${item.APPLY_LENGTH_DISPLAY}</td>
					<td class='td_center'>${item.LEAVE_TYPE}</td>
					<td class='td_center'>${item.LEAVE_TOTAL}</td>
					<td class='td_center'>${item.LEAVE_USE}</td>
					<td class='td_center'>${item.LEAVE_SURPLUS}</td>
					<td style="text-align:left">${item.LEAVE_REASON}</td>
					<td style="text-align:left">
						<c:forEach items="${item.fileList}" var="file" varStatus="j">	
								&nbsp;&nbsp;<a href="/ess/infoApplyLeave/downloadFile?fileName=${file.FILE_URL }&file=${file.FILE_NAME}" >${file.FILE_NAME}</a>;
						</c:forEach>
					</td>
				</tr>			
			</c:forEach>			
		</tbody>
	</table>
	<c:set value="/ess/affirmLeaveApply/viewLeaveApplyAffirmorList?APPLY_NO=${infoApplyLeave.APPLY_NO}" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>
</c:if>
<div class="pageContent">
	<form id="addLeaveAffirmInfo" method="post" action="/ess/affirmLeaveApply/addLeaveAffirmInfo" class="pageForm required-validate" 
			onsubmit="return validateLeaveAffirmCallback(this,navTabAjaxDoneWithParentParam);">
		<div>
			<table class="user_table" width="100%">	
			<c:if test="${infoApplyLeave.APPLY_TYPE eq 'PERSON'}">
				<tr>
					<td colspan="8">
						<table class="user_table" width="100%" border="0">
							<tr>
								<td class="td_title" style="text-align: center;width:12%;">社号/姓名</td>
								<td class="td_type" style="text-align:left;width:12%;">[${infoApplyLeave.EMPID}]${infoApplyLeave.LOCAL_NAME}</td>
								<td class="td_title" style="text-align: center;width:12%;">开始日期时间</td>
								<td class="td_type" style="text-align:left;width:12%;">${infoApplyLeave.LEAVE_FROM_TIME}&nbsp;${infoApplyLeave.FROMTIME}</td>
								<td class="td_title" style="text-align: center;width:12%;">结束日期时间</td>
								<td class="td_type" style="text-align:left;width:12%;">${infoApplyLeave.LEAVE_TO_TIME}&nbsp;${infoApplyLeave.TOTIME}</td>
								<td class="td_title" style="text-align: center;width:12%;">Leave时长</td>
								<td>${infoApplyLeave.APPLY_LENGTH_DISPLAY}</td>
							</tr>
							<tr>
								<td class="td_title" style="text-align: center;width:12%;">考勤类型</td>
								<td class="td_type" style="text-align:left;width:12%;">${infoApplyLeave.APPLY_TYPE_NAME}</td>
								<td class="td_title" style="text-align: center">Leave/销假</td>
								<td class="td_type" style="text-align:left;width:12%;">
									<c:if test="${infoApplyLeave.APPLY_TYPE_NAME eq '销假' }">销假</c:if>
									<c:if test="${infoApplyLeave.APPLY_TYPE_NAME ne '销假' }">Leave</c:if>
								</td>
								<td class="td_title" style="text-align: center">附件</td>
								<td class="td_type" style="text-align:left;width:12%;">
									<c:forEach items="${infoApplyLeave.fileList}" var="file" varStatus="j">	
										<a href="/ess/infoApplyLeave/downloadFile?fileName=${file.FILE_URL }&file=${file.FILE_NAME}" >${file.FILE_NAME}</a>
									</c:forEach>
								</td>
								<td class="td_title" style="text-align: center">申请事由</td>
								<td  class="td_type" style="text-align:left;width:12%;" >${infoApplyLeave.LEAVE_REASON}</td>
							</tr>
							<c:if test="${infoApplyLeave.LEAVE_TYPE_CODE eq '26' or infoApplyLeave.LEAVE_TYPE_CODE eq '18135'}">
								<tr>
								<td class="td_title" style="text-align: center;width:12%;">${infoApplyLeave.APPLY_TYPE_NAME}总天数</td>
								<td class="td_type" style="text-align:left;width:12%;">${infoApplyLeave.LEAVE_TOTAL}</td>
								<td class="td_title" style="text-align: center">已使用${infoApplyLeave.APPLY_TYPE_NAME}天数</td>
								<td class="td_type" style="text-align:left;width:12%;">${infoApplyLeave.LEAVE_USE}</td>
								<td class="td_title" style="text-align: center">剩余${infoApplyLeave.APPLY_TYPE_NAME}天数</td>
								<td class="td_type" style="text-align:left;width:12%;">${infoApplyLeave.LEAVE_SURPLUS}</td>
								<td class="td_title" style="text-align: center"></td>
								<td  class="td_type" style="text-align:left;width:12%;"></td>
							</tr>
							</c:if>
						</table>
					</td>
				</tr>
			</c:if>
				<tr>
					<td class="td_title" width="10%" style="text-align: center"><!-- 决裁线 -->
						决裁线
					<!-- 隐藏的一些参数 -->
					<input id="APPLY_NO" name="APPLY_NO" type="hidden" value="${APPLY_NO }" />
					<c:if test="${infoApplyLeave.APPLY_TYPE eq 'BATCH'}">
					<input id="APPLY_TYPE" name="APPLY_TYPE" type="hidden" value="${infoApplyLeave.LEAVE_TYPE_CODE }" />
					</c:if>
					<c:if test="${infoApplyLeave.APPLY_TYPE eq 'PERSON'}">
					<input id="APPLY_TYPE" name="APPLY_TYPE" type="hidden" value="${infoApplyLeave.LEAVE_TYPE_CODE }" />
					</c:if>
					<input id="PERSON_ID" name="PERSON_ID" type="hidden" value="${PERSON_ID }" />
					<input id="AFFIRM_FLAG_LEAVE_APPLY" name="AFFIRM_FLAG" type="hidden" value="" />
					<input id="parentParam" name="parentParam" type="hidden" value="${parentParam }" />
					</td>
					<td colspan="7">
						<table width="100%" border="0" cellpadding="0" cellspacing="0" id="affirmor_list_leave">
							<thead>
							<td class="td_title" style="text-align: center;width:8%;"><!-- 决裁等级 -->
								决裁等级
							</td>
							<td class="td_title" style="text-align: center;width:15%;"><!-- 决裁者 -->
								决裁者
							</td>
							<td class="td_title" style="text-align: center;width:12%;"><!-- 决裁情况 -->
								决裁情况
							</td>
							<td class="td_title" style="text-align: center;width:15%;"><!-- 审批时间 -->
								审批时间
							</td>
							<td class="td_title" style="text-align: center;width:34%;"><!-- 决裁批注 -->
								决裁批注
							</td>
							<td class="td_title" style="text-align: center;width:8%;"><!-- 决裁批注 -->
								决裁者(+/-)
							</td>
							<td class="td_title" style="text-align: center;width:8%;"><!-- 决裁批注 -->
								check(+/-)
							</td>
							</thead>
							<tbody>
								<c:forEach items="${affirmorList}" var="affirmor" varStatus="j">			
									<tr id="${affirmor.AFFIRMOR_ID }_${j.index}">
										<td class="td_type" style="text-align: center">${affirmor.AFFIRM_LEVEL}</td>
										<td class="td_type" style="text-align: center">[${affirmor.EMPID}]${affirmor.LOCAL_NAME}<c:if test="${defaultCpny eq 'SST'}">/${affirmor.POSITION_NO }</c:if></td>
										<td class="td_type" style="text-align: center">
											<c:if test="${affirmor.AFFIRM_FLAG eq '0'}">未决裁</c:if>
											<c:if test="${affirmor.AFFIRM_FLAG eq '1'}">已通过</c:if>
											<c:if test="${affirmor.AFFIRM_FLAG eq '2'}">已否决</c:if>
										</td>
										<td class="td_type" style="text-align: center">${affirmor.AFFIRM_DATE}</td>
										<td class="td_type">
											<c:if test="${affirmor.ESS_AFFIRM_NO ne essAffirmNo}">
												${affirmor.AFFIRM_CONTENT}
											</c:if>
											<c:if test="${affirmor.ESS_AFFIRM_NO eq essAffirmNo}">
												<input type="text" id="AFFIRM_CONTENT" name="AFFIRM_CONTENT" title="批注" size="55" maxlength="200" 
													value="${affirmor.AFFIRM_CONTENT}"/>
												<input id="AFFIRMOR_ID" name="AFFIRMOR_ID" type="hidden" value="${affirmor.AFFIRMOR_ID }" />
												<input id="ESS_AFFIRM_NO" name="ESS_AFFIRM_NO" type="hidden" value="${affirmor.ESS_AFFIRM_NO }" />
											</c:if>
										</td>
										<td class="td_type" style="text-align: center">
											<%-- 如果是自己决裁时，且未决裁时，允许添加决裁者 --%>
											<c:if test="${affirmor.ESS_AFFIRM_NO eq essAffirmNo}">
												<img id="${affirmor.AFFIRMOR_ID}_${j.index}" src="/resources/images/+.gif" title="添加决裁者"
													border="0" align="absmiddle" style="cursor:hand" onclick="addAffirmorRow_ess0242(this.id)"/>
											</c:if>
										</td>
										<td class="td_type" style="text-align: center">
											<c:if test="${affirmor.ESS_AFFIRM_NO eq essAffirmNo}">
											<img id="${affirmor.AFFIRMOR_ID}" src="/resources/images/+.gif" title="添加Check"
												border="0" align="absmiddle" style="cursor:hand" onclick="addRowByIDEss0242_Check(${affirmor.ESS_AFFIRM_NO})"/>
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
						<td class="td_title" width="10%" style="text-align: center" rowspan="${checkorListCnt*2+1 }"><!-- Review -->
							Review
						</td>
					</c:if>
					<c:if test="${checkorListCnt == 0}">
						<td class="td_title" width="10%" style="text-align: center" rowspan="2"><!-- Review -->
							Review
						</td>
					</c:if>
					<td colspan="7">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<thead>
								<tr>
									<td class="td_title" style="text-align: center;width:8%;"><!-- Type -->
										Type
									</td>
									<td class="td_title" style="text-align: center;width:42%;"><!-- Requests -->
										Requests
									</td>
									<td class="td_title" style="text-align: center;width:50%;"><!-- Reviewed -->
										Checked
									</td>
								</tr>
							</thead>
							<tbody>
							<c:forEach items="${checkorList}" var="checkor" varStatus="i">			
								<tr>
									<td class="td_type" style="text-align: center">Public</td>
									<td class="td_type">
										[${checkor.AFFIRM_EMPID}]${checkor.AFFIRM_NAME}&nbsp;${checkor.AFFIRM_POSITION}
										&nbsp;&nbsp;(${checkor.AFFIRM_DEPT_NAME})&nbsp;/&nbsp;${checkor.CHECK_CREATE_DATE}&nbsp;<br/>
										[Request]：${checkor.CHECK_REASON}
									</td>
									<td class="td_type">
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
									<td class="td_type" style="text-align: center">Public</td>
									<td class="td_type">无</td>
									<td class="td_type">无</td>
								</tr>
							</c:if>
							</tbody>
						</table>
					</td>
			</table>
		</div>
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
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" class="close">
								<spring:message code="public.title.cancle"/><!-- 关闭 -->
							</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</form>
</div>