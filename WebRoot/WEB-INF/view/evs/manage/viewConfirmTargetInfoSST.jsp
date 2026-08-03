<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
$(document).ready(function(){
	var total = 0;
	$('#viewRegPersonalTarget_table tr td:[sysLog="ITEM_SCORE"]',$.pdialog.getCurrent()).each(function(i, obj){
		total += parseFloat($(obj).html());
	});
	$("#objectTargetSum",$.pdialog.getCurrent()).html(total);
});

function modifyObjectActivity(flag){
	var msg = "<spring:message code='hrm.approve.RETURN'/>";//退回
	if(flag == 1){
		msg = "<spring:message code='evs.viewConfirmTargetInfoSST.CHENGREN.a'/>";//承认
	}                  //确定要                             																				吗？
	alertMsg.confirm("<spring:message code='ess.viewMonthDetailConfirmList.QUEDINGYAO.a'/>" + msg + "<spring:message code='ess.viewMonthDetailConfirmList.MAO.a'/>",
  		{okCall:function(){
		  	$.ajax({
  				type: 'POST',
  				url: '/evs/manage/modifyObjectActivity',
  				data:[{name:'FLAG',value:flag},{name:'EVS_OBJECT_SEQ',value:'${viewEvsObjectInfo.SEQ}'}],
  				dataType:"json",
  				cache: false,
  				success: function(json){
		  			DWZ.ajaxDone(json);
		  			$.pdialog.closeCurrent();
		  		},
  				error: DWZ.ajaxError
  		});
  	}});
}

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
		if($("#EVS_GRADE1",$.pdialog.getCurrent()).find("option:selected").attr("name") == ''){
			alertMsg.warn("<spring:message code='evs.viewConfirmTargetInfoAbility.QINGXUANZEKAOHEDENGJI.a'/>");//请选择考核等级
			return false;
		}
	}
	//获取页面的值
	var jsonData = '[';
	$('input:[name="EVS_SCORE1"]',$.pdialog.getCurrent()).each(function(i, obj){
		if (jsonData.length > 1) {
			jsonData += ',{';
		} else {
			jsonData += '{';
		}
		jsonData += ' "EVS_SCORE1": "' + obj.value + '" ,';
		jsonData += ' "SEQ": "' + $(obj).attr("sysIndex") + '" ,';
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
  				url: '/evs/manage/addEvsDetailInfoHTSVAbility?EVS_TYPE=Performance',
  				data:[{ name: 'jsonData', value: jsonData },
  				      { name: 'AFFIRM_CONTENT', value: $("#AFFIRM_CONTENT1",$.pdialog.getCurrent()).val() },
  				      { name: 'EVS_GRADE', value: $("#EVS_GRADE1",$.pdialog.getCurrent()).find("option:selected").attr("name") },
  				      { name: 'EVS_POINT', value: $("#objectTargetSumScore",$.pdialog.getCurrent()).html() },
  				      { name: 'SEQ', value: $("#SEQ1",$.pdialog.getCurrent()).val() },
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
	$('input:[name="EVS_SCORE2"]',$.pdialog.getCurrent()).each(function(i, obj){
		if (jsonData.length > 1) {
			jsonData += ',{';
		} else {
			jsonData += '{';
		}
		jsonData += ' "EVS_SCORE2": "' + obj.value + '" ,';
		jsonData += ' "SEQ": "' + $(obj).attr("sysIndex2") + '" ,';
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
  				url: '/evs/manage/addEvsDetailInfoHTSVAbility?EVS_TYPE=Performance',
  				data:[{ name: 'jsonData', value: jsonData },
  				      { name: 'AFFIRM_CONTENT', value: $("#AFFIRM_CONTENT2",$.pdialog.getCurrent()).val() },
  				      { name: 'EVS_GRADE', value: $("#EVS_GRADE2",$.pdialog.getCurrent()).find("option:selected").attr("name") },
  				      { name: 'EVS_POINT', value: $("#objectTargetSumScore2",$.pdialog.getCurrent()).html() },
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

function sumObjectTargetScore(value){
	var total = 0;
	var evsTotal = 0;
	$('#viewRegPersonalTarget_table tr td:[sysLog="ITEM_SCORE"]',$.pdialog.getCurrent()).each(function(i, obj){
		if($(obj).html() != ''){
			total += parseFloat($(obj).html());
			evsTotal += parseFloat($(obj).html()) * parseInt($(obj).parent().find('input:[name="EVS_SCORE1"]').val());
			
		}
	});
	
	$("#objectTargetSum",$.pdialog.getCurrent()).html(total);
	if(total == 0){
		$("#objectTargetSumScore",$.pdialog.getCurrent()).html(0);
		$("#EVS_POINT1",$.pdialog.getCurrent()).val(0);
		
		$('#EVS_GRADE1 option',$.pdialog.getCurrent()).each(function(i, obj){
			if(i>0){
				if(total >= parseInt($(obj).val())){
					$(obj).attr("selected","selected");
					return false;
				}
			}
		});
		
	}else{
		$("#objectTargetSumScore",$.pdialog.getCurrent()).html((evsTotal / total).toFixed(1));
		$("#EVS_POINT1",$.pdialog.getCurrent()).val((evsTotal / total).toFixed(1));
		
		$('#EVS_GRADE1 option',$.pdialog.getCurrent()).each(function(i, obj){
			if(i>0){
				if((evsTotal / total) > parseInt($(obj).val())){
					$(obj).attr("selected","selected");
					return false;
				}
			}
		});
	}
}

function sumObjectTargetScore2(value){
	var total = 0;
	var evsTotal = 0;
	$('#viewRegPersonalTarget_table tr td:[sysLog="ITEM_SCORE"]',$.pdialog.getCurrent()).each(function(i, obj){
		if($(obj).html() != ''){
			total += parseFloat($(obj).html());
			evsTotal += parseFloat($(obj).html()) * parseInt($(obj).parent().find('input:[name="EVS_SCORE2"]').val());
			
		}
	});
	
	$("#objectTargetSum",$.pdialog.getCurrent()).html(total);
	if(total == 0){
		$("#objectTargetSumScore2",$.pdialog.getCurrent()).html(0);
		$("#EVS_POINT2",$.pdialog.getCurrent()).val(0);
		
		$('#EVS_GRADE2 option',$.pdialog.getCurrent()).each(function(i, obj){
			if(i>0){
				if(total > parseInt($(obj).val())){
					$(obj).attr("selected","selected");
					return false;
				}
			}
		});
		
	}else{
		$("#objectTargetSumScore2",$.pdialog.getCurrent()).html((evsTotal / total).toFixed(1));
		$("#EVS_POINT2",$.pdialog.getCurrent()).val((evsTotal / total).toFixed(1));
		
		$('#EVS_GRADE2 option',$.pdialog.getCurrent()).each(function(i, obj){
			if(i>0){
				if((evsTotal / total) > parseInt($(obj).val())){
					$(obj).attr("selected","selected");
					return false;
				}
			}
		});
	}
}

</script>
<div class="pageContent" layoutH="5">
<form id="viewConfirmTargetInfoForm" action="/evs/manage/viewEvsBySelfHTSV" method="post" >
	<div style="padding-left:10px;padding-right:10px;padding-top:5px;">
	<%@ include file="/WEB-INF/view/evs/manage/viewPersonalInfoHead_evs.jsp"%>
		<div style="font:bold 14px/20px arial,sans-serif;float:left;height:20px;line-height:20px;">Objective Confirm</div>
		<table class="user_table" width="100%" id="viewRegPersonalTarget_table">	
			<tr>
				<td class="td_title"  style="text-align:center;" width="5%">No</td>
				
				<td class="td_title"  style="text-align:center;" width="25%"><spring:message code="inct.salesman.evaluationItemType"/><!--评价项目--></td>
				
				<td class="td_title" style="text-align:center;" width="35%"><spring:message code="inct.salesman.eval.personal.target"/><!--目标--></td>
				<td class="td_title" style="text-align:center;" width="10%"><spring:message code="evs.viewConfirmTargetInfoAbility.CEZHONGZHI.a"/><!--侧重值--></td>
				<td class="td_title" style="text-align:center;" width="10%"><spring:message code="evs.viewEvsIndex.BENRENPINGJIA.a"/> (%)<!--本人评价（%）--></td>
				<td class="td_title" style="text-align:center;" width="11%"><spring:message code="evs.viewAffirmTarget1Ability.YICIPINGJIA.a"/><!--1次评价--></td>
				<c:if test="${ACTIVITY eq '14015358'}">
				<td class="td_title" style="text-align:center;" width="11%"><spring:message code="evs.viewAffirmTarget1Ability.ERCIPINGJIA.a"/><!--2次评价--></td>
				</c:if>
			</tr>
			<c:forEach items="${viewSSTEvsItem}" var="item" varStatus="i">
				<tr>
					<td class="td_type" style="text-align:center">${i.count}</td>
					
			    	<td class="td_type" style="text-align:left;">${item.ITEM_NAME }</td>
			    	
			    	<td class="td_type" style="text-align:left;">${item.ITEM_CONTENT }</td>
			    	<td class="td_type" style="text-align:right" sysLog="ITEM_SCORE" id="ITEM_SCORE">${item.ITEM_SCORE }</td>
			    	<td class="td_type" style="text-align:right" sysLog="EVS_SCORE">${item.EVS_SCORE }</td>
			    	<c:if test="${ACTIVITY eq '14015357'}">
			    	<td class="td_type" style="text-align:center">
			    	<input name="EVS_SCORE1" value="${item.EVS_SCORE1 }" sysIndex="${item.SEQ }" type="text" size="10" onblur="sumObjectTargetScore(this.value)" class="required number" min="0" max="100"></td>
			    	</c:if>
			    	<c:if test="${ACTIVITY eq '14015358'}">
			    	<td class="td_type" style="text-align:center">${item.EVS_SCORE1 }</td>
			    	<td class="td_type" style="text-align:center">
			    		<input name="EVS_SCORE2" value="${item.EVS_SCORE2 }" sysIndex2="${item.SEQ }" type="text" size="10" onblur="sumObjectTargetScore2(this.value)" class="required number" min="0" max="100"></td>
			    	</c:if>
			    	<input name="ITEM_SEQ" type="hidden" value="${item.SEQ }">
				</tr>
			</c:forEach>
			<tr>
				<td class="td_title"></td>
				
				<td class="td_title"></td>
				
				<td class="td_title"></td>
				<td class="td_title" style="text-align:right;" id="objectTargetSum">0</td>
				<td class="td_title" style="text-align:right;" >${viewEvsObjectInfo.EVS_POINT0 }</td>
				<td class="td_title" style="text-align:right;" id="objectTargetSumScore">${viewEvsObjectInfo.EVS_POINT1 }</td>
				<c:if test="${ACTIVITY eq '14015358'}">
				<td class="td_title" style="text-align:right;" id="objectTargetSumScore2">${viewEvsObjectInfo.EVS_POINT2 }</td>
				</c:if>
			</tr>
		</table>
		
		<br/>
		<table class="user_table" width="100%">
			<tr>
				<td class="td_title"  style="text-align:center;" width="80%"><spring:message code="evs.viewConfirmTargetInfoSST.BENRENYIJIAN.a"/><!--本人意见--></td>
				<td class="td_title"  style="text-align:center;" width="10%"><spring:message code="hr.viewCompetence.title.MARK"/><!--分数--></td>
				<td class="td_title"  style="text-align:center;" width="10%"><spring:message code="sys.affirm.title.affirmLevel"/><!--等级--></td>
			</tr>
			<tr>
				<td class="td_type">
					${viewEvsObjectInfo.AFFIRM_CONTENT0 }
				</td>
				<td class="td_type" style="text-align:center;">
					${viewEvsObjectInfo.EVS_POINT0 }
				</td>
				<td class="td_type"  style="text-align:center;">
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
				<td class="td_title"  style="text-align:center;" width="10%"><spring:message code="sys.affirm.title.affirmLevel"/><!--等级--></td>
			</tr>
			<tr>
				<td class="td_type" style="text-align:center;">
					<textarea style="width:100%;height:100px" id="AFFIRM_CONTENT1" class="editor required" tools="Cut,Copy,Paste,|,Fullscreen">${viewEvsObjectInfo.AFFIRM_CONTENT1 }</textarea>
					<input type="hidden" id="SEQ1" value="${viewEvsObjectInfo.SEQ1 }">
				</td>
				<td class="td_type" style="text-align:center;" id="objectTargetSumScore">${viewEvsObjectInfo.EVS_POINT1 }</td>
				<td class="td_type" style="text-align:center;">
					<select id="EVS_GRADE1" style="width:80px" disabled>
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
								</c:choose>--%>
							</option>
						</c:forEach>
					</select>
				</td>
			</tr>
		</table>
		</c:if>
		<c:if test="${ACTIVITY eq '14015358'}">
		<br/>
		<table class="user_table" width="100%">
			<tr>
				<td class="td_title"  style="text-align:center;" width="80%"><spring:message code="evs.viewConfirmTargetInfoSST.YICIKAOHEYIJIAN.a"/><!--1次考核意见--></td>
				<td class="td_title"  style="text-align:center;" width="10%"><spring:message code="hr.viewCompetence.title.MARK"/><!--分数--></td>
				<td class="td_title"  style="text-align:center;" width="10%"><spring:message code="sys.affirm.title.affirmLevel"/><!--等级--></td>
			</tr>
			<tr>
				<td class="td_type">${viewEvsObjectInfo.AFFIRM_CONTENT1 }</td>
				<td class="td_type" style="text-align:center;">${viewEvsObjectInfo.EVS_POINT1 }</td>
				<td class="td_type" style="text-align:center;">
				 ${item.EVS_GRADE_NAME} 
					<%--<c:choose>
						<c:when test="${viewEvsObjectInfo.EVS_GRADE1 == 'A'}">EX</c:when>
						<c:when test="${viewEvsObjectInfo.EVS_GRADE1 == 'B'}">VG</c:when>
						<c:when test="${viewEvsObjectInfo.EVS_GRADE1 == 'C'}">GD</c:when>
						<c:when test="${viewEvsObjectInfo.EVS_GRADE1 == 'D'}">NI</c:when>
						<c:when test="${viewEvsObjectInfo.EVS_GRADE1 == 'E'}">UX</c:when>
					</c:choose>--%>
				</td>
			</tr>
		</table>
		<br/>
		<table class="user_table" width="100%">
			<tr>
				<td class="td_title"  style="text-align:center;" width="80%"><spring:message code="evs.viewConfirmTargetInfoSST.ERCIKAOHEYIJIAN.a"/><!--2次考核意见--></td>
				<td class="td_title"  style="text-align:center;" width="10%"><spring:message code="hr.viewCompetence.title.MARK"/><!--分数--></td>
				<td class="td_title"  style="text-align:center;" width="10%"><spring:message code="sys.affirm.title.affirmLevel"/><!--等级--></td>
			</tr>
			<tr>
				<td class="td_type" style="text-align:center;">
					<textarea style="width:100%;height:100px" id="AFFIRM_CONTENT2" class="editor required" tools="Cut,Copy,Paste,|,Fullscreen">${viewEvsObjectInfo.AFFIRM_CONTENT2 }</textarea>
					<input type="hidden" id="SEQ2" value="${viewEvsObjectInfo.SEQ2 }">
				</td>
				<td class="td_type" style="text-align:center;" id="objectTargetSumScore2">${viewEvsObjectInfo.EVS_POINT2 }</td>
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
