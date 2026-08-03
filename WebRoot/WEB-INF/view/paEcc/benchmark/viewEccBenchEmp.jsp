<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<style>
<!--
	.gridScroller .gridTbody td div{
		text-align: center;
	}
-->
</style>
<script type="text/javascript">

function estimateEcc(form){
		var $form = form;
		var msg1 = "请输入工号";
		var msg2 = "请输入预离职日期";
		var msg3 = "请输入协议金";
		var empId = document.getElementById("empId").value;
		var stime = document.getElementById("leavetime").value;
		var xieyijin = document.getElementById("xieyijin").value;
		if(empId==null||empId==''){
			alert(msg1);
			return false;
		}
		if(stime==null||stime==''){
			alert(msg2);
			return false;
		}
		if(xieyijin==null||xieyijin==''){
			alert(msg3);
			return false;
		}
		document.getElementById("employeeId").value = empId;
		$.ajax({
			 cache: false,
			 type: 'post',
			 url:$form.attr("action"),
			 data: {'employeeId':empId,'STIME':stime,'xieyijin':xieyijin},
			 dataType:"json",
			 success:function(){
				if(data.statusCode=='200'){
					alert("200");
					navTabNum('/paEcc/benchmark/viewEccBenchEmp?pageNum=1&menuNo=124920&navTabId=jx0001',
							'jx0001','预估补偿金');
				}
			},
			error: DWZ.ajaxError
		});
		return false;
		
	}
</script>
<div class="pageHeader">
<form action="/paEcc/benchmark/viewEccBenchEmp" method="post" name="searchForm" onsubmit="return navTabSearch(this)"><!--  -->
 <input type="hidden" name="numPerPage" value="10" />
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					<spring:message code="public.title.empId"/><!--工号-->
				</td>
				<td>
					<input type="text" id="empId" name="dwz.person.empId"  value="${empId}" lookupGroup="person" size="10" readonly/>
					<input type="hidden" id="employeeId" name="employeeId" value="${empId}">
					<a class="btnLook" href="/paEcc/benchmark/searchEccBenchEmp?pageNum=1" 
						lookupGroup="person"><spring:message code="pa.insurance.title.lookUpAndBack"/><%--查找带回--%></a>
				</td>
				<td><spring:message code="display.pa.ecc.expectresigndate"/><!--预离职日期  --></td>
				<td>
					<input type="text" id="leavetime" name="STIME" value="${STIME}" class="date"
										yearstart="-20" yearend="20" readonly="true" />
				</td>
				<td><spring:message code="display.pa.ecc.agreementpay"/><!-- 协议金 --></td>
				<td>
					<input name="xieyijin" id="xieyijin" type="text" value="${xieyijin}">
				</td>
			</tr>
		</table>
		<div class="subBar">
			<ul>
				<li>
					<!-- <div class="buttonActive">
						<div class="buttonContent"><button type="submit">???</button></div>
					</div> -->
					<div class="buttonActive">
						<div class="buttonContent">
							<!-- 
							<button onclick="estimateEcc()">&nbsp;基准生成&nbsp;</button>
							 -->
							 <button type="submit">&nbsp;<spring:message code="display.pa.ecc.basicgenerate"/>&nbsp;</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</div>
	</form>
</div>
<div class="pageContent">
	<table width="980" class="table">
		<!-- 
		 -->
		<thead>
			<tr>
				<th style="text-align: center;"><!-- 区分 --><spring:message code="hr.viewHealth.title.INDUSTRY_DISTINGUISH_NAME"/></th>
				<th><!-- 姓名 --><spring:message code="hr.viewPersonalInfo.title.LOCAL_NAME"/></th>
				<th><!-- 部门 -->
					<spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/>
				</th>
				<th><!--职责-->
					<spring:message code="hr.viewPersonalInfo.title.DUTY_NAME"/>
				</th>
				<th><!--职级名称(职务)  -->
					<spring:message code="hr.viewPersonalInfo.title.POST_NAME"/>
				</th>
				<th style="text-align: center;"><!-- 平均工资 --><spring:message code="display.pa.averagewage"/></th>
				<th style="text-align: center;"><!--补偿月数 --><spring:message code="display.pa.ecc.compensatemonth"/></th>
				<th style="text-align: center;"><!--法定平均工资 --><spring:message code="display.pa.ecc.legalavgwage"/></th>
				<th style="text-align: center;"><!--法定补偿月数 --><spring:message code="display.pa.ecc.legalcompenmonth"/></th>
				<th style="text-align: center;"><!--经济补偿金 --><spring:message code="display.pa.ecc.compenpayment"/></th>
				<th style="text-align: center;"><!--课税 --><spring:message code="display.pa.tax"/></th>
				<th style="text-align: center;"><!--实发经济补偿金 --><spring:message code="display.pa.ecc.tyicalpayment"/></th>
			</tr>
		</thead>
		<c:forEach items="${eccList }" var="list">
			<tr align="center" onclick="band('#f4f7fa','black')">
				<td>
					${list.LEFT_TYPE }
				</td>
				<td>${list.LOCAL_NAME}</td>
				<td>${list.DEPTNAME}</td>
		  		<td>${list.DUTY_NAME }</td>
				<td>${list.POST_NAME}</td>
				<td>${list.AVG_SALARY }</td>
				<td>${list.COMPENSATE }</td>
				<td>${list.AVG_SALARY_FD }</td>
				<td>${list.COMPENSATE_FD }</td>
				<td>${list.ECCAMT }</td>
				<td>${list.TAX }</td>
				<td>${list.ECCAMT - list.TAX}</td>
			</tr>
		</c:forEach>
</table>
</div>