<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
$(document).ready(function(){
	//保存
	$("#viewAddEmpLeftList_save",$.pdialog.getCurrent()).click(function(){	
		//获取页面的值
		var jsonData = '[';
		$("[sysLog='date']",$.pdialog.getCurrent()).each(function(i, obj){
			if($(obj).html() != ""){
				if (jsonData.length > 1) {
					jsonData += ',{';
				} else {
					jsonData += '{';
				}
				var index = $(obj).attr("sysIndex");
				jsonData += ' "PERSON_ID": "' + $(obj).attr("sysPersonId") + '" ,';
				jsonData += ' "DATE_LEFT": "' + $(obj).html() + '" ,';
				jsonData += ' "TRANS_RESOURCE": "' + $("#TRANS_RESOURCE_" + index,$.pdialog.getCurrent()).html() + '" ,';
				jsonData += ' "REMARK": "' + $("#REMARK_" + index,$.pdialog.getCurrent()).html() + '" ,';
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
					url: '/ess/tempEmp/addTempEmpLeft',
					data: [{ name: 'jsonData', value: jsonData }],
	  				dataType:"json",
	  				cache: false,
	  				success: dialogAjaxDone,
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
	     "scrollY": 380,
	     "scrollX": true,
	     "scrollCollapse": false,
	     "deferRender":true,
	        "columnDefs": [//自定义排序类型
		                     { "orderable": false, "targets": [1] }
	                     ],
	    //"fixedColumns":{leftColumns: 3},
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
		initEditFun_ess3446();
	});
	initEditFun_ess3446();
});

function initEditFun_ess3446(){
	$('.orderList tbody tr td:[sysLog="date"]',$.pdialog.getCurrent()).editable({type:'date',
		onblur:function(val,settings){
			var index = $(this).attr("sysIndex");
			this.editing = false;
			if(val == ""){
				$("#modifyFlag_" + index,navTab.getCurrentPanel()).html("");
			}else{
				$("#modifyFlag_" + index,navTab.getCurrentPanel()).html("modify");
			}
		}
	});
	$('.orderList tbody tr td:[sysLog="text"]',$.pdialog.getCurrent()).editable({type:'text'});
	$('.orderList tbody tr td:[sysLog="select"]',$.pdialog.getCurrent()).editable({type:'select',
		onblur:function(val,settings){
			$(this).html(val);
			var index = $(this).attr("sysIndex");
			this.editing = false;
		}
	});
}
</script>
<div class="formBar">
	<ul class="toolBar">
		<li><a class="buttonActive" id="viewAddEmpLeftList_save" href="#"><span><!--申请--><spring:message code="ar.viewAdjustLeaveTSTOBatchList.SHENQING.b" /></span></a></li>
	</ul>
</div>
<div class="pageContent">
				<table class="orderList">
					<thead>
						<tr>
							<th width="3%">NO.</th>
							<th width="8%"><!--工号--><spring:message code="edu.planManager.GONGHAO.a" /></th>
							<th width="8%"><!--姓名--><spring:message code="alert.pa.pasalarycanshu.xingming" /></th>
							<th width="12%"><!--部门--><spring:message code="ar.attendanceView.viewNoSwipingCard.deptName" /></th>
							<th width="10%"><!--职群--><spring:message code="hr.assignment.group" /></th>
							<th width="10%"><!--岗位--><spring:message code="rp.report.title.dutyinfo" /></th>
							<th width="10%"><!--入职日期--><spring:message code="hrm.recruitManage.DATE_STARTED" /></th>
							<th width="10%" class="titleColor"><!--离职日期--><spring:message code="hrm.recruitManage.LEAVE_DATE" /></th>
							<c:if test="${LoginUser.cpnyId eq 'SPC_NJ'}"><th width="10%" class="titleColor"><!--主要原因--><spring:message code="ar.viewEmpLeftConfirmList.ZHUYAOYUANYIN.b" /></th></c:if>
							<th width="19%" class="titleColor"><!--离职原因--><spring:message code="ess.trans.title.resignReason" /></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${viewAddEmpLeftList}" var="item" varStatus="i">
							<tr target="SEQ" rel="${item.SEQ}">
								<td class='td_center'>${i.count}</td>
								<td class='td_center'>${item.EMPID}</td>
								<td class='td_center'>${item.LOCAL_NAME}</td>
							 	<td class='td_center'>${item.DEPT_NAME}</td>
								<td class='td_center'>${item.POST_FAMILY_NAME}</td>
								<td class='td_center'>${item.DUTY_NO_NAME}</td>
								<td class='td_center'>${item.DATE_STARTED}</td>
								<td class='td_center' sysLog="date" sysIndex="${i.index}" sysPersonId="${item.PERSON_ID}" id="DATE_LEFT_${i.index}"></td>
								<c:if test="${LoginUser.cpnyId eq 'SPC_NJ'}">
								<td class='td_center' sysLog="select" sysIndex="${i.index}" id="TRANS_RESOURCE_${i.index}" sysValue='${TRANS_RESOURCE_STR }'>${item.TRANS_RESOURCE_STR }</td>
								</c:if>
								<td sysLog="text" sysIndex="${i.index}" id="REMARK_${i.index}"></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
		</div>
