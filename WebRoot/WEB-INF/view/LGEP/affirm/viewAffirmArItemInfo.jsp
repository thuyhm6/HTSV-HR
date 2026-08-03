<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="../../inc/initTaglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<title>LGE CHRS2.0</title>
<link href="/resources/css/dwzUI/core.css" rel="stylesheet"
	type="text/css" />


<link href="/resources/css/dwzUI/uploadify/uploadify.css"
	rel="stylesheet" type="text/css" />
<link href="/resources/css/ligerUI/skins/Aqua/css/ligerui-all.css"
	rel="stylesheet" type="text/css" />

<link href="/resources/css/ztree/zTreeStyle/zTreeStyle.css"
	rel="stylesheet" type="text/css" />
<link href="/resources/css/dwzUI/themes/lge/style.css" rel="stylesheet"
	type="text/css" />
<link href="/resources/css/dwzUI/themes/lge/sso_style.css"
	rel="stylesheet" type="text/css" />
	
	
	
<script src="/resources/js/jquery/jquery.all.js" type="text/javascript"></script>
<script src="/resources/js/jquery/jquery.validate.js"
	type="text/javascript"></script>
<script src="/resources/js/dwzUI/source/dwz.validate.method.js"
	type="text/javascript"></script>
<script src="/resources/js/dwzUI/source/dwz.navTab.js"
	type="text/javascript"></script>
<script src="/resources/js/dwzUI/source/dwz.tab.js"
	type="text/javascript"></script>
<script src="/resources/js/dwzUI/source/dwz.ajax.js"
	type="text/javascript"></script>
<script src="/resources/js/dwzUI/dwz.regional.zh.js"
	type="text/javascript"></script>
<script type="text/javascript">
function validateCallback_ItemAffirm(form, callback) {
	var $form = $("#updateAffirmAttendInfo");
	var pro_flag = $("#pro_flag").val();
		if(pro_flag == 0){
	$.ajax({
			type: form.method || 'POST',
			url:$form.attr("action"),
			data:$form.serializeArray(),
			dataType:"json",
			cache: false,
			success: function(json){
			    
				if (json.statusCode == 200){
				    alert(json.message);
				    pro_flag=1;
					window.location.href=window.location.href;
				}else{
						alert(json.message);
						$("#pro_flag").val("0");
					}
			}
		});
		}else{
			alert("审批处理中，请稍等。。。");
		}
		return false;
}
</script>
<a id="ar_affirm" href="" target="navTab" rel="ar_affirm" style="display:none;">添加</a>
<div class="pageContent">

<form onsubmit="return validateCallback_ItemAffirm(this,navTabAjaxDone);"
		action="/LGEP/affirm/updateAffirmAttendInfo" id="updateAffirmAttendInfo" method="post">
<table class="table" width="100%" layoutH="270">
	<thead>
		<tr>
				<th width="10%"> 
					申请类型
				</th>
			    <th width="10%"> 
					<spring:message code="pa.insurance.title.projectType"/><!--项目类型-->
				</th>
				<th width="15%">
					<spring:message code="pa.insurance.title.projectName"/><!--项目名称-->
				</th>
				<th width="20%">
					<spring:message code="pa.salarycode.affirm.reason"/><!--申请事由-->
				</th>
				<th width="15%">
					<spring:message code="hr.viewSuggestion.title.Suggestion"/><!--决裁意见-->
				</th>
				<th width="15%"><spring:message code="ess.infoApply.title.essApplyTime"/><!--申请时间-->
					</th>
				<th width="15%" style="text-align: center">
					<spring:message code="ar.viewcycle.title.zhuangtai"/>
				</th>	
			</tr>
	</thead>
	<tbody>
		
				<tr target="AFFIRM_ITEM_NO" rel="${arItemInfo.AFFIRM_ITEM_NO}">
				  
				    <td >
						${arItemInfo.ACTIVITY_TYPE eq '1' ? '新项目申请' : '申请启用'}
						<input type="hidden" name="ACTIVITY_TYPE" id="ACTIVITY_TYPE" value="${arItemInfo.ACTIVITY_TYPE}"/>
					</td>
				    <td >
						${arItemInfo.PROJECT_TYPE eq 1 ? '明细项目' : '汇总项目'}
						<input type="hidden" name="PROJECT_TYPE" id="PROJECT_TYPE" value="${arItemInfo.PROJECT_TYPE}"/>
					</td>
					
					<td>
						${arItemInfo.ITEM_NAME}
						<input type="hidden" name="AFFIRM_ITEM_NO" id="AFFIRM_ITEM_NO" value="${arItemInfo.AFFIRM_ITEM_NO}"/>
						<input type="hidden" name="ITEM_NO" id="ITEM_NO" value="${arItemInfo.AFFIRM_ITEM_NO}"/>
						<input type="hidden" name="ITEM_NAME" id="ITEM_NAME" value="${arItemInfo.ITEM_NAME}"/>
						<input type="hidden" name="cpny" id="cpny" value="${arItemInfo.AFFIRM_CPNY_ID}"/>
						<input type="hidden" name="CPNY_ID" id="CPNY_ID" value="${CPNY_ID}"/>
						<input type="hidden" name="personId" id="personId" value="${personId}"/>
						<input id="pro_flag" name="pro_flag" type="hidden" value="0" />
					</td>
					<td>
						${arItemInfo.AFFIRM_REASON}
					</td>
					<td>
					   <input name="AFFIRM_DESCR" id="AFFIRM_DESCR" value="${arItemInfo.AFFIRM_DESCR }"/>
					</td>
					<td>
						<fmt:formatDate value="${arItemInfo.CREATE_DATE }" pattern="yyyy-MM-dd HH:mm:ss" /> 
					</td>
					<td style="text-align: center">
					<c:if test="${arItemInfo.ACTIVITY==0}">
					<input name="flag" type="radio" value="1" checked/>通过
				    <input name="flag" type="radio" value="2"/>否决
				    </c:if>
				    <c:if test="${arItemInfo.ACTIVITY==1}">
				        已通过
				    </c:if>
				    <c:if test="${arItemInfo.ACTIVITY==2}">
				        已否决
				    </c:if>
                    </td>		
				</tr>
			
	</tbody>
</table>
<c:if test="${arItemInfo.ACTIVITY==0}">
<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">
				     提交</button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close">
					<spring:message code="public.title.cancle"/><!--取消--></button></div></div>
				</li>
			</ul>
		</div>
		</c:if>
</form>
</div>
