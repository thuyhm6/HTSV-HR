<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function validateCallbacktransferNormal(form,callback) {	
	var $form = $("#transferNormal");
	
	if (!$form.valid()) {
		return false;
	}


	var checked = false ;

	$form.find(":checkbox[id='d1']").each(function(index, checkBoxObj){
	    
	    if(checkBoxObj.checked){
	      checked = true ;      
	    }
	    
	  });
	 if(!checked){
		 	//请选择信息再进行保存操作
			alertMsg.error('<spring:message code="hr.alert.message.viewPersonalInfo.checkBoxForChecked"/>');
			return false;
		 }
	$form.find(":checkbox[id='d1']").each(function(index, checkBoxObj){
	    if(checkBoxObj.checked){
	      checked = true ;

	      var empid = $(checkBoxObj).val() ;
		  var cpny = $('#cpny').val();  
		   
		  if($form.find("[name='START_DATE_" + empid + "']").val()==''){
				//alert("生效日期不能为空");
			 
				alertMsg.error('<spring:message code="hr.alert.message.viewUpgrade.checkNotNullFunctionDate"/>');
				$form.find("[name='START_DATE_" + empid + "']").focus();
				checked=false;
			}else if($form.find("[name='BECOME_TYPE_" + empid + "']").val()==''){
				//alert("转正类型不能为空");
			 
				alertMsg.error('<spring:message code="hr.alert.message.viewTransferNormal.checkNotNullTransferNormalType"/>');
				 
				$form.find("[name='BECOME_TYPE_" + empid + "']").focus();
				checked=false;
				//劳务转正
			}else if($form.find("[name='BECOME_TYPE_" + empid + "']").val()== '21368'){ 
					// 劳务派遣  类型员工 才能做劳务转正 
					if($form.find("[name='EMP_TYPE_CODE_R_" + empid + "']").val()!='14890' &&cpny=='C04') {
						//alert("当做劳务转正时,员工类型必须为劳务派遣员工");
						alertMsg.error('<spring:message code="hr.alert.message.viewTransferNormal.checkTransferNormalType"/>');
						$form.find("[name='EMP_TYPE_CODE_" + empid + "']").focus();
						checked=false;
					}
					
			}	  
	    }
	  });
	
	if(checked){
		
		//确定要提交吗？
		if (confirm ('<spring:message code="hr.viewEvaluate.title.COMMIT_CONFIRM"/>')){	
		  	$.ajax({
				type: form.method || 'POST',
				url:$form.attr("action"),
				data:$form.serializeArray(),
				dataType:"json",
				cache: false,
				success: function(data) {
					if(300==data.statusCode){
						alertMsg.error(data.message);
						return false;
					}else{
						//alert("发令成功！");
						alertMsg.correct('<spring:message code="hr.alert.message.dekreti_success"/>');
					}
				  }
			});
			return false;
		}
	}
	
	return false ;
}
function navTabSearchtransferNormal(form, navTabId){
	var $form = $("#searchTransferNormal");
		
	if (form[DWZ.pageInfo.pageNum]){
		form[DWZ.pageInfo.pageNum].value = 1 ;
	}
	var sd=document.getElementById("seach_JOIN_COMPANY_START_DATE").value;
	var ed=document.getElementById("seach_JOIN_COMPANY_END_DATE").value;

	var date1 = sd.replaceAll("-","");
	var date2 = ed.replaceAll("-","");
	
	if (date1 - date2 > 0) {
		//alert("入司开始如期不能晚于入司结束日期");
		alertMsg.error('<spring:message code="hr.alert.message.viewUpgrade.checkNotNullJoinCompanyDateAndEndDate"/>');
		document.getElementById("seach_JOIN_COMPANY_START_DATE").focus();
		return false;
	}
	var params = $(form).serializeArray();
	if (!form[DWZ.pageInfo.pageNum]){
		params.push({name: DWZ.pageInfo.pageNum, value: 1}) ;
	}
	
	navTab.reload($form.attr('action'), {data: params, navTabId:navTabId});
	return false;
}


</script>
<div style="padding:5px;">
<div class="pageHeader">
	<form id="searchTransferNormal" onsubmit="return navTabSearchtransferNormal(this);" action="/hrm/transferOrder/viewTransferNormal" method="post" rel="pagerForm">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					<spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/>
					<!-- 部门： -->
					<ait:deptTree name="seach_DEPTMENTNO" limit="hr" selected="${DEPTMENTNO}"/>
				</td>
				<td>
					<spring:message code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME"/>
					<!--社号/姓名：-->
					<input type="text" name="seach_key" value="${key}" />
				</td>
				<td>
					<span class="span_left"><spring:message code="hr.viewContractInfoForSearch.title.JOIN_COMPANY_DATE"/><!--入司日期：--></span><input type="text" id="seach_JOIN_COMPANY_START_DATE" name="seach_JOIN_COMPANY_START_DATE" value="${JOIN_COMPANY_START_DATE}" class="date"/><span class="span_left">~</span>
							  <input type="text" id="seach_JOIN_COMPANY_END_DATE" name="seach_JOIN_COMPANY_END_DATE" value="${JOIN_COMPANY_END_DATE}" class="date"/>
				</td>
				
				<a id="searchEmp" name="searchEmp" target="dialog" mask="true"  href="#" lookupGroup="person" width="950" height="600"></a>
				<input type="hidden" id="eids" name="eids" value="${eids}">
				<input type="hidden" id="cpny" name="cpny" value="${defaultCpny}">
			</tr>
		</table>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="showSearch('${request_navTabId}');"><spring:message code="ar.viewempcalender.title.search"/><!-- 搜索 --></button></div></div></li>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit"><spring:message code="public.title.search"/><!-- 检索 --></button></div></div></li>
			</ul>
		</div>
	</div>
	</form>
