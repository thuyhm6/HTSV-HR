<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
//初始
$(document).ready(function(){
	//初始化
	initOtWeekendDateTx();
	//保存
	$("#viewSSTOtApplyInfoTx_save",navTab.getCurrentPanel()).click(function(){
		var otLength = $("#OT_LENGTH",navTab.getCurrentPanel()).val();
		if(otLength != '4' && otLength != '8'){
			alertMsg.info("申请时间必须以4小时或8小时为单位");
			return false;
		}
		var $form = $("#viewSSTOtApplyInfoTxForm",navTab.getCurrentPanel());
		alertMsg.confirm("确定要提交吗？",
		  	{okCall:function(){
				$.ajax({
					type:'POST',
					url:"/ess/infoApply/addSSTOvertimeApply",
					data:$form.serializeArray(),
					dataType:"json",
					cache: false,
					success: navTabAjaxDone ,
					error: DWZ.ajaxError
				});	
		}});
		return false;
	});
});
//初始化加班日期
function initOtWeekendDateTx(){
	$.ajaxSettings.global = false;
	$.ajax({
		type: 'POST',
		url: '/hrm/recruitManage/doSql',
		data:{sql:"select GET_RECENT_WEEKEND_SST('${LoginUser.adminID}','${LoginUser.cpnyId}') APPLY_DATE from dual"},
		dataType:"json",
		cache: false,
		success: function(data){
			$("#APPLY_DATE",navTab.getCurrentPanel()).val(data.result[0].APPLY_DATE);
			getDefaultOtTimeSSTTx();
		},
		error: DWZ.ajaxError
	});
	$.ajaxSettings.global = true;
}
//获取班次结束时间
function getDefaultOtTimeSSTTx(){
	$.ajaxSettings.global = false;
	var APPLY_DATE = $("#APPLY_DATE",navTab.getCurrentPanel()).val().replace(".", "-").replace(".", "-");
	$.ajax({
		type: 'POST',
		url: '/hrm/recruitManage/doSql',
		data:{sql:"select GET_AR_DATETYPE('${LoginUser.adminID}','" + APPLY_DATE + "','${LoginUser.cpnyId}') DATETYPE from dual"},
		dataType:"json",
		cache: false,
		success: function(data){
			if(data.result[0].DATETYPE == '1441'){
				$("#APPLY_TYPE_CODE_NEW",navTab.getCurrentPanel()).attr("value",141471);
				$("#APPLY_TYPE_CODE",navTab.getCurrentPanel()).attr("value",141471);
				$("#OT_FROM_TIME",navTab.getCurrentPanel()).attr("value","08:00");
				$("#OT_TO_TIME",navTab.getCurrentPanel()).attr("value","16:00");
				getOtLengthSSTTx();
			}else{
				alertMsg.info("你选择的日期类型不是周末");
				initOtWeekendDateTx();
			}
		},
		error: DWZ.ajaxError
	});
	$.ajaxSettings.global = true;
}
//获取加班时间长度
function getOtLengthSSTTx(){
	$.ajaxSettings.global = false;
	$.ajax({
		type: 'POST',
		url: '/hrm/recruitManage/doSql',
		data:{sql:"select GET_OT_LENGTH_SST('" + $("#APPLY_DATE",navTab.getCurrentPanel()).val() + "','" + $("#OT_FROM_TIME",navTab.getCurrentPanel()).val() + "','" + $("#OT_TO_TIME",navTab.getCurrentPanel()).val() + "') OT_LENGTH from dual"},
		dataType:"json",
		cache: false,
		success: function(data){

			$("#OT_LENGTH",navTab.getCurrentPanel()).val(data.result[0].OT_LENGTH);
			var length = data.result[0].OT_LENGTH/8 ;
			var lengthText = "";
			if( length > 0 ){
				lengthText += length + "天";
			}
			$("#otApplyLength",navTab.getCurrentPanel()).html(lengthText);
		},
		error: DWZ.ajaxError
	});
	$.ajaxSettings.global = true;
}
//添加决裁者
function addRowByIDLTwoTx(currentRowID){
	var count = parseInt($("#affirmorListCntTx",navTab.getCurrentPanel()).val());
    var htm  ='<tr id="rowIdApplyLotTx'+ count +'"><td class="td_type" style="text-align: center" width="5%"><span name="rowIndex"></span></td>';
        htm +='<td class="td_type" style="text-align: center" width="20%">';
	    htm +='<input type="radio" id="approvType' + count + '" name="approvType' + count + '" value="1" checked="checked" />审批 ';
	    htm +='<input type="radio" id="approvType' + count + '" name="approvType' + count + '" value="2" />协议';
	    htm +='<input type="radio" id="approvType' + count + '" name="approvType' + count + '" value="3" />通报';
        htm +='<input type="hidden" name="approvTypeIndex" value="' + count + '" />';
	    htm +='</td>';
		htm +='<td class="td_type" style="text-align: center" width="30%">';
		htm +='<input id="dwz.person.LotpersonIdTx'+count+'" name="AFFIRMOR_ID" value="" type="hidden" lookupGroup="person"/>';
		htm +='<input id="dwz.person.LotempNameTx'+count+'" name="empid" value="" type="text" lookupGroup="person" onkeydown="submitKeyClick_affirmorPTx(this,' + count + ',event)" class="required"/>';
		htm +='</td>';
		htm +='<td class="td_type" style="text-align: center" width="30%">';
		htm +='<input id="dwz.person.InfoLotempNameTx' + count + '"  type="text"  size="25" disabled="disabled"/>';
		htm +='</td>';
		htm +='<td class="td_type" style="text-align: center" width="15%">';
		htm +='<img src="/resources/images/+.gif" title="添加"';	
		htm +='border="0" align="absmiddle" style="cursor:hand" onclick="addRowByIDLTwoTx(' + count + ')"/>&nbsp;&nbsp;&nbsp;';
		htm +='<img src="/resources/images/-.gif" title="删除"';	
		htm +='border="0" align="absmiddle" style="cursor:hand" onclick="javaScript:document.all.addApplyLOTAffirm_list_Tx.deleteRow(event.srcElement.parentElement.parentElement.rowIndex);changeApplyOtLevelTx();"/></td></tr>';

   	//当前行之后插入一行
   	$("#rowIdApplyLotTx" + currentRowID).after(htm);
  	$("[id='dwz.person.LotempNameTx" + count + "']").attr("alt","请输入关键字按回车检索").attr("size","25").inputAlert();
   	changeApplyOtLevelTx();
  	$("#affirmorListCntTx").val(++count) ;
}
//添加第一行审判者
function addRowByIDApplyPOTFirstTx(){
	var count = parseInt($("#affirmorListCntTx").val());
    var htm  ='<tr id="rowIdApplyLotTx'+ count +'"><td class="td_type" style="text-align: center" width="5%"><span name="rowIndex"></span></td>';
    htm +='<td class="td_type" style="text-align: center" width="20%">';
    htm +='<input type="radio" id="approvType' + count + '" name="approvType' + count + '" value="1" checked="checked" />审批 ';
    htm +='<input type="radio" id="approvType' + count + '" name="approvType' + count + '" value="2" />协议'; 
    htm +='<input type="radio" id="approvType' + count + '" name="approvType' + count + '" value="3" />通报';
    htm +='<input type="hidden" name="approvTypeIndex" value="' + count + '" />';
    htm +='</td>';
	htm +='<td class="td_type" style="text-align: center" width="30%">';
	htm +='<input id="dwz.person.LotpersonIdTx'+count+'" name="AFFIRMOR_ID" value="" type="hidden" lookupGroup="person"/>';
	htm +='<input id="dwz.person.LotempNameTx'+count+'" name="empid" value="" type="text" lookupGroup="person" onkeydown="submitKeyClick_affirmorPTx(this,' + count + ',event)" class="required"/>';
	htm +='</td>';
	htm +='<td class="td_type" style="text-align: center" width="30%">';
	htm +='<input id="dwz.person.InfoLotempNameTx' + count + '"  type="text"  size="25" disabled="disabled"/>';
	htm +='</td>';
	htm +='<td class="td_type" style="text-align: center" width="15%">';
	htm +='<img src="/resources/images/+.gif" title="添加"';	
	htm +='border="0" align="absmiddle" style="cursor:hand" onclick="addRowByIDLTwoTx(' + count + ')"/>&nbsp;&nbsp;&nbsp;';
	htm +='<img src="/resources/images/-.gif" title="删除"';	
	htm +='border="0" align="absmiddle" style="cursor:hand" onclick="javaScript:document.all.addApplyLOTAffirm_list_Tx.deleteRow(event.srcElement.parentElement.parentElement.rowIndex);changeApplyOtLevelTx();"/></td></tr>';

	var tb2 = document.getElementById("addApplyLOTAffirm_list_Tx");

   	if(tb2.rows.length == 0){
   		$("#addApplyLOTAffirm_list_Tx:last tbody").html(htm);
   	} else {
   	   	//当前行之后插入一行
   	   	$("#" + tb2.rows[0].id).before(htm);
   	}
  	$("[id='dwz.person.LotempNameTx" + count + "']").attr("alt","请输入关键字按回车检索").attr("size","25").inputAlert();
   	changeApplyOtLevelTx();
  	$("#affirmorListCntTx").val(++count) ;
}
//修改决裁者等级
function changeApplyOtLevelTx(){
	var tb2 = document.getElementById("addApplyLOTAffirm_list_Tx");
	var rowCount = tb2.rows.length;
	for(var m=0;m<rowCount;m++){
		tb2.rows[m].cells[0].innerHTML = m+1;
	}
}

