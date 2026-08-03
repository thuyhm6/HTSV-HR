<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
$(document).ready(function(){
	//查询
	$("#viewArBaseEmpInfo_Serch",navTab.getCurrentPanel()).click(function(){
		$("#viewArBaseEmpInfoForm",navTab.getCurrentPanel()).submit();
	});
	
	$("#seach_KEY",navTab.getCurrentPanel()).keydown(function(e) {
        if ( e.keyCode == 13) {
       	 	var name=encodeURI(encodeURI($('#seach_KEY',navTab.getCurrentPanel()).val()));
       		$('.btnLook',navTab.getCurrentPanel()).attr('href','/hrm/empinfo/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=viewArBaseEmpInfoList&seach_KEY='+name);
       		$('.btnLook',navTab.getCurrentPanel()).click();
        }
    });
	$(".btnLook",navTab.getCurrentPanel()).click(function(e) {
       	 var name=encodeURI(encodeURI($('#seach_KEY',navTab.getCurrentPanel()).val()));
       	$('.btnLook',navTab.getCurrentPanel()).attr('href','/hrm/empinfo/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=viewArBaseEmpInfoList&seach_KEY='+name);
    });

	$(".list",navTab.getCurrentPanel()).dataTable({"bPaginate": false,    //关闭分页
	    "bAutoWidth":false,//表格宽度不自动变化
	    "bProcessing":false,
		"bLengthChange": false,  //关闭按多少条记录显示下拉框
		"bFilter": false,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
		"bSort": true,   //关闭排序功能
		"bInfo": false,   //不显示datatables的信息（底部的页数，条目数信息）
		"bScrollInfinite":true,
		"scrollY": 400,
        "scrollX": true,
        "orderClasses": false
	});
	openOnRight('/ar/arShiftGroupManagement/viewArBaseEmpInfoDetail?PERSON_ID=${arShiftGroupHistoryList[0].PERSON_ID}','viewArBaseEmpInfoDetail');
	$("#viewArBaseEmpInfoList_save").click(function(){
		//确定要保存吗？
		alertMsg.confirm("<spring:message code='ess.message.confirm_sava'/>",
	  		  	{okCall:function(){
			  	$.ajax({
	  				type: 'POST',
	  				url:'/ar/arShiftGroupManagement/updateArBaseEmpInfoDetail',
	  				data:{ID_CARD_NO:$("#ID_CARD_NO").val(),WORK_HOUR_TYPE:$("#WORK_HOUR_TYPE").val(),PERSON_ID:$("#PERSON_ID").val()},
	  				dataType:"json",
	  				cache: false,
	  				success: DWZ.ajaxDone,
	  				error: DWZ.ajaxError
	  			});
	  	}});
	});
	
});

function hiddenleft(hiddenId,showId){
	$("#" + hiddenId).hide();
	$("#" + showId).show();
	$("#layout3").show();
	$("#layout5").hide();
	$("#layout2").hide();
	$("#layout4").hide();
}
function showId(showId){
	$("#" + showId).show();
	$("#viewArBaseEmpInfoList").css("width","430px");
	$("#layout5").show();
	$("#layout2").show();
	$("#layout3").hide();
	$("#layout4").hide();
}
function showIdLeft(showId){
	$("#" + showId).show();
	$("#viewArBaseEmpInfoList").css("width","430px");
	$("#layout5").show();
	$("#layout2").show();
	$("#layout3").hide();
	$("#layout4").hide();
}
function hiddenRight(hiddenId,showId){
	$("#" + hiddenId).hide();
	$("#" + showId).show();
	$("#" + showId).css("width","95%");
	$("#layout5").hide();
	$("#layout2").show();
	$("#layout3").hide();
	$("#layout4").show();
}
function searchPop_ar0606(flag) {
	var name = encodeURI(encodeURI($("#seach_KEY", navTab.getCurrentPanel())
			.val()));
	var refreshUrl = '/ar/arShiftGroupManagement/viewArBaseEmpInfoList?firstView=1';
	var refreshMenuCode = 'ar0606';
	var refreshMenuName = encodeURI(encodeURI("<spring:message code='ar.viewArBaseEmpInfoList.KAOQINJIBENSHIXIANG.b'/>"));//考勤基本事项
	//$('#searchPop',navTab.getCurrent())
	$("#searchPop_ess3404", navTab.getCurrentPanel())
			.attr(
					'href',
					'/pa/workManagement/viewEmpForPopList?pageNum=1&numPerPage=10&limit=ar&seach_KEY='
							+ name
							+ '&refreshUrl='
							+ refreshUrl
							+ '&refreshMenuCode='
							+ refreshMenuCode
							+ '&refreshMenuName=' + refreshMenuName);
	if (flag == 'onkeyup')
		$("#searchPop_ar0606", navTab.getCurrentPanel()).click();
}
</script>
<div class="pageHeader">
	<form id="viewArBaseEmpInfoForm" onsubmit="return navTabSearch(this);" 
		action="/ar/arShiftGroupManagement/viewArBaseEmpInfoList" method="post">
		<input type="hidden" value="ar" name="limit" />
		<input type="hidden" value="1" name="firstView" />
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					<!-- 工号/姓名： --> <spring:message
						code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME" />
				</td>
				<td><input type="text" name="seach_KEY" id="seach_KEY"
					value="${KEY}"
					onkeydown="javascript:if(event.keyCode == 13)searchPop_ar0606('onkeyup');" />
				</td>
				<td><a class="btnLook" id="searchPop_ar0606"
					onclick="searchPop_ar0606()" href="#" lookupGroup="person"> </a></td>
				<td>${empInfoShow } </a>
				</td>
			</tr>
			<tr>
				<td>
					<!-- 部门： --> <spring:message
						code="hr.viewPersonalInfo.title.DEPTNAME" />
				</td>
				<td><ait:deptList name="seach_DEPTNO"
						cpnyId="${LoginUser.cpnyId}" limit="ar"
						id="viewArBaseEmpInfoList_seachDept" /> <ait:deptTreeIcon
						name="seach_DEPTNO" cpnyId="${LoginUser.cpnyId}" limit="ar"
						id="viewArBaseEmpInfoList_seachDept" selected="${DEPTNO}" /></td>
				<td><!-- 班组类型 --><spring:message code="ar.viewArBaseEmpInfoList.BANZULEIXING.b" /></td>
				<td><ait:SelectSyCodeByCpnyID name="seach_SHIFT_NO"
						parentNo="400223" cnpyID="${LoginUser.cpnyId}" limit="all"
						selected="${SHIFT_NO }" />
				</td>
				<td><!-- 员工类型 --><spring:message code="org.title.EMP_TYPE" /></td>
				<td><ait:SelectSyCodeByCpnyID name="seach_EMP_TYPE_CODE"
						parentNo="13864" cnpyID="${LoginUser.cpnyId}" limit="all"
						selected="${EMP_TYPE_CODE }" />
				</td>
				<td><!-- 职种 --><spring:message code="hrm.contract.POSITION" /></td>
				<td>
					<ait:SelectSyCodeByCpnyID name="seach_POSITION" selected="${POSITION}" parentNo="123228"  limit="all" />
				</td>
				<td><input name="includeLeftManYN"  value="Y" type="checkbox" <c:if test="${includeLeftManYN == 'Y' }">checked</c:if>/><!-- 包含退职人员 --><spring:message code="ar.viewArBaseEmpInfoList.BAOHANTUIZHIRENYUAN.b" /></td>
			</tr>
		</table>
	</div>
	</form>
