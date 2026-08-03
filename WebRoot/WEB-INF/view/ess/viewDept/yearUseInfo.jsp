<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ include file="/WEB-INF/view/hrm/empinfo/viewPersonalInfoHead_ess.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<script type="text/javascript">
$(document).ready(function(){
	$("#applyVacInfo_save",navTab.getCurrentPanel()).click(function(){
		var $form = $("#applyVacInfoForm",navTab.getCurrentPanel());
		alertMsg.confirm("<spring:message code='ess.message.confirm_sava' />",//确定要保存吗？
	  		{okCall:function(){
			  	$.ajax({
	  				type: 'post',
	  				url:$form.attr("action"),
	  				data:$form.serializeArray(),
	  				dataType:"json",
	  				cache: false,
	  				success: navTabAjaxDone,
					error: DWZ.ajaxError
	  			});
	  		}});
		return false;
	});
});
function getStartEndTime(){
	var itime = $("#VAC_ID",navTab.getCurrentPanel()).val();
	$.ajax({
		cache: false,
		 type: 'post',
		 async:false,
		 url: '/hrm/recruitManage/doSql',
		 data:{sql:"SELECT A.VACATION_NO, TO_CHAR(A.STRT_DATE,'YYYY.MM.DD') STRT_DATE, TO_CHAR(A.END_DATE,'YYYY.MM.DD') END_DATE FROM AR_VAC_EMP A WHERE TO_DATE('"+itime+"','YYYY.MM.DD') BETWEEN A.STRT_DATE AND A.END_DATE AND A.PERSON_ID = ${LoginUser.personId}"},
		 dataType:"json",
		 success: function(data) {
				var startTime = data.result[0].STRT_DATE;
				var endTime = data.result[0].END_DATE;
				var VACATION_NO = data.result[0].VACATION_NO;
				$("#vacStartTimeSpan",navTab.getCurrentPanel()).html("<spring:message code='hr.viewTranslate.title.PUBLIC_START_DATE' />："+startTime);//开始日期
				$("#vacEndTimeSpan",navTab.getCurrentPanel()).html("<spring:message code='hr.viewTranslate.title.PUBLIC_END_DATE' />："+endTime);//结束时间
				$("#vacStartTime",navTab.getCurrentPanel()).val(startTime);
				$("#vacEndTime",navTab.getCurrentPanel()).val(endTime);
				$("#VACATION_NO",navTab.getCurrentPanel()).val(VACATION_NO);
			},
		 error: DWZ.ajaxError,
	});
}
function delVacApply(applyNo){
	alertMsg.confirm("<spring:message code='hrm.alert.empinfo.Sure.delete' />",//确定要删除吗？
	  		{okCall:function(){
				$.ajax({
					cache: false,
					 type: 'post',
					 async:false,
					 url: '/hrm/recruitManage/doSql',
					 data:{sql:"UPDATE AR_VAC_EMP_APPLY SET ACTIVITY = '0' WHERE APPLY_NO = '"+ applyNo +"'"},
					 dataType:"json",
					 success: function(){
						 navTabSearch($("#addAddressInfo"));
					 },
					 error: DWZ.ajaxError,
				});
	  		}})
}
</script>
<div class="pageHeader">
	<form id="addAddressInfo" method="post"
		action="/ess/viewDept/yearUseInfo" class="pageForm required-validate"
		onsubmit="return navTabSearch(this);">
		<div class="searchBar">
			<table class="searchContent">
			<tr>
				<td><!-- 年份 --><spring:message code="pa.payear.title.payear" /></td>
				<td>
					<ait:date yearName="seach_VAR_YEAR"  yearSelected="${VAR_YEAR}"  yearPlus="10"/>
				</td>
				
				<%-- <td><!-- 基准日 --><spring:message code="ess.title.JIZHUNRI" /></td>
				<td>
					<input type="text" id="seach_VAR_YEAR" name="seach_VAR_YEAR" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy.MM.dd'})" value="${VAR_YEAR}"/>
				</td>
				<td>
					<spring:message code="pa.payear.title.payear"/><!-- 年份 -->
				</td>
				<td>
					<input type="text" id="seach_VAR_YEAR" name="seach_VAR_YEAR" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy'})"value="${VAR_YEAR}" />
				</td> --%>
			</tr>
			</table>
			<div class="subBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit" id="addAddressInfoButton">
									<spring:message code="public.title.search"/><!--检索-->
								</button>
								
							</div>
						</div>
					</li>
				</ul>
			</div>
		</div>
