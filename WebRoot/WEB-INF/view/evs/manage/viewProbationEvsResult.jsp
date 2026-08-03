<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
$(document).ready(function(){
	$("#viewRegPersonalTargetProbationResumeNo",navTab.getCurrentPanel()).change(function(){
		$("#viewProbationEvsResultForm",navTab.getCurrentPanel()).submit();
	});
	<c:if test="${ACTIVITY ne 4}">
	$('td:[sysLog="text"]').editable({type:'text',onblur:function(val,settings){
        $(this).html(val);
		this.editing = false;

		var total = 0;
		$('td[sysAff="aff"]',navTab.getCurrentPanel()).each(function(i, obj){
			var index = $(obj).attr("sysIndex");
			if($("#affirmScoreRat_" + index,navTab.getCurrentPanel()).html() != ''){
				total = total + parseInt($("#affirmScoreRat_" + index,navTab.getCurrentPanel()).html()) * parseInt($("#sumAffirmScore_" + index,navTab.getCurrentPanel()).html())/100;
			}
		});
		$("#sumTotalHrPoint",navTab.getCurrentPanel()).html(total.toFixed(1));
		$('#EVS_GRADE option',$.pdialog.getCurrent()).each(function(i, obj){
			if(total > parseInt($(obj).val())){
				$("#sumTotalHrGrade",navTab.getCurrentPanel()).html($(obj).html());
				return false;
			}
		});
	}});
	</c:if>
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
	//保存
	$("#proEvsSave",navTab.getCurrentPanel()).click(function(){

		//获取页面的值
		var jsonData = '[';
		$('td[sysAff="aff"]',navTab.getCurrentPanel()).each(function(i, obj){
			var index = $(obj).attr("sysIndex");
			if (jsonData.length > 1) {
				jsonData += ',{';
			} else {
				jsonData += '{';
			}
			jsonData += ' "AFFIRM_SEQ": "' + $("#affirmScoreRat_" + index,navTab.getCurrentPanel()).attr("sysSeq") + '" ,';
			jsonData += ' "AFFIRM_RAT": "' + $("#affirmScoreRat_" + index,navTab.getCurrentPanel()).html() + '" ,';
			jsonData += ' "adminID": "' + '${LoginUser.adminID}' + '" ,';
			jsonData += ' "adminIP": "' + '${LoginUser.adminIP}' + '" ,';
			jsonData += ' "interCpnyID": "' + '${LoginUser.cpnyId}' + '" ';
			jsonData += '}';
		});
		jsonData += ']';
		
		alertMsg.confirm("<spring:message code='ess.message.confirm_sava'/>",//确定要保存吗？
		  	{okCall:function(){
				 $.ajax({
		  			type: 'POST',
		  			url: '/evs/manage/saveProbationResult',
	  				data:[{ name: 'jsonData', value: jsonData },
	  				      { name: 'RESUME_SEQ', value: '${RESUME_SEQ}' },
	  				      { name: 'FINAL_POINT', value: $("#sumTotalHrPoint",navTab.getCurrentPanel()).html() },
	  				      { name: 'FINAL_GRADE', value: $("#sumTotalHrGrade",navTab.getCurrentPanel()).html() },],
		  			dataType:"json",
		  			cache: false,
		  			success: DWZ.ajaxDone,
		  			error: DWZ.ajaxError
		  	});
		}});
	});
	//试用期评价结束
	$("#proEvsFinish",navTab.getCurrentPanel()).click(function(){
		alertMsg.confirm("<spring:message code='evs.viewProbationEvsResult.QUEDINGYAOJIESHUPINGJIAMA.a'/>",//确定要结束评价吗？
		  	{okCall:function(){
				 $.ajax({
		  			type: 'POST',
		  			url: '/evs/manage/finishProbationResult',
	  				data:[{ name: 'RESUME_SEQ', value: '${RESUME_SEQ}' }],
		  			dataType:"json",
		  			cache: false,
		  			success: navTabAjaxDoneWithForm,
		  			error: DWZ.ajaxError
		  	});
		}});
	});
});
</script>
<div class="pageHeader">
	<form id="viewProbationEvsResultForm" onsubmit="return navTabSearch(this);" action="/evs/manage/viewProbationEvsResult" method="post" >
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td><spring:message code="evs.viewResumeList.PINGJIAMING.a"/><!--评价名--></td>
					<td>
						<select id="viewRegPersonalTargetProbationResumeNo" name="RESUME_SEQ">
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
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">
									<spring:message code="button.search"/><!--查询-->
								</button>
							</div>
						</div>
					</li>
					<c:if test="${ACTIVITY ne 4}">
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="proEvsSave">
									<spring:message code="ar.viewempcalender.title.save"/><!--保存-->
								</button>
							</div>
						</div>
					</li>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="button" id="proEvsFinish">
									<spring:message code="evs.viewEvsResult.JIESHUPINGJIA.a"/><!--结束评价-->
								</button>
							</div>
						</div>
					</li>
					</c:if>
				</ul>
			</div>
		</div>
	</form>
