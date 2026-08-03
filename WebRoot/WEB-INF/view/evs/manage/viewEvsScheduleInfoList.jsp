<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
$(document).ready(function(){
	$("#viewEvsSchedulePanel_currentIndex").val('${currentIndex}');

	$('.orderList tbody tr td:[sysLog="date"]',navTab.getCurrentPanel()).editable({type:'date',
		onblur:function(val,settings){
			var index = $(this).attr("sysIndex");
			$("#modifyFlag_${currentIndex}_" + index,navTab.getCurrentPanel()).html("modify");
			this.editing = false;
		}
	});

	$('.orderList tbody tr td:[sysLog="select"]').editable({type:'select',
		onblur:function(val,settings){
	    	$(this).html(val);
			var index = $(this).attr("sysIndex");
			$("#modifyFlag_${currentIndex}_" + index,navTab.getCurrentPanel()).html("modify");
			this.editing = false;
		}
	});

	$('.orderList tbody tr td:[sysLog="lookUp"]').editable({type:'lookUp',
		onblur:function(val,settings){
			var index = $(this).attr("sysIndex");
			submitKeyClick_affirmorPEvsSch(val,index);
			$("#modifyFlag_${currentIndex}_" + index,navTab.getCurrentPanel()).html("modify");
			this.editing = false;
		}
	});
	
	$("#viewEvsScheduleInfoList_table_${currentIndex}",navTab.getCurrentPanel()).dataTable({
		"bPaginate": true,    //分页
	    "bAutoWidth":false,//表格宽度自动变化
	    "bProcessing":true,
		"bLengthChange": true,  //按多少条记录显示下拉框
		"bFilter": true,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
	 	"searching": true,//本地搜索
		"bSort": true,   //排序功能
		"bInfo": true,   //显示datatables的信息（底部的页数，条目数信息）
		"bScrollInfinite":true,
	     "orderClasses": false,
	     "order":[],//初始化不用自动排序
	     "scrollY": $(document.body).height() - 290,
	     "scrollCollapse": false,
	     "deferRender":true,
	     "fixedColumns":false,
	    "oLanguage": {//多语言配置
	        "sProcessing": "<spring:message code="hem.alert.empinfo.Is_loading"/>",//正在加载中......
	        "sZeroRecords": "<spring:message code="hem.alert.empinfo.not_find_relevant_data"/>",//查询不到相关数据！
	        "sEmptyTable": "<spring:message code="hrm.alert.empinfo.No_data_in_table"/>",//表中无数据存在！
	        "sSearch": "<spring:message code="hrm.alert.contractInfo.Rapid_screening"/>",//快速筛选
	        "sLengthMenu": "<spring:message code="hrm.alert.contractInfo.Record_page"/>",//每页 _MENU_ 条记录
	        "sInfo": "<spring:message code="hrm.alert.contractInfo.START_END_TOTAL"/>",//从 _START_ 到 _END_ /共 _TOTAL_ 条数据
	        "sInfoFiltered": "(<spring:message code="hrm.alert.contractInfo.Record_filter"/>)",//从 _MAX_ 条记录过滤
	        "oPaginate": {
	            "sPrevious": "<spring:message code="hrm.alert.contractInfo.Previous_page"/>",//上一页
	            "sNext": "<spring:message code="hrm.alert.contractInfo.NEXT_PAGE"/>"//下一页
	        }
	    },
	    "sDom":'<"top"r<"clear">fBl>t<"bottom"ip<"clear">>',
	    "buttons": [] 
	});
	//保存
	$("#viewEvsScheduleInfoList_save${currentIndex}",navTab.getCurrentPanel()).click(function(){
		//获取页面的值
		var jsonData = '[';
		$("div[sysLog='modifyFlag${currentIndex }']",navTab.getCurrentPanel()).each(function(i, obj){
			var currentIndex = '${currentIndex }';
			if($(this).html() == "modify"){
				if (jsonData.length > 1) {
					jsonData += ',{';
				} else {
					jsonData += '{';
				}
				var index = $(this).attr("sysIndex");
				var ACTIVITY = 0;
				if($("#viewEvsSchedulePanel_checkbox${currentIndex }_" + index ).attr("checked") == "checked" ){
					var ACTIVITY = 1;
				}
				if(currentIndex == '2'){
					jsonData += ' "PERSON_ID": "' + $("[id='EMPID_${currentIndex }_" + index + "']",navTab.getCurrentPanel()).attr("sysPersonId") + '" ,';
					jsonData += ' "EMPID": "' + $("[id='EMPID_${currentIndex }_" + index + "']",navTab.getCurrentPanel()).attr("sysEmpId") + '" ,';
					jsonData += ' "LOCAL_NAME": "' + $("[id='EMPID_${currentIndex }_" + index + "']",navTab.getCurrentPanel()).html() + '" ,';
					jsonData += ' "POST_GRADE_NAME": "' + $("#POST_GRADE_${currentIndex }_" + index,navTab.getCurrentPanel()).html() + '" ,';
				}else{
					jsonData += ' "NAME": "' + $("#EVS_NAME_${currentIndex }_" + index,navTab.getCurrentPanel()).html() + '" ,';
				}
				jsonData += ' "EVS_STEP_NAME": "' + $("#EVS_STEP_NAME_${currentIndex }_" + index,navTab.getCurrentPanel()).html() + '" ,';
				jsonData += ' "START_DATE": "' + $("#START_DATE_${currentIndex }_" + index,navTab.getCurrentPanel()).html() + '" ,';
				jsonData += ' "END_DATE": "' + $("#END_DATE_${currentIndex }_" + index,navTab.getCurrentPanel()).html() + '" ,';
				jsonData += ' "SEQ": "' + $("#SEQ_${currentIndex }_" + index,navTab.getCurrentPanel()).html() + '" ,';
				jsonData += ' "ORDER_NO": "' + index + '" ,';
				jsonData += ' "ACTIVITY": "' + ACTIVITY + '" ,';
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
					url: '/evs/manage/addScheduleInfo?currentIndex=${currentIndex }',
					data: [{ name: 'jsonData', value: jsonData }],
	  				dataType:"json",
	  				cache: false,
	  				success: navTabAjaxDoneWithForm,
	  				error: DWZ.ajaxError
	  			});
	  	}});
	});
});


