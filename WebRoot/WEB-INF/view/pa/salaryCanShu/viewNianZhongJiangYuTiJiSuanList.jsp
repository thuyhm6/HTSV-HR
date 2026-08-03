<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
//excel导出
function exportPaInfo_pa0705() {
	var sform = document.getElementById("viewNianZhongJiangYuTiJiSuanList");
	alertMsg.confirm("Do you want to export?", {
		okCall : function() {
			//用于excel导出的表单参数处理
			var eForm = document.getElementById("excelExportForm_pa0705"); 
			document.getElementById("pa0705Link").innerHTML = "EXCEL密码设置";
			eForm.zhifunian.value 		= sform.seach_zhifunian.value;
			eForm.zhifuyue.value 			= sform.seach_zhifuyue.value;
			eForm.PAY_AREA_CD.value 		= sform.seach_PAY_AREA_CD.value;
			eForm.shehaoxingming.value 		= sform.seach_shehaoxingming.value;
			eForm.JobTypeGroupNo.value 		= sform.seach_JobTypeGroupNo.value;
			$("#importExcelDialog_pa0705").attr('href', "/sys/encryptExcel"
					+"?exportFunName=/pa/salaryCanShu/viewNianZhongJiangYuTiJiSuanExcel"
					+"&navTabId=pa0705"
					+"&formId=excelExportForm_pa0705");
			$("#importExcelDialog_pa0705").attr('width', "300");
			$("#importExcelDialog_pa0705").attr('height', "150");
			$("#importExcelDialog_pa0705").click();
		}
	});
}
function f_Calculate_viewardetailcalculate(){
	//是否开始计算?
	alertMsg.confirm("<spring:message code='ar.alert.message.viewardetailcaculate.canstart'/>", {
		okCall: function(){
			$.ajax({
				type: 'post',
				cache: false,
				contentType: 'application/json',
				url: '/pa/salaryCanShu/callNianZhongJiangYuTiJiSuanProduce',
				dataType: 'json',
				success: navTabAjaxDone || DWZ.ajaxDone
			});
		}
	});
}
</script>
<a id="importExcelDialog_pa0705" href="#" target="dialog" mask="true">
<span id="pa0705Link" style="display: none"></span></a> 
<div class="pageHeader">
	<form id="viewNianZhongJiangYuTiJiSuanList" onsubmit="return navTabSearch(this);" action="/pa/salaryCanShu/viewNianZhongJiangYuTiJiSuanList" method="post" rel="pagerForm">
	<div class="searchBar" >
		<table class="searchContent">
				<tr>
					<td>
	    				<spring:message code="pa.salary.canShu.faRen"/><!--法人-->
	    			</td>
	    			<td>
						${LoginUser.cpnyId}
	    			</td>
	    			<td>
	    				月份
	    			</td>
	    			<td>
	    				 <ait:dateProMonth yearName="seach_zhifunian" yearSelected="${zhifunian}" monthName="seach_zhifuyue" monthSelected="${zhifuyue}"/>	
	    			</td>
	    			<td>
	    				<spring:message code="public.title.empIdAndName"/><!--社号/姓名-->
	    			</td>
	    			<td>
	    				  <input name="seach_shehaoxingming" value="${shehaoxingming}">
	    			</td>
	    			
		    	</tr>
		    	<tr>
		    	 <c:if test="${faren eq 'TSTO'}">
		    		 <td>
	    				大区
	    			</td>
	    			<td ><ait:deptTreeMulti id="seach_PAY_AREA_CD" name="seach_PAY_AREA_NM" limit="pa" level="2" ></ait:deptTreeMulti>
	    			</td>
		    		</c:if>
	    		 <c:if test="${faren ne 'TSTO'}">
	    			 <td>
		    				部门
		    			</td>
		    			<td ><ait:deptTreeMulti id="seach_PAY_AREA_CD" name="seach_PAY_AREA_NM" limit="pa"  ></ait:deptTreeMulti>
		    			</td>
	    			</c:if>
	    			
	    			
						 <td>人员类型组 </td>
						<td>
						<ait:SelectEmpTypeCode name="seach_JobTypeGroupNo" selected="${JobTypeGroupNo}" limit="pa" type="group" cnpyID="${defaultCpny}"/>
						</td>
		    	</tr>
		</table>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">
					<spring:message code="public.title.search"/><!-- 检索 --></button></div></div>
				</li>
				<li>
					<div class="buttonActive"><div class="buttonContent"><!-- 导出Excel -->
						<button type="button" onclick="exportPaInfo_pa0705()" title="<spring:message code='rp.report.title.exportYN'/>">
							<%--导出Excel--%><spring:message code="ar.addempshift.title.excelexport"/>
						</button>
					</div></div>
				</li>
				<li>
					<div class="buttonActive">
						<a onclick = "f_Calculate_viewardetailcalculate();">
						&nbsp;&nbsp;<SPAN><spring:message code="alert.pa.pasalarycanshu.yutijisuan"/><!-- 预提计算 --></SPAN>&nbsp;
						</a>
					</div>
				</li>
			</ul>
		</div>
	</div>
	</form>
