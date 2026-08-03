<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function f_ObjectCalcFlagUpdate(index,calc_flag,person_id,empid){
	
	var params = [];
	params.push({
		name: 'seach_PERSON_ID',
		value: person_id
	});
	CALC_FLAG = calc_flag == 'Y' ? 'N': 'Y';
	var imgValue=empid+"_gif";
	//$("#seach_PARAM_NO",navTab.getCurrentPanel()).val();
	var src=$("#"+imgValue,navTab.getCurrentPanel())[0].src;
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
	  url: '/pa/bonus/updateBonusCalcFlagByPersonId',
	  data: params,
	  type:'post',
	  cache: false,
	  success: function(responseText){
		if (responseText == "Y"){
			//成功之后不提示，否则太麻烦
			//alert('<spring:message code="alert.message.pa.salary.add_success"/>');
			//页面重载
			//navTabSearch_pa0612(document.viewBonusObject);
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

function reportExcel(a){
	var $this=$(a);
    var title = $this.attr("title"); 
    var $form = $("#viewBonusObject"); 
    
	  var url ="/pa/bonus/viewBonusObjectExcel";
	   alertMsg.confirm(title, {
					okCall: function(){  
					   window.location =url+ (url.indexOf('?') == -1 ? "?" : "&") + $form.serialize();
				}});
}

//奖金计算对象
function insertBonusCalculationObject(){
	var paramSalary={};

	paramSalary["PA_MONTH_pa0413"]=$("#seach_bonusYear",navTab.getCurrentPanel()).val()+$("#seach_bonusMonth",navTab.getCurrentPanel()).val();//工资月
	if($("#BONUS_GIVE_DATE",navTab.getCurrentPanel()).val() == null || $("#BONUS_GIVE_DATE",navTab.getCurrentPanel()).val() == ""){
		alertMsg.error('<spring:message code="zxc.paBonus.title.PLEASE_CHOOSE_BONUS_GIVE_DATE"/>');
		return;
	}
	paramSalary["PA_GIVE_DATE"]=$("#BONUS_GIVE_DATE",navTab.getCurrentPanel()).val();//工资发放日
	paramSalary["CALTYPE"]="BN";//工资标识
	
	var date=$("#BONUS_GIVE_DATE",navTab.getCurrentPanel()).val();
	var year=$("#seach_bonusYear",navTab.getCurrentPanel()).val();
	var month=$("#seach_bonusMonth",navTab.getCurrentPanel()).val();
	
	var yearMonth=year+month;
	
	//<spring:message code="pa.salary.title.salaryProvideDate"/>：
	if (confirm ('<spring:message code="zxc.paBonus.title.COMMIT_CONFIRM"/>	<spring:message code="pa.insurance.title.salaryMonth"/>:'+yearMonth+',<spring:message code="pa.salary.title.salaryProvideDate"/>:'+date)){//确定提交吗？
	  	$.ajax({
			type: 'POST',
			url: "/pa/bonus/bonusCalculationObject",
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
	var $give_date = $("#BONUS_GIVE_DATE",navTab.getCurrentPanel());
	if($give_date.val() == ''){
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
function navTabSearch_pa0612(form, navTabId){
	var $form = $(form);
	var pageNum=$("#pageNum_pa0612").val();	
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
	<input type="hidden" value="${pageNum}" id="pageNum_pa0612"/>
	<form id="viewBonusObject" name="viewBonusObject" 
	onsubmit="return navTabSearchPaMaster(this,'${request_navTabId}');" 
	action="/pa/bonus/viewBonusObject?numPerPage=${numPerPage}" method="post" rel="pagerForm">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td width="25%" style="padding: 4px;">
						<spring:message code="pa.insurance.title.salaryMonth"/><!--工资月-->
						<ait:date yearName="seach_bonusYear" monthName="seach_bonusMonth" yearSelected="${bonusYear}" monthSelected="${bonusMonth}"/>
					</td>
					<td width="35%" style="padding: 4px;" align="center">
						<ul>
							<li style="float:left;padding-top:5px;">
					    		<spring:message code="pa.salary.title.salaryProvideDate"/>：<!-- 工资发放日 -->
							</li>
							<li style="float:left;">
								<input id="BONUS_GIVE_DATE" name="seach_BONUS_GIVE_DATE" class="required date" value="${BONUS_GIVE_DATE }" >
							</li>
						</ul>
					</td>
				</tr>
				<tr>
					<td width="25%" style="padding: 4px;">
						<spring:message code="public.title.empId"/><!--工号-->/
						<spring:message code="public.title.name"/><!--姓名-->：
						<input type="text" name="seach_KEY" value="${KEY}" />
					</td>
					<td width="25%" style="padding: 4px;">
						<spring:message code="public.title.deptName"/><!--部门-->：
						<ait:deptTree name="seach_DEPTNO" limit="ar" selected="${DEPTNO}" />
					</td>
					<td width="25%" style="padding: 4px;">
						<spring:message code="pa.insurance.title.caculateFlag"/><!--计算标识-->：
						<select id="seach_BONUS_CALC_FLAG" name="seach_BONUS_CALC_FLAG" >
							<option value=""><%-- 请选择 --%>
								<spring:message code="pa.insurance.title.pleaseChoose"/>
							</option>
							<option value="Y" <c:if test="${CALC_FLAG eq 'Y' }">selected</c:if>>&nbsp;&nbsp;Y</option>
							<option value="N" <c:if test="${CALC_FLAG eq 'N' }">selected</c:if>>&nbsp;&nbsp;N</option>
						</select>
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li>
						<div class="buttonActive">
							<a onclick="insertBonusCalculationObject()">
								<span>
									<spring:message code="zxc.pa.wagebase.title.BONUS_CALCULATE_OBJECT"/><!-- 生成奖金计算对象 -->
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

	<c:set value="/pa/bonus/updateBonusObjectView?PERSON_ID={sid}" var="edit_Url" />
	<c:set value="600" var="edit_width"/>
	<c:set value="400" var="edit_height"/>
	<%@ include file="/WEB-INF/view/inc/includeButton.jsp"%>
	
	<table class="table" width="100%" layoutH="180">
		<thead>
			<tr>
				<th width="5%" orderField="nlssort(PA_MONTH,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
					<spring:message code="pa.insurance.title.salaryMonth"/><!--工资月-->
				</th>
				<th width="6%" orderField="nlssort(GIVE_DATE,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
					<spring:message code="pa.salary.title.salaryProvideDate"/><!-- 工资发放日 -->
				</th>
				<th width="6%" orderField="EMPID" class="${orderDirection}">
					<spring:message code="public.title.empId"/><!--工号-->
				</th>
				<th width="6%" orderField="nlssort(CHINESE_NAME,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
					<spring:message code="hr.viewPersonalInfo.title.LOCAL_NAME"/><!--姓名-->
				</th>
				<th width="6%" orderField="nlssort(DEPTNAME,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
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
				<th width="6%" orderField="nlssort(IN_THE_DIFFERENCE,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection }">
					<spring:message code="liang.hr.viewPersonalInfo.title.WHETHER_OR_NOT_TO_TRY"/><!-- 试用与否 -->
				</th>
				<th width="6%" orderField="nlssort(JOIN_COMPANY_DATE,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
					<spring:message code="pa.insurance.title.entryCpmpanyDate"/><!--入司日期-->
				</th>
				<th width="7%" orderField="nlssort(DATE_LEFT,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
					<spring:message code="pa.insurance.title.resignDate"/><!--离职日期-->
				</th>
				<th width="7%" orderField="nlssort(SETTLEMENT_DATE,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
					<spring:message code="pa.wagebase.title.salaryCaculateDate"/><!--工资计算日期-->
				</th>
				<th width="10%" orderField="CALC_FLAG" class="${orderDirection}"><!--计算标识-->
					<spring:message code="pa.wagebase.title.caculateFlag"/>(点击更改)
				</th>
				<th width="7%" orderField="nlssort(REMARK,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
					<spring:message code="hr.viewPromote.title.REMARK"/><!-- 备注  -->
				</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${itemList}" var="item" varStatus="i">
				<tr target="sid" rel="${item.PERSON_ID}&PA_MONTH=${item.PA_MONTH }&GIVE_DATE=${item.GIVE_DATE }&pageNum=${pageNum}" id="${item.EMPID}" name="${item.EMPID}">
					<td width="5%"  class="td_center">${item.PA_MONTH }</td>
					<td width="6%"  class="td_center">${item.GIVE_DATE }</td>
					<td width="6%" class="td_center">${item.EMPID}</td>
					<td width="6%" class="td_center">${item.CHINESE_NAME}</td>
					<td width="6%" class="td_center">${item.DEPTNAME}</td>
					<%--<td width="6%"  class="td_center">${item.DEPT_DISTINGUISH_NAME}</td>
					--%><td width="4%"  class="td_center">${item.POST_GRADE_NAME}</td>
					<td width="6%"  class="td_center">${item.EMP_STATUS}</td>
					<td width="6%"  class="td_center">${item.IN_THE_DIFFERENCE }</td>
					<td width="7%"  class="td_center">${item.JOIN_COMPANY_DATE}</td>
					<td width="7%"  class="td_center">${item.DATE_LEFT}</td>
					<td width="7%"  class="td_center">${item.SETTLEMENT_DATE}</td>
					<td width="10%"  class="td_center">
					<c:if test="${item.CALC_FLAG ne null && item.CALC_FLAG ne '' }">
						<img id="${item.EMPID}_gif" src="/resources/images/${item.CALC_FLAG}.gif" onclick="f_ObjectCalcFlagUpdate(${i.index},'${item.CALC_FLAG}','${item.PERSON_ID}','${item.EMPID}')"
						style="cursor:pointer;"/> 
					</c:if>
					</td>
					<td width="7%" class="td_center">${item.REMARK }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table> 
	<c:set value="/pa/bonus/viewBonusObject" var="pageUrl" />
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>
