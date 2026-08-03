<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
$(document).ready(function(){

	$("[sysActivity='1']",navTab.getCurrentPanel()).attr("sysLog","");

	$("[sysLog='modifyFlag']",navTab.getCurrentPanel()).each(function(i, obj){
		var index = $(obj).attr("sysIndex");
		var sysWeek = $(obj).attr("sysWeek");
		var item = $("#SHIFT_NO_" + sysWeek + "_" + index,navTab.getCurrentPanel()).html();
		var sysDateStr = $(obj).attr("sysDateStr");
		if(item == '<spring:message code="ar.viewCompanyCalendar.title.rest" />'){//休息
			$("td[sysWeek='" + sysWeek + "'][sysIndex='" + index + "']",navTab.getCurrentPanel()).css("background-color","red");
		}
	});
	
	//查询
	$("#viewWuLiuShiftList_Serch",navTab.getCurrentPanel()).click(function(){
		$("#viewWuLiuShiftListForm",navTab.getCurrentPanel()).submit();
	});
	//上星期
	$("#viewWuLiuShiftList_up",navTab.getCurrentPanel()).click(function(){
		var date = $("#changeFlag_0",navTab.getCurrentPanel()).attr("sysDateStr");
		$.ajax({
			type: 'POST',
			url: '/hrm/recruitManage/doSql',
			data:{sql:"select TO_CHAR(TO_DATE('" + date + "','YYYY/MM/DD') - 1,'YYYY/MM/DD') DATE_STR from dual"},
			dataType:"json",
			cache: false,
			success: function(data){
				$("#DDATE_STR",navTab.getCurrentPanel()).val(data.result[0].DATE_STR);
				$("#viewWuLiuShiftListForm",navTab.getCurrentPanel()).submit();
			},
			error: DWZ.ajaxError
		});
	});
	//下星期
	$("#viewWuLiuShiftList_down",navTab.getCurrentPanel()).click(function(){
		var date = $("#changeFlag_6",navTab.getCurrentPanel()).attr("sysDateStr");
		$.ajax({
			type: 'POST',
			url: '/hrm/recruitManage/doSql',
			data:{sql:"select TO_CHAR(TO_DATE('" + date + "','YYYY/MM/DD') + 1,'YYYY/MM/DD') DATE_STR from dual"},
			dataType:"json",
			cache: false,
			success: function(data){
				$("#DDATE_STR",navTab.getCurrentPanel()).val(data.result[0].DATE_STR);
				$("#viewWuLiuShiftListForm",navTab.getCurrentPanel()).submit();
			},
			error: DWZ.ajaxError
		});
	});
	//保存
	$("#viewWuLiuShiftList_Save",navTab.getCurrentPanel()).click(function(){	
		//获取页面的值
		var jsonData = '[';
		$("[sysLog='modifyFlag']",navTab.getCurrentPanel()).each(function(i, obj){
				if (jsonData.length > 1) {
					jsonData += ',{';
				} else {
					jsonData += '{';
				}
				var index = $(obj).attr("sysIndex");
				var sysWeek = $(obj).attr("sysWeek");
				
				jsonData += ' "PERSON_ID": "' + $("#PERSON_ID_" + index,navTab.getCurrentPanel()).val() + '" ,';
				jsonData += ' "AR_DATE_STR": "' + $(obj).attr("sysDateStr") + '" ,';
				jsonData += ' "SHIFT_NO": "' + $("#SHIFT_NO_" + sysWeek + "_" + index,navTab.getCurrentPanel()).html() + '" ,';
				jsonData += ' "adminID": "' + '${LoginUser.adminID}' + '" ,';
				jsonData += ' "adminIP": "' + '${LoginUser.adminIP}' + '" ,';
				jsonData += ' "interCpnyID": "' + '${LoginUser.cpnyId}' + '" ';
				
				jsonData += '}';
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
					url: '/ess/tempEmp/addWuLiuShift',
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
		"iDisplayLength": 10, //默认每页显示的记录数
		"bFilter": true,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
     	"searching": true,//本地搜索
		"bSort": false,   //排序功能
		"bInfo": true,   //显示datatables的信息（底部的页数，条目数信息）
		"bScrollInfinite":true,
	    "orderClasses": false,
	    "order":[],//初始化不用自动排序
	    "scrollY": $(document.body).height() - 350,
	    "scrollX": true,
	    "scrollCollapse": false,
	    "deferRender":true,
	    "columnDefs": [//自定义排序类型
		               { "orderable": false, "targets": [1] }
	                  ],
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

	$('.orderList',navTab.getCurrentPanel()).on( 'draw.dt', function () {
		initEditFun_ess3439();
	});
	
	initEditFun_ess3439();
	
	$("#viewWuLiuShiftList_fill",navTab.getCurrentPanel()).click(function(){
	 	var shift = $("#SHIFT_FILL",navTab.getCurrentPanel()).val();
		$("input[name='SHIFT_FULL_CHECK']",navTab.getCurrentPanel()).each(function(i, object){
			if(object.checked){
				var week = $(object).val();
				$("input[name='BATCH_SHIFT']",navTab.getCurrentPanel()).each(function(i, obj){
					if(obj.checked){
						var index = $(obj).val();
						$("#SHIFT_NO_" + week + "_" + index,navTab.getCurrentPanel()).html(shift);
						$("#modifyFlag_" + week + "_" + index,navTab.getCurrentPanel()).html("modify");
						if(shift == "休息"){
							$("td[sysWeek='" + week + "'][sysIndex='" + index + "']",navTab.getCurrentPanel()).css("background-color","red");
						}
					}
				});
			}
		});
	});
});

function initEditFun_ess3439(){
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
</script>
<div class="pageHeader">
<form id="viewWuLiuShiftListForm" onsubmit="return navTabSearch(this);" action="/ess/tempEmp/viewWuLiuShiftList" method="post">
<div class="searchBar">
<table class="searchContent">
	<tr>
		<td><spring:message code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME" /><!-- 社号/姓名 --></td>
		<td>
			<input type="text" name="seach_KEY" value="${KEY}">
		</td>
		<td><!-- 部门： --> <spring:message
							code="ess.infoApply.DEPT" /></td>
		<td>
			<ait:deptList name="seach_DEPTNO" limit="manager" id="viewWuLiuShiftList_deptList" />
			<ait:deptTreeIcon name="seach_DEPTNO" limit="manager" id="viewWuLiuShiftList_deptList" selected="${DEPTNO}"/>
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
				<td><spring:message code="ess.message.work_shift" /><!-- 班次 --></td>
				<td>
					<select id="SHIFT_FILL">
						<c:forEach items="${shiftNoList}" var="item" varStatus="i">
							<option value="${item.CODENAME }">${item.CODENAME }</option>
						</c:forEach>
					</select>
				</td>
			</tr>
		</table>
			<div class="subBar">
				<ul class="toolBar">
	             <li>
	            	 <a class="buttonActive" id="viewWuLiuShiftList_fill"><span><spring:message code="ess.message.all_reaction" /><!-- 全部反应 --></span></a>
	             </li>
				</ul>
			</div>
	</div>
</div>

<div class="formBar">
	<ul class="toolBar">
		<li><a class="buttonActive" id="viewWuLiuShiftList_Serch" href="#"><span><spring:message code="org.title.SELECT" /><!-- 查询 --></span></a></li>
		<li><a class="buttonActive" id="viewWuLiuShiftList_up" href="#"><span><spring:message code="ess.message.last_week" /><!-- 上星期 --></span></a></li>
		<li><a class="buttonActive" id="viewWuLiuShiftList_down" href="#"><span><spring:message code="ess.message.next_week" /><!-- 下星期 --></span></a></li>
		<li><a class="buttonActive" href="#" onclick="downloadExcel('viewWuLiuShiftListForm','/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=222','/ess/tempEmp/viewWuLiuShiftList')"><span><spring:message code="ar.addempshift.title.excelexport" /><!-- EXCEL导出 --></span></a></li>
		<li><a class="buttonActive" id="viewWuLiuShiftList_Save" href="#"><span><spring:message code="ar.viewempcalender.title.save" /><!-- 保存 --></span></a></li>
	</ul>
</div>
<div class="pageContent">
				<table class="orderList" width="99%">
					<thead>
						<tr>
							<th width="4%">NO.</th>
							<th width="8%"><spring:message code="hr.viewPersonalInfo.title.LOCAL_NAME" /><!-- 姓名 --></th>
							<th width="8%"><spring:message code="hr.viewPersonalInfo.title.POSITION_NAME" /><!--职位 --></th>
							<th width="3%">
						    	<input type="checkbox" class="checkboxCtrl" group="BATCH_SHIFT" />
						    </th>
							<c:forEach items="${viewWeekList}" var="item" varStatus="i">
								<th width="11%" id="changeFlag_${i.index }" sysDateStr="${item.DDATE_STR }">
									<c:if test="${item.TYPEID eq 1442}"><font color="red">${item.DDATE_TITLE }</font></c:if>
									<c:if test="${item.TYPEID ne 1442}">${item.DDATE_TITLE }</c:if>
									<input type="checkbox" name="SHIFT_FULL_CHECK" value="${item.IWEEK}" />
								</th>
							</c:forEach>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${viewWuLiuShiftList}" var="item" varStatus="i">
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
								<td class='td_center'>${item.LOCAL_NAME}</td>
								<td class='td_center'>${item.POSITION_NAME}</td>
							    <td style="text-align: center">
							        <input type="checkbox" id="BATCH_SHIFT_${i.index}" name="BATCH_SHIFT" value="${i.index}" />
							    </td>
							 	<td class='td_center' sysActivity="${item.ACTIVITY_1}" sysWeek="1" sysType="SHIFT_NO" sysLog="select" sysValue='${shiftList }' sysIndex="${i.index}" id="SHIFT_NO_1_${i.index}">${item.SHIFT_NO_1}</td>
							 	<td class='td_center' sysActivity="${item.ACTIVITY_2}" sysWeek="2" sysType="SHIFT_NO" sysLog="select" sysValue='${shiftList }' sysIndex="${i.index}" id="SHIFT_NO_2_${i.index}">${item.SHIFT_NO_2}</td>
							 	<td class='td_center' sysActivity="${item.ACTIVITY_3}" sysWeek="3" sysType="SHIFT_NO" sysLog="select" sysValue='${shiftList }' sysIndex="${i.index}" id="SHIFT_NO_3_${i.index}">${item.SHIFT_NO_3}</td>
							 	<td class='td_center' sysActivity="${item.ACTIVITY_4}" sysWeek="4" sysType="SHIFT_NO" sysLog="select" sysValue='${shiftList }' sysIndex="${i.index}" id="SHIFT_NO_4_${i.index}">${item.SHIFT_NO_4}</td>
							 	<td class='td_center' sysActivity="${item.ACTIVITY_5}" sysWeek="5" sysType="SHIFT_NO" sysLog="select" sysValue='${shiftList }' sysIndex="${i.index}" id="SHIFT_NO_5_${i.index}">${item.SHIFT_NO_5}</td>
							 	<td class='td_center' sysActivity="${item.ACTIVITY_6}" sysWeek="6" sysType="SHIFT_NO" sysLog="select" sysValue='${shiftList }' sysIndex="${i.index}" id="SHIFT_NO_6_${i.index}">${item.SHIFT_NO_6}</td>
							 	<td class='td_center' sysActivity="${item.ACTIVITY_0}" sysWeek="0" sysType="SHIFT_NO" sysLog="select" sysValue='${shiftList }' sysIndex="${i.index}" id="SHIFT_NO_0_${i.index}">${item.SHIFT_NO_0}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
		</div>