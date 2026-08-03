<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageContent">
	<div class="pageFormContent nowrap" layoutH="60">
		<table class="table" width="100%" >
			<tr>
				<td><!-- 工号 --><spring:message code="public.title.empId"/></td>
				<td>${arAdjustRestInfo.EMPID}</td>
			</tr>
			<tr>
				<td><!-- 姓名 --><spring:message code="public.title.name"/></td>
				<td>${arAdjustRestInfo.LOCAL_NAME}</td>
			</tr>
			<tr>
				<td><!-- 部门 --><spring:message code="public.title.deptName"/></td>
				<td>${arAdjustRestInfo.DEPTNAME}</td>
			</tr>
			<tr>
				<td><!-- 一月调休总数 --><spring:message code="ar.viewArAdjustRest.title.total1"/></td>
				<td>${arAdjustRestInfo.JAN_TOTAL}</td>
			</tr>
			<tr>
				<td><!-- 一月已用调休数 --><spring:message code="ar.viewArAdjustRest.title.used1"/></td>
				<td>${arAdjustRestInfo.JAN_USE_TOTAL}</td>
			</tr>
			<tr>
				<td><!-- 二月调休总数 --><spring:message code="ar.viewArAdjustRest.title.total2"/></td>
				<td>${arAdjustRestInfo.FEB_TOTAL}</td>
			</tr>
			<tr>
				<td><!-- 二月已用调休数 --><spring:message code="ar.viewArAdjustRest.title.used2"/></td>
				<td>${arAdjustRestInfo.FEB_USE_TOTAL}</td>
			</tr>
			<tr>
				<td><!-- 三月调休总数 --><spring:message code="ar.viewArAdjustRest.title.total3"/></td>
				<td>${arAdjustRestInfo.MAR_TOTAL}</td>
			</tr>
			<tr>
				<td><!-- 三月已用调休数 --><spring:message code="ar.viewArAdjustRest.title.used3"/></td>
				<td>${arAdjustRestInfo.MAR_USE_TOTAL}</td>
			</tr>
			<tr>
				<td><!-- 四月调休总数 --><spring:message code="ar.viewArAdjustRest.title.total4"/></td>
				<td>${arAdjustRestInfo.APR_TOTAL}</td>
			</tr>
			<tr>
				<td><!-- 四月已用调休数 --><spring:message code="ar.viewArAdjustRest.title.used4"/></td>
				<td>${arAdjustRestInfo.APR_USE_TOTAL}</td>
			</tr>
			<tr>
				<td><!-- 五月调休总数 --><spring:message code="ar.viewArAdjustRest.title.total5"/></td>
				<td>${arAdjustRestInfo.MAY_TOTAL}</td>
			</tr>
			<tr>
				<td><!-- 五月已用调休数 --><spring:message code="ar.viewArAdjustRest.title.used5"/></td>
				<td>${arAdjustRestInfo.MAY_USE_TOTAL}</td>
			</tr>
			<tr>
				<td><!-- 六月调休总数 --><spring:message code="ar.viewArAdjustRest.title.total6"/></td>
				<td>${arAdjustRestInfo.JUN_TOTAL}</td>
			</tr>
			<tr>
				<td><!-- 六月已用调休数 --><spring:message code="ar.viewArAdjustRest.title.used6"/></td>
				<td>${arAdjustRestInfo.JUN_USE_TOTAL}</td>
			</tr>
			<tr>
				<td><!-- 七月调休总数 --><spring:message code="ar.viewArAdjustRest.title.total7"/></td>
				<td>${arAdjustRestInfo.JUL_TOTAL}</td>
			</tr>
			<tr>
				<td><!-- 七月已用调休数 --><spring:message code="ar.viewArAdjustRest.title.used7"/></td>
				<td>${arAdjustRestInfo.JUL_USE_TOTAL}</td>
			</tr>
			<tr>
				<td><!-- 八月调休总数 --><spring:message code="ar.viewArAdjustRest.title.total8"/></td>
				<td>${arAdjustRestInfo.AUG_TOTAL}</td>
			</tr>
			<tr>
				<td><!-- 八月已用调休数 --><spring:message code="ar.viewArAdjustRest.title.used8"/></td>
				<td>${arAdjustRestInfo.AUG_USE_TOTAL}</td>
			</tr>
			<tr>
				<td><!-- 九月调休总数 --><spring:message code="ar.viewArAdjustRest.title.total9"/></td>
				<td>${arAdjustRestInfo.SEP_TOTAL}</td>
			</tr>
			<tr>
				<td><!-- 九月已用调休数 --><spring:message code="ar.viewArAdjustRest.title.used9"/></td>
				<td>${arAdjustRestInfo.SEP_USE_TOTAL}</td>
			</tr>
			<tr>
				<td><!-- 十月调休总数 --><spring:message code="ar.viewArAdjustRest.title.total10"/></td>
				<td>${arAdjustRestInfo.OCT_TOTAL}</td>
			</tr>
			<tr>
				<td><!-- 十月已用调休数 --><spring:message code="ar.viewArAdjustRest.title.used10"/></td>
				<td>${arAdjustRestInfo.OCT_USE_TOTAL}</td>
			</tr>
			<tr>
				<td><!-- 十一月调休总数 --><spring:message code="ar.viewArAdjustRest.title.total11"/></td>
				<td>${arAdjustRestInfo.NOV_TOTAL}</td>
			</tr>
			<tr>
				<td><!-- 十一月已用调休数 --><spring:message code="ar.viewArAdjustRest.title.used11"/></td>
				<td>${arAdjustRestInfo.NOV_USE_TOTAL}</td>
			</tr>
			<tr>
				<td><!-- 十二月调休总数 --><spring:message code="ar.viewArAdjustRest.title.total12"/></td>
				<td>${arAdjustRestInfo.DEC_TOTAL}</td>
			</tr>
			<tr>
				<td><!-- 十二月已用调休数 --><spring:message code="ar.viewArAdjustRest.title.used12"/></td>
				<td>${arAdjustRestInfo.DEC_USE_TOTAL}</td>
			</tr>
		</table>
	</div>
</div>
