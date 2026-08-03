<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script type="text/javascript">
function validateCallbackUpdateIn(form,callback) {	
	var $form = $("#viewInsuranceInputItemDataApplyList");
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
//申请
function insuranceApplyCallback(form,callback,flag) {
	var $form=null;
	if($('#'+form).length>0)
		$form=$('#'+form);
	else
 		$form = $(form);
	
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
		alertMsg.error("请选择需要申请的信息在进行申请");
		return false;
	}

    $form.attr("action","/pa/insurance/insuranceApply");
    if (confirm ('<spring:message code="alert.message.pa.insurance.confirmSubmit"/>')){
		$.ajax({
			type: form.method || 'POST',
			url:$form.attr("action"),
			data:$form.serializeArray(),
			dataType:"json",
			cache: false,
			success: function(data){ //请求成功后处理函数。
				if(data.statusCode=="200"){
					$("#viewInsuranceInputItemDataApplyTow").attr('href','/pa/insurance/viewInsuranceInputItemDataApplyList?pageNum=1&seach_PARAM_NO='+data.PARAM_NO+'&seach_CPNY_ID='+data.CPNY_ID);
					$("#viewInsuranceInputItemDataApplyTow").click();
					alertMsg.correct(data.message);
				}else{
					if(data.result=="2"){
						$("#viewInsuranceInputItemDataApplyTow").attr('href','/pa/insurance/viewInsuranceInputItemDataApplyList?pageNum=1&seach_PARAM_NO='+data.PARAM_NO+'&seach_CPNY_ID='+data.CPNY_ID);
						$("#viewInsuranceInputItemDataApplyTow").click();
						alertMsg.info(data.message);
					}else{
						$("#viewInsuranceInputItemDataApplyTow").attr('href','/pa/insurance/viewInsuranceInputItemDataApplyList?pageNum=1&seach_PARAM_NO='+data.PARAM_NO+'&seach_CPNY_ID='+data.CPNY_ID);
						$("#viewInsuranceInputItemDataApplyTow").click();
						alertMsg.error(data.message);
					}
				}   
	   	 	}  ,
			error: DWZ.ajaxError
		});
		return false;
    }
}

</script>
<div id="viewInsuranceInputItemDataViewApplyMark">
<c:if test="${insuranceInputItemParamInfo.DISTINCT_FIELD eq 'PERSON_ID'}">
			<div class="pageHeader" style="border:1px #B8D0D6 solid" >
				<form   onsubmit="return divSearch(this, 'viewInsuranceInputItemDataApply');" action="/pa/insurance/viewInsuranceInputItemDataApplyList?pageNum=1&seach_PARAM_NO=${PARAM_NO}&seach_CPNY_ID=${CPNY_ID}" method="post" rel="pagerForm" >
					<div class="searchBar">
						<table class="searchContent">
							<tr>
								<td>
									<spring:message code="public.title.name"/><!--姓名-->/
									<spring:message code="public.title.empId"/><!--工号-->:
									<input type="text" value="${EMPID}"  name="seach_EMPID"/>
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
							</tr>
						</table>
						<div class="subBar">
							<ul>
								<li><div class="buttonActive"><div class="buttonContent"><button type="submit">
								<spring:message code="public.title.search"/><!--检索--></button></div></div></li>
							</ul>
						</div>
					</div>
				</form>
			</div>
