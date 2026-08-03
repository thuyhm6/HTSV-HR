<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
$(document).ready(function(){
	$("#viewEvsBySelfHTSVResumeNo",navTab.getCurrentPanel()).change(function(){
		$("#viewEvsBySelfHTSVForm",navTab.getCurrentPanel()).submit();
	});
	<c:if test="${viewEvsObjectInfo.ACTIVITY eq '14015356'}">
	sumObjectTargetScore();
	</c:if>
});

//添加决裁者
function addRowByIDObjectTarget(currentRowID){
	var count = parseInt($("#objectTargetCnt").val());
    var htm  ='<tr id="rowIdObjectTarget_' + count + '"><td class="td_type" style="text-align:center"></td>';
    htm +='<td class="td_type" style="text-align:center"><input name="ITEM_NAME" value="" type="text" size="50" class="required"></td>';
    htm +='<td class="td_type" style="text-align:center"><textarea style="width:99.5%;height:100px" name="ITEM_CONTENT" class="editor required" tools="Cut,Copy,Paste,|,Fullscreen"></textarea></td>';
    htm +='<td class="td_type" style="text-align:center"><input name="ITEM_SCORE" value="0" type="text" size="10" onblur="sumObjectTargetScore()" class="required number" min="0" max="100"></td>';
    htm +='<td class="td_type" style="text-align:center"><input name="EVS_SCORE" value="0" type="text" size="10" onblur="sumObjectTargetScore()" class="required number" min="0" max="100"></td>';
	htm +='<td class="td_type" style="text-align: center">';
	htm +='<img src="/resources/images/+.gif" title="<spring:message code="button.add"/>"';	//添加
	htm +='border="0" align="absmiddle" style="cursor:hand" onclick="addRowByIDObjectTarget(' + count + ')"/>&nbsp;&nbsp;&nbsp;';
	htm +='<img src="/resources/images/-.gif" title="<spring:message code="button.delete"/>"';	//删除
	htm +='border="0" align="absmiddle" style="cursor:hand" onclick="javaScript:document.all.viewEvsBySelfHTSV_table.deleteRow(event.srcElement.parentElement.parentElement.rowIndex);changeObjectTargetLevel();sumObjectTargetScore();"/></td></tr>';

   	//当前行之后插入一行
   	$("#rowIdObjectTarget_" + currentRowID).after(htm);

   	var op = {html5Upload:false, skin: 'default',tools: 'Cut,Copy,Paste,|,Fullscreen'};
   	$("#rowIdObjectTarget_" + count).find("textarea").xheditor(op);
   	changeObjectTargetLevel();
  	$("#objectTargetCnt").val(++count) ;
}
//修改No
function changeObjectTargetLevel(){
	var tb2 = document.getElementById("viewEvsBySelfHTSV_table");
	var rowCount = tb2.rows.length;
	for(var m=1;m < rowCount - 1;m++){
		tb2.rows[m].cells[0].innerHTML = m;
	}
}
function sumObjectTargetScore(){
	var total = 0;
	var evsTotal = 0;
	$('input:[name="ITEM_SCORE"]',navTab.getCurrentPanel()).each(function(i, obj){
		if($(obj).val() != ''){
			total += parseInt($(obj).val());
			evsTotal += parseInt($(obj).val()) * parseInt($(obj).parent().parent().find('input:[name="EVS_SCORE"]').val())
		}
	});
	$("#objectTargetSum",navTab.getCurrentPanel()).html(total);
	if(total == 0){
		$("#objectTargetSumScore",navTab.getCurrentPanel()).html(0);
		$("#EVS_POINT",navTab.getCurrentPanel()).val(0);
		
		$('#EVS_GRADE option',navTab.getCurrentPanel()).each(function(i, obj){
			if(i>0){
				if(total >= parseInt($(obj).val())){
					$(obj).attr("selected","selected");
					return false;
				}
			}
		});
		
	}else{
		$("#objectTargetSumScore",navTab.getCurrentPanel()).html((evsTotal / total).toFixed(1));
		$("#EVS_POINT",navTab.getCurrentPanel()).val((evsTotal / total).toFixed(1));
		
		$('#EVS_GRADE option',navTab.getCurrentPanel()).each(function(i, obj){
			if(i>0){
				if((evsTotal / total) >= parseInt($(obj).val())){
					$(obj).attr("selected","selected");
					return false;
				}
			}
		});
	}
}


