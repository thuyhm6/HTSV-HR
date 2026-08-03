<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<c:set var="base" value="${pageContext.request.contextPath}" />

<script type="text/javascript">
function CheckForm(form,navTabId){
	var $form=$(form);

	return true;
}
/* function doGradeStatisticsListExport(from){
  	var $from =$(from);
  	var url ="${base}/empsubject/viewGradeStatisticsListExcel";
  	window.location =url+ (url.indexOf('?') == -1 ? "?" : "&") + $from.serialize();
} */
function doGradeStatisticsListExport(from){
	var sform = document.getElementById("viewGradeStatistics");
	var eForm = document.getElementById("excelForm_jy0200");
	
	document.getElementById("jy0200Link").innerHTML = "EXCEL密码设置";
	eForm.BRANCH_EDU_3.value = sform.seach_BRANCH_EDU_3.value;
	eForm.START_DATE.value	= sform.seach_START_DATE.value;
	eForm.END_DATE.value	= sform.seach_END_DATE.value;
	eForm.SUBJT_GR_ID.value		= sform.seach_SUBJT_GR_ID.value;
	eForm.SUBJT_NM.value		= sform.SUBJT_NM.value;
	eForm.PROD_TP.value		    = sform.seach_PROD_TP.value;
	
	$("#excelDialog_jy0200").attr('href', "/sys/encryptExcel"
					+"?exportFunName=/empsubject/viewGradeStatisticsListExcel"
					+"&navTabId=jy0200"
					+"&formId=excelForm_jy0200");
	$("#excelDialog_jy0200").attr('width', "300");
	$("#excelDialog_jy0200").attr('height', "150");
	$("#excelDialog_jy0200").click();
}
function expGradeStatisticsList(a,navTabId){
  	var $this = $(a);
  	var title = $this.attr("title");
  	var $from = $("#viewGradeStatistics");
  	if(CheckForm($from,navTabId)){
	     alertMsg.confirm(title, {okCall: function(){ doGradeStatisticsListExport($from);}});
    } 
}

function navTabSearch_viewGradeStatistics(form, navTabId){
	var $form = $(form);

	var CPNY_ID = document.viewGradeStatistics.CPNY_ID.value;
	/*var areaStr="";
	if(CPNY_ID == 'TSTO'){
  
        $("input[name='isChecked']:checkbox").each(function(){ 
            if($(this).attr("checked")){
                areaStr += $(this).val()+"!";
            }
        });
	    //if(areaStr.length == 0){    
		//        alertMsg.error("请选择大区");
		//        return ;
	    //}
	   
	    areaStr=areaStr.substring(0, areaStr.lastIndexOf('!'));
    }
	*/
	if (form[DWZ.pageInfo.pageNum]){
		form[DWZ.pageInfo.pageNum].value = 1 ;
	}
	var params = $(form).serializeArray();
	if (!form[DWZ.pageInfo.pageNum]){
		params.push({name: DWZ.pageInfo.pageNum, value: 1}) ;
	}
	//params.push({name: 'areaStr', value: areaStr}) ;

	navTab.reload($form.attr('action'), {data: params, navTabId:navTabId});
	return false;
}
	
$(document).ready(function() {
	var branch0=$('#hBRANCH_EDU_3').val();
	//changePayArea(branch0);
	
	$.ajax({
		cache: false,
		url : '${base}/promoter/getListBySelect?type=BRANCH&selected='+branch0+'&name=seach_BRANCH_EDU_3&deptLevel=3',
		type : "get",
		dataType : "html",
		success : function(data) {
			$("#seach_BRANCH_EDU_3").html(data);
		}
	});
	
});	
</script>

<div class="pageHeader">

<form id="viewGradeStatistics"  name="viewGradeStatistics" onsubmit="return navTabSearch(this);" action="/empsubject/viewGradeStatisticsList" method="post" rel="pagerForm">
<div class="searchBar">
	    <input id="hBRANCH_EDU_3" name="hBRANCH_EDU_3" type="hidden" value="${BRANCH_EDU_3}" />
