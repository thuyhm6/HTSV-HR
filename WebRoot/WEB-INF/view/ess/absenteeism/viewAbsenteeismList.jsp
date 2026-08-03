<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.easydrag.js"></script>
<script>

function locked_addAbsent(seq){
   document.getElementById("Absenteeism_"+seq).checked=true; 
}

function f_viewardetail_addAbsent(form, callback) {

	//选中检查
	var checked=false;
	var ids= document.getElementsByName("Absenteeism"); 
	for(var i=0;i<ids.length;i++){
		if(ids[i].checked){
			checked=true;
		}
	}
	if(!checked){
		//请选择人员再进行添加操作!
		alertMsg.error("<spring:message code='ar.alert.message.viewdynamicgroup.choosepersonforadd'/>");
		return false;
	}

	//json传值
	var jsonData = '[';
     
	$.each($("input[name='Absenteeism']"),function(i, obj) {
		if (obj.checked) {
			if (jsonData.length > 1) {
				jsonData += ',{';
			} else {
				jsonData += '{';
			}
			jsonData += ' "PK_NO": "' + obj.value + '" ,';
			jsonData += ' "ITEM_NO": "' + $("#ITEM_NO_"+i,navTab.getCurrentPanel()).val() + '" ,';
			jsonData += ' "AR_DATE_STR": "' + $("#AR_DATE_STR_"+i,navTab.getCurrentPanel()).val()  + '" ,';
			jsonData += ' "APPLY_PERSON_ID": "' + $("#APPLY_PERSON_ID_"+i,navTab.getCurrentPanel()).val()  + '" ,';
			jsonData += ' "APPLY_EMP_ID": "' + $("#APPLY_EMP_ID_"+i,navTab.getCurrentPanel()).val()  + '" ,';
			jsonData += ' "GONG_SI_QUFEN": "' + $("#GONG_SI_QUFEN_"+i,navTab.getCurrentPanel()).val()  + '" ,';
			jsonData += ' "AR_MONTH_STR": "' + $("#AR_MONTH_STR_"+i,navTab.getCurrentPanel()).val()  + '" ,';
			jsonData += ' "REASON": "' + $("#REASON_"+i,navTab.getCurrentPanel()).val()  + '" ,';
			jsonData += ' "FROM_TIME": "' + $("#FROMTIME_"+i,navTab.getCurrentPanel()).val()  + '" ,';
			jsonData += ' "TO_TIME": "' + $("#TOTIME_"+i,navTab.getCurrentPanel()).val()  + '" ,';
			jsonData += ' "REASON": "' + $("#REASON_"+i,navTab.getCurrentPanel()).val()  + '" ,';
			jsonData += ' "AFFIRM_ID": "' + $("#AFFIRM_ID_A_"+i,navTab.getCurrentPanel()).val()  + '" ,';
			jsonData += ' "APPLY_TYPE_NO": "218197",';
			jsonData += ' "BIAO": "A",';
			jsonData += ' "GERENORPILIANG": "GEREN",';
			jsonData += ' "REMARKS": "' + $("#REMARKS_"+i,navTab.getCurrentPanel()).val()  + '"';
			jsonData += '}';
		}
	});
	jsonData += ']';

	if (jsonData.length == 2) {
		//请选择要添加的数据
		alertMsg.error("<spring:message code='ar.alert.message.viewardetail.choosetoadd'/>");
		return false;
	}
	var $form = $(form);

//	if (!$form.valid()) {
//		return;
//	}

	//确定要提交吗？
	
 	if (confirm ("<spring:message code='ar.alert.message.viewArAnnualStandard.consubmit'/>")){	
 		$.ajax({
 			type:form.method || 'POST',
 			url:$form.attr("action"),
 			data:[{ name: 'jsonData', value: jsonData }],
 			dataType:"json",
			cache: false,
			success: callback || DWZ.ajaxDone,
			error: DWZ.ajaxError
 		});
		
 	}
 	return false;
}

