<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
//离职保存
function saveResignReq(flag) {	
	$("#REQTYPE").val(flag);
	$form = $("#addResignForm");	
	$form.submit();
}
function validateCallbackAddResign(form,callback) {	
	var $form = $("#addResignForm");	
	if (!$form.valid()) {
		return false;
	}
	var validFlag = false ;
	$form.find(":checkbox[id='resignCKB']").each(function(index, checkBoxObj){	    
	    if(checkBoxObj.checked){
	      validFlag = true ;      
	    }	    
	});
	if(!validFlag){
	 	//请选择信息再进行保存操作
		alertMsg.error('<spring:message code="hr.alert.message.viewPersonalInfo.checkBoxForChecked"/>');
		return false;
	}
	$form.find(":checkbox[id='resignCKB']").each(function(index, checkBoxObj){
	    if(checkBoxObj.checked){
	      validFlag = true ;
	      var empid = $(checkBoxObj).val() ;	      
		  if($form.find("[name='RESIGN_DATE_" + empid + "']").val()==''){
				alertMsg.error('离职日期不能为空！');
				$form.find("[name='RESIGN_DATE_" + empid + "']").focus();
				validFlag=false;
		  }
		  if($form.find("[name='RESIGN_TYPE_CODE_" + empid + "']").val()==null
		   || $form.find("[name='RESIGN_TYPE_CODE_" + empid + "']").val()==""){
				alertMsg.error('离职类型不能为空！');
				$form.find("[name='RESIGN_TYPE_CODE_" + empid + "']").focus();
				validFlag=false;
		  }
		  if($form.find("[name='RESIGN_REASON_" + empid + "']").val()==null
			|| $form.find("[name='RESIGN_REASON_" + empid + "']").val()==""){
			    alertMsg.error('离职原因不能为空！');
				$form.find("[name='RESIGN_REASON_" + empid + "']").focus();
				validFlag=false;
		  }
	    }
	});
	
	if(validFlag){		
		//确定要提交吗？
		if (confirm ('<spring:message code="hr.viewEvaluate.title.COMMIT_CONFIRM"/>')){				
		  	$.ajax({
				type: form.method || 'POST',
				url:$form.attr("action"),
				data:$form.serializeArray(),
				dataType:"json",
				cache: false,
				success: function(result) {
					if (result.statusCode == 200) {
						$.pdialog.closeCurrent();
						navTabSearch("searchResign");
					}
					alertMsg.info(result.message);
				},
				error: DWZ.ajaxError
			});				
			return false;
		}
	}	
	return false ;
}
</script>
<div class="pageHeader">
	<form method="post" 
	action="/hrm/transferOrder/viewResignAddList" 
	onsubmit="return dwzSearch(this,'dialog')" 
	rel="pagerForm">
		<input type="hidden" name="pageNum" value="${pageNum}" />
		<input type="hidden" name="numPerPage" value="${numPerPage}" />
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>法人:
				</td>
				<td>				
					<input type="text" id="seach_CPNY_ID" name="seach_CPNY_ID" value="${interCpnyID}" readonly/>
			    </td>
				<td>
					<spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/>:
					<!-- 部门： -->
				</td>
				<td>
					<c:if test="${searchMap.authority eq '1'}">
					<ait:deptList name="seach_DEPTMENTNO" cpnyId="${searchMap.defaultCpny}" limit="super" id="viewResignAdd_seachDept"/>
					<ait:deptTreeIcon name="seach_DEPTMENTNO" cpnyId="${searchMap.defaultCpny}" limit="super" id="viewResignAdd_seachDept" selected="${searchMap.DEPTMENTNO}"/>
					</c:if>
					<c:if test="${searchMap.authority ne '1'}">
					<ait:deptList name="seach_DEPTMENTNO" cpnyId="${searchMap.defaultCpny}" limit="hr" id="viewResignAdd_seachDept"/>
					<ait:deptTreeIcon name="seach_DEPTMENTNO" cpnyId="${searchMap.defaultCpny}" limit="hr" id="viewResignAdd_seachDept" selected="${searchMap.DEPTMENTNO}"/>
					</c:if>
				</td>
				<td>
					<spring:message code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME"/>:
					<!--社号/姓名：-->
				</td>
				<td>
					<input id="seach_EMPID" name="seach_EMPID" 
						type="text" value="${searchMap.EMPID}"
						onkeyup="this.value=this.value.toLocaleUpperCase().replace(/(^\s*)|(\s*$)/g, '')" 
						/>					
				</td>
			</tr>
		</table>
		<div class="subBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="submit"><!-- 查询 -->
							<spring:message code="button.search"/>
							</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</div>
	</form>
