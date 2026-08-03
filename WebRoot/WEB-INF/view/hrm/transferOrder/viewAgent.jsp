<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function validateCallbackAgent(form,callback) {	
	
	var $form = $("#agent");
	
	if (!$form.valid()) {
		return false;
	}
	
	var checked = false ;

	$form.find(":checkbox[id='agentGp']").each(function(index, checkBoxObj){
	    
	    if(checkBoxObj.checked){
	    	checked = true ;      
	    }
	    
	 });
	  
	if(!checked){
	 	//请选择信息再进行保存操作
		alertMsg.error('<spring:message code="hr.alert.message.viewPersonalInfo.checkBoxForChecked"/>');
		return false;
	}
	
	$form.find(":checkbox[id='agentGp']").each(function(index, checkBoxObj){
	    if(checkBoxObj.checked){
	        checked = true ;
	        var empid = $(checkBoxObj).val();
	      	if($form.find("[name='DEPTNO_" + empid + "']").val()==''){
				//alert("部门不能为空");
				alertMsg.error('<spring:message code="hr.alert.message.viewHire.checkNotNullDeptNo"/>');
				$form.find("[name='DEPTNO_" + empid + "']").focus();
				checked=false;
				return false;
			}else if($form.find("[name='AGENT_POSITION_NO_" + empid + "']").val()==''){
				//alert("职(岗)位不能为空");
				alertMsg.error('<spring:message code="hr.alert.message.viewHire.checkNotNullPositionNo"/>');
				$form.find("[name='AGENT_POSITION_NO_" + empid + "']").focus();
				checked=false;
				return false;
			}else if($form.find("select[name='AGENT_DUTY_NO_" + empid + "']").val()==''){
				//alert("职责不能为空");
				alertMsg.error('<spring:message code="hr.alert.message.viewUpgrade.checkNotNullDutyNo"/>');
				$form.find("select[name='AGENT_DUTY_NO_" + empid + "']").focus();
				checked=false;
				return false;
			}else if($form.find("select[name='AGENT_WORK_AREA_" + empid + "']").val()==''){
				//alert("工作地不能为空");
				alertMsg.error('<spring:message code="hr.alert.message.viewHire.checkNotNullWorkArea"/>');
				$form.find("select[name='AGENT_WORK_AREA_" + empid + "']").focus();
				checked=false;
				return false;
			}else if($form.find("select[name='AGENT_POST_GRADE_NO_" + empid + "']").val()==''){
				//alert("代理职级不能为空");
				alertMsg.error('<spring:message code="hr.alert.message.viewAgent.checkNotNullAgentPostGradeNo"/>');
				$form.find("select[name='AGENT_POST_GRADE_NO_" + empid + "']").focus();
				checked=false;
				return false;
			}else if($form.find("[name='START_DATE_" + empid + "']").val()==''){
				//alert("生效日期不能为空");
				alertMsg.error('<spring:message code="hr.alert.message.viewUpgrade.checkNotNullFunctionDate"/>');
				$form.find("[name='START_DATE_" + empid + "']").focus();
				checked=false;
				return false;
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
function navTabSearchUpAgent(form, navTabId){
	var $form = $("#searchAgent");
		
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
function setWorkArea_agent(treeNode, name){

	var index = name.slice((name.lastIndexOf('_') + 1)) ;
	
	var $form = $("#agent");
	
	$form.find(":checkbox[value='" + index + "']").each(function(i, checkBoxObj){
			
			var deptDistinguishNo = treeNode.DEPT_DISTINGUISH_NO ;
			
			if(deptDistinguishNo != null && deptDistinguishNo.length > 0){
				var cid = $('#combox_WORK_AREA_' + index).attr("cid") ;

				var selectObj = $('#op_combox_' + cid).find("a[value='" + deptDistinguishNo + "']") ;
				if(selectObj != null && selectObj.size() > 0){
					selectObj.click() ;
				}
			}
	  });
	
}
</script>

<SCRIPT type='text/javascript'>
    var empid_agent = "" ;

	var setting_agent = {
		view: {
			dblClickExpand: false,
			showLine: true,
			selectedMulti: false,
			expandSpeed: "fast"
		},
		data: {
			key: {
				children: "children",
				name: "DEPTNAME",
				title: ""
			},
			simpleData: {
				enable: true,
				idKey: "DEPTNO",
				pIdKey: "PARENT_DEPT_NO",
				rootPId: ""
			}
		},
		callback: {
			onClick: onClick_agent
		}
	};
	var zNodes_agent = ${deptJson} ;
	
	function onClick_agent(event, treeId, treeNode) {
	 
	    var $form = $("#agent");
	    
	    var temp = $form.find("[name='deptName_"+empid_agent+"']").val();

		setWorkArea_agent(treeNode, empid_agent) ;
		
		var deptName = $("#deptName_" + empid_agent).val();

		$form.find("[name='deptName_"+empid_agent+"']").attr("value", treeNode.DEPTNAME);
		
		$form.find("[name='DEPTNO_"+empid_agent+"']").attr("value", treeNode.DEPTNO);
		
	}
	function showTree_agent(nameObjId, noObjId, key) {

		if(key == null || key.length == 0){
			return ;
		}
		
		empid_agent = key ;
		
		var $treeObj_agent = $.fn.zTree.getZTreeObj("onClick_agent") ;
		var $nodeObj_agent = $treeObj_agent.getNodeByParam('DEPTNO', $("#" + noObjId).val()) ;
		if($nodeObj_agent != null ){
			$treeObj_agent.selectNode($nodeObj_agent) ;
			onClick_agent(null, null, $nodeObj_agent) ;
		}

		var $form = $("#agent");
		var deptNameObj = $form.find("[name='"+nameObjId+"']");
		var deptNameOffset = $form.find("[name='"+nameObjId+"']").offset() ;
		
		$("#deptContent_agent").css({left:(deptNameOffset.left - 186) + "px", top:(deptNameOffset.top + deptNameObj.outerHeight() - 104) + "px"}).slideDown("fast") ;
		
		$("body").bind("mousedown", onBodyDown_agent);
	}
	function hideMenu_agent() {
		$("#deptContent_agent").fadeOut("fast");
		$("body").unbind("mousedown", onBodyDown_agent);
	}
	function onBodyDown_agent(event) {
		if (! (event.target.id == "menuBtn" || event.target.id == "deptContent_agent" || $(event.target).parents("#deptContent_agent").length > 0)) {
			hideMenu_agent();
		}
	}
	$(document).ready(function() {
		$.fn.zTree.init($("#onClick_agent"), setting_agent, zNodes_agent);
	});
</SCRIPT>

<div style="padding:5px;">
<div class="pageHeader">
	<form id="searchAgent" onsubmit="return navTabSearchUpAgent(this);" action="/hrm/transferOrder/viewAgent" method="post" rel="pagerForm">
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
					<!-- 类型： -->
					<ait:SelectSyCodeByCpnyID name="seach_AGENT_TYPE" parentNo="16196" cnpyID="${defaultCpny}" selected="${AGENT_TYPE}"/>
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

	<form style="margin:0px;padding:0px;" id="agent" onsubmit="return validateCallbackAgent(this,navTabAjaxDone);" action="/hrm/transferOrder/saveTransferOrderAgent" method="post"  class="pageForm required-validate">
	<div class="formBar">
			<tr>	
			<label style="float: left">
				<input type="checkbox" class="checkboxCtrl" group="agentGp" />
				<spring:message code="hr.viewUpgrade.title.CHECKALL"/>
				<!--全选-->
			</label>
			<ul>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" class="checkboxCtrl" group="agentGp"
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
	<table width="100%" class="tablea" layoutH="140">
		<thead>
			<tr>
				<th width="5%">
					<spring:message code="public.title.choose"/>
					<!--选择-->
				</th>
				<th width="8%">
					<spring:message code="hr.viewPersonalInfo.title.EMPID"/>
					<!--社号-->
				</th>
				<th width="10%">
					<spring:message code="hr.viewPersonalInfo.title.LOCAL_NAME"/>
					<!--姓名-->
				</th>
				<th width="15%">
					<spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/>
					<!--部门-->
				</th>
				<th width="10%">
					<spring:message code="hr.viewPersonalInfo.title.POSITION_NAME"/>
					<!--职(岗)位-->
				</th>
				<th width="8%">
					<spring:message code="hr.viewPersonalInfo.title.AGENT_POST_GRADE_NAME"/>
					<!--代理职级-->
				</th>
				<th width="12%">
					<spring:message code="hr.viewAgent.title.AGENT_POST_NO"/>
					<!--代理职级名称(职务)-->
				</th>
				<th width="10%">
					<spring:message code="hr.viewPersonalInfo.title.DUTY_NAME"/>
					<!--职责-->
				</th>
				<th width="8%">
					<spring:message code="hr.viewPersonalInfo.title.WORK_AREA_NAME"/>
					<!--工作地-->
				</th>
				<th width="10%">
					<spring:message code="hr.viewPromote.title.EFFECTIVE_DATE"/>
					<!--生效日期-->
				</th>
				<th>
					<spring:message code="hr.viewAgent.title.AGENT_REMARK"/>
					<!--代理事由-->
				</th>
			</tr>
		</thead>
		<tbody>
		
			<c:forEach items="${agentList}" var="agent">
				<tr target="sid" rel="${agent.EMPID}">
					<input type="hidden" name="PERSON_ID_${agent.EMPID}" value="${agent.PERSON_ID}"/>
					<input type="hidden" name="POSITION_NO_${agent.EMPID}" value="${agent.POSITION_NO}"/>
					<input type="hidden" name="POST_GRADE_NO_${agent.EMPID}" value="${agent.POST_GRADE_NO}"/>
					<input type="hidden" name="DUTY_NO_${agent.EMPID}" value="${agent.DUTY_NO}"/>
					<input type="hidden" name="WORK_AREA_${agent.EMPID}" value="${agent.WORK_AREA}"/>
					<input type="hidden" name="OLD_DEPTNO_${agent.EMPID}" value="${agent.DEPTNO}"/>
					<input type="hidden" name="AGENT_TYPE" value="${AGENT_TYPE}"/>
						
					<td>
						<input type="checkbox" id="agentGp" name="agentGp" value="${agent.EMPID}" />
					</td>
					
					<td>${agent.EMPID}</td>
					
					<td>${agent.LOCAL_NAME}</td>
					
					<td>
						<input id="deptName_${agent.EMPID}" name="deptName_${agent.EMPID}" type="text" value="${agent.DEPT_NAME}" onclick="showTree_agent('deptName_${agent.EMPID}',  'deptNo_${agent.EMPID}', '${agent.EMPID}')" readonly />
						<input id="deptNo_${agent.EMPID}" name="DEPTNO_${agent.EMPID}" value="${agent.DEPTNO}" type="hidden" />
					</td>
					<td>
						<select name="AGENT_POSITION_NO_${agent.EMPID}" id="AGENT_POSITION_NO">
							<option value=""><spring:message code="hr.viewPersonalInfo.title.ADDED_BY_KELI"/><!-- 请选择 --></option>
							<c:forEach items="${positionList}" var="position">
								<option value="${position.POSITION_NO}" <c:if test="${position.POSITION_NO eq agent.POSITION_NO}">selected</c:if>>${position.POSITION_NAME}</option>
							</c:forEach>
						</select>
					</td>
					<td>
						<select class="combox" name="AGENT_POST_GRADE_NO_${agent.EMPID}" ref="combox_POST_NO" refUrl="/hrm/transferOrder/getPostNoByPostGradeNo?POST_GRADE_NO={value}" id="AGENT_POST_GRADE_NO" ref2="combox_AGENT_DUTY_NO" refUrl2="/hrm/transferOrder/getDutyNoByPostGradeNo?POST_GRADE_NO={value}">
							<option value=""><spring:message code="hr.viewPersonalInfo.title.ADDED_BY_KELI"/><!-- 请选择 --></option>
							<c:forEach items="${postGradeList}" var="postGrade">
								<option value="${postGrade.POST_GRADE_NO}" <c:if test="${postGrade.POST_GRADE_NO eq agent.POST_GRADE_NO}">selected</c:if>>${postGrade.POST_GRADE_NAME}</option>
							</c:forEach>
						</select>
					</td>
					<td>
						<select class="combox" name="POST_NO_${agent.EMPID}" id="combox_POST_NO" readonly="true">
								<option value="${agent.POST_NO}" >${agent.POST_NAME}</option>
						</select>
					</td>
					<td>
						<!--<ait:SelectSyCodeByCpnyID name="DUTY_NO_${agent.EMPID}" parentNo="881" cnpyID="${defaultCpny}" selected="${agent.DUTY_NO}" limit="all"/>-->
						<select name="AGENT_DUTY_NO_${agent.EMPID}" id="combox_AGENT_DUTY_NO">
							<option value=""><spring:message code="hr.viewPersonalInfo.title.ADDED_BY_KELI"/><!-- 请选择 --></option>
							<c:forEach items="${dutyList}" var="duty">
								<option value="${duty.DUTY_NO}" <c:if test="${duty.DUTY_NO eq agent.DUTY_NO}">selected</c:if>>${duty.DUTY_NAME}</option>
							</c:forEach>
						</select>
					</td>
					<td>
						<select class="combox" name="AGENT_WORK_AREA_${agent.EMPID}" id="combox_WORK_AREA_${agent.EMPID}">
						<option value=""><spring:message code="hr.viewPersonalInfo.title.ADDED_BY_KELI"/><!-- 请选择 --></option>
						<c:forEach items="${workAreaList}" var="workArea">
							<option value="${workArea.WORK_AREA}" <c:if test="${workArea.WORK_AREA eq agent.WORK_AREA}">selected</c:if>>${workArea.WORKAREA_NAME}</option>
						</c:forEach>
					</select>
					</td>	
					<td>
						<input type="text" id="START_DATE" name="START_DATE_${agent.EMPID}" size="10" class="date"/>
					</td>
					<td>
						<input type="text" id="AGENT_REASON" name="AGENT_REASON_${agent.EMPID}" >
					</td>							
				</tr>
			</c:forEach>
		</tbody>
	</table>			
</form>
			<c:set value="/hrm/transferOrder/viewAgent" var="pageUrl"/>
			<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>
<div id="deptContent_agent" style="display:none; position: absolute;z-index:999;overflow:auto;height:350px; border:solid 1px #CCC; line-height:21px; background:#FFF;" >
	<ul id="onClick_agent" class="ztree" style="margin-top:0; width:300px;"></ul>
</div>