function modifyCwaAffirmList(divId,affirmIdStr,personId){
	$.pdialog.open("/sys/notice/addAffirmWindow?PERSON_ID=" + personId + "&divId=" + divId + "&affirmIdStr=" + affirmIdStr + "&APPLY_TYPE=218197", "addAffirmWindow", "添加审批者", {width:550,height:320,mask:true});
}
</script>
<div class="pageHeader">
	<form id="pageForm"  onsubmit="return navTabSearch(this);" action="/ess/absenteeism/viewAbsenteeismList" rel="pagerForm"
		method="post" >
		<div class="searchBar">
			<table class="searchContent">
			<tr>
					<td><!-- 社号/姓名： --> <spring:message
						code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME" />
					</td>
					<td> 
						<input type="text" name="seach_KEY" value="${KEY}" />
					</td>
					<td><!-- 部门： --> <spring:message
						code="hr.viewPersonalInfo.title.DEPTNAME" /> 
					</td>
					<td>
				      <ait:deptList name="seach_DEPT_NO" limit="ar" id="viewAnnualadjustmentInfoAbsent_seachDept"/>
				      <ait:deptTreeIcon name="seach_DEPT_NO" limit="ar" id="viewAnnualadjustmentInfoAbsent_seachDept" selected="${DEPT_NO}"/>
				    </td>
						<td>人员类型组</td>
						<td>一般家电促销员</td>
						<td>在职状态</td>
						<td>
				 	<ait:SelectSyCodeByCpnyID id="seach_EmpOffice" name="seach_EmpOffice" parentNo="15118" selected="${EmpOffice}" cnpyID="${LoginUser.cpnyId}" limit="all"/>
						</td>
				</tr>
				<tr>
				   <td >
									<!-- 开始日期 -->
									<spring:message code="ar.viewcycleparameter.title.kaishiriqi" />
								</td>
								<td >
									<input type="text" name="seach_sDate" class="date"
										value="${sDate}" yearstart="-20" yearend="20" readonly="true" />
										<a class="inputDateButton" href="javascript:;"><spring:message code="public.title.choose"/><!-- 选择 --></a>
								</td>
								<td >
									<!-- 结束日期 -->
									<spring:message code="ar.viewcycleparameter.title.jieshuriqi" />
								</td>
								<td>
									<input type="text" name="seach_eDate" class="date"
										value="${eDate}" yearstart="-20" yearend="20" readonly="true" />
										<a class="inputDateButton" href="javascript:;"><spring:message code="public.title.choose"/><!-- 选择 --></a>
								</td>
							</tr>
			 
			</table>
			<div class="subBar">
				<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">
									<!-- 查询 -->
									<spring:message code="button.search" />
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
	 
	<form id="addCwaAbnormalApplyAbsent" method="post" action="/ess/infoApply/addCwaAbnormalApply" class="pageForm required-validate" 
	   onsubmit="return f_viewardetail_addAbsent(this,navTabAjaxDone);">

		<div class="formBar">
			<ul class="toolBar">
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">
								<spring:message code="ess.infoApply.title.apply"/><!--申请-->
							</button>
						</div>
					</div>
				</li>
				<li>
					<a class="submit"
						 href="/ess/infoApply/viewCwaAbnormalApplyInfo?pageNum=1" target="navTab" rel="iewCwaAbnormalApplyInfo">
							 <span>
								 查看已申请信息
						 </span> 
					 </a> 
				</li>
			</ul>
		</div>
			<table class="table" width="100%" height="80%" layoutH="235">
				<thead>
					<tr>
						<th align="center">
							<input type="checkbox" class="checkboxCtrl" group="Absenteeism">
						</th>
						<th>
						     <!-- 工号/姓名 -->
							<spring:message code="public.title.empIdAndName" />
							
						</th>
						<th>
							<!-- 部门 -->
							<spring:message code="public.title.deptName" />
						</th>
						<th>
						        考勤日期
						</th>
						<th>
						        考勤状态
						</th>
						<th width="30%">
							修改原因
						</th>
						<th>
							审批线
						</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${arDetailList}" var="list" varStatus="i">
						<tr>
							<td style="text-align: center" >
								<input type="checkbox" id="Absenteeism_${i.index}" name="Absenteeism"
									value="${list.PK_NO}">
							</td>
							<td style="text-align: left">
								<input type="hidden" id="APPLY_PERSON_ID_${i.index}"
									name="APPLY_PERSON_ID_${i.index}" value="${list.PERSON_ID}" />
								<input type="hidden" id="APPLY_EMP_ID_${i.index}"
									name="APPLY_EMP_ID_${i.index}" value="${list.EMPID}" />
								(${list.EMPID})${list.LOCAL_NAME}
								<input type="hidden" id="AR_MONTH_STR_${i.index}"
									name="AR_MONTH_STR_${i.index}" value="${list.AR_MONTH_STR}" />
								<input type="hidden" id="FROMTIME_${i.index}"
									name="FROMTIME_${i.index}" value="${list.FROMTIME}" />
								<input type="hidden" id="TOTIME_${i.index}"
									name="TOTIME_${i.index}" value="${list.TOTIME}" />
							</td>
							<td style="text-align: left">
								${list.DEPTNAME}
							</td>
							<td style="text-align: center">
							 ${list.AR_DATE_STR }
							 <input type="hidden" id="AR_DATE_STR_${i.index}"
									name="AR_DATE_STR_${i.index}" value="${list.AR_DATE_STR}" />
							</td>
							<td style="text-align: center" >
								<input type="hidden" id="ITEM_NO_${i.index}"
									name="ITEM_NO_${i.index}" value="${list.ITEM_NO}" />
								${list.ITEM_NAME }
								<input name="GONG_SI_QUFEN_${i.index}"  id="GONG_SI_QUFEN_${i.index}" type="hidden"/>
							</td>
							<td style="text-align: center">
								<input style="width: 100%;" name="REASON_${i.index}" id="REASON_${i.index}"
									type="text"/>
								<input name="AFFIRM_ID_${i.index}"  id="AFFIRM_ID_A_${i.index}" type="hidden" value="<c:forEach items="${list.affirmList}" var="affirm" varStatus="j">${affirm.AFFIRMOR_ID};</c:forEach>"/>
							</td>
							<td style="text-align: left" id="cwaAddAffirm_A_${i.index}">
							<c:forEach items="${list.affirmList}" var="affirm" varStatus="j">	
								${affirm.AFFIRM_LEVEL}、[${affirm.EMPID}]${affirm.LOCAL_NAME};
							</c:forEach>
							(<img src="/resources/images/+.gif" title="添加" border="0" align="absmiddle" style="cursor:hand" onclick="modifyCwaAffirmList('cwaAddAffirm_A_${i.index}','AFFIRM_ID_A_${i.index}',${list.PERSON_ID})"/>)
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
	</form>
	<c:set value="/ess/absenteeism/viewAbsenteeismList" var="pageUrl" />
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>