</div>
<div class="pageContent">
	<form style="margin:0px;padding:0px;" id="addResignForm" name="addResignForm" 
	onsubmit="return validateCallbackAddResign(this,navTabAjaxDone);" 
	action="/hrm/transferOrder/saveResignation?SAVETYPE=ADD" 
	method="post" 
	class="pageForm required-validate">
	<div class="formBar">
			<ul>
				<!--li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="button" onclick="saveResignReq('DRAFT')">
								暂存
							</button>
						</div>
					</div>
				</li-->
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="button" onclick="saveResignReq('SAVE')">
								<spring:message code="hr.viewUpgrade.title.SAVE"/>
								<!--保存-->
							</button>
						</div>
					</div>
				</li>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" id="btnClose" name="btnClose" class="close">
							<spring:message code="public.title.cancle"/><!--取消--></button>
						</div>
					</div>
				</li>
		</ul>
	</div>
	<table class="table" width="140%" layoutH="170" targetType="dialog" >
		<thead>
			<tr >
				<th width="2%">
					<input type="checkbox" class="checkboxCtrl" group="resignCKB" />
				</th>
				<th width="7%">
					<spring:message code="hr.viewPersonalInfo.title.EMPID"/>
					<!--社号-->
				</th>
				<th width="7%">
					<spring:message code="hr.viewPersonalInfo.title.LOCAL_NAME"/>
					<!--姓名-->
				</th>
				<th width="10%">
					<spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/>
					<!--部门-->
				</th>
				<th width="10%">
					人员类型
				</th>
				<th width="9%">
					职责
				</th>
				<th width="9%">
					<spring:message code="hr.viewPersonalInfo.title.DATE_LEFT"/>
					<font color="red">*</font>
					<!--离职日期-->
				</th>
				<th width="11%">
					<spring:message code="hr.viewPromote.title.RESIGN_TYPE_NAME"/>
					<font color="red">*</font>
					<!--离职类型-->
				</th>
				<th width="13%">
					<spring:message code="hr.viewPromote.title.RESIGN_REASON"/>
					<font color="red">*</font>
					<!--离职原因-->
				</th>
				<th width="6%" >
					<spring:message code="hr.viewResign.title.BLACKYN"/>
					<!--能否再入职-->
				</th>
				<th width="8%" >
					<spring:message code="hr.viewPromote.title.REMARK"/>
					<!--备注-->
				</th>
				<th width="8%" >
					<spring:message code="hr.viewResign.title.BLACKREMARK"/>
					<!--黑名单理由-->
				</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${resignEmpList}" var="resignEmp">
				<tr target="sid" rel="${resignEmp.EMPID}">
					<td>
						<input type="checkbox" id="resignCKB" name="resignCKB" value="${resignEmp.EMPID}" />
						<input type="hidden" name="PERSON_ID_${resignEmp.EMPID}" value="${resignEmp.PERSON_ID}"/>
						<input type="hidden" name="DEPTNO_${resignEmp.EMPID}" value="${resignEmp.DEPTNO}"/>
						<input type="hidden" name="EMP_OFFICE_${resignEmp.EMPID}" value="${resignEmp.EMP_OFFICE}"/>
						<input type="hidden" name="STATUS_CODE_${resignEmp.EMPID}" value="${resignEmp.STATUS_CODE}"/>
						<input type="hidden" name="REQTYPE" id="REQTYPE" value="" />
					</td>					
					<td class='td_center'>
						${resignEmp.EMPID}
					</td>					
					<td class='td_center'>
						${resignEmp.LOCAL_NAME}
					</td>					
					<td>
						${resignEmp.DEPT_NAME}
					</td>					
					<td>
						${resignEmp.EMP_TYPE_NAME}
					</td>					
					<td class='td_center'>
						${resignEmp.POSITION_NAME}
					</td>					
					<td class='td_center'>
						<input type="text" id="RESIGN_DATE_${resignEmp.EMPID}" name="RESIGN_DATE_${resignEmp.EMPID}" size="10" class="date" readonly="true"/>
						<a class="inputDateButton"><spring:message code="public.title.choose" /><!-- 选择 --></a>
					</td>						
					<td>
						<ait:SelectSyCodeByCpnyID name="RESIGN_TYPE_CODE_${resignEmp.EMPID}" 
							id="RESIGN_TYPE_CODE_${resignEmp.EMPID}"
							parentNo="643"
							selected="${resignEmp.RESIGN_TYPE_CODE}" cnpyID="${searchMap.defaultCpny}"/>
					</td>
					<td>
						<ait:SelectSyCodeByCpnyID name="RESIGN_REASON_${resignEmp.EMPID}" 
						id="RESIGN_REASON_${resignEmp.EMPID}" 
						parentNo="4265" cnpyID="${searchMap.defaultCpny}"/>
					</td>					
					<td class='td_center'>						
						<ait:SelectSyCodeByCpnyID name="BLACKLIST_YN_${resignEmp.EMPID}" 
							id="BLACKLIST_YN_${resignEmp.EMPID}"
							parentNo="14892"
							selected="${resignEmp.BLACKLIST_YN}" cnpyID="${searchMap.defaultCpny}"/>
					</td>					
					<td>
						<input type="text" id="REMARK_${resignEmp.EMPID}" name="REMARK_${resignEmp.EMPID}" size="20"  maxlength="60"/>
					</td>					
					<td  class='td_center'>
						<input type="text" id="BLACKLIST_REASON_${resignEmp.EMPID}" name="BLACKLIST_REASON_${resignEmp.EMPID}" size="20"  maxlength="60"/>
					</td>					
				</tr>
			</c:forEach>	
		</tbody>
	</table>
	</form>
	<form id="pagerForm" method="post" action="/hrm/transferOrder/viewResignAddList">
	<div class="panelBar">
		<div class="pages">
			<span><!-- 显示 --><spring:message code="public.title.view"/></span>
				<select class="combox" name="numPerPage" onchange="dialogPageBreak({targetType:'dialog', numPerPage:this.value})">
					<option value="10"  <c:if test="${numPerPage == 10 }" >selected</c:if> >10</option>
					<option value="20"  <c:if test="${numPerPage == 20 }" >selected</c:if> >20</option>
					<option value="30"  <c:if test="${numPerPage == 30 }" >selected</c:if> >30</option>
				</select>
			<span><!-- 条 --><spring:message code="public.title.tiao"/>，<!-- 共 --><spring:message code="public.title.gong"/>
			${totalCount}<!-- 条 --><spring:message code="public.title.tiao"/></span>
		</div>
		<div class="pagination" targetType="dialog" totalCount="${totalCount}" numPerPage="${numPerPage}" currentPage="${pageNum}"></div>
	</div>
	</form>
</div>