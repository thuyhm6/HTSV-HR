<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script type="text/javascript">
function CheckFormExpiredContract(form,navTabId){
	var $form=$(form);
	
    return true;
}
function doLeaveViewExport(from){
  	var $from =$(from);
  	var url ="/ar/attendanceVacations/viewLeaveViewForSearchExcel";
  	window.location =url+ (url.indexOf('?') == -1 ? "?" : "&") + $from.serialize();
}
function expLeaveView(a,navTabId){
  	var $this = $(a);
  	var title = $this.attr("title");
  	var $from = $("#viewLeaveViewInfo");
  	if(CheckFormExpiredContract($from,navTabId)){
	     alertMsg.confirm(title, {okCall: function(){ doLeaveViewExport($from);}});
    } 
}

</script>

<div class="pageHeader">
	<form id="viewLeaveViewInfo" onsubmit="return navTabSearch(this);" action="/ar/attendanceVacations/viewLeaveView" method="post" rel="pagerForm">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td><!-- 部门： -->
					<spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/>
					<ait:deptTree name="seach_DEPTNO" limit="hr" selected="${DEPTNO}" /> 
				</td>
				<td><!-- 工号/姓名： -->
					<spring:message code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME"/>
					<input type="text" name="seach_KEY" value="${KEY}" />
					
				</td>
				<td>
				
				</td>
				<td width="25%" style="padding: 4px;">
						<spring:message code="ess.infoApply.title.date"/><!--工资月-->
						<ait:date yearName="seach_inYear" monthName="seach_inMonth" yearSelected="${year}" monthSelected="${month}"/>
											
				</td>
				<td>
					
					<spring:message code="ess.infoApply.title.leaveApplyType"/>
                    <ait:SelectSyCodeByCpnyID id="a" name="a" parentNo="21" cnpyID="${defaultCpny}" onChangeName="" limit="all"/>	
				</td>
			</tr>
			
		</table>
		<div class="subBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">
								<spring:message code="public.title.search"/><!-- 检索 -->
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
<div class="formBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="button" onclick="expLeaveView(this)" title="<spring:message code='rp.report.title.exportYN'/>">
								<%--导出Excel--%><spring:message code="ar.addempshift.title.excelexport"/>
							</button>
						</div>
					</div>						
				</li>
			</ul>
		</div>
	<table width="120%" class="table" layoutH="214">
		<thead>
		  <tr>
		    <th width="3%">职号</th>
		    <th width="3%">姓名</th>
		    <th width="3%">部门</th>
		    <th width="3%">职系</th>
		    <th width="5%">入社日期</th>
		    <th width="4%">工龄明细 本公司</th>
		    <th width="5%">工龄明细 其他公司</th>
		    <th width="4%">工龄明细 总计</th>
		    <th width="6%">发生基准月</th>
		    <th width="5%">对象与否</th>
		    <th width="5%">基本休假</th>
		    <th width="5%">基本扣除</th>
		    <th width="4%">新标准</th>
		    <th width="4%">新扣除</th>
		    <th width="5%">年假合计</th>
		    <th width="5%">本年移年年假</th>
		    <th width="5%">本年清算年假</th>
		    <th width="5%">移年年假</th>
		    <th width="5%">移年清算年假</th>
		    <th width="5%">已休移年年假</th>
		    <th nowrap width="5%">已休本年年假</th>
		    <th  nowrap width="4%">剩余年假</th>
		  </tr>
		</thead>
		<tbody>
		<c:forEach items="${viewLeaveViewList}" var="oneResult" varStatus="i">
			<tr>
		 	<td class="td_center">${oneResult.EMPID}</td>
			<td nowrap="nowrap">${oneResult.LOCAL_NAME} &nbsp;</td>
			<td nowrap="nowrap">${oneResult.DEPTNAME} &nbsp;</td>
			<td nowrap="nowrap">${oneResult.POST_COEFNAME} &nbsp;</td>
			<td nowrap="nowrap">${oneResult.DATE_STARTED}</td>
			<td nowrap="nowrap">${oneResult.DAY_N}<spring:message code="display.mutual.month"/></td>
			<td nowrap="nowrap">${oneResult.DAY_W}<spring:message code="display.mutual.month"/></td>
			<td nowrap="nowrap">${oneResult.DAY}<spring:message code="display.mutual.month"/></td>
			<td nowrap="nowrap">${yearb}-12-20</td>
			<td nowrap="nowrap">Y</td>
			<td nowrap="nowrap">${oneResult.VAC_STANDARD_OLD}&nbsp;<!--天--><spring:message code="display.mutual.day"/></td>
			<td nowrap="nowrap">${oneResult.VAC_DEDUCT_OLD}&nbsp;<!--天--><spring:message code="display.mutual.day"/></td>
			<td nowrap="nowrap">${oneResult.VAC_STANDARD_NEW}<spring:message code="display.mutual.day"/></td>
			<td nowrap="nowrap">${oneResult.VAC_DEDUCT_NEW}&nbsp;<spring:message code="display.mutual.day"/></td>
			<td nowrap="nowrap">${oneResult.TOT_VAC_CNT}&nbsp;<spring:message code="display.mutual.day"/></td>
			<td nowrap="nowrap">${oneResult.NEXT_VAC}&nbsp;<spring:message code="display.mutual.day"/></td>
            <td nowrap="nowrap">${oneResult.LIQUIDATION}&nbsp;<spring:message code="display.mutual.day"/></td>
            <td nowrap="nowrap">${oneResult.LAST_YEAR_VAC}&nbsp;<spring:message code="display.mutual.day"/></td>
            <td nowrap="nowrap">${oneResult.LIQUIDATION_LAST}&nbsp;<spring:message code="display.mutual.day"/></td>
            <td nowrap="nowrap">${oneResult.XIUYINIAN}&nbsp;<spring:message code="display.mutual.day"/></td>
            <td nowrap="nowrap">${oneResult.XIUBENNIAN}&nbsp;<spring:message code="display.mutual.day"/></td>
            <td nowrap="nowrap">${oneResult.SHENGYU}&nbsp;<spring:message code="display.mutual.day"/></td>
			</tr>
		</c:forEach>
		</tbody>
</table>
	<c:set value="/ar/attendanceVacations/viewLeaveView" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
	
</div>
