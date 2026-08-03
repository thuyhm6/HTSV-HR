<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
$(document).ready(function(){

	$("[sysLog='activity']",navTab.getCurrentPanel()).each(function(i, obj){
		var index = $(obj).attr("sysIndex");
	 	var sysActivity = $(obj).attr("sysActivity");
	 	var sysDeptNo = $(obj).attr("sysDeptNo");
	 	var sysDutyNo = $(obj).attr("sysDutyNo");
	 	var sysPost = $(obj).attr("sysPost");
		if(sysActivity != '0' && sysActivity != '1'){
			$("td[sysIndex='" + index + "']",navTab.getCurrentPanel()).attr("sysLog","");
		}

		if(sysDeptNo != 'BJ010201030103' && sysDeptNo != 'BJ01040101030103' && sysDeptNo != 'BJ01040101030203' && sysDeptNo != 'BJ01040101030303' && sysDeptNo != 'BJ01040101030403'){
			$("#TENGXUN_OT_" + index,navTab.getCurrentPanel()).attr("sysLog","");
		}
		if(sysDutyNo !='80000010' && sysDutyNo !='14015683' && sysDutyNo !='14015697'){
			$("#P_ACTIVITY_FEE_" + index,navTab.getCurrentPanel()).attr("sysLog","");
		}
		if(sysDutyNo !='80000010'){
			$("#P_SHOP_SUB_" + index,navTab.getCurrentPanel()).attr("sysLog","");
		}
		if(sysDutyNo !='14015690' && sysDutyNo !='14015683' && sysDutyNo !='14015697' && sysDutyNo !='80000011' && sysDutyNo !='80000012' && sysDutyNo !='14015663'){
			$("#P_HEALTH_BT_" + index,navTab.getCurrentPanel()).attr("sysLog","");
			$("#P_PINZHI_BT_" + index,navTab.getCurrentPanel()).attr("sysLog","");
			$("#P_ZHIWU_BT_" + index,navTab.getCurrentPanel()).attr("sysLog","");
			$("#P_SHENGCHAN_BT_" + index,navTab.getCurrentPanel()).attr("sysLog","");
			$("#P_SPCIALSHOP_SUB_" + index,navTab.getCurrentPanel()).attr("sysLog","");
		}
		if(sysDutyNo !='14015663'){
			$("#P_MULTIFUNCTIONAL_SUB_" + index,navTab.getCurrentPanel()).attr("sysLog","");
		}
		if(sysPost !='14015817'){
			$("#LUNCH_ALLOWANCE_" + index,navTab.getCurrentPanel()).attr("sysLog","");
			$("#P_GAOWEN_BT_" + index,navTab.getCurrentPanel()).attr("sysLog","");
		}
		if(sysDutyNo !='14015673' && sysDutyNo !='14016395'){
			$("#P_LENGDONG_BT_" + index,navTab.getCurrentPanel()).attr("sysLog","");
			$("#P_YEBU_BT_" + index,navTab.getCurrentPanel()).attr("sysLog","");
		}
		
	});

	//查询
	$("#viewShopDetailConfirmList_Serch",navTab.getCurrentPanel()).click(function(){
		$("#viewShopDetailConfirmListForm",navTab.getCurrentPanel()).submit();
	});
	//保存
	$("#viewShopDetailConfirmList_Save",navTab.getCurrentPanel()).click(function(){	
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
				jsonData += ' "REMARK": "' + $("#REMARK_" + index,navTab.getCurrentPanel()).html() + '" ,';
				<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
				jsonData += ' "ALLOWANCE": "' + $("#ALLOWANCE_" + index,navTab.getCurrentPanel()).html() + '" ,';
				jsonData += ' "TENGXUN_OT": "' + $("#TENGXUN_OT_" + index,navTab.getCurrentPanel()).html() + '" ,';
				/* jsonData += ' "P_ACTIVITY_FEE": "' + $("#P_ACTIVITY_FEE_" + index,navTab.getCurrentPanel()).html() + '" ,';
				jsonData += ' "P_SHOP_SUB": "' + $("#P_SHOP_SUB_" + index,navTab.getCurrentPanel()).html() + '" ,';
				jsonData += ' "P_HEALTH_BT": "' + $("#P_HEALTH_BT_" + index,navTab.getCurrentPanel()).html() + '" ,';
				jsonData += ' "P_PINZHI_BT": "' + $("#P_PINZHI_BT_" + index,navTab.getCurrentPanel()).html() + '" ,';
				jsonData += ' "P_ZHIWU_BT": "' + $("#P_ZHIWU_BT_" + index,navTab.getCurrentPanel()).html() + '" ,';
				jsonData += ' "P_SHENGCHAN_BT": "' + $("#P_SHENGCHAN_BT_" + index,navTab.getCurrentPanel()).html() + '" ,';
				jsonData += ' "P_SPCIALSHOP_SUB": "' + $("#P_SPCIALSHOP_SUB_" + index,navTab.getCurrentPanel()).html() + '" ,';
				jsonData += ' "P_MULTIFUNCTIONAL_SUB": "' + $("#P_MULTIFUNCTIONAL_SUB_" + index,navTab.getCurrentPanel()).html() + '" ,';
				jsonData += ' "LUNCH_ALLOWANCE": "' + $("#LUNCH_ALLOWANCE_" + index,navTab.getCurrentPanel()).html() + '" ,';
				jsonData += ' "P_GAOWEN_BT": "' + $("#P_GAOWEN_BT_" + index,navTab.getCurrentPanel()).html() + '" ,';
				jsonData += ' "P_LENGDONG_BT": "' + $("#P_LENGDONG_BT_" + index,navTab.getCurrentPanel()).html() + '" ,';
				jsonData += ' "P_YEBU_BT": "' + $("#P_YEBU_BT_" + index,navTab.getCurrentPanel()).html() + '" ,'; */
				</c:if>
				jsonData += ' "PERSON_ID": "' + $(obj).attr("sysPersonId") + '" ,';
				jsonData += ' "AR_DATE_STR": "' + $(obj).attr("sysArDateStr") + '" ,';
				jsonData += ' "ITEM_NO": "' + $("#ITEM_NO_" + index,navTab.getCurrentPanel()).html() + '" ,';
				jsonData += ' "adminID": "' + '${LoginUser.adminID}' + '" ,';
				jsonData += ' "adminIP": "' + '${LoginUser.adminIP}' + '" ,';
				jsonData += ' "interCpnyID": "' + '${LoginUser.cpnyId}' + '" ';
				
				jsonData += '}';
			}
		});
		jsonData += ']';
		
		if (jsonData.length == 2) {
			alertMsg.info("<spring:message code='ess.infoApply.NO_NEED_TO_APPLY_DATA' />");//没有需要申请的数据
			return;
		}
		alertMsg.confirm("<spring:message code='ess.message.confirm_apply' />",//确定要申请吗？
	  		  	{okCall:function(){
			  	$.ajax({
	  				type: 'POST',
					url: '/ess/tempEmp/addShopShiftConfirm',
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
	    "lengthMenu": [[10,20,35, 50], [10,20, 35, 50]],
		"bLengthChange": true,  //按多少条记录显示下拉框
		"iDisplayLength": 20, //默认每页显示的记录数
		"bFilter": true,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
     	"searching": true,//本地搜索
		"bSort": true,   //排序功能
		"bInfo": true,   //显示datatables的信息（底部的页数，条目数信息）
		"bScrollInfinite":true,
	     "orderClasses": false,
	     "order":[],//初始化不用自动排序
	     "scrollY": $(document.body).height() - 290,
	     "scrollX": $(document.body).width(),
	     "scrollCollapse": false,
	     "deferRender":true,
	        "columnDefs": [//自定义排序类型
		                     { "orderable": false, "targets": [1,2] }
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
						if($("#CONFIRM_TO_TIME_" + index,navTab.getCurrentPanel()).html() == ''){
							$("#CONFIRM_TO_TIME_" + index,navTab.getCurrentPanel()).html( addTime(val, 8) );
							minusLen = 0;
						}else{
							var shiftToTime = $("#CONFIRM_TO_TIME_" + index,navTab.getCurrentPanel()).html();
							minusLen = addTime2(shiftToTime, val) - 8;
						}
					}
				}else if(sysType == 'CONFIRM_TO_TIME'){
					if(val != ''){
						if($("#CONFIRM_FROM_TIME_" + index,navTab.getCurrentPanel()).html() == ''){
							$("#CONFIRM_FROM_TIME_" + index,navTab.getCurrentPanel()).html( addTime3(val, 8) );
							minusLen = 0;
						}else{
							var shiftFromTime = $("#CONFIRM_FROM_TIME_" + index,navTab.getCurrentPanel()).html();
							minusLen = addTime2(val, shiftFromTime) - 8;
						}
					}
				}else if(sysType == 'MINUS_LENGTH'){
					minusLen = parseFloat(val);
				}
				if(val != ''){
					if(sysType != 'MINUS_LENGTH'){
						$("#MINUS_LENGTH_" + index,navTab.getCurrentPanel()).html(minusLen);
						if(minusLen > 0){
							$("#ITEM_NO_" + index,navTab.getCurrentPanel()).html( "<spring:message code='ess.attendance.ot' />" );//加班
						}else if(minusLen == 0){
							$("#ITEM_NO_" + index,navTab.getCurrentPanel()).html( "<spring:message code='ar.excelexport.title.zhengchangchuqin' />" );//正常出勤
						}else{
							$("#ITEM_NO_" + index,navTab.getCurrentPanel()).html( "<spring:message code='ar.monthwork.title.EarlyLeave' />" );//早退
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
			$("#BATCH_SHOP_CONFIRM_" + index,navTab.getCurrentPanel()).attr("checked","checked");
			this.editing = false;
		}
	});
}
</script>
<div class="pageHeader">
<form id="viewShopDetailConfirmListForm" onsubmit="return navTabSearch(this);" action="/ess/tempEmp/viewShopDetailConfirmList" method="post">
<div class="searchBar">
<table class="searchContent">
	<tr>
		<td><spring:message code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME" /><!-- 社号/姓名 --></td>
		<td>
			<input type="text" id="seach_KEY" name="seach_KEY" value="${KEY}">
		</td>
		<td><!-- 部门： --> <spring:message
							code="ess.infoApply.DEPT" /></td>
		<td>
			<ait:deptList name="seach_DEPTNO" limit="manager" id="viewShopDetailConfirmList_deptList" />
			<ait:deptTreeIcon name="seach_DEPTNO" limit="manager" id="viewShopDetailConfirmList_deptList" selected="${DEPTNO}"/>
		</td>
		<td><!-- 确认状态 --> <spring:message
							code="ess.humanConfirm.title.confirmStatus" /></td>
		<td>
			<select name="seach_ACTIVITY">
				<option value="" ><!--全部 --> <spring:message
							code="pa.salary.canShu.quanBu" /></option>
				<option value="1" <c:if test="${ACTIVITY eq '1'}">selected</c:if>><!-- 是 --> <spring:message
							code="sys.affirm.title.yes" /></option>
				<option value="0" <c:if test="${ACTIVITY eq '0'}">selected</c:if>><!-- 否 --> <spring:message
							code="empsubject.no" /></option>
			</select>
		</td>
		<td><spring:message code="ess.workgroup.title.duration" /><!-- 期间 --></td>
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
		<li><a class="buttonActive" id="viewShopDetailConfirmList_Serch" href="#"><span><spring:message code="hrm.empinfo.QUERY" /><!-- 查询 --></span></a></li>
		<li><a class="buttonActive" id="viewShopDetailConfirmList_Save" href="#"><span><spring:message code="ess.infoApply.confirm" /><!-- 确认--></span></a></li>
		<li><a class="buttonActive" onclick="downloadExcel('viewShopDetailConfirmListForm','/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=285','/ess/tempEmp/viewShopDetailConfirmList?firstFlag=N&deleteYN=Y')"><span><spring:message code="ess.infoApply.export_to_Excel" /><!-- 导出到Excel--></span></a></li>
	</ul>
</div>
<div class="pageContent">
				<table class="orderList" width="2200px">
					<thead>
						<tr>
							<th width="30px">NO.</th>
							<th width="70px"><spring:message code="org.title.EMPID" /><!-- 社号 --></th>
							<th <c:if test="${LoginUser.cpnyId ne 'HAE'}">width="70px"</c:if><c:if test="${LoginUser.cpnyId eq 'HAE'}">width="50px"</c:if>><spring:message code="alert.pa.pasalarycanshu.xingming" /><!-- 姓名--></th>
							<th <c:if test="${LoginUser.cpnyId ne 'HAE'}">width="170px"</c:if><c:if test="${LoginUser.cpnyId eq 'HAE'}">width="90px"</c:if> ><spring:message code="ess.viewShopDetailConfirmList.DANGRISUOZAIBUMEN.a" /><!-- 当日所在部门 --></th>
							<th <c:if test="${LoginUser.cpnyId ne 'HAE'}">width="80px"</c:if><c:if test="${LoginUser.cpnyId eq 'HAE'}">width="60px"</c:if>><spring:message code="ar.attendanceView.viewNoSwipingCard.dateTime" /><!-- 日期 --></th>
							<th width="30px"><input type="checkbox" class="checkboxCtrl" group="BATCH_SHOP_CONFIRM" /></th>
							<th <c:if test="${LoginUser.cpnyId ne 'HAE'}">width="70px"</c:if><c:if test="${LoginUser.cpnyId eq 'HAE'}">width="40px"</c:if>><spring:message code="ess.infoApply.in_door_time" /><!-- 进门时间 --></th>
							<th <c:if test="${LoginUser.cpnyId ne 'HAE'}">width="70px"</c:if><c:if test="${LoginUser.cpnyId eq 'HAE'}">width="40px"</c:if>><spring:message code="ess.infoApply.out_door_time" /><!-- 出门时间 --></th>
							<th width="70px"><spring:message code="ess.infoApply.work_time" /><!-- 上班时间 --></th>
							<th width="70px"><spring:message code="ess.infoApply.out_work_time" /><!-- 下班时间 --></th>
							<th width="60px"><spring:message code="ess.infoApply.difference" /><!-- 差异 --></th>
							<th width="80px"><font color="red"><spring:message code="ess.infoApply.confirm_work_time" /><!-- 确认上班时间 --></font></th>
							<th width="80px"><font color="red"><spring:message code="ess.infoApply.confirm_out_work_time" /><!-- 确认下班时间 --></font></th>
							<th width="70px"><font color="red"><spring:message code="ess.infoApply.confirm_difference" /><!-- 确认差异 --></font></th>
							<th width="70px"><font color="red"><spring:message code="ess.infoApply.localyn" /><!-- 考勤状态 --></font></th>
							<th width="170px"><font color="red"><spring:message code="ess.empInfo.remarks" /><!-- 备注 --></font></th>
							<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
							<th width="85px"><font color="red"><spring:message code="ar.menu.title.receivingallowance" /><!-- 收银津贴 --></font></th>
							<th width="85px"><font color="red"><spring:message code="ess.infoApply.tengxunot" /><!-- 腾讯加班 --></font></th>
							<!-- <th width="85px"><font color="red">活动费</font></th>
							<th width="85px"><font color="red">店铺补助</font></th>
							<th width="85px"><font color="red">卫生津贴</font></th>
							<th width="85px"><font color="red">品质津贴</font></th>
							<th width="85px"><font color="red">职务津贴</font></th>
							<th width="85px"><font color="red">生产津贴</font></th>
							<th width="85px"><font color="red">特殊店铺补助</font></th>
							<th width="85px"><font color="red">多功能技师补助</font></th>
							<th width="85px"><font color="red">餐补</font></th>
							<th width="85px"><font color="red">高温津贴</font></th>
							<th width="85px"><font color="red">冷冻补贴</font></th>
							<th width="85px"><font color="red">夜补奖金</font></th> -->
							</c:if>
							<th width="70px"><spring:message code="ess.infoApply.confirm_status" /><!-- 确认状态 --></th>
							<th width="120px"><spring:message code="ess.infoApply.confirm_person" /><!-- 确认者 --></th>
							<th width="120px"><spring:message code="ess.infoApply.confirm_date" /><!-- 确认日期 --></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${viewShopDetailConfirmList}" var="item" varStatus="i">
							<tr target="SEQ" rel="${item.SEQ}">
								<td class='td_center'>${i.count}</td>
								<td class='td_center'>${item.EMPID}</td>
								<td class='td_center'>${item.LOCAL_NAME}</td>
							 	<td class='td_center'>${item.SHOP_DEPT_NAME}</td>
								<td class='td_center'>${item.AR_DATE_STR}</td>
								<td class='td_center' sysLog="activity" sysActivity="${item.ACTIVITY}" sysDeptNo="${item.DEPTNO }" sysPost="${item.POST_FAMILY}" sysDutyNo="${item.DUTY_NO}" sysIndex="${i.index}">
									<c:if test="${item.ACTIVITY eq '0' or item.ACTIVITY eq '1' }">
										<input type="checkbox" id="BATCH_SHOP_CONFIRM_${i.index}" sysPersonId="${item.PERSON_ID}" sysArDateStr="${item.AR_DATE_STR}" name="BATCH_SHOP_CONFIRM" value="${i.index}" />
									</c:if>
								</td>
								<td class='td_center'>${item.IN_MAC_TIME}</td>
								<td class='td_center'>${item.OUT_MAC_TIME}</td>
								<td class='td_center'>${item.SHIFT_FROM_TIME}</td>
								<td class='td_center'>${item.SHIFT_TO_TIME}</td>
								<td class='td_center'>
										${item.MINUS_LENGTH}
								</td>
								<td class='td_center' sysType="CONFIRM_FROM_TIME" sysLog="text" sysIndex="${i.index}" id="CONFIRM_FROM_TIME_${i.index}">${item.CONFIRM_FROM_TIME}</td>
								<td class='td_center' sysType="CONFIRM_TO_TIME" sysLog="text" sysIndex="${i.index}" id="CONFIRM_TO_TIME_${i.index}">${item.CONFIRM_TO_TIME}</td>
								<td class='td_center' sysType="MINUS_LENGTH" sysLog="text" sysIndex="${i.index}" id="MINUS_LENGTH_${i.index}">${item.CONFIRM_MINUS_LENGTH}</td>
								<td class='td_center' sysLog="select" sysValue='${shiftItem }' sysIndex="${i.index}" id="ITEM_NO_${i.index}">${item.ITEM_NAME}</td>
								<td sysType="REMARK" sysLog="text" sysIndex="${i.index}" id="REMARK_${i.index}">${item.REMARK}</td>
								<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
								<td sysType="ALLOWANCE" sysLog="text" sysIndex="${i.index}" id="ALLOWANCE_${i.index}">${item.ALLOWANCE}</td>
								<td sysType="TENGXUN_OT" sysLog="text" sysIndex="${i.index}" id="TENGXUN_OT_${i.index}">${item.TENGXUN_OT}</td>
								<%-- <td sysType="ALLOWANCE" sysLog="text" sysIndex="${i.index}" id="P_ACTIVITY_FEE_${i.index}">${item.P_ACTIVITY_FEE}</td>
								<td sysType="ALLOWANCE" sysLog="text" sysIndex="${i.index}" id="P_SHOP_SUB_${i.index}">${item.P_SHOP_SUB}</td>
								<td sysType="ALLOWANCE" sysLog="text" sysIndex="${i.index}" id="P_HEALTH_BT_${i.index}">${item.P_HEALTH_BT}</td>
								<td sysType="ALLOWANCE" sysLog="text" sysIndex="${i.index}" id="P_PINZHI_BT_${i.index}">${item.P_PINZHI_BT}</td>
								<td sysType="ALLOWANCE" sysLog="text" sysIndex="${i.index}" id="P_ZHIWU_BT_${i.index}">${item.P_ZHIWU_BT}</td>
								<td sysType="ALLOWANCE" sysLog="text" sysIndex="${i.index}" id="P_SHENGCHAN_BT_${i.index}">${item.P_SHENGCHAN_BT}</td>
								<td sysType="ALLOWANCE" sysLog="text" sysIndex="${i.index}" id="P_SPCIALSHOP_SUB_${i.index}">${item.P_SPCIALSHOP_SUB}</td>
								<td sysType="ALLOWANCE" sysLog="text" sysIndex="${i.index}" id="P_MULTIFUNCTIONAL_SUB_${i.index}">${item.P_MULTIFUNCTIONAL_SUB}</td>
								<td sysType="ALLOWANCE" sysLog="text" sysIndex="${i.index}" id="LUNCH_ALLOWANCE_${i.index}">${item.LUNCH_ALLOWANCE}</td>
								<td sysType="ALLOWANCE" sysLog="text" sysIndex="${i.index}" id="P_GAOWEN_BT_${i.index}">${item.P_GAOWEN_BT}</td>
								<td sysType="ALLOWANCE" sysLog="text" sysIndex="${i.index}" id="P_LENGDONG_BT_${i.index}">${item.P_LENGDONG_BT}</td>
								<td sysType="ALLOWANCE" sysLog="text" sysIndex="${i.index}" id="P_YEBU_BT_${i.index}">${item.P_YEBU_BT}</td> --%>
								</c:if>
								<td class='td_center'><img src="/resources/images/${item.ACTIVITY}.gif"></img></td>
								<td class='td_center'>${item.CONFIRM_BY}</td>
								<td class='td_center'>${item.CONFIRM_DATE}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
		</div>