<table class="searchContent">
	<tr>
		<td><spring:message code="empsubject.branch"/><!-- 支社： -->
		</td>
		<td>
		    <span id="seach_BRANCH_EDU_3" name="seach_BRANCH_EDU_3"></select></span>
		</td>
		<td><!-- 开始日期 -->
			<spring:message code="public.title.startDate"/>
		</td>
		<td>
			<input id="seach_START_DATE" type="text" name="seach_START_DATE" class="date required" readonly="true" value="${START_DATE}"/>
			<a class="inputDateButton"><!-- 选择 -->
				 <spring:message code="public.title.choose"/>
			</a>
		</td>
		<td><!-- 结束日期 -->
			<spring:message code="public.title.endDate"/>
		</td>				
		<td>
			<input id="seach_END_DATE" type="text" name="seach_END_DATE" class="date required" readonly="true" value="${END_DATE}"/>
		    <a class="inputDateButton"><!-- 选择 -->
                 <spring:message code="public.title.choose"/>
			</a>
		</td>  
	</tr>
	<tr>		
  		<td><!-- 课程组id： -->
			<spring:message code="empsubject.subjectGrID"/>
		</td>
		<td>
		    <!--<ait:ComboDeptByCpnyIDTag id="seach_SUBJT_GR_NM" name="seach_SUBJT_GR_NM" parentNo="198659" selected="${SUBJT_GR_NM}" cnpyID="${defaultCpny}" limit="all"/> -->
		    <select name="seach_SUBJT_GR_ID" class="input_select_short" value="" >
                    <option value = "">请选择</option>
                    <c:forEach items="${groupList}" var="GRCDResult">
                        <option value="<c:out value='${GRCDResult.SUBJT_GR_ID}'/>" <c:if test="${SUBJT_GR_ID==GRCDResult.SUBJT_GR_ID}"> selected</c:if> > 
                          <c:out value='${GRCDResult.SUBJT_GR_ID}'/>&nbsp;|&nbsp;<c:out value='${GRCDResult.SUBJT_GR_NM}'/>
                        </option>
                    </c:forEach>
            </select>
		</td>
		<td><spring:message code="empsubject.subjectNm"/><!-- 课程名称 --></td>     
        <td id="selectTd">
        	<input type="text" name="SUBJT_NM" value="<c:out value='${SUBJT_NM}'/>">
        </td>
        <td><spring:message code="empsubject.prodTp"/><!-- 产品类型 -->
		</td>
		<td>
			<ait:prodTpTreeMulti id="seach_PROD_TP" name="seach_PROD_TP_NM"  parentNo="211424" limit="all"
			selected="${PROD_TP}"
			selectedNm="${PROD_TP_NM}" />	
		</td>
	</tr>
</table>

<div class="subBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="submit"><spring:message code="public.title.search"/><!-- 检索 --></button>
						</div>
					</div>
				</li>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="button" onclick="expGradeStatisticsList(this)" title="<spring:message code='rp.report.title.exportYN'/>">
								<%--导出Excel--%><spring:message code="ar.addempshift.title.excelexport"/>
							</button>
						</div>
					</div>						
				</li>
			</ul>
</div>
</div>
</form>
</div>

<div class="pageContent">
	
