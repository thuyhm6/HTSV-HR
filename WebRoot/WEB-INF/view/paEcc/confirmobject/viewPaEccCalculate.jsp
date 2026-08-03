<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>经济补偿金计算</title>
<style>
	.prograss div{
		float:left;
		margin:10px;
		width:150px;
		height:25px;
		padding:5px;
		line-height:23px;
		background:url("../../resources/images/button/m_bannerb.gif") no-repeat right center;
		border:3px solid #999;
		cursor:pointer;
	}
	.prograss .lose{
		background:url("../../resources/images/button/m_bannera.gif") no-repeat right center;
	}
	.bttr a{
		color:#FFF;
	}
	.bttr li{
		float:right;
		list-style:none;
		margin: auto 2px;
	}
	.gridScroller td div{
		text-align: center;
		padding:0 5px;
		line-height: 10px;
		width: auto;		
	}
		
</style>
<script type="text/javascript">
function exportPaMonthModuleExcel(){

	document.getElementById("exportPaMonthModuleExcel").href="/pa/excelExport/exportPaEccModule";
	document.getElementById("exportPaMonthModuleExcel").click();

}
function exportPaMonthModuleExport(){
	var batches = document.vieweccmonth.batches.value;
	var year = $("select[name='year']").val();
	var month = $("select[name='month']").val();
	if(batches==""){
		alert('<spring:message code="hr.viewCondSql.title.QINGXUANZE"/><spring:message code="display.pa.ecc.pici"/>');//请选择批次！
		return;
	}
	document.getElementById("exp_t").href="/pa/excelExport/exportPaEccInfo?year="+year+"&month="+month+"&batches="+batches;
}
function importPaEccInfo(){
	var PA_MONTH = document.vieweccmonth.year.value + document.vieweccmonth.month.value;
	location.href="/pa/excelImport/importArItemData?importFunName=importPaEccInfo";
}
function delEccHistory(){
	var dels = [];
	$(":input[name='chooseOne']:checked").each(function(i,item){
		dels.push($(this).val());
	});
	if(dels.length == 0){
		alert('<spring:message code="display.alert.selectone"/>'); /// "请至少选择一条数据!"
		return;
	}else{
		if(confirm('<spring:message code="display.pa.confirm"/>')){  // '确定此操作吗?'
			dels = dels.join(',');
			var paMonth = $("[name='year']").val()+$("[name='month']").val();
			var batches = $("[name='batches']").val();
			$.ajax({
				type:'POST',
				url:"/paEcc/confirmobject/delPaEccHisInfo",
				data: { 'dels': dels,'PA_MONTH':paMonth,'batches':batches},
				dataType:"json",
				cache: false,
				success: function(data){
					alert(data.returnMsg);
					navTabNum('/paEcc/confirmobject/viewPaEccCalculate?pageNum=1');
				}
			});
			//$("#delEccHistory").href="/paEcc/confirmobject/delPaEccHisInfo?dels="+dels;
		}
	}
}
function calculate(){
	var year = $("select[name='year']").val();
	var month = $("select[name='month']").val();
	//var $row = $(":input[name='chooseOne']:checked");
	var rowObj;
	var updates = "[";
	$(":input[name='chooseOne']:checked").each(function(i,item){
		rowObj = this.parentNode.parentNode.parentNode;
		updates += "{'eccNo':'" + $(rowObj).find("input[name='eccNO']").val() + "',";
		updates += "'leftType':'" + $(rowObj).find("select[name='leftType']").val() + "',";
		updates += "'eccAmt':'" + $(rowObj).find("input[name='eccAmt']").val() + "',";
		//updates += "'oldEccAmt':'" + $(rowObj).find("input[name='oldEccAmt']").val() + "',";
		updates += "'eccTax':'" + $(rowObj).find("input[name='eccTax']").val() + "',";
		//updates += "'oldEccTax':'" + $(rowObj).find("input[name='oldEccTax']").val() + "',";
		updates += "'noticeAmt':'" + $(rowObj).find("input[name='noticeAmt']").val() + "',";
		//updates += "'oldNoticeAmt':'" + $(rowObj).find("input[name='oldNoticeAmt']").val() + "',";
		updates += "},";
	});
	updates = updates.substr(0,updates.length-1);
	updates += "]";
	if(updates.length == 1){
		alert('<spring:message code="display.alert.selectone"/>'); //请选择需要重新计算的数据!
		return;
	}else{
		if(confirm('<spring:message code="display.pa.confirm"/>')){
			$.ajax({
				type:'POST',
				url:"/paEcc/confirmobject/calculatePaEcc",
				data: {'updates': updates,'PA_MONTH':year+month},
				dataType:"json",
				cache: false,
				success: function(data){
					alert(data.returnMsg);
					navTabNum('/paEcc/confirmobject/viewPaEccCalculate?pageNum=1&year='+year+'&month='+month);
				}
			});
		}
	}
}
function settlement(){
	var year = $("select[name='year']").val();
	var month = $("select[name='month']").val();
	var batches = $("select[name='batches']").val();
	var payDate = $("input[name='payDate']").val();
	if(batches==''){
		alert('<spring:message code="hr.viewCondSql.title.QINGXUANZE"/><spring:message code="display.pa.ecc.pici"/>');
		return;
	}
	if(payDate==null||payDate==''){
		alert('<spring:message code="display.pa.ecc.paydatevalid"/>');
		return;
	}
	if(confirm('<spring:message code="display.pa.confirm"/>')){   // 确定此操作吗?
		$.ajax({
			type:'POST',
			url:"/paEcc/confirmobject/settlementPaEcc",
			data: {'payDate':payDate,'PA_MONTH':year+month,'batches':batches},
			dataType:"json",
			cache: false,
			success: function(data){
				alert(data.returnMsg);
				navTabNum('/paEcc/confirmobject/viewPaEccCalculate?year='+year+'&month='+month);
			}
		});
	}
}
function cancelSettlement(){
	var year = $("select[name='year']").val();
	var month = $("select[name='month']").val();
	var batches = $("select[name='batches']").val();
	if(batches==''){
		alert('<spring:message code="hr.viewCondSql.title.QINGXUANZE"/><spring:message code="display.pa.ecc.pici"/>');
		return;
	}
	if(confirm('<spring:message code="display.pa.confirm"/>')){   // 确定此操作吗?
		$.ajax({
			type:'POST',
			url:"/paEcc/confirmobject/cancelsettlementPaEcc",
			data: {'PA_MONTH':year+month,'batches':batches},
			dataType:"json",
			cache: false,
			success: function(data){
				alert(data.returnMsg);
				navTabNum('/paEcc/confirmobject/viewPaEccCalculate?year='+year+'&month='+month);
			}
		});
	}
}
function search(){
	var year = $("select[name='year']").val();
	var month = $("select[name='month']").val();
	var batches = $("select[name='batches']").val();
	navTabNum('/paEcc/confirmobject/viewPaEccCalculate?year='+year+'&month='+month+'&batches='+batches);
}
</script>
</head>

