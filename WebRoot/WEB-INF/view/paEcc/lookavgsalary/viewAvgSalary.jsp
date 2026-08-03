<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include  file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<style>
<!--
.gridScroller td div{
		text-align: center;
	}
-->
</style>
<script type="text/javascript">
//添加新行的工号文本框触发
	function F_HR_SubmitKeyClick_jx0007(event){
	   	if(event.keyCode==13){
	   		var emp= document.getElementById("empid_jx0007").value;
			//15119设置默认查找在职员工
			document.getElementById("onck").href=encodeURI(encodeURI("/hrm/empinfo/viewEmpIdList?pageNum=1&seach_EMPID="+emp+"&seach_LOCAL_NAME="+emp));
			//document.getElementById("onck").href=encodeURI(encodeURI("/hrm/empinfo/viewEmpIdList?pageNum=1&seach_EMP_OFFICE=15119'"));
			document.getElementById("onck").click();	
		}
 	}
 	function F_HR_ShowMore1(personId,name,empid,i,deptname,deptno,glno,glname,dutyno,dutynoname,postno,postname,postgradeno,postgradename,posino,posiname){
		//personid_
		document.getElementById("empid_jx0007").value=empid;
		$.pdialog.closeCurrent();
	}
</script>
<div class="pageHeader">
<a id="onck" name="onck" href="/hrm/empinfo/viewEmpIdList?pageNum=1" lookupGroup="person" width="950"></a>
<form action="/paEcc/lookavgsalary/viewAvgSalary" method="post" name="searchForm" onsubmit="return navTabSearch(this)">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					<spring:message code="public.title.empId"/><!--工号-->
				</td>
				<!-- 
				 /
				<spring:message code="public.title.name"/>:<!--姓名-->
				<td>
					<input type="text" id="empId" name="dwz.person.empId" 
							value="${empId}" lookupGroup="person" size="10" readonly/>
					<a class="btnLook" href="/paEcc/benchmark/searchEccBenchEmp?pageNum=1" 
						lookupGroup="person">查找查找带回</a> 
					<%-- 	
				<input id="empid_jx0007" name="dwz.person.empId" type='text' class='required' value='' size='10' onkeydown="F_HR_SubmitKeyClick_jx0007(event)" />	
					--%>	
				</td>
			</tr>
		</table>
		<div class="subBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<!-- 
								搜索
							 -->
							<input type="hidden" name="PA_MONTH" value="${empInfo.PAMONTH}"/>
							 <button type="submit">&nbsp;<spring:message code="ar.viewempcalender.title.search"/>&nbsp;</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</div>
	</form>
</div>
<div class="pageContent">
	<table class="table" width="100%">
		<thead>
			<tr>
				<th><spring:message code="display.pa.ecc.date"/><!-- 年月 --></th>
				<th><spring:message code="display.pa.ecc.pici"/><!-- 批次 --></th>
				<th><!--姓名--> 
					<spring:message code="hr.viewPersonalInfo.title.LOCAL_NAME"/>
				</th>
				<th><!-- 部门 -->
					<spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/>
				</th>
				<th>
					<spring:message code="hr.viewPersonalInfo.title.GRADE_LEVEL_NAME" />
					<!--职等-->
				</th>
				<th><!--职责-->
					<spring:message code="hr.viewPersonalInfo.title.DUTY_NAME"/>
				</th>
				<th><!--职级名称(职务)  -->
					<spring:message code="hr.viewPersonalInfo.title.POST_NAME"/>
				</th>
				<th><spring:message code="display.emp.statistics.mes206"/><!-- 入社日期  --></th>
				<th><spring:message code="ess.trans.title.resignDate"/> <!--离职日期 --></th>
				<th><spring:message code="display.pa.ecc.agreementresign"/><!-- 协议离职 --></th>
			</tr>
		</thead>
			<tr>
				<td>${empInfo.PAMONTH}</td>
				<td>${empInfo.BATCHES}</td>
				<td>${empInfo.LOCAL_NAME}</td>
				<td>${empInfo.DEPT_NAME}</td>
				<td>${empInfo.GRADE_LEVEL_NAME }</td>
		  		<td>${empInfo.DUTY_NAME }</td>
		  		<td>${empInfo.POST_NAME }</td>
				<td>
				 ${fn:substring(empInfo.DATE_STARTED,0,10)}
				
				
				</td>
				
				
				<td>
					 ${fn:substring(empInfo.DATE_LEFT,0,10)}
				</td>
				<td>
					<c:choose>
						<c:when test="${empInfo.LEFT_TYPE eq 'leftReasonType003'}"><spring:message code="hr.viewRelation.title.YES"/></c:when>
						<c:when test="${empInfo.LEFT_TYPE eq 'leftReasonType001' or 
										empInfo.LEFT_TYPE eq 'leftReasonType002'}"><spring:message code="hr.viewRelation.title.NO"/></c:when>
					</c:choose>
				</td>
			</tr>
	</table>
	<br/><br/>
	<c:if test="${nullInfo=='1'}">
		<script type="text/javascript">alert('<spring:message code="display.pa.ecc.noneemp"/>');</script>
	</c:if>
	<table width="100%" class="table">
					<c:if test="${fn:length(list) == 0 }">
					</c:if>
					<c:forEach items="${list}" var="list" varStatus="x">
							<c:if test="${x.first }">
							<thead>
							<tr>
								<th>${list.TYPE }</th>
								<th>${list.MONTH1 }&nbsp;</th>
								<th>${list.MONTH2 }&nbsp;</th>
								<th>${list.MONTH3 }&nbsp;</th>
								<th>${list.MONTH4 }&nbsp;</th>
								<th>${list.MONTH5 }&nbsp;</th>
								<th>${list.MONTH6 }&nbsp;</th>
								<th>${list.MONTH7 }&nbsp;</th>
								<th>${list.MONTH8 }&nbsp;</th>
								<th>${list.MONTH9 }&nbsp;</th>
								<th>${list.MONTH10 }&nbsp;</th>
								<th>${list.MONTH11 }&nbsp;</th>
								<th>${list.MONTH12 }&nbsp;</th>
								<th>${list.TOTAL}&nbsp;</th>
								<th>${list.AVGSALARY}&nbsp;</th>
							</tr>
							</thead>
							</c:if>
							<c:if test="${not x.first }">
							<tr>
								<td>${list.TYPE }</td>
								<td>${list.MONTH1 }&nbsp;</td>
								<td>${list.MONTH2 }&nbsp;</td>
								<td>${list.MONTH3 }&nbsp;</td>
								<td>${list.MONTH4 }&nbsp;</td>
								<td>${list.MONTH5 }&nbsp;</td>
								<td>${list.MONTH6 }&nbsp;</td>
								<td>${list.MONTH7 }&nbsp;</td>
								<td>${list.MONTH8 }&nbsp;</td>
								<td>${list.MONTH9 }&nbsp;</td>
								<td>${list.MONTH10 }&nbsp;</td>
								<td>${list.MONTH11 }&nbsp;</td>
								<td>${list.MONTH12 }&nbsp;</td>
								<td>${list.TOTAL}&nbsp;</td>
								<c:if test="${x.index lt 2 }">
						              <td>&nbsp;</td>
						       </c:if>
						       <c:if test="${x.index eq 2 }">
						              <td>${list.AVGSALARY}</td>
						       </c:if>
							</tr>
							</c:if>
					</c:forEach>
			</table>

			
</div>