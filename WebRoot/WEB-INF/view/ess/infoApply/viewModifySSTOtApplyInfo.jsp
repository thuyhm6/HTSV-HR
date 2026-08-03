<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
//初始
$(document).ready(function(){
	//保存
	$("#viewModifySSTOtApplyInfo_save",$.pdialog.getCurrent()).click(function(){
		var otLength = $("#OT_LENGTH",$.pdialog.getCurrent()).val();
		if(otLength == 0){
			alertMsg.info("加班时长必须大于0");
		}
		if(otLength > $("#OT_LENGTH_OLD",$.pdialog.getCurrent()).val()){
			alertMsg.info("事后申请时长不能大于事前申请时长");
		}
		if($("#APPLY_TYPE_CODE",$.pdialog.getCurrent()).val() == '141471'){
			if(otLength != '4' && otLength != '8'){
				alertMsg.info("调休申请时长必须以4小时或8小时为单位");
				return false;
			}
		}
		var $form = $("#viewModifySSTOtApplyInfoForm",$.pdialog.getCurrent());
		alertMsg.confirm("确定要提交吗？",
		  	{okCall:function(){
				$.ajax({
					type:'POST',
					url:"/ess/infoApply/modifySSTOvertimeApply",
					data:$form.serializeArray(),
					dataType:"json",
					cache: false,
					success: dialogAjaxDoneWithForm,
					error: DWZ.ajaxError
				});	
		}});
		return false;
	});
});

