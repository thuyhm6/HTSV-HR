<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
		function compareDate(beginDate,endDate){
			 var str11 =new Date(beginDate.replace("-",",")).getTime();
			 var str22=new Date(endDate.replace("-",",")).getTime(); 
			   if(str11>str22)
			   return false;
			   else 
			   return true;
			} 

		function check(){
			var beginTime =  document.getElementById("seach_beginTime").value;
			var endTime =  document.getElementById("seach_endTime").value;
			if(!compareDate(beginTime,endTime))
			   {
			     alert('<spring:message code="ar.attendanceView.viewNoSwipingCard.beginTimeDontendTime"/>');
				  return false;
			   }
		}

		//导出
		function expNoSwipingList(a,navTabId){
		    var $from = $("#viewNoSwipingCardList");
		    alertMsg.confirm("Do you want to export?", {
				okCall: function(){ doNoSwipingCardListExport($from);}});
		}

		/* function doPromotoGradeListExport(from){
		    var $from = $("#viewPromotoGrade"); 
		    var url ="/empsubject/viewPromotoGradeListExcel";
		    window.location =url+ (url.indexOf('?') == -1 ? "?" : "&") + $from.serialize();
		}   */
		function doNoSwipingCardListExport(from){
			var sform = document.getElementById("viewNoSwipingCardList");
			var eForm = document.getElementById("excelExportNoSwipingCard");
			
			document.getElementById("jy04000Link").innerHTML = "EXCEL密码设置";
			eForm.empIdAndName.value = sform.seach_empIdAndName.value;
			eForm.deptName.value	 = sform.seach_deptName.value;
			eForm.beginTime.value	 = sform.seach_beginTime.value;
			eForm.endTime.value	 = sform.seach_endTime.value;
			
			
			$("#excelDialog_jy04000").attr('href', "/sys/encryptExcel"
							+"?exportFunName=/empsubject/viewNoSwipingListExcel"
							+"&navTabId=ar0111"
							+"&formId=excelExportNoSwipingCard");
			$("#excelDialog_jy04000").attr('width', "300");
			$("#excelDialog_jy04000").attr('height', "150");
			$("#excelDialog_jy04000").click();
		}
</script>
<div class="pageHeader" >
     <a id="importExcelNoSwipingCard" href="#" target="dialog" mask="true"><span
		id="NoSwipingCardLink" style="display: none"></span></a> 
	<form id="viewNoSwipingCardList" onsubmit="return navTabSearch(this);" action="/ar/attendanceView/viewNoSwipingCardList" method="post" rel="pagerForm">
	<div class="searchBar">
		<table class="searchContent">
			<tr> 
				<td><!-- 工号/姓名 -->
					<spring:message code="public.title.empIdAndName"/>
				</td>
				<td>			
			        <input name="seach_empIdAndName" id="seach_empIdAndName" type="text" value="${empIdAndName}"/>
				</td>
				
				<td><!-- 部门 -->
					<spring:message code="ar.attendanceView.viewNoSwipingCard.deptName"/>
				</td>
				<td>		
					<ait:deptList name="seach_DEPTNO" limit="ar"  id="ar0111_seachDept"/>
					<ait:deptTreeIcon name="seach_DEPTNO" limit="ar" id="ar0111_seachDept" selected="${DEPTNO}"/>
				</td>
						<td>人员类型组 </td>
						<td>
							<input type="hidden" id="ar0111_limit" name="limit" value="ar">
							<input type="hidden" id="ar0111_seach_CPNY" name="seach_CPNY" value="${LoginUser.cpnyId}">
							<ait:SelectEmpTypeCode  id="ar0111_seach_JobTypeGroupNo"  name="seach_JobTypeGroupNo" selected="${JobTypeGroupNo}" limit="ar" type="group"
								onChangeName="ajaxEmpTypeForGroupToList(-1,ar0111_seach_JobTypeGroupNo,ar0111_seach_EmpTypeCodeNo,ar0111_seach_CPNY,ar0111_limit)"/>
						</td>
						<td>人员类型 </td>
						<td>
		 					<ait:SelectEmpTypeCode id="ar0111_seach_EmpTypeCodeNo" name="seach_EmpTypeCodeNo" selected="${EmpTypeCodeNo}" limit="ar"/>
						</td>	
		</tr>
		<tr>
				<td><!-- 开始日期 -->
					<spring:message code="ar.attendanceView.viewNoSwipingCard.beginTime"/>
				</td>
				<td>			
			        			        <input type="text" id="seach_beginTime" name="seach_beginTime" value="${beginTime }" class="date required"
										yearstart="-20" yearend="20" readonly="true" />
										<a class="inputDateButton"><spring:message code="public.title.choose"/><!-- 选择 --></a>
				</td>
				
				<td><!-- 结束日期 -->
					<spring:message code="ar.attendanceView.viewNoSwipingCard.endTime"/>
				</td>
				<td>			
				<input type="text" id="seach_endTime" name="seach_endTime" value="${endTime }" class="date required"
										yearstart="-20" yearend="20" readonly="true" />
										<a class="inputDateButton"><spring:message code="public.title.choose"/><!-- 选择 --></a>
					<input name="seach_status" value="" type="hidden">
					<input name="seach_attendanceDistinct" value="" type="hidden">
				</td>
		   <td>在职状态</td>
						<td>
		 <ait:SelectSyCodeByCpnyID id="seach_EmpOffice" name="seach_EmpOffice" parentNo="15118" selected="${EmpOffice}" cnpyID="${LoginUser.cpnyId}" limit="all"/>
						</td>
		</tr>
		</table>
		<div class="subBar">
		 	<ul><li><div class="buttonActive"><div class="buttonContent"><button type="submit" onclick="check();">
		 		&nbsp;<spring:message code="public.title.search"/><!--检索-->&nbsp;</button></div></div></li></ul>
		</div> 
	</div>
	</form>