</c:if>
<div class="pageContent" style="border-left:1px #B8D0D6 solid;border-right:1px #B8D0D6 solid">
	<form id="viewInsuranceInputItemDataApplyList" method="post"
		action="/pa/insurance/updateInsuranceInputItemDataInfo"
		class="pageForm required-validate"
		onsubmit="return validateCallbackUpdateIn(this, navTabAjaxDone);">
		<div class="formBar">
			<label style="float: left">
				<input type="checkbox" class="checkboxCtrl" group="c1" />
				<spring:message code="pa.salary.title.allChecked"/><!--全选-->
			</label>
			<ul>
			   <li>
			 	  <c:if test="${insuranceInputItemParamInfo.DISTINCT_FIELD eq 'PERSON_ID'}">
						<a class="buttonActive" href="/pa/excelImport/importInsuranceInputItemData?id=${PARAM_NO}&CPNY_ID=${CPNY_ID}
							&importFunName=importInsuranceInputApplyDataExcelIsNotNull" target="dialog" mask="true" width="500" height="200" >
							<span><spring:message code="ar.addempshift.title.excelimport"/><!-- EXCEL导入 --></span>
						</a>
				  </c:if> 
				  <c:if test="${insuranceInputItemParamInfo.DISTINCT_FIELD ne 'PERSON_ID'}">
					  	<c:choose>
					  	<c:when test="${insuranceInputItemParamInfo.DISTINCT_FIELD_2ND eq null}">
						  	<a class="buttonActive" href="/pa/excelImport/importInsuranceInputItemData?id=${PARAM_NO}&CPNY_ID=${CPNY_ID}
								&importFunName=importInsuranceInputApplyDataExcelIsNull
								&FIELD1_NAME=${insuranceInputItemParamInfo.DISTINCT_FIELD }" target="dialog" mask="true" width="500" height="200" >
								<span><spring:message code="ar.addempshift.title.excelimport"/><!-- EXCEL导入 --></span>
							</a>
					  	</c:when>
						<c:otherwise>
							<a class="buttonActive"
								href="/pa/excelImport/importInsuranceInputItemData?id=${PARAM_NO}&CPNY_ID=${CPNY_ID}
								&importFunName=importInsuranceInputApplyDataExcelIsNull2
								&FIELD1_NAME=${insuranceInputItemParamInfo.DISTINCT_FIELD }&
								&FIELD2_NAME=${insuranceInputItemParamInfo.DISTINCT_FIELD_2ND }" target="dialog" mask="true" width="500" height="200" >
								<span><spring:message code="ar.addempshift.title.excelimport"/><!-- EXCEL导入 --></span>
							</a>
						</c:otherwise>
						</c:choose>
				  </c:if>
				</li>
				<li>
				 <c:if test="${insuranceInputItemParamInfo.DISTINCT_FIELD eq 'PERSON_ID'}">
						<a class="buttonActive"
							href="/pa/excelExport/exportInsuranceInputApplyDataExcelIsNotNull?id=${PARAM_NO}&CPNY_ID=${CPNY_ID}" >
							<span><spring:message code="pa.insurance.title.excelExport"/><!--EXCEL导出  --></span>
						</a>
				  </c:if>
				  <c:if test="${insuranceInputItemParamInfo.DISTINCT_FIELD ne 'PERSON_ID'}">
					  	<c:choose>
					  	<c:when test="${insuranceInputItemParamInfo.DISTINCT_FIELD_2ND eq null}">
						  	<a class="buttonActive"
								href="/pa/excelExport/exportInsuranceInputApplyDataExcelIsNull?seach_PARAM_NO=${PARAM_NO}&seach_CPNY_ID=${CPNY_ID}">
								<span><spring:message code="pa.insurance.title.excelExport"/><!--EXCEL导出  --></span>
							</a>
					  	</c:when>
						<c:otherwise>
							<a class="buttonActive"
								href="/pa/excelExport/exportInsuranceInputApplyDataExcelIsNull?seach_PARAM_NO=${PARAM_NO}&seach_CPNY_ID=${CPNY_ID}">
								<span><spring:message code="pa.insurance.title.excelExport"/><!--EXCEL导出  --></span>
							</a>
						</c:otherwise>
						</c:choose>
				  </c:if>	
				</li>
				<li>
					<c:if test="${insuranceInputItemParamInfo.DISTINCT_FIELD eq 'PERSON_ID'}">
							<a class="buttonActive"
								href="/pa/excelExport/exportInsuranceInputApplyDataExcelIsNotNullModule?id=${PARAM_NO}&CPNY_ID=${CPNY_ID}"  ><span>
								<spring:message code="pa.insurance.title.downloadImportTemplate"/><!--下载导入模板--></span>
							</a>
					  </c:if>
					  <c:if test="${insuranceInputItemParamInfo.DISTINCT_FIELD ne 'PERSON_ID'}">
						  	<c:choose>
						  	<c:when test="${insuranceInputItemParamInfo.DISTINCT_FIELD_2ND eq null}">
							  	<a class="buttonActive"
									href="/pa/excelExport/exportInsuranceInputApplyDataExcelIsNullModule?id=${PARAM_NO}&CPNY_ID=${CPNY_ID}&FIELD1_NAME=${insuranceInputItemParamInfo.DISTINCT_FIELD_NAME}&FIELD1=${insuranceInputItemParamInfo.DISTINCT_FIELD}"
									><span><spring:message code="pa.insurance.title.downloadImportTemplate"/><!--下载导入模板--></span>
								</a>
						  	</c:when>
							<c:otherwise>
								<a class="buttonActive"
									href="/pa/excelExport/exportInsuranceInputApplyDataExcelIsNullModule?id=${PARAM_NO}&CPNY_ID=${CPNY_ID}
									&FIELD1_NAME=${insuranceInputItemParamInfo.DISTINCT_FIELD_NAME}
									&FIELD2_NAME=${insuranceInputItemParamInfo.DISTINCT_FIELD_2ND_NAME }
									&FIELD1=${insuranceInputItemParamInfo.DISTINCT_FIELD}
									&FIELD2=${insuranceInputItemParamInfo.DISTINCT_FIELD_2ND }"
									><span><spring:message code="pa.insurance.title.downloadImportTemplate"/><!--下载导入模板--></span>
								</a>
							</c:otherwise>
							</c:choose>
					  </c:if>
				</li>
				<%-- 
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
				--%>
				<li>
					<a class="buttonActive" href="/pa/insurance/addInsuranceInputItemDataApplyView?seach_PARAM_NO=${PARAM_NO}"
						target="dialog" mask="true" width="600" height="450" rel="insuranceInputItemData"><span>
						<spring:message code="button.add"/><!--添加--></span>
					</a>
				</li>
				<li>
					<div class="subBar">
			            <div class="buttonActive">
						    <a class="update" onclick="insuranceApplyCallback('viewInsuranceInputItemDataApplyList',DWZ.ajaxDone,'${PARAM_NO}')" href="#" ><span>
						  <spring:message code="ar.viewitemparameter.title.shenqing"/><!--申请--></span></a>
                        </div>
                     </div>
				</li>
				<%--
				<li>
					<a class="buttonActive" href="/pa/insurance/viewInsuranceInputItemDataPersonList?pageNum=1" target="navTab">
					<span><spring:message code="pa.insurance.title.personalEntry"/><!--个人别录入--></span> </a>
				</li>
				--%>
				<input id="seach_PARAM_NO" name="seach_PARAM_NO" type="hidden" size="30" value="${PARAM_NO}" />
				<input id="seach_CPNY_ID" name="seach_CPNY_ID" type="hidden" size="30" value="${CPNY_ID}" />
				<input id="PARAM_NO" name="PARAM_NO" type="hidden" size="30" value="${PARAM_NO}" />
			</ul>
		</div>
		<table class="table" width="99%" layoutH="195">
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
						<th width="50"><!--状态-->
							<spring:message code="pa.insurance.title.status"/>
						</th>
						<th width="50"><!--公司法人-->
							<spring:message code="pa.insurance.title.companyLegalPerson"/>
						</th>
						<th width="50"><!--开始月-->
							<spring:message code="pa.insurance.title.startMonth"/>
						</th>
