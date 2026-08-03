<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
function viewConfirmTargetInfoAbilitySave(flag){
	var msg = "<spring:message code='evs.viewAffirmTarget1.LINGSHIBAOCUN.a'/>";//临时保存
	if(flag == 1){
		msg = "<spring:message code='evs.viewConfirmTargetInfoAbility.SHIXING.a'/>";//实行
	}else if(flag == 0){
		msg = "<spring:message code='hrm.approve.RETURN'/>";//退回
	}
	if(flag != 0){
		var form = $("#viewConfirmTargetInfoForm",$.pdialog.getCurrent());
		if (!form.valid()) {
			return false;
		}
		if($("#EVS_GRADE",$.pdialog.getCurrent()).find("option:selected").attr("name") == ''){
			alertMsg.warn("<spring:message code='evs.viewConfirmTargetInfoAbility.QINGXUANZEKAOHEDENGJI.a'/>");//请选择考核等级
			return false;
		}
	}
	//获取页面的值
	var jsonData = '[';
	$('select:[name="EVS_SCORE"]',$.pdialog.getCurrent()).each(function(i, obj){
		if (jsonData.length > 1) {
			jsonData += ',{';
		} else {
			jsonData += '{';
		}
		jsonData += ' "EVS_SCORE": "' + obj.value + '" ,';
		jsonData += ' "ITEM_SEQ": "' + $(obj).attr("sysIndex") + '" ,';
		jsonData += ' "SEQ": "${viewEvsObjectInfo.SEQ1 }" ,';
		jsonData += ' "RESUME_SEQ": "${viewEvsObjectInfo.RESUME_SEQ}" ,';
		jsonData += ' "adminID": "' + '${LoginUser.adminID}' + '" ,';
		jsonData += ' "adminIP": "' + '${LoginUser.adminIP}' + '" ,';
		jsonData += ' "interCpnyID": "' + '${LoginUser.cpnyId}' + '" ';
		jsonData += '}';
	});
	jsonData += ']';
	                         //确定要                                                  												吗？
	alertMsg.confirm("<spring:message code='ess.viewMonthDetailConfirmList.QUEDINGYAO.a'/>" + msg + "<spring:message code='ess.viewMonthDetailConfirmList.MAO.a'/>",
  		{okCall:function(){
		  	$.ajax({
  				type: 'POST',
  				url: '/evs/manage/addEvsDetailInfoHTSVAbility?EVS_TYPE=Ability',
  				data:[{ name: 'jsonData', value: jsonData },
  				      { name: 'AFFIRM_CONTENT', value: $("#AFFIRM_CONTENT",$.pdialog.getCurrent()).val() },
  				      { name: 'EVS_GRADE', value: $("#EVS_GRADE",$.pdialog.getCurrent()).find("option:selected").attr("name") },
  				      { name: 'EVS_POINT', value: $("#objectTargetSumAffirmScore",$.pdialog.getCurrent()).html() },
  				      { name: 'SEQ', value: $("#SEQ",$.pdialog.getCurrent()).val() },
  				      { name: 'EVS_OBJECT_SEQ', value: '${viewEvsObjectInfo.SEQ}' },
  				      { name: 'FLAG', value: flag }],
  				dataType:"json",
  				cache: false,
  				success: dialogAjaxDone,
  				error: DWZ.ajaxError
  		});
  	}});
	
}
	
