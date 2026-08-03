<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript">

var ajaxGet_getShiftTime;
function getShiftTime(flag,dateStr){

	if (ajaxGet_getShiftTime != null) {
		ajaxGet_getShiftTime.abort();
	}
	var CPNY_ID = $("#viewApplyLeaveInfo input[id='CPNY_ID']").val();
	if(dateStr != null && dateStr != "" && CPNY_ID != 'TSTO'){
		ajaxGet_getShiftTime = $.ajax({
			 cache: false,
			 type: 'post',
			 async:false,
			 url: "/ess/infoApplyLeave/getShiftTime",
			 data: [{ name : 'DATE_STR' , value : dateStr },
			        { name : 'PERSON_ID' , value : $("#PERSON_ID").val() },
			        { name : 'FLAG' , value : flag }],
			 dataType:"json",
			 success: function(response) {
					 	if(flag == 1){
							 $("#fromTime").val(response);
					 	}else{
							 $("#toTime").val(response);
					 	}
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
	//	哺乳假
	if('16415' == id){
		document.getElementById('view_buru_sub').style.display = 'block';
		$("#view_buru_sub_title").css('display','block');
	}else{
		document.getElementById('view_buru_sub').style.display = 'none';
		$("#view_buru_sub_title").css('display','none');
	}
	$.ajax({
		 cache: false,
		 type: 'post',
		 async:false,
		 url: "/ess/infoApplyLeave/getZhengce",
		 data: [{ name: 'apply_type', value: id }],
		 dataType:"json",
		 success: function(response) {
			 document.getElementById('zhengce').innerHTML = response;
		 }
	});

	var PERSON_ID = $("#viewApplyLeaveInfo input[id='PERSON_ID']").val();
	//加载Leave日期
	$.ajax({
		 cache: false,
		 type: 'post',
		 async:false,
		 url: "/ess/infoApplyLeave/getLeaveDate",
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
				if(id == '141474' || id == '141475'){
					var leave_from_date = $("#viewApplyLeaveInfo input[id='LEAVE_FROM_TIME']");
					var leave_to_date = $("#viewApplyLeaveInfo input[id='LEAVE_TO_TIME']");
					leave_from_date.val(data.leaveDate);
					leave_to_date.val(data.leaveDate);
					leave_from_date.hide();
					leave_to_date.hide();
					$("#LEAVE_FROM_TIME_IMG").hide();
					$("#LEAVE_TO_TIME_IMG").hide();
					$("#leave_date_default_start").html(data.leaveDate);
					$("#leave_date_default_end").html(data.leaveDate);
				}else{
					if($("#viewApplyLeaveInfo input[id='LEAVE_FROM_TIME']").css("display") == "none"){
						$("#viewApplyLeaveInfo input[id='LEAVE_FROM_TIME']").show();
						$("#viewApplyLeaveInfo input[id='LEAVE_TO_TIME']").show();
						$("#LEAVE_FROM_TIME_IMG").show();
						$("#LEAVE_TO_TIME_IMG").show();
						$("#leave_date_default_start").html("");
						$("#leave_date_default_end").html("");
					}
				}
			}
		 }
	});
}
/**
 * 141473	/	福利年假
 141474	/	妇女节假
 141475	/	青年节假
 15501	/	事假
 16415	/	哺乳假
 18135	/	加班调休
 217873	/	季节休假
 218112	/	销假
 218134	/	公假
 218182	/	陪护假
 218246	/	节育手术假
 218247	/	探亲假
 218340	/	停产假
 218395	/	无薪休息日
 218396	/	公司纪念日
 22	/	婚假
 23	/	丧假
 23619	/	流产假
 24	/	病假
 25	/	工伤假
 26	/	法定年假
 27	/	产假
 28	/	陪产假
 482	/	产期检查假
 219977   /  开斋节
  
 */
//提交前附件上传的限制
function checkAttachment(){
	var CPNY_ID = $("#viewApplyLeaveInfo input[id='CPNY_ID']").val();
	var id = $("#LEAVE_APPLY_TYPE_CODE").val();
	if(CPNY_ID == 'TSTO'){
		if( id == 22 || id == 24 || id == 482 || id == 27 || id == 16415 || id == 23619 || id == 25 || id == 28){
			var attFlag = $("#fileUrl").val();
			if( attFlag == ''){
				alertMsg.error("请先上传附件");
				return false;
			}
		}
	}
	if(CPNY_ID == 'LGEHZ'){
		if( id == 22 || id == 23 || id == 24 || id == 27 || id == 28 || id == 23619 || id == 25){
			var attFlag = $("#fileUrl").val();
			if( attFlag == ''){
				alertMsg.error("请先上传附件");
				return false;
			}
		}
	}
	if(CPNY_ID == 'LGEND'){
		if( id == 218134 ){
			var attFlag = $("#fileUrl").val();
			if( attFlag == ''){
				alertMsg.error("请先上传附件");
				return false;
			}
		}
	}
	if(CPNY_ID == 'LGEQA'){
		if( id == 22 || id == 27 || id == 16415 ){
			var attFlag = $("#fileUrl").val();
			if( attFlag == ''){
				alertMsg.error("请先上传附件");
				return false;
			}
		}
	}
	if(CPNY_ID == 'LGEQD'){
		if( id == 22 || id == 27 || id == 16415 || id == 24 || id == 25 ){
			var attFlag = $("#fileUrl").val();
			if( attFlag == ''){
				alertMsg.error("请先上传附件");
				return false;
			}
		}
	}
	if(CPNY_ID == 'LGEQH'){
		if( id == 22 || id == 23 || id == 482 || id == 24 || id == 27 || id == 16415 ){
			var attFlag = $("#fileUrl").val();
			if( attFlag == ''){
				alertMsg.error("请先上传附件");
				return false;
			}
		}
	}
	if(CPNY_ID == 'LGETR'){
		if( id == 22 || id == 23 || id == 482 || id == 24 || id == 27 || id == 23619 || id == 25 || id == 28 || id ==  218246 || id == 218247 ){
			var attFlag = $("#fileUrl").val();
			if( attFlag == ''){
				alertMsg.error("请先上传附件");
				return false;
			}
		}
	}
	if(CPNY_ID == 'LGEYT'){
		if( id == 22 || id == 27 || id == 16415 || id == 24 || id == 25 ){
			var attFlag = $("#fileUrl").val();
			if( attFlag == ''){
				alertMsg.error("请先上传附件");
				return false;
			}
		}
	}
	return true;
}
function submitFormPre(flag){
	$("#viewApplyLeaveInfo").attr("action","/ess/infoApplyLeave/addAssigmentLeaveApply");
	$("#viewApplyLeaveInfo").attr("enctype","multipart/form-data");
	$("#viewApplyLeaveInfo").attr("target","callbackframe");
	
	$("#viewApplyLeaveInfo").attr("class","pageForm required-validate");
	$("#viewApplyLeaveInfo").attr("onsubmit","return validateApplyLeaveCallback(this,dialogAjaxDoneWithForm);");
	
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
    var sub_applyTypeCode = document.getElementById("SUB_LEAVE_APPLY_TYPE_CODE").value;
    var PERSON_ID = document.getElementById("PERSON_ID").value;
    var cpnyId = document.getElementById("CPNY_ID").value;
    var TX_SHENGYU = document.getElementById("TX_SHENGYU").value;
    var APPLY_LENGTH = document.getElementById("shenqingshichang").value;
    if(PERSON_ID==""){
		alertMsg.error('请选择Leave人！');
		return false;
	}
  	//提交前附件上传的限制
    if( !checkAttachment()){
        return false;
    }
    	
    	var leave_from_date = $("#viewApplyLeaveInfo input[id='LEAVE_FROM_TIME']").val();
    	var fromTime = $("#viewApplyLeaveInfo select[id='fromTime']").val();
    	var leave_to_date = $("#viewApplyLeaveInfo input[id='LEAVE_TO_TIME']").val();
    	var toTime = $("#viewApplyLeaveInfo select[id='toTime']").val();
    	
    	if(leave_from_date==""){
    		alertMsg.error('请选择Leave开始时间！');
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

   		if(cpnyId == 'LGEND' && TX_SHENGYU >= 0.5 && applyTypeCode == '18135' && APPLY_LENGTH%4 != 0){
   			alertMsg.error('加班调休请按四小时为单位申请！');
   			return false;
   		}
   		var leavefromtime = leave_from_date + " " + fromTime + ":" + "00";
		var leavetotime = leave_to_date + " " + toTime + ":" + "00";
		if(comptime(leavefromtime,leavetotime)!=1){
			alertMsg.error('<spring:message code="alert.message.ess.infoApply.startTimeCanNotLaterThanEndTime"/>');
			return false;
		}

	var flag = $("#LEAVE_APPLY_AFFIRM_FLAG_ESS0240").val();
	var result = "确定要发令吗？";
	alertMsg.confirm(result,
  		  	{okCall:function(){
		  	$.ajax({
  				type: form.method || 'POST',
  				url:$form.attr("action"),
  				data:$form.serializeArray(),
  				dataType:"json",
  				cache: false,
  				success: callback || DWZ.ajaxDone,
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
 
function submitKeyClick_apply_assigmentLeave(obj,event){
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
								$.pdialog.reload("/ess/infoApplyLeave/viewAssigmentLeaveInfo" + "?navTabId=" + "ess0241" + "&PERSON_ID=" + jsonObject.personId + "&APPLY_TYPE_NO=" + "21");
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
//上传附件的按钮
function shangchuan(){
	
	var PERSON_ID = document.getElementById("PERSON_ID").value;
	if(PERSON_ID == ''){
		alertMsg.error('请先选择Leave人员!');
		return false;
	}
	$("#viewApplyLeaveInfo").attr("action","/ess/infoApplyLeave/upload?PERSON_ID="+PERSON_ID);
	$("#viewApplyLeaveInfo").attr("enctype","multipart/form-data");
	$("#viewApplyLeaveInfo").attr("target","callbackframe");
	
	$("#viewApplyLeaveInfo").attr("class","pageForm");
	$("#viewApplyLeaveInfo").attr("onsubmit","return iframeCallback_uploadfile(this,attUploadApplyLeave);");
	
}

function iframeCallback_uploadfile(form, callback){
	var filePath = document.getElementById("file1").value;
	if(document.getElementById("file1").value == ''){
		alertMsg.error('请先选择要上传附件!');
		return false;
	}
	var $form = $(form), $iframe = $("#callbackframe");
	var $iframe = $("#callbackframe");

	if ($iframe.size() == 0) {
		$iframe = $("<iframe id='callbackframe' name='callbackframe' src='about:blank' style='display:none'></iframe>");
		$form.appendTo($iframe);
	}
	if(!form.ajax) {
		$form.append('<input type="hidden" name="ajax" value="1" />');
	}
	_iframeResponse($iframe[0], callback || DWZ.ajaxDone);
}
//附件上传成功回调函数
function attUploadApplyLeave(json){
	DWZ.ajaxDone(json);
	$("#attUploadFlagApplyLeave").val("Y");
}
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
			 url: "/ess/infoApplyLeave/getShenqingshichang",
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

function delfileName(){
	document.getElementById('fujianm').style.display = 'none';
	document.getElementById('fujianv').style.display = 'block';
}

var ajaxGet_add_leave_apply;
function ajaxAdd_add_leave_apply() {
	if (ajaxGet_add_leave_apply != null) {
		ajaxGet_add_leave_apply.abort();
	}
	if($("#PERSON_ID").val() != '' && $("#shenqingshichang").val() != '' && $(":input[name='LEAVE_APPLY_TYPE_CODE']").val() != ''){
		$.ajaxSettings.global = false;
		ajaxGet_add_leave_apply = $.ajax( {
			type : "POST",
			url : "/ess/infoApplyLeave/getAffirmList",
			data : {applyParentType : '21',applyType : $(":input[name='LEAVE_APPLY_TYPE_CODE']").val(),applyLength:$("#shenqingshichang").val(),personId : $("#PERSON_ID").val()},
			dataType : "json",
			success : function(data) {
				$('#addApplyLeaveAffirm_list').html("");
				var html = "";
				if (typeof (data['affirmList']) != "undefined") {
					$.each(data['affirmList'],
									function(commentIndex, comment) {
										html += '<tr id="rowIdApplyLeave' + commentIndex  + '">';
										html += '<td class="td_type" style="text-align: center" width="33%">' + (commentIndex + 1) + '</td>';
										html += '<td class="td_type" style="text-align: center" width="33%">[' + comment['EMPID']
												+ ']-' + comment['LOCAL_NAME'];
										html += '<input type="hidden" name="AFFIRMOR_ID" value="' + comment['AFFIRMOR_ID'] + '"/></td>';
										html += '<td class="td_type" style="text-align: center" width="33%">';
											html += '<img src="/resources/images/+.gif" title="添加" border="0" align="absmiddle" style="cursor:hand" onclick="addRowByIDApplyLeave('+ commentIndex +')"/>';
										html += '</td>';
										$("#applyLeaveCount").val(commentIndex + 1);
									});
				}
				$('#addApplyLeaveAffirm_list').html(html);
			}
		});
		$.ajaxSettings.global = true;
	}
}

//添加决裁者
function addRowByIDApplyLeave(currentRowID){
	var count = parseInt($("#applyLeaveCount").val());
    var htm  ='<tr id="rowIdApplyLeave'+ count +'"><td class="td_type" style="text-align: center" width="33%"><span name="rowIndex"></span></td>';
		htm +='<td class="td_type" style="text-align: center" width="33%">';
		htm +='<input id="dwz.person.AFFIRMOR_IDApplyLeave' + count + '" name="AFFIRMOR_ID" value="" type="hidden"/>';
		htm +='<input id="dwz.person.EMPINFOApplyLeave' + count + '" name="empid" type="text" alt="请输入关键字按回车检索" class="required" lookupGroup="person" size="30" onkeydown="submitKeyClick_applyLeave(this,' + count + ',event)"/>';
		htm +='</td>';
		htm +='<td class="td_type" style="text-align: center" width="33%">';
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
    var htm  ='<tr id="rowIdApplyLeave'+ count +'"><td class="td_type" style="text-align: center" width="33%"><span name="rowIndex"></span></td>';
		htm +='<td class="td_type" style="text-align: center" width="33%">';
		htm +='<input id="dwz.person.AFFIRMOR_IDApplyLeave' + count + '" name="AFFIRMOR_ID" value="" type="hidden"/>';
		htm +='<input id="dwz.person.EMPINFOApplyLeave' + count + '" name="empid" type="text" alt="请输入关键字按回车检索" size="30" class="required" lookupGroup="person" onkeydown="submitKeyClick_applyLeave(this,' + count + ',event)"/>';
		htm +='</td>';
		htm +='<td class="td_type" style="text-align: center" width="33%">';
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

function uploadifySuccess_Leave(file, data, response){
  //获取后台返回到前台的文件名，添加到隐藏域,多文件用";"号隔开
  var files = $("#fileNmae",$.pdialog.getCurrent()).html();
  var fileUrl = $("#fileUrl",$.pdialog.getCurrent()).val();
  var fileName = $("#fileName",$.pdialog.getCurrent()).val();
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
  $("#fileNmae",$.pdialog.getCurrent()).html(files);
  $("#fileUrl",$.pdialog.getCurrent()).val(fileUrl);
  $("#fileName",$.pdialog.getCurrent()).val(fileName);
}

<c:if test="${!empty leaveApplyMap.PERSON_ID}">
$(function(){
	xiujialeixing($("#LEAVE_APPLY_TYPE_CODE").val());
	callength();
});
</c:if>
</script>

<div class="pageContent" layouth="10">
	<div>
		<form id="viewApplyLeaveInfo" method="post" action="/ess/infoApplyLeave/addAssigmentLeaveApply" class="pageForm required-validate" 
			onsubmit="return validateApplyLeaveCallback(this,dialogAjaxDoneWithForm);">
		   <div class="formBar">
				<ul>
					<li>
						<div class="button">
							<div class="buttonContent"><!--提交-->
								<button type="button" onclick="submitFormPre(5)">
									发令
								</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
			<div>
				<table class="user_table" width="100%" border="1" cellpadding="2" cellspacing="1">
					<tr>
						<td>
							<!-- 显示年假与调休 -->
							<table  class="user_table" width="100%">
							   <thead>
								   	<tr>
								   		<td width="50%" class="td_title" colspan="6" style="text-align:center">年假</td>
								   		<td width="50%" class="td_title" colspan="3" style="text-align:center">调休</td>
								   	</tr>
							   </thead>
							   <tbody>
								   	<tr>
								   		<td class="td_title" colspan="2" style="text-align:center">法定年假</td>
								   		<td class="td_title" colspan="2" style="text-align:center">福利年假</td>
								   		<td class="td_title" rowspan="2" style="text-align:center">已用天数</td>
								   		<td class="td_title" rowspan="2" style="text-align:center">剩余天数</td>
								   		<td class="td_title" rowspan="2" style="text-align:center">调休天数</td>
								   		<td class="td_title" rowspan="2" style="text-align:center">已用天数</td>
								   		<td class="td_title" rowspan="2" style="text-align:center">剩余天数</td>
								   	</tr>
								   	<tr>
								   		<td class="td_type td_center">本年年假</td>
								   		<td class="td_type td_center">移年年假</td>
								   		<td class="td_type td_center">福利年假</td>
								   		<td class="td_type td_center">福利年假调整</td>
								   	</tr>
								   	<tr>
								   		<td class="td_type td_center" width="11%">${empVacInfo.TOT_VAC_CNT1 }</td>
								   		<td class="td_type td_center" width="11%">${empVacInfo.LAST_YEAR_VAC1 }</td>
								   		<td class="td_type td_center" width="11%">${empVacInfo.TOT_VAC_CNT2 }</td>
								   		<td class="td_type td_center" width="11%">${empVacInfo.ADD_VAC }</td>
								   		<td class="td_type td_center" width="11%">${empVacInfo.USE_VAC }</td>
								   		<td class="td_type td_center" width="11%">${empVacInfo.SURPLUS_VAC }</td>
								   		<td class="td_type td_center" width="11%">${empVacInfo.TX_TOTAL }</td>
								   		<td class="td_type td_center" width="11%">${empVacInfo.TX_USE }</td>
								   		<td class="td_type td_center" width="12%">${empVacInfo.TX_SHENGYU }</td>
								   	</tr>
							   	</tbody>
							</table>
						</td>
					</tr>
					<tr>
						<td>
							<table  class="user_table" width="100%">
								<tr>
									<td width="100%" class="td_title" colspan="4" style="text-align: center">Leave申请</td>
								</tr>
								<tr>
									<td width="20%" class="td_title">申请人</td>
									<td width="30%" class="td_type">
										<c:if test="${!empty leaveApplyMap.PERSON_ID || authority ne '1'}">
											${personInfo.EMPID}
											<input id="APPLY_NO" name="APPLY_NO" type="hidden" value="${leaveApplyMap.APPLY_NO}" />
											<input id="UPDATE_FLAG" name="UPDATE_FLAG" type="hidden" value="${update_page}" />
						                    <input id="empName_apply" name="dwz.person.empName1" value="${personInfo.LOCAL_NAME}" readonly style="border:0;background:transparent;"
						                    	type="text" lookupGroup="person"/>
						                    <input id="PERSON_ID" name="dwz.person.personId1" type="hidden" size="30"
												   value="${personInfo.PERSON_ID}" lookupGroup="person"/>
										</c:if>
										<c:if test="${empty leaveApplyMap.PERSON_ID && authority eq '1'}">
											<input id="empId_apply" name="dwz.person.empId1" value="${personInfo.EMPID}" type="text" lookupGroup="person"
												onkeydown="submitKeyClick_apply_assigmentLeave(this,event)" class="required"/>
						                    <input id="empName_apply" name="dwz.person.empName1" value="${personInfo.LOCAL_NAME}" readonly style="border:0;background:transparent;"
						                    	type="text" lookupGroup="person"/>
						                    <input id="PERSON_ID" name="dwz.person.personId1" type="hidden" size="30"
												   value="${personInfo.PERSON_ID}" lookupGroup="person"  rel="submitKeyClick_apply_assigmentLeave"/>
										</c:if>
										<input id="CPNY_ID" name="CPNY_ID" type="hidden" size="30" value="${defaultCpny}" />
										<%--考勤申请类型 --%>
										<input id="LEAVE_TIME_TYPE" name="LEAVE_TIME_TYPE" type="hidden" size="30" value="21" />
										<input id="APPLY_TYPE_NO" name="APPLY_TYPE_NO" type="hidden" size="30" value="21" />
										<input id="APPLY_TYPE" name="APPLY_TYPE" type="hidden" value="PERSON" />
										<input id="LEAVE_APPLY_AFFIRM_FLAG_ESS0240" name="AFFIRM_FLAG" type="hidden" value="" />
										<input id="TX_SHENGYU" name="TX_SHENGYU" type="hidden" value="4" />
										<%--判断附件是否上传 --%>
										<input id="IS_UPLOAD" name="IS_UPLOAD" type="hidden" value="" />
									</td>
									<td width="20%" class="td_title">
										申请日期
									</td>
									<td width="30%" class="td_type">
										<c:if test="${empty leaveApplyMap.APPLY_TIME}">
											${DEFAULT_APPLY_TIME}
											<input type="hidden" id="APPLY_TIME" name="APPLY_TIME" readonly="true" value="${DEFAULT_APPLY_TIME}"/>
					 					</c:if>
									    <c:if test="${!empty leaveApplyMap.APPLY_TIME}">
									    	${leaveApplyMap.APPLY_TIME}
											<input type="hidden" id="APPLY_TIME" name="APPLY_TIME" readonly="true" value="${leaveApplyMap.APPLY_TIME}"/>
										</c:if>
									</td>
								</tr>
								<tr>
									<td width="20%" class="td_title">
										开始时间
									</td>
									<td width="30%" class="td_type" >
										<span id="leave_date_default_start"></span>
										<input type="text" id="LEAVE_FROM_TIME" name="LEAVE_FROM_TIME" class="date required" format="yyyy-MM-dd" 
					                    	readonly="true" value="${leaveApplyMap.LEAVE_FROM_TIME}" onchange="callength_date();getShiftTime(1,this.value);" openChange="true"/>
					                    <a id="LEAVE_FROM_TIME_IMG" class="inputDateButton" href="javascript:;"><spring:message code="public.title.choose"/><!-- 选择 --></a>
										<input type="hidden" id="is_equal_from_time" name="is_equal_from_time" value=""/>
									    <c:if test="${defaultCpny eq 'TSTO'}">
									    	<ait:time name="fromTime" special="08:30,13:00" selected="08:30" onChange="callength();"/>
									    </c:if>
									    <c:if test="${defaultCpny ne 'TSTO'}">
											<ait:time name="fromTime" spacing="30" selected="${leaveApplyMap.FROMTIME}" onChange="callength();"/>
									    </c:if>
									</td>
									<td width="20%" class="td_title">
										考勤类型
									</td>
									<td width="30%" class="td_type">
					                    <ait:SelectSyCodeCombinByCpnyID name="LEAVE_APPLY_TYPE_CODE" combinParentNo="21,18" exclude="218112" cnpyID="${defaultCpny}" selected="${leaveApplyMap.LEAVE_TYPE_CODE}" limit="all" onChangeName="xiujialeixing(this.value);callength();"/>
									</td>
								</tr>
								<tr>
									<td width="20%" class="td_title">
										结束时间
									</td>
									<td width="30%" class="td_type">
										<span id="leave_date_default_end"></span>
										<input type="text" id="LEAVE_TO_TIME" name="LEAVE_TO_TIME" class="date required" format="yyyy-MM-dd" 
									    	readonly="true" value="${leaveApplyMap.LEAVE_TO_TIME}" onchange="callength_date();getShiftTime(0,this.value);" openChange="true"/>
									    <a id="LEAVE_TO_TIME_IMG" class="inputDateButton" href="javascript:;"><spring:message code="public.title.choose"/><!-- 选择 --></a>
									    <input type="hidden" id="is_equal_to_time" name="is_equal_to_time" value=""/>
										<c:if test="${defaultCpny eq 'TSTO'}">
										    <ait:time name="toTime" special="12:00,17:30" selected="17:30" onChange="callength();"/>
										</c:if>
										<c:if test="${defaultCpny ne 'TSTO'}">
									    	<ait:time name="toTime" spacing="30" selected="${leaveApplyMap.TOTIME}" onChange="callength();"/>
										</c:if>
									</td>
									<td width="20%" class="td_title">
										<span id="view_buru_sub_title" 
									   		<c:if test="${leaveApplyMap.LEAVE_TYPE_CODE eq '16415' }">
									   			style="display:block"
									   		</c:if>
									   		<c:if test="${leaveApplyMap.LEAVE_TYPE_CODE ne '16415' }">
									   			style="display:none"
									   		</c:if>>哺乳假类型</span>
									</td>
									<td width="30%" class="td_type">
									   	<div id="view_buru_sub" 
									   		<c:if test="${leaveApplyMap.LEAVE_TYPE_CODE eq '16415' }">
									   			style="display:block"
									   		</c:if>
									   		<c:if test="${leaveApplyMap.LEAVE_TYPE_CODE ne '16415' }">
									   			style="display:none"
									   		</c:if>
									   		>
									   		<ait:SelectSyCodeByCpnyID name="SUB_LEAVE_APPLY_TYPE_CODE" selected="${leaveApplyMap.SUB_LEAVE_TYPE_CODE}" parentNo="218107" cnpyID="${defaultCpny}"/>
									   	</div>
									</td> 
								</tr>
								<tr>
									<td width="20%" class="td_title">
										申请时长
									</td>
									<td width="30%" class="td_type">
										<div id="shenqingshichangText"></div>
										<input type="hidden" id="shenqingshichang" name="APPLY_LENGTH" value="0"/>
										<input type="hidden" id="LEAVE_TIME_TYPE" name="LEAVE_TIME_TYPE" value="P"/>
									</td>
									<td width="20%" class="td_title">
										人事政策
									</td>
									<td width="30%" class="td_type">
										<div id="zhengce"></div>
									</td>   
								</tr>
								<tr>
									<td width="20%" class="td_title">
										申请事由
									</td>
									<td width="30%" class="td_type">
										<textarea name="LEAVE_REASON"  style="width:400px;height:100px">${leaveApplyMap.LEAVE_REASON}</textarea>
									</td>
									<td width="20%" class="td_title">
										附件上传
									</td>
									<td width="30%" class="td_type">
										 <input id="testFileInput_Leave" type="file" name="file" 
												uploaderOption="{
													swf:'/resources/js/uploadify/scripts/uploadify.swf',
													uploader:'/ess/infoApplyLeave/uploadBatch?PERSON_ID=' + ${personInfo.PERSON_ID},
													formData:{ajax:1},
													queueID:'fileQueue_Leave',
													buttonText:'请选择',
													height:25,
													width:50,
													auto:false,
													onUploadSuccess:uploadifySuccess_Leave,
													removeTimeout:1
												}"
											/>
										  <span id="fileNmae">${leaveApplyMap.FILE_NAME}</span>
										  <div id="fileQueue_Leave" class="fileQueue"></div>
										  <input type="hidden" id="fileUrl" name="fileUrl" value="${leaveApplyMap.FILE_URL}"/>
										  <input type="hidden" id="fileName" name="fileName" value="${leaveApplyMap.FILE_NAME}"/>
										  
											<div class="buttonActive">
												<div class="buttonContent"><!--保存-->
													<button type="button" onclick="$('#testFileInput_Leave').uploadify('upload', '*');return false;">
														上传
													</button>
												</div>
											</div>
											<div class="buttonActive">
												<div class="buttonContent"><!--提交-->
													<button type="button" onclick="$('#testFileInput_Leave').uploadify('cancel', '*');return false;">
														取消
													</button>
												</div>
											</div>
									</td>  
								</tr>	
							</table>	
						</td>
					</tr>
				</table>
		    <input type="hidden" name="applyLeaveCount" id="applyLeaveCount" value="">
			</div>
	  	</form>	
	</div>
</div>