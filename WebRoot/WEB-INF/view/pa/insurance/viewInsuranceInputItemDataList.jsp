<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script type="text/javascript">
function validateCallbackUpdateIn(form,callback) {	
	var $form = $("#viewInsuranceInputItemDateList",navTab.getCurrentPanel());
	if (!$form.valid()) {
		return false;
	}
	var checked=false;
	var ids= document.getElementsByName("c1");
	for(var i=0;i<ids.length;i++){
		if(ids[i].checked){
		   var name = ids[i].value;
		   var startMonthStrs = $("#START_MONTH_"+name).val();
		   var endMonthStrs = $("#END_MONTH_"+name).val();
		   if (!/^(?:19[7-9]\d|2\d{3,3})(?:0[1-9]|1[0-2])$/.test(startMonthStrs)){
	         alertMsg.error('<spring:message code="alert.message.pa.insurance.startMonthIsNotCorrect"/>');
	         return false;
	       }
	       if(endMonthStrs !=null && endMonthStrs !=""){
			  if(!/^(?:19[7-9]\d|2\d{3,3})(?:0[1-9]|1[0-2])$/.test(endMonthStrs)){
			    alertMsg.error('<spring:message code="alert.message.pa.insurance.endMonthIsNotCorrect"/>');
			    return false;
			  }
				//如果结束月份比开始月早， 请重新填写结束月！
			  if(endMonthStrs < startMonthStrs){
			    alertMsg.error('<spring:message code="alert.message.pa.insurance.endMonthIsBeforeStartDate"/>');
			    return false;
			  }
			}
		   checked=true;
		}
	}
	if(!checked){
		alertMsg.error('<spring:message code="alert.message.pa.insurance.pleaseInputDataFirst"/>');
		return false;
	}

	if (confirm ('<spring:message code="alert.message.pa.insurance.confirmSubmit"/>')){
	  	$.ajax({
			type: form.method || 'POST',
			url:$form.attr("action"),
			data:$form.serializeArray(),
			dataType:"json",
			cache: false,
			success: callback || DWZ.ajaxDone,
			error: DWZ.ajaxError
		});	
		return false;
	}
	
}

function getInsuranceInputItemDataList(formName){
//seach_insYear seach_insMonth

if($("#seach_insYear",navTab.getCurrentPanel()).val()==""||$("#seach_insMonth",navTab.getCurrentPanel()).val()==""){
	
	return false;
}else{
	$("#"+formName).submit();
	}
}

function checkStartMonthAndEndMonth(obj,name){
	if(obj.value.length == 6){
		var startMonthStr = $("#START_MONTH_"+name).val();
		var endMonthStr = obj.value;
		if(endMonthStr < startMonthStr){
			alertMsg.error('<spring:message code="alert.message.pa.insurance.endMonthIsBeforeStartDate"/>');
		}
	}
}

$(document).ready(function() {
		var type=$("#viewInsuranceInputItemDataList_pa0409").val();
		if(type==""){
			var myDate = new Date();
			var year=myDate.getFullYear(); //当前年
			var month=myDate.getMonth(); 
			$("#seach_insYear",navTab.getCurrentPanel()).val(year);  
			var curmonth=parseInt(parseInt(month)+1);
			$("#seach_insMonth",navTab.getCurrentPanel()).val(curmonth);
		}
		var viewInsuranceInputItemDataHref="";
		$("#is_excelExport").click(function(){
			var empid=$("#seach_EMPID",navTab.getCurrentPanel()).val();
			var deptno=$("#seach_DEPTNO",navTab.getCurrentPanel()).val();
			var year=$("#seach_insYear",navTab.getCurrentPanel()).val();
			var month=$("#seach_insMonth",navTab.getCurrentPanel()).val();
			viewInsuranceInputItemDataHref=$(this).attr("href");
			$(this).attr("href",$(this).attr("href")+"&empid="+empid+"&deptno="+deptno+"&year="+year+"&month="+month+"");
		});
		$("#is_excelExport").focusout(function() {  
			  //alert($(this).attr("href"));
			  
			  $(this).attr("href",viewInsuranceInputItemDataHref);
		});  
		
});
function submitFormViewInsurance_pa0409(){
	$("#viewInsuranceInputItemDataList_pa0409").val("2");
  	var $from = $("#viewInsuranceInputItemDataListForm");
  	$from.submit();
}
$(document).ready(function(){

	$("input[id^='END_MONTH'][name^='END_MONTH'][inputType='date']").removeClass("required");
});

function yearMonthChange(){
	var year=$("#seach_insYear").val();
	var month=$("#seach_insMonth").val();
	$("#pagerForm input[id=seach_insYear]").val(year);
	$("#pagerForm input[id=seach_insMonth]").val(month);
	
}
function pageFromSea(a){
	//$("#idName",navTab.getCurrentPanel()).val();
	 
	var seach_EMPID=$("#seach_EMPID",navTab.getCurrentPanel()).val()
	var seach_DEPTNO=$("#seach_DEPTNO",navTab.getCurrentPanel()).val();
	var seach_insYear=$("#seach_insYear",navTab.getCurrentPanel()).val();
	var seach_insMonth=$("#seach_insMonth",navTab.getCurrentPanel()).val();
	var seach_PARAM_NO=$("#seach_PARAM_NO",navTab.getCurrentPanel()).val();
	// var viewInsuranceInputItemDataList_pa0409="2";
	 
	if 	(seach_insMonth =='' ||seach_insMonth == null )
	{
	 alert("基准月为空");
	 return ;
	}
															
	$("#pagerForm",navTab.getCurrentPanel()).attr("action", "/pa/insurance/viewInsuranceInputItemDataList?seach_EMPID="+seach_EMPID+"&seach_DEPTNO="+seach_DEPTNO+"&seach_insYear="+seach_insYear+"&seach_insMonth="+seach_insMonth+"&viewInsuranceInputItemDataList_pa0409=2&seach_PARAM_NO="+seach_PARAM_NO);
 
}


