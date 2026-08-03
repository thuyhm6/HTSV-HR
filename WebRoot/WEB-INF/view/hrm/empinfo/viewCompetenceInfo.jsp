<%@ page contentType="text/html; charset=UTF-8" language="java"  errorPage="" %>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>

<script type="text/javascript">
	function addrow(){
	    var count = parseInt($("#count").val());
	  
	    var i = count ;
	    var htm="";
	   		htm+='<table id="table' + i + '" border="0" width="100%" cellpadding="0" cellspacing="0" class="user_table margin_b">';
	   		htm+='<tr>';
	   		
	   		htm+='<td  class="td_title"><spring:message code="hr.viewCompetence.title.QUAL_NAME"/><!--资格证名称--></td>';
	   		htm+='<td class="td_type"><input type="text" id="QUAL_NAME' + i + '" name="QUAL_NAME' + i + '" class="required" maxlength="20"></td>';
	   		
	   		/*htm+='<td  class="td_title"><spring:message code="hr.viewCompetence.title.QUAL_CARD_NO"/><!--证件号--></td>';
	   		htm+='<td class="td_type"><input type="text" name="QUAL_CARD_NO' + i + '" class="textInput alphanumeric required" maxlength="50"/></td>';*/
	   		
	   		htm+='<td  class="td_title"><spring:message code="hr.viewCompetence.title.QUAL_LEVEL_NAME"/><!--证件级别--></td>';
	   		htm+='<td class="td_type"><select name="QUAL_LEVEL' + i + '"><option value=""><spring:message code="hr.viewPersonalInfo.title.ADDED_BY_KELI"/></option>' ;
	   			<c:forEach items="${qualLevelList}" var="item" >
	   			htm+='<option value="${item.CODE_NO}">${item.CODENAME}</option>';
				</c:forEach>
	   		htm+='</select></td>';
	   		
	   		htm+='<td class="td_title"><spring:message code="hr.viewCompetence.title.QUAL_GRADE_NAME"/><!--职称--></td>';
	   		htm+='<td class="td_type"><select name="QUAL_GRADE' + i + '"><option value=""><spring:message code="hr.viewPersonalInfo.title.ADDED_BY_KELI"/></option>';
                <c:forEach items="${qualGradeList}" var="item" >
                htm+='<option value="${item.CODE_NO}">${item.CODENAME}</option>';
                </c:forEach>
            htm+='</select></td>';
        	
	   		htm+='<td  class="td_title"><spring:message code="hr.viewCompetence.title.QUAL_INSTITUTE"/><!--发证处--></td>';
	   		htm+='<td class="td_type"><input type="text" name="QUAL_INSTITUTE' + i + '" class="textInput" maxlength="30"/></td>';
	   		
	   		
	   		htm+='<td class="td_title"><spring:message code="hr.viewCompetence.title.DATE_OBTAINED"/><!--取证日期--></td>';
	   		htm+='<td class="td_type"><input type="text" id="DATE_OBTAINED' + i + '" name="DATE_OBTAINED' + i + '" class="date required" readonly="true" format="yyyy-MM-dd" yearstart="-50" yearend="5" onClick="setdate(this);"/></td>';
	   		
	   		htm+='<td class="td_title" style="display:none"><spring:message code="hr.viewCompetence.title.VALIDITY_DATE"/><!--有效期--></td>';
	   		htm+='<td class="td_type"  style="display:none"><input type="text" id="VALIDITY_DATE' + i + '" name="VALIDITY_DATE' + i + '" class="date" readonly="true" format="yyyy-MM-dd" yearstart="-50" yearend="5" onClick="setdate(this);"/></td>';
	   	
	   		
	   		htm+='<td class="td_title"><spring:message code="hr.viewPersonalInfo.title.jintiebiaozhun"/><!--津贴标准(金额)--></td>';
            htm+='<td class="td_type"><input type="text" name="QUAL_REMARK' + i + '" class="textInput" maxlength="100"/></td>';
            
	   		htm+='<td rowspan="2"><img src="/resources/css/ligerUI/skins/icons/delete.gif" onclick="f_delShiftInfo(\'table'+i+'\')" style="cursor:hand"> </td>';
	   		htm+='</tr>';
	   		htm+='</table>';
			
	   	$("#createTable").append(htm) ;

	   	count++;  
	    $("#count").attr("value",count) ;
    }
    
    function addrowL(){
	    var countL = parseInt($("#countL").val());
	   
	    var j = countL ;
	    var htm="";
	   		htm+='<table id="tableL' + j + '" border="0" width="100%" cellpadding="0" cellspacing="0" class="user_table margin_b">';
	   		htm+='<tr>';
	   		
	   		htm+='<td  class="td_title"><spring:message code="hr.viewCompetence.title.LANGUAGE_TYPE_NAME"/><!--语言类型--></td>';
	   		htm+='<td class="td_type"><select name="LANGUAGE_TYPE_CODE' + j + '"><option value="">请选择</option>' ;
	   			<c:forEach items="${languageTypeCodeList}" var="item" >
	   			htm+='<option value="${item.CODE_NO}">${item.CODENAME}</option>';
				</c:forEach>
	   		htm+='</select></td>';

	   		htm+='<td  class="td_title"><spring:message code="hr.viewCompetence.title.EXAM_NAME"/><!--考试名--></td>';
	   		htm+='<td class="td_type"><select name="EXAM_NAME_CODE' + j + '"><option value="">请选择</option>' ;
	   			<c:forEach items="${examNameCodeList}" var="item" >
	   			htm+='<option value="${item.CODE_NO}">${item.CODENAME}</option>';
				</c:forEach>
	   		htm+='</select></td>';
	   		
	   		htm+='<td  class="td_title"><spring:message code="hr.viewCompetence.title.QUALIFICATION_NAME"/><!--证书名称--></td>';
	   		htm+='<td class="td_type"><input type="text" name="QUALIFICATION_NAME' + j + '" class="textInput required" maxlength="30"/></td>';
	   		
	   		htm+='<td rowspan="2"><img src="/resources/css/ligerUI/skins/icons/delete.gif" onclick="f_delShiftInfoL(\'tableL'+j+'\')" style="cursor:hand"> </td>';
	   		
	   		htm+='</tr>';
	   		htm+='<tr>';
	   		
	   		
	   		htm+='<td  class="td_title"><spring:message code="hr.viewCompetence.title.LANGUAGE_LEVEL_NAME"/><!--等级--></td>';
	   		htm+='<td class="td_type"><select name="LANGUAGE_LEVEL_CODE' + j + '"><option value="">请选择</option>' ;
	   			<c:forEach items="${languageLevelCodeList}" var="item" >
	   			htm+='<option value="${item.CODE_NO}">${item.CODENAME}</option>';
				</c:forEach>
	   		htm+='</select></td>';
	   		
	   		htm+='<td  class="td_title"><spring:message code="hr.viewCompetence.title.MARK"/><!--分数--></td>';
	   		htm+='<td class="td_type">	<input type="text" name="MARK' + j + '" class="textInput" maxlength="3"/></td>';
	   		
	   		htm+='<td  class="td_title"><spring:message code="hr.viewCompetence.title.DATE_OBTAINED_QUALIFICATION"/><!--取得日期--></td>';
	   		htm+='<td class="td_type"><input type="text" id="DATE_OBTAINEDL' + j + '" name="DATE_OBTAINEDL' + j + '" class="date required" readonly="true" format="yyyy-MM-dd" yearstart="-50" yearend="5" onClick="setdate(this);"/></td>';
	   		
	   		htm+='</tr>';
	   		htm+='</table>';

	   	$//("#createTableL").append(htm) ;

	   	countL++;  
	    //$("#countL").attr("value",countL) ;
    }


    function f_delShiftInfo(obj){
    	$("#"+obj+"").remove();
    }
    
     function f_delShiftInfoL(obj){
    	$("#"+obj+"").remove();
    }
    
    function validateCallbackViewCompetenceInfo(form, callback) {
	
		var $form = $("#viewCompetenceInfo");
		
		if (!$form.valid()) {
			return false;
		}
		
		/*if(document.getElementById("languageDiv").style.display=='none' && document.getElementById("qualDiv").style.display == 'none' ){
			//alert('您没有添加任何信息请确认');
			alertMsg.error('<spring:message code="hr.alert.message.viewCompetence.addNull"/>');
			return false;
		}*/
		
		var count = parseInt($("#count").val());
		if(document.getElementById("qualDiv").style.display=='block'){
			for (i=0;i<count;i++){	
			 if(document.getElementById("DATE_OBTAINED"+i)!=null){
				if(document.getElementById("VALIDITY_DATE"+i).value != null && document.getElementById("VALIDITY_DATE"+i).value !=""){
					
					var sd=document.getElementById("DATE_OBTAINED"+i).value;
					var ed=document.getElementById("VALIDITY_DATE"+i).value;
					
					var date1 = sd.replaceAll("-","");
					var date2 = ed.replaceAll("-","");
					
					if (date1 - date2 > 0) {
						activity="1";
						//alert("取证日期不能晚于有效期");
						alertMsg.error('<spring:message code="heran.hr.viewLanguage.DATENULL"/>');
						document.getElementById("VALIDITY_DATE"+i).focus();
						return false;
					}
				}
			}
		}
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
	
	function showLanguageDiv(obj){
		document.getElementById("languageDiv").style.display=obj;
		var countL = parseInt($("#countL").val());
		
		if(obj == 'none'){
			
			for(i=0;i<=countL;i++){
			
				$("#tableL"+i+"").remove();
				document.getElementById("DATE_OBTAINEDL0").value='';
				
			}
			
		}
	}
	
	function showQualDiv(obj){
		document.getElementById("qualDiv").style.display=obj;
		var count = parseInt($("#count").val());
		
		if(obj == 'none'){
			
			for(i=1;i<=count;i++){
			
				$("#table"+i).remove();
				document.getElementById("QUAL_CARD_NO0").value='';
				
			}
		
		}
		
	}
	
	
</script>

<div class="pageContent">
	<form id="viewCompetenceInfo" method="post" action="/hrm/empinfo/addCompetenceInfo" class="pageForm required-validate" onsubmit="return validateCallbackViewCompetenceInfo(this, dialogAjaxDone);">
		<div class="pageFormContent" id="qualDiv" style="display:block" layoutH="96">
			
			<div>
				<table width="100%"  border="0" cellpadding="0" cellspacing="0" class="user_table margin_b">
					<tr>
						<td class="td_title">
							<spring:message code="hr.viewCompetence.title.QUAL_NAME"/>
							<input type="hidden" name="PERSON_ID" class="textInput" value="${PERSON_ID }"/>
							<!--资格证名称-->
						</td>
						<td class="td_type">
							<input type="text" name="QUAL_NAME0" class="textInput required" maxlength="30"/>
							
						</td>
						<!--<td class="td_title">
							<spring:message code="hr.viewCompetence.title.QUAL_CARD_NO"/>
							证件号
						</td>
						<td class="td_type">
							<input type="text" id="QUAL_CARD_NO0" name="QUAL_CARD_NO0" class="textInput alphanumeric required" maxlength="50"/>
						</td>
						-->
						<td class="td_title">
							<spring:message code="hr.viewCompetence.title.QUAL_LEVEL_NAME"/>
							<!--证件级别-->
						</td>
						<td class="td_type">
							<ait:SelectSyCodeByCpnyID name="QUAL_LEVEL0" parentNo="14910" cnpyID="${defaultCpny}" limit="all"/>
						</td>
                        <td class="td_title">
                            <spring:message code="hr.viewCompetence.title.QUAL_GRADE_NAME"/>
                            <!--职称-->
                        </td>
                        <td class="td_type">
                            <ait:SelectSyCodeByCpnyID name="QUAL_GRADE0" parentNo="123485" cnpyID="${defaultCpny}" limit="all"/>
                        </td>
						<td class="td_title">
							<spring:message code="hr.viewCompetence.title.QUAL_INSTITUTE"/>
							<!--发证处-->
						</td>
						<td class="td_type">
							<input type="text" name="QUAL_INSTITUTE0" class="textInput" maxlength="30"/>
						</td>
						
					
						<!--<td class="td_title">
							<spring:message code="hr.viewCompetence.title.ACQUISITION_NAME"/>
							取得方式
						</td>
						<td class="td_type">
							<ait:SelectSyCodeByCpnyID name="ACQUISITION_MODES0" parentNo="14906" cnpyID="${defaultCpny}" limit="all"/>
						</td>
						-->
						<td class="td_title">
							<spring:message code="hr.viewCompetence.title.DATE_OBTAINED"/>
							<!--取证日期-->
						</td>
						<td class="td_type">
							<input type="text" id="DATE_OBTAINED0"  name="DATE_OBTAINED0" class="date required" readonly="true" format="yyyy-MM-dd" yearstart="-50" yearend="5" onClick="setdate(this);"/>
						</td>
						<td class="td_title" style="display:none">
							<spring:message code="hr.viewCompetence.title.VALIDITY_DATE"/>
							<!--有效期-->
						</td>
                        <td class="td_type" style="display:none"> 
												                          
							<input type="text" id="VALIDITY_DATE0"  name="VALIDITY_DATE0" class="date" readonly="true" format="yyyy-MM-dd" yearstart="-50" yearend="5" onClick="setdate(this);"/>
						</td>
                        <td class="td_title">
                            <spring:message code="hr.viewPersonalInfo.title.jintiebiaozhun"/><!--津贴标准(金额)-->
                        </td>
                        <td class="td_type">
                            <input type="text" name="QUAL_REMARK0" class="textInput" maxlength="100"/>
                        </td>
						<td class="td_type" rowspan='2'><img src="/resources/css/ligerUI/skins/icons/add.gif" border="0" align="absmiddle" style="cursor:hand" onclick="addrow()"/></td>
					</tr>
				</table>
				<div id="createTable" width="100%"></div>
			<input type="hidden" name="count" id="count" value="1">
			</div>
			
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