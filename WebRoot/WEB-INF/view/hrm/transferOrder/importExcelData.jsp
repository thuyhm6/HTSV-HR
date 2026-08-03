<%@ page contentType="text/html;charset=utf-8"%>
<%@ include file="/WEB-INF/view/inc/initTaglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<title><!-- EXCEL导入 --><spring:message code="ar.addempshift.title.excelimport"/></title>
<script type="text/javascript">
 
	function uploadExcel(form, callback){
		
		  var imgpath=document.getElementById("filename").value;
		  var imgPostfix=imgpath.split(".");
		  if(imgpath != ""){
		     if(imgPostfix[imgPostfix.length-1] == "xls"  ){
		         // window.document.addimgform.action= "/pa/excelImport/importExcel";
			      //window.document.addimgform.fireSubmit();
			     
		    	 var $form = $(form), $iframe = $("#callbackframe");
		    		if(!$form.valid()) {return false;}
		    		//转化URL  $ to &
		  		    var url = $form.attr("action");
		    		 $form.attr("action",url.replace(/\\$/g,"&"));
		    		if ($iframe.size() == 0) {alert(1);
		    			$iframe = $("<iframe id='callbackframe' name='callbackframe' src='about:blank' style='display:none'></iframe>");
		    			$form.appendTo($iframe);
		    			
		    		}
		    		if(!form.ajax) {
		    			$form.append('<input type="hidden" name="ajax" value="1" />');
		    		}
		    	_iframeResponse($iframe[0], function(data){
		    	alertMsg.info(data.statusCode);	
			  	alertMsg.info("<spring:message code='ar.alert.message.excelimport.importfail'/>");
			  	 $("#errorExcelValue").val(data.message); 
		    	//$.pdialog.closeCurrent();
		    	parent.document.getElementById("OrderType").onchange();
		    	//parent.document.getElementById("error").style.display = "block";
		    	//parent.document.getElementById("error").innerHTML = data.message;
		    	
		    	 getErrorExcel();
		    	
		    }); 
		    	
		     }else{
			     //格式错误，请正确选择execl格式。
		     	 alert("<spring:message code='ar.alert.message.excelimport.typeerror'/>");
		     }
		   }else{
			   	//上传文件不能为空！
		        alert("<spring:message code='ar.alert.message.excelimport.fillnull'/>");
		   }
	} 
	
	function getErrorExcel(){
			
			var errorExcelValue = encodeURI($("#errorExcelValue").val());  
       			 errorExcelValue = encodeURI(errorExcelValue);  //需要通过两次编码  
  
			$("#importExcel").attr('href','/hrm/transferOrder/errorExcel?errorExcelValue='+errorExcelValue);
			$("#importExcel").click();
	}
</script>
</head>
<body> 
	<center>
		<div>
			<a id="errorExcel"  href="#" target="dialog" mask="true"></a>
			<input type="hidden" value=""  id="errorExcelValue" name="errorExcelValue"/>
			<form id="addimgform" method="post" action="/pa/excelImport${importFunName}?TEMPLATE_TYPE=${TEMPLATE_TYPE}" 
				enctype="multipart/form-data" class="pageForm required-validate" onsubmit="return uploadExcel(this,dialogAjaxDone);" target="callbackframe">
				<table width="100%" >
					<tr>
						<td style="text-align: center;padding:5px">
							<font color=red><!-- 提示：目前只支持以xls后缀结尾的excel格式（2003版本） -->
								<spring:message code="ar.alert.message.excelimport.friendasu"/>
							</font>
						</td>
					</tr>
					<tr>
						<td style="text-align: center;padding:5px">&nbsp;&nbsp;<input type="file" name="filename" id="filename"/>
						<iframe id='callbackframe' name='callbackframe' src='about:blank' style='display:none'></iframe>
						</td>
						<td style="text-align: center;padding:5px">
							<div class="buttonActive" style="text-align: center;">
								<div class="buttonContent" style="text-align: center;">
									<button type="submit" style="text-align: center;">
										<spring:message code="pa.insurance.title.submit"/><!-- 提交 -->
									</button>
								</div>
							</div>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</center>
</body>
</html>