<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script type="text/javascript">
$(document).ready(function() {
		
		$("#viewPaInputItemDataListbn_excelExport",navTab.getCurrentPanel()).click(function(){
			var empid=encodeURI($("#seach_EMPID",navTab.getCurrentPanel()).val());
			var deptno=$("#seach_DEPTNO",navTab.getCurrentPanel()).val();
			var year=$("#seach_paYear",navTab.getCurrentPanel()).val();
			var month=$("#seach_paMonth",navTab.getCurrentPanel()).val();
			var remark=encodeURI($("#seach_REMARK",navTab.getCurrentPanel()).val());
			var obj = $("#viewPaInputItemDataListbn_excelExport",navTab.getCurrentPanel()) ;
			obj.attr("href",obj.attr("href")+"&empid="
			+empid+"&remark="+remark+"&deptno="+deptno+"&year="+year+"&month="+month);
			//alert(obj.attr("href"));
		});
		
}); 
function validateCallbackPaInputItemData(form,callback) {	
	var $form = $("#paInputItemDataForm",navTab.getCurrentPanel());
	if (!$form.valid()) {
		return false;
	}
	var checked=false;
	$("input[name='c1']",navTab.getCurrentPanel()).each(function(i, obj){

	//$.each($("input[name='c1']",navTab.getCurrentPanel()),function(i, obj) {
		//for(var i=0;i<obj.length;i++){
			if(obj.checked){
				checked=true;
			}
		//}
	});
	//var ids= document.getElementsByName("c1");
	
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
	}
	return false;
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
		var startMonthStr = $("#START_MONTH_"+num,navTab.getCurrentPanel()).val();
		var endMonthStr = obj.value;
		/* if(endMonthStr < startMonthStr){
			alertMsg.error('<spring:message code="alert.message.pa.insurance.endMonthIsBeforeStartDate"/>');
		} */
	}
}

$(document).ready(function(){

	$("input[id^='END_MONTH'][name^='END_MONTH'][inputType='date']").removeClass("required");
});
		 
function submitFormViewPaInput_pa0212(){
  	var $from = $("#paInputForm",navTab.getCurrentPanel());
  	$from.submit();
}

