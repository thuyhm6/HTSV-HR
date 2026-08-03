<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.easydrag.js"></script>
<script>
$(document).ready(function(){
      $("#viewSearchApplyOtInfoList_Serch",navTab.getCurrentPanel()).click(function(){
			$("#viewSearchApplyOtInfoList",navTab.getCurrentPanel()).submit();
	   });
	  //搜索
     $("#seach_KEY",navTab.getCurrentPanel()).keydown(function(e) {
        if ( e.keyCode == 13) {
       	 	var name=encodeURI(encodeURI($('#seach_KEY',navTab.getCurrentPanel()).val()));
       	    var FROM_DATE=encodeURI(encodeURI($('#seach_FROM_DATE',navTab.getCurrentPanel()).val()));
      	    var TO_DATE=encodeURI(encodeURI($('#seach_TO_DATE',navTab.getCurrentPanel()).val()));
       		$('.btnLook',navTab.getCurrentPanel()).attr('href','/ar/attendanceMintenance/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=viewSearchApplyOtInfoList&seach_KEY='+name+'&FROM_DATE='+FROM_DATE+'&TO_DATE='+TO_DATE);
       		$('.btnLook',navTab.getCurrentPanel()).click();
        }
     });
	 $(".btnLook",navTab.getCurrentPanel()).click(function(e) {
      	 var name=encodeURI(encodeURI($('#seach_KEY',navTab.getCurrentPanel()).val()));
      	 var FROM_DATE=encodeURI(encodeURI($('#seach_FROM_DATE',navTab.getCurrentPanel()).val()));
      	 var TO_DATE=encodeURI(encodeURI($('#seach_TO_DATE',navTab.getCurrentPanel()).val()));
      	$('.btnLook',navTab.getCurrentPanel()).attr('href','/ar/attendanceMintenance/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=viewSearchApplyOtInfoList&seach_KEY='+name+'&FROM_DATE='+FROM_DATE+'&TO_DATE='+TO_DATE);
     });
	$(".orderList",navTab.getCurrentPanel()).dataTable({"bPaginate": false,    //关闭分页
		   // "bAutoWidth":false,//表格宽度不自动变化
		    "bProcessing":true,
			"bLengthChange": false,  //关闭按多少条记录显示下拉框
			"bFilter": true,   //开启快速过滤功能（过滤整个表，可以指定那些列过滤，或者不过滤），本功能默认是开启的，
			"bSort": true,   //关闭排序功能
			"bInfo": false,   //不显示datatables的信息（底部的页数，条目数信息）
			"bScrollInfinite":true,
			"scrollY": $(document.body).height() - 240,
            "scrollX": true,
            "orderClasses": false,
            "oLanguage": {
                "sProcessing": "正在加载中......",
                "sZeroRecords": "查询不到相关数据！",
                "sEmptyTable": "表中无数据存在！",
                "sSearch": "快速筛选"
            } //多语言配置
		});
});
function selectAdjust(){
   if(document.getElementById("seach_ADJSTYN").checked){
   $('#ADJSTYN').val(1);
   }else{
    $('#ADJSTYN').val(0);
   }
}

