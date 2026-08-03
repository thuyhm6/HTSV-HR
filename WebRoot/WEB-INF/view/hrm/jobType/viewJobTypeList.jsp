<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function importJobTypeData(){
	$("#importExcelDialog_hr0034").attr('href','/pa/excelImport/importExcelData?importFunName=/importJobTypeData');
	$("#importExcelDialog_hr0034").click();
}

$(document).ready(function() {
		var message = "${message}";
		if (message != '') {
			alertMsg.info("${message}");
		}
	});
function downloadImportTemplate_jobtype() {//下载导入模板
        var form = document.getElementById("pageForm_hr0034");
        form.action="/hrm/jobType/downloadTempleteJobType";
        form.submit();
		//document.form.action = "/hrm/jobType/downloadTempleteJobType";
		//document.form.submit();
	}
	
	//删除数据，可进行批量的删除或者单一的删除
	function deleteData_hr0034(form) {
		var flag = false;

		var $form = null;
		if ($('#' + form).length > 0)
			$form = $('#' + form);
		else
			$form = $(form);

		var cs = document.getElementsByName("check_hr0034");
		for ( var i = 0; i < cs.length; i++) {
			if (cs[i].checked == true) {
				flag = true;
			}
		}
		if (flag) {
			var result = confirm("确定要删除么？");
			if (result == true) {
			$form.attr("action", "/hrm/jobType/deleteJobTypeInfo");
				$.ajax({
							type :'POST',
							url : $form.attr("action"),
							async : false,
							data : $form.serializeArray(),
							dataType : "json",
							cache : false,
							success : function(data) { //请求成功后处理函数。
								if (data.statusCode == "200") {
									alertMsg.correct(data.message);
									navTabNum(
											'/hrm/jobType/viewJobTypeList?pageNum=1&menuNo=125248&navTabId=hr0034',
											'人员类型设置');
								} else if (data.statusCode == "300") {
									alertMsg.info(data.message);
								}
							}
						});
			} else {
				return false;
			}
		} else {
			alertMsg.info('请选择删除项！');

		}
	}
	
</script>
<div style="display: none">
<a id="importExcelDialog_hr0034" rel="update" mask="true" width="600"
	height="400" target="dialog" />
