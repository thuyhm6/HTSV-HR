<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
function openUpdateTempSalaryWin(){
	var checked=false;
	var eventId="";
	var ids= document.getElementsByName("isChecked");
	for(var i=0;i<ids.length;i++){
		if(ids[i].checked){
			checked=true;
			eventId = ids[i].value;
			break;
		}
	}
	if(!checked){
		//请选择信息再进行保存操作
		alertMsg.error('<spring:message code="hr.alert.message.viewPersonalInfo.checkBoxForChecked.xiugai"/>');
		return false;
	}else{
		$.pdialog.open("/pa/tempsale/viewUpdatePaTempSales?EVENT_ID=" + eventId, "updateTempSalaryWin", "临促工资修改", {width:800,height:500,mask:true});
	}
}
</script>
<div class="pageHeader">
<form onsubmit="return navTabSearch(this);"
	action="/pa/tempsale/viewTempSaleAccrualAffirmList" rel="pagerForm" method="post">
<div class="searchBar">
<table class="searchContent">
	<tr>
		<td>审批状态</td>
		<td><select id="seach_AFFIRM_FLAG" name="seach_AFFIRM_FLAG">
			<option value="">全部</option>
			<option value="-1" <c:if test="${AFFIRM_FLAG eq '-1'}">selected</c:if>>提交</option>
			<option value="0" <c:if test="${AFFIRM_FLAG eq '0'}">selected</c:if>>审批中</option>
			<option value="1" <c:if test="${AFFIRM_FLAG eq '1'}">selected</c:if>>通过</option>
			<option value="2" <c:if test="${AFFIRM_FLAG eq '2'}">selected</c:if>>否决</option>
		</select></td>
	</tr>
</table>
<div class="subBar">
<ul>
	<li>
	<div class="buttonActive">
	<div class="buttonContent">
	<button type="submit"><spring:message
		code="public.title.search" /><!-- 检索 --></button>
	</div>
	</div>
	</li>
</ul>
</div>
</div>
</form>
</div>

<div class="pageContent">
<table class="table" width="100%" layoutH="172" nowrapTD="false">
	<thead>
		<tr>
			<th>NO</th>
			<th>大区</th>
			<th>支社名称</th>
			<th align="center">支付月份</th>
			<th align="center">对应共同社编</th>
			<th align="right">总金额</th>
			<th align="center">审批状态</th>
			<th align="center">Type</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${paTempSalesList}" var="item" varStatus="i">
			<tr>
				<td style="text-align: center">${i.count }</td>
				<td style="text-align: center">${item.PAY_AREA_NAME }</td>
				<td style="text-align: center">${item.ORG_NAME_LOCAL }</td>
				<td style="text-align: center">${item.PAY_DATE }</td>
				<td style="text-align: center">${item.COMMON_EMPID }</td>
				<td style="text-align: center">${item.TOTAL_SALARY }</td>
				<td style="text-align: center">
					<c:if test="${item.AFFIRM_FLAG eq '-1'}">提交</c:if>
					<c:if test="${item.AFFIRM_FLAG eq '0'}">审批中</c:if>
					<c:if test="${item.AFFIRM_FLAG eq '1'}">通过</c:if>
					<c:if test="${item.AFFIRM_FLAG eq '2'}">否决</c:if>
				</td>
				<td style="text-align: center">
					<c:if test="${item.AFFIRM_FLAG_PERSON eq '1'}">
						<a href="/pa/tempsale/viewTempSaleAccrualAffirm?EVENT_ID=${item.EVENT_ID }&affirmOrCheck=1&accrualFlag=Y"
							target="navTab" 
								rel="pa0901_affirm">审批</a>
					</c:if>
					<c:if test="${item.AFFIRM_FLAG_PERSON ne '1'}">
						<a onclick="navTabNum('/pa/tempsale/viewTempSaleAccrualAffirmInfoList?pageNum=1&navTabId=pa0701_EMPINFO&EVENT_ID=${item.EVENT_ID }','pa0701_EMPINFO','审批查看');">审批查看</a>
					</c:if>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<c:set value="/pa/tempsale/viewTempSaleAccrualAffirmList" var="pageUrl" /> 
<%@ include file="/WEB-INF/view/inc/initPagination2.jsp"%>
</div>