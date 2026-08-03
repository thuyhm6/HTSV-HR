<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@page import="com.ait.sys.service.ToolMenuSer"%>
<%@page import="com.ait.sys.service.impl.ToolMenuSerImpl"%>
<script type="text/javascript">

function navTabSearchS(form) {

	var $form = $("#essApplyBatchApproval");

	var id_array = new Array();
	$('input[name="checkedSon"]:checked').each(function() {
		id_array.push($(this).val());//向数组中添加元素
		});
	var idstr = id_array.join(',,');//将数组元素连接起来以构建一个字符串
	$("#getValueS").attr("value", idstr);

	if (!$form.valid()) {
		return false;
	}
	
	if(id_array.length==0){
		alertMsg.error("<spring:message code='hr.viewCondSql.title.QINGXUANZE' />");//请选择
		return false;
	}
	$.ajax( {
		type : form.method || 'POST',
		url : $form.attr("action"),
		data : $form.serializeArray(),
		dataType : "json",
		cache : false,
		success : DWZ.ajaxDone,
		error : DWZ.ajaxError
	});

	return false;

}

	 
function checkedAllSon(obj) {

	$("input[name='checkedSon']").attr("checked", obj.checked);

}
//判断是否有值
var p =$(".pername");
var seach_KEY =$("[name='seach_KEY']");
if(p != null){
	seach_KEY.val(p.val());
}
</script>
 <!-- <script type="text/javascript"> 
/* $("#seach_KEY",navTab.getCurrentPanel()).keydown(function(e) {
        if ( e.keyCode == 13) {
       	 	var name=encodeURI(encodeURI($('#seach_KEY',navTab.getCurrentPanel()).val()));
       		$('.btnLook',navTab.getCurrentPanel()).attr('href','/hrm/approve/viewEmpInfoListSearch?pageNum=1&firstFlag=N&searchChange=viewPersonalInfo&seach_KEY='+name);
       		$('.btnLook',navTab.getCurrentPanel()).click();
        }
    }); */
 	$(".btnLook",navTab.getCurrentPanel()).click(function(e) {
       	 var name=encodeURI(encodeURI($('#seach_KEY',navTab.getCurrentPanel()).val()));
       	$('.btnLook',navTab.getCurrentPanel()).attr('href','/hrm/approve/viewEmpInfoListSearch?pageNum=1&firstFlag=N&searchChange=viewPersonalInfo&seach_KEY='+name);
    }); 