var keyCodeInit=0;
function submitKeyClick_affirmorPTx(obj,index,event){
	var localName = '';
	var idcardNo = '';
	var navTabId = '';
	
 	var e= event ? event : window.event; 
 	var keyCode = e.which ? e.which : e.keyCode;
   	if(keyCode==13){
   		keyCodeInit=keyCode;
		var empid=obj.value.replace(/[ ]/g,"");
		var empIdStr=obj.id;
		var empIdStr=obj.id.substring(11);
		var personIdStr="LotpersonIdTx"+empIdStr.substring(12);
		if(empid == ''){
			obj.value=" ";
			document.getElementById("onckTx").href=encodeURI(encodeURI("/ar/attendanceMintenance/viewAddAffirmList?isEmployeement=1&firstFlag=1&limit=super&pageNum=1"
					+'&seach_KEY='+empid
					+'&empidStr='+empIdStr
					+'&personidStr='+personIdStr  
					));
			document.getElementById("onckTx").click();
		}else{
	   		$.ajax({
				type: 'POST',
				url: encodeURI('/sys/affirm/getPersonCntByEmpid?EMPID='+empid ),
				dataType:"json",
				cache: false,
				success: function(jsonObject){
					if(jsonObject.perCnt != 1 ){
						document.getElementById("onckTx").href=encodeURI(encodeURI("/ar/attendanceMintenance/viewAddAffirmList?isEmployeement=1&limit=super&pageNum=1"
								+'&seach_KEY='+empid
								+'&empidStr='+empIdStr
								+'&personidStr='+personIdStr
								));
						document.getElementById("onckTx").click();
					}
					if(jsonObject.perCnt==1){
					  	$("[id='dwz.person.LotempNameTx" + index + "']").val('['+jsonObject.empId + ']-'+jsonObject.empName);
					  	$("[id='dwz.person.LotpersonIdTx" + index + "']").val( jsonObject.personId);
					  	$("[id='dwz.person.InfoLotempNameTx" + index + "']").val( jsonObject.empName + "/" + jsonObject.POST_GRADE_NAME + "/" + jsonObject.deptName);
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
<div class="panel"><h1>周末工作调休申请</h1>
<div>
<%@ include file="/WEB-INF/view/hrm/empinfo/viewPersonalInfoHead_ess.jsp"%>
</div>
<div class="pageContent">
	<div>
		<form id="viewSSTOtApplyInfoTxForm" method="post" action="/ess/infoApply/addPOvertimeApply" class="required-validate">
			<div>
				<table class="user_table" width="100%"  border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="20%" class="td_title" style="text-align:right">日期</td>
						    <td width="30%" class="td_type">
 							<input type="text" id="APPLY_DATE" name="APPLY_DATE" class="Wdate"  value="${APPLY_DATE}" onClick="WdatePicker({dateFmt:'yyyy.MM.dd',onpicked:getDefaultOtTimeSSTTx})"/>					    
						</td>
						<td width="20%" class="td_title" style="text-align:right"><!--加班类型-->
							<spring:message code="ess.viewApply.title.overtimeApplyType"/>
						</td>
						<td width="30%" class="td_type">
							 <input type="hidden" id="APPLY_AFFIRM_FLAG"  name="APPLY_AFFIRM_FLAG" value="14015193"/>
							 <input type="hidden" id="APPLY_TYPE_NO"  name="APPLY_TYPE_NO" value="31"/>
							 <input type="hidden" id="APPLY_FLAG"  name="APPLY_FLAG" value="0"/>
							 <input type="hidden" id="APPLY_TYPE_CODE"  name="APPLY_TYPE_CODE" value=""/>
							 <ait:SelectSyCodeByCpnyID name="APPLY_TYPE_CODE_NEW" parentNo="31" selected="" disabled="true"/>
						</td>
					</tr>
					<tr>
					    <td width="20%" class="td_title" style="text-align:right">
					    	时间
					    </td>
					    <td width="80%" class="td_type" colspan="3">
							<ait:time name="OT_FROM_TIME" spacing="30" selected="00:00" onChange="getOtLengthSSTTx();"/>
							~
							<ait:time name="OT_TO_TIME" spacing="30" selected="00:00" onChange="getOtLengthSSTTx();"/>
						</td>
					</tr>
					<tr>		
 						<td width="20%" class="td_title" style="text-align:right">
							生成个数
						</td>
						<td width="80%" class="td_type"  colspan="3">
							<div id="otApplyLength">0</div>
				            <input type="hidden" id="OT_LENGTH" name="OT_LENGTH" value="0"/>
						</td>
					</tr>
					<tr>
					    <td width="20%" class="td_title" style="text-align:right"><!--其他原因-->
					    	原因
					    </td>
					    <td width="80%" class="td_type" colspan="3">
					    	<textarea style="width:500px;height:100px" id="APPLY_REMARK" name="APPLY_REMARK"></textarea>
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
									<td class="td_title" style="text-align:center;" width="15%">是否新增(<img src="/resources/images/+.gif" title="添加" border="0" align="absmiddle" style="cursor:hand" onclick="addRowByIDApplyPOTFirstTx()"/>)</td>
								</tr>
								<tr>
									<td colspan="5">
										<table width="100%" border="0" cellpadding="0" cellspacing="0" id="addApplyLOTAffirm_list_Tx">
											<tbody>
												<c:forEach items="${affirmorList}" var="item" varStatus="i">
													<tr id="rowIdApplyLotTx${i.index}">
														<td class="td_type" style="text-align: center" width="5%"><span name="rowIndex">${i.count}</span></td>
								    					<td class="td_type" style="text-align: center" width="20%">
														    <input type="radio" id="approvType${i.index}" name="approvType${i.index}" value="1" <c:if test="${item.AFFIRM_TYPE eq '1' }">checked="checked"</c:if>/>审批 
														    <input type="radio" id="approvType${i.index}" name="approvType${i.index}" value="2" <c:if test="${item.AFFIRM_TYPE eq '2' }">checked="checked"</c:if>/>协议 
														    <input type="radio" id="approvType${i.index}" name="approvType${i.index}" value="3" <c:if test="${item.AFFIRM_TYPE eq '3' }">checked="checked"</c:if>/>通报
													    	<input type="hidden" name="approvTypeIndex" value="${i.index}" />
													    </td>
														<td class="td_type" style="text-align: center" width="30%">
															<input id="dwz.person.LotpersonIdTx${i.index}" name="AFFIRMOR_ID" value="${item.AFFIRMOR_ID}" type="hidden" lookupGroup="person"/>
															<input id="dwz.person.LotempNameTx${i.index}" name="empid" value="${item.AFFIRMOR}" type="text" size="25" alt="请输入关键字按回车检索" lookupGroup="person" onkeydown="submitKeyClick_affirmorPTx(this,'${i.index}',event)" class="required"/>
														</td>
														<td class="td_type" style="text-align: center" width="30%">
															<input id="dwz.person.InfoLotempNameTx${i.index}" type="text" value="${item.AFFIRMOR_INFO}" size="25" disabled="disabled"/>
														</td>
														<td class="td_type" style="text-align: center" width="15%">
															<img src="/resources/images/+.gif" title="添加"	border="0" align="absmiddle" style="cursor:hand" onclick="addRowByIDLTwoTx(${i.index})"/>&nbsp;&nbsp;&nbsp;<img src="/resources/images/-.gif" title="删除" border="0" align="absmiddle" style="cursor:hand" onclick="javaScript:document.all.addApplyLOTAffirm_list_Tx.deleteRow(event.srcElement.parentElement.parentElement.rowIndex);changeApplyOtLevelTx();"/>
														</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</td>
								</tr>
							</table>
							<a id="onckTx" name="onckTx"  href="" lookupGroup="person"></a>
					 		<input type="hidden" id="affirmorListCntTx" name="affirmorListCntTx" value="${affirmorListCntTx }"/>
						</td>
					</tr>
				</table>
			</div>
			<div class="formBar">
				<ul>
					<li>
						<div class="button">
							<div class="buttonContent"><!--禀告-->
								<button type="button" id="viewSSTOtApplyInfoTx_save">
									禀告
								</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
			<div style="color:red;">
				※ 调休需在3个月内使用 <br>
				※ 周末工作只能申请4小时或8小时（以4小时为单位） <br>
				※ 周末工作申请审批结束后生成的调休个数会生效，即可在考勤申请中申请调休 <br>
				※ 周末工作事后确认审批结束后会自动生成调休个数，即可在考勤申请中申请调休
			</div>
	  	</form>	
	</div>
</div>
</div>