<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function qufenAndNameCheng(obj){
	if(obj == null || obj.length == 0){
		return ;
	}
	$("#importItemDiv").hide();
	var sel = $("#seach_PARAM_NO",navTab.getCurrentPanel());//项目区分
	sel.empty();
	$.ajax({
		 cache: false,
		 type: 'post',
		 async:false,
		 url: "/pa/salary/getqufenAndNameCheng?",
		 data: 'seach_ITEM_DISTINGUISH=' + obj,
		 dataType:"json",
		 success: function(data) {
		 sel.append("<option value=''><spring:message code='hr.viewPersonalInfo.title.ADDED_BY_KELI' /><!--请选择--> </option>"); 
			$.each(data, function(key,value){
					if($(data).size() > 0){
 							sel.append('<option value='+key+'>'+value+'</option>'); 
					}
			});
		 }
	});
}
function pageFromSea(a){
	//$("#"+imgValue,navTab.getCurrentPanel())[0].src;
	var seach_PARAM_NO=$("#seach_PARAM_NO",navTab.getCurrentPanel()).val();
	$("#pagerForm",navTab.getCurrentPanel()).attr("action", "/pa/salary/viewItemBatchImport?seach_PARAM_NO="+seach_PARAM_NO);
}
</script>
<div id="viewItemBatchImportPa">
<div class="pageHeader">
	<form  action="/pa/salary/viewItemBatchImport?pageNum=1" method="post"  rel="pagerForm" onsubmit="return navTabSearch(this);"  id="viewItemBatchImportPaFrom" name="viewItemBatchImportPaFrom">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
			<td> <!--项目区分--><spring:message code="liang.public.title.ItemDistinguish"/></td>
				<td>
				<ait:selectSyCode name="seach_ITEM_DISTINGUISH" parentNo="506" selected="${ITEM_DISTINGUISH }" limit="all"  onChangeName="qufenAndNameCheng(this.value);" />
				</td>
				<td><!-- 项目名称--><spring:message code="liang.public.title.ItemName"/></td>
				<td>
						<select name="seach_PARAM_NO" id="seach_PARAM_NO">
							<option value=""><spring:message code="pa.insurance.title.pleaseChoose"/><!--请选择:--></option>
							<c:forEach items="${getItemNameList}" var="item">
								<option <c:if test="${PARAM_NO eq item.PARAM_NO }"> selected</c:if> value="${item.PARAM_NO}">${item.ALIAS_NAME}</option>
							</c:forEach>
						</select>
				</td>
				 <td>
					<spring:message code="inct.salesman.daqu"/>
					<!-- 部门： -->
				</td>	
				<td>
					<%-- <ait:deptTree name="seach_DEPTNO" limit="pa" selected="${DEPTNO }"/> --%>
					<select name="seach_DEPTNO" >
								<option value="">select</option>
							     <c:forEach items="${deptList}" var="vlist" varStatus="i">
								      <option value="${vlist.DEPTNO}" <c:if test="${vlist.DEPTNO eq DEPTNO}">selected</c:if>>${vlist.DEPTNAME}</option>
							    </c:forEach>
						</select>
				</td>
				
			</tr>
			<tr>
			   <td>
			      <spring:message code="zxc.pa.insurance.title.BASE_MONTH"/><!--基准月-->
			   </td>
			   <td>
				  <ait:date yearName="seach_paYear" monthName="seach_paMonth" yearSelected="${paYear}" 
														monthSelected="${paMonth}" limit="all"/>
		      </td>
		      <td>
					<spring:message code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME"/>
					<!--工号/姓名：-->
				</td>
				<td>
					<input type="text" name="seach_EMPID" value="${EMPID}" />
				</td>
				<td>
					是否删除 
				</td>
				
				<td>
					<select  id="seach_UP_FLAG"  name="seach_UP_FLAG" >
								<option value="" <c:if test="${ seach_UP_FLAG eq ''}">selected</c:if>>所有</option>
								<option value="Y"<c:if test="${ seach_UP_FLAG eq 'Y'}">selected</c:if>>未删除</option>
								<option value="N"<c:if test="${ seach_UP_FLAG eq 'N'}">selected</c:if>>已删除</option>
					</select>
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
	
