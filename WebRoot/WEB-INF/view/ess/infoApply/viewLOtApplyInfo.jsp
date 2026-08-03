<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.easydrag.js"></script>
<script type="text/javascript">
//<!--


var ajaxGet_add_ot_apply_one;
function ajaxAdd_add_ot_apply_one() {
	if (ajaxGet_add_ot_apply_one != null) {
		ajaxGet_add_ot_apply_one.abort();
	}

    var otApplyHour   = $("#OT_APPLY_HOUR",navTab.getCurrentPanel()).val();//document.getElementById("OT_APPLY_HOUR").value;
    
    var OT_APPLY_HOUR_2   =$("#OT_APPLY_HOUR_2",navTab.getCurrentPanel()).val();// document.getElementById("OT_APPLY_HOUR_2").value;

    if(otApplyHour == null||otApplyHour==""){
    	otApplyHour=OT_APPLY_HOUR_2;
        }
    var adjustYn = $("#viewLOtApplyInfo input[name='ADJUST_YN']:checked").val();
    var applytypecode=$("#viewLOtApplyInfo select[id='APPLY_TYPE_CODE']").val();
    var ot_place_type =   $("#viewLOtApplyInfo select[id='OT_PLACE_TYPE']").val();

    var PERSON_ID = $("#PERSON_ID",navTab.getCurrentPanel()).val();
 
 	if(PERSON_ID!= '' && otApplyHour != '' && applytypecode!= ''){
  		
		  
		$.ajaxSettings.global = false;
		 
		ajaxGet_add_ot_apply_one = $.ajax( {
			type : "POST",
			url : "/ess/infoApply/getAffirmList",
			data : {applyParentType : '31',applyType : applytypecode,applyLength : otApplyHour,personId : PERSON_ID,ADJUST_YN:adjustYn,OT_PLACE_TYPE : ot_place_type},
			dataType : "json",
			success : function(data) {
				$('#addApplyLOTAffirm_list tbody').html("");
				var html = "";
				if (typeof (data['affirmList']) != "undefined") {
					$.each(data['affirmList'],
									function(commentIndex, comment) {
										html += '<tr id="rowIdApplyLot' + commentIndex  + '">';
										html += '<td class="td_type" style="text-align: center" width="33%">' + (commentIndex + 1) + '</td>';
										html += '<td class="td_type" style="text-align: center" width="33%">[' + comment['EMPID']
												+ ']-' + comment['LOCAL_NAME'];
										html += '<input type="hidden" name="AFFIRMOR_ID" value="' + comment['AFFIRMOR_ID'] + '"/></td>';
								 
										html += '<td class="td_type" style="text-align: center" width="33%">';
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
		htm +='<input id="dwz.person.LotpersonId'+count+'" name="AFFIRMOR_ID" value="" type="hidden" lookupGroup="person"/>';
		htm +='<input id="dwz.person.LotempName'+count+'" name="empid" value="" type="text" lookupGroup="person"  lookupGroup="person" onkeydown="submitKeyClick_affirmorL(this,' + count + ',event)" class="required"/>';
        htm +='<td class="td_type" style="text-align: center" width="25%">';
		htm +='<input id="dwz.person.InfoLotempName' + count + '"  type="text"  size="40" disabled="disabled"/>';
		htm +='</td>';
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

function addRowByIDApplyLOTFirst(){
	var count = parseInt($("#affirmorListCnt").val());
    var htm  ='<tr id="rowIdApplyLot'+ count +'"><td class="td_type" style="text-align: center" width="5%"><span name="rowIndex"></span></td>';
    htm +='<td class="td_type" style="text-align: center" width="20%">';
    htm +='<input type="radio" id="approvType' + count + '" name="approvType' + count + '" value="1001" checked="checked" />审批 ';
    htm +='<input type="radio" id="approvType' + count + '" name="approvType' + count + '" value="1002" />协议'; 
    htm +='<input type="radio" id="approvType' + count + '" name="approvType' + count + '" value="1003" />通报';
    htm +='</td>';
	htm +='<td class="td_type" style="text-align: center" width="25%">';
	htm +='<input id="dwz.person.LotpersonId'+count+'" name="AFFIRMOR_ID" value="" type="hidden" lookupGroup="person"/>';
	htm +='<input id="dwz.person.LotempName'+count+'" name="empid" value="" type="text" lookupGroup="person"  lookupGroup="person" onkeydown="submitKeyClick_affirmorL(this,' + count + ',event)" class="required"/>';
    htm +='<td class="td_type" style="text-align: center" width="25%">';
	htm +='<input id="dwz.person.InfoLotempName' + count + '"  type="text"  size="40" disabled="disabled"/>';
	htm +='</td>';
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
function addRowByIDL(currentRowID){
    //遍历每一行，找到指定id的行的位置i,然后在该行后添加新行
 
  	var count = $("#affirmorListCnt").val();
  	 
	$.each( $('addApplyLOTAffirm_list:last tbody tr'), function(i, tr){

		 
        if($(this).attr('id')==currentRowID){
            //获取当前行
            var currentRow=$('addApplyLOTAffirm_list:last tbody tr:eq('+i+')');
            //要添加的行的id
            var addRowID=count;
            str = ''
	            +'<tr id = "'+addRowID+'">'
	            	+'<td style="text-align: center">'+addRowID+'</td>'
            		+'<td style="text-align: center">'
						+'<input id="personId'+addRowID+'" name="dwz.person.personId'+addRowID+'" value="" type="hidden" lookupGroup="person"/>'
						+'<input id="empId'+addRowID+'" name="dwz.person.empId'+addRowID+'" value="" type="hidden" lookupGroup="person"/>'
						+'<input id="empName'+addRowID+'" name="dwz.person.empName'+addRowID+'" value="" type="text" lookupGroup="person" '
						+'	onkeydown="submitKeyClick_affirmorL(this,' + count + ',event)" class="required"/>'
            		+'</td>'
            		+'<td style="text-align: center">'
            			+'<c:if test="${affirmorListCnt != 1}">'
            			+'   <img id= "'+addRowID+'" src="/resources/images/+.gif" style="cursor:hand" title="添加" onclick="addRowByIDL('+count+');"/>'
            			+'</c:if>&nbsp;&nbsp;&nbsp;'
            			+'   <img id= "'+addRowID+'" src="/resources/images/-.gif" style="cursor:hand" title="删除" '
            			     //先删除，再排序--上面一种方法Firefox不支持
            			//+'	onclick="javaScript:document.all.addAffirm_listL.deleteRow(event.srcElement.parentElement.parentElement.rowIndex);delRowByIDL();"/>'
            			+'      onclick="deleteRowL(this);"'
					+'</td>'
				+'</tr>';
            //当前行之后插入一行
            currentRow.after(str);
            
        }
    });
	  $("#affirmorListCnt").val(count+1);
   	var tb2 = document.getElementById("addAffirm_listL");
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
function submitLFormPre(flag){
	 $("#viewLOtApplyInfo input[id='AFFIRM_FLAG']").val(flag);
  	var $from = $("#viewLOtApplyInfo");
  	$from.submit();
}
function validateLOvertimeApplyCallback(form,callback) {	

	 
	var $form = $(form);	
	
	var otTimeType  = document.getElementById("OT_TIME_TYPE").value;
    var person_id  = document.getElementById("PERSON_ID").value;
    var applyTypeNo = document.getElementById("APPLY_TYPE_NO").value;
 
    var applyTypeCode = $("#viewLOtApplyInfo select[id='APPLY_TYPE_CODE']").val();
   	var applyOtDate    = document.getElementById("APPLY_OT_DATE").value;
    var otApplyHour   = document.getElementById("OT_APPLY_HOUR").value;
    var otApplyHour2   = document.getElementById("OT_APPLY_HOUR");
    var otApplyHour22   = document.getElementById("OT_APPLY_HOUR_2").value;
    
    if(otApplyHour==null||otApplyHour==""){
    	otApplyHour=otApplyHour22;
     
     	$("#OT_APPLY_HOUR").val(otApplyHour22);
        }
    var applyTypeCode2 = document.getElementById("APPLY_TYPE_CODE");
    var adjustYn2 = document.getElementById("ADJUST_YN_Y");
    var adjustYn = document.getElementById("ADJUST_YN_N");
    adjustYn.disabled=false;
    adjustYn2.disabled=false;
    applyTypeCode2.disabled=false;
    otApplyHour2.disabled=false;
 
    var otApplyMinute = document.getElementById("OT_APPLY_MINUTE").value;
 
  
  
   	
   	if(applyTypeCode==""){
   		adjustYn.disabled=true;
        adjustYn2.disabled=true;
        applyTypeCode2.disabled=true;
        
        alertMsg.error('<spring:message code="alert.message.ess.infoApply.overtimeApplyTypeIsMust"/>');
        return false;
 	}
 	
   	var limitFlag   = document.getElementById("LIMIT_FLAG").value;
	if(limitFlag=='1' && applyTypeCode!='34'){//如果加班人员是被限制的人员，则不允许申请节假日以外的加班申请!
		adjustYn.disabled=true;
	     adjustYn2.disabled=true;
	     applyTypeCode2.disabled=true;
	      
		alertMsg.error("该员工为营业职或促销员，只允许申请法定假加班，请重新选择加班日期!");
		return false;
	}
	
   	if(applyOtDate==""){
   		adjustYn.disabled=true;
        adjustYn2.disabled=true;
        applyTypeCode2.disabled=true;
         
    	alertMsg.error("加班日期不能为空，请选择加班日期！");
    	return false;
    }
    
    if(otApplyHour==""){
    	adjustYn.disabled=true;
        adjustYn2.disabled=true;
        applyTypeCode2.disabled=true;
         
    	alertMsg.error("加班申请长度不得少于一小时，请重新选择！");
    	return false;
    }
    
	//if (confirm ('<spring:message code="alert.message.ess.infoApply.areYouSureToApply"/>')){	          
		$.ajax({
			type: form.method || 'POST',
			url:$form.attr("action"),
			data:$form.serializeArray(),
			dataType:"json",
			cache: false,
			success: callback || DWZ.ajaxDone,
			error: DWZ.ajaxError
		});		
	//}
		adjustYn.disabled=true;
        adjustYn2.disabled=true;
        applyTypeCode2.disabled=true;
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

//获取该日期的加班类型
function getLOtApplyType(){
	var otApplyDate = $("#APPLY_OT_DATE",navTab.getCurrentPanel()).val();
    var opts = document.getElementById("OT_APPLY_HOUR");
    var value ="";  
    var otApplyMinute2 = document.getElementById("OT_APPLY_MINUTE");  
    var hou98 = document.getElementById("hou98");
    var hour92 = document.getElementById("hour92"); 
  
  
 
    var adjustYn = document.getElementById("ADJUST_YN_Y");
    var adjustYn2 = document.getElementById("ADJUST_YN_N");
    var personid = $("#PERSON_ID",navTab.getCurrentPanel()).val();
	if(otApplyDate!=""){
		$.ajax({
			cache: false,
			type: 'post',
			async:false,
			url: "/ess/infoApply/getDateTypeByDateAndEmpCpny?",
			data:'DDATE_STR=' + otApplyDate +'&PERSON_ID='+personid,
			dataType:"json",
			success: function(data) {
	 
				var dateType = data.TYPEID;
				 
				if(dateType == '1440'){       
					$("#APPLY_TYPE_CODE").find("option").eq(1).attr("selected","selected");
					//applyTypeCodeView="32";
					$("#hou98").hide();
					$("#hour92").show();
					
					$("#OT_APPLY_HOUR").val(""); //98 
			    //	value ="2";
					// for(var i=0;i<opts.options.length;i++){
		 			//    if(value==opts.options[i].value){
			       //      opts.options[i].selected = true;
			       //    
			        //     opts.disabled=true;
			        //     otApplyMinute2.disabled=true;
				 	//      break;
			     //   }
			  //   }
			  adjustYn2.checked=true;
				 adjustYn.value="0";
			    	adjustYn2.disabled=true;
			    	adjustYn.disabled=true;
			    	 
				}else if(dateType == '1441'){
					$("#APPLY_TYPE_CODE").find("option").eq(2).attr("selected","selected");
					//applyTypeCodeView="33";
					$("#hou98").show();
					$("#hour92").hide();
					$("#OT_APPLY_HOUR_2").val(""); //92
					adjustYn2.disabled=false;
					 opts.disabled=false;
		             otApplyMinute2.disabled=false;
					adjustYn.disabled=false;
				}else if(dateType == '1442'){
					$("#APPLY_TYPE_CODE").find("option").eq(3).attr("selected","selected");
					//applyTypeCodeView="34";
					adjustYn.value="0";
					$("#hou98").show();
					$("#hour92").hide();
					$("#OT_APPLY_HOUR_2").val(""); //92
					 opts.disabled=false;
		             otApplyMinute2.disabled=false;
					adjustYn2.disabled=true;
					adjustYn.disabled=true;
				}else{
					$("#APPLY_TYPE_CODE").find("option").eq(1).attr("selected","selected");
					//applyTypeCodeView="32";
					$("#hou98").show();
					$("#hour92").hide();
					$("#OT_APPLY_HOUR_2").val(""); //92
			    	adjustYn.value="0";
			    	 opts.disabled=false;
		             otApplyMinute2.disabled=false;
			    	adjustYn2.disabled=true;
			    	adjustYn.disabled=true;
				}
				  var otApplyHour   = document.getElementById("OT_APPLY_HOUR").value;
			    
			    var OT_APPLY_HOUR_2   = document.getElementById("OT_APPLY_HOUR_2").value;
				if($("#OT_APPLY_HOUR").val()==null||$("#OT_APPLY_HOUR").val()==""){  
					otApplyHour =OT_APPLY_HOUR_2;
			       }
				if((dateType=='1440' &&otApplyHour==2)||(dateType=='1441'&&otApplyHour==8)||(dateType=='1442'&&otApplyHour==8)){
					otApplyMinute2.value=0;
					otApplyMinute2.disabled=true;
				}else{
					otApplyMinute2.disabled=false;

				}
				
				// $("#APPLY_TYPE_CODE option:selected").attr('disabled','disabled');
				
				 document.getElementById("APPLY_TYPE_CODE").disabled=true;
				//applyTypeCodeView.disabled=true;
				calLoTRemark();

				
			}
		});
	}
	//setTimeout("getLOtApplyType()",1000);
}

//获取加班的人事政策
function calLoTRemark(){
	var otTypeCode = $("#viewLOtApplyInfo select[id='APPLY_TYPE_CODE']").val();
	var otPlaceType = $("#viewLOtApplyInfo select[id='OT_PLACE_TYPE']").val();
 
	   var adjustYn = $("#viewLOtApplyInfo input[name='ADJUST_YN']:checked").val();
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
		        { name: 'OT_PLACE_TYPE', value: otPlaceType }],
		 dataType:"json",
		 success: function(response) {
			 document.getElementById('otLApplyRemark').innerHTML = response;
		 }
	});
}

//-->
</script>
<script type="text/javascript">
//<!--
//注意input的id和tr的id要一样

function deleteRowL(r){
	var i=r.parentNode.parentNode.rowIndex;
	document.getElementById('addAffirm_listL').deleteRow(i);
	//存在一种情况无法解决，同时添加两人，再删除第一人，则再添加新决裁者为第二位出现错位现象
	var tb2 = document.getElementById("addAffirm_listL");
	//如果决裁者只有一个时，删除添加的决裁者之后需要恢复原来的添加按钮
   	var affirmorListCnt = document.getElementById("affirmorListCnt").value;
   	var affirmorIdOnly = document.getElementById("affirmorIdOnly").value;
   	if(affirmorListCnt == 1){
   		var addStr = '<img id="'+affirmorIdOnly+'" src="/resources/images/+.gif" style="cursor:hand" title="添加" onclick="addRowByIDL('+affirmorIdOnly+');"/>';
   		tb2.rows[0].cells[2].innerHTML = addStr;
	}
	var rowCount = tb2.rows.length;
	for(var m=0;m<rowCount;m++){
		tb2.rows[m].cells[0].innerHTML = m+1;
	}
}

function delRowByIDL(){	
	//存在一种情况无法解决，同时添加两人，再删除第一人，则再添加新决裁者为第二位出现错位现象
	var tb2 = document.getElementById("addAffirm_listL");
	var rowCount = tb2.rows.length;
	for(var m=0;m<rowCount;m++){
		tb2.rows[m].cells[0].innerHTML = m+1;
	}
}

var keyCodeInit=0;
function submitKeyClick_affirmorL(obj,index,event){
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
							document.getElementById(empNameStr).value='['+jsonObject.empId + ']-'+jsonObject.empName;
							if(jsonObject.perCnt==1){
								
								 navTabAjaxDone(
					    	    	{
					    	    		"statusCode":"200", 
										"forwardUrl":"/ess/infoApply/viewLOtApplyInfo" + "?navTabId=" + "ess0235" + "&PERSON_ID=" + jsonObject.personId + "&APPLY_TYPE_NO=" + "31", //考勤申请类型
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
</script>
<div class="panel"><h1>加班申请</h1>
<div>
<%@ include file="/WEB-INF/view/hrm/empinfo/viewPersonalInfoHead_ess.jsp"%>
</div>
</div>
<div class="pageContent">
	<div>
		<form id="viewLOtApplyInfo" method="post" action="/ess/infoApply/addLOvertimeApply" class="pageForm required-validate" 
			onsubmit="return validateLOvertimeApplyCallback(this,navTabAjaxDone);">
			<div>
				<table class="user_table" width="100%"    border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="20%" class="td_title" style="text-align:center">日期</td>
						<td width="30%" class="td_type">
						  <input type="text" id="APPLY_OT_DATE" name="APPLY_OT_DATE" class="date required" 
						    	format="yyyy-MM-dd" readonly="true" value="${APPLY_OT_DATE}"/>
						    <a class="inputDateButton" href="javascript:;"><spring:message code="public.title.choose"/> 选择 </a>
						  <!-- 隐藏的一些参数 -->
						    <input id="empId_apply_b" name="dwz.person.empId1" value="${personInfo.EMPID}" type="hidden" />
						    <input id="APPLY_TYPE_NO" name="APPLY_TYPE_NO" type="hidden" value="31" />		   
						    <input id="APPLY_OT_DATE" name="APPLY_OT_DATE" type="hidden" value="${APPLY_OT_DATE}" />		   
						    <input id="APPLY_TYPE" name="APPLY_TYPE" type="hidden" value="PERSON" />
						    <input id="AFFIRM_FLAG" name="AFFIRM_FLAG" type="hidden" value="" />
						    <input type="hidden" id="OT_TIME_TYPE" name="OT_TIME_TYPE" value="L"/>
						    <input type="hidden" id="EMP_TYPE_CODE_GROUP" name="EMP_TYPE_CODE_GROUP" value="${personInfo.EMP_TYPE_CODE_GROUP }"/>
						    <input type="hidden" id="LLASTMONTH" name="LLASTMONTH" value="${LLASTMONTH }"/>
						    <input type="hidden" id="LIMIT_FLAG" name="LIMIT_FLAG" value="${personInfo.LIMIT_FLAG }"/>
						</td>
						<td width="20%" class="td_title" style="text-align:center">考勤类型</td>
						<td width="30%" class="td_type">
						   
						</td>
					</tr>
					<tr>
					   <td width="20%" class="td_title" style="text-align:center">倒休</td>
					   <td width="80%"  colspan="3" class="td_type">
					        <input type="checkbox" id="ADJUST_YN" name="ADJUST_YN" value="1"/>
					  </td>
					</tr>
				
					<!--<tr>
						<td width="20%" class="td_title" style="text-align:center"> 加班日期 
							加班日期
						</td>
						<td width="30%" class="td_type">
							触发对了，但是只有Firefox不好使
						    <input type="text" id="APPLY_OT_DATE" name="APPLY_OT_DATE" class="date required" onpropertychange="getLOtApplyType();ajaxAdd_add_ot_apply_one();"
						    	format="yyyy-MM-dd" readonly="true" value="${APPLY_OT_DATE}"/>
						    <a class="inputDateButton" href="javascript:;"><spring:message code="public.title.choose"/> 选择 </a>
						</td>
						<td width="20%" class="td_title" style="text-align:center">加班类型
							<spring:message code="ess.viewApply.title.overtimeApplyType"/>
						</td>
						<td width="30%" class="td_type"> 
							 
							<select id="APPLY_TYPE_CODE" name="APPLY_TYPE_CODE" onchange="calLoTRemark();ajaxAdd_add_ot_apply_one();" disabled="disabled">
						    	<option value="">请选择</option>
						    	<option value="32">平时加班</option>
						    	<option value="33">周末加班</option>
						    	<option value="34">法定假加班</option>
 								</select>
						    					    	
						    &nbsp;&nbsp;
						    <select id="OT_PLACE_TYPE" name="OT_PLACE_TYPE" onchange="calLoTRemark();ajaxAdd_add_ot_apply_one();">
						    	<option value="INSIDE">社内</option>
						    	<option value="OUTSIDE">社外</option>
						    </select>
						</td> 
					</tr>	
						
					
					<tr>
						<td width="20%" class="td_title" style="text-align:center">
							时间长度
						</td>
						<td width="30%" class="td_type">
						 
						 	<div id="hou98" style="float:left">
						    <select name="OT_APPLY_HOUR" id="OT_APPLY_HOUR"  onchange="getLOtApplyType();ajaxAdd_add_ot_apply_one();">
					
								<option value="">请选择
		                   			<spring:message code="sys.affirm.title.choose"/>
		                   		</option>
								<c:forEach var="h" begin="3" end="8" step="1">
									<option value="${h}" <c:if test="${h eq OT_APPLY_HOUR}">selected</c:if>>
										${h}小时
									</option>
								</c:forEach>
								 	 
							</select>
						 </div>
						 <div id="hour92" style="display:none;float:left">
						    <select name="OT_APPLY_HOUR_2" id="OT_APPLY_HOUR_2"   onchange="getLOtApplyType();ajaxAdd_add_ot_apply_one();">
					
								<option value="">请选择
		                   			<spring:message code="sys.affirm.title.choose"/>
		                   		</option>
								<c:forEach var="h" begin="1" end="2" step="1">
									<option value="${h}" <c:if test="${h eq OT_APPLY_HOUR}">selected</c:if>>
										${h}小时
									</option>
								</c:forEach>
								 	 
							</select>
						 </div>
						 
							<select name="OT_APPLY_MINUTE" id="OT_APPLY_MINUTE">
								<option value="">请选择
		                   			<spring:message code="sys.affirm.title.choose"/>
		                   		</option>
								<c:forEach var="m" begin="0" end="59" step="30">
									<option value="${m}" <c:if test="${m eq OT_APPLY_MINUTE}">selected</c:if>>
										${m}分
									</option>
								</c:forEach>
							</select>	   
						 
						</td>
						<td width="20%" class="td_title" style="text-align:center">是否转调休
					    	是否转调休
					    </td>
					    <td width="30%" class="td_type">
					    	<input type="radio" id="ADJUST_YN_N" name="ADJUST_YN" value="0" title="否" checked="checked"  onclick="calLoTRemark();"/>否&nbsp;&nbsp;&nbsp;
					    	<input type="radio" id="ADJUST_YN_Y" name="ADJUST_YN" value="1" title="是"  onclick="calLoTRemark();"/>是
					    </td>
					<tr>
					    <td width="20%" class="td_title" style="text-align:center">人事政策
					    	人事政策
					    </td>
					    <td width="80%" class="td_type" colspan="3">
					    	<font color="red">
					    		<div id="otLApplyRemark"></div>
					    	</font>
					    </td>
					</tr>
				
					-->
					<tr>
					    <td width="20%" class="td_title" style="text-align:center">
					    	班次
					    </td>
					    <td width="30%" class="td_type" >
					    	
					    </td>
					    <td width="20%" class="td_title" style="text-align:center">
					    	工作时间
					    </td>
					    <td width="30%" class="td_type" >
					    	
					    </td>
					</tr>
						<tr>
					    <td width="20%" class="td_title" style="text-align:center">
					    	进出门时间
					    </td>
					    <td width="80%" class="td_type" colspan="3">
					    	                                                                                                                进门时间：
					    	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;出门时间:
					    </td>
					</tr>
					</tr>
						<tr>
					    <td width="20%" class="td_title" style="text-align:center">
					    	时间
					    </td>
					    <td width="80%" class="td_type" colspan="3">
			 	            <select name="OT_APPLY_HOUR_1" id="OT_APPLY_HOUR_1"  >
								<c:forEach var="h" begin="0" end="23" step="1" varStatus="status">
									<option value="${h}" <c:if test="${h eq OT_APPLY_HOUR}">selected</c:if>>
									
									  <c:if test="${status.count<=10}">
									   0${h}
									  </c:if>
									   <c:if test="${status.count>10}">
									   ${h}
									  </c:if>
									 
									</option>
								</c:forEach>
								 	 
							</select>
							<select name="OT_APPLY_MIN1" id="OT_APPLY_MIN_1"   >
									<option value="00" <c:if test="${h eq OT_APPLY_HOUR}">00</c:if>>00</option>
									<option value="30" <c:if test="${h eq OT_APPLY_HOUR}">00</c:if>>30</option>
							</select>
							~
							 <select name="OT_APPLY_HOUR_2" id="OT_APPLY_HOUR_2"   >
								<c:forEach var="h" begin="0" end="23" step="1" varStatus="status">
									<option value="${h}" <c:if test="${h eq OT_APPLY_HOUR}">selected</c:if>>
									  <c:if test="${status.count<=10}">
									   0${h}
									  </c:if>
									   <c:if test="${status.count>10}">
									   ${h}
									  </c:if>
									</option>
								</c:forEach>
								 	 
							</select>
							<select name="OT_APPLY_MIN_2" id="OT_APPLY_MIN_2"   >
									<option value="00" <c:if test="${h eq OT_APPLY_HOUR}">00</c:if>>00</option>
									<option value="30" <c:if test="${h eq OT_APPLY_HOUR}">00</c:if>>30</option>
							</select>
							
							</tr>
						<tr>
					    <td width="20%" class="td_title" style="text-align:center">
					    	加班时间(时间/分)
					    </td>
					    <td width="80%" class="td_type" colspan="3">
					    	   00' 00"                                                                                                       
					    </td>
					</tr>
					    </td>
					    
					</tr>
					</tr>
								<tr>							
					    <td width="20%" class="td_title" style="text-align:center"><!--其他原因-->
					    	其他原因
					    </td>
					    <td width="80%" class="td_type" colspan="3">
					    	<textarea style="width:500px;height:100px" id="APPLY_REMARK" name="APPLY_REMARK"/>
					    </td>
					</tr>	
								
					<tr>
						<td colspan="4">
							<table class="user_table" width="100%">	
								<tr>
								    
								    <td class="td_title"  style="text-align:center;" width="5%">序号</td>
									<td class="td_title"  style="text-align:center;" width="20%">审批区分</td>
									<td class="td_title" style="text-align:center;" width="25%">审批人</td>
									<td class="td_title" style="text-align:center;" width="25%">审批人信息</td>
									<td class="td_title" style="text-align:center;" width="25%">是否新增(<img src="/resources/images/+.gif" title="添加" border="0" align="absmiddle" style="cursor:hand" onclick="addRowByIDApplyLOTFirst()"/>)</td>
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
						</td>
					</tr>
					  <input type="hidden" id="affirmorListCnt" name="affirmorListCnt" value="${affirmorListCnt }"/>
					  <input type="hidden" name="applyOtCount_1" id="applyOtCount_1" value="">
					  <a id="onck" name="onck"  href="" lookupGroup="person"></a>
				</table>
			</div>
			<div class="formBar">
				<ul>
					<li>
						<div class="button">
							<div class="buttonContent"><!--提交-->
								<button type="button" onclick="submitLFormPre(0)">
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