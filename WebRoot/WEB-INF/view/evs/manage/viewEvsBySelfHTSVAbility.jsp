<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
$(document).ready(function(){
	$("#viewEvsBySelfHTSVAbilityResumeNo",navTab.getCurrentPanel()).change(function(){
		$("#viewEvsBySelfHTSVAbilityForm",navTab.getCurrentPanel()).submit();
	});
});
function sumObjectTargetScoreAbility(object){
	var itemScore = parseInt($(object).parent().parent().find('td[sysLong="ITEM_SCORE"]').html());
	if(parseInt(object.value) > itemScore){
		alertMsg.error("<spring:message code='evs.viewEvsBySelfHTSVAbility.ZIPINGFENSHUBUNENGDAYUXIANGMUQUANZHONGFENSHU.a'/>");//自评分数不能大于项目权重分数
		object.value = 0;
	}
	var total = 0;
	$('input:[name="EVS_SCORE"]',navTab.getCurrentPanel()).each(function(i, obj){
		if($(obj).val() != ''){
			total += parseInt($(obj).val());
		}
	});
	$("#objectTargetSum",navTab.getCurrentPanel()).html(total);
}

function saveEvsBySelf(flag){
	var msg = "<spring:message code='evs.viewAffirmTarget1.LINGSHIBAOCUN.a'/>";//临时保存
	if(flag == 1){
		msg = "<spring:message code='evs.viewConfirmTargetInfoAbility.SHIXING.a'/>";//实行
	}
	var form = $("#viewEvsBySelfHTSVAbilitySaveForm",navTab.getCurrentPanel());
	if (!form.valid()) {
		return false;
	}
	//获取页面的值
	var jsonData = '[';
	$('input:[name="EVS_SCORE"]',navTab.getCurrentPanel()).each(function(i, obj){
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
	
	alertMsg.confirm("<spring:message code='ess.viewMonthDetailConfirmList.QUEDINGYAO.a'/>" + msg + "<spring:message code='ess.viewMonthDetailConfirmList.MAO.a'/>",
  		{okCall:function(){
		  	$.ajax({
  				type: 'POST',
  				url: '/evs/manage/addEvsBySelfTSTOAbility',
  				data:[{ name: 'jsonData', value: jsonData },
  				      { name: 'EVS_POINT', value: $("#objectTargetSum",navTab.getCurrentPanel()).html() },
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
</script>
<c:if test="${not empty resumeList}">
<div class="pageHeader">
	<form id="viewEvsBySelfHTSVAbilityForm" onsubmit="return navTabSearch(this);" action="/evs/manage/viewEvsBySelfHTSVAbility" method="post" >
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td><spring:message code="evs.viewResumeList.PINGJIAMING.a"/><!--评价名--></td>
					<td>
						<select id="viewEvsBySelfHTSVAbilityResumeNo" name="RESUME_SEQ">
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
	<form id="viewEvsBySelfHTSVAbilitySaveForm" action="/evs/manage/addRegPersonalTarget" method="post">
	<div style="padding-left:10px;padding-right:10px;padding-top:5px;padding-bottom:30px;">
		<%@ include file="/WEB-INF/view/evs/manage/viewPersonalInfoHead_evs.jsp"%>
		<div style="font:bold 14px/20px arial,sans-serif;float:left;height:20px;line-height:20px;"><spring:message code="evs.viewProbationEvsResult.BUFEN.a"/><!-- SECTION --> 1</div>
		<c:if test="${viewEvsObjectInfo.ACTIVITY eq '14015356'}">
		<div style="float:right;height:20px;line-height:20px;margin-top:5px;">
			<a class="w_button" onclick="saveEvsBySelf(0)"><span><spring:message code="evs.viewAffirmTarget1.LINGSHIBAOCUN.a"/><!--临时保存--></span></a>
			<a class="w_button" onclick="saveEvsBySelf(1)"><span><spring:message code="evs.viewConfirmTargetInfoAbility.SHIXING.a"/><!--实行--></span></a>
		</div>
		</c:if>
		<table class="user_table" width="100%">	
			<tr>
				<td class="td_title"  style="text-align:center;" width="5%">No</td>
				<td class="td_title"  style="text-align:center;" width="55%"><spring:message code="ar.viewItem.title.xiangmumingcheng"/><!--项目名称--></td>
				<td class="td_title" style="text-align:center;" width="20%"><spring:message code="evs.viewConfirmTargetInfoAbility.CEZHONGZHI.a"/><!--测重值--></td>
				<td class="td_title" style="text-align:center;" width="20%"><spring:message code="evs.viewConfirmTargetInfoHTSV.BENRENPINGJIAFENSHU.a"/><!--本人评价（分数）--></td>
			</tr>
			<c:if test="${viewEvsObjectInfo.ACTIVITY eq '14015356'}">
			<c:forEach items="${viewEvsItem}" var="item" varStatus="i">
				<tr>
					<td class="td_type" style="text-align:center">${i.count}</td>
			    	<td class="td_type">${item.ITEM_NAME }</td>
			    	<td class="td_type" style="text-align:right" sysLong="ITEM_SCORE">${item.ITEM_SCORE }</td>
			    	<td class="td_type" style="text-align:center"><input name="EVS_SCORE" sysIndex="${item.SEQ }" value="${item.EVS_SCORE0 }" type="text" style="width:96%" onblur="sumObjectTargetScoreAbility(this)" class="required number" min="0" max="100"></td>
				</tr>
			</c:forEach>
			</c:if>
			<c:if test="${viewEvsObjectInfo.ACTIVITY ne '14015356'}">
			<c:forEach items="${viewEvsItem}" var="item" varStatus="i">
				<tr>
					<td class="td_type" style="text-align:center">${i.count}</td>
			    	<td class="td_type">${item.ITEM_NAME }</td>
			    	<td class="td_type" style="text-align:right">${item.ITEM_SCORE }</td>
			    	<td class="td_type" style="text-align:right">${item.EVS_SCORE0 }</td>
				</tr>
			</c:forEach>
			</c:if>
			<tr>
				<td class="td_title"></td>
				<td class="td_title"></td>
				<td class="td_title" style="text-align:right;">100</td>
				<td class="td_title" style="text-align:right;" id="objectTargetSum">${viewEvsAffirmInfo.EVS_POINT }</td>
			</tr>
		</table>
	</div>
	</form>
</div>
</c:if>
<c:if test="${empty resumeList}">
	<%@ include file="/WEB-INF/view/evs/manage/no_evs.jsp"%>
</c:if>
