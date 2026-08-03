<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
	//读取变动工资
	function doSalesmanVariablePayRead(form1) {
		var params = $("#searchForm_se0201_aa").serialize();
		var CLOS_FLAG = document.searchForm_se0201_aa.CLOS_FLAG.value;
		var payAreaCd = document.searchForm_se0201_aa.seach_PAY_AREA_NM.value;
		if (payAreaCd == '') {
			alert("请先选择大区!");
			return false;
		}
		if (CLOS_FLAG == 'Y') {
			alert("工资计算已经关闭,不可以读取变动工资!");
		} else {
			alertMsg.confirm("确认读取变动工资?", {
				okCall : function() {
					$.ajax({
						type : 'post',
						cache : false,
						url : "/inct/salesman/callSalesmanVariablePayRead?"
								+ params,
						success : function(result) {							
							if (result.statusCode == 200) {	
								alertMsg.info("读取变动工资成功！");
								//页面重载
								navTabSearch($("#searchForm_se0201_aa"));
							}else{
								alertMsg.info(result.message);
							}
						}
					});
				}
			});
		}
	}
	//提成计算
	function doSalesmanIncentiveCalc(form1) {
		var params = $("#searchForm_se0201_aa").serialize();
		var CLOS_FLAG = document.searchForm_se0201_aa.CLOS_FLAG.value;
		var payAreaCd = document.searchForm_se0201_aa.seach_PAY_AREA_NM.value;
		if (payAreaCd == '') {
			alert("请先选择大区!");
			return false;
		}
		if (CLOS_FLAG == 'Y') {
			alert("工资计算已经关闭,不可以进行提成计算!")
		} else {
			alertMsg.confirm("确认开始计算提成?", {
				okCall : function() {
					$.ajax({
						type : 'post',
						cache : false,
						url : "/inct/salesman/callSalesmanIncentiveCalc?"
								+ params,
						success : function(result) {							
							if (result.statusCode == 200) {
								alertMsg.info("计算成功！");
								//页面重载
								navTabSearch($("#searchForm_se0201_aa"));
							}else{
								alertMsg.info(result.message);
							}
						}
					});
				}
			});
		}
	}
	//excel导出
	function exportIncentiveCalcList(a, navTabId) {
		var payAreaCd = document.searchForm_se0201_aa.seach_PAY_AREA_NM.value;
		var sform = document.getElementById("searchForm_se0201_aa");
		alertMsg.confirm("Do you want to export?", {
			okCall : function() {
				//用于excel导出的表单参数处理
				var eForm = document.getElementById("excelExportForm_se0201"); 
				document.getElementById("se0201Link").innerHTML = "EXCEL密码设置";
				eForm.PAY_AREA_CD.value	= sform.seach_PAY_AREA_CD.value;
				eForm.DEPTNO.value 		= sform.seach_DEPTNO.value;
				eForm.YEAR.value 		= sform.seach_YEAR.value;
				eForm.MONTH.value 		= sform.seach_MONTH.value;
				eForm.EMPNO.value 		= sform.seach_EMPID.value;
				$("#importExcelDialog_se0201").attr('href', "/sys/encryptExcel"
						+"?exportFunName=/inct/salesman/viewIncentiveCalcListExcel"
						+"&navTabId=se0201"
						+"&formId=excelExportForm_se0201");
				$("#importExcelDialog_se0201").attr('width', "300");
				$("#importExcelDialog_se0201").attr('height', "150");
				$("#importExcelDialog_se0201").click();
			}
		});
	}

	//个人明细修改
	function editIncentiveCalcItem(param) {
		var CLOS_FLAG = document.searchForm_se0201_aa.CLOS_FLAG.value;
		if (CLOS_FLAG == 'Y') {
			alert("工资计算已经关闭,不可以进行业务员业务员提成更新调整,计算提成!")
		} else {
			$("#importExcelDialog_se0201").attr('href',
					"/inct/salesman/editIncentiveCalcItemView?" + param);
			$("#importExcelDialog_se0201").attr('width', "400");
			$("#importExcelDialog_se0201").attr('height', "350");
			var se0201Link = document.getElementById("se0201Link");
			se0201Link.innerHTML = "提成计算结果调整";
			$("#importExcelDialog_se0201").click();
		}

	}
	//导入数据
	function importExcelSalesIncData() {
		var params = $("#searchForm_se0201_aa").serialize();
		var CLOS_FLAG = document.searchForm_se0201_aa.CLOS_FLAG.value;
		var payAreaCd = document.searchForm_se0201_aa.seach_PAY_AREA_NM.value;
		if (payAreaCd == '') {
			alert("请先选择大区!");
			return false;
		}
		if (CLOS_FLAG == 'Y') {
			alert("工资计算已经关闭,不可以进行业务员业务员提成导入调整,计算提成!")
		} else {
			var se0201Link = document.getElementById("se0201Link");
			var payAreaCd = document.searchForm_se0201_aa.seach_PAY_AREA_CD.value;
			se0201Link.innerHTML = "EXCEL导入提成调整";
			var url = '/pa/excelImport/importExcelData'
					+ '?importFunName=/importExcelSalesIncCalcData'
					+ '&ACCRUAL_YN=N';
			$("#importExcelDialog_se0201").attr('href', url);
			$("#importExcelDialog_se0201").attr('width', "400");
			$("#importExcelDialog_se0201").attr('height', "200");
			$("#importExcelDialog_se0201").click();
		}
	}
