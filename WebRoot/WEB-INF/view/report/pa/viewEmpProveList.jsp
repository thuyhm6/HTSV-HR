<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function  openEmpProveSearch(){
	 var $form = $("#viewEmpProveList",navTab.getCurrentPanel());
    var empid = $form.find("#seach_EMPID").val();
    var deptno = $form.find("#seach_DEPTNO").val();
    var year = $form.find("#seach_YEAR_pa0109").val();
    var month = $form.find("#seach_MONTH_pa0109").val();
    
    if(checkEmpPaMonth()==true){
	     window.document.getElementById("onOfficeProve").href="/report/pa/viewEmpProveList?pageNum=1&seach_EMPID="+empid+"&seach_DEPTNO="+deptno
	     		+"&seach_YEAR="+year+"&seach_MONTH="+month;
    }else{
   	 	window.document.getElementById("onOfficeProve").href="/report/pa/viewSapPaError";
    }
}
function checkEmpPaMonth(){
	 var $form = $("#viewEmpProveList",navTab.getCurrentPanel());
    var deptno = $form.find("#seach_DEPTNO").val();
    var year = $form.find("#seach_YEAR_pa0109").val();
    var month = $form.find("#seach_MONTH_pa0109").val();
    if(deptno=='' || deptno==null){
   	//alert("部门为必选项，请选择部门！");
		alertMsg.error("<spring:message code='pa.message.pa.check.deptnomustchoosed'/>");
		$form.find("#seach_DEPTNO").focus();
		return false;
    }
    if(year=='' || year==null){
   	//alert("工资年份为必选项，请选择年份！");
		alertMsg.error("<spring:message code='pa.message.pa.check.payearmustchoosed'/>");
		$form.find("#seach_YEAR_pa0109").focus();
		return false;
    }
    if(month=='' || month==null){
   	//alert("工资月份为必选项，请选择月份！");
		alertMsg.error("<spring:message code='pa.message.pa.check.pamonthmustchoosed'/>");
		$form.find("#seach_MONTH_pa0109").focus();
		return false;
    }
    return true;
}
function openEmpProveExecl(a){
     var $this=$(a);
     var title = $this.attr("title"); 
     var $from = $("#viewEmpProveList");  
	
	
	  var url ="/report/pa/viewEmpProveExcel";
	  
		   alertMsg.confirm(title, {
						okCall: function(){  
						   window.location =url+ (url.indexOf('?') == -1 ? "?" : "&") + $from.serialize();
						}});
	  
   
}
function getSalaryProvideDatePaEmp(){
	//var paMonth = $("#seach_YEAR_pa0109").val() + $("#seach_MONTH_pa0109").val();
	var paMonth = $("form[id='viewEmpProveList'] #seach_YEAR_pa0109").val() + $("form[id='viewEmpProveList'] #seach_MONTH_pa0109").val();
	var sel = $("form[id='viewEmpProveList'] #seach_GIVE_DATE_pa0109");//职级
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
	getSalaryProvideDatePaEmp();

    var dValue = "${GIVE_DATE}";    
    if (dValue != ""){
       $("#seach_GIVE_DATE_pa0109 > option[value='${GIVE_DATE}']").attr("selected", "true");
    }
	
});

function submitForm_pa0109(){
	if($("#seach_YEAR_pa0109").val()==""||$("#seach_MONTH_pa0109").val()==""){
		 alert("请选择工资月");
		 return false;
	 }
	if($("#seach_GIVE_DATE_pa0109").val()==""){
		 alert("请选择资发放日");
		
		 return false;
	 }
	
    if($("#seach_GIVE_DATE_pa0109").val()=="当前月没有符合发放日期"){
        alert("请选择工资发放日!");
       
        return false;
    }
	
  	var $from = $("#viewEmpProveList");
  	$from.submit();
}
 
</script>
<div class="pageHeader" >
	<form id="viewEmpProveList" onsubmit="return navTabSearch(this);" action="/report/pa/viewEmpProveList" method="post" rel="pagerForm">
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
					<select id="seach_YEAR_pa0109" name="seach_YEAR_pa0109" style="width:75px">
						<c:forEach var="i" begin="2012" end="2025" step="1"> 
					    	<option value="${i}" <c:if test="${YEAR eq i }">selected</c:if> >${i}</option>
					    </c:forEach> 
					 </select>
					<select id="seach_MONTH_pa0109" name="seach_MONTH_pa0109" onchange="getSalaryProvideDatePaEmp()">
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
						<select id="seach_GIVE_DATE_pa0109" name="seach_GIVE_DATE_pa0109" class="select" >
							<option value=''><spring:message code='hr.viewPersonalInfo.title.ADDED_BY_KELI' /><!--请选择--> </option>
						</select>
				</td>
			</tr>
		</table>
		<div class="subBar">
		 	<ul><li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="submitForm_pa0109()">
		 		<spring:message code="public.title.search"/><!--检索--></button></div></div></li></ul>
		</div> 
	</div>
	</form>
</div>

<div class="pageContent">
	<div class="formBar">
		<ul class="toolBar">
			 <li>
				  <a class="edit" onclick="openEmpProveExecl(this)"<%--是否导出?--%> title="<spring:message code='rp.report.title.exportYN'/>">
				  <span><%--Excel导出--%><spring:message code="ar.addempshift.title.excelexport"/></span></a>
	           </li>
		</ul>
	</div>
	<table class="table" width="100%" layoutH="214">
		<thead>
			<tr>
				<th align="center"><%--区分--%>
					<spring:message code="hr.viewHealth.title.INDUSTRY_DISTINGUISH_NAME"/>
				</th>
				<th align="center"><%--姓名--%>
					<spring:message code="public.title.name"/>
				</th>
				<th align="center"><%--护照号码 /身份证号码--%>
					<spring:message code="rp.report.title.idcardorpassport"/>
				</th>
				<th align="center"><%--职位--%>
					<spring:message code="sys.postManage.title.position"/>
				</th>
				<th align="center"><%--在职期间--%>
					<spring:message code="rp.report.title.officepersoid"/>
				</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${empProveList}" var="item" varStatus="i">
				<tr target="EMPID" rel="${item.EMPID}">
					<td align="center">${i.index+1 }</td>
					<td align="center">${item.LOCAL_NAME }</td>
					<td align="center" style="vnd.ms-excel.numberformat:@">${item.IDCARD_NO }</td>
					<td align="center">${item.POSTNO }</td>
					<td align="center">${item.DATE_STARTED }&nbsp;&nbsp;
						<c:if test="${item.DATE_LEFT ne null}">~&nbsp;&nbsp;${item.DATE_LEFT }</c:if>
					</td>
				</tr>
			</c:forEach>    
		</tbody>
	</table>
	<c:set value="/report/pa/viewEmpProveList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>