<%@ page contentType="text/html; charset=UTF-8" language="java"
	errorPage=""%>
<%@page import="com.ait.sys.service.ToolMenuSer"%>
<%@page import="com.ait.sys.service.impl.ToolMenuSerImpl"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<script>
	//页面去除重复数据
	function distinctValue(name){
		var distinctValue=document.getElementsByName(name).value;
		for(var i=0;i<distinctValue.length;i++){
			
		}
	}
    // jjy check GridValue
    function validateGridValue(){
    	var $form = $("#diaolingForm");
        
        if (!$form.valid()) {
            return false;
        }
        
	    var isError = false;
        var vId ="";
        var vIdSelected ="";

        $("#tableTitleName tr option:selected").each(function(i, obj) {
            if($(obj).text()=="请选择" || $(obj).text()=="선택하세요" ){
                $(this).parent().css('border-color','Red');
                isError = true;
            }else{
                $(this).parent().css('border-color','');
            }
        });
        
        if(isError) { 
            alertMsg.error('<spring:message code="hr.alert.message.viewPersonalInfo.checkBoxForChecked"/>');
        }
        return isError;
    }
        
 // jjy check GridValue
    function validateGridValueForSubmit(){
        var isError = false;
        $("#tableTitleName tr .required").each(function(i,obj) {
            //발령신청인 경우는 해당라인의 체크박스가 선택된 경우만 확인한다.
            $(obj).parent().parent().find("td").each(function(i,ooo) {
                if($(ooo).children().attr("name")=="chexkbox"   ) {
                   if($(ooo).children().is(":checked")){
                	   //alert("find checkbox val:::"+$(ooo).children().val());
                    	if($(obj).val() == "") {
                            $(obj).css('border-color','Red');
                            //화면에 에러표시 추가해줘야 한다!!!
                            //$(obj).append('<span class="error" for="TRANS_ORDER_DATE_1" generated="true">必填字段</span>');
                            isError = true;    
                        }
                        else {
                            $(obj).css('border-color','');
                        } 
                	
                   }
                }
            });
                              
        })
        if(isError) { 
            return isError;
        }
        $("#tableTitleName tr option:selected").each(function(i,obj) {
            //발령신청인 경우는 해당라인의 체크박스가 선택된 경우만 확인한다.
            $(obj).parent().parent().parent().find("td").each(function(i,ooo) {
                if($(ooo).children().attr("name")=="chexkbox"   ) {
                   if($(ooo).children().is(":checked")){
                	    //alert("find checkbox val in select :::"+$(obj).text());
                	    if($(obj).text()=="请选择" || $(obj).text()=="선택하세요" ){
                            $(obj).parent().css('border-color','Red');
                            isError = true;
                        }else{
                            $(obj).parent().css('border-color','');
                        }
                   }
                }
            });
        });
        if(isError) { 
            alertMsg.error('<spring:message code="hr.alert.message.viewPersonalInfo.checkBoxForChecked"/>');
            return isError;
        }
        return isError;
    }
        
	
	function  validateCallbackViewOrderOperation1(v,callback,type1){
		
	    //jjy
	    //alert(type1);
	    var isValidationError = true;
	    if(type1 == "2") {
	    	isValidationError =  validateGridValue();
	    }else if(type1 == "1"){
	    	isValidationError =  validateGridValueForSubmit();    
	    }else{
	    	return false;
	    }
	    
		if(isValidationError) {
			return false;
		}
<%--		var colNum=document.getElementsByName("personid").value;--%>
<%--	--%>
<%--		document.getElementById("diaolingType").value=type;--%>
<%--		document.diaoling.action="/hrm/transferOrder/SaveHrExperienceInside?type="+type+"&colNum="+colNum;--%>
<%--		document.diaoling.submit();--%>
		
			if(type1=="1"){
				if($("#TRANSFER_ORDER_NO").val()==null||$("#TRANSFER_ORDER_NO").val()==""){
					alertMsg.error("请填写调令编号");
					return false;
				}
			}
			var cloumeNum=$("#tableTitleName tr").length;
			$("#cloumeNum").val(cloumeNum);
			$("#diaolingType").val(type1);
			var checkValue="";
			//调令申请1   保存2
			
			$("input[name='T1']:checkbox:checked").each(function(){ 
				checkValue+=$(this).val()+"-" ;
				
			}) 
			
			return false;
			if(checkValue==""){
				alertMsg.error("请选择调令");
				return false;
			}
			
			var nameValue=document.getElementsByName("T1");	
			var checkValues=checkValue.split("-");	
			for(var i=0; i<checkValues.length;i++){
				if(checkValues[i]==null||checkValues[i]==""){
					continue;
				}
				var sdateid="sdate_"+checkValues[i];
				
				var empidValue=document.getElementById("sdate_"+checkValues[i]).value;		
				if(empidValue==""){
					
					alertMsg.error("请填写调令日期");
					return false;
				}
			}
			for(var i=0; i<checkValues.length;i++){
				if(checkValues[i]==null||checkValues[i]==""){
					continue;
				}
				var empidValue=document.getElementById("edate_"+checkValues[i]).value;
				
				if(empidValue==""){
					
					alertMsg.error("请填写生效日期");
					return false;
				}
			}
				
			document.getElementById("checkBoxValue").value=checkValue;
			//$("#checkBoxValue").val(checkValue);
			
			//确定要提交吗？
			
			if (confirm ('<spring:message code="hr.viewEvaluate.title.COMMIT_CONFIRM"/>')){
				var $form = $("#diaolingForm");	
			  	$.ajax({
					type: 'POST',
					url:"/hrm/transferOrder/SaveHrExperienceInside_send_1",
					data:$form.serializeArray(),
					dataType:"json",
					cache: false,
					//success :callback || DWZ.ajaxDone,
			  		success: function(data) {
			  				
			  				alertMsg.info(data.message);
			  				    
			  				document.getElementById("OrderType").onchange();
			  		}	,
					error: DWZ.ajaxError
				});	
				return false;
			}
		
	}
	//调令申请
	function  validateCallbackViewOrderOperation(v,callback,type1){
		
	    //jjy
	    //alert(type1);
	    var isValidationError = true;
	    if(type1 == "2") {
	    	isValidationError =  validateGridValue();
	    }else if(type1 == "1"){
	    	isValidationError =  validateGridValueForSubmit();    
	    }else{
	    	return false;
	    }
	    
		if(isValidationError) {
			return false;
		}
		if(type1=="1"){
			if($("#TRANSFER_ORDER_NO").val()==null||$("#TRANSFER_ORDER_NO").val()==""){
				alertMsg.error("请填写调令编号");
				return false;
			}
		}
			var cloumeNum=$("#tableTitleName tr").length;
			$("#cloumeNum").val(cloumeNum);
			$("#diaolingType").val(type1);
			var checkValue="";
			var expno="";
			//调令申请1   保存2
			$("input[name='T1']:checkbox:checked").each(function(){ 
				checkValue+=$(this).val()+"-" ;
				var expid="EXPNO_"+$(this).val();
				
				expno+=$("#"+expid).val()+",";
			}) 
			$("#expids").val(expno);
			
			
			if(checkValue==""){
				alertMsg.error("请选择调令");
				return false;
			}
			
			var nameValue=document.getElementsByName("T1");	
			var checkValues=checkValue.split("-");	
			for(var i=0; i<checkValues.length;i++){
				if(checkValues[i]==null||checkValues[i]==""){
					continue;
				}
				var sdateid="sdate_"+checkValues[i];
				
				var empidValue=document.getElementById("sdate_"+checkValues[i]).value;		
				if(empidValue==""){
					
					alertMsg.error("请填写调令日期");
					return false;
				}
			}
			for(var i=0; i<checkValues.length;i++){
				if(checkValues[i]==null||checkValues[i]==""){
					continue;
				}
				var empidValue=document.getElementById("edate_"+checkValues[i]).value;
				
				if(empidValue==""){
					
					alertMsg.error("请填写生效日期");
					return false;
				}
			}
				
			document.getElementById("checkBoxValue").value=checkValue;
			//$("#checkBoxValue").val(checkValue);
			
			//确定要提交吗？
			
			if (confirm ('<spring:message code="hr.viewEvaluate.title.COMMIT_CONFIRM"/>')){
				var $form = $("#diaolingForm");	
			  	$.ajax({
					type: 'POST',
					url:"/hrm/transferOrder/SaveHrExperienceInside_send_2",
					data:$form.serializeArray(),
					dataType:"json",
					cache: false,
					//success :callback || DWZ.ajaxDone,
			  		success: function(data) {
			  				alertMsg.info(data.message);
			  				document.getElementById("OrderType").onchange();
			  		}	,
					error: DWZ.ajaxError
				});	
				return false;
			}
		
	}
	function downloadImportTemplate(){
		var selectedType=$("#OrderType :selected").val();
		if(selectedType != ''){
			var url = "/pa/excelExport/downloadHrSendAddress";
			document.getElementById("exportExcel").href=encodeURI(url);
		}else{
			alert('请选择发令类型');
		}
	}


	function getCurrentTransferOrderType(){
		var selectedType=$("#OrderType :selected").val();
		if(selectedType != ''){
			$("#importExcel").attr('href','/pa/excelImport/importExcelData?importFunName=/importOrderOperationExcel_new&transferOrder_type='+selectedType);
			$("#importExcel").click();
			
		}else{
			alertMsg.error("请先选择发令类型");
			return false;
		}
	}
	
	
	
	
	
