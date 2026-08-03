<%@ page contentType="text/html; charset=UTF-8" import="java.util.*" pageEncoding="UTF-8" language="java" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/pa/empSummary/viewEmpSummary" method="post" enctype="application/x-www-form-urlencoded" rel="pagerForm">
	<div class="searchBar" >
		<table class="searchContent" width="80%">
			<tr>
				<td>
					<spring:message code="org.orgManage.title.orgHorizView"/><!--组织架构-->：
				</td>
				<td>
			<ait:deptList name="seach_DEPTNO" cpnyId="${defaultCpny}" id="viewEmpInfoList_seachDept"/>
			<ait:deptTreeIcon name="seach_DEPTNO" cpnyId="${defaultCpny}" limit="hr" id="viewEmpInfoList_seachDept" selected="${DEPTNO}"/>
				</td>
				<td><spring:message
			code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME" /><!-- 工号/姓名： --> ：
				</td>
				<td><input
			type="text" name="seach_KEY" value="${KEY}" />
				</td>
			</tr>
			<tr>
				<td>
					<spring:message code="ar.viewarcardrecord.title.shijian"/><!--时间-->：
				</td>
				<td>
					<input type="text" id="seach_TIME" name="seach_TIME" class="date" format="yyyy-MM-dd" readonly="true" value="${TIME}"/>
				    <a class="inputDateButton" href="javascript:;"><spring:message code="public.title.choose"/><!-- 选择 --></a>
				</td>
				<td>
					<spring:message code="inct.salesman.division"/><!--分公司-->：
				</td>
				<td>
			<select id="hr2100_seach_CPNY_ID" name="seach_defaultCpny" onchange="reloadPage();">
				<c:forEach items="${companyList}" var="item" varStatus="i">
					<option value="${item.CPNY_ID }" <c:if test="${defaultCpny eq item.CPNY_ID}">selected</c:if>>${item.CPNY_ID }</option>
				</c:forEach>
			</select>
				</td>
			</tr>
		</table>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">
				<spring:message code="public.title.search"/><!-- 检索 --></button></div></div></li>
			</ul>
		</div>
	</div>
	</form>
</div>
<div class="pageContent">
	<table width="100%">
		<tr>
			<td>
				<div class="panel">
					<h1 style="text-align:center">
						<spring:message code="ess.viewpersonalpainfo.yuangongxinxi"/><!--人员基本信息-->
					</h1>
<table class="user_table" width="100%" border="1" cellspacing="0" cellpadding="0" align="center">
		<thead>
  <tr>
    <th class="td_title" style="text-align:center">公司</th>
    <th class="td_title" style="text-align:center">Belong Org</th>
    <th class="td_title" style="text-align:center">社号</th>
    <th class="td_title" style="text-align:center">姓名</th>
    <th class="td_title" style="text-align:center">职级</th>
    <th class="td_title" style="text-align:center">JOB TP NM</th>
    <th class="td_title" style="text-align:center">JOB POSITION</th>
    <th class="td_title" style="text-align:center">职位</th>
  </tr>
  </thead>
  <tbody><c:forEach items="${empInfo}" var="item">
  <tr>
									<td class="td_type" style="text-align: center">
										${item.CPNY}
										<!-- 分公司 -->
									</td>
									<td class="td_type" style="text-align: center">
										${item.DEPT_NAME}
										<!-- Belong Org -->
									</td>
									<td class="td_type" style="text-align: center">
										${item.EMPID}
										<!--员工工号-->
									</td>
									<td class="td_type" style="text-align: center">
										${item.LOCAL_NAME}
										<!--员工姓名-->
									</td>
									<td class="td_type" style="text-align: center">
										${item.POST_GRADE}
										<!--职级-->
									</td>
									<td class="td_type" style="text-align: center">
										${item.POST}
										<!--JOB TP NM-->

									</td>
									<td class="td_type" style="text-align: center">
										${item.POSITION }
										<!-- JOB POSITION -->
									</td>
									<td class="td_type" style="text-align: center">
										${item.DUTY_NAME}
										<!-- 职位 -->
									</td>
								</tr></c:forEach></tbody>
</table></div></td></tr>
			<tr>
			<td width="100%">
				<div class="panel">
					<h1 style="text-align:center">
						<spring:message code="pa.salary.title.attendanceBasicInfo"/><!--考勤基本信息-->
					</h1>
