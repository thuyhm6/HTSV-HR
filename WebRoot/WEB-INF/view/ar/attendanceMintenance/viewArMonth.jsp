<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript"> 
function validateCallback_viewarmonth(form, callback) {

	var mothlyLock = $("#mothlyLock").val();
	if(mothlyLock == '1'){
		//该月考勤已经锁定，不能进行修改操作！
		alertMsg.error("<spring:message code='ar.viewarmonth.title.kaoqinsuoding'/>");
		return false;
	}
	
	var $form = $(form);
	
	if (!$form.valid()) {
		return false;
	}

	var checked=false;
	var ids= document.getElementsByName("c1");
	for(var i=0;i<ids.length;i++){
		if(ids[i].checked){
			checked=true;
		}
	}
	if(!checked){
		//请选择人员再进行修改操作!
		alertMsg.error("<spring:message code='ar.alert.message.viewardetail.choosetoupdate'/>");
		return false;
	}
	//确定要提交吗？
	if (confirm ("<spring:message code='ar.alert.message.viewArAnnualStandard.consubmit'/>")){	
		$.ajax({
			type: 'POST',
			url:$form.attr("action"),
			data:$form.serializeArray(),
			dataType:"json",
			cache: false,
			success: callback || DWZ.ajaxDone,
			error: DWZ.ajaxError
		});
	}

	return false;
}

function checkNum(value){
	if(value == ''){
		//值不能为空!
		alert("<spring:message code='ar.alert.message.viewardetail.valuenotnull'/>");
	}
	if(isNaN(value)){
		//值必须为数字!
		alert("<spring:message code='ar.alert.message.viewardetail.numbervalue'/>");
	}
}

function add_param(){
	
	var temp = $("#exp_t").attr("href").split("?");
	$("#exp_t").attr("href",temp[0]+"?arMonth="+$("#seach_year").val()+$("#seach_month").val());
	
}

function oncheck(index){

	if(document.subForm_viewarmonth.c1[index]){
		
		document.subForm_viewarmonth.c1[index].checked = true;
	}
}

function exportArMonthModuleExcel(obj){

	var STAT_NO = document.viewarmonth.seach_STAT_NO.value;
	var AR_MONTH = document.viewarmonth.seach_year_ar0106.value + document.viewarmonth.seach_month_ar0106.value;
    var deptNO = document.viewarmonth.seach_deptNO.value ;
	var JobTypeGroupNo = document.viewarmonth.seach_JobTypeGroupNo.value ;
	var EmpTypeCodeNo = document.viewarmonth.seach_EmpTypeCodeNo.value ;
	var EmpOffice = document.viewarmonth.seach_EmpOffice.value ;
	var condition = document.viewarmonth.seach_condition.value ;
	document.getElementById("exportArMonthModuleExcel").href="/pa/excelExport/exportArMonthModule?AR_MONTH="+AR_MONTH+"&STAT_NO="+STAT_NO+"&deptNO="+deptNO+"&JobTypeGroupNo="+JobTypeGroupNo+"&EmpTypeCodeNo="+EmpTypeCodeNo+"&EmpOffice="+EmpOffice+"&condition="+condition;
	document.getElementById("exportArMonthModuleExcel").click();
   
}

function exportArMonthModuleExport(obj){

	var STAT_NO = document.viewarmonth.seach_STAT_NO.value;
	var AR_MONTH = document.viewarmonth.seach_year_ar0106.value + document.viewarmonth.seach_month_ar0106.value;
	var DEPT_NO = document.viewarmonth.seach_deptNO.value ;
	var JobTypeGroupNo = document.viewarmonth.seach_JobTypeGroupNo.value ;
	var EmpTypeCodeNo = document.viewarmonth.seach_EmpTypeCodeNo.value ;
	var EmpOffice = document.viewarmonth.seach_EmpOffice.value ;
	var condition = document.viewarmonth.seach_condition.value ;
	 
	document.getElementById("exp_t").href="/pa/excelExport/exportArItemDataExcel?arMonth="+AR_MONTH+"&STAT_NO="+STAT_NO+"&DEPT_NO="+DEPT_NO+"&JobTypeGroupNo="+JobTypeGroupNo+"&EmpTypeCodeNo="+EmpTypeCodeNo+"&EmpOffice="+EmpOffice+"&condition="+condition;
	document.getElementById("exp_t").click();

}
</script>

