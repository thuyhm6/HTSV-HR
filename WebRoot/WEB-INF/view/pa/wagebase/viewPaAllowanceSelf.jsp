<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
//excel导出
function exportExcel(a, navTabId) {
	var sform = document.getElementById("postSelfName");
	alertMsg.confirm("确定要导出么?", {
		okCall : function() {
			//用于excel导出的表单参数处理
			var eForm = document.getElementById("excelExportForm_self0102"); 
			document.getElementById("self0102").innerHTML = "EXCEL密码设置";
			eForm.CORPORATION.value		= sform.seach_CORPORATION.value;
			eForm.duty.value	        = sform.seach_duty.value;
			eForm.type.value 		    = sform.seach_type.value;
			eForm.noName.value 			= sform.seach_noName.value;
			$("#importExcelDialog_self0102").attr('href', "/sys/encryptExcel"
					+"?exportFunName=/pa/wagebase/viewPaAllowanceSelfExcel"
					+"&navTabId=pa0508"
					+"&formId=excelExportForm_self0102");
			$("#importExcelDialog_self0102").attr('width', "300");
			$("#importExcelDialog_self0102").attr('height', "150");
			$("#importExcelDialog_self0102").click();
		}
	});
}
</script>
<a id="importExcelDialog_self0102" href="#" target="dialog" mask="true">
	<span id="self0102" style="display: none"></span></a> 
<a id="importExcel_pa0221"  href="#" target="navTab" mask="true"><span style="display:none;">职责津贴个人导入结果</span></a>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/pa/wagebase/viewPaAllowanceSelf" method="post" rel="pagerForm" id="postSelfName">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						<%--法人--%><spring:message code="sys.essParam.title.legalPerson"/>
					</td>
					<td>
						<select name="seach_CORPORATION" <c:if test="${superUser eq '0'}">disabled</c:if>><option value="">请选择</option>
							<c:forEach items="${cpnyList}" var="cpny">
								<option value="${cpny.CONTENT}" <c:if test="${cpny.CONTENT eq CORPORATION}">selected</c:if>>${cpny.CONTENT}
							</c:forEach>
						</select>
					</td>
				    <td>
						<%--行号/姓名--%><spring:message code="public.title.empIdAndName"/>
					</td>
					<td>
						<input type="text" name="seach_noName" value="${noName}"/>
						<input type="hidden" id="seach_FIRST_FLAG" name="seach_FIRST_FLAG" value="1" />
					</td>
					<td>
						<%--职责--%><spring:message code="ess.infoApply.title.dutyName"/>
					</td>
					<td>
						<select name="seach_duty"><option value="">请选择</option>
							<c:forEach items="${positionList}" var="position">
								<option value="${position.POSITION}" <c:if test="${position.POSITION eq duty}">selected</c:if>>${position.POSITION}</option>
							</c:forEach>
						</select>
					</td>
					<td>在职状态</td>
						<td>
				 	<ait:SelectSyCodeByCpnyID id="seach_EmpOffice" name="seach_EmpOffice" parentNo="15118" selected="${EmpOffice}" cnpyID="${LoginUser.cpnyId}" limit="all"/>
						</td>
					
				</tr>
				<tr>
						<td>人员类型组 </td>
						<td>
							<input type="hidden" id="pa0509_limit" name="limit" value="pa">
							<input type="hidden" id="pa0509_seach_CPNY" name="seach_CPNY" value="${LoginUser.cpnyId}">
							<ait:SelectEmpTypeCode  id="pa0509_seach_JobTypeGroupNo"  name="seach_JobTypeGroupNo" selected="${JobTypeGroupNo}" limit="pa" type="group"
								onChangeName="ajaxEmpTypeForGroupToList(-1,pa0509_seach_JobTypeGroupNo,pa0509_seach_EmpTypeCodeNo,pa0509_seach_CPNY,pa0509_limit)"/>
						</td>
						<td>人员类型 </td>
						<td>
		 					<ait:SelectEmpTypeCode id="pa0509_seach_EmpTypeCodeNo" name="seach_type" selected="${type}" limit="pa"/>
						</td>	
				</tr>
				
			</table>
			<div class="subBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">
									<spring:message code="public.title.search"/><!--检索-->
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
	<c:set value="/pa/wagebase/updateViewPaAllowanceSelf?ALLOWANCE_ID={ALLOWANCE_ID}"
		var="edit_Url" />
	<c:set value="600" var="edit_width" />
	<c:set value="400" var="edit_height" />
	<c:set value="dialog" var="edit_tab" />
	<div class="formBar">
	<ul class="toolBar">
			<li>
				<a class="buttonActive"
					href="/pa/wagebase/exportPaAllowanceSelfExcelModule"><span>
					<spring:message code="pa.insurance.title.downloadImportTemplate"/><!--下载导入模板--></span>
				</a>
			</li>
			<li>
				<a class="buttonActive"
					href="/pa/excelImport/importExcelData?importFunName=/importPaAllowanceSelf" target="dialog" mask="true" width="500" height="200" >
					<span><spring:message code="ar.addempshift.title.excelimport"/><!-- EXCEL导入 --></span>
				</a>
			</li>
			<li>
				<a onClick="exportExcel(this,'pa0509');" href="#">
					<span><spring:message code="pa.insurance.title.excelExport"/><!--EXCEL导出  --></span>
				</a>
			</li>
		<c:if test="${toolbarInfo.UPDATER == '1'}">
			<c:if test="${edit_Url ne '' && edit_Url ne null}">
				<li id="editLi">
					<a class="edit"
						href="${edit_Url}"
						<c:if test="${edit_target_exit eq '' || edit_target_exit eq null }">
							target="${edit_tab eq '' || edit_tab eq null ? 'dialog' : edit_tab}"
						</c:if>
							mask="${edit_mask eq '' || edit_mask eq null ? 'true' : edit_mask }" 
							width="${edit_width eq '' || edit_width eq null ? '800' : edit_width}" 
							height="${edit_height eq '' || edit_height eq null ? '400' : edit_height}"
							<c:if test="${edit_rel ne '' && edit_rel ne null}">
								rel="${edit_rel}"
							</c:if>
							><span>
								<c:choose>
								   <c:when test="${edit_name eq '' || edit_name eq null}">
								     	<spring:message code="button.update" />
								   </c:when>
								   <c:otherwise>
								   		${edit_name}
								   </c:otherwise>
								</c:choose>
							</span>
					</a>
				</li>
			</c:if>
		</c:if>
	</ul>
