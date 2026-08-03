<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageContent">
	<form method="post" action="/pa/workManagement/updatePayObjStdInfo" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDoneWithForm);">
		<div class="pageFormContent nowrap">
			
			<dl>
				<dt><!-- 工资区分 --><spring:message code="pa.viewPaPaySchedule.GONGZIQUFEN.C" /></dt>
				<dd>
					<ait:SelectSyCodeByCpnyID name="SALARY_DISTIN_NO" parentNo="14013797"
							cnpyID="${LoginUser.cpnyId}"  selected="${paPayObjStdInfo.SALARY_DISTIN_NO }"/>
					<input name="PAY_OBJ_STD_NO" type="hidden" value="${paPayObjStdInfo.PAY_OBJ_STD_NO }"/>
				</dd>
			</dl>
			<dl>
				<dt><!--月份区分--><spring:message code="pa.viewPayObjectStd.YUEFENQUFEN.b" /></dt>
				<dd>
					<input type="radio" name="MONTH_TYPE" 
					<c:if test="${paPayObjStdInfo.MONTH_TYPE == 'currentMonth' }">checked="checked"</c:if>
					class="required" value="currentMonth" /><!--当月--><spring:message code="pa.monthPersonCountInfoList.DANGYUE.b" />
					<input type="radio" name="MONTH_TYPE" 
					<c:if test="${paPayObjStdInfo.MONTH_TYPE == 'lastMonth' }">checked="checked"</c:if>
					class="required" value="lastMonth"/><!--上月--><spring:message code="ess.infoApply.LASTMONTH" />
				</dd>
			</dl>
			<dl>
				<dt><!--员工类型--><spring:message code="ess.infoApply.employee_type" /></dt>
				<dd>
					<ait:SelectSyCodeByCpnyID name="EMP_TYPE_CODE" parentNo="13864"
							cnpyID="${LoginUser.cpnyId}"  selected="${paPayObjStdInfo.EMP_TYPE_CODE }"/>
				</dd>
			</dl>
			<dl>
				<dt><!--日期--><spring:message code="ess.infoApply.date" /></dt>
				<dd>
					<select name="DAY_STR">
						<c:forEach begin="1" end="31" varStatus="i">
							<c:choose>
								<c:when test="${i.count == paPayObjStdInfo.DAY_STR }">
									<option value="${i.count }" selected="selected">${i.count }</option>
								</c:when>
								<c:otherwise>
									<option value="${i.count }">${i.count }</option>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</select>
				</dd>
			</dl>
			<dl>
				<dt><!--开始日期--><spring:message code="public.title.startDate" /></dt>
				<dd>
					<input id="START_DATE" type="text" name="START_DATE" class="Wdate required" onClick="WdatePicker({dateFmt:'dd-MM-yyyy',lang:'en'})" readonly="true" value="${paPayObjStdInfo.START_DATE }"/>
				</dd>
			</dl>
			<dl>
				<dt><!--结束日期--><spring:message code="public.title.endDate" /></dt>
				<dd>
					<input id="END_DATE" type="text" name="END_DATE" class="Wdate required" onClick="WdatePicker({dateFmt:'dd-MM-yyyy',lang:'en'})" readonly="true" value="${paPayObjStdInfo.END_DATE }"/>
				</dd>
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