var keyCodeInit=0;
function submitKeyClick_affirmorPEvsSch(obj,index){
	var empid=obj.replace(/[ ]/g," ");
	if(empid != ''){
	   	$.ajax({
			type: 'POST',
			url: encodeURI('/sys/affirm/getPersonCntByEmpid?EMPID='+empid ),
			dataType:"json",
			cache: false,
			success: function(jsonObject){
				if(jsonObject.perCnt != 1 ){
					document.getElementById("onckSche").href=encodeURI(encodeURI("/ar/attendanceMintenance/viewAddAffirmEvsList?isEmployeement=1&limit=super&pageNum=1"
							+'&seach_KEY='+empid
							+'&personidStr=viewScheduleInfo'
							+'&empidStr=${currentIndex }_' + index));
					document.getElementById("onckSche").click();
				}
				if(jsonObject.perCnt==1){
					  $("[id='EMPID_${currentIndex }_" + index + "']",navTab.getCurrentPanel()).html(jsonObject.empName);
					  $("[id='EMPID_${currentIndex }_" + index + "']",navTab.getCurrentPanel()).attr("sysPersonId",jsonObject.personId);
					  $("[id='EMPID_${currentIndex }_" + index + "']",navTab.getCurrentPanel()).attr("sysEmpId",jsonObject.empId);
					  $("[id='POST_GRADE_${currentIndex }_" + index + "']",navTab.getCurrentPanel()).html(jsonObject.POST_GRADE_NAME);
				}
			},
			error: DWZ.ajaxError
		});
	}else{
		  $("[id='EMPID_${currentIndex }_" + index + "']",navTab.getCurrentPanel()).html("");
		  $("[id='EMPID_${currentIndex }_" + index + "']",navTab.getCurrentPanel()).attr("sysPersonId","");
		  $("[id='EMPID_${currentIndex }_" + index + "']",navTab.getCurrentPanel()).attr("sysEmpId","");
		  $("[id='POST_GRADE_${currentIndex }_" + index + "']",navTab.getCurrentPanel()).html("");
	}
}

