<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
//初始
$(document).ready(function(){
	getDefaultOtTimeSST();
	//保存
	$("#viewSSTOtApplyInfo_save",navTab.getCurrentPanel()).click(function(){
		
        var OT_LENGTH = $("#OT_LENGTH",navTab.getCurrentPanel()).val();
		var OT_TOTAIL_MONTH = $("#OT_TOTAIL_MONTH",navTab.getCurrentPanel()).html();//
		var OT_APPLY_REMARK = $("#APPLY_REMARK",navTab.getCurrentPanel()).val();
		var OT_LIMIT = $("#OT_LIMIT",navTab.getCurrentPanel()).val();
		var OT_LIMIT_100 = $("#OT_LIMIT_100",navTab.getCurrentPanel()).val();
		var OT_TOTAIL = $("#OT_TOTAIL",navTab.getCurrentPanel()).html();//
		var OT_WEEKDAY_TOTAL = $("#WEEKDAY_OT_TOTAIL",navTab.getCurrentPanel()).html();
		var OT_TOTAIL_MONTH_LIMIT = $("#OT_TOTAIL_MONTH_LIMIT",navTab.getCurrentPanel()).html();//;
		var OT_TOTAIL_LIMIT = $("#OT_TOTAIL_LIMIT",navTab.getCurrentPanel()).html();//
		var APPLY_TYPE_CODE = $("#APPLY_TYPE_CODE_OT",navTab.getCurrentPanel()).val();
		var APPLY_DATE = $("#APPLY_DATE",navTab.getCurrentPanel()).val();
		APPLY_DATE = APPLY_DATE.substring(6,10) + "/" + APPLY_DATE.substring(3,5) + "/" + APPLY_DATE.substring(0,2);
        var $form = $("#viewSSTOtApplyInfoForm",navTab.getCurrentPanel());
        if (OT_APPLY_REMARK == '' || OT_APPLY_REMARK == ' ') {
        	alertMsg.info("<spring:message code='ess.infoApply.ApplyReasonNotull' />");
			return false;
        }
        var OT_FROM_TIME = $("#OT_FROM_TIME",navTab.getCurrentPanel()).val();
        OT_FROM_TIME = OT_FROM_TIME.substring(0,2);
        var OT_FROM_MINUTE = $("#OT_FROM_TIME",navTab.getCurrentPanel()).val().substring(3,5);
        
        var otDate = new Date(APPLY_DATE);
		var date = new Date();
		var dateLengh = (otDate.getTime() - date.getTime())/(1000*60*60*24)
		
		
		/* if (dateLengh > 0 && APPLY_TYPE_CODE == '32' || dateLengh > 2) {
			alertMsg.error("<spring:message code='ar.viewArOvertimeManagentFast.OtDateCanNotFutureUnlessRestDay' />"); //加班日期不能用将来，除非次日是休息日期!
			return false;
		} */

        if(OT_LENGTH == 0){
			alertMsg.info("<spring:message code='ess.infoApply.JIABAN_LONGER_THAN_0.Z' />");//加班时长必须大于0
			return false;
		}
        if("${LoginUser.postFamily}" == "14015813" && OT_LENGTH < 1 && OT_FROM_TIME != 12 && OT_FROM_TIME != 07 && OT_FROM_MINUTE != 32){
			alertMsg.info("<spring:message code='alert.message.GUANLIZHIZUISHAOJIABANYIXIAOSHI.b' />");//管理职最少加班一小时
			return false;
		}
        if(parseFloat(OT_TOTAIL_MONTH)  + parseFloat(OT_LENGTH)  > parseFloat(OT_TOTAIL_MONTH_LIMIT) && OT_LIMIT == 1){
			alertMsg.info("<spring:message code='ess.viewSSTOtApplyInfo.JIABANCHAOGUOYUESHANGXIAN.b' />");//加班时长超过月加班上限,无法申请!
			return false;
		}
        
        if(parseFloat(OT_TOTAIL_MONTH) + parseFloat(OT_LENGTH)  > 40  && OT_LIMIT_100 == 1){
			alertMsg.info("<spring:message code='ess.viewSSTOtApplyInfo.JIABANCHAOGUOYUESHANGXIAN.b' />");//加班时长超过年加班上限,无法申请!
			return false;
		}
        /**if(APPLY_TYPE_CODE == '32' && parseFloat(OT_LENGTH) > 4 ){
        	alertMsg.info("<spring:message code='ess.viewSSTOtApplyInfo.PINGRIJIABANSHANGXIANSIXIAOSHI.b' />");//平日加班最多申请4小时,请重新选择!
			return false;
        }*/
        var otFromDate = APPLY_DATE.replaceAll("/",".") + " " +$("#OT_FROM_TIME",navTab.getCurrentPanel()).val();
        var otToDate   = APPLY_DATE.replaceAll("/",".") + " " +$("#OT_TO_TIME",navTab.getCurrentPanel()).val();
        var offset = $("#OFFSET_Y",navTab.getCurrentPanel()).val();
        $.ajax({//人事确认过的会和AR_DETAIL表的考勤冲突
			 cache: false,
			 type: 'post',
			 url: '/hrm/recruitManage/doSql',
			 data:{sql:"select AR_GET_OT_CLASH('1','${LoginUser.adminID}','"+otFromDate+"','"+otToDate+"','"+offset+"') FLAG from dual"},
			 dataType:"json",
			 success: function(data) {
				var flag = data.result[0].FLAG;
				/* if(flag>0){
					alertMsg.error("<spring:message code='ar.viewArOvertimeManagentFast.JIABANSHIJIANCHONGTUQINGJIANCHA.b' />");//与已有加班冲突，请检查该时间内是否已经申请
					return false;
				}else if(flag == -1){
					alertMsg.error("<spring:message code='ar.viewApplyAttenanceManagentInfoList.BAOHANKAOQINGUANBIDESHIJIAN.b' />");//包含考勤关闭的时间
					return false;
				}else if(flag == -2){
					alertMsg.error("<spring:message code='ar.viewArOvertimeManaget_fast.Include_apply_closed.b' />");//包含考勤关闭的时间
					return false;
				}
				else */ if(flag == -4){
					alertMsg.error("<spring:message code='ar.viewAdjustLeaveTSTOBatchList.WomenOvertime.b' />");//Đang trong thời gian mang thai hoặc nuôi con nhỏ. Không thể tăng ca!
					return false;
				}else if(flag == -3){
					alertMsg.error("<spring:message code='ar.viewAdjustLeaveTSTOBatchList.OvertimeLimit.b' />");//Bạn đã tăng ca quá thời gian quy định trong tháng (400 hoặc 300 tiếng), xin liên hệ phòng nhân sự!
					return false;
				}
			 },
			 error:DWZ.ajaxError
			}); 

        var affirmJsonData = '[';
		var tb2 = document.getElementById("addApplyLOTAffirm_list");
	   	if(tb2.rows.length == 0){
	   		alertMsg.error("<spring:message code='alert.message.pleaseFirstSetRuler.b' />");//请先设置决裁者
			return false;
	   	}else{
            var affirms = $("[name ='OT_AFFIRMOR_ID']",navTab.getCurrentPanel());
            for(var i=0;i<affirms.length;i++){
            	if (affirmJsonData.length > 1) {
            		affirmJsonData += ',{';
				} else {
					affirmJsonData += '{';
				}
               var rowId = affirms[i].id.substring(29);
               if(affirms[i].value == "" || affirms[i].value == null){
            	   alertMsg.error("<spring:message code='alert.message.Please_Affirmor_Complete.b' />");//请将裁决者信息补充完整!
                   return false;
               }
               affirmJsonData += ' "AFFIRMOR_ID": "' + affirms[i].value + '" ,';
               affirmJsonData += ' "AFFIRM_LEVEL": "' + $("#rowIndex_"+rowId,navTab.getCurrentPanel()).text() + '",';
               affirmJsonData += ' "AFFIRM_TYPE": "' + $("#approvType"+rowId,navTab.getCurrentPanel()).val() + '"';
               affirmJsonData += '}';
            }
		}
		affirmJsonData += ']';

		var jsonData = $form.serializeArray();
		jsonData.push({ name: 'affirmJsonData', value: affirmJsonData });
		console.log(jsonData);
		alertMsg.confirm("<spring:message code='hr.viewEvaluate.title.COMMIT_CONFIRM' />",//确定要提交吗？
		  	{okCall:function(){
				$.ajax({
					type:'POST',
					url:"/ess/infoApply/addSSTOvertimeApply",
					data:jsonData,
					dataType:"json",
					cache: false,
					success: navTabAjaxDone ,
					error: DWZ.ajaxError
				});	
		}});
		return false;
	});
});
//获取班次结束时间
function getDefaultOtTimeSST(){
	$.ajaxSettings.global = false;
	var APPLY_DATE = $("#APPLY_DATE",navTab.getCurrentPanel()).val();
	APPLY_DATE = APPLY_DATE.substring(6,10) + "/" + APPLY_DATE.substring(3,5) + "/" + APPLY_DATE.substring(0,2);

	$.ajax({
		type: 'POST',
		url: '/ess/infoApply/getOtShiftTime',
		data:[{ name: 'PERSON_ID', value: '${LoginUser.adminID}' },
		           { name: 'CNPY_ID', value: '${LoginUser.cpnyId}' },
				   { name: 'APPLY_DATE', value: APPLY_DATE }],
		dataType:"json",
		cache: false,
		success: function(data){
			var shiftStartTime = data.SHIFT_START_TIME;
			var shiftEndTime = data.SHIFT_END_TIME;
			var shiftEndTime_2 = data.SHIFT_END_TIME_2;
			if(data.DATETYPE == '1440'){
				$("#APPLY_TYPE_CODE_NEW",navTab.getCurrentPanel()).attr("value",32);
				$("#APPLY_TYPE_CODE_OT",navTab.getCurrentPanel()).attr("value",32);
				$("#OT_FROM_TIME",navTab.getCurrentPanel()).attr("value",shiftEndTime);
				$("#OT_TO_TIME",navTab.getCurrentPanel()).attr("value",shiftEndTime_2);
				$("#DEDUCT_Y",navTab.getCurrentPanel()).removeAttr("disabled");
				$("#DEDUCT_N",navTab.getCurrentPanel()).removeAttr("disabled");
			}else if(data.DATETYPE == '1441'){
				$("#APPLY_TYPE_CODE_NEW",navTab.getCurrentPanel()).attr("value",33);
				$("#APPLY_TYPE_CODE_OT",navTab.getCurrentPanel()).attr("value",33);
				$("#OT_FROM_TIME",navTab.getCurrentPanel()).attr("value",shiftStartTime);
				$("#OT_TO_TIME",navTab.getCurrentPanel()).attr("value",shiftEndTime);
				$("#DEDUCT_Y",navTab.getCurrentPanel()).removeAttr("disabled");
				$("#DEDUCT_N",navTab.getCurrentPanel()).removeAttr("disabled");
			}else{
				$("#APPLY_TYPE_CODE_NEW",navTab.getCurrentPanel()).attr("value",34);
				$("#APPLY_TYPE_CODE_OT",navTab.getCurrentPanel()).attr("value",34);
				$("#OT_FROM_TIME",navTab.getCurrentPanel()).attr("value",shiftStartTime);
				$("#OT_TO_TIME",navTab.getCurrentPanel()).attr("value",shiftEndTime);
				$("#DEDUCT_Y",navTab.getCurrentPanel()).removeAttr("disabled");
				$("#DEDUCT_N",navTab.getCurrentPanel()).removeAttr("disabled");
			}
			//$("#ADJUST_N",navTab.getCurrentPanel()).attr("checked","false");
			//$("#SPECIAL_N",navTab.getCurrentPanel()).attr("checked","false");
			$("#OFFSET_N",navTab.getCurrentPanel()).attr("checked","false");
			$("#SHIFTNAME",navTab.getCurrentPanel()).html(data.SHIFTNAME);
			$("#SHIFTTIME",navTab.getCurrentPanel()).html(data.SHIFT_START_TIME + "~" + data.SHIFT_END_TIME);
			
			$("#INDOOR_TIME",navTab.getCurrentPanel()).html(data.INDOOR_TIME);
			$("#OUTDOOR_TIME",navTab.getCurrentPanel()).html(data.OUTDOOR_TIME);
			$("#INDOOR",navTab.getCurrentPanel()).val(data.INDOOR_TIME);
			$("#OUTDOOR",navTab.getCurrentPanel()).val(data.OUTDOOR_TIME);
			$("#OT_LIMIT",navTab.getCurrentPanel()).val(data.OT_LIMIT);
			$("#OT_LIMIT_100",navTab.getCurrentPanel()).val(data.OT_LIMIT_100);
			$("#OT_TOTAIL_MONTH",navTab.getCurrentPanel()).html(data.OT_TOTAIL_MONTH+"&nbsp;h");//小时	
			$("#WEEKDAY_OT_TOTAIL",navTab.getCurrentPanel()).html(data.WEEKDAY_OT_TOTAIL+"&nbsp;h");//小时	
			$("#OT_TOTAIL",navTab.getCurrentPanel()).html(data.OT_TOTAIL+"&nbsp;h");//小时
			getOtLengthSST(0);
		},
		error: DWZ.ajaxError
	});
	$.ajaxSettings.global = true;
}
//获取加班时间长度
function getOtLengthSST(flag){
	var deduct_yn = 0;
	var applyDate = $("#APPLY_DATE",navTab.getCurrentPanel()).val();
	    applyDate = applyDate.substring(6,10) + "." + applyDate.substring(3,5) + "." + applyDate.substring(0,2);
	var otFromTime = $("#OT_FROM_TIME",navTab.getCurrentPanel()).val();
	var otToTime = $("#OT_TO_TIME",navTab.getCurrentPanel()).val();
	var otToTime = $("#OT_TO_TIME",navTab.getCurrentPanel()).val();
	var otLength = '0';
	var otTypeCode = $("#APPLY_TYPE_CODE_NEW",navTab.getCurrentPanel()).val();
	if($("#DEDUCT_Y",navTab.getCurrentPanel()).attr("checked")=='checked'){
		deduct_yn = 1;
	}
	if($("#DEDUCT_N",navTab.getCurrentPanel()).attr("checked")=='checked'){
		deduct_yn = 0;
	}
	$.ajaxSettings.global = false;
	$.ajax({
		type: 'POST',
		url: '/ess/infoApply/getOtLength',
		data:[{ name: 'PERSON_ID', value: '${LoginUser.adminID}' },{ name: 'CNPY_ID', value: '${LoginUser.cpnyId}' },{ name: 'OT_TYPE_CODE', value: otTypeCode },
		      { name: 'APPLY_DATE', value: applyDate },{ name: 'otFromTime', value: otFromTime },{ name: 'otToTime', value: otToTime },{ name: 'DEDUCT_YN', value: deduct_yn }],
		dataType:"json",
		cache: false,
		success: function(data){
			$("#otApplyLength",navTab.getCurrentPanel()).html(data.OT_LENGTH + "<spring:message code='ar.viewitemparameter.title.xiaoshi' />");//小时
			$("#OT_LENGTH",navTab.getCurrentPanel()).val(data.OT_LENGTH);
			//$("#APPLY_TYPE_CODE_NEW",navTab.getCurrentPanel()).attr("value",data.OT_TYPE_CODE);
			//$("#APPLY_TYPE_CODE_OT",navTab.getCurrentPanel()).attr("value",data.OT_TYPE_CODE);
			otLength = data.OT_LENGTH;
			if(flag == '0'){
				if(new Date(applyDate +" "+otToTime)-new Date(applyDate +" "+otFromTime)<0){
					$("#OFFSET_Y",navTab.getCurrentPanel()).attr("checked",true);
				}else{
					$("#OFFSET_N",navTab.getCurrentPanel()).attr("checked",true);
				}
			}
		},
		error: DWZ.ajaxError
	});
	getAffirmor_OT(otLength);
	$.ajaxSettings.global = true;
}
function getAffirmor_OT(applyLength){
	var applyTypeCode = $("#APPLY_TYPE_CODE_NEW").val();
	var applyTypeNo = $("#APPLY_TYPE_NO_OT").val();
	var htm = '';
	$.ajax({
		type: 'POST',
		url: '/ess/infoApplyAttendance/viewAffirmorList',
		data:[{ name: 'applyTypeCode', value: applyTypeCode },
		      { name: 'applyLength', value: applyLength },
		      { name: 'applyTypeNo', value: applyTypeNo }],
		dataType:"json",
		cache: false,
		async:false,
		success: function(data){
			if(data.affirmorList.length>0){
				for(var i=0;i<data.affirmorList.length;i++){
					var count = $("#applyOtCount").val();
					htm +='<tr id="rowIdApplyOt'+ count +'"><td class="td_type" style="text-align: center" width="5%" id="rowIndex_'+ count +'"><span name="rowIndex">'+(i+1)+'</span></td>';
			        htm +='<td class="td_type" style="text-align: center" width="20%">';
			        htm +='<input type="hidden" id="approvType' + count + '" name="approvType' + count + '" value="1" />';
			        htm +='<input type="hidden" name="approvTypeIndex" value="' + count + '" />';
			        htm +='<input id="dwz.person.AFFIRMOR_IDApplyOt' + count + '" name="OT_AFFIRMOR_ID" value="'+ data.affirmorList[i].AFFIRMOR_ID +'" type="hidden" />';
			        htm += data.affirmorList[i].LOCAL_NAME;
			        htm +='</td>';
					htm +='<td class="td_type" style="text-align: center" width="20%">';
					htm += data.affirmorList[i].EMPID;
					htm +='</td>';
					htm +='<td class="td_type" style="text-align: center" width="20%">';
					htm += data.affirmorList[i].DEPTNAME;
					htm +='</td>';
					htm +='<td class="td_type" style="text-align: center" width="20%">';
					htm += data.affirmorList[i].POSITION_NAME;
					htm +='</td>';
					<c:if test="${LoginUser.cpnyId eq 'HAE'}">
					htm +='<td class="td_type" style="text-align: center" width="15%">';
					htm +='<img src="/resources/images/+.gif" title="<spring:message code="button.add"/>"';	
					htm +='border="0" align="absmiddle" style="cursor:hand" onclick="addRowByIDApplyOt(' + count + ')"/>&nbsp;&nbsp;&nbsp;';
					htm +='<img src="/resources/images/-.gif" title="<spring:message code="button.delete"/>"';	
					htm +='border="0" align="absmiddle" style="cursor:hand" onclick="javaScript:document.all.addApplyLOTAffirm_list.deleteRow(event.srcElement.parentElement.parentElement.rowIndex);changeApplyOtLevel();"/></td>';
					</c:if>
					htm +='</tr>';
			  	$("#applyOtCount").val(++count) ;
				} 
				$("#addApplyLOTAffirm_list").html(htm);
			}else{
				$("#addApplyLOTAffirm_list").html('');
			}
		},
		error: DWZ.ajaxError
	});
}
function addRowByIDApplyOtFirst(){
	var count = parseInt($("#applyOtCount").val());
    var htm  ='<tr id="rowIdApplyOt'+ count +'"><td class="td_type" style="text-align: center" width="5%" id="rowIndex_'+ count +'"><span name="rowIndex">'+(i+1)+'</span></td>';
        htm +='<td class="td_type" style="text-align: center" width="20%">';
        htm +='<input type="hidden" id="approvType' + count + '" name="approvType" value="1" />';
        htm +='<input type="hidden" name="approvTypeIndex" value="' + count + '" />';
        //htm +='<input id="dwz.person.AFFIRMOR_IDApplyOt' + count + '" name="OT_AFFIRMOR_ID" value="" type="hidden" />';
        htm +='<input id="dwz.person.EMPNAMEApplyOt' + count + '"  type="text" style="text-align: center"  size="24" disabled="disabled"/>';
        htm +='</td>';        
		htm +='<td class="td_type" style="text-align: center" width="20%">';
		htm +='<input id="dwz.person.AFFIRMOR_IDApplyOt' + count + '" name="OT_AFFIRMOR_ID" value="" type="hidden"/>';
		htm +='<input id="dwz.person.EMPINFOApplyOt' + count + '" name="empid" alt="<spring:message code="evs.affirm.please_input_enter.e"/>" type="text" style="text-align: center" size="25" class="required" lookupGroup="person" onkeydown="submitKeyClick_applyOt(this,' + count + ',event)"/>';
		htm +='</td>';
		htm +='<td class="td_type" style="text-align: center" width="20%">';
		htm +='<input id="dwz.person.DEPTNAMEApplyOt' + count + '"  type="text" style="text-align: center"  size="30" disabled="disabled"/>';
		htm +='</td>';
		htm +='<td class="td_type" style="text-align: center" width="20%">';
		htm +='<input id="dwz.person.POSITIONApplyOt' + count + '"  type="text" style="text-align: center"  size="30" disabled="disabled"/>';
		htm +='</td>';
		htm +='<td class="td_type" style="text-align: center" width="15%">';
		htm +='<img src="/resources/images/+.gif" title="<spring:message code="button.add"/>"';	
		htm +='border="0" align="absmiddle" style="cursor:hand" onclick="addRowByIDApplyOt(' + count + ')"/>&nbsp;&nbsp;&nbsp;';
		htm +='<img src="/resources/images/-.gif" title="<spring:message code="button.delete"/>"';	
		htm +='border="0" align="absmiddle" style="cursor:hand" onclick="javaScript:document.all.addApplyLOTAffirm_list.deleteRow(event.srcElement.parentElement.parentElement.rowIndex);changeApplyOtLevel();"/></td></tr>';
        
		var tb2 = document.getElementById("addApplyLOTAffirm_list");
   	if(tb2.rows.length == 0){
   		$("#addApplyLOTAffirm_list").html(htm);
   	} else{
   	   	//当前行之后插入一行
   	   	$("#" + tb2.rows[0].id,navTab.getCurrentPanel()).before(htm);
   	}
  	$("[id='dwz.person.EMPINFOApplyOt" + count + "']",navTab.getCurrentPanel()).inputAlert();
   	changeApplyOtLevel();
  	$("#applyOtCount").val(++count);
}

