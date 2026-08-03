<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../../inc/initTaglibs.jsp"%>
<html>
<head>

<title>Translate information</title>
<%@ include file="../../inc/initMeta.jsp"%>

<style type="text/css">           
        	.l-button-update,.l-button-edit{width:80px; float:right; 
        	margin-right:10px;margin-top: 10px;}    
    	</style>
</head>
<body>
<div id="loading" style="width:90%;text-align:center;padding-top: 200px;position: absolute;">
			<img src="/resources/images/loading.gif">
</div>
<div id="mainBody">
		<input type="button" value='<spring:message code="submit"/>' id="update" class="l-button l-button-update"/>
		<input type="button" value='<spring:message code="edit"/>' onclick="edit();" class="l-button l-button-edit" />		
	
<form id="infoForm">
	<%@include file="viewBasicInfo.jsp"%>


	<div id="bizlistinfo" onmouseout="change('bizlistinfo');"
					style="border: dashed 1px #cccccc;position: absolute;">
					<table cellpadding="1" cellspacing="1" style="height:25px;font-size: 12px;">
						<tr>
							<td align="right">
							<img src="/resources/images/title/top_1.gif" align="center"/>
							</td>
							<td align="left"><!--担当业务  -->
								<font size="2"><spring:message code="hrm.operation"/></font>
							</td>						
						</tr>
					</table>
	</div>
    <div id="bizlistGrid"   onmouseout="change('bizlistGrid');" style="position: absolute;"></div>
    </form>
    </div>
</body>
</html>