function delPaInputItemDataCallback(form,callback) {
	var $form = $("#paInputItemDataForm",navTab.getCurrentPanel());
	if (!$form.valid()) {
		return false;
	}
	var checked=false;
	$("input[name='c1']",navTab.getCurrentPanel()).each(function(i, obj){

			if(obj.checked){
				checked=true;
			}
	});
	if(!checked){
		alertMsg.error('<spring:message code="alert.message.ess.affirmApply.chooseApplyRecordFirstForBatch"/>'); 
		return false;
	}
    $form.attr("action","/pa/salary/deleteCheckPaInputItemData");
    //确定要批量删除吗
    alertMsg.confirm ("<spring:message code='pa.viewPaInputItemDataList.QUEDINGYAOPILIANGSHANCHUMA.C'/>?",{
        okCall:function(){
	    	$.ajax({
				type: form.method || 'POST',
				url:$form.attr("action"),
				data:$form.serializeArray(),
				dataType:"json",
				cache: false,
				
				success: function(data){ //请求成功后处理函数。
					if(data.statusCode=="200"){
						submitFormViewPaInput_pa0212();
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

function clearPaInputItemDataCallback(form,callback) {
	var $form = $("#paInputItemDataForm",navTab.getCurrentPanel());
	if (!$form.valid()) {
		return false;
	}
    $form.attr("action","/pa/salary/clearPaInputItemDataCallback");
    //确定要清除所有数据吗
    alertMsg.confirm ("<spring:message code='pa.viewPaInputItemDataList.QUEDINGYAOQINGCHUSUOYOUSHUJUMA.C'/>?",{
        okCall:function(){
	    	$.ajax({
				type: form.method || 'POST',
				url:$form.attr("action"),
				data:$form.serializeArray(),
				dataType:"json",
				cache: false,
				
				success: function(data){ //请求成功后处理函数。
					if(data.statusCode=="200"){
						submitFormViewPaInput_pa0212();
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

function deleteInfoOne(url){
	//是否确定要删除
	if (confirm ('<spring:message code="pa.viewPaInputItemDataList.SHIFOUQUEDINGYAOSHANCHU.C"/>?')){
	  	$.ajax({
			type:'POST',
			url:url,
			dataType:"json",
			cache: false,
			success: submitFormViewPaInput_pa0212,
			error: DWZ.ajaxError
		}); 
	}
	return false;
}
</script>
<div class="pageHeader">
<a id="importExcel_pa0212"   target="navTab" mask="true"><span style="display:none;"><!--工资数据导入结果--><spring:message code="pa.viewPaParamDownloud.GONGZISHUJUDAORUJIEGUO.C" /></span></a>
	<form id="paInputForm"onsubmit="return divSearch(this, 'viewPaInputItemData_${itemType }');" action="/pa/salary/viewPaInputItemDataList?seach_PARAM_NO=${PARAM_NO}&seach_CPNY_ID=${CPNY_ID}&itemType=${itemType }&pageNum=1" method="post" rel="pagerForm">
		<div class="searchBar">
			<input type="hidden" value="${paInputItemParamInfo.DISTINCT_FIELD}" id="viewPaInputItemDataList_pa0212" name="viewPaInputItemDataList_pa0212"/>
			
			<table class="searchContent">
			<c:choose>
				<c:when test="${paInputItemParamInfo.DISTINCT_FIELD eq 'PERSON_ID'}">
					<tr onkeyup="if(event.keyCode == 13)$('#paInputForm').submit();">
								<td>
									<spring:message code="public.title.name"/><!--姓名-->/
									<spring:message code="public.title.empId"/><!--工号-->:
									<input id="seach_EMPID" type="text" value="${EMPID}"  name="seach_EMPID" />
								</td>
								<td>
									<!--备注--><spring:message code="ar.viewarcardrecord.title.beizhu" />:
									<input id="seach_REMARK" type="text" value="${REMARK}"  name="seach_REMARK" />
								</td>
								<td>
									<spring:message code="public.title.deptName"/><!--部门-->:
									<ait:deptTree  selected="${DEPTNO}" name="seach_DEPTNO" limit="pa"/>
								</td>
								<td><spring:message code="hrm.empinfo.POST_FAMILY" /><!-- 职群 --></td>
								<td>
								    <select id="seach_WORKER_FLAG" name="seach_WORKER_FLAG">
								        <option value="" <c:if test="${POST_FAMILY eq null || POST_FAMILY eq ''}" >selected</c:if>><!--全部--><spring:message code="org.title.ALL" /></option>
									      <option value="1" <c:if test="${POST_FAMILY eq '1'}" >selected</c:if>><spring:message code="ar.addempshift.title.byperson" /></option>
									      <option value="2" <c:if test="${POST_FAMILY eq '2'}" >selected</c:if>>Worker</option>
								    </select>
								</td>
						    </tr>
						    <tr>
								<td>
									<span>
										<spring:message code="zxc.pa.insurance.title.BASE_MONTH"/>: <!--基准月-->
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<ait:date yearName="seach_paYear" monthName="seach_paMonth" yearSelected="${paYear}" 
													monthSelected="${paMonth}" limit="all"/>
									</span>
								</td>
							</tr>
				</c:when>
				<c:when test="${paInputItemParamInfo.DISTINCT_FIELD eq 'POSITION_NO' }">
					<tr>
						<td>
							<!--职责--><spring:message code="ess.infoApply.title.dutyName" />:
							<ait:SelectSyCodeByCpnyID name="seach_CODE_NO" parentNo="13813"
							cnpyID="${LoginUser.cpnyId}" limit="all" selected="${CODE_NO }"/>
						</td>
						<td>
							<span style="margin-left:3%;">
								<spring:message code="zxc.pa.insurance.title.BASE_MONTH"/>: <!--基准月-->
								<ait:date yearName="seach_paYear" monthName="seach_paMonth" yearSelected="${paYear}" 
											monthSelected="${paMonth}" limit="all"/>
							</span>
						</td>
					</tr>
				</c:when>
				<c:when test="${paInputItemParamInfo.DISTINCT_FIELD eq 'POST_GRADE_NO' }">
					<tr>
						<c:choose>
							<c:when test="${LoginUser.cpnyId == 'TSTO' }">
									<td>
									<!--职级--><spring:message code="ess.trans.title.postGradeName" />:
									<%-- <ait:SelectSyCodeByCpnyID name="seach_CODE_NO" parentNo="400001"
									cnpyID="${LoginUser.cpnyId}"  limit="all" selected="${CODE_NO }"/> --%>
									<ait:SelectSyCodeCombinByCpnyID name="seach_CODE_NO" combinParentNo="14014289,14014290,14014291,14014292,14014293" 
									cnpyID="${LoginUser.cpnyId}"  limit="all" />
								</td>
							</c:when>
							<c:otherwise>
								<td>
										<!--职级--><spring:message code="ess.trans.title.postGradeName" />:
										<%-- <ait:SelectSyCodeByCpnyID name="seach_CODE_NO" parentNo="400001"
										cnpyID="${LoginUser.cpnyId}"  limit="all" selected="${CODE_NO }"/> --%>
										<ait:SelectSyCodeCombinByCpnyID name="seach_CODE_NO" combinParentNo="14015088,14015089,14015090,14015091" 
										cnpyID="${LoginUser.cpnyId}"  limit="all" />
									</td>
							</c:otherwise>
						</c:choose>
						<td>
							<span style="margin-left:3%;">
								<spring:message code="zxc.pa.insurance.title.BASE_MONTH"/>: <!--基准月-->
								<ait:date yearName="seach_paYear" monthName="seach_paMonth" yearSelected="${paYear}" 
											monthSelected="${paMonth}" limit="all"/>
							</span>
						</td>
					</tr>
				</c:when>
				<c:otherwise>
					<tr>
						<td>
							<span style="margin-left:3%;">
								<spring:message code="zxc.pa.insurance.title.BASE_MONTH"/>: <!--基准月-->
								<ait:date yearName="seach_paYear" monthName="seach_paMonth" yearSelected="${paYear}" 
											monthSelected="${paMonth}" limit="all"/>
							</span>
						</td>
					</tr>
				</c:otherwise>
			</c:choose>
			</table>
						<div class="subBar">
							<ul>
								<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="submitFormViewPaInput_pa0212()">
								<spring:message code="public.title.search"/><!--检索--></button></div></div></li>
							</ul>
						</div>
					</div>
			</form>
			</div>
<div class="pageContent">	
   
	<form name="paInputItemDataForm" id="paInputItemDataForm" method="post" action="/pa/salary/updatePaInputItemDataInfo"
		class="pageForm required-validate" onsubmit="return validateCallbackPaInputItemData(this, submitFormViewPaInput_pa0212);">
					<input type="hidden" value="${paInputItemParamInfo.DISTINCT_FIELD}" id="viewPaInputItemDataList_pa0212" name="viewPaInputItemDataList_pa0212"/>
					<input type="hidden" value="${seach_PARAM_NO}" id="seach_PARAM_NO" name="seach_PARAM_NO"/>
		<div style = "padding-top: 5px;">
			<h2 class="thisPageStyle_pa0131"
			style="background-image: url('/resources/css/dwzUI/themes/partner/images/li_prent.jpg'); background-repeat: no-repeat">
			&nbsp;&nbsp;${paInputItemParamInfo.ALIAS_NAME}</h2>
		</div>
		<div class="formBar">
			<label style="float: left">
				<input type="checkbox" class="checkboxCtrl" group="c1" />
				<spring:message code="pa.salary.title.allChecked"/><!--全选-->
				<spring:message code="pa.insurance.title.defaltValue"/><!-- 默认值 -->：
				${paInputItemParamInfo.DEFAULT_VAL}
			</label>
			<ul> 				 
				<li>
			 	  <c:if test="${paInputItemParamInfo.DISTINCT_FIELD eq 'PERSON_ID' or paInputItemParamInfo.DISTINCT_FIELD eq 'POST_GRADE_NO'}">
						<a class="buttonActive" href="/pa/excelImport/importPaBasicItemExcelData?id=${PARAM_NO}
							&CPNY_ID=${CPNY_ID}&DISTINCT_FIELD=${paInputItemParamInfo.DISTINCT_FIELD}&importFunName=/importPaInputItemDataExcelIsNotNull" target="dialog" mask="true" width="500" height="200" >
							<span><spring:message code="ar.addempshift.title.excelimport"/><!-- EXCEL导入 --></span>
						</a>
				  </c:if>
				</li>
				<li>
					<c:if test="${paInputItemParamInfo.DISTINCT_FIELD eq 'PERSON_ID' or paInputItemParamInfo.DISTINCT_FIELD eq 'POST_GRADE_NO'}">
						<a id="viewPaInputItemDataListbn_excelExport" class="buttonActive" href="/pa/excelExport/exportPaInputItemDataExcelIsNotNull?id=${PARAM_NO}&CPNY_ID=${CPNY_ID}&DISTINCT_FIELD=${paInputItemParamInfo.DISTINCT_FIELD}" >
							<span><spring:message code="pa.insurance.title.excelExport"/><!--EXCEL导出--></span>
						</a>
						<input type="hidden" value="1"  name="type"/>
				  	</c:if>
				</li>
				<li>
					<c:if test="${paInputItemParamInfo.DISTINCT_FIELD eq 'PERSON_ID' or paInputItemParamInfo.DISTINCT_FIELD eq 'POST_GRADE_NO'}">
							<a class="buttonActive" href="/pa/excelExport/exportPaInputItemDataExcelIsNotNullModule?id=${PARAM_NO}&CPNY_ID=${CPNY_ID}&DISTINCT_FIELD=${paInputItemParamInfo.DISTINCT_FIELD}"  ><span>
								<spring:message code="pa.insurance.title.downloadImportTemplate"/><!--下载导入模板--></span>
							</a>
					</c:if>
				</li> 
				<li>
					<c:if test="${paInputItemParamInfo.DISTINCT_FIELD eq 'PERSON_ID'}">
						<a class="buttonActive" href="/pa/excelImport/importPaMonthBasicItemExcelData?id=${PARAM_NO}
								&CPNY_ID=${CPNY_ID}&DISTINCT_FIELD=${paInputItemParamInfo.DISTINCT_FIELD}&importFunName=/importPaMonthInputItemDataExcelIsNotNull" target="dialog" mask="true" width="500" height="200" >
							<span><!--导入开始结束月--><spring:message code="pa.viewPaInputItemDataList.DAORUKAISHIJIESHUYUE.C" /></span>
						</a>
					</c:if>
				</li>	
				 <li>
				 	<c:if test="${paInputItemParamInfo.DISTINCT_FIELD eq 'PERSON_ID'}">
						<a class="buttonActive" href="/pa/excelExport/exportPaMonthInputItemDataExcelIsNotNullModule?id=${PARAM_NO}&CPNY_ID=${CPNY_ID}&DISTINCT_FIELD=${paInputItemParamInfo.DISTINCT_FIELD}"  >
							<span><!--下载开始结束月导入模板--><spring:message code="pa.viewPaInputItemDataList.XIAZAIKAISHIJIESHUYUEDAORUMUBAN.C" /></span>
						</a>
					</c:if>
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
				
					<a class="buttonActive" href="/pa/salary/addPaInputItemDataView?seach_PARAM_NO=${PARAM_NO}"
						target="dialog" mask="true" width="800" height="420" rel="viewPaData"><span>
						<spring:message code="button.add"/><!--添加--></span>
					</a>
					
					
				</li>
				
				   <li>
				  <a  class="buttonActive" onclick="delPaInputItemDataCallback('delPaInputItemDataForm',submitFormViewPaInput_pa0212)" href="#"><span><!--删除--><spring:message code="button.delete"/></span></a>					
			      </li>
			      <c:if test="${CPNY_ID eq 'SPC_SH'}">
			      <li>
				  <a  class="buttonActive" onclick="clearPaInputItemDataCallback('delPaInputItemDataForm',submitFormViewPaInput_pa0212)" href="#"><span><!--全部清除--><spring:message code="pa.viewPaInputItemDataList.QUANBUQINGCHU.C"/></span></a>					
			      </li>
			      </c:if>
			      
			      <!-- <li><a class="buttonActive" href="/pa/workManagement/paParemDownloadExcelTemplate?file=down"><span>导入所有科目模板</span></a></li> -->
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
		<%-- 
		<form name="delPaInputItemDataForm" id="delPaInputItemDataForm" method="post" action="/pa/salary/deleteCheckPaInputItemData" 
	  onsubmit="return delPaInputItemDataCallback(this, navTabAjaxDone);"> 
	  --%>
		<table class="table" width="99%" layoutH="201">
			<c:if test="${paInputItemParamInfo.DISTINCT_FIELD eq 'PERSON_ID'}">
				<thead>
					<tr>
						<th></th>
						<th>No</th>
						<th ><!--工号-->
							<spring:message code="public.title.empId"/>
						</th>
						<th ><!--姓名-->
							<spring:message code="public.title.name"/>
						</th>
						<th ><!--部门-->
							<spring:message code="public.title.deptName"/>
						</th>
						<th ><!--职级-->
							<spring:message code="pa.insurance.title.postGrade"/>
						</th>
						<th >
							<spring:message code="hr.viewPersonalInfo.title.STATUS_NAME" /><!--员工状态-->
						</th>
						<%--
						<th ><!--状态-->
							<spring:message code="pa.insurance.title.status"/>
						</th>
						--%>
						<%-- <th ><!--公司法人-->
							<spring:message code="pa.insurance.title.companyLegalPerson"/>
						</th> --%>
						<th ><!--开始月-->
							<spring:message code="pa.insurance.title.startMonth"/>
						</th>
						<th ><!--结束月-->
							<spring:message code="pa.insurance.title.endMonth"/>
						</th>
						<th ><!--数值-->
							<spring:message code="pa.insurance.title.dataValue"/>
						</th>
						<th><!--备注-->
							<spring:message code="hr.viewPromote.title.REMARK"/>
						</th>
						<th >
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
							<%-- <c:if test="${itemData.IS_APPLY ne 'Y'}">
								<input type="checkbox" id="c1" name="c1" value="${itemData.PARAM_DATA_NO}" />
							</c:if> --%>
								<input type="checkbox" id="c1${itemData.PARAM_DATA_NO }" name="c1" value="${itemData.PARAM_DATA_NO}" />
							</td>
							<td>${status.count}</td>
							<td>${itemData.EMPID}</td>
							<td>${itemData.LOCAL_NAME}</td>
							<td>${itemData.DEPT_NAME}</td>
							<td>${itemData.POST_GRADE_NAME}</td>
							<td>${itemData.EMP_OFFICE}</td>
							<%-- <td>${itemData.CPNY_NAME}</td> --%>
							<td>
								<%--<input name="START_MONTH_${itemData.PARAM_DATA_NO}" type="text" maxlength="10" size="10" value="${itemData.START_MONTH}" />--%>
								<ait:inputText name="START_MONTH_${itemData.PARAM_DATA_NO}" inputType="date" id="START_MONTH_${status.index+1}" 
												value="${itemData.START_MONTH}" maxLength="6"  
												onChange="$('#c1${itemData.PARAM_DATA_NO }',navTab.getCurrentPanel()).attr('checked','checked');"
												/>
							</td>
							<td>
								<%--<input name="END_MONTH_${itemData.PARAM_DATA_NO}" type="text" maxlength="10" size="10" value="${itemData.END_MONTH}" />--%>
								<ait:inputText name="END_MONTH_${itemData.PARAM_DATA_NO}" inputType="date" id="END_MONTH_${status.index+1}"  
												value="${itemData.END_MONTH}" maxLength="6" onKeyUp="checkStartMonthAndEndMonth(this,'${status.index+1}')"
												onChange="$('#c1${itemData.PARAM_DATA_NO }',navTab.getCurrentPanel()).attr('checked','checked');"
												/>
							</td>
							<td>
								<c:choose>
									<c:when test="${PARAM_NO == 52 }">
										<c:if test="${empty itemData.RETURN_VALUE}">
											<input name="RETURN_VALUE_${itemData.PARAM_DATA_NO}" type="text" maxlength="50"  style="text-align:right;"
										alt='<spring:message code="pa.insurance.title.defaltValueIsZero"/>' class="textInput" required="required"/>
										</c:if>
										<c:if test="${not empty itemData.RETURN_VALUE}">
											<input name="RETURN_VALUE_${itemData.PARAM_DATA_NO}" type="text"  style="text-align:right;"
											maxlength="50" class="textInput"  value="${itemData.RETURN_VALUE}" required="required"/>
										</c:if>
									</c:when>
									<c:otherwise>
										<c:if test="${empty itemData.RETURN_VALUE}">
											<input name="RETURN_VALUE_${itemData.PARAM_DATA_NO}" type="text" maxlength="200"  style="text-align:left;"
												onChange="$('#c1${itemData.PARAM_DATA_NO }',navTab.getCurrentPanel()).attr('checked','checked');"
										alt='<spring:message code="pa.insurance.title.defaltValueIsZero"/>' class="textInput" min="-99999999999999" value="0" />
										</c:if>
										<c:if test="${not empty itemData.RETURN_VALUE}">
											<input name="RETURN_VALUE_${itemData.PARAM_DATA_NO}" type="text"  style="text-align:left;"
												onChange="$('#c1${itemData.PARAM_DATA_NO }',navTab.getCurrentPanel()).attr('checked','checked');"
											maxlength="50" class="textInput" min="-99999999999999" value="${itemData.RETURN_VALUE}" />
										</c:if>
									</c:otherwise>
								</c:choose>
							</td>
							<td>
								<input name="REMARK_${itemData.PARAM_DATA_NO}" type="text" maxlength="200" style="text-align:left;" 
												onChange="$('#c1${itemData.PARAM_DATA_NO }',navTab.getCurrentPanel()).attr('checked','checked');" value="${itemData.REMARK}" />
							</td>
							<td>
								<c:if test="${toolbarInfo.DELETER == '1'}">
									<a class="delete"  onclick="deleteInfoOne('/pa/salary/deletePaInputItemDataInfo?PARAM_DATA_NO=${itemData.PARAM_DATA_NO}')" href="#"><span>
										<spring:message code="button.delete"/><!--删除--></span>
									</a>
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
								<th >${paInputItemParamInfo.DISTINCT_FIELD_NAME }</th>
								<%-- <th ><!--公司法人-->
									<spring:message code="pa.insurance.title.companyLegalPerson"/>
								</th> --%>
								<th ><!--开始月-->
									<spring:message code="pa.insurance.title.startMonth"/>
								</th>
								<th ><!--结束月-->
									<spring:message code="pa.insurance.title.endMonth"/>
								</th>
								<th ><!--数值-->
									<spring:message code="pa.insurance.title.dataValue"/>
								</th>
								<th width="20"><!--备注-->
									<spring:message code="hr.viewPromote.title.REMARK"/>
								</th>
								<th >
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
										<input type="checkbox" id="c1${itemParam.PARAM_DATA_NO}" name="c1" value="${itemParam.PARAM_DATA_NO}" />
									</td>
									<td>${itemParam.FIELD1_VALUE}</td>
									<%-- <td>${itemParam.CPNY_NAME}</td> --%>
									<td>
										<%--<input name="START_MONTH_${itemParam.PARAM_DATA_NO}" type="text" maxlength="10" size="10" value="${itemParam.START_MONTH}" />--%>
										<ait:inputText name="START_MONTH_${itemParam.PARAM_DATA_NO}" inputType="date" id="START_MONTH_${status.index+1}" style=" width:55px"
												onChange="$('#c1${itemParam.PARAM_DATA_NO }',navTab.getCurrentPanel()).attr('checked','checked');" 
														value="${itemParam.START_MONTH}" maxLength="6"/>
									</td>
									<td>
										<%--<input name="END_MONTH_${itemParam.PARAM_DATA_NO}" type="text" maxlength="10" size="10" value="${itemParam.END_MONTH}" />--%>
										<ait:inputText name="END_MONTH_${itemParam.PARAM_DATA_NO}" inputType="date" id="END_MONTH_${status.index+1}"  style=" width:55px"
												onChange="$('#c1${itemParam.PARAM_DATA_NO }',navTab.getCurrentPanel()).attr('checked','checked');" 
														value="${itemParam.END_MONTH}" maxLength="6" onKeyUp="checkStartMonthAndEndMonth(this,'${status.index+1}')"/>
									</td>
									<td>
										<c:if test="${empty itemParam.RETURN_VALUE}">
											<input name="RETURN_VALUE_${itemParam.PARAM_DATA_NO}" type="text" maxlength="200" class="textInput" min="-99999999999999"
												onChange="$('#c1${itemParam.PARAM_DATA_NO }',navTab.getCurrentPanel()).attr('checked','checked');" 
												alt='<spring:message code="pa.insurance.title.defaltValueIsZero"/>' value="0"  style="text-align:right;width:75px"/>
										</c:if>
										<c:if test="${not empty itemParam.RETURN_VALUE}">
											<input name="RETURN_VALUE_${itemParam.PARAM_DATA_NO}" type="text"  style="text-align:right;width:75px"
												onChange="$('#c1${itemParam.PARAM_DATA_NO }',navTab.getCurrentPanel()).attr('checked','checked');" 
											maxlength="200" class="textInput" min="-99999999999999" value="${itemParam.RETURN_VALUE}" />
										</c:if>
									</td>
									<td>
										<input name="REMARK_${itemParam.PARAM_DATA_NO}" type="text" maxlength="200" size="10" 
												onChange="$('#c1${itemParam.PARAM_DATA_NO }',navTab.getCurrentPanel()).attr('checked','checked');"  value="${itemParam.REMARK}" />
									</td>
									<td>
										<c:if test="${toolbarInfo.DELETER == '1'}">
											<a class="delete"  onclick="deleteInfoOne('/pa/salary/deletePaInputItemDataInfo?PARAM_DATA_NO=${itemParam.PARAM_DATA_NO}&type=1')" href="#"><span>
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
								<th></th>
								<th >${paInputItemParamInfo.DISTINCT_FIELD_NAME }</th>
								<th >${paInputItemParamInfo.DISTINCT_FIELD_2ND_NAME }</th>
								<%-- <th ><!--公司法人-->
									<spring:message code="pa.insurance.title.companyLegalPerson"/>
								</th> --%>
								<th style=" width:55px"><!--开始月-->
									<spring:message code="pa.insurance.title.startMonth"/>
								</th>
								<th ><!--结束月-->
									<spring:message code="pa.insurance.title.endMonth"/>
								</th>
								<th ><!--数值-->
									<spring:message code="pa.insurance.title.dataValue"/>
								</th>
								<th><!--备注-->
									<spring:message code="hr.viewPromote.title.REMARK"/>
								</th>
								<th >
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
									<%-- <td>${itemParam.CPNY_NAME}</td> --%>
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
											<a class="delete"  onclick="deleteInfoOne('/pa/salary/deletePaInputItemDataInfo?PARAM_DATA_NO=${itemParam.PARAM_DATA_NO}&type=1')" href="#"><span>
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
	<c:set value="/pa/salary/viewPaInputItemDataList?seach_PARAM_NO=${PARAM_NO}&seach_CPNY_ID=${CPNY_ID}&itemType=${itemType }" var="pageUrl" />
	<form id="pagerForm" method="post" action="${pageUrl}">
		<input type="hidden" name="pageNum" value="${pageNum}" />
		<input type="hidden" name="numPerPage" value="${numPerPage}" />
	</form>
	<div class="panelBar">
		<div class="pages">
			<span><spring:message code="public.title.view"/><!-- 显示 -->&nbsp;</span>
				<select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value},'viewPaInputItemData_${itemType }')">
					<option value="20"  <c:if test="${numPerPage == 20 }" >selected</c:if> >20</option>
					<option value="50"  <c:if test="${numPerPage == 50 }" >selected</c:if> >50</option>
					<option value="100"  <c:if test="${numPerPage == 100 }" >selected</c:if> >100</option>
					<option value="200"  <c:if test="${numPerPage == 200 }" >selected</c:if> >200</option>
					<option value="500"  <c:if test="${numPerPage == 500 }" >selected</c:if> >500</option>
					<option value="1000"  <c:if test="${numPerPage == 1000 }" >selected</c:if> >1000</option>
				</select>
			<span><spring:message code="public.title.tiao"/><!--条-->,<spring:message code="public.title.gong"/><!--共-->
			${totalCount}&nbsp;<spring:message code="public.title.tiao"/><!--条--></span>	
		</div>
		<div class="pagination" rel="viewPaInputItemData_${itemType }" totalCount="${totalCount}" numPerPage="${numPerPage}" currentPage="${pageNum}"></div>
	</div>
</div>