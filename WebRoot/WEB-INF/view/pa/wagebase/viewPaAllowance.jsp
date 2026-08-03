<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
//excel导出
function exportSalesmanEvalInfo(a, navTabId) {
	var sform = document.getElementById("postName");
	alertMsg.confirm("确定要导出么?", {
		okCall : function() {
			//用于excel导出的表单参数处理
			var eForm = document.getElementById("excelExportForm_allow0102"); 
			document.getElementById("allow0102").innerHTML = "EXCEL密码设置";
			eForm.CORPORATION.value		= sform.seach_CORPORATION.value;
			eForm.DEPTNO.value	= sform.seach_DEPTNO.value;
			eForm.POSITION.value 		= sform.seach_POSITION.value;
			eForm.PERSON.value 			= sform.seach_PERSON.value;
			$("#importExcelDialog_allow0102").attr('href', "/sys/encryptExcel"
					+"?exportFunName=/pa/wagebase/viewPaAllowanceExcel"
					+"&navTabId=pa0508"
					+"&formId=excelExportForm_allow0102");
			$("#importExcelDialog_allow0102").attr('width', "300");
			$("#importExcelDialog_allow0102").attr('height', "150");
			$("#importExcelDialog_allow0102").click();
		}
	});
}
</script>
<a id="importExcelDialog_allow0102" href="#" target="dialog" mask="true">
	<span id="allow0102" style="display: none"></span></a> 
