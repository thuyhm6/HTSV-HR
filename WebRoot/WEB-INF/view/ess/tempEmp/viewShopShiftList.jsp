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
		if(item == '<spring:message code="ar.viewCompanyCalendar.title.rest" />'){//休息
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
	$("#viewShopShiftList_Serch",navTab.getCurrentPanel()).click(function(){
		$("#viewShopShiftListForm",navTab.getCurrentPanel()).submit();
	});
	//上星期
	$("#viewShopShiftList_up",navTab.getCurrentPanel()).click(function(){
		var date = $("#changeFlag_0",navTab.getCurrentPanel()).attr("sysDateStr");
		$.ajax({
			type: 'POST',
			url: '/hrm/recruitManage/doSql',
			data:{sql:"select TO_CHAR(TO_DATE('" + date + "','YYYY/MM/DD') - 1,'YYYY/MM/DD') DATE_STR from dual"},
			dataType:"json",
			cache: false,
			success: function(data){
				$("#DDATE_STR",navTab.getCurrentPanel()).val(data.result[0].DATE_STR);
				$("#viewShopShiftListForm",navTab.getCurrentPanel()).submit();
			},
			error: DWZ.ajaxError
		});
	});
	//下星期
	$("#viewShopShiftList_down",navTab.getCurrentPanel()).click(function(){
		var date = $("#changeFlag_6",navTab.getCurrentPanel()).attr("sysDateStr");
		$.ajax({
			type: 'POST',
			url: '/hrm/recruitManage/doSql',
			data:{sql:"select TO_CHAR(TO_DATE('" + date + "','YYYY/MM/DD') + 1,'YYYY/MM/DD') DATE_STR from dual"},
			dataType:"json",
			cache: false,
			success: function(data){
				$("#DDATE_STR",navTab.getCurrentPanel()).val(data.result[0].DATE_STR);
				$("#viewShopShiftListForm",navTab.getCurrentPanel()).submit();
			},
			error: DWZ.ajaxError
		});
	});
	//保存
	$("#viewShopShiftList_Save",navTab.getCurrentPanel()).click(function(){	
		//获取页面的值
		var jsonData = '[';
		$("[sysLog='modifyFlag']",navTab.getCurrentPanel()).each(function(i, obj){
			var index = $(obj).attr("sysIndex");
			var sysWeek = $(obj).attr("sysWeek");
			
			if($(obj).html() == "modify" && $("#ITEM_NO_" + sysWeek + "_" + index,navTab.getCurrentPanel()).html() != ""){
				if (jsonData.length > 1) {
					jsonData += ',{';
				} else {
					jsonData += '{';
				}
				jsonData += ' "SHIFT_FROM_TIME": "' + $("#SHIFT_FROM_TIME_" + sysWeek + "_" + index,navTab.getCurrentPanel()).html() + '" ,';
				jsonData += ' "SHIFT_TO_TIME": "' + $("#SHIFT_TO_TIME_" + sysWeek + "_" + index,navTab.getCurrentPanel()).html() + '" ,';
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
			alertMsg.info("<spring:message code='hrm.empinfo.NOTSAVE_DATA' />");//没有需要保存的数据
			return;
		}
		alertMsg.confirm("<spring:message code='ess.message.confirm_sava' />",//确定要保存吗？
	  		  	{okCall:function(){
			  	$.ajax({
	  				type: 'POST',
					url: '/ess/tempEmp/addShopShift?typeFlag=shop',
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
	     "scrollY": $(document.body).height() - 350,
	     "scrollX": $(document.body).width(),
	     "scrollCollapse": false,
	     "deferRender":true,
	        "columnDefs": [//自定义排序类型
		                     { "orderable": false, "targets": [0,3] }
	                     ],
	    "fixedColumns":{leftColumns: 4},
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
		initEditFun_ess3422();
	});

	initEditFun_ess3422();
	
	$("#viewShopShiftList_fill",navTab.getCurrentPanel()).click(function(){
		var week = $("#DDATE_STR_FILL",navTab.getCurrentPanel()).val();
		var fromTime = $("#SHIFT_FROM_TIME_FILL",navTab.getCurrentPanel()).val();
		var toTime = $("#SHIFT_TO_TIME_FILL",navTab.getCurrentPanel()).val();
	 	var minusLen = $("#MINUS_LENGTH_FILL",navTab.getCurrentPanel()).val();
	 	var item = $("#ITEM_FILL",navTab.getCurrentPanel()).val();
		$("input[name='BATCH_SHOP_SHIFT']",navTab.getCurrentPanel()).each(function(i, obj){
			if(obj.checked){
				var index = $(obj).val();
				$("#SHIFT_FROM_TIME_" + week + "_" + index,navTab.getCurrentPanel()).html(fromTime);
				$("#SHIFT_TO_TIME_" + week + "_" + index,navTab.getCurrentPanel()).html(toTime);
				$("#MINUS_LENGTH_" + week + "_" + index,navTab.getCurrentPanel()).html(minusLen);
				$("#ITEM_NO_" + week + "_" + index,navTab.getCurrentPanel()).html(item);
				$("#modifyFlag_" + week + "_" + index,navTab.getCurrentPanel()).html("modify");
				if(item == "<spring:message code='ar.viewCompanyCalendar.title.rest' />"){//休息
					$("td[sysWeek='" + week + "'][sysIndex='" + index + "']",navTab.getCurrentPanel()).css("background-color","red");
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
		if(sysType == 'SHIFT_FROM_TIME_FILL'){
			if(val != ''){
				if($("#SHIFT_TO_TIME_FILL",navTab.getCurrentPanel()).val() == ''){
					$("#SHIFT_TO_TIME_FILL",navTab.getCurrentPanel()).val( addTime(val, 8) );
					minusLen = 0;
				}else{
					var shiftToTime = $("#SHIFT_TO_TIME_FILL",navTab.getCurrentPanel()).val();
					minusLen = addTime2(shiftToTime , val) - 8;
				}
			}
		}else if(sysType == 'SHIFT_TO_TIME_FILL'){
			if(val != ''){
				if($("#SHIFT_FROM_TIME_FILL",navTab.getCurrentPanel()).val() == ''){
					$("#SHIFT_FROM_TIME_FILL",navTab.getCurrentPanel()).val( addTime3(val , 8) );
					minusLen = 0;
				}else{
					var shiftFromTime = $("#SHIFT_FROM_TIME_FILL",navTab.getCurrentPanel()).val();
					minusLen = addTime2(val, shiftFromTime) - 8;
				}
			}
		}else if(sysType == 'MINUS_LENGTH'){
			minusLen = parseFloat(val);
		}
		if(val != ''){
			$("#MINUS_LENGTH_FILL",navTab.getCurrentPanel()).val(minusLen);
			if(minusLen > 0){
				$("#ITEM_FILL",navTab.getCurrentPanel()).val( "<spring:message code='ess.attendance.ot' />" );//加班
			}else if(minusLen == 0){
				$("#ITEM_FILL",navTab.getCurrentPanel()).val( "<spring:message code='ess.infoApply.normal_attendance' />" );//正常出勤
			}else{
				$("#ITEM_FILL",navTab.getCurrentPanel()).val( "<spring:message code='ess.infoApply.leave_early' />" );//早退
			}
		}
	});
});

function initEditFun_ess3422(){
	$('.orderList tbody tr td:[sysLog="text"]',navTab.getCurrentPanel()).editable({type:'text',
		onblur:function(val,settings){
			var index = $(this).attr("sysIndex");
			var sysWeek = $(this).attr("sysWeek");
			var sysType = $(this).attr("sysType");
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
    		
			var minusLen = 0;
			if(sysType == 'SHIFT_FROM_TIME'){
				if(val != ''){
					if($("#SHIFT_TO_TIME_" + sysWeek + "_" + index,navTab.getCurrentPanel()).html() == ''){
						$("#SHIFT_TO_TIME_" + sysWeek + "_" + index,navTab.getCurrentPanel()).html( addTime(val , 8) );
						minusLen = 0;
					}else{
						var shiftToTime = $("#SHIFT_TO_TIME_" + sysWeek + "_" + index,navTab.getCurrentPanel()).html();
						minusLen = addTime2(shiftToTime, val) - 8;
					}
				}
			}else if(sysType == 'SHIFT_TO_TIME'){
				if(val != ''){
					if($("#SHIFT_FROM_TIME_" + sysWeek + "_" + index,navTab.getCurrentPanel()).html() == ''){
						$("#SHIFT_FROM_TIME_" + sysWeek + "_" + index,navTab.getCurrentPanel()).html( addTime3(val, 8) );
						minusLen = 0;
					}else{
						var shiftFromTime = $("#SHIFT_FROM_TIME_" + sysWeek + "_" + index,navTab.getCurrentPanel()).html();
						minusLen = addTime2(val, shiftFromTime) - 8;
					}
				}
			}else if(sysType == 'MINUS_LENGTH'){
				minusLen = parseFloat(val);
			}
			if(val != ''){
				if(sysType != 'MINUS_LENGTH'){
					$("#MINUS_LENGTH_" + sysWeek + "_" + index,navTab.getCurrentPanel()).html(minusLen);
					if(minusLen > 0){
						$("#ITEM_NO_" + sysWeek + "_" + index,navTab.getCurrentPanel()).html( "<spring:message code='ess.attendance.ot' />" );//加班
					}else if(minusLen == 0){
						$("#ITEM_NO_" + sysWeek + "_" + index,navTab.getCurrentPanel()).html( "<spring:message code='ess.infoApply.normal_attendance' />" );//正常出勤
					}else{
						$("#ITEM_NO_" + sysWeek + "_" + index,navTab.getCurrentPanel()).html( "<spring:message code='ess.infoApply.leave_early' />" );//早退
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
			if(val == '<spring:message code="ar.viewCompanyCalendar.title.rest" />'){//休息
				$("td[sysWeek='" + sysWeek + "'][sysIndex='" + index + "']",navTab.getCurrentPanel()).css("background-color","red");
			}else{
				$("td[sysWeek='" + sysWeek + "'][sysIndex='" + index + "']",navTab.getCurrentPanel()).css("background-color","");
			}
			$("#modifyFlag_" + sysWeek + "_" + index,navTab.getCurrentPanel()).html("modify");
			this.editing = false;
		}
	});
}

function excelimport_ess3422(){
	$("#importExcelDialog_ess3422").attr('href','/pa/excelImport/importExcelData?importFunName=/importShopShiftTemp&TYPE=ess3422');
	$("#importExcelDialog_ess3422").click();
}
</script>
<a id="importExcelDialog_ess3422"  href="#" target="dialog" mask="true"></a>
<a id="importExcel_ess3422" href="#" target="navTab" mask="true"><span style="display:none;"><spring:message code="ess.title.DAORUJIEGUO" /><!-- 导入结果 --></span></a>
<div class="pageHeader">
<form id="viewShopShiftListForm" onsubmit="return navTabSearch(this);" action="/ess/tempEmp/viewShopShiftList" method="post">
<div class="searchBar">
<table class="searchContent">
	<tr>
		<td><spring:message code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME" /><!-- 社号/姓名 --></td>
		<td>
			<input type="text" id="seach_KEY" name="seach_KEY" value="${KEY}">
		</td>
		<td><spring:message code="hr.viewPersonalInfo.title.DEPTNAME" /><!-- 部门 --></td>
		<td>
			<ait:deptTreeMulti id="seach_DEPTNO_Multi" name="seach_DEPTNO" limit="manager" selectedNm="${DEPTNO}" selected="${DEPTNO_Multi}"></ait:deptTreeMulti>
		</td>
		<td><spring:message code="ess.infoApply.attendance_date" /><!-- 考勤日期 --></td>
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
				<td><spring:message code="ess.title.KAOQINRI" /><!-- 考勤日--></td>
				<td>
					<select id="DDATE_STR_FILL">
						<option value="1"><spring:message code="ar.week.XINGQIYI.b" /><!-- 星期一--></option>
						<option value="2"><spring:message code="ar.week.XINGQIER.b" /><!-- 星期二--></option>
						<option value="3"><spring:message code="ar.week.XINGQISAN.b" /><!-- 星期三--></option>
						<option value="4"><spring:message code="ar.week.XINGQISI.b" /><!-- 星期四--></option>
						<option value="5"><spring:message code="ar.week.XINGQIWU.b" /><!-- 星期五--></option>
						<option value="6"><spring:message code="ar.week.XINGQILIU.b" /><!-- 星期六--></option>
						<option value="0"><spring:message code="ar.week.XINGQIRI.b" /><!-- 星期日--></option>
					</select>
				</td>
				<td><spring:message code="ess.infoApply.work_time" /><!-- 上班时间--></td>
				<td>
					<input type="text" id="SHIFT_FROM_TIME_FILL" size="5">
				</td>
				<td><spring:message code="ess.infoApply.out_work_time" /><!-- 下班时间--></td>
				<td>
					<input type="text" id="SHIFT_TO_TIME_FILL" size="5">
				</td>
				<td><spring:message code="ess.infoApply.difference" /><!-- 差异--></td>
				<td>
					<input type="text" id="MINUS_LENGTH_FILL" size="5">
				</td>
				<td><spring:message code="ess.infoApply.localyn" /><!-- 考勤状态--></td>
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
	            	 <a class="buttonActive" id="viewShopShiftList_fill"><span><spring:message code="hrm.approve.ALL_REACTION" /><!-- 全部反应--></span></a>
	             </li>
				</ul>
			</div>
	</div>
</div>

<div class="formBar">
	<ul class="toolBar">
		<li><a class="buttonActive" id="viewShopShiftList_Serch" href="#"><span><!-- 查询--><spring:message code="hrm.empinfo.QUERY" /></span></a></li>
		<li><a class="buttonActive" id="viewShopShiftList_up" href="#"><span><!--上星期--><spring:message code="ess.message.last_week" /></span></a></li>
		<li><a class="buttonActive" id="viewShopShiftList_down" href="#"><span><!--下星期--><spring:message code="ess.message.next_week" /></span></a></li>
		<li><a class="buttonActive" href="/pa/excelExport/downloadShopShiftExcelTemplate?file=ShopShiftAction_Add&EMP_TYPE=SHOP"><span><!--模板下载--><spring:message code="ess.message.template_download" /></span></a></li>
		<li><a class="buttonActive" href="#" onclick="excelimport_ess3422();"><span><!--Excel导入--><spring:message code="ar.addempshift.title.excelimport" /></span></a></li>
		<li><a class="buttonActive" href="#" onclick="downloadExcel('viewShopShiftListForm','/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=219','/ess/tempEmp/viewShopShiftList')"><span><!--Excel导出--><spring:message code="inct.salesman.downloadToExcel" /></span></a></li>
		<li><a class="buttonActive" id="viewShopShiftList_Save" href="#"><span><!--保存--><spring:message code="pa.insurance.title.submit" /></span></a></li>
	</ul>
</div>
<div class="pageContent">
				<table class="orderList" width="1600px">
					<thead>
						<tr>
							<th width="30px" rowspan="2">NO.</th>
							<th width="70px" rowspan="2"><!--社号--><spring:message code="public.title.empId" /></th>
							<th width="50px" rowspan="2"><!--姓名--><spring:message code="public.title.empName" /></th>
							<th width="30px" rowspan="2">
						    	<input type="checkbox" class="checkboxCtrl" group="BATCH_SHOP_SHIFT" />
						    </th>
							<c:forEach items="${viewWeekList}" var="item" varStatus="i">
								<th width="180px" colspan="4" id="changeFlag_${i.index }" sysDateStr="${item.DDATE_STR }">
									<c:if test="${item.TYPEID eq 1442}"><font color="red">${item.DDATE_TITLE }</font></c:if>
									<c:if test="${item.TYPEID ne 1442}">${item.DDATE_TITLE }</c:if>
								</th>
							</c:forEach>
						</tr>
						<tr>
							<c:forEach items="${viewWeekList}" var="item" varStatus="i">
									<c:if test="${item.TYPEID eq 1442}">
										<th width="40px"><font color="red"><!--上班--><spring:message code="ess.title.SHANGBAN" /></font></th>
										<th width="40px"><font color="red"><!--下班--><spring:message code="ess.title.XIABAN" /></font></th>
										<th width="40px"><font color="red"><!--差异--><spring:message code="ess.infoApply.difference" /></font></th>
										<th width="60px"><font color="red"><!--考勤--><spring:message code="ess.infoApply.check_work" /></font></th>
									</c:if>
									<c:if test="${item.TYPEID ne 1442}">
										<th width="40px"><!--上班--><spring:message code="ess.title.SHANGBAN" /></th>
										<th width="40px"><!--下班--><spring:message code="ess.title.XIABAN" /></th>
										<th width="40px"><!--差异--><spring:message code="ess.infoApply.difference" /></th>
										<th width="60px"><!--考勤--><spring:message code="ess.infoApply.check_work" /></th>
									</c:if>
							</c:forEach>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${viewShopShiftList}" var="item" varStatus="i">
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
							    <td style="text-align: center">
							        <input type="checkbox" id="BATCH_SHOP_SHIFT_${i.index}" name="BATCH_SHOP_SHIFT" value="${i.index}" />
							    </td>
							 	<td class='td_center' sysWeek="1" sysType="SHIFT_FROM_TIME" <c:if test="${item.ACTIVITY_1 ne '2'}">sysLog="text"</c:if> sysIndex="${i.index}" id="SHIFT_FROM_TIME_1_${i.index}" sysDateStr="${item.DDATE_STR_1}">${item.SHIFT_FROM_TIME_1}</td>
							 	<td class='td_center' sysWeek="1" sysType="SHIFT_TO_TIME" <c:if test="${item.ACTIVITY_1 ne '2'}">sysLog="text"</c:if> sysIndex="${i.index}" id="SHIFT_TO_TIME_1_${i.index}">${item.SHIFT_TO_TIME_1}</td>
							 	<td class='td_center' sysWeek="1" sysType="MINUS_LENGTH" <c:if test="${item.ACTIVITY_1 ne '2'}">sysLog="text"</c:if> sysIndex="${i.index}" id="MINUS_LENGTH_1_${i.index}">${item.MINUS_LENGTH_1}</td>
							 	<td class='td_center' sysWeek="1" sysActivity="${item.ACTIVITY_1}" sysType="ITEM_NO" <c:if test="${item.ACTIVITY_1 ne '2'}">sysLog="select"</c:if> sysValue='${shiftItem }' sysIndex="${i.index}" id="ITEM_NO_1_${i.index}">${item.ITEM_NO_1}<c:if test="${item.ACTIVITY_1 eq '2'}"><br/><!--审批中--><spring:message code="ess.affirmApply.title.remark.shenpizhong" /></c:if></td>
							 	
							 	<td class='td_center' sysWeek="2" sysType="SHIFT_FROM_TIME" <c:if test="${item.ACTIVITY_2 ne '2'}">sysLog="text"</c:if> sysIndex="${i.index}" id="SHIFT_FROM_TIME_2_${i.index}" sysDateStr="${item.DDATE_STR_2}">${item.SHIFT_FROM_TIME_2}</td>
							 	<td class='td_center' sysWeek="2" sysType="SHIFT_TO_TIME" <c:if test="${item.ACTIVITY_2 ne '2'}">sysLog="text"</c:if> sysIndex="${i.index}" id="SHIFT_TO_TIME_2_${i.index}">${item.SHIFT_TO_TIME_2}</td>
							 	<td class='td_center' sysWeek="2" sysType="MINUS_LENGTH" <c:if test="${item.ACTIVITY_2 ne '2'}">sysLog="text"</c:if> sysIndex="${i.index}" id="MINUS_LENGTH_2_${i.index}">${item.MINUS_LENGTH_2}</td>
							 	<td class='td_center' sysWeek="2" sysActivity="${item.ACTIVITY_2}" sysType="ITEM_NO" <c:if test="${item.ACTIVITY_2 ne '2'}">sysLog="select"</c:if> sysValue='${shiftItem }' sysIndex="${i.index}" id="ITEM_NO_2_${i.index}">${item.ITEM_NO_2}<c:if test="${item.ACTIVITY_2 eq '2'}"><br/><!--审批中--><spring:message code="ess.affirmApply.title.remark.shenpizhong" /></c:if></td>
							 	
							 	<td class='td_center' sysWeek="3" sysType="SHIFT_FROM_TIME" <c:if test="${item.ACTIVITY_3 ne '2'}">sysLog="text"</c:if> sysIndex="${i.index}" id="SHIFT_FROM_TIME_3_${i.index}" sysDateStr="${item.DDATE_STR_3}">${item.SHIFT_FROM_TIME_3}</td>
							 	<td class='td_center' sysWeek="3" sysType="SHIFT_TO_TIME" <c:if test="${item.ACTIVITY_3 ne '2'}">sysLog="text"</c:if> sysIndex="${i.index}" id="SHIFT_TO_TIME_3_${i.index}">${item.SHIFT_TO_TIME_3}</td>
							 	<td class='td_center' sysWeek="3" sysType="MINUS_LENGTH" <c:if test="${item.ACTIVITY_3 ne '2'}">sysLog="text"</c:if> sysIndex="${i.index}" id="MINUS_LENGTH_3_${i.index}">${item.MINUS_LENGTH_3}</td>
							 	<td class='td_center' sysWeek="3" sysActivity="${item.ACTIVITY_3}" sysType="ITEM_NO" <c:if test="${item.ACTIVITY_3 ne '2'}">sysLog="select"</c:if> sysValue='${shiftItem }' sysIndex="${i.index}" id="ITEM_NO_3_${i.index}">${item.ITEM_NO_3}<c:if test="${item.ACTIVITY_3 eq '2'}"><br/><!--审批中--><spring:message code="ess.affirmApply.title.remark.shenpizhong" /></c:if></td>
							 	
							 	<td class='td_center' sysWeek="4" sysType="SHIFT_FROM_TIME" <c:if test="${item.ACTIVITY_4 ne '2'}">sysLog="text"</c:if> sysIndex="${i.index}" id="SHIFT_FROM_TIME_4_${i.index}" sysDateStr="${item.DDATE_STR_4}">${item.SHIFT_FROM_TIME_4}</td>
							 	<td class='td_center' sysWeek="4" sysType="SHIFT_TO_TIME" <c:if test="${item.ACTIVITY_4 ne '2'}">sysLog="text"</c:if> sysIndex="${i.index}" id="SHIFT_TO_TIME_4_${i.index}">${item.SHIFT_TO_TIME_4}</td>
							 	<td class='td_center' sysWeek="4" sysType="MINUS_LENGTH" <c:if test="${item.ACTIVITY_4 ne '2'}">sysLog="text"</c:if> sysIndex="${i.index}" id="MINUS_LENGTH_4_${i.index}">${item.MINUS_LENGTH_4}</td>
							 	<td class='td_center' sysWeek="4" sysActivity="${item.ACTIVITY_4}" sysType="ITEM_NO" <c:if test="${item.ACTIVITY_4 ne '2'}">sysLog="select"</c:if> sysValue='${shiftItem }' sysIndex="${i.index}" id="ITEM_NO_4_${i.index}">${item.ITEM_NO_4}<c:if test="${item.ACTIVITY_4 eq '2'}"><br/><!--审批中--><spring:message code="ess.affirmApply.title.remark.shenpizhong" /></c:if></td>
							 	
							 	<td class='td_center' sysWeek="5" sysType="SHIFT_FROM_TIME" <c:if test="${item.ACTIVITY_5 ne '2'}">sysLog="text"</c:if> sysIndex="${i.index}" id="SHIFT_FROM_TIME_5_${i.index}" sysDateStr="${item.DDATE_STR_5}">${item.SHIFT_FROM_TIME_5}</td>
							 	<td class='td_center' sysWeek="5" sysType="SHIFT_TO_TIME" <c:if test="${item.ACTIVITY_5 ne '2'}">sysLog="text"</c:if> sysIndex="${i.index}" id="SHIFT_TO_TIME_5_${i.index}">${item.SHIFT_TO_TIME_5}</td>
							 	<td class='td_center' sysWeek="5" sysType="MINUS_LENGTH" <c:if test="${item.ACTIVITY_5 ne '2'}">sysLog="text"</c:if> sysIndex="${i.index}" id="MINUS_LENGTH_5_${i.index}">${item.MINUS_LENGTH_5}</td>
							 	<td class='td_center' sysWeek="5" sysActivity="${item.ACTIVITY_5}" sysType="ITEM_NO" <c:if test="${item.ACTIVITY_5 ne '2'}">sysLog="select"</c:if> sysValue='${shiftItem }' sysIndex="${i.index}" id="ITEM_NO_5_${i.index}">${item.ITEM_NO_5}<c:if test="${item.ACTIVITY_5 eq '2'}"><br/><!--审批中--><spring:message code="ess.affirmApply.title.remark.shenpizhong" /></c:if></td>
							 	
							 	<td class='td_center' sysWeek="6" sysType="SHIFT_FROM_TIME" <c:if test="${item.ACTIVITY_6 ne '2'}">sysLog="text"</c:if> sysIndex="${i.index}" id="SHIFT_FROM_TIME_6_${i.index}" sysDateStr="${item.DDATE_STR_6}">${item.SHIFT_FROM_TIME_6}</td>
							 	<td class='td_center' sysWeek="6" sysType="SHIFT_TO_TIME" <c:if test="${item.ACTIVITY_6 ne '2'}">sysLog="text"</c:if> sysIndex="${i.index}" id="SHIFT_TO_TIME_6_${i.index}">${item.SHIFT_TO_TIME_6}</td>
							 	<td class='td_center' sysWeek="6" sysType="MINUS_LENGTH" <c:if test="${item.ACTIVITY_6 ne '2'}">sysLog="text"</c:if> sysIndex="${i.index}" id="MINUS_LENGTH_6_${i.index}">${item.MINUS_LENGTH_6}</td>
							 	<td class='td_center' sysWeek="6" sysActivity="${item.ACTIVITY_6}" sysType="ITEM_NO" <c:if test="${item.ACTIVITY_6 ne '2'}">sysLog="select"</c:if> sysValue='${shiftItem }' sysIndex="${i.index}" id="ITEM_NO_6_${i.index}">${item.ITEM_NO_6}<c:if test="${item.ACTIVITY_6 eq '2'}"><br/><!--审批中--><spring:message code="ess.affirmApply.title.remark.shenpizhong" /></c:if></td>
							 	
							 	<td class='td_center' sysWeek="0" sysType="SHIFT_FROM_TIME" <c:if test="${item.ACTIVITY_0 ne '2'}">sysLog="text"</c:if> sysIndex="${i.index}" id="SHIFT_FROM_TIME_0_${i.index}" sysDateStr="${item.DDATE_STR_0}">${item.SHIFT_FROM_TIME_0}</td>
							 	<td class='td_center' sysWeek="0" sysType="SHIFT_TO_TIME" <c:if test="${item.ACTIVITY_0 ne '2'}">sysLog="text"</c:if> sysIndex="${i.index}" id="SHIFT_TO_TIME_0_${i.index}">${item.SHIFT_TO_TIME_0}</td>
							 	<td class='td_center' sysWeek="0" sysType="MINUS_LENGTH" <c:if test="${item.ACTIVITY_0 ne '2'}">sysLog="text"</c:if> sysIndex="${i.index}" id="MINUS_LENGTH_0_${i.index}">${item.MINUS_LENGTH_0}</td>
							 	<td class='td_center' sysWeek="0" sysActivity="${item.ACTIVITY_0}" sysType="ITEM_NO" <c:if test="${item.ACTIVITY_0 ne '2'}">sysLog="select"</c:if> sysValue='${shiftItem }' sysIndex="${i.index}" id="ITEM_NO_0_${i.index}">${item.ITEM_NO_0}<c:if test="${item.ACTIVITY_0 eq '2'}"><br/><!--审批中--><spring:message code="ess.affirmApply.title.remark.shenpizhong" /></c:if></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
		</div>