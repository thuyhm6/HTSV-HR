<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.easydrag.js"></script>
<script type="text/javascript">
 
 
function getDefaultShiftTime(){
   
	var  datetype = $("#viewPOtApplyInfo input[name='dateType']").val();
    var personId =$("#PERSON_ID",navTab.getCurrentPanel()).val();
	var fromDate = $("#FROM_DATE",navTab.getCurrentPanel()).val();
	var CPNY_ID =$("#CPNY_ID",navTab.getCurrentPanel()).val();
	if($("#viewPOtApplyInfo input[name='FROM_DATE']").val()!= ""){
	
		$.ajax({
			 cache: false,
			 type: 'post',
			 async:false,
			 url: "/ess/infoApply/getDefaultStartEndTime",
			 data: [{ name : 'APPLY_OT_DATE' , value :fromDate },
			        { name : 'CPNY_ID' , value :CPNY_ID },
			        { name : 'PERSON_ID' , value :personId}],
			 dataType:"json",
			 success: function(data) {
			    if(datetype == '1440'){
					 $("#OT_FROM_TIME").val(data.START_TIME);
					 $("#OT_TO_TIME").val(data.END_TIME);
					 $("#OT_FROM_TIME_FLAG").val(data.START_TIME);
					 $("#OT_TO_TIME_FLAG").val(data.END_TIME);
					 $("#workTime").html(data.WORK_TIME);
					
			    }
			     if(datetype == '1441' || datetype == '1442'){
			         var aa = data.START_TIME.substr(0,4);
			         var bb= data.START_TIME.substr(0,4);
					 $("#OT_FROM_TIME").val(aa);
					 $("#OT_TO_TIME").val(bb);
					 $("#OT_FROM_TIME_FLAG").val(aa);
					 $("#OT_TO_TIME_FLAG").val(bb);
					 $("#workTime").html(aa+'-'+bb);
			    }
			     $("#IN_DOOR").html(data.INDOOR_DATE);
				 $("#OUT_DOOR").html(data.OUTDOOR_DATE);
			 }
		});
	}
 if($("#viewPOtApplyInfo input[name='FROM_DATE']").val()!= ""){
	    var from_date = $("#viewPOtApplyInfo input[id='OT_APPLY_DATE']").val();
		if(from_date==null ||from_date==""){
		   from_date = $("#viewPOtApplyInfo input[id='FROM_DATE']").val();
		}
	    var cpnyId = document.getElementById("CPNY_ID").value;
	    var fromTime1 = $("#viewPOtApplyInfo input[id='OT_FROM_TIME']").val();
	    
	    var fromTime = fromTime1.substr(0,2)+":"+fromTime1.substr(2,2); 
	    var toTime1 = $("#viewPOtApplyInfo input[id='OT_TO_TIME']").val();
	    
	    var toTime =toTime1.substr(0,2)+":"+toTime1.substr(2,2);
		var personId =$("#PERSON_ID",navTab.getCurrentPanel()).val();
		$("#OT_FROM_TIME_FLAG").val(fromTime);
		var otfromtime = from_date + " " + fromTime + ":" + "00";
		var ottotime = from_date + " " + toTime + ":" + "00";
		
		var dateTypeId = $("#viewPOtApplyInfo input[name='dateType']").val(); 
		var applytypecode=$("#viewPOtApplyInfo input[name='APPLY_TYPE_CODE']").val();
		
		if(from_date!=null&&from_date!=""){
	
			$.ajax({
				 cache: false,
				 type: 'post',
				 async:false,
				 url: "/ess/infoApply/getOtApplyLengthWq2",
				 data: [
				        { name: 'OT_FROM_TIME', value: otfromtime },
				        { name: 'FROM_TIME', value: fromTime },
				        { name: 'PERSON_ID', value: personId },
				        { name: 'DATE_TYPE', value: dateTypeId },
				        { name: 'APPLY_TYPE_CODE', value: applytypecode },
				        { name: 'APPLY_OT_DATE', value: from_date },
				        { name: 'OT_TO_TIME', value: ottotime },
				        { name: 'TO_TIME', value: toTime }
				        ],
				 dataType:"json",
				  success: function(data) {
				  var hour = data.OT_HOUR;
				  var min = data.OT_MINUTE;
				   document.getElementById('otApplyLength').innerHTML = hour+"小时"+min+"分";
				   $("#Lotlengthone").val((hour*60+min)/60);
				   $("#Lotlengthonehour").val(hour);
				   $("#Lotlengthonemin").val(min);
				 }
			});
			}
	  }	
}
//获取加班上限
function getOtLimit(){
    var personId =$("#PERSON_ID",navTab.getCurrentPanel()).val();
	var fromDate = $("#FROM_DATE",navTab.getCurrentPanel()).val();
	if($("#viewPOtApplyInfo input[name='FROM_DATE']").val()!= ""){
	
		$.ajax({
			 cache: false,
			 type: 'post',
			 async:false,
			 url: "/ess/infoApply/getOtLimit",
			 data: [{ name : 'APPLY_OT_DATE' , value :fromDate },
			        { name : 'PERSON_ID' , value :personId}],
			 dataType:"json",
			 success: function(data) {
			    $('#ot_total').val(data.OT_TOTAIL);
			    $('#ot_limit').val(data.OT_LIMIT);
			    $('#ot_total_html').html(data.OT_TOTAIL);
			    $('#ot_limit_html').html(data.OT_LIMIT);
			 }
		});
	}
} 


