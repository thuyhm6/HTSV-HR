<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">	
$("#sealLoginForm").submit();
$.pdialog.closeCurrent();
</script> 
<div class="pageContent">
	<form id="sealLoginForm" name="sealLoginForm" method="post" 
	    action = "http://sealcn.lge.com:5310/sealLogin.jsp" target="color12345">   
		   <input name="id" id="id" type="hidden" value="${paramMap.empId}">
		   <input name="pwd" id="pwd" type="hidden" value="${paramMap.pwd}">
	       <input name="comCd" name="comCd" type="hidden" value="${paramMap.cpnyId}">
	</form>
</div>