<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript" src="script/jquery.js"></script>
<script type="text/javascript" src="script/jquery.easydrag.js"></script>
<script>
<!--
function pageFromSea(a){
	var seach_PERSON_ID=$("#seach_PERSON_ID",navTab.getCurrentPanel()).val()==undefined?
			"":$("#seach_PERSON_ID",navTab.getCurrentPanel()).val();
	
	var seach_AR_MONTH=$("#seach_AR_MONTH",navTab.getCurrentPanel()).val()==undefined?
			"":$("#seach_AR_MONTH",navTab.getCurrentPanel()).val();
	var seach_APPLY_TYPE_CODE=$("#seach_APPLY_TYPE_CODE",navTab.getCurrentPanel()).val()==undefined?
			"":$("#seach_APPLY_TYPE_CODE",navTab.getCurrentPanel()).val();
	var seach_AFFIRM_FLAG=$("#seach_AFFIRM_FLAG",navTab.getCurrentPanel()).val()==undefined?
			"":$("#seach_AFFIRM_FLAG",navTab.getCurrentPanel()).val();
	
	$("#pagerForm",navTab.getCurrentPanel()).attr("action", "/ess/infoApplyLeave/viewApplyLeaveInfoList?seach_FLAG=${FLAG}&seach_PERSON_ID="+seach_PERSON_ID
			+"&seach_AR_MONTH="+seach_AR_MONTH+"&seach_APPLY_TYPE_CODE="+seach_APPLY_TYPE_CODE+"&seach_AFFIRM_FLAG="+seach_AFFIRM_FLAG);
}

function cancelLeaveApplyNoAffirm_Batch(apply_no){
	var params = [];
	params.push({
		name: 'APPLY_NO',
		value: apply_no
	},{
		name: 'APPLY_TYPE',
		value: 'PERSON'
	});
	alertMsg.confirm ("确定要销假吗?",{
		okCall:function(){
		$.ajax({
			  url: '/ess/infoApplyLeave/cancelLeaveApplyNoAffirm',
			  data: params,
			  cache: false,
			  success: function(responseText){
				if (responseText == "Y"){
					alertMsg.correct("销假成功!");
					//页面重载
					navTabSearch(document.viewApplyLeaveInfoBatchList);
				}else{
					alertMsg.error("销假失败!");
				}
			  }
			});
		}});	  
}

