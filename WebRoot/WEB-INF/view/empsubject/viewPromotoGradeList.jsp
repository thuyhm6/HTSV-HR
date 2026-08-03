<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<c:set var="base" value="${pageContext.request.contextPath}" />

<script type="text/javascript">
function CheckForm(form,navTabId){
	var $form=$(form);

	return true;
}
//导入数据
function importExcelPromotoGradeData(){
	$("#importExcelDialog_jy0400").attr('href','/pa/excelImport/importExcelData?importFunName=/importExcelPromotoGradeData');
	$("#importExcelDialog_jy0400").attr('height', "200");
	$("#importExcelDialog_jy0400").attr('width', "400");
	$("#importExcelDialog_jy0400").click();
}

//导出
function expPromotoGradeList(a,navTabId){
    var $from = $("#viewPromotoGrade");
    alertMsg.confirm("Do you want to export?", {
		okCall: function(){ doPromotoGradeListExport($from);}});
}

/* function doPromotoGradeListExport(from){
    var $from = $("#viewPromotoGrade"); 
    var url ="/empsubject/viewPromotoGradeListExcel";
    window.location =url+ (url.indexOf('?') == -1 ? "?" : "&") + $from.serialize();
}   */
function doPromotoGradeListExport(from){
	var sform = document.getElementById("viewPromotoGrade");
	var eForm = document.getElementById("excelForm_jy0400");
	
	document.getElementById("jy0400Link").innerHTML = "EXCEL密码设置";
	eForm.BRANCH_EDU_1.value = sform.seach_BRANCH_EDU_1.value;
	eForm.START_DATE.value	 = sform.seach_START_DATE.value;
	eForm.END_DATE.value	 = sform.seach_END_DATE.value;
	eForm.SUBJT_GR_ID.value	 = sform.seach_SUBJT_GR_ID.value;
	eForm.SUBJT_NM.value	 = sform.SUBJT_NM.value;
	eForm.EMPNO.value		 = sform.seach_EMPID.value;
	eForm.USE_YN.value		 = sform.USE_YN.value;
	
	$("#excelDialog_jy0400").attr('href', "/sys/encryptExcel"
					+"?exportFunName=/empsubject/viewPromotoGradeListExcel"
					+"&navTabId=jy0400"
					+"&formId=excelForm_jy0400");
	$("#excelDialog_jy0400").attr('width', "300");
	$("#excelDialog_jy0400").attr('height', "150");
	$("#excelDialog_jy0400").click();
}
function f_delete(callback) {

	var checked=false;
	var ids= document.getElementsByName("promotoGradeCKB");
	for(var i=0;i<ids.length;i++){
		if(ids[i].checked){
			checked=true;
		}
	}
	if(!checked){
		//请选择人员再进行删除操作!
		alertMsg.error("<spring:message code='ar.alert.message.viewdynamicgroup.chooseperson'/>");
		return;
	}

	var DEL_USER = $("#DEL_USER").val();
		
	//json传值
	var jsonData = '[';

	$.each($("input[name='promotoGradeCKB']"),
	function(i, obj) {
		if (obj.checked) {
			
			if (jsonData.length > 1) {

				jsonData += ',{';
			} else {
				jsonData += '{';
			}

			
			jsonData += ' "DEL_USER": "' + DEL_USER + '",';	
			jsonData += ' "EVAL_SEQ": "' + obj.value + '"';
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
	if (confirm ("<spring:message code='ar.alert.message.viewArAnnualStandard.consubmit'/>")){	
		$.ajax({
			type: 'POST',
			url: '/empsubject/deletePromotoGrade',
			data: [{ name: 'jsonData', value: jsonData }],
			dataType:"json",
			cache: false,
			success: function(result){
						if (result.jieguo == "Y"){
							alertMsg.info("<spring:message code='empsubject.alert.delSuccess'/>");
							//alert('删除教育实绩信息成功！');
							//页面重载
							navTabSearch(document.viewPromotoGrade);
						}else{
							alertMsg.error("<spring:message code='empsubject.alert.delFail'/>");
							//alert('删除教育实绩信息失败！');
						}
					  }
			,
			error: DWZ.ajaxError
		});
		
	}
}
	
$(document).ready(function() {
	var branch0=$('#hBRANCH_EDU_1').val();
	//changePayArea(branch0);
	
	$.ajax({
		cache: false,
		url : '${base}/promoter/getListBySelect?type=BRANCH&selected='+branch0+'&name=seach_BRANCH_EDU_1&deptLevel=3',
		type : "get",
		dataType : "html",
		success : function(data) {
			$("#seach_BRANCH_EDU_1").html(data);
		}
	});
	
});
</script>
<a id="importExcelDialog_jy0400" href="#" width="400" height="200" target="dialog" mask="true"><span id="import_jy0400Link" style="display: none"></span></a> 
<a id="importExcel_jy0400" href="#" target="dialog" width="800" height="420" mask="true"><span	style="display: none;"><spring:message code="empsubject.alert.uploadResult"/><!--教育实绩导入结果--></span></a>
		 
<div class="pageHeader">


<form id="viewPromotoGrade" name="viewPromotoGrade" onsubmit="return navTabSearch(this);" action="/empsubject/viewPromotoGradeList" method="post" rel="pagerForm">
<div class="searchBar">
	    <input id="hBRANCH_EDU_1" name="hBRANCH_EDU_1" type="hidden" value="${BRANCH_EDU_1}" />
<table class="searchContent">
	
   <tr>
		<td><spring:message code="empsubject.branch"/><!-- 支社： -->
		</td>
		<td>
		    <span id="seach_BRANCH_EDU_1" name="seach_BRANCH_EDU_1"></select></span>
		</td>
		<td><!-- 开始日期 -->
			<spring:message code="public.title.startDate"/>
		</td>
		<td width = "155">
			<input id="seach_START_DATE" type="text" name="seach_START_DATE" class="date required" readonly value="${START_DATE}"/>
			<a class="inputDateButton"><!-- 选择 -->
				 <spring:message code="public.title.choose"/>
			</a>
		</td>
		<td><!-- 结束日期 -->
			<spring:message code="public.title.endDate"/>
		</td>				
		<td width = "160">
			<input id="seach_END_DATE" type="text" name="seach_END_DATE" class="date required" readonly value="${END_DATE}"/>
		    <a class="inputDateButton"><!-- 选择 -->
                 <spring:message code="public.title.choose"/>
			</a>
		</td>  <td></td><td></td><td></td>
	</tr>
	<tr>		
  		<td><!-- 课程组id -->
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
		<td><spring:message code="empsubject.subjectNm"/><!-- 课程名称 --> </td>     
        <td id="selectTd">
        	<input type="text" name="SUBJT_NM" value="<c:out value='${SUBJT_NM}'/>">
        </td>
        <td><spring:message code="inct.salesman.empNoNName" /><!--社号/姓名--></td>
		<td>
						<input id="seach_EMPID" name="dwz.person.empId" type="text" value="${searchMap.EMPNO}" readOnly lookupGroup="person"/>
						<input id="seach_PERSON_ID" name="dwz.person.personId" type="hidden" value="" readOnly lookupGroup="person"/>
						<a class="btnLook" style="float:right;" href="/ar/attendanceSettings/viewKeeperList?pageNum=1" width="900" height="400" lookupGroup="person"><!-- 查找带回 --><spring:message code="ar.alert.message.viewattendencekeeper.chazhaodaihui"/></a>
		</td>
	</tr>
	<tr>
		<td><spring:message code="empsubject.useYn"/><!-- 状态 -->
		</td>
		<td style="width:100px">
			<select name="USE_YN">
				<option value="Y" <c:if test="${USE_YN eq 'Y'}">selected</c:if>>
				<spring:message code="empsubject.useY"/><!--使用--></option>
				<option value="N" <c:if test="${USE_YN eq 'N'}">selected</c:if>>
				<spring:message code="empsubject.useN"/><!--不使用--></option>
			</select>
		</td>
		<td>线上区分
		</td>
		<td style="width:100px">
			<ait:ComboSyCodeDescByCpnyID id="seach_ONLINE_STATE"
			name="seach_ONLINE_STATE" parentNo="14013523"
			selected="${ONLINE_STATE}" cnpyID="${defaultCpny}" limit="all" />
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
			</ul>
</div>
</div>
</form>
</div>

<div class="pageContent">
	<c:set value="0" var="delete_target_exit"/>
	<c:set value="javascript:f_delete(navTabAjaxDone);" var="delete_Url"/>
	
	
	<c:set value="/empsubject/downloadPromotoGradeImpTemplate" var="excelT_Url" />
	<c:set value="javascript:importExcelPromotoGradeData();" var="excelU_Url"/>
	<c:set value="javascript:expPromotoGradeList(this,'${request_navTabId}');" var="excelD_Url"/>
	<%@ include file="/WEB-INF/view/inc/includeExcelButton.jsp"%>	
<table class="table" width="150%" layoutH="232">
	<thead>
		<tr>
		    
			<th align="center"  width="1%">
				<input type="checkbox" class="checkboxCtrl" group="promotoGradeCKB" />
			</th>
			<th width="4%"><spring:message code="inct.salesman.daqu"/><!-- 大区--></th>
			<th width="4%"><spring:message code="empsubject.branch"/><!-- 支社--></th>
			<th width="4%"><spring:message code="empsubject.cityName"/><!-- 城市--> </th>
			<th width="3%"><spring:message code="empsubject.channel1Name"/><!-- 流通--></th>
			<th width="3%"><spring:message code="empsubject.shopName"/><!-- 门店名称--></th>
			<th width="3%"><spring:message code="empsubject.shopLevel"/><!-- 门店等级--></th>
			<th width="3%"><spring:message code="empsubject.empno"/><!-- 社号--></th>
			<th width="3%"><spring:message code="empsubject.userNm"/><!-- 姓名--></th>
			<th width="3%"><spring:message code="empsubject.sexName"/><!-- 性别--></th>
			<th width="3%"><spring:message code="empsubject.officePhone"/><!-- 电话--></th>
			<th width="3%"><spring:message code="empsubject.userProd"/><!-- 主责产品--></th>
			<th width="3%"><spring:message code="empsubject.subjectGrNm"/><!-- 课程组 --> </th>
			<th width="3%"><spring:message code="empsubject.subjectNm"/><!-- 课程名称 --></th>
			<th width="3%">线上区分</th>
			<th width="3%"><spring:message code="empsubject.gradePoint"/><!-- 成绩--></th>
			<th width="3%">平均分</th>
			<th width="3%"><spring:message code="empsubject.courseS"/><!-- 课程满意度--></th>
			<th width="3%"><spring:message code="empsubject.lecturerS"/><!-- 讲师满意度--></th>
			<th width="3%"><spring:message code="empsubject.nps"/><!-- NPS--></th>
			<th width="3%"><spring:message code="empsubject.subjtTime"/><!-- 课时--></th>
			<th width="3%"><spring:message code="empsubject.tcrNm"/><!-- 讲师姓名 --></th>
			<th width="3%"><spring:message code="empsubject.eduTime"/><!-- 培训开始日期--></th>
			<th width="3%"><spring:message code="empsubject.eduRm"/><!-- 培训地点--></th>
			<th width="3%"><spring:message code="empsubject.npsReason"/><!-- NPS推荐理由--></th>
			<th width="3%"><spring:message code="empsubject.npnNoReason"/><!-- NPS不推荐理由--></th>
			<th width="3%"><spring:message code="empsubject.salesTalk"/><!-- Sales Talk内容--></th>
			<th width="3%"><spring:message code="empsubject.comClubInfo"/><!-- 竞争社信息--></th>
			<th width="3%"><spring:message code="empsubject.otherFeedback"/><!-- 促销员其他反馈--></th>
			<th width="3%"><spring:message code="empsubject.updtTime"/><!-- 修改时间 --></th>
			<th width="3%"><spring:message code="empsubject.updateUser"/><!-- 更新人 --></th>
			<th width="4%"><spring:message code="empsubject.useYn"/><!-- 状态 --></th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${promotoGrade}" var="item" varStatus="i" >

			<tr target="sid" rel="EVAL_SEQ=${item.EVAL_SEQ}">
 
				<td>
					<c:if test="${item.USE_YN=='Y'}" >
						<input type="checkbox" id="promotoGradeCKB" name="promotoGradeCKB" value="${item.EVAL_SEQ }">
					</c:if>
				</td>
				<td class='td_center'>${item.PAY_AREA_NM}</td>
				<td class='td_center'>${item.BRANCH_NM}</td>
                <input type="hidden" name="BRANCH_CD" value="${item.BRANCH_CD}"/>
				<td class='td_center'>${item.CITY_NAME}</td>
				<td class='td_center'>${item.CHANNEL1_NAME}</td>
				<td class='td_center'>${item.SHOP_NAME}</td>
				<td class='td_center'>${item.SHOP_LEVEL}</td>
				<td class='td_center'>${item.EMPNO}</td>
				<td class='td_center'>${item.EMP_NM}</td>
				<td class='td_center'>${item.SEX_NAME}</td>
				<td class='td_center'>${item.OFFICE_PHONE}</td>
				<td class='td_center'>${item.CN_CD_NM}</td>
				<td class='td_center' >${item.SUBJT_GR_NM}</td>
                <input type="hidden" name="SUBJT_GR" value="${item.SUBJT_GR}"/>
				<td class='td_center'>${item.SUBJT_NM}</td>
				<td class='td_center'>${item.ONLINE_STATE}</td>
                <input type="hidden" name="SUBJT_ID" value="${item.SUBJT_ID}"/>
				<td class='td_right'>${item.GRADE_POINT}</td>
				<td class='td_right'>${item.AVG_POINT}</td>
				<td class='td_right'>${item.COURSE_S}</td>
				<td class='td_right'>${item.LECTURER_S}</td>
				<td class='td_center'>${item.NPS}</td>
				<td class='td_right'>${item.SUBJT_TIME}</td>
				<td class='td_center'>${item.TCR_NM}</td>
				<td class='td_center'>${item.EDU_TIME}</td>
				<td class='td_center'>${item.EDU_RM}</td>
				<td class='td_center'>${item.NPS_REASON}</td>
				<td class='td_center'>${item.NPS_NO_REASON}</td>
				<td class='td_center'>${item.SALES_TALK}</td>
				<td class='td_center'>${item.COM_CLUB_INFO}</td>
				<td class='td_center'>${item.OTHER_FEEDBACK}</td>
				<td class='td_center'>${item.UPDT_DTIME}</td>
				<td class='td_center'>${item.UPDT_USER}</td>
				<td class='td_center'>
					<c:if test="${item.USE_YN eq 'Y'}" >使用</c:if>
					<c:if test="${item.USE_YN eq 'N'}" >不使用</c:if>
				</td>				
			</tr>			
			<input type="hidden" id="DEL_USER" name="DEL_USER" value="${DEL_USER}" />
		</c:forEach>
	</tbody>
</table>
<c:set value="/empsubject/viewPromotoGradeList" var="pageUrl"/>
<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>

<a id="excelDialog_jy0400" href="#" target="dialog" mask="true"><span
		id="jy0400Link" style="display: none"></span></a>
	<form id="excelForm_jy0400" name="excelForm_jy0400" method="post">
	    <input type="hidden" id="password" name="password" value="" />
	    <input type="hidden" id="BRANCH_EDU_1" name="BRANCH_EDU_1" value="" />
	    <input type="hidden" id="START_DATE" name="START_DATE" value="" />
	    <input type="hidden" id="END_DATE" name="END_DATE" value="" />
	    <input type="hidden" id="SUBJT_GR_ID" name="SUBJT_GR_ID" value="" />
	    <input type="hidden" id="SUBJT_NM" name="SUBJT_NM" value="" />
	    <input type="hidden" id="EMPNO" name="EMPNO" value="" />
	    <input type="hidden" id="USE_YN" name="USE_YN" value="" />
	</form>
</div>