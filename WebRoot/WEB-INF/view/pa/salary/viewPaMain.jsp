<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<div class="pageContent">
	<div
		style="height: 882px; line-height: 529px; overflow: auto; overflow-x: hidden; background: url('/resources/images/pa_main.jpg') no-repeat;">
		<div
			style="width: 170px; padding-left: 555px; margin-top: 136px; height: 120px; overflow: auto; overflow-x: hidden; float: left;">
			<c:if test="${LoginUser.username ne 'HQ' && LoginUser.isBaoXianUser ne 1 }">
			<div style="padding: 3px; cursor: pointer;">
				<a style="text-decoration: none;"
					onclick="navTabNum('/pa/workManagement/viewPaPaySchedule','pageNum=1&menuNo=14013759&navTabId=pa0811','pa0811','<spring:message code="ess.empInfo.pay_plan" />');">*<!-- 计划管理 -->
					<!--工资支付计划--><spring:message code="ess.empInfo.pay_plan" /></a>
			</div>
			<!-- <div style="padding: 3px; cursor: pointer;">
				<a style="text-decoration: none;"
					onclick="navTabNum('/pa/workManagement/viewPayObjStd','pageNum=1&menuNo=14013860&navTabId=pa0823','pa0823','<spring:message code="pa.viewPaMain.DUIXIANGJIZHUN.C" />');">*
					对象选定基准</a>
			</div> -->
			<div style="padding: 3px; cursor: pointer;">
				<a style="text-decoration: none;"
					onclick="navTabNum('/pa/workManagement/viewPaPayObj','pageNum=1&menuNo=14013760&navTabId=pa0812','pa0812','<spring:message code="pa.viewPaMain.DUIXIANGTIAOZHENG.C" />');">*<!-- 对象调整 -->
					<!--对象调整--><spring:message code="pa.viewPaMain.DUIXIANGTIAOZHENG.C" /></a>
			</div>
			<c:if test="${LoginUser.cpnyId eq 'HTSV'}">
			<div style="padding: 3px; cursor: pointer;">
				<a style="text-decoration: none;"
					onclick="navTabNum('/pa/workManagement/viewPaWorkerSalaryTable','pageNum=1&menuNo=90000425&navTabId=pa0825','pa0825','<spring:message code="pa.viewPaMain.PaWorkerSalaryTable.C" />');">*<!-- Worker Salary Table -->
					<!--Worker Salary Table--><spring:message code="pa.viewPaMain.PaWorkerSalaryTable.C" /></a>
			</div>
			</c:if>
		</c:if>
		</div>
		<div
			style="width: 170px; margin-left: -80px; margin-top: 330px; height: 120px; overflow: auto; overflow-x: hidden; float: left;">
			<c:if test="${LoginUser.username ne 'HQ'&& LoginUser.isBaoXianUser ne 1 }">
			<div style="padding: 3px; cursor: pointer;">
				<a style="text-decoration: none;"
					onclick="navTabNum('/pa/workManagement/viewPaWorkFlow','pageNum=1&menuNo=14013761&navTabId=pa0813','pa0813','<spring:message code="pa.viewPaMain.JISUANLIUCHENG.C" />');">*<!-- 计算流程 -->
					<!--工资工作流程--><spring:message code="pa.viewPaMain.GONGZIGONGZUOLIUCHENG.C" /></a>
			</div>
			</c:if>
			<div style="padding: 3px; cursor: pointer;">
				<a style="text-decoration: none;"
					onclick="navTabNum('/pa/workManagement/viewResultConfirmList','pageNum=1&menuNo=14013776&navTabId=pa1019','pa1019','<spring:message code="pa.viewPaResult.JIEGUOQUEREN.b" />');">*<!-- 结果确认 -->
					<!--计算结果确认--><spring:message code="pa.viewPaMain.JISUANJIEGUOQUEREN.C" /></a>
			</div>
			<div style="padding: 3px; cursor: pointer;">
				<a style="text-decoration: none;"
					onclick="navTabNum('/pa/workManagement/detailmonthCountInfoLeft','pageNum=1&menuNo=14013778&navTabId=pa0132','pa0132','<spring:message code="pa.viewPaMain.YUEGONGZIMINGXI.C" />');">*<!--月工资明细-->
					<!--月工资明细--><spring:message code="pa.viewPaMain.YUEGONGZIMINGXI.C" /></a>
			</div>
			<div style="padding: 3px; cursor: pointer;">
				<a style="text-decoration: none;"
					onclick="navTabNum('/pa/workManagement/detailYearCountInfoLeft','pageNum=1&menuNo=14013779&navTabId=pa0133','pa0133','<spring:message code="pa.viewPaMain.NIANGONGZIMINGXI.C" />');">*<!--年工资明细-->
					<!--年工资明细--><spring:message code="pa.viewPaMain.NIANGONGZIMINGXI.C" /></a>
			</div>
			<div style="padding: 3px; cursor: pointer;">
				<a style="text-decoration: none;"
					onclick="navTabNum('/report/ar/viewArReportsList','pageNum=1&menuNo=14013782&navTabId=pa0136','pa0136','<spring:message code="pa.viewPaMain.GONGZIBAOBIAO.C" />');">*<!--工资报表-->
					<!--工资报表--><spring:message code="pa.viewPaMain.GONGZIBAOBIAO.C" /></a>
			</div>
			<div style="padding: 3px; cursor: pointer;">
				<a style="text-decoration: none;"
					onclick="navTabNum('/pa/workManagement/detailPersonCountInfo','pageNum=1&menuNo=14013771&navTabId=pa1014','pa1014','<spring:message code="pa.viewPaMain.GONGZIXIANGXIMINGXI.C" />');">*<!--工资详细明细-->
					<!--工资详细明细--><spring:message code="pa.viewPaMain.GONGZIXIANGXIMINGXI.C" /></a>
			</div>
		</div>
		<div
			style="width: 170px; margin-left: -522px; margin-top: 135px; height: 120px; overflow: auto; overflow-x: hidden; float: left;">
			<c:if test="${LoginUser.username ne 'HQ' && LoginUser.isBaoXianUser ne 1 }">
			<div style="padding: 3px; cursor: pointer;">
				<a style="text-decoration: none;"
					onclick="navTabNum('/pa/workManagement/viewPaEmpAccount','pageNum=1&menuNo=2561&navTabId=pa0818&firstView=Y','pa0818','<spring:message code="hr.viewCondSql.title.ZHANGHUXINXI" />');">*<!--账户信息-->
					<!--账户信息管理--><spring:message code="pa.viewPaMain.ZHANGHUXINXIGUANLI.C" /></a>
			</div>
			<c:if test="${LoginUser.cpnyName == 'TSTO' }">
				<div style="padding:3px;cursor: pointer;">
					<a style="text-decoration:none ;"  onclick="navTabNum('/pa/salary/viewPaInputItemData?itemType=10','pageNum=1&menuNo=14014447&navTabId=pa0434-2','pa0434-2','<spring:message code="pa.viewPaMain.SHOUGONGSHANGCHUANTONGJIXIANGMU.C" />');">* <!--手工上传统计项目--><spring:message code="pa.viewPaMain.SHOUGONGSHANGCHUANTONGJIXIANGMU.C" /></a>
				</div>
			</c:if>
			<div style="padding:3px;cursor: pointer;">
				<a style="text-decoration:none ;"  onclick="navTabNum('/pa/workManagement/viewPaParamDownloud','pageNum=1&menuNo=14014446&navTabId=pa0824','pa0824','<spring:message code="pa.viewPaMain.GONGZISHUJUPILIANGDAORU.C" />');">* <!--工资数据批量导入--><spring:message code="pa.viewPaMain.GONGZISHUJUPILIANGDAORU.C" /></a>
			</div>
			<c:if test="${LoginUser.cpnyId ne 'HTSV' and LoginUser.cpnyId ne 'HAE'}">
				<div style="padding: 3px; cursor: pointer;">
					<a style="text-decoration:none ;" onclick="navTabNum('/pa/salaryCanShu/viewHaoFengSetList','pageNum=1&amp;menuNo=218365&amp;navTabId=pa0804','pa0804','<spring:message code="pa.viewPaMain.HAOFENGSHEZHI.C" />');"><font color="#090909">* <!--号俸设置--><spring:message code="pa.viewPaMain.HAOFENGSHEZHI.C" /></font></a>
				</div>
			</c:if>
		</c:if>
		</div>
		<div
			style="width: 170px; margin-left: -522px; margin-top: 290px; height: 120px; overflow: auto; overflow-x: hidden; float: left;">
			<c:if test="${LoginUser.username ne 'HQ'&& LoginUser.isBaoXianUser ne 1 }">
			<div style="padding: 3px; cursor: pointer;">
				<a style="text-decoration: none;"
					onclick="navTabNum('/pa/salary/viewPaInputItemData?itemType=1','pageNum=1&menuNo=14013763&navTabId=pa0511','pa0511','<spring:message code="pa.viewPaParamDownloud.BIAOZHUNXIANGMU.C" />');">*<!--标准项目 -->
					<!--标准项目--><spring:message code="pa.viewPaMain.BIAOZHUNXIANGMU.C" /></a>
			</div>
			<div style="padding: 3px; cursor: pointer;">
				<a style="text-decoration: none;"
					onclick="navTabNum('/pa/salary/viewPaInputItemData?itemType=2','pageNum=1&menuNo=14013764&navTabId=pa0512','pa0512','<spring:message code="pa.viewPaMain.ZHIFUTIAOZHENGXIANGMU.C" />');">*<!--支付调整项目  -->
					<!--支付调整项目--><spring:message code="pa.viewPaMain.ZHIFUTIAOZHENGXIANGMU.C" /></a>
			</div>
			<div style="padding: 3px; cursor: pointer;">
				<a style="text-decoration: none;"
					onclick="navTabNum('/pa/salary/viewPaInputItemData?itemType=3','pageNum=1&menuNo=14013765&navTabId=pa0513','pa0513','<spring:message code="pa.viewSalaryCodeList.ZHIFULIWAIXIANGMU.b" />');">*<!--支付例外项目 -->
					<!--支付例外项目--><spring:message code="pa.viewPaMain.ZHIFULIWAIXIANGMU.C" /></a>
			</div>
		</c:if>
		</div>
		<div
			style="width: 170px; margin-left: -522px; margin-top: 470px; height: 120px; overflow: auto; overflow-x: hidden; float: left;">
			<c:if test="${LoginUser.username ne 'HQ' && LoginUser.isBaoXianUser ne 1}">
			<div style="padding: 3px; cursor: pointer;">
				<a style="text-decoration: none;"
					onclick="navTabNum('/pa/salary/viewPaInputItemData?itemType=4','pageNum=1&menuNo=2568&navTabId=pa0231','pa0231','<spring:message code="pa.viewPaMain.KOUCHUTIAOZHENGXIANGMU.C" />');">*<!--扣除调整项目 -->
					<!--扣除调整项目--><spring:message code="pa.viewPaMain.KOUCHUTIAOZHENGXIANGMU.C" /></a>
			</div>
			<div style="padding: 3px; cursor: pointer;">
				<a style="text-decoration: none;"
					onclick="navTabNum('/pa/salary/viewPaInputItemData?itemType=5','pageNum=1&menuNo=14013766&navTabId=pa0232','pa0232','<spring:message code="pa.viewSalaryCodeList.KOUCHULIWAIXIANGMU.b" />');">*<!--扣除例外项目  -->
					<!--扣除例外项目--><spring:message code="pa.viewPaMain.KOUCHULIWAIXIANGMU.C" /></a>
			</div>
			</c:if>
		</div>
		<div
			style="width: 170px; margin-left: -52px; margin-top: 135px; height: 120px; overflow: auto; overflow-x: hidden; float: left;">
			<c:if test="${LoginUser.username ne 'HQ' }">
			<div style="padding: 3px; cursor: pointer;">
				<a style="text-decoration: none;"
					onclick="navTabNum('/pa/salary/viewPaInputItemData?itemType=6','pageNum=1&menuNo=14014420&navTabId=pa0431','pa0431','<spring:message code="pa.viewPaMain.BAOXIANBIAOZHUNXIANGMU.C" />');">*<!--保险标准项目  -->
					<!--保险标准项目--><spring:message code="pa.viewPaMain.BAOXIANBIAOZHUNXIANGMU.C" /></a>
			</div>
			<div style="padding: 3px; cursor: pointer;">
				<a style="text-decoration: none;"
					onclick="navTabNum('/pa/salary/viewPaInputItemData?itemType=7','pageNum=1&menuNo=14014421&navTabId=pa0432','pa0432','<spring:message code="pa.viewPaMain.BAOXIANBUJIAOXIANGMU.C" />');">*<!-- 保险补缴项目 -->
					<!--保险补缴项目--><spring:message code="pa.viewPaMain.BAOXIANBUJIAOXIANGMU.C" /></a>
			</div>
			<div style="padding: 3px; cursor: pointer;">
				<a style="text-decoration: none;"
					onclick="navTabNum('/pa/salary/viewPaInputItemData?itemType=8','pageNum=1&menuNo=14014422&navTabId=pa0433','pa0433','<spring:message code="pa.viewPaMain.BAOXIANBUKOUXIANGMU.C" />');">*<!-- 保险补扣项目 -->
					<!--保险补扣项目--><spring:message code="pa.viewPaMain.BAOXIANBUKOUXIANGMU.C" /></a>
			</div>
			<div style="padding: 3px; cursor: pointer;">
				<a style="text-decoration: none;"
					onclick="navTabNum('/pa/salary/viewPaInputItemData?itemType=9','pageNum=1&menuNo=14014447&navTabId=pa0434','pa0434','<spring:message code="pa.viewPaMain.BAOXIANLIWAIXIANGMU.C" />');">*<!--保险例外项目  -->
					<!--保险例外项目--><spring:message code="pa.viewPaMain.BAOXIANLIWAIXIANGMU.C" /></a>
			</div>
		</c:if>
		</div>
		<div
			style="width: 170px; margin-left: -880px; margin-top: 420px; height: 120px; overflow: auto; overflow-x: hidden; float: left;">
			<c:if test="${LoginUser.username ne 'HQ'}">
			<div style="padding: 3px; cursor: pointer;">
				<a style="text-decoration: none;"
					onclick="navTabNum('/pa/workManagement/viewPaArSummaryForManageList','pageNum=1&menuNo=14015404&navTabId=pa1301','pa1301','<spring:message code="pa.viewPaMain.KAOQINHUIZONGGUANLI.C" />');">*<!-- 考勤汇总管理 -->
					<!--考勤汇总管理--><spring:message code="pa.viewPaMain.KAOQINHUIZONGGUANLI.C" /></a>
			</div>
			</c:if>
			<div style="padding: 3px; cursor: pointer;">
				<a style="text-decoration: none;"
					onclick="navTabNum('/pa/workManagement/viewPaArSummarySearchList','pageNum=1&menuNo=14015430&navTabId=pa1302','pa1302','<spring:message code="ar.viewSummaryFormula.title.kaoqinghuizongliebiao" />');">*
					<!-- 考勤汇总搜索  --><spring:message code="ar.viewSummaryFormula.title.kaoqinghuizongliebiao" /></a>
			</div> 
		</div>
		<div
			style="width: 170px; margin-left: -270px; margin-top: 510px; height: 120px; overflow: auto; overflow-x: hidden; float: left;">
			<div style="padding: 3px; cursor: pointer;">
				<a style="text-decoration: none;"
					onclick="navTabNum('/pa/workManagement/payStub','pageNum=1&menuNo=14013777&navTabId=pa0131','pa0131','<spring:message code="pa.viewPaMain.GONGZITIAO.C" />');">*<!-- 工资条 -->
					<!--工资条--><spring:message code="pa.viewPaMain.GONGZITIAO.C" /></a>
			</div>
			<div style="padding: 3px; cursor: pointer;">
				<a style="text-decoration: none;"
					onclick="navTabNum('/pa/workManagement/viewPaResultList','pageNum=1&menuNo=14013780&navTabId=pa0134','pa0134','<spring:message code="pa.viewPaMain.ZHIFUHEJIGEREN.C" />');">*<!-- 支付合计(个人) -->
					<!--支付合计(个人)--><spring:message code="pa.viewPaMain.ZHIFUHEJIGEREN.C" /></a>
			</div>
			<div style="padding: 3px; cursor: pointer;">
				<a style="text-decoration: none;"
					onclick="navTabNum('/pa/workManagement/viewDeptPaResultList','pageNum=1&menuNo=14013781&navTabId=pa0135','pa0135','<spring:message code="pa.viewPaMain.ZHIFUHEJIBUMEN.C" />');">*<!-- 支付合计（部门） -->
					<!--支付合计(部门)--><spring:message code="pa.viewPaMain.ZHIFUHEJIBUMEN.C" /></a>
			</div>
		</div>
		<div
			style="width: 170px; margin-left: -880px; margin-top: 140px; height: 120px; overflow: auto; overflow-x: hidden; float: left;">
			<div style="padding: 3px; cursor: pointer;">
				<a style="text-decoration: none;"
					onclick="navTabNum('/hrm/empinfo/viewPersonalInfo','pageNum=1&menuNo=125244&navTabId=hr2100','hr2100','<spring:message code="hrm.empinfo.COOMPREHENSIVE_INTRODUCTION.Z" />');">*<!-- 综合简介 -->
					<!--综合简介--><spring:message code="hrm.empinfo.COOMPREHENSIVE_INTRODUCTION.Z" /></a>
			</div>
			<div style="padding: 3px; cursor: pointer;">
				<a style="text-decoration: none;"
					onclick="navTabNum('/hrm/empinfo/viewStartPoint','pageNum=1&menuNo=14013651&navTabId=hr0204','hr0204','<spring:message code="hrm.empinfo.The_person" />');">*<!-- 个人发令 -->
					<!--个人发令--><spring:message code="hrm.empinfo.The_person" /></a>
			</div>
		</div>
	</div>
</div>