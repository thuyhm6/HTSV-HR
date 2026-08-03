<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function save(form){
	
	var deptNos = $('#deptNos').attr("value");
	var deptNosArray = deptNos.split(",");
	
	var jsonData = '[' ;
//	$("#keeperDeptTree a").each(function(index){
//		if($(this).parent().find("div.checked").size() > 0){
//
//			if (jsonData.length > 1){
//            	jsonData += ',{'
//            }
//            else{
//            	jsonData += '{'
//            }
//            jsonData += ' "DEPTNO": "' + $(this).attr("tvalue") + '", ' ;
//            jsonData += ' "PERSON_ID": "' + $('#PERSON_ID').attr("value") + '" ' ;
//            jsonData += '}' ;
//			
//		}
//	});

	for(var i=0;i<deptNosArray.length;i++){
		if (jsonData.length > 1){
           	jsonData += ',{'
        }else{
        	jsonData += '{'
        }
        jsonData += ' "DEPTNO": "' + deptNosArray[i] + '", ' ;
        jsonData += ' "PERSON_ID": "' + $('#PERSON_ID').attr("value") + '" ' ;
        jsonData += '}' ;
	}
	
	jsonData += ']' ;
	
    $('#jsonData').attr('value' , jsonData);
    
	var $form = $(form);
	if (!$form.valid()) {
		return false;
	}
	$.ajax({
		type: form.method || 'POST',
		url:$form.attr("action"),
		data:$form.serializeArray(),
		dataType:"json",
		cache: false,
		success: navTabAjaxDone || DWZ.ajaxDone,
		error: DWZ.ajaxError
	});
	
	return false;
}
</script>
<div class="pageContent">
	<form method="post" action="/ar/attendanceSettings/updateAttendanceKeeperInfo" class="pageForm required-validate" onsubmit="return save(this);">
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit"><!-- 提交 --><spring:message code="public.title.submit"/></button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close"><!-- 取消 --><spring:message code="public.title.cancle"/></button></div></div></li>
			</ul>
		</div>
		<div class="panel" style=" float:left; width:50%;clear:none;" >
				<h1><!-- 人员信息 --><spring:message code="ar.alert.message.viewattendencekeeper.personalInfo"/></h1>
					<div class="pageFormContent nowrap" layoutH="90">
					<dl>
								<dt><!-- 工号 --><spring:message code="public.title.empId"/>:</dt>
								<dd><input id="jsonData" name="jsonData" value="" type="hidden"/>
									<input id="PERSON_ID" name="PERSON_ID" value="${attendanceKeeperInfo.PERSON_ID}" type="hidden"/>
									<input id="personId" name="dwz.person.personId" value="" type="hidden" lookupGroup="person" />
									<input name="dwz.person.empId" type="text" class="required" value="${attendanceKeeperInfo.EMPID}"  readOnly lookupGroup="person"/>
									<%--<a class="btnLook" href="/ar/attendanceSettings/viewKeeperList?pageNum=1" lookupGroup="person"></a> --%>
								</dd>
							</dl>
							<dl>
								<dt><!-- 姓名 --><spring:message code="public.title.name"/>:</dt>
								<dd><input id="PERSON_NAME" name="dwz.person.empName" value="${attendanceKeeperInfo.HRNAME}" type="text" readOnly lookupGroup="person"/></dd>
							</dl>
							<dl>
								<dt><!-- 部门 --><spring:message code="public.title.deptName"/>:</dt>
								<dd><input id="PERSON_DEPT" name="dwz.person.empDept" value="${attendanceKeeperInfo.DEPT_NAME}" type="text" readOnly lookupGroup="person"/></dd>
							</dl>
					</div>
			</div>
			<%@ include file="updateAttendanceKeeperDeptTreeView.jsp"%>
	</form>
</div>
