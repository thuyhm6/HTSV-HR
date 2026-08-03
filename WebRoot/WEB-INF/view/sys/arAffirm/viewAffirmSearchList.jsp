<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
	function ajaxChange(){
			var seach_APPLY_TYPE_NO=document.getElementById("seach_APPLY_TYPE_NO_ID").value;	
	   		$.ajax({
				type: 'POST',
				url:encodeURI(encodeURI('/sys/arAffirm/getApplyTypeCodeList?seach_APPLY_TYPE_NO=' + seach_APPLY_TYPE_NO)),
				dataType:"json",
				cache: false,
				success: function(jsonObject){
	   					var length = jsonObject.length;
	   					var seach_APPLY_TYPE_CODE_ID = document.getElementById("seach_APPLY_TYPE_CODE_ID");
	   					seach_APPLY_TYPE_CODE_ID.length=0;	
	   					seach_APPLY_TYPE_CODE_ID.options.add(new Option("select", ""));
	   					for(var i=0;i<length;i++){
	   						seach_APPLY_TYPE_CODE_ID.options.add(new Option(jsonObject[i].CODENAME , jsonObject[i].CODE_NO));
	   						
	   					}	
					},
				error:function(){
							alert("ajax error!");
					}
			});
	 }
</script> 
<div class="pageHeader"> 
	<form onsubmit="return navTabSearch(this);" action="/sys/arAffirm/viewAffirmSearchList" method="post">
		<div class="searchBar" style="padding:5px;">
			<table class="searchContent">
				<tr>
				  <td><!--社号--><spring:message code="org.title.EMPID" />:</td>
				  <td>	
						<input id="personId" name="dwz.person.personId"  type="hidden" lookupGroup="person" value="${Person_Id }" />
						<input id="empid" name="dwz.person.empId" type="text"  lookupGroup="person" class="required"  readOnly value="${Emp_Id }" /> 					
				  </td> 
				  <td>
				  	<a class="btnLook"	href="/ar/attendanceMintenance/viewEmpCalendarList?firstFlag=1&limit=hr&pageNum=1" lookupGroup="person">
						<spring:message	code="ar.alert.message.viewattendencekeeper.personalInfo" />
					</a>
				  </td>
				  <td><!--信息申请类型--><spring:message code="sys.viewAffirmSearchList.XINXISHENQINGLEIXING.b" />：</td>
				  <td>	
					<select name="APPLY_TYPE_NO" id = "seach_APPLY_TYPE_NO_ID" onchange="ajaxChange();">
					    <option value="">select</option>
						<c:forEach items="${applyTypeNoList}" var="applyTypeNo">
						  <option value="${applyTypeNo.CODE_NO}" <c:if test="${applyTypeNo.CODE_NO eq Apply_Type_No}">selected="selected"</c:if>>${applyTypeNo.CODENAME}</option>
						</c:forEach>
					</select>
	              </td> 
	              <td><spring:message code="sys.affirm.title.applyType"/><!--申请类型-->：</td>
	              <td>					
					<select name="APPLY_TYPE_CODE" id = "seach_APPLY_TYPE_CODE_ID" >
					    <option value="">select</option>
						<c:forEach items="${applyTypeCodeList}" var="applyTypeCode">
						  <option value="${applyTypeCode.CODE_NO}" <c:if test="${applyTypeCode.CODE_NO eq Apply_Type_Code}">selected="selected"</c:if>>${applyTypeCode.CODENAME}</option>
						</c:forEach>
					</select>
	              </td>
	              <td><!--申请长度--><spring:message code="sys.viewAffirmSearchList.SHENQINGCHANGDU.b" /></td>
	              <td>
					<input name="APPLY_LENGTH" type="text"	id="APPLY_LENGTH" size="5" maxlength="5" value="${Apply_Length }">
				  </td> 
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent"><button type="submit">
					<spring:message code="public.title.search"/><!-- 检索 --></button></div></div></li>
				</ul>
			</div>
		</div>
	</form>	
</div>
<div>
	<table class="table" width="100%" layoutH="206" nowrapTD="false">
			<thead>
			<tr>
				<th><!--裁决者等级--><spring:message code="sys.viewAffirmSearchList.JUECAIZHEDENGJI.b" /></th>
				<th><!--社号--><spring:message code="org.title.EMPID" /></th>       
				<th><!--姓名--><spring:message code="org.title.LOCAL_NAME" /></th>  
				<th><!--职责--><spring:message code="sys.affirm.title.duty" /></th>  
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${affirmorList}" var="arAffirmor" varStatus="i">
			  	<tr>
				 	<td style="text-align:center;">${i.count }</td>
				 	<td style="text-align:center;">${arAffirmor.EMPID }</td>
				 	<td style="text-align:center;">${arAffirmor.LOCAL_NAME }</td>
				 	<td style="text-align:center;">${arAffirmor.POSITION_NO }</td>
				</tr>
			</c:forEach>			
		</tbody>
		
	</table>
	<div id="affirmDetail" style="border:10;overflow:auto;bottom:10;right:100;width:300;z-index:1;"></div>
	<c:set value="/sys/arAffirm/viewAffirmSearchList" var="pageUrl"/>
	 <%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>