</script>
<div class="pageHeader">
	<a id="importExcelDialog_se0201" href="#" target="dialog" mask="true"><span
		id="se0201Link" style="display: none"></span></a> 
	<a id="importExcel_se0201" href="#" width="800" height="520" target="dialog" mask="true"><span
		id="tabName_se0201" style="display: none">提成调整EXCEL导入结果</span></a>
	<form name="searchForm_se0201_aa" id="searchForm_se0201_aa"
		onsubmit="return navTabSearch(this);" 
		action="/inct/salesman/viewIncentiveCalcList" method="post"
		rel="pagerForm">
		<div class="searchBar">
			<table class="searchContent">
				<input type="hidden" id="CLOS_FLAG" name="CLOS_FLAG" value="${closeFlag}" />
				<input type="hidden" name="seach_ACCRUAL_YN" id="seach_ACCRUAL_YN" value="N"/>
				<tr>
					<td><spring:message code="inct.salesman.daqu" /> <!--大区-->：</td>
					<td>
						<ait:deptTreeMulti id="seach_PAY_AREA_CD" name="seach_PAY_AREA_NM" limit="pa" level="2" 
						selected="${searchMap.PAY_AREA_CD}"
						selectedNm="${searchMap.PAY_AREA_NM}" />	
					</td>
					<td><spring:message code="hr.viewPersonalInfo.title.DEPTNAME" /> <!--部门名称-->：</td>
					<td><ait:deptList name="seach_DEPTNO" id="seachDept_se0201"/>
						<ait:deptTreeIcon name="seach_DEPTNO" limit="pa" id="seachDept_se0201" selected="${searchMap.DEPTNO}"/>
					</td>
				</tr>
				<tr>
					<td><spring:message code="ar.excelexport.title.month" /> <!--月份-->：</td>
					<td><ait:date yearName="seach_YEAR" yearSelected="${searchMap.YEAR}"
							monthName="seach_MONTH" monthSelected="${searchMap.MONTH}" /></td>

					<td><spring:message code="inct.salesman.empNoNName" /> <!--社号/姓名-->：</td>
					<td>						
						<input id="seach_EMPID" name="dwz.person.empId" type="text" value="${searchMap.EMPNO}" lookupGroup="person"/>
						<input id="seach_PERSON_ID" name="dwz.person.personId" type="hidden" value="" readOnly lookupGroup="person"/>
						<a class="btnLook" style="float:right;" href="/ar/attendanceSettings/viewKeeperList?pageNum=1" width="900" height="400" lookupGroup="person"><!-- 查找带回 --><spring:message code="ar.alert.message.viewattendencekeeper.chazhaodaihui"/></a>
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<c:if test="${toolbarInfo.UPDATER == '1'}">
									<button type="button" onclick="doSalesmanVariablePayRead(this)">
										<spring:message code="inct.salesman.button.readVariablePay" />
										<!--读取变动工资-->
									</button>
								</c:if>
							</div>
						</div>
					</li>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<c:if test="${toolbarInfo.UPDATER == '1'}">
									<button type="button" onclick="doSalesmanIncentiveCalc(this)">
										<spring:message code="inct.salesman.button.incentiveCalc" />
										<!--提成计算-->
									</button>
								</c:if>
							</div>
						</div>
					</li>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">
									<spring:message code="public.title.search" />
									<!-- 检索 -->
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
			<li><div class="buttonActive">
					<div class="buttonContent">
						<c:if test="${toolbarInfo.UPDATER == '1'}">
							<a class="downloadExel" href="/inct/salesman/downloadIncentiveCalcTemplate">						
							<span>
								<spring:message code="inct.salesman.downloadExcelTemplate" />
							</span>
							</a>
						</c:if>
					</div>
				</div></li>
			<li><div class="buttonActive">
					<div class="buttonContent">
						<c:if test="${toolbarInfo.UPDATER == '1'}">
							<button type="button" onclick="importExcelSalesIncData()">
								<spring:message code="inct.salesman.uploadExcel" />
								<!--上传excel-->
							</button>
						</c:if>
					</div>
				</div></li>
			<li><div class="buttonActive">
					<div class="buttonContent">
						<button type="button" id="btnExcelExport_se0201" name="btnExcelExport_se0201"
							onclick="exportIncentiveCalcList(this,'${request_navTabId}')">
							<spring:message code="inct.salesman.downloadToExcel" />
							<!--excel导出-->
						</button>
					</div>
				</div></li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="231">
		<thead>
			<tr>
				<th width="10%"><spring:message code="hr.viewPersonalInfo.title.DEPTNAME" />
					<!--部门名称--></th>
				<th width="6%"><spring:message code="inct.salesman.empNo" /> <!--社号--></th>
				<th width="6%"><spring:message code="inct.salesman.empName" />
					<!--员工姓名--></th>
				<th width="6%"><spring:message code="ar.excelexport.title.month" /> 
					<!--月份--></th>
				<th width="5%"><spring:message code="inct.salesman.variablePay" /> 
					<!--变动工资--></th>
				<th width="5%"><spring:message code="inct.salesman.ratio" />
					<!--比率--></th>
				<th width="8%"><spring:message
						code="inct.salesman.variablePay" />*<spring:message
						code="inct.salesman.evalPoint" /> <!--变动工资*评价分--></th>
				<th width="5%"><spring:message code="inct.salesman.deduct" />
					<!--扣款--></th>
				<th width="5%"><spring:message code="inct.salesman.adjustInct" />
					<!--调整提成--></th>
				<th width="5%"><spring:message code="inct.salesman.inctResult" />
					<!--提成结果--></th>
				<th width="10%"><spring:message code="inct.salesman.remark" />
					<!--备注--></th>
				<th width="7%">注册人
					<!--注册人-->
				</th>
				<th width="7%"><spring:message code="inct.salesman.createTime" />
					<!--注册时间-->
				</th>
				<th width="7%"><spring:message code="inct.salesman.updateBy" />
					<!--更新人--></th>
				<th width="7%"><spring:message code="inct.salesman.updateTime" />
					<!--更新时间--></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${itemList}" var="mdata" varStatus="i">
				<tr target="incentiveItem"
					rel="EMPNO=${mdata.EMPNO}&INCTV_MON=${mdata.INCTV_MON}&ACCRUAL_YN=${mdata.ACCRUAL_YN}">
					<td>${mdata.ORG_NM}</td>
					<td class='td_center'><a
						onclick="editIncentiveCalcItem('SUBSD_CD=${mdata.SUBSD_CD}&EMPNO=${mdata.EMPNO}&INCTV_MON=${mdata.INCTV_MON}&ACCRUAL_YN=N');">
							${mdata.EMPNO} </a></td>
					<td class='td_center'>${mdata.EMP_NM}</td>
					<td class='td_center'>${mdata.INCTV_MON}</td>
					<td class='td_right'>${mdata.VARB_INCTV_AMT}</td>
					<td class='td_right'>${mdata.ACHV_RAT}</td>
					<td class='td_right'>${mdata.BASE_PAY}</td>
					<td class='td_right'>${mdata.DEDUT_AMT}</td>
					<td class='td_right'>${mdata.ADJST_AMT}</td>
					<td class='td_right'>${mdata.TOT_INCTV_AMT}</td>
					<td>${mdata.REMARK}</td>
					<td class='td_center'>${mdata.RGST_USER}</td>
					<td class='td_center'>${mdata.RGST_DTIME}</td>
					<td class='td_center'>${mdata.UPDT_USER}</td>
					<td class='td_center'>${mdata.UPDT_DTIME}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<c:set value="/inct/salesman/viewIncentiveCalcList" var="pageUrl" />
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
	
<form id="excelExportForm_se0201" name="excelExportForm_se0201" method="post">
	<input type="hidden" id="password" name="password" value="" />
	<input type="hidden" id="PAY_AREA_CD" name="PAY_AREA_CD" value="" />
	<input type="hidden" id="DEPTNO" name="DEPTNO" value="" />
	<input type="hidden" id="YEAR" name="YEAR" value="" />
	<input type="hidden" id="MONTH" name="MONTH" value="" />
	<input type="hidden" id="EMPNO" name="EMPNO" value="" />
</form>
</div>