//获取加班时间长度
function getOtLengthSST(){
	$.ajaxSettings.global = false;
	$.ajax({
		type: 'POST',
		url: '/hrm/recruitManage/doSql',
		data:{sql:"select GET_OT_LENGTH_SST('" + $("#APPLY_DATE",$.pdialog.getCurrent()).val() + "','" + $("#OT_FROM_TIME",$.pdialog.getCurrent()).val() + "','" + $("#OT_TO_TIME",$.pdialog.getCurrent()).val() + "') OT_LENGTH from dual"},
		dataType:"json",
		cache: false,
		success: function(data){
			$("#otApplyLength",$.pdialog.getCurrent()).html(data.result[0].OT_LENGTH + "小时");
			$("#OT_LENGTH",$.pdialog.getCurrent()).val(data.result[0].OT_LENGTH);
		},
		error: DWZ.ajaxError
	});
	$.ajaxSettings.global = true;
}
//添加决裁者
function addRowByIDLTwoModify(currentRowID){
	var count = parseInt($("#affirmorListCntModify").val());
    var htm  ='<tr id="rowIdApplyLot'+ count +'"><td class="td_type" style="text-align: center"><span name="rowIndex"></span></td>';
         htm +='<td class="td_type" style="text-align: center">';
	    htm +='<input type="radio" id="approvType' + count + '" name="approvType' + count + '" value="1" checked="checked" />审批 ';
	    htm +='<input type="radio" id="approvType' + count + '" name="approvType' + count + '" value="2" />协议';
	    htm +='<input type="radio" id="approvType' + count + '" name="approvType' + count + '" value="3" />通报';
        htm +='<input type="hidden" name="approvTypeIndex" value="' + count + '" />';
	    htm +='</td>';
		htm +='<td class="td_type" style="text-align: center">';
		htm +='<input id="dwz.person.LotpersonId'+count+'" name="AFFIRMOR_ID" value="" type="hidden" lookupGroup="person"/>';
		htm +='<input id="dwz.person.LotempName'+count+'" name="empid" value="" type="text" lookupGroup="person"  lookupGroup="person" onkeydown="submitKeyClick_affirmorPModify(this,' + count + ',event)" class="required"/>';
		htm +='</td>';
		htm +='<td class="td_type" style="text-align: center">';
		htm +='<input id="dwz.person.InfoLotempName' + count + '"  type="text"  size="25" disabled="disabled"/>';
		htm +='</td>';
		htm +='<td class="td_type" style="text-align: center">';
		htm +='<img src="/resources/images/+.gif" title="添加"';	
		htm +='border="0" align="absmiddle" style="cursor:hand" onclick="addRowByIDLTwoModify(' + count + ')"/>&nbsp;&nbsp;&nbsp;';
		htm +='<img src="/resources/images/-.gif" title="删除"';	
		htm +='border="0" align="absmiddle" style="cursor:hand" onclick="javaScript:document.all.addApplyLOTAffirm_list_modify.deleteRow(event.srcElement.parentElement.parentElement.rowIndex);changeApplyOtLevelModify();"/></td></tr>';

   	//当前行之后插入一行
   	$("#rowIdApplyLot" + currentRowID).after(htm);
  	$("[id='dwz.person.LotempName" + count + "']").attr("alt","请输入关键字按回车检索").attr("size","25").inputAlert();
   	changeApplyOtLevelModify();
  	$("#affirmorListCntModify").val(++count) ;
}
//添加第一行审判者
function addRowByIDApplyPOTFirstModify(){
	var count = parseInt($("#affirmorListCntModify").val());
    var htm  ='<tr id="rowIdApplyLot'+ count +'"><td class="td_type" style="text-align: center" width="5%"><span name="rowIndex"></span></td>';
    htm +='<td class="td_type" style="text-align: center" width="20%">';
    htm +='<input type="radio" id="approvType' + count + '" name="approvType' + count + '" value="1" checked="checked" />审批 ';
    htm +='<input type="radio" id="approvType' + count + '" name="approvType' + count + '" value="2" />协议'; 
    htm +='<input type="radio" id="approvType' + count + '" name="approvType' + count + '" value="3" />通报';
    htm +='<input type="hidden" name="approvTypeIndex" value="' + count + '" />';
    htm +='</td>';
	htm +='<td class="td_type" style="text-align: center" width="30%">';
	htm +='<input id="dwz.person.LotpersonId'+count+'" name="AFFIRMOR_ID" value="" type="hidden" lookupGroup="person"/>';
	htm +='<input id="dwz.person.LotempName'+count+'" name="empid" value="" type="text" lookupGroup="person" onkeydown="submitKeyClick_affirmorPModify(this,' + count + ',event)" class="required"/>';
	htm +='</td>';
	htm +='<td class="td_type" style="text-align: center" width="30%">';
	htm +='<input id="dwz.person.InfoLotempName' + count + '"  type="text"  size="25" disabled="disabled"/>';
	htm +='</td>';
	htm +='<td class="td_type" style="text-align: center" width="15%">';
	htm +='<img src="/resources/images/+.gif" title="添加"';	
	htm +='border="0" align="absmiddle" style="cursor:hand" onclick="addRowByIDLTwoModify(' + count + ')"/>&nbsp;&nbsp;&nbsp;';
	htm +='<img src="/resources/images/-.gif" title="删除"';	
	htm +='border="0" align="absmiddle" style="cursor:hand" onclick="javaScript:document.all.addApplyLOTAffirm_list_modify.deleteRow(event.srcElement.parentElement.parentElement.rowIndex);changeApplyOtLevelModify();"/></td></tr>';

	var tb2 = document.getElementById("addApplyLOTAffirm_list_modify");

   	if(tb2.rows.length == 0){
   		$("#addApplyLOTAffirm_list_modify:last tbody").html(htm);
   	} else {
   	   	//当前行之后插入一行
   	   	$("#" + tb2.rows[0].id).before(htm);
   	}
  	$("[id='dwz.person.LotempName" + count + "']").attr("alt","请输入关键字按回车检索").attr("size","25").inputAlert();
   	changeApplyOtLevelModify();
  	$("#affirmorListCntModify").val(++count) ;
}
//修改决裁者等级
function changeApplyOtLevelModify(){
	var tb2 = document.getElementById("addApplyLOTAffirm_list_modify");
	var rowCount = tb2.rows.length;
	for(var m=0;m<rowCount;m++){
		tb2.rows[m].cells[0].innerHTML = m+1;
	}
}

