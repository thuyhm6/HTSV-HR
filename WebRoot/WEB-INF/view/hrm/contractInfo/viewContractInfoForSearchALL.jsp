<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function CheckFormContractSearch(form,navTabId){
	var $form=$(form);
	
	<%--var sapYear = $form.find("#seach_sapYear").val();
	
	if(sapYear == ''){
		//alert("工资年份为必选项，请选择年份！");
		alertMsg.error("<spring:message code='pa.message.pa.check.payearmustchoosed'/>");
		$form.find("#seach_sapYear").focus();
		return false;
	}--%>
	
    return true;
}
function doContractInfoExport(from){
  	var $from =$(from);
  	var url ="/hrm/contractInfo/viewContractInfoForSearchALLExcel";
  	window.location =url+ (url.indexOf('?') == -1 ? "?" : "&") + $from.serialize();
}
function expContractInfo(a,navTabId){
  	var $this = $(a);
  	var title = $this.attr("title");
  	var $from = $("#viewContractInfoForSearchALL");
  	if(CheckFormContractSearch($from,navTabId)){
	     alertMsg.confirm(title, {okCall: function(){ doContractInfoExport($from);}});
    } 
}

function submitForm(){
  	var $form = $("#viewContractInfoForSearchALL");
  	$form.submit();
}

/*
$(document).ready(function(){
	$("#EMP_OFFICE").click(function(){
		if(this.checked == true){
			this.value=15120;//"15120"表示离职
		}else{
			this.value=0;//"0"表示在职和休职
		}
	});
});*/
</script>
<div class="pageHeader">
	<form id="viewContractInfoForSearchALL" onsubmit="return navTabSearch(this);" action="/hrm/contractInfo/viewContractInfoForSearchALL" method="post" rel="pagerForm" >
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td ><!-- 法人 -->
					<spring:message code="sys.essParam.title.legalPerson"/>
				</td>
				<td >
					<ait:SyCompany target="login" name="seach_COMPANY_ID" selected="${defaultCpny}"
					language="zh" limit="ALL" activity="1" onChangeName="submitForm();"/>
				</td>
				<td width='62%'>&nbsp;</td>
				<td>
					<div class="subBar">
						<ul>
							<li>
								<div class="buttonActive">
									<div class="buttonContent">
										<button type="submit">
											<spring:message code="public.title.search"/><!-- 检索  -->
										</button>
									</div>
								</div>
							</li>
							<li>
								<div class="buttonActive">
									<div class="buttonContent">
										<button type="button" onclick="expContractInfo(this)" title="<spring:message code='rp.report.title.exportYN'/>"><%--导出Excel--%>
											<spring:message code="ar.addempshift.title.excelexport"/>
										</button>
									</div>
								</div>						
							</li>
						</ul>
					</div>
				</td>
			</tr>
		</table>
	</div>
	</form>
</div>

<div class="pageContent">
	<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>

	<table class="table" width="101.8%" layoutH="170">
		<thead>
			<tr>
				<th >NO</th>
				<th><!-- 法人 --><spring:message code="sys.essParam.title.legalPerson"/></th>
				<th><!-- 社号 --><spring:message code="public.title.empId"/></th>
				
				<th><!-- 姓名 --><spring:message code="public.title.name"/></th>
				<th><!-- 部门 --><spring:message code="public.title.deptName"/></th>
				<th><!-- 职位 --><spring:message code="public.title.positionName"/></th>
				<th><!-- 职级名称(职务) --><spring:message code="public.title.postName"/></th>
				<th><!--合同开始日--> <spring:message code="zxc.hr.contract.CONTRACT_START_DATE"/></th>
				<th><!-- 合同到期日 --><spring:message code="zxc.hr.contract.CONTRACT_END_DATE"/></th>
				<th><!-- 倒计天数 --><spring:message code="main.home.message.daojitianshu"/></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${itemList}" var="contractInfo" varStatus="i">
				<tr >
					<td  style="text-align: center">${i.count}</td>
					<td style="text-align: center">${contractInfo.CPNY_NAME}</td>
					<td style="text-align: center">${contractInfo.EMPID}</td>
					<td>${contractInfo.LOCAL_NAME}</td>
					<td>${contractInfo.DEPARTMENT_NAME}</td>
					<td>${contractInfo.POSITION_NAME}</td>
					<td>${contractInfo.POST_NAME}</td>
					<td style="text-align: center">${contractInfo.START_CONTRACT_DATE}</td>
					<td style="text-align: center">${contractInfo.END_CONTRACT_DATE}</td>
					<td style="text-align: center" >${contractInfo.DAYS}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	
	<c:set value="/hrm/contractInfo/viewContractInfoForSearchALL" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
	
</div>
