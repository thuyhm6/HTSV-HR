<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function validateCallbackUpgrade(form,callback) {	
	var $form = $("#upGrade");
	
	if (!$form.valid()) {
		return false;
	}


	var checked = false ;

	$form.find(":checkbox[id='c1']").each(function(index, checkBoxObj){
	    
	    if(checkBoxObj.checked){
	      checked = true ;      
	    }
	    
	  });
	 if(!checked){
		 	//请选择信息再进行保存操作
			alertMsg.error('<spring:message code="hr.alert.message.viewPersonalInfo.checkBoxForChecked"/>');
			return false;
		 }
	$form.find(":checkbox[id='c1']").each(function(index, checkBoxObj){
	    if(checkBoxObj.checked){
	      checked = true ;
		  
	      var empid = $(checkBoxObj).val();
	      
		    if($form.find("[name='POSITION_NO_" + empid + "']").val()==''){
				//alert("职(岗)位不能为空");
				alertMsg.error('<spring:message code="hr.alert.message.viewHire.checkNotNullPositionNo"/>');
				$form.find("[name='POSITION_NO_" + empid + "']").focus();
				checked=false;
				return false;
			}else if($form.find("select[name='POST_GRADE_NO_" + empid + "']").val()==''){
				//alert("职级(GGS)不能为空");
				alertMsg.error('<spring:message code="hr.alert.message.viewHire.checkNotNullPostGradeNo"/>');
				$form.find("select[name='POST_GRADE_NO_" + empid + "']").focus();
				checked=false;
				return false;
			}else if($form.find("select[name='DUTY_NO_" + empid + "']").val()==''){
				//alert("职责不能为空");
				alertMsg.error('<spring:message code="hr.alert.message.viewUpgrade.checkNotNullDutyNo"/>');
				$form.find("select[name='DUTY_NO_" + empid + "']").focus();
				checked=false;
				return false;
			}else if($form.find("select[name='WORK_AREA_" + empid + "']").val()==''){
				//alert("工作地不能为空");
				alertMsg.error('<spring:message code="hr.alert.message.viewHire.checkNotNullWorkArea"/>');
				$form.find("select[name='WORK_AREA_" + empid + "']").focus();
				checked=false;
				return false;
			}else if($form.find("[name='FUNCTION_DATE_" + empid + "']").val()==''){
				//alert("生效日期不能为空");
				alertMsg.error('<spring:message code="hr.alert.message.viewUpgrade.checkNotNullFunctionDate"/>');
				$form.find("[name='FUNCTION_DATE_" + empid + "']").focus();
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
function navTabSearchUpGrade(form, navTabId){
	var $form = $("#searchUpGrade");
		
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
function setWorkArea_upGrade(treeNode, name){

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
    var empid_upGrade = "" ;

	var setting_upGrade = {
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
			onClick: onClick_upGrade
		}
	};
	var zNodes_upGrade = ${deptJson} ;
	
	function onClick_upGrade(event, treeId, treeNode) {
	 
	    var $form = $("#upGrade");
	    
	    var temp = $form.find("[name='deptName_"+empid_upGrade+"']").val();

		setWorkArea_upGrade(treeNode, empid_upGrade) ;
		
		var deptName = $("#deptName_" + empid_upGrade).val();

		$form.find("[name='deptName_"+empid_upGrade+"']").attr("value", treeNode.DEPTNAME);
		
		$form.find("[name='DEPTNO_"+empid_upGrade+"']").attr("value", treeNode.DEPTNO);
		
	}
	function showTree_upGrade(nameObjId, noObjId, key) {

		if(key == null || key.length == 0){
			return ;
		}
		
		empid_upGrade = key ;
		
		var $treeObj_upGrade = $.fn.zTree.getZTreeObj("deptTree_upGrade") ;
		var $nodeObj_upGrade = $treeObj_upGrade.getNodeByParam('DEPTNO', $("#" + noObjId).val()) ;
		if($nodeObj_upGrade != null ){
			$treeObj_upGrade.selectNode($nodeObj_upGrade) ;
			onClick_upGrade(null, null, $nodeObj_upGrade) ;
		}

		var $form = $("#upGrade");
		var deptNameObj = $form.find("[name='"+nameObjId+"']");
		var deptNameOffset = $form.find("[name='"+nameObjId+"']").offset() ;
		
		$("#deptContent_upGrade").css({left:(deptNameOffset.left - 186) + "px", top:(deptNameOffset.top + deptNameObj.outerHeight() - 104) + "px"}).slideDown("fast") ;
		
		$("body").bind("mousedown", onBodyDown_upGrade);
	}
	function hideMenu_upGrade() {
		$("#deptContent_upGrade").fadeOut("fast");
		$("body").unbind("mousedown", onBodyDown_upGrade);
	}
	function onBodyDown_upGrade(event) {
		if (! (event.target.id == "menuBtn" || event.target.id == "deptContent_upGrade" || $(event.target).parents("#deptContent_upGrade").length > 0)) {
			hideMenu_upGrade();
		}
	}
	$(document).ready(function() {
		$.fn.zTree.init($("#deptTree_upGrade"), setting_upGrade, zNodes_upGrade);
	});
</SCRIPT>

<div style="padding:5px;">
<div class="pageHeader">
	<form id="searchUpGrade" onsubmit="return navTabSearchUpGrade(this);" action="/hrm/transferOrder/viewUpgrade" method="post" rel="pagerForm">
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

	<form style="margin:0px;padding:0px;" id="upGrade" onsubmit="return validateCallbackUpgrade(this,navTabAjaxDone);" action="/hrm/transferOrder/saveTransferOrderUpgrade" method="post"  class="pageForm required-validate">
	<div class="formBar">
			<tr>	
			<label style="float: left">
				<input type="checkbox" class="checkboxCtrl" group="c1" />
				<spring:message code="hr.viewUpgrade.title.CHECKALL"/>
				<!--全选-->
			</label>
			<ul>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" class="checkboxCtrl" group="c1"
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
			<tr>
				<!-- orderField 与sql 的orderBy对应  orderDirection 为相反配置 class 控制图片-->
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
				<th width="8%" orderField="nlssort(POST_GRADE_NAME,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
					<spring:message code="hr.viewPersonalInfo.title.POST_GRADE_NAME"/>
					<!--职级(GGS)-->
				</th>
				<th width="10%" orderField="nlssort(DUTY_NAME,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
					<spring:message code="hr.viewPersonalInfo.title.DUTY_NAME"/>
					<!--职责-->
				</th>
				<th width="8%" orderField="nlssort(WORK_AREA_NAME,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
					<spring:message code="hr.viewPersonalInfo.title.WORK_AREA_NAME"/>
					<!--工作地-->
				</th>
				<th width="12%" orderField="nlssort(POST_NAME,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
					<spring:message code="hr.viewPersonalInfo.title.POST_NAME"/>
					<!--职级名称(职务)-->
				</th>
				<th width="10%">
					<spring:message code="hr.viewPromote.title.EFFECTIVE_DATE"/>
					<!--生效日期-->
				</th>
				<th>
					<spring:message code="hr.viewUpgrade.title.UPGRADETYPE"/>
					<!--调动类型-->
				</th>
				
			</tr>
		</thead>
		<tbody>
		
			<c:forEach items="${upGradeList}" var="upGrade">
				<tr target="sid" rel="${upGrade.EMPID}">
					<input type="hidden" name="OLD_POST_GRADE_NO_${upGrade.EMPID}" value="${upGrade.POST_GRADE_NO}"/>
					<input type="hidden" name="STATUS_CODE_${upGrade.EMPID}" value="${upGrade.STATUS_CODE}"/>
					<input type="hidden" name="PERSON_ID_${upGrade.EMPID}" value="${upGrade.PERSON_ID}"/>
					<td>
						<input type="checkbox" id="c1" name="c1" value="${upGrade.EMPID}" />
					</td>
					<td>${upGrade.EMPID}</td>
					<td>${upGrade.LOCAL_NAME}</td>
					<td>
						<input id="deptName_${upGrade.EMPID}" name="deptName_${upGrade.EMPID}" type="text" value="${upGrade.DEPT_NAME}" onclick="showTree_upGrade('deptName_${upGrade.EMPID}',  'deptNo_${upGrade.EMPID}', '${upGrade.EMPID}')" readonly />
						<input id="deptNo_${upGrade.EMPID}" name="DEPTNO_${upGrade.EMPID}" value="${upGrade.DEPTNO}" type="hidden" />
					</td>
					<td>
						<select name="POSITION_NO_${upGrade.EMPID}" id="POSITION_NO">
							<option value=""><spring:message code="hr.viewPersonalInfo.title.ADDED_BY_KELI"/><!-- 请选择 --></option>
							<c:forEach items="${positionList}" var="position">
								<option value="${position.POSITION_NO}" <c:if test="${position.POSITION_NO eq upGrade.POSITION_NO}">selected</c:if>>${position.POSITION_NAME}</option>
							</c:forEach>
						</select>
					</td>
					<td>
						<select class="combox" name="POST_GRADE_NO_${upGrade.EMPID}" ref="combox_POST_NO" refUrl="/hrm/transferOrder/getPostNoByPostGradeNo?POST_GRADE_NO={value}" id="POST_GRADE_NO" ref2="combox_DUTY_NO" refUrl2="/hrm/transferOrder/getDutyNoByPostGradeNo?POST_GRADE_NO={value}">
							<option value=""><spring:message code="hr.viewPersonalInfo.title.ADDED_BY_KELI"/><!-- 请选择 --></option>
							<c:forEach items="${postGradeList}" var="postGrade">
								<option value="${postGrade.POST_GRADE_NO}" <c:if test="${postGrade.POST_GRADE_NO eq upGrade.POST_GRADE_NO}">selected</c:if>>${postGrade.POST_GRADE_NAME}</option>
							</c:forEach>
						</select>
					</td>	
					<td>
						<!--<ait:SelectSyCodeByCpnyID name="DUTY_NO_${upGrade.EMPID}" parentNo="881" cnpyID="${defaultCpny}" selected="${upGrade.DUTY_NO}" limit="all"/>-->
						<select name="DUTY_NO_${upGrade.EMPID}" id="combox_DUTY_NO">
							<option value=""><spring:message code="hr.viewPersonalInfo.title.ADDED_BY_KELI"/><!-- 请选择 --></option>
							<c:forEach items="${dutyList}" var="duty">
								<option value="${duty.DUTY_NO}" <c:if test="${duty.DUTY_NO eq upGrade.DUTY_NO}">selected</c:if>>${duty.DUTY_NAME}</option>
							</c:forEach>
						</select>
					</td>
					<td>
						<select class="combox" name="WORK_AREA_${upGrade.EMPID}" id="combox_WORK_AREA_${upGrade.EMPID}">
						<option value=""><spring:message code="hr.viewPersonalInfo.title.ADDED_BY_KELI"/><!-- 请选择 --></option>
						<c:forEach items="${workAreaList}" var="workArea">
							<option value="${workArea.WORK_AREA}" <c:if test="${workArea.WORK_AREA eq upGrade.WORK_AREA}">selected</c:if>>${workArea.WORKAREA_NAME}</option>
						</c:forEach>
					</select>
					</td>	
					<td>
						<select class="combox" name="POST_NO_${upGrade.EMPID}" id="combox_POST_NO" readonly="readonly">
								<option value="${upGrade.POST_NO}" >${upGrade.POST_NAME}</option>
						</select>
					</td>
					<td>
						<input type="text" id="FUNCTION_DATE" name="FUNCTION_DATE_${upGrade.EMPID}" size="10" class="date"/>
					</td>
					<td>
						<ait:SelectSyCodeByCpnyID name="TRANS_CODE_${upGrade.EMPID}" parentNo="1365" cnpyID="${defaultCpny}"/>
					</td>							
				</tr>
			</c:forEach>
		</tbody>
	</table>			
</form>
			<c:set value="/hrm/transferOrder/viewUpgrade" var="pageUrl"/>
			<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>
<div id="deptContent_upGrade" style="display:none; position: absolute;z-index:999;overflow:auto;height:350px; border:solid 1px #CCC; line-height:21px; background:#FFF;" >
	<ul id="deptTree_upGrade" class="ztree" style="margin-top:0; width:300px;"></ul>
</div>