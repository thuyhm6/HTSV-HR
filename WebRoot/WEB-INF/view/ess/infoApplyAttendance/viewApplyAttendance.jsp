<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript">
 
 
 

var ajaxGet_getShiftTime;
function getShiftTime2(){
	if (ajaxGet_getShiftTime != null) {
		ajaxGet_getShiftTime.abort();
	}
	var LEAVE_FROM_TIME = $("#LEAVE_FROM_TIME",navTab.getCurrentPanel()).val();
	var LEAVE_TO_TIME = $("#LEAVE_TO_TIME",navTab.getCurrentPanel()).val();
	if(LEAVE_FROM_TIME != ""){
	  dateStr = $("#LEAVE_FROM_TIME",navTab.getCurrentPanel()).val();
	}else{
	  dateStr = $("#LEAVE_TO_TIME",navTab.getCurrentPanel()).val();
	}
	var CPNY_ID = $("#viewApplyLeaveInfo input[id='CPNY_ID']").val();
	var PERSON_ID = $("#viewApplyLeaveInfo input[id='PERSON_ID']").val();
	if(dateStr != null && dateStr != ""){
		ajaxGet_getShiftTime = $.ajax({
			 cache: false,
			 type: 'post',
			 async:false,
			 url: "/ess/infoApply/getDefaultStartEndTime",
			 data: [{ name : 'APPLY_OT_DATE' , value : dateStr },
			        { name : 'PERSON_ID' , value : $("#PERSON_ID").val() },
			        { name : 'CPNY_ID' , value : CPNY_ID }],
			 dataType:"json",
			 success: function(data) {
			           // var sDate = data.START_TIME.substr(0,2)+':'+data.START_TIME.substr(2,2);
			            //var eDate = data.END_TIME.substr(0,2)+':'+data.END_TIME.substr(2,2);
			            var sDate = '08:00';
			            var eDate = '17:00';
					    $("#fromTime").val(sDate);
						$("#toTime").val(eDate);
						var leave_from_date = $("#viewApplyLeaveInfo input[id='LEAVE_FROM_TIME']");
						var leave_to_date = $("#viewApplyLeaveInfo input[id='LEAVE_TO_TIME']");
						if(leave_from_date != '' && leave_to_date != ''){
						 	callength();
						}
			 }
		});
	}
}


