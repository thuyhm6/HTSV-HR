<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.easydrag.js"></script>
<script type="text/javascript">

function submitPFormPre(flag){
	$("#AFFIRM_FLAG").val(flag);
  	var $from = $("#applyOvertimeBatchFormP");
  	$from.submit();
}

function validateBatchOvertimeApplyCallback_P(form,callback) {	
	var $form = $("#applyOvertimeBatchFormP");
//	if (!$form.valid()) {
//		return false;
//	}
	var checked = false ;
	$form.find(":checkbox[id='c1']").each(function(index, checkBoxObj){	    
	if(checkBoxObj.checked){
	   checked = true ;      
	  }	    
	});  
	if(!checked){
	 	alertMsg.error('<spring:message code="alert.message.ess.infoApply.choosePersonFirst"/>');
		return false;
	}
	$form.find(":checkbox[id='c1']").each(function(index, checkBoxObj){
	    if(checkBoxObj.checked){
			checked = true ;
	     	var personId = $(checkBoxObj).val().split(",")[0] ;
      	 	var i=parseInt(index)+2;
      	 	var k=$(checkBoxObj).val().split(",")[1] ;
		  	if($form.find("[name='"+personId+"_FROM_DATE_"+k+"']").val()==''){
				alertMsg.error('<spring:message code="alert.message.ess.infoApply.startDateTimeIsMust"/>');
				$form.find("[name='"+personId+"_FROM_DATE_"+k+"']").focus();
				checked=false;
				return false;
		   	}  
		   	var cpnyId = document.getElementById("CPNY_ID").value;
		   	if(cpnyId == 'LGEQH'){
		   		if($form.find("[name='"+personId+"_OT_FROM_TIME_H_"+k+"']").val()=='' && $form.find("[name='"+personId+"_OT_FROM_TIME_M_"+k+"']").val()==''){
				   alertMsg.error('<spring:message code="alert.message.ess.infoApply.startTimeIsMust"/>');
				   $form.find("[name='"+personId+"_OT_FROM_TIME_H_"+k+"']").focus();
				   $form.find("[name='"+personId+"_OT_FROM_TIME_M_"+k+"']").focus();
				   checked=false;
				   return false;
		   	    }
          	    if($form.find("[name='"+personId+"_OT_TO_TIME_H_"+k+"']").val()=='' && $form.find("[name='"+personId+"_OT_TO_TIME_M_"+k+"']").val()==''){
				   alertMsg.error('<spring:message code="alert.message.ess.infoApply.endTimeIsMust"/>');
				   $form.find("[name='"+personId+"_OT_TO_TIME_H_"+k+"']").focus();
				   $form.find("[name='"+personId+"_OT_TO_TIME_M_"+k+"']").focus();
				   checked=false;
				   return false;
		      	}
		   	}else{
		   		if($form.find("[name='"+personId+"_OT_FROM_TIME_"+k+"']").val()==''){
				   alertMsg.error('<spring:message code="alert.message.ess.infoApply.startTimeIsMust"/>');
				   $form.find("[name='"+personId+"_OT_FROM_TIME_"+k+"']").focus();
				   checked=false;
				   return false;
		   	    }
          	    if($form.find("[name='"+personId+"_OT_TO_TIME_"+k+"']").val()==''){
				   alertMsg.error('<spring:message code="alert.message.ess.infoApply.endTimeIsMust"/>');
				   $form.find("[name='"+personId+"_OT_TO_TIME_"+k+"']").focus();
				   checked=false;
				   return false;
		      	}
		   	}  
          	if($form.find("select[name='"+personId+"_OT_APPLY_TYPE_CODE_"+k+"']").val()==''){
				alertMsg.error('<spring:message code="alert.message.ess.infoApply.overtimeApplyTypeIsMust"/>');
				$form.find("select[name='"+personId+"_OT_APPLY_TYPE_CODE_"+k+"']").focus();
				checked=false;
				return false;
			}
			
		  	if($form.find("[name='"+personId+"_END_DAY_OFFSET_"+k+"']").val()=='0'){	 
              if(cpnyId == 'LGEQH'){	 
			  if(($form.find("[name='"+personId+"_OT_FROM_TIME_H_"+k+"']").val()+":"+$form.find("[name='"+personId+"_OT_FROM_TIME_M_"+k+"']").val())>($form.find("[name='"+personId+"_OT_TO_TIME_H_"+k+"']").val()+":"+$form.find("[name='"+personId+"_OT_TO_TIME_M_"+k+"']").val()))
				{
				   alertMsg.error('<spring:message code="alert.message.ess.infoApply.startTimeNotLaterThanEndTime"/>');
				   $form.find("[name='"+personId+"_OT_FROM_TIME_H_"+k+"']").focus();
				   $form.find("[name='"+personId+"_OT_FROM_TIME_M_"+k+"']").focus();
				  checked=false;
				  return false;
				}
			  }else{
			    if($form.find("[name='"+personId+"_OT_FROM_TIME_"+k+"']").val()>$form.find("[name='"+personId+"_OT_TO_TIME_"+k+"']").val())
				{
				   alertMsg.error('<spring:message code="alert.message.ess.infoApply.startTimeNotLaterThanEndTime"/>');
				   $form.find("[name='"+personId+"_OT_FROM_TIME_"+k+"']").focus();
				  checked=false;
				  return false;
				}
			  }
		 	}	 

		  	var endDayOffset = $form.find("[name='"+personId+"_END_DAY_OFFSET_"+k+"']").val();
	      	var fromDate = $form.find("[name='"+personId+"_FROM_DATE_"+k+"']").val();
            var fromTime = '';
	      	if(cpnyId == 'LGEQH'){
	      	   fromTime = $form.find("[name='"+personId+"_OT_FROM_TIME_H_"+k+"']").val()+":"+$form.find("[name='"+personId+"_OT_FROM_TIME_M_"+k+"']").val();
	      	}else{
	      	   fromTime = $form.find("[name='"+personId+"_OT_FROM_TIME_"+k+"']").val();
	      	}
	      	
	        //如果结束日期为空，默认为当天日期
	      	var	toDate = fromDate;
	      	var toTime = '';
	      	if(cpnyId == 'LGEQH'){
	      	   toTime = $form.find("[name='"+personId+"_OT_TO_TIME_H_"+k+"']").val()+":"+$form.find("[name='"+personId+"_OT_TO_TIME_M_"+k+"']").val()+":";
	      	}else{
	      	   toTime = $form.find("[name='"+personId+"_OT_TO_TIME_"+k+"']").val();
	      	}
	      	
   			var leavefromtime = fromDate + " " + fromTime + ":" + "00";
   			var leavetotime = toDate + " " + toTime +":" + "00";
   			//因为没有结束日期的选择所以只需要在没有跨天时比较时间即可
   			//加班不跨天时，结束时间一定要晚于开始时间
   			if(endDayOffset == '0'){
	   			if(comptime(leavefromtime,leavetotime)!=1){
	    			alertMsg.error('<spring:message code="alert.message.ess.infoApply.startTimeCanNotLaterThanEndTime1"/>');
	    			checked=false;
	    			return false;
	   			}
	   			if(comptime2(leavefromtime,leavetotime)!=1){
	   				alertMsg.error("加班时长不得低于一小时，请重新选择加班时间！");
	   				checked=false;
	   				return false;
	   			}
   			}
	    }
	});
	if(checked){		
		if (confirm ('<spring:message code="alert.message.ess.infoApply.areYouSureToApply"/>')){		
			$.ajax({
			type: form.method || 'POST',
			url:$form.attr("action"),
			data:$form.serializeArray(),
			dataType:"json",
			cache: false,
		 success: callback || DWZ.ajaxDone,
			error: DWZ.ajaxError
		});			
	    return false;
		}
	}
	return false ;
}

