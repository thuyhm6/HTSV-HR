<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>

</script>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/pa/salaryCanShu/viewYuTiDuiXiangGuanLiList" method="post" rel="pagerForm">
	<div class="searchBar" >
		<table class="searchContent">
				<tr>
					<td>
	    				<spring:message code="pa.salary.canShu.faRen"/><!--法人-->
	    			</td>
	    			<td >
	    				<ait:SyCompany target="config" cpnyId="${sessionScope.LoginUser.cpnyId }" name="seach_faren" language="zh"  limit="ALL" activity="1" selected="${faren}"/>
	    			</td>			
	    			<td>
	    				<spring:message code="pa.salary.canShu.renYuanLeiXingZu"/><!--人员类型组-->
	    			</td> 
	    			<td >
						<ait:SelectEmpTypeCode name="seach_renyuanleixingzu" selected="${renyuanleixingzu}" limit="pa" type="group"/>
	    			</td>
	    			
	    			<td>
	    				<spring:message code="pa.salary.canShu.shiFouCanYuYuTi"/><!--是否参与预提-->
	    			</td>
	    			<td >
	    				<SELECT name="seach_shifoucanyuyuti"> 
	    					<option value=""><spring:message code="pa.salary.canShu.qingXuanZe"/></option>
	    					<option value="Y" <c:if test="${shifoucanyuyuti eq 'Y' }"> selected</c:if>>Y</option>
	    					<option value="N" <c:if test="${shifoucanyuyuti eq 'N' }"> selected</c:if>>N</option>
	    				</SELECT>
	    			</td>
	    			
	    			<td>
	    				<spring:message code="pa.salary.canShu.shiFouQiYong"/><!--是否启用-->
	    			</td>
	    			<td >
	    				<SELECT name="seach_shifouqiyong"> 
	    					<option value=""><spring:message code="pa.salary.canShu.qingXuanZe"/></option>
	    					<option value="1" <c:if test="${shifouqiyong eq '1' }"> selected</c:if>><spring:message code="sys.arAffirmPost.title.able"/></option>
	    					<option value="2" <c:if test="${shifouqiyong eq '2' }"> selected</c:if>><spring:message code="sys.arAffirmPost.title.enable"/></option>
	    				</SELECT>
	    			</td>
	    			
	    			
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
	<c:set value="dialog" var="add_tab"/>
	<c:set value="800" var="add_width"/>
	<c:set value="280" var="add_height"/>
	<c:set value="/pa/salaryCanShu/addYuTiDuiXiangGuanLiView" var="add_Url"/>
	<c:set value="ajaxTodo" var="delete_tab"/>
	<c:set value="/pa/salaryCanShu/deleteYuTiDuiXiangGuanLiInfo?PQD_NO={PQD_NO}" var="delete_Url"/>
	<c:set value="dialog" var="edit_tab"/>
	<c:set value="800" var="edit_width"/>
	<c:set value="280" var="edit_height"/>
	<c:set value="/pa/salaryCanShu/updateYuTiDuiXiangGuanLiView?PQD_NO={PQD_NO}" var="edit_Url"/>
	<%@ include file="/WEB-INF/view/inc/includeButtonSalaryCs.jsp"%>
	<table class="table" width="100%" layoutH="210">
		<thead>
			<tr>
				<th width="50"><spring:message code="pa.salary.canShu.xuHao"/><!--序号--></th>
				<th width="70"><spring:message code="pa.salary.canShu.faRen"/><!--法人--></th>
				<c:if test="${'TSTO' eq interCpnyID}">
					<th width="120">大区名称</th>
				</c:if>
				<th width="120"><spring:message code="pa.salary.canShu.renYuanLeiXingZu"/><!--人员类型组--></th>
				<th width="80"><spring:message code="pa.salary.canShu.shiFouCanYuYuTi"/><!--是否参与预提--></th>
				<th width="120"><spring:message code="pa.salary.canShu.chuanJianRiQi"/><!--创建日期--></th>
				<th width="120"><spring:message code="pa.salary.canShu.xiuGaiShiJian"/><!--修改时间--></th>
				<th width="70"><spring:message code="pa.salary.canShu.xiuGaiRen"/><!--修改人--></th>
				<th width="50"><spring:message code="sys.arAffirmPost.title.ableStatus"/><!--启用状态--></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${paiQianDiList}" var="pQd" varStatus="i">
				<tr target="PQD_NO" rel="${pQd.NO1}">
					<td><center>${i.index + 1}</center></td>
					<td><center>${pQd.FR}</center></td>
					<c:if test="${'TSTO' eq interCpnyID}">
						<td><center>${pQd.DQ}</center></td>
					</c:if>
					<td><center>${pQd.RYLXZ}</center></td>
					<td><center>${pQd.SFCYYT}</center></td>
					<td><center>${pQd.CD}</center></td>
					<td><center>${pQd.UD}</center></td>
					<td><center>${pQd.UY}</center></td>
					<td><center><img src="/resources/images/a_${pQd.PQD_ACTIVITY}.gif"></center></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>		
	<c:set value="/pa/salaryCanShu/viewYuTiDuiXiangGuanLiList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>