function deleteInsuranceInputItemDataList(form,callback)
{

var $form = $("#viewInsuranceInputItemDateList",navTab.getCurrentPanel());
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
		alertMsg.error('请选择删除的数据');
		return false;
	}
	if (confirm ('是否确定删除?')){
	  	$.ajax({
			type:"POST",
			url:"/pa/insurance/deleteAllInsuranceInputItemDataInfo",
			data:$form.serializeArray(),
			dataType:"json",
			cache: false,
			success: callback || DWZ.ajaxDone,
			error: DWZ.ajaxError
		});	
		return false;
	}
	


}
</script>
<a id="importExcel_pa0409"  href="#" target="navTab" mask="true"><span style="display:none;">输入项目数据导入结果</span></a>
<c:if test="${insuranceInputItemParamInfo.DISTINCT_FIELD eq 'PERSON_ID'}">
			<div class="pageHeader">
				<form onsubmit="return divSearch(this, 'viewInsuranceInputItemData');" action="/pa/insurance/viewInsuranceInputItemDataList?pageNum=1&seach_PARAM_NO=${PARAM_NO}&seach_CPNY_ID=${CPNY_ID}&numPerPage=${numPerPage}&ITEM_NO=${ITEM_NO}" method="post" rel="pagerForm"  id="viewInsuranceInputItemDataListForm">
					<input type="hidden" value="${type}" id="viewInsuranceInputItemDataList_pa0409" name="viewInsuranceInputItemDataList_pa0409"/>
					<div class="searchBar">
						<table class="searchContent">
							<tr>
								<td>
									<spring:message code="public.title.name"/><!--姓名-->/
									<spring:message code="public.title.empId"/><!--工号-->
								</td>
								<td>
									<input type="text" value="${EMPID}"  name="seach_EMPID" id="seach_EMPID"/>
								</td>
								<td>
									<spring:message code="public.title.deptName"/><!--部门-->
								</td>
								<td>
									<%-- <c:if test="${loginName ne 'IT'}">
										<ait:deptList name="seach_DEPTNO" limit="pa"  id="viewInsuranceInputItemDataList_seachDept"/>
										<ait:deptTreeIcon name="seach_DEPTNO" limit="pa" id="viewInsuranceInputItemDataList_seachDept" selected="${DEPTNO}"/>
									</c:if>
									<c:if test="${loginName eq 'IT'}">
										<ait:deptList name="seach_DEPTNO" limit="hr"  id="viewInsuranceInputItemDataList_seachDept"/>
										<ait:deptTreeIcon name="seach_DEPTNO" limit="hr" id="viewInsuranceInputItemDataList_seachDept" selected="${DEPTNO}"/>
									</c:if> --%>
									<c:if test="${loginName ne 'IT'}">
										<ait:deptTree  selected="${DEPTNO}" name="seach_DEPTNO" limit="pa"/>
									</c:if>
									<c:if test="${loginName eq 'IT'}">
										<ait:deptTree  selected="${DEPTNO}" name="seach_DEPTNO" limit="hr"/>
									</c:if>
								</td>
								<td>
					<spring:message code="zxc.pa.insurance.title.BASE_MONTH"/><!--基准月-->
								</td>
								<td>
					<ait:date yearName="seach_insYear" monthName="seach_insMonth" yearSelected="${insYear}" monthSelected="${insMonth}" limit="all" onChange="yearMonthChange()"/>
					</td>
							</tr>
						</table>
					<div class="subBar">
							<ul>
								<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="submitFormViewInsurance_pa0409()">
								<spring:message code="public.title.search"/><!--检索--></button></div></div></li>
							</ul>
						</div>
					</div>
				</form>
			</div>
</c:if>
<c:if test="${insuranceInputItemParamInfo.DISTINCT_FIELD ne 'PERSON_ID'}">
<div class="pageHeader">
			<form onsubmit="return divSearch(this, 'viewInsuranceInputItemData');" action="/pa/insurance/viewInsuranceInputItemDataList?pageNum=1&seach_PARAM_NO=${PARAM_NO}&seach_CPNY_ID=${CPNY_ID}&numPerPage=${numPerPage}" method="post" rel="pagerForm"  id="viewInsuranceInputItemDataListForm">
					<input type="hidden" value="${type}" id="viewInsuranceInputItemDataList_pa0409" name="viewInsuranceInputItemDataList_pa0409"/>
					<div class="searchBar">
						<span>
						<spring:message code="zxc.pa.insurance.title.BASE_MONTH"/>: <!--基准月-->
						
						<ait:date yearName="seach_insYear" monthName="seach_insMonth" yearSelected="${insYear}" monthSelected="${insMonth}" limit="all" onChange="yearMonthChange()"/>
						</span>
						<div class="subBar">
							<ul>
								<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="submitFormViewInsurance_pa0409()">
								<spring:message code="public.title.search"/><!--检索--></button></div></div></li>
							</ul>
						</div>
					</div>
					</form>
			
			
			</div>

