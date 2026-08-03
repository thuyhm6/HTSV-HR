<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script>
$(document).ready(function(){
     $("#viewApplyAttenanceBatchInfoListHAE_Serch",navTab.getCurrentPanel()).click(function(){
			$("#viewApplyAttenanceBatchInfoListHAE",navTab.getCurrentPanel()).submit();
	   });
     $('#LeaveApplyAffirmTable',navTab.getCurrentPanel()).on( 'draw.dt', function () {
			initEditFun_ess3431();
			getLeaveTypeCode_ess3431();
		} );
	$(".orderList",navTab.getCurrentPanel()).dataTable({
		"bPaginate": false,    //分页
	    "bAutoWidth":false,//表格宽度自动变化
	    "bProcessing":true,
	    "lengthMenu": [[10,20,35, 50], [10,20, 35, 50]],
		"bLengthChange": false,  //按多少条记录显示下拉框
		"iDisplayLength": 10, //默认每页显示的记录数
		//"bFilter": false,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
     	"searching": false,//本地搜索
		"bSort": true,   //排序功能
		"bInfo": false,   //显示datatables的信息（底部的页数，条目数信息）
		"bScrollInfinite":true,
	     "orderClasses": false,
	     "order":[],//初始化不用自动排序
	     "scrollY": $(document.body).height() - 400,
	     "scrollX": $(document.body).width(),
	     "scrollCollapse": false,
	     "deferRender":true,
	        "columnDefs": [//自定义排序类型
		                     { "orderable": false, "targets": [1] }
	                     ],
	    "fixedColumns":false,
        "oLanguage": {//多语言配置
		//正在加载中......
    	"sProcessing": "<spring:message code='ess.message.loading' />",
        //查询不到相关数据！
        "sZeroRecords": "<spring:message code='ess.message.NOT_FOUND_DATA' />",
        //表中无数据存在！
        "sEmptyTable": "<spring:message code='ess.message.NOT_FOUND_DATA_FROM_TABLE' />",
        //快速筛选
        "sSearch": "<spring:message code='ess.message.rapid_screening' />",
        //每页 _MENU_ 条记录
        "sLengthMenu": "<spring:message code='ess.message.page_of_lines' />",
        //从 _START_ 到 _END_ /共 _TOTAL_ 条数据
        "sInfo": "<spring:message code='ess.message.sum_begin_to_end' />",
        //(从 _MAX_ 条记录过滤)
        "sInfoFiltered": "<spring:message code='ess.message.filter_from_max' />",
        "oPaginate": {
            //上一页
            "sPrevious": "<spring:message code='ess.message.previous_page' />",
            //下一页
            "sNext": "<spring:message code='ess.message.next_page' />"
            }
        },
        "sDom":'<"top"r<"clear">fBl>t<"bottom"ip<"clear">>',
        "buttons": [] 
		});
	getAffirmor();
	init_ess3431();
});
function init_ess3431(){
	var ids= document.getElementsByName("BATCH_LEAVE");
	for(var i=0;i<ids.length;i++){
			var index = ids[i].id.substring(12);
			callength(index);
	}
}
function getLeaveTypeCode_ess3431(){
	$.ajax({
		type:'post',
		url:'/ess/infoApplyAttendance/getLeaveTypeCode' ,
		data:null,
		dataType:"json",
		cache: false,
		success: function(res){
			$.each($("input[name='BATCH_LEAVE']"),function(i, obj) {
				var index = obj.id.substring(12);
				$("#LEAVE_TYPE_CODE_NAME_"+index).attr("sysValue",res.LEAVE_TYPE_CODE);
			});
		},
		error: DWZ.ajaxError
		});
}
function initEditFun_ess3431(){
	$('.orderList tbody tr td:[sysLog="text"]',navTab.getCurrentPanel()).editable({type:'text',
		onblur:function(val,settings){
			var index = $(this).attr("sysIndex");
			$("#BATCH_LEAVE_"+index,navTab.getCurrentPanel()).attr("checked",true); 
			$(this).html(val);
			$("#modifyFlag_" + index,navTab.getCurrentPanel()).html("modify");
			this.editing = false;
		}
	});
	$('.orderList tbody tr td:[sysLog="date"]',navTab.getCurrentPanel()).editable({type:'date1',
		onblur:function(val,settings){
			var index = $(this).attr("sysIndex");
			$("#BATCH_LEAVE_"+index,navTab.getCurrentPanel()).attr("checked",true); 
			$("#modifyFlag_" + index,navTab.getCurrentPanel()).html("modify");
			this.editing = false;
		}
	});
	$('.orderList tbody tr td:[sysLog="select"]').editable({type:'select',
		onblur:function(val,settings){
		    var leaveTypeCode = $('select', this).val();
			$(this).html(val);
			var index = $(this).attr("sysIndex");

			//如果是休假类型，则取出code放入隐藏域
			if($(this).attr("id").substring(0,20) == 'LEAVE_TYPE_CODE_NAME'){
				$("#LEAVE_TYPE_CODE_" + index,navTab.getCurrentPanel()).val(leaveTypeCode);
		    }
			
			callength(index);
			 $("#BATCH_LEAVE_"+index,navTab.getCurrentPanel()).attr("checked",true); 
			$("#modifyFlag_" + index,navTab.getCurrentPanel()).html("modify");
			this.editing = false;
		}
	});
	$('.orderList tbody tr td:[sysLog="lookUp"]').editable({type:'lookUp',
		onblur:function(val,settings){
			var index = $(this).attr("sysIndex");
			$(this).html(val);
			submitKeyClick_applyAttenance(val,index);
			 $("#BATCH_LEAVE_"+index,navTab.getCurrentPanel()).attr("checked",true); 
			$("#modifyFlag_" + index,navTab.getCurrentPanel()).html("modify");
			this.editing = false;
		}
	});
	var ids= document.getElementsByName("BATCH_LEAVE");
	for(var i=0;i<ids.length;i++){
			var index = ids[i].id.substring(12);
			callength(index);
	}
}
function saveApplyAttenanceBatchInfo(){
	var jsonData = '[';
		$.each($("input[name='BATCH_LEAVE']"),function(i, obj) {
			if (obj.checked) {
				if (jsonData.length > 1) {
					jsonData += ',{';
				} else {
					jsonData += '{';
				}
				var index = obj.id.substring(12);
				jsonData += ' "APPLY_NO": "' + obj.value + '" ,';
				jsonData += ' "PERSON_ID": "' + $("#EMPID_"+index,navTab.getCurrentPanel()).attr('sysPersonId') + '" ,';
				jsonData += ' "LOCAL_NAME": "' + $("#LOCAL_NAME_"+index,navTab.getCurrentPanel()).html()+ '" ,';
				jsonData += ' "LEAVE_TYPE_CODE":"'+ $("#LEAVE_TYPE_CODE_"+index,navTab.getCurrentPanel()).val() +'" ,';
				jsonData += ' "LEAVE_FROM_DATE":"'+ $("#FROM_DATE_"+index,navTab.getCurrentPanel()).html() +'" ,';
				jsonData += ' "LEAVE_TO_DATE":"'+ $("#TO_DATE_"+index,navTab.getCurrentPanel()).html() +'" ,';
				jsonData += ' "LEAVE_FROM_TIME":"'+ $("#FROM_DATE_"+index,navTab.getCurrentPanel()).html() +" "+ $("#FROM_TIME_"+index,navTab.getCurrentPanel()).html() +'" ,';
				jsonData += ' "LEAVE_TO_TIME":"'+ $("#TO_DATE_"+index,navTab.getCurrentPanel()).html() +" "+ $("#TO_TIME_"+index,navTab.getCurrentPanel()).html()+'" ,';
				jsonData += ' "APPLY_LENGTH":"'+ $("#APPLY_LENGTH_"+index,navTab.getCurrentPanel()).val() +'" ,';
				jsonData += ' "LEAVE_REASON":"'+ $("#LEAVE_REASON_"+index,navTab.getCurrentPanel()).html() +'"';
				jsonData += '}';
			}
		});

		jsonData += ']';

		var affirmJsonData = '[';
		var tb2 = document.getElementById("addApplyLeaveAffirm_list");
	   	if(tb2.rows.length == 0){
	   		alertMsg.error("<spring:message code='alert.message.pleaseFirstSetRuler.b' />");//请先设置决裁者
			return false;
	   	}else{
            var affirms = $("[name ='AFFIRMOR_ID']",navTab.getCurrentPanel());
            for(var i=0;i<affirms.length;i++){
            	if (affirmJsonData.length > 1) {
            		affirmJsonData += ',{';
				} else {
					affirmJsonData += '{';
				}
				if(affirms[i].value == "" || affirms[i].value == null){
					alertMsg.error("<spring:message code='alert.message.Please_Affirmor_Complete.b' />");//请将裁决者信息补充完整!
	                return false;
	            }
               var rowId = affirms[i].id.substring(32);
               affirmJsonData += ' "AFFIRMOR_ID": "' + affirms[i].value + '" ,';
               affirmJsonData += ' "AFFIRM_LEVEL": "' + $("#rowIndex_"+rowId,navTab.getCurrentPanel()).text() + '"';
               affirmJsonData += '}';
            }
		}

	   	affirmJsonData += ']';
		
		var ids= document.getElementsByName("BATCH_LEAVE");
		for(var i=0;i<ids.length;i++){
			if(ids[i].checked){
				checked=true;
				var index = ids[i].id.substring(12);
				var sysPersonId = $("#EMPID_"+index,navTab.getCurrentPanel()).attr('sysPersonId');
				//判定可优化 直接调用 CHECK_LEAVE_TIME_SPC_法人  函数，或者去后台调函数，像个人考勤申请那样
				if(sysPersonId == null||sysPersonId==''){
					alertMsg.error("<spring:message code='ar.viewarcardrecord.title.empidnotnull' />");//社号不能为空
					return false;
				}
				if($("#APPLY_LENGTH_"+index,navTab.getCurrentPanel()).val()=='0' || $("#APPLY_LENGTH_"+index,navTab.getCurrentPanel()).val()==''){
					alertMsg.error("<spring:message code='ar.viewApplyAttenanceManagentInfoList.SHICHANGBUNENGDENGYULING.b' />");//时长不能等于0
					return false;
				} 
				leaveFromDate=$("#FROM_DATE_"+index,navTab.getCurrentPanel()).html() +" "+ $("#FROM_TIME_"+index,navTab.getCurrentPanel()).html();
				leaveToDate=$("#TO_DATE_"+index,navTab.getCurrentPanel()).html() +" "+ $("#TO_TIME_"+index,navTab.getCurrentPanel()).html();
				var applyNo = $("#BATCH_LEAVE_"+index,navTab.getCurrentPanel()).val();
				if(leaveFromDate.substring(0,1)=="<"){
					alertMsg.error("<spring:message code='ar.viewApplyAttenanceManagentInfoList.QINGQUEDINGKAISHIRIQI.b' />");//请确定开始日期
					return false;
				}
				if(leaveToDate.substring(0,1)=="<"){
					alertMsg.error("<spring:message code='ar.viewApplyAttenanceManagentInfoList.QINGQUEDINGJIESHURIQI.b' />");//请确定结束日期
					return false;
				}
				$.ajax({
					 cache: false,
					 type: 'post',
					 url: '/hrm/recruitManage/doSql',
					 data:{sql:"select AR_GET_LEAVE_CLASH('"+applyNo+"','"+sysPersonId+"',to_char(to_date('" + leaveFromDate + "','DD-MM-YYYY HH24:MI'),'YYYY-MM-DD HH24:MI'),to_char(to_date('" + leaveToDate + "','DD-MM-YYYY HH24:MI'),'YYYY-MM-DD HH24:MI')) FLAG from dual"},
					 dataType:"json",
					 success: function(data) {
						var flag = data.result[0].FLAG;
						if(flag>0){
							alertMsg.error("<spring:message code='ar.viewApplyAttenanceManagentInfoList.KAOQINCHONGTUJIANCHASHIJIAN.b' />");//与已有考勤冲突，请检查该时间内是否已经申请
							return false;
						}else if(flag == -1){
							alertMsg.error("<spring:message code='ar.viewApplyAttenanceManagentInfoList.BAOHANKAOQINGUANBIDESHIJIAN.b' />");//包含考勤关闭的时间
							return false;
						}else if(flag == -2){
							alertMsg.error("<spring:message code='ar.viewArOvertimeManaget_fast.Include_apply_closed.b' />");//包含申请关闭的日期
							return false;
						}
					 },
					 error:DWZ.ajaxError
				}); 
				if($("#LEAVE_TYPE_CODE_"+index,navTab.getCurrentPanel()).val()==''){
					alertMsg.error("<spring:message code='ar.viewApplyAttenanceManagentInfoList.KAOQINBUNENGWEIKONG.b' />");//考勤状态不能为空
					return false;
				}
				if($("#LEAVE_TYPE_CODE_"+index,navTab.getCurrentPanel()).val()=='26'){//年假
					$.ajax({
						 cache: false,
						 type: 'post',
						 url: '/hrm/recruitManage/doSql',
						 data:{sql:"select AR_GET_DAY_HOURS('" + sysPersonId + "',to_char(to_date('" + leaveFromDate + "','DD-MM-YYYY HH24:MI:SS'),'YYYY-MM-DD HH24:MI:SS')) DAY_HOURS from dual"},
						 dataType:"json",
						 success: function(data) {
							var dayHours = data.result[0].DAY_HOURS;
							/* var shengYu = data.result[0].SHENGYU_VAC; */
								if(parseFloat($("#APPLY_LENGTH_"+index,navTab.getCurrentPanel()).val()) > parseFloat($("#SHENGYU_VAC_CNT_"+index,navTab.getCurrentPanel()).html())* dayHours){
									alertMsg.error("<spring:message code='ar.viewApplyAttenanceManagentInfoList.NIANJIATIANSHUBUZU.b' />");//年假天数不足
									return false;
								}
						 },
						 error:DWZ.ajaxError
					});
				}
			}
		}
		if (jsonData.length == 2) {
			//请选择要添加的数据
			alertMsg.error("<spring:message code='ar.alert.message.viewardetail.choosetoadd'/>");
			return false;
		}
		alertMsg.confirm("<spring:message code='ar.alert.message.viewArAnnualStandard.consubmit'/>",{
	 		okCall:function(){
		 		$.ajax({
		 			type:'post',
		 			url:'/ess/infoApplyAttendance/saveAttendanceApplyInfoForBatchHAE',
		 			data:[{ name: 'jsonData', value: jsonData },{ name: 'affirmJsonData', value: affirmJsonData }],
		 			dataType:"json",
					cache: false,
					success: function(data){ //请求成功后处理函数。
						if(data.statusCode=="200"){
							navTabSearch($("#viewApplyAttenanceBatchInfoListHAE"));
							alertMsg.correct(data.message);
						}else{
							if(data.result=="2"){
								alertMsg.info(data.message);
							}else{
								alertMsg.error(data.message);
							}
						}   
			   	 	},
					error: DWZ.ajaxError
		 		});
	 		}
		});
	 	return false;
}
function addApplyAttenanceBatchInfo(){
	var href = "/ess/infoApplyAttendance/viewBatchApplyEmpList?pageNum=1&supervisor=1&addType=21";
	$.pdialog.open(href,"ess3431","<spring:message code='hrm.empinfo.EMPLOYEE_SEARCH.Z'/>", {width:800,height:400,mask:true});//员工搜索
}
 
 function delLeaveApplyCallbackBatch(OP_FLAG,form,callback) {
		var $form=null;
		if($('#'+form).length>0)
			$form=$('#'+form);
		else
	 		$form = $(form);
		
		if (!$form.valid()) {
			return false;
		}
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
	    $form.attr("action","/ess/infoApplyAttendance/delAttendanceApplyInfoForBatch");
	    alertMsg.confirm ("<spring:message code='ar.viewApplyAttenanceManagentInfoList.QUEDINGPILIANGQUXIAOMA.b' />",{//确定要批量取消吗?
	        okCall:function(){
		    	$.ajax({
					type: form.method || 'POST',
					url:$form.attr("action"),
					data:$form.serializeArray(),
					dataType:"json",
					cache: false,
					success: function(data){ //请求成功后处理函数。
						if(data.statusCode=="200"){
							navTabSearch($("#viewApplyAttenanceBatchInfoListHAE"));
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
function fillItem(){
	var leaveReason = $("#LEAVE_REASON",navTab.getCurrentPanel()).val();
	var leaveTypeCode = $("#LEAVE_TYPE_CODE",navTab.getCurrentPanel()).val();
	var fromDate = $("#FROM_DATE",navTab.getCurrentPanel()).val();
	var toDate =$("#TO_DATE",navTab.getCurrentPanel()).val();
	var fromTime = $("#FROM_TIME",navTab.getCurrentPanel()).val();
	var toTime = $("#TO_TIME",navTab.getCurrentPanel()).val();
	var ids= document.getElementsByName("BATCH_LEAVE");
	var checked=false;
	var leave_type_code_name = "";
	$.ajax({
		 cache: false,
		 type: 'post',
		 async:false,
		 url: '/hrm/recruitManage/doSql',
		 data:{sql:"select get_code_name('"+ leaveTypeCode +"','${LoginUser.language}') LEAVE_TYPE_CODE_NAME from dual" },
		 dataType:"json",
		 success: function(data) {
			length = data.result[0].LEAVE_LENGTH;
			leave_type_code_name = data.result[0].LEAVE_TYPE_CODE_NAME;
			for(var i=0;i<ids.length;i++){
				if(ids[i].checked){
					checked=true;
					var index = ids[i].id.substring(12);
					$("#LEAVE_TYPE_CODE_NAME_"+index).html(leave_type_code_name);
					$("#LEAVE_TYPE_CODE_"+index).val(leaveTypeCode);
					if(leaveReason!=""){
						$("#LEAVE_REASON_"+index).html(leaveReason);
					}
					if(fromDate!=""&&toDate!=""){
						$("#FROM_DATE_"+index,navTab.getCurrentPanel()).html(fromDate);
						$("#TO_DATE_"+index,navTab.getCurrentPanel()).html(toDate);
						$("#FROM_TIME_"+index,navTab.getCurrentPanel()).html(fromTime);
						$("#TO_TIME_"+index,navTab.getCurrentPanel()).html(toTime);
						callength(index);
					}
				}
			}
		 },
		 error:DWZ.ajaxError
	});	
	if(!checked){
		alertMsg.error('<spring:message code="ar.viewApplyAttenanceManagentInfoList.QINGXUANZEXIUGAINEIRONG.b" />'); //请选择要修改的内容
		return false;
	}
}
$('.orderList tbody tr td:[sysLog="text"]',navTab.getCurrentPanel()).editable({type:'text',
	onblur:function(val,settings){
		var index = $(this).attr("sysIndex");
		$("#BATCH_LEAVE_"+index,navTab.getCurrentPanel()).attr("checked",true); 
		$(this).html(val);
		$("#modifyFlag_" + index,navTab.getCurrentPanel()).html("modify");
		this.editing = false;
	}
});
$('.orderList tbody tr td:[sysLog="date"]',navTab.getCurrentPanel()).editable({type:'date1',
	onblur:function(val,settings){
		var index = $(this).attr("sysIndex");
		$("#BATCH_LEAVE_"+index,navTab.getCurrentPanel()).attr("checked",true); 
		$("#modifyFlag_" + index,navTab.getCurrentPanel()).html("modify");
		this.editing = false;
	}
});
$('.orderList tbody tr td:[sysLog="select"]').editable({type:'select',
	onblur:function(val,settings){
		$(this).html(val);
		var index = $(this).attr("sysIndex");
		callength(index);
		 $("#BATCH_LEAVE_"+index,navTab.getCurrentPanel()).attr("checked",true); 
		$("#modifyFlag_" + index,navTab.getCurrentPanel()).html("modify");
		this.editing = false;
	}
});
$('.orderList tbody tr td:[sysLog="lookUp"]').editable({type:'lookUp',
	onblur:function(val,settings){
		var index = $(this).attr("sysIndex");
		$(this).html(val);
		submitKeyClick_applyAttenance(val,index);
		 $("#BATCH_LEAVE_"+index,navTab.getCurrentPanel()).attr("checked",true); 
		$("#modifyFlag_" + index,navTab.getCurrentPanel()).html("modify");
		this.editing = false;
	}
});
function callength(index){
	var personId = $("#EMPID_" + index,navTab.getCurrentPanel()).attr('sysPersonId');
	var leaveTypeCode=$("#LEAVE_TYPE_CODE_"+index,navTab.getCurrentPanel()).val();
	var leaveFromDate=$("#FROM_DATE_"+index,navTab.getCurrentPanel()).html() +" "+ $("#FROM_TIME_"+index,navTab.getCurrentPanel()).html();
	var leaveToDate=$("#TO_DATE_"+index,navTab.getCurrentPanel()).html() +" "+ $("#TO_TIME_"+index,navTab.getCurrentPanel()).html();
	if(leaveFromDate.substring(0,1)=="<"){
		alertMsg.error("<spring:message code='ar.viewApplyAttenanceManagentInfoList.QINGQUEDINGKAISHIRIQI.b' />");//请确定开始日期
		return false;
	}
	if(leaveToDate.substring(0,1)=="<"){
		alertMsg.error("<spring:message code='ar.viewApplyAttenanceManagentInfoList.QINGQUEDINGJIESHURIQI.b' />");//请确定结束日期
		return false;
	}
	if(leaveTypeCode=='16415'){//哺乳假
		leaveFromDate = $("#FROM_DATE_"+index,navTab.getCurrentPanel()).html() + " 00:00:00";
		leaveToDate = $("#TO_DATE_"+index,navTab.getCurrentPanel()).html() +" 23:00:00";
		$.ajax({
			 cache: false,
			 type: 'post',
			 async:false,
			 url: '/hrm/recruitManage/doSql',
			 data:{sql:"select AR_GET_DAY_HOURS('"+ personId +"',to_char(to_date('" + leaveFromDate + "','DD-MM-YYYY HH24:MI:SS'),'YYYY-MM-DD HH24:MI:SS')) DAY_HOURS,GET_AR_LEAVE_LENGTH('" + personId + "','${LoginUser.cpnyId}',to_date('" + leaveFromDate + "','DD-MM-YYYY HH24:MI:SS'),to_date('" + leaveToDate+"','DD-MM-YYYY HH24:MI:SS'),'"+ leaveTypeCode + "') LEAVE_LENGTH from dual"},
			 dataType:"json",
			 success: function(data) {
				 $("#APPLY_LENGTH_"+index,navTab.getCurrentPanel()).val(data.result[0].LEAVE_LENGTH);
				var lengthText = "";
				var length = Math.floor(data.result[0].LEAVE_LENGTH/data.result[0].DAY_HOURS) ;
				if( length > 0 ){
					lengthText += length + "<spring:message code='ar.viewitemparameter.title.dayofunit' />";//天
				}
				if(lengthText == ""){
					$("#APPLY_LENGTH_TEXT_"+index,navTab.getCurrentPanel()).html("0<spring:message code='ar.viewitemparameter.title.xiaoshi' />");//小时
				}else{
					$("#APPLY_LENGTH_TEXT_"+index,navTab.getCurrentPanel()).html(lengthText);
				}
			 }
		});
	}else{
		$.ajax({
			 cache: false,
			 type: 'post',
			 async:false,
			 url: '/hrm/recruitManage/doSql',
			 data:{sql:"select AR_GET_DAY_HOURS('" + personId + "',to_char(to_date('" + leaveFromDate + "','DD-MM-YYYY HH24:MI:SS'),'YYYY-MM-DD HH24:MI:SS')) DAY_HOURS,GET_AR_LEAVE_LENGTH('" + personId + "','${LoginUser.cpnyId}',to_date('" + leaveFromDate + "','DD-MM-YYYY HH24:MI:SS'),to_date('" + leaveToDate+"','DD-MM-YYYY HH24:MI:SS'),'"+ leaveTypeCode + "') LEAVE_LENGTH from dual"},
			 dataType:"json",
			 success: function(data) {
				$("#APPLY_LENGTH_"+index,navTab.getCurrentPanel()).val(data.result[0].LEAVE_LENGTH);
				var lengthText = "";
				var dayHours = data.result[0].DAY_HOURS;
				var length = Math.floor(data.result[0].LEAVE_LENGTH/dayHours) ;
				if( length > 0 ){
					lengthText += length + "<spring:message code='ar.viewitemparameter.title.dayofunit' />";//天
				}
				var length = data.result[0].LEAVE_LENGTH % dayHours ;
				if(length > 0 ){
					lengthText += length + "<spring:message code='ar.viewitemparameter.title.xiaoshi' />";//小时
				}
				if(lengthText == ""){
					$("#APPLY_LENGTH_TEXT_"+index,navTab.getCurrentPanel()).html("0<spring:message code='ar.viewitemparameter.title.xiaoshi' />");//小时
				}else{
					$("#APPLY_LENGTH_TEXT_"+index,navTab.getCurrentPanel()).html(lengthText);
				}
			 },
			 error:DWZ.ajaxError
		});
	}
	getAttendanceInformation(index);
}
function submitKeyClick_applyAttenance(obj,index){
	var empid=obj.replace(/[ ]/g," ");
	var personIdStr="applyAttendance";
	if(empid != ''){
	   	$.ajax({
			type: 'POST',
			url: encodeURI('/sys/affirm/getPersonCntByEmpid?EMPID='+empid ),
			dataType:"json",
			cache: false,
			success: function(jsonObject){
				 if(jsonObject.perCnt != 1 ){
					document.getElementById("onckSche").href=encodeURI(encodeURI("/ar/attendanceMintenance/viewAddAffirmEvsList?isEmployeement=1&limit=ar&pageNum=1"
							+'&seach_KEY='+empid+'&personidStr='+personIdStr + '&index=' + index));
					document.getElementById("onckSche").click();
				}
				if(jsonObject.perCnt==1){
					$("#EMPID_" + index,navTab.getCurrentPanel()).html(jsonObject.empId);
					$("#EMPID_" + index,navTab.getCurrentPanel()).attr('sysPersonId',jsonObject.personId);
					$("#DEPT_NAME_" +  index,navTab.getCurrentPanel()).html(jsonObject.deptName);
					$("#LOCAL_NAME_" + index,navTab.getCurrentPanel()).html(jsonObject.empName);
					$("#DUTY_NO_" + index,navTab.getCurrentPanel()).val(jsonObject.dutyNo);
					$("#SHIFT_NAME_" + index,navTab.getCurrentPanel()).html(jsonObject.shiftName);
				} ;
				getAttendanceInformation(index);
			},
			error: DWZ.ajaxError
		});
	}else{
		$("#EMPID_" + index,navTab.getCurrentPanel()).html("");
		$("#DEPT_NAME_" +  index,navTab.getCurrentPanel()).html("");
		$("#LOCAL_NAME_" + index,navTab.getCurrentPanel()).html("");
	}
}

function getAttendanceInformation(index){
	var PERSON_ID = $("#EMPID_"+index,navTab.getCurrentPanel()).attr('sysPersonId');
	var applyBatchdate = $("#FROM_DATE_"+index,navTab.getCurrentPanel()).html();

	var year = applyBatchdate.substring(6,10);
	var month = applyBatchdate.substring(3,5);
	var day = applyBatchdate.substring(0,2);
	applyBatchdate = year + '-' + month + '-' + day;
	
	if(PERSON_ID != ''){
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
		    	    $("#TOT_VAC_CNT_"+index).html(data.TOT_VAC_CNT);
					$("#SHENGYU_VAC_CNT_"+index).html(data.SHENGYU_VAC_CNT);
					/*$("#TOT_SHENGYU_CNT_"+index,navTab.getCurrentPanel()).html(data.THREE_TOTAL_TX-data.THREE_USE_TX);*/
				},
				error: DWZ.ajaxError
	      });
	 }
}

function getAffirmor(){
	var applyTypeNo = $("#APPLY_TYPE_NO",navTab.getCurrentPanel()).val();
	var htm = '';
	$.ajax({
		type: 'POST',
		url: '/ess/infoApplyAttendance/viewAffirmorList',
		data:[{ name: 'applyTypeCode', value: '18135' }, //默认事假
		      { name: 'applyLength', value: '0' }, //默认0
		      { name: 'applyTypeNo', value: applyTypeNo }],
		dataType:"json",
		cache: false,
		async:false,
		success: function(data){
			if(data.affirmorList.length>0){
				for(var i=0;i<data.affirmorList.length;i++){
					var count = $("#applyLeaveCount").val();
					htm +='<tr id="rowIdApplyLeave'+ count +'"><td class="td_type" style="text-align: center" width="5%" id="rowIndex_'+ count +'"><span name="rowIndex">'+(i+1)+'</span></td>';
			        htm +='<td class="td_type" style="text-align: center" width="20%">';
			        htm +='<input type="hidden" id="approvType' + count + '" name="approvType" value="1" />';
			        htm +='<input type="hidden" name="approvTypeIndex" value="' + count + '" />';
			        //htm +='<input id="dwz.person.AFFIRMOR_IDApplyLeave' + count + '" name="ATT_AFFIRMOR_ID" value="'+ data.affirmorList[i].AFFIRMOR_ID +'" type="hidden" />';
			        htm +='<input id="dwz.person.AFFIRMOR_IDApplyLeave' + count + '" name="AFFIRMOR_ID" value="'+ data.affirmorList[i].AFFIRMOR_ID +'" type="hidden"/>';
			        //htm += data.affirmorList[i].LOCAL_NAME;
			        htm +='<input id="dwz.person.EMPNAMEApplyLeave' + count + '" value="'+data.affirmorList[i].LOCAL_NAME+'"  type="text"  size="24" disabled="disabled"/>';
			        htm +='</td>';
					htm +='<td class="td_type" style="text-align: center" width="20%">';
					//htm += data.affirmorList[i].EMPID;
					htm +='<input id="dwz.person.EMPINFOApplyLeave' + count + '" name="empid" value="'+data.affirmorList[i].EMPID+'" type="text" size="25" class="required" lookupGroup="person" onkeydown="submitKeyClick_applyLeave(this,' + count + ',event)"/>';
					htm +='</td>';
					htm +='<td class="td_type" style="text-align: center" width="20%">';
					//htm += data.affirmorList[i].DEPTNAME;
					htm +='<input id="dwz.person.DEPTNAMEApplyLeave' + count + '" value="'+data.affirmorList[i].DEPTNAME+'" type="text"  size="30" disabled="disabled"/>';
					htm +='</td>';
					htm +='<td class="td_type" style="text-align: center" width="20%">';
					//htm += data.affirmorList[i].POSITION_NAME;
					htm +='<input id="dwz.person.POSITIONApplyLeave' + count + '" value="'+data.affirmorList[i].POSITION_NAME+'" type="text"  size="30" disabled="disabled"/>';
					htm +='</td>';
					htm +='<td class="td_type" style="text-align: center" width="15%">';
					htm +='<img src="/resources/images/+.gif" title="<spring:message code="button.add"/>"';	
					htm +='border="0" align="absmiddle" style="cursor:hand" onclick="addRowByIDApplyLeave(' + count + ')"/>&nbsp;&nbsp;&nbsp;';
					htm +='<img src="/resources/images/-.gif" title="<spring:message code="button.delete"/>"';	
					htm +='border="0" align="absmiddle" style="cursor:hand" onclick="javaScript:document.all.addApplyLeaveAffirm_list.deleteRow(event.srcElement.parentElement.parentElement.rowIndex);changeApplyLeaveLevel();"/></td></tr>';
			  	$("#applyLeaveCount",navTab.getCurrentPanel()).val(++count) ;
				} 
				$("#rowIdApplyLeaveq",navTab.getCurrentPanel()).html(htm);
			}else{
				$("#rowIdApplyLeaveq",navTab.getCurrentPanel()).html('');
			}
		},
		error: DWZ.ajaxError
	});
}

function addRowByIDApplyLeaveFirst(){
	var count = parseInt($("#applyLeaveCount").val());
    var htm  ='<tr id="rowIdApplyLeave'+ count +'"><td class="td_type" style="text-align: center" width="5%" id="rowIndex_'+ count +'"><span name="rowIndex">'+(i+1)+'</span></td>';
        htm +='<td class="td_type" style="text-align: center" width="20%">';
        htm +='<input type="hidden" id="approvType' + count + '" name="approvType" value="1" />';
        htm +='<input type="hidden" name="approvTypeIndex" value="' + count + '" />';
        //htm +='<input id="dwz.person.AFFIRMOR_IDApplyLeave' + count + '" name="ATT_AFFIRMOR_ID" value="" type="hidden" />';
        htm +='<input id="dwz.person.EMPNAMEApplyLeave' + count + '"  type="text"  size="24" disabled="disabled"/>';
        htm +='</td>';        
		htm +='<td class="td_type" style="text-align: center" width="20%">';
		htm +='<input id="dwz.person.AFFIRMOR_IDApplyLeave' + count + '" name="AFFIRMOR_ID" value="" type="hidden"/>';
		htm +='<input id="dwz.person.EMPINFOApplyLeave' + count + '" name="empid" alt="<spring:message code="evs.affirm.please_input_enter.e"/>" type="text" size="25" class="required" lookupGroup="person" onkeydown="submitKeyClick_applyLeave(this,' + count + ',event)"/>';
		htm +='</td>';
		htm +='<td class="td_type" style="text-align: center" width="20%">';
		htm +='<input id="dwz.person.DEPTNAMEApplyLeave' + count + '"  type="text"  size="30" disabled="disabled"/>';
		htm +='</td>';
		htm +='<td class="td_type" style="text-align: center" width="20%">';
		htm +='<input id="dwz.person.POSITIONApplyLeave' + count + '"  type="text"  size="30" disabled="disabled"/>';
		htm +='</td>';
		htm +='<td class="td_type" style="text-align: center" width="15%">';
		htm +='<img src="/resources/images/+.gif" title="<spring:message code="button.add"/>"';	
		htm +='border="0" align="absmiddle" style="cursor:hand" onclick="addRowByIDApplyLeave(' + count + ')"/>&nbsp;&nbsp;&nbsp;';
		htm +='<img src="/resources/images/-.gif" title="<spring:message code="button.delete"/>"';	
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
  	$("#applyLeaveCount").val(++count);
}

function addRowByIDApplyLeave(currentRowID){
	var count = parseInt($("#applyLeaveCount").val());
	var htm  ='<tr id="rowIdApplyLeave'+ count +'"><td class="td_type" style="text-align: center" width="5%" id="rowIndex_'+ count +'"><span name="rowIndex">'+(i+1)+'</span></td>';
    htm +='<td class="td_type" style="text-align: center" width="20%">';
    htm +='<input type="hidden" id="approvType' + count + '" name="approvType" value="1" />';
    htm +='<input type="hidden" name="approvTypeIndex" value="' + count + '" />';
    //htm +='<input id="dwz.person.AFFIRMOR_IDApplyLeave' + count + '" name="ATT_AFFIRMOR_ID" value="" type="hidden" />';
    htm +='<input id="dwz.person.EMPNAMEApplyLeave' + count + '"  type="text"  size="24" disabled="disabled"/>';
    htm +='</td>';        
	htm +='<td class="td_type" style="text-align: center" width="20%">';
	htm +='<input id="dwz.person.AFFIRMOR_IDApplyLeave' + count + '" name="AFFIRMOR_ID" value="" type="hidden"/>';
	htm +='<input id="dwz.person.EMPINFOApplyLeave' + count + '" name="empid" type="text" alt="<spring:message code="evs.affirm.please_input_enter.e"/>" size="25" class="required" lookupGroup="person" onkeydown="submitKeyClick_applyLeave(this,' + count + ',event)"/>';
	htm +='</td>';
	htm +='<td class="td_type" style="text-align: center" width="20%">';
	htm +='<input id="dwz.person.DEPTNAMEApplyLeave' + count + '"  type="text"  size="30" disabled="disabled"/>';
	htm +='</td>';
	htm +='<td class="td_type" style="text-align: center" width="20%">';
	htm +='<input id="dwz.person.POSITIONApplyLeave' + count + '"  type="text"  size="30" disabled="disabled"/>';
	htm +='</td>';
	htm +='<td class="td_type" style="text-align: center" width="15%">';
	htm +='<img src="/resources/images/+.gif" title="<spring:message code="button.add"/>"';	
	htm +='border="0" align="absmiddle" style="cursor:hand" onclick="addRowByIDApplyLeave(' + count + ')"/>&nbsp;&nbsp;&nbsp;';
	htm +='<img src="/resources/images/-.gif" title="<spring:message code="button.delete"/>"';	
	htm +='border="0" align="absmiddle" style="cursor:hand" onclick="javaScript:document.all.addApplyLeaveAffirm_list.deleteRow(event.srcElement.parentElement.parentElement.rowIndex);changeApplyLeaveLevel();"/></td></tr>';

   	//当前行之后插入一行
   	$("#rowIdApplyLeave" + currentRowID).after(htm);
  	$("[id='dwz.person.EMPINFOApplyLeave" + count + "']").inputAlert();
   	changeApplyLeaveLevel();
  	$("#applyLeaveCount").val(++count) ;
}

/*修改裁决者等级*/
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
		var empNameStr = "EMPNAMEApplyLeave"+empIdStr.substring(17);
		var positionIdStr = "POSITIONApplyLeave"+empIdStr.substring(17);
		var deptIdStr = "DEPTNAMEApplyLeave"+empIdStr.substring(17);
		if(empid == ''){
			obj.value=" ";
			document.getElementById("onck").href=encodeURI(encodeURI("/ar/attendanceMintenance/viewAddAffirmList?isEmployeement=1&firstFlag=1&limit=super&pageNum=1"
					+'&seach_KEY='+empid
					+'&empidStr='+empIdStr
					+'&personidStr='+personIdStr
					+'&positionIdStr='+positionIdStr
					+'&deptIdStr='+deptIdStr
					+'&empNameStr='+empNameStr					
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
										+'&positionIdStr='+positionIdStr
										+'&deptIdStr='+deptIdStr 
										+'&empNameStr='+empNameStr				
										));
								document.getElementById("onck").click();
							}
							if(jsonObject.perCnt==1){
								$("[id='dwz.person.EMPNAMEApplyLeave" + index + "']").val(jsonObject.empName);
							  	$("[id='dwz.person.EMPINFOApplyLeave" + index + "']").val(jsonObject.empId);
							  	$("[id='dwz.person.AFFIRMOR_IDApplyLeave" + index + "']").val( jsonObject.personId);
							  	$("[id='dwz.person.POSITIONApplyLeave" + index + "']").val( jsonObject.POSITION_NAME);
							  	$("[id='dwz.person.DEPTNAMEApplyLeave" + index + "']").val( jsonObject.deptName);
							}
						},
				error: DWZ.ajaxError
			});
		}
    }
 }

