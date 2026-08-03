<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
function validateCallback_insertSqlMaster(form, callback) {
	var $form = $("#addSqlMaster");
	var CPNY_IDv = $("#addSqlMaster select[name='CPNY_ID']:selected").val();
	var PGM_NMv = $("#addSqlMaster select[name='PGM_NM']:selected").val();
	var SQL_NMv = $("#addSqlMaster input[name='SQL_NM']").val();
	var SQL_FROM_STMTv = $("#addSqlMaster input[name='SQL_FROM_STMT']").val();
	var SQL_ORDER_BY_IDv = $("#addSqlMaster input[name='SQL_ORDER_BY_ID']").val();
	var SQL_DESCv = $("#addSqlMaster input[name='SQL_DESC']").val();
	
	if(CPNY_IDv==""){ 
		alertMsg.error("法人不能为空，请选择法人!");
		$("#CPNY_ID").focus();
		return false;
	}
	if(PGM_NMv==""){ 
		alertMsg.error("模块不能为空，请选择模块!");
		$("#PGM_NM").focus();
		return false;
	}
	if(SQL_NMv==""){ 
		alertMsg.error("SQL名不能为空，请输入SQL名!");
		$("#SQL_NM").focus();
		return false;
	}
	if(SQL_FROM_STMTv==""){ 
		alertMsg.error("SQL来源不能为空，请输入!");
		$("#SQL_FROM_STMT").focus();
		return false;
	}
	if(SQL_ORDER_BY_IDv==""){ 
		alertMsg.error("SQL排序号不能为空，请输入!");
		$("#SQL_ORDER_BY_ID").focus();
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
	<form id="affirmReplace" name="affirmReplace" method="post" class="pageForm required-validate" 
		action="/sys/affirmReplace/addAffirmReplace" onsubmit="return validateCallback_insertSqlMaster(this, dialogAjaxDone);">
		<div class="pageFormContent nowrap" layoutH="90">
			<dl>
				<dt>
					法人：
				</dt>
					<dd>
					<select id= "CPNY_ID" name="CPNY_ID">
            			<option value="<c:out value="${CPNY_ID}"/>" selected>
              				 <c:out value="${CPNY_ID}"/></option>
           			</select>
					</dd>
			</dl>
			<dl>
				<dt>
					替换类型:
				</dt>
				<dd>
					<c:out value='替换'/>  
					<input name="REPLACE_TYPE" type="hidden" readonly="true" size="18" value="<c:out value='TH'/>">
				</dd>
 
			</dl>
			<dl><dt>作用域：</dt>
					<dd>
						<select id="TABLE_NAME" name="TABLE_NAME">
							<option value="ALL" <c:if test="${TABLE_NAME eq 'ALL'}">selected</c:if>>全部</option>
							<option value="ZZ" <c:if test="${TABLE_NAME eq 'ZZ'}">selected</c:if>>最终裁决</option>
							<option value="TS" <c:if test="${TABLE_NAME eq 'TS'}">selected</c:if>>特殊裁决</option>
						</select>
					</dd>
			</dl>
			
			<dl>
				<dt>
					被替换者:
				</dt>
				<dd>
					<input id="OLDCHECK_NM" name="dwz.person.empNameold" type="text" value="" readonly lookupGroup="person"/>
					<input id="OLDCHECK_EMPID" name="dwz.person.empIdold" type="hidden" value=""  lookupGroup="person"/>
					<input type="hidden" name="dwz.person.person_idold" id="OLDCHECK_PERSONID" value="${OLDCHECK_PERSONID}"  lookupGroup="person"/>
			 		<a class="btnLook" href="/sys/affirmReplace/viewAffirmEmpList?firstFlag=1&limit=pa&pageNum=1&newold=old" rel="getemp"lookupGroup="person">
			 		<!-- 查找带回 --><spring:message code="ar.alert.message.viewattendencekeeper.chazhaodaihui"/></a>
				</dd>
			</dl>
			<dl>
				<dt>
					替换者:
				</dt>
				<dd>
					<input id="NEWCHECK_NM" name="dwz.person.empNamenew" type="text" value="" readonly lookupGroup="person"/>
					<input id="NEWCHECK_EMPID" name="dwz.person.empIdnew" type="hidden" value=""  lookupGroup="person"/>
					<input type="hidden" id="NEWCHECK_PERSONID" name="dwz.person.person_idnew"  value="${NEWCHECK_PERSONID}"  lookupGroup="person"/>
			 		<a class="btnLook" href="/sys/affirmReplace/viewAffirmEmpList?firstFlag=1&limit=pa&pageNum=1&newold=new" rel="getemp"lookupGroup="person">
			 		<!-- 查找带回 --><spring:message code="ar.alert.message.viewattendencekeeper.chazhaodaihui"/></a>
				</dd>
			</dl>
			
			<dl>
				<dt>
					输入人:
				</dt>
				<dd>
					<c:out value='${UPDT_NN}'/>  
					<input name="UPDT_USER" type="hidden" readonly="true" size="18" value="<c:out value='${UPDT_USER}'/>">
				</dd>
 
			</dl>
			<input  type="hidden" name="ACTIVITY" id="ACTIVITY" value="1"/>
		</div>
		<div class="formBar" layoutH="260">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">
								<spring:message code="public.title.submit"/><!--保存-->
							</button>
						</div>
					</div>
				</li>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" class="close">
								<spring:message code="public.title.cancle"/><!--取消-->
							</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</form>
</div>

		
		
		
		
