<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script type="text/javascript">

function validateCallbackPluralityPromote(form, callback) {
	
	
	var $form = $("#pluralityPromote");

	if ('' == $form.find("[name='PLURALITY_TYPE']").val() || '1685' == $form.find("[name='PLURALITY_TYPE']").val()) {

		if (!$form.valid()) {
			return false;
		}
		var checked = false;

		$form.find(":checkbox[id='pluralityCKB']").each(function(index, checkBoxObj) {

			if (checkBoxObj.checked) {
				checked = true;
			}

		});
		if (!checked) {
			//请选择信息再进行保存操作
			alertMsg.error('<spring:message code="hr.alert.message.viewPersonalInfo.checkBoxForChecked"/>');
			return false;
		}
		$form.find(":checkbox[id='pluralityCKB']").each(function(index, checkBoxObj) {
			if (checkBoxObj.checked) {
				checked = true;

				var empid = $(checkBoxObj).val();

				if ($form.find("[name='PLU_DEPTNO_" + empid + "']").val() == '') {
					//alert("兼职部门不能为空");
					alertMsg.error('<spring:message code="hr.alert.message.viewPlurality.checkNotNullPluDeptNo"/>');
					$form.find("[name='PLU_DEPTNO_" + empid + "']").focus();
					checked = false;
				} else if ($form.find("[name='PLU_POSITION_NO_" + empid + "']").val() == '') {
					//alert("兼职职(岗)位不能为空");
					alertMsg.error('<spring:message code="hr.alert.message.viewPlurality.checkNotNullPluPositionNo"/>');
					$form.find("[name='PLU_POSITION_NO_" + empid + "']").focus();
					checked = false;
				} else if ($form.find("[name='PLU_DUTY_NO_" + empid + "']").val() == '') {
					//alert("兼职职责不能为空");
					alertMsg.error('<spring:message code="hr.alert.message.viewPlurality.checkNotNullPluDutyNo"/>');
					$form.find("[name='PLU_DUTY_NO_" + empid + "']").focus();
					checked = false;
				} else if ($form.find("[name='START_DATE_" + empid + "']").val() == '') {
					//alert("生效日期不能为空");
					alertMsg.error('<spring:message code="hr.alert.message.viewUpgrade.checkNotNullFunctionDate"/>');
					$form.find("[name='START_DATE_" + empid + "']").focus();
					checked = false;
				}

			}
		});

		if (checked) {

			//确定要提交吗？
			if (confirm ('<spring:message code="hr.viewEvaluate.title.COMMIT_CONFIRM"/>')){		
				$.ajax({
					type: form.method || 'POST',
					url: $form.attr("action"),
					data: $form.serializeArray(),
					dataType: "json",
					cache: false,
					success: callback || DWZ.ajaxDone,
					error: DWZ.ajaxError
				});

				return false;
			}
		}

		return false;
	} else {
		alert(2)
		//alert("解除兼职");
		if (!$form.valid()) {
			return false;
		}
		var checked = false;

		$form.find(":checkbox[id='pluralityCKB']").each(function(index, checkBoxObj) {

			if (checkBoxObj.checked) {
				checked = true;
			}

		});
		if (!checked) {
			//请选择信息再进行保存操作
			alertMsg.error('<spring:message code="hr.alert.message.viewPersonalInfo.checkBoxForChecked"/>');
			return false;
		}
		$form.find(":checkbox[id='pluralityCKB']").each(function(index, checkBoxObj) {
			if (checkBoxObj.checked) {
				checked = true;

				var empid = $(checkBoxObj).val();

				if ($form.find("[name='START_DATE_" + empid + "']").val() == '') {
					//alert("生效日期不能为空");
					alertMsg.error('<spring:message code="hr.alert.message.viewUpgrade.checkNotNullFunctionDate"/>');
					$form.find("[name='START_DATE_" + empid + "']").focus();
					checked = false;
				}

			}
		});

		if (checked) {

			//确定要提交吗？
			if (confirm ('<spring:message code="hr.viewEvaluate.title.COMMIT_CONFIRM"/>')){		
				$.ajax({
					type: form.method || 'POST',
					url: $form.attr("action"),
					data: $form.serializeArray(),
					dataType: "json",
					cache: false,
					success: callback || DWZ.ajaxDone,
					error: DWZ.ajaxError
				});

				return false;
			}
		}

		return false;
	}



}


