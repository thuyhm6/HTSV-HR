<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
<!--
function cancelTransferNormaValidateCallback(form, callback,flag) {
	
	var $form=null;
	if($('#'+form).length>0)
		$form=$('#'+form);
	else
 		$form = $(form);
	
	if (!$form.valid()) {
		return false;
	}
	
    var checked=false;
	var ids= document.getElementsByName("searchTransferNorma");
	for(var i=0;i<ids.length;i++){
		if(ids[i].checked){
			checked=true;
			break;
		}
	}
	if(!checked){
		//请选择信息再进行保存操作
		alertMsg.error('<spring:message code="hr.alert.message.viewPersonalInfo.checkBoxForChecked"/>');
		return false;
	}

    $form.attr("action","/hrm/searchTransferOrder/cancelTransferNormaInBatch");
	
	$.ajax({
		type: form.method || 'POST',
		url:$form.attr("action"),
		data:$form.serializeArray(),
		dataType:"json",
		cache: false,
		//success: callback || DWZ.ajaxDone,
		success: function(data){ //请求成功后处理函数。
			if(data.statusCode=="200"){
				navTabSearch("searchTransferNormalForm");
				alertMsg.correct(data.message);
			}else{
				if(data.result=="2"){
					alertMsg.info(data.message);
				}else{
					alertMsg.error(data.message);
				}
			}   
 		}  ,
		error: DWZ.ajaxError
	});
	
	return false;
}
//-->
</script>

<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/hrm/searchTransferOrder/searchTransferNormal" 
	method="post" id="searchTransferNormalForm" name="searchTransferNormalForm" rel="pagerForm">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					<spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/>
					<!-- 部门： -->
				</td>
				<td>
					<ait:deptTree name="seach_DEPT_NO" limit="hr" selected="${DEPT_NO }" />
				</td>
                <td>
					<spring:message code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME"/>
					<!--社号/姓名：-->
				</td>
				<td>
					<input type="text" name="seach_KEY" value="${KEY}" />
				</td>
				<td>
					<spring:message code="hr.viewTransferNormal.title.TRANSFERNORMALTYPE"/>
					<!--转正类型:-->
				</td>									 
				<td>
				     <ait:SelectSyCodeByCpnyID name="seach_TRANS_CODE" parentNo="1360" cnpyID="${defaultCpny}" selected="${TRANS_CODE}" limit="all"/>       
				</td>
			</tr>
			</table>
			<table class="searchContent">
			<tr>
                <td>
                    <spring:message code="hr.viewTranslate.title.PUBLIC_START_DATE"/>
                    <!--开始时间:-->
                </td>
			    <td>
			        <input type="text" name="seach_PROBATION_DATE_FROM" class="date" format="yyyy-MM-dd" readonly="true" value="${PROBATION_DATE_FROM }"/>
				    <a class="inputDateButton" href="javascript:;"></a>			   
			    </td>
                <td>
                    <spring:message code="hr.viewTranslate.title.PUBLIC_END_DATE"/>
                    <!--结束时间:-->
                </td>                			     
				<td>
				    <input type="text" name="seach_PROBATION_DATE_TO" class="date" format="yyyy-MM-dd" readonly="true" value = "${PROBATION_DATE_TO }"/>
				    <a class="inputDateButton" href="javascript:;"></a>
				</td> 			
				<td>
					<spring:message code="hr.viewTransactionTransViewList.title.TRANSSTATUSCODE"/>
					<!--决裁状态:-->
				</td>					
				<td>
				     <ait:SelectSyCodeByCpnyID name="seach_STATUS_CODE" parentNo="3528" cnpyID="${defaultCpny}" selected="${STATUS_CODE}" limit="all"/>       
				</td>		 
			</tr>
		</table>

	</div>
		<div class="formBar">
			<tr>
			<label style="float: left">
				<input type="checkbox" class="checkboxCtrl" group="searchTransferNorma" />
				<spring:message code="hr.viewUpgrade.title.CHECKALL"/>
				<!--全选-->
			</label>
			<ul>
				<li>
					<div class="subBar">
                            <div class="buttonActive"><div class="buttonContent"><button type="submit"><spring:message code="public.title.search"/><!-- 检索 --></button></div></div> 					
						    <div class="buttonActive">
						         <a class="update" onclick="return cancelTransferNormaValidateCallback('updateTransferNormaForm',DWZ.ajaxDone,'');" href="#" ><span><spring:message code="hr.viewTransactionTransViewList.title.CANCLETRANS"/><!-- 取消发令 --></span></a>
                            </div>
				    </div>	
				</li>
		</tr>
	</div>
	</form>
</div>

      	