</div>
<div class="pageContent">
	<table class="table" width="100%" layoutH="198">
		<thead>
			<tr>
				<th width="30"><spring:message code="pa.salary.canShu.xuHao"/><!--序号--></th>
				<th width="70"><spring:message code="alert.pa.pasalarycanshu.shehao"/><!--社号--></th>
				<th width="70"><spring:message code="alert.pa.pasalarycanshu.xingming"/><!--姓名--></th>
				<th width="70"><spring:message code="pa.salary.canShu.faRen"/><!--法人--></th>
				<th width="70">预提月份</th>
				<th width="70">人员类型组</th>
				<th width="70">AU CODE</th>
				<th width="140"><spring:message code="public.title.deptName"/><!--部门--></th>
				<th width="60"><spring:message code="pa.salary.canShu.zhiFuYueFen"/><!--支付月份--></th>
				<th width="60"><spring:message code="alert.pa.pasalarycanshu.jisuanjishu"/><!--计算基数--></th>
				<th width="60"><spring:message code="pa.salary.canShu.jiTiBiLv"/><!--计提比率--></th>
				<th width="60"><spring:message code="alert.pa.pasalarycanshu.jitijine"/><!--计提金额--></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${paiQianDiList}" var="pQd" varStatus="i">
				<tr>
					<td><center>${i.index + 1}</center></td>
					<td><center>${pQd.SH}</center></td>
					<td><center>${pQd.XM}</center></td>
					<td><center>${pQd.FR}</center></td>
					<td><center>${pQd.BONUS_MONTH}</center></td>
					<td><center>${pQd.EMPTYPE_GROUP_NAME}</center></td>
					<td><center>${pQd.AU_CODE}</center></td>
					<td>${pQd.BM}</td>
					<td><center>${pQd.ZFYF}</center></td>
					<td><center>${pQd.JSJS}</center></td>
					<td><center>${pQd.JTBL}</center></td>
					<td><center>${pQd.JTJE}</center></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<c:set value="/pa/salaryCanShu/viewNianZhongJiangYuTiJiSuanList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
	<form id="excelExportForm_pa0705" name="excelExportForm_pa0705" method="post">
		<input type="hidden" id="password" 			name="password" 		value="" />
		<input type="hidden" id="zhifunian" 				name="zhifunian" 			value="" />
		<input type="hidden" id="zhifuyue" 			name="zhifuyue" 			value="" />
		<input type="hidden" id="PAY_AREA_CD" 		name="PAY_AREA_CD" 		value="" />
		<input type="hidden" id="shehaoxingming" 		name="shehaoxingming" 		value="" />
		<input type="hidden" id="JobTypeGroupNo" 		name="JobTypeGroupNo" 		value="" />
	</form>
</div>