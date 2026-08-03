<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
 					<!-- 头部搜索条开始 -->
 					<div class="pageHeader">
						<form onsubmit="return divSearch(this, 'jbsxBox_sy0420');" action="/sys/basicMaintenance/getCodeListByParentCode?pageNum=1" method="post" rel="pagerForm" >
						<div class="searchBar">
							<table class="searchContent">
								<tr>
									<td class="dateRange">
										<spring:message code="sys.basic.title.codeName"/><!--代码名称-->:
										<input type="text" value="${CODE_NAME}"  name="seach_CODE_NAME">
										<input type="hidden" name="seach_PARENT_CODE_NO" value="${PARENT_CODE_NO}" />
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
					<!-- 头部搜索条结束 -->
					<!-- 表格主体开始 -->
					<div class="pageContent">
						<!-- <div class="panelBar">
							<ul class="toolBar">
								<li><a class="add" href="/sys/basicMaintenance/getAddCodeView?seach_PARENT_CODE_NO=${PARENT_CODE_NO}" target="dialog" mask="true"><span>添加</span></a></li>
								<li><a class="edit"  href="/sys/basicMaintenance/getEditCodeView?NO={CodeNO}" target="dialog" mask="true"><span>修改</span></a></li>
								<li><a class="delete" href="/sys/basicMaintenance/delCodeByCodeNo?NO={CodeNO}" target="ajaxTodo" title="确定要删除吗?"><span>删除</span></a></li>
								<li class="line">line</li>
							</ul>
						</div> -->
						<c:set value="dialog" var="add_tab"/>
						<c:set value="400" var="add_width"/>
						<c:set value="400" var="add_height"/>
						<c:set value="/sys/basicMaintenance/getAddCodeView?seach_PARENT_CODE_NO=${PARENT_CODE_NO}" var="add_Url"/>
						<c:set value="ajaxTodo" var="delete_tab"/>
						<c:set value="/sys/basicMaintenance/delCodeByCodeNo?NO={CodeNO}" var="delete_Url"/>
						<c:set value="dialog" var="edit_tab"/>
						<c:set value="400" var="edit_width"/>
						<c:set value="400" var="edit_height"/>
						<c:set value="/sys/basicMaintenance/getEditCodeView?NO={CodeNO}" var="edit_Url"/>
						<%@ include file="/WEB-INF/view/inc/includeButton_nodelete.jsp"%>
						<table class="table" width="99%" layoutH="214">
							<thead>
								<tr>
									<th><spring:message code="sys.affirm.indexNum"/><!--序号--></th>
									<th><spring:message code="sys.affirm.CodeNo"/><!--  代码NO--></th>
									<th><spring:message code="sys.affirm.CodeCode"/><!--  代码CODE--></th>
									<th><spring:message code="sys.basic.title.codeNameCN"/><!--中文代码名称--></th>
									<!--<th><spring:message code="sys.basic.title.codeNameKO"/>  韩文代码名称</th>-->
									<th><spring:message code="sys.basic.title.OrderCode"/><!--  排序字段--></th>
								</tr>
							</thead>
							<tbody>
							<c:forEach items="${codeLists}" var="code" varStatus="i">
								<tr target="CodeNO" rel="${code.CODE_NO}">
									<td>${i.index+1}</td>
									<td>${code.CODE_NO }</td>
									<td>${code.DESCRIPTION }</td>
									<td>${code.CONTENT}</td>
									<!--<td>${code.KO}</td>-->
									<td>${code.ORDERNO}</td>
									<!--<td>${ code.CODE_NAME_EN}</td>-->
								</tr>
							</c:forEach>
							</tbody>
						</table>
						
                        <script>
                        function pageFromSea(totalCnt){
                        }
                        </script>
                        <c:set value="/sys/basicMaintenance/getCodeListByParentCode" var="pageUrl"/>
                        <c:set value="jbsxBox_sy0420" var="targetInfo"/>
                        <%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
                        <%-- 
						<form id="pagerForm" method="post" action="${pageUrl}">
							<input type="hidden" name="pageNum" value="${pageNum}" />
							<input type="hidden" name="numPerPage" value="${numPerPage}" />
						</form>
						<div class="panelBar">
							<div class="pages">
								<span><spring:message code="public.title.view"/><!-- 显示 --></span>
								<select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value}, 'jbsxBox_sy0420')">
									<option value="10"  <c:if test="${numPerPage == 10 }" >selected</c:if> >10</option>
									<option value="20"  <c:if test="${numPerPage == 20 }" >selected</c:if> >20</option>
									<option value="30"  <c:if test="${numPerPage == 30 }" >selected</c:if> >30</option>
								</select>
							<span><spring:message code="public.title.tiao"/><!--条-->,<spring:message code="public.title.gong"/><!--共-->${totalCount}<spring:message code="public.title.tiao"/><!--条--></span>
							</div>
							<div class="pagination" rel="jbsxBox_sy0420"  totalCount="${totalCount}" numPerPage="${numPerPage}" currentPage="${pageNum}"></div>
						</div>
                        --%>
					</div>
					<!--表格主体开始 -->