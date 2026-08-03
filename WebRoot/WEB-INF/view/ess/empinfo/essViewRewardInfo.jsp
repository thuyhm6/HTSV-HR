<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<script>


    function f_delShiftInfo(obj){
    	$("#"+obj+"").remove();
    }
    
    function validateCallbackViewFamilyInfo(form, callback) {
	
		var $form = $("#essAddRewardInfo");
		
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
  

function uploadifySuccess_resume(file, data, response){
	  //获取后台返回到前台的文件名，添加到隐藏域,多文件用";"号隔开
	  var files = $("#fileNmae",navTab.getCurrentPanel()).html();
	  var fileUrl = $("#fileUrl",navTab.getCurrentPanel()).val();
	  var fileName = $("#fileName",navTab.getCurrentPanel()).val();
	  var fileResult = data.split(";");
	  //第一个文件
	  if(files==""){
	    files = fileResult[0];
	    fileName = fileResult[0];
	    fileUrl = fileResult[1];
	  }else{
	    files+=";"+fileResult[0];
	    fileName+=";"+fileResult[0];
	    fileUrl+=";"+fileResult[1];
	  }
	  $("#fileNmae",navTab.getCurrentPanel()).html(files);
	  $("#fileUrl",navTab.getCurrentPanel()).val(fileUrl);
	  $("#fileName",navTab.getCurrentPanel()).val(fileName);
}
    

</script>

<div class="pageContent">
<h1>能力信息（表彰事项）</h1>
	<form id="essAddRewardInfo" method="post"
		action="/ess/empinfo/essAddRewardInfo"
		class="pageForm required-validate"
		onsubmit="return validateCallbackViewFamilyInfo(this, dialogAjaxDone);">

		<div class="pageFormContent" layoutH="56">
			<table width="100%" border="0" cellpadding="0" cellspacing="0"
				class="user_table">
				<c:if test="${APPLY_TYPE=='1'}">
					<input type="hidden" name="APPLY_TYPE" value="${1}" />

				</c:if>
				<c:if test="${APPLY_TYPE=='2'}">
					<input type="hidden" name="APPLY_TYPE" value="${2}" />

				</c:if>
				<input type="hidden" name="UPDATE_REWARD_NO" value="${REWARD_NO}" />

				<tr>

					<td class="td_title" >
						表扬/得奖
					</td>


					<td class="td_type" >

					<ait:SelectSyCodeByCpnyID name="REWARD_TYPE" parentNo="14014334"
							cnpyID="${defaultCpny}"  />
					</td>

				</tr>
				
				
				<tr>

					<td class="td_title" >
						表扬（得奖）日
					</td>


					<td class="td_type" >

						<input type="text" name="REWARD_DATE" class="Wdate required"
										readonly="true" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
					</td>

				</tr>

				<tr>

					<td class="td_title" >
				授予机关
					</td>


					<td class="td_type" >
					
					<input type="text" name="REWARD_CNPY" class="textInput" />
								
								
					</td>
				

				</tr>
				<tr>

					<td class="td_title" >
			     奖金
					</td>


					<td class="td_type" >
					
			    	<input type="text" name="REWARD" class="textInput" />
								
								
					</td>
					<td class="td_title" >
			     奖金支付类型代码
					</td>


					<td class="td_type">
					

					<ait:SelectSyCodeByCpnyID name="REWARD_TYPE_CODE" parentNo="14014347"
							cnpyID="${defaultCpny}"  />								
								
					</td>
				

				</tr>


				<tr>

					<td class="td_title" >
						备注
					</td>


					<td class="td_type">

						<textarea rows="4" cols="30" name='REMARKS'> </textarea>
					</td>

					

				</tr>




			</table>

			<div id="createTable" width="100%"></div>

			<input type="hidden" name="count" id="count" value="1">
		</div>
		<div class="formBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">
								<spring:message code="public.title.submit" />
								<!-- 保存 -->
							</button>
						</div>
					</div>
				</li>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" class="close">
								<spring:message code="public.title.cancle" />
								<!-- 取消 -->
							</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</form>
</div>