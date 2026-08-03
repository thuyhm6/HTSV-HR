<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
	function validateCallback_addSubject(form, callback) {
		var $form = $(form);
		if (!$form.valid()) {
			return false;
		}
		var standardC = document.addSubjectForm.STANDARD_C.value;
		if (standardC<0 || standardC>9999) {
			alertMsg.error("<spring:message code='empsubject.alert.standardC'/>");
			//alert("标准课时必须是0-9999之间的数！");
			return false;
		}
		if (standardC.indexOf('.') > -1
				&& standardC.length - standardC.indexOf('.') - 1 > 1) {
			alertMsg.error("<spring:message code='empsubject.alert.standardCLength'/>");
			//alert("最多保留1位小数！");
			return false;
		}
		$.ajax({
			type : form.method || 'POST',
			url : $form.attr("action"),
			data : $form.serializeArray(),
			dataType : "json",
			cache : false,
			success : callback || DWZ.ajaxDone,
			error : DWZ.ajaxError
		});
		return false;
	}
</script>
<div class="pageContent">
	<form  id="addSubjectForm" name="addSubjectForm" method="post" action="/empsubject/addSubject" class="pageForm required-validate" onsubmit="return validateCallback_addSubject(this,dialogAjaxDone);">
		<input name="USE_YN" type="hidden" value="" />
		<div class="pageFormContent nowrap" layoutH="60">
			<dl>
				<dt><spring:message code="ar.viewcycleparameter.title.gongsi"/><!-- 公司 --></dt>
				<dd style="width:100px"><input name="SUBSD_CD" value="${defaultCpny}"  readonly/></dd>
			</dl>
			<dl>
				<dt><spring:message code="empsubject.subjectGrID"/><!-- 课程组ID --></dt>
				<dd style="width:100px">
				  <select name="SUBJT_GR_ID" class="input_select_short" value="" >
                    <c:forEach items="${groupList}" var="GRCDResult">
                        <option value="<c:out value='${GRCDResult.SUBJT_GR_ID}'/>" <c:if test="${SUBJT_GR_ID==GRCDResult.SUBJT_GR_ID}"> selected</c:if> > 
                          <c:out value='${GRCDResult.SUBJT_GR_ID}'/>&nbsp;|&nbsp;<c:out value='${GRCDResult.SUBJT_GR_NM}'/>
                        </option>
                    </c:forEach>
           		   </select>
           		 </dd>
			</dl>
			<dl>
				<dt><spring:message code="empsubject.subjectID"/><!-- 课程ID --></dt>
				<dd style="width:100px"><input name="SUBJT_ID" value="" maxlength="20" onkeyup="value=value.replace(/[^\w\.\/-]/ig,'')" style="ime-mode:disabled" class="required textInput"/></dd>
			</dl>
			<dl>
				<dt><spring:message code="empsubject.subjectNm"/><!-- 课程名称 --></dt>
				<dd style="width:100px"><input name="SUBJT_NM" value="" maxlength="33" class="required textInput"/></dd>
			</dl>
			<dl>
				<dt><spring:message code="empsubject.standardC"/><!-- 标准课时 --></dt>
					<dd style="width:260px">
					  <input  id="STANDARD_C" name="STANDARD_C" value="" maxlength="6" class="required textInput" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')"  style="ime-mode:disabled"/>
					     <spring:message code="empsubject.message.standardC"/><!-- 【0-9999,1位小数】 -->
					</dd>
			</dl>
			<dl>
				<dt><spring:message code="empsubject.prodTp"/><!-- 产品类型 --></dt>
				<dd style="width:100px"><ait:SelectSyCodeByCpnyID id="PROD_TP" name="PROD_TP" parentNo="211424" selected="" cnpyID="${defaultCpny}"/></dd>
			</dl>
			<dl><!-- 开始日期 -->
				<dt><spring:message code="public.title.startDate"/></dt>
				<dd style="width:260x"><input id="START_DATE" type="text" name="START_DATE" class="date required" readonly value=""/>
				<a class="inputDateButton"><!-- 选择 -->
					 <spring:message code="public.title.choose"/>
				</a></dd>
			</dl>
			<dl><!-- 结束日期 -->
				<dt><spring:message code="public.title.endDate"/></dt>
				<dd style="width:260px"><input id="END_DATE" type="text" name="END_DATE" class="date required" readonly value=""/>
			    <a class="inputDateButton"><!-- 选择 -->
	                 <spring:message code="public.title.choose"/>
				</a></dd>
			</dl>
			<dl>
				<dt>线上区分</dt>
				<dd style="width:100px">
					<ait:ComboSyCodeDescByCpnyID id="ONLINE_STATE"
							name="ONLINE_STATE" parentNo="14013523"
							selected="${ONLINE_STATE}" cnpyID="${defaultCpny}" limit="all" />
				</dd>
			</dl>
			<dl>
				<dt><spring:message code="empsubject.empNm"/><!-- 员工 --></dt>
				<dd style="width:300px">[${empId}]${personName}</dd>
			</dl>			
		</div>
		
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">
				<spring:message code="button.sys.affirm.save"/><!--保存--></button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">
				<spring:message code="public.title.cancle"/><!--取消--></button></div></div></li>
			</ul>
		</div>
	</form>
	
</div>