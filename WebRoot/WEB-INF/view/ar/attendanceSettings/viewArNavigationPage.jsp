<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageContent" >
	<div style="height:882px;line-height:700px;background:url('/resources/images/ar_main.jpg') no-repeat;">
		<div style="width:170px;margin-left:265px;margin-top:160px;height:120px;float:left;">
			<c:if test="${isSuperUser == 1 or isSuperHrUser == 1 or isArUser == 1}">
			<div style="padding:3px;"><a href="#" style="text-decoration:none ;"onclick="navTabNum('/ar/attendanceSettings/viewClassCalendar','pageNum=1&menuNo=216695&navTabId=ar0603','ar0603','<spring:message code="ar.viewArNavigationPage.BANZURILI.b" />');">* <spring:message code="ar.viewArNavigationPage.BANZURILI.b" /><!--班组日历 --></a></div>
			<div style="padding:3px;"><a href="#" style="text-decoration:none ;" onclick="navTabNum('/ess/deptEmpAtt/viewArShiftGroupList','pageNum=1&menuNo=14013735&navTabId=ar0605','ar0605','<spring:message code="ar.viewArNavigationPage.GERENBANZUJINGLIGUANLI.b" />');">* <spring:message code="ar.viewArNavigationPage.GERENBANZUJINGLIGUANLI.b" /><!--个人班组经历管理--></a></div>
			<div style="padding:3px;"><a href="#" style="text-decoration:none ;" onclick="navTabNum('/ar/arShiftGroupManagement/viewArShiftMonthCheckList','pageNum=1&menuNo=14013738&navTabId=ar0608','ar0608','<spring:message code="ar.viewArNavigationPage.BANZUYUEBIELIEBIAOCHAXUN.b" />');">* <spring:message code="ar.viewArNavigationPage.BANZUYUEBIELIEBIAOCHAXUN.b" /><!--班组月别列表查询--></a></div>
			</c:if>
		</div>
		<div style="width:170px;padding-left: 70px;margin-top:160px;height:60px;float:left;">
			<c:if test="${isSuperUser == 1 or isSuperHrUser == 1 or isArUser == 1}">
			<div style="padding:3px;"><a href="#" style="text-decoration:none ;" onclick="navTabNum('/ar/attendanceMintenance/addEmpShiftView','pageNum=1&menuNo=2359&navTabId=ar0602','ar0602','<spring:message code="ar.viewArNavigationPage.YUANGONGPAIBAN.b" />');">* <spring:message code="ar.viewArNavigationPage.YUANGONGPAIBAN.b" /><!--员工排班--></a></div>
			<div style="padding:3px;"><a href="#" style="text-decoration:none ;" onclick="navTabNum('/ar/attendanceMintenance/viewEmpCalendar','pageNum=1&menuNo=2361&navTabId=ar0604','ar0604','<spring:message code="ar.viewArNavigationPage.GERENRILI.b" />');">* <spring:message code="ar.viewArNavigationPage.GERENRILI.b" /><!--个人日历--></a></div>
			</c:if>
		</div>
		<div style="width:190px;margin-left:105px;margin-top:140px;height:120px;float:left;">
		<c:if test="${isSuperUser == 1 or isSuperHrUser == 1 or isArUser == 1}">
			<div style="padding:3px;margin-top: 10px;"><a href="#" style="text-decoration:none ;" onclick="navTabNum('/ar/attendanceSettings/viewCompanyCalendar','pageNum=1&menuNo=2352&navTabId=ar0102','ar0102','<spring:message code="ar.viewArNavigationPage.GONGSIRILI.b" />');">* <spring:message code="ar.viewArNavigationPage.GONGSIRILI.b" /><!--公司日历--></a></div>
			<div style="padding:3px;"><a href="#" style="text-decoration:none ;"onclick="navTabNum('/ar/attendanceSettings/viewShift','pageNum=1&menuNo=2351&navTabId=ar0303','ar0303','<spring:message code="ar.viewArNavigationPage.BANCISHEZHI.b" />');">* <spring:message code="ar.viewArNavigationPage.BANCISHEZHI.b" /><!--班次设置--></a></div>
			<!--<div style="padding:3px;"><a href="#" style="text-decoration:none ;"onclick="navTabNum('/ar/attendanceSettings/viewStatutoryHolidays','pageNum=1&menuNo=14013732&navTabId=ar0130','ar0130','<spring:message code="ar.viewArNavigationPage.FADINGJIEJIARISHEZHI.b" />');">* <spring:message code="ar.viewArNavigationPage.FADINGJIEJIARISHEZHI.b" />法定节假日设置</a></div>-->
			<div style="padding:3px;"><a href="#" style="text-decoration:none ;"onclick="navTabNum('/ar/attendanceSettings/viewAttendanceKeeper','pageNum=1&menuNo=2355&navTabId=ar0202','ar0202','<spring:message code="ar.viewArNavigationPage.KAOQINDANDANGSHEZHI.b" />');">* <spring:message code="ar.viewArNavigationPage.KAOQINDANDANGSHEZHI.b" /><!--考勤担当设置--></a></div>
			</c:if>
		</div>
	 
		<div style="width:170px;margin-left:-705px;margin-top:311px;height:120px;float:left;">
		<c:if test="${isSuperUser == 1 or isSuperHrUser == 1 or isArUser == 1}">
		<c:if test="${LoginUser.cpnyId eq 'HTSV' }">
			<div style="padding:2px;margin-top: 10px;"><a href="#" style="text-decoration:none ;"onclick="navTabNum('/ar/attendanceMintenance/viewApplyAttenanceManagentInfoList_new?firstPage=1','pageNum=1&menuNo=14013739&navTabId=ar0230&deleteYN=Y&CANCEL_FLAG=1&CONFIRM_FLAG=1101','ar0230','<spring:message code="ar.viewArNavigationPage.KAOQINGUANLI.b" />');">* <spring:message code="ar.viewArNavigationPage.KAOQINGUANLI.b" /><!--考勤管理--></a></div>
		</c:if>
		<c:if test="${LoginUser.cpnyId eq 'HAE' }">
			<div style="padding:2px;margin-top: 10px;"><a href="#" style="text-decoration:none ;"onclick="navTabNum('/ar/attendanceMintenance/viewApplyAttManagentByAnyApproverList','pageNum=1&menuNo=90000428&navTabId=ar0234&deleteYN=Y','ar0234','<spring:message code="ar.viewArNavigationPage.KAOQINGUANLI.b" />');">* <spring:message code="ar.viewArNavigationPage.KAOQINGUANLI.b" /><!--考勤管理--></a></div>
		</c:if>
			<div style="padding:2px;"><a href="#" style="text-decoration:none ;"onclick="navTabNum('/ar/attendanceMintenance/viewAttendanceManagentForSerchInfoList','pageNum=1&menuNo=14013740&navTabId=ar0231','ar0231','<spring:message code="ar.viewAttendanceManagentForSerchInfo.KAOQINCHAXUN.b" />');">* <spring:message code="ar.viewAttendanceManagentForSerchInfo.KAOQINCHAXUN.b" /><!--考勤搜索--></a></div>
			<div style="padding:2px;"><a href="#" style="text-decoration:none ;"onclick="navTabNum('/ar/attendanceMintenance/viewArCardRecord?firstFlag=1','pageNum=1&menuNo=2386&navTabId=ar0801','ar0801','<spring:message code="ar.viewArNavigationPage.CHURUJILUGUANLI.b" />');">* <spring:message code="ar.viewArNavigationPage.CHURUJILUGUANLI.b" /><!--出入记录维护--></a></div>
			<div style="padding:3px;"><a href="#" style="text-decoration:none ;"onclick="navTabNum('/ar/attendanceSettings/viewVacEmpList?firstFlag=1&EMP_OFFICE=15119','pageNum=1&menuNo=14013741&navTabId=ar0232','ar0232','<spring:message code="ar.viewArNavigationPage.NIANJIASHIYONGGUANLI.b" />');">* <spring:message code="ar.viewArNavigationPage.NIANJIASHIYONGGUANLI.b" /><!--年假使用管理--></a></div>
		</c:if>
		</div>  
		<div style="width:170px;margin-left:-460px;margin-top:311px;height:120px;float:left;">
		<c:if test="${isSuperUser == 1 or isSuperHrUser == 1 or isArUser == 1}">
		<c:if test="${LoginUser.cpnyId eq 'HTSV' }">
			<div style="padding:2px;margin-top: 10px;"><a href="#" style="text-decoration:none ;"onclick="navTabNum('/ar/attendanceMintenance/viewArOvertimeManagent_fast?deleteYN=Y&CANCEL_FLAG=1&firstPage=1&CONFIRM_FLAG=1101','pageNum=1&menuNo=14013744&navTabId=ar0701','ar0701','<spring:message code="ar.viewArNavigationPage.JIABANGUANLI.b" />');">* <spring:message code="ar.viewArNavigationPage.JIABANGUANLI.b" /><!--加班管理--></a></div>
		</c:if>
		<c:if test="${LoginUser.cpnyId eq 'HAE' }">
			<div style="padding:2px;margin-top: 10px;"><a href="#" style="text-decoration:none ;"onclick="navTabNum('/ar/attendanceMintenance/viewApplyOtManagentByAnyApproverList?deleteYN=Y','pageNum=1&menuNo=90000429&navTabId=ar0708','ar0708','<spring:message code="ar.viewArNavigationPage.JIABANGUANLI.b" />');">* <spring:message code="ar.viewArNavigationPage.JIABANGUANLI.b" /><!--加班管理--></a></div>
		</c:if>
			<div style="padding:3px;"><a href="#" style="text-decoration:none ;" onclick="navTabNum('/ar/attendanceMintenance/viewSearchApplyOtInfoList','pageNum=1&menuNo=14013745&navTabId=ar0702','ar0702','<spring:message code="ar.viewSearchApplyOtInfoList.JIABANSOUSUO.b" />');">* <spring:message code="ar.viewSearchApplyOtInfoList.JIABANSOUSUO.b" /><!--加班搜索--></a></div>
			<div style="padding:3px;"><a href="#" style="text-decoration:none ;" onclick="navTabNum('/ar/attendanceMintenance/viewOverTimeLimit?OT_LIMIT_PARAM=1','pageNum=1&menuNo=14013745&navTabId=ar0702','ar0702','<spring:message code="ar.viewAdjustLeaveTSTOBatchList.JIABANSHANGXIAN.b" />');">* <spring:message code="ar.viewAdjustLeaveTSTOBatchList.JIABANSHANGXIAN.b" /><!--加班上限--></a></div>
			<div style="padding:3px;"><a href="#" style="text-decoration:none ;" onclick="navTabNum('/ar/attendanceMintenance/viewArCardRecordDay?firstFlag=1','pageNum=1&amp;menuNo=14013647&amp;navTabId=ar1234','ar1234','<spring:message code="ar.viewArNavigationPage.CHURUSHUJUSOUSUO.b" />');">* <spring:message code="ar.viewArNavigationPage.CHURUSHUJUSOUSUO.b" /><!--出入数据搜索--></a></div>
		</c:if>
		</div>  
		<div style="width:170px;margin-left:-710px;margin-top:490px;height:120px;float:left;">
		<c:if test="${isSuperUser == 1 or isSuperHrUser == 1 or isArUser == 1}">
			<div style="padding:3px;margin-top: 10px;"><a href="#" style="text-decoration:none ;"onclick="navTabNum('/ar/countAttendance/arForDeptCountInfoList','pageNum=1&menuNo=14013748&navTabId=ar0151','ar0151','<spring:message code="ar.viewArNavigationPage.BUMENXIANKUANG.b" />');">* <spring:message code="ar.viewArNavigationPage.BUMENXIANKUANG.b" /><!--部门现况--></a></div>
			<div style="padding:3px;"><a href="#" style="text-decoration:none ;"onclick="navTabNum('/ar/countAttendance/arCountInfoList','pageNum=1&menuNo=14013749&navTabId=ar0152','ar0152','<spring:message code="ar.viewArNavigationPage.GERENXIANZHUANG.b" />');">* <spring:message code="ar.viewArNavigationPage.GERENXIANZHUANG.b" /><!--个人现况--></a></div>
			<div style="padding:3px;"><a href="#" style="text-decoration:none ;"onclick="navTabNum('/ar/countAttendance/arForDateCountInfoList','pageNum=1&menuNo=14013750&navTabId=ar0153','ar0153','<spring:message code="ar.viewArNavigationPage.RIQIXIANKUANG.b" />');">* <spring:message code="ar.viewArNavigationPage.RIQIXIANKUANG.b" /><!--日期现况--></a></div>
			</c:if>
		</div>
		<div style="width:170px;margin-left:-465px;margin-top:500px;height:120px;float:left;">
			<c:if test="${isSuperUser == 1 or isSuperHrUser == 1 or isArUser == 1}">
			<div style="padding:3px;"><a href="#" style="text-decoration:none ;"onclick="navTabNum('/ess/arConfirm/viewAttendanceExConfirm?seach_CONFIRM_FLAG=0','pageNum=1&menuNo=14015692&navTabId=ar0901','ar0901','<spring:message code="ar.viewArNavigationPage.KAOQINYICHANGRENSHIQUEREN.b" />');">* <spring:message code="ar.viewArNavigationPage.KAOQINYICHANGRENSHIQUEREN.b" /><!-- 考勤异常人事确认 --></a></div>
			<div style="padding:3px;"><a href="#" style="text-decoration:none ;"onclick="navTabNum('/ess/arConfirm/viewLeaveConfirmList?seach_CONFIRM_FLAG=0','pageNum=1&menuNo=14015695&navTabId=ar0903','ar0903','<spring:message code="ar.viewArNavigationPage.XIUJIARENSHIQUEREN.b" />');">* <spring:message code="ar.viewArNavigationPage.XIUJIARENSHIQUEREN.b" /><!--休假人事确认--></a></div>
			<div style="padding:3px;"><a href="#" style="text-decoration:none ;"onclick="navTabNum('/ess/arConfirm/viewSickLeaveProofConfirmList?seach_PROOF_YN=0','pageNum=1&menuNo=90000352&navTabId=ar0909','ar0909','<spring:message code="sys.main.sickLeaveProofConfirm.b" />');">* <spring:message code="sys.main.sickLeaveProofConfirm.b" /><!--病假证明提交确认--></a></div>
			<!--<div style="padding:2px;"><a href="#" style="text-decoration:none ;"onclick="navTabNum('/ess/arConfirm/viewPOtApplyInfoConfirmList?seach_CONFIRM_FLAG=0','pageNum=1&menuNo=14015693&navTabId=ar0902','ar0902','<spring:message code="ar.viewArNavigationPage.JIABANRENSHIQUEREN.b" />');">* <spring:message code="ar.viewArNavigationPage.JIABANRENSHIQUEREN.b" />加班人事确认</a></div>-->
			</c:if>
		</div>
	</div>
</div>
