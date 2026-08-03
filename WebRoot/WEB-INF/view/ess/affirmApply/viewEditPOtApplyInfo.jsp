<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.easydrag.js"></script>
<script type="text/javascript">



function getDefaultShiftTime(){

	var  emptype_code = $("#updatePOtApply input[name='EMP_TYPE_CODE']").val();
	var cpny_id = $("#updatePOtApply input[name='CPNY_ID']").val();
	var  datetype = $("#updatePOtApply input[name='dateType']").val();
 
	if($("#updatePOtApply input[name='FROM_DATE']").val()!= ""){
		$.ajax({
			 cache: false,
			 type: 'post',
			 async:false,
			 url: "/ess/infoApply/getDefaultStartEndTime",
			 data: [{ name : 'APPLY_OT_DATE' , value :$("#updatePOtApply input[name='FROM_DATE']").val() },
			        { name : 'EMP_TYPE_CODE' , value :emptype_code },
			        { name : 'FLAG' , value :'A' }, 
			        { name : 'TESHU' , value :datetype},
			        { name : 'PERSON_ID' , value :$("#updatePOtApply input[name='PERSON_ID']").val()}],
			 dataType:"json",
			 success: function(data) {
			 var cpnyId = document.getElementById("CPNY_ID").value;
			 if(cpnyId == 'LGEQH'){
			 	 document.getElementById("OT_FROM_TIME_H").value = data.START_TIME.substring(0,2); 
			 	 document.getElementById("OT_FROM_TIME_M").value = data.START_TIME.substring(3,5); 
			 	 $("#OT_FROM_TIME_FLAG").val(data.START_TIME); 
			 }else{
			 	 $("#OT_FROM_TIME").val(data.START_TIME);
			     $("#OT_FROM_TIME_FLAG").val(data.START_TIME); 
			 }
				 if(cpny_id == 'SST' && emptype_code == '211792'){
					 $("#OT_TO_TIME").val(data.END_TIME);
				 }else if(datetype =='1441' || datetype=='1442'){
				     if(cpny_id == 'LGEQH'){
				     	 document.getElementById("OT_TO_TIME_H").value = data.END_TIME.substring(0,2); 
			 	         document.getElementById("OT_TO_TIME_M").value = data.END_TIME.substring(3,5); 
				     }else{
				     	 $("#OT_TO_TIME").val(data.END_TIME);
				     }
			     }else if(datetype =='1440'){
			         if(cpny_id == 'LGEQH'){
			             document.getElementById("OT_TO_TIME_H").value =  data.END_TIME.substring(0,2); 
			 	         document.getElementById("OT_TO_TIME_M").value = data.END_TIME.substring(3,5); 
			         }else{
			             $("#OT_TO_TIME").val($("#OT_TO_TIME_FLAG").val());
			         }
			     }
			 }
		});
	}
	calPoTLengthEdit();
}
function getChangeOtType(){
	 
	var cpnyId = $("#updatePOtApply input[name='CPNY_ID']").val();
	var  fromdate =$("#updatePOtApply input[name='FROM_DATE']").val();
	var ot_from_time_ps = $("#updatePOtApply select[id='OT_FROM_TIME']").val();

	 
	 if(cpnyId == 'LGEQH'){
	        ot_from_time_ps = document.getElementById("OT_FROM_TIME_H").value + ":" +
	        document.getElementById("OT_FROM_TIME_M").value;
	    }else{
	        ot_from_time_ps = $("#updatePOtApply select[id='OT_FROM_TIME']").val();
	    }
	var  emptype_code = $("#updatePOtApply input[name='EMP_TYPE_CODE']").val();

	var teshuYnEdit	 = $("#updatePOtApply input[name='TESHU_YN']:checked").val();
	 
	var cpny_id = $("#updatePOtApply input[name='CPNY_ID']").val();
	var  datetype = $("#updatePOtApply input[name='dateType']").val();
	var peronsid = $("#updatePOtApply input[name='PERSON_ID']").val();
	var otfromtime =    fromdate + " " + ot_from_time_ps + ":" + "00";
	var adjustYn = $("#updatePOtApply input[name='ADJUST_YN']:checked").val();//调休选中的值
	var endDayOffset = $("#updatePOtApply input[name='END_DAY_OFFSET']:checked").val();
	var otPlaceType = $("#updatePOtApply select[id='OT_PLACE_TYPE']").val();

	  
	if(fromdate!= ""){
		$.ajax({
			 cache: false,
			 type: 'post',
			 async:false,
			 url: "/ess/infoApply/getChangeOtType",
			 data: [{ name : 'APPLY_OT_DATE' , value :fromdate },
			        { name : 'FROM_TIME' , value :otfromtime },
			        { name : 'EMP_TYPE_CODE' , value :emptype_code },
			        { name : 'TESHU_YN' , value :teshuYnEdit},
			        { name : 'OT_PLACE_TYPE' , value :otPlaceType},
			        { name : 'END_DAY_OFFSET' , value :endDayOffset},   
			        { name : 'ADJUST_YN' , value :adjustYn},
			        { name : 'PERSON_ID' , value : peronsid}],
			 dataType:"json",
			 success: function(data) {
			 $("#OT_APPLY_DATE").val(data.OT_APPLY_DATE);
			 $("#APPLY_TYPE_CODE_JUECAI").val(data.OT_TYPE);
			 $(":input[name='APPLY_TYPE_CODE']").val(data.OT_TYPE);
				    
			 }
		});
	}
	calPoTRemarkEdit();
}
var ajaxGet_add_ot_apply_four;
//更新裁决线
function ajaxAdd_add_ot_apply_one_four() {
	if (ajaxGet_add_ot_apply_four != null) {
		ajaxGet_add_ot_apply_four.abort();
	}

    var otApplyHour   = document.getElementById("Lotlengthtwo").value;
    
	 
	var teshuYn = $("#updatePOtApply input[name='TESHU_YN']:checked").val();

	var adjustYn = $("#updatePOtApply input[name='ADJUST_YN']:checked").val();
    var applytypecode=$("#updatePOtApply input[name='APPLY_TYPE_CODE']").val();
    
 	if($("#PERSON_ID").val() != '' && otApplyHour != '' && $(":input[name='APPLY_TYPE_CODE']").val() != ''){
  		
 
 
		$.ajaxSettings.global = false;
		 
		ajaxGet_add_ot_apply_four = $.ajax( {
			type : "POST",
			url : "/ess/infoApply/getAffirmList",
			data : {applyParentType : '31',applyType : applytypecode,applyLength : otApplyHour,personId : $("#PERSON_ID").val(),TESHU_YN:teshuYn,ADJUST_YN:adjustYn,OT_PLACE_TYPE : $(":input[name = 'OT_PLACE_TYPE']").val()},
			dataType : "json",
			success : function(data) {
				$('#addApplyPOTAffirm_listTwo tbody').html("");
				var html = "";
				if (typeof (data['affirmList']) != "undefined") {
					$.each(data['affirmList'],
									function(commentIndex, comment) {
										html += '<tr id="rowIdApplyLot' + commentIndex  + '">';
										html += '<td class="td_type" style="text-align: center" width="33%">' + (commentIndex + 1) + '</td>';
										html += '<td class="td_type" style="text-align: center" width="33%">[' + comment['EMPID'] + ']-' + comment['LOCAL_NAME'];
										html += '<input type="hidden" name="AFFIRMOR_ID" value="' + comment['AFFIRMOR_ID'] + '"/></td>';
								 
										html += '<td class="td_type" style="text-align: center" width="33%">';
										html += '<img src="/resources/images/+.gif" title="添加" border="0" align="absmiddle" style="cursor:hand" onclick="addRowByIDLTwo('+ commentIndex +')"/>';
										html += '</td>';
										$("#affirmorListCnt").val(commentIndex + 1);
									});
				}
				$('#addApplyPOTAffirm_listTwo tbody').html(html);
			}
		});
		$.ajaxSettings.global = true;
	}
}
//添加决裁者
function addRowByIDLTwo(currentRowID){
	var count = parseInt($("#affirmorListCnt").val());
    var htm  ='<tr id="rowIdApplyLot'+ count +'"><td class="td_type" style="text-align: center" width="33%"><span name="rowIndex"></span></td>';
		htm +='<td class="td_type" style="text-align: center" width="33%">';
		htm +='<input id="dwz.person.LotpersonId'+count+'" name="AFFIRMOR_ID" value="" type="hidden" lookupGroup="person"/>';
		htm +='<input id="dwz.person.LotempName'+count+'" name="empid" value="" type="text" lookupGroup="person" onkeydown="submitKeyClick_affirmorP(this,' + count + ',event)" class="required"/>';

		
		//htm +='<a class="btnLook" href="/ar/attendanceSettings/viewKeeperList?pageNum=1" lookupGroup="person">';
		htm +='</td>';
		htm +='<td class="td_type" style="text-align: center" width="33%">';
		htm +='<img src="/resources/images/+.gif" title="添加"';	
		htm +='border="0" align="absmiddle" style="cursor:hand" onclick="addRowByIDLTwo(' + count + ')"/>&nbsp;&nbsp;&nbsp;';
		htm +='<img src="/resources/images/-.gif" title="删除"';	
		htm +='border="0" align="absmiddle" style="cursor:hand" onclick="javaScript:document.all.addApplyPOTAffirm_listTwo.deleteRow(event.srcElement.parentElement.parentElement.rowIndex);changeApplyOtLevel();"/></td></tr>';

   	//当前行之后插入一行
   	$("#rowIdApplyLot" + currentRowID).after(htm);
  	$("[id='dwz.person.LotempName" + count + "']").attr("alt","请输入关键字按回车检索").attr("size","30").inputAlert();
   	changeApplyOtLevel();
  	$("#affirmorListCnt").val(++count) ;
}

