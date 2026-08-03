<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function f_ObjectCalcFlagUpdate(index,calc_flag,person_id,empid,flag){
	
	var numPerPage=$("#pageNum_pa0507").val();
	var params = [];
	params.push({
		name: 'seach_PERSON_ID',
		value: person_id
	});
	CALC_FLAG = calc_flag == 'Y' ? 'N': 'Y';
	var imgValue=empid+"_gif";
	var src=$("#"+imgValue)[0].src;
	var srcNum=src.lastIndexOf(".");
	var srcText=src.substring(parseInt(srcNum)-1);
	if(srcText=="Y.gif"){
		CALC_FLAG="N";
	}else if(srcText=="N.gif"){
		CALC_FLAG="Y";
	}
	params.push({
		name: 'seach_CALC_FLAG',
		value: CALC_FLAG
	});
	$.ajax({
	  url: '/pa/wagebase/updatePaSalaryCalcFlagByPersonId?empid='+empid+"&numPerPage="+numPerPage,
	  async:true,
	  type:'post',
	  data: params,
	  cache: true,
	  success: function(responseText){
		var responseText1=responseText.split(",");
		var imgValue=empid+"_gif";
		if (responseText1[0] == "Y"){
			//成功之后不提示，否则太麻烦
			//alert('<spring:message code="alert.message.pa.salary.add_success"/>');
			//页面重载	
			url="/resources/images/"+CALC_FLAG+".gif";
			$("#"+imgValue).attr("src",url);
		}else{
			//错误之后提示
			url="/resources/images/N.gif";
			$("#"+imgValue).attr("src",url);
			alert('<spring:message code="alert.message.pa.salary.add_fail"/>');
		}
	  }
	});
} 
function onTopClick(empid) { 
	var emp="#"+empid;
	//alert(empid);
	
	window.location.hash = emp;   
} 

function reportExcel(a){
	var $this=$(a);
    var title = $this.attr("title"); 
    var $form = $("#viewPaSalaryObject");  
    
	  var url ="/pa/wagebase/viewPaSalaryObjectExcel";
	   alertMsg.confirm(title, {
					okCall: function(){  
					   window.location =url+ (url.indexOf('?') == -1 ? "?" : "&") + $form.serialize();
				}});
}

//工资计算对象
function insertSalaryCalculationObject(){
	var paramSalary={};

	paramSalary["PA_MONTH_pa0413"]=$("#seach_paYear",navTab.getCurrentPanel()).val()+$("#seach_paMonth",navTab.getCurrentPanel()).val();//工资月 
	if($("#PA_GIVE_DATE",navTab.getCurrentPanel()).val() == null ||$("#PA_GIVE_DATE",navTab.getCurrentPanel()).val() ==""){
		alertMsg.error('<spring:message code="zxc.paSalary.title.PLEASE_CHOOSE_SALARY_GIVE_DATE"/>');
		return;
	}
	paramSalary["PA_GIVE_DATE"]=$("#PA_GIVE_DATE",navTab.getCurrentPanel()).val();//工资发放日
	paramSalary["CALTYPE"]="PA";//工资标识
	var date=$("#PA_GIVE_DATE",navTab.getCurrentPanel()).val();
	var year=$("#seach_paYear",navTab.getCurrentPanel()).val();
	var month=$("#seach_paMonth",navTab.getCurrentPanel()).val();
	var yearMonth=year+month;
	
	if (confirm ('<spring:message code="zxc.paSalary.title.COMMIT_CONFIRM"/><spring:message code="pa.insurance.title.salaryMonth"/>:'+yearMonth+',<spring:message code="pa.salary.title.salaryProvideDate"/>:'+date)){//确定提交吗？
	  	$.ajax({
			type: 'POST',      
			url: "/pa/wagebase/salaryCalculationObject",
			data: paramSalary,
			dataType: "json",
			cache: false,
			success: function(data){
				alertMsg.info(data);
			},
			error: DWZ.ajaxError
		});	
		
		return false;
	}
	
}

function submitForm(form){
	var $PA_GIVE_DATE = $("#PA_GIVE_DATE",navTab.getCurrentPanel());
	if($PA_GIVE_DATE.val() == ''){
		//请选择工资发放日
		alertMsg.info('<spring:message code="alert.message.paGiveDate"/>');
		return false;
	}
	return true;
}
function navTabSearchPaMaster(form, navTabId){
	var $form = $(form);

	if (form[DWZ.pageInfo.pageNum]){
		form[DWZ.pageInfo.pageNum].value = 1 ;
	}
	if(submitForm(form)){
		var params = $(form).serializeArray();
		if (!form[DWZ.pageInfo.pageNum]){
			params.push({name: DWZ.pageInfo.pageNum, value: 1}) ;
		}

		navTab.reload($form.attr('action'), {data: params, navTabId:navTabId});
	}
	return false;
}
function pageFromSea(a){
	//$("#id",navTab.getCurrent())    根据id 获取当前navTab的dom节点
	
	var seach_KEY=$("#seach_KEY",navTab.getCurrentPanel()).val();
	//$("#id",navTab.getCurrent())   
	//$("#pagerForm",navTab.getCurrentPanel());
	$("#pagerForm",navTab.getCurrentPanel()).attr("action", "/pa/wagebase/viewPaSalaryObject?seach_KEY="+seach_KEY);
}

