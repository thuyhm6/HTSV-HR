<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script language="JavaScript" type="text/JavaScript">
function exportInfo(a){
  	var $this = $(a);
  	var title = $this.attr("title");
  	var $from = $("#viewSalesAchievement");
  	alertMsg.confirm(title, {okCall: function(){ doExport($from);}});
}
function doExport(from){
  	//var $from =$(from);
  	//var url ="${base}/promoter/viewSalesAchievementListExcel";
  	//window.location =url+ (url.indexOf('?') == -1 ? "?" : "&") + $from.serialize();
	var sform = document.getElementById("viewSalesAchievement");
	var eForm = document.getElementById("excelForm_cx1100");
	
	document.getElementById("cx1100Link").innerHTML = "EXCEL密码设置";
	eForm.PAY_AREA_CD.value		= sform.seach_PAY_AREA_2.value;
	eForm.BRANCH_CD.value		= sform.seach_BRANCH_2.value;
	eForm.PROD_TP.value		    = sform.seach_PROD_TP.value;
	eForm.year.value		    = sform.year.value;
	eForm.month.value		    = sform.month.value;
	eForm.ACC_YN.value		    = sform.seach_ACC_YN.value;
	
	$("#excelDialog_cx1100").attr('href', "/sys/encryptExcel"
					+"?exportFunName=/promoter/expSalesAchievementListExcel"
					+"&navTabId=cx1100"
					+"&formId=excelForm_cx1100");
	$("#excelDialog_cx1100").attr('width', "300");
	$("#excelDialog_cx1100").attr('height', "150");
	$("#excelDialog_cx1100").click();
}
function reLoadSalesAchievement(form1)
{
  	var params   = $("#viewSalesAchievement").serialize();
  	var PAY_AREA_CD = document.viewSalesAchievement.seach_PAY_AREA_2.value;
    if(PAY_AREA_CD == null||PAY_AREA_CD ==""){
	    alert("请选择大区！");
	    return false;
	}

    alertMsg.confirm("确认执行数据提取?",
	{
    	okCall : function() {
    		$.ajax( {
    			type : 'post',
    			cache : false,
    			url : "/promoter/reLoadSalesAchievement?" + params,
    					success : function(data) {
							if (data.statusCode==200){
								alert("数据提取成功！");
								//页面重载
								navTabSearch($("#viewSalesAchievement"));
							}else{
								alert(data.message);
							}
						}
			});
		}
	});
}
function calSalesAchievement(form2)
{
  	var params   = $("#viewSalesAchievement").serialize();
  	var PAY_AREA_CD = document.viewSalesAchievement.seach_PAY_AREA_2.value;
    if(PAY_AREA_CD == null||PAY_AREA_CD ==""){
	    alert("请选择大区！");
	    return false;
	}
    
    alertMsg.confirm("确认执行产品别提成?",
	{
    	okCall : function() {
    		$.ajax( {
    			type : 'post',
    			cache : false,
    			url : "/promoter/calSalesAchievement?" + params,
    					success : function(data) {
							if (data.statusCode==200){
								alert("产品别提成成功！");
								//页面重载
								navTabSearch($("#viewSalesAchievement"));
							}else{
								alert(data.message);
							}
						}
			});
		}
	});
}
$(document).ready(function() {
	var payAreaCd=$('#seach_PAY_AREA_2').val();
	var branch0=$('#hBRANCH2').val();
	chgPayArea2(payAreaCd,branch0);
	
	$('#seach_PAY_AREA_2').live('change',function(){
		var st=$('#seach_PAY_AREA_2').val();
		document.getElementById('hBRANCH2').value = "";
		branch0="";
		chgPayArea2(st,"");
	});
	
});

function chgPayArea2(payAreaCd, branch){
	$.ajax({
		cache: false,
		url : '${base}/promoter/getListBySelect?type=BRANCH&parentNo='+payAreaCd+'&selected='+branch+'&name=seach_BRANCH_2',
		type : "get",
		dataType : "html",
		success : function(data) {
			$("#seach_BRANCH_2").html(data);
		}
	});
}
</script>

	<form id="viewSalesAchievement" name="viewSalesAchievement" onsubmit="return navTabSearch(this);" action="/promoter/viewSalesAchievementList" method="post" rel="pagerForm">
