<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
$(document).ready(function(){
	//查询
	$("#viewEvsIndexs_Serch",navTab.getCurrentPanel()).click(function(){
		$("#viewEvsIndexsForm",navTab.getCurrentPanel()).submit();
	});

	$("#viewEvsIndexs_evsType",navTab.getCurrentPanel()).change(function(){
		$("#viewEvsIndexsForm",navTab.getCurrentPanel()).submit();
	});
});
</script>
<div class="pageHeader">
	<form id="viewEvsIndexsForm" onsubmit="return navTabSearch(this);" action="/evs/manage/viewEvsIndex" method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td><spring:message code="hr.viewEvaluate.title.EV_TYPE_NAME"/><!--评价类型--></td>
					<td>
						<select id="viewEvsIndexs_evsType" name="evsType">
							<option value="performance" <c:if test="${evsType eq 'performance'}">selected</c:if>><spring:message code="evs.viewEvsIndex.YEJIPINGJIA.a"/><!--业绩评价-->&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>
							<option value="ability" <c:if test="${evsType eq 'ability'}">selected</c:if>><spring:message code="evs.viewEvsIndex.LILIANGPINGJIA.a"/><!--力量评价-->&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option>
							<%-- <option value="probation" <c:if test="${evsType eq 'probation'}">selected</c:if>><spring:message code="evs.viewEvsIndex.SHIYONGQIPINGJIA.a"/><!--试用期评价-->&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</option> --%>
						</select>
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li>
						<a class="buttonActive" id="viewEvsIndexs_Serch" href="#">
							<span><spring:message code="button.search"/><!--查询--></span>
						</a>
					</li>
				</ul>
			</div>
		</div>
	</form>
</div>
<c:if test="${evsType eq 'ability'}">
<div class="pageContent">
	<div style="height:830px;line-height:800px;overflow:auto;overflow-x:hidden;background:url('/resources/images/evs_index_${LoginUser.cpnyId }_${evsType }.jpg') no-repeat;">
		<div style="width:200px;padding-left:60px;margin-top:145px;height:450px;overflow:auto;overflow-x:hidden;float:left;">
			<div style="height:400px;overflow:auto;overflow-x:hidden;">
				<div style="padding:3px;"><a href="#" style="text-decoration:none;" onclick="navTabNum('/evs/manage/viewResumeList?evsType=ability','pageNum=1&amp;menuNo=14015031&amp;navTabId=evs0201','evs0201','<spring:message code="evs.viewEvsIndex.LILIANGKAOHEGAIYAO.a"/>');"><spring:message code="evs.viewEvsIndex.LILIANGKAOHEGAIYAO.a"/><!--力量考核摘要--></a></div>
				<div style="padding:3px;"><a href="#" style="text-decoration:none ;" onclick="navTabNum('/evs/manage/viewEvsSchedulePanel?evsType=ability','pageNum=1&amp;menuNo=14015032&amp;navTabId=evs0202','evs0202','<spring:message code="evs.viewEvsIndex.KAOHERICHENGGUANLI.a"/>');"><spring:message code="evs.viewEvsIndex.KAOHERICHENGGUANLI.a"/><!--考核日程管理--></a></div> 
				<div style="padding:3px;margin-top:53px;"><a href="#" style="text-decoration:none ;" onclick="navTabNum('/evs/manage/viewEvsParamPanel?evsType=ability','pageNum=1&amp;menuNo=14015033&amp;navTabId=evs0203','evs0203','<spring:message code="evs.viewEvsIndex.KAOHEYUNYINGJIZHUN.a"/>');"><spring:message code="evs.viewEvsIndex.KAOHEYUNYINGJIZHUN.a"/><!--考核运营基准--></a></div>
				<div style="padding:3px;"><a href="#" style="text-decoration:none ;" onclick="navTabNum('/evs/manage/viewEvsDistributionRatePanel?evsType=ability','pageNum=1&amp;menuNo=14015034&amp;navTabId=evs0204','evs0204','<spring:message code="evs.viewEvsIndex.FENPEILVGUANLI.a"/>');"><spring:message code="evs.viewEvsIndex.FENPEILVGUANLI.a"/><!--分配率管理--></a></div>
				<div style="padding:3px;margin-top:87px;"><a href="#" style="text-decoration:none ;" onclick="navTabNum('/evs/manage/viewEvsItemPanel?evsType=ability','pageNum=1&amp;menuNo=14015035&amp;navTabId=evs0205','evs0205','<spring:message code="evs.viewEvsIndex.LILIANGXIANGMUDINGYI.a"/>');"><spring:message code="evs.viewEvsIndex.LILIANGXIANGMUDINGYI.a"/><!--力量项目定义--></a></div>
				<div style="padding:3px;margin-top:87px;"><a href="#" style="text-decoration:none ;" onclick="navTabNum('/evs/manage/viewEvsAffirmorSetup?evsType=ability','pageNum=1&amp;menuNo=14015036&amp;navTabId=evs0206','evs0206','<spring:message code="evs.viewEvsIndex.KAOHEDUIXIANGJIKAOHEZHE.a"/>');"><spring:message code="evs.viewEvsIndex.KAOHEDUIXIANGJIKAOHEZHE.a"/><!--考核对象及考核者设定--></a></div>
			</div>
		</div>
		<div style="width:170px;margin-left:200px;margin-top:170px;height:500px;overflow:auto;overflow-x:hidden;float:left;">
			<div style="padding:3px;"><spring:message code="evs.viewEvsIndex.BENRENPINGJIA.a"/><!--本人评价--></div>
			<div style="padding:3px;margin-top:175px;"><spring:message code="evs.viewEvsIndex.CIPINGJIA.a"/><!--次评价--> 1</div>
			<div style="padding:3px;"><spring:message code="evs.viewEvsIndex.CIPINGJIA.a"/><!--次评价--> 2</div>
			<%-- <div style="padding:3px;"><spring:message code="evs.viewEvsIndex.CIPINGJIA.a"/><!--次评价--> 3</div> --%>
			<div style="padding:3px;margin-top:113px;"><a href="#" style="text-decoration:none ;" onclick="navTabNum('/evs/manage/viewEvsResult?evsType=ability','pageNum=1&amp;menuNo=14015037&amp;navTabId=evs0207','evs0207','<spring:message code="evs.viewEvsIndex.LILIANGKAOPINGJIEGUO.a"/>');"><spring:message code="evs.viewEvsIndex.LILIANGKAOPINGJIEGUO.a"/><!--力量考评结果--></a></div>
		</div>
	</div>
