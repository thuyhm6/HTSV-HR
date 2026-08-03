<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>


<div class="pageHeader">
	<form id="viewPaInputItemParamForm" onsubmit="return navTabSearch(this);" action="/pa/salary/viewPaInputItemParam" method="post" rel="pagerForm">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
						<spring:message code="pa.insurance.title.projectType"/><!--项目类型-->：
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
				<td>
					<spring:message code="pa.insurance.title.projectName"/><!--项目名称-->：
					<input type="text" name="seach_KEY" value="${KEY }" />
				</td>
				<!--<td>
					公司法人：<select name="seach_CPNY_ID" id="CPNY_ID" style="width:180px; position: static; visibility: inherit;">
								<c:forEach items="${cpnyList}" var="cpny">
								    <option value="${cpny.CPNY_ID}" <c:if test="${CPNY_ID eq cpny.CPNY_ID}">selected</c:if>>${cpny.CONTENT}</option>
								</c:forEach>
							</select>

				</td>	  -->		
			</tr>
		</table>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">
                <spring:message code="public.title.search"/><!--检索--></button></div></div></li>
					<!-- <li>
						<a class="buttonActive"  href="/pa/salary/addPaInputItemParamView?TABLE_NAME=PA_HR_V" target="dialog" mask="true" 
							width="800" 
							height="400">
							<span>添加</span>
						</a>
					</li> -->
					<li>
						<a class="buttonActive"  href="/pa/salary/updatePaInputItemParamView?PARAM_NO={sid}&TABLE_NAME=PA_HR_V" target="dialog" mask="true" 
							width="800" 
							height="400">
							<span><spring:message code="button.update"/><!--修改--></span>
						</a>
					</li>
			</ul>
		</div>
	</div>
	</form>
</div>

<div class="pageContent" >		
		<%-- <c:set value="/pa/salary/addPaInputItemParamView?TABLE_NAME=PA_HR_V" var="add_Url"/>
		<c:set value="600" var="add_width" />
		<c:set value="480" var="add_height" />
		<c:set value="/pa/salary/deletePaInputItemParamInfo?PARAM_NO={sid}" var="delete_Url"/>
		<c:set value="/pa/salary/updatePaInputItemParamView?PARAM_NO={sid}&TABLE_NAME=PA_HR_V" var="edit_Url"/>
		<c:set value="600" var="edit_width" />
		<c:set value="480" var="edit_height" />
		<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%> --%>
	
	<table class="table" width="100%" layoutH="206">
		<thead>
			<tr>
				<th width="100"><spring:message code="pa.insurance.title.company"/><!--公司--></th>
				<th width="100"><spring:message code="pa.insurance.title.projectType"/><!--项目类型--></th>
				<th width="100"><spring:message code="pa.insurance.title.distinctName1"/><!--区分项目1:--></th>
				<th width="100"><spring:message code="pa.insurance.title.distinctName2"/><!--区分项目2:--></th>
				<th width="100"><spring:message code="pa.insurance.title.defaltValue"/><!--默认值:--></th>
				<th width="100"><spring:message code="pa.insurance.title.projectName"/><!--项目名称--></th>
				<th width="100"><spring:message code="sys.arAffirmPost.title.ableStatus"/><!--启用状态--></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${paInputItemParamList}" var="item" varStatus="i">			
				<tr target="sid" rel="${item.PARAM_NO}&CPNY_ID=${item.CPNY_ID }&PARAM_ITEM_NO=${item.PARAM_ITEM_NO }">
					<td>${item.CPNY_NAME}</td>
					<td><c:choose>
							<c:when test="${item.ITEM_TYPE == 1 }"><!--标准项目 --><spring:message code="pa.viewSalaryCodeList.BIAOZHUNXIANGMU.b" /></c:when>
							<c:when test="${item.ITEM_TYPE == 2 }"><!--支付调整项目 --><spring:message code="pa.viewSalaryCodeList.ZHIFUTIAOZHENGXIANGMU.b" /></c:when>
							<c:when test="${item.ITEM_TYPE == 3 }"><!--支付例外项目 --><spring:message code="pa.viewSalaryCodeList.ZHIFULIWAIXIANGMU.b" /></c:when>
							<c:when test="${item.ITEM_TYPE == 4 }"><!--扣除调整项目 --><spring:message code="pa.viewSalaryCodeList.KOUCHUTIAOZHENGXIANGMU.b" /></c:when>
							<c:when test="${item.ITEM_TYPE == 5 }"><!--扣除例外项目 --><spring:message code="pa.viewSalaryCodeList.KOUCHULIWAIXIANGMU.b" /></c:when>
							<c:when test="${item.ITEM_TYPE == 6 }"><!--计算项目 --><spring:message code="pa.viewSalaryCodeList.JISUANXIANGMU.b" /></c:when>
						</c:choose></td>
					<td>${item.DISTINCT_FIELD_NAME}</td>
					<td>${item.DISTINCT_FIELD_2ND_NAME}</td>
					<td>${item.DEFAULT_VAL}</td>
					<td>${item.ALIAS_NAME}</td>
					<td class='td_center'>
						<c:if test="${item.ACTIVITY eq 1 }"><spring:message code="sys.arAffirmPost.title.able"/><!--启用--></c:if>
						<c:if test="${item.ACTIVITY ne 1 }"><spring:message code="sys.arAffirmPost.title.enable"/><!--不启用--></c:if>
					</td>
				</tr>			
			</c:forEach>
		</tbody>
	</table>
	<c:set value="/pa/salary/viewPaInputItemParam" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>