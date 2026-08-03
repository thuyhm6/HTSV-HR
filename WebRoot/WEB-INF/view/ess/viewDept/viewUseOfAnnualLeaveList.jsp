<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage="" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script>
$(document).ready(function(){
	
	 $("#viewCoordApplyAttendanceInfoList_Serch",navTab.getCurrentPanel()).click(function(){
			$("#viewUseOfAnnualLeaveList",navTab.getCurrentPanel()).submit();
	   });
	  //搜索
  $("#seach_KEY",navTab.getCurrentPanel()).keydown(function(e) {
     if ( e.keyCode == 13) {
    	 	var name=encodeURI(encodeURI($('#seach_KEY',navTab.getCurrentPanel()).val()));
    	    var FROM_DATE=encodeURI(encodeURI($('#seach_FROM_DATE',navTab.getCurrentPanel()).val()));
    	    var TO_DATE=encodeURI(encodeURI($('#seach_TO_DATE',navTab.getCurrentPanel()).val()));
    		$('.btnLook',navTab.getCurrentPanel()).attr('href','/ar/attendanceMintenance/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=viewUseOfAnnualLeaveList&seach_KEY='+name+'&seach_FROM_DATE='+FROM_DATE+'&seach_TO_DATE='+TO_DATE);
    		$('.btnLook',navTab.getCurrentPanel()).click();
     }
  });
	 $(".btnLook",navTab.getCurrentPanel()).click(function(e) {
   	 var name=encodeURI(encodeURI($('#seach_KEY',navTab.getCurrentPanel()).val()));
   	 var FROM_DATE=encodeURI(encodeURI($('#seach_FROM_DATE',navTab.getCurrentPanel()).val()));
    	 var TO_DATE=encodeURI(encodeURI($('#seach_TO_DATE',navTab.getCurrentPanel()).val()));
   	$('.btnLook',navTab.getCurrentPanel()).attr('href','/ar/attendanceMintenance/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=viewUseOfAnnualLeaveList&seach_KEY='+name+'&seach_FROM_DATE='+FROM_DATE+'&seach_TO_DATE='+TO_DATE);
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
	     "scrollX": true,
	     "scrollCollapse": false,
	     "deferRender":true,
	     //"scroller":true,
        "oLanguage": {//多语言配置
		//正在加载中......
    	"sProcessing": "<spring:message code='ess.message.loading' />",
        //查询不到相关数据！
        "sZeroRecords": "<spring:message code='ess.message.NOT_FOUND_DATA' />",
        //表中无数据存在！
        "sEmptyTable": "<spring:message code='ess.message.NOT_FOUND_DATA_FROM_TABLE' />",
        //快速筛选
        "sSearch": "<spring:message code='ess.message.rapid_screening' />",
        //每页 _MENU_ 条记录
        "sLengthMenu": "<spring:message code='ess.message.page_of_lines' />",
        //从 _START_ 到 _END_ /共 _TOTAL_ 条数据
        "sInfo": "<spring:message code='ess.message.sum_begin_to_end' />",
        //(从 _MAX_ 条记录过滤)
        "sInfoFiltered": "<spring:message code='ess.message.filter_from_max' />",
        "oPaginate": {
            //上一页
            "sPrevious": "<spring:message code='ess.message.previous_page' />",
            //下一页
            "sNext": "<spring:message code='ess.message.next_page' />"
            }
        },
        "sDom":'<"top"r<"clear">fBl>t<"bottom"ip<"clear">>',
        "buttons": [
              ] 
	});
});

function tijiao(){

		$("#viewUseOfAnnualLeaveList").submit();
	}
</script>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/ess/viewDept/viewUseOfAnnualLeaveList?firstFlag=N"  method="post"
		id="viewUseOfAnnualLeaveList" name="viewUseOfAnnualLeaveList">
		<div class="searchBar">
			<table class="searchContent" style="height: 20px">
			    <tr>					
					<td><!-- 姓名/社号 --><spring:message code="ess.infoApply.NAME_EMPID" /></td>
					<td>
						<div style="float:left"><input type="text" name="seach_KEY" id="seach_KEY" value="${KEY}"/></div>
						<!-- <div style="float:left"><a class="btnLook" href="" lookupGroup="person"></a></div> -->
					</td>
					<td><!-- 部门 --><spring:message code="hr.viewPersonalInfo.title.DEPTNAME" /> 
					</td>
					<td>
						<%-- <ait:deptList name="seach_DEPTNO" cpnyId="${defaultCpny}" limit="manager" id="viewCoordApplyAttendanceInfoList_seachDept" selected="${DEPTNO}"/>
						<ait:deptTreeIcon name="seach_DEPTNO" cpnyId="${defaultCpny}" limit="manager" id="viewCoordApplyAttendanceInfoList_seachDept" selected="${DEPTNO}"/> --%>
						<ait:deptTreeMulti id="seach_DEPTNO_Multi" name="seach_DEPTNO" limit="manager" selectedNm="${DEPTNO}" selected="${DEPTNO_Multi}"></ait:deptTreeMulti>
					</td>
				</tr>
				<tr>
					<td><!-- 年份 --><spring:message code="pa.payear.title.payear" /></td>
					<td>
						<ait:date yearName="seach_VAR_YEAR"  yearSelected="${VAR_YEAR}"  yearPlus="10"/>
					</td>
					
					<%--<c:if test="${LoginUser.cpnyId eq 'HTSV' or LoginUser.cpnyId eq 'HAE' or LoginUser.cpnyId eq 'SPC_DL'}">
					<td><!--基准日--><spring:message code="ess.title.JIZHUNRI" /></td>
					<td>
						<input type="text" id="seach_VAR_YEAR" name="seach_VAR_YEAR" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy.MM.dd'})" value="${VAR_YEAR}"/>
					</td>
					</c:if>
					--%>
	                <td><!--员工类型 --><spring:message code="org.title.EMP_TYPE" /></td>
					<td>
				 	    <ait:SelectSyCodeByCpnyID id="seach_EMP_TYPE_CODE" name="seach_EMP_TYPE_CODE" parentNo="13864" selected="${EMP_TYPE_CODE}" cnpyID="${LoginUser.cpnyId}" limit="all"/>
					</td>
					<td><!-- 任职状态 --><spring:message code="ess.infoApply.renzhizhuangtai" /></td>
					<td>
					 <ait:SelectSyCodeByCpnyID id="seach_EMP_OFFICE" name="seach_EMP_OFFICE" parentNo="15118" selected="${EMP_OFFICE}"  limit="all"/>
					</td>
				</tr>
			</table>
		</div>
	</form>
