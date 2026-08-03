<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script>
$(document).ready(function(){
      $("#viewArAdjustHolidayManagent_Serch",navTab.getCurrentPanel()).click(function(){
			$("#viewArAdjustHolidayManagent",navTab.getCurrentPanel()).submit();
	   });
      $('#AdjustHolidayListTable',navTab.getCurrentPanel()).on( 'draw.dt', function () {
			initEditFun_ar0706();
			<c:if test="${LoginUser.cpnyId ne 'HTSV'}">
				init_ar0706();
			</c:if>
		} );
	$(".orderList",navTab.getCurrentPanel()).dataTable({
		"bPaginate": true,    //分页
	    "bAutoWidth":false,//表格宽度自动变化
	    "bProcessing":true,
	    "lengthMenu": [[10,20,35, 50], [10,20, 35, 50]],
		"bLengthChange": true,  //按多少条记录显示下拉框
		"iDisplayLength": 10, //默认每页显示的记录数
		"bFilter": true,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
	 	"searching": true,//本地搜索
		"bSort": true,   //排序功能
		"bInfo": true,   //显示datatables的信息（底部的页数，条目数信息）
		"bScrollInfinite":true,
	     "orderClasses": false,
	     "order":[],//初始化不用自动排序
	     "scrollY": $(document.body).height() - 370,
	     "scrollX": $(document.body).width(),
	     "scrollCollapse": false,
	     "deferRender":true,
	        "columnDefs": [//自定义排序类型
		                     { "orderable": false, "targets":[1] }
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
	$('.orderList tbody tr td:[sysLog="text"]',navTab.getCurrentPanel()).editable({type:'text',
		onblur:function(val,settings){
			var index = $(this).attr("sysIndex");
			 $("#BATCH_ADJUST_"+index,navTab.getCurrentPanel()).attr("checked",true); 
			$(this).html(val);
			$("#modifyFlag_" + index,navTab.getCurrentPanel()).html("modify");
			this.editing = false;
		}
	});
	$('.orderList tbody tr td:[sysLog="date"]',navTab.getCurrentPanel()).editable({type:'date1',
		onblur:function(val,settings){
			var index = $(this).attr("sysIndex");
			 $("#BATCH_ADJUST_"+index,navTab.getCurrentPanel()).attr("checked",true); 
			$("#modifyFlag_" + index,navTab.getCurrentPanel()).html("modify");
			this.editing = false;
		}
	});
	$('.orderList tbody tr td:[sysLog="select"]').editable({type:'select',
		onblur:function(val,settings){
			$(this).html(val);
			var index = $(this).attr("sysIndex");
			var sysFlag = $("#AD_OT_FROM_TIME_"+index,navTab.getCurrentPanel()).attr("sysFlag");
			if(sysFlag=="OT_TIME"){
				ad_callength(index,2);
			}
			 $("#BATCH_ADJUST_"+index,navTab.getCurrentPanel()).attr("checked",true); 
			$("#modifyFlag_" + index,navTab.getCurrentPanel()).html("modify");
			this.editing = false;
		}
	});
	$('.orderList tbody tr td:[sysLog="lookUp"]').editable({type:'lookUp',
		onblur:function(val,settings){
			var index = $(this).attr("sysIndex");
			$(this).html(val);
			submitKeyClick_AD(val,index);
			 $("#BATCH_ADJUST_"+index,navTab.getCurrentPanel()).attr("checked",true); 
			$("#modifyFlag_" + index,navTab.getCurrentPanel()).html("modify");
			this.editing = false;
		}
	});
	<c:if test="${LoginUser.cpnyId ne 'HTSV'}">
		init_ar0706();
	</c:if>
});
function init_ar0706(){
	var ids= document.getElementsByName("BATCH_ADJUST");
	for(var i=0;i<ids.length;i++){
		var index = ids[i].id.substring(13);
		getAffirmor_ar0706(index);
	}
}
function initEditFun_ar0706(){
	$('.orderList tbody tr td:[sysLog="text"]',navTab.getCurrentPanel()).editable({type:'text',
		onblur:function(val,settings){
			var index = $(this).attr("sysIndex");
			 $("#BATCH_ADJUST_"+index,navTab.getCurrentPanel()).attr("checked",true); 
			$(this).html(val);
			$("#modifyFlag_" + index,navTab.getCurrentPanel()).html("modify");
			this.editing = false;
		}
	});
	$('.orderList tbody tr td:[sysLog="date"]',navTab.getCurrentPanel()).editable({type:'date1',
		onblur:function(val,settings){
			var index = $(this).attr("sysIndex");
			 $("#BATCH_ADJUST_"+index,navTab.getCurrentPanel()).attr("checked",true); 
			$("#modifyFlag_" + index,navTab.getCurrentPanel()).html("modify");
			this.editing = false;
		}
	});
	$('.orderList tbody tr td:[sysLog="select"]').editable({type:'select',
		onblur:function(val,settings){
			$(this).html(val);
			var index = $(this).attr("sysIndex");
			var sysFlag = $("#AD_OT_FROM_TIME_"+index,navTab.getCurrentPanel()).attr("sysFlag");
			if(sysFlag=="OT_TIME"){
				ad_callength(index,2);
			}
			 $("#BATCH_ADJUST_"+index,navTab.getCurrentPanel()).attr("checked",true); 
			$("#modifyFlag_" + index,navTab.getCurrentPanel()).html("modify");
			this.editing = false;
		}
	});
	$('.orderList tbody tr td:[sysLog="lookUp"]').editable({type:'lookUp',
		onblur:function(val,settings){
			var index = $(this).attr("sysIndex");
			$(this).html(val);
			submitKeyClick_AD(val,index);
			 $("#BATCH_ADJUST_"+index,navTab.getCurrentPanel()).attr("checked",true); 
			$("#modifyFlag_" + index,navTab.getCurrentPanel()).html("modify");
			this.editing = false;
		}
	});
}
function addAdjustHolidayApply(){
	 $.ajax({
			type:'post',
			url:'/ess/infoApply/addAdjustHolidayApply',
			dataType:null,
			success:function(data){ //请求成功后处理函数。
				navTab.reload('/ar/attendanceMintenance/viewArAdjustHolidayManagent?type=add&firstFlag=N&seach_FROM_DATE=${FROM_DATE}&seach_TO_DATE=${TO_DATE}');
	   	 	}  ,
			error: DWZ.ajaxError
			});
}
function delAdjustHolidayApplyCallback(OP_FLAG,form,callback){
	var $form=null;
	if($('#'+form).length>0){
		$form=$('#'+form);}
	else{
 		$form = $(form);
 	}
	if (!$form.valid()) {
		return false;
	}
    var checked=false;
	var ids= document.getElementsByName("BATCH_ADJUST");
	for(var i=0;i<ids.length;i++){
		if(ids[i].checked){
			checked=true;
		}
	}
	if(!checked){
		alertMsg.error('<spring:message code="alert.message.ess.affirmApply.chooseApplyRecordFirstForBatch"/>'); 
		return false;
	}
    $form.attr("action","/ess/infoApply/delAdjustHolidayApplyForBatch");
    //确定要批量取消吗?
    alertMsg.confirm ("<spring:message code='ar.viewApplyAttenanceManagentInfoList.QUEDINGPILIANGQUXIAOMA.b'/>",{
        okCall:function(){
	    	$.ajax({
				type: form.method || 'POST',
				url:$form.attr("action"),
				data:$form.serializeArray(),
				dataType:"json",
				cache: false,
				success: function(data){ //请求成功后处理函数。
					if(data.statusCode=="200"){
						navTabSearch($("#viewArAdjustHolidayManagent"));
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
function saveAdjustHolidayApply(){
	var jsonData = '[';
	$.each($("input[name='BATCH_ADJUST']"),function(i, obj) {
		if (obj.checked) {
			if (jsonData.length > 1) {
				jsonData += ',{';
			} else {
				jsonData += '{';
			}
			var index = obj.id.substring(13);
			var adjusr = $("#AD_ADJUST_YN_"+index,navTab.getCurrentPanel()).attr("checked");
			if(adjusr=='checked'){
				$("#AD_ADJUST_YN_"+index,navTab.getCurrentPanel()).val("1");
			}else{
				$("#AD_ADJUST_YN_"+index,navTab.getCurrentPanel()).val("0");
			}
			jsonData += ' "APPLY_NO": "' + obj.value + '" ,';
			jsonData += ' "PERSON_ID": "' + $("#delOtAdjustApplyAffirmForm #EMPID_"+index,navTab.getCurrentPanel()).attr('sysPersonId') + '" ,';
			jsonData += ' "APPLY_OT_DATE":"'+ $("#AD_APPLY_OT_DATE_"+index,navTab.getCurrentPanel()).html() +'" ,';
			jsonData += ' "OT_FROM_TIME":"'+ $("#AD_APPLY_OT_DATE_"+index,navTab.getCurrentPanel()).html() +" "+ $("#AD_OT_FROM_TIME_"+index,navTab.getCurrentPanel()).html() +'" ,';
			jsonData += ' "OT_TO_TIME":"'+ $("#AD_APPLY_OT_DATE_"+index,navTab.getCurrentPanel()).html() +" "+ $("#AD_OT_TO_TIME_"+index,navTab.getCurrentPanel()).html() +'" ,';
			jsonData += ' "OT_APPLY_HOUR":"'+ $("#AD_OT_APPLY_HOUR_"+index,navTab.getCurrentPanel()).val() +'" ,';
			jsonData += ' "ADJUST_YN":"'+ $("#AD_ADJUST_YN_"+index,navTab.getCurrentPanel()).val() +'" ,';
			jsonData += ' "OFFSET_YN":"'+ $("#AD_OFFSET_YN_"+index,navTab.getCurrentPanel()).val() +'" ,';
			jsonData += ' "SPECIAL_YN":"'+ $("#AD_SPECIAL_YN_"+index,navTab.getCurrentPanel()).val() +'" ,';
			jsonData += ' "OT_TYPE_CODE":"'+ $("#AD_OT_TYPE_CODE_"+index,navTab.getCurrentPanel()).html() +'" ,';
			jsonData += ' "APPLY_OT_REMARK":"'+ $("#AD_APPLY_OT_REMARK_"+index,navTab.getCurrentPanel()).html() +'"';
			jsonData += '}';
		}
	});
	jsonData += ']';
	var ids= document.getElementsByName("BATCH_ADJUST");
	for(var i=0;i<ids.length;i++){
		if(ids[i].checked){
			checked=true;
			var index = ids[i].id.substring(13);
			var sysPersonId = $("#delOtAdjustApplyAffirmForm #EMPID_"+index,navTab.getCurrentPanel()).attr('sysPersonId');
			if(sysPersonId == null||sysPersonId==''){
				//社号不能为空
				alertMsg.error("<spring:message code='ar.viewarcardrecord.title.empidnotnull' />");
				return false;
			}
			var apply_ot_date = $("#AD_APPLY_OT_DATE_"+index,navTab.getCurrentPanel()).html();
			if(apply_ot_date.substring(0,1)=="<"||apply_ot_date==''){
				//请选择正确日期
				alertMsg.error("<spring:message code='ar.viewArOvertimeManagentFast.QINGXUANZEZHENGQUERIQI.b' />");
				return false;
			}
			if($("#AD_AFFIRM_FLAG_"+index,navTab.getCurrentPanel()).val()!='14014308' && $("#AD_AFFIRM_FLAG_"+index,navTab.getCurrentPanel()).val()!=''){
				//请选择正确选项
				alertMsg.error("<spring:message code='ar.viewArOvertimeManagentFast.QINGXUANZEZHENGQUEXUANXIANG.b' />");
				return false;
			}
			if($("#AD_OT_APPLY_HOUR_"+index,navTab.getCurrentPanel()).val()=='0' || $("#AD_OT_APPLY_HOUR_"+index,navTab.getCurrentPanel()).val()==''){
                //加班时长不能等于0
				alertMsg.error("<spring:message code='ar.viewArOvertimeManagentFast.JIABANSHIJIANBUNENGLING.b' />");
				return false;
			}
			$.ajax({
				type: 'POST',
				url: '/hrm/recruitManage/doSql',
				data:{sql:"select get_post_family('"+sysPersonId+"','${LoginUser.cpnyId}') POST_FAMILY from dual"},
				dataType:"json",
				success: function(data){
					var POST_FAMILY = data.result[0].POST_FAMILY;
					if ((POST_FAMILY=='14015816'||POST_FAMILY=='14015815') || (POST_FAMILY == '14015817' &&('${LoginUser.cpnyId}'=='HTSV'|| '${LoginUser.cpnyId}' == 'SPC_SH')) ||(( POST_FAMILY=='14015814'||POST_FAMILY=='14015818') && '${LoginUser.cpnyId}' == 'SPC_SH')){
                        //该职群不能在此处申请
						alertMsg.error("<spring:message code='ar.viewArOvertimeManagentFast.GAIZHIQUNBUNENGZAICISHENQING.b' />");
						return false;
					}
				},
				error: DWZ.ajaxError
			});
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
	 			url:'/ess/infoApply/saveAdjustHolidayApplyForBatch' ,
	 			data:[{ name: 'jsonData', value: jsonData }],
	 			dataType:"json",
				cache: false,
				success:function(data){ //请求成功后处理函数。
					if(data.statusCode=="200"){
						navTabSearch($("#viewArAdjustHolidayManagent"));
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

function submitKeyClick_AD(obj,index){
	var empid=obj.replace(/[ ]/g," ");
	var personIdStr="applyOtAd";
	if(empid != ''){
	   	$.ajax({
			type: 'POST',
			url: encodeURI('/sys/affirm/getPersonCntByEmpid?EMPID='+empid ),
			dataType:"json",
			cache: false,
			success: function(jsonObject){
				 if(jsonObject.perCnt != 1 ){
					document.getElementById("ad_onckSche").href=encodeURI(encodeURI("/ar/attendanceMintenance/viewAddAffirmEvsList?isEmployeement=1&limit=ar&pageNum=1"
							+'&seach_KEY='+empid+'&personidStr='+personIdStr + '&index=' + index));
					document.getElementById("ad_onckSche").click();
				}
				if(jsonObject.perCnt==1){
					$("#delOtAdjustApplyAffirmForm #EMPID_" + index,navTab.getCurrentPanel()).html(jsonObject.empId);
					$("#delOtAdjustApplyAffirmForm #EMPID_" + index,navTab.getCurrentPanel()).attr('sysPersonId',jsonObject.personId);
					$("#delOtAdjustApplyAffirmForm #DEPT_NAME_" +  index,navTab.getCurrentPanel()).html(jsonObject.deptName);
					$("#delOtAdjustApplyAffirmForm #LOCAL_NAME_" + index,navTab.getCurrentPanel()).html(jsonObject.empName);
					ad_callength(index,1);
					<c:if test="${LoginUser.cpnyId ne 'HTSV'}">
					getAffirmor_ar0706(index);
					</c:if>
				} ;
			},
			error: DWZ.ajaxError
		});
	}else{
		$("#delOtAdjustApplyAffirmForm #EMPID_" + index,navTab.getCurrentPanel()).html("");
		$("#delOtAdjustApplyAffirmForm #DEPT_NAME_" +  index,navTab.getCurrentPanel()).html("");
		$("#delOtAdjustApplyAffirmForm #LOCAL_NAME_" + index,navTab.getCurrentPanel()).html("");
	}
}
function ad_callength(index,FLAG){
	var adjust_yn = 0;
	var sprcial_yn = 0;
	var apply_ot_date = $("#AD_APPLY_OT_DATE_"+index,navTab.getCurrentPanel()).html().replaceAll('-','.');
	var otFromTime = $("#AD_OT_FROM_TIME_"+index,navTab.getCurrentPanel()).html();
	var otToTime = $("#AD_OT_TO_TIME_"+index,navTab.getCurrentPanel()).html();
	var apply_person_id = $("#delOtAdjustApplyAffirmForm #EMPID_"+index,navTab.getCurrentPanel()).attr('sysPersonId');
	var length = " ";
	if($("#AD_ADJUST_YN_"+index,navTab.getCurrentPanel()).attr("checked")=='checked'){
		adjust_yn=1;
	}
	if($("#AD_SPECIAL_YN_"+index,navTab.getCurrentPanel()).attr("checked")=='checked'){
		sprcial_yn=1;
	}
	if(apply_ot_date.substring(0,1)=="<"||apply_ot_date==''){
		//请选择正确日期
		alertMsg.error("<spring:message code='ar.viewArOvertimeManagentFast.QINGXUANZEZHENGQUERIQI.b'/>");
		return false;
	}
	$.ajax({
		 cache: false,
		 type: 'post',
		 async:false,
		 url: '/ess/infoApply/getValidateInfo',
		 data:{"SPTCIAL_YN":sprcial_yn,"ADJUSY_YN":adjust_yn,"CNPY_ID":'${LoginUser.cpnyId}',"PERSON_ID":apply_person_id,"apply_ot_date":apply_ot_date,"otFromTime":otFromTime,"otToTime":otToTime,"apply_person_id":apply_person_id},
		 dataType:"json",
		 success: function(data) {
			 length = data.result[0].OT_LENGTH;
			 var shiftStartTime = data.result[0].SHIFT_START_TIME;
			 var shiftEndTime = data.result[0].SHIFT_END_TIME;
			 var shiftEndTime_2 = data.result[0].SHIFT_END_TIME_2;
			 var otShiftLength = data.result[0].OT_SHIFT_LENGTH;
			 $("#AD_OT_TYPE_CODE_"+index,navTab.getCurrentPanel()).html(data.result[0].OT_TYPE_CODE_NAME);
			 if(data.result[0].DATETYPE == '1440'){
					if(FLAG==1){
						$("#AD_OT_FROM_TIME_"+index,navTab.getCurrentPanel()).html(shiftEndTime);
						$("#AD_OT_TO_TIME_"+index,navTab.getCurrentPanel()).html(shiftEndTime_2);
						$("#AD_OT_APPLY_HOUR_TEXT_"+index,navTab.getCurrentPanel()).html("2小时");
						$("#AD_OT_APPLY_HOUR_"+index,navTab.getCurrentPanel()).val(2);
						$("#AD_SHIFT_START_TIME_"+index,navTab.getCurrentPanel()).html(shiftStartTime);
						$("#AD_SHIFT_END_TIME_"+index,navTab.getCurrentPanel()).html(shiftEndTime);
						$("#AD_INDOOR_TIME_"+index,navTab.getCurrentPanel()).html(data.result[0].INDOOR_TIME);
						$("#AD_OUTDOOR_TIME_"+index,navTab.getCurrentPanel()).html(data.result[0].OUTDOOR_TIME);
					 }else{
						$("#AD_OT_APPLY_HOUR_TEXT_"+index,navTab.getCurrentPanel()).html(length+"小时");
						$("#AD_OT_APPLY_HOUR_"+index,navTab.getCurrentPanel()).val(length);
					 }
				}else if(data.result[0].DATETYPE == '1441'){
					if(FLAG==1){
						$("#AD_OT_FROM_TIME_"+index,navTab.getCurrentPanel()).html(shiftStartTime);
						$("#AD_OT_TO_TIME_"+index,navTab.getCurrentPanel()).html(shiftEndTime);
						$("#AD_OT_APPLY_HOUR_TEXT_"+index,navTab.getCurrentPanel()).html(otShiftLength+"小时");
						$("#AD_OT_APPLY_HOUR_"+index,navTab.getCurrentPanel()).val(otShiftLength);
						$("#AD_SHIFT_START_TIME_"+index,navTab.getCurrentPanel()).html(shiftStartTime);
						$("#AD_SHIFT_END_TIME_"+index,navTab.getCurrentPanel()).html(shiftEndTime);
						$("#AD_INDOOR_TIME_"+index,navTab.getCurrentPanel()).html(data.result[0].INDOOR_TIME);
						$("#AD_OUTDOOR_TIME_"+index,navTab.getCurrentPanel()).html(data.result[0].OUTDOOR_TIME);
					}else{
						$("#AD_OT_APPLY_HOUR_TEXT_"+index,navTab.getCurrentPanel()).html(length+"小时");
						$("#AD_OT_APPLY_HOUR_"+index,navTab.getCurrentPanel()).val(length);
					 }
				}else{
					if(FLAG==1){
						$("#AD_OT_FROM_TIME_"+index,navTab.getCurrentPanel()).html(shiftStartTime);
						$("#AD_OT_TO_TIME_"+index,navTab.getCurrentPanel()).html(shiftEndTime);
						$("#AD_OT_APPLY_HOUR_TEXT_"+index,navTab.getCurrentPanel()).html(otShiftLength+"小时");
						$("#AD_OT_APPLY_HOUR_"+index,navTab.getCurrentPanel()).val(otShiftLength);
						$("#AD_SHIFT_START_TIME_"+index,navTab.getCurrentPanel()).html(shiftStartTime);
						$("#AD_SHIFT_END_TIME_"+index,navTab.getCurrentPanel()).html(shiftEndTime);
						$("#AD_INDOOR_TIME_"+index,navTab.getCurrentPanel()).html(data.result[0].INDOOR_TIME);
						$("#AD_OUTDOOR_TIME_"+index,navTab.getCurrentPanel()).html(data.result[0].OUTDOOR_TIME);
					}else{
						$("#AD_OT_APPLY_HOUR_TEXT_"+index,navTab.getCurrentPanel()).html(length+"小时");
						$("#AD_OT_APPLY_HOUR_"+index,navTab.getCurrentPanel()).val(length);
					 }
				}
		 },
		 error:DWZ.ajaxError
	});
}
function fillItemAdjust(){
	$.ajaxSettings.global = false;
	var applyOtRemark = $("#AD_APPLY_OT_REMARK",navTab.getCurrentPanel()).val();
	var fromDate = $("#AD_OT_FROM_DATE",navTab.getCurrentPanel()).val();
	var fromTime = $("#AD_OT_FROM_TIME",navTab.getCurrentPanel()).val();
	var toTime = $("#AD_OT_TO_TIME",navTab.getCurrentPanel()).val();
	var ids= document.getElementsByName("BATCH_ADJUST");
	var checked=false;
	var length = null;
	var lengthText = " ";
	var otTypeCodeName = " ";
	var SHIFT_START_TIME = "";
	var SHIFT_END_TIME = "";
	if(fromDate.substring(0,1)=="<"||fromDate==''){
		//请选择正确日期
		alertMsg.error("<spring:message code='ar.viewArOvertimeManagentFast.QINGXUANZEZHENGQUERIQI.b'/>");
		return false;
	}
	for(var i=0;i<ids.length;i++){
		if(ids[i].checked){
			checked=true;
			var index = ids[i].id.substring(13);
			var adjust_yn = 0;
			var sprcial_yn = 0;
			var apply_person_id = $("#EMPID_"+index,navTab.getCurrentPanel()).attr('sysPersonId');
			if($("#AD_ADJUST_YN_"+index,navTab.getCurrentPanel()).attr("checked")=='checked'){
				adjust_yn=1;
			}
			if($("#AD_SPECIAL_YN_"+index,navTab.getCurrentPanel()).attr("checked")=='checked'){
				sprcial_yn=1;
			}
			$.ajax({
				type: 'POST',
				url: '/ess/infoApply/getValidateInfo',
				async:false,
				data:{"SPTCIAL_YN":sprcial_yn,"ADJUSY_YN":adjust_yn,"CNPY_ID":'${LoginUser.cpnyId}',"PERSON_ID":apply_person_id,"apply_ot_date":fromDate,"otFromTime":fromTime,"otToTime":toTime,"apply_person_id":apply_person_id},
				dataType:"json",
				success: function(data){
					length = data.result[0].OT_LENGTH;
					lengthText = data.result[0].OT_LENGTH + "小时";
					otTypeCodeName = data.result[0].OT_TYPE_CODE_NAME;
					SHIFT_START_TIME = data.result[0].SHIFT_START_TIME;
					SHIFT_END_TIME = data.result[0].SHIFT_END_TIME;
				},
				error: DWZ.ajaxError
			}); 
			$("#AD_APPLY_OT_DATE_"+index,navTab.getCurrentPanel()).html(fromDate);
			$("#AD_OT_FROM_TIME_"+index,navTab.getCurrentPanel()).html(fromTime);
			$("#AD_OT_TO_TIME_"+index,navTab.getCurrentPanel()).html(toTime);
			$("#AD_APPLY_OT_REMARK_"+index,navTab.getCurrentPanel()).html(applyOtRemark);
			$("#AD_OT_APPLY_HOUR_TEXT_"+index,navTab.getCurrentPanel()).html(lengthText);
			$("#AD_OT_APPLY_HOUR_"+index,navTab.getCurrentPanel()).val(length);
			$("#AD_OT_TYPE_CODE_"+index,navTab.getCurrentPanel()).html(otTypeCodeName);
			$("#AD_SHIFT_START_TIME_"+index,navTab.getCurrentPanel()).html(SHIFT_START_TIME);
			$("#AD_SHIFT_END_TIME_"+index,navTab.getCurrentPanel()).html(SHIFT_END_TIME);
			$("#AD_ADJUST_YN_"+index,navTab.getCurrentPanel()).attr("checked",true);
			if(fromTime>toTime){
				$("#OFFSET_YN_"+index,navTab.getCurrentPanel()).attr("checked",true);
			}else{
				$("#OFFSET_YN_"+index,navTab.getCurrentPanel()).attr("checked",false);
			}
		}
	}
	if(!checked){
		//请选择要修改的内容
		alertMsg.error("<spring:message code='ar.viewApplyAttenanceManagentInfoList.QINGXUANZEXIUGAINEIRONG.b'/>"); 
		return false;
	}
	$.ajaxSettings.global = true;
}
function getDefaultAdjust(){
	$.ajaxSettings.global = false;
	var OT_FROM_DATE = $("#AD_OT_FROM_DATE",navTab.getCurrentPanel()).val();
	if(OT_FROM_DATE.substring(0,1)=="<"||OT_FROM_DATE==''){
		//请选择正确日期
		alertMsg.error("<spring:message code='ar.viewArOvertimeManagentFast.QINGXUANZEZHENGQUERIQI.b'/>");
		return false;
	}
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
				$("#AD_OT_TYPE_CODE",navTab.getCurrentPanel()).val(14015836);
				$("#AD_OT_FROM_TIME",navTab.getCurrentPanel()).val(shiftEndTime);
				$("#AD_OT_TO_TIME",navTab.getCurrentPanel()).val(shiftEndTime_2);
			}else if(data.result[0].DATETYPE == '1441'){
				$("#AD_OT_TYPE_CODE",navTab.getCurrentPanel()).val(14015837);
				$("#AD_OT_FROM_TIME",navTab.getCurrentPanel()).val(shiftStartTime);
				$("#AD_OT_TO_TIME",navTab.getCurrentPanel()).val(shiftEndTime);
			}else{
				$("#AD_OT_TYPE_CODE",navTab.getCurrentPanel()).val(278597);
				$("#AD_OT_FROM_TIME",navTab.getCurrentPanel()).val(shiftStartTime);
				$("#AD_OT_TO_TIME",navTab.getCurrentPanel()).val(shiftEndTime);
			}
		},
		error: DWZ.ajaxError
	});
	$.ajaxSettings.global = true;
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
	        	   $("#seach_KEY",navTab.getCurrentPanel())[0].focus(); 
		           return false; 
		       }else {
			        return true;
			   }   
	      }  
	 }  
};
function ad_updateChecked(index){
	$("#BATCH_ADJUST_"+index).attr('checked',true);
}
function getAffirmor_ar0706(index){
	var htm = '';
	var personId = $("#EMPID_"+index,navTab.getCurrentPanel()).attr('sysPersonId');
	$.ajax({
		type: 'POST',
		url: '/ess/infoApplyAttendance/viewAffirmorByPersonIdList',
		data:[{ name: 'applyTypeCode', value: '32' },/* 审批线相同，无所谓 */
		      { name: 'applyLength', value: '2' },
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
				$("#AD_AFFIRMOR_"+index,navTab.getCurrentPanel()).html(htm);
			}else{
				$("#AD_AFFIRMOR_"+index,navTab.getCurrentPanel()).html('');
			}
		},
		error: DWZ.ajaxError
	});
}
</script>
<div class="panel"><h1><!-- 倒休管理 --> <spring:message code="ar.viewArAdjustHolidayManagent.DAOXIUGUANLI.b" /></h1></div>
<div   id="viewApplyAjBatch"  class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/ar/attendanceMintenance/viewArAdjustHolidayManagent?firstFlag=N"  method="post"
		id="viewArAdjustHolidayManagent" name="viewArAdjustHolidayManagent">
		<div class="searchBar">
				<table class="searchContent">
				<tr>
					<td><!-- 社号/姓名 --><spring:message code="hrm.empinfo.nameAndEmpid"/></td>
					<td>
					  <input type="text" name="seach_KEY" id="seach_KEY" value="${KEY}"/>
					</td>
					<td><!-- 部门： --> <spring:message code="hr.viewPersonalInfo.title.DEPTNAME" /></td>
					<td>
						<ait:deptList name="seach_DEPTNO" cpnyId="${defaultCpny}" limit="ar" id="viewArAdjustHolidayManagent_seachDept" selected="${DEPTNO}"/>
						<ait:deptTreeIcon name="seach_DEPTNO" cpnyId="${defaultCpny}" limit="ar" id="viewArAdjustHolidayManagent_seachDept" selected="${DEPTNO}"/>
					</td>
					<td><spring:message code="ess.workgroup.title.duration" text="期间"/></td>
					<td>
					  <input type="text" name="seach_FROM_DATE" id="seach_FROM_DATE"  class="Wdate" onClick="WdatePicker({dateFmt:'yyyy.MM.dd'})" value="${FROM_DATE }"/>
				      ~
					  <input type="text" name="seach_TO_DATE" id="seach_TO_DATE"  class="Wdate" onClick="WdatePicker({dateFmt:'yyyy.MM.dd'})" value="${TO_DATE }"/>
					</td>
								
				<!-- </tr>
				<tr> -->
					<%-- <td>班组&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<ait:SelectSyCodeByCpnyID id="seach_GROUP_ID" name="seach_GROUP_ID" parentNo="400223" selected="${GROUP_ID}" cnpyID="${LoginUser.cpnyId}" limit="all"/> 
					</td> --%>
					<%-- <td>审批状态 &nbsp;&nbsp;&nbsp;&nbsp;
						  <ait:selectCodeMulti id="seach_AFFIRM_FLAG" name="seach_AFFIRM_FLAG_NAME" parentNo="14014304" selected="${AFFIRM_FLAG}"  selectedNm="${AFFIRM_FLAG_NAME}"/>
					</td> --%>					
				</tr>
				<tr>
				    <td><!-- 员工类型 --><spring:message code="org.title.EMP_TYPE"/></td>
				 	<td>
				 	   <ait:SelectSyCodeByCpnyID id="seach_EMP_TYPE_CODE" name="seach_EMP_TYPE_CODE" parentNo="13864" selected="${EMP_TYPE_CODE}" cnpyID="${LoginUser.cpnyId}" limit="all"/>
					</td>
					<td><!-- 审批状态 --><spring:message code="ess.infoApply.approval_status"/></td>
					<td>
						<ait:SelectSyCodeByCpnyID name="seach_AFFIRM_FLAG" parentNo="14014304" selected="${AFFIRM_FLAG}" limit="ALL"/>
					</td>
					<td><!-- 人事确认状态  --><spring:message code="ess.title.RENSHIQUERENZHUANGTAI"/></td>
					<td>
					<select name="seach_CONFIRM_FLAG">
						<option value=""<c:if test="${CONFIRM_FLAG == null}">selected</c:if>><!-- 请选择  --><spring:message code="org.title.PLEASE_SELECT"/></option>
						<option value="0"<c:if test="${CONFIRM_FLAG == '0'}">selected</c:if>><!-- 未确认  --><spring:message code="ess.title.WEIQUEREN"/> </option>
						<option value="1"<c:if test="${CONFIRM_FLAG == '1'}">selected</c:if>><!-- 通过  --><spring:message code="ess.infoApply.adopt"/></option>
						<option value="2"<c:if test="${CONFIRM_FLAG == '2'}">selected</c:if>><!-- 否决  --><spring:message code="ess.infoApply.veto"/></option> 
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
			    	<td><!-- 加班状态  --><spring:message code="ess.title.JIABANZHUANGTAI"/></td>
				   <td>
						<ait:SelectSyCodeByCpnyID name="AD_OT_TYPE_CODE" parentNo="31" disabled="true" selected="14015836"/>	
				   </td>
				   <td><!-- 加班日期  --><spring:message code="ess.infoApply.title.overtimeTime"/> </td>
				   <td>
				       <input type="text" name="AD_OT_FROM_DATE" id="AD_OT_FROM_DATE"  class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',onpicked:getDefaultAdjust})" value=""/>
				   </td>
				   <td><!-- 开始时间  --><spring:message code="ess.infoApply.title.startTime"/></td>
				   <td>
						<ait:time name="AD_OT_FROM_TIME"  spacing="30"   selected=""/>
					</td>
					<td><!-- 结束时间  --><spring:message code="ess.infoApply.title.endTime"/></td>
					<td>
						<ait:time name="AD_OT_TO_TIME"  spacing="30"  selected=""/>
					</td>
					<td><!-- 原因  --><spring:message code="ess.infoApply.Reason"/></td>
					<td>
						<input type="text" id="AD_APPLY_OT_REMARK" name="APPLY_OT_REMARK"  value=""/>
					</td>
			</tr>
		</table>
		<div class="subBar"><ul class="toolBar"><li><a class="buttonActive" onclick="fillItemAdjust();"><span><!-- 全部反应 --><spring:message code="hrm.approve.ALL_REACTION"/></span></a> </li></ul></div>
	</div>
</div>
<div class="pageContent" >
<div class="formBar">
	<ul class="toolBar">
	    <li><a class="buttonActive" id="viewArAdjustHolidayManagent_Serch" href="#"><span><!-- 查询 --><spring:message code="hrm.empinfo.QUERY"/></span></a></li>
	    <li><a class="buttonActive" onclick="addAdjustHolidayApply()"><span><!-- 添加 --><spring:message code="ess.empInfo.insert"/></span></a></li>
		<li><a class="buttonActive" onclick="delAdjustHolidayApplyCallback(0,'delOtAdjustApplyAffirmForm',DWZ.ajaxDone)"><span><!-- 删除 --><spring:message code="ess.empInfo.Delete"/></span></a></li>
		<li><a class="buttonActive" onclick="saveAdjustHolidayApply()"><span><!-- 保存 --><spring:message code="org.title.SAVE"/></span></a></li>
		<li><a class="buttonActive" onclick="downloadExcel('viewArAdjustHolidayManagent','/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=184&CPNY=${LoginUser.cpnyId}','/ar/attendanceMintenance/viewArAdjustHolidayManagent?firstFlag=N')"><span><!-- 导出到Excel --><spring:message code="org.title.exportLOtImportExcel"/></span></a></li>
	</ul>
</div>
	<form name="delOtAdjustApplyAffirmForm" id="delOtAdjustApplyAffirmForm" method="post" action="/ar/attendanceMintenance/delOtAdjustApplyAffirmForm" 
	  onsubmit="return delAdjustHolidayApplyCallback(this, navTabAjaxDone);">
	  <input type="hidden" id="AdjustHolidayListCnt" value="Total:${fn:length(AdjustHolidayList)}">
	  <%-- <div class="user_table" style="font:bold 12px/20px arial,sans-serif;">Total:${fn:length(AdjustHolidayList)}</div> --%>                    
		<table class="orderList" width="1980px" id="AdjustHolidayListTable">
			<thead>
			    <tr>
				    <th  rowspan="2"><!--NO-->
						NO
					</th>
					<th rowspan="2">
				    	<input type="checkbox" class="checkboxCtrl" group="BATCH_ADJUST" />
				    </th>
					<th rowspan="2" width="60px"><!--申请人-->
						<!-- 姓名 --><spring:message code="org.title.LOCAL_NAME"/>
					</th >
				    <th rowspan="2" width="60px" class="titleColor"><!--社号-->
						<!-- 社号 --><spring:message code="ess.infoApply.EMPID"/>
					</th>
					<th  rowspan="2" width="120px">
						<!-- 部门 --><spring:message code="ess.infoApply.DEPT"/>
					</th>
					<th rowspan="2" width="80px"  class="titleColor"><!--日期-->
						<!-- 加班日期 --><spring:message code="ess.infoApply.title.overtimeTime"/>
					</th>
					<!-- <th rowspan="2" >类型
						类型
					</th> -->
					<th colspan="11" ><!--申请-->
						<!-- 申请 --><spring:message code="ess.infoApply.title.apply"/>
					</th>
					<th rowspan="2" width="240px" class="titleColor"><!--原因-->
						<!-- 原因 --><spring:message code="ess.infoApply.Reason"/>
					</th>
					<c:if test="${LoginUser.cpnyId ne 'HTSV'}">
					    <th rowspan="2" width="160px">
							<!-- 审批者--><spring:message code="hrm.contractInfo.APPROVAL_PERSON"/>
						</th>
					    </c:if>
					<th  rowspan="2"  width="105px"><!--审批状态-->
						<!-- 审批状态--><spring:message code="ess.affirmApply.title.remark.shenpizhuangtai"/>
					</th>
					<th colspan="4"><!--加班累计-->
						<!-- 加班累计--><spring:message code="ess.title.JIABANLEIJI"/>
					</th>
					<th rowspan="2" width="100px"><!--输入者-->
						<!-- 输入者--><spring:message code="ar.viewArAdjustHolidayManagent.SHURUZHE.b"/>
					</th>
					<th rowspan="2" width="80px"><!--输入时间-->
						<!-- 输入时间--><spring:message code="ar.viewArAdjustHolidayManagent.SHURUSHIJIAN.b"/>
					</th>
					<th rowspan="2" width="100px"><!--最终修改人-->
						<!-- 修改者--><spring:message code="ar.viewApplyAttenanceManagentInfoList.XIUGAIZHE.b"/>
					</th>
					<th rowspan="2" width="80px"><!--修改时间-->
						<!-- 修改时间--><spring:message code="empsubject.updtTime"/>
					</th>
				</tr>
				<tr>
					<th  width="60px"><!--进门-->
						<!-- 上班时间--><spring:message code="ess.infoApply.work_time"/>
					</th>
					<th  width="60px"><!--出门-->
						<!-- 下班时间--><spring:message code="ess.infoApply.out_work_time"/>
					</th>
					<th  width="60px"><!--进门-->
						<!-- 进门时间--><spring:message code="ess.infoApply.in_door_time"/>
					</th>
					<th  width="60px"><!--出门-->
						<!-- 出门时间--><spring:message code="ess.infoApply.out_door_time"/>
					</th>
					<th  class="titleColor"  width="60px"><!--开始-->
						<!-- 开始时间--><spring:message code="ess.infoApply.title.startTime"/>
					</th>
					<th class="titleColor"  width="60px"><!--结束-->
						<!-- 结束时间--><spring:message code="ess.infoApply.title.endTime"/>
					</th>
					<th  width="60px"><!-- 加班时间-->
					    <!-- 加班时长--><spring:message code="ess.infoApply.overtime_hours"/>
					</th>
					<th class="titleColor"  width="30px"><!-- 是否调休-->
					  	<!-- 跨天--><spring:message code="ess.title.KUATIAN"/>
					</th>
					<th class="titleColor"  width="30px"><!-- 是否调休-->
					    <!-- 调休--><spring:message code="ar.viewArNavigationPage.TIAOXIU.b"/>
					</th>
					<th class="titleColor"  width="30px"><!-- 是否调休-->
					  	<!-- 特殊--><spring:message code="ess.title.TESHU"/>
					</th>
					<th width="150px">
					    <!-- 加班状态--><spring:message code="ess.title.JIABANZHUANGTAI"/>
					</th>
					<th  width="60px">
						<!-- 加班合计--><spring:message code="ess.infoApply.jiabnaheji"/>
					</th>
					<th  width="30px">
						<!-- 平时--><spring:message code="ar.viewArOvertimeManagentFast.PINGSHI.b"/>
					</th>
					<th  width="30px">
						<!-- 周末--><spring:message code="ar.viewitemparameter.title.zhoumo"/>
					</th>
					<th  width="45px">
						<!-- 节假日--><spring:message code="ar.viewitemparameter.title.jiejiari"/>
					</th>
				</tr>
			</thead>
				<c:forEach items="${AdjustHolidayList}" var="otAdjustApply" varStatus="i">	
					<tr target="sid" rel="${admin.personId}">
					    <td  style="text-align: center">${i.count}</td>
					    <td  style="text-align: center">
					    <c:if test="${otAdjustApply.AFFIRM_FLAG ne '14014309' and otAdjustApply.AFFIRM_FLAG ne '14014310'}">
					        <input type="checkbox" id="BATCH_ADJUST_${i.index}" name="BATCH_ADJUST" value="${otAdjustApply.APPLY_NO}" />
					        </c:if>
					    </td>
					    <td style="text-align: center" id="LOCAL_NAME_${i.index}" sysIndex="${i.index}">${otAdjustApply.LOCAL_NAME}</td>
					    <td style="text-align: center" id="EMPID_${i.index}" <c:if test='${otAdjustApply.PERSON_ID eq null}'>sysLog="lookUp"</c:if> sysIndex="${i.index}" sysPersonId="${otAdjustApply.PERSON_ID}" >${otAdjustApply.EMPID}</td>
					    <td style="text-align: center" id ="DEPT_NAME_${i.index}" sysIndex="${i.index}">${otAdjustApply.DEPTNAME}</td>
					    <td style="text-align: center;" sysLog="date" sysIndex="${i.index}" format="yyyy-MM-dd" id="AD_APPLY_OT_DATE_${i.index}" sysFlag="2">${otAdjustApply.APPLY_OT_DATE}</td>
					    <td  style="text-align: center">
					      <span id="AD_SHIFT_START_TIME_${i.index}">${otAdjustApply.SHIFT_START_TIME}</span> 
					    </td>
					    <td  style="text-align: center">
					       <span id="AD_SHIFT_END_TIME_${i.index}">${otAdjustApply.SHIFT_END_TIME}</span>
					    </td>
					    <td  style="text-align: center">
					      <span id="AD_INDOOR_TIME_${i.index}">${otAdjustApply.INDOOR_TIME}</span>
					    </td>
					    <td  style="text-align: center">
					      <span id="AD_OUTDOOR_TIME_${i.index}">${otAdjustApply.OUTDOOR_TIME}</span>
					    </td>
					   	<td style="text-align: center;" sysLog="select" sysIndex="${i.index}" id="AD_OT_FROM_TIME_${i.index}" sysValue="${TIME_STR}" sysFlag="OT_TIME">${otAdjustApply.OT_FROM_TIME}</td>
					    <td style="text-align: center;" sysLog="select" sysIndex="${i.index}" id="AD_OT_TO_TIME_${i.index}" sysValue="${TIME_STR}" sysFlag="OT_TIME">${otAdjustApply.OT_TO_TIME}</td>
					    <td  style="text-align: center" id="AD_OT_APPLY_HOUR_TEXT_${i.index}">
					   		 ${otAdjustApply.OT_APPLY_HOUR}<!-- 小时--><spring:message code="ar.viewsummaryparameteritem.title.hour"/>
					    </td>
					     <input type="hidden" id="AD_OT_APPLY_HOUR_${i.index}" value="${otAdjustApply.OT_APPLY_HOUR}">
					     <td  style="text-align: center">
					       <input type="checkbox" id="AD_OFFSET_YN_${i.index}" onclick="ad_updateChecked(${i.index})" <c:if test="${otAdjustApply.OFFSET_YN eq 1}"> checked="checked"</c:if> value="${otAdjustApply.OFFSET_YN}">
					    </td>
					    <td  style="text-align: center">
					       <input type="checkbox" id="AD_ADJUST_YN_${i.index}"  onclick="ad_callength(${i.index},2)"  <c:if test="${otAdjustApply.ADJUST_YN eq 1}">checked="checked"</c:if> value="${otAdjustApply.ADJUST_YN}">
					    </td>
					   <td  style="text-align: center">
					       <input type="checkbox" id="AD_SPECIAL_YN_${i.index}"  onclick="ad_callength(${i.index},2)"  <c:if test="${otApply.SPECIAL_YN eq 1}">checked="checked"</c:if> value="${otAdjustApply.SPECIAL_YN}">
					    </td>
					    <td style="text-align: center;" id="AD_OT_TYPE_CODE_${i.index}">${otAdjustApply.OT_TYPE_CODE_NAME}</td>
					    <td sysLog="text" sysIndex="${i.index}" id="AD_APPLY_OT_REMARK_${i.index}">${otAdjustApply.APPLY_OT_REMARK}</td> 
					   <c:if test="${LoginUser.cpnyId ne 'HTSV'}">
					    	<td  style="text-align: center" id="AD_AFFIRMOR_${i.index}"></td>
					    </c:if>
					    <td style="text-align: center">
					  	  ${otAdjustApply.AFFIRM_FLAG_NAME}&nbsp;<c:if test="${otAdjustApply.CONFIRM_FLAG ne '1' and otAdjustApply.CONFIRM_FLAG ne '2' }"><!-- 人事未确认--><spring:message code="ess.title.WEIQUEREN"/></c:if><c:if test="${otAdjustApply.CONFIRM_FLAG eq '1' }"><!-- 人事通过--><spring:message code="ess.title.RENSHITONGGUO"/></c:if><c:if test="${otAdjustApply.CONFIRM_FLAG eq '2' }"><!-- 人事否决--><spring:message code="ess.title.RENSHIFOUJUE"/></c:if>
					  	</td>
					    <td  style="text-align: center">
					      ${otAdjustApply.OT_TOTAIL}
					    </td>
					    <td  style="text-align: center">
					      ${otAdjustApply.WEEKDAY_OT_TOTAIL}
					    </td>
					    <td  style="text-align: center">
					      ${otAdjustApply.WEEKEND_OT_TOTAIL}
					    </td>
					    <td  style="text-align: center">
					      ${otAdjustApply.HOILDAY_OT_TOTAIL}
					    </td>
					    <td  style="text-align: center">
					      ${otAdjustApply.CREATED_BY}
					    </td>
					    <td  style="text-align: center">
					      ${otAdjustApply.CREATE_DATE}
					    </td>
					    <td  style="text-align: center">
					      ${otAdjustApply.UPDATED_BY}
					    </td>
					    <td  style="text-align: center">
					      ${otAdjustApply.UPDATE_DATE}
					    </td>
					    <input type="hidden" id="AD_AFFIRM_FLAG_${i.index}" value="${otAdjustApply.AFFIRM_FLAG}">
					   <%--  <input type="hidden" id="AD_OT_TYPE_CODE_${i.index}" value="${otAdjustApply.OT_TYPE_CODE_NAME}"/> --%>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<input type="hidden" id="BATCH_ADJUST_OP_FLAG" name="OP_FLAG" value="0" />
	</form>
	<a id="ad_onckSche" name="onckSche"  href="" lookupGroup="person"></a>
</div>