<table class="user_table" width="100%" border="0" cellspacing="0" cellpadding="0">
		<thead>
  <tr>
    <th class="td_title" style="text-align:center">实际出勤</th>
    <th class="td_title" style="text-align:center">应出勤天数</th>
    <th class="td_title" style="text-align:center">平时加班</th>
    <th class="td_title" style="text-align:center">休息日加班</th>
    <th class="td_title" style="text-align:center">法定节日加班</th>
    <th class="td_title" style="text-align:center">事假</th>
    <th class="td_title" style="text-align:center">病假</th>
    <th class="td_title" style="text-align:center">出差</th>
  </tr>
  </thead><c:forEach items="${paInfo}" var="item">
  <tbody>
  <tr>
    <td class="td_type" style="text-align: center">${item.P1 }&nbsp;</td>
    <td class="td_type" style="text-align: center">${item.P2 }&nbsp;</td>
    <td class="td_type" style="text-align: center">${item.P3 }&nbsp;</td>
    <td class="td_type" style="text-align: center">${item.P4 }&nbsp;</td>
    <td class="td_type" style="text-align: center">${item.P5 }&nbsp;</td>
    <td class="td_type" style="text-align: center">${item.P6 }&nbsp;</td>
    <td class="td_type" style="text-align: center">${item.P7 }&nbsp;</td>
    <td class="td_type" style="text-align: center">${item.P8 }&nbsp;</td>
  </tr></tbody><thead>
  <tr>
    <th class="td_title" style="text-align:center">旷工</th>
    <th class="td_title" style="text-align:center">年/月假</th>
    <th class="td_title" style="text-align:center">其他休假/休业假</th>
    <th class="td_title" style="text-align:center">迟到次数</th>
    <th class="td_title" style="text-align:center">早退次数</th>
    <th class="td_title" style="text-align:center">&nbsp;</th>
    <th class="td_title" style="text-align:center">&nbsp;</th>
    <th class="td_title" style="text-align:center">&nbsp;</th>
  </tr>
  </thead><tbody>
  <tr>
    <td class="td_type" style="text-align: center">${item.P9 }&nbsp;</td>
    <td class="td_type" style="text-align: center">${item.P10 }&nbsp;</td>
    <td class="td_type" style="text-align: center">${item.P11 }&nbsp;</td>
    <td class="td_type" style="text-align: center">${item.P12 }&nbsp;</td>
    <td class="td_type" style="text-align: center">${item.P13 }&nbsp;</td>
    <td class="td_type" style="text-align: center">&nbsp;</td>
    <td class="td_type" style="text-align: center">&nbsp;</td>
    <td class="td_type" style="text-align: center">&nbsp;</td>
  </tr></tbody><thead>
  <tr>
    <th class="td_title" style="text-align:center">平时2小时延时</th>
    <th class="td_title" style="text-align:center">周末超过8小时</th>
    <th class="td_title" style="text-align:center">法定节日超过8小时</th>
    <th class="td_title" style="text-align:center">&nbsp;</th>
    <th class="td_title" style="text-align:center">&nbsp;</th>
    <th class="td_title" style="text-align:center">&nbsp;</th>
    <th class="td_title" style="text-align:center">&nbsp;</th>
    <th class="td_title" style="text-align:center">&nbsp;</th>
  </tr>
  </thead><tbody>
  <tr>
    <td class="td_type" style="text-align: center">${item.P14 }&nbsp;</td>
    <td class="td_type" style="text-align: center">${item.P15 }&nbsp;</td>
    <td class="td_type" style="text-align: center">${item.P16 }&nbsp;</td>
    <td class="td_type" style="text-align: center">&nbsp;</td>
    <td class="td_type" style="text-align: center">&nbsp;</td>
    <td class="td_type" style="text-align: center">&nbsp;</td>
    <td class="td_type" style="text-align: center">&nbsp;</td>
    <td class="td_type" style="text-align: center">&nbsp;</td>
  </tr></tbody></c:forEach>
</table></div></td></tr>
			<tr>
			<td width="100%">
				<div class="panel">
					<h1 style="text-align:center">
						年假信息<!--年假信息-->
					</h1>