function xiujialeixing(id){

	<c:if test="${defaultCpny eq 'TSTO'}">
	//	年假
	if('14013814' == id){
		document.getElementById('view_nianjia_sub').style.display = 'block';
		$("#view_nianjia_sub_title").css('display','block');
	}else{
		document.getElementById('view_nianjia_sub').style.display = 'none';
		$("#view_nianjia_sub_title").css('display','none');
	}
	//	倒休
	if('14013816' == id){
		document.getElementById('view_daoxiu_sub').style.display = 'block';
		$("#view_daoxiu_sub_title").css('display','block');
	}else{
		document.getElementById('view_daoxiu_sub').style.display = 'none';
		$("#view_daoxiu_sub_title").css('display','none');
	}
	</c:if>
	
	<c:if test="${defaultCpny eq 'SST'}">
	//	年假
	if('14013814' == id){
		document.getElementById('view_nianjia_sub').style.display = 'block';
		$("#view_nianjia_sub_title").css('display','block');
	}else{
		document.getElementById('view_nianjia_sub').style.display = 'none';
		$("#view_nianjia_sub_title").css('display','none');
	}
	//	倒休
	if('14013816' == id){
		document.getElementById('view_daoxiu_sub').style.display = 'block';
		$("#view_daoxiu_sub_title").css('display','block');
	}else{
		document.getElementById('view_daoxiu_sub').style.display = 'none';
		$("#view_daoxiu_sub_title").css('display','none');
	}
	</c:if>

	var PERSON_ID = $("#viewApplyLeaveInfo input[id='PERSON_ID']").val();
	//加载Leave日期
	$.ajax({
		 cache: false,
		 type: 'post',
		 async:false,
		 url: "/ess/infoApplyAttendance/getLeaveDate",
		 data: [{ name: 'apply_type', value: id },
		        { name: 'PERSON_ID', value: PERSON_ID }
				 ],
		 dataType:"json",
		 success: function(data) {
			if(data.statusCode == 300){
				alertMsg.error(data.message);
				$("select[name='LEAVE_APPLY_TYPE_CODE']").val('');
			}
			if(data.statusCode == 200){
				
			}
		 }
	});
	
	var fromDate = $("#LEAVE_FROM_TIME",navTab.getCurrentPanel()).val();
	var CPNY_ID =$("#CPNY_ID",navTab.getCurrentPanel()).val();

	if($("#viewPOtApplyInfo input[name='LEAVE_FROM_TIME']").val()!= ""){
		$.ajax({
			 cache: false,
			 type: 'post',
			 async:false,
			 url: "/ess/infoApplyAttendance/getLeaveDispalydaoxiuOrnianjMap",
			 data: [{ name : 'arDateStr' , value :fromDate },
			        { name : 'CPNY_ID' , value :CPNY_ID },
			        { name : 'PERSON_ID' , value :PERSON_ID}],
			 dataType:"json",
			 success: function(data) {
			    $("#last_adjust").html(data.SHENGYU_TX_P);
			    $("#this_adjust").html(data.SHENGYU_TX);
			    $("#TOT_VAC_CNT").html(data.TOT_VAC_CNT);
			    $("#SHENGYU_VAC_CNT").html(data.SHENGYU_VAC_CNT);
			    $("#nianjia_shengyu_count").val(data.SHENGYU_VAC_CNT);
			 }
		});
	 }
}
/**
 123495	病假2
14013809	护理假
14013810	停工休假
14013811	哺乳集体
14013812	产假 1
14013813	研修
14013814	年休假
14013815	补班
14013816	倒休
14013817	半日半休假
14013818	会议培训
15501	           工伤1
15821	           病假
16415	          哺乳假
18135	          事假
218134	         公假
22	                    婚假
23	                    丧假
25	                   工伤
27	产假
300012	计划生育
4084	出差
482	产前检查假
  
 */
