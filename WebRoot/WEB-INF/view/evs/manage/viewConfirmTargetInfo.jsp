<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
$(document).ready(function(){
	var total = 0;
	$('#viewRegPersonalTarget_table tr td:[sysLog="ITEM_SCORE"]',$.pdialog.getCurrent()).each(function(i, obj){
		total += parseInt($(obj).html());
	});
	$("#objectTargetSum",$.pdialog.getCurrent()).html(total);
});

function modifyObjectActivity(flag,level){
	var msg = "<spring:message code='hrm.approve.RETURN'/>";//退回
	var comment = "";
	if(flag == 1){
		msg = "<spring:message code='evs.viewConfirmTargetInfoSST.CHENGREN.a'/>";//承认
	} 
	if (level == 1) {
		comment = document.getElementById("AFFIRM_COMMENT1").value;
	}
	if (level == 2) {
		comment = document.getElementById("AFFIRM_COMMENT2").value;
	}
	alertMsg.confirm("<spring:message code='ess.viewMonthDetailConfirmList.QUEDINGYAO.a'/>" + msg + "<spring:message code='ess.viewMonthDetailConfirmList.MAO.a'/>",
  		{okCall:function(){
		  	$.ajax({
  				type: 'POST',
  				url: '/evs/manage/modifyObjectActivity',
  				data:[{name:'FLAG',value:flag},{name:'EVS_OBJECT_SEQ',value:'${viewEvsObjectInfo.SEQ}'},{name:'AFFIRM_COMMENT',value:comment},{name:'LEVEL',value:level}],
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
</script>
<div class="pageContent" layoutH="5">
	<div style="padding-left:10px;padding-right:10px;padding-top:5px;">
	<%@ include file="/WEB-INF/view/evs/manage/viewPersonalInfoHead_evs.jsp"%>
		<div style="font:bold 14px/20px arial,sans-serif;float:left;height:20px;line-height:20px;">Objective Confirm</div>
		<table class="user_table" width="100%" id="viewRegPersonalTarget_table">	
			<tr>
				<td class="td_title"  style="text-align:center;" width="5%">No</td>
				
				<td class="td_title"  style="text-align:center;" width="25%"><spring:message code="inct.salesman.evaluationItemType"/><!--评价项目--></td>
				
				<td class="td_title" style="text-align:center;" width="35%"><spring:message code="inct.salesman.eval.personal.target"/><!--目标--></td>
				<td class="td_title" style="text-align:center;" width="25%"><spring:message code="inct.salesman.ratio"/><!--比率-->(%)</td>
			</tr>
			<c:forEach items="${viewSSTEvsItem}" var="item" varStatus="i">
				<tr>
					<td class="td_type" style="text-align:center">${i.count}</td>
					
			    	<td class="td_type" style="text-align:left;">${item.ITEM_NAME }</td>
			    	
			    	<td class="td_type" style="text-align:left;">${item.ITEM_CONTENT }</td>
			    	<td class="td_type" style="text-align:right" sysLog="ITEM_SCORE">${item.ITEM_SCORE }</td>
				</tr>
			</c:forEach>
			<tr>
				<td class="td_title"></td>
				
				<td class="td_title"></td>
				
				<td class="td_title"></td>
				<td class="td_title" style="text-align:right;" id="objectTargetSum">0</td>
			</tr>
		</table>
	</div>
	<div style="padding-left:10px;padding-right:10px;padding-top:5px;padding-bottom:30px;">
		<div style="font:bold 14px/20px arial,sans-serif;float:left;height:20px;line-height:20px;"><spring:message code="evs.viewProbationEvsResult.BUFEN.a"/> 2</div>
		<table class="user_table" width="100%">
			<tr id="rowIdEvsBySelf_100">
				<td class="td_title" colspan="2" style="text-align:center;"><spring:message code="ess.infoApply.comment"/></td>
			</tr>
			<c:if test="${viewEvsObjectInfo.ACTIVITY eq ACTIVITY}">
			<tr>
				<td class="td_title" style="text-align:center;width:10%;"><spring:message code="evs.viewEvsAffirmorSetup.YIJIPINGJIAZHE.a"/><!--一级评价者--></td>
				<td >
				<%-- <textarea style="width:99.5%;height:100px" id="AFFIRM_COMMENT" name="AFFIRM_COMMENT" class="editor required" tools="Cut,Copy,Paste,|,Fullscreen">${viewEvsObjectInfo.AFFIRM_COMMENT1 }</textarea> --%>
				<textarea <c:if test = "${LEVEL == 2 }">readonly</c:if> type="text" id="AFFIRM_COMMENT1" name="AFFIRM_COMMENT1" style="width:99.5%;height:100px">${viewEvsObjectInfo.AFFIRM_COMMENT1 }</textarea>
				</td>
			</tr>
			<tr>
				<td class="td_title"  style="text-align:center;"><spring:message code="evs.viewEvsAffirmorSetup.ERJIPINGJIAZHE.a"/><!--二级评价者--></td>
				<td ><textarea <c:if test = "${LEVEL == 1 }">readonly</c:if> type="text" id="AFFIRM_COMMENT2" name="AFFIRM_COMMENT2" style="width:99.5%;height:100px">${viewEvsObjectInfo.AFFIRM_COMMENT2 }</textarea></td>
			</tr>
			</c:if>
			<c:if test="${viewEvsObjectInfo.ACTIVITY ne ACTIVITY}">
			<tr>
				<td class="td_title" style="text-align:center;width:10%;"><spring:message code="evs.viewEvsAffirmorSetup.YIJIPINGJIAZHE.a"/><!--一级评价者--></td>
				<td><textarea readonly style="width:99.5%;height:100px">${viewEvsObjectInfo.AFFIRM_COMMENT1 }</textarea></td>
				
			</tr>
			<tr>
				<td class="td_title"  style="text-align:center;"><spring:message code="evs.viewEvsAffirmorSetup.ERJIPINGJIAZHE.a"/><!--二级评价者--></td>
				<td><textarea readonly style="width:99.5%;height:100px">${viewEvsObjectInfo.AFFIRM_COMMENT2 }</textarea></td>
			</tr>
			</c:if>
		</table>
	</div>
	<div class="formBar">
			<ul>
				<c:if test="${viewEvsObjectInfo.ACTIVITY eq ACTIVITY}">
				<li>
					<div class="button">
						<div class="buttonContent"><!-- 通过 -->
							<button type="button" onclick="modifyObjectActivity(1,'${LEVEL}')">
								<spring:message code="evs.viewConfirmTargetInfoSST.CHENGREN.a"/><!--承认-->
							</button>
						</div>
					</div>
				</li>
				<li>
					<div class="button">
						<div class="buttonContent"><!-- 否决 -->
							<button type="button" onclick="modifyObjectActivity(0,'${LEVEL}')">
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