/**
 * 禁用textArea以及Input框的enter键的自动提交
 */
document.onkeydown = function(event) {  
	  var target, code, tag;  
	  if (!event) {  
	       event = window.event; //针对ie浏览器  
	       target = event.srcElement;  
	       code = event.keyCode;  
	       if (code == 13) {  
	           tag = target.tagName;  
	           if (tag == "TEXTAREA") {
		           return true;
		       }else{ 
			       return false;
			   }  
	       }  
	  }else {  
	       target = event.target; //针对遵循w3c标准的浏览器，如Firefox  
	       code = event.keyCode;  
	       if (code == 13) {  
	           tag = target.tagName;  
	           if (tag == "INPUT"){ 
		           return false; 
		       }else {
			        return true;
			   }   
	      }  
	 }  
}

function deleteScheduleInfo(currentIndex){
	var currentIndex = '${currentIndex }';
	//获取页面的值
	var jsonData = '[';
	$("input[name='evsSchedulebox']",navTab.getCurrentPanel()).each(function(i, obj){
		if(obj.checked){
			if (jsonData.length > 1) {
				jsonData += ',{';
			} else {
				jsonData += '{';
			}
			jsonData += ' "SEQ": "' + obj.value + '" ,';
			jsonData += ' "currentIndex": "' + currentIndex + '" ,';
			jsonData += ' "adminID": "' + '${LoginUser.adminID}' + '" ,';
			jsonData += ' "adminIP": "' + '${LoginUser.adminIP}' + '" ';
			
			jsonData += '}';
		}
	});
	jsonData += ']';
	
	if (jsonData.length == 2) {
		alertMsg.info("<spring:message code='org.title.SELECT_DELETEDATA'/>");//请选择需要删除的数据
		return;
	}
	alertMsg.confirm("<spring:message code='button.delete.sure'/>",//确定要删除吗？
  		  	{okCall:function(){
		  	$.ajax({
  				type: 'POST',
				url: '/evs/manage/deleteScheduleInfo?currentIndex=${currentIndex }',
				data: [{ name: 'jsonData', value: jsonData }],
  				dataType:"json",
  				cache: false,
  				success: navTabAjaxDoneWithForm,
  				error: DWZ.ajaxError
  			});
  	}});
}
</script>
<c:if test="${currentIndex eq '0'}">
<div class="pageContent">
			<div class="user_table">
				<div style="float:right;height:25px;line-height:25px;">
					<a class="w_button" onclick="openOnRight('/evs/manage/viewEvsScheduleInfoList?evsType=${evsType }&RESUME_SEQ=${RESUME_SEQ }&SCHEDULE_TYPE=${SCHEDULE_TYPE }&currentIndex=${currentIndex }&FLAG=ADD','viewEvsScheduleInfoList_${currentIndex }')">
					<span><spring:message code="ess.empInfo.insert"/><!--添加--></span></a>
					<a class="w_button" onclick="deleteScheduleInfo(${currentIndex})" href="#" ><span><spring:message code="ess.empInfo.Delete"/><!--删除--></span></a>
					<a class="w_button" id="viewEvsScheduleInfoList_save${currentIndex}"><span><spring:message code="org.title.SAVE"/><!--保存--></span></a>
					<%-- <c:if test="${LoginUser.cpnyId eq 'TSTO'}">
					<a class="w_button" href="/evs/manage/syncScheduleInfo?RESUME_SEQ=${RESUME_SEQ }" target="ajaxTodo" callback="navTabAjaxDoneWithForm" title="<spring:message code="evs.viewEvsScheduleInfoList.QUEDINGYAOGENGXINGERENRIZHIMA.a"/>">
					<span><spring:message code="evs.viewEvsScheduleInfoList.GERENRICHENGTONGBU.a"/><!--个人日程同步--></span></a>
					</c:if> --%>
				</div>
			</div>
			<div class="user_table" style="font:bold 12px/20px arial,sans-serif;">Total:${scheduleInfoListSize}</div>
				<table id="viewEvsScheduleInfoList_table_${currentIndex }" class="orderList" width="900px;">
					<thead>
						<tr>
							<th width="10px"><input type="checkbox" class="checkboxCtrl" group="evsSchedulebox" /></th>
							<th width="30px">No.</th>
							<th width="120px"><spring:message code="evs.viewEvsScheduleInfoList.PINGJIAJINXINGQUFEN.a"/><!--评价进行区分--></th>
							<th width="80px"><spring:message code="ar.attendanceView.viewNoSwipingCard.beginTime"/><!--开始日期--></th>
							<th width="80px"><spring:message code="ar.attendanceView.viewNoSwipingCard.endTime"/><!--结束日期--></th>
							<th width="50px"><spring:message code="empsubject.useY"/><!--使用--><input type="checkbox" class="checkbox" group="viewEvsSchedulePanel_checkbox${currentIndex }"/></th>
							<th width="180px"><spring:message code="hrm.empinfo.UPDATED_BY"/><!--变更者--></th>
							<th width="120px"><spring:message code="hrm.empinfo.UPDATE_DATE"/><!--变更时间--></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${scheduleInfoList}" var="item" varStatus="i">
							<tr target="SEQ" rel="${item.SEQ}">
								<td style="text-align: center">
							        <input name="CPNY_ID" id="CPNY_ID" type="hidden" value="${LoginUser.cpnyId}"/>
							        <input type="checkbox" sysIndex="${i.index}" id="evsSchedulebox_${i.index}" name="evsSchedulebox" value="${item.SEQ}" />
							        <input type="hidden" name="currentIndex" id="currentIndex" value="${currentIndex }"/>
							    </td>
								<td class='td_center'>${i.count}</td>
								<td sysLog="select" sysIndex="${i.index}" sysValue='${evsStep }' id="EVS_STEP_NAME_${currentIndex }_${i.index}">${item.EVS_STEP_NAME}</td>
								<td sysLog="date" sysIndex="${i.index}"  id="START_DATE_${currentIndex }_${i.index}" format="dd/MM/yyyy" >${item.START_DATE }</td>
								<td sysLog="date" sysIndex="${i.index}" format="dd/MM/yyyy" id="END_DATE_${currentIndex }_${i.index}" >${item.END_DATE}</td>
								<td class='td_center'><input type="checkbox" id="viewEvsSchedulePanel_checkbox${currentIndex }_${i.index}" value="1"<c:if test="${item.ACTIVITY eq '1' }">checked="checked"</c:if>/></td>
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
</c:if>