function excelimport_ess3431(){
	$("#importExcelDialog_ess3431").attr('href','/pa/excelImport/importExcelData?importFunName=/importApplyAttenance');
	$("#importExcelDialog_ess3431").click();
}
function changeURL_ess3431(applyNo){
	var href = "/ess/infoApply/viewApprovaledLeaveInfo?seach_APPLY_NO=" + applyNo;
	$.pdialog.open(href,"ess3431", "<spring:message code='ar.viewApplyAttenanceManagentInfoList.MINGXICHAKAN.b' />", {width:1000,height:600,mask:true});//明细查看
}
</script>
<div class="pageContent">
<table width="100%" border="0" cellpadding="0" cellspacing="0" height="50">
	<tr>
		<td valign="top">
		<br/>
			<table width="100%"  border="0" cellpadding="0" cellspacing="0" class="user_table">
			<tr>
					<td style="text-align:right" class="td_title" width="20%">
						<spring:message code="ar.viewLeaveConfirmList.DAISHENQINGREN.b" />
						<!--代申请人-->
						<input type="hidden" id="APPLY_TYPE_NO"  name="APPLY_TYPE_NO" value="21"/>
					</td>
					<td class="td_type" width="%30">
						${LoginUser.localName}
					</td>
					<td style="text-align:right" class="td_title" width="20%">
						<spring:message code="hr.enpinfo.title.EMP.EMPNUMBER"/>
						<!--社号  -->
					</td>
					<td class="td_type" width="30%">
							${LoginUser.empID}
					</td>
				</tr>
				
				<tr>
					<td style="text-align:right" class="td_title" width="20%">
						<spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/>
						<!--部门  -->
					</td>
					<td class="td_type" width="30%">
							${LoginUser.content }
					</td>
					<td style="text-align:right" class="td_title" width="20%">
						<!--部门长--><spring:message code="hrm.empinfo.HEAD_DEPARTMENT"/>
					</td>
					<td class="td_type" width="30%">
                       ${LoginUser.headDepartment }
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</div>
<a id="importExcelDialog_ess3431"  href="#" target="dialog" mask="true"></a>
<a id="importExcel_ess3431" href="#" target="navTab" mask="true"><span style="display:none;"><!-- 导入结果 --><spring:message code="ess.title.DAORUJIEGUO" /></span></a>
<div id="viewApplyAttenBatch" class="pageHeader" >
    <div class="searchBar">
			<table class="searchContent">
			    <tr>
			       <td><!-- 考勤状态--><spring:message code="ess.infoApply.attendState" /></td>
				   <td>
				   <ait:SelectSyCodeByCpnyID name="LEAVE_TYPE_CODE" id="LEAVE_TYPE_CODE" parentNo="21" selected="" limit="all"/>
				  		
				   </td>
				   <td><!--开始日期--><spring:message code="org.title.STARTDATE" /> </td>
				   <td>
				       <input type="text" name="FROM_DATE" id="FROM_DATE"  class="Wdate" onClick="WdatePicker({dateFmt:'dd-MM-yyyy',lang:'en'})" value=""/>
				   </td>
				   <td><!--开始时间--><spring:message code="ess.infoApply.title.startTime" /></td>
				   <td>
						<ait:time name="FROM_TIME"  spacing="30"   selected=""/>
					</td>
			    </tr>
				<tr>
					<td><!--原因--><spring:message code="hrm.empinfo.reason" /></td>
					<td>
						<input type="text" id="LEAVE_REASON" name="LEAVE_REASON"  value=""/>
					</td >
					<td><!--结束日期--><spring:message code="hrm.recruitManage.END_DATE1" /> </td>
				   <td>
				       <input type="text" name="TO_DATE" id="TO_DATE"  class="Wdate" onClick="WdatePicker({dateFmt:'dd-MM-yyyy',lang:'en'})" value=""/>
				       
				   </td>
					<td><!--结束时间--><spring:message code="ess.infoApply.end_time" /> </td>
					<td>
						<ait:time name="TO_TIME"  spacing="30"  selected=""/>
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul class="toolBar">
	             <li>
	            	 <a class="buttonActive" onclick="fillItem();"><span><!--全部反应--><spring:message code="ess.message.all_reaction" /></span></a>
	             </li>
				</ul>
			</div>
	</div>
