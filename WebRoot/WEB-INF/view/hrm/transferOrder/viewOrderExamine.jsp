<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script type="text/javascript">

$(function (){
	$("#seach_ACTIVITY option").each(function(index, obj){
        if($(obj).val() == "${ACTIVITY}"){
            obj.selected = true ;
        }
    });
});


function cancelPluralityCallback(form, callback) {
	
	var $form=null;
	if($('#'+form).length>0)
		$form=$('#'+form);
	else
 		$form = $(form);
	
	if (!$form.valid()) {
		return false;
	}
	
    var checked=false;
	var ids= document.getElementsByName("c1");
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

    $form.attr("action","/hrm/transferOrder/cancelPlurality");
	
	$.ajax({
		type: form.method || 'POST',
		url:$form.attr("action"),
		data:$form.serializeArray(),
		dataType:"json",
		cache: false,
		success: function(data){ //请求成功后处理函数。
			if(data.statusCode=="200"){
				navTabSearch("transactionTransForm");
				alertMsg.correct(data.message);
				document.getElementById("seach_OrderType").value=data.type;
				document.getElementById("jiansuo").click();
				
				
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

function excelIsNot(){
	if($("#seach_OrderType").val()!=""){
		$("#excelIsNot").attr('href','/hrm/transferOrder/viewOrderExamineExcel?seach_OrderType='+$("#seach_OrderType").val());

	}else{
		alertMsg.error("请先选择调令类型");
	}
}
</script>
<div class="pageHeader">
	<form  action="/hrm/transferOrder/viewOrderExamine" rel="pagerForm" method="post"  onsubmit="return navTabSearch(this);"  id="transactionTransForm" name="transactionTransForm">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td >
					
					 <spring:message code="heran.examineType.title"/><!--  调令类型 -->
				</td>
				<td >
					<ait:SelectSyCodeByCpnyID name="seach_OrderType" parentNo="123313"
						cnpyID="${defaultCpny}" limit="all"  selected="${OrderType}"/>
				</td>
                <!--<td>
					
					 <spring:message code="hr.viewCondSql.title.transReason"/>  调令事由 
				</td>
				<td>
					<ait:selectSyCode name="diaolingShiyou" parentNo="123444" limit="all"/>
				</td>
				--><td>
					
					<spring:message code="hr.viewCondSql.title.transNo"/><!--  调令编号		 -->		
				</td>
				<td>
					<input type="text" value="${BIANHAO}" name="seach_BIANHAO" />
				</td>
				<td>
					<spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/>
					<!-- 部门： -->
				</td>	
				<td>
					<ait:deptTree name="seach_DEPTNO" limit="hr" selected="${DEPTNO }"/>
				</td>
                <td>
					<spring:message code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME"/>
					<!--社号/姓名：-->
				</td>
				<td>
					<input type="text" name="seach_EMPID" value="${EMPID}" />
				</td>					
			</tr>
			<tr>
                			
				<td>
					<spring:message code="hr.viewTransactionTransViewList.title.TRANSSTATUSCODE"/>
					<!--决裁状态:-->
				</td>					
				<td>
				    <ait:SelectSyCodeByCpnyID name="seach_ACTIVITY" parentNo="123489" cnpyID="${defaultCpny}" selected="${ACTIVITY}" limit="all"/>
                     <%--
                     <!-- 标记这条发令是否生效: 0未生效 1已生效2裁决否定3发令取消 -->
                    <select name="seach_ACTIVITY" id="seach_ACTIVITY">
                        <option value="">请选择</option>
                        <option value="0">未生效 </option>
                        <option value="1">已生效</option>
                        <option value="2">裁决否定 </option>
                        <option value="3">发令取消</option>
                    </select>
                    --%>
				 </td>
                <td>
                	<spring:message code="hr.viewTranslate.title.PUBLIC_START_DATE"/>
                    <!--开始时间:-->
                </td>			
			    <td>
			        <input type="text" name="seach_START_DATE" class="date required" format="yyyy-MM-dd" readonly="true" value="${START_DATE }"/>
				    <a class="inputDateButton" href="javascript:;"></a>			   
			    </td>
                <td>
                	<spring:message code="hr.viewTranslate.title.PUBLIC_END_DATE"/>
                    <!--结束时间:-->
                </td>                			     
				<td>
				    <input type="text" name="seach_END_DATE" class="date required" format="yyyy-MM-dd" readonly="true" value = "${END_DATE }"/>
				    <a class="inputDateButton" href="javascript:;"></a>
				</td> 			
				<td>
					
				</td>					
				<td>
				</td>		 
			</tr>
		</table>
		<div class="subBar">
			<ul>
				<li>
				<div class="buttonActive">
                            <div class="buttonContent"><button type="submit" id="jiansuo"><spring:message code="public.title.search"/><!-- 检索 --></button></div></div></li>
			</ul>
		</div>
	</div>
	
	</form>
</div>
<div class="pageContent">
<div class="formBar">
			<label style="float: left">
				<input type="checkbox" class="checkboxCtrl" group="c1" />
				<spring:message code="hr.viewUpgrade.title.CHECKALL"/>
				<!--全选-->
			</label>
			<ul>
				<li>
					<div class="subBar">
                             					
						    
						    <div class="buttonActive">
						         <a class="update" id="excelIsNot" onclick="excelIsNot();" href="#"  ><span><spring:message code="pa.insurance.title.excelExport"/><!--EXCEL导出--></a>
                            </div>
                            <div class="buttonActive">
						         <a class="update" onclick="return cancelPluralityCallback('cancelTransForm',DWZ.ajaxDone);" href="#" ><span><spring:message code="hr.viewTransactionTransViewList.title.CANCLETRANS"/><!-- 取消发令 --></span></a>
                            </div>
                            
				    </div>	
				</li>
			</ul>
	</div>
<form name="cancelTransForm" id="cancelTransForm" method="post" action="/hrm/transferOrder/cancelPlurality"
	  onsubmit="return cancelPluralityCallback(this, navTabAjaxDone);" rel="pagerForm" >
	  
 	<table class="table" width="100%" layoutH="209" nowrapTD="false">      
		<thead>
			<tr>
				<c:if test="${!empty listTitle}">
					<th width="40">
				    	<spring:message code="hr.viewUpgrade.title.CHECKALL"/>
				    	<!--全选-->
				    </th>
			    </c:if>
				<c:forEach items="${listTitle}" var="title" varStatus="i">
					<th>${title.CONTENT}</th>
				</c:forEach>
				<c:if test="${!empty listTitle}">
					<th width="100">
						<spring:message code="hr.viewTransactionTransViewList.title.DEFINITELY_CUTTION_CODITIONS"/>
						<!--决裁情况-->
					</th>
					<th width="80">
					<spring:message code="hr.viewTransactionTransViewList.title.IT_BECOME_EFFECTIVE"/>
					<!--是否生效-->
					</th>	
			    </c:if>
			</tr>
		</thead>
		<tbody>
				<c:forEach items="${insideList}" var="temp">  
				<tr target="sid" rel="${temp.EXP_INSIDE_NO}">  
					<td class="td_center">
					 <c:if test="${temp.ACTIVITY== 0 }" >
				        <input type="checkbox" id="c1" name="c1" value="${temp.EXP_INSIDE_NO}" />
				     </c:if>
				    </td>
				<c:forEach items="${temp}" var="map">          
					
					<c:choose>
						<c:when test="${map.key == 'EXP_INSIDE_NO'}"></c:when>
						<c:when test="${map.key == 'ACTIVITY'}"></c:when>
						<c:when test="${map.key == 'affirmerList'}"></c:when>
						<c:when test="${map.key == 'ROWNUM_'}"></c:when>
						<c:when test="${map.key == 'DEPTNO'}"></c:when>
						<c:when test="${map.key == 'CURRENT_AFFIRM_ID'}"></c:when>
						<c:when test="${map.key == 'AFFIRM_LEVEL'}"></c:when>
						<c:when test="${map.key == 'START_DATE'}"><td><fmt:formatDate  value="${map.value}" pattern="yyyy-MM-dd"/></td></c:when>
						<c:when test="${map.key == 'END_DATE'}"><td><fmt:formatDate  value="${map.value}" pattern="yyyy-MM-dd"/></td></c:when>
						<c:when test="${map.key == 'SAL_CALCULATE_DATE'}"><td><fmt:formatDate  value="${map.value}" pattern="yyyy-MM-dd"/></td></c:when>
						<c:when test="${map.key == 'POSITIVE_DATES'}"><td><fmt:formatDate  value="${map.value}" pattern="yyyy-MM-dd"/></td></c:when>
						<c:otherwise><td class="title">${map.value} </td> </c:otherwise>
					</c:choose>
					
					
				</c:forEach>
				<td>
					<c:forEach items="${temp.affirmerList}" var="affirmer" varStatus="i">					  					           
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
				<td>
			        <c:if test="${temp.ACTIVITY== 0 }" >
			             <%--<img src="/resources/images/a_1.gif" style="cursor:hand"/>
			           标记这条发令是否生效: 0未生效 1已生效2裁决否定3发令取消 --%>
			            		<img src="/resources/images/0.gif" title="<spring:message code='alert.message.approval_status_ing'/>" />
			            </c:if>
			            <c:if test="${temp.ACTIVITY== 1 }" >
			             <img src="/resources/images/1.gif" style="cursor:hand" title="<spring:message code='alert.message.approval_status_end'/>" />
			            </c:if>
			            <c:if test="${temp.ACTIVITY== 2}" >
                            <img src="/resources/images/0.gif" style="cursor:hand" title="<spring:message code='alert.message.approval_status_reject'/>" />
                        </c:if> 
                        <c:if test="${temp.ACTIVITY== 3}" >
			            	<img src="/resources/images/0.gif" style="cursor:hand" title="<spring:message code='alert.message.approval_status_cancel'/>" />
			            </c:if>
			        </td>	  
				</tr>
		    </c:forEach>
		</tbody>
	</table>
	<input type="hidden" value="${OrderType}" name="seach_biaohao" />
</form>
	<c:set value="/hrm/transferOrder/viewOrderExamine" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>
      	


