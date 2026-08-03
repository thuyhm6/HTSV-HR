<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
$(document).ready(function(){
	$("#viewEvsBySelfSSTAbilityResumeNo",navTab.getCurrentPanel()).change(function(){
		$("#viewEvsBySelfSSTAbilityForm",navTab.getCurrentPanel()).submit();
	});
});
function sumObjectTargetScoreSSTAbility(){
	var total = 0;
	$('select:[name="EVS_SCORE"]',navTab.getCurrentPanel()).each(function(i, obj){
		if($(obj).val() != ''){
			total += parseFloat($(obj).val()) * parseFloat($(obj).attr("sysItemScore")) / parseFloat($(obj).find("option:last").val());
		}
	});
	$("#objectTargetSumAffirmScore",navTab.getCurrentPanel()).html(total.toFixed(2));

	$('#EVS_GRADE option',navTab.getCurrentPanel()).each(function(i, obj){
		if(i>0){
			if(total >= parseFloat($(obj).val())){
				$(obj).attr("selected","selected");
				return false;
			}
		}
	});
}

function saveEvsBySelfSSTAbility(flag){
	var msg = "<spring:message code='evs.viewAffirmTarget1.LINGSHIBAOCUN.a'/>";//临时保存
	if(flag == 1){
		msg = "<spring:message code='evs.viewConfirmTargetInfoAbility.SHIXING.a'/>";//实行
	}
	var form = $("#viewEvsBySelfSSTAbilitySaveForm",navTab.getCurrentPanel());
	if (!form.valid()) {
		return false;
	}
	//获取页面的值
	var jsonData = '[';
	$('select:[name="EVS_SCORE"]',navTab.getCurrentPanel()).each(function(i, obj){
		if (jsonData.length > 1) {
			jsonData += ',{';
		} else {
			jsonData += '{';
		}
		jsonData += ' "EVS_SCORE": "' + $(obj).val() + '" ,';
		jsonData += ' "ITEM_SEQ": "' + $(obj).attr("sysIndex") + '" ,';
		jsonData += ' "SEQ": "${viewEvsAffirmInfo.SEQ }" ,';
		jsonData += ' "RESUME_SEQ": "${RESUME_SEQ}" ,';
		jsonData += ' "adminID": "' + '${LoginUser.adminID}' + '" ,';
		jsonData += ' "adminIP": "' + '${LoginUser.adminIP}' + '" ,';
		jsonData += ' "interCpnyID": "' + '${LoginUser.cpnyId}' + '" ';
		jsonData += '}';
	});
	jsonData += ']';
                        //确定要																						吗？
	alertMsg.confirm("<spring:message code='ess.viewMonthDetailConfirmList.QUEDINGYAO.a'/>" + msg + "<spring:message code='ess.viewMonthDetailConfirmList.MAO.a'/>",
  		{okCall:function(){
		  	$.ajax({
  				type: 'POST',
  				url: '/evs/manage/addEvsBySelfTSTOAbility',
  				data:[{ name: 'jsonData', value: jsonData },
  				      { name: 'AFFIRM_CONTENT', value: $("#AFFIRM_CONTENT",navTab.getCurrentPanel()).val() },
  				      { name: 'EVS_GRADE', value: $("#EVS_GRADE",navTab.getCurrentPanel()).find("option:selected").text()},
  				      { name: 'EVS_POINT', value: $("#objectTargetSumAffirmScore",navTab.getCurrentPanel()).html() },
  				      { name: 'SEQ', value: '${viewEvsAffirmInfo.SEQ }' },
  				      { name: 'EVS_OBJECT_SEQ', value: '${viewEvsObjectInfo.SEQ}' },
  				      { name: 'FLAG', value: flag }],
  				dataType:"json",
  				cache: false,
  				success: navTabAjaxDoneWithForm,
  				error: DWZ.ajaxError
  		});
  	}});
}
	
function openEvsInfoWindow(empIdsStr,personId){
	window.open('/evs/manage/viewConfirmTargetInfoPrint?APPLY_PERSON_ID='+personId+'&EVS_PERSON_ID_BATCH=' + empIdsStr + '&RESUME_SEQ=${RESUME_SEQ}&ACTIVITY=14015359','newwindow','status=no,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,width=1400,height=600');
}

