<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">

</script>
<div class="pageHeader">
	<form id="viewSalaryCodeListForm" onsubmit="return navTabSearch(this);"
		action="/pa/salarycode/viewSalaryCodeList" method="post" rel="pagerForm">
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
					<li>
						<a class="buttonActive"  href="/pa/salarycode/addSalaryCodeView" target="dialog" mask="true" 
							width="800" 
							height="400">
							<span><!--添加 --><spring:message code="ess.empInfo.insert" /></span>
						</a>
					</li>
					<!-- <li>
						<a class="buttonActive" href="/pa/salarycode/updateSalaryCodeView?NO={ITEM_NO}" target="dialog" mask="true" 
							width="800" 
							height="400">
							<span>修改</span>有问题功能
						</a>
					</li> -->
				</ul>
			</div>
		</div>
</div>	

<div class="pageContent">

	<table class="table" width="100%" layoutH="206">
		<thead>
			<tr>
			    <th > 
					<!--序号 --><spring:message code="sys.affirm.title.indexNum" />
				</th>
			    <th > 
					<spring:message code="pa.insurance.title.projectType"/><!--项目类型-->
				</th>
				<th >
					<spring:message code="pa.insurance.title.projectID"/><!--项目ID-->
				</th>
				<th >
					<spring:message code="pa.insurance.title.projectName"/><!--项目名称-->
				</th>
				<th >
					<spring:message code="pa.insurance.title.dataType"/><!--数据类型-->
				</th>
				<th >
					<spring:message code="pa.insurance.title.description"/><!--描述-->
				</th>
				<th >
					<!--创建者 --><spring:message code="ar.viewattendencekeeper.title.chuangjianzhe" />
				</th>
				<th >
					<!--创建时间 --><spring:message code="ar.excelexport.title.createdate" />
				</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${salaryCodeList}" var="item" varStatus="i">
				<tr target="ITEM_NO" rel="${item.ITEM_NO}&ITEM_TYPE=${item.ITEM_TYPE }">
				    <td >
						${i.count}
					</td>
				    <td >
						<c:choose>
							<c:when test="${item.ITEM_TYPE == 1 }"><!--标准项目 --><spring:message code="pa.viewSalaryCodeList.BIAOZHUNXIANGMU.b" /></c:when>
							<c:when test="${item.ITEM_TYPE == 2 }"><!--支付调整项目 --><spring:message code="pa.viewSalaryCodeList.ZHIFUTIAOZHENGXIANGMU.b" /></c:when>
							<c:when test="${item.ITEM_TYPE == 3 }"><!--支付例外项目 --><spring:message code="pa.viewSalaryCodeList.ZHIFULIWAIXIANGMU.b" /></c:when>
							<c:when test="${item.ITEM_TYPE == 4 }"><!--扣除调整项目 --><spring:message code="pa.viewSalaryCodeList.KOUCHUTIAOZHENGXIANGMU.b" /></c:when>
							<c:when test="${item.ITEM_TYPE == 5 }"><!--扣除例外项目 --><spring:message code="pa.viewSalaryCodeList.KOUCHULIWAIXIANGMU.b" /></c:when>
							<c:when test="${item.ITEM_TYPE == 6 }"><!--计算项目 --><spring:message code="pa.viewSalaryCodeList.JISUANXIANGMU.b" /></c:when>
						</c:choose>
					</td>
					<td>
						${item.ITEM_ID}
					</td>
					<td>
						${item.ITEM_NAME}
					</td>
					<td>
						<c:if test="${item.DATA_TYPE eq 'NUMBER(14,4)' }">
						<spring:message code="pa.insurance.title.numberType"/><!--数字类型--></c:if>
						<c:if test="${item.DATA_TYPE eq 'VARCHAR(100)' }">
						<spring:message code="pa.insurance.title.varcharType"/><!--字符型--></c:if>
					</td>
					<td>
						${item.DESCR}
					</td>
					<td>
						${item.CREATED_BY}
					</td>
					<td>
						${item.CREATE_DATE}
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</form>
	<c:set value="/pa/salarycode/viewSalaryCodeList" var="pageUrl" />
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>