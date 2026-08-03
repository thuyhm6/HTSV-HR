<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function validateCallback_updatecycleparamview(form, callback) {
	
	if($("#START_DATE").val() > $("#END_DATE").val()){
		// 开始日期不能大于结束日期 
		alertMsg.error("<spring:message code='ar.alert.message.startDate_endDate'/>");
		return false;
	}
	
	var $form = $(form);
	
	if (!$form.valid()) {
		return false;
	}
	
	$.ajax({
		type: form.method || 'POST',
		url:$form.attr("action"),
		data:$form.serializeArray(),
		dataType:"json",
		cache: false,
		success: callback || DWZ.ajaxDone,
		error: DWZ.ajaxError
	});
	
	return false;
}
</script>
<div class="pageContent">
	<form method="post" action="/ar/attendanceSettings/updateCycleParamInfo" class="pageForm required-validate" onsubmit="return validateCallback_updatecycleparamview(this,dialogAjaxDone);">
		<div class="pageFormContent nowrap" layoutH="60">
			<input type="hidden" name="NO" value="${cycleParamInfo.PARAM_NO}"/>
			
			<dl>
				<dt><!-- 区间 --><spring:message code="ar.viewcycleparameter.title.qujian"/></dt>
				<dd>
					<select class="combox" name="STAT_NO">
						<c:forEach items="${cycleList}" var="cycle">
							<option value="${cycle.STAT_NO}" <c:if test="${cycleParamInfo.STAT_NO eq cycle.STAT_NO}">selected</c:if>>${cycle.STAT_NAME}</option>
						</c:forEach>
					</select>
				</dd>
			</dl>
			
			<dl>
				<dt><!-- 公司 --><spring:message code="ar.viewcycleparameter.title.gongsi"/></dt>
				<dd>
					<input id="CPNY_ID" name="CPNY_ID" type="text" value="${cycleParamInfo.CPNY_ID}" readonly="readonly"/>
				</dd>
			</dl>
			
			<dl>
				<dt><!-- 开始日期 --><spring:message code="ar.viewcycleparameter.title.kaishiriqi"/></dt>
				<dd>
					<input id="START_DATE" type="text" name="START_DATE" class="date required" readonly="true" value="${cycleParamInfo.START_DATE}"/>
					<a class="inputDateButton"><!-- 选择 --><spring:message code="ar.viewcycleparameter.content.choose"/></a>
				</dd>
			</dl>
			
			<dl>
				<dt><!-- 结束日期 --><spring:message code="ar.viewcycleparameter.title.jieshuriqi"/></dt>
				<dd>
					<input id="END_DATE" type="text" name="END_DATE" class="date required" readonly="true" value="${cycleParamInfo.END_DATE}"/>
					<a class="inputDateButton"><!-- 选择 --><spring:message code="ar.viewcycleparameter.content.choose"/></a>
				</dd>
			</dl>
			
			<dl>
				<dt><!-- 是否活跃 --><spring:message code="ar.viewcycle.title.shifouhuoyue"/></dt>
				<dd>
					<input id="ACTIVITY" type="radio" name="ACTIVITY" value="1" <c:if test="${cycleParamInfo.ACTIVITY eq 1}">checked</c:if>/><!-- 是 --><spring:message code="ar.viewcycle.content.yes"/>
					<input id="ACTIVITY" type="radio" name="ACTIVITY" value="0" <c:if test="${cycleParamInfo.ACTIVITY eq 0}">checked</c:if>/><!-- 否 --><spring:message code="ar.viewcycle.content.no"/>
				</dd>
			</dl>
			<dl style="height:auto;">
				<table>
					<tr>
						<td class="td_title" style="width:122px"><!--员工类型--><spring:message code="hr.viewPersonalInfo.title.EMP_TYPE_NAME"/></td>
						<td class="td_type">
					<ul class="dl_ul"> 
				       <c:forEach items="${empTypeCodeList}" var="vList" varStatus="i">
				             <c:choose>
				              	<c:when test="${i.count % 4 == 0}">
						           <li>
						              <input name="isChecked" id="isChecked_${vList.EMP_TYPE_CODE}" value="${vList.EMP_TYPE_CODE}"  type="checkbox" style="border:0px"
						              <c:forEach items="${statisticList}" var="sList" varStatus="j">
						              		<c:if test="${sList.EMP_TYPE_CODE eq vList.EMP_TYPE_CODE}">
						              			checked=true
						              		</c:if>
						              </c:forEach>
						              />
						              	${vList.EMP_TYPE_NAME}
						            </li>	
								</c:when>
				  				<c:otherwise>
							         <li>
							           <input name="isChecked" id="isChecked_${vList.EMP_TYPE_CODE}" value="${vList.EMP_TYPE_CODE}"  type="checkbox" style="border:0px"
							           <c:forEach items="${statisticList}" var="sList" varStatus="j">
						              		<c:if test="${sList.EMP_TYPE_CODE eq vList.EMP_TYPE_CODE}">
						              			checked=true
						              		</c:if>
						              </c:forEach>
							           />
						              		${vList.EMP_TYPE_NAME}
							          </li>		  							
				  				</c:otherwise>
					      </c:choose>	
						</c:forEach>
		    	</ul>
						</td>
					</tr>
				</table>
			</dl>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit"><!-- 提交 --><spring:message code="public.title.submit"/></button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close"><!-- 取消 --><spring:message code="public.title.cancle"/></button></div></div></li>
			</ul>
		</div>
	</form>
</div>