</div>

<div class="pageContent">
	<form id="viewRegPersonalTargetProbationSaveForm" action="/evs/manage/addRegPersonalTargetProbation" method="post">
	<div style="padding-left:10px;padding-right:10px;padding-top:5px;">
	<%@ include file="/WEB-INF/view/evs/manage/viewPersonalInfoHead_evsProbation.jsp"%>
		<div style="font:bold 14px/20px arial,sans-serif;float:left;height:20px;line-height:20px;width:100%;"><spring:message code="evs.viewProbationEvsResult.RENSHIJILU.a"/><!--人事记录--></div>
		<table class="user_table" width="70%">	
			<tr>
				<td rowspan="2" class="td_title" style="text-align:center;width:10%;"><spring:message code="ess.infoApply.check_work"/><!--考勤--></td>
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
		<div style="font:bold 14px/20px arial,sans-serif;float:left;height:20px;line-height:20px;">Objective Confirm</div>
		<table class="user_table" width="100%" id="viewRegPersonalTargetProbation_table">	
			<tr id="rowIdObjectTarget_100">
				<td class="td_title"  style="text-align:center;" width="5%">No</td>
				<td class="td_title"  style="text-align:center;" width="25%"><spring:message code="inct.salesman.evaluationItemType"/><!--评价项目--></td>
				<td class="td_title" style="text-align:center;" width="50%"><spring:message code="inct.salesman.eval.personal.target"/><!--目标--></td>
				<td class="td_title" style="text-align:center;" width="5%"><spring:message code="inct.salesman.ratio"/><!--比率-->(%)</td>
				<c:forEach items="${viewEvsAffirmList}" var="item" varStatus="i">
					<c:if test="${item.AFFIRM_TYPE eq 1}">
						<td class="td_title" sysAff="aff" sysIndex="${i.index }" style="text-align:center;" width="5%">${item.LOCAL_NAME}</td>
					</c:if>
				</c:forEach>
			</tr>
			<c:forEach items="${viewSSTEvsItem}" var="item" varStatus="i">
				<tr>
					<td class="td_type" style="text-align:center">${i.count}</td>
			    	<td class="td_type">${item.ITEM_NAME }</td>
			    	<td class="td_type">${item.ITEM_CONTENT }</td>
			    	<td class="td_type" style="text-align:center" sysLong="ITEM_SCORE">${item.ITEM_SCORE }</td>
					<c:forEach items="${viewEvsAffirmList}" var="aff" varStatus="j">
						<c:if test="${aff.AFFIRM_TYPE eq 1}">
							<td class="td_type" style="text-align:center" sysType="yj" sysLong="AFFIRM_SCORE_${j.index }">
							<c:forEach items="${viewProAffScore}" var="score" varStatus="k">
								<c:if test="${aff.SEQ eq score.AFFIRM_SEQ and item.SEQ eq score.ITEM_SEQ}">
			    					${score.EVS_SCORE }
								</c:if>
							</c:forEach>
							</td>
						</c:if>
					</c:forEach>
				</tr>
			</c:forEach>
			<tr id="rowIdObjectTarget_101">
				<td class="td_title"></td>
				<td class="td_title" colspan="2" style="text-align:right;"><spring:message code="evs.viewProbationEvsResult.YEJIXIAOJI.a"/><!--业绩小计--></td>
				<td class="td_title" style="text-align:center;" id="objectTargetSum">50</td>
				<c:forEach items="${viewEvsAffirmList}" var="item" varStatus="i">
					<c:if test="${item.AFFIRM_TYPE eq 1}">
						<td class="td_title" id="yjAffirmScore_${i.index }" sysIndex="${i.index }" style="text-align:center;"></td>
					</c:if>
				</c:forEach>
			</tr>
			
			<c:forEach items="${viewSSTProbationEvsItem}" var="item" varStatus="i">
				<tr>
					<td class="td_type" style="text-align:center">${i.count}</td>
			    	<td class="td_type" colspan="2">${item.ITEM_CONTENT }</td>
			    	<td class="td_type" style="text-align:center" sysLong="ITEM_SCORE">${item.ITEM_SCORE }</td>
					<c:forEach items="${viewEvsAffirmList}" var="aff" varStatus="j">
						<c:if test="${aff.AFFIRM_TYPE eq 1}">
							<td class="td_type" style="text-align:center" sysType="nl" sysLong="AFFIRM_SCORE_${j.index }">
							<c:forEach items="${viewProAffScore}" var="score" varStatus="k">
								<c:if test="${aff.SEQ eq score.AFFIRM_SEQ and item.SEQ eq score.ITEM_SEQ}">
			    					${score.EVS_SCORE }
								</c:if>
							</c:forEach>
							</td>
						</c:if>
					</c:forEach>
				</tr>
			</c:forEach>
			<tr id="rowIdObjectTarget_102">
				<td class="td_title"></td>
				<td class="td_title" colspan="2" style="text-align:right;"><spring:message code="evs.viewProbationEvsResult.NENGLIXIAOJI.a"/><!--能力小计--></td>
				<td class="td_title" style="text-align:center;" id="objectTargetSum">50</td>
				<c:forEach items="${viewEvsAffirmList}" var="item" varStatus="i">
					<c:if test="${item.AFFIRM_TYPE eq 1}">
						<td class="td_title" id="nlAffirmScore_${i.index }" sysIndex="${i.index }" style="text-align:center;"></td>
					</c:if>
				</c:forEach>
			</tr>
			<tr id="rowIdObjectTarget_103">
				<td class="td_title"></td>
				<td class="td_title" colspan="2" style="text-align:right;"><spring:message code="edu.studentChakan.ZONGFEN.a"/><!--总分--></td>
				<td class="td_title" style="text-align:center;" id="objectTargetSum">100</td>
				<c:forEach items="${viewEvsAffirmList}" var="item" varStatus="i">
					<c:if test="${item.AFFIRM_TYPE eq 1}">
						<td class="td_title" id="sumAffirmScore_${i.index }" sysIndex="${i.index }" style="text-align:center;"></td>
					</c:if>
				</c:forEach>
			</tr>
			
			<tr id="rowIdObjectTarget_104">
				<td class="td_title"></td>
				<td class="td_title" colspan="2" style="text-align:right;"><spring:message code="evs.viewProbationEvsResult.JIAQUANPINGJUNPINGJIAFENSHU.a"/><!--加权平均评价分数--></td>
				<td class="td_title" style="text-align:center;" id="objectTargetSum">100</td>
				<c:forEach items="${viewEvsAffirmList}" var="item" varStatus="i">
					<c:if test="${item.AFFIRM_TYPE eq 1}">
						<td class="td_title" id="affirmScoreRat_${i.index }" sysLog="text" sysIndex="${i.index }" sysSeq="${item.SEQ }" style="text-align:center;">${item.AFFIRM_RAT }</td>
					</c:if>
				</c:forEach>
			</tr>
			<tr id="rowIdObjectTarget_105">
				<td class="td_title"></td>
				<td class="td_title" colspan="2" style="text-align:right;"><spring:message code="evs.viewProbationEvsResult.ZONGJIFENSHU.a"/><!--总计分数--></td>
				<td class="td_title" style="text-align:center;" id="objectTargetSum">100</td>
				<td class="td_title" id="sumTotalHrPoint" style="text-align:center;" colspan="${viewEvsAffirmSize }">${viewEvsObjectInfo.FINAL_POINT }</td>
			</tr>
			<tr id="rowIdObjectTarget_106">
				<td class="td_title"></td>
				<td class="td_title" colspan="2" style="text-align:right;"><spring:message code="evs.viewProbationEvsResult.DENGJI.a"/><!--等级--></td>
				<td class="td_title" style="text-align:center;" id="objectTargetSum">100</td>
				<td class="td_title" id="sumTotalHrGrade" style="text-align:center;" colspan="${viewEvsAffirmSize }">${viewEvsObjectInfo.FINAL_GRADE }</td>
			</tr>
		</table>
		<input type="hidden" id="objectTargetCnt" name="objectTargetCnt" value="${objectTargetCnt }"/>
		<input type="hidden" name="RESUME_SEQ" value="${RESUME_SEQ }"/>
		<input type="hidden" name="EVS_OBJECT_SEQ" value="${viewEvsObjectInfo.EVS_OBJECT_SEQ }"/>
		<input type="hidden" name="SEQ" value="${viewEvsAffirmInfo.SEQ }"/>
		<input type="hidden" id="objectTargetFlag" name="FLAG" value="0"/>
	</div>
	<div style="padding-left:10px;padding-right:10px;padding-top:5px;">
		<div style="font:bold 14px/20px arial,sans-serif;float:left;height:20px;line-height:20px;"><spring:message code="evs.viewProbationEvsResult.YUANGONGZIPING.a"/><!--员工自评--></div>
		<table class="user_table" width="100%">
			<tr id="rowIdEvsBySelf_100">
				<td class="td_title"  style="text-align:center;">Comment</td>
			</tr>
			<tr id="rowIdEvsBySelf_100">
				<td class="td_type">${viewEvsObjectInfo.AFFIRM_CONTENT }</td>
			</tr>
		</table>
	</div>
	<div style="padding-left:10px;padding-right:10px;padding-top:5px;padding-bottom:50px;">
		<div style="font:bold 14px/20px arial,sans-serif;float:left;height:20px;line-height:20px;"><spring:message code="edu.trainResult.PINGJIAZHE.a"/><!--评价者--></div>
		<table class="user_table" width="100%">
			<tr>
				<td class="td_title"  style="text-align:center;" width="5%"><spring:message code="ar.viewcycle.title.xuhao"/><!--序号--></td>
				<td class="td_title"  style="text-align:center;" width="10%"><spring:message code="evs.viewProbationEvsResult.PINGJIAQUFEN.a"/><!--评价区分--></td>
				<td class="td_title" style="text-align:center;" width="15%"><spring:message code="edu.trainResult.PINGJIAZHE.a"/><!--评价者--></td>
				<td class="td_title" style="text-align:center;" width="70%"><spring:message code="hrm.empinfo.Evaluation_message"/><!--评价信息--></td>
			</tr>
			<c:forEach items="${viewEvsAffirmList}" var="item" varStatus="i">
				<tr id="rowIdApplyLotPro${i.index}">
					<td class="td_type" style="text-align: center"><span name="rowIndex">${i.count}</span></td>
					<td class="td_type" style="text-align: center">
						<c:if test="${item.AFFIRM_TYPE eq '1' }"><spring:message code="edu.trainResult.PINGJIAZHE.a"/><!--评价者--></c:if>
						<c:if test="${item.AFFIRM_TYPE eq '2' }"><spring:message code="ess.infoApply.confirm_person"/><!--确认者--></c:if>
					</td>
					<td class="td_type" style="text-align: center">
						${item.AFFIRMOR_INFO}
					</td>
					<td class="td_type">
						${item.AFFIRM_CONTENT}
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>
	</form>
	<select id="EVS_GRADE" style="display:none;">
		<c:forEach items="${viewGradeList}" var="item" varStatus="i">
			<option value="${item.START_SCORE }">${item.EVS_GRADE_NAME}</option>
		</c:forEach>
	</select>
</div>