function addRowByIDApplyLOTEditFirst(){
	var count = parseInt($("#affirmorListCnt").val());
    var htm  ='<tr id="rowIdApplyLot'+ count +'"><td class="td_type" style="text-align: center" width="33%"><span name="rowIndex"></span></td>';
	htm +='<td class="td_type" style="text-align: center" width="33%">';
	htm +='<input id="dwz.person.LotpersonId'+count+'" name="AFFIRMOR_ID" value="" type="hidden" lookupGroup="person"/>';
	htm +='<input id="dwz.person.LotempName'+count+'" name="empid" value="" type="text" lookupGroup="person" onkeydown="submitKeyClick_affirmorP(this,' + count + ',event)" class="required"/>';

	htm +='</td>';
	htm +='<td class="td_type" style="text-align: center" width="33%">';
	htm +='<img src="/resources/images/+.gif" title="添加"';	
	htm +='border="0" align="absmiddle" style="cursor:hand" onclick="addRowByIDLTwo(' + count + ')"/>&nbsp;&nbsp;&nbsp;';
	htm +='<img src="/resources/images/-.gif" title="删除"';	
	htm +='border="0" align="absmiddle" style="cursor:hand" onclick="javaScript:document.all.addApplyPOTAffirm_listTwo.deleteRow(event.srcElement.parentElement.parentElement.rowIndex);changeApplyOtLevel();"/></td></tr>';


	var tb2 = document.getElementById("addApplyPOTAffirm_listTwo");


   	if(tb2.rows.length == 0){
   		$("#addApplyPOTAffirm_listTwo:last tbody").html(htm);
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
	var tb2 = document.getElementById("addApplyPOTAffirm_listTwo");
	var rowCount = tb2.rows.length;
	for(var m=0;m<rowCount;m++){
		tb2.rows[m].cells[0].innerHTML = m+1;
	}
}

function submitKeyClick_affirmorP(obj,index,event){
	var localName = '';
	var idcardNo = '';
	var navTabId = '';
 	var e= event ? event : window.event; 
 	var keyCode = e.which ? e.which : e.keyCode;

   	if(keyCode==13){
   		keyCodeInit=keyCode;
		var empid=obj.value.replace(/[ ]/g,"");
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

function submitPUpdateFormPre(flag){
	$("#AFFIRM_FLAG").val(flag);
	$("#updatePOtApply input[id='AFFIRM_FLAG']").val(flag);
  	var $from = $("#updatePOtApply");
  	$from.submit();
}
function updatePOtApplyCallback(form,callback) {	
	var $form = $(form);	
	var cpnyId = document.getElementById("CPNY_ID").value; 
	var applyTypeCode = document.getElementById("APPLY_TYPE_CODE_JUECAI").value;
   	var fromDate  = document.getElementById("FROM_DATE").value;
    var otFromTime = '';
    var otToTime = '';
	if(cpnyId == 'LGEQH'){
        otFromTime = document.getElementById("OT_FROM_TIME_H").value + ":" +
        document.getElementById("OT_FROM_TIME_M").value;
        otToTime = document.getElementById("OT_TO_TIME_H").value + ":" +
        document.getElementById("OT_TO_TIME_M").value;
    }else{
        otFromTime = document.getElementById("OT_FROM_TIME").value;
        otToTime = document.getElementById("OT_TO_TIME").value;
    }
   // var endDayOffset = document.getElementById("END_DAY_OFFSET").value;   
    var endDayOffset = $("#updatePOtApply input[name='END_DAY_OFFSET']:checked").val();
	var appid = document.getElementById("APPLY_TYPE_CODE_JUECAI").value;
	 var  emptype_code = $("#updatePOtApply input[name='EMP_TYPE_CODE']").val();
	    if(emptype_code==''||emptype_code==null){
				alert("申请失败：您的人员类型是空的，请联系考勤担设置您的人员类型");
			return false;
	      }
    
    var applyTypeCode2 = document.getElementById("APPLY_TYPE_CODE_JUECAI");
     
    
    
  
    var adjustYn2 = document.getElementById("ADJUST_YN_Y");
    var adjustYn = document.getElementById("ADJUST_YN_N");
    
    var llastMonth   = document.getElementById("LLASTMONTH").value;
 
	if(fromDate <= llastMonth){
		alertMsg.error("不允许做"+llastMonth+"之前的加班申请，请重新选择加班日期！");
		return false;
	}

	if(applyTypeCode==""){
       alertMsg.error('<spring:message code="alert.message.ess.infoApply.overtimeApplyTypeIsMust"/>');
       return false;
	}
	
	var limitFlag   = document.getElementById("LIMIT_FLAG").value;
	if(limitFlag=='1' && applyTypeCode!='34'){//如果加班人员是被限制的人员，则不允许申请节假日以外的加班申请!
		alertMsg.error("该员工为营业职或促销员，只允许申请法定假加班，请重新选择加班日期!");
		return false;
	}
    
    //因为没有结束日期的选择所以只需要在没有跨天时比较时间即可
	//加班不跨天时，结束时间一定要晚于开始时间
	if(endDayOffset == '0'){
		var leavefromtime = fromDate + " " + otFromTime + ":" + "00";
		var leavetotime = fromDate + " " + otToTime + ":" + "00";
		if(comptime(leavefromtime,leavetotime)!=1){
			alertMsg.error('<spring:message code="alert.message.ess.infoApply.startTimeCanNotLaterThanEndTime"/>');
			return false;
		}
		if(comptime2(leavefromtime,leavetotime)!=1){
			alertMsg.error("加班时长不得低于一小时，请重新选择加班时间！");
			return false;
		}
	}
	var but1 = document.getElementById("but1");
	var but2 = document.getElementById("but2");
	if (confirm ("确定要修改加班申请吗？")){	    
		applyTypeCode2.disabled=false;
	    adjustYn.disabled=false;
	    adjustYn2.disabled=false;      
		$.ajax({
			type: form.method || 'POST',
			url:$form.attr("action"),
			data:$form.serializeArray(),
			dataType:"json",
			cache: false,
			success: function(json){
			 DWZ.ajaxDone(json);
			 if (json.statusCode == DWZ.statusCode.ok){
				if (json.navTabId){ //把指定navTab页面标记为需要“重新载入”。注意navTabId不能是当前navTab页面的
					navTab.reloadFlag(json.navTabId);
				} else { //重新载入当前navTab页面
					navTabPageBreak({}, json.rel);
				}
				
				if ("closeCurrent" == json.callbackType) {
					setTimeout(function(){navTab.closeCurrentTab();}, 100);
				} else if ("forward" == json.callbackType) {
					navTab.reload(json.forwardUrl);
				}
			}

			 
			adjustYn.disabled=true;
			adjustYn2.disabled=true;
			applyTypeCode2.disabled=true;
		     
			} ,
				
			 
			error: DWZ.ajaxError
		});		
	}
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
		return 'exception'
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
//-->
</script>

<script type="text/javascript">


//计算时长
function calPoTLengthEdit(){
    var personId = $("#PERSON_ID",navTab.getCurrentPanel()).val();
	var from_date =	  $("#updatePOtApply input[id='OT_APPLY_DATE']").val();
	var cpnyId = document.getElementById("CPNY_ID").value;
    var fromTime = '';
    var toTime = '';
    if(cpnyId == 'LGEQH'){
        fromTime = document.getElementById("OT_FROM_TIME_H").value + ":" +
        document.getElementById("OT_FROM_TIME_M").value;
        toTime = document.getElementById("OT_TO_TIME_H").value + ":" +
        document.getElementById("OT_TO_TIME_M").value;
    }else{
        fromTime = $("#updatePOtApply select[id='OT_FROM_TIME']").val();
        toTime = $("#updatePOtApply select[id='OT_TO_TIME']").val();
    }
	var endDayOffset = $("#updatePOtApply input[name='END_DAY_OFFSET']:checked").val();
	var teshuYn = $("#updatePOtApply select[id='TESHU_YN']").val();
	 var dateTypeId = document.getElementById("dateType");
	var otfromtime = from_date + " " + fromTime + ":" + "00";
	var ottotime = from_date + " " + toTime + ":" + "00";
	var otPlaceType = $("#updatePOtApply select[id='OT_PLACE_TYPE']").val();
	//加班结束时间早于开始时间，或者 加班
	//if(comptime(otfromtime,ottotime)!=1){
		//document.getElementById('otApplyLength').innerHTML = "0小时0分钟（加班结算时间晚于开始时间）";
	//}
	  var applytypecode=$("#updatePOtApply input[name='APPLY_TYPE_CODE']").val();
		if(from_date!=null&&from_date!=""){
					$.ajax({
						 cache: false,
						 type: 'post',
						 async:false,
						 url: "/ess/infoApply/getOtApplyLengthWq",
						 data: [{ name: 'END_DAY_OFFSET', value: endDayOffset },
						        { name: 'OT_FROM_TIME', value: otfromtime },
						        { name: 'PERSON_ID', value: personId },
						        { name: 'TESHU_YN', value: teshuYn },
						        { name: 'OT_PLACE_TYPE', value: otPlaceType },
						        { name: 'DATE_TYPE', value: dateTypeId },
						        { name: 'APPLY_TYPE_CODE', value: applytypecode },
						        { name: 'OT_TO_TIME', value: ottotime }],
						 dataType:"json",
						 success: function(response) {

						if(response<0){
							response =0;
						 }
						 if(cpnyId != 'LGEQH'){
							 document.getElementById('otApplyLengthEdit').innerHTML = response+" 小时";
						 }
						
							 $("#Lotlengthtwo").val(response);
						 }

						 
						 
					});
					}
			if(cpnyId=='LGEQD' || cpnyId=='LGEYT'){
     	if(from_date!=null&&from_date!=""){
	 

		$.ajax({
			 cache: false,
			 type: 'post',
			 async:false,
			 url: "/ess/infoApply/getOtApplyLengthZong",
			 data: [{ name: 'END_DAY_OFFSET', value: endDayOffset },
			        { name: 'OT_FROM_TIME', value: otfromtime },
			        { name: 'PERSON_ID', value: personId },
			        { name: 'DATE_TYPE', value: dateTypeId },
			        { name: 'TESHU_YN', value: teshuYn },
			        { name: 'OT_PLACE_TYPE', value: otPlaceType },
			        { name: 'DATE_TYPE', value: dateTypeId },
			        { name: 'APPLY_TYPE_CODE', value: applytypecode },
			        { name: 'OT_TO_TIME', value: ottotime }],
			 dataType:"json",
			 success: function(response) {
			if(response<0){
				response =0;
			 }
			 if(cpnyId != 'LGEQH'){
				 document.getElementById('otApplyLengthZong').innerHTML = response+" 小时";
			 }
			     if(cpnyId == 'LGEQD' && response>=70){
			        document.getElementById('otApplyLengthZong').style.color="red";
			     }
			     if(cpnyId == 'LGEYT' && response>=90){
			        document.getElementById('otApplyLengthZong').style.color="red";
			     }
				 $("#LotlengthoneZong").val(response);
			 }
		});
     }		
		}
		ajaxAdd_add_ot_apply_one_four();
}

//获取加班的人事政策
function calPoTRemarkEdit(){
	var otTypeCode = $("#updatePOtApply select[id='APPLY_TYPE_CODE_JUECAI']").val();
	var otPlaceType = $("#updatePOtApply select[id='OT_PLACE_TYPE']").val();
	var teshuYn = $("#updatePOtApply input[name='TESHU_YN']:checked").val();
	var adjustYn = $("#updatePOtApply input[name='ADJUST_YN']:checked").val();
	
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
			 document.getElementById('otPApplyRemarkEdit').innerHTML = response;
		 }
	});
}
//-->

//获取该日期的加班类型
function getPOtApplyType(){
	var personId =$("#PERSON_ID",navTab.getCurrentPanel()).val();
	var fromDate = $("#FROM_DATE",navTab.getCurrentPanel()).val();
	var CPNY_ID =$("#CPNY_ID",navTab.getCurrentPanel()).val();
	var cpnyId = document.getElementById("CPNY_ID").value;
    var ot_from_time_ps = '';
    if(cpnyId == 'LGEQH'){
        ot_from_time_ps = document.getElementById("OT_FROM_TIME_H").value + ":" +
        document.getElementById("OT_FROM_TIME_M").value;
    }else{
        ot_from_time_ps = $("#updatePOtApply select[id='OT_FROM_TIME']").val();
    }
	var otfromtime = fromDate + " " + ot_from_time_ps + ":" + "00";
	
	var applyTypeCode = document.getElementById("APPLY_TYPE_CODE_JUECAI");
	var applyTypeNO = document.getElementById("APPLY_TYPE_NO");
	var applyTypeCodeView = document.getElementById("APPLY_TYPE_CODE_JUECAI");
    var adjustYn = $("#updatePOtApply input[name='ADJUST_YN']");
    var TESHU_YN = $("#updatePOtApply input[name='TESHU_YN']");
    var adjust = $("#updatePOtApply input[name='ADJUST_YN']:checked").val();
    var teshu = $("#updatePOtApply input[name='TESHU_YN']:checked").val();
    var OT_PLACE_TYPE = document.getElementById("OT_PLACE_TYPE");
    var dateTypeId = document.getElementById("dateType");
    var peopleTypeId = document.getElementById("peopleType");
    

 	if(fromDate!=""){
		$.ajax({
			cache: false,
			type: 'post',
			async:false,
			url: "/ess/infoApply/getDateTypeByDateAndEmpCpny?",
			data:'DDATE_STR=' + fromDate +'&PERSON_ID='+personId+'&TESHU_YN='+teshu+'&FROM_TIME='+otfromtime,
			dataType:"json",
			success: function(data) {
				var dateType = data.TYPEID;
				var peopleType = data.PEOPLETYPEID;//个人排班编号
 
				dateTypeId.value=dateType;
				peopleTypeId.value=peopleType;
				//alert("11调休："+adjust+"特殊："+teshu+"date:"+dateType);
				if(dateType == '1440'){       //平时
					applyTypeCode.value="32";
					applyTypeNO.value="32";
					applyTypeCodeView.value="32";
					//调休不可用
			    	adjustYn.eq(0).attr('checked', 'true');
					adjustYn.attr("disabled",true);
			    	//特殊加班可用
			    	TESHU_YN.eq(teshu).attr('checked', 'true');
			    	TESHU_YN.attr("disabled",false);
			    	//社内外可用
			    	OT_PLACE_TYPE.value=OT_PLACE_TYPE.value;
			    	OT_PLACE_TYPE.disabled=false;
			    	
			    	if(CPNY_ID == 'SST'){
						$("#OT_FROM_TIME",navTab.getCurrentPanel()).attr("value","17:30");
						  $("#OT_TO_TIME",navTab.getCurrentPanel()).attr("value","20:30");
						}
					 
				}else if(dateType == '1441'){ //周末 
				 
					applyTypeCode.value="33";
					applyTypeNO.value="33";

					applyTypeCodeView.value="33";
					if(CPNY_ID == 'SST'){
						if(peopleType == '217871'){
							applyTypeCode.value="217871";
							applyTypeCodeView.value="217871";
					    }
						var emp_type_code = document.getElementById("EMP_TYPE_CODE").value;
						if(emp_type_code == '211792'){
							$("#OT_FROM_TIME",navTab.getCurrentPanel()).attr("value","08:00");
							$("#OT_TO_TIME",navTab.getCurrentPanel()).attr("value","17:00");
						    var ot_from_time = document.getElementById("OT_FROM_TIME");
                            var opt = ot_from_time.options;
                                opt[0].value = '8:00';opt[0].text = '8:00';opt[1].value = '8:30';opt[1].text = '8:30';
                                opt[2].value = '9:00';opt[2].text = '9:00';opt[3].value = '9:30';opt[3].text = '9:30';
                                opt[4].value = '10:00';opt[4].text = '10:00';opt[5].value = '10:30';opt[5].text = '10:30';
                                opt[6].value = '11:00';opt[6].text = '11:00';opt[7].value = '11:30';opt[7].text = '11:30';
                                opt[8].value = '12:00';opt[8].text = '12:00';opt[9].value = '12:30';opt[9].text = '12:30';
                                opt[10].value = '13:00';opt[10].text = '13:00';opt[11].value = '13:30';opt[11].text = '13:30';
                                opt[12].value = '14:00';opt[12].text = '14:00';opt[13].value = '14:30';opt[13].text = '14:30';
                                opt[14].value = '15:00';opt[14].text = '15:00';opt[15].value = '15:30';opt[15].text = '15:30';
                                opt[16].value = '16:00';opt[16].text = '16:00';opt[17].value = '16:30';opt[17].text = '16:30';
                                opt[18].value = '17:00';opt[18].text = '17:00';opt[19].value = '17:30';opt[19].text = '17:30';
                                opt[20].value = '18:00';opt[20].text = '18:00';opt[21].value = '18:30';opt[21].text = '18:30';
                                opt[22].value = '19:00';opt[22].text = '19:00';opt[23].value = '19:30';opt[23].text = '19:30';
                                opt[24].value = '20:00';opt[24].text = '20:00';opt[25].value = '20:30';opt[25].text = '20:30';
                                opt[26].value = '21:00';opt[26].text = '21:00';opt[27].value = '21:30';opt[27].text = '21:30';
                                opt[28].value = '22:00';opt[28].text = '22:00';opt[29].value = '22:30';opt[29].text = '22:30';
                                opt[30].value = '23:00';opt[30].text = '23:00';opt[31].value = '23:30';opt[31].text = '23:30';
                                opt[32].value = '0:00';opt[32].text = '0:00';
                                document.getElementById("OT_FROM_TIME").value = opt.value;
                                document.getElementById("OT_FROM_TIME").text = opt.text;
                                $("#OT_FROM_TIME",navTab.getCurrentPanel()).attr("value","8:00");
						        $("#OT_FROM_TIME_FLAG").val("8:00");
						}
					}
					//调休可用
			    	adjustYn.eq(adjust).attr('checked', 'true');
					adjustYn.attr("disabled",false);
			    	//特殊加班可用
			    	TESHU_YN.eq(teshu).attr('checked', 'true');
			    	TESHU_YN.attr("disabled",false);
			    	//社内外可用
			    	OT_PLACE_TYPE.value=OT_PLACE_TYPE.value;
			    	OT_PLACE_TYPE.disabled=false;
			    	
				}else if(dateType == '1442'){ //法定
					
					applyTypeCode.value="34";
					applyTypeNO.value="34";

					applyTypeCodeView.value="34";

					//调休不可用
					
					//调休可用
					if(CPNY_ID == 'LGEHZ' || CPNY_ID == 'LGEYT'){
					   adjustYn.eq(0).attr('checked', 'true');
					   adjustYn.attr("disabled",true);
					}else{
					   adjustYn.eq(0).attr('checked', 'true');
					   adjustYn.attr("disabled",false);
					}
			     
			    	//特殊加班可用
			    	TESHU_YN.eq(teshu).attr('checked', 'true');
			    	TESHU_YN.attr("disabled",false);
			    	//社内外可用
			    	OT_PLACE_TYPE.value=OT_PLACE_TYPE.value;
			    	OT_PLACE_TYPE.disabled=false;
					if(CPNY_ID == 'SST'){
						if(peopleType == '217872'){
							applyTypeCode.value="217872";
							applyTypeCodeView.value="217872";
					    }
						  var emp_type_code = document.getElementById("EMP_TYPE_CODE").value;
						  if(emp_type_code == '211792'){
							  $("#OT_FROM_TIME",navTab.getCurrentPanel()).attr("value","17:30");
							  $("#OT_TO_TIME",navTab.getCurrentPanel()).attr("value","20:30");
						    var ot_from_time = document.getElementById("OT_FROM_TIME");
                            var opt = ot_from_time.options;
// 							for(var i=0,len=options.length;i<len;i++){
                                opt[0].value = '8:00';opt[0].text = '8:00';opt[1].value = '8:30';opt[1].text = '8:30';
                                opt[2].value = '9:00';opt[2].text = '9:00';opt[3].value = '9:30';opt[3].text = '9:30';
                                opt[4].value = '10:00';opt[4].text = '10:00';opt[5].value = '10:30';opt[5].text = '10:30';
                                opt[6].value = '11:00';opt[6].text = '11:00';opt[7].value = '11:30';opt[7].text = '11:30';
                                opt[8].value = '12:00';opt[8].text = '12:00';opt[9].value = '12:30';opt[9].text = '12:30';
                                opt[10].value = '13:00';opt[10].text = '13:00';opt[11].value = '13:30';opt[11].text = '13:30';
                                opt[12].value = '14:00';opt[12].text = '14:00';opt[13].value = '14:30';opt[13].text = '14:30';
                                opt[14].value = '15:00';opt[14].text = '15:00';opt[15].value = '15:30';opt[15].text = '15:30';
                                opt[16].value = '16:00';opt[16].text = '16:00';opt[17].value = '16:30';opt[17].text = '16:30';
                                opt[18].value = '17:00';opt[18].text = '17:00';opt[19].value = '17:30';opt[19].text = '17:30';
                                opt[20].value = '18:00';opt[20].text = '18:00';opt[21].value = '18:30';opt[21].text = '18:30';
                                opt[22].value = '19:00';opt[22].text = '19:00';opt[23].value = '19:30';opt[23].text = '19:30';
                                opt[24].value = '20:00';opt[24].text = '20:00';opt[25].value = '20:30';opt[25].text = '20:30';
                                opt[26].value = '21:00';opt[26].text = '21:00';opt[27].value = '21:30';opt[27].text = '21:30';
                                opt[28].value = '22:00';opt[28].text = '22:00';opt[29].value = '22:30';opt[29].text = '22:30';
                                opt[30].value = '23:00';opt[30].text = '23:00';opt[31].value = '23:30';opt[31].text = '23:30';
                                opt[32].value = '0:00';opt[32].text = '0:00';
                                document.getElementById("OT_FROM_TIME").value = opt.value;
                                document.getElementById("OT_FROM_TIME").text = opt.text;
                                $("#OT_FROM_TIME",navTab.getCurrentPanel()).attr("value","8:00");
						        $("#OT_FROM_TIME_FLAG").val("8:00");
//                          }
						  }
						}
				}else{ 
					applyTypeCode.value="32";
					applyTypeNO.value="32";

					applyTypeCodeView.value="32";
					

					//调休不可用
			    	adjustYn.eq(0).attr('checked', 'true');
					adjustYn.attr("disabled",true);
			    	//特殊加班可用
			    	TESHU_YN.eq(teshu).attr('checked', 'true');
			    	TESHU_YN.attr("disabled",false);
			    	//社内外可用
			    	OT_PLACE_TYPE.value=OT_PLACE_TYPE.value;
			    	OT_PLACE_TYPE.disabled=false;
			    	
					if(CPNY_ID == 'SST'){
						$("#OT_FROM_TIME",navTab.getCurrentPanel()).attr("value","17:30");
						  $("#OT_TO_TIME",navTab.getCurrentPanel()).attr("value","20:30");
						}
				}
				applyTypeCodeView.disabled=true;
				
			}
		});
	}
 	calPoTRemarkEdit();
 	 
	calPoTLengthEdit();
	//setTimeout("getPOtApplyType()",1000);
}


</script>

<script type="text/javascript">

//注意input的id和tr的id要一样
function addPRowByIDEdit(currentRowID){
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
						+'	onkeydown="submitKeyClick_Paffirmor(this,event)" class="required"/>'
            		+'</td>'
            		+'<td style="text-align: center">'
            			+'<c:if test="${affirmorListCnt != 1}">'
            			+'    <img id= "'+addRowID+'" src="/resources/images/+.gif" style="cursor:hand" title="添加" onclick="addPRowByIDEdit(this.id);"/>'
            			+'</c:if>&nbsp;&nbsp;&nbsp;'
            			+'    <img id= "'+addRowID+'" src="/resources/images/-.gif" style="cursor:hand" title="删除" '
            			//先删除，再排序
            			//+'	onclick="javaScript:document.all.addAffirm_listEdit.deleteRow(event.srcElement.parentElement.parentElement.rowIndex);delPRowByID();"/>'
            			+' onclick="deletePRowByIDEdit(this);"'
					+'</td>'
				+'</tr>';
            //当前行之后插入一行
            currentRow.after(str);
        }
    });
   	var tb2 = document.getElementById("addAffirm_listEdit");
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
function deletePRowByIDEdit(r){
	var i=r.parentNode.parentNode.rowIndex;
	document.getElementById('addAffirm_listEdit').deleteRow(i);
	//存在一种情况无法解决，同时添加两人，再删除第一人，则再添加新决裁者为第二位出现错位现象
	var tb2 = document.getElementById("addAffirm_listEdit");
	//如果决裁者只有一个时，删除添加的决裁者之后需要恢复原来的添加按钮
   	var affirmorListCnt = document.getElementById("affirmorListCnt").value;
   	var affirmorIdOnly = document.getElementById("affirmorIdOnly").value;
   	if(affirmorListCnt == 1){
   		var addStr = '<img id="'+affirmorIdOnly+'" src="/resources/images/+.gif" style="cursor:hand" title="添加" onclick="addPRowByIDEdit('+affirmorIdOnly+');"/>';
   		tb2.rows[0].cells[2].innerHTML = addStr;
	}
	var rowCount = tb2.rows.length;
	for(var m=0;m<rowCount;m++){
		tb2.rows[m].cells[0].innerHTML = m+1;
	}
}

