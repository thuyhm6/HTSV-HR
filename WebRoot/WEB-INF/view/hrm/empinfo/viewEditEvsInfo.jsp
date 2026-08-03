<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>


<script type="text/javascript">
<!--
function validateCallbackEditEvsInfo(form, callback) {


	var $form = $("#viewEditEvsInfo");
	
	if (!$form.valid()) {
		return false;
	}
   
	var checked=false;
	var ids= document.getElementsByName("EPNO");
	for(var i=0;i<ids.length;i++){
		if(ids[i].checked){
			checked=true;
		}
	}
	if(!checked){
		alertMsg.error('<spring:message code="hr.alert.message.viewPersonalInfo.checkBoxForChecked"/>');//请选择信息再进行保存操作!
		return false;
	}
	//确认要提交吗？
	if (confirm ('<spring:message code="hr.viewEvaluate.title.COMMIT_CONFIRM"/>')){	

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
function setCheckboxChecked(index){
  //var ckElems = document.getElementsByName(elemName);
  var tld="EPNO"+index
  //if (ckElems != null && ckElems.length != null &&index >=0){ 
    //ckElems(index).checked=true;
    document.getElementById(tld).checked=true;
  //}
}
//-->
</script>


<div class="pageContent">
	<form id="viewEditEvsInfo" method="post" action="/hrm/empinfo/editEvsInfo" class="pageForm required-validate" onsubmit="return validateCallbackEditEvsInfo(this, dialogAjaxDone);">
				
			<div class="panelBar" style="display:none">
				<ul class="toolBar">
					<li id="addLi">
						<span>&nbsp;</span>
					</li>
				</ul>
			</div>
			<table class="user_table margin_b" width="99%" layoutH="40">
            
                <c:forEach items="${evsInfoList}" var="item" varStatus="i">
                <tr>
                    <td rowspan="2" width="20" class="td_center">
                        <input type="checkbox" id="EPNO${i.count}" name="EPNO" value="${item.EV_PERIOD}" />
                    </td>
                    <td class="td_title">
                        <spring:message code="hr.viewEvaluate.title.EV_PERIOD"/>
                        <!--评价期间-->
                    </td>
                    
                    <td class="td_type">
                        <input onclick="setCheckboxChecked(${i.count})" type="text" name="EV_PERIOD_${item.EV_PERIOD}" class="textInput required" value="${item.EV_PERIOD }" />
                        <input type="hidden" name="EV_PERIOD1_${item.EV_PERIOD}" class="textInput required" value="${item.EV_PERIOD }" />
                    </td>
                    <td class="td_title">
                        <spring:message code="hr.viewEvaluate.title.EV_ACHI"/>
                        <!--업적-->
                    </td>
                    <td class="td_type">
                        <input onclick="setCheckboxChecked(${i.count})" type="text" name="EV_ACHI_${item.EV_PERIOD}" class="textInput number" maxlength="3" size="10" value="${item.EV_ACHI }"/>
                    </td>
                    
                   <td class="td_title">
                        <spring:message code="hr.viewEvaluate.title.EV_ATTI"/>
                        <!--태도-->
                    </td>
                    <td class="td_type">
                        <input onclick="setCheckboxChecked(${i.count})" type="text" name="EV_ATTI_${item.EV_PERIOD}" class="textInput number" maxlength="3" size="10" value="${item.EV_ATTI }"/>
                    </td>    
                    
                   <td class="td_title">
                        <spring:message code="hr.viewEvaluate.title.EV_ABIL"/>
                        <!--능력-->
                    </td>
                    <td class="td_type">
                        <input onclick="setCheckboxChecked(${i.count})" type="text" name="EV_ABIL_${item.EV_PERIOD}" class="textInput number" maxlength="3"  size="10" value="${item.EV_ABIL }"/>
                    </td>                                       
                                        
                    <td class="td_title">
                        <spring:message code="hr.viewEvaluate.title.EV_MARK"/>
                        <!--评价分数-->
                    </td>
                    <td class="td_type">
                        <input onclick="setCheckboxChecked(${i.count})" type="text" name="EV_MARK_${item.EV_PERIOD}" class="textInput number" maxlength="3" size="10" value="${item.EV_MARK }"/>
                    </td>
                    <td class="td_title">
                        <spring:message code="hr.viewEvaluate.title.EV_GRADE_NAME"/>
                        <!--评价等级-->
                    </td>
                    <td class="td_type">
                        <ait:SelectSyCodeByCpnyID onClickName="setCheckboxChecked(${i.count})" name="EV_GRADE_${item.EV_PERIOD}" parentNo="3538" cnpyID="${defaultCpny}" selected="${item.EV_GRADE }" />
                    </td>
                    
                </tr>
                <tr>
                    <td width="100" class="td_title">
                        <spring:message code="hr.viewSuggestion.title.Suggestion" />
                        <!--意见-->
                    </td>
                    <td class="td_type" colspan="3">
                        <textarea onclick="setCheckboxChecked(${i.count})" type="text" name="SUGGESTION_${item.EV_PERIOD}" maxlength="100" rows="1" cols=60" >${item.SUGGESTION}</textarea>
                    </td>
                    <td class="td_title">
                        <spring:message code="hr.viewFinalSequence.title.FinalSequence" />
                        <!--最终顺位-->
                    </td>
                    <td class="td_type">
                        <input onclick="setCheckboxChecked(${i.count})" type="text" name="FINAL_SEQUENCE_${item.EV_PERIOD}" value="${item.FINAL_SEQUENCE}" maxlength="100" size="10" />
                    </td>
                    <td width="100" class="td_title">
                        <spring:message code="hr.viewTotalPeople.title.TotalPeople" />
                        <!--总职级员人数-->
                    </td>
                    <td class="td_type">
                        <input onclick="setCheckboxChecked(${i.count})"  type="text" name="TOTAL_PEOPLE_${item.EV_PERIOD}" value="${item.TOTAL_PEOPLE }" maxlength="100" size="10" />
                    </td>
                    <td class="td_title">
                        <spring:message code="hr.viewPromote.title.REMARK"/>
                        <!--备注-->
                    </td>
                    <td class="td_type">
                        <input onclick="setCheckboxChecked(${i.count})" type="text" name="EV_REMARK_${item.EV_PERIOD}" class="textInput" value="${item.EV_REMARK }" maxlength="30" size="10" />
                    </td>
                    <td colspan="2"></td>
                </tr>
                    </c:forEach>
                
                
            
            
            
            
            
<%--           
				<thead>
					<tr>
						<td class="td_title" width="20"></td>
						<td class="td_title" width="100">
							<spring:message code="hr.viewEvaluate.title.EV_PERIOD"/>
							<!--评价期间-->
						</td>
						<!--<th width="100">  -->
							<!--<spring:message code="hr.viewEvaluate.title.EV_TYPE_NAME"/>-->
							<!--评价类型-->
						<!--</th>-->
                        
                        <td class="td_title" width="30">
                            <spring:message code="hr.viewEvaluate.title.EV_ACHI"/>
                            <!--업적-->
                        </td>
                        <td class="td_title" width="30">
                            <spring:message code="hr.viewEvaluate.title.EV_ATTI"/>
                            <!--태도-->
                        </td>
                        <td class="td_title" width="30">
                            <spring:message code="hr.viewEvaluate.title.EV_ABIL"/>
                            <!--능력-->
                        </td>
                        
						<td class="td_title" width="30">
							<spring:message code="hr.viewEvaluate.title.EV_MARK"/>
							<!--评价分数-->
						</td>
						<td class="td_title" width="50">
							<spring:message code="hr.viewEvaluate.title.EV_GRADE_NAME"/>
							<!--评价等级-->
						</td>
						
    					<td class="td_title" width="50" >
    						<spring:message code="hr.viewFinalSequence.title.FinalSequence" />
    						<!--最终顺位-->
    					</td>
    					
    					<td class="td_title" width="50">
    						<spring:message code="hr.viewTotalPeople.title.TotalPeople" />
    						<!--总职级员人数-->
    					</td>
    					
                        <td class="td_title" width="300" >
                            <spring:message code="hr.viewSuggestion.title.Suggestion" />
                            <!--意见-->
                        </td>
                        
                        
                        <td class="td_title" width="100">
							<spring:message code="hr.viewPromote.title.REMARK"/>
							<!--备注-->
						</td>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${evsInfoList}" var="item" varStatus="i">
					
						<tr target="PERSON_ID">
							<td class="td_type"><input type="checkbox" id="EPNO${i.count}" name="EPNO" value="${item.EV_PERIOD}" /></td>
							<td class="td_type">
								<input onclick="setCheckboxChecked(${i.count})" type="text" name="EV_PERIOD_${item.EV_PERIOD}" class="textInput required" value="${item.EV_PERIOD }" />
								<input type="hidden" name="EV_PERIOD1_${item.EV_PERIOD}" class="textInput required" value="${item.EV_PERIOD }" />
							</td>
							<!--<td><ait:SelectSyCodeByCpnyID onClickName="setCheckboxChecked(${i.count})" name="EV_TYPE_ID_${item.EV_PERIOD}" parentNo="14895" cnpyID="${defaultCpny}" selected="${item.EV_TYPE_ID }" /></td>  -->
                            <td class="td_type"><input onclick="setCheckboxChecked(${i.count})" type="text" name="EV_ACHI_${item.EV_PERIOD}" class="textInput number" maxlength="3" value="${item.EV_ACHI }"/></td>
                            <td class="td_type"><input onclick="setCheckboxChecked(${i.count})" type="text" name="EV_ATTI_${item.EV_PERIOD}" class="textInput number" maxlength="3" value="${item.EV_ATTI }"/></td>
                            <td class="td_type"><input onclick="setCheckboxChecked(${i.count})" type="text" name="EV_ABIL_${item.EV_PERIOD}" class="textInput number" maxlength="3" value="${item.EV_ABIL }"/></td>                                                                                    
							<td class="td_type"><input onclick="setCheckboxChecked(${i.count})" type="text" name="EV_MARK_${item.EV_PERIOD}" class="textInput number" maxlength="3" value="${item.EV_MARK }"/></td>
							<td class='td_center'><ait:SelectSyCodeByCpnyID onClickName="setCheckboxChecked(${i.count})" name="EV_GRADE_${item.EV_PERIOD}" parentNo="3538" cnpyID="${defaultCpny}" selected="${item.EV_GRADE }" /></td>
							<td class="td_type"><input onclick="setCheckboxChecked(${i.count})" type="text" name="FINAL_SEQUENCE_${item.EV_PERIOD}" value="${item.FINAL_SEQUENCE}" maxlength="100"/></td>
							<td class="td_type"><input onclick="setCheckboxChecked(${i.count})"  type="text" name="TOTAL_PEOPLE_${item.EV_PERIOD}" value="${item.TOTAL_PEOPLE }" maxlength="100"/></td>
							<td class="td_type">
                                <textarea onclick="setCheckboxChecked(${i.count})" type="text" name="SUGGESTION_${item.EV_PERIOD}" maxlength="100" rows="1" cols=60" >${item.SUGGESTION}</textarea>
                            </td>
                            <td class="td_type"><input onclick="setCheckboxChecked(${i.count})" type="text" name="EV_REMARK_${item.EV_PERIOD}" class="textInput" value="${item.EV_REMARK }" maxlength="30" /></td>
							
						</tr>
					
					</c:forEach>
					
				</tbody>
 --%>                 
			</table>
			
			
			<input type="hidden" name="PERSON_ID" class="textInput required" value="${PERSON_ID }" />
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