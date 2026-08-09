<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript">
//初始
$(document).ready(function(){
	//保存
	$("#viewSSTApplyAttendance_save",navTab.getCurrentPanel()).click(function(){

	    var applyTypeCode = $("#APPLY_TYPE_CODE",navTab.getCurrentPanel()).val();
	    var APPLY_LENGTH = $("#shenqingshichang",navTab.getCurrentPanel()).val();
	    var APPLY_LENGTH_HOUR = $("#shenchangFormatHour",navTab.getCurrentPanel()).val();	
	    var DAY_HOUR = $("#dayHour",navTab.getCurrentPanel()).val();
	    var leave_from_date = $("#LEAVE_FROM_TIME",navTab.getCurrentPanel()).val();
	    var fromTime = $("#fromTime",navTab.getCurrentPanel()).val();
	    var leave_to_date = $("#LEAVE_TO_TIME",navTab.getCurrentPanel()).val();
	    var toTime = $("#toTime",navTab.getCurrentPanel()).val();
	    if(leave_from_date==""){
	    	alertMsg.error('<spring:message code="ess.infoApplyAttendance.PLEASE_ATTENDANCE_SDATE.Z" />');//请选择考勤开始时间！
	    	return false;
	   	}
	    if(APPLY_LENGTH==0){
	    	alertMsg.error('<spring:message code="ess.infoApplyAttendance.ATTENDANCE_LONGER_THAN_0.Z" />');//考勤时长必须大于0
	    	return false;
	   	}
	    	
	    if(leave_to_date==""){
	    	alertMsg.error('<spring:message code="ess.infoApplyAttendance.PLEASE_ATTENDANCE_EDATE.Z" />');//请选择考勤结束时间
	    	return false;
	    }
	   	if(applyTypeCode==""){
	   	    alertMsg.error('<spring:message code="ess.infoApplyAttendance.PLEASE_ATTENDANCE_TYPE.Z" />');//请选择考勤类型
	   		return false;
	   	}
	   	var leavefromtime = leave_from_date + " " + fromTime + ":" + "00";
		var leavetotime = leave_to_date + " " + toTime + ":" + "00";
		if(comptime(leavefromtime,leavetotime)!=1){
			alertMsg.error('<spring:message code="ess.infoApplyAttendance.ATTENDANCE_SDATE_THAN_EDATE.Z" />');//考勤结束时间必须大于考勤开始时间
			return false;
		}
		
		$.ajax({
			 cache: false,
			 type: 'post',
			 url: '/hrm/recruitManage/doSql',
			 data:{sql:"select AR_GET_LEAVE_CLASH('1','${LoginUser.adminID}"+applyTypeCode+"',to_char(to_date('" + leavefromtime + "','DD/MM/YYYY HH24:MI:SS'),'YYYY/MM/DD HH24:MI'),to_char(to_date('" + leavetotime + "','DD/MM/YYYY HH24:MI:SS'),'YYYY/MM/DD HH24:MI')) FLAG from dual"},
			 dataType:"json",
			 success: function(data) {
				var flag = data.result[0].FLAG;
				 if(flag == -1){
					alertMsg.error("<spring:message code='ar.viewApplyAttenanceManagentInfoList.BAOHANKAOQINGUANBIDESHIJIAN.b' />");//包含考勤关闭的时间
					return false;
				}else if(flag == -2){
					alertMsg.error("<spring:message code='ar.viewArOvertimeManaget_fast.Include_apply_closed.b' />");//包含申请关闭的日期
					return false;
				}
				else if(flag == -3){
					alertMsg.error("<spring:message code='alert.message.ess.infoApply.noEnoughAnnualCannotApplyAnnualLeave' />");//No enough anual leave
					return false;
				}
			 },
			 error:DWZ.ajaxError
		}); 
		
		if(applyTypeCode == '22'){
			if(APPLY_LENGTH_HOUR > 3){
				alertMsg.error('<spring:message code="alert.message.GERENHUNJIAZUIDUOSANTIAN.b" />');//个人婚假最多3天!
		   		return false;
			}
		}
        if(applyTypeCode == '27'){
			
			if(APPLY_LENGTH_HOUR > 180){
				alertMsg.error('<spring:message code="alert.message.CHANJIAZUIDUOYIBAIBASHITIAN.b" />');//产假最多180天!
		   		return false;
			}
			
		}
		if(applyTypeCode == '27' || applyTypeCode == '482' || applyTypeCode == '15821' || applyTypeCode == '14013816' || applyTypeCode == '25' || 
				applyTypeCode == '14015956' || applyTypeCode == '14015957' ){
			
			if(APPLY_LENGTH % DAY_HOUR != 0 && DAY_HOUR != 7 && DAY_HOUR != 7.53){
				alertMsg.error('<spring:message code="alert.message.ess.infoApply.applyTimeCanNotLessThanMinValue" />');//休假最小指是半天
		   		return false;
			}
		}
        if(applyTypeCode == '23'){
			
			if(APPLY_LENGTH_HOUR > 3){
				alertMsg.error('<spring:message code="alert.message.YOUXINSANGJIAZUIDUOSANTIAN.b" />');//有薪丧假最多3天!
		   		return false;
			}
		}
        
		if(applyTypeCode == '26'){
			if(APPLY_LENGTH % (DAY_HOUR / 2) != 0 && DAY_HOUR != 7 && DAY_HOUR != 7.53){
				alertMsg.error('<spring:message code="alert.message.ess.infoApply.applyTimeCanNotLessThanMinValue" />');//休假最小指是半天
		   		return false;
			}
		}

        var affirmJsonData = '[';
		var tb2 = document.getElementById("addApplyLeaveAffirm_list");
	   	if(tb2.rows.length == 0){
	   		alertMsg.error("<spring:message code='alert.message.pleaseFirstSetRuler.b' />");//请先设置决裁者
			return false;
	   	}else{
            var affirms = $("[name ='ATT_AFFIRMOR_ID']",navTab.getCurrentPanel());
            for(var i=0;i<affirms.length;i++){
            	if (affirmJsonData.length > 1) {
            		affirmJsonData += ',{';
				} else {
					affirmJsonData += '{';
				}
               var rowId = affirms[i].id.substring(32);
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
        
		var $form = $("#viewSSTApplyAttendance_form",navTab.getCurrentPanel());
		var jsonData = $form.serializeArray();
		jsonData.push({ name: 'affirmJsonData', value: affirmJsonData });
		alertMsg.confirm("<spring:message code='ess.message.confirm_sava' />",//确定要保存吗？
	  		{okCall:function(){
			  	$.ajax({
	  				type: 'POST',
	  				url:$form.attr("action"),
	  				data:jsonData,
	  				dataType:"json",
	  				cache: false,
	  				success: navTabAjaxDone,
					error: DWZ.ajaxError
	  			});
	  		}});
		return false;
	});
 	//考勤类型验证
	$("#APPLY_TYPE_CODE",navTab.getCurrentPanel()).change(function(){
		var id = $(this).val();
		//年假
/*		$.ajax({//休假说明
			cache: false,
			 type: 'post',
			 async:false,
			 url: '/hrm/recruitManage/doSql',
			 data:{sql:"SELECT GET_LEAVE_EXPLAIN('"+id+"','${LoginUser.cpnyId}') LEAVE_EXPLAIN FROM DUAL"},
			 dataType:"json",
			 success: function(data) {
				 	var LEAVE_EXPLAIN = data.result[0].LEAVE_EXPLAIN;
					$("#LEAVE_EXPLAIN",navTab.getCurrentPanel()).html(LEAVE_EXPLAIN);
				},
			 error: DWZ.ajaxError,
		}); */
		if('26' == id){
			//$("#view_daoxiu_sub",navTab.getCurrentPanel()).css("display","none");
			$("#view_vac_sub",navTab.getCurrentPanel()).css("display","");
			//$("#view_chanjia_sub",navTab.getCurrentPanel()).css("display","none");
		}else{
			//$("#view_daoxiu_sub",navTab.getCurrentPanel()).css("display","none");
			$("#view_vac_sub",navTab.getCurrentPanel()).css("display","none");
			//$("#view_chanjia_sub",navTab.getCurrentPanel()).css("display","none");
		}
		if('27' == id || '16415' == id || '28' == id || '482' == id ){
			$.ajax({
				 cache: false,
				 type: 'post',
				 async:false,
				 url: "/ess/infoApplyAttendance/getLeaveDateSST",
				 data: [{ name: 'apply_type', value: id },
				        { name: 'PERSON_ID', value: '${LoginUser.adminID}' }],
				 dataType:"json",
				 success: function(data) {
					if(data.statusCode == 300){
						alertMsg.error(data.message);
						$("select[name='APPLY_TYPE_CODE']").val('');
						$("#MATERNITY_LEAVE_SPAN",navTab.getCurrentPanel()).css("display","none");
					}
				 }
			});
		}
	});
});

function composeLeaveTime(){
	$("#fromTime",navTab.getCurrentPanel()).val($("#fromTime_shi",navTab.getCurrentPanel()).val() + ":" + $("#fromTime_fen",navTab.getCurrentPanel()).val());
	$("#toTime",navTab.getCurrentPanel()).val($("#toTime_shi",navTab.getCurrentPanel()).val() + ":" + $("#toTime_fen",navTab.getCurrentPanel()).val());
	callength();
	getAffirmor();
}
function getEndDate(){
	var applyTypeCode = $("#APPLY_TYPE_CODE").val();
	if(applyTypeCode =="27"){ //产假
		var leave_from_date = $("#LEAVE_FROM_TIME",navTab.getCurrentPanel()).val();
		var chanjiashengyu = $("#chanJiaShengYuDays",navTab.getCurrentPanel()).val();
		$.ajax({
			cache: false,
			 type: 'post',
			 async:false,
			 url: '/hrm/recruitManage/doSql',
			 data:{sql:"SELECT TO_CHAR(TO_DATE('"+leave_from_date+"','yyyy.mm.dd')+"+chanjiashengyu+"-1,'yyyy.mm.dd') END_DATE FROM DUAL"},
			 dataType:"json",
			 success: function(data) {
				 	var endDate = data.result[0].END_DATE;
					$("#viewSSTApplyAttendance_form input[id='LEAVE_TO_TIME']").val(endDate);
				},
			 error: DWZ.ajaxError,
		});
		getAffirmor();
		callength();
	}
}
/*
function getStartDate(){
	var applyTypeCode = $("#APPLY_TYPE_CODE").val();
	if(applyTypeCode =="27"){
		var leave_to_date = $("#LEAVE_TO_TIME",navTab.getCurrentPanel()).val();
		var chanjiashengyu = $("#chanJiaShengYuDays",navTab.getCurrentPanel()).val();
		$.ajax({
			cache: false,
			 type: 'post',
			 async:false,
			 url: '/hrm/recruitManage/doSql',
			 data:{sql:"SELECT TO_CHAR(TO_DATE('"+leave_to_date+"','yyyy.mm.dd')-"+chanjiashengyu+"+1,'yyyy.mm.dd') START_DATE FROM DUAL"},
			 dataType:"json",
			 success: function(data) {
				 	var startDate = data.result[0].START_DATE;
					$("#viewSSTApplyAttendance_form input[id='LEAVE_FROM_TIME']").val(startDate);
				},
			 error: DWZ.ajaxError,
		});
		getAffirmor();
		callength();
	}
}
*/
//比较时间 格式 dd-mm-yyyy hh:mi:ss 
function comptime(beginTime,endTime){
	var beginTimes=beginTime.substring(0,10).split('/');
	var endTimes=endTime.substring(0,10).split('/');
	
	beginTime=beginTimes[2]+'/'+beginTimes[1]+'/'+beginTimes[0]+' '+beginTime.substring(10,19);
	endTime=endTimes[2]+'/'+endTimes[1]+'/'+endTimes[0]+' '+endTime.substring(10,19);

	var a =(Date.parse(endTime)-Date.parse(beginTime))/3600/1000;
	if(a<0){
		return -1;
	}else if (a>0){
		return 1;
	}else if (a==0){
		return 0;
	}else{
		return 'exception';
	}
}

//计算长度
function callength(){
	//dd.mm.yyyy 格式
	var leave_from_date = $("#viewSSTApplyAttendance_form input[id='LEAVE_FROM_TIME']").val();
	//转换成yyyy.mm.dd格式的 
    var leave_from_date_format = leave_from_date.substring(6,10) + '-' + leave_from_date.substring(3,5) + '-' + leave_from_date.substring(0,2); 
	
	var fromTime = $("#viewSSTApplyAttendance_form input[id='fromTime']").val();
	var leave_to_date = $("#viewSSTApplyAttendance_form input[id='LEAVE_TO_TIME']").val();
	var leave_to_date_format = leave_to_date.substring(6,10) + '-' + leave_to_date.substring(3,5) + '-' + leave_to_date.substring(0,2); 

	var toTime = $("#viewSSTApplyAttendance_form input[id='toTime']").val();
	var leavefromtime = leave_from_date + " " + fromTime + ":" + "00";
	var leavefromtimeformat = leave_from_date_format + " " + fromTime + ":" + "00";
	var leavetotime = leave_to_date + " " + toTime + ":" + "00";
	var leavetotimeformat = leave_to_date_format + " " + toTime + ":" + "00";
	var applyTypeCode = $(":input[name='APPLY_TYPE_CODE']").val();
	if(comptime(leavefromtime,leavetotime)!=1){
		$("#viewSSTApplyAttendance_form input[id='LEAVE_TO_TIME']").val(leave_from_date);
		leave_to_date = leave_from_date;
		leave_to_date_format = leave_from_date_format;
		leavefromtime = leave_from_date + " " + fromTime + ":" + "00";
		leavefromtimeformat = leave_from_date_format + " " + fromTime + ":" + "00";
		leavetotime = leave_to_date + " " + toTime + ":" + "00";
		var leavetotimeformat = leave_to_date_format + " " + toTime + ":" + "00";
	}
	/* if(applyTypeCode=='16415'){
		leavefromtime = leave_from_date + " 00:00:00";
		leavefromtimeformat = leave_from_date_format + " 00:00:00";
		leavetotime = leave_to_date + " 23:00:00";
		leavetotimeformat = leave_to_date_format + " 23:00:00";
		$.ajax({
			 cache: false,
			 type: 'post',
			 async:false,
			 url: '/hrm/recruitManage/doSql',
			 data:{sql:"select ar_get_day_hours('${LoginUser.adminID}','"+leavefromtimeformat+"') DAY_HOUR,GET_AR_LEAVE_LENGTH('${LoginUser.adminID}','${LoginUser.cpnyId}',to_date('" + leavefromtimeformat + "','YYYY.MM.DD HH24:MI:SS'),to_date('" + leavetotimeformat + "','YYYY.MM.DD HH24:MI:SS'),'" + applyTypeCode + "') LEAVE_LENGTH from dual"},
			 dataType:"json",
			 success: function(data) {
				 $("#shenqingshichang",navTab.getCurrentPanel()).val(data.result[0].LEAVE_LENGTH);
				var lengthText = "";
				var length = Math.floor(data.result[0].LEAVE_LENGTH/data.result[0].DAY_HOUR) ;
				$("#shenchangFormatHour",navTab.getCurrentPanel()).val(data.result[0].LEAVE_LENGTH/data.result[0].DAY_HOUR);				
				if( length > 0 ){
					lengthText += length + "<spring:message code='ar.viewitemparameter.title.dayofunit' />";//天
				}
				if(lengthText == ""){
					$("#shenqingshichangText",navTab.getCurrentPanel()).html("0<spring:message code='ar.viewitemparameter.title.xiaoshi' />");//小时
				}else{
					$("#shenqingshichangText",navTab.getCurrentPanel()).html(lengthText);
				}
			 }
		});
	}else{ */
		$.ajax({
			 cache: false,
			 type: 'post',
			 async:false,
			 url: '/hrm/recruitManage/doSql',
			 data:{sql:"select ar_get_day_hours('${LoginUser.adminID}','"+leavefromtimeformat+"') DAY_HOUR,GET_AR_LEAVE_LENGTH('${LoginUser.adminID}','${LoginUser.cpnyId}',to_date('" + leavefromtimeformat + "','YYYY.MM.DD HH24:MI:SS'),to_date('" + leavetotimeformat + "','YYYY.MM.DD HH24:MI:SS'),'" + applyTypeCode + "') LEAVE_LENGTH from dual"},
			 dataType:"json",
			 success: function(data) {
				 $("#shenqingshichang",navTab.getCurrentPanel()).val(data.result[0].LEAVE_LENGTH);
				var lengthText = "";
				var length = Math.floor(data.result[0].LEAVE_LENGTH/data.result[0].DAY_HOUR) ;
				 $("#shenchangFormatHour",navTab.getCurrentPanel()).val(data.result[0].LEAVE_LENGTH/data.result[0].DAY_HOUR);
				 $("#dayHour",navTab.getCurrentPanel()).val(data.result[0].DAY_HOUR);
				if( length > 0 ){
					lengthText += length + "<spring:message code='ar.viewitemparameter.title.dayofunit' />";//天
				}
				var length = (data.result[0].LEAVE_LENGTH % data.result[0].DAY_HOUR).toFixed(1) ;
				if(length > 0 ){
					lengthText += length + "<spring:message code='ar.viewitemparameter.title.xiaoshi' />";//小时
				}
				if(lengthText == ""){
					$("#shenqingshichangText",navTab.getCurrentPanel()).html("0<spring:message code='ar.viewitemparameter.title.xiaoshi' />");//小时
				}else{
					$("#shenqingshichangText",navTab.getCurrentPanel()).html(lengthText);
				}
			 }
		});
	//}
	getAffirmor();
}

/**
 * 禁用textArea以及Input框的enter键的自动提交
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
	        	   $("#refresh",navTab.getCurrentPanel()).focus();
		           return false; 
		       }else {
			        return true;
			   }   
	      }  
	 }  
}
 */
function getAffirmor(){
	var applyTypeCode = $("#APPLY_TYPE_CODE",navTab.getCurrentPanel()).val();
	var applyLength = $("#shenqingshichang",navTab.getCurrentPanel()).val();
	var applyTypeNo = $("#APPLY_TYPE_NO",navTab.getCurrentPanel()).val();
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
					var count = $("#applyLeaveCount").val();
					htm +='<tr id="rowIdApplyLeave'+ count +'"><td class="td_type" style="text-align: center" width="5%" id="rowIndex_'+ count +'"><span name="rowIndex">'+(i+1)+'</span></td>';
			        htm +='<td class="td_type" style="text-align: center" width="20%">';
			        htm +='<input type="hidden" id="approvType' + count + '" name="approvType' + count + '" value="1" />';
			        htm +='<input type="hidden" name="approvTypeIndex" value="' + count + '" />';
			        htm +='<input id="dwz.person.AFFIRMOR_IDApplyLeave' + count + '" name="ATT_AFFIRMOR_ID" value="'+ data.affirmorList[i].AFFIRMOR_ID +'" type="hidden" />';
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
					htm +='border="0" align="absmiddle" style="cursor:hand" onclick="addRowByIDApplyLeave(' + count + ')"/>&nbsp;&nbsp;&nbsp;';
					htm +='<img src="/resources/images/-.gif" title="<spring:message code="button.delete"/>"';	
					htm +='border="0" align="absmiddle" style="cursor:hand" onclick="javaScript:document.all.addApplyLeaveAffirm_list.deleteRow(event.srcElement.parentElement.parentElement.rowIndex);changeApplyLeaveLevel();"/></td>';
					</c:if>
					htm +='</tr>';
			  	$("#applyLeaveCount",navTab.getCurrentPanel()).val(++count) ;
				} 
				$("#addApplyLeaveAffirm_list",navTab.getCurrentPanel()).html(htm);
			}else{
				$("#addApplyLeaveAffirm_list",navTab.getCurrentPanel()).html('');
			}
		},
		error: DWZ.ajaxError
	});
	/*if(applyTypeCode=='27'){
		$("#MATERNITY_LEAVE_SPAN",navTab.getCurrentPanel()).css("display","");
	}else{
		$("#MATERNITY_LEAVE_SPAN",navTab.getCurrentPanel()).css("display","none");
	}*/
}