</div>
</c:if>
<c:if test="${evsType eq 'performance'}">
<c:if test="${LoginUser.cpnyId eq 'HAE'}">
	<div class="pageContent">
		<div style="height:830px;line-height:800px;overflow:auto;overflow-x:hidden;background:url('/resources/images/evs_index_${LoginUser.cpnyId }_${evsType }.jpg') no-repeat;">
			<div style="width:170px;padding-left:265px;margin-top:170px;height:450px;overflow:auto;overflow-x:hidden;float:left;">
				<div style="height:400px;overflow:auto;overflow-x:hidden;">
					<div style="padding:3px;"><a href="#" style="text-decoration:none ;" onclick="navTabNum('/evs/manage/viewResumeList?evsType=performance','pageNum=1&amp;menuNo=14015023&amp;navTabId=evs0101','evs0101','<spring:message code="evs.viewEvsIndex.YEJIKAOHEZHAIYAO.a"/>');"><spring:message code="evs.viewEvsIndex.YEJIKAOHEZHAIYAO.a"/><!--业绩考核摘要--></a></div>
					<div style="padding:3px;"><a href="#" style="text-decoration:none ;" onclick="navTabNum('/evs/manage/viewEvsSchedulePanel?evsType=performance','pageNum=1&amp;menuNo=14015024&amp;navTabId=evs0102','evs0102','<spring:message code="evs.viewEvsIndex.KAOHERICHENGGUANLI.a"/>');"><spring:message code="evs.viewEvsIndex.KAOHERICHENGGUANLI.a"/><!--考核日程管理--></a></div>
					<div style="padding:3px;margin-top:70px;"><a href="#" style="text-decoration:none ;" onclick="navTabNum('/evs/manage/viewEvsParamPanel?evsType=performance','pageNum=1&amp;menuNo=14015025&amp;navTabId=evs0103','evs0103','<spring:message code="evs.viewEvsIndex.KAOHEYUNYINGJIZHUN.a"/>');"><spring:message code="evs.viewEvsIndex.KAOHEYUNYINGJIZHUN.a"/><!--考核运营基准--></a></div>
					<div style="padding:3px;"><a href="#" style="text-decoration:none ;" onclick="navTabNum('/evs/manage/viewEvsDistributionRatePanel?evsType=performance','pageNum=1&amp;menuNo=14015026&amp;navTabId=evs0104','evs0104','<spring:message code="evs.viewEvsIndex.FENPEILVGUANLI.a"/>');"><spring:message code="evs.viewEvsIndex.FENPEILVGUANLI.a"/><!--分配率管理--></a></div>
					<div style="padding:3px;margin-top:85px;"><a href="#" style="text-decoration:none ;" onclick="navTabNum('/evs/manage/viewEvsAffirmorSetup?evsType=performance','pageNum=1&amp;menuNo=14015027&amp;navTabId=evs0105','evs0105','<spring:message code="evs.viewEvsIndex.KAOHEDUIXIANGJIKAOHEZHE.a"/>');"><spring:message code="evs.viewEvsIndex.KAOHEDUIXIANGJIKAOHEZHE.a"/><!--考核对象及考核者设定--></a></div>
				</div>
			</div>
			<div style="width:170px;margin-left:190px;margin-top:185px;height:400px;overflow:auto;overflow-x:hidden;float:left;">
				<div style="padding:3px;"><spring:message code="evs.viewEvsIndex.BENRENPINGJIA.a"/><!--本人评价--></div>
				<div style="padding:3px;margin-top:75px;">No page available</div>
				<div style="padding:3px;margin-top:85px;"><a href="#" style="text-decoration:none ;" onclick="navTabNum('/evs/manage/viewEvsResult?evsType=performance','pageNum=1&amp;menuNo=14015029&amp;navTabId=evs0107','evs0107','<spring:message code="ar.viewEmpInfoListTanchu.YEJIKAOPINGJIEGUO.b"/>');"><spring:message code="ar.viewEmpInfoListTanchu.YEJIKAOPINGJIEGUO.b"/><!--业绩考评结果--></a></div>
			</div>
			<div style="width:170px;margin-left:130px;margin-top:235px;height:120px;overflow:auto;overflow-x:hidden;float:left;">
				<div style="padding:3px;"><a href="#" style="text-decoration:none ;" onclick="navTabNum('/evs/manage/viewEvsTargetInfoList?evsType=performance','pageNum=1&amp;menuNo=14015028&amp;navTabId=evs0106','evs0106','<spring:message code="ar.viewEmpInfoListTanchu.YEJIKAOHEMUBIAODENGJIZHUANGTAI.b"/>');"><spring:message code="ar.viewEmpInfoListTanchu.YEJIKAOHEMUBIAODENGJIZHUANGTAI.b"/><!--业绩考核目标登记状态--></a></div>
			</div>
		</div>
	</div>
