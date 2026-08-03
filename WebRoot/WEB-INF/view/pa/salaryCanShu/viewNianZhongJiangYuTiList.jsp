<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
	
</script>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/pa/salaryCanShu/viewNianZhongJiangYuTiList" method="post" rel="pagerForm">
	<div class="searchBar" >
		<table class="searchContent">
				<tr>
					<td>
	    				<spring:message code="pa.salary.canShu.nianDu"/><!--年度-->
	    			</td>
	    			<td >
	    				<ait:date yearName="seach_niandu" yearSelected="${niandu}" ></ait:date>
	    			</td>
					<td>
	    				<spring:message code="pa.salary.canShu.faRen"/><!--法人-->
	    			</td>
	    			<td >
						<c:if test="${authority eq '1'}">
							<select id="seach_faren" name="seach_faren">
								<option value="">全部</option>
								<c:forEach items="${companyList}" var="item" varStatus="i">
									<option value="${item.CPNY_ID }" <c:if test="${faren eq item.CPNY_ID}">selected</c:if>>${item.CPNY_ID }</option>
								</c:forEach>
							</select>
						</c:if>
						<c:if test="${authority eq '0'}">
							${faren}
							<input type="hidden" id="hr2100_seach_CPNY_ID" name="seach_faren" value="${faren}"/>
						</c:if>
	    			</td>
					<td>
	    				启用状态
	    			</td>
	    			<td >
							<select id="seach_ACTIVITY" name="seach_ACTIVITY">
								<option value="">全部</option>
								<option value="1" <c:if test="${'1' eq ACTIVITY}">selected</c:if>>启用</option>
								<option value="0" <c:if test="${'0' eq ACTIVITY}">selected</c:if>>未启用</option>
							</select>
	    			</td>
		    	</tr>
		</table>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">
					<spring:message code="public.title.search"/><!-- 检索 --></button></div></div>
				</li>
				<li>
					<div style="float:left; ">
						<a class="buttonActive" id ="exportExcel"
						href="/pa/excelExport/exportNianZhongJiangYuTiInfoList?seach_niandu=${niandu}&seach_faren=${faren}">
											<SPAN><spring:message code="ar.addempshift.title.excelexport"/><!-- EXCEL导出 --></SPAN>
						</a>
					</div>
				</li>
				<c:if test="${authority eq '1'}">
					<li>
						<div style="float:left; ">
						<a class="buttonActive" target="ajaxTodo"
						href="/pa/salaryCanShu/checkAnnualBonusParamSetup">
											<SPAN>CHECK</SPAN>
						</a>
					</div>
				</li>
				</c:if>
			</ul>
		</div>
	</div>
	</form>
</div>
<div class="pageContent">
	<c:set value="dialog" var="add_tab"/>
	<c:set value="800" var="add_width"/>
	<c:set value="270" var="add_height"/>
	<c:set value="/pa/salaryCanShu/addNianZhongJiangYuTiView" var="add_Url"/>
	<%@ include file="/WEB-INF/view/inc/includeButtonSalaryCsNzJYt.jsp"%>
	<table class="table" width="100%" layoutH="198">
		<thead>
			<tr>
				<th width="50"><spring:message code="pa.salary.canShu.xuHao"/><!--序号--></th>
				<th width="90"><spring:message code="pa.salary.canShu.nianDu"/><!--年度--></th>
				<th width="90"><spring:message code="pa.salary.canShu.faRen"/><!--法人--></th>
				<th width="100"><spring:message code="pa.salary.canShu.jiTiBiLv"/><!--计提比率--></th>
				<th width="120"><spring:message code="pa.salary.canShu.zhiFuYueFen"/><!--支付月份--></th>
				<th width="120">基数</th>
				<th width="120">修改人</th>
				<th width="120">修改日期</th>
				<th width="120">备注</th>
				<th width="80"><spring:message code="sys.arAffirmPost.title.ableStatus"/><!--启用状态--></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${paiQianDiList}" var="pQd" varStatus="i">
				<tr target="PQD_NO" rel="${pQd.NO1}">
					<td><center>${i.index + 1}</center></td>
					<td><center>${pQd.ND}</center></td>
					<td>
					<c:if test="${pQd.PQD_ACTIVITY eq '1'}"><center><a href="/pa/salaryCanShu/updateNianZhongJiangYuTiView?PQD_NO=${pQd.NO1}" width="800" height="270" target="dialog" mask="true"><font color="red">${pQd.FR}</font></a></center></c:if>
					<c:if test="${pQd.PQD_ACTIVITY ne '1'}"><center>${pQd.FR}</center></c:if>
					</td>
					<td><center>${pQd.JTBL}%</center></td>
					<td><center>${pQd.ZFYF}</center></td>
					<td><center>${pQd.FORMULA}</center></td>
					<td><center>${pQd.LOCAL_NAME}</center></td>
					<td><center>${pQd.CREATE_DATE}</center></td>
					<td><center>${pQd.REMARK}</center></td>
					<td><center><img src="/resources/images/a_${pQd.PQD_ACTIVITY}.gif"></center></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>		
	<c:set value="/pa/salaryCanShu/viewNianZhongJiangYuTiList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>