function submitFormPre(flag){
	$("#viewApplyLeaveInfo").attr("action","/ess/infoApplyAttendance/addLeaveAttendance");
	$("#viewApplyLeaveInfo").attr("enctype","multipart/form-data");
	$("#viewApplyLeaveInfo").attr("target","callbackframe");
	
	$("#viewApplyLeaveInfo").attr("class","pageForm required-validate");
	$("#viewApplyLeaveInfo").attr("onsubmit","return validateApplyLeaveCallback(this,navTabAjaxDoneWithForm);");
	
	$("#LEAVE_APPLY_AFFIRM_FLAG_ESS0240").val(flag);
  	var $from = $("#viewApplyLeaveInfo");
  	$from.submit();
}
function validateApplyLeaveCallback(form,callback) {
	var $form = $(form);	
	var leaveTimeType  = document.getElementById("LEAVE_TIME_TYPE").value;
	var apply_time  = document.getElementById("APPLY_TIME").value;
    var person_id  = document.getElementById("PERSON_ID").value;
    var applyTypeNo = document.getElementById("APPLY_TYPE_NO").value;
    var applyTypeCode = document.getElementById("LEAVE_APPLY_TYPE_CODE").value;
    var PERSON_ID = document.getElementById("PERSON_ID").value;
    var cpnyId = document.getElementById("CPNY_ID").value;
    var TX_SHENGYU = document.getElementById("TX_SHENGYU").value;
    var APPLY_LENGTH = document.getElementById("shenqingshichang").value;
    var destination = document.getElementById("destination").value;
    var liaison = document.getElementById("liaison").value;
    var approvType = document.getElementById("liaison").value;
    var vacition = document.getElementById("nianjia_shengyu_count").value;
    // alert(person_id);
    if(PERSON_ID==""){
		alertMsg.error('请选择Leave人！');
		return false;
	}
  	
    	
    	var leave_from_date = $("#viewApplyLeaveInfo input[id='LEAVE_FROM_TIME']").val();
    	var fromTime = $("#viewApplyLeaveInfo select[id='fromTime']").val();
    	var leave_to_date = $("#viewApplyLeaveInfo input[id='LEAVE_TO_TIME']").val();
    	var toTime = $("#viewApplyLeaveInfo select[id='toTime']").val();
    	var applyCountYN;
    	var LOCKYN;
    	$.ajax({
			cache: false,
		    type: 'post', 
			async:false,
			url: "/ess/infoApplyAttendance/getApplyCountYN",
			data: [
				{ name: 'leave_from_date', value: leave_from_date },
				{ name: 'fromTime', value: fromTime },
				{ name: 'leave_to_date', value: leave_to_date },
				{ name: 'toTime', value: toTime },
				{ name: 'PERSON_ID', value: PERSON_ID }],
			dataType:"json",
			success: function(data) {
			
			   applyCountYN = data.applyCountYN;
			}
		});
    	var AR_DATE_STR = $("#LEAVE_FROM_TIME",navTab.getCurrentPanel()).val();
    	$.ajax({
			cache: false,
		    type: 'post', 
			async:false,
			url: "/ess/infoApplyAttendance/getApplyLOCKYN",
			data: [
				{ name: 'applyBatchdate', value: AR_DATE_STR },
				{ name: 'PERSON_ID', value: PERSON_ID }
				],
			dataType:"json",
			success: function(data) {
			
			   LOCKYN = data.LOCKYN;
			}
		});
		
		if(LOCKYN=="Y"){
    		alertMsg.info('当前考勤申请已锁定！');
    		return false;
   	    }
		
    	if(applyCountYN == 'Y'){
    	   alertMsg.error(leave_from_date+'到'+leave_to_date+'已有休假申请,请重新选择休假时间段');
    		return false;
    	}
    	
    	if(leave_from_date==""){
    		alertMsg.error('请选择Leave开始时间！');
    		return false;
   	    }
    	if(APPLY_LENGTH==0){
    		alertMsg.error('请选择休假时长！');
    		return false;
   	    }
    	
    	if(leave_to_date==""){
    		alertMsg.error('请选择Leave结束时间！');
    		return false;
    	}
    	
   	    if(applyTypeCode==""){
   	    	alertMsg.error('请选择Leave类型！');
   		    return false;
   		}
   	    if(applyTypeCode=="14013814"){
   	        if(vacition <= 0 ){
	   	    	alertMsg.error('年假剩余天数不足,不能再做年假申请!');
	   		    return false;
   		    }
   		}

   		var leavefromtime = leave_from_date + " " + fromTime + ":" + "00";
		var leavetotime = leave_to_date + " " + toTime + ":" + "00";
		if(comptime(leavefromtime,leavetotime)!=1){
			alertMsg.error('考勤开始时间不能早于考勤 结束时间');
			return false;
		}

	var flag = $("#LEAVE_APPLY_AFFIRM_FLAG_ESS0240").val();
	var result = "确定要提交吗？";
	if( flag == -1){
		result = "确定要暂存吗？";
	}
	alertMsg.confirm(result,
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
		return 'exception'
	}
}
//注意input的id和tr的id要一样
function addRowByID(currentRowID){
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
						+'	onkeydown="submitKeyClick_affirmor(this,event)" class="required"/>'
            		+'</td>'
            		+'<td style="text-align: center">'
            			+'<img id= "'+addRowID+'" src="/resources/images/+.gif" style="cursor:hand" title="添加" onclick="addRowByID(this.id);"/>&nbsp;&nbsp;&nbsp;'
            			+'<img id= "'+addRowID+'" src="/resources/images/-.gif" style="cursor:hand" title="删除" '
            			//先删除，再排序
            			+'	onclick="javaScript:document.all.addAffirm_list.deleteRow(event.srcElement.parentElement.parentElement.rowIndex);delRowByID();"/>'
					+'</td>'
				+'</tr>';
            //当前行之后插入一行
            currentRow.after(str);
        }
    });
   var tb2 = document.getElementById("addAffirm_list");
   var rowCount = tb2.rows.length;
   for(var m=0;m<rowCount;m++){
		tb2.rows[m].cells[0].innerHTML = m+1;
   }
}
function delRowByID(){
	//存在一种情况无法解决，同时添加两人，再删除第一人，则再添加新决裁者为第二位出现错位现象
	var tb2 = document.getElementById("addAffirm_list");
	   var rowCount = tb2.rows.length;
	   for(var m=0;m<rowCount;m++){
			tb2.rows[m].cells[0].innerHTML = m+1;
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
			url: encodeURI('/sys/affirm/getPersonCnt?navTabId=' + navTabId + '&EMPID='+empid+'&LOCAL_NAME='+localName+'&IDCARD_NO='+idcardNo + '&seach_type=apply'),
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
 
function submitKeyClick_apply_Leave(obj,event){
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
							document.getElementById(empNameStr).value='['+jsonObject.empId + ']-'+jsonObject.empName;
							if(jsonObject.perCnt==1){
								uploadfy_destory();
								$.pdialog.reload("/ess/infoApplyLeave/viewApplyLeaveInfo" + "?navTabId=" + "ess0241" + "&PERSON_ID=" + jsonObject.personId + "&APPLY_TYPE_NO=" + "21");
							}
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
//计算时长
function callength_date(){
//	取值判断
	var is_equal_from_time = $("#viewApplyLeaveInfo input[id='is_equal_from_time']").val();
	var leave_from_date = $("#viewApplyLeaveInfo input[id='LEAVE_FROM_TIME']").val();
	var is_equal_to_time = $("#viewApplyLeaveInfo input[id='is_equal_to_time']").val();
	var leave_to_date = $("#viewApplyLeaveInfo input[id='LEAVE_TO_TIME']").val();
	
//  赋值
	$("#viewApplyLeaveInfo input[id='is_equal_from_time']").val(leave_from_date);
	$("#viewApplyLeaveInfo input[id='is_equal_to_time']").val(leave_to_date);
	
	if((is_equal_from_time != leave_from_date
			&& '' != leave_from_date)
			|| (is_equal_to_time != leave_to_date
					&& '' != leave_to_date)){
		callength();
	}
}

function callength(){
	var leave_from_date = $("#viewApplyLeaveInfo input[id='LEAVE_FROM_TIME']").val();
	var fromTime = $("#viewApplyLeaveInfo select[id='fromTime']").val();
	var leave_to_date = $("#viewApplyLeaveInfo input[id='LEAVE_TO_TIME']").val();
	var toTime = $("#viewApplyLeaveInfo select[id='toTime']").val();
	var leavefromtime = leave_from_date + " " + fromTime + ":" + "00";
	var leavetotime = leave_to_date + " " + toTime + ":" + "00";
	var applyTypeCode = $(":input[name='LEAVE_APPLY_TYPE_CODE']").val();
	if(comptime(leavefromtime,leavetotime)==1){
		var PERSON_ID = $("#viewApplyLeaveInfo input[id='PERSON_ID']").val();
		var CPNY_ID = $("#viewApplyLeaveInfo input[id='CPNY_ID']").val();
		$.ajax({
			 cache: false,
			 type: 'post',
			 async:false,
			 url: "/ess/infoApplyAttendance/getShenqingshichang",
			 data: [{ name: 'PERSON_ID', value: PERSON_ID },  
			        { name: 'CPNY_ID', value: CPNY_ID },
			        { name: 'leavefromtime', value: leavefromtime },
			        { name: 'leavetotime', value: leavetotime },
			        { name: 'applyTypeCode', value: applyTypeCode }],
			 dataType:"json",
			 success: function(data) {
				$("#shenqingshichangText").html(data.lengthStr);
				$("#shenqingshichang").val(data.length);
			 }
		});
	}
}

var ajaxGet_add_leave_apply;
$(document).ready(
function ajaxAdd_add_leave_apply() {
	if (ajaxGet_add_leave_apply != null) {
		ajaxGet_add_leave_apply.abort();
	}
	
		$.ajaxSettings.global = false;
		ajaxGet_add_leave_apply = $.ajax( {
			type : "POST",
			url : "/ess/infoApplyAttendance/getAffirmList",
			data : {applyParentType : '21',applyType : $(":input[name='LEAVE_APPLY_TYPE_CODE']").val(),applyLength:$("#shenqingshichang").val(),personId : $("#PERSON_ID").val()},
			dataType : "json",
			success : function(data) {
				$('#addApplyLeaveAffirm_list').html("");
				var html = "";
				if (typeof (data['affirmList']) != "undefined") {

					$.each(data['affirmList'],
									function(commentIndex, comment) {
										html += '<tr id="rowIdApplyLeave' + commentIndex  + '">';
										html += '<td class="td_type" style="text-align: center" width="5%">' + (commentIndex + 1) + '</td>';
										html += '<td class="td_type" style="text-align: center" width="20%">审批 </td>';
										html += '<td class="td_type" style="text-align: center" width="25%">[' + comment['EMPID']
												+ ']-' + comment['LOCAL_NAME'];
										html += '<input type="hidden" name="AFFIRMOR_ID" value="' + comment['AFFIRMOR_ID'] + '"/></td>';
										html += '<td class="td_type" style="text-align: center" width="25%">' + comment['LOCAL_NAME'] + '/'+comment['POSTIONNAME']+'/'+comment['DEPTNAME']+'</td>';
										html += '<td class="td_type" style="text-align: center" width="25%">';
										html += '<img src="/resources/images/+.gif" title="添加" border="0" align="absmiddle" style="cursor:hand" onclick="addRowByIDApplyLeave('+ commentIndex +')"/>';
										html += '</td>';
										$("#applyLeaveCount").val(commentIndex + 1);
									});
				}
				$('#addApplyLeaveAffirm_list').html(html);
			}
		});
		$.ajaxSettings.global = true;
});

//添加决裁者
function addRowByIDApplyLeave(currentRowID){
	var count = parseInt($("#applyLeaveCount").val());
    var htm  ='<tr id="rowIdApplyLeave'+ count +'"><td class="td_type" style="text-align: center" width="5%"><span name="rowIndex"></span></td>';
        htm +='<td class="td_type" style="text-align: center" width="20%">';
        htm +='<input type="radio" id="approvType' + count + '" name="approvType' + count + '" value="1001" checked="checked" />审批 ';
        htm +='<input type="radio" id="approvType' + count + '" name="approvType' + count + '" value="1002" />协议'; 
        htm +='<input type="radio" id="approvType' + count + '" name="approvType' + count + '" value="1003" />通报';
        htm +='</td>';
		htm +='<td class="td_type" style="text-align: center" width="25%">';
		htm +='<input id="dwz.person.AFFIRMOR_IDApplyLeave' + count + '" name="AFFIRMOR_ID" value="" type="hidden"/>';
		htm +='<input id="dwz.person.EMPINFOApplyLeave' + count + '" name="empid" type="text" alt="请输入关键字按回车检索" class="required" lookupGroup="person" size="30" onkeydown="submitKeyClick_applyLeave(this,' + count + ',event)"/>';
		htm +='</td>';
		htm +='<td class="td_type" style="text-align: center" width="25%">';
		htm +='<input id="dwz.person.InfoEMPINFOApplyLeave' + count + '"  type="text"  size="40" disabled="disabled"/>';
		htm +='</td>';
		htm +='<td class="td_type" style="text-align: center" width="25%">';
		htm +='<img src="/resources/images/+.gif" title="添加"';	
		htm +='border="0" align="absmiddle" style="cursor:hand" onclick="addRowByIDApplyLeave(' + count + ')"/>&nbsp;&nbsp;&nbsp;';
		htm +='<img src="/resources/images/-.gif" title="删除"';	
		htm +='border="0" align="absmiddle" style="cursor:hand" onclick="javaScript:document.all.addApplyLeaveAffirm_list.deleteRow(event.srcElement.parentElement.parentElement.rowIndex);changeApplyLeaveLevel();"/></td></tr>';

   	//当前行之后插入一行
   	$("#rowIdApplyLeave" + currentRowID).after(htm);
  	$("[id='dwz.person.EMPINFOApplyLeave" + count + "']").inputAlert();
   	changeApplyLeaveLevel();
  	$("#applyLeaveCount").val(++count) ;
}

function addRowByIDApplyLeaveFirst(){
	var count = parseInt($("#applyLeaveCount").val());
    var htm  ='<tr id="rowIdApplyLeave'+ count +'"><td class="td_type" style="text-align: center" width="5%"><span name="rowIndex"></span></td>';
        htm +='<td class="td_type" style="text-align: center" width="20%">';
        htm +='<input type="radio" id="approvType' + count + '" name="approvType' + count + '" value="1001" checked="checked" />审批 ';
        htm +='<input type="radio" id="approvType' + count + '" name="approvType' + count + '" value="1002" />协议'; 
        htm +='<input type="radio" id="approvType' + count + '" name="approvType' + count + '" value="1003" />通报';
        htm +='</td>';
		htm +='<td class="td_type" style="text-align: center" width="33%">';
		htm +='<input id="dwz.person.AFFIRMOR_IDApplyLeave' + count + '" name="AFFIRMOR_ID" value="" type="hidden"/>';
		htm +='<input id="dwz.person.EMPINFOApplyLeave' + count + '" name="empid" type="text" alt="请输入关键字按回车检索" size="30" class="required" lookupGroup="person" onkeydown="submitKeyClick_applyLeave(this,' + count + ',event)"/>';
		htm +='</td>';
		htm +='<td class="td_type" style="text-align: center" width="25%">';
		htm +='<input id="dwz.person.InfoEMPINFOApplyLeave' + count + '"  type="text"  size="40" disabled="disabled"/>';
		htm +='</td>';
		htm +='<td class="td_type" style="text-align: center" width="25%">';
		htm +='<img src="/resources/images/+.gif" title="添加"';	
		htm +='border="0" align="absmiddle" style="cursor:hand" onclick="addRowByIDApplyLeave(' + count + ')"/>&nbsp;&nbsp;&nbsp;';
		htm +='<img src="/resources/images/-.gif" title="删除"';	
		htm +='border="0" align="absmiddle" style="cursor:hand" onclick="javaScript:document.all.addApplyLeaveAffirm_list.deleteRow(event.srcElement.parentElement.parentElement.rowIndex);changeApplyLeaveLevel();"/></td></tr>';


	var tb2 = document.getElementById("addApplyLeaveAffirm_list");
   	if(tb2.rows.length == 0){
   		$("#addApplyLeaveAffirm_list").html(htm);
   	} else{
   	   	//当前行之后插入一行
   	   	$("#" + tb2.rows[0].id).before(htm);
   	}
  	$("[id='dwz.person.EMPINFOApplyLeave" + count + "']").inputAlert();
   	changeApplyLeaveLevel();
  	$("#applyLeaveCount").val(++count) ;
}
//修改决裁者等级
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
							  	$("[id='dwz.person.EMPINFOApplyLeave" + index + "']").val('['+jsonObject.empId + ']-'+jsonObject.empName);
							  	$("[id='dwz.person.AFFIRMOR_IDApplyLeave" + index + "']").val( jsonObject.personId);
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
}
</script>
<div class="panel"><h1>考勤申请</h1>
<div>
<%@ include file="/WEB-INF/view/hrm/empinfo/viewPersonalInfoHead_ess.jsp"%>
</div>
</div>
<div class="pageContent" layouth="10">
	<div>
		<form id="viewApplyLeaveInfo" method="post" action="/ess/infoApplyLeave/addLeaveApply" class="pageForm required-validate" 
			onsubmit="return validateApplyLeaveCallback(this,navTabAjaxDoneWithForm);">
			<div>
				<table class="user_table" width="100%" border="1" cellpadding="2" cellspacing="1">
					
					<tr>
						<td>
							<table  class="user_table" width="100%">
								<input id="CPNY_ID" name="CPNY_ID" type="hidden" size="30" value="${defaultCpny}" />
								<input type="hidden" id="APPLY_TIME" name="APPLY_TIME" readonly="true" value="${DEFAULT_APPLY_TIME}"/>
								<input id="PERSON_ID" name="dwz.person.personId1" type="hidden" size="30" value="${personInfo.PERSON_ID}" lookupGroup="person" />
								<%--考勤申请类型 --%>
								<input id="LEAVE_TIME_TYPE" name="LEAVE_TIME_TYPE" type="hidden" size="30" value="21" />
							    <input id="APPLY_TYPE_NO" name="APPLY_TYPE_NO" type="hidden" size="30" value="21" />
								<input id="APPLY_TYPE" name="APPLY_TYPE" type="hidden" value="PERSON" />
								<input id="LEAVE_APPLY_AFFIRM_FLAG_ESS0240" name="AFFIRM_FLAG" type="hidden" value="" />
								<input id="TX_SHENGYU" name="TX_SHENGYU" type="hidden" value="4" />
								<tr>
								   <td style="text-align:right" width="20%" class="td_title">
										考勤类型
									</td>
									<td width="30%" class="td_type">
					                    	<ait:SelectSyCodeCombinByCpnyID name="LEAVE_APPLY_TYPE_CODE" combinParentNo="21" 
					                    	 exclude=" 123495 ,14013809,14013811,14013812,14013813,14013814,14013815,14013816,14013810,14015344,14015345,14015346,14015347,14015348,14015349,
					                    	 14013816,14013817,14013818,15501,15821,16415,18135,218134,22,23,25,27,300012,482,123495,14015171"
					                    	 cnpyID="${defaultCpny}" selected="${leaveApplyMap.LEAVE_TYPE_CODE}"  
					                    	 onChangeName="xiujialeixing(this.value);callength();"/>
									</td>									
									<td style="text-align:right" width="20%" class="td_title">
										申请时长
									</td>
									<td width="30%" class="td_type">
										<div id="shenqingshichangText"></div>
										<input type="hidden" id="shenqingshichang" name="APPLY_LENGTH" value="0"/>
										<input type="hidden" id="LEAVE_TIME_TYPE" name="LEAVE_TIME_TYPE" value="P"/>
									</td>
								</tr>
								<tr>
									<td style="text-align:right" width="20%" class="td_title">
										开始时间
									</td>
									<td width="30%" class="td_type" >
										<span id="leave_date_default_start"></span>
										<input type="text" name="LEAVE_FROM_TIME" id="LEAVE_FROM_TIME"  class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',onpicked:callength_date,onpicked:getShiftTime2})" 
										  value="${CREATE_DATE}"    />
										<input type="hidden" id="is_equal_from_time" name="is_equal_from_time" value=""/>
										 <select id="fromTime" name="fromTime" onchange="callength();">
									       <option value="08:00" <c:if test="${toTime eq '08:00'}"></c:if>  selected="selected">08:00</option>
									       <option value="12:00">12:00</option>
									     </select>
										<%--<ait:time name="fromTime" spacing="30"  onChange="callength();"/>--%>
									</td>
									<td width="20%" class="td_title" style="text-align:right">
										<span id="view_nianjia_sub_title" 
									   		<c:if test="${leaveApplyMap.LEAVE_TYPE_CODE eq '14013814'">
									   			style="display:block"
									   		</c:if>
									   		<c:if test="${leaveApplyMap.LEAVE_TYPE_CODE ne '14013814'" >
									   			style="display:none"
									   		</c:if>>年假天数</span>
									</td>
									<td width="30%" class="td_type">
									   	<div id="view_nianjia_sub" 
									   		<c:if test="${leaveApplyMap.LEAVE_TYPE_CODE eq '14013814'">
									   			style="display:block"
									   		</c:if>
									   		<c:if test="${leaveApplyMap.LEAVE_TYPE_CODE ne '14013814'">
									   			style="display:none"
									   		</c:if>
									   		>
									   		年假天数:<div id="TOT_VAC_CNT" style="display: inline"></div>/剩余年假天数:<div id="SHENGYU_VAC_CNT" style="display: inline"></div>
									   		<input type="hidden" id="nianjia_shengyu_count" name="nianjia_shengyu_count" value=""/>
									   	</div>
									 </td>
									
								</tr>
								<tr>
									<td style="text-align:right" width="20%" class="td_title">
										结束时间
									</td>
									<td width="30%" class="td_type">
										<span id="leave_date_default_end"></span>
										<input type="text" name="LEAVE_TO_TIME" id="LEAVE_TO_TIME"  class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',onpicked:callength_date,onpicked:getShiftTime2})" 
										value="${CREATE_DATE}"  onchange="getShiftTime(1,this.value);" openChange="true"
										/>
									    <input type="hidden" id="is_equal_to_time" name="is_equal_to_time" value=""/>
									    
									     <select id="toTime" name="toTime" onchange="callength();">
									       <option value="13:00">13:00</option>
									       <option value="17:00" <c:if test="${toTime eq '17:00'}"></c:if>  selected="selected" >17:00</option>
									     </select>
									    
									   <%-- <ait:time name="toTime" spacing="30"  selected="08:00"  onChange="callength();"/>--%> 
									</td>
									<td width="20%" style="text-align:right" class="td_title">
										<span id="view_daoxiu_sub_title" 
									   		<c:if test="${leaveApplyMap.LEAVE_TYPE_CODE eq '14013816' and defaultCpny eq 'TSTO'}">
									   			style="display:block"
									   		</c:if>
									   		<c:if test="${leaveApplyMap.LEAVE_TYPE_CODE ne '14013816' or defaultCpny ne 'TSTO'}">
									   			style="display:none"
									   		</c:if>>倒休</span>
									</td>
									<td width="30%" class="td_type">
									   	<div id="view_daoxiu_sub" 
									   		<c:if test="${leaveApplyMap.LEAVE_TYPE_CODE eq '14013816' and defaultCpny eq 'TSTO'}">
									   			style="display:block"
									   		</c:if>
									   		<c:if test="${leaveApplyMap.LEAVE_TYPE_CODE ne '14013816' or defaultCpny ne 'TSTO'}">
									   			style="display:none"
									   		</c:if>
									   		>
									   		上月倒休剩余: <div id ="last_adjust" style="display:inline" ></div>    /本月剩余倒休:<div id ="this_adjust" style="display:inline"></div>
									   	</div>
									</td> 
								</tr>
								<tr>
									<td style="text-align:right" width="20%" class="td_title">
										其他原因
									</td>
									<td width="80%" class="td_type" colspan="3">
										<textarea name="LEAVE_REASON"  style="width:500px;height:100px"> ${leaveApplyMap.LEAVE_REASON}</textarea>
									</td>
								</tr>
								<tr>
								   <td width="20%" style="text-align:right" class="td_title">
										目的地
									</td>
									<td width="30%" class="td_type">
									    <input text="text" name="destination" style="width:250px;" id="destination" />
									</td>
									<td width="20%"  style="text-align:right" class="td_title">
										联络处
									</td>
									<td width="30%" class="td_type">
										<input text="text" name="liaison" style="width:250px;" id="liaison"  />
									</td>   
								</tr>	
							</table>	
						</td>
					</tr>
				    <tr>
				    <table class="user_table" width="100%">
				    <tr height="15px">	
				    <span >* Select Approval</span>
				    </tr>
				    </table>
				    </tr>
					<tr>
						<td>
							<table class="user_table" width="100%">	
								<tr>
								    <td class="td_title"  style="text-align:center;" width="5%">序号</td>
									<td class="td_title"  style="text-align:center;" width="20%">审批区分</td>
									<td class="td_title" style="text-align:center;" width="25%">审批人</td>
									<td class="td_title" style="text-align:center;" width="25%">审批人信息</td>
									<td class="td_title" style="text-align:center;" width="25%">是否新增(<img src="/resources/images/+.gif" title="添加" border="0" align="absmiddle" style="cursor:hand" onclick="addRowByIDApplyLeaveFirst()"/>)</td>
								</tr>
								<tr>
									<td colspan="5">
										<table width="100%" border="0" cellpadding="0" cellspacing="0" id="addApplyLeaveAffirm_list">
										</table>
										<a id="onck" name="onck"  href="" lookupGroup="person" rel="submitKeyClick_apply_Leave_affirm"></a>
									</td>	
								</tr>
							</table>
						</td>
					</tr>
				</table>
		    <input type="hidden" name="applyLeaveCount" id="applyLeaveCount" value="0">
			</div>
			<div class="formBar">
				<ul>
					<li>
						<div class="button">
							<div class="buttonContent"><!--提交-->
								<button type="button" onclick="submitFormPre(0)">
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