<!-- 						<th width="50">结束月 -->
<!-- 							<spring:message code="pa.insurance.title.endMonth"/> -->
<!-- 						</th> -->
						<th width="50"><!--数值-->
							<spring:message code="pa.insurance.title.dataValue"/>
						</th>
						<th width="20"><!--备注-->
							<spring:message code="hr.viewPromote.title.REMARK"/>
						</th>
						<th width="100"><!--网址-->
							<spring:message code="pa.ins.alert.message.exportdata.netAddress"/>
						</th>
						<th width="20"><!--附件-->
							<spring:message code="pa.ins.alert.message.exportdata.fileAdd"/>
						</th>
						<th width="20"><!--删除-->
							<spring:message code="hr.viewCondSql.title.SHANCHU"/>
						</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${insuranceItemDataList}" var="itemData">
						<tr target="PARAM_DATA_NO" rel="${itemData.PARAM_DATA_NO}">
							<td class='td_center'>
								<input type="checkbox" id="c1" name="c1" value="${itemData.PARAM_DATA_NO}" />
							</td>
							<td class='td_center'>${itemData.EMPID}</td>
							<td class='td_center'>${itemData.LOCAL_NAME}</td>
							<td class='td_center'>${itemData.DEPT_NAME}</td>
							<td class='td_center'>${itemData.POST_GRADE_NAME}</td>
							<td class='td_center'>${itemData.STATUS_NAME}</td>
							<td class='td_center'>${itemData.CPNY_NAME}</td>
							
							<td>${itemData.START_MONTH}</td>