function passPOvertimeApplyValue(personId,i){   
    var fromDate      = document.getElementById(personId+"_FROM_DATE_"+i).value;
     if(fromDate==""){
       alertMsg.error('<spring:message code="alert.message.ess.infoApply.startDateTimeIsMust"/>');
       return false;
	}
    var applyTypeNo   = document.getElementById("APPLY_TYPE_NO").value;
    
    var endDayOffset = $form.find("[name='"+personId+"_END_DAY_OFFSET_"+k+"']").val();
    var toDate        = document.getElementById(personId+"_FROM_DATE_"+i+"").value;
    var fromTime   = document.getElementById(personId+"_OT_FROM_TIME_"+i+"").value;
    var fromTime = '';
    var toTime = '';
    if(cpnyId == 'LGEQH'){
      fromTime = document.getElementById(personId+"_OT_FROM_TIME_H_"+i+"").value+":"+document.getElementById(personId+"_OT_FROM_TIME_M_"+i+"").value;
      toTime = document.getElementById(personId+"_OT_TO_TIME_H_"+i+"").value+":"+document.getElementById(personId+"_OT_TO_TIME_M_"+i+"").value;
    }else{
      fromTime = document.getElementById(personId+"_OT_FROM_TIME_"+i+"").value;
      toTime = document.getElementById(personId+"_OT_TO_TIME_"+i+"").value;
    }   
    var applyTypeCode = document.getElementById(personId+"_OT_APPLY_TYPE_CODE_"+i+"").value;
    
    if(fromDate==""){
       alertMsg.error('<spring:message code="alert.message.ess.infoApply.startDateTimeIsMust"/>');
       return false;
	}
	if(fromTime==""){
       alertMsg.error('<spring:message code="alert.message.ess.infoApply.startTimeIsMust"/>');
       return false;
	}
    if(toTime==""){
	   alertMsg.error('<spring:message code="alert.message.ess.infoApply.endTimeIsMust"/>');
	   return false;
	}
	
	var leavefromtime = fromDate + " " + fromTime + ":" + "00";
	var leavetotime = toDate + " " + toTime + ":" + "00";
	if(endDayOffset=='0'){
		if(comptime(leavefromtime,leavetotime)!=1){
			alertMsg.error('<spring:message code="alert.message.ess.infoApply.startTimeCanNotLaterThanEndTime"/>');
			return false;
		}
		if(comptime2(leavefromtime,leavetotime)!=1){
			alertMsg.error("加班时长不得低于一小时，请重新选择加班时间！");
			return false;
		}
	}
	if(applyTypeCode==""){
       alertMsg.error('<spring:message code="alert.message.ess.infoApply.overtimeApplyTypeIsMust"/>');
       return false;
	}

    document.getElementById("viewOvertimeApplyBatchPHref").href = 
    document.getElementById("viewOvertimeApplyBatchPHref").href+
    						"APPLY_TYPE_NO=31&&PERSON_ID="+personId+
                            "&APPLY_TYPE_CODE="+applyTypeCode+"&APPLY_TYPE_NO="+applyTypeNo+
                            "&FROM_DATE="+fromDate+"&END_DAY_OFFSET="+endDayOffset+
                            "&OT_FROM_TIME="+fromTime+"&OT_TO_TIME="+toTime;
    var hid="viewOvertimeApplyBatchPHref";
   
    $("#"+hid).click();
}

function fillItemOtP(){
	var $form = $("#applyOvertimeBatchFormP");
//	if (!$form.valid()) {
//		return false;
//	}
	var checked = false ;
	$form.find(":checkbox[id='c1']").each(function(index, checkBoxObj){	    
		if(checkBoxObj.checked){
	   		checked = true ;      
	  	}	    
	});
	if(!checked){
	 	alertMsg.error('<spring:message code="alert.message.ess.infoApply.choosePersonFirst"/>');
		return false;
	}
  	
	var otFromDate=document.getElementById("otFromDate").value; 
  	var cpnyId = document.getElementById("CPNY_ID").value;
  	var otFromTime='';
  	var otToTime='';
  	var otFromTime_h='';
  	var otFromTime_m='';
  	var otToTime_h='';
  	var otToTime_m='';
  	if(cpnyId == 'LGEQH'){
  	   otFromTime_h=document.getElementById("otFromTime_h").value;
  	   otFromTime_m=document.getElementById("otFromTime_m").value;
  	   otToTime_h=document.getElementById("otToTime_h").value;
  	   otToTime_m=document.getElementById("otToTime_m").value;
  	   otFromTime = document.getElementById("otFromTime_h").value+":"+document.getElementById("otFromTime_m").value; 
  	   otToTime = document.getElementById("otToTime_h").value+":"+document.getElementById("otToTime_m").value;
  	}else{
  	   otFromTime = document.getElementById("otFromTime").value; 
  	   otToTime = document.getElementById("otToTime").value;
  	}

  	var endDayOffset=document.getElementById("endDayOffset").value;
  	//var otApplyLengthB=document.getElementById("otApplyLengthB").value; 
  	var otApplyTypeCode=document.getElementById("otApplyTypeCode").value;    
  	 
 	var otAdjustYn=document.getElementById("adjustYn").value;    
	var teshuYn=document.getElementById("teshuYn").value; 
  	var otPlaceType=document.getElementById("otPlaceType").value;         
  	var otApplyReason =document.getElementById("otApplyRemark").value;
	var teshuYndis=document.getElementById("teshuYn").disabled; 
  	var otPlaceTypedis=document.getElementById("otPlaceType").disabled;  
  	var otAdjustYndis=document.getElementById("adjustYn").disabled; 
  	
  	var $form = $("#applyOvertimeBatchFormP");
  	//$form.find("[name='"+personId+"_FROM_DATE']")
  	var cloumeCount =document.getElementById("cloumeCount").value;
  	$form.find(":checkbox[id='c1']").each(function(index, checkBoxObj){
  		if(checkBoxObj.checked){
      		var personId = $(checkBoxObj).val().split(",")[0] ;
      	 	var i=parseInt(index)+2;
      	 	var k=$(checkBoxObj).val().split(",")[1] ;
      		
      	 	$form.find("[name='"+personId+"_FROM_DATE_"+k+"']").attr("value",otFromDate);
        	if(cpnyId == 'LGEQH'){
        	   $form.find("[name='"+personId+"_OT_FROM_TIME_H_"+k+"']").attr("value",otFromTime_h);
        	   $form.find("[name='"+personId+"_OT_FROM_TIME_M_"+k+"']").attr("value",otFromTime_m);
        	   $form.find("[name='"+personId+"_OT_TO_TIME_H_"+k+"']").attr("value",otToTime_h);  
        	   $form.find("[name='"+personId+"_OT_TO_TIME_M_"+k+"']").attr("value",otToTime_m); 
        	}else{
        	   $form.find("[name='"+personId+"_OT_FROM_TIME_"+k+"']").attr("value",otFromTime);
        	   $form.find("[name='"+personId+"_OT_TO_TIME_"+k+"']").attr("value",otToTime);   
        	}
        	
         	$form.find("select[name='"+personId+"_END_DAY_OFFSET_"+k+"']").attr("value",endDayOffset);
         	getPOtApplyTypeBatch(personId,k);//改变人员的加班类型
         	
            $form.find("select[name='"+personId+"_OT_APPLY_LENGTH_"+k+"']").attr("value",otApplyLengthB);
         	//$form.find("select[name='"+personId+"_OT_APPLY_TYPE_CODE_"+k+"']").attr("value",otApplyTypeCode);
         	$form.find("select[name='"+personId+"_TESHU_YN_"+k+"']").attr("value",teshuYn);
         	$form.find("select[name='"+personId+"_ADJUST_YN_"+k+"']").attr("value",otAdjustYn);
         	$form.find("select[name='"+personId+"_OT_PLACE_TYPE_"+k+"']").attr("value",otPlaceType);
         	$form.find("select[name='"+personId+"_TESHU_YN_"+k+"']").attr("disabled",teshuYndis);
         	$form.find("select[name='"+personId+"_ADJUST_YN_"+k+"']").attr("disabled",otAdjustYndis);
         	$form.find("select[name='"+personId+"_OT_PLACE_TYPE_"+k+"']").attr("disabled",otPlaceTypedis);
         	$form.find("[name='"+personId+"_APPLY_REMARK_"+k+"']").attr("value",otApplyReason);
			//计算时长
         	changeApplyType(personId,k);
         	calPoTLengthBatch(personId,k);
         	//getDate2(i,personId,otFromDate);
  		}
  	});
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
	//alert(beginTime+endTime+beginTime);
	//alert(Date.parse(endTime)+" "+Date.parse(beginTime));
	var a =(Date.parse(endTime)-Date.parse(beginTime))/3600/1000;
	if(a<0){
		return -1;
	}else if (a>=1){
		return 1;
	}else if (a==0){
		return 0;
	}else{
		return 'exception'
	}
}
</script>

<script type="text/javascript">

