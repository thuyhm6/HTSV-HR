<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script>
$(document).ready(function(){
     $("#viewCoordApplyAttendanceInfoList_Serch",navTab.getCurrentPanel()).click(function(){
			$("#viewCoordApplyAttendanceInfoList",navTab.getCurrentPanel()).submit();
	   });
	  //搜索
     $("#seach_KEY",navTab.getCurrentPanel()).keydown(function(e) {
        if ( e.keyCode == 13) {
       	 	var name=encodeURI(encodeURI($('#seach_KEY',navTab.getCurrentPanel()).val()));
       	    var FROM_DATE=encodeURI(encodeURI($('#seach_FROM_DATE',navTab.getCurrentPanel()).val()));
       	    var TO_DATE=encodeURI(encodeURI($('#seach_TO_DATE',navTab.getCurrentPanel()).val()));
       		$('.btnLook',navTab.getCurrentPanel()).attr('href','/ar/attendanceMintenance/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=viewCoordApplyAttendanceInfoList&seach_KEY='+name+'&seach_FROM_DATE='+FROM_DATE+'&seach_TO_DATE='+TO_DATE);
       		$('.btnLook',navTab.getCurrentPanel()).click();
        }
     });
	 $(".btnLook",navTab.getCurrentPanel()).click(function(e) {
      	 var name=encodeURI(encodeURI($('#seach_KEY',navTab.getCurrentPanel()).val()));
      	 var FROM_DATE=encodeURI(encodeURI($('#seach_FROM_DATE',navTab.getCurrentPanel()).val()));
       	 var TO_DATE=encodeURI(encodeURI($('#seach_TO_DATE',navTab.getCurrentPanel()).val()));
      	$('.btnLook',navTab.getCurrentPanel()).attr('href','/ar/attendanceMintenance/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=viewCoordApplyAttendanceInfoList&seach_KEY='+name+'&seach_FROM_DATE='+FROM_DATE+'&seach_TO_DATE='+TO_DATE);
     });
     
		$(".orderList",navTab.getCurrentPanel()).dataTable({
			"bPaginate": true,    //分页
		    "bAutoWidth":false,//表格宽度自动变化
		    "bProcessing":true,
		    "lengthMenu": [[50,100,200, 500], [50,100,200, 500]],
			"bLengthChange": true,  //按多少条记录显示下拉框
			"iDisplayLength": 50, //默认每页显示的记录数
			"bFilter": true,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
	     	"searching": true,//本地搜索
			"bSort": true,   //排序功能
			"bInfo": true,   //显示datatables的信息（底部的页数，条目数信息）
			"bScrollInfinite":true,
		     "orderClasses": false,
		     "order":[],//初始化不用自动排序
		     "scrollY": $(document.body).height() - 320,
		     "scrollCollapse": false,
		     "deferRender":true,
		     "scroller":true,
	        "oLanguage": {//多语言配置
	            //正在加载中......
	        	"sProcessing": "<spring:message code='ess.message.loading' />",
	        	//查询不到相关数据！
	            "sZeroRecords": "<spring:message code='ess.message.NOT_FOUND_DATA' />",
	            "sEmptyTable": '<spring:message code="ess.infoApply.titel.messages200"/>',
	            "sSearch": '<spring:message code="hrm.alert.contractInfo.Rapid_screening"/>',
	            "sLengthMenu": '<spring:message code="ess.infoApply.titel.messages201"/> _MENU_ <spring:message code="ess.infoApply.titel.messages202"/>',
	            "sInfo": '<spring:message code="hrm.alert.contractInfo.START_END_TOTAL"/>',
	            //(从 _MAX_ 条记录过滤)
	            "sInfoFiltered": "<spring:message code='ess.message.filter_from_max' />",
	            "oPaginate": {
	                "sPrevious": '<spring:message code="hrm.alert.contractInfo.Previous_page"/>',
	                "sNext": '<spring:message code="hrm.alert.contractInfo.NEXT_PAGE"/>'
	            }
	        },
	        "sDom":'<"top"r<"clear">fBl>t<"bottom"ip<"clear">>',
	        "buttons": [
	              ] 
		});
});

