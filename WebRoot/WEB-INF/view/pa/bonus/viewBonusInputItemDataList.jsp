<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script type="text/javascript">
$(document).ready(function() {
		//seach_bonusInputYear   seach_bonusInputMonth
		var type=$("#viewBonusInputItemDataList_pa0610").val();
		if(type==""){
			var myDate = new Date();
			var year=myDate.getFullYear(); //当前年
			var month=myDate.getMonth(); 
			$("#seach_bonusInputYear ",navTab.getCurrentPanel()).val(year);  
			var curmonth=parseInt(parseInt(month)+1);
			$("#seach_bonusInputMonth ",navTab.getCurrentPanel()).val(curmonth);
		}
		var viewBonusInputItemDataListHref="";
		$("#bn_excelExport").click(function(){
			var empid=$("#seach_EMPID",navTab.getCurrentPanel()).val();
			var deptno=$("#seach_DEPTNO",navTab.getCurrentPanel()).val();
			var year=$("#seach_bonusInputYear",navTab.getCurrentPanel()).val();
			var month=$("#seach_bonusInputMonth",navTab.getCurrentPanel()).val();
			viewBonusInputItemDataListHref=$(this).attr("href");
			$(this).attr("href",$(this).attr("href")+"&empid="+empid+"&deptno="+deptno+"&year="+year+"&month="+month+"");
		});
		$("#bn_excelExport").focusout(function() {  
			  //alert($(this).attr("href"));
			  
			  $(this).attr("href",viewBonusInputItemDataListHref);
		});  
		
});
function validateCallbackUpdateBn(form,callback) {	                     
	var $form = $("#addBounsInputItemDataForm");
	
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
function submitFormViewBonus_pa0610(){
	$("#viewBonusInputItemDataList_pa0610").val("2");
	
  	var $from = $("#viewBonusInputItemDataListForm_pa0610");
  	$from.submit();
}

$(document).ready(function(){

	$("input[id^='END_MONTH'][name^='END_MONTH'][inputType='date']").removeClass("required");
});
function pageFromSea(a){
	var seach_EMPID=$("#seach_EMPID",navTab.getCurrentPanel()).val();
	var seach_DEPTNO=$("#seach_DEPTNO",navTab.getCurrentPanel()).val()
	var seach_bonusInputYear=$("#seach_bonusInputYear",navTab.getCurrentPanel()).val()
	var seach_bonusInputMonth=$("#seach_bonusInputMonth",navTab.getCurrentPanel()).val()
	var seach_ALIAS=$("#seach_ALIAS",navTab.getCurrentPanel()).val();
	var viewBonusInputItemDataList_pa0610="2";
	var seach_PARAM_NO=$("#seach_PARAM_NO",navTab.getCurrentPanel()).val();
	$("#pagerForm",navTab.getCurrentPanel()).attr("action", "/pa/bonus/viewBonusInputItemDataList?seach_EMPID="+seach_EMPID+"&seach_DEPTNO="+seach_DEPTNO+"&seach_bonusInputYear="+seach_bonusInputYear+"&seach_bonusInputMonth="+seach_bonusInputMonth+"&viewBonusInputItemDataList_pa0610=2&seach_PARAM_NO="+seach_PARAM_NO);
}
</script>

<c:if test="${bonusInputItemParamInfo.DISTINCT_FIELD eq 'PERSON_ID'}">
	<div class="pageHeader" style="border: 1px #B8D0D6 solid">
		<form id="viewBonusInputItemDataListForm_pa0610" onsubmit="return divSearch(this, 'viewBonusInputItemData');"
			action="/pa/bonus/viewBonusInputItemDataList?pageNum=1&seach_PARAM_NO=${PARAM_NO}&numPerPage=${numPerPage}"
			method="post" rel="pagerForm">
			<input type="hidden" value="${type}" id="viewBonusInputItemDataList_pa0610" name="viewBonusInputItemDataList_pa0610"/>
			
			<div class="searchBar">
				<table class="searchContent">
					<tr>
						<td>
							<spring:message code="public.title.name"/><!--姓名-->/
							<spring:message code="public.title.empId"/><!--工号-->:
							<input type="text" id="seach_EMPID" value="${EMPID}" name="seach_EMPID" />
						</td>
						<td>
							<spring:message code="public.title.deptName"/><!--部门-->:
							<ait:deptTree  selected="${DEPTNO}" name="seach_DEPTNO" limit="pa" />
						</td>
						<td>
							<c:if test="${bonusInputItemParamInfo.DISTINCT_FIELD eq 'PERSON_ID'}">
								<span style="margin-left:3%;">
									<spring:message code="zxc.pa.insurance.title.BASE_MONTH"/>: <!--基准月-->
									<ait:date yearName="seach_bonusInputYear" monthName="seach_bonusInputMonth" yearSelected="${bonusInputYear}" 
												monthSelected="${bonusInputMonth}" limit="all"/>
								</span>
							</c:if>
						</td>
					</tr>
				</table>
				<div class="subBar">
					<ul>
						<li>
							<div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="submitFormViewBonus_pa0610()">
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
</c:if>

<div class="pageContent"
	style="border-left: 1px #B8D0D6 solid; border-right: 1px #B8D0D6 solid">
	<form name="addBounsInputItemDataForm" id="addBounsInputItemDataForm"
		method="post" action="/pa/bonus/updateBonusInputItemDataInfo"
		class="pageForm required-validate"
		onsubmit="return validateCallbackUpdateBn(this,navTabAjaxDone);">
		<input type="hidden" value="" id="excelExportHidden" name="excelExportHidden"/>
		<div class="formBar">
			<label style="float: left">
				<input type="checkbox" class="checkboxCtrl" group="c1" />
				<spring:message code="pa.salary.title.allChecked"/><!--全选-->
			</label>
			<ul>
				<li>
			 	  <c:if test="${bonusInputItemParamInfo.DISTINCT_FIELD eq 'PERSON_ID'}">
			 	  	
						<a class="buttonActive"   
							href="/pa/excelImport/importInsuranceInputItemData?id=${PARAM_NO}&CPNY_ID=${CPNY_ID}&importFunName=importBonusInputItemDataExcelIsNotNull" target="dialog" mask="true" width="500" height="200" >
							<span><spring:message code="ar.addempshift.title.excelimport"/><!-- EXCEL导入 --></span>
						</a>
				  </c:if> 
				  <c:if test="${bonusInputItemParamInfo.DISTINCT_FIELD ne 'PERSON_ID'}">
					  	<c:choose>
					  	<c:when test="${bonusInputItemParamInfo.DISTINCT_FIELD_2ND eq null}">
						  	<a class="buttonActive"   
								href="/pa/excelImport/importInsuranceInputItemData?
								id=${PARAM_NO}
								&CPNY_ID=${CPNY_ID}
								&importFunName=importBonusInputItemDataExcelIsNull
								&FIELD1_NAME=${bonusInputItemParamInfo.DISTINCT_FIELD }" target="dialog" mask="true" width="500" height="200" >
								<span><spring:message code="ar.addempshift.title.excelimport"/><!-- EXCEL导入 --></span>
							</a>
					  	</c:when>
						<c:otherwise>
							<a class="buttonActive"
								href="/pa/excelImport/importInsuranceInputItemData?id=${PARAM_NO}&CPNY_ID=${CPNY_ID}
								&importFunName=importBonusInputItemDataExcelIsNull2
								&FIELD1_NAME=${bonusInputItemParamInfo.DISTINCT_FIELD }&
								&FIELD2_NAME=${bonusInputItemParamInfo.DISTINCT_FIELD_2ND }" target="dialog" mask="true" width="500" height="200" >
								<span><spring:message code="ar.addempshift.title.excelimport"/><!-- EXCEL导入 --></span>
							</a>
						</c:otherwise>
						</c:choose>
				  </c:if>
				</li>
				<li>
				 <c:if test="${bonusInputItemParamInfo.DISTINCT_FIELD eq 'PERSON_ID'}">
						<a id="bn_excelExport" class="buttonActive"
							href="/pa/excelExport/exportBonusInputItemDataExcelIsNotNull?id=${PARAM_NO}&CPNY_ID=${CPNY_ID}" >
							<span><spring:message code="pa.insurance.title.excelExport"/><!--EXCEL导出  --></span>
						</a>
				  </c:if>
				  <c:if test="${bonusInputItemParamInfo.DISTINCT_FIELD ne 'PERSON_ID'}">
					  	<c:choose>
					  	<c:when test="${bonusInputItemParamInfo.DISTINCT_FIELD_2ND eq null}">
						  	<a  class="buttonActive"
								href="/pa/excelExport/exportBonusInputItemDataExcelIsNull?seach_PARAM_NO=${PARAM_NO}&seach_CPNY_ID=${CPNY_ID}">
								<span><spring:message code="pa.insurance.title.excelExport"/><!--EXCEL导出  --></span>
							</a>
					  	</c:when>
						<c:otherwise>
							<a class="buttonActive"
								href="/pa/excelExport/exportBonusInputItemDataExcelIsNull?seach_PARAM_NO=${PARAM_NO}&seach_CPNY_ID=${CPNY_ID}">
								<span><spring:message code="pa.insurance.title.excelExport"/><!--EXCEL导出  --></span>
							</a>
						</c:otherwise>
						</c:choose>
				  </c:if>	
				</li>
				<li>
					<c:if test="${bonusInputItemParamInfo.DISTINCT_FIELD eq 'PERSON_ID'}">
							<a class="buttonActive"
								href="/pa/excelExport/exportBonusInputItemDataExcelIsNotNullModule?id=${PARAM_NO}&CPNY_ID=${CPNY_ID}"  ><span>
								<spring:message code="pa.insurance.title.downloadImportTemplate"/><!--下载导入模板--></span>
							</a>
					  </c:if>
					  <c:if test="${bonusInputItemParamInfo.DISTINCT_FIELD ne 'PERSON_ID'}">
						  	<c:choose>
						  	<c:when test="${bonusInputItemParamInfo.DISTINCT_FIELD_2ND eq null}">
							  	<a class="buttonActive"
									href="/pa/excelExport/exportBonusInputItemDataExcelIsNullModule?id=${PARAM_NO}&CPNY_ID=${CPNY_ID}&FIELD1_NAME=${bonusInputItemParamInfo.DISTINCT_FIELD_NAME}&FIELD1=${bonusInputItemParamInfo.DISTINCT_FIELD}"
									><span><spring:message code="pa.insurance.title.downloadImportTemplate"/><!--下载导入模板--></span>
								</a>
						  	</c:when>
							<c:otherwise>
								<a class="buttonActive"
									href="/pa/excelExport/exportBonusInputItemDataExcelIsNullModule?id=${PARAM_NO}&CPNY_ID=${CPNY_ID}
									&FIELD1_NAME=${bonusInputItemParamInfo.DISTINCT_FIELD_NAME}
									&FIELD2_NAME=${bonusInputItemParamInfo.DISTINCT_FIELD_2ND_NAME }
									&FIELD1=${bonusInputItemParamInfo.DISTINCT_FIELD}
									&FIELD2=${bonusInputItemParamInfo.DISTINCT_FIELD_2ND }"
									><span><spring:message code="pa.insurance.title.downloadImportTemplate"/><!--下载导入模板--></span>
								</a>
							</c:otherwise>
							</c:choose>
					  </c:if>
				</li>
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
					<a class="buttonActive"
						href="/pa/bonus/addBonusInputItemDataView?seach_PARAM_NO=${PARAM_NO}"
						target="dialog" mask="true" width="600" height="400" rel="viewBonusInputItemData">
						<span>
						<spring:message code="button.add"/><!--添加-->
						</span>
					</a>
				</li>
				<!--<li>
					<a class="add" href="/pa/bonus/viewBonusInputItemDataPersonList?pageNum=1&PA_MONTH=${month}"  target="navTab"><span>个人别录入</span> </a>
				</li>-->
				<input id="seach_PARAM_NO" name="seach_PARAM_NO" type="hidden"
					size="30" value="${PARAM_NO}" />
			</ul>
		</div>
		<table class="table" width="99%" layoutH="200">
			<c:if test="${bonusInputItemParamInfo.DISTINCT_FIELD eq 'PERSON_ID'}">
				<thead>
					<tr>
						<th width="5%"></th>
						<th width="50">
							<spring:message code="public.title.empId"/><!--工号-->
						</th>
						<th width="50">
							<spring:message code="public.title.name"/><!--姓名-->
						</th>
						<th width="50">
							<spring:message code="public.title.deptName"/><!--部门-->
						</th>
						<th width="50">
							<spring:message code="pa.insurance.title.postGrade"/><!--职级-->
						</th>
						<th width="50">
							<spring:message code="hr.viewPersonalInfo.title.STATUS_NAME" /><!--员工状态-->
						</th>
						<th width="50">
							<spring:message code="liang.hr.viewPersonalInfo.title.WHETHER_OR_NOT_TO_TRY"/><!-- 试用与否 -->
						</th>
						<%--
						<th width="50">
							<spring:message code="pa.insurance.title.status"/><!--状态-->
						</th>
						--%>
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
								<!--<a class="delete" href="/pa/bonus/deleteBonusInputItemDataBatchInfo?PARAM_NO=${PARAM_NO}"
						       target="ajaxTodo" mask="true" width="800" height="600"><span><spring:message code="pa.bonus.title.deleteAllInfo"/><!--删除全部--></span>
							</li>
						</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${bonusInputItemDataList}" var="itemData">
						<tr target="PARAM_DATA_NO" rel="${itemData.PARAM_DATA_NO}">
							<td class="td_center">
								<input type="checkbox" id="c1" name="c1"
									value="${itemData.PARAM_DATA_NO}" />
							</td>
							<td class="td_center">
								${itemData.EMPID}
							</td>
							<td class="td_center">
								${itemData.LOCAL_NAME}
							</td>
							<td class="td_center">
								${itemData.DEPT_NAME}
							</td>
							<td class="td_center">
								${itemData.POST_GRADE_NAME}
							</td>
							<%--
							<td>
								${itemData.STATUS_NAME}
							</td>
							--%>
							<td class="td_center">
								${itemData.EMP_OFFICE}
							</td>
							<td class="td_center">
								${itemData.IN_THE_DIFFERENCE}
							</td>
							<td class="td_center">
								${itemData.CPNY_NAME}
							</td>
							<td >
								<%--
								<input name="START_MONTH_${itemData.PARAM_DATA_NO}" type="text"
									maxlength="10" size="10" value="${itemData.START_MONTH}" />
								--%>
								<ait:inputText name="START_MONTH_${itemData.PARAM_DATA_NO}" id="START_MONTH_${itemData.PARAM_DATA_NO}" 
									inputType="date" maxLength="10" inputSize="10" value="${itemData.START_MONTH}"  style="width:55px"/>
							</td>
							<td>
								<%--
								<input name="END_MONTH_${itemData.PARAM_DATA_NO}" type="text"
									maxlength="10" size="10" value="${itemData.END_MONTH}" />
								--%>
								<ait:inputText name="END_MONTH_${itemData.PARAM_DATA_NO}" id="END_MONTH_${itemData.PARAM_DATA_NO}" 
									inputType="date" maxLength="10" inputSize="10" value="${itemData.END_MONTH}"  style=" width:55px"/>
							</td>
							<td>
								<c:if test="${empty itemData.RETURN_VALUE}">
									<input name="RETURN_VALUE_${itemData.PARAM_DATA_NO}"
										type="text" maxlength="200" alt='<spring:message code="pa.insurance.title.defaltValueIsZero"/>' 
										class="textInput" min="0" value="0"  style="text-align:right;width:75px"/>
								</c:if>
								<c:if test="${not empty itemData.RETURN_VALUE}">
									<input name="RETURN_VALUE_${itemData.PARAM_DATA_NO}"
										type="text" maxlength="50" class="textInput" min="0" value="${itemData.RETURN_VALUE}"  style="text-align:right;width:75px"/>
								</c:if>
							</td>
							<td>
								<input name="REMARK_${itemData.PARAM_DATA_NO}" type="text" maxlength="200"  size="10"
										value="${itemData.REMARK}" />
							</td>
							<td>
								<li>
								<c:if test="${toolbarInfo.DELETER == '1'}">
									<a class="delete"
										href="/pa/bonus/deleteBonusInputItemDataInfo?PARAM_DATA_NO=${itemData.PARAM_DATA_NO}"
										target="ajaxTodo" mask="true" width="800" height="600"><span><spring:message code="button.delete"/><!--删除--></span>
									</a>
								</c:if>
								</li>
							</td>

						</tr>
					</c:forEach>
				</tbody>
			</c:if>
			<c:if test="${bonusInputItemParamInfo.DISTINCT_FIELD ne 'PERSON_ID'}">
				<c:choose>
					<c:when
						test="${bonusInputItemParamInfo.DISTINCT_FIELD_2ND eq null}">
						<thead>
							<tr>
								<th width="5%"></th>
								<th width="50">
									${bonusInputItemParamInfo.DISTINCT_FIELD_NAME }
								</th>
								<th width="50">
									<spring:message code="pa.insurance.title.companyLegalPerson"/><!--公司法人-->
								</th>
								<th width="50">
									<spring:message code="pa.insurance.title.startMonth"/><!--开始月-->
								</th>
								<th width="50">
									<spring:message code="pa.insurance.title.endMonth"/><!--开始月-->
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
										<!--<a class="delete" href="/pa/bonus/deleteBonusInputItemDataBatchInfo?PARAM_NO=${PARAM_NO}&type=1"
							       target="ajaxTodo" mask="true" width="800" height="600"><span><spring:message code="pa.bonus.title.deleteAllInfo"/><!--删除全部</span>--> 
									</li>
								</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${bonusInputItemDataList}" var="itemParam">
								<tr target="PARAM_DATA_NO" rel="${itemParam.PARAM_DATA_NO}">
									<td>
										<input type="checkbox" id="c1" name="c1"
											value="${itemParam.PARAM_DATA_NO}" />
									</td>
									<td>
										${itemParam.FIELD1_NAME}
									</td>
									<td>
										${itemParam.CPNY_NAME}
									</td>
									<td>
										<%--
										<input name="START_MONTH_${itemParam.PARAM_DATA_NO}"
											type="text" maxlength="10" size="10"
											value="${itemParam.START_MONTH}" />
										--%>
										<ait:inputText name="START_MONTH_${itemParam.PARAM_DATA_NO}" id="START_MONTH_${itemParam.PARAM_DATA_NO}" 
											inputType="date" maxLength="10" inputSize="10" value="${itemParam.START_MONTH}"  style=" width:55px"/>
									</td>
									<td>
										<%--
										<input name="END_MONTH_${itemParam.PARAM_DATA_NO}" type="text"
											maxlength="10" size="10" value="${itemParam.END_MONTH}" />
										--%>
										<ait:inputText name="END_MONTH_${itemParam.PARAM_DATA_NO}" id="END_MONTH_${itemParam.PARAM_DATA_NO}" 
											inputType="date" maxLength="10" inputSize="10" value="${itemParam.END_MONTH}"  style=" width:55px"/>
									</td>
									<td>
										<c:if test="${empty itemParam.RETURN_VALUE}">
											<input name="RETURN_VALUE_${itemParam.PARAM_DATA_NO}" style="text-align:right;width:75px"
												type="text" maxlength="200" alt="默认值:0" class="textInput" min="0" value="0" />
										</c:if>
										<c:if test="${not empty itemParam.RETURN_VALUE}">
											<input name="RETURN_VALUE_${itemParam.PARAM_DATA_NO}"
												type="text" maxlength="200" class="textInput" min="0" 
												value="${itemParam.RETURN_VALUE}"  style="text-align:right;width:75px"/>
										</c:if>
									</td>
									<td>
										<input name="REMARK_${itemParam.PARAM_DATA_NO}" type="text" maxlength="200"  size="10"
											value="${itemParam.REMARK}" />
									</td>
									<td>
										<li>
										<c:if test="${toolbarInfo.DELETER == '1'}">
											<a class="delete"
												href="/pa/bonus/deleteBonusInputItemDataInfo?PARAM_DATA_NO=${itemParam.PARAM_DATA_NO}&type=1"
												target="ajaxTodo" mask="true" width="800" height="600">
												<span>
												<spring:message code="button.delete"/><!--删除-->
												</span>
											</a>
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
								<th width="50">
									${bonusInputItemParamInfo.DISTINCT_FIELD_NAME }
								</th>
								<th width="50">
									${bonusInputItemParamInfo.DISTINCT_FIELD_2ND_NAME }
								</th>
								<th width="50">
									<spring:message code="pa.insurance.title.companyLegalPerson"/><!--公司法人-->
								</th>
								<th width="50">
									<spring:message code="pa.insurance.title.startMonth"/><!--开始月-->
								</th>
								<th width="50">
									<spring:message code="pa.insurance.title.endMonth"/><!--开始月-->
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
										<!--<a class="delete" href="/pa/bonus/deleteBonusInputItemDataBatchInfo?PARAM_NO=${PARAM_NO}&type=1"
								       target="ajaxTodo" mask="true" width="800" height="600"><span><spring:message code="pa.bonus.title.deleteAllInfo"/><!--删除全部</span>  -->
									</li>
								</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${bonusInputItemDataList}" var="itemParam">
								<tr target="PARAM_DATA_NO" rel="${itemParam.PARAM_DATA_NO}">
									<td>
										<input type="checkbox" id="c1" name="c1"
											value="${itemParam.PARAM_DATA_NO}" />
									</td>
									<td>
										${itemParam.FIELD1_NAME}
									</td>
									<td>
										${itemParam.FIELD2_NAME}
									</td>
									<td>
										${itemParam.CPNY_NAME}
									</td>
									<td>
										<%--
										<input name="START_MONTH_${itemParam.PARAM_DATA_NO}"
											type="text" maxlength="10" size="10"
											value="${itemParam.START_MONTH}" />
										--%>
										<ait:inputText name="START_MONTH_${itemParam.PARAM_DATA_NO}" id="START_MONTH_${itemParam.PARAM_DATA_NO}" 
											inputType="date" maxLength="10" inputSize="10" value="${itemParam.START_MONTH}"   style="width:55px"/>
									</td>
									<td>
										<%--
										<input name="END_MONTH_${itemParam.PARAM_DATA_NO}" type="text"
											maxlength="10" size="10" value="${itemParam.END_MONTH}" />
										--%>
										<ait:inputText name="END_MONTH_${itemParam.PARAM_DATA_NO}" id="END_MONTH_${itemParam.PARAM_DATA_NO}" 
											inputType="date" maxLength="10" inputSize="10" value="${itemParam.END_MONTH}"  style=" width:55px"/>
									</td>
									<td>
										<c:if test="${empty itemParam.RETURN_VALUE}">
											<input name="RETURN_VALUE_${itemParam.PARAM_DATA_NO}" style="text-align:right;width:75px"
												type="text" maxlength="200" class="textInput" min="0" alt='<spring:message code="pa.insurance.title.defaltValueIsZero"/>' value="0" />
										</c:if>
										<c:if test="${not empty itemParam.RETURN_VALUE}">
											<input name="RETURN_VALUE_${itemParam.PARAM_DATA_NO}"
												type="text" maxlength="200" class="textInput" min="0" 
												value="${itemParam.RETURN_VALUE}"  style="text-align:right;width:75px"/>
										</c:if>
									</td>
									<td>
										<input name="REMARK_${itemParam.PARAM_DATA_NO}" type="text" maxlength="200"  size="10"
											value="${itemParam.REMARK}" />
									</td>
									<td>
									<c:if test="${toolbarInfo.DELETER == '1'}">
										<li>
											<a class="delete"
												href="/pa/bonus/deleteBonusInputItemDataInfo?PARAM_DATA_NO=${itemParam.PARAM_DATA_NO}&type=1"
												target="ajaxTodo" mask="true" width="800" height="600"><span><spring:message code="button.delete"/><!--删除--></span>
											</a>
										</li>
									</c:if>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</c:otherwise>
				</c:choose>
			</c:if>
		</table>
	</form>
	<c:set
		value="/pa/bonus/viewBonusInputItemDataList?seach_PARAM_NO=${PARAM_NO}"
		var="pageUrl" />
	<form id="pagerForm" method="post" action="${pageUrl}">
		<input type="hidden" name="pageNum" value="${pageNum}" />
		<input type="hidden" name="numPerPage" value="${numPerPage}" />
	</form>
	<div class="panelBar">
		<div class="pages">
			<span><spring:message code="public.title.view"/><!-- 显示 --></span>
			<select id="numPerPage" class="combox" name="numPerPage"
				onchange="pageFromSea('${totalCount}');navTabPageBreak({numPerPage:this.value},'viewBonusInputItemData')">
				<option value="10"
					<c:if test="${numPerPage == 10 }" >selected</c:if>>
					10
				</option>
				<option value="20"
					<c:if test="${numPerPage == 20 }" >selected</c:if>>
					20
				</option>
				<option value="30"
					<c:if test="${numPerPage == 30 }" >selected</c:if>>
					30
				</option>
				<option value="1000"
					<c:if test="${numPerPage == 1000 }" >selected</c:if>>
					1000
				</option>
			</select>
			<span><spring:message code="public.title.tiao"/><!--条-->,<spring:message code="public.title.gong"/><!--共-->
			${totalCount}<spring:message code="public.title.tiao"/><!--条--></span>	
		</div>
		<div class="pagination" rel="viewBonusInputItemData"
			totalCount="${totalCount}" numPerPage="${numPerPage}"
			currentPage="${pageNum}"></div>
	</div>
</div>