<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<SCRIPT type='text/javascript'>

function validateCallback_PA1109(form, callback) {


	var $form = $("#addApplicationSalaryInfo");
	 
	if (!$form.valid()) {
		return false;
	} 
	
	var checked=false;
		var ids= document.getElementsByName("check_pa");
		for(var i=0;i<ids.length;i++){
			if(ids[i].checked){
				checked=true;
			}
		}
		if(!checked){
			//请选择信息再进行删除操作!
			alert("请选择工资项目");
			return false;
		} 

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
	}
	return false;
}
</SCRIPT>

<div class="pageContent">
	<form method="post" id="addApplicationSalaryInfo" action="/pa/salarycode/addApplicationSalaryInfo" class="pageForm required-validate" onsubmit="return validateCallback_PA1109(this, navTabAjaxDone);">
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit"><!-- 提交 --><spring:message code="public.title.submit"/></button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close"><!-- 取消 --><spring:message code="public.title.cancle"/></button></div></div></li>
			</ul>
		</div>
			<div class="panel" style="display:block;float:left;width:99%;clear:none;" >
				
					<h1><!-- 人员信息 --><spring:message code="ar.alert.message.viewattendencekeeper.personalInfo"/></h1>
					<div class="pageFormContent nowrap" layoutH="90">
							<dl>
								<dt><!-- 工号 --><spring:message code="public.title.empId"/>:</dt>
								<dd><input id="jsonData" name="jsonData" value="" type="hidden"/>
									<input id="PERSON_ID" name="PERSON_ID" value="" type="hidden"/>
									<input id="personId" name="dwz.person.personId" value="" type="hidden" lookupGroup="person"/>
									<input id="cpny" name="dwz.person.cpny" value="" type="hidden" lookupGroup="person"/>
									<input name="dwz.person.empId" type="text" class="required"  readOnly lookupGroup="person"/> <a class="btnLook"
							href="/ar/attendanceSettings/viewKeeperList?pageNum=1&LIZHI=1&EmpOffice=15119"
							lookupGroup="person">
							<!-- 人员信息-->
							<spring:message
								code="ar.alert.message.viewattendencekeeper.personalInfo" />
						</a>
					</dd>
							</dl>
							<dl>
								<dt><!-- 姓名 --><spring:message code="public.title.name"/>:</dt>
								<dd><input name="dwz.person.empName" type="text" readOnly lookupGroup="person"/></dd>
							</dl>
							<dl>
								<dt><!-- 部门 --><spring:message code="public.title.deptName"/>:</dt>
								<dd><input name="dwz.person.empDept" type="text" readOnly lookupGroup="person"/></dd>
							</dl>
							<dl>
								<dt><!-- 法人 -->法人:</dt>
								<dd><input name="dwz.person.cpny" type="text" readOnly lookupGroup="person"/></dd>
							</dl>
							<dl style="height:auto">
								<table width="100%">
								<tr>
								<td class="td_title" style="width:140px;"><spring:message code="pa.diff.title.salaryItem" />:</td>
								<td class="td_type">
									<table class="table" width="100%">
		       <thead>
			      <tr>
			     <th width="50"><input type="checkbox" name="c1_c"
										id="c1_c" class="checkboxCtrl" group="check_pa">
									</th>
				<th width="150"><spring:message code="hr.viewPersonalInfo.title.TRADEUNION_NUM" /></th>
				<th><spring:message code="pa.diff.title.salaryItem" /></th>
			     </tr>
		      </thead>
		      <tbody>
				<c:forEach items="${salaryCodeList}" var="item" varStatus="i">
									<tr onclick="band('#f4f7fa','black')" target="sid"
										rel="${item.ITEM_NO}" id="ITEM_NO" name="ITEM_NO">
					<td class="td_center" style="white-space:nowrap"><input
											type="checkbox" id="check_pa" name="check_pa"
											value="${item.ITEM_NO},${item.ITEM_NAME }" />
										</td>
										<td>${i.index+1 }</td>
 										<td>${item.ITEM_NAME}
                                        <input type="hidden" name="ITEM_NO" 
 											id="ITEM_NO" value="${item.ITEM_NO }" />
										</td>
				</tr>
								</c:forEach>
							</tbody>
						</table>
								</td>
								</tr>
							</table>
							</dl>
					</div>
			</div>
	</form>
</div>