function searchPop_ess1234(flag) {
	var name = encodeURI(encodeURI($("#seach_KEY", navTab.getCurrentPanel()).val()));
	$("#searchPop_ess1234", navTab.getCurrentPanel())
			.attr(
					'href',
					'/pa/workManagement/viewEmpForPopList?pageNum=1&numPerPage=10&limit=ar&seach_KEY='
							+ name);
	if (flag == 'onkeyup')
		$("#searchPop_ess1234", navTab.getCurrentPanel()).click();
}
</script>
<div class="panel"><h1>考勤搜索页面</h1>
</div>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/ar/attendanceMintenance/viewMyhomeCarInfoList?firstFlag=N" rel="pagerForm" method="post"
		id="viewSearchApplyOtInfoList" name="viewSearchApplyOtInfoList">
		<div class="searchBar">
			<table class="searchContent" style="height: 20px">
			    <tr>					
					<td width="7%">社号/姓名</td>
					<td width="23%">
						<div style="float:left"><input type="text" name="seach_KEY" id="seach_KEY" value="${KEY}"/></div>
						<div style="float:left"><a class="btnLook" href="" lookupGroup="person"></a></div>
					</td>
					<td width="50%" colspan="5">
						<c:if test="${not empty personInfo}">
						<span style="margin-left: 50px;" >${personInfo.LOCAL_NAME }&nbsp/&nbsp${personInfo.EMPID }&nbsp/&nbsp${personInfo.POST_GRADE_NO_NAME}&nbsp/&nbsp${personInfo.EMP_OFFICE_NAME }</span>
						</c:if>
					</td>
					<td width="20%"></td>
				</tr>
				<tr>
				    <td>
						<spring:message code="ess.workgroup.title.duration" text="期间"/>
					</td>
					<td>
					     <input type="text" name="seach_FROM_DATE" id="seach_FROM_DATE"  class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="${FROM_DATE}"/>
					</td>
					<td>~</td>
					<td>
					    <input type="text" name="seach_TO_DATE" id="seach_TO_DATE"  class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="${TO_DATE}"/>
					</td>
					</td>
					<td >班组 </td>
					<td >
						<ait:SelectSyCodeByCpnyID id="seach_GROUP_NO" name="seach_GROUP_NO" parentNo="400223" selected="${GROUP_NO}" cnpyID="${LoginUser.cpnyId}" limit="all"/> 
					</td>
				</tr>
				<tr>
					<td>
					    审批状态 
					</td>
					<td>
					    <ait:selectCodeMulti id="seach_AFFIRM_FLAG" name="seach_AFFIRM_FLAG_NAME" parentNo="14014304" selected="${AFFIRM_FLAG}"  selectedNm="${AFFIRM_FLAG_NAME}"/>
					</td>
					</td>
				    <td>加班时间(>=)</td>	 
					<td>
					   	<input  title="加班时间(>=)" type="text" id="seach_length" name="seach_length"  value="${length}" />
					</td>
				</tr>
				<tr>
					<td width="10%">员工类型</td>
						<td width="20%">
				 	<ait:SelectSyCodeByCpnyID id="seach_EMP_TYPE_CODE" name="seach_EMP_TYPE_CODE" parentNo="13864" selected="${EMP_TYPE_CODE}" cnpyID="${LoginUser.cpnyId}" limit="all"/>
						</td>
					<td width="10%">任职状态</td>
					<td width="20%">
				 	<ait:SelectSyCodeByCpnyID id="seach_EmpOffice" name="seach_EmpOffice" parentNo="15118" selected="${EmpOffice}" cnpyID="${LoginUser.cpnyId}" limit="all"/>
					</td>
					<td><spring:message
						code="hr.viewPersonalInfo.title.DEPTNAME" /> 
					</td>
					<td>
						<ait:deptList name="seach_DEPTNO" cpnyId="${defaultCpny}" limit="ar" id="viewSearchApplyOtInfoList_seachDept" selected="${DEPTNO}"/>
						<ait:deptTreeIcon name="seach_DEPTNO" cpnyId="${defaultCpny}" limit="ar" id="viewSearchApplyOtInfoList_seachDept" selected="${DEPTNO}"/>
					</td>
				   <td width="30%">     
					含调休：
					<c:if test="${ADJSTYN eq '1'}">
					    <input type="checkbox"  id="seach_ADJSTYN"  onclick="selectAdjust()" checked="checked" value="1" />
					</c:if>
					<c:if test="${ADJSTYN eq '0'}">
					    <input type="checkbox"  id="seach_ADJSTYN"  onclick="selectAdjust()"  value="" />
					</c:if>
					 <input type="hidden"  id="ADJSTYN" name="ADJSTYN"  value="${ADJSTYN}" />
						
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
	<li><a class="buttonActive" id="viewSearchApplyOtInfoList_Serch" href="#"><span>查询</span></a></li>
	<li><a class="buttonActive" onclick="downloadExcel('viewCoordApplyOtInfoList','/disc/autoExcel/exportLOtImportExcel?SQL_SEQMEAN=132&CPNY=${LoginUser.cpnyId}','/ar/attendanceMintenance/viewSearchApplyOtInfoList?firstFlag=N')"><span>导出到Excel</span></a></li>
	 </ul>