//置换是第一行的公共选项id还是每个人的标签id
function changeID(personId,i){
	var all=new Map();
	if(personId == '0' && i =='0'){
		all.put("otApplyTypeCode","otApplyTypeCode");
		all.put("adjustYn","adjustYn");
		all.put("otPlaceType","otPlaceType");
		all.put("teshuYn","teshuYn");
		all.put("dateType","publicDateType");
		all.put("peopleType","publicPeopleType");
		all.put("fromDate","otFromDate");
	}else{
		all.put("otApplyTypeCode",personId+"_OT_APPLY_TYPE_CODE_"+i+"_show");//显示出来的
		all.put("otApplyTypeCodeValue",personId+"_OT_APPLY_TYPE_CODE_"+i);//传给后台的。。只有34,32,33
		all.put("adjustYn",personId+"_ADJUST_YN_"+i);
		all.put("otPlaceType",personId+"_OT_PLACE_TYPE_"+i);
		all.put("teshuYn",personId+"_TESHU_YN_"+i);
		all.put("dateType",personId+"_dateType_"+i);
		all.put("peopleType",personId+"_peopleType_"+i);
		all.put("fromDate",personId+"_FROM_DATE_"+i);
	}
	return all;
}


//获取该日期的加班类型 有人员的改变日期方法
function getPOtApplyTypeBatch(personId,i){
   var allID=changeID(personId,i);//获取该行的各元素的id
	var $form = $("#applyOvertimeBatchFormP");
	var fromDate = $("#applyOvertimeBatchFormP input[id="+allID.get("fromDate")+"]").val();
	var adjustYn = $("#applyOvertimeBatchFormP select[id='"+personId+'_ADJUST_YN_'+i+"']").val();//调休
	var teshuYn = $("#applyOvertimeBatchFormP select[id='"+personId+'_TESHU_YN_'+i+"']").val();//特殊
	var otPlaceType = $("#applyOvertimeBatchFormP select[id='"+personId+'_OT_PLACE_TYPE_'+i+"']").val();//社内外
	if(fromDate!=""){
		$.ajax({
			cache: false,
			type: 'post',
			async:false,
			url: "/ess/infoApply/getDateTypeByDateAndEmpCpny?",
		    data:'DDATE_STR=' + fromDate +'&PERSON_ID='+personId,
			dataType:"json",
			success: function(data) {
				var dateType = data.TYPEID;
				var peopleType =data.PEOPLETYPEID;
				$form.find("input[name="+allID.get("dateType")+"]").attr("value",dateType);//个人日历当天类型公司类型显示
				$form.find("input[name="+allID.get("peopleType")+"]").attr("value",peopleType);//个人日历当天类型
				if(dateType == '1440'){  
					$form.find("select[name="+allID.get("otApplyTypeCode")+"]").attr("value","32");
					$form.find("input[name="+allID.get("otApplyTypeCodeValue")+"]").attr("value","32");
					
					
					$form.find("select[name="+allID.get("adjustYn")+"]").attr("value",'0');
			    	$form.find("select[name="+allID.get("adjustYn")+"]").attr("disabled",true);
					$form.find("select[name="+allID.get("otPlaceType")+"]").attr("value",otPlaceType);//社内外
					$form.find("select[name="+allID.get("otPlaceType")+"]").attr("disabled",false);
					$form.find("select[name="+allID.get("teshuYn")+"]").attr("value",teshuYn);//特殊
					$form.find("select[name="+allID.get("teshuYn")+"]").attr("disabled",false);
				}else if(dateType == '1441'){
				
					$form.find("select[name="+allID.get("otApplyTypeCode")+"]").attr("value","33");
					$form.find("input[name="+allID.get("otApplyTypeCodeValue")+"]").attr("value","33");
					$form.find("select[name="+allID.get("adjustYn")+"]").attr("value",adjustYn);//调休
			    	$form.find("select[name="+allID.get("adjustYn")+"]").attr("disabled",false);
					$form.find("select[name="+allID.get("otPlaceType")+"]").attr("value",otPlaceType);//社内外
					$form.find("select[name="+allID.get("otPlaceType")+"]").attr("disabled",false);
					$form.find("select[name="+allID.get("teshuYn")+"]").attr("value",teshuYn);//特殊
					$form.find("select[name="+allID.get("teshuYn")+"]").attr("disabled",false);
				}else if(dateType == '1442'){
					
					$form.find("select[name="+allID.get("otApplyTypeCode")+"]").attr("value","34");
					$form.find("input[name="+allID.get("otApplyTypeCodeValue")+"]").attr("value","34");

					$form.find("select[name="+allID.get("adjustYn")+"]").attr("value",adjustYn);//调休
			    	$form.find("select[name="+allID.get("adjustYn")+"]").attr("disabled",true);
					$form.find("select[name="+allID.get("otPlaceType")+"]").attr("value",otPlaceType);//社内外
					$form.find("select[name="+allID.get("otPlaceType")+"]").attr("disabled",false);
					$form.find("select[name="+allID.get("teshuYn")+"]").attr("value",teshuYn);//特殊
					$form.find("select[name="+allID.get("teshuYn")+"]").attr("disabled",false);
				}else{
					$form.find("select[name="+allID.get("otApplyTypeCode")+"]").attr("value","32");
					$form.find("input[name="+allID.get("otApplyTypeCodeValue")+"]").attr("value","32");

					$form.find("select[name="+allID.get("adjustYn")+"]").attr("value",'0');//调休
			    	$form.find("select[name="+allID.get("adjustYn")+"]").attr("disabled",true);
					$form.find("select[name="+allID.get("otPlaceType")+"]").attr("value",otPlaceType);//社内外
					$form.find("select[name="+allID.get("otPlaceType")+"]").attr("disabled",false);
					$form.find("select[name="+allID.get("teshuYn")+"]").attr("value",teshuYn);//特殊
					$form.find("select[name="+allID.get("teshuYn")+"]").attr("disabled",false);
				}
			}
		});
	}
	changeApplyType(personId,i);
	//setTimeout("getPOtApplyTypeBatch("+person_id+","+i+")",1000);
}
//改变加班类型
function  changeApplyType(personId,i){
	var allID=changeID(personId,i)//获取该行的各元素的id
	var $form = $("#applyOvertimeBatchFormP");
	var fromDate = $("#applyOvertimeBatchFormP input[id="+allID.get("fromDate")+"]").val();//加班日期
	var adjustYn = $("#applyOvertimeBatchFormP select[id="+allID.get("adjustYn")+"]").val();//调休
	var teshuYn = $("#applyOvertimeBatchFormP select[id="+allID.get("teshuYn")+"]").val();//特殊
	var otPlaceType = $("#applyOvertimeBatchFormP select[id="+allID.get("otPlaceType")+"]").val();//社内外
	var dateType = $("#applyOvertimeBatchFormP input[id="+allID.get("dateType")+"]").val();//公司当天类型
	var peopleType = $("#applyOvertimeBatchFormP input[id="+allID.get("peopleType")+"]").val();//个人当天类型
	
	//alert(adjustYn+" "+teshuYn+" "+otPlaceType);
	if(dateType == '1440'){
		if(teshuYn == '1' && adjustYn =='0' && otPlaceType =='INSIDE'){
			$form.find("select[name="+allID.get("otApplyTypeCode")+"]").attr("value","218181");//特殊加班（平时）
		}else if(teshuYn == '0' && adjustYn =='0' && otPlaceType =='OUTSIDE'){
			$form.find("select[name="+allID.get("otApplyTypeCode")+"]").attr("value","17378");//社外平时加班(付薪)
		}else {
			$form.find("select[name="+allID.get("otApplyTypeCode")+"]").attr("value","32");//平时加班(付薪)
			
		}
	}else if(dateType == '1441'){
		if(teshuYn == '1' && adjustYn =='0' && otPlaceType =='INSIDE'){
			$form.find("select[name="+allID.get("otApplyTypeCode")+"]").attr("value","277592");//特殊加班周末
		}else if(teshuYn == '0' && adjustYn =='1' && otPlaceType =='INSIDE'){
			$form.find("select[name="+allID.get("otApplyTypeCode")+"]").attr("value","141471");//周末加班调休
		}else if(teshuYn == '0' && adjustYn =='0' && otPlaceType =='OUTSIDE'){
			$form.find("select[name="+allID.get("otApplyTypeCode")+"]").attr("value","17379");//社外周末加班
		}else if(teshuYn == '0' && adjustYn =='1' && otPlaceType =='OUTSIDE'){
			$form.find("select[name="+allID.get("otApplyTypeCode")+"]").attr("value","141472");//社外周末加班调休
		}else {
			if(peopleType == '219627'  || peopleType == '217885') {//如果个人日历为周末夜班或者夜班休息
			$form.find("select[name="+allID.get("otApplyTypeCode")+"]").attr("value","217871");//周末夜班加班
			}else { 
			$form.find("select[name="+allID.get("otApplyTypeCode")+"]").attr("value","33");//周末加班
			}
		}
	}else if(dateType == '1442'){
		if(teshuYn == '1' && adjustYn =='0' && otPlaceType =='INSIDE'){
			$form.find("select[name="+allID.get("otApplyTypeCode")+"]").attr("value","278597");//特殊加班法定日
		}else if(teshuYn == '0' && adjustYn =='0' && otPlaceType =='OUTSIDE'){
			$form.find("select[name="+allID.get("otApplyTypeCode")+"]").attr("value","141470");//社外法定加班
		}else {
			if(peopleType == '219627'  ) {//如果个人日历为夜班休息
				$form.find("select[name="+allID.get("otApplyTypeCode")+"]").attr("value","217872");//法定夜班加班
				}else {
				$form.find("select[name="+allID.get("otApplyTypeCode")+"]").attr("value","34");//法定加班
			}
		}
	}else{
    	$form.find("select[name="+allID.get("otApplyTypeCode")+"]").attr("value","32");//平时加班
	}
}
//调休改变其他选项
function  adjustYnChange(personId,i){
	var allID=changeID(personId,i)//获取该行的各元素的id
	var $form = $("#applyOvertimeBatchFormP");
	var adjustYn = $("#applyOvertimeBatchFormP select[id="+allID.get("adjustYn")+"]").val();//调休
	var teshuYn = $("#applyOvertimeBatchFormP select[id="+allID.get("teshuYn")+"]").val();//特殊
	var otPlaceType = $("#applyOvertimeBatchFormP select[id="+allID.get("otPlaceType")+"]").val();//社内外
	var dateType = $("#applyOvertimeBatchFormP input[id="+allID.get("dateType")+"]").val();//公司当天类型
	var peopleType = $("#applyOvertimeBatchFormP input[id="+allID.get("peopleType")+"]").val();//个人当天类型
	
 	if(adjustYn == '0'){
 		if(dateType=='1441'){
 			$form.find("select[name="+allID.get("otPlaceType")+"]").attr("value",otPlaceType);//社外
 	 		$form.find("select[name="+allID.get("otPlaceType")+"]").attr("disabled",false);
 		}else {
 			$form.find("select[name="+allID.get("otPlaceType")+"]").attr("value","INSIDE");//社外
 	 		$form.find("select[name="+allID.get("otPlaceType")+"]").attr("disabled",false);
 		}
		$form.find("select[name="+allID.get("teshuYn")+"]").attr("value",'0');//特殊
		$form.find("select[name="+allID.get("teshuYn")+"]").attr("disabled",false);
 	}else if(adjustYn == '1'){
 		if(dateType=='1441'){
 			$form.find("select[name="+allID.get("otPlaceType")+"]").attr("value",otPlaceType);//社外
 	 		$form.find("select[name="+allID.get("otPlaceType")+"]").attr("disabled",false);
 		}else {
 			$form.find("select[name="+allID.get("otPlaceType")+"]").attr("value","INSIDE");//社外
 	 		$form.find("select[name="+allID.get("otPlaceType")+"]").attr("disabled",false);
 		}
 		
 		$form.find("select[name="+allID.get("teshuYn")+"]").attr("value",'0');//特殊
 		$form.find("select[name="+allID.get("teshuYn")+"]").attr("disabled",true);
 	}
 	changeApplyType(personId,i);
}
//特殊改变其他选项
function  teshuYnChange(personId,i){
	var allID=changeID(personId,i)//获取该行的各元素的id
	var $form = $("#applyOvertimeBatchFormP");
	var adjustYn = $("#applyOvertimeBatchFormP select[id="+allID.get("adjustYn")+"]").val();//调休
	var teshuYn = $("#applyOvertimeBatchFormP select[id="+allID.get("teshuYn")+"]").val();//特殊
	var otPlaceType = $("#applyOvertimeBatchFormP select[id="+allID.get("otPlaceType")+"]").val();//社内外
	var dateType = $("#applyOvertimeBatchFormP input[id="+allID.get("dateType")+"]").val();//公司当天类型
	var peopleType = $("#applyOvertimeBatchFormP input[id="+allID.get("peopleType")+"]").val();//个人当天类型
	
	
 	if(teshuYn == '0'){
		$form.find("select[name="+allID.get("otPlaceType")+"]").attr("value",'INSIDE');//社外
		$form.find("select[name="+allID.get("otPlaceType")+"]").attr("disabled",false);
		if(dateType=='1440'){
			$form.find("select[name="+allID.get("adjustYn")+"]").attr("value",'0');//调休
			$form.find("select[name="+allID.get("adjustYn")+"]").attr("disabled",true);
		}else{
			$form.find("select[name="+allID.get("adjustYn")+"]").attr("value",'0');//调休
			$form.find("select[name="+allID.get("adjustYn")+"]").attr("disabled",false);	
		}
 	}else if(teshuYn == '1'){
 		$form.find("select[name="+allID.get("otPlaceType")+"]").attr("value",'INSIDE');//社外
 		$form.find("select[name="+allID.get("otPlaceType")+"]").attr("disabled",true);
 		$form.find("select[name="+allID.get("adjustYn")+"]").attr("value",'0');//调休
 		$form.find("select[name="+allID.get("adjustYn")+"]").attr("disabled",true);
 	}
 	changeApplyType(personId,i);
}
//社内外改变其他选项
function  otplaceTypeChange(personId,i){
	var allID=changeID(personId,i)//获取该行的各元素的id
	var $form = $("#applyOvertimeBatchFormP");
	var adjustYn = $("#applyOvertimeBatchFormP select[id="+allID.get("adjustYn")+"]").val();//调休
	var teshuYn = $("#applyOvertimeBatchFormP select[id="+allID.get("teshuYn")+"]").val();//特殊
	var otPlaceType = $("#applyOvertimeBatchFormP select[id="+allID.get("otPlaceType")+"]").val();//社内外
	var dateType = $("#applyOvertimeBatchFormP input[id="+allID.get("dateType")+"]").val();//公司当天类型
	var peopleType = $("#applyOvertimeBatchFormP input[id="+allID.get("peopleType")+"]").val();//个人当天类型
 	if(otPlaceType == 'INSIDE'){
 		if(dateType=='1441'){//只有周末有调休
 			$form.find("select[name="+allID.get("adjustYn")+"]").attr("value",adjustYn);//调休
 			$form.find("select[name="+allID.get("adjustYn")+"]").attr("disabled",false);
 		}else{
 			$form.find("select[name="+allID.get("adjustYn")+"]").attr("value","0");//调休
 			$form.find("select[name="+allID.get("adjustYn")+"]").attr("disabled",true);
 		}
		$form.find("select[name="+allID.get("teshuYn")+"]").attr("value",'0');//特殊
		$form.find("select[name="+allID.get("teshuYn")+"]").attr("disabled",false);
 	}else if(otPlaceType == 'OUTSIDE'){
 		if(dateType=='1441'){//只有周末有调休
 			$form.find("select[name="+allID.get("adjustYn")+"]").attr("value",adjustYn);//调休
 			$form.find("select[name="+allID.get("adjustYn")+"]").attr("disabled",false);
 		}else{
 			$form.find("select[name="+allID.get("adjustYn")+"]").attr("value","0");//调休
 			$form.find("select[name="+allID.get("adjustYn")+"]").attr("disabled",true);
 		}
 		$form.find("select[name="+allID.get("teshuYn")+"]").attr("value",'0');//特殊
 		$form.find("select[name="+allID.get("teshuYn")+"]").attr("disabled",true);
 	}
 	changeApplyType(personId,i);
}
//计算时长
function calPoTLengthB(){
	var from_date = $("#applyOvertimeBatchFormP input[id='otFromDate']").val();
	var cpnyId = document.getElementById("CPNY_ID").value;
	var fromTime = '';
	var toTime = '';
	if(cpnyId == 'LGEQH'){
	    fromTime = document.getElementById("otFromTime_h").value+":"+document.getElementById("otFromTime_m").value; 
	    toTime = document.getElementById("otToTime_h").value+":"+document.getElementById("otToTime_m").value; 
	}else{
	    fromTime = $("#applyOvertimeBatchFormP select[id='otFromTime']").val();
	    toTime = $("#applyOvertimeBatchFormP select[id='otToTime']").val();
	}
	var endDayOffset = $("#applyOvertimeBatchFormP select[id='endDayOffset']").val();

	if(from_date==""){
       alertMsg.error("请先选择加班日期！");
       return false;
	}
	
	var otfromtime = from_date + " " + fromTime + ":" + "00";
	var ottotime = from_date + " " + toTime + ":" + "00";
	//if(comptime(otfromtime,ottotime)!=1 && endDayOffset == '0'){
		//alertMsg.error("结束时间要不得早于开始时间，请重新选择结束时间！");
	    //return false;
	//}
	//if(comptime(otfromtime,ottotime)==1 || endDayOffset == '1'){
		$.ajax({
			 cache: false,
			 type: 'post',
			 async:false,
			 url: "/ess/infoApply/getOtApplyLength",
			 data: [{ name: 'END_DAY_OFFSET', value: endDayOffset },
			        { name: 'OT_FROM_TIME', value: otfromtime },
			        { name: 'OT_TO_TIME', value: ottotime }],
			 dataType:"json",
			 success: function(response) {
				 document.getElementById('otApplyLengthB').innerHTML = response;
			 }
		});
	//}
}

