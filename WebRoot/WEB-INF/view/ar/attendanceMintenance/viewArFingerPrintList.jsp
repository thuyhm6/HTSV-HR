<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function CheckFormFingerPrint(form,navTabId){
	var $form=$(form);
	
    return true;
}
function doFingerPrintExport(form){
  	var $form =$(form);
  	var url ="/ar/attendanceMintenance/viewArFingerPrintExcel";
  	window.location =url+ (url.indexOf('?') == -1 ? "?" : "&") + $form.serialize();
}
function expFingerPrint(a,navTabId){
  	var $this = $(a);
  	var title = $this.attr("title");
  	var $form = $("#viewArFingerPrintList");
  	if(CheckFormFingerPrint($form,navTabId)){
	     alertMsg.confirm(title, {okCall: function(){ doFingerPrintExport($form);}});
    } 
}

</script>
<div class="pageHeader" >
	<form id="viewArFingerPrintList" onsubmit="return navTabSearch(this);" action="/ar/attendanceMintenance/viewArFingerPrintList" method="post">
	<div class="searchBar" style="padding:5px;">
		<input id="seach_DATA_FLAG" name="seach_DATA_FLAG" value="1" type="hidden"/>
		<table class="searchContent">
			<tr>
				<td style="text-align: right"><!-- 部门： -->
					<spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/>：
				</td>	
				<td>
					<ait:deptTree name="seach_DEPT_NO" limit="hr" selected="${DEPT_NO }"/>
				</td>
				<td style="text-align: right"><!--工号/姓名：-->
					<spring:message code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME"/>：
				</td>
				<td>
					<input type="text" id="seach_KEY" name="seach_KEY" value="${KEY}" />
				</td>
				<td style="text-align: right">指纹ID：</td>
				<td>
					<input type="text" id="seach_FINGER_ID" name="seach_FINGER_ID" value="${FINGER_ID}" />
				</td>
				<td>&nbsp;</td>
			</tr>
		</table>
		<div class="subBar">
		 	<ul>
			 	<li>
			 		<div class="buttonActive"><div class="buttonContent"><button type="submit">
			 		&nbsp;<spring:message code="public.title.search"/><!--检索-->&nbsp;</button></div></div>
		 		</li>
		 	</ul>
		</div> 
	</div>
	</form>
</div>

<div class="pageContent">
	<div class="formBar">
		<ul class="toolBar">
			<li>
				<a class="delete" onclick="expFingerPrint()" title="确认导出？">
					<span><%--导出Excel--%><spring:message code="ar.addempshift.title.excelexport"/></span></a>
			</li>
		</ul>
	</div>
	<table class="table" width="98%" layoutH="176">
		<thead>
			<tr>
				<th width="5%" style="text-align: center">序号</th>
				<th width="10%" style="text-align: center">工号</th>
				<th width="10%" style="text-align: center">姓名</th>
				<th width="15%" style="text-align: center">指纹编号</th>
				<th width="10%" style="text-align: center">员工状态</th>
				<th width="15%" style="text-align: center">部门</th>
				<th width="15%" style="text-align: center">分店名</th>
				
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${fingerPringList}" var="finger" varStatus="i">
				<tr target="sid" rel="${finger.PERSON_ID}">
					<td style="text-align: center">${i.index+1}</td>
					<td style="text-align: center">${finger.EMPID }</td>
					<td style="text-align: center">${finger.LOCAL_NAME }</td>
					<td style="text-align: center">${finger.FINGER_ID }</td>
					<td style="text-align: center">${finger.EMP_OFFICE }</td>
					<td style="text-align: center">${finger.DEPARTMENT }</td>
					<td style="text-align: center">${finger.DISTINGUISH_NAME }</td>
					
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<c:set value="/ar/attendanceMintenance/viewArFingerPrintList?seach_DATA_FLAG=1" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>