<a id="importExcel_hr0034" href="#" target="navTab" mask="true">查看导入</a>
</div>
<div class="pageHeader">
	<form id="pageForm_hr0034" onsubmit="return navTabSearch(this);"
		action="/hrm/jobType/viewJobTypeList" method="post" rel="pagerForm">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					 <td>法人:
				</td>
			<td>
				<c:if test="${authority eq '1'}">
				
				<select id="seach_CPNY_ID" name="defaultCpny" onchange="reloadPage();">
				    <option value="">全部</option>
					<c:forEach items="${companyList}" var="item" varStatus="i">
						<option value="${item.CPNY_ID }" <c:if test="${defaultCpny eq item.CPNY_ID}">selected</c:if>>${item.CPNY_ID }</option>
					</c:forEach>
				</select>
				</c:if>
				<c:if test="${authority eq '0'}">
					<input type="text" id="seach_CPNY_ID" name="seach_defaultCpny" value="${defaultCpny}" readonly="readonly"/>
						</c:if>
	   			 </td>

					<td>
						<!--人员类型组--> <spring:message
							code="hrm.jobType.title.JOBTYPE_GROUP_NAME" /></td>

					<td><input type="text" name="seach_JOBTYPE_GROUP_NAME"
						value="${JOBTYPE_GROUP_NAME }" /></td>
					<td>
						<!--人员类型--> <spring:message code="is.company.title.PERSON_TYPE" />
					</td>

					<td><input type="text" name="seach_JOBTYPE_NAME"
						value="${JOBTYPE_NAME }" /></td>
				</tr>
			</table>
			<div class="subBar">
				<ul>

					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">
									<!-- 查询 -->
									<spring:message code="button.search" />
								</button>
							</div>
						</div></li>
				</ul>
			</div>
		</div>

	<c:set value="dialog" var="add_tab" />
	<c:set value="600" var="add_width" />
	<c:set value="500" var="add_height" />
	<c:set value="/hrm/jobType/addJobTypeView" var="add_Url" />
	<c:set value="dialog" var="edit_tab" />
	<c:set value="600" var="edit_width" />
	<c:set value="500" var="edit_height" />
	<c:set value="/hrm/jobType/updateJobTypeView?NO={sid}&NO={sid}"
		var="edit_Url" />
	<div class="formBar">
		<ul class="toolBar">
			<li>
				<div class="buttonActive">
					<div class="buttonContent">
						<button type="button" id="exportExcel"
							onclick="downloadImportTemplate_jobtype();">
							<spring:message code="pa.insurance.title.downloadImportTemplate" />
							<!--下载导入模板-->
						</button>
					</div>
				</div></li>
			<li><a class="buttonActive" onclick="importJobTypeData()"> <span><spring:message
							code="ar.addempshift.title.excelimport" /> <!-- EXCEL导入 --> </span> </a></li>
			<c:if test="${toolbarInfo.INSERTR == '1'}">
				<c:if test="${init_Url ne '' && init_Url ne null}">
					<li id="addLi"><a class="add" href="${init_Url}"
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
							<span> <c:choose>
									<c:when test="${init_name eq '' || init_name eq null}">
										<spring:message code="button.init" />
									</c:when>
									<c:otherwise>
							   		${init_name}
							   </c:otherwise>
								</c:choose> </span> </a></li>
				</c:if>
			</c:if>

			<c:if test="${toolbarInfo.INSERTR == '1'}">
				<c:if test="${add_Url ne '' && add_Url ne null}">
					<li id="addLi"><a class="add" href="${add_Url}"
						<c:if test="${add_target_exit eq '' || add_target_exit eq null }">
							target="${add_tab eq '' || add_tab eq null ? 'dialog' : add_tab}" 
						</c:if>
						mask="${add_mask eq '' || add_mask eq null ? 'true' : add_mask }"
						width="${add_width eq '' || add_width eq null ? '800' : add_width}"
						height="${add_height eq '' || add_height eq null ? '400' : add_height}"
						<c:if test="${add_rel ne '' && add_rel ne null}">
								rel="${add_rel}"
							</c:if>>
							<span> <c:choose>
									<c:when test="${add_name eq '' || add_name eq null}">
										<spring:message code="button.add" />
									</c:when>
									<c:otherwise>
								   		${add_name}
								   </c:otherwise>
								</c:choose> </span> </a></li>
				</c:if>
			</c:if>

			<c:if test="${toolbarInfo.DELETER == '1'}">
				<c:if test="${delete_Url ne '' && delete_Url ne null}">
					<li id="deleteLi"><a class="delete" href="${delete_Url}"
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
							<span> <c:choose>
									<c:when test="${delete_name eq '' || delete_name eq null}">
										<spring:message code="button.delete" />
									</c:when>
									<c:otherwise>
							   		${delete_name}
							   </c:otherwise>
								</c:choose> </span> </a></li>
				</c:if>
			</c:if>

			<c:if test="${toolbarInfo.UPDATER == '1'}">
				<c:if test="${edit_Url ne '' && edit_Url ne null}">
					<li id="editLi"><a class="edit" href="${edit_Url}"
						<c:if test="${edit_target_exit eq '' || edit_target_exit eq null }">
							target="${edit_tab eq '' || edit_tab eq null ? 'dialog' : edit_tab}"
						</c:if>
						mask="${edit_mask eq '' || edit_mask eq null ? 'true' : edit_mask }"
						width="${edit_width eq '' || edit_width eq null ? '800' : edit_width}"
						height="${edit_height eq '' || edit_height eq null ? '400' : edit_height}"
						<c:if test="${edit_rel ne '' && edit_rel ne null}">
								rel="${edit_rel}"
							</c:if>><span>
								<c:choose>
									<c:when test="${edit_name eq '' || edit_name eq null}">
										<spring:message code="button.update" />
									</c:when>
									<c:otherwise>
								   		${edit_name}
								   </c:otherwise>
								</c:choose> </span> </a></li>
				</c:if>
			</c:if>
			<li>
				<div class="buttonActive">
					<div class="buttonContent">

						<button type="button"
							onclick="deleteData_hr0034('pageForm_hr0034');">
							<spring:message code="button.delete" />
							<!--删除-->
						</button>
					</div>
				</div></li>
		</ul>
	</div>

	<table class="table" width="100%" layoutH="206">
		<thead>
			<tr>
				<th width="50"><input type="checkbox" name="c1_bx0103_c"
					id="c1_bx0103_c" class="checkboxCtrl" group="check_hr0034">
				</th>
				<th width="100">
					<!-- 序号--> <spring:message
						code="hr.viewPersonalInfo.title.TRADEUNION_NUM" /></th>
				<th>
					<!-- 公司名称 --> <spring:message
						code="ar.viewcycleparameter.title.gongsimingcheng" /></th>
				<th>
					<!--人员类型组--> <spring:message
						code="hrm.jobType.title.JOBTYPE_GROUP_NAME" /></th>
				<th>
					<!--人员类型--> <spring:message code="is.company.title.PERSON_TYPE" />
				</th>
				<th>
					<!-- 状态 --> <spring:message code="ar.viewcycle.title.zhuangtai" />
				</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${jobTypeList}" var="parameter" varStatus="i">

				<tr align="center" onclick="band('#f4f7fa','black')" target="sid"
					rel="${parameter.JOB_TYPE_SETUP_NO}">
					<td class="td_center" style="white-space:nowrap"><input
						type="checkbox" id="check_hr0034" name="check_hr0034"
						value="${parameter.JOB_TYPE_SETUP_NO}" /></td>
					<td style="white-space:nowrap">${i.index + 1}&nbsp;</td>
					<td>${parameter.CPNY_NAME}</td>
					<td>${parameter.JOBTYPE_GROUP_NAME}</td>
					<td>${parameter.JOBTYPE_NAME}</td>
					<td><img src="/resources/images/a_${parameter.ACTIVITY}.gif">
					</td>
				</tr>
				<input type="hidden" name="CPNY_ID" value="${parameter.CPNY_ID }" />
			</c:forEach>
		</tbody>
	</table>
	</form>
	<c:set value="/hrm/jobType/viewJobTypeList" var="pageUrl" />
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>

</div>
