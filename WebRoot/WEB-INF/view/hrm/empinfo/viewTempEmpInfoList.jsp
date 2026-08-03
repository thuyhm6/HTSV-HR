<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript"> 
$(document).ready(function(){
	$("#empWomenInfo_search",navTab.getCurrentPanel()).click(function(){
		$("#viewEmpWomenInfo",navTab.getCurrentPanel()).submit();
	});
	$("#seach_KEY",navTab.getCurrentPanel()).keydown(function(e) {
        if ( e.keyCode == 13) {
       	 	var name=encodeURI(encodeURI($('#seach_KEY',navTab.getCurrentPanel()).val()));
       		$('.btnLook',navTab.getCurrentPanel()).attr('href','/hrm/empinfo/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=viewArCardRecord&seach_KEY='+name);
       		$('.btnLook',navTab.getCurrentPanel()).click();
        }
    });
	$(".btnLook",navTab.getCurrentPanel()).click(function(e) {
       	 var name=encodeURI(encodeURI($('#seach_KEY',navTab.getCurrentPanel()).val()));
       	$('.btnLook',navTab.getCurrentPanel()).attr('href','/hrm/empinfo/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=viewArCardRecord&seach_KEY='+name);
    });

	$(".list",navTab.getCurrentPanel()).dataTable({"bPaginate": false,    //关闭分页
	    "bAutoWidth":false,//表格宽度不自动变化
	    "bProcessing":false,
		"bLengthChange": false,  //关闭按多少条记录显示下拉框
		"bFilter": true,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
		"bSort": true,   //关闭排序功能
		"bInfo": false,   //不显示datatables的信息（底部的页数，条目数信息）
		"bScrollInfinite":true,
		"bAutoWidth": false,    //表格宽度不自动变化
		"scrollY": $(document.body).height() - 310,
        "scrollX": true,
        "orderClasses": false,
        "oLanguage": {
			//正在加载中......
	    	"sProcessing": "<spring:message code='ess.message.loading' />",
	    	//查询不到相关数据！
	        "sZeroRecords": "<spring:message code='ess.message.NOT_FOUND_DATA' />",
	        //表中无数据存在！
	        "sEmptyTable": "<spring:message code='ess.message.NOT_FOUND_DATA_FROM_TABLE' />",
	        //快速筛选
	        "sSearch": "<spring:message code='ess.message.rapid_screening' />"
        } //多语言配置
	});
});

function f_delete_viewtempEmpInfo(callback) {
	var checked=false;
	var ids= document.getElementsByName("c1");
	for(var i=0;i<ids.length;i++){
		if(ids[i].checked){
			checked=true;
		}
	}
	if(!checked){
		//请选择信息再进行删除操作!
		alert("<spring:message code='ar.alert.message.viewArAnnualStandard.choosedelete'/>");
		return;
	}
	var defaultCpny = $("#defaultCpny").val();
	//json传值
	var jsonData = '[';

	$.each($("input[name='c1']"),
	function(i, obj) {
		if (obj.checked) {
			
			if (jsonData.length > 1) {

				jsonData += ',{';
			} else {
				jsonData += '{';
			}

			jsonData += ' "SPECIAL_NO": "' + obj.value + '",';
			jsonData += ' "CPNY_ID": "${LoginUser.cpnyId}" ';
			jsonData += '}';

		}
	});
	jsonData += ']';

	if (jsonData.length == 2) {
		//请选择要删除的数据
		alertMsg.error("<spring:message code='ar.alert.message.viewArAnnualStandard.chooseinfo'/>");
		return;
	}
	//确定要提交吗？
	if (confirm ("<spring:message code='button.delete.sure'/>")){	
		$.ajax({
			type: 'POST',
			url: '/hrm/empinfo/deleteSpecialMatter',
			data: [{ name: 'jsonData', value: jsonData }],
			dataType:"json",
			cache: false,
			success: callback || DWZ.ajaxDone,
			error: DWZ.ajaxError
		});	
	}
}