//计算时长
function calPoTLengthBatch(personId,i){
	var from_date = $("#applyOvertimeBatchFormP input[id='"+personId+'_FROM_DATE_'+i+"']").val();
	var cpnyId = document.getElementById("CPNY_ID").value;
	var fromTime = '';
	var toTime = '';
	if(cpnyId == 'LGEQH'){
	    fromTime = $("#applyOvertimeBatchFormP select[id='"+personId+'_OT_FROM_TIME_H_'+i+"']").val()+":"+$("#applyOvertimeBatchFormP select[id='"+personId+'_OT_FROM_TIME_M_'+i+"']").val(); 
	    toTime = $("#applyOvertimeBatchFormP select[id='"+personId+'_OT_TO_TIME_H_'+i+"']").val()+":"+$("#applyOvertimeBatchFormP select[id='"+personId+'_OT_TO_TIME_M_'+i+"']").val(); 
	}else{
	    fromTime = $("#applyOvertimeBatchFormP select[id='"+personId+'_OT_FROM_TIME_'+i+"']").val();
	    toTime = $("#applyOvertimeBatchFormP select[id='"+personId+'_OT_TO_TIME_'+i+"']").val();
	}
	var endDayOffset = $("#applyOvertimeBatchFormP select[id='"+personId+'_END_DAY_OFFSET_'+i+"']").val();
	
	if(from_date==""){
		alertMsg.error("请先选择加班日期！");
       	return false;
	}
	
	var otfromtime = from_date + " " + fromTime + ":" + "00";
	var ottotime = from_date + " " + toTime + ":" + "00";
	
	if(comptime(otfromtime,ottotime)!=1 && endDayOffset == '0'){
		alertMsg.error("结束时间要不得早于开始时间，请重新选择结束时间！");
	    return false;
	}
	var teshuYn = $("#applyOvertimeBatchFormP select[id='"+personId+'_TESHU_YN_'+i+"']").val();//特殊
	var otPlaceType = $("#applyOvertimeBatchFormP select[id='"+personId+'_OT_PLACE_TYPE_'+i+"']").val();//社内外
	if(comptime(otfromtime,ottotime)==1 || endDayOffset == '1'){
		  
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
			        { name: 'OT_TO_TIME', value: ottotime }],
			 dataType:"json",
			 success: function(response) {
			if(response<0){
				response =0;
			 }
			 document.getElementById(personId+"_OT_APPLY_LENGTH_"+i).innerHTML = response+" 小时";
				 
			 }
		});
		
	}
}
</script>



