<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
$(document).ready(function(){
	$("#viewRegPersonalTarget_SEQ").change(function(){
		$("#viewRegPersonalTargetForm",navTab.getCurrentPanel()).submit();
	});
	<c:if test="${viewEvsObjectInfo.ACTIVITY eq '14015354' or viewEvsObjectInfo.ACTIVITY eq '14015362'}">
	sumObjectTargetScore();
	</c:if>
	<c:if test="${viewEvsObjectInfo.ACTIVITY ne '14015354' and viewEvsObjectInfo.ACTIVITY ne '14015362'}">
	var total = 0;
	$('td:[sysLong="ITEM_SCORE"]',navTab.getCurrentPanel()).each(function(i, obj){
		if($(obj).html() != ''){
			total += parseInt($(obj).html());
		}
	});
	$("#objectTargetSum",navTab.getCurrentPanel()).html(total);
	</c:if>
});

//添加决裁者
function addRowByIDObjectTarget(currentRowID){
	var count = parseInt($("#objectTargetCnt").val());
    var htm  ='<tr id="rowIdObjectTarget_' + count + '"><td class="td_type" style="text-align:center"></td>';
    htm +='<td class="td_type" style="text-align:center"><input name="ITEM_NAME" value="" type="text" size="23" class="required"></td>';
    htm +='<td class="td_type" style="text-align:center"><textarea style="width:99.5%;height:100px" name="ITEM_CONTENT" class="editor required" tools="Cut,Copy,Paste,|,Fullscreen"></textarea></td>';
    htm +='<td class="td_type" style="text-align:center"><input type="text" id="START_DATE" name="START_DATE" class="Wdate required" onClick="WdatePicker({dateFmt:\'yyyy.MM.dd\'})"/></td>';
    htm +='<td class="td_type" style="text-align:center"><input type="text" id="END_DATE" name="END_DATE" class="Wdate required" onClick="WdatePicker({dateFmt:\'yyyy.MM.dd\'})"/></td>';
    htm +='<td class="td_type" style="text-align:center"><input name="ITEM_SCORE" value="0" type="text" size="13" onblur="sumObjectTargetScore()" class="required number" min="0" max="100"></td>';
	htm +='<td class="td_type" style="text-align: center">';
	htm +='<img src="/resources/images/+.gif" title="<spring:message code="button.add"/>"';	//添加
	htm +='border="0" align="absmiddle" style="cursor:hand" onclick="addRowByIDObjectTarget(' + count + ')"/>&nbsp;&nbsp;&nbsp;';
	htm +='<img src="/resources/images/-.gif" title="<spring:message code="button.delete"/>"';	//删除
	htm +='border="0" align="absmiddle" style="cursor:hand" onclick="javaScript:document.all.viewRegPersonalTarget_table.deleteRow(event.srcElement.parentElement.parentElement.rowIndex);changeObjectTargetLevel();sumObjectTargetScore();"/></td></tr>';

   	//当前行之后插入一行
   	$("#rowIdObjectTarget_" + currentRowID).after(htm);

   	var op = {html5Upload:false, skin: 'default',tools: 'Cut,Copy,Paste,|,Fullscreen'};
   	$("#rowIdObjectTarget_" + count).find("textarea").xheditor(op);
   	
   	changeObjectTargetLevel();
  	$("#objectTargetCnt").val(++count) ;
}
//修改No
function changeObjectTargetLevel(){
	var tb2 = document.getElementById("viewRegPersonalTarget_table");
	var rowCount = tb2.rows.length;
	for(var m=1;m < rowCount - 1;m++){
		tb2.rows[m].cells[0].innerHTML = m;
	}
}
function sumObjectTargetScore(){
	var total = 0;
	$('input:[name="ITEM_SCORE"]',navTab.getCurrentPanel()).each(function(i, obj){
		if($(obj).val() != ''){
			total += parseInt($(obj).val());
		}
	});
	$("#objectTargetSum",navTab.getCurrentPanel()).html(total);
}

