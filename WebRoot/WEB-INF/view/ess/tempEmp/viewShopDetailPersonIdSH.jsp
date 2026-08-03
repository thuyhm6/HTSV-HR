<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
$(document).ready(function(){
	//月考勤锁定  禁止修改
	$("[sysActivity='3']",$.pdialog.getCurrent()).attr("sysLog","");
	
	if($("[sysActivity='3']",$.pdialog.getCurrent()).length > 0){
		$("#viewShopDetailPersonId_Save",$.pdialog.getCurrent()).css("display","none");
	}
	//保存
	$("#viewShopDetailPersonIdSH_Save",$.pdialog.getCurrent()).click(function(){	
		//获取页面的值
		var jsonData = '[';
		$("input:[name='BATCH_SHOP_CONFIRM']",$.pdialog.getCurrent()).each(function(i, obj){
			if (jsonData.length > 1) {
				jsonData += ',{';
			} else {
				jsonData += '{';
			}
			var index = $(obj).val();
			jsonData += ' "SHIFT_FROM_TIME": "' + $("#CONFIRM_FROM_TIME_" + index,$.pdialog.getCurrent()).html() + '" ,';
			jsonData += ' "SHIFT_TO_TIME": "' + $("#CONFIRM_TO_TIME_" + index,$.pdialog.getCurrent()).html() + '" ,';
			jsonData += ' "WORKING_HOUR": "' + $("#WORKING_HOUR_" + index,$.pdialog.getCurrent()).html() + '" ,';
			jsonData += ' "MINUS_LENGTH": "' + $("#MINUS_LENGTH_" + index,$.pdialog.getCurrent()).html() + '" ,';
			jsonData += ' "PERSON_ID": "' + $(obj).attr("sysPersonId") + '" ,';
			jsonData += ' "AR_DATE_STR": "' + $(obj).attr("sysArDateStr") + '" ,';

			jsonData += ' "ITEM_NO": "' + $("#ITEM_NO_0_" + index,$.pdialog.getCurrent()).attr("sysItem1") + '" ,';
			jsonData += ' "ITEM_LEN_1": "' + $("#ITEM_NO_0_" + index,$.pdialog.getCurrent()).attr("sysLen1") + '" ,';
			jsonData += ' "ITEM_NO_2": "' + $("#ITEM_NO_0_" + index,$.pdialog.getCurrent()).attr("sysItem2") + '" ,';
			jsonData += ' "ITEM_LEN_2": "' + $("#ITEM_NO_0_" + index,$.pdialog.getCurrent()).attr("sysLen2") + '" ,';
			
			jsonData += ' "adminID": "' + '${LoginUser.adminID}' + '" ,';
			jsonData += ' "adminIP": "' + '${LoginUser.adminIP}' + '" ,';
			jsonData += ' "interCpnyID": "' + '${LoginUser.cpnyId}' + '" ';
			
			jsonData += '}';
		});
		jsonData += ']';
		
		if (jsonData.length == 2) {
			alertMsg.info("没有需要确认的数据");
			return;
		}
		alertMsg.confirm("确定要确认吗？",
	  		  	{okCall:function(){
			  	$.ajax({
	  				type: 'POST',
					url: '/ess/tempEmp/addShopShiftFinalConfirmSH',
					data: [{ name: 'jsonData', value: jsonData }],
	  				dataType:"json",
	  				cache: false,
	  				success: function(){
		  				navTabSearch($("#viewShopSummaryConfirmListForm"));
			  			$.pdialog.closeCurrent();
	  				},
	  				error: DWZ.ajaxError
	  			});
	  	}});
	});
	
	$(".orderList",$.pdialog.getCurrent()).dataTable({
		"bPaginate": true,    //分页
	    "bAutoWidth":false,//表格宽度自动变化
	    "bProcessing":true,
	    "lengthMenu": [[15,20,35, 50], [15,20, 35, 50]],
		"bLengthChange": true,  //按多少条记录显示下拉框
		"iDisplayLength": 15, //默认每页显示的记录数
		"bFilter": true,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
     	"searching": true,//本地搜索
		"bSort": true,   //排序功能
		"bInfo": true,   //显示datatables的信息（底部的页数，条目数信息）
		"bScrollInfinite":true,
	     "orderClasses": false,
	     "order":[],//初始化不用自动排序
	     "scrollY": $(document.body).height() - 240,
	     "scrollX": $(document.body).width(),
	     "scrollCollapse": false,
	     "deferRender":true,
	    "fixedColumns":{leftColumns: 3},
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
        "buttons": [] 
	});
	
	$('.orderList',$.pdialog.getCurrent()).on( 'draw.dt', function () {
		initEditFun_ess3437();
	});
	initEditFun_ess3437();
});