</div>
</div>
<div class="pageContent" style="padding:5px;">
	<form style="margin:0px;padding:0px;" id="transferNormal" onsubmit="return validateCallbacktransferNormal(this,navTabAjaxDone);" action="/hrm/transferOrder/saveTransferNormal" method="post"  class="pageForm required-validate">
	<div class="formBar">
			<tr>	
			<label style="float: left">
				<input type="checkbox" class="checkboxCtrl" group="d1" />
				<spring:message code="hr.viewUpgrade.title.CHECKALL"/>
				<!--全选-->
			</label>
			<ul>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" class="checkboxCtrl" group="d1"
								selectType="invert">
								<spring:message code="hr.viewUpgrade.title.CTRLSHIFT"/>
								<!--反选-->
							</button>
						</div>
					</div>
				</li>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">
								<spring:message code="hr.viewUpgrade.title.SAVE"/>
								<!--保存-->
							</button>
						</div>
					</div>
				</li>
				</ul>
		</tr>
	</div>
	<table width="100%" class="table" layoutH="140">
		<thead>
			<tr >
				<th width="5%">
					<spring:message code="public.title.choose"/>
					<!--选择-->
				</th>
				<th width="8%" orderField="HR.EMPID" class="${orderDirection}">
					<spring:message code="hr.viewPersonalInfo.title.EMPID"/>
					<!--社号-->
				</th>
				<th width="10%" orderField="nlssort(HR.LOCAL_NAME,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
					<spring:message code="hr.viewPersonalInfo.title.LOCAL_NAME"/>
					<!--姓名-->
				</th>
				<th width="15%" orderField="nlssort(DEPT_NAME,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
					<spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/>
					<!--部门-->
				</th>
				<th width="10%" orderField="nlssort(POSITION_NAME,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
					<spring:message code="hr.viewPersonalInfo.title.POSITION_NAME"/>
					<!--职(岗)位-->
				</th>
				<th width="10%" orderField="nlssort(POST_NAME,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
					<spring:message code="hr.viewPersonalInfo.title.POST_NAME"/>
					<!--职级名称(职务)-->
				</th>
				<th width="8%" orderField="nlssort(STATUS_NAME,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
					<spring:message code="hr.viewPersonalInfo.title.STATUS_NAME"/>
					<!--员工状态-->
				</th>
				<th width="10%" orderField="BEFORE_END_PROBATION_DATE" class="${orderDirection}">
					<spring:message code="hr.viewPersonalInfo.title.BEFORE_END_PROBATION_DATE"/>
					<!--预转正日期-->
				</th>
				<th width="10%">
					<spring:message code="hr.viewPromote.title.EFFECTIVE_DATE"/>
					<!--生效日期-->
				</th>
				<th>
					<spring:message code="hr.viewTransferNormal.title.TRANSFERNORMALTYPE"/>
					<!--转正类型-->
				</th>
				<th>
					<spring:message code="hr.viewPersonalInfo.title.EMP_TYPE_NAME"/>
					<!--员工类型-->
				</th>
				<th>
					<spring:message code="hr.viewPromote.title.REMARK"/>
					<!--备注-->
				</th>
				
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${transferNormalList}" var="transferNormal">
				<tr target="sid" rel="${transferNormal.EMPID}">
				<input type="hidden" name="PERSON_ID_${transferNormal.EMPID}" value="${transferNormal.PERSON_ID}"/>
				
					<td>
						<input type="checkbox" id="d1" name="d1" value="${transferNormal.EMPID}" />
						<input type="hidden" name="EMP_TYPE_CODE_R_${transferNormal.EMPID}" value="${transferNormal.EMP_TYPE_CODE}" />
					</td>
					<td>${transferNormal.EMPID}</td>
					<td>${transferNormal.LOCAL_NAME}</td>
					<td>
						${transferNormal.DEPT_NAME}
					</td>
					<td>
						${transferNormal.POSITION_NAME}
					</td>
					<td>						
						${transferNormal.POST_NAME}
					</td>
					<td>
						${transferNormal.STATUS_NAME}
					</td>	
					<td>
						${transferNormal.BEFORE_END_PROBATION_DATE}
					</td>
					<td>
						<input id="START_DATE" name="START_DATE_${transferNormal.EMPID}" size="10" class="date">
					</td>
					<td>
						<ait:SelectSyCodeByCpnyID name="BECOME_TYPE_${transferNormal.EMPID}" parentNo="1360" cnpyID="${defaultCpny}"limit="all"/>
					</td>
					<td>
						<ait:SelectSyCodeByCpnyID name="EMP_TYPE_CODE_${transferNormal.EMPID}" parentNo="1368" cnpyID="${defaultCpny}"limit="all"/>
					</td>
					<td>
						<input id="REMARK" name="REMARK_${transferNormal.EMPID}" size="10">
					</td>							
				</tr>
			</c:forEach>
		</tbody>
	</table>			
</form>
			<c:set value="/hrm/transferOrder/viewTransferNormal" var="pageUrl"/>
			<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>
