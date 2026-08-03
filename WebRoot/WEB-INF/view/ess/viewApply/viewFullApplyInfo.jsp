<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageContent" >
	<table class="table" width="100%" layoutH="100">	
			<tr>
				<th align="center">${APPLY_REMARK}</th>
			</tr>		
	</table>
		<div class="formBar">
			<ul>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" class="close">
								<spring:message code="ess.title.close"/><!--关闭--> 
							</button>
						</div>
					</div>
				</li>
			</ul>
		</div>		
</div>