<script type="text/javascript">
//新增一行
function addNewTableP() {
	var table = document.getElementById("tableTitleName_P");
	var colums = table.rows[0].cells.length;
	var cloumeNum1=$("#tableTitleName_P tr").length;
	var tr = table.insertRow();
	tr.id="tr_"+cloumeNum1;
	var $form = $("#applyOvertimeBatchFormP"); //$form.find("[name='EMPID']")
	var bool=true;
	
  	for(var i=2;i<cloumeNum1;i++){
  	   if(document.getElementById("tableTitleName_P").rows[i].cells[4]==undefined){
  		   bool=true;
  		   break;
  	   }	
	   var v=document.getElementById("tableTitleName_P").rows[i].cells[4].TEXT;
	   var empid="EMPID_"+i;
	   if(document.getElementById(empid)==null){
			continue;
		}
		var empidValue=document.getElementById(empid).value;
		if(empidValue==""){
			alertMsg.error('<spring:message code="alert.ar.applyifo.addinfo"/>');
			var id="tr_"+cloumeNum1;
			var tr = document.getElementById(id);
			tr.parentNode.removeChild(tr);
			bool= false;
			break;
		}
  	}
  	if(bool==false){
	  	return false;
  	}	
  	//判断一下加班申请的方式P:时间点方式；L:时间长度方式；
  	var otTimeType= document.getElementById("OT_TIME_TYPE").value;
  	
	var td1 = tr.insertCell(0);  
		td1.className="td_center";                                             
		td1.innerHTML="<img src='/resources/css/ligerUI/skins/icons/delete.gif' onclick='deleteNewTableP(this)' /><input type='hidden' name='cloumeNumValue' value=''/>";
	var td0 = tr.insertCell(1);  
		td0.className="td_left";     
		//td0.innerHTML='<input type="checkbox" id="c1" name="c1"/>'
		//td0.innerHTML="<input id='chexkbox_"+cloumeNum1+"' type='checkbox' name='c1' value='' />";
	var td2=tr.insertCell(2);
		td2.className="";
		td2.innerHTML="<input type='text' id='EMPID_"+cloumeNum1+"' name='EMPID' onkeydown='F_HR_SubmitKeyClickP("+cloumeNum1+",event)' size='10'/><input type='hidden' id='empid*"+cloumeNum1+"' value=''/>";
	var td3=tr.insertCell(3);
		td3.className="td_left";
		td3.innerHTML="<input type='hidden' value='' id='LOCAL_NAME_"+cloumeNum1+"' name='LOCAL_NAME'><div id='LOCAL_NAME_"+cloumeNum1+"_div'  style='display: none'></div>";
	var td4=tr.insertCell(4);
		td4.className="td_left";
		td4.innerHTML="<input type='hidden' value='' id='POSITION_NO_"+cloumeNum1+"' name=''><div id='POSITION_NO_"+cloumeNum1+"_div'  style='display: none'></div>";

	var td5=tr.insertCell(5);//加班日期
		td5.className="td_center";
		
	var td6=tr.insertCell(6);//开始时间
		td6.className="td_left";
		
	var td7=tr.insertCell(7);//结束时间
		td7.className="td_left";
		
	var td8=tr.insertCell(8);//是否跨天
		td8.className="td_left";
		
	var td9=tr.insertCell(9);//时间长度
		td9.className="td_left";
		
		
	var td11=tr.insertCell(10);//是否调休
		td11.className="td_left";
		
	var td12=tr.insertCell(11);//社内/外
		td12.className="td_left";
	var td10=tr.insertCell(12);//加班类型
		td10.className="td_left";	
	var td13=tr.insertCell(13);//加班事由
		td13.className="td_left";
		
	var td14=tr.insertCell(14);//查看
		td14.className="td_left";
} 
function deleteNewTableP(row){
	var table = document.getElementById("tableTitleName_P");
	$(row).parent().parent().remove();
}
//添加新行的工号文本框触发
function F_HR_SubmitKeyClickP(i,event){
	var event = event || window.event;
   	if(event.keyCode==13){
   		var emp= document.getElementById("EMPID_"+i).value;
		//15119设置默认查找在职员工
		document.getElementById("onck").href=encodeURI(encodeURI("/hrm/transferOrder/viewEmpIdList?pageNum=1&seach_EMPID="+emp+'&seach_LOCAL_NAME='+emp+'&seach_IDCARD_NO='+emp+'&seach_EMP_OFFICE=15119&empid='+i+'&viewEmpIdListColnum='+i+"&seach_NAVID=ess" ));
		//document.getElementById("onck").href=encodeURI(encodeURI("/hrm/empinfo/viewEmpIdList?pageNum=1&seach_EMP_OFFICE=15119'"));
		document.getElementById("onck").click();	
	}
 }
