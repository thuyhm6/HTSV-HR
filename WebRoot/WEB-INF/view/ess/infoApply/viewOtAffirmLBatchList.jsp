<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.easydrag.js"></script>
<script type="text/javascript">
<!--
function submitLFormPre(flag){
	$("#AFFIRM_FLAG").val(flag);
  	var $from = $("#applyOvertimeBatchFormL");
  	$from.submit();
}

function validateBatchOvertimeApplyCallback_L(form,callback) {	
	var $form = $("#applyOvertimeBatchFormL");
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
		  	if($form.find("[name='"+personId+"_APPLY_OT_DATE_"+k+"']").val()==''){
				alertMsg.error("加班日期为必选项，请选择加班日期！");
				$form.find("[name='"+personId+"_APPLY_OT_DATE_"+k+"']").focus();
				checked=false;
				return false;
		   	}  
		  	if($form.find("[name='"+personId+"_OT_APPLY_HOUR_"+k+"']").val()==''){
		  		alertMsg.error("加班时长不得低于一小时，请选择小时数！");
				$form.find("[name='"+personId+"_OT_APPLY_HOUR_"+k+"']").focus();
				checked=false;
				return false;
		   	}
          	if($form.find("[name='"+personId+"_OT_APPLY_TYPE_CODE_"+k+"']").val()==''){
				alertMsg.error("加班类型为必选项，请选择加班类型！");
				$form.find("[name='"+personId+"_OT_APPLY_TYPE_CODE_"+k+"']").focus();
				checked=false;
				return false;
		   	}		   
          	if($form.find("select[name='"+personId+"_OT_PLACE_TYPE_"+k+"']").val()==''){
				alertMsg.error("社内/外加班为必选项，请选择！");
				$form.find("select[name='"+personId+"_OT_PLACE_TYPE_"+k+"']").focus();
				checked=false;
				return false;
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
					navTabSearch("searchOvertimeApplyBatchFormL");
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

function passLOvertimeApplyValue(personId,i){   
    var applyOtDate   = document.getElementById(personId+"__APPLY_OT_DATE_"+i).value;
     if(fromDate==""){
       alertMsg.error('<spring:message code="alert.message.ess.infoApply.startDateTimeIsMust"/>');
       return false;
	}
    var applyTypeNo   = document.getElementById("APPLY_TYPE_NO").value;
    var otApplyHour   = document.getElementById(personId+"_OT_APPLY_HOUR_"+i+"").value;
    var otApplyMinute = document.getElementById(personId+"_OT_APPLY_MINUTE_"+i+"").value;
    var applyTypeCode = document.getElementById(personId+"_OT_APPLY_TYPE_CODE_"+i+"").value;
    
    if(applyOtDate==""){
       alertMsg.error("加班日期为必选项，请选择加班日期！");
       return false;
	}
    
	
	if(otApplyHour==""){
       alertMsg.error("加班时长不得低于一小时，请选择小时数！");
       return false;
	}
	
	if(applyTypeCode==""){
       alertMsg.error('<spring:message code="alert.message.ess.infoApply.overtimeApplyTypeIsMust"/>');
       return false;
	}

    document.getElementById("viewOvertimeApplyBatchLHref").href = 
    document.getElementById("viewOvertimeApplyBatchLHref").href+
    						"APPLY_TYPE_NO=31&&PERSON_ID="+personId+
                            "&APPLY_TYPE_CODE="+applyTypeCode+"&APPLY_TYPE_NO="+applyTypeNo+
                            "&OT_TIME_TYPE='L'"+"&APPLY_OT_DATE="+applyOtDate+
                            "&OT_APPLY_HOUR="+otApplyHour+
                            "&OT_APPLY_MINUTE="+otApplyMinute;
    
    var hid="viewOvertimeApplyBatchLHref";
    $("#"+hid).click();
}

function fillItemOtL(){
	var $form = $("#applyOvertimeBatchFormL");
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
 	var otApplyDate=document.getElementById("otApplyDate").value;
 	var otApplyHour=document.getElementById("otApplyHour").value;    
 	 var OT_APPLY_HOUR_2   = document.getElementById("otApplyHour_2").value;
 	 
	   if(otApplyHour==null||otApplyHour==""){
		   otApplyHour=OT_APPLY_HOUR_2;
	     
	       }  
 	 
  	var otApplyMinute=document.getElementById("otApplyMinute").value;      
  	var otApplyTypeCode=document.getElementById("otApplyTypeCode").value;  
 	var otAdjustYn=document.getElementById("adjustYn").value;  
 	  
  	var otPlaceType=document.getElementById("otPlaceType").value;   
  	var otApplyReason =document.getElementById("otApplyRemark").value;
 	      
  	var $form = $("#applyOvertimeBatchFormL");
  	//$form.find("[name='"+personId+"_APPLY_OT_DATE_']")
  	var cloumeCount =document.getElementById("cloumeCount").value;
  	$form.find(":checkbox[id='c1']").each(function(index, checkBoxObj){
  		if(checkBoxObj.checked){
      		var personId = $(checkBoxObj).val().split(",")[0] ;
      	 	var i=parseInt(index)+2;
      	 	var k=$(checkBoxObj).val().split(",")[1] ;
      		
      	 	$form.find("[name='"+personId+"_APPLY_OT_DATE_"+k+"']").attr("value",otApplyDate);
      	 	$form.find("select[name='"+personId+"_OT_APPLY_HOUR_"+k+"']").attr("value",otApplyHour);
         	$form.find("select[name='"+personId+"_OT_APPLY_MINUTE_"+k+"']").attr("value",otApplyMinute);
         	$form.find("select[name='"+personId+"_OT_APPLY_TYPE_CODE_"+k+"']").attr("value",otApplyTypeCode);
         	if(otApplyTypeCode == '33'){
         		$form.find("select[name='"+personId+"_ADJUST_YN_"+k+"']").attr("value",otAdjustYn);
            }else{
            	$form.find("select[name='"+personId+"_ADJUST_YN_"+k+"']").attr("disabled",'true');
            	$form.find("select[name='"+personId+"_ADJUST_YN_"+k+"']").attr("value",'0');
            }
         	$form.find("select[name='"+personId+"_OT_PLACE_TYPE_"+k+"']").attr("value",otPlaceType);
         	$form.find("[name='"+personId+"_APPLY_REMARK_"+k+"']").attr("value",otApplyReason);
         	
         	//getDate2(i,personId,otFromDate);
  		}
  	});
}

//-->
</script>

<script type="text/javascript">
//<!--
//获取该日期的加班类型
function getLOtApplyTypeB(){
 
	var $form = $("#applyOvertimeBatchFormL");
	var fromDate = $("#otApplyDate",navTab.getCurrentPanel()).val();
	//var applyTypeCode = document.getElementById("otApplyTypeCode");
  	//var adjustYn = document.getElementById("adjustYn");
		var otApplyMinute2 = document.getElementById("otApplyMinute");
	    var hour8 = document.getElementById("hour8");
	    var hour2 = document.getElementById("hour2");
	    var otApplyHour   = document.getElementById("otApplyHour").value;
	    var OT_APPLY_HOUR_2   = document.getElementById("otApplyHour_2").value;
 
	   if(otApplyHour==null||otApplyHour==""){
	    	otApplyHour=OT_APPLY_HOUR_2;
	     
	       }
	if(fromDate!=""){
		$.ajax({
			cache: false,
			type: 'post',
			async:false,
			url: "/ess/infoApply/getDateTypeByDateAndCpny?",
			data:'DDATE_STR=' + fromDate,
			dataType:"json",
			success: function(data) {
				var dateType = data.TYPEID;
				if(dateType == '1440'){  
					$("#hour8").hide();
					$("#hour2").show();
					 $form.find("select[name='otApplyTypeCode']").attr("value","32");
					$form.find("select[name='adjustYn']").attr("value",'0');
			    	$form.find("select[name='adjustYn']").attr("disabled",true);
				}else if(dateType == '1441'){
					$("#hour8").show();
					$("#hour2").hide();
					 
					$form.find("select[name='otApplyTypeCode']").attr("value","33");
					//$form.find("select[name='adjustYn']").attr("value",'0');
			    	$form.find("select[name='adjustYn']").attr("disabled",false);
				}else if(dateType == '1442'){
					$("#hour8").show();
					$("#hour2").hide();
					 
					$form.find("select[name='otApplyTypeCode']").attr("value","34");
					$form.find("select[name='adjustYn']").attr("value",'0');
			    	$form.find("select[name='adjustYn']").attr("disabled",true);
			    	$form.find("select[name='otApplyTypeCode']").attr("disabled",true);
				}else{
					$("#hour8").show();
					$("#hour2").hide();
					 
			    	$form.find("select[name='otApplyTypeCode']").attr("value","32");
			    	$form.find("select[name='otApplyTypeCode']").attr("disabled",true);
					$form.find("select[name='adjustYn']").attr("value",'0');
			    	$form.find("select[name='adjustYn']").attr("disabled",true);
				}
				 
				if((dateType=='1440' && otApplyHour==2)||(dateType=='1441'&&otApplyHour==8)){
					otApplyMinute2.value=0;
					 
					$("#otApplyMinute").val(0);
					otApplyMinute2.disabled=true;
				}else{
				 
					otApplyMinute2.disabled=false;
					$('otApplyMinute').attr("disabled",false);

				}
				$form.find("select[name='otApplyTypeCode']").attr("disabled",true);
			}
		});
	}
	//setTimeout("getLOtApplyTypeB()",10000);
}

//获取该日期的加班类型
function getLOtApplyTypeBatch(personId,i){
	var $form = $("#applyOvertimeBatchFormL");
	var fromDate = $("#applyOvertimeBatchFormL input[id='"+personId+'_APPLY_OT_DATE_'+i+"']").val();
	
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
				if(dateType == '1440'){  
					$form.find("select[name='"+personId+"_OT_APPLY_TYPE_CODE_"+i+"']").attr("value","32");
					$form.find("select[name='"+personId+"_ADJUST_YN_"+i+"']").attr("value",'0');
			    	$form.find("select[name='"+personId+"_ADJUST_YN_"+i+"']").attr("disabled",true);
				}else if(dateType == '1441'){
					$form.find("select[name='"+personId+"_OT_APPLY_TYPE_CODE_"+i+"']").attr("value","33");
					//$form.find("select[name='"+personId+"_ADJUST_YN_"+i+"']").attr("value",'0');
					$form.find("select[name='"+personId+"_ADJUST_YN_"+i+"']").attr("disabled",false);
				}else if(dateType == '1442'){
					$form.find("select[name='"+personId+"_OT_APPLY_TYPE_CODE_"+i+"']").attr("value","34");
					$form.find("select[name='"+personId+"_ADJUST_YN_"+i+"']").attr("value",'0');
					$form.find("select[name='"+personId+"_ADJUST_YN_"+i+"']").attr("disabled",true);
				}else{
			    	$form.find("select[name='"+personId+"_OT_APPLY_TYPE_CODE_"+i+"']").attr("value","32");
			    	$form.find("select[name='"+personId+"_ADJUST_YN_"+i+"']").attr("value",'0');
			    	$form.find("select[name='"+personId+"_ADJUST_YN_"+i+"']").attr("disabled",true);
				}
			}
		});
	}
	//setTimeout("getLOtApplyTypeBatch("+person_id+","+i+")",1000);
}
//-->
</script>



