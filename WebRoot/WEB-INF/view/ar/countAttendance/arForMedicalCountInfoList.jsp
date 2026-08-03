<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage="" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script>
$(document).ready(function(){
	
	 $("#viewCoordApplyAttendanceInfoList_Serch",navTab.getCurrentPanel()).click(function(){
			$("#arForMedicalCountInfoList",navTab.getCurrentPanel()).submit();
	   });
	  //搜索
  $("#seach_KEY",navTab.getCurrentPanel()).keydown(function(e) {
     if ( e.keyCode == 13) {
    	 	var name=encodeURI(encodeURI($('#seach_KEY',navTab.getCurrentPanel()).val()));
    	    var FROM_DATE=encodeURI(encodeURI($('#seach_FROM_DATE',navTab.getCurrentPanel()).val()));
    	    var TO_DATE=encodeURI(encodeURI($('#seach_TO_DATE',navTab.getCurrentPanel()).val()));
    		$('.btnLook',navTab.getCurrentPanel()).attr('href','/ar/attendanceMintenance/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=arForMedicalCountInfoList&seach_KEY='+name+'&seach_FROM_DATE='+FROM_DATE+'&seach_TO_DATE='+TO_DATE);
    		$('.btnLook',navTab.getCurrentPanel()).click();
     }
  });
	 $(".btnLook",navTab.getCurrentPanel()).click(function(e) {
   	 var name=encodeURI(encodeURI($('#seach_KEY',navTab.getCurrentPanel()).val()));
   	 var FROM_DATE=encodeURI(encodeURI($('#seach_FROM_DATE',navTab.getCurrentPanel()).val()));
    	 var TO_DATE=encodeURI(encodeURI($('#seach_TO_DATE',navTab.getCurrentPanel()).val()));
   	$('.btnLook',navTab.getCurrentPanel()).attr('href','/ar/attendanceMintenance/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=arForMedicalCountInfoList&seach_KEY='+name+'&seach_FROM_DATE='+FROM_DATE+'&seach_TO_DATE='+TO_DATE);
  });
	
	
	$(".orderList",navTab.getCurrentPanel()).dataTable({
		"bPaginate": true,    //分页
	    "bAutoWidth":false,//表格宽度自动变化
	    "bProcessing":true,
	    "lengthMenu": [[15,20,35, 50], [15,20, 35, 50]],
		"bLengthChange": true,  //按多少条记录显示下拉框
		"iDisplayLength": 15, //默认每页显示的记录数
		"bFilter": true,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
     	"searching": true,//本地搜索
		"bSort": true,   //排序功能
		"bInfo": true,   //显示datatables的信息（底部的页数，条目数信息）
		"bScrollInfinite":true,
	     "orderClasses": false,
	     "order":[],//初始化不用自动排序
	     "scrollY": $(document.body).height() - 260,
	     "scrollCollapse": false,
	     "deferRender":true,
	     //"scroller":true,
        "oLanguage": {//多语言配置
        	"sProcessing": "正在加载中......",
            "sZeroRecords": "查询不到相关数据！",
            "sEmptyTable": "表中无数据存在！",
            "sSearch": "快速筛选",
            "sLengthMenu": "每页 _MENU_ 条记录",
            "sInfo": "从 _START_ 到 _END_ /共 _TOTAL_ 条数据",
            "sInfoFiltered": "(从 _MAX_ 条记录过滤)",
            "oPaginate": {
                "sPrevious": "上一页",
                "sNext": "下一页"
            }
        },
        "sDom":'<"top"r<"clear">fBl>t<"bottom"ip<"clear">>',
        "buttons": [
              ] 
	});
});
function changeURL_ar0157(personId,startDate,endDate){ 
	var href = "/ar/countAttendance/viewMedicalInfo?seach_PERSON_ID=" + personId + "&seach_START_DATE=" + startDate + "&seach_END_DATE=" + endDate+"";
	$.pdialog.open(href,"ar0157", "明细查看", {width:1000,height:600,mask:true});
}
</script>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/ar/countAttendance/arForMedicalCountInfoList?firstFlag=N"  method="post"
		id="arForMedicalCountInfoList" name="arForMedicalCountInfoList">
		<div class="searchBar">
			<table class="searchContent" style="height: 20px">
				<tr>
				   <td width="7%">社号/姓名</td>
					<td width="23%">
						<div style="float:left"><input type="text" name="seach_KEY" id="seach_KEY" value="${KEY}"/></div>
					</td>
					<%-- <td width="50%" colspan="5">
						<c:if test="${not empty personInfo}">
						<span style="margin-left: 50px;" >${personInfo.LOCAL_NAME }&nbsp/&nbsp${personInfo.EMPID }&nbsp/&nbsp${personInfo.POST_GRADE_NO_NAME}&nbsp/&nbsp${personInfo.EMP_OFFICE_NAME }</span>
						</c:if>
					</td> --%>
					<td><spring:message
						code="hr.viewPersonalInfo.title.DEPTNAME" /> 
					</td>
					<td>
						<ait:deptList name="seach_DEPTNO" cpnyId="${defaultCpny}" limit="ar" id="viewCoordApplyAttendanceInfoList_seachDept" selected="${DEPTNO}"/>
						<ait:deptTreeIcon name="seach_DEPTNO" cpnyId="${defaultCpny}" limit="ar" id="viewCoordApplyAttendanceInfoList_seachDept" selected="${DEPTNO}"/>
					</td>
					<td width="10%">任职状态</td>
					<td width="20%">
				 	<ait:SelectSyCodeByCpnyID id="seach_EmpOffice" name="seach_EmpOffice" parentNo="15118" selected="${EmpOffice}" cnpyID="${LoginUser.cpnyId}" limit="all"/>
					</td>
					<c:if test="${LoginUser.cpnyId eq 'SPC_NJ'}">
					<td>基准日</td>
					<td>
						<input type="text" id="seach_AR_DATE_STR" name="seach_AR_DATE_STR" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy/MM/dd'})" value="${AR_DATE_STR}"/>
					</td>
					</c:if>
				</tr>
			</table>
		</div>
	</form>
