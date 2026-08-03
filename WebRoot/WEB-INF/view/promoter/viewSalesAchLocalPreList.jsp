<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script language="JavaScript" type="text/JavaScript">
    function calcPre(form1)
    {
        var params   = $("#viewcalSalesAchLocalPre").serialize();
      	var PAY_AREA_CD = document.viewcalSalesAchLocalPre.seach_PAY_AREA_5.value;
        if(PAY_AREA_CD == null||PAY_AREA_CD ==""){
    	    alert("请选择大区！");
    	    return false;
    	}
	    alertMsg.confirm("确认预提计算?",
		{
			okCall : function() {
					$.ajax( {
					type : 'post',
					cache : false,
					url : "/promoter/calSalesAchLocalPre?" + params,
					success : function(data) {
						if (data.statusCode==200){
							alert("预提计算成功！");
							//页面重载
							navTabSearch($("#viewcalSalesAchLocalPre"));
						}else{
							alert(data.message);
						}
					}
				});
			}
		});
	}
    $(document).ready(function() {
    	var payAreaCd=$('#seach_PAY_AREA_5').val();
    	var branch0=$('#hBRANCH5').val();
    	chgPayArea5(payAreaCd,branch0);
    	
    	$('#seach_PAY_AREA_5').live('change',function(){
    		var st=$('#seach_PAY_AREA_5').val();
    		document.getElementById('hBRANCH5').value = "";
    		branch0="";
    		chgPayArea5(st,"");
    	});
    	
    });

    function chgPayArea5(payAreaCd, branch){
    	$.ajax({
    		cache: false,
    		url : '${base}/promoter/getListBySelect?type=BRANCH&parentNo='+payAreaCd+'&selected='+branch+'&name=seach_BRANCH_5',
    		type : "get",
    		dataType : "html",
    		success : function(data) {
    			$("#seach_BRANCH_5").html(data);
    		}
    	});
    }
</script>

<div class="pageHeader">
	<form id="viewcalSalesAchLocalPre" name="viewcalSalesAchLocalPre" onsubmit="return navTabSearch(this);" action="/promoter/viewSalesAchLocalPreList" method="post" rel="pagerForm">
		<input name="seach_ACC_YN" id="seach_ACC_YN" type="hidden" value="${ACC_YN}" />
		<input name="hBRANCH5" id="hBRANCH5" type="hidden" value="${BRANCH_5}" />
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td><!-- 大区： -->
						大区
					</td>
					<td>
						<ait:deptTreeMulti id="seach_PAY_AREA_5" name="seach_PAY_AREA_NM" limit="pa" level="2" 
						selected="${PAY_AREA_5}" selectedNm="${PAY_AREA_NM}" />
					</td>
					<td><!-- 月份： -->
						月份
					</td>
					<td>
						<ait:date yearName="year" yearSelected="${year}" monthName="month" monthSelected="${month}"/>
					</td>
				</tr>
				<tr>
					<td><!-- 产品类型： -->
						产品类型
					</td>
					<td>
						<ait:ComboSyCodeDescByCpnyID id="seach_PROD_TP" name="seach_PROD_TP" parentNo="211424" selected="${PROD_TP}" cnpyID="${defaultCpny}" limit="all"/>
					</td>
					<td><!-- 组织名称： -->
						组织名称
					</td>
					<td>
						<ait:deptList name="seach_DEPTNO" id="seachDept_se0710"/>
						<ait:deptTreeIcon name="seach_DEPTNO" limit="pa" id="seachDept_se0710" selected="${DEPTNO}"/>
					</td>
					<td><!-- 社号/姓名： -->
						社号/姓名
					</td>
					<td>
						<input type="text" name="seach_EMP" value="${EMP}" />
					</td>
				</tr>
				<tr>
					<td><!-- 查询条件： -->
						查询条件
					</td>
					<td>
						<select name="search_Type">
						    <option value="ALL" <c:if test="${search_Type eq 'ALL'}">selected</c:if>>汇总</option>
						    <option value="DTL" <c:if test="${search_Type eq 'DTL'}">selected</c:if>>明细</option>
						</select>
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
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="button" onclick="calcPre(this)" title="预提计算">
									预提计算
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
<table class="table" width="120%" layoutH="258">
	<thead>
		<tr>
			<th width="6%" rowspan="2">大区</th>
			<th width="6%" rowspan="2">支社</th>
			<th width="6%" rowspan="2">社号</th>
			<th width="6%" rowspan="2">姓名</th>
			<th width="6%" rowspan="2">销售量 </th>
			<th width="6%" rowspan="2">销售额 </th>
			<th width="12%" colspan="2">当月目标</th>
			<th width="12%" colspan="2">最小目标</th>
			<th width="5%" rowspan="2">固定提成</th>
			<th width="5%" rowspan="2">变动提成 </th>
			<th width="5%" rowspan="2">达成率</th>
			<th width="5%" rowspan="2">达成奖惩</th>
			<th width="5%" rowspan="2">其他+</th>
			<th width="5%" rowspan="2">产品类型</th>
			<th width="5%" rowspan="1">提成</th>
			<th width="5%" rowspan="1">实贩卖</th>
		</tr>
		<tr>
			<th width="6%">QTY</th>
			<th width="6%">AMT</th>
			<th width="6%">QTY</th>
			<th width="6%">AMT</th>
			<th width="5%">比率</th>
			<th width="5%">比率</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${fixedPay}" var="item" varStatus="i">
			<tr>
				<td width="6%" class='td_center'>${item.PAY_AREA_NM}</td>
				<td width="6%" class='td_center'>${item.BRANCH}</td>
				<td width="6%" class='td_center'>${item.EMPNO}</td>
				<td width="6%" class='td_center'>${item.EMPNM}</td>
				<td width="6%" class='td_right'>${item.TOT_SALS_QTY}</td>
				<td width="6%" class='td_right'>${item.TOT_SALS_AMT}</td>
				<td width="6%" class='td_right'>${item.TOT_MON_GOAL_AMT}</td>
				<td width="6%" class='td_right'>${item.TOT_MON_GOAL_AMT_APP}</td>
				<td width="6%" class='td_right'>${item.TOT_MIN_GOAL_AMT}</td>
				<td width="6%" class='td_right'>${item.TOT_MIN_GOAL_AMT_APP}</td>
				<td width="5%" class='td_right'>${item.FXD_INCTV_AMT}</td>
				<td width="5%" class='td_right'>${item.VARB_INCTV_AMT}</td>
				<td width="5%" class='td_right'>${item.TARG_RAT}</td>
				<td width="5%" class='td_right'>${item.TARG_ALOWN_AMT}</td>
				<td width="5%" class='td_right'>${item.ALOWN_AMT}</td>
				<td width="5%" class='td_center'>${item.PROD_TP}</td>
				<td width="5%" class='td_right'>${item.VARB_INCTV_RATIO}</td>
				<td width="5%" class='td_right'>${item.SALE_AMT_RATIO}</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<c:set value="/promoter/viewSalesAchLocalPreList" var="pageUrl"/>
<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>