//双击返回的人员的数据
function F_HR_ShowMore1(personId,name,empid,i,deptname,deptno,glno,glname,dutyno,dutynoname,postno,postname,postgradeno,postgradename,posino,posiname){
	//personid_
	//i=document.getElementById("viewEmpIdListColnum").value;
	//alert(personId,name,empid,i,deptname,deptno,glno,glname,dutyno,dutynoname,postno,postname,postgradeno,postgradename,posino,posiname);
	if(document.getElementById("empid*"+i)!=null){
		document.getElementById("empid*"+i).value=personId;
		//var personIdValue=personId+","+i;
		//document.getElementById("empid*"+i).value=personIdValue;
	}
	//工号
	if(document.getElementById("EMPID_"+i)!=null){
		document.getElementById("EMPID_"+i).value=empid;
		if(document.getElementById("LOCAL_NAME_"+i)!=null){
			document.getElementById("LOCAL_NAME_"+i).value=name;		
			document.getElementById("LOCAL_NAME_"+i+"_div").innerHTML=name;
			document.getElementById("LOCAL_NAME_"+i+"_div").style.display='block';
		}
	}
	//职位名称 POSITION_NO_
	if(document.getElementById("POSITION_NO_"+i)!=null){
		if(document.getElementById("POSITION_NO_"+i+"_div")!=null){
			document.getElementById("POSITION_NO_"+i+"_div").innerHTML=posiname;
			document.getElementById("POSITION_NO_"+i+"_div").style.display='block';
			document.getElementById("POSITION_NO_"+i).value=posino;
		}else{
			document.getElementById("POSITION_NO_"+i).value=posino;
			//document.getElementById("DEPTNO"+i).value=deptname
		}
	}
	$.pdialog.closeCurrent();
	//var table = document.getElementById("tableTitleName_P");
	//var colums = table.rows[0].cells.length;
	//var tr = table.insertRow();
	var cloumeNum1=$("#tableTitleName_P tr").length;
	//总条数 
	var cloumeCount=$("#cloumeCount").val();
	if(cloumeCount=="0"){
		cloumeCount=2;
	}else{
		cloumeCount++;
	}
	//开始日期tableTitleName_P
	
	document.getElementById("tableTitleName_P").rows[cloumeNum1-1].childNodes[1].innerHTML = 
		"<input type='checkbox' id='c1' name='c1' value='"+personId+","+cloumeCount+"'/>";
		
	document.getElementById("tableTitleName_P").rows[cloumeNum1-1].childNodes[5].innerHTML = //
		"<input type='text' id='"+personId+"_FROM_DATE_"+cloumeCount+"' name='"+ personId + "_FROM_DATE_" + cloumeCount
		+"' class='date' readonly='true' format='yyyy-MM-dd' yearstart='-50' yearend='5' onClick='setdate(this);' "
		+"  onpropertychange='getPOtApplyTypeBatch("+personId+","+cloumeCount+");' size='10'/>";
	
	document.getElementById("tableTitleName_P").rows[cloumeNum1-1].childNodes[6].innerHTML=""
		+'<ait:time name="'+personId+'_OT_FROM_TIME_'+cloumeCount+'" spacing="30" selected="18:00" onChange="calPoTLengthBatch('+personId+','+cloumeCount+');"/>';
	
	document.getElementById("tableTitleName_P").rows[cloumeNum1-1].childNodes[7].innerHTML=""
		+'<ait:time name="'+personId+'_OT_TO_TIME_'+cloumeCount+'" spacing="30" selected="20:00" onChange="calPoTLengthBatch('+personId+','+cloumeCount+');"/>';
		//" <ait:time name='"+personId+"_OT_TO_TIME_"+cloumeCount+"' spacing='30' selected='08:30' onChange='calPoTLengthBatch();'/>";
		
	document.getElementById("tableTitleName_P").rows[cloumeNum1-1].childNodes[8].innerHTML=
		" <select id='"+personId+"_END_DAY_OFFSET_"+cloumeCount+"' name='"+personId+"_END_DAY_OFFSET_"+cloumeCount+"' onchange='calPoTLengthBatch("+personId+","+cloumeCount+");'>"
		+"   <option value='0'>否</option> "
		+"   <option value='1'>是</option> "
		+" </select> ";                                                                          
	
	document.getElementById("tableTitleName_P").rows[cloumeNum1-1].childNodes[9].innerHTML="<div id='"+personId+"_OT_APPLY_LENGTH_"+cloumeCount+"'></div>";
		
	document.getElementById("tableTitleName_P").rows[cloumeNum1-1].childNodes[10].innerHTML=
		" <ait:SelectSyCodeByCpnyID parentNo='31' cnpyID='${defaultCpny}' name='"+personId+"_OT_APPLY_TYPE_CODE_"+cloumeCount+"' selected=''  limit='all'/>";
	
	document.getElementById("tableTitleName_P").rows[cloumeNum1-1].childNodes[11].innerHTML=
		" <select id='"+personId+"_ADJUST_YN_"+cloumeCount+"' name='"+personId+"_ADJUST_YN_"+cloumeCount+"'>"
		+"   <option value='0'>否</option> "
		+"   <option value='1'>是</option> "
		+" </select> ";

		document.getElementById("tableTitleName_P").rows[cloumeNum1-1].childNodes[12].innerHTML=
			" <select id='"+personId+"_TESHU_YN_"+cloumeCount+"' name='"+personId+"_TESHU_YN_"+cloumeCount+"'>"
			+"   <option value='0'>否</option> "
			+"   <option value='1'>是</option> "
			+" </select> ";
		
	document.getElementById("tableTitleName_P").rows[cloumeNum1-1].childNodes[13].innerHTML=
		" <select id='"+personId+"_OT_PLACE_TYPE_"+cloumeCount+"' name='"+personId+"_OT_PLACE_TYPE_"+cloumeCount+"'>"
		+"   <option value='INSIDE'>社内</option> "
		+"   <option value='OUTSIDE'>社外</option> "
		+" </select> ";
	
	document.getElementById("tableTitleName_P").rows[cloumeNum1-1].childNodes[14].innerHTML=
		"<textarea id='"+personId+"_APPLY_REMARK_"+cloumeCount+"' name='"+personId+"_APPLY_REMARK_"+cloumeCount+"' cols='25' rows='1'></textarea>";
		                                                               
	document.getElementById("tableTitleName_P").rows[cloumeNum1-1].childNodes[15].innerHTML=
		"<a rel='"+personId+"_overtimeApplyBatchPAffirmView' onclick='passPOvertimeApplyValue("+personId+","+cloumeCount+");'><span style='cursor:pointer;'><spring:message code='ess.infoApply.title.viewDetail'/><!--查看详细--></span></a><a id='"+personId+"_viewOvertimeApplyBatchPHref'   href='/ess/infoApply/viewAffirmorByPersonIdAndAppCode?APPLY_TYPE_NO=31&&PERSON_ID="+personId+"' target='dialog' mask='true' width='300' height='300'></a>";
  	
	$.ajaxSettings.global=true;
	$("#cloumeCount").val(cloumeCount);
}
</script>



<input type="hidden" value="${defaultCpny}" id="CPNY_ID" name="CPNY_ID" />