</div>

<div class="pageContent">
	<table class="table" width="100%" layoutH="173">
		<thead>
			<tr>
				<!--	日期		-->
				<th style="text-align: center" width="11%"><B><spring:message code="ar.attendanceView.viewNoSwipingCard.dateTime"/></B></th>
				<!--  工号/姓名 -->
				<th style="text-align: center" width="11%"><B><spring:message code="public.title.empIdAndName"/></B></th>
				<!--	部门		-->
				<th style="text-align: center" width="11%"><B><spring:message code="ar.attendanceView.viewNoSwipingCard.deptName"/></B></th>
				<!--    班次	-->
				<th style="text-align: center" width="11%"><B><spring:message code="ar.attendanceView.viewNoSwipingCard.classes"/></B></th>
				<!--	状态		-->
				<th style="text-align: center" width="11%"><B><spring:message code="ar.attendanceView.viewNoSwipingCard.status"/></B></th>
				<!--	上班打卡时间		-->
				<th style="text-align: center" width="11%"><B><spring:message code="ar.attendanceView.viewNoSwipingCard.clockTime"/></B></th>
				<!--	下班打卡时间		-->
				<th style="text-align: center" width="11%"><B><spring:message code="ar.attendanceView.viewNoSwipingCard.punchOutTime"/></B></th>
				<!--	长度		-->
				<th style="text-align: center" width="11%"><B><spring:message code="ar.attendanceView.viewNoSwipingCard.length"/></B></th>
			</tr>
			</thead>
			<tbody>
			<c:forEach items="${noSwipingCardList}" var="noSwiping" varStatus="i">
				<tr>
					<td style="text-align: center" width="11%">${noSwiping.ARDATESTR }</td>
					<td style="text-align: center" width="11%">${noSwiping.EMPIDANDNAME}</td>
					<td style="text-align: center" width="11%">${noSwiping.DEPTNAME}</td>
					<td style="text-align: center" width="11%">${noSwiping.SHIFTNAME}</td>
					<td style="text-align: center" width="11%">${noSwiping.ITEMNAME}</td>
					<td style="text-align: center" width="11%">${noSwiping.FROMTIME}</td>
					<td style="text-align: center" width="11%">${noSwiping.TOTIME}</td>
					<td style="text-align: center" width="11%">${noSwiping.LENGTHQ}</td>
				</tr>
			</c:forEach>
			</tbody>
	</table>
	<c:set value="/ar/attendanceView/viewNoSwipingCardList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
	<a id="excelDialog_jy04000" href="#" target="dialog" mask="true"><span
		id="jy04000Link" style="display: none"></span></a>
	<form id="excelExportNoSwipingCard" name="excelExportNoSwipingCard" method="post">
	<input type="hidden" id="password" 			name="password" 		value="" />
	<input type="hidden" id="empIdAndName" 				name="empIdAndName" 			value="" />
	<input type="hidden" id="deptName" 			name="deptName" 			value="" />
	<input type="hidden" id="beginTime" 			name="beginTime" 			value="" />
	<input type="hidden" id="endTime" 			name="endTime" 			value="" />
    </form>
</div>