function saveEvsBySelf(flag){
	
	var evsGrade = $("#EVS_GRADE",navTab.getCurrentPanel()).find("option:selected").text();
	
	var msg = "<spring:message code='evs.viewAffirmTarget1.LINGSHIBAOCUN.a'/>";//临时保存
	if(flag == 1){
		msg = "<spring:message code='evs.viewConfirmTargetInfoAbility.SHIXING.a'/>";//实行
	}
	var form = $("#viewEvsBySelfHTSVSaveForm");
	if (!form.valid()) {
		return false;
	}
	if($("#objectTargetSum",navTab.getCurrentPanel()).html() != '100'){
		alertMsg.warn("<spring:message code='evs.viewEvsBySelfHTSV.CEZHONGZHIZONGHEBIXUDENGYU.a'/>");//侧重值总和必须等于100
		return false;
	}
	if(parseInt($("#objectTargetSumScore",navTab.getCurrentPanel()).html()) > 100 || parseInt($("#objectTargetSumScore",navTab.getCurrentPanel()).html()) < 20){
		alertMsg.warn("<spring:message code='evs.viewEvsBySelfHTSV.ZIWOPINGJIAFENSHUBIXU.a'/>");//自我评价分数必须在20~100之间
		return false;
	}                //确定要																		吗？
	alertMsg.confirm("<spring:message code='ess.viewMonthDetailConfirmList.QUEDINGYAO.a'/>" + msg + "<spring:message code='ess.viewMonthDetailConfirmList.MAO.a'/>",
  		{okCall:function(){
		  	$.ajax({
  				type: 'POST',
  				url: '/evs/manage/addEvsBySelfHTSV?FLAG=' + flag +'&EVS_GRADE=' + evsGrade,
  				data: form.serializeArray(),
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
	<form id="viewEvsBySelfHTSVForm" onsubmit="return navTabSearch(this);" action="/evs/manage/viewEvsBySelfHTSV" method="post" >
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td><spring:message code="evs.viewResumeList.PINGJIAMING.a"/><!--评价名--></td>
					<td>
						<select id="viewEvsBySelfHTSVResumeNo" name="RESUME_SEQ">
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
	<form id="viewEvsBySelfHTSVSaveForm" action="/evs/manage/addRegPersonalTarget" method="post">
	<div style="padding-left:10px;padding-right:10px;padding-top:5px;">
		<%@ include file="/WEB-INF/view/evs/manage/viewPersonalInfoHead_evs.jsp"%>
		<div style="font:bold 14px/20px arial,sans-serif;float:left;height:20px;line-height:20px;"><spring:message code="evs.viewProbationEvsResult.BUFEN.a"/> 1</div>
		<c:if test="${viewEvsObjectInfo.ACTIVITY eq '14015356'}">
		<div style="float:right;height:20px;line-height:20px;margin-top:5px;">
			<a class="w_button" onclick="saveEvsBySelf(0)"><span><spring:message code="evs.viewAffirmTarget1.LINGSHIBAOCUN.a"/><!--临时保存--></span></a>
			<a class="w_button" onclick="saveEvsBySelf(1)"><span><spring:message code="evs.viewConfirmTargetInfoAbility.SHIXING.a"/><!--实行--></span></a>
		</div>
		</c:if>
		<table class="user_table" width="100%" id="viewEvsBySelfHTSV_table">	
			<tr id="rowIdObjectTarget_100">
				<td class="td_title"  style="text-align:center;" width="5%">No</td>
				<td class="td_title"  style="text-align:center;" width="20%"><spring:message code="inct.salesman.evaluationItemType"/><!--评价项目--></td>
				<td class="td_title"  style="text-align:center;" width="55%"><spring:message code="evs.viewEvsBySelfHTSV.GONGZUONEIRONGZHIBIAOFEIZHIBIAO.a"/><!--工作内容（指标+非指标）--></td>
				<td class="td_title" style="text-align:center;" width="10%"><spring:message code="evs.viewConfirmTargetInfoAbility.CEZHONGZHI.a"/><!--侧重值--></td>
				<td class="td_title" style="text-align:center;" width="10%"><spring:message code="evs.viewConfirmTargetInfoHTSV.BENRENPINGJIAFENSHU.a"/><!--本人评价（分数）--></td>
				<c:if test="${viewEvsObjectInfo.ACTIVITY eq '14015356'}">
				<td class="td_title" style="text-align:center;" width="15%"><spring:message code="org.title.ADD"/><!--新增-->(<img src="/resources/images/+.gif" title="<spring:message code="org.title.INSERT"/>" border="0" align="absmiddle" style="cursor:hand" onclick="addRowByIDObjectTarget(100)"/>)</td>
				</c:if>
			</tr>
			<c:if test="${viewEvsObjectInfo.ACTIVITY eq '14015356'}">
			<c:forEach items="${viewSSTEvsItem}" var="item" varStatus="i">
				<tr id="rowIdObjectTarget_${i.index }">
					<td class="td_type" style="text-align:center">${i.count}</td>
					<td class="td_type" style="text-align:center"><input name="ITEM_NAME" value="${item.ITEM_NAME }" type="text" size="50" class="required"></td>
			    	<td class="td_type"><textarea style="width:99.5%;height:100px" name="ITEM_CONTENT" class="editor required" tools="Cut,Copy,Paste,|,Fullscreen">${item.ITEM_CONTENT }</textarea></td>
			    	<td class="td_type" style="text-align:center"><input name="ITEM_SCORE" value="${item.ITEM_SCORE }" type="text" size="10" onblur="sumObjectTargetScore()" class="required number" min="0" max="100"></td>
			    	<td class="td_type" style="text-align:center"><input name="EVS_SCORE" value="${item.EVS_SCORE }" type="text" size="10" onblur="sumObjectTargetScore()" class="required number" min="0" max="100"></td>
					<td class="td_type" style="text-align: center">
						<img src="/resources/images/+.gif" title="<spring:message code="org.title.INSERT"/>"	
							border="0" align="absmiddle" style="cursor:hand" onclick="addRowByIDObjectTarget('${i.index }')"/>&nbsp;&nbsp;&nbsp;
						<img src="/resources/images/-.gif" title="<spring:message code="button.delete"/>"	
							border="0" align="absmiddle" style="cursor:hand" onclick="javaScript:document.all.viewEvsBySelfHTSV_table.deleteRow(event.srcElement.parentElement.parentElement.rowIndex);changeObjectTargetLevel();sumObjectTargetScore();"/>
					</td>
				</tr>
			</c:forEach>
			</c:if>
			<c:if test="${viewEvsObjectInfo.ACTIVITY ne '14015356'}">
			<c:forEach items="${viewSSTEvsItem}" var="item" varStatus="i">
				<tr>
					<td class="td_type" style="text-align:center">${i.count}</td>
					<td class="td_type">${item.ITEM_NAME }</td>
			    	<td class="td_type">${item.ITEM_CONTENT }</td>
			    	<td class="td_type" style="text-align:right" sysLong="ITEM_SCORE">${item.ITEM_SCORE }</td>
			    	<td class="td_type" style="text-align:right" sysLong="EVS_SCORE">${item.EVS_SCORE }</td>
				</tr>
			</c:forEach>
			</c:if>
			<tr id="rowIdObjectTarget_101">
				<td class="td_title"></td>
				<td class="td_title"></td>
				<td class="td_title"></td>
				<td class="td_title" style="text-align:right;" id="objectTargetSum">100</td>
				<td class="td_title" style="text-align:right;" id="objectTargetSumScore">${viewEvsAffirmInfo.EVS_POINT }</td>
				<c:if test="${viewEvsObjectInfo.ACTIVITY eq '14015356'}">
				<td class="td_title">
					<select id="EVS_GRADE" name = "EVS_GRADE" style="width:50px; visibility: hidden" disabled="disabled">
						<option value=""></option>
						<c:forEach items="${viewGradeList}" var="item" varStatus="i">
							<option value="${item.START_SCORE }" name="${item.EVS_GRADE_ORIGINAL}" <c:if test="${item.EVS_GRADE_NAME eq viewEvsAffirmInfo.EVS_GRADE}">selected="selected"</c:if>>${item.EVS_GRADE_NAME}</option>
						</c:forEach>
					</select>
				</td>
				</c:if>
			</tr>
		</table>
		<input type="hidden" id="objectTargetCnt" name="objectTargetCnt" value="${objectTargetCnt }"/>
		<input type="hidden" name="RESUME_SEQ" value="${RESUME_SEQ }"/>
		<input type="hidden" name="EVS_OBJECT_SEQ" value="${viewEvsObjectInfo.SEQ }"/>
		<input type="hidden" name="EVS_OBJECT_SEQ" value="${viewEvsObjectInfo.PERSON_ID }"/>
		<input type="hidden" id="SEQ" name="SEQ" value="${viewEvsAffirmInfo.SEQ }"/>
		<input type="hidden" id="EVS_POINT" name="EVS_POINT" value="${viewEvsAffirmInfo.EVS_POINT }"/>
	</div>
	<div style="padding-left:10px;padding-right:10px;padding-top:5px;padding-bottom:30px;">
		<div style="font:bold 14px/20px arial,sans-serif;float:left;height:20px;line-height:20px;"><spring:message code="evs.viewProbationEvsResult.BUFEN.a"/> 2</div>
		<table class="user_table" width="100%">
			<tr id="rowIdEvsBySelf_100">
				<td class="td_title" colspan="2" style="text-align:center;"><spring:message code="ess.infoApply.comment"/></td>
			</tr>
			<c:if test="${viewEvsObjectInfo.ACTIVITY eq '14015356'}">
			<tr>
				<td class="td_title" style="text-align:center;width:10%;"><spring:message code="evs.viewEvsBySelfHTSV.YEJI.a"/><!--业绩--></td>
				<td style="width:90%;"><textarea style="width:99.5%;height:100px" id="AFFIRM_CONTENT1" name="AFFIRM_CONTENT" class="editor required" tools="Cut,Copy,Paste,|,Fullscreen">${viewEvsAffirmInfo.AFFIRM_CONTENT }</textarea></td>
			</tr>
			<tr>
				<td class="td_title"  style="text-align:center;"><spring:message code="evs.viewEvsBySelfHTSV.BUZU.a"/><!--不足--></td>
				<td><textarea style="width:99.5%;height:100px" id="AFFIRM_CONTENT2" name="AFFIRM_CONTENT2" class="editor required" tools="Cut,Copy,Paste,|,Fullscreen">${viewEvsAffirmInfo.AFFIRM_CONTENT2 }</textarea></td>
			</tr>
			</c:if>
			<c:if test="${viewEvsObjectInfo.ACTIVITY ne '14015356'}">
			<tr id="rowIdEvsBySelf_100">
				<td class="td_type">${viewEvsAffirmInfo.AFFIRM_CONTENT }<br>${viewEvsAffirmInfo.AFFIRM_CONTENT2 }</td>
			</tr>
			</c:if>
		</table>
	</div>
		<c:if test="${viewEvsObjectInfo.ACTIVITY eq '14015361'}"> <!-- Khi nào kết thúc đánh giá mới cho xem kết quả -->
			<div style="float:right;height:20px;line-height:20px;margin-top:5px;">
				<a class="w_button" href="#" style="color:blue;" onclick="openEvsInfoWindow(${viewEvsObjectInfo.SEQ},${LoginUser.adminID })">
				<span><spring:message code="ar.viewEmpInfoListTanchu.YEJIKAOPINGJIEGUO.b"/></span></a>
			</div>
		</c:if>
	</form>
</div>
</c:if>
<c:if test="${empty resumeList}">
	<%@ include file="/WEB-INF/view/evs/manage/no_evs.jsp"%>
</c:if>
