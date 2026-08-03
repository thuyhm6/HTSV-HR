			
<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>

<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script src="/resources/js/jquery/jquery.number.js" type="text/javascript"></script>
<script type="text/javascript">

$(document).ready(function() {
		var type=$("#viewPaBasicItemDataList_pa0610").val();
		
		if(type==""){
			var myDate = new Date();
			var year=myDate.getFullYear(); //当前年
			var month=myDate.getMonth(); 
			$("#seach_paBasicYear",navTab.getCurrentPanel()).val(year);  
			var curmonth=parseInt(parseInt(month)+1);
			var curmonthText="";
			if(parseInt(curmonth)<10){
				
				curmonthText="0"+curmonth;
			}
			$("#seach_paBasicMonth",navTab.getCurrentPanel()).val(curmonthText);
		}
	
		var viewPaBasicItemDataListHref="";
		$("#viewPaBasicItemDataListbn_excelExport").click(function(){
			var empid=$("#seach_EMPID",navTab.getCurrentPanel()).val();
			var deptno=$("#seach_DEPTNO",navTab.getCurrentPanel()).val();
			var year=$("#seach_paBasicYear",navTab.getCurrentPanel()).val();
			var month=$("#seach_paBasicMonth",navTab.getCurrentPanel()).val();
			viewPaBasicItemDataListHref=$(this).attr("href");
			$(this).attr("href",$(this).attr("href")+"&empid="+empid+"&deptno="+deptno+"&year="+year+"&month="+month+"");
		});
		$("#viewPaBasicItemDataListbn_excelExport").focusout(function() {  
			  //alert($(this).attr("href"));
			  
			  $(this).attr("href",viewPaBasicItemDataListHref);
		});  
		
});


function validateCallbackPaBasicItemDataForm(form,callback) {	
	var $form = $("#viewPaBasicItemDataForm");
	
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
	}else{
		return false;
	}
	
	
}
function submitFormViewPaBasic_pa0610(){
	$("#viewPaBasicItemDataList_pa0610").val("2");
  	var $from = $("#viewPaBasicItemDataListForm_pa0610");
  	$from.submit();
}
function pageFromSea(a){
	//$("#idName",navTab.getCurrentPanel()).val();
	var seach_EMPID=$("#seach_EMPID",navTab.getCurrentPanel()).val()
	var seach_DEPTNO=$("#seach_DEPTNO",navTab.getCurrentPanel()).val();
	var seach_paBasicYear=$("#seach_paBasicYear",navTab.getCurrentPanel()).val();
	var seach_paBasicMonth=$("#seach_paBasicMonth",navTab.getCurrentPanel()).val();
	var seach_PARAM_NO=$("#seach_PARAM_NO",navTab.getCurrentPanel()).val();
	var viewPaBasicItemDataList_pa0610="2";
															
	$("#pagerForm",navTab.getCurrentPanel()).attr("action", "/pa/wagebase/viewPaBasicItemDataList?seach_EMPID="+seach_EMPID+"&seach_DEPTNO="+seach_DEPTNO+"&seach_paBasicYear="+seach_paBasicYear+"&seach_paBasicMonth="+seach_paBasicMonth+"&viewPaBasicItemDataList_pa0610=2&seach_PARAM_NO="+seach_PARAM_NO);
}
function aa(){
	var form=$("#viewPaBasicItemDataForm");
	form.submit();
}

function delPaBasicItemDataCallback(form,callback) {
	var $form = $("#viewPaBasicItemDataForm");
	if (!$form.valid()) {
		return false;
	}
	var checked=false;
	var ids= document.getElementsByName("c1");
	//alert(ids.length);
	for(var i=0;i<ids.length;i++){
		if(ids[i].checked){
		
			checked=true;
			
		}
		
	}
	if(!checked){
		alertMsg.error('<spring:message code="alert.message.ess.affirmApply.chooseApplyRecordFirstForBatch"/>'); 
		return false;
	}
    $form.attr("action","/pa/salary/deleteCheckPaBasicItemData");
    alertMsg.confirm ("确定要批量删除吗?",{
        okCall:function(){
	    	$.ajax({
				type: form.method || 'POST',
				url:$form.attr("action"),
				data:$form.serializeArray(),
				dataType:"json",
				cache: false,
				//success: callback || DWZ.ajaxDone,
				
				success: function(data){ //请求成功后处理函数。
					if(data.statusCode=="200"){
						navTabSearch("viewPaBasicItemDataListForm_pa0610");
						alertMsg.correct(data.message);
					}else{
						if(data.result=="2"){
							alertMsg.info(data.message);
						}else{
							alertMsg.error(data.message);
						}
					}   
		   	 	}  ,
				error: DWZ.ajaxError
			});
        }});
	return false;
}
</script>
<a id="importExcelDialog_pa503" href="#" target="dialog" mask="true">
	<span id="pa0610" style="display: none"></span></a> 
