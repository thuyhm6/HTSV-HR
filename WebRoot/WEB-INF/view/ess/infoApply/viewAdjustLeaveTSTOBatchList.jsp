<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<script>
//<!--

$(document).ready(function(){ 
    
     $("#viewAdjustLeaveTSTOBatchList_Serch",navTab.getCurrentPanel()).click(function(){
			$("#viewAdjustLeaveTSTOBatchList",navTab.getCurrentPanel()).submit();
	   });
	  //搜索
     $("#seach_KEY",navTab.getCurrentPanel()).keydown(function(e) {
        if ( e.keyCode == 13) {
       	 	var name=encodeURI(encodeURI($('#seach_KEY',navTab.getCurrentPanel()).val()));
       	    var FROM_DATE=encodeURI(encodeURI($('#seach_FROM_DATE',navTab.getCurrentPanel()).val()));
       	    var TO_DATE=encodeURI(encodeURI($('#seach_TO_DATE',navTab.getCurrentPanel()).val()));
       		$('.btnLook',navTab.getCurrentPanel()).attr('href','/ar/attendanceMintenance/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=viewAdjustLeaveTSTOBatchList&seach_KEY='+name+'&seach_FROM_DATE='+FROM_DATE+'&seach_TO_DATE='+TO_DATE);
       		$('.btnLook',navTab.getCurrentPanel()).click();
        }
     });
	 $(".btnLook",navTab.getCurrentPanel()).click(function(e) {
      	 var name=encodeURI(encodeURI($('#seach_KEY',navTab.getCurrentPanel()).val()));
      	 var FROM_DATE=encodeURI(encodeURI($('#seach_FROM_DATE',navTab.getCurrentPanel()).val()));
       	 var TO_DATE=encodeURI(encodeURI($('#seach_TO_DATE',navTab.getCurrentPanel()).val()));
      	$('.btnLook',navTab.getCurrentPanel()).attr('href','/ar/attendanceMintenance/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=viewAdjustLeaveTSTOBatchList&seach_KEY='+name+'&seach_FROM_DATE='+FROM_DATE+'&seach_TO_DATE='+TO_DATE);
     });
	$(".orderList",navTab.getCurrentPanel()).dataTable({"bPaginate": false,    //关闭分页
		    "bAutoWidth":false,//表格宽度不自动变化
		    "bProcessing":true,
			"bLengthChange": false,  //关闭按多少条记录显示下拉框
			"bFilter": true,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
			"bSort": true,   //关闭排序功能
			"bInfo": false,   //不显示datatables的信息（底部的页数，条目数信息）
			"bScrollInfinite":true,
			"scrollY": $(document.body).height() - 200,
            "scrollX": true,
            "orderClasses": false,
            "oLanguage": {
				//正在加载中......
		    	"sProcessing": "<spring:message code='ess.message.loading' />",
		        //查询不到相关数据！
		        "sZeroRecords": "<spring:message code='ess.message.NOT_FOUND_DATA' />",
		        //表中无数据存在！
		        "sEmptyTable": "<spring:message code='ess.message.NOT_FOUND_DATA_FROM_TABLE' />",
		        //快速筛选
		        "sSearch": "<spring:message code='ess.message.rapid_screening' />"
            } //多语言配置
		});
});
function delAdjustHolidayApplyCallback(OP_FLAG,form,callback) {
		
		$("#BATCH_ADJUST_OP_FLAG").val(OP_FLAG);
		var $form=null;
		if($('#'+form).length>0)
			$form=$('#'+form);
		else
	 		$form = $(form);
		
		if (!$form.valid()) {
			return false;
		}
	    var checked=false;
		var ids= document.getElementsByName("BATCH_ADJUST");
		
		for(var i=0;i<ids.length;i++){
			if(ids[i].checked){
				checked=true;
			}
		}
		if(OP_FLAG != 2){
		    var checked=false;
			var ids= document.getElementsByName("BATCH_ADJUST");
			for(var i=0;i<ids.length;i++){
				if(ids[i].checked){
					checked=true;
				}
			}
			if(!checked){
				alertMsg.error('<spring:message code="alert.message.ess.affirmApply.chooseApplyRecordFirstForBatch"/>'); 
				return false;
			}
		}
		
		if(OP_FLAG ==1){
		  var ids= document.getElementsByName("BATCH_ADJUST");
		  var checked=false;
		  for(var i=0;i<ids.length;i++){
			  if(ids[i].checked){
				  var fromTime = document.getElementById('fromTimeAdjust'+ids[i].value).value;
				  var toTime = document.getElementById('toTimeAdjust'+ids[i].value).value;
				  var LOCAL_NAME = document.getElementById('dwz.person.EMPINFOApplyLeave'+ids[i].value).value;
				  var PERSON_ID = document.getElementById('dwz.person.AFFIRMOR_IDApplyLeave'+ids[i].value).value;
				   if(LOCAL_NAME == "" ){
						alertMsg.error('<spring:message code="ar.viewAdjustLeaveTSTOBatchList.TIANJIAXINGMINGWEIKONGQINGXIANSOUSUO.b" />');//添加数据姓名为空,请搜索要添加的员工
						return false;
					}else{
					   if(PERSON_ID == ""){
					      alertMsg.error('<spring:message code="ar.viewAdjustLeaveTSTOBatchList.QINGSUOSOUYAOTIANJIAYUANGONG.b" />');//请搜索要添加的员工
						 return false;
					   }
					}
				  if(fromTime.length != 4){
					  alertMsg.error('<spring:message code="ar.viewAdjustLeaveTSTOBatchList.QINGSHURUHEFAKAISHISHIJIAN.b" />');//请输入合法的开始时间!
					  return false;
				  }
				  if(toTime.length != 4){
				    alertMsg.error('<spring:message code="ar.viewAdjustLeaveTSTOBatchList.QINGSHURUHEFAJIESHUSHIJIAN.b" />');//请输入合法的结束时间!
					return false;
				  }
			   }
			}		   
	     }
	     //添加当天的加班信息
	    var myDate = new Date();
	    var year = myDate.getFullYear();
	    var month = myDate.getMonth()+1;
	    month =(month<10 ? "0"+month:month); 
	    var date = myDate.getDate();
	    var applyDate = year+'-'+month+'-'+date;
	    $form.attr("action","/ess/infoApply/delAdjustApplyLeaveCoordForm?APPLY_DATE="+applyDate);
	    var msg = "<spring:message code='hrm.alert.empinfo.Sure.delete' />";//确定要删除吗？
	    if(OP_FLAG == 1){
	        var msg = "<spring:message code='hr.viewEvaluate.title.COMMIT_CONFIRM' />";//确定要提交吗？
	    }
	    if(OP_FLAG != 2)
	    alertMsg.confirm(msg,{okCall:function(){
				$.ajax({
					type: form.method || 'POST',
					url:$form.attr("action"), 
					data:$form.serializeArray(),
					dataType:"json",
					cache: false,
					success: function(data){ //请求成功后处理函数。
						if(data.statusCode=="200"){
							navTabSearch(document.viewAdjustLeaveTSTOBatchList);
							alertMsg.correct(data.message);
						}else{
							if(data.result=="2"){
								alertMsg.info(data.message);
							}else{
								alertMsg.error(data.message);
							}
						}   
			   	 	}  ,
					error: DWZ.ajaxError
				});
	        }});
	   if(OP_FLAG == 2)
	         $.ajax({
				type: form.method || 'POST',
			    url:$form.attr("action"), 
			    data:$form.serializeArray(),
				dataType:"json",
				cache: false,
				success: function(data){ //请求成功后处理函数。
				   //减少deleteYN,保证点击搜索 后删除null数据源
				   $("#viewAdjustLeaveTSTOBatchList").attr("action","/ess/infoApply/viewAdjustLeaveTSTOBatchList?firstFlag=N&nullYN=Y");
				   navTabSearch(document.viewAdjustLeaveTSTOBatchList);
				}  ,
				error: DWZ.ajaxError
			 });
		return false;
	}
	
//-->

function closeOrOpenSearchDiv(id){
	
	if('0' == id){
		document.getElementById('viewApplyAjBatch').style.display = 'none';
		document.getElementById('closeApplyAj').style.display = 'none';
		document.getElementById('openApplyAj').style.display = 'block';
	}else{
		document.getElementById('viewApplyAjBatch').style.display = 'block';
		document.getElementById('closeApplyAj').style.display = 'block';
		document.getElementById('openApplyAj').style.display = 'none';
	}
}

function jsSelectItemByValueAdjust(objSelect, objItemText) {        
      //判断是否存在        
      var isExit = false;       
      for (var i = 0; i < objSelect.options.length; i++) { 
          if (objSelect.options[i].value == objItemText) {        
              objSelect.options[i].selected = true;    
              isExit = true;        
              break;        
          }        
      }                      
 } 
 
 
 //html fill
 function htmlMuliAdjust(name,value){
  var ids = document.getElementsByName("BATCH_ADJUST");
 
		if(ids.length>0){
			for(var i=0;i<ids.length;i++){
		      var j=ids[i].value;
				if(ids[i].checked==true){
		    		document.getElementById(name+j).innerHTML=value;
				}
			}		  
		}
}
 
    
 //文本fill
 function textMuliAdjust(name,value){
  var ids = document.getElementsByName("BATCH_ADJUST");
 
		if(ids.length>0){
			for(var i=0;i<ids.length;i++){
		      var j=ids[i].value;
				if(ids[i].checked==true){
		    		document.getElementById(name+j).value=value;
				}
			}		  
		}
}
//复选框赋值
 function checkYNAdjust(name,value){
  var ids = document.getElementsByName("BATCH_ADJUST");
		if(ids.length>0){
			for(var i=0;i<ids.length;i++){
		      var j=ids[i].value;
				if(ids[i].checked==true){
		    		 var boxe= document.getElementById(name+j);
		    		 if(boxe.value == value){			
		    		 	boxe.checked = true;			
		    		 }else{
		    		    boxe.checked = false;
		    		 }
				}
			}		  
		}
}
//下拉框多选
function selMuliAdjust(name,value){
 var ids = document.getElementsByName("BATCH_ADJUST");
		if(ids.length>0){
			for(var i=0;i<ids.length;i++){
	           var j=ids[i].value;
				if(ids[i].checked==true){
		    		var sel=document.getElementById(name+j);
		    		jsSelectItemByValueAdjust(sel,value);
				}
			}		  
		}
}
function fillItemAdjust(){
    var checked=false;
		var ids= document.getElementsByName("BATCH_ADJUST");
		for(var i=0;i<ids.length;i++){
			if(ids[i].checked){
				checked=true;
			}
		}
		if(!checked){
			alertMsg.error('<spring:message code="ar.viewAdjustLeaveTSTOBatchList.QINGXUANZEFANYINGJILU.b" />'); //请选择反应记录
			return false;
		}
  var reason=document.getElementById("reason_aj").value;
  var otherReason=document.getElementById("otherReason_aj").value;
  var fromTime=document.getElementById("fromTime_aj").value;
  if(fromTime !="" && fromTime != null){
      var  resultfromTime = checkTimeAj(fromTime);
	  if(resultfromTime == false){
	    return false;
	  }
  }
  var toTime=document.getElementById("toTime_aj").value;
  if(toTime !="" && toTime != null){
  var  resulttoTime = checkTimeAj(toTime);
	  if(resulttoTime == false){
	    return false;
	  }
  }
  var fillAffirmFlag=document.getElementById("FILLAFFIRMFLAG_aj").value;
  var obj = document.getElementById("FILLAFFIRMFLAG_aj");
  var txt = obj.options[obj.selectedIndex].text;
  //工作形态
  var work_time_shift=document.getElementById("work_time_shift_aj").value;
  var obj3 = document.getElementById("work_time_shift_aj");
  var txt3 = obj3.options[obj3.selectedIndex].text;
  
  
   
  //刷新后全部反应的内容还存在
  document.getElementById("otherReason_adjust").value=otherReason;
  document.getElementById("fromTime_adjust").value=fromTime;
  document.getElementById("toTime_adjust").value=toTime;
  document.getElementById("reason_adjust").value=reason;
  document.getElementById("FILLAFFIRMFLAG_adjust").value=fillAffirmFlag;
  document.getElementById("work_time_shift_adjust").value=work_time_shift;  

  if(otherReason != "" && otherReason != null){
       textMuliAdjust("otherReasonAdjust",otherReason);  
  }
  if(fromTime !="" && fromTime != null){
     textMuliAdjust("fromTimeAdjust",fromTime); 
  }
  if(toTime !="" && toTime != null){
     textMuliAdjust("toTimeAdjust",toTime);     
  }
  
  //工作形态
  if(work_time_shift !=null && work_time_shift != ""){
	  textMuliAdjust("valibl_value_SHIFT_NOAdjust",work_time_shift);
	  textMuliAdjust("valibl_input_SHIFT_NOAdjust",txt3);
  }
  if(reason !=null && reason != ""){
       textMuliAdjust("valibl_value_reasonAdjust",reason);
       textMuliAdjust("valibl_input_reasonAdjust",document.getElementById("reason_aj").options[document.getElementById("reason_aj").selectedIndex].text);	
  }
  calAJLengthForFill(); //计算时长
  if(fillAffirmFlag != "" && fillAffirmFlag != null){
     textMuliAdjust("valibl_value_AFFIRM_NOAdjust",fillAffirmFlag);  	
     textMuliAdjust("valibl_input_AFFIRM_NOAdjust",txt);  
  }else{
    return;
  } 
}


