<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
	function searchPerson()
	{
		var edition_no = $("#seach_EDITION_NO").find("option:selected").text();
		var href = "/ess/dimissionApply/viewEditionItemMapping?ACTIVITY=1&editionFlag=1";
		href+="&EDITION_NO="+edition_no;
		navTabNum(href,'sys2017');
	}
</script>
<form id="view_paHistory" method="post" action="/ess/dimissionApply/updateEditionItemTypeParamInfo" class="pageForm required-validate" onsubmit="return validateCallback(this)">
	<div class="searchBar" style="padding:5px;">
		<table class="searchContent" width="100%">
			<tr>
				<td>
	               	<spring:message code="ess.dimission.title.editionnumber"/><!--版本号-->：
	               	<select name="seach_EDITION_NO" id="seach_EDITION_NO" onchange="searchPerson()">
	               	<option value=""<c:if test="${'' eq edition_no}"></c:if>></option>
	               <c:forEach items="${EditionList}" var="item">
	                  <option value="${item.EDITION_NO}"<c:if test="${item.EDITION_NO eq edition_no}">selected="selected"</c:if>>${item.EDITION_NO}</option>
				   </c:forEach>
	               	</select>
	               	具体审批人和部门领导（二选一），如果两个都选优先取部门领导
	            </td> 
			</tr>
		</table>
	</div>
	
   <input type="hidden" name="index" value="${fn:length(map)}"/>
	<c:forEach items="${map}" var="item" varStatus="i">
	<div class="pageContent" id="paHistoryResultCenter">
		<div class="panel">
			<h1>
			   ${item.key.EDITION_ITEM_NAME}
			   <input type="hidden" name="index${i.index}" value="${fn:length(item.value)}"/>
			</h1>
			<div>
			<c:forEach items="${item.value}" var="item1" varStatus="j">
				<table width="100%">
				<tr>
				<td width="50%">
				 
				
				 <input id="jsonData" name="jsonData" value="" type="hidden"/>
									<input id="personId${i.index}${j.index }" name="dwz.person${i.index}${j.index }.personId" value="${item1.PERSON_ID }" type="hidden" lookupGroup="person${i.index}${j.index }"/>
									<input id="empName${i.index}${j.index }" name="dwz.person${i.index}${j.index }.empName" type="text" value="${item1.LOCAL_NAME}"  readOnly lookupGroup="person${i.index}${j.index }"/>
									 <a class="btnLook" href="/ar/attendanceSettings/viewKeeperList?pageNum=1"
							lookupGroup="person${i.index}${j.index }">
							<!-- 人员信息-->
							<spring:message
								code="ar.alert.message.viewattendencekeeper.personalInfo" />
						</a>	   
				
				  ${item1.EDITION_PARAM_NAME}
				   <input type="hidden" name="EDITION_ITEM_NO${i.index}${j.index }" id="EDITION_ITEM_NO" value="${item1.EDITION_ITEM_NO }"/>
				   
			   </td>
			   <td><input id="jsonData" name="jsonData" value="" type="hidden"/>
									<input id="manageId${i.index}${j.index }" name="dwz.dept${i.index}${j.index }.manageId" value="${item1.PERSON_ID }" type="hidden" lookupGroup="dept${i.index}${j.index }"/>
									<input id="manageName${i.index}${j.index }" name="dwz.dept${i.index}${j.index }.manageName" type="text" value="${item1.LOCAL_NAME}"  readOnly lookupGroup="dept${i.index}${j.index }"/>
									 <a class="tree_icon" href="/ess/dimissionApply/viewOrgList?pageNum=1"
							lookupGroup="dept${i.index}${j.index }">
							
						</a></td>
			    </tr>
				</table>
				</c:forEach>
			</div>
			
		</div>
	</div>
	</c:forEach>
	<div class="formBar" layoutH="106">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">
									<spring:message code="public.title.submit"/><!-- 提交 -->
								</button>
							</div>
						</div>
					</li>
					<li>
						<div class="button">
							<div class="buttonContent">
								<button type="button" class="close">
									<spring:message code="public.title.cancle"/><!--取消-->
								</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
</form>