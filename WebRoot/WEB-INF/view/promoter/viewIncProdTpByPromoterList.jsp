<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<c:set var="base" value="${pageContext.request.contextPath}" />

<script type="text/javascript">
function CheckForm(form,navTabId){
	var $form=$(form);

	return true;
}
function doIncProdTpByPromoterListExport(from){
  	var $from =$(from);
  	var url ="${base}/promoter/viewIncProdTpByPromoterListExcel";
  	window.location =url+ (url.indexOf('?') == -1 ? "?" : "&") + $from.serialize();
}
function expIncProdTpByPromoterList(a,navTabId){
  	var $this = $(a);
  	var title = $this.attr("title");
  	var $from = $("#viewIncProdTpByPromoter");
  	if(CheckForm($from,navTabId)){
	     alertMsg.confirm(title, {okCall: function(){ doIncProdTpByPromoterListExport($from);}});
    } 
}

</script>

<div class="pageHeader">
	<form id="viewIncProdTpByPromoter" onsubmit="return navTabSearch(this);" action="/promoter/viewIncProdTpByPromoterList" method="post" rel="pagerForm">
	<div class="searchBar">
	    <table class="searchContent">
			<tr>
				<td><!-- 大区： -->
					大区
				</td>
				<td>
					<ait:ComboDeptByCpnyIDTag id="seach_PAY_AREA_CD" name="seach_PAY_AREA_CD" parentNo="198659" selected="${PAY_AREA_CD}" cnpyID="${defaultCpny}" limit="all"/>
				</td>
				<td><!-- 组织名称： -->
					组织名称
				</td>
				<td>
					<ait:deptTree name="seach_DEPTNO" limit="hr" selected="${DEPTNO}" />
				</td>
				<td>
					<!-- 工号 --><spring:message code="public.title.empId"/>：
				</td>
				<td>
					<input name="dwz.person.empId" type="text" value="${EMPID}" readOnly lookupGroup="person"/>
				</td>
				<td>
					<a class="btnLook" href="/ar/attendanceMintenance/viewEmpCalendarList?pageNum=1" lookupGroup="person"><!-- 查找带回 --><spring:message code="ar.alert.message.viewattendencekeeper.chazhaodaihui"/></a>
					<input type="hidden" name="dwz.person.person_id" id="person_id1" value="${person_id}" readOnly lookupGroup="person"/>
			  		<input type="hidden" name="person_id" id="person_id" value="${person_id}"/>
					<input type="hidden" name="cpny_id" id="cpny_id" value="${cpny_id}"/>
				    <input type="hidden" name="STAT_NO" id="STAT_NO" value="${STAT_NO}"/>
			 	</td>
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
							<button type="button" onclick="expIncProdTpByPromoterList(this)" title="<spring:message code='rp.report.title.exportYN'/>">
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
	<c:set value="dialog" var="edit_tab"/>
	<c:set value="450" var="edit_width"/>
	<c:set value="400" var="edit_height"/>
	<c:set value="/promoter/updateIncProdTpByPromoterView?{sid}" var="edit_Url"/>
	<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>
	<table class="table" width="100%" layoutH="206">
		<thead>
			<tr>
				<th width="100"><!-- 社号  -->
					社号
				</th>
				<th width="100"><!-- 员工姓名   -->
					员工姓名
				</th>
				<th width="100"><!-- 大区  -->
					大区
				</th>
				<th width="100"><!-- 产品类型 -->
					产品类型
				</th>
				<th width="100"><!-- 地区区分-->
					地区区分
				</th>
				<th width="100"><!-- 促销员所属 -->
					促销员所属
				</th>
				<th width="100"><!-- 工作类型 -->
					工作类型
				</th>
				<th width="100"><!-- 是否在职-->
					是否在职
				</th>
				<th width="100"><!-- 是否使用 -->
					是否使用
				</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${itemList}" var="item" varStatus="i">
				<tr target="sid" rel="SUBSD_CD=${item.SUBSD_CD}&EMPID=${item.EMPNO}">
					<td>${item.EMPNO}</td>
	                <td>${item.EMP_NM}</td>
	                <td>${item.PAY_AREA_NM}</td>
	                <td>${item.PROD_TP_NM}</td>
	                <td>${item.OFICE_AREA_NM}</td>
	                <td>${item.PROMTR_PAYMNT_TP_NM}</td>
	                <td>${item.PROMTR_WORK_TP_NM}</td>
	                <td>${item.TENU_STAT}</td>
	                <td>${item.USE_YN}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<c:set value="/promoter/viewIncProdTpByPromoterList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>