function navTabSearch1(form, navTabId){
	var $form = $(form);
	var pageNum=$("#pageNum_pa0507").val();	
	if (form[DWZ.pageInfo.pageNum]){
		form[DWZ.pageInfo.pageNum].value = 8 ;
	}
	
	var params = $(form).serializeArray();
	if (!form[DWZ.pageInfo.pageNum]){
		params.push({name: DWZ.pageInfo.pageNum, value: pageNum}) ;
	}
	
	if($("#pagerForm", navTab.getCurrentPanel()).find("input[name='numPerPage']")){
		params.push({name: DWZ.pageInfo.numPerPage, value: $("#pagerForm", navTab.getCurrentPanel()).find("input[name='numPerPage']").val()}) ;
	}
	
	navTab.reload($form.attr('action'), {data: params, navTabId:navTabId});
	return false;
}





 




</script>
<div class="pageHeader">
	<input type="hidden" value="${pageNum}" id="pageNum_pa0507"/>
	<form id="viewPaSalaryObject" name="viewPaSalaryObject" 
		onsubmit="return navTabSearchPaMaster(this,'${request_navTabId}');" 
		action="/pa/wagebase/viewPaSalaryObject?numPerPage=${numPerPage}" 
		method="post" rel="pagerForm">
		<div class="searchBar">
			<table class="searchContent" >
				<tr>
					<td >
						<spring:message code="pa.insurance.title.salaryMonth"/><!--工资月-->
						<ait:date yearName="seach_paYear" monthName="seach_paMonth" yearSelected="${paYear}" monthSelected="${paMonth}"/>
					</td>
					<td >
						<ul>
							<li style="float:left;padding-top:5px;">
					    		<spring:message code="pa.salary.title.salaryProvideDate"/>：<!-- 工资发放日 -->
							</li>
							<li style="float:left;">
								<input id="PA_GIVE_DATE" name="seach_PA_GIVE_DATE" class="required date" value="${PA_GIVE_DATE }">
							</li>
						</ul>
					</td>
				</tr>
				<tr>
					<td >
						<spring:message code="public.title.empId"/><!--工号-->/
						<spring:message code="public.title.name"/><!--姓名-->：
						<input type="text" id="seach_KEY" name="seach_KEY" value="${KEY}" />
					</td>
					<td >
						<spring:message code="public.title.deptName"/><!--部门-->：
						<ait:deptTree name="seach_DEPTNO" limit="ar" selected="${DEPTNO}" />
					</td>
					<td >
						<!--工资对象-->
						<spring:message code="pa.wagebase.title.caculateFlag1"/>：
						<select id="seach_CALC_FLAG_NEW" name="seach_CALC_FLAG_NEW" >
							<option value=""><%-- 请选择 --%>
								<spring:message code="pa.insurance.title.pleaseChoose"/>
							</option>
							<option value="Y" <c:if test="${CALC_FLAG_NEW eq 'Y' }">selected</c:if>>&nbsp;&nbsp;Y</option>
							<option value="N" <c:if test="${CALC_FLAG_NEW eq 'N' }">selected</c:if>>&nbsp;&nbsp;N</option>
						</select>
					</td>
					<td >
						<spring:message code="pa.wagebase.title.caculateFlag2"/><!-- 是否可计算工资 -->：
						<select id="seach_NOT_FLAG" name="seach_NOT_FLAG" >
							<option value=""><%-- 请选择 --%>
								<spring:message code="pa.insurance.title.pleaseChoose"/>
							</option>
							<option value="Y" <c:if test="${NOT_FLAG eq 'Y' }">selected</c:if>>&nbsp;&nbsp;Y</option>
							<option value="N" <c:if test="${NOT_FLAG eq 'N' }">selected</c:if>>&nbsp;&nbsp;N</option>
						</select>
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li>
						<div class="buttonActive">
							<a onclick="insertSalaryCalculationObject()">
								<span>
									<spring:message code="zxc.pa.wagebase.title.SAL_CALCULATE_OBJECT"/><!-- 生成工资发放对象 -->
								</span>
							</a>
						</div>
					</li>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">
									<spring:message code="public.title.search"/><!--检索-->
								</button>
							</div>
						</div>
					</li>
					<li>
						<div class="buttonActive">
							<a onclick="reportExcel(this)"  <%--是否导出?--%> title="<spring:message code='rp.report.title.exportYN'/>">
							  	<span><%--Excel导出--%><spring:message code="ar.addempshift.title.excelexport"/></span></a>
						</div>
					</li>
				</ul>
			</div>
		</div>
	</form>
</div>

