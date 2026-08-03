<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage="" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script>
$(document).ready(function(){
	$(".orderList",navTab.getCurrentPanel()).dataTable({"bPaginate": false,    //关闭分页
		    "bAutoWidth":false,//表格宽度不自动变化
		    "bProcessing":true,
			"bLengthChange": false,  //关闭按多少条记录显示下拉框
			"bFilter": true,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
			"bSort": true,   //关闭排序功能
			"bInfo": false,   //不显示datatables的信息（底部的页数，条目数信息）
			"bScrollInfinite":true,
			"scrollY": $(document.body).height() - 200,
            //"scrollX": true,
            "orderClasses": false,
            "oLanguage": {
                "sProcessing": "正在加载中......",
                "sZeroRecords": "查询不到相关数据！",
                "sEmptyTable": "表中无数据存在！",
                "sSearch": "快速筛选"
            } //多语言配置
		});
});
</script>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/ess/viewDept/viewUseOfAdjustLeaveList" rel="pagerForm"
		method="post" id="viewUseOfAdjustLeaveList"
		name="viewUseOfAdjustLeaveList">
		<input type="hidden" name='CODE_NO' />
		<div class="searchBar">
			<table class="searchContent">
				<tr>

                   <input type="hidden" value="${LoginUser.adminID}" name="adminId">
                   <td>期间</td>
					<td>
						<input type="text" id="seach_AR_MONTH" name="seach_AR_MONTH" class="Wdate" value="${AR_MONTHR}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
					</td>
					<td>
						<!-- 部门： -->
						<spring:message code="hr.viewPersonalInfo.title.DEPTNAME" />
					</td>
					<td>
						<ait:deptList name="seach_DEPT_NO" cpnyId="${defaultCpny}"
							limit="manager" id="viewUseOfAdjustLeaveList_seachDept" />
						<ait:deptTreeIcon name="seach_DEPT_NO" cpnyId="${defaultCpny}"
							limit="manager" id="viewUseOfAdjustLeaveList_seachDept"
							selected="${DEPTNO}" />
					</td>
					<td>
						<!-- 社号/姓名： -->
						<spring:message
							code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME" />
					</td>
					<td>
						<input type="text" name="seach_KEY" value="${KEY}" />
					</td>
				</tr>
				
			</table>
			<div class="subBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">
									<spring:message code="public.title.search" />
								</button>
							</div>
						</div>
					</li>
					<li>
					<a class="buttonActive" onclick="downloadExcel('viewUseOfAdjustLeaveList','/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=209','/ess/viewDept/viewUseOfAdjustLeaveList')">
		              <span>导出到Excel</span></a>
		            </li>
				</ul>
			</div>
		</div>
	</form>
</div>

<div class="pageContent">
         <div class="user_table" style="font:bold 12px/20px arial,sans-serif;">Total:${useOfAdjustLeaveListCnt}</div>
		<table class="orderList" width="100%">
			<thead>
				<tr>
					<th rowspan="2" width="3%">
						NO
					</th>
					<th rowspan="2" width="5%">
						<!--社号-->
						社号
					</th>
					<th rowspan="2" width="5%">
						<!--姓名 -->
						姓名
					</th>
					<th rowspan="2">
						<!--部门 -->
						部门
					</th>
					<th rowspan="2">
						<!--职级 -->
						职级
					</th>
					<th  colspan="3">
						<!--年假计划 -->
					      截止日
					</th>
				</tr>
				<tr>
					<th>总数(小时)</th>
					<th>使用(小时)</th>
					<th>剩余(小时)</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${useOfAdjustLeaveList}" var="item"
					varStatus="i">
					<tr target="sid" rel="">
						<td class="td_type">
							${i.count}
						</td>
						<td class="td_type">
						    ${item.EMPID}
						</td>
						<td class="td_type">
						    ${item.LOCAL_NAME}
						</td>
						<td class="td_type">
						    ${item.DEPTNAME}
						</td>
						<td class="td_type">
						    ${item.POST_GRADE_NAME}
						</td>
						<td class="td_type">
						    ${item.TOTAL_TX}
						</td>
						<td class="td_type">
						    ${item.USE_TX}
						</td>
						<td class="td_type"> 
						    ${item.SHENGYU_TX}
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	<c:set value="/ess/viewDept/viewUseOfAdjustLeaveList" var="pageUrl" />
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>