function setWorkArea_plurality(treeNode, name){

	var index = name.slice((name.lastIndexOf('_') + 1)) ;
	
	var $form = $("#upGrade");
	
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
    var empid_plurality = "" ;

	var setting_plurality = {
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
			onClick: onClick_plurality
		}
	};
	var zNodes_plurality = ${deptJson} ;
	
	function onClick_plurality(event, treeId, treeNode) {

		setWorkArea_plurality(treeNode, empid_plurality) ;
		
		var deptName = $("#deptName_" + empid_plurality).val();

		$("#deptName_" + empid_plurality).attr("value", treeNode.DEPTNAME);
		$("#deptNo_" + empid_plurality).attr("value", treeNode.DEPTNO);
		
	}
	function showTree_plurality(nameObjId, noObjId, key) {

		if(key == null || key.length == 0){
			return ;
		}
		
		empid_plurality = key ;
		
		var $treeObj_plurality = $.fn.zTree.getZTreeObj("deptTree_plurality") ;
		var $nodeObj_plurality = $treeObj_plurality.getNodeByParam('DEPTNO', $("#" + noObjId).val()) ;
		if($nodeObj_plurality != null ){
			$treeObj_plurality.selectNode($nodeObj_plurality) ;
			onClick_plurality(null, null, $nodeObj_plurality) ;
		}

		var deptNameObj = $("#" + nameObjId) ;
		var deptNameOffset = $("#" + nameObjId).offset() ;
		
		$("#deptContent_plurality").css({left:(deptNameOffset.left - 186) + "px", top:(deptNameOffset.top + deptNameObj.outerHeight() - 104) + "px"}).slideDown("fast") ;
		
		//$("#deptContent_plurality").css(deptNameOffset).slideDown("fast") ;

		//$("#deptContent_plurality").slideDown("fast") ;
		
		$("body").bind("mousedown", onBodyDown_plurality);
	}
	function hideMenu_plurality() {
		$("#deptContent_plurality").fadeOut("fast");
		//$("#deptContent_plurality").offset({top:0, left:0})
		$("body").unbind("mousedown", onBodyDown_plurality);
	}
	function onBodyDown_plurality(event) {
		if (! (event.target.id == "menuBtn" || event.target.id == "deptContent_plurality" || $(event.target).parents("#deptContent_plurality").length > 0)) {
			hideMenu_plurality();
		}
	}
	$(document).ready(function() {
		$.fn.zTree.init($("#deptTree_plurality"), setting_plurality, zNodes_plurality);
	});
</SCRIPT>


