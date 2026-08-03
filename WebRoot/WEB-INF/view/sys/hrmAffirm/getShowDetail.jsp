<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
</script> 
<div layouth="30" class="pageContent" >
	<table class="table" width="122%" >
			<thead>
			<tr>
				<th align="center" ><spring:message code="sys.affirm.title.duty"/><!--职责--></th>   
				<th align="center" ><spring:message code="sys.affirm.title.affirmLevel"/><!--决裁级别--></th>   
			</tr>
			</thead>
			<tbody>
			<c:forEach items="${paramList}" var="item" varStatus="i">			
				<tr>
				    <td align="center" >${item.DUTY_NAME}</td>
					<td align="center" >${i.count}
					</td>
			    </tr>			
			</c:forEach>			
		</tbody>		
	</table>
	 <div id="affirmDetail" style="position:absolute;border:10;overflow:auto;bottom:10;right:100;width:300;z-index:1;"></div>
</div>