function getChangeOtType(){
	var  fromdate =$("#viewPOtApplyInfo input[name='FROM_DATE']").val();
	var cpnyId = document.getElementById("CPNY_ID").value;
    var ot_from_time_ps = '';
   
    var ot_from_time_ps = $("#viewPOtApplyInfo select[id='OT_FROM_TIME']").val();
	var  emptype_code = $("#viewPOtApplyInfo input[name='EMP_TYPE_CODE']").val();
	var teshuYn = $("#viewPOtApplyInfo input[name='TESHU_YN']:checked").val();
	var cpny_id = $("#viewPOtApplyInfo input[name='CPNY_ID']").val();
	var  datetype = $("#viewPOtApplyInfo input[name='dateType']").val();
	var peronsid = $("#viewPOtApplyInfo input[name='PERSON_ID']").val();
	var otfromtime =    fromdate + " " + ot_from_time_ps + ":" + "00";
	var adjustYn = $("#viewPOtApplyInfo input[name='ADJUST_YN']:checked").val();//调休选中的值
	var endDayOffset = $("#viewPOtApplyInfo input[name='END_DAY_OFFSET']:checked").val();
	var otPlaceType = $("#viewPOtApplyInfo select[id='OT_PLACE_TYPE']").val();
 
	if($("#viewPOtApplyInfo input[name='FROM_DATE']").val()!= ""){
		$.ajax({
			 cache: false,
			 type: 'post',
			 async:false,
			 url: "/ess/infoApply/getChangeOtType",
			 data: [{ name : 'APPLY_OT_DATE' , value :fromdate },
			        { name : 'FROM_TIME' , value :otfromtime },
			        { name : 'EMP_TYPE_CODE' , value :emptype_code },
			        { name : 'TESHU_YN' , value :teshuYn},
			        { name : 'OT_PLACE_TYPE' , value :otPlaceType},
			        { name : 'END_DAY_OFFSET' , value :endDayOffset},   
			        { name : 'ADJUST_YN' , value :adjustYn},
			        { name : 'PERSON_ID' , value : peronsid}],
			 dataType:"json",
			 success: function(data) {
			 $("#OT_APPLY_DATE").val(data.OT_APPLY_DATE);
			 $("#APPLY_TYPE_CODE_NEW").val(data.OT_TYPE);
			 $("#APPLY_TYPE_CODE").val(data.OT_TYPE);
				    
			 }
		});
	}
		   
}
function compareTime(){
	var  emptype_code = $("#viewPOtApplyInfo input[name='EMP_TYPE_CODE']").val();
	var cpny_id = $("#viewPOtApplyInfo input[name='CPNY_ID']").val();
	var teshuYn = $("#viewPOtApplyInfo input[name='TESHU_YN']:checked").val();
	var  fromdate =$("#viewPOtApplyInfo input[name='FROM_DATE']").val();
	var cpnyId = document.getElementById("CPNY_ID").value;
    var ot_from_time_ps = '';
    
    ot_from_time_ps = $("#viewPOtApplyInfo select[id='OT_FROM_TIME']").val();
	return true;
}
function comptime1() {
    var cpnyId = document.getElementById("CPNY_ID").value;
    var beginTime = '';
    if(cpnyId == 'LGEQH'){
        beginTime = document.getElementById("OT_FROM_TIME_H").value + ":" +
        document.getElementById("OT_FROM_TIME_M").value;
    }else{
        beginTime = $("#OT_FROM_TIME").val();
    }
    var beginTimeFlag = $("#OT_FROM_TIME_FLAG").val();
    var END_DAY_OFFSET = $("#viewPOtApplyInfo input[name='END_DAY_OFFSET']:checked").val();;

    var beginTimeHH = beginTime.substring(0, 2);
    var beginTimeFlagHH = beginTimeFlag.substring(0, 2);
    var beginTimeMM = beginTime.substring(3, 4);
    var beginTimeFlagMM = beginTimeFlag.substring(3, 4);
	if(END_DAY_OFFSET == 0){
	    if (beginTimeHH < beginTimeFlagHH) {
			alertMsg.error("加班开始时间不能早于" + beginTimeFlag);
	        return false;
	    } else if(beginTimeHH == beginTimeFlagHH){
	        if(beginTimeMM < beginTimeFlagMM ){
	    		alertMsg.error("加班开始时间不能早于" + beginTimeFlag);
	            return false;
	        }
	    }
	}
    return true;
}
var ajaxGet_add_ot_apply_three;
//获取裁决线
function ajaxAdd_add_ot_apply_one_three() {

	if (ajaxGet_add_ot_apply_three != null) {
		ajaxGet_add_ot_apply_three.abort();
	}

    var otApplyHour   = document.getElementById("Lotlengthone").value;
    	
    
    var applytypecode=$(":input[name='APPLY_TYPE_CODE']").val();
    var applynew  = document.getElementById("APPLY_TYPE_CODE_NEW").value;
  
    if(applytypecode==""||applytypecode==null){
    	applytypecode =applynew;
        }
    var PERSON_ID = $("#PERSON_ID",navTab.getCurrentPanel()).val();
   
 	if(PERSON_ID != '' && otApplyHour != '' && applytypecode != ''){
  		
	 
 		var adjustYn = $("#viewPOtApplyInfo input[name='ADJUST_YN']:checked").val();
 		
		$.ajaxSettings.global = false;
		
		ajaxGet_add_ot_apply_three = $.ajax( {
			type : "POST",
			url : "/ess/infoApply/getAffirmList",
			data : {applyParentType : '31',applyType : applytypecode,applyLength : otApplyHour,personId : $("#PERSON_ID").val(),ADJUST_YN:adjustYn},
			dataType : "json",
			success : function(data) {
				$('#addApplyLOTAffirm_list tbody').html("");
				var html = "";
				if (typeof (data['affirmList']) != "undefined") {
					$.each(data['affirmList'],
									function(commentIndex, comment) {
										html += '<tr id="rowIdApplyLot' + commentIndex  + '">';
										html += '<td class="td_type" style="text-align: center" width="5%">' + (commentIndex + 1) + '</td>';
										html += '<td class="td_type" style="text-align: center" width="20%">审批 </td>';
										html += '<td class="td_type" style="text-align: center" width="25%">[' + comment['EMPID']
												+ ']-' + comment['LOCAL_NAME'];
										html += '<input type="hidden" name="AFFIRMOR_ID" value="' + comment['AFFIRMOR_ID'] + '"/></td>';
								        html += '<td class="td_type" style="text-align: center" width="25%">' + comment['LOCAL_NAME'] + '/'+comment['POSTIONNAME']+'/'+comment['DEPTNAME']+'</td>';
										html += '<td class="td_type" style="text-align: center" width="25%">';
										html += '<img src="/resources/images/+.gif" title="添加" border="0" align="absmiddle" style="cursor:hand" onclick="addRowByIDLTwo('+ commentIndex +')"/>';
										html += '</td>';
										$("#affirmorListCnt").val(commentIndex + 1);
									});
				}
				$('#addApplyLOTAffirm_list tbody').html(html);
			}
		});
		$.ajaxSettings.global = true;
	}
}
//添加决裁者
function addRowByIDLTwo(currentRowID){
	var count = parseInt($("#affirmorListCnt").val());
    var htm  ='<tr id="rowIdApplyLot'+ count +'"><td class="td_type" style="text-align: center" width="5%"><span name="rowIndex"></span></td>';
         htm +='<td class="td_type" style="text-align: center" width="20%">';
	    htm +='<input type="radio" id="approvType' + count + '" name="approvType' + count + '" value="1001" checked="checked" />审批 ';
	    htm +='<input type="radio" id="approvType' + count + '" name="approvType' + count + '" value="1002" />协议'; 
	    htm +='<input type="radio" id="approvType' + count + '" name="approvType' + count + '" value="1003" />通报';
	    htm +='</td>';
		htm +='<td class="td_type" style="text-align: center" width="25%">';
		htm +='<input id="AFFIRMOR_IDApplyLot' + count + '" name="AFFIRMOR_ID" value="" type="hidden"/>';
		htm +='<input id="dwz.person.LotpersonId'+count+'" name="AFFIRMOR_ID" value="" type="hidden" lookupGroup="person"/>';
		htm +='<input id="dwz.person.LotempName'+count+'" name="empid" value="" type="text" lookupGroup="person"  lookupGroup="person" onkeydown="submitKeyClick_affirmorP(this,' + count + ',event)" class="required"/>';
		htm +='</td>';
		htm +='<td class="td_type" style="text-align: center" width="25%">';
		htm +='<input id="dwz.person.InfoLotempName' + count + '"  type="text"  size="40" disabled="disabled"/>';
		htm +='</td>';
		htm +='<td class="td_type" style="text-align: center" width="25%">';
		htm +='<img src="/resources/images/+.gif" title="添加"';	
		htm +='border="0" align="absmiddle" style="cursor:hand" onclick="addRowByIDLTwo(' + count + ')"/>&nbsp;&nbsp;&nbsp;';
		htm +='<img src="/resources/images/-.gif" title="删除"';	
		htm +='border="0" align="absmiddle" style="cursor:hand" onclick="javaScript:document.all.addApplyLOTAffirm_list.deleteRow(event.srcElement.parentElement.parentElement.rowIndex);changeApplyOtLevel();"/></td></tr>';

   	//当前行之后插入一行
   	$("#rowIdApplyLot" + currentRowID).after(htm);
  	$("[id='dwz.person.LotempName" + count + "']").attr("alt","请输入关键字按回车检索").attr("size","30").inputAlert();
   	changeApplyOtLevel();
  	$("#affirmorListCnt").val(++count) ;
}