function addRowByIDApplyLeaveFirst(){
	var count = parseInt($("#applyLeaveCount").val());
    var htm  ='<tr id="rowIdApplyLeave'+ count +'"><td class="td_type" style="text-align: center" width="5%" id="rowIndex_'+ count +'"><span name="rowIndex">'+(i+1)+'</span></td>';
        htm +='<td class="td_type" style="text-align: center" width="20%">';
        htm +='<input type="hidden" id="approvType' + count + '" name="approvType" value="1" />';
        htm +='<input type="hidden" name="approvTypeIndex" value="' + count + '" />';
        //htm +='<input id="dwz.person.AFFIRMOR_IDApplyLeave' + count + '" name="ATT_AFFIRMOR_ID" value="" type="hidden" />';
        htm +='<input id="dwz.person.EMPNAMEApplyLeave' + count + '"  type="text" style="text-align: center"  size="24" disabled="disabled"/>';
        htm +='</td>';        
		htm +='<td class="td_type" style="text-align: center" width="20%">';
		htm +='<input id="dwz.person.AFFIRMOR_IDApplyLeave' + count + '" name="ATT_AFFIRMOR_ID" value="" type="hidden"/>';
		htm +='<input id="dwz.person.EMPINFOApplyLeave' + count + '" name="empid" alt="<spring:message code="evs.affirm.please_input_enter.e"/>" type="text" style="text-align: center" size="25" class="required" lookupGroup="person" onkeydown="submitKeyClick_applyLeave(this,' + count + ',event)"/>';
		htm +='</td>';
		htm +='<td class="td_type" style="text-align: center" width="20%">';
		htm +='<input id="dwz.person.DEPTNAMEApplyLeave' + count + '"  type="text" style="text-align: center"  size="30" disabled="disabled"/>';
		htm +='</td>';
		htm +='<td class="td_type" style="text-align: center" width="20%">';
		htm +='<input id="dwz.person.POSITIONApplyLeave' + count + '"  type="text" style="text-align: center"  size="30" disabled="disabled"/>';
		htm +='</td>';
		htm +='<td class="td_type" style="text-align: center" width="15%">';
		htm +='<img src="/resources/images/+.gif" title="<spring:message code="button.add"/>"';	
		htm +='border="0" align="absmiddle" style="cursor:hand" onclick="addRowByIDApplyLeave(' + count + ')"/>&nbsp;&nbsp;&nbsp;';
		htm +='<img src="/resources/images/-.gif" title="<spring:message code="button.delete"/>"';	
		htm +='border="0" align="absmiddle" style="cursor:hand" onclick="javaScript:document.all.addApplyLeaveAffirm_list.deleteRow(event.srcElement.parentElement.parentElement.rowIndex);changeApplyLeaveLevel();"/></td></tr>';
        
		var tb2 = document.getElementById("addApplyLeaveAffirm_list");
   	if(tb2.rows.length == 0){
   		$("#addApplyLeaveAffirm_list").html(htm);
   	} else{
   	   	//当前行之后插入一行
   	   	$("#" + tb2.rows[0].id,navTab.getCurrentPanel()).before(htm);
   	}
  	$("[id='dwz.person.EMPINFOApplyLeave" + count + "']",navTab.getCurrentPanel()).inputAlert();
   	changeApplyLeaveLevel();
  	$("#applyLeaveCount").val(++count);
}

