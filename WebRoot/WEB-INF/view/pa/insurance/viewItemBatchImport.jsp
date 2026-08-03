<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
function pageFromSea(a){
	//$("#"+imgValue,navTab.getCurrentPanel())[0].src;
	var seach_PARAM_NO=$("#seach_PARAM_NO",navTab.getCurrentPanel()).val();
	$("#pagerForm",navTab.getCurrentPanel()).attr("action", "/pa/insurance/viewItemBatchImport?seach_PARAM_NO="+seach_PARAM_NO);
}
</script>
<div id="viewItemBatchImport">
<div class="pageHeader">
	<form  action="/pa/insurance/viewItemBatchImport?pageNum=1" method="post"  rel="pagerForm" onsubmit="return navTabSearch(this);"  id="viewItemBatchImportFrom" name="viewItemBatchImportFrom">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td><!-- 项目名称--><spring:message code="liang.public.title.ItemName"/></td>
				<td>
						<select id="seach_PARAM_NO" name="seach_PARAM_NO">
							<option value=""><spring:message code="pa.insurance.title.pleaseChoose"/><!--请选择:--></option>
							<c:forEach items="${getItemNameList}" var="item">
								<option <c:if test="${PARAM_NO eq item.PARAM_NO }"> selected</c:if> value="${item.PARAM_NO}">${item.ALIAS_NAME}</option>
							</c:forEach>
						</select>
				</td>
				 <td>
					<spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/>
					<!-- 部门： -->
				</td>	
				<td>
					<ait:deptTree name="seach_DEPTNO" limit="hr" selected="${DEPTNO }"/>
				</td>
				<td>
					<spring:message code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME"/>
					<!--工号/姓名：-->
				</td>
				<td>
					<input type="text" name="seach_EMPID" value="${EMPID}" />
					<input type="hidden" id="seach_FIRST_FLAG" name="seach_FIRST_FLAG" value="1" />
				</td>	
			</tr>
			</table>
			<div class="subBar">
							<ul>
								<li><div class="buttonActive"><div class="buttonContent"><button type="submit">
								<spring:message code="public.title.search"/><!--检索--></button></div></div></li>
							</ul>
						</div>
	</div>
	
	</form>
</div>
<div class="pageContent" >
	<from  action="/pa/insurance/viewItemBatchImport" method="post"  rel="pagerForm" onsubmit="return navTabSearch(this);"  >
	<div class="formBar">
		<ul>
			<li>
				<a class="buttonActive" href="/pa/excelImport/importInsuranceInputItemData?id=${PARAM_NO}&CPNY_ID=${CPNY_ID}
					&importFunName=importItemBatchImportExcelIsNotNull" target="dialog" mask="true" width="500" height="200" >
					<span><spring:message code="ar.addempshift.title.excelimport"/><!-- EXCEL导入 --></span>
				</a>
			</li>
			<li>
				<a class="buttonActive"
					href="/pa/excelExport/itemBatchImportExcelIsNotNullModule?id=${PARAM_NO}&CPNY_ID=${CPNY_ID}"  ><span>
					<spring:message code="pa.insurance.title.downloadImportTemplate"/><!--下载导入模板--></span>
				</a>
			</li>
		</ul>
	</div>
		<table class="table" width="99%" layoutH="206">
			<thead>
					<tr>
						<th width="5%"><spring:message code="sys.affirm.title.indexNum"/><!-- 序号 --></th>
						<th width="50"><!--项目名称-->
							<spring:message code="liang.public.title.ItemName"/>
						</th>
						<th width="50"><!--工号-->
							<spring:message code="public.title.empId"/>
						</th>
						<th width="50"><!--姓名-->
							<spring:message code="public.title.name"/>
						</th>
						<th width="50"><!--部门-->
							<spring:message code="public.title.deptName"/>
						</th>
						<th width="50"><!--开始月-->
							<spring:message code="pa.insurance.title.startMonth"/>
						</th>
						<th width="50"><!--结束月-->
							<spring:message code="pa.insurance.title.endMonth"/>
						</th>
						<th width="50"><!--数值-->
							<spring:message code="pa.insurance.title.dataValue"/>
						</th>
						<th width="20"><!--备注-->
							<spring:message code="hr.viewPromote.title.REMARK"/>
						</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${insuranceItemDataList}" var="itemData" varStatus="i">
						<tr target="PARAM_DATA_NO" rel="${itemData.PARAM_DATA_NO}">
							<td>${i.count }</td>
							<td class='td_center'>${itemData.ALIAS_NAME}</td>
							<td class='td_center'>${itemData.EMPID}</td>
							<td class='td_center'>${itemData.LOCAL_NAME}</td>
							<td class='td_center'>${itemData.DEPT_NAME}</td>
							<td class='td_center'>${itemData.START_MONTH}</td>
							<td class='td_center'>${itemData.END_MONTH}</td>
							<td>
								<c:if test="${empty itemData.RETURN_VALUE}">
									0
								</c:if>
								<c:if test="${not empty itemData.RETURN_VALUE}">
									${itemData.RETURN_VALUE}
								</c:if>
							</td>
							<td>${itemData.REMARK}</td>
						</tr>
					</c:forEach>
				</tbody>
		</table>
	</from>
	<!-- 分页 -->
	<c:set value="/pa/insurance/viewItemBatchImport?seach_PARAM_NO=${PARAM_NO}&seach_CPNY_ID=${CPNY_ID}" var="pageUrl" />
	<form id="pagerForm" method="post" action="${pageUrl}">
		<input type="hidden" name="pageNum" value="${pageNum}" />
		<input type="hidden" name="numPerPage" value="${numPerPage}" />
	</form>
	<div class="panelBar">
		<div class="pages">
			<span><spring:message code="public.title.view"/><!-- 显示 --></span>
				<select class="combox" name="numPerPage" onchange="pageFromSea('${totalCount}');navTabPageBreak({numPerPage:this.value},'viewItemBatchImport')">
					<option value="10"  <c:if test="${numPerPage == 10 }" >selected</c:if> >10</option>
					<option value="20"  <c:if test="${numPerPage == 20 }" >selected</c:if> >20</option>
					<option value="30"  <c:if test="${numPerPage == 30 }" >selected</c:if> >30</option>
					<option value="1000"  <c:if test="${numPerPage == 1000 }" >selected</c:if> >1000</option>
				</select>
			<span><spring:message code="public.title.tiao"/><!--条-->,<spring:message code="public.title.gong"/><!--共-->
			${totalCount}<spring:message code="public.title.tiao"/><!--条--></span>	
		</div>
		<div class="pagination" rel="viewItemBatchImport" totalCount="${totalCount}" numPerPage="${numPerPage}" 
		     currentPage="${pageNum}"></div>
	</div>
</div>