<div class="pageHeader">
	<form id="viewarmonth" name="viewarmonth" onsubmit="return navTabSearch(this);" action="/ar/attendanceMintenance/viewArMonth" method="post" rel="pagerForm">
	<div class="searchBar" >
		<table class="searchContent">
			<tr>
				<td><!-- 考勤月 --><spring:message code='ar.excelexport.title.armonth'/></td>
				<td><ait:date yearName="seach_year_ar0106" yearSelected="${year_ar0106}"
						monthName="seach_month_ar0106" monthSelected="${month_ar0106}"/></td>
				<td><!-- 区间 --><spring:message code="ar.viewcycleparameter.title.qujian"/></td>
				<td>
					<select class="combox" name="seach_STAT_NO" id="seach_STAT_NO">
						<option value="">select</option>
						<c:forEach items="${statnoList}" var="list">
							<option value="${list.STAT_NO}" <c:if test="${list.STAT_NO eq STAT_NO}">selected</c:if>>${list.STAT_NAME}</option>
						</c:forEach>
					</select>
				</td>
				<td><!-- 工号/姓名 --><spring:message code='public.title.empIdAndName'/></td>
				<td>
					<input name="seach_condition" type="text" id="seach_condition" value="${condition}"/>
				</td>
				<td><!-- 部门 --><spring:message code='public.title.deptName'/></td>
				<td>
					<ait:deptList name="seach_deptNO" limit="ar"  id="ar0106_seachDept"/>
					<ait:deptTreeIcon name="seach_deptNO" limit="ar" id="ar0106_seachDept" selected="${deptNO}"/>
				</td>
			</tr>
			<tr>
					<td>人员类型组 </td>
						<td>
							<input type="hidden" id="ar0106_limit" name="limit" value="ar">
							<input type="hidden" id="ar0106_seach_CPNY" name="seach_CPNY" value="${LoginUser.cpnyId}">
							<ait:SelectEmpTypeCode  id="ar0106_seach_JobTypeGroupNo"  name="seach_JobTypeGroupNo" selected="${JobTypeGroupNo}" limit="ar" type="group"
								onChangeName="ajaxEmpTypeForGroupToList(-1,ar0106_seach_JobTypeGroupNo,ar0106_seach_EmpTypeCodeNo,ar0106_seach_CPNY,ar0106_limit)"/>
						</td>
						<td>人员类型 </td>
						<td>
		 					<ait:SelectEmpTypeCode id="ar0106_seach_EmpTypeCodeNo" name="seach_EmpTypeCodeNo" selected="${EmpTypeCodeNo}" limit="ar"/>
						</td>	
			  <td>在职状态</td>
						<td>
				 	<ait:SelectSyCodeByCpnyID id="seach_EmpOffice" name="seach_EmpOffice" parentNo="15118" selected="${EmpOffice}" cnpyID="${LoginUser.cpnyId}" limit="all"/>
						</td>
			</tr>
		</table>
		<div class="subBar">
						<ul>
                        <li><div class="buttonActive"><div class="buttonContent"><button type="submit"><!-- 查询 --><spring:message code="button.search"/></button></div></div></li>
					 <li>
					 <a id="exp_t" class="buttonActive"
								onclick="exportArMonthModuleExport(this);"
								><span><!-- EXCEL导出 --><spring:message code="ar.addempshift.title.excelexport"/></span>
							</a>
					</ul>
					</div>
	</div>
	</form>
</div>

<div class="pageContent">
	<form name="subForm_viewarmonth" onsubmit="return validateCallback_viewarmonth(this, navTabAjaxDone);" class="pageForm required-validate" 
		 action="/ar/attendanceMintenance/updateArMonthInfo" method="post" rel="pagerForm">
	<input type="hidden" id="arMonth" name="arMonth" value="${arMonth}" />
	<input type="hidden" id="mothlyLock" name="mothlyLock" value="${mothlyLock}" />
	<%--
	<div class="formBar">
		<ul>
			<li>
			
			<c:if test="${toolbarInfo.INSERTR == '1'}">

				<li>
					<a id="exportArMonthModuleExcel" class="buttonActive"
					onclick="exportArMonthModuleExcel(this);"
					><span><!-- 下载导入模板 --><spring:message code="ar.addempshift.title.downloadmodule"/></span>
				</a>
				</li>
				<li>						 
					<a class="buttonActive"
						href="/pa/excelImport/importArItemData?importFunName=importArItemDataExcel" target="dialog" mask="true" width="400" height="200" ><span><!-- EXCEL导入 --><spring:message code="ar.addempshift.title.excelimport"/></span>
					</a>
				</li>
				<li>
				
					 <a id="exp_t" class="buttonActive"
						onclick="exportArMonthModuleExport(this);"
						><span><!-- EXCEL导出 --><spring:message code="ar.addempshift.title.excelexport"/></span>
					</a>
					
				</li>
			</c:if>

				<c:if test="${toolbarInfo.INSERTR == '1'}">
					<li>
					<div class="buttonActive"><div class="buttonContent"><button type="submit" ><!-- 保存 --><spring:message code='ar.viewempcalender.title.save'/></button></div></div>
					</li>
				</c:if>
				
			</li>
			
		</ul>
	</div>
	 --%>
	 
	<table >
	    ${datetable }
	</table>
 
	<table class="table" width="100%" layoutH="206">
		<thead>
			<tr>
				<th width="30"><input type="checkbox" class="checkboxCtrl" group="c1"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
				<th width="45"><!-- 工号 --><spring:message code='public.title.empId'/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
				<th width="45"><!-- 姓名 --><spring:message code='public.title.name'/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
				<th width="100"><!-- 部门 --><spring:message code='public.title.deptName'/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
				<th width="45">人员类型</th>
				
				<c:forEach items="${arColumnsList}" var="column" varStatus="i">
					<th width="10">${column.ITEM_NAME}</th>
				</c:forEach>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${getArMonthList}" var="list" varStatus="j">
			
				<tr>
					<td width="30"><input type="checkbox" name="c1" value="${list.PERSON_ID}" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td width="45">${list.EMPID}&nbsp;&nbsp;&nbsp;</td>
					<td width="45">${list.LOCAL_NAME}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td width="100">${list.DEPTNAME}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td width="45">${list.EMP_TYPE_NAME}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<c:forEach items="${arColumnsList}" var="column_i" varStatus="k">
						<td width="10">
							<input type="text" name="${column_i.COLUMN_NAME}_${list.PERSON_ID}" value="${list[column_i.COLUMN_NAME]}" size="5" onblur="checkNum(this.value)" onchange="oncheck('${j.index}');"/>
						</td>
					</c:forEach>
					
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</form>
	<c:set value="/ar/attendanceMintenance/viewArMonth" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>