function searchPop_ess2222(flag) {
	var name = encodeURI(encodeURI($("#seach_KEY", navTab.getCurrentPanel()).val()));
	var seach_applyBatchdate = $("#seach_applyBatchdate", navTab.getCurrentPanel()).val();
	//$('#searchPop',navTab.getCurrent())
	$("#searchPop_ess2222", navTab.getCurrentPanel())
			.attr(
					'href',
					'/pa/workManagement/viewEmpForPopList?pageNum=1&numPerPage=10&limit=ar&seach_KEY='
							+ name);
	if (flag == 'onkeyup')
		$("#searchPop_ess2222", navTab.getCurrentPanel()).click();
}

</script>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/ess/infoApplyAttendance/viewCoordApplyAttendanceInfoList?firstFlag=N"  method="post"
		id="viewCoordApplyAttendanceInfoList" name="viewCoordApplyAttendanceInfoList">
		<div class="searchBar">
			<table class="searchContent">
			    <tr>					
					<td width="7%"><!-- 姓名/社号 --><spring:message code="ess.infoApply.NAME_EMPID" /></td>
					<td width="23%">
						<div style="float:left"><input type="text" name="seach_KEY" id="seach_KEY" value="${KEY}"/></div>
					</td>
					<td><spring:message
						code="hr.viewPersonalInfo.title.DEPTNAME" /> 
					</td>
					<td>
						<ait:deptTreeMulti id="seach_DEPTNO_Multi" name="seach_DEPTNO" limit="manager" selectedNm="${DEPTNO}" selected="${DEPTNO_Multi}"></ait:deptTreeMulti>
					</td>
				</tr>
				<tr>
				    <td>
						<spring:message code="ess.workgroup.title.duration"/>
					</td>
					<td>
					     <input type="text" name="seach_FROM_DATE" id="seach_FROM_DATE"  class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})" value="${FROM_DATE}"/>
						~
					    <input type="text" name="seach_TO_DATE" id="seach_TO_DATE"  class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})" value="${TO_DATE}"/>
					</td>
					<td><spring:message code="hrm.empinfo.POST_FAMILY"/><!-- 职群 --></td>
					<td><ait:selectCodeMulti id="seach_POST_FAMILY_Multi"
							name="seach_POST_FAMILY_NAME" parentNo="14015812"
							selected="${POST_FAMILY_Multi}" selectedNm="${POST_FAMILY_NAME}" />
					</td>
					
					<td ><!--班组--><spring:message code="hr.viewPersonalInfo.title.banzu"/> </td>
					<td >
						<%-- <ait:SelectSyCodeByCpnyID id="seach_GROUP_NO" name="seach_SHIFT_NO" parentNo="400223" selected="${SHIFT_NO}" cnpyID="${LoginUser.cpnyId}" limit="all"/>  --%>
						<select name="seach_SHIFT_NO" id="seach_SHIFT_NO">
							<option value=""><spring:message code="hr.viewCondSql.title.QINGXUANZE"/><!--请选择--></option>
							<c:forEach items="${shiftList}" var="result">
								<option value="${result.SHIFT_NAME}" name="${result.SHIFT_NAME}" <c:if test="${result.SHIFT_NAME eq SHIFT_NAME}">selected="selected"</c:if>>${result.SHIFT_NAME}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td width="10%"><!--员工类型 --><spring:message code="org.title.EMP_TYPE" /></td>
						<td width="20%">
				 	<ait:SelectSyCodeByCpnyID id="seach_EMP_TYPE_CODE" name="seach_EMP_TYPE_CODE" parentNo="13864" selected="${EMP_TYPE_CODE}" cnpyID="${LoginUser.cpnyId}" limit="all"/>
						</td>
					<td width="10%"><!--任职状态 --><spring:message code="ess.infoApply.renzhizhuangtai" /></td>
					<td width="20%">
				 	<ait:SelectSyCodeByCpnyID id="seach_EmpOffice" name="seach_EmpOffice" parentNo="15118" selected="${EmpOffice}" cnpyID="${LoginUser.cpnyId}" limit="all"/>
					</td>
							<td><!-- 考勤状态 --><spring:message code="ess.infoApply.attendState" /></td>	 
					<td>
					     <!--<select name="seach_ITEM_NO" id="seach_ITEM_NO" >
					             <option value="" > 请选择 <spring:message code="org.title.PLEASE_SELECT" /></option>
						<c:forEach items="${itemList}" var="item">
								<option value="${item.ITEM_NO}" 
							<c:if test="${item.ITEM_NO eq ITEM_NO}">selected</c:if>
											>
									        ${item.ITEM_NAME}
									</option>
								</c:forEach>
						</select>-->
						<ait:selectCodeMultiArDetail id="seach_AR_DETAIL_ITEM" name="seach_AR_DETAIL_ITEM_NAME"  selected="${AR_DETAIL_ITEM}" selectedNm="${AR_DETAIL_ITEM_NAME}"/>
						<img alt="clear" src="/resources/images/newImages/Modify_little.gif" style="vertical-align:middle ;"
						onclick="$('input[name=seach_AR_DETAIL_ITEM_NAME]',navTab.getCurrentPanel()).attr('value','');$('input[name=seach_AR_DETAIL_ITEM]',navTab.getCurrentPanel()).attr('value','');">
							
					</td>			
				</tr>
			</table>
		</div>
	</form>