var keyCodeInit=0;
function submitKeyClick_affirmorPModify(obj,index,event){
	var localName = '';
	var idcardNo = '';
	var navTabId = '';
	 
	var  currentEmpid = $("#empId_apply").val();

	if(currentEmpid==null||currentEmpid==""){
		currentEmpid=$("#empId_apply_b").val();
		}
 	
 	var e= event ? event : window.event; 
 	var keyCode = e.which ? e.which : e.keyCode;
   	if(keyCode==13){
   		keyCodeInit=keyCode;
		var empid=obj.value.replace(/[ ]/g,"");
		var empIdStr=obj.id;
		var empIdStr=obj.id.substring(11);
		var personIdStr="LotpersonId"+empIdStr.substring(10);
		if(empid == ''){
			obj.value=" ";
			document.getElementById("onckModify").href=encodeURI(encodeURI("/ar/attendanceMintenance/viewAddAffirmList?isEmployeement=1&firstFlag=1&limit=super&pageNum=1"
					+'&seach_KEY='+empid
					+'&empidStr='+empIdStr
					+'&personidStr='+personIdStr  
					));
			document.getElementById("onckModify").click();
		}else{
	   		$.ajax({
				type: 'POST',
				url: encodeURI('/sys/affirm/getPersonCntByEmpid?EMPID='+empid ),
				dataType:"json",
				cache: false,
				success: function(jsonObject){
					if(jsonObject.perCnt != 1 ){
						document.getElementById("onckModify").href=encodeURI(encodeURI("/ar/attendanceMintenance/viewAddAffirmList?isEmployeement=1&limit=super&pageNum=1"
								+'&seach_KEY='+empid
								+'&empidStr='+empIdStr
								+'&personidStr='+personIdStr
								));
						document.getElementById("onckModify").click();
					}
					if(jsonObject.perCnt==1){
					  	$("[id='dwz.person.LotempName" + index + "']").val('['+jsonObject.empId + ']-'+jsonObject.empName);
					  	$("[id='dwz.person.LotpersonId" + index + "']").val( jsonObject.personId);
					  	$("[id='dwz.person.InfoLotempName" + index + "']").val(  jsonObject.empName + "/" + jsonObject.POST_GRADE_NAME + "/" + jsonObject.deptName);
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
</script>
<div>
<%@ include file="/WEB-INF/view/hrm/empinfo/viewPersonalInfoHead_ess.jsp"%>
</div>
<br></br>
<div class="pageContent">
	<div>
		<form id="viewModifySSTOtApplyInfoForm" method="post" action="/ess/infoApply/addPOvertimeApply" class="required-validate">
			<div>
				<table class="user_table" width="100%"  border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="20%" class="td_title" style="text-align:right">日期</td>
						    <td width="30%" class="td_type">
 							<input type="text" id="APPLY_DATE" name="APPLY_DATE" value="${otApplyInfo.APPLY_OT_DATE}" readonly/>					    
						</td>
						<td width="20%" class="td_title" style="text-align:right"><!--加班类型-->
							<spring:message code="ess.viewApply.title.overtimeApplyType"/>
						</td>
						<td width="30%" class="td_type">
							 <input type="hidden" id="APPLY_AFFIRM_FLAG"  name="APPLY_AFFIRM_FLAG" value="14014311"/>
							 <input type="hidden" id="APPLY_TYPE_NO"  name="APPLY_TYPE_NO" value="31"/>
							 <input type="hidden" id="APPLY_FLAG"  name="APPLY_FLAG" value="1"/>
							 <input type="hidden" id="APPLY_NO_SEQ"  name="APPLY_NO_SEQ" value="${otApplyInfo.APPLY_NO }"/>
							 <input type="hidden" id="APPLY_TYPE_CODE"  name="APPLY_TYPE_CODE" value="${otApplyInfo.OT_TYPE_CODE }"/>
							 <ait:SelectSyCodeByCpnyID name="APPLY_TYPE_CODE_NEW" parentNo="31" selected="${otApplyInfo.OT_TYPE_CODE }" disabled="true"/>
						</td>
					</tr>
					<tr>
					    <td width="20%" class="td_title" style="text-align:right">
					    	时间
					    </td>
					    <td width="80%" class="td_type" colspan="3">
							<ait:time name="OT_FROM_TIME" spacing="30" selected="${otApplyInfo.FROM_TIME }" onChange="getOtLengthSST();"/>
							~
							<ait:time name="OT_TO_TIME" spacing="30" selected="${otApplyInfo.TO_TIME }" onChange="getOtLengthSST();"/>
						</td>
					</tr>
					<tr>		
 						<td width="20%" class="td_title" style="text-align:right">
							加班时间
						</td>
						<td width="80%" class="td_type"  colspan="3">
							<div id="otApplyLength">${otApplyInfo.OT_LENGTH }小时</div>
				            <input type="hidden" id="OT_LENGTH" name="OT_LENGTH" value="${otApplyInfo.OT_LENGTH }"/>
				            <input type="hidden" id="OT_LENGTH_OLD" name="OT_LENGTH_OLD" value="${otApplyInfo.OT_LENGTH }"/>
						</td>
					</tr>
					<tr>
					    <td width="20%" class="td_title" style="text-align:right"><!--其他原因-->
					    	原因
					    </td>
					    <td width="80%" class="td_type" colspan="3">
					    	<textarea style="width:500px;height:100px" id="APPLY_REMARK" name="APPLY_REMARK">${otApplyInfo.APPLY_REMARK }</textarea>
					    </td>
					</tr>		
					<tr>
						<td colspan="5">
							<table class="user_table" width="100%">	
								<tr>
								    <td class="td_title"  style="text-align:center;" width="5%">序号</td>
									<td class="td_title"  style="text-align:center;" width="20%">审批区分</td>
									<td class="td_title" style="text-align:center;" width="30%">审批人</td>
									<td class="td_title" style="text-align:center;" width="30%">审批人信息</td>
									<td class="td_title" style="text-align:center;" width="15%">是否新增(<img src="/resources/images/+.gif" title="添加" border="0" align="absmiddle" style="cursor:hand" onclick="addRowByIDApplyPOTFirstModify()"/>)</td>
								</tr>
								<tr>
									<td colspan="5">
										<table width="100%" border="0" cellpadding="0" cellspacing="0" id="addApplyLOTAffirm_list_modify">
											<tbody>
												<c:forEach items="${affirmorList}" var="item" varStatus="i">
													<tr id="rowIdApplyLot${i.index}">
														<td class="td_type" style="text-align: center" width="5%"><span name="rowIndex">${i.count}</span></td>
								    					<td class="td_type" style="text-align: center" width="20%">
														    <input type="radio" id="approvType${i.index}" name="approvType${i.index}" value="1" <c:if test="${item.AFFIRM_TYPE eq '1' }">checked="checked"</c:if>/>审批 
														    <input type="radio" id="approvType${i.index}" name="approvType${i.index}" value="2" <c:if test="${item.AFFIRM_TYPE eq '2' }">checked="checked"</c:if>/>协议 
														    <input type="radio" id="approvType${i.index}" name="approvType${i.index}" value="3" <c:if test="${item.AFFIRM_TYPE eq '3' }">checked="checked"</c:if>/>通报
													    	<input type="hidden" name="approvTypeIndex" value="${i.index}" />
													    </td>
														<td class="td_type" style="text-align: center" width="30%">
															<input id="dwz.person.LotpersonId${i.index}" name="AFFIRMOR_ID" value="${item.AFFIRMOR_ID}" type="hidden" lookupGroup="person"/>
															<input id="dwz.person.LotempName${i.index}" name="empid" value="${item.AFFIRMOR}" type="text" size="25" alt="请输入关键字按回车检索" lookupGroup="person" onkeydown="submitKeyClick_affirmorPModify(this,'${i.index}',event)" class="required"/>
														</td>
														<td class="td_type" style="text-align: center" width="30%">
															<input id="dwz.person.InfoLotempName${i.index}" type="text" value="${item.AFFIRMOR_INFO}" size="25" disabled="disabled"/>
														</td>
														<td class="td_type" style="text-align: center" width="15%">
															<img src="/resources/images/+.gif" title="添加"	border="0" align="absmiddle" style="cursor:hand" onclick="addRowByIDLTwoModify(${i.index})"/>&nbsp;&nbsp;&nbsp;
															<img src="/resources/images/-.gif" title="删除" border="0" align="absmiddle" style="cursor:hand" onclick="javaScript:document.all.addApplyLOTAffirm_list_modify.deleteRow(event.srcElement.parentElement.parentElement.rowIndex);changeApplyOtLevelModify();"/>
														</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</td>
								</tr>
							</table>
							<a id="onckModify" name="onckModify"  href="" lookupGroup="person"></a>
					 		<input type="hidden" id="affirmorListCntModify" name="affirmorListCntModify" value="${affirmorListCnt }"/>
						</td>
					</tr>
				</table>
			</div>
			<div class="formBar">
				<ul>
					<li>
						<div class="button">
							<div class="buttonContent"><!--禀告-->
								<button type="button" id="viewModifySSTOtApplyInfo_save">
									禀告
								</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
	  	</form>	
	</div>
</div>
