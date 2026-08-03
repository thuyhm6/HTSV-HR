<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function validateCallbackSuspend(form,callback) {	
	var $form = $("#suspend");
	
	if (!$form.valid()) {
		return false;
	}


	var checked = false ;

	$form.find(":checkbox[id='suspend']").each(function(index, checkBoxObj){
	    
	    if(checkBoxObj.checked){
	      checked = true ;      
	    }
	    
	  });
	 if(!checked){
		 	//请选择信息再进行保存操作
			alertMsg.error('<spring:message code="hr.alert.message.viewPersonalInfo.checkBoxForChecked"/>');
			return false;
		 }
	$form.find(":checkbox[id='suspend']").each(function(index, checkBoxObj){
	    if(checkBoxObj.checked){
	      checked = true ;

	      var empid = $(checkBoxObj).val() ;
	      
		  if($form.find("[name='START_DATE_" + empid + "']").val()==''){
				//alert("生效日期不能为空");
				alertMsg.error('<spring:message code="hr.alert.message.viewUpgrade.checkNotNullFunctionDate"/>');
				$form.find("[name='START_DATE_" + empid + "']").focus();
				checked=false;
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
				success: callback || DWZ.ajaxDone,
				error: DWZ.ajaxError
			});	
			
			return false;
		}
	}
	
	return false ;
}
function navTabSearchSuspend(form, navTabId){
	var $form = $("#searchsuspend");
		
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
	<form id="searchsuspend" onsubmit="return navTabSearchSuspend(this);" action="/hrm/transferOrder/viewSuspend" method="post" rel="pagerForm">
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
				<td>
					<spring:message code="hr.viewTransferPromote.title.TYPE"/>
					<!-- 类型 -->
					<ait:SelectSyCodeByCpnyID name="seach_SUSPEND_TYPE" parentNo="16193" cnpyID="${defaultCpny}" selected="${SUSPEND_TYPE}"/>
				</td>
				<a id="searchEmp" name="searchEmp" target="dialog" mask="true"  href="#" lookupGroup="person" width="950" height="600"></a>
				<input type="hidden" id="eids" name="eids" value="${eids}">
				
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
	<form style="margin:0px;padding:0px;" id="suspend" onsubmit="return validateCallbackSuspend(this,navTabAjaxDone);" action="/hrm/transferOrder/saveSuspend" method="post"  class="pageForm required-validate">
	<div class="formBar">
			<tr>	
			<label style="float: left">
				<input type="checkbox" class="checkboxCtrl" group="suspend" />
				<spring:message code="hr.viewUpgrade.title.CHECKALL"/>
				<!--全选-->
			</label>
			<ul>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" class="checkboxCtrl" group="suspend"
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
	<table width="100%" class="tablea" layoutH="145">
		<thead>
			<tr >
				<th>
					<spring:message code="public.title.choose"/>
					<!--选择-->
				</th>
				<th>
					<spring:message code="hr.viewPersonalInfo.title.EMPID"/>
					<!--社号-->
				</th>
				<th>
					<spring:message code="hr.viewPersonalInfo.title.LOCAL_NAME"/>
					<!--姓名-->
				</th>
				<th>
					<spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/>
					<!--部门-->
				</th>
				<th>	
					<spring:message code="hr.viewPersonalInfo.title.POSITION_NAME"/>
					<!--职(岗)位-->
				</th>
				<th>
					<spring:message code="hr.viewPersonalInfo.title.POST_NAME"/>
					<!--职级名称(职务)-->
				</th>
				<th>
					<spring:message code="hr.viewPersonalInfo.title.STATUS_NAME"/>
					<!--员工状态-->
				</th>
				<th><spring:message code="hr.viewPromote.title.EFFECTIVE_DATE"/>
					<!--生效日期-->
				</th>
				<th>
					<spring:message code="hr.viewSuspend.title.SUSPENDMARK"/>
					<!--停职事由-->
				</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${suspendList}" var="suspend">
				<tr target="sid" rel="${suspend.EMPID}">
					<input type="hidden" name="PERSON_ID_${suspend.EMPID}" value="${suspend.PERSON_ID}"/>
					<input type="hidden" name="STATUS_CODE_${suspend.EMPID}" value="${suspend.STATUS_CODE}"/>
					<input type="hidden" name="SUSPEND_TYPE" value="${SUSPEND_TYPE}"/>
					<td>
						<input type="checkbox" id="suspend" name="suspend" value="${suspend.EMPID}" />
					</td>
					<td>${suspend.EMPID}</td>
					<td>${suspend.LOCAL_NAME}</td>
					<td>
						${suspend.DEPT_NAME}
					</td>
					<td>
						${suspend.POSITION_NAME}
					</td>
					<td>						
						${suspend.POST_NAME}
					</td>
					<td>
						${suspend.STATUS_NAME}
					</td>
					<td>
						<input id="START_DATE" name="START_DATE_${suspend.EMPID}" size="10" class="date">
					</td>
					<td>
						<input id="SUSPEND_REASON" name="SUSPEND_REASON_${suspend.EMPID}" size="10">
					</td>							
				</tr>
			</c:forEach>
		</tbody>
	</table>			
</form>
			<c:set value="/hrm/transferOrder/viewSuspend" var="pageUrl"/>
			<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>
