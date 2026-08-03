<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script>
//<!--

$(document).ready(function(){
	$("#viewApplyOtBatchTable",navTab.getCurrentPanel()).dataTable({
			"bPaginate": true,    //分页
		    "bAutoWidth":false,//表格宽度自动变化
		    "bProcessing":true,
		    "lengthMenu": [[20, 50, 100, -1], [20, 50, 100, "所有"]],
			"bLengthChange": true,  //按多少条记录显示下拉框
			"iDisplayLength": 20, //默认每页显示的记录数
			"bFilter": false,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
	     	"searching": true,//本地搜索
			"bSort": true,   //排序功能
			"bInfo": true,   //显示datatables的信息（底部的页数，条目数信息）
			"bScrollInfinite":true,
		     "orderClasses": false,
		     "order":[],//初始化不用自动排序
		     "scrollY": $(document.body).height() - 420,
		     "scrollX": $(document.body).width(),
		     "scrollCollapse": false,
		     "deferRender":true,
		     //"scroller":true,
	         "columnDefs": [//自定义排序类型
		                     { "orderDataType": "dom-text-numeric", "targets": [15,16,19,21] },
		                     { "orderDataType": "dom-select", "targets": [9,10,12,20,22]},
		                     { "visible": false, "searchable": true, "targets": [5] },
		                     { "orderable": false, "targets": [4] }
	                      ],
	                      initComplete: function () {//列筛选
	                          var api = this.api();
	                          api.columns().indexes().flatten().each(function (i) {
	                        	  if(i==4){//选中后，标记为需要提交的数据
	                        		  var column = api.column(i);
	                        		  column.on('change','tr',function(){
	                        			  if($('#'+api.cell($(this).context._DT_RowIndex,4).node().children[0].id,navTab.getCurrentPanel()).attr('checked')=='checked'){
	                        			  	api.cell($(this).context._DT_RowIndex,5).data('@willBeCommit@');
	                        			     $(this).toggleClass('selected');
	                        			  }else 
	                        				api.cell($(this).context._DT_RowIndex,5).data(''); 
	                        		  });
	                        	  }
	                          });
	        
	                      },
             "fixedColumns":{
                     leftColumns: 4
                 },
            "oLanguage": {//多语言配置
            	"sProcessing": "正在加载中......",
                "sZeroRecords": "查询不到相关数据！",
                "sEmptyTable": "表中无数据存在！",
                "sSearch": "快速筛选",
                "sLengthMenu": "每页 _MENU_ 条记录",
                "sInfo": "从 _START_ 到 _END_ /共 _TOTAL_ 条数据",
                "sInfoFiltered": "(从 _MAX_ 条记录过滤)",
                "oPaginate": {
                    "sPrevious": "上一页",
                    "sNext": "下一页"
                }
            },
            "sDom":'<"top"r<"clear">fBl>t<"bottom"ip<"clear">>',
            "buttons": [
                      {
                   	   text: '查看选中',
                          action: function ( e, dt, node, config ) {
                       	   onlyCheckedInfo();
                          }
                      },
                      {
                   	   text: '查看全部',
                          action: function ( e, dt, node, config ) {
                       	   allCheckedInfo();
                          }
                      },
                      {
                    	   text: '保存',
                           action: function ( e, dt, node, config ) {
                        	   delOtApplyCallback(1,'delOtApplyAffirmForm',DWZ.ajaxDone);
                           }
                       },
                      {
                    	   text: '导出到Excel',
                           action: function ( e, dt, node, config ) {
                        	   downloadExcel('viewArOvertimeManagent','/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=130&CPNY=${LoginUser.cpnyId}','/ar/attendanceMintenance/viewArOvertimeManagent?firstFlag=N');
                           }
                       }
                  ] 
		});
	$("#viewApplyOtBatchTable tbody",navTab.getCurrentPanel()).on( 'click', 'tr', function () {
		if($('#'+$("#viewApplyOtBatchTable",navTab.getCurrentPanel()).dataTable().api()
				.cell($(this).context._DT_RowIndex,4).node().children[0].id,navTab.getCurrentPanel()).attr('checked')!='checked'){
			$(this).toggleClass('selected');
		}else{
			$(this).attr('class','selected');
		}
	     $("#viewApplyOtBatchTable",navTab.getCurrentPanel()).dataTable().api().fixedColumns().update();
	 } );
	//全选后，分页里面的所有数据都会被选中
	$("#c1Group",navTab.getCurrentPanel()).click(function(){
		if($("#c1Group",navTab.getCurrentPanel()).attr('checked')=='checked'){
			$("#viewApplyOtBatchTable",navTab.getCurrentPanel()).dataTable().api().page.len(-1).draw();
			$('input[name=c1]',$("#viewApplyOtBatchTable",navTab.getCurrentPanel()).dataTable()).each(function(){this.checked=true;});
			var tempRowInd = 0;
			$("#viewApplyOtBatchTable",navTab.getCurrentPanel()).dataTable().api().column(5).nodes().each(
				function(){
					this.cell(tempRowInd++,5).data('@willBeCommit@');
				}	
			);
			
		}else{
			$('input[name=c1]',navTab.getCurrentPanel()).each(function(){this.checked=false;});
			var tempRowInd = 0;
			$("#viewApplyOtBatchTable",navTab.getCurrentPanel()).dataTable().api().column(5).nodes().each(
				function(){
					this.cell(tempRowInd++,5).data('');
				}	
			);
			$("#viewApplyOtBatchTable",navTab.getCurrentPanel()).dataTable().api().page.len(20).draw();
		}
		
	}); 
});
	function onlyCheckedInfo(){
		 	//点击查看选中后，筛选出所有要修改的记录
			var column = $("#viewApplyOtBatchTable",navTab.getCurrentPanel()).dataTable().api().column(5);
			column.search('@willBeCommit@', true, false).draw();
			$("#viewApplyOtBatchTable",navTab.getCurrentPanel()).dataTable().api().page.len(-1).draw();
	}
	function allCheckedInfo(){
		 	//点击查看选中后，筛选出所有要修改的记录
			$("#viewApplyOtBatchTable",navTab.getCurrentPanel()).dataTable().api().page.len(20).draw();
			var column = $("#viewApplyOtBatchTable",navTab.getCurrentPanel()).dataTable().api().column(5);
			column.search('', true, false).draw();
	}
	function delOtApplyCallback(OP_FLAG,form,callback) {
		onlyCheckedInfo();
		//$("#viewApplyOtBatchTable",navTab.getCurrentPanel()).dataTable().api().page.len(-1).draw();
		
		$("#BATCH_LOT_OP_FLAG",navTab.getCurrentPanel()).val(OP_FLAG);
		var $form=null;
		if($('#'+form).length>0)
			$form=$('#'+form);
		else
	 		$form = $(form);
		
		if (!$form.valid()) {
			return false;
		}
	    var checked=false;
		var ids= document.getElementsByName("c1");
		
		for(var i=0;i<ids.length;i++){
			if(ids[i].checked){
				checked=true;
			}
		}
		if(!checked){
			alertMsg.error('<spring:message code="alert.message.ess.affirmApply.chooseApplyRecordFirstForBatch"/>'); 
			return false;
		}
		
		if(OP_FLAG ==1){
		  var ids= document.getElementsByName("c1");
		  var checked=false;
			  for(var i=0;i<ids.length;i++){
				 	if(ids[i].checked){
				 			var fromTime = $("#fromTime"+ids[i].value,navTab.getCurrentPanel()).val();
							//var fromTime = document.getElementById('fromTime'+ids[i].value).value;
				 			var toTime = $("#toTime"+ids[i].value,navTab.getCurrentPanel()).val();
							//var toTime = document.getElementById('toTime'+ids[i].value).value;
							if(fromTime.length != 4){
								alertMsg.error('请输入合法的开始时间!');
								return false;
							}
						    if(toTime.length != 4){
								alertMsg.error('请输入合法的结束时间!');
								return false;
							}
				     }
			    }		   
	     }
	    $form.attr("action","/ar/attendanceMintenance/delLOvertimeApplyInBatch");
	    var msg = "确定要删除吗？";
	    if(OP_FLAG == 1){
	        var msg = "确定要提交吗？";
	    }
	    alertMsg.confirm(msg,{okCall:function(){
				$.ajax({
					type: form.method || 'POST',
					url:$form.attr("action"), 
					data:$form.serializeArray(),
					dataType:"json",
					cache: false,
					success: function(data){ //请求成功后处理函数。
						if(data.statusCode=="200"){
							navTabSearch(document.viewArOvertimeManagent);
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
		return false;
	}
	
//-->

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
  var ids = document.getElementsByName("c1");
 
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
  var ids = document.getElementsByName("c1");
 
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
  var ids = document.getElementsByName("c1");
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
 var ids = document.getElementsByName("c1");
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
	onlyCheckedInfo();
	$("#viewApplyOtBatchTable",navTab.getCurrentPanel()).dataTable().api().page.len(-1).draw();
    var checked=false;
		var ids= document.getElementsByName("c1");
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
  var CONFIRM_FLAG=document.getElementById("CONFIRM_FLAG").value;
  var ADJSTYN =document.getElementById("ADJSTYN_FILL").value;
  var otherReason=document.getElementById("otherReason").value;
  var fromTime=document.getElementById("fromTime").value;
  if(fromTime !="" && fromTime != null){
      var  resultfromTime = checkTime(fromTime);
	  if(resultfromTime == false){
	    return false;
	  }
  }
  var toTime=document.getElementById("toTime").value;
  if(toTime !="" && toTime != null){
  var  resulttoTime = checkTime(toTime);
	  if(resulttoTime == false){
	    return false;
	  }
  }
  var fillAffirmFlag=document.getElementById("FILLAFFIRMFLAG").value;
  var obj = document.getElementById("FILLAFFIRMFLAG");
  var txt = obj.options[obj.selectedIndex].text;
  //工作形态
  var work_time_shift=document.getElementById("work_time_shift").value;
  var obj3 = document.getElementById("work_time_shift");
  var txt3 = obj3.options[obj3.selectedIndex].text;
  
  
   
  //刷新后全部反应的内容还存在
  document.getElementById("otherReason1").value=otherReason;
  document.getElementById("fromTime1").value=fromTime;
  document.getElementById("toTime1").value=toTime;
  document.getElementById("reason1").value=reason;
  document.getElementById("FILLAFFIRMFLAG1").value=fillAffirmFlag;
  document.getElementById("work_time_shift1").value=work_time_shift;
  document.getElementById("CONFIRM_FLAG1").value=CONFIRM_FLAG;
  document.getElementById("ADJSTYN_OT").value=ADJSTYN;
  
  

  if(otherReason != "" && otherReason != null){
       textMuli("otherReason",otherReason);  
  }
  if(fromTime !="" && fromTime != null){
     textMuli("fromTime",fromTime); 
  }
  if(toTime !="" && toTime != null){
     textMuli("toTime",toTime);     
  }
  
  //工作形态
  if(work_time_shift !=null && work_time_shift != ""){
	  //textMuli("valibl_value_SHIFT_NO",work_time_shift);
	  //textMuli("valibl_input_SHIFT_NO",txt3);
	  selMuli("valibl_value_SHIFT_NO",work_time_shift);
  }
  if(reason !=null && reason != ""){
	  selMuli("valibl_value_reason",reason);
      /*  textMuli("valibl_value_reason",reason);
       textMuli("valibl_input_reason",document.getElementById("reason").options[document.getElementById("reason").selectedIndex].text);	 */
  }
  if(ADJSTYN !=null && ADJSTYN != ""){
    checkYN("ADJST_YN",ADJSTYN);
  }
  if(CONFIRM_FLAG == "1"){
      textMuli("CONFIRM_FLAG",CONFIRM_FLAG);
      textMuli("APPLY_LOCK",CONFIRM_FLAG);
      htmlMuli("CONFIRM_FLAGTEXT",'Y');
	  $("#viewApplyOtBatchTable",navTab.getCurrentPanel()).dataTable().api().fixedColumns().update();
  }
  if(CONFIRM_FLAG == "0"){
	  textMuli("CONFIRM_FLAG",'0');
	  textMuli("APPLY_LOCK",CONFIRM_FLAG);
	  htmlMuli("CONFIRM_FLAGTEXT",'');
	  $("#viewApplyOtBatchTable",navTab.getCurrentPanel()).dataTable().api().fixedColumns().update();
  } 
  
      calPoTLengthForFill();//计算加班时长
 
  if(fillAffirmFlag != "" && fillAffirmFlag != null){
	 selMuli("valibl_value_AFFIRM_FLAG",fillAffirmFlag);
     /* textMuli("valibl_value_AFFIRM_NO",fillAffirmFlag);  	
     textMuli("valibl_input_AFFIRM_NO",txt);   */
  }else{
    return;
  } 
}


function getWorkTimeStartEndTime(){
   var work_time_shift=document.getElementById("work_time_shift").value;
   var obj3 = document.getElementById("work_time_shift");
   var txt3 = obj3.options[obj3.selectedIndex].text;
   var startTime = txt3.substr(0,4);
   var endTime = txt3.substr(5,4);
    var dateType = txt3.substr(9,1);
   if(work_time_shift != ''&& work_time_shift != null){
     if(dateType == '休'){
		   document.getElementById("fromTime").value = startTime;
		   document.getElementById("toTime").value = startTime;
	  }else{
	      document.getElementById("fromTime").value = startTime;
	      document.getElementById("toTime").value = endTime;
	  }
   }else{
       document.getElementById("fromTime").value = '';
	   document.getElementById("toTime").value = '';
   }
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




//计算时长
function calPoTLength(event ,j){
     var e= event ? event : window.event; 
     var keyCode = e.which ? e.which : e.keyCode;
	if(keyCode==13){
		 var	from_date = $("#APPLY_DATE"+j).val();
		 var cpnyId = document.getElementById("CPNY_ID").value;
		 var fromTime1 =  document.getElementById("fromTime"+j).value;
		 if(fromTime1.length==4){
			  var resultfromTime =   checkTime(fromTime1);
			  if(resultfromTime == false){
			    $("#fromTime"+j).val('');
			    $("#fromTime"+j).focus();
			  }
			  $('#c1'+j).attr('checked','checked');
		 }
		 var fromTime = fromTime1.substr(0,2)+":"+fromTime1.substr(2,2);
			 var toTime1 = document.getElementById("toTime"+j).value;
		 if(toTime1.length==4){
				var resultToTime =   checkTime(toTime1);
				if(resultToTime == false){
				   $("#toTime"+j).val('');
				   $("#toTime"+j).focus();
				 }
				$('#c1'+j).attr('checked','checked');
		  }
			var toTime = toTime1.substr(0,2)+":"+toTime1.substr(2,2);
			var FIRST_TIME = document.getElementById("FIRST_TIME"+j).value;
			var LAST_TIME = document.getElementById("LAST_TIME"+j).value;
			var SHIFT_NO = $('#valibl_value_SHIFT_NO'+j,navTab.getCurrentPanel()).val();
			//var SHIFT_NO = document.getElementById("valibl_value_SHIFT_NO"+j).value;
		    if(fromTime1.length!=4){
		        $("#fromTime"+j).val('');
		        $("#fromTime"+j).focus();
		        $("#shenqingshichangText"+j).html(0+"小时"+0+"分");
			    $("#shenqingshichang"+j).val(0);
			    $("#Lotlengthonehour"+j).val(0);
				$("#Lotlengthonemin"+j).val(0);
		    }
		    if(toTime1.length !=4){
		        $("#toTime"+j).val('');
			    $("#toTime"+j).focus();
			    $("#shenqingshichangText"+j).html(0+"小时"+0+"分");
			    $("#shenqingshichang"+j).val(0);
			    $("#Lotlengthonehour"+j).val(0);
				$("#Lotlengthonemin"+j).val(0);
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
						document.getElementById('shenqingshichangText'+j).innerHTML = hour+"小时"+min+"分";
						$("#shenqingshichang"+j).val((hour*60+min)/60);
						$("#Lotlengthonehour"+j).val(hour);
				        $("#Lotlengthonemin"+j).val(min);
					}
				});
			}
		}
	}
}


//填充的时候计算时长
//计算时长
function calPoTLengthForFill(){
	var ids = document.getElementsByName("c1");
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
					var from_date = $("#APPLY_DATE"+j).val();
				    var cpnyId = document.getElementById("CPNY_ID").value;
				    var fromTime1 =  document.getElementById("fromTime"+j).value;
				    var fromTime = fromTime1.substr(0,2)+":"+fromTime1.substr(2,2);
				    var toTime1 = document.getElementById("toTime"+j).value;
				    var toTime = toTime1.substr(0,2)+":"+toTime1.substr(2,2);
				    var FIRST_TIME = document.getElementById("FIRST_TIME"+j).value;
				    var LAST_TIME = document.getElementById("LAST_TIME"+j).value;
				    var SHIFT_NO = document.getElementById("valibl_value_SHIFT_NO"+j).value;
				   
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
								   document.getElementById('shenqingshichangText'+j).innerHTML = hour+"小时"+min+"分";
								   $("#shenqingshichang"+j).val((hour*60+min)/60);
								   $("#Lotlengthonehour"+j).val(hour);
				                    $("#Lotlengthonemin"+j).val(min);
								 }
							});
						  }
						}
		             }
		          }
		      }
		  
}