</script>
<div class="pageHeader">
<form id="viewEmpWomenInfo" onsubmit="return navTabSearch(this);" action="/hrm/empinfo/viewTempEmpInfoList?firstFlag=N&defaultCpny=${defaultCpny }" method="post" rel="pagerForm">
<input type="hidden" name='CPNY' value="${LoginUser.cpnyId }"/>
<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td><!-- 社号/姓名 --><spring:message code="hrm.empinfo.nameAndEmpid"/></td>
					<td>
						<div style="float:left">
							<input type="text" name="KEY" id="KEY" value="${KEY}"/>
						</div>
						<input type="hidden" name="seach_EMPID" id="seach_EMPID" value="${personInfo.EMPID}"/>
						<!-- <div style="float:left"><a class="btnLook" href="" lookupGroup="person"></a></div> -->
					</td>
					<%-- <td colspan="3">
						<c:if test="${not empty personInfo}">
							<span style="margin-left: 50px;" >${personInfo.LOCAL_NAME }&nbsp/&nbsp${personInfo.EMPID }&nbsp/&nbsp${personInfo.POST_GRADE_NO_NAME}&nbsp/&nbsp${personInfo.EMP_OFFICE_NAME }</span>
						</c:if>
					</td>
					<td></td> --%>
					
					<td>
						<spring:message code="hr.viewPersonalInfo.title.DEPTNAME" /> 
					</td>
					<td>
						<ait:deptList name="DEPTNO" limit="ar" id="viewArCardRecordList_seachDept" selected="${DEPTNO}"/>
						<ait:deptTreeIcon name="DEPTNO" limit="ar" id="viewArCardRecordList_seachDept" selected="${DEPTNO}"/>
					</td>
					<td class="text"><spring:message code="inct.salesman.classify" /> <!-- 信息区分 --> </td>
						<td class="td_type">
							<ait:SelectSyCodeByCpnyID name="INFOR_DIS_CODE" id="INFOR_DIS_CODE" parentNo="14014361" selected="${INFOR_DIS_CODE}" limit="all" /></td>
					<td width="5%" style="padding-top: 4px">
						<spring:message code="ar.viewcycle.title.jieshuri" /><!--结束日期-->
					</td>
					<td width="5%" style="padding-top: 4px">
						<input type="text" id="START_DATE" name="START_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})" value="${START_DATE}"/> ~ 
						<input type="text" id="END_DATE" name="END_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})" value="${END_DATE}"  />
					</td>
				</tr>
			</table>
		</div>
</form>
</div>
<div class="pageContent">
<div class="formBar">
		<ul class="toolBar">
			<li>	            
				<a class="buttonActive" id="empWomenInfo_search" href="#"><span><!-- 查询 --><spring:message code="hrm.empinfo.QUERY"/></span></a> 
			</li>	
			<%-- <li>
				<a href="/ess/infoApplyLeave/downloadFile?fileName=/resources/template/cardTemplate.xls&file=cardTemplate.xls"><span><!-- 下载导入模板 --><spring:message code="ar.addempshift.title.downloadmodule"/></span></a>
			</li>
			<li>
				<a class="buttonActive" href="/pa/excelImport/importExcelData?&importFunName=/importArCardRecordExcel" target="dialog" mask="true" width="400" height="200" ><span><!-- EXCEL导入 --><spring:message code="ar.addempshift.title.excelimport"/></span></a>
			</li> --%>
			<li>
				<a class="buttonActive" href="/hrm/empinfo/addTempEmpInfoList" target="dialog" mask="true" width="750" height="400" rel="addArCardRecordInfoView"><span><!-- 添加 --><spring:message code="ess.empInfo.insert"/></span></a>
			</li>
			<li>
				<a class="buttonActive" href="#" onclick="javascript:f_delete_viewtempEmpInfo(navTabAjaxDoneWithForm);"><span><!-- 删除 --><spring:message code="ess.empInfo.Delete"/></span></a>
			</li>
			<li>
				<a class="buttonActive" href="/hrm/empinfo/updateTempEmpInfo?SPECIAL_NO={SPECIAL_NO}" target="dialog" mask="true" width="750" height="500" ><span><!-- 修改 --><spring:message code="ess.empInfo.modify"/></span></a>
			</li>
			<%-- <li>
				<a class="buttonActive" href="/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=24">
				<span><spring:message code="hrm.empinfo.EXPORT"/><!-- 导出到EXECL --></span></a>					
			</li> --%>
			<li>
				<a class="buttonActive" onclick="downloadExcel('viewEmpWomenInfo','/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=24','/hrm/empinfo/viewTempEmpInfoList')">
				<span><!--导出到Excel --><spring:message code="ess.infoApply.export_to_Excel" /></span>
				</a>
			</li>
	</ul>
