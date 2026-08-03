<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script type="text/javascript">
<!--
function validateBatchEalCountCallback(form,callback) {	
	var $form = $("#eatMealCountBatchForm");
	if (!$form.valid()) {
		return false;
	}
	var checked = false ;
	$form.find(":checkbox[id='c1']").each(function(index, checkBoxObj){	    
	if(checkBoxObj.checked){
	   checked = true ;      
	  }	    
	  });
	
	if(!checked){
	 	alertMsg.error('<spring:message code="alert.message.ess.infoApply.choosePersonFirst"/>');
		return false;
	}
	$form.find(":checkbox[id='c1']").each(function(index, checkBoxObj){
	    if(checkBoxObj.checked){
	      	checked = true ;
	      	var personId = $(checkBoxObj).val() ;	
		  	if($form.find("[name='"+personId+"_FROM_DATE']").val()==''){
				alertMsg.error('<spring:message code="alert.message.ess.infoApply.startDateTimeIsMust"/>');
				$form.find("[name='"+personId+"_FROM_DATE']").focus();
				checked=false;
		   	}
		  	if($form.find("[name='"+personId+"_TO_DATE']").val()!=''){	 
			  if($form.find("[name='"+personId+"_FROM_DATE']").val()>$form.find("[name='"+personId+"_TO_DATE']").val())
				{
				   alertMsg.error('<spring:message code="alert.message.ess.infoApply.startTimeNotLaterThanEndTime"/>');
				   $form.find("[name='"+personId+"_FROM_DATE']").focus();
				  checked=false;
				}
		  	}
			//结束日期、开始日期时间比较，结束日期不能早于开始日期。
	      	var fromDate = $form.find("[name='"+personId+"_FROM_DATE']").val();
	      	var toDate = $form.find("[name='"+personId+"_TO_DATE']").val();
	      	//如果结束日期为空，默认为当天日期
	      	if(toDate == ''){
	      		toDate = fromDate;
		    }
      		var fromtime = fromDate + " 01:01:00";
      		var totime = toDate + " 01:01:00";
      		<%--
      		//系统时间
	      	var todayDate = $form.find("seach_TODAY_DATE").val();
      		var todaytime = todayDate + " 01:01:00";
      		//只允许添加系统时间和之后的食堂刷卡信息，已经过去的日期不允许再添加
      		if(comptime(fromtime,todaytime)!=1){
      			alertMsg.error('只能添加今天和晚于今天的食堂刷卡信息，已过去的日期不允许添加！');
      			checked=false;
      		}--%>
      		//开始日期不得晚于结束日期
      		if(comptime(fromtime,totime)!=1){
      			alertMsg.error('<spring:message code="alert.message.ess.infoApply.startTimeCanNotLaterThanEndTime"/>');
      			checked=false;
      		}
	    }
	  });
	
	if(checked){		
		if (confirm ('<spring:message code="alert.message.ess.infoApply.areYouSureToApply"/>')){		
			$.ajax({
				type: form.method || 'POST',
				url:$form.attr("action"),
				data:$form.serializeArray(),
				dataType:"json",
				cache: false,
				success: function(data){ //请求成功后处理函数。
					if(data.statusCode=="200"){
						navTabSearch("searchEatCountBatchForm");
						alertMsg.correct(data.message);
					}else{
						if(data.result=="-2"){
							alertMsg.info(data.message);
						}else if(data.result=="-3"){
							alertMsg.info(data.message);
						}else if(data.result=="-12"){
							alertMsg.info("员工"+data.message+"的刷卡信息存在，且信息锁定，不允许再添加！");
						}else{
							alertMsg.error(data.message);
						}
					}   
		   	 	}  ,
				error: DWZ.ajaxError
			});			
	    	return false;
		}
	}
	return false ;
}

function fillItemEatCount(){
  var mealFromDate = document.getElementById("mealFromDate").value; 
  var mealToDate = document.getElementById("mealToDate").value;
  var mealcount = document.getElementById("mealCount").value; 
  var mealRemark = document.getElementById("mealRemark").value; 
  
  
  var $form = $("#eatMealCountBatchForm");
  $form.find(":checkbox[id='c1']").each(function(index, checkBoxObj){
  if(checkBoxObj.checked){
      var personId = $(checkBoxObj).val() ;
         $form.find("[name='"+personId+"_FROM_DATE']").attr("value",mealFromDate);
         $form.find("[name='"+personId+"_TO_DATE']").attr("value",mealToDate);
         $form.find("select[name='"+personId+"_MEAL_COUNT']").attr("value",mealcount);
         $form.find("[name='"+personId+"_MEAL_REMARK']").attr("value",mealRemark);
  }
  });
}

