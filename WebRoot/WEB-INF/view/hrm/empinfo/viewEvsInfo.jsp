<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>



<script>
	function addrow(){
	    var count = parseInt($("#count").val());
	  
	    var i = count ;
	    var htm="";
	   		htm+='<table id="table' + i + '" border="0" width="100%" cellpadding="0" cellspacing="0" class="user_table margin_b">';
	   		htm+='<tr>';
	   		
	   		htm+='<td  class="td_title"><spring:message code="hr.viewEvaluate.title.EV_PERIOD"/><!--评价期间--></td>';
	   		htm+='<td  class="td_type"><input type="text" name="EV_PERIOD' + i + '" class="textInput required" maxlength="100"></td>';
	   		
	   	    // EV_TYPE_CD SELECT
	   		//htm+='<td  class="td_title"><spring:message code="hr.viewEvaluate.title.EV_TYPE_NAME"/><!--评价类型--></td>';
	   		//htm+='<td  class="td_type"><select name="EV_TYPE_ID' + i + '">' ;
	   		//	<!--<c:forEach items="${codeList}" var="item" >-->
	   		    //htm+='<option value="${item.CODE_NO}">${item.CODENAME}</option>';
			//	<!--</c:forEach>-->
	   		//htm+='</select></td>';

            htm+='<td class="td_title"><spring:message code="hr.viewEvaluate.title.EV_ACHI"/><!--업적--></td>'
            htm+='<td class="td_type"><input type="text" name="EV_ACHI'+i+'" class="textInput number" maxlength="3"></td>'
            htm+='<td class="td_title"><spring:message code="hr.viewEvaluate.title.EV_ATTI"/><!--태도--></td>'
            htm+='<td class="td_type"><input type="text" name="EV_ATTI'+i+'" class="textInput number" maxlength="3"></td>'
            htm+='<td class="td_title"><spring:message code="hr.viewEvaluate.title.EV_ABIL"/><!--능력--></td>'
            htm+='<td class="td_type"><input type="text" name="EV_ABIL'+i+'" class="textInput number" maxlength="3"></td>'
            
	   		htm+='<td  class="td_title"><spring:message code="hr.viewEvaluate.title.EV_MARK"/><!--评价分数--></td>';
	   		htm+='<td  class="td_type"><input type="text" name="EV_MARK' + i + '" class="textInput number" maxlength="3"></td>';
	   		
	   		
			htm+='<td  class="td_title"><spring:message code="hr.viewEvaluate.title.EV_GRADE_NAME"/><!--评价等级--></td>';
			htm+='<td  class="td_type"><select name="EV_GRADE' + i + '">' ;
	   			<c:forEach items="${gradeCodeList}" var="item" >
	   			htm+='<option value="${item.CODE_NO}">${item.CODENAME}</option>';
				</c:forEach>
	   		htm+='</select></td>';
	   		htm+='<td rowspan="2"><img src="/resources/css/ligerUI/skins/icons/delete.gif" onclick="f_delShiftInfo(\'table'+i+'\')" style="cursor:hand"> </td>';
	   		htm+='</tr>';
	   		htm+='<tr>';
	   		htm+='<td width="100" class="td_title"><spring:message code="hr.viewSuggestion.title.Suggestion" /><!--意见--></td>';		
	   		htm+='<td class="td_type" colspan="3"><textarea name="SUGGESTION'+i+'"maxlength="100" cols="60" rows="1" ></textarea></td>';
			
	   		htm+='<td class="td_title"><spring:message code="hr.viewFinalSequence.title.FinalSequence" /><!--最终顺位--></td>';
	   		htm+='<td class="td_type"><input type="text" name="FINAL_SEQUENCE'+i+'"maxlength="100"/></td>';
			
			htm+='<td width="100" class="td_title"><spring:message code="hr.viewTotalPeople.title.TotalPeople" /><!--总职级员人数--></td>';
			htm+='<td class="td_type"><input type="text" name="TOTAL_PEOPLE'+i+'"maxlength="100"/></td>';
			
			htm+='<td  class="td_title"><spring:message code="hr.viewPromote.title.REMARK"/><!--备注--></td>';
	   		htm+='<td  class="td_type"><input type="text" name="EV_REMARK' + i + '" class="textInput" maxlength="30" ></td>';
	   		
	   		htm+='<td colspan="2"></td>';

	   		htm+='</tr>';
	   		htm+='</table>';	   		
	   		

	   	$("#createTable").append(htm) ;

	   	count++;  
	    $("#count").attr("value",count) ;
    }


    function f_delShiftInfo(obj){
    	$("#"+obj+"").remove();
    }
    
    function validateCallbackViewEvsInfo(form, callback) {
	
		var $form = $("#viewEvsInfo");
		
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
	<form id="viewEvsInfo" method="post" action="/hrm/empinfo/addEvsInfo" class="pageForm required-validate" onsubmit="return validateCallbackViewEvsInfo(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="56">
			<table width="100%"  border="0" cellpadding="0" cellspacing="0" class="user_table margin_b">
				<tr>
					<td class="td_title">
						<spring:message code="hr.viewEvaluate.title.EV_PERIOD"/>
						<!--评价期间-->
					</td>
					
					<td class="td_type">
						<input type="text" name="EV_PERIOD0" class="textInput required" maxlength="100">
						
					</td>
					
					<input type="hidden" name="PERSON_ID" value="${PERSON_ID}" />
					<!--<td class="td_title">-->
						<!--<spring:message code="hr.viewEvaluate.title.EV_TYPE_NAME"/>-->
						<!--评价类型-->
					<!--</td>-->
					<!--<td class="td_type">-->
						<!--<ait:SelectSyCodeByCpnyID name="EV_TYPE_ID0" parentNo="14895" cnpyID="${defaultCpny}"/>-->
					<!--</td>-->
                    
                    <td class="td_title">
                        <spring:message code="hr.viewEvaluate.title.EV_ACHI"/>
                        <!--업적-->
                    </td>
                    <td class="td_type">
                        <input type="text" name="EV_ACHI0" class="textInput number" maxlength="3">
                    </td>
                    
                   <td class="td_title">
                        <spring:message code="hr.viewEvaluate.title.EV_ATTI"/>
                        <!--태도-->
                    </td>
                    <td class="td_type">
                        <input type="text" name="EV_ATTI0" class="textInput number" maxlength="3">
                    </td>    
                    
                   <td class="td_title">
                        <spring:message code="hr.viewEvaluate.title.EV_ABIL"/>
                        <!--능력-->
                    </td>
                    <td class="td_type">
                        <input type="text" name="EV_ABIL0" class="textInput number" maxlength="3">
                    </td>                                       
                                        
					<td class="td_title">
						<spring:message code="hr.viewEvaluate.title.EV_MARK"/>
						<!--评价分数-->
					</td>
					<td class="td_type">
						<input type="text" name="EV_MARK0" class="textInput number" maxlength="3">
					</td>
					<td class="td_title">
						<spring:message code="hr.viewEvaluate.title.EV_GRADE_NAME"/>
						<!--评价等级-->
					</td>
					<td class="td_type">
						<ait:SelectSyCodeByCpnyID name="EV_GRADE0" parentNo="3538" cnpyID="${defaultCpny}"/>
					</td>
					
					<td rowspan="2"><img src="/resources/css/ligerUI/skins/icons/add.gif" border="0" align="absmiddle" style="cursor:hand" onclick="addrow()"/></td>
				</tr>
				<tr>
					<td width="100" class="td_title">
						<spring:message code="hr.viewSuggestion.title.Suggestion" />
						<!--意见-->
					</td>
					<td class="td_type" colspan="3">
						<textarea name="SUGGESTION0" maxlength="100" cols="60" rows="1" ></textarea>
					</td>
					<td class="td_title">
						<spring:message code="hr.viewFinalSequence.title.FinalSequence" />
						<!--最终顺位-->
					</td>
					<td class="td_type">
						<input type="text" name="FINAL_SEQUENCE0" maxlength="100"/>
					</td>
					<td width="100" class="td_title">
						<spring:message code="hr.viewTotalPeople.title.TotalPeople" />
						<!--总职级员人数-->
					</td>
					<td class="td_type">
						<input type="text" name="TOTAL_PEOPLE0" maxlength="100"/>
					</td>
					<td class="td_title">
						<spring:message code="hr.viewPromote.title.REMARK"/>
						<!--备注-->
					</td>
					<td class="td_type">
						<input type="text" name="EV_REMARK0" class="textInput"  maxlength="30" >
					</td>
                    <td colspan="2"></td>
				</tr>
			</table>
			
			<div id="createTable" width="100%"></div>
		    <input type="hidden" name="count" id="count" value="1">
			
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit"><spring:message code="public.title.submit"/><!-- 保存 --></button></div></div></li>
				<li>
					<div class="button"><div class="buttonContent"><button type="button" class="close"><spring:message code="public.title.cancle"/><!-- 取消 --></button></div></div>
				</li>
			</ul>
		</div>
	</form>
</div>