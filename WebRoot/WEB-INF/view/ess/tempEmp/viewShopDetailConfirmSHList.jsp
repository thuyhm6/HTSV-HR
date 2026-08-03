<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
$(document).ready(function(){
	
	$("[sysLog='activity']",navTab.getCurrentPanel()).each(function(i, obj){
		var index = $(obj).attr("sysIndex");
	 	var sysActivity = $(obj).attr("sysActivity");
		if(sysActivity != '0' && sysActivity != '1'){
			$("td[sysIndex='" + index + "']",navTab.getCurrentPanel()).attr("sysLog","");
		}
		
	});
	//查询
	$("#viewShopDetailConfirmSHList_Serch",navTab.getCurrentPanel()).click(function(){
		$("#viewShopDetailConfirmSHListForm",navTab.getCurrentPanel()).submit();
	});
	//保存
	$("#viewShopDetailConfirmSHList_Save",navTab.getCurrentPanel()).click(function(){	
		//获取页面的值
		var jsonData = '[';
		$("input:[name='BATCH_SHOP_CONFIRM']",navTab.getCurrentPanel()).each(function(i, obj){
			if(obj.checked){
				if (jsonData.length > 1) {
					jsonData += ',{';
				} else {
					jsonData += '{';
				}
				var index = $(obj).val();
				jsonData += ' "SHIFT_FROM_TIME": "' + $("#CONFIRM_FROM_TIME_" + index,navTab.getCurrentPanel()).html() + '" ,';
				jsonData += ' "SHIFT_TO_TIME": "' + $("#CONFIRM_TO_TIME_" + index,navTab.getCurrentPanel()).html() + '" ,';
				jsonData += ' "MINUS_LENGTH": "' + $("#MINUS_LENGTH_" + index,navTab.getCurrentPanel()).html() + '" ,';
				jsonData += ' "WORKING_HOUR": "' + $("#WORKING_HOUR_" + index,navTab.getCurrentPanel()).html() + '" ,';
				jsonData += ' "REMARK": "' + $("#REMARK_" + index,navTab.getCurrentPanel()).html() + '" ,';
				jsonData += ' "PERSON_ID": "' + $(obj).attr("sysPersonId") + '" ,';
				jsonData += ' "AR_DATE_STR": "' + $(obj).attr("sysArDateStr") + '" ,';

				jsonData += ' "ITEM_NO": "' + $("#ITEM_NO_0_" + index,navTab.getCurrentPanel()).attr("sysItem1") + '" ,';
				jsonData += ' "ITEM_LEN_1": "' + $("#ITEM_NO_0_" + index,navTab.getCurrentPanel()).attr("sysLen1") + '" ,';
				jsonData += ' "ITEM_NO_2": "' + $("#ITEM_NO_0_" + index,navTab.getCurrentPanel()).attr("sysItem2") + '" ,';
				jsonData += ' "ITEM_LEN_2": "' + $("#ITEM_NO_0_" + index,navTab.getCurrentPanel()).attr("sysLen2") + '" ,';
				
				jsonData += ' "adminID": "' + '${LoginUser.adminID}' + '" ,';
				jsonData += ' "adminIP": "' + '${LoginUser.adminIP}' + '" ,';
				jsonData += ' "interCpnyID": "' + '${LoginUser.cpnyId}' + '" ';
				
				jsonData += '}';
			}
		});
		jsonData += ']';
		if (jsonData.length == 2) {
			//没有需要申请的数据
			alertMsg.info("<spring:message code='ess.infoApply.NO_NEED_TO_APPLY_DATA' />");
			return;
		}
		//确定要申请吗
		alertMsg.confirm("<spring:message code='ess.message.confirm_apply' />",
	  		  	{okCall:function(){
			  	$.ajax({
	  				type: 'POST',
					url: '/ess/tempEmp/addShopShiftConfirmSH',
					data: [{ name: 'jsonData', value: jsonData }],
	  				dataType:"json",
	  				cache: false,
	  				success: navTabAjaxDoneWithForm,
	  				error: DWZ.ajaxError
	  			});
	  	}});
	});
	
	$(".orderList",navTab.getCurrentPanel()).dataTable({
		"bPaginate": true,    //分页
	    "bAutoWidth":false,//表格宽度自动变化
	    "bProcessing":true,
	    "lengthMenu": [[15,20,35, 50], [15,20, 35, 50]],
		"bLengthChange": true,  //按多少条记录显示下拉框
		"iDisplayLength": 20, //默认每页显示的记录数
		"bFilter": true,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
     	"searching": true,//本地搜索
		"bSort": true,   //排序功能
		"bInfo": true,   //显示datatables的信息（底部的页数，条目数信息）
		"bScrollInfinite":true,
	     "orderClasses": false,
	     "order":[],//初始化不用自动排序
	     "scrollY": $(document.body).height() - 280,
	     "scrollX": $(document.body).width(),
	     "scrollCollapse": false,
	     "deferRender":true,
	        "columnDefs": [//自定义排序类型
		                     { "orderable": false, "targets": [5] }
	                     ],
	    "fixedColumns":{leftColumns: 6},
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
	
	$('.orderList',navTab.getCurrentPanel()).on( 'draw.dt', function () {
		initEditFun_ess3436();
	});
	initEditFun_ess3436();
});

function initEditFun_ess3436(){

	$('.orderList tbody tr td:[sysLog="text"]',navTab.getCurrentPanel()).editable({type:'text',
		onblur:function(val,settings){
			var sysType = $(this).attr("sysType");
			var index = $(this).attr("sysIndex");
			if(sysType == 'REMARK'){
				$(this).html(val);
			}else{
				var minusFlag = $(this).attr("sysFamily");
				var sysDutyNo = $(this).attr("sysDutyNo");
				var workHour = 8;
				var lunchHour = 1;
				if (minusFlag == '14015817'||sysDutyNo=='80000008'||sysDutyNo=='14015672'){
					workHour = 8.5;
					lunchHour = 0.5;
				}else if(minusFlag == '14015814'){
					workHour = 9;
					lunchHour = 1;
				}
				if(sysType != 'MINUS_LENGTH' && sysType != 'WORKING_HOUR'){
					val = composeTime(val);
					if(val == ":"){
			    		$(this).html("");
						this.editing = false;
						return false;
					}
				}
				$(this).html(val);
				var workingHour = 0;
				var minusLen = 0;
				if(sysType == 'CONFIRM_FROM_TIME'){
					if(val != ''){
						if($("#CONFIRM_TO_TIME_" + index,navTab.getCurrentPanel()).html() == ''){
							$("#CONFIRM_TO_TIME_" + index,navTab.getCurrentPanel()).html( addTime(val, workHour) );
							minusLen = 0;
							workingHours = workHour - lunchHour;
						}else{
							var shiftToTime = $("#CONFIRM_TO_TIME_" + index,navTab.getCurrentPanel()).html();
							minusLen = addTime2(shiftToTime,val) - workHour;
							workingHours = addTime2(shiftToTime, val) - lunchHour;
						}
					}
				}else if(sysType == 'CONFIRM_TO_TIME'){
					if(val != ''){
						if($("#CONFIRM_FROM_TIME_" + index,navTab.getCurrentPanel()).html() == ''){
							$("#CONFIRM_FROM_TIME_" + index,navTab.getCurrentPanel()).html( addTime3(val, workHour) );
							minusLen = 0;
							workingHours = workHour - lunchHour;
						}else{
							var shiftFromTime = $("#CONFIRM_FROM_TIME_" + index,navTab.getCurrentPanel()).html();
							minusLen = addTime2(val,shiftFromTime) - workHour;
							workingHours = addTime2(val, shiftFromTime) - lunchHour;
						}
					}
				}else if(sysType == 'MINUS_LENGTH'){
					minusLen = parseFloat(val);
				}else if(sysType == 'WORKING_HOUR'){
					minusLen = parseFloat(val) - workHour + lunchHour;
					workingHours = parseFloat(val);
				}
				if(val != ''){
					if(sysType != 'MINUS_LENGTH'){
						$("#MINUS_LENGTH_" + index,navTab.getCurrentPanel()).html(minusLen);
						$("#WORKING_HOUR_" + index,navTab.getCurrentPanel()).html(workingHours);
						if(minusLen >= 0){                                       //正常出勤
							$("#ITEM_NO_" + index,navTab.getCurrentPanel()).html( "<spring:message code='ess.infoApply.normal_attendance' />" );
						}else{                                                   //早退
							$("#ITEM_NO_" + index,navTab.getCurrentPanel()).html( "<spring:message code='ess.infoApply.leave_early' />" );
						}
					}
				}
			}
			$("#BATCH_SHOP_CONFIRM_" + index,navTab.getCurrentPanel()).attr("checked","checked");
			this.editing = false;
		}
	});

	$('.orderList tbody tr td:[sysLog="select"]',navTab.getCurrentPanel()).editable({type:'select',
		onblur:function(val,settings){
			$(this).html(val);
			
			var index = $(this).attr("sysIndex");

			$("#ITEM_NO_0_" + index,navTab.getCurrentPanel()).attr("sysItem1",val);
			$("#ITEM_NO_0_" + index,navTab.getCurrentPanel()).attr("sysItem2","");
			$("#ITEM_NO_0_" + index,navTab.getCurrentPanel()).attr("sysLen1","");
			$("#ITEM_NO_0_" + index,navTab.getCurrentPanel()).attr("sysLen2","");
			           //多状态
			if(val == '多状态'){
				$(this).html("");
				$("#ITEM_NO_0_" + index,navTab.getCurrentPanel()).attr("sysItem1","");
				$.pdialog.open(encodeURI("/ess/tempEmp/viewSHItemList?seach_index=" + index + "&seach_sysWeek=0" + "&seach_timeLimit=7"), "shopShift", "考勤", {width:500,height:270,mask:true});
			}
			$("#BATCH_SHOP_CONFIRM_" + index,navTab.getCurrentPanel()).attr("checked","checked");
			this.editing = false;
		}
	});
}
</script>
<div class="pageHeader">
<form id="viewShopDetailConfirmSHListForm" onsubmit="return navTabSearch(this);" action="/ess/tempEmp/viewShopDetailConfirmSHList" method="post">
<div class="searchBar">
<table class="searchContent">
	<tr>
		<td><!--姓名/社号 --><spring:message code="ess.infoApply.NAME_EMPID" /></td>
		<td>
			<input type="text" id="seach_KEY" name="seach_KEY" value="${KEY}">
		</td>
		<td><!--部门 --><spring:message code="ess.infoApply.DEPT" /></td>
		<td>
			<ait:deptList name="seach_DEPTNO" limit="ar" id="viewShopDetailConfirmSHList_deptList" />
			<ait:deptTreeIcon name="seach_DEPTNO" limit="ar" id="viewShopDetailConfirmSHList_deptList" selected="${DEPTNO}"/>
		</td>
		<td><!--确认状态 --><spring:message code="ess.infoApply.confirm_status" /></td>
		<td>
			<select name="seach_ACTIVITY">
				<option value=""><!--全部 --><spring:message code="ess.infoApply.whole" /></option>
				<option value="1" <c:if test="${ACTIVITY eq '1'}">selected</c:if>>
				<!-- 是   -->
				<spring:message code="ess.infoApply.yes" /></option>
				<option value="0" <c:if test="${ACTIVITY eq '0'}">selected</c:if>>
				<!-- 否   -->
				<spring:message code="ess.infoApply.no" /></option>
			</select>
		</td>
		<td><!--期间-->
		<spring:message code="ess.infoApply.Period" />
		</td>
		<td>
			<input type="text" id="START_DATE" name="seach_START_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy/MM/dd'})" value="${START_DATE}"/>~
			<input type="text" id="END_DATE" name="seach_END_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy/MM/dd'})" value="${END_DATE}"/>
		</td>
	</tr>
</table>
</div>
</form>
</div>
<div class="formBar">
	<ul class="toolBar">
		<li><a class="buttonActive" id="viewShopDetailConfirmSHList_Serch" href="#"><span><!--查询--><spring:message code="ess.infoApply.SELECT" /></span></a></li>
		<li><a class="buttonActive" id="viewShopDetailConfirmSHList_Save" href="#"><span><!--确认--><spring:message code="ess.infoApply.confirm" /></span></a></li>
	</ul>
</div>
<div class="pageContent">
				<table class="orderList" width="1720px">
					<thead>
						<tr>
							<th width="30px"><!--NO.--><spring:message code="ess.infoApply.NO." /></th>
							<th width="70px"><!--社号--><spring:message code="ess.infoApply.EMPID" /></th>
							<th width="70px"><!--姓名 --><spring:message code="ess.infoApply.NAME" /></th>
							<th width="170px"><!--部门 -->当日所在<spring:message code="ess.infoApply.DEPT" /></th>
							<th width="80px"><!--日期 --><spring:message code="ess.infoApply.date" /></th>
							<th width="30px"><input type="checkbox" class="checkboxCtrl" group="BATCH_SHOP_CONFIRM" /></th>
							<th width="70px"><!--进门时间 --><spring:message code="ess.infoApply.in_door_time" /></th>
							<th width="70px"><!--出门时间 --><spring:message code="ess.infoApply.out_door_time" /></th>
							<th width="70px"><!--上班时间 --><spring:message code="ess.infoApply.work_time" /></th>
							<th width="70px"><!--下班时间 --><spring:message code="ess.infoApply.out_work_time" /></th>
							<th width="60px"><!--工时 --><spring:message code="ess.infoApply.working_hours" /></th>
							<th width="60px"><!--差异 --><spring:message code="ess.infoApply.difference" /></th>
							<th width="80px"><font color="red"><!--确认上班时间 --><spring:message code="ess.infoApply.confirm_work_time" /></font></th>
							<th width="80px"><font color="red"><!--确认下班时间 --><spring:message code="ess.infoApply.confirm_out_work_time" /></font></th>
							<th width="80px"><font color="red"><!--确认工时 --><spring:message code="ess.infoApply.confirm_working_hours" /></font></th>
							<th width="70px"><font color="red"><!--确认差异 --><spring:message code="ess.infoApply.confirm_difference" /></font></th>
							<th width="70px"><font color="red"><!--确认状态 --><spring:message code="ess.infoApply.confirm_status" /></font></th>
							<th width="170px"><font color="red"><!--备注 --><spring:message code="org.title.REMARK" /></font></th>
							<th width="70px"><!--确认状态 --><spring:message code="ess.infoApply.confirm_status" /></th>
							<th width="120px"><!--确认者 --><spring:message code="ess.infoApply.confirm_person" /></th>
							<th width="120px"><!--确认日期 --><spring:message code="ess.infoApply.confirm_date" /></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${viewShopDetailConfirmSHList}" var="item" varStatus="i">
							<tr target="SEQ" rel="${item.SEQ}">
								<td class='td_center'>${i.count}</td>
								<td class='td_center'>${item.EMPID}</td>
								<td class='td_center'>${item.LOCAL_NAME}</td>
							 	<td class='td_center'>${item.SHOP_DEPT_NAME}</td>
								<td class='td_center'>${item.AR_DATE_STR}</td>
								<td class='td_center' sysLog="activity" sysActivity="${item.ACTIVITY}" sysIndex="${i.index}">
									<c:if test="${item.ACTIVITY eq '0' or item.ACTIVITY eq '1' }">
										<input type="checkbox" id="BATCH_SHOP_CONFIRM_${i.index}" sysPersonId="${item.PERSON_ID}" sysArDateStr="${item.AR_DATE_STR}" name="BATCH_SHOP_CONFIRM" value="${i.index}" />
									</c:if>
								</td>
								<td class='td_center'>${item.IN_MAC_TIME}</td>
								<td class='td_center'>${item.OUT_MAC_TIME}</td>
								<td class='td_center'>${item.SHIFT_FROM_TIME}</td>
								<td class='td_center'>${item.SHIFT_TO_TIME}</td>
								<td class='td_center'>${item.WORKING_HOUR}</td>
								<td class='td_center'>${item.MINUS_LENGTH}</td>
								<td class='td_center' sysType="CONFIRM_FROM_TIME" sysLog="text" sysDutyNo="${item.DUTY_NO}" sysFamily="${item.POST_FAMILY }" sysIndex="${i.index}" id="CONFIRM_FROM_TIME_${i.index}">${item.CONFIRM_FROM_TIME}</td>
								<td class='td_center' sysType="CONFIRM_TO_TIME" sysLog="text" sysDutyNo="${item.DUTY_NO}"  sysFamily="${item.POST_FAMILY }" sysIndex="${i.index}" id="CONFIRM_TO_TIME_${i.index}">${item.CONFIRM_TO_TIME}</td>
								<td class='td_center' sysType="WORKING_HOUR" sysLog="text" sysDutyNo="${item.DUTY_NO}"  sysFamily="${item.POST_FAMILY }" sysIndex="${i.index}" id="WORKING_HOUR_${i.index}">${item.CONFIRM_WORKING_HOUR}</td>
								<td class='td_center' sysType="MINUS_LENGTH" sysLog="text" sysDutyNo="${item.DUTY_NO}"  sysFamily="${item.POST_FAMILY }" sysIndex="${i.index}" id="MINUS_LENGTH_${i.index}">${item.CONFIRM_MINUS_LENGTH}</td>
								<td class='td_center' sysLog="select" sysValue='${shiftItem }' sysFamily="${item.POST_FAMILY }" sysIndex="${i.index}" sysItem1="${item.ITEM_NAME }" sysLen1="${item.CONFIRM_ITEM_LEN_1 }" sysItem2="${item.ITEM_NAME_2 }" sysLen2="${item.CONFIRM_ITEM_LEN_2 }" id="ITEM_NO_0_${i.index}">${item.ITEM_NAME}<c:if test="${not empty item.ITEM_NAME_2}"> ${item.CONFIRM_ITEM_LEN_1}<br/>${item.ITEM_NAME_2} ${item.CONFIRM_ITEM_LEN_2}</c:if></td>
								<td sysType="REMARK" sysLog="text" sysFamily="${item.POST_FAMILY }" sysIndex="${i.index}" id="REMARK_${i.index}">${item.REMARK}</td>
								<td class='td_center'><img src="/resources/images/${item.ACTIVITY}.gif"></img></td>
								<td class='td_center'>${item.CONFIRM_BY}</td>
								<td class='td_center'>${item.CONFIRM_DATE}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
		</div>