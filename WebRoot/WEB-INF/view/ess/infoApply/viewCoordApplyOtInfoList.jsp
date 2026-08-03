<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
$(document).ready(function(){
    $("#viewCoordApplyOtInfoList_Serch",navTab.getCurrentPanel()).click(function(){
			$("#viewCoordApplyOtInfoList",navTab.getCurrentPanel()).submit();
	   });
	  //搜索
     $("#seach_KEY",navTab.getCurrentPanel()).keydown(function(e) {
        if ( e.keyCode == 13) {
       	 	var name=encodeURI(encodeURI($('#seach_KEY',navTab.getCurrentPanel()).val()));
       	    var FROM_DATE=encodeURI(encodeURI($('#seach_FROM_DATE',navTab.getCurrentPanel()).val()));
       	    var TO_DATE=encodeURI(encodeURI($('#seach_TO_DATE',navTab.getCurrentPanel()).val()));
       		$('.btnLook',navTab.getCurrentPanel()).attr('href','/ar/attendanceMintenance/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=viewCoordApplyOtInfoList&seach_KEY='+name+'&seach_FROM_DATE='+FROM_DATE+'&seach_TO_DATE='+TO_DATE);
       		$('.btnLook',navTab.getCurrentPanel()).click();
        }
     });
	 $(".btnLook",navTab.getCurrentPanel()).click(function(e) {
      	 var name=encodeURI(encodeURI($('#seach_KEY',navTab.getCurrentPanel()).val()));
      	 var FROM_DATE=encodeURI(encodeURI($('#seach_FROM_DATE',navTab.getCurrentPanel()).val()));
       	 var TO_DATE=encodeURI(encodeURI($('#seach_TO_DATE',navTab.getCurrentPanel()).val()));
      	$('.btnLook',navTab.getCurrentPanel()).attr('href','/ar/attendanceMintenance/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=viewCoordApplyOtInfoList&seach_KEY='+name+'&seach_FROM_DATE='+FROM_DATE+'&seach_TO_DATE='+TO_DATE);
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
	     "scrollY": $(document.body).height() - 350,
	     "scrollCollapse": false,
	     "deferRender":true,
	     //"scroller":true,
        "oLanguage": {//多语言配置
        	"sProcessing": "<spring:message code='hem.alert.empinfo.Is_loading'/>",//正在加载中......
            "sZeroRecords": "<spring:message code='hem.alert.empinfo.not_find_relevant_data'/>",//查询不到相关数据！
            "sEmptyTable": '<spring:message code="ess.infoApply.titel.messages200"/>',
            "sSearch": '<spring:message code="hrm.alert.contractInfo.Rapid_screening"/>',
            "sLengthMenu": '<spring:message code="ess.infoApply.titel.messages201"/> _MENU_ <spring:message code="ess.infoApply.titel.messages202"/>',
            "sInfo": '<spring:message code="hrm.alert.contractInfo.START_END_TOTAL"/>',
            "sInfoFiltered": "(<spring:message code='hrm.alert.contractInfo.Record_filter'/>)",//从 _MAX_ 条记录过滤
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
function searchPop_ess3405(flag) {
	var name = encodeURI(encodeURI($("#seach_KEY", navTab.getCurrentPanel()).val()));
	var seach_FROM_DATE = $("#seach_FROM_DATE", navTab.getCurrentPanel()).val();
	var seach_TO_DATE = $("#seach_TO_DATE", navTab.getCurrentPanel()).val();
	var refreshUrl = '/ess/infoApply/viewCoordApplyOtInfoList?firstFlag=N&seach_FROM_DATE='+seach_FROM_DATE+'&seach_TO_DATE='+seach_TO_DATE;
	var refreshMenuCode = 'ess3405';
	var refreshMenuName = encodeURI(encodeURI('加班查询'));
	//$('#searchPop',navTab.getCurrent())
	$("#searchPop_ess3405", navTab.getCurrentPanel())
			.attr(
					'href',
					'/pa/workManagement/viewEmpForPopList?pageNum=1&numPerPage=10&limit=ar&seach_KEY='
							+ name);
	if (flag == 'onkeyup')
		$("#searchPop_ess3405", navTab.getCurrentPanel()).click();
}
/**
 * 禁用textArea以及Input框的enter键的自动提交
 */
document.onkeydown = function(event) {  
	  var target, code, tag;  
	  if (!event) {  
	       event = window.event; //针对ie浏览器  
	       target = event.srcElement;  
	       code = event.keyCode;  
	       if (code == 13) {  
	           tag = target.tagName;  
	           if (tag == "TEXTAREA") {
		           return true;
		       }else{ 
			       return false;
			   }  
	       }  
	  }else {  
	       target = event.target; //针对遵循w3c标准的浏览器，如Firefox  
	       code = event.keyCode;  
	       if (code == 13) {  
	           tag = target.tagName;  
	           if (tag == "INPUT"){ 
		           return false; 
		       }else {
			        return true;
			   }   
	      }  
	 }  
}
</script>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/ess/infoApply/viewCoordApplyOtInfoList?firstFlag=N" method="post"
		id="viewCoordApplyOtInfoList" name="viewCoordApplyOtInfoList">
		<div class="searchBar">
			<table class="searchContent" style="height: 20px">
			    <tr>					
					<td width="7%"><!--社号/姓名--><spring:message code="public.title.empIdAndName" /></td>
					<td width="23%">
						<div style="float:left"><input type="text" name="seach_KEY" id="seach_KEY" value="${KEY}"/></div>
						<!--<div style="float:left"><a class="btnLook" href="" lookupGroup="person"></a></div>-->
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
						<ait:deptTreeMulti id="seach_DEPTNO_Multi" name="seach_DEPTNO" limit="manager" selectedNm="${DEPTNO}" selected="${DEPTNO_Multi}"></ait:deptTreeMulti>
					</td>
					<%-- <td>
					    <spring:message code="hr.viewPersonalInfo.title.DEPTNAME" /> 
					</td>
					<td>
						<ait:deptList name="seach_DEPTNO" cpnyId="${defaultCpny}" limit="ar" id="viewApplyCoordApplyOtList_seachDept" selected="${DEPTNO}"/>
						<ait:deptTreeIcon name="seach_DEPTNO" cpnyId="${defaultCpny}" limit="ar" id="viewApplyCoordApplyOtList_seachDept" selected="${DEPTNO}"/>
					</td> --%>
				</tr>
				<tr>
				    <td>
						<spring:message code="ess.workgroup.title.duration"/><!-- 期间 -->
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
					<td><!--加班类型--><spring:message code="ess.infoApply.overtime_type" /></td>
					<td>
					    <ait:SelectSyCodeByCpnyID name="seach_OT_TYPE_CODE" parentNo="31" selected="${OT_TYPE_CODE}" limit="ALL"/>
					</td>
					<!--<td>考勤状态</td>	 
					<td>
					     <select name="seach_ITEM_NO" id="seach_ITEM_NO" >
					             <option value="" >请选择</option>
						<c:forEach items="${itemList}" var="item">
								<option value="${item.ITEM_NO}" 
							<c:if test="${item.ITEM_NO eq ITEM_NO}">selected</c:if>
											>
									        ${item.ITEM_NAME}
									</option>
								</c:forEach>
						</select>
							
					</td>
					-->
					<!--<td >工作形态&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<select name="seach_SHIFT_NO" id="seach_SHIFT_NO" >
					        <option value="">请选择</option> 
						    <c:forEach items="${shiftList}" var="item">
							 <option value="${item.SHIFT_NO}" <c:if test="${item.SHIFT_NO eq SHIFT_NO}">selected</c:if>
											>
							   ${item.SHIFT_SHORTNAME}
						   </c:forEach>
						</select>
					</td>-->
				</tr>
				<tr>
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
				    <td><!--员工类型--><spring:message code="ess.infoApply.employee_type"/></td>
					<td>
				 	    <ait:SelectSyCodeByCpnyID id="seach_EMP_TYPE_CODE" name="seach_EMP_TYPE_CODE" parentNo="13864" selected="${EMP_TYPE_CODE}" cnpyID="${LoginUser.cpnyId}" limit="all"/>
					</td>
					<td><!--任职状态--><spring:message code="ess.infoApply.renzhizhuangtai"/></td>
					<td>
				 	<ait:SelectSyCodeByCpnyID id="seach_EmpOffice" name="seach_EmpOffice" parentNo="15118" selected="${EmpOffice}" cnpyID="${LoginUser.cpnyId}" limit="all"/>
					</td>
				</tr>
			</table>
		</div>
	</form>
</div>

<div class="pageContent" >
<div class="formBar">
<div style="font: 12px/ 20px arial, sans-serif; float: left; height: 30px; line-height: 30px;">Total:${fn:length(otCoordList)}</div>
	<ul class="toolBar">
	     <li><a class="buttonActive" id="viewCoordApplyOtInfoList_Serch" href="#"><span><!--查询--><spring:message code="ess.infoApply.SELECT"/></span></a></li>
		 <li><a class="buttonActive" onclick="downloadExcel('viewCoordApplyOtInfoList','/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=133','/ess/infoApply/viewCoordApplyOtInfoList?firstFlag=N')"><span><!--导出到Excel--><spring:message code="ess.infoApply.export_to_Excel"/></span></a></li>
	 </ul>
</div>         
		<table class="orderList" >
			<thead>
				<tr><th rowspan="2"><!--NO-->
						NO
					</th >
					<th rowspan="2">
						<!--社号--><spring:message code="ess.infoApply.EMPID"/>
					</th>
					<th rowspan="2">
				    	<!--姓名--><spring:message code="ess.infoApply.NAME"/>
				    </th>
					<th rowspan="2"> 
						<!--部门名--><spring:message code="ess.infoApply.DEPT_NAME"/>
					</th>
					<th rowspan="2">
						<!--职级--><spring:message code="ess.infoApply.Rank"/>
					</th>
					<th rowspan="2">
						<!--日期--><spring:message code="ess.infoApply.date"/>
					</th>
					<th rowspan="2"><!--星期-->
						<!--星期--><spring:message code="ess.infoApply.week"/>
					</th>
					<th rowspan="2">
						<!--班组--><spring:message code="hr.viewPersonalInfo.title.banzu"/>
					</th>
					<th rowspan="2">
						<!--工作时间--><spring:message code="ess.infoApply.working_hours"/>
					</th>
					<th colspan="6">
						<!--加班--><spring:message code="ess.attendance.ot"/>
					</th>
					<!--<th rowspan="2">
						锁定状态<spring:message code="ess.infoApply.LOCK_STATUS.Z"/>
					</th>-->
				</tr>
				<tr>
					<th>
						<!--进门--><spring:message code="ar.viewarcardrecord.title.jinmen"/>
					</th>
					<th>
						<!--出门--><spring:message code="ar.viewarcardrecord.title.chumen"/>
					</th>
					<th>
						<!--开始时间--><spring:message code="ess.infoApply.title.startTime"/>
					</th>
					<th>
						<!--结束时间--><spring:message code="ess.infoApply.end_time"/>
					</th>
					<th>
						<!--加班类型--><spring:message code="ess.infoApply.overtime_type"/>
					</th>
					<th>
						<!--加班时长--><spring:message code="ess.infoApply.overtime_hours"/>
					</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${otCoordList}" var="CoordOtApply" varStatus="i">	
					<tr target="sid" rel="">
					    <td style="text-align: center">${i.count}</td>
					    <td style="text-align: center">${CoordOtApply.EMPID}</td>
					    <td style="text-align: center">${CoordOtApply.LOCAL_NAME}</td>
						<td style="text-align: center">${CoordOtApply.DEPT_NAME}</td>
						<td style="text-align: center">${CoordOtApply.POST_GRADE_NAME}</td>
						<td style="text-align: center">${CoordOtApply.AR_DATE_STR}</td>
						<td style="text-align: center">
                            <c:if test="${CoordOtApply.IWEEK eq '0'}"><!-- 星期日 --><spring:message code="ar.week.XINGQIRI.b"/></c:if>
	                        <c:if test="${CoordOtApply.IWEEK eq '1'}"><!-- 星期一 --><spring:message code="ar.week.XINGQIYI.b"/></c:if>
	                        <c:if test="${CoordOtApply.IWEEK eq '2'}"><!-- 星期二 --><spring:message code="ar.week.XINGQIER.b"/></c:if>
	                        <c:if test="${CoordOtApply.IWEEK eq '3'}"><!-- 星期三 --><spring:message code="ar.week.XINGQISAN.b"/></c:if>
	                        <c:if test="${CoordOtApply.IWEEK eq '4'}"><!-- 星期四 --><spring:message code="ar.week.XINGQISI.b"/></c:if>
	                        <c:if test="${CoordOtApply.IWEEK eq '5'}"><!-- 星期五 --><spring:message code="ar.week.XINGQIWU.b"/></c:if>
	                        <c:if test="${CoordOtApply.IWEEK eq '6'}"><!-- 星期六 --><spring:message code="ar.week.XINGQILIU.b"/></c:if>
                        </td>
						<td style="text-align: center">${CoordOtApply.SHIFT_NAME}</td>
						<td style="text-align: center">${CoordOtApply.SHIFT_START_TIME}-${CoordOtApply.SHIFT_END_TIME}</td>
						<td style="text-align: center">${CoordOtApply.INDOOR_TIME}</td>
						<td style="text-align: center">${CoordOtApply.OUTDOOR_TIME}</td>
						<td style="text-align: center">${CoordOtApply.FROM_DATE}</td>
						<td style="text-align: center">${CoordOtApply.TO_DATE}</td>
						<td style="text-align: center">${CoordOtApply.ITEM_NAME}</td>
						<td style="text-align: center">
						    ${CoordOtApply.QUANTITY}<!-- 小时 --><spring:message code="ar.viewitemparameter.title.xiaoshi"/>
						 </td>
						<!--<td style="text-align: center">
							 <c:if test="${CoordOtApply.LOCK_YN ne 'N' }">
								锁定<spring:message code="ar.viewCoordApplyAttendanceInfoList.YISUODING.b"/>
							 </c:if>
							
							 <c:if test="${CoordOtApply.LOCK_YN eq 'N' }">
								未锁定<spring:message code="ar.viewCoordApplyAttendanceInfoList.WEISUODING.b"/>
							</c:if>
						</td>-->
						<!--<td style="text-align: center">${CoordOtApply.LEAVEREASON}</td>
						<c:choose>
							<c:when test="${fn:length(CoordOtApply.REASON_OTHER) > 6 }">
								<td style="text-align: center" title="${CoordOtApply.REASON_OTHER}">
										${fn:substring(CoordOtApply.REASON_OTHER,0,6) }...
								</td>
							</c:when>
							<c:otherwise>
								<td style="text-align: center" >
										${CoordOtApply.REASON_OTHER}
								</td>
							</c:otherwise>
						</c:choose>-->
					</tr>
				</c:forEach>
			</tbody>
		</table>
</div>