function addRowByIDApplyLeave(currentRowID){
	var count = parseInt($("#applyLeaveCount").val());
	var htm  ='<tr id="rowIdApplyLeave'+ count +'"><td class="td_type" style="text-align: center" width="5%" id="rowIndex_'+ count +'"><span name="rowIndex">'+(i+1)+'</span></td>';
    htm +='<td class="td_type" style="text-align: center" width="20%">';
    htm +='<input type="hidden" id="approvType' + count + '" name="approvType" value="1" />';
    htm +='<input type="hidden" name="approvTypeIndex" value="' + count + '" />';
    //htm +='<input id="dwz.person.AFFIRMOR_IDApplyLeave' + count + '" name="ATT_AFFIRMOR_ID" value="" type="hidden" />';
    htm +='<input id="dwz.person.EMPNAMEApplyLeave' + count + '"  type="text" style="text-align: center"  size="24" disabled="disabled"/>';
    htm +='</td>';        
	htm +='<td class="td_type" style="text-align: center" width="20%">';
	htm +='<input id="dwz.person.AFFIRMOR_IDApplyLeave' + count + '" name="ATT_AFFIRMOR_ID" value="" type="hidden"/>';
	htm +='<input id="dwz.person.EMPINFOApplyLeave' + count + '" name="empid" type="text" style="text-align: center" alt="<spring:message code="evs.affirm.please_input_enter.e"/>" size="25" class="required" lookupGroup="person" onkeydown="submitKeyClick_applyLeave(this,' + count + ',event)"/>';
	htm +='</td>';
	htm +='<td class="td_type" style="text-align: center" width="20%">';
	htm +='<input id="dwz.person.DEPTNAMEApplyLeave' + count + '"  type="text" style="text-align: center"  size="30" disabled="disabled"/>';
	htm +='</td>';
	htm +='<td class="td_type" style="text-align: center" width="20%">';
	htm +='<input id="dwz.person.POSITIONApplyLeave' + count + '"  type="text" style="text-align: center"  size="30" disabled="disabled"/>';
	htm +='</td>';
	htm +='<td class="td_type" style="text-align: center" width="15%">';
	htm +='<img src="/resources/images/+.gif" title="<spring:message code="button.add"/>"';	
	htm +='border="0" align="absmiddle" style="cursor:hand" onclick="addRowByIDApplyLeave(' + count + ')"/>&nbsp;&nbsp;&nbsp;';
	htm +='<img src="/resources/images/-.gif" title="<spring:message code="button.delete"/>"';	
	htm +='border="0" align="absmiddle" style="cursor:hand" onclick="javaScript:document.all.addApplyLeaveAffirm_list.deleteRow(event.srcElement.parentElement.parentElement.rowIndex);changeApplyLeaveLevel();"/></td></tr>';

   	//当前行之后插入一行
   	$("#rowIdApplyLeave" + currentRowID,navTab.getCurrentPanel()).after(htm);
  	$("[id='dwz.person.EMPINFOApplyLeave" + count + "']",navTab.getCurrentPanel()).inputAlert();
   	changeApplyLeaveLevel();
  	$("#applyLeaveCount").val(++count) ;
}

