<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
//导出
function exportSalesmanEvalImportInfo(a) {
	var $from = $("#viewEvalDataExcelImportResult");
	alertMsg.confirm("确定导出么?", {
		okCall : function() {
			doSalesmanEvalImportInfoExport($from);
		}
	});
}
function doSalesmanEvalImportInfoExport(from) {
	var $from = $("#viewEvalDataExcelImportResult");
	var url = "/hrm/empinfo/viewExportEmpInfoExcelTempList";
	window.location = url + (url.indexOf('?') == -1 ? "?" : "&")
			+ $from.serialize();
}
</script>
<div class="pageHeader">
	<form id="viewEvalDataExcelImportResult" name="viewEvalDataExcelImportResult"
			action="/hrm/empinfo/importEmpInfoTempList" 
			onsubmit="return navTabSearch(this);"
			method="post" 
			rel="pagerForm" >
	<div class="searchBar" >
		<table class="searchContent">
			<tr>
				<th>
					<spring:message code="inct.salesman.excel.totalCnt"/><!-- 总行数-->：
				</th>
				<td>
					${totalCnt}
					<iframe id='callbackframe' name='callbackframe' src='about:blank' style='display:none'></iframe>
				</td>
				<th>
					<spring:message code="inct.salesman.excel.errCnt"/><!-- 出错行数-->：
				</th>
				<td>							
					${errCnt}
				</td>
				<th>
					<!-- 出错与否： --><spring:message code="empsubject.errorYn" />
				</th>
				<td>							
					<select name="seach_RESULT_FLAG" id="seach_RESULT_FLAG">
							<option value="" <c:if test="${RESULT_FLAG eq '' }">selected</c:if>><!-- 全部 --><spring:message code="hrm.empinfo.ALL"/></option>
							<option value="E" <c:if test="${RESULT_FLAG eq 'E' }">selected</c:if>><!--是  --><spring:message code="hrm.empinfo.yes" /></option>
							<option value="N" <c:if test="${RESULT_FLAG eq 'N' }">selected</c:if>><!-- 否 --><spring:message code="hrm.empinfo.no"/></option>
					</select>
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
			<%-- <li><a class="buttonActive" onclick="importExcelTempSalesAccrualData()"> <span><spring:message
			code="ar.addempshift.title.excelimport" /><!-- EXCEL导入 --></span> </a></li> --%>
			<li>
				<div class="buttonActive">
					<div class="buttonContent">
						<button type="button" onclick="exportSalesmanEvalImportInfo(this)">
							<!--excel导出--><spring:message code="inct.salesman.downloadToExcel" />
						</button>
					</div>
				</div>
			</li>
			<li><a class="buttonActive" target="ajaxTodo" callback="navTabAjaxDoneRefreshCurrentPage" 
			href="/hrm/empinfo/submitImportExcelWorkExperienceData?accrual=baseEmpInfo"  title="确定要提交吗?"><span>
				<spring:message code="public.title.submit"/><!--提交--></span></a>
			</li>
		</ul>
	</div>
	<table class="table" width="200%" layoutH="220">
		<thead>
			<tr>
				<th><!-- 序号 --><spring:message code="hrm.contract.NO"/></th>
				<th><!-- 社号(必填) --><spring:message code="hrm.empinfo.NUMBER_MUST_WRITE" /></th>
				<th><!-- 员工姓名(可为空) --><spring:message code="hrm.empinfo.EMPLOYEE_NAME_OR_NULL"/></th>
				<th><!-- 家庭住址 --><spring:message code="hrm.empinfo.HOME_ADDRESS" /></th>
				<th><!-- 籍贯 --><spring:message code="hr.viewPersonalInfo.title.BORNPLACE_NAME" /></th>
				<th><!-- 民族 --><spring:message code="hrm.empinfo.NATION_CODE"/></th>
				<th><!-- 政治面貌 --><spring:message code="hr.viewPersonalInfo.title.POLITY_NAME"/></th>
				<th><!-- 是否共产党员 --><spring:message code="hr.viewPersonalInfo.title.WHETHER_COMMUNIST"/></th>
				<th><!-- 身高 --><spring:message code="hr.viewPersonalInfo.title.shengao"/></th>
				<th><!-- 体重 --><spring:message code="hr.viewPersonalInfo.title.tizhong"/></th>
				<th><!-- 血型 --><spring:message code="hr.viewHealth.title.BLOOD_TYPE_NAME"/></th>
				<th><!-- 是否残疾 --><spring:message code="hr.viewCondSql.titleSHIFOUCANJI"/></th>
				<th><!-- 招聘来源 --><spring:message code="hr.viewPersonalInfo.title.zhaopinlaiyuan"/></th>
				<th><!-- 离职原因 --><spring:message code="hr.viewPromote.title.RESIGN_REASON"/></th>
				<th><!-- 奖惩备注 --><spring:message code="hr.viewPersonalInfo.title.jiangchengbeizhu"/></th>
				<th><!-- 爱心基金支付方式 --><spring:message code="hr.viewPersonalInfo.title.aixinjijinzhifufangshi"/></th>
				<th><!-- 是否支付爱心基金 --><spring:message code="hr.viewPersonalInfo.title.shifouzhifuaixinjijin"/></th>
				<th><!-- 负担房租标志 --><spring:message code="hr.viewPersonalInfo.title.fudanfangzubiaozhi"/></th>
				<th><!-- 负担医疗费标志 --><spring:message code="hr.viewPersonalInfo.title.fudanyiliaofeibiaozhi"/></th>
				<th><!-- 负担教育费标志 --><spring:message code="hr.viewPersonalInfo.title.fudanjiaoyufeibiaozhi"/></th>
				<!-- <th>人员类型(CHR)</th> -->
				<th><!-- 工作类型(CHR) --><spring:message code="hr.viewPersonalInfo.title.gongzuoleixing.chr"/></th>
				<!-- <th>人员类型生效日期</th> -->
				<th><!-- 福利地区(保险) --><spring:message code="hrm.empinfo.WELFARE_AREA_INSURANCE"/></th>
				<th><!-- 工作地区 --><spring:message code="hrm.contractInfo.WORK_AREA"/></th>
				<th><!-- 劳动手册编号 --><spring:message code="hr.viewPersonalInfo.title.laodongshoucebianhao"/></th>
				<th><!-- 社外工龄 --><spring:message code="hr.viewPersonalInfo.title.shewaigongling"/></th>
				<th><!-- 福利地区(公积金)--><spring:message code="hrm.empinfo.WELFARE_AREA_ACCUMULATION_FUND"/></th>
				<th><!-- 保险公司 --><spring:message code="hr.viewPersonalInfo.title.baoxiangongsi"/></th>
				<th><!-- 保险类型 --><spring:message code="hrm.empinfo.SAFE_TYPE_CODE_NAME"/></th>
				<th><!-- 年假基准 --><spring:message code="hr.viewPersonalInfo.title.nianjiajizhun"/></th>
				<th><!-- 产品 --><spring:message code="hrm.empinfo.PRODUCT"/></th>
				<th><!-- 促销员所属 --><spring:message code="hr.viewPersonalInfo.title.cuxiaoyuansuoshu"/></th>
				<th><!-- 星级级别 --><spring:message code="hr.viewPersonalInfo.title.xingjijibie"/></th>
				<th><!-- 是否兼卖 --><spring:message code="hr.viewPersonalInfo.title.shifoujianmai"/></th>
				<th><!-- 是否共建促销员 --> <spring:message code="hr.viewPersonalInfo.title.shifougongjiancuxiaoyuan"/></th>
				<th><!-- 评价类型 --> <spring:message code="hr.viewEvaluate.title.EV_TYPE_NAME"/></th>
				<th><!-- 错误原因 --><spring:message code="hrm.empinfo.ERROR_REASON"/></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${MDATA}" var="info" varStatus="i">
				<tr>
					<td>${i.count}</td>
					<td>${info.EMPID}</td>
					<td>${info.EMPNAME}</td>
					<td>${info.IDCARD_ADDR}</td>
					<td>${info.BORNPLACE_CODE}</td>
					<td>${info.NATION_CODE}</td>
					<td>${info.POLITY_CODE}</td>
					<td>${info.WHETHER_COMMUNIST}</td>
					<td>${info.HEIGHT}</td>
					<td>${info.WEIGHT}</td>
					<td>${info.BLOOD_TYPE}</td>
					<td>${info.DISABILITY_YN}</td>
					<td>${info.RECRUITMENT_SOURCE_TYPE}</td>
					<td>${info.LEAVE_REASON}</td>
					<td>${info.REMARK}</td>
					<td>${info.LOVE_FUND_PAYMENT_TYPE}</td>
					<td>${info.IF_PAYMENT_LOVE_FUND}</td>
					<td>${info.IF_PAYMENT_RENT}</td>
					<td>${info.IF_PAYMENT_MEDICAL}</td>
					<td>${info.IF_PAYMENT_EDUCATION}</td>
					<%-- <td>${info.EMP_TYPE_CODE}</td> --%>
					<td>${info.PROMTR_WORK_TP}</td>
					<%-- <td>${info.EMP_TYPE_START_DATE}</td> --%>
					<td>${info.INSRAREA_ID_NAME}</td>
					<td>${info.WORK_AREA}</td>
					<td>${info.MANUAL_NUM}</td>
					<td>${info.OUTER_WORK_YEAR}</td>
					<td>${info.INSRAREA_ID_INS_NAME}</td>
					<td>${info.INSURANCE_COMPANY}</td>
					<td>${info.INSURANCE_TYPE_CODE}</td>
					<td>${info.YY_VAC_STD_DATE}</td>
					<td>${info.PROD_TP}</td>
					<td>${info.PROMTR_TP}</td>
					<td>${info.STAR_TP}</td>
					<td>${info.PART_TIME_YN}</td>
					<td>${info.COMM_YN}</td>
					<td>${info.EVS_TYPE_NAME}</td>
					<td>${info.UPLOAD_ERROR_MSG}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<c:set value="/hrm/empinfo/importEmpInfoTempList" var="pageUrl" />
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>