<!-- 							<td>${itemData.END_MONTH}</td> -->
							<td>${itemData.RETURN_VALUE}</td>
							<td>${itemData.REMARK}</td>
							<td>${itemData.URL_STR}</td>
							<td class='td_center'><c:forEach items="${itemData.accessoryList }" var="itme">
							<a href="/pa/insurance/downloadFile?fileName=${itme.ACCESSORY_SITE }&file=${itme.ORIGINAL_NAME}" ><spring:message code="pa.ins.alert.message.button.dolowdFile"/></a>
							</c:forEach>	
							</td>
							<td style="text-align: center">
								<a class="delete" href="/pa/insurance/deleteInsDataApply?PARAM_DATA_NO=${itemData.PARAM_DATA_NO}
									&PARAM_NO=${itemData.PARAM_NO}&CPNY_ID=${itemData.CPNY_ID}&applyMark=1"
									target="ajaxTodo" mask="true" width="800" height="600">
									<spring:message code="button.delete"/><!--删除-->
								</a>
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
<!-- 							<th width="50"> -->
<!-- 								<spring:message code="pa.insurance.title.endMonth"/>结束月 -->
<!-- 							</th> -->
							<th width="30">
								<spring:message code="pa.insurance.title.dataValue"/><!--数值-->
							</th>
							<th width="20">
								<spring:message code="hr.viewPromote.title.REMARK"/><!--备注-->
							</th>
							<th width="100"><!--网址-->
								<spring:message code="pa.ins.alert.message.exportdata.netAddress"/>
							</th>
							<th width="20"><!--附件-->
								<spring:message code="pa.ins.alert.message.exportdata.fileAdd"/>
							</th>
							<th width="20"><!--删除-->
								<spring:message code="hr.viewCondSql.title.SHANCHU"/>
							</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${insuranceItemDataList}" var="itemParam">
							<tr target="PARAM_DATA_NO" rel="${itemParam.PARAM_DATA_NO}">
								<td class='td_center'>
									<input type="checkbox" id="c1" name="c1" value="${itemParam.PARAM_DATA_NO}" />
								</td>
								<td class='td_center' >${itemParam.FIELD1_NAME}</td>
								<td class='td_center' >${itemParam.CPNY_NAME}</td>
								<td>${itemParam.START_MONTH}</td>
