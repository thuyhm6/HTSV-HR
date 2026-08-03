<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script type="text/javascript">
$(document).ready(function() {
		var type=$("#viewPaInputItemDataList_pa0219").val();
		
		if(type==""){
			var myDate = new Date();
			var year=myDate.getFullYear(); //当前年
			var month=myDate.getMonth(); 
			$("#seach_paYear",navTab.getCurrentPanel()).val(year);  
			var curmonth=parseInt(parseInt(month)+1);
			$("#seach_paMonth",navTab.getCurrentPanel()).val(curmonth);
		}
		var viewPaInputItemDataListHref="";
		$("#viewPaInputItemDataListbn_excelExportFSE").click(function(){
			var empid=$("#seach_EMPID",navTab.getCurrentPanel()).val();
			var deptno=$("#seach_DEPTNO",navTab.getCurrentPanel()).val();
			var year=$("#seach_paYear",navTab.getCurrentPanel()).val();
			var month=$("#seach_paMonth",navTab.getCurrentPanel()).val();
			viewPaInputItemDataListHref=$(this).attr("href");
			$(this).attr("href",$(this).attr("href")+"&empid="+empid+"&deptno="+deptno+"&year="+year+"&month="+month+"");
		});
		$("#viewPaInputItemDataListbn_excelExportFSE").focusout(function() {  
			  //alert($(this).attr("href"));
			  
			  $(this).attr("href",viewPaInputItemDataListHref);
		});  
		
});
function validateCallbackPaInputItemDataFSE(form,callback) {	
	var $form = $("#paInputItemDataFormFSE");
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

function getCurrentMonthData(formName){
	if($("#seach_paYear",navTab.getCurrentPanel()).val()==""||$("#seach_paMonth",navTab.getCurrentPanel()).val()==""){
		return false;
	}else{
		$("#"+formName).submit();
	}
}

function checkStartMonthAndEndMonth(obj,num){

	if(obj.value.length == 6){
		var startMonthStr = $("#START_MONTH_"+num).val();
		var endMonthStr = obj.value;
		if(endMonthStr < startMonthStr){
			alertMsg.error('<spring:message code="alert.message.pa.insurance.endMonthIsBeforeStartDate"/>');
		}
	}
}

$(document).ready(function(){

	$("input[id^='END_MONTH'][name^='END_MONTH'][inputType='date']").removeClass("required");
});
		 
function submitFormViewPaInput_pa0219(){
	$("#viewPaInputItemDataList_pa0219").val("2");
  	var $from = $("#paInputFormFSE");
  	$from.submit();
}
function pageFromSea(a){
	//$("#idName",navTab.getCurrentPanel()).val();
	var seach_EMPID=$("#seach_EMPID",navTab.getCurrentPanel()).val()
	var seach_DEPTNO=$("#seach_DEPTNO",navTab.getCurrentPanel()).val();
	var seach_paYear=$("#seach_paYear",navTab.getCurrentPanel()).val();
	var seach_paMonth=$("#seach_paMonth",navTab.getCurrentPanel()).val();
	var seach_PARAM_NO=$("#seach_PARAM_NO",navTab.getCurrentPanel()).val();
	var viewPaInputItemDataList_pa0219="2";
															
	$("#pagerForm",navTab.getCurrentPanel()).attr("action", "/pa/salary/viewPaInputItemDataListFSE?seach_EMPID="+seach_EMPID+"&seach_DEPTNO="+seach_DEPTNO+"&seach_paYear="+seach_paYear+"&seach_paMonth="+seach_paMonth+"&viewPaInputItemDataList_pa0219=2&seach_PARAM_NO="+seach_PARAM_NO);
}

function delPaInputItemDataFSECallback(form,callback) {
	var $form = $("#paInputItemDataFormFSE");
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
    $form.attr("action","/pa/salary/deleteCheckPaInputItemDataFSE");
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
						navTabSearch("paInputFormFSE");
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
<a id="importExcel_pa0219"  href="#" target="navTab" mask="true"><span style="display:none;">输入项目数据FSE导入结果</span></a>

		<c:if test="${paInputItemParamInfo.DISTINCT_FIELD eq 'PERSON_ID'}">
			<div class="pageHeader">
				<form id="paInputFormFSE" name="paInputFormFSE"  onsubmit="return divSearch(this, 'viewPaInputItemDataFSE');" action="/pa/salary/viewPaInputItemDataListFSE?pageNum=1&seach_PARAM_NO=${PARAM_NO}
					&seach_CPNY_ID=${CPNY_ID}&numPerPage=${numPerPage}" method="post" rel="pagerForm" >
									                         						 
					<input type="hidden" value="${type}" id="viewPaInputItemDataList_pa0219" name="viewPaInputItemDataList_pa0219"/>
					<div class="searchBar">
						<table class="searchContent">
							<tr>
								<td>
									<spring:message code="public.title.name"/><!--姓名-->/
									<spring:message code="public.title.empId"/><!--工号-->:
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
								<td>
									<c:if test="${paInputItemParamInfo.DISTINCT_FIELD eq 'PERSON_ID'}">
										<span style="margin-left:3%;">
											<spring:message code="zxc.pa.insurance.title.BASE_MONTH"/>: <!--基准月-->
											<ait:date yearName="seach_paYear" monthName="seach_paMonth" yearSelected="${paYear}" 
														monthSelected="${paMonth}" limit="all"/>
										</span>
									</c:if>
								</td>
								<td>
									备注:
									<input id="seach_REMARK" type="text" value="${REMARK}"  name="seach_REMARK"/>
								</td>
							</tr>
						</table>
						<div class="subBar">
							<ul>
								<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="submitFormViewPaInput_pa0219()">
								<spring:message code="public.title.search"/><!--检索--></button></div></div></li>
							</ul>
						</div>
					</div>
				</form>
			</div>
		</c:if>
<div class="pageContent">

	<c:if test="${paInputItemParamInfo.DISTINCT_FIELD ne 'PERSON_ID'}">
		<form id="paInputFormFSE"  name="paInputFormFSE" onsubmit="return divSearch(this, 'viewPaInputItemDataFSE');" action="/pa/salary/viewPaInputItemDataListFSE?pageNum=1&seach_PARAM_NO=${PARAM_NO}
			&seach_CPNY_ID=${CPNY_ID}&numPerPage=${numPerPage}" method="post" rel="pagerForm" >
			<div class="formBar">
				<span>
					<spring:message code="zxc.pa.insurance.title.BASE_MONTH"/>: <!--基准月-->
					<ait:date yearName="seach_paYear" monthName="seach_paMonth" yearSelected="${paYear}" monthSelected="${paMonth}"
								 limit="all"/>
								
				</span>
				<ul>
								<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="submitFormViewPaInput_pa0219()">
								<spring:message code="public.title.search"/><!--检索--></button></div></div></li>
							</ul>
				
			</div>
			
							
						
		</form>
	</c:if>
	
	<form name="paInputItemDataFormFSE" id="paInputItemDataFormFSE" method="post" action="/pa/salary/updatePaInputItemDataInfoFSE"
		class="pageForm required-validate" onsubmit="return validateCallbackPaInputItemDataFSE(this, navTabAjaxDone);">
		<div class="formBar">
			<label style="float: left">
				<input type="checkbox" class="checkboxCtrl" group="c1" />
				<spring:message code="pa.salary.title.allChecked"/><!--全选-->
				<spring:message code="pa.insurance.title.defaltValue"/><!-- 默认值 -->：
				${paInputItemParamInfo.DEFAULT_VAL}
			</label>
			<ul> 				 
				  <li>
			 	  <c:if test="${paInputItemParamInfo.DISTINCT_FIELD eq 'PERSON_ID'}">
						<a class="buttonActive" href="/pa/excelImport/importPaBasicItemExcelData?id=${PARAM_NO}
							&CPNY_ID=${CPNY_ID}&importFunName=/importPaInputItemDataExcelIsNotNullFSE" target="dialog" mask="true" width="500" height="200" >
							<span><spring:message code="ar.addempshift.title.excelimport"/><!-- EXCEL导入 --></span>
						</a>
				  </c:if>
				  <%--
				  <c:if test="${paInputItemParamInfo.DISTINCT_FIELD ne 'PERSON_ID'}">
					  	<c:choose>
					  	<c:when test="${paInputItemParamInfo.DISTINCT_FIELD_2ND eq null}">
						  	<a class="buttonActive" href="/pa/excelImport/importInsuranceInputItemData?id=${PARAM_NO}
						  		&CPNY_ID=${CPNY_ID}&importFunName=importPaInputItemDataExcelIsNull&FIELD1_NAME=${paInputItemParamInfo.DISTINCT_FIELD}" 
						  		target="dialog" mask="true" width="500" height="200" >
								<span><spring:message code="ar.addempshift.title.excelimport"/><!-- EXCEL导入 --></span>
							</a>
					  	</c:when>
						<c:otherwise>
							<a class="buttonActive" href="/pa/excelImport/importInsuranceInputItemData?id=${PARAM_NO}
								&CPNY_ID=${CPNY_ID}&importFunName=importPaInputItemDataExcelIsNull2&FIELD1_NAME=${paInputItemParamInfo.DISTINCT_FIELD}
								&FIELD2_NAME=${paInputItemParamInfo.DISTINCT_FIELD_2ND}" target="dialog" mask="true" width="500" height="200" >
								<span><spring:message code="ar.addempshift.title.excelimport"/><!-- EXCEL导入 --></span>
							</a>
						</c:otherwise>
						</c:choose>
				  </c:if>
				   --%>
				</li>
				<li>
					<c:if test="${paInputItemParamInfo.DISTINCT_FIELD eq 'PERSON_ID'}">
						<a id="viewPaInputItemDataListbn_excelExportFSE" class="buttonActive" href="/pa/excelExport/exportPaInputItemDataExcelIsNotNull?id=${PARAM_NO}&CPNY_ID=${CPNY_ID}" >
							<span><spring:message code="pa.insurance.title.excelExport"/><!--EXCEL导出--></span>
						</a>
				  	</c:if>
				  	<%--
				  	<c:if test="${paInputItemParamInfo.DISTINCT_FIELD ne 'PERSON_ID'}">
					  	<c:choose>
					  	<c:when test="${paInputItemParamInfo.DISTINCT_FIELD_2ND eq null}">
						  	<a class="buttonActive" href="/pa/excelExport/exportPaInputItemDataExcelIsNull?seach_PARAM_NO=${PARAM_NO}&seach_CPNY_ID=${CPNY_ID}">
								<span><spring:message code="pa.insurance.title.excelExport"/><!--EXCEL导出--></span>
							</a>
					  	</c:when>
						<c:otherwise>
							<a class="buttonActive" href="/pa/excelExport/exportPaInputItemDataExcelIsNull?seach_PARAM_NO=${PARAM_NO}&seach_CPNY_ID=${CPNY_ID}">
								<span><spring:message code="pa.insurance.title.excelExport"/><!--EXCEL导出--></span>
							</a>
						</c:otherwise>
						</c:choose>
				  	</c:if>	
				  	 --%>
				</li>
				<li>
					<c:if test="${paInputItemParamInfo.DISTINCT_FIELD eq 'PERSON_ID'}">
							<a class="buttonActive" href="/pa/excelExport/exportPaInputItemDataExcelIsNotNullModule?id=${PARAM_NO}&CPNY_ID=${CPNY_ID}"  ><span>
								<spring:message code="pa.insurance.title.downloadImportTemplate"/><!--下载导入模板--></span>
							</a>
					  </c:if>
					  <%--
					  <c:if test="${paInputItemParamInfo.DISTINCT_FIELD ne 'PERSON_ID'}">
						  	<c:choose>
						  	<c:when test="${paInputItemParamInfo.DISTINCT_FIELD_2ND eq null}">
							  	<a class="buttonActive" href="/pa/excelExport/exportPaInputItemDataExcelIsNullModule?id=${PARAM_NO}
							  		&CPNY_ID=${CPNY_ID}&FIELD1_NAME=${paInputItemParamInfo.DISTINCT_FIELD_NAME}&FIELD1=${paInputItemParamInfo.DISTINCT_FIELD}"
									><span><spring:message code="pa.insurance.title.downloadImportTemplate"/><!--下载导入模板--></span>
								</a>
						  	</c:when>
							<c:otherwise>
								<a class="buttonActive" href="/pa/excelExport/exportPaInputItemDataExcelIsNullModule?id=${PARAM_NO}&CPNY_ID=${CPNY_ID}
									&FIELD1_NAME=${paInputItemParamInfo.DISTINCT_FIELD_NAME}&FIELD2_NAME=${paInputItemParamInfo.DISTINCT_FIELD_2ND_NAME }
									&FIELD1=${paInputItemParamInfo.DISTINCT_FIELD}&FIELD2=${paInputItemParamInfo.DISTINCT_FIELD_2ND}"
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
							<button type="button" class="checkboxCtrl" group="c1" selectType="invert">
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
					<a class="buttonActive" href="/pa/salary/addPaInputItemDataViewFSE?seach_PARAM_NO=${PARAM_NO}"
						target="dialog" mask="true" width="600" height="420" rel="viewPaData"><span>
						<spring:message code="button.add"/><!--添加--></span>
					</a>
				</li>
				 <li>
				  <a  class="buttonActive" onclick="delPaInputItemDataFSECallback('delPaInputItemDataFSEForm',DWZ.ajaxDone)" href="#"><span>删除</span></a>					
			      </li>
				<%--
				<li>
					<a class="buttonActive" href="/pa/salary/viewPaInputItemDataPersonList?pageNum=1"
						target="navTab"><span><spring:message code="pa.insurance.title.personalEntry"/><!--个人别录入--></span> 
					</a>
				</li>
				--%>
				<input id="seach_PARAM_NO" name="seach_PARAM_NO" type="hidden" size="30" value="${PARAM_NO}" />
				<input id="PARAM_NO" name="PARAM_NO" type="hidden" size="30" value="${PARAM_NO}" />
			</ul>
		</div>
		<table class="table" width="99%" layoutH="211">
			<c:if test="${paInputItemParamInfo.DISTINCT_FIELD eq 'PERSON_ID'}">
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
						<%--
						<th width="50"><!--状态-->
							<spring:message code="pa.insurance.title.status"/>
						</th>
						--%>
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
							<spring:message code="sys.affirm.title.affirmOperation"/>
								<!-- <a class="delete"
									href="/pa/salary/deletePaInputItemDataBatchInfo?PARAM_NO=${PARAM_NO}"
									target="ajaxTodo" mask="true" width="800" height="600"><span>删除全部</span> -->
						</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${paItemDataList}" var="itemData" varStatus="status">
						<tr target="PARAM_DATA_NO" rel="${itemData.PARAM_DATA_NO}">
							<td>
							<c:if test="${itemData.IS_APPLY ne 'Y'}">
								<input type="checkbox" id="c1" name="c1" value="${itemData.PARAM_DATA_NO}" />
							</c:if>
							</td>
							<td>${itemData.EMPID}</td>
							<td>${itemData.LOCAL_NAME}</td>
							<td>${itemData.DEPT_NAME}</td>
							<td>${itemData.POST_GRADE_NAME}</td>
							<td>${itemData.EMP_OFFICE}</td>
							<td>${itemData.CPNY_NAME}</td>
							<td>
								<%--<input name="START_MONTH_${itemData.PARAM_DATA_NO}" type="text" maxlength="10" size="10" value="${itemData.START_MONTH}" />--%>
								<ait:inputText name="START_MONTH_${itemData.PARAM_DATA_NO}" inputType="date" id="START_MONTH_${status.index+1}" 
												value="${itemData.START_MONTH}" maxLength="6"  style=" width:55px"/>
							</td>
							<td>
								<%--<input name="END_MONTH_${itemData.PARAM_DATA_NO}" type="text" maxlength="10" size="10" value="${itemData.END_MONTH}" />--%>
								<ait:inputText name="END_MONTH_${itemData.PARAM_DATA_NO}" inputType="date" id="END_MONTH_${status.index+1}"  style=" width:55px"
												value="${itemData.END_MONTH}" maxLength="6" onKeyUp="checkStartMonthAndEndMonth(this,'${status.index+1}')"/>
							</td>
							<td>
								<c:if test="${empty itemData.RETURN_VALUE}">
									<input name="RETURN_VALUE_${itemData.PARAM_DATA_NO}" type="text" maxlength="200"  style="text-align:right;width:75px"
										alt='<spring:message code="pa.insurance.title.defaltValueIsZero"/>' class="textInput" min="-99999999999999" value="0" />
								</c:if>
								<c:if test="${not empty itemData.RETURN_VALUE}">
									<input name="RETURN_VALUE_${itemData.PARAM_DATA_NO}" type="text"  style="text-align:right;width:75px"
									maxlength="50" class="textInput" min="-99999999999999" value="${itemData.RETURN_VALUE}" />
								</c:if>
							</td>
							<td>
								<input name="REMARK_${itemData.PARAM_DATA_NO}" type="text" maxlength="200"  size="10" value="${itemData.REMARK}" />
							</td>
							<td>
								<c:if test="${toolbarInfo.DELETER == '1'}">
									<c:if test="${itemData.IS_APPLY ne 'Y'}">
										<a class="delete" href="/pa/salary/deletePaInputItemDataInfoFSE?PARAM_DATA_NO=${itemData.PARAM_DATA_NO}"
										target="ajaxTodo" mask="true" width="800" height="600"><span>
										<spring:message code="button.delete"/><!--删除--></span>
									</a>
									</c:if>
								</c:if>
							</td>

						</tr>
					</c:forEach>
				</tbody>
			</c:if>
			<c:if test="${paInputItemParamInfo.DISTINCT_FIELD ne 'PERSON_ID'}">
				<c:choose>
					<c:when
						test="${paInputItemParamInfo.DISTINCT_FIELD_2ND eq null}">
						<thead>
							<tr>
								<th width="5%"></th>
								<th width="50">${paInputItemParamInfo.DISTINCT_FIELD_NAME }</th>
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
									<spring:message code="sys.affirm.title.affirmOperation"/>
										<!--<a class="delete"
											href="/pa/salary/deletePaInputItemDataBatchInfo?PARAM_NO=${PARAM_NO}&type=1"
											target="ajaxTodo" mask="true" width="800" height="600"><span>删除全部</span>-->
								</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${paItemDataList}" var="itemParam" varStatus="status">
								<tr target="PARAM_DATA_NO" rel="${itemParam.PARAM_DATA_NO}">
									<td>
										<input type="checkbox" id="c1" name="c1" value="${itemParam.PARAM_DATA_NO}" />
									</td>
									<td>${itemParam.FIELD1_VALUE}</td>
									<td>${itemParam.CPNY_NAME}</td>
									<td>
										<%--<input name="START_MONTH_${itemParam.PARAM_DATA_NO}" type="text" maxlength="10" size="10" value="${itemParam.START_MONTH}" />--%>
										<ait:inputText name="START_MONTH_${itemParam.PARAM_DATA_NO}" inputType="date" id="START_MONTH_${status.index+1}" style=" width:55px"
														value="${itemParam.START_MONTH}" maxLength="6"/>
									</td>
									<td>
										<%--<input name="END_MONTH_${itemParam.PARAM_DATA_NO}" type="text" maxlength="10" size="10" value="${itemParam.END_MONTH}" />--%>
										<ait:inputText name="END_MONTH_${itemParam.PARAM_DATA_NO}" inputType="date" id="END_MONTH_${status.index+1}"  style=" width:55px"
														value="${itemParam.END_MONTH}" maxLength="6" onKeyUp="checkStartMonthAndEndMonth(this,'${status.index+1}')"/>
									</td>
									<td>
										<c:if test="${empty itemParam.RETURN_VALUE}">
											<input name="RETURN_VALUE_${itemParam.PARAM_DATA_NO}" type="text" maxlength="200" class="textInput" min="-99999999999999"
												alt='<spring:message code="pa.insurance.title.defaltValueIsZero"/>' value="0"  style="text-align:right;width:75px"/>
										</c:if>
										<c:if test="${not empty itemParam.RETURN_VALUE}">
											<input name="RETURN_VALUE_${itemParam.PARAM_DATA_NO}" type="text"  style="text-align:right;width:75px"
											maxlength="200" class="textInput" min="-99999999999999" value="${itemParam.RETURN_VALUE}" />
										</c:if>
									</td>
									<td>
										<input name="REMARK_${itemParam.PARAM_DATA_NO}" type="text" maxlength="200" size="10" value="${itemParam.REMARK}" />
									</td>
									<td>
										<c:if test="${toolbarInfo.DELETER == '1'}">
											<a class="delete" href="/pa/salary/deletePaInputItemDataInfoFSE?PARAM_DATA_NO=${itemParam.PARAM_DATA_NO}&type=1"
												target="ajaxTodo" mask="true" width="800" height="600"><span>
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
								<th width="50">${paInputItemParamInfo.DISTINCT_FIELD_NAME }</th>
								<th width="50">${paInputItemParamInfo.DISTINCT_FIELD_2ND_NAME }</th>
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
									<spring:message code="sys.affirm.title.affirmOperation"/>
										<!--<a class="delete"
											href="/pa/salary/deletePaInputItemDataBatchInfo?PARAM_NO=${PARAM_NO}&type=1"
											target="ajaxTodo" mask="true" width="800" height="600"><span>删除全部</span>-->
								</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${paItemDataList}" var="itemParam" varStatus="status">
								<tr target="PARAM_DATA_NO" rel="${itemParam.PARAM_DATA_NO}">
									<td>
										<input type="checkbox" id="c1" name="c1" value="${itemParam.PARAM_DATA_NO}" />
									</td>
									<td>${itemParam.FIELD1_VALUE}</td>
									<td>${itemParam.FIELD2_VALUE}</td>
									<td>${itemParam.CPNY_NAME}</td>
									<td>
										<%--<input name="START_MONTH_${itemParam.PARAM_DATA_NO}" type="text" maxlength="10" size="10" value="${itemParam.START_MONTH}" />--%>
										<ait:inputText name="START_MONTH_${itemParam.PARAM_DATA_NO}" inputType="date" id="START_MONTH_${status.index+1}"
														value="${itemParam.START_MONTH}" maxLength="6" style=" width:55px"/>
									</td>
									<td>
										<%--<input name="END_MONTH_${itemParam.PARAM_DATA_NO}" type="text" maxlength="10" size="10" value="${itemParam.END_MONTH}" />--%>
										<ait:inputText name="END_MONTH_${itemParam.PARAM_DATA_NO}" inputType="date" id="END_MONTH_${status.index+1}" style=" width:55px"
														value="${itemParam.END_MONTH}" maxLength="6" onKeyUp="checkStartMonthAndEndMonth(this,'${status.index+1}')"/>
									</td>
									<td>
										<c:if test="${empty itemParam.RETURN_VALUE}">
											<input name="RETURN_VALUE_${itemParam.PARAM_DATA_NO}"  style="text-align:right;width:75px"
												type="text" maxlength="200" alt='<spring:message code="pa.insurance.title.defaltValueIsZero"/>' class="textInput" min="-99999999999999" value="0" />
										</c:if>
										<c:if test="${not empty itemParam.RETURN_VALUE}">
											<input name="RETURN_VALUE_${itemParam.PARAM_DATA_NO}" type="text"  style="text-align:right;width:75px"
											maxlength="200" class="textInput" min="-99999999999999" value="${itemParam.RETURN_VALUE}" />
										</c:if>
									</td>
									<td>
										<input name="REMARK_${itemData.PARAM_DATA_NO}" type="text" maxlength="200" size="10" value="${itemParam.REMARK}" />
									</td>
									<td>
										<c:if test="${toolbarInfo.DELETER == '1'}">
											<a class="delete" href="/pa/salary/deletePaInputItemDataInfoFSE?PARAM_DATA_NO=${itemParam.PARAM_DATA_NO}&type=1"
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
	<c:set value="/pa/salary/viewPaInputItemDataListFSE?seach_PARAM_NO=${PARAM_NO}&seach_CPNY_ID=${CPNY_ID}" var="pageUrl" />
	<form id="pagerForm" method="post" action="${pageUrl}">
		<input type="hidden" name="pageNum" value="${pageNum}" />
		<input type="hidden" name="numPerPage" value="${numPerPage}" />
	</form>
	<div class="panelBar">
		<div class="pages">
			<span><spring:message code="public.title.view"/><!-- 显示 --></span>
				<select class="combox" name="numPerPage" onchange="pageFromSea('${totalCount}');navTabPageBreak({numPerPage:this.value},'viewPaInputItemDataFSE')">
					<option value="10"  <c:if test="${numPerPage == 10 }" >selected</c:if> >10</option>
					<option value="20"  <c:if test="${numPerPage == 20 }" >selected</c:if> >20</option>
					<option value="30"  <c:if test="${numPerPage == 30 }" >selected</c:if> >30</option>
					<option value="1000"  <c:if test="${numPerPage == 1000 }" >selected</c:if> >1000</option>
				</select>
			<span><spring:message code="public.title.tiao"/><!--条-->,<spring:message code="public.title.gong"/><!--共-->
			${totalCount}<spring:message code="public.title.tiao"/><!--条--></span>	
		</div>
		<div class="pagination" rel="viewPaInputItemDataFSE" totalCount="${totalCount}" numPerPage="${numPerPage}" currentPage="${pageNum}"></div>
	</div>
</div>