<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
$(document).ready(function(){
	$("#viewDeptManagerCheck_Serch").click(function(){
		$("#viewDeptManagerCheckForm").submit();
	});
});
</script>
<div class="pageContent">
<div class="formBar">
	<ul class="toolBar">
			<li>
				<a class="buttonActive" id="viewDeptManagerCheck_Serch" href="#">
					<span><spring:message code="org.title.SELECT" /><!-- 查询 --></span>
				</a>
			</li>
			<li>
				<a class="add" href="#"><span><spring:message code="org.title.INSERT" /><!-- 添加 --></span></a>
			</li>
			<li>
				<a class="add" href="#"><span><spring:message code="org.title.DELETE" /><!-- 删除 --></span></a>
			</li>
			<li>
				<a class="buttonActive" href="#"><span><spring:message code="org.title.SAVE" /><!-- 保存 --></span></a>
			</li>
			<li>
				<a class="add" href="#"><span><spring:message code="org.title.PRINT" /><!-- 打印 --></span></a>
			</li>
			<li>
				<a class="delete" href="/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=6&RESUME_NO=${ RESUME_NO}"><span><spring:message code="org.title.exportLOtImportExcel" /><!-- 导出到EXECL --></span></a>					
			</li>
	</ul>
</div>

<div class="pageContent">
	<div class="user_table" style="font:bold 12px/20px arial,sans-serif;">Total:${deptManagerCheckCnt}</div>
	<div class="grid">
		<div class="gridHeader">
			<div class="gridThead" style="position: relative; left: 0px;">
				<table class="table" width="100%">
					<thead>
						<tr>
							<th width="30px">No.</th>
							<th width="150px"><spring:message code="org.title.dept" /><!-- 部门 --></th>
							<th width="80px"><spring:message code="org.title.PRODUCT_TYPE" /><!-- 产品类型 --></th>
							<th width="80px"><spring:message code="org.title.IS_DEFAULT" /><!-- 默认与否 --></th>
							<th width="80px"><spring:message code="org.title.UPDATED_IP" /><!-- 变更者 --></th>
							<th width="80px"><spring:message code="org.title.UPDATE_DATE" /><!-- 变更时间 --></th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
	</div>
	<div class="gridScroller" style="height: 254px; overflow: auto;">
		<div class="gridTbody">
				<table class="table" width="100%">
					<tbody>
						<c:forEach items="${deptManagerCheckList}" var="item" varStatus="i">
							<tr>
								<td class='td_center' width="30px" >${i.count}</td>
								<td style="text-align:left" width="150px" >${item.ORG_NAME_LOCAL}</td>
								<td class='td_center' width="80px">${item.EMPID}</td>
								<td class='td_center' width="80px">
									<input type="checkbox" name="viewModifyEmpInfo_partTime" value="1"
										<c:if test="${item.IS_PART_TIME eq '1' }">checked</c:if>/>
								</td>
								<td class='td_center' width="80px">${item.UPDATED_BY} ${item.UPDATED_IP}</td>
								<td class='td_center' width="80px">${item.UPDATE_DATE}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
		</div>
	</div>
</div>
</div>
