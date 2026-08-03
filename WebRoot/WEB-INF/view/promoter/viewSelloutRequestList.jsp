<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
function addSelloutReq(){
    var params      = $("#viewSelloutRequestList").serialize();
    var PAY_AREA_CD = document.viewSelloutRequestList.seach_PAY_AREA_cx1600.value;
    var BRANCH      = document.viewSelloutRequestList.seach_BRANCH_cx1600.value;
    
    if(BRANCH == null||BRANCH ==""){
        alert("请选择支社！");
    }else
    {
    	$("#importExcelDialog_cx1600").attr('href','/promoter/viewSelloutRequest?' + params);
    	$("#importExcelDialog_cx1600").attr('width',"800");
    	$("#importExcelDialog_cx1600").attr('height',"600");
    	$("#importExcelDialog_cx1600").click();
    }
}

function submitSelloutReq(flag){//1:提交 2：删除
	$("#FLAG_cx1600").val(flag);
	var msg = "";
	if(flag == 1){
		msg = "确定要提交吗?";
	}else{
		msg = "确定要删除吗?";
	}
	alertMsg.confirm(msg, {
        okCall: function(){
		var params   = $("#submitSelloutReq").serialize();
		$.ajax( {
			type : 'post',
			cache : false,
			url : "/promoter/submitSelloutReq?SUBMITFLAG="+flag+"&" + params,
					success : function(data) {
						if (data.statusCode==200){
							if(flag == 1){
								alert("数据保存成功!");
							}else{
								alert("删除成功!");
							}
							//页面重载
							navTabSearch($("#viewSelloutRequestList"));
						}else{
							alert(data.message);
						}
					}
		});
        }
    });
}
function validateCallbackSelloutReq(form, callback) {
	var $form = $("#submitTempSalary_cx1600");
		if (!$form.valid()) {
			return false;
		}
		var checked=false;
		var ids= document.getElementsByName("cx1600Check");
		for(var i=0;i<ids.length;i++){
			if(ids[i].checked){
				checked=true;
			}
		}
		if(!checked){
			//请选择信息再进行保存操作
			if($("#FLAG_cx1600").val() == 1){
				alertMsg.error('<spring:message code="hr.alert.message.viewPersonalInfo.checkBoxForChecked.tijiao"/>');
			}else{
				alertMsg.error('<spring:message code="hr.alert.message.viewPersonalInfo.checkBoxForChecked.shanchu"/>');
			}
			return false;
		}
		$.ajax({
			type: form.method || 'POST',
			url:$form.attr("action"),
			data:$form.serializeArray(),
			dataType:"json",
			cache: false,
			success: callback || DWZ.ajaxDone,
			error: DWZ.ajaxError
		});
		return false;
}