<a id="importExcel_pa0503"  href="#" target="navTab" mask="true"><span style="display:none;">基础项目数据导入结果</span></a>
			<div class="pageHeader">
				<form id="viewPaBasicItemDataListForm_pa0610" onsubmit="return divSearch(this, 'viewPaBasicItemData');" action="/pa/wagebase/viewPaBasicItemDataList?pageNum=1&seach_PARAM_NO=${PARAM_NO}&seach_CPNY_ID=${CPNY_ID}&numPerPage=${numPerPage}"
				 method="post" rel="pagerForm" name="viewPaBasicItemDataListForm_pa0610">
					<input type="hidden" value="${type}" id="viewPaBasicItemDataList_pa0610" name="viewPaBasicItemDataList_pa0610"/>
					<div class="searchBar">
						<table class="searchContent">
							<tr>
						<c:if test="${paBasicItemDataInfo.DISTINCT_FIELD eq 'PERSON_ID'}">
								<td>
									<spring:message code="public.title.empId"/><!--工号-->/
									<spring:message code="public.title.name"/><!--姓名-->:
									<input id="seach_EMPID" type="text" value="${EMPID}"  name="seach_EMPID"/>
								</td>
								
								<td>
									<spring:message code="public.title.deptName"/><!--部门-->:
									<c:if test="${loginName ne 'IT'}">
										<ait:deptTree  selected="${DEPTNO}" name="seach_DEPTNO" limit="pa"/>
									</c:if>
									<c:if test="${loginName eq 'IT'}">
										<ait:deptTree  selected="${DEPTNO}" name="seach_DEPTNO" limit="hr"/>
									</c:if>
								</td>
							</c:if>
								<td>
										<span style="margin-left:3%;">
											<spring:message code="zxc.pa.insurance.title.BASE_MONTH"/>: <!--基准月-->
											<ait:date yearName="seach_paBasicYear" monthName="seach_paBasicMonth" yearSelected="${paBasicYear}" monthSelected="${paBasicMonth}" limit="all"/>
													
										</span>
									
								</td>
								 <td>
									备注:
									<input id="seach_REMARK" type="text" value="${REMARK}"  name="seach_REMARK"/>
								 </td>
							</tr>
						</table>
						<div class="subBar">
							<ul>
								<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="submitFormViewPaBasic_pa0610()">
                                <spring:message code="public.title.search"/><!--检索--></button></div></div></li>
							</ul>
						</div>
					</div>
				</form>
			</div>


