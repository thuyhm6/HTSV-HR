<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<script type="text/javascript">
function cancelSalaryAdjustTransValidateCallback(form, callback) {
	
	var $form=null;
	if($('#'+form).length>0)
		$form=$('#'+form);
	else
 		$form = $(form);
	
	if (!$form.valid()) {
		return false;
	}
	
    var checked=false;
	var ids= document.getElementsByName("searchPa");
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

    $form.attr("action","/hrm/searchTransferOrder/cancelPaAdjustInBatch");
	
	$.ajax({
		type: form.method || 'POST',
		url:$form.attr("action"),
		data:$form.serializeArray(),
		dataType:"json",
		cache: false,
		success: function(data){ //请求成功后处理函数。
			if(data.statusCode=="200"){
				navTabSearch("searchPaForm");
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
</script>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/hrm/searchTransferOrder/searchPaAdjust" 
	rel="pagerForm" method="post" name="searchPaForm" id="searchPaForm">
	<div class="searchBar">
		<table class="searchContent">
			<tr>			
				<td>
					<spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/>
					<!-- 部门： -->
				</td>	
				<td>
					<ait:deptTree name="seach_DEPT_NO" limit="hr" selected="${DEPT_NO }"/>
				</td>
                <td>
					<spring:message code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME"/>
					<!--社号/姓名：-->
				</td>
				<td>
					<input type="text" name="seach_KEY" value="${KEY}" />
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
			        <input type="text" name="seach_FROM_TIME" class="date required" format="yyyy-MM-dd" readonly="true" value="${FROM_TIME}"/>
				    <a class="inputDateButton" href="javascript:;"></a>			   
			    </td>
                <td>
                    <spring:message code="hr.viewTranslate.title.PUBLIC_END_DATE"/>
                    <!--结束时间:-->
                </td>                			     
				<td>
				    <input type="text" name="seach_TO_TIME" class="date required" format="yyyy-MM-dd" readonly="true" value = "${TO_TIME}"/>
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
				<input type="checkbox" class="checkboxCtrl" group="searchPa" />
				<spring:message code="hr.viewUpgrade.title.CHECKALL"/>
				<!--全选-->
			</label>
			<ul>
				<li>
					<div class="subBar">
                            <div class="buttonActive"><div class="buttonContent"><button type="submit"><spring:message code="public.title.search"/><!-- 检索 --></button></div></div> 					
						    <div class="buttonActive">
						         <a class="update" onclick="return cancelSalaryAdjustTransValidateCallback('updatePaAdjustForm',DWZ.ajaxDone);" href="#" ><span><spring:message code="hr.viewTransactionTransViewList.title.CANCLETRANS"/><!-- 取消发令 --></span></a>
                            </div>
				    </div>	
				</li>
		</tr>
	</div>
	</form>
</div>

      	

<div class="pageContent">
<form name="updatePaAdjustForm" id="updatePaAdjustForm" method="post" action="/hrm/searchTransferOrder/cancelPaAdjustInBatch"
	  onsubmit="return cancelSalaryAdjustTransValidateCallback(this, navTabAjaxDone);">
 	<table class="table" width="120%" layoutH="150" nowrapTD="false">      
		<thead>
			<tr>
			    <th width="40">
			    	<spring:message code="hr.viewUpgrade.title.CHECKALL"/>
			    	<!--全选-->
			    </th>
				<th width="80">
					<spring:message code="hr.viewPersonalInfo.title.EMPID"/>
					<!--社号-->
				</th>
				<th width="80">
					<spring:message code="hr.viewPersonalInfo.title.LOCAL_NAME"/>
					<!--姓名-->
				</th>
				<th width="100">
					<spring:message code="hr.viewPersonalInfo.title.XIANBUMEN"/>
					<!--现部门-->
				</th>
				<th width="80">
					<spring:message code="hr.viewPersonalInfo.title.XIANZHIWEI"/>
					<!--现职位-->
				</th>
				<th width="120">
					<spring:message code="hr.viewPersonalInfo.title.POST_NAME"/>
					<!--职级名称(职务)-->
				</th>
			    <th width="40">
			    	<spring:message code="hr.viewPersonalInfo.title.XIANZHIJI"/>
					<!--现职级-->
			    </th>
			    <th width="100">
			    	<spring:message code="hr.viewPersonalInfo.title.XIANZHIZE"/>
					<!--现职责-->
			    </th>
			    <th width="80">
			    	<spring:message code="hr.viewPayrise.title.NOWPAY"/>
			    	<!--当前薪资-->
			    </th>
				<th width="100">
					<spring:message code="hr.viewPayrise.title.RETURNVALUE"/>
					<!--调整薪资-->
				</th>
				<th width="80">
					<spring:message code="hr.viewPayrise.title.ADJUST_REASON"/>
					<!--调整事由-->
				</th>
				<th width="80">
					<spring:message code="hr.viewPromote.title.EFFECTIVE_DATE"/>
					<!--生效日期-->
				</th>
				<th width="80">
					<spring:message code="hr.searchPaAdjust.title.PAAD_TYPE"/>
					<!--薪资调整类型-->
				</th>
				<th width="100">
					<spring:message code="hr.viewTransactionTransViewList.title.DEFINITELY_CUTTION_CODITIONS"/>
					<!--决裁情况-->
				</th>	
				<th width="80">
					<spring:message code="hr.viewTransactionTransViewList.title.HANDLERS"/>
					<!--操作者-->
				</th>	
				<th width="80">
					<spring:message code="hr.viewTransactionTransViewList.title.IT_BECOME_EFFECTIVE"/>
					<!--是否生效-->
				</th>							
			</tr>
		</thead>
	
		<tbody>
			<c:forEach items="${hrPaAdjustList}" var="paAdjust" varStatus="i">			
				<tr target="sid" rel="${paAdjust.EXP_INSIDE_NO}">
				    <td>
				        <input type="checkbox" id="searchPa" name="searchPa" value="${paAdjust.EXP_INSIDE_NO}" />
				    </td>
					<td>${paAdjust.EMPID}</td>
					<td>${paAdjust.LOCAL_NAME}</td>
					<td>${paAdjust.DEPTNAME}</td>
					<td>${paAdjust.POSITIONNAME}</td>
					<td>${paAdjust.POST_NAME}</td>
					<td>${paAdjust.POST_GRADE_NAME}</td>
					<td>${paAdjust.DUTYNAME}</td>
					<td style="text-align:right">
					    <fmt:formatNumber value="${paAdjust.PAY_STEP_BEFORE}" pattern="#,##0.00"/>
				    </td>
					<td style="text-align:right">
					    <fmt:formatNumber value="${paAdjust.RETURN_VALUE}" pattern="#,##0.00"/>
					</td>
					<td>${paAdjust.ADJUST_REASON}</td>
					<td>${paAdjust.START_DATE}</td>
					<td>${paAdjust.TRANS_CODE_NAME}</td>
					<td>
					    <c:forEach items="${paAdjust.affirmerList}" var="affirmer" varStatus="i">					  					           
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
					<td>${paAdjust.CREATE_NAME}</td>
					<td>
						<c:if test="${paAdjust.ACTIVITY_FLAG == 0 }" >
			             <img src="/resources/images/0.gif" style="cursor:hand"/>
			            </c:if>	  
			            <c:if test="${paAdjust.ACTIVITY_FLAG == 1 }" > 
			             <img src="/resources/images/a_1.gif" style="cursor:hand"/>
			            </c:if>
			            <c:if test="${paAdjust.ACTIVITY_FLAG == 2 }" >
			             	 &nbsp;<spring:message code="hr.viewTransactionTransViewList.title.FLAGVOTEDOWN"/><!-- 已否决 -->&nbsp;
			            </c:if>
			            <c:if test="${paAdjust.ACTIVITY_FLAG == 3 }" >
			                  &nbsp;<spring:message code="hr.viewTransactionTransViewList.title.CANCELLED"/><!-- 已取消 -->&nbsp;
			            </c:if>			            
			        </td> 						
		       </tr>			
			</c:forEach>			
		</tbody>	
	</table>
</form>	
	<c:set value="/hrm/searchTransferOrder/searchPaAdjust" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>
