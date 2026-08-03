<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script text="java/script">
function exceptMonthWorkExcle(){
	var obj = document.getElementById('dataForm');
	obj.action = "/ar/attendanceView/viewDetailWorkControlExcel";
	obj.submit();
	obj.action = "/ar/attendanceView/viewDetailWorkControl";
}
function changeOption(k){
	for(var i=1;i<6;i++){
		if(i != k){
			//其他的select
			 var obj=document.getElementById('ITEM'+i);
			//修改的select
       		 var objk = document.getElementById('ITEM'+k);
       		for (var j = 0; j < obj.options.length; j++) {        
                if (obj.options[j].value == objk.value) {        
                    obj.options.remove(j);        
                    break;        
                }        
            }  
			//alert(objk.selectedIndex);
			//其他的select中移除修改的select值
			//obj.options.remove(objk.selectedIndex);
			//修改的select中的原来的值
			//alert(v);
			//alert(objk.options[v].value);
			//alert(objk.options[v].text);
			 //obj.options.add(new Option("key","value")); 
		}
	}
}
function loadItem(){
	for(var i=1;i<6;i++){
		changeOption(i);
	}
}
</script>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/ar/attendanceView/viewDetailWorkControl" method="post" rel="pagerForm" id="dataForm">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						<%--部门--%><spring:message code="ar.attendanceView.viewNoSwipingCard.deptName"/>
					</td>
					<td>	
					<ait:deptList name="seach_DEPT_NO" limit="ar"  id="viewMonWo_seachDept"/>
					<ait:deptTreeIcon name="seach_DEPT_NO" limit="ar" id="viewMonWo_seachDept" selected="${DEPT_NO}"/>
					</td>
					<td>
						<%--开始日期--%><spring:message code="ar.viewcycleparameter.title.kaishiriqi"/>
					</td>
					<td>
						<input type="text" name="seach_START_DATE"  class="date required" readonly="true" value="${START_DATE}"/>
						<a class="inputDateButton"><spring:message code="public.title.choose" /><!-- 选择 --></a>
					</td>
					<td>
						<%--结束日期--%><spring:message code="ar.viewcycleparameter.title.jieshuriqi"/>
					</td>
					<td>
						<input type="text" name="seach_END_DATE"  class="date required" readonly="true" value="${END_DATE}"/>
						<a class="inputDateButton"><spring:message code="public.title.choose" /><!-- 选择 --></a>
					</td>
				</tr>
				<tr>
				  <td>在职状态</td>
						<td>
				 	<ait:SelectSyCodeByCpnyID id="seach_EmpOffice" name="seach_EmpOffice" parentNo="15118" selected="${EmpOffice}" cnpyID="${LoginUser.cpnyId}" limit="all"/>
						</td>
						<td>人员类型组 </td>
						<td>
							<input type="hidden" id="ar0121_limit" name="limit" value="ar">
							<input type="hidden" id="ar0121_seach_CPNY" name="seach_CPNY" value="${LoginUser.cpnyId}">
							<ait:SelectEmpTypeCode  id="ar0121_seach_JobTypeGroupNo"  name="seach_JobTypeGroupNo" selected="${JobTypeGroupNo}" limit="ar" type="group"
								onChangeName="ajaxEmpTypeForGroupToList(-1,ar0121_seach_JobTypeGroupNo,ar0121_seach_EmpTypeCodeNo,ar0121_seach_CPNY,ar0121_limit)"/>
						</td>
						<td>人员类型 </td>
						<td>
		 					<ait:SelectEmpTypeCode id="ar0121_seach_EmpTypeCodeNo" name="seach_EmpTypeCodeNo" selected="${EmpTypeCodeNo}" limit="ar"/>
						</td>	
				</tr>
				<tr>
					<td>
						<%--监控项目--%><spring:message code="ar.viewmonthwork.jiankongxiangmu"/>
					</td>
					<td>
						<select name="seach_ITEM1" id="ITEM1" onChange="changeOption(1)">
							<c:forEach items="${controlMap}" var="item">
								<option value="${item.key}"<c:if test="${item.key eq ITEM1}">selected</c:if>>${item.value}</option>
							</c:forEach>
						</select>
					</td>
					<td>
						<select name="seach_ITEM2" id="ITEM2" onChange="changeOption(2)">
							<c:forEach items="${controlMap}" var="item">
								<option value="${item.key}"<c:if test="${item.key eq ITEM2}">selected</c:if>>${item.value}</option>
							</c:forEach>
						</select>
					</td>
					<td>
						<select name="seach_ITEM3" id="ITEM3" onChange="changeOption(3)">
							<c:forEach items="${controlMap}" var="item">
								<option value="${item.key}"<c:if test="${item.key eq ITEM3}">selected</c:if>>${item.value}</option>
							</c:forEach>
						</select>
					</td>
					<td>
						<select name="seach_ITEM4" id="ITEM4" onChange="changeOption(4)">
							<c:forEach items="${controlMap}" var="item">
								<option value="${item.key}"<c:if test="${item.key eq ITEM4}">selected</c:if>>${item.value}</option>
							</c:forEach>
						</select>
					</td>
					<td>
						<select name="seach_ITEM5" id="ITEM5" onChange="changeOption(5)">
							<c:forEach items="${controlMap}" var="item">
								<option value="${item.key}"<c:if test="${item.key eq ITEM5}">selected</c:if>>${item.value}</option>
							</c:forEach>
						</select>
					</td>
					
				</tr>
			</table>
			<script text="java/script">loadItem();</script>
			<div class="subBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">
									<!--检索--><spring:message code="public.title.search" />
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