</div>
<div class="pageContent">
   <div id="importItemDiv">
	<c:if test="${ITEM_DISTINGUISH ne null }">
		<c:if test="${ITEM_DISTINGUISH eq 507 }"> <!-- 基本项目导入 -->
			<div class="formBar">
				<ul>
					<li>                                              
						<a class="buttonActive" href="/pa/excelImport/importInsuranceInputItemData?id=${PARAM_NO}&CPNY_ID=${CPNY_ID}
							&importFunName=importItemBatchImportExcelIsNotNullPaBasis" target="dialog" mask="true" width="500" height="200" >
							<span><spring:message code="ar.addempshift.title.excelimport"/><!-- EXCEL导入 --></span>
						</a>
					</li>
					<li>
						<a class="buttonActive"
							href="/pa/excelExport/itemBatchImportExcelIsNotNullModulePaBasis?id=${PARAM_NO}&CPNY_ID=${CPNY_ID}&seach_ITEM_DISTINGUISH=${ITEM_DISTINGUISH}&type=507"  ><span>
							<spring:message code="pa.insurance.title.downloadImportTemplate"/><!--下载导入模板--></span>
						</a>
					</li>
				</ul>
			</div>
		</c:if>
		<c:if test="${ITEM_DISTINGUISH eq 508 }"><!-- 输入项目导入 -->
			<div class="formBar">
				<ul>
					<li>
						<a class="buttonActive" href="/pa/excelImport/importInsuranceInputItemData?id=${PARAM_NO}&CPNY_ID=${CPNY_ID}
							&importFunName=importItemBatchImportExcelIsNotNullPa" target="dialog" mask="true" width="500" height="200" >
							<span><spring:message code="ar.addempshift.title.excelimport"/><!-- EXCEL导入 --></span>
						</a>
					</li>
					<li>
						<a class="buttonActive"
							href="/pa/excelExport/itemBatchImportExcelIsNotNullModulePa?id=${PARAM_NO}&CPNY_ID=${CPNY_ID}&seach_ITEM_DISTINGUISH=${ITEM_DISTINGUISH}&type=508"><span>
							<spring:message code="pa.insurance.title.downloadImportTemplate"/><!--下载导入模板--></span>
						</a>
					</li>
				</ul>
			</div>
		</c:if>
	</c:if>
   </div>
	</form>
	<from  action="/pa/salary/viewItemBatchImport" method="post"  rel="pagerForm" onsubmit="return navTabSearch(this);"  >
		<c:if test="${ITEM_DISTINGUISH ne null }">
			<c:if test="${ITEM_DISTINGUISH eq 507 }"> <!-- 基本项目导入 -->
				<table class="table" width="99%" layoutH="195">
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
								<th width="50">
									<spring:message code="public.title.startDate"/><!-- 开始日期 -->
								</th>
								<th width="50">
									<spring:message code="public.title.endDate"/><!-- 结束日期 -->
								</th>
								<th width="50"><!--数值-->
									<spring:message code="pa.insurance.title.dataValue"/>
								</th>
								<th width="20"><!--备注-->
									<spring:message code="hr.viewPromote.title.REMARK"/>
								</th>
								<th width="20">
							 是否删除
								</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${insuranceItemDataList}" var="itemData" varStatus="i">
								<tr target="PARAM_DATA_NO" rel="${itemData.PARAM_DATA_NO}">
									<td>${i.count }</td>
									<td>${itemData.ALIAS_NAME}</td>

									<td  class="td_center" style="text-align: center" ><a rel="ItemBatchData" 
									href="/pa/salary/updateItemBatchDataView?DATA_NO=${itemData.DATA_NO}
									&&ITEM_DISTINGUISH=507" title="数据修改" target="dialog" 
									mask="true" width="550" height="450" id="annApplyRemarkHref" >${itemData.EMPID}</a>
									</td>
									<td class="td_center"> ${itemData.LOCAL_NAME}</td>
									<td class="td_center">${itemData.DEPT_NAME}</td>
									<td class="td_center">${itemData.START_DATE}</td>
									<td class="td_center">${itemData.END_DATE}</td>
									<td class="td_center">
										<c:if test="${empty itemData.RETURN_VALUE}">
											0
										</c:if>
										<c:if test="${not empty itemData.RETURN_VALUE}">
											${itemData.RETURN_VALUE}
										</c:if>
									</td>
									<td>${itemData.REMARK}</td>
									<td class="td_center">
									 
										<c:if test="${itemData.UP_FLAG eq 'Y'}">
											未删除
										</c:if>
										<c:if test="${itemData.UP_FLAG eq 'N'}">
											已删除
										</c:if>
									</td>
								</tr>
							</c:forEach>
						</tbody>
				</table>
			</c:if>
		</c:if>
		<c:if test="${ITEM_DISTINGUISH ne null }">
			<c:if test="${ITEM_DISTINGUISH eq 508 }"> <!-- 输入项目导入 -->
				<table class="table" width="99%" layoutH="195">
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
								<th width="20">
									是否删除
								</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${insuranceItemDataList}" var="itemData" varStatus="i">
								<tr target="PARAM_DATA_NO" rel="${itemData.PARAM_DATA_NO}">
									<td>${i.count }</td>
									<td class="td_center">${itemData.ALIAS_NAME}</td>
									<td  class="td_center" style="text-align: center" ><a rel="ItemBatchData" 
									href="/pa/salary/updateItemBatchDataView?DATA_NO=${itemData.DATA_NO}
									&&ITEM_DISTINGUISH=508" title="数据修改" target="dialog" 
									mask="true" width="550" height="450" id="annApplyRemarkHref" >${itemData.EMPID}</a>
									<td class="td_center">${itemData.LOCAL_NAME}</td>
									<td class="td_center">${itemData.DEPT_NAME}</td>
									<td class="td_center">${itemData.START_MONTH}</td>
									<td class="td_center">${itemData.END_MONTH}</td>
									<td class="td_center">
										<c:if test="${empty itemData.RETURN_VALUE}">
											0
										</c:if>
										<c:if test="${not empty itemData.RETURN_VALUE}">
											${itemData.RETURN_VALUE}
										</c:if>
									</td>
									
									<td>${itemData.REMARK}</td>
									<td class="td_center">
										<c:if test="${itemData.UP_FLAG eq 'Y'}">
											未删除
										</c:if>
										<c:if test="${ itemData.UP_FLAG eq 'N'}">
											已删除
										</c:if>
									</td>
								</tr>
							</c:forEach>
						</tbody>
				</table>
			</c:if>
		</c:if>
	</from>
	
	<c:if test="${ITEM_DISTINGUISH eq null or ITEM_DISTINGUISH eq ''}">
	<table class="table" width="99%" layoutH="195">
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
								<th width="50">
									<spring:message code="public.title.startDate"/><!-- 开始日期 -->
								</th>
								<th width="50">
									<spring:message code="public.title.endDate"/><!-- 结束日期 -->
								</th>
								<th width="50"><!--数值-->
									<spring:message code="pa.insurance.title.dataValue"/>
								</th>
								<th width="20"><!--备注-->
									<spring:message code="hr.viewPromote.title.REMARK"/>
								</th>
								<th width="20">
									是否删除
								</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${insuranceItemDataList}" var="itemData" varStatus="i">
								<tr target="PARAM_DATA_NO" rel="${itemData.PARAM_DATA_NO}">
									<td>${i.count }</td>
									<td>${itemData.ALIAS_NAME}</td>
									<td  class="td_center" style="text-align: center" ><a rel="ItemBatchData" 
									href="/pa/salary/updateItemBatchDataView?DATA_NO=${itemData.DATA_NO}
									&&ITEM_DISTINGUISH=${itemData.ITEM_DISTINGUISH}" title="数据修改" target="dialog" 
									mask="true" width="550" height="450" id="annApplyRemarkHref" >${itemData.EMPID}</a>
									<td class="td_center"> ${itemData.LOCAL_NAME}</td>
									<td class="td_center">${itemData.DEPT_NAME}</td>
									<td class="td_center">${itemData.START_DATE}</td>
									<td class="td_center">${itemData.END_DATE}</td>
									<td class="td_center">
										<c:if test="${empty itemData.RETURN_VALUE}">
											0
										</c:if>
										<c:if test="${not empty itemData.RETURN_VALUE}">
											${itemData.RETURN_VALUE}
										</c:if>
									</td>
									
									<td>${itemData.REMARK}</td>
									<td class="td_center">
										<c:if test="${itemData.UP_FLAG eq 'Y'}">
											未删除
										</c:if>
										<c:if test="${ itemData.UP_FLAG eq 'N'}">
											已删除
										</c:if>
									</td>
								</tr>
							</c:forEach>
						</tbody>
				</table>
		<!--  <div class="panelBar">
			<div class="pages">
				<span><spring:message code="public.title.view"/></span>
					<select class="combox" name="numPerPage" onchange="pageFromSea('${totalCount}');navTabPageBreak({numPerPage:this.value},'viewItemBatchImportPa')">
						<option value="10"  <c:if test="${numPerPage == 10 }" >selected</c:if> >10</option>
						<option value="20"  <c:if test="${numPerPage == 20 }" >selected</c:if> >20</option>
						<option value="30"  <c:if test="${numPerPage == 30 }" >selected</c:if> >30</option>
					</select>
				<span><spring:message code="public.title.tiao"/>,<spring:message code="public.title.gong"/>
				${totalCount}<spring:message code="public.title.tiao"/></span>	
			</div>
			<div class="pagination" rel="viewItemBatchImportPa" totalCount="${totalCount}" numPerPage="${numPerPage}" currentPage="${pageNum}"></div>
		</div>-->
	</c:if>
	<!-- 分页 -->
	<c:if test="${ITEM_DISTINGUISH ne null or ITEM_DISTINGUISH ne ''}">
	
	<c:set value="/pa/salary/viewItemBatchImport?seach_PARAM_NO=${PARAM_NO}&seach_CPNY_ID=${CPNY_ID}" var="pageUrl" />
	
	<!--  <form id="pagerForm" method="post" action="${pageUrl}">
			<input type="hidden" name="pageNum" value="${pageNum}" />
			<input type="hidden" name="numPerPage" value="${numPerPage}" />
		</form>-->
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
	</c:if>
</div>
</div>