<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
$(document).ready(function(){
	$(".orderList",navTab.getCurrentPanel()).dataTable({
		"bPaginate": true,    //分页
	    "bAutoWidth":true,//表格宽度自动变化
	    "bProcessing":true,
	    "lengthMenu": [[10,20,30, 50,100,500], [10,20, 30, 50,100,500]],
		"bLengthChange": true,  //按多少条记录显示下拉框
		"iDisplayLength": 30, //默认每页显示的记录数
		"bFilter": true,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
     	"searching": true,//本地搜索
		"bSort": true,   //排序功能
		"bInfo": true,   //显示datatables的信息（底部的页数，条目数信息）
		"bScrollInfinite":true,
	     "orderClasses": false,
	     "order":[],//初始化不用自动排序
	     "scrollY": $(document.body).height() - 220,
	     "scrollX": $(document.body).width(),
	     "scrollCollapse": false,
	     "deferRender":true,
	    "columnDefs": false,
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
		
		
		
		
		$("#downloadHrmCard_Batch",navTab.getCurrentPanel()).click(function(){
		//获取页面的值
		var personIds = '';
		$("input[name='HRM_CARD_BATCH']",navTab.getCurrentPanel()).each(function(i, obj){
			if(obj.checked){
				personIds = personIds + "'" + $(obj).val() + "',";
			}
		});
		personIds = personIds + "'1234951753'";
		if (personIds.length == 12) {
			alertMsg.info('<spring:message code="hrm.alert.empinfo.No_selected_objects"/>');//没有选择的对象
			return;
		}
		downloadExcel('viewPaNotImport','/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=296&EMPID_STR=' + personIds,'/hrm/contractInfo/viewPaNotImport');
	});
});

</script>
<div class="formBar">
	<ul class="toolBar">
		<li><a class="buttonActive" id="viewPaParamDownloud_Serch" href="/pa/workManagement/viewPaParamDownloud?pageNum=1&menuNo=14014446&navTabId=pa0824"   width="1000" height="600" target="navTab" ref="14014446" rel="pa0824"  title="<spring:message code='pa.viewPaMain.GONGZISHUJUPILIANGDAORU.C' />"><!-- 工资数据批量导入 -->
			<span><!-- 转到工资数据批量导入 --><spring:message code="hrm.contractInfo.WAGES_BATCH_IMPORT.Z" /></span></a></li>
	<li><a class="buttonActive" id="downloadHrmCard_Batch" href="#">
			<span><!-- 导出到EXCEL --><spring:message code="hrm.empinfo.EXPORT" /></span></a></li>
	</ul>
</div>
<div class="pageContent">
<form onsubmit="return navTabSearch(this);" action="/hrm/contractInfo/viewPaNotImport?firstFlag=N&deleteYN=Y"  method="post"
		id="viewPaNotImport" name="viewPaNotImport">
		<table class="orderList" width="99%">
			<thead>
				<tr>
					<th width="3%">
				    	<input type="checkbox" class="checkboxCtrl" group="HRM_CARD_BATCH" />
				    </th>
					<th>NO</th>
					<th><!-- 社号 --><spring:message code="ess.infoApply.EMPID" /></th>
					<th><!-- 姓名 --><spring:message code="ess.infoApply.NAME" /></th>
					<th><!-- 部门 --><spring:message code="ess.infoApply.DEPT" /></th>
					<th><!-- 提示信息 --><spring:message code="hrm.empinfo.PROMPT_INFORMATION.Z" /></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${notImportList}" var="item" varStatus="i">
					<tr>
						<td style="text-align: center">
							        <input type="checkbox" id="HRM_CARD_BATCH_${i.index}" name="HRM_CARD_BATCH" value="${item.PERSON_ID}" />
							    </td>
						<td>${i.count }</td>
						<td>${item.EMPID }</td>
						<td>${item.LOCAL_NAME }</td>
						<td>${item.DEPT_NAME }</td>
						<td>${item.REMIND_STR }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<div class="subBar" style="padding-left: 650px;">
		</div>
</form>
</div>