function getWorkTimeStartEndTimeAdjust(){
   var work_time_shift=document.getElementById("work_time_shift_aj").value;
   var obj3 = document.getElementById("work_time_shift_aj");
   var txt3 = obj3.options[obj3.selectedIndex].text;
   var startTime = txt3.substr(0,4);
   var endTime = txt3.substr(5,4);
    var dateType = txt3.substr(9,1);
   if(work_time_shift != ''&& work_time_shift != null){
     if(dateType == '休'){
		   document.getElementById("fromTime_aj").value = startTime;
		   document.getElementById("toTime_aj").value = startTime;
	  }else{
	      document.getElementById("fromTime_aj").value = startTime;
	      document.getElementById("toTime_aj").value = endTime;
	  }
   }else{
       document.getElementById("fromTime_aj").value = '';
	   document.getElementById("toTime_aj").value = '';
   }
}

//核查时间
function checkTimeAj(timeText){
    var regTime = /^([0-2][0-9])([0-5][0-9])$/;
    var result = false;
    if (regTime.test(timeText)) {
        if ((parseInt(RegExp.$1) < 24) && (parseInt(RegExp.$2) < 60)) {
            result = true;
        }
    }
    if (result) {
       
    }else {
    alert("<spring:message code='ar.viewAdjustLeaveTSTOBatchList.SHIJIANGESHICUOWUQINGSHURUHEFASHIJIAN.b' />");//时间格式错误,请输入合法的时间
    return false;
    }
 return result;
}




//计算时长
function calPoTLengthAdjust(event ,j){
     var e= event ? event : window.event; 
     var keyCode = e.which ? e.which : e.keyCode;
	 var	from_date = $("#APPLY_DATE_ADJUST"+j).val();
	 var cpnyId = document.getElementById("CPNY_ID").value;
	 var fromTime1 =  document.getElementById("fromTimeAdjust"+j).value;
	 if(fromTime1.length==4){
		  var resultfromTime =   checkTimeAj(fromTime1);
		  if(resultfromTime == false){
		    $("#fromTimeAdjust"+j).val('');
		    $("#fromTimeAdjust"+j).focus();
		  }
		  $('#BATCH_ADJUST'+j).attr('checked','checked');
	 }
	 var fromTime = fromTime1.substr(0,2)+":"+fromTime1.substr(2,2);
		 var toTime1 = document.getElementById("toTimeAdjust"+j).value;
	 if(toTime1.length==4){
			var resultToTime =   checkTimeAj(toTime1);
			if(resultToTime == false){
			   $("#toTimeAdjust"+j).val('');
			   $("#toTimeAdjust"+j).focus();
			 }
			$('#BATCH_ADJUST'+j).attr('checked','checked');
	  }
		var toTime = toTime1.substr(0,2)+":"+toTime1.substr(2,2);
		var FIRST_TIME = document.getElementById("FIRST_TIME_ADJUST"+j).value;
		var LAST_TIME = document.getElementById("LAST_TIME_ADJUST"+j).value;
		var SHIFT_NO = document.getElementById("valibl_value_SHIFT_NOAdjust"+j).value;
		if(keyCode==13){
		    if(fromTime1.length!=4){
		        $("#fromTimeAdjust"+j).val('');
		        $("#fromTimeAdjust"+j).focus();
		        $("#shenqingshichangText_ADJUST"+j).html(0+"<spring:message code='ar.viewsummaryparameteritem.title.hour' />"+0+"<spring:message code='ar.viewsummaryparameteritem.title.minite' />");//小时   分钟
			    $("#shenqingshichang_ADJUST"+j).val(0);
			    $("#Lotlengthonehour_ADJUST"+j).val(0);
				$("#Lotlengthonemin_ADJUST"+j).val(0);
		    }
		    if(toTime1.length !=4){
		        $("#toTimeAdjust"+j).val('');
			    $("#toTimeAdjust"+j).focus();
			    $("#shenqingshichangText_ADJUST"+j).html(0+"<spring:message code='ar.viewsummaryparameteritem.title.hour' />"+0+"<spring:message code='ar.viewsummaryparameteritem.title.minite' />");//小时   分钟
			    $("#shenqingshichang_ADJUST"+j).val(0);
			    $("#Lotlengthonehour_ADJUST"+j).val(0);
				$("#Lotlengthonemin_ADJUST"+j).val(0);
		    }
		    if(fromTime1.length==4 && toTime1.length ==4)
			if(from_date!=null&&from_date!=""){
				 if(cpnyId=="TSTO"){
					$.ajax({
					cache: false,
					type: 'post',
					async:false,
					url: "/ess/infoApply/getOtApplyLengthWq",
					data: [
							 { name: 'fromTime', value: fromTime },
							{ name: 'toTime', value: toTime },
							{ name: 'FIRST_TIME', value: FIRST_TIME },
							{ name: 'LAST_TIME', value: LAST_TIME },
						    { name: 'from_date', value: from_date },
						    { name: 'SHIFT_NO', value: SHIFT_NO }],
				    dataType:"json",
				    success: function(data) {
						var hour = data.OT_HOUR;
						var min = data.OT_MINUTE;
						document.getElementById('shenqingshichangText_ADJUST'+j).innerHTML = hour+"<spring:message code='ar.viewsummaryparameteritem.title.hour' />"+min+"<spring:message code='ar.viewsummaryparameteritem.title.minite' />";//小时     分
						$("#shenqingshichang_ADJUST"+j).val((hour*60+min)/60);
						$("#Lotlengthonehour_ADJUST"+j).val(hour);
				        $("#Lotlengthonemin_ADJUST"+j).val(min);
					}
				});
			}
		}
	}
}


//填充的时候计算时长
//计算时长
function calAJLengthForFill(){
	var ids = document.getElementsByName("BATCH_ADJUST");
    var checked=false;
		for(var i=0;i<ids.length;i++){
			if(ids[i].checked){
				checked=true;
			}
		}
		if(!checked){
			alertMsg.error('<spring:message code="ar.viewAdjustLeaveTSTOBatchList.QINGXUANZESHENQINGJILU.b" />'); //请选择申请记录
			return false;
		}
      
  if(ids.length>0){
		for(var i=0;i<ids.length;i++){
			 if(ids[i].checked){
			        var j=ids[i].value;
					var	from_date = $("#APPLY_DATE_ADJUST"+j).val();
				    var cpnyId = document.getElementById("CPNY_ID").value;
				    var fromTime1 =  document.getElementById("fromTimeAdjust"+j).value;
				    var fromTime = fromTime1.substr(0,2)+":"+fromTime1.substr(2,2);
				    var toTime1 = document.getElementById("toTimeAdjust"+j).value;
				    var toTime = toTime1.substr(0,2)+":"+toTime1.substr(2,2);
				    var FIRST_TIME = document.getElementById("FIRST_TIME_ADJUST"+j).value;
				    var LAST_TIME = document.getElementById("LAST_TIME_ADJUST"+j).value;
				    var SHIFT_NO = document.getElementById("valibl_value_SHIFT_NOAdjust"+j).value;
				   
					if(from_date!=null&&from_date!=""){
				          if(cpnyId == 'TSTO') {
							$.ajax({
								 cache: false,
								 type: 'post',
								 async:false,
								 url: "/ess/infoApply/getOtApplyLengthWq",
								 data: [
								        { name: 'fromTime', value: fromTime },
								        { name: 'toTime', value: toTime },
								        { name: 'FIRST_TIME', value: FIRST_TIME },
								        { name: 'LAST_TIME', value: LAST_TIME },
								        { name: 'from_date', value: from_date },
								        { name: 'SHIFT_NO', value: SHIFT_NO }],
								 dataType:"json",
								  success: function(data) {
								  var hour = data.OT_HOUR;
								  var min = data.OT_MINUTE;
								   document.getElementById('shenqingshichangText_ADJUST'+j).innerHTML = hour+"<spring:message code='ar.viewsummaryparameteritem.title.hour' />"+min+"<spring:message code='ar.viewsummaryparameteritem.title.minite' />";////小时     分
								   $("#shenqingshichang_ADJUST"+j).val((hour*60+min)/60);
								   $("#Lotlengthonehour_ADJUST"+j).val(hour);
				                    $("#Lotlengthonemin_ADJUST"+j).val(min);
								 }
							});
						  }
						}
		             }
		          }
		      }
		  
}
//添加时计算时长
function calPoTLengthForADDAdjust(j){
 
		var	from_date = $("#APPLY_DATE_ADJUST"+j).val();
	    var cpnyId = document.getElementById("CPNY_ID").value;
		var fromTime1 =  document.getElementById("fromTimeAdjust"+j).value;
		var fromTime = fromTime1.substr(0,2)+":"+fromTime1.substr(2,2);
		var toTime1 = document.getElementById("toTimeAdjust"+j).value;
		var toTime = toTime1.substr(0,2)+":"+toTime1.substr(2,2);
		var FIRST_TIME = document.getElementById("FIRST_TIME_ADJUST"+j).value;
	    var LAST_TIME = document.getElementById("LAST_TIME_ADJUST"+j).value;
		var SHIFT_NO = document.getElementById("valibl_value_SHIFT_NOAdjust"+j).value;
			if(from_date!=null&&from_date!=""){
					$.ajax({
						cache: false,
					    type: 'post',
						async:false,
						url: "/ess/infoApply/getOtApplyLengthWq",
						data: [
							{ name: 'fromTime', value: fromTime },
							{ name: 'toTime', value: toTime },
						    { name: 'FIRST_TIME', value: FIRST_TIME },
						    { name: 'LAST_TIME', value: LAST_TIME },
							{ name: 'from_date', value: from_date },
							{ name: 'SHIFT_NO', value: SHIFT_NO }],
						dataType:"json",
						success: function(data) {
						var hour = data.OT_HOUR;
						var min = data.OT_MINUTE;
						document.getElementById('shenqingshichangText_ADJUST'+j).innerHTML = hour+"<spring:message code='ar.viewsummaryparameteritem.title.hour' />"+min+"<spring:message code='ar.viewsummaryparameteritem.title.minite' />";//小时     分
						$("#shenqingshichang_ADJUST"+j).val((hour*60+min)/60);
						$("#Lotlengthonehour_ADJUST"+j).val(hour);
				        $("#Lotlengthonemin_ADJUST"+j).val(min);
						}
					});
			}
}

 
 //onpropetychange 
 
