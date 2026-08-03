<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
$(document).ready(function(){

	$("[sysLog='modifyFlag']",navTab.getCurrentPanel()).each(function(i, obj){
		var index = $(obj).attr("sysIndex");
		var sysWeek = $(obj).attr("sysWeek");
		var item = $("#ITEM_NO_" + sysWeek + "_" + index,navTab.getCurrentPanel()).html();
		var sysDateStr = $(obj).attr("sysDateStr");
	 	var sysActivity = $("#ITEM_NO_" + sysWeek + "_" + index,navTab.getCurrentPanel()).attr("sysActivity");
		if(item == '休息'){
			$("td[sysWeek='" + sysWeek + "'][sysIndex='" + index + "']",navTab.getCurrentPanel()).css("background-color","red");
		}
		if(sysDateStr == '1990/01/14'){
			$("td[sysWeek='" + sysWeek + "'][sysIndex='" + index + "']",navTab.getCurrentPanel()).css("background-color","#708090");
			$("td[sysWeek='" + sysWeek + "'][sysIndex='" + index + "']",navTab.getCurrentPanel()).attr("sysLog","");
		}
		if(sysActivity == '2'){
			$("td[sysWeek='" + sysWeek + "'][sysIndex='" + index + "']",navTab.getCurrentPanel()).css("background-color","#708090");
		}
		if(sysActivity != '0' && sysActivity != ''){
			$("td[sysWeek='" + sysWeek + "'][sysIndex='" + index + "']",navTab.getCurrentPanel()).attr("sysLog","");
		}
		
	});
	
	//查询
	$("#viewFactoryShiftList_Serch",navTab.getCurrentPanel()).click(function(){
		$("#viewFactoryShiftListForm",navTab.getCurrentPanel()).submit();
	});
	//上星期
	$("#viewFactoryShiftList_up",navTab.getCurrentPanel()).click(function(){
		var date = $("#changeFlag_0",navTab.getCurrentPanel()).attr("sysDateStr");
		$.ajax({
			type: 'POST',
			url: '/hrm/recruitManage/doSql',
			data:{sql:"select TO_CHAR(TO_DATE('" + date + "','YYYY/MM/DD') - 1,'YYYY/MM/DD') DATE_STR from dual"},
			dataType:"json",
			cache: false,
			success: function(data){
				$("#DDATE_STR",navTab.getCurrentPanel()).val(data.result[0].DATE_STR);
				$("#viewFactoryShiftListForm",navTab.getCurrentPanel()).submit();
			},
			error: DWZ.ajaxError
		});
	});
	//下星期
	$("#viewFactoryShiftList_down",navTab.getCurrentPanel()).click(function(){
		var date = $("#changeFlag_6",navTab.getCurrentPanel()).attr("sysDateStr");
		$.ajax({
			type: 'POST',
			url: '/hrm/recruitManage/doSql',
			data:{sql:"select TO_CHAR(TO_DATE('" + date + "','YYYY/MM/DD') + 1,'YYYY/MM/DD') DATE_STR from dual"},
			dataType:"json",
			cache: false,
			success: function(data){
				$("#DDATE_STR",navTab.getCurrentPanel()).val(data.result[0].DATE_STR);
				$("#viewFactoryShiftListForm",navTab.getCurrentPanel()).submit();
			},
			error: DWZ.ajaxError
		});
	});
	//保存
	$("#viewFactoryShiftList_Save",navTab.getCurrentPanel()).click(function(){	
		//获取页面的值
		var jsonData = '[';
		$("[sysLog='modifyFlag']",navTab.getCurrentPanel()).each(function(i, obj){
			if($(obj).html() == "modify"){
				if (jsonData.length > 1) {
					jsonData += ',{';
				} else {
					jsonData += '{';
				}
				var index = $(obj).attr("sysIndex");
				var sysWeek = $(obj).attr("sysWeek");
				
				jsonData += ' "SHIFT_FROM_TIME": "' + $("#SHIFT_FROM_TIME_" + sysWeek + "_" + index,navTab.getCurrentPanel()).html() + '" ,';
				jsonData += ' "SHIFT_TO_TIME": "' + $("#SHIFT_TO_TIME_" + sysWeek + "_" + index,navTab.getCurrentPanel()).html() + '" ,';
				jsonData += ' "WORKING_HOUR": "' + $("#WORKING_HOURS_" + sysWeek + "_" + index,navTab.getCurrentPanel()).html() + '" ,';
				jsonData += ' "MINUS_LENGTH": "' + $("#MINUS_LENGTH_" + sysWeek + "_" + index,navTab.getCurrentPanel()).html() + '" ,';
				jsonData += ' "PERSON_ID": "' + $("#PERSON_ID_" + index,navTab.getCurrentPanel()).val() + '" ,';
				jsonData += ' "AR_DATE_STR": "' + $(obj).attr("sysDateStr") + '" ,';
				jsonData += ' "ITEM_NO": "' + $("#ITEM_NO_" + sysWeek + "_" + index,navTab.getCurrentPanel()).html() + '" ,';
				jsonData += ' "adminID": "' + '${LoginUser.adminID}' + '" ,';
				jsonData += ' "adminIP": "' + '${LoginUser.adminIP}' + '" ,';
				jsonData += ' "interCpnyID": "' + '${LoginUser.cpnyId}' + '" ';
				
				jsonData += '}';
			}
		});
		jsonData += ']';
		
		if (jsonData.length == 2) {
			alertMsg.info("没有需要保存的数据");
			return;
		}
		alertMsg.confirm("确定要保存吗？",
	  		  	{okCall:function(){
			  	$.ajax({
	  				type: 'POST',
					url: '/ess/tempEmp/addShopShift?typeFlag=factory',
					data: [{ name: 'jsonData', value: jsonData }],
	  				dataType:"json",
	  				cache: false,
	  				success: navTabAjaxDoneWithForm,
	  				error: DWZ.ajaxError
	  			});
	  	}});
	});

	$('.orderList',navTab.getCurrentPanel()).on( 'draw.dt', function () {
		initEditFun_ess3423();
	});
	
	initEditFun_ess3423();
	
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
	    "scrollY": $(document.body).height() - 350,
	    "scrollX": $(document.body).width(),
	    "scrollCollapse": false,
	    "deferRender":true,
        "columnDefs": [//自定义排序类型
	                     { "orderable": false, "targets": [0,5] }
                     ],
        "fixedColumns":{leftColumns: 6},
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
    //全部反应
	$("#viewFactoryShiftList_fill",navTab.getCurrentPanel()).click(function(){
		var week = $("#DDATE_STR_FILL",navTab.getCurrentPanel()).val();
		var fromTime = $("#SHIFT_FROM_TIME_FILL",navTab.getCurrentPanel()).val();
		var toTime = $("#SHIFT_TO_TIME_FILL",navTab.getCurrentPanel()).val();
	 	var minusLen = $("#MINUS_LENGTH_FILL",navTab.getCurrentPanel()).val();
	 	var item = $("#ITEM_FILL",navTab.getCurrentPanel()).val();
		var workingHour = $("#WORKING_HOURS_FILL",navTab.getCurrentPanel()).val();
		
		$("input[name='BATCH_FACTORY_SHIFT']",navTab.getCurrentPanel()).each(function(i, obj){
			if(obj.checked){
				var index = $(obj).val();
				$("#SHIFT_FROM_TIME_" + week + "_" + index,navTab.getCurrentPanel()).html(fromTime);
				$("#SHIFT_TO_TIME_" + week + "_" + index,navTab.getCurrentPanel()).html(toTime);
				$("#WORKING_HOURS_" + week + "_" + index,navTab.getCurrentPanel()).html(workingHour);
				$("#MINUS_LENGTH_" + week + "_" + index,navTab.getCurrentPanel()).html(minusLen);
				$("#ITEM_NO_" + week + "_" + index,navTab.getCurrentPanel()).html(item);
				$("#modifyFlag_" + week + "_" + index,navTab.getCurrentPanel()).html("modify");
				if(item == "休息"){
					$("td[sysWeek='" + week + "'][sysIndex='" + index + "']",navTab.getCurrentPanel()).css("background-color","red");
				}else{
					$("td[sysWeek='" + week + "'][sysIndex='" + index + "']",navTab.getCurrentPanel()).css("background-color","");
				}
			}
		});
	});

	$("#SHIFT_FROM_TIME_FILL,#SHIFT_TO_TIME_FILL",navTab.getCurrentPanel()).blur(function(){
		var val = composeTime($(this).val());
		if(val == ":"){
			$(this).val("");
			return false;
		}
		$(this).val(val);
		var sysType = $(this).attr("id");
		var minusLen = 0;
		var workingHours = 0;
		if(sysType == 'SHIFT_FROM_TIME_FILL'){
			if(val != ''){
				if($("#SHIFT_TO_TIME_FILL",navTab.getCurrentPanel()).val() == ''){
					$("#SHIFT_TO_TIME_FILL",navTab.getCurrentPanel()).val( addTime(val , 9) );
					minusLen = 0;
					workingHours = 8;
				}else{
					var shiftToTime = $("#SHIFT_TO_TIME_FILL",navTab.getCurrentPanel()).val();
					minusLen = addTime2(shiftToTime, val) - 9;
					workingHours = addTime2(shiftToTime, val) - 1;
				}
			}
		}else if(sysType == 'SHIFT_TO_TIME_FILL'){
			if(val != ''){
				if($("#SHIFT_FROM_TIME_FILL",navTab.getCurrentPanel()).val() == ''){
					$("#SHIFT_FROM_TIME_FILL",navTab.getCurrentPanel()).val( addTime3(val, 9) );
					minusLen = 0;
					workingHours = 8;
				}else{
					var shiftFromTime = $("#SHIFT_FROM_TIME_FILL",navTab.getCurrentPanel()).val();
					minusLen = addTime2(val, shiftFromTime) - 9;
					workingHours = addTime2(val, shiftFromTime) - 1;
				}
			}
		}else if(sysType == 'MINUS_LENGTH'){
			minusLen = parseFloat(val);
		}

		if(val != ''){
			$("#MINUS_LENGTH_FILL",navTab.getCurrentPanel()).val(minusLen);
			$("#WORKING_HOURS_FILL",navTab.getCurrentPanel()).val(workingHours);
			if(minusLen > 0){
				$("#ITEM_FILL",navTab.getCurrentPanel()).val( "加班" );
			}else if(minusLen == 0){
				$("#ITEM_FILL",navTab.getCurrentPanel()).val( "正常出勤" );
			}else{
				$("#ITEM_FILL",navTab.getCurrentPanel()).val( "早退" );
			}
		}
	});
	
});