<table class="table" width="1600" layoutH="232">
	<thead>
		<tr>
			<th width="5%"><spring:message code="inct.salesman.daqu"/><!-- 大区--></th>
			<th width="8%"><spring:message code="empsubject.branch"/><!-- 支社--></th>
			<th width="5%"><spring:message code="empsubject.prod"/><!-- 产品--> </th>
			<th width="5%"><spring:message code="empsubject.subjectGrNm"/><!-- 课程组 --></th>
			<th width="7%"><spring:message code="empsubject.subjectNm"/><!-- 课程名称 --></th>
			<th width="4%"><spring:message code="empsubject.totalCnt"/><!-- 总人数--></th>
			<th width="5%"><spring:message code="empsubject.studyCnt"/><!-- 参加人员数量--></th>
			<th width="4%"><spring:message code="empsubject.rate"/><!-- 覆盖率--></th>
			<th width="5%"><spring:message code="empsubject.gradePoint"/><!-- 成绩--></th>
			<th width="5%"><spring:message code="empsubject.courseS"/><!-- 课程满意度--></th>
			<th width="5%"><spring:message code="empsubject.lecturerS"/><!-- 讲师满意度--></th>
			<th width="2%"><spring:message code="empsubject.nps"/><!-- NPS--></th>
			<th width="2%"><spring:message code="empsubject.subjtTime"/><!-- 课时--></th>
			<th width="5%"><spring:message code="empsubject.eduTime"/><!-- 培训开始日期--></th>
			<th width="5%"><spring:message code="empsubject.eduRm"/><!-- 培训地点--></th>
			<th width="5%"><spring:message code="empsubject.npsReason"/><!-- NPS推荐理由--></th>
			<th width="5%"><spring:message code="empsubject.npnNoReason"/><!-- NPS不推荐理由--> </th>
			<th width="6%"><spring:message code="empsubject.salesTalk"/><!-- Sales Talk内容--></th>
			<th width="5%"><spring:message code="empsubject.comClubInfo"/><!-- 竞争社信息--></th>
			<th width="7%"><spring:message code="empsubject.otherFeedback"/><!-- 促销员其他反馈--></th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${gradeStatistics}" var="item" varStatus="i">

			<tr target="sid" rel="SUBJT_GR_ID=${item.SUBJT_GR_ID}&SUBJT_ID=${item.SUBJT_ID}">
				<td class='td_center'>${item.PAY_AREA_CD}</td>
				<td class='td_center'>${item.ORG_NM}</td>
				<td class='td_center'>${item.PROD_TP}</td>
				<td class='td_center'>${item.SUBJT_GR_NM}</td>
				<td class='td_center'>${item.SUBJT_NM}</td>
				<td class='td_right'>${item.TOTAL_COUNT}</td>
				<td class='td_right'>${item.STUDY_COUNT}</td>
				<td class='td_center'>${item.RATE}</td>
				<td class='td_right'>${item.GRADE_POINT}</td>
				<td class='td_right' >${item.COURSE_S}</td>
				<td class='td_right'>${item.LECTURER_S}</td>
				<td class='td_right'>${item.NPS}</td>
				<td class='td_right'>${item.SUBJT_TIME}</td>
				<td class='td_center'>${item.EDU_TIME}</td>
				<td class='td_center'>${item.EDU_RM}</td>
				<td class='td_center'>${item.NPS_REASON}</td>
				<td class='td_center'>${item.NPS_NO_REASON}</td>
				<td class='td_center'>${item.SALES_TALK}</td>
				<td class='td_center'>${item.COM_CLUB_INFO}</td>
				<td class='td_center'>${item.OTHER_FEEDBACK}</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<c:set value="/empsubject/viewGradeStatisticsList" var="pageUrl"/>
<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>

<a id="excelDialog_jy0200" href="#" target="dialog" mask="true"><span
		id="jy0200Link" style="display: none"></span></a>
	<form id="excelForm_jy0200" name="excelForm_jy0200" method="post">
	    <input type="hidden" id="password" name="password" value="" />
	    <input type="hidden" id="BRANCH_EDU_3" name="BRANCH_EDU_3" value="" />
	    <input type="hidden" id="START_DATE" name="START_DATE" value="" />
	    <input type="hidden" id="END_DATE" name="END_DATE" value="" />
	    <input type="hidden" id="SUBJT_GR_ID" name="SUBJT_GR_ID" value="" />
	    <input type="hidden" id="SUBJT_NM" name="SUBJT_NM" value="" />
	    <input type="hidden" id="PROD_TP" name="PROD_TP" value="" />
	</form>
</div>
