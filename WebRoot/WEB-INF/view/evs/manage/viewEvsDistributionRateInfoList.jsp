<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
$(document).ready(function(){
	$("#viewEvsDistributionRateInfoPanel_currentIndex").val('${currentIndex}');
	
	$('td:[sysLog="text"]',navTab.getCurrentPanel()).editable({type:'text',
		onblur:function(val,settings){
    		if(isNaN(val)){
    	        $(this).html(0);
    		}if(val == ''){
    	        $(this).html(0);
    		}else{
    	        $(this).html(val);
    		}
    		this.editing   = false;
    		var total = 0;
    		$('td:[name="score"]',navTab.getCurrentPanel()).each(function(i, obj){
    			total += parseInt($(obj).html());
    		});
    		$("#total_${currentIndex }",navTab.getCurrentPanel()).html(total);
		}
	});
	//保存
	$("#viewEvsDistributionRateInfoList_save0",navTab.getCurrentPanel()).click(function(){
		//获取页面的值
		var jsonData = '[{';
		$('td:[name="score"]',navTab.getCurrentPanel()).each(function(i, obj){
			jsonData += ' "' + $("#score_" + i,navTab.getCurrentPanel()).attr("spVal") + '": "' + $(obj).html() + '" ,';
		});
		jsonData += ' "SUM": "' + $("#total_${currentIndex }",navTab.getCurrentPanel()).html() + '" ,';
		jsonData += ' "SEQ": "' + $("#SEQ_${currentIndex }",navTab.getCurrentPanel()).html() + '" ,';
		jsonData += ' "RESUME_SEQ": "${RESUME_SEQ}" ,';
		jsonData += ' "SCORE_TYPE": "CPNY" ,';
		jsonData += ' "adminID": "' + '${LoginUser.adminID}' + '" ,';
		jsonData += ' "adminIP": "' + '${LoginUser.adminIP}' + '" ,';
		jsonData += ' "interCpnyID": "' + '${LoginUser.cpnyId}' + '" ';
		jsonData += '}]';
		if (jsonData.length == 2) {
			alertMsg.info("<spring:message code='ess.message.NO_NEED_TO_SAVE_DATA'/>");//没有需要保存的数据
			return;
		}
		alertMsg.confirm("<spring:message code='ess.message.confirm_sava'/>",//确定要保存吗？
	  		  	{okCall:function(){
			  	$.ajax({
	  				type: 'POST',
					url: '/evs/manage/addEvsDistributionRateInfo?currentIndex=${currentIndex }',
					data: [{ name: 'jsonData', value: jsonData }],
	  				dataType:"json",
	  				cache: false,
	  				success: navTabAjaxDoneWithForm,
	  				error: DWZ.ajaxError
	  			});
	  	}});
	});
	sumScore();
});
function sumScore(){
	var total = 0;
	$('td:[name="score"]',navTab.getCurrentPanel()).each(function(i, obj){
		total += parseInt($(obj).html());
	});
	$("#total_${currentIndex }",navTab.getCurrentPanel()).html(total);
}
</script>
<c:if test="${currentIndex eq '0'}">
<div class="pageContent">
			<c:if test="${ACTIVITY ne '4'}">
			<div class="user_table">
				<div style="float:right;height:30px;line-height:30px;padding-top:8px;">
					<a class="w_button" id="viewEvsDistributionRateInfoList_save${currentIndex}"><span><spring:message code="org.title.SAVE"/><!--保存--></span></a>
				</div>
			</div>
			</c:if>
			<div class="user_table" style="font:bold 12px/20px arial,sans-serif;">Total:1</div>
				<table id="viewEvsDistributionRateInfoList_table_${currentIndex }" class="list" width="1320px;">
					<thead>
						<tr>
							<c:forEach items="${gradeList}" var="item" varStatus="i">
									<th width="100px" id="score_${i.index }" spVal="${item.EVS_GRADE_NAME }">${item.EVS_GRADE_NAME}
										<%-- <c:choose>
											<c:when test="${item.EVS_GRADE_NAME == 'A'}">S</c:when>
											<c:when test="${item.EVS_GRADE_NAME == 'B'}">A</c:when>
											<c:when test="${item.EVS_GRADE_NAME == 'C'}">B+</c:when>
											<c:when test="${item.EVS_GRADE_NAME == 'D'}">B</c:when>
											<c:when test="${item.EVS_GRADE_NAME == 'E'}">C</c:when>
										</c:choose> --%>
									</th>
							</c:forEach>
							<th width="100px"><spring:message code="ess.viewpersonalpainfo.heji"/><!--合计--></th>
							<th width="200px"><spring:message code="hrm.empinfo.UPDATED_BY"/><!--变更者--></th>
							<th width="150px"><spring:message code="org.title.UPDATE_DATE"/><!--变更时间--></th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<c:forEach items="${gradeList}" var="item" varStatus="i">
									<td sysLog="text" name="score" id="${item.EVS_GRADE_ORIGINAL}">${scoreList[0][item.EVS_GRADE_ORIGINAL]}</td>
							</c:forEach>
							<td id="total_${currentIndex }"></td>
							<td>${scoreList[0].UPDATED_BY}</td>
							<td>${scoreList[0].UPDATE_DATE}<div id="SEQ_${currentIndex }" style="display:none;">${scoreList[0].SEQ}</div></td>
						</tr>
					</tbody>
				</table>
