<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
function viewConfirmTargetInfoHTSVAbilitySave(flag){
	var msg = "<spring:message code='evs.viewAffirmTarget1.LINGSHIBAOCUN.a'/>";//临时保存
	if(flag == 1){
		msg = "<spring:message code='evs.viewConfirmTargetInfoAbility.SHIXING.a'/>";//实行
	}else if(flag == 0){
		msg = "<spring:message code='hrm.approve.RETURN'/>";//退回
	}
	if(flag != 0){
		var form = $("#viewConfirmTargetInfoHTSVForm",$.pdialog.getCurrent());
		if (!form.valid()) {
			return false;
		}
		if($("#EVS_GRADE",$.pdialog.getCurrent()).find("option:selected").text() == ''){
			alertMsg.warn("<spring:message code='evs.viewConfirmTargetInfoAbility.QINGXUANZEKAOHEDENGJI.a'/>");//请选择考核等级
			return false;
		}
	}
	//获取页面的值
	var jsonData = '[';
	$('input:[name="AFFIRM_SCORE"]',$.pdialog.getCurrent()).each(function(i, obj){
		if (jsonData.length > 1) {
			jsonData += ',{';
		} else {
			jsonData += '{';
		}
		jsonData += ' "EVS_SCORE": "' + obj.value + '" ,';
		jsonData += ' "ITEM_SEQ": "' + $(obj).parent().find("input:[name='ITEM_SEQ']").val() + '" ,';
		jsonData += ' "SEQ": "${viewEvsObjectInfo.SEQ1 }" ,';
		jsonData += ' "RESUME_SEQ": "${viewEvsObjectInfo.RESUME_SEQ}" ,';
		jsonData += ' "adminID": "' + '${LoginUser.adminID}' + '" ,';
		jsonData += ' "adminIP": "' + '${LoginUser.adminIP}' + '" ,';
		jsonData += ' "interCpnyID": "' + '${LoginUser.cpnyId}' + '" ';
		jsonData += '}';
	});
	jsonData += ']';
								//确定要															吗？
	alertMsg.confirm("<spring:message code='ess.viewMonthDetailConfirmList.QUEDINGYAO.a'/>" + msg + "<spring:message code='ess.viewMonthDetailConfirmList.MAO.a'/>",
  		{okCall:function(){
		  	$.ajax({
  				type: 'POST',
  				url: '/evs/manage/addEvsDetailInfoHTSVAbility',
  				data:[{ name: 'jsonData', value: jsonData },
  				      { name: 'AFFIRM_CONTENT', value: $("#AFFIRM_CONTENT",$.pdialog.getCurrent()).val() },
  				      { name: 'EVS_GRADE', value: $("#EVS_GRADE",$.pdialog.getCurrent()).find("option:selected").text() },
  				      { name: 'EVS_POINT', value: $("#objectTargetSumAffirmScore",$.pdialog.getCurrent()).html() },
  				      { name: 'SEQ', value: $("#SEQ",$.pdialog.getCurrent()).val() },
  				      { name: 'EVS_OBJECT_SEQ', value: '${viewEvsObjectInfo.SEQ}' },
  				      { name: 'FLAG', value: flag }],
  				dataType:"json",
  				cache: false,
  				success: function(json){
		  			DWZ.ajaxDone(json);
		  			$("#viewAffirmTargetForm",navTab.getCurrentPanel()).submit();
		  			$.pdialog.closeCurrent();
				},
  				error: DWZ.ajaxError
  		});
  	}});
}

