<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
	 
</script> 
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="edit" id="editButton" href="/sys/homeParam/updateHomeParamView?PARAM_NO={sid}" target="dialog" mask="true" width="500" height="500" ><span>
			<spring:message code="sys.basic.title.designatedLegalPerson"/></span></a></li>
			<li class="line">line</li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138" nowrapTD="false">
			<thead>
			<tr>
				<th width="10"><spring:message code="sys.homeParam.title.NO"/><!--NO--></th>
				<th width="190"><spring:message code="sys.homeParam.title.homePageParamName"/><!--首页参数名称--></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${homeParamList}" var="homeParam" varStatus="i">
				<tr height="30" target="sid" rel="${homeParam.PARAM_NO}">
					<td>
						 ${i.count}
					</td>
					<td>
						${homeParam.PARAM_NAME} 
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>