<c:if test="${currentIndex eq '1'}">
<div class="pageContent">
			<c:if test="${ACTIVITY ne '4'}">
			<div class="user_table">
				<div style="float:right;height:25px;line-height:25px;">
					<a class="w_button" onclick="openOnRight('/evs/manage/viewEvsScheduleInfoList?evsType=${evsType }&RESUME_SEQ=${RESUME_SEQ }&SCHEDULE_TYPE=${SCHEDULE_TYPE }&currentIndex=${currentIndex }&FLAG=ADD','viewEvsScheduleInfoList_${currentIndex }')">
					<span><spring:message code="ess.empInfo.insert"/><!--添加--></span></a>
					<a class="w_button" onclick="deleteScheduleInfo(${currentIndex})" href="#" ><span><spring:message code="ess.empInfo.Delete"/><!--删除--></span></a>
					<a class="w_button" id="viewEvsScheduleInfoList_save${currentIndex}"><span><spring:message code="org.title.SAVE"/><!--保存--></span></a>
				</div>
			</div>
			</c:if>
			<div class="user_table" style="font:bold 12px/20px arial,sans-serif;">Total:${scheduleInfoListSize}</div>
				<table id="viewEvsScheduleInfoList_table_${currentIndex }"  class="orderList" width="1000px;">
					<thead>
						<tr>
							<th width="10px"><input type="checkbox" class="checkboxCtrl" group="evsSchedulebox" /></th>
							<th width="30px">No.</th>
							<th width="120px"><spring:message code="ess.infoApply.DEPT"/><!--部门--></th>
							<th width="80px"><spring:message code="org.title.DEPT_CODE"/><!--部门代码--></th>
							<th width="80px"><spring:message code="evs.viewEvsScheduleInfoList.PINGJIAJINXINGQUFEN.a"/><!--评价进行区分--></th>
							<th width="80px"><spring:message code="ar.attendanceView.viewNoSwipingCard.beginTime"/><!--开始日期--></th>
							<th width="80px"><spring:message code="ar.attendanceView.viewNoSwipingCard.endTime"/><!--结束日期--></th>
							<th width="50px"><spring:message code="empsubject.useY"/><!--使用--><input type="checkbox" class="checkbox" group="viewEvsSchedulePanel_checkbox${currentIndex }"/></th>
							<th width="180px"><spring:message code="hrm.empinfo.UPDATED_BY"/><!--变更者--></th>
							<th width="120px"><spring:message code="hrm.empinfo.UPDATE_DATE"/><!--变更时间--></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${scheduleInfoList}" var="item" varStatus="i">
							<tr target="SEQ" rel="${item.SEQ}">
								<td style="text-align: center">
							        <input name="CPNY_ID" id="CPNY_ID" type="hidden" value="${LoginUser.cpnyId}"/>
							        <input type="checkbox" sysIndex="${i.index}" id="evsSchedulebox_${i.index}" name="evsSchedulebox" value="${item.SEQ}" />
							        <input type="hidden" name="currentIndex" id="currentIndex" value="${currentIndex }"/>
							    </td>
								<td class='td_center'>${i.count}</td>
								<td sysLog="select" sysIndex="${i.index}" sysValue='${dept }' id="EVS_NAME_${currentIndex }_${i.index}">${item.NAME}</td>
								<td>${item.NO}</td>
								<td sysLog="select" sysIndex="${i.index}" sysValue='${evsStep }' id="EVS_STEP_NAME_${currentIndex }_${i.index}">${item.EVS_STEP_NAME}</td>
								<td sysLog="date" sysIndex="${i.index}" format="dd/MM/yyyy" id="START_DATE_${currentIndex }_${i.index}" >${item.START_DATE }</td>
								<td sysLog="date" sysIndex="${i.index}" format="dd/MM/yyyy" id="END_DATE_${currentIndex }_${i.index}" >${item.END_DATE}</td>
								<td class='td_center'><input type="checkbox" id="viewEvsSchedulePanel_checkbox${currentIndex }_${i.index}" value="1"<c:if test="${item.ACTIVITY eq '1' }">checked="checked"</c:if>/></td>
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
</c:if>