<div class="pageContent">
	<form style="margin:0px;padding:0px;" name="applyOvertimeBatchFormP"
		id="applyOvertimeBatchFormP" method="post"
		action="/ess/infoApply/addPBatchOtApplyUpdate"
		class="pageForm required-validate"
		onsubmit="return validateBatchOvertimeApplyCallback_P(this,navTabAjaxDone);">
		<a href="/ess/infoApply/viewAffirmorByPersonIdAndAppCode?test=1"
			target="dialog" mask="true" width="300" height="300"
			id="viewOvertimeApplyBatchPHref"></a> <input id="APPLY_TYPE"
			name="APPLY_TYPE" type="hidden" value="BATCH" /> <input
			id="AFFIRM_FLAG" name="AFFIRM_FLAG" type="hidden" value="" /> <input
			type="hidden" id="type" name="type" value="update"></input> <input
			type="hidden" id="update_apply_no" name="update_apply_no"
			value="${APPLY_NO}"></input> <input type="hidden" id="OT_TIME_TYPE"
			name="OT_TIME_TYPE" value="P" />

		<div class="formBar">
			<div style="color:red">提示：修改时未选中的加班会被删除。</div>
			<ul class="toolBar">
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<!--点击填充-->
							<button type="button" onclick="fillItemOtP();">点击填充</button>
						</div>
					</div></li>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<!--保存-->
							<button type="button" onclick="submitPFormPre(-1);">保存</button>
						</div>
					</div></li>
				<li>
					<div class="button">
						<div class="buttonContent">
							<!--提交-->
							<button type="button" onclick="submitPFormPre(0);">提交</button>
						</div>
					</div></li>

			</ul>
		</div>
		<table class="tablea" width="100%" layoutH="183" id="tableTitleName_P">
			<input type="hidden" value="${totalCount}" id="cloumeCount"
				name="cloumeCount" />
			<thead>
				<tr>
					<!-- 
					<th width="10">
						<img src="/resources/css/ligerUI/skins/icons/add.gif" onclick="addNewTableP()"/>
					</th>
				    -->
					<th width="20"><input type="checkbox" class="checkboxCtrl"
						group="c1" /></th>
					<th width="40">
						<!--工号--> <spring:message code="public.title.empId" /></th>
					<th width="40">
						<!--姓名--> <spring:message code="public.title.name" /></th>
					<th width="100">部门</th>

					<th width="60">
						<!-- 加班日期 --> 加班日期</th>
					<th width="45">
						<!--开始时间--> <spring:message code="ess.infoApply.title.startTime" />
					</th>
					<th width="45">
						<!--结束时间--> <spring:message code="ess.infoApply.title.endTime" />
					</th>
					<th width="30">是否跨天</th>
					<th width="65">加班时长</th>


					<th width="30">是否调休</th>
					<th width="30">是否特殊</th>
					<th width="30">社内/外</th>
					<th width="60">
						<!--加班类型--> <spring:message
							code="ess.viewApply.title.overtimeApplyType" /></th>
					<th width="50">
						<!--工作内容--> <spring:message code="ess.infoApply.title.workContent" />
					</th>
					<!--
					<th width="40">决裁者
						<spring:message code="ess.infoApply.title.affirmor"/>
					</th>	
				-->
				</tr>
			</thead>
			<input id="APPLY_TYPE_NO" name="APPLY_TYPE_NO" type="hidden"
				size="30" value="31" />
			<tr>
				<td colspan="4">
					<!--<spring:message code="ess.infoApply.title.fillItemIntroduction"/>点击填充按钮对选中的行进行填充-->
					&nbsp;&nbsp;公共填充数据： <!--<input type="checkbox" id="continueApply" name="continueApply" value="1"/>&nbsp;&nbsp;<font color="red">连续申请</font>-->
				</td>
				<td><input type="text" id="otFromDate" name="otFromDate"
					class="date" format="yyyy-MM-dd" readonly="true" size="10"
					onpropertychange="getPOtApplyTypeBatch(0,0);" /></td>
				<td><c:if test="${defaultCpny eq 'LGEQH'}">
						<select name="otFromTime_h" id="otFromTime_h"
							onChange="calPoTLengthB();">
							<c:forEach items="${hourParam}" var="vlist" varStatus="i">
								<option value="${vlist.OT_TIME}"
									<c:if test="${vlist.OT_TIME eq '17'}">selected</c:if>>${vlist.OT_TIME}</option>
							</c:forEach>
						</select>
						<select name="otFromTime_m" id="otFromTime_m"
							onChange="calPoTLengthB();">
							<c:forEach items="${muniteParam}" var="list" varStatus="i">
								<option value="${list.OT_TIME}"
									<c:if test="${list.OT_TIME eq '30'}">selected</c:if>>${list.OT_TIME}</option>
							</c:forEach>
						</select>
					</c:if> <c:if test="${defaultCpny ne 'LGEQH'}">
						<ait:time name="otFromTime" spacing="30" selected="18:00"
							onChange="calPoTLengthB();" />
					</c:if></td>
				<td><c:if test="${defaultCpny eq 'LGEQH'}">
						<select name="otToTime_h" id="otToTime_h"
							onChange="calPoTLengthB();">
							<c:forEach items="${hourParam}" var="vlist" varStatus="i">
								<option value="${vlist.OT_TIME}"
									<c:if test="${vlist.OT_TIME eq '20'}">selected</c:if>>${vlist.OT_TIME}</option>
							</c:forEach>
						</select>
						<select name="otToTime_m" id="otToTime_m"
							onChange="calPoTLengthB();">
							<c:forEach items="${muniteParam}" var="list" varStatus="i">
								<option value="${list.OT_TIME}"
									<c:if test="${list.OT_TIME eq '30'}">selected</c:if>>${list.OT_TIME}</option>
							</c:forEach>
						</select>
					</c:if> <c:if test="${defaultCpny ne 'LGEQH'}">
						<ait:time name="otToTime" spacing="30" selected="20:00"
							onChange="calPoTLengthB();" />
					</c:if></td>

				<td><select id="endDayOffset" name="endDayOffset"
					onchange="calPoTLengthB();">
						<option value="0"
							<c:if test="${endDayOffset eq '0' }">selected="selected"</c:if>>
							否</option>
						<option value="1"
							<c:if test="${endDayOffset eq '1' }">selected="selected"</c:if>>
							是</option>
				</select></td>

				<td>
					<div id="otApplyLengthB"></div> <%-- 
				   <select name="otApplyHour" id="otApplyHour" disabled="disabled">
						<option value=""><!--请选择-->
                   			<spring:message code="sys.affirm.title.choose"/>
                   		</option>
						<c:forEach var="h" begin="1" end="8" step="1">
							<option value="${h}" <c:if test="${h eq otApplyHour}">selected</c:if>>
								${h}小时
							</option>
						</c:forEach>
					</select>
					<select name="otApplyMinute" id="otApplyMinute" disabled="disabled">
						<option value=""><!--请选择-->
                   			<spring:message code="sys.affirm.title.choose"/>
                   		</option>
						<c:forEach var="m" begin="0" end="59" step="30">
							<option value="${m}" <c:if test="${m eq otApplyMinute}">selected</c:if>>
								${m}分
							</option>
						</c:forEach>
					</select>
					--%></td>


				<td><select id="adjustYn" name="adjustYn" disabled="true"
					onChange="adjustYnChange(0,0);">
						<option value="0"
							<c:if test="${adjustYn eq '0' }">selected="selected"</c:if>>
							否</option>
						<option value="1"
							<c:if test="${adjustYn eq '1' }">selected="selected"</c:if>>
							是</option>
				</select></td>

				<td><select id="teshuYn" name="teshuYn" disabled="true"
					onChange="teshuYnChange(0,0);">
						<option value="0"
							<c:if test="${teshuYn eq '0' }">selected="selected"</c:if>>
							否</option>
						<option value="1"
							<c:if test="${teshuYn eq '1' }">selected="selected"</c:if>>
							是</option>
				</select></td>
				<td><select id="otPlaceType" name="otPlaceType" disabled="true"
					onChange="otplaceTypeChange(0,0);">
						<option value="INSIDE"
							<c:if test="${otPlaceType eq 'INSIDE' }">selected="selected"</c:if>>
							社内</option>
						<option value="OUTSIDE"
							<c:if test="${otPlaceType eq 'OUTSIDE' }">selected="selected"</c:if>>
							社外</option>
				</select></td>
				<td align="right"><ait:SelectSyCodeByCpnyID parentNo="31"
						cnpyID="${defaultCpny}" disabled="true" name="otApplyTypeCode"
						limit="all" /> <input type="hidden" id="publicDateType"
					name="publicDateType" value="" /> <input type="hidden"
					id="publicPeoPleType" name="publicPeoPleType" value="0" /> <input
					type="hidden" id="otApplyTypeCode" name="otApplyTypeCodeValue"
					value="" /></td>
				<td><textarea id="otApplyRemark" name="otApplyRemark" cols="25"
						rows="1"></textarea></td>
				<!--				
				<td>&nbsp;</td>
			-->
			</tr>
			<tbody>
				<c:forEach items="${personList}" var="person" varStatus="i">
					<tr target="sid" rel="${person.PERSON_ID}">
						<!--
					
						<td>
							<img src='/resources/css/ligerUI/skins/icons/delete.gif' onclick='deleteNewTable(this)' />
						</td>
					    -->
						<td><input type="checkbox" id="c1" name="c1"
							value="${person.PERSON_ID},${i.count}" /></td>
						<td>${person.EMPID} <input type="hidden"
							id="empid*${i.count}" name="" value="${person.PERSON_ID}" /></td>
						<td>${person.LOCAL_NAME}</td>
						<td>${person.DEPT_NAME}</td>

						<td width="60"><input type="text"
							id="${person.PERSON_ID}_FROM_DATE_${i.count}"
							name="${person.PERSON_ID}_FROM_DATE_${i.count}"
							value="${person.FROM_DATE}" class="date" format="yyyy-MM-dd"
							readonly="true" size="10"
							onpropertychange="getPOtApplyTypeBatch(${person.PERSON_ID},${i.count});" />


						</td>
						<td width="45">
						<c:if test="${defaultCpny eq 'LGEQH'}">
								<select name="${person.PERSON_ID}_OT_FROM_TIME_H_${i.count}"
									id="${person.PERSON_ID}_OT_FROM_TIME_H_${i.count}"
									onChange="calPoTLengthBatch(${person.PERSON_ID},${i.count});">
									<c:forEach items="${hourParam}" var="vlist" varStatus="j">
										<option value="${vlist.OT_TIME}"
											<c:if test="${vlist.OT_TIME eq '17'}">selected</c:if>>${vlist.OT_TIME}</option>
									</c:forEach>
								</select>
								<select name="${person.PERSON_ID}_OT_FROM_TIME_M_${i.count}"
									id="${person.PERSON_ID}_OT_FROM_TIME_M_${i.count}"
									onChange="calPoTLengthBatch(${person.PERSON_ID},${i.count});">
									<c:forEach items="${muniteParam}" var="list" varStatus="j">
										<option value="${list.OT_TIME}"
											<c:if test="${list.OT_TIME eq '30'}">selected</c:if>>${list.OT_TIME}</option>
									</c:forEach>
								</select>
							</c:if> 
							<c:if test="${defaultCpny ne 'LGEQH'}">
								<ait:time name="${person.PERSON_ID}_OT_FROM_TIME_${i.count}"
									spacing="30" selected="18:00"
									onChange="calPoTLengthBatch(${person.PERSON_ID},${i.count});" />
							</c:if> 
