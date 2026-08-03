<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function save(form){
	
	var personId = $('#personId').attr("value");
	var deptNos = $('#deptNos').attr("value");
	if(deptNos == ''){
		alertMsg.error('<spring:message code="pa.wagebase.title.chooseDeptThenSubmitData"/>')
		return false;
	}
	var deptNosArray = deptNos.split(",");	
	var jsonData = '[' ;
	for(var i=0;i<deptNosArray.length;i++){
		if (jsonData.length > 1){
           	jsonData += ',{'
        }else{
        	jsonData += '{'
        }
        jsonData += ' "DEPTNO": "' + deptNosArray[i] + '", ' ;
        jsonData += ' "PERSON_ID": "' + personId + '" ' ;
        jsonData += '}' ;
	}
	jsonData += ']' ;
	if(jsonData.indexOf("DEPTNO") == -1){
		alertMsg.error('<spring:message code="pa.wagebase.title.chooseDeptThenSubmitData"/>')
		return false;
	}
	
    $('#jsonData').attr('value' , jsonData);
    $('#PERSON_ID').attr('value',personId);
    
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
	<form method="post" action="/pa/wagebase/addPaSupervisorInfo" class="pageForm required-validate" onsubmit="return save(this);">
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">
				<spring:message code="pa.insurance.title.submit"/><!--保存--></button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">
				<spring:message code="public.title.cancle"/><!--取消--></button></div></div></li>
			</ul>
		</div>
		<div class="panel" style=" float:left; width:50%;clear:none;" >
				<h1><!-- 人员信息 --><spring:message code="ar.alert.message.viewattendencekeeper.personalInfo"/></h1>
					<div class="pageFormContent nowrap" layoutH="90">
						<table class="searchContent">
							<tr>
								<td style="width:30px">&nbsp;</td>
								<td><spring:message code="public.title.empId"/><!--工号-->:</td>
								<td><input id="jsonData" name="jsonData" value="" type="hidden"/>
									<input id="PERSON_ID" name="PERSON_ID" value="" type="hidden"/>
									<input id="personId" name="dwz.person.personId" value="" type="hidden" lookupGroup="person"/>
									<input name="dwz.person.empId" type="text" class="required"  readOnly lookupGroup="person"/>
									<a class="btnLook" href="/ar/attendanceMintenance/viewEmpCalendarList?isEmployeement=1&firstFlag=1&limit=pa&pageNum=1" lookupGroup="person">
									<spring:message code="pa.insurance.title.lookUpAndBack"/></a>
								</td>
							</tr>
							<tr>
								<td style="width:30px">&nbsp;</td>
								<td><spring:message code="public.title.name"/><!--姓名-->:</td>
								<td><input name="dwz.person.empName" type="text" readOnly lookupGroup="person"/></td>
							</tr>
							<tr>
								<td style="width:30px">&nbsp;</td>
								<td><spring:message code="public.title.deptName"/><!--部门-->:</td>
								<td><input name="dwz.person.empDept" type="text" readOnly lookupGroup="person"/></td>
							</tr>
							<%-- <tr>
								<td style="width:30px">&nbsp;</td>
								<td>人员类型组:</td>
								<td>
								<table width="100%">
				    			 <tr align="center" width="100%"> 
							       <c:forEach items="${jobTypeGroupList}" var="vList" varStatus="i">
							             <c:choose>
							              	<c:when test="${i.count % 2 == 0}">
									           <td width="25%" align="left">
									              <input name="isChecked" id="isChecked_${vList.JOBTYPE_GROUP_NO}" value="${vList.JOBTYPE_GROUP_NO}"  type="checkbox" style="border:0px"
									              />
									              	${vList.JOBTYPE_GROUP_NAME}
									            </td>	
										    </tr>
											</c:when>
							  				<c:otherwise>
										         <td width="25%" align="left">
										           <input name="isChecked" id="isChecked_${vList.JOBTYPE_GROUP_NO}" value="${vList.JOBTYPE_GROUP_NO}"  type="checkbox" style="border:0px"
										           />
									              		${vList.JOBTYPE_GROUP_NAME}
										          </td>		  							
							  				</c:otherwise>
								      </c:choose>	
									</c:forEach>
					    	     </table>

                                </td>
							</tr> --%>
						</table>
					</div>
			</div>
			<%@ include file="updatePaSupervisorDeptTreeView.jsp"%>
	</form>
</div>