function onLoadFunction(applyNo){   
    var workTimeDoc = document.getElementById('valibl_value_SHIFT_NO'+applyNo);
    var workTime = workTimeDoc.options[workTimeDoc.selectedIndex].text;
    document.getElementById('fromTime'+applyNo).value=workTime.substr(0,4);
	document.getElementById('toTime'+applyNo).value=workTime.substr(5,4); 
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

function searchPop_ar0702(flag) {
	var name = encodeURI(encodeURI($("#seach_KEY", navTab.getCurrentPanel()).val()));
	$("#searchPop_ar0702", navTab.getCurrentPanel())
			.attr(
					'href',
					'/pa/workManagement/viewEmpForPopList?pageNum=1&numPerPage=10&limit=ar&seach_KEY='
							+ name);
	if (flag == 'onkeyup')
		$("#searchPop_ar0702", navTab.getCurrentPanel()).click();
}

function selectAdjust(){
   if(document.getElementById("seach_ADJSTYN").checked){
   $('#ADJSTYN').val(1);
   }else{
    $('#ADJSTYN').val(0);
   }
}
function selectattenState(){
   if(document.getElementById("seach_attenState").checked){
   $('#attenState').val(1);
   }else{
    $('#attenState').val(0);
   }
}
function isDate(dateStr){
	var DATE_FORMAT = /^[0-9]{4}-[0-1]?[0-9]{1}-[0-3]?[0-9]{1}$/;
	return DATE_FORMAT.test(dateStr);
}
function checkSearchDateFun(dateId,dateId2,flag){
	var sDateTxt = $('#'+dateId, navTab.getCurrentPanel()).val() ;
	var eDateTxt = $('#'+dateId2, navTab.getCurrentPanel()).val() ;
	//先验证日期格式
	if(isDate(sDateTxt)&&isDate(eDateTxt)){
		var startDate = new Date(sDateTxt.replace(/-/g,"/"));
		var endDate = new Date(eDateTxt.replace(/-/g,"/"));
		if(flag == 'sDate'){
			if(startDate > endDate)
				$('#'+dateId2, navTab.getCurrentPanel()).val(sDateTxt);
		}else{
			if(endDate < startDate)
				$('#'+dateId, navTab.getCurrentPanel()).val(eDateTxt);
		}
	}
}
</script>
<div   id="viewApplyOtBatch"  class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/ar/attendanceMintenance/viewArOvertimeManagent_new?firstFlag=N" method="post"
		id="viewArOvertimeManagent" name="viewArOvertimeManagent">
		<div class="searchBar">
				<table class="searchContent">
				<tr>
				    <input type="hidden" id="fromTime1"  name="fromTime1"  size="3"  value="${fromTime1}"/>
				    <input type="hidden" id="toTime1"  name="toTime1"  size="3" value="${toTime1}"/>
				    <input type="hidden" id="reason1"  name="reason"  size="3" value="${reason}"/>
				    <input type="hidden" id="CONFIRM_FLAG1"  name="CONFIRM_FLAG1"  size="3" value="${CONFIRM_FLAG1}"/>
				    <input type="hidden" id="otherReason1" name="otherReason"  value="${otherReason}"/>
				    <input type="hidden" id="FILLAFFIRMFLAG1" name="FILLAFFIRMFLAG"  value="${FILLAFFIRMFLAG}"/>
				    <input type="hidden" id="work_time_shift1" name="work_time_shift1"  value="${work_time_shift1}"/>
				    <input type="hidden" id="ADJSTYN_OT" name="ADJSTYN_OT"  value=""/>
					
					<td>社号/姓名</td>
					<td>
						<input type="text" name="seach_KEY" id="seach_KEY"
							value="${KEY}" style="float:left;" 
						onkeydown="javascript:if(event.keyCode == 13)searchPop_ar0702('onkeyup');" />
						<a class="btnLook" id="searchPop_ar0702"
						onclick="searchPop_ar0702()" href="#" lookupGroup="person"> </a>
						<input id="dwz.person.empInfo"   name="empInfo" type="text" readonly lookupGroup="person" size="80" value="${empInfo}"/> 
					</td>
				</tr>
				<tr>
					<td width="10%">
						<spring:message code="ess.workgroup.title.duration" text="期间"/>
					</td>
					<td width="20%">
					  <input type="text" name="seach_FROM_DATE" id="seach_FROM_DATE"  class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="${FROM_DATE }" onchange="checkSearchDateFun('seach_FROM_DATE','seach_TO_DATE','sDate');"/>
					</td>
					<td width="30%">
					     <input type="text" name="seach_TO_DATE" id="seach_TO_DATE"  class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="${TO_DATE }"onchange="checkSearchDateFun('seach_FROM_DATE','seach_TO_DATE','eDate');"/>
					</td>
					<td width="30%">审批状态 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						  <ait:selectCodeMulti id="seach_AFFIRM_FLAG" name="seach_AFFIRM_FLAG_NAME" parentNo="14014304" selected="${AFFIRM_FLAG}"  selectedNm="${AFFIRM_FLAG_NAME}"/>
						  <img alt="清除" src="/resources/images/newImages/Modify_little.gif" style="vertical-align:middle ;"
							onclick="$('input[name=seach_AFFIRM_FLAG_NAME]',navTab.getCurrentPanel()).attr('value','');$('input[name=seach_AFFIRM_FLAG]',navTab.getCurrentPanel()).attr('value','');">
					</td>
				</tr>
				<tr>
					<td width="10%">员工类型</td>
						<td width="20%">
				 	<ait:SelectSyCodeByCpnyID id="seach_EMP_TYPE_CODE" name="seach_EMP_TYPE_CODE" parentNo="13864" selected="${EMP_TYPE_CODE}" cnpyID="${LoginUser.cpnyId}" limit="all"/>
						</td>
					<td width="30%">工作形态&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					       <select name="seach_SHIFT_NO" id="seach_SHIFT_NO" >
					            <option value="">请选择</option> 
								<c:forEach items="${workTimeList}" var="item">
									<option value="${item.SHIFT_NO}" <c:if test="${item.SHIFT_NO eq SHIFT_NO}">selected</c:if>
											>
									        ${item.SHIFT_SHORTNAME}
								</c:forEach>
							</select>
					 </td>
					 <td width="30%">班组&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
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
						<ait:deptList name="seach_DEPTNO" cpnyId="${defaultCpny}" limit="ar" id="viewApplyOtInfoBatchList_seachDept" selected="${DEPTNO}"/>
						<ait:deptTreeIcon name="seach_DEPTNO" cpnyId="${defaultCpny}" limit="ar" id="viewApplyOtInfoBatchList_seachDept" selected="${DEPTNO}"/>
					</td>
				</c:if>
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
				<td width="30%">
				<c:if test="${attenState eq '1'}">
				<input type="checkbox" id="seach_attenState" value="1" checked="checked" onclick="selectattenState()"/> ALL
				</c:if>
				<c:if test="${attenState eq '0'}">
				<input type="checkbox" id="seach_attenState" value="0"  onclick="selectattenState()"/> ALL
				</c:if>
				<input type="hidden"  id="attenState" name="attenState"  value="${attenState}" />
				
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;       
					含调休：
					<c:if test="${ADJSTYN eq '1'}">
					    <input type="checkbox"  id="seach_ADJSTYN"  onclick="selectAdjust()" checked="checked" value="1" />
					</c:if>
					<c:if test="${ADJSTYN eq '0'}">
					    <input type="checkbox"  id="seach_ADJSTYN"  onclick="selectAdjust()"  value="" />
					</c:if>
					 <input type="hidden"  id="ADJSTYN" name="ADJSTYN"  value="${ADJSTYN}" />
				</td>				
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
				   <td width="10%">
				               时间
				   </td>
				   <td width="30%" colspan="0">
						<input type="text" class="textInputNew" id="fromTime"  name="fromTime"  size="3"  value="${fromTime1}"/>
						~
						<input type="text" class="textInputNew" id="toTime"  name="toTime"  size="3" value="${toTime1}"/>
				   </td>
				   <td width="30%">工作形态&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					       <select name="work_time_shift" id="work_time_shift" onchange="getWorkTimeStartEndTime();">
					            <option value="">请选择</option> 
								<c:forEach items="${workTimeList}" var="item">
									<option value="${item.SHIFT_NO}" <c:if test="${item.SHIFT_NO eq work_time_shift1}">selected</c:if>
											>
									        ${item.SHIFT_SHORTNAME}
								</c:forEach>
							</select>
					 </td>
					 <td width="30%">
					   锁定状态：
					  <select id="CONFIRM_FLAG" name="CONFIRM_FLAG" >
					     <option value="">请选择</option>
					     <option value="1"  <c:if test="${CONFIRM_FLAG1 eq '1'}">selected</c:if>
											>Confirmed</option>
					     <option value="0"<c:if test="${CONFIRM_FLAG1 eq '0'}">selected</c:if>
											>Unconfirmed</option>
					  </select>
					</td>
			    </tr>
				<tr>
					<td width="10%">原因</td>
					<td width="40%">
					<ait:SelectSyCodeCombinByCpnyID name="reason" combinParentNo="14014313"  selected="${reason}" cnpyID="${LoginUser.cpnyId}"  limit="all"/>
				     <input type="text" class="textInputNew" id="otherReason" name="otherReason"  value="${otherReason}"/>
					</td >
					<td width="10%">审批状态 : &nbsp;&nbsp;&nbsp;&nbsp;
						<ait:SelectSyCodeCombinByCpnyID name="FILLAFFIRMFLAG" combinParentNo="14014304" selected="${FILLAFFIRMFLAG}"  cnpyID="${LoginUser.cpnyId}"  limit="all" />
					</td>
					<td >
					     是否调休：
					     <select id="ADJSTYN_FILL" name="ADJSTYN_FILL">
					        <option value="">请选择</option>
					        <option value="1" <c:if test="${ADJSTYN_OT eq '1' }">selected</c:if>>Y</option>
					        <option value="0" <c:if test="${ADJSTYN_OT eq '0' }">selected</c:if>>N</option>
					     </select>
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
	<form name="delOtApplyAffirmForm" id="delOtApplyAffirmForm" method="post" action="/ar/attendanceMintenance/delLOvertimeApplyInBatch" 
	  onsubmit="return delOtApplyCallback(this, navTabAjaxDone);">                    
		<table id="viewApplyOtBatchTable" class="orderList" width="2090px;">
			<thead>
			    <tr>
				    <th  rowspan="2" width="20px"><!--NO-->
						NO
					</th>
				    <th  rowspan="2"  width="25px"><!--状态-->
						状态
					</th>
					<th rowspan="2" width="50px"><!--申请人-->
						姓名
					</th >
				    <th rowspan="2" width="55px"><!--社号-->
						社号
					</th>
					<th rowspan="2"  width="25px;">
						<input type="checkbox" id="c1Group" />
				    </th>
				    <th  rowspan="2" ></th>
					<th  rowspan="2" width="95px;">
						部门
					</th>
					<!--
					<th rowspan="2">
						职级
					</th>
					-->
					<th rowspan="2" width="70px;"><!--日期-->
						日期
					</th>
					<th rowspan="2" width="42px;"><!--星期-->
						星期
					</th>
					<th rowspan="2" width="48px;"><!--类型-->
						类型
					</th>
					<th rowspan="2" width="95px;"><!--班组-->
						班组
					</th>
					<th rowspan="2" width="70px;"><!--考勤-->
						考勤
					</th>
					<th colspan="8" ><!--申请-->
						申请
					</th>
					<th colspan="2" ><!--原因-->
						原因
					</th>
					<th  rowspan="2" width="105px;"><!--审批状态-->
						审批状态
					</th>
					<th colspan="6"><!--加班累计-->
						加班累计
					</th>
					<th rowspan="2" width="50px"><!--输入者-->
						输入者
					</th>
					<th rowspan="2" width="70px;"><!--输入时间-->
						输入时间
					</th>
					<th rowspan="2" width="50px"><!--最终修改人-->
						修改人
					</th>
					<th rowspan="2" width="70px;"><!--修改时间-->
						修改时间
					</th>
				</tr>
				<tr>
					<th width="125px;"><!--工作形态-->
						工作形态
					</th>
					<th width="40px;"><!--进门-->
						进门
					</th>
					<th width="40px;"><!--出门-->
						出门
					</th>
					<th width="40px;"><!--开始-->
						开始
					</th>
					<th width="40px;"><!--结束-->
						结束
					</th>
					<th width="60px"><!-- 加班时间-->
					          时长
					</th>
					<th width="30px"><!-- 是否调休-->
					          调休
					</th>
					<th width="35px"><!-- 中夜班津贴  -->
					          津贴
					</th>
					<th width="84px;"><!--原因-->
						原因
					</th>
					<th width="200px;"><!--其他原因-->
						其他原因
					</th>
					<th width="45px"><!--加班合计-->
						合计
					</th>
					<th width="45px"><!--平时-->
						平时
					</th>
					<th width="45px"><!--周末-->
						周末
					</th >
					<th width="45px"><!--法定节假日-->
						法定
					</th>
					<th width="45px"><!--综合加班-->
						综合
					</th>
					<th width="45px"><!--月平均-->
						平均
					</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${oTAffirmList}" var="otApply" varStatus="i">	
					<tr target="sid" rel="${admin.personId}" 
					  <c:if test="${otApply.AFFIRM_FLAG eq '14014307' || otApply.AFFIRM_FLAG eq '14014311'}">style="color: blue;"</c:if>
							<c:if test="${otApply.AFFIRM_FLAG eq '14014309' || otApply.AFFIRM_FLAG eq '14014310'}">style="color: red;"</c:if>
					>
					    <td  style="text-align: center">${i.count}</td>
					    <td  style="text-align: center">
					         <div id="CONFIRM_FLAGTEXT${otApply.APPLY_NO}"> ${otApply.CONFIRM_FLAG}</div>
					        <input id="CONFIRM_FLAG${otApply.APPLY_NO}" name="CONFIRM_FLAG${otApply.APPLY_NO}" type="hidden"  value="${otApply.CONFLAG}"/>
					        <input id="APPLY_LOCK${otApply.APPLY_NO}" name="APPLY_LOCK${otApply.APPLY_NO}" type="hidden"  value="${otApply.APPLY_LOCK}"/>
                            <input id="dwz.person.AFFIRMOR_IDApplyLeave${otApply.APPLY_NO}" name="personid${otApply.APPLY_NO}" type="hidden"  value="${otApply.PERSON_ID}"/>
					    </td>
					    <td  style="text-align: center">${otApply.LOCAL_NAME}</td>
					    <td style="text-align: center">
					     <a href="/ess/viewDept/viewApplyDeptPersonalInfo?EMPID=${otApply.EMPID}&LOCAL_NAME= ${otApply.LOCAL_NAME}" 
					     target="dialog" style="color: blue;" title="考勤个人信息" title="考勤个人信息"   [ mask=true ] width="1200" height="400"> 
					     ${otApply.EMPID}</a>
					    </td>
					    <td  style="text-align: center">
							<input type="checkbox" id="c1${otApply.APPLY_NO }" name="c1" value="${otApply.APPLY_NO}"/>
					       <%--  <input type="checkbox" id="c1${otApply.APPLY_NO}" name="c1" value="${otApply.APPLY_NO}" /> --%>
						     <input id="UNIT${otApply.APPLY_NO}" name="UNIT${otApply.APPLY_NO}" type="hidden"  value="${otApply.UNIT}"/>
						     <input id="STATUS_CODE${otApply.APPLY_NO}" name="STATUS_CODE${otApply.APPLY_NO}" type="hidden"  value="${otApply.STATUS_CODE}"/>
						     <input id="STATUS_NAME${otApply.APPLY_NO}" name="STATUS_NAME${otApply.APPLY_NO}" type="hidden"  value="${otApply.STATUS_NAME}"/>
						     <input id="IWEEK${otApply.APPLY_NO}" name="IWEEK${otApply.APPLY_NO}" type="hidden"  value="${otApply.IWEEK}"/>
						     <input id="LOCK_YN${otApply.APPLY_NO}" name="LOCK_YN${otApply.APPLY_NO}" type="hidden"  value="${otApply.LOCK_YN}"/>
						     <input id="CPNY_ID" name="CPNY_ID" type="hidden"  value="${LoginUser.cpnyId}"/>
					    </td>
					    <td  style="text-align: center"></td>
					    <td style="text-align: center"title="${otApply.DEPARTMENT}">
					        <%-- ${fn:substring(otApply.DEPARTMENT,0,2)}... --%>
					        ${otApply.DEPARTMENT}
					    </td>
					    <td style="text-align: center">${otApply.AR_DATE_STR_VIEW}
					       <input type="hidden" id="APPLY_DATE${otApply.APPLY_NO}" name="APPLY_DATE${otApply.APPLY_NO}"   value="${otApply.AR_DATE_STR}"  />
					    </td>
					    <td  style="text-align: center">${otApply.WEEKDAY}</td>
					    <td style="text-align: center">
					      	<select name="DATE_TYPE${otApply.APPLY_NO}" onchange="$('#c1${otApply.APPLY_NO}',navTab.getCurrentPanel()).attr('checked','checked');">
					      		<c:forEach items="${dateTypeLsit}" var="item" varStatus="i">
					      			<option value="${item.DATE_TYPE}" <c:if test="${item.DATE_TYPE == otApply.DATE_TYPE}">selected</c:if> >${item.DATE_TYPE_NAME}</option>
					      		</c:forEach>
					      	</select>
							<input type="hidden" id="OLD_DATE_TYPE${otApply.APPLY_NO}" name="OLD_DATE_TYPE${otApply.APPLY_NO}" value="${otApply.DATE_TYPE}"/>
					    </td>
					      <td style="text-align: center">
					      	<select name="GROUP_ID${otApply.APPLY_NO}" onchange="$('#c1${otApply.APPLY_NO}',navTab.getCurrentPanel()).attr('checked','checked');">
					      		<c:forEach items="${codeList4}" var="item" varStatus="i">
					      			<option value="${item.CODE_NO}" <c:if test="${item.CODE_NO == otApply.GROUP_ID}">selected</c:if> >${item.CODE_NAME}</option>
					      		</c:forEach>
					      	</select>
							<input id="OLD_GROUP_ID${otApply.APPLY_NO}" name="OLD_GROUP_ID${otApply.APPLY_NO}" type="hidden"  value="${otApply.GROUP_ID}"/>
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
					    </td>
					    <td  style="text-align: center" >
					      	<select id="valibl_value_SHIFT_NO${otApply.APPLY_NO}" name="SHIFT_NO${otApply.APPLY_NO}" onchange="$('#c1${otApply.APPLY_NO}',navTab.getCurrentPanel()).attr('checked','checked');onLoadFunction(${otApply.APPLY_NO});">
					      		<c:choose>
					      			<c:when test="${'1440' eq otApply.DATE_TYPE }">
							      		<c:forEach items="${workTimeList1}" var="item" varStatus="i">
							      			<option value="${item.SHIFT_NO}" <c:if test="${item.SHIFT_NO == otApply.SHIFT_NO}">selected</c:if> >${item.SHIFT_SHORTNAME}</option>
							      		</c:forEach>
							      		<c:forEach items="${workTimeList2}" var="item" varStatus="i">
							      			<option value="${item.SHIFT_NO}" <c:if test="${item.SHIFT_NO == otApply.SHIFT_NO}">selected</c:if> >${item.SHIFT_SHORTNAME}</option>
							      		</c:forEach>
					      			</c:when>
					      			<c:otherwise>
							      		<c:forEach items="${workTimeList2}" var="item" varStatus="i">
							      			<option value="${item.SHIFT_NO}" <c:if test="${item.SHIFT_NO == otApply.SHIFT_NO}">selected</c:if> >${item.SHIFT_SHORTNAME}</option>
							      		</c:forEach>
							      		<c:forEach items="${workTimeList1}" var="item" varStatus="i">
							      			<option value="${item.SHIFT_NO}" <c:if test="${item.SHIFT_NO == otApply.SHIFT_NO}">selected</c:if> >${item.SHIFT_SHORTNAME}</option>
							      		</c:forEach>
					      			</c:otherwise>
					      		</c:choose>
					      	</select>
							<input id="OLD_SHIFT_NO${otApply.APPLY_NO}" name="OLD_SHIFT_NO${otApply.APPLY_NO}" type="hidden" value="${otApply.SHIFT_NO}" />
							<!-- 工作形态的开始结束 -->
							<input type="hidden" id="FIRST_TIME${otApply.APPLY_NO}" name="FIRST_TIME${otApply.APPLY_NO}" value="${otApply.FIRST_TIME}"/> 
							<input type="hidden" id="LAST_TIME${otApply.APPLY_NO}" name="LAST_TIME${otApply.APPLY_NO}" value="${otApply.LAST_TIME}"/> 
					    </td>
					   
					    <td style="text-align: center">${otApply.INDOOR_DATE}</td>
					    <td  style="text-align: center">${otApply.OUTDOOR_DATE}</td>
					    <td style="text-align: center">
					        <input type="text" width="100%" size="4" class="textInputNew" id="fromTime${otApply.APPLY_NO}" name="fromTime${otApply.APPLY_NO}" value="${otApply.FROM_TIME}" onkeyup="$('#c1${otApply.APPLY_NO}').attr('checked','checked');calPoTLength(event,${otApply.APPLY_NO});"onclick="select();"/>  
					    </td>
					    <td style="text-align: center">
					        <input type="text" width="100%" size="4" class="textInputNew" id="toTime${otApply.APPLY_NO}" name="toTime${otApply.APPLY_NO}" value="${otApply.TO_TIME}" onkeyup="$('#c1${otApply.APPLY_NO}').attr('checked','checked');calPoTLength(event,${otApply.APPLY_NO});"onclick="select();"/>
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
					             <input type="checkbox"  id="ADJST_YN${otApply.APPLY_NO}" name="ADJST_YN${otApply.APPLY_NO}" checked="checked"  value="1" />
					       </c:if>
					       <c:if test="${otApply.ITEM_NO ne '141452'}">
					          <input type="checkbox"  id="ADJST_YN${otApply.APPLY_NO}" name="ADJST_YN${otApply.APPLY_NO}"  value="1" />
					        </c:if>
				         </td>
					    <td  style="text-align: center">
					         <input min="-99999999" class="textInputNew" size="3" title="${otApply.ALLOWANCE}" id="allowance${otApply.APPLY_NO}" name="allowance${otApply.APPLY_NO}" value="${otApply.ALLOWANCE}" onkeyup="$('#c1${otApply.APPLY_NO}').attr('checked','checked');"  type="text" onclick="select();"/>
					    </td>
					    <td style="text-align: center">
					      	<select id="valibl_value_reason${otApply.APPLY_NO}" name="reason${otApply.APPLY_NO}" onchange="$('#c1${otApply.APPLY_NO}').attr('checked','checked');">
					      		<option value="">请选择</option>
					      		<c:forEach items="${codeList2}" var="item" varStatus="i">
					      			<option value="${item.CODE_NO}" <c:if test="${item.CODE_NO == otApply.REASON}">selected</c:if> >${item.CODE_NAME}</option>
					      		</c:forEach>
					      	</select>
					    </td>
					    <td  style="text-align: center">
					       <input style="width: 100%;" type="text"class="textInputNew" id="otherReason${otApply.APPLY_NO}" name="otherReason${otApply.APPLY_NO}" value="${otApply.REASON_OTHER}" onkeyup="$('#c1${otApply.APPLY_NO}').attr('checked','checked');" />
					    </td>
					     <td>
					      	<select id="valibl_value_AFFIRM_FLAG${otApply.APPLY_NO}" name="AFFIRM_FLAG${otApply.APPLY_NO}" onchange="$('#c1${otApply.APPLY_NO}').attr('checked','checked');">
					      		<c:forEach items="${codeList}" var="item" varStatus="i">
					      			<option value="${item.CODE_NO}" <c:if test="${item.CODE_NO == otApply.AFFIRM_FLAG}">selected</c:if> >${item.CODE_NAME}</option>
					      		</c:forEach>
					      	</select>
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
					    <td  style="text-align: center" title="${otApply.CREATED_BY}&nbsp; [${otApply.CREATED_IP}]">
					          ${otApply.CREATED_BY}
					    </td>
					    <td  style="text-align: center" title="${otApply.CREATE_DATE}">
					          ${fn:substring(otApply.CREATE_DATE,0,10)}
					    </td>
					    <td  style="text-align: center" title="${otApply.UPDATED_BY} &nbsp; [${otApply.UPDATED_IP}]">
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
</div>