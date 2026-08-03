<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function validateCallbackPayrise(form,callback) {	
	var $form = $("#payStep");
	
	if (!$form.valid()) {
		return false;
	}

	var checked = false ;

	$form.find(":checkbox[id='payStep']").each(function(index, checkBoxObj){
	    
	    if(checkBoxObj.checked){
	      checked = true ;      
	    }
	    
	  });
	 if(!checked){
		 	//请选择信息再进行保存操作
			alertMsg.error('<spring:message code="hr.alert.message.viewPersonalInfo.checkBoxForChecked"/>');
			return false;
		 }
	$form.find(":checkbox[id='payStep']").each(function(index, checkBoxObj){
		if(checkBoxObj.checked){
			checked = true ;
			
			var empid = $(checkBoxObj).val() ;
			   
			if($form.find("[name='START_DATE_" + empid + "']").val()==''){
				//alert("生效日期不能为空");
				alertMsg.error('<spring:message code="hr.alert.message.viewUpgrade.checkNotNullFunctionDate"/>');
				$form.find("[name='START_DATE_" + empid + "']").focus();
				checked=false;
				
			}
			if($form.find("[name='OLD_POST_GRADE_NO_" + empid + "']").val()==$form.find("[name='OLD_POST_GRADE_NOCheck_" + empid + "']").val()&&$form.find("[name='PAY_STEP_NO_" + empid + "']").val()==$form.find("[name='PAY_STEP_NOCheck_" + empid + "']").val()){
				alertMsg.error('工号为'+empid+'的员工号俸及(薪资)职级,号俸均未做出修改');
				$form.find("[name='PAY_STEP_NO_" + empid + "']").focus();
				$form.find("[name='OLD_POST_GRADE_NO_" + empid + "']").focus();
				checked=false;
			}
		}
	  });
	
	if(checked){
		$form.find(":checkbox[id='payStep']").each(function(index, checkBoxObj){
			if(checkBoxObj.checked){
				var empid = $(checkBoxObj).val() ;
				$form.find("[name='PAY_STEP_NOCheck_" + empid + "']").val($form.find("[name='PAY_STEP_NO_" + empid + "']").val());
				$form.find("[name='OLD_POST_GRADE_NOCheck_" + empid + "']").val($form.find("[name='OLD_POST_GRADE_NO_" + empid + "']").val());
			}
		});
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
function navTabSearchPayrise(form, navTabId){
	var $form = $("#searchPayrise");
		
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


function getReturnValue(itemNo,empid,personId){
	
	$.ajax({
		 cache: false,
		 type: 'post',
		 url: "/hrm/transferOrder/getReturnValueByItemNo?PERSON_ID="+personId,
		 data: 'ITEM_NO=' + itemNo,
		 dataType:"json",
		 success: function(data) {
			if($(data).size() > 0){
				document.getElementById("nowPay_"+empid).value=data.returnValue;
			}
		 }
		 
		});
}
</script>
<div style="padding:5px;">
<div class="pageHeader">
	<form id="searchPayrise" onsubmit="return navTabSearchPayrise(this);" action="/hrm/transferOrder/viewPayStep" method="post" rel="pagerForm">
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
	<form style="margin:0px;padding:0px;" id="payStep" onsubmit="return validateCallbackPayrise(this,navTabAjaxDone);" action="/hrm/transferOrder/savePayStep" method="post"  class="pageForm required-validate">
	<div class="formBar">
			<tr>	
			<label style="float: left">
				<input type="checkbox" class="checkboxCtrl" group="payStep" />
				<spring:message code="hr.viewUpgrade.title.CHECKALL"/>
				<!--全选-->
			</label>
			<ul>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" class="checkboxCtrl" group="payStep"
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
					<spring:message code="hr.viewPersonalInfo.title.POST_GRADE_NAME"/>
					<!--职级(GGS)-->
				</th>
				<th>
					<spring:message code="hr.viewPersonalInfo.title.POST_NAME"/>
					<!--职级名称(职务)-->
				</th>
				<th>
					<spring:message code="hr.viewPersonalInfo.title.POSITION_NAME"/>
					<!--职(岗)位-->
				</th>
				
				<th>
					号俸
					<!--生效日期-->
				</th>
				<th>
					<spring:message
							code="hr.viewPersonalInfo.title.OLD_POST_GRADE_NAME" />
					<!--(薪资)职级,号俸-->
				</th>
				
				<th>
					<spring:message code="hr.viewPromote.title.EFFECTIVE_DATE"/>
					<!--生效日期-->
				</th>				
				<th>
					<spring:message code="hr.viewPayrise.title.ADJUST_REASON"/>
					<!--调整是由-->
				</th>
				
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${payStepList}" var="payStep">
				<tr target="sid" rel="${payStep.EMPID}">
					<td>
						<input type="checkbox" id="payStep" name="payStep" value="${payStep.EMPID}" />
						<input type="hidden" name="EMPID_${payStep.EMPID}" value="${payStep.EMPID}"/>
						<input type="hidden" name="PERSON_ID_${payStep.EMPID}" value="${payStep.PERSON_ID}"/>
						<input type="hidden" name="DEPTNO_${payStep.EMPID}" value="${payStep.DEPTNO}"/>
						<input type="hidden" name="POSITION_NO_${payStep.EMPID}" value="${payStep.POSITION_NO}"/>
						<input type="hidden" name="DUTY_NO_${payStep.EMPID}" value="${payStep.DUTY_NO}"/>
						<input type="hidden" name="POST_GRADE_NO_${payStep.EMPID}" value="${payStep.POST_GRADE_NO}"/>
						<input type="hidden" name="PAY_STEP_NOCheck_${payStep.EMPID}" value="${payStep.PAY_STEP_NO}"/>
						<input type="hidden" name="OLD_POST_GRADE_NOCheck_${payStep.EMPID}" value="${payStep.OLD_POST_GRADE_NO}"/>
					</td>
					<td>${payStep.EMPID}</td>
					<td>${payStep.LOCAL_NAME}</td>
					<td>
						${payStep.DEPT_NAME}
					</td>
					<td>
						${payStep.POST_GRADE_NAME}
					</td>
					<td>						
						${payStep.POST_NAME}
					</td>
					<td>
						${payStep.POSITION_NAME}
					</td>
					<td>
						<select name="PAY_STEP_NO_${payStep.EMPID}">
							<c:forEach items="${haofengList}" var="paySteps">
								<option <c:if test="${paySteps.CONTENT == payStep.PAY_STEP}">selected</c:if> value="${paySteps.PAY_STEP_NO}">
									${paySteps.CONTENT}
								</option>
							</c:forEach>
						</select>
					</td>
					<td>
						<select name="OLD_POST_GRADE_NO_${payStep.EMPID}">
							<c:forEach items="${oldGradeNoList}" var="oldGradeNo">
								<option <c:if test="${oldGradeNo.OLD_POST_GRADE_NAME == payStep.OLD_POST_GRADE_NAME}">selected</c:if> value="${oldGradeNo.OLD_POST_GRADE_NO}">
									${oldGradeNo.OLD_POST_GRADE_NAME}
								</option>
							</c:forEach>
						</select>
					</td>
					<td>
						<input type="text" name="START_DATE_${payStep.EMPID}" size="10" class="date"/>
					</td>
					<td>
						<ait:SelectSyCodeByCpnyID name="ADJUST_REASON_${payStep.EMPID}" parentNo="21946" cnpyID="${defaultCpny}"/>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>			
</form>
			<c:set value="/hrm/transferOrder/viewPayStep" var="pageUrl"/>
			<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>
