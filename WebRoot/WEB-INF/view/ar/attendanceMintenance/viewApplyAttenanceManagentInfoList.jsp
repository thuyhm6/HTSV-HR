<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script>

$(document).ready(function(){
       //查询
       $("#viewApplyAttenanceManagentInfoList_Serch",navTab.getCurrentPanel()).click(function(){
			$("#viewApplyAttenanceManagentInfoList",navTab.getCurrentPanel()).submit();
	   });
      //搜索
     $("#seach_KEY",navTab.getCurrentPanel()).keydown(function(e) {
        if ( e.keyCode == 13) {
       	 	var name=encodeURI(encodeURI($('#seach_KEY',navTab.getCurrentPanel()).val()));
       	    var FROM_DATE=encodeURI(encodeURI($('#seach_FROM_DATE',navTab.getCurrentPanel()).val()));
      	    var TO_DATE=encodeURI(encodeURI($('#seach_TO_DATE',navTab.getCurrentPanel()).val()));
      	    var APPLY_CODE=encodeURI(encodeURI($('#seach_APPLY_CODE',navTab.getCurrentPanel()).val()));
       		$('.btnLook',navTab.getCurrentPanel()).attr('href','/ar/attendanceMintenance/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=viewApplyAttenanceManagentInfoList&seach_KEY='+name+'&FROM_DATE='+FROM_DATE+'&TO_DATE='+TO_DATE+'&APPLY_CODE='+APPLY_CODE);
       		$('.btnLook',navTab.getCurrentPanel()).click();
        }
     });
	 $(".btnLook",navTab.getCurrentPanel()).click(function(e) {
      	 var name=encodeURI(encodeURI($('#seach_KEY',navTab.getCurrentPanel()).val()));
      	 var FROM_DATE=encodeURI(encodeURI($('#seach_FROM_DATE',navTab.getCurrentPanel()).val()));
      	 var TO_DATE=encodeURI(encodeURI($('#seach_TO_DATE',navTab.getCurrentPanel()).val()));
      	 var APPLY_CODE=encodeURI(encodeURI($('#seach_APPLY_CODE',navTab.getCurrentPanel()).val()));
      	$('.btnLook',navTab.getCurrentPanel()).attr('href','/ar/attendanceMintenance/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=viewApplyAttenanceManagentInfoList&seach_KEY='+name+'&FROM_DATE='+FROM_DATE+'&TO_DATE='+TO_DATE+'&APPLY_CODE='+APPLY_CODE);
     });
	$(".orderList",navTab.getCurrentPanel()).dataTable({"bPaginate": false,    //关闭分页
		    "bAutoWidth":false,//表格宽度不自动变化
		    "bProcessing":true,
			"bLengthChange": false,  //关闭按多少条记录显示下拉框
			"bFilter": true,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
			"bSort": true,   //关闭排序功能
			"bInfo": false,   //不显示datatables的信息（底部的页数，条目数信息）
			"bScrollInfinite":true,
			"scrollY": $(document.body).height() - 240,
            "scrollX": true,
            "orderClasses": false,
            "oLanguage": {
                "sProcessing": "正在加载中......",
                "sZeroRecords": "查询不到相关数据！",
                "sEmptyTable": "表中无数据存在！",
                "sSearch": "快速筛选"
            } //多语言配置
		});
});
function delLeaveApplyCallbackBatch(OP_FLAG,form,callback) {
	$("#BATCH_LEAVE_OP_FLAG").val(OP_FLAG);
	var $form=null;
	if($('#'+form).length>0)
		$form=$('#'+form);
	else
 		$form = $(form);
	
	if (!$form.valid()) {
		return false;
	}
	if(OP_FLAG != 2){
	    var checked=false;
		var ids= document.getElementsByName("BATCH_LEAVE");
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
	   var ids= document.getElementsByName("BATCH_LEAVE");
	   var checked=false;
		for(var i=0;i<ids.length;i++){
		    if(ids[i].checked){
				var personid = document.getElementById('dwz.person.AFFIRMOR_IDApplyLeave'+ids[i].value).value;
				var LOCAL_NAME = document.getElementById('dwz.person.EMPINFOApplyLeave'+ids[i].value).value;
				var ITEM_NO = document.getElementById('valibl_value_ITEM_NO'+ids[i].value).value;
				var AFFIRM_FLAG = document.getElementById('valibl_value_AFFIRM_NO'+ids[i].value).value;
				var fromTime=document.getElementById('fromTime'+ids[i].value).value;
				var toTime=document.getElementById('toTime'+ids[i].value).value;
				var shenqingshichang=document.getElementById('shenqingshichang'+ids[i].value).value;//获取申请时长
				var PK_NO = ids[i].value;
	            var affrimold;
	            if(ids[i].value != null){
				   $.ajax({
							cache: false,
						    type: 'post',
							async:false,
					        url: "/ess/infoApplyAttendance/getChechedAffrim",
							data: [{ name: 'PK_NO', value: PK_NO }],
							dataType:"json",
							success: function(data) {
							  affrimold = data.apply_affrim;
							}
					}); 
					if(LOCAL_NAME == "" ){
						alertMsg.error('添加数据姓名为空,请搜索要添加的员工');
						return false;
					}else{
					   if(personid == ""){
					      alertMsg.error('请搜索要添加的员工');
						 return false;
					   }
					}
					if(ITEM_NO==""){
						alertMsg.error('你还未输入考勤状态');
						return false;
					}
					if(ITEM_NO=="141439"){
						alertMsg.error('正常出勤不需要做申请,请选择其他考勤类型');
						$('#valibl_value_ITEM_NO'+ids[i].value).focus();
						return false;
					}
					
				    if(fromTime.length != 4){
						alertMsg.error('请输入合法的开始时间!');
						return false;
					}
				    if(toTime.length != 4){
						alertMsg.error('请输入合法的结束时间!');
						return false;
					}
					if(ITEM_NO=="141440"||ITEM_NO=="141441"||ITEM_NO=="141442"||ITEM_NO=="141443"||ITEM_NO=="14013783"){
					   if(affrimold=="14014306"){
						     if(AFFIRM_FLAG!="14014308"){
								document.getElementById('valibl_value_AFFIRM_NO'+ids[i].value).value = '14014308';
							 }
					   }else{
					         if(AFFIRM_FLAG!="14014312"){
								document.getElementById('valibl_value_AFFIRM_NO'+ids[i].value).value = '14014312';
							 }
					   }
					}
				    if(shenqingshichang=="" || shenqingshichang == null || shenqingshichang==0){
						alertMsg.error('休假时长是0或者为空');
						return false;
					}
			   }
		    }
		}
	}
	var CPNY_ID = document.getElementById("CPNY_ID").value;
	var myDate = new Date();
	var year = myDate.getFullYear();
	var month = myDate.getMonth()+1;
	month =(month<10 ? "0"+month:month); 
	var date = myDate.getDate();
	var applyDate = year+'-'+month+'-'+date;
	var FROM_DATE=document.getElementById('seach_FROM_DATE').value;
	var applyBatchdate;
	if('TSTO' == CPNY_ID){
	  applyBatchdate = FROM_DATE;
	}else{
	  applyBatchdate = applyDate;
	}
    $form.attr("action","/ar/attendanceMintenance/delAttendanceApplyInBatchForBatch?OP_FLAG="+OP_FLAG+"&applyBatchdate="+applyBatchdate);
    var msg ="确定要添加?";
    if(OP_FLAG == 0)
    var msg = "确定要删除吗？";
    if(OP_FLAG == 1){
        var msg = "确定要提交吗？";
    }
    if(OP_FLAG != 2){
	    alertMsg.confirm(msg,{okCall:function(){
				$.ajax({
					type: form.method || 'POST',
					url:$form.attr("action"), 
					data:$form.serializeArray(),
					dataType:"json",
					cache: false,
					success: function(data){ //请求成功后处理函数。
						if(data.statusCode=="200"){
						    navTabSearch(document.viewApplyAttenanceManagentInfoList);
							  if(OP_FLAG != 2){
							alertMsg.correct(data.message);
						}
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
	 }else{
	     $.ajax({
					type: form.method || 'POST',
					url:$form.attr("action"), 
					data:$form.serializeArray(),
					dataType:"json",
					cache: false,
					success: function(data){ //请求成功后处理函数。
					  //减少deleteYN,保证点击搜索 后删除null数据源
				      $("#viewApplyAttenanceManagentInfoList").attr("action","/ar/attendanceMintenance/viewApplyAttenanceManagentInfoList?firstFlag=N&nullYN=Y");
					  navTabSearch(document.viewApplyAttenanceManagentInfoList);
			   	 	},
					error: DWZ.ajaxError
				});
	 }
	return false;
}



function xiujialeixing(id){
	if('0' == id){
		document.getElementById('viewApplyAttenBatch').style.display = 'none';
		document.getElementById('closeApplyLeave').style.display = 'none';
		document.getElementById('openApplyLeave').style.display = 'block';
	}else{
		document.getElementById('viewApplyAttenBatch').style.display = 'block';
		document.getElementById('closeApplyLeave').style.display = 'block';
		document.getElementById('openApplyLeave').style.display = 'none';
	}
}
//日期验证
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

function jsSelectItemByValue(objSelect, objItemText) {        
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
 //文本fill
 function textMuli(name,value){
  var ids = document.getElementsByName("BATCH_LEAVE");
 
		if(ids.length>0){
			for(var i=0;i<ids.length;i++){
		      var j=ids[i].value;
				if(ids[i].checked==true){
		    		document.getElementById(name+j).value=value;
				}
			}		  
		}
}
//下拉框多选
function selMuli(name,value){
 var ids = document.getElementsByName("BATCH_LEAVE");
		if(ids.length>0){
			for(var i=0;i<ids.length;i++){
	           var j=ids[i].value;
				if(ids[i].checked==true){
		    		var sel=document.getElementById(name+j);
		    		jsSelectItemByValue(sel,value);
				}
			}		  
		}
}
function fillItem(){
   
     var checked=false;
		var ids= document.getElementsByName("BATCH_LEAVE");
		for(var i=0;i<ids.length;i++){
			if(ids[i].checked){
				checked=true;
			}
		}
		if(!checked){
			alertMsg.error('请选择反应记录'); 
			return false;
		}
   
  var reason=document.getElementById("reason").value;
  var FROM_DATE=document.getElementById("FROM_DATE").value;
  var fromTime=document.getElementById("fromTime").value;
  if(fromTime != null && fromTime != ""){
     var fromTimeCheck = checkTime(fromTime);
	  if(fromTimeCheck == false ){
		return ;
	  }
  }
  var CPNY_ID = document.getElementById("CPNY_ID").value;
  var TO_DATE=document.getElementById("TO_DATE").value; 
  var toTime=document.getElementById("toTime").value;
  if(toTime != null && toTime != ""){
	  var toTimeCheck = checkTime(toTime);
	  if(toTimeCheck == false ){
		return ;
	  }
  }
  var fillAffirmFlag=document.getElementById("FILLAFFIRMFLAG").value;
  var ITEM_NO=document.getElementById("ITEM_NO").value;
  var obj = document.getElementById("FILLAFFIRMFLAG");
  var txt = obj.options[obj.selectedIndex].text;
  
   //刷新后全部反应的内容还存在
  document.getElementById("FROM_DATE1").value=FROM_DATE;
  document.getElementById("TO_DATE1").value=TO_DATE;
  document.getElementById("otherReason1").value=reason;
 
  document.getElementById("ITEM_NO1").value=ITEM_NO;
  document.getElementById("fromTime1").value=fromTime;
  document.getElementById("toTime1").value=toTime;
  document.getElementById("FILLAFFIRMFLAG1").value=fillAffirmFlag;
  
  if(reason != null && reason !=""){
     textMuli("reason",reason);  
  }
  if(FROM_DATE != null && FROM_DATE !=""){
     textMuli("FROM_DATE",FROM_DATE);  
  }
  if(TO_DATE != null && TO_DATE !=""){
     textMuli("TO_DATE",TO_DATE);  
  }
  if(fromTime != null && fromTime !=""){
     textMuli("fromTime",fromTime);   
  }
  if(toTime != null && toTime !=""){
     textMuli("toTime",toTime);   
  }
   
  if(fillAffirmFlag != "" && fillAffirmFlag != null){
     textMuli("valibl_input_AFFIRM_NO",txt);  	
     textMuli("valibl_value_AFFIRM_NO",fillAffirmFlag);   
  }else{
    if(CPNY_ID == 'TSTO'){
	     textMuli("valibl_value_AFFIRM_NO",'14014308');  	
	     textMuli("valibl_input_AFFIRM_NO",'部门长批准');
     }else{
         textMuli("valibl_value_AFFIRM_NO",'14015210');  	
	     textMuli("valibl_input_AFFIRM_NO",'部门审批');
     } 
  }    
 if(ITEM_NO != null && ITEM_NO != ""){
	  textMuli("valibl_value_ITEM_NO",ITEM_NO);
	  textMuli("valibl_input_ITEM_NO",document.getElementById("ITEM_NO").options[document.getElementById("ITEM_NO").selectedIndex].text);
 }
 
  callengthForFill();
}

 function eventfunction(obj)   {   
   if(obj.id == undefined)
	   obj = document.getElementById(''+obj) ;
   var empIdStr=document.getElementById('dwz.person.'+obj.id.substring(11)).value;
   var PERSON_ID=document.getElementById('dwz.person.AFFIRMOR_IDApplyLeave'+obj.id.substring(28)).value;
   if(empIdStr != '' && PERSON_ID == ''){
	   //alert('没有精确到某个员工!请搜索员工');
	   return;
   }
   
   var applyBatchdate = document.getElementById('seach_applyBatchdate').value;
   var CPNY_ID = document.getElementById('CPNY_ID').value;
   var shfitNoTemp ;
   var j=obj.id.substring(28);
     if(empIdStr != ''){
	      $.ajax({
				cache: false,
			    type: 'post',
				async:false,
		        url: "/ess/infoApplyAttendance/getAttendanceInformation",
				data: [{ name: 'PERSON_ID', value: PERSON_ID },
				       { name: 'applyBatchdate',value: applyBatchdate }
				],
				dataType:"json",
				success: function(data) {
				$('#TOT_VAC_CNTText'+j).html(data.TOT_VAC_CNT);
				$('#DEPTNONAMETEXT'+j).html(data.DEPTNONAME);
				$('#DEPTNOTEXT'+j).val(data.DEPTNO);
				$('#EMPIDTEXT'+j).html(data.EMPID);
				$('#SHENGYU_VAC_CNTText'+j).html(data.SHENGYU_VAC_CNT);
				$('#SHENGYU_VAC_CNT'+j).val(data.SHENGYU_VAC_CNT);
				var TOTAL_TX ;
				var USE_TX ;
				if(CPNY_ID=='TSTO'){
				   TOTAL_TX = data.TOTAL_TX;
				   USE_TX = data.USE_TX;
				}else{
				   TOTAL_TX = parseFloat(data.TOTAL_TX)/8;
				   USE_TX = parseFloat(data.USE_TX)/8;
				}
				$('#TOTAL_TXText'+j).html(TOTAL_TX);
				$('#USE_TXText'+j).html(USE_TX);
				if(CPNY_ID=='TSTO'){
				   $('#GROUP_NAMEText'+j).html(data.GROUP_NAME);
				   $('#GROUP_IDTEXT'+j).val(data.GROUP_ID);
				}else{
				   $('#GROUP_NAMEText'+j).html('正常班');
				   $('#GROUP_IDTEXT'+j).val('400224');
				}
				$('#WORK_TIMETEXT'+j).html(data.WORK_TIME);
				$('#AR_MONTH_STRTEXT'+j).val(data.AR_MONTH_STR);
				$('#INDOOR_DATETEXT'+j).html(data.INDOOR_DATE);
				$('#OUTDOOR_DATETEXT'+j).html(data.OUTDOOR_DATE);
				$('#DATA_TYPETEXT'+j).val(data.DATA_TYPE);
				$('#TYPE_NAMETEXT'+j).html(data.TYPE_NAME);
				$('#STATUS_CODETEXT'+j).val(data.STATUS_CODE);
				$('#STATUS_NAMETEXT'+j).val(data.STATUS_NAME);
				$('#IWEEKTEXT'+j).val(data.IWEEK);
				$('#POST_GRADE_NOTEXT'+j).val(data.POST_GRADE_NO);
				shfitNoTemp = data.SHIFT_NO;
				$('#SHIFT_NO'+j).val(shfitNoTemp);
				
				$('#fromTime'+j).val(data.FROM_TIME_FIRST);
				$('#toTime'+j).val(data.TO_TIME_LAST);
					if(CPNY_ID == 'TSTO'){
						$('#valibl_input_AFFIRM_NO'+j).val('部门长批准');
						$('#valibl_value_AFFIRM_NO'+j).val('14014308');
					}else{
					    $('#valibl_input_AFFIRM_NO'+j).val('部门审批');
						$('#valibl_value_AFFIRM_NO'+j).val('14015210');
					}
				}
		 });
     
	  }
	 //计算时长
			    var leave_from_date = $("#FROM_DATE"+j).val();
				var fromTime1 = $("#fromTime"+j).val();
				checkTime(fromTime1);
				var fromTime = fromTime1.substr(0,2)+":"+fromTime1.substr(2,2);
				var leave_to_date = $("#TO_DATE"+j).val();
				var toTime1 = $("#toTime"+j).val();
				checkTime(toTime1);
				var toTime = toTime1.substr(0,2)+":"+toTime1.substr(2,2);
					//alert(patrn.test(toTime));
				var leavefromtime = leave_from_date + " " + fromTime + ":" + "00";
				var leavetotime = leave_to_date + " " + toTime + ":" + "00";
				var applyTypeCode = $("#valibl_value_ITEM_NO"+j).val();
			  
				if(comptime(leavefromtime,leavetotime)==1||shfitNoTemp=='14013824'
					||shfitNoTemp=='14013825'
						||shfitNoTemp=='14013826'
							||shfitNoTemp=='14013831'){
					var PERSON_ID = document.getElementById("dwz.person.AFFIRMOR_IDApplyLeave"+j).value;
					var CPNY_ID = document.getElementById("CPNY_ID").value;
					$.ajax({
						 cache: false,
						 type: 'post',
						 async:false,
						 url: "/ess/infoApplyAttendance/getShenqingshichang",
						 data: [{ name: 'PERSON_ID', value: PERSON_ID },  
						        { name: 'CPNY_ID', value: CPNY_ID },
						        { name: 'leavefromtime', value: leavefromtime },
						        { name: 'leavetotime', value: leavetotime },
						        { name: 'ITEM_NO', value: applyTypeCode }],
						 dataType:"json",
						 success: function(data) {
							$("#shenqingshichangText"+j).html(data.lengthStr);
							$("#shenqingshichang"+j).val(data.length);
						 }
					});
				}
		//选中 
		$('#BATCH_LEAVE'+j).attr('checked','checked');
 }

function callength(event,j){
	var leave_from_date = $("#FROM_DATE"+j).val();
	var fromTime1 = $("#fromTime"+j).val();
	if(fromTime1.length==4){
	   
	  var resultFromTime = checkTime(fromTime1);
	  if(resultFromTime == false){
	    $("#fromTime"+j).val('')
	  }
	  $('#BATCH_LEAVE'+j).attr('checked','checked');
	}
	var fromTime = fromTime1.substr(0,2)+":"+fromTime1.substr(2,2);
	var leave_to_date = $("#TO_DATE"+j).val();
	var toTime1 = $("#toTime"+j).val();
	if(toTime1.length==4){
	  var resultToTime =   checkTime(toTime1);
	  if(resultToTime == false){
	    $("#toTime"+j).val('');
	  }
	  $('#BATCH_LEAVE'+j).attr('checked','checked');
	}
	var toTime = toTime1.substr(0,2)+":"+toTime1.substr(2,2);
					//alert(patrn.test(toTime));
	var leavefromtime = leave_from_date + " " + fromTime + ":" + "00";
	var leavetotime = leave_to_date + " " + toTime + ":" + "00";
	var applyTypeCode = $("#valibl_value_ITEM_NO"+j).val();
	var shfitNoTemp = $('#SHIFT_NO'+j).val() ;
	var e= event ? event : window.event; 
 	var keyCode = e.which ? e.which : e.keyCode;
   	if(keyCode==13){
		if(comptime(leavefromtime,leavetotime)==1||shfitNoTemp=='14013824'
					||shfitNoTemp=='14013825'
						||shfitNoTemp=='14013826'
							||shfitNoTemp=='14013831'){
			var PERSON_ID = document.getElementById("dwz.person.AFFIRMOR_IDApplyLeave"+j).value;
			var CPNY_ID = document.getElementById("CPNY_ID").value;
				$.ajax({
						 cache: false,
						 type: 'post',
						 async:false,
						 url: "/ess/infoApplyAttendance/getShenqingshichang",
						 data: [{ name: 'PERSON_ID', value: PERSON_ID },  
						        { name: 'CPNY_ID', value: CPNY_ID },
						        { name: 'leavefromtime', value: leavefromtime },
						        { name: 'leavetotime', value: leavetotime },
						        { name: 'ITEM_NO', value: applyTypeCode }],
						 dataType:"json",
						 success: function(data) {
							$("#shenqingshichangText"+j).html(data.lengthStr);
							$("#shenqingshichang"+j).val(data.length);
						 }
				});
		}
	}
}

//联动日期获取加班时长

function callengthForDate(){
 var ids = document.getElementsByName("BATCH_LEAVE");
 var checked=false;
		for(var i=0;i<ids.length;i++){
			if(ids[i].checked){
				checked=true;
			}
		}
		if(!checked){
			alertMsg.error('请选择申请记录'); 
			return false;
		}
 	if(ids.length>0){
		for(var i=0;i<ids.length;i++){
			 if(ids[i].checked){
	           var j=ids[i].value;
			    var leave_from_date = $("#FROM_DATE"+j).val();
				var fromTime1 = $("#fromTime"+j).val();
				var  rsfromTime1= checkTime(fromTime1);
				if(rsfromTime1 == false ){
				  return false;
				}
				checkTime(fromTime1);
				var fromTime = fromTime1.substr(0,2)+":"+fromTime1.substr(2,2);
				var leave_to_date = $("#TO_DATE"+j).val();
				var toTime1 = $("#toTime"+j).val();
				var  rstoTime1= checkTime(toTime1);
				if(rstoTime1 == false ){
				  return false;
				}
				var toTime = toTime1.substr(0,2)+":"+toTime1.substr(2,2);
					//alert(patrn.test(toTime));
				var leavefromtime = leave_from_date + " " + fromTime + ":" + "00";
				var leavetotime = leave_to_date + " " + toTime + ":" + "00";
				var applyTypeCode = $("#valibl_value_ITEM_NO"+j).val();
				var shfitNoTemp = $('#SHIFT_NO'+j).val() ;
			  
				if(comptime(leavefromtime,leavetotime)==1||shfitNoTemp=='14013824'
					||shfitNoTemp=='14013825'
						||shfitNoTemp=='14013826'
							||shfitNoTemp=='14013831'){
					var PERSON_ID = document.getElementById("dwz.person.AFFIRMOR_IDApplyLeave"+j).value;
					var CPNY_ID = document.getElementById("CPNY_ID").value;
					$.ajax({
						 cache: false,
						 type: 'post',
						 async:false,
						 url: "/ess/infoApplyAttendance/getShenqingshichang",
						 data: [{ name: 'PERSON_ID', value: PERSON_ID },  
						        { name: 'CPNY_ID', value: CPNY_ID },
						        { name: 'leavefromtime', value: leavefromtime },
						        { name: 'leavetotime', value: leavetotime },
						        { name: 'ITEM_NO', value: applyTypeCode }],
						 dataType:"json",
						 success: function(data) {
							$("#shenqingshichangText"+j).html(data.lengthStr);
							$("#shenqingshichang"+j).val(data.length);
						 }
					});
				}
				}
			  }
			}
}
//填充用
function callengthForFill(){
 var ids = document.getElementsByName("BATCH_LEAVE");
 var checked=false;
		for(var i=0;i<ids.length;i++){
			if(ids[i].checked){
				checked=true;
			}
		}
		if(!checked){
			alertMsg.error('请选择申请记录'); 
			return false;
		}
		
 	if(ids.length>0){
		for(var i=0;i<ids.length;i++){
			 if(ids[i].checked){
	           var j=ids[i].value;
			    var leave_from_date = $("#FROM_DATE"+j).val();
				var fromTime1 = $("#fromTime"+j).val();
				checkTime(fromTime1);
				var fromTime = fromTime1.substr(0,2)+":"+fromTime1.substr(2,2);
				var leave_to_date = $("#TO_DATE"+j).val();
				var toTime1 = $("#toTime"+j).val();
				checkTime(toTime1);
				var toTime = toTime1.substr(0,2)+":"+toTime1.substr(2,2);
					//alert(patrn.test(toTime));
				var leavefromtime = leave_from_date + " " + fromTime + ":" + "00";
				var leavetotime = leave_to_date + " " + toTime + ":" + "00";
				var applyTypeCode = $("#valibl_value_ITEM_NO"+j).val();
			    var shfitNoTemp = $('#SHIFT_NO'+j).val() ;
				if(comptime(leavefromtime,leavetotime)==1||shfitNoTemp=='14013824'
					||shfitNoTemp=='14013825'
						||shfitNoTemp=='14013826'
							||shfitNoTemp=='14013831'){
					var PERSON_ID = document.getElementById("dwz.person.AFFIRMOR_IDApplyLeave"+j).value;
					var CPNY_ID = document.getElementById("CPNY_ID").value;
					$.ajax({
						 cache: false,
						 type: 'post',
						 async:false,
						 url: "/ess/infoApplyAttendance/getShenqingshichang",
						 data: [{ name: 'PERSON_ID', value: PERSON_ID },  
						        { name: 'CPNY_ID', value: CPNY_ID },
						        { name: 'leavefromtime', value: leavefromtime },
						        { name: 'leavetotime', value: leavetotime },
						        { name: 'ITEM_NO', value: applyTypeCode }],
						 dataType:"json",
						 success: function(data) {
							$("#shenqingshichangText"+j).html(data.lengthStr);
							$("#shenqingshichang"+j).val(data.length);
						 }
					});
				}
				 }
			  }
			}
}

//计算时长
function callength_date(){
//	取值判断
 var ids = document.getElementsByName("BATCH_LEAVE");
 var checked=false;
	for(var i=0;i<ids.length;i++){
	    var j=ids[i].value;
		//var is_equal_from_time = $("#is_equal_from_time"+j).val();
		var leave_from_date = $("#FROM_DATE"+j).val();
		var old_leave_from_date = $("#oldFROM_DATE"+j).val();
		//var is_equal_to_time = $("is_equal_to_time"+j).val();
		var leave_to_date = $("#TO_DATE"+j).val();	
		var old_leave_to_date = $("#oldTO_DATE"+j).val();	
			//  赋值
		$("#is_equal_from_time"+j).val(leave_from_date);
		$("#is_equal_to_time"+j).val(leave_to_date);
		if((old_leave_from_date != leave_from_date && '' != leave_from_date)|| (old_leave_to_date != leave_to_date && '' != leave_to_date)){
		    $("#BATCH_LEAVE"+j).attr('checked','checked');
			callengthForDate();
		}else{
		    callengthForDate();
		}
	}	  
}

//当倒休的时候打开倒休选择的窗口
function openAJDialog(id){
  var CPNY_ID = document.getElementById('CPNY_ID').value;
  if(CPNY_ID == 'TSTO')
  var ITEM_NO = $('#valibl_value_ITEM_NO'+id).val();
  //倒休的时候弹出倒休的记录数
  if(ITEM_NO == '14013845'){
    var PERSON_ID = document.getElementById('dwz.person.AFFIRMOR_IDApplyLeave'+id).value;
    var AR_DATE_STR = document.getElementById('FROM_DATE'+id).value;
	 $('#openadjust').attr('href','/ar/attendanceMintenance/viewAdjustRecords?PERSON_ID='+PERSON_ID+'&AR_DATE_STR='+AR_DATE_STR+'&ID='+id);
	 $('#openadjust').click();
  }
}

//时间格式比较
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

function downloadExl(url){
		$('#viewApplyLeaveInfoList').attr("action",url) ;
		$('#viewApplyLeaveInfoList').attr("onsubmit",'') ;
		$('#viewApplyLeaveInfoList').submit() ;
		$('#viewApplyLeaveInfoList').attr("action",'/ess/infoApplyAttendance/viewApplyAttenanceBatchInfoList') ;
		$('#viewApplyLeaveInfoList').attr("onsubmit",'return navTabSearch(this);') ;
	}
 
 
function addEmpPop_ar0200(obj,flag) {
	var name = encodeURI(encodeURI($(obj).val()));
	var empIdStr=obj.id.substring(11);
	var personIdStr="AFFIRMOR_IDApplyLeave"+empIdStr.substring(17);
	$("#addEmpPop_ar0200", navTab.getCurrentPanel())
			.attr(
					'href',
					'/pa/workManagement/viewEmpForArAddPopList?pageNum=1&numPerPage=10&limit=ar&seach_KEY='
							+ name+'&empidStr='+empIdStr
							+'&personidStr='+personIdStr   );
	if (flag == 'onkeyup')
		$("#addEmpPop_ar0200", navTab.getCurrentPanel()).click();
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


function searchPop_ess6666(flag) {
	var name = encodeURI(encodeURI($("#seach_KEY", navTab.getCurrentPanel()).val()));
	//$('#searchPop',navTab.getCurrent())
	$("#searchPop_ess6666", navTab.getCurrentPanel())
			.attr(
					'href',
					'/pa/workManagement/viewEmpForPopList?pageNum=1&numPerPage=10&limit=ar&seach_KEY='
							+ name);
	if (flag == 'onkeyup')
		$("#searchPop_ess6666", navTab.getCurrentPanel()).click();
}

</script>
<div id="viewApplyAttenBatch"  class="pageHeader" >
	<form onsubmit="return navTabSearch(this);" action="/ar/attendanceMintenance/viewApplyAttenanceManagentInfoList?firstFlag=N&deleteYN=Y"  method="post"
		id="viewApplyAttenanceManagentInfoList" name="viewApplyAttenanceManagentInfoList">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
				    <input id="CPNY_ID" name="CPNY_ID" type="hidden" value="${LoginUser.cpnyId}"/>
				      <input type="hidden" id="FROM_DATE1" name="FROM_DATE1"  value="${FROM_DATE1}" />
				    <input type="hidden" id="TO_DATE1" name="TO_DATE1"  value="${TO_DATE1}" />
				    <input type="hidden" id="fromTime1"  name="fromTime1"  size="3"  value="${fromTime1}"/>
				    <input type="hidden" id="toTime1"  name="toTime1"  size="3" value="${toTime1}"/>
				    <input type="hidden" id="ITEM_NO1"  name="ITEM_NO1"  size="3" value="${ITEM_NO1}"/>
				    <input type="hidden" id="otherReason1" name="otherReason1"  value="${otherReason1}"/>
				    <input type="hidden" id="FILLAFFIRMFLAG1" name="FILLAFFIRMFLAG1"  value="${FILLAFFIRMFLAG1}"/>
				    <%-- 
					<td >社号/姓名</td>
					 <td colspan="4">
					     <input type="text" name="seach_KEY" id="seach_KEY"
							value="${KEY}" style="float:left;" 
						onkeydown="javascript:if(event.keyCode == 13)searchPop_ess6666('onkeyup');" />
						<a class="btnLook" id="searchPop_ess6666"
						onclick="searchPop_ess6666()" href="#" lookupGroup="person"> </a>
						<input id="dwz.person.empInfo"  type="text" readonly lookupGroup="person" size="80" value="${empInfo}"/> 
						<input type="hidden" id="firstFlag" name="firstFlag" value="N"/>
					</td>
					--%>
					<td width="7%">社号/姓名</td>
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
					<td width="10%">
						 期间
					</td>
						
					<td width="20%">
					    <input type="hidden" id="seach_applyBatchdate" name="seach_applyBatchdate"   value="${FROM_DATE}"  />
					    <input type="text" name="seach_FROM_DATE" id="seach_FROM_DATE"  class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="${FROM_DATE}"/>
					     <input type="text" name="seach_TO_DATE" id="seach_TO_DATE"  class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="${TO_DATE}"/>
					</td>
					<td width="30%">审批状态 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					     <c:if test="${LoginUser.cpnyId eq 'TSTO'}">
					      <ait:selectCodeMulti id="seach_AFFIRM_FLAG" name="seach_AFFIRM_FLAG_NAME" parentNo="14014304" selected="${AFFIRM_FLAG}"  selectedNm="${AFFIRM_FLAG_NAME}"/>
						 </c:if>
						 <c:if test="${LoginUser.cpnyId eq 'SST'}">
					      <ait:selectCodeMulti id="seach_AFFIRM_FLAG" name="seach_AFFIRM_FLAG_NAME" parentNo="14014304" selected="${AFFIRM_FLAG}"  selectedNm="${AFFIRM_FLAG_NAME}"/>
						 </c:if>
					</td>
				</tr>
				<tr>
					<td width="10%">员工类型</td>
						<td width="20%">
				 	<ait:SelectSyCodeByCpnyID id="seach_EMP_TYPE_CODE" name="seach_EMP_TYPE_CODE" parentNo="13864" selected="${EMP_TYPE_CODE}" cnpyID="${LoginUser.cpnyId}" limit="all"/>
						</td>
					<td width="30%">任职状态
				 	<ait:SelectSyCodeByCpnyID id="seach_EmpOffice" name="seach_EmpOffice" parentNo="15118" selected="${EmpOffice}" cnpyID="${LoginUser.cpnyId}" limit="all"/>
					</td>
					<td width="30%">班组 
						<ait:SelectSyCodeByCpnyID id="seach_GROUP_ID" name="seach_GROUP_ID" parentNo="400223" selected="${GROUP_ID}" cnpyID="${LoginUser.cpnyId}" limit="all"/> 
					</td>
				</tr>
				<tr>
				<c:if test="${authority ne '1'}">
				   <td>
						部门
					</td>
					<td>
						${personInfo.DEPARTMENT }
					</td>
				</c:if>
				<c:if test="${authority eq '1'}">
				     <td><!-- 部门： --> <spring:message
						code="hr.viewPersonalInfo.title.DEPTNAME" /> 
					</td>
					<td>
						<ait:deptList name="seach_DEPTNO" cpnyId="${defaultCpny}" limit="ar" id="viewApplyAttenanceManagentInfoList_seachDept" selected="${DEPTNO}"/>
						<ait:deptTreeIcon name="seach_DEPTNO" cpnyId="${defaultCpny}" limit="ar" id="viewApplyAttenanceManagentInfoList_seachDept" selected="${DEPTNO}"/>
					</td>
				</c:if>
					 <td width="30%">
						考勤&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				   <c:if test="${defaultCpny eq 'SST'}">
					   <select name="seach_ITEM_NO" id="seach_ITEM_NO" >
					      <option value="">请选择</option>
						<c:forEach items="${itemList}" var="item">
								<option value="${item.ITEM_NO}" 
										<c:if test="${item.ITEM_NO eq ITEM_NO}">selected</c:if>	>
									        ${item.ITEM_NAME}
									</option>
								</c:forEach>
						</select>
				   </c:if>
				   <c:if test="${defaultCpny ne 'SST'}">
					   <ait:selectCodeMulti id="seach_APPLY_CODE" name="seach_APPLY_CODE_NAME" parentNo="21" selected="${APPLY_CODE}"  selectedNm="${APPLY_CODE_NAME}"/>
				   </c:if>
				   </td>
				<td>
				<input type="checkbox" name="seach_attenState" value="all" checked="checked"/> ALL
				</td>
			   </tr>
			</table>
		</div>
	</form>
</div>

<div>
  <table>
  <tr>
    <td id="openApplyLeave" style="display:none">
       &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src="/resources/images/+.gif" title="打开" border="0" align="absmiddle" style="cursor:hand" onclick="xiujialeixing(1)"/>
    </td>
    <td id="closeApplyLeave" >
       &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src="/resources/images/-.gif" title="关闭" border="0" align="absmiddle" style="cursor:hand" onclick="xiujialeixing(0)"/>
    </td>
   </tr>
  </table>
</div>
<div id="viewApplyAttenBatch" class="pageHeader" >
    <div class="searchBar">
			<table class="searchContent">
			    <tr>
			       <td width="10%">
						考勤
				   </td>
				   <td width="20%" class="td_type">
				   <c:if test="${defaultCpny eq 'SST'}">
					   <select name="ITEM_NO" id="ITEM_NO" >
					      <option value="">请选择</option>
						<c:forEach items="${itemList}" var="item">
								<option value="${item.ITEM_NO}" 
										<c:if test="${item.ITEM_NO eq ITEM_NO1}">selected</c:if>	>
									        ${item.ITEM_NAME}
									</option>
								</c:forEach>
						</select>
				   </c:if>
				   <c:if test="${defaultCpny ne 'SST'}">
					   <select name="ITEM_NO" id="ITEM_NO" >
					        <option value="">请选择</option>
							<c:forEach items="${itemList}" var="item">
								<option value="${item.ITEM_NO}" 
									<c:if test="${item.ITEM_NO eq ITEM_NO1}">selected</c:if>	>
									       ${item.ITEM_NAME}
								</option>
							</c:forEach>
					</select>
				   </c:if>
				   </td>
				   <td width="30%">
				               时间&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				          <input type="text" name="FROM_DATE" id="FROM_DATE"  class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="${FROM_DATE1}"/>
						 <input type="text" size="4" id="fromTime" name="fromTime" value="${fromTime1}"/>
				   </td >
				   <td width="25%">
				   		<input type="text" name="TO_DATE" id="TO_DATE"  class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="${TO_DATE1}"/>
						 <input type="text" size="4" id="toTime" name="toTime" value="${toTime1}"/>
				   </td>
			    </tr>
				<tr>
					<td width="10%">原因</td>
					<td width="25%">
						<input type="text" id="reason" name="reason"  value="${otherReason1 }"/>
					</td >
					<td width="25%">
					     审批状态 &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;: &nbsp;&nbsp; &nbsp;&nbsp;&nbsp;<ait:SelectSyCodeCombinByCpnyID name="FILLAFFIRMFLAG" combinParentNo="14014304" selected="${FILLAFFIRMFLAG1}"  cnpyID="${LoginUser.cpnyId}"  limit="all"/>
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul class="toolBar">
	             <li>
	             <a class="buttonActive" onclick="fillItem();"><span>全部反应</span></a>
	             </li>
				</ul>
			</div>
	</div>
</div>
<div class="pageContent" >
<div class="formBar">
    <div style="font: 12px/ 20px arial, sans-serif; float: left; height: 30px; line-height: 30px;">Total:${fn:length(leaveAffirmList)+fn:length(leaveAffirmListForMoreDay)+ fn:length(nullLeaveAffirmList)}</div>
    <c:if test="${LoginUser.cpnyId eq 'TSTO'}">
    <a  href="#"  id="openadjust" rel="ping" target="dialog" mask="true" width="600" height="400" style="margin-left: 346px;" title="加班申请记录">
    </c:if>
	<ul class="toolBar">
	    <li><a class="buttonActive" id="viewApplyAttenanceManagentInfoList_Serch" href="#"><span>查询</span></a></li>
	    <li><a class="buttonActive" onclick="delLeaveApplyCallbackBatch(2,'delLeaveApplyAffirmFormBatch',DWZ.ajaxDone)"><span>添加</span></a></li>
		<li><a class="buttonActive" onclick="delLeaveApplyCallbackBatch(0,'delLeaveApplyAffirmFormBatch',DWZ.ajaxDone)"><span>删除</span></a></li>
		<li><a class="buttonActive" onclick="delLeaveApplyCallbackBatch(1,'delLeaveApplyAffirmFormBatch',DWZ.ajaxDone)"><span>保存</span></a></li>
		<c:if test="${LoginUser.cpnyId eq 'TSTO'}">
		<li><a class="buttonActive" onclick="downloadExcel('viewApplyAttenanceManagentInfoList','/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=129&CPNY=${LoginUser.cpnyId}','/ar/attendanceMintenance/viewApplyAttenanceManagentInfoList?firstFlag=N')"><span>导出到EXECL</span></a></li>
		</c:if>
		<c:if test="${LoginUser.cpnyId eq 'SST'}">
		<li><a class="buttonActive" onclick="downloadExcel('viewApplyAttenanceManagentInfoList','/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=204&CPNY=${LoginUser.cpnyId}','/ar/attendanceMintenance/viewApplyAttenanceManagentInfoList?firstFlag=N')"><span>导出到EXECL</span></a></li>
		</c:if>
	</ul>
</div >
	<form name="delLeaveApplyAffirmForm" id="delLeaveApplyAffirmFormBatch" method="post" action="/ar/attendanceMintenance/delAttendanceApplyInBatchForBatch" 
	  onsubmit="return delLeaveApplyCallbackBatch(this, navTabAjaxDone);">     
		<table class="orderList" width="100%">                                           
			<thead>
				<tr>
				    <th width="1%" ><!--NO-->
						NO
					</th>
					<th width="1%" >
				    	<input type="checkbox" class="checkboxCtrl" group="BATCH_LEAVE" />
				    </th>
				    <th  width="1%" style="text-align: center"><!--状态-->
						状态
					</th>
					<th  width="3%" style="text-align: center;"><!--申请人-->
						姓名
					</th>
				    <th style="text-align: center"><!--社号-->
						社号
					</th>
					<th  width="3%"  style="text-align: center"><!--部门-->
						部门
					</th>
					<th width="3%" style="text-align: center"><!--年假天数-->
						年假天数
					</th>
					<th width="3%" style="text-align: center"><!--年假剩余-->
						年假剩余
					</th>
					<th width="3%"><!--本月调休-->
						调休总数
					</th>
					<th width="3%" style="text-align: center"><!--调休剩余-->
						调休剩余
					</th>
					<th width="3%" style="text-align: center"><!--班组-->
						班组
					</th>
					<th width="3%" style="text-align: center"><!--班次-->
						工作形态
					</th>
					<th width="3%" style="text-align: center"><!--进门时间-->
						进门时间
					</th>
					<th width="3%" style="text-align: center"><!--出门时间-->
						出门时间
					</th>
					<th  style="text-align: center;width:3%"><!--类型-->
						类型
					</th>
					<th style="text-align: center"><!--考勤状态-->
						考勤状态
					</th>
					<th style="text-align: center"><!--班次-->
						开始日期
					</th>
					<th style="text-align: center"><!--结束日期-->
						结束日期
					</th>
					<th style="text-align: center"><!--开始时间-->
						开始时间
					</th>
					<th style="text-align: center"><!--结束时间-->
						结束时间
					</th>
					<th style="text-align: center;width=2%"><!--时长-->
						时长
					</th>
					<th style="text-align: center" width="5%"><!--原因-->
						原因
					</th>
					<th style="text-align: center;"><!--审批状态-->
						审批状态
					</th>
					<th style="text-align: center;" width="4%"><!--输入者-->
						输入者
					</th>
					<th style="text-align: center;width:6%"><!--创建时间-->
						创建时间
					</th>
					<th style="text-align: center;" width="4%"><!--修改者-->
						修改者
					</th>
					<th style="text-align: center;width:6%"><!--修改时间-->
						修改时间
					</th>
				</tr>
			</thead>
			<tbody>
			    <!-- null的 start-->
			       <c:forEach items="${nullLeaveAffirmList}" var="leaveApply" varStatus="i">
					<tr target="sid" rel="${admin.personId}" >
					    <td style="text-align: center">${i.count}</td>
					    <td style="text-align: center">
					        <input type="checkbox" id="BATCH_LEAVE${leaveApply.APPLY_NO}" name="BATCH_LEAVE" value="${leaveApply.APPLY_NO}" />
					    </td>
						     <input id="DATA_TYPETEXT${leaveApply.APPLY_NO}" name="DATE_TYPE${leaveApply.APPLY_NO}" type="hidden"  value=""/>
						     <input id="UNIT${leaveApply.APPLY_NO}" name="UNIT${leaveApply.APPLY_NO}" type="hidden"  value="${leaveApply.UNIT}"/>
						     <input id="STATUS_CODETEXT${leaveApply.APPLY_NO}" name="STATUS_CODE${leaveApply.APPLY_NO}" type="hidden"  value=""/>
						     <input id="STATUS_NAMETEXT${leaveApply.APPLY_NO}" name="STATUS_NAME${leaveApply.APPLY_NO}" type="hidden"  value=""/>
						     <input id="IWEEKTEXT${leaveApply.APPLY_NO}" name="IWEEK${leaveApply.APPLY_NO}" type="hidden"  value=""/>
						     <input id="POST_GRADE_NOTEXT${leaveApply.APPLY_NO}" name="POST_GRADE_NO${leaveApply.APPLY_NO}" type="hidden"  value=""/>
						     <input id="AR_MONTH_STRTEXT${leaveApply.APPLY_NO}" name="AR_MONTH_STR${leaveApply.APPLY_NO}" type="hidden"  value=""/>
					    <td style="text-align: center">
					         <input id="LOCK_YN${leaveApply.APPLY_NO}" name="LOCK_YN${leaveApply.APPLY_NO}" type="hidden"  value="N"/>
					         <input id="CONFIRM_FLAG${leaveApply.APPLY_NO}" name="CONFIRM_FLAG${leaveApply.APPLY_NO}" type="hidden"  value="${leaveApply.CONFIRM_FLAG}"/>
					    </td>
					    <td style="text-align: center">
	                        <input type="text" name="LOCAL_NAME" id="dwz.person.EMPINFOApplyLeave${leaveApply.APPLY_NO}"
								value="${KEY}" style="float:left;" size="4"onclick="eventfunction(this);"
							onkeydown="javascript:if(event.keyCode == 13)addEmpPop_ar0200(this,'onkeyup');" />
							<input id="dwz.person.AFFIRMOR_IDApplyLeave${leaveApply.APPLY_NO}" onpropertychange="eventfunction('dwz.person.EMPINFOApplyLeave${leaveApply.APPLY_NO}');"
							name="personid${leaveApply.APPLY_NO}" type="hidden"  size="8"  lookupGroup="person" />
							<a id="addEmpPop_ar0200" 
							onclick="addEmpPop_ar0200()" href="#" lookupGroup="person"> </a>
					    </td>
					    <td style="text-align: center">
					        <div id="EMPIDTEXT${leaveApply.APPLY_NO}"> </div>
					    </td>
					    <td style="text-align: center">
					      <div id="DEPTNONAMETEXT${leaveApply.APPLY_NO}"> </div>
						<input id="DEPTNOTEXT${leaveApply.APPLY_NO}" name="DEPTNO${leaveApply.APPLY_NO}" type="hidden"  value=""/>
					    </td>
					    <td style="text-align: center">
					     <div id="TOT_VAC_CNTText${leaveApply.APPLY_NO}" ></div>
					    </td>
					    <td style="text-align: center">
					       <div id="SHENGYU_VAC_CNTText${leaveApply.APPLY_NO}" ></div>
					    </td>
					     <td style="text-align: center">
					     <div id="TOTAL_TXText${leaveApply.APPLY_NO}" ></div>
					    </td>
					    <td style="text-align: center">
					       <div id="USE_TXText${leaveApply.APPLY_NO}" ></div>
					    </td>
					    <td style="text-align: center">
					    <div id="GROUP_NAMEText${leaveApply.APPLY_NO}"> </div>
						<input id="GROUP_IDTEXT${leaveApply.APPLY_NO}" name="GROUP_ID${leaveApply.APPLY_NO}" type="hidden"  value=""/>
					    </td>
					    <td style="text-align: center" title="${leaveApply.SHIFNAME}" >
					     <div id="WORK_TIMETEXT${leaveApply.APPLY_NO}"> </div>
					    </td>
					    <input id="SHIFT_NO${leaveApply.APPLY_NO}" name="SHIFT_NO${leaveApply.APPLY_NO}" type="hidden"  value=""/>
					    <td style="text-align: center">
					      <div id="INDOOR_DATETEXT${leaveApply.APPLY_NO}"> </div>
					    </td>
					   <td style="text-align: center">
					     <div id="OUTDOOR_DATETEXT${leaveApply.APPLY_NO}"> </div>
					    </td>
					    <td style="text-align: center">
					     <div id="TYPE_NAMETEXT${leaveApply.APPLY_NO}"> </div>
					    </td>
					    <td style="text-align: center;">
							<input id="valibl_input_ITEM_NO${leaveApply.APPLY_NO}" type="text" size="6" value="${leaveApply.ITEM_NAME}" 
								onfocus="$('#valibl_pop_ITEM_NO${leaveApply.APPLY_NO}').css('display', 'block');" readonly="readonly"/>
							<input id="valibl_value_ITEM_NO${leaveApply.APPLY_NO}" name="ITEM_NO${leaveApply.APPLY_NO}" type="hidden" value="${leaveApply.ITEM_NO}" />
							 <div id="valibl_pop_ITEM_NO${leaveApply.APPLY_NO}"
								onmouseover="valibl_mouseover_item_pop('ITEM_NO${leaveApply.APPLY_NO}')" 
								onclick="$('#valibl_pop_ITEM_NO${leaveApply.APPLY_NO}').css('display', 'none');"class="deptContent"
								style="display: none;height: 200px;width: 80px;margin-left:-5px;">
								<div class="ztree_dept" style="height: 200px;width: 80px;overflow:auto;overflow-x:hidden;">
									<div class="ztree_dept_title">
										<table width="100%">
											<tr>
												<th>考勤状态</th>
											</tr>
										</table>
									</div>
									<div class="ztree_dept_type" style="height: 75%">
										<ul class="ztree_dept_table">
											<c:forEach items="${itemList}" var="item" varStatus="i">
												<c:choose>
													<c:when test="${item.ITEM_NO == 141444 || item.ITEM_NO == 141445 || item.ITEM_NO == 141446 }"></c:when>
													<c:when test="${i.count % 2 == 0 }">
														<li class="deptTreeLi" 
														onclick="$('#valibl_input_ITEM_NO${leaveApply.APPLY_NO}').attr('value','${item.ITEM_NAME}');
														$('#valibl_value_ITEM_NO${leaveApply.APPLY_NO}').attr('value','${item.ITEM_NO}');
														$('#valibl_pop_ITEM_NO${leaveApply.APPLY_NO}').css('display', 'none');
														eventfunction('dwz.person.EMPINFOApplyLeave${leaveApply.APPLY_NO}');
														openAJDialog(${leaveApply.APPLY_NO});
														$('#BATCH_LEAVE${leaveApply.APPLY_NO}').attr('checked','checked');"><span>${item.ITEM_NAME}</span></li>
													</c:when>
													<c:otherwise>
														<li onclick="$('#valibl_input_ITEM_NO${leaveApply.APPLY_NO}').attr('value','${item.ITEM_NAME}');
														$('#valibl_value_ITEM_NO${leaveApply.APPLY_NO}').attr('value','${item.ITEM_NO}');
														$('#valibl_pop_ITEM_NO${leaveApply.APPLY_NO}').css('display', 'none');
														eventfunction('dwz.person.EMPINFOApplyLeave${leaveApply.APPLY_NO}');
														openAJDialog(${leaveApply.APPLY_NO});
														$('#BATCH_LEAVE${leaveApply.APPLY_NO}').attr('checked','checked');"><span>${item.ITEM_NAME}</span></li>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</ul>
									</div>
									<div class="ztree_dept_color">
										<a href="#" onclick="$('#valibl_pop_ITEM_NO${leaveApply.APPLY_NO}').css('display', 'none');"
											class="ztree_dept_color_a"> <span>关闭</span>
										</a>
									</div>
								</div>
							</div>
					    </td>
					    <td style="text-align: center;">
						     <input  size="10" type="text" name="FROM_DATE${leaveApply.APPLY_NO}" id="FROM_DATE${leaveApply.APPLY_NO}"  class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',onpicked:callength_date})" value="${leaveApply.FROM_DATE}" />
						     <input   type="hidden" name="oldFROM_DATE${leaveApply.APPLY_NO}" id="oldFROM_DATE${leaveApply.APPLY_NO}"  value="${leaveApply.FROM_DATE}" />
							 <input type="hidden" id="is_equal_from_time${leaveApply.APPLY_NO}" name="is_equal_from_time" value=""/>
					    </td>
					    <td style="text-align: center;">
						    <input size="10" type="text" name="TO_DATE${leaveApply.APPLY_NO}" id="TO_DATE${leaveApply.APPLY_NO}"  class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',onpicked:callength_date})" value="${leaveApply.TO_DATE}"/>
						    <input  type="hidden" name="oldTO_DATE${leaveApply.APPLY_NO}" id="oldTO_DATE${leaveApply.APPLY_NO}"   value="${leaveApply.TO_DATE}"/>
							<input type="hidden" id="is_equal_to_time${leaveApply.APPLY_NO}" name="is_equal_to_time" value=""/>
					    </td>
					    <td style="text-align: center">
					         <input type="text" size="3" id="fromTime${leaveApply.APPLY_NO}" name="fromTime${leaveApply.APPLY_NO}" value="${leaveApply.FROM_TIME}" onkeyup="callength(event,${leaveApply.APPLY_NO});"/>
					         <input type="hidden" id="fromTime_first${leaveApply.APPLY_NO}" name="fromTime_first${leaveApply.APPLY_NO}" value="${leaveApply.fromTime_first}" />
					    </td>
					    <td style="text-align: center">
					         <input type="text" size="3" id="toTime${leaveApply.APPLY_NO}" name="toTime${leaveApply.APPLY_NO}" value="${leaveApply.TO_TIME}"  onkeyup="callength(event,${leaveApply.APPLY_NO});"/>
					         <input type="hidden" id="toTime_first${leaveApply.APPLY_NO}" name="toTime_first${leaveApply.APPLY_NO}" value="${leaveApply.toTime_first}" />
					    </td>
					    <td style="text-align: center">
					        <div id="shenqingshichangText${leaveApply.APPLY_NO}">${leaveApply.APPLY_LENGTH} </div>
							<input type="hidden" id="shenqingshichang${leaveApply.APPLY_NO}" name="APPLY_LENGTH${leaveApply.APPLY_NO}" value="${leaveApply.APPLY_LENGTH2}" />
							 <c:if test="${LoginUser.cpnyId eq 'TSTO'}">
					           <input id="ajSeq${leaveApply.APPLY_NO}" name="ajSeq${leaveApply.APPLY_NO}" value="0"  type="hidden" />
					           <input id="ajQUANTITY${leaveApply.APPLY_NO}" name="ajQUANTITY${leaveApply.APPLY_NO}" value="0"  type="hidden" />
					       </c:if>
					    </td>
					    <td style="text-align: center">
					       <input id="reason${leaveApply.APPLY_NO}" name="reason${leaveApply.APPLY_NO}" value="${leaveApply.LEAVEREASON}"  onkeyup="$('#BATCH_LEAVE${leaveApply.APPLY_NO}').attr('checked','checked');" type="text" size="20"/>
					    </td>
					    <td style="text-align: center">
							<input id="valibl_input_AFFIRM_NO${leaveApply.APPLY_NO}" type="text" size="6" value="${leaveApply.AFFIRM_NAME}"
								onfocus="$('#valibl_pop_AFFIRM_NO${leaveApply.APPLY_NO}').css('display', 'block');" readonly="readonly"/>
							<input id="valibl_value_AFFIRM_NO${leaveApply.APPLY_NO}" name="AFFIRM_FLAG${leaveApply.APPLY_NO}" type="hidden" value="${leaveApply.AFFIRM_FLAG}" />
							 <div id="valibl_pop_AFFIRM_NO${leaveApply.APPLY_NO}"
								onmouseover="valibl_mouseover_item_pop('AFFIRM_NO${leaveApply.APPLY_NO}')" 
								onclick="$('#valibl_pop_AFFIRM_NO${leaveApply.APPLY_NO}').css('display', 'none');"class="deptContent"
								style="display: none;height: 200px;width: 80px;margin-left:-20px;">
								<div class="ztree_dept" style="height: 200px;width: 80px;overflow:auto;overflow-x:hidden;">
									<div class="ztree_dept_title">
										<table width="100%">
											<tr>
												<th>审批状态</th>
											</tr>
										</table>
									</div>
									<div class="ztree_dept_type" style="height: 75%">
										<ul class="ztree_dept_table">
											<c:forEach items="${codeList}" var="item" varStatus="i">
												<c:choose>
													<c:when test="${i.count % 2 == 0 }">
														<li style="width: 300px" class="deptTreeLi" 
														onclick="$('#valibl_input_AFFIRM_NO${leaveApply.APPLY_NO}').attr('value','${item.CODE_NAME}');
														$('#valibl_value_AFFIRM_NO${leaveApply.APPLY_NO}').attr('value','${item.CODE_NO}');
														$('#BATCH_LEAVE${leaveApply.APPLY_NO}').attr('checked','checked');
														$('#valibl_pop_AFFIRM_NO${leaveApply.APPLY_NO}').css('display', 'none');"><span>${item.CODE_NAME}</span></li>
													</c:when>
													<c:otherwise>
														<li style="width: 300px" onclick="$('#valibl_input_AFFIRM_NO${leaveApply.APPLY_NO}').attr('value','${item.CODE_NAME}');
														$('#valibl_value_AFFIRM_NO${leaveApply.APPLY_NO}').attr('value','${item.CODE_NO}');
														$('#BATCH_LEAVE${leaveApply.APPLY_NO}').attr('checked','checked');
														$('#valibl_pop_AFFIRM_NO${leaveApply.APPLY_NO}').css('display', 'none');"><span>${item.CODE_NAME}</span></li>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</ul>
									</div>
									<div class="ztree_dept_color">
										<a href="#" onclick="$('#valibl_pop_AFFIRM_NO${leaveApply.APPLY_NO}').css('display', 'none');"
											class="ztree_dept_color_a"> <span>关闭</span>
										</a>
									</div>
								</div>
							</div>
					    </td>
					    <td >
					    </td>
					    <td>
					    </td>
					    <td>
					    </td>
					    <td>
					    </td>
					</tr>
				</c:forEach>
				<c:forEach items="${leaveAffirmList}" var="leaveApply" varStatus="i">
					<tr target="sid" rel="${admin.personId}" 
						onclick="$(this).attr('class').indexOf('selected')!=-1?$(this).removeClass('selected'):$(this).addClass('selected');"
							<c:if test="${leaveApply.AFFIRM_FLAG eq '14014307' || leaveApply.AFFIRM_FLAG eq '14014311'}">style="color: blue;"</c:if>
							<c:if test="${leaveApply.AFFIRM_FLAG eq '14014309' || leaveApply.AFFIRM_FLAG eq '14014310'}">style="color: red;"</c:if>
						>
					    <td style="text-align: center">${fn:length(leaveAffirmListForMoreDay)+ fn:length(nullLeaveAffirmList)+ i.count}</td>
					    <td style="text-align: center">
					        <input type="checkbox" id="BATCH_LEAVE${leaveApply.APPLY_NO}" name="BATCH_LEAVE" value="${leaveApply.APPLY_NO}" />
					    </td>
					     <input id="DATE_TYPE${leaveApply.APPLY_NO}" name="DATE_TYPE${leaveApply.APPLY_NO}" type="hidden"  value="${leaveApply.DATE_TYPE}"/>
					     <input id="UNIT${leaveApply.APPLY_NO}" name="UNIT${leaveApply.APPLY_NO}" type="hidden"  value="${leaveApply.UNIT}"/>
					     <input id="STATUS_CODE${leaveApply.APPLY_NO}" name="STATUS_CODE${leaveApply.APPLY_NO}" type="hidden"  value="${leaveApply.STATUS_CODE}"/>
					     <input id="STATUS_NAME${leaveApply.APPLY_NO}" name="STATUS_NAME${leaveApply.APPLY_NO}" type="hidden"  value="${leaveApply.STATUS_NAME}"/>
					     <input id="IWEEK${leaveApply.APPLY_NO}" name="IWEEK${leaveApply.APPLY_NO}" type="hidden"  value="${leaveApply.IWEEK}"/>
					     <input id="AR_MONTH_STR${leaveApply.APPLY_NO}" name="AR_MONTH_STR${leaveApply.APPLY_NO}" type="hidden"  value="${leaveApply.AR_MONTH_STR}"/>
					    <td style="text-align: center">
					      <input id="LOCK_YN${leaveApply.APPLY_NO}" name="LOCK_YN${leaveApply.APPLY_NO}" type="hidden"  value="${leaveApply.LOCK_YN}"/>
					        ${leaveApply.CONFIRM_FLAG}
					    </td>
					    <td style="text-align: center">
	                         <a href="/ess/viewDept/viewApplyDeptPersonalInfo?EMPID=${leaveApply.EMPID}&LOCAL_NAME= ${leaveApply.LOCAL_NAME}" target="dialog" style="color: blue;" title="考勤个人信息"   [ mask=true ] width="1000" height="300"> 
	                         ${leaveApply.LOCAL_NAME}
	                         <input id="dwz.person.EMPINFOApplyLeave${leaveApply.APPLY_NO}"  type="hidden"  value=" ${leaveApply.LOCAL_NAME}"/>
					    </a><input id="dwz.person.AFFIRMOR_IDApplyLeave${leaveApply.APPLY_NO}" name="personid${leaveApply.APPLY_NO}" type="hidden"  value="${leaveApply.PERSON_ID}"/>
					    </td>
					    <td style="text-align: center">
					    <a href="/ess/viewDept/viewApplyDeptPersonalInfo?EMPID=${leaveApply.EMPID}&LOCAL_NAME= ${leaveApply.LOCAL_NAME}" target="dialog" style="color: blue;" title="考勤个人信息"   [ mask=true ] width="1000" height="300">${leaveApply.EMPID}</a>
					    </td>
					    <td style="text-align: center" title=" ${leaveApply.DEPARTMENT}">
					    <input id="DEPTNO${leaveApply.APPLY_NO}" name="DEPTNO${leaveApply.APPLY_NO}" type="hidden"  value="${leaveApply.DEPTNO}"/>
					     ${fn:substring(leaveApply.DEPARTMENT,0,2)}...
					    </td>
					    <td style="text-align: center">${leaveApply.TOT_VAC_CNT}</td>
					    <td style="text-align: center">${leaveApply.SHENGYU_VAC_CNT}</td>
					    <c:if test="${LoginUser.cpnyId eq 'SST'}">
					     <td style="text-align: center">${leaveApply.TOTAL_TX/8}</td>
					    <td style="text-align: center">${leaveApply.TOTAL_TX/8-leaveApply.USE_TX/8}</td>
					    </c:if>
					    <c:if test="${LoginUser.cpnyId eq 'TSTO'}">
					     <td style="text-align: center">${leaveApply.TOTAL_TX}</td>
					    <td style="text-align: center">${leaveApply.TOTAL_TX-leaveApply.USE_TX}</td>
					    </c:if>
					    <td style="text-align: center" title="${leaveApply.GROUPNAME}">
					        ${fn:substring(leaveApply.GROUPNAME,0,2)}...
					     </td>
					     <input id="GROUP_ID${leaveApply.APPLY_NO}" name="GROUP_ID${leaveApply.APPLY_NO}" type="hidden"  value="${leaveApply.GROUP_ID}"/>
					    <td style="text-align: center" title="${leaveApply.FROM_TIME_FIRST}-${leaveApply.TO_TIME_FIRST}" >${leaveApply.FROM_TIME_FIRST}...</td>
					    <input id="SHIFT_NO${leaveApply.APPLY_NO}" name="SHIFT_NO${leaveApply.APPLY_NO}" type="hidden"  value="${leaveApply.SHIFT_NO}"/>
					    <td style="text-align: center">
					       ${leaveApply.INDOOR_DATE}
					    </td>
					   <td style="text-align: center">
					      ${leaveApply.OUTDOOR_DATE}
					    </td>
					    <td style="text-align: center" >
					     ${leaveApply.TYPENAME}
					    </td>
					    <td style="text-align: center;">
							<c:if test="${leaveApply.ITEM_NO eq '141439' || leaveApply.ITEM_NO eq '141440' }">
							   <input id="valibl_input_ITEM_NO${leaveApply.APPLY_NO}" type="text" size="6" value="" 
								onfocus="$('#valibl_pop_ITEM_NO${leaveApply.APPLY_NO}').css('display', 'block');" readonly="readonly"/>
							</c:if>
							<c:if test="${leaveApply.ITEM_NO ne '141439' && leaveApply.ITEM_NO ne '141440' }">
							   <input id="valibl_input_ITEM_NO${leaveApply.APPLY_NO}" type="text" size="6" value="${leaveApply.ITEM_NAME}" 
								onfocus="$('#valibl_pop_ITEM_NO${leaveApply.APPLY_NO}').css('display', 'block');" readonly="readonly"/>
							</c:if>
							<input id="valibl_value_ITEM_NO${leaveApply.APPLY_NO}" name="ITEM_NO${leaveApply.APPLY_NO}" type="hidden" value="${leaveApply.ITEM_NO}" />
							 <div id="valibl_pop_ITEM_NO${leaveApply.APPLY_NO}"
								onmouseover="valibl_mouseover_item_pop('ITEM_NO${leaveApply.APPLY_NO}')" 
								onclick="$('#valibl_pop_ITEM_NO${leaveApply.APPLY_NO}').css('display', 'none');"class="deptContent"
								style="display: none;height: 200px;width: 80px;margin-left:-5px;">
								<div class="ztree_dept" style="height: 200px;width: 80px;overflow:auto;overflow-x:hidden;">
									<div class="ztree_dept_title">
										<table width="100%">
											<tr>
												<th>考勤状态</th>
											</tr>
										</table>
									</div>
									<div class="ztree_dept_type" style="height: 75%">
										<ul class="ztree_dept_table">
											<c:forEach items="${itemList}" var="item" varStatus="i">
												<c:choose>
													<c:when test="${item.ITEM_NO == 141444 || item.ITEM_NO == 141445 || item.ITEM_NO == 141446 }"></c:when>
													<c:when test="${i.count % 2 == 0 }">
														<li class="deptTreeLi" 
														onclick="$('#valibl_input_ITEM_NO${leaveApply.APPLY_NO}').attr('value','${item.ITEM_NAME}');
														$('#valibl_value_ITEM_NO${leaveApply.APPLY_NO}').attr('value','${item.ITEM_NO}');
														$('#BATCH_LEAVE${leaveApply.APPLY_NO}').attr('checked','checked');
														openAJDialog(${leaveApply.APPLY_NO});
														$('#valibl_pop_ITEM_NO${leaveApply.APPLY_NO}').css('display', 'none');"><span>${item.ITEM_NAME}</span></li>
													</c:when>
													<c:otherwise>
														<li onclick="$('#valibl_input_ITEM_NO${leaveApply.APPLY_NO}').attr('value','${item.ITEM_NAME}');
														$('#valibl_value_ITEM_NO${leaveApply.APPLY_NO}').attr('value','${item.ITEM_NO}');
														$('#BATCH_LEAVE${leaveApply.APPLY_NO}').attr('checked','checked');
														openAJDialog(${leaveApply.APPLY_NO});
														$('#valibl_pop_ITEM_NO${leaveApply.APPLY_NO}').css('display', 'none');"><span>${item.ITEM_NAME}</span></li>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</ul>
									</div>
									<div class="ztree_dept_color">
										<a href="#" onclick="$('#valibl_pop_ITEM_NO${leaveApply.APPLY_NO}').css('display', 'none');"
											class="ztree_dept_color_a"> <span>关闭</span>
										</a>
									</div>
								</div>
							</div>
					    </td>
					   <td style="text-align: center;">
						    <input size="10" type="text" name="FROM_DATE${leaveApply.APPLY_NO}" id="FROM_DATE${leaveApply.APPLY_NO}"  class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',onpicked:callength_date})" value="${leaveApply.FROM_DATE}" />
						     <input  type="hidden" name="oldFROM_DATE${leaveApply.APPLY_NO}" id="oldFROM_DATE${leaveApply.APPLY_NO}"  value="${leaveApply.FROM_DATE}" />
							<input type="hidden" id="is_equal_from_time${leaveApply.APPLY_NO}" name="is_equal_from_time" value=""/>
					    </td>
					    <td style="text-align: center;">
						    <input size="10" type="text" name="TO_DATE${leaveApply.APPLY_NO}" id="TO_DATE${leaveApply.APPLY_NO}"  class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',onpicked:callength_date})" value="${leaveApply.TO_DATE}" />
						      <input  type="hidden" name="oldTO_DATE${leaveApply.APPLY_NO}" id="oldTO_DATE${leaveApply.APPLY_NO}" value="${leaveApply.TO_DATE}" />
							<input type="hidden" id="is_equal_to_time${leaveApply.APPLY_NO}" name="is_equal_to_time" value=""/>
					    </td>
					    <td style="text-align: center">
					         <input type="text" size="3" id="fromTime${leaveApply.APPLY_NO}" name="fromTime${leaveApply.APPLY_NO}" value="${leaveApply.FROM_TIME}" onkeyup="callength(event,${leaveApply.APPLY_NO});"/>
					          <input type="hidden" size="3" id="oldfromTime${leaveApply.APPLY_NO}" name="oldfromTime${leaveApply.APPLY_NO}" value="${leaveApply.TO_TIME}" />
					         <input type="hidden" id="fromTime_first${leaveApply.APPLY_NO}" name="fromTime_first${leaveApply.APPLY_NO}" value="${leaveApply.fromTime_first}" />
					    </td>
					    <td style="text-align: center">
					         <input type="text" size="4" id="toTime${leaveApply.APPLY_NO}" name="toTime${leaveApply.APPLY_NO}" value="${leaveApply.TO_TIME}"  onkeyup="callength(event,${leaveApply.APPLY_NO});"/>
					         <input type="hidden"  id="oldtoTime${leaveApply.APPLY_NO}" name="oldtoTime${leaveApply.APPLY_NO}" value="${leaveApply.TO_TIME}" />
					         <input type="hidden" id="toTime_first${leaveApply.APPLY_NO}" name="toTime_first${leaveApply.APPLY_NO}" value="${leaveApply.toTime_first}" />
					    </td>
					    <td style="text-align: center">
					        <div id="shenqingshichangText${leaveApply.APPLY_NO}">${leaveApply.APPLY_LENGTH} </div>
							<input type="hidden" id="shenqingshichang${leaveApply.APPLY_NO}" name="APPLY_LENGTH${leaveApply.APPLY_NO}" value="${leaveApply.APPLY_LENGTH2}" />
						
					    </td>
					    <td style="text-align: center">
					       <input id="reason${leaveApply.APPLY_NO}" name="reason${leaveApply.APPLY_NO}" value="${leaveApply.LEAVEREASON}"  onkeyup="$('#BATCH_LEAVE${leaveApply.APPLY_NO}').attr('checked','checked');" type="text" size="15"/>
					       <c:if test="${LoginUser.cpnyId eq 'TSTO'}">
					           <input id="ajSeq${leaveApply.APPLY_NO}" name="ajSeq${leaveApply.APPLY_NO}" value="0"  type="hidden" />
					           <input id="ajQUANTITY${leaveApply.APPLY_NO}" name="ajQUANTITY${leaveApply.APPLY_NO}" value="0"  type="hidden" />
					       </c:if>
					    </td>
					    <td style="text-align: center">
							<input id="valibl_input_AFFIRM_NO${leaveApply.APPLY_NO}" type="text" size="6"  title="${leaveApply.AFFIRM_NAME}" value="${leaveApply.AFFIRM_NAME}"
								onfocus="$('#valibl_pop_AFFIRM_NO${leaveApply.APPLY_NO}').css('display', 'block');" readonly="readonly"/>
							<input id="valibl_value_AFFIRM_NO${leaveApply.APPLY_NO}" name="AFFIRM_FLAG${leaveApply.APPLY_NO}" type="hidden" value="${leaveApply.AFFIRM_FLAG}" />
							 <div id="valibl_pop_AFFIRM_NO${leaveApply.APPLY_NO}"
								onmouseover="valibl_mouseover_item_pop('AFFIRM_NO${leaveApply.APPLY_NO}')" 
								onclick="$('#valibl_pop_AFFIRM_NO${leaveApply.APPLY_NO}').css('display', 'none');"class="deptContent"
								style="display: none;height: 200px;width: 100px;margin-left:-20px;">
								<div class="ztree_dept" style="height: 200px;width: 100px;overflow:auto;overflow-x:hidden;">
									<div class="ztree_dept_title">
										<table width="100%">
											<tr>
												<th>审批状态</th>
											</tr>
										</table>
									</div>
									<div class="ztree_dept_type" style="height: 75%">
										<ul class="ztree_dept_table">
											<c:forEach items="${codeList}" var="item" varStatus="i">
												<c:choose>
													<c:when test="${i.count % 2 == 0 }">
														<li style="width: 300px" class="deptTreeLi" 
														onclick="$('#valibl_input_AFFIRM_NO${leaveApply.APPLY_NO}').attr('value','${item.CODE_NAME}');
														$('#valibl_value_AFFIRM_NO${leaveApply.APPLY_NO}').attr('value','${item.CODE_NO}');
														$('#BATCH_LEAVE${leaveApply.APPLY_NO}').attr('checked','checked');
														$('#valibl_pop_AFFIRM_NO${leaveApply.APPLY_NO}').css('display', 'none');"><span>${item.CODE_NAME}</span></li>
													</c:when>
													<c:otherwise>
														<li style="width: 300px" onclick="$('#valibl_input_AFFIRM_NO${leaveApply.APPLY_NO}').attr('value','${item.CODE_NAME}');
														$('#valibl_value_AFFIRM_NO${leaveApply.APPLY_NO}').attr('value','${item.CODE_NO}');
														$('#BATCH_LEAVE${leaveApply.APPLY_NO}').attr('checked','checked');
														$('#valibl_pop_AFFIRM_NO${leaveApply.APPLY_NO}').css('display', 'none');"><span>${item.CODE_NAME}</span></li>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</ul>
									</div>
									<div class="ztree_dept_color">
										<a href="#" onclick="$('#valibl_pop_AFFIRM_NO${leaveApply.APPLY_NO}').css('display', 'none');"
											class="ztree_dept_color_a"> <span>关闭</span>
										</a>
									</div>
								</div>
							</div>
					    </td>
					    <td style="text-align: center" title="${leaveApply.CREATED_BY}&nbsp;[${leaveApply.CREATED_IP}]">
					       ${leaveApply.CREATED_BY}
					    </td>
					    <td style="text-align: center" title="${leaveApply.CREATE_DATE}">
					        ${fn:substring(leaveApply.CREATE_DATE,0,10)}
					    </td>
					    <td style="text-align: center" title="${leaveApply.UPDATED_BY}&nbsp;[${leaveApply.UPDATED_IP}]">
					      ${leaveApply.UPDATED_BY}
					    </td>
					    <td style="text-align: center" title="${leaveApply.UPDATE_DATE}">
					      ${fn:substring(leaveApply.UPDATE_DATE,0,10)}
					    </td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</form >   
</div>