<table class="user_table" width="100%" border="0" cellspacing="0" cellpadding="0">
<thead>
  <tr>
    <th class="td_title" style="text-align:center">年</th>
    <th class="td_title" style="text-align:center">社号</th>
    <th class="td_title" style="text-align:center">法定年假</th>
    <th class="td_title" style="text-align:center">福利年假</th>
  </tr>
  </thead>
  <tbody><c:forEach items="${paYearInfo}" var="item">
  <tr>
    <td class="td_type" style="text-align: center">${item.P1 }&nbsp;</td>
    <td class="td_type" style="text-align: center">${item.P2 }&nbsp;</td>
    <td class="td_type" style="text-align: center">${item.P3 }&nbsp;</td>
    <td class="td_type" style="text-align: center">${item.P4 }&nbsp;</td>
  </tr></c:forEach></tbody>
</table></div></td></tr>
			<tr>
			<td width="100%">
				<div class="panel">
					<h1 style="text-align:center">
						<spring:message code="pa.salary.title.salaryItemList"/><!--发款项详细列表-->
					</h1>
<table class="user_table" width="100%" border="0" cellspacing="0" cellpadding="0">
<thead>
  <tr>
    <th colspan="5" align="center" style="text-align:center;">发款项详细列表</th>
  </tr>
  <tr>
    <th class="td_title" style="text-align:center">月工资</th>
    <th class="td_title" style="text-align:center">变动/成果工资</th>
    <th class="td_title" style="text-align:center">交通/午餐补贴</th>
    <th class="td_title" style="text-align:center">其他补贴</th>
    <th class="td_title" style="text-align:center">绩效奖金</th>
  </tr>
  </thead>
  <tbody><c:forEach items="${payDetilInfo}" var="item">
  <tr>
    <td class="td_type" style="text-align: center">${item.P1 }&nbsp;</td>
    <td class="td_type" style="text-align: center">${item.P2 }&nbsp;</td>
    <td class="td_type" style="text-align: center">${item.P3 }&nbsp;</td>
    <td class="td_type" style="text-align: center">${item.P4 }&nbsp;</td>
    <td class="td_type" style="text-align: center">${item.P5 }&nbsp;</td>
  </tr></c:forEach></tbody>
</table></div></td></tr>
			<tr>
			<td width="100%">
				<div class="panel">
					<h1 style="text-align:center">
						其他扣款详细列表<!--其他扣款详情列表-->
					</h1>
<table class="user_table" width="100%" border="0" cellspacing="0" cellpadding="0">
<thead>
  <tr>
    <th class="td_title" style="text-align:center">爱心基金</th>
    <th class="td_title" style="text-align:center">&nbsp;</th>
    </tr>
  </thead>
  <tbody><c:forEach items="${otherPayInfo}" var="item">
  <tr>
    <td class="td_type" style="text-align: center">${item.P1 }&nbsp;</td>
    <td class="td_type" style="text-align: center">${item.P2 }&nbsp;</td>
  </tr></c:forEach></tbody>
</table></div></td></tr>
			<tr>
			<td width="100%">
				<div class="panel">
					<h1 style="text-align:center">
						<spring:message code="pa.insurance.title.baoxianfulijikoukuanxiangmu"/><!--保险福利及税金扣款项目-->
					</h1>
