<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
	function changeUrlToNavTabNum(url,param,menu_code,menu_name){
		url = encodeURI(encodeURI(url)) ;
		navTabNum(url,'',menu_code,menu_name);
	}
	$(document).ready(function() { 
		if('${totalCount}' == 1){
			$('#xp').dblclick();
		}
	});

</script>
<div class="pageHeader">
<form id="viewEmpInfo" onsubmit="return dwzSearch(this,'dialog');" action="/hrm/approve/viewEmpInfoListSearch?firstFlag=N&searchChange=${searchChange }&defaultCpny=${defaultCpny }" method="post" rel="pagerForm">
<input type="hidden" name="pageNum" value="${pageNum}" />
		<input type="hidden" name="numPerPage" value="${numPerPage}" />
<div class="searchBar">
<table class="searchContent">
	<tr>
		<td><!-- 社号/姓名： --> <spring:message
			code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME" />
		</td>
		<td><input
			type="text" name="seach_KEY" value="${KEY}" />
			</td>
	</tr>
</table>
<div class="subBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="submit" id="jiansuo">
								<spring:message code="public.title.search"/><!-- 检索 -->
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
			<table class="table" width="100%" layoutH="198" targetType="dialog">
				<thead>
					<tr>
				<th orderfield="empId"><!-- 员工号 --><spring:message code="hr.viewPersonalInfo.title.EMPID"/></th>
				<th orderfield="empName"><!-- 员工姓名 --><spring:message code="public.title.name"/></th>
				<th orderfield="empDept"><!-- 部门 --><spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/></th>
				<!-- <th orderfield="empDept">人员类型</th> -->
				<th orderfield="empDept"><!-- 在职区分 --><spring:message code="hr.viewPersonalInfo.title.IN_THE_DIFFERENCE"/></th>
				<%-- <th width="80"><!-- 确认 --><spring:message code="hr.viewPersonalInfo.title.AFFIRM"/></th> --%>
			</tr>
				</thead>
				<c:if test="${searchChange=='viewPersonalInfo' }">
				<tbody>
					<c:forEach items="${empInfo}" var="keeper">
			<tr id='xp' ondblclick="changeUrlToNavTabNum('/hrm/approve/viewEssApplyInfo?PERSON_ID=${keeper.PERSON_ID}&KEY=${KEY }','pageNum=1&menuNo=14014333&navTabId=sy0501','sy0501','员工信息改变管理');javascript:
							$.bringBack({
								person_id:'${keeper.PERSON_ID}',
								personId:'${keeper.PERSON_ID}',
								empId:'${keeper.EMPID}',
								cpny_id:'${keeper.CPNY_ID}',
								stat_no:'${keeper.STAT_NO}',
								empName:'${keeper.LOCAL_NAME}',
								empDept:'${keeper.DEPT_NAME}'
								
							})">
				<td>${keeper.EMPID}</td>
				<td>${keeper.LOCAL_NAME}</td>
				<td>${keeper.DEPT_NAME}</td>
				<td>${keeper.EMP_OFFICE_NAME}</td>
			</tr>
		</c:forEach>
			
				</tbody>
				</c:if>
				
				
			</table>
			<form id="pagerForm" method="post" action="/hrm/approve/viewEmpInfoListSearch?firstFlag=N&searchChange=${searchChange }&defaultCpny=${defaultCpny }">
	
	<div class="panelBar">
		<div class="pages">
			<span><!-- 显示 --><spring:message code="public.title.view"/></span>
				<select class="combox" name="numPerPage" onchange="dialogPageBreak({targetType:'dialog', numPerPage:this.value})">
					<option value="10"  <c:if test="${numPerPage == 10 }" >selected</c:if> >10</option>
					<option value="20"  <c:if test="${numPerPage == 20 }" >selected</c:if> >20</option>
					<option value="30"  <c:if test="${numPerPage == 30 }" >selected</c:if> >30</option>
				</select>
			<span><!-- 条 --><spring:message code="public.title.tiao"/>，<!-- 共 --><spring:message code="public.title.gong"/>${totalCount}<!-- 条 --><spring:message code="public.title.tiao"/></span>
		</div>
		<div class="pagination" targetType="dialog" totalCount="${totalCount}" numPerPage="${numPerPage}" currentPage="${pageNum}"></div>
	</div>
	</form>
		</div>
<script type="text/javascript">
</script>