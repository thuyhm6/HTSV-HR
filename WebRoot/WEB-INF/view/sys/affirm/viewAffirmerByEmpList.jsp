<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
 
</script> 
 
<div class="pageContent">
<form method="post"  id="updateGrid" action="/sys/affirm/updateAffirmInfo" class="pageForm required-validate" onsubmit="return validateCallback(this);" >
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="/sys/affirm/saveAffirmView" target="dialog" mask="true" width="500" height="400" ><span>
			<spring:message code="button.sys.affirm.save"/><!--保存--></span></a></li>
			<li><a class="delete" href="/sys/affirm/deleteAffirmInfo?deptId={paramno}" posttype="string" rel="ids" 
			       target="ajaxTodo" title="<spring:message code='button.delete.sure'/>"><span>
			<spring:message code="button.delete"/><!--删除--></span></a></li>
			<li><a class="edit" href="/sys/affirm/updateAffirmView?deptId={paramno}" target="dialog" mask="true" width="500" height="400" ><span>
			<spring:message code="button.update"/><!--修改--></span></a></li>
			<li class="line">line</li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
			<thead>
			<tr>
				<th><spring:message code="sys.affirm.title.type"/><!--类型--></th>
				<th><spring:message code="sys.affirm.title.classType"/><!--级别--></th>
				<th><spring:message code="public.title.empId"/><!--工号-->
				   (<spring:message code="public.title.name"/><!--姓名-->)<input type="hidden" id="AFFIRM_LEVEL" name="AFFIRM_LEVEL"/>
				<input type="hidden" id="AFFIRM_PERSON" name="AFFIRM_PERSON" value="${AFFIRMOR_ID}"/>
				</th>
				<th style="cursor: default;" class=""><div title="" class="gridCol">
				    <spring:message code="sys.affirm.title.affirmOperation"/><!--操作-->
				    (<spring:message code="sys.affirm.title.deleteAll"/><!--全部删除-->
				    <input type="checkbox" class="checkboxCtrl" group="ids"/>
				    )</div></th>
			</tr>
		</thead>
		
		<tbody>
		<script>
			function changeLevel(id,trIndex,level,type){
				var number=parseInt(id);
				var levelNum=parseInt(level);
				if(number>0){
					 //当前的empId和name
					 var empId=document.getElementById(id+'empid'+trIndex).value;
					 var name=document.getElementById(id+'name'+trIndex).innerHTML;

					 var empIdUp=document.getElementById((number-1)+'empid'+trIndex).value;
					 var nameUp=document.getElementById((number-1)+'name'+trIndex).innerHTML;
					
					 document.getElementById(id+'name'+trIndex).innerHTML=nameUp; 
					 document.getElementById((number-1)+'name'+trIndex).innerHTML=name;

					 document.getElementById(id+'empid'+trIndex).value=empIdUp;
					 document.getElementById((number-1)+'empid'+trIndex).value=empId;
					 
					 document.getElementById("AFFIRM_LEVEL").value=levelNum+","+empIdUp+","+type+";"+(levelNum-1)+","+empId+","+type;
					 $("#updateGrid").submit();
				} 
			}
		</script>
			<c:forEach items="${typeList}" var="type" varStatus="i">
			  <c:forEach items="${type.detailList}" var="detail" varStatus="j">
			 	 <c:if test="${j.index==0}">
				 	  <tr>
					  	<td rowspan="${fn:length(type.detailList)}">${type.CONTENT} </td>
					  	<td>${detail.AFFIRM_LEVEL}</td>
					  	<td height="30" >
					  		<input type="text" id="${j.index}empid${i.index}" value="${detail.EMPID}" /> 
					  		<span id="${j.index}name${i.index}">(${detail.NAME})</span>
					  	</td>
					  	<td>
						  	<span id="${j.index}order${i.index}" 
						  	      onclick="changeLevel('${j.index}','${i.index}','${detail.AFFIRM_LEVEL}','${type.CODE_NO}');">
						  	<spring:message code="sys.affirm.title.orderAsc"/><!--升序--></span>&nbsp;&nbsp;
						  	<span><spring:message code="sys.affirm.title.orderDesc"/><!--降序--></span>
						  	<input type="checkbox" value="xxx" name="ids"/>
						  	<a class="delete" href="/sys/affirm/deleteAffirmLevelInfo?deptId={paramno}" target="ajaxTodo" 
						  	   title="<spring:message code='sys.affirm.title.type'/>"><span><!--确定要删除吗?-->
						  	   <spring:message code="button.delete"/><!--删除--></span></a>
					  	</td>
					 </tr>
			 	 </c:if>
			 	 <c:if test="${j.index>0}">
			 	 	<tr>
					  <td>${detail.AFFIRM_LEVEL}</td>
					  <td height="30"  >
					 	 <input type="text" id="${j.index}empid${i.index}" value="${detail.EMPID}" />
					 	 <span id="${j.index}name${i.index}">(${detail.NAME})</span>
					  </td>
					  <td>
						  <span onclick="changeLevel('${j.index}','${i.index}','${detail.AFFIRM_LEVEL}','${type.CODE_NO}');">
						  <spring:message code="sys.affirm.title.orderAsc"/><!--升序--></span>&nbsp;&nbsp;
						  <span><spring:message code="sys.affirm.title.orderDesc"/><!--降序--></span><input type="checkbox" value="xxx" name="ids"/>
						  <a class="delete" href="/sys/affirm/deleteAffirmLevelInfo?LEVEL=${detail.AFFIRM_LEVEL}&AFFIRM_EMPID=" target="ajaxTodo" 
						     title="<spring:message code='button.delete.sure'/>"><span><!--确定要删除吗?-->
						     <spring:message code="button.delete"/><!--删除--></span></a>
					  </td>
				  	</tr>
			 	 </c:if>
			  	
			  </c:forEach>
			</c:forEach>
			
		</tbody>
		
	</table>
	<c:set value="/sys/affirm/viewAffirmerByEmpList" var="pageUrl"/>
	 <%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
	 </form>
</div>