function addRowByIDApplyOt(currentRowID){
	var count = parseInt($("#applyOtCount").val());
	var htm  ='<tr id="rowIdApplyOt'+ count +'"><td class="td_type" style="text-align: center" width="5%" id="rowIndex_'+ count +'"><span name="rowIndex">'+(i+1)+'</span></td>';
    htm +='<td class="td_type" style="text-align: center" width="20%">';
    htm +='<input type="hidden" id="approvType' + count + '" name="approvType" value="1" />';
    htm +='<input type="hidden" name="approvTypeIndex" value="' + count + '" />';
    //htm +='<input id="dwz.person.AFFIRMOR_IDApplyOt' + count + '" name="OT_AFFIRMOR_ID" value="" type="hidden" />';
    htm +='<input id="dwz.person.EMPNAMEApplyOt' + count + '"  type="text" style="text-align: center"  size="24" disabled="disabled"/>';
    htm +='</td>';        
	htm +='<td class="td_type" style="text-align: center" width="20%">';
	htm +='<input id="dwz.person.AFFIRMOR_IDApplyOt' + count + '" name="OT_AFFIRMOR_ID" value="" type="hidden"/>';
	htm +='<input id="dwz.person.EMPINFOApplyOt' + count + '" name="empid" type="text" style="text-align: center" alt="<spring:message code="evs.affirm.please_input_enter.e"/>" size="25" class="required" lookupGroup="person" onkeydown="submitKeyClick_applyOt(this,' + count + ',event)"/>';
	htm +='</td>';
	htm +='<td class="td_type" style="text-align: center" width="20%">';
	htm +='<input id="dwz.person.DEPTNAMEApplyOt' + count + '"  type="text" style="text-align: center"  size="30" disabled="disabled"/>';
	htm +='</td>';
	htm +='<td class="td_type" style="text-align: center" width="20%">';
	htm +='<input id="dwz.person.POSITIONApplyOt' + count + '"  type="text" style="text-align: center"  size="30" disabled="disabled"/>';
	htm +='</td>';
	htm +='<td class="td_type" style="text-align: center" width="15%">';
	htm +='<img src="/resources/images/+.gif" title="<spring:message code="button.add"/>"';	
	htm +='border="0" align="absmiddle" style="cursor:hand" onclick="addRowByIDApplyOt(' + count + ')"/>&nbsp;&nbsp;&nbsp;';
	htm +='<img src="/resources/images/-.gif" title="<spring:message code="button.delete"/>"';	
	htm +='border="0" align="absmiddle" style="cursor:hand" onclick="javaScript:document.all.addApplyLOTAffirm_list.deleteRow(event.srcElement.parentElement.parentElement.rowIndex);changeApplyOtLevel();"/></td></tr>';
   	//当前行之后插入一行
   	$("#rowIdApplyOt" + currentRowID,navTab.getCurrentPanel()).after(htm);
  	$("[id='dwz.person.EMPINFOApplyOt" + count + "']",navTab.getCurrentPanel()).inputAlert();
   	changeApplyOtLevel();
  	$("#applyOtCount").val(++count) ;
}

