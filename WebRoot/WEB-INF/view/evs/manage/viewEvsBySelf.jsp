<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
$(document).ready(function(){
	//查询
	$("#viewEvsBySelf_Serch",navTab.getCurrentPanel()).click(function(){
		$("#viewEvsBySelfForm",navTab.getCurrentPanel()).submit();
	});
	$("#viewEvsBySelf_SEQ",navTab.getCurrentPanel()).change(function(){
		$("#viewEvsBySelfForm",navTab.getCurrentPanel()).submit();
	});
});
function sumObjectTargetScoreSelf(objThis){
	if(objThis.value > 100 || objThis.value <= 0){
		alertMsg.error("<spring:message code='evs.viewEvsBySelf.FENSHUBIXUZAI.a'/>");//分数必须在0~100之间，请确认
		objThis.value=0;
		return false;
	}
	var total = 0;
	$('input:[name="ITEM_SCORE"]',navTab.getCurrentPanel()).each(function(i, obj){
		if($(obj).val() != ''){
			total += parseInt($(obj).val()) * parseInt($("#ITEM_SCORE_" + i).html())/100;
		}
	});
	$("#objectTargetSumSelf",navTab.getCurrentPanel()).html(total.toFixed(1));
	$("#objectTargetSumSelfInput",navTab.getCurrentPanel()).val(total.toFixed(1));
	$('#EVS_GRADE option',navTab.getCurrentPanel()).each(function(i, obj){
		if(i>0){
			if(total > parseInt($(obj).val())){
				$(obj).attr("selected","selected");
				return false;
			}
		}
	});
}

function saveEvsBySelf(flag){
	var msg = "<spring:message code='evs.viewAffirmTarget1.LINGSHIBAOCUN.a'/>";//临时保存
	if(flag == 1){
		msg = "<spring:message code='evs.viewConfirmTargetInfoAbility.SHIXING.a'/>";//实行
	}
	var form = $("#viewEvsBySelfSaveForm");
	if (!form.valid()) {
		return false;
	}
	//获取页面的值
	var jsonData = '[';
	$('input:[name="ITEM_SCORE"]',navTab.getCurrentPanel()).each(function(i, obj){
		if (jsonData.length > 1) {
			jsonData += ',{';
		} else {
			jsonData += '{';
		}
		jsonData += ' "SEQ": "' + $("#ITEM_SEQ_" + i,navTab.getCurrentPanel()).val() + '" ,';
		jsonData += ' "EVS_SCORE": "' + obj.value + '" ,';
		jsonData += ' "adminID": "' + '${LoginUser.adminID}' + '" ,';
		jsonData += ' "adminIP": "' + '${LoginUser.adminIP}' + '" ,';
		jsonData += ' "interCpnyID": "' + '${LoginUser.cpnyId}' + '" ';
		jsonData += '}';
	});
	jsonData += ']';
	                                                  //确定要										吗？	
	alertMsg.confirm("<spring:message code='ess.viewMonthDetailConfirmList.QUEDINGYAO.a'/>" + msg + "<spring:message code='ess.viewMonthDetailConfirmList.MAO.a'/>",
  		{okCall:function(){
		  	$.ajax({
  				type: 'POST',
  				url: '/evs/manage/addEvsBySelf',
  				data:[{ name: 'jsonData', value: jsonData },
  				      { name: 'AFFIRM_CONTENT', value: $("#AFFIRM_CONTENT",navTab.getCurrentPanel()).val() },
  				      { name: 'EVS_GRADE', value: $("#EVS_GRADE",navTab.getCurrentPanel()).find("option:selected").text() },
  				      { name: 'EVS_POINT', value: $("#objectTargetSumSelfInput",navTab.getCurrentPanel()).val() },
  				      { name: 'SEQ', value: $("#EVS_AFFIRM_SEQ",navTab.getCurrentPanel()).val() },
  				      { name: 'EVS_OBJECT_SEQ', value: '${viewEvsObjectInfo.SEQ}' },
  				      { name: 'FLAG', value: flag }],
  				dataType:"json",
  				cache: false,
  				success: navTabAjaxDoneWithForm,
  				error: DWZ.ajaxError
  		});
  	}});
}
</script>
<c:if test="${not empty resumeList}">
<div class="pageHeader">
	<form id="viewEvsBySelfForm" onsubmit="return navTabSearch(this);" action="/evs/manage/viewEvsBySelf" method="post" >
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td><spring:message code="evs.viewResumeList.PINGJIAMING.a"/><!--评价名--></td>
					<td>
						<select id="viewEvsBySelfResumeNo" name="RESUME_SEQ">
							<c:forEach items="${resumeList}" var="result">
								<option value="${result.SEQ}" <c:if test="${result.SEQ eq RESUME_SEQ}">selected</c:if>>${result.RESUME_NAME}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>
							</c:forEach>
						</select>
						<input type="hidden" name="evsType" value="${evsType }">
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li>
						<a class="buttonActive" id="viewEvsBySelf_search" href="#">
							<span><spring:message code="button.search"/><!--查询--></span>
						</a>
					</li>
				</ul>
			</div>
		</div>
	</form>
</div>