function delPRowByID(){
	//存在一种情况无法解决，同时添加两人，再删除第一人，则再添加新决裁者为第二位出现错位现象
	var tb2 = document.getElementById("addAffirm_listEdit");
	   var rowCount = tb2.rows.length;
	   for(var m=0;m<rowCount;m++){
			tb2.rows[m].cells[0].innerHTML = m+1;
	   }
}

//添加新行的工号文本框触发
function F_HR_SubmitKeyClick1(i,event){
	var event = event || window.event;
   	if(event.keyCode==13){
   		var emp= document.getElementById("EMPID_"+i).value;
		//15119设置默认查找在职员工
		document.getElementById("onck").href=encodeURI(encodeURI("/hrm/transferOrder/viewEmpIdList?pageNum=1&seach_EMPID="+emp+'&seach_LOCAL_NAME='+emp+'&seach_IDCARD_NO='+emp+'&seach_EMP_OFFICE=15119&empid='+i+'&viewEmpIdListColnum='+i+"&seach_NAVID=ess" ));
		//document.getElementById("onck").href=encodeURI(encodeURI("/hrm/empinfo/viewEmpIdList?pageNum=1&seach_EMP_OFFICE=15119'"));
		document.getElementById("onck").click();	
	}
 }

var keyCodeInit=0;
function submitKeyClick_Paffirmor(obj,index,event){
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

		var  currentPersonid = $("#PERSON_ID").val();

		 
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
							document.getElementById("onck").href=encodeURI(encodeURI("/sys/arAffirmPost/viewAffirmorsEmpIdListNew?pageNum=1&navTabId=" + navTabId 
									+'&seach_EMPID='+empid
									+'&seach_LOCAL_NAME='+localName
									+'&seach_IDCARD_NO='+idcardNo
									+'&empId='+empIdStr
									+'&personId='+personIdStr  
									+'&empName='+empNameStr 
									));
							document.getElementById("onck").click();
						}
						if(jsonObject.perCnt==1&&currentPersonid!=jsonObject.personId){
							$("#empName" + index).val('['+jsonObject.empId + ']-'+jsonObject.empName);
							$("#empId" + index).val( jsonObject.personId);
							$("#personId"+index).val(jsonObject.personId);
				
						}
						if(jsonObject.perCnt==1&&currentPersonid==jsonObject.personId){
							alertMsg.error('决策人不能为自己！');
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
function uploadifySuccess_editPot(file, data, response){
	  //获取后台返回到前台的文件名，添加到隐藏域,多文件用";"号隔开
	  var files = $("#fileNmae",navTab.getCurrentPanel()).html();
	  var fileUrl = $("#fileUrl",navTab.getCurrentPanel()).val();
	  var fileName = $("#fileName",navTab.getCurrentPanel()).val();
	  var fileResult = data.split(";");
	  //第一个文件
	  if(files==""){
	    files = fileResult[0];
	    fileName = fileResult[0];
	    fileUrl = fileResult[1];
	  }else{
	    files+=";"+fileResult[0];
	    fileName+=";"+fileResult[0];
	    fileUrl+=";"+fileResult[1];
	  }
	  $("#fileNmae",navTab.getCurrentPanel()).html(files);
	  $("#fileUrl",navTab.getCurrentPanel()).val(fileUrl);
	  $("#fileName",navTab.getCurrentPanel()).val(fileName);
	} 
//-->


document.getElementById('FROM_DATE').attachEvent('onpropertychange',function(o){   
    if(o.propertyName!='value')return;  //不是value改变不执行下面的操作   
    //.......函数处理   onchange="getPOtApplyType();ajaxAdd_add_ot_apply_one_three();getShiftEndTime();"
    getPOtApplyType();
    getDefaultShiftTime();
   ajaxAdd_add_ot_apply_one_four();
 
});   

//加载完执行将加班类型变为只读
$(document).ready(function() {
	var $form = $("#updatePOtApply");

	$form.find("select[id='APPLY_TYPE_CODE_JUECAI']").attr("disabled",true);		//将加班类型设置为不可用

	});
</script>

<div class="pageContent">
	<div>
		<form id="updatePOtApply" name="updatePOtApply" method="post" action="/ess/affirmApply/updatePOtApply" class="pageForm required-validate" 
			onsubmit="return updatePOtApplyCallback(this,navTabAjaxDone);">
		   	<div class="formBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent"><!--保存-->
								<button id="but1" type="button" onclick="submitPUpdateFormPre(-1)">
									暂存
								</button>
							</div>
						</div>
					</li>
					<li>
						<div class="button">
							<div class="buttonContent"><!--提交-->
								<button id="but2" type="button" onclick="submitPUpdateFormPre(0)">
									提交
								</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
			<div>
				<table class="user_table" width="100%" border="0" cellpadding="0" cellspacing="0">
					

					<tr>
						
						<td width="20%" class="td_title" style="text-align:center">工号/姓名/部门
						<!-- 隐藏的一些参数 -->
						 <input  type="hidden" id="OT_APPLY_DATE" NAME="OT_APPLY_DATE" value=""></input>
								<input id="PERSON_ID" name="PERSON_ID" type="hidden" value="${otApplyInfo.PERSON_ID}"/>
			                    <input id="APPLY_NO" name="APPLY_NO" type="hidden" value="${otApplyInfo.APPLY_NO}"/>
			                    <input id="APPLY_TYPE_NO" name="APPLY_TYPE_NO" type="hidden" value="31"/>
			                    <input id="OT_TIME_TYPE" name="OT_TIME_TYPE" type="hidden" value="P"/>
							    <input id="AFFIRM_FLAG" name="AFFIRM_FLAG" type="hidden" value=""/>
							    <input id="EMP_TYPE_CODE" name="EMP_TYPE_CODE" type="hidden" value="${otApplyInfo.EMP_TYPE_CODE}"/>
							    <input type="hidden" id="LLASTMONTH" name="LLASTMONTH" value="${LLASTMONTH }"/>
							    <input type="hidden" id="LIMIT_FLAG" name="LIMIT_FLAG" value="${otApplyInfo.LIMIT_FLAG }"/>
							    <input id="CPNY_ID" name="CPNY_ID" type="hidden" value="${defaultCpny}" />
							 	<input type="hidden" id="dateType" name="dateType" value=""/>
								<input type="hidden" id="peopleType" name="peopleType" value=""/>
								<input type="hidden" id="APPLY_TYPE_CODE" name="APPLY_TYPE_CODE" value=""/>
								
						</td>
						<td width="30%" class="td_type">${otApplyInfo.EMPID} / ${otApplyInfo.LOCAL_NAME} / ${otApplyInfo.DEPARTMENT}</td>
						<td width="20%" class="td_title" style="text-align:center">申请日期</td>
						<td width="30%" class="td_type">${otApplyInfo.CREATE_DATE}</td>
					</tr>
					
					<tr>
						<td width="20%" class="td_title" style="text-align:center"><!-- 开始日期 -->
							<spring:message code="public.title.startDate"/>
						</td>
						<td width="30%" class="td_type">
							<!--触发对了，但是只有Firefox不好使-->
						    <input type="text" id="FROM_DATE" name="FROM_DATE" class="date required"  
						    	  format="yyyy-MM-dd" readonly="true" value="${otApplyInfo.FROM_DATE}"/>   
							<%--Firefox好使,但是触发不对，需要多次执行
						    <input type="text" id="FROM_DATE" name="FROM_DATE" class="date required"  onblur="getPOtApplyTypeEdit();"
						    	 format="yyyy-MM-dd" readonly="true" value="${otApplyInfo.FROM_DATE}"/>
						    --%>						    	 
						    <a class="inputDateButton" href="javascript:;"><spring:message code="public.title.choose"/><!-- 选择 --></a>
						</td>
						<td width="20%" class="td_title" style="text-align:center"><!--加班类型-->
							<spring:message code="ess.viewApply.title.overtimeApplyType"/>
						</td>
						<td width="30%" class="td_type">
						    <ait:SelectSyCodeByCpnyID  id="APPLY_TYPE_CODE_JUECAI" name="APPLY_TYPE_CODE" parentNo="31" cnpyID="${defaultCpny}" 
						    	selected="${otApplyInfo.APPLY_TYPE_CODE}" limit="all" onChangeName="calPoTRemarkEdit();ajaxAdd_add_ot_apply_one_four();"/>
						    <select id="OT_PLACE_TYPE" name="OT_PLACE_TYPE" onchange="getChangeOtType();calPoTRemarkEdit();calPoTLengthEdit();">
						    	<option value="INSIDE" <c:if test="${otApplyInfo.OT_PLACE_TYPE eq 'INSIDE' }">selected</c:if>>社内</option>
						    	<option value="OUTSIDE" <c:if test="${otApplyInfo.OT_PLACE_TYPE eq 'OUTSIDE'}">selected</c:if>>社外</option>
						    </select>
						</td>
					</tr>
					
					<tr>
						<td width="20%" class="td_title" style="text-align:center"><!-- 开始时间 -->
							开始时间
						</td>
						<td width="30%" class="td_type">
						    <c:if test="${defaultCpny ne 'LGEQH'}">
							<ait:time name="OT_FROM_TIME" spacing="30" selected="${otApplyInfo.FROM_TIME}" onChange="calPoTLengthEdit();getChangeOtType();"/>
							</c:if>
							<c:if test="${defaultCpny eq 'LGEQH'}">
							<select name="OT_FROM_TIME_H" id="OT_FROM_TIME_H" onChange="calPoTLengthEdit();getChangeOtType();">
								 <c:forEach items="${hourParam}" var="vlist" varStatus="i">
								     <option value="${vlist.OT_TIME}" <c:if test="${vlist.OT_TIME eq otApplyInfo.FROM_TIME_H}">selected</c:if>>${vlist.OT_TIME}</option>
							     </c:forEach>
						    </select>
						    <select name="OT_FROM_TIME_M" id="OT_FROM_TIME_M" onChange="calPoTLengthEdit();getChangeOtType();">
								 <c:forEach items="${muniteParam}" var="list" varStatus="i">
								     <option value="${list.OT_TIME}" <c:if test="${list.OT_TIME eq otApplyInfo.FROM_TIME_M}">selected</c:if>>${list.OT_TIME}</option>
							     </c:forEach>
						    </select>
						    </c:if>
						    <input type="hidden" id="OT_FROM_TIME_FLAG" name="OT_FROM_TIME_FLAG" value="${otApplyInfo.FROM_TIME}"/>
						</td>
						<td width="20%" class="td_title" style="text-align:center"><!--是否转调休-->
					    	是否转调休测试
					    </td>
					    <td width="30%" class="td_type">
					    	<input type="radio" id="ADJUST_YN_N" name="ADJUST_YN" value="0" onclick="getChangeOtType();calPoTRemarkEdit();ajaxAdd_add_ot_apply_one_four();" title="否" 
					    		<c:if test="${otApplyInfo.ADJUST_YN eq '0'}">checked="checked"</c:if> <c:if test="${  defaultCpny eq 'LGEYT'}">disabled</c:if>/>否&nbsp;&nbsp;&nbsp;
					    	<input type="radio" id="ADJUST_YN_Y" name="ADJUST_YN" value="1" onclick="getChangeOtType();calPoTRemarkEdit();ajaxAdd_add_ot_apply_one_four();" title="是"
					    		<c:if test="${otApplyInfo.ADJUST_YN eq '1' }">checked="checked"</c:if> <c:if test="${  defaultCpny eq 'LGEYT'}">disabled</c:if>/>是
					    </td>
					</tr>
					
					<tr>
						<td width="20%" class="td_title" style="text-align:center"><!-- 结束时间 -->
							结束时间
						</td>
						<td width="30%" class="td_type">
						  <c:if test="${defaultCpny ne 'LGEQH'}">
						    <ait:time name="OT_TO_TIME" spacing="30" selected="${otApplyInfo.TO_TIME}" onChange="getChangeOtType();calPoTLengthEdit();"/>	
						   </c:if>	
						   <c:if test="${defaultCpny eq 'LGEQH'}">
							<select name="OT_TO_TIME_H" id="OT_TO_TIME_H" onChange="getChangeOtType();calPoTLengthEdit();calPoTRemarkEdit();">
								 <c:forEach items="${hourParam}" var="vlist" varStatus="i">
								     <option value="${vlist.OT_TIME}" <c:if test="${vlist.OT_TIME eq otApplyInfo.TO_TIME_H}">selected</c:if>>${vlist.OT_TIME}</option>
							     </c:forEach>
						    </select>
						    <select name="OT_TO_TIME_M" id="OT_TO_TIME_M" onChange="getChangeOtType();calPoTLengthEdit();calPoTRemarkEdit();">
								 <c:forEach items="${muniteParam}" var="list" varStatus="i">
								     <option value="${list.OT_TIME}" <c:if test="${list.OT_TIME eq otApplyInfo.TO_TIME_M}">selected</c:if>>${list.OT_TIME}</option>
							     </c:forEach>
						    </select>
						    </c:if>	    
						</td>  
						<td width="20%" class="td_title" style="text-align:center"><!--是否跨天-->
					    	是否跨天 
					    </td>
					    <td width="30%" class="td_type">
					    	<input type="hidden" id="BEGIN_DAY_OFFSET" name="BEGIN_DAY_OFFSET" value="0"/>
					    	<input type="radio" id="END_DAY_OFFSET" name="END_DAY_OFFSET" value="0" title="否" onclick="getChangeOtType();calPoTLengthEdit();calPoTRemarkEdit();"
					    		<c:if test="${otApplyInfo.END_DAY_OFFSET eq '0'}">checked="checked"</c:if>/>否
					    	&nbsp;&nbsp;&nbsp;
					    	<input type="radio" id="END_DAY_OFFSET" name="END_DAY_OFFSET" value="1" title="是" onclick="getChangeOtType();calPoTLengthEdit();calPoTRemarkEdit();"
					    		<c:if test="${otApplyInfo.END_DAY_OFFSET eq '1'}">checked="checked"</c:if>/>是
					    </td>
					</tr>
					
					<tr>
						<td width="20%" class="td_title" style="text-align:center">
							时间长度
						</td>
						<td width="30%" class="td_type" >
							<div id="otApplyLengthEdit"></div>		
									<input type="hidden" id="Lotlengthtwo" name="Lotlengthtwo" value=""/> 
							
						</td>
						<td width="20%" class="td_title" style="text-align:center"><!--是否特殊加班-->
					    	是否特殊加班
					    </td>
					    <td width="30%" class="td_type">
					    	<input type="radio" id="TESHU_YN_N" name="TESHU_YN" value="0" title="否" checked="checked"  onclick="getChangeOtType();calPoTLengthEdit();calPoTRemarkEdit();"
					    	 <c:if test="${otApplyInfo.TESHU_YN eq '0'}">checked="checked"</c:if>/>否
					    	&nbsp;&nbsp;&nbsp;
					    	<input type="radio" id="TESHU_YN_Y" name="TESHU_YN" value="1" title="是" onclick="getChangeOtType();calPoTRemarkEdit();calPoTLengthEdit();"
					    	<c:if test="${otApplyInfo.TESHU_YN eq '1'}">checked="checked"</c:if>/>是
					    </td>
					</tr>
					<c:if test="${defaultCpny ne 'LGEQD'}">
					<c:if test="${defaultCpny ne 'LGEYT'}">
					<tr>
					    <td width="20%" class="td_title" style="text-align:center"><!--人事政策-->
					    	人事政策
					    </td>
					    <td width="30" class="td_type" colspan="3">
					    	 
					    		<div id="otPApplyRemarkEdit" style="color:red">
					    		</div>
					    	 
					    </td>

					   </tr>
					   </c:if>
					   </c:if>
					   <c:if test="${defaultCpny eq 'LGEYT'}">
					   <td width="20%" class="td_title" style="text-align:center">
					    	本月总时长(包含本次申请)
					    </td>
					    <td width="30" class="td_type">
					    	 
					    		<div id="otApplyLengthZong">
					    		</div>
					   				<input type="hidden" id="LotlengthoneZong" name="LotlengthoneZong" value=""/>  	 
					    </td>
					    <td width="20%" class="td_title" style="text-align:center"><!--人事政策-->
					    	人事政策
					    </td>
					    <td width="30" class="td_type">
					    	 
					    		<div id="otPApplyRemarkEdit" style="color:red">
					    		</div>
					    	 
					    </td>
					   </c:if>
					   <c:if test="${defaultCpny eq 'LGEQD'}">
					 <td width="20%" class="td_title" style="text-align:center">
					    	本月总时长(包含本次申请)
					    </td>
					    <td width="30" class="td_type">
					    	 
					    		<div id="otApplyLengthZong">
					    		</div>
					   				<input type="hidden" id="LotlengthoneZong" name="LotlengthoneZong" value=""/>  	 
					    </td>
					    <td width="20%" class="td_title" style="text-align:center"><!--人事政策-->
					    	人事政策
					    </td>
					    <td width="30" class="td_type">
					    	 
					    		<div id="otPApplyRemarkEdit" style="color:red">
					    		</div>
					    	 
					    </td>
					   </c:if>
						</tr>					
<!-- 					<tr> -->
<!-- 					    <td width="20%" class="td_title" style="text-align:center">人事政策 -->
<!-- 					    	人事政策 -->
<!-- 					    </td> -->
<!-- 					    <td width="80%" class="td_type" colspan="3"> -->
<!-- 					    	<font color="red"> -->
<!-- 					    		<div id="otPApplyRemarkEdit"></div> -->
<!-- 					    	</font> -->
<!-- 					    </td> -->
<!-- 					</tr> -->
					<tr>						
					    <td width="20%" class="td_title" style="text-align:center"><!--加班事由-->
					    	加班事由
					    </td>
					    <td width="30%" class="td_type">
					    	<textarea style="width:400px;height:100px" id="APPLY_REMARK" name="APPLY_REMARK">${otApplyInfo.APPLY_OT_REMARK }</textarea>
					    </td>
									<td width="20%" class="td_title" style="text-align:center">
										附件上传
									</td>
									<td width="30%" class="td_type">
									    <input id="testFileInput_editPot" type="file" name="file" 
												uploaderOption="{
													swf:'/resources/js/uploadify/scripts/uploadify.swf',
													uploader:'/ess/infoApplyLeave/uploadBatch?PERSON_ID=${otApplyInfo.PERSON_ID}',
													formData:{ajax:1},
													queueID:'fileQueue_editPot',
													buttonText:'请选择',
													height:25,
													width:50,
													auto:false,
													onUploadSuccess:uploadifySuccess_editPot,
													removeTimeout:1
												}"
											/><span id="fileNmae">${otApplyInfo.FILE_NAME}</span>
										  <div id="fileQueue_editPot" class="fileQueue"></div>
										  <input type="hidden" id="fileUrl" name="fileUrl" value="${otApplyInfo.FILE_URL}"/>
										  <input type="hidden" id="fileName" name="fileName" value="${otApplyInfo.FILE_NAME}"/>
											<div class="buttonActive">
												<div class="buttonContent"><!--保存-->
													<button type="button" onclick="$('#testFileInput_editPot').uploadify('upload', '*');return false;">
														上传
													</button>
												</div>
											</div>
											<div class="buttonActive">
												<div class="buttonContent"><!--提交-->
													<button type="button" onclick="$('#testFileInput_editPot').uploadify('cancel', '*');return false;">
														取消
													</button>
												</div>
											</div>
									</td>
								</tr>	
					
								<tr>
								<td colspan="4">
									<table class="user_table" width="100%">	
										<tr>
											<td class="td_title" rowspan="2" width="25%">决裁线</td>
											<td class="td_title" width="25%">决裁等级</td>
											<td class="td_title" width="25%">决裁者</td>
											<td class="td_title" width="25%">是否新增(<img src="/resources/images/+.gif" title="添加" border="0" align="absmiddle" style="cursor:hand" onclick="addRowByIDApplyLOTEditFirst()"/>)</td>
										</tr>
										<tr>
											<td colspan="3">
												<table width="100%" border="0" cellpadding="0" cellspacing="0" id="addApplyPOTAffirm_listTwo">
													<tbody>
													</tbody>
												</table>
											</td>	
										</tr>
									</table>
								</td>
								</tr>
				 </table>
					<input type="hidden" id="affirmorListCnt" name="affirmorListCnt" value="${affirmorListCnt }"/>
					<input type="hidden" name="applyOtCount_1" id="applyOtCount_1" value="">
					<a id="onck" name="onck"  href="" lookupGroup="person"></a>
			</div>
	  	</form>	
	</div>
</div>