</c:if>
<div class="pageContent">
	<form id="viewInsuranceInputItemDateList" method="post"
		action="/pa/insurance/updateInsuranceInputItemDataInfo"
		class="pageForm required-validate"
		onsubmit="return validateCallbackUpdateIn(this, navTabAjaxDone);">
		<div class="formBar">
			<label style="float: left">
				<input type="checkbox" class="checkboxCtrl" group="c1" />
				<spring:message code="pa.salary.title.allChecked"/><!--全选-->
				<spring:message code="pa.insurance.title.defaltValue"/><!-- 默认值 -->：
				${insuranceInputItemParamInfo.DEFAULT_VAL}
			</label>
			<ul>
			   <li>
			 	  <c:if test="${insuranceInputItemParamInfo.DISTINCT_FIELD eq 'PERSON_ID'}">
			 	  	<c:if test="${insuranceInputItemParamInfo.APPLY_FLAG eq 'Y' and rolesGroup ne 0}">
						<a class="buttonActive" href="/pa/excelImport/importPaBasicItemExcelData?id=${PARAM_NO}&CPNY_ID=${CPNY_ID}
							&importFunName=/importInsuranceInputItemDataExcelIsNotNull" target="dialog" mask="true" width="500" height="200" >
							<span><spring:message code="ar.addempshift.title.excelimport"/><!-- EXCEL导入 --></span>
						</a>
					</c:if>
			 	  	<c:if test="${insuranceInputItemParamInfo.APPLY_FLAG eq 'N' or empty insuranceInputItemParamInfo.APPLY_FLAG }">
						<a class="buttonActive" href="/pa/excelImport/importPaBasicItemExcelData?id=${PARAM_NO}&CPNY_ID=${CPNY_ID}
							&importFunName=/importInsuranceInputItemDataExcelIsNotNull" target="dialog" mask="true" width="500" height="200" >
							<span><spring:message code="ar.addempshift.title.excelimport"/><!-- EXCEL导入 --></span>
						</a>
					</c:if>
				  </c:if> 
				  <%-- 
				  <c:if test="${insuranceInputItemParamInfo.DISTINCT_FIELD ne 'PERSON_ID'}">
				  		<c:if test="${insuranceInputItemParamInfo.APPLY_FLAG eq 'Y' and rolesGroup ne 0}">
						  	<c:choose>
						  	<c:when test="${insuranceInputItemParamInfo.DISTINCT_FIELD_2ND eq null}">
							  	<a class="buttonActive" href="/pa/excelImport/importInsuranceInputItemData?id=${PARAM_NO}&CPNY_ID=${CPNY_ID}
									&importFunName=importInsuranceInputItemDataExcelIsNull
									&FIELD1_NAME=${insuranceInputItemParamInfo.DISTINCT_FIELD }" target="dialog" mask="true" width="500" height="200" >
									<span><spring:message code="ar.addempshift.title.excelimport"/><!-- EXCEL导入 --></span>
								</a>
						  	</c:when>
							<c:otherwise>
								<a class="buttonActive"
									href="/pa/excelImport/importInsuranceInputItemData?id=${PARAM_NO}&CPNY_ID=${CPNY_ID}
									&importFunName=importInsuranceInputItemDataExcelIsNull2
									&FIELD1_NAME=${insuranceInputItemParamInfo.DISTINCT_FIELD }&
									&FIELD2_NAME=${insuranceInputItemParamInfo.DISTINCT_FIELD_2ND }" target="dialog" mask="true" width="500" height="200" >
									<span><spring:message code="ar.addempshift.title.excelimport"/><!-- EXCEL导入 --></span>
								</a>
							</c:otherwise>
							</c:choose>
						</c:if>
						<c:if test="${insuranceInputItemParamInfo.APPLY_FLAG eq 'N' or empty insuranceInputItemParamInfo.APPLY_FLAG}">
							<c:choose>
						  	<c:when test="${insuranceInputItemParamInfo.DISTINCT_FIELD_2ND eq null}">
							  	<a class="buttonActive" href="/pa/excelImport/importInsuranceInputItemData?id=${PARAM_NO}&CPNY_ID=${CPNY_ID}
									&importFunName=importInsuranceInputItemDataExcelIsNull
									&FIELD1_NAME=${insuranceInputItemParamInfo.DISTINCT_FIELD }" target="dialog" mask="true" width="500" height="200" >
									<span><spring:message code="ar.addempshift.title.excelimport"/><!-- EXCEL导入 --></span>
								</a>
						  	</c:when>
							<c:otherwise>
								<a class="buttonActive"
									href="/pa/excelImport/importInsuranceInputItemData?id=${PARAM_NO}&CPNY_ID=${CPNY_ID}
									&importFunName=importInsuranceInputItemDataExcelIsNull2
									&FIELD1_NAME=${insuranceInputItemParamInfo.DISTINCT_FIELD }&
									&FIELD2_NAME=${insuranceInputItemParamInfo.DISTINCT_FIELD_2ND }" target="dialog" mask="true" width="500" height="200" >
									<span><spring:message code="ar.addempshift.title.excelimport"/><!-- EXCEL导入 --></span>
								</a>
							</c:otherwise>
							</c:choose>
						</c:if>
				  </c:if>
				   --%>
				</li>
				<li>
				 <c:if test="${insuranceInputItemParamInfo.DISTINCT_FIELD eq 'PERSON_ID'}">
						<a id="is_excelExport" class="buttonActive"
							href="/pa/excelExport/exportInsuranceInputItemDataExcelIsNotNull?id=${PARAM_NO}&CPNY_ID=${CPNY_ID}">
							<span><spring:message code="pa.insurance.title.excelExport"/><!--EXCEL导出  --></span>
						</a>
				  </c:if>
				  <%-- 
				  <c:if test="${insuranceInputItemParamInfo.DISTINCT_FIELD ne 'PERSON_ID'}">
					  	<c:choose>
					  	<c:when test="${insuranceInputItemParamInfo.DISTINCT_FIELD_2ND eq null}">
						  	<a class="buttonActive"
								href="/pa/excelExport/exportInsuranceInputItemDataExcelIsNull?seach_PARAM_NO=${PARAM_NO}&seach_CPNY_ID=${CPNY_ID}">
								<span><spring:message code="pa.insurance.title.excelExport"/><!--EXCEL导出  --></span>
							</a>
					  	</c:when>
						<c:otherwise>
							<a class="buttonActive"
								href="/pa/excelExport/exportInsuranceInputItemDataExcelIsNull?seach_PARAM_NO=${PARAM_NO}&seach_CPNY_ID=${CPNY_ID}">
								<span><spring:message code="pa.insurance.title.excelExport"/><!--EXCEL导出  --></span>
							</a>
						</c:otherwise>
						</c:choose>
				  </c:if>	
				   --%>
				</li>
				<li>
					<c:if test="${insuranceInputItemParamInfo.DISTINCT_FIELD eq 'PERSON_ID'}">
						<c:if test="${insuranceInputItemParamInfo.APPLY_FLAG eq 'Y' and rolesGroup ne 0}">
							<a class="buttonActive"
								href="/pa/excelExport/exportInsuranceInputItemDataExcelIsNotNullModule?id=${PARAM_NO}&CPNY_ID=${CPNY_ID}"  ><span>
								<spring:message code="pa.insurance.title.downloadImportTemplate"/><!--下载导入模板--></span>
							</a>
						</c:if>
						<c:if test="${insuranceInputItemParamInfo.APPLY_FLAG eq 'N' or empty insuranceInputItemParamInfo.APPLY_FLAG}">
							<a class="buttonActive"
								href="/pa/excelExport/exportInsuranceInputItemDataExcelIsNotNullModule?id=${PARAM_NO}&CPNY_ID=${CPNY_ID}"  ><span>
								<spring:message code="pa.insurance.title.downloadImportTemplate"/><!--下载导入模板--></span>
							</a>
						</c:if>

					  </c:if>
					  <%-- 
					  <c:if test="${insuranceInputItemParamInfo.DISTINCT_FIELD ne 'PERSON_ID'}">
					  	<c:if test="${insuranceInputItemParamInfo.APPLY_FLAG eq 'Y' and rolesGroup ne 0}">
						  	<c:choose>
						  	<c:when test="${insuranceInputItemParamInfo.DISTINCT_FIELD_2ND eq null}">
							  	<a class="buttonActive"
									href="/pa/excelExport/exportInsuranceInputItemDataExcelIsNullModule?id=${PARAM_NO}&CPNY_ID=${CPNY_ID}&FIELD1_NAME=${insuranceInputItemParamInfo.DISTINCT_FIELD_NAME}&FIELD1=${insuranceInputItemParamInfo.DISTINCT_FIELD}"
									><span><spring:message code="pa.insurance.title.downloadImportTemplate"/><!--下载导入模板--></span>
								</a>
						  	</c:when>
							<c:otherwise>
								<a class="buttonActive"
									href="/pa/excelExport/exportInsuranceInputItemDataExcelIsNullModule?id=${PARAM_NO}&CPNY_ID=${CPNY_ID}
									&FIELD1_NAME=${insuranceInputItemParamInfo.DISTINCT_FIELD_NAME}
									&FIELD2_NAME=${insuranceInputItemParamInfo.DISTINCT_FIELD_2ND_NAME }
									&FIELD1=${insuranceInputItemParamInfo.DISTINCT_FIELD}
									&FIELD2=${insuranceInputItemParamInfo.DISTINCT_FIELD_2ND }"
									><span><spring:message code="pa.insurance.title.downloadImportTemplate"/><!--下载导入模板--></span>
								</a>
							</c:otherwise>
							</c:choose>
						</c:if>
						<c:if test="${insuranceInputItemParamInfo.APPLY_FLAG eq 'N' or empty insuranceInputItemParamInfo.APPLY_FLAG  }">
							<c:choose>
						  	<c:when test="${insuranceInputItemParamInfo.DISTINCT_FIELD_2ND eq null}">
							  	<a class="buttonActive"					 
									href="/pa/excelExport/exportInsuranceInputItemDataExcelIsNullModule?id=${PARAM_NO}&CPNY_ID=${CPNY_ID}&FIELD1_NAME=${insuranceInputItemParamInfo.DISTINCT_FIELD_NAME}&FIELD1=${insuranceInputItemParamInfo.DISTINCT_FIELD}"
									><span><spring:message code="pa.insurance.title.downloadImportTemplate"/><!--下载导入模板--></span>
								</a>
						  	</c:when>
							<c:otherwise>
								<a class="buttonActive"
									href="/pa/excelExport/exportInsuranceInputItemDataExcelIsNullModule?id=${PARAM_NO}&CPNY_ID=${CPNY_ID}
									&FIELD1_NAME=${insuranceInputItemParamInfo.DISTINCT_FIELD_NAME}
									&FIELD2_NAME=${insuranceInputItemParamInfo.DISTINCT_FIELD_2ND_NAME }
									&FIELD1=${insuranceInputItemParamInfo.DISTINCT_FIELD}
									&FIELD2=${insuranceInputItemParamInfo.DISTINCT_FIELD_2ND }"
									><span><spring:message code="pa.insurance.title.downloadImportTemplate"/><!--下载导入模板--></span>
								</a>
							</c:otherwise>
							</c:choose>
						</c:if>					
					  </c:if>
					   --%>
				</li>
				<c:if test="${insuranceInputItemParamInfo.APPLY_FLAG eq 'Y' and rolesGroup ne 0}">
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" class="checkboxCtrl" group="c1"
								selectType="invert">
								<spring:message code="pa.salary.title.opsiteChecked"/><!--反选-->
							</button>
						</div>
					</div>
				</li>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">
								<spring:message code="pa.insurance.title.submit"/><!--保存-->
							</button>
						</div>
					</div>
				</li>
				<li>
					<a class="buttonActive" href="/pa/insurance/addInsuranceInputItemDataView?seach_PARAM_NO=${PARAM_NO}&ITEM_NO=${ITEM_NO}"
						target="dialog" mask="true" width="600" height="450" rel="insuranceInputItemData"><span>
						<spring:message code="button.add"/><!--添加--></span>
					</a>
				</li>
				<%--
				<li>
					<a class="buttonActive" href="/pa/insurance/viewInsuranceInputItemDataPersonList?pageNum=1" target="navTab">
					<span><spring:message code="pa.insurance.title.personalEntry"/><!--个人别录入--></span> </a>
				</li>
				--%>
				</c:if>
				<c:if test="${insuranceInputItemParamInfo.APPLY_FLAG eq 'N' or empty insuranceInputItemParamInfo.APPLY_FLAG}">
					<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" class="checkboxCtrl" group="c1"
								selectType="invert">
								<spring:message code="pa.salary.title.opsiteChecked"/><!--反选-->
							</button>
						</div>
					</div>
				</li>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">
								<spring:message code="pa.insurance.title.submit"/><!--保存-->
							</button>
						</div>
					</div>
				</li>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="button" onclick="return deleteInsuranceInputItemDataList(this, navTabAjaxDone);" >
							批量删除
							</button>
						</div>
					</div>
				</li>
				<li>
					<a class="buttonActive" href="/pa/insurance/addInsuranceInputItemDataView?seach_PARAM_NO=${PARAM_NO}&ITEM_NO=${ITEM_NO}"
						target="dialog" mask="true" width="600" height="450" rel="insuranceInputItemData"><span>
						<spring:message code="button.add"/><!--添加--></span>
					</a>
				</li>
				<%--
				<li>
					<a class="buttonActive" href="/pa/insurance/viewInsuranceInputItemDataPersonList?pageNum=1" target="navTab">
					<span><spring:message code="pa.insurance.title.personalEntry"/><!--个人别录入--></span> </a>
				</li>
				--%>
				</c:if>
				<input id="seach_PARAM_NO" name="seach_PARAM_NO" type="hidden" size="30" value="${PARAM_NO}" />
				<input id="PARAM_NO" name="PARAM_NO" type="hidden" size="30" value="${PARAM_NO}" />
			</ul>
		</div>
		<table class="table" width="99%" layoutH="231">
			<c:if test="${insuranceInputItemParamInfo.DISTINCT_FIELD eq 'PERSON_ID'}">
				<thead>
					<tr>
						<th width="5%"></th>
						<th width="50"><!--工号-->
							<spring:message code="public.title.empId"/>
						</th>
						<th width="50"><!--姓名-->
							<spring:message code="public.title.name"/>
						</th>
						<th width="50"><!--部门-->
							<spring:message code="public.title.deptName"/>
						</th>
						<th width="50"><!--职级-->
							<spring:message code="pa.insurance.title.postGrade"/>
						</th>
						<th width="50">
							<spring:message code="hr.viewPersonalInfo.title.STATUS_NAME" /><!--员工状态-->
						</th>
						<th width="50">
							<spring:message code="liang.hr.viewPersonalInfo.title.WHETHER_OR_NOT_TO_TRY"/><!-- 试用与否 -->
						</th>
						<%-- <th width="50"><!--状态-->
							<spring:message code="pa.insurance.title.status"/>
						</th> --%>
						<th width="50"><!--公司法人-->
							<spring:message code="pa.insurance.title.companyLegalPerson"/>
						</th>
						<th width="50"><!--开始月-->
							<spring:message code="pa.insurance.title.startMonth"/>
						</th>
						<th width="50"><!--结束月-->
							<spring:message code="pa.insurance.title.endMonth"/>
						</th>
						<th width="50"><!--数值-->
							<spring:message code="pa.insurance.title.dataValue"/>
						</th>
						<th width="20"><!--备注-->
							<spring:message code="hr.viewPromote.title.REMARK"/>
						</th>
						<th width="50">
							<li>
							<spring:message code="sys.affirm.title.affirmOperation"/>
							<!--<a class="delete" href="/pa/insurance/deleteInsuranceInputItemDataBatchInfo?PARAM_NO=${PARAM_NO}"
						       target="ajaxTodo" mask="true" width="800" height="600"><span>删除全部</span></a>  -->
							</li>
						</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${insuranceItemDataList}" var="itemData">
						<tr target="PARAM_DATA_NO" rel="${itemData.PARAM_DATA_NO}">
							<td>
								<input type="checkbox" id="c1" name="c1" value="${itemData.PARAM_DATA_NO}" />
							</td>
							<td class='td_center'>${itemData.EMPID}</td>
							<td class='td_center'>${itemData.LOCAL_NAME}</td>
							<td class='td_center'>${itemData.DEPT_NAME}</td>
							<td class='td_center'>${itemData.POST_GRADE_NAME}</td>
							<%-- <td>${itemData.STATUS_NAME}</td> --%>
							<td class='td_center'>${itemData.EMP_OFFICE}</td>
							<td class='td_center'>${itemData.IN_THE_DIFFERENCE}</td>
							<td class='td_center'>${itemData.CPNY_NAME}</td>
							<td>
