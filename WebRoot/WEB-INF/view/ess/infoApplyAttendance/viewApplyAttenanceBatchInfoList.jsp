<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script>
$(document).ready(function(){
     $("#viewApplyAttenanceBatchInfoList_Serch",navTab.getCurrentPanel()).click(function(){
			$("#viewApplyAttenanceBatchInfoList",navTab.getCurrentPanel()).submit();
	   });
     $('input[type=text]').on('keydown', function(e) {
		    if (e.which == 13) {
		    	$("#viewApplyAttenanceBatchInfoList",navTab.getCurrentPanel()).submit();
		    }
		});
     $('#LeaveApplyAffirmTable',navTab.getCurrentPanel()).on( 'draw.dt', function () {
			initEditFun_ess3401();
			getLeaveTypeCode_ess3401();
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
	//init_ess3401();
});
function init_ess3401(){
	var ids= document.getElementsByName("BATCH_LEAVE");
	for(var i=0;i<ids.length;i++){
			var index = ids[i].id.substring(12);
			callength(index);
			getAgoAffirmor_ess3401(index);
	}
}
function getLeaveTypeCode_ess3401(){
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
function initEditFun_ess3401(){
	$('.orderList tbody tr td:[sysLog="text"]',navTab.getCurrentPanel()).editable({type:'text',
		onblur:function(val,settings){
			var index = $(this).attr("sysIndex");
			if(!$("#BATCH_LEAVE_"+index,navTab.getCurrentPanel()).prop("disabled")){
				$("#BATCH_LEAVE_"+index,navTab.getCurrentPanel()).attr("checked",true); 
			}
			$(this).html(val);
			$("#modifyFlag_" + index,navTab.getCurrentPanel()).html("modify");
			this.editing = false;
		}
	});
	$('.orderList tbody tr td:[sysLog="date"]',navTab.getCurrentPanel()).editable({type:'date1',
		onblur:function(val,settings){
			var index = $(this).attr("sysIndex");
			if(!$("#BATCH_LEAVE_"+index,navTab.getCurrentPanel()).prop("disabled")){
				$("#BATCH_LEAVE_"+index,navTab.getCurrentPanel()).attr("checked",true); 
			}
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
			if(!$("#BATCH_LEAVE_"+index,navTab.getCurrentPanel()).prop("disabled")){
				$("#BATCH_LEAVE_"+index,navTab.getCurrentPanel()).attr("checked",true); 
			}
			$("#modifyFlag_" + index,navTab.getCurrentPanel()).html("modify");
			this.editing = false;
		}
	});
	$('.orderList tbody tr td:[sysLog="lookUp"]').editable({type:'lookUp',
		onblur:function(val,settings){
			var index = $(this).attr("sysIndex");
			$(this).html(val);
			submitKeyClick_applyAttenance(val,index);
			if(!$("#BATCH_LEAVE_"+index,navTab.getCurrentPanel()).prop("disabled")){
				$("#BATCH_LEAVE_"+index,navTab.getCurrentPanel()).attr("checked",true); 
			}
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
				if($("#AFFIRM_FLAG_"+index,navTab.getCurrentPanel()).val()!='14014308' && $("#AFFIRM_FLAG_"+index,navTab.getCurrentPanel()).val()!=''){
					alertMsg.error("<spring:message code='ar.viewApplyAttenanceManagentInfoList.SHENPIZHUANGTAIWEITONGGUO.b' />");//审批状态为通过才能保存
					return false;
				}
				 if($("#APPLY_LENGTH_"+index,navTab.getCurrentPanel()).val()=='0' || $("#APPLY_LENGTH_"+index,navTab.getCurrentPanel()).val()==''){
					alertMsg.error("<spring:message code='ar.viewApplyAttenanceManagentInfoList.SHICHANGBUNENGDENGYULING.b' />");//时长不能等于0
					return false;
				} 
				leaveFromDate=$("#FROM_DATE_"+index,navTab.getCurrentPanel()).html() +" "+ $("#FROM_TIME_"+index,navTab.getCurrentPanel()).html();
				leaveToDate=$("#TO_DATE_"+index,navTab.getCurrentPanel()).html() +" "+ $("#TO_TIME_"+index,navTab.getCurrentPanel()).html();
				leaveTypeCode = $("#LEAVE_TYPE_CODE_"+index,navTab.getCurrentPanel()).val();
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
					 data:{sql:"select AR_GET_LEAVE_CLASH('"+applyNo+"','"+sysPersonId+""+leaveTypeCode+"',to_char(to_date('" + leaveFromDate + "','DD-MM-YYYY HH24:MI'),'YYYY-MM-DD HH24:MI'),to_char(to_date('" + leaveToDate + "','DD-MM-YYYY HH24:MI'),'YYYY-MM-DD HH24:MI')) FLAG from dual"},
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
						else if(flag == -3){
							alertMsg.error("<spring:message code='alert.message.ess.infoApply.noEnoughAnnualCannotApplyAnnualLeave' />");//No enough anual leave
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
							dayHours = 8; //Mặc định là  = 8
							/* var shengYu = data.result[0].SHENGYU_VAC; */
								if(parseFloat($("#APPLY_LENGTH_"+index,navTab.getCurrentPanel()).val()) > parseFloat($("#SHENGYU_VAC_CNT_"+index,navTab.getCurrentPanel()).html())* dayHours){
									alertMsg.error("<spring:message code='ar.viewApplyAttenanceManagentInfoList.NIANJIATIANSHUBUZU.b' />");//年假天数不足
									return false;
								}
						 },
						 error:DWZ.ajaxError
					});
					if(parseFloat($("#APPLY_LENGTH_"+index,navTab.getCurrentPanel()).val()) % (parseFloat($("#DAY_HOURS_"+index,navTab.getCurrentPanel()).val()) / 2) != 0 
							&& (parseFloat($("#DAY_HOURS_"+index,navTab.getCurrentPanel()).val())) != 7
							&& (parseFloat($("#DAY_HOURS_"+index,navTab.getCurrentPanel()).val())) != 7.67){
						alertMsg.error("<spring:message code='alert.message.ess.infoApply.applyTimeCanNotLessThanMinValue' />");//休假最小指是半天
						return false;
					}
				}
				
				var applyTypeCode = $("#LEAVE_TYPE_CODE_"+index,navTab.getCurrentPanel()).val();
				if(applyTypeCode == '27' || applyTypeCode == '482' || applyTypeCode == '15821' || applyTypeCode == '14013816' || applyTypeCode == '25' || 
						applyTypeCode == '14015956' || applyTypeCode == '14015957' ){
					
					if(parseFloat($("#APPLY_LENGTH_"+index,navTab.getCurrentPanel()).val()) % (parseFloat($("#DAY_HOURS_"+index,navTab.getCurrentPanel()).val())) != 0 
							&& (parseFloat($("#DAY_HOURS_"+index,navTab.getCurrentPanel()).val())) != 7
							&& (parseFloat($("#DAY_HOURS_"+index,navTab.getCurrentPanel()).val())) != 7.67){
						alertMsg.error('<spring:message code="alert.message.ess.infoApply.applyTimeCanNotLessThanMinValue" />');//休假最小指是半天
				   		return false;
					}
				}
				/*if($("#LEAVE_TYPE_CODE_"+index,navTab.getCurrentPanel()).html()=='<spring:message code="ar.viewArAnnualStandard.title.tiaoxiu" />'){//调休
					if(parseFloat($("#APPLY_LENGTH_"+index,navTab.getCurrentPanel()).val()) > parseFloat($("#TOT_SHENGYU_CNT_"+index,navTab.getCurrentPanel()).html())){
						alertMsg.error("<spring:message code='ar.viewApplyAttenanceManagentInfoList.TIAOXIUSHISHUBUZU.b' />");//调休时数不足
						return false;
					}
				}                                                                                 //哺乳假(集中)
				if($("#LEAVE_TYPE_CODE_"+index,navTab.getCurrentPanel()).html()=='<spring:message code="ess.viewApplyAttenanceBatchInfoList.BURUJIAJIZHONG.a" />'&&$("#FROM_DATE_"+index,navTab.getCurrentPanel()).html()!=$("#TO_DATE_"+index,navTab.getCurrentPanel()).html()){
					alertMsg.error("<spring:message code='ar.viewApplyAttenanceManagentInfoList.SHENQINGMEITIANYIXIAOSHIXUANBURUJIA.b' />");//申请每天一小时请选择“哺乳假”
					return false;
				}
				//南京医疗期天数判断
				<c:if test="${LoginUser.cpnyId eq 'SPC_NJ'}">
				if($("#LEAVE_TYPE_CODE_"+index,navTab.getCurrentPanel()).html()=='<spring:message code="ar.menu.title.bingjia" />'){//病假
					$.ajax({
						 cache: false,
						 type: 'post',
						 url: '/hrm/recruitManage/doSql',
						 data:{sql:"select CHECK_LEAVE_TIME_SPC_NJ('','"+leaveFromDate+"','"+leaveToDate+"','"+sysPersonId+"','15821') RETURN_FLAG from dual"},
						 dataType:"json",
						 success: function(data) {
							var returnFlag = data.result[0].RETURN_FLAG;
							if(returnFlag != 'OK'){
								alertMsg.error(returnFlag);
								return false;
							}
						 },
						 error:DWZ.ajaxError
					});
				}
				</c:if>*/
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
		 			url:'/ess/infoApplyAttendance/saveAttendanceApplyInfoForBatch' ,
		 			data:[{ name: 'jsonData', value: jsonData }],
		 			dataType:"json",
					cache: false,
					success: function(data){ //请求成功后处理函数。
						if(data.statusCode=="200"){
							navTabSearch($("#viewApplyAttenanceBatchInfoList"));
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
	 		}
		});
	 	return false;
}
 function addApplyAttenanceBatchInfo(){
	 $.ajax({
			type:'post',
			url:'/ess/infoApplyAttendance/addAttendanceApplyInfoForBatch',
			dataType:null,
			success: function(data){ //请求成功后处理函数。
				navTab.reload('/ess/infoApplyAttendance/viewApplyAttenanceBatchInfoList?type=add&firstFlag=N&seach_START_DATE=${START_DATE}&seach_END_DATE=${END_DATE}');
	   	 	}  ,
			error: DWZ.ajaxError});
} 
 function addApplyAttenanceNoRefresh(){
	 $.ajax({
			type:'post',
			url:'/ess/infoApplyAttendance/addAttendanceApplyInfoForBatch',
			dataType:null,
			success: function(data){ //请求成功后处理函数。
					 $.ajax({
							type:'POST',
							url:'/ar/attendanceMintenance/getAddAttendanceApplyInfo',
							dataType:"json",
							//cache: false,
							success: function(data){ //请求成功后处理函数。
								if(data.statusCode=="200"){
									for(var i=0;i<data.nullLeaveAffirmList.length;i++){
										
										var tb2 = document.getElementById("LeaveApplyAffirmTable");
										var newFlag = 0;
										var htm = "";
                                        var ids = document.getElementsByName("BATCH_LEAVE");

										for (var j=0;j<ids.length;j++) {
											if(ids[j].value == data.nullLeaveAffirmList[i].APPLY_NO){
                                            	++newFlag;
                                            }
									    }

										if(newFlag == 0){
									   	   	
									   	   	htm += '<tr><td style="text-align: center">' + (tb2.rows.length) + '</td>';
									   	    htm += '<td style="text-align: center"><input type="checkbox" id="BATCH_LEAVE_'+(tb2.rows.length)+'" sysIndex="'+(tb2.rows.length)+'" name="BATCH_LEAVE" value="'+data.nullLeaveAffirmList[i].APPLY_NO+'" />';
                                            htm += '<input type="hidden" id="PERSON_ID_'+(tb2.rows.length)+'" value="'+data.nullLeaveAffirmList[i].PERSON_ID+'"></td>';
                                            htm += '<td style="text-align: center" id="EMPID_'+(tb2.rows.length)+'" sysLog="lookUp" sysIndex="'+(tb2.rows.length)+'" sysPersonId="" ></td>';
                                            htm += '<td style="text-align: center; color: blue; cursor: pointer;" id="LOCAL_NAME_'+(tb2.rows.length)+'" sysIndex="'+(tb2.rows.length)+'"></td>';
                                            htm += '<td style="text-align: center" id ="DEPT_NAME_'+(tb2.rows.length)+'" sysIndex="'+(tb2.rows.length)+'"></td>';
                                            htm += '<td style="text-align: center" id="SHIFT_NAME_'+(tb2.rows.length)+'"></td>';
                                            htm += '<input type="hidden" id="POST_FAMILY_'+(tb2.rows.length)+'" value="">';
                                            htm += '<td  style="text-align: center">'+data.nullLeaveAffirmList[i].APPLY_TIME+'</td>';
                                            htm += '<td style="text-align: center"><span id="TOT_VAC_CNT_'+(tb2.rows.length)+'">'+data.nullLeaveAffirmList[i].TOT_VAC_CNT+'</span><spring:message code="ar.viewsummaryparameteritem.title.day"/></td>';
                                            htm += '<td style="text-align: center"><span id="SHENGYU_VAC_CNT_'+(tb2.rows.length)+'">'+data.nullLeaveAffirmList[i].SHENGYU_VAC_CNT+'</span><spring:message code="ar.viewsummaryparameteritem.title.day"/></td>';
                                            htm += '<td style="text-align: center;" sysLog="select" sysIndex="'+(tb2.rows.length)+'" id="LEAVE_TYPE_CODE_NAME_'+(tb2.rows.length)+'" sysValue=""></td>';
                                            htm += '<input type="hidden" id="LEAVE_TYPE_CODE_'+(tb2.rows.length)+'" value="">';
                                            htm += '<td style="text-align: center;" sysLog="date" sysIndex="'+(tb2.rows.length)+'" format="dd-MM-yyyy" id="FROM_DATE_'+(tb2.rows.length)+'" sysFlag="1">'+data.nullLeaveAffirmList[i].FROM_DATE+'</td>';
                                            htm += '<input type="hidden" id="LEAVE_FROM_DATE_'+(tb2.rows.length)+'" value="'+data.nullLeaveAffirmList[i].FROM_DATE+'">';
                                            htm += '<td style="text-align: center;" sysLog="select" sysIndex="'+(tb2.rows.length)+'" id="FROM_TIME_'+(tb2.rows.length)+'" sysValue="'+"${TIME_STR}"+'" sysFlag="TIME">'+data.nullLeaveAffirmList[i].FROM_TIME+'</td>';
                                            htm += '<td style="text-align: center;" sysLog="date" sysIndex="'+(tb2.rows.length)+'" format="dd-MM-yyyy" id="TO_DATE_'+(tb2.rows.length)+'" sysFalg="1">'+data.nullLeaveAffirmList[i].TO_DATE+'</td>';
                                            htm += '<input type="hidden" id="LEAVE_TO_DATE_'+(tb2.rows.length)+'" value="'+data.nullLeaveAffirmList[i].TO_DATE+'">';
                                            htm += '<td style="text-align: center;" sysLog="select" sysIndex="'+(tb2.rows.length)+'" id="TO_TIME_'+(tb2.rows.length)+'" sysValue="'+"${TIME_STR}"+'" sysFlag="TIME">'+data.nullLeaveAffirmList[i].TO_TIME+'</td>';
                                            htm += '<td style="text-align: center" id="APPLY_LENGTH_TEXT_'+(tb2.rows.length)+'"><spring:message code="ar.viewsummaryparameteritem.title.hour"/></td>';
                                            htm += '<input type="hidden" id = "APPLY_LENGTH_'+(tb2.rows.length)+'" value="">';
                                            htm += '<td sysLog="text" sysIndex="'+(tb2.rows.length)+'" id="LEAVE_REASON_'+(tb2.rows.length)+'"></td>';
                                            htm += '<td></td>';
                                            htm += '<td style="text-align: center" id="AFFIRMOR_'+(tb2.rows.length)+'"></td>';
                                            htm += '<td style="text-align: center"><spring:message code="ess.title.WEIQUEREN"/></td>';
                                            htm += '<td style="text-align: center">'+data.nullLeaveAffirmList[i].CREATED_BY+'</td>';
                                            //htm += '<td style="text-align: center">'+data.nullLeaveAffirmList[i].CREATE_DATE+'</td>';
                                            //htm += '<td style="text-align: center"></td>';
                                            //htm += '<td style="text-align: center"></td>';
                                            htm += '<input type="hidden" id = "AFFIRM_FLAG_'+(tb2.rows.length)+'" value="">';
                                            htm += '<div id="modifyFlag_'+(tb2.rows.length)+'" sysLog="modifyFlag" sysIndex="'+(tb2.rows.length)+'" style="display:none;"></div>';
                                            htm += '</tr>';

										   	   //当前行之后插入一行
									   	   	var row = tb2.rows[tb2.rows.length - 1];
									   	   	$(row).after(htm);
									   	   	
									   	 	initEditFun_ess3401();
											getLeaveTypeCode_ess3401();
									   	}
									}
								}
					   	 	},
							error: DWZ.ajaxError
					});
	   	 	},
			error: DWZ.ajaxError});

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
		//var ids= document.getElementsByName("BATCH_LEAVE");
		var ids= $("input[type='checkbox'][name='BATCH_LEAVE']");
		for(var i=0;i<ids.length;i++){
			if(ids[i].checked){
				checked=true;
				
				var index = ids[i].id.substring(12);
				var sysPersonId = $("#EMPID_"+index,navTab.getCurrentPanel()).attr('sysPersonId');
				var leaveFromDate=$("#FROM_DATE_"+index,navTab.getCurrentPanel()).html() +" "+ $("#FROM_TIME_"+index,navTab.getCurrentPanel()).html();
				var leaveToDate=$("#TO_DATE_"+index,navTab.getCurrentPanel()).html() +" "+ $("#TO_TIME_"+index,navTab.getCurrentPanel()).html();
				var applyNo = $("#BATCH_LEAVE_"+index,navTab.getCurrentPanel()).val();
				var leaveTypeCode = $("#LEAVE_TYPE_CODE_"+index,navTab.getCurrentPanel()).val();
				$.ajax({
					 cache: false,
					 type: 'post',
					 url: '/hrm/recruitManage/doSql',
					 data:{sql:"select AR_GET_LEAVE_CLASH('"+applyNo+"','"+sysPersonId+""+leaveTypeCode+"',to_char(to_date('" + leaveFromDate + "','DD-MM-YYYY HH24:MI'),'YYYY-MM-DD HH24:MI'),to_char(to_date('" + leaveToDate + "','DD-MM-YYYY HH24:MI'),'YYYY-MM-DD HH24:MI')) FLAG from dual"},
					 dataType:"json",
					 success: function(data) {
						var flag = data.result[0].FLAG;
						 if(flag == -1){
							alertMsg.error("<spring:message code='ar.viewApplyAttenanceManagentInfoList.BAOHANKAOQINGUANBIDESHIJIAN.b' />");//包含考勤关闭的时间
							return false;
						}else if(flag == -2){
							alertMsg.error("<spring:message code='ar.viewArOvertimeManaget_fast.Include_apply_closed.b' />");//包含申请关闭的日期
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
							navTabSearch($("#viewApplyAttenanceBatchInfoList"));
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
		if(!$("#BATCH_LEAVE_"+index,navTab.getCurrentPanel()).prop("disabled")){
			$("#BATCH_LEAVE_"+index,navTab.getCurrentPanel()).attr("checked",true); 
		}
		$(this).html(val);
		$("#modifyFlag_" + index,navTab.getCurrentPanel()).html("modify");
		this.editing = false;
	}
});
$('.orderList tbody tr td:[sysLog="date"]',navTab.getCurrentPanel()).editable({type:'date1',
	onblur:function(val,settings){
		var index = $(this).attr("sysIndex");
		if(!$("#BATCH_LEAVE_"+index,navTab.getCurrentPanel()).prop("disabled")){
			$("#BATCH_LEAVE_"+index,navTab.getCurrentPanel()).attr("checked",true); 
		}
		$("#modifyFlag_" + index,navTab.getCurrentPanel()).html("modify");
		this.editing = false;
	}
});
$('.orderList tbody tr td:[sysLog="select"]').editable({type:'select',
	onblur:function(val,settings){
		$(this).html(val);
		var index = $(this).attr("sysIndex");
		callength(index);
		if(!$("#BATCH_LEAVE_"+index,navTab.getCurrentPanel()).prop("disabled")){
			$("#BATCH_LEAVE_"+index,navTab.getCurrentPanel()).attr("checked",true); 
		}
		$("#modifyFlag_" + index,navTab.getCurrentPanel()).html("modify");
		this.editing = false;
	}
});
$('.orderList tbody tr td:[sysLog="lookUp"]').editable({type:'lookUp',
	onblur:function(val,settings){
		var index = $(this).attr("sysIndex");
		$(this).html(val);
		submitKeyClick_applyAttenance(val,index);
		if(!$("#BATCH_LEAVE_"+index,navTab.getCurrentPanel()).prop("disabled")){
			$("#BATCH_LEAVE_"+index,navTab.getCurrentPanel()).attr("checked",true); 
		}
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
	/* if(leaveTypeCode=='16415'){//哺乳假
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
	}else{ */
		$.ajax({
			 cache: false,
			 type: 'post',
			 async:false,
			 url: '/hrm/recruitManage/doSql',
			 data:{sql:"select AR_GET_DAY_HOURS('" + personId + "',to_char(to_date('" + leaveFromDate + "','DD-MM-YYYY HH24:MI:SS'),'YYYY-MM-DD HH24:MI:SS')) DAY_HOURS,GET_AR_LEAVE_LENGTH('" + personId + "','${LoginUser.cpnyId}',to_date('" + leaveFromDate + "','DD-MM-YYYY HH24:MI:SS'),to_date('" + leaveToDate+"','DD-MM-YYYY HH24:MI:SS'),'"+ leaveTypeCode + "') LEAVE_LENGTH from dual"},
			 dataType:"json",
			 success: function(data) {
				$("#APPLY_LENGTH_"+index,navTab.getCurrentPanel()).val(data.result[0].LEAVE_LENGTH);
				$("#DAY_HOURS_"+index,navTab.getCurrentPanel()).val(data.result[0].DAY_HOURS);
				var lengthText = "";
				var dayHours = data.result[0].DAY_HOURS;
				var length = Math.floor(data.result[0].LEAVE_LENGTH/dayHours) ;
				if( length > 0 ){
					lengthText += length + "<spring:message code='ar.viewitemparameter.title.dayofunit' />";//天
				}
				var length = (data.result[0].LEAVE_LENGTH % dayHours).toFixed(1) ;
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
	//}
	getAffirmor_ess3401(index);
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
					getAffirmor_ess3401(index);
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

function getAgoAffirmor_ess3401(index){
	var htm = '';
	var applyNo = $("#BATCH_LEAVE_"+index,navTab.getCurrentPanel()).val();
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
					$("#AFFIRMOR_"+index,navTab.getCurrentPanel()).html(htm);
				}else{
					$("#AFFIRMOR_"+index,navTab.getCurrentPanel()).html('');
				}
			},
			error: DWZ.ajaxError
		});
}

function getAffirmor_ess3401(index){
	 var applyTypeCode = $("#LEAVE_TYPE_CODE_"+index,navTab.getCurrentPanel()).val(); 
	 var applyLength = $("#APPLY_LENGTH_"+index,navTab.getCurrentPanel()).val(); 
	 var htm = '';
	 var personId = $("#EMPID_"+index,navTab.getCurrentPanel()).attr('sysPersonId');
	 var dutyNo = $("#DUTY_NO_"+index,navTab.getCurrentPanel()).val();

	 if($("#AFFIRM_FLAG_"+index,navTab.getCurrentPanel()).val()!='14014308' && $("#AFFIRM_FLAG_"+index,navTab.getCurrentPanel()).val()!=''){
		 return false;
	 }
	
		$.ajax({
			type: 'POST',
			url: '/ess/infoApplyAttendance/viewAffirmorByPersonIdListForCode',
			data:[{ name: 'applyTypeCode', value: applyTypeCode },/* 审批线相同，无所谓 */
			      { name: 'applyLength', value: applyLength },
			      { name: 'applyTypeNo', value: '21' },
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
					$("#AFFIRMOR_"+index,navTab.getCurrentPanel()).html(htm);
				}else{
					$("#AFFIRMOR_"+index,navTab.getCurrentPanel()).html('');
				}
			},
			error: DWZ.ajaxError
		});
}
function excelimport_ess3401(){
	$("#importExcelDialog_ess3401").attr('href','/pa/excelImport/importExcelData?importFunName=/importApplyAttenance');
	$("#importExcelDialog_ess3401").click();
}
function changeURL_ess3401(applyNo){
	var href = "/ess/infoApply/viewApprovaledLeaveInfo?seach_APPLY_NO=" + applyNo;
	$.pdialog.open(href,"ess3401", "<spring:message code='ar.viewApplyAttenanceManagentInfoList.MINGXICHAKAN.b' />", {width:1000,height:600,mask:true});//明细查看
}
</script>
<a id="importExcelDialog_ess3401"  href="#" target="dialog" mask="true"></a>
<a id="importExcel_ess3401" href="#" target="navTab" mask="true"><span style="display:none;"><!-- 导入结果 --><spring:message code="ess.title.DAORUJIEGUO" /></span></a>
<div id="viewApplyAttenBatch"  class="pageHeader" >
	<form onsubmit="return navTabSearch(this);" action="/ess/infoApplyAttendance/viewApplyAttenanceBatchInfoList?firstFlag=N&deleteYN=Y" method="post"
		id="viewApplyAttenanceBatchInfoList" name="viewApplyAttenanceBatchInfoList">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td><!-- 社号/姓名 --><spring:message code="hrm.empinfo.nameAndEmpid" /></td>
					<td>
						<div style="float:left"><input type="text" name="seach_KEY" id="seach_KEY" value="${KEY}"/></div>
					</td>
					<td><!-- 部门 --><spring:message code="hr.viewPersonalInfo.title.DEPTNAME" /></td>
					<td>
						<%-- <ait:deptList name="seach_DEPTNO" cpnyId="${defaultCpny}" limit="manager" id="viewApplyLeaveInfoBatchList_seachDept" selected="${DEPTNO}"/>
						<ait:deptTreeIcon name="seach_DEPTNO" cpnyId="${defaultCpny}" limit="manager" id="viewApplyLeaveInfoBatchList_seachDept" selected="${DEPTNO}"/> --%>
						<ait:deptTreeMulti name="seach_DEPTNAME" id="seach_DEPTNO_Multi" limit="manager" selectedNm="${DEPTNAME }" selected="${DEPTNO_Multi }"></ait:deptTreeMulti>
					</td>
					<td> <!-- 期间 --><spring:message code="ess.infoApply.Period" /></td>
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
				    <td><!-- 考勤类型 --><spring:message code="ess.infoApply.attendance_type" /></td>
					<td>
						<ait:SelectSyCodeByCpnyID name="seach_LEAVE_TYPE_CODE" parentNo="21" selected="${LEAVE_TYPE_CODE }" limit="all"/>
					</td>
				</tr>
				<tr>
				    <td><!-- 审批状态 --><spring:message code="ess.infoApply.approval_status" /></td>
					<td>
						<ait:SelectSyCodeByCpnyID name="seach_AFFIRM_FLAG" parentNo="14014304" selected="${AFFIRM_FLAG}" limit="ALL"/>
						<input type="checkbox" name="CANCEL_FLAG" value="1" <c:if test="${CANCEL_FLAG eq '1' }">checked="checked"</c:if>> <!-- 取消申请 --><spring:message code="ess.affirmApply.title.quxiaoshenqing"/>
					</td>
					<td><!-- 人事确认状态 --><spring:message code="ess.title.RENSHIQUERENZHUANGTAI" /></td>
					<td>
					<select name="seach_CONFIRM_FLAG">
						<option value=""<c:if test="${CONFIRM_FLAG == null}">selected</c:if>><!-- 全部 --><spring:message code="org.title.ALL" /></option>
						<option value="0"<c:if test="${CONFIRM_FLAG == '0'}">selected</c:if>><!-- 未确认--><spring:message code="ess.title.WEIQUEREN" /></option>
						<option value="1"<c:if test="${CONFIRM_FLAG == '1'}">selected</c:if>><!-- 通过--><spring:message code="ess.infoApply.adopt" /></option>
						<option value="2"<c:if test="${CONFIRM_FLAG == '2'}">selected</c:if>><!-- 否决--><spring:message code="ess.infoApply.veto" /></option> 
					</select>
					</td>
				</tr>
			</table>
		</div>
	</form>
</div>
<div id="viewApplyAttenBatch" class="pageHeader" >
    <div class="searchBar">
			<table class="searchContent">
			    <tr>
			       <td><!-- 考勤状态--><spring:message code="ess.infoApply.attendance_type" /></td>
				   <td>
				   <ait:SelectSyCodeByCpnyID name="LEAVE_TYPE_CODE" id="LEAVE_TYPE_CODE" parentNo="21" selected="" limit="all"/>
				  		
				   </td>
				   <td><!--开始日期--><spring:message code="org.title.STARTDATE" /> </td>
				   <td>
				       <input type="text" name="FROM_DATE" id="FROM_DATE"  class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})" value=""/>
				   </td>
				   <td><!--开始时间--><spring:message code="ess.infoApply.title.startTime" /></td>
				   <td>
						<ait:time name="FROM_TIME"  spacing="10"   selected=""/>
					</td>
			    </tr>
				<tr>
					<td><!--原因--><spring:message code="hrm.empinfo.reason" /></td>
					<td>
						<input type="text" id="LEAVE_REASON" name="LEAVE_REASON"  value=""/>
					</td >
					<td><!--结束日期--><spring:message code="hrm.recruitManage.END_DATE1" /> </td>
				   <td>
				       <input type="text" name="TO_DATE" id="TO_DATE"  class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})" value=""/>
				       
				   </td>
					<td><!--结束时间--><spring:message code="ess.infoApply.end_time" /> </td>
					<td>
						<ait:time name="TO_TIME"  spacing="10"  selected=""/>
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
<div class="formBar">
	<ul class="toolBar">
	    <li><a class="buttonActive" id="viewApplyAttenanceBatchInfoList_Serch" href="#"><span><!--查询--><spring:message code="org.title.SELECT" /></span></a></li>
	    <c:if test="${type eq '' || type eq null}">
	    	<li><a class="buttonActive" onclick="addApplyAttenanceBatchInfo()"><span><!--添加--><spring:message code="ess.empInfo.insert" /></span></a></li>
	    </c:if>
	    <c:if test="${type ne '' && type ne null}">
	    	<li><a class="buttonActive" onclick="addApplyAttenanceNoRefresh()"><span><!-- 添加 --><spring:message code="ess.empInfo.insert"/></span></a></li>
	    </c:if>
		<li><a class="buttonActive" onclick="delLeaveApplyCallbackBatch(0,'delLeaveApplyAffirmFormBatch',DWZ.ajaxDone)"><span><spring:message code="ess.affirmApply.title.quxiaoshenqing" /><!--取消申请--></span></a></li>
		<li><a class="buttonActive" onclick="saveApplyAttenanceBatchInfo()"><span><!--保存--><spring:message code="ar.viewempcalender.title.save" /></span></a></li>
		<li><a class="buttonActive" href="/pa/excelExport/downloadExcelAttendanceApply?file=AttendanceApply_add" ><span><!--模板下载--><spring:message code="ess.message.template_download" /></span></a></li>
		<li><a class="buttonActive" onclick="excelimport_ess3401()"><span><!--Excel导入--><spring:message code="ess.infoApply.EXCEL_IN" /></span></a></li>
		<li><a class="buttonActive" onclick="downloadExcel('viewApplyAttenanceBatchInfoList','/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=122','/ess/infoApplyAttendance/viewApplyAttenanceBatchInfoList?firstFlag=N&deleteYN=Y')"><span><!--导出到Excel--><spring:message code="hrm.empinfo.EXPORT" /></span></a></li>
	</ul>
</div >
	<form name="delLeaveApplyAffirmForm" id="delLeaveApplyAffirmFormBatch" method="post" action="/ess/infoApplyAttendance/delAttendanceApplyInBatchForBatch" 
	  onsubmit="return delLeaveApplyCallbackBatch(this, navTabAjaxDone);">     
	  <input type="hidden" id="nullLeaveAffirmListCnt" value="${nullLeaveAffirmListCnt }">
	  <%-- <div class="user_table" style="font:bold 12px/20px arial,sans-serif;">Total:${nullLeaveAffirmListCnt}</div> --%>
		<table class="orderList" width="2800px" id="LeaveApplyAffirmTable">                                           
			<thead>
				<tr>
				    <th width="5px;" ><!--NO-->
						NO
					</th>
					<th width="5px;" >
				    	<input type="checkbox" class="checkboxCtrl" group="BATCH_LEAVE" />
				    </th>
				    <th width="100px;"  class="titleColor"><!--社号-->
						<!--社号--><spring:message code="ess.infoApply.EMPID" />
					</th>
					<th  width="140px;" ><!--申请人-->
						<!--姓名--><spring:message code="ess.infoApply.NAME" />
					</th>
					<th width="220px;" ><!--部门-->
						<!--部门--><spring:message code="ess.infoApply.DEPT" />
					</th>
					<th width="80px">
						<!-- 班组 --><spring:message code="hr.viewPersonalInfo.title.banzu"/>
					</th>
					<th width="80px;" >
						<!--申请日期--><spring:message code="ess.empInfo.date_application" />
					</th>
					<th style="text-align: center" width="100px;" ><!--年假剩余-->
						<!--年假总数--><spring:message code="ess.infoApply.sum_year_leave_days" />
					</th>
					<th style="text-align: center" width="100px;" ><!--年假剩余-->
						<!--年假剩余--><spring:message code="ess.infoApply.nianjiashengyu" />
					</th>
					<!--<th style="text-align: center"width="50px;" >调休剩余
						调休剩余<spring:message code="ess.title.TIAOXIUSHENGYU" />
					</th>
					-->
					<th style="text-align: center"width="120px;" class="titleColor" ><!--考勤状态-->
						<!--考勤状态--><spring:message code="ess.infoApply.attendState" />
					</th>
					<th style="text-align: center" width="90px;" class="titleColor"><!--班次-->
						<!--开始日期--><spring:message code="org.title.STARTDATE" />
					</th>
					<th style="text-align: center" width="90px;" class="titleColor"><!--开始时间-->
						<!--开始时间--><spring:message code="ess.infoApply.title.startTime" />
					</th>
					<th style="text-align: center" width="90px;" class="titleColor"><!--结束日期-->
						<!--结束日期--><spring:message code="hr.viewTranslate.title.PUBLIC_END_DATE" />
					</th>
					<th style="text-align: center" width="90px;" class="titleColor"><!--结束时间-->
						<!--结束时间--><spring:message code="ess.infoApply.end_time" />
					</th>
					<th style="text-align: center" width="100px;" ><!--时长-->
						<!--时长--><spring:message code="ess.infoApply.duration" />
					</th>
					<th style="text-align: center" width="250px"  class="titleColor"><!--原因-->
						<!--原因--><spring:message code="ess.infoApply.Reason" />
					</th>
					<%-- <th style="text-align: center" width="150px">
						<!--附件--><spring:message code="ess.empInfo.enclosure" />
					</th> --%>
						<th style="text-align: center" width="180px;" >
							<!--审批者--><spring:message code="hrm.contractInfo.APPROVAL_PERSON" />
						</th>
					<th style="text-align: center" width="140px;">
						<!--审批状态--><spring:message code="ess.infoApply.approval_status" />
					</th>
					<th style="text-align: center" width="250px">
							<!--人事记录--><spring:message code="evs.affirm.personnel_record.e" />
						</th>
					<th style="text-align: center" width="140px;">
						<!--创建者--><spring:message code="hrm.contract.creator" />
					</th>
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
					        	<input type="checkbox" id="BATCH_LEAVE_${i.index}" name="BATCH_LEAVE" value="${leaveApply.APPLY_NO}" disabled />
					        </c:if>
					        <input type="hidden" id="PERSON_ID_${i.index}" value="${leaveApply.PERSON_ID}">
					    </td>
					    <td style="text-align: center;" id="EMPID_${i.index}" <c:if test='${leaveApply.PERSON_ID eq null}'>sysLog="lookUp"</c:if> sysIndex="${i.index}" sysPersonId="${leaveApply.PERSON_ID}" >${leaveApply.EMPID}</td>
					    <td style="text-align: center; color: blue; cursor: pointer;" id="LOCAL_NAME_${i.index}" sysIndex="${i.index}" <c:if test='${leaveApply.PERSON_ID ne null}'> onclick='javascript:changeURL_ess3401(${leaveApply.APPLY_NO });' </c:if>>${leaveApply.LOCAL_NAME}</td>
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
					    <td style="text-align: center;" sysLog="date" sysIndex="${i.index}" format="dd/MM/yyyy" lang="en" id="FROM_DATE_${i.index}" sysFlag="1">${leaveApply.FROM_DATE}</td>
					    <input type="hidden" id="LEAVE_FROM_DATE_${i.index}" value="${leaveApply.FROM_DATE}">
					    <td style="text-align: center;" sysLog="select" sysIndex="${i.index}" id="FROM_TIME_${i.index}" sysValue="${TIME_STR}" sysFlag="TIME">${leaveApply.FROM_TIME }</td>
					    <td style="text-align: center;" sysLog="date" sysIndex="${i.index}" format="dd/MM/yyyy" id="TO_DATE_${i.index}" sysFalg="1">${leaveApply.TO_DATE}</td>
					    <input type="hidden" id="LEAVE_TO_DATE_${i.index}" value="${leaveApply.TO_DATE}">
					    <td style="text-align: center;" sysLog="select" sysIndex="${i.index}" id="TO_TIME_${i.index}" sysValue="${TIME_STR}" sysFlag="TIME">${leaveApply.TO_TIME}</td>
					    <td style="text-align: center" id="APPLY_LENGTH_TEXT_${i.index}">${leaveApply.APPLY_LENGTH}<!--小时--><spring:message code="ar.viewitemparameter.title.xiaoshi" /></td>
					    <input type="hidden" id = "APPLY_LENGTH_${i.index}" value="${leaveApply.APPLY_LENGTH}">
					    <input type="hidden" id = "DAY_HOURS_${i.index}" value="${leaveApply.DAY_HOURS}">
					    <input type="hidden" id = "DUTY_NO_${i.index}" value="${leaveApply.DUTY_NO}">
					    <td sysLog="text" sysIndex="${i.index}" id="LEAVE_REASON_${i.index}">${leaveApply.LEAVE_REASON}</td>
					    <%-- <td >
					    	<c:forEach items="${leaveApply.fileList}" var="item" varStatus="j">
									<a href="/ess/infoApplyLeave/downloadFile?fileName=${item.FILE_URL }&file=${item.FILE_NAME}" >${item.FILE_NAME}</a><br/>
							</c:forEach>
						</td>  --%>
					    <td style="text-align: center" id="AFFIRMOR_${i.index}"></td>
					    <td  style="text-align: center">${leaveApply.AFFIRM_FLAG_NAME}&nbsp;/ 
						    <c:if test="${leaveApply.CONFIRM_FLAG ne '1' and leaveApply.CONFIRM_FLAG ne '2' }"><!--人事未确认-->
						    	<spring:message code="ess.title.WEIQUEREN" /></c:if>
						    <c:if test="${leaveApply.CONFIRM_FLAG eq '1' }"><!--人事通过-->
						    	<spring:message code="ess.title.RENSHITONGGUO" /></c:if>
						    <c:if test="${leaveApply.CONFIRM_FLAG eq '2' }"><!--人事否决-->
						    	<spring:message code="ess.title.RENSHIFOUJUE" />
					    </c:if></td>
					    <td style="text-align: center">${leaveApply.HR_COMMENT}</td>
					   	<td style="text-align: center">${leaveApply.CREATED_BY} : ${leaveApply.CREATE_DATE}</td>
					    <input type="hidden" id = "AFFIRM_FLAG_${i.index}" value="${leaveApply.AFFIRM_FLAG}">
					    <div id="modifyFlag_${i.index}" sysLog="modifyFlag" sysIndex="${i.index}" style="display:none;"></div>
					</tr>
			</c:forEach>
			</tbody>
		</table>
	</form >
	<a id="onckSche" name="onckSche"  href="" lookupGroup="person"></a>
</div>