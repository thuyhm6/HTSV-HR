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
		
		
});



function tijiao() {
	//获取页面的值
	var jsonData = '[';
	$("input[name='HRM_CARD_BATCH']",navTab.getCurrentPanel()).each(function(i, obj){
		
		if(obj.checked){
			if (jsonData.length > 1) {
				jsonData += ',{';
			} else {
				jsonData += '{';
			}
			var index = obj.value;
			jsonData += ' "PERSON_ID": "' + $("#PERSON_ID_" + index,navTab.getCurrentPanel()).val() + '" ,';
			jsonData += ' "PINGJIA": "' + $("#PINGJIA_" + index,navTab.getCurrentPanel()).val() + '" ';

			jsonData += '}';
		}
	});
	jsonData += ']';
	alertMsg.confirm("确定要提交吗？", {//确定要签订吗？
		okCall : function() {
			$.ajax( {
				type : 'POST',
				url : "/hrm/contractInfo/insertBecomeRegularEvaluate",
				data: [{ name: 'jsonData', value: jsonData }],
				dataType : "json",
				cache : false,
				success : doAjaxDoneWithForm,
				error : DWZ.ajaxError
			});
		}
	});
}



/* 导出个人转正详细信息	 */	
/* function tijiao(personId){
	window.location.href="/hrm/contractInfo/insertBecomeRegularEvaluate?PERSON_ID=" + personId;
} */


</script>

<div class="pageContent"><!-- 
<form onsubmit="return navTabSearch(this);" action="/hrm/contractInfo/becomeRegularEvaluate?firstFlag=N&deleteYN=Y"  method="post"
		id="becomeRegularEvaluate" name="becomeRegularEvaluate"> -->
	<div class="pageHeader">	
		<div class="subBar" style="float: right;">
			<ul>
				<li>
					<a class="buttonActive"
						onclick="tijiao();" href="#"> 
						<span>提交</span>
					</a>
				</li>
			</ul>
		</div>
	</div>
		<table class="orderList" width="99%">
			<thead>
				<tr>
					<th width="3%">
				    	<input type="checkbox" class="checkboxCtrl" group="HRM_CARD_BATCH" />
				    </th> 
					<th>NO</th>
					<th>工号</th>
					<th>姓名</th>
					<th>部门</th>
					<th>转正日期</th>
					<th>评价</th>
					<!-- <th>操作</th> -->
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${becomeRegularList}" var="item" varStatus="i">
					<tr>
										<input type="hidden" id="PERSON_ID_${i.index}" name="PERSON_ID" value="${item.PERSON_ID}"/>

						 <td style="text-align: center">
					        <input type="checkbox" id="HRM_CARD_BATCH_${i.index}" name="HRM_CARD_BATCH" value="${i.index}" />
					    </td> 
						<td>${i.count }</td>
						<td>${item.EMPID }</td>
						<td>${item.LOCAL_NAME }</td>
						<td>${item.DEPT_NAME }</td>
						<td>${item.END_PROBATION_DATE }</td>
						<td><textarea style="width: 100%" id="PINGJIA_${i.index}" name=PINGJIA>${item.BECOME_REGULAR_EVALUATE}</textarea></td>
<%-- 					<td class='td_center'><a href="#" onclick="tijiao(${item.PERSON_ID})">
								提交</a></td> --%>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<div class="subBar" style="padding-left: 650px;">
		</div>
<!-- </form> -->
</div>