<body>
<div class="pageHeader">
<form action="/paEcc/confirmobject/viewPaEccCalculate" id="vieweccmonth" name="vieweccmonth" 
		onsubmit="return navTabSearch(this);" method="post">
<input type="hidden" name="pageNum" value="${pageNum}" />
<input type="hidden" name="numPerPage" value="${numPerPage}" />
<table class="searchContent">
  <tr>
    <td colspan="15">
		<div class="prograss">
			<c:choose>
				<c:when test="${palFlag=='0'}">
					<div onclick="calculate()"><spring:message code="display.pa.calculate"/></div><!-- 计算 -->
					<div onclick="settlement()"><spring:message code="display.pa.settlement"/></div><!-- 结算 -->
				</c:when>
				<c:when test="${palFlag=='1'}">
					<div class="lose"><spring:message code="display.pa.calculate"/></div><!-- 计算 -->
					<div onclick="cancelSettlement()"><spring:message code="display.pa.cancelsettle"/></div><!-- 取消结算 -->
				</c:when>
			</c:choose>
			<div>生成禀议</div>
			<div>明细开放</div>
		</div>
    </td>
  </tr>
	<!-- 
    	查询条件
	 -->
  <tr>
    <td><spring:message code="display.pa.ecc.date"/>&nbsp;</td><!-- 年月 -->
    <td><ait:date yearName="year" monthName="month"
			yearSelected="${year }" monthSelected="${month }" yearPlus="10" /></td>
    <td>&nbsp;<spring:message code="display.pa.ecc.pici"/>&nbsp;</td><!-- 批次 -->
    <td><select name="batches" onchange="search();" id="batches">
										<option value=""><!--请选择--><spring:message code="hr.viewCondSql.title.QINGXUANZE"/></option>
										<c:forEach items="${batcheList }" var="batch">
											<!-- 
												<c:if test="${empty batches and defaultSelect eq batch.BATCHVALUE }">selected</c:if>
											 -->
											<option value="${batch.BATCHVALUE }" 
												<c:if test="${not empty batches and batches eq batch.BATCHVALUE }">selected</c:if>
											>${batch.BATCHVALUE }</option>
										</c:forEach>
	  </select></td>
    <td><spring:message code="display.pa.ecc.paydate"/></td><!-- 支付日期 -->
    <td><input type="text" id="payDate" name="payDate" value="${payDate}" class="date required"
							yearstart="-20" yearend="20" readonly="true" /></td>
    <td><spring:message code="public.title.empId"/><!--工号--></td>
    <td>
    <input type="text" id="empId" name="dwz.person.empId" 
							value="${empId}" lookupGroup="person" size="10" readonly/>
	<!-- 
    <input type="text" value="${empID }" name="empID" title='请输入工号查找'/>
	 -->
    <a class="btnLook" href="/paEcc/benchmark/searchEccBenchEmp?pageNum=1" 
						lookupGroup="person">查找<%--查找带回--%></a>&nbsp;&nbsp;&nbsp;
    </td>
    <td colspan="6" class="bttr">
    	<div class="subBar">
			<ul>
                <li><div class="buttonActive"><div class="buttonContent">
                	<button type="submit" id="searchEccButton"><!-- 查询 -->
                	<spring:message code="button.search"/></button></div></div></li>
				<li>
					<a id="exportPaMonthModuleExcel" class="buttonActive"
					onclick="exportPaMonthModuleExcel();"
					><span><!-- 下载导入模板 --><spring:message code="ar.addempshift.title.downloadmodule"/></span>
				</a>
				</li>
				<li><!-- 导入 -->				 
					<a class="buttonActive"
						href="/pa/excelImport/importArItemData?importFunName=importPaEccInfo" target="dialog" 
							mask="true" width="400" height="200" ><span><!-- EXCEL导入 -->
							<spring:message code="ar.addempshift.title.excelimport"/></span>
					</a>
				</li>
				<li>
				 <a id="exp_t" class="buttonActive"
					onclick="exportPaMonthModuleExport();"
					><span><!-- EXCEL导出 --><spring:message code="ar.addempshift.title.excelexport"/></span>
				</a>
				</li>
				<li>
					<a id="delEccHistory" class="buttonActive" onclick="delEccHistory()">
					<!-- 删除 --><span><spring:message code="hr.viewCondSql.title.SHANCHU"/></span>
					</a>
				</li>
			</ul>
		</div>
    </td>
  </tr>
