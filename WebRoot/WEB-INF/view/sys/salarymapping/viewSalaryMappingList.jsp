<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">

</script>
<div class="pageHeader">
	<form id="viewSalaryMappingListForm" onsubmit="return navTabSearch(this);" rel="pagerForm"
		action="/sys/salarymapping/viewSalaryMappingList" method="post" >
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td><!--法人 --><spring:message code="sys.essParam.title.legalPerson" />：
					</td>
					<td> 
<!-- 					<input type="text" name="seach_CPNY" value="${CPNY }" /> -->
						<select name="seach_CPNY">
						   <option value=""><!--请选择--><spring:message code="hr.viewCondSql.title.QINGXUANZE"/></option>
						<c:forEach items="${companyList}" var="item" varStatus="i">
						   <option value="${item.CPNY_ID}" <c:if test="${item.CPNY_ID eq CPNY }">selected</c:if>>${item.CPNY_ID}</option>
						</c:forEach>
						</select>
					</td>
					<td>
						<spring:message code="pa.insurance.title.projectType"/><!--项目类型-->：
					</td>
					<td>
						<select id="seach_TYPE" name="seach_TYPE" value="${TYPE }"> 
						<option value=""><!--全部 --><spring:message code="ess.infoApply.whole" /></option>
						<option value="1" <c:if test="${TYPE eq 1}">selected</c:if>>
							<!--标准项目 --><spring:message code="pa.viewSalaryCodeList.BIAOZHUNXIANGMU.b" />
						</option>
						<option value="2" <c:if test="${TYPE eq 2}">selected</c:if>>
							<!--支付调整项目 --><spring:message code="pa.viewSalaryCodeList.ZHIFUTIAOZHENGXIANGMU.b" />
						</option>
						<option value="3" <c:if test="${TYPE eq 3}">selected</c:if>>
							<!--支付例外项目 --><spring:message code="pa.viewSalaryCodeList.ZHIFULIWAIXIANGMU.b" />
						</option>
						<option value="4" <c:if test="${TYPE eq 4}">selected</c:if>>
							<!--扣除调整项目 --><spring:message code="pa.viewSalaryCodeList.KOUCHUTIAOZHENGXIANGMU.b" />
						</option>
						<option value="5" <c:if test="${TYPE eq 5}">selected</c:if>>
							<!--扣除例外项目 --><spring:message code="pa.viewSalaryCodeList.KOUCHULIWAIXIANGMU.b" />
						</option>
						<option value="6" <c:if test="${TYPE eq 6}">selected</c:if>>
							<!--计算项目 --><spring:message code="pa.viewSalaryCodeList.JISUANXIANGMU.b" />
						</option>
						</select>
					</td>
					<td><spring:message code="pa.insurance.title.projectName" />
						<!--项目名称-->：
					</td>
					<td>
					 <input type="text" name="seach_KEY" value="${KEY }" />
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">
									<spring:message code="public.title.search" />
									<!--检索-->
								</button>

							</div>
						</div>
					</li>
					<li>
						<a class="buttonActive"  href="/sys/salarymapping/newSalaryMappingView?NO={ITEM_NO}" target="dialog" mask="true" 
							width="800" 
							height="400">
							<span><!--指定法人 --><spring:message code="sys.basic.title.designatedLegalPerson" /></span>
						</a>
					</li>
				</ul>
			</div>
		</div>
	</form>
</div>

<div class="pageContent">
	<%-- <c:set value="/sys/salarymapping/newSalaryMappingView?&navTabId=sy1104_newSalaryMappingView&NO={ITEM_NO}"
		var="add_Url" />
	<c:set value="600" var="add_width" />
	<c:set value="500" var="add_height" />
	<c:set value="${add_name}" var="add_name" />
	<c:set value="navTab" var="add_tab" />
	<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%> --%>

	<table class="table" width="100%" layoutH="206">
		<thead>
			<tr width="100%">
				<th width="3%" align="center"><spring:message
						code="sys.affirm.indexNum" />
					<!--序号--></th>
				<th width="6%"><spring:message
						code="pa.insurance.title.projectType" />
					<!--项目类型--></th>
				<th width="12%"><spring:message
						code="pa.insurance.title.projectName" />
					<!--项目名称--></th>
				<th width="13%"><spring:message
						code="pa.insurance.title.description" />
					<!--描述--></th>
				<th width="4%" align="center">HTSV</th>
				<th width="4%" align="center">HAE</th>
				</tr>
		</thead>
		<tbody>
			<c:forEach items="${salaryMappingList}" var="item" varStatus="i">
				<tr target="ITEM_NO"
					rel="${item.ITEM_NO}&ITEM_TYPE=${item.ITEM_TYPE }">
					<td>${i.index + 1}&nbsp;</td>
					<td><c:choose>
							<c:when test="${item.ITEM_TYPE == 1 }"><!--标准项目 --><spring:message code="pa.viewSalaryCodeList.BIAOZHUNXIANGMU.b" /></c:when>
							<c:when test="${item.ITEM_TYPE == 2 }"><!--支付调整项目 --><spring:message code="pa.viewSalaryCodeList.ZHIFUTIAOZHENGXIANGMU.b" /></c:when>
							<c:when test="${item.ITEM_TYPE == 3 }"><!--支付例外项目 --><spring:message code="pa.viewSalaryCodeList.ZHIFULIWAIXIANGMU.b" /></c:when>
							<c:when test="${item.ITEM_TYPE == 4 }"><!--扣除调整项目 --><spring:message code="pa.viewSalaryCodeList.KOUCHUTIAOZHENGXIANGMU.b" /></c:when>
							<c:when test="${item.ITEM_TYPE == 5 }"><!--扣除例外项目 --><spring:message code="pa.viewSalaryCodeList.KOUCHULIWAIXIANGMU.b" /></c:when>
							<c:when test="${item.ITEM_TYPE == 6 }"><!--计算项目 --><spring:message code="pa.viewSalaryCodeList.JISUANXIANGMU.b" /></c:when>
						</c:choose></td>
					<td>${item.ITEM_NAME}</td>
					<td>${item.DESCR}</td>
					<td width="4%"><c:if test="${item.HTSV == 1}">√</c:if></td>
					<td width="4%"><c:if test="${item.HAE == 1}">√</c:if></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<c:set value="/sys/salarymapping/viewSalaryMappingList?seach_CPNY=${CPNY}" var="pageUrl" />
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
	
</div>