<script type="text/javascript">
<!--
function addNewTableL() {
	var table = document.getElementById("tableTitleName_L");
	var colums = table.rows[0].cells.length;
	var cloumeNum1=$("#tableTitleName_L tr").length;
	var tr = table.insertRow();
	tr.id="tr_"+cloumeNum1;
	var $form = $("#applyOvertimeBatchFormL"); //$form.find("[name='EMPID']")
	var bool=true;
  	for(var i=2;i<cloumeNum1;i++){
  	   if(document.getElementById("tableTitleName_L").rows[i].cells[4]==undefined){
  		   bool=true;
  		   break;
  	   }	
	   var v=document.getElementById("tableTitleName_L").rows[i].cells[4].TEXT;
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
		td1.innerHTML="<img src='/resources/css/ligerUI/skins/icons/delete.gif' onclick='deleteNewTableL(this)' /><input type='hidden' name='cloumeNumValue' value=''/>";
	var td0 = tr.insertCell(1);  
		td0.className="td_left";
	var td2=tr.insertCell(2);
		td2.className="";
		td2.innerHTML="<input type='text' id='EMPID_"+cloumeNum1+"' name='EMPID' onkeydown='F_HR_SubmitKeyClickL("+cloumeNum1+",event)' size='10'/><input type='hidden' id='empid*"+cloumeNum1+"' value=''/>";
	var td3=tr.insertCell(3);
		td3.className="td_left";
		td3.innerHTML="<input type='hidden' value='' id='LOCAL_NAME_"+cloumeNum1+"' name='LOCAL_NAME'><div id='LOCAL_NAME_"+cloumeNum1+"_div'  style='display: none'></div>";
	var td4=tr.insertCell(4);
		td4.className="td_left";
		td4.innerHTML="<input type='hidden' value='' id='POSITION_NO_"+cloumeNum1+"' name=''><div id='POSITION_NO_"+cloumeNum1+"_div'  style='display: none'></div>";
	
	var td5=tr.insertCell(5);
		td5.className="td_center";
	var td6=tr.insertCell(6);
		td6.className="td_left";
	var td7=tr.insertCell(7);
		td7.className="td_left";
	var td8=tr.insertCell(8);
		td8.className="td_left";
	var td9=tr.insertCell(9);
		td9.className="td_left";
	var td10=tr.insertCell(10);
		td10.className="td_left";
	var td11=tr.insertCell(11);
		td11.className="td_left";
	
} 
function deleteNewTableL(row){
	var table = document.getElementById("tableTitleName_L");
	$(row).parent().parent().remove();
}
//添加新行的工号文本框触发
function F_HR_SubmitKeyClickL(i,event){
	var event = event || window.event;
   	if(event.keyCode==13){
   		var emp= document.getElementById("EMPID_"+i).value;
		//15119设置默认查找在职员工
		document.getElementById("onck").href=encodeURI(encodeURI("/hrm/transferOrder/viewEmpIdList?pageNum=1&seach_EMPID="+emp+'&seach_LOCAL_NAME='
				+emp+'&seach_IDCARD_NO='+emp+'&seach_EMP_OFFICE=15119&empid='+i+'&viewEmpIdListColnum='+i+"&seach_NAVID=ess" ));
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
	//var table = document.getElementById("tableTitleName_L");
	//var colums = table.rows[0].cells.length;
	//var tr = table.insertRow();
	var cloumeNum1=$("#tableTitleName_L tr").length;
	//总条数 
	var cloumeCount=$("#cloumeCount").val();
	if(cloumeCount=="0"){
		cloumeCount=2;
	}else{
		cloumeCount++;
	}
	//开始日期tableTitleName_L
	document.getElementById("tableTitleName_L").rows[cloumeNum1-1].childNodes[1].innerHTML = 
		" <input type='checkbox' id='c1' name='c1' value='"+personId+","+cloumeCount+"' />";
	
	document.getElementById("tableTitleName_L").rows[cloumeNum1-1].childNodes[5].innerHTML =
		" <input type='text' id='"+personId+"_APPLY_OT_DATE_"+cloumeCount+"' name='"+personId+"_APPLY_OT_DATE_"+cloumeCount+"' class='date' "
		+"	readonly='true' format='yyyy-MM-dd' yearstart='-50' yearend='5' onClick='setdate(this);' size='10' "
		+"  onpropertychange='getLOtApplyTypeBatch("+personId+","+cloumeCount+");'/>";
		
 	document.getElementById("tableTitleName_L").rows[cloumeNum1-1].childNodes[6].innerHTML=
		" <select id='"+personId+"_OT_APPLY_HOUR_"+cloumeCount+"' name='"+personId+"_OT_APPLY_HOUR_"+cloumeCount+"'> "
		+"     <option value=''> "
		+"         <spring:message code='sys.affirm.title.choose'/> "
		+"     </option> "
		+"     <c:forEach var='h' begin='1' end='8' step='1'> "
		+"         <option value='${h}'> "
		+"             ${h}小时  "
		+"         </option> "
		+"     </c:forEach> "
		+" </select> "
		+" <select id='"+personId+"_OT_APPLY_MINUTE_"+cloumeCount+"' name='"+personId+"_OT_APPLY_MINUTE_"+cloumeCount+"'> "
		+"     <option value=''> "
		+"     		<spring:message code='sys.affirm.title.choose'/> "
		+"     </option> "
		+"     <c:forEach var='m' begin='0' end='59' step='30'> "
		+"     		<option value='${m}'> "
		+"     			${m}分 "
		+"     		</option> "
		+"     </c:forEach> "
		+" </select> ";
	document.getElementById("tableTitleName_L").rows[cloumeNum1-1].childNodes[7].innerHTML=
		" <ait:SelectSyCodeByCpnyID parentNo='31' cnpyID='${defaultCpny}' name='"+personId+"_OT_APPLY_TYPE_CODE_"+cloumeCount+"' selected='' limit='all'/>";			
	document.getElementById("tableTitleName_L").rows[cloumeNum1-1].childNodes[8].innerHTML=
		" <select id='"+personId+"_ADJUST_YN_"+cloumeCount+"' name='"+personId+"_ADJUST_YN_"+cloumeCount+"'>"
		+"   <option value='0'>否</option> "
		+"   <option value='1'>是</option> "
		+" </select> ";
	document.getElementById("tableTitleName_L").rows[cloumeNum1-1].childNodes[9].innerHTML=
		" <select id='"+personId+"_OT_PLACE_TYPE_"+cloumeCount+"' name='"+personId+"_OT_PLACE_TYPE_"+cloumeCount+"'>"
		+"   <option value='INSIDE'>社内</option> "
		+"   <option value='OUTSIDE'>社外</option> "
		+" </select> ";
	document.getElementById("tableTitleName_L").rows[cloumeNum1-1].childNodes[10].innerHTML=
		"<textarea id='"+personId+"_APPLY_REMARK_"+cloumeCount+"' name='"+personId+"_APPLY_REMARK_"+cloumeCount+"' cols='25' rows='1'></textarea>";                                                               
	document.getElementById("tableTitleName_L").rows[cloumeNum1-1].childNodes[11].innerHTML=
		" <a rel='"+personId+"_overtimeApplyBatchLAffirmView' onclick='passLOvertimeApplyValue("+personId+","+cloumeCount+");'>"
		+" <span style='cursor:pointer;'><spring:message code='ess.infoApply.title.viewDetail'/><!--查看详细--></span></a>"
		+" <a id='"+personId+"_viewOvertimeApplyBatchLHref'   href='/ess/infoApply/viewAffirmorByPersonIdAndAppCode?APPLY_TYPE_NO=31&&PERSON_ID="+personId
		+"' target='dialog' mask='true' width='300' height='300' ></a>";
  	
	$.ajaxSettings.global=true;
	$("#cloumeCount").val(cloumeCount);
}
//-->
</script>



<input type="hidden" value="${defaultCpny}" id="CPNY_ID"  name="CPNY_ID"/>
<div class="pageHeader">
	<a id="onck" name="onck" href="/hrm/empinfo/viewEmpIdList?pageNum=1" lookupGroup="person" width="950"></a>
  	<form onsubmit="return navTabSearch(this);" action="/ess/infoApply/viewOtAffirmLBatchList" method="post" 
    	rel="pagerForm" id="searchOvertimeApplyBatchFormL" name="searchOvertimeApplyBatchFormL">
    	<div class="searchBar">
			<table class="searchContent">
		        <tr>
		        	<td><!-- 部门 -->
						 <spring:message code="public.title.deptName"/>：
					</td>	
					<td>
 <ait:deptList name="seach_DEPT_NO" cpnyId="${defaultCpny}" id="viewApplyOtLInfoList_seachDept"/>
						<ait:deptTreeIcon name="seach_DEPT_NO" cpnyId="${defaultCpny}"   limit="hr" id="viewApplyOtLInfoList_seachDept" selected="${DEPTNO}"/>
					</td>
	                <td><!-- 工号/姓名 -->
						<spring:message code="public.title.empIdAndName"/>：
					</td>
					<td>
						<input type="text"  name="seach_KEY" value="${KEY}"/>
					</td>
					<td><!-- 动态组 -->
                      <spring:message code="ar.addempshift.title.dynamicgroup"/>：
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
				<tr>
				   <td>人员类型组： </td>
						<td>
						
								<ait:SelectEmpTypeCode  id="ess0240222_1_seach_JobTypeGroupNo"  name="seach_JobTypeGroupNo" selected="${JobTypeGroupNo}" limit="ar" type="group"/>
					 
						</td>
					<td>在职状态：</td>
						<td>
		 <ait:SelectSyCodeByCpnyID id="seach_EmpOffice" name="seach_EmpOffice" parentNo="15118" selected="${EmpOffice}" cnpyID="${LoginUser.cpnyId}" limit="all"/>
						</td>
				</tr>
			</table>
			<div class="subBar">
<ul>
	<li>
	<div class="buttonActive">
	<div class="buttonContent">
	<button type="submit"><spring:message
		code="public.title.search" /><!-- 检索 --></button>
	</div>
	</div>
	</li>
</ul>
</div>
			
    	</div>
  	</form>
	
</div>  

<div class="pageContent">
	<form style="margin:0px;padding:0px;" name="applyOvertimeBatchFormL" id="applyOvertimeBatchFormL" method="post" action="/ess/infoApply/addLBatchOtApply" 
       	class="pageForm required-validate" onsubmit="return validateBatchOvertimeApplyCallback_L(this,navTabAjaxDone);">  
 		<a href="/ess/infoApply/viewAffirmorByPersonIdAndAppCode?test=1"  
			target="dialog" mask="true" width="300" height="300" id="viewOvertimeApplyBatchLHref" ></a>
		<input id="APPLY_TYPE" name="APPLY_TYPE" type="hidden" value="BATCH"/>
		<input id="AFFIRM_FLAG" name="AFFIRM_FLAG" type="hidden" value="" />
		<input type="hidden" id="OT_TIME_TYPE" name="OT_TIME_TYPE" value="L"/>
		<div class="formBar">
			<ul class="toolBar">
				<li>
					<div class="buttonActive">
						<div class="buttonContent"><!--点击填充-->
							<button type="button" onclick="fillItemOtL();">
								点击填充
							</button>
						</div>
					</div>
				</li>
				<li>
					<div class="buttonActive">
						<div class="buttonContent"><!--保存-->
							<button type="button" onclick="submitLFormPre(-1);">
								保存
							</button>
						</div>
					</div>
				</li>
				<li>
					<div class="button">
						<div class="buttonContent"><!--提交-->
							<button type="button" onclick="submitLFormPre(0);">
								提交
							</button>
						</div>
					</div>
				</li>
				 
			 
	      	</ul>
	</div>
		<table class="tablea" width="100%" layoutH="183" id="tableTitleName_L">
			<input type="hidden" value="${totalCount}" id="cloumeCount" name="cloumeCount"/>
			<thead>
				<tr> 
					<th width="10">
						<img src="/resources/css/ligerUI/skins/icons/add.gif" onclick="addNewTableL()"/>
					</th>
				    <th width="20">
				    	<input type="checkbox" class="checkboxCtrl" group="c1" />
				    </th>
					<th width="40"><!--工号-->
						<spring:message code="public.title.empId"/>
					</th>
					<th width="40"><!--姓名-->
						<spring:message code="public.title.name"/>
					</th>
					<th width="40">部门</th>
					
					<th width="80"><!--加班日期-->
						加班日期
					</th>
					
					<th width="120">加班时长</th>
					
					<th width="60"><!--加班类型-->
						<spring:message code="ess.viewApply.title.overtimeApplyType"/>
					</th>
					<th width="40">是否调休</th>
					<th width="40">社内/外</th>
					<th width="50"><!--工作内容-->
						<spring:message code="ess.infoApply.title.workContent"/>
					</th>
					<th width="40"><!--决裁者-->
						<spring:message code="ess.infoApply.title.affirmor"/>
					</th>	
				</tr>
			</thead>
        	<input id="APPLY_TYPE_NO" name="APPLY_TYPE_NO" type="hidden" size="30" value="31" />	
		    <tr>
			    <td width="40" colspan="5">
			    	<!--<spring:message code="ess.infoApply.title.fillItemIntroduction"/>点击填充按钮对选中的行进行填充-->
			    	&nbsp;&nbsp;
			    	<!--<input type="checkbox" id="continueApply" name="continueApply" value="1"/>&nbsp;&nbsp;<font color="red">连续申请</font>-->
			    </td>
			     
		    	<td width="60">
		    		<input type="text" id="otApplyDate" name="otApplyDate" class="date" format="yyyy-MM-dd" readonly="true" 
		    			size="10" onpropertychange="getLOtApplyTypeB();"/>
		    	</td>
		    	<td width="120">
		    	
		    		<div id="hour8"  style="float:left"    >
				   <select name="otApplyHour" id="otApplyHour"  onpropertychange="getLOtApplyTypeB();" >
						<option value=""><!--请选择-->
                   			<spring:message code="sys.affirm.title.choose"/>
                   		</option>
						<c:forEach var="h" begin="1" end="8" step="1">
							<option value="${h}" <c:if test="${h eq otApplyHour}">selected</c:if>>
								${h}小时
							</option>
						</c:forEach>
					</select>
					</div>
				  <div id="hour2" style="display:none;float:left">
					 <select name="otApplyHour_2" id="otApplyHour_2"   onpropertychange="getLOtApplyTypeB();" >
						<option value=""><!--请选择-->
                   			<spring:message code="sys.affirm.title.choose"/>
                   		</option>
						<c:forEach var="h" begin="1" end="2" step="1">
							<option value="${h}" <c:if test="${h eq otApplyHour}">selected</c:if>>
								${h}小时
							</option>
						</c:forEach>
					</select>
					</div>
					<select name="otApplyMinute" id="otApplyMinute">
						<option value=""><!--请选择-->
                   			<spring:message code="sys.affirm.title.choose"/>
                   		</option>
						<c:forEach var="m" begin="0" end="59" step="30">
							<option value="${m}" <c:if test="${m eq otApplyMinute}">selected</c:if>>
								${m}分
							</option>
						</c:forEach>
					</select>	   				
			    </td>
		    	
			    <td width="60" align="right">
					<ait:SelectSyCodeByCpnyID parentNo="31" cnpyID="${defaultCpny}" id="otApplyTypeCode" name="otApplyTypeCode" limit="all"/>
				</td>
			    <td>
			    	<select id="adjustYn" name="adjustYn" >
						<option value="0" <c:if test="${adjustYn eq '0' }">selected="selected"</c:if>>
							否
                   		</option>
                   		<option value="1" <c:if test="${adjustYn eq '1' }">selected="selected"</c:if>>
							是
                   		</option>
					</select>
			    </td>
			    <td>
			    	<select id="otPlaceType" name="otPlaceType">
						<option value="INSIDE" <c:if test="${otPlaceType eq 'INSIDE' }">selected="selected"</c:if>>
							社内
                   		</option>
                   		<option value="OUTSIDE" <c:if test="${otPlaceType eq 'OUTSIDE' }">selected="selected"</c:if>>
							社外
                   		</option>
					</select>
			    </td>
				<td width="50">
					<textarea id="otApplyRemark" name="otApplyRemark" cols="25" rows="1"></textarea>
				</td>				
				<td width="40">&nbsp;</td>
			</tr>		        
			<tbody>		 
				<c:forEach items="${personList}" var="person" varStatus="i" >			
					<tr target="sid" rel="${person.PERSON_ID}"  >
						<td>
							<img src='/resources/css/ligerUI/skins/icons/delete.gif' onclick='deleteNewTable(this)' />
						</td>
					    <td>
							<input type="checkbox" id="c1" name="c1" value="${person.PERSON_ID},${i.count}" />
					    </td>
						<td>${person.EMPID}
							<input type="hidden" id="empid*${i.count}" name="" value="${person.PERSON_ID}"/>
						</td>
						<td>${person.LOCAL_NAME}</td>
						<td>${person.POSITION_NAME}</td>
						
						<td>
						    <input type="text" id="${person.PERSON_ID}_APPLY_OT_DATE_${i.count}" name="${person.PERSON_ID}_APPLY_OT_DATE_${i.count}" 
						    	class="date" format="yyyy-MM-dd" readonly="true" size="10" onpropertychange="getLOtApplyTypeBatch(${person.PERSON_ID},${i.count});"/>
					    </td>
					    
					    <td>
						    <select id="${person.PERSON_ID}_OT_APPLY_HOUR_${i.count}" name="${person.PERSON_ID}_OT_APPLY_HOUR_${i.count}">
								<option value=""><!--请选择-->
		                   			<spring:message code="sys.affirm.title.choose"/>
		                   		</option>
								<c:forEach var="h" begin="1" end="8" step="1">
									<option value="${h}" <c:if test="${h eq OT_APPLY_HOUR}">selected</c:if>>
										${h}小时
									</option>
								</c:forEach>
							</select>
							<select id="${person.PERSON_ID}_OT_APPLY_MINUTE_${i.count}" name="${person.PERSON_ID}_OT_APPLY_MINUTE_${i.count}">
								<option value=""><!--请选择-->
		                   			<spring:message code="sys.affirm.title.choose"/>
		                   		</option>
								<c:forEach var="m" begin="0" end="59" step="30">
									<option value="${m}" <c:if test="${m eq OT_APPLY_MINUTE}">selected</c:if>>
										${m}分
									</option>
								</c:forEach>
							</select>
						</td>
					    
						<td>
							<ait:SelectSyCodeByCpnyID parentNo="31" cnpyID="${defaultCpny}" name="${person.PERSON_ID}_OT_APPLY_TYPE_CODE_${i.count}"
						    	selected="${OT_APPLY_TYPE_CODE}" limit="all"/>
						</td>
						<td>
					    	<select id="${person.PERSON_ID}_ADJUST_YN_${i.count}" name="${person.PERSON_ID}_ADJUST_YN_${i.count}" >
								<option value="0" <c:if test="${ADJUST_YN eq '0' }">selected="selected"</c:if>>
									否
		                   		</option>
		                   		<option value="1" <c:if test="${ADJUST_YN eq '1' }">selected="selected"</c:if>>
									是
		                   		</option>
							</select>
					    </td>
					    <td>
					    	<select id="${person.PERSON_ID}_OT_PLACE_TYPE_${i.count}" name="${person.PERSON_ID}_OT_PLACE_TYPE_${i.count}">
								<option value="INSIDE" <c:if test="${OT_PLACE_TYPE eq 'INSIDE' }">selected="selected"</c:if>>
									社内
		                   		</option>
		                   		<option value="OUTSIDE" <c:if test="${OT_PLACE_TYPE eq 'OUTSIDE' }">selected="selected"</c:if>>
									社外
		                   		</option>
							</select>
					    </td>
						<td>
							<textarea id="${person.PERSON_ID}_APPLY_REMARK_${i.count}" name="${person.PERSON_ID}_APPLY_REMARK_${i.count}" 
								cols="25" rows="1"></textarea>
						</td>		
						<td>
							<a rel="${person.PERSON_ID}_overtimeApplyBatchLAffirmView" onclick="passOvertimeApplyValue(${person.PERSON_ID},${i.count});">
							   <span style="cursor:pointer;"><spring:message code="ess.infoApply.title.viewDetail"/><!--查看详细--></span>
							</a>
						    <a href="/ess/infoApply/viewAffirmorByPersonIdAndAppCode?APPLY_TYPE_NO=31&&PERSON_ID=${person.PERSON_ID}"  
						       target="dialog" mask="true" width="300" height="300" id="123456_viewOvertimeApplyBatchLHref" ></a>
					    </td> 	
					</tr>			
				</c:forEach>			
			</tbody>
		</table>
		<div id="overtimeApplyBatchLAffirmView" style="border:10;overflow:auto;bottom:10;right:100;width:300;z-index:1;"></div>	
	</form>
	<c:set value="/ess/infoApply/viewOtAffirmLBatchList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>	
</div>