</c:if>
<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
	<div class="pageContent">
		<div style="height:830px;line-height:800px;overflow:auto;overflow-x:hidden;background:url('/resources/images/evs_index_${LoginUser.cpnyId }_${evsType }.jpg') no-repeat;">
			<div style="width:170px;padding-left:265px;margin-top:170px;height:450px;overflow:auto;overflow-x:hidden;float:left;">
				<div style="height:400px;overflow:auto;overflow-x:hidden;">
					<div style="padding:3px;"><a href="#" style="text-decoration:none ;" onclick="navTabNum('/evs/manage/viewResumeList?evsType=performance','pageNum=1&amp;menuNo=14015023&amp;navTabId=evs0101','evs0101','<spring:message code="evs.viewEvsIndex.YEJIKAOHEZHAIYAO.a"/>');"><spring:message code="evs.viewEvsIndex.YEJIKAOHEZHAIYAO.a"/><!--业绩考核摘要--></a></div>
					<div style="padding:3px;"><a href="#" style="text-decoration:none ;" onclick="navTabNum('/evs/manage/viewEvsSchedulePanel?evsType=performance','pageNum=1&amp;menuNo=14015024&amp;navTabId=evs0102','evs0102','<spring:message code="evs.viewEvsIndex.KAOHERICHENGGUANLI.a"/>');"><spring:message code="evs.viewEvsIndex.KAOHERICHENGGUANLI.a"/><!--考核日程管理--></a></div>
					<div style="padding:3px;margin-top:70px;"><a href="#" style="text-decoration:none ;" onclick="navTabNum('/evs/manage/viewEvsParamPanel?evsType=performance','pageNum=1&amp;menuNo=14015025&amp;navTabId=evs0103','evs0103','<spring:message code="evs.viewEvsIndex.KAOHEYUNYINGJIZHUN.a"/>');"><spring:message code="evs.viewEvsIndex.KAOHEYUNYINGJIZHUN.a"/><!--考核运营基准--></a></div>
					<div style="padding:3px;"><a href="#" style="text-decoration:none ;" onclick="navTabNum('/evs/manage/viewEvsDistributionRatePanel?evsType=performance','pageNum=1&amp;menuNo=14015026&amp;navTabId=evs0104','evs0104','<spring:message code="evs.viewEvsIndex.FENPEILVGUANLI.a"/>');"><spring:message code="evs.viewEvsIndex.FENPEILVGUANLI.a"/><!--分配率管理--></a></div>
					<div style="padding:3px;margin-top:82px;"><a href="#" style="text-decoration:none ;" onclick="navTabNum('/evs/manage/viewEvsAffirmorSetup?evsType=performance','pageNum=1&amp;menuNo=14015027&amp;navTabId=evs0105','evs0105','<spring:message code="evs.viewEvsIndex.KAOHEDUIXIANGJIKAOHEZHE.a"/>');"><spring:message code="evs.viewEvsIndex.KAOHEDUIXIANGJIKAOHEZHE.a"/><!--考核对象及考核者设定--></a></div>
					<%-- <div style="padding:3px;"><a href="#" style="text-decoration:none ;" onclick="navTabNum('/evs/manage/viewEvsConfirmSetupList?evsType=performance','pageNum=1&menuNo=14015431&navTabId=evs0108','evs0108','<spring:message code="evs.viewEvsIndex.YEJIKAOPINGQUERENZHE.a"/>');"><spring:message code="evs.viewEvsIndex.YEJIKAOPINGQUERENZHE.a"/><!--业绩考评确认者设置--></a></div> --%>
				</div>
			</div>
			<div style="width:170px;margin-left:190px;margin-top:180px;height:400px;overflow:auto;overflow-x:hidden;float:left;">
				<div style="padding:3px;"><spring:message code="evs.viewEvsIndex.BENRENPINGJIA.a"/><!--本人评价--></div>
				<div style="padding:3px;margin-top:70px;">No page available</div>
				<div style="padding:3px;margin-top:85px;"><a href="#" style="text-decoration:none ;" onclick="navTabNum('/evs/manage/viewEvsResult?evsType=performance','pageNum=1&amp;menuNo=14015029&amp;navTabId=evs0107','evs0107','<spring:message code="ar.viewEmpInfoListTanchu.YEJIKAOPINGJIEGUO.b"/>');"><spring:message code="ar.viewEmpInfoListTanchu.YEJIKAOPINGJIEGUO.b"/><!--业绩考评结果--></a></div>
			</div>
			<div style="width:170px;margin-left:130px;margin-top:220px;height:120px;overflow:auto;overflow-x:hidden;float:left;">
				<div style="padding:3px;"><a href="#" style="text-decoration:none ;" onclick="navTabNum('/evs/manage/viewEvsTargetInfoList?evsType=performance','pageNum=1&amp;menuNo=14015028&amp;navTabId=evs0106','evs0106','<spring:message code="ar.viewEmpInfoListTanchu.YEJIKAOHEMUBIAODENGJIZHUANGTAI.b"/>');"><spring:message code="ar.viewEmpInfoListTanchu.YEJIKAOHEMUBIAODENGJIZHUANGTAI.b"/><!--业绩考核目标登记状态--></a></div>
			</div>
		</div>
	</div>
</c:if>
</c:if>
