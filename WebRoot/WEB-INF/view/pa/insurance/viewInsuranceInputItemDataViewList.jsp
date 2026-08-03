<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script type="text/javascript">
function validateCallbackUpdateIn(form,callback) {	
	var $form = $("#viewInsDataViewList");
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

function deleteInsDataApplyCallback(form,callback) {
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
		alertMsg.error('<spring:message code="alert.message.pa.insurance.pleaseInputDataFirst"/>');
		return false;
	}

    $form.attr("action","/pa/insurance/deleteInsDataApplyInBatch");
    if (confirm ('<spring:message code="alert.message.pa.insurance.confirmSubmit"/>')){
		$.ajax({
			type: form.method || 'POST',
			url:$form.attr("action"),
			data:$form.serializeArray(),
			dataType:"json",
			cache: false,
			success: function(data){ //请求成功后处理函数。
				if(data.statusCode=="200"){
					navTabSearch("seachInsuranceInputItemDataViewList");
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
		return false;
    }
}
</script>

	<div class="pageHeader" style="border:1px #B8D0D6 solid">
		<form onsubmit="return divSearch(this, 'viewInsuranceInputItemDataView');" 
		    id="seachInsuranceInputItemDataViewList" name="seachInsuranceInputItemDataViewList"
			action="/pa/insurance/viewInsuranceInputItemDataViewList?pageNum=1&seach_PARAM_NO=${PARAM_NO}" 
			method="post" rel="pagerForm" >
			<div class="searchBar">
				<table class="searchContent">
					<tr>
						<td><!--  公司-->
							<spring:message code="ar.viewcycleparameter.title.gongsi"/>：
						</td>
						<td>
							<ait:SyCompany target="login" name="seach_CPNY_ID" language="zh" limit="ALL" activity="1" selected="${CPNY_ID}"/>
						</td>
						<td><!--决裁情况-->
							<spring:message code="ess.viewApply.title.affirmCondition"/>：
						</td>
						<td>
							<select id="seach_AFFIRM_STATUS" name="seach_AFFIRM_STATUS">
								<option value="">
		           	     			<spring:message code="pa.insurance.title.pleaseChoose"/><!--请选择:-->
		           	     		</option>
		           	     		<option value="1" <c:if test="${AFFIRM_STATUS eq '1' }">selected</c:if>>未决裁</option>
		           	     		<option value="2" <c:if test="${AFFIRM_STATUS eq '2' }">selected</c:if>>已决裁</option>
		           	     		<option value="3" <c:if test="${AFFIRM_STATUS eq '3' }">selected</c:if>>全部</option>
							</select>
						</td>
						<td><!--申请日期: -->
							<spring:message code="ess.infoApply.title.essApplyTime"/>：
						</td>
						<td>
							<input type="text" id="seach_APPLY_START_DATE" name="seach_APPLY_START_DATE" class="date" readonly="true" value="${APPLY_START_DATE }"/>~
						</td>
						<td>
							<input type="text" id="seach_APPLY_END_DATE" name="seach_APPLY_END_DATE" class="date" readonly="true" value="${APPLY_END_DATE }"/>
						</td>
						<td>&nbsp;</td>
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
<div class="pageContent" style="border-left:1px #B8D0D6 solid;border-right:1px #B8D0D6 solid">
	<form id="viewInsDataViewList" name="viewInsDataViewList" method="post"
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
					<div class="subBar">
			            <div class="buttonActive">
						    <a class="update" onclick="deleteInsDataApplyCallback('viewInsuranceInputItemDataView',DWZ.ajaxDone,'1')" href="#" ><span>
						   	<!-- 批量删除 --><spring:message code="pa.ins.alert.message.button.deleteInBatch"/></span></a>
                        </div>				
				    </div>	
				</li>
				<input id="seach_PARAM_NO" name="seach_PARAM_NO" type="hidden" size="30" value="${PARAM_NO}" />
				<input id="PARAM_NO" name="PARAM_NO" type="hidden" size="30" value="${PARAM_NO}" />
			</ul>
		</div>
		<table class="table" width="99%" layoutH="195">
			<c:if test="${insuranceInputItemParamInfo.DISTINCT_FIELD eq 'PERSON_ID'}">
				<thead>
					<tr>
						<th></th>
						<th><!--申请日期 -->
							<spring:message code="ess.infoApply.title.essApplyTime"/>
						</th>
						<th><!--工号-->
							<spring:message code="public.title.empId"/>
						</th>
						<th><!--姓名-->
							<spring:message code="public.title.name"/>
						</th>
						<th><!--部门-->
							<spring:message code="public.title.deptName"/>
						</th>
						<th><!--职级-->
							<spring:message code="pa.insurance.title.postGrade"/>
						</th>
						<th><!--状态-->
							<spring:message code="pa.insurance.title.status"/>
						</th>
						<th><!--公司法人-->
							<spring:message code="pa.insurance.title.companyLegalPerson"/>
						</th>
						<th><!--开始月-->
							<spring:message code="pa.insurance.title.startMonth"/>
						</th>
						<th><!--结束月-->
							<spring:message code="pa.insurance.title.endMonth"/>
						</th>
						<th><!--数值-->
							<spring:message code="pa.insurance.title.dataValue"/>
						</th>
						<th><!--备注-->
							<spring:message code="hr.viewPromote.title.REMARK"/>
						</th>
						<th><!--网址-->
							<spring:message code="pa.ins.alert.message.exportdata.netAddress"/>
						</th>
						<th><!--附件-->
							<spring:message code="pa.ins.alert.message.exportdata.fileAdd"/>
						</th>
						<th><!--统一试用公司-->
							<spring:message code="pa.ins.alert.message.title.theSameUseCompany"/>
						</th>
						<th><!--决裁情况-->
							<spring:message code="ess.viewApply.title.affirmCondition"/>
						</th>
						<th><!--删除-->
							<spring:message code="hr.viewCondSql.title.SHANCHU"/>
						</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${insuranceItemDataList}" var="itemData">
						<tr target="PARAM_DATA_NO" rel="${itemData.PARAM_DATA_NO}">
							<td>
								<c:if test="${itemData.AFFIRM_FLAG eq '0'}">
									<input type="checkbox" id="c1" name="c1" value="${itemData.PARAM_DATA_NO}" />
								</c:if>
							</td>
							<td>${itemData.APPLY_DATE}</td>
							<td>${itemData.EMPID}</td>
							<td>${itemData.LOCAL_NAME}</td>
							<td>${itemData.DEPT_NAME}</td>
							<td>${itemData.POST_GRADE_NAME}</td>
							<td>${itemData.STATUS_NAME}</td>
							<td>${itemData.CPNY_NAME}</td>
							
							<td>${itemData.START_MONTH}</td>
							<td>${itemData.END_MONTH}</td>
							<td>${itemData.RETURN_VALUE}</td>
							<td>${itemData.REMARK}</td>
							
							<td>${itemData.URL_STR}</td>
							<td style="text-align: center"><!-- 下载 -->
								<spring:message code="pa.ins.alert.message.button.dolowdFile"/>
							</td>
							<td style="text-align: center"><!--点击查看-->
								<spring:message code="pa.ins.alert.message.title.clickForDetail"/>
							</td>
							<td style="text-align: center">
								<c:if test="${itemData.AFFIRM_FLAG eq '0'}">
									<b><font color="blue"><!--未决裁--><spring:message code="ess.trans.title.notAffirmed"/></font></b>
								</c:if>
								<c:if test="${itemData.AFFIRM_FLAG eq '1'}">
									<b><font color="green"><!--通过--><spring:message code="ess.trans.title.pass"/></font></b>
								</c:if>
								<c:if test="${itemData.AFFIRM_FLAG eq '2'}">
									<b><font color="red"><!--否决--><spring:message code="ess.trans.title.reject"/></font></b>
								</c:if>
							</td>
							<td style="text-align: center">
								<c:if test="${itemData.AFFIRM_FLAG eq '0'}">
									<li>
										<a class="delete" href="/pa/insurance/deleteInsDataApply?PARAM_DATA_NO=${itemData.PARAM_DATA_NO}
											&PARAM_NO=${itemData.PARAM_NO}&CPNY_ID=${itemData.CPNY_ID}"
											target="ajaxTodo" mask="true" width="800" height="600"><span>
											<spring:message code="button.delete"/><!--删除--></span>
										</a>
									</li>
								</c:if>
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
							<th></th>
							<th><!--申请日期 -->
								<spring:message code="ess.infoApply.title.essApplyTime"/>
							</th>
							<th>${insuranceInputItemParamInfo.DISTINCT_FIELD_NAME}</th>
							<th>
								<spring:message code="pa.insurance.title.companyLegalPerson"/><!--公司法人-->
							</th>
							<th>·
								<spring:message code="pa.insurance.title.startMonth"/><!--开始月-->
							</th>
							<th>
								<spring:message code="pa.insurance.title.endMonth"/><!--结束月-->
							</th>
							<th>
								<spring:message code="pa.insurance.title.dataValue"/><!--数值-->
							</th>
							<th>
								<spring:message code="hr.viewPromote.title.REMARK"/><!--备注-->
							</th>
							<th><!--网址-->
								<spring:message code="pa.ins.alert.message.exportdata.netAddress"/>
							</th>
							<th><!--附件-->
								<spring:message code="pa.ins.alert.message.exportdata.fileAdd"/>
							</th>
							<th><!--统一试用公司-->
								<spring:message code="pa.ins.alert.message.title.theSameUseCompany"/>
							</th>
							<th><!--决裁情况-->
								<spring:message code="ess.viewApply.title.affirmCondition"/>
							</th>
							<th><!--删除-->
								<spring:message code="hr.viewCondSql.title.SHANCHU"/>
							</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${insuranceItemDataList}" var="itemParam">
							<tr target="PARAM_DATA_NO" rel="${itemParam.PARAM_DATA_NO}">
								<td>
									<c:if test="${itemParam.AFFIRM_FLAG eq '0'}">
										<input type="checkbox" id="c1" name="c1" value="${itemParam.PARAM_DATA_NO}" />
									</c:if>
								</td>
								<td>${itemParam.APPLY_DATE}</td>
								<td>${itemParam.FIELD1_NAME}</td>
								<td>${itemParam.CPNY_NAME}</td>
								
								<td>${itemParam.START_MONTH}</td>
								<td>${itemParam.END_MONTH}</td>
								<td>${itemParam.RETURN_VALUE}</td>
								<td>${itemParam.REMARK}</td>
								<td>${itemParam.URL_STR}</td>
								
								<td style="text-align: center"><!-- 下载 -->
									<spring:message code="pa.ins.alert.message.button.dolowdFile"/>
								</td>
								<td style="text-align: center"><!--点击查看-->
									<spring:message code="pa.ins.alert.message.title.clickForDetail"/>
								</td>
								<td style="text-align: center">
									<c:if test="${itemParam.AFFIRM_FLAG eq '0'}">
										<b><font color="blue"><!--未决裁--><spring:message code="ess.trans.title.notAffirmed"/></font></b>
									</c:if>
									<c:if test="${itemParam.AFFIRM_FLAG eq '1'}">
										<b><font color="green"><!--通过--><spring:message code="ess.trans.title.pass"/></font></b>
									</c:if>
									<c:if test="${itemParam.AFFIRM_FLAG eq '2'}">
										<b><font color="red"><!--否决--><spring:message code="ess.trans.title.reject"/></font></b>
									</c:if>
								</td>
								<td style="text-align: center">
									<c:if test="${itemParam.AFFIRM_FLAG eq '0'}">
										<li>
											<a class="delete" href="/pa/insurance/deleteInsDataApply?PARAM_DATA_NO=${itemParam.PARAM_DATA_NO}
												&PARAM_NO=${itemParam.PARAM_NO}&CPNY_ID=${itemParam.CPNY_ID}"
												target="ajaxTodo" mask="true" width="800" height="600"><span>
												<spring:message code="button.delete"/><!--删除--></span>
											</a>
										</li>
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
							<th><!--申请日期 -->
								<spring:message code="ess.infoApply.title.essApplyTime"/>
							</th>
							<th>${insuranceInputItemParamInfo.DISTINCT_FIELD_NAME }</th>
							<th>${insuranceInputItemParamInfo.DISTINCT_FIELD_2ND_NAME }</th>
							<th>
								<spring:message code="pa.insurance.title.companyLegalPerson"/><!--公司法人-->
							</th>
							<th>
								<spring:message code="pa.insurance.title.startMonth"/><!--开始月-->
							</th>
							<th>
								<spring:message code="pa.insurance.title.endMonth"/><!--结束月-->
							</th>
							<th>
								<spring:message code="pa.insurance.title.dataValue"/><!--数值-->
							</th>
							<th>
								<spring:message code="hr.viewPromote.title.REMARK"/><!--备注-->
							</th>
							<th><!--网址-->
								<spring:message code="pa.ins.alert.message.exportdata.netAddress"/>
							</th>
							<th><!--附件-->
								<spring:message code="pa.ins.alert.message.exportdata.fileAdd"/>
							</th>
							<th><!--统一试用公司-->
								<spring:message code="pa.ins.alert.message.title.theSameUseCompany"/>
							</th>
							<th><!--决裁情况-->
								<spring:message code="ess.viewApply.title.affirmCondition"/>
							</th>
							<th><!--删除-->
								<spring:message code="hr.viewCondSql.title.SHANCHU"/>
							</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${insuranceItemDataList}" var="itemParam">
							<tr target="PARAM_DATA_NO" rel="${itemParam.PARAM_DATA_NO}">
								<td>
									<c:if test="${itemParam.AFFIRM_FLAG eq '0'}">
										<input type="checkbox" id="c1" name="c1" value="${itemParam.PARAM_DATA_NO}" />
									</c:if>
								</td>
								<td>${itemParam.APPLY_DATE}</td>
								<td>${itemParam.FIELD1_NAME}</td>
								<td>${itemParam.FIELD2_NAME}</td>
								<td>${itemParam.CPNY_NAME}</td>
								
								<td>${itemParam.START_MONTH}</td>
								<td>${itemParam.END_MONTH}</td>
								<td>${itemParam.RETURN_VALUE}</td>
								<td>${itemParam.REMARK}</td>
								<td>${itemParam.URL_STR}</td>
								
								<td style="text-align: center"><!-- 下载 -->
									<spring:message code="pa.ins.alert.message.button.dolowdFile"/>
								</td>
								<td style="text-align: center"><!--点击查看-->
									<spring:message code="pa.ins.alert.message.title.clickForDetail"/>
								</td>
								<td style="text-align: center">
									<c:if test="${itemParam.AFFIRM_FLAG eq '0'}">
										<b><font color="blue"><!--未决裁--><spring:message code="ess.trans.title.notAffirmed"/></font></b>
									</c:if>
									<c:if test="${itemParam.AFFIRM_FLAG eq '1'}">
										<b><font color="green"><!--通过--><spring:message code="ess.trans.title.pass"/></font></b>
									</c:if>
									<c:if test="${itemParam.AFFIRM_FLAG eq '2'}">
										<b><font color="red"><!--否决--><spring:message code="ess.trans.title.reject"/></font></b>
									</c:if>
								</td>
								<td style="text-align: center">
									<c:if test="${itemParam.AFFIRM_FLAG eq '0'}">
										<li>
											<a class="delete" href="/pa/insurance/deleteInsDataApply?PARAM_DATA_NO=${itemParam.PARAM_DATA_NO}
												&PARAM_NO=${itemParam.PARAM_NO}&CPNY_ID=${itemParam.CPNY_ID}"
												target="ajaxTodo" mask="true" width="800" height="600"><span>
												<spring:message code="button.delete"/><!--删除--></span>
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
	<c:set value="/pa/insurance/viewInsuranceInputItemDataViewList?seach_PARAM_NO=${PARAM_NO}&seach_CPNY_ID=${CPNY_ID}" var="pageUrl" />
	<form id="pagerForm" method="post" action="${pageUrl}">
		<input type="hidden" name="pageNum" value="${pageNum}" />
		<input type="hidden" name="numPerPage" value="${numPerPage}" />
	</form>
	<div class="panelBar">
		<div class="pages">
			<span><spring:message code="public.title.view"/><!-- 显示 --></span>
				<select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value},'viewInsuranceInputItemDataView')">
					<option value="10"  <c:if test="${numPerPage == 10 }" >selected</c:if> >10</option>
					<option value="20"  <c:if test="${numPerPage == 20 }" >selected</c:if> >20</option>
					<option value="30"  <c:if test="${numPerPage == 30 }" >selected</c:if> >30</option>
					<option value="50"  <c:if test="${numPerPage == 50 }" >selected</c:if> >50</option>
				</select>
			<span><spring:message code="public.title.tiao"/><!--条-->,<spring:message code="public.title.gong"/><!--共-->
			${totalCount}<spring:message code="public.title.tiao"/><!--条--></span>	
		</div>
		<div class="pagination" rel="viewInsuranceInputItemDataView" totalCount="${totalCount}" numPerPage="${numPerPage}" 
		     currentPage="${pageNum}"></div>
	</div>
</div>