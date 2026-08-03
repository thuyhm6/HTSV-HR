<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript">
function batchSubmit1(form,callback) {
 	$form = $('#updateLeaveApplyAffirmForm');
	if (!$form.valid()) {
		return false;
	}
    var checked=false;
	var ids= document.getElementsByName("c1");
	for(var i=0;i<ids.length;i++){
		if(ids[i].checked){
			checked=true;
		}
	}
	if(!checked){
		alertMsg.error('<spring:message code="alert.message.ess.affirmApply.chooseApplyRecordFirstForBatch"/>'); 
		return false;
	}
    $form.attr("action","/ess/wageApplication/batchSubmitApplication?AFFIRM_FLAG="+flag);
	$.ajax({
		type: form.method || 'POST',
		url:$form.attr("action"),
		data:$form.serializeArray(),
		dataType:"json",
		cache: false,
		success: function(data){ //请求成功后处理函数。
			if(data.statusCode=="200"){
				navTabSearch("updateLeaveApplyAffirmForm");
				alertMsg.correct(data.message);
			}else{
				if(data.result=="2"){
					alertMsg.info(data.message);
				}else{
					alertMsg.error(data.message);
				}
			}   
   	 	}  ,
		error: DWZ.ajaxError
	});
	return false;
}

function batchSubmit123(form,flag){
	$.pdialog.open("/ess/wageApplication/batchContent","addRowByIDEss0242_Check","决裁批注", 
		{width:300,height:150,mask:true});
}
function batchAllSubmit(){
	$('#updateLeaveApplyAffirmForm').attr("action","/ess/wageApplication/batchSubmitApplication?AFFIRM_FLAG="+flag);
    $('#updateLeaveApplyAffirmForm').submit();
}
</script>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/ess/wageApplication/viewWageApplicationList" method="post" rel="pagerForm">
	<div class="searchBar" >
		<table class="searchContent">
			<tr>
				<td>社号/姓名：</td>
				<td><input type="text" name="seach_EMPID" value="${EMPID}"/></td>
				<td>开始时间：</td>
				<td> <input type="text" name="seach_STARTTIME" class="date required" readonly="true" value="${STARTTIME}"/>
				    <a class="inputDateButton" href="javascript:;"><spring:message code="public.title.choose"/><!-- 选择 --></a>
				</td>
				<td>结束时间：</td>
				<td><input type="text" name="seach_ENDTIME" class="date required" readonly="true" value="${ENDTIME}"/>
					<a class="inputDateButton"><spring:message code="public.title.choose" /><!-- 选择 --></a>
				</td>
				<td>审批状态：</td>
				<td>
				 <select name="seach_STATE" value="${STATE}">
					 <option value="">全部</option>
					 <option value="0" <c:if test="${STATE eq '0'}">selected</c:if>>未决裁</option>
					 <option value="3" <c:if test="${STATE eq '3'}">selected</c:if>>决裁中</option>
					 <option value="1" <c:if test="${STATE eq '1'}">selected</c:if>>通过</option>
					 <option value="2" <c:if test="${STATE eq '2'}">selected</c:if>>否决</option>
				 </select>
				</td>
			</tr>
		</table>
		<div class="subBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">
								<!--检索--><spring:message code="public.title.search"/>
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
<form name="updateLeaveApplyAffirmForm" id="updateLeaveApplyAffirmForm" method="post" action="/ess/wageApplication/batchSubmitApplication"
	onsubmit="return batchSubmit1(this, navTabAjaxDone);">
	<div class="formBar">
	<ul class="toolBar">
		<input type="hidden" id="batcon" name="AFFIRM_CONTENT"/>
		<li><a class="buttonActive"
			onClick="batchSubmit123('updateLeaveApplyAffirmForm','1')"><span>批量通过</span></a></li>
		<li><a class="buttonActive"
			onClick="batchSubmit123('updateLeaveApplyAffirmForm','2')"><span>批量否决</span></a></li>
	</ul>
	</div>
	 
	<table class="table" width="100%" layoutH="206">
	  <thead>
		<tr>
			<th width="10%" align="center"><input type="checkbox" class="checkboxCtrl" group="c1" ></th>
			<th width="15%" align="center"><spring:message code="ess.viewApply.title.applyName"/><!-- 申请者 --></th>
			<th width="30%" align="center"><spring:message code="hr.viewCondSql.title.NEIRONG"/><!--申请内容--></th>
			<th width="10%" align="center">附件查看</th>
			<th width="10%" align="center"><spring:message code="ess.viewApply.title.applyDate"/><!--申请日期--></th>
			<%--<th width="10%" align="center"><spring:message code="ar.viewArAdjustRest.title.chakanxiangxi"/><!--详细查看--></th>--%>
			<th width="10%" align="center"><spring:message code="ess.trans.title.affirmStatus"/><!--决裁状态--></th>
			<th width="15%" align="center">Type</td>
		</tr>
		</thead>
		<tbody>
			<c:forEach items="${wageList}" var="wage" varStatus="i">			
				<tr target="waid" rel="${wage.id}">
					<td width="5%"><c:if test="${wage.AFFIRMOR_ID eq LoginUser.personId}"><input type="checkbox" name="c1" id="c1" value="${wage.ESS_AFFIRM_NO}">${wage.ESS_AFFIRM_NO}</c:if></td>
					<td width="15%">${wage.EMPNAME}[${wage.EMPID}]</td>
					<td width="30%">${wage.TITLE}</td>
					<td><a href="/ess/wageApplication/showWageAppliFileList?APPLY_NO=${wage.ID}&PERSON_ID=${wage.CREATED_BY}" target="dialog" mask="true" width="730" height="500">附件查看</a></td>
					<td width="10%">${wage.CREATEDATE}</td>
					<%--<td width="10%"><a href="/ess/wageApplication/showApplicationList?APPLY_NO=${wage.ID}&PERSON_ID=${wage.CREATED_BY}" target="dialog" mask="true" width="730" height="500">点击查看</a></td>--%>
					<td width="10%">
						<c:if test="${wage.FLAG eq '0'}">未决裁</c:if><c:if test="${wage.FLAG eq '3'}">决裁中</c:if>
						<c:if test="${wage.FLAG eq '1'}">通过</c:if><c:if test="${wage.FLAG eq '2'}">否决</c:if>
					</td>
					<td width="15%">
						<c:choose>
							<c:when test="${wage.AFFIRMOR_ID eq LoginUser.personId && (wage.FLAG eq '0' || wage.FLAG eq '3')}">
								<a href="/ess/wageApplication/proveApplicationList?APPLY_NO=${wage.ID}&APPLY_TYPE=217886" target="navTab" rel="proApp_3666" title="费用审批">审批</a>
							</c:when>
							<c:otherwise>
								<a href="/ess/wageApplication/showWageApplicationList?APPLY_NO=${wage.ID}&APPLY_TYPE=217886" target="navTab" title="审批查看">审批查看</a>
							</c:otherwise>
						</c:choose>
					</td>
				</tr>			
			</c:forEach>
		</tbody>
	</table>	
</form>
	<c:set value="/ess/wageApplication/viewWageApplicationList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>