<!-- 							<ait:time name="${person.PERSON_ID}_OT_FROM_TIME_${i.count}"
								spacing="30" selected="${person.FROM_TIME}" 
								onChange="calPoTLengthBatch(${person.PERSON_ID},${i.count});" /> -->

						</td>
						<td width="45">
						<c:if test="${defaultCpny eq 'LGEQH'}">
                     <select name="${person.PERSON_ID}_OT_TO_TIME_H_${i.count}" id="${person.PERSON_ID}_OT_TO_TIME_H_${i.count}" onChange="calPoTLengthBatch(${person.PERSON_ID},${i.count});">
						 <c:forEach items="${hourParam}" var="vlist" varStatus="j">
						   <option value="${vlist.OT_TIME}" <c:if test="${vlist.OT_TIME eq '20'}">selected</c:if>>${vlist.OT_TIME}</option>
					     </c:forEach>
					  </select>
					  <select name="${person.PERSON_ID}_OT_TO_TIME_M_${i.count}" id="${person.PERSON_ID}_OT_TO_TIME_M_${i.count}" onChange="calPoTLengthBatch(${person.PERSON_ID},${i.count});">
						<c:forEach items="${muniteParam}" var="list" varStatus="j">
						  <option value="${list.OT_TIME}" <c:if test="${list.OT_TIME eq '30'}">selected</c:if>>${list.OT_TIME}</option>
					    </c:forEach>
					  </select>		
                    </c:if>
                   <c:if test="${defaultCpny ne 'LGEQH'}">
                    <ait:time name="${person.PERSON_ID}_OT_TO_TIME_${i.count}" spacing="30" selected="20:00" onChange="calPoTLengthBatch(${person.PERSON_ID},${i.count});"/>
                   </c:if>					
<!-- 						<ait:time 
 								name="${person.PERSON_ID}_OT_TO_TIME_${i.count}" spacing="30" 
 								selected="${person.TO_TIME}" 
 								onChange="calPoTLengthBatch(${person.PERSON_ID},${i.count});" /> -->
						</td>
						<td><select
							id="${person.PERSON_ID}_END_DAY_OFFSET_${i.count}"
							name="${person.PERSON_ID}_END_DAY_OFFSET_${i.count}"
							onchange="calPoTLengthBatch(${person.PERSON_ID},${i.count});">
								<option value="0"
									<c:if test="${person.END_DAY_OFFSET eq '0' }">selected="selected"</c:if>>
									否</option>
								<option value="1"
									<c:if test="${person.END_DAY_OFFSET eq '1' }">selected="selected"</c:if>>
									是</option>
						</select></td>
						<td>
							<div id="${person.PERSON_ID}_OT_APPLY_LENGTH_${i.count}">
								${person.OT_LENGTH}</div></td>


						<td><select id="${person.PERSON_ID}_ADJUST_YN_${i.count}"
							name="${person.PERSON_ID}_ADJUST_YN_${i.count}" disabled="true"
							onchange="adjustYnChange(${person.PERSON_ID},${i.count});">

								<option value="0"
									<c:if test="${person.ADJUST_YN eq '0' }">selected="selected"</c:if>>
									否</option>
								<option value="1"
									<c:if test="${person.ADJUST_YN eq '1' }">selected="selected"</c:if>>
									是</option>
						</select></td>

						<td><select id="${person.PERSON_ID}_TESHU_YN_${i.count}"
							name="${person.PERSON_ID}_TESHU_YN_${i.count}" disabled="true"
							onchange="teshuYnChange(${person.PERSON_ID},${i.count});">
								<option value="0"
									<c:if test="${person.TESHU_YN eq '0' }">selected="selected"</c:if>>
									否</option>
								<option value="1"
									<c:if test="${person.TESHU_YN eq '1' }">selected="selected"</c:if>>
									是</option>
						</select></td>

						<td><select id="${person.PERSON_ID}_OT_PLACE_TYPE_${i.count}"
							name="${person.PERSON_ID}_OT_PLACE_TYPE_${i.count}"
							disabled="true"
							onchange="otplaceTypeChange(${person.PERSON_ID},${i.count});">
								<option value="INSIDE"
									<c:if test="${person.OT_PLACE_TYPE eq 'INSIDE' }">selected="selected"</c:if>>
									社内</option>
								<option value="OUTSIDE"
									<c:if test="${person.OT_PLACE_TYPE eq 'OUTSIDE' }">selected="selected"</c:if>>
									社外</option>
						</select></td>
						<td><ait:SelectSyCodeByCpnyID parentNo="31"
								cnpyID="${defaultCpny}"
								name="${person.PERSON_ID}_OT_APPLY_TYPE_CODE_${i.count}_show"
								disabled="true" selected="${person.OT_TYPE_CODE}" limit="all" />
							<!-- 一些隐藏的参数 --> <input type='hidden'
							id="${person.PERSON_ID}_OT_APPLY_TYPE_CODE_${i.count}"
							name="${person.PERSON_ID}_OT_APPLY_TYPE_CODE_${i.count}" value='' />
							<input type='hidden' id="${person.PERSON_ID}_dateType_${i.count}"
							name="${person.PERSON_ID}_dateType_${i.count}" value='' /> <input
							type='hidden' id="${person.PERSON_ID}_peopleType_${i.count}"
							name="${person.PERSON_ID}_peopleType_${i.count}" value='' /></td>
						<td><input type="text"
							id="${person.PERSON_ID}_APPLY_REMARK_${i.count}"
							name="${person.PERSON_ID}_APPLY_REMARK_${i.count}"
							value="${person.APPLY_OT_REMARK }" size="20" /></td>
						<!--<td>
					 
						       <a rel="${person.PERSON_ID}_overtimeApplyBatchLAffirmView" onclick="passOvertimeApplyValue(${person.PERSON_ID},${i.count});">
							   <span style="cursor:pointer;"><spring:message code="ess.infoApply.title.viewDetail"/>查看详细</span>
							</a>
						    <a href="/ess/infoApply/viewAffirmorByPersonIdAndAppCode?APPLY_TYPE_NO=31&&PERSON_ID=${person.PERSON_ID}"  
						       target="dialog" mask="true" width="300" height="300" id="123456_viewOvertimeApplyBatchLHref" ></a>
					    </td> 	
					-->
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<div id="overtimeApplyBatchPAffirmView"
			style="border:10;overflow:auto;bottom:10;right:100;width:300;z-index:1;"></div>
	</form>
	<c:set value="/ess/infoApply/viewOtAffirmPBatchList" var="pageUrl" />
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>