function initEditFun_ess3423(){
	$('.orderList tbody tr td:[sysLog="text"]',navTab.getCurrentPanel()).editable({type:'text',
		onblur:function(val,settings){
			var index = $(this).attr("sysIndex");
			var sysWeek = $(this).attr("sysWeek");
			var sysType = $(this).attr("sysType");
			if(sysType != 'MINUS_LENGTH' && sysType != 'WORKING_HOURS'){
				val = composeTime(val);
				if(val == ":"){
		    		$(this).html("");
					this.editing = false;
					return false;
				}
			}
			$(this).html(val);
			var minusLen = 0;
			var workingHours = 0;
			if(sysType == 'SHIFT_FROM_TIME'){
				if(val != ''){
					if($("#SHIFT_TO_TIME_" + sysWeek + "_" + index,navTab.getCurrentPanel()).html() == ''){
						$("#SHIFT_TO_TIME_" + sysWeek + "_" + index,navTab.getCurrentPanel()).html( addTime(val, 9 ));
						minusLen = 0;
						workingHours = 8;
					}else{
						var shiftToTime = $("#SHIFT_TO_TIME_" + sysWeek + "_" + index,navTab.getCurrentPanel()).html();
						minusLen = addTime2(shiftToTime, val) - 9;
						workingHours = addTime2(shiftToTime, val) - 1;
					}
				}
			}else if(sysType == 'SHIFT_TO_TIME'){
				if(val != ''){
					if($("#SHIFT_FROM_TIME_" + sysWeek + "_" + index,navTab.getCurrentPanel()).html() == ''){
						$("#SHIFT_FROM_TIME_" + sysWeek + "_" + index,navTab.getCurrentPanel()).html( addTime3(val, 9) );
						minusLen = 0;
						workingHours = 8;
					}else{
						var shiftFromTime = $("#SHIFT_FROM_TIME_" + sysWeek + "_" + index,navTab.getCurrentPanel()).html();
						minusLen = addTime2(val, shiftFromTime) - 9;
						workingHours = addTime2(val, shiftFromTime) - 1;
					}
				}
			}else if(sysType == 'MINUS_LENGTH'){
				minusLen = parseFloat(val);
			}else if(sysType == 'WORKING_HOURS'){
				minusLen = parseFloat(val) - 8;
				workingHours = parseFloat(val);
			}
			if(val != ''){
				if(sysType != 'MINUS_LENGTH'){
					$("#MINUS_LENGTH_" + sysWeek + "_" + index,navTab.getCurrentPanel()).html(minusLen);
					$("#WORKING_HOURS_" + sysWeek + "_" + index,navTab.getCurrentPanel()).html(workingHours);
					if(minusLen > 0){
						$("#ITEM_NO_" + sysWeek + "_" + index,navTab.getCurrentPanel()).html( "加班" );
					}else if(minusLen == 0){
						$("#ITEM_NO_" + sysWeek + "_" + index,navTab.getCurrentPanel()).html( "正常出勤" );
					}else{
						$("#ITEM_NO_" + sysWeek + "_" + index,navTab.getCurrentPanel()).html( "早退" );
					}
				}
			}
			$("#modifyFlag_" + sysWeek + "_" + index,navTab.getCurrentPanel()).html("modify");
			this.editing = false;
		}
	});

	$('.orderList tbody tr td:[sysLog="select"]',navTab.getCurrentPanel()).editable({type:'select',
		onblur:function(val,settings){
			$(this).html(val);
			var index = $(this).attr("sysIndex");
			var sysWeek = $(this).attr("sysWeek");
			if(val == '休息'){
				$("td[sysWeek='" + sysWeek + "'][sysIndex='" + index + "']",navTab.getCurrentPanel()).css("background-color","red");
			}else{
				$("td[sysWeek='" + sysWeek + "'][sysIndex='" + index + "']",navTab.getCurrentPanel()).css("background-color","");
			}
			$("#modifyFlag_" + sysWeek + "_" + index,navTab.getCurrentPanel()).html("modify");
			this.editing = false;
		}
	});
}
function excelimport_ess3423(){
	$("#importExcelDialog_ess3423").attr('href','/pa/excelImport/importExcelData?importFunName=/importShopShiftTemp&TYPE=ess3423');
	$("#importExcelDialog_ess3423").click();
}
</script>
<a id="importExcelDialog_ess3423"  href="#" target="dialog" mask="true"></a>
<a id="importExcel_ess3423" href="#" target="navTab" mask="true"><span style="display:none;">导入结果</span></a>
<div class="pageHeader">
<form id="viewFactoryShiftListForm" onsubmit="return navTabSearch(this);" action="/ess/tempEmp/viewFactoryShiftList" method="post">
<div class="searchBar">
<table class="searchContent">
	<tr>
		<td>姓名/社号</td>
		<td>
			<input type="text" id="seach_KEY" name="seach_KEY" value="${KEY}">
		</td>
		<td>部门</td>
		<td>
			<ait:deptList name="seach_DEPTNO" limit="manager" id="viewFactoryShiftList_deptList" />
			<ait:deptTreeIcon name="seach_DEPTNO" limit="manager" id="viewFactoryShiftList_deptList" selected="${DEPTNO}"/>
		</td>
		<td>员工状态</td>
		<td>
			<ait:SelectSyCodeByCpnyID name="seach_EMP_OFFICE" selected="${EMP_OFFICE}" parentNo="15118" limit="all"/>
		</td>
		<td>考勤日期</td>
		<td>
			<input type="text" id="DDATE_STR" name="seach_DDATE_STR" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy/MM/dd'})" value="${DDATE_STR}"/>
		</td>
	</tr>
