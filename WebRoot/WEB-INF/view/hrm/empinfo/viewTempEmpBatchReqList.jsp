<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">

//添加决裁者
function addRowByIDhr0517(currentRowID){
	var count = parseInt($("#affirmCount").val());
	var htm  ='<tr id="rowIdhr0517'+ count +'"><td class="td_type" style="text-align: center" width="25%"><span name="rowIndex"></span></td>';
		htm +='<td class="td_type" style="text-align: center" width="37.5%">';
		htm +='<input id="AFFIRMOR_IDhr0517' + count + '" name="AFFIRMOR_ID" value="" type="hidden"/>';
		htm +='<input id="EMPINFOhr0517' + count + '" name="empid" type="text" class="required" lookupGroup="person" onkeydown="submitKeyClick_affirmor(this,' + count + ',event)" class="required"/>';
		htm +='</td>';
		htm +='<td class="td_type" style="text-align: center" width="37.5%">';
		htm +='<img src="/resources/images/+.gif" title="添加"';	
		htm +='border="0" align="absmiddle" style="cursor:hand" onclick="addRowByIDhr0517(' + count + ')"/>&nbsp;&nbsp;&nbsp;';
		htm +='<img src="/resources/images/-.gif" title="删除"';	
		htm +='border="0" align="absmiddle" style="cursor:hand" onclick="javaScript:document.all.addAffirm_listhr0517.deleteRow(event.srcElement.parentElement.parentElement.rowIndex);changeAffirmLevel();"/></td></tr>';

 	//当前行之后插入一行
 	$("#rowIdhr0517" + currentRowID).after(htm);
 	changeAffirmLevel();
	$("#affirmCount").val(++count) ;
}
function addRowByID0517First(){
	var count = parseInt($("#affirmCount").val());
	var htm  ='<tr id="rowIdhr0517'+ count +'"><td class="td_type" style="text-align: center" width="25%"><span name="rowIndex"></span></td>';
		htm +='<td class="td_type" style="text-align: center" width="37.5%">';
		htm +='<input id="AFFIRMOR_IDhr0517' + count + '" name="AFFIRMOR_ID" value="" type="hidden"/>';
		htm +='<input id="EMPINFOhr0517' + count + '" name="empid" type="text" class="required" lookupGroup="person" onkeydown="submitKeyClick_affirmor(this,' + count + ',event)" class="required"/>';
		htm +='</td>';
		htm +='<td class="td_type" style="text-align: center" width="37.5%">';
		htm +='<img src="/resources/images/+.gif" title="添加"';	
		htm +='border="0" align="absmiddle" style="cursor:hand" onclick="addRowByIDhr0517(' + count + ')"/>&nbsp;&nbsp;&nbsp;';
		htm +='<img src="/resources/images/-.gif" title="删除"';	
		htm +='border="0" align="absmiddle" style="cursor:hand" onclick="javaScript:document.all.addAffirm_listhr0517.deleteRow(event.srcElement.parentElement.parentElement.rowIndex);changeAffirmLevel();"/></td></tr>';

	var tb2 = document.getElementById("addAffirm_listhr0517");
   	if(tb2.rows.length == 0){
   		$("#addAffirm_listhr0517:last tbody").html(htm);
   	} else {
   	   	//当前行之后插入一行
   	   	$("#" + tb2.rows[0].id).before(htm);
   	}
  	$("[id='EMPINFOhr0517" + count + "']").attr("alt","请输入关键字按回车检索").attr("size","30").inputAlert();
  	changeAffirmLevel();
  	$("#affirmCount").val(++count) ;
}
//修改决裁者等级
function changeAffirmLevel(){
	var tb2 = document.getElementById("addAffirm_listhr0517");
	var rowCount = tb2.rows.length;
	for(var m=0;m<rowCount;m++){
		tb2.rows[m].cells[0].innerHTML = m+1;
	}
}
var keyCodeInit=0;
function submitKeyClick_affirmor(obj,index,event){
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
			url: encodeURI('/sys/affirm/getPersonCntByEmpid?EMPID='+empid ),
			dataType:"json",
			cache: false,
			success: function(jsonObject){
						if (jsonObject.perCnt!=1){
							alertMsg.error('<spring:message code="alert.message.sys.affirm.searchNoPerson"/>');
						}
						if(jsonObject.perCnt==1){
							$("#EMPINFOhr0517" + index).val('['+jsonObject.empId + ']-'+jsonObject.empName);
							$("#AFFIRMOR_IDhr0517" + index).val( jsonObject.personId);
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

	function saveReq(flag){
		$("#viewTempEmpBatchConf").submit();
	}

	function upload_tempEmp_f(file, data, response){
		  //获取后台返回到前台的文件名，添加到隐藏域,多文件用";"号隔开
		  var hire_files = $("#hire_fileNmae").html();
		  var hire_fileUrl = $("#hire_fileUrl").val();
		  var hire_fileName = $("#hire_fileName").val();
		  var hire_fileResult = data.split(";");
		  //第一个文件
		  if(hire_files=="" || hire_files==null){
			  hire_files = hire_fileResult[0];
			  hire_fileName = hire_fileResult[0];
			  hire_fileUrl = hire_fileResult[1];
		  }else{
			  hire_files+=";"+hire_fileResult[0];
			  hire_fileName+=";"+hire_fileResult[0];
			  hire_fileUrl+=";"+hire_fileResult[1];
		  }
		  $("#hire_fileNmae").html(hire_files);
		  $("#hire_fileUrl").val(hire_fileUrl);
		  $("#hire_fileName").val(hire_fileName);
	}
</script>

<div class="pageContent">
	<table class="table" width="150%" layoutH="300" targetType="dialog">
		<thead>
			<tr>
		        <th width="10%">中文姓名</th>
		        <th width="10%">英文姓名</th>
		        <th width="10%">身份证号码</th>
		        <th width="10%">入社日期</th>
		        <th width="10%">生日</th>
		        <th width="5%">性别</th>
		        <th width="10%">福利地区</th>
		        <th width="14%">人员类型</th>
		        <th width="15%">部门</th>
		        <th width="6">基本工资</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${itemList}" var="item" varStatus="i">			
			<tr>
			    <input type="hidden" name="hr0517Person" value="${item.PERSON_ID}"/>
				<input type="hidden" name="hr0517DeptNo" value="${item.DEPTNO}"/>
				<td class='td_center'>${item.LOCAL_NAME}</td>
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
		
	<form id="pagerForm" method="post" action="/hrm/empinfo/viewTempEmpBatchReqList?pageNum=1&navTabId=hr0517">
	<div class="panelBar">
		<div class="pages">
			<span><spring:message code="public.title.view"/><!--显示--></span>
				<select class="combox" name="numPerPage" onchange="dialogPageBreak({targetType:'dialog', numPerPage:this.value})">
					<option value="10"  <c:if test="${numPerPage == 10 }" >selected</c:if> >10</option>
					<option value="20"  <c:if test="${numPerPage == 20 }" >selected</c:if> >20</option>
					<option value="30"  <c:if test="${numPerPage == 30 }" >selected</c:if> >30</option>
				</select>
			<span><spring:message code="public.title.tiao"/><!--条-->，<spring:message code="public.title.gong"/><!--共-->${totalCount}<spring:message code="public.title.tiao"/><!--条--></span>
		</div>
		<div class="pagination" targetType="dialog" totalCount="${totalCount}" numPerPage="${numPerPage}" currentPage="${pageNum}"></div>
	</div>
	</form>
	
	<form id="viewTempEmpBatchConf" method="post" action="/hrm/empinfo/confirmTempEmpBatch" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone)">
	<div class="pageFormContent">
		<input type="hidden" name="affirmCount" id="affirmCount" value="${fn:length(affirmorList)}">
		<input type="hidden" name="BATCH_NOS" id="BATCH_NOS" value="${BATCH_NOS}">
		<table>
		    <tr valign="top">
		    <td width="50%">
		    <table width="100%"  border="0" cellpadding="0" cellspacing="0" class="user_table margin_b">
			<tr>
				<td width="20%" class="td_title" style="text-align:center">
					附件上传
				</td>
				<td width="80%" class="td_type">
				    <input id="reqtemp_file" type="file" name="file" 
							uploaderOption="{
								swf:'/resources/js/uploadify/scripts/uploadify.swf',
								uploader:'/ess/infoApplyLeave/uploadBatch?PERSON_ID=${PERSON_ID}',
								formData:{ajax:1},
								queueID:'fileQueue_Hire',
								buttonText:'请选择',
								height:25,
								width:50,
								auto:false,
								onUploadSuccess:upload_tempEmp_f,
								removeTimeout:1
							}"
						/><span id="hire_fileNmae"></span>
					  <div id="fileQueue_Hire" class="fileQueue" style="height:30px"></div>
					  <input type="hidden" id="hire_fileUrl" name="hire_fileUrl" value=""/>
					  <input type="hidden" id="hire_fileName" name="hire_fileName" value=""/>
						<div class="buttonActive">
							<div class="buttonContent"><!--保存-->
								<button type="button" onclick="$('#reqtemp_file').uploadify('upload', '*');return false;">
									上传
								</button>
							</div>
						</div>
						<div class="buttonActive">
							<div class="buttonContent"><!--提交-->
								<button type="button" onclick="$('#reqtemp_file').uploadify('cancel', '*');return false;">
									取消
								</button>
							</div>
						</div>
				</td>
			</tr>
		    </table>
		    </td>
		    
		    <td width="50%">
			<table width="100%"  border="0" cellpadding="0" cellspacing="0" class="user_table margin_b">
			<tr>
				<td class="td_title" style="text-align: center" width="20%" rowspan="2">决裁线</td>
				<td class="td_title" style="text-align: center" width="20%">决裁等级</td>
				<td class="td_title" style="text-align: center" width="40%">决裁者</td>
				<td class="td_title" style="text-align: center" width="20%">是否新增(<img src="/resources/images/+.gif" title="添加" border="0" align="absmiddle" style="cursor:hand" onclick="addRowByID0517First()"/>)</td>
			</tr>
			<tr>
				<td colspan="3">
					<table width="100%" border="0" cellpadding="0" cellspacing="0" id="addAffirm_listhr0517">
						<tbody>
							<c:forEach items="${affirmorList}" var="affirmor" varStatus="j">
								<tr id="rowIdhr0517${j.index}">
									<td class="td_type" style="text-align: center" width="25%">${j.count}</td>
									<td class="td_type" style="text-align: center" width="50%">
										[${affirmor.EMPID}]-${affirmor.LOCAL_NAME }
										<input type="hidden" name="AFFIRMOR_ID" value="${affirmor.AFFIRMOR_ID }" />
									</td>
									<td class="td_type" style="text-align: center" width="25%">
									 <!-- c:if test="${fn:length(affirmorList) gt j.count}"> -->
										<img src="/resources/images/+.gif" title="添加" border="0"
											align="absmiddle" style="cursor: hand" onclick="addRowByIDhr0517(${j.index})" />
									 <!-- /c:if> -->
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</td>
			</tr>
		    </table>
		    </td>
		    </tr>
		</table>
	</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="saveReq(1);"><spring:message code="public.title.submit"/><!-- 提交 --></button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close"><spring:message code="public.title.cancle"/><!-- 取消 --></button></div></div></li>
			</ul>
		</div>
	</form>
	
</div>