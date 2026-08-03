<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">

function downloadExl(url) {
	$("#ar_CountInfoList_tagss").attr("value",
			$("#viewarCountInfoReport_currentIndex").attr("value"));
	$('#arCountInfoReport').attr("action", url);
	$('#arCountInfoReport').attr("onsubmit", '');
	$('#arCountInfoReport').submit();
	$('#arCountInfoReport').attr("action", '/ar/countAttendance/arCountInfoReport');
	$('#arCountInfoReport').attr("onsubmit", 'return navTabSearcha(this);');
}
</script>
<div class="pageHeader">
	<form onsubmit="return navTabSearcha(this);"
		action="/ar/countAttendance/arCountInfoReport" method="post"
		id="arCountInfoReport" name="viewArPersonalList">
		<input type="hidden" name='CODE_NO' />
		<div class="searchBar">
			<table class="searchContent">
				<tr>

					<input type="hidden" id="currentIndex" value="0"
						name="currentIndex">

					<input type="hidden" value="0" name="report">


					<input type="hidden" id='ar_CountInfoList_tagss' value="0"
						name="ar_CountInfoList_tag">
					<td>
						<!-- 部门： -->
						<spring:message code="hr.viewPersonalInfo.title.DEPTNAME" />
					</td>
					<td>
						<ait:deptList name="seach_DEPT_NO" cpnyId="${defaultCpny}"
							limit="ar" id="arCountInfoReport_seachDept" />
						<ait:deptTreeIcon name="seach_DEPT_NO" cpnyId="${defaultCpny}"
							limit="ar" id="arCountInfoReport_seachDept" selected="${DEPTNO}" />
					</td>
					<td>
						<!-- 社号/姓名： -->
						<spring:message
							code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME" />
					</td>
					<td>

						<input type="text" name="seach_KEY" id="arCountInfoReport_seachKey"
							value="${KEY}" />

						<input type="hidden" name='CPNY' value="${LoginUser.cpnyId}">


					</td>

				</tr>
				<tr>
					<td>

						<input type="hidden" format="yyyy/MM/dd" id="STIMESS"
							value="${STIME}" />
						<input type="hidden" format="yyyy/MM/dd" id="ETIMESS"
							value="${ETIME}" />
						<!-- 开始日期 -->
						<spring:message code="public.title.startDate" />
					</td>
					<td>
						<input type="text" id="arCountInfoReport_seach_STIME"
							name="seach_STIME" value="${STIME}" class="Wdate"
							onClick="WdatePicker({dateFmt:'yyyy/MM/dd'})" />
					</td>
					<td>
						<!-- 结束日期 -->
						<spring:message code="public.title.endDate" />
					</td>
					<td style="position: relative; overflow: hidden">
						<input type="text" id="arCountInfoReport_seach_ETIME"
							name="seach_ETIME" class="Wdate"
							onClick="WdatePicker({dateFmt:'yyyy/MM/dd'})" value="${ETIME}" />
					</td>


					<td>
						<!-- 班组 --><spring:message code="hr.viewPersonalInfo.title.banzu" />
					</td>
					<td>
						<ait:SelectSyCodeByCpnyID name="seach_GROUP"
							id="arCountInfoReport_GROUP" limit="all" parentNo="400223"
							cnpyID="${LoginUser.cpnyId}" />
					</td>
					<td>
						<!-- 审批状态 --><spring:message code="ess.infoApply.approval_status" />
					</td>
					<td>

						<select name="seach_AFFIRM_FLAG"
							id="arCountInfoReport_AFFIRM_FLAG">
							<option value="0">
								<!-- 全部 --><spring:message code="org.title.ALL" />
							</option>
							<option value="1" selected="selected">
								<!-- 部门长批准 --><spring:message code="hr.contract.title.shenpi" />
							</option>
							<option value="2">
								<!-- 部门申请 --><spring:message code="ar.viewAdjustLeaveTSTOBatchList.SHENQING.b" />
							</option>


						</select>


					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>


					<li>
						<div>
							<div class="buttonContent">
								<a class="buttonActive"
									onclick="downloadExl('/ar/countAttendance/arCountInfoSonList_Export')"
									href="#"> <span><!-- 导出到EXECL --><spring:message code="ess.infoApply.export_to_Excel" /></span> </a>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</div>
	</form>
</div>