</table>
</div>
</form>
</div>

<div class="pageHeader">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>考勤日</td>
				<td>
					<select id="DDATE_STR_FILL">
						<option value="1">星期一</option>
						<option value="2">星期二</option>
						<option value="3">星期三</option>
						<option value="4">星期四</option>
						<option value="5">星期五</option>
						<option value="6">星期六</option>
						<option value="0">星期日</option>
					</select>
				</td>
				<td>上班时间</td>
				<td>
					<input type="text" id="SHIFT_FROM_TIME_FILL" size="5">
				</td>
				<td>下班时间</td>
				<td>
					<input type="text" id="SHIFT_TO_TIME_FILL" size="5">
				</td>
				<td>出勤时间</td>
				<td>
					<input type="text" id="WORKING_HOURS_FILL" size="5">
				</td>
				<td>差异</td>
				<td>
					<input type="text" id="MINUS_LENGTH_FILL" size="5">
				</td>
				<td>考勤状态</td>
				<td>
					<select id="ITEM_FILL">
						<c:forEach items="${shiftNoItem}" var="item" varStatus="i">
						<option value="${item.CODENAME }">${item.CODENAME }</option>
						</c:forEach>
					</select>
				</td>
			</tr>
		</table>
			<div class="subBar">
				<ul class="toolBar">
	             <li>
	            	 <a class="buttonActive" id="viewFactoryShiftList_fill"><span>全部反应</span></a>
	             </li>
				</ul>
			</div>
	</div>