//Leave类型联动查询
$(document).ready(function(){
	$("#seach_APPLY_TYPE_NO").bind('change',function(){
		if($("#seach_APPLY_TYPE_NO").val() == 218112){
			$('#seach_APPLY_TYPE_CODE').html("<option value='218112'>销假</option>");
		}else{
			ajaxAdd_add_ess0240(-1);
		}
	});
	
	if($("#seach_APPLY_TYPE_NO").val() != ''){
		if($("#seach_APPLY_TYPE_NO").val() == 218112){
			$('#seach_APPLY_TYPE_CODE').html("<option value='218112'>销假</option>");
		}else{
			var APPLY_TYPE_CODE = $("#seach_APPLY_TYPE_CODE_TEMP").val();
			ajaxAdd_add_ess0240(APPLY_TYPE_CODE);
		}
	}
});
var ajaxGet_add_ess0240;
function ajaxAdd_add_ess0240(APPLY_TYPE_CODE) {
		if (ajaxGet_add_ess0240 != null) {
			ajaxGet_add_ess0240.abort();
		}
		$.ajaxSettings.global = false;
		ajaxGet_add_ess0240 = $.ajax( {
			type : "POST",
			url : "/ess/infoApplyLeave/getCodeList",
			data : { APPLY_TYPE_NO : $("#seach_APPLY_TYPE_NO").val()},
			dataType : "json",
			success : function(data) {
				$('#seach_APPLY_TYPE_CODE').html("");
				var html = '<option value="">全部</option>';
				if (typeof (data['codeList']) != "undefined") {
					$.each(data['codeList'], function(commentIndex, comment) {
							html += '<option value="' + comment['CODE_NO'] + '">' + comment['CODE_NAME'] + '</option>';
						});
				}
				$('#seach_APPLY_TYPE_CODE').html(html);
				if(APPLY_TYPE_CODE != -1){
					$("#seach_APPLY_TYPE_CODE").val(APPLY_TYPE_CODE);
				}
			}
		}); 
		$.ajaxSettings.global = true;
}
//-->
</script>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/ess/infoApplyLeave/viewApplyLeaveInfoBatchList" rel="pagerForm" method="post"
		id="viewApplyLeaveInfoList" name="viewApplyLeaveInfoList">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<c:if test="${authority ne '1'}">
					<td>
						部门
					</td>
					<td>
						${personInfo.DEPARTMENT }
					</td>
					<td>社号/姓名</td>
					<td>
						${personInfo.EMPID }/${personInfo.LOCAL_NAME }
						<input type="hidden" id="seach_PERSON_ID" name="seach_PERSON_ID" value="${personInfo.PERSON_ID }"/>
					</td>
					</c:if>
					<c:if test="${authority eq '1'}">
					<td><!-- 部门： --> <spring:message
						code="hr.viewPersonalInfo.title.DEPTNAME" /> 
					</td>
					<td>
						<ait:deptList name="seach_DEPTNO" cpnyId="${defaultCpny}" limit="ar" id="viewApplyLeaveInfoBatchList_seachDept"/>
						<ait:deptTreeIcon name="seach_DEPTNO" cpnyId="${defaultCpny}" limit="ar" id="viewApplyLeaveInfoBatchList_seachDept" selected="${DEPTNO}"/></td>
					<td><!-- 社号/姓名： --> <spring:message
						code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME" />
					</td>
					<td> 
						<c:if test="${FLAG eq '1'}">
							<input type="text" name="seach_KEY" value="${KEY}" />
						</c:if>
						<c:if test="${FLAG ne '1'}">
							<input type="text" name="seach_KEY" value="${personInfo.EMPID}" />
						</c:if>
						 <input type="hidden" id="seach_FLAG" name="seach_FLAG" value="1"/>
					</td>
					</c:if>
					
					
			   	<td>人员类型组 </td>
						<td>
						<input type="hidden" id="ess0240_1_limit" name="limit" value="ar">
						<input type="hidden" id="ess0240_1_seach_CPNY" name="seach_CPNY" value="${defaultCpny}">
			<ait:SelectEmpTypeCode  id="ess0240_1_seach_JobTypeGroupNo"  name="seach_JobTypeGroupNo" selected="${JobTypeGroupNo}" limit="ar" type="group"
			onChangeName="ajaxEmpTypeForGroupToList(-1,ess0240_1_seach_JobTypeGroupNo,ess0240_1_seach_EmpTypeCodeNo,ess0240_1_seach_CPNY,ess0240_1_limit)"/>
						</td>
						<td>人员类型 </td>
						<td>
		 	<ait:SelectEmpTypeCode id="ess0240_1_seach_EmpTypeCodeNo" name="seach_EmpTypeCodeNo" selected="${EmpTypeCodeNo}" limit="ar"/>
						</td>
				</tr>
				<tr>
					<td>
						<!-- 开始日期 -->
						<spring:message code="public.title.startDate"/>
					</td>
					<td>
						<input type="text" id="seach_FROM_DATE" name="seach_FROM_DATE" class="date" format="yyyy-MM-dd" readonly="true" value="${FROM_DATE}" />
						<a class="inputDateButton" href="javascript:;"><spring:message code="public.title.choose"/><!-- 选择 --></a>
					</td>
					<td>
						<!-- 结束日期 -->
						<spring:message code="public.title.endDate"/>
					</td>
					<td>
						<input type="text" id="seach_TO_DATE" name="seach_TO_DATE" class="date" format="yyyy-MM-dd" readonly="true" value="${TO_DATE}" />
						<a class="inputDateButton" href="javascript:;"><spring:message code="public.title.choose"/><!-- 选择 --></a>
					</td>
					<td>审批状态 </td>
					<td>
						 <select id="seach_AFFIRM_FLAG" name="seach_AFFIRM_FLAG">
							 <option value="">全部</option>
							 <option value="-1" <c:if test="${AFFIRM_FLAG eq '-1'}">selected</c:if>>暂存</option>
							 <option value="0" <c:if test="${AFFIRM_FLAG eq '0'}">selected</c:if>>提交</option>
							 <option value="4" <c:if test="${AFFIRM_FLAG eq '4'}">selected</c:if>>审批中</option>
							 <option value="1" <c:if test="${AFFIRM_FLAG eq '1'}">selected</c:if>>通过</option>
							 <option value="2" <c:if test="${AFFIRM_FLAG eq '2'}">selected</c:if>>否决</option>
							 <option value="3" <c:if test="${AFFIRM_FLAG eq '3'}">selected</c:if>>撤销</option>
						 </select>
					</td>
				    <td>在职状态</td>
					<td>
				 	<ait:SelectSyCodeByCpnyID id="seach_EmpOffice" name="seach_EmpOffice" parentNo="15118" selected="${EmpOffice}" cnpyID="${LoginUser.cpnyId}" limit="all"/>
					</td>
				</tr>
				<tr>
					<td>Leave类型</td>
						<td>
				 	<ait:SelectSyCodeByCpnyID id="seach_APPLY_TYPE_CODE" name="seach_APPLY_TYPE_CODE" parentNo="21" selected="${APPLY_TYPE_CODE}" cnpyID="${LoginUser.cpnyId}" limit="all"/>
						</td>
					<td>批次号</td>
					<td>
						<input type="text" id="seach_BATCH_APPLY_NO" name="seach_BATCH_APPLY_NO" value="${BATCH_APPLY_NO }"/>
					</td>
				</tr>
			</table>
			<div class="subBar">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent">
					    <button type="submit">
					       <spring:message code="public.title.search"/> 
					    </button>
				        </div>
				        </div>
				    </li>
				</ul>
			</div>
		</div>
	</form>