</div>
<div class="pageContent" >
<form onsubmit="return navTabSearch(this);" action="/ess/infoApplyAttendance/viewApplyAttenanceBatchInfoHAE" id="viewApplyAttenanceBatchInfoListHAE" method="post">

</form>
<div class="formBar">
	<ul class="toolBar">
	    <!--<li><a class="buttonActive" id="viewApplyAttenanceBatchInfoListHAE_Serch" href="#"><span>查询<spring:message code="org.title.SELECT" /></span></a></li>-->
	    <li><a class="buttonActive" onclick="addApplyAttenanceBatchInfo()"><span><!--添加--><spring:message code="ess.empInfo.insert" /></span></a></li>
		<li><a class="buttonActive" onclick="delLeaveApplyCallbackBatch(0,'delLeaveApplyAffirmFormBatch',DWZ.ajaxDone)"><span><!--删除--><spring:message code="ess.empInfo.Delete" /></span></a></li>
		<li><a class="buttonActive" onclick="saveApplyAttenanceBatchInfo()"><span><!--保存--><spring:message code="ar.viewempcalender.title.save" /></span></a></li>
		<!--<li><a class="buttonActive" href="/pa/excelExport/downloadExcelAttendanceApply?file=AttendanceApply_add" ><span>模板下载<spring:message code="ess.message.template_download" /></span></a></li>
		<li><a class="buttonActive" onclick="excelimport_ess3431()"><span>Excel导入<spring:message code="ess.infoApply.EXCEL_IN" /></span></a></li>
		<li><a class="buttonActive" onclick="downloadExcel('viewApplyAttenanceBatchInfoList','/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=122','/ess/infoApplyAttendance/viewApplyAttenanceBatchInfoList?firstFlag=N&deleteYN=Y')"><span>导出到Excel<spring:message code="hrm.empinfo.EXPORT" /></span></a></li>
	    -->
	</ul>