</div>

<div class="pageContent">
<div class="formBar">
	<ul class="toolBar">
	<li><a class="buttonActive"  id="viewCoordApplyAttendanceInfoList_Serch" href="#" ><span>查询</span></a></li>
	<li><a class="buttonActive" onclick="downloadExcel('arForMedicalCountInfoList','/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=259&CPNY=${LoginUser.cpnyId}','/ar/countAttendance/arForMedicalCountInfoList?firstFlag=N')"><span>导出到Excel</span></a></li>
	 </ul>
</div>
		<table class="orderList" width="100%">
			<thead>
				<tr>
					<th width="50px">
						NO
					</th>
					<th width="100px">
						<!--姓名 -->
						姓名
					</th>
					<th width="100px">
						<!--社号 -->
						社号
					</th>
					<th width="200px">
						<!--部门名-->
						部门名
					</th>
					<th width="130px">
						入社日
					</th>
					<c:if test="${LoginUser.cpnyId eq 'SPC_NJ'}">
					<th width="130px">
						开始日期
					</th>
					<th width="130px">
						结束日期
					</th>
					<th  width="130px">
						可休医疗期月数
					</th>
					<th width="130px">
						已休医疗期天数
					</th>
					<th width="130px">
						剩余天数
					</th>
					</c:if>
					<c:if test="${LoginUser.cpnyId ne 'SPC_NJ'}">
					<th  width="130px">
						可休医疗期时数
					</th>
					<th width="130px">
						已休医疗期时数
					</th>
					<th width="130px">
						剩余时数
					</th>
					</c:if>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${useOfAnnualLeaveList}" var="item"
					varStatus="i">
					<tr target="sid" rel="">
						<td class="td_type">
							${i.count}
						</td>
						<td class="td_type">
						    ${item.LOCAL_NAME}
						</td>
						<td class="td_type">
						    ${item.EMPID}
						</td>
						<td class="td_type">
						    ${item.DEPATNAME}
						</td>
						<td class="td_type">
						    ${item.DATE_STARTED}
						</td>
						<c:if test="${LoginUser.cpnyId eq 'SPC_NJ'}">
						<td class="td_type">
						    ${item.START_DATE}
						</td>
						<td class="td_type">
						    ${item.END_DATE}
						</td>
						<td class="td_type">						
						   ${item.MEDICAL_HOUR}
						</td>
						<td class="td_type"  style="text-align: center; color: blue; cursor: pointer;" onclick='javascript:changeURL_ar0157(${item.PERSON_ID },"${item.START_DATE }","${item.END_DATE }")' >						
						    ${item.TOTAL_SICK_HOURS}
						</td>
						<td class="td_type">
						    ${item.MEDICAL_DAY - item.TOTAL_SICK_HOURS}
						</td>
						</c:if>
						<c:if test="${LoginUser.cpnyId ne 'SPC_NJ'}">
						<td class="td_type">
						   ${item.MEDICAL_HOUR}
						</td>
						<td class="td_type">
						    ${item.TOTAL_SICK_HOURS}
						</td>
						<td class="td_type">
						    ${item.MEDICAL_DAY - item.TOTAL_SICK_HOURS}
						</td>
						</c:if>
					</tr>
				</c:forEach>
			</tbody>
		</table>
</div>