function viewConfirmTargetInfoAbilitySave2(flag){
	var msg = "<spring:message code='evs.viewAffirmTarget1.LINGSHIBAOCUN.a'/>";//临时保存
	if(flag == 1){
		msg = "<spring:message code='evs.viewConfirmTargetInfoAbility.SHIXING.a'/>";//实行
	}else if(flag == 0){
		msg = "<spring:message code='hrm.approve.RETURN'/>";//退回
	}
	if(flag != 0){
		var form = $("#viewConfirmTargetInfoForm",$.pdialog.getCurrent());
		if (!form.valid()) {
			return false;
		}
		if($("#EVS_GRADE2",$.pdialog.getCurrent()).find("option:selected").attr("name") == ''){
			alertMsg.warn("<spring:message code='evs.viewConfirmTargetInfoAbility.QINGXUANZEKAOHEDENGJI.a'/>");//请选择考核等级
			return false;
		}
	}
	//获取页面的值
	var jsonData = '[';
	$('select:[name="EVS_SCORE2"]',$.pdialog.getCurrent()).each(function(i, obj){
		if (jsonData.length > 1) {
			jsonData += ',{';
		} else {
			jsonData += '{';
		}
		jsonData += ' "EVS_SCORE": "' + obj.value + '" ,';
		jsonData += ' "ITEM_SEQ": "' + $(obj).attr("sysIndex2") + '" ,';
		jsonData += ' "SEQ": "${viewEvsObjectInfo.SEQ2 }" ,';
		jsonData += ' "RESUME_SEQ": "${viewEvsObjectInfo.RESUME_SEQ}" ,';
		jsonData += ' "adminID": "' + '${LoginUser.adminID}' + '" ,';
		jsonData += ' "adminIP": "' + '${LoginUser.adminIP}' + '" ,';
		jsonData += ' "interCpnyID": "' + '${LoginUser.cpnyId}' + '" ';
		jsonData += '}';
	});
	jsonData += ']';
	                         //确定要                                                  												吗？
	alertMsg.confirm("<spring:message code='ess.viewMonthDetailConfirmList.QUEDINGYAO.a'/>" + msg + "<spring:message code='ess.viewMonthDetailConfirmList.MAO.a'/>",
  		{okCall:function(){
		  	$.ajax({
  				type: 'POST',
  				url: '/evs/manage/addEvsDetailInfoHTSVAbility?EVS_TYPE=Ability',
  				data:[{ name: 'jsonData', value: jsonData },
  				      { name: 'AFFIRM_CONTENT', value: $("#AFFIRM_CONTENT2",$.pdialog.getCurrent()).val() },
  				      { name: 'EVS_GRADE', value: $("#EVS_GRADE2",$.pdialog.getCurrent()).find("option:selected").attr("name") },
  				      { name: 'EVS_POINT', value: $("#objectTargetSumAffirmScore2",$.pdialog.getCurrent()).html() },
  				      { name: 'SEQ', value: $("#SEQ2",$.pdialog.getCurrent()).val() },
  				      { name: 'EVS_OBJECT_SEQ', value: '${viewEvsObjectInfo.SEQ}' },
  				      { name: 'FLAG', value: flag }],
  				dataType:"json",
  				cache: false,
  				success: dialogAjaxDone,
  				error: DWZ.ajaxError
  		});
  	}});
	
}

function viewConfirmTargetInfoAbilitySumScore(){
	var total = 0;
	$('select:[name="EVS_SCORE"]',$.pdialog.getCurrent()).each(function(i, obj){
		if($(obj).val() != ''){
			total += parseFloat($(obj).val()) * parseFloat($(obj).attr("sysItemScore")) / parseFloat($(obj).find("option:last").val());
		}
	});
	$("#objectTargetSumAffirmScore",$.pdialog.getCurrent()).html(total.toFixed(2));
	$("#objectTargetSumAffirmScore1",$.pdialog.getCurrent()).html(total.toFixed(2));

	$('#EVS_GRADE option',$.pdialog.getCurrent()).each(function(i, obj){
		if(i>0){
			if(total >= parseFloat($(obj).val())){
				$(obj).attr("selected","selected");
				return false;
			}
		}
	});
}