function addRowByIDApplyPOTFirst(){
	var count = parseInt($("#affirmorListCnt").val());
    var htm  ='<tr id="rowIdApplyLot'+ count +'"><td class="td_type" style="text-align: center" width="5%"><span name="rowIndex"></span></td>';
    htm +='<td class="td_type" style="text-align: center" width="20%">';
    htm +='<input type="radio" id="approvType' + count + '" name="approvType' + count + '" value="1001" checked="checked" />审批 ';
    htm +='<input type="radio" id="approvType' + count + '" name="approvType' + count + '" value="1002" />协议'; 
    htm +='<input type="radio" id="approvType' + count + '" name="approvType' + count + '" value="1003" />通报';
    htm +='</td>';
	htm +='<td class="td_type" style="text-align: center" width="25%">';
	htm +='<input id="dwz.person.LotpersonId'+count+'" name="AFFIRMOR_ID" value="" type="hidden" lookupGroup="person"/>';
	htm +='<input id="dwz.person.LotempName'+count+'" name="empid" value="" type="text" lookupGroup="person"  lookupGroup="person" onkeydown="submitKeyClick_affirmorP(this,' + count + ',event)" class="required"/>';
	htm +='</td>';
	htm +='<td class="td_type" style="text-align: center" width="25%">';
	htm +='<input id="dwz.person.InfoLotempName' + count + '"  type="text"  size="40" disabled="disabled"/>';
	htm +='</td>';
	htm +='<td class="td_type" style="text-align: center" width="25%">';
	htm +='<img src="/resources/images/+.gif" title="添加"';	
	htm +='border="0" align="absmiddle" style="cursor:hand" onclick="addRowByIDLTwo(' + count + ')"/>&nbsp;&nbsp;&nbsp;';
	htm +='<img src="/resources/images/-.gif" title="删除"';	
	htm +='border="0" align="absmiddle" style="cursor:hand" onclick="javaScript:document.all.addApplyLOTAffirm_list.deleteRow(event.srcElement.parentElement.parentElement.rowIndex);changeApplyOtLevel();"/></td></tr>';


	var tb2 = document.getElementById("addApplyLOTAffirm_list");

   	if(tb2.rows.length == 0){
   		$("#addApplyLOTAffirm_list:last tbody").html(htm);
   	} else {
   	   	//当前行之后插入一行
   	   	$("#" + tb2.rows[0].id).before(htm);
   	}
  	$("[id='dwz.person.LotempName" + count + "']").attr("alt","请输入关键字按回车检索").attr("size","30").inputAlert();
   	changeApplyOtLevel();
  	$("#affirmorListCnt").val(++count) ;
}

