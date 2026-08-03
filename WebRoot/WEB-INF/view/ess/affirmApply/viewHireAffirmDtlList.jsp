<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.easydrag.js"></script>
<script type="text/javascript">
<!--
//注意input的id和tr的id要一样
function addAffirmorRow_ess0252(currentRowID){
  	//遍历每一行，找到指定id的行的位置i,然后在该行后添加新行
	$.each( $('#hire_affirmor_list tbody tr'), function(i, tr){
      	if($(this).attr('id')==currentRowID){
        	//获取当前行
          	var currentRow=$('#hire_affirmor_list tbody tr:eq('+i+')');
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
      					+'<img id= "'+addRowID+'" src="/resources/images/+.gif" style="cursor:hand" title="添加" onclick="addAffirmorRow_ess0252(this.id);"/>&nbsp;&nbsp;&nbsp;'
      					+'<img id= "'+addRowID+'" src="/resources/images/-.gif" style="cursor:hand" title="删除" '
      					//先删除，再排序
      					+'	onclick="javaScript:document.all.hire_affirmor_list.deleteRow(event.srcElement.parentElement.parentElement.rowIndex);delRowAffirmor();"/>'
					+'</td>'
          			//+'<td style="text-align: center">&nbsp;</td>'
				+'</tr>';
          	//当前行之后插入一行
          	currentRow.after(str);
      	}
  	});
 	var tb2 = document.getElementById("hire_affirmor_list");
 	var rowCount = tb2.rows.length;
 	for(var m=1;m<rowCount;m++){
		tb2.rows[m].cells[0].innerHTML = m;
	}
}
function delRowAffirmor(){
	//存在一种情况无法解决，同时添加两人，再删除第一人，则再添加新决裁者为第二位出现错位现象
	var tb2 = document.getElementById("hire_affirmor_list");
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
		var empid=obj.value.replace(/[ ]/g,"");
		var empIdStr=obj.id;
		var personIdStr="personId"+empIdStr.substring(7);
		var empNameStr="empName"+empIdStr.substring(7);
		
		if(empid == ''){
			obj.value=" ";
			document.getElementById("onck").href=encodeURI(encodeURI("/ar/attendanceMintenance/viewAddAffirmList?isEmployeement=1&firstFlag=1&limit=super&pageNum=1"
					+'&seach_KEY='+empid
					+'&empidStr='+empIdStr
					+'&personidStr='+personIdStr  
					));
			document.getElementById("onck").click();
		}else{
   		$.ajax({
			type: 'POST',
			url: encodeURI('/sys/affirm/getPersonCntByEmpid?EMPID='+empid ),
			dataType:"json",
			cache: false,
			success: function(jsonObject){
						if (jsonObject.perCnt!=1){
							alertMsg.error('<spring:message code="alert.message.sys.affirm.searchNoPerson"/>');
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
function submitAffirmPreEss0252(flag){
	$("#AFFIRM_FLAG_HIRE_APPLY").val(flag);
  	var $from = $("#addHireAffirmInfo");
	$("#AFFIRM_CONTENT").val(document.getElementById("AFFIRM_CONTENT_HIRE").value);
  	$from.submit();
  	document.all.btn_submitEss0252.disabled = true;
  	document.all.btn_rejectEss0252.disabled = true;
}

function validateHireAffirmCallback(form,callback) {	
	var $form = $(form);	
	if (!$form.valid()) {
		return false;
	}
	var applyNo = document.getElementById("APPLY_NO").value;
	var essAffirmNo  = document.getElementById("ESS_AFFIRM_NO").value;
    var person_id  = document.getElementById("PERSON_ID").value;
    var affirmFlag = document.getElementById("AFFIRM_FLAG_HIRE_APPLY").value;
    
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
				data:$form.serialize(),
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

function addRowByIDEss0252_Check(ESS_AFFIRM_NO){
	$.pdialog.open("/ess/affirmLeaveApply/viewLeaveApplyRemarkInfo?PAGE_FLAG=LEAVE_APPLY&seach_APPLY_NO=${APPLY_NO }&seach_ESS_AFFIRM_NO=" + ESS_AFFIRM_NO, "addRowByIDEss0252_Check", "添加Check人", {width:600,height:350,mask:true});
}
//-->
</script>
<div class="pageContent">
	<form id="addHireAffirmInfo" method="post" action="/ess/affirmApply/affirmReqHire" class="pageForm required-validate" onsubmit="return validateHireAffirmCallback(this,navTabAjaxDone);">

		<div class="pageContent">
			<div class="pageFormContent">
				<table class="user_table" width="100%">
					<tr>
					    <input type="hidden" name="REQ_ID" value="${essAffirmReq.REQ_ID}"/>
						<input type="hidden" name="APPLY_NO" id="APPLY_NO" value="${essAffirmReq.REQ_ID}"/>
						<input type="hidden" name="ESS_AFFIRM_NO" value="${essAffirmNo}"/>
						<input type="hidden" name="AFFIRMOR_ID" value="${PERSON_ID}"/>
						<input type="hidden" name="AFFIRM_FLAG" id="AFFIRM_FLAG_HIRE_APPLY" value="" />
						<input type="hidden" name="AFFIRM_CONTENT" id="AFFIRM_CONTENT" value="" />
						<td class="td_title" style="text-align: right;width:10%;">申请人</td>
						<td style="text-align: left;width:20%;">[${essAffirmReq.EMPID}]${essAffirmReq.LOCAL_NAME}</td>
						<td class="td_title" style="text-align: center";width:10%;">申请日期</td>
						<td style="text-align: left;width:60%;">${essAffirmReq.REQ_DATE}</td>
					</tr>
				</table>
			</div>
		</div>

<div class="pageContent">
	<table class="table" width="100%" layoutH="340" nowrapTD="false">
		<thead>
			<tr>
		        <th width="10%">中文姓名</th>
		        <th width="10%">英文姓名</th>
		        <th width="10%">身份证号码</th>
		        <th width="8%">入社日期</th>
		        <th width="8%">生日</th>
		        <th width="5%">性别</th>
		        <th width="8%">福利地区</th>
		        <th width="14%">人员类型</th>
		        <th width="21%">部门</th>
		        <th width="6">基本工资</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${infoApplyHire}" var="item" varStatus="i">			
			<tr>
			    <input type="hidden" name="hr0504Person" value="${item.PERSON_ID}"/>
				<td class='td_center'>
				    <a rel="leaveApplyAffirm" href="/hrm/empinfo/viewTempEmpReqDetail?PERSON_ID=${item.PERSON_ID}&APPLY_NO=${item.REQ_ID}" title="申请详情"
					          target="navTab" rel="viewTempEmpBatchAffirmInfo">${item.LOCAL_NAME}</a></td>
				<td class='td_center'>${item.CHINESE_PINYIN}</td>
				<td class='td_center'>${item.IDCARD_NO}</td>
				<td class='td_center'>${item.JOIN_COMPANY_DATE}</td>
				<td class='td_center'>${item.DOB}</td>
				<td class='td_center'>${item.SEXCODE}</td>
				<td class='td_center'>${item.INSRAREA_NM}</td>
				<td class='td_center'>${item.EMP_TYPE_NM}</td>
				<td>${item.DEPT_NAME}</td>
				<td class='td_right'>${item.BASE_PAY}</td>
			</tr>			
			</c:forEach>			
		</tbody>
	</table>
	<c:set value="/ess/affirmApply/viewHireAffirmDtlList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>

<div class="pageContent">
<div class="pageFormContent">
	<table class="table" width="100%">
		<tbody>
		<tr>
		    <td width="20%" style="text-align:center">
		             附件
		    </td>
		    <td width="80%" class='td_type'>
			<c:forEach items="${fileList}" var="file" varStatus="i">
			    <a href="/ess/infoApplyLeave/downloadFile?fileName=${file.FILE_URL }&file=${file.FILE_NAME}" >${file.FILE_NAME}</a>
			    &nbsp;&nbsp;&nbsp;
			</c:forEach>
		    </td>
		</tr>			
		</tbody>
	</table>
</div>
</div>

<div class="pageContent">
			<table class="user_table" width="100%">
				<tr>
					<td class="td_title" style="text-align:right;width:7%;"><!-- 决裁线 -->
						决裁线
					</td>
					<td colspan="7">
					    <a id="onck" name="onck"  href="" lookupGroup="person"></a>
						<table width="100%" border="0" cellpadding="0" cellspacing="0" id="hire_affirmor_list">
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
								<!-- td class="td_title" style="text-align: center;width:6%;">
									check(+/-)
								</td> -->
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
												<input type="text" id="AFFIRM_CONTENT_HIRE" name="AFFIRM_CONTENT" title="批注" size="55" maxlength="200" 
													value="${affirmor.AFFIRM_CONTENT}"/>
												<input id="ESS_AFFIRM_NO" name="ESS_AFFIRM_NO" type="hidden" value="${affirmor.ESS_AFFIRM_NO }" />
												<input id="pro_flag" name="pro_flag" type="hidden" value="0" />
												<!-- 隐藏的一些参数 -->
												<input id="APPLY_NO" name="APPLY_NO" type="hidden" value="${infoApplyLeave.APPLY_NO}" />
												<input id="PERSON_ID" name="PERSON_ID" type="hidden" value="${PERSON_ID }" />
												<input id="AFFIRMOR_ID" name="AFFIRMOR_ID" type="hidden" value="${affirmor.AFFIRMOR_ID}" />
											<input id="affirm_count" name="affirm_count" type="hidden" value="${fn:length(affirmorList)}" />
											</c:if>
										</td>
										<td class="td_type" style="text-align: center">
											<%-- 如果是自己决裁时，且未决裁时，允许添加决裁者 --%>
											<c:if test="${affirmor.ESS_AFFIRM_NO eq essAffirmNo  && j.count<fn:length(affirmorList)}">
												<img id="${affirmor.AFFIRMOR_ID}_${j.index}" src="/resources/images/+.gif" title="添加决裁者"
													border="0" align="absmiddle" style="cursor:hand" onclick="addAffirmorRow_ess0252(this.id)"/>
											</c:if>
										</td>
										<!-- td class="td_type" style="text-align: center">
											<c:if test="${affirmor.ESS_AFFIRM_NO eq essAffirmNo}">
											<img id="${affirmor.AFFIRMOR_ID}" src="/resources/images/+.gif" title="添加Check"
												border="0" align="absmiddle" style="cursor:hand" onclick="addCheck(${affirmor.ESS_AFFIRM_NO},${affirmor.AFFIRMOR_ID})"/>
											</c:if>
										</td> -->
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
								<td class="td_title" style="text-align:center;width:46%;"><!-- Requests -->
									Requests
								</td>
								<td class="td_title" style="text-align:center;width:47%;"><!-- Reviewed -->
									Checked
								</td>
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
				</tr>
			</table>
		<c:if test="${essAffirmNo ne ''}">
			<div class="formBar">
				<ul>
					<li>
						<div class="button">
							<div class="buttonContent"><!-- 通过 -->
								<button name="btn_submitEss0252" type="button" onclick="submitAffirmPreEss0252(1)">
									通过
								</button>
							</div>
						</div>
					</li>
					<li>
						<div class="button">
							<div class="buttonContent"><!-- 否决 -->
								<button name="btn_rejectEss0252" type="button" onclick="submitAffirmPreEss0252(2)">
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
</div>