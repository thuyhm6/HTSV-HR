<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<html>
<head>
<title>CHR-Hub</title><!-- 【${LOGIN_CPNY}】 -->
<link href="/resources/css/dwzUI/themes/hub/style.css" rel="stylesheet" type="text/css" />
<script src="/resources/js/jquery/jquery.all.js" type="text/javascript"></script>
<style>
.formBar { clear:both; padding:0 5px; height:30px; padding-top:5px; border-style:solid; border-width:1px 0 0 0;}
.formBar ul { float:right;}
.formBar li { float:left; margin-left:5px;}
td{padding:5px;}
</style>
<script>
function printView(){
	document.getElementById("buttonDiv").style.display = 'none';
	window.print();
	document.getElementById("buttonDiv").style.display = '';
}
$(document).ready(function(){
	$('div[sysLong="printDiv"]').each(function(i, obj){
		var height = $(obj).css("height");
		height = parseInt(height.substring(0,4));
		if(height > 1142){
			$(obj).css("height","2136px");
		}
	});
});
</script>
</head>
<body>
<div>
	<div class="formBar" id="buttonDiv">
			<ul>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" onclick="printView()">
								<spring:message code="hrm.approve.PRINTING"/><!--印刷-->
							</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	<c:forEach items="${viewEvsObjectInfo}" var="viewEvsObjectInfo" varStatus="i">
	<div sysLong="printDiv" style="min-height:1068px;height:auto;">
	<div style="padding-left:10px;padding-right:10px;padding-top:5px;">
	<%@ include file="/WEB-INF/view/evs/manage/viewPersonalInfoHead_evs.jsp"%>
	
		<%-- <div style="width:100%;font:bold 14px/20px arial,sans-serif;float:left;height:20px;line-height:20px;"><spring:message code="evs.viewEvsAffirmRat.KAOQINBIAOZHUN.a"/><!--考勤标准--></div>
		<table class="user_table" width="80%">
			<tr>
				<td class="td_title"  style="text-align:center;" width="25%"><spring:message code="ar.monthwork.title.kuanggong"/><!--旷工--></td>
				<td class="td_title"  style="text-align:center;" width="75%"><spring:message code="evs.viewEvsAffirmRat.YICIJLIANGCIJIYISHANG.a"/><!--1次 'D'；2次及以上 'E'--></td>
			</tr>
			<tr>
				<td class="td_title"  style="text-align:center;" width="25%"><spring:message code="ar.monthwork.title.EarlyLeave"/><!--早退--></td>
				<td class="td_title"  style="text-align:center;" width="75%"><spring:message code="evs.viewEvsAffirmRat.YICIJLIANGCIJIYISHANG.a"/><!--1次 'D'；2次及以上 'E'--></td>
			</tr>
			<tr>
				<td class="td_title"  style="text-align:center;" width="25%"><spring:message code="ar.monthwork.title.Lateness"/><!--迟到--></td>
				<td class="td_title"  style="text-align:center;" width="75%"><spring:message code="evs.viewEvsAffirmRat.SANZHIWUCILIUCIJIYISHANG.a"/><!--3~5次 'D'；6次及以上 'E'--></td>
			</tr>
		</table>
		<br/>
		<table class="user_table" width="100%">	
			<tr>
				<td class="td_title"  style="text-align:center;" width="12%"><spring:message code="ar.monthwork.title.kuanggong"/><!--旷工--></td>
				<td class="td_title"  style="text-align:center;" width="12%"><spring:message code="evs.viewAffirmTarget11HTSV.BINGSHIJIA.a"/><!--病/事假--></td>
				<td class="td_title"  style="text-align:center;" width="12%"><spring:message code="evs.viewAffirmTarget11HTSV.CHANBUJISHENG.a"/><!--产/哺/计生--></td>
				<td class="td_title"  style="text-align:center;" width="12%"><spring:message code="evs.viewAffirmTarget11HTSV.TINGGONG.a"/><!--停工--></td>
				<td class="td_title"  style="text-align:center;" width="12%"><spring:message code="evs.viewAffirmTarget11HTSV.HUNSANG.a"/><!--婚丧--></td>
				<td class="td_title"  style="text-align:center;" width="12%"><spring:message code="ar.viewArAnnualStandard.title.ninjia"/><!--年假--></td>
				<td class="td_title"  style="text-align:center;" width="12%"><spring:message code="ess.infoApply.LATE_TIMES"/><!--迟到次数--></td>
				<td class="td_title"  style="text-align:center;" width="12%"><spring:message code="ess.infoApply.leave_early"/><!--早退--></td>
			</tr>
			<tr>
				<td class="td_type"  style="text-align:center;">${viewEvsObjectInfo.KUANGGONG }</td>
				<td class="td_type"  style="text-align:center;">${viewEvsObjectInfo.BINGJIA }</td>
				<td class="td_type"  style="text-align:center;">${viewEvsObjectInfo.CHANJIA }</td>
				<td class="td_type"  style="text-align:center;">${viewEvsObjectInfo.TINGGONG }</td>
				<td class="td_type"  style="text-align:center;">${viewEvsObjectInfo.HUNSANG }</td>
				<td class="td_type"  style="text-align:center;">${viewEvsObjectInfo.NIANJIA }</td>
				<td class="td_type"  style="text-align:center;">${viewEvsObjectInfo.CHIDAO }</td>
				<td class="td_type"  style="text-align:center;">${viewEvsObjectInfo.ZAOTUI }</td>
			</tr>
		</table> --%>
		
		<div style="font:bold 14px/20px arial,sans-serif;float:left;height:20px;line-height:20px;">SECTION 1</div>
		<table class="user_table" width="100%" id="viewRegPersonalTarget_table">	
			<tr>
				<td class="td_title"  style="text-align:center;" width="3%">No</td>
				<td class="td_title"  style="text-align:center;" width="20%"><spring:message code="inct.salesman.evaluationItemType"/><!--评价项目--></td>
				<td class="td_title"  style="text-align:center;" width="40%"><spring:message code="evs.viewConfirmTargetInfoPrint.ZHIBIAOXIANGMU.a"/><!--指 标 项 目--></td>
				<td class="td_title" style="text-align:center;" width="8%"><spring:message code="evs.viewConfirmTargetInfoAbility.CEZHONGZHI.a"/><!--测重值--></td>
				<td class="td_title" style="text-align:center;" width="8%"><spring:message code="evs.viewEvsIndex.BENRENPINGJIA.a"/><!--本人评价--></td>
				<td class="td_title" style="text-align:center;" width="10%"><spring:message code="evs.viewAffirmTarget1Ability.YICIPINGJIA.a"/><!--1 次 考核--></td>
				<td class="td_title" style="text-align:center;" width="10%"><spring:message code="evs.viewAffirmTarget1Ability.ERCIPINGJIA.a"/><!--2 次 考核--></td>
			</tr>
			<c:forEach items="${viewEvsObjectInfo.viewSSTEvsItem}" var="item" varStatus="i">
				<tr>
					<td class="td_type" style="text-align:center">${i.count}</td>
					<td class="td_type" style="text-align:center">${item.ITEM_NAME}</td>
			    	<td class="td_type" style="text-align:left;">${item.ITEM_CONTENT }${item.GROUP_NAME }</td>
			    	<td class="td_type" style="text-align:right" sysLong="ITEM_SCORE">${item.ITEM_SCORE }</td>
			    	<td class="td_type" style="text-align:right">${item.EVS_SCORE }${item.EVS_SCORE0 }</td>
			    	<td class="td_type" style="text-align:right">${item.EVS_SCORE1 }</td>
			    	<td class="td_type" style="text-align:right">${item.EVS_SCORE2 }</td>
				</tr>
			</c:forEach>
			<tr>
				<td class="td_title"></td>
				<td class="td_title"></td>
				<td class="td_title"></td>
				<td class="td_title" style="text-align:right;" id="objectTargetSum">100</td>
				<td class="td_title" style="text-align:right;">${viewEvsObjectInfo.EVS_POINT0 }</td>
				<td class="td_title" style="text-align:right;" id="objectTargetSumAffirmScore">${viewEvsObjectInfo.EVS_POINT1 }</td>
				<td class="td_title" style="text-align:right;" id="objectTargetSumAffirmScore2">${viewEvsObjectInfo.EVS_POINT2 }</td>
			</tr>
		</table>
		
		<div style="font:bold 14px/20px arial,sans-serif;float:left;height:20px;line-height:20px;">SECTION 2</div>
		<table class="user_table" width="100%">
			<tr>
				<td class="td_title" style="text-align:center;" width="100%"><spring:message code="evs.viewConfirmTargetInfoPrint.YEJIFENXIZHUYAOYEJIYUBUZU.a"/><!--业绩分析(主要业绩与不足)--></td>
			</tr>
			<tr>
				<td class="td_type">${viewEvsObjectInfo.AFFIRM_CONTENT0 }</td>
			</tr>
		</table>
		
		<c:if test="${ACTIVITY eq '14015357' or ACTIVITY eq '14015358' or ACTIVITY eq '14015359'}">
		<br/>
		<table class="user_table" width="100%">
			<tr>
				<td class="td_title"  style="text-align:center;" width="10%"><spring:message code="evs.viewConfirmTargetInfoPrint.YICIKAOHEDENGJI.a"/><!--1 次 考核等级--></td>
				<th class="td_title"  style="text-align:center;" width="10%"><spring:message code="hr.viewCompetence.title.MARK"/><!--分数--></th>
				<td class="td_title"  style="text-align:center;" width="80%"><spring:message code="evs.viewConfirmTargetInfoPrint.YICIPINGYU.a"/><!--1 次 评语--></td>
			</tr>
			<tr>
				<td class="td_type" style="text-align:center;">
				${viewEvsObjectInfo.EVS_GRADE1}
					<%-- <c:choose>
						<c:when test="${viewEvsObjectInfo.EVS_GRADE1 == 'A'}">EX</c:when>
						<c:when test="${viewEvsObjectInfo.EVS_GRADE1 == 'B'}">VG</c:when>
						<c:when test="${viewEvsObjectInfo.EVS_GRADE1 == 'C'}">GD</c:when>
						<c:when test="${viewEvsObjectInfo.EVS_GRADE1 == 'D'}">NI</c:when>
						<c:when test="${viewEvsObjectInfo.EVS_GRADE1 == 'E'}">UX</c:when>
					</c:choose> --%>
				</td>
				<td class="td_type">${viewEvsObjectInfo.EVS_POINT1 } </td>
				<td class="td_type">${viewEvsObjectInfo.AFFIRM_CONTENT1 } </td>
			</tr>
		</table>
		</c:if>
		
		<c:if test="${ACTIVITY eq '14015358' or ACTIVITY eq '14015359'}">
		<br/>
		<table class="user_table" width="100%">
			<tr>
				<td class="td_title"  style="text-align:center;" width="10%"><spring:message code="evs.viewConfirmTargetInfoPrint.ERCIKAOHEDENGJI.a"/><!--2 次 考核等级--></td>
				<th class="td_title"  style="text-align:center;" width="10%"><spring:message code="hr.viewCompetence.title.MARK"/><!--分数--></th>
				<td class="td_title"  style="text-align:center;" width="80%"><spring:message code="evs.viewConfirmTargetInfoPrint.ERCIPINGYU.a"/><!--2 次 评语--></td>
			</tr>
			<tr>
				<td class="td_type"  style="text-align:center;">
				${viewEvsObjectInfo.EVS_GRADE2}
					<%-- <c:choose>
						<c:when test="${viewEvsObjectInfo.EVS_GRADE2 == 'A'}">EX</c:when>
						<c:when test="${viewEvsObjectInfo.EVS_GRADE2 == 'B'}">VG</c:when>
						<c:when test="${viewEvsObjectInfo.EVS_GRADE2 == 'C'}">GD</c:when>
						<c:when test="${viewEvsObjectInfo.EVS_GRADE2 == 'D'}">NI</c:when>
						<c:when test="${viewEvsObjectInfo.EVS_GRADE2 == 'E'}">UX</c:when>
					</c:choose> --%>
				</td>
				<td class="td_type">${viewEvsObjectInfo.EVS_POINT2 } </td>
				<td class="td_type">${viewEvsObjectInfo.AFFIRM_CONTENT2 } </td>
			</tr>
		</table>
		</c:if>
		<c:if test="${ACTIVITY eq '14015359'}">
		<br/>
		<%-- <table class="user_table" width="100%">
			<tr>
				<td class="td_title"  style="text-align:center;" width="10%"><spring:message code="evs.viewConfirmTargetInfoPrint.SANCIKAOHEDENGJI.a"/><!--3 次 考核等级--></td>
				<td class="td_title"  style="text-align:center;" width="90%"><spring:message code="evs.viewConfirmTargetInfoPrint.SANCIPINGYU.a"/><!--3 次 评语--></td>
			</tr>
			<tr>
				<td class="td_type"  style="text-align:center;">${viewEvsObjectInfo.EVS_GRADE3} </td>
				<td class="td_type">${viewEvsObjectInfo.AFFIRM_CONTENT3 } </td>
			</tr>
		</table> --%>
		</c:if>
	</div>
	</div>
	</c:forEach>
</div>
</body>
</html>