//修改决裁者等级
function changeApplyOtLevel(){
	var tb2 = document.getElementById("addApplyLOTAffirm_list");
	var rowCount = tb2.rows.length;
	for(var m=0;m<rowCount;m++){
		tb2.rows[m].cells[0].innerHTML = m+1;
	}
}

//<!--
function submitPFormPre(flag){
	 
	 $("#viewPOtApplyInfo input[id='AFFIRM_FLAG']").val(flag);
  	var $from = $("#viewPOtApplyInfo");
  	$from.submit();
}
function validatePOvertimeApplyCallback(form,callback) {	
	var $form = $(form);	
	 
	var otTimeType  = document.getElementById("OT_TIME_TYPE").value;
    var person_id  = document.getElementById("PERSON_ID").value;
    var applyTypeNo = document.getElementById("APPLY_TYPE_NO").value;
    var applyTypeCode = document.getElementById("APPLY_TYPE_CODE_NEW").value;
    var Lotlengthone = document.getElementById("Lotlengthone").value;
    var ot_total = document.getElementById("ot_total").value;
    var ot_limit = document.getElementById("ot_limit").value;
    var empTypeCode = document.getElementById("EMP_TYPE_CODE").value;
    var cpnyId = document.getElementById("CPNY_ID").value;
    var OTLOCKYN;
   // var TESHU_YN = $("#viewPOtApplyInfo input[name='TESHU_YN']:checked").val();

    var applyTypeCode2 = document.getElementById("APPLY_TYPE_CODE");
    applyTypeCode2.disabled=false;
   	var fromDate  = document.getElementById("FROM_DATE").value;
    var toDate    = "";
    var  emptype_code = $("#viewPOtApplyInfo input[name='EMP_TYPE_CODE']").val();
    if(emptype_code==''||emptype_code==null){
			alert("申请失败：您的人员类型是空的，请联系考勤担设置您的人员类型");
		return false;
      }
	var  datetype = $("#viewPOtApplyInfo input[name='dateType']").val();
	 var otFromTime = '';
    otFromTime = document.getElementById("OT_FROM_TIME").value;
    var otToTime = '';
    otToTime = document.getElementById("OT_TO_TIME").value;
    //获取锁定标识
    	
    	$.ajax({
			cache: false,
		    type: 'post', 
			async:false,
			url: "/ess/infoApplyAttendance/getOTLOCKYN",
			data: [
				{ name: 'applyBatchdate', value: fromDate }],
			dataType:"json",
			success: function(data) {
			
			   OTLOCKYN = data.OTLOCKYN;
			}
		});
    //个人加班锁定
    if(OTLOCKYN=="Y"){
    	alertMsg.info('当前申请加班已锁定！');
    	return false;
   	}
   	//申请类型
	if(applyTypeCode==""){
       alertMsg.error('<spring:message code="alert.message.ess.infoApply.overtimeApplyTypeIsMust"/>');
       return false;
	}
	//加班时长
	if(Lotlengthone==0){
       alertMsg.error('请选择加班时长,加班时长为0');
       return false;
	}
	if(toDate==""){
       toDate = fromDate ;
    }else{
	    if(fromDate>toDate){
		   alertMsg.error('<spring:message code="alert.message.ess.infoApply.startTimeNotLaterThanEndTime"/>');
		   return false;
		}   
	} 
	//周末加班只能半天或者一天
	if(datetype=='1441'){
	  if(Lotlengthone != 4 && Lotlengthone != 8){
	       alertMsg.error('周末加班只能申请4或者8小时！');
	       return false;
      }
	}
	
	//验证是否超过加班上限
	if(document.getElementById("ADJUST_YN").checked!=true){
		if(parseFloat(ot_total)+parseFloat(Lotlengthone) > parseFloat(ot_limit)){
			 var oting = parseFloat(ot_limit) - parseFloat(ot_total)+'';
			 alertMsg.error('本月的加班上限'+ot_limit+'小时,当前已累计加班'+ot_total+'小时,当前可申请时长是'+oting+'小时!' );
			 return false;
		}
	}
			
	

	alertMsg.confirm("确定要提交吗？",
  		  	{okCall:function(){	
		$.ajax({
			type: form.method || 'POST',
			url:$form.attr("action"),
			data:$form.serializeArray(),
			dataType:"json",
			cache: false,
			success: function(json){
			 DWZ.ajaxDone(json);
			 if (json.statusCode == DWZ.statusCode.ok){
			
				if ("closeCurrent" == json.callbackType) {
					setTimeout(function(){navTab.closeCurrentTab();}, 100);
				} else if ("forward" == json.callbackType) {
					navTab.reload(json.forwardUrl);
				}
			  }
			} ,
		error: DWZ.ajaxError
		});	
	}});
	return false;
}
//比较时间 格式 yyyy-mm-dd hh:mi:ss
function comptime(beginTime,endTime){
	var beginTimes=beginTime.substring(0,10).split('-');
	var endTimes=endTime.substring(0,10).split('-');
	beginTime=beginTimes[1]+'-'+beginTimes[2]+'-'+beginTimes[0]+' '+beginTime.substring(10,19);
	endTime=endTimes[1]+'-'+endTimes[2]+'-'+endTimes[0]+' '+endTime.substring(10,19);
	
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

//比较时间 格式 yyyy-mm-dd hh:mi:ss，加班时间
function comptime2(beginTime,endTime){
	var beginTimes=beginTime.substring(0,10).split('-');
	var endTimes=endTime.substring(0,10).split('-');
	beginTime=beginTimes[1]+'-'+beginTimes[2]+'-'+beginTimes[0]+' '+beginTime.substring(10,19);
	endTime=endTimes[1]+'-'+endTimes[2]+'-'+endTimes[0]+' '+endTime.substring(10,19);
	
	var a =(Date.parse(endTime)-Date.parse(beginTime))/3600/1000;
	if(a<0){
		return -1;
	}else if (a>=1){
		return 1;
	}else if (a==0){
		return 0;
	}else{
		return 'exception';
	}
}

//获取该日期的加班类型
function getPOtApplyType(){
    var cpnyId = document.getElementById("CPNY_ID").value;
    var ot_from_time_ps = '';
    ot_from_time_ps = $("#viewPOtApplyInfo input[id='OT_FROM_TIME']").val();
    var personId =$("#PERSON_ID",navTab.getCurrentPanel()).val();
	var fromDate = $("#FROM_DATE",navTab.getCurrentPanel()).val();

	var otfromtime = fromDate + " " + ot_from_time_ps + ":" + "00";
	var CPNY_ID =$("#CPNY_ID",navTab.getCurrentPanel()).val();
	var applyTypeCode = document.getElementById("APPLY_TYPE_CODE");
	var applyTypeCodeView = document.getElementById("APPLY_TYPE_CODE_NEW");
	//var adjustYn = $("#viewPOtApplyInfo input[name='ADJUST_YN']:checked").val();
    var adjustYn = $("#viewPOtApplyInfo input[name='ADJUST_YN']");
	var emp_type_code =$("#EMP_TYPE_CODE",navTab.getCurrentPanel()).val();
    var dateTypeId = document.getElementById("dateType");
    var peopleTypeId = document.getElementById("peopleType");
 	if(fromDate!=""){
		$.ajax({
			cache: false,
			type: 'post',
			async:false,
			url: "/ess/infoApply/getDateTypeByDateAndEmpCpny?",
			data:'DDATE_STR=' + fromDate +'&PERSON_ID='+personId+'&FROM_TIME='+otfromtime,
			dataType:"json",
			success: function(data) {
				var dateType = data.TYPEID;
				var peopleType = data.PEOPLETYPEID;//个人排班编号
				dateTypeId.value=dateType;
				peopleTypeId.value=peopleType;
				if(dateType == '1440'){       //平时
					applyTypeCode.value="32";
					applyTypeCodeView.value="32";
					//调休不可用
					adjustYn.eq(0).removeAttr('checked');
					adjustYn.attr("disabled",true);
					 
				}else if(dateType == '1441'){ //周末 
				 
					applyTypeCode.value="33";
					applyTypeCodeView.value="33";
					//调休可用
					adjustYn.eq(0).removeAttr('checked');
					adjustYn.attr("disabled",false);
					

				}else if(dateType == '1442'){ //法定
					applyTypeCode.value="34";
					applyTypeCodeView.value="34";
					//调休不可用
					adjustYn.eq(0).removeAttr('checked');
					adjustYn.attr("disabled",true);
				}else{ 
					applyTypeCode.value="32";
					applyTypeCodeView.value="32";
					
					//调休不可用
					adjustYn.eq(0).removeAttr('checked');
					adjustYn.attr("disabled",true);
				}
				applyTypeCodeView.disabled=true;
			}
		});
	}
}


//核查时间
function checkTime(timeText){
    var regTime = /^([0-2][0-9])([0-5][0-9])$/;
    var result = false;
    if (regTime.test(timeText)) {
        if ((parseInt(RegExp.$1) < 24) && (parseInt(RegExp.$2) < 60)) {
            result = true;
        }
    }
    if (result) {
       
    }else {
    alert("时间格式错误,请输入合法的时间");
    return false;
    }
 return result;
}
//onkeyup
function calLength(time){  
   var timeLength = time.length;
   var hour =0;
   var min = 0;
   if(timeLength == 4) {
    var regTime = /^([0-2][0-9])([0-5][0-9])$/;
    var result = false;
    if (regTime.test(time)) {
        if ((parseInt(RegExp.$1) < 24) && (parseInt(RegExp.$2) < 60)) {
            calPoTLength();
            getOtLimit();
        }else{
          return false;
        }
    }
   }
   if(timeLength < 4) {
       document.getElementById('otApplyLength').innerHTML = hour+"小时"+min+"分";
	   $("#Lotlengthone").val((hour*60+min)/60);
	   $("#Lotlengthonehour").val(hour);
	   $("#Lotlengthonemin").val(min);
   }
   if(timeLength > 4) {
      if(checkTime(time) == false){
	   document.getElementById('otApplyLength').innerHTML = hour+"小时"+min+"分";
	   $("#Lotlengthone").val((hour*60+min)/60);
	   $("#Lotlengthonehour").val(hour);
	   $("#Lotlengthonemin").val(min);
	  }
   }
   
}
//计算时长
function calPoTLength(){
	
	var from_date = $("#viewPOtApplyInfo input[id='OT_APPLY_DATE']").val();
	if(from_date==null ||from_date==""){
	   from_date = $("#viewPOtApplyInfo input[id='FROM_DATE']").val();
	}
    var cpnyId = document.getElementById("CPNY_ID").value;
    var fromTime1= $("#viewPOtApplyInfo input[id='OT_FROM_TIME']").val();
   
    var fromTime = fromTime1.substr(0,2)+":"+fromTime1.substr(2,2); 
	var toTime1 = $("#viewPOtApplyInfo input[id='OT_TO_TIME']").val();
   
	
    var toTime =toTime1.substr(0,2)+":"+toTime1.substr(2,2);
	var personId =$("#PERSON_ID",navTab.getCurrentPanel()).val();
	$("#OT_FROM_TIME_FLAG").val(fromTime);
	var otfromtime = from_date + " " + fromTime + ":" + "00";
	var ottotime = from_date + " " + toTime + ":" + "00";
	
	var dateTypeId = $("#viewPOtApplyInfo input[name='dateType']").val(); 
	var applytypecode=$("#viewPOtApplyInfo input[name='APPLY_TYPE_CODE']").val();
	if(from_date!=null&&from_date!=""){

		$.ajax({
			 cache: false,
			 type: 'post',
			 async:false,
			 url: "/ess/infoApply/getOtApplyLengthWq2",
			 data: [
			        { name: 'OT_FROM_TIME', value: otfromtime },
			        { name: 'FROM_TIME', value: fromTime },
			        { name: 'PERSON_ID', value: personId },
			        { name: 'DATE_TYPE', value: dateTypeId },
			        { name: 'APPLY_TYPE_CODE', value: applytypecode },
			        { name: 'APPLY_OT_DATE', value: from_date },
			        { name: 'OT_TO_TIME', value: ottotime },
			        { name: 'TO_TIME', value: toTime }
			        ],
			 dataType:"json",
			  success: function(data) {
			  var hour = data.OT_HOUR;
			  var min = data.OT_MINUTE;
			   document.getElementById('otApplyLength').innerHTML = hour+"小时"+min+"分";
			   $("#Lotlengthone").val((hour*60+min)/60);
			   $("#Lotlengthonehour").val(hour);
			   $("#Lotlengthonemin").val(min);
			 }
		});
		}
}

//获取加班的人事政策
function calPoTRemark(){
	var otTypeCode = $("#viewPOtApplyInfo select[id='APPLY_TYPE_CODE_NEW']").val();
	var otPlaceType = $("#viewPOtApplyInfo select[id='OT_PLACE_TYPE']").val();
	var adjustYn = $("#viewPOtApplyInfo input[name='ADJUST_YN']:checked").val();
	var teshuYn = $("#viewPOtApplyInfo input[name='TESHU_YN']:checked").val();
	
	if(otTypeCode==""){
		otTypeCode = "32";
	}
	if(adjustYn==""){
		adjustYn = "0";
	}
	$.ajax({
		 cache: false,
		 type: 'post',
		 async:false,
		 url: "/ess/infoApply/getOtApplyRemark",
		 data: [{ name: 'ADJUST_YN', value: adjustYn },
		        { name: 'APPLY_TYPE_CODE', value: otTypeCode },
		        { name: 'TESHU_YN', value: teshuYn },
		        { name: 'OT_PLACE_TYPE', value: otPlaceType }],
		   
		 dataType:"json",
		 success: function(response) {
			 document.getElementById('otPApplyRemark').innerHTML = response;
		 }
	});
}
 //获取个人班次信息
function getOtApplyShiftNo(){
	var personId =$("#PERSON_ID",navTab.getCurrentPanel()).val();
	var fromDate = $("#FROM_DATE",navTab.getCurrentPanel()).val();
	var CPNY_ID =$("#CPNY_ID",navTab.getCurrentPanel()).val();
	$.ajax({
		 cache: false,
		 type: 'post',
		 async:false,
		 url: "/ess/infoApply/getOtApplyShiftNo",
		 data: [{ name: 'personId', value: personId },
		        { name: 'fromDate', value: fromDate },
		        { name: 'CPNY_ID', value: CPNY_ID }],
		   
		 dataType:"json",
		 success: function(data) {
			 $('#OtApplyShiftNoName').html( data.SHIFTNAME);
			 $('#OtApplyShiftNo').val(data.SHIFT_NO)  ;
		 }
	});
}
//-->
</script>
<script type="text/javascript">
<!--
//注意input的id和tr的id要一样
function addRowByIDP(currentRowID){
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
						+'	onkeydown="submitKeyClick_affirmorP(this,event)" class="required"/>'
            		+'</td>'
            		+'<td style="text-align: center">'
            		    +'<c:if test="${affirmorListCnt != 1}">'
            			+'    <img id= "'+addRowID+'" src="/resources/images/+.gif" style="cursor:hand" title="添加" onclick="addRowByIDP(this.id);"/>'
            			+'</c:if>&nbsp;&nbsp;&nbsp;'
            			+'    <img id= "'+addRowID+'" src="/resources/images/-.gif" style="cursor:hand" title="删除" '
            			           //先删除，再排序--上面一种方法Firefox不支持
            			//+'       onclick="javaScript:document.all.addAffirm_listP.deleteRow(event.srcElement.parentElement.parentElement.rowIndex);delRowByIDP();"/>'
            			+'         onclick="deleteRowP(this);"'
					+'</td>'
				+'</tr>';
            //当前行之后插入一行
            currentRow.after(str);
        }
    });
   	var tb2 = document.getElementById("addAffirm_listP");
   	//如果决裁者只有一个时，添加一个决裁者之后需要取消此按钮
   	var affirmorListCnt = document.getElementById("affirmorListCnt").value;
   	if(affirmorListCnt == 1){
   		tb2.rows[0].cells[2].innerHTML = '';
	}
   	var rowCount = tb2.rows.length;
   	for(var m=0;m<rowCount;m++){
		tb2.rows[m].cells[0].innerHTML = m+1;
   	}
}

