<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script>
$(document).ready(function(){
	$("#viewPiciOtAffirmLBatchList_Serch",navTab.getCurrentPanel()).click(function(){
		$("#viewPiciOtAffirmLBatchList",navTab.getCurrentPanel()).submit();
   });
	$('input[type=text]').on('keydown', function(e) {
	    if (e.which == 13) {
	    	$("#viewPiciOtAffirmLBatchList",navTab.getCurrentPanel()).submit();
	    }
	});
	 $('#otAffirmLBatchTable',navTab.getCurrentPanel()).on( 'draw.dt', function () {
			initEditFun_ess3403();
			init_ess3403();
		} );
	$(".orderList",navTab.getCurrentPanel()).dataTable({
		"bPaginate": true,    //分页
	    "bAutoWidth":false,//表格宽度自动变化
	    "bProcessing":true,
	    "lengthMenu": [[50,100,200, 500], [50,100,200, 500]],
		"bLengthChange": true,  //按多少条记录显示下拉框
		"iDisplayLength": 50, //默认每页显示的记录数
		"bFilter": true,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
     	"searching": true,//本地搜索
		"bSort": true,   //排序功能
		"bInfo": true,   //显示datatables的信息（底部的页数，条目数信息）
		"bScrollInfinite":true,
	     "orderClasses": false,
	     "order":[],//初始化不用自动排序
	     "scrollY": $(document.body).height() - 390,
	     "scrollX": $(document.body).width(),
	     "scrollCollapse": false,
	     "deferRender":true,
	        "columnDefs": [//自定义排序类型
		                     { "orderable": false, "targets": [1,14,15] }
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
		init_ess3403();
});
function init_ess3403(){
	var ids= document.getElementsByName("c1");
	for(var i=0;i<ids.length;i++){
		var index = ids[i].id.substring(3);
		getAgoAffirmor_ess3403(index);
	}
}
function initEditFun_ess3403(){
	$('.orderList tbody tr td:[sysLog="text"]',navTab.getCurrentPanel()).editable({type:'text',
		onblur:function(val,settings){
			var index = $(this).attr("sysIndex");
			if(!$("#c1_"+index,navTab.getCurrentPanel()).prop("disabled")){
				$("#c1_"+index,navTab.getCurrentPanel()).attr("checked",true); 
			}
			$(this).html(val);
			$("#modifyFlag_" + index,navTab.getCurrentPanel()).html("modify");
			this.editing = false;
		}
	});
	$('.orderList tbody tr td:[sysLog="date"]',navTab.getCurrentPanel()).editable({type:'date1',
		onblur:function(val,settings){
			var index = $(this).attr("sysIndex");
			if(!$("#c1_"+index,navTab.getCurrentPanel()).prop("disabled")){
				$("#c1_"+index,navTab.getCurrentPanel()).attr("checked",true); 
			}
			$("#modifyFlag_" + index,navTab.getCurrentPanel()).html("modify");
			/*$("#ADJUST_YN_"+index,navTab.getCurrentPanel()).attr("checked",false);
			$("#SPECIAL_YN_"+index,navTab.getCurrentPanel()).attr("checked",false);*/
			$("#OFFSET_YN_"+index,navTab.getCurrentPanel()).attr("checked",false);
			this.editing = false;
		}
	});
	$('.orderList tbody tr td:[sysLog="select"]').editable({type:'select',
		onblur:function(val,settings){
			$(this).html(val);
			var index = $(this).attr("sysIndex");
			otAffirm_callength(index,2);
			if(!$("#c1_"+index,navTab.getCurrentPanel()).prop("disabled")){
				$("#c1_"+index,navTab.getCurrentPanel()).attr("checked",true); 
			}
			$("#modifyFlag_" + index,navTab.getCurrentPanel()).html("modify");
			this.editing = false;
		}
	});
	$('.orderList tbody tr td:[sysLog="lookUp"]').editable({type:'lookUp',
		onblur:function(val,settings){
			var index = $(this).attr("sysIndex");
			$(this).html(val);
			submitKeyClick_OtAffirm(val,index);
			if(!$("#c1_"+index,navTab.getCurrentPanel()).prop("disabled")){
				$("#c1_"+index,navTab.getCurrentPanel()).attr("checked",true); 
			}
			$("#modifyFlag_" + index,navTab.getCurrentPanel()).html("modify");
			this.editing = false;
		}
	});
}
$('.orderList tbody tr td:[sysLog="text"]',navTab.getCurrentPanel()).editable({type:'text',
	onblur:function(val,settings){
		var index = $(this).attr("sysIndex");
		if(!$("#c1_"+index,navTab.getCurrentPanel()).prop("disabled")){
			$("#c1_"+index,navTab.getCurrentPanel()).attr("checked",true); 
		}
		$(this).html(val);
		$("#modifyFlag_" + index,navTab.getCurrentPanel()).html("modify");
		this.editing = false;
	}
});
$('.orderList tbody tr td:[sysLog="date"]',navTab.getCurrentPanel()).editable({type:'date1',
	onblur:function(val,settings){
		var index = $(this).attr("sysIndex");
		if(!$("#c1_"+index,navTab.getCurrentPanel()).prop("disabled")){
			$("#c1_"+index,navTab.getCurrentPanel()).attr("checked",true); 
		}
		$("#modifyFlag_" + index,navTab.getCurrentPanel()).html("modify");
		/*$("#ADJUST_YN_"+index,navTab.getCurrentPanel()).attr("checked",false);
		$("#SPECIAL_YN_"+index,navTab.getCurrentPanel()).attr("checked",false);*/
		$("#OFFSET_YN_"+index,navTab.getCurrentPanel()).attr("checked",false);
		this.editing = false;
	}
});
$('.orderList tbody tr td:[sysLog="select"]').editable({type:'select',
	onblur:function(val,settings){
		$(this).html(val);
		var index = $(this).attr("sysIndex");
		otAffirm_callength(index,2);
		if(!$("#c1_"+index,navTab.getCurrentPanel()).prop("disabled")){
			$("#c1_"+index,navTab.getCurrentPanel()).attr("checked",true); 
		}
		$("#modifyFlag_" + index,navTab.getCurrentPanel()).html("modify");
		this.editing = false;
	}
});
$('.orderList tbody tr td:[sysLog="lookUp"]').editable({type:'lookUp',
	onblur:function(val,settings){
		var index = $(this).attr("sysIndex");
		$(this).html(val);
		submitKeyClick_OtAffirm(val,index);
		if(!$("#c1_"+index,navTab.getCurrentPanel()).prop("disabled")){
			$("#c1_"+index,navTab.getCurrentPanel()).attr("checked",true); 
		}
		$("#modifyFlag_" + index,navTab.getCurrentPanel()).html("modify");
		this.editing = false;
	}
});
function saveOtApplyAffirm(){
	var jsonData = '[';
	$.each($("input[name='c1']"),function(i, obj) {
		if (obj.checked) {
			if (jsonData.length > 1) {
				jsonData += ',{';
			} else {
				jsonData += '{';
			}
			var index = obj.id.substring(3);
			//var adjusr = $("#ADJUST_YN_"+index,navTab.getCurrentPanel()).attr("checked");
			var offset = $("#OFFSET_YN_"+index,navTab.getCurrentPanel()).attr("checked");
			var deduct = $("#DEDUCT_YN_"+index,navTab.getCurrentPanel()).attr("checked");
			var apply_ot_date = $("#APPLY_OT_DATE_"+index,navTab.getCurrentPanel()).html();
			apply_ot_date = apply_ot_date.substring(6,10)+"-"+apply_ot_date.substring(3,5)+"-"+apply_ot_date.substring(0,2);
			//var special = $("#SPECIAL_YN_"+index,navTab.getCurrentPanel()).attr("checked");
			/*if(adjusr=='checked'){
				$("#ADJUST_YN_"+index,navTab.getCurrentPanel()).val("1");
			}else{
				$("#ADJUST_YN_"+index,navTab.getCurrentPanel()).val("0");
			}
			if(special=='checked'){
				$("#SPECIAL_YN_"+index,navTab.getCurrentPanel()).val("1");
			}else{
				$("#SPECIAL_YN_"+index,navTab.getCurrentPanel()).val("0");
			}*/
			if(offset=='checked'){
				$("#OFFSET_YN_"+index,navTab.getCurrentPanel()).val("1");
			}else{
				$("#OFFSET_YN_"+index,navTab.getCurrentPanel()).val("0");
			}
			if(deduct=='checked'){
				$("#DEDUCT_YN_"+index,navTab.getCurrentPanel()).val("1");
			}else{
				$("#DEDUCT_YN_"+index,navTab.getCurrentPanel()).val("0");
			}
			jsonData += ' "APPLY_NO": "' + obj.value + '" ,';
			jsonData += ' "PERSON_ID": "' + $("#EMPID_"+index,navTab.getCurrentPanel()).attr('sysPersonId') + '" ,';
			jsonData += ' "APPLY_OT_DATE":"'+ $("#APPLY_OT_DATE_"+index,navTab.getCurrentPanel()).html() +'" ,';
			jsonData += ' "OT_FROM_TIME":"'+ $("#APPLY_OT_DATE_"+index,navTab.getCurrentPanel()).html() +" "+ $("#OT_FROM_TIME_"+index,navTab.getCurrentPanel()).html() +'" ,';
			jsonData += ' "OT_TO_TIME":"'+ $("#APPLY_OT_DATE_"+index,navTab.getCurrentPanel()).html() +" "+ $("#OT_TO_TIME_"+index,navTab.getCurrentPanel()).html() +'" ,';
			jsonData += ' "OT_FROM_TIME_CONVERT":"'+ apply_ot_date +" "+ $("#OT_FROM_TIME_"+index,navTab.getCurrentPanel()).html() +'" ,';
			jsonData += ' "OT_TO_TIME_CONVERT":"'+ apply_ot_date +" "+ $("#OT_TO_TIME_"+index,navTab.getCurrentPanel()).html() +'" ,';
			jsonData += ' "OT_APPLY_HOUR":"'+ $("#OT_APPLY_HOUR_"+index,navTab.getCurrentPanel()).val() +'" ,';
			jsonData += ' "OFFSET_YN":"'+ $("#OFFSET_YN_"+index,navTab.getCurrentPanel()).val() +'" ,';
			jsonData += ' "DEDUCT_YN":"'+ $("#DEDUCT_YN_"+index,navTab.getCurrentPanel()).val() +'" ,';
			jsonData += ' "OT_TYPE_CODE":"'+ $("#OT_TYPE_CODE_NO_"+index,navTab.getCurrentPanel()).val() +'" ,';
			jsonData += ' "APPLY_OT_REMARK":"'+ $("#APPLY_OT_REMARK_"+index,navTab.getCurrentPanel()).html() +'"';
			jsonData += '}';
		}

	});
	jsonData += ']';
	var ids= document.getElementsByName("c1");
	for(var i=0;i<ids.length;i++){
		if(ids[i].checked){
			checked=true;
			var index = ids[i].id.substring(3);
			var sysPersonId = $("#EMPID_"+index,navTab.getCurrentPanel()).attr('sysPersonId');
			if(sysPersonId == null||sysPersonId==''){
				alertMsg.error("<spring:message code='ar.viewarcardrecord.title.empidnotnull' />");//社号不能为空
				return false;
			}
			var apply_ot_date = $("#APPLY_OT_DATE_"+index,navTab.getCurrentPanel()).html();
			apply_ot_date = apply_ot_date.substring(6,10)+"-"+apply_ot_date.substring(3,5)+"-"+apply_ot_date.substring(0,2);
			var otFromDate = apply_ot_date +" "+ $("#OT_FROM_TIME_"+index,navTab.getCurrentPanel()).html();
			var otToDate = apply_ot_date +" "+ $("#OT_TO_TIME_"+index,navTab.getCurrentPanel()).html();
			for(var j= i + 1;j<ids.length;j++){
				var indexJ = ids[j].id.substring(3);
				var sysPersonId_J = $("#EMPID_"+indexJ,navTab.getCurrentPanel()).attr('sysPersonId');
				var apply_ot_date_J = $("#APPLY_OT_DATE_"+indexJ,navTab.getCurrentPanel()).html();
				apply_ot_date_J = apply_ot_date_J.substring(6,10)+"-"+apply_ot_date_J.substring(3,5)+"-"+apply_ot_date_J.substring(0,2);
				var otFromDate_J = apply_ot_date_J +" "+ $("#OT_FROM_TIME_"+indexJ,navTab.getCurrentPanel()).html();
				if (sysPersonId == sysPersonId_J && otFromDate == otFromDate_J) {
					alertMsg.error("<spring:message code='alert.message.add_fail_repart' />");//社号不能为空
					return false;
				}
			}
			var OT_TYPE_CODE = $("#OT_TYPE_CODE_NO_"+index,navTab.getCurrentPanel()).val();
			var otDate = new Date(apply_ot_date);
			var date = new Date();
			var dateLengh = (otDate.getTime() - date.getTime())/(1000*60*60*24)
			/* if (dateLengh > 0 && OT_TYPE_CODE == '32' || dateLengh > 2) {
				alertMsg.error("<spring:message code='ar.viewArOvertimeManagentFast.OtDateCanNotFutureUnlessRestDay' />"); //加班日期不能用将来，除非次日是休息日期!
				return false;
			} */
			if(apply_ot_date.substring(0,1)=="<"||apply_ot_date==''){
				alertMsg.error("<spring:message code='ar.viewArOvertimeManagentFast.QINGXUANZEZHENGQUERIQI.b' />");//请选择正确日期
				return false;
			}
			var applyNo = $("#c1_"+index,navTab.getCurrentPanel()).val(); 
			var offset = $("#OFFSET_YN_"+index,navTab.getCurrentPanel()).attr("checked");
			if(offset=='checked'){
				offset = 1;
			}else{
				offset = 0;
			}
			 $.ajax({//人事确认过的会和AR_DETAIL表的考勤冲突
			 cache: false,
			 type: 'post',
			 url: '/hrm/recruitManage/doSql',
			 data:{sql:"select AR_GET_OT_CLASH('"+applyNo+"','"+sysPersonId+"','"+otFromDate+"','"+otToDate+"','"+offset+"') FLAG from dual"},
			 dataType:"json",
			 success: function(data) {
				var flag = data.result[0].FLAG;
				if(flag>0){
					alertMsg.error("<spring:message code='ar.viewArOvertimeManagentFast.JIABANSHIJIANCHONGTUQINGJIANCHA.b' />");//与已有加班冲突，请检查该时间内是否已经申请
					return false;
				}else if(flag == -1){
					alertMsg.error("<spring:message code='ar.viewApplyAttenanceManagentInfoList.BAOHANKAOQINGUANBIDESHIJIAN.b' />");//包含考勤关闭的时间
					return false;
				}else if(flag == -2){
					alertMsg.error("<spring:message code='ar.viewArOvertimeManaget_fast.Include_apply_closed.b' />");//包含考勤关闭的时间
					return false;
				}else if(flag == -3){
					alertMsg.error("<spring:message code='ar.viewAdjustLeaveTSTOBatchList.OvertimeLimit.b' />");//Bạn đã tăng ca quá thời gian quy định trong tháng (40 hoặc 300 tiếng), xin liên hệ phòng nhân sự!
					return false;
				} else if(flag == -4){
					alertMsg.error("<spring:message code='ar.viewAdjustLeaveTSTOBatchList.WomenOvertime.b' />");//Đang trong thời gian mang thai hoặc nuôi con nhỏ. Không thể tăng ca!
					return false;
				}
			 },
			 error:DWZ.ajaxError
			}); 

            var oTApplyHour = $("#OT_APPLY_HOUR_"+index,navTab.getCurrentPanel()).val();
            var otPostFamily = $("#OT_POST_FAMILY_"+index,navTab.getCurrentPanel()).val();
            var otTotail = $("#OT_TOTAIL_"+index,navTab.getCurrentPanel()).html();
            var otTotailLimit = $("#OT_TOTAIL_LIMIT_"+index,navTab.getCurrentPanel()).html();
            var otTotailMonth = $("#OT_TOTAIL_MONTH_"+index,navTab.getCurrentPanel()).html();
            var otTotailMonthLimit = $("#OT_TOTAIL_MONTH_LIMIT_"+index,navTab.getCurrentPanel()).html();
            var otTotalMonthWeekday = $("#WEEKDAY_OT_TOTAIL_"+index,navTab.getCurrentPanel()).html();
            var otLimit = $("#OT_LIMIT_"+index,navTab.getCurrentPanel()).val();
            var otFromTimeSub = $("#OT_FROM_TIME_"+index,navTab.getCurrentPanel()).html();
            var otApplyRemark = $("#APPLY_OT_REMARK_"+index,navTab.getCurrentPanel()).html();
            otFromTimeSub = otFromTimeSub.substring(0,2);
            var OT_FROM_MINUTE = $("#OT_FROM_TIME_"+index,navTab.getCurrentPanel()).html().substring(3,5);
				
            if (otApplyRemark == '' || otApplyRemark == ' ') {
            	alertMsg.info("<spring:message code='ess.infoApply.ApplyReasonNotull' />");
    			return false;
            }
			// if($("#AFFIRM_FLAG_"+index,navTab.getCurrentPanel()).val()!='14014308' && $("#AFFIRM_FLAG_"+index,navTab.getCurrentPanel()).val()!='14014309' && $("#AFFIRM_FLAG_"+index,navTab.getCurrentPanel()).val()!=''){
			// 	alertMsg.error("<spring:message code='ar.viewArOvertimeManagentFast.QINGXUANZEZHENGQUEXUANXIANG.b' />");//请选择正确选项
			// 	return false;
			// }
			if(oTApplyHour == '0' || oTApplyHour ==''){
				alertMsg.error("<spring:message code='ar.viewArOvertimeManagentFast.JIABANSHIJIANBUNENGLING.b' />");//加班时长不能等于0
				return false;
			}
			if(parseFloat(oTApplyHour) < 1 && otPostFamily == '14015813' && otFromTimeSub != 12 && otFromTimeSub != 07 && OT_FROM_MINUTE != 32){
				alertMsg.error("<spring:message code='alert.message.GUANLIZHI_MIX_ONE_OTHOUR.b' />");//管理职最少加班一小时
				return false;
			}
			/*if(parseFloat(oTApplyHour) > 4 && $("#OT_TYPE_CODE_NO_"+index,navTab.getCurrentPanel()).val()=='32'){
				alertMsg.error("<spring:message code='ess.viewSSTOtApplyInfo.PINGRIJIABANSHANGXIANSIXIAOSHI.b' />");//平日加班最多申请4小时,请重新选择!
				return false;
			}
			if(parseFloat(otTotail) + parseFloat(oTApplyHour) > parseFloat(otTotailLimit)){
				alertMsg.error("<spring:message code='ess.viewSSTOtApplyInfo.JIABANCHAOGUONIANSHANGXIAN.b' />");//加班时长超过年加班上限,无法申请!
				return false;
			}*/
			if(parseFloat(otTotailMonth) + parseFloat(oTApplyHour) > parseFloat(otTotailMonthLimit) && otLimit == 1){
				alertMsg.error("<spring:message code='ess.viewSSTOtApplyInfo.JIABANCHAOGUOYUESHANGXIAN.b' />");//加班时长超过月加班上限,无法申请!
				return false;
			}
		}
	}
	if (jsonData.length == 2) {
		//请选择要添加的数据
		alertMsg.error("<spring:message code='ar.alert.message.viewardetail.choosetoadd'/>");
		return false;
	}
	alertMsg.confirm ("<spring:message code='ar.alert.message.viewArAnnualStandard.consubmit'/>",{
		okCall:function(){
			$.ajax({
	 			type:'post',
	 			url:'/ess/infoApply/saveOtApplyAffirmForBatch' ,
	 			data:[{ name: 'jsonData', value: jsonData }],
	 			dataType:"json",
				cache: false,
				success:function(data){ //请求成功后处理函数。
					if(data.statusCode=="200"){
						navTabSearch($("#viewPiciOtAffirmLBatchList"));
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
 	}})
 	return false;
}
function delOtApplyCallback(OP_FLAG,form,callback){
	var $form=null;
	if($('#'+form).length>0){
		$form=$('#'+form);}
	else{
 		$form = $(form);}
	
	if (!$form.valid()) {
		return false;
	}
    var checked=false;
	var ids= document.getElementsByName("c1");
	for(var i=0;i<ids.length;i++){
		if(ids[i].checked){
			checked=true;
			
			var index = ids[i].id.substring(3);
			var sysPersonId = $("#EMPID_"+index,navTab.getCurrentPanel()).attr('sysPersonId');
			var apply_ot_date = $("#APPLY_OT_DATE_"+index,navTab.getCurrentPanel()).html();
			apply_ot_date = apply_ot_date.substring(6,10)+"-"+apply_ot_date.substring(3,5)+"-"+apply_ot_date.substring(0,2);
			var OT_TYPE_CODE = $("#OT_TYPE_CODE_NO_"+index,navTab.getCurrentPanel()).val();
			var otDate = new Date(apply_ot_date);
			var date = new Date();
			var dateLengh = (otDate.getTime() - date.getTime())/(1000*60*60*24)
			var applyNo = $("#c1_"+index,navTab.getCurrentPanel()).val(); 
			var offset = $("#OFFSET_YN_"+index,navTab.getCurrentPanel()).attr("checked");
			if(offset=='checked'){
				offset = 1;
			}else{
				offset = 0;
			}
			var otFromDate = apply_ot_date +" "+ $("#OT_FROM_TIME_"+index,navTab.getCurrentPanel()).html();
			var otToDate = apply_ot_date +" "+ $("#OT_TO_TIME_"+index,navTab.getCurrentPanel()).html();
			$.ajax({//人事确认过的会和AR_DETAIL表的考勤冲突
			 cache: false,
			 type: 'post',
			 url: '/hrm/recruitManage/doSql',
			 data:{sql:"select AR_GET_OT_CLASH('"+applyNo+"','"+sysPersonId+"','"+otFromDate+"','"+otToDate+"','"+offset+"') FLAG from dual"},
			 dataType:"json",
			 success: function(data) {
				var flag = data.result[0].FLAG;
				if(flag == -1){
					alertMsg.error("<spring:message code='ar.viewApplyAttenanceManagentInfoList.BAOHANKAOQINGUANBIDESHIJIAN.b' />");//包含考勤关闭的时间
					return false;
				}else if(flag == -2){
					alertMsg.error("<spring:message code='ar.viewArOvertimeManaget_fast.Include_apply_closed.b' />");//包含考勤关闭的时间
					return false;
				}
			 },
			 error:DWZ.ajaxError
			}); 
		}
	}
	if(!checked){
		alertMsg.error('<spring:message code="alert.message.ess.affirmApply.chooseApplyRecordFirstForBatch"/>'); 
		return false;
	}
    $form.attr("action","/ess/infoApply/delOtApplyAffirmForBatch");
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
						navTabSearch($("#viewPiciOtAffirmLBatchList"));
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
function addOtApplyAffirm(){
	 $.ajax({
			type:'post',
			url:'/ess/infoApply/addOtApplyAffirm',
			dataType:null,
			success: function(data){ //请求成功后处理函数。
				navTab.reload('/ess/infoApply/viewPiciOtAffirmLBatchList?type=add&firstFlag=N&seach_START_DATE=${START_DATE}&seach_END_DATE=${END_DATE}');
	   	 	}  ,
			error: DWZ.ajaxError});
}

function addOtApplyAffirmNoRefresh(){
	 $.ajax({
			type:'post',
			url:'/ess/infoApply/addOtApplyAffirm',
			dataType:null,
			success: function(data){ //请求成功后处理函数。
					 $.ajax({
							type:'POST',
							url:'/ar/attendanceMintenance/getAddOtApplyInfo',
							dataType:"json",
							//cache: false,
							success: function(data){ //请求成功后处理函数。
								if(data.statusCode=="200"){
									for(var i=0;i<data.nullOTTSTOAffirmList.length;i++){
										
										var tb2 = document.getElementById("otAffirmLBatchTable");
										var newFlag = 0;
										var htm = "";
                                      var ids = document.getElementsByName("c1");

										for (var j=0;j<ids.length;j++) {
											if(ids[j].value == data.nullOTTSTOAffirmList[i].APPLY_NO){
                                          	++newFlag;
                                          }
									    }

										if(newFlag == 0){
									   	   	
									   	   htm += '<tr><td style="text-align: center">' + (tb2.rows.length) + '</td>';
									   	   htm += '<td><input type="checkbox" id="c1_'+(tb2.rows.length)+'" name="c1" value="'+data.nullOTTSTOAffirmList[i].APPLY_NO+'" /></td>';
                                          htm += '<td style="text-align: center" id="EMPID_'+(tb2.rows.length)+'" sysLog="lookUp" sysIndex="'+(tb2.rows.length)+'" sysPersonId="" ></td>';
                                          htm += '<td style="text-align: center; color: blue; cursor: pointer;" id="LOCAL_NAME_'+(tb2.rows.length)+'" sysIndex="'+(tb2.rows.length)+'"></td>';
                                          htm += '<td style="text-align: center" id ="DEPT_NAME_'+(tb2.rows.length)+'" sysIndex="'+(tb2.rows.length)+'"></td>';
                                          htm += '<td style="text-align: center" id="SHIFT_NAME_'+(tb2.rows.length)+'"></td>';
                                          htm += '<input type="hidden" id="OT_POST_FAMILY_'+(tb2.rows.length)+'" value="'+data.nullOTTSTOAffirmList[i].POST_FAMILY+'">';
                                          htm += '<td style="text-align: center;" sysLog="date" sysIndex="'+(tb2.rows.length)+'" format="dd-MM-yyyy" id="APPLY_OT_DATE_'+(tb2.rows.length)+'" sysFlag="2">'+data.nullOTTSTOAffirmList[i].APPLY_OT_DATE+'</td>';
                                          htm += '<td style="text-align: center"><span id="SHIFT_START_TIME_'+(tb2.rows.length)+'">'+data.nullOTTSTOAffirmList[i].SHIFT_START_TIME+'</span></td>';
                                          htm += '<td style="text-align: center"><span id="SHIFT_END_TIME_'+(tb2.rows.length)+'">'+data.nullOTTSTOAffirmList[i].SHIFT_END_TIME+'</span></td>';
                                          htm += '<td style="text-align: center"><span id="INDOOR_TIME_'+(tb2.rows.length)+'">'+data.nullOTTSTOAffirmList[i].INDOOR_TIME+'</span></td>';
                                          htm += '<td style="text-align: center"><span id="OUTDOOR_TIME_'+(tb2.rows.length)+'">'+data.nullOTTSTOAffirmList[i].OUTDOOR_TIME+'</span></td>';
                                          htm += '<td style="text-align: center;" sysLog="select" sysIndex="'+(tb2.rows.length)+'" id="OT_FROM_TIME_'+(tb2.rows.length)+'" sysValue="'+"${TIME_STR}"+'" sysFlag="OT_TIME">'+data.nullOTTSTOAffirmList[i].OT_FROM_TIME+'</td>';
                                          htm += '<td style="text-align: center;" sysLog="select" sysIndex="'+(tb2.rows.length)+'" id="OT_TO_TIME_'+(tb2.rows.length)+'" sysValue="'+"${TIME_STR}"+'" sysFlag="OT_TIME">'+data.nullOTTSTOAffirmList[i].OT_TO_TIME+'</td>';
                                          htm += '<td style="text-align: center" id="OT_APPLY_HOUR_TEXT_'+(tb2.rows.length)+'">'+data.nullOTTSTOAffirmList[i].OT_APPLY_HOUR+'<spring:message code="ar.viewsummaryparameteritem.title.hour"/></td>';
                                          htm += '<input type="hidden" id="OT_APPLY_HOUR_'+(tb2.rows.length)+'" value="'+data.nullOTTSTOAffirmList[i].OT_APPLY_HOUR+'">';
                                          htm += '<td style="text-align: center"><input type="checkbox" id="DEDUCT_YN_'+(tb2.rows.length)+'" onclick="updateChecked('+(tb2.rows.length)+');otAffirm_callength('+(tb2.rows.length)+',2);" value="'+data.nullOTTSTOAffirmList[i].DEDUCT_YN+'">';
                                          htm += '<td style="text-align: center"><input type="checkbox" id="OFFSET_YN_'+(tb2.rows.length)+'" onclick="updateChecked('+(tb2.rows.length)+')" value="'+data.nullOTTSTOAffirmList[i].OFFSET_YN+'">';
                                          htm += '<td style="text-align: center;" id="OT_TYPE_CODE_'+(tb2.rows.length)+'">'+data.nullOTTSTOAffirmList[i].OT_TYPE_CODE_NAME+'</td>';
                                          htm += '<input type="hidden" id="OT_TYPE_CODE_NO_'+(tb2.rows.length)+'" value="'+data.nullOTTSTOAffirmList[i].OT_TYPE_CODE+'"/>';
                                          htm += '<td sysLog="text" sysIndex="'+(tb2.rows.length)+'" id="APPLY_OT_REMARK_'+(tb2.rows.length)+'"></td>';
                                          htm += '<td style="text-align: center" id="OT_TOTAIL_'+(tb2.rows.length)+'">'+data.nullOTTSTOAffirmList[i].OT_TOTAIL+'</td>';
                                          htm += '<td style="text-align: center" id="OT_TOTAIL_MONTH_'+(tb2.rows.length)+'">'+data.nullOTTSTOAffirmList[i].OT_TOTAIL_MONTH+'</td>';
                                          htm += '<td style="text-align: center" id="WEEKDAY_OT_TOTAIL_'+(tb2.rows.length)+'">'+data.nullOTTSTOAffirmList[i].WEEKDAY_OT_TOTAIL+'</td>';
                                          htm += '<td style="text-align: center" id="WEEKEND_OT_TOTAIL_'+(tb2.rows.length)+'">'+data.nullOTTSTOAffirmList[i].WEEKEND_OT_TOTAIL+'</td>';
                                          htm += '<td style="text-align: center" id="HOILDAY_OT_TOTAIL_'+(tb2.rows.length)+'">'+data.nullOTTSTOAffirmList[i].HOILDAY_OT_TOTAIL+'</td>';
                                          htm += '<td style="text-align: center" id="OT_TOTAIL_LIMIT_'+(tb2.rows.length)+'">300</td>';
                                          htm += '<td style="text-align: center" id="OT_TOTAIL_MONTH_LIMIT_'+(tb2.rows.length)+'">40</td>';
                                          htm += '<input type="hidden" id="OT_LIMIT_'+(tb2.rows.length)+'" value="'+data.nullOTTSTOAffirmList[i].OT_LIMIT+'"/>';
                                          htm += '<td style="text-align: center" id="OT_AFFIRMOR_'+(tb2.rows.length)+'"></td>';
                                          htm += '<td style="text-align: center"></td>';
                                          htm += '<td style="text-align: center">'+data.nullOTTSTOAffirmList[i].CREATED_BY+'</td>';
                                          //htm += '<td style="text-align: center">'+data.nullOTTSTOAffirmList[i].CREATE_DATE+'</td>';
                                          //htm += '<td style="text-align: center"></td>';
                                          //htm += '<td  style="text-align: center"></td>';
                                          htm += '<input type="hidden" id="AFFIRM_FLAG_'+(tb2.rows.length)+'" value="">';
                                          htm += '<div id="modifyFlag_'+(tb2.rows.length)+'" sysLog="modifyFlag" sysIndex="'+(tb2.rows.length)+'" style="display:none;"></div>';
                                          htm += '</tr>';

										   	   //当前行之后插入一行
									   	   	var row = tb2.rows[tb2.rows.length - 1];
									   	   	$(row).after(htm);
									   	   	
									   	 	initEditFun_ess3403();
											init_ess3403();
									   	}
									}
								}
					   	 	},
							error: DWZ.ajaxError
					});
	   	 	},
			error: DWZ.ajaxError});

}

function submitKeyClick_OtAffirm(obj,index){
	var empid=obj.replace(/[ ]/g," ");
	var personIdStr="applyOt";
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
					$("#OT_DUTY_NO_" + index,navTab.getCurrentPanel()).val(jsonObject.dutyNo);
					$("#OT_POST_FAMILY_" + index,navTab.getCurrentPanel()).val(jsonObject.POST_FAMILY);
					$("#SHIFT_NAME_" + index,navTab.getCurrentPanel()).html(jsonObject.shiftName);
					getAffirmor_ess3403(index);
				} ;
				getOt_Totail(index);
				otAffirm_callength(index,1);
			},
			error: DWZ.ajaxError
		});
	}else{
		$("#EMPID_" + index,navTab.getCurrentPanel()).html("");
		$("#DEPT_NAME_" +  index,navTab.getCurrentPanel()).html("");
		$("#LOCAL_NAME_" + index,navTab.getCurrentPanel()).html("");
	}
}
function otAffirm_fillItem(){
	$.ajaxSettings.global = false;
	var applyOtRemark = $("#APPLY_OT_REMARK",navTab.getCurrentPanel()).val();
	var fromDate = $("#OT_FROM_DATE",navTab.getCurrentPanel()).val();
	var fromTime = $("#OT_FROM_TIME",navTab.getCurrentPanel()).val();
	var toTime = $("#OT_TO_TIME",navTab.getCurrentPanel()).val();
	var otTypeCode = $("#OT_TYPE_CODE",navTab.getCurrentPanel()).val();
	var ids= document.getElementsByName("c1");
	var checked=false;
	var length = null;
	var lengthText = " ";
	var otTypeCodeName = " ";
	var otTypeCode = " ";
	if(fromDate.substring(0,1)=="<"||fromDate==''){
		alertMsg.error("<spring:message code='ar.viewArOvertimeManagentFast.QINGXUANZEZHENGQUERIQI.b' />");//请选择正确日期
		return false;
	}

	var fromDateformat = fromDate.substring(6,10)+"-"+fromDate.substring(3,5)+"-"+fromDate.substring(0,2);
	
	for(var i=0;i<ids.length;i++){
		if(ids[i].checked){
			checked=true;
			var index = ids[i].id.substring(3);
			var adjust_yn = 0;
			var sprcial_yn = 0;
			var apply_person_id = $("#EMPID_"+index,navTab.getCurrentPanel()).attr('sysPersonId');
			/*if($("#ADJUST_YN_"+index,navTab.getCurrentPanel()).attr("checked")=='checked'){
				adjust_yn=1;
			}
			if($("#SPECIAL_YN_"+index,navTab.getCurrentPanel()).attr("checked")=='checked'){
				sprcial_yn=1;
			}*/
			$.ajax({
				type: 'POST',
				url: '/ess/infoApply/getValidateInfo',
				async:false,
				data:{"CNPY_ID":'${LoginUser.cpnyId}',"PERSON_ID":apply_person_id,"apply_ot_date":fromDateformat,"otFromTime":fromTime,"otToTime":toTime,"apply_person_id":apply_person_id,"DEDUCT_YN":"0"},
				dataType:"json",
				success: function(data){
					length = data.result[0].OT_LENGTH;
					lengthText = data.result[0].OT_LENGTH + "<spring:message code='ar.viewitemparameter.title.xiaoshi' />";//小时
					otTypeCode = data.result[0].OT_TYPE_CODE;
					otTypeCodeName = data.result[0].OT_TYPE_CODE_NAME;
					SHIFT_START_TIME = data.result[0].SHIFT_START_TIME;
					SHIFT_END_TIME = data.result[0].SHIFT_END_TIME;
				},
				error: DWZ.ajaxError
			}); 
			
			$("#APPLY_OT_DATE_"+index,navTab.getCurrentPanel()).html(fromDate);
			$("#OT_FROM_TIME_"+index,navTab.getCurrentPanel()).html(fromTime);
			$("#OT_TO_TIME_"+index,navTab.getCurrentPanel()).html(toTime);
			if(applyOtRemark != ""){
				$("#APPLY_OT_REMARK_"+index,navTab.getCurrentPanel()).html(applyOtRemark);
			}
			$("#OT_APPLY_HOUR_TEXT_"+index,navTab.getCurrentPanel()).html(lengthText);
			$("#OT_APPLY_HOUR_"+index,navTab.getCurrentPanel()).val(length);
			$("#OT_TYPE_CODE_"+index,navTab.getCurrentPanel()).html(otTypeCodeName);
			$("#OT_TYPE_CODE_NO_"+index,navTab.getCurrentPanel()).val(otTypeCode);
			$("#SHIFT_START_TIME_"+index,navTab.getCurrentPanel()).html(SHIFT_START_TIME);
			$("#SHIFT_END_TIME_"+index,navTab.getCurrentPanel()).html(SHIFT_END_TIME);
			
			$("#DEDUCT_YN_"+index,navTab.getCurrentPanel()).attr("checked",false);
			if(fromTime>toTime){
				$("#OFFSET_YN_"+index,navTab.getCurrentPanel()).attr("checked",true);
			}else{
				$("#OFFSET_YN_"+index,navTab.getCurrentPanel()).attr("checked",false);
			}
		}
	}
	if(!checked){
		alertMsg.error('<spring:message code="ar.viewApplyAttenanceManagentInfoList.QINGXUANZEXIUGAINEIRONG.b" />'); //请选择要修改的内容
		return false;
	}
	$.ajaxSettings.global = true;
}
function otAffirm_callength(index,FLAG){
	var adjust_yn = 0;
	var sprcial_yn = 0;
	var deduct_yn = 0;
	var apply_ot_date = $("#APPLY_OT_DATE_"+index,navTab.getCurrentPanel()).html();
	apply_ot_date = apply_ot_date.substring(6,10)+"."+apply_ot_date.substring(3,5)+"."+apply_ot_date.substring(0,2);
	var otFromTime = $("#OT_FROM_TIME_"+index,navTab.getCurrentPanel()).html();
	var otToTime = $("#OT_TO_TIME_"+index,navTab.getCurrentPanel()).html();
	var apply_person_id = $("#EMPID_"+index,navTab.getCurrentPanel()).attr('sysPersonId');
	var length = " ";
	if($("#DEDUCT_YN_"+index,navTab.getCurrentPanel()).attr("checked")=='checked'){
		deduct_yn = 1;
	}
	if(new Date(apply_ot_date+" "+otToTime) - new Date(apply_ot_date+" "+otFromTime)<0){
		$("#OFFSET_YN_"+index).attr("checked",true);
	}
	if(new Date(apply_ot_date+" "+otToTime) - new Date(apply_ot_date+" "+otFromTime)>0){
		$("#OFFSET_YN_"+index).attr("checked",false);
	}
	if(apply_ot_date.substring(0,1)=="<"||apply_ot_date==''){
		alertMsg.error("<spring:message code='ar.viewArOvertimeManagentFast.QINGXUANZEZHENGQUERIQI.b' />");//请选择正确日期
		return false;
	}
	if(!$("#c1_"+index,navTab.getCurrentPanel()).prop("disabled")){
		$("#c1_"+index,navTab.getCurrentPanel()).attr("checked",true); 
	}
	$.ajax({
		 cache: false,
		 type: 'post',
		 async:false,
		 url: '/ess/infoApply/getValidateInfo',
		 data:{"CNPY_ID":'${LoginUser.cpnyId}',"PERSON_ID":apply_person_id,"apply_ot_date":apply_ot_date,"otFromTime":otFromTime,"otToTime":otToTime,"apply_person_id":apply_person_id,"DEDUCT_YN":deduct_yn},
		 dataType:"json",
		 success: function(data) {
			 length = data.result[0].OT_LENGTH;
			 var shiftStartTime = data.result[0].SHIFT_START_TIME;
			 var shiftEndTime = data.result[0].SHIFT_END_TIME;
			 var shiftEndTime_2 = data.result[0].SHIFT_END_TIME_2;
			 var otShiftLength = data.result[0].OT_SHIFT_LENGTH;
			 $("#OT_TYPE_CODE_"+index,navTab.getCurrentPanel()).html(data.result[0].OT_TYPE_CODE_NAME);
			 $("#OT_TYPE_CODE_NO_"+index,navTab.getCurrentPanel()).val(data.result[0].OT_TYPE_CODE);
			 if(data.result[0].DATETYPE == '1440'){
				 //$("#DEDUCT_YN_"+index,navTab.getCurrentPanel()).removeAttr("disabled");
				if(FLAG==1){
					$("#OT_FROM_TIME_"+index,navTab.getCurrentPanel()).html(shiftEndTime);
					$("#OT_TO_TIME_"+index,navTab.getCurrentPanel()).html(shiftEndTime_2);
					$("#OT_APPLY_HOUR_TEXT_"+index,navTab.getCurrentPanel()).html(2+"<spring:message code='ar.viewitemparameter.title.xiaoshi' />");//小时
					$("#OT_APPLY_HOUR_"+index,navTab.getCurrentPanel()).val(2);
					$("#SHIFT_START_TIME_"+index,navTab.getCurrentPanel()).html(shiftStartTime);
					$("#SHIFT_END_TIME_"+index,navTab.getCurrentPanel()).html(shiftEndTime);
					$("#INDOOR_TIME_"+index,navTab.getCurrentPanel()).html(data.result[0].INDOOR_TIME);
					$("#OUTDOOR_TIME_"+index,navTab.getCurrentPanel()).html(data.result[0].OUTDOOR_TIME);
				 }else{
					 $("#OT_APPLY_HOUR_TEXT_"+index,navTab.getCurrentPanel()).html(length+"<spring:message code='ar.viewitemparameter.title.xiaoshi' />");//小时
					 $("#OT_APPLY_HOUR_"+index,navTab.getCurrentPanel()).val(length);
				 }
			}else if(data.result[0].DATETYPE == '1441'){
				//$("#DEDUCT_YN_"+index,navTab.getCurrentPanel()).attr("checked",false);
				//$("#DEDUCT_YN_"+index,navTab.getCurrentPanel()).attr("disabled","disabled");
				if(FLAG==1){
					$("#OT_FROM_TIME_"+index,navTab.getCurrentPanel()).html(shiftStartTime);
					$("#OT_TO_TIME_"+index,navTab.getCurrentPanel()).html(shiftEndTime);
					$("#OT_APPLY_HOUR_TEXT_"+index,navTab.getCurrentPanel()).html(otShiftLength+"<spring:message code='ar.viewitemparameter.title.xiaoshi' />");//小时
					$("#OT_APPLY_HOUR_"+index,navTab.getCurrentPanel()).val(otShiftLength);
					$("#SHIFT_START_TIME_"+index,navTab.getCurrentPanel()).html(shiftStartTime);
					$("#SHIFT_END_TIME_"+index,navTab.getCurrentPanel()).html(shiftEndTime);
					$("#INDOOR_TIME_"+index,navTab.getCurrentPanel()).html(data.result[0].INDOOR_TIME);
					$("#OUTDOOR_TIME_"+index,navTab.getCurrentPanel()).html(data.result[0].OUTDOOR_TIME);
				}else{
					 $("#OT_APPLY_HOUR_TEXT_"+index,navTab.getCurrentPanel()).html(length+"<spring:message code='ar.viewitemparameter.title.xiaoshi' />");//小时
					 $("#OT_APPLY_HOUR_"+index,navTab.getCurrentPanel()).val(length);
				 }
			}else{
				//$("#DEDUCT_YN_"+index,navTab.getCurrentPanel()).attr("checked",false);
				//$("#DEDUCT_YN_"+index,navTab.getCurrentPanel()).attr("disabled","disabled");
				if(FLAG==1){
					$("#OT_FROM_TIME_"+index,navTab.getCurrentPanel()).html(shiftStartTime);
					$("#OT_TO_TIME_"+index,navTab.getCurrentPanel()).html(shiftEndTime);
					$("#OT_APPLY_HOUR_TEXT_"+index,navTab.getCurrentPanel()).html(otShiftLength+"<spring:message code='ar.viewitemparameter.title.xiaoshi' />");//小时
					$("#OT_APPLY_HOUR_"+index,navTab.getCurrentPanel()).val(otShiftLength);
					$("#SHIFT_START_TIME_"+index,navTab.getCurrentPanel()).html(shiftStartTime);
					$("#SHIFT_END_TIME_"+index,navTab.getCurrentPanel()).html(shiftEndTime);
					$("#INDOOR_TIME_"+index,navTab.getCurrentPanel()).html(data.result[0].INDOOR_TIME);
					$("#OUTDOOR_TIME_"+index,navTab.getCurrentPanel()).html(data.result[0].OUTDOOR_TIME);
				}else{
					 $("#OT_APPLY_HOUR_TEXT_"+index,navTab.getCurrentPanel()).html(length+"<spring:message code='ar.viewitemparameter.title.xiaoshi' />");//小时
					 $("#OT_APPLY_HOUR_"+index,navTab.getCurrentPanel()).val(length);
				}
			}
		 },
		 error:DWZ.ajaxError
	});
	getAffirmor_ess3403(index);
}
function getDefaultOtTimeSST(){
	$.ajaxSettings.global = false;
	var OT_FROM_DATE = $("#OT_FROM_DATE",navTab.getCurrentPanel()).val();
	if(OT_FROM_DATE.substring(0,1)=="<"||OT_FROM_DATE==''){
		alertMsg.error("<spring:message code='ar.viewArOvertimeManagentFast.QINGXUANZEZHENGQUERIQI.b' />");//请选择正确日期
		return false;
	}
	OT_FROM_DATE = OT_FROM_DATE.substring(6,10)+"-"+OT_FROM_DATE.substring(3,5)+"-"+OT_FROM_DATE.substring(0,2);

	$.ajax({
		type: 'POST',
		url: '/hrm/recruitManage/doSql',
		data:{sql:"select GET_AR_DATETYPE('${LoginUser.adminID}','" + OT_FROM_DATE + "','${LoginUser.cpnyId}') DATETYPE," +
			"TO_CHAR(TO_DATE(GET_AR_SHIFT_END_TIME('${LoginUser.adminID}','"+OT_FROM_DATE+"','${LoginUser.cpnyId}'),'YYYY.MM.DD HH24:MI'),'HH24:MI') SHIFT_END_TIME,"+
			"TO_CHAR(TO_DATE(GET_AR_SHIFT_END_TIME('${LoginUser.adminID}','"+OT_FROM_DATE+"','${LoginUser.cpnyId}'),'YYYY.MM.DD HH24:MI')+2/24,'HH24:MI') SHIFT_END_TIME_2," +
			"TO_CHAR(TO_DATE(GET_AR_SHIFT_START_TIME('${LoginUser.adminID}','"+OT_FROM_DATE+"','${LoginUser.cpnyId}'),'YYYY.MM.DD HH24:MI'),'HH24:MI') SHIFT_START_TIME from dual"},
		dataType:"json",
		cache: false,
		success: function(data){
			var shiftStartTime = data.result[0].SHIFT_START_TIME;
			var shiftEndTime = data.result[0].SHIFT_END_TIME;
			var shiftEndTime_2 = data.result[0].SHIFT_END_TIME_2;
			if(data.result[0].DATETYPE == '1440'){
				$("#OT_TYPE_CODE",navTab.getCurrentPanel()).val(32);
				$("#OT_FROM_TIME",navTab.getCurrentPanel()).val(shiftEndTime);
				$("#OT_TO_TIME",navTab.getCurrentPanel()).val(shiftEndTime_2);
			}else if(data.result[0].DATETYPE == '1441'){
				$("#OT_TYPE_CODE",navTab.getCurrentPanel()).val(33);
				$("#OT_FROM_TIME",navTab.getCurrentPanel()).val(shiftStartTime);
				$("#OT_TO_TIME",navTab.getCurrentPanel()).val(shiftEndTime);
			}else{
				$("#OT_TYPE_CODE",navTab.getCurrentPanel()).val(34);
				$("#OT_FROM_TIME",navTab.getCurrentPanel()).val(shiftStartTime);
				$("#OT_TO_TIME",navTab.getCurrentPanel()).val(shiftEndTime);
			}
		},
		error: DWZ.ajaxError
	});
	$.ajaxSettings.global = true;
}
/**
 * 禁用textArea以及Input框的enter键的自动提交
 */
/**document.onkeydown = function(event) {  
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
	        	   $("#seach_KEY",navTab.getCurrentPanel())[0].focus();
		           return false; 
		       }else {
			        return true;
			   }   
	      }  
	 }  
};*/
function updateChecked(index){
	if(!$("#c1_"+index,navTab.getCurrentPanel()).prop("disabled")){
		$("#c1_"+index,navTab.getCurrentPanel()).attr("checked",true); 
	}
}

function getAgoAffirmor_ess3403(index){
	var htm = '';
	var applyNo = $("#c1_"+index,navTab.getCurrentPanel()).val();
	$.ajax({
		type: 'POST',
		url: '/ess/infoApplyAttendance/getAffirmorByApplyNoList',
		data:[{ name: 'APPLY_NO', value: applyNo }],
		dataType:"json",
		cache: false,
		async:false,
		success: function(data){
			if(data.affirmorList.length>0){
				for(var i=0;i<data.affirmorList.length;i++){
					if(i%2==0){
						htm += (i+1)+' ['+data.affirmorList[i].EMPID+']'+data.affirmorList[i].LOCAL_NAME+'<br>'; 
					}else{
						htm += (i+1)+' ['+data.affirmorList[i].EMPID+']'+data.affirmorList[i].LOCAL_NAME+'<br>'; 
					}
				}
				$("#OT_AFFIRMOR_"+index,navTab.getCurrentPanel()).html(htm);
			}else{
				$("#OT_AFFIRMOR_"+index,navTab.getCurrentPanel()).html('');
			}
		},
		error: DWZ.ajaxError
	});
}

function getAffirmor_ess3403(index){
	var htm = '';
	var personId = $("#EMPID_"+index,navTab.getCurrentPanel()).attr('sysPersonId');
	var otTypeCode = $("#OT_TYPE_CODE_NO_"+index,navTab.getCurrentPanel()).val();
	var applyLength = $("#OT_APPLY_HOUR_"+index,navTab.getCurrentPanel()).val();
	var dutyNo = $("#OT_DUTY_NO_"+index,navTab.getCurrentPanel()).val();

	if($("#AFFIRM_FLAG_"+index,navTab.getCurrentPanel()).val()!='14014308' && $("#AFFIRM_FLAG_"+index,navTab.getCurrentPanel()).val()!=''){
		return false;
	}
	
	$.ajax({
		type: 'POST',
		url: '/ess/infoApplyAttendance/viewAffirmorByPersonIdListForOt',
		data:[{ name: 'applyTypeCode', value: otTypeCode },/* 审批线相同，无所谓 */
		      { name: 'applyLength', value: applyLength },
		      { name: 'applyTypeNo', value: '31' },
		      { name: 'personId', value: personId }],
		dataType:"json",
		cache: false,
		async:false,
		success: function(data){
			if(data.affirmorList.length>0){
				for(var i=0;i<data.affirmorList.length;i++){
					if(i%2==0){
						htm += (i+1)+' ['+data.affirmorList[i].EMPID+']'+data.affirmorList[i].LOCAL_NAME+'<br>'; 
					}else{
						htm += (i+1)+' ['+data.affirmorList[i].EMPID+']'+data.affirmorList[i].LOCAL_NAME+'<br>'; 
					}
				}
				$("#OT_AFFIRMOR_"+index,navTab.getCurrentPanel()).html(htm);
			}else{
				$("#OT_AFFIRMOR_"+index,navTab.getCurrentPanel()).html('');
			}
		},
		error: DWZ.ajaxError
	});
}

function getOt_Totail(index){
	$.ajaxSettings.global = false;
	var APPLY_OT_DATE = $("#APPLY_OT_DATE_"+index,navTab.getCurrentPanel()).html();
	var APPLY_OT_DATE_FORMAT = APPLY_OT_DATE.substring(6,10)+"-"+APPLY_OT_DATE.substring(3,5)+"-"+APPLY_OT_DATE.substring(0,2);
	var PERSON_ID = $("#EMPID_"+index,navTab.getCurrentPanel()).attr('sysPersonId');
	$.ajax({
		type: 'POST',
		url: '/hrm/recruitManage/doSql',
		data:{sql:"select GET_AR_OT_TOTAIL(replace('" + APPLY_OT_DATE_FORMAT + "','-','/'),'${LoginUser.cpnyId}','" + PERSON_ID + "','141444') WEEKDAY_OT_TOTAIL," +
			"GET_AR_OT_TOTAIL(replace('" + APPLY_OT_DATE_FORMAT + "','-','/'),'${LoginUser.cpnyId}','" + PERSON_ID + "','141445')  WEEKEND_OT_TOTAIL," +
			"GET_AR_OT_TOTAIL(replace('" + APPLY_OT_DATE_FORMAT + "','-','/'),'${LoginUser.cpnyId}','" + PERSON_ID + "','141446')  HOILDAY_OT_TOTAIL," +
			"GET_AR_OT_TOTAIL(replace('" + APPLY_OT_DATE_FORMAT + "','-','/'),'${LoginUser.cpnyId}','" + PERSON_ID + "','200')  OT_TOTAIL," +
			"GET_AR_OT_TOTAIL(replace('" + APPLY_OT_DATE_FORMAT + "','-','/'),'${LoginUser.cpnyId}','" + PERSON_ID + "','30')  OT_TOTAIL_MONTH," +
			"(SELECT OT_LIMIT FROM HR_EMPLOYEE WHERE PERSON_ID = '"+PERSON_ID+"') OT_LIMIT" +
			" FROM DUAL"},
		dataType:"json",
		cache: false,
		success: function(data){
			var weekday_ot_totail = data.result[0].WEEKDAY_OT_TOTAIL;
			var weekend_ot_totail = data.result[0].WEEKEND_OT_TOTAIL;
			var hoilday_ot_totail = data.result[0].HOILDAY_OT_TOTAIL;
			var ot_totail = data.result[0].OT_TOTAIL;
			var ot_totail_month = data.result[0].OT_TOTAIL_MONTH;
			var otLimit = data.result[0].OT_LIMIT;
			
			$("#WEEKDAY_OT_TOTAIL_"+index,navTab.getCurrentPanel()).html(weekday_ot_totail);
			$("#WEEKEND_OT_TOTAIL_"+index,navTab.getCurrentPanel()).html(weekend_ot_totail);
			$("#HOILDAY_OT_TOTAIL_"+index,navTab.getCurrentPanel()).html(hoilday_ot_totail);
			$("#OT_TOTAIL_"+index,navTab.getCurrentPanel()).html(ot_totail);
			$("#OT_TOTAIL_MONTH_"+index,navTab.getCurrentPanel()).html(ot_totail_month);
			$("#OT_LIMIT_"+index,navTab.getCurrentPanel()).val(otLimit);
			
		},
		error: DWZ.ajaxError
	});
	$.ajaxSettings.global = true;
}

function batchCheckedAndCallength(control){
	if(control.checked){
		$.each($("input[name='DEDUCT_YN']"),function(i, obj) {
			if (!obj.disabled) {
				obj.checked = true;
				var index = obj.id.substr(10);
				updateChecked(index);
		        otAffirm_callength(index,2);
			}
		});
    }else{
    	$.each($("input[name='DEDUCT_YN']"),function(i, obj) {
			if (!obj.disabled) {
				obj.checked = false;
				var index = obj.id.substr(10);
				updateChecked(index);
		        otAffirm_callength(index,2);
			}
		});
    }
}

function excelimport_ess3403(){
	$("#importExcelDialog_ess3403").attr('href','/pa/excelImport/importExcelData?importFunName=/importApplyOt');
	$("#importExcelDialog_ess3403").click();
}
function changeURL_ess3403(applyNo){
	var href = "/ess/infoApply/viewApprovaledOtInfo?seach_APPLY_NO=" + applyNo;
	$.pdialog.open(href,"ess3403", "<spring:message code='ar.viewApplyAttenanceManagentInfoList.MINGXICHAKAN.b' />", {width:1000,height:600,mask:true});//明细查看
}
</script>
<a id="importExcelDialog_ess3403"  href="#" target="dialog" mask="true"></a>
<a id="importExcel_ess3403" href="#" target="navTab" mask="true"><span style="display:none;"><!-- 导入结果 --><spring:message code="ess.title.DAORUJIEGUO" /></span></a>
<div   id="viewApplyOtBatch"  class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/ess/infoApply/viewPiciOtAffirmLBatchList?firstFlag=N" method="post"
		id="viewPiciOtAffirmLBatchList" name="viewPiciOtAffirmLBatchList">
		 
		<div class="searchBar">
				<table class="searchContent">
				<tr>
					<td><!-- 社号/姓名 --><spring:message code="hrm.empinfo.nameAndEmpid" /></td>
					<td>
						<div style="float:left"><input type="text" name="seach_KEY" id="seach_KEY" value="${KEY}"/></div>
					</td>
					<td><!-- 部门 --><spring:message code="hr.viewPersonalInfo.title.DEPTNAME" /></td>
					<td>
						<%-- <ait:deptList name="seach_DEPTNO" cpnyId="${defaultCpny}" limit="manager" id="viewPiciOtAffirmLBatchList_seachDept" selected="${DEPTNO}"/>
						<ait:deptTreeIcon name="seach_DEPTNO" cpnyId="${defaultCpny}" limit="manager" id="viewPiciOtAffirmLBatchList_seachDept" selected="${DEPTNO}"/> --%>
						<ait:deptTreeMulti name="seach_DEPTNAME" id="seach_DEPTNO_Multi" limit="manager" selectedNm="${DEPTNAME }" selected="${DEPTNO_Multi }"></ait:deptTreeMulti>
					</td>
					<td><!--日期--><spring:message code="org.title.DATE" /> </td>
					<td>
						<input type="text" id="seach_START_DATE" name="seach_START_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})" value="${START_DATE}"/>~
						<input type="text" id="seach_END_DATE" name="seach_END_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})" value="${END_DATE}"/>
					</td>
				</tr>
				<tr>	
				    <td><!--班组类型--><spring:message code="ar.viewArBaseEmpInfoList.BANZULEIXING.b" /></td>
					<td>
						<%-- <ait:SelectSyCodeByCpnyID name="seach_SHIFT_NO" parentNo="400223" cnpyID="${LoginUser.cpnyId}" limit="all" selected="${SHIFT_NO }" /> --%>
						<select name="seach_SHIFT_NO" id="seach_SHIFT_NO">
						   <option value="" ><spring:message code="hr.viewCondSql.title.QINGXUANZE"/><!-- Select --></option>
						    <c:forEach items="${shiftList }" var="result">
						       <option value="${result.SHIFT_NAME }" name="${result.SHIFT_NAME }" <c:if test="${result.SHIFT_NAME eq SHIFT_NAME}">selected="selected"</c:if>> ${result.SHIFT_NAME}</option>
						    </c:forEach>
						</select>
					</td>				
					<td><!-- 加班类型 --><spring:message code="ess.infoApply.overtime_type" /></td>
					<td>
					<ait:SelectSyCodeByCpnyID name="seach_OT_TYPE_CODE" parentNo="31" selected="${OT_TYPE_CODE}" limit="ALL"/>
					</td>	
					<td><!--审批状态--><spring:message code="ess.infoApply.approval_status" /> </td>
					<td>
						<ait:SelectSyCodeByCpnyID name="seach_AFFIRM_FLAG" parentNo="14014304" selected="${AFFIRM_FLAG}" limit="ALL"/>
						<input type="checkbox" name="CANCEL_FLAG" value="1" <c:if test="${CANCEL_FLAG eq '1' }">checked="checked"</c:if>> <!-- 取消申请 --><spring:message code="ess.affirmApply.title.quxiaoshenqing"/>
					</td>					
				</tr>
				<!--<tr>
					<td> 人事确认状态 <spring:message code="ess.title.RENSHIQUERENZHUANGTAI" /></td>
					<td>
					<select name="seach_CONFIRM_FLAG">
						<option value=""<c:if test="${CONFIRM_FLAG == null}">selected</c:if>> 全部 <spring:message code="org.title.ALL" /></option>
						<option value="0"<c:if test="${CONFIRM_FLAG == '0'}">selected</c:if>> 未确认<spring:message code="ess.title.WEIQUEREN" /></option>
						<option value="1"<c:if test="${CONFIRM_FLAG == '1'}">selected</c:if>> 通过<spring:message code="ess.infoApply.adopt" /></option>
						<option value="2"<c:if test="${CONFIRM_FLAG == '2'}">selected</c:if>> 否决<spring:message code="ess.infoApply.veto" /></option> 
					</select>
					</td>
				</tr>-->
			</table>
		</div>
	</form>
</div>
<div id="viewApplyAttenBatch" class="pageHeader" >
    <div class="searchBar">
			<table class="searchContent">
			    <tr>
			       <td><!--加班状态--><spring:message code="ess.title.JIABANZHUANGTAI" /></td>
				   <td>
						<ait:SelectSyCodeByCpnyID name="OT_TYPE_CODE" parentNo="31" disabled="true"/>	
				   </td>
				   <td><!--加班日期--><spring:message code="ess.infoApply.title.overtimeTime" /> </td>
				   <td>
				       <input type="text" name="OT_FROM_DATE" id="OT_FROM_DATE"  class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en',onpicked:getDefaultOtTimeSST})" value=""/>
				   </td>
				   <td><!--开始时间--><spring:message code="ess.infoApply.title.startTime" /></td>
				   <td>
						<ait:time name="OT_FROM_TIME"  spacing="15" extra="12:10,20:05"   selected=""/>
					</td>
					<td><!--结束时间--><spring:message code="ess.infoApply.end_time" /></td>
					<td>
						<ait:time name="OT_TO_TIME"  spacing="15" extra="12:10,20:05"  selected=""/>
					</td>
					<td><!--原因--><spring:message code="hrm.empinfo.reason" /></td>
					<td>
						<input type="text" id="APPLY_OT_REMARK" name="APPLY_OT_REMARK"  value=""/>
					</td >
					
				</tr>
			</table>
			<div class="subBar">
				<ul class="toolBar">
	             <li>
	             <a class="buttonActive" onclick="otAffirm_fillItem();"><span><!--全部反应--><spring:message code="ess.message.all_reaction" /></span></a>
	             </li>
				</ul>
			</div>
	</div>
</div>
<div class="pageContent" >
<div class="formBar">
	<ul class="toolBar">
	    <li><a class="buttonActive" id="viewPiciOtAffirmLBatchList_Serch" href="#"><span><!--查询--><spring:message code="org.title.SELECT" /></span></a></li>
	    <c:if test="${type eq '' || type eq null}">
	    	<li><a class="buttonActive" onclick="addOtApplyAffirm()"><span><!--添加--><spring:message code="ess.empInfo.insert" /></span></a></li>
	    </c:if>
	    <c:if test="${type ne '' && type ne null}">
	    	<li><a class="buttonActive" onclick="addOtApplyAffirmNoRefresh()"><span><!-- 添加 --><spring:message code="ess.empInfo.insert"/></span></a></li>
	    </c:if>
		<li><a class="buttonActive" onclick="delOtApplyCallback(0,'delOtApplyAffirmForm',DWZ.ajaxDone)"><span><!--删除--><spring:message code="ess.empInfo.Delete" /></span></a></li>
		<li><a class="buttonActive" onclick="saveOtApplyAffirm()"><span><!--保存--><spring:message code="ar.viewempcalender.title.save" /></span></a></li>
		<li><a class="buttonActive" href="/pa/excelExport/downloadExcelOtApply?file=OtApply_add" ><span><!--模板下载--><spring:message code="ess.message.template_download" /></span></a></li>
		<li><a class="buttonActive" onclick="excelimport_ess3403()"><span><!--Excel导入--><spring:message code="ess.infoApply.EXCEL_IN" /></span></a></li>
		<li><a class="buttonActive" onclick="downloadExcel('viewPiciOtAffirmLBatchList','/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=128','/ess/infoApply/viewPiciOtAffirmLBatchList?firstFlag=N')">
		<span><!--导出到Excel--><spring:message code="hrm.empinfo.EXPORT" /></span></a></li>	
	</ul>
</div>
	<form name="delOtApplyAffirmForm" id="delOtApplyAffirmForm" method="post" action="/ess/infoApply/delLOvertimeApplyInBatch" 
	  onsubmit="return delOtApplyCallback(this, navTabAjaxDone);">      
	  <input type="hidden" id="nullOTTSTOAffirmListCnt" value="${nullOTTSTOAffirmListCnt}">
	  <%-- <div class="user_table" style="font:bold 12px/20px arial,sans-serif;">Total:${nullOTTSTOAffirmListCnt}</div>  --%>             
		<table class="orderList" width="3400px" id="otAffirmLBatchTable">
			<thead>
			    <tr>
				    <th rowspan="2" width="5px"><!--NO-->
						NO
					</th>
					<th rowspan="2" >
				    	<input type="checkbox" class="checkboxCtrl" group="c1" />
				    </th>
				    <th rowspan="2" width="120px" class="titleColor"><!--社号-->
						<!--社号--><spring:message code="ess.infoApply.EMPID" />
					</th>
					<th rowspan="2" width="200px"><!--申请人-->
						<!--姓名--><spring:message code="ess.infoApply.NAME" />
					</th >
					<th  rowspan="2" width="220px">
						<!--部门--><spring:message code="ess.infoApply.DEPT" />
					</th>
					<th  rowspan="2" width="140px">
						<!-- 班组 --><spring:message code="hr.viewPersonalInfo.title.banzu"/>
					</th>
					<th rowspan="2" width="120px" class="titleColor"><!--日期-->
						<!--加班日期--><spring:message code="ess.infoApply.title.overtimeTime" />
					</th>
					<th colspan="10" ><!--申请-->
						<!--申请--><spring:message code="ess.infoApply.title.apply" />
					</th>
					<th rowspan="2"  width="300px" class="titleColor"><!--原因-->
						<!--原因--><spring:message code="ess.infoApply.Reason" />
					</th>
					<th colspan="5"><!--加班累计-->
						<!--加班累计--><spring:message code="ess.title.JIABANLEIJI" />
					</th>
					<th colspan="2">
						<!--加班上限--><spring:message code="ar.viewAdjustLeaveTSTOBatchList.JIABANSHANGXIAN.b" />
					</th>
				    <th rowspan="2" width="260px">
						<!--审批者--><spring:message code="hrm.contractInfo.APPROVAL_PERSON" />
					</th>
					<th rowspan="2"  width="140px;">
						<!--审批状态--><spring:message code="ess.infoApply.approval_status" />
					</th>
					<th rowspan="2" width="220px">
						<!--创建者--><spring:message code="hrm.contract.creator" />
					</th>
				</tr>
				<tr>
					<th  width="160px">
						<!--上班时间--><spring:message code="ess.infoApply.work_time" />
					</th>
					<th  width="160px">
						<!--下班时间--><spring:message code="ess.infoApply.out_work_time" />
					</th>
					<th  width="140px">
						<!--进门时间--><spring:message code="ess.infoApply.in_door_time" />
					</th>
					<th  width="140px">
						<!--出门时间--><spring:message code="ess.infoApply.out_door_time" />
					</th>
					<th  width="140px" class="titleColor">
						<!--开始时间--><spring:message code="ess.infoApply.title.startTime" />
					</th>
					<th  width="140px" class="titleColor">
					   <!--结束时间--><spring:message code="ess.infoApply.end_time" />
					</th>
					<th width="120px">
					  <!--加班时长--><spring:message code="ess.infoApply.overtime_hours" />
					</th>
					<th width="100px" class="titleColor">
						<!--扣除吃饭--><spring:message code="ess.viewPiciOtAffirmLBatchList.DEDUCT_MEAL_TIME.b" /> / <spring:message code="heran.ess.viewLikeLeaveApplyInfo.yuanxiuxiri" /> (30')&nbsp;<input type="checkbox" group="DEDUCT_YN" onclick="batchCheckedAndCallength(this)" style="vertical-align:middle"/>
					</th>
					<th width="80px" class="titleColor">
						<!--跨天--><spring:message code="ess.title.KUATIAN" />
					</th>
					<!--<th width="30px" class="titleColor">
					   调休<spring:message code="ar.viewArAnnualStandard.title.tiaoxiu" />
					</th>
					<th width="30px" class="titleColor">
						特殊<spring:message code="ess.title.TESHU" />
					</th>
					-->
					<th width="150px">
					        <!--加班状态--><spring:message code="ess.title.JIABANZHUANGTAI" />
					</th>
					<th  width="70px">
						<!--本年--><spring:message code="ess.viewPiciOtAffirmBatchList.BENNIAN.b" />
					</th>
					<th  width="80px">
						<!--本月--><spring:message code="ess.viewPiciOtAffirmBatchList.BENYUE.b" />
					</th>
					<th  width="80px">
						<!--平时--><spring:message code="ar.viewitemparameter.title.pingshi" />
					</th>
					<th  width="80px">
						<!--周末--><spring:message code="ar.viewitemparameter.title.zhoumo" />
					</th>
					<th  width="80px">
						<!--节假日--><spring:message code="ar.viewitemparameter.title.jiejiari" />
					</th>
					<th  width="100px">
						<!--月上限--><spring:message code="ess.viewPiciOtAffirmBatchList.NIANSHANGXIAN.b" />
					</th>
					<th  width="100px">
						<!--年上限--><spring:message code="ess.viewPiciOtAffirmBatchList.YUESHANGXIAN.b" />
					</th>
				</tr>
			</thead>
			<tbody>
                 <c:forEach items="${nullOTTSTOAffirmList}" var="otApply" varStatus="i">
					<tr target="sid" rel="${admin.personId}">
					    <td  style="text-align: center">${i.count}</td>
					    <td  style="text-align: center">
					    <c:if test="${otApply.AFFIRM_FLAG ne '14014310'}">
					         <input type="checkbox" id="c1_${i.index}" name="c1" value="${otApply.APPLY_NO}" />
					    </c:if>
					    <c:if test="${otApply.AFFIRM_FLAG eq '14014310'}">
					         <input type="checkbox" id="c1_${i.index}" name="c1" value="${otApply.APPLY_NO}" disabled/>
					    </c:if>
					    </td>
					    <td style="text-align: center" id="EMPID_${i.index}" <c:if test='${otApply.PERSON_ID eq null}'>sysLog="lookUp"</c:if> sysIndex="${i.index}" sysPersonId="${otApply.PERSON_ID}" >${otApply.EMPID}</td>
					    <td style="text-align: center; cursor: pointer; color: blue;" id="LOCAL_NAME_${i.index}" sysIndex="${i.index}" <c:if test='${otApply.PERSON_ID ne null}'> onclick='javascript:changeURL_ess3403(${otApply.APPLY_NO });'</c:if> >${otApply.LOCAL_NAME}</td>
					    <td style="text-align: center" id ="DEPT_NAME_${i.index}" sysIndex="${i.index}">${otApply.DEPTNAME}</td>
					    <td style="text-align: center" id ="SHIFT_NAME_${i.index}" sysIndex="${i.index}">${otApply.SHIFT_NAME}</td>
					    <input type="hidden" id="OT_POST_FAMILY_${i.index}" name="OT_POST_FAMILY_${i.index}" value="${otApply.POST_FAMILY }"/>
					    <input type="hidden" id="OT_DUTY_NO_${i.index}" value="${otApply.DUTY_NO}">
					    <td style="text-align: center;" sysLog="date" sysIndex="${i.index}" format="dd/MM/yyyy" id="APPLY_OT_DATE_${i.index}" sysFlag="2">${otApply.APPLY_OT_DATE}</td>
					    <td  style="text-align: center">
					      <span id="SHIFT_START_TIME_${i.index}">${otApply.SHIFT_START_TIME}</span> 
					    </td>
					    <td  style="text-align: center">
					       <span id="SHIFT_END_TIME_${i.index}">${otApply.SHIFT_END_TIME}</span>
					    </td>
					    <td  style="text-align: center">
					      <span id="INDOOR_TIME_${i.index}">${otApply.INDOOR_TIME}</span>
					    </td>
					    <td  style="text-align: center">
					      <span id="OUTDOOR_TIME_${i.index}">${otApply.OUTDOOR_TIME}</span>
					    </td>
					    <td style="text-align: center;" sysLog="select" sysIndex="${i.index}" id="OT_FROM_TIME_${i.index}" sysValue="${TIME_STR}" sysFlag="OT_TIME">${otApply.OT_FROM_TIME}</td>
					    <td style="text-align: center;" sysLog="select" sysIndex="${i.index}" id="OT_TO_TIME_${i.index}" sysValue="${TIME_STR}" sysFlag="OT_TIME">${otApply.OT_TO_TIME}</td>
					   
					    <td  style="text-align: center" id="OT_APPLY_HOUR_TEXT_${i.index}">
					   		 ${otApply.OT_APPLY_HOUR}<!--小时--><spring:message code="ar.viewitemparameter.title.xiaoshi" />
					    </td>
					     <input type="hidden" id="OT_APPLY_HOUR_${i.index}" value="${otApply.OT_APPLY_HOUR}">
					    <td  style="text-align: center">
					       <input type="checkbox" id="DEDUCT_YN_${i.index}" name="DEDUCT_YN" onclick="updateChecked(${i.index});otAffirm_callength(${i.index},2);" <c:if test="${otApply.DEDUCT_YN eq 1}"> checked="checked"</c:if> value="${otApply.DEDUCT_YN}">
					    </td>
					    <td  style="text-align: center">
					       <input type="checkbox" id="OFFSET_YN_${i.index}" onclick="updateChecked(${i.index})" <c:if test="${otApply.OFFSET_YN eq 1}"> checked="checked"</c:if> value="${otApply.OFFSET_YN}">
					    </td>
					    <!--<td  style="text-align: center">
					       <input type="checkbox" id="ADJUST_YN_${i.index}"onclick="otAffirm_callength(${i.index},2)"<c:if test="${otApply.ADJUST_YN eq 1}">checked="checked"</c:if> value="${otApply.ADJUST_YN}">
					    </td>
					    <td  style="text-align: center">
					       <input type="checkbox" id="SPECIAL_YN_${i.index}" onclick="otAffirm_callength(${i.index},2)" <c:if test="${otApply.SPECIAL_YN eq 1}">checked="checked"</c:if> value="${otApply.SPECIAL_YN}">
					    </td>
					    -->
					    <td style="text-align: center;" id="OT_TYPE_CODE_${i.index}">${otApply.OT_TYPE_CODE_NAME}</td>
					    <input type="hidden" id="OT_TYPE_CODE_NO_${i.index}" value="${otApply.OT_TYPE_CODE}" />
					    <td sysLog="text" sysIndex="${i.index}" id="APPLY_OT_REMARK_${i.index}">${otApply.APPLY_OT_REMARK}</td> 
					    <td  style="text-align: center" id="OT_TOTAIL_${i.index}">
					      ${otApply.OT_TOTAIL}
					    </td>
					    <td  style="text-align: center" id="OT_TOTAIL_MONTH_${i.index}">
					      ${otApply.OT_TOTAIL_MONTH}
					    </td>
					    <td  style="text-align: center" id="WEEKDAY_OT_TOTAIL_${i.index}">
					      ${otApply.WEEKDAY_OT_TOTAIL}
					    </td>
					    <td  style="text-align: center" id="WEEKEND_OT_TOTAIL_${i.index}">
					      ${otApply.WEEKEND_OT_TOTAIL}
					    </td>
					    <td  style="text-align: center" id="HOILDAY_OT_TOTAIL_${i.index}">
					      ${otApply.HOILDAY_OT_TOTAIL}
					    </td>
					    <td  style="text-align: center" id="OT_TOTAIL_LIMIT_${i.index}">
					      300
					    </td>
					    <td  style="text-align: center" id="OT_TOTAIL_MONTH_LIMIT_${i.index}">
					      40
					    </td>
					    <input type="hidden" id="OT_LIMIT_${i.index}" value="${otApply.OT_LIMIT}" />
					    <td  style="text-align: center" id="OT_AFFIRMOR_${i.index}"></td>
					    <td  style="text-align: center">
					      ${otApply.AFFIRM_FLAG_NAME}
					      <%-- &nbsp;<c:if test="${otApply.CONFIRM_FLAG ne '1' and otApply.CONFIRM_FLAG ne '2' }"><!--人事未确认--><spring:message code="ess.title.WEIQUEREN" /></c:if><c:if test="${otApply.CONFIRM_FLAG eq '1' }"><!--人事通过--><spring:message code="ess.title.RENSHITONGGUO" /></c:if><c:if test="${otApply.CONFIRM_FLAG eq '2' }"><!--人事否决--><spring:message code="ess.title.RENSHITONGGUO" /></c:if> --%>
					    </td>	
					    <td  style="text-align: center">
					      ${otApply.CREATED_BY}
					    </td>
					    <input type="hidden" id="AFFIRM_FLAG_${i.index}" value="${otApply.AFFIRM_FLAG}">
					    <div id="modifyFlag_${i.index}" sysLog="modifyFlag" sysIndex="${i.index}" style="display:none;"></div>
					</tr>
				</c:forEach> 
			</tbody>
		</table>
	</form>
	<a id="onckSche" name="onckSche"  href="" lookupGroup="person"></a>
</div>