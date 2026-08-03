<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
$(document).ready(function(){
	$("#executeProcess",navTab.getCurrentPanel()).click(function(){
		var processType="";
		var flag=false;
		var num = 0;
		$("input[name='processType']",navTab.getCurrentPanel()).each(function(){
			if($(this).attr("checked") == "checked"){
				processType = $(this).val();
				flag = true;
				num+=1;
			}
		});
		if(flag == false){
			alertMsg.error('<spring:message code="org.title.SELECT_EXECUTE" />');
			return false;
		}
		if(num>1){
			alertMsg.error('<spring:message code="org.orgManage.STEP_BY_STEP.Z" />');//请一步一步操作
			return false;
		}
		alertMsg.confirm('<spring:message code="org.title.IS_SELECT_EXECUTE" />',
	  		  	{okCall:function(){
			  	$.ajax({
	  				type:'POST',
	  				url:'/org/orgManage/executeResumeProcess',
	  				data:{type:processType,RESUME_NO:$("#viewResumeProcessResumeNo",navTab.getCurrentPanel()).val()},
	  				dataType:"json",
	  				cache: false,
	  				success: navTabAjaxDoneWithForm,
	  				error: DWZ.ajaxError
	  			});
	  	}});
		return false;
	});
	$("#viewResumeProcessResumeNo",navTab.getCurrentPanel()).change(function(){
		$("#viewResumeProcessForm",navTab.getCurrentPanel()).submit();
	});
});
</script>
<div class="pageHeader">
	<form id="viewResumeProcessForm" onsubmit="return navTabSearch(this);" action="/org/orgManage/viewResumeProcess" method="post" >
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td><spring:message code="org.title.VERSION_NAME" /><!-- 版本名称 --></td>
					<td>
						<select id="viewResumeProcessResumeNo" name="RESUME_NO">
							<c:forEach items="${orgResumeList}" var="result">
								<option value="${result.NO}" <c:if test="${result.NO eq RESUME_NO}">selected</c:if>>${result.NO }&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${result.RESUME_NAME}</option>
							</c:forEach>
						</select>
						&nbsp;&nbsp;<spring:message code="org.title.status" /><!-- 状态 -->：${resumeProcessInfo.ACTIVITY }
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
				<c:if test="${resumeProcessInfo.ACTIVITY != 'Confirmed'}">
					<li>
						<a class="buttonActive" id="executeProcess" href="#">
							<span><spring:message code="org.title.WORK_EXECUTE" /><!-- 工作执行 --></span>
						</a>
					</li>
				</c:if>
				</ul>
			</div>
		</div>
	</form>
</div>