function openUpdateTempSalaryWin_cx1600(){
	var checked=false;
	var eventId="";
	var ids= document.getElementsByName("cx1600Check");
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
$(document).ready(function() {
	var payArea_cx1600 = $('#seach_PAY_AREA_cx1600').val();
	var branch_cx1600  = $('#hBRANCH_cx1600').val();
	chgPayArea_cx1600(payArea_cx1600, branch_cx1600);
	
	$('#seach_PAY_AREA_cx1600').live('change',function(){
		var st = $('#seach_PAY_AREA_cx1600').val();
		document.getElementById('hBRANCH_cx1600').value = "";
		branch_cx1600 = "";
		chgPayArea_cx1600(st,"");
	});
});

function chgPayArea_cx1600(payAreaCd, branch){
	$.ajax({
		cache: false,
		url : '${base}/promoter/getListBySelect?type=BRANCH&parentNo='+payAreaCd+'&selected='+branch+'&name=seach_BRANCH_cx1600',
		type : "get",
		dataType : "html",
		success : function(data) {
			$("#seach_BRANCH_cx1600").html(data);
		}
	});
}
</script>

<div class="pageHeader">
<form id="viewSelloutRequestList" name="viewSelloutRequestList" onsubmit="return navTabSearch(this);" action="/promoter/viewSelloutRequestList" rel="pagerForm" method="post">
<input name="hBRANCH_cx1600" id="hBRANCH_cx1600" type="hidden" value="${BRANCH_cx1600}" />
<div class="searchBar">
<table class="searchContent">
	<tr>
		<td>大区</td>
		<td><ait:SelectState id="seach_PAY_AREA_cx1600" name="seach_PAY_AREA_cx1600" type="PAYAREA" parentNo="" selected="${PAY_AREA_cx1600}" limit="all"/></td>
		<td>支社</td>
		<td><span id="seach_BRANCH_cx1600" name="seach_BRANCH_cx1600"></select></span></td>
		<td><spring:message code="public.title.startDate" /><!-- 开始日期 -->
		</td>
		<td><input id="seach_START_DATE" type="text"
			name="seach_START_DATE" class="date required" readonly="true"
			value="${START_DATE}" /> <a class="inputDateButton"><spring:message
			code="public.title.choose" /><!-- 选择 --></a></td>
		<td><spring:message code="public.title.endDate" /><!-- 结束日期 -->
		</td>
		<td><input id="seach_END_DATE" type="text" name="seach_END_DATE"
			class="date required" readonly="true" value="${END_DATE}" /> <a
			class="inputDateButton"><spring:message
			code="public.title.choose" /><!-- 选择 --></a></td>
	</tr>
	<tr>
		<td>月份</td>
		<td>
			<ait:date yearName="year" yearSelected="${year}" monthName="month" monthSelected="${month}"/>
		</td>
		<td>审批状态</td>
		<td><select id="seach_AFFIRM_FLAG" name="seach_AFFIRM_FLAG">
			<option value="">全部</option>
			<option value="-2" <c:if test="${AFFIRM_FLAG eq '-2'}">selected</c:if>>暂存</option>
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
<a id="importExcelDialog_cx1600" href="#" target="dialog" mask="true"></a>
<form id="submitSelloutReq" method="post" action="/promoter/submitSelloutReq" class="pageForm required-validate" 
     	onsubmit="return validateCallbackSelloutReq(this, navTabAjaxDone)">
<div class="formBar">
	<ul class="toolBar">
		<li><a class="buttonActive" onclick="addSelloutReq()"><span>申请</span></a></li>
		<li><a class="buttonActive" onclick="submitSelloutReq(2)"><span>删除</span></a></li>
		<li><a class="buttonActive" onclick="submitSelloutReq(1)"><span>提交</span></a></li>
	</ul>
</div>
<table class="table" width="100%" layoutH="235" nowrapTD="false">
	<thead>
		<tr>
			<th><input type="checkbox" class="checkboxCtrl" group="cx1600Check" /></th>
			<th>申请类容</th>
			<th>申请人</th>
			<th>大区</th>
			<th>支社</th>
			<th>月份</th>
			<th>审批状态</th>
			<th>数据状态</th>
			<th>审批查看</th>
			<th>备注</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${itemList}" var="item" varStatus="i">
			<tr>
				<td class="td_center" >
				  <c:if test="${item.ACTIVITY ne '0'}">
					<c:if test="${item.SUBMIT_STATUS eq '0'}">
					   <input type="checkbox" name="cx1600Check" value="${item.REQ_ID }"/>
					</c:if>
				  </c:if>
				</td>
				<td style="text-align:left">
				  <c:if test="${item.ACTIVITY ne '0'}">
					<c:if test="${item.SUBMIT_STATUS eq '0'}">
						<a class="add" href="/promoter/viewSelloutRequestDtl?REQ_ID=${item.REQ_ID}&REQ_TITLE=${item.REQ_TITLE}" title="查询"
					         target="dialog"  rel="viewSelloutReqInfo" width="1000" height="700" mask="true"><font color="red">${item.REQ_TITLE }</font></a>
					</c:if>
					<c:if test="${item.SUBMIT_STATUS ne '0'}">
						${item.REQ_TITLE }
					</c:if>
				  </c:if>
				  <c:if test="${item.ACTIVITY eq '0'}">
				    <font color=grey>${item.REQ_TITLE }</font>
				  </c:if>
				</td>
				<td class="td_center">${item.EMPNAME }</td>
				<td class="td_center">${item.PAY_AREA_NM}</td>
				<td class="td_center">${item.BRANCH_NM}</td>
				<td class="td_center">${item.REQ_DATE}</td>
				<td class="td_center">
					<c:if test="${item.SUBMIT_STATUS eq '0'}">暂存</c:if>
					<c:if test="${item.SUBMIT_STATUS eq '1'}">
						<c:if test="${item.AFFIRM_FLAG eq '-1'}">提交</c:if>
						<c:if test="${item.AFFIRM_FLAG eq '0'}">审批中</c:if>
						<c:if test="${item.AFFIRM_FLAG eq '1'}">通过</c:if>
						<c:if test="${item.AFFIRM_FLAG eq '2'}">否决</c:if>
					</c:if>
				</td>
				<td class="td_center">
					<c:if test="${item.ACTIVITY eq '1'}">正常</c:if>	
					<c:if test="${item.ACTIVITY eq '0'}"><font color=red>删除</font></c:if>	
				</td>
				<td class="td_center">
				<a class="add" href="/promoter/viewSelloutRequestDtl?REQ_ID=${item.REQ_ID}&REQ_TITLE=${item.REQ_TITLE}" title="查看"
					         target="dialog"  rel="viewSelloutReqInfo" width="1000" height="700" mask="true">查看</a>
				</td>
				<td>${item.REMARK }</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<input type="hidden" id="FLAG_cx1600" name="FLAG" value="0" />
</form>
<c:set value="/promoter/viewSelloutRequestList" var="pageUrl" /> 
<%@ include file="/WEB-INF/view/inc/initPagination2.jsp"%>
</div>