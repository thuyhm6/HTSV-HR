<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">

</script> 
	<form onsubmit="return navTabSearch(this);"
		action="/pa/affirmsalarycode/viewAffirmSalaryCodeList" method="post" rel="pagerForm">
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						<spring:message code="pa.insurance.title.projectType"/><!--项目类型-->：
						<select id="seach_TYPE" name="seach_TYPE" value="${TYPE }"> 
						<option value="">请选择
						</option>
						<option value="1" <c:if test="${TYPE eq 1}">selected</c:if>>
							<spring:message code="pa.wagebase.title.basicItemInfo"/>
						</option>
						<option value="2" <c:if test="${TYPE eq 2}">selected</c:if>>
							<spring:message code="pa.salary.title.inputItem"/>
						</option>
						<option value="3" <c:if test="${TYPE eq 3}">selected</c:if>>
							<spring:message code="pa.salary.title.caculateItem"/>
						</option>
						</select>
					</td>
					<td>
						<spring:message code="pa.insurance.title.projectName"/><!--项目名称-->：
						<input type="text" name="seach_KEY" value="${KEY }" />
					</td>
					<td>
						审批状态
						<select id="seach_KEYVALUE" name="seach_KEYVALUE" value="${KEYVALUE }"> 
						<option value="">请选择
						</option>
						<option value="0" <c:if test="${KEYVALUE eq '0'}">selected</c:if>>
							未审批
						</option>
						<option value="1" <c:if test="${KEYVALUE eq '1'}">selected</c:if>>
							已通过
						</option>
						<option value="2" <c:if test="${KEYVALUE eq '2'}">selected</c:if>>
							<spring:message code="hr.viewTransactionTransViewList.title.FLAGVOTEDOWN"/>
						</option>
					</select>
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">
									<spring:message code="public.title.search"/><!--检索-->
								</button>
								
							</div>
						</div>
					</li>
				</ul>
			</div>
		</div>
</div>
<div class="pageContent">
	<table class="table" width="100%" layoutH="171">
		<thead>
			<tr>
			    <th width="5%"><spring:message code="sys.affirm.indexNum" /><!--序号--></th>
				<th width="8%"> 
					申请类型
				</th>
				<th width="7%"> 
					申请法人
				</th>
			    <th width="7%"> 
					<spring:message code="pa.insurance.title.projectType"/><!--项目类型-->
				</th>
				<th width="15%">
					<spring:message code="pa.insurance.title.projectName"/><!--项目名称-->
				</th>
				<th width="20%">
					<spring:message code="pa.salarycode.affirm.reason"/><!--申请事由-->
				</th>
				<th width="20%">
					<spring:message code="hr.viewSuggestion.title.Suggestion"/><!--决裁意见-->
				</th>
				<th width="15%"><spring:message code="ess.infoApply.title.essApplyTime"/><!--申请时间-->
					</th>
				<th width="10%" style="text-align: center"><!--删除-->
					<spring:message code="button.delete"/>
				</th>	
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${salaryCodeList}" var="item" varStatus="i">
				<tr target="NO" rel="${item.AFFIRM_ITEM_NO}">
				    <td>${i.index + 1}&nbsp;</td>
				    <td >
						${item.ACTIVITY_TYPE eq 1 ? '新申请项目' : '启用项目'}
					</td>
				    <td >
						${item.AFFIRM_CPNY_ID}
					</td>
				    <td >
						${item.PROJECT_TYPE eq 1 ? '基础项目' : item.PROJECT_TYPE eq 2 ? '输入项目' : '计算项目'}
					</td>
					
					<td>
						${item.ITEM_NAME}
					</td>
					<td>
						${item.AFFIRM_REASON}
					</td>
					<td>
						${item.AFFIRM_DESCR}
					</td>
					<td>
						<fmt:formatDate value="${item.CREATE_DATE }" pattern="yyyy-MM-dd HH:mm:ss" /> 
					</td>
					<td style="text-align: center">
					    <c:if test="${item.ACTIVITY eq 0}">
	                        <a href="/pa/affirmsalarycode/deleteSalaryCodeAffirmInfo?NO=${item.AFFIRM_ITEM_NO}" target="ajaxTodo">
	                           <span><img src="/resources/images/button/Delete_little.gif"></span>
							</a>
						</c:if>
                    </td>		
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>
	</form>
	<c:set value="/pa/affirmsalarycode/viewAffirmSalaryCodeList" var="pageUrl" />
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