</script>
<div class="pageContent">
 <input type="hidden" name="count" id="count" value="1">
<form id="diaolingForm"  name="diaolingForm" action="/hrm/transferOrder/SaveHrExperienceInside" method="post" onsubmit="return validateCallbackViewOrderOperation(this,navTabAjaxDone,type);">  
<input type="hidden" value="" id="expids" name="expids"/>
<input type="hidden" value="" id="checkBoxValue" name="checkBoxValue" value=""/>
<input type="hidden" value="" id="diaolingType" name="type"/>
	<div class="panel">
		<h1>
			
		</h1>
		<div>
			<%@ include file="/WEB-INF/view/hrm/empinfo/viewPersonalInfoHead6.jsp"%>
		</div>
	</div>
		<table width="100%" >
			<tr>
				
				<td class="td_title" align="left">
					<spring:message code="heran.examineType.date"/><!--调令日期				--> 
				</td>
				<td class="td_type">
				<input type="text"  name="diaolingDate" id="diaolingDate" class="date" readonly="true" style="width: 100px" format="yyyy-MM-dd" yearstart="-50" yearend="5" onClick="setdate(this);"/>
				<%--<input type="button" value="保存"  onclick="diaolingSubmit(this,2)" />--%>
				</td>
			
				
				<td align="right">
					<a style="float:right; " class="buttonActive" id ="exportExcel" onclick="downloadImportTemplate();" href="#"><span><spring:message code="pa.insurance.title.downloadImportTemplate"/><!--下载导入模板--></span></a>
					<div style="float:right; " class="buttonActive"  onclick="getCurrentTransferOrderType();"><span><spring:message code="ar.addempshift.title.excelimport"/><!-- EXCEL导入 --></span></div>
					<a style="float:right; " class="buttonActive"  onclick="validateCallbackViewOrderOperation1(this,navTabAjaxDone,2)" href="#">
						<span>
						<spring:message code="heran.examineSave.title"/><!--保存			--> 
						</span>
					</a>
					<a style="float:right; " class="buttonActive"  onclick="validateCallbackViewOrderOperation(this,navTabAjaxDone,1)" href="#">
					<span>
					<spring:message code="heran.examineApply.title"/><!--调令申请			--> 
					
					</span>
					</a>
				<%--<input type="button" value="调令申请" onclick="diaolingSubmit(this,1)" />--%>
			</td>
			</tr>
		
		</table>
		
		<a   id="importExcel"  href="#" target="dialog" mask="true"></a>
		
		<div id="createTable"></div>
</form>
		<div id="error" style="display: none"></div>	
	</div>	
	