<div class="pageContent">
	
	<c:set value="/pa/wagebase/updatePaObjectView?PERSON_ID={sid}" var="edit_Url" />
	<c:set value="600" var="edit_width"/>
	<c:set value="400" var="edit_height"/>
	<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>

	<table id="abcd123" name="abcd123" class="table" width="100%" layoutH="180">
		<thead>
			<tr>
				<th width="5%" orderField="nlssort(PA_MONTH,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
					<spring:message code="pa.insurance.title.salaryMonth"/><!--工资月-->
				</th>
				<th width="6%" orderField="nlssort(PA_GIVE_DATE,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
					<spring:message code="pa.salary.title.salaryProvideDate"/><!-- 工资发放日 -->
				</th>
				<th width="5%" orderField="EMPID" class="${orderDirection}">
					<spring:message code="public.title.empId"/><!--工号-->
				</th>
				<th width="5%" orderField="nlssort(CHINESE_NAME,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
					<spring:message code="hr.viewPersonalInfo.title.LOCAL_NAME"/><!--姓名-->
				</th>
				<th width="5%" orderField="nlssort(DEPTNAME,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
					<spring:message code="public.title.deptName"/><!--部门-->
				</th>
				<%--<th width="6%" orderField="nlssort(DEPT_DISTINGUISH_NAME,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
					<spring:message code="org.orgManage.title.deptDistinct"/><!--部门区分-->
				</th>
				--%><th width="4%" orderField="nlssort(POST_GRADE_NAME,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
					<spring:message code="pa.insurance.title.postGrade"/><!--职级-->
				</th>
				<th width="6%" orderField="nlssort(EMP_STATUS,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
					<spring:message code="hr.viewPersonalInfo.title.STATUS_NAME" /><!--员工状态-->
				</th>
				<th width="5%" orderField="nlssort(JOIN_COMPANY_DATE,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
					<spring:message code="pa.insurance.title.entryCpmpanyDate"/><!--入司日期-->
				</th>
				<th width="5%" orderField="nlssort(DATE_LEFT,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
					<spring:message code="pa.insurance.title.resignDate"/><!--离职日期-->
				</th>
                <%--
				<th width="5%" orderField="nlssort(SETTLEMENT_DATE,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
					<spring:message code="pa.wagebase.title.salaryCaculateDate"/><!--工资计算日期-->
				</th>
                --%>
				<th width="10%" orderField="CALC_FLAG" class="${orderDirection}"><!--工资对象-->
					<spring:message code="pa.wagebase.title.caculateFlag1"/>
				</th>
				<th width="9%" orderField="nlssort(NOT_FLAG,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
					<spring:message code="pa.wagebase.title.caculateFlag2"/><!-- 是否可计算工资 -->
				</th>
				<th width="5%" orderField="nlssort(REMARK,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
					<spring:message code="hr.viewPromote.title.REMARK"/><!-- 备注  -->
				</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${itemList}" var="item" varStatus="i">
				<tr target="sid" rel="${item.PERSON_ID}&PA_MONTH=${item.PA_MONTH }&PA_GIVE_DATE=${item.PA_GIVE_DATE }&pageNum=${pageNum}" id="${item.EMPID}" name="${item.EMPID}" >
					<td width="5%" class="td_center">${item.PA_MONTH }</td>
					<td width="6%" class="td_center">${item.PA_GIVE_DATE }</td>
					<td width="5%">${item.EMPID}</td>
					<td width="5%">${item.CHINESE_NAME}</td>
					<td width="5%">${item.DEPTNAME}</td>
					<%--<td width="6%" class="td_center">${item.DEPT_DISTINGUISH_NAME}</td>
					--%><td width="4%" class="td_center">${item.POST_GRADE_NAME}</td>
					<td width="6%" class="td_center">${item.EMP_STATUS}</td>
					<td width="5%">${item.DATE_STARTED}</td>
					<td width="5%">${item.DATE_LEFT}</td>
					<%--
                    <td width="5%">${item.SETTLEMENT_DATE}</td>
                     --%>
					<td width="10%" class="td_center">
					<c:if test="${item.CALC_FLAG ne null && item.CALC_FLAG ne '' }">
						<img id="${item.EMPID}_gif" src="/resources/images/${item.CALC_FLAG}.gif" onclick="f_ObjectCalcFlagUpdate(${i.index},'${item.CALC_FLAG}','${item.PERSON_ID}','${item.EMPID}','${item.CALC_FLAG}')"
						style="cursor:pointer;"/>
					</c:if>
					</td>
					<td width="9%" class="td_center">
					<!--  onclick="f_ObjectCalcFlagUpdate(${i.index},'${item.NOT_FLAG}','${item.PERSON_ID}')" -->
						<c:if test="${item.NOT_FLAG ne null && item.NOT_FLAG ne ''}">
							<img src="/resources/images/${item.NOT_FLAG}.gif" />
						</c:if>
					</td>
					<td width="5%" class="td_center">${item.REMARK }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table> 
	<c:set value="/pa/wagebase/viewPaSalaryObject" var="pageUrl" />
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>