</div>
		<table class="orderList" width="100%">   
			<thead>
				<tr><th><!--NO-->
						NO
					</th>
					<th>
				    	姓名
				    </th>
					<th>
						社号
					</th>
					<th>
						部门名
					</th>
					<th>
						职级
					</th>
					<th><!--申请日期-->
						日期
					</th>
					<th><!--星期-->
						星期
					</th>
					<th><!--考勤-->
						考勤
					</th>
					<th><!--时间-->
						时间
					</th>
					<th><!--班次-->
						班组
					</th>
					<th><!--工作时间-->
						工作时间
					</th>
					<th><!--工作时间-->
						进门
					</th>
					<th><!--工作时间-->
						出门
					</th>
					<th><!--申请时长-->
						开始时间
					</th>
					<th><!--申请时长-->
						结束时间
					</th>
					<th><!--时长-->
						加班时间
					</th>
					<th><!--原因-->
						原因
					</th>
					<th><!--原因-->
						其他原因
					</th>
					<th><!--决裁情况-->
						审批状态
					</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${otCoordList}" var="CoordOtApply" varStatus="i">	
					<tr target="sid" rel="">
					    <td style="text-align: center">${i.count}</td>
					    <td style="text-align: center">
					    <a href="/ess/viewDept/viewApplyDeptPersonalInfo?EMPID=${CoordOtApply.EMPID}&LOCAL_NAME= ${CoordOtApply.LOCAL_NAME}" target="dialog" style="color: blue;" title="考勤个人信息"   [ mask=true ] width="1000" height="300"> 
					    ${CoordOtApply.LOCAL_NAME}
					    </a>
					    </td>
						<td style="text-align: center">
						<a href="/ess/viewDept/viewApplyDeptPersonalInfo?EMPID=${CoordOtApply.EMPID}&LOCAL_NAME= ${CoordOtApply.LOCAL_NAME}" target="dialog" style="color: blue;" title="考勤个人信息"   [ mask=true ] width="1000" height="300">
						${CoordOtApply.EMPID}
						</a>
						</td>
						<td style="text-align: center">${CoordOtApply.DEPARTMENT}</td>
						<td style="text-align: center">${CoordOtApply.POST_GRADE_NAME}</td>
						<td style="text-align: center">${CoordOtApply.AR_DATE_STR}</td>
						<td style="text-align: center">${CoordOtApply.IWEEK}</td>
						<td style="text-align: center">${CoordOtApply.KAOQINITEM}</td>
						<td style="text-align: center">${CoordOtApply.AR_LENGTH}</td>
						<td style="text-align: center">${CoordOtApply.GROUPNAME}</td>
						<td style="text-align: center">${CoordOtApply.FROM_TIME_FIRST}-${CoordOtApply.TO_TIME_FIRST}</td>
						<td style="text-align: center">${CoordOtApply.INDOOR_DATE}</td>
						<td style="text-align: center">${CoordOtApply.OUTDOOR_DATE}</td>
						<td style="text-align: center">${CoordOtApply.FROM_TIME}</td>
						<td style="text-align: center">${CoordOtApply.TO_TIME}</td>
						<td style="text-align: center">
						  ${CoordOtApply.APPLY_LENGTH}
						    <c:if test="${CoordOtApply.ITEM_NO == '141452'}">
					               <font color="red"> (√)</font>
					         </c:if>
						</td>
						<td style="text-align: center">${CoordOtApply.REASONNAME}</td>
						<td style="text-align: center">${CoordOtApply.REASON_OTHER}</td>
						<td style="text-align: center">${CoordOtApply.AFFRIMNAME}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	<div style="visibility: hidden">
    <c:set value="/ess/infoApplyAttendance/viewApplyAttendanceInfoList?firstFlag=N" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination2.jsp"%>
	</div>
</div>