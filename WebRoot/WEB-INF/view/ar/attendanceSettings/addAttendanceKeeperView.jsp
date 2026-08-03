<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function save(form){
	
	var personId = $('#personId',navTab.getCurrentPanel()).attr("value");
	var deptNos = $('#deptNos',navTab.getCurrentPanel()).attr("value");
	var deptNosArray = deptNos.split(",");
	
	var jsonData = '[' ;
	for(var i=0;i<deptNosArray.length;i++){
		if (jsonData.length > 1){
           	jsonData += ',{'
        }else{
        	jsonData += '{'
        }
        jsonData += ' "DEPTNO": "' + deptNosArray[i] + '", ' ;
        jsonData += ' "PERSON_ID": "' + personId + '", ' ;
        jsonData += ' "CREATED_IP": "${loginUser.admin.adminIP}", ';
        jsonData += ' "CREATED_BY": "${loginUser.admin.adminID}" ';
        jsonData += '}' ;
	}
	jsonData += ']' ;
	
    $('#jsonData',navTab.getCurrentPanel()).attr('value' , jsonData);
    $('#PERSON_ID',navTab.getCurrentPanel()).attr('value',personId);
    
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
		success: navTabAjaxDone,
		error: DWZ.ajaxError
	});
	
	return false;
}
</script>
<div class="pageContent">
	<form method="post" action="/ar/attendanceSettings/addAttendanceKeeperInfo" class="pageForm required-validate" onsubmit="return save(this);">
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit"><!-- 提交 --><spring:message code="public.title.submit"/></button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close"><!-- 取消 --><spring:message code="public.title.cancle"/></button></div></div></li>
			</ul>
		</div>
			<div class="panel" style="display:block;float:left;width:50%;clear:none;" >
				
					<h1><!-- 人员信息 --><spring:message code="ar.alert.message.viewattendencekeeper.personalInfo"/></h1>
					<div class="pageFormContent nowrap" layoutH="90">
							<dl>
								<dt><!-- 工号 --><spring:message code="public.title.empId"/>:</dt>
								<dd><input id="jsonData" name="jsonData" value="" type="hidden"/>
									<input id="PERSON_ID" name="PERSON_ID" value="" type="hidden"/>
									<input id="personId" name="dwz.person.personId" value="" type="hidden" lookupGroup="person"/>
									<input name="dwz.person.empId" type="text" class="required"  readOnly lookupGroup="person"/>
									<a class="btnLook" href="/ar/attendanceMintenance/viewEmpCalendarList?isEmployeement=1&firstFlag=1&limit=hr&pageNum=1" lookupGroup="person"><!-- 查找带回 --><spring:message code="ar.alert.message.viewattendencekeeper.chazhaodaihui"/></a>
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
						<%--	<dl style="height:auto">
								<table>
								<tr>
								<td class="td_title" style="width:122px;">人员类型组:</td>
								<td class="td_type">
								<ul class="dl_ul"> 
							       <c:forEach items="${jobTypeGroupList}" var="vList" varStatus="i">
							             <c:choose>
							              	<c:when test="${i.count % 2 == 0}">
									           <li>
									              <input name="isChecked" id="isChecked_${vList.JOBTYPE_GROUP_NO}" value="${vList.JOBTYPE_GROUP_NO}"  type="checkbox" style="border:0px"/>
									              	${vList.JOBTYPE_GROUP_NAME}
									            </li>
											</c:when>
							  				<c:otherwise>
										         <li>
										           <input name="isChecked" id="isChecked_${vList.JOBTYPE_GROUP_NO}" value="${vList.JOBTYPE_GROUP_NO}"  type="checkbox" style="border:0px"/>
									              		${vList.JOBTYPE_GROUP_NAME}
										          </li>		  							
							  				</c:otherwise>
								      </c:choose>	
									</c:forEach>
					    	     </ul>
								</td>
								</tr>
							</table>
							</dl>--%>
					</div>
			</div>
			<%@ include file="updateAttendanceKeeperDeptTreeView.jsp"%>
		
		
	</form>
</div>
