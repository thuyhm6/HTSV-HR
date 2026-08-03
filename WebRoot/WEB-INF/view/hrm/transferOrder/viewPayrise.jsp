<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function validateCallbackPayrise(form,callback) {	
	var $form = $("#payrise");
	
	if (!$form.valid()) {
		return false;
	}

	var checked = false ;

	$form.find(":checkbox[id='payrise']").each(function(index, checkBoxObj){
	    
	    if(checkBoxObj.checked){
	      checked = true ;      
	    }
	    
	  });
	 if(!checked){
		 	//请选择信息再进行保存操作
			alertMsg.error('<spring:message code="hr.alert.message.viewPersonalInfo.checkBoxForChecked"/>');
			return false;
		 }
	$form.find(":checkbox[id='payrise']").each(function(index, checkBoxObj){
		if(checkBoxObj.checked){
			checked = true ;
			
			var empid = $(checkBoxObj).val() ;
			   
			if($form.find("[name='START_DATE_" + empid + "']").val()==''){
				//alert("生效日期不能为空");
				alertMsg.error('<spring:message code="hr.alert.message.viewUpgrade.checkNotNullFunctionDate"/>');
				$form.find("[name='START_DATE_" + empid + "']").focus();
				checked=false;
				
			}
			
			if($form.find("[id='ITEM_NO_" + empid + "']").val()==''){
				//alert("工资基础项目不能为空");
				alertMsg.error('<spring:message code="hr.alert.message.viewUpgrade.checkNotNullItemNo"/>');
				$form.find("[id='ITEM_NO_" + empid + "']").focus();
				checked=false;
			}
			
			if($form.find("[name='RETURN_VALUE_" + empid + "']").val()!=''){
				if($form.find("[name='RETURN_VALUE_" + empid + "']").val()<=0){
					//alert("调整薪资必须大于0");
					alertMsg.error('<spring:message code="hr.alert.message.viewPayrise.checkReturnValueGreaterThanZero"/>');
					$form.find("[name='RETURN_VALUE_" + empid + "']").focus();
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
	<form id="searchPayrise" onsubmit="return navTabSearchPayrise(this);" action="/hrm/transferOrder/viewPayrise" method="post" rel="pagerForm">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					<spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/>：
					<!-- 部门： -->
					<ait:deptTree name="seach_DEPTMENTNO" limit="hr" selected="${DEPTMENTNO}"/>
				</td>
				<td>
					<spring:message code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME"/>：
					<!--社号/姓名：-->
					<input type="text" name="seach_key" value="${key}" />
				</td>
				<td>
					<span class="span_left"><spring:message code="hr.viewContractInfoForSearch.title.JOIN_COMPANY_DATE"/>：<!--入司日期：-->
					</span>
					<input type="text" id="seach_JOIN_COMPANY_START_DATE" name="seach_JOIN_COMPANY_START_DATE" value="${JOIN_COMPANY_START_DATE}" class="date"/><span class="span_left">~</span>
					<input type="text" id="seach_JOIN_COMPANY_END_DATE" name="seach_JOIN_COMPANY_END_DATE" value="${JOIN_COMPANY_END_DATE}" class="date"/>
				</td>
				<a id="searchEmp" name="searchEmp" target="dialog" mask="true"  href="#" lookupGroup="person" width="950" height="600"></a>
				<input type="hidden" id="eids" name="eids" value="${eids}">
				<td>
				    <spring:message code="hr.viewContractByInsert.title.ifHaveResignPerson"/>：<!--是否包含离职人员：-->
				    <select name='seach_ifHaveResign' id='seach_ifHaveResign'>
						<option value='Y'><spring:message code="sys.affirm.title.yes"/><!--是--></option>
						<option value='N' selected="selected"><spring:message code="sys.affirm.title.no"/><!--否--></option>
					</select>
				</td>
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
	<form style="margin:0px;padding:0px;" id="payrise" onsubmit="return validateCallbackPayrise(this,navTabAjaxDone);" action="/hrm/transferOrder/savePayrise" method="post"  class="pageForm required-validate">
	<div class="formBar">
			<tr>	
			<label style="float: left">
				<input type="checkbox" class="checkboxCtrl" group="payrise" />
				<spring:message code="hr.viewUpgrade.title.CHECKALL"/>
				<!--全选-->
			</label>
			<ul>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" class="checkboxCtrl" group="payrise"
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
				<th><!--选择-->
					<spring:message code="public.title.choose"/>
				</th>
				<th><!--社号-->
					<spring:message code="hr.viewPersonalInfo.title.EMPID"/>
				</th>
				<th><!--姓名-->
					<spring:message code="hr.viewPersonalInfo.title.LOCAL_NAME"/>
				</th>
				<th><!--部门-->
					<spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/>
				</th>
				<th><!--入司日期-->
					<spring:message code="hr.viewContractInfoForSearch.title.JOIN_COMPANY_DATE" />
				</th>
				<th><!--职级(GGS)-->
					<spring:message code="hr.viewPersonalInfo.title.POST_GRADE_NAME"/>
				</th>
				<th><!--职级名称(职务)-->
					<spring:message code="hr.viewPersonalInfo.title.POST_NAME"/>
				</th>
				<th><!--职(岗)位-->
					<spring:message code="hr.viewPersonalInfo.title.POSITION_NAME"/>
				</th>
				<th><!--工资基础项目-->
					<spring:message code="hr.viewPayrise.title.PABASICITEM"/>
				</th>
				<th><!--当前薪资-->
					<spring:message code="hr.viewPayrise.title.NOWPAY"/>
				</th>
				<th><!--调整后薪资-->
					<spring:message code="hr.viewPayrise.title.RETURNVALUE1"/>
				</th>
				<th><!--生效日期-->
					<spring:message code="hr.viewPromote.title.EFFECTIVE_DATE"/>
				</th>
				<th><!--调整是由-->
					<spring:message code="hr.viewPayrise.title.ADJUST_REASON"/>
				</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${payriseList}" var="payrise">
				<tr target="sid" rel="${payrise.EMPID}">
					<td>
						<input type="checkbox" id="payrise" name="payrise" value="${payrise.EMPID}" />
						<input type="hidden" name="PERSON_ID_${payrise.EMPID}" value="${payrise.PERSON_ID}"/>
						<input type="hidden" name="DEPTNO_${payrise.EMPID}" value="${payrise.DEPTNO}"/>
						<input type="hidden" name="POSITION_NO_${payrise.EMPID}" value="${payrise.POSITION_NO}"/>
						<input type="hidden" name="DUTY_NO_${payrise.EMPID}" value="${payrise.DUTY_NO}"/>
						<input type="hidden" name="POST_GRADE_NO_${payrise.EMPID}" value="${payrise.POST_GRADE_NO}"/>
					</td>
					<td>${payrise.EMPID}</td>
					<td>${payrise.LOCAL_NAME}</td>
					<td>${payrise.DEPT_NAME}</td>
					<td>${payrise.JOIN_COMPANY_DATE}</td>
					<td>${payrise.POST_GRADE_NAME}</td>
					<td>${payrise.POST_NAME}</td>
					<td>${payrise.POSITION_NAME}</td>
					<td>
						<select class="required combox" name="ITEM_NO_${payrise.EMPID}" onChange="getReturnValue(this.value,${payrise.EMPID},${payrise.PERSON_ID});" id="ITEM_NO_${payrise.EMPID}">
							<option value=""><spring:message code="hr.viewPersonalInfo.title.ADDED_BY_KELI"/><!-- 请选择 --></option>
							<c:forEach items="${paBasicItemList}" var="paBasicItem">
								<option value="${paBasicItem.ITEM_NO}">${paBasicItem.ITEM_NAME}</option>
							</c:forEach>
						</select>
					</td>
					<td>
						<input id="nowPay_${payrise.EMPID}" name="nowPay_${payrise.EMPID}" type="text" size="10" readonly="true">
					</td>
					<td>
						<input name="RETURN_VALUE_${payrise.EMPID}" size="10" class="number" maxlength="7">
					</td>
					<td>
						<input type="text" name="START_DATE_${payrise.EMPID}" size="10" class="date"/>
					</td>
					<td>
						<ait:SelectSyCodeByCpnyID name="ADJUST_REASON_${payrise.EMPID}" parentNo="21946" cnpyID="${defaultCpny}"/>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>			
</form>
	<c:set value="/hrm/transferOrder/viewPayrise" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>
