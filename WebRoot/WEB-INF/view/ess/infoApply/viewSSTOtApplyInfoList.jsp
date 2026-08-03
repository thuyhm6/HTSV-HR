<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
$(document).ready(function(){
	$("#viewSSTOtApplyInfoList_seach",navTab.getCurrentPanel()).click(function(){
		$("#viewSSTOtApplyInfoListForm",navTab.getCurrentPanel()).submit();
	});
});

function otInfoWindow(affirmFlag,param){
	if(affirmFlag == '14015194'){
		$.pdialog.open("/ess/infoApply/viewModifySSTOtApplyInfo?" + param , "otModify", "加班变更申请 ", {width:900,height:600,mask:true});
	}else if(affirmFlag == '123'){
		$.pdialog.open("/ess/infoApply/viewApprovaledOt?" + param + "&APPLY_FLAG=0", "otView", "加班详细明细", {width:800,height:600,mask:true});
	}else{
		$.pdialog.open("/ess/infoApply/viewApprovaledOt?" + param + "&APPLY_FLAG=1", "otView", "加班详细明细", {width:800,height:600,mask:true});
	}
}
</script>
<div class="panel">
<h1>加班事后确认</h1>
<div>
<%@ include file="/WEB-INF/view/hrm/empinfo/viewPersonalInfoHead_ess.jsp"%>
</div>
<div>
	<form name="viewSSTOtApplyInfoListForm" id="viewSSTOtApplyInfoListForm" method="post" action="/ess/infoApply/viewSSTOtApplyInfoList" 
	  onsubmit="return navTabSearch(this);"> 
		<table width="100%" border="0" cellpadding="0" cellspacing="0" height="50">
			<tr>
				<td valign="top">
				<br/>
					<table width="100%"  border="0" cellpadding="0" cellspacing="0" class="user_table">
						<tr>
							<td style="text-align:right" class="td_title" width="20%">
								<spring:message code="ess.workgroup.title.duration" text="期间"/>
							</td>
							<td class="td_type" width="80%" colspan="3">
								<input type="text" id="seach_START_DATE" name="seach_START_DATE" class="Wdate" value="${START_DATE }" onClick="WdatePicker({dateFmt:'yyyy.MM.dd'})"/>~
								<input type="text" id="seach_END_DATE" name="seach_END_DATE" class="Wdate" value="${END_DATE }" onClick="WdatePicker({dateFmt:'yyyy.MM.dd'})"/>
							</td>
						</tr>
						<tr>
							<td style="text-align:right" class="td_title" width="20%">
								审批状态
							</td>
							<td class="td_type" width="80%" colspan="3">
								<ait:SelectSyCodeCombinByCpnyID name="seach_AFFIRM_FLAG" combinParentNo="14014304" selected="${AFFIRM_FLAG}"  cnpyID="${LoginUser.cpnyId}"  limit="all"/>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</form>
</div>
	<div class="pageContent" >
		<div class="formBar">
			<ul class="toolBar">
				<li>
					<a class="add" href="#" id="viewSSTOtApplyInfoList_seach"><span>查询</span></a>
				</li>
			</ul>
		</div> 
		<table class="table" width="100%" layoutH="231" nowrapTD="false">
			<thead>
				<tr>
				    <th width="20">
				    	NO
				    </th>
				    <th width="80" style="text-align: center"><!--日期-->
						日期
					</th>
				    <th width="40" style="text-align: center"><!--星期-->
					          星期
					</th>
					<th width="60" style="text-align: center"><!--加班类型-->
						加班类型
					</th>
					<th width="80" style="text-align: center"><!--开始时间-->
						开始时间
					</th>
					<th width="80" style="text-align: center"><!--结束时间-->
						结束时间
					</th>
					<th width="60" style="text-align: center"><!--加班类型-->
						加班时长
					</th>
					<th width="60" style="text-align: center"><!--加班-->
					  	原因
					</th>
					<th width="60" style="text-align: center"><!--决裁状态-->
						审批状态
					</th>
					<th width="40" style="text-align: center"><!--取消状态-->
						状态
					</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${otList}" var="otApply" varStatus="i">			
					<tr onclick="otInfoWindow('${otApply.DISPLAY_AFFIRM_FLAG}','seach_APPLY_NO=${otApply.APPLY_NO}&seach_APPLY_TYPE=${otApply.OT_TYPE_CODE}')">
					    <td style="text-align: center">${i.count}</td>
					    <td style="text-align: center">${otApply.APPLY_OT_DATE}</td>
					    <td style="text-align: center">${otApply.APPLY_WEEK}</td>
					    <td style="text-align: center">${otApply.OT_TYPE_NAME}</td>
					    <td style="text-align: center">${otApply.OT_FROM_TIME}</td>
					    <td style="text-align: center">${otApply.OT_TO_TIME}</td>
						<td style="text-align: center">${otApply.OT_LENGTH}小时</td>
						<td style="text-align: center">
					        ${otApply.APPLY_REMARK}
						</td>
						<td style="text-align: center">
							${otApply.AFFIRM_FLAG_NAME}
						</td>
						<td style="text-align: center">
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>