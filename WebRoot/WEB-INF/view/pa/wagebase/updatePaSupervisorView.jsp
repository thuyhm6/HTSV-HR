<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function save(form){
	
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
        jsonData += ' "PERSON_ID": "' + $('#PERSON_ID').attr("value") + '" ' ;
        jsonData += '}' ;
	}
	
	jsonData += ']' ;
	if(jsonData.indexOf("DEPTNO") == -1){
		alertMsg.error('<spring:message code="pa.wagebase.title.chooseDeptThenSubmitData"/>')
		return false;
	}
	
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
	<form method="post" action="/pa/wagebase/updatePaSupervisorInfo" class="pageForm required-validate" onsubmit="return save(this);">
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
							<dl>
								<dt><spring:message code="public.title.empId"/><!--工号-->:</dt>
								<dd><input id="jsonData" name="jsonData" value="" type="hidden"/>
									<input id="PERSON_ID" name="PERSON_ID" value="${paSupervisorInfo.PERSON_ID}" type="hidden"/>
									<input id="personId" name="dwz.person.personId" value="" type="hidden" lookupGroup="person" />
									<input name="dwz.person.empId" type="text" class="required" value="${paSupervisorInfo.EMPID}"  readOnly lookupGroup="person"/>
								</dd>
							</dl>
							<dl>
								<dt><spring:message code="public.title.name"/><!--姓名-->:</dt>
								<dd><input id="PERSON_NAME" name="dwz.person.empName" value="${paSupervisorInfo.HRNAME}" type="text" readOnly lookupGroup="person"/></dd>
							</dl>
							<dl>
								<dt><spring:message code="public.title.deptName"/><!--部门-->:</td>
								<dd><input id="PERSON_DEPT" name="dwz.person.empDept" value="${paSupervisorInfo.DEPT_NAME}" type="text" readOnly lookupGroup="person"/></dd>
							</dl>
							<%-- <dl style="height:auto">
								<table>
				    			 <tr> 
									<td class="td_title" style="width:122px;">人员类型:</td>
									<td class="td_type">
									<ul class="dl_ul"> 
							      <c:forEach items="${jobTypeGroupList}" var="vList" varStatus="i">
							             <c:choose>
							              	<c:when test="${i.count % 2 == 0}">
									           <li>
									              <input name="isChecked" id="isChecked_${vList.JOBTYPE_GROUP_NO}" value="${vList.JOBTYPE_GROUP_NO}"  type="checkbox" style="border:0px"
									              <c:forEach items="${statisticList}" var="sList" varStatus="j">
									              		<c:if test="${sList.JOBTYPE_GROUP_NO  eq  vList.JOBTYPE_GROUP_NO}">
									              			checked=true
									              		</c:if>
									              </c:forEach>
									              />
									              	${vList.JOBTYPE_GROUP_NAME}
									            </li>	
											</c:when>
							  				<c:otherwise>
										         <li>
										           <input name="isChecked" id="isChecked_${vList.JOBTYPE_GROUP_NO}" value="${vList.JOBTYPE_GROUP_NO}"  type="checkbox" style="border:0px"
										           <c:forEach items="${statisticList}" var="sList" varStatus="j">
									              		<c:if test="${sList.JOBTYPE_GROUP_NO eq  vList.JOBTYPE_GROUP_NO}">
									              			checked=true
									              		</c:if>
									              </c:forEach>
										           />
									              		${vList.JOBTYPE_GROUP_NAME}
										          </li>		  							
							  				</c:otherwise>
								      </c:choose>	
									</c:forEach>
					    	    </ul>
								</td>
								</tr>
					    	     </table>
							</dl> --%>
					</div>
			</div>
			<%@ include file="updatePaSupervisorDeptTreeView.jsp"%>
	</form>
</div>