function viewConfirmTargetInfoAbilitySumScore2(){
	var total = 0;
	$('select:[name="EVS_SCORE2"]',$.pdialog.getCurrent()).each(function(i, obj){
		if($(obj).val() != ''){
			total += parseFloat($(obj).val()) * parseFloat($(obj).attr("sysItemScore2")) / parseFloat($(obj).find("option:last").val());
		}
	});
	$("#objectTargetSumAffirmScore2",$.pdialog.getCurrent()).html(total.toFixed(2));
	$("#objectTargetSumAffirmScore2",$.pdialog.getCurrent()).html(total.toFixed(2));

	$('#EVS_GRADE2 option',$.pdialog.getCurrent()).each(function(i, obj){
		if(i>0){
			if(total >= parseFloat($(obj).val())){
				$(obj).attr("selected","selected");
				return false;
			}
		}
	});
}
</script>
<div class="pageContent" layoutH="5">
	<form id="viewConfirmTargetInfoForm" action="/evs/manage/viewEvsBySelfHTSV" method="post" >
	<div style="padding-left:10px;padding-right:10px;padding-top:5px;" sysLong='printDiv'>
	<%@ include file="/WEB-INF/view/evs/manage/viewPersonalInfoHead_evs.jsp"%>
		<div style="font:bold 14px/20px arial,sans-serif;float:left;height:20px;line-height:20px;">SECTION 1</div>
		<table class="user_table" width="100%" id="viewRegPersonalTarget_table">	
			<tr>
				<td class="td_title"  style="text-align:center;" width="5%">No</td>
				<td class="td_title"  style="text-align:center;" width="15%"><spring:message code="display.emp.ben.or.benhs67"/><!--区分--></td>
				<td class="td_title"  style="text-align:center;" width="15%"><spring:message code="evs.viewConfirmTargetInfoAbility.KAOHEXIANGMU.a"/><!--考核项目--></td>
				<td class="td_title"  style="text-align:center;" width="40%"><spring:message code="evs.viewConfirmTargetInfoAbility.KAOHEZHIBIAO.a"/><!--考核指标--></td>
				<td class="td_title" style="text-align:center;" width="7%"><spring:message code="evs.viewConfirmTargetInfoAbility.CEZHONGZHI.a"/><!--测重值--></td>
				<td class="td_title" style="text-align:center;" width="7%"><spring:message code="evs.viewEvsIndex.BENRENPINGJIA.a"/><!--本人评价--></td>
				<td class="td_title" style="text-align:center;" width="11%"><spring:message code="evs.viewAffirmTarget1Ability.YICIPINGJIA.a"/><!--1次评价--></td>
				<c:if test="${ACTIVITY eq '14015358'}">
				<td class="td_title" style="text-align:center;" width="11%"><spring:message code="evs.viewAffirmTarget1Ability.ERCIPINGJIA.a"/><!--2次评价--></td>
				</c:if>
			</tr>
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
			    	<td class="td_type" style="text-align:right">${item.EVS_SCORE0 }</td>
					<c:if test="${ACTIVITY eq '14015357'}">
			    	<td class="td_type" style="text-align:right">
			    		<select name="EVS_SCORE" style="width:100px" sysIndex="${item.SEQ }" sysItemScore="${item.ITEM_SCORE }" onchange="viewConfirmTargetInfoAbilitySumScore()">
							<option value=""></option>
							<c:forEach items="${viewEvsGradeItem}" var="itemGrade" varStatus="i">
								<option value="${itemGrade.EVS_SCORE }" <c:if test="${itemGrade.EVS_SCORE eq item.EVS_SCORE1}">selected="selected"</c:if>>${itemGrade.CODE_NAME}</option>
							</c:forEach>
						</select>
			    	</td>
			    	</c:if>
					<c:if test="${ACTIVITY ne '14015357'}">
			    	<td class="td_type" style="text-align:right">${item.EVS_SCORE1 }</td>
			    	</c:if>
			    	<c:if test="${ACTIVITY eq '14015358'}">
					<td class="td_type" style="text-align:right">
			    		<select name="EVS_SCORE2" style="width:100px" sysIndex2="${item.SEQ }" sysItemScore2="${item.ITEM_SCORE }" onchange="viewConfirmTargetInfoAbilitySumScore2()">
							<option value=""></option>
							<c:forEach items="${viewEvsGradeItem}" var="itemGrade" varStatus="i">
								<option value="${itemGrade.EVS_SCORE }" <c:if test="${itemGrade.EVS_SCORE eq item.EVS_SCORE2}">selected="selected"</c:if>>${itemGrade.CODE_NAME}</option>
							</c:forEach>
						</select>
			    	</td>
					</c:if>
				</tr>
			</c:forEach>
			<tr>
				<td class="td_title"></td>
				<td class="td_title"></td>
				<td class="td_title"></td>
				<td class="td_title"></td>
				<td class="td_title" style="text-align:right;">100</td>
				<td class="td_title" style="text-align:right;">${viewEvsObjectInfo.EVS_POINT0 }</td>
				<td class="td_title" style="text-align:right;" id="objectTargetSumAffirmScore">${viewEvsObjectInfo.EVS_POINT1 }</td>
				<c:if test="${ACTIVITY eq '14015358'}">
				<td class="td_title" style="text-align:right;" id="objectTargetSumAffirmScore2">${viewEvsObjectInfo.EVS_POINT2 }</td>
				</c:if>
			</tr>
		</table>
		
		<br/>
		<table class="user_table" width="100%">
			<tr>
				<td class="td_title"  style="text-align:center;" width="80%"><spring:message code="evs.viewConfirmTargetInfoSST.BENRENYIJIAN.a"/><!--本人意见--></td>
				<td class="td_title"  style="text-align:center;" width="10%"><spring:message code="hr.viewCompetence.title.MARK"/><!--分数--></td>
				<td class="td_title"  style="text-align:center;" width="10%"><spring:message code="evs.viewProbationEvsResult.DENGJI.a"/><!--等级--></td>
			</tr>
			<tr>
				<td class="td_type">
					${viewEvsObjectInfo.AFFIRM_CONTENT0 }
				</td>
				<td class="td_type" style="text-align:center;">
					${viewEvsObjectInfo.EVS_POINT0 }
				</td>
				<td class="td_type"  style="text-align:center;" name="${viewEvsObjectInfo.EVS_GRADE0 }">
					 ${viewEvsObjectInfo.EVS_GRADE0} 
					<%--<c:choose>
						<c:when test="${viewEvsObjectInfo.EVS_GRADE0 == 'A'}">EX</c:when>
						<c:when test="${viewEvsObjectInfo.EVS_GRADE0 == 'B'}">VG</c:when>
						<c:when test="${viewEvsObjectInfo.EVS_GRADE0 == 'C'}">GD</c:when>
						<c:when test="${viewEvsObjectInfo.EVS_GRADE0 == 'D'}">NI</c:when>
						<c:when test="${viewEvsObjectInfo.EVS_GRADE0 == 'E'}">UX</c:when>
					</c:choose>--%>
					
				</td>
			</tr>
		</table>
		
		<c:if test="${ACTIVITY eq '14015357'}">
		<br/>
		<table class="user_table" width="100%">
			<tr>
				<td class="td_title"  style="text-align:center;" width="80%"><spring:message code="evs.viewConfirmTargetInfoSST.YICIKAOHEYIJIAN.a"/><!--1次考核意见--></td>
				<td class="td_title"  style="text-align:center;" width="10%"><spring:message code="hr.viewCompetence.title.MARK"/><!--分数--></td>
				<td class="td_title"  style="text-align:center;" width="10%"><spring:message code="evs.viewProbationEvsResult.DENGJI.a"/><!--等级--></td>
			</tr>
			<tr>
				<td class="td_type" style="text-align:center;">
					<textarea style="width:100%;height:100px" id="AFFIRM_CONTENT" class="editor required" tools="Cut,Copy,Paste,|,Fullscreen">${viewEvsObjectInfo.AFFIRM_CONTENT1 }</textarea>
					<input type="hidden" id="SEQ" value="${viewEvsObjectInfo.SEQ1 }">
				</td>
				<td class="td_type" style="text-align:center;" id="objectTargetSumAffirmScore1">${viewEvsObjectInfo.EVS_POINT1 }</td>
				<td class="td_type" style="text-align:center;">
					<select id="EVS_GRADE" style="width:80px" disabled>
						<option value=""></option>
						<c:forEach items="${viewGradeList}" var="item" varStatus="i">
							<option value="${item.START_SCORE }" name="${item.EVS_GRADE_ORIGINAL}" <c:if test="${item.EVS_GRADE_NAME eq viewEvsObjectInfo.EVS_GRADE1}">selected="selected"</c:if>>
							 ${item.EVS_GRADE_NAME}
								<%--<c:choose>
									<c:when test="${item.EVS_GRADE_NAME == 'A'}">EX</c:when>
									<c:when test="${item.EVS_GRADE_NAME == 'B'}">VG</c:when>
									<c:when test="${item.EVS_GRADE_NAME == 'C'}">GD</c:when>
									<c:when test="${item.EVS_GRADE_NAME == 'D'}">NI</c:when>
									<c:when test="${item.EVS_GRADE_NAME == 'E'}">UX</c:when>
								</c:choose> --%>
							</option>
						</c:forEach>
					</select>
				</td>
			</tr>
		</table>
		</c:if>
		
		<c:if test="${ACTIVITY ne '14015357'}">
		<br/>
		<table class="user_table" width="100%">
			<tr>
				<td class="td_title"  style="text-align:center;" width="80%"><spring:message code="evs.viewConfirmTargetInfoSST.YICIKAOHEYIJIAN.a"/><!--1次考核意见--></td>
				<td class="td_title"  style="text-align:center;" width="10%"><spring:message code="hr.viewCompetence.title.MARK"/><!--分数--></td>
				<td class="td_title"  style="text-align:center;" width="10%"><spring:message code="evs.viewProbationEvsResult.DENGJI.a"/><!--等级--></td>
			</tr>
			<tr>
				<td class="td_type">${viewEvsObjectInfo.AFFIRM_CONTENT1 }</td>
				<td class="td_type" style="text-align:center;">${viewEvsObjectInfo.EVS_POINT1 }</td>
				<td class="td_type"  style="text-align:center;" name="${viewEvsObjectInfo.EVS_GRADE1}">
				${viewEvsObjectInfo.EVS_GRADE1} 
					<%-- <c:choose>
						<c:when test="${viewEvsObjectInfo.EVS_GRADE1 == 'A'}">EX</c:when>
						<c:when test="${viewEvsObjectInfo.EVS_GRADE1 == 'B'}">VG</c:when>
						<c:when test="${viewEvsObjectInfo.EVS_GRADE1 == 'C'}">GD</c:when>
						<c:when test="${viewEvsObjectInfo.EVS_GRADE1 == 'D'}">NI</c:when>
						<c:when test="${viewEvsObjectInfo.EVS_GRADE1 == 'E'}">UX</c:when>
					</c:choose>--%>
				</td>
			</tr>
		</table>
		</c:if>
		<c:if test="${ACTIVITY eq '14015358'}">
		<br/>
		<table class="user_table" width="100%">
			<tr>
				<td class="td_title"  style="text-align:center;" width="80%"><spring:message code="evs.viewConfirmTargetInfoSST.ERCIKAOHEYIJIAN.a"/><!--2次考核意见--></td>
				<td class="td_title"  style="text-align:center;" width="10%"><spring:message code="hr.viewCompetence.title.MARK"/><!--分数--></td>
				<td class="td_title"  style="text-align:center;" width="10%"><spring:message code="evs.viewProbationEvsResult.DENGJI.a"/><!--等级--></td>
			</tr>
			<tr>
				<td class="td_type" style="text-align:center;">
					<textarea style="width:100%;height:100px" id="AFFIRM_CONTENT2" class="editor required" tools="Cut,Copy,Paste,|,Fullscreen">${viewEvsObjectInfo.AFFIRM_CONTENT2 }</textarea>
					<input type="hidden" id="SEQ2" value="${viewEvsObjectInfo.SEQ2 }">
				</td>
				<td class="td_type" style="text-align:center;" id="objectTargetSumAffirmScore2">${viewEvsObjectInfo.EVS_POINT2 }</td>
				<td class="td_type" style="text-align:center;">
					<select id="EVS_GRADE2" style="width:80px" disabled>
						<option value=""></option>
						<c:forEach items="${viewGradeList}" var="item" varStatus="i">
							<option value="${item.START_SCORE }" name="${item.EVS_GRADE_ORIGINAL}" <c:if test="${item.EVS_GRADE_NAME eq viewEvsObjectInfo.EVS_GRADE2}">selected="selected"</c:if>>
							 ${item.EVS_GRADE_NAME} 
								<%--<c:choose>
									<c:when test="${item.EVS_GRADE_NAME == 'A'}">EX</c:when>
									<c:when test="${item.EVS_GRADE_NAME == 'B'}">VG</c:when>
									<c:when test="${item.EVS_GRADE_NAME == 'C'}">GD</c:when>
									<c:when test="${item.EVS_GRADE_NAME == 'D'}">NI</c:when>
									<c:when test="${item.EVS_GRADE_NAME == 'E'}">UX</c:when>
								</c:choose>--%>
							</option>
						</c:forEach>
					</select>
				</td>
			</tr>
		</table>
		</c:if>
	</div>
	</form>
	<div class="formBar">
			<ul>
				<c:if test="${ACTIVITY eq '14015357' and viewEvsObjectInfo.ACTIVITY eq '14015357'}">
				<li>
					<div class="button">
						<div class="buttonContent"><!-- 通过 -->
							<button type="button" onclick="viewConfirmTargetInfoAbilitySave(2)">
								<spring:message code="evs.viewAffirmTarget1.LINGSHIBAOCUN.a"/><!--临时保存-->
							</button>
						</div>
					</div>
				</li>
				<li>
					<div class="button">
						<div class="buttonContent"><!-- 通过 -->
							<button type="button" onclick="viewConfirmTargetInfoAbilitySave(1)">
								<spring:message code="evs.viewConfirmTargetInfoSST.CHENGREN.a"/><!--承认-->
							</button>
						</div>
					</div>
				</li>
				<li>
					<div class="button">
						<div class="buttonContent"><!-- 否决 -->
							<button type="button" onclick="viewConfirmTargetInfoAbilitySave(0)">
								<spring:message code="hrm.approve.RETURN"/><!--退回-->
							</button>
						</div>
					</div>
				</li>
				</c:if>
				<c:if test="${ACTIVITY eq '14015358' and viewEvsObjectInfo.ACTIVITY eq '14015358'}">
				<li>
					<div class="button">
						<div class="buttonContent"><!-- 通过 -->
							<button type="button" onclick="viewConfirmTargetInfoAbilitySave2(2)">
								<spring:message code="evs.viewAffirmTarget1.LINGSHIBAOCUN.a"/><!--临时保存-->
							</button>
						</div>
					</div>
				</li>
				<li>
					<div class="button">
						<div class="buttonContent"><!-- 通过 -->
							<button type="button" onclick="viewConfirmTargetInfoAbilitySave2(1)">
								<spring:message code="evs.viewConfirmTargetInfoSST.CHENGREN.a"/><!--承认-->
							</button>
						</div>
					</div>
				</li>
				<li>
					<div class="button">
						<div class="buttonContent"><!-- 否决 -->
							<button type="button" onclick="viewConfirmTargetInfoAbilitySave2(0)">
								<spring:message code="hrm.approve.RETURN"/><!--退回-->
							</button>
						</div>
					</div>
				</li>
				</c:if>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" class="close">
								<spring:message code="ess.infoApply.close"/><!--关闭-->
							</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
</div>