function eventfunctionAdjust(obj,AA)   {   
   if(obj.id == undefined)
	   obj = document.getElementById(''+obj) ;
   var empIdStr=document.getElementById('dwz.person.'+obj.id.substring(11)).value;
   var PERSON_ID=document.getElementById('dwz.person.AFFIRMOR_IDApplyLeave'+obj.id.substring(28)).value;
   if(empIdStr != '' && PERSON_ID == ''){
	   //alert('没有精确到某个员工!请搜索员工');
	   return;
   }
   var APPLY_DATE_ADJUST = document.getElementById('APPLY_DATE_ADJUST'+obj.id.substring(28)).value;//获取罪行的选择的加班时间
   if(AA == undefined){
      applyBatchdate = APPLY_DATE_ADJUST;
   }else{
      applyBatchdate = AA;
   }
   var j=obj.id.substring(28);
     if(empIdStr != ''){
	      $.ajax({
				cache: false,
			    type: 'post',
				async:false,
		        url: "/ess/infoApplyAttendance/getAJTSTOInformation",
				data: [{ name: 'PERSON_ID', value: PERSON_ID },
				       { name: 'applyBatchdate',value: applyBatchdate }
				],
				dataType:"json",
				success: function(data) {
				$('#AJOLD_APPLY_NO'+j).val(data.APPLY_NO);
				$('#CONFIRM_FLAGTEXT_ADJUST'+j).html(data.APPLY_LOCK_NAME);
				$('#CONFIRM_FLAG_ADJUST'+j).val(data.CONFIRM_FLAGVALUE);
				$('#APPLY_LOCK_ADJUST'+j).val(data.APPLY_LOCK);
				$('#AJEMPIDTEXT'+j).html(data.EMPID);
				$('#APPLY_DATE_ADJUST'+j).val(data.AR_DATE_STR);
				$('#AJWEEKDAYTEXT'+j).html(data.IWEEK);
				$('#AJDATETYPETEXT'+j).html(data.TYPENAME);
				$('#AJDATE_TYPE'+j).val(data.DATE_TYPE);
				$('#AJFINAL_DATETEXT'+j).html(data.FINAL_DATE);
				$('#AJFINAL_DATE'+j).val(data.FINAL_DATE);
				$('#KAOQINITEMTEXT'+j).html(data.KAOQINITEM);
				$('#ITEM_NOAdjust'+j).val(data.ITEM_NO);
				$('#GROUP_IDAdjust'+j).val(data.GROUP_ID);
				$('#ADJST_YN_ADJUST'+j).attr('checked','checked');
				$('#APPLY_TYPE_CODEAdjust'+j).val(data.APPLY_TYPE_CODE);
				$('#valibl_input_SHIFT_NOAdjust'+j).val(data.WORK_TIME_NAME);
				$('#valibl_value_SHIFT_NOAdjust'+j).val(data.SHIFT_NO);
				$('#AJINDOOR_DATETEXT'+j).html(data.INDOOR_DATE);
				$('#AJOUTDOOR_DATETEXT'+j).html(data.OUTDOOR_DATE);
				$('#fromTimeAdjust'+j).val(data.FROM_TIME);
				$('#toTimeAdjust'+j).val(data.TO_TIME);
				$('#FIRST_TIME_ADJUST'+j).val(data.FIRST_TIME);
				$('#LAST_TIME_ADJUST'+j).val(data.LAST_TIME);
				$('#allowanceAdjust'+j).val(data.ALLOWANCE);
				$('#valibl_input_AFFIRM_NOAdjust'+j).val('部门申请');
				$('#valibl_value_AFFIRM_NOAdjust'+j).val('14014307');
				$('#AJOT_TOTAILTEXT'+j).html(data.OT_TOTAIL);
				$('#AJWEEKDAY_OT_TOTAILTEXT'+j).html(data.WEEKDAY_OT_TOTAIL);
				$('#AJWEEKEND_OT_TOTAILTEXT'+j).html(data.WEEKEND_OT_TOTAIL);
				$('#AJHOILDAY_OT_TOTAILTEXT'+j).html(data.HOILDAY_OT_TOTAIL);
				$('#AJCOMPRE_OT_TOTAILTEXT'+j).html(data.COMPRE_OT_TOTAIL);
				$('#AJOT_TOAVGTEXT'+j).html(data.OT_TOAVG);
				}
		 });
     
	  }
	  $('#BATCH_ADJUST'+j).attr('checked','checked');
	  if(PERSON_ID != null && PERSON_ID != '')
	  calPoTLengthForADDAdjust(j);
}


function onLoadFunctionAdjust(applyNo){   
    var workTime = document.getElementById('valibl_input_SHIFT_NOAdjust'+applyNo).value ;
    document.getElementById('fromTimeAdjust'+applyNo).value=workTime.substr(0,4);
	document.getElementById('toTimeAdjust'+applyNo).value=workTime.substr(5,4); 
}

function addEmpPop_ess3803(obj,flag) {
	var name = encodeURI(encodeURI($(obj).val()));
	var empIdStr=obj.id.substring(11);
	var personIdStr="AFFIRMOR_IDApplyLeave"+empIdStr.substring(17);
	$("#addEmpPop_ess3803", navTab.getCurrentPanel())
			.attr(
					'href',
					'/pa/workManagement/viewEmpForArAddPopList?pageNum=1&numPerPage=10&limit=ar&seach_KEY='
							+ name+'&empidStr='+empIdStr
							+'&personidStr='+personIdStr   );
	if (flag == 'onkeyup')
		$("#addEmpPop_ess3803", navTab.getCurrentPanel()).click();
}


function valibl_mouseover_item_pop(idStr){
	$("#valibl_input_"+idStr).unbind("blur");
	$("#valibl_pop_"+idStr).mouseout(
					function() {
						$("#valibl_input_"+idStr).blur(
										function() {
											$('#valibl_pop_'+idStr).css('display', 'none');
										});
					});
}

function searchPop_ess3803(flag) {
	var name = encodeURI(encodeURI($("#seach_KEY", navTab.getCurrentPanel()).val()));
	$("#searchPop_ess3803", navTab.getCurrentPanel())
			.attr(
					'href',
					'/pa/workManagement/viewEmpForPopList?pageNum=1&numPerPage=10&limit=ar&seach_KEY='
							+ name);
	if (flag == 'onkeyup')
		$("#searchPop_ess3803", navTab.getCurrentPanel()).click();
}

</script>
<div   id="viewApplyAjBatch"  class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/ess/infoApply/viewAdjustLeaveTSTOBatchList?firstFlag=N"  method="post"
		id="viewAdjustLeaveTSTOBatchList" name="viewAdjustLeaveTSTOBatchList">
		<div class="searchBar">
				<table class="searchContent">
				<tr>
				     <input id="CPNY_ID" name="CPNY_ID" type="hidden"  value="${LoginUser.cpnyId}"/>
				    <input type="hidden" id="fromTime_adjust"  name="fromTime1"  size="3"  value="${fromTime1}"/>
				    <input type="hidden" id="toTime_adjust"  name="toTime1"  size="3" value="${toTime1}"/>
				    <input type="hidden" id="reason_adjust"  name="reason"  size="3" value="${reason}"/>
				    <input type="hidden" id="otherReason_adjust" name="otherReason"  value="${otherReason}"/>
				    <input type="hidden" id="FILLAFFIRMFLAG_adjust" name="FILLAFFIRMFLAG"  value="${FILLAFFIRMFLAG}"/>
				    <input type="hidden" id="work_time_shift_adjust" name="work_time_shift1"  value="${work_time_shift1}"/>
				  <td width="7%"><!--社号/姓名--><spring:message code="public.title.empIdAndName" /></td>
					<td width="23%">
						<div style="float:left"><input type="text" name="seach_KEY" id="seach_KEY" value="${KEY}"/></div>
						<div style="float:left"><a class="btnLook" href="" lookupGroup="person"></a></div>
					</td>
					<td width="50%" colspan="5">
						<c:if test="${not empty personInfo}">
						<span style="margin-left: 50px;" >${personInfo.LOCAL_NAME }&nbsp/&nbsp${personInfo.EMPID }&nbsp/&nbsp${personInfo.POST_GRADE_NO_NAME}&nbsp/&nbsp${personInfo.EMP_OFFICE_NAME }</span>
						</c:if>
					</td>
					<td width="20%"></td>
				</tr>
				<tr>
					<td width="5%">
						<spring:message code="ess.workgroup.title.duration" text="期间"/>
					</td>
					<td width="20%">
					  <input type="text" name="seach_FROM_DATE" id="seach_FROM_DATE"  class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="${FROM_DATE }"/>
				      ~
					  <input type="text" name="seach_TO_DATE" id="seach_TO_DATE"  class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="${TO_DATE }"/>
					</td>
					<td width="20%"><!--审批状态--><spring:message code="ess.affirmApply.title.remark.shenpizhuangtai" /> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						  <ait:selectCodeMulti id="seach_AFFIRM_FLAG" name="seach_AFFIRM_FLAG_NAME" parentNo="14014304" selected="${AFFIRM_FLAG}"  selectedNm="${AFFIRM_FLAG_NAME}"/>
					</td>
					<td width="10%" colspan="2"><!--员工类型--><spring:message code="hr.viewPersonalInfo.title.EMP_TYPE_NAME" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				 	<ait:SelectSyCodeByCpnyID id="seach_EMP_TYPE_CODE" name="seach_EMP_TYPE_CODE" parentNo="13864" selected="${EMP_TYPE_CODE}" cnpyID="${LoginUser.cpnyId}" limit="all"/>
					</td>
					<td width="30%"><!--工作形态--><spring:message code="ess.infoApply.WORKTYPE" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					       <select name="seach_SHIFT_NO" id="seach_SHIFT_NO" >
					            <option value=""><!--请选择--><spring:message code="org.title.PLEASE_SELECT" /></option> 
								<c:forEach items="${workTimeList}" var="item">
									<option value="${item.SHIFT_NO}" <c:if test="${item.SHIFT_NO eq SHIFT_NO}">selected</c:if>
											>
									        ${item.SHIFT_SHORTNAME}
								</c:forEach>
							</select>
					 </td>
				</tr>
				<tr>
				     <td width="5%"><!-- 部门： --> <spring:message
						code="hr.viewPersonalInfo.title.DEPTNAME" /> 
					</td>
					<td>
						<ait:deptList name="seach_DEPTNO" cpnyId="${defaultCpny}" limit="ar" id="viewAdjustLeaveTSTOBatchList_seachDept" selected="${DEPTNO}"/>
						<ait:deptTreeIcon name="seach_DEPTNO" cpnyId="${defaultCpny}" limit="ar" id="viewAdjustLeaveTSTOBatchList_seachDept" selected="${DEPTNO}"/>
					</td>
				    <td width="10%" >
						   <!--状态--><spring:message code="org.title.status" />:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						  <select id="seach_CONFIRM_FLAG" name="seach_CONFIRM_FLAG" >
						     <option value=""><!--请选择--><spring:message code="org.title.PLEASE_SELECT" /></option>
						     <option value="1"  <c:if test="${CONFIRM_FLAG eq 1}">selected</c:if>
												>Confirmed</option>
						     <option value="0"<c:if test="${CONFIRM_FLAG eq 0}">selected</c:if>
												>Unconfirmed</option>
						  </select>
					 </td>
					<td width="30%" colspan="3"><!--班组--><spring:message code="hr.viewPersonalInfo.title.banzu" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<ait:SelectSyCodeByCpnyID id="seach_GROUP_ID" name="seach_GROUP_ID" parentNo="400223" selected="${GROUP_ID}" cnpyID="${LoginUser.cpnyId}" limit="all"/> 
					</td>			
			   </tr>
			</table>
		</div>
	</form>
