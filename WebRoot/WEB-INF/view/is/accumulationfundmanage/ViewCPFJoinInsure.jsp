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

	function save_bx0203() {//修改
		var flag = false;
		var cs = document.getElementsByName("checkid");
		var ids = "";
		for ( var i = 0; i < cs.length; i++) {
			if (cs[i].checked == true) {
				ids += cs[i].value + ",";
				flag = true;
			}
		}
		if (flag) {
			$("#updateid_bx0203").attr(
					"href",
					"/is/accumulationfundmanage/updateBenshObjectManage?ids="
							+ ids);
			$("#updateid_bx0203").click();
			//$.pdialog.open("/is/accumulationfund/updateCPFBaseManagement?ids="+ids, "updateCPFBaseManagement", "基数管理-修改", "mask:true,width:100px,height:100px");

		} else {
			alert('请选择修改项！');
		}
	}

	function band(backColor, textColor) {
		if (typeof (preEl) != 'undefined') {
			preEl.bgColor = orgBColor;

			try {
				ChangeTextColor(preEl, orgTColor);
			} catch (e) {
				;
			}
		}
		var el = event.srcElement;
		el = el.parentElement;
		orgBColor = el.bgColor;
		orgTColor = el.style.color;
		el.bgColor = backColor;
		try {
			ChangeTextColor(el, textColor);
		} catch (e) {
			;
		}
		preEl = el;
		//document.form1.vacation_no.value=i;
	}
	function downloadImportTemplate() {//下载导入模板
		document.searchForm.action = "/is/accumulationfundmanage/downloadTemplete";
		document.searchForm.submit();
	}

	//搜索
	function doSearch(form) {

		var $form = null;
		if ($('#' + form).length > 0)
			$form = $('#' + form);
		else
			$form = $(form);
		$form.attr("action", "/is/accumulationfundmanage/ViewCPFJoinInsure");
		$
				.ajax({
					type : form.method || 'POST',
					url : $form.attr("action"),
					async : false,
					dataType : "json",
					cache : false,
					success : function(data) { //请求成功后处理函数。
						//alert(data.statusCode);
						if (data.statusCode == "200") {
							navTabNum(
									'/is/accumulationfundmanage/ViewCPFJoinInsure?pageNum=1&menuNo=124913&navTabId=bx0203&method=create',
									'bx0203', '对象增加');
						} else if (data.statusCode == "300") {
							alertMsg.info(data.message);
						}
					}
				});

		//document.getElementById("searchForm").submit();
	}

	// 		function deleteDate(){//移除选中对象
	//			"确定要删除吗?"
	// 			var flag = false;
	// 			var cs = document.getElementsByName("checkid");
	// 			for ( var i = 0; i < cs.length; i++) {
	// 				if(cs[i].checked == true){
	// 					flag = true;
	// 				}
	// 			}
	// 			if(flag){
	//				var result = confirm("确定要移除该对象么？");
	// 				var result = confirm('<spring:message code="display.mutual.duixiangzengjiadel"/>');
	// 				if(result == true){
	// 					document.searchForm.action="/is/accumulationfundmanage/deleteBenshObjectManageAdd";
	// 					document.searchForm.submit();
	// 				}else{
	// 					return false;
	// 				}
	// 			}else{
	//				alert('请选择移除对象！');
	// 				alert('<spring:message code="display.mutual.duixiangzengjiadel"/>');
	// 			}
	// 		}

	function deleteDate_bx0203(form) {//删除
		//"确定要删除吗?"
		// document.getElementById('logtype').value="DELETE" ;
		var flag = false;

		var $form = null;
		if ($('#' + form).length > 0)
			$form = $('#' + form);
		else
			$form = $(form);

		var cs = document.getElementsByName("checkid");
		for ( var i = 0; i < cs.length; i++) {
			if (cs[i].checked == true) {
				flag = true;
			}
		}

		if (flag) {
			//var result = confirm("确定要删除么？");
			var result = confirm("确定要删除么？");
			if (result == true) {
				$form
						.attr("action",
								"/is/accumulationfundmanage/deleteBenshObjectManageAdd");

				$
						.ajax({
							type : form.method || 'POST',
							url : $form.attr("action"),
							async : false,
							data : $form.serializeArray(),
							dataType : "json",
							cache : false,
							success : function(data) { //请求成功后处理函数。
								if (data.statusCode == "200") {
									alertMsg.correct(data.message);
									navTabNum(
											'/is/accumulationfundmanage/ViewCPFJoinInsure?pageNum=1&menuNo=124913&navTabId=bx0203',
											'bx0203', '对象增加');
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

	function importManageAddFundNum() {
		$("#importExcel")
				.attr('href',
						'/pa/excelImport/importExcelData?importFunName=/importManageAddFundNum');
		$("#importExcel").click();

	}
</script>

<a id="updateid_bx0203" rel="updateBenshObjectManage" mask="true"
	width="1200" ,height="400" target="dialog"></a>
<a id="orderid_bx0203" rel="orderCPFBaseManagement" mask="true"
	width="1200" ,height="400" target="dialog"></a>
<form name="searchForm_bx0203a" id="searchForm_bx0203a" method="post">
	<div class="pageHeader">
		<div class="searchBar">
			<div class="subBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">

								<button type="button" onclick="doSearch('searchForm')">
									<!--  搜索-->
									<spring:message code="ar.viewempcalender.title.search" />
								</button>
							</div>
						</div></li>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="button" onclick="javascript:deleteDate()">
									<!--  工资计算-->
									移除对象
									<%-- <spring:message code="pa.salary.title.salarycalculation" /> --%>
								</button>
							</div>
						</div>
					</li>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">

								<button type="button">
									<!-- 发令 -->
									<spring:message code="display.emp.statistics.mes202" />
								</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</div>
		<br>
		<table width="100%" class="searchContent">
			<tr>
				<td width="150px" class="info_content_00">增加人员条件设定</td>
				<td width="300px"><input type="text" name="leftDate"
					id="leftDate" class="date" readonly="true" style="width: 100px; "
					format="yyyy-MM-dd" yearstart="-50" yearend="5"
					onClick="setdate(this);" />&nbsp;&nbsp;&nbsp; 以前进行入社发令，截至</td>
				<td align="left"><input type="text" name="rightDate"
					id="rightDate" class="date" readonly="true" style="width: 100px; "
					format="yyyy-MM-dd" yearstart="-50" yearend="5"
					onClick="setdate(this);" />&nbsp;&nbsp;&nbsp; 在职，未缴纳住房公积金的正规职人员。 <%-- <spring:message code="display.emp.statistics.mes196" /> --%>
				</td>
				<td><a style="float:right; " class="buttonActive"
					id="exportExcel" onclick="downloadImportTemplate();" href="#"><span><spring:message
								code="pa.insurance.title.downloadImportTemplate" /> <!--下载导入模板-->
					</span> </a> 
					<a style="float:right; " class="buttonActive" id="importExcel"
					onclick="importManageAddFundNum();" href="#" target="dialog"><span><spring:message
								code="ar.addempshift.title.excelimport" /> <!--Excel导入--> </span> </a> 
								<a
					style="float:right; " class="buttonActive" id="exportExcel"
					onclick="downloadImportTemplate();" href="#"><span><spring:message
								code="ar.addempshift.title.excelexport" /> <!--Excel导出--> </span> </a> <a
					style="float:right; " class="buttonActive" id=""
					onclick="deleteDate_bx0203('searchForm_bx0203a');" href="#"><span><spring:message
								code="button.delete" /> <!--删除--> </span> </a> <a style="float:right; "
					class="buttonActive" id="exportExcel" onclick="save_bx0203();"
					href="#"><span><spring:message code="button.update" />
							<!--修改--> </span> </a>
				</td>
			</tr>



		</table>
	</div>
	<div class="pageContent">
		<table class="table" width="101.8%" layoutH="100" asc="asc"
			desc="desc">
			<thead>
				<tr>
					<th width="100">
						<!-- 复选框--> <input type="checkbox" name="c1_hr0302" id="c1_hr0302">
					</th>
					<th width="100">
						<!-- 序号--> <spring:message
							code="hr.viewPersonalInfo.title.TRADEUNION_NUM" />
					</th>
					<th width="100" class="asc">
						<!-- 部门--> <spring:message code="public.title.deptName" />
					</th>
					<th width="100" class="asc">
						<!-- 职号--> <spring:message code="display.emp.statistics.mes209" />
					</th>
					<th width="100">
						<!-- 姓名--> <spring:message code="public.title.name" />
					</th>

					<th width="100" class="asc">
						<!-- 户口性质 --> <spring:message
							code="hr.viewPersonalInfo.title.REG_TYPE_NAME" />
					</th>
					<th width="100" class="asc">
						<!-- 入社日期--> <spring:message code="display.emp.statistics.mes206" />
					</th>
					<th width="100" class="asc">
						<!-- 离职日期--> <spring:message code="ess.trans.title.resignDate" />
					</th>

					<th width="100" class="asc">
						<!-- 开始缴纳月--> <spring:message code="display.emp.statistics.mes212" />
					</th>
					<th width="115" class="asc">
						<!-- 基数参照工资--> <spring:message
							code="display.emp.statistics.mes213" /></th>

					<th width="100" class="asc">
						<!-- 入社基数--> <spring:message code="display.emp.statistics.mes214" />
					</th>
					<th width="100" class="asc">
						<!-- 标记--> <spring:message code="display.emp.statistics.mes215" />
					</th>
					<th width="100" class="asc">
						<!-- 缴纳基数--> <spring:message code="display.emp.statistics.mes216" />
					</th>
					<th width="100" class="asc">
						<!-- 状态--> <spring:message code="display.emp.statistics.mes217" />
					</th>
					<th width="100" class="asc">
						<!-- 提示--> <spring:message code="display.emp.statistics.mes218" />
					</th>
			</thead>
			<tbody>
				<c:forEach items="${showList}" var="show" varStatus="i">
					<tr align="center" onclick="band('#f4f7fa','black')">
						<td style="white-space:nowrap"><input type="checkbox"
							id="checkid" name="checkid"
							value="${show.PA_BENHS_MANAGE_ADD_SEQ}" />
						</td>
						<td style="white-space:nowrap">${i.index + 1}&nbsp;</td>
						<td style="white-space:nowrap" align="left">
							${show.CONTENT}&nbsp;</td>
						<td style="white-space:nowrap">${show.PERSON_ID}&nbsp;</td>
						<td style="white-space:nowrap">${show.LOCAL_NAME}&nbsp;</td>
						<td style="white-space:nowrap">
							${show.REG_TYPE_CODE_NAME}&nbsp;</td>
						<td style="white-space:nowrap">${show.DATE_STARTED1}&nbsp;</td>
						<td style="white-space:nowrap"><c:choose>
								<c:when test="${empty show.DATE_LEFT}">-</c:when>
								<c:otherwise>${show.DATE_LEFT}</c:otherwise>
							</c:choose>
						</td>
						<input type="hidden" name="id"
							value="${show.PA_BEN_MANAGE_ADD_SEQ}" />
						<td style="white-space:nowrap">${show.START_DATE}&nbsp;</td>
						<td style="white-space:nowrap">${show.REFER_VALUE}&nbsp;</td>
						<td style="white-space:nowrap">${show.JOIN_VALUE}&nbsp;</td>
						<td style="white-space:nowrap">${show.BASELINE}&nbsp;</td>
						<td style="white-space:nowrap">${show.ENDOWMENT_BASE}&nbsp;</td>
						<td style="white-space:nowrap"><c:choose>
								<c:when test="${empty show.DATE_TYPE}">-</c:when>
								<c:otherwise>${show.DATE_TYPE}</c:otherwise>
							</c:choose>
						</td>
						<td style="white-space:nowrap"><c:choose>
								<c:when test="${empty show.ERROR_REMARK}">-</c:when>
								<c:otherwise>${show.ERROR_REMARK}</c:otherwise>
							</c:choose>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</form>


