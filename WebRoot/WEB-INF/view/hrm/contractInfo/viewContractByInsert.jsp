<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script type="text/javascript">
function CheckFormContractByInsert(form,navTabId){
	var $form=$(form);

	return true;
}
function doContractByInsertExport(from){
  	var $from =$(from);
  	var url ="/hrm/contractInfo/viewContractByInsertForSearchExcel";
  	window.location =url+ (url.indexOf('?') == -1 ? "?" : "&") + $from.serialize();
}
function expContractByInsert(a,navTabId){
  	var $this = $(a);
  	var title = $this.attr("title");
  	var $from = $("#contractByInsert");
  	if(CheckFormContractByInsert($from,navTabId)){
	     alertMsg.confirm(title, {okCall: function(){ doContractByInsertExport($from);}});
    } 
}
</script>

<div class="pageHeader">
	<form id="contractByInsert" onsubmit="return navTabSearch(this);" action="/hrm/contractInfo/viewContractByInsert" method="post" rel="pagerForm">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td><!-- 部门： -->
					<spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/>
					<ait:deptTree name="seach_DEPTNO" limit="hr" selected="${DEPTNO}" /> 
				</td>
				<td><!-- 社号|姓名： -->
					<spring:message code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME"/>
					<input type="text" name="seach_KEY" value="${KEY}" />
				</td>
				<%--
				<td><!-- 排序： -->
					<spring:message code="hr.viewPersonalInfo.title.ORDERTYPE"/>
					<select id="seach_orderType" name="seach_orderType">
						<option value=""><spring:message code="hr.viewPersonalInfo.title.ADDED_BY_KELI"/><!-- 请选择 --></option>
						<c:forEach items="${sortNameNoList}" var="sort">
							<option value="${sort.SORT_COLUMN_ID }" <c:if test="${sort.SORT_COLUMN_ID==orderType }">selected</c:if>>${sort.SORT_NAME }</option>
						</c:forEach>
					</select>
				</td>
				<td><!--升/降序：-->
					<spring:message code="hr.viewPersonalInfo.title.ASCORDESC"/>
					<input id="sortForSign"  name="sortForSign" <c:if test="${sortOrGradeDownForSign=='sort'}">checked="checked"</c:if> type="checkbox" onclick="document.getElementById('gradeDownForSign').checked=this.checked?false:true;document.getElementById('seach_sortOrGradeDownForSign').value='sort';" /> 
					<input id="gradeDownForSign" name="gradeDownForSign" <c:if test="${sortOrGradeDownForSign=='gradeDown'}">checked="checked"</c:if> type="checkbox" onclick="document.getElementById('sortForSign').checked=this.checked?false:true;document.getElementById('seach_sortOrGradeDownForSign').value='gradeDown';" />
					<input id="seach_sortOrGradeDownForSign" name="seach_sortOrGradeDownForSign" type="hidden" value="${sortOrGradeDownForSign }">
				</td>
				--%>
			</tr>
		</table>
		<div class="subBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="submit"><spring:message code="public.title.search"/><!-- 检索 --></button>
						</div>
					</div>
				</li>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="button" onclick="expContractByInsert(this)" title="<spring:message code='rp.report.title.exportYN'/>">
								<%--导出Excel--%><spring:message code="ar.addempshift.title.excelexport"/>
							</button>
						</div>
					</div>						
				</li>
			</ul>
		</div>
	</div>
	</form>
</div>

<div class="pageContent">
	<c:set value="600" var="add_width" />
	<c:set value="550" var="add_height" />
	<c:set value="dialog" var="add_tab"/>
	<c:set value="/hrm/contractInfo/updateContractByInsert?NO={sid}" var="add_Url"/>
	<%@ include file="/WEB-INF/view/inc/includeButton1.jsp"%>
<script type="text/javascript">
  function DbAdd(){
      <c:if test="${toolbarInfo.INSERTR == '1'}"> document.getElementById("trDBadd_hr0302").click(); </c:if> 
  }
</script>
	
	<table class="table" width="101.8%" layoutH="160">
		<thead>
			<tr>
				<th width="100"><!-- 社号-->
					<spring:message code="hr.viewPersonalInfo.title.EMPID"/>
				</th>
				<th width="100"><!-- 姓名-->
					<spring:message code="hr.viewPersonalInfo.title.LOCAL_NAME"/>
				</th>
				<th width="100"><!-- 部门-->
					<spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/>
				</th>
				<th width="100"><!-- 详细人力区分 -->
					<spring:message code="liang.hr.viewPersonalInfo.title.PARTICULAR_HUMAN_DISTINGUISH"/>
				</th>
				<th width="100"><!-- 试用与否-->
					<spring:message code="liang.hr.viewPersonalInfo.title.WHETHER_OR_NOT_TO_TRY"/>
				</th>
				<th width="100"><!-- 预转正日期 -->
					<spring:message code="hr.viewPersonalInfo.title.BEFORE_END_PROBATION_DATE" />
				</th>
				<th width="100"><!-- 职位-->
					<spring:message code="hr.viewPersonalInfo.title.POSITION_NAME"/>
				</th>
				<th width="100"><!-- 职级名称-->
					<spring:message code="hr.viewPersonalInfo.title.POST_NAME"/>
				</th>
				
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${itemList}" var="item" varStatus="i">
				<tr target="sid" rel="${item.PERSON_ID}&LOCAL_NAME=${item.LOCAL_NAME}&DEPARTMENT_NAME=${item.DEPARTMENT_NAME}&EMP_TYPE_CODE=${item.EMP_TYPE_CODE}"  onDblClick="DbAdd();">
					<td style="text-align:left">${item.EMPID}   </td>
					<td style="text-align:left">${item.LOCAL_NAME}</td>
					<td style="text-align:left">${item.DEPARTMENT_NAME}</td>
					<td style="text-align:left">${item.EMP_TYPE_CODE }</td>
					<td style="text-align:left">${item.IN_THE_DIFFERENCE }</td>
					<td class="td_center">${item.BEFORE_END_PROBATION_DATE }</td>
					<td style="text-align:left">${item.POSITION_NAME}</td>
					<td style="text-align:left">${item.POST_NAME}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<c:if test="${toolbarInfo.INSERTR == '1'}">
		 <c:if test="${add_Url ne '' && add_Url ne null}">
		       <a id="trDBadd_hr0302" class="add" href="${add_Url}"
					<c:if test="${add_target_exit eq '' || add_target_exit eq null }">
						target="${add_tab eq '' || add_tab eq null ? 'dialog' : add_tab}" 
					</c:if>
					mask="${add_mask eq '' || add_mask eq null ? 'true' : add_mask }" 
					width="${add_width eq '' || add_width eq null ? '800' : add_width}" 
					height="${add_height eq '' || add_height eq null ? '400' : add_height}"
					<c:if test="${add_rel ne '' && add_rel ne null}">
						rel="${add_rel}"
					</c:if>
				></a>
		 </c:if>
	</c:if>
	<c:set value="/hrm/contractInfo/viewContractByInsert" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>