<div class="pageContent">

	<form name="viewPaBasicItemDataForm" id="viewPaBasicItemDataForm" method="post"
		action="/pa/wagebase/updatePaBasicItemDataInfo"
		class="pageForm required-validate"
	
		onsubmit="return validateCallbackPaBasicItemDataForm(this, navTabAjaxDone);">
		<div class="formBar">
			<label style="float: left">
				<input type="checkbox" class="checkboxCtrl" group="c1" />
				<spring:message code="pa.salary.title.allChecked"/><!--全选-->
				<spring:message code="pa.insurance.title.defaltValue"/><!-- 默认值 -->：
				${paBasicItemDataInfo.DEFAULT_VAL}
			</label>
			<ul>
				  <li>
			 	  <c:if test="${paBasicItemDataInfo.DISTINCT_FIELD eq 'PERSON_ID'}">
						<a  class="buttonActive"
							href="/pa/excelImport/importPaBasicItemExcelData?id=${PARAM_NO}&CPNY_ID=${CPNY_ID}&importFunName=/importPaBasicItemDataExcelIsNotNull" target="dialog" mask="true" width="500" height="200" >
							<span><spring:message code="ar.addempshift.title.excelimport"/><!-- EXCEL导入 --></span>
						</a>
				  </c:if>
				  <%--
				  <c:if test="${paBasicItemDataInfo.DISTINCT_FIELD ne 'PERSON_ID'}">
					  	<c:choose>
					  	<c:when test="${paBasicItemDataInfo.DISTINCT_FIELD_2ND eq null}">
						  	<a class="buttonActive"
								href="/pa/excelImport/importInsuranceInputItemData?id=${PARAM_NO}&CPNY_ID=${CPNY_ID}&importFunName=importPaBasicItemDataExcelIsNull&FIELD1_NAME=${paBasicItemDataInfo.DISTINCT_FIELD}" target="dialog" mask="true" width="500" height="200" >
								<span><spring:message code="ar.addempshift.title.excelimport"/><!-- EXCEL导入 --></span>
							</a>
					  	</c:when>
						<c:otherwise>
							<a class="buttonActive"
								href="/pa/excelImport/importInsuranceInputItemData?id=${PARAM_NO}&CPNY_ID=${CPNY_ID}&importFunName=importPaBasicItemDataExcelIsNull2&FIELD1_NAME=${paBasicItemDataInfo.DISTINCT_FIELD}&FIELD2_NAME=${paBasicItemDataInfo.DISTINCT_FIELD_2ND}" target="dialog" mask="true" width="500" height="200" >
								<span><spring:message code="ar.addempshift.title.excelimport"/><!-- EXCEL导入 --></span>
							</a>
						</c:otherwise>
						</c:choose>
				  </c:if>
				   --%>
				</li>
				 <li>
			 	  <c:if test="${paBasicItemDataInfo.DISTINCT_FIELD eq 'PERSON_ID'}">
						<a id="viewPaBasicItemDataListbn_excelExport" class="buttonActive"
							href="/pa/excelExport/exportPaBasicInputItemDataExcelIsNotNull?id=${PARAM_NO}&CPNY_ID=${CPNY_ID}">
							<span><spring:message code="pa.insurance.title.excelExport"/><!--EXCEL导出  --></span>
						</a>
				  </c:if>
				  <%--
				  <c:if test="${paBasicItemDataInfo.DISTINCT_FIELD ne 'PERSON_ID'}">
					  	<c:choose>
					  	<c:when test="${paBasicItemDataInfo.DISTINCT_FIELD_2ND eq null}">
						  	<a id="viewPaBasicItemDataListbn_excelExport" class="buttonActive"
								href="/pa/excelExport/exportPaBasicInputItemDataExcelIsNull?seach_PARAM_NO=${PARAM_NO}&seach_CPNY_ID=${CPNY_ID}">
								<span><spring:message code="pa.insurance.title.excelExport"/><!--EXCEL导出  --></span>
							</a>
					  	</c:when>
						<c:otherwise>
							<a id="viewPaBasicItemDataListbn_excelExport" class="buttonActive"
								href="/pa/excelExport/exportPaBasicInputItemDataExcelIsNull?seach_PARAM_NO=${PARAM_NO}&seach_CPNY_ID=${CPNY_ID}">
								<span><spring:message code="pa.insurance.title.excelExport"/><!--EXCEL导出  --></span>
							</a>
						</c:otherwise>
						</c:choose>
				  </c:if>
				   --%>
				</li>
				<li>
					<c:if test="${paBasicItemDataInfo.DISTINCT_FIELD eq 'PERSON_ID'}">
							<a class="buttonActive"
								href="/pa/excelExport/exportPaBasicItemDataExcelIsNotNullModule?id=${PARAM_NO}&CPNY_ID=${CPNY_ID}"  ><span>
								<spring:message code="pa.insurance.title.downloadImportTemplate"/><!--下载导入模板--></span>
							</a>
					  </c:if>
					  <%--
					  <c:if test="${paBasicItemDataInfo.DISTINCT_FIELD ne 'PERSON_ID'}">
						  	<c:choose>
						  	<c:when test="${paBasicItemDataInfo.DISTINCT_FIELD_2ND eq null}">
							  	<a class="buttonActive"
									href="/pa/excelExport/exportPaBasicItemDataExcelIsNullModule?id=${PARAM_NO}&CPNY_ID=${CPNY_ID}&FIELD1_NAME=${paBasicItemDataInfo.DISTINCT_FIELD_NAME}&FIELD1=${paBasicItemDataInfo.DISTINCT_FIELD}"
									><span><spring:message code="pa.insurance.title.downloadImportTemplate"/><!--下载导入模板--></span>
								</a>
						  	</c:when>
							<c:otherwise>
								<a class="buttonActive"  
									href="/pa/excelExport/exportPaBasicItemDataExcelIsNullModule?id=${PARAM_NO}&CPNY_ID=${CPNY_ID}&FIELD1_NAME=${paBasicItemDataInfo.DISTINCT_FIELD_NAME}&FIELD2_NAME=${paBasicItemDataInfo.DISTINCT_FIELD_2ND_NAME }&FIELD1=${paBasicItemDataInfo.DISTINCT_FIELD}&FIELD2=${paBasicItemDataInfo.DISTINCT_FIELD_2ND}"
									><span><spring:message code="pa.insurance.title.downloadImportTemplate"/><!--下载导入模板--></span>
								</a>
							</c:otherwise>
							</c:choose>
					  </c:if>
					   --%>
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
						href="/pa/wagebase/addPaBasicItemDataView?seach_PARAM_NO=${PARAM_NO}"
						target="dialog" mask="true" width="600" height="400" rel="addPaBasicData"><span>
                        <spring:message code="button.add"/><!--添加--></span>
					</a>
				</li>
				
				 <li>
				  <a  class="buttonActive" onclick="delPaBasicItemDataCallback('delPaBasicItemDataForm',DWZ.ajaxDone)" href="#"><span>删除</span></a>					
			      </li>
			      
			      <%--  
				<li>  
				    <a class="buttonActive" href="/pa/salary/deletePaBasicItemDataInfo?PARAM_DATA_NO={PARAM_DATA_NO}"
										target="ajaxTodo" mask="true" width="800" height="600" rel="viewPaData"><span>
										<spring:message code="button.delete"/><!--删除--></span>
					</a>
				</li>
				--%> 
				<input id="seach_PARAM_NO" name="seach_PARAM_NO" type="hidden" size="30"
					value="${PARAM_NO}" />
				<input id="PARAM_NO" name="PARAM_NO" type="hidden" size="30" value="${PARAM_NO}" />
			</ul>
		</div>
		
		<table class="table" width="100%" layoutH="211" >
			<c:if test="${paBasicItemDataInfo.DISTINCT_FIELD eq 'PERSON_ID'}">
				<thead>
					<tr>
						<th width="4%"></th>
						<th width="70">
							<spring:message code="public.title.empId"/><!--工号-->
						</th>
						<th width="50">
							<spring:message code="public.title.name"/><!--姓名-->
						</th>
						<th width="60">
							<spring:message code="public.title.deptName"/><!--部门-->
						</th>
						<th width="50">
							<spring:message code="pa.insurance.title.postGrade"/><!--职级-->
						</th>
                        <th width="70">
							<spring:message code="hr.viewPersonalInfo.title.STATUS_NAME" /><!--员工状态-->
						</th>
                        <%--
						<th width="70">
							<spring:message code="liang.hr.viewPersonalInfo.title.WHETHER_OR_NOT_TO_TRY"/><!-- 试用与否 -->
						</th>
						<th width="50">
							<spring:message code="pa.insurance.title.status"/><!--状态-->
						</th>
						<th width="70">
							<spring:message code="pa.insurance.title.companyLegalPerson"/><!--公司法人-->
						</th>
                        --%>
						<th width="30">
							<spring:message code="public.title.startDate"/><!-- 开始日期 -->
						</th>
						<th width="30">
							<spring:message code="public.title.endDate"/><!-- 结束日期 -->
						</th>
						<th width="20">
							<spring:message code="pa.insurance.title.dataValue"/><!--数值-->
						</th>
						<th width="20">
							<spring:message code="hr.viewPromote.title.REMARK"/><!--备注-->
						</th>
						<th width="60">
							<li>
							<spring:message code="sys.affirm.title.affirmOperation"/>
							<!-- <a class="delete" href="/pa/wagebase/deletePaBasicItemDataBatchInfo?PARAM_NO=${PARAM_NO}"
						       target="ajaxTodo" mask="true" width="800" height="600"><span>删除全部</span> -->
							</li>
						</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${paBasicItemDataList}" var="itemData">
						<tr target="BASIC_DATA_NO" rel="${itemData.BASIC_DATA_NO}">
							<td style="text-align:center;">
								<input type="checkbox" id="c1" name="c1"
									value="${itemData.BASIC_DATA_NO}" />
							</td>
							<td>
								${itemData.EMPID}
							</td>
							<td>
								${itemData.LOCAL_NAME}
							</td>
							<td>
								${itemData.DEPT_NAME}
							</td>
							<td style="text-align:center;">
								${itemData.POST_GRADE_NAME}
							</td>
                            <td style="text-align:center;">
								${itemData.EMP_OFFICE}
							</td>
                            <%--
							<td>
								${itemData.STATUS}
							</td>
							<td>
								${itemData.CPNY_NAME}
							</td>
                            --%>
							<td>
								<input name="START_DATE_${itemData.BASIC_DATA_NO}" type="text" maxlength="200"
										value="${itemData.START_DATE}" class="date" style=" width:80px"/>
							</td>
							<td>
								<input name="END_DATE_${itemData.BASIC_DATA_NO}" type="text" maxlength="200"
										value="${itemData.END_DATE}" class="date" style=" width:80px"/>
							</td>
							<td nowrap="nowrap">
								<c:if test="${empty itemData.RETURN_VALUE}">
								
									<input name="RETURN_VALUE_${itemData.BASIC_DATA_NO}" type="text" maxlength="200" class="textInput"
									 alt='<spring:message code="pa.insurance.title.defaltValueIsZero"/>'
										value="0" size="10" min="-99999999999999" style="text-align:right;width:75px"/>
								</c:if>
								<c:if test="${not empty itemData.RETURN_VALUE}">
									<input name="RETURN_VALUE_${itemData.BASIC_DATA_NO}" type="text" maxlength="200" class="textInput"
											value="${itemData.RETURN_VALUE}" size="10" min="-99999999999999" style="text-align:right;width:75px"/>
								</c:if>
							</td>
							<td>
								<input name="REMARK_${itemData.BASIC_DATA_NO}" type="text" maxlength="200"  size="10"
										value="${itemData.REMARK}" />
							</td>
							<td style="text-align:center;">
								<c:if test="${toolbarInfo.DELETER == '1'}">
									<a class="delete"
										href="/pa/wagebase/deletePaBasicItemDataInfo?BASIC_DATA_NO=${itemData.BASIC_DATA_NO}"
										target="ajaxTodo" mask="true" width="800" height="600"><span>
										<spring:message code="button.delete"/><!--删除--></span>
									</a>
								</c:if>
							</td>

						</tr>
					</c:forEach>
				</tbody>
			</c:if>
			<c:if test="${paBasicItemDataInfo.DISTINCT_FIELD ne 'PERSON_ID'}">
				<c:choose>
					<c:when test="${paBasicItemDataInfo.DISTINCT_FIELD_2ND eq null}">
				<thead>
					<tr>
						<th width="5%"></th>
						<th width="50">
							${paBasicItemDataInfo.DISTINCT_FIELD_NAME}
						</th>
						<th width="50">
							<spring:message code="pa.insurance.title.companyLegalPerson"/><!--公司法人-->
						</th>
						<th width="30">
							<spring:message code="public.title.startDate"/><!-- 开始日期 -->
						</th>
						<th width="30">
							<spring:message code="public.title.endDate"/><!-- 结束日期 -->
						</th>
						<th width="20">
							<spring:message code="pa.insurance.title.dataValue"/><!--数值-->
						</th>
						<th width="20">
							<spring:message code="hr.viewPromote.title.REMARK"/><!--备注-->
						</th>
						<th width="50">
							<li>
							<spring:message code="sys.affirm.title.affirmOperation"/>
							<!--<a class="delete" href="/pa/wagebase/deletePaBasicItemDataBatchInfo?PARAM_NO=${PARAM_NO}&type=1"
						       target="ajaxTodo" mask="true" width="800" height="600"><span>删除全部</span>	-->						
							</li>
						</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${paBasicItemDataList}" var="itemData">
						<tr target="BASIC_DATA_NO" rel="${itemData.BASIC_DATA_NO}">
							<td>
								<input type="checkbox" id="c1" name="c1"
									value="${itemData.BASIC_DATA_NO}" />
							</td>
							<td>
								${itemData.FIELD1_VALUE}
							</td>
							<td>
								${itemData.CPNY_NAME}
							</td>
							<td>
								<input name="START_DATE_${itemData.BASIC_DATA_NO}" type="text" maxlength="200" size="10"
										value="${itemData.START_DATE}" class="date"  style="width:80px"/>
								
										
							</td>
							<td>
								<input name="END_DATE_${itemData.BASIC_DATA_NO}" type="text" maxlength="200"  size="10"
										value="${itemData.END_DATE}" class="date"  style=" width:80px"/>
							</td>
							<td>
								<c:if test="${empty itemData.RETURN_VALUE}">
									<input name="RETURN_VALUE_${itemData.BASIC_DATA_NO}" type="text" maxlength="200" class="textInput"
									alt='<spring:message code="pa.insurance.title.defaltValueIsZero"/>'
										value="0" min="-99999999999999"  style="text-align:right;width:75px"/>
								</c:if>
								<c:if test="${not empty itemData.RETURN_VALUE}">
									<input name="RETURN_VALUE_${itemData.BASIC_DATA_NO}" type="text" maxlength="200" class="textInput"
										value="${itemData.RETURN_VALUE}" min="-99999999999999"  style="text-align:right;width:75px"/>
								</c:if>
							</td>
							<td>
								<input name="REMARK_${itemData.BASIC_DATA_NO}" type="text" maxlength="200"  size="10"
										value="${itemData.REMARK}" />
							</td>
							<td style="text-align:center;">
								<c:if test="${toolbarInfo.DELETER == '1'}">
									<a class="delete"
										href="/pa/wagebase/deletePaBasicItemDataInfo?BASIC_DATA_NO=${itemData.BASIC_DATA_NO}&type=1"
										target="ajaxTodo" mask="true" width="800" height="600">
										<spring:message code="button.delete"/><!--删除--></span>
									</a>
								</c:if>
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
							${paBasicItemDataInfo.DISTINCT_FIELD_NAME}
						</th>
						<th width="50">
							${paBasicItemDataInfo.DISTINCT_FIELD_2ND_NAME}
						</th>
						<th width="50">
							<spring:message code="pa.insurance.title.companyLegalPerson"/><!--公司法人-->
						</th>
						<th width="30">
							<spring:message code="public.title.startDate"/><!-- 开始日期 -->
						</th>
						<th width="30">
							<spring:message code="public.title.endDate"/><!-- 结束日期 -->
						</th>
						<th width="20">
							<spring:message code="pa.insurance.title.dataValue"/><!--数值-->
						</th>
						<th width="20">
							<spring:message code="hr.viewPromote.title.REMARK"/><!--备注-->
						</th>
						<th width="50">
							<li>
							<spring:message code="sys.affirm.title.affirmOperation"/>
							<!--<a class="delete" href="/pa/wagebase/deletePaBasicItemDataBatchInfo?PARAM_NO=${PARAM_NO}&type=1"
						       target="ajaxTodo" mask="true" width="800" height="600"><span>删除全部</span>	  -->						
							</li>
						</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${paBasicItemDataList}" var="itemData">
						<tr target="BASIC_DATA_NO" rel="${itemData.BASIC_DATA_NO}">
							<td>
								<input type="checkbox" id="c1" name="c1"
									value="${itemData.BASIC_DATA_NO}" />
							</td>
							<td>
								${itemData.FIELD1_VALUE}
							</td>
							<td>
								${itemData.FIELD2_VALUE}
							</td>
							<td>
								${itemData.CPNY_NAME}
							</td>
							<td>
								<input name="START_DATE_${itemData.BASIC_DATA_NO}" type="text" maxlength="200" size="10"
										value="${itemData.START_DATE}" class="date" style=" width:80px"/>
							</td>
							<td>
								<input name="END_DATE_${itemData.BASIC_DATA_NO}" type="text" maxlength="200"  size="10"
										value="${itemData.END_DATE}" class="date" style=" width:80px"/>
							</td>
							<td>
								<c:if test="${empty itemData.RETURN_VALUE}">
									<input name="RETURN_VALUE_${itemData.BASIC_DATA_NO}" type="text" maxlength="200" class="textInput" 
									alt='<spring:message code="pa.insurance.title.defaltValueIsZero"/>'
										value="0" min="-99999999999999" style="text-align:right;width:75px"/>
								</c:if>
								<c:if test="${not empty itemData.RETURN_VALUE}">
									<input name="RETURN_VALUE_${itemData.BASIC_DATA_NO}" type="text" maxlength="200" class="textInput" 
										value="${itemData.RETURN_VALUE}" min="-99999999999999" style="text-align:right;width:75px"/>
								</c:if>
							</td>
							<td>
								<input name="REMARK_${itemData.BASIC_DATA_NO}" type="text" maxlength="200"  size="10"
										value="${itemData.REMARK}" />
							</td>
							<td style="text-align:center;">
								<c:if test="${toolbarInfo.DELETER == '1'}">
									<a class="delete"
										href="/pa/wagebase/deletePaBasicItemDataInfo?BASIC_DATA_NO=${itemData.BASIC_DATA_NO}&type=1"
										target="ajaxTodo" mask="true" width="800" height="600"><span>
										<spring:message code="button.delete"/><!--删除--></span>
									</a>
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
	<c:set value="/pa/wagebase/viewPaBasicItemDataList?seach_PARAM_NO=${PARAM_NO}&seach_CPNY_ID=${CPNY_ID}" var="pageUrl" />
	<form id="pagerForm" method="post" action="${pageUrl}">
		<input type="hidden" name="pageNum" value="${pageNum}" />
		<input type="hidden" name="numPerPage" value="${numPerPage}" />
	</form>
	<div class="panelBar">
		<div class="pages">
			<span><spring:message code="public.title.view"/><!-- 显示 --></span>
				<select class="combox" name="numPerPage" onchange="pageFromSea('${totalCount}');navTabPageBreak({numPerPage:this.value},'viewPaBasicItemData')">
					<option value="10"  <c:if test="${numPerPage == 10 }" >selected</c:if> >10</option>
					<option value="20"  <c:if test="${numPerPage == 20 }" >selected</c:if> >20</option>
					<option value="30"  <c:if test="${numPerPage == 30 }" >selected</c:if> >30</option>
					<option value="50"  <c:if test="${numPerPage == 50 }" >selected</c:if> >50</option>
					<option value="80"  <c:if test="${numPerPage == 80 }" >selected</c:if> >80</option>
					<option value="100"  <c:if test="${numPerPage == 100 }" >selected</c:if> >100</option>
					<option value="200"  <c:if test="${numPerPage == 200 }" >selected</c:if> >200</option>
					<option value="300"  <c:if test="${numPerPage == 300 }" >selected</c:if> >300</option>
					<option value="500"  <c:if test="${numPerPage == 500 }" >selected</c:if> >500</option>
					<option value="800"  <c:if test="${numPerPage == 800 }" >selected</c:if> >800</option>
					<option value="1000"  <c:if test="${numPerPage == 1000 }" >selected</c:if> >1000</option>
				</select>
			<span><spring:message code="public.title.tiao"/><!--条-->,<spring:message code="public.title.gong"/><!--共-->
			${totalCount}<spring:message code="public.title.tiao"/><!--条--></span>	
		</div>
		<div class="pagination" rel="viewPaBasicItemData" totalCount="${totalCount}" numPerPage="${numPerPage}" currentPage="${pageNum}"></div>
	</div>
</div>