function deleteRowP(r){
	var i=r.parentNode.parentNode.rowIndex;
	document.getElementById('addAffirm_listP').deleteRow(i);
	//存在一种情况无法解决，同时添加两人，再删除第一人，则再添加新决裁者为第二位出现错位现象
	var tb2 = document.getElementById("addAffirm_listP");
	//如果决裁者只有一个时，删除添加的决裁者之后需要恢复原来的添加按钮
   	var affirmorListCnt = document.getElementById("affirmorListCnt").value;
   	var affirmorIdOnly = document.getElementById("affirmorIdOnly").value;
   	if(affirmorListCnt == 1){
   		var addStr = '<img id="'+affirmorIdOnly+'" src="/resources/images/+.gif" style="cursor:hand" title="添加" onclick="addRowByIDP('+affirmorIdOnly+');"/>';
   		tb2.rows[0].cells[2].innerHTML = addStr;
	}
	var rowCount = tb2.rows.length;
	for(var m=0;m<rowCount;m++){
		tb2.rows[m].cells[0].innerHTML = m+1;
	}
}

function delRowByIDP(){
	//存在一种情况无法解决，同时添加两人，再删除第一人，则再添加新决裁者为第二位出现错位现象
	var tb2 = document.getElementById("addAffirm_listP");
	var rowCount = tb2.rows.length;
	for(var m=0;m<rowCount;m++){
		tb2.rows[m].cells[0].innerHTML = m+1;
	}
}