<div class="pageContent">
<form name="updateTransferNormaForm" id="updateTransferNormaForm" method="post" action="/hrm/searchTransferOrder/cancelTransferNormaInBatch"
	  onsubmit="return cancelTransferNormaValidateCallback(this,navTabAjaxDone);">
 	<table class="table" width="120%" layoutH="150" nowrapTD="false">      
		<thead>
			<tr>
			    <th>
			    	<spring:message code="hr.viewUpgrade.title.CHECKALL"/>
			    	<!--全选-->
			    </th>
				<th orderField="EMPID" class="${orderDirection}">
					<spring:message code="hr.viewPersonalInfo.title.EMPID"/>
					<!--社号-->
				</th>
				<th orderField="nlssort(LOCAL_NAME,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
					<spring:message code="hr.viewPersonalInfo.title.LOCAL_NAME"/>
					<!--姓名-->
				</th>
				<th orderField="nlssort(DEPT_NAME,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
					<spring:message code="hr.viewPersonalInfo.title.XIANBUMEN"/>
					<!--现部门-->
				</th>
				<th orderField="nlssort(POSITION_NAME,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
					<spring:message code="hr.viewPersonalInfo.title.XIANZHIWEI"/>
					<!--现职位-->
				</th>
				<th orderField="nlssort(POST_NAME,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
					<spring:message code="hr.viewPersonalInfo.title.POST_NAME"/>
					<!--职级名称(职务)-->
				</th>
				<th orderField="nlssort(STATUS_NAME,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
					<spring:message code="hr.viewPersonalInfo.title.STATUS_NAME"/>
					<!--员工状态-->
				</th>
				<th orderField="nlssort(SCHEDULE_PROBATION_DATE,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
					<spring:message code="hr.viewPersonalInfo.title.BEFORE_END_PROBATION_DATE"/>
					<!--预转正日期-->
				</th>
				<th orderField="nlssort(PROBATION_DATE,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
					<spring:message code="hr.viewPromote.title.EFFECTIVE_DATE"/>
					<!--生效日期-->
				</th>
				<th orderField="nlssort(TRANS_NAME,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
					<spring:message code="hr.viewTransferNormal.title.TRANSFERNORMALTYPE"/>
					<!--转正类型-->
				</th>
				<th orderField="nlssort(EMP_TYPE_NAME,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
					<spring:message code="hr.viewPersonalInfo.title.EMP_TYPE_NAME"/>
					<!--员工类型-->
				</th>
				<th>
					<spring:message code="hr.viewPromote.title.REMARK"/>
					<!-- 备注 -->
				</th>
				<th>
					<spring:message code="hr.viewTransactionTransViewList.title.DEFINITELY_CUTTION_CODITIONS"/>
					<!--决裁情况-->
				</th>
				<th>
					<spring:message code="hr.viewTransactionTransViewList.title.HANDLERS"/>
					<!--操作者-->
				</th>	
				<th>
					<spring:message code="hr.viewTransactionTransViewList.title.IT_BECOME_EFFECTIVE"/>
					<!--是否生效-->
				</th>									
			</tr>
		</thead>
	
		<tbody>
			<c:forEach items="${hrTransferNormalList}" var="hrTransferNormal" varStatus="i">			
				<tr target="sid" rel="${hrTransferNormal.EXP_INSIDE_NO}">
				    <td>
				        <input type="checkbox" id="searchTransferNorma" name="searchTransferNorma" value="${hrTransferNormal.EXP_INSIDE_NO}" />
				    </td>
					<td>${hrTransferNormal.EMPID}</td>
					<td>${hrTransferNormal.LOCAL_NAME}</td>
					<td>${hrTransferNormal.DEPT_NAME}</td>
					<td>${hrTransferNormal.POSITION_NAME}</td>
					<td>${hrTransferNormal.POST_NAME}</td>
					<td>${hrTransferNormal.STATUS_NAME}</td>
					<td>${hrTransferNormal.SCHEDULE_PROBATION_DATE}</td>
					<td>${hrTransferNormal.PROBATION_DATE}</td>
					<td>${hrTransferNormal.TRANS_NAME}</td>
					<td>${hrTransferNormal.EMP_TYPE_NAME}</td>
					<td>${hrTransferNormal.CONTENT}</td>
				    <%--<td>					
                        <a href="/hrm/searchTransferOrder/searchTransferNormalForHistory?PERSON_ID=${hrTransferNormal.PERSON_ID}" target="navTab">
                            <span>历史记录</span>
						</a>
					</td>--%>
					<td>
					    <c:forEach items="${hrTransferNormal.affirmerList}" var="affirmer" varStatus="i">					  					           
					        <dt style="padding: 1px;">
					            ${affirmer.LOCAL_NAME}
						         <c:if test="${affirmer.AFFIRM_FLAG==1}" >			                            
			                              &nbsp;<spring:message code="hr.viewTransactionTransViewList.title.PASS"/><!-- 通过 -->
			                     </c:if> 
			                     <c:if test="${affirmer.AFFIRM_FLAG==2}" >         
			                              &nbsp;<spring:message code="hr.viewTransactionTransViewList.title.VOTE_DOWN"/><!-- 否决 --> 
								 </c:if>
			                     <c:if test="${affirmer.AFFIRM_FLAG==0}" >         
			                              &nbsp;<spring:message code="hr.viewTransactionTransViewList.title.PENDING_CUTTION"/><!-- 未决裁 --> 
								 </c:if>								 
							</dt>
						</c:forEach>	
					</td>						
					<td>${hrTransferNormal.CREATED_BY_NAME}</td>
			        <td>
			            <c:if test="${hrTransferNormal.ACTIVITY == 1 }" >
			               <img src="/resources/images/a_1.gif" style="cursor:hand"/>
			            </c:if>
			            <c:if test="${hrTransferNormal.ACTIVITY == 0 }" >
			               <img src="/resources/images/0.gif" style="cursor:hand"/>
			            </c:if>
			            <c:if test="${hrTransferNormal.ACTIVITY == 2 }" >
			                &nbsp;<spring:message code="hr.viewTransactionTransViewList.title.CANCELLED"/><!-- 已取消 -->&nbsp;
			            </c:if>
			        </td>
		       </tr>			
			</c:forEach>			
		</tbody>	
	</table>
</form>
	<c:set value="/hrm/searchTransferOrder/searchTransferNormal" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>	