</div>

<div class="formBar">
	<ul class="toolBar">
		<li><a class="buttonActive" id="viewFactoryShiftList_Serch" href="#"><span>查询</span></a></li>
		<li><a class="buttonActive" id="viewFactoryShiftList_up" href="#"><span>上星期</span></a></li>
		<li><a class="buttonActive" id="viewFactoryShiftList_down" href="#"><span>下星期</span></a></li>
		<li><a class="buttonActive" href="/pa/excelExport/downloadShopShiftExcelTemplate?file=FactoryShiftAction_Add&EMP_TYPE=FACTORY"><span>模板下载</span></a></li>
		<li><a class="buttonActive" href="#" onclick="excelimport_ess3423();"><span>Excel导入</span></a></li>
		<li><a class="buttonActive" href="#" onclick="downloadExcel('viewFactoryShiftListForm','/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=220','/ess/tempEmp/viewFactoryShiftList')"><span>Excel导出</span></a></li>
		<li><a class="buttonActive" id="viewFactoryShiftList_Save" href="#"><span>保存</span></a></li>
	</ul>
</div>
<div class="pageContent">
				<table class="orderList" width="1940px">
					<thead>
						<tr>
							<th width="30px" rowspan="2">NO.</th>
							<th width="50px" rowspan="2">社号</th>
							<th width="50px" rowspan="2">姓名</th>
							<th width="80px" rowspan="2">部门</th>
							<th width="50px" rowspan="2">调休</th>
							<th width="30px" rowspan="2">
						    	<input type="checkbox" class="checkboxCtrl" group="BATCH_FACTORY_SHIFT" />
						    </th>
							<c:forEach items="${viewWeekList}" var="item" varStatus="i">
								<th width="250px" colspan="5" id="changeFlag_${i.index }" sysDateStr="${item.DDATE_STR }">
									<c:if test="${item.TYPEID eq 1442}"><font color="red">${item.DDATE_TITLE }</font></c:if>
									<c:if test="${item.TYPEID ne 1442}">${item.DDATE_TITLE }</c:if>
								</th>
							</c:forEach>
						</tr>
						<tr>
							<c:forEach items="${viewWeekList}" var="item" varStatus="i">
								<c:if test="${item.TYPEID eq 1442}">
									<th><font color="red">上班</font></th>
									<th><font color="red">下班</font></th>
									<th><font color="red">出勤时间</font></th>
									<th><font color="red">差异</font></th>
									<th><font color="red">考勤状态</font></th>
								</c:if>
								<c:if test="${item.TYPEID ne 1442}">
									<th>上班</th>
									<th>下班</th>
									<th>出勤时间</th>
									<th>差异</th>
									<th>考勤状态</th>
								</c:if>
							</c:forEach>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${viewFactoryShiftList}" var="item" varStatus="i">
							<tr target="SEQ" rel="${item.SEQ}">
								<td class='td_center'>${i.count}
									<div id="modifyFlag_1_${i.index}" sysWeek="1" sysLog="modifyFlag" sysIndex="${i.index}" sysDateStr="${item.DDATE_STR_1}" style="display:none;"></div>
									<div id="modifyFlag_2_${i.index}" sysWeek="2" sysLog="modifyFlag" sysIndex="${i.index}" sysDateStr="${item.DDATE_STR_2}" style="display:none;"></div>
									<div id="modifyFlag_3_${i.index}" sysWeek="3" sysLog="modifyFlag" sysIndex="${i.index}" sysDateStr="${item.DDATE_STR_3}" style="display:none;"></div>
									<div id="modifyFlag_4_${i.index}" sysWeek="4" sysLog="modifyFlag" sysIndex="${i.index}" sysDateStr="${item.DDATE_STR_4}" style="display:none;"></div>
									<div id="modifyFlag_5_${i.index}" sysWeek="5" sysLog="modifyFlag" sysIndex="${i.index}" sysDateStr="${item.DDATE_STR_5}" style="display:none;"></div>
									<div id="modifyFlag_6_${i.index}" sysWeek="6" sysLog="modifyFlag" sysIndex="${i.index}" sysDateStr="${item.DDATE_STR_6}" style="display:none;"></div>
									<div id="modifyFlag_0_${i.index}" sysWeek="0" sysLog="modifyFlag" sysIndex="${i.index}" sysDateStr="${item.DDATE_STR_0}" style="display:none;"></div>
									<input type="hidden" id="PERSON_ID_${i.index}" value="${item.PERSON_ID }"/>
								</td>
								<td class='td_center'>${item.EMPID}</td>
								<td class='td_center'>${item.LOCAL_NAME}</td>
								<td class='td_center'>${item.DEPT_NAME}</td>
								<td class='td_center'>${item.TX_LEAVE}</td>
							    <td style="text-align: center">
							        <input type="checkbox" id="BATCH_FACTORY_SHIFT_${i.index}" name="BATCH_FACTORY_SHIFT" value="${i.index}" />
							    </td>
							 	<td class='td_center' sysWeek="1" sysType="SHIFT_FROM_TIME" <c:if test="${item.ACTIVITY_1 ne '2'}">sysLog="text"</c:if> sysIndex="${i.index}" id="SHIFT_FROM_TIME_1_${i.index}" sysDateStr="${item.DDATE_STR_1}">${item.SHIFT_FROM_TIME_1}</td>
							 	<td class='td_center' sysWeek="1" sysType="SHIFT_TO_TIME" <c:if test="${item.ACTIVITY_1 ne '2'}">sysLog="text"</c:if> sysIndex="${i.index}" id="SHIFT_TO_TIME_1_${i.index}">${item.SHIFT_TO_TIME_1}</td>
							 	<td class='td_center' sysWeek="1" sysType="WORKING_HOURS" <c:if test="${item.ACTIVITY_1 ne '2'}">sysLog="text"</c:if> sysIndex="${i.index}" id="WORKING_HOURS_1_${i.index}">${item.WORKING_HOURS_1}</td>
							 	<td class='td_center' sysWeek="1" sysType="MINUS_LENGTH" <c:if test="${item.ACTIVITY_1 ne '2'}">sysLog="text"</c:if> sysIndex="${i.index}" id="MINUS_LENGTH_1_${i.index}">${item.MINUS_LENGTH_1}</td>
							 	<td class='td_center' sysWeek="1" sysActivity="${item.ACTIVITY_1}" sysType="ITEM_NO" <c:if test="${item.ACTIVITY_1 ne '2'}">sysLog="select"</c:if> sysValue='${shiftItem }' sysIndex="${i.index}" id="ITEM_NO_1_${i.index}">${item.ITEM_NO_1}<c:if test="${item.ACTIVITY_1 eq '2'}"><br/>审批中</c:if></td>
							 	
							 	<td class='td_center' sysWeek="2" sysType="SHIFT_FROM_TIME" <c:if test="${item.ACTIVITY_2 ne '2'}">sysLog="text"</c:if> sysIndex="${i.index}" id="SHIFT_FROM_TIME_2_${i.index}" sysDateStr="${item.DDATE_STR_2}">${item.SHIFT_FROM_TIME_2}</td>
							 	<td class='td_center' sysWeek="2" sysType="SHIFT_TO_TIME" <c:if test="${item.ACTIVITY_2 ne '2'}">sysLog="text"</c:if> sysIndex="${i.index}" id="SHIFT_TO_TIME_2_${i.index}">${item.SHIFT_TO_TIME_2}</td>
							 	<td class='td_center' sysWeek="2" sysType="WORKING_HOURS" <c:if test="${item.ACTIVITY_2 ne '2'}">sysLog="text"</c:if> sysIndex="${i.index}" id="WORKING_HOURS_2_${i.index}">${item.WORKING_HOURS_2}</td>
							 	<td class='td_center' sysWeek="2" sysType="MINUS_LENGTH" <c:if test="${item.ACTIVITY_2 ne '2'}">sysLog="text"</c:if> sysIndex="${i.index}" id="MINUS_LENGTH_2_${i.index}">${item.MINUS_LENGTH_2}</td>
							 	<td class='td_center' sysWeek="2" sysActivity="${item.ACTIVITY_2}" sysType="ITEM_NO" <c:if test="${item.ACTIVITY_2 ne '2'}">sysLog="select"</c:if> sysValue='${shiftItem }' sysIndex="${i.index}" id="ITEM_NO_2_${i.index}">${item.ITEM_NO_2}<c:if test="${item.ACTIVITY_2 eq '2'}"><br/>审批中</c:if></td>
							 	
							 	<td class='td_center' sysWeek="3" sysType="SHIFT_FROM_TIME" <c:if test="${item.ACTIVITY_3 ne '2'}">sysLog="text"</c:if> sysIndex="${i.index}" id="SHIFT_FROM_TIME_3_${i.index}" sysDateStr="${item.DDATE_STR_3}">${item.SHIFT_FROM_TIME_3}</td>
							 	<td class='td_center' sysWeek="3" sysType="SHIFT_TO_TIME" <c:if test="${item.ACTIVITY_3 ne '2'}">sysLog="text"</c:if> sysIndex="${i.index}" id="SHIFT_TO_TIME_3_${i.index}">${item.SHIFT_TO_TIME_3}</td>
							 	<td class='td_center' sysWeek="3" sysType="WORKING_HOURS" <c:if test="${item.ACTIVITY_3 ne '2'}">sysLog="text"</c:if> sysIndex="${i.index}" id="WORKING_HOURS_3_${i.index}">${item.WORKING_HOURS_3}</td>
							 	<td class='td_center' sysWeek="3" sysType="MINUS_LENGTH" <c:if test="${item.ACTIVITY_3 ne '2'}">sysLog="text"</c:if> sysIndex="${i.index}" id="MINUS_LENGTH_3_${i.index}">${item.MINUS_LENGTH_3}</td>
							 	<td class='td_center' sysWeek="3" sysActivity="${item.ACTIVITY_3}" sysType="ITEM_NO" <c:if test="${item.ACTIVITY_3 ne '2'}">sysLog="select"</c:if> sysValue='${shiftItem }' sysIndex="${i.index}" id="ITEM_NO_3_${i.index}">${item.ITEM_NO_3}<c:if test="${item.ACTIVITY_3 eq '2'}"><br/>审批中</c:if></td>
							 	
							 	<td class='td_center' sysWeek="4" sysType="SHIFT_FROM_TIME" <c:if test="${item.ACTIVITY_4 ne '2'}">sysLog="text"</c:if> sysIndex="${i.index}" id="SHIFT_FROM_TIME_4_${i.index}" sysDateStr="${item.DDATE_STR_4}">${item.SHIFT_FROM_TIME_4}</td>
							 	<td class='td_center' sysWeek="4" sysType="SHIFT_TO_TIME" <c:if test="${item.ACTIVITY_4 ne '2'}">sysLog="text"</c:if> sysIndex="${i.index}" id="SHIFT_TO_TIME_4_${i.index}">${item.SHIFT_TO_TIME_4}</td>
							 	<td class='td_center' sysWeek="4" sysType="WORKING_HOURS" <c:if test="${item.ACTIVITY_4 ne '2'}">sysLog="text"</c:if> sysIndex="${i.index}" id="WORKING_HOURS_4_${i.index}">${item.WORKING_HOURS_4}</td>
							 	<td class='td_center' sysWeek="4" sysType="MINUS_LENGTH" <c:if test="${item.ACTIVITY_4 ne '2'}">sysLog="text"</c:if> sysIndex="${i.index}" id="MINUS_LENGTH_4_${i.index}">${item.MINUS_LENGTH_4}</td>
							 	<td class='td_center' sysWeek="4" sysActivity="${item.ACTIVITY_4}" sysType="ITEM_NO" <c:if test="${item.ACTIVITY_4 ne '2'}">sysLog="select"</c:if> sysValue='${shiftItem }' sysIndex="${i.index}" id="ITEM_NO_4_${i.index}">${item.ITEM_NO_4}<c:if test="${item.ACTIVITY_4 eq '2'}"><br/>审批中</c:if></td>
							 	
							 	<td class='td_center' sysWeek="5" sysType="SHIFT_FROM_TIME" <c:if test="${item.ACTIVITY_5 ne '2'}">sysLog="text"</c:if> sysIndex="${i.index}" id="SHIFT_FROM_TIME_5_${i.index}" sysDateStr="${item.DDATE_STR_5}">${item.SHIFT_FROM_TIME_5}</td>
							 	<td class='td_center' sysWeek="5" sysType="SHIFT_TO_TIME" <c:if test="${item.ACTIVITY_5 ne '2'}">sysLog="text"</c:if> sysIndex="${i.index}" id="SHIFT_TO_TIME_5_${i.index}">${item.SHIFT_TO_TIME_5}</td>
							 	<td class='td_center' sysWeek="5" sysType="WORKING_HOURS" <c:if test="${item.ACTIVITY_5 ne '2'}">sysLog="text"</c:if> sysIndex="${i.index}" id="WORKING_HOURS_5_${i.index}">${item.WORKING_HOURS_5}</td>
							 	<td class='td_center' sysWeek="5" sysType="MINUS_LENGTH" <c:if test="${item.ACTIVITY_5 ne '2'}">sysLog="text"</c:if> sysIndex="${i.index}" id="MINUS_LENGTH_5_${i.index}">${item.MINUS_LENGTH_5}</td>
							 	<td class='td_center' sysWeek="5" sysActivity="${item.ACTIVITY_5}" sysType="ITEM_NO" <c:if test="${item.ACTIVITY_5 ne '2'}">sysLog="select"</c:if> sysValue='${shiftItem }' sysIndex="${i.index}" id="ITEM_NO_5_${i.index}">${item.ITEM_NO_5}<c:if test="${item.ACTIVITY_5 eq '2'}"><br/>审批中</c:if></td>
							 	
							 	<td class='td_center' sysWeek="6" sysType="SHIFT_FROM_TIME" <c:if test="${item.ACTIVITY_6 ne '2'}">sysLog="text"</c:if> sysIndex="${i.index}" id="SHIFT_FROM_TIME_6_${i.index}" sysDateStr="${item.DDATE_STR_6}">${item.SHIFT_FROM_TIME_6}</td>
							 	<td class='td_center' sysWeek="6" sysType="SHIFT_TO_TIME" <c:if test="${item.ACTIVITY_6 ne '2'}">sysLog="text"</c:if> sysIndex="${i.index}" id="SHIFT_TO_TIME_6_${i.index}">${item.SHIFT_TO_TIME_6}</td>
							 	<td class='td_center' sysWeek="6" sysType="WORKING_HOURS" <c:if test="${item.ACTIVITY_6 ne '2'}">sysLog="text"</c:if> sysIndex="${i.index}" id="WORKING_HOURS_6_${i.index}">${item.WORKING_HOURS_6}</td>
							 	<td class='td_center' sysWeek="6" sysType="MINUS_LENGTH" <c:if test="${item.ACTIVITY_6 ne '2'}">sysLog="text"</c:if> sysIndex="${i.index}" id="MINUS_LENGTH_6_${i.index}">${item.MINUS_LENGTH_6}</td>
							 	<td class='td_center' sysWeek="6" sysActivity="${item.ACTIVITY_6}" sysType="ITEM_NO" <c:if test="${item.ACTIVITY_6 ne '2'}">sysLog="select"</c:if> sysValue='${shiftItem }' sysIndex="${i.index}" id="ITEM_NO_6_${i.index}">${item.ITEM_NO_6}<c:if test="${item.ACTIVITY_6 eq '2'}"><br/>审批中</c:if></td>
							 	
							 	<td class='td_center' sysWeek="0" sysType="SHIFT_FROM_TIME" <c:if test="${item.ACTIVITY_0 ne '2'}">sysLog="text"</c:if> sysIndex="${i.index}" id="SHIFT_FROM_TIME_0_${i.index}" sysDateStr="${item.DDATE_STR_0}">${item.SHIFT_FROM_TIME_0}</td>
							 	<td class='td_center' sysWeek="0" sysType="SHIFT_TO_TIME" <c:if test="${item.ACTIVITY_0 ne '2'}">sysLog="text"</c:if> sysIndex="${i.index}" id="SHIFT_TO_TIME_0_${i.index}">${item.SHIFT_TO_TIME_0}</td>
							 	<td class='td_center' sysWeek="0" sysType="WORKING_HOURS" <c:if test="${item.ACTIVITY_0 ne '2'}">sysLog="text"</c:if> sysIndex="${i.index}" id="WORKING_HOURS_0_${i.index}">${item.WORKING_HOURS_0}</td>
							 	<td class='td_center' sysWeek="0" sysType="MINUS_LENGTH" <c:if test="${item.ACTIVITY_0 ne '2'}">sysLog="text"</c:if> sysIndex="${i.index}" id="MINUS_LENGTH_0_${i.index}">${item.MINUS_LENGTH_0}</td>
							 	<td class='td_center' sysWeek="0" sysActivity="${item.ACTIVITY_0}" sysType="ITEM_NO" <c:if test="${item.ACTIVITY_0 ne '2'}">sysLog="select"</c:if> sysValue='${shiftItem }' sysIndex="${i.index}" id="ITEM_NO_0_${i.index}">${item.ITEM_NO_0}<c:if test="${item.ACTIVITY_0 eq '2'}"><br/>审批中</c:if></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
		</div>