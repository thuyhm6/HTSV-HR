<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
$(document).ready(function(){
	$("#viewProbationEvsItemPanel_currentIndex").val('${currentIndex}');

	$('.list tbody tr td:[sysLog="text"]',navTab.getCurrentPanel()).editable({type:'text',
		onblur:function(val,settings){
        	$(this).html(val);
			var index = $(this).attr("sysIndex");
			$("#modifyFlag_${currentIndex}_" + index,navTab.getCurrentPanel()).html("modify");
			this.editing = false;
		}
	});
	
	$("#viewProbationEvsItemList_table_${currentIndex}",navTab.getCurrentPanel()).dataTable({"bPaginate": false,    //关闭分页
	    "bAutoWidth":false,//表格宽度不自动变化
	    "bProcessing":false,
		"bLengthChange": false,  //关闭按多少条记录显示下拉框
		"bFilter": false,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
		"bSort": true,   //关闭排序功能
		"bInfo": false,   //不显示datatables的信息（底部的页数，条目数信息）
		"bScrollInfinite":true,
		"bAutoWidth": false,    //表格宽度不自动变化
		"scrollY": $(document.body).height() - 250,
        "scrollX": true,
        "orderClasses": false,
        "oLanguage": {
		"sProcessing": "<spring:message code='hem.alert.empinfo.Is_loading'/>",//正在加载中......
        "sZeroRecords": "<spring:message code='ess.message.NOT_FOUND_DATA'/>",//查询不到相关数据！
        "sEmptyTable": "<spring:message code='ess.message.NOT_FOUND_DATA_FROM_TABLE'/>",//表中无数据存在！
        "sSearch": "<spring:message code='ess.message.rapid_screening'/>"//快速筛选
        } //多语言配置
	});
	//保存
	$("#viewProbationEvsItemList_save${currentIndex}",navTab.getCurrentPanel()).click(function(){
		var currentIndex = '${currentIndex }';
		//获取页面的值
		var jsonData = '[';
		$("div[sysLog='modifyFlag${currentIndex }']",navTab.getCurrentPanel()).each(function(i, obj){
			if($(this).html() == "modify"){
				if (jsonData.length > 1) {
					jsonData += ',{';
				} else {
					jsonData += '{';
				}
				var index = $(this).attr("sysIndex");

				jsonData += ' "ITEM_SCORE": "' + $("#ITEM_SCORE_${currentIndex }_" + index,navTab.getCurrentPanel()).html() + '" ,';
				jsonData += ' "ITEM_CONTENT": "' + $("#ITEM_CONTENT_${currentIndex }_" + index,navTab.getCurrentPanel()).html() + '" ,';
				jsonData += ' "SEQ": "' + $("#SEQ_${currentIndex }_" + index,navTab.getCurrentPanel()).html() + '" ,';
				jsonData += ' "RESUME_SEQ": "${RESUME_SEQ }" ,';
				jsonData += ' "adminID": "' + '${LoginUser.adminID}' + '" ,';
				jsonData += ' "adminIP": "' + '${LoginUser.adminIP}' + '" ,';
				jsonData += ' "interCpnyID": "' + '${LoginUser.cpnyId}' + '" ';
				jsonData += '}';
			}
		});
		jsonData += ']';
		if (jsonData.length == 2) {
			alertMsg.info("<spring:message code='ess.message.NO_NEED_TO_SAVE_DATA'/>");//没有需要保存的数据
			return;
		}
		alertMsg.confirm("<spring:message code='ess.message.confirm_sava'/>",//确定要保存吗？
	  		  	{okCall:function(){
			  	$.ajax({
	  				type: 'POST',
					url: '/evs/manage/addProbationEvsItemInfo?currentIndex=${currentIndex }',
					data: [{ name: 'jsonData', value: jsonData }],
	  				dataType:"json",
	  				cache: false,
	  				success: navTabAjaxDoneWithForm,
	  				error: DWZ.ajaxError
	  			});
	  	}});
	});
});
</script>
<div class="pageContent">
			<div class="user_table">
				<div style="float:right;height:25px;line-height:25px;">
					<a class="w_button" onclick="openOnRight('/evs/manage/viewProbationEvsItemList?evsType=${evsType }&RESUME_SEQ=${RESUME_SEQ }&currentIndex=${currentIndex }&FLAG=ADD','viewProbationEvsItemList_${currentIndex }')"><span><spring:message code="ess.empInfo.insert"/><!--添加--></span></a>
					<a class="w_button" href="/evs/manage/deleteProbationEvsItemInfo?SEQ={SEQ}&currentIndex=${currentIndex }" target="ajaxTodo" callback="navTabAjaxDoneWithForm" title="<spring:message code="ar.viewRetrieveSqlMasterList.QUEDINGYAOSHANCHUMA.b"/>"><span><spring:message code="ess.empInfo.Delete"/><!--删除--></span></a>
					<a class="w_button" id="viewProbationEvsItemList_save${currentIndex}"><span><spring:message code="org.title.SAVE"/><!--保存--></span></a>
				</div>
			</div>
			<div class="user_table" style="font:bold 12px/20px arial,sans-serif;">Total:${viewEvsItemListSize}</div>
				<table id="viewProbationEvsItemList_table_${currentIndex }"  class="list" width="1100px;">
					<thead>
						<tr>
							<th width="30px">No.</th>
							<th width="200px"><spring:message code="evs.viewProbationEvsItemList.NENGLIXIANGMU.a"/><!--能力项目--></th>
							<th width="80px"><spring:message code="evs.viewEvsItemList.JIAQUANZHI.a"/><!--加权值--></th>
							<th width="180px"><spring:message code="hrm.empinfo.UPDATED_BY"/><!--变更者--></th>
							<th width="120px"><spring:message code="hrm.empinfo.UPDATE_DATE"/><!--变更时间--></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${viewEvsItemList}" var="item" varStatus="i">
							<tr target="SEQ" rel="${item.SEQ}">
								<td class='td_center'>${i.count}</td>
								<td sysLog="text" sysIndex="${i.index}" id="ITEM_CONTENT_${currentIndex }_${i.index}">${item.ITEM_CONTENT}</td>
								<td sysLog="text" sysIndex="${i.index}" id="ITEM_SCORE_${currentIndex }_${i.index}">${item.ITEM_SCORE}</td>
								<td>${item.UPDATED_BY}</td>
								<td>${item.UPDATE_DATE}
									<div id="modifyFlag_${currentIndex }_${i.index}" sysLog="modifyFlag${currentIndex }" sysIndex="${i.index}" style="display:none;"></div>
									<div id="SEQ_${currentIndex }_${i.index}" style="display:none;">${item.SEQ}</div>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
</div>
