<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageContent">
	<form id="addarcardrecordinfoview" method="post" action="/ar/attendanceMintenance/updateArCardRecordInfo" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDoneWithForm);">
		
		<input type="hidden" id="RECORD_NO" name="RECORD_NO" value="${arCardRecordInfo.RECORD_NO}"/>
		<input type="hidden" id="PERSON_ID" name="PERSON_ID" value="${arCardRecordInfo.PERSON_ID}"/>
		
		<div class="pageFormContent nowrap" layoutH="56">
			
			<dl>
				<dt><!-- 工号 --><spring:message code="hr.viewPersonalInfo.title.EMPID"/></dt>
				<dd>
					${arCardRecordInfo.EMPID}
				</dd>
			</dl>
			<dl>
				<dt><!-- 姓名 --><spring:message code="public.title.name"/></dt>
				<dd>
					${arCardRecordInfo.LOCAL_NAME}
				</dd>
			</dl>
			<dl>
				<dt><!-- 部门 --><spring:message code="public.title.deptName"/></dt>
				<dd>
					${arCardRecordInfo.DEPTNAME}
				</dd>
			</dl>
			<dl>
                <dt><!-- 考勤日期 --><spring:message code="ess.infoApply.attendance_date"/></dt>
                <dd>
                    <input type="text" id="AR_DATE_STR" name="AR_DATE_STR" class="Wdate" onClick="WdatePicker({dateFmt:'yyyy/MM/dd',lang:'en'})" value="${arCardRecordInfo.AR_DATE_STR}" readonly="true"/>
                </dd>
            </dl>
			<dl>
				<dt><!-- 时间 --><spring:message code="ar.viewarcardrecord.title.shijian"/></dt>
				<dd>
				    <input type="text" id="R_DATE" name="R_DATE" class="Wdate" onClick="WdatePicker({dateFmt:'dd-MM-yyyy',lang:'en'})" value="${arCardRecordInfo.R_DATE}" readonly="true"/>
				    
					<!--<input type="text" id="R_DATE" name="R_DATE" class="date required" value="${arCardRecordInfo.R_DATE}" yearstart="-20" yearend="20" readonly="true" />-->
					:<input id="R_HOUR" name="R_HOUR" type="text" class="digits required" value="${arCardRecordInfo.R_HOUR}" size="3" maxlength="2"  min="0" max="23" >
					:<input id="R_MINITE" name="R_MINITE" type="text" class="digits required" value="${arCardRecordInfo.R_MINITE}" size="3" maxlength="2" min="0" max="59">
				</dd>
			</dl>
			
			<dl>
				<dt><!-- 类型 --><spring:message code="ar.viewarcardrecord.title.leixing"/></dt>
				<dd>
					<select id="DOOR_TYPE" name="DOOR_TYPE">
						<option value="IN" <c:if test="${arCardRecordInfo.DOOR_TYPE eq 'IN'}">selected</c:if>><!-- 进门 --><spring:message code="ar.viewarcardrecord.title.jinmen"/></option>
						<option value="OUT" <c:if test="${arCardRecordInfo.DOOR_TYPE eq 'OUT'}">selected</c:if>><!-- 出门 --><spring:message code="ar.viewarcardrecord.title.chumen"/></option>
					</select>
				</dd>
			</dl>
			
			<dl>
				<dt><!-- 备注 --><spring:message code="ar.viewarcardrecord.title.beizhu"/></dt>
				<dd>
					<textarea name="REMARK" cols="60" rows="2">${arCardRecordInfo.REMARK}</textarea>
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
