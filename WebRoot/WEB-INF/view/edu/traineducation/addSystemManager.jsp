<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">

function codeRelation3(parentCodeValue,sonCodeId,sonValue,typeFlag){
	var sonVal = '';
	if(parentCodeValue != ''){
		$.ajaxSettings.global = false;
		$.ajax( {
			type : "POST",
			url : "/sys/basicMaintenance/getCodeRelation",
			data : { seach_PARENT_CODE_NO : parentCodeValue, type : typeFlag },
			dataType : "json",
			success : function(data) {
				//先清空
			    $('#' + sonCodeId).html("<option value=''><spring:message code='hr.viewCondSql.title.QINGXUANZE'/></option>");
				var index = 0;
				//循环填充下拉框
				if (typeof (data['result']) != "undefined") {
					$.each(data['result'], function(commentIndex, comment) {
						index = index + 1;
						if(sonValue == comment['CODE_NO']){
							sonVal = sonValue;
						}
						$('#' + sonCodeId).append('<option value="' + comment['CODE_NO'] + '">' + comment['CONTENT'] + '</option>');
					});
				}
				//如果所选中的值不存在，就不需要执行选中方法
				if(sonVal != null && sonVal != ''){
					$("#" + sonCodeId).attr("value",sonVal);
				}
			}
		});
		$.ajaxSettings.global = true;
	}else{
		//先清空
		$('#' + sonCodeId).html("");
	}
}


function trainDivADD(value){
	codeRelation3(value,'TRAIN_TYPE_CODE1','');
}
</script>
<div class="pageContent">
	<form method="post" action="/edu/traineducation/addSystemManagerInfo" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone);">
		<div class="pageFormContent nowrap">
			<dl>
				<dt><spring:message code="liang.hr.viewTraining.title.TRAINING_DIFFERENTIATE"/><!--培训区分--></dt>
				<dd>
					<ait:SelectSyCodeByCpnyID name="TRAIN_DIFF_CODE" id="TRAIN_DIFF_CODE"
                    parentNo="14014478" cnpyID="${defaultCpny}" selected="" limit="all"  onChangeName="trainDivADD(this.value);" />
				</dd>
			</dl>
			<dl>
				<dt><spring:message code="edu.systemManager.PEIXUNLEIXING.a"/><!--培训类型--></dt>
				<dd>
					<div id="transReasonDiv1">
				        <select name="TRAIN_TYPE_CODE" id="TRAIN_TYPE_CODE1" >
						</select>
					</div>
				</dd>
			</dl>
			<dl>
				<dt><spring:message code="edu.systemManager.LEIXINGBIANHAO.a"/><!--类型编号--></dt>
				<dd>
				</dd>
			</dl>
			<dl>
				<dt><spring:message code="org.title.REMARK"/><!--备注--></dt>
				<dd>
					<input type="text" name="REMARK" id="REMARK"/>
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