function viewConfirmTargetInfoHTSVAbilitySumScore(object){
	var itemScore = parseInt($(object).parent().parent().find('td[sysLong="ITEM_SCORE"]').html());
	if(parseInt(object.value) > itemScore){
		alertMsg.error("<spring:message code='evs.viewConfirmTargetInfoHTSVAbility.KAOPINGFENSHUBUNENGDAYUXIANGMU.a'/>");//考评分数不能大于项目权重分数
		object.value = 0;
	}
	var total = 0;
	$('input:[name="AFFIRM_SCORE"]',$.pdialog.getCurrent()).each(function(i, obj){
		if($(obj).val() != ''){
			total += parseInt($(obj).val());
		}
	});
	$("#objectTargetSumAffirmScore",$.pdialog.getCurrent()).html(total);
}
</script>
<div class="pageContent" layoutH="5">
	<form id="viewConfirmTargetInfoHTSVForm" action="/evs/manage/viewEvsBySelfHTSV" method="post" >
	<div style="padding-left:10px;padding-right:10px;padding-top:5px;" sysLong='printDiv'>
	<%@ include file="/WEB-INF/view/evs/manage/viewPersonalInfoHead_evs.jsp"%>
		<div style="font:bold 14px/20px arial,sans-serif;float:left;height:20px;line-height:20px;"><spring:message code="evs.viewProbationEvsResult.BUFEN.a"/><!-- SECTION --> 1</div>
		<table class="user_table" width="100%" id="viewRegPersonalTarget_table">	
			<tr>
				<td class="td_title"  style="text-align:center;" width="10%">No</td>
				<td class="td_title"  style="text-align:center;" width="15%"><spring:message code="evs.viewEvsItemPanel.LIDUQUN.a"/><!--力度群--></td>
				<td class="td_title" style="text-align:center;" width="45%"><spring:message code="evs.viewConfirmTargetInfoAbility.KAOHEXIANGMU.a"/><!--考核项目--></td>
				<td class="td_title" style="text-align:center;" width="10%"><spring:message code="evs.viewConfirmTargetInfoHTSVAbility.QUANZHONG.a"/><!--权重--></td>
				<td class="td_title" style="text-align:center;" width="10%"><spring:message code="evs.viewConfirmTargetInfoHTSV.BENRENPINGJIAFENSHU.a"/><!--本人评价（分数）--></td>
				<td class="td_title" style="text-align:center;" width="10%"><spring:message code="evs.viewConfirmTargetInfoHTSV.SHANGSIPINGJIAFENSHU.a"/><!--上司评价（分数）--></td>
			</tr>
			<c:forEach items="${viewEvsItem}" var="item" varStatus="i">
				<tr>
					<td class="td_type" style="text-align:center">${i.count}</td>
			    	<td class="td_type" style="text-align:left;">${item.GROUP_NAME }</td>
			    	<td class="td_type" style="text-align:left;">${item.ITEM_NAME }</td>
			    	<td class="td_type" style="text-align:right" sysLong="ITEM_SCORE">${item.ITEM_SCORE }</td>
			    	<td class="td_type" style="text-align:right">${item.EVS_SCORE0 }</td>
					<c:if test="${viewEvsObjectInfo.ACTIVITY eq '14015357'}">
			    	<td class="td_type" style="text-align:right">
			    		<input name="AFFIRM_SCORE" value="${item.EVS_SCORE1 }" type="text" size="25" onblur="viewConfirmTargetInfoHTSVAbilitySumScore(this)" class="required number" min="0" max="100">
			    		<input name="ITEM_SEQ" type="hidden" value="${item.SEQ }">
			    	</td>
			    	</c:if>
					<c:if test="${viewEvsObjectInfo.ACTIVITY ne '14015357'}">
			    	<td class="td_type" style="text-align:right">${item.EVS_SCORE1 }</td>
			    	</c:if>
				</tr>
			</c:forEach>
			<tr>
				<td class="td_title"></td>
				<td class="td_title"></td>
				<td class="td_title"></td>
				<td class="td_title" style="text-align:right;">100</td>
				<td class="td_title" style="text-align:right;">${viewEvsObjectInfo.EVS_POINT0 }</td>
				<td class="td_title" style="text-align:right;" id="objectTargetSumAffirmScore">${viewEvsObjectInfo.EVS_POINT1 }</td>
			</tr>
		</table>
		
		<c:if test="${viewEvsObjectInfo.ACTIVITY eq '14015357'}">
		<br/>
		<table class="user_table" width="100%">
			<tr>
				<td class="td_title"  style="text-align:center;" width="10%"><spring:message code="evs.viewConfirmTargetInfoPrint.YICIKAOHEDENGJI.a"/><!--1 次 考核等级--></td>
				<td class="td_title"  style="text-align:center;" width="90%"><spring:message code="evs.viewConfirmTargetInfoPrint.YICIPINGYU.a"/><!--1 次 评语--></td>
			</tr>
			<tr>
				<td class="td_type"  style="text-align:center;">
					<select id="EVS_GRADE" style="width:150px">
						<option value=""></option>
						<c:forEach items="${viewGradeList}" var="item" varStatus="i">
							<option value="${item.START_SCORE }" <c:if test="${item.EVS_GRADE_NAME eq viewEvsObjectInfo.EVS_GRADE1}">selected="selected"</c:if>>${item.EVS_GRADE_NAME}</option>
						</c:forEach>
					</select>
				</td>
				<td class="td_type" style="text-align:center;">
					<textarea style="width:99.5%;height:70px" id="AFFIRM_CONTENT" class="required">${viewEvsObjectInfo.AFFIRM_CONTENT1 }</textarea>
					<input type="hidden" id="SEQ" value="${viewEvsObjectInfo.SEQ1 }">
				</td>
			</tr>
		</table>
		</c:if>
		
		<c:if test="${viewEvsObjectInfo.ACTIVITY ne '14015357'}">
		<br/>
		<table class="user_table" width="100%">
			<tr>
				<td class="td_title"  style="text-align:center;" width="10%"><spring:message code="evs.viewConfirmTargetInfoPrint.YICIKAOHEDENGJI.a"/><!--1 次 考核等级--></td>
				<td class="td_title"  style="text-align:center;" width="90%"><spring:message code="evs.viewConfirmTargetInfoPrint.YICIPINGYU.a"/><!--1 次 评语--></td>
			</tr>
			<tr>
				<td class="td_type"  style="text-align:center;">${viewEvsObjectInfo.EVS_GRADE1}</td>
				<td class="td_type">${viewEvsObjectInfo.AFFIRM_CONTENT1 }</td>
			</tr>
		</table>
		</c:if>


		
		<c:if test="${viewEvsObjectInfo.ACTIVITY eq '14015358' and ACTIVITY eq '14015358'}">
		<br/>
		<table class="user_table" width="100%">
			<tr>
				<td class="td_title"  style="text-align:center;" width="10%"><spring:message code="evs.viewConfirmTargetInfoPrint.ERCIKAOHEDENGJI.a"/><!--2 次 考核等级--></td>
				<td class="td_title"  style="text-align:center;" width="90%"><spring:message code="evs.viewConfirmTargetInfoPrint.ERCIPINGYU.a"/><!--2 次 评语--></td>
			</tr>
			<tr>
				<td class="td_type"  style="text-align:center;">
					<select id="EVS_GRADE" style="width:150px">
						<option value=""></option>
						<c:forEach items="${viewGradeList}" var="item" varStatus="i">
							<option value="${item.START_SCORE }" <c:if test="${item.EVS_GRADE_NAME eq viewEvsObjectInfo.EVS_GRADE2}">selected="selected"</c:if>>${item.EVS_GRADE_NAME}</option>
						</c:forEach>
					</select>
				</td>
				<td class="td_type" style="text-align:center;">
					<textarea style="width:99.5%;height:100px" id="AFFIRM_CONTENT" class="editor required" tools="Cut,Copy,Paste,|,Fullscreen">${viewEvsObjectInfo.AFFIRM_CONTENT2 }</textarea>
					<input type="hidden" id="SEQ" value="${viewEvsObjectInfo.SEQ2 }">
				</td>
			</tr>
		</table>
		</c:if>
		
		<c:if test="${viewEvsObjectInfo.ACTIVITY ne '14015358' and not empty viewEvsObjectInfo.EVS_GRADE2}">
		<c:if test="${ACTIVITY eq '14015358' or ACTIVITY eq '14015359'}">
		<br/>
		<table class="user_table" width="100%">
			<tr>
				<td class="td_title"  style="text-align:center;" width="10%"><spring:message code="evs.viewConfirmTargetInfoPrint.ERCIKAOHEDENGJI.a"/><!--2 次 考核等级--></td>
				<td class="td_title"  style="text-align:center;" width="90%"><spring:message code="evs.viewConfirmTargetInfoPrint.ERCIPINGYU.a"/><!--2 次 评语--></td>
			</tr>
			<tr>
				<td class="td_type"  style="text-align:center;">${viewEvsObjectInfo.EVS_GRADE2}</td>
				<td class="td_type">${viewEvsObjectInfo.AFFIRM_CONTENT2 }</td>
			</tr>
		</table>
		</c:if>
		</c:if>
		
		<c:if test="${viewEvsObjectInfo.ACTIVITY eq '14015359' and ACTIVITY eq '14015359'}">
		<br/>
		<table class="user_table" width="100%">
			<tr>
				<td class="td_title" style="text-align:center;" width="10%"><spring:message code="evs.viewConfirmTargetInfoPrint.SANCIKAOHEDENGJI.a"/><!--3 次 考核等级--></td>
				<td class="td_title" style="text-align:center;" width="90%"><spring:message code="evs.viewConfirmTargetInfoPrint.SANCIPINGYU.a"/><!--3 次 评语--></td>
			</tr>
			<tr>
				<td class="td_type"  style="text-align:center;">
					<select id="EVS_GRADE" style="width:150px">
						<option value=""></option>
						<c:forEach items="${viewGradeList}" var="item" varStatus="i">
							<option value="${item.START_SCORE }" <c:if test="${item.EVS_GRADE_NAME eq viewEvsObjectInfo.EVS_GRADE3}">selected="selected"</c:if>>${item.EVS_GRADE_NAME}</option>
						</c:forEach>
					</select>
				</td>
				<td class="td_type" style="text-align:center;">
					<textarea style="width:99.5%;height:100px" id="AFFIRM_CONTENT" class="editor required" tools="Cut,Copy,Paste,|,Fullscreen">${viewEvsObjectInfo.AFFIRM_CONTENT3 }</textarea>
					<input type="hidden" id="SEQ" value="${viewEvsObjectInfo.SEQ3 }">
				</td>
			</tr>
		</table>
		</c:if>
		
		<c:if test="${not empty viewEvsObjectInfo.EVS_GRADE3}">
		<c:if test="${viewEvsObjectInfo.ACTIVITY ne '14015359' and ACTIVITY eq '14015359'}">
		<br/>
		<table class="user_table" width="100%">
			<tr>
				<td class="td_title"  style="text-align:center;" width="10%"><spring:message code="evs.viewConfirmTargetInfoPrint.SANCIKAOHEDENGJI.a"/><!--3 次 考核等级--></td>
				<td class="td_title"  style="text-align:center;" width="90%"><spring:message code="evs.viewConfirmTargetInfoPrint.SANCIPINGYU.a"/><!--3 次 评语--></td>
			</tr>
			<tr>
				<td class="td_type"  style="text-align:center;">${viewEvsObjectInfo.EVS_GRADE3}</td>
				<td class="td_type">${viewEvsObjectInfo.AFFIRM_CONTENT3 }</td>
			</tr>
		</table>
		</c:if>
		</c:if>
	</div>
	</form>
	<div class="formBar">
			<ul>
				<c:if test="${viewEvsObjectInfo.ACTIVITY eq ACTIVITY}">
				<li>
					<div class="button">
						<div class="buttonContent"><!-- 通过 -->
							<button type="button" onclick="viewConfirmTargetInfoHTSVAbilitySave(2)">
								<spring:message code="evs.viewAffirmTarget1.LINGSHIBAOCUN.a"/><!--临时保存-->
							</button>
						</div>
					</div>
				</li>
				<li>
					<div class="button">
						<div class="buttonContent"><!-- 通过 -->
							<button type="button" onclick="viewConfirmTargetInfoHTSVAbilitySave(1)">
								<spring:message code="evs.viewConfirmTargetInfoSST.CHENGREN.a"/><!--承认-->
							</button>
						</div>
					</div>
				</li>
				</c:if>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" class="close">
								<spring:message code="ess.title.close"/><!--关闭-->
							</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
</div>
