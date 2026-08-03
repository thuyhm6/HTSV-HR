<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage="" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script type="text/javascript" src="script/jquery.js">
</script>
<script type="text/javascript" src="script/jquery.easydrag.js">
</script>
<script>

function changeURL(obj) {

	//$("input[name='keleyicom']");
	
	
	obj.href="viewEntryInfoList?CODE_NO="+obj.name;
	

	

}


function exportExcle(a){
     var $this=$(a);
     var title = $this.attr("title"); 
     var $from = $("#viewArVacationMonth");  
     
     var url = "/ar/attendanceVacations/viewArVacationMonthExcel";
	 alertMsg.confirm(title, {
					okCall: function(){  
					   window.location =url+ (url.indexOf('?') == -1 ? "?" : "&") + $from.serialize();
					}});
}

</script>
<a id="importExcelDialog_ess0240" href="#" target="dialog" mask="true"></a>
<a id="importExcel_ess0240" href="#" target="navTab" mask="true"><span
	style="display: none;">发令Leave信息导入结果</span>
</a>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="viewEntryInfoList" rel="pagerForm"
		method="post" id="viewAbsenteeismInfoList" name="viewAbsenteeismInfoList" > 
		<input type="hidden" name='CODE_NO' />
		<div class="searchBar">
			<table class="searchContent">
				<tr>




					<td>
						<!-- 部门： -->
						<spring:message code="hr.viewPersonalInfo.title.DEPTNAME" />
					</td>
					<td>
						<ait:deptList name="seach_DEPT_NO" cpnyId="${defaultCpny}"
							limit="ar" id="viewApplyLeaveInfoList_seachDept" />
						<ait:deptTreeIcon name="seach_DEPT_NO" cpnyId="${defaultCpny}"
							limit="ar" id="viewApplyLeaveInfoList_seachDept"
							selected="${DEPTNO}" />
					</td>
					<td>
						<!-- 社号/姓名： -->
						<spring:message
							code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME" />
					</td>
					<td>

						<input type="text" name="seach_KEY" value="${KEY}" />

                           


					</td><%--



					<td>
						人员类型组
					</td>
					<td>
						<input type="hidden" id="ess0240_1_limit" name="limit" value="ar">
						<input type="hidden" id="ess0240_1_seach_CPNY" name="seach_CPNY"
							value="${defaultCpny}">
						<ait:SelectEmpTypeCode id="ess0240_1_seach_JobTypeGroupNo"
							name="seach_JobTypeGroupNo" selected="${JobTypeGroupNo}"
							limit="ar" type="group"
							onChangeName="ajaxEmpTypeForGroupToList(-1,ess0240_1_seach_JobTypeGroupNo,ess0240_1_seach_EmpTypeCodeNo,ess0240_1_seach_CPNY,ess0240_1_limit)" />
					</td>
					<td>
						人员类型
					</td>
					<td>
						<ait:SelectEmpTypeCode id="ess0240_1_seach_EmpTypeCodeNo"
							name="seach_EmpTypeCodeNo" selected="${EmpTypeCodeNo}" limit="ar" />
					</td>
				--%></tr>
				<tr>
					<td>
						<!--期间 -->
						期间
					</td>
					<td>
						<input type="text" id="seach_FROM_DATE" name="seach_FROM_DATE"
							class="date" format="yyyy-MM-dd" readonly="true"
							value="${FROM_DATE}" />
						<a class="inputDateButton" href="javascript:;"><spring:message
								code="public.title.choose" />
							<!-- 选择 -->
						</a>
					</td>
					<td>
						<!-- 结束日期 -->