function initEditFun_ess3437(){

	$('.orderList tbody tr td:[sysLog="text"]',$.pdialog.getCurrent()).editable({type:'text',
		onblur:function(val,settings){
			var index = $(this).attr("sysIndex");
			var sysType = $(this).attr("sysType");
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
					if($("#CONFIRM_TO_TIME_" + index,$.pdialog.getCurrent()).html() == ''){
						$("#CONFIRM_TO_TIME_" + index,$.pdialog.getCurrent()).html( addTime(val, workHour ) );
						minusLen = 0;
						workingHours = workHour - lunchHour;
					}else{
						var shiftToTime = $("#CONFIRM_TO_TIME_" + index,$.pdialog.getCurrent()).html();
						minusLen = addTime2(shiftToTime ,val) - workHour;
						workingHours = addTime2(shiftToTime, val) - lunchHour;
					}
				}
			}else if(sysType == 'CONFIRM_TO_TIME'){
				if(val != ''){
					if($("#CONFIRM_FROM_TIME_" + index,$.pdialog.getCurrent()).html() == ''){
						$("#CONFIRM_FROM_TIME_" + index,$.pdialog.getCurrent()).html( addTime3(val,workHour ));
						minusLen = 0;
						workingHours = workHour - lunchHour;
					}else{
						var shiftFromTime = $("#CONFIRM_FROM_TIME_" + index,$.pdialog.getCurrent()).html();
						minusLen = addTime2(val, shiftFromTime) - workHour;
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
					$("#MINUS_LENGTH_" + index,$.pdialog.getCurrent()).html(minusLen);
					$("#WORKING_HOUR_" + index,$.pdialog.getCurrent()).html(workingHours);
					if(minusLen >= 0){
						$("#ITEM_NO_0_" + index,$.pdialog.getCurrent()).html( "正常出勤" );
					}else{
						$("#ITEM_NO_0_" + index,$.pdialog.getCurrent()).html( "早退" );
					}
				}
			}
			
			$("#BATCH_SHOP_CONFIRM_" + index,$.pdialog.getCurrent()).attr("checked","checked");
			this.editing = false;
		}
	});

	$('.orderList tbody tr td:[sysLog="select"]',$.pdialog.getCurrent()).editable({type:'select',
		onblur:function(val,settings){
			$(this).html(val);
			var index = $(this).attr("sysIndex");

			$("#ITEM_NO_0_" + index,$.pdialog.getCurrent()).attr("sysItem1",val);
			$("#ITEM_NO_0_" + index,$.pdialog.getCurrent()).attr("sysItem2","");
			$("#ITEM_NO_0_" + index,$.pdialog.getCurrent()).attr("sysLen1","");
			$("#ITEM_NO_0_" + index,$.pdialog.getCurrent()).attr("sysLen2","");
			
			if(val == '多状态'){
				$(this).html("");
				$("#ITEM_NO_0_" + index,$.pdialog.getCurrent()).attr("sysItem1","");
				$.pdialog.open(encodeURI("/ess/tempEmp/viewSHItemList?seach_index=" + index + "&seach_sysWeek=0" + "&seach_timeLimit=7&seach_panelType=dialog"), "shopShift", "考勤", {width:500,height:270,mask:true});
			}
			$("#BATCH_SHOP_CONFIRM_" + index,$.pdialog.getCurrent()).attr("checked","checked");
			this.editing = false;
		}
	});
}
</script>
<div class="formBar">
	<ul class="toolBar">
		<li><a class="buttonActive" id="viewShopDetailPersonIdSH_Save" href="#"><span>确认</span></a></li>
	</ul>
