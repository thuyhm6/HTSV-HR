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
	
/* 	$("[name='stated']",$.pdialog.getCurrent()).each(function(i, obj){
		var index = $(obj).attr("sysIndex");
	 	var sysDeptNo = $(obj).attr("sysDeptNo");
	 	var sysDutyNo = $(obj).attr("sysDutyNo");
	 	var sysPost = $(obj).attr("sysPost");
		if(sysDeptNo != 'BJ010201030103' && sysDeptNo != 'BJ01040101030103' && sysDeptNo != 'BJ01040101030203' && sysDeptNo != 'BJ01040101030303' && sysDeptNo != 'BJ01040101030403'){
			$("#TENGXUN_OT_" + index,$.pdialog.getCurrent()).attr("sysLog","");
		}
		if(sysDutyNo !='80000010' && sysDutyNo !='14015683' && sysDutyNo !='14015697'){
			$("#P_ACTIVITY_FEE_" + index,$.pdialog.getCurrent()).attr("sysLog","");
		}
		if(sysDutyNo !='80000010'){
			$("#P_SHOP_SUB_" + index,navTab.getCurrentPanel()).attr("sysLog","");
		}
		if(sysDutyNo !='14015690' && sysDutyNo !='14015683' && sysDutyNo !='14015697' && sysDutyNo !='80000011' && sysDutyNo !='80000012' && sysDutyNo !='14015663'){
			$("#P_HEALTH_BT_" + index,$.pdialog.getCurrent()).attr("sysLog","");
			$("#P_PINZHI_BT_" + index,$.pdialog.getCurrent()).attr("sysLog","");
			$("#P_ZHIWU_BT_" + index,$.pdialog.getCurrent()).attr("sysLog","");
			$("#P_SHENGCHAN_BT_" + index,$.pdialog.getCurrent()).attr("sysLog","");
			$("#P_SPCIALSHOP_SUB_" + index,$.pdialog.getCurrent()).attr("sysLog","");
		}
		if(sysDutyNo !='14015663'){
			$("#P_MULTIFUNCTIONAL_SUB_" + index,$.pdialog.getCurrent()).attr("sysLog","");
		}
		if(sysPost !='14015817'){
			$("#LUNCH_ALLOWANCE_" + index,$.pdialog.getCurrent()).attr("sysLog","");
			$("#P_GAOWEN_BT_" + index,$.pdialog.getCurrent()).attr("sysLog","");
		}
		if(sysDutyNo !='14015673' && sysDutyNo !='14016395'){
			$("#P_LENGDONG_BT_" + index,$.pdialog.getCurrent()).attr("sysLog","");
			$("#P_YEBU_BT_" + index,$.pdialog.getCurrent()).attr("sysLog","");
		}
		
	}); */
	
	//保存
	$("#viewShopDetailPersonId_Save",$.pdialog.getCurrent()).click(function(){
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
				jsonData += ' "MINUS_LENGTH": "' + $("#MINUS_LENGTH_" + index,$.pdialog.getCurrent()).html() + '" ,';
				/* <c:if test="${LoginUser.cpnyId eq 'HTSV'}">
				jsonData += ' "ALLOWANCE": "' + $("#ALLOWANCE_" + index,$.pdialog.getCurrent()).html() + '" ,';
				jsonData += ' "TENGXUN_OT": "' + $("#TENGXUN_OT_" + index,$.pdialog.getCurrent()).html() + '" ,';
				jsonData += ' "P_ACTIVITY_FEE": "' + $("#P_ACTIVITY_FEE_" + index,$.pdialog.getCurrent()).html() + '" ,';
				jsonData += ' "P_SHOP_SUB": "' + $("#P_SHOP_SUB_" + index,$.pdialog.getCurrent()).html() + '" ,';
				jsonData += ' "P_HEALTH_BT": "' + $("#P_HEALTH_BT_" + index,$.pdialog.getCurrent()).html() + '" ,';
				jsonData += ' "P_PINZHI_BT": "' + $("#P_PINZHI_BT_" + index,$.pdialog.getCurrent()).html() + '" ,';
				jsonData += ' "P_ZHIWU_BT": "' + $("#P_ZHIWU_BT_" + index,$.pdialog.getCurrent()).html() + '" ,';
				jsonData += ' "P_SHENGCHAN_BT": "' + $("#P_SHENGCHAN_BT_" + index,$.pdialog.getCurrent()).html() + '" ,';
				jsonData += ' "P_SPCIALSHOP_SUB": "' + $("#P_SPCIALSHOP_SUB_" + index,$.pdialog.getCurrent()).html() + '" ,';
				jsonData += ' "P_MULTIFUNCTIONAL_SUB": "' + $("#P_MULTIFUNCTIONAL_SUB_" + index,$.pdialog.getCurrent()).html() + '" ,';
				jsonData += ' "LUNCH_ALLOWANCE": "' + $("#LUNCH_ALLOWANCE_" + index,$.pdialog.getCurrent()).html() + '" ,';
				jsonData += ' "P_GAOWEN_BT": "' + $("#P_GAOWEN_BT_" + index,$.pdialog.getCurrent()).html() + '" ,';
				jsonData += ' "P_LENGDONG_BT": "' + $("#P_LENGDONG_BT_" + index,$.pdialog.getCurrent()).html() + '" ,';
				jsonData += ' "P_YEBU_BT": "' + $("#P_YEBU_BT_" + index,$.pdialog.getCurrent()).html() + '" ,';
				</c:if> */
				jsonData += ' "PERSON_ID": "' + $(obj).attr("sysPersonId") + '" ,';
				jsonData += ' "AR_DATE_STR": "' + $(obj).attr("sysArDateStr") + '" ,';
				jsonData += ' "ITEM_NO": "' + $("#ITEM_NO_" + index,$.pdialog.getCurrent()).html() + '" ,';
				jsonData += ' "adminID": "' + '${LoginUser.adminID}' + '" ,';
				jsonData += ' "adminIP": "' + '${LoginUser.adminIP}' + '" ,';
				jsonData += ' "interCpnyID": "' + '${LoginUser.cpnyId}' + '" ';
				
				jsonData += '}';
		});
		jsonData += ']';
		
		if (jsonData.length == 2) {
			alertMsg.info("<spring:message code='ess.viewShopDetailPersonId.MEIYOUXUYAOQUERENDESHUJU.a' />");//没有需要确认的数据
			return;
		}
		alertMsg.confirm("<spring:message code='ess.viewShopDetailPersonId.QUEDINGYAOQUERENMA.a' />",//确定要确认吗？
	  		  	{okCall:function(){
			  	$.ajax({
	  				type: 'POST',
					url: '/ess/tempEmp/addShopShiftFinalConfirm',
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
	
	$('.orderList',$.pdialog.getCurrent()).on( 'draw.dt', function () {
		initEditFun_ess3437();
	});
	initEditFun_ess3437();
});

function initEditFun_ess3437(){

	/*$('.orderList tbody tr td:[sysLog="text"]',$.pdialog.getCurrent()).editable({type:'text',
		onblur:function(val,settings){
			var index = $(this).attr("sysIndex");
			var sysType = $(this).attr("sysType");
			if(sysType != 'MINUS_LENGTH'){
				val = composeTime(val);
				if(val == ":"){
		    		$(this).html("");
					this.editing = false;
					return false;
				}
			}else if(sysType == 'ALLOWANCE' || sysType == 'TENGXUN_OT'){
				$(this).html(val.replace(/[^\-?\d.]/g,''));
			}else{
				val=val.replace(/[^\-?\d.]/g,'');
			
				$(this).html(val);
				var workingHour = 0;
				var minusLen = 0;
				if(sysType == 'CONFIRM_FROM_TIME'){
					if(val != ''){
						if($("#CONFIRM_TO_TIME_" + index,$.pdialog.getCurrent()).html() == ''){
							$("#CONFIRM_TO_TIME_" + index,$.pdialog.getCurrent()).html( addTime(val, 8 ));
							minusLen = 0;
						}else{
							var shiftToTime = $("#CONFIRM_TO_TIME_" + index,$.pdialog.getCurrent()).html();
							minusLen = addTime2(shiftToTime,val) - 8;
						}
					}
				}else if(sysType == 'CONFIRM_TO_TIME'){
					if(val != ''){
						if($("#CONFIRM_FROM_TIME_" + index,$.pdialog.getCurrent()).html() == ''){
							$("#CONFIRM_FROM_TIME_" + index,$.pdialog.getCurrent()).html( addTime3(val, 8) );
							minusLen = 0;
						}else{
							var shiftFromTime = $("#CONFIRM_FROM_TIME_" + index,$.pdialog.getCurrent()).html();
							minusLen = addTime2(val, shiftFromTime) - 8;
						}
					}
				}
				if(val != ''){
					if(sysType != 'MINUS_LENGTH'){
						$("#MINUS_LENGTH_" + index,$.pdialog.getCurrent()).html(minusLen);
						if(minusLen > 0){
							$("#ITEM_NO_" + index,$.pdialog.getCurrent()).html( "加班" );
						}else if(minusLen == 0){
							$("#ITEM_NO_" + index,$.pdialog.getCurrent()).html( "正常出勤" );
						}else{
							$("#ITEM_NO_" + index,$.pdialog.getCurrent()).html( "早退" );
						}
					}
				}
			}
			$("#BATCH_SHOP_CONFIRM_" + index,$.pdialog.getCurrent()).attr("checked","checked");
			this.editing = false;
		}
	}); */
	
 	$('.orderList tbody tr td:[sysLog="text"]',$.pdialog.getCurrent()).editable({type:'text',
		onblur:function(val,settings){
			var sysType = $(this).attr("sysType");
			var index = $(this).attr("sysIndex");
			if(sysType == 'REMARK'){
				$(this).html(val);
			}else if(sysType == 'ALLOWANCE' || sysType == 'TENGXUN_OT'){
				$(this).html(val.replace(/[^\-?\d.]/g,''));
			}else{
				if(sysType != 'MINUS_LENGTH'){
					val = composeTime(val);
					if(val == ":"){
			    		$(this).html("");
						this.editing = false;
						return false;
					}
				}else{
					val=val.replace(/[^\-?\d.]/g,'');
				}
				$(this).html(val);
				var workingHour = 0;
				var minusLen = 0;
				if(sysType == 'CONFIRM_FROM_TIME'){
					if(val != ''){
						if($("#CONFIRM_TO_TIME_" + index,$.pdialog.getCurrent()).html() == ''){
							$("#CONFIRM_TO_TIME_" + index,$.pdialog.getCurrent()).html( addTime(val, 8) );
							minusLen = 0;
						}else{
							var shiftToTime = $("#CONFIRM_TO_TIME_" + index,$.pdialog.getCurrent()).html();
							minusLen = addTime2(shiftToTime, val) - 8;
						}
					}
				}else if(sysType == 'CONFIRM_TO_TIME'){
					if(val != ''){
						if($("#CONFIRM_FROM_TIME_" + index,$.pdialog.getCurrent()).html() == ''){
							$("#CONFIRM_FROM_TIME_" + index,$.pdialog.getCurrent()).html( addTime3(val, 8) );
							minusLen = 0;
						}else{
							var shiftFromTime = $("#CONFIRM_FROM_TIME_" + index,$.pdialog.getCurrent()).html();
							minusLen = addTime2(val, shiftFromTime) - 8;
						}
					}
				}else if(sysType == 'MINUS_LENGTH'){
					minusLen = parseFloat(val);
				}
				if(val != ''){
					if(sysType != 'MINUS_LENGTH'){
						$("#MINUS_LENGTH_" + index,$.pdialog.getCurrent()).html(minusLen);
						if(minusLen > 0){
							$("#ITEM_NO_" + index,$.pdialog.getCurrent()).html( "<spring:message code='ess.attendance.ot' />" );//加班
						}else if(minusLen == 0){
							$("#ITEM_NO_" + index,$.pdialog.getCurrent()).html( "<spring:message code='ar.excelexport.title.zhengchangchuqin' />" );//正常出勤
						}else{
							$("#ITEM_NO_" + index,$.pdialog.getCurrent()).html( "<spring:message code='ess.infoApply.leave_early' />" );//早退
						}
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
			if(val == ''){
				$(this).html($(this).attr("sysOldValue"));
			}else{
				$(this).attr("sysOldValue",val);
			}
			var index = $(this).attr("sysIndex");
			$("#BATCH_SHOP_CONFIRM_" + index,$.pdialog.getCurrent()).attr("checked","checked");
			this.editing = false;
		}
	});
}
</script>
<div class="formBar">
	<ul class="toolBar">
		<li><a class="buttonActive" id="viewShopDetailPersonId_Save" href="#"><span><!--确认 --><spring:message code="org.title.IS_CONFIRM" /></span></a></li>
	</ul>
</div>
<div class="pageContent">
				<table class="orderList" width="1360px">
					<thead>
						<tr>
							<th width="80px"><!--日期 --><spring:message code="org.title.DATE" /></th>
							<th width="70px"><!--进门时间 --><spring:message code="ess.infoApply.in_door_time" /></th>
							<th width="70px"><!--出门时间 --><spring:message code="ess.infoApply.out_door_time" /></th>
							<th width="70px"><!--上班时间 --><spring:message code="ess.infoApply.work_time" /></th>
							<th width="70px"><!--下班时间 --><spring:message code="ess.infoApply.out_work_time" /></th>
							<th width="60px"><!--差异 --><spring:message code="ess.infoApply.difference" /></th>
							<th width="80px"><!--确认上班时间 --><spring:message code="ess.infoApply.confirm_work_time" /></th>
							<th width="80px"><!--确认下班时间 --><spring:message code="ess.infoApply.confirm_out_work_time" /></th>
							<th width="70px"><!--确认差异 --><spring:message code="ess.infoApply.confirm_difference" /></th>
							<th width="70px"><!--考勤状态 --><spring:message code="ess.infoApply.localyn" /></th>
							<th width="120px"><!--确认者 --><spring:message code="ess.infoApply.confirm_person" /></th>
							<th width="120px"><!--确认日期 --><spring:message code="ess.infoApply.confirm_date" /></th>
							<th width="80px"><font color="red"><!--最终上班时间 --><spring:message code="ess.viewShopDetailPersonId.ZUIZHONGSHANGBANSHIJIAN.a" /></font></th>
							<th width="80px"><font color="red"><!--最终下班时间 --><spring:message code="ess.viewShopDetailPersonId.ZUIZHONGXIABANSHIJIAN.a" /></font></th>
							<th width="70px"><font color="red"><!--最终差异 --><spring:message code="ess.viewShopDetailPersonId.ZUIZHONGCHAYI.a" /></font></th>
							<th width="70px"><font color="red"><!--最终考勤状态--><spring:message code="ess.viewShopDetailPersonId.ZUIZHONGKAOQINZHUANGTAI.a" /></font></th>
							<%-- <c:if test="${LoginUser.cpnyId eq 'HTSV'}">
							 	<th width="80px"><font >收银津贴</th>
								<th width="80px"><font >腾讯加班</th>
								<th width="80px"><font >活动费</font></th>
								<th width="80px"><font >店铺补助</font></th>
								<th width="80px"><font >卫生津贴</font></th>
								<th width="80px"><font >品质津贴</font></th>
								<th width="80px"><font >职务津贴</font></th>
								<th width="80px"><font >生产津贴</font></th>
								<th width="80px"><font >特殊店铺补助</font></th>
								<th width="80px"><font >多功能技师补助</font></th>
								<th width="80px"><font >餐补</font></th>
								<th width="80px"><font >高温津贴</font></th>
								<th width="80px"><font >冷冻补贴</font></th>
								<th width="80px"><font >夜补奖金</font></th>
							</c:if> --%>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${viewShopDetailPersonId}" var="item" varStatus="i">
							<tr>
								<td class='td_center'>${item.AR_DATE_STR}<input type="hidden" id="BATCH_SHOP_CONFIRM_${i.index}" sysPersonId="${item.PERSON_ID}" sysArDateStr="${item.AR_DATE_STR}" name="BATCH_SHOP_CONFIRM" value="${i.index}" /></td>
								<td class='td_center'>${item.IN_MAC_TIME}</td>
								<td class='td_center'>${item.OUT_MAC_TIME}</td>
								<td class='td_center'>${item.SHIFT_FROM_TIME}</td>
								<td class='td_center'>${item.SHIFT_TO_TIME}</td>
								<td class='td_center'>${item.MINUS_LENGTH}</td>
								<td class='td_center'>${item.CONFIRM_FROM_TIME}</td>
								<td class='td_center'>${item.CONFIRM_TO_TIME}</td>
								<td class='td_center'>${item.CONFIRM_MINUS_LENGTH}</td>
								<td class='td_center'>${item.CONFIRM_ITEM_NAME }</td>
								<td class='td_center'>${item.CONFIRM_BY}</td>
								<td class='td_center'>${item.CONFIRM_DATE}</td>
								
								<td class='td_center' sysActivity="${item.ACTIVITY }" sysType="CONFIRM_FROM_TIME" sysLog="text" sysIndex="${i.index}" id="CONFIRM_FROM_TIME_${i.index}">${item.FINAL_CONFIRM_FROM_TIME}</td>
								<td class='td_center' sysActivity="${item.ACTIVITY }" sysType="CONFIRM_TO_TIME" sysLog="text" sysIndex="${i.index}" id="CONFIRM_TO_TIME_${i.index}">${item.FINAL_CONFIRM_TO_TIME}</td>
								<td class='td_center' sysActivity="${item.ACTIVITY }" sysType="MINUS_LENGTH" sysLog="text" sysIndex="${i.index}" id="MINUS_LENGTH_${i.index}">${item.FINAL_CONFIRM_MINUS_LENGTH}</td>
								<td class='td_center' sysActivity="${item.ACTIVITY }" sysLog="select"  sysTrueLog="activity" sysValue='${shiftItem}' sysIndex="${i.index}" sysOldValue="${item.FINAL_CONFIRM_ITEM_NAME}" id="ITEM_NO_${i.index}">${item.FINAL_CONFIRM_ITEM_NAME}</td>
								<%--<input type="hidden" id="stated_${i.index}" name="stated" sysActivity="${item.ACTIVITY}" sysDeptNo="${item.DEPTNO }" sysPost="${item.POST_FAMILY}" sysDutyNo="${item.DUTY_NO}"   sysIndex="${i.index}">
								 <c:if test="${LoginUser.cpnyId eq 'HTSV'}">
									<td class='td_center' sysType="ALLOWANCE"  sysIndex="${i.index}" id="ALLOWANCE_${i.index}">${item.RECEIVING_ALLOWANCE}</td>
									<td class='td_center' sysType="TENGXUN_OT"  sysIndex="${i.index}" id="TENCENT_OT_${i.index}">${item.TENCENT_OT}</td>
									<td class='td_center' sysType="ALLOWANCE"  sysIndex="${i.index}" id="P_ACTIVITY_FEE_${i.index}">${item.P_ACTIVITY_FEE}</td>
									<td class='td_center' sysType="ALLOWANCE"  sysIndex="${i.index}" id="P_SHOP_SUB_${i.index}">${item.P_SHOP_SUB}</td>
									<td class='td_center' sysType="ALLOWANCE"  sysIndex="${i.index}" id="P_HEALTH_BT_${i.index}">${item.P_HEALTH_BT}</td>
									<td class='td_center' sysType="ALLOWANCE"  sysIndex="${i.index}" id="P_PINZHI_BT_${i.index}">${item.P_PINZHI_BT}</td>
									<td class='td_center' sysType="ALLOWANCE"  sysIndex="${i.index}" id="P_ZHIWU_BT_${i.index}">${item.P_ZHIWU_BT}</td>
									<td class='td_center' sysType="ALLOWANCE"  sysIndex="${i.index}" id="P_SHENGCHAN_BT_${i.index}">${item.P_SHENGCHAN_BT}</td>
									<td class='td_center' sysType="ALLOWANCE"  sysIndex="${i.index}" id="P_SPCIALSHOP_SUB_${i.index}">${item.P_SPCIALSHOP_SUB}</td>
									<td class='td_center' sysType="ALLOWANCE"  sysIndex="${i.index}" id="P_MULTIFUNCTIONAL_SUB_${i.index}">${item.P_MULTIFUNCTIONAL_SUB}</td>
									<td class='td_center' sysType="ALLOWANCE"  sysIndex="${i.index}" id="LUNCH_ALLOWANCE_${i.index}">${item.LUNCH_ALLOWANCE}</td>
									<td class='td_center' sysType="ALLOWANCE"  sysIndex="${i.index}" id="P_GAOWEN_BT_${i.index}">${item.P_GAOWEN_BT}</td>
									<td class='td_center' sysType="ALLOWANCE"  sysIndex="${i.index}" id="P_LENGDONG_BT_${i.index}">${item.P_LENGDONG_BT}</td>
									<td class='td_center' sysType="ALLOWANCE"  sysIndex="${i.index}" id="P_YEBU_BT_${i.index}">${item.P_YEBU_BT}</td>
								</c:if> --%>
							</tr>
						</c:forEach>
					</tbody>
				</table>
		</div>