-					
</td>
					<td>
						<input type="text" id="seach_TO_DATE" name="seach_TO_DATE"
							class="date" format="yyyy-MM-dd" readonly="true"
							value="${TO_DATE}" />
						<a class="inputDateButton" href="javascript:;"><spring:message
								code="public.title.choose" />
							<!-- 选择 -->
						</a>
					</td><%--

					<td>
						班组
					</td>
					<td>
						<ait:SelectSyCodeByCpnyID name="SHIFT_NO" id="SHIFT_NO"
							parentNo="14013793" cnpyID="${LoginUser.cpnyId}" />
					</td>
					--%><%--<td>
						班次
					</td>
					<td>
						<ait:SelectSyCodeByCpnyID name="GROUP" id="GROUP"
							parentNo="400223" cnpyID="${LoginUser.cpnyId}" />
					</td>
					--%><%--<td>
						审批状态
					</td>
					<td>
						<select id="seach_AFFIRM_FLAG" name="seach_AFFIRM_FLAG">
							<option value="">
								全部
							</option>
							<option value="-1"
								<c:if test="${AFFIRM_FLAG eq '-1'}">selected</c:if>>
								暂存
							</option>
							<option value="0"
								<c:if test="${AFFIRM_FLAG eq '0'}">selected</c:if>>
								提交
							</option>
							<option value="4"
								<c:if test="${AFFIRM_FLAG eq '4'}">selected</c:if>>
								审批中
							</option>
							<option value="1"
								<c:if test="${AFFIRM_FLAG eq '1'}">selected</c:if>>
								通过
							</option>
							<option value="2"
								<c:if test="${AFFIRM_FLAG eq '2'}">selected</c:if>>
								否决
							</option>
							<option value="3"
								<c:if test="${AFFIRM_FLAG eq '3'}">selected</c:if>>
								撤销
							</option>
							<option value="3"
								<c:if test="${AFFIRM_FLAG eq '5'}">selected</c:if>>
								发令
							</option>
						</select>
					</td>
				--%></tr>
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
				</ul>
			</div>
		</div>
	</form>
</div>

<div class="pageContent"  >
	<div class="formBar">
		<ul class="toolBar">

			<li>
				<a class="buttonActive" onclick="exportExcle(this)"<%--是否导出?--%> title="<spring:message code='rp.report.title.exportYN'/>">
				  		<span><%--Excel导出--%><spring:message code="ar.addempshift.title.excelexport"/></span></a>
			</li>

		</ul>
	</div>
	<form name="delLeaveApplyAffirmForm" id="delLeaveApplyAffirmForm"
		method="post" action="viewEntryInfoList"
		onsubmit="return delLeaveApplyCallback(this, navTabAjaxDone);"
		target="dialog">
		<table class="table" layoutH="235" border="1" width="100%" nowrapTD="false">
			<thead>
				<tr>
					<th
						>
						NO
					</th>
					<%--<td
						style="layout-flow: vertical-ideographic; height: 50px; overflow: auto">
						<!--姓名 -->
						姓名
					</td>
					--%>
					<th>
						<!--姓名 -->
						姓名
					</th>
					<th>
						<!--部门 -->
						社号
					</th>
					<th>
						<!--等级名-->
					日期
					</th>
					<th
						>
						<!--GEN -->
						进门
					</th>
					<th>
						<!--GEN -->
						出门
					</th>
						<th>
						<!--GEN -->
						备注
					</th>
					
					


				</tr>
				
					


			
			</thead>
			<tbody>
				<c:forEach items="${viewAbsenteeismInfoList}" var="personList" varStatus="i">
					<tr target="sid" rel="">
						<td style="text-align: center">

							${i.count}
						</td>
						<td style="text-align: center">

							${personList.LOCAL_NAME}
						</td>
						<td style="text-align: center">

							${personList.EMPID_ID}
						</td>
						
						<td style="text-align: center">


						</td>
							<td style="text-align: center">


						</td>
							<td style="text-align: center">


						</td>
							<td style="text-align: center">
</td>

					
                          
                          
                         


					</tr>
				</c:forEach>
			</tbody>
		</table>
	</form>
	<c:set value="/ess/viewDept/viewEntryInfoList" var="pageUrl" />
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>