</div>

<div class="pageContent">
<div class="formBar">
	<ul class="toolBar">
	<li><a class="buttonActive"  id="viewCoordApplyAttendanceInfoList_Serch" onclick="tijiao();" ><span><!--查询 --><spring:message code="org.title.SELECT" /></span></a></li>
	<li><a class="buttonActive" onclick="downloadExcel('viewUseOfAnnualLeaveList','/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=206','/ess/viewDept/viewUseOfAnnualLeaveList?firstFlag=N')"><span><!--导出到Excel --><spring:message code="org.title.exportLOtImportExcel" /></span></a></li>
	 </ul>
</div>
		<table class="orderList" width="99%">
			<thead>
				<tr>
					<th>
						NO
					</th>
					<th>
						<!--部门名 --><spring:message code="ess.infoApply.DEPT_NAME" />
					</th>
					<th>
						<!--社号 --><spring:message code="org.title.EMPID" />
					</th>
					<th>
						<!--姓名 --><spring:message code="org.title.LOCAL_NAME" />
					</th>
					<th>
						<!-- 入职日期  --><spring:message code="org.title.DATE_STARTED" />
					</th>
					<th>
						<!-- 年份  --><spring:message code="pa.payear.title.payear" />
					</th>
					<th>
						<!-- 开始时间  --><spring:message code="ess.infoApply.title.startTime" />
					</th>
					<th>
						<!-- 结束时间  --><spring:message code="ess.infoApply.end_time" />
					</th>
					<th>
						<!-- 年假总数  --><spring:message code="ess.infoApply.sum_year_leave_days" />
					</th>
					<th>
						<!-- 生成年假  --><spring:message code="ar.viewVacEmpList.SHENGCHENGNIANJIA.b" />
					</th>
					<th>
						<!-- 特殊年假  --><spring:message code="ar.viewVacEmpList.TESHUNIANJIA.b" />
					</th>
					<th>
						<!-- 移年年假 --><spring:message code="ar.viewVacEmpList.YINIANNIANJIA.b" />
					</th>
					<th>
						<!-- 年假使用  --><spring:message code="ess.infoApply.nianjiashiyong" />
					</th>
					<th>
						<!-- 年假剩余  --><spring:message code="ess.infoApply.nianjiashengyu" />
					</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${useOfAnnualLeaveList}" var="item"
					varStatus="i">
					<tr>
						<td class="td_type">
							${i.count}
						</td>
						<td class="td_type">
						    ${item.DEPATNAME}
						</td>
						<td class="td_type">
						    ${item.EMPID}
						</td>
						<td class="td_type">
						    ${item.LOCAL_NAME}
						</td>
						<td class="td_type">
						    ${item.DATE_STARTED}
						</td>
						<td class="td_type">
						    ${item.VAC_ID}
						</td>
						<td class="td_type">
						    ${item.STRT_DATE}
						</td>
						<td class="td_type">
						    ${item.END_DATE}
						</td>
						<td class="td_type">
						    ${item.TOT_VAC_CNT + item.ADD_VAC + item.LAST_YEAR_VAC}
						</td>
						<td class="td_type">
						    ${item.TOT_VAC_CNT }
						</td>
						<td class="td_type">
						    ${item.ADD_VAC }
						</td>
						<td class="td_type">
						   ${item.LAST_YEAR_VAC }
						</td>
						<td class="td_type">
						   ${item.USE_VAC + item.AFFIRM_USE_VAC + item.USE_VAC_CNT}
						</td>
						<td class="td_type"> 
						   ${item.TOT_VAC_CNT + item.ADD_VAC + item.LAST_YEAR_VAC - item.USE_VAC - item.AFFIRM_USE_VAC - item.USE_VAC_CNT}
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
</div>