<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
$(document).ready(function() {
	var message = "${message}";
	if (message != '') {
		alertMsg.info("${message}");
	}
});

//导入数据
function importObjManageFundNum() {

	$("#importExcel")
			.attr('href',
					'/pa/excelImport/importExcelData?importFunName=/importObjManageFundNum');
	$("#importExcel").click();
}
//下载导入模板
function downloadImportStop() {
	document.InstanceBaseNumList.action = "/pa/excelExport/downloadInstanceNum";
	document.InstanceBaseNumList.submit();

}
</script>
<a id="updateid_bx0205" rel="updateBenshObjectManage" mask="true"
	width="1200" height="400" target="dialog"></a>
<a id="orderid_bx0205" rel="orderBenshObjectManage" mask="true"
	width="1200" height="400" target="dialog"></a>
<form name="searchForm_bx0205a" id="searchForm_bx0205a" method="post">
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr align="center">
					<td>
						<table width="100%" border="0" cellpadding="0" class="table_list">
							<tr>
								<td class="info_title_01">
									<!--缴纳月份 --><spring:message code="is.objmanagement.title.paymonth"/>
								</td>
								<td class="info_content_00">
									<ait:date yearName="year" monthName="month" yearPlus="10"
										yearSelected="${year}" monthSelected="${month}" yearMinus="10"/>
								</td>
								<td class="info_title_01">
									<!--开始缴纳月 --><spring:message code="is.joininstance.title.startwithmonth"/>
								</td>
								<td class="info_content_00">
									<ait:date yearName="year" monthName="month"
										yearSelected="${show.START_YEAR}"
										monthSelected="${show.START_MONTH}" yearPlus="10"
										yearMinus="10" />
								</td>
								<td class="info_title_01">
									<!--停止缴纳月 --><spring:message code="display.emp.statistics.mes224"/>
								</td>
								<td class="info_content_00">
									<ait:date yearName="endYear" monthName="endMonth"
										yearSelected="${show.START_YEAR}"
										monthSelected="${show.START_MONTH}" yearPlus="10"
										yearMinus="10" />
								</td>
								<td class="info_title_01">
									<!-- 社保状态 -->
									<spring:message code="is.objmanagement.title.socialstate" />
								</td>
								<td class="info_content_00">
									<select name="socialStatus">
										<option value="">
											全部
										</option>
										<option value="LAST">
											在保
										</option>
										<option value="STOP">
											停保
										</option>
										<option value="JOIN">
											新参保
										</option>
										<option value="FUCK">
											本月应缴
										</option>
									</select>
								</td>
							</tr>
							<tr>
								<td class="info_title_01">
									<!-- 社保号码为空 -->
									<spring:message code="is.objmanagement.title.socialnumisnull" />
								</td>
								<td class="info_content_00">
									<select name="socialNoNull">
										<option value="">
											不筛选
										</option>
										<option value="Y">
											筛选
										</option>
									</select>
								</td>
								<td class="info_title_01">
									<!-- 职系 -->
									<spring:message code="is.objmanagement.title.zhixi" />
								</td>
								<td class="info_content_00">
									<select>
										<option>
											全部
										</option>
										<option>
											事务职
										</option>
										<option>
											监督职
										</option>
										<option>
											技能职
										</option>
									</select>
								</td>
								<td class="info_title_01">
									<!-- 在职状态 -->
									<spring:message code="display.emp.statistics.mes204" />
								</td>
								<td class="info_content_00">
									<select>
										<option>
											在职
										</option>
										<option>
											离职
										</option>
									</select>
								</td>
								<td class="info_title_01">
									<!-- 户口性质 -->
									<spring:message code="hr.viewPersonalInfo.title.REG_TYPE_NAME" />
								</td>
								<td class="info_content_00">
									<select>
										<option>
											外地城镇
										</option>
										<option>
											外地农村
										</option>
										<option>
											其他
										</option>
									</select>
								</td>
							</tr>
							<tr>
								<td class="info_title_01">
									<spring:message code="public.title.deptName" />
									<%--部门--%>
								</td>
								<td class="info_content_00">
									<ait:deptTree name="seach_DEPT_NO" limit="hr"
										selected="${DEPT_NO }" />
								</td>
								<td class="info_title_01">
									<!-- 职号/姓名 -->
									<spring:message code="is.objmanagement.title.numorname" />
								</td>
								<td class="info_content_00">
									<input id="empID" name="empID"
										value="<c:out value='${basic.EMPID}'/>"
										onkeyup="SearchContent(this.value,this.id)" size="10" />
									<%--title='<ait:message  messageID="alert.emp.staff_info.basic_info.search_name" module="hrm" />'--%>
									<span id="empName">${basic.LOCAL_NAME}</span>
								</td>
								<td class="info_title_01">
									<!-- 身份证号码 -->
									<spring:message code="ess.personalinfo.title.IDCardNo" />
								</td>
								<td class="info_content_00">
									<input type="text" size="20" name="cardID">
								</td>
								<td class="info_title_01">
									<!-- 社会保险号码 -->
									<spring:message code="is.objmanagement.title.socialInsureNum" />
								</td>
								<td class="info_content_00">
									<input type="text" size="20" name="searchSocialNo">
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">
									<!--  搜索-->
									<spring:message code="ar.viewempcalender.title.search" />
								</button>
							</div>
						</div>
					</li>
					<li>
						<li>
							<div class="buttonActive">
								<div class="buttonContent">
									<button type="button" id="exportExcel"
										onclick="downloadImportStop();">
										<spring:message
											code="pa.insurance.title.downloadImportTemplate" />
										<!--下载导入模板-->
									</button>
								</div>
							</div>
						</li>
						<li>
							<div class="buttonActive">
								<div class="buttonContent">
									<a style="float: right;" class="buttonActive" id="importExcel"
										onclick="importObjManageFundNum();" href="#" target="dialog"><span><spring:message
												code="ar.addempshift.title.excelimport" /> <!--Excel导入--> </span>
									</a>
								</div>
							</div>
						</li>
						<li>
							<div class="buttonActive">
								<div class="buttonContent">

									<button type="button" onclick=""
										title="<spring:message code='rp.report.title.exportYN'/>">
										<spring:message code="ar.addempshift.title.excelexport" />
										<!--Excel导出-->
									</button>
								</div>
							</div>
						</li>
						<li>
							<div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="">
										<spring:message code="button.update" />
										<!--修改-->
									</button>
								</div>
							</div>
						</li>
				</ul>
			</div>
		</div>
	</div>

	<div class="pageContent">
		<table class="table" width="101.8%" layoutH="160">
			<thead>
				<tr>
					<th width="50">
						<input type="checkbox" name="c1_bx0104_c" id="c1_bx0104_c"
							class="checkboxCtrl" group="check">
					</th>
					<th width="50">
						<!-- 序号-->
						<spring:message code="hr.viewPersonalInfo.title.TRADEUNION_NUM" />
					</th>
					<th width="100">
						<!-- 部门-->
						<spring:message code="public.title.deptName" />
					</th>
					<th width="100">
						<!-- 职号-->
						<spring:message code="display.emp.statistics.mes209" />
					</th>
					<th width="80">
						<!-- 姓名-->
						<spring:message code="public.title.name" />
					</th>
					<th width="80">
						<!-- 社保状态 -->
						<spring:message code="is.objmanagement.title.socialstate" />
					</th>

					<th width="130">
						<!-- 社会保险号码 -->
						<spring:message code="is.objmanagement.title.socialInsureNum" />
					</th>
					<th width="100">
						<!-- 入社基数-->
						<spring:message code="is.joininstance.title.basenum" />
					</th>
					<th width="100">
						<!-- 年度基数-->
						<spring:message code="display.emp.statistics.mes208" />
					</th>
					<th width="100">
						<!-- 标记-->
						<spring:message code="is.joininstance.title.remarking" />
					</th>

					<th width="50">
						<!-- 养老 -->
						<spring:message code="is.joininstance.title.yanglao" />
					</th>
					<th width="50">
						<!-- 医疗 -->
						<spring:message code="is.joininstance.title.yiliao" />
					</th>
					<th width="50">
						<!-- 生育 -->
						<spring:message code="is.joininstance.title.shengyu" />
					</th>
					<th width="50">
						<!-- 工伤 -->
						<spring:message code="is.joininstance.title.gongshang" />
					</th>
					<th width="50">
						<!-- 失业 -->
						<spring:message code="is.joininstance.title.shiye" />
					</th>
			</thead>
			<tbody>
				<c:forEach items="${manageList}" var="show" varStatus="i">
					<tr align="center" onclick="band('#f4f7fa','black')">
						<td class="td_center" style="white-space: nowrap">
							<input type="checkbox" name="check"	value="${show.PA_BEN_MANAGE_SEQ}" />
						</td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</form>
