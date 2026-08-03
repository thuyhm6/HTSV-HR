<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
function f_Calculate_viewardetailcalculate(){
	//是否开始计算?
	alertMsg.confirm("<spring:message code='ar.alert.message.viewardetailcaculate.canstart'/>", {
		okCall: function(){
			$("#loading_viewardetailcalculate").show();
			$("#detailCalculate").hide();
			$.ajax({
				type: 'post',
				cache: false,
				contentType: 'application/json',
				url: '/pa/salaryCanShu/callZhengGuiYuTiJiSuanProduce',
				dataType: 'json',
				success: function(responseStr) {
					$("#ardetail_calculateResult").html(responseStr);
					$("#loading_viewardetailcalculate").hide();
					$("#detailCalculate").show();
				}
			});
		}
	});
}


var ajaxGet_add;
function changeCpnyId_pa0705023(cpnyId,valueOld) {
	if (ajaxGet_add != null) {
		ajaxGet_add.abort();
	}
	$.ajaxSettings.global = false;
	ajaxGet_add = $.ajax( {
				type : "POST",
				url : "/hrm/empinfo/getEmpTypeGroup",
				data : {CPNY_ID : cpnyId},
				dataType : "json",
				success : function(data) {
					$('#seach_EMP_TYPE_GROUP').html('');
					var html = '<option value="">请选择</option>';
					if (typeof (data['result']) != "undefined") {
						$.each(data['result'],
										function(commentIndex, comment) {
											html += '<option value="'+ comment['CODE_NO']+ '">'+ comment['CONTENT'] + '</option>';
										});
					}
					$('#seach_EMP_TYPE_GROUP').html(html);
					if(valueOld == -1){
						$("#seach_EMP_TYPE_GROUP").val($("#empTypeGroupCode").val());
					}
				}
			});
	$.ajaxSettings.global = true;
}
 
</script>

<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/pa/salaryCanShu/viewZhengGuiYuTiJiSuanList" method="post" rel="pagerForm">
	<div class="searchBar" >
		<table class="searchContent">
				<tr>
					<td>
	    				<spring:message code="pa.salary.canShu.faRen"/><!--法人-->
	    			</td>
	    			<td >
	    			 
	    			<c:if test="${authority eq '1'}">
							<select id="seach_faren" name="seach_faren" onChange="changeCpnyId_pa070502(this.value)">
								<c:forEach items="${companyList}" var="item" varStatus="i">
									<option value="${item.CPNY_ID }" <c:if test="${faren eq item.CPNY_ID}">selected</c:if>>${item.CPNY_ID }</option>
								</c:forEach>
							</select>
						</c:if>
						<c:if test="${authority eq '0'}">
							${faren}
							<input type="hidden" id="seach_faren" name="seach_faren" value="${faren}"/>
						</c:if>
	    			
	    			</td>
	    			
	    			<td>
	    				<spring:message code="pa.salary.canShu.nianDu"/><!--期次年月-->
	    			</td>
	    			<td >
	    				 <ait:date yearName="seach_zhifunian" yearSelected="${zhifunian}" monthName="seach_zhifuyue" monthSelected="${zhifuyue}"/>	
	    			</td>
	    			
	    			<td>
	    						<!-- 部门： --> <spring:message
							code="hr.viewPersonalInfo.title.DEPTNAME" />
	    			</td>
	    			<td ><!--
	    			
 <ait:deptTreeMulti id="seach_PAY_AREA_CD" name="seach_PAY_AREA_NM" limit="pa" level="2" ></ait:deptTreeMulti>
 
 
 --><ait:deptList name="seach_DEPT_NO" id="viewFeiCuLowSalaryHistoryList_seachDept" limit="pa"/>
	 <ait:deptTreeIcon name="seach_DEPT_NO" limit="pa" id="viewFeiCuLowSalaryHistoryList_seachDept" selected="${DEPT_NO}"/>
					
	    			</td>
						 <td>人员类型</td>
						<td>
						<ait:SelectEmpTypeCode name="seach_leixing" selected="${leixing}" limit="pa"/>
						</td>
	    			<td>
	    				<spring:message code="public.title.empIdAndName"/><!--社号/姓名-->
	    			</td>
	    			<td >
	    				  <input name="seach_shehaoxingming" value="${shehaoxingming}">
	    			</td>
		    	</tr>
		</table>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">
					<spring:message code="public.title.search"/><!-- 检索 --></button></div></div>
				</li>
			</ul>
		</div>
	</div>
	</form>
</div>
<div class="pageContent"><!--
					<div style="float:left; ">
						<a class="buttonActive" id ="exportExcel"
						onclick = "f_Calculate_viewardetailcalculate();">
						&nbsp;&nbsp;<SPAN><spring:message code="alert.pa.pasalarycanshu.yutijisuan"/> 预提计算 </SPAN>&nbsp;
						</a>
					</div>
					--><!--		updateNianZhongJiangYuTi			-->
					<div style="float:left; ">
						<a class="buttonActive" id ="exportExcel"
						href="/pa/excelExport/exportZhengGuiZhiJiSuanInfoList?seach_faren=${faren}&seach_zhifunian=${zhifunian}&seach_zhifuyue=${zhifuyue}&seach_bumen=${bumen}&seach_shehaoxingming=${shehaoxingming}">
											<SPAN><spring:message code="ar.addempshift.title.excelexport"/><!-- EXCEL导出 --></SPAN>
						</a>
					</div>
					<div style="float: none" id="ardetail_calculateResult"></div>
	<table class="table" width="100%" layoutH="198">
		<thead>
			<tr>
				<th width="50"><spring:message code="pa.salary.canShu.xuHao"/><!--序号--></th>
				<th width="90"><spring:message code="pa.salary.canShu.faRen"/><!--法人--></th>
				<th width="120"><spring:message code="pa.salary.canShu.qici"/><!--期次--></th>
				<th width="90"><spring:message code="public.title.deptName"/><!--部门--></th>
				<th width="100"><spring:message code="alert.pa.pasalarycanshu.shehao"/><!--社号--></th>
				<th width="100"><spring:message code="alert.pa.pasalarycanshu.xingming"/><!--姓名--></th>
				<th width="100"><spring:message code="pa.salary.canShu.yutigongzi"/><!--预提工资--></th>
				<th width="100"><spring:message code="pa.salary.canShu.chuansongzhuangtai"/><!--传送状态--></th>
						<th width="100"><spring:message code="pa.salary.canShu.accdivcode"/><!--ACC_DIV_CODE--></th>
				<th width="100"><spring:message code="pa.salary.canShu.renyuanleixing"/><!--人员类型--></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${paiQianDiList}" var="pQd" varStatus="i">
				<tr>
					<td><center>${i.index + 1}</center></td>
					<td><center>${pQd.CPNY_ID}</center></td>
					<td><center>${pQd.PERIOD_NAME}</center></td>
					<td><center>${pQd.BM}</center></td>
					<td><center>${pQd.EMPNO}</center></td>
					
					<td><center>${pQd.LOCAL_NAME}</center></td>
					<td><center>${pQd.SALARY_AMOUNT}</center></td>
					<td><center>${pQd.TRANSFER_FLAG}</center></td>
					<td><center>${pQd.ACC_DIV_CODE}</center></td>
					<td><center>${pQd.LEIXING}</center></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<c:set value="/pa/salaryCanShu/viewZhengGuiYuTiJiSuanList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>