<table class="user_table" width="100%" border="0" cellspacing="0" cellpadding="0"><thead>
  <tr>
    <th class="td_title" style="text-align:center">养老保险(公司)</th>
    <th class="td_title" style="text-align:center">公积金(公司)</th>
    <th class="td_title" style="text-align:center">失业保险(公司)</th>
    <th class="td_title" style="text-align:center">医疗保险(公司)</th>
    <th class="td_title" style="text-align:center">生育保险(公司)</th>
    <th class="td_title" style="text-align:center">工伤保险(公司)</th>
    </tr>
  </thead><c:forEach items="${welfarePayInfo}" var="item">
  <tbody>
  <tr>
    <td class="td_type" style="text-align: center">${item.P1 }&nbsp;</td>
    <td class="td_type" style="text-align: center">${item.P2 }&nbsp;</td>
    <td class="td_type" style="text-align: center">${item.P3 }&nbsp;</td>
    <td class="td_type" style="text-align: center">${item.P4 }&nbsp;</td>
    <td class="td_type" style="text-align: center">${item.P5 }&nbsp;</td>
    <td class="td_type" style="text-align: center">${item.P6 }&nbsp;</td>
  </tr></tbody>
  <thead>
  <tr>
    <th class="td_title" style="text-align:center">养老保险(个人)</th>
    <th class="td_title" style="text-align:center">公积金(个人)</th>
    <th class="td_title" style="text-align:center">失业保险(个人)</th>
    <th class="td_title" style="text-align:center">医疗保险(个人)</th>
    <th class="td_title" style="text-align:center">生育保险(个人)</th>
    <th class="td_title" style="text-align:center">大额大病保险(个人)</th>
    </tr>
  </thead>
  <tbody>
  <tr>
    <td class="td_type" style="text-align: center">${item.P7 }&nbsp;</td>
    <td class="td_type" style="text-align: center">${item.P8 }&nbsp;</td>
    <td class="td_type" style="text-align: center">${item.P9 }&nbsp;</td>
    <td class="td_type" style="text-align: center">${item.P10 }&nbsp;</td>
    <td class="td_type" style="text-align: center">${item.P11 }&nbsp;</td>
    <td class="td_type" style="text-align: center">${item.P12 }&nbsp;</td>
  </tr></tbody></c:forEach>
</table></div></td></tr>
			<tr>
			<td width="100%">
				<div class="panel">
					<h1 style="text-align:center">
						管理费用,税金,其他,实发工资<!--管理费用,税金,其他,实发工资-->
					</h1>
<table class="user_table" width="100%" border="0" cellspacing="0" cellpadding="0"><thead>
  <tr>
    <th class="td_title" style="text-align:center">工会费(个人)</th>
    <th class="td_title" style="text-align:center">爱心捐赠等退税</th>
    <th class="td_title" style="text-align:center">所得税</th>
    <th class="td_title" style="text-align:center">应发工资(已经扣除考勤扣减)</th>
    <th class="td_title" style="text-align:center">爱心基金</th>
    <th class="td_title" style="text-align:center">实发工资</th>
  </tr>
  </thead><c:forEach items="${administrationPayInfo}" var="item">
  <tbody>
  <tr>
    <td class="td_type" style="text-align: center">${item.P1 }&nbsp;</td>
    <td class="td_type" style="text-align: center">${item.P2 }&nbsp;</td>
    <td class="td_type" style="text-align: center">${item.P3 }&nbsp;</td>
    <td class="td_type" style="text-align: center">${item.P4 }&nbsp;</td>
    <td class="td_type" style="text-align: center">${item.P5 }&nbsp;</td>
    <td class="td_type" style="text-align: center">${item.P6 }&nbsp;</td>
  </tr></tbody></c:forEach>
</table></div></td></tr>
			<tr>
			<td width="100%">
				<div class="panel">
					<h1 style="text-align:center">
						手工调整项目列表<!-- 手工调整项目列表 -->
					</h1>
<table class="user_table" width="100%" border="0" cellspacing="0" cellpadding="0"><thead>
  <tr>
    <th class="td_title" style="text-align:center">工资项目编号</th>
    <th class="td_title" style="text-align:center">名称</th>
    <th class="td_title" style="text-align:center">金额</th>
    <th class="td_title" style="text-align:center">应用月</th>
    <th class="td_title" style="text-align:center">过期月</th>
    <th class="td_title" style="text-align:center">备注</th>
  </tr>
  </thead>
  <tbody><c:forEach items="${paManuallyInfo}" var="item">
  <tr>
    <td class="td_type" style="text-align: center">${item.P1 }&nbsp;</td>
    <td class="td_type" style="text-align: center">${item.P2 }&nbsp;</td>
    <td class="td_type" style="text-align: center">${item.P3 }&nbsp;</td>
    <td class="td_type" style="text-align: center">${item.P4 }&nbsp;</td>
    <td class="td_type" style="text-align: center">${item.P5 }&nbsp;</td>
    <td class="td_type" style="text-align: center">${item.P6 }&nbsp;</td>
  </tr></c:forEach></tbody>
</table></div></td></tr></table>
	<form id="pagerForm" name="pagerForm_${totalCount}" method="post" action="${pageUrl}"></form>
</div>