/*修改裁决者等级*/
function changeApplyLeaveLevel(){
	var tb2 = document.getElementById("addApplyLeaveAffirm_list");
	var rowCount = tb2.rows.length;
	for(var m=0;m<rowCount;m++){
		tb2.rows[m].cells[0].innerHTML = m+1;
	}
}

var keyCodeInit=0;
function submitKeyClick_applyLeave(obj,index,event){
	var localName = '';
	var idcardNo = '';
	var navTabId = '';
 	var e= event ? event : window.event; 
 	var keyCode = e.which ? e.which : e.keyCode;
   	if(keyCode==13){
   		keyCodeInit=keyCode;
		var empid=obj.value.replace(/[ ]/g,"");		
		var empIdStr=obj.id.substring(11);
		var personIdStr="AFFIRMOR_IDApplyLeave"+empIdStr.substring(17);
		var empNameStr = "EMPNAMEApplyLeave"+empIdStr.substring(17);
		var positionIdStr = "POSITIONApplyLeave"+empIdStr.substring(17);
		var deptIdStr = "DEPTNAMEApplyLeave"+empIdStr.substring(17);
		if(empid == ''){
			obj.value=" ";
			document.getElementById("onck").href=encodeURI(encodeURI("/ar/attendanceMintenance/viewAddAffirmList?isEmployeement=1&firstFlag=1&limit=super&pageNum=1"
					+'&seach_KEY='+empid
					+'&empidStr='+empIdStr
					+'&personidStr='+personIdStr
					+'&positionIdStr='+positionIdStr
					+'&deptIdStr='+deptIdStr
					+'&empNameStr='+empNameStr					
					));
			document.getElementById("onck").click();
		}else{
	   		$.ajax({
				type: 'POST',
				url: encodeURI('/sys/affirm/getPersonCntByEmpid?EMPID='+empid ),
				dataType:"json",
				cache: false,
				success: function(jsonObject){
							if(jsonObject.perCnt != 1 ){
								document.getElementById("onck").href=encodeURI(encodeURI("/ar/attendanceMintenance/viewAddAffirmList?isEmployeement=1&limit=all&pageNum=1"
										+'&seach_KEY='+empid
										+'&empidStr='+empIdStr
										+'&personidStr='+personIdStr
										+'&positionIdStr='+positionIdStr
										+'&deptIdStr='+deptIdStr 
										+'&empNameStr='+empNameStr));
								document.getElementById("onck").click();
							}
							if(jsonObject.perCnt==1){
								$("[id='dwz.person.EMPNAMEApplyLeave" + index + "']").val(jsonObject.empName);
							  	$("[id='dwz.person.EMPINFOApplyLeave" + index + "']").val(jsonObject.empId);
							  	$("[id='dwz.person.AFFIRMOR_IDApplyLeave" + index + "']").val( jsonObject.personId);
							  	$("[id='dwz.person.POSITIONApplyLeave" + index + "']").val( jsonObject.POSITION_NAME);
							  	$("[id='dwz.person.DEPTNAMEApplyLeave" + index + "']").val( jsonObject.deptName);
							}
						},
				error: DWZ.ajaxError
			});
		}
    }
 }