/*修改裁决者等级*/
function changeApplyOtLevel(){
	var tb2 = document.getElementById("addApplyLOTAffirm_list");
	var rowCount = tb2.rows.length;
	for(var m=0;m<rowCount;m++){
		tb2.rows[m].cells[0].innerHTML = m+1;
	}
}

var keyCodeInit=0;
function submitKeyClick_applyOt(obj,index,event){
	var localName = '';
	var idcardNo = '';
	var navTabId = '';
 	var e= event ? event : window.event; 
 	var keyCode = e.which ? e.which : e.keyCode;
   	if(keyCode==13){
   		keyCodeInit=keyCode;
		var empid=obj.value.replace(/[ ]/g,"");		
		var empIdStr=obj.id.substring(11);
		var personIdStr="AFFIRMOR_IDApplyOt"+empIdStr.substring(14);
		var empNameStr = "EMPNAMEApplyOt"+empIdStr.substring(14);
		var positionIdStr = "POSITIONApplyOt"+empIdStr.substring(14);
		var deptIdStr = "DEPTNAMEApplyOt"+empIdStr.substring(14);
		if(empid == ''){
			obj.value=" ";
			document.getElementById("onck_ot").href=encodeURI(encodeURI("/ar/attendanceMintenance/viewAddAffirmList?isEmployeement=1&firstFlag=1&limit=super&pageNum=1"
					+'&seach_KEY='+empid
					+'&empidStr='+empIdStr
					+'&personidStr='+personIdStr
					+'&positionIdStr='+positionIdStr
					+'&deptIdStr='+deptIdStr
					+'&empNameStr='+empNameStr					
					));
			document.getElementById("onck_ot").click();
		}else{
	   		$.ajax({
				type: 'POST',
				url: encodeURI('/sys/affirm/getPersonCntByEmpid?EMPID='+empid ),
				dataType:"json",
				cache: false,
				success: function(jsonObject){
							if(jsonObject.perCnt != 1 ){
								document.getElementById("onck_ot").href=encodeURI(encodeURI("/ar/attendanceMintenance/viewAddAffirmList?isEmployeement=1&limit=all&pageNum=1"
										+'&seach_KEY='+empid
										+'&empidStr='+empIdStr
										+'&personidStr='+personIdStr
										+'&positionIdStr='+positionIdStr
										+'&deptIdStr='+deptIdStr 
										+'&empNameStr='+empNameStr));
								document.getElementById("onck_ot").click();
							}
							if(jsonObject.perCnt==1){
								$("[id='dwz.person.EMPNAMEApplyOt" + index + "']").val(jsonObject.empName);
							  	$("[id='dwz.person.EMPINFOApplyOt" + index + "']").val(jsonObject.empId);
							  	$("[id='dwz.person.AFFIRMOR_IDApplyOt" + index + "']").val( jsonObject.personId);
							  	$("[id='dwz.person.POSITIONApplyOt" + index + "']").val( jsonObject.POSITION_NAME);
							  	$("[id='dwz.person.DEPTNAMEApplyOt" + index + "']").val( jsonObject.deptName);
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
/*document.onkeydown = function(event) {  
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
};*/
</script>
<%@ include file="/WEB-INF/view/hrm/empinfo/viewPersonalInfoHead_ess.jsp"%>
<div class="pageContent" style="padding-top:5px;">
		<form id="viewSSTOtApplyInfoForm" method="post" action="/ess/infoApply/addPOvertimeApply" class="required-validate">
			<div>
				<table class="user_table" width="100%"  border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="20%" class="td_title" style="text-align:right"><!--日期--><spring:message code="ess.infoApply.date"/></td>
						    <td width="30%" class="td_type">
 							<input type="text" id="APPLY_DATE" name="APPLY_DATE" class="Wdate"  value="${APPLY_DATE}" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en',onpicked:getDefaultOtTimeSST})"/>					    
						</td>
						<td width="20%" class="td_title" style="text-align:right">
							<!--加班情况--><spring:message code="ess.infoApply.title.OTQINGKUANG"/>
						</td>
						<td width="30%" class="td_type">
						    <!--本月累计加班--><spring:message code="ess.viewSSTOtApplyInfo.BENYUELEIJIJIABAN.b"/>:&nbsp;&nbsp;<span id="OT_TOTAIL_MONTH"></span>&nbsp;&nbsp;&nbsp;&nbsp;
						    <!--平时--><spring:message code="ar.viewitemparameter.title.pingshi" />:&nbsp;&nbsp;<span id="WEEKDAY_OT_TOTAIL"></span>&nbsp;&nbsp;&nbsp;&nbsp;
						    <!--本月累计加班上限--><spring:message code="ess.viewSSTOtApplyInfo.BENYUELEIJIJIABANSHANXIAN.b"/>:&nbsp;&nbsp;<span id="OT_TOTAIL_MONTH_LIMIT">40&nbsp;h</span>
						    <!-- Over Time Limit --><input type="hidden" id="OT_LIMIT" name="OT_LIMIT"/>
						    						<input type="hidden" id="OT_LIMIT_100" name="OT_LIMIT_100"/>
						    <br>													    					    
							<!--本年累计加班--><spring:message code="ess.viewSSTOtApplyInfo.BENNIANLEIJIJIABAN.b"/>:&nbsp;&nbsp;<span id="OT_TOTAIL"></span>&nbsp;&nbsp;&nbsp;&nbsp;
							<!--本年累计加班上限--><spring:message code="ess.viewSSTOtApplyInfo.BENNIANLEIJIJIABANSHANXIAN.b"/>:&nbsp;&nbsp;<span id="OT_TOTAIL_LIMIT">300&nbsp;h</span>							
						</td>
					</tr>
					<tr>
					    <td width="20%" class="td_title" style="text-align:right">
					    	<!--时间--><spring:message code="ess.infoApply.title.time"/>
					    </td>
					    <td width="30%" class="td_type">
							<ait:time name="OT_FROM_TIME" spacing="15" extra="12:10,20:05" selected="00:00" onChange="getOtLengthSST(0);"/>
							~
							<ait:time name="OT_TO_TIME" spacing="15" extra="12:10,20:05" selected="00:00" onChange="getOtLengthSST(0);"/>
						</td>
						<td width="20%" class="td_title" style="text-align:right"><!--加班类型-->
							<spring:message code="ess.viewApply.title.overtimeApplyType"/>
						</td>
							 <input type="hidden" id="APPLY_AFFIRM_FLAG_OT"  name="APPLY_AFFIRM_FLAG" value="14014306"/>
							 <input type="hidden" id="APPLY_TYPE_NO_OT"  name="APPLY_TYPE_NO" value="31"/>
							 <input type="hidden" id="APPLY_FLAG_OT"  name="APPLY_FLAG" value="0"/>
							 <input type="hidden" id="APPLY_TYPE_CODE_OT"  name="APPLY_TYPE_CODE" value=""/>
						<td width="30%" class="td_type">
							 <ait:SelectSyCodeByCpnyID id="APPLY_TYPE_CODE_NEW" name="APPLY_TYPE_CODE_NEW" parentNo="31" selected="" disabled="true"/>
						</td>
					</tr>
					<tr>
					    <td width="20%" class="td_title" style="text-align:right">
					    	<!--班次--><spring:message code="ess.message.work_shift"/>
					    </td>
					    <td width="30%" class="td_type" id="SHIFTNAME"></td>
					    <td width="20%" class="td_title" style="text-align:right">
					    	<!--工作时间--><spring:message code="ess.infoApply.working_hours"/>
					    </td>
					    <td width="30%" class="td_type" id="SHIFTTIME"></td>
						
					</tr>
					<tr>
					    <td width="20%" class="td_title" style="text-align:right">
					    	 <!--进出门时间--><spring:message code="ess.infoApply.inouttime"/>
					    </td>
					    <td width="30%" class="td_type" >
							<!--进门时间--><spring:message code="ess.infoApply.in_door_time"/>:&nbsp;&nbsp;<span id="INDOOR_TIME"></span><input type="hidden" id="INDOOR"><br>
							<!--出门时间--><spring:message code="ess.infoApply.out_door_time"/>:&nbsp;&nbsp;<span id="OUTDOOR_TIME"></span><input type="hidden" id="OUTDOOR">
						</td>
						<td width="20%" class="td_title" style="text-align:right">
					    	<!--是否跨日--><spring:message code="ess.infoApply.ISORNOTNEXTDAY"/>
					    </td>
					    <td width="30%" class="td_type" >
							<input type="radio" name="OFFSET_YN" value="1" id="OFFSET_Y" readonly="true"><!--是--><spring:message code="ess.infoApply.yes"/>
							<input type="radio" name="OFFSET_YN" value="0" id="OFFSET_N" checked="checked" readonly="true"><!--否--><spring:message code="ess.infoApply.no"/>
						</td>
						<!--<td width="20%" class="td_title" style="text-align:right">
						   <div id="ADJUST_SHOW" style="">是否调休<spring:message code="ess.infoApply.shifoutiaoxiu"/></div>
						</td>
						<td width="30%" class="td_type" >
							<div id="ADJUST_SHOW1" style="">
								<input type="radio" name="ADJUST_YN" value="1" id="ADJUST_Y"  onclick="getOtLengthSST(1)">是<spring:message code="ess.infoApply.yes"/>
								<input type="radio" name="ADJUST_YN" value="0" id="ADJUST_N"  checked="checked"  onclick="getOtLengthSST(1)">否<spring:message code="ess.infoApply.no"/>
							</div>
						</td>-->
					</tr>	
					<tr>		
 						<td width="20%" class="td_title" style="text-align:right">
							<!--加班时长--><spring:message code="ess.infoApply.overtime_hours"/>
						</td>
						<td width="30%" class="td_type">
							<div id="otApplyLength">0</div>
						</td>
						<input type="hidden" id="OT_LENGTH" name="OT_LENGTH" value="0"/>
						<td width="20%" class="td_title" style="text-align:right">
						    <!--是否扣除吃饭时间--><spring:message code="ess.viewSSTOtApplyInfo.DEDUCT_MEAL_TIME_YN.b"/> / <spring:message code="heran.ess.viewLikeLeaveApplyInfo.yuanxiuxiri"/> (30')
						</td>
						<td width="30%" class="td_type" >
							<input type="radio" name="DEDUCT_YN" value="1" id="DEDUCT_Y" disabled="disabled" onclick="getOtLengthSST(1)" ><!--是--><spring:message code="ess.infoApply.yes"/>
							<input type="radio" name="DEDUCT_YN" value="0" id="DEDUCT_N" checked="checked" disabled="disabled" onclick="getOtLengthSST(1)"><!--否--><spring:message code="ess.infoApply.no"/>
						</td>
					</tr>
					<tr>
					    <td width="20%" class="td_title" style="text-align:right"><!--其他原因-->
					    	<!--原因--><spring:message code="ess.infoApply.Reason"/>
					    </td>
					    <td width="80%" class="td_type" colspan="3">
					    	<textarea style="width:500px;height:80px;resize: none;" id="APPLY_REMARK" name="APPLY_REMARK"></textarea>
					    </td>
					</tr>		
					<tr>
						<td colspan="6">
						<br>
							<table class="user_table" width="100%">	
								<tr>
								    <td class="td_title"  style="text-align:center;" width="5%"><!--序号--><spring:message code="ess.viewApply.title.number"/></td>
									<td class="td_title"  style="text-align:center;" width="20%"><!--决裁者--><spring:message code="hr.viewTransactionTransViewList.title.DEFINITELY_CUTTION_CODITIONS"/></td>
									<td class="td_title" style="text-align:center;" width="20%"><!--社号--><spring:message code="hr.enpinfo.title.EMP.EMPNUMBER"/></td>
									<td class="td_title"  style="text-align:center;" width="20%"><!--部门--><spring:message code="hrm.empinfo.ORG_NAME_LOCAL"/></td>
									<td class="td_title" style="text-align:center;" width="20%"><!--职责--><spring:message code="hrm.contract.POSITION_NO"/></td>
								    <c:if test="${LoginUser.cpnyId eq 'HAE'}">
								    <td class="td_title" style="text-align:center;" width="15%"><!--是否新增 --> <spring:message code="evs.viewRegPersonalProbation.SHIFOUXINZENG.a" />(<img src="/resources/images/+.gif" title="<spring:message code="button.add"/>" border="0" align="absmiddle" style="cursor:hand" onclick="addRowByIDApplyOtFirst()"/>)</td>
								    </c:if>
								</tr>
								<tr>
									<td colspan="6">
										<table width="100%" border="0" cellpadding="0" cellspacing="0" id="addApplyLOTAffirm_list">
											<tbody id="rowIdApplyOt">
											</tbody>
										</table>
										<input type="hidden" name="applyOtCount" id="applyOtCount" value="1">
										<a id="onck_ot" name="onck_ot"  href="" lookupGroup="person" rel="submitKeyClick_apply_Ot_affirm"></a>
				    				</td>
		    					</tr>
		    				</table>
						</td>
					</tr>
				</table>
			</div>
			<div class="formBar">
				<ul>
					<li>
						<div class="button">
							<div class="buttonContent"><!--禀告-->
								<button type="button" id="viewSSTOtApplyInfo_save">
									<!--申请--><spring:message code="ess.infoApply.title.apply"/>
								</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
	  	</form>	
</div>