<a id="importExcel_pa0220"  href="#" target="navTab" mask="true"><span style="display:none;">导入结果</span></a>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/pa/wagebase/viewPaAllowance" method="post" rel="pagerForm" id="postName">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						<%--法人--%><spring:message code="sys.essParam.title.legalPerson"/>：
						<select name="seach_CORPORATION" <c:if test="${superUser eq '0'}">disabled</c:if>><option value="">请选择</option>
							<c:forEach items="${cpnyList}" var="cpny">
								<option value="${cpny.CONTENT}" <c:if test="${cpny.CONTENT eq CORPORATION}">selected</c:if>>${cpny.CONTENT}
							</c:forEach>
						</select>
					</td>
					<td>
						<%--部门--%><spring:message code="ar.attendanceView.viewNoSwipingCard.deptName"/>：
						<ait:deptList name="seach_DEPTNO" id="addViewPa"/>
						<ait:deptTreeIcon name="seach_DEPTNO" id="addViewPa" selected="${DEPTNO}"/>
					</td>
					<td>
						<%--职责--%><spring:message code="ess.infoApply.title.dutyName"/>：
						<select name="seach_POSITION">
							<option value="">请选择</option>
							<c:forEach items="${positionList}" var="position">
								<option value="${position.POSITION}" <c:if test="${position.POSITION eq POSITION}">selected</c:if>>${position.POSITION}
							</c:forEach>
						</select>
					</td>
					<td>
						<%--人员类型--%><spring:message code="is.company.title.PERSON_TYPE"/>：
						<select name="seach_PERSON">
							<option value="">请选择</option>
							<c:forEach items="${personType}" var="person">
								<option value="${person.CODE_NO}" <c:if test="${person.CODE_NO eq PERSON}">selected</c:if>>${person.CONTENT}
							</c:forEach>
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
	<c:set value="/pa/wagebase/addPaAllowance" var="add_Url" />
	<c:set value="600" var="add_width" />
	<c:set value="320" var="add_height" />
	<c:set value="dialog" var="target" />
	<c:set value="/pa/wagebase/deletePaAllowance?ALLOWANCE_ID={ALLOWANCE_ID}" var="delete_Url" />
	<c:set value="/pa/wagebase/addPaAllowance?ALLOWANCE_ID={ALLOWANCE_ID}" var="edit_Url" />
	<c:set value="600" var="edit_width" />
	<c:set value="400" var="edit_height" />
	<c:set value="dialog" var="edit_tab" />
	
	<div class="formBar">
	<ul class="toolBar">
		<li>
		<a class="buttonActive"
			href="/pa/wagebase/exportPaAllowanceExcelModule"><span>
			<spring:message code="pa.insurance.title.downloadImportTemplate"/><!--下载导入模板--></span>
		</a>	
		</li>
		<li>
		<a class="buttonActive"
			href="/pa/excelImport/importExcelData?importFunName=/importPaAllowance" target="dialog" mask="true" width="500" height="200" >
			<span><spring:message code="ar.addempshift.title.excelimport"/><!-- EXCEL导入 --></span>
		</a>
		</li>
		<li>
		<a onClick="exportSalesmanEvalInfo(this,'pa0508');">
			<span><spring:message code="pa.insurance.title.excelExport"/><!--EXCEL导出  --></span>
		</a>
		</li>
		 <c:if test="${toolbarInfo.INSERTR == '1'}">
			 <c:if test="${init_Url ne '' && init_Url ne null}">
				<li id="addLi">
					<a class="add"
						href="${init_Url}"
						<c:if test="${init_target_exit eq '' || init_target_exit eq null }">
							target="${init_tab eq '' || init_tab eq null ? 'ajaxTodo' : init_tab}"
						</c:if>
						<c:if test="${init_range ne '' && init_range ne null }">
							width="${init_width eq '' || init_width eq null ? '500' : init_width}" 
							height="${init_height eq '' || init_height eq null ? '400' : init_height}"
						</c:if>
						title="
							<c:choose>
							   <c:when test="${init_title eq '' || init_title eq null}">
							     	<spring:message code="button.init.sure" />
							   </c:when>
							   <c:otherwise>
							   		${init_title}
							   </c:otherwise>
							</c:choose>
						">
						<span>
							<c:choose>
							   <c:when test="${init_name eq '' || init_name eq null}">
							     	<spring:message code="button.init" />
							   </c:when>
							   <c:otherwise>
							   		${init_name}
							   </c:otherwise>
							</c:choose>
						</span>
					</a>
				</li>
			</c:if>
		</c:if>
		
		<c:if test="${toolbarInfo.INSERTR == '1'}">
			 <c:if test="${add_Url ne '' && add_Url ne null}">
				<li id="addLi">
				 
					<a class="add" href="${add_Url}"
						<c:if test="${add_target_exit eq '' || add_target_exit eq null }">
							target="${add_tab eq '' || add_tab eq null ? 'dialog' : add_tab}" 
						</c:if>
							mask="${add_mask eq '' || add_mask eq null ? 'true' : add_mask }" 
							width="${add_width eq '' || add_width eq null ? '800' : add_width}" 
							height="${add_height eq '' || add_height eq null ? '400' : add_height}"
							<c:if test="${add_rel ne '' && add_rel ne null}">
								rel="${add_rel}"
							</c:if>
							>
							<span>
								<c:choose>
								   <c:when test="${add_name eq '' || add_name eq null}">
								     	<spring:message code="button.add" />
								   </c:when>
								   <c:otherwise>
								   		${add_name}
								   </c:otherwise>
								</c:choose>
							</span>
					</a>
				</li>
			 </c:if>
		</c:if>
		
		<c:if test="${toolbarInfo.DELETER == '1'}">
			<c:if test="${delete_Url ne '' && delete_Url ne null}">
				<li id="deleteLi">
					<a class="delete" href="${delete_Url}"
						<c:if test="${delete_target_exit eq '' || delete_target_exit eq null }">
							target="${delete_tab eq '' || delete_tab eq null ? 'ajaxTodo' : delete_tab}"
						</c:if>
						<c:if test="${delete_mask_exit ne '' && delete_mask_exit ne null }">
							mask="${delete_mask eq '' || delete_mask eq null ? 'true' : delete_mask }" 
						</c:if>
						<c:if test="${delete_range ne '' && delete_range ne null }">
							width="${delete_width eq '' || delete_width eq null ? '500' : delete_width}" 
							height="${delete_height eq '' || delete_height eq null ? '400' : delete_height}"
						</c:if>
						 title="
						 	<c:choose>
							   <c:when test="${delete_title eq '' || delete_title eq null}">
							     	<spring:message code="button.delete.sure" />
							   </c:when>
							   <c:otherwise>
							   		${delete_title}
							   </c:otherwise>
							</c:choose>
						 ">
						<span>
							<c:choose>
							   <c:when test="${delete_name eq '' || delete_name eq null}">
							     	<spring:message code="button.delete" />
							   </c:when>
							   <c:otherwise>
							   		${delete_name}
							   </c:otherwise>
							</c:choose>
						</span>
					</a>
				</li>
			</c:if>
		</c:if>
		
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
				<th width="10%"><%--序号--%><spring:message code="ar.viewcycle.title.xuhao"/></th>
				<th width="18%"><%--法人--%><spring:message code="sys.essParam.title.legalPerson"/></th>
				<th width="18%"><%--部门--%><spring:message code="ar.attendanceView.viewNoSwipingCard.deptName"/></th>
				<th width="18%"><%--职责--%><spring:message code="ess.infoApply.title.dutyName"/></th>
				<th width="18%"><%--人员类型--%><spring:message code="is.company.title.PERSON_TYPE"/></th>
				<th width="18%"><%--数值--%><spring:message code="pa.insurance.title.dataValue"/></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${paBasicItemList}" var="item" varStatus="i">
				<tr target="ALLOWANCE_ID" rel="${item.ALLOWANCE_ID}">
					<td>${i.count}</td>
					<td>${item.CPNY_NAME}</td>
					<td>${item.DEPT_ID}</td>
					<td>${item.DUTY_ALLOWANCE}</td>
					<td>${item.TYPE_ALLOWANCE}</td>
					<td>${item.POSITION_ALLOWANCE}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<c:set value="/pa/wagebase/viewPaAllowance" var="pageUrl" />
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
<form id="excelExportForm_allow0102" name="excelExportForm_allow0102" method="post">
	<input type="hidden" id="password" 	name="password" value="" />
	<input type="hidden" id="CORPORATION" name="CORPORATION" value="" />
	<input type="hidden" id="DEPTNO" 	name="DEPTNO" value="" />
	<input type="hidden" id="POSITION" 	name="POSITION" value="" />
	<input type="hidden" id="PERSON" 	name="PERSON" value="" />
</form>
</div>