</script>  -->
<div class="pageHeader" style="border: 0px;">
	<form id="viewEssApplyInfo_form" onsubmit="return navTabSearch(this);"
		action="/hrm/approve/viewEssApplyInfo" method="post">
		<div class="searchBar">
			<table class="user_table" width="100%" style="border: 0px;">
				<tr>
					<td class="td_title" width="10%" style="border: 0px; background-color: white;">
						<!-- 社号/姓名 --> <spring:message code="hrm.empinfo.nameAndEmpid" />
					</td>
					<td width="30%" class="td_type" style="border: 0px;">
						<table width="10%">
							<tr>
								<td width="50%" style="border: 0px;">
									<input type="text" class="text" name="seach_KEY" value="${KEY}">
								</td>
								<td style="text-align: center;border: 0px;">
									<a class="btnLook"
										href="/hrm/approve/viewEmpInfoListSearch?pageNum=1&firstFlag=N&searchChange=viewPersonalInfo"
										lookupgroup="person"></a>
									<!-- 放大镜 -->
								</td>
								<td style="border: 0px;">
								<div style="float: left;width: 200px;" >
								<c:if test="${not empty personInfo}">
							${personInfo.LOCAL_NAME
								}&nbsp/&nbsp${personInfo.EMPID
								}&nbsp/&nbsp${personInfo.POST_GRADE_NO_NAME}&nbsp/&nbsp${personInfo.EMP_OFFICE_NAME}
								<input type="hidden" name="pername" class="pername" value="${personInfo.LOCAL_NAME}">
								<input type="hidden" name="pernames" class="pernames" value="${personInfo.LOCAL_NAME}&nbsp/&nbsp${personInfo.EMPID}&nbsp/&nbsp${personInfo.POST_GRADE_NO_NAME}&nbsp/&nbsp${personInfo.EMP_OFFICE_NAME}">
						</c:if>
						<c:if test="${not empty pernames}">
						${pernames}
						<input type="hidden" name="pernames" class="pernames" value="${pernames}">
						</c:if>
						</div>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="td_title" width="10%" style="border: 0px; background-color: white;">
						<!-- 数据类型 --><spring:message code="pa.insurance.title.dataType" />
					</td>
					<td class="td_type" style="border: 0px;">
						<ait:SelectSyCodeByCpnyID name="seach_ESS_TYPE_CODE"
							selected="${ESS_TYPE_CODE}" limit="all" parentNo="14013917"
							cnpyID="${defaultCpny}" />
					</td>
					<td class="td_title" width="10%" style="border: 0px; background-color: white;">
						<spring:message code="ess.workgroup.title.duration"/>
						<!--期间-->
						
					</td>
					<td width="30%" class="td_type" style="border: 0px;">
						<table width="100%" style="border: 0px;">
							<tr>
								<td width="30px" style="border: 0px;">
									<input type="text" name="seach_sDate" value="${sDate}"
										class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})" />
								</td>
								<td  style="border: 0px;">
									~
								</td>
								<td style="border: 0px;">
									<input type="text" name="seach_eDate" value="${eDate}"
										class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})" />
								</td>
								<input type="hidden" id="activity_type" value="${activity_type}" />
							</tr>
						</table>
					</td>
				</tr>

				<tr>
					<td class="td_title"  style="border: 0px; background-color: white;">
						<spring:message code="hr.viewPersonalInfo.title.DEPTNAME" />
					</td>
					<td class="td_type" style="border: 0px;">
						<ait:deptList name="seach_DEPTNO" cpnyId="${defaultCpny}"
							limit="hr" id="viewEssApplyInfo_seachDept" />
						<ait:deptTreeIcon name="seach_DEPTNO" cpnyId="${defaultCpny}"
							limit="hr" id="viewEssApplyInfo_seachDept"
							selected="${DEPTNO}" />
					</td>
					<td class="td_title" width="10%" style="border: 0px; background-color: white;">
						<!-- 状态  --> <spring:message code="org.title.status" />
					</td>
					<td class="td_type" style="border: 0px;">
						<ul>
							<li style="float: left">
								<input type="radio" name="seach_ACTIVITY" ${ACTIVITY
									eq '' ? 'checked':''} value="">
								 <!-- 全部 --><spring:message code="hrm.contract.ALL" />
								<!-- 全部 -->
								&nbsp;
							</li>
							<li style="float: left">
								<input type="radio" name="seach_ACTIVITY" ${ACTIVITY
									eq '1' ? 'checked':''}  value="${1}">
								&nbsp;<spring:message code="public.title.submit" />&nbsp;
								<!-- 提交  -->
							</li>
							<li style="float: left">
								<input type="radio" name="seach_ACTIVITY" ${ACTIVITY
									eq '2' ? 'checked':''} value="${2}">
								&nbsp;<spring:message code="ess.affirmApply.title.remark.tongguo" />&nbsp;
								<!-- 通过  -->
							</li>
							<li style="float: left">
								<input type="radio" name="seach_ACTIVITY" ${ACTIVITY
									eq '3' ? 'checked':''} value="${3}">
								&nbsp;<spring:message code="ess.empInfo.Reject" />&nbsp;
								<!-- 驳回  -->
							</li>
							<!--<li style="float: left">
								<input type="radio" name="seach_ACTIVITY" ${ACTIVITY
									eq '4' ? 'checked':''} value="${4}">
								&nbsp;<spring:message code="hrm.empinfo.EXPIRATION.Z" />&nbsp;
								 期满 
							</li>-->
						</ul>
					</td>
				</tr>
			</table>
			<div class="subBar" >
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">
									<spring:message code="public.title.search" />
									<!--检索-->
								</button>

							</div>
						</div>
					</li>
					</form>
					<li>
	<form onsubmit="return navTabSearchS(this);"
		action="/hrm/approve/essApplyBatchApproval" id="essApplyBatchApproval"
		method="post">
			<input type="hidden" id="getValueS" name="getValueS">
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">
									<spring:message code="hrm.empinfo.ALL_SUBMIT.Z" /><!--全部提交-->
								</button>
							</div>
						</div>
	</form>
					</li>
				</ul>
			</div>
		</div>
