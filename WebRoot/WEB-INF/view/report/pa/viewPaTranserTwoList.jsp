<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
 function  openPaTranserTwoExecl(a){
      var $this=$(a);
      var title = $this.attr("title"); 
      var $from = $("#viewPaTranserTwoList");  
      
	  var url ="/report/pa/viewPaTranserTwoExcel";
	   alertMsg.confirm(title, {
					okCall: function(){  
					   window.location =url+ (url.indexOf('?') == -1 ? "?" : "&") + $from.serialize();
					}});
    
 }
 function getSalaryProvideDatePa(){
	//var paMonth = $("#seach_YEAR_pa0108").val() + $("#seach_MONTH_pa0108").val();
	var paMonth = $("form[id='viewPaTranserTwoList'] #seach_YEAR_pa0108").val() + $("form[id='viewPaTranserTwoList'] #seach_MONTH_pa0108").val();
	var sel = $("form[id='viewPaTranserTwoList'] #seach_GIVE_DATE_pa0108");//职级
	sel.empty();
	$.ajax({
		 cache: false,
		 type: 'post',
		 async:false,
		 url: "/pa/salary/getSalaryProvideDatePa?",
		 data: 'paMonth=' + paMonth,
		 dataType:"json",
		 success: function(data) {
		 //sel.append("<option value=''><spring:message code='hr.viewPersonalInfo.title.ADDED_BY_KELI' /><!--请选择--> </option>"); 
			$.each(data, function(key,value){
					if($(data).size() > 0){
 							sel.append('<option value='+key+'>'+value+'</option>'); 
					}
			});
		 }
	});
}

 $(document).ready(function(){
	getSalaryProvideDatePa();
	
    var dValue = "${GIVE_DATE_PA0108}"; 	
	if (dValue != ""){
	   $("#seach_GIVE_DATE_pa0108 > option[value='${GIVE_DATE_PA0108}'']").attr("selected", "true");
	}
    
 });

function navTabSearch(form, navTabId){
	var $form = $(form);
	if($("#seach_YEAR_pa0108").val()==""||$("#seach_MONTH_pa0108").val()==""){
		 alert("请选择工资月");
		 navTab.reload($form.attr('action'), {data: params, navTabId:navTabId});
		 return false;
	 }

	if (form[DWZ.pageInfo.pageNum]){
		form[DWZ.pageInfo.pageNum].value = 1 ;
	}


	var params = $(form).serializeArray();
	

	if (!form[DWZ.pageInfo.pageNum]){
		params.push({name: DWZ.pageInfo.pageNum, value: 1}) ;
	}


	if($("#pagerForm", navTab.getCurrentPanel()).find("input[name='numPerPage']")){
		params.push({name: DWZ.pageInfo.numPerPage, value: $("#pagerForm", navTab.getCurrentPanel()).find("input[name='numPerPage']").val()}) ;
	}
	
	navTab.reload($form.attr('action'), {data: params, navTabId:navTabId});
	return false;
}

	
function submitForm_pa0108(){
	//
	
	if($("#seach_YEAR_pa0108").val()==""||$("#seach_MONTH_pa0108").val()==""){
		 alert("请选择工资月");
		
		 return false;
	 }
	if($("#seach_GIVE_DATE_pa0108").val()==""){
		 alert("请选择工资发放日");
		
		 return false;
	 }
	
    if($("#seach_GIVE_DATE_pa0108").val()=="当前月没有符合发放日期"){
        alert("请选择工资发放日!");
       
        return false;
    }
			
  	//var form = document.getElementById("viewPaTranserTwoList");
  	var $form = $("#viewPaTranserTwoList");
  	
  	$form.submit();
}


