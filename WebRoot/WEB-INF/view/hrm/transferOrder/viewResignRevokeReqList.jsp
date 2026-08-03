<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
//离职保存
function confirmResignRevoke(type) {	
	$("#hr0206RevokeResignType").val(type);
	$form = $("#ComfirmResignRevokeForm");	
	$form.submit();
}
function validateCallbackConfirmResignRevoke(form,callback) {	
	var $form = $("#ComfirmResignRevokeForm");	
	if (!$form.valid()) {
		return false;
	}
	var cnt = parseInt('${fn:length(revokeResignPersonIdList)}');
	if(cnt==0){
		alertMsg.error('没有要申请的内容，请确认！');
		return false;
	}
	var state = $("#hr0206RevokeResignState").attr("value");
	var affirmFlag = $("#hr0206RevokeResignAffirmFlag").attr("value");
	var validFlag = true ;
	if(affirmFlag == 1){	
		$form.find("input[name='AFFIRMOR_ID']").each(function(index, inputObj){	      
		      var affirmorId = $(inputObj).val() ;
			  if(affirmorId == ''){
				  alertMsg.error('审批线未设置！');
				  validFlag=false;
			  }else{
				  validFlag=true;
			  }
		});
	}
	if(validFlag){		
		//确定要提交吗？
		if (confirm ('<spring:message code="hr.viewEvaluate.title.COMMIT_CONFIRM"/>')){				
		  	$.ajax({
				type: form.method || 'POST',
				url:$form.attr("action"),
				data:$form.serializeArray(),
				dataType:"json",
				cache: false,
				success: function(result) {
					if (result.statusCode == 200) {
						alert("撤销离职发令申请成功！");
						$.pdialog.closeCurrent();
						navTabSearch("searchResign");
					}else{
						alert("撤销离职发令申请失败！");
					}
				},
				error: DWZ.ajaxError
			});				
			return false;
		}
	}	
	return false ;
}
function uploadifySuccess_Lot_hr0206(file, data, response){
	  //获取后台返回到前台的文件名，添加到隐藏域,多文件用";"号隔开
	  var files = $("#fileNmae_hr0206").html();
	  var fileUrl = $("#fileUrl_hr0206").val();
	  var fileName = $("#fileName_hr0206").val();
	  var fileResult = data.split(";");
	  //第一个文件
	  if(files==""){
	    files = fileResult[0];
	    fileName = fileResult[0];
	    fileUrl = fileResult[1];
	  }else{
	    files+=";<br/>"+fileResult[0];
	    fileName+=";"+fileResult[0];
	    fileUrl+=";"+fileResult[1];
	  }
	  $("#fileNmae_hr0206").html(files);
	  $("#fileUrl_hr0206").val(fileUrl);
	  $("#fileName_hr0206").val(fileName);
	}