</div>
<div id="viewEssApplyInfo_left" layoutH="180"
	style="float: left; display: block; overflow: auto; width: 60%;  border: solid 1px #CCC;  background: #fff">
	<div class="user_table"
		style="font: bold 12px/ 20px arial, sans-serif;">
		<!--Total:${orgRecruitSize}  -->
	</div>
	<table class="table" width="100%" layoutH="230">
		<thead>
			<tr>
				<th width="10%">
					NO
					<!--NO-->
				</th>
				<th width="10%">
					<input name="checkedAll" type="checkbox"
						onclick="checkedAllSon(this)" />
					<!--选择--><spring:message code="public.title.choose" />
				</th>
				<th width="10%">
					<!-- 申请日期 --><spring:message code="ess.viewApply.title.applyDate" />
				</th>
				<th width="10%">
					<!-- 申请区分 --><spring:message code="hrm.approve.APPLY_DIFFERENTIATE" />
				</th>
				<th width="10%">
					<!--状态--><spring:message code="org.title.status" />
				</th>
				<th width="10%">
					<!-- 数据类型 --><spring:message code="pa.insurance.title.dataType" />
				</th>
				<th width="10%">
		<!--社号-->			<spring:message code="hrm.empinfo.empid"></spring:message>
				</th>
				<th width="10%">
					<!-- 姓名 --><spring:message code="pa.title.message.empHrmName" />
				</th>
				<th width="10%">
					<!-- 部门 --><spring:message code="hrm.empinfo.ORG_NAME_LOCAL" />
				</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${applyList}" var="item" varStatus="i">
				<c:choose>
					<c:when test="${item.APPLYNAME eq 'PERS' }">
						<tr
							onclick="openOnRight('/hrm/approve/viewApplyPersonalInfo?PERSON_NO=${item.APPLY_NOW}&PERSON_ID_ID=${item.APPLY_PRO}','viewEssApplyInfo_uitl');">
					</c:when>
					<c:when test="${item.APPLYNAME eq 'ADDR' }">
						<tr
							onclick="openOnRight('/hrm/approve/viewApplyAddressInfo?ADDRESS_NO=${item.APPLY_NOW}&ADDRESS_NO_NO=${item.APPLY_PRO}','viewEssApplyInfo_uitl');">
					</c:when>
					<c:when test="${item.APPLYNAME eq 'EMER' }">
						<tr
							onclick="openOnRight('/hrm/approve/viewApplyEmergencyAddress?EMERGENCY_NO=${item.APPLY_NOW}&EMERGENCY_NO_NO=${item.APPLY_PRO}','viewEssApplyInfo_uitl');">
					</c:when>
					<c:when test="${item.APPLYNAME eq 'FAMI' }">
						<tr
							onclick="openOnRight('/hrm/approve/viewApplyHomeRelation?FAMILY_NO=${item.APPLY_NOW}&FAMILY_NO_NO=${item.APPLY_PRO}','viewEssApplyInfo_uitl');">
					</c:when>
					<c:when test="${item.APPLYNAME eq 'WORK' }">
						<tr onclick="openOnRight('/hrm/approve/viewApplyWorkInfo?WORK_EXPER_NO=${item.APPLY_NOW}&WORK_EXPER_NO_NO=${item.APPLY_PRO}','viewEssApplyInfo_uitl');">
					</c:when>
					<c:when test="${item.APPLYNAME eq 'PROD' }">
						<tr
							onclick="openOnRight('/hrm/approve/viewApplyProductInfo?PRODUCT_NO=${item.APPLY_NOW}&PRODUCT_NO_NO=${item.APPLY_PRO}','viewEssApplyInfo_uitl');">
					</c:when>
					<c:when test="${item.APPLYNAME eq 'EDUC' }">
						<tr
							onclick="openOnRight('/hrm/approve/viewApplyEducationInfo?EDUC_NO=${item.APPLY_NOW}&EDUC_NO_NO=${item.APPLY_PRO}','viewEssApplyInfo_uitl');">
					</c:when>
					<c:when test="${item.APPLYNAME eq 'QUAL' }">
						<tr
							onclick="openOnRight('/hrm/approve/viewApplyQualificationInfo?QUAL_NO=${item.APPLY_NOW}&QUAL_NO_NO=${item.APPLY_PRO}','viewEssApplyInfo_uitl');">
					</c:when>
					<c:when test="${item.APPLYNAME eq 'REWR' }">
						<tr
							onclick="openOnRight('/hrm/approve/viewApplyRewardInfo?REWARD_NO=${item.APPLY_NOW}&REWARD_NO_NO=${item.APPLY_PRO}','viewEssApplyInfo_uitl');">
					</c:when>
				</c:choose>
				<td>
					${i.count}
				</td>
				<td>
					<input name="checkedSon" type="checkbox"
						value="${item.PERSON_ID} --${item.SUBMIT_TYPE} --${item.APPLY_TYPE_NUM} --${item.APPLY_PRO} --${item.APPLY_NOW} --${item.ESS_TYPE_CODE_S} --${item.FILE_URL} --${item.FILE_NAME} --${item.FILENOSSTR} --${item.APPLY_TYPE} --${item.ESS_TYPE_CODE} " />
				</td>
				<td>
					${item.CREATE_DATE}

				</td>
				<td>
					${item.APPLY_TYPE}
				</td>
				<td>
					${item.ACTIVITY}
				</td>
				<td>
					${item.ESS_TYPE_CODE}
				</td>
				<td>
					${item.EMPID}
				</td>
				<td>
					${item.LOCAL_NAME}
				</td>
				<td>
					${item.DEPT_NAME}
				</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
<div class="w-layout-collapse">
	<div id="layout5" class="w-layout-collapse-left"
		onclick="hiddenRight('viewEssApplyInfo_uitl','viewEssApplyInfo_left')"></div>
	<div id="layout4" class="w-layout-collapse-right"
		style="display: none;"
		onclick="showIdLeft('viewEssApplyInfo_uitl','viewEssApplyInfo_left')"></div>
	<div id="layout2" class="w-layout-collapse-right"
		onclick="hiddenleft('viewEssApplyInfo_left','viewEssApplyInfo_uitl')"></div>
	<div id="layout3" class="w-layout-collapse-left" style="display: none;"
		onclick="showId('viewEssApplyInfo_left')"></div>
</div>
<div id="viewEssApplyInfo_uitl" style="display: block;">
</div>