function saveObjectTarget(flag){
	var msg = "<spring:message code='evs.viewAffirmTarget1.LINGSHIBAOCUN.a'/>";//临时保存
	if(flag == 1){
		if($("#objectTargetSum",navTab.getCurrentPanel()).html() != 100){
			alertMsg.error("<spring:message code='evs.viewRegPersonalProbation.BILVBUSHIBAIFENZHIBAI.a'/>");//比率不是100%,请确认
			return false;
		}
		msg = "<spring:message code='evs.viewConfirmTargetInfoAbility.SHIXING.a'/>";//实行
	}
	$("#objectTargetFlag",navTab.getCurrentPanel()).val(flag);
	var form = $("#viewRegPersonalTargetSaveForm",navTab.getCurrentPanel());
	if (!form.valid()) {
		return false;
	}								//确定要 																			吗？
	alertMsg.confirm("<spring:message code='ess.viewMonthDetailConfirmList.QUEDINGYAO.a'/>" + msg + "<spring:message code='ess.viewMonthDetailConfirmList.MAO.a'/>",
  		{okCall:function(){
		  	$.ajax({
  				type: 'POST',
  				url: '/evs/manage/addRegPersonalTarget',
  				data:form.serializeArray(),
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
	<form id="viewRegPersonalTargetForm" onsubmit="return navTabSearch(this);" action="/evs/manage/viewRegPersonalTargetTSTO" method="post" >
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td><spring:message code="evs.viewResumeList.PINGJIAMING.a"/><!--评价名--></td>
					<td>
						<select id="viewRegPersonalTargetResumeNo" name="RESUME_SEQ">
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
	<form id="viewRegPersonalTargetSaveForm" action="/evs/manage/addRegPersonalTarget" method="post">
	<div style="padding-left:10px;padding-right:10px;padding-top:5px;">
	<%@ include file="/WEB-INF/view/evs/manage/viewPersonalInfoHead_evs.jsp"%>
		<div style="font:bold 14px/20px arial,sans-serif;float:left;height:20px;line-height:20px;">Objective Confirm</div>
		<c:if test="${viewEvsObjectInfo.ACTIVITY eq '14015354' or viewEvsObjectInfo.ACTIVITY eq '14015362'}">
		<div style="float:right;height:20px;line-height:20px;margin-top:5px;">
			<a class="w_button" onclick="saveObjectTarget(0)"><span><spring:message code="evs.viewAffirmTarget1.LINGSHIBAOCUN.a"/><!--临时保存--></span></a>
			<a class="w_button" onclick="saveObjectTarget(1)"><span><spring:message code="evs.viewConfirmTargetInfoAbility.SHIXING.a"/><!--实行--></span></a>
		</div>
		</c:if>
		<table class="user_table" width="100%" id="viewRegPersonalTarget_table">	
			<tr id="rowIdObjectTarget_100">
				<td class="td_title"  style="text-align:center;" width="5%">No</td>
				<td class="td_title"  style="text-align:center;" width="15%"><spring:message code="inct.salesman.evaluationItemType"/><!--评价项目--></td>
				<td class="td_title" style="text-align:center;" width="35%"><spring:message code="inct.salesman.eval.personal.target"/><!--目标--></td>
				<td class="td_title" style="text-align:center;" width="13%"><spring:message code="ess.infoApply.title.startTime"/><!--开始时间--></td>
				<td class="td_title" style="text-align:center;" width="13%"><spring:message code="evs.viewRegPersonalProbation.WANCHENGSHIJIAN.a"/><!--完成时间--></td>
				<td class="td_title" style="text-align:center;" width="10%"><spring:message code="inct.salesman.ratio"/><!--比率-->(%)</td>
				<c:if test="${viewEvsObjectInfo.ACTIVITY eq '14015354' or viewEvsObjectInfo.ACTIVITY eq '14015362'}">
				<td class="td_title" style="text-align:center;" width="9%"><spring:message code="org.title.ADD"/><!--新增-->(<img src="/resources/images/+.gif" title="<spring:message code="ess.empInfo.insert"/>" border="0" align="absmiddle" style="cursor:hand" onclick="addRowByIDObjectTarget(100)"/>)</td>
				</c:if>
			</tr>
			<c:if test="${viewEvsObjectInfo.ACTIVITY eq '14015354' or viewEvsObjectInfo.ACTIVITY eq '14015362'}">
			<c:forEach items="${viewSSTEvsItem}" var="item" varStatus="i">
				<tr id="rowIdObjectTarget_${i.index }">
					<td class="td_type" style="text-align:center">${i.count}</td>
			    	<td class="td_type" style="text-align:center"><input name="ITEM_NAME" value="${item.ITEM_NAME }" type="text" size="23" class="required"></td>
			    	<td class="td_type" style="text-align:center"><textarea style="width:99.5%;height:100px" name="ITEM_CONTENT"  class="editor required" tools="Cut,Copy,Paste,|,Fullscreen">${item.ITEM_CONTENT }</textarea></td>
			    	<td class="td_type" style="text-align: center"><input type="text" id="START_DATE" name="START_DATE" class="Wdate required" onClick="WdatePicker({dateFmt:'yyyy.MM.dd'})" value="${item.START_DATE }"/></td>
					<td class="td_type" style="text-align: center"><input type="text" id="END_DATE" name="END_DATE" class="Wdate required" onClick="WdatePicker({dateFmt:'yyyy.MM.dd'})" value="${item.END_DATE }"/></td>
			    	<td class="td_type" style="text-align:center"><input name="ITEM_SCORE" value="${item.ITEM_SCORE }" type="text" size="13" onblur="sumObjectTargetScore()" class="required number" min="0" max="100"></td>
					<td class="td_type" style="text-align: center">
						<img src="/resources/images/+.gif" title="<spring:message code="ess.empInfo.insert"/>"	
							border="0" align="absmiddle" style="cursor:hand" onclick="addRowByIDObjectTarget('${i.index }')"/>&nbsp;&nbsp;&nbsp;
						<img src="/resources/images/-.gif" title="<spring:message code="ess.empInfo.Delete"/>"	
							border="0" align="absmiddle" style="cursor:hand" onclick="javaScript:document.all.viewRegPersonalTarget_table.deleteRow(event.srcElement.parentElement.parentElement.rowIndex);changeObjectTargetLevel();sumObjectTargetScore();"/>
					</td>
				</tr>
			</c:forEach>
			</c:if>
			<c:if test="${viewEvsObjectInfo.ACTIVITY ne '14015354' and viewEvsObjectInfo.ACTIVITY ne '14015362'}">
			<c:forEach items="${viewSSTEvsItem}" var="item" varStatus="i">
				<tr>
					<td class="td_type" style="text-align:center">${i.count}</td>
			    	<td class="td_type">${item.ITEM_NAME }</td>
			    	<td class="td_type">${item.ITEM_CONTENT }</td>
			    	<td class="td_type" style="text-align:right" sysLong="ITEM_SCORE">${item.ITEM_SCORE }</td>
				</tr>
			</c:forEach>
			</c:if>
			<tr id="rowIdObjectTarget_101">
				<td class="td_title"></td>
				<td class="td_title"></td>
				<td class="td_title"></td>
				<td class="td_title"></td>
				<td class="td_title"></td>
				<td class="td_title" style="text-align:right;" id="objectTargetSum">0</td>
				<c:if test="${viewEvsObjectInfo.ACTIVITY eq '14015354' or viewEvsObjectInfo.ACTIVITY eq '14015362'}">
				<td class="td_title"></td>
				</c:if>
			</tr>
		</table>
		<input type="hidden" id="objectTargetCnt" name="objectTargetCnt" value="${objectTargetCnt }"/>
		<input type="hidden" name="RESUME_SEQ" value="${RESUME_SEQ }"/>
		<input type="hidden" name="EVS_OBJECT_SEQ" value="${viewEvsObjectInfo.SEQ }"/>
		<input type="hidden" id="objectTargetFlag" name="FLAG" value="0"/>
	</div>
	</form>
</div>
</c:if>
<c:if test="${empty resumeList}">
	<%@ include file="/WEB-INF/view/evs/manage/no_evs.jsp"%>
</c:if>