</script>
<c:if test="${not empty resumeList}">
<div class="pageHeader">
	<form id="viewEvsBySelfSSTAbilityForm" onsubmit="return navTabSearch(this);" action="/evs/manage/viewEvsBySelfSSTAbility" method="post" >
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td><spring:message code="evs.viewResumeList.PINGJIAMING.a"/><!--评价名--></td>
					<td>
						<select id="viewEvsBySelfSSTAbilityResumeNo" name="RESUME_SEQ">
							<c:forEach items="${resumeList}" var="result">
								<option value="${result.SEQ}" <c:if test="${result.SEQ eq RESUME_SEQ}">selected</c:if>>${result.RESUME_NAME}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>
							</c:forEach>
						</select>
						<input type="hidden" name="evsType" value="${evsType }">
						<input type="hidden" name="seach_LIMIT" value="${LIMIT }">
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">
								<spring:message code="button.search"/><!--查询-->
								</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</div>
	</form>
</div>

<div class="pageContent">
	<form id="viewEvsBySelfSSTAbilitySaveForm" action="/evs/manage/addRegPersonalTarget" method="post">
	<div style="padding-left:10px;padding-right:10px;padding-top:5px;">
		<%@ include file="/WEB-INF/view/evs/manage/viewPersonalInfoHead_evs.jsp"%>
		<div style="font:bold 14px/20px arial,sans-serif;float:left;height:20px;line-height:20px;">SECTION 1</div>
		<c:if test="${viewEvsObjectInfo.ACTIVITY eq '14015356'}">
		<div style="float:right;height:20px;line-height:20px;margin-top:5px;">
			<a class="w_button" onclick="saveEvsBySelfSSTAbility(0)"><span><spring:message code="evs.viewAffirmTarget1.LINGSHIBAOCUN.a"/><!--临时保存--></span></a>
			<a class="w_button" onclick="saveEvsBySelfSSTAbility(1)"><span><spring:message code="evs.viewConfirmTargetInfoAbility.SHIXING.a"/><!--实行--></span></a>
		</div>
		</c:if>
		<table class="user_table" width="100%">	
			<tr>
				<td class="td_title"  style="text-align:center;" width="5%">No</td>
				<td class="td_title"  style="text-align:center;" width="15%"><spring:message code="display.emp.ben.or.benhs67"/><!--区分--></td>
				<td class="td_title"  style="text-align:center;" width="15%"><spring:message code="evs.viewConfirmTargetInfoAbility.KAOHEXIANGMU.a"/><!--考核项目--></td>
				<td class="td_title"  style="text-align:center;" width="45%"><spring:message code="evs.viewConfirmTargetInfoAbility.KAOHEZHIBIAO.a"/><!--考核指标--></td>
				<td class="td_title" style="text-align:center;" width="10%"><spring:message code="evs.viewConfirmTargetInfoAbility.CEZHONGZHI.a"/><!--测重值--></td>
				<td class="td_title" style="text-align:center;" width="10%"><spring:message code="evs.viewEvsIndex.BENRENPINGJIA.a"/><!--本人评价--></td>
			</tr>
			<c:if test="${viewEvsObjectInfo.ACTIVITY eq '14015356'}">
			<c:forEach items="${viewEvsItem}" var="item" varStatus="i">
				<tr>
					<td class="td_type" style="text-align:center">${i.count}</td>
			    	<td class="td_type">${item.GROUP_NAME }</td>
			    	<c:if test="${LoginUser.language eq 'ko' }">
			    	<td class="td_type">${item.ITEM_NAME_KO }</td>
			    	<td class="td_type">${item.REMARK_KO }</td>
			    	</c:if>
			    	<c:if test="${LoginUser.language ne 'ko'}">
			    	<td class="td_type">${item.ITEM_NAME }</td>
			    	<td class="td_type">${item.REMARK }</td>
			    	</c:if>
			    	<td class="td_type" style="text-align:right" sysLong="ITEM_SCORE">${item.ITEM_SCORE }</td>
			    	<td class="td_type" style="text-align:center">
			    		<select name="EVS_SCORE" style="width:100px" sysIndex="${item.SEQ }" sysItemScore="${item.ITEM_SCORE }" onchange="sumObjectTargetScoreSSTAbility()">
							<option value=""></option>
							<c:forEach items="${viewEvsGradeItem}" var="itemGrade" varStatus="i">
								<option value="${itemGrade.EVS_SCORE }" <c:if test="${itemGrade.EVS_SCORE eq item.EVS_SCORE0}">selected="selected"</c:if>>${itemGrade.CODE_NAME}</option>
							</c:forEach>
						</select>
			    	</td>
				</tr>
			</c:forEach>
			</c:if>
			<c:if test="${viewEvsObjectInfo.ACTIVITY ne '14015356'}">
			<c:forEach items="${viewEvsItem}" var="item" varStatus="i">
				<tr>
					<td class="td_type" style="text-align:center">${i.count}</td>
			    	<td class="td_type">${item.GROUP_NAME }</td>
			    	<c:if test="${LoginUser.language eq 'ko' }">
			    	<td class="td_type">${item.ITEM_NAME_KO }</td>
			    	<td class="td_type">${item.REMARK_KO }</td>
			    	</c:if>
			    	<c:if test="${LoginUser.language ne 'ko'}">
			    	<td class="td_type">${item.ITEM_NAME }</td>
			    	<td class="td_type">${item.REMARK }</td>
			    	</c:if>
			    	<td class="td_type" style="text-align:right">${item.ITEM_SCORE }</td>
			    	<td class="td_type" style="text-align:right">${item.EVS_SCORE0 }</td>
				</tr>
			</c:forEach>
			</c:if>
			<tr>
				<td class="td_title"></td>
				<td class="td_title"></td>
				<td class="td_title"></td>
				<td class="td_title"></td>
				<td class="td_title" style="text-align:right;">100</td>
				<td class="td_title" style="text-align:right;" id="objectTargetSumAffirmScore">${viewEvsAffirmInfo.EVS_POINT }</td>
			</tr>
		</table>
	</div>
	<div style="padding-left:10px;padding-right:10px;padding-top:5px;padding-bottom:30px;">
		<div style="font:bold 14px/20px arial,sans-serif;float:left;height:20px;line-height:20px;">SECTION 2</div>
		<table class="user_table" width="100%">
			<tr id="rowIdEvsBySelf_100">
				<td class="td_title" style="text-align:center;" width="85%">Comment</td>
				<td class="td_title" style="text-align:center; visibility: hidden" width="15%">self-Rating</td>
			</tr>
			<c:if test="${viewEvsObjectInfo.ACTIVITY eq '14015356'}">
			<tr id="rowIdEvsBySelf_100">
				<td><textarea style="width:100%;height:100px" id="AFFIRM_CONTENT" class="editor required" tools="Cut,Copy,Paste,|,Fullscreen">${viewEvsAffirmInfo.AFFIRM_CONTENT }</textarea></td>
				<td style="text-align:center; visibility: hidden">
					<select id="EVS_GRADE" style="width:200px; visibility: hidden" disabled="disabled">
						<option value=""></option>
						<c:forEach items="${viewGradeList}" var="item" varStatus="i">
							<option value="${item.START_SCORE }" name="${item.EVS_GRADE_ORIGINAL}" <c:if test="${item.EVS_GRADE_NAME eq viewEvsAffirmInfo.EVS_GRADE}">selected="selected"</c:if>>${item.EVS_GRADE_NAME}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			</c:if>
			<c:if test="${viewEvsObjectInfo.ACTIVITY ne '14015356'}">
			<tr id="rowIdEvsBySelf_100">
				<td class="td_type">${viewEvsAffirmInfo.AFFIRM_CONTENT }</td>
				<td class="td_type" style="text-align:center; visibility: hidden">${viewEvsAffirmInfo.EVS_GRADE}</td>
			</tr>
			</c:if>
		</table>
		<c:if test="${viewEvsObjectInfo.ACTIVITY eq '14015361'}"> <!-- Khi nào kết thúc đánh giá mới cho xem kết quả -->
		<div style="float:right;height:20px;line-height:20px;margin-top:5px;">
			<a class="w_button" href="#" style="color:blue;" onclick="openEvsInfoWindow(${viewEvsObjectInfo.SEQ},${LoginUser.adminID })">
			<span><spring:message code="ar.viewEmpInfoListTanchu.YEJIKAOPINGJIEGUO.b"/></span></a>
		</div>
		</c:if>
	</div>
	</form>
</div>
</c:if>
<c:if test="${empty resumeList}">
	<%@ include file="/WEB-INF/view/evs/manage/no_evs.jsp"%>
</c:if>
