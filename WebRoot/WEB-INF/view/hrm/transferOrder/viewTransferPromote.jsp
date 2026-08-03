<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function validateCallbacktransferPromote(form,callback) {	
	var $form = $("#transferPromote");
	
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
	      
		  if($form.find("[name='START_DATE_" + empid + "']").val()==''){
				//alert("生效日期不能为空");
				alertMsg.error('<spring:message code="hr.alert.message.viewUpgrade.checkNotNullFunctionDate"/>');
				$form.find("[name='START_DATE_" + empid + "']").focus();
				checked=false;
			}else if($form.find("[name='PROMOTE_TYPE_" + empid + "']").val()==''){
				//alert("类型不能为空");
				alertMsg.error('<spring:message code="hr.alert.message.viewTransferPromote.checkNotNullType"/>');
				$form.find("[name='PROMOTE_TYPE_" + empid + "']").focus();
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
function navTabSearchTransferPromote(form, navTabId){
	var $form = $("#searchTransferPromote");
		
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
function setWorkArea_transferPromote(treeNode, name){

	var index = name.slice((name.lastIndexOf('_') + 1)) ;
	
	var $form = $("#transferPromote");
	
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
    var empid_transferPromote = "" ;

	var setting_transferPromote = {
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
			onClick: onClick_transferPromote
		}
	};
	var zNodes_transferPromote = ${deptJson} ;
	
	function onClick_transferPromote(event, treeId, treeNode) {

		var $form = $("#transferPromote");
	    
	    var temp = $form.find("[name='deptName_"+empid_transferPromote+"']").val();
	    
		setWorkArea_transferPromote(treeNode, empid_transferPromote) ;
		
		var deptName = $("#deptName_" + empid_transferPromote).val();
		
		$form.find("[name='deptName_"+empid_transferPromote+"']").attr("value", treeNode.DEPTNAME);
		
		$form.find("[name='DEPTNO_"+empid_transferPromote+"']").attr("value", treeNode.DEPTNO);
		
	}
	function showTree_transferPromote(nameObjId, noObjId, key) {

		if(key == null || key.length == 0){
			return ;
		}
		empid_transferPromote = key ;
		
		var $treeObj_transferPromote = $.fn.zTree.getZTreeObj("deptTree_transferPromote") ;
		var $nodeObj_transferPromote = $treeObj_transferPromote.getNodeByParam('DEPTNO', $("#" + noObjId).val()) ;
		if($nodeObj_transferPromote != null ){
			$treeObj_transferPromote.selectNode($nodeObj_transferPromote) ;
			onClick_transferPromote(null, null, $nodeObj_transferPromote) ;
		}
		
		var $form = $("#transferPromote");
		var deptNameObj = $form.find("[name='"+nameObjId+"']");
		var deptNameOffset = $form.find("[name='"+nameObjId+"']").offset() ;
		
		$("#deptContent_transferPromote").css({left:(deptNameOffset.left - 186) + "px", top:(deptNameOffset.top + deptNameObj.outerHeight() - 104) + "px"}).slideDown("fast") ;
		
		$("body").bind("mousedown", onBodyDown_transferPromote);
	}
	function hideMenu_transferPromote() {
		$("#deptContent_transferPromote").fadeOut("fast");
		$("body").unbind("mousedown", onBodyDown_transferPromote);
	}
	function onBodyDown_transferPromote(event) {
		if (! (event.target.id == "menuBtn" || event.target.id == "deptContent_transferPromote" || $(event.target).parents("#deptContent_transferPromote").length > 0)) {
			hideMenu_transferPromote();
		}
	}
	$(document).ready(function() {
		$.fn.zTree.init($("#deptTree_transferPromote"), setting_transferPromote, zNodes_transferPromote);
	});
</SCRIPT>
<div style="padding:5px;">
<div class="pageHeader">
	<form id="searchTransferPromote" onsubmit="return navTabSearchTransferPromote(this);" action="/hrm/transferOrder/viewTransferPromote" method="post" rel="pagerForm">
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
	<form style="margin:0px;padding:0px;" id="transferPromote" onsubmit="return validateCallbacktransferPromote(this,navTabAjaxDone);" action="/hrm/transferOrder/saveTransferPromote" method="post"  class="pageForm required-validate">
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
	<table width="100%" class="tablea" layoutH="140">
		<thead>
			<tr >
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
					<spring:message code="hr.viewPersonalInfo.title.WORK_AREA_NAME"/>
					<!--工作地-->
				</th>
				<th width="10%">
					<spring:message code="hr.viewPersonalInfo.title.POSITION_NAME"/>
					<!--职(岗)位-->
				</th>
				<th width="10%">
					<spring:message code="hr.viewPersonalInfo.title.POST_GRADE_NAME"/>
					<!--职级(GGS)-->
				</th>
				<th width="10%">
					<spring:message code="hr.viewPersonalInfo.title.DUTY_NAME"/>
					<!--职责-->
				</th>
				<th width="10%">
					<spring:message code="hr.viewPersonalInfo.title.POST_NAME"/>
					<!--职级名称(职务)-->
				</th>
				<th width="10%">
					<spring:message code="hr.viewPromote.title.EFFECTIVE_DATE"/>
					<!--生效日期-->
				</th>
				<th>
					<spring:message code="hr.viewTransferPromote.title.TYPE"/>
					<!--类型-->
				</th>
				
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${transferPromoteList}" var="transferPromote">
				<tr target="sid" rel="${transferPromote.EMPID}">
					<input type="hidden" name="PERSON_ID_${transferPromote.EMPID}" value="${transferPromote.PERSON_ID}"/>
					<td>
						<input type="checkbox" id="d1" name="d1" value="${transferPromote.EMPID}" />
					</td>
					<td>${transferPromote.EMPID}</td>
					<td>${transferPromote.LOCAL_NAME}</td>
					<td>
						<input id="deptName_${transferPromote.EMPID}" name="deptName_${transferPromote.EMPID}" type="text" value="${transferPromote.DEPT_NAME}" onclick="showTree_transferPromote('deptName_${transferPromote.EMPID}',  'deptNo_${transferPromote.EMPID}', '${transferPromote.EMPID}')" readonly />
						<input id="deptNo_${transferPromote.EMPID}" name="DEPTNO_${transferPromote.EMPID}" value="${transferPromote.DEPTNO}" type="hidden" />
					</td>
					<td>
						<select class="combox" name="WORK_AREA_${transferPromote.EMPID}" id="combox_WORK_AREA_${transferPromote.EMPID}">
							<option value=""><spring:message code="hr.viewPersonalInfo.title.ADDED_BY_KELI"/><!-- 请选择 --></option>
							<c:forEach items="${workAreaList}" var="workArea">
								<option value="${workArea.WORK_AREA}" <c:if test="${workArea.WORK_AREA eq transferPromote.WORK_AREA}">selected</c:if>>${workArea.WORKAREA_NAME}</option>
							</c:forEach>
						</select>
					</td>	
					<td>
						<select name="POSITION_NO_${transferPromote.EMPID}" id="POSITION_NO">
							<option value=""><spring:message code="hr.viewPersonalInfo.title.ADDED_BY_KELI"/><!-- 请选择 --></option>
							<c:forEach items="${positionList}" var="position">
								<option value="${position.POSITION_NO}" <c:if test="${position.POSITION_NO eq transferPromote.POSITION_NO}">selected</c:if>>${position.POSITION_NAME}</option>
							</c:forEach>
						</select>
					</td>
					<td>
						<select class="combox" name="POST_GRADE_NO_${transferPromote.EMPID}" ref="combox_POST_NO" refUrl="/hrm/transferOrder/getPostNoByPostGradeNo?POST_GRADE_NO={value}" id="POST_GRADE_NO" ref2="combox_DUTY_NO" refUrl2="/hrm/transferOrder/getDutyNoByPostGradeNo?POST_GRADE_NO={value}">
							<option value=""><spring:message code="hr.viewPersonalInfo.title.ADDED_BY_KELI"/><!-- 请选择 --></option>
							<c:forEach items="${postGradeList}" var="postGrade">
								<option value="${postGrade.POST_GRADE_NO}" <c:if test="${postGrade.POST_GRADE_NO eq transferPromote.POST_GRADE_NO}">selected</c:if>>${postGrade.POST_GRADE_NAME}</option>
							</c:forEach>
						</select>
					</td>	
					<td>
						<select name="DUTY_NO_${transferPromote.EMPID}" id="combox_DUTY_NO">
							<option value=""><spring:message code="hr.viewPersonalInfo.title.ADDED_BY_KELI"/><!-- 请选择 --></option>
							<c:forEach items="${dutyList}" var="duty">
								<option value="${duty.DUTY_NO}" <c:if test="${duty.DUTY_NO eq transferPromote.DUTY_NO}">selected</c:if>>${duty.DUTY_NAME}</option>
							</c:forEach>
						</select>
					</td>
					<td>						
						<select class="combox" name="POST_NO_${transferPromote.EMPID}" id="combox_POST_NO" readonly="readonly">
								<option value="${transferPromote.POST_NO}">${transferPromote.POST_NAME}</option>
						</select>
					</td>
					<td>
						<input type="text" id="START_DATE" name="START_DATE_${transferPromote.EMPID}" size="10" class="date"/>
					</td>
					<td>
						<ait:SelectSyCodeByCpnyID name="PROMOTE_TYPE_${transferPromote.EMPID}" parentNo="1363" cnpyID="${defaultCpny}" limit="all"/>
					</td>						
				</tr> 
			</c:forEach>
		</tbody>
	</table>			 
</form>
			<c:set value="/hrm/transferOrder/viewTransferPromote" var="pageUrl"/>
			<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>
<div id="deptContent_transferPromote" style="display:none; position: absolute;z-index:999;overflow:auto;height:350px; border:solid 1px #CCC; line-height:21px; background:#FFF;" >
	<ul id="deptTree_transferPromote" class="ztree" style="margin-top:0; width:300px;"></ul>
</div>