<div style="padding:5px;">
<div class="pageHeader">
	<form method="post" action="/hrm/transferOrder/viewPlurality" onsubmit="return navTabSearch(this)" rel="pagerForm">
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
					<ait:SelectSyCodeByCpnyID name="seach_PLURALITY_TYPE" parentNo="1361" cnpyID="${defaultCpny}" selected="${PLURALITY_TYPE}"/>
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
	<form style="margin:0px;padding:0px;" id="pluralityPromote" onsubmit="return validateCallbackPluralityPromote(this, navTabAjaxDone);" action="/hrm/transferOrder/savePlurality" method="post" class="pageForm required-validate" >
	<div class="formBar">
			<tr>	
			<label style="float: left">
				<input type="checkbox" class="checkboxCtrl" group="pluralityCKB" />
				<spring:message code="hr.viewUpgrade.title.CHECKALL"/>
				<!--全选-->
			</label>
			<ul>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" class="checkboxCtrl" group="pluralityCKB"
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
					<spring:message code="hr.viewPersonalInfo.title.PLU_DEPTNAME"/>
					<!--兼职部门-->
				</th>
				<th>
					<spring:message code="hr.viewPersonalInfo.title.PLU_POSITINO_NAME"/>
					<!--兼职职(岗)位-->
				</th>
				<th>
					<spring:message code="hr.viewPersonalInfo.title.PLU_DUTY_NAME"/>
					<!--兼职职责-->
				</th>
				<th>
					<spring:message code="hr.viewPromote.title.EFFECTIVE_DATE"/>
					<!--生效日期-->
				</th>
				<th>
					<spring:message code="hr.viewPlurality.title.REMOVEREMARK"/>
					<!--(解除)兼职事由-->
				</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${pluralityList}" var="plurality">
				<tr target="sid" rel="${plurality.EMPID}">
					<td>
						<input type="checkbox" id="pluralityCKB" name="pluralityCKB" value="${plurality.EMPID}" />
						<input type="hidden" name="PERSON_ID_${plurality.EMPID}" value="${plurality.PERSON_ID}"/>
						
						<input type="hidden" name="POSITION_NO_${plurality.EMPID}" value="${plurality.POSITION_NO}"/>
						<input type="hidden" name="POST_NO_${plurality.EMPID}" value="${plurality.POST_NO}"/>
						<input type="hidden" name="PLURALITY_TYPE" value="${PLURALITY_TYPE}"/>
						<input type="hidden" name="EXP_INSIDE_NO" value="${EXP_INSIDE_NO}"/>
					</td>
					
					<td>
						${plurality.EMPID}
					</td>
					
					<td>
						${plurality.LOCAL_NAME}
					</td>
					
					<td>
						${plurality.DEPT_NAME}
					</td>
					
					<td>
						${plurality.POSITION_NAME}
					</td>
					
					<td>
						${plurality.POST_NAME}
					</td>
						
					<td>
						<input id="deptName_${plurality.EMPID}" name="deptName_${plurality.EMPID}" type="text" value="${plurality.DEPT_NAME}" onclick="showTree_plurality('deptName_${plurality.EMPID}',  'deptNo_${plurality.EMPID}', '${plurality.EMPID}')" readonly />
						<input id="deptNo_${plurality.EMPID}" name="DEPTNO_${plurality.EMPID}" value="${plurality.DEPTNO}" type="hidden" />
						
					</td>
					
					<td>
						<select name="PLU_POSITION_NO_${plurality.EMPID}" id="PLU_POSITION_NO_${plurality.EMPID}">
							<option value=""><spring:message code="hr.viewPersonalInfo.title.ADDED_BY_KELI"/><!-- 请选择 --></option>
							<c:forEach items="${positionList}" var="position">
								<option value="${position.POSITION_NO}">${position.POSITION_NAME}</option>
							</c:forEach>
						</select>
					</td>
					
					<td>
						<select name="PLU_DUTY_NO_${plurality.EMPID}" id="PLU_DUTY_NO_${plurality.EMPID}">
							<option value=""><spring:message code="hr.viewPersonalInfo.title.ADDED_BY_KELI"/><!-- 请选择 --></option>
							<c:forEach items="${dutyList}" var="duty">
								<option value="${duty.DUTY_NO}">${duty.DUTY_NAME}</option>
							</c:forEach>
						</select>
					</td>
					
					<td>
						<input type="text" id="START_DATE_${plurality.EMPID}" name="START_DATE_${plurality.EMPID}" class="date" size="10" />
		    				<a class="inputDateButton" href="javascript:;"><spring:message code="public.title.choose"/><!-- 选择 --></a>
					</td>
						
					<td>
						<input type="text" id="PLU_REASON_${plurality.EMPID}" name="PLU_REASON_${plurality.EMPID}" maxlength="60"/>	
					</td>
					
				</tr>
			</c:forEach>
		</tbody>
	</table>			
</form>
			<c:set value="/hrm/transferOrder/viewPlurality" var="pageUrl"/>
			<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>

<div id="deptContent_plurality" style="display:none; position: absolute;z-index:999;overflow:auto;height:350px; border:solid 1px #CCC; line-height:21px; background:#FFF;" >
	<ul id="deptTree_plurality" class="ztree" style="margin-top:0; width:300px;"></ul>
</div>