</div>
<div class="pageContent">
 	<form id="addAddressInfo" method="post"
		action="/ess/viewDept/yearUseInfo" class="pageForm required-validate"
		onsubmit="return navTabSearch(this);">
		<div>
		<h2>
			<spring:message code="ess.infoApply.year_leave_information"/><!-- 年假信息 -->
		</h2></div><br/>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			class="user_table margin_b">
			<tr>
				<td class="td_title" width="5%">
					No
				</td>
				<td class="td_title" width="10%">
					<spring:message code="org.title.STARTDATE"/><!-- 开始日期 -->
				</td>
				<td class="td_title" width="10%">
					<spring:message code="hrm.recruitManage.END_DATE1"/><!-- 结束日期 -->
				</td>
				<td class="td_title" width="10%">
					<spring:message code="ess.infoApply.nianjiazongtianshu"/><!-- 年假总天数 -->
				</td>
				<td class="td_title" width="10%">					
				    <spring:message code="ess.infoApply.yiniannianjiatianshu"/><!-- 移年年假天数 -->
				</td>
				<td class="td_title" width="10%">
					<font color="red"><spring:message code="ar.yearUseInfo.TESHUNIANJIATIANSHU.b"/></font><!-- 特殊年假天数 -->
				</td>
				<td class="td_title" width="10%">
					<spring:message code="ess.infoApply.yixiunianjiatianshu"/><!-- 已休年假天数 -->
				</td>
				<td class="td_title" width="10%">
					<spring:message code="ess.infoApply.shenpizhongnianjiatianshu"/><!-- 审批中年假天数 -->
				</td>
				<!--<td class="td_title" width="10%">
					<spring:message code="ess.infoApply.jiesuannianjiatianshu"/> 结算年假天数 
				</td>
				-->
				<td class="td_title" width="10%">
					<spring:message code="ess.infoApply.nianjiashengyu"/><!-- 年假剩余 -->
				</td>
			</tr>
			<c:forEach items="${yearInfo}" var="item" varStatus="i">
				<tr>
					<td class="td_type">
						${i.count}
					</td>
					<td class="td_type">
						${item.STRT_DATE}
					</td>
					<td class="td_type">
						${item.END_DATE}
					</td>
					<td class="td_type">
						${item.LAST_YEAR_VAC + item.TOT_VAC_CNT + item.ADD_VAC}
					</td>
					<td class="td_type">
						${item.LAST_YEAR_VAC}
					</td>
					<td class="td_type">
						${item.ADD_VAC}
					</td>
					<td class="td_type">
						${item.USE_VAC}
					</td>
					<td class="td_type">
						${item.AFFIRM_USE_VAC}
					</td>
					<!--<td class="td_type">
						${item.MENT_VAC}
					</td>
					-->
					<td class="td_type">
						${item.LAST_YEAR_VAC + item.TOT_VAC_CNT + item.ADD_VAC - item.AFFIRM_USE_VAC - item.USE_VAC}
					</td>
				</tr>
			</c:forEach>
		</table>

		<div><h2>
			<spring:message code="ess.infoApply.nianjiashiyongqingkuang"/><!-- 年假使用情况 -->
		</h2></div>
	</form>
<br/>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			class="user_table margin_b">

			<tr>
				<td class="td_title" width="5%">
					No
				</td>
				<td class="td_title" width="10%">
					<spring:message code="hr.viewTranslate.title.PUBLIC_START_DATE"/><!-- 开始日期 -->
				</td>
				<td class="td_title" width="10%">
					<spring:message code="ess.infoApply.title.startTime"/><!-- 开始时间 -->
				</td>
				<td class="td_title" width="10%">
					<spring:message code="hrm.recruitManage.END_DATE1"/><!-- 结束日期 -->
				</td>
				<td class="td_title" width="10%">
					<spring:message code="ess.infoApply.end_time"/><!-- 结束时间 -->
				</td>
				<td class="td_title" width="10%">
					<spring:message code="ess.infoApply.duration"/><!-- 时长 -->
				</td>
				<td class="td_title" width="10%">
					<spring:message code="ess.infoApply.approval_status"/><!-- 审批状态 -->
				</td>
			</tr>
			<c:forEach items="${yearUseInfo}" var="item" varStatus="i">
				<tr>
					<td class="td_type">${i.count}</td>
					<td class="td_type">${item.FROM_DATE}</td>
					<td class="td_type">${item.FROM_TIME}</td>
					<td class="td_type">${item.TO_DATE}</td>
					<td class="td_type">${item.TO_TIME}</td>
					<td class="td_type">
					<c:if test="${item.DAY_HOURS != 0 }">
						<c:if test="${item.QUANTITY ge item.DAY_HOURS }">
							<fmt:formatNumber type="number"  value="${item.QUANTITY / item.DAY_HOURS + (item.QUANTITY % item.DAY_HOURS == 0 ? 0 : -1)}" pattern="#" maxFractionDigits="0"/>&nbsp;<!-- 天-->
							<spring:message code="ar.viewsummaryparameteritem.title.day"/>
						</c:if>&nbsp;${item.QUANTITY % item.DAY_HOURS}&nbsp;<!-- 小时--><spring:message code="ar.viewsummaryparameteritem.title.hour"/>
					</c:if>
					<c:if test="${item.DAY_HOURS == 0 }">
						${item.QUANTITY}&nbsp;<!-- 小时--><spring:message code="ar.viewsummaryparameteritem.title.day"/>
					</c:if>
					</td>
					<td class="td_type">${item.AFFIRM_FLAG}</td>

				</tr>
			</c:forEach>
		</table>
</div >