<!-- 								<td>${itemParam.END_MONTH}</td> -->
								
								<td>${itemParam.RETURN_VALUE}</td>
								<td>${itemParam.REMARK}</td>
								<td>${itemParam.URL_STR}</td>
								<td class='td_center'><c:forEach items="${itemParam.accessoryList }" var="itme">
									<a href="/pa/insurance/downloadFile?fileName=${itme.ACCESSORY_SITE }&file=${itme.ORIGINAL_NAME}" ><spring:message code="pa.ins.alert.message.button.dolowdFile"/></a>
									</c:forEach>	
								</td>
								<td   class='td_center'>
								<a  href="/pa/insurance/deleteInsDataApply?PARAM_DATA_NO=${itemParam.PARAM_DATA_NO}
									&PARAM_NO=${itemParam.PARAM_NO}&CPNY_ID=${itemParam.CPNY_ID}&applyMark=1"
									target="ajaxTodo" >
									<spring:message code="button.delete"/><!--删除-->
								</a>
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
<!-- 							<th width="50"> -->
<!-- 								<spring:message code="pa.insurance.title.endMonth"/>结束月 -->
<!-- 							</th> -->
							<th width="50">
								<spring:message code="pa.insurance.title.dataValue"/><!--数值-->
							</th>
							<th width="20">
								<spring:message code="hr.viewPromote.title.REMARK"/><!--备注-->
							</th>
							<th width="100"><!--网址-->
								<spring:message code="pa.ins.alert.message.exportdata.netAddress"/>
							</th>
							<th width="20"><!--附件-->
								<spring:message code="pa.ins.alert.message.exportdata.fileAdd"/>
							</th>
							<th width="20"><!--删除-->
								<spring:message code="hr.viewCondSql.title.SHANCHU"/>
							</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${insuranceItemDataList}" var="itemParam">
							<tr target="PARAM_DATA_NO" rel="${itemParam.PARAM_DATA_NO}">
								<td class='td_center'>
									<input type="checkbox" id="c1" name="c1" value="${itemParam.PARAM_DATA_NO}" />
								</td>
								<td class='td_center'>${itemParam.FIELD1_NAME}</td>
								<td class='td_center'>${itemParam.FIELD2_NAME}</td>
								<td class='td_center'>${itemParam.CPNY_NAME}</td>
								<td>${itemParam.START_MONTH}</td>
<!-- 								<td>${itemParam.END_MONTH}</td> -->
								
								<td>${itemParam.RETURN_VALUE}</td>
								<td>${itemParam.REMARK}</td>
								<td>${itemParam.URL_STR}</td>
								<td class='td_center'><c:forEach items="${itemParam.accessoryList }" var="itme">
									<a href="/pa/insurance/downloadFile?fileName=${itme.ACCESSORY_SITE }&file=${itme.ORIGINAL_NAME}" ><spring:message code="pa.ins.alert.message.button.dolowdFile"/></a>
									</c:forEach>	
								</td>
								<td   class='td_center'>
								<a  href="/pa/insurance/deleteInsDataApply?PARAM_DATA_NO=${itemParam.PARAM_DATA_NO}
									&PARAM_NO=${itemParam.PARAM_NO}&CPNY_ID=${itemParam.CPNY_ID}&applyMark=1"
									target="ajaxTodo" >
									<spring:message code="button.delete"/><!--删除-->
								</a>
							</td>
							</tr>
						</c:forEach>
					</tbody>
				   </c:otherwise>
				</c:choose>	
			</c:if>
		</table>
	</form>
	<c:set value="/pa/insurance/viewInsuranceInputItemDataApplyList?seach_PARAM_NO=${PARAM_NO}&seach_CPNY_ID=${CPNY_ID}" var="pageUrl" />
	<form id="pagerForm" method="post" action="${pageUrl}">
		<input type="hidden" name="pageNum" value="${pageNum}" />
		<input type="hidden" name="numPerPage" value="${numPerPage}" />
	</form>
	<div class="panelBar">
		<div class="pages">
			<span><spring:message code="public.title.view"/><!-- 显示 --></span>
				<select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value},'viewInsuranceInputItemDataApply')">
					<option value="10"  <c:if test="${numPerPage == 10 }" >selected</c:if> >10</option>
					<option value="20"  <c:if test="${numPerPage == 20 }" >selected</c:if> >20</option>
					<option value="30"  <c:if test="${numPerPage == 30 }" >selected</c:if> >30</option>
					<option value="50"  <c:if test="${numPerPage == 50 }" >selected</c:if> >50</option>
					<option value="1000"  <c:if test="${numPerPage == 1000 }" >selected</c:if> >1000</option>
				</select>
			<span><spring:message code="public.title.tiao"/><!--条-->,<spring:message code="public.title.gong"/><!--共-->
			${totalCount}<spring:message code="public.title.tiao"/><!--条--></span>	
		</div>
		<div class="pagination" rel="viewInsuranceInputItemDataApply" totalCount="${totalCount}" numPerPage="${numPerPage}" 
		     currentPage="${pageNum}"></div>
	</div>
</div>
</div>