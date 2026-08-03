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
	
	
	obj.href="/ess/viewDept/viewOtApplySingleList?ITEM_NO="+obj.name+"&PERSON_ID="+obj.type+"&STIMESS="+$("#STIMESS").attr("value")+"&ETIMESS="+$("#ETIMESS").attr("value");
	

	

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
	style="display: none;">考勤加班申请</span>
</a>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/ess/viewDept/viewOtApplyPersonalManageList" rel="pagerForm"
		method="post" id="viewOtApplyPersonalManageList" name="viewOtApplyPersonalManageList" > 
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
							  id="viewOtApplyPersonalManageList_seachDept" />
						<ait:deptTreeIcon name="seach_DEPT_NO" cpnyId="${defaultCpny}"
							  id="viewOtApplyPersonalManageList_seachDept"
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



					<td>
					 </td>
					<td>
				 </td>
					<td>
					 </td>
					<td>
					 </td>
				</tr>
				<tr>
					<td>
						<!-- 开始日期 -->
						<spring:message code="public.title.startDate" />
					</td>
					<input type="hidden" format="yyyy/MM/dd" id="STIMESS" value="${FROM_DATE}"/>
					<input type="hidden" format="yyyy/MM/dd" id="ETIMESS" value="${TO_DATE}"/>
					<td>
						<input type="text" id="seach_FROM_DATE" name="seach_FROM_DATE"
							class="Wdate" onClick="WdatePicker({dateFmt:'yyyy/MM/dd'})" 
							value="${FROM_DATE}" />
					</td>
					<td>
						<!-- 结束日期 -->
						<spring:message code="public.title.endDate" />
					</td>
					<td>
						<input type="text" id="seach_TO_DATE" name="seach_TO_DATE"
							class="Wdate" onClick="WdatePicker({dateFmt:'yyyy/MM/dd'})" 
							value="${TO_DATE}" />
					</td>

					<%--<td>
						班次
					</td>
					<td>
						<ait:SelectSyCodeByCpnyID name="seach_SHIFT_NO" id="SHIFT_NO"
							parentNo="14013793" cnpyID="${LoginUser.cpnyId}" limit="all" />
					</td>
					--%><td>
						班组
					</td>
					<td>
						<ait:SelectSyCodeByCpnyID name="seach_GROUP" id="GROUP"
							parentNo="400223" cnpyID="${LoginUser.cpnyId}"  limit="all" />
					</td>
					<td>
						审批状态
					</td>
					<td>
						<ait:SelectSyCodeByCpnyID name="seach_AFFIRM_FLAG" id="AFFIRM_FLAG" 
							parentNo="14014304" cnpyID="${LoginUser.cpnyId}" selected="${AFFIRM_FLAG}" limit="all"/>
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
		method="post" action="/ess/viewDept/viewArPersonalSingleList"
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
						部门名
					</th>
					<th
						>
						<!--GEN -->
						职级
					</th>
					<th>
						<!--GEN -->
						合计
					</th>
						<th>
						<!--GEN -->
						平日
					</th>
						<th>
						<!--GEN -->
						周末
					</th>
						<th>
						<!--GEN -->
						法定节假日
					</th>
						<th>
						<!--GEN -->
						中夜班津贴
					</th>
					


				</tr>
				
					


			
			</thead>
			<tbody>
				<c:forEach items="${viewOtApplyPersonalList}" var="personList" varStatus="i">
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

							${personList.DEPT_NAME}
						</td>
						<td style="text-align: center">

							${personList.POST_GRADE_NO}
						</td>
						<td style="text-align: center">
                        ${personList.WEEKDAY_OT_TOTAIL+personList.WEEKEND_OT_TOTAIL+personList.HOILDAY_OT_TOTAIL}

						</td>
						<td style="text-align: center">
						
						<a style="cursor: pointer;" width="1000"  height="400"  id="codeChange"
								onclick='javascript:changeURL(this);'
								name="141444" type="${personList.PERSON_ID_ID}"  target="dialog">
								<span>${personList.WEEKDAY_OT_TOTAIL}</span>
							</a>
                    	
                           
						</td>
							<td style="text-align: center">
	<a style="cursor: pointer;" width="1000"  height="400"  id="codeChange"
								onclick='javascript:changeURL(this);'
								name="141445" type="${personList.PERSON_ID_ID}"  target="dialog">
								<span>${personList.WEEKEND_OT_TOTAIL}</span>
							</a>
                           
						</td>
							<td style="text-align: center">
	<a style="cursor: pointer;" width="1000"  height="400"  id="codeChange"
								onclick='javascript:changeURL(this);'
								name="141446" type="${personList.PERSON_ID_ID}"  target="dialog">
								<span> ${personList.HOILDAY_OT_TOTAIL}</span>
							</a>
                          
						</td>
							<td style="text-align: center">
	<a style="cursor: pointer;" width="1000"  height="400"  id="codeChange"
								onclick='javascript:changeURL(this);'
								name="14013809" type="${personList.PERSON_ID_ID}"  target="dialog">
								<span>${personList.ALLOWANCE}</span>
							</a>
                           
						</td>
							
                          
                          
                         


					</tr>
				</c:forEach>
			</tbody>
		</table>
	</form>
	<c:set value="/ess/viewDept/viewOtApplyPersonalManageList" var="pageUrl" />
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>