//比较时间 格式 yyyy-mm-dd hh:mi:ss
function comptime(beginTime,endTime){
	var beginTimes=beginTime.substring(0,10).split('-');
	var endTimes=endTime.substring(0,10).split('-');
	
	beginTime=beginTimes[1]+'-'+beginTimes[2]+'-'+beginTimes[0]+' '+beginTime.substring(10,19);
	endTime=endTimes[1]+'-'+endTimes[2]+'-'+endTimes[0]+' '+endTime.substring(10,19);
	
	var a =(Date.parse(endTime)-Date.parse(beginTime))/3600/1000;

	if(a<0){
		return -1;
	}else if (a>=0){
		return 1;
	}else{
		return 'exception'
	}
}
//-->
</script>

<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/ar/attendanceSettings/viewArEatCountBatchPersonList" method="post" 
	      rel="pagerForm" id="searchEatCountBatchForm" name="searchEatCountBatchForm">
	 	<input id="seach_DATA_FLAG" name="seach_DATA_FLAG" value="Y" type="hidden"/>
		<div class="searchBar">
		<table class="searchContent">
			<tr>			
				<td><!-- 部门 -->
					 <spring:message code="public.title.deptName"/>:
				</td>	
				<td>
					<ait:deptTree name="seach_DEPT_NO" limit="ar" selected="${DEPT_NO }"/>
				</td>
                <td><!--关键字-->
                    <spring:message code="ess.infoApply.title.kewWord"/>:
				</td>
				<td>
					<input type="text" name="seach_KEY" value="${KEY}" />
				</td>
                <td><!--在职区分--> 
					在职区分:
                </td>			
			    <td>
			        <ait:SelectSyCodeByCpnyID parentNo="15118" cnpyID="${defaultCpny}" name="seach_EMP_OFFICE" limit="all"/>				   
			    </td>
				<td><!-- 动态组 -->
					<spring:message code="ar.addempshift.title.dynamicgroup"/>:
                </td>                			     
				<td>
				    <select name="seach_GROUP_NO" id="seach_GROUP_NO">
						<option value=""><!-- 全部 -->
							<spring:message code="ar.viewarcardrecord.title.quanbu"/>
						</option>
						<c:forEach items="${dynamicGroupList}" var="groupList">
							<option value="${groupList.GROUP_NO}" <c:if test="${groupList.GROUP_NO eq GROUP_NO}">selected</c:if>>${groupList.GROUP_NAME}</option>
						</c:forEach>
					</select>
				</td> 									
			</tr>
			</table>
	    </div>
		<div class="formBar">
			<tr>
				<ul>
					<li>
						<div class="subBar">
	                        <div class="buttonActive">
		                        <div class="buttonContent">
		                        	<button type="submit"><!-- 检索 -->
		                        		<spring:message code="public.title.search"/>
		                            </button>
								</div>
							</div> 					
							<div class="buttonActive">
							    <a class="update" onclick="return validateBatchEalCountCallback('eatMealCountBatchForm',DWZ.ajaxDone);" href="#" >
							    	<span><!--申请-->
							        	<spring:message code="ess.infoApply.title.apply"/>
									</span>
							    </a>
							    <a class="update" onclick="fillItemEatCount();" href="#" >
							    	<span><!--填充-->
							        	<spring:message code="ess.infoApply.title.fillItem"/>
							        </span>
								</a>
							</div>
					    </div>	
					</li>
				</ul>
			</tr>
		</div>
	</form>
</div>	