<div class="pageHeader">
	<div class="searchBar" style="height:400px;line-height:360px;overflow:auto;overflow-x:hidden;background:url('/resources/images/resume_process.jpg') no-repeat;">
		<div style="width:180px;height:360px;line-height:360px;overflow:auto;overflow-x:hidden;float:left;padding-left:55px;">
			<div style="padding-top:95px;">
				<c:if test="${resumeProcessInfo.COPY_FLAG eq '1'}">
					<input type="checkbox" name="processType" value="copyOrg"/>&nbsp;<font color="#0A258F"><spring:message code="org.title.COPY_ORG" /><!-- 复制现组织 --></font>
				</c:if>
				<c:if test="${resumeProcessInfo.COPY_FLAG ne '1'}">
					<input type="checkbox" name="processType" value="copyOrg"/>&nbsp;<font color="#ff8000"><spring:message code="org.title.COPY_ORG" /><!-- 复制现组织 --></font>
				</c:if>
			</div>
			<div style="padding-left:17px;padding-top:57px;">
				<a href="#" onclick="navTabNum('/org/orgManage/viewComposeOrg','RESUME_NO=${resumeProcessInfo.NO}&pageNum=1&menuNo=14013676&navTabId=org0203','org0203','<spring:message code="org.title.ORG_CONSTITUTE" />');" href="#" style="cursor:hand"><font color="white"><spring:message code="org.title.ORG_CONSTITUTE" /></font></a><!-- 组织图构成 -->
			</div>
		</div>
		<div style="width:180px;height:360px;line-height:360px;overflow:auto;overflow-x:hidden;float:left;padding-left:90px;">
			<div style="padding-left:17px;padding-top:96px;">
				<a href="#" onclick="navTabNum('/org/orgManage/viewDeptManagerCheck','RESUME_NO=${resumeProcessInfo.NO}&pageNum=1&amp;menuNo=14013677&amp;navTabId=org0204','org0204','<spring:message code="org.title.OCCUPANT_CHECK" />');"><font color="white"><spring:message code="org.title.OCCUPANT_CHECK" /><!-- 任职者核查 --></font></a><!-- 任职者核查 -->
			</div>
			<div style="padding-top:55px;">
				<c:if test="${resumeProcessInfo.SCFL_FLAG eq '1'}">
					<input type="checkbox" name="processType" value="scfl"/>&nbsp;<font color="#0A258F"><spring:message code="org.title.BUILD_BISESDATA" /><!-- 生成发令基础材料 --></font>
				</c:if>
				<c:if test="${resumeProcessInfo.SCFL_FLAG ne '1'}">
					<input type="checkbox" name="processType" value="scfl"/>&nbsp;<font color="#ff8000"><spring:message code="org.title.BUILD_BISESDATA" /><!-- 生成发令基础材料 --></font>
				</c:if>
			</div>
			<div style="padding-left:17px;padding-top:59px;">
				<a href="#" onclick="navTabNum('/org/orgManage/viewOrgExperirnceInsideList','RESUME_NO=${resumeProcessInfo.NO}&pageNum=1&amp;menuNo=14013678&amp;navTabId=org0205','org0205','<spring:message code="org.title.ORDERS_CHECK" />');"><font color="white"><spring:message code="org.title.ORDERS_CHECK" /><!-- 发令核查 --></font></a>
			</div>
			<div style="padding-left:17px;padding-top:52px;">
				<a href="#" onclick="navTabNum('/org/orgManage/viewOrgPreExperirnceInsideList','RESUME_NO=${resumeProcessInfo.NO}&pageNum=1&amp;menuNo=14013679&amp;navTabId=org0206','org0206','<spring:message code="org.title.PRORDERS_CHECK" />');"><font color="white"><spring:message code="org.title.PRORDERS_CHECK" /><!-- 预发令核查 --></font></a>
			</div>
		</div>
		<div style="width:180px;height:360px;line-height:360px;float:left;padding-left:105px;">
			<div style="padding-top:85px;">
				<c:if test="${resumeProcessInfo.SCZZ_FLAG eq '1'}">
					<input type="checkbox" name="processType" value="sczz"/>&nbsp;<font color="#0A258F"><spring:message code="org.title.BUILD_ORG_UPDATEINFO" /><!-- 生成组织变更详细内容 --></font>
				</c:if>
				<c:if test="${resumeProcessInfo.SCZZ_FLAG ne '1'}">
					<input type="checkbox" name="processType" value="sczz"/>&nbsp;<font color="#ff8000"><spring:message code="org.title.BUILD_ORG_UPDATEINFO" /><!-- 生成组织变更详细内容 --></font>
				</c:if>
			</div>
			<div style="padding-left:17px;padding-top:47px;">
				<a href="#" onclick="navTabNum('/org/orgManage/viewOrgChangeInfoList','pageNum=1&amp;menuNo=14013681&amp;navTabId=org0208','org0208','<spring:message code="org.title.UPDATE_RESUME_DEPT" />');"><font color="white"><spring:message code="org.title.UPDATE_RESUME_DEPT" /><!-- 变更履历-部门 --></font></a>
			</div>
			<div style="padding-left:17px;padding-top:127px;">
			 	<a href="#" onclick="navTabNum('/org/orgManage/viewCommonOrgTreeInfo?targetRel=viewChangeInfoList','RESUME_NO=${resumeProcessInfo.NO}&pageNum=1&amp;menuNo=14013682&amp;navTabId=org0209','org0209','<spring:message code="org.title.UPDATE_CHECK" />');"><font color="white"><spring:message code="org.title.UPDATE_CHECK" /><!-- 变更查看 --></font></a>
			 </div>
			<div style="padding-top:48px;">
				<c:if test="${resumeProcessInfo.QDZZ_FLAG eq '1'}">
					<input type="checkbox" name="processType" value="qdzz" DISABLED/>&nbsp;<font color="#0A258F"><spring:message code="org.title.ORG_IS_TRUE" /><!-- 确定组织 --></font>
				</c:if>
				<c:if test="${resumeProcessInfo.QDZZ_FLAG ne '1'}">
					<input type="checkbox" name="processType" value="qdzz"/>&nbsp;<font color="#ff8000"><spring:message code="org.title.ORG_IS_TRUE" /><!-- 确定组织 --></font>
				</c:if>
			</div>
		</div>
	</div>
</div>