/*
function getMultipleBirths(obj,flag){
	if(flag = 1){
		if($("#MATERNITY_LEAVE",navTab.getCurrentPanel()).val()=='14016136'){
			$("#MULTIPLE_BIRTHS_SPAN",navTab.getCurrentPanel()).css("display","");
		}else{
			$("#MULTIPLE_BIRTHS_SPAN",navTab.getCurrentPanel()).css("display","none");
		}
	}
	var leave_from_date = $("#viewSSTApplyAttendance_form input[id='LEAVE_FROM_TIME']").val();
	var fromTime = $("#viewSSTApplyAttendance_form input[id='fromTime']").val();
	var leavefromtime = leave_from_date + " " + fromTime + ":" + "00";
	var chanJiaType =  $("#MATERNITY_LEAVE",navTab.getCurrentPanel()).val();
	var duoBaoTai = $("#MULTIPLE_BIRTHS",navTab.getCurrentPanel()).val();
	if(chanJiaType == ''){
		chanJiaType = 0;
	}
	if(duoBaoTai == ''){
		duoBaoTai = 0;
	}
	$.ajax({
		cache: false,
		 type: 'post',
		 async:false,
		 url: '/hrm/recruitManage/doSql',
		 data:{sql:"SELECT GET_AR_SHENGYU_CHANJIA_DAYS('"+leavefromtime+"','${LoginUser.adminID}','${LoginUser.cpnyId}',"+chanJiaType+","+duoBaoTai+") SHENGYU_CHANJIA_DAYS,TO_CHAR(TO_DATE('"+leave_from_date+"','yyyy.mm.dd')+GET_AR_SHENGYU_CHANJIA_DAYS('"+leavefromtime+"','${LoginUser.adminID}','${LoginUser.cpnyId}',"+chanJiaType+","+duoBaoTai+")-1,'yyyy.mm.dd') END_DATE FROM DUAL"},
		 dataType:"json",
		 success: function(data) {
				var shengYu = data.result[0].SHENGYU_CHANJIA_DAYS;
				var endDate = data.result[0].END_DATE;
				$("#chanJiaShengYu",navTab.getCurrentPanel()).html(shengYu);
				$("#chanJiaShengYuDays",navTab.getCurrentPanel()).val(shengYu);
				$("#viewSSTApplyAttendance_form input[id='LEAVE_TO_TIME']",navTab.getCurrentPanel()).val(endDate);
			},
		 error: DWZ.ajaxError,
	});
	callength();
}
*/
</script>
<%@ include file="/WEB-INF/view/hrm/empinfo/viewPersonalInfoHead_ess.jsp"%>
<div class="pageContent" style="padding-top:5px;">
		<form id="viewSSTApplyAttendance_form" method="post" action="/ess/infoApplyLeave/addLeaveApplySST">
			<div>
							<table  class="user_table" width="100%">
								<tr>
								   <td style="text-align:right" width="20%" class="td_title">
										<!--考勤类型 --> <spring:message code="ess.infoApply.attendance_type" />
									</td>
									<td width="30%" class="td_type">
									 	<input type="hidden" id="APPLY_AFFIRM_FLAG"  name="APPLY_AFFIRM_FLAG" value="14014306"/>
									 	<input type="hidden" id="APPLY_TYPE_NO"  name="APPLY_TYPE_NO" value="21"/>
									 	<input type="hidden" id="APPLY_FLAG"  name="APPLY_FLAG" value="1"/>
									 	<input type="hidden" id="APPLY_TYPE"  name="APPLY_TYPE" value="PERSON"/>
					                    <ait:SelectSyCodeByCpnyID name="APPLY_TYPE_CODE" parentNo="21" selected="" limit="all" onChangeName="getAffirmor()"/>
									</td>
									<td width="50%" class="td_type" colspan=2>
									    <div id="view_vac_sub" style="display:none;">
											<!--年假总天数 --><spring:message code="ess.infoApply.nianjiazongtianshu" />:${empVacInfo.TOT_VAC_CNT }/&nbsp;
											<!--生成年假数 --><spring:message code="ar.viewVacEmpList.SHENGCHENGNIANJIA.b" />:${empVacInfo.YEAR_VAC_CNT }/&nbsp;
											<!--移年年假数 --><spring:message code="ar.viewVacEmpList.YINIANNIANJIA.b" />:${empVacInfo.LAST_YEAR_VAC }/&nbsp;
											<!--特殊年假数 --><spring:message code="ar.viewVacEmpList.TESHUNIANJIA.b" />:${empVacInfo.ADD_VAC }/&nbsp;
											<!--已使用天数--> <spring:message code="ar.viewArAdjustRest.title.DAYSUSEED" />:${empVacInfo.USE_VAC}/&nbsp;
											<!--剩余天数 --> <spring:message code="ar.viewArAdjustRest.title.shengyutianshu" />:${empVacInfo.TOT_VAC_CNT - empVacInfo.USE_VAC}
									   	</div>
									</td>
								</tr>
								<tr>
									<td style="text-align:right" width="20%" class="td_title">
										<!--开始时间 --> <spring:message code="ess.infoApply.title.startTime" />
									</td>
									<td width="80%" class="td_type" colspan=3>
										<span id="leave_date_default_start"></span>
										<input type="text" name="LEAVE_FROM_TIME" id="LEAVE_FROM_TIME" value="${APPLY_DATE }" class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en',onpicked:callength})" />
										<input type="hidden" name="fromTime" id="fromTime" value="08:00"/>
										<select id="fromTime_shi" name="fromTime_shi" onchange="composeLeaveTime();">
											<option value="00">00</option>
											<option value="01">01</option>
											<option value="02">02</option>
											<option value="03">03</option>
											<option value="04">04</option>
											<option value="05">05</option>
											<option value="06">06</option>
											<option value="07">07</option>
											<option value="08"  selected="selected">08</option>
											<option value="09">09</option>
											<option value="10">10</option>
											<option value="11">11</option>
											<option value="12">12</option>
											<option value="13">13</option>
											<option value="14">14</option>
											<option value="15">15</option>
											<option value="16">16</option>
											<option value="17">17</option>
											<option value="18">18</option>
											<option value="19">19</option>
											<option value="20">20</option>
											<option value="21">21</option>
											<option value="22">22</option>
											<option value="23">23</option>
										</select>&nbsp;:
										<select id="fromTime_fen" name="fromTime_fen" onchange="composeLeaveTime();">
											<option value="00" selected="selected">00</option>
											<option value="02">02</option>
											<option value="10">10</option>
											<option value="15">15</option>
											<option value="17">17</option>
											<option value="30">30</option>
											<option value="32">32</option>
											<option value="45">45</option>
											<option value="47">47</option>
										</select>
									</td>
									
								</tr>
								<tr>
									<td style="text-align:right" width="20%" class="td_title">
										<!--结束时间 --> <spring:message code="ess.infoApply.end_time" />
									</td>
									<td width="80%" class="td_type" colspan=3>
										<input type="text" name="LEAVE_TO_TIME" id="LEAVE_TO_TIME"  value="${APPLY_DATE }" class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en',onpicked:callength})"/>
										<input type="hidden" name="toTime" id="toTime" value="17:00"/>
										<select id="toTime_shi" name="toTime_shi" onchange="composeLeaveTime();">
											<option value="00">00</option>
											<option value="01">01</option>
											<option value="02">02</option>
											<option value="03">03</option>
											<option value="04">04</option>
											<option value="05">05</option>
											<option value="06">06</option>
											<option value="07">07</option>
											<option value="08">08</option>
											<option value="09">09</option>
											<option value="10">10</option>
											<option value="11">11</option>
											<option value="12">12</option>
											<option value="13">13</option>
											<option value="14">14</option>
											<option value="15">15</option>
											<option value="16">16</option>
											<option value="17"  selected="selected">17</option>
											<option value="18">18</option>
											<option value="19">19</option>
											<option value="20">20</option>
											<option value="21">21</option>
											<option value="22">22</option>
											<option value="23">23</option>
										</select>&nbsp;:
										<select id="toTime_fen" name="toTime_fen" onchange="composeLeaveTime();">
											<option value="00" selected="selected">00</option>
											<option value="02">02</option>
											<option value="10">10</option>
											<option value="15">15</option>
											<option value="17">17</option>
											<option value="30">30</option>
											<option value="32">32</option>
											<option value="45">45</option>
											<option value="47">47</option>
										</select>
									</td>
									
								</tr>
								<tr>
									<td style="text-align:right" width="20%" class="td_title">
										<!--申请时长 --> <spring:message code="ar.viewArAdjustRest.title.APPLYLENGTH" />
									</td>
									<td width="30%" class="td_type" colspan="3">
										<div id="shenqingshichangText">1<!--天 --> <spring:message code="ar.viewitemparameter.title.dayofunit" /></div>
										<input type="hidden" id="shenqingshichang" name="APPLY_LENGTH" value="8" onchange="getAffirmor()"/>
										<input type="hidden" id="shenchangFormatHour" name="shenchangFormatHour" value="8" onchange="getAffirmor()"/>
										<input type="hidden" id="dayHour" name="dayHour" value="4" onchange="getAffirmor()"/>
									</td>
								</tr>
								<!--<tr>
									<td style="text-align:right" width="20%" class="td_title">
										休假说明  <spring:message code="ess.infoApply.xiujiashuoming" />
									</td>
									<td width="30%" class="td_type" colspan="3">
										<span style="color: red;" id = "LEAVE_EXPLAIN"><spring:message code="ess.infoApplyAttendance.PAID_ANNUAL_LEAVE.Z" /> 公司实行带薪年休假制度，连续工作满12个月以上的员工可享受带薪休假。 </span>
									</td>
								</tr>
								-->
								<tr>
									<td style="text-align:right" width="20%" class="td_title">
										<!--原因 --> <spring:message code="ess.infoApply.Reason" />
									</td>
									<td width="80%" class="td_type" colspan="3">
										<textarea name="LEAVE_REASON"  style="width:500px;height:80px;resize: none;"></textarea>
									</td>
								</tr>
							</table>
							<div style="font:10px;float:left;height:20px;line-height:20px;">
								<a class="w_button" href="#" onclick="uploadAttDialogInsert_new('<spring:message code="js.upload.msg.fileToUpload" />');"><span><spring:message code="hrm.recruitManage.ATTACHED_FILE" /><!-- 附加文件 --></span></a>
								<a class="w_button" href="#" onclick="deleteAttListInsert_new('<spring:message code="js.upload.msg.firstSelectDelete" />');"><span><spring:message code="public.title.delete" /><!-- 删除 --></span></a>
							</div>
							 <table id="fileTable" class="list" width="100%">
								<thead>
									<tr>
										<th width="10%">V</th>
										<th width="90%"><spring:message code="hr.viewBadArchives.title.FILE" /><!-- 附件 --></th>
									</tr>
								</thead>
								<tbody>
								</tbody>
							</table>
							</br> 
							<table class="user_table" width="100%">	
								<tr>
								    <td class="td_title"  style="text-align:center;" width="5%"><!--序号 --> <spring:message code="org.title.NO" /></td>
									<td class="td_title"  style="text-align:center;" width="20%"><!--决裁者 --> <spring:message code="sys.affirm.title.affirmPerson" /></td>
									<td class="td_title" style="text-align:center;" width="20%"><!--社号--> <spring:message code="hr.enpinfo.title.EMP.EMPNUMBER" /></td>
									<td class="td_title"  style="text-align:center;" width="20%"><!--部门 --> <spring:message code="hr.viewPersonalInfo.title.DEPTNAME" /></td>
									<td class="td_title" style="text-align:center;" width="20%"><!--职责 --> <spring:message code="hrm.contract.POSITION_NO" /></td>
									<c:if test="${LoginUser.cpnyId eq 'HAE'}">
								    <td class="td_title" style="text-align:center;" width="15%"><!--是否新增 --> <spring:message code="evs.viewRegPersonalProbation.SHIFOUXINZENG.a" />(<img src="/resources/images/+.gif" title="<spring:message code="button.add"/>" border="0" align="absmiddle" style="cursor:hand" onclick="addRowByIDApplyLeaveFirst()"/>)</td>
								    </c:if>
								</tr>
								<tr>
									<td colspan="6">
										<table width="100%" border="0" cellpadding="0" cellspacing="0" id="addApplyLeaveAffirm_list">
											<tbody id="rowIdApplyLeaveq">
											</tbody>
										</table>
				    				<input type="hidden" name="applyLeaveCount" id="applyLeaveCount" value="1">
				    				<a id="onck" name="onck"  href="" lookupGroup="person" rel="submitKeyClick_apply_Leave_affirm"></a>
				    				</td>
		    					</tr>
		    				</table>
			</div>
			<div class="formBar">
				<ul>
					<li>
						<div class="button">
							<div class="buttonContent">
								<button type="button" id="viewSSTApplyAttendance_save">
									<!--申请 --> <spring:message code="ess.infoApply.title.apply" />
								</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
	  	</form>	
</div>