//添加决裁者
function addRowByIDhr0206(currentRowID){
	var count = parseInt($("#affirmCount").val());
    var htm  ='<tr id="rowIdhr0206'+ count +'"><td class="td_type" style="text-align: center" width="30%"><span name="rowIndex"></span></td>';
		htm +='<td class="td_type" style="text-align: center" width="40%">';
		htm +='<input id="AFFIRMOR_IDhr0206' + count + '" name="AFFIRMOR_ID" value="" type="hidden"/>';
		htm +='<input id="EMPINFOhr0206' + count + '" name="empid" type="text" class="required" lookupGroup="person" onkeydown="submitKeyClick_affirmor(this,' + count + ',event)" class="required"/>';
		//htm +='<a class="btnLook" href="/ar/attendanceSettings/viewKeeperList?pageNum=1" lookupGroup="person">';
		htm +='</td>';
		htm +='<td class="td_type" style="text-align: center" width="30%">';
		htm +='<img src="/resources/images/+.gif" title="添加"';	
		htm +='border="0" align="absmiddle" style="cursor:hand" onclick="addRowByIDhr0206(' + count + ')"/>&nbsp;&nbsp;&nbsp;';
		htm +='<img src="/resources/images/-.gif" title="删除"';	
		htm +='border="0" align="absmiddle" style="cursor:hand" onclick="javaScript:document.all.addAffirm_listhr0206.deleteRow(event.srcElement.parentElement.parentElement.rowIndex);changeAffirmLevel();"/></td></tr>';

   	//当前行之后插入一行
   	$("#rowIdhr0206" + currentRowID).after(htm);
   	changeAffirmLevel();
  	$("#affirmCount").val(++count) ;
}
function addRowByIDApplyResignRevokeFirst(){
	var count = parseInt($("#affirmCount").val());
    var htm  ='<tr id="rowIdhr0206'+ count +'"><td class="td_type" style="text-align: center" width="30%"><span name="rowIndex"></span></td>';
		htm +='<td class="td_type" style="text-align: center" width="40%">';
		htm +='<input id="AFFIRMOR_IDhr0206' + count + '" name="AFFIRMOR_ID" value="" type="hidden"/>';
		htm +='<input id="EMPINFOhr0206' + count + '" name="empid" type="text" class="required" lookupGroup="person" onkeydown="submitKeyClick_affirmor(this,' + count + ',event)" class="required"/>';
		
		htm +='</td>';
		htm +='<td class="td_type" style="text-align: center" width="30%">';
		htm +='<img src="/resources/images/+.gif" title="添加"';	
		htm +='border="0" align="absmiddle" style="cursor:hand" onclick="addRowByIDhr0206(' + count + ')"/>&nbsp;&nbsp;&nbsp;';
		htm +='<img src="/resources/images/-.gif" title="删除"';	
		htm +='border="0" align="absmiddle" style="cursor:hand" onclick="javaScript:document.all.addAffirm_listhr0206.deleteRow(event.srcElement.parentElement.parentElement.rowIndex);changeAffirmLevel();"/></td></tr>';

	var tb2 = document.getElementById("addAffirm_listhr0206");


   	if(tb2.rows.length == 0){
   		$("#addAffirm_listhr0206:last tbody").html(htm);
   	} else {
   	   	//当前行之后插入一行
   	   	$("#" + tb2.rows[0].id).before(htm);
   	}
  	$("[id='EMPINFOhr0206" + count + "']").attr("alt","请输入关键字按回车检索").attr("size","30").inputAlert();
  	changeAffirmLevel();
  	$("#affirmCount").val(++count) ;
}
//修改决裁者等级
function changeAffirmLevel(){
	var tb2 = document.getElementById("addAffirm_listhr0206");
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
									+'&empId_hr0206='+empIdStr
									+'&personId_hr0206='+personIdStr  
									+'&empName_hr0206='+empNameStr 
									));
							document.getElementById("onck").click();
						}
						if(jsonObject.perCnt==1){
							$("#EMPINFOhr0206" + index).val('['+jsonObject.empId + ']-'+jsonObject.empName);
							$("#AFFIRMOR_IDhr0206" + index).val( jsonObject.personId);
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
</script>
<div class="pageContent">
<table class="user_table" width="100%" border="0" cellpadding="0" cellspacing="0">
<tr><td>
	<table class="table" width="100%" layoutH="270" targetType="dialog" >
		<thead>
			<tr >
				<th width="7%">
					<spring:message code="hr.viewPersonalInfo.title.EMPID"/>
					<!--社号-->
				</th>
				<th width="7%">
					<spring:message code="hr.viewPersonalInfo.title.LOCAL_NAME"/>
					<!--姓名-->
				</th>
				<th width="10%">
					<spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/>
					<!--部门-->
				</th>
				<th width="10%">
					人员类型
				</th>
				<th width="9%">
					职责
				</th>
				<th width="9%">
					<spring:message code="hr.viewPersonalInfo.title.DATE_LEFT"/>
					<font color="red">*</font>
					<!--离职日期-->
				</th>
				<th width="13%">
					<spring:message code="hr.viewPromote.title.RESIGN_TYPE_NAME"/>
					<font color="red">*</font>
					<!--离职类型-->
				</th>
				<th width="13%">
					<spring:message code="hr.viewPromote.title.RESIGN_REASON"/>
					<font color="red">*</font>
					<!--离职原因-->
				</th>
				<th width="6%" >
					<spring:message code="hr.viewResign.title.BLACKYN"/>
					<!--能否再入职-->
				</th>
				<th width="8%" >
					<spring:message code="hr.viewPromote.title.REMARK"/>
					<!--备注-->
				</th>
				<th width="8%" >
					<spring:message code="hr.viewResign.title.BLACKREMARK"/>
					<!--黑名单理由-->
				</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${resignRevokeReqList}" var="resignRevokeReq">
				<tr target="sid" rel="${resignRevokeReq.EMPID}">
					<td class="td_center">
						<input type="hidden" name="PERSON_ID_${resignRevokeReq.EMPID}" value="${resignRevokeReq.PERSON_ID}"/>
						<input type="hidden" name="DEPTNO_${resignRevokeReq.EMPID}" value="${resignRevokeReq.DEPTNO}"/>
						<input type="hidden" name="POSITION_NO_${resignRevokeReq.EMPID}" value="${resignRevokeReq.POSITION_NO}"/>
						<input type="hidden" name="POST_NO_${resignRevokeReq.EMPID}" value="${resignRevokeReq.POST_NO}"/>
						<input type="hidden" name="EXP_INSIDE_NO_${resignRevokeReq.EMPID}" value="${resignRevokeReq.EXP_INSIDE_NO}"/>
						<input type="hidden" name="REQTYPE" id="REQTYPE" value="" />
						${resignRevokeReq.EMPID}
					</td>					
					<td class='td_center'>
						${resignRevokeReq.LOCAL_NAME}
					</td>					
					<td>
						${resignRevokeReq.DEPT_NAME}
					</td>					
					<td>
						${resignRevokeReq.EMP_TYPE_NAME}
					</td>					
					<td class='td_center'>
						${resignRevokeReq.POSITION_NAME}
					</td>					
					<td class='td_center'>
						${resignRevokeReq.RESIGN_DATE}
					</td>						
					<td>
						${resignRevokeReq.RESIGN_TYPE_NAME}
					</td>
					<td>
						${resignRevokeReq.RESIGN_REASON_DESC}
					</td>					
					<td class='td_center'>	
						${resignRevokeReq.BLACKLIST_YN_DESC}
					</td>					
					<td>
						${resignRevokeReq.REMARK}
					</td>					
					<td  class='td_center'>
						${resignRevokeReq.BLACKLIST_REASON}
					</td>					
				</tr>
			</c:forEach>	
		</tbody>
	</table>
	<form id="pagerForm" method="post" action="/hrm/transferOrder/viewResignRevokeReqList?pageNum=1&RESIGN_NOS=${searchMap.RESIGN_NOS}">
	<div class="panelBar">
		<div class="pages">
			<span><!-- 显示 --><spring:message code="public.title.view"/></span>
				<select class="combox" name="numPerPage" onchange="dialogPageBreak({targetType:'dialog', numPerPage:this.value})">
					<option value="10"  <c:if test="${numPerPage == 10 }" >selected</c:if> >10</option>
					<option value="20"  <c:if test="${numPerPage == 20 }" >selected</c:if> >20</option>
					<option value="30"  <c:if test="${numPerPage == 30 }" >selected</c:if> >30</option>
				</select>
			<span><!-- 条 --><spring:message code="public.title.tiao"/>，<!-- 共 --><spring:message code="public.title.gong"/>
			${totalCount}<!-- 条 --><spring:message code="public.title.tiao"/></span>
		</div>
		<div class="pagination" targetType="dialog" totalCount="${totalCount}" numPerPage="${numPerPage}" currentPage="${pageNum}"></div>
	</div>
	</form>
</td></tr>
<tr><td>
	<form style="margin:0px;padding:0px;" id="ComfirmResignRevokeForm" name="ComfirmResignRevokeForm" 
	onsubmit="return validateCallbackConfirmResignRevoke(this,navTabAjaxDone);" 
	action="/hrm/transferOrder/confirmRevokeResignation" 
	method="post" 
	class="pageForm required-validate">
		<input type="hidden" id="hr0206RevokeResignState" name="hr0206RevokeResignState" value="${STATE}"/>
		<input type="hidden" id="hr0206RevokeResignType" name="hr0206RevokeResignType" value=""/>
		<input type="hidden" id="hr0206RevokeResignAffirmFlag" name="hr0206RevokeResignAffirmFlag" value="${searchMap.affirmFlag}"/>
		<c:forEach items="${revokeResignPersonIdList}" var="revokeResignPersons">
			<input type="hidden" name="hr0206RevokeResignNo" value="${revokeResignPersons.RESIGN_NO}"/>
		</c:forEach>
		<c:if test="${searchMap.affirmFlag eq 1 and (STATE eq 50 or STATE eq 80)}">
		<div>
			<table class="user_table" width="100%" border="0" cellpadding="0" cellspacing="0" layoutH="270">
				<tr>
					<td width="10%" class="td_title" style="text-align:center" >
						决裁线
					</td>
					<td width="50%" valign="top">
						<table width="100%"  border="0" cellpadding="0" 
						 style="border-top:none;" cellspacing="0" class="user_table margin_b" >
							<tr>
								<td class="td_title" style="text-align:center" width="30%">
									决裁等级
								</td>
								<td class="td_title" style="text-align:center" width="40%">
									决裁者
								</td>
								<td class="td_title" style="text-align:center" width="30%">
									是否新增
									(<img src="/resources/images/+.gif" title="添加" 
									border="0" align="absmiddle" style="cursor:hand" 
									onclick="addRowByIDApplyResignRevokeFirst()"/>)
								</td>
							</tr>
							<tr>
								<td colspan="3">
									<table width="100%" border="0" cellpadding="0" cellspacing="0" id="addAffirm_listhr0206">
										<tbody>
										<c:forEach items="${affirmorList}" var="affirmor" varStatus="j">	
											<tr id="rowIdhr0206${j.index}">
												<td class="td_type" style="text-align: center" width="30%">
													${j.count}
												</td>
												<td class="td_type" style="text-align: center" width="40%">
													[${affirmor.EMPID}]-${affirmor.LOCAL_NAME }
													<input type="hidden" name="AFFIRMOR_ID" value="${affirmor.AFFIRMOR_ID }"/>
												</td>
												<td class="td_type" style="text-align: center" width="30%">
												    <img src="/resources/images/+.gif" title="添加"
													border="0" align="absmiddle" style="cursor:hand" onclick="addRowByIDhr0206(${j.index})"/>
												</td>
											</tr>
										</c:forEach>
										</tbody>
									</table>
								</td>
							</tr>
						</table>
					    <input type="hidden" name="count" id="count" value="0">
					    <input type="hidden" name="affirmCount" id="affirmCount" value="${affirmorListCnt }">
					</td>
					<td width="10%" class="td_title" style="text-align:center" >
						附件上传
					</td>
					<td width="30%" class="td_type">
					    <input id="testFileInput_Lot" type="file" name="file" 
								uploaderOption="{
									swf:'/resources/js/uploadify/scripts/uploadify.swf',
									uploader:'/ess/infoApplyLeave/uploadBatch?PERSON_ID=${searchMap.PERSON_ID}',
									formData:{ajax:1},
									queueID:'fileQueue_Lot',
									buttonText:'请选择',
									height:25,
									width:50,
									auto:false,
									onUploadSuccess:uploadifySuccess_Lot_hr0206,
									removeTimeout:1
								}"
							/><span id="fileNmae_hr0206"></span>
						  <div id="fileQueue_Lot" class="fileQueue"></div>
						  <input type="hidden" id="fileUrl_hr0206" name="fileUrl" value=""/>
						  <input type="hidden" id="fileName_hr0206" name="fileName" value=""/>
							<div class="buttonActive">
								<div class="buttonContent"><!--保存-->
									<button type="button" onclick="$('#testFileInput_Lot').uploadify('upload', '*');return false;">
										上传
									</button>
								</div>
							</div>
							<div class="buttonActive">
								<div class="buttonContent"><!--提交-->
									<button type="button" onclick="$('#testFileInput_Lot').uploadify('cancel', '*');return false;">
										取消
									</button>
								</div>
							</div>
					</td>
				</tr>				  
			</table>
		</div>
		</c:if>
	</form>
</td></tr>
</table>
<div class="formBar">
	<ul>
		<li>
			<div class="buttonActive">
				<div class="buttonContent">					
					<button type="button" onclick="confirmResignRevoke('SEL')">
						按选中人员撤销
					</button>
				</div>
			</div>
		</li>
		<li>
			<div class="buttonActive">
				<div class="buttonContent">					
					<button type="button" onclick="confirmResignRevoke('REQ')">
						按申请撤销
					</button>
				</div>
			</div>
		</li>
		<li>
			<div class="button">
				<div class="buttonContent">
					<button type="button" id="btnClose" name="btnClose" class="close">
					<spring:message code="public.title.cancle"/><!--取消--></button>
				</div>
			</div>
		</li>
	</ul>
</div>
</div>