</div>
</c:if>

<c:if test="${currentIndex eq '1'}">
<div class="pageContent">
			<div class="user_table">
				<div style="float:right;height:25px;line-height:25px;">
					<a class="w_button" onclick="openOnRight('/evs/manage/viewEvsDistributionRateInfoList?evsType=${evsType }&RESUME_SEQ=${RESUME_SEQ }&SCHEDULE_TYPE=${SCHEDULE_TYPE }&currentIndex=${currentIndex }&FLAG=ADD','viewEvsDistributionRateInfoList_${currentIndex }')"><span><spring:message code="ess.empInfo.insert"/><!--添加--></span></a>
					<a class="w_button" href="/evs/manage/deleteScheduleInfo?SEQ={SEQ}" target="ajaxTodo" callback="navTabAjaxDoneWithForm" title="<spring:message code="ar.viewRetrieveSqlMasterList.QUEDINGYAOSHANCHUMA.b"/>"><span><spring:message code="ess.empInfo.Delete"/><!--删除--></span></a>
					<a class="w_button" id="viewEvsDistributionRateInfoList_save${currentIndex}"><span><spring:message code="org.title.SAVE"/><!--保存--></span></a>
				</div>
			</div>
			<div class="user_table" style="font:bold 12px/20px arial,sans-serif;">Total:${scheduleInfoListSize}</div>
				<table id="viewEvsDistributionRateInfoList_table_${currentIndex }"  class="list" width="1000px;">
					<thead>
						<tr>
							<th width="30px">No.</th>
							<th width="120px"><spring:message code="org.title.dept"/><!--部门--></th>
							<th width="80px"><spring:message code="org.title.DEPT_CODE"/><!--部门代码--></th>
							<th width="80px"><spring:message code="evs.viewEvsParamInfoList.PINGJIABIAOLEIXING.a"/><!--评价表类型--></th>
							<th width="80px"><spring:message code="evs.viewEvsScheduleInfoList.PINGJIAJINXINGQUFEN.a"/><!--评价进行区分--></th>
							<th width="80px"><spring:message code="ar.attendanceView.viewNoSwipingCard.beginTime"/><!--开始日期--></th>
							<th width="80px"><spring:message code="ar.attendanceView.viewNoSwipingCard.endTime"/><!--结束日期--></th>
							<th width="50px"><spring:message code="empsubject.useY"/><!--使用--><input type="checkbox" class="checkbox" group="viewEvsDistributionRateInfoPanel_checkbox${currentIndex }"/></th>
							<th width="180px"><spring:message code="hrm.empinfo.UPDATED_BY"/><!--变更者--></th>
							<th width="120px"><spring:message code="org.title.UPDATE_DATE"/><!--变更时间--></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${scheduleInfoList}" var="item" varStatus="i">
							<tr target="SEQ" rel="${item.SEQ}">
								<td class='td_center'>${i.count}</td>
								<td>${item.NO}</td>
								<td>${item.NAME}</td>
								<td>${item.EVS_OBJECT_NAME}</td>
								<td>${item.EVS_STEP_NAME}</td>
								<td sysLog="date" sysIndex="${i.index}" format="yyyy.MM.dd" id="START_DATE_${currentIndex }_${i.index}" >${item.START_DATE }</td>
								<td sysLog="date" sysIndex="${i.index}" format="yyyy.MM.dd" id="END_DATE_${currentIndex }_${i.index}" >${item.END_DATE}</td>
								<td class='td_center'><input type="checkbox" id="viewEvsDistributionRateInfoPanel_checkbox${currentIndex }_${i.index}" value="1"<c:if test="${item.ACTIVITY eq '1' }">checked="checked"</c:if>/></td>
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
			<div class="user_table">
				<div style="float:right;height:25px;line-height:25px;">
					<a class="w_button" onclick="openOnRight('/evs/manage/viewEvsDistributionRateInfoList?evsType=${evsType }&RESUME_SEQ=${RESUME_SEQ }&SCHEDULE_TYPE=${SCHEDULE_TYPE }&currentIndex=${currentIndex }&FLAG=ADD','viewEvsDistributionRateInfoList_${currentIndex }')"><span><spring:message code="ess.empInfo.insert"/><!--添加--></span></a>
					<a class="w_button" href="/evs/manage/deleteScheduleInfo?SEQ={SEQ}" target="ajaxTodo" callback="navTabAjaxDoneWithForm" title="<spring:message code="ar.viewRetrieveSqlMasterList.QUEDINGYAOSHANCHUMA.b"/>"><span><spring:message code="ess.empInfo.Delete"/><!--删除--></span></a>
					<a class="w_button" id="viewEvsDistributionRateInfoList_save${currentIndex}"><span><spring:message code="org.title.SAVE"/><!--保存--></span></a>
				</div>
			</div>
			<div class="user_table" style="font:bold 12px/20px arial,sans-serif;">Total:${scheduleInfoListSize}</div>
				<table id="viewEvsDistributionRateInfoList_table_${currentIndex }"  class="list" width="1100px;">
					<thead>
						<tr>
							<th width="30px">No.</th>
							<th width="100px"><spring:message code="org.title.dept"/><!--部门--></th>
							<th width="80px"><spring:message code="empsubject.empno"/><!--社号--></th>
							<th width="80px"><spring:message code="empsubject.userNm"/><!--姓名--></th>
							<th width="80px"><spring:message code="hrm.contract.Rank"/><!--职级--></th>
							<th width="80px"><spring:message code="evs.viewEvsScheduleInfoList.PINGJIAJINXINGQUFEN.a"/><!--评价进行区分--></th>
							<th width="80px"><spring:message code="ar.attendanceView.viewNoSwipingCard.beginTime"/><!--开始日期--></th>
							<th width="80px"><spring:message code="ar.attendanceView.viewNoSwipingCard.endTime"/><!--结束日期--></th>
							<th width="50px"><spring:message code="empsubject.useY"/><!--使用--><input type="checkbox" class="checkbox" group="viewEvsDistributionRateInfoPanel_checkbox${currentIndex }"/></th>
							<th width="180px"><spring:message code="hrm.empinfo.UPDATED_BY"/><!--变更者--></th>
							<th width="120px"><spring:message code="org.title.UPDATE_DATE"/><!--变更时间--></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${scheduleInfoList}" var="item" varStatus="i">
							<tr target="SEQ" rel="${item.SEQ}">
								<td class='td_center'>${i.count}</td>
								<td>${item.DEPTNAME}</td>
								<td>${item.NO}</td>
								<td>${item.NAME}</td>
								<td>${item.POST_GRADE_NAME}</td>
								<td>${item.EVS_STEP_NAME}</td>
								<td sysLog="date" sysIndex="${i.index}" format="yyyy.MM.dd" id="START_DATE_${currentIndex }_${i.index}" >${item.START_DATE }</td>
								<td sysLog="date" sysIndex="${i.index}" format="yyyy.MM.dd" id="END_DATE_${currentIndex }_${i.index}" >${item.END_DATE}</td>
								<td class='td_center'><input type="checkbox" id="viewEvsDistributionRateInfoPanel_checkbox${currentIndex }_${i.index}" value="1"<c:if test="${item.ACTIVITY eq '1' }">checked="checked"</c:if>/></td>
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