<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
	
	<form id="pagerForm" name="pagerForm_${totalCount}" method="post" action="${pageUrl}">
		
		<input type="hidden" name="pageNum" value="${pageNum}" />
		<input type="hidden" name="numPerPage" value="${numPerPage}" />
		<input type="hidden" name="orderField" value="${orderField}" />
		<input type="hidden" name="orderDirection" value="${orderDirection}" />
	</form>
	<div class="panelBar">
		<div class="pages">
			<span><!-- 显示 --><spring:message code="public.title.view"/></span>
				<select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value},'${targetInfo}');">
					<option value="10"  <c:if test="${numPerPage == 10 }" >selected</c:if> >10</option> 
					<option value="30"  <c:if test="${numPerPage == 30 }" >selected</c:if> >30</option>
					<option value="50"  <c:if test="${numPerPage == 50 }" >selected</c:if> >50</option>
					<option value="80"  <c:if test="${numPerPage == 80 }" >selected</c:if> >80</option>
					<option value="100"  <c:if test="${numPerPage == 100 }" >selected</c:if> >100</option>
					<option value="200"  <c:if test="${numPerPage == 200 }" >selected</c:if> >200</option>
					<option value="300"  <c:if test="${numPerPage == 300 }" >selected</c:if> >300</option>
					<option value="500"  <c:if test="${numPerPage == 500 }" >selected</c:if> >500</option>
					<option value="800"  <c:if test="${numPerPage == 800 }" >selected</c:if> >800</option>
					<%--<option value="10000"  <c:if test="${numPerPage == 10000 }" >selected</c:if> >10000</option>--%>
				</select>
			<span><!-- 条 --><spring:message code="public.title.tiao"/>，<!-- 共 --><spring:message code="public.title.gong"/>
			${totalCount}<!-- 条 --><spring:message code="public.title.tiao"/></span>
		</div>
		<div class="pagination" targetType="navTab" rel="${targetInfo}"  totalCount="${totalCount}" numPerPage="${numPerPage}" currentPage="${pageNum}"></div>
		 
	</div>