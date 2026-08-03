<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<script>
//

$(document).ready(function(){	
	/**$("#viewArOvertimeSSTManagent_search").click(function(){
		  var KEY = $("#seach_KEY",navTab.getCurrentPanel()).val();
		  if(KEY != ""&& KEY != null){
		     $("#viewArOvertimeSSTManagent").attr("action","/ar/attendanceMintenance/viewArOvertimeSSTManagent?firstFlag=ADD");
		     var TO_DATE =  $("#seach_TO_DATE",navTab.getCurrentPanel()).val();
		     $("#seach_FROM_DATE",navTab.getCurrentPanel()).val(TO_DATE);
			 $("#viewArOvertimeSSTManagent").submit();
		  }
		  else{
		     delOtApplyCallback(2,'delOtApplyAffirmForm',DWZ.ajaxDone); 
		  }
	});*/
     
     
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
                "sProcessing": "正在加载中......",
                "sZeroRecords": "查询不到相关数据！",
                "sEmptyTable": "表中无数据存在！",
                "sSearch": "快速筛选"
            } //多语言配置
		});
});
	function delOtApplyCallback(OP_FLAG,form,callback) {
		
		$("#BATCH_LOT_OP_FLAG").val(OP_FLAG);
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
			var ids= document.getElementsByName("BATCH_OT");
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
		  var ids= document.getElementsByName("BATCH_OT");
		   var checked=false;
		  for(var i=0;i<ids.length;i++){
		 if(ids[i].checked){
		    var j = ids[i].value;
			var	AFFIRM_FLAG = $("#valibl_value_AFFIRM_NO"+ids[i].value).val();
			var	otlength = $("#shenqingshichang"+ids[i].value).val();
			var	old_otlength = $("#OLD_APPLY_LENGTH"+ids[i].value).val();
			var PERSON_ID = document.getElementById('dwz.person.AFFIRMOR_IDApplyLeave'+j).value;
			var LOCAL_NAME = document.getElementById('dwz.person.EMPINFOApplyLeave'+j).value;
			var fromTime = document.getElementById('fromTime'+ids[i].value).value;
			var toTime = document.getElementById('toTime'+ids[i].value).value;
		
			if(fromTime.length != 4){
				alertMsg.error('请输入合法的开始时间!');
				return false;
			}
		    if(toTime.length != 4){
				alertMsg.error('请输入合法的结束时间!');
				return false;
			}
		    if(otlength == 0){
				alertMsg.error('加班时长为0');
				return false;
			}
				
		    if(LOCAL_NAME == "" ){
				alertMsg.error('添加数据姓名为空,请搜索要添加的员工');
				return false;
			}else{
			   if(PERSON_ID == ""){
			      alertMsg.error('请搜索要添加的员工');
				 return false;
			   }
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
	  var applyBatchdate = applyDate ;
	    $form.attr("action","/ar/attendanceMintenance/delLOvertimeApplyInBatchSST?OP_FLAG="+OP_FLAG+"&APPLY_DATE="+applyBatchdate);
	    var msg = "确定要删除吗？";
	    if(OP_FLAG == 1){
	        var msg = "确定要提交吗？";
	    }
	    if(OP_FLAG == 2){
          var msg = "确定要添加吗？";
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
								navTabSearch(document.viewArOvertimeSSTManagent);
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
		 }else{
					$.ajax({
						type: form.method || 'POST',
						url:$form.attr("action"), 
						data:$form.serializeArray(),
						dataType:"json",
						cache: false,
						success: function(data){ //请求成功后处理函数。
						  //减少deleteYN,保证点击搜索 后删除null数据源
						   $("#viewArOvertimeSSTManagent").attr("action","/ar/attendanceMintenance/viewArOvertimeSSTManagent?firstFlag=N&nullYN=Y");
						   navTabSearch(document.viewArOvertimeSSTManagent);
				   	 	}  ,
						error: DWZ.ajaxError
					});
		 }
		return false;
	}
	
//

function xiujialeixing(id){
	
	if('0' == id){
		document.getElementById('viewApplyOtBatch').style.display = 'none';
		document.getElementById('closeApplyOt').style.display = 'none';
		document.getElementById('openApplyOt').style.display = 'block';
	}else{
		document.getElementById('viewApplyOtBatch').style.display = 'block';
		document.getElementById('closeApplyOt').style.display = 'block';
		document.getElementById('openApplyOt').style.display = 'none';
	}
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
 
 
 //html fill
 function htmlMuli(name,value){
  var ids = document.getElementsByName("BATCH_OT");
 
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
 function textMuli(name,value){
  var ids = document.getElementsByName("BATCH_OT");
 
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
 function checkYN(name,value){
  var ids = document.getElementsByName("BATCH_OT");
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
function selMuli(name,value){
 var ids = document.getElementsByName("BATCH_OT");
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
		var ids= document.getElementsByName("BATCH_OT");
		for(var i=0;i<ids.length;i++){
			if(ids[i].checked){
				checked=true;
			}
		}
		if(!checked){
			alertMsg.error('请选择反应记录'); 
			return false;
		}
  var CONFIRM_FLAG=document.getElementById("CONFIRM_FLAG").value;
  var ADJSTYN;
	if(document.getElementById("ADJSTYN").checked) {  
	ADJSTYN =document.getElementById("ADJSTYN").value ='1';
	}else{ 
     ADJSTYN=document.getElementById("ADJSTYN").value ='0';
	}
  var otherReason=document.getElementById("otherReason").value;
  var fromTime=document.getElementById("fromTime").value;
  var  resultfromTime = checkTime(fromTime);
  if(resultfromTime == false){
    return false;
  }
  var toTime=document.getElementById("toTime").value;
  var  resulttoTime = checkTime(toTime);
  if(resulttoTime == false){
    return false;
  }
  var fillAffirmFlag=document.getElementById("FILLAFFIRMFLAG").value;
  var obj = document.getElementById("FILLAFFIRMFLAG");
  var txt = obj.options[obj.selectedIndex].text;  
   
  //刷新后全部反应的内容还存在
  document.getElementById("otherReason1").value=otherReason;
  document.getElementById("fromTime1").value=fromTime;
  document.getElementById("toTime1").value=toTime;
  document.getElementById("FILLAFFIRMFLAG1").value=fillAffirmFlag;
  
  
  textMuli("otherReason",otherReason);  
  textMuli("fromTime",fromTime);  
  textMuli("toTime",toTime);  
  
  if(fillAffirmFlag != "" && fillAffirmFlag != null){
     textMuli("valibl_value_AFFIRM_NO",fillAffirmFlag);  	
     textMuli("valibl_input_AFFIRM_NO",txt);  
  }else{
     textMuli("valibl_value_AFFIRM_NO",'14015210');  	
     textMuli("valibl_input_AFFIRM_NO",'部门审批'); 
  } 
 
  checkYN("ADJST_YN",ADJSTYN);
  if(CONFIRM_FLAG == "1"){
      textMuli("CONFIRM_FLAG",CONFIRM_FLAG);
      htmlMuli("CONFIRM_FLAGTEXT",'Y');
  }else{
      textMuli("CONFIRM_FLAG",'0');
      htmlMuli("CONFIRM_FLAGTEXT",'');
  } 
  
 calPoTLengthForFill();
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
			document.getElementById("onck").href=encodeURI(encodeURI("/ar/attendanceMintenance/viewAddBatchPerList?isEmployeement=1&firstFlag=1&limit=ar&pageNum=1"
					+'&seach_KEY='+empid
					+'&empidStr='+empIdStr
					+'&personidStr='+personIdStr   
					));
			document.getElementById("onck").click();
		}
		
    }
    
 }
 
 
 //onpropetychange 时间的加载
 
  function eventfunction(obj)   {   
   if(obj.id == undefined)
	   obj = document.getElementById(''+obj) ;
   var empIdStr=document.getElementById('dwz.person.'+obj.id.substring(11)).value;
   var PERSON_ID=document.getElementById('dwz.person.AFFIRMOR_IDApplyLeave'+obj.id.substring(28)).value;
   if(empIdStr != '' && PERSON_ID == ''){
	   //alert('没有精确到某个员工!请搜索员工');
	   return;
   }
   var applyBatchdate = document.getElementById('APPLY_DATE'+obj.id.substring(28)).value;//获取罪行的选择的加班时间
   var j=obj.id.substring(28);
     if(empIdStr != ''){
	      $.ajax({
				cache: false,
			    type: 'post',
				async:false,
		        url: "/ess/infoApplyAttendance/getOTSSTInformation",
				data: [{ name: 'PERSON_ID', value: PERSON_ID },
				       { name: 'applyBatchdate',value: applyBatchdate }
				],
				dataType:"json",
				success: function(data) {
				$('#OLD_APPLY_NO'+j).val(data.APPLY_NO);
				$('#CONFIRM_FLAGTEXT'+j).val('');
				$('#CONFIRM_FLAG'+j).val(data.CONFIRM_FLAG);
				$('#EMPIDTEXT'+j).html(data.EMPID);
				$('#WEEKDAYTEXT'+j).html(data.IWEEK);
				$('#TYPENAMETEXT'+j).html(data.TYPENAME);
				$('#DATE_TYPE'+j).val(data.DATE_TYPE);
				$('#KAOQINITEMTEXT'+j).html(data.KAOQINITEM);
				$('#SHIFT_NO'+j).val(data.SHIFT_NO);
				$('#GROUP_ID'+j).val(data.GROUP_ID);
				$('#FIRST_TIME'+j).val(data.FIRST_TIME);
				$('#LAST_TIME'+j).val(data.FIRST_TIME);
				$('#fromTime'+j).val('0800');
				$('#toTime'+j).val('1700');
				$('#APPLY_TYPE_CODE'+j).val(data.APPLY_TYPE_CODE);
				$('#INDOOR_DATETEXT'+j).html(data.INDOOR_DATE);
				$('#OUTDOOR_DATETEXT'+j).html(data.OUTDOOR_DATE);
				$('#valibl_input_AFFIRM_NO'+j).val('部门审批');
				$('#valibl_value_AFFIRM_NO'+j).val('14015210');
				$('#OT_TOTAILTEXT'+j).html(data.OT_TOTAIL);
				$('#WEEKDAY_OT_TOTAILTEXT'+j).html(data.WEEKDAY_OT_TOTAIL);
				$('#WEEKEND_OT_TOTAILTEXT'+j).html(data.WEEKEND_OT_TOTAIL);
				$('#HOILDAY_OT_TOTAILTEXT'+j).html(data.HOILDAY_OT_TOTAIL);
				$('#COMPRE_OT_TOTAILTEXT'+j).html(data.COMPRE_OT_TOTAIL);
				$('#OT_TOAVGTEXT'+j).html(data.OT_TOAVG);
				$('#APPLY_DATETEXT'+j).html(data.AR_DATE_STR);
				$('#APPLY_DATE'+j).val(data.AR_DATE_STR);
				
				
				}
		 });
     
	  }
	  $('#BATCH_OT'+j).attr('checked','checked');
	  calPoTLengthForADD(j);
}
 
 
 function eventfunction(obj,AA)   {   
   if(obj.id == undefined)
	   obj = document.getElementById(''+obj) ;
   var empIdStr=document.getElementById('dwz.person.'+obj.id.substring(11)).value;
   var PERSON_ID=document.getElementById('dwz.person.AFFIRMOR_IDApplyLeave'+obj.id.substring(28)).value;
   if(empIdStr != '' && PERSON_ID == ''){
	   //alert('没有精确到某个员工!请搜索员工');
	   return;
   }
   var APPLY_DATE = document.getElementById('APPLY_DATE'+obj.id.substring(28)).value;
   if(AA == undefined){
      applyBatchdate = APPLY_DATE;
   }else{
      applyBatchdate = AA;
   }
   var j=obj.id.substring(28);
     if(empIdStr != ''){
	      $.ajax({
				cache: false,
			    type: 'post',
				async:false,
		        url: "/ess/infoApplyAttendance/getOTSSTInformation",
				data: [{ name: 'PERSON_ID', value: PERSON_ID },
				       { name: 'applyBatchdate',value: applyBatchdate }
				],
				dataType:"json",
				success: function(data) {
				$('#OLD_APPLY_NO'+j).val(data.APPLY_NO);
				$('#CONFIRM_FLAGTEXT'+j).val('');
				$('#CONFIRM_FLAG'+j).val(data.CONFIRM_FLAG);
				$('#EMPIDTEXT'+j).html(data.EMPID);
				$('#WEEKDAYTEXT'+j).html(data.IWEEK);
				$('#TYPENAMETEXT'+j).html(data.TYPENAME);
				$('#DATE_TYPE'+j).val(data.DATE_TYPE);
				$('#KAOQINITEMTEXT'+j).html(data.KAOQINITEM);
				$('#SHIFT_NO'+j).val(data.SHIFT_NO);
				$('#GROUP_ID'+j).val(data.GROUP_ID);
				$('#FIRST_TIME'+j).val(data.FIRST_TIME);
				$('#LAST_TIME'+j).val(data.FIRST_TIME);
				$('#fromTime'+j).val('0800');
				$('#toTime'+j).val('1700');
				$('#APPLY_TYPE_CODE'+j).val(data.APPLY_TYPE_CODE);
				$('#INDOOR_DATETEXT'+j).html(data.INDOOR_DATE);
				$('#OUTDOOR_DATETEXT'+j).html(data.OUTDOOR_DATE);
				$('#valibl_input_AFFIRM_NO'+j).val('部门审批');
				$('#valibl_value_AFFIRM_NO'+j).val('14015210');
				$('#OT_TOTAILTEXT'+j).html(data.OT_TOTAIL);
				$('#WEEKDAY_OT_TOTAILTEXT'+j).html(data.WEEKDAY_OT_TOTAIL);
				$('#WEEKEND_OT_TOTAILTEXT'+j).html(data.WEEKEND_OT_TOTAIL);
				$('#HOILDAY_OT_TOTAILTEXT'+j).html(data.HOILDAY_OT_TOTAIL);
				$('#COMPRE_OT_TOTAILTEXT'+j).html(data.COMPRE_OT_TOTAIL);
				$('#OT_TOAVGTEXT'+j).html(data.OT_TOAVG);
				$('#APPLY_DATETEXT'+j).html(data.AR_DATE_STR);
				$('#APPLY_DATE'+j).val(data.AR_DATE_STR);
				
				
				}
		 });
     
	  }
	   $('#BATCH_OT'+j).attr('checked','checked');
	  calPoTLengthForADD(j);
}


//计算时长
function calPoTLength(event,j){
		var	from_date = $("#APPLY_DATE"+j).val();
		var cpnyId = document.getElementById("CPNY_ID").value;
		var fromTime1 =  document.getElementById("fromTime"+j).value;
		if(fromTime1.length==4){
		  var resultfromTime =   checkTime(fromTime1);
		  if(resultfromTime == false){
		    $("#fromTime"+j).val('');
		    $("#fromTime"+j).focus();
		  }
		  $('#BATCH_OT'+j).attr('checked','checked');
		}
		var fromTime = fromTime1.substr(0,2)+":"+fromTime1.substr(2,2);
		var toTime1 = document.getElementById("toTime"+j).value;
		if(toTime1.length==4){
		  var resultToTime =   checkTime(toTime1);
		  if(resultToTime == false){
		    $("#toTime"+j).val('');
		    $("#toTime"+j).focus();
		  }
		  $('#BATCH_OT'+j).attr('checked','checked');
		}
		var toTime = toTime1.substr(0,2)+":"+toTime1.substr(2,2);
		var FIRST_TIME = document.getElementById("FIRST_TIME"+j).value;
		var LAST_TIME = document.getElementById("LAST_TIME"+j).value;
		var SHIFT_NO = document.getElementById("SHIFT_NO"+j).value;
		var e= event ? event : window.event; 
       var keyCode = e.which ? e.which : e.keyCode;
	if(keyCode==13){
	      if(fromTime1.length != 4){
	         $("#fromTime"+j).val('');
		     $("#fromTime"+j).focus();
		     $('#shenqingshichangText'+j).html(0+ "小时");
			$("#shenqingshichang"+j).val(0);
	      }
	        
	      if(toTime1.length !=4){
	        $("#toTime"+j).val('');
		    $("#toTime"+j).focus();
		    $('#shenqingshichangText'+j).html(0+ "小时");
			$("#shenqingshichang"+j).val(0);
	      }
	     if(fromTime1.length == 4&&toTime1.length ==4)
		if(from_date!=null&&from_date!=""){
			 $.ajaxSettings.global = false;
				 $.ajax({
				type: 'POST',
				url: '/hrm/recruitManage/doSql',
			    data:{sql:"select GET_OT_LENGTH_SST('" + from_date 
									+ "','" + fromTime+ "','" + toTime + "') OT_LENGTH from dual"},
			    dataType:"json",
			    cache: false,
			    success: function(data){
					document.getElementById('shenqingshichangText'+j).innerHTML = data.result[0].OT_LENGTH + "小时";
					$("#shenqingshichang"+j).val(data.result[0].OT_LENGTH);},
				error: DWZ.ajaxError
		       });
			$.ajaxSettings.global = true;
		}
    }
}


//填充的时候计算时长
//计算时长
function calPoTLengthForFill(){
	var ids = document.getElementsByName("BATCH_OT");
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
					var	from_date = $("#APPLY_DATE"+j).val();
				    var cpnyId = document.getElementById("CPNY_ID").value;
				    var fromTime1 =  document.getElementById("fromTime"+j).value;
				    var fromTime = fromTime1.substr(0,2)+":"+fromTime1.substr(2,2);
				    var toTime1 = document.getElementById("toTime"+j).value;
				    var toTime = toTime1.substr(0,2)+":"+toTime1.substr(2,2);
				    var FIRST_TIME = document.getElementById("FIRST_TIME"+j).value;
				    var LAST_TIME = document.getElementById("LAST_TIME"+j).value;
				    var SHIFT_NO = document.getElementById("SHIFT_NO"+j).value;
				   
					if(from_date!=null&&from_date!=""){
								 $.ajaxSettings.global = false;
									$.ajax({
										type: 'POST',
										url: '/hrm/recruitManage/doSql',
										data:{sql:"select GET_OT_LENGTH_SST('" + from_date 
										+ "','" + fromTime+ "','" + toTime + "') OT_LENGTH from dual"},
										dataType:"json",
										cache: false,
										success: function(data){
										    document.getElementById('shenqingshichangText'+j).innerHTML = data.result[0].OT_LENGTH + "小时";
									        $("#shenqingshichang"+j).val(data.result[0].OT_LENGTH);
										},
										error: DWZ.ajaxError
									});
									$.ajaxSettings.global = true;
						}
		             }
		          }
		      }
		  
}


//填充的时候计算时长
//计算时长
function calPoTLengthForADD(j){
	var	from_date = $("#APPLY_DATE"+j).val();
	var cpnyId = document.getElementById("CPNY_ID").value;
	var fromTime1 =  document.getElementById("fromTime"+j).value;
	var fromTime = fromTime1.substr(0,2)+":"+fromTime1.substr(2,2);
	var toTime1 = document.getElementById("toTime"+j).value;
	var toTime = toTime1.substr(0,2)+":"+toTime1.substr(2,2);
	var FIRST_TIME = document.getElementById("FIRST_TIME"+j).value;
	var LAST_TIME = document.getElementById("LAST_TIME"+j).value;
	var SHIFT_NO = document.getElementById("SHIFT_NO"+j).value;
				   
		if(from_date!=null&&from_date!=""){
			$.ajaxSettings.global = false;
					$.ajax({
						type: 'POST',
						url: '/hrm/recruitManage/doSql',
					    data:{sql:"select GET_OT_LENGTH_SST('" + from_date 
									+ "','" + fromTime+ "','" + toTime + "') OT_LENGTH from dual"},
					    dataType:"json",
						cache: false,
					    success: function(data){
							document.getElementById('shenqingshichangText'+j).innerHTML = data.result[0].OT_LENGTH + "小时";
							$("#shenqingshichang"+j).val(data.result[0].OT_LENGTH);
						},
						error: DWZ.ajaxError
					});
			$.ajaxSettings.global = true;
	   }
}

function onLoadFunction(applyNo){   
    var workTime = document.getElementById('valibl_input_SHIFT_NO'+applyNo).value ;
    document.getElementById('fromTime'+applyNo).value=workTime.substr(0,4);
	document.getElementById('toTime'+applyNo).value=workTime.substr(5,4); 
}

function searchPop_ar0705(flag) {
	var name = encodeURI(encodeURI($("#seach_KEY", navTab.getCurrentPanel()).val()));
	$("#searchPop_ar0705", navTab.getCurrentPanel())
			.attr(
					'href',
					'/pa/workManagement/viewEmpForPopList?pageNum=1&numPerPage=10&limit=ar&seach_KEY='
							+ name);
	if (flag == 'onkeyup')
		$("#searchPop_ar0705", navTab.getCurrentPanel()).click();
}

function addEmpPop_ar0705(obj,flag) {
	var name = encodeURI(encodeURI($(obj).val()));
	var empIdStr=obj.id.substring(11);
	var personIdStr="AFFIRMOR_IDApplyLeave"+empIdStr.substring(17);
	$("#addEmpPop_ar0705", navTab.getCurrentPanel())
			.attr(
					'href',
					'/pa/workManagement/viewEmpForArAddPopList?pageNum=1&numPerPage=10&limit=ar&seach_KEY='
							+ name+'&empidStr='+empIdStr
							+'&personidStr='+personIdStr   );
	if (flag == 'onkeyup')
		$("#addEmpPop_ar0705", navTab.getCurrentPanel()).click();
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

function getInformationOnApplyDate(){
    var ids = document.getElementsByName("BATCH_OT");
    var checked=false;
	for(var i=0;i<ids.length;i++){
		if(ids[i].checked){
		 var obj = 'dwz.person.AFFIRMOR_IDApplyLeave'+ids[i].value;
		 eventfunction(obj);		
		}
	}
} 

</script>
<div   id="viewApplyOtBatch"  class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/ar/attendanceMintenance/viewArOvertimeSSTManagent?firstFlag=N&deleteYN=Y" rel="pagerForm" method="post"
		id="viewArOvertimeSSTManagent" name="viewArOvertimeSSTManagent">
		<div class="searchBar">
				<table class="searchContent">
			<tr>
				    <input type="hidden" id="fromTime1"  name="fromTime1"  size="3"  value="${fromTime1}"/>
				    <input type="hidden" id="toTime1"  name="toTime1"  size="3" value="${toTime1}"/>
				    <input type="hidden" id="reason1"  name="reason"  size="3" value="${reason}"/>
				    <input type="hidden" id="otherReason1" name="otherReason"  value="${otherReason}"/>
				    <input type="hidden" id="FILLAFFIRMFLAG1" name="FILLAFFIRMFLAG"  value="${FILLAFFIRMFLAG}"/>
				    <td>社号/姓名</td>
					<td>
						<input type="text" name="seach_KEY" id="seach_KEY"
							value="${KEY}" style="float:left;" 
						onkeydown="javascript:if(event.keyCode == 13)searchPop_ar0705('onkeyup');" />
						<a class="btnLook" id="searchPop_ar0705"
						onclick="searchPop_ar0705()" href="#" lookupGroup="person"> </a>
						<input id="dwz.person.empInfo"   name="empInfo" type="text" readonly lookupGroup="person" size="80" value="${empInfo}"/> 
					</td>
				</tr>
				<tr>
					<td width="10%">
						<spring:message code="ess.workgroup.title.duration" text="期间"/>
					</td>
					<td width="20%">
					  <input type="text" name="seach_FROM_DATE" id="seach_FROM_DATE"  class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="${FROM_DATE }"/>
					     <input type="text" name="seach_TO_DATE" id="seach_TO_DATE"  class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="${TO_DATE }"/>
					</td>
					<td width="30%">审批状态 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					   <c:if test="${defaultCpny eq 'SST'}">
						<ait:selectCodeMulti id="seach_AFFIRM_FLAG" name="seach_AFFIRM_FLAG_NAME" parentNo="14014304" selected="${AFFIRM_FLAG}"  selectedNm="${AFFIRM_FLAG_NAME}"/>
						</c:if>
					</td>
					<td width="30%">
				       <input type="checkbox" name="seach_attenState" value="all" checked="checked"/> ALL
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;       
					含调休：<input type="checkbox"  id="seach_ADJSTYN" name="seach_ADJSTYN"    value="1" />
				</td>	
				</tr>
				<tr>
				<td width="10%">员工类型</td>
				<td width="20%">
				 	<ait:SelectSyCodeByCpnyID id="seach_EMP_TYPE_CODE" name="seach_EMP_TYPE_CODE" parentNo="13864" selected="${EMP_TYPE_CODE}" cnpyID="${LoginUser.cpnyId}" limit="all"/>
				</td>
				<td width="10%">
					   状态:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					  <select id="seach_CONFIRM_FLAG" name="seach_CONFIRM_FLAG" >
					     <option value="">请选择</option>
					     <option value="1"  <c:if test="${CONFIRM_FLAG eq 1}">selected</c:if>
											>Confirmed</option>
					     <option value="0"<c:if test="${CONFIRM_FLAG eq 0}">selected</c:if>
											>Unconfirmed</option>
					  </select>
					</td>		
				<c:if test="${authority ne '1'}">
				   <td width="30%">
						部门&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					
						${personInfo.DEPARTMENT }
					</td>
				</c:if>
				<c:if test="${authority eq '1'}">
				     <td width="30%"><!-- 部门： --> <spring:message
						code="hr.viewPersonalInfo.title.DEPTNAME" /> 
					   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<ait:deptList name="seach_DEPTNO" cpnyId="${defaultCpny}" limit="ar" id="viewApplyOtInfoBatchList_seachDept" selected="${DEPTNO}"/>
						<ait:deptTreeIcon name="seach_DEPTNO" cpnyId="${defaultCpny}" limit="ar" id="viewApplyOtInfoBatchList_seachDept" selected="${DEPTNO}"/>
					</td>
				</c:if>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent">
					    <button type="submit">
					       <spring:message code="public.title.search"/>
					    </button>
				        </div>
				        </div>
				    </li>
				</ul>
			</div>
		</div>
	</form>
</div>
<div>
  <table>
  <tr>
    <td id="openApplyOt" style="display:none">
       &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src="/resources/images/+.gif" title="打开" border="0" align="absmiddle" style="cursor:hand" onclick="xiujialeixing(1)"/>
    </td>
    <td id="closeApplyOt" >
       &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src="/resources/images/-.gif" title="关闭" border="0" align="absmiddle" style="cursor:hand" onclick="xiujialeixing(0)"/>
    </td>
   </tr>
  </table>
</div>
<div id="viewApplyAttenBatch" class="pageHeader" >
    <div class="searchBar">
			<table class="searchContent">
			    <tr>
				   <td>
				               时间
				   </td>
				   <td >
						<input id="fromTime"  name="fromTime"  size="3"  value="${fromTime1}"/>
						~
						<input id="toTime"  name="toTime"  size="3" value="${toTime1}"/>
				   </td>
					 <td>
					   锁定状态：
					  <select id="CONFIRM_FLAG" name="CONFIRM_FLAG" >
					     <option value="">请选择</option>
					     <option value="1"  <c:if test="${CONFIRM_FLAG eq 1}">selected</c:if>
											>Confirmed</option>
					     <option value="0"<c:if test="${CONFIRM_FLAG eq 0}">selected</c:if>
											>Unconfirmed</option>
					  </select>
					</td>
					<td >原因</td>
					<td >
				     <input type="text" id="otherReason" name="otherReason"  value="${otherReason}"/>
					</td >
					<td >审批状态 : &nbsp;&nbsp;&nbsp;&nbsp;
						<ait:SelectSyCodeCombinByCpnyID name="FILLAFFIRMFLAG" combinParentNo="14014304" selected="${FILLAFFIRMFLAG}"  cnpyID="${LoginUser.cpnyId}"  limit="all" />
					</td>
					<td >
					     是否调休：<input type="checkbox"  id="ADJSTYN" name="ADJSTYN"  value="" />
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
     <div style="font: 12px/ 20px arial, sans-serif; float: left; height: 30px; line-height: 30px;">Total:${fn:length(oTAffirmList)}</div>
	<ul class="toolBar">
	
	
	    <li><a class="buttonActive" onclick="delOtApplyCallback(2,'delOtApplyAffirmForm',DWZ.ajaxDone)"><span>添加</span></a></li>
		<li><a class="buttonActive" onclick="delOtApplyCallback(1,'delOtApplyAffirmForm',DWZ.ajaxDone)"><span>保存</span></a></li>
		<li><a class="buttonActive" onclick="delOtApplyCallback(0,'delOtApplyAffirmForm',DWZ.ajaxDone)"><span>删除</span></a></li>
		<li><a class="buttonActive" onclick="downloadExcel('viewArOvertimeSSTManagent','/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=153','/ar/attendanceMintenance/viewArOvertimeSSTManagent?firstFlag=N')"><span>导出到Excel</span></a></li>
			
	</ul>
</div>
	<form name="delOtApplyAffirmForm" id="delOtApplyAffirmForm" method="post" action="/ar/attendanceMintenance/delLOvertimeApplyInBatchSST" 
	  onsubmit="return delOtApplyCallback(this, navTabAjaxDone);">                    
		<table class="orderList" width="100%">
			<thead>
			    <tr>
				    <th  rowspan="2"  width="1%" ><!--NO-->
						NO
					</th>
					<th rowspan="2" width="1%" >
				    	<input type="checkbox" class="checkboxCtrl" group="BATCH_OT" />
				    </th>
				    <th  rowspan="2" width="1%"><!--状态-->
						状态
					</th>
					<th rowspan="2" width="4%"><!--申请人-->
						姓名
					</th >
				    <th rowspan="2" width="4%"><!--社号-->
						社号
					</th>
					<th rowspan="2" width="6%" ><!--日期-->
						日期
					</th>
					<th rowspan="2" width="4%"><!--星期-->
						星期
					</th>
					<th rowspan="2" ><!--类型-->
						类型
					</th>
					<th rowspan="2" width="4%"><!--考勤-->
						考勤
					</th>
					<th colspan="6" ><!--申请-->
						申请
					</th>
					<th colspan="1"><!--原因-->
						原因
					</th>
					<th  rowspan="2"><!--审批状态-->
						审批状态
					</th>
					<th colspan="6"><!--加班累计-->
						加班累计
					</th>
					<th rowspan="2" width="4%"><!--输入者-->
						输入者
					</th>
					<th rowspan="2"  width="6%"><!--输入时间-->
						输入时间
					</th>
					<th rowspan="2" width="4%"><!--最终修改人-->
						修改人
					</th>
					<th rowspan="2"  width="6%"><!--修改时间-->
						修改时间
					</th>
				</tr>
				<tr>
					<th ><!--进门-->
						进门
					</th>
					<th ><!--出门-->
						出门
					</th>
					<th ><!--开始-->
						开始
					</th>
					<th><!--结束-->
						结束
					</th>
					<th width="4%"><!-- 加班时间-->
					          时长
					</th>
					<th width="3%"><!-- 是否调休-->
					          调休
					</th>
					<th width="4%"><!--其他原因-->
						原因
					</th>
					<th width="3%"><!--加班合计-->
						合计
					</th>
					<th width="3%"><!--平时-->
						平时
					</th>
					<th width="3%"><!--周末-->
						周末
					</th >
					<th width="3%"><!--法定节假日-->
						法定
					</th>
					<th width="3%"><!--综合加班-->
						综合
					</th>
					<th width="3%"><!--月平均-->
						月均
					</th>
				</tr>
			</thead>
			<tbody>
			    <%--NULL --%>
			    
			    <c:forEach items="${nullOTSSTAffirmList}" var="otApply" varStatus="i">	
					<tr target="sid" rel="${admin.personId}" 
					  <c:if test="${otApply.AFFIRM_FLAG eq '14014307' || otApply.AFFIRM_FLAG eq '14014311'}">style="color: blue;"</c:if>
							<c:if test="${otApply.AFFIRM_FLAG eq '14014309' || otApply.AFFIRM_FLAG eq '14014310'}">style="color: red;"</c:if>
					>
					    <td  style="text-align: center">${i.count}</td>
					    <td  style="text-align: center">
					        <input type="checkbox" id="BATCH_OT${otApply.APPLY_NO}" name="BATCH_OT" value="${otApply.APPLY_NO}" />
						     <input id="UNIT${otApply.APPLY_NO}" name="UNIT${otApply.APPLY_NO}" type="hidden"  value="${otApply.UNIT}"/>
						     <input id="OLD_APPLY_NO${otApply.APPLY_NO}" name="OLD_APPLY_NO${otApply.APPLY_NO}" type="hidden"  value=""/>
						     <input id="STATUS_CODE${otApply.APPLY_NO}" name="STATUS_CODE${otApply.APPLY_NO}" type="hidden"  value="${otApply.STATUS_CODE}"/>
						     <input id="STATUS_NAME${otApply.APPLY_NO}" name="STATUS_NAME${otApply.APPLY_NO}" type="hidden"  value="${otApply.STATUS_NAME}"/>
						     <input id="IWEEK${otApply.APPLY_NO}" name="IWEEK${otApply.APPLY_NO}" type="hidden"  value="${otApply.IWEEK}"/>
						     <input id="LOCK_YN${otApply.APPLY_NO}" name="LOCK_YN${otApply.APPLY_NO}" type="hidden"  value="${otApply.LOCK_YN}"/>
						      <input id="GROUP_ID${otApply.APPLY_NO}" name="GROUP_ID${otApply.APPLY_NO}" type="hidden"  value="${otApply.GROUP_ID}"/>
						     <input id="CPNY_ID" name="CPNY_ID" type="hidden"  value="${LoginUser.cpnyId}"/>
					    </td>
					    <td  style="text-align: center">
					         <div id="CONFIRM_FLAGTEXT${otApply.APPLY_NO}"></div>
					        <input id="CONFIRM_FLAG${otApply.APPLY_NO}" name="CONFIRM_FLAG${otApply.APPLY_NO}" type="hidden"  value="${otApply.CONFLAG}"/>
					    </td>
					     <td style="text-align: center">             
							<input type="text" name="LOCAL_NAME" id="dwz.person.EMPINFOApplyLeave${otApply.APPLY_NO}"
								value="${KEY}" style="float:left;" size="4"onclick="eventfunction(this);"
							onkeydown="javascript:if(event.keyCode == 13)addEmpPop_ar0705(this,'onkeyup');" />
							<input id="dwz.person.AFFIRMOR_IDApplyLeave${otApply.APPLY_NO}" onpropertychange="eventfunction('dwz.person.EMPINFOApplyLeave${otApply.APPLY_NO}');"
							name="personid${otApply.APPLY_NO}" type="hidden"  size="8"  lookupGroup="person" />
							<a id="addEmpPop_ar0705" 
							onclick="addEmpPop_ar0705()" href="#" lookupGroup="person"> </a>
					    </td>
					    <td style="text-align: center">
					      <div  id="EMPIDTEXT${otApply.APPLY_NO}"></div>
					    </td>
					    <td style="text-align: center">
					       <input type="text" size="10"  id="APPLY_DATE${otApply.APPLY_NO}" name="APPLY_DATE${otApply.APPLY_NO}"    class="Wdate" 
					       onClick="WdatePicker({dateFmt:'yyyy-MM-dd',onpicking:
					         function(dp){
					             $('#BATCH_OT${otApply.APPLY_NO}').attr('checked','checked');
					             eventfunction('dwz.person.EMPINFOApplyLeave${otApply.APPLY_NO}',dp.cal.getNewDateStr());
					         }
					       })"   
					        value="${otApply.AR_DATE_STR}"  />
					    </td>
					    <td  style="text-align: center">
					      <div id="WEEKDAYTEXT${otApply.APPLY_NO}"></div>
					    </td>
					    <td style="text-align: center">
					        <div id="TYPENAMETEXT${otApply.APPLY_NO}"></div>
					          <input id="DATE_TYPE${otApply.APPLY_NO}" name="DATE_TYPE${otApply.APPLY_NO}" type="hidden"  value="${otApply.DATE_TYPE}"/>
					    </td>
					    <td style="text-align: center">
					        <div id="KAOQINITEMTEXT${otApply.APPLY_NO}"></div>
					         <input type="hidden" id="APPLY_TYPE_CODE${otApply.APPLY_NO}" name="APPLY_TYPE_CODE${otApply.APPLY_NO}" value="${otApply.APPLY_TYPE_CODE}"/>
					       <input type="hidden" id="SHIFT_NO${otApply.APPLY_NO}" name="SHIFT_NO${otApply.APPLY_NO}" value=" ${otApply.SHIFT_NO}"/>
					         <input type="hidden" id="FIRST_TIME${otApply.APPLY_NO}" name="FIRST_TIME${otApply.APPLY_NO}" value="${otApply.FIRST_TIME}"/> 
							<input type="hidden" id="LAST_TIME${otApply.APPLY_NO}" name="LAST_TIME${otApply.APPLY_NO}" value="${otApply.LAST_TIME}"/> 
					    </td>
					    <td style="text-align: center">
					       <div id="INDOOR_DATETEXT${otApply.APPLY_NO}"></div>
					    </td>
					    <td  style="text-align: center">
					        <div id="OUTDOOR_DATETEXT${otApply.APPLY_NO}"></div>
					    </td>
					    <td style="text-align: center">
					        <input type="text" size="4" id="fromTime${otApply.APPLY_NO}" name="fromTime${otApply.APPLY_NO}" value="${otApply.FROM_TIME}" onkeyup="calPoTLength(event,${otApply.APPLY_NO});"/>  
					    </td>
					    <td style="text-align: center">
					        <input type="text" size="4" id="toTime${otApply.APPLY_NO}" name="toTime${otApply.APPLY_NO}" value="${otApply.TO_TIME}" onkeyup="calPoTLength(event,${otApply.APPLY_NO});"/>
					    </td>
					    <td  style="text-align: center">
					         <div id="shenqingshichangText${otApply.APPLY_NO}"></div>
							<input type="hidden" id="shenqingshichang${otApply.APPLY_NO}" name="APPLY_LENGTH${otApply.APPLY_NO}" value="${otApply.APPLY_LENGTH2}" />
							<input type="hidden" id="Lotlengthonehour${otApply.APPLY_NO}" name="Lotlengthonehour${otApply.APPLY_NO}" value=""/> 
				            <input type="hidden" id="Lotlengthonemin${otApply.APPLY_NO}" name="Lotlengthonemin${otApply.APPLY_NO}" value=""/> 
				         </td>
					    <td  style="text-align: center">
					           <input type="checkbox"  id="ADJST_YN${otApply.APPLY_NO}" name="ADJST_YN${otApply.APPLY_NO}"  value="1" onclick="$('#BATCH_OT${otApply.APPLY_NO}').attr('checked','checked');"/>
				         </td>
					    <td  style="text-align: center">
					       <input style="width: 100%;" id="otherReason${otApply.APPLY_NO}" name="otherReason${otApply.APPLY_NO}" value="${otApply.REASON_OTHER}" onkeyup="$('#BATCH_OT${otApply.APPLY_NO}').attr('checked','checked');" type="text"/>
					    </td>
					     <td>
					    <input id="valibl_input_AFFIRM_NO${otApply.APPLY_NO}" type="text" size="8" value="${otApply.AFFIRM_NAME}"
								onfocus="$('#valibl_pop_AFFIRM_NO${otApply.APPLY_NO}').css('display', 'block');" readonly="readonly"  />
							<input id="valibl_value_AFFIRM_NO${otApply.APPLY_NO}" name="AFFIRM_FLAG${otApply.APPLY_NO}" type="hidden" value="${otApply.AFFIRM_FLAG}" />
							 <div id="valibl_pop_AFFIRM_NO${otApply.APPLY_NO}"
								onmouseover="valibl_mouseover_item_pop('AFFIRM_NO${otApply.APPLY_NO}')" 
								onclick="$('#valibl_pop_AFFIRM_NO${otApply.APPLY_NO}').css('display', 'none');"class="deptContent"
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
														<li class="deptTreeLi" style="width: 300px"
														onclick="$('#valibl_input_AFFIRM_NO${otApply.APPLY_NO}').attr('value','${item.CODE_NAME}');
														$('#valibl_value_AFFIRM_NO${otApply.APPLY_NO}').attr('value','${item.CODE_NO}');
														$('#BATCH_OT${otApply.APPLY_NO}').attr('checked','checked');
														$('#valibl_pop_AFFIRM_NO${otApply.APPLY_NO}').css('display', 'none');"><span>${item.CODE_NAME}</span></li>
													</c:when>
													<c:otherwise>
														<li style="width: 300px" onclick="$('#valibl_input_AFFIRM_NO${otApply.APPLY_NO}').attr('value','${item.CODE_NAME}');
														$('#valibl_value_AFFIRM_NO${otApply.APPLY_NO}').attr('value','${item.CODE_NO}');
														$('#BATCH_OT${otApply.APPLY_NO}').attr('checked','checked');
														$('#valibl_pop_AFFIRM_NO${otApply.APPLY_NO}').css('display', 'none');"><span>${item.CODE_NAME}</span></li>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</ul>
									</div>
									<div class="ztree_dept_color">
										<a href="#" onclick="$('#valibl_pop_AFFIRM_NO${otApply.APPLY_NO}').css('display', 'none');"
											class="ztree_dept_color_a"> <span>关闭</span>
										</a>
									</div>
								</div>
							</div>
					    </td>
					    <td  style="text-align: center">
					      <div id="OT_TOTAILTEXT${otApply.APPLY_NO}"></div>
					    </td>
					    <td  style="text-align: center">
					        <div id="WEEKDAY_OT_TOTAILTEXT${otApply.APPLY_NO}"></div>
					    </td>
					    <td  style="text-align: center">
					        <div id="WEEKEND_OT_TOTAILTEXT${otApply.APPLY_NO}"></div>
					    </td>
					    <td style="text-align: center">
					          <div id="HOILDAY_OT_TOTAILTEXT${otApply.APPLY_NO}"></div>
					    </td>
					    <td  style="text-align: center">
					          <div id="COMPRE_OT_TOTAILTEXT${otApply.APPLY_NO}"></div>
					    </td>
					    <td  style="text-align: center">
					            <div id="OT_TOAVGTEXT${otApply.APPLY_NO}"></div>
					    </td>
					    <td  style="text-align: center">
					          ${otApply.CREATED_BY}
					    </td>
					    <td  style="text-align: center">
					          ${otApply.CREATE_DATE}
					    </td>
					    <td  style="text-align: center">
					          ${otApply.UPDATED_BY}
					    </td>
					    <td  style="text-align: center">
					          ${otApply.UPDATE_DATE}
					    </td>
					    
					</tr>
				</c:forEach>
			<%--NULL end --%>
			
				<c:forEach items="${oTAffirmList}" var="otApply" varStatus="i">	
					<tr target="sid" rel="${admin.personId}" 
					  <c:if test="${otApply.AFFIRM_FLAG eq '14014307' || otApply.AFFIRM_FLAG eq '14014311'}">style="color: blue;"</c:if>
							<c:if test="${otApply.AFFIRM_FLAG eq '14014309' || otApply.AFFIRM_FLAG eq '14014310'}">style="color: red;"</c:if>
					>
					    <td  style="text-align: center">${i.count}</td>
					    <td  style="text-align: center">
					        <input type="checkbox" id="BATCH_OT${otApply.APPLY_NO}" name="BATCH_OT" value="${otApply.APPLY_NO}" />
						     <input id="UNIT${otApply.APPLY_NO}" name="UNIT${otApply.APPLY_NO}" type="hidden"  value="${otApply.UNIT}"/>
						     <input id="STATUS_CODE${otApply.APPLY_NO}" name="STATUS_CODE${otApply.APPLY_NO}" type="hidden"  value="${otApply.STATUS_CODE}"/>
						     <input id="STATUS_NAME${otApply.APPLY_NO}" name="STATUS_NAME${otApply.APPLY_NO}" type="hidden"  value="${otApply.STATUS_NAME}"/>
						     <input id="IWEEK${otApply.APPLY_NO}" name="IWEEK${otApply.APPLY_NO}" type="hidden"  value="${otApply.IWEEK}"/>
						     <input id="LOCK_YN${otApply.APPLY_NO}" name="LOCK_YN${otApply.APPLY_NO}" type="hidden"  value="${otApply.LOCK_YN}"/>
						      <input id="GROUP_ID${otApply.APPLY_NO}" name="GROUP_ID${otApply.APPLY_NO}" type="hidden"  value="${otApply.GROUP_ID}"/>
						     <input id="CPNY_ID" name="CPNY_ID" type="hidden"  value="${LoginUser.cpnyId}"/>
					    </td>
					    <td  style="text-align: center">
					         <div id="CONFIRM_FLAGTEXT${otApply.APPLY_NO}"> ${otApply.CONFIRM_FLAG}</div>
					        <input id="CONFIRM_FLAG${otApply.APPLY_NO}" name="CONFIRM_FLAG${otApply.APPLY_NO}" type="hidden"  value="${otApply.CONFLAG}"/>
					    </td>
					    <td  style="text-align: center">
					          <!-- [max=true, mask=true, maxable=true  minable=true, resizable = true ,drawable=true ] -->
                              <a href="/ess/viewDept/viewApplyDeptPersonalInfo?EMPID=${otApply.EMPID}&LOCAL_NAME= ${otApply.LOCAL_NAME}" 
                              target="dialog" style="color: blue;"  title="考勤个人信息"   [ mask=true ] width="1000" height="300"> 
                              ${otApply.LOCAL_NAME}</a>
                              <input id="dwz.person.AFFIRMOR_IDApplyLeave${otApply.APPLY_NO}" name="personid${otApply.APPLY_NO}" type="hidden"  value="${otApply.PERSON_ID}"/>
                              <input id="dwz.person.EMPINFOApplyLeave${otApply.APPLY_NO}"  type="hidden"  value="${otApply.LOCAL_NAME}"/>
                              <input id="PERSON_ID${otApply.APPLY_NO}"  type="hidden"  value="${otApply.EMPID}"/>
					    </td>
					    <td style="text-align: center">
					     <a href="/ess/viewDept/viewApplyDeptPersonalInfo?EMPID=${otApply.EMPID}&LOCAL_NAME= ${otApply.LOCAL_NAME}" 
					     target="dialog" style="color: blue;" title="考勤个人信息" title="考勤个人信息"   [ mask=true ] width="1200" height="400"> 
					     ${otApply.EMPID}</a>
					    </td>
					    <td style="text-align: center">
					       ${otApply.AR_DATE_STR}
					       <input type="hidden" id="APPLY_DATE${otApply.APPLY_NO}" name="APPLY_DATE${otApply.APPLY_NO}"  value="${otApply.AR_DATE_STR}"  />
					    </td>
					    <td  style="text-align: center">${otApply.WEEKDAY}</td>
					    <td style="text-align: center">
					       ${otApply.TYPENAME}
					    </td>
					    <td style="text-align: center">
					      <c:if test="${otApply.KAOQINITEM eq '正常出勤' || otApply.KAOQINITEM eq '休息' }">
					          <input type="hidden" id="KAOQINITEM${otApply.APPLY_NO}" name="KAOQINITEM${otApply.APPLY_NO}" value=""/>
					     </c:if>
					      <c:if test="${otApply.KAOQINITEM ne '正常出勤' && otApply.KAOQINITEM ne '休息'}">
					         ${otApply.KAOQINITEM}
					           <input type="hidden" id="KAOQINITEM${otApply.APPLY_NO}" name="KAOQINITEM${otApply.APPLY_NO}" value="${otApply.KAOQINITEM}"/>
					     </c:if>
					       <input type="hidden" id="ITEM_NO${otApply.APPLY_NO}" name="ITEM_NO${otApply.APPLY_NO}" value="${otApply.ITEM_NO}"/>
					       <input type="hidden" id="APPLY_TYPE_CODE${otApply.APPLY_NO}" name="APPLY_TYPE_CODE${otApply.APPLY_NO}" value="${otApply.APPLY_TYPE_CODE}"/>
					       <input type="hidden" id="SHIFT_NO${otApply.APPLY_NO}" name="SHIFT_NO${otApply.APPLY_NO}" value=" ${otApply.SHIFT_NO}"/>
					         <input type="hidden" id="FIRST_TIME${otApply.APPLY_NO}" name="FIRST_TIME${otApply.APPLY_NO}" value="${otApply.FIRST_TIME}"/> 
							<input type="hidden" id="LAST_TIME${otApply.APPLY_NO}" name="LAST_TIME${otApply.APPLY_NO}" value="${otApply.LAST_TIME}"/> 
					    </td>
					    <td style="text-align: center">${otApply.INDOOR_DATE}</td>
					    <td  style="text-align: center">${otApply.OUTDOOR_DATE}</td>
					    <td style="text-align: center">
					        <input type="text" size="4" id="fromTime${otApply.APPLY_NO}" name="fromTime${otApply.APPLY_NO}" value="${otApply.FROM_TIME}" onkeyup="calPoTLength(event,${otApply.APPLY_NO});"/>  
					    </td>
					    <td style="text-align: center">
					        <input type="text" size="4" id="toTime${otApply.APPLY_NO}" name="toTime${otApply.APPLY_NO}" value="${otApply.TO_TIME}" onkeyup="calPoTLength(event,${otApply.APPLY_NO});"/>
					    </td>
					    <td  style="text-align: center">
					         <div id="shenqingshichangText${otApply.APPLY_NO}">
					         ${otApply.APPLY_LENGTH} 
					             <c:if test="${otApply.ITEM_NO == '141452'}">
					               <font color="red"> (√)</font>
					             </c:if>
					         </div>
					            <input type="hidden" id="OLD_APPLY_LENGTH${otApply.APPLY_NO}" name="OLD_APPLY_LENGTH${otApply.APPLY_NO}" value="${otApply.APPLY_LENGTH2}" />
							<input type="hidden" id="shenqingshichang${otApply.APPLY_NO}" name="APPLY_LENGTH${otApply.APPLY_NO}" value="${otApply.APPLY_LENGTH2}" />
							<input type="hidden" id="Lotlengthonehour${otApply.APPLY_NO}" name="Lotlengthonehour${otApply.APPLY_NO}" value=""/> 
				            <input type="hidden" id="Lotlengthonemin${otApply.APPLY_NO}" name="Lotlengthonemin${otApply.APPLY_NO}" value=""/> 
				         </td>
					    <td  style="text-align: center">
					       <c:if test="${otApply.ITEM_NO eq '141452'}">
					             <input type="checkbox"  id="ADJST_YN${otApply.APPLY_NO}" name="ADJST_YN${otApply.APPLY_NO}" checked="checked"  value="1"  onclick="$('#BATCH_OT${otApply.APPLY_NO}').attr('checked','checked');" />
					       </c:if>
					       <c:if test="${otApply.ITEM_NO ne '141452'}">
					          <input type="checkbox"  id="ADJST_YN${otApply.APPLY_NO}" name="ADJST_YN${otApply.APPLY_NO}"  value="1" onclick="$('#BATCH_OT${otApply.APPLY_NO}').attr('checked','checked');"/>
					        </c:if>
				         </td>
					    <td  style="text-align: center">
					       <input style="width: 100%;" id="otherReason${otApply.APPLY_NO}" name="otherReason${otApply.APPLY_NO}" value="${otApply.REASON_OTHER}" onkeyup="$('#BATCH_OT${otApply.APPLY_NO}').attr('checked','checked');"  type="text"/>
					    </td>
					     <td>
					    <input id="valibl_input_AFFIRM_NO${otApply.APPLY_NO}" type="text" size="6" value="${otApply.AFFIRM_NAME}"
								onfocus="$('#valibl_pop_AFFIRM_NO${otApply.APPLY_NO}').css('display', 'block');" readonly="readonly"  />
							<input id="valibl_value_AFFIRM_NO${otApply.APPLY_NO}" name="AFFIRM_FLAG${otApply.APPLY_NO}" type="hidden" value="${otApply.AFFIRM_FLAG}" />
							 <div id="valibl_pop_AFFIRM_NO${otApply.APPLY_NO}"
								onmouseover="valibl_mouseover_item_pop('AFFIRM_NO${otApply.APPLY_NO}')" 
								onclick="$('#valibl_pop_AFFIRM_NO${otApply.APPLY_NO}').css('display', 'none');"class="deptContent"
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
														<li class="deptTreeLi" style="width: 300px"
														onclick="$('#valibl_input_AFFIRM_NO${otApply.APPLY_NO}').attr('value','${item.CODE_NAME}');
														$('#valibl_value_AFFIRM_NO${otApply.APPLY_NO}').attr('value','${item.CODE_NO}');
														$('#BATCH_OT${otApply.APPLY_NO}').attr('checked','checked');
														$('#valibl_pop_AFFIRM_NO${otApply.APPLY_NO}').css('display', 'none');"><span>${item.CODE_NAME}</span></li>
													</c:when>
													<c:otherwise>
														<li style="width: 300px" onclick="$('#valibl_input_AFFIRM_NO${otApply.APPLY_NO}').attr('value','${item.CODE_NAME}');
														$('#valibl_value_AFFIRM_NO${otApply.APPLY_NO}').attr('value','${item.CODE_NO}');
														$('#BATCH_OT${otApply.APPLY_NO}').attr('checked','checked');
														$('#valibl_pop_AFFIRM_NO${otApply.APPLY_NO}').css('display', 'none');"><span>${item.CODE_NAME}</span></li>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</ul>
									</div>
									<div class="ztree_dept_color">
										<a href="#" onclick="$('#valibl_pop_AFFIRM_NO${otApply.APPLY_NO}').css('display', 'none');"
											class="ztree_dept_color_a"> <span>关闭</span>
										</a>
									</div>
								</div>
							</div>
					    </td>
					    <td  style="text-align: center">
					     <input type="hidden" id="OT_TOTAIL${otApply.APPLY_NO}" name="OT_TOTAIL${otApply.APPLY_NO}" value="${otApply.OT_TOTAIL}" />
					      ${otApply.OT_TOTAIL}
					    </td>
					    <td  style="text-align: center">
					       ${otApply.WEEKDAY_OT_TOTAIL}
					    </td>
					    <td  style="text-align: center">
					        ${otApply.WEEKEND_OT_TOTAIL}
					    </td>
					    <td style="text-align: center">
					         ${otApply.HOILDAY_OT_TOTAIL}
					    </td>
					    <td  style="text-align: center">
					         ${otApply.COMPRE_OT_TOTAIL}
					          <input type="hidden" id="OT_LIMIT${otApply.APPLY_NO}" name="OT_LIMIT${otApply.APPLY_NO}" value="${otApply.OT_LIMIT}" />
					    </td>
					    <td  style="text-align: center">
					          ${otApply.OT_TOAVG}
					    </td>
					    <td  style="text-align: center" title=" ${otApply.CREATED_BY}&nbsp;[ ${otApply.CREATED_IP}]">
					          ${otApply.CREATED_BY}
					    </td>
					    <td  style="text-align: center" title="${otApply.CREATE_DATE}">
					         ${fn:substring(otApply.CREATE_DATE,0,10)}
					          
					    </td>
					    <td  style="text-align: center" title="${otApply.UPDATED_BY}&nbsp; [ ${otApply.UPDATED_IP}]">
					          ${otApply.UPDATED_BY}
					    </td>
					    <td  style="text-align: center" title="${otApply.UPDATE_DATE}">
					          ${fn:substring(otApply.UPDATE_DATE,0,10)}
					    </td>
					    
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<input type="hidden" id="BATCH_LOT_OP_FLAG" name="OP_FLAG" value="0" />
	</form>
	<div style="visibility: hidden;">
	<%@ include file="/WEB-INF/view/inc/initPagination2.jsp"%>
	</div>
</div>