<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
$(document).ready(function(){
	$("#viewRegPersonalTargetProbationResumeNo",navTab.getCurrentPanel()).change(function(){
		$("#viewRegPersonalTargetProbationForm",navTab.getCurrentPanel()).submit();
	});
	$('td[sysAff="aff"]',navTab.getCurrentPanel()).each(function(i, obj){
		var index = $(obj).attr("sysIndex");
		
		var total = 0;
		var yjTotal = 0;
		var nlTotal = 0;
		$('td[sysLong="AFFIRM_SCORE_' + index + '"]',navTab.getCurrentPanel()).each(function(i, obj){
			if($(obj).html() != ''){
				var objVal = 0;
				if(!isNaN(parseInt($(obj).html()))){
					objVal = parseInt($(obj).html());
				}
				if($(obj).attr("sysType") == 'yj'){
					yjTotal += objVal;
				}else{
					nlTotal += objVal;
				}
				total += objVal;
			}
		});
		$("#yjAffirmScore_" + index,navTab.getCurrentPanel()).html(yjTotal);
		$("#nlAffirmScore_" + index,navTab.getCurrentPanel()).html(nlTotal);
		$("#sumAffirmScore_" + index,navTab.getCurrentPanel()).html(total);
	});
});
</script>
<c:if test="${not empty resumeList}">
<div class="pageContent">
	<form id="viewRegPersonalTargetProbationSaveForm" action="/evs/manage/addRegPersonalTargetProbation" method="post">
	<div style="padding-left:10px;padding-right:10px;padding-top:5px;">
		<div style="font:bold 14px/20px arial,sans-serif;float:left;width:60%;">
			<%@ include file="/WEB-INF/view/evs/manage/viewPersonalInfoHead_evsProbation.jsp"%>
		</div>
		<div style="font:bold 14px/20px arial,sans-serif;float:left;height:20px;line-height:20px;width:100%;"><spring:message code="evs.viewProbationResultEmp.SHIYONGQIPINGJIAJIEGUO.a"/><!--试用期评价结果--></div>
		<table class="user_table" width="60%">	
			<tr>
				<td class="td_title" style="text-align:center;width:33%;"><spring:message code="evs.viewProbationResultEmp.JIEGUO.a"/><!--结果--></td>
				<td class="td_title" style="text-align:center;width:33%;"><spring:message code="evs.viewProbationEvsResult.DENGJI.a"/><!--等级--></td>
				<td class="td_title" style="text-align:center;width:33%;"><spring:message code="evs.viewProbationEvsResult.ZONGJIFENSHU.a"/><!--总计分数--></td>
			</tr>
			<tr>
				<td class="td_type" style="text-align:center">
					<c:if test="${viewEvsObjectInfo.FINAL_GRADE eq 'A'}"><spring:message code="evs.viewProbationResultEmp.YOUXIU.a"/><!--优秀--></c:if>
					<c:if test="${viewEvsObjectInfo.FINAL_GRADE eq 'B'}"><spring:message code="evs.viewProbationResultEmp.LIANGHAO.a"/><!--良好--></c:if>
					<c:if test="${viewEvsObjectInfo.FINAL_GRADE eq 'C'}"><spring:message code="evs.viewProbationResultEmp.HEGE.a"/><!--合格--></c:if>
					<c:if test="${viewEvsObjectInfo.FINAL_GRADE eq 'D'}"><spring:message code="evs.viewProbationResultEmp.BUHEGEYUYICITUI.a"/><!--不合格，予以辞退--></c:if>
				</td>
				<td class="td_type" style="text-align:center">&nbsp;${viewEvsObjectInfo.FINAL_GRADE }&nbsp;</td>
				<td class="td_type" style="text-align:center">&nbsp;${viewEvsObjectInfo.FINAL_POINT }&nbsp;</td>
			</tr>
		</table>
		<div style="font:bold 14px/20px arial,sans-serif;float:left;height:20px;line-height:20px;width:100%;"><spring:message code="evs.viewProbationEvsResult.RENSHIJILU.a"/><!--人事记录--></div>
		<table class="user_table" width="60%">	
			<tr>
				<td rowspan="2" class="td_title" style="text-align:center;width:10%;"><spring:message code="ess.attendance.state"/><!--考勤--></td>
				<td colspan="2" class="td_title" style="text-align:center;width:20%;"><spring:message code="evs.viewProbationEvsResult.CHIDAOZAOTUI.a"/><!--迟到/早退--></td>
				<td colspan="2" class="td_title" style="text-align:center;width:20%;"><spring:message code="ar.monthwork.title.kuanggong"/><!--旷工--></td>
				<td colspan="2" class="td_title" style="text-align:center;width:20%;"><spring:message code="ess.viewpersonalpainfo.shijia"/><!--事假--></td>
			</tr>
			<tr>
				<td colspan="2" class="td_type" style="text-align:center">&nbsp;${viewEvsObjectInfo.CHIDAO }&nbsp;</td>
				<td colspan="2" class="td_type" style="text-align:center">&nbsp;${viewEvsObjectInfo.KUANGGONG }&nbsp;</td>
				<td colspan="2" class="td_type" style="text-align:center">&nbsp;${viewEvsObjectInfo.SHIJIA }&nbsp;</td>
			</tr>
			<tr>
				<td rowspan="2" class="td_title" style="text-align:center;width:10%;" ><spring:message code="evs.viewProbationEvsResult.JIANGFA.a"/><!--奖罚--></td>
				<td colspan="3" class="td_title" style="text-align:center;width:30%;" ><spring:message code="evs.viewProbationEvsResult.JIANG.a"/><!--奖--></td>
				<td colspan="3" class="td_title" style="text-align:center;width:30%;" ><spring:message code="evs.viewProbationEvsResult.FA.a"/><!--罚--></td>
			</tr>
			<tr>
				<td colspan="3" class="td_type">&nbsp;
					<c:forEach items="${getEvsRewardInfo}" var="item" varStatus="i">
						${item.LOCAL_NAME }<br/>
					</c:forEach>
				</td>
				<td colspan="3" class="td_type">&nbsp;
					<c:forEach items="${getEvsPunishmentInfo}" var="item" varStatus="i">
						${item.LOCAL_NAME }<br/>
					</c:forEach>
				</td>
			</tr>
		</table>
		<div style="font:bold 14px/20px arial,sans-serif;float:left;height:20px;line-height:20px;width:100%;"><spring:message code="evs.viewProbationResultEmp.SHIYONGQIPINGJIADENGJICANKAO.a"/><!--试用期评价等级参考--></div>
		<table class="user_table" width="60%">	
			<tr>
				<td class="td_title" style="text-align:center;width:25%;"><spring:message code="evs.viewProbationEvsResult.DENGJI.a"/><!--等级--></td>
				<td class="td_title" style="text-align:center;width:25%;"><spring:message code="evs.viewProbationResultEmp.KAISHIFENSHU.a"/><!--开始分数--></td>
				<td class="td_title" style="text-align:center;width:25%;"><spring:message code="evs.viewProbationResultEmp.JIESHUFENSHU.a"/><!--结束分数--></td>
				<td class="td_title" style="text-align:center;width:25%;"><spring:message code="evs.viewProbationResultEmp.JIEGUO.a"/><!--结果--></td>
			</tr>
			<c:forEach items="${viewGradeList}" var="item" varStatus="i">
				<tr>
					<td class="td_type" style="text-align:center">${item.EVS_GRADE_NAME}</td>
					<td class="td_type" style="text-align:center">${item.START_SCORE }</td>
					<td class="td_type" style="text-align:center">${item.END_SCORE }</td>
					<td class="td_type" style="text-align:center">
						<c:if test="${item.EVS_GRADE_NAME eq 'A'}"><spring:message code="evs.viewProbationResultEmp.YOUXIU.a"/><!--优秀--></c:if>
						<c:if test="${item.EVS_GRADE_NAME eq 'B'}"><spring:message code="evs.viewProbationResultEmp.LIANGHAO.a"/><!--良好--></c:if>
						<c:if test="${item.EVS_GRADE_NAME eq 'C'}"><spring:message code="evs.viewProbationResultEmp.HEGE.a"/><!--合格--></c:if>
						<c:if test="${item.EVS_GRADE_NAME eq 'D'}"><spring:message code="evs.viewProbationResultEmp.BUHEGEYUYICITUI.a"/><!--不合格，予以辞退--></c:if>
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>
	</form>
</div>
</c:if>
<c:if test="${empty resumeList}">
	<%@ include file="/WEB-INF/view/evs/manage/no_evs.jsp"%>
</c:if>