</div>

<div class="formBar">
	<ul class="toolBar">
	    <li><a class="buttonActive" id="viewArBaseEmpInfo_Serch" href="#"><span><!-- 查询 --><spring:message code="hrm.empinfo.QUERY" /></span></a></li>
<%-- 		<c:if test="${LoginUser.cpnyId eq 'SST'}"><li><a class="buttonActive" id="viewArBaseEmpInfoList_save" href="#"><span>保存</span></a></li></c:if> --%>
</ul>
</div>
<div class="pageContent">
	<div id="viewArBaseEmpInfoList" style="float:left; display:block; overflow:auto;width:430px; height:450px; border:solid 1px #CCC; line-height:21px; background:#fff">
		<div class="user_table" style="font:bold 12px/20px arial,sans-serif;">Total:${totalCount}</div>
		<table class="list" style="width:430px;">
			<thead>
				<tr>
					<th width="2%">No.</th>
					<th><!-- 姓名 --><spring:message code="ess.infoApply.NAME" /></th>
					<th><!-- 工号 --><spring:message code="ess.infoApply.EMP_ID" /></th>
					<th ><!-- 部门 --><spring:message code="org.title.dept" /></th>
					<th ><!-- 职种 --><spring:message code="hrm.contract.POSITION" /></th>
					<th ><!-- 工作地 --><spring:message code="org.title.WORD_AREA_NAME" /></th>
					<th ><!-- 工时类型 --><spring:message code="hrm.empinfo.WORK_TIME_TYPE" /></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${arShiftGroupHistoryList}" var="item" varStatus="i">
					<tr onclick="openOnRight('/ar/arShiftGroupManagement/viewArBaseEmpInfoDetail?PERSON_ID=${item.PERSON_ID}','viewArBaseEmpInfoDetail');">
						<td style="text-align:left">${i.count}</td>
						<td style="text-align:left">${item.LOCAL_NAME}</td>
						<td class='td_center' >${item.EMPID}</td>
						<td class='td_center' >${item.DEPTNAME}</td>
						<td class='td_center' >${item.POSITION_NAME}</td>
						<td class='td_center' >${item.WORK_AREA_NAME}</td>
						<td class='td_center' >${item.WORK_HOUR_TYPE}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<div class="w-layout-collapse">
		<div id="layout5" class="w-layout-collapse-left" onclick="hiddenRight('viewArBaseEmpInfoDetail','viewArBaseEmpInfoList')"></div>
		<div id="layout4" class="w-layout-collapse-right" style="display:none;" onclick="showIdLeft('viewArBaseEmpInfoDetail')"></div>
		<div id="layout2" class="w-layout-collapse-right" onclick="hiddenleft('viewArBaseEmpInfoList','viewArBaseEmpInfoDetail')"></div>
		<div id="layout3" class="w-layout-collapse-left" style="display:none;" onclick="showId('viewArBaseEmpInfoList')"></div>
	</div>
	<div id="viewArBaseEmpInfoDetail"  style="display:block;"></div>
</div>