</table>
</form>
</div>
<div class="pageContent">
<table class="table" width="1900" layoutH="160" id="eccTable">
 <tr>
    <td><input type="checkbox" class="checkboxCtrl" group="chooseOne"/></td>
	<td><spring:message code="display.pa.ecc.date"/><!-- 年月 --></td>
	<td><!--工号--> 
		<spring:message code="hr.viewPersonalInfo.title.EMPID"/>
	</td>
	<td><!--姓名--> 
		<spring:message code="hr.viewPersonalInfo.title.LOCAL_NAME"/>
	</td>
	<td><!-- 部门 -->
		<spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/>
	</td>
	<td>
		<spring:message code="hr.viewPersonalInfo.title.GRADE_LEVEL_NAME" />
		<!--职等-->
	</td>
	<td><!--职责-->
		<spring:message code="hr.viewPersonalInfo.title.DUTY_NAME"/>
	</td>
	<td><!--职级名称(职务)  -->
		<spring:message code="hr.viewPersonalInfo.title.POST_NAME"/>
	</td>
	<td><!--离职日  --><spring:message code="pa.insurance.title.resignDate"/></td>
	<td><!-- 离职类型 --><spring:message code="hr.viewPromote.title.RESIGN_TYPE_NAME"/></td>
	<td><!--经济补偿金结算日--><spring:message code="display.pa.ecc.settledate"/></td>
	<td><!--平均工资--><spring:message code="display.pa.averagewage"/></td>
	<td><!--补偿月数--><spring:message code="display.pa.ecc.compensatemonth"/></td>
	<td><!--法定平均工资--><spring:message code="display.pa.ecc.legalavgwage"/></td>
	<td><!--法定补偿月数--><spring:message code="display.pa.ecc.legalcompenmonth"/></td>
	<td><!--经济补偿金 --><spring:message code="display.pa.ecc.compenpayment"/></td>
	<td><!--待通知金--><spring:message code="display.pa.ecc.noticeamout"/></td>
	<td><!--课税--><spring:message code="display.pa.tax"/></td>
	<td><!--备注--><spring:message code="liang.hr.viewPersonalInfo.title.REMARKS" /></td>
  </tr>
  <c:forEach items="${eccEmpInfos}" var="item">
  	<tr>
  		<td><input type="checkbox" name="chooseOne" value="${item.EMPID}"/></td>
  		<td>${item.PA_MONTH }
  			<input type="hidden" name="eccNO" value="${item.PA_ECC_NO }"/>
  		</td>
  		<td>${item.EMPID }</td>
  		<td>${item.LOCAL_NAME}</td>
  		<td>${item.DEPTNAME}</td>
  		<td>${item.GRADE_LEVEL_NAME }</td>
  		<td>${item.DUTY_NAME }</td>
  		<td>${item.POST_NAME }</td>
  		<td>${item.LEFT_DATE }</td>
  		<td>
  			<select name="leftType">
  				<option value="leftReasonType001">不续签合同</option>
  				<option value="leftReasonType002">劝告离职</option>
  				<option value="leftReasonType003">协议离职</option>
  			</select>
  		</td>
  		<td>${item.ECC_END_DATE }</td>
  		<td>${item.AVG_SALARY }</td>
  		<td>${item.COMPENSATION_MONTH }</td>
  		<td>${item.AVG_SALARY_FD }</td>
  		<td>${item.COMPENSATION_MONTH_FD }</td>
  		<td>${item.ECC_AMT }
  		<!-- 
  		<input name="eccAmt" value="${item.ECC_AMT }" type="text" size="5"/>
  		 -->
  		</td>
  		<td><input type="text" name="noticeAmt" value="${item.NOTICE_AMT }" size="3"></td>
  		<td><input type="text" name="eccTax" value="${item.TAX }" size="5"/></td>
  		<td>${item.REMARK }</td>
  	</tr>
  </c:forEach>
</table>
<form id="pagerForm" method="post" action="/paEcc/confirmobject/viewPaEccCalculate">
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
<div>
	&nbsp;&nbsp;&nbsp;&nbsp;
	平均工资上限:8000&nbsp;&nbsp;
	平均工资下限:2000&nbsp;&nbsp;
	经济补偿金免税额:172404
</div>
</div>
</body>
</html>
