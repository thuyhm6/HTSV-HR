<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@page import="com.ait.sys.service.ToolMenuSer"%>
<%@page import="com.ait.sys.service.impl.ToolMenuSerImpl"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<style type="text/css">
.all{
   padding-left: 20px;
   padding-top: 40px;
}
.three{
	float:left;
	font-size:14px;
	color:#f00;
	width:730px;
	height:200px;
	
}
.four{
	float:left;
	font-size:14px;
	color:#f00;
	width:240px;
	height:400px;
	margin-left: 30px;
}
</style>
<script type="text/javascript">

</script>
<%-- <a class="buttonActive" onclick="openOnRight('/hrm/empinfo/viewSingleHrEmergencyAddress?pageNum=1&menuNo=2540&navTabId=hr0101&PERSON_ID=${personid}&EMERGENCY_NO=0','viewResumeList_viewEmergencyAddressunit');" href="#">
					<span>保存形象</span>
				</a>
				<a class="buttonActive" href="#" onclick="chaxun()"><span>查询</span></a>
				<a class="buttonActive" onclick="validateDeleteResumeInfoCallback('viewEmployeePhoto',navTabAjaxDone)" href="#"><span>删除</span></a>
				<a class="buttonActive" onclick="validateAddResumeInfoCallback('editHrEmergencyAddress',navTabAjaxDone)" href="#"><span>保存</span></a>
				<a class="buttonActive" href="/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=8&PERSON_ID=${PERSON_ID }"><span>登记资料</span></a>
 --%>
<div class="three">

<table id="photo_cc" class="user_table" width="100%" border="1" cellpadding="2" cellspacing="1">
<tr><td><span style="color:#000">Total:</span></td><td></td><td></td><td></td><td></td></tr>
<tr>
<td width="5%" class="td_title" ><input type="checkbox" name="allCheck1" id="allCheck1">&nbsp&nbsp&nbspNO.</td>
<td width="5%" class="td_title" ><spring:message code="hrm.empinfo.name" /><!-- 姓名 --></td>
<td width="5%" class="td_title" ><spring:message code="hrm.empinfo.empid" /><!-- 社号 --></td>
<td width="5%" class="td_title" ><spring:message code="hrm.empinfo.ORG_NAME_LOCAL" /><!-- 部门 --></td>
<td width="5%" class="td_title" ><spring:message code="hrm.contract.Rank" /><!-- 职级 --></td>
</tr>
<%-- 
<c:forEach items="${viewEmployeePhoto }" var="ep">
<tr>
<td width="5%" class="td_type">${i.count }</td>
<td width="5%" class="td_type">${ep.LOCAL_NAME}</td>
<td width="5%" class="td_type">${ep.EMPID}</td>
<td width="5%" class="td_type">${ep.ORG_NAME_LOCAL}</td>
<td width="5%" class="td_type">${ep.POST_GRADE_NO_NAME}</td>
</tr>
</c:forEach>
</table> --%>

</div>

<!-- <div  class="four"  >
<table class="user_table" width="100%" border="1" cellpadding="2" cellspacing="1">
<tr>
<td class="td_title" width="5%"><img id='photoImage' name='photoImage' src='' 
				border=1 style='width:220px;height:276px;' ></td>

</tr>
<tr>
</tr>
</table>
</div> -->
