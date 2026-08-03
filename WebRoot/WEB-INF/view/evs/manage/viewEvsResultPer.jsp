<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
$(document).ready(function(){
	$("#seach_KEY",navTab.getCurrentPanel()).keydown(function(e) {
        if ( e.keyCode == 13) {
       	 	var name=encodeURI(encodeURI($('#seach_KEY',navTab.getCurrentPanel()).val()));
       		$('.btnLook',navTab.getCurrentPanel()).attr('href','/hrm/empinfo/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=viewEvsResultPer&seach_KEY='+name);
       		$('.btnLook',navTab.getCurrentPanel()).click();
        }
    });
	$(".btnLook",navTab.getCurrentPanel()).click(function(e) {
      	 var name=encodeURI(encodeURI($('#seach_KEY',navTab.getCurrentPanel()).val()));
      	$('.btnLook',navTab.getCurrentPanel()).attr('href','/hrm/empinfo/viewEmpInfoListTanchu?pageNum=1&firstFlag=N&searchChange=viewEvsResultPer&seach_KEY='+name);
    });
});
</script>
<div class="pageHeader">
	<form id="viewEvsResultForm" onsubmit="return navTabSearch(this);" action="/evs/manage/viewEvsResultPer" method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td><spring:message code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME"/><!--社号/姓名--></td>
					<td>
						<div style="float:left"><input type="text" name="seach_KEY" id="seach_KEY" value="${KEY}"/></div>
						<div style="float:left"><a class="btnLook" href="" lookupGroup="person"></a></div>
					</td>
					<td colspan="3">
						<c:if test="${not empty personInfo}">
							<span style="margin-left: 50px;" >${personInfo.LOCAL_NAME }&nbsp/&nbsp${personInfo.EMPID }&nbsp/&nbsp${personInfo.POST_GRADE_NO_NAME}&nbsp/&nbsp${personInfo.EMP_OFFICE_NAME }</span>
						</c:if>
					</td>
				</tr>
			</table>
		</div>
	</form>
</div>
<div class="pageContent">
	<div style="font:bold 14px/20px arial,sans-serif;float:left;height:20px;line-height:20px;"><spring:message code="evs.viewEvsResultEmp.PINGJIAJIEGUOLVLI.a"/><!--评价结果履历--></div>
	<c:if test="${LoginUser.cpnyId eq 'TSTO'}">
		<table class="table" layoutH="100" width="100%">
			<thead>
				<tr>
					<th width="8%"><spring:message code="hrm.empinfo.Evaluation_year"/><!--评价年度--></th>
					<th width="7%">1<spring:message code="display.mutual.month"/><!--月--></th>
					<th width="7%">2<spring:message code="display.mutual.month"/><!--月--></th>
					<th width="7%">3<spring:message code="display.mutual.month"/><!--月--></th>
					<th width="7%">4<spring:message code="display.mutual.month"/><!--月--></th>
					<th width="7%">5<spring:message code="display.mutual.month"/><!--月--></th>
					<th width="7%">6<spring:message code="display.mutual.month"/><!--月--></th>
					<th width="7%">7<spring:message code="display.mutual.month"/><!--月--></th>
					<th width="7%">8<spring:message code="display.mutual.month"/><!--月--></th>
					<th width="7%">9<spring:message code="display.mutual.month"/><!--月--></th>
					<th width="7%">10<spring:message code="display.mutual.month"/><!--月--></th>
					<th width="7%">11<spring:message code="display.mutual.month"/><!--月--></th>
					<th width="7%">12<spring:message code="display.mutual.month"/><!--月--></th>
					<th width="8%"><spring:message code="hr.viewEvaluate.title.EV_ABIL"/><!--能力--></th>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${objectList}" var="item" varStatus="i">
				<tr>
					<td style="text-align:center">${item.EVS_YEAR}</td>
					<td style="text-align:center">${item.EVS_MONTH1}</td>
					<td style="text-align:center">${item.EVS_MONTH2 }</td>
					<td style="text-align:center">${item.EVS_MONTH3 }</td>
					<td style="text-align:center">${item.EVS_MONTH4 }</td>
					<td style="text-align:center">${item.EVS_MONTH5 }</td>
					<td style="text-align:center">${item.EVS_MONTH6 }</td>
					<td style="text-align:center">${item.EVS_MONTH7 }</td>
					<td style="text-align:center">${item.EVS_MONTH8 }</td>
					<td style="text-align:center">${item.EVS_MONTH9}</td>
					<td style="text-align:center">${item.EVS_MONTH10}</td>
					<td style="text-align:center">${item.EVS_MONTH11}</td>
					<td style="text-align:center">${item.EVS_MONTH12}</td>
					<td style="text-align:center">${item.EVS_MONTH13}</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	</c:if>
	<c:if test="${LoginUser.cpnyId eq 'SST'}">
		<table class="table" layoutH="100" width="100%">
			<thead>
				<tr>
					<th width="25%"><spring:message code="inct.salesman.year"/><!--年--></th>
					<th width="25%"><spring:message code="evs.viewEvsResultEmp.KAOHELEIXING.a"/><!--考核类型--></th>
					<th width="25%"><spring:message code="evs.viewEvsResultEmp.KAOHESHIDUAN.a"/><!--考核时段--> </th>
					<th width="25%"><spring:message code="hr.viewCompetence.title.LANGUAGE_LEVEL_NAME"/><!--等级--></th>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${objectList}" var="item" varStatus="i">
				<tr>
					<td style="text-align:center">${item.EVS_YEAR}</td>
					<td style="text-align:center">${item.EVS_TYPE_NAME}</td>
					<td style="text-align:center">${item.EVS_CYCLE_NAME }</td>
					<td style="text-align:center">${item.FINAL_GRADE }</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	</c:if>
</div>