var keyCodeInit=0;
function submitKeyClick_affirmorP(obj,index,event){
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
					if(jsonObject.perCnt != 1 ){
						document.getElementById("onck").href=encodeURI(encodeURI("/ar/attendanceMintenance/viewAddAffirmList?isEmployeement=1&limit=super&pageNum=1"
								+'&seach_KEY='+empid
								+'&empidStr='+empIdStr
								+'&personidStr='+personIdStr
								));
						document.getElementById("onck").click();
					}
					if(jsonObject.perCnt==1){
					  	$("[id='dwz.person.LotempName" + index + "']").val('['+jsonObject.empId + ']-'+jsonObject.empName);
					  	$("[id='dwz.person.LotpersonId" + index + "']").val( jsonObject.personId);
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


function submitKeyClick_apply(obj,event){
	var localName = '';
	var idcardNo = '';
	var navTabId = '';
 	var e= event ? event : window.event; 
 	var keyCode = e.which ? e.which : e.keyCode;
   	if(keyCode==13){
   		keyCodeInit=keyCode;
		var empid=obj.value;
		var empIdStr=obj.id;
		var personIdStr="PERSON_ID";
		var empNameStr="empName_apply";
   		$.ajax({
			type: 'POST',
			url: encodeURI('/sys/affirm/getPersonCnt?limit=ar&navTabId=' + navTabId + '&EMPID='+empid+'&LOCAL_NAME='+localName+'&IDCARD_NO='+idcardNo),
			dataType:"json",
			cache: false,
			success: function(jsonObject){
						if (jsonObject.perCnt==0){
							alertMsg.error('<spring:message code="alert.message.sys.affirm.searchNoPersonAuthority"/>');
						}
						if(jsonObject.perCnt>1 ){
							alertMsg.error('请填写准确工号！');
						}
						if(jsonObject.perCnt==1){
							document.getElementById(empIdStr).value=jsonObject.empId;
							document.getElementById(personIdStr).value=jsonObject.personId;
							document.getElementById("EMP_TYPE_CODE").value=jsonObject.emp_type_code;
							document.getElementById(empNameStr).value='['+jsonObject.empId + ']-'+jsonObject.empName;
							if(jsonObject.perCnt==1){
								
								 navTabAjaxDone(
					    	    	{
					    	    		"statusCode":"200", 
										"forwardUrl":"/ess/infoApply/viewPOtApplyInfo" + "?navTabId=" + "ess0234" + "&PERSON_ID=" + jsonObject.personId + "&APPLY_TYPE_NO=" + "31", //考勤申请类型
										"callbackType":"forward"
					    	    	}
					    	    ) ;
							}
						}
					},
			error: DWZ.ajaxError
		});
    }
 }

function onLoadFunction(){   
    //if(o.propertyName!='value')return;  //不是value改变不执行下面的操作   
    //.......函数处理   onchange="getPOtApplyType();ajaxAdd_add_ot_apply_one_three();getShiftEndTime();"
    getPOtApplyType();
    getOtApplyShiftNo(); //获取班次号
    getDefaultShiftTime(); //获取班次时间
    ajaxAdd_add_ot_apply_one_three();
    getOtLimit();
} 

$(document).ready(function() {
	ajaxAdd_add_ot_apply_one_three();
	getPOtApplyType();
	getDefaultShiftTime();
});

 
</script>
<div class="panel"><h1>加班申请</h1>
<div>
<%@ include file="/WEB-INF/view/hrm/empinfo/viewPersonalInfoHead_ess.jsp"%>
</div>
</div>
<div class="pageContent">
	<div>
		<form id="viewPOtApplyInfo" method="post" action="/ess/infoApply/addPOvertimeApply" class="pageForm required-validate" 
			onsubmit="return validatePOvertimeApplyCallback(this,navTabAjaxDone);">
			<div>
				<table class="user_table" width="100%"  border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="20%" class="td_title" style="text-align:center">日期</td>
						    <td width="30%" class="td_type">
							
                            <input type="text" id="FROM_DATE" name="FROM_DATE" class="Wdate required"  onClick="WdatePicker({dateFmt:'yyyy-MM-dd',onpicked:onLoadFunction})" value="${CREATE_DATE}"/>
						    <input id="empName_apply" name="empName_apply" type="hidden" value="${personInfo.LOCAL_NAME}" readonly style="border:0;background:transparent;"
						                    	type="text"  />
						    <!-- 隐藏的一些参数 -->
						    <input  type="hidden" id="OT_APPLY_DATE" NAME="OT_APPLY_DATE" value=""></input>
						    <input id="CPNY_ID" name="CPNY_ID" type="hidden" value="${defaultCpny}" />
		                    <input id="PERSON_ID" name="PERSON_ID" type="hidden" value="${personInfo.PERSON_ID}" />	
		                    <input id="EMP_TYPE_CODE" name="EMP_TYPE_CODE" type="hidden" value="${personInfo.EMP_TYPE_CODE }">
						    <input id="APPLY_TYPE_NO" name="APPLY_TYPE_NO" type="hidden" value="31" />		   
						    <input id="APPLY_TYPE" name="APPLY_TYPE" type="hidden" value="PERSON" />
						    <input id="AFFIRM_FLAG" name="AFFIRM_FLAG" type="hidden" value="14014307" />
						    <input type="hidden" id="OT_TIME_TYPE" name="OT_TIME_TYPE" value="P"/>
						    <input type="hidden" id="LLASTMONTH" name="LLASTMONTH" value="${LLASTMONTH }"/>
						    <input type="hidden" id="LIMIT_FLAG" name="LIMIT_FLAG" value="${personInfo.LIMIT_FLAG }"/>
						    <input type="hidden" id="dateType" name="dateType" value=""/>
							<input type="hidden" id="peopleType" name="peopleType" value=""/>
						</td>
						<td width="20%" class="td_title" style="text-align:center"><!--加班类型-->
							<spring:message code="ess.viewApply.title.overtimeApplyType"/>
						</td>
						<td width="30%" class="td_type">
							 <input type="hidden" id="APPLY_TYPE_CODE"  name="APPLY_TYPE_CODE" value=""/>
							 <ait:SelectSyCodeByCpnyID name="APPLY_TYPE_CODE_NEW" parentNo="31" cnpyID="${defaultCpny}" 
						    	selected="${APPLY_TYPE_CODE}" limit="all" onChangeName="ajaxAdd_add_ot_apply_one_three();" disabled="true"/>
						    &nbsp;&nbsp;&nbsp;
						</td>
					</tr>
					<tr>
					   <td width="20%" class="td_title" style="text-align:center">倒休</td>
					   <td width="30%"   class="td_type">
					        <input type="checkbox" id="ADJUST_YN" name="ADJUST_YN" value="1"/>
					  </td>
					    <td width="20%" class="td_title" style="text-align:center">加班情况</td>
					   <td width="30%"  class="td_type">
					         <span>本月累计加班：<div style="display:inline" id="ot_total_html" ></div>小时&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					    	    本月加班上限：<div style="display:inline" id="ot_limit_html" ></div>小时</span>
					    	    <input id="ot_total"  type="hidden" value="" >
					    	    <input id="ot_limit"  type="hidden" value="" >
					  </td>
					</tr>
					<tr>
					    <td width="20%" class="td_title" style="text-align:center">
					    	班次
					    </td>
					    <td width="30%" class="td_type" >
					        <div id="OtApplyShiftNoName">${SHIFTNAME}</div>
					        <input id="OtApplyShiftNo"  type="hidden"  name="SHIFT_NO" value="${SHIFT_NO}" />
					    </td>
					    <td width="20%" class="td_title" style="text-align:center">
					    	工作时间
					    </td>
					    <td width="30%" class="td_type" >
							<div id="workTime">${WORKTIMESTRING}</div>
					    </td>
					</tr>
						<tr>
					    <td width="20%" class="td_title" style="text-align:center">
					    	进出门时间
					    </td>
					    <td width="80%" class="td_type" colspan="3">
					    	      <span>进门时间：<div style="display:inline" id="IN_DOOR" >${applyParam.INDOOR_DATE}</div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					    	    出门时间:<div style="display:inline" id="OUT_DOOR" >${applyParam.OUTDOOR_DATE}</div></span>
					    </td>
					</tr>
					</tr>
						<tr>
					    <td width="20%" class="td_title" style="text-align:center">
					    	时间
					    </td>
					    <td width="80%" class="td_type" colspan="3">
							<input type="text" size="2" id="OT_FROM_TIME" name="OT_FROM_TIME" value="${STARTTIME}" onkeyup="calLength(this.value);"/>  
							<input type="hidden" id="OT_FROM_TIME_FLAG" name="OT_FROM_TIME_FLAG" value="${STARTTIME}"/>
							~
						   <input type="text" size="2" id="OT_TO_TIME" name="OT_TO_TIME" value="${ENDTIME}" onkeyup="calLength(this.value);"/>   
                             <input type="hidden" id="OT_TO_TIME_FLAG" name="OT_TO_TIME_FLAG" value="${ENDTIME}"/>
						</td> 
					</tr>
					<tr>		
 						<td width="20%" class="td_title" style="text-align:center">
							加班时间(时间/分)
						</td>
						<td width="80%" class="td_type"  colspan="3">
							<div id="otApplyLength">0</div>
				            <input type="hidden" id="Lotlengthone" name="Lotlengthone" value="0"/> 
				            <input type="hidden" id="Lotlengthonehour" name="Lotlengthonehour" value=""/> 
				            <input type="hidden" id="Lotlengthonemin" name="Lotlengthonemin" value=""/> 
						</td>
					</tr>
					<tr>					
					    <td width="20%" class="td_title" style="text-align:center"><!--其他原因-->
					    	其他原因
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
									<td class="td_title" style="text-align:center;" width="25%">审批人</td>
									<td class="td_title" style="text-align:center;" width="25%">审批人信息</td>
									<td class="td_title" width="25%">是否新增(<img src="/resources/images/+.gif" title="添加" border="0" align="absmiddle" style="cursor:hand" onclick="addRowByIDApplyPOTFirst()"/>)</td>
								</tr>
								<tr>
									<td colspan="5">
										<table width="100%" border="0" cellpadding="0" cellspacing="0" id="addApplyLOTAffirm_list">
											<tbody>
											</tbody>
										</table>
									</td>	
								</tr>
							</table>
							<a id="onck" name="onck"  href="" lookupGroup="person"></a>
					 		<input type="hidden" id="affirmorListCnt" name="affirmorListCnt" value="${affirmorListCnt }"/>
					     	<input type="hidden" name="applyOtCount_1" id="applyOtCount_1" value="">
						</td>
					</tr>
					</table>
			</div>
			<div class="formBar">
				<ul>
					<li>
						<div class="button">
							<div class="buttonContent"><!--禀告-->
								<button type="button" onclick="submitPFormPre(0)">
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
 

 