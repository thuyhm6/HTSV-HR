<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script type="text/javascript">
<!--
function validateBatchOvertimeApplyCallback_ess0302(form,callback) {	
	var $form = $("#applyOvertimeBatchForm");
	if (!$form.valid()) {
		return false;
	}
	
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
		  if($form.find("[name='"+personId+"_FROM_TIME_HOUR_"+k+"']").val()==''){
			  	
				alertMsg.error('<spring:message code="alert.message.ess.infoApply.startTimeIsMust"/>');
				$form.find("[name='"+personId+"_FROM_TIME_HOUR_"+k+"']").focus();
				checked=false;
				return false;
		   }
		 
          if($form.find("[name='"+personId+"_TO_TIME_HOUR_"+k+"']").val()==''){
				alertMsg.error('<spring:message code="alert.message.ess.infoApply.endTimeIsMust"/>');
				$form.find("[name='"+personId+"_TO_TIME_HOUR_"+k+"']").focus();
				checked=false;
				return false;
		   }
		  if($form.find("[name='"+personId+"_FROM_TIME_MINUTE_"+k+"']").val()==''){
				alertMsg.error('<spring:message code="alert.message.ess.infoApply.startTimeIsMust"/>');
				$form.find("[name='"+personId+"_FROM_TIME_MINUTE_"+k+"']").focus();
				checked=false;
				return false;
		   }
          if($form.find("[name='"+personId+"_TO_TIME_MINUTE_"+k+"']").val()==''){
				alertMsg.error('<spring:message code="alert.message.ess.infoApply.endTimeIsMust"/>');
				$form.find("[name='"+personId+"_TO_TIME_MINUTE_"+k+"']").focus();
				checked=false;
				return false;
		   }		   
          if($form.find("select[name='"+personId+"_OT_APPLY_TYPE_CODE_"+k+"']").val()==''){
				alertMsg.error('<spring:message code="alert.message.ess.infoApply.overtimeApplyTypeIsMust"/>');
				$form.find("select[name='"+personId+"_OT_APPLY_TYPE_CODE_"+k+"']").focus();
				checked=false;
				return false;
			}

		  if($form.find("[name='"+personId+"_TO_DATE_"+k+"']").val()!=''){	 
			  if($form.find("[name='"+personId+"_FROM_DATE_"+k+"']").val()>$form.find("[name='"+personId+"_TO_DATE_"+k+"']").val())
				{
				   alertMsg.error('<spring:message code="alert.message.ess.infoApply.startTimeNotLaterThanEndTime"/>');
				   $form.find("[name='"+personId+"_FROM_DATE_"+k+"']").focus();
				  checked=false;
				  return false;
				}
		  }	 
		
		//如果是连续申请 则只判断时间；否则日期+时间 判断
	      	if(document.getElementById("continueApply").checked == true ){
	      		if($form.find("[name='"+personId+"_FROM_TIME_HOUR_"+k+"']").val()>$form.find("[name='"+personId+"_TO_TIME_HOUR_"+k+"']").val())
				{
				   alertMsg.error('<spring:message code="alert.message.ess.infoApply.startTimeCanNotLaterThanEndTime"/>');
				   $form.find("[name='"+personId+"_FROM_TIME_HOUR_"+k+"']").focus();
				   checked=false;
				}
	      	}else{
		      	var fromDate = $form.find("[name='"+personId+"_FROM_DATE_"+k+"']").val();
		      	var fromTimeHour = $form.find("[name='"+personId+"_FROM_TIME_HOUR_"+k+"']").val();
		      	var fromTimeMinute = $form.find("[name='"+personId+"_FROM_TIME_MINUTE_"+k+"']").val();

		      	var toDate = $form.find("[name='"+personId+"_TO_DATE_"+k+"']").val();
		      	var toTimeHour = $form.find("[name='"+personId+"_TO_TIME_HOUR_"+k+"']").val();
		      	var toTimeMinute = $form.find("[name='"+personId+"_TO_TIME_MINUTE_"+k+"']").val();
		      	//如果结束日期为空，默认为当天日期
		      	if(toDate == ''){
		      		toDate = fromDate;
			    }
		      	
	      		var leavefromtime = fromDate + " " + fromTimeHour + ":" + fromTimeMinute + ":" + "00";
	      		var leavetotime = toDate + " " + toTimeHour + ":" + toTimeMinute + ":" + "00";
	      		
	      		
	      		if(comptime(leavefromtime,leavetotime)!=1){
	      		  					 
	      			alertMsg.error('<spring:message code="alert.message.ess.infoApply.startTimeCanNotLaterThanEndTime1"/>');
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
			success: function(data){ //请求成功后处理函数。
				if(data.statusCode=="200"){
					navTabSearch("searchOvertimeApplyBatchForm");
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
	    return false;
		}
	}
	return false ;
}

function passOvertimeApplyValue(personId,i)
{   
	
    var fromDate      = document.getElementById(personId+"_FROM_DATE_"+i).value;
    
     if(fromDate==""){
   
       alertMsg.error('<spring:message code="alert.message.ess.infoApply.startDateTimeIsMust"/>');
       return false;
	}
   
    var toDate        = document.getElementById(personId+"_TO_DATE_"+i+"").value;
	
    var fromTimeHour   = document.getElementById(personId+"_FROM_TIME_HOUR_"+i+"").value;
  
    var fromTimeMinute = document.getElementById(personId+"_FROM_TIME_MINUTE_"+i+"").value;
  
    var toTimeHour     = document.getElementById(personId+"_TO_TIME_HOUR_"+i+"").value;
    
    var toTimeMinute   = document.getElementById(personId+"_TO_TIME_MINUTE_"+i+"").value; 
   
    var applyTypeCode = document.getElementById(personId+"_OT_APPLY_TYPE_CODE_"+i+"").value;
    
    var applyTypeNo   = document.getElementById(personId+"_OT_APPLY_TYPE_CODE_"+i+"").value;
  
  
    if(fromDate==""){
   
       alertMsg.error('<spring:message code="alert.message.ess.infoApply.startDateTimeIsMust"/>');
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
   
	if(fromTimeHour==""){
       alertMsg.error('<spring:message code="alert.message.ess.infoApply.startTimeIsMust"/>');
       return false;
	}
	
    if(toTimeHour==""){
	   alertMsg.error('<spring:message code="alert.message.ess.infoApply.endTimeIsMust"/>');
	   return false;
	}
	if(fromTimeMinute==""){
       alertMsg.error('<spring:message code="alert.message.ess.infoApply.startTimeIsMust"/>');
       return false;
	}
    if(toTimeMinute==""){
	   alertMsg.error('<spring:message code="alert.message.ess.infoApply.endTimeIsMust"/>');
	   return false;
	}	

  //如果是连续申请 则只判断时间；否则日期+时间 判断
	if(document.getElementById("continueApply").checked == true ){
		if(fromTimeHour>toTimeHour){
		   alertMsg.error('<spring:message code="alert.message.ess.infoApply.startTimeCanNotLaterThanEndTime"/>');
		   return false;
		}
	}else{
		var leavefromtime = fromDate + " " + fromTimeHour + ":" + fromTimeMinute + ":" + "00";
		var leavetotime = toDate + " " + toTimeHour + ":" + toTimeMinute + ":" + "00";
		if(comptime(leavefromtime,leavetotime)!=1){
			alertMsg.error('<spring:message code="alert.message.ess.infoApply.startTimeCanNotLaterThanEndTime"/>');
			return false;
		}
	}
	
	if(applyTypeCode==""){
       alertMsg.error('<spring:message code="alert.message.ess.infoApply.overtimeApplyTypeIsMust"/>');
       return false;
	}
	//APPLY_TYPE_NO=31&&PERSON_ID=${person.PERSON_ID}
    document.getElementById("123456_viewOvertimeApplyBatchInfoHref").href = 
    document.getElementById("123456_viewOvertimeApplyBatchInfoHref").href+
    						"APPLY_TYPE_NO=31&&PERSON_ID="+personId+
                            "&&APPLY_TYPE_CODE="+applyTypeCode+"&&APPLY_TYPE_NO="+applyTypeNo+
                            "&&FROM_DATE="+fromDate+"&&TO_DATE="+toDate+
                            "&&FROM_TIME_HOUR="+fromTimeHour+"&&TO_TIME_HOUR="+toTimeHour+
                            "&&FROM_TIME_MINUTE="+fromTimeMinute+"&&TO_TIME_MINUTE="+toTimeMinute;
   	//alert(personId+"_viewOvertimeApplyBatchInfoHref");
   		//salert(document.getElementById(personId+"_viewOvertimeApplyBatchInfoHref").href);					  
    //document.getElementById(personId+"_viewOvertimeApplyBatchInfoHref").click();
    //       123456_viewOvertimeApplyBatchInfoHref          
    var hid="123456_viewOvertimeApplyBatchInfoHref";
   
    $("#"+hid).click();
}

function fillItemOt(){
	var $form = $("#applyOvertimeBatchForm");
	if (!$form.valid()) {
		return false;
	}
	
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
 
  var otToDate=document.getElementById("otToDate").value; 
  var otFromTimeHour=document.getElementById("otFromTimeHour").value; 
  var otToTimeHour=document.getElementById("otToTimeHour").value;
  var otFromTimeMinute=document.getElementById("otFromTimeMinute").value; 
  var otToTimeMinute=document.getElementById("otToTimeMinute").value;    
  var otApplyReason =document.getElementById("otApplyRemark").value; 
   
  //var otApplyTypeCode=document.getElementById("otApplyTypeCode").value; 
  

  var $form = $("#applyOvertimeBatchForm");
  //$form.find("[name='"+personId+"_FROM_DATE']")
  var cloumeCount =document.getElementById("cloumeCount").value;
  $form.find(":checkbox[id='c1']").each(function(index, checkBoxObj){
  if(checkBoxObj.checked){
      var personId = $(checkBoxObj).val().split(",")[0] ;
      	 var i=parseInt(index)+2;
      	 var k=$(checkBoxObj).val().split(",")[1] ;
      	 //30003784_FROM_DATE_1
        
         $form.find("[name='"+personId+"_FROM_DATE_"+k+"']").attr("value",otFromDate);
         $form.find("[name='"+personId+"_TO_DATE_"+k+"']").attr("value",otToDate);
         $form.find("[name='"+personId+"_FROM_TIME_HOUR_"+k+"']").attr("value",otFromTimeHour);
         $form.find("[name='"+personId+"_TO_TIME_HOUR_"+k+"']").attr("value",otToTimeHour);
         $form.find("[name='"+personId+"_FROM_TIME_MINUTE_"+k+"']").attr("value",otFromTimeMinute);
         $form.find("[name='"+personId+"_TO_TIME_MINUTE_"+k+"']").attr("value",otToTimeMinute);         
         //$form.find("select[name='"+personId+"_OT_APPLY_TYPE_CODE']").attr("value",otApplyTypeCode);
         $form.find("[name='"+personId+"_APPLY_REMARK_"+k+"']").attr("value",otApplyReason);
         getDate2(i,personId,otFromDate);
         
         
  }
  });
}

//比较时间 格式 yyyy-mm-dd hh:mi:ss
function comptime(beginTime,endTime){
	var beginTimes=beginTime.substring(0,10).split('-');
	var endTimes=endTime.substring(0,10).split('-');
	
	beginTime=beginTimes[1]+'-'+beginTimes[2]+'-'+beginTimes[0]+' '+beginTime.substring(10,19);
	endTime=endTimes[1]+'-'+endTimes[2]+'-'+endTimes[0]+' '+endTime.substring(10,19);

	// alert(beginTime+endTime+beginTime);
	
	//alert(Date.parse(endTime)+" "+Date.parse(beginTime));
	
	
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
function addNewTable() {
	var table = document.getElementById("tableTitleName_ess0302");
	var colums = table.rows[0].cells.length;

	var cloumeNum1=$("#tableTitleName_ess0302 tr").length;
	
	var tr = table.insertRow();
	tr.id="tr_"+cloumeNum1;
	var $form = $("#applyOvertimeBatchForm"); //$form.find("[name='EMPID']")
	var bool=true;
	
  	for(var i=2;i<cloumeNum1;i++){
  	   if(document.getElementById("tableTitleName_ess0302").rows[i].cells[4]==undefined){
  		   bool=true;
  		   break;
  	   }	
	   var v=document.getElementById("tableTitleName_ess0302").rows[i].cells[4].TEXT;
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
	var td1 = tr.insertCell(0);  
		td1.className="td_center";                                             
		td1.innerHTML="<img src='/resources/css/ligerUI/skins/icons/delete.gif' onclick='deleteNewTable(this)' /><input type='hidden' name='cloumeNumValue' value=''/>";
	var td0 = tr.insertCell(1);  
		td0.className="td_left";     
		//td0.innerHTML='<input type="checkbox" id="c1" name="c1"/>'
		//td0.innerHTML="<input id='chexkbox_"+cloumeNum1+"' type='checkbox' name='c1' value='' />";
	var td2=tr.insertCell(2);
		td2.className="";
		td2.innerHTML="<input type='text' id='EMPID_"+cloumeNum1+"' name='EMPID' onkeydown='F_HR_SubmitKeyClick1("+cloumeNum1+",event)' size='10'/><input type='hidden' id='empid*"+cloumeNum1+"' value=''/>";
	var td3=tr.insertCell(3);
		td3.className="td_left";
		td3.innerHTML="<input type='hidden' value='' id='LOCAL_NAME_"+cloumeNum1+"' name='LOCAL_NAME'><div id='LOCAL_NAME_"+cloumeNum1+"_div'  style='display: none'></div>";
	var td4=tr.insertCell(4);
		td4.className="td_left";
		td4.innerHTML="<input type='hidden' value='' id='POSITION_NO_"+cloumeNum1+"' name=''><div id='POSITION_NO_"+cloumeNum1+"_div'  style='display: none'></div>";
	var td5=tr.insertCell(5);
		td5.className="td_left";
		td5.innerHTML="<input type='hidden' value='' id='' name=''><div id='_div'  style='display: none'></div>";

	var td6=tr.insertCell(6);
		td6.className="td_center";
		//td6.innerHTML = "<input type='text' id='START_DATE_"+cloumeNum1+"' name='START_DATE' class='date' readonly='true' format='yyyy-MM-dd' yearstart='-50' yearend='5' onClick='setdate(this);'/>";
	var td7=tr.insertCell(7);
		td7.className="td_center";
		//td7.innerHTML="<input type='text' name='seach_TO_TIME' class='date' format='yyyy-MM-dd' readonly='true' value='${TO_TIME}'/>";
	var td8=tr.insertCell(8);
		td8.className="td_left";
		//td8.innerHTML=" <input type='text' id='' name=''  value='' min='0' max='23' size='4'>: <input type='text' id='' name=''  value='' min='0' max='59' size='4'>	";
	var td9=tr.insertCell(9);
		td9.className="td_left";
		//td9.innerHTML=" <input type='text' id='' name=''  value='' min='0' max='23' size='4'>: <input type='text' id='' name=''  value='' min='0' max='59' size='4'>	";
	var td10=tr.insertCell(10);
		td10.className="td_left";
		var selectHtml="";
		selectHtml="<select id='otApplyTypeCode_"+cloumeNum1+"' name='otApplyTypeCode'>";
		selectHtml+="<option value='0'>请选择</option>";
		<c:forEach items="${codeList}" var="item" >
			 selectHtml+="<option value='${item.CODE_NO}'>${item.CODE_NAME}</option>";
		</c:forEach>	
		selectHtml+='</select>';
	   	//td10.innerHTML=selectHtml;
	var td11=tr.insertCell(11);
		td11.className="td_center";
		//td11.innerHTML="<textarea id='${person.PERSON_ID}_APPLY_REMARK' name='' cols='40' rows='1'></textarea>";
	var td12=tr.insertCell(12);
		td12.className="td_left";
		//td12.innerHTML="查看详细 ";
	
	
} 
function deleteNewTable(row){
		var table = document.getElementById("tableTitleName_ess0302");
		$(row).parent().parent().remove();
}
//添加新行的工号文本框触发
function F_HR_SubmitKeyClick1(i,event){
		var event = event || window.event;
	   	if(event.keyCode==13)
	   	{
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
	//var table = document.getElementById("tableTitleName_ess0302");
	//var colums = table.rows[0].cells.length;
	//var tr = table.insertRow();
	var cloumeNum1=$("#tableTitleName_ess0302 tr").length;
	//总条数 
	var cloumeCount=$("#cloumeCount").val();
	if(cloumeCount=="0"){
		cloumeCount=2;
	}else{
		cloumeCount++;
	}
	//开始日期tableTitleName_ess0302
	document.getElementById("tableTitleName_ess0302").rows[cloumeNum1-1].childNodes[1].innerHTML = "<input type='checkbox' id='c1' name='c1' value='"+personId+","+cloumeCount+"' />";
	document.getElementById("tableTitleName_ess0302").rows[cloumeNum1-1].childNodes[6].innerHTML = "<input type='text' id='"+personId+"_FROM_DATE_"+cloumeCount+"' name='"+personId+"_FROM_DATE_"+cloumeCount+"' class='date'  readonly='true' format='yyyy-MM-dd' yearstart='-50' yearend='5' onClick='setdate(this);' size='8' />";

	document.getElementById("tableTitleName_ess0302").rows[cloumeNum1-1].childNodes[7].innerHTML="<input type='text'  id='"+personId+"_TO_DATE_"+cloumeCount+"' name='"+personId+"_TO_DATE_"+cloumeCount+"' class='date' format='yyyy-MM-dd' readonly='true' yearstart='-50' yearend='5' onClick='setdate(this);' size='8'/>";	
	document.getElementById("tableTitleName_ess0302").rows[cloumeNum1-1].childNodes[8].innerHTML=" <input type='text' id='"+personId+"_FROM_TIME_HOUR_"+cloumeCount+"' name='"+personId+"_FROM_TIME_HOUR_"+cloumeCount+"'  value='' min='0' max='23' size='4'>: <input type='text' id='"+personId+"_FROM_TIME_MINUTE_"+cloumeCount+"' name='"+personId+"_FROM_TIME_MINUTE_"+cloumeCount+"'  value='' min='0' max='59' size='4'>	";	
	document.getElementById("tableTitleName_ess0302").rows[cloumeNum1-1].childNodes[9].innerHTML=" <input type='text' id='"+personId+"_TO_TIME_HOUR_"+cloumeCount+"' name='"+personId+"_TO_TIME_HOUR_"+cloumeCount+"'  value='' min='0' max='23' size='4'>: <input type='text' id='"+personId+"_TO_TIME_MINUTE_"+cloumeCount+"' name='"+personId+"_TO_TIME_MINUTE_"+cloumeCount+"'  value='' min='0' max='59' size='4'>	";                                                                          
	document.getElementById("tableTitleName_ess0302").rows[cloumeNum1-1].childNodes[10].innerHTML="<input type='hidden' id='"+personId+"_OT_APPLY_TYPE_CODE_"+cloumeCount+"' name='"+personId+"_OT_APPLY_TYPE_CODE_"+cloumeCount+"' value=''/>";
	
	
	document.getElementById("tableTitleName_ess0302").rows[cloumeNum1-1].childNodes[11].innerHTML="<textarea id='"+personId+"_APPLY_REMARK_"+cloumeCount+"' name='"+personId+"_APPLY_REMARK_"+cloumeCount+"' cols='25' rows='1'></textarea>";                                                               
	document.getElementById("tableTitleName_ess0302").rows[cloumeNum1-1].childNodes[12].innerHTML="<a rel='"+personId+"_overtimeApplyBatchAffirmView' onclick='passOvertimeApplyValue("+personId+","+cloumeCount+");'><span style='cursor:pointer;'><spring:message code='ess.infoApply.title.viewDetail'/><!--查看详细--></span></a><a id='"+personId+"_viewOvertimeApplyBatchInfoHref'   href='/ess/infoApply/viewAffirmorByPersonIdAndAppCode?APPLY_TYPE_NO=31&&PERSON_ID="+personId+"' target='dialog' mask='true' width='300' height='300' ></a>";
	var num=i;
	
	var tid="tr_"+num;
	
	$("#"+tid).click(function () {
	
	var id="empid*"+num;
	var personid1=document.getElementById(id).value;
	
	var idDate=personid1+"_FROM_DATE_"+cloumeCount;
   
	var date=document.getElementById(idDate).value;
	
	var selectId=personid1+"_OT_APPLY_TYPE_CODE_"+cloumeCount;//_OT_APPLY_TYPE_CODE
	
	var selectValue=document.getElementById(selectId).value;
	
	//if(date==""){
		//return false;
	//}
	$.ajaxSettings.global=false;
	 $.ajax({
               type: 'POST',
               url:"/ess/infoApply/getDateByPersonIdAndCpny",
               data:'personid=' + personid1+"&time="+date,
               dataType:"json",
               cache: false,
               success: function(data) {
		 				var idDate1=personid1+"_FROM_DATE_"+cloumeCount;
						var date1=document.getElementById(idDate1).value;

						var code=data.CODE;
		 			 	var codeValue="";
		 			 	
		 			 	var code1="";
		 			 		if(code=="1440"){
		 			 		//平日
		 			 		code1="32";
		 			 		codeValue="平日加班";
		 			 	}else if(code=="1441"){
		 			 		//周末
		 			 		code1="33";
		 			 		codeValue="周末加班";
		 			 	}else if(code=="1442"){
		 			 		//节假日
		 			 		code1="34";
		 			 		codeValue="节假日加班";
		 			 	}
						var cpnyId=$("#CPNY_ID").val();
						alert(cpnyId);
						if(cpnyId!="C02"){
							if(date1!=""&&code1=="32"){
			 			  	 document.getElementById("tableTitleName_ess0302").rows[cloumeNum1-1].childNodes[5].innerHTML=data.SHIFT_START_TIME.substring(11)+" "+data.SHIFT_END_TIME.substring(11);
			 			   	}else{
			 				   document.getElementById( "tableTitleName_ess0302").rows[i].cells[5].innerHTML="";
			 			    }
						}
		 			  	 //td5.innerHTML=data.SHIFT_START_TIME.substring(11)+" "+data.SHIFT_END_TIME.substring(11);
		 			 	//结束日期 var idDate=personid1+"_FROM_DATE_"+cloumeCount;
		 			 	var startDateId=personid1+"_FROM_DATE_"+cloumeCount;
		 			 	var endDateId=personid1+"_TO_DATE_"+cloumeCount;
		 			 	var startDateValue=$("#"+startDateId).val();
		 			 	$("#"+endDateId).val(startDateValue);
		 			 	document.getElementById("tableTitleName_ess0302").rows[cloumeNum1-1].childNodes[10].innerHTML=codeValue+"<input type='hidden' id='"+personid1+"_OT_APPLY_TYPE_CODE_"+cloumeCount+"' name='"+personid1+"_OT_APPLY_TYPE_CODE_"+cloumeCount+"' value='"+code1+"'/>";
		 			 	//$("#select_id option[text='34']").attr("selected", true);    
                       //alertMsg.info(data.message);
                       //$(row).parent().parent().remove();
                       //document.getElementById("OrderType").onchange();
                        },
               error: DWZ.ajaxError
   		});
 	});
	$.ajaxSettings.global=true;
	$("#cloumeCount").val(cloumeCount);
}
function getDate(v,i){
	var date=v.value;
	//var id="empid*"+i;
	alert(date);
	return false;
	var personid=document.getElementById(id).value;
	 $.ajax({
               type: 'POST',
               url:"/ess/infoApply/getDateByPersonIdAndCpny",
               data:'personid=' + personid+"&time="+date,
               dataType:"json",
               cache: false,
               success: function(data) {
                       alertMsg.info(data.message);
                       $(row).parent().parent().remove();
                              //document.getElementById("OrderType").onchange();
                        },
               error: DWZ.ajaxError
   });
}
function getDate1(cloumeNum1,personid){
	var id="empid*"+cloumeNum1;
	var idDate=personid+"_FROM_DATE";//_FROM_DATE
	var date=document.getElementById(idDate).value;
	var personid=document.getElementById(id).value;
	var selectId=personid+"_OT_APPLY_TYPE_CODE";//_OT_APPLY_TYPE_CODE
	//var selectValue=document.getElementById(selectId).value;
	
	//if(date!=""&&selectValue!=""){
		//return false;
	//}
	
	if(date==""){
		return false;
	}
	
	$.ajaxSettings.global=false;
	 $.ajax({
               type: 'POST',
               url:"/ess/infoApply/getDateByPersonIdAndCpny",
               data:'personid=' + personid+"&time="+date,
               dataType:"json",
               cache: false,
               success: function(data) {
		 			   var code=data.CODE;
		 			 	var codeValue="";
		 			 	var code1="";
		 			 		if(code=="1440"){
		 			 		//平日
		 			 		code1="32";
		 			 		codeValue="平日加班";
		 			 	}else if(code=="1441"){
		 			 		//周末
		 			 		code1="33";
		 			 		codeValue="周末加班";
		 			 	}else if(code=="1442"){
		 			 		//节假日
		 			 		code1="34";
		 			 		codeValue="节假日加班";
		 			 	}
		 			   if(code1==32){		
		 			   		document.getElementById( "tableTitleName_ess0302").rows[cloumeNum1+1].cells[5].innerHTML=data.SHIFT_START_TIME.substring(11)+" "+data.SHIFT_END_TIME.substring(11); 
		 			    }else{
		 				   document.getElementById( "tableTitleName_ess0302").rows[i].cells[5].innerHTML="";
		 			   }
						//td5.innerHTML=data.SHIFT_START_TIME.substring(11)+" "+data.SHIFT_END_TIME.substring(11);
		 			 	document.getElementById("tableTitleName_ess0302").rows[cloumeNum1+1].cells[10].innerHTML=codeValue+"<input type='hidden' id='"+personid+"_OT_APPLY_TYPE_CODE' name='"+personid+"_OT_APPLY_TYPE_CODE' value='"+code1+"'/>";;
		 			 	//$("#select_id option[text='34']").attr("selected", true);    
                       //alertMsg.info(data.message);
                       //$(row).parent().parent().remove();
                       //document.getElementById("OrderType").onchange();
                        },
               error: DWZ.ajaxError
   		});
 	$.ajaxSettings.global=true;
}
function getDate2(i,personid,date){
	 $.ajax({
               type: 'POST',
               url:"/ess/infoApply/getDateByPersonIdAndCpny",
               data:'personid=' + personid+"&time="+date,
               dataType:"json",
               cache: false,
               success: function(data) {
		 				var code=data.CODE;
		 			 	var codeValue="";
		 			 	var code1="";
		 			 		if(code=="1440"){
		 			 		//平日
		 			 		code1="32";
		 			 		codeValue="平日加班";
		 			 	}else if(code=="1441"){
		 			 		//周末
		 			 		code1="33";
		 			 		codeValue="周末加班";
		 			 	}else if(code=="1442"){
		 			 		//节假日
		 			 		code1="34";
		 			 		codeValue="节假日加班";
		 			 	}
		 			 	var cpnyId=$("#CPNY_ID").val();
		 			 	if(cpnyId!="C02"){
			 			 	if(code1=="32"){	
			 			  		 document.getElementById( "tableTitleName_ess0302").rows[i].cells[5].innerHTML=data.SHIFT_START_TIME.substring(11)+" "+data.SHIFT_END_TIME.substring(11); 
			 			   }else{
			 				   document.getElementById( "tableTitleName_ess0302").rows[i].cells[5].innerHTML="";
			 			   }
			 			 }
		 			 	//结束日期默认是 开始日期
		 			    var startDateId=personid1+"_FROM_DATE_"+i;
		 			 	var endDateId=personid1+"_TO_DATE_"+i;
		 			 	var startDateValue=$("#"+startDateId).val();
		 			 	$("#"+endDateId).val(startDateValue);
		 			   
		 			 	document.getElementById("tableTitleName_ess0302").rows[i].cells[10].innerHTML=codeValue+"<input type='hidden' id='"+personid+"_OT_APPLY_TYPE_CODE_"+i+"' name='"+personid+"_OT_APPLY_TYPE_CODE_"+i+"' value='"+code1+"'/>";;
		 			 	//$("#select_id option[text='34']").attr("selected", true);    
                       //alertMsg.info(data.message);
                       //$(row).parent().parent().remove();
                       //document.getElementById("OrderType").onchange();
                        },
               error: DWZ.ajaxError
   		});
}
//-->
</script>
<input type="hidden" value="${defaultCpny}" id="CPNY_ID"  name="CPNY_ID"/>
 <div class="pageHeader">
	
	<a id="onck" name="onck" href="/hrm/empinfo/viewEmpIdList?pageNum=1" lookupGroup="person" width="950"></a>
	<form onsubmit="return navTabSearch(this);" action="/ess/infoApply/viewOtBatchApplyPersonListNewList" method="post" 
	      rel="pagerForm" id="searchOvertimeApplyBatchForm" name="searchOvertimeApplyBatchForm">
	
	<div class="searchBar">
		<table class="searchContent">
			<tr>			
				<td>
					 <spring:message code="public.title.deptName"/><!-- 部门 -->:
				</td>	
				<td>
					<ait:deptTree name="seach_DEPT_NO" limit="ar" selected="${DEPT_NO }"/>
				</td>
                <td>
                    <%--<spring:message code="ess.infoApply.title.kewWord"/><!--关键字-->:
				--%>
					<spring:message code="public.title.empIdAndName"/><!-- 工号/姓名 -->
				</td>
				<td>
					<input  type="text"  name="seach_KEY" value="${KEY}"  />
				</td>
                <td><%--
                
                        <spring:message code="public.title.startDate"/><!-- 开始日期 -->:--%>
                </td>			
			    <td><%--
			        <input type="text" name="seach_FROM_TIME" class="date" format="yyyy-MM-dd" readonly="true" value="${FROM_TIME}" />
				   
			    --%></td>
                <td>
                        <%--<spring:message code="public.title.endDate"/><!-- 结束日期 -->:
                --%></td>                			     
				<td>
				    <%--<input type="text" name="seach_TO_TIME" class="date" format="yyyy-MM-dd" readonly="true" value="${TO_TIME}"/>
				   
				--%></td> 
				<td>
                      <!-- 动态组 --><spring:message code="ar.addempshift.title.dynamicgroup"/>:
                </td>                			     
				<td>
				  <select name="seach_GROUP_NO" id="seach_GROUP_NO">
						<option value=""><!-- 全部 --><spring:message code="ar.viewarcardrecord.title.quanbu"/></option>
						<c:forEach items="${dynamicGroupList}" var="groupList">
							<option value="${groupList.GROUP_NO}" <c:if test="${groupList.GROUP_NO eq GROUP_NO}">selected</c:if>>${groupList.GROUP_NAME}</option>
						</c:forEach>
					</select>
				</td> 									
			</tr>
			</table>
			<div class="subBar">
				<ul>
					<li>
                            <div class="buttonActive">
                            <div class="buttonContent">
                            <button type="submit">
                                 <spring:message code="public.title.search"/><!-- 检索 -->
                            </button>
                            </div>
                            </div>
					</li>
				</ul>
			</div>
	    </div>
		
	</form>
</div>	

<div class="pageContent" >
	<div class="formBar">
			<ul>
				<li>
					<div class="buttonActive">
						<a class="update" onclick="return validateBatchOvertimeApplyCallback_ess0302('applyOvertimeBatchForm',DWZ.ajaxDone);" href="#" ><span>
						         <spring:message code="ess.infoApply.title.apply"/><!--申请--></span></a>
					</div>
			 	</li>
			 	<li>
			 		<div class="buttonActive">
						<a class="update" onclick="fillItemOt();" href="#" >
						         <span><spring:message code="ess.infoApply.title.fillItem"/><!--填充--></span></a>
				    </div>	
				</li>
			</ul>
	</div>
     <form style="margin:0px;padding:0px;" name="applyOvertimeBatchForm" id="applyOvertimeBatchForm" method="post" action="/ess/infoApply/addBatchOvertimeApply" 
           class="pageForm required-validate" onsubmit="return validateBatchOvertimeApplyCallback(this,navTabAjaxDone);">  
	 <a href="/ess/infoApply/viewAffirmorByPersonIdAndAppCode?test=1"  
					       target="dialog" mask="true" width="300" height="300" id="123456_viewOvertimeApplyBatchInfoHref" ></a> 
	<input type="hidden" value="${totalCount}" id="cloumeCount" name="cloumeCount"/>
	<table class="tablea" width="100%" layoutH="183" id="tableTitleName_ess0302">
		<thead>
			<tr>
				<th>
						<img src="/resources/css/ligerUI/skins/icons/add.gif" onclick="addNewTable()"/>
				</th>
			    <th width="40"><input type="checkbox" class="checkboxCtrl" group="c1" /></th>
				<th width="80"><spring:message code="public.title.empId"/><!--工号--></th>
				<th width="80"><spring:message code="public.title.name"/><!--姓名--></th>
				<th width="80"><spring:message code="public.title.positionName"/><!--职岗位--></th>
				<th width="80">班次<!--  班次 --></th>
				<th width="100"><spring:message code="public.title.startDate"/><!-- 开始日期 --></th>			
				<th width="100"><spring:message code="public.title.endDate"/><!-- 结束日期 --></th>
				<th width="100"><spring:message code="ess.infoApply.title.startTime"/></th>
				<th width="100"><spring:message code="ess.infoApply.title.endTime"/><!--结束时间--></th>
				<th width="100"><spring:message code="ess.viewApply.title.overtimeApplyType"/><!--加班类型--></th>
				<%--<th width="100"><spring:message code="ess.infoApply.title.forcedTypeChoice"/><!--强制类型选择-->
				</th>
				--%>
				<th width="100" ><spring:message code="ess.infoApply.title.workContent"/><!--工作内容--></th>
				<th width="100"><spring:message code="ess.infoApply.title.affirmor"/><!--决裁者--></th>				
			</tr>
		</thead>
        <input id="APPLY_TYPE_NO" name="APPLY_TYPE_NO" type="hidden" size="30" value="31" />	
		    <tr>
			    <td width="40" colspan="6"><spring:message code="ess.infoApply.title.fillItemIntroduction"/><!--点击填充按钮将按此行数据对选中的行数据进行填充-->
			    	&nbsp;&nbsp;<input type="checkbox" id="continueApply" name="continueApply" value="1"/>&nbsp;&nbsp;<font color="red">连续申请</font>
			    </td>
				<td width="120">
				    <input type="text" id="otFromDate" name="otFromDate" class="date" format="yyyy-MM-dd" readonly="true" size="8"/>
				    
                </td>
				<td >
				    <input type="text" id="otToDate" name="otToDate" class="date" format="yyyy-MM-dd" readonly="true" size="8"/>
				   
                </td>
				<td width="180">
			        <input type="text" id="otFromTimeHour" name="fromTimeHour" min="0" max="23" size="4">:
			        <input type="text" id="otFromTimeMinute" name="fromTimeMinute"  min="0" max="59" size="4">	
			    </td>
			  
				<td width="180">
				    <input type="text" id="otToTimeHour"   name="toTimeHour"   min="0" max="23" size="4">:
				    <input type="text" id="otToTimeMinute" name="toTimeMinute" min="0" max="59" size="4">					
			    </td>	
			    <td>
			    </td>                                 
				<td width="100" align="right">
					<textarea id="otApplyRemark" name="otApplyRemark" cols="25" rows="1"></textarea>
				<%--
				    <ait:SelectSyCodeByCpnyID parentNo="31" cnpyID="${defaultCpny}" name="otApplyTypeCode" limit="all"/>		    
				--%></td>
				
				<td width="80" colspan="2"><%--
				   <textarea id="otApplyRemark" name="otApplyRemark" cols="40" rows="1"></textarea>
				--%></td>							
			</tr>		        
		<tbody>		 
			<c:forEach items="${personList}" var="person" varStatus="i" >			
				<tr target="sid" rel="${person.PERSON_ID}" onclick="getDate1(${i.count},${person.PERSON_ID})">
					<td>
						<img src='/resources/css/ligerUI/skins/icons/delete.gif' onclick='deleteNewTable(this)' />
					</td>
				    <td>
				        <input type="checkbox" id="c1" name="c1" value="${person.PERSON_ID},${i.count}" />
				       
				    </td>
					<td>${person.EMPID}
						<input type="hidden" value="${person.PERSON_ID}" id="empid*${i.count}" name=""/>
					</td>
					<td>${person.LOCAL_NAME}</td>
				
					<td>${person.POSITION_NAME}</td>
						<td></td>
					<td>
					    <input type="text" id="${person.PERSON_ID}_FROM_DATE" name="${person.PERSON_ID}_FROM_DATE_${i.count}" class="date" format="yyyy-MM-dd" readonly="true" size="8"/>
				       
				    </td>
					<td>
					    <input type="text" id="${person.PERSON_ID}_TO_DATE" name="${person.PERSON_ID}_TO_DATE_${i.count}" class="date" format="yyyy-MM-dd" readonly="true" size="8"/>
				       
				    </td>
					<td>
				        <input type="text" id="${person.PERSON_ID}_FROM_TIME_HOUR" name="${person.PERSON_ID}_FROM_TIME_HOUR_${i.count}"  
				               value="${FROM_TIME_HOUR}" min="0" max="23" size="4">:
				        <input type="text" id="${person.PERSON_ID}_FROM_TIME_MINUTE" name="${person.PERSON_ID}_FROM_TIME_MINUTE_${i.count}"  
				               value="${FROM_TIME_MINUTE}" min="0" max="59" size="4">	
				    </td>
					<td width="80">
					    <input type="text" id="${person.PERSON_ID}_TO_TIME_HOUR"   name="${person.PERSON_ID}_TO_TIME_HOUR_${i.count}"   
					           value="${TO_TIME_HOUR}"  min="0" max="23" size="4">:
					    <input type="text" id="${person.PERSON_ID}_TO_TIME_MINUTE" name="${person.PERSON_ID}_TO_TIME_MINUTE_${i.count}" 
					           value="${TO_TIME_MINUTE}" min="0" max="59" size="4">					
				    </td>				    
					<td>
					    <%--<ait:SelectSyCodeByCpnyID parentNo="31" cnpyID="${defaultCpny}" name="${person.PERSON_ID}_OT_APPLY_TYPE_CODE" selected="${OT_APPLY_TYPE_CODE}" limit="all"/>
					--%></td>
					<%--<td>
					    <select name="${person.PERSON_ID}_ifForcedTypeChoice">
							<option value="0" selected="true"><spring:message code="sys.affirm.title.no"/><!--否--></option>
							<option value="1"><spring:message code="sys.affirm.title.yes"/><!--是--></option>
						</select>	
					</td>
					--%><td><textarea id="${person.PERSON_ID}_APPLY_REMARK" name="${person.PERSON_ID}_APPLY_REMARK_${i.count}" cols="25" rows="1"></textarea></td>
					<td>
                        <a rel="${person.PERSON_ID}_overtimeApplyBatchAffirmView" onclick="passOvertimeApplyValue(${person.PERSON_ID},${i.count});">
						   <span style="cursor:pointer;"><spring:message code="ess.infoApply.title.viewDetail"/><!--查看详细--></span>
						</a>
					    <a href="/ess/infoApply/viewAffirmorByPersonIdAndAppCode?APPLY_TYPE_NO=31&&PERSON_ID=${person.PERSON_ID}"  
					       target="dialog" mask="true" width="300" height="300" id="123456_viewOvertimeApplyBatchInfoHref" ></a> 
	                </td>					
				</tr>			
			</c:forEach>			
		</tbody>
	</table>
	<div id="overtimeApplyBatchAffirmView" style="border:10;overflow:auto;bottom:10;right:100;width:300;z-index:1;"></div>	
	</form>
	<c:set value="/ess/infoApply/viewOtBatchApplyPersonListNewList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>	
</div>