<div class="pageContent" style="padding:5px;">
     <form style="margin:0px;padding:0px;" name="eatMealCountBatchForm" id="eatMealCountBatchForm" method="post" action="/ar/attendanceSettings/addBatchEatCount" 
           class="pageForm required-validate" onsubmit="return validateBatchEalCountCallback(this,navTabAjaxDone);">
           
	<table class="tablea" width="98%" layoutH="110">
		<thead>
			<tr>
			    <th width="40">
			    	<input type="checkbox" class="checkboxCtrl" group="c1" />
			    </th>
				<th width="80"><!--工号-->
					<spring:message code="public.title.empId"/>
				</th>
				<th width="80"><!--姓名-->
					<spring:message code="public.title.name"/>
				</th>
				<th width="80"><!--职岗位-->
					<spring:message code="public.title.positionName"/>
				</th>
				<th width="80"><!--在职区分-->
					在职区分
				</th>
				<th width="180"><!-- 开始日期 -->
					<spring:message code="public.title.startDate"/>
				</th>			
				<th width="180"><!-- 结束日期 -->
					<spring:message code="public.title.endDate"/>
				</th>
				
				<th width="100"><!--吃饭次数-->
					吃饭次数
				</th>
				<th width="100"><!--备注-->
					备注
				</th>
			</tr>
		</thead>	
	    <tr>
		    <td width="40" colspan="5"><!--点击填充按钮将按此行数据对选中的行数据进行填充-->
		    	<spring:message code="ess.infoApply.title.fillItemIntroduction"/>
		    	<font color="red" size="1">(注：只能添加今天与今天之后的数据。)</font>
		    </td>
			<td width="180"><!--开始日期-->
			    <input type="text" id="mealFromDate" name="mealFromDate" class="date" format="yyyy-MM-dd" readonly="true"/>
			    <a class="inputDateButton" href="javascript:;"><!-- 选择 -->
			    	<spring:message code="public.title.choose"/>
			    </a>
            </td>
			<td width="180"><!--结束日期-->
			    <input type="text" id="mealToDate" name="mealToDate" class="date" format="yyyy-MM-dd" readonly="true"/>
			    <a class="inputDateButton" href="javascript:;"><!-- 选择 -->
			    	<spring:message code="public.title.choose"/>
			    </a>
			</td>                                
			<td width="100"><!--吃饭次数-->
				<select name="mealCount" id="mealCount">
					<option value="1">1</option>
					<option value="2">2</option>
					<option value="3">3</option>
					<option value="4">4</option>
				</select>
			</td>
			<td width="80" colspan="2"><!--备注-->
			   <textarea id="mealRemark" name="mealRemark" cols="40" rows="1"></textarea>
			</td>							
		</tr>		        
		<tbody>		 
			<c:forEach items="${personList}" var="person" varStatus="i">			
				<tr target="sid" rel="${person.PERSON_ID}">
				    <td>
				        <input type="checkbox" id="c1" name="c1" value="${person.PERSON_ID}" />
				    </td>
					<td>${person.EMPID}</td>
					<td>${person.LOCAL_NAME}</td>
					<td>${person.POSITION_NAME}</td>
					<td>${person.EMP_OFFICE}</td>
					<td>
					    <input type="text" id="${person.PERSON_ID}_FROM_DATE" name="${person.PERSON_ID}_FROM_DATE" class="date" format="yyyy-MM-dd" readonly="true"/>
				        <a class="inputDateButton" href="javascript:;"><!-- 选择 -->
				        	<spring:message code="public.title.choose"/>
				        </a>
				    </td>
					<td>
					    <input type="text" id="${person.PERSON_ID}_TO_DATE" name="${person.PERSON_ID}_TO_DATE" class="date" format="yyyy-MM-dd" readonly="true"/>
				        <a class="inputDateButton" href="javascript:;"><!-- 选择 -->
				        	<spring:message code="public.title.choose"/>
				        </a>
				    </td>
							    
					<td>
						<select name="${person.PERSON_ID}_MEAL_COUNT" id="${person.PERSON_ID}_MEAL_COUNT">
							<option value="1">1</option>
							<option value="2">2</option>
							<option value="3">3</option>
							<option value="4">4</option>
						</select>					        
					</td>
					<td>
						<textarea id="${person.PERSON_ID}_MEAL_REMARK" name="${person.PERSON_ID}_MEAL_REMARK" cols="40" rows="1"></textarea>
					</td>			
				</tr>			
			</c:forEach>
		</tbody>
	</table>
	<div id="eatMealCountBatchaddView" style="border:10;overflow:auto;bottom:10;right:100;width:300;z-index:1;"></div>	
	</form>
	<c:set value="/ar/attendanceSettings/viewArEatCountBatchPersonList" var="pageUrl"/>
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>	
</div>