<div class="pageHeader">
		<input name="seach_ACC_YN" id="seach_ACC_YN" type="hidden" value="${ACC_YN}" />
		<input name="hBRANCH2" id="hBRANCH2" type="hidden" value="${BRANCH_2}" />
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td><!-- 大区： -->
						大区
					</td>
					<td>
						<ait:SelectState id="seach_PAY_AREA_2" name="seach_PAY_AREA_2" type="PAYAREA" parentNo="" selected="${PAY_AREA_2}" limit="all"/>
					</td>
					<td>
						支社
					</td>
					<td>
						<span id="seach_BRANCH_2" name="seach_BRANCH_2"></select></span>
					</td>
					<td><!-- 产品类型： -->
						产品类型
					</td>
					<td>
						<ait:ComboSyCodeDescByCpnyID id="seach_PROD_TP" name="seach_PROD_TP" parentNo="211424" selected="${PROD_TP}" cnpyID="${defaultCpny}" limit="all"/>
					</td>
					<td><!-- 月份： -->
						月份
					</td>
					<td>
						<ait:date yearName="year" yearSelected="${year}" monthName="month" monthSelected="${month}"/>
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">
									<spring:message code="public.title.search"/><!-- 检索 -->
								</button>
							</div>
						</div>
					</li>
				</ul>
			</div>
		</div>
</div>

<div class="pageContent">
<div class="formBar">
				<ul>
					<li>
						<div class="button">
							<div class="buttonContent">
								<button type="button" onclick="javascript:exportInfo(this)" title="Excel下载">
									Excel下载
								</button>
							</div>
						</div>						
					</li>
					<li>
						<div class="button">
							<div class="buttonContent">
								<button type="button" onclick="javascript:reLoadSalesAchievement(this)" title="数据提取">
									数据提取
								</button>
							</div>
						</div>						
					</li>
					<li>
						<div class="button">
							<div class="buttonContent">
								<button type="button" onclick="javascript:calSalesAchievement(this)" title="产品别提成">
									产品别提成
								</button>
							</div>
						</div>						
					</li>
				</ul>
</div>
	</form>
<table class="table" width="100%" layoutH="256">
	<thead>
		<tr>
			<th>大区</th>
			<th>支社</th>
			<th>社号</th>
			<th>姓名</th>
			<th>产品类型</th>
			<th>产品ID</th>
			<th>客户ID</th>
			<th>客户类型</th>
			<th>销售数量</th>
			<th>变动提成</th>
			<th>固定提成</th>
			<th>销售金额</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${itemList}" var="item" varStatus="i">
			<tr>
				<td>${item.PAY_AREA_NM}</td>
				<td>${item.BRANCH}</td>
				<td>${item.EMPNO}</td>
				<td>${item.EMP_NM}</td>
				<td>${item.PROD_TP_NM}</td>
				<td>${item.PROD_ID}</td>
				<td>${item.CUST_ID}</td>
				<td>${item.CUST_TP}</td>
				<td>${item.SALS_QTY}</td>
				<td>${item.VARB_INCTV_AMT}</td>
				<td>${item.FXD_INCTV_AMT}</td>
				<td>${item.SALS_AMT}</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<c:set value="/promoter/viewSalesAchievementList" var="pageUrl"/>
<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
	
	<a id="excelDialog_cx1100" href="#" target="dialog" mask="true"><span
		id="cx1100Link" style="display: none"></span></a>
	<form id="excelForm_cx1100" name="excelForm_cx1100" method="post">
	    <input type="hidden" id="password" 		name="password" 	value="" />
	    <input type="hidden" id="PAY_AREA_CD" 	name="PAY_AREA_CD" 	value="" />
	    <input type="hidden" id="BRANCH_CD" 	name="BRANCH_CD" 	value="" />
	    <input type="hidden" id="PROD_TP" 		name="PROD_TP" 		value="" />
	    <input type="hidden" id="year" 		    name="year" 		value="" />
	    <input type="hidden" id="month"         name="month"        value="" />
	    <input type="hidden" id="ACC_YN"        name="ACC_YN"       value="" />
	</form>
	
</div>
