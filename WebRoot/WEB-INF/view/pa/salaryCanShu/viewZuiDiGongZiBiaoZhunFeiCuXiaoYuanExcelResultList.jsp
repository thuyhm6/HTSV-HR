<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
		function importZuiDiGongZiBiaoZhunCuXiaoYuanInfo(){
			$("#importExcelDialog_pa0807_temp").attr('href','/pa/excelImport/importPaiQianDiGUanLiInfoData?importFunName=/importZuiDiGongZiBiaoZhunFeiCuXiaoYuanInfo');
			$("#importExcelDialog_pa0807_temp").click();
		}
</script>

<a id="importExcelDialog_pa0807_temp"  href="#" target="dialog" mask="true"></a>
<a id="importExcel_pa0807_temp"  href="#" target="navTab" mask="true">
<span style="display:none;"><spring:message code="pa.salary.canShu.zDgzFcxyDaoRuJieGuo"/><!--最低工资(非促销员)导入结果 --></span></a>


<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/pa/salaryCanShu/viewZuiDiGongZiBiaoZhunFeiCuXiaoYuanExcelResultList" method="post" rel="pagerForm">
	<div class="searchBar" >
		<table class="searchContent">
			<tr>
				<th>
					<spring:message code="inct.salesman.excel.totalCnt"/><!-- 总行数-->：
				</th>
				<td>
					${totalCount}
					<iframe id='callbackframe' name='callbackframe' src='about:blank' style='display:none'></iframe>
				</td>
				<th>
					<spring:message code="inct.salesman.excel.errCnt"/><!-- 出错行数-->：
				</th>
				<td>							
					${errCnt}
				</td>
				<th>
				<spring:message code="pa.salary.canShu.chuCuoYuFou"/><!-- 出错与否：-->
				</th>
				<th> 
					<select name="seach_RESULT_FLAG" id="seach_RESULT_FLAG">
							<option value="" <c:if test="${RESULT_FLAG eq '' }">selected</c:if>><spring:message code="pa.salary.canShu.quanBu"/><!-- 全部 --></option>
							<option value="E" <c:if test="${RESULT_FLAG eq 'E' }">selected</c:if>><spring:message code="pa.salary.canShu.shi"/><!-- 是 --></option>
							<option value="N" <c:if test="${RESULT_FLAG eq 'N' }">selected</c:if>><spring:message code="pa.salary.canShu.fou"/><!--否 --></option>
					</select>
				</th>
			</tr>
		</table>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">
				<spring:message code="public.title.search"/><!-- 检索 --></button></div></div></li>
			</ul>
		</div>
	</div>
	</form>
</div>
<div class="pageContent">
	<div style="float:right; ">
		<a class="buttonActive" onclick="importZuiDiGongZiBiaoZhunFeiCuXiaoYuanInfo()">
		<SPAN><spring:message code="ar.addempshift.title.excelimport"/><!-- EXCEL导入 --></SPAN>
	 	</a>
	</div>
	
	<div style="float:right; ">
		<a class="buttonActive" id ="exportExcel"
		href="/pa/excelExport/exportZuiDiGongZiBiaoZhunFeiCuXiaoYuanTempInfoList?seach_RESULT_FLAG=${RESULT_FLAG}">
							<SPAN><spring:message code="ar.addempshift.title.excelexport"/><!-- EXCEL导出 --></SPAN>
		</a>
	</div>
	
	<div style="float:right; ">
		<a class="buttonActive" target="ajaxTodo" callback="navTabAjaxDoneRefreshCurrentPage"
			href="/pa/tempsale/submitImportExcelTempZuiDiGongZiBiaoZhunFeiCuXiaoYuanData"  title="确定要提交吗?"><span>
				<spring:message code="public.title.submit"/><!--提交--></span></a>
	</div>
					
	<table class="table" width="100%" layoutH="198">
		<thead>
			<tr>
				<th width="50"><spring:message code="pa.salary.canShu.xianSHiShunXu"/><!--NO.--></th>
				<th width="50"><spring:message code="pa.salary.canShu.faRen"/><!--法人--></th>
				<th width="50"><spring:message code="pa.salary.canShu.niandu"/><!--年度--></th>
				<th width="50"><spring:message code="pa.salary.canShu.fulidiqu"/><!--福利地区--></th>
				<th width="50"><spring:message code="pa.salary.canShu.zuidigongzi"/><!--最低工资--></th>
				<th width="50"><spring:message code="pa.salary.canShu.shepinggongzi"/><!--社平工资--></th>
				<th width="50"><spring:message code="pa.salary.canShu.zuixiaojishu"/><!--最小基数--></th>
				<th width="50"><spring:message code="pa.salary.canShu.zuidajishu"/><!--最大基数--></th>
				<th width="50"><spring:message code="pa.salary.canShu.qufen"/><!--区分--></th>
				<th width="100"><spring:message code="pa.salary.canShu.yanZhengJieGuo"/><!--验证结果--></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${paiQianDiList}" var="pQd" varStatus="i">
				<tr target="PQD_NO" rel="${pQd.NO1}">
					<td><center>${i.index + 1}</center></td>
					<td><center>${pQd.FR}</center></td>
					<td><center>${pQd.ND}</center></td>
					<td><center>${pQd.FLDQ}</center></td>
					<td><center>${pQd.ZDGZ}</center></td>
					<td><center>${pQd.SPGZ}</center></td>
					<td><center>${pQd.ZXJS}</center></td>
					<td><center>${pQd.ZDJS}</center></td>
					<td><center>${pQd.QF}</center></td>
					<td><center>${pQd.YZJG}</center></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>		
	<c:set value="/pa/salaryCanShu/viewZuiDiGongZiBiaoZhunFeiCuXiaoYuanExcelResultList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination2.jsp"%>
</div>