</div>
	<form name="delLeaveApplyAffirmForm" id="delLeaveApplyAffirmFormBatch" method="post" action="/ess/infoApplyAttendance/delAttendanceApplyInBatchForBatch" 
	  onsubmit="return delLeaveApplyCallbackBatch(this, navTabAjaxDone);">     
	  <input type="hidden" id="nullLeaveAffirmListCnt" value="${nullLeaveAffirmListCnt }">
	  <%-- <div class="user_table" style="font:bold 12px/20px arial,sans-serif;">Total:${nullLeaveAffirmListCnt}</div> --%>
		<table class="orderList" width="2000px" id="LeaveApplyAffirmTable">                                           
			<thead>
				<tr>
				    <th width="5px;" ><!--NO-->
						NO
					</th>
					<th width="5px;" >
				    	<input type="checkbox" class="checkboxCtrl" group="BATCH_LEAVE" />
				    </th>
					<th  width="100px;" ><!--申请人-->
						<!--姓名--><spring:message code="ess.infoApply.NAME" />
					</th>
				    <th width="100px;"  class="titleColor"><!--社号-->
						<!--社号--><spring:message code="ess.infoApply.EMPID" />
					</th>
					<th width="220px;" ><!--部门-->
						<!--部门--><spring:message code="ess.infoApply.DEPT" />
					</th>
					<th width="140px">
						<!-- 班组 --><spring:message code="hr.viewPersonalInfo.title.banzu"/>
					</th>
					<th width="120px;" >
						<!--申请日期--><spring:message code="ess.empInfo.date_application" />
					</th>
					<th style="text-align: center" width="220px;" ><!--年假剩余-->
						<!--年假总数--><spring:message code="ess.infoApply.sum_year_leave_days" />
					</th>
					<th style="text-align: center" width="200px;" ><!--年假剩余-->
						<!--年假剩余--><spring:message code="ess.infoApply.nianjiashengyu" />
					</th>
					<!--<th style="text-align: center"width="50px;" >调休剩余
						调休剩余<spring:message code="ess.title.TIAOXIUSHENGYU" />
					</th>
					-->
					<th style="text-align: center"width="120px;" class="titleColor" ><!--考勤状态-->
						<!--考勤状态--><spring:message code="ess.infoApply.attendState" />
					</th>
					<th style="text-align: center" width="120px;" class="titleColor"><!--班次-->
						<!--开始日期--><spring:message code="org.title.STARTDATE" />
					</th>
					<th style="text-align: center" width="140px;" class="titleColor"><!--开始时间-->
						<!--开始时间--><spring:message code="ess.infoApply.title.startTime" />
					</th>
					<th style="text-align: center" width="120px;" class="titleColor"><!--结束日期-->
						<!--结束日期--><spring:message code="hr.viewTranslate.title.PUBLIC_END_DATE" />
					</th>
					<th style="text-align: center" width="140px;" class="titleColor"><!--结束时间-->
						<!--结束时间--><spring:message code="ess.infoApply.end_time" />
					</th>
					<th style="text-align: center" width="100px;" ><!--时长-->
						<!--时长--><spring:message code="ess.infoApply.duration" />
					</th>
					<th style="text-align: center" width="250px"  class="titleColor"><!--原因-->
						<!--原因--><spring:message code="ess.infoApply.Reason" />
					</th>
					<!--<th style="text-align: center" width="150px">
						附件<spring:message code="ess.empInfo.enclosure" />
					</th>
						<th style="text-align: center" width="170px;" >
							审批者<spring:message code="hrm.contractInfo.APPROVAL_PERSON" />
						</th>
					<th style="text-align: center" width="240px;">
						审批状态<spring:message code="ess.infoApply.approval_status" />
					</th>
					<th style="text-align: center" width="140px;">
						创建者<spring:message code="hrm.contract.creator" />
					</th>-->
				</tr>
			</thead>
			<tbody id="tbody">
				<c:forEach items="${nullLeaveAffirmList}" var="leaveApply" varStatus="i"> 
					<tr target="sid" rel="${admin.personId}" >
					    <td style="text-align: center">${i.count}</td>
					    <td style="text-align: center">
					    	<c:if test="${leaveApply.AFFIRM_FLAG ne '14014309' and leaveApply.AFFIRM_FLAG ne '14014310'}">
					        	<input type="checkbox" id="BATCH_LEAVE_${i.index}" name="BATCH_LEAVE" value="${leaveApply.APPLY_NO}" />
					        </c:if>
					        <c:if test="${leaveApply.AFFIRM_FLAG eq '14014309' or leaveApply.AFFIRM_FLAG eq '14014310'}">
					        	<input type="hidden" id="BATCH_LEAVE_${i.index}" name="BATCH_LEAVE" value="${leaveApply.APPLY_NO}" />
					        </c:if>
					        <input type="hidden" id="PERSON_ID_${i.index}" value="${leaveApply.PERSON_ID}">
					    </td>
					    <!--<td style="text-align: center; color: blue; cursor: pointer;" id="LOCAL_NAME_${i.index}" sysIndex="${i.index}" <c:if test='${leaveApply.PERSON_ID ne null}'> onclick='javascript:changeURL_ess3431(${leaveApply.APPLY_NO });' </c:if>>${leaveApply.LOCAL_NAME}</td>-->
					    <td style="text-align: center;" id="LOCAL_NAME_${i.index}" sysIndex="${i.index}">${leaveApply.LOCAL_NAME}</td>
					    <td style="text-align: center;" id="EMPID_${i.index}" sysIndex="${i.index}" sysPersonId="${leaveApply.PERSON_ID}" >${leaveApply.EMPID}</td>
					    <td style="text-align: center;" id ="DEPT_NAME_${i.index}" sysIndex="${i.index}">${leaveApply.DEPT_NAME}</td>
					    <td style="text-align: center" id ="SHIFT_NAME_${i.index}" sysIndex="${i.index}">${leaveApply.SHIFT_NAME}</td>
					    <input type="hidden" id="DUTY_NO_${i.index}" value="${leaveApply.DUTY_NO}">
					    <input type="hidden" id="POST_FAMILY_${i.index}" value="${leaveApply.POST_FAMILY}">
					    <td  style="text-align: center">${leaveApply.APPLY_TIME}</td>
					    <td style="text-align: center">
					    	<span id="TOT_VAC_CNT_${i.index}">${leaveApply.TOT_VAC_CNT}</span><!--天--><spring:message code="ar.viewitemparameter.title.dayofunit" />
					    </td>
					    <td style="text-align: center">
					    	<span id="SHENGYU_VAC_CNT_${i.index}">${leaveApply.SHENGYU_VAC_CNT}</span><!--天--><spring:message code="ar.viewitemparameter.title.dayofunit" />
					    </td>
					    <!--<td style="text-align: center"><span id="TOT_SHENGYU_CNT_${i.index}"><fmt:formatNumber type="number" value="${(leaveApply.USE_TX)}" maxFractionDigits="1"/></span>小时<spring:message code="ar.viewitemparameter.title.xiaoshi" /></td>-->
				 	    <td style="text-align: center;" sysLog="select" sysIndex="${i.index}" id="LEAVE_TYPE_CODE_NAME_${i.index}" sysValue='${LEAVE_TYPE_CODE }'>${leaveApply.LEAVE_TYPE_CODE_NAME }</td>
				 	    <input type="hidden" id="LEAVE_TYPE_CODE_${i.index}" value="${leaveApply.LEAVE_TYPE_CODE}">
					    <td style="text-align: center;" sysLog="date" sysIndex="${i.index}" format="dd-MM-yyyy" lang="en" id="FROM_DATE_${i.index}" sysFlag="1">${leaveApply.FROM_DATE}</td>
					    <input type="hidden" id="LEAVE_FROM_DATE_${i.index}" value="${leaveApply.FROM_DATE}">
					    <td style="text-align: center;" sysLog="select" sysIndex="${i.index}" id="FROM_TIME_${i.index}" sysValue="${TIME_STR}" sysFlag="TIME">${leaveApply.FROM_TIME }</td>
					    <td style="text-align: center;" sysLog="date" sysIndex="${i.index}" format="dd-MM-yyyy" id="TO_DATE_${i.index}" sysFalg="1">${leaveApply.TO_DATE}</td>
					    <input type="hidden" id="LEAVE_TO_DATE_${i.index}" value="${leaveApply.TO_DATE}">
					    <td style="text-align: center;" sysLog="select" sysIndex="${i.index}" id="TO_TIME_${i.index}" sysValue="${TIME_STR}" sysFlag="TIME">${leaveApply.TO_TIME}</td>
					    <td style="text-align: center" id="APPLY_LENGTH_TEXT_${i.index}">${leaveApply.APPLY_LENGTH}<!--小时--><spring:message code="ar.viewitemparameter.title.xiaoshi" /></td>
					    <input type="hidden" id = "APPLY_LENGTH_${i.index}" value="${leaveApply.APPLY_LENGTH}">
					    <input type="hidden" id = "DUTY_NO_${i.index}" value="${leaveApply.DUTY_NO}">
					    <td sysLog="text" sysIndex="${i.index}" id="LEAVE_REASON_${i.index}">${leaveApply.LEAVE_REASON}</td>
					    <!--<td >
					    	<c:forEach items="${leaveApply.fileList}" var="item" varStatus="j">
									<a href="/ess/infoApplyLeave/downloadFile?fileName=${item.FILE_URL }&file=${item.FILE_NAME}" >${item.FILE_NAME}</a><br/>
							</c:forEach>
						</td> 
					    <td style="text-align: center" id="AFFIRMOR_${i.index}"></td>
					    <td  style="text-align: center">${leaveApply.AFFIRM_FLAG_NAME}&nbsp;<c:if test="${leaveApply.CONFIRM_FLAG ne '1' and leaveApply.CONFIRM_FLAG ne '2' }">人事未确认<spring:message code="ess.title.WEIQUEREN" /></c:if><c:if test="${leaveApply.CONFIRM_FLAG eq '1' }">人事通过<spring:message code="ess.title.RENSHITONGGUO" /></c:if><c:if test="${leaveApply.CONFIRM_FLAG eq '2' }">人事否决<spring:message code="ess.title.RENSHITONGGUO" /></c:if></td>
					   	<td style="text-align: center">${leaveApply.CREATED_BY}</td>
					    -->
					    <input type="hidden" id = "AFFIRM_FLAG_${i.index}" value="${leaveApply.AFFIRM_FLAG}">
					    <div id="modifyFlag_${i.index}" sysLog="modifyFlag" sysIndex="${i.index}" style="display:none;"></div>
					</tr>
			</c:forEach>
			</tbody>
		</table>
	</form>
	    <table class="user_table" width="100%">	
								<tr>
								    <td class="td_title"  style="text-align:center;" width="5%"><!--序号 --> <spring:message code="org.title.NO" /></td>
									<td class="td_title"  style="text-align:center;" width="20%"><!--决裁者 --> <spring:message code="sys.affirm.title.affirmPerson" /></td>
									<td class="td_title" style="text-align:center;" width="20%"><!--社号--> <spring:message code="hr.enpinfo.title.EMP.EMPNUMBER" /></td>
									<td class="td_title"  style="text-align:center;" width="20%"><!--部门 --> <spring:message code="hr.viewPersonalInfo.title.DEPTNAME" /></td>
									<td class="td_title" style="text-align:center;" width="20%"><!--职责 --> <spring:message code="hrm.contract.POSITION_NO" /></td>
								    <td class="td_title" style="text-align:center;" width="15%"><!--是否新增 --> <spring:message code="evs.viewRegPersonalProbation.SHIFOUXINZENG.a" />(<img src="/resources/images/+.gif" title="<spring:message code="button.add"/>" border="0" align="absmiddle" style="cursor:hand" onclick="addRowByIDApplyLeaveFirst()"/>)</td>
								</tr>
								<tr>
									<td colspan="6">
										<table width="100%" border="0" cellpadding="0" cellspacing="0" id="addApplyLeaveAffirm_list">
											<tbody id="rowIdApplyLeaveq">
											</tbody>
										</table>
				    				<input type="hidden" name="applyLeaveCount" id="applyLeaveCount" value="1">
				    				<a id="onck" name="onck"  href="" lookupGroup="person" rel="submitKeyClick_apply_Leave_affirm"></a>
				    				</td>
		    					</tr>
		    				</table>
	<a id="onckSche" name="onckSche"  href="" lookupGroup="person"></a>
</div>