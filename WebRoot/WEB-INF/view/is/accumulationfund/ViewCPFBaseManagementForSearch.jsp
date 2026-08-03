<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">

    function downloadImportTemplate(){//下载导入模板
	    document.searchForm_bx0202.action="/is/accumulationfundmanage/downloadTemplete";
		document.searchForm_bx0202.submit();
	}
	$(document).ready(function() {
		var message = "${message}";
		if (message != '') {
			alertMsg.info("${message}");
		}
	});
	function search_bx0202(form) {//搜索
		var leftDate = $("#leftDate", navTab.getCurrentPanel()).val();
		var year1 = $("#year1", navTab.getCurrentPanel()).val();
		var month1 = $("#month1", navTab.getCurrentPanel()).val();
		var $form = null;
		if ($('#' + form).length > 0)
			$form = $('#' + form);
		else
			$form = $(form);
		$form.attr("action", "/is/accumulationfund/createCPFBaseManagement");
		$.ajax({
					type : form.method || 'POST',
					url : $form.attr("action"),
					async : false,
					data : "seach_year1=" + year1 + "&seach_month1=" + month1
							+ "&seach_leftDate=" + leftDate,
					dataType : "json",
					cache : false,
					success : function(data) { //请求成功后处理函数。
						//alert(data.statusCode);
						if (data.statusCode == "200") {
							navTabNum(
									'/is/accumulationfund/ViewCPFBaseManagementForSearch?pageNum=1&menuNo=124912&navTabId=bx0202&method=create',
									'bx0202', '基数管理');
						} else if (data.statusCode == "300") {
							alertMsg.info(data.message);
						}
					}
				});
		/*document.searchForm.action="/paBenHsControlServlet?operation=paBenHs_benchmark_manage&menu_code=${menu_code}";
		showHidObje(maskArray,"","数据加载中");
		document.searchForm.submit();*/
	}
	function deleteDate_bx0202(form) {//删除
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
			$form.attr("action", "/is/accumulationfund/deleteCPFBaseManagement");
				$.ajax({
							type : form.method || 'POST',
							url : $form.attr("action"),
							async : false,
							data : $form.serializeArray(),
							dataType : "json",
							cache : false,
							success : function(data) { //请求成功后处理函数。
								//alert(data.statusCode);
								if (data.statusCode == "200") {
									alertMsg.correct(data.message);
									navTabNum(
											'/is/accumulationfund/ViewCPFBaseManagementForSearch?pageNum=1&menuNo=124912&navTabId=bx0202',
											'bx0202', '基数管理');
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
	
	function save_bx0202(){//修改
	     
		var flag = false;
		var cs = document.getElementsByName("checkid");
		var ids="";
		for ( var i = 0; i < cs.length; i++) {
			if(cs[i].checked == true){
				ids+=cs[i].value+",";
				flag = true;
			}
		}
		if(flag){
			$("#updateid_bx0202").attr("href","/is/accumulationfund/updateCPFBaseManagement?ids="+ids);
			$("#updateid_bx0202").click();
			//$.pdialog.open("/is/accumulationfund/updateCPFBaseManagement?ids="+ids, "updateCPFBaseManagement", "基数管理-修改", "mask:true,width:100px,height:100px");
			
		}else{
			alert('请选择修改项！');
			
		}
	}
	function band(backColor,textColor)
	{
	    if(typeof(preEl)!='undefined')
	    {
	    preEl.bgColor=orgBColor;

	    try{ChangeTextColor(preEl,orgTColor);}catch(e){;}
	    }
	    var el = event.srcElement;
	    el = el.parentElement;
	    orgBColor = el.bgColor;
	    orgTColor = el.style.color;
	    el.bgColor=backColor;
	    try{ChangeTextColor(el,textColor);}catch(e){;}
	    preEl = el;
	    //document.form1.vacation_no.value=i;
	}
	
	function compute_bx0202(form){//计算
		//document.getElementById('logtype').value="INSERT" ;
		var cs = document.getElementsByName("check");
		if(cs.length == 0){
			alert('没有可计算的人员');
			return;
		}
		var leftDate = $("#leftDate", navTab.getCurrentPanel()).val();
		var year1 = $("#year1", navTab.getCurrentPanel()).val();
		var year2 = $("#year2", navTab.getCurrentPanel()).val();
		var year3 = $("#year3", navTab.getCurrentPanel()).val();
		var month1 = $("#month1", navTab.getCurrentPanel()).val();
		var month2 = $("#month2", navTab.getCurrentPanel()).val();
		var month3 = $("#month3", navTab.getCurrentPanel()).val();
		var beforeDate = $("#beforeDate", navTab.getCurrentPanel()).val();
		var startDate = $("#startDate", navTab.getCurrentPanel()).val();
		var endDate = $("#endDate", navTab.getCurrentPanel()).val();
		var $form = null;
		if(startDate==undefined){
			startDate="";
		}
		if(endDate==undefined){
			endDate="";
		}
		if ($('#' + form).length > 0)
			$form = $('#' + form);
		else
			$form = $(form);
		$form.attr("action", "/is/accumulationfund/computeCPFBaseManagement");
		$
				.ajax({
					type : form.method || 'POST',
					url : $form.attr("action"),
					async : false,
					data : "seach_year1=" + year1 + "&seach_year2=" + year2 + "&seach_year3=" + year3 + "&seach_month1=" + month1
							+ "&seach_month2=" + month2
							+ "&seach_month3=" + month3
							+ "&seach_startDate=" + startDate
							+ "&seach_endDate=" + endDate
							+ "&seach_beforeDate=" + beforeDate
							+ "&seach_leftDate=" + leftDate,
					dataType : "json",
					cache : false,
					success : function(data) { //请求成功后处理函数。
						alert(data.message);
						navTabNum('/is/accumulationfund/ViewCPFBaseManagementForSearch?pageNum=1&menuNo=124912&navTabId=bx0202',
									'bx0202', '基数管理');
						
					}
				});
		//document.form1.action="/paBenHsControlServlet?operation=paBenHs_benchmark_basenum&method=compute&menu_code=${menu_code}";
		//showHidObje(maskArray,"","数据加载中");
		//document.form1.submit();
	}
	function order_bx0202(){//发令
	     	$("#orderid_bx0202").attr("href","/is/accumulationfund/orderCPFBaseManagement");
			$("#orderid_bx0202").click();
			//$.pdialog.open("/is/accumulationfund/updateCPFBaseManagement?ids="+ids, "updateCPFBaseManagement", "基数管理-修改", "mask:true,width:100px,height:100px");
		
	}
	
	
	
	function importExcelCPFBaseManagement(){

	
		$("#importExcel").attr('href','/pa/excelImport/importExcelData?importFunName=/importExclInsatanceBaseAccumulationFundNum');
		$("#importExcel").click();
		
    }	
</script>


<a id="updateid_bx0202" rel="updateCPFBaseManagement" mask="true" width="1200",height="400" target="dialog"></a>
<a id="orderid_bx0202" rel="orderCPFBaseManagement" mask="true" width="1200",height="400" target="dialog"></a>
<div class="pageHeader">

	<div class="searchBar">
		<form name="searchForm_bx0202" id="searchForm_bx0202" method="post">
			<div class="subBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">

								<button type="button"
									onclick="search_bx0202('searchForm_bx0202');">
									<!--  搜索-->
									<spring:message code="ar.viewempcalender.title.search" />
								</button>
							</div>
						</div>
						</li>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="button" onclick="compute_bx0202('searchForm_bx0202');">
									<!--  工资计算-->
									<spring:message code="pa.salary.title.salarycalculation" />

								</button>
							</div>
						</div>
					</li>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">

								<button type="button"   onclick="order_bx0202();">
									<!-- 发令 -->
									<spring:message code="display.emp.statistics.mes202" />
								</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</form>
	</div>
	<table width="100%" class="searchContent">
		<tr>
			<td width="150px" class="info_content_00"><spring:message
					code="display.emp.statistics.mes194" />
			</td>
			<td width="300px"><ait:date yearName="year1" monthName="month1"
					yearSelected="${year1}" monthSelected="${month1}" yearPlus="10"
					yearMinus="10" />&nbsp;&nbsp;&nbsp; <spring:message
					code="display.emp.statistics.mes211" />&nbsp;&nbsp;&nbsp;</td>
			<td align="left"><input type="text" name="leftDate"
				id="leftDate" class="date" readonly="true" style="width: 100px; "
				format="yyyy-MM-dd" yearstart="-50" yearend="5" onClick="setdate(this);" />&nbsp;&nbsp;&nbsp;<spring:message
					code="display.emp.statistics.mes196" />
			</td>
		</tr>
		<tr>
			<td width="150px" class="info_content_00"><!-- 平均扣税工资计算期间 --><spring:message
					code="display.emp.statistics.mes197" />
			</td>
			<td><ait:date yearName="year2" monthName="month2"
					yearSelected="${year2}" monthSelected="${month2}" yearPlus="10"
					yearMinus="10" /> — <ait:date yearName="year3" monthName="month3"
					yearSelected="${year3}" monthSelected="${month3}" yearPlus="10"
					yearMinus="10" />
			</td>
		</tr>
		<tr>
			<td rowspan="2"><!-- 平均扣税工资 --><spring:message
					code="display.emp.statistics.mes198" /> <br><br> <!-- 计算条件设定 --><spring:message
					code="display.emp.statistics.mes199" />
			</td>
			<td><input type="text" name="beforeDate" id="beforeDate"
				class="date" readonly="true" style="width: 100px; "
				format="yyyy-MM-dd" yearstart="-50" yearend="5"
				onClick="setdate(this);" /><!-- 以前入社人员计算平均扣税工资 --> <spring:message
					code="display.emp.statistics.mes200" />
			</td>

		</tr>
		 <c:if test="${companyID eq 'CompanyId07'}">
			<tr>
			<td class="info_content_00" colspan="4">
				&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" size="13" name="startDate" onclick="setday(this);" readonly="readonly">
				— <input type="text" size="13" name="endDate" onclick="setday(this);" readonly="readonly">&nbsp;&nbsp;&nbsp;&nbsp;
				<!-- 入社人员，计算第二个月税前工资。（广州适用） -->
			</td>
			</tr>
 		</c:if>
		<tr>
			<td><input type="text" name="afterDate" id="afterDate"
				class="date" readonly="true" style="width: 100px; "
				format="yyyy-MM-dd" yearstart="-50" yearend="5"
				onClick="setdate(this);" /> <!-- 后入社人员，按入社基数计算。 -->  
				<spring:message code="display.emp.statistics.mes201" />
			</td> 
			<td>
			<a style="float:right; " class="buttonActive" id="downloadImportTemplate" 
			onclick="downloadImportTemplate();" href="#">
			<span><spring:message code="pa.insurance.title.downloadImportTemplate" /> 
			<!--下载导入模板--></span> 
			</a> 
				
				
			<a style="float:right; " class="buttonActive" id="importExcel"
			onclick="javascript:importExcelCPFBaseManagement();" href="#" target="dialog">
			<span><spring:message code="ar.addempshift.title.excelimport" /> 
			<!--Excel导入--> </span> </a> 
							
							
							
			<a style="float:right; " class="buttonActive" id=""onclick="" href="#">
            <span><spring:message code="ar.addempshift.title.excelexport" /> 
            <!--Excel导出--> </span> </a> 
							
							
							
			<a style="float:right; " class="buttonActive" id=""
			onclick="deleteDate_bx0202('searchForm_bx0202a');" href="#"><span>
			<spring:message code="button.delete" /> <!--删除--> </span> </a> 
			<a style="float:right; " class="buttonActive" id=""
			onclick="save_bx0202();" href="#">
			<span><spring:message code="button.update" /> <!--修改--> </span> </a>
			</td>
		</tr>

	</table>

</div>

<div class="pageContent">
<form name="searchForm_bx0202a" id="searchForm_bx0202a" method="post">
	<table class="table" width="101.8%" layoutH="160" asc="asc" desc="desc">
		<thead>
			<tr>
				<th width="100">
					<!-- 复选框--> <input type="checkbox" name="c1_hr0302" id="c1_hr0302" class="checkboxCtrl" group="check">
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

				<th width="100">
					<!-- 身份证号 --> <spring:message
						code="ess.personalinfo.title.IDCardNo" />
				</th>
				<th width="100" class="asc">
					<!-- 职系--> <spring:message code="display.emp.statistics.mes210" />
				</th>
				<th width="100" class="asc">
					<!-- 户口性质 --> <spring:message
						code="hr.viewPersonalInfo.title.REG_TYPE_NAME" />
				</th>

				<th width="100" class="asc">
					<!-- 在职状态-->
					<spring:message code="display.emp.statistics.mes204"/>
				</th>
				<th width="100" class="asc"><!-- 入社日期--> <spring:message
						code="display.emp.statistics.mes206" />
				</th>

				<th width="100" class="asc">
					<!-- 离职日期--> <spring:message code="ess.trans.title.resignDate" />
				</th>
				<th width="100" class="asc">
					<!-- 平均扣税工资--> <spring:message code="display.emp.statistics.mes198" />
				</th>
				<th width="100" class="asc">
					<!-- 年度基数--> <spring:message code="display.emp.statistics.mes208" />
				</th>
		</thead>
		<tbody>
			<c:forEach items="${showList}" var="show" varStatus="i">
				<tr align="center" onclick="band('#f4f7fa','black')">
					<td class="td_center"><input type="checkbox" name="checkid" id="checkid"
						value="${show.PA_BENHS_BASE_SEQ}" />
					</td>
					<td>${i.index + 1}&nbsp;</td>
					<td align="left">${show.DEPTNAME}&nbsp;</td>
					<td>${show.EMPID}&nbsp;</td>
					<td>${show.CHINESENAME}&nbsp;</td>
					<td>${show.IDCARD_NO}&nbsp;</td>
					<td>${show.POST_COEF_NAME}&nbsp;</td>
					<td>${show.REG_TYPE_CODE_NAME}&nbsp;</td>
					<td>${show.STATUS_CODE_NAME}&nbsp;</td>
					<td><c:choose>
							<c:when test="${empty show.DATE_STARTED}">-</c:when>
							<c:otherwise>${show.DATE_STARTED}</c:otherwise>
						</c:choose>
					</td>
					<td><c:choose>
							<c:when test="${empty show.DATE_LEFT}">-</c:when>
							<c:otherwise>${show.DATE_LEFT}</c:otherwise>
						</c:choose>
					</td>
					<td>${show.PAY_SALARY}&nbsp;</td>
					<td>${show.AVG_SALARY}&nbsp;</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</form>
</div>


