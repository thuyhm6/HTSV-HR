<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
	
</script>
<form onsubmit="return navTabSearch(this);"
		action="/ess/wageApplication/viewWageCheckList" method="post">
	<div class="pageHeader">
		<div class="searchBar">
			<table class="searchContent">
			<c:if test="${cpny_id eq 'TSTO'}">
				<tr>
					<td>大区</td>
					<td>
	    				<ait:deptTreeMulti id="seach_PAYAREA" selected="${PAYAREA}" selectedNm= "${PAY_AREA_NM}" name="seach_PAY_AREA_NM" limit="pa" level="2" ></ait:deptTreeMulti>
	    			</td>
					<td>人员类型组</td>
					<td><ait:SelectEmpTypeCode name="seach_EMP_TYPE_GROUP" selected="${EMP_TYPE_GROUP}" limit="pa" type="group"/></td>
					
					<td>工号/姓名</td>
					<td><input type="text" name="seach_KEY" value="${KEY}" />
					</td>
					<td>修改人</td>
					<td><select id="seach_UPDATED_NAME" name="seach_UPDATED_NAME"
						value="${UPDATED_NAME}">
							<option value=""></option>
							<c:forEach items="${feeList}" var="item">
								<option value="${item.UPDATED_BY}"
									<c:if test="${item.UPDATED_BY eq UPDATED_NAME}">selected</c:if>>
									${item.UPDATED_NAME }</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td><spring:message code="pa.insurance.title.projectName" />
						<!--项目名称--></td>
					<td><input type="text" name="seach_TYPENAME"
						value="${TYPENAME}" /></td>
					<td>支付月份</td>
					<td><input type="text" id="MONTH" name="MONTH"
						value="${MONTH}" class="date" format="yyyy-MM" /></td>
				</tr>
				</c:if>
				<c:if test="${cpny_id ne 'TSTO'}">
				<tr>
				    <td>
						<!-- 部门： --> <spring:message
							code="hr.viewPersonalInfo.title.DEPTNAME" /></td>
					<td><ait:deptList name="seach_DEPTNO" cpnyId="${cpny_id}"
							id="viewEmpInfoList_seachDept" /> 
						<ait:deptTreeIcon
							name="seach_DEPTNO" cpnyId="${cpny_id}" limit="pa"
							id="viewEmpInfoList_seachDept" selected="${DEPTNO}" />
					</td>
					<td><spring:message code="pa.insurance.title.projectName" />
					</td>
					<td>
						<!--项目名称--><input type="text" name="seach_TYPENAME"
						value="${TYPENAME}" /></td>
						
					<td>人员类型组</td>
					<td><ait:SelectEmpTypeCode name="seach_EMP_TYPE_GROUP" selected="${EMP_TYPE_GROUP}" limit="pa" type="group"/>
					</td>
					<td>支付月份</td>
					<td><input type="text" id="MONTH" name="MONTH"
						value="${MONTH}" class="date" format="yyyy-MM" /></td>
				</tr>
				<tr>
						<td>
						<!-- 工号/姓名： --> <spring:message
							code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME" />
					</td>
					<td><input type="text" name="seach_KEY" value="${KEY}" />
					</td>
                    <td>修改人
					</td>
					<td>
                    <select id="seach_UPDATED_NAME" name="seach_UPDATED_NAME"
						value="${UPDATED_NAME}">
							<option value="">全部</option>
							<c:forEach items="${feeList}" var="item">
								<option value="${item.UPDATED_BY}"
									<c:if test="${item.UPDATED_BY eq UPDATED_NAME}">selected</c:if>>
									${item.UPDATED_NAME }</option>
							</c:forEach>
					</select></td>
				</tr>
				</c:if>
			</table>
			<div class="subBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">
									<spring:message code="public.title.search" />
									<!--检索-->
								</button>

							</div>
						</div></li>
				</ul>
			</div>
		</div>
	</div>
		<div class="pageContent">
			<table class="table" width="100%" layoutH="200">
				<thead>
					<tr>
						<th width="5%"><spring:message code="sys.affirm.indexNum" />
							<!--序号-->
						</th>
						<c:if test="${cpny_id eq 'TSTO'}">
							<th width="15%">大区</th>
						</c:if>
						<th width="15%">部门</th>
						<th width="10%"><spring:message
								code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME" />
							<!--工号姓名--></th>
						<th width="10%"><spring:message
								code="hr.enpinfo.title.EMP.TYPE" />
							<!--人员类型--></th>
						<th width="13%"><spring:message
								code="pa.insurance.title.projectName" />
							<!--项目名称--></th>
						<th width="8%">开始月</th>
						<th width="8%">结束月</th>
						<th width="8%">金额</th>
						<th width="8%">修改人</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${feeList}" var="item" varStatus="i">
						<tr>
							<td>${i.index + 1}&nbsp;</td>
							<c:if test="${cpny_id eq 'TSTO'}">
								<td>${item.PAY_AREA}</td>
							</c:if>
							<td>${item.DEPT_NAME}</td>
							<td>[${item.EMPID}]${item.WAGE_NAME}</td>
							<td>${item.EMP_TYPE_NAME}</td>
							<td>${item.TYPENAME}</td>
							<td>${item.START_DATE}</td>
							<td>${item.END_DATE}</td>
							<td>${item.MONEY}</td>
							<td>${item.UPDATED_NAME }</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</form>
	<c:set value="/ess/wageApplication/viewWageCheckList" var="pageUrl" />
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
