<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
//导出
function exportPromotoGradeImportInfo(a){
    var $from = $("#viewPromotoGradeExcelImportResult");
    alertMsg.confirm("Do you want to export?", {
		okCall: function(){ doSalesmanEvalImportInfoExport($from);}});
  } 
 function doSalesmanEvalImportInfoExport(from){
    var $from = $("#viewPromotoGradeExcelImportResult"); 
    var url ="/empsubject/viewPromotoGradeResultListExcel";
    window.location =url+ (url.indexOf('?') == -1 ? "?" : "&") + $from.serialize();
}  
/* function doSalesmanEvalImportInfoExport(from){
	var sform = document.getElementById("viewPromotoGradeExcelImportResult");
	var eForm = document.getElementById("excelForm_jy0700");
	
	document.getElementById("jy0700Link").innerHTML = "EXCEL密码设置";
	eForm.RESULT_FLAG.value		    = sform.seach_RESULT_FLAG.value;
	
	$("#excelDialog_jy0700").attr('href', "/sys/encryptExcel"
					+"?exportFunName=/empsubject/viewPromotoGradeResultListExcel"
					+"&navTabId=jy0700"
					+"&formId=excelForm_jy0700");
	$("#excelDialog_jy0700").attr('width', "300");
	$("#excelDialog_jy0700").attr('height', "150");
	$("#excelDialog_jy0700").click();
} */
//提交导入数据
function doPromotoGradeDataImport()
{
    var params   = $("#viewPromotoGradeExcelImportResult").serialize();
        alertMsg.confirm("<spring:message code='empsubject.alert.uploadYn'/>",
			{
				okCall : function() {
						$.ajax( {
						type : 'post',
						cache : false,
						url : "/empsubject/createPromotoGradeResult?" + params,
						success : function(result) {
							if (result == 1){
								//alert("教育实绩数据导入成功！");
								alert("<spring:message code='empsubject.alert.uploadSuccess'/>");
							}else{
								//alert("教育实绩数据导入失败！");
								alertMsg.error("<spring:message code='empsubject.alert.uploadFail'/>");
							}
							//页面重载
							dwzSearch($("#viewPromotoGradeExcelImportResult"),'dialog');
						}
					});
				}
			});
	
}
</script>
<div class="pageHeader">
	<form id="viewPromotoGradeExcelImportResult" name="viewPromotoGradeExcelImportResult"
			action="/empsubject/viewPromotoGradeResultList" 
			onsubmit="return dwzSearch(this,'dialog')"
			method="post" 
			rel="pagerForm" >
	<input type="hidden" name="pageNum" value="${pageNum}" />
	<input type="hidden" name="numPerPage" value="${numPerPage}" />
	<div class="searchBar" >
		<table class="searchContent">
			<tr>
				<th>
					<spring:message code="inct.salesman.excel.totalCnt"/><!-- 总行数-->：
				</th>
				<td>
					${totalCnt}
					<iframe id='callbackframe' name='callbackframe' src='about:blank' style='display:none'></iframe>
				</td>
				<th>
					<spring:message code="inct.salesman.excel.errCnt"/><!-- 出错行数-->：
				</th>
				<td>							
					${errCnt}
				</td>
				<th>
					<spring:message code="empsubject.errorYn"/><!-- 出错与否-->
				</th>
				<td>							
					<select name="seach_RESULT_FLAG" id="seach_RESULT_FLAG">
							<option value="" <c:if test="${RESULT_FLAG eq '' }">selected</c:if>><spring:message code="empsubject.all"/><!-- 全部--></option>
							<option value="E" <c:if test="${RESULT_FLAG eq 'E' }">selected</c:if>><spring:message code="empsubject.yes"/><!-- 是--></option>
							<option value="N" <c:if test="${RESULT_FLAG eq 'N' }">selected</c:if>><spring:message code="empsubject.no"/><!-- 否--></option>
					</select>
				</td>
			</tr>
		</table>
		<div class="subBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="submit" id="btnSearch_jy0700_1" name="btnSearch_jy0700_1">
								<spring:message code="public.title.search"/><!-- 检索 -->
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
	
	<div class="formBar">
		<ul>			
			
			<li><div class="buttonActive"><div class="buttonContent">
				<button type="button"  onclick="exportPromotoGradeImportInfo(this)">
				<spring:message code="inct.salesman.downloadToExcel" /><!--excel导出--></button>
			</div></div></li>
			<li><div class="buttonActive"><div class="buttonContent">
				<button type="submit"  onclick="doPromotoGradeDataImport()">
				<spring:message code="public.title.submit"/><!--提交--></button>
			</div></div></li>
			<li><div class="button"><div class="buttonContent">
				<button type="button" class="close">
				<spring:message code="public.title.cancle"/><!--取消--></button>
			</div></div></li>
		</ul>
	</div>	
		
	<table class="table" width="1800" layoutH="150">
		<thead>
			<tr>
				<th width="2%">Line<!--excel行号--></th>
				<th width="3%"><spring:message code="ar.viewcycleparameter.title.gongsi"/><!-- 公司 --> </th>
				<th width="3%"><spring:message code="inct.salesman.daqu"/><!-- 大区--></th>
				<th width="3%"><spring:message code="empsubject.branch"/><!-- 支社--></th>
				<th width="4%"><spring:message code="empsubject.empno"/><!-- 社号--></th>
				<th width="3%"><spring:message code="empsubject.subjectGrID"/><!-- 课程组ID --> </th>
				<th width="5%"><spring:message code="empsubject.subjectID"/><!-- 课程ID --></th>
				<th width="2%"><spring:message code="empsubject.gradePoint"/><!-- 成绩--></th>
				<th width="5%"><spring:message code="empsubject.courseS"/><!-- 课程满意度--></th>
				<th width="5%"><spring:message code="empsubject.lecturerS"/><!-- 讲师满意度--></th>
				<th width="2%"><spring:message code="empsubject.nps"/><!-- NPS--></th>
				<th width="4%"><spring:message code="empsubject.tcrNm"/><!-- 讲师姓名 --></th>
				<th width="5%"><spring:message code="empsubject.eduTime"/><!-- 培训开始日期--></th>
				<th width="3%"><spring:message code="empsubject.eduRm"/><!-- 培训地点--></th>
				<th width="2%"><spring:message code="empsubject.useYn"/><!-- 状态 --></th>
				<th width="2%"><spring:message code="empsubject.subjtTime"/><!-- 课时--></th>
				<th width="6%"><spring:message code="empsubject.npsReason"/><!-- NPS推荐理由--></th>
				<th width="6%"><spring:message code="empsubject.npnNoReason"/><!-- NPS不推荐理由--></th>
				<th width="5%"><spring:message code="empsubject.salesTalk"/><!-- Sales Talk内容--></th>
				<th width="5%"><spring:message code="empsubject.comClubInfo"/><!-- 竞争社信息--></th>
				<th width="6%"><spring:message code="empsubject.otherFeedback"/><!-- 促销员其他反馈--></th>
				<th width="10%"><spring:message code="inct.salesman.validateMessage"/><!--验证结果--></th>
				<th width="4%"><spring:message code="inct.salesman.updateBy"/><!--更新人--></th>
				<th width="5%"><spring:message code="inct.salesman.updateTime"/><!--更新时间--></th>		
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${MDATA}" var="item" varStatus="i">
				<tr <c:if test="${item.RESULT_FLAG == 'E'}">font style="COLOR: #FF0000"</c:if>> 
					<td class='td_right'>${item.LINE_ID}</td>
					<td class='td_center'>${item.SUBSD_CD}</td>
					<td class='td_right'>${item.PAY_AREA_CD}</td>
					<td class='td_right'>${item.BRANCH_CD}</td>
					<td class='td_center'>${item.EMPNO}</td>
					<td class='td_center'>${item.SUBJT_GR}</td>
					<td class='td_center'>${item.SUBJT_ID}</td>
					<td class='td_right'>${item.GRADE_POINT}</td>
					<td class='td_right'>${item.COURSE_S}</td>
					<td class='td_right'>${item.LECTURER_S}</td>
					<td class='td_right'>${item.NPS}</td>
					<td class='td_center'>${item.TCR_NM}</td>
					<td class='td_center'>${item.EDU_TIME}</td>
					<td class='td_center'>${item.EDU_RM}</td>
					<td class='td_center'>${item.USE_YN}</td>	
					<td class='td_right'>${item.SUBJT_TIME}</td>
					<td class='td_center'>${item.NPS_REASON}</td>
					<td class='td_center'>${item.NPS_NO_REASON}</td>
					<td class='td_center'>${item.SALES_TALK}</td>
					<td class='td_center'>${item.COM_CLUB_INFO}</td>
					<td class='td_center'>${item.OTHER_FEEDBACK}</td>	
					<td>${item.ERROR_INFO}</td>
					<td class='td_center'>${item.UPDT_USER}</td>
					<td class='td_center'>${item.UPDT_DTIME}</td>	
				</tr>			
			</c:forEach>			
		</tbody>
	</table>
		
	<form id="pagerForm" method="post" action="/empsubject/viewPromotoGradeResultList?navTabId=${request_navTabId}">
	<div class="panelBar">
		<div class="pages">
			<span><spring:message code="public.title.view"/><!--显示--></span>
				<select class="combox" name="numPerPage" onchange="dialogPageBreak({targetType:'dialog', numPerPage:this.value})">
					<option value="10"  <c:if test="${numPerPage == 10 }" >selected</c:if> >10</option>
					<option value="20"  <c:if test="${numPerPage == 20 }" >selected</c:if> >20</option>
					<option value="30"  <c:if test="${numPerPage == 30 }" >selected</c:if> >30</option>
				</select>
			<span><spring:message code="public.title.tiao"/><!--条-->，<spring:message code="public.title.gong"/><!--共-->${totalCount}<spring:message code="public.title.tiao"/><!--条--></span>
		</div>
		<div class="pagination" targetType="dialog" totalCount="${totalCount}" numPerPage="${numPerPage}" currentPage="${pageNum}"></div>
	</div>
	</form>	
</div>