<c:if test="${currentIndex eq '2'}">
<div class="pageContent">
			<c:if test="${ACTIVITY ne '4'}">
			<div class="user_table">
				<div style="float:right;height:25px;line-height:25px;">
					<a class="w_button" onclick="openOnRight('/evs/manage/viewEvsScheduleInfoList?evsType=${evsType }&RESUME_SEQ=${RESUME_SEQ }&SCHEDULE_TYPE=${SCHEDULE_TYPE }&currentIndex=${currentIndex }&FLAG=ADD','viewEvsScheduleInfoList_${currentIndex }')">
					<span><spring:message code="ess.empInfo.insert"/><!--添加--></span></a>
					<a class="w_button" onclick="deleteScheduleInfo(${currentIndex})" href="#" ><span><spring:message code="ess.empInfo.Delete"/><!--删除--></span></a>
					<a class="w_button" id="viewEvsScheduleInfoList_save${currentIndex}"><span><spring:message code="org.title.SAVE"/><!--保存--></span></a>
				</div>
			</div>
			</c:if>
			<div class="user_table" style="font:bold 12px/20px arial,sans-serif;">Total:${scheduleInfoListSize}</div>
				<table id="viewEvsScheduleInfoList_table_${currentIndex }"  class="orderList" width="1100px;">
					<thead>
						<tr>
							<th width="10px"><input type="checkbox" class="checkboxCtrl" group="evsSchedulebox" /></th>
							<th width="30px">No.</th>
							<th width="80px"><spring:message code="alert.pa.pasalarycanshu.xingming"/><!--姓名--></th>
							<th width="80px"><spring:message code="ess.trans.title.postGradeName"/><!--职级--></th>
							<th width="80px"><spring:message code="evs.viewEvsScheduleInfoList.PINGJIAJINXINGQUFEN.a"/><!--评价进行区分--></th>
							<th width="80px"><spring:message code="hrm.recruitManage.START_DATE1"/><!--开始日期--></th>
							<th width="80px"><spring:message code="hrm.recruitManage.END_DATE1"/><!--结束日期--></th>
							<th width="50px"><spring:message code="empsubject.useY"/><!--使用--><input type="checkbox" class="checkbox" group="viewEvsSchedulePanel_checkbox${currentIndex }"/></th>
							<th width="180px"><spring:message code="hrm.empinfo.UPDATED_BY"/><!--变更者--></th>
							<th width="120px"><spring:message code="hrm.empinfo.UPDATE_DATE"/><!--变更时间--></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${scheduleInfoList}" var="item" varStatus="i">
							<tr target="SEQ" rel="${item.SEQ}">
								<td style="text-align: center">
							        <input name="CPNY_ID" id="CPNY_ID" type="hidden" value="${LoginUser.cpnyId}"/>
							        <input type="checkbox" sysIndex="${i.index}" id="evsSchedulebox_${i.index}" name="evsSchedulebox" value="${item.SEQ}" />
							        <input type="hidden" name="currentIndex" id="currentIndex" value="${currentIndex }"/>
							    </td>
								<td class='td_center'>${i.count}</td>
								<td sysLog="lookUp" sysIndex="${i.index}" sysEmpId="${item.NO}" sysPersonId="${item.PERSON_ID}" id="EMPID_${currentIndex }_${i.index}">${item.NAME}</td>
								<td id="POST_GRADE_${currentIndex }_${i.index}">${item.POST_GRADE_NAME}</td>
								<td sysLog="select" sysIndex="${i.index}" sysValue='${evsStep }' id="EVS_STEP_NAME_${currentIndex }_${i.index}">${item.EVS_STEP_NAME}</td>
								<td sysLog="date" sysIndex="${i.index}" format="dd/MM/yyyy" id="START_DATE_${currentIndex }_${i.index}" >${item.START_DATE }</td>
								<td sysLog="date" sysIndex="${i.index}" format="dd/MM/yyyy" id="END_DATE_${currentIndex }_${i.index}" >${item.END_DATE}</td>
								<td class='td_center'><input type="checkbox" id="viewEvsSchedulePanel_checkbox${currentIndex }_${i.index}" value="1"<c:if test="${item.ACTIVITY eq '1' }">checked="checked"</c:if>/></td>
								<td>${item.UPDATED_BY}</td>
								<td>${item.UPDATE_DATE}
									<div id="modifyFlag_${currentIndex }_${i.index}" sysLog="modifyFlag${currentIndex }" sysIndex="${i.index}" style="display:none;"></div>
									<div id="SEQ_${currentIndex }_${i.index}" style="display:none;">${item.SEQ}</div>
									<a id="onckSche" name="onckSche"  href="" lookupGroup="person"></a>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
</div>
</c:if>