</div>
<div>
 <table>
  <tr>
    <td id="openApplyAj" style="display:none"><!--打开-->
       &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src="/resources/images/+.gif" title="<spring:message code='ar.viewAdjustLeaveTSTOBatchList.YDAKAI.b' />" border="0" align="absmiddle" style="cursor:hand" onclick="closeOrOpenSearchDiv(1)"/>
    </td>
    <td id="closeApplyAj" ><!--关闭-->
       &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src="/resources/images/-.gif" title="<spring:message code='ess.infoApply.close' />" border="0" align="absmiddle" style="cursor:hand" onclick="closeOrOpenSearchDiv(0)"/>
    </td>
   </tr>
  </table>
</div>
<div id="viewApplyAttenBatch" class="pageHeader" >
    <div class="searchBar">
	   <table class="searchContent">
			    <tr>
				   <td width="10%">
				      <!--时间--><spring:message code="ess.infoApply.title.time" />：
						<input id="fromTime_aj"  name="fromTime"  size="3"  value="${fromTime1}"/>
						~
						<input id="toTime_aj"  name="toTime"  size="3" value="${toTime1}"/>
				   </td>
				   <td width="10%"><!--工作形态--><spring:message code="ess.infoApply.WORKTYPE" />：
					     <select name="work_time_shift" id="work_time_shift_aj" onchange="getWorkTimeStartEndTimeAdjust();">
					            <option value=""><!--请选择--><spring:message code="org.title.PLEASE_SELECT" /></option> 
								<c:forEach items="${workTimeList2}" var="item">
									<option value="${item.SHIFT_NO}" <c:if test="${item.SHIFT_NO eq work_time_shift1}">selected</c:if>
											>
									        ${item.SHIFT_SHORTNAME}
								</c:forEach>
						</select>
				 </td>
				<td width="10%"><!--原因--><spring:message code="ess.infoApply.Reason" />：
					      <ait:SelectSyCodeCombinByCpnyID name="reason_aj" combinParentNo="14014313"  selected="${reason}" cnpyID="${LoginUser.cpnyId}"  limit="all"/>
				         <input type="text" id="otherReason_aj" name="otherReason"  value="${otherReason}"/>
				</td >
				<td width="10%"><!--审批状态--><spring:message code="ess.infoApply.approval_status" /> : &nbsp;&nbsp;&nbsp;&nbsp;
					<ait:SelectSyCodeCombinByCpnyID name="FILLAFFIRMFLAG_aj" combinParentNo="14014304" selected="${FILLAFFIRMFLAG}"  cnpyID="${LoginUser.cpnyId}"  limit="all" />
				</td>
			</tr>
		</table>
		<div class="subBar"><ul class="toolBar"><li><a class="buttonActive" onclick="fillItemAdjust();"><span><!--全部反应--><spring:message code="ess.message.all_reaction" /></span></a> </li></ul></div>
	</div>
</div>
<div class="pageContent" >
<div class="formBar">
     <div style="font: 12px/ 20px arial, sans-serif; float: left; height: 30px; line-height: 30px;">Total:${fn:length(AdjustHolidayList)}</div>
	<ul class="toolBar">
	    <li><a class="buttonActive" id="viewAdjustLeaveTSTOBatchList_Serch" href="#"><span><!--查询--><spring:message code="ess.infoApply.SELECT" /></span></a></li>
	    <li><a class="buttonActive" onclick="delAdjustHolidayApplyCallback(2,'delAdjustApplyLeaveCoordForm',DWZ.ajaxDone)"><span><!--添加--><spring:message code="button.add" /></span></a></li>
		<li><a class="buttonActive" onclick="delAdjustHolidayApplyCallback(0,'delAdjustApplyLeaveCoordForm',DWZ.ajaxDone)"><span><!--删除--><spring:message code="button.delete" /></span></a></li>
		<li><a class="buttonActive" onclick="delAdjustHolidayApplyCallback(1,'delAdjustApplyLeaveCoordForm',DWZ.ajaxDone)"><span><!--保存--><spring:message code="button.sys.affirm.save" /></span></a></li>
		<li><a class="buttonActive" onclick="downloadExcel('viewAdjustLeaveTSTOBatchList','/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=189&CPNY=${LoginUser.cpnyId}','/ess/infoApply/viewAdjustLeaveTSTOBatchList?firstFlag=N')"><span><!--导出到Excel--><spring:message code="ess.infoApply.export_to_Excel" /></span></a></li>
	</ul>