<div class="pageContent">
	<form id="viewEvsBySelfSaveForm" action="/evs/manage/viewEvsBySelf" method="post" >
	<div style="padding-left:10px;padding-right:10px;padding-top:5px;">
	<%@ include file="/WEB-INF/view/evs/manage/viewPersonalInfoHead_evs.jsp"%>
		<div style="font:bold 14px/20px arial,sans-serif;float:left;height:20px;line-height:20px;">SECTION 1</div>
		<c:if test="${viewEvsObjectInfo.ACTIVITY eq '14015356'}">
		<div style="float:right;height:20px;line-height:20px;margin-top:5px;">
			<a class="w_button" onclick="saveEvsBySelf(0)"><span><spring:message code="evs.viewAffirmTarget1.LINGSHIBAOCUN.a"/><!--临时保存--></span></a>
			<a class="w_button" onclick="saveEvsBySelf(1)"><span><spring:message code="evs.viewConfirmTargetInfoAbility.SHIXING.a"/><!--实行--></span></a>
		</div>
		</c:if>
		<table class="user_table" width="100%">	
			<tr id="rowIdEvsBySelf_100">
				<td class="td_title"  style="text-align:center;" width="5%">No</td>
				<td class="td_title"  style="text-align:center;" width="25%"><spring:message code="inct.salesman.evaluationItemType"/><!--评价项目--></td>
				<td class="td_title" style="text-align:center;" width="30%"><spring:message code="inct.salesman.eval.personal.target"/><!--目标--></td>
				<td class="td_title" style="text-align:center;" width="25%"><spring:message code="empsubject.gradeRate"/><!--比率-->(%)</td>
				<td class="td_title" style="text-align:center;" width="15%"><spring:message code="button.search"/><!--达成率-->(%)</td>
			</tr>
			<c:forEach items="${viewSSTEvsItem}" var="item" varStatus="i">
				<c:if test="${viewEvsObjectInfo.ACTIVITY eq '14015356'}">
				<tr id="rowIdEvsBySelf_${i.index }">
					<td class="td_type" style="text-align:center">${i.count}</td>
			    	<td class="td_type">${item.ITEM_NAME }</td>
			    	<td class="td_type">${item.ITEM_CONTENT }</td>
			    	<td class="td_type" style="text-align:right" id="ITEM_SCORE_${i.index }">${item.ITEM_SCORE }</td>
					<td class="td_type" style="text-align:center">
						<input name="ITEM_SCORE" value="${item.EVS_SCORE }" type="text" size="25" onblur="sumObjectTargetScoreSelf(this)" class="required number" min="0" max="100">
						<input id="ITEM_SEQ_${i.index }" type="hidden" value="${item.SEQ }">
					</td>
				</tr>
				</c:if>
				<c:if test="${viewEvsObjectInfo.ACTIVITY ne '14015356'}">
				<tr id="rowIdEvsBySelf_${i.index }">
					<td class="td_type" style="text-align:center">${i.count}</td>
			    	<td class="td_type">${item.ITEM_NAME }</td>
			    	<td class="td_type">${item.ITEM_CONTENT }</td>
			    	<td class="td_type" style="text-align:right" id="ITEM_SCORE_${i.index }">${item.ITEM_SCORE }</td>
					<td class="td_type" style="text-align:right">${item.EVS_SCORE }</td>
				</tr>
				</c:if>
			</c:forEach>
			<tr id="rowIdEvsBySelf_101">
				<td class="td_title"></td>
				<td class="td_title"></td>
				<td class="td_title"></td>
				<td class="td_title" style="text-align:right;">100</td>
				<td class="td_title" style="text-align:right;" id="objectTargetSumSelf">${viewEvsAffirmInfo.EVS_POINT }</td>
			</tr>
		</table>
		<input type="hidden" id="objectTargetCnt" name="objectTargetCnt" value="${objectTargetCnt}"/>
		<input type="hidden" name="RESUME_SEQ" value="${RESUME_SEQ }"/>
		<input type="hidden" name="EVS_OBJECT_SEQ" value="${viewEvsObjectInfo.SEQ }"/>
		<input type="hidden" id="EVS_AFFIRM_SEQ" value="${viewEvsAffirmInfo.SEQ }"/>
		<input type="hidden" id="objectTargetSumSelfInput" name="EVS_SCORE_SELF" value="${viewEvsAffirmInfo.EVS_POINT }"/>
	</div>
	<div style="padding-left:10px;padding-right:10px;padding-top:5px;padding-bottom:30px;">
		<div style="font:bold 14px/20px arial,sans-serif;float:left;height:20px;line-height:20px;">SECTION 2</div>
		<table class="user_table" width="100%">
			<tr id="rowIdEvsBySelf_100">
				<td class="td_title"  style="text-align:center;" width="70%">Comment</td>
				<td class="td_title" style="text-align:center;" width="30%">self-Rating</td>
			</tr>
			<c:if test="${viewEvsObjectInfo.ACTIVITY eq '14015356'}">
			<tr id="rowIdEvsBySelf_100">
				<td><textarea style="width:100%;height:100px" id="AFFIRM_CONTENT" class="editor required" tools="Cut,Copy,Paste,|,Fullscreen">${viewEvsAffirmInfo.AFFIRM_CONTENT }</textarea></td>
				<td style="text-align:center;">
					<select id="EVS_GRADE" style="width:200px" disabled="disabled">
						<option value=""></option>
						<c:forEach items="${viewGradeList}" var="item" varStatus="i">
							<option value="${item.START_SCORE }" <c:if test="${item.EVS_GRADE_NAME eq viewEvsAffirmInfo.EVS_GRADE}">selected="selected"</c:if>>${item.EVS_GRADE_NAME}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			</c:if>
			<c:if test="${viewEvsObjectInfo.ACTIVITY ne '14015356'}">
			<tr id="rowIdEvsBySelf_100">
				<td class="td_type">${viewEvsAffirmInfo.AFFIRM_CONTENT }</td>
				<td class="td_type" style="text-align:right;">${viewEvsAffirmInfo.EVS_GRADE}</td>
			</tr>
			</c:if>
		</table>
	</div>
	</form>
</div>
</c:if>
<c:if test="${empty resumeList}">
	<%@ include file="/WEB-INF/view/evs/manage/no_evs.jsp"%>
</c:if>