<div class="formBar">
	<ul class="toolBar">
		<li>
			<a class="edit" onclick="exceptMonthWorkExcle()" title="<spring:message code='rp.report.title.exportYN'/>">
				<span><%--Excel导出--%>	<spring:message code="ar.addempshift.title.excelexport"/></span>
			</a>
		</li>
	</ul>
</div>

<table class="table" layoutH="253">
	<thead>
		<tr>
			<th align="center" width="80" >社号</th>
			<th align="center" width="80" ><%--姓名--%><spring:message code="hr.viewPersonalInfo.title.LOCAL_NAME"/></th>
			<th align="center" width="170" ><%--部门--%><spring:message code="ar.attendanceView.viewNoSwipingCard.deptName"/></th>
			<th align="center" width="170" >考勤日期</th>
			<th align="center" width="170">开始时间</th>
			<th align="center" width="170">结束时间</th>
			<th align="center" width="170">考勤项目</th>
			<th align="center" width="170">时长</th>
			<%--<th align="center" width="70" >${controlMap[ITEM1]}<spring:message code="ar.attendanceview.cishu"/></th>
			<th align="center" width="70" >${controlMap[ITEM2]}<spring:message code="ar.attendanceview.paiming"/></th>
			<th align="center" width="70" >${controlMap[ITEM2]}<spring:message code="ar.attendanceview.cishu"/></th>
			<th align="center" width="70" >${controlMap[ITEM3]}<spring:message code="ar.attendanceview.paiming"/></th>
			<th align="center" width="70" >${controlMap[ITEM3]}<spring:message code="ar.attendanceview.cishu"/></th>
			<th align="center" width="70" >${controlMap[ITEM4]}<spring:message code="ar.attendanceview.paiming"/></th>
			<th align="center" width="70" >${controlMap[ITEM4]}<spring:message code="ar.attendanceview.cishu"/></th>
			<th align="center" width="70" >${controlMap[ITEM5]}<spring:message code="ar.attendanceview.paiming"/></th>
			<th align="center" width="70" >${controlMap[ITEM5]}<spring:message code="ar.attendanceview.cishu"/></th>--%>
		</tr>
		<%-- <tr>
			<th align="center" width="70">排名<spring:message code="ar.attendanceview.paiming"/></th>
			<th align="center" width="70">次数<spring:message code="ar.attendanceview.cishu"/></th>
			<th align="center" width="70">排名<spring:message code="ar.attendanceview.paiming"/></th>
			<th align="center" width="70">次数<spring:message code="ar.attendanceview.cishu"/></th>
			<th align="center" width="70">排名<spring:message code="ar.attendanceview.paiming"/></th>
			<th align="center" width="70">次数<spring:message code="ar.attendanceview.cishu"/></th>
			<th align="center" width="70">排名<spring:message code="ar.attendanceview.paiming"/></th>
			<th align="center" width="70">次数<spring:message code="ar.attendanceview.cishu"/></th>
			<th align="center" width="70">排名<spring:message code="ar.attendanceview.paiming"/></th>
			<th align="center" width="70">次数<spring:message code="ar.attendanceview.cishu"/></th>
		</tr> --%>
	</thead>
	<tbody>
		<c:forEach items="${dataList}" var="month">
			<tr>
				<td>${month.EMPID}</td>
				<td>${month.PERSONNAME}</td>
				<td>${month.DEPTNAME}</td>
				<td>${month.AR_DATE_STR}</td>
				<td>${month.FROM_TIME}</td>
				<td>${month.TO_TIME}</td>
				<td>${month.ITEM_NAME}</td>
				<td>${month.QUANTITY}</td>
				<%--<td>${month.ITEM_NO1}</td>
				<td>${month.DESC2}</td>
				<td>${month.ITEM_NO2}</td>
				<td>${month.DESC3}</td>
				<td>${month.ITEM_NO3}</td>
				<td>${month.DESC4}</td>
				<td>${month.ITEM_NO4}</td>
				<td>${month.DESC5}</td>
				<td>${month.ITEM_NO5}</td>--%>
			</tr>
		</c:forEach>
	</tbody>
</table>
	<c:set value="/ar/attendanceView/viewDetailWorkControl" var="pageUrl" />
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>
