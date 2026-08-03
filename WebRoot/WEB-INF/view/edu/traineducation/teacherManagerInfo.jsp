<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
$(function(){
	var BUSINESS_ACT_TIME='${teacherManagerInfo.ALLTIME}';
	if(BUSINESS_ACT_TIME!=''){
		var year=parseInt(BUSINESS_ACT_TIME)/12;
		year=Math.floor(year);
		var month=parseInt(BUSINESS_ACT_TIME)%12;
		if(year>0){
			$('#allTime').html(year+"<spring:message code='edu.teacherManager.NIANLING.a'/>"+month+"<spring:message code='hr.viewPersonalInfo.title.WORKINFO_MONTH'/>");//年零           个月
		}else{
			$('#allTime').html(month+"<spring:message code='hr.viewPersonalInfo.title.WORKINFO_MONTH'/>");// 个月
		}
	}
});
</script>
<div class="pageContent" layoutH="10">
	<form method="post" action="/edu/traineducation/updateTeacherManager" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone);">
		<input type="hidden" name="TEACHER_NO" id="TEACHER_NO" value="${teacherManagerInfo.TEACHER_NO }">
		<div class="pageFormContent nowrap">
		<table id="eduTable" class="user_table" width="100%" border="1" cellpadding="2" cellspacing="1" >
		<tr>
		<td class="td_title" width="1%"><spring:message code="empsubject.tcrNm"/><!--讲师姓名--></td>
		<td class="td_type"  width="20%" >
		<span>${teacherManagerInfo.TEACHER_NAME }</span>
		</td>
		</tr>
		<tr>
		<td class="td_title" width="1%"><spring:message code="alert.pa.pasalarycanshu.shehao"/><!--社号
			--></td>
		<td class="td_type"  width="20%" >
		<span>${teacherManagerInfo.EMPID }</span>
		</td>
		</tr>
		
		<tr>
		<td class="td_title" width="1%"><spring:message code="ar.attendanceView.viewNoSwipingCard.deptName"/><!--部门
			--></td>
		<td class="td_type"  width="20%" >
		<span>${teacherManagerInfo.ORG_NAME_LOCAL }</span>
		</td>
		</tr>
		<tr>
		<td class="td_title" width="1%"><spring:message code="ess.trans.title.postGradeName"/><!--职级
			--></td>
		<td class="td_type"  width="20%" >
		<span>${teacherManagerInfo.POST_GRADE_NO_NAME }</span>
		</td>
		</tr>
		<tr>
		<td class="td_title" width="1%"><spring:message code="inct.salesman.position"/><!--职务
			--></td>
		<td class="td_type"  width="20%" >
		<span>${teacherManagerInfo.POSITION_NO_NAME }</span>
		</td>
		</tr><tr>
		<td class="td_title" width="1%"><spring:message code="edu.teacherManager.JIANGSHIJIBIE.a"/><!--讲师级别
			--></td>
		<td class="td_type"  width="20%" >
		<ait:SelectSyCodeByCpnyID name="TEACH_LEVEL_CODE" id="TEACH_LEVEL_CODE"
                    parentNo="14015140" cnpyID="${defaultCpny}" selected="${teacherManagerInfo.TEACH_LEVEL_CODE }" limit="all"/>
		</td>
		</tr><tr>
		<td class="td_title" width="1%"><spring:message code="edu.teacherManager.JIANGKELINGYU.a"/><!--讲课领域
			--></td>
		<td class="td_type"  width="20%" >
		<ait:SelectSyCodeByCpnyID name="TEACH_FIELD_CODE" id="TEACH_FIELD_CODE"
                    parentNo="14015148" cnpyID="${defaultCpny}" selected="${teacherManagerInfo.TEACH_FIELD_CODE }" limit="all"/>
		</td>
		</tr><tr>
		<td class="td_title" width="1%"><spring:message code="edu.teacherManager.YEWUDANDANGSHIJIAN.a"/><!--业务担当时间
			--></td>
		<td class="td_type"  width="20%" >
		<!-- <input type="text" style="width:60px;" name="BUSINESS_ACT_TIME_year" id="BUSINESS_ACT_TIME_year" value="" min="0">
		 <span style="float:left;margin-top:4px;">年</span>
		 <input type="text" style="width:60px;" name="BUSINESS_ACT_TIME_month" id="BUSINESS_ACT_TIME_month" value="" min="1">月 -->
		<span id="allTime"></span>
		</td>
		</tr>
		<tr>
		<td class="td_title" width="1%"><spring:message code="edu.teacherManager.PINYONGSHIJIAN.a"/><!--聘用时间
			--></td>
		<td class="td_type"  width="1%" >
		<input type="text" name="HIRE_TIME"  id="HIRE_TIME" class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy'})" value="${teacherManagerInfo.HIRE_TIME }" />
		</td>
		</tr>
		<tr>
		<td class="td_title" width="1%"><spring:message code="edu.teacherManager.JIEPINSHIJIAN.a"/><!--解聘时间
			--></td>
		<td class="td_type"  width="1%" >
		<input type="text" name="FIRING_TIME"  id="FIRING_TIME" class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy'})" value="${teacherManagerInfo.FIRING_TIME }" />
		</td>
		</tr>
		<tr>
		<td class="td_title" width="1%"><spring:message code="ar.attendanceView.viewNoSwipingCard.status"/><!--状态
			--></td>
		<td class="td_type"  width="1%" >
		<ait:SelectSyCodeByCpnyID name="TEACH_STATUS_CODE" id="TEACH_STATUS_CODE"
                    parentNo="14015155" cnpyID="${defaultCpny}" selected="${teacherManagerInfo.TEACH_STATUS_CODE }" limit="all"/>
		</td>
		</tr>
		<tr>
		<td class="td_title" width="1%"><spring:message code="pa.salary.canShu.beiZhu"/><!--备注
			--></td>
		<td class="td_type"  width="1%" >
		 <textarea name="REMARK"  id="REMARK" style="width:300px;height:80px">${teacherManagerInfo.REMARK }</textarea>
		</td>
		</tr>
		
		</table>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit"><!-- 提交 --><spring:message code="public.title.submit"/></button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close"><!-- 取消 --><spring:message code="public.title.cancle"/></button></div></div></li>
			</ul>
		</div>
	</form>
</div>