</div>

<div class="pageContent" >
		<table class="table" width="100%" layoutH="235" nowrapTD="false">
			<thead>
				<tr>
				    <th><!--申请人-->
						批次号
					</th>
				    <th><!--申请人-->
						社号
					</th>
				    <th><!--申请人-->
						申请人
					</th>
				    <th><!--部门-->
						部门
					</th>
					<th><!--申请日期-->
						申请日期
					</th>
					<th><!--申请日期-->
						开始日期
					</th>
					<th><!--申请日期-->
						结束日期
					</th>
					<th><!--申请时长-->
						申请时长
					</th>
					<th><!--考勤类型-->
						考勤类型
					</th>
					<th><!--附件查看-->
						附件查看
					</th>
					<th><!--审批详情-->
						审批详情
					</th>
					<th><!--决裁情况-->
						审批情况
					</th>
					<th><!--是否取消-->
						是否取消
					</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${leaveAffirmList}" var="leaveApply" varStatus="i">			
					<tr target="sid" rel="${personInfo.PERSON_ID}">
					    <td style="text-align: center">${leaveApply.BATCH_APPLY_NO}</td>
					    <td style="text-align: center">${leaveApply.EMPID}</td>
					    <td style="text-align: center">
							${leaveApply.LOCAL_NAME}
						</td>
					    <td style="text-align: center">${leaveApply.DEPARTMENT}</td>
					    <td style="text-align: center">${leaveApply.APPLY_LEAVE_DATE}</td>
						<td style="text-align: center">${leaveApply.LEAVE_FROM_TIME}</td>
						<td style="text-align: center">${leaveApply.LEAVE_TO_TIME}</td>
						<td style="text-align: center">${leaveApply.APPLY_LENGTH}</td>
						<td style="text-align: center">${leaveApply.LEAVE_TYPE_NAME}</td>
						<td style="text-align: center">
							<c:forEach items="${leaveApply.fileList}" var="file" varStatus="j">	
								<div style="display:block;line-height:30px;"><a href="/ess/infoApplyLeave/downloadFile?fileName=${file.FILE_URL }&file=${file.FILE_NAME}" >${file.FILE_NAME}</a></div>
							</c:forEach>
						</td>
						<td style="text-align: center">
							<c:forEach items="${leaveApply.affirmorList}" var="affirmList" varStatus="index">	
								<div style="display:block;line-height:15px;font-size:10px;">
									[${affirmList.EMPID }]${affirmList.LOCAL_NAME }&nbsp;
									<c:if test="${affirmList.AFFIRM_FLAG==0}" >
									    未审批
									</c:if>	
									<c:if test="${affirmList.AFFIRM_FLAG==1}" >
									    通过
									</c:if>	
									<c:if test="${affirmList.AFFIRM_FLAG==2}" >
									    否决
									</c:if>	
								</div>
							</c:forEach>
						</td>
						<td style="text-align: center">
							<c:if test="${leaveApply.ACTIVITY eq '3' or leaveApply.CAL_FLAG ne '0'}">
								撤销
							</c:if>
							<c:if test="${leaveApply.ACTIVITY ne '3' and leaveApply.CAL_FLAG eq '0'}">
								<c:if test="${leaveApply.AFFIRM_FLAG eq '-1'}">
									暂存
								</c:if>
								<c:if test="${leaveApply.AFFIRM_FLAG eq '0'}">
									提交
								</c:if>
								<c:if test="${leaveApply.AFFIRM_FLAG eq '1'}">
									通过
								</c:if>
								<c:if test="${leaveApply.AFFIRM_FLAG eq '2'}">
									否决
								</c:if>
								<c:if test="${leaveApply.AFFIRM_FLAG eq '3'}">
									撤销
								</c:if>
								<c:if test="${leaveApply.AFFIRM_FLAG eq '4'}">
									审批中
								</c:if>
								<c:if test="${leaveApply.AFFIRM_FLAG eq '5'}">
									发令
								</c:if>
							</c:if>
						</td>	
						<td style="text-align: center">
							<c:if test="${leaveApply.LASTMONTH_YN eq 'N'}">
								<c:if test="${leaveApply.AFFIRM_FLAG eq '1' and leaveApply.LEAVE_TYPE_CODE ne '218112' and leaveApply.CAL_FLAG eq '0' and leaveApply.ACTIVITY ne '3'}">
									<a href="#" title="销假" onclick="cancelLeaveApplyNoAffirm_Batch('${leaveApply.BATCH_APPLY_NO_B}')" style="cursor: hand">
										<font color="red">销假</font>
									</a>
								</c:if>
							</c:if>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
    <c:set value="/ess/infoApplyLeave/viewApplyLeaveInfoBatchList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination2.jsp"%>
</div>