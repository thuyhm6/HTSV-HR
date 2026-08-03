<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<ait:deptTreeResume name="TREE_DEPTNO" cpnyId="${LoginUser.cpnyId}" limit="super" id="viewComposeOrgTree" 
		clickFun="1" resumeNo="${RESUME_NO}"
		style="float:left; display:block; margin:10px; overflow:auto;width:300px; height:500px; border:solid 1px #CCC; line-height:21px; background:#FFF;" selected="${DEPTNO}"/>
	