<!-- 							<input name="START_MONTH_${itemData.PARAM_DATA_NO}" type="text" maxlength="10" size="10" value="${itemData.START_MONTH}" /> -->
								<ait:inputText name="START_MONTH_${itemData.PARAM_DATA_NO}"  style=" width:55px"
								inputType="date" id="START_MONTH_${itemData.PARAM_DATA_NO}" maxLength="6" value="${itemData.START_MONTH}"/>
							</td>
							<td>
<!-- 							<input name="END_MONTH_${itemData.PARAM_DATA_NO}" type="text" maxlength="10" size="10" value="${itemData.END_MONTH}" /> -->
								<ait:inputText name="END_MONTH_${itemData.PARAM_DATA_NO}" inputType="date"  style="width:55px"
								id="END_MONTH_${itemData.PARAM_DATA_NO}" maxLength="6" value="${itemData.END_MONTH}" onKeyUp="checkStartMonthAndEndMonth(this,'${itemData.PARAM_DATA_NO}')"  />
							</td>
							<td>
								<c:if test="${empty itemData.RETURN_VALUE}">
									<input name="RETURN_VALUE_${itemData.PARAM_DATA_NO}" type="text" maxlength="200" 
									 alt='<spring:message code="pa.insurance.title.defaltValueIsZero"/>' 
									 class="textInput" min="-99999999999999" value="0"  style="text-align:right;width:75px"/>
								</c:if>
								<c:if test="${not empty itemData.RETURN_VALUE}">
									<input name="RETURN_VALUE_${itemData.PARAM_DATA_NO}" type="text" maxlength="50" 
									class="textInput" min="-99999999999999"  value="${itemData.RETURN_VALUE}"  style="text-align:right;width:75px"/>
								</c:if>
							</td>
							<td>
								<input name="REMARK_${itemData.PARAM_DATA_NO}" type="text" maxlength="200"  size="10" value="${itemData.REMARK}" />
							</td>
							<td class='td_center'>
								<li>
								<c:if test="${toolbarInfo.DELETER == '1'}">
									<c:if test="${insuranceInputItemParamInfo.APPLY_FLAG eq 'Y' and rolesGroup ne 0}">
										<a class="delete" href="/pa/insurance/deleteInsuranceInputItemDataInfo?PARAM_DATA_NO=${itemData.PARAM_DATA_NO}"
											target="ajaxTodo" mask="true" width="800" height="600"><span>
											<spring:message code="button.delete"/><!--删除--></span>
										</a>
									</c:if>
									<c:if test="${insuranceInputItemParamInfo.APPLY_FLAG eq 'N' or empty insuranceInputItemParamInfo.APPLY_FLAG}">
										<a class="delete" href="/pa/insurance/deleteInsuranceInputItemDataInfo?PARAM_DATA_NO=${itemData.PARAM_DATA_NO}"
											target="ajaxTodo" mask="true" width="800" height="600"><span>
											<spring:message code="button.delete"/><!--删除--></span>
										</a>
									</c:if>
								</c:if>
								</li>
							</td>

						</tr>
					</c:forEach>
				</tbody>
			</c:if>
			<c:if test="${insuranceInputItemParamInfo.DISTINCT_FIELD ne 'PERSON_ID'}">
				<c:choose>
				  <c:when test="${insuranceInputItemParamInfo.DISTINCT_FIELD_2ND eq null}">
					<thead>
						<tr>
							<th width="5%"></th>
							<th width="50">${insuranceInputItemParamInfo.DISTINCT_FIELD_NAME}</th>
							<th width="120">
								<spring:message code="pa.insurance.title.companyLegalPerson"/><!--公司法人-->
							</th>
							<th width="50">
								<spring:message code="pa.insurance.title.startMonth"/><!--开始月-->
							</th>
							<th width="50">
								<spring:message code="pa.insurance.title.endMonth"/><!--结束月-->
							</th>
							<th width="30">
								<spring:message code="pa.insurance.title.dataValue"/><!--数值-->
							</th>
							<th width="20">
								<spring:message code="hr.viewPromote.title.REMARK"/><!--备注-->
							</th>
							<th width="50">
								<li>
								<spring:message code="sys.affirm.title.affirmOperation"/>
								<!-- <a class="delete" href="/pa/insurance/deleteInsuranceInputItemDataBatchInfo?PARAM_NO=${PARAM_NO}&type=1"
							       target="ajaxTodo" mask="true" width="800" height="600"><span>删除全部</span>	 -->						
								</li>
							</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${insuranceItemDataList}" var="itemParam">
							<tr target="PARAM_DATA_NO" rel="${itemParam.PARAM_DATA_NO}">
								<td>
									<input type="checkbox" id="c1" name="c1" value="${itemParam.PARAM_DATA_NO}" />
								</td>
								<td class='td_center'>${itemParam.FIELD1_NAME}</td>
								<td class='td_center'>${itemParam.CPNY_NAME}</td>
								<td>
									<%-- <input name="START_MONTH_${itemParam.PARAM_DATA_NO}" type="text" maxlength="10" size="10" value="${itemParam.START_MONTH}" /> --%>
									<ait:inputText name="START_MONTH_${itemParam.PARAM_DATA_NO}" inputType="date"  style=" width:55px"
									id="START_MONTH_${itemParam.PARAM_DATA_NO}" maxLength="6" value="${itemParam.START_MONTH}"/>
								</td>
								<td><%--
									<input name="END_MONTH_${itemParam.PARAM_DATA_NO}" type="text" maxlength="6" 
									size="16" value="${itemParam.END_MONTH}"  style=" width:55px"/> --%>
									<ait:inputText name="END_MONTH_${itemParam.PARAM_DATA_NO}" inputType="date"  style=" width:55px"
									  id="END_MONTH_${itemParam.PARAM_DATA_NO}" maxLength="6" value="${itemParam.END_MONTH}" onKeyUp="checkStartMonthAndEndMonth(this,'${itemData.PARAM_DATA_NO}')"/> 
								</td>
								<td>
									<c:if test="${empty itemParam.RETURN_VALUE}">
										<input name="RETURN_VALUE_${itemParam.PARAM_DATA_NO}" type="text" maxlength="200"  style="text-align:right;width:75px"
										    alt='<spring:message code="pa.insurance.title.defaltValueIsZero"/>'  class="textInput" min="-99999999999999"  value="0" />
									</c:if>
									<c:if test="${not empty itemParam.RETURN_VALUE}">
										<input name="RETURN_VALUE_${itemParam.PARAM_DATA_NO}" type="text"   style="text-align:right;width:75px"
										maxlength="200" class="textInput" min="-99999999999999"  value="${itemParam.RETURN_VALUE}" />
									</c:if>
								</td>
								<td>
									<input name="REMARK_${itemParam.PARAM_DATA_NO}" type="text" maxlength="200"  size="10" value="${itemParam.REMARK}" />
								</td>
								<td class='td_center'>
									<li>
									<c:if test="${toolbarInfo.DELETER == '1'}">
										<c:if test="${insuranceInputItemParamInfo.APPLY_FLAG eq 'Y' and rolesGroup ne 0}">
											<a class="delete" href="/pa/insurance/deleteInsuranceInputItemDataInfo?PARAM_DATA_NO=${itemParam.PARAM_DATA_NO}&type=1"
												target="ajaxTodo" mask="true" width="800" height="600"><span>
												<spring:message code="button.delete"/><!--删除--></span>
											</a>
										</c:if>
										<c:if test="${insuranceInputItemParamInfo.APPLY_FLAG eq 'N' or empty insuranceInputItemParamInfo.APPLY_FLAG}">
											<a class="delete" href="/pa/insurance/deleteInsuranceInputItemDataInfo?PARAM_DATA_NO=${itemParam.PARAM_DATA_NO}&type=1"
												target="ajaxTodo" mask="true" width="800" height="600"><span>
												<spring:message code="button.delete"/><!--删除--></span>
											</a>
										</c:if>
										
									</c:if>
									</li>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				  </c:when>
				  <c:otherwise>
				  	<thead>
						<tr>
							<th width="5%"></th>
							<th width="50">${insuranceInputItemParamInfo.DISTINCT_FIELD_NAME }</th>
							<th width="50">${insuranceInputItemParamInfo.DISTINCT_FIELD_2ND_NAME }</th>
							<th width="50">
								<spring:message code="pa.insurance.title.companyLegalPerson"/><!--公司法人-->
							</th>
							<th width="50">
								<spring:message code="pa.insurance.title.startMonth"/><!--开始月-->
							</th>
							<th width="50">
								<spring:message code="pa.insurance.title.endMonth"/><!--结束月-->
							</th>
							<th width="50">
								<spring:message code="pa.insurance.title.dataValue"/><!--数值-->
							</th>
							<th width="20">
								<spring:message code="hr.viewPromote.title.REMARK"/><!--备注-->
							</th>
							<th width="50">
								<li>
								<spring:message code="sys.affirm.title.affirmOperation"/>
								<!--<a class="delete" href="/pa/insurance/deleteInsuranceInputItemDataBatchInfo?PARAM_NO=${PARAM_NO}&type=1"
							       target="ajaxTodo" mask="true" width="800" height="600"><span>删除全部</span>	  -->						
								</li>
							</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${insuranceItemDataList}" var="itemParam">
							<tr target="PARAM_DATA_NO" rel="${itemParam.PARAM_DATA_NO}">
								<td>
									<input type="checkbox" id="c1" name="c1" value="${itemParam.PARAM_DATA_NO}" />
								</td>
								<td class='td_center'>${itemParam.FIELD1_NAME}</td>
								<td class='td_center'>${itemParam.FIELD2_NAME}</td>
								<td class='td_center'>${itemParam.CPNY_NAME}</td>
								<td>
								<%-- <input name="START_MONTH_${itemParam.PARAM_DATA_NO}" type="text" maxlength="10" size="10" value="${itemParam.START_MONTH}" /> --%>
									<ait:inputText name="START_MONTH_${itemData.PARAM_DATA_NO}" inputType="date"  style=" width:55px"
									id="START_MONTH_${itemData.PARAM_DATA_NO}" maxLength="6" value="${itemParam.START_MONTH}" />
								</td>
								<td>
								<%-- <input name="END_MONTH_${itemParam.PARAM_DATA_NO}" type="text" maxlength="10" size="10" value="${itemParam.END_MONTH}" /> --%>
									<ait:inputText name="END_MONTH_${itemData.PARAM_DATA_NO}" inputType="date"  style=" width:55px"
									id="END_MONTH_${itemData.PARAM_DATA_NO}" maxLength="6" value="${itemParam.END_MONTH}"  onKeyUp="checkStartMonthAndEndMonth(this,'${itemData.PARAM_DATA_NO}')"/>
								 
								
								</td>
								<td>
									<c:if test="${empty itemParam.RETURN_VALUE}">
										<input name="RETURN_VALUE_${itemParam.PARAM_DATA_NO}" type="text" maxlength="200"  style="text-align:right;width:75px"
										alt='<spring:message code="pa.insurance.title.defaltValueIsZero"/>' class="textInput" min="-99999999999999"  value="0" />
									</c:if>
									<c:if test="${not empty itemParam.RETURN_VALUE}">
										<input name="RETURN_VALUE_${itemParam.PARAM_DATA_NO}" type="text" maxlength="200" 
										class="textInput" min="-99999999999999"  value="${itemParam.RETURN_VALUE}"  style="text-align:right;width:75px"/>
									</c:if>
								</td>
								<td>
									<input name="REMARK_${itemParam.PARAM_DATA_NO}" type="text" maxlength="200"  size="10" value="${itemParam.REMARK}" />
								</td>
								<td class='td_center'>
									<li>
									<c:if test="${toolbarInfo.DELETER == '1'}">
										<c:if test="${insuranceInputItemParamInfo.APPLY_FLAG eq 'Y' and rolesGroup ne 0}">
											<a class="delete" href="/pa/insurance/deleteInsuranceInputItemDataInfo?PARAM_DATA_NO=${itemParam.PARAM_DATA_NO}&type=1"
												target="ajaxTodo" mask="true" width="800" height="600"><span>
												<spring:message code="button.delete"/><!--删除--></span>
											</a>
										</c:if>
										<c:if test="${insuranceInputItemParamInfo.APPLY_FLAG eq 'N' or empty insuranceInputItemParamInfo.APPLY_FLAG}">
											<a class="delete" href="/pa/insurance/deleteInsuranceInputItemDataInfo?PARAM_DATA_NO=${itemParam.PARAM_DATA_NO}&type=1"
												target="ajaxTodo" mask="true" width="800" height="600"><span>
												<spring:message code="button.delete"/><!--删除--></span>
											</a>
										</c:if>
									</c:if>
									</li>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				   </c:otherwise>
				</c:choose>	
			</c:if>
		</table>
	</form>
	                            
	<c:set value="/pa/insurance/viewInsuranceInputItemDataList?seach_PARAM_NO=${PARAM_NO}&seach_CPNY_ID=${CPNY_ID}&ITEM_NO=${ITEM_NO}" var="pageUrl" />
	<form id="pagerForm" method="post" action="${pageUrl}"><%--
		yearName="seach_insYear" monthName="seach_insMonth"
		--%>
		
		<input type="hidden" value="2" id="viewInsuranceInputItemDataList_pa0409" name="viewInsuranceInputItemDataList_pa0409"/>
		<input type="hidden" name="seach_insYear" id="seach_insYear" value="${insYear}" />
		<input type="hidden" name="seach_insMonth" id="seach_insMonth" value="${insMonth}" />
		<input type="hidden" name="pageNum" value="${pageNum}" />
		<input type="hidden" name="numPerPage" value="${numPerPage}" />
		<input type="hidden" name="ITEM_NO" value="${ITEM_NO}" />
	</form>
	<div class="panelBar">
		<div class="pages">
			<span><spring:message code="public.title.view"/><!-- 显示 --> </span>
				<select  class="combox" name="numPerPage" onchange="pageFromSea('${totalCount}');navTabPageBreak({numPerPage:this.value},'viewInsuranceInputItemData')">
						<option value="10"  <c:if test="${numPerPage == 10 }" >selected</c:if> >10</option>
				    <option value="30"  <c:if test="${numPerPage == 30 }" >selected</c:if> >30</option>
					<option value="50"  <c:if test="${numPerPage == 50 }" >selected</c:if> >50</option>
					<option value="150"  <c:if test="${numPerPage == 150 }" >selected</c:if> >150</option>
					<option value="200"  <c:if test="${numPerPage == 200 }" >selected</c:if> >200</option>
				    <option value="500"  <c:if test="${numPerPage == 500 }" >selected</c:if> >500</option>
					<option value="700"  <c:if test="${numPerPage == 700 }" >selected</c:if> >800</option>
					<option value="1000"  <c:if test="${numPerPage == 1000 }" >selected</c:if> >1000</option>
					<option value="2000"  <c:if test="${numPerPage == 2000 }" >selected</c:if> >2000</option>
					<option value="3000"  <c:if test="${numPerPage == 2000 }" >selected</c:if> >3000</option>
					<option value="5000"  <c:if test="${numPerPage == 2000 }" >selected</c:if> >5000</option>
				</select>
			<span><spring:message code="public.title.tiao"/><!--条-->,<spring:message code="public.title.gong"/><!--共-->
			${totalCount}<spring:message code="public.title.tiao"/><!--条--></span>	
		</div>
		<div class="pagination" rel="viewInsuranceInputItemData" totalCount="${totalCount}" numPerPage="${numPerPage}" 
		     currentPage="${pageNum}"></div>
	</div>
</div>