<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function updateLowSalaryValidateCallback(form, callback,flag) {
	
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

    $form.attr("action","/pa/salary/updateLowSalaryBatch");
	
	$.ajax({
		type: form.method || 'POST',
		url:$form.attr("action"),
		data:$form.serializeArray(),
		dataType:"json",
		cache: false,
		//success: callback || DWZ.ajaxDone,
		success: function(data){ //请求成功后处理函数。
			if(data.statusCode=="200"){
				navTabSearch("searchLowSalaryForm");
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

function f_cancellowadjust(callback, pa_month,person_id,adjust_bef){
	
	if (confirm ('<spring:message code="pa.low.adjust.chexiaotiaozheng"/>')){	
		$.ajax({
			type: 'POST',
			url: '/pa/salary/cancellowadjust',
			data: [{ name: 'pa_month', value: pa_month },
			       { name: 'person_id', value: person_id },
			       { name: 'adjust_bef', value: adjust_bef }],
			dataType:"json",
			cache: false,
			success: callback || DWZ.ajaxDone,
			error: DWZ.ajaxError
		});
		
	}
}

</script>

<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);"
		action="/pa/salary/viewLowSalaryHistoryList" method="post"
		id="searchLowSalaryForm" name="searchLowSalaryForm" rel="pagerForm">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						<!-- 工资月 -->
						 <spring:message code="ar.viewarprogress.title.gongziyue"/>
					</td>
					<td>
						<ait:date yearName="seach_paYear" monthName="seach_paMonth" yearSelected="${paYear}" monthSelected="${paMonth}"/>
					</td>
				
					<td>
						<!-- 部门： --> <spring:message
							code="hr.viewPersonalInfo.title.DEPTNAME" />
					</td>
					<td>
						<ait:deptList name="seach_DEPT_NO" id="viewLowSalaryHistoryList_seachDept" limit="pa"/>
						<ait:deptTreeIcon name="seach_DEPT_NO" limit="pa" id="viewLowSalaryHistoryList_seachDept" selected="${DEPT_NO}"/>
						
						<%--
						<ait:deptTree name="seach_DEPT_NO" limit="pa"
							selected="${DEPT_NO }" />
						--%>
					</td>
					<td>
						<!--工号/姓名：--> <spring:message
							code="hr.viewContractByInsert.title.EMPIDANDLOCALNAME" />
					</td>
					<td><input type="text" name="seach_KEY" value="${KEY}" />
					</td>
					<td>
						<!--提交状态:--> <spring:message code="pa.low.adjust.submitstatus" />
					</td>
					<td>
						<select id='seach_ACTIVITY' name='seach_ACTIVITY'>
							<option value='' <c:if
								test="${ACTIVITY == null }">
								selected = "selected"
							</c:if>>
							<!-- 全部 --><spring:message code="ar.viewarcardrecord.title.quanbu"/></option>
							
							<option value='1' <c:if
								test="${ACTIVITY == '1' }">
								selected = "selected"
							</c:if>><spring:message code="pa.low.adjust.yitijiao" /></option>
							
							<option value='0' <c:if
								test="${ACTIVITY == '0' }">
								selected = "selected"
							</c:if>><spring:message code="pa.low.adjust.weitijiao" /></option>
						</select>
						
					</td>
				</tr>
			</table>
		</div>
		<div class="formBar">
			<tr>
				<label style="float: left"> <input type="checkbox"
					class="checkboxCtrl" group="c1" /> <spring:message
						code="hr.viewUpgrade.title.CHECKALL" /> <!--全选--> </label>
				<ul>
					<li>
						<div class="subBar">
							<div class="buttonActive">
								<div class="buttonContent">
									<button type="submit">
										<spring:message code="public.title.search" />
										<!-- 检索 -->
									</button>
								</div>
							</div>
						</div>
					</li>
			</tr>
		</div>
	</form>
</div>



<div class="pageContent">
	<form name="updateLowSalaryForm" id="updateLowSalaryForm" method="post"
		action="/pa/salary/updateLowSalary"
		onsubmit="return updateLowSalaryValidateCallback(this, navTabAjaxDone);">
		
		<input name="PA_MONTH2" type="hidden" id="PA_MONTH2" value="${paYear}${paMonth}"/>
		<input name="DEPT_NO2" type="hidden" id="DEPT_NO2" value="${DEPT_NO}"/>
		<input name="KEY2" type="hidden" id="KEY2" value="${KEY}"/>
		<input name="ADJUST_SUB2" type="hidden" id="ADJUST_SUB2" value="${ADJUST_SUB}"/>
		
		<table class="table" width="100%" layoutH="150" nowrapTD="false">
			<thead>
				<tr>
					<th width="100"><spring:message
							code="hr.viewPersonalInfo.title.DEPTNAME" /> <!--部门-->
					</th>
					<th width="80">
						<spring:message code="hr.viewPersonalInfo.title.EMPID" /> <!--工号-->
					</th>
					<th width="80"><spring:message
							code="hr.viewPersonalInfo.title.LOCAL_NAME" /> <!--姓名-->
					</th>
					
					<th width="80"><spring:message
							code="pa.low.adjust.fulidiqu" /> <!--福利地区-->
					</th>
					<th width="120"><spring:message
							code="pa.low.adjust.gongzizhifuyue" /> <!--工资支付月-->
					</th>
					<th width="120">
						应出勤天数
					</th>
					<th width="120">
						实际出勤天数
					</th>
					<th width="40"><spring:message
							code="pa.low.adjust.benyuebingjiatianshu" /> <!--本月病假天数-->
					</th>
					<%--TA法人没有补扣 --%>
					<c:if test="${defaultCpny ne 'SST'}">
						<th width="80"><spring:message
								code="pa.low.adjust.baoxianbukoujine" /> <!--保险补扣金额-->
						</th>
					</c:if>
					<th width="100">
						<spring:message code="pa.low.adjust.benyueyingfagongzi" />
						<!--本月应发工资-->
					</th>
					<th width="140"><spring:message
							code="pa.low.adjust.benyueshifafagongzi" /> <!--本月实发工资-->
					</th>
					<th width="140"><spring:message
							code="pa.low.adjust.zuidigongzibiaozhun" /> <!--最低工资标准-->
							(80%)
					</th>
					<th width="100"><spring:message
							code="pa.low.adjust.zuidigongzibiaozhun" /> <!--最低工资标准-->
							(100%)
					</th>
					<th width="160"><spring:message
							code="pa.low.adjust.xitongjisuantiaozhengjine" />
						<!--系统计算调整金额-->
					</th>
					<th width="80"><spring:message
							code="pa.low.adjust.shijitiaozhengjine" /> <!--实际调整金额-->
					</th>
					<th width="80"><!--调整后实发工资--> 
						<spring:message code="pa.low.adjust.tiaozhenghoushifagongzi" />
					</th>
					<th width="80"><!--提交状态:--> 
						<spring:message code="pa.low.adjust.submitstatus" />
					</th>
					<th width="80"><!-- 备注 -->
						<spring:message code="ar.viewarcardrecord.title.beizhu"/>
					</th>
				</tr>
			</thead>

			<tbody>
				<c:forEach items="${paLowAdjustList}" var="paLowAdjust"
					varStatus="i">
					<tr target="PERSON_ID" rel="${paLowAdjust.PERSON_ID}">
						
						<td>${paLowAdjust.DEPT_NAME}</td>
						<td>${paLowAdjust.EMPID}</td>
						<td>${paLowAdjust.LOCAL_NAME}</td>
						<td>${paLowAdjust.FULIDIQU}</td>
						<td>${paLowAdjust.PA_MONTH}</td>
						<td>${paLowAdjust.SCHEDULED_DAYS}</td>
						<td>${paLowAdjust.ACTUAL_WORK_DAYS}</td>
						<td>${paLowAdjust.BENYUEBINGJIATIANSHU}</td>
						<c:if test="${defaultCpny ne 'SST'}">
							<td>${paLowAdjust.BAOXIANBUKOUJINE}</td>
						</c:if>
						<td>${paLowAdjust.BENYUEYINGFAGONGZI}</td>
						<td>${paLowAdjust.TIAOZHENGQIANSHIFA}</td>
						<td>${paLowAdjust.ZUIDIGONGZIBIAOZHUN1}</td>
						<td>${paLowAdjust.ZUIDIGONGZIBIAOZHUN2}</td>
						<td>${paLowAdjust.XITONGJISUANTIAOZHENGJINE}</td>
						<td>
							<c:if
								test="${paLowAdjust.ACTIVITY == '1' }">
								${paLowAdjust.SHIJITIAOZHENGJINE}
							</c:if>
							<c:if
								test="${paLowAdjust.ACTIVITY != '1' }">
								
							</c:if>
						</td>
						<td>
							${paLowAdjust.NET_PAY}
						</td>
						<td>
							<c:if
								test="${paLowAdjust.ACTIVITY == '1' }">
								<spring:message code="pa.low.adjust.yitijiao" />
							</c:if>
							<c:if
								test="${paLowAdjust.ACTIVITY != '1' }">
								<spring:message code="pa.low.adjust.weitijiao" />
							</c:if>
						</td>
						<td>
							${paLowAdjust.CONTENT}
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</form>
	<c:set value="/pa/salary/viewLowSalaryHistoryList"
		var="pageUrl" />
	<%@ include file="/WEB-INF/view/inc/initPagination.jsp"%>
</div>