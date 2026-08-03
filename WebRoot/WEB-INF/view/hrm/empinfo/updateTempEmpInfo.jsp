<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<div class="pageContent">
	<form id="updateTempEmpInfoSpecial" method="post" action="/hrm/empinfo/updateTempEmpInfoSpecial" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDoneWithForm);">
		
		<input type="hidden" id="SPECIAL_NO" name="SPECIAL_NO" value="${tempEmpinfo.SPECIAL_NO}"/>
		<input type="hidden" id="PERSON_ID" name="PERSON_ID" value="${tempEmpinfo.PERSON_ID}"/>
		
		<div class="pageFormContent nowrap" layoutH="56">
			
			<dl>
				<dt><!-- 工号 --><spring:message code="hr.viewPersonalInfo.title.EMPID"/></dt>
				<dd>
					${tempEmpinfo.EMPID}
				</dd>
			</dl>
			<dl>
				<dt><!-- 姓名 --><spring:message code="public.title.name"/></dt>
				<dd>
					${tempEmpinfo.LOCAL_NAME}
				</dd>
			</dl>
			<dl>
				<dt><!-- 部门 --><spring:message code="public.title.deptName"/></dt>
				<dd>
					${tempEmpinfo.DEPTNAME}
				</dd>
			</dl>
			<dl>
				<dt class="td_title"><spring:message code="inct.salesman.classify"/><!-- 信息区分 --></dt>
				<dd class="td_type">
				    <ait:SelectSyCodeByCpnyID name="INFOR_DIS_CODE" id="INFOR_DIS_CODE" parentNo="14014361" cnpyID="${defaultCpny}" selected="${tempEmpinfo.INFOR_DIS_CODE}" limit="all" />
					
				</dd>
			</dl>
			<dl>
				<dt class="td_title" > <spring:message code="ar.viewcycle.title.kaishiri"/><!-- 开始日期 --></dt>
                <dd class="td_type">
					<input name="START_DATE"  id="START_DATE" type="text" class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})" value="${tempEmpinfo.START_DATE}" />
				</dd>
		   </dl>
		   <dl>
		   	<dt class="td_title" > <spring:message code="ar.viewcycle.title.jieshuri"/><!-- 结束日期 --> </dt>
                <dd class="td_type">
					<input name="END_DATE"  id="END_DATE" type="text" class="Wdate" onClick="WdatePicker({dateFmt:'dd/MM/yyyy',lang:'en'})" value="${tempEmpinfo.END_DATE}" />
				</dd>
		   </dl>
		   <dl>
				<dt class="td_title"><spring:message code="ess.attendance.ot"/><!-- Overtime --></dt>
				<dd class="td_type">
				    <select id="OT_FLAG" name="OT_FLAG" >
							<option value="1"  <c:if test="${tempEmpinfo.OT_FLAG == 1 }">selected</c:if>><!-- Yes --> <spring:message code="ar.viewcycle.content.yes" /></option>
						<option value="0"  <c:if test="${tempEmpinfo.OT_FLAG == 0 }">selected</c:if>><!--No--> <spring:message code="ar.viewcycle.content.no" /></option>
						</select>
					
				</dd>
			</dl>
			<dl>
				<dt class="td_title"> <spring:message code="hrm.contract.content"/><!-- 内容 --> </dt>
				<dd class="td_type" colspan="3">
					<textarea name="SPECIAL_CONTENT"  id="SPECIAL_CONTENT" style="width:500px;height:80px">${tempEmpinfo.SPECIAL_CONTENT}</textarea>
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
