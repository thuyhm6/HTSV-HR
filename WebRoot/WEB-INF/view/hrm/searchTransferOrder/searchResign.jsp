<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
<!--
function cancelResignValidateCallback(form, callback,flag) {
	
	var $form=null;
	if($('#'+form).length>0)
		$form=$('#'+form);
	else
 		$form = $(form);
	
	if (!$form.valid()) {
		return false;
	}
	
    var checked=false;
	var ids= document.getElementsByName("searchResign");
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

    $form.attr("action","/hrm/searchTransferOrder/cancelResignationInBatch");
	
	$.ajax({
		type: form.method || 'POST',
		url:$form.attr("action"),
		data:$form.serializeArray(),
		dataType:"json",
		cache: false,
		//success: callback || DWZ.ajaxDone,
		success: function(data){ //请求成功后处理函数。
			if(data.statusCode=="200"){
				navTabSearch("searchResignForm");
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
	<form onsubmit="return navTabSearch(this);" action="/hrm/searchTransferOrder/searchResign" 
	rel="pagerForm" method="post" id="searchResignForm" name="searchResignForm">
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
					<spring:message code="hr.viewPromote.title.RESIGN_TYPE_NAME"/>
					<!--离职类型:-->
				</td>									 
				<td>
				     <ait:SelectSyCodeByCpnyID name="seach_TRANS_CODE" parentNo="643" cnpyID="${defaultCpny}" selected="${TRANS_CODE}" limit="all"/>       
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
			        <input type="text" name="seach_RESIGN_DATE_FROM" class="date" format="yyyy-MM-dd" readonly="true" value="${RESIGN_DATE_FROM }"/>
				    <a class="inputDateButton" href="javascript:;"></a>			   
			    </td>
                <td>
                    <spring:message code="hr.viewTranslate.title.PUBLIC_END_DATE"/>
                    <!--结束时间:-->
                </td>                			     
				<td>
				    <input type="text" name="seach_RESIGN_DATE_TO" class="date" format="yyyy-MM-dd" readonly="true" value = "${RESIGN_DATE_TO }"/>
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
				<input type="checkbox" class="checkboxCtrl" group="searchResign" />
				<spring:message code="hr.viewUpgrade.title.CHECKALL"/>
				<!--全选-->
			</label>
			<ul>
				<li>
					<div class="subBar">
                            <div class="buttonActive"><div class="buttonContent"><button type="submit"><spring:message code="public.title.search"/><!-- 检索 --></button></div></div> 					
						    <div class="buttonActive">
						         <a class="update" onclick="return cancelResignValidateCallback('updateResignationForm',DWZ.ajaxDone,'');" href="#" ><span><spring:message code="hr.viewTransactionTransViewList.title.CANCLETRANS"/><!-- 取消发令 --></span></a>
                            </div>
				    </div>	
				</li>
		</tr>
	</div>
	</form>
</div>

      	

<div class="pageContent">
<form name="updateResignationForm" id="updateResignationForm" method="post" action="/hrm/searchTransferOrder/cancelResignationInBatch"
	  onsubmit="return cancelResignValidateCallback(this,navTabAjaxDone);">
 	<table class="table" width="100%" layoutH="150" nowrapTD="false">      
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
				<th width="120" orderField="nlssort(POST_NAME,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
					<spring:message code="hr.viewPersonalInfo.title.POST_NAME"/>
					<!--职级名称(职务)-->
				</th>
				<th orderField="RESIGN_DATE" class="${orderDirection}">
					<spring:message code="hr.viewPersonalInfo.title.DATE_LEFT"/>
					<!--离职日期-->
				</th>
				<th orderField="nlssort(TRANS_NAME,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
					<spring:message code="hr.viewPromote.title.RESIGN_TYPE_NAME"/>
					<!--离职类型-->
				</th>
				<th>
					<spring:message code="hr.viewPromote.title.REMARK"/>
					<!--备注-->
				</th>
				<th orderField="nlssort(RESIGN_REASON_NAME,'NLS_SORT=SCHINESE_PINYIN_M')" class="${orderDirection}">
					<spring:message code="hr.viewPromote.title.RESIGN_REASON"/>
					<!--离职原因-->
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
			<c:forEach items="${hrResignationList}" var="hrResignation" varStatus="i">			
				<tr target="sid" rel="${hrResignation.EXP_INSIDE_NO}">
				    <td>
				        <input type="checkbox" id="searchResign" name="searchResign" value="${hrResignation.EXP_INSIDE_NO}" />
				    </td>
					<td>${hrResignation.EMPID}</td>
					<td>${hrResignation.LOCAL_NAME}</td>
					<td>${hrResignation.DEPT_NAME}</td>
					<td>${hrResignation.POSITION_NAME}</td>
					<td>${hrResignation.POST_NAME}</td>
					
					<td>${hrResignation.RESIGN_DATE}</td>
					<td>${hrResignation.TRANS_NAME}</td>
					<td>${hrResignation.POSTREMARK_NAME}</td>
					<td>${hrResignation.RESIGN_REASON_NAME}</td>
					
				    <%-- <td>					
                        <a href="/hrm/searchTransferOrder/searchTransferNormalForHistory?PERSON_ID=${hrResignation.PERSON_ID}" target="navTab">
                            <span>历史记录</span>
						</a>
					</td>--%>
					<td>
					    <c:forEach items="${hrResignation.affirmerList}" var="affirmer" varStatus="i">					  					           
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
					<td>${hrResignation.CREATED_BY_NAME}</td>
			        <td>
			            <c:if test="${hrResignation.ACTIVITY == 1 }" >
			               <img src="/resources/images/a_1.gif" style="cursor:hand"/>
			            </c:if>
			            <c:if test="${hrResignation.ACTIVITY == 0 }" >
			               <img src="/resources/images/0.gif" style="cursor:hand"/>
			            </c:if>
			            <c:if test="${hrResignation.ACTIVITY == 2 }" >
			                 &nbsp;<spring:message code="hr.viewTransactionTransViewList.title.CANCELLED"/><!-- 已取消 -->&nbsp;
			            </c:if>
			        </td>
		       </tr>			
			</c:forEach>			
		</tbody>	
	</table>
</form>
	<c:set value="/hrm/searchTransferOrder/searchResign" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>	