</div>
<div class="pageContent">
				<table class="orderList" width="1560px">
					<thead>
						<tr>
							<th width="80px">日期</th>
							<th width="70px">进门时间</th>
							<th width="70px">出门时间</th>
							<th width="70px">上班时间</th>
							<th width="70px">下班时间</th>
							<th width="60px">工时</th>
							<th width="60px">差异</th>
							<th width="80px">确认上班时间</th>
							<th width="80px">确认下班时间</th>
							<th width="70px">确认工时</th>
							<th width="70px">确认差异</th>
							<th width="70px">考勤状态</th>
							<th width="120px">确认者</th>
							<th width="120px">确认日期</th>
							<th width="80px"><font color="red">最终上班时间</font></th>
							<th width="80px"><font color="red">最终下班时间</font></th>
							<th width="70px"><font color="red">最终工时</font></th>
							<th width="70px"><font color="red">最终差异</font></th>
							<th width="70px"><font color="red">最终考勤状态</font></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${viewShopDetailPersonIdSH}" var="item" varStatus="i">
							<tr target="SEQ" rel="${item.SEQ}">
								<td class='td_center'>${item.AR_DATE_STR}<input type="hidden" id="BATCH_SHOP_CONFIRM_${i.index}" sysPersonId="${item.PERSON_ID}" sysArDateStr="${item.AR_DATE_STR}" name="BATCH_SHOP_CONFIRM" value="${i.index}" /></td>
								<td class='td_center'>${item.IN_MAC_TIME}</td>
								<td class='td_center'>${item.OUT_MAC_TIME}</td>
								<td class='td_center'>${item.SHIFT_FROM_TIME}</td>
								<td class='td_center'>${item.SHIFT_TO_TIME}</td>
								<td class='td_center'>${item.WORKING_HOUR}</td>
								<td class='td_center'>${item.MINUS_LENGTH}</td>
								<td class='td_center'>${item.CONFIRM_FROM_TIME}</td>
								<td class='td_center'>${item.CONFIRM_TO_TIME}</td>
								<td class='td_center'>${item.CONFIRM_WORKING_HOUR}</td>
								<td class='td_center'>${item.CONFIRM_MINUS_LENGTH}</td>
								<td class='td_center'>${item.CONFIRM_ITEM_NAME}<c:if test="${not empty item.CONFIRM_ITEM_NAME_2}"> ${item.CONFIRM_ITEM_LEN_1}<br/>${item.CONFIRM_ITEM_NAME_2} ${item.CONFIRM_ITEM_LEN_2}</c:if></td>
								<td class='td_center'>${item.CONFIRM_BY}</td>
								<td class='td_center'>${item.CONFIRM_DATE}</td>
								
								<td class='td_center' sysActivity="${item.ACTIVITY }" sysType="CONFIRM_FROM_TIME" sysLog="text" sysFamily="${item.POST_FAMILY }" sysIndex="${i.index}" id="CONFIRM_FROM_TIME_${i.index}">${item.FINAL_CONFIRM_FROM_TIME}</td>
								<td class='td_center' sysActivity="${item.ACTIVITY }" sysType="CONFIRM_TO_TIME" sysLog="text" sysFamily="${item.POST_FAMILY }" sysIndex="${i.index}" id="CONFIRM_TO_TIME_${i.index}">${item.FINAL_CONFIRM_TO_TIME}</td>
								<td class='td_center' sysActivity="${item.ACTIVITY }" sysType="WORKING_HOUR" sysLog="text" sysFamily="${item.POST_FAMILY }" sysIndex="${i.index}" id="WORKING_HOUR_${i.index}">${item.FINAL_CONFIRM_WORKING_HOUR}</td>
								<td class='td_center' sysActivity="${item.ACTIVITY }" sysType="MINUS_LENGTH" sysLog="text" sysFamily="${item.POST_FAMILY }" sysIndex="${i.index}" id="MINUS_LENGTH_${i.index}">${item.FINAL_CONFIRM_MINUS_LENGTH}</td>
								<td class='td_center' sysActivity="${item.ACTIVITY }" sysLog="select" sysValue='${shiftItem}' 
									sysOldValue="${item.FINAL_CONFIRM_ITEM_NAME}" sysFamily="${item.POST_FAMILY }" 
									sysIndex="${i.index}" sysItem1="${item.FINAL_CONFIRM_ITEM_NAME }" 
									sysLen1="${item.FINAL_CONFIRM_ITEM_LEN_1 }" sysItem2="${item.FINAL_CONFIRM_ITEM_NAME_2 }" 
									sysLen2="${item.FINAL_CONFIRM_ITEM_LEN_2 }" id="ITEM_NO_0_${i.index}">
									${item.FINAL_CONFIRM_ITEM_NAME}
										<c:if test="${not empty item.FINAL_CONFIRM_ITEM_NAME_2}">
											 ${item.FINAL_CONFIRM_ITEM_LEN_1}<br/>${item.FINAL_CONFIRM_ITEM_NAME_2} 
											 ${item.FINAL_CONFIRM_ITEM_LEN_2}
										 </c:if>
							    </td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
		</div>
