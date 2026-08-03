<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function cancelPluralityTransValidateCallback(form, callback) {
	
	var $form=null;
	if($('#'+form).length>0)
		$form=$('#'+form);
	else
 		$form = $(form);
	
	if (!$form.valid()) {
		return false;
	}
	
    var checked=false;
	var ids= document.getElementsByName("searchPlu");
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

    $form.attr("action","/hrm/searchTransferOrder/cancelPluralityInBatch");
	
	$.ajax({
		type: form.method || 'POST',
		url:$form.attr("action"),
		data:$form.serializeArray(),
		dataType:"json",
		cache: false,
		success: function(data){ //请求成功后处理函数。
			if(data.statusCode=="200"){
				navTabSearch("searchPluForm");
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
	<form onsubmit="return navTabSearch(this);" action="/hrm/searchTransferOrder/searchPlurality" 
	rel="pagerForm" method="post" name="searchPluForm" id="searchPluForm">
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
				<td>
					<spring:message code="hr.viewTranslate.title.PART_TIME_TYPE"/>
					<!--兼职类型:-->
				</td>									 
				<td>
				     <ait:SelectSyCodeByCpnyID name="seach_TRANS_CODE" parentNo="1361" cnpyID="${defaultCpny}" selected="${TRANS_CODE}" limit="all"/>       
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
				<input type="checkbox" class="checkboxCtrl" group="searchPlu" />
				<spring:message code="hr.viewUpgrade.title.CHECKALL"/>
				<!--全选-->
			</label>
			<ul>
				<li>
					<div class="subBar">
                            <div class="buttonActive"><div class="buttonContent"><button type="submit"><spring:message code="public.title.search"/><!-- 检索 --></button></div></div> 					
						    <div class="buttonActive">
						         <a class="update" onclick="return cancelPluralityTransValidateCallback('updatePluForm',DWZ.ajaxDone);" href="#" ><span><spring:message code="hr.viewTransactionTransViewList.title.CANCLETRANS"/><!-- 取消发令 --></span></a>
                            </div>
				    </div>	
				</li>
		</tr>
	</div>
	</form>
</div>

      	

<div class="pageContent">
<form name="updatePluForm" id="updatePluForm" method="post" action="/hrm/searchTransferOrder/cancelPluralityInBatch"
	  onsubmit="return cancelPluralityTransValidateCallback(this, navTabAjaxDone);">
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
			    	<spring:message code="hr.viewTranslate.title.THE_TYPE"/>
			    	<!--发令类型-->
			    </th>
				<th width="100">
					<spring:message code="hr.viewPersonalInfo.title.PLU_DEPTNAME"/>
					<!--兼职部门-->
				</th>
				<th width="80">
					<spring:message code="hr.viewPersonalInfo.title.PLU_POSITINO_NAME"/>
					<!--兼职职(岗)位-->
				</th>
				<th width="120">
					<spring:message code="hr.viewTranslate.title.PUBLIC_START_DATE"/>
					<!--开始日期-->
				</th>
			    <th width="100">
			    	<spring:message code="hr.viewTranslate.title.PART_TIME_CONTENTS"/>
			    	<!--兼职事由-->
			    </th>		
			    <th width="100">
			    	<spring:message code="hr.searchPlurality.title.CANCEL_DATE"/>
			    	<!--兼职解除日期-->
			    </th>		    
				<th width="100">
					<spring:message code="hr.viewTranslate.title.PART_TIME_CONTENTS_EXPLAIN"/>
					<!--兼职解释事由-->
				</th>
				<th width="80">
					<spring:message code="hr.viewTranslate.title.PART_TIME_TYPE"/>
					<!--兼职类型-->
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
			<c:forEach items="${hrPluralityList}" var="plurality" varStatus="i">			
				<tr target="sid" rel="${plurality.EXP_INSIDE_NO}">
				    <td>
				        <input type="checkbox"  name="searchPlu" value="${plurality.EXP_INSIDE_NO}" />
				    </td>
					<td>${plurality.EMPID}</td>
					<td>${plurality.LOCAL_NAME}</td>
					<td>${plurality.DEPTNAME}</td>
					<td>${plurality.POSITIONNAME}</td>
					<td>${plurality.POST_NAME}</td>
					<td>${plurality.POST_GRADE_NAME}</td>
					<td>${plurality.DUTYNAME}</td>
					<td>${plurality.RELATION_EXP_INSIDE_NO}
						<c:if test="${plurality.RELATION_EXP_INSIDE_NO ==null}"><spring:message code="hr.viewTranslate.title.PART_TIME_JOB"/><!-- 兼职 --></c:if>
						<c:if test="${plurality.RELATION_EXP_INSIDE_NO != null}"><spring:message code="hr.searchPlurality.title.CANCEL_PLURALITY"/><!-- 解除兼职 --></c:if>
					</td>
					<td>${plurality.PLU_DEPT_NAME}</td>
					<td>${plurality.PLU_POSITION_NAME}</td>
					<td>${plurality.START_DATE}</td>
					<td>${plurality.PLU_REASON}</td>
					<td>${plurality.END_DATE}</td>
					<td>${plurality.REMARK}</td>
					<td>${plurality.TRANS_CODE_NAME}</td>
					<td>
					    <c:forEach items="${plurality.affirmerList}" var="affirmer" varStatus="i">					  					           
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
					<td>${plurality.CREATE_NAME}</td>
					<td>	    
			            <c:if test="${plurality.ACTIVITY_FLAG == 1 }" >
			             <img src="/resources/images/a_1.gif" style="cursor:hand"/>
			            </c:if>
			            <c:if test="${plurality.ACTIVITY_FLAG == 0 }" >
			             <img src="/resources/images/0.gif" style="cursor:hand"/>
			            </c:if>
			            <c:if test="${plurality.ACTIVITY_FLAG == 2 }" >
			            	&nbsp;<spring:message code="hr.viewTransactionTransViewList.title.CANCELLED"/><!-- 已取消 -->&nbsp;
			            </c:if>			            
			        </td> 						
		       </tr>			
			</c:forEach>			
		</tbody>	
	</table>
</form>	
	<c:set value="/hrm/searchTransferOrder/searchPlurality" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>
