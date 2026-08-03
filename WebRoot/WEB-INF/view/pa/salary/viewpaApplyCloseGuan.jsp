<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script type="text/javascript">
function validateArCardRecordApplyCallback(form,callback) {	
	var $form = $(form);	
	if (!$form.valid()) {
		return false;
	}
	
	var url = "";
	if($("#viewAddPaTempSalesFLAG").val() == 2 ){
	   url = $form.attr("action","/pa/salary/paOFF?FLAG=2");
	}else{
	   url = $form.attr("action","/pa/salary/paOFF?FLAG=0");
	} 

	if (confirm ("确认进行进操作？")){	          
		$.ajax({
			type: form.method || 'POST',
			url:$form.attr("action"),
			data:$form.serializeArray(),
			dataType:"json",
			cache: false,
			success: callback || DWZ.ajaxDone,
			error: DWZ.ajaxError
		});		
	}
	return false;
}



function saveTempSalary(flag){

		$("#viewAddPaTempSalesFLAG").val(flag);
		$("#addArCardRecordApplyView").submit();
	}

</script>

 
		<form id="addArCardRecordApplyView" method="post" action="/pa/salary/paOFF" class="pageForm required-validate" 
			onsubmit="return validateArCardRecordApplyCallback(this,navTabAjaxDone);">
			<div class="formBar">
				<ul> 
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="saveTempSalary(0);">发送邮件 </button></div></div></li>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="saveTempSalary(2);">强行否决并关闭申请</button></div></div></li>
				
				</ul>
			</div>
			<table class="user_table" width="100%" layoutH="0" border="1" cellpadding="2" cellspacing="1">
			<input type="hidden"  name="STAT_NO"  value="${STAT_NO }"  />
			<input type="hidden"  name="arMonth"  value="${arMonth }"  />
			<input type="hidden"  name="AR_DEPT_NO"  value="${AR_DEPT_NO }"  />
			<input type="hidden" id="viewAddPaTempSalesFLAG" name="viewAddPaTempSalesFLAG"  />
			
			<c:forEach items="${EssCntlist }" var="lists" varStatus="i">
			   <tr>
			      <td >${lists.DEPT_NAME }</td>
			      <c:forEach items="${lists.TYPESHU}" var="shu">
			         <td>
			           ${shu.TYPE } : ${shu.SHU }
			         </td>
			      </c:forEach>
			   </tr>
			</c:forEach>

            </table>
  		</form>	
 