<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<c:set value="${defaultPersonId}" var="defaultPersonId"/>
<script type="text/javascript">
function validateApproveTransactionCallback(form,callback,flag) {
	
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
	var c1Value="";
	for(var i=0;i<ids.length;i++){
		if(ids[i].checked){
			checked=true;
			c1Value+=ids[i].value+",";
		}
	}
	if(!checked){
		alertMsg.info('<spring:message code="ess.trans.title.chooseFirstThenBatchOperation"/>');
		return false;
	}

    $form.attr("action","/ess/trans/approveTransactionTransInBatch?AFFIRM_FLAG="+flag);
	
    
    var seach_OrderType=$("#seach_OrderType").val();
    var seach_FROM_TIME=$("#seach_FROM_TIME").val();
    var seach_TO_TIME=$("#seach_TO_TIME").val();
    var seach_KEY=$("#seach_KEY").val();
    
    $.ajax({
		type: form.method || 'POST',
		url:$form.attr("action"),
		data:$form.serializeArray(),
		dataType:"json",
		cache: false,
		success: function(data){ //请求成功后处理函数。
			if(data.statusCode=="200"){
				navTabSearch("searchTransAffirmForm");
				alertMsg.correct(data.message);
				$("#seach_OrderType").val(seach_OrderType);
				$("#seach_FROM_TIME").val(seach_FROM_TIME);
				$("#seach_TO_TIME").val(seach_TO_TIME);
				$("#seach_KEY").val(seach_KEY);
				$("#diandian").click();
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

function approvalAction(url) {
	var $form=$("#searchTransAffirmForm");
    $.ajax({
        type: 'POST',
        url:url,
        data:$form.serializeArray(),
        dataType:"json",
        cache: false,
        success: function(data){ //请求成功后处理函数。
            if(data.statusCode=="200"){
            	$form.submit();
                alertMsg.correct(data.message);
            }else{
                alertMsg.error(data.message);
            }   
        }  ,
        error: DWZ.ajaxError
    });
    return false;
}
$(function (){
    var transCodeFromQuickMenu = "${transCodeFromQuickMenu}"; //퀵메뉴에서 받아온 발령유형코드값
    //if(transCodeFromQuickMenu == "123314"){
    if(transCodeFromQuickMenu != ""){
    	$("#seach_OrderType > option[value=${transCodeFromQuickMenu}]").attr("selected", "true");
        $("#diandian").trigger("click");
        //$("#searchTransAffirmForm").submit();
    }	
});

function pageFromSea(a){                         
		
	var seach_KEY=$("#seach_KEY",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_KEY",navTab.getCurrentPanel()).val();
	var seach_DEPT_NO=$("#seach_DEPT_NO",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_DEPT_NO",navTab.getCurrentPanel()).val();
	var seach_OrderType=$("#seach_OrderType",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_OrderType",navTab.getCurrentPanel()).val();
	var seach_DIAOLING_BIANHAO=$("#seach_DIAOLING_BIANHAO",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_DIAOLING_BIANHAO",navTab.getCurrentPanel()).val();
	var seach_STATUS_CODE=$("#seach_STATUS_CODE",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_STATUS_CODE",navTab.getCurrentPanel()).val();
	var seach_FROM_TIME=$("#seach_FROM_TIME",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_FROM_TIME",navTab.getCurrentPanel()).val();
	var seach_TO_TIME=$("#seach_TO_TIME",navTab.getCurrentPanel()).val()==undefined?"":$("#seach_TO_TIME",navTab.getCurrentPanel()).val();
	
	$("#pagerForm",navTab.getCurrentPanel()).attr("action", "/ess/trans/viewOrderExamine?seach_KEY="+seach_KEY+"&seach_DEPT_NO="+seach_DEPT_NO+"&seach_OrderType="+seach_OrderType+"&seach_DIAOLING_BIANHAO="+seach_DIAOLING_BIANHAO+"&seach_STATUS_CODE="+seach_STATUS_CODE+"&seach_FROM_TIME="+seach_FROM_TIME+"&seach_TO_TIME="+seach_TO_TIME);
}
</script>

<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/ess/trans/viewOrderExamine" method="post"
	      rel="pagerForm" name="searchTransAffirmForm" id="searchTransAffirmForm"  >
	
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td >
					<spring:message code="heran.examineType.title"/><!--  调令类型 -->
				</td>
				<td >
                    <ait:SelectSyCodeByCpnyID name="seach_OrderType" parentNo="123313"
						cnpyID="${defaultCpny}" limit="all" selected="${OrderType}"
						/>
				</td>
                <!--<td>
					调令事由
				</td>
				<td>
					<ait:selectSyCode name="diaolingShiyou" parentNo="123444" limit="all"/>
				</td>
				--><td>
					<spring:message code="hr.viewCondSql.title.transNo"/><!--  调令编号         -->
				</td>
				<td>
					<input type="" value="${ DIAOLING_BIANHAO}" name="seach_DIAOLING_BIANHAO"  id="seach_DIAOLING_BIANHAO"/>
				</td>
				<td>
					<spring:message code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME"/>
					<!--工号/姓名：-->
				</td>
				<td>
					<input type="text" name="seach_KEY" id="seach_KEY" value="${KEY}" />
				</td>
				<td>
					<spring:message code="hr.viewPersonalInfo.title.DEPTNAME"/>
					<!-- 部门： -->
				</td>	
				<td>
					<ait:deptTree name="seach_DEPT_NO" limit="hr" selected="${DEPT_NO }"/>
				</td>					
			</tr>
			<tr>
                <td>
                	<spring:message code="hr.viewTranslate.title.PUBLIC_START_DATE"/>
                    <!--开始时间:-->
                </td>			
			    <td>
			        <input type="text" name="seach_FROM_TIME" id="seach_FROM_TIME" class="date" format="yyyy-MM-dd" readonly="true" value="${FROM_TIME }"/>
				    <a class="inputDateButton" href="javascript:;"></a>			   
			    </td>
                <td>
                	<spring:message code="hr.viewTranslate.title.PUBLIC_END_DATE"/>
                    <!--结束时间:-->
                </td>                			     
				<td>
				    <input type="text" name="seach_TO_TIME" id="seach_TO_TIME" class="date" format="yyyy-MM-dd" readonly="true" value = "${TO_TIME }"/>
				    <a class="inputDateButton" href="javascript:;"></a>
				</td>
                <!--			
				<td>
				  <%--<ait:SelectSyCodeByCpnyID name="seach_ACTIVITY" parentNo="3528" cnpyID="${defaultCpny}" selected="${ACTIVITY}" limit="all"/>       --%><%--
				 	标记这条发令是否生效: 0未生效 1已生效2裁决否定3发令取消--%>
				 	<select name="seach_ACTIVITY">
				 		<option value="">请选择</option>
				 		<option value="0">未生效 </option>
				 		<option value="1">已生效</option>
				 		<option value="2">裁决否定 </option>
				 		<option value="3">发令取消</option>
				 	</select>
				</td>					
				-->
				
				<td>
					<spring:message code="ess.trans.title.affirmStatus"/><!-- 决裁状态 -->:
				</td>
				<td>
                     <ait:SelectSyCodeByCpnyID name="seach_STATUS_CODE" parentNo="3528" cnpyID="${defaultCpny}" selected="${STATUS_CODE}" limit="all"/>       
                <!-- 
                     <ait:SelectSyCodeByCpnyID name="seach_ACTIVITY" parentNo="123489" cnpyID="${defaultCpny}" selected="${ACTIVITY}" limit="all"/>
                -->
                </td>		 
			</tr>
		</table>
		<div class="subBar">
		    <%--  
					<label style="float: left">
						<input type="checkbox" class="checkboxCtrl" group="c1" />
						<spring:message code="hr.viewUpgrade.title.CHECKALL"/>
						<!--全选-->
					</label> 
					--%>
		            <ul>
						<li>
							<div class="buttonActive"><div class="buttonContent"><button type="submit" id="diandian"><spring:message code="public.title.search"/><!-- 检索 --></button></div></div> 					
						</li>
						</ul>
			</div>
	</div>
	
	</form>
</div>
<div class="pageContent">
<div class="formBar">
            <ul>
				<li>
					 	<div class="buttonActive">
						 <a class="update" onclick="validateApproveTransactionCallback('updateTransForm',DWZ.ajaxDone,'1')" href="#" ><span>
							<spring:message code="ess.trans.title.passInBatch"/><!--批量通过--></span></a>
			           </div>
			    </li>
			    <li>
						<div class="buttonActive">
						 <a class="update" onclick="validateApproveTransactionCallback('updateTransForm',DWZ.ajaxDone,'2')" href="#" ><span>
						<spring:message code="ess.trans.title.rejectInBatch"/><!--批量否决--></span></a>
			            </div>								
				</li>
			</ul>
</div>
<form name="updateTransForm" id="updateTransForm" method="post" action="/ess/trans/cancelTransactionTransInBatch"
	  onsubmit="return cancelTransValidateCallback(this, navTabAjaxDone);" >
 	<table class="table" width="100%" layoutH="231" nowrapTD="false">      
		<thead>
			<tr>
				<th>
					<input type="checkbox" class="checkboxCtrl" group="c1" />
				</th>
				<c:forEach items="${listTitle}" var="title" varStatus="i">
			
				
				<th>${title.CONTENT}</th>
				
				</c:forEach>
				<th>
					<spring:message code="ess.trans.title.affirmor"/><!--决裁者-->
				</th>
				<th>
					<spring:message code="ess.trans.title.affirmStatus"/><!-- 决裁状态 -->
				</th>
			</tr>
		</thead>
		<tbody>
			
				<c:forEach items="${insideList}" var="temp">  
				<tr>  
					<td>
				   <c:if test="${temp.ACTIVITY eq '0'}">
                        <c:forEach items="${temp.affirmerList}" var="affirmer" varStatus="i">
                            <c:if test="${affirmer.AFFIRMOR_ID eq defaultPersonId }" >
                                    <c:if test="${affirmer.AFFIRM_FLAG == 0}" >
                                         <input type="checkbox" id="c1" name="c1" value="${temp.EXP_INSIDE_NO}" />
                                    </c:if>
                                    <c:if test="${affirmer.AFFIRM_FLAG == 1}" >
                                         <input type="checkbox" id="c1" name="c1" value="${temp.EXP_INSIDE_NO}" style="display:none" />
                                    </c:if>
                                    <c:if test="${affirmer.AFFIRM_FLAG == 2}" >
                                        <input type="checkbox" id="c1" name="c1" value="${temp.EXP_INSIDE_NO}" style="display:none" />
                                    </c:if>
                                    <c:if test="${affirmer.AFFIRM_FLAG == 3}" >
                                        <input type="checkbox" id="c1" name="c1" value="${temp.EXP_INSIDE_NO}" style="display:none" />
                                    </c:if>
                            </c:if>
                        </c:forEach>
                    </c:if>
                    <c:if test="${temp.ACTIVITY eq '1'}">
                        <input type="checkbox" id="c1" name="c1" value="${temp.EXP_INSIDE_NO}" style="display:none" />
                    </c:if>
                    <c:if test="${temp.ACTIVITY eq '2'}">
                        <input type="checkbox" id="c1" name="c1" value="${temp.EXP_INSIDE_NO}" style="display:none" />
                    </c:if>
                    <c:if test="${temp.ACTIVITY eq '3'}">
                        <input type="checkbox" id="c1" name="c1" value="${temp.EXP_INSIDE_NO}" style="display:none" />
                    </c:if>
                    <%--
                                      <input type="checkbox" id="c1" name="c1" value="${temp.EXP_INSIDE_NO}" />
                   --%>
				  <c:forEach items="${temp}" var="map" varStatus="i">	
					 <c:if test="${map.key eq 'EXP_INSIDE_NO'}">
					 	 <c:forEach items="${temp}" var="map1" varStatus="i">	
					 	 	<c:if test="${map1.key eq 'AFFIRM_LEVEL'}">
					 			 <input type="hidden" id="${map.value}_AFFIRM_LEVEL" name = "${map.value}_AFFIRM_LEVEL" value="${map1.value}" />  
					 		</c:if>
					 		<c:if test="${map1.key eq 'affirmerList'}">
								<c:forEach items="${temp}" var="map3" varStatus="i">
									<c:if test="${map3.key eq 'AFFIRM_LEVEL'}">
											 <c:forEach items="${map1.value}" var="map2" varStatus="i" begin="${map3.value-1}" end="${map3.value-1}" step="1" >	
					 				 				<input type="hidden" id="${map.value}_HR_AFFIRM_NO" name = "${map.value}_HR_AFFIRM_NO" value="${map2.HR_AFFIRM_NO}" /> 
					 						</c:forEach>		
									</c:if>
								</c:forEach>					 		
					 		</c:if>
					 		<c:if test="${map1.key eq 'affirmerList'}">
								<c:forEach items="${temp}" var="map3" varStatus="i">
									<c:if test="${map3.key eq 'AFFIRM_LEVEL'}">
										<c:forEach items="${temp}" var="map6" varStatus="i">
											<c:if test="${map6.key eq 'affirmerList'}">
												<c:forEach items="${map1.value}" var="map2" varStatus="i" begin="${map3.value-1}" end="${map3.value-1}" step="1" >	
													<input type="hidden" id="${map.value}_AFFIRMOR_ID" name = "${map.value}_AFFIRMOR_ID" value="${map2.AFFIRMOR_ID}" /> 
												</c:forEach>
											</c:if>
										</c:forEach>
									</c:if>
								</c:forEach>					 		
					 		</c:if>
					 		<c:if test="${map1.key eq 'START_DATE'}">
					 			 <input type="hidden" id="${map.value}_START_DATE" name = "${map.value}_START_DATE" value="${map1.value}" />  
					 		</c:if>
					 		<c:if test="${map1.key eq 'PERSON_ID'}">
					 			 <input type="hidden" id="${map.value}_PERSON_ID" name = "${map.value}_PERSON_ID" value="${map1.value}" />  
					 		</c:if>
					 		<c:if test="${map1.key eq 'WORK_AREA'}">
					 			 <input type="hidden" id="${map.value}_WORK_AREA" name = "${map.value}_WORK_AREA" value="${map1.value}" />  
					 		</c:if>
					 		<c:if test="${map1.key eq 'DETAIL_HR_DIFF'}">
					 			 <input type="hidden" id="${map.value}_EMP_TYPE_CODE" name = "${map.value}_EMP_TYPE_CODE" value="${map1.value}" />  
					 		</c:if>
					 		<c:if test="${map1.key eq 'GRADE_LEVEL'}">
					 			 <input type="hidden" id="${map.value}_GRADE_LEVEL" name = "${map.value}_GRADE_LEVEL" value="${map1.value}" />  
					 		</c:if>
					 	</c:forEach>
					 </c:if>
					
				</c:forEach>
				        
				        
				        
				      
				</td>
				
				<c:forEach items="${temp}" var="map"> 
					
					<c:choose>
						<c:when test="${map.key == 'EXP_INSIDE_NO'}"></c:when>
						<c:when test="${map.key == 'ACTIVITY'}"></c:when>
													
						<c:when test="${map.key == 'affirmerList'}"></c:when>
						<c:when test="${map.key == 'ROWNUM_'}"></c:when>
						<c:when test="${map.key == 'DEPTNO'}"></c:when>
						<c:when test="${map.key == 'DEPTNO1'}"></c:when>
						<c:when test="${map.key == 'DEPTNO1'}"></c:when>
						<c:when test="${map.key == 'PERSON_ID'}"></c:when>
						<c:when test="${map.key == 'ADMIN_ID'}"></c:when>
						<c:when test="${map.key == 'CURRENT_AFFIRM_ID'}"></c:when>
						<c:when test="${map.key == 'AFFIRM_LEVEL'}"></c:when>
						
						
						
					
						
						<c:when test="${map.key == 'START_DATE'}"><td><fmt:formatDate  value="${map.value}" pattern="yyyy-MM-dd"/></td></c:when>
						<c:when test="${map.key == 'END_DATE'}"><td> <fmt:formatDate  value="${map.value}" pattern="yyyy-MM-dd"/></td></c:when>
						<c:when test="${map.key == 'SAL_CALCULATE_DATE'}"><td><fmt:formatDate  value="${map.value}" pattern="yyyy-MM-dd"/></td></c:when>
						<c:when test="${map.key == 'POSITIVE_DATES'}"><td><fmt:formatDate  value="${map.value}" pattern="yyyy-MM-dd"/></td></c:when>
						<c:otherwise><td>${map.value} </td> </c:otherwise>
						
					
					</c:choose>
					
					
				</c:forEach>  
				<td>
					
					<c:forEach items="${temp.affirmerList}" var="affirmer" varStatus="i">					    					       					      
						        <dt style="padding: 1px;">
							        <c:if test="${affirmer.AFFIRM_FLAG==0 }" >
								        <span style="color:blue;">
			                           ${affirmer.LOCAL_NAME}  
			                            </span>
			                            <c:choose>
			                            
									        <c:when test="${affirmer.AFFIRMOR_ID ==temp.ADMIN_ID&& affirmer.AFFIRM_LEVEL == temp.AFFIRM_LEVEL }">													
										      <fmt:setLocale value="ko_KR" />
										         <c:if test="${affirmer.HREF_FLAG == 1}" >
										         	
										         	<c:if test="${temp.START_DATE != null}">
												    	<fmt:parseDate value="${affirmer.START_DATE}" var="date" pattern="yyyy-MM-dd"/>
													</c:if>
													<c:if test="${temp.START_DATE == null}">
												    	<c:set var="date" value=""></c:set>
													</c:if>
													
													<a href="#" onclick="approvalAction('/ess/trans/approveTransactionTrans?HR_AFFIRM_NO=${affirmer.HR_AFFIRM_NO}&&START_DATE=<fmt:formatDate value="${date}" pattern="yyyy-MM-dd"/>&&EMPID=${hrExpInside.EMPID}&&PERSON_ID=${hrExpInside.PERSON_ID}&&AFFIRM_FLAG=1&&AFFIRM_LEVEL=${affirmer.AFFIRM_LEVEL}&&EXP_INSIDE_NO=${affirmer.EXP_INSIDE_NO}&&ORDERTYPE=${seach_OrderType}&&WORK_AREA=${temp.WORK_AREA}&&GRADE_LEVEL=${temp.GRADE_LEVEL}&&EMP_TYPE_CODE=${temp.DETAIL_HR_DIFF}');">
							                          <span style="color:blue;">&nbsp;<spring:message code="ess.trans.title.pass"/><!--通过--></span>
													</a>
													 <a href="#" onclick="approvalAction('/ess/trans/approveTransactionTrans?HR_AFFIRM_NO=${affirmer.HR_AFFIRM_NO}&&START_DATE=<fmt:formatDate value="${date}" pattern="yyyy-MM-dd"/>&&EMPID=${hrExpInside.EMPID}&&PERSON_ID=${hrExpInside.PERSON_ID}&&AFFIRM_FLAG=2&&AFFIRM_LEVEL=${affirmer.AFFIRM_LEVEL}&&EXP_INSIDE_NO=${affirmer.EXP_INSIDE_NO}&&ORDERTYPE=${seach_OrderType}');">
							                           <span style="color:blue;">&nbsp;<spring:message code="ess.trans.title.reject"/><!--否决--></span> 
													</a>
											     </c:if>
											     <c:if test="${affirmer.AFFIRM_FLAG != 1}" >
											       &nbsp;<spring:message code="ess.trans.title.notAffirmed"/><!--未决裁-->
											     </c:if>									
									        </c:when>									
									        <c:otherwise>									
									              &nbsp;<spring:message code="ess.trans.title.notAffirmed"/><!--未决裁-->						
									        </c:otherwise>									
									    </c:choose>				                            																	
									</c:if>
								</dt>
								<dt style="padding: 1px;">
									<c:if test="${affirmer.AFFIRM_FLAG==1}" >
										<span style="color:green;">&nbsp;${affirmer.LOCAL_NAME}</span> 
									    <span style="color:green;">&nbsp;<spring:message code="ess.trans.title.pass"/><!--通过--></span>
									</c:if>	
								</dt>
								<dt style="padding: 1px;">
									<c:if test="${affirmer.AFFIRM_FLAG==2}" >
									    <span style="color:red;">&nbsp;${affirmer.LOCAL_NAME}</span>
									    <span style="color:red;">&nbsp;<spring:message code="ess.trans.title.reject"/><!--否决--></span>
									</c:if>								
								</dt>	
							</c:forEach> 
					
					
				</td>
				<td>
                    <!--  
					<c:if test="${temp.ACTIVITY eq '0'}">
						未裁决
					</c:if>
					<c:if test="${temp.ACTIVITY eq '1'}">
						已裁决
					</c:if>
                     -->
                     <%--
                     ${temp.affirmerList}
                    <c:if test="${temp.ACTIVITY == 0}" >
                        <c:if test="${temp.CURRENT_AFFIRM_ID eq defaultPersonId}" >
                            <span style="color:red;"><spring:message code="ess.trans.title.notAffirmed"/><!--未决裁--></span>
                        </c:if>
                        <c:if test="${temp.CURRENT_AFFIRM_ID ne defaultPersonId}" >
                            <span style="color:green;"><spring:message code="ess.trans.title.affirmed"/><!--已决裁--></span>
                        </c:if>

                    </c:if>
                    <c:if test="${temp.ACTIVITY != 0 }" >
                        <span style="color:green;"><spring:message code="ess.trans.title.affirmed"/><!--已决裁--></span>
                    </c:if>
                    ${temp.affirmerList}:::${defaultPersonId}
                    --%>
                    
                    <c:if test="${temp.ACTIVITY eq '0'}">
                        <c:forEach items="${temp.affirmerList}" var="affirmer" varStatus="i">
                            <c:if test="${affirmer.AFFIRMOR_ID eq defaultPersonId }" >
                                    <c:if test="${affirmer.AFFIRM_FLAG == 0}" >
                                         <span style="color:red;"><spring:message code="ess.trans.title.notAffirmed"/><!--未决裁--></span>
                                    </c:if>
                                    <c:if test="${affirmer.AFFIRM_FLAG == 1}" >
                                         <span style="color:green;"><spring:message code="ess.trans.title.affirmed"/><!--已决裁--></span>
                                    </c:if>
                                    <c:if test="${affirmer.AFFIRM_FLAG == 2}" >
                                        <span style="color:green;"><spring:message code="ess.trans.title.affirmed"/><!--已决裁--></span>
                                    </c:if>
                                    <c:if test="${affirmer.AFFIRM_FLAG == 3}" >
                                        <span style="color:green;"><spring:message code="ess.trans.title.affirmed"/><!--已决裁--></span>
                                    </c:if>
                            </c:if>
                        </c:forEach>
                    </c:if>
                    <c:if test="${temp.ACTIVITY eq '1'}">
                        <span style="color:green;"><spring:message code="ess.trans.title.affirmed"/><!--已决裁--></span>
                    </c:if>
                    <c:if test="${temp.ACTIVITY eq '2'}">
                        <span style="color:green;"><spring:message code="ess.trans.title.affirmed"/><!--已决裁--></span>
                    </c:if>
                    <c:if test="${temp.ACTIVITY eq '3'}">
                        <span style="color:green;"><spring:message code="ess.trans.title.affirmed"/><!--已决裁--></span>
                    </c:if>
				</td>
			</tr>
		    </c:forEach>

		</tbody>
	</table>
</form>
<c:set value="/ess/trans/viewOrderExamine" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination2.jsp"%>
	
</div>
      	