</div>

<div class="pageContent" >
<div class="formBar">
<div style="font: 12px/ 20px arial, sans-serif; float: left; height: 30px; line-height: 30px;">Total:${fn:length(leaveCoordList)}</div>
	<ul class="toolBar">
	<li><a class="buttonActive"  id="viewCoordApplyAttendanceInfoList_Serch" href="#" ><span><!--查询 --><spring:message code="org.title.SELECT" /></span></a></li>
	<li><a class="buttonActive" onclick="downloadExcel('viewCoordApplyAttendanceInfoList','/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=134&firstFlag=N&CPNY=${LoginUser.cpnyId}','/ess/infoApplyAttendance/viewCoordApplyAttendanceInfoList?firstFlag=N')"><span><!--导出到Excel --><spring:message code="org.title.exportLOtImportExcel" /></span></a></li>
	 </ul>
</div>
		<table class="orderList" width="100%">   
			<thead>
				<tr><th><!--NO-->
						NO
					</th>
					<th>
						<!--社号 --><spring:message code="org.title.EMPID" />
					</th>
					<th>
				    	<!--姓名 --><spring:message code="org.title.LOCAL_NAME" />
				    </th>
					<th>
						<!--部门名 --><spring:message code="ess.infoApply.DEPT_NAME" />
					</th>
					<th>
						<!--职级--><spring:message code="ess.infoApply.Rank" />
					</th>
					<th>
						<!--班组--><spring:message code="hr.viewPersonalInfo.title.banzu"/>
					</th>
					<th>
						<!--考勤状态 --><spring:message code="ess.infoApply.attendState" />
					</th>
					<th>
						<!--考勤时长 --><spring:message code="ess.infoApply.attendance_time" />
					</th>
					<th>
						<!--日期 --><spring:message code="org.title.DATE" />
					</th>
					<th>
						<!--开始时间 --><spring:message code="ess.infoApply.title.startTime" />
					</th>
					<th>
						<!--结束时间 --><spring:message code="ess.infoApply.end_time" />
					</th>
					<!--<th>
						锁定状态 <spring:message code="ess.infoApply.LOCK_STATUS.Z" />
					</th>-->
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${leaveCoordList}" var="CoordleaveApply" varStatus="i">	
					<tr target="sid" rel="">
					    <td style="text-align: center">${i.count}</td>
					    <td style="text-align: center">${CoordleaveApply.EMPID}</td>
					    <td style="text-align: center">${CoordleaveApply.LOCAL_NAME}</td>
						<td style="text-align: center">${CoordleaveApply.DEPT_NAME}</td>
						<td style="text-align: center">${CoordleaveApply.POST_GRADE_NAME}</td>
						<td style="text-align: center">${CoordleaveApply.SHIFT_NAME}</td>
						<td style="text-align: center">${CoordleaveApply.ITEM_NAME}</td>
						<td style="text-align: center">${CoordleaveApply.QUANTITY}&nbsp;${CoordleaveApply.UNIT}</td>
						<td style="text-align: center">${CoordleaveApply.AR_DATE_STR}</td>
						<td style="text-align: center">${CoordleaveApply.FROM_DATE}</td>
						<td style="text-align: center">${CoordleaveApply.TO_DATE}</td>
						<!--<td style="text-align: center">
							<c:if test="${CoordleaveApply.LOCK_YN eq 'Y'}">
								已锁定 <spring:message code="ar.viewCoordApplyAttendanceInfoList.YISUODING.b" />
							</c:if>
							<c:if test="${CoordleaveApply.LOCK_YN eq 'N'}">
								未锁定 <spring:message code="ar.viewCoordApplyAttendanceInfoList.WEISUODING.b" />
							</c:if>
						</td>-->
					</tr>
				</c:forEach>
			</tbody>
		</table>
</div>