</div>
	<div class="user_table" style="font:bold 12px/20px arial,sans-serif;">Total:${fn:length(getArCardRecordList)}</div>
	<table class="list" width="99%">
		<thead>
			<tr>
				<th width="2%" align="center" >No.</th>
				<th width="1%" align="center" ><input type="checkbox" class="checkboxCtrl" group="c1"></th>
				<th width="5%"><!-- 工号 --><spring:message code="public.title.empId"/></th>
				<th width="10%"><!-- 姓名 --><spring:message code="public.title.name"/></th>
				<th width="15%"><!-- 部门 --><spring:message code="public.title.deptName"/></th>
				<th width="6%"><!-- 职级 --><spring:message code="ess.infoApply.Rank"/></th>
				<th width="10%"><spring:message code="inct.salesman.classify"/><!-- 信息区分 --></th>
				<th width="6%"><spring:message code="ar.viewcycle.title.kaishiri"/><!-- 开始日期 --></th>
				<th width="6%"><spring:message code="ar.viewcycle.title.jieshuri"/><!-- 结束日期 --></th>
				<th width="6%"><spring:message code="ar.viewcycle.title.canNotOtDate"/><!-- No ot date --></th>
				<th width="10%"><spring:message code="hrm.contract.content"/><!-- 内容 --></th>
				<th width="6%"><spring:message code="ess.attendance.ot"/><!-- Overtime --></th>
				<th width="6%"><spring:message code="hrm.empinfo.UPDATED_BY"/><!-- 变更者 --></th>
				<th width="6%"><spring:message code="hrm.empinfo.UPDATE_DATE"/><!-- 变更时间 --></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${empInfo}" var="list" varStatus="i">
				<tr target="SPECIAL_NO" rel="${list.SPECIAL_NO}">
					<td>${i.count}</td>
					<td><input type="checkbox" name="c1" value="${list.SPECIAL_NO }"></td>
					<td>${list.EMPID}</td>
					<td>${list.LOCAL_NAME}</td>
					<td>${list.DEPTNAME}</td>
					<td>${list.POST_GRADE_NO_NAME_TITLE}</td>
					<td>${list.INFOR_DIS_CODE_NAME}</td>
					<td>${list.START_DATE}</td>
					<td>${list.END_DATE}</td>
					<td>${list.NO_OT_DATE}</td>
					<td>${list.SPECIAL_CONTENT}</td>
					<td>
						<c:if test="${list.OT_FLAG == 1 }"><spring:message code="ar.viewcycle.content.yes" /></c:if><!-- Yes --> 
						<c:if test="${list.OT_FLAG == 0 }"><spring:message code="ar.viewcycle.content.no" /></c:if><!--No--> 
				</td>
					<td>${list.UPDATED_BY}</td>
					<td>${list.UPDATE_DATE}</td>
				</tr>
			</c:forEach>			
		</tbody>
	</table>
<c:set value="/hrm/empinfo/viewTempEmpInfoList?firstFlag=N&seach_defaultCpny=${defaultCpny}&defaultCpny=${defaultCpny }&seach_DEPTNO=${DEPTNO }&seach_POSITION=${POSITION }&seach_EMP_TYPE_GROUP=${EMP_TYPE_GROUP }&seach_EMP_TYPE=${EMP_TYPE }&seach_EMP_OFFICE_NAME=${EMP_OFFICE_NAME }" var="pageUrl"/>
<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>