</script>
<div class="pageHeader" >
	
	<form id="viewPaTranserTwoList" onsubmit="return navTabSearch(this);" action="/report/pa/viewPaTranserTwoList" method="post" rel="pagerForm">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td width="5%" style="text-align:center"><%--部门名称--%>
					<spring:message code="org.orgManage.title.deptName"/>： 
				</td>
				<td width="12%" style="text-align:left">
					<ait:deptTree name="seach_DEPTNO" limit="hr" selected="${DEPTNO}"/>
				</td>
				<td width="5%" style="text-align:center"><%--工号/姓名--%>
					<spring:message code="public.title.empIdAndName"/>：
				</td>
				<td width="8%" style="text-align:left">
				    <input type="text" id="seach_EMPID" name="seach_EMPID" maxlength="25" size="8" value="${EMPID }">
				</td>
				<td width="5%" style="text-align:center"><%--工资月--%>
					<spring:message code="ar.viewarprogress.title.gongziyue"/>：
				</td>
				<td width="15%" style="text-align:left">
					<select id="seach_YEAR_pa0108" name="seach_YEAR_pa0108" style="width:75px" >
				    	<option value=""><%--请选择 --%>
				    		<spring:message code="hr.viewPersonalInfo.title.ADDED_BY_KELI"/>
				    	</option>
						<c:forEach var="i" begin="2000" end="2020" step="1"> 
					    	<option value="${i}" <c:if test="${YEAR eq i }">selected</c:if> >${i}</option>
					    </c:forEach> 
					 </select>
					<select id="seach_MONTH_pa0108" name="seach_MONTH_pa0108" onchange="getSalaryProvideDatePa()">
						<option value=""><%--请选择--%>
							<spring:message code="hr.viewPersonalInfo.title.ADDED_BY_KELI"/>
						</option>
						<option value="01" <c:if test="${MONTH eq '01' }">selected</c:if>>01</option>
						<option value="02" <c:if test="${MONTH eq '02' }">selected</c:if>>02</option>
						<option value="03" <c:if test="${MONTH eq '03' }">selected</c:if>>03</option>
						<option value="04" <c:if test="${MONTH eq '04' }">selected</c:if>>04</option>
						<option value="05" <c:if test="${MONTH eq '05' }">selected</c:if>>05</option>
						<option value="06" <c:if test="${MONTH eq '06' }">selected</c:if>>06</option>
						<option value="07" <c:if test="${MONTH eq '07' }">selected</c:if>>07</option>
						<option value="08" <c:if test="${MONTH eq '08' }">selected</c:if>>08</option>
						<option value="09" <c:if test="${MONTH eq '09' }">selected</c:if>>09</option>
						<option value="10" <c:if test="${MONTH eq '10' }">selected</c:if>>10</option>
						<option value="11" <c:if test="${MONTH eq '11' }">selected</c:if>>11</option>
						<option value="12" <c:if test="${MONTH eq '12' }">selected</c:if>>12</option>
					</select>
				</td>
				<td width="35%" style="padding: 4px;" align="center">
					<spring:message code="pa.salary.title.salaryProvideDate"/><!-- 工资发放日 -->
						<select id="seach_GIVE_DATE_pa0108" name="seach_GIVE_DATE_pa0108" class="select" >
							<option value=''><spring:message code='hr.viewPersonalInfo.title.ADDED_BY_KELI' /><!--请选择--> </option>
						</select>
				</td>
			</tr>
		</table>
		<div class="subBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent">
						
						<button   type="button" onclick="submitForm_pa0108()"><spring:message code="public.title.search"/><!-- 检索--></button>
					</div></div>
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
				  <a class="edit" onclick="openPaTranserTwoExecl(this)"<%--是否导出?--%> title="<spring:message code='rp.report.title.exportYN'/>">
				  <span><%--Excel导出--%><spring:message code="ar.addempshift.title.excelexport"/></span></a>
	           </li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="214">
		<thead>
			<tr>
				<th width="10%" align="center"><%--区分--%>
					<spring:message code="hr.viewHealth.title.INDUSTRY_DISTINGUISH_NAME"/>
				</th>
				<th width="10%" align="center"><%--工号--%>
					<spring:message code="public.title.empId"/>
				</th>
				<th width="10%" align="center"><%--姓名--%>
					<spring:message code="public.title.name"/>
				</th>
				<th width="25%" align="center"><%--金额--%>
					<spring:message code="rp.report.title.amount"/>
				</th>
				<th width="10%" align="center"><%--银行--%>
					<spring:message code="rp.report.title.bankname"/>
				</th>
				<th width="35%" align="center"><%--银行账号--%>
					<spring:message code="rp.report.title.bankcardno"/>
				</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${paTranserList}" var="item" varStatus="i">
				<tr target="PERSON_ID" rel="${item.PERSON_ID}">
					<td width="10%" align="center">${i.index + 1}</td>
					<td width="10%" align="center">${item.EMPID }</td>
					<td width="10%" align="center">${item.LOCAL_NAME }</td>
					<td width="25%" align="center" style="vnd.ms-excel.numberformat:@"> <fmt:formatNumber value="${item.ACTUAL_RELEASE_SALARY }" pattern="#,###.00" type="number"/> </td>
					<td width="15%" align="center">${item.BANK }</td>
					<td width="35%" align="center" style="vnd.ms-excel.numberformat:@">${item.CARD_NO }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<c:set value="/report/pa/viewPaTranserTwoList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>