</div>
	<table class="table" width="100%" layoutH="210">
		<thead>
			<tr>
				<th width="5%"><%--序号--%><spring:message code="ar.viewcycle.title.xuhao"/></th>
				<th width="5%">法人</th>
				<th width="10%"><%--社号--%><spring:message code="hr.viewPersonalInfo.title.EMPID"/></th>
				<th width="7%"><%--姓名--%><spring:message code="hr.viewPersonalInfo.title.LOCAL_NAME"/></th>
				<th width="10%">职责</th>
				<th width="10%">人员类型</th>
				<th width="8%">G数值</th>
				<th width="8%">C数值</th>
				<th width="7%"><%--支付比例(%)--%><spring:message code="pa.allowance.zhifubili"/>(%)</th>
				<th width="10%"><%--发令日期--%><spring:message code="display.emp.ben.transdate"/></th>
				<th width="7%"><%--有效期月数--%><spring:message code="pa.allowance.youxiaoqiyueshu"/></th>
				<th width="5%">启用状态</th>
				<th width="10%"><%--备注--%><spring:message code="ar.viewarcardrecord.title.beizhu"/></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${dataList}" var="item" varStatus="i">
				<tr target="ALLOWANCE_ID" rel="${item.ALLOWANCE_ID}">
					<td>${i.count}</td>
					<td>${item.CPNY_ID}</td>
					<td>${item.EMPID}</td>
					<td>${item.CHINESENAME}</td>
					<td>${item.POSITION_NO}</td>
					<td>${item.EMPTYPE}</td>
					<td>${item.POSITION_ALLOWANCE}</td>
					<td>${item.POSITION_ALLOWANCE_C}</td>
					<td>${item.PERCENT_ALLOWANCE}</td>
					<td>${item.CHANGE_DATE}</td>
					<td>${item.VALID_MONTH}</td>
					<td><img src="/resources/images/a_${item.ACTIVITY}.gif"></td>
					<td>${item.DEMO_ALLOWANCE }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<c:set value="/pa/wagebase/viewPaAllowanceSelf" var="pageUrl" />
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
<form id="excelExportForm_self0102" name="excelExportForm_self0102" method="post">
	<input type="hidden" id="password" 	name="password" value="" />
	<input type="hidden" id="CORPORATION" name="CORPORATION" value="" />
	<input type="hidden" id="duty" 	name="duty" value="" />
	<input type="hidden" id="type" 	name="type" value="" />
	<input type="hidden" id="noName" name="noName" value="" />
</form>
</div>