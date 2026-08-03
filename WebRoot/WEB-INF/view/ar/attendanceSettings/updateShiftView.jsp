<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>

	function f_save(form) {
		
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
			success: navTabAjaxDone || DWZ.ajaxDone,
			error: DWZ.ajaxError
		});
		return false;
	}
</script>
<div class="pageContent">
	<form method="post" action="/ar/attendanceSettings/updateShiftInfo" class="pageForm required-validate" onsubmit="return f_save(this)">
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit"><!-- 提交 --><spring:message code="public.title.submit"/></button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close"><!-- 取消 --><spring:message code="public.title.cancle"/></button></div></div>
				</li>
			</ul>
		</div>
		<div class="pageFormContent nowrap" layoutH="56">
			
			<dl>
				<dt><!-- 班次ID --><spring:message code="ar.viewshift.title.banciID"/></dt>
				<dd>
					<input id="SHIFT_ID" type="text" name="SHIFT_ID" value="${shiftInfo.SHIFT_ID}" class="required"/>
					<input type="hidden" name="NO" value="${shiftInfo.SHIFT_NO}"/>
					<input type="hidden" name="SHIFT_NO" value="${shiftInfo.SHIFT_NO}"/>
				</dd>
			</dl>
			<dl>
				<dt><!-- 班次性质 --><spring:message code="ar.viewshift.title.bancixingzhi"/></dt>
				<dd>
				    <input type="hidden" name="DATATYPE" value="${shiftInfo.DATATYPE}"/>
					${shiftInfo.DATATYPE_NAME}
				</dd>
			</dl>
			
			<ait:SyLanguage languageNo="${shiftInfo.SHIFT_NO}"/>
			
			<dl>
				<dt><!-- 扣除时间 --><spring:message code="ar.viewshift.title.kouchushijian"/></dt>
				<dd>
					${shiftInfo.DEDUCT_TIME}
					<input type="hidden" name="DEDUCT_TIME" value="${shiftInfo.DEDUCT_TIME}"/>
				</dd>
			</dl>
			<dl>
				<dt><!-- 加班开始时间 --><spring:message code="ess.infoApply.overtime_start_time"/></dt>
				<dd>
					<input id="OT_TIME_START_H" name="OT_TIME_START_H" type="text"  size="3" maxlength="2" min="0" max="23" value="${shiftInfo.OT_TIME_START_H}"/>:
				</dd>
				<dd>
					<input id="OT_TIME_START_M" name="OT_TIME_START_M" type="text"  size="3" maxlength="2" min="0" max="59" value="${shiftInfo.OT_TIME_START_M}"/>
				</dd>
			</dl>
			<!--<dl>
				<dt>中夜班津贴</dt>
				<dd>
					<input id="OT_ALLOWANCE" name="OT_ALLOWANCE" type="text"  size="3" value="${shiftInfo.OT_ALLOWANCE}"/>
				</dd>
			</dl>
		   -->
		   <dl>
				<dt style="float:left"> <!-- 工作形态 --><spring:message code="ess.infoApply.WORKTYPE"/> </dt>
				<dd>
					<input id="F_WORK_TIME_H" name="F_WORK_TIME_H"   value="${shiftInfo.F_H}" type="text"  size="3" maxlength="2" min="0" max="23" >:
				</dd>
				<dd>
					<input id="F_WORK_TIME_M" name="F_WORK_TIME_M"   value="${shiftInfo.F_M}" type="text"  size="3" maxlength="2" min="0" max="59"> &nbsp;~&nbsp;
				</dd>
			   <dd>
					<input id="T_WORK_TIME_H" name="T_WORK_TIME_H"   value="${shiftInfo.T_H}" type="text"  size="3" maxlength="2" min="0" max="23" >:
				</dd>
				<dd>
					<input id="T_WORK_TIME_M" name="T_WORK_TIME_M"   value="${shiftInfo.T_M}" type="text"  size="3" maxlength="2" min="0" max="59">
				</dd>
			</dl>
			<div id="createTable1">
				<c:forEach items="${shiftParameterList}" var="list">
					<table id="table" width="100%"  border="1" cellpadding="0" cellspacing="0" class="l-table-edit" >
						<tr>
							<td align="right" class="l-table-edit-td"><!-- 开始时间 --><spring:message code="ess.infoApply.title.startTime"/></td>
							<td align="left" class="l-table-edit-td">${list.FROM_TIME}</td>
							<td align="right" class="l-table-edit-td"><!-- 结束时间 --><spring:message code="ess.infoApply.title.endTime"/></td>
							<td align="left" class="l-table-edit-td">${list.TO_TIME}</td>
						    <td align="center" class="l-table-edit-td">${list.ITEM_TIME}</td>
							<td >
								<table width="100%"  border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td align="right" class="l-table-edit-td"><!-- 开始 --><spring:message code="ar.viewshift.title.start"/></td>
										<td align="left" class="l-table-edit-td">
											<c:if test="${list.BEGIN_DAY_OFFSET eq '-1'}"><!-- 昨日 --><spring:message code="ar.viewshift.title.zuori"/></c:if>
											<c:if test="${list.BEGIN_DAY_OFFSET eq '0'}"><!-- 当日 --><spring:message code="ar.viewshift.title.dangri"/></c:if>
											<c:if test="${list.BEGIN_DAY_OFFSET eq '1'}"><!-- 次日 --><spring:message code="ar.viewshift.title.ciri"/></c:if>
										</td>
									</tr>
									<tr>
										<td align="right" class="l-table-edit-td"><!-- 结束 --><spring:message code="ar.viewshift.title.end"/></td>
										<td align="left" class="l-table-edit-td">
											<c:if test="${list.END_DAY_OFFSET eq '-1'}"><!-- 昨日 --><spring:message code="ar.viewshift.title.zuori"/></c:if>
											<c:if test="${list.END_DAY_OFFSET eq '0'}"><!-- 当日 --><spring:message code="ar.viewshift.title.dangri"/></c:if>
											<c:if test="${list.END_DAY_OFFSET eq '1'}"><!-- 次日 --><spring:message code="ar.viewshift.title.ciri"/></c:if>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</c:forEach>
			</div>
		    <input type="hidden" name="count" id="count" value="0">
		</div>
		
	</form>	
</div>