</div>
	<form name="delAdjustApplyLeaveCoordForm" id="delAdjustApplyLeaveCoordForm" method="post" action="/ess/infoApply/delAdjustApplyLeaveCoordForm" 
	  onsubmit="return delAdjustHolidayApplyCallback(this, navTabAjaxDone);">                    
		<table class="orderList" width="100%">
			<thead>
			    <tr>
				    <th  rowspan="2"  width="1%" ><!--NO-->
						NO
					</th>
					<th rowspan="2" width="1%" >
				    	<input type="checkbox" class="checkboxCtrl" group="BATCH_ADJUST" />
				    </th>
				    <th  rowspan="2" width="1%"><!--状态-->
						<!--状态--><spring:message code="org.title.status" />
					</th>
					<th rowspan="2" width="4%"><!--申请人-->
						<spring:message code="hrm.empinfo.FAM_NAME" /><!--姓名-->
					</th >
				    <th rowspan="2"><!--社号-->
						 <!--社号--><spring:message code="org.title.EMPID" />
					</th>
					<th rowspan="2" width="4%"><!--日期-->
						<!--日期--><spring:message code="org.title.DATE" />
					</th>
					<th rowspan="2" width="5%"><!--星期-->
						<!--星期--><spring:message code="ess.infoApply.week" />
					</th>
					<th rowspan="2" width="4%" ><!--类型-->
						<!--类型--><spring:message code="sys.affirm.title.type" />
					</th>
					<th rowspan="2" width="4%"><!--考勤-->
						<!--考勤--><spring:message code="ess.infoApply.check_work" />
					</th>
					<th colspan="9" ><!--申请-->
						<!--申请--><spring:message code="ar.viewAdjustLeaveTSTOBatchList.SHENQING.b" />
					</th>
					<th colspan="2"><!--原因-->
						<!--原因--><spring:message code="ess.infoApply.Reason" />
					</th>
					<th  rowspan="2"><!--审批状态-->
						<!--审批状态--><spring:message code="ess.infoApply.approval_status" />
					</th>
					<th colspan="6"><!--加班累计-->
						<!--加班累计--><spring:message code="ess.title.JIABANLEIJI" />
					</th>
					<th rowspan="2"><!--加班上限-->
						<!--加班上限--><spring:message code="ar.viewAdjustLeaveTSTOBatchList.JIABANSHANGXIAN.b" />
					</th>
				</tr>
				<tr>
					<th width="5%"><!--工作形态-->
						<!--工作形态--><spring:message code="ar.addShiftView.GONGZUOXINGTAI.b" />
					</th>
					<th ><!--进门-->
						<!--进门--><spring:message code="ar.viewarcardrecord.title.jinmen" />
					</th>
					<th ><!--出门-->
						<!--出门--><spring:message code="ar.viewarcardrecord.title.chumen" />
					</th>
					<th ><!--开始-->
						<!--开始--><spring:message code="ar.viewshift.title.start" />
					</th>
					<th><!--结束-->
						 <!--结束--><spring:message code="ar.viewshift.title.end" />
					</th>
					<th width="5%"><!-- 加班时间-->
					     <!--加班时间--><spring:message code="ar.viewAdjustLeaveTSTOBatchList.JIABANSHIJIAN.b" />
					</th>
					<th width="2%"><!-- 是否调休-->
					    <!-- 调休--><spring:message code="ar.viewArNavigationPage.TIAOXIU.b" />
					</th>
					<th width="2%"><!-- 截止日期-->
					   <!--截止日期--><spring:message code="ar.viewAdjustLeaveTSTOBatchList.JIEZHIRIQI.b" />
					</th>
					<th width="4%"><!-- 中夜班津贴  -->
					    <!--中夜班津贴--><spring:message code="ar.viewShiftParameter.ZHONGYEBANJINTIE.b" />
					</th>
					<th ><!--原因-->
						<!-- 原因--><spring:message code="ess.infoApply.Reason" />
					</th>
					<th width="4%"><!--其他原因-->
						<!-- 其他原因--><spring:message code="ar.viewAdjustLeaveTSTOBatchList.QITAYUANYIN.b" />
					</th>
					<th width="3%"><!--加班合计-->
						<!-- 加班合计--><spring:message code="ess.title.JIABANHEJI" />
					</th>
					<th width="3%"><!--平时-->
						<!-- 平时--><spring:message code="ar.viewArOvertimeManagentFast.PINGSHI.b" />
					</th>
					<th width="3%"><!--周末-->
						<!-- 周末--><spring:message code="ar.viewitemparameter.title.zhoumo" />
					</th >
					<th width="3%"><!--法定节假日-->
						<!-- 节假日--><spring:message code="ar.viewitemparameter.title.jiejiari" />
					</th>
					<th width="3%"><!--综合加班-->
						<!-- 综合加班--><spring:message code="ar.viewAdjustLeaveTSTOBatchList.ZONGHEJIABAN.b" />
					</th>
					<th width="3%"><!--月平均-->
						<!-- 月平均--><spring:message code="ar.viewAdjustLeaveTSTOBatchList.YUEPINGJUN.b" />
					</th>
				</tr>
			</thead>
			<tbody>
			
			    <!-- start add adjust -->
			    
			    <c:forEach items="${nullAJTSTOAffirmList}" var="otAdjustApply" varStatus="i">	
					<tr target="sid" rel="${admin.personId}" 
					  <c:if test="${otAdjustApply.AFFIRM_FLAG eq '14014307' || otAdjustApply.AFFIRM_FLAG eq '14014311'}">style="color: blue;"</c:if>
							<c:if test="${otAdjustApply.AFFIRM_FLAG eq '14014309' || otAdjustApply.AFFIRM_FLAG eq '14014310'}">style="color: red;"</c:if>
					>
					    <td  style="text-align: center">${i.count}</td>
					    <td  style="text-align: center">
					        <input type="checkbox" id="BATCH_ADJUST${otAdjustApply.APPLY_NO}" name="BATCH_ADJUST" value="${otAdjustApply.APPLY_NO}" />
					         <input type="hidden"   id="AJOLD_APPLY_NO${otAdjustApply.APPLY_NO}" name="OLD_APPLY_NO${otAdjustApply.APPLY_NO}" value=""/>
					    </td>
					    <td  style="text-align: center">
					         <div id="CONFIRM_FLAGTEXT_ADJUST${otAdjustApply.APPLY_NO}"></div>
					        <input id="CONFIRM_FLAG_ADJUST${otAdjustApply.APPLY_NO}" name="CONFIRM_FLAG${otAdjustApply.APPLY_NO}" type="hidden"  value="${otAdjustApply.CONFLAG}"/>
					        <input id="APPLY_LOCK_ADJUST${otAdjustApply.APPLY_NO}" name="APPLY_LOCK${otAdjustApply.APPLY_NO}" type="hidden"  value="${otAdjustApply.APPLY_LOCK}"/>
					    </td>
					    <td style="text-align: center">             
							<input type="text" name="LOCAL_NAME" id="dwz.person.EMPINFOApplyLeave${otAdjustApply.APPLY_NO}"
								value="${KEY}" style="float:left;" size="4"onclick="eventfunctionAdjust(this);"
							onkeydown="javascript:if(event.keyCode == 13)addEmpPop_ess3803(this,'onkeyup');" />
							<input id="dwz.person.AFFIRMOR_IDApplyLeave${otAdjustApply.APPLY_NO}" onpropertychange="eventfunctionAdjust('dwz.person.EMPINFOApplyLeave${otAdjustApply.APPLY_NO}');"
							name="personid${otAdjustApply.APPLY_NO}" type="hidden"  size="8"  lookupGroup="person" />
							<a id="addEmpPop_ess3803" 
							onclick="addEmpPop_ess3803()" href="#" lookupGroup="person"> </a>
					    </td>
					    <td style="text-align: center">
					         <div  id="AJEMPIDTEXT${otAdjustApply.APPLY_NO}"></div>
					    </td>
					    <td style="text-align: center">
					       <input type="text" size="10"  id="APPLY_DATE_ADJUST${otAdjustApply.APPLY_NO}" name="APPLY_DATE${otAdjustApply.APPLY_NO}"    class="Wdate" 
					       onClick="WdatePicker({dateFmt:'yyyy-MM-dd',onpicking:
					         function(dp){
					             $('#BATCH_ADJUST${otAdjustApply.APPLY_NO}').attr('checked','checked');
					             eventfunctionAdjust('dwz.person.EMPINFOApplyLeave${otAdjustApply.APPLY_NO}',dp.cal.getNewDateStr());
					         }
					       })"   
					        value="${otAdjustApply.AR_DATE_STR}"  />
					    </td>
					    <td  style="text-align: center">
					          <div id="AJWEEKDAYTEXT${otAdjustApply.APPLY_NO}"></div>
					    </td>
					    <td style="text-align: center">
					      <div id="AJDATETYPETEXT${otAdjustApply.APPLY_NO}"></div>
					      	<input id="AJDATE_TYPE${otAdjustApply.APPLY_NO}" name="DATE_TYPE${otAdjustApply.APPLY_NO}" type="hidden" value="${otAdjustApply.DATE_TYPE}" />
					    </td>
					    <td style="text-align: center">
					       <div id="KAOQINITEMTEXT${otAdjustApply.APPLY_NO}"></div>
					       <input type="hidden" id="GROUP_IDAdjust${otAdjustApply.APPLY_NO}" name="GROUP_ID${otAdjustApply.APPLY_NO}"   value=""/>
					       <input type="hidden" id="ITEM_NOAdjust${otAdjustApply.APPLY_NO}" name="ITEM_NO${otAdjustApply.APPLY_NO}" value=""/>
					       <input type="hidden" id="APPLY_TYPE_CODEAdjust${otAdjustApply.APPLY_NO}" name="APPLY_TYPE_CODE${otAdjustApply.APPLY_NO}" value=""/>
					    </td>
					    <td  style="text-align: center" >
					                                                                                     
					          <input id="valibl_input_SHIFT_NOAdjust${otAdjustApply.APPLY_NO}" type="text" size="8" value="${otAdjustApply.WORK_TIME_NAME}"
								onfocus="$('#valibl_pop_SHIFT_NOAdjust${otAdjustApply.APPLY_NO}').css('display', 'block');" readonly="readonly"    />
							<input id="valibl_value_SHIFT_NOAdjust${otAdjustApply.APPLY_NO}" name="SHIFT_NO${otAdjustApply.APPLY_NO}" type="hidden" value="${otAdjustApply.SHIFT_NO}" />
							<input id="Adjust_OLD_SHIFT_NO${otAdjustApply.APPLY_NO}" name="OLD_SHIFT_NO${otAdjustApply.APPLY_NO}" type="hidden" value="${otAdjustApply.SHIFT_NO}" />
							<!-- 工作形态的开始结束 -->
							<input type="hidden" id="FIRST_TIME_ADJUST${otAdjustApply.APPLY_NO}" name="FIRST_TIME${otAdjustApply.APPLY_NO}" value="${otAdjustApply.FIRST_TIME}"/> 
							<input type="hidden" id="LAST_TIME_ADJUST${otAdjustApply.APPLY_NO}" name="LAST_TIME${otAdjustApply.APPLY_NO}" value="${otAdjustApply.LAST_TIME}"/> 
							 <div id="valibl_pop_SHIFT_NOAdjust${otAdjustApply.APPLY_NO}"
								onmouseover="valibl_mouseover_item_pop('SHIFT_NO${otAdjustApply.APPLY_NO}')" 
								onclick="$('#valibl_pop_SHIFT_NOAdjust${otAdjustApply.APPLY_NO}').css('display', 'none');"class="deptContent"
								style="display: none;height: 200px;width: 120px;margin-left:-20px;">
								<div class="ztree_dept" style="height: 200px;width: 120px;overflow:auto;overflow-x:hidden;">
									<div class="ztree_dept_title">
										<table width="100%">
											<tr>
												<th><!--工作形态--><spring:message code="ar.addShiftView.GONGZUOXINGTAI.b" /></th>
											</tr>
										</table>
									</div>
									<div class="ztree_dept_type" style="height: 75%;">
										<ul class="ztree_dept_table" >
											<c:forEach items="${workTimeList1}" var="item" varStatus="i">
												<c:choose>
													<c:when test="${i.count % 2 == 0 }">
														<li class="deptTreeLi" style="width: 400px"
														onclick="$('#valibl_input_SHIFT_NOAdjust${otAdjustApply.APPLY_NO}').attr('value','${item.SHIFT_SHORTNAME}');
														$('#valibl_value_SHIFT_NOAdjust${otAdjustApply.APPLY_NO}').attr('value','${item.SHIFT_NO}');
														$('#BATCH_ADJUST${otAdjustApply.APPLY_NO}').attr('checked','checked');
														$('#valibl_pop_SHIFT_NOAdjust${otAdjustApply.APPLY_NO}').css('display', 'none');onLoadFunctionAdjust(${otAdjustApply.APPLY_NO});"><span>${item.SHIFT_SHORTNAME}</span></li>
													</c:when>
													<c:otherwise>
														<li style="width: 400px" onclick="$('#valibl_input_SHIFT_NOAdjust${otAdjustApply.APPLY_NO}').attr('value','${item.SHIFT_SHORTNAME}');
														$('#valibl_value_SHIFT_NOAdjust${otAdjustApply.APPLY_NO}').attr('value','${item.SHIFT_NO}');
														$('#BATCH_ADJUST${otAdjustApply.APPLY_NO}').attr('checked','checked');
														$('#valibl_pop_SHIFT_NOAdjust${otAdjustApply.APPLY_NO}').css('display', 'none'); onLoadFunctionAdjust(${otAdjustApply.APPLY_NO}); "><span>${item.SHIFT_SHORTNAME}</span></li>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</ul>
									</div>
									<div class="ztree_dept_color">
										<a href="#" onclick="$('#valibl_pop_SHIFT_NOAdjust${otAdjustApply.APPLY_NO}').css('display', 'none');"
											class="ztree_dept_color_a"> <span><spring:message code='ess.infoApply.close' /><!--关闭--></span>
										</a>
									</div>
								</div>
							</div>
					        
					    </td>
					    <td style="text-align: center"><div id="AJINDOOR_DATETEXT${otAdjustApply.APPLY_NO}"></div></td>
					    <td  style="text-align: center"><div id="AJOUTDOOR_DATETEXT${otAdjustApply.APPLY_NO}"></div></td>
					    <td style="text-align: center">
					        <input type="text" size="4" id="fromTimeAdjust${otAdjustApply.APPLY_NO}" name="fromTime${otAdjustApply.APPLY_NO}" value="${otAdjustApply.FROM_TIME}" onkeyup="calPoTLengthAdjust(event,${otAdjustApply.APPLY_NO});"/>  
					    </td>
					    <td style="text-align: center">
					        <input type="text" size="4" id="toTimeAdjust${otAdjustApply.APPLY_NO}" name="toTime${otAdjustApply.APPLY_NO}" value="${otAdjustApply.TO_TIME}" onkeyup="calPoTLengthAdjust(event,${otAdjustApply.APPLY_NO});"/>
					    </td>
					    <td  style="text-align: center">
					         <div id="shenqingshichangText_ADJUST${otAdjustApply.APPLY_NO}"></div>
							<input type="hidden" id="shenqingshichang_ADJUST${otAdjustApply.APPLY_NO}" name="APPLY_LENGTH${otAdjustApply.APPLY_NO}" value="${otAdjustApply.APPLY_LENGTH2}" />
							<input type="hidden" id="Lotlengthonehour_ADJUST${otAdjustApply.APPLY_NO}" name="Lotlengthonehour${otAdjustApply.APPLY_NO}" value=""/> 
				            <input type="hidden" id="Lotlengthonemin_ADJUST${otAdjustApply.APPLY_NO}" name="Lotlengthonemin${otAdjustApply.APPLY_NO}" value=""/> 
				         </td>
					    <td  style="text-align: center">
					          <input type="checkbox"  id="ADJST_YN_ADJUST${otAdjustApply.APPLY_NO}" name="ADJST_YN${otAdjustApply.APPLY_NO}"  value="1" onclick="$('#BATCH_ADJUST${otAdjustApply.APPLY_NO}').attr('checked','checked');"/>
				         </td>
				       <td style="text-align: center">
				            <div id="AJFINAL_DATETEXT${otAdjustApply.APPLY_NO}"></div>
					        <input type="hidden" name="FINAL_DATE${otAdjustApply.APPLY_NO}" id="AJFINAL_DATE${otAdjustApply.APPLY_NO}"  value="${otAdjustApply.FINAL_DATE }"/>
					    </td>
					    <td  style="text-align: center">
					         <input style="width: 100%;" min="-99999999"  id="allowanceAdjust${otAdjustApply.APPLY_NO}" name="allowance${otAdjustApply.APPLY_NO}" value="${otAdjustApply.ALLOWANCE}" onkeyup="$('#BATCH_ADJUST${otAdjustApply.APPLY_NO}').attr('checked','checked');"  type="text"/>
					    </td>
					    <td style="text-align: center">
					         <input id="valibl_input_reasonAdjust${otAdjustApply.APPLY_NO}" type="text" size="6" value="${otAdjustApply.LEAVEREASON}"
								onfocus="$('#valibl_pop_reasonAdjust${otAdjustApply.APPLY_NO}').css('display', 'block');" readonly="readonly"/>
							<input id="valibl_value_reasonAdjust${otAdjustApply.APPLY_NO}" name="reason${otAdjustApply.APPLY_NO}" type="hidden" value="${otAdjustApply.REASON}" />
							 <div id="valibl_pop_reasonAdjust${otAdjustApply.APPLY_NO}"
								onmouseover="valibl_mouseover_item_pop('reason${otAdjustApply.APPLY_NO}')" 
								onclick="$('#valibl_pop_reasonAdjust${otAdjustApply.APPLY_NO}').css('display', 'none');"class="deptContent"
								style="display: none;height: 200px;width: 100px;margin-left:-20px;">
								<div class="ztree_dept" style="height: 200px;width: 100px;overflow:auto;overflow-x:hidden;">
									<div class="ztree_dept_title">
										<table width="100%">
											<tr>
												<th><!-- 原因--><spring:message code="ess.infoApply.Reason" /></th>
											</tr>
										</table>
									</div>
									<div class="ztree_dept_type" style="height: 75%">
										<ul class="ztree_dept_table">
											<c:forEach items="${codeList2}" var="item" varStatus="i">
												<c:choose>
													<c:when test="${i.count % 2 == 0 }">
														<li class="deptTreeLi" style="width: 300px"
														onclick="$('#valibl_input_reasonAdjust${otAdjustApply.APPLY_NO}').attr('value','${item.CODE_NAME}');
														$('#valibl_value_reasonAdjust${otAdjustApply.APPLY_NO}').attr('value','${item.CODE_NO}');
														$('#BATCH_ADJUST${otAdjustApply.APPLY_NO}').attr('checked','checked');
														$('#valibl_pop_reasonAdjust${otAdjustApply.APPLY_NO}').css('display', 'none');"><span>${item.CODE_NAME}</span></li>
													</c:when>
													<c:otherwise>
														<li style="width: 300px" onclick="$('#valibl_input_reasonAdjust${otAdjustApply.APPLY_NO}').attr('value','${item.CODE_NAME}');
														$('#valibl_value_reasonAdjust${otAdjustApply.APPLY_NO}').attr('value','${item.CODE_NO}');
														$('#BATCH_ADJUST${otAdjustApply.APPLY_NO}').attr('checked','checked');
														$('#valibl_pop_reasonAdjust${otAdjustApply.APPLY_NO}').css('display', 'none');"><span>${item.CODE_NAME}</span></li>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</ul>
									</div>
									<div class="ztree_dept_color">
										<a href="#" onclick="$('#valibl_pop_reasonAdjust${otAdjustApply.APPLY_NO}').css('display', 'none');"
											class="ztree_dept_color_a"> <span><spring:message code='ess.infoApply.close' /><!--关闭--></span>
										</a>
									</div>
								</div>
							</div>
					    </td>
					    <td  style="text-align: center">
					       <input style="width: 100%;" id="otherReasonAdjust${otAdjustApply.APPLY_NO}" name="otherReason${otAdjustApply.APPLY_NO}" value="${otAdjustApply.REASON_OTHER}" onkeyup="$('#BATCH_ADJUST${otAdjustApply.APPLY_NO}').attr('checked','checked');" type="text"/>
					    </td>
					     <td>
					    <input id="valibl_input_AFFIRM_NOAdjust${otAdjustApply.APPLY_NO}" type="text" size="6" value="${otAdjustApply.AFFIRM_NAME}"
								onfocus="$('#valibl_pop_AFFIRM_NOAdjust${otAdjustApply.APPLY_NO}').css('display', 'block');" readonly="readonly"  />
							<input id="valibl_value_AFFIRM_NOAdjust${otAdjustApply.APPLY_NO}" name="AFFIRM_FLAG${otAdjustApply.APPLY_NO}" type="hidden" value="${otAdjustApply.AFFIRM_FLAG}" />
							 <div id="valibl_pop_AFFIRM_NOAdjust${otAdjustApply.APPLY_NO}"
								onmouseover="valibl_mouseover_item_pop('AFFIRM_NO${otAdjustApply.APPLY_NO}')" 
								onclick="$('#valibl_pop_AFFIRM_NOAdjust${otAdjustApply.APPLY_NO}').css('display', 'none');"class="deptContent"
								style="display: none;height: 200px;width: 100px;margin-left:-20px;">
								<div class="ztree_dept" style="height: 200px;width: 100px;overflow:auto;overflow-x:hidden;">
									<div class="ztree_dept_title">
										<table width="100%">
											<tr>
												<th><!--审批状态--><spring:message code="ess.infoApply.approval_status" /></th>
											</tr>
										</table>
									</div>
									<div class="ztree_dept_type" style="height: 75%">
										<ul class="ztree_dept_table">
											<c:forEach items="${codeList}" var="item" varStatus="i">
												<c:choose>
													<c:when test="${i.count % 2 == 0 }">
														<li class="deptTreeLi" style="width: 300px"
														onclick="$('#valibl_input_AFFIRM_NOAdjust${otAdjustApply.APPLY_NO}').attr('value','${item.CODE_NAME}');
														$('#valibl_value_AFFIRM_NOAdjust${otAdjustApply.APPLY_NO}').attr('value','${item.CODE_NO}');
														$('#BATCH_ADJUST${otAdjustApply.APPLY_NO}').attr('checked','checked');
														$('#valibl_pop_AFFIRM_NOAdjust${otAdjustApply.APPLY_NO}').css('display', 'none');"><span>${item.CODE_NAME}</span></li>
													</c:when>
													<c:otherwise>
														<li style="width: 300px" onclick="$('#valibl_input_AFFIRM_NOAdjust${otAdjustApply.APPLY_NO}').attr('value','${item.CODE_NAME}');
														$('#valibl_value_AFFIRM_NOAdjust${otAdjustApply.APPLY_NO}').attr('value','${item.CODE_NO}');
														$('#BATCH_ADJUST${otAdjustApply.APPLY_NO}').attr('checked','checked');
														$('#valibl_pop_AFFIRM_NOAdjust${otAdjustApply.APPLY_NO}').css('display', 'none');"><span>${item.CODE_NAME}</span></li>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</ul>
									</div>
									<div class="ztree_dept_color">
										<a href="#" onclick="$('#valibl_pop_AFFIRM_NOAdjust${otAdjustApply.APPLY_NO}').css('display', 'none');"
											class="ztree_dept_color_a"> <span><spring:message code='ess.infoApply.close' /><!--关闭--></span>
										</a>
									</div>
								</div>
							</div>
					    </td>
					   	    <td  style="text-align: center">
					      <div id="AJOT_TOTAILTEXT${otAdjustApply.APPLY_NO}"></div>
					    </td>
					    <td  style="text-align: center">
					        <div id="AJWEEKDAY_OT_TOTAILTEXT${otAdjustApply.APPLY_NO}"></div>
					    </td>
					    <td  style="text-align: center">
					        <div id="AJWEEKEND_OT_TOTAILTEXT${otAdjustApply.APPLY_NO}"></div>
					    </td>
					    <td style="text-align: center">
					          <div id="AJHOILDAY_OT_TOTAILTEXT${otAdjustApply.APPLY_NO}"></div>
					    </td>
					    <td  style="text-align: center">
					          <div id="AJCOMPRE_OT_TOTAILTEXT${otAdjustApply.APPLY_NO}"></div>
					    </td>
					    <td  style="text-align: center">
					            <div id="AJOT_TOAVGTEXT${otAdjustApply.APPLY_NO}"></div>
					    </td>
					    <td></td>
					</tr>
				</c:forEach>
			    
			    <!-- end add adjust -->
				<c:forEach items="${AdjustHolidayList}" var="otAdjustApply" varStatus="i">	
					<tr target="sid" rel="${admin.personId}" 
					  <c:if test="${otAdjustApply.AFFIRM_FLAG eq '14014307' || otAdjustApply.AFFIRM_FLAG eq '14014311'}">style="color: blue;"</c:if>
							<c:if test="${otAdjustApply.AFFIRM_FLAG eq '14014309' || otAdjustApply.AFFIRM_FLAG eq '14014310'}">style="color: red;"</c:if>
					>
					    <td  style="text-align: center">${i.count}</td>
					    <td  style="text-align: center">
					        <input type="checkbox" id="BATCH_ADJUST${otAdjustApply.APPLY_NO}" name="BATCH_ADJUST" value="${otAdjustApply.APPLY_NO}" />
						     <input id="UNIT${otAdjustApply.APPLY_NO}" name="UNIT${otAdjustApply.APPLY_NO}" type="hidden"  value="${otAdjustApply.UNIT}"/>
						     <input id="STATUS_CODE${otAdjustApply.APPLY_NO}" name="STATUS_CODE${otAdjustApply.APPLY_NO}" type="hidden"  value="${otAdjustApply.STATUS_CODE}"/>
						     <input id="STATUS_NAME${otAdjustApply.APPLY_NO}" name="STATUS_NAME${otAdjustApply.APPLY_NO}" type="hidden"  value="${otAdjustApply.STATUS_NAME}"/>
						     <input id="IWEEK${otAdjustApply.APPLY_NO}" name="IWEEK${otAdjustApply.APPLY_NO}" type="hidden"  value="${otAdjustApply.IWEEK}"/>
						     <input id="LOCK_YN${otAdjustApply.APPLY_NO}" name="LOCK_YN${otAdjustApply.APPLY_NO}" type="hidden"  value="${otAdjustApply.LOCK_YN}"/>
					    </td>
					    <td  style="text-align: center">
					         <div id="CONFIRM_FLAGTEXT_ADJUST${otAdjustApply.APPLY_NO}"> ${otAdjustApply.APPLY_LOCK}</div>
					        <input id="CONFIRM_FLAG_ADJUST${otAdjustApply.APPLY_NO}" name="CONFIRM_FLAG${otAdjustApply.APPLY_NO}" type="hidden"  value="${otAdjustApply.CONFLAG}"/>
					        <input id="APPLY_LOCK_ADJUST${otAdjustApply.APPLY_NO}" name="APPLY_LOCK${otAdjustApply.APPLY_NO}" type="hidden"  value="${otAdjustApply.APPLYLOCK}"/>
					    </td>
					    <td  style="text-align: center"><!--考勤个人信息-->
					          <!-- [max=true, mask=true, maxable=true  minable=true, resizable = true ,drawable=true ] -->
                              <a href="/ess/viewDept/viewApplyDeptPersonalInfo?EMPID=${otAdjustApply.EMPID}&LOCAL_NAME= ${otAdjustApply.LOCAL_NAME}" 
                              target="dialog" style="color: blue;"  title="<spring:message code='ar.viewAdjustLeaveTSTOBatchList.KAOQINGERENXINXI.b' />"   [ mask=true ] width="1000" height="300"> 
                              ${otAdjustApply.LOCAL_NAME}</a>
                              <input id="dwz.person.AFFIRMOR_IDApplyLeave${otAdjustApply.APPLY_NO}" name="personid${otAdjustApply.APPLY_NO}" type="hidden"  value="${otAdjustApply.PERSON_ID}"/>
                              <input id="dwz.person.EMPINFOApplyLeave${otAdjustApply.APPLY_NO}"  type="hidden"  value="${otAdjustApply.LOCAL_NAME}"/>
					    </td>
					    <td style="text-align: center"><!--考勤个人信息-->
					     <a href="/ess/viewDept/viewApplyDeptPersonalInfo?EMPID=${otAdjustApply.EMPID}&LOCAL_NAME= ${otAdjustApply.LOCAL_NAME}" 
					     target="dialog" style="color: blue;" title="<spring:message code='ar.viewAdjustLeaveTSTOBatchList.KAOQINGERENXINXI.b' />" title="<spring:message code='ar.viewAdjustLeaveTSTOBatchList.KAOQINGERENXINXI.b' />"   [ mask=true ] width="1200" height="400"> 
					     ${otAdjustApply.EMPID}</a>
					    </td>
					    <td style="text-align: center">${otAdjustApply.AR_DATE_STR_VIEW}
					       <input type="hidden" id="APPLY_DATE_ADJUST${otAdjustApply.APPLY_NO}" name="APPLY_DATE${otAdjustApply.APPLY_NO}"   value="${otAdjustApply.AR_DATE_STR}"  />
					    </td>
					    <td  style="text-align: center">${otAdjustApply.WEEKDAY}</td>
					    <td style="text-align: center">
					      ${otAdjustApply.TYPENAME}
					      <input id="valibl_value_dateTypeAdjust${otAdjustApply.APPLY_NO}" name="DATE_TYPE${otAdjustApply.APPLY_NO}" type="hidden" value="${otAdjustApply.DATE_TYPE}" />
					    </td>
					    <td style="text-align: center">
					      <c:if test="${otAdjustApply.KAOQINITEM eq '正常出勤' || otAdjustApply.KAOQINITEM eq '休息' }">
					          <input type="hidden" id="KAOQINITEM${otAdjustApply.APPLY_NO}" name="KAOQINITEM${otAdjustApply.APPLY_NO}" value=""/>
					     </c:if>
					      <c:if test="${otAdjustApply.KAOQINITEM ne '正常出勤' && otAdjustApply.KAOQINITEM ne '休息'}">
					         ${otAdjustApply.KAOQINITEM}
					           <input type="hidden" id="KAOQINITEM${otAdjustApply.APPLY_NO}" name="KAOQINITEM${otAdjustApply.APPLY_NO}" value="${otAdjustApply.KAOQINITEM}"/>
					     </c:if>
					       <input type="hidden" id="GROUP_ID${otAdjustApply.APPLY_NO}" name="GROUP_ID${otAdjustApply.APPLY_NO}"   value="${otAdjustApply.GROUP_ID}"/>
					       <input type="hidden" id="ITEM_NO${otAdjustApply.APPLY_NO}" name="ITEM_NO${otAdjustApply.APPLY_NO}" value="${otAdjustApply.ITEM_NO}"/>
					       <input type="hidden" id="APPLY_TYPE_CODE${otAdjustApply.APPLY_NO}" name="APPLY_TYPE_CODE${otAdjustApply.APPLY_NO}" value="${otAdjustApply.APPLY_TYPE_CODE}"/>
					    </td>
					    <td  style="text-align: center" >
					                                                                                     
					          <input id="valibl_input_SHIFT_NOAdjust${otAdjustApply.APPLY_NO}" type="text" size="8" value="${otAdjustApply.WORK_TIME_NAME}"
								onfocus="$('#valibl_pop_SHIFT_NOAdjust${otAdjustApply.APPLY_NO}').css('display', 'block');" readonly="readonly"    />
							<input id="valibl_value_SHIFT_NOAdjust${otAdjustApply.APPLY_NO}" name="SHIFT_NO${otAdjustApply.APPLY_NO}" type="hidden" value="${otAdjustApply.SHIFT_NO}" />
							<input id="Adjust_OLD_SHIFT_NO${otAdjustApply.APPLY_NO}" name="OLD_SHIFT_NO${otAdjustApply.APPLY_NO}" type="hidden" value="${otAdjustApply.SHIFT_NO}" />
							<!-- 工作形态的开始结束 -->
							<input type="hidden" id="FIRST_TIME_ADJUST${otAdjustApply.APPLY_NO}" name="FIRST_TIME${otAdjustApply.APPLY_NO}" value="${otAdjustApply.FIRST_TIME}"/> 
							<input type="hidden" id="LAST_TIME_ADJUST${otAdjustApply.APPLY_NO}" name="LAST_TIME${otAdjustApply.APPLY_NO}" value="${otAdjustApply.LAST_TIME}"/> 
							 <div id="valibl_pop_SHIFT_NOAdjust${otAdjustApply.APPLY_NO}"
								onmouseover="valibl_mouseover_item_pop('SHIFT_NO${otAdjustApply.APPLY_NO}')" 
								onclick="$('#valibl_pop_SHIFT_NOAdjust${otAdjustApply.APPLY_NO}').css('display', 'none');"class="deptContent"
								style="display: none;height: 200px;width: 120px;margin-left:-20px;">
								<div class="ztree_dept" style="height: 200px;width: 120px;overflow:auto;overflow-x:hidden;">
									<div class="ztree_dept_title">
										<table width="100%">
											<tr>
												<th><!--工作形态--><spring:message code="ar.addShiftView.GONGZUOXINGTAI.b" /></th>
											</tr>
										</table>
									</div>
									<c:if test="${'1440' eq otAdjustApply.DATE_TYPE}">
									<div class="ztree_dept_type" style="height: 75%;">
										<ul class="ztree_dept_table" >
											<c:forEach items="${workTimeList1}" var="item" varStatus="i">
												<c:choose>
													<c:when test="${i.count % 2 == 0 }">
														<li class="deptTreeLi" style="width: 400px"
														onclick="$('#valibl_input_SHIFT_NOAdjust${otAdjustApply.APPLY_NO}').attr('value','${item.SHIFT_SHORTNAME}');
														$('#valibl_value_SHIFT_NOAdjust${otAdjustApply.APPLY_NO}').attr('value','${item.SHIFT_NO}');
														$('#BATCH_ADJUST${otAdjustApply.APPLY_NO}').attr('checked','checked');
														$('#valibl_pop_SHIFT_NOAdjust${otAdjustApply.APPLY_NO}').css('display', 'none');onLoadFunctionAdjust(${otAdjustApply.APPLY_NO});"><span>${item.SHIFT_SHORTNAME}</span></li>
													</c:when>
													<c:otherwise>
														<li style="width: 400px" onclick="$('#valibl_input_SHIFT_NOAdjust${otAdjustApply.APPLY_NO}').attr('value','${item.SHIFT_SHORTNAME}');
														$('#valibl_value_SHIFT_NOAdjust${otAdjustApply.APPLY_NO}').attr('value','${item.SHIFT_NO}');
														$('#BATCH_ADJUST${otAdjustApply.APPLY_NO}').attr('checked','checked');
														$('#valibl_pop_SHIFT_NOAdjust${otAdjustApply.APPLY_NO}').css('display', 'none'); onLoadFunctionAdjust(${otAdjustApply.APPLY_NO}); "><span>${item.SHIFT_SHORTNAME}</span></li>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</ul>
									</div>
									</c:if>
									<c:if test="${'1440' ne  otAdjustApply.DATE_TYPE}">
									<div class="ztree_dept_type" style="height: 75%;">
										<ul class="ztree_dept_table" >
											<c:forEach items="${workTimeList2}" var="item" varStatus="i">
												<c:choose>
													<c:when test="${i.count % 2 == 0 }">
														<li class="deptTreeLi" style="width: 400px"
														onclick="$('#valibl_input_SHIFT_NOAdjust${otAdjustApply.APPLY_NO}').attr('value','${item.SHIFT_SHORTNAME}');
														$('#valibl_value_SHIFT_NOAdjust${otAdjustApply.APPLY_NO}').attr('value','${item.SHIFT_NO}');
														$('#BATCH_ADJUST${otAdjustApply.APPLY_NO}').attr('checked','checked');
														$('#valibl_pop_SHIFT_NOAdjust${otAdjustApply.APPLY_NO}').css('display', 'none');onLoadFunctionAdjust(${otAdjustApply.APPLY_NO});"><span>${item.SHIFT_SHORTNAME}</span></li>
													</c:when>
													<c:otherwise>
														<li style="width: 400px" onclick="$('#valibl_input_SHIFT_NOAdjust${otAdjustApply.APPLY_NO}').attr('value','${item.SHIFT_SHORTNAME}');
														$('#valibl_value_SHIFT_NOAdjust${otAdjustApply.APPLY_NO}').attr('value','${item.SHIFT_NO}');
														$('#BATCH_ADJUST${otAdjustApply.APPLY_NO}').attr('checked','checked');
														$('#valibl_pop_SHIFT_NOAdjust${otAdjustApply.APPLY_NO}').css('display', 'none'); onLoadFunctionAdjust(${otAdjustApply.APPLY_NO}); "><span>${item.SHIFT_SHORTNAME}</span></li>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</ul>
									</div>
									</c:if>
									<div class="ztree_dept_color">
										<a href="#" onclick="$('#valibl_pop_SHIFT_NOAdjust${otAdjustApply.APPLY_NO}').css('display', 'none');"
											class="ztree_dept_color_a"> <span><spring:message code='ess.infoApply.close' /><!--关闭--></span>
										</a>
									</div>
								</div>
							</div>
					        
					    </td>
					   
					    <td style="text-align: center">${otAdjustApply.INDOOR_DATE}</td>
					    <td  style="text-align: center">${otAdjustApply.OUTDOOR_DATE}</td>
					    <td style="text-align: center">
					        <input type="text" size="4" id="fromTimeAdjust${otAdjustApply.APPLY_NO}" name="fromTime${otAdjustApply.APPLY_NO}" value="${otAdjustApply.FROM_TIME}" onkeyup="calPoTLengthAdjust(event,${otAdjustApply.APPLY_NO});"/>  
					    </td>
					    <td style="text-align: center">
					        <input type="text" size="4" id="toTimeAdjust${otAdjustApply.APPLY_NO}" name="toTime${otAdjustApply.APPLY_NO}" value="${otAdjustApply.TO_TIME}" onkeyup="calPoTLengthAdjust(event,${otAdjustApply.APPLY_NO});"/>
					    </td>
					    <td  style="text-align: center">
					         <div id="shenqingshichangText_ADJUST${otAdjustApply.APPLY_NO}">
					         ${otAdjustApply.APPLY_LENGTH} 
					             <c:if test="${otAdjustApply.ITEM_NO == '141452'}">
					               <font color="red"> (√)</font>
					             </c:if>
					         </div>
					            <input type="hidden" id="ADJUST_OLD_APPLY_LENGTH${otAdjustApply.APPLY_NO}" name="OLD_APPLY_LENGTH${otAdjustApply.APPLY_NO}" value="${otAdjustApply.APPLY_LENGTH2}" />
							<input type="hidden" id="shenqingshichang_ADJUST${otAdjustApply.APPLY_NO}" name="APPLY_LENGTH${otAdjustApply.APPLY_NO}" value="${otAdjustApply.APPLY_LENGTH2}" />
							<input type="hidden" id="Lotlengthonehour_ADJUST${otAdjustApply.APPLY_NO}" name="Lotlengthonehour${otAdjustApply.APPLY_NO}" value=""/> 
				            <input type="hidden" id="Lotlengthonemin_ADJUST${otAdjustApply.APPLY_NO}" name="Lotlengthonemin${otAdjustApply.APPLY_NO}" value=""/> 
				         </td>
					    <td  style="text-align: center">
					       <c:if test="${otAdjustApply.ITEM_NO eq '141452'}">
					             <input type="checkbox"  id="ADJST_YN_ADJUST${otAdjustApply.APPLY_NO}" name="ADJST_YN${otAdjustApply.APPLY_NO}" checked="checked"  value="1" onclick="$('#BATCH_ADJUST${otAdjustApply.APPLY_NO}').attr('checked','checked');"/>
					       </c:if>
					       <c:if test="${otAdjustApply.ITEM_NO ne '141452'}">
					          <input type="checkbox"  id="ADJST_YN_ADJUST${otAdjustApply.APPLY_NO}" name="ADJST_YN${otAdjustApply.APPLY_NO}"  value="1" onclick="$('#BATCH_ADJUST${otAdjustApply.APPLY_NO}').attr('checked','checked');"/>
					        </c:if>
				         </td>
				        <td style="text-align: center">
				           ${otAdjustApply.FINAL_DATE }
					        <input type="hidden" name="FINAL_DATE${otAdjustApply.APPLY_NO}" id="FINAL_DATE${otAdjustApply.APPLY_NO}"  value="${otAdjustApply.FINAL_DATE }"/>
					    </td>
					    <td  style="text-align: center">
					         <input style="width: 100%;" min="-99999999" title="${otAdjustApply.ALLOWANCE}" id="allowance${otAdjustApply.APPLY_NO}" name="allowance${otAdjustApply.APPLY_NO}" value="${otAdjustApply.ALLOWANCE}" onkeyup="$('#BATCH_ADJUST${otAdjustApply.APPLY_NO}').attr('checked','checked');"  type="text"/>
					    </td>
					    <td style="text-align: center">
					      
					         <input id="valibl_input_reasonAdjust${otAdjustApply.APPLY_NO}" type="text" size="6" value="${otAdjustApply.LEAVEREASON}"
								onfocus="$('#valibl_pop_reasonAdjust${otAdjustApply.APPLY_NO}').css('display', 'block');" readonly="readonly"/>
							<input id="valibl_value_reasonAdjust${otAdjustApply.APPLY_NO}" name="reason${otAdjustApply.APPLY_NO}" type="hidden" value="${otAdjustApply.REASON}" />
							 <div id="valibl_pop_reasonAdjust${otAdjustApply.APPLY_NO}"
								onmouseover="valibl_mouseover_item_pop('reason${otAdjustApply.APPLY_NO}')" 
								onclick="$('#valibl_pop_reasonAdjust${otAdjustApply.APPLY_NO}').css('display', 'none');"class="deptContent"
								style="display: none;height: 200px;width: 100px;margin-left:-20px;">
								<div class="ztree_dept" style="height: 200px;width: 100px;overflow:auto;overflow-x:hidden;">
									<div class="ztree_dept_title">
										<table width="100%">
											<tr>
												<th><!-- 原因--><spring:message code="ess.infoApply.Reason" /></th>
											</tr>
										</table>
									</div>
									<div class="ztree_dept_type" style="height: 75%">
										<ul class="ztree_dept_table">
											<c:forEach items="${codeList2}" var="item" varStatus="i">
												<c:choose>
													<c:when test="${i.count % 2 == 0 }">
														<li class="deptTreeLi" style="width: 300px"
														onclick="$('#valibl_input_reasonAdjust${otAdjustApply.APPLY_NO}').attr('value','${item.CODE_NAME}');
														$('#valibl_value_reasonAdjust${otAdjustApply.APPLY_NO}').attr('value','${item.CODE_NO}');
														$('#BATCH_ADJUST${otAdjustApply.APPLY_NO}').attr('checked','checked');
														$('#valibl_pop_reasonAdjust${otAdjustApply.APPLY_NO}').css('display', 'none');"><span>${item.CODE_NAME}</span></li>
													</c:when>
													<c:otherwise>
														<li style="width: 300px" onclick="$('#valibl_input_reasonAdjust${otAdjustApply.APPLY_NO}').attr('value','${item.CODE_NAME}');
														$('#valibl_value_reasonAdjust${otAdjustApply.APPLY_NO}').attr('value','${item.CODE_NO}');
														$('#BATCH_ADJUST${otAdjustApply.APPLY_NO}').attr('checked','checked');
														$('#valibl_pop_reasonAdjust${otAdjustApply.APPLY_NO}').css('display', 'none');"><span>${item.CODE_NAME}</span></li>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</ul>
									</div>
									<div class="ztree_dept_color">
										<a href="#" onclick="$('#valibl_pop_reasonAdjust${otAdjustApply.APPLY_NO}').css('display', 'none');"
											class="ztree_dept_color_a"> <span><spring:message code='ess.infoApply.close' /><!--关闭--></span>
										</a>
									</div>
								</div>
							</div>
					    </td>
					    <td  style="text-align: center">
					       <input style="width: 100%;" id="otherReasonAdjust${otAdjustApply.APPLY_NO}" name="otherReason${otAdjustApply.APPLY_NO}" value="${otAdjustApply.REASON_OTHER}" onkeyup="$('#BATCH_ADJUST${otAdjustApply.APPLY_NO}').attr('checked','checked');" type="text"/>
					    </td>
					     <td>
					    <input id="valibl_input_AFFIRM_NOAdjust${otAdjustApply.APPLY_NO}" type="text" size="6" value="${otAdjustApply.AFFIRM_NAME}"
								onfocus="$('#valibl_pop_AFFIRM_NOAdjust${otAdjustApply.APPLY_NO}').css('display', 'block');" readonly="readonly"  />
							<input id="valibl_value_AFFIRM_NOAdjust${otAdjustApply.APPLY_NO}" name="AFFIRM_FLAG${otAdjustApply.APPLY_NO}" type="hidden" value="${otAdjustApply.AFFIRM_FLAG}" />
							 <div id="valibl_pop_AFFIRM_NOAdjust${otAdjustApply.APPLY_NO}"
								onmouseover="valibl_mouseover_item_pop('AFFIRM_NO${otAdjustApply.APPLY_NO}')" 
								onclick="$('#valibl_pop_AFFIRM_NOAdjust${otAdjustApply.APPLY_NO}').css('display', 'none');"class="deptContent"
								style="display: none;height: 200px;width: 100px;margin-left:-20px;">
								<div class="ztree_dept" style="height: 200px;width: 100px;overflow:auto;overflow-x:hidden;">
									<div class="ztree_dept_title">
										<table width="100%">
											<tr>
												<th><!--审批状态--><spring:message code="ess.infoApply.approval_status" /></th>
											</tr>
										</table>
									</div>
									<div class="ztree_dept_type" style="height: 75%">
										<ul class="ztree_dept_table">
											<c:forEach items="${codeList}" var="item" varStatus="i">
												<c:choose>
													<c:when test="${i.count % 2 == 0 }">
														<li class="deptTreeLi" style="width: 300px"
														onclick="$('#valibl_input_AFFIRM_NOAdjust${otAdjustApply.APPLY_NO}').attr('value','${item.CODE_NAME}');
														$('#valibl_value_AFFIRM_NOAdjust${otAdjustApply.APPLY_NO}').attr('value','${item.CODE_NO}');
														$('#BATCH_ADJUST${otAdjustApply.APPLY_NO}').attr('checked','checked');
														$('#valibl_pop_AFFIRM_NOAdjust${otAdjustApply.APPLY_NO}').css('display', 'none');"><span>${item.CODE_NAME}</span></li>
													</c:when>
													<c:otherwise>
														<li style="width: 300px" onclick="$('#valibl_input_AFFIRM_NOAdjust${otAdjustApply.APPLY_NO}').attr('value','${item.CODE_NAME}');
														$('#valibl_value_AFFIRM_NOAdjust${otAdjustApply.APPLY_NO}').attr('value','${item.CODE_NO}');
														$('#BATCH_ADJUST${otAdjustApply.APPLY_NO}').attr('checked','checked');
														$('#valibl_pop_AFFIRM_NOAdjust${otAdjustApply.APPLY_NO}').css('display', 'none');"><span>${item.CODE_NAME}</span></li>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</ul>
									</div>
									<div class="ztree_dept_color">
										<a href="#" onclick="$('#valibl_pop_AFFIRM_NOAdjust${otAdjustApply.APPLY_NO}').css('display', 'none');"
											class="ztree_dept_color_a"> <span><spring:message code='ess.infoApply.close' /><!--关闭--></span>
										</a>
									</div>
								</div>
							</div>
					    </td>
					    <td  style="text-align: center">
					     <input type="hidden" id="OT_TOTAIL${otAdjustApply.APPLY_NO}"  value="${otAdjustApply.OT_TOTAIL}" />
					      ${otAdjustApply.OT_TOTAIL}
					    </td>
					    <td  style="text-align: center">
					       ${otAdjustApply.WEEKDAY_OT_TOTAIL}
					    </td>
					    <td  style="text-align: center">
					        ${otAdjustApply.WEEKEND_OT_TOTAIL}
					    </td>
					    <td style="text-align: center">
					         ${otAdjustApply.HOILDAY_OT_TOTAIL}
					    </td>
					    <td  style="text-align: center">
					         ${otAdjustApply.COMPRE_OT_TOTAIL}
					    </td>
					    <td  style="text-align: center">
					          ${otAdjustApply.OT_TOAVG}
					    </td>
					     <td  style="text-align: center">
					        <input type="hidden" id="OT_LIMIT_ADJUST${otAdjustApply.APPLY_NO}" value="${otAdjustApply.OT_LIMIT}" />
					         ${otAdjustApply.OT_LIMIT}
					    </td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<input type="hidden" id="BATCH_ADJUST_OP_FLAG" name="OP_FLAG" value="0" />
	</form>
</div>