<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script type="text/javascript">
/**
 * 删除附件(新增)
 */
function deleteAttListInsertPlan() {
	var flag = false;
	$("input[name='FILE_NO']").each(function() {
		if ($(this).attr("checked") == "checked") {
			$(this).parent().parent().remove();
			flag = true;
		}
	});
	if (flag == false) {
		alertMsg.info("<spring:message code='edu.planManager.QINGXIANXUANZEYAOSHANCHUDEFUJIAN.a'/>");//请先选择要删除的附件
		return false;
	}
}
</script>
<div class="pageContent" layoutH="10">
	<form method="post" action="/edu/traineducation/addTrainOrganInfo" class="pageForm required-validate" onsubmit="return validateCallback(this,dialogAjaxDone);">
		<div class="pageFormContent nowrap">
		<table  class="user_table" width="100%" border="1" cellpadding="2" cellspacing="1" >
		<tr>
		<td class="td_title" width="1%"><spring:message code="edu.trainOrgan.PEIXUNJIGOUMINGCHENG.a"/><!--培训机构名称--></td>
		<td class="td_type"  width="20%">
		<input type="text" class="required" name="ORGAN_NAME" id="ORGAN_NAME" value="">
		</td>
		</tr>
		
		<tr>
		<td class="td_title" width="1%"><spring:message code="edu.trainOrgan.LIANXIREN.a"/> <!--联系人
			--></td>
		<td class="td_type"  width="20%">
		<input type="text"  name="LINKMAN" id="LINKMAN" value="">
		</td>
		</tr>
		
		<tr>
		<td class="td_title" width="1%"><spring:message code="hr.viewRelation.title.FAM_ADDRESS"/><!--地址
			--></td>
		<td class="td_type"  width="20%">
		<input type="text"  name="ADDRESS" id="ADDRESS" value="">
		</td>
		</tr>
		
		<tr>
		<td class="td_title" width="1%"><spring:message code="hr.viewHire.title.OFFICE_PHONE"/><!--办公室电话
			--></td>
		<td class="td_type"  width="20%">
		<input type="text"  name="OFFICE_PHONE" id="OFFICE_PHONE" value="">
		</td>
		</tr>
		
		<tr>
		<td class="td_title" width="1%"><spring:message code="hrm.empinfo.MOBILE_TELEPHONE"/><!--移动电话
			--></td>
		<td class="td_type"  width="20%">
		<input type="text"  name="CELLPHONE" id="CELLPHONE" value="">
		</td>
		</tr>
		
		<tr>
		<td class="td_title" width="1%"><spring:message code="pa.ins.alert.message.exportdata.netAddress"/><!--网址
			--></td>
		<td class="td_type"  width="20%">
		<input type="text"  name="URL_NET" id="URL_NET" value="">
		</td>
		</tr>
		
		<tr>
		<td class="td_title" width="1%"><spring:message code="edu.trainOrgan.ZHUYINGLINGYU.a"/><!--主营领域
			--></td>
		<td class="td_type"  width="20%">
		<input type="text"  name="MAIN_FIELD" id="MAIN_FIELD" value="">
		</td>
		</tr>
		
		<tr>
		<td class="td_title" width="1%"><spring:message code="edu.trainOrgan.HEZUOQINGKUANGJIPINGJIA.a"/><!--合作情况及评价
		--></td>
		<td class="td_type"  width="20%">
		<input type="text"  name="WORK_TOGETHER" id="WORK_TOGETHER" value="">
		</td>
		</tr>
		
		<tr>
		<td class="td_title" width="1%"><spring:message code="edu.trainOrgan.JIGOUJIANJIE.a"/><!--机构简介
		--></td>
		<td class="td_type"  width="20%">
		<input type="text"  name="ORGAN_ABSTRACT" id="ORGAN_ABSTRACT" value="">
		</td>
		</tr>
		
		<tr>
		<td class="td_title" width="1%"><spring:message code="edu.trainOrgan.HEZUOXIEYI.a"/><!--合作协议
		--></td>
		<td class="td_type" width="20%">
						<a class="w_button" href="#" onclick="uploadAttDialogInsert_new('<spring:message code="js.upload.msg.fileToUpload" />');"><span><spring:message code="button.add"/><!--添加
			--></span>
						</a>
						<a class="w_button" href="#" onclick="deleteAttListInsertPlan();"><span><spring:message code="button.delete"/><!--删除
			--></span>
						</a>
						<table id="fileTable" class="list" width="100%">
							<thead>
							</thead>
							<tbody>
							</tbody>
						</table>
					</td>
		</tr>
		
		</table>
		
		</div>
		
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit"><!-- 提交 --><spring:message code="public.title.submit"/></button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close"><!-- 取消 --><spring